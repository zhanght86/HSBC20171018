package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class ContInvaliBL {
private static Logger logger = Logger.getLogger(ContInvaliBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private int invalidNum = 0; // 成功失效险种的数目
	private String mEndDate = ""; // 旧状态结束日期
	private double mSPay = 0; // 应收总额
	private double mAPay = 0; // 暂交费
	private String mManageCom = "";
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private TransferData mTransferData = new TransferData();
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	private GlobalInput mGlobalInput = null;
	/**保全保单结算计算类 */
	BqPolBalBL tBqPolBalBL = new BqPolBalBL();

	public ContInvaliBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData)) {
			return false;
		}
		if (cOperate != null && cOperate.trim().equals("Available")) {
			
//			日志监控，性能监控
			PubFun.logPerformance (mGlobalInput,mGlobalInput.LogID[1],"续期未交失效批处理","41");
			// 调用失效批处理
			callInvaliBL();
			
			//add by jiaqiangli 2008-10-16 垫交保单宽限期外失效批处理
//			日志监控，性能监控
			PubFun.logPerformance (mGlobalInput,mGlobalInput.LogID[1]," 垫交保单宽限期外失效批处理","42");
			callPayPremOutGraceInvaliBL();
			//add by jiaqiangli 2008-10-16 垫交保单宽限期外失效批处理
			
			//add by jiaqiangli 2008-10-16 保单借款失效批处理
//			日志监控，性能监控
			PubFun.logPerformance (mGlobalInput,mGlobalInput.LogID[1],"保单借款失效批处理","43");
			callLoanInvaliBL();
			//add by jiaqiangli 2008-10-16 保单借款失效批处理			 
		} 
		else {
			mErrors.addOneError(new CError("不支持的操作字符串！"));
			return false;
		}
		return true;
	}
	
	private boolean callPayPremOutGraceInvaliBL() {
		int suc=0;
	    //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"垫交宽限期外失效批处理开始");
		//垫交宽限期外失效
	  	SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String strsql = "Select ContNo,PolNo from LCContState a"
				+ " where StateType='PayPrem' and State='1' and EndDate is null "
				//当前日期大于垫至日期
				+ " and exists (select 1 from loloan where "
				//+ " polno=a.polno and "
				//zqflag 垫交至天宽限期外 垫整期的由垫缴批处理运行
				+ " contno=a.contno and loantype='1' and payoffflag='0' and zqflag='0' and loantodate <= '?mCurrentDate?')"
				+ " and not exists(select 'X' from lcconthangupstate where posflag = '1' and contno = a.contno) "
				+ " and exists(select 'X' from lcpol where appflag = '1' and polno=mainpolno and contno = a.contno) "
				+ " and not exists ( select 'X' from LCContState where " 
				//+ " PolNo=a.PolNo and " 
				+ " ContNo=a.ContNo "
				+ " and StateType='Available' " + " and State='1' and EndDate is null)"
				//+ " and rownum <= 100 " 
				; // 不失效
        sqlbv.sql(strsql);
        sqlbv.put("mCurrentDate", mCurrentDate);
		MMap sMap = new MMap();
		SSRS tSSRS = null;
		ExeSQL tExeSQL = null;
		String aPolNo = "";
		String aContNo = "";
		logger.debug("strsql" + strsql);
		try {
			tSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv);
		} catch (Exception e) {
			// @@错误处理
			CError.buildErr(this, "查询贷款的保单状态信息时产生错误!");
			return false;
		}
		if (tSSRS != null && tSSRS.MaxRow > 0) {
			
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
								
				aContNo = tSSRS.GetText(i, 1);
				aPolNo = tSSRS.GetText(i, 2);
				SQLwithBindVariables sbv=new SQLwithBindVariables();
			    sbv.sql("select loantodate from loloan where contno='"+"?aContNo?"+"' and loantype='1' and payoffflag='0' and zqflag='0' ");
				sbv.put("aContNo", aContNo);				
				//取宽限期外的借至日期为有效的终止日期
				String tLoanToDate = tExeSQL.getOneValue(sbv);
				//loantodate 是与paytodate一样的含义 下次交费对应日
				//而这里的this.mEndDate应该是有效的止期 也就是说这个tLoanToDate是失效的startdate
				this.mEndDate = PubFun.calDate(tLoanToDate, -1, "D", null);
				
				//修改成公共的调用失效批处理
				LCPolSchema tLCPolSchema = new LCPolSchema();
                LCPolDB tLCPolDB = new LCPolDB();
                SQLwithBindVariables s=new SQLwithBindVariables();
                String tMainPolSQL = "select * from lcpol where contno='"+"?aContNo?"+"' and polno=mainpolno and appflag='1' ";
                s.sql(tMainPolSQL);
                s.put("aContNo", aContNo);
                LCPolSet tLCPolSet = tLCPolDB.executeQuery(s);
                if (tLCPolSet.size()>0) {
                    tLCPolSchema.setSchema(tLCPolSet.get(1));
                    mLCPolSchema.setSchema(tLCPolSchema);
                }
                //BqCode.BQ_InvalidationStateReason_OutGracePayPrem
                if (dealInvali(tLCPolSchema.getPolNo(),BqCode.BQ_InvalidationStateReason_OutGracePayPrem)) {
					logger.debug(tLCPolSchema.getPolNo()+ "垫缴宽限期外失效处理成功");
//					日志监控,状态监控          
	  		    	PubFun.logState(mGlobalInput,tLCPolSchema.getContNo(),"保单"+tLCPolSchema.getContNo()+"的"+tLCPolSchema.getRiskCode()+"险种垫缴宽限期外失效处理成功","1");
			    	suc++;
					invalidNum++;
				}
                
//				// 生成借款失效通知书
//				LOPRTManagerSchema tLOPRTManagerSchema = setLOPRTManager(aContNo, "42");
//				LCContStateSet tLCContStateSet = new LCContStateSet();
//				tLCContStateSet = setPayPremEndState(aContNo, aPolNo);
//
//				// 每次提交
//				sMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
//				sMap.put(tLCContStateSet, "DELETE&INSERT");
//				mResult.clear();
//				mResult.add(sMap);
//				PubSubmit tSubmit = new PubSubmit();
//				if (!tSubmit.submitData(mResult, "")) {
//					// 这里作错误处理，只记录错误信息，不返回结果
//					this.mErrors.copyAllErrors(tSubmit.mErrors);
//					CError tError = new CError();
//					tError.moduleName = "LoanEndBL";
//					tError.functionName = "dealData";
//					tError.errorMessage = "数据提交失败! 保单号为: " + aContNo;
//					this.mErrors.addOneError(tError);
//					continue;
//				}
			}
		}
//		日志监控,结果监控
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"共有"+suc+"个保单险种垫缴宽限期外失效处理成功");
	    //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"垫交宽限期外失效批处理结束");
		return true;
	}
        
	private boolean callLoanInvaliBL() {
		int suc=0;
	    //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"保单借款失效批处理开始");

        LCContStateSet mLCContStateSet = new LCContStateSet();
        MMap sMap = new MMap();
        LCContStateSet tLCContStateSet = new LCContStateSet();
        EdorCalZT tEdorCalZT = new EdorCalZT(mGlobalInput);

        // 现金价值净额(现金价值除掉贷款本息和、自垫本息和， 之后的净额)
        double tPV = 0.0;
        //现金价值
        double tCashValue = 0.0;
        //贷款、自垫 本息和
        double tLoan = 0.0;
        double tAutoPay = 0.0;
        String aPolNo = "";
        String aContNo = "";
        String tLoanDate = "";  //贷款开始日期
        String tLoanByDate = ""; //六个月后的贷款对应日
        //String tNDate = PubFun.calDate(mCurrentDate,1,"D",null); //当前计算日期的次日
        //comment by jiaqiangli 2008-10-28
        //MS计息方法 1日借2日还 算一天的利息 直接日期相减
        FDate tFDate = new FDate();
        //查询用
        SSRS tSSRS = null;
        ExeSQL tExeSQL = null;
        String strsql = "";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        //*********************************************//
        //逐日判断“次日保单的现金价值是否大于贷款、自垫本息和”,这里不用筛选时间
        strsql = "Select ContNo,PolNo from LCContState a"
         //+ " where StateType='Loan' and State='1' and EndDate is null and contno = '86210020080219001125'"
         + " where StateType='Loan' and State='1' and EndDate is null "
         //add by jiaqiangli 2009-03-06 万能险借款不再走失效而是终止批处理 万能险已做批处理 此处排除万能险险种
         + " and exists (select 1 from lcpol b where b.contno=a.contno and exists(select 1 from lmedorztduty c where b.riskcode=c.riskcode and c.paybyacc<>'1'))"
         + " and not exists(select 'X' from lcconthangupstate where posflag = '1' and contno = a.contno) "
         //add by jiaqiangli 2009-11-05 对于批处理日期大于等于责任止期的不予借款失效处理 lis-9823
         //应交未交失效与垫缴失效应该不会存在这样的问题，因为他们一定会处于“缴费期”内
         + " and exists(select 'X' from lcpol where appflag = '1' and polno=mainpolno and '" + "?mCurrentDate?" + "' < EndDate and contno = a.contno) "
         //add by jiaqiangli 2009-03-06 万能险借款不再走失效而是终止批处理 万能险已做批处理 此处排除万能险险种
         + " and not exists ( select 'X' from LCContState where "
         //+ " PolNo=a.PolNo and "
         + " ContNo=a.ContNo "
         + " and StateType='Available' "
         + " and State='1' and EndDate is null)"
         //+ " and rownum <= 100 "
         ;  //不失效
        sqlbv.sql(strsql);
        sqlbv.put("mCurrentDate", mCurrentDate);
        logger.debug("strsql" + strsql);
        try
        {
            tSSRS = new SSRS();
            tExeSQL = new ExeSQL();
            tSSRS = tExeSQL.execSQL(sqlbv);
        }
        catch (Exception e)
        {
            // @@错误处理
            CError.buildErr(this, "查询贷款的保单状态信息时产生错误!");
            return false;
        }
        if (tSSRS != null && tSSRS.MaxRow > 0)
        {
        	//有效止期为昨天，从今天开始失效
        	this.mEndDate = PubFun.calDate(this.mCurrentDate, -1, "D", null);
        	
            for (int i = 1; i <= tSSRS.MaxRow; i++)
            {
            	
              aContNo = tSSRS.GetText(i, 1);
              aPolNo = tSSRS.GetText(i, 2);
              
            //借用lpedoritem来调用getContCashValue(LPEdorItemSchema tLPEdorItemSchema)
          	LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
          	tLPEdorItemSchema.setContNo(aContNo);
          	tLPEdorItemSchema.setEdorType("");
          	tLPEdorItemSchema.setEdorAppDate(mCurrentDate);
          	tLPEdorItemSchema.setEdorValiDate(mCurrentDate);
          	tEdorCalZT.setEdorInfo(tLPEdorItemSchema);
          	//取得当前计算日期的次日的保单现金价值
              //tCashValue = tEdorCalZT.getCashValue(aPolNo, null, mCurrentDate);
          	tCashValue = tEdorCalZT.getContCashValue(tLPEdorItemSchema);
              logger.debug("tCashValue:" + tCashValue);
              
              if (tCashValue < 0) {
      			continue;
      		  }
              
              //查询贷款起期
              LOLoanDB tLOLoanDB = new LOLoanDB();
              LOLoanSet tLOLoanSet = new LOLoanSet();
              LOLoanSchema tLOLoanSchema = new LOLoanSchema();
              tLOLoanDB.setContNo(aContNo);
              //tLOLoanDB.setPolNo(aPolNo);
              tLOLoanDB.setLoanType("0");
              tLOLoanDB.setPayOffFlag("0");
              tLOLoanSet = tLOLoanDB.query();
              if (tLOLoanSet == null || tLOLoanSet.size() <= 0)
              {
                  logger.debug("日志：没有查到未清偿的贷款业务");
                  continue;
              }
              //目前业务上只允许一笔贷款，所以这里不循环了
              //一笔贷款的理解：若有多次借款会合并到一条记录存储，但是借款日期如何算？
              //上次借款的本息和与当此借款金额作加和
              tLOLoanSchema = tLOLoanSet.get(1);
              tLoanDate = tLOLoanSchema.getLoanDate();
              tLoanByDate = PubFun.calDate(tLoanDate,6,"M",null);
              //垫款借款的loloan.polno保存的是主险的polno
              aPolNo = tLOLoanSchema.getPolNo();
              //当前日期大于贷款六个月后的对应日
              //commment by jiaqiangli 2008-10-15 蒋莱确认 MS只做循环判断无时间上的判断
//              if (tFDate.getDate(tLoanByDate).compareTo(tFDate.getDate(mCurrentDate)) <= 0)
//              {
                  LCPolSchema tLCPolSchema = new LCPolSchema();
                  LCPolDB tLCPolDB = new LCPolDB();
                  tLCPolDB.setPolNo(aPolNo);
                  if (tLCPolDB.getInfo())
                  {
                      tLCPolSchema.setSchema(tLCPolDB.getSchema());
                      mLCPolSchema.setSchema(tLCPolSchema);
                  }
                  
                  tBqPolBalBL = new BqPolBalBL();
                  //取得当前计算日期的次日的保单未清偿贷款和自垫的本息和
                  if (tBqPolBalBL.calLoanCorpusAddInterest(aContNo, mCurrentDate))
                  {
                      tLoan = tBqPolBalBL.getCalResult();
                      //use as test case
                      //tLoan = tLoan*1.3;
                      logger.debug("tLoan:" + tLoan);
                  }
                  //add by jiaqiangli 2009-04-10 此处需要重新初始化类，否则有可能将借款的值传给垫缴的值
                  //正确的做法是类里头每次计算前都应该清空一次
                  tBqPolBalBL = new BqPolBalBL();
                  if (tBqPolBalBL.calAutoPayPremAddInterest(aPolNo, mCurrentDate))
                  {
                      tAutoPay = tBqPolBalBL.getCalResult();
                      logger.debug("tAutoPay:" + tAutoPay);
                  }
                  //现金价值净额(现金价值除掉贷款本息和、自垫本息和， 之后的净额)
                  tPV = tCashValue - tLoan - tAutoPay;
                  logger.debug("tCashValue:" + tCashValue);
                  logger.debug("tPV:" + tPV);
                  if (tPV < 0)
                  {
                	  //add by jiaqiangli 2009-03-06 调用公共的失效批处理程序 StateReason=BqCode.BQ_InvalidationStateReason_Loan
                	  if (dealInvali(tLCPolSchema.getPolNo(),BqCode.BQ_InvalidationStateReason_Loan)) {
  						logger.debug(tLCPolSchema.getPolNo()+ "借款失效处理成功");
//  						日志监控,状态监控          
  		  		    	PubFun.logState(mGlobalInput,tLCPolSchema.getContNo(),"保单"+tLCPolSchema.getContNo()+"的"+tLCPolSchema.getRiskCode()+"险种借款失效处理成功","1");
  				    	suc++;
  						invalidNum++;
  					}
//                      //生成借款失效通知书
//                      LOPRTManagerSchema tLOPRTManagerSchema = setLOPRTManager(aContNo,"42");
//                      tLCContStateSet = new LCContStateSet();
//                      tLCContStateSet = setLoanEndState(tLCPolSchema);
//                      mLCContStateSet.add(tLCContStateSet);
//
//                      //每次提交
//                      sMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
//                      sMap.put(mLCContStateSet, "DELETE&INSERT");
//                      mResult.clear();
//                      mResult.add(sMap);
//                      PubSubmit tSubmit = new PubSubmit();
//                      if (!tSubmit.submitData(mResult, ""))
//                      {
//                          //这里作错误处理，只记录错误信息，不返回结果
//                          this.mErrors.copyAllErrors(tSubmit.mErrors);
//                          CError tError = new CError();
//                          tError.moduleName = "LoanEndBL";
//                          tError.functionName = "dealData";
//                          tError.errorMessage = "数据提交失败! 保单号为: "+aContNo;
//                          this.mErrors.addOneError(tError);
//                          continue;
//                      }
                  }
//              }
          }
      }
//		日志监控,结果监控
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"共有"+suc+"个保单险种借款失效处理成功");
	    //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"保单借款失效批处理结束");
		return true;
	}
	
	private boolean callInvaliBL() {
		logger.debug("失效批处理运行开始。。。。");
	    //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"失效批处理开始");
		String subSql = (mManageCom != null && !mManageCom.trim().equals("")) ? (" and substr(ManageCom,1,4) in("
				+ "?mManageCom?" + ") ")
				: " ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String strSql = "select * from lcpol a where 1 = 1  "
			//comment by jiaqiangli 2008-10-17 MS的失效处理支持到垫交日后失效
				+ " and not exists(select 'X' from lccontstate b where a.contno=b.contno and b.statetype in ('Available','PH') and b.state = '1' and  b.enddate is null and b.polno = a.polno ) "
				+ " and payintv <> 0 and payintv <> -1 "
//				+ // 115险种不参与失效处理
//				" and exists(select 'X' from ljspayperson where polno = a.polno "
//				+ subSql
//				+ " ) "
				//add by jiaqiangli 2009-03-20 失效取数逻辑只取个单
				+ " and conttype = '1' and appflag = '1'  "
				+ " and polno = mainpolno  "
				//add by jiaqiang 2009-11-12 由于这里是非垫缴保单的应交未交失效，将垫缴的排除之外而不是通过批处理的先后执行顺序来控制
				//也就是说让主险垫缴(包含lcpol层与产品定义层)的走垫缴的那个自动运行批处理 否则lccontstate.statereason不对应
				//+ " and not exists (select 1 from lcpol b where autopayflag='1' and exists (select 1 from LMRiskApp where RiskCode=b.RiskCode and AutoPayFlag='1') and polno=a.polno) "
				+ " and (case (select autopayflag from LMRiskApp where RiskCode = a.RiskCode) when '1' then (case when (select autopayflag from lccont where contno = a.contno) is not null then (select autopayflag from lccont where contno = a.contno) else '0' end) else '0' end) <> '1' "
				//addd by jiaqiangli 2009-03-06 加上and paytodate<payenddate
				+ " and paytodate<payenddate and paytodate < '"
				+ "?mCurrentDate?"
				+ "' "
				+ subSql
				//add by jiaqiangli 2009-03-06 上面的逻辑里以主险进行判断 长期性附加险失效，短期附加险终止
				
//				+ " union "
//				+ " select * from lcpol c where 1 = 1 "
//				+ " and polno <> mainpolno "
//				+ " and exists(select 'X' from lcpol where polno = mainpolno and payintv = '0' and appflag = '1' and polno = c.mainpolno "
//				+ subSql
//				+ " ) "
////				+ " and exists(select 'X' from ljspayperson where polno = c.polno "
////				+ subSql
////				+ " ) "
//				+ " and appflag = '1' "
//				//addd by jiaqiangli 2009-03-06 加上and paytodate<payenddate
//				+ " and paytodate<payenddate and paytodate < '"
//				+ mCurrentDate + "' " + subSql
				//+ "and contno = '86130020060210053846' and rownum <= 100 "
				;
        sqlbv.sql(strSql);
        sqlbv.put("mManageCom", mManageCom);
        sqlbv.put("mCurrentDate", mCurrentDate);
		LCPolSet tLCPolSet = new LCPolSet();
		RSWrapper rsWrapper = new RSWrapper();
		// 传入结果集合,SQL
		if (!rsWrapper.prepareData(tLCPolSet, sqlbv)) {
			this.mErrors.copyAllErrors(rsWrapper.mErrors);
			logger.debug(rsWrapper.mErrors.getFirstError());
			return false;
		}
		do {
			rsWrapper.getData();
			if (tLCPolSet != null && tLCPolSet.size() > 0) {
				for (int i = 1; i <= tLCPolSet.size(); i++) {
					// 逐条调用保单失效处理
					// true 作累加 false 作conitue
					if (dealInvali(tLCPolSet.get(i).getPolNo(),BqCode.BQ_InvalidationStateReason_Nomal)) {
						logger.debug(tLCPolSet.get(i).getPolNo()
								+ "失效处理成功");
//						日志监控,状态监控          
		  		    	PubFun.logState(mGlobalInput,tLCPolSet.get(i).getContNo(),"保单"+tLCPolSet.get(i).getContNo()+"的"+tLCPolSet.get(i).getRiskCode()+"险种失效处理成功","1");
						invalidNum++;
					}
				}
			}
		} while (tLCPolSet != null && tLCPolSet.size() > 0);

		rsWrapper.close();

		logger.debug("共计 " + invalidNum + " 个保单险种失效");
//		日志监控,结果监控
		PubFun.logResult (mGlobalInput,mGlobalInput.LogID[1],"共有"+invalidNum+"个保单险种失效处理成功");
		logger.debug("失效批处理运行结束。。。。");
	    //日志监控,过程监控        
	  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"失效批处理结束");
		return true;
	}

	/** **************************失效批处理业务处理区域*************************** */
	// 对于某些附加险不进行终止而进行失效
	private boolean isSubRisk(String mRiskCode) {
		// 对于长期附加险不终止，而是失效
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String strsql = " select riskcode from  lmriskapp where RiskPeriod = 'L' "
				+ " and SubRiskFlag = 'S' and riskcode = '" + "?mRiskCode?" + "'";
		sqlbv.sql(strsql);
		sqlbv.put("mRiskCode", mRiskCode);
		ExeSQL q_exesql = new ExeSQL();
		SSRS tssrs = new SSRS();
		tssrs = q_exesql.execSQL(sqlbv);
		if (tssrs != null && tssrs.getMaxRow() >= 1) {
			return true;
		}
//		// 不参与终止处理的附加险种数组,在程序中写死
//		String[] riskArray = { "279" };
//		// 如果险种编码大于5位
//		if (mRiskCode.length() >= 5) {
//			String midThree = mRiskCode.trim().substring(2, 5); // 取出第3-5位
//
//			for (int i = 0; i < riskArray.length; i++) {
//				if (midThree.equals(riskArray[i])) {
//					return true;
//				}
//			}
//		}
//		// 如果是3位的险种编码
//		for (int i = 0; i < riskArray.length; i++) {
//			if (mRiskCode.equals(riskArray[i])) {
//				return true;
//			}
//		}
		return false;
	}

	// 判断保单是否满足失效条件
	private boolean canInvali(String sPolNo,String sStateReason) {
		// 判断是否处于保全挂起状态
		if (!checkPosHangUp(sPolNo, mLCPolSchema.getContNo())) {
			return false;
		}
		// 判断是否已经失效或处于自垫约定
		// if (isOnState(sPolNo, " 'Available', 'PayPrem' "))
		// {
		// return false;
		// }
		// 判断是超过宽限期 只有应交未交失效才校验是否超过宽末
		if (BqCode.BQ_InvalidationStateReason_Nomal.equals(sStateReason)) {
			if (!checkLapseDate(sPolNo)) {
				return false;
			}
			// 校验银行在途和暂交费信息
			if (!checkTempFee(sPolNo)) {
				return false;
			}
			// 校验豁免信息
			if (!checkFreeFlag(sPolNo)) {
				return false;
			}
		}
		return true;
	}

	// 终止tLCPolSchema的附加险，终止时间为tEndDate
	// 对于某些附加险不终止，而是失效
	private boolean terminateSubRisk(LCPolSchema aLCPolSchema, String tEndDate,String tStateReason) {
		LCPolSet subLCPolSet = null;
		String sPolNo = aLCPolSchema.getPolNo();
		if (aLCPolSchema.getPolNo().equals(aLCPolSchema.getMainPolNo())) {
			subLCPolSet = querySubRisk(sPolNo);
		} 
		else {
			subLCPolSet = new LCPolSet();
			subLCPolSet.add(aLCPolSchema);
		}

		if (subLCPolSet != null && subLCPolSet.size() >= 1) {
			LCPolSchema subCPolSchema;
			LCPolSet tTerSubLCPolSet = new LCPolSet();
			boolean tHasSubTerminate = false;
			String tListString = "";//终止的险种代码
			LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
			for (int k = 1; k <= subLCPolSet.size(); k++) {
				subCPolSchema = subLCPolSet.get(k);
				if (isSubRisk(subCPolSchema.getRiskCode())) {
					if (!isOnState(sPolNo, "'Available'")) {
						//comment by jiaqiangli 2008-10-15 加上失效原因编码规则
						//changeState(subCPolSchema, tEndDate, "Available", "1",BqCode.BQ_InvalidationStateReason_Nomal);
						// modify by jiaqiangli 2009-04-03 此处要处理三种失效
						changeState(subCPolSchema, tEndDate, "Available", "1",tStateReason);
					}
				} 
				else {
					// 主险失效，附加险终止
					if (!isOnState(sPolNo, "'Terminate'")) {
						//comment by jiaqiangli 从paytodate开始终止更合理
						// modify by jiaqiangli 2009-04-18 此处对借款失效可能出现 tEndDate < paytodate -1 需做比较取小者
						String tTmpEndDate = PubFun.calDate(subCPolSchema.getPaytoDate(),-1, "D", null); // 附加险失效终止状态起始日期为保单交费对应日
						if (tEndDate.compareTo(tTmpEndDate) < 0)
							//tEndDate = subCPolSchema.getPaytoDate();//modify by jiaqiangli 2009-03-06 程序统一处理
							changeState(subCPolSchema, tEndDate, "Terminate", "1","07");
						else 
							changeState(subCPolSchema, tTmpEndDate, "Terminate", "1","07");
					}
					subCPolSchema.setAppFlag("4");
					subCPolSchema.setExpiryFlag("1");
					subCPolSchema.setModifyDate(mCurrentDate);
					subCPolSchema.setModifyTime(mCurrentTime);
					tTerSubLCPolSet.add(subCPolSchema);
					//add by jiaqiangli 这里会有问题的?? subLCPolSet.size() >= 2时
					//mMap.put(subCPolSchema, "UPDATE");
					tLMRiskAppDB.setRiskCode(subCPolSchema.getRiskCode());
					tLMRiskAppDB.getInfo();
					if (tHasSubTerminate == true) {
						tListString += "、"+tLMRiskAppDB.getRiskName();
					}
					else if (tHasSubTerminate == false) {
						tListString += tLMRiskAppDB.getRiskName();
					}
					if (tHasSubTerminate == false) tHasSubTerminate = true;
				}
			}
			mMap.put(tTerSubLCPolSet, "UPDATE");
			//update loprtmanager 将此次终止的附加险拼串至remark中以方便通知书打印
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql("update loprtmanager set remark='"+"?tListString?"+"' where otherno='"+"?otherno?"+"' and othernotype='"+"?othernotype?"+"' and code='"+"?code?"+"' ");
			sqlbv.put("tListString", tListString);
			sqlbv.put("otherno", aLCPolSchema.getContNo());
			sqlbv.put("othernotype", PrintManagerBL.ONT_CONT);
			sqlbv.put("code", PrintManagerBL.CODE_PEdorContInvalid);
			if (tHasSubTerminate == true) 
			    mMap.put(sqlbv, "UPDATE");
		}
		return true;
	}

	// 发失效通知书
	private boolean sendInvaliNotice(LCPolSchema tLCPolSchema, String tCom,
			String tCode,String tInvaReason) {
		String tStartDate = PubFun.calDate(mEndDate, 1, "D", null);
		String tLimit = PubFun.getNoLimit(tCom);
		String serNo = PubFun1.CreateMaxNo("PRTSEQNO", tLimit);
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(serNo);
		tLOPRTManagerSchema.setOtherNo(tLCPolSchema.getContNo());
		tLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_CONT); // 个人保单号
		tLOPRTManagerSchema.setCode(tCode); // 42为失效通知书的代码
		tLOPRTManagerSchema.setManageCom(tLCPolSchema.getManageCom());
		tLOPRTManagerSchema.setAgentCode(tLCPolSchema.getAgentCode());
		tLOPRTManagerSchema.setReqCom(tCom);
		tLOPRTManagerSchema.setReqOperator("000");
		tLOPRTManagerSchema.setPrtType("0"); // 前台打印
		tLOPRTManagerSchema.setStateFlag("0"); // 提交打印
		tLOPRTManagerSchema.setMakeDate(mCurrentDate);
		tLOPRTManagerSchema.setMakeTime(mCurrentTime);
		tLOPRTManagerSchema.setStandbyFlag1(tStartDate); // 失效日期
		tLOPRTManagerSchema.setStandbyFlag2(String.valueOf(mSPay)); // 应收
		tLOPRTManagerSchema.setStandbyFlag3(String.valueOf(mAPay)); // 实收
		tLOPRTManagerSchema.setStandbyFlag4(tInvaReason);
		mMap.put(tLOPRTManagerSchema, "DELETE&INSERT");
		return true;
	}
	
	/** *************************工具函数区域******************************** */

	// 判断保单是否处于某种状态
	private boolean isOnState(String sPolNo, String sStateType) {
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String sql = " select count(*) from lccontstate where statetype in ( "
				+ "?sStateType?" + " ) and state = '1' and ((startdate <= '"
				+ "?mCurrentDate?" + "' and '" + "?mCurrentDate?" + "' <= enddate) or "
				+ " (startdate <= '" + "?mCurrentDate?"
				+ "' and enddate is null)) " + " and polno = '" + "?sPolNo?" + "'";
		sqlbv.sql(sql);
		sqlbv.put("sStateType", sStateType);
		sqlbv.put("mCurrentDate", mCurrentDate);
		sqlbv.put("sPolNo", sPolNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sCount = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			// logger.debug(sPolNo + "保单状态查询失败");
			return false;
		}
		if (sCount != null && sCount.equals("1")) {
			// logger.debug(sPolNo + "保单已经失效或处于自垫约定");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 以后对LCContState表的操作，都是把用contno或polno跟statetype确定的旧状态结束，然后插入
	 * insuredno为'000000'的新记录
	 * 改变险种状态，tEndDate为旧有状态结束日期，tStateType为状态类型，tState为新的状态:"0"或"1"
	 * 
	 * @param: tLCPolSchema tEndDate tStateType tState tStateReason 输入的数据
	 * @return: boolean
	 */
	private boolean changeState(LCPolSchema tLCPolSchema, String tEndDate,
			String tStateType, String tState, String tStateReason) {
		if (tLCPolSchema == null || tEndDate == null || tStateType == null
				|| tState == null || tEndDate.trim().equals("")
				|| tStateType.trim().equals("") || tState.trim().equals("")) {
			return false;
		}
		// 新状态起始日期为旧状态结束日期次日
		String tStartDate = PubFun.calDate(tEndDate, 1, "D", null);

		LCContStateDB tLCContStateDB = new LCContStateDB();
		LCContStateSet tLCContStateSet = new LCContStateSet();
		LCContStateSchema oldLCContStateSchema = new LCContStateSchema();
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String strSql = " select * from lccontstate where statetype = '"
				+ "?tStateType?" + "' " + " and enddate is null and contno = '"
				+ "?contno?" + "' and polno = '"
				+ "?polno?" + "'";
		sqlbv.sql(strSql);
		sqlbv.put("tStateType", tStateType);
		sqlbv.put("contno", tLCPolSchema.getContNo());
		sqlbv.put("polno", tLCPolSchema.getPolNo());
		tLCContStateSet = tLCContStateDB.executeQuery(sqlbv);
		if (!tLCContStateDB.mErrors.needDealError()) {
			if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
				for (int i = 1; i <= tLCContStateSet.size(); i++) {
					oldLCContStateSchema = tLCContStateSet.get(i).getSchema();
					oldLCContStateSchema.setEndDate(tEndDate);
					//modify by jiaqiangli 2009-05-14 对新状态加上原因
					//oldLCContStateSchema.setStateReason(tStateReason);
					oldLCContStateSchema.setModifyDate(mCurrentDate);
					oldLCContStateSchema.setModifyTime(mCurrentTime);
					mMap.put(oldLCContStateSchema, "DELETE&INSERT");
				}
			}
		} else {
			logger.debug("保单状态查询失败");
			return false;
		}
		LCContStateSchema tLCContStateSchema = new LCContStateSchema();
		tLCContStateSchema.setContNo(tLCPolSchema.getContNo());
		tLCContStateSchema.setInsuredNo("000000");
		//comment by jiaqiangli 2009-03-06 失效，终止状态下的lccontstate.polno=lcpol.polno而非'000000'
		tLCContStateSchema.setPolNo(tLCPolSchema.getPolNo());
		tLCContStateSchema.setStateType(tStateType);
		tLCContStateSchema.setStateReason(tStateReason);
		tLCContStateSchema.setState(tState);
		tLCContStateSchema.setOperator("000");
		tLCContStateSchema.setStartDate(tStartDate);
		tLCContStateSchema.setMakeDate(mCurrentDate);
		tLCContStateSchema.setMakeTime(mCurrentTime);
		tLCContStateSchema.setModifyDate(mCurrentDate);
		tLCContStateSchema.setModifyTime(mCurrentTime);
		mMap.put(tLCContStateSchema, "DELETE&INSERT");
		return true;
	}

	// 查询主险下附加险
	private LCPolSet querySubRisk(String mainPolNo) {
		LCPolSet tLCPolSet = new LCPolSet();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String strSql = "select * from lcpol where mainpolno = '" + "?mainPolNo?"
				+ "' and polno <> mainpolno and appflag = '1'";
		sqlbv.sql(strSql);
		sqlbv.put("mainPolNo", mainPolNo);
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolSet = tLCPolDB.executeQuery(sqlbv);
		return tLCPolSet;
	}

	// 判断是否处于保全挂起状态
	private boolean checkPosHangUp(String sPolNo, String sContNo) {
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String sql = " select count(1) from lcconthangupstate where posflag = '1' "
				+ " and contno = '" + "?sContNo?" + "' ";
		sqlbv.sql(sql);
		sqlbv.put("sContNo", sContNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sCount = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			// logger.debug(sPolNo + "保全挂起状态查询失败");
			return false;
		}
		if (sCount != null && sCount.equals("0")) {
			// 没有被保全挂起
			return true;
		} else {
			logger.debug(sPolNo + "保全挂起");
			return false;
		}
	}

	// 判断是超过宽限期
	private boolean checkLapseDate(String sPolNo) {
		FDate tFDate = new FDate();
		String tPayToDate = mLCPolSchema.getPaytoDate();
		String tLapseDate = EdorCalZT.CalLapseDateNew(mLCPolSchema,mLCPolSchema.getRiskCode(),
				tPayToDate);
		if (tLapseDate == null || tLapseDate.trim().equals("")) {
			// logger.debug(sPolNo + "计算宽限期止期失败");
			return false;
		}
		if (!tFDate.getDate(mCurrentDate).after(tFDate.getDate(tLapseDate))) {
			// logger.debug(sPolNo + "未到宽限期止期");
			return false;
		}

		//comment by jiaqiangli 2009-03-10
		//注意此处的判断逻辑 paytodate+60+7 = tLapseDate mCurrentDate>tLapseDate
		//也就是说这个tLapseDate是有效的止期，paytodate+60+8<=Currentdate为失效的起期startdate
		mEndDate = tLapseDate;
		return true;
	}

	// 校验银行在途和暂交费信息
	private boolean checkTempFee(String sPolNo) {
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String strSql = " select bankonthewayflag, getnoticeno from ljspay "
				+ " where otherno = '" + "?otherno?"
				+ "' and othernotype in('2','3') ";
		sqlbv.sql(strSql);
		sqlbv.put("otherno", mLCPolSchema.getContNo());
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tssrs = tExeSQL.execSQL(sqlbv);
//		if (tssrs == null || tssrs.getMaxRow() < 1) {
//			return false;
//		}
		// add by jiaqiangli 2009-04-08 有应收才去判断
		if (tssrs != null && tssrs.getMaxRow() >= 1) {
			String tBankFlag = tssrs.GetText(1, 1);
			String tNoticeNo = tssrs.GetText(1, 2);
			// mSPay = Double.parseDouble(tssrs.GetText(1, 3));
			if (tBankFlag != null && !tBankFlag.trim().equals("") && tBankFlag.trim().equals("1")) {
				logger.debug(sPolNo + "处于银行划款期间");
				return false;
			}
			// 如果已有暂交费，不用判断其金额
			strSql = "select paymoney from ljtempfee " + " where tempfeeno = '" + "?tNoticeNo?" + "' "
					+ " and tempfeetype = '2' ";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSql);
			sqlbv.put("tNoticeNo", tNoticeNo);
			tssrs = tExeSQL.execSQL(sqlbv);
			if (tssrs != null && tssrs.getMaxRow() > 0) {
				// mAPay = Double.parseDouble(tssrs.GetText(1, 1));
				// if (mAPay >= mSPay)
				// {
				// logger.debug(sPolNo + "已有足够暂交费");
				return false;
				// }
			}
		}
		return true;
	}

	// 校验豁免信息
	private boolean checkFreeFlag(String sPolNo) {
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String strSql = " select freeflag from lcprem "
				+ " where polno = '"
				+ "?sPolNo?"
				+ "' and paytodate between freestartdate and freeenddate and freeflag = '1' ";
		sqlbv.sql(strSql);
		sqlbv.put("sPolNo", sPolNo);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tssrs = tExeSQL.execSQL(sqlbv);
		if (tssrs != null && tssrs.getMaxRow() >= 1) {
			// 如果有有效豁免，则不予失效
			return false;
		}
		return true;
	}

	/** ******************************************************************** */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate(String cOperate) {
		return this.mOperate;
	}

	public VData getResult() {
		return mResult;
	}

	/** ******************************************************************** */

	/**
	 * 保单失效处理
	 * 
	 * @param sPolNo
	 * @return boolean
	 */
	public boolean dealInvali(String sPolNo,String sStateReason) {
		mInputData = new VData();
		mMap = new MMap();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		String sql = " select * from lcpol where polno = '" + "?sPolNo?" + "' ";
		sqlbv.sql(sql);
		sqlbv.put("sPolNo", sPolNo);
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv);
		if (tLCPolDB.mErrors.needDealError()) {
			return false;
		}
		mLCPolSchema = tLCPolSet.get(1);
		ExeSQL tExeSQL = new ExeSQL();
		String TL_flag="";
		if (mLCPolSchema.getPolNo().equals(mLCPolSchema.getMainPolNo())) { // 如果是主险，进行失效处理
			//tongmeng 2011-01-19 modify
			//按照lmriskpay描述的设置后续状态
			String tAfterState = "";
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			String tSQL_afterState = " select overduedeal from lmriskpay where riskcode='"+"?riskcode?"+"' ";
			sbv.sql(tSQL_afterState);
			sbv.put("riskcode", mLCPolSchema.getRiskCode());
			tAfterState = tExeSQL.getOneValue(sbv);
			if(tAfterState==null||tAfterState.equals(""))
			{
				tAfterState = "L";
			}
			
			/*
			 L: 失效（Lapse）
			 H：缓缴（Premiem Holiday）
			 P: 缴清（Paid-up）
			 R: 减额缴清（RPU）
			 E: 展期（ETI）
			 N: 不失效（Non-Lapse）

			 */
			
			if(tAfterState.equals("L"))
			{
				if (!canInvali(sPolNo,sStateReason)) {
					return false;
				}
				
				//modify by jiaqiangli 2008-10-15 状态原因
				//非垫交保当应交未交失效
				if (!changeState(mLCPolSchema, mEndDate, "Available", "1", sStateReason)) {
					return false;
				}
				// 发失效通知书
				sendInvaliNotice(mLCPolSchema, "86", PrintManagerBL.CODE_PEdorContInvalid,sStateReason);
				
				// 主险失效，附加险终止 长期性附加险也失效
				terminateSubRisk(mLCPolSchema, mEndDate,sStateReason);
			}
			else if(tAfterState.equals("H"))
			{
				if (!canInvali(sPolNo,sStateReason)) {
					return false;
				}
				if (!changeState(mLCPolSchema, mEndDate, "PH", "1", "01")) {
					return false;
				}	
			}
			else if(tAfterState.equals("R")||tAfterState.equals("E"))
			{
				//预留,具体处理状态稍后补充
			}
			else if(tAfterState.equals("P"))
			{
				if (!canInvali(sPolNo,sStateReason)) {
					return false;
				}
				//预留,缴清的具体处理逻辑待讨论 
				if (!changeState(mLCPolSchema, mEndDate, "Paidup", "1", "01")) {
					return false;
				}	
				
			}
			else if(tAfterState.equals("N"))
			{
				//return true;
			}
			
		
		}
		// 主险失效，附加险终止 长期性附加险也失效
		//terminateSubRisk(mLCPolSchema, mEndDate,sStateReason);

		if (!SubmitMap()) {
			return false;
		}
		return true;
	}

	private boolean SubmitMap() {
		PubSubmit tPubSubmit = new PubSubmit();
		mInputData.add(mMap);
		if (!tPubSubmit.submitData(mInputData, "")) {
			logger.debug(mLCPolSchema.getPolNo() + "提交数据失败！");
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData != null) {
			mManageCom = (String) mTransferData.getValueByName("ManageCom");
		}
		mGlobalInput=(GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);	    
		//mGlobalInput = new GlobalInput();
        mGlobalInput.Operator = "000"; // 系统自动操作
        //mGlobalInput.ManageCom = "86";
        mGlobalInput.ManageCom = mManageCom;
		return true;
	}
    /* 校验该产品类型 投连Or万能
     * @param String cPolNo
     * @param String cDutyCode
     * @param String cPayPlanCode
     */
    public String CheckRiskType(String cRiskCode)
    {

    	String RiskType ="";
        LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();

        mLMRiskAppDB.setRiskCode(cRiskCode);

        if (!mLMRiskAppDB.getInfo())
        {
            CError.buildErr(this, "查询险种信息失败");
            RiskType = "Unknow";
        }
        /*--投连--*/
        if (mLMRiskAppDB.getRiskType3().equals("3"))
        {
            RiskType = "TL";
        }
        /*--万能--*/
        else if (mLMRiskAppDB.getRiskType3().equals("4"))
        {
            RiskType = "WN";
        }

        else
        {
            RiskType = "other";

        }
        return RiskType;
    }
	public static void main(String[] args) {
		logger.debug("start ContInvaliBL");
		String tManageCom = "8611";

		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", tManageCom);
		tVData.add(tTransferData);

		ContInvaliBL tContInvaliBL = new ContInvaliBL();

		logger.debug("Start ContInvaliBL");
		if (!tContInvaliBL.submitData(tVData, "Available")) {
			logger.debug(tContInvaliBL.mErrors.getError(0).errorMessage);
		}

		logger.debug("end ContInvaliBL");
	}
}
