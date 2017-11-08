package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LDHealthDB;
import com.sinosoft.lis.db.LDHealthSubDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDHealthSchema;
import com.sinosoft.lis.vschema.LDHealthSet;
import com.sinosoft.lis.vschema.LDHealthSubSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

public class LLUWAutoHealthQueryBL implements BusinessService {
private static Logger logger = Logger.getLogger(LLUWAutoHealthQueryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public  CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData ;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	
	private String mCalCode; //计算编码
        private String mPolType = ""; //保单类型

	private FDate fDate = new FDate();
	private double mValue;
    
	private String mInsuredNo = "";
	private String mContNo = "";
	private TransferData mTransferData = new TransferData();
	/** 保费项表 */
	
    /**体检资料描述表**/
    private LDHealthSet mLDHealthSet = new LDHealthSet();
    private LDHealthSet mAllLDHealthSet = new LDHealthSet();


	public LLUWAutoHealthQueryBL() {}

	/**
	* 传输数据的公共方法
	* @param: cInputData 输入的数据
	*         cOperate 数据操作
	* @return:
	*/
	public boolean submitData(VData cInputData,String cOperate)
	{
		this.mOperate = cOperate;
		//将操作数据拷贝到本类中
		mInputData = (VData)cInputData.clone();

		logger.debug("---1---");
	   	//得到外部传入的数据,将数据备份到本类中
		//按照被保人的保额初始化体检项目
		if(mOperate.equals("INSERT"))
		{
	    	if (!getInputData(cInputData))
	        	return false;
	    		logger.debug("----UWAutoHealthQueryBL getInputData----");
                logger.debug("----UWAutoHealthQueryBL checkData----");
                // 数据操作业务处理
                if (!dealData())
                  //continue;
                  return false;
            	//准备给后台的数据
    	    	prepareOutputData();

		}
		//初始化体检项目界面
		else if(this.mOperate.equals("INIT"))
		{
			//
			//查询所有体检
			if(!initHealth())
			{
				return false;
			}
			
		}
		logger.debug("----UWAutoHealthQueryBL dealData----");
	
		logger.debug("----UWAutoHealthQueryBL prepareOutputData----");
		//数据提交
	    return true;
	}

	/**
	 * 查询界面初始化的体检项目
	 * @return
	 */
	private boolean initHealth()
	{
		//开始查询体检项目组合
		String tSQL_Main = "select healthcode,healthname,subhealthcode from ldhealth "
					+ " group by healthcode,healthname,subhealthcode "
					+ " order by healthcode,subhealthcode ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSQL_Main);
		SSRS tSSRS_Main = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS_Main = tExeSQL.execSQL(sqlbv);
		
		String tSQL_Sub = "select subhealthcode,subhealthname,sum(note2/1) x from ldhealthsub "
			            + " group by subhealthcode,subhealthname "
			            + " order by sum(note2/1) ";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSQL_Sub);
		SSRS tSSRS_Sub = new SSRS();
		tSSRS_Sub = tExeSQL.execSQL(sqlbv1);
		this.mInputData.add(0,tSSRS_Main);
		this.mInputData.add(1,tSSRS_Sub);
		return true;
	}
	/**
	* 数据操作类业务处理
	* 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	*/
	private boolean dealData()
	{
        logger.debug("---dealData---");
		if (dealOnePol() == false)
			return false;

		return true;
	}

	/**
	* 操作一张保单的业务处理
	* 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	*/
	private boolean dealOnePol()
	{
		logger.debug("---dealOnePol Begin---");
	  // 健康信息
		if (prepareHealthNew() == false)
			return false;
        logger.debug("---dealOnePol End---");
//                LDHealthSet tLDHealthSet = new LDHealthSet();
//                tLDHealthSet.set( mLDHealthSet );
//		mAllLDHealthSet.add( tLDHealthSet );

		return true;
	}

	/**
	* 从输入数据中得到所有对象
	*输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	*/
	private boolean getInputData(VData cInputData)
	{
	    GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
		mOperate = tGlobalInput.Operator;

		this.mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);
		//获取合同号和被保人号
		this.mContNo = (String)mTransferData.getValueByName("ContNo");
		this.mInsuredNo = (String)mTransferData.getValueByName("InsuredNo");
		
		if(this.mContNo==null||this.mContNo.equals(""))
		{
			CError.buildErr(this,"合同号为空!");
			return false;
		}
		if(this.mInsuredNo==null||this.mInsuredNo.equals(""))
		{
			CError.buildErr(this,"客户号为空!");
			return false;
		}
		return true;
	}

	/**
	 * 处理特殊体检项目
	 * @param tLDHealthSchema
	 * @param tItemType
	 * @param tSex
	 * @param tAge
	 * @param tMoney
	 */
	private void prepareSpecHealth(LDHealthSchema tLDHealthSchema,String tItemType,String tSex,int tAge,double tMoney )
	{
		//查询是否有特殊的体检
		//tongmeng 2008-12-10 modify
		//按照LDHealth表带出的体检项目,如果不符合子项目的要求,那么需要过滤掉.
		String tSubHealthCodeStr = tLDHealthSchema.getSubHealthCode();
		String[] tSubHealth = tSubHealthCodeStr.split("#");
		String tSubHealthCodeFinal = "";
		
		
		String tSQL = "";
		for (int n = 0; n < tSubHealth.length; n++) {
			String tSubCodeCurr = tSubHealth[n];
			if(tSubCodeCurr==null||tSubCodeCurr.equals(""))
			{
				continue;
			}
			tSQL = " select * from LDHealthSub where " 
				 + " (healthtype = '" + "?healthtype?" + "' and (note1 is null or note1<>'X') )"
				 // X 表示不区分性别
				 + " and sex in ('" + "?sex?" + "','X') " 
				 + " and StartAge <= "+ "?age?" 
				 + " and EndAge >= " + "?age?" 
				 + " and StartMoney <= " + "?money?" + " " 
				 + " and EndMoney >= " + "?money?" + " "
				 + " and subhealthcode='"+"?subhealthcode?"+"' "
				 + " union "
				 + " select * from LDHealthSub where " 
				 + " note1 ='X' "
				 // X 表示不区分性别
				 + " and sex in ('" + "?sex?" + "','X') "
				 + " and subhealthcode='"+"?subhealthcode?"+"' ";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tSQL);
			sqlbv2.put("healthtype", tItemType);
			sqlbv2.put("sex", tSex);
			sqlbv2.put("age", tAge);
			sqlbv2.put("money", tMoney);
			sqlbv2.put("subhealthcode", tSubCodeCurr);
			LDHealthSubSet tLDHealthSubSet = new LDHealthSubSet();
			LDHealthSubDB tLDHealthSubDB = new LDHealthSubDB();
			tLDHealthSubSet = tLDHealthSubDB.executeQuery(sqlbv2);
			if(tLDHealthSubSet.size()<=0)
			{
				continue;
			}
			if(tSubHealthCodeFinal.equals(""))
			{
				tSubHealthCodeFinal = tSubCodeCurr;
			}
			else
			{
				tSubHealthCodeFinal = tSubHealthCodeFinal + "#" + tSubCodeCurr;
			}
		}
		logger.debug("tSubHealthCodeFinal:"+tSubHealthCodeFinal);
		tLDHealthSchema.setSubHealthCode(tSubHealthCodeFinal);
	}
	/**
	 * tongmeng 2008-12-08 add
	 * 新的体检项目带出算法
	 * @return
	 */
	private boolean prepareHealthNew()
	{
		//取出被保人的性别,投保年龄等信息,如果没有查询出来,那么认为发送对象为投保人,查询投保人的信息
		int tAge = 0;
		String tBirthDay = "";
		//性别,0-男,1-女
		String tSex = "0";
		//客户类别,I-被保人,A-投保人
		String tCustomerType = "I";
		String tPolApplyDate = "";
		
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(this.mContNo);
		tLCInsuredDB.setInsuredNo(this.mInsuredNo);
		if(!tLCInsuredDB.getInfo())
		{
			//没查询到,认为发送对象为投保人
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(this.mContNo);
			tLCAppntDB.setAppntNo(this.mInsuredNo);
			if(!tLCAppntDB.getInfo())
			{
				CError.buildErr(this,"没有查询到体检人基本信息!");
				return false;
			}
			tCustomerType = "A";
			tSex = tLCAppntDB.getAppntSex();
			//查询投保年龄
			tBirthDay = tLCAppntDB.getAppntBirthday();
		}
		else
		{
			tSex = tLCInsuredDB.getSex();
			tBirthDay = tLCInsuredDB.getBirthday();
		}
		//查询合同投保时间,用于计算投保年龄
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(this.mContNo);
		if(!tLCContDB.getInfo())
		{
			CError.buildErr(this,"查询合同信息出错!");
			return false;
		}
		tPolApplyDate = tLCContDB.getPolApplyDate();
		//计算年龄
		tAge = PubFun.calInterval(tBirthDay,tPolApplyDate, "Y");
		
		String tSQL_Flag = "";
		tSQL_Flag = "select count(*) from lcpol where insuredno='"+"?insuredno?"+"' "
		          + " and riskcode not in ('112211','121602','121504','121303','112214','121801','121507')";
		String tFlag = "";
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSQL_Flag);
		sqlbv3.put("insuredno", this.mInsuredNo);
		ExeSQL tExeSQL = new ExeSQL();
		tFlag = tExeSQL.getOneValue(sqlbv3);
		if(tExeSQL.mErrors.needDealError()||tFlag==null||tFlag.equals(""))
		{
			//有错误信息
			CError.buildErr(this,"判断客户是否投保过久久系列产品出错!");
			return false;
		}
		//累计被保人寿险风险保额
		//累计被保人重疾风险保额
		double RiskAmnt1=0;//累计寿险风险保额
		double RiskAmnt2=0;//累积重疾风险保额
		double RiskAmnt3=0;//累积医疗险风险保额
		double RiskAmnt4=0;//
		double RiskAmnt6=0;
		double tRiskAmnt=0;//险种累计的保额
		double RiskAmntLAndH=0;//寿险和重疾风险保额和
		
		String risktype = "";
		ExeSQL riskSql = new ExeSQL();
		SSRS sumAmntSSRS = new SSRS(); // 险种个数
		SSRS typeSSRS = new SSRS(); // 险种分类
		SSRS AmntSSRS = new SSRS(); // 各种保额
		String tsql = "select distinct riskcode from lcpol where " 
				//+"contno='"+ mContNo + "' and "
				+"insuredno='"+ "?insuredno?" + "'";
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql(tsql);
		sqlbv4.put("insuredno", mInsuredNo);
		sumAmntSSRS = riskSql.execSQL(sqlbv4);
		for (int i = 1; i <= sumAmntSSRS.MaxRow; i++) {
			tsql = "select RiskSortValue from lmrisksort where riskcode='"
					+ "?riskcode?" + "' and risksorttype='3'";
			SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
			sqlbv5.sql(tsql);
			sqlbv5.put("riskcode", sumAmntSSRS.GetText(i, 1));
			typeSSRS = riskSql.execSQL(sqlbv5);
			risktype = typeSSRS.GetText(1, 1); // 险种分类
			if("1".equals(risktype)||"2".equals(risktype)||"3".equals(risktype)||"4".equals(risktype)||"6".equals(risktype)){
				/** @todo Second healthamnt */
//				tsql = "select healthyamnt2('" + "?insuredno?"
//						+ "','" + "?sumamnt?" + "','" + "?risktype?"
//						+ "') from dual";
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tsql = "select healthyamnt2('" + "?insuredno?" + "','" + "?sumamnt?" + "','" + "?risktype?"+ "') from dual";
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					tsql = "{ call healthyamnt2( ?#@d#?,'"  + "?insuredno?" + "','" + "?sumamnt?" + "','" + "?risktype?"+"') }";
				}
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql(tsql);
				sqlbv6.put("insuredno", mInsuredNo);
				sqlbv6.put("sumamnt", sumAmntSSRS.GetText(i, 1));
				sqlbv6.put("risktype", risktype);
				AmntSSRS = riskSql.execSQL(sqlbv6);
				// 0.跟此项无关 1.寿险 2.重疾 3. 意外医疗 4.住院医疗 5.住院补贴 6.高额医疗 7.意外伤害 8.投连
				// 9.银代意外住院 10.银代意外医疗 11. 直销防癌
				if (AmntSSRS.MaxRow > 0) {
					if("1".equals(risktype)){
						RiskAmnt1 = parseFloat(AmntSSRS.GetText(1, 1));
					}
					if("2".equals(risktype)){
						RiskAmnt2 = parseFloat(AmntSSRS.GetText(1, 1));
					}
					if("3".equals(risktype)){
						RiskAmnt3 = parseFloat(AmntSSRS.GetText(1, 1));
					}
					if("4".equals(risktype)){
						RiskAmnt4 = parseFloat(AmntSSRS.GetText(1, 1));
					}
					if("6".equals(risktype)){
						RiskAmnt6 = parseFloat(AmntSSRS.GetText(1, 1));
					}
					//tRiskAmnt = parseFloat(AmntSSRS.GetText(1, 1));
				} 
			}
		}
		logger.debug("RiskAmnt1:"+RiskAmnt1+"  RiskAmnt2:"+RiskAmnt2+" " +
				" RiskAmnt4:"+RiskAmnt4+"  RiskAmnt6:"+RiskAmnt6+"  RiskAmnt3:"+RiskAmnt3);
		RiskAmntLAndH = RiskAmnt1 + RiskAmnt2;
		String tsql_health1 = "select * from LDHealth where"
				    + " StartAge <= "+"?age?"
				    + " and EndAge >= "+"?age?";
		String tsql_health = "";
		//体检表类型
		//L 寿险 D 重疾 L1 久久寿险 D1 久久重疾
		String tItemType = "";
		//风险保额
		double tMoney = 0;
		
		LDHealthDB tLDHealthDB = new LDHealthDB();
		LDHealthSet tLDHealthSet = new LDHealthSet();
		LDHealthSchema tLDHealthSchema = new LDHealthSchema();
		if(!tFlag.equals("0"))
		{
			//投保过久久系列之外的产品,使用旧的体检表处理
			//查询是否符合寿险体检规则
			tItemType = "L";
			tMoney = RiskAmnt1;
			tsql_health = "";
			tsql_health = tsql_health1 + ""
			            + " and healthtype='"+"?healthtype?"+"' "
			            + " and StartMoney <= "+"?money?"+" "
			            + " and EndMoney >= "+"?money?"+" ";
			logger.debug("tsql_health2:"+tsql_health);
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(tsql_health);
			sqlbv7.put("age", tAge);
			sqlbv7.put("healthtype", tItemType);
			sqlbv7.put("money", tMoney);
			tLDHealthSet = tLDHealthDB.executeQuery(sqlbv7);
			if(tLDHealthSet.size()>0)
			{
				//查询有结果.查询LDHealthSub处理特殊体检项目
				//待补充
				tLDHealthSchema = tLDHealthSet.get(1);
				prepareSpecHealth(tLDHealthSchema,tItemType,tSex,tAge,tMoney );
				
			}
			else
			{
				//查询无结果,查重疾体检表
				tItemType = "D";
				tMoney = RiskAmnt2;
				tsql_health = "";
				tsql_health = tsql_health1 + ""
	            + " and healthtype='"+"?healthtype?"+"' "
	            + " and StartMoney <= "+"?money?"+" "
	            + " and EndMoney >= "+"?money?"+" ";
				logger.debug("tsql_health2:"+tsql_health);
				SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
				sqlbv8.sql(tsql_health);
				sqlbv8.put("age", tAge);
				sqlbv8.put("healthtype", tItemType);
				sqlbv8.put("money", tMoney);
				tLDHealthSet = tLDHealthDB.executeQuery(sqlbv8);
				if(tLDHealthSet.size()>0)
				{
					//查询有结果..查询LDHealthSub处理特殊体检项目
					//待补充
					tLDHealthSchema = tLDHealthSet.get(1);
					prepareSpecHealth(tLDHealthSchema,tItemType,tSex,tAge,tMoney );
				}
				else
				{
					//查询无结果.把寿险和重疾的保额加起来,
					tItemType = "L";
					tMoney = RiskAmntLAndH;
					tsql_health = "";
					tsql_health = tsql_health1 + ""
					            + " and healthtype='"+"?healthtype?"+"' "
					            + " and StartMoney <= "+"?money?"+" "
					            + " and EndMoney >= "+"?money?"+" ";
					logger.debug("tsql_health3:"+tsql_health);
					SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
					sqlbv9.sql(tsql_health);
					sqlbv9.put("age", tAge);
					sqlbv9.put("healthtype", tItemType);
					sqlbv9.put("money", tMoney);
					tLDHealthSet = tLDHealthDB.executeQuery(sqlbv9);
					if(tLDHealthSet.size()>0)
					{
						//查询有结果.查询LDHealthSub处理特殊体检项目
						//待补充
						tLDHealthSchema = tLDHealthSet.get(1);
						prepareSpecHealth(tLDHealthSchema,tItemType,tSex,tAge,tMoney );
						
					}
				}
			}
		}
		else
		{
			//只投保久久系列产品,使用新的体检表
			tItemType = "L1";
			tMoney = RiskAmnt1;
			tsql_health = "";
			tsql_health = tsql_health1 + ""
					+ " and healthtype='"+"?healthtype?"+"' "
		            + " and StartMoney <= "+"?money?"+" "
		            + " and EndMoney >= "+"?money?"+" ";
			logger.debug("tsql_health1:"+tsql_health);
			SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
			sqlbv10.sql(tsql_health);
			sqlbv10.put("age", tAge);
			sqlbv10.put("healthtype", tItemType);
			sqlbv10.put("money", tMoney);
			tLDHealthSet = tLDHealthDB.executeQuery(sqlbv10);
			if(tLDHealthSet.size()>0)
			{
				//查询有结果.查询LDHealthSub处理特殊体检项目
				//待补充
				tLDHealthSchema = tLDHealthSet.get(1);
				prepareSpecHealth(tLDHealthSchema,tItemType,tSex,tAge,tMoney );
				
			}
			else
			{
				//查询无结果,查重疾体检表
				tItemType = "D1";
				tMoney = RiskAmnt2;
				tsql_health = "";
				tsql_health = tsql_health1 + ""
						+ " and healthtype='"+"?healthtype?"+"' "
			            + " and StartMoney <= "+"?money?"+" "
			            + " and EndMoney >= "+"?money?"+" ";
				logger.debug("tsql_health2:"+tsql_health);
				SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
				sqlbv11.sql(tsql_health);
				sqlbv11.put("age", tAge);
				sqlbv11.put("healthtype", tItemType);
				sqlbv11.put("money", tMoney);
				tLDHealthSet = tLDHealthDB.executeQuery(sqlbv11);
				if(tLDHealthSet.size()>0)
				{
					//查询有结果..查询LDHealthSub处理特殊体检项目
					//待补充
					tLDHealthSchema = tLDHealthSet.get(1);
					prepareSpecHealth(tLDHealthSchema,tItemType,tSex,tAge,tMoney );
				}
				else
				{
					//查询无结果.把寿险和重疾的保额加起来,
					tItemType = "L1";
					tMoney = RiskAmntLAndH;
					tsql_health = "";
					tsql_health = tsql_health1 + ""
							+ " and healthtype='"+"?healthtype?"+"' "
				            + " and StartMoney <= "+"?money?"+" "
				            + " and EndMoney >= "+"?money?"+" ";
					logger.debug("tsql_health3:"+tsql_health);
					SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
					sqlbv12.sql(tsql_health);
					sqlbv12.put("age", tAge);
					sqlbv12.put("healthtype", tItemType);
					sqlbv12.put("money", tMoney);
					tLDHealthSet = tLDHealthDB.executeQuery(sqlbv12);
					if(tLDHealthSet.size()>0)
					{
						//查询有结果.查询LDHealthSub处理特殊体检项目
						//待补充
						tLDHealthSchema = tLDHealthSet.get(1);
						prepareSpecHealth(tLDHealthSchema,tItemType,tSex,tAge,tMoney );
						
					}
				}
			}
		}
		//判断是否查到体检,如果没有体检项目,查询是否复核医疗险
		if(tLDHealthSchema.getHealthCode()==null||tLDHealthSchema.getHealthCode().equals(""))
		{
			tItemType = "H";
			tsql_health = "";
			tMoney = RiskAmnt3;
			tsql_health = tsql_health1 + ""
					+ " and healthtype='"+"?healthtype?"+"' "
		            + " and StartMoney <= "+"?money?"+" "
		            + " and EndMoney >= "+"?money?"+" ";
			logger.debug("tsql_health3:"+tsql_health);
			SQLwithBindVariables sqlbv13 = new SQLwithBindVariables();
			sqlbv13.sql(tsql_health);
			sqlbv13.put("age", tAge);
			sqlbv13.put("healthtype", tItemType);
			sqlbv13.put("money", tMoney);
			tLDHealthSet = tLDHealthDB.executeQuery(sqlbv13);
			if(tLDHealthSet.size()>0)
			{
				//查询有结果.查询LDHealthSub处理特殊体检项目
				//待补充
				tLDHealthSchema = tLDHealthSet.get(1);
				prepareSpecHealth(tLDHealthSchema,tItemType,tSex,tAge,tMoney );
				
			}
		}
		
		mAllLDHealthSet.add(tLDHealthSchema);
		
		return true;
	}
	/**
	* 准备体检资料信息
	* 输出：如果发生错误则返回false,否则返回true
	*/
	private boolean prepareHealth()
	{
	  logger.debug("---prepareHealth  Begin---");
	  double tMoney = 0;
	  //取被保人体检额度
	  logger.debug("---prepareHealth  End---");
	  logger.debug("---mPolType:"+mPolType.toString());

	  //计算被保人体检额度
	  String tsql1;
	  tsql1 ="select PECHECK('"+"?mContNo?"+"','"+"?mInsuredNo?"+"') from dual where 1=1";
	  SQLwithBindVariables sqlbv14 = new SQLwithBindVariables();
	  sqlbv14.sql(tsql1);
	  sqlbv14.put("mContNo", this.mContNo);
	  sqlbv14.put("mInsuredNo", this.mInsuredNo);
	  String tReSult = new String();
	  ExeSQL tExeSQL=new ExeSQL();
	  tReSult =tExeSQL.getOneValue(sqlbv14);
	  if(tExeSQL.mErrors.needDealError())
	  {
		// @@错误处理
		this.mErrors.copyAllErrors(tExeSQL.mErrors);
		CError.buildErr(this,"执行SQL语句："+tsql1+"失败!");
		return false;
	  }

	  tMoney=tMoney+Double.parseDouble(tReSult.substring(0,tReSult.indexOf("|")));
          String Node1 = tReSult.substring(tReSult.indexOf("|") + 1 );
          if(Node1==null||Node1.equals("N"))
          {
              mLDHealthSet.clear();
              return true;
          }


	  //团体
	

	  String tsql2 = "";
/*

	  if(tSex.equals("0"))
	  {
		tsql2 = "select * from LDHealth where 1 = 1"
		+ " and StartAge <= "+tAge
		+ " and EndAge >= "+tAge
		+ " and StartMoney <= "+tMoney
		+ " and EndMoney >= " +tMoney
                + " and Note1 = '" +Node1
		+ "' and sex = '0'";
	  }
	  else  //如果是女性，并且检查项目中有B超项目，一并连带妇科B超检查
	  {
      String tTestSql = "select nvl(count(1),0) from LDHealth where "
                      + "  StartAge <= "+tAge
                      + " and EndAge >= "+tAge
                      + " and StartMoney <= "+tMoney
                      + " and EndMoney >= " +tMoney
                      + " and Note1 = '" +Node1 +"'"
                      + " and healthname like '%B超%'"
                      + " and sex in ('0','1')";
      tExeSQL = new ExeSQL();
      int tBCount = Integer.parseInt(tExeSQL.getOneValue(tTestSql));
      if(tBCount > 0)
      {
        tsql2 = "select * from LDHealth where"
              + " (StartAge <= "+tAge
              + " and EndAge >= "+tAge
              + " and StartMoney <= "+tMoney
              + " and EndMoney >= " +tMoney
              + " and Note1 = '" +Node1
              + "' and sex in ('0','1')) or Note1='SA'";
      }
      else
      {
        tsql2 = "select * from LDHealth where"
              + " StartAge <= "+tAge
              + " and EndAge >= "+tAge
              + " and StartMoney <= "+tMoney
              + " and EndMoney >= " +tMoney
              + " and Note1 = '" +Node1
              + "' and sex in ('0','1')";
      }
	  }
	  logger.debug(tsql2);

	  LDHealthSchema tLDHealthSchema = new LDHealthSchema();
	  LDHealthSet tLDHealthSet = new LDHealthSet();
	  LDHealthDB tLDHealthDB = new LDHealthDB();

	  mLDHealthSet.clear();
	  mLDHealthSet = tLDHealthDB.executeQuery(tsql2);
*/
	  return true;
	}

  
	/**
	* 准备需要保存的数据
	*/
	private void prepareOutputData()
	{
		mInputData.clear();
		mInputData.add( mAllLDHealthSet );
	}

        public VData getResult()
        {
          return mInputData;
        }
        
        public CErrors getErrors() {
    		// TODO Auto-generated method stub
    		return mErrors;
    	}
        
    public float parseFloat(String s) {
    	if (s.length() < 0 || s.equals("")) {
    			return 0;
    		}
    		float f1 = 0;
    		String tmp = "";
    		String s1 = "";
    		for (int i = 0; i < s.length(); i++) {
    			s1 = s.substring(i, i + 1);
    			if (s1.equals("0") || s1.equals("1") || s1.equals("2")
    					|| s1.equals("3") || s1.equals("4") || s1.equals("5")
    					|| s1.equals("6") || s1.equals("7") || s1.equals("8")
    					|| s1.equals("9") || s1.equals(".")) {
    				tmp = tmp + s1;
    			} else if (tmp.length() > 0) {
    				break;
    			}
    		}
    		f1 = Float.parseFloat(tmp);
    		return f1;
    	}
}
