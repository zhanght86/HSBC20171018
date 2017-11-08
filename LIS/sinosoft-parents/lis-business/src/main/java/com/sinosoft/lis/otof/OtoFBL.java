/*
 * <p>ClassName: OtoFBL </p>
 * <p>Description: OtoFBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：
 */
package com.sinosoft.lis.otof;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.LJAGetDrawDBSet;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

import java.text.*;
import java.util.*;

public class OtoFBL
{
private static Logger logger = Logger.getLogger(OtoFBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 数据操作字符串 */
	private String mOperate;

	private String mStartDate = "";
	private String mEndDate = "";
	private String mToday = "";
	private int mTime = 0;
	private String DateFlag = "0";
	private String mInputDate = ""; // 界面传入的记帐日期
	private GlobalInput mGlobalInput = new GlobalInput();
	private String cManageCom = ""; // 提取管理机构

	private String tmoney = ""; // >金额</money>
	private String mBillID = ""; // 对应凭证科目，便于查找 == 凭证类别+凭证分类(一张凭证内部有多个分类的情况)+内部编码
	private String mReversedStatus = ""; // 冲消状态
	private String mOrigRowID = ""; // 被冲消的行
	private String mReversedRowID = ""; // 冲消生成的行
	private String mCurrencyCode = ""; // 币别
	private String mVoucherType = ""; // 凭证类别
	private String mManageCom = ""; // 核算单位代码
	private String mSegment2 = ""; // 成本中心
	private String mAccountCode = ""; // 科目代码
	private String mAccountSubCode = ""; // 科目明细代码
	private String mSaleChnl = ""; // 销售渠道
	private String mRiskCode = ""; // 保险产品代码
	private String mTransDate = ""; // 事务日期
	private String mAccountingDate = ""; // 记帐日期
	private int mMatchID = 0; // 借贷关系key值
	private String mBatchNo = ""; // 批次号
	private String mEnteredDR = ""; // 事务借计金额
	private String mEnteredCR = ""; // 事务贷计金额
	private String mHeadDescription = ""; // 日记帐摘要
	private String mLineDescription = ""; // 行记帐摘要
	private String mPolNo = ""; // 保单号
	private String mContNo = ""; // 合同号
	private String mInsuredName = ""; // 被保险人姓名
	private String mEndYear = ""; //保险期间
	private String mBranchType = ""; //销售渠道
	private String mAgentName = ""; //代理人姓名
	private String mBankCode = ""; //银行编码
	private String mAgentCom = ""; //代理机构
 	private String mBussNo = ""; // 收付款单据号
	private String mAttribute5 = ""; // 行为明细类别
	private String mAttribute6 = ""; // 代理机构（航意险应收凭证）
	private int mPayIntv = 0; // 保费交费间隔
	private Date dbdate;
	private Date dedate;
    double tYHSLMoney = 0.0;//印花税金额
    private HashSet mLITranInfoSetHashtable=new HashSet();

	// 对表的定义
	private OFInterfaceSet mOFInterfaceSet = new OFInterfaceSet();
	private LITranInfoSet mLITranInfoSet = new LITranInfoSet();
	private LITranErrSet mLITranErrSet = new LITranErrSet();
	private LIVertifySet mLIVertifySet = new LIVertifySet();         //科目约束性
	private OtoFLockBL tOtoFLockBL = new OtoFLockBL();
	private String tRecordNo;     //进行锁表的管理机构
	private OFinaLogSchema tFinaLogSchema = new OFinaLogSchema();//提取日志表

	public OtoFBL()
	{
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		logger.debug("--- OtoFBL begin ---");
		mInputData = (VData)cInputData.clone();
		this.mOperate = cOperate;

		try
		{
			if(!cOperate.equals("Buss"))
			{
				CError.buildErr(this, "不支持的业务凭证提取操作字符串");
				return false;
			}

			initVar(); // 避免getinputdata中的数据被冲掉
			// 得到外部传入的数据，将数据备份到本类中
			if(!getInputData(cInputData))
			{
				return false;
			}
			
			if(!checkData())
			{
				CError.buildErr(this, "校验数据失败！");
				return false;
			}
			while(dbdate.compareTo(dedate) <= 0)
			{
				try
				{
					FDate chgdate = new FDate();
					mToday = chgdate.getString(dbdate);
					Date mDate = chgdate.getDate(PubFun.getCurrentDate());

			        tRecordNo=cManageCom+new SimpleDateFormat("yyyyMMdd").format(dbdate)
			        			+new SimpleDateFormat("yyyyMMdd").format(mDate)+String.valueOf(mTime);	        
			    	logger.debug("tRecordNo---------"+tRecordNo);
			    	//调用并发控制程序
			    	tFinaLogSchema = new OFinaLogSchema();
			    	if(!tOtoFLockBL.lock(tRecordNo, cManageCom, String.valueOf(mTime),mToday,mGlobalInput))
			    	{
			    		CError.buildErr(this,  "自动提取加锁或者解锁失败");
			            return false;
			    	}
			    	else
			    	{   	  	 	
				        //针对自动运行的并发控制，且一天的一中类型的凭证的批次号一致
//				        if("1".equals(DateFlag))
//				        {
			    		//手工界面提取的规则与自动运行提取一致，即提取日期一致、操作日期一致、凭证类型一致的批次号则一致
		        		OFinaLogDB tFinaLogDB = new OFinaLogDB();
		        		tFinaLogDB.setRecordNo(tRecordNo);
		                tFinaLogSchema =tFinaLogDB.query().get(1);
		        		mBatchNo = tFinaLogSchema.getBatchNo(); 
		        		mMatchID = (int)tFinaLogSchema.getMatchID();//一个批次下mMatchID不重复
	
//				        }
//				        else
//				           mBatchNo = PubFun1.CreateMaxNo("OTOF","20");
			    	}
			    	
					// 准备所有要打印的数据
					if(!getVoucherData(mTime))
					{
						CError.buildErr(this, "凭证提取错误!");
						return false;
					}

			    	tFinaLogSchema.setMatchID(mMatchID);
					prepareOutputData();
					PubSubmit ps = new PubSubmit();
					if(!ps.submitData(mInputData))
					{
						mErrors.copyAllErrors(ps.mErrors);
						CError.buildErr(this, "提交数据库失败!");
						return false;
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					CError.buildErr(this, ex.toString());
					return false;
				}
				finally
				{
	                logger.debug("unlock");
	                tOtoFLockBL.unLock(tRecordNo);
				}
				dbdate = PubFun.calDate(dbdate, 1, "D", null);
			}


		}

		catch(Exception ex)
		{
			ex.printStackTrace();
			CError.buildErr(this, ex.toString());
			return false;
		}
		return true;
	}

	
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0));
		TransferData mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
		mStartDate = (String)mTransferData.getValueByName("mStartDate");
		mEndDate = (String)mTransferData.getValueByName("mEndDate");
		Integer itemp = (Integer)mTransferData.getValueByName("itemp");
		mTime = itemp.intValue();
		DateFlag = (String)mTransferData.getValueByName("DateFlag");
		mInputDate = (String)mTransferData.getValueByName("mInputDate"); // 记账日期
		cManageCom = (String)mTransferData.getValueByName("cManageCom"); // 提取管理机构

		logger.debug("凭证编码：" + mTime);

		if(mStartDate.equals(""))
		{
			CError.buildErr(this, "没有起始日期!");
			return false;
		}

		if(mEndDate.equals(""))
		{
			CError.buildErr(this, "没有终止日期!");
			return false;
		}

		if(mStartDate.length() != 10 || mEndDate.length() != 10)
		{
			CError.buildErr(this, "请检查本地机器的时间格式设置，请采用YYYY-MM-DD格式!");
			return false;
		}

		if(mGlobalInput == null)
		{
			CError.buildErr(this, "没有得到足够的信息!");
			return false;
		}
		return true;
	}
	
	
	private boolean checkData()
	{
		FDate chgdate = new FDate();
		dbdate = chgdate.getDate(mStartDate);
		dedate = chgdate.getDate(mEndDate);
		mLITranErrSet.clear();
		mLITranInfoSet.clear();
        LIVertifyDB tLIVertifyDB = new LIVertifyDB();
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql("select * from livertify");
        mLIVertifySet = (LIVertifySet)tLIVertifyDB.executeQuery(sqlbv);


		return true;
	}
	
	
	private boolean dealData()
	{


		return true;
	}
	
/**
 * 凭证提取的分类 
 **/	
	private boolean getVoucherData(int i)
	{
		try
		{
			switch(i)
			{
			case 1: // 暂收保费
				if(!getFeeDataTemp())
					return false;
				break;
			case 2: // 预收保费
				if(!getFeeDataAdvance())
					return false;
				break;
			case 3: // 保费收入
				if(!getFeeDataPrem())
					return false;
				break;
			case 4: // 赔款支出
				if(!PayFeeDataClaimJ())
					return false;
				break;
			case 5: // 保全应付
				if(!PayFeeDataEndordseJ())
					return false;
				break;
			case 6: // 领取类给付
				if(!PayFeeDataDrawJ())
					return false;
				break;
			case 7: // 其他应付
				if(!PayFeeDataJ())
					return false;
				break;
			case 8: // 实付
				if(!PayFeeDataD())
					return false;
				break;
//			case 9: // 保全收费
//				if(!getEndorsePrem())
//					return false;
//				break;
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			CError.buildErr(this, ex.toString());
			return false;
		}

		return true;
	}
	

	
/**
	 * 暂收凭证的提取
 **/	
	private boolean getFeeDataTemp() 
	{
		// 暂收费（排除银保通的处理）
		String tSql = "select distinct(tempfeeno) from ljtempfee a "
				+ "where confmakedate='?mToday?' and managecom like concat('?cManageCom?','%') "
				+ "and tempfeetype='1' and exists(  " // 当为银行代收时分开处理集中代收和独立代收
				+ "select 1 from ljtempfeeclass b where tempfeeno = a.tempfeeno "
				+ "and (paymode not in ('4','A','B') " 
				+ "or ( paymode in ('4','A') and exists(select 1 from ldcode where codetype='bank' and trim(code) = trim(b.bankcode) "
				+ "and rpad(trim(comcode),6,'0') = substr(b.managecom,1,6)))))"; // paymode='A'针对河南邮储
		        // 2008-6-20 zy 将银行编码从ldbank转移到ldcode，因为有些银行不需要报返盘格式，不再ldbank中进行描述
		logger.debug(tSql);
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("mToday",mToday);
		sqlbv1.put("cManageCom", cManageCom);
		
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv1);
		for(int i = 1; i <= tSSRS.MaxRow; i++)
		{
			String tTempfeeno = tSSRS.GetText(i, 1);
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
			String tSql1 = "select * from LJTempFee " + "where tempfeeno='?tempfeeno?' order by paymoney desc";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(tSql1);
			sqlbv2.put("tempfeeno", tTempfeeno.trim());
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv2);
			String tSql2 = "select * from LJTempFeeClass " + "where tempfeeno='?tempfeeno?' order by paymoney desc";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(tSql2);
			sqlbv3.put("tempfeeno", tTempfeeno.trim());
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv3);
			double tLJTempFeeSum = 0;
			double tLJTempFeeClassSum = 0;
			for(int k = 1; k <= tLJTempFeeSet.size(); k++)
			{
				tLJTempFeeSum = tLJTempFeeSum + tLJTempFeeSet.get(k).getPayMoney();
			}
			for(int k = 1; k <= tLJTempFeeClassSet.size(); k++)
			{
				tLJTempFeeClassSum = tLJTempFeeClassSum + tLJTempFeeClassSet.get(k).getPayMoney();
			}
			if(Math.abs((tLJTempFeeSum - tLJTempFeeClassSum)) >= 0.001)
			{
				dealError("LJTempFee", tTempfeeno, tTempfeeno, "006", mVoucherType+mMatchID+i, "暂收费与暂收费分类金额不一致");
				continue;
			}

			for(int j = 1; j <= tLJTempFeeSet.size(); j++)
			{
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema = tLJTempFeeSet.get(j);
				if(tLJTempFeeSchema.getPayMoney() <= 0)
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "004",
							mVoucherType+mMatchID+j, "暂收费金额为非正数");
					continue;
				}
				if(tLJTempFeeSchema.getManageCom().trim().length() == 2)
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "007",
							mVoucherType+mMatchID+j, "管理机构为2位，不予提取");
					continue;
				}
				if("1".equals(tLJTempFeeSchema.getRiskType()))
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "013",
							mVoucherType+mMatchID+j, "该保单为健康委托产品，不在此进行凭证的提取");
					continue;
				}

				initVar();
				mMatchID++;
				mPolNo = tLJTempFeeSchema.getOtherNo();
				mContNo = tLJTempFeeSchema.getOtherNo();
				mBussNo = tLJTempFeeSchema.getTempFeeNo();
				mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
				mTransDate = tLJTempFeeSchema.getConfMakeDate();
				mSegment2 = tLJTempFeeSchema.getManageCom();
				mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 6); // 所有支票都转入商业银行账户
				mRiskCode = tLJTempFeeSchema.getRiskCode();


				String tFailFlag = "0";
				// 取值
				LJTempFeeClassSet ttLJTempFeeClassSet = this.getAccTemp(1, tLJTempFeeSchema.getPayMoney(),
						tLJTempFeeClassSet);
				for(int k = 1; k <= ttLJTempFeeClassSet.size(); k++)
				{
					LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
					tLJTempFeeClassSchema.setSchema(ttLJTempFeeClassSet.get(k));
					tLJTempFeeClassSchema.setPayMoney(new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema
							.getPayMoney())));
					if(tLJTempFeeClassSchema.getPayMoney() < 0.01)
					{
						dealError("LJTempFeeClass", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "004",
								mVoucherType+mMatchID+k, "暂收分类金额为非正数");
						continue;
					}

					mHeadDescription = "分类收暂收保费！";
					tmoney = new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema.getPayMoney()));
					//判断如果是邮保通的保单则获取保单信息
					if(tLJTempFeeClassSchema.getPayMode().toUpperCase().equals("A"))
					{
						if(!setPolInfo(mPolNo,mRiskCode, "LJTempFeeClass", mVoucherType+mMatchID+k))
						{
							tFailFlag = "1";
							continue;
						}
					}
					for(int t = 1; t <= 2; t++)
					{
						if(t == 1)
						{
							// 贷方如果有问题，则借方不再处理
							if(tFailFlag.equals("1"))
								break;
							
							if("8637".equals(mManageCom.substring(0, 4))
									&& "2227".equals(tLJTempFeeClassSchema.getBankCode()))
							{
								mBillID = "0116";
								mAccountCode = "其他应收款-资金中转-邮政储蓄";
							}
							else
							{
								if(tLJTempFeeClassSchema.getPayMode().equals("1"))
								{
									mBillID = "0100";
									mAccountCode = "库存现金-人民币-业务";
								}
								else if(tLJTempFeeClassSchema.getPayMode().equals("5"))
								{
									mBillID = "0101";
									mAccountCode = "应付业务支出-内部转帐"; // 内部转账借
								}
								else if(tLJTempFeeClassSchema.getPayMode().equals("3"))
								{
									mBillID = "0102";
									mAccountCode = "银行存款-活期-人民币"; // 银行支票
									mAccountSubCode = "支" + mManageCom;
								}
								else if(tLJTempFeeClassSchema.getPayMode().toUpperCase().equals("A"))
								{
									mHeadDescription = "应收保费！";
									mTransDate = tLJTempFeeSchema.getEnterAccDate();
//									if(!setPolInfo(mPolNo,mRiskCode, "LJTempFeeClass", mVoucherType+mMatchID+k))
//									{
//										tFailFlag = "1";
//										break;
//									}
									mBillID = "0110";
									mAccountCode = "银行存款-活期-人民币"; // 针对邮保通保费核销
									mAccountSubCode = "收" + tLJTempFeeClassSchema.getBankCode();
								}
								else
								{
									if(tLJTempFeeClassSchema.getBankCode() == null) // 银行托收银行代码为空
									{
										dealError("LJTempFeeClass", tLJTempFeeClassSchema.getTempFeeNo(),
										tLJTempFeeSchema.getOtherNo(), "008", mVoucherType+mMatchID+k, "银行托收/现金支票银行代码为空");
										tFailFlag = "1";
										break;
									}
									mBillID = "0103";
									mAccountCode = "银行存款-活期-人民币"; // 银行存款
									mAccountSubCode = "收" + tLJTempFeeClassSchema.getBankCode();
								}

							}
							mEnteredDR = String.valueOf(tmoney);
							mEnteredCR = "";
						}
						else
						{
							// 借方如果有问题，则贷方不再处理
							if(tFailFlag.equals("1"))
								break;
							if(tLJTempFeeClassSchema.getPayMode().toUpperCase().equals("A")) // 邮保通保费核销
							{
								mBillID = "0111";
								mAccountCode = "应收保费-首期-邮保通";
								mHeadDescription = "应收保费";
								mAccountSubCode = "NA";
							}
							else
							{
								mBillID = "0104";
								mAccountCode = "暂收保费"; // 暂收
								mAccountSubCode = "NA";
								mHeadDescription = "收暂收保费！";
							}
							mEnteredDR = "";
							mEnteredCR = String.valueOf(tmoney);

						}

						if(isExitInTab(mBussNo, null, mBillID))
						{
							tFailFlag = "1";
							break;
						}
						OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
						if(tOFInterfaceSchema != null)
							mOFInterfaceSet.add(tOFInterfaceSchema);

						dealLITranInfo();
					}
				}
			}
		}

		// 处理集中代收的数据
		String sql = "select distinct(tempfeeno) from LJTempFee a "
				+ "where Confmakedate='?mToday?' and ManageCom like concat('?cManageCom?','%') "
				+ "and TempFeeType='1' and exists(  " // 当为银行代收时分开处理集中代收和独立代收
				+ "select 1 from ljtempfeeclass b where tempfeeno = a.tempfeeno and bankcode is not null and paymode in( '4','A' ) "
				// paymode='A'针对河南邮储
				+ "and exists(select 1 from ldcode  where  codetype='bank' and trim(code) = trim(b.bankcode) "
				+ "and rpad(trim(comcode),6,'0') <> substr(b.managecom,1,6)))";
		logger.debug(sql);
		tExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(sql);
		sqlbv4.put("mToday", mToday);
		sqlbv4.put("cManageCom", cManageCom);
		SSRS ssrs = tExeSQL.execSQL(sqlbv4);
		for(int i = 1; i <= ssrs.MaxRow; i++)
		{
			String tTempfeeno = ssrs.GetText(i, 1);
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();

			String tSql1 = "select * from LJTempFee " + "where tempfeeno='?tempfeeno?' order by paymoney desc";
			String tSql2 = "select * from LJTempFeeClass " + "where tempfeeno='?tempfeeno?' order by paymoney desc";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(tSql1);
			sqlbv5.put("tempfeeno",tTempfeeno.trim());
			SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
			sqlbv6.sql(tSql2);
			sqlbv6.put("tempfeeno",tTempfeeno.trim());
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv5);
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv6);
			double tLJTempFeeSum = 0;
			double tLJTempFeeClassSum = 0;
			for(int k = 1; k <= tLJTempFeeSet.size(); k++)
			{
				tLJTempFeeSum = tLJTempFeeSum + tLJTempFeeSet.get(k).getPayMoney();
			}
			for(int k = 1; k <= tLJTempFeeClassSet.size(); k++)
			{
				tLJTempFeeClassSum = tLJTempFeeClassSum + tLJTempFeeClassSet.get(k).getPayMoney();
			}
			if(Math.abs((tLJTempFeeSum - tLJTempFeeClassSum)) >= 0.001)
			{
				dealError("LJTempFee", tTempfeeno, tTempfeeno, "006", mVoucherType+mMatchID+i, "暂收费与暂收费分类金额不一致");
				continue;
			}

			for(int j = 1; j <= tLJTempFeeSet.size(); j++)
			{
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema = tLJTempFeeSet.get(j);
				if(tLJTempFeeSchema.getPayMoney() <= 0)
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "004",
							mVoucherType+mMatchID+j, "暂收费金额为非正数");
					continue;
				}
				if(tLJTempFeeSchema.getManageCom().trim().length() == 2)
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "007",
							mVoucherType+mMatchID+j, "管理机构为2位，不予提取");
					continue;
				}

				if("1".equals(tLJTempFeeSchema.getRiskType()))
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "013",
							mVoucherType+mMatchID+j, "该保单为健康委托产品，不在此进行凭证的提取");
					continue;
				}

				initVar();
				mMatchID++;
				mPolNo = tLJTempFeeSchema.getOtherNo();
				mContNo = tLJTempFeeSchema.getOtherNo();
				mBussNo = tLJTempFeeSchema.getTempFeeNo();
				mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
				mTransDate = tLJTempFeeSchema.getConfMakeDate();
				mRiskCode = tLJTempFeeSchema.getRiskCode();

				String tFailFlag = "0";
				// 取值
				LJTempFeeClassSet ttLJTempFeeClassSet = this.getAccTemp(1, tLJTempFeeSchema.getPayMoney(),
						tLJTempFeeClassSet);
				for(int k = 1; k <= ttLJTempFeeClassSet.size(); k++)
				{
					LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
					tLJTempFeeClassSchema.setSchema(ttLJTempFeeClassSet.get(k));
					tLJTempFeeClassSchema.setPayMoney(new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema
							.getPayMoney())));
					if(tLJTempFeeClassSchema.getPayMoney() < 0.01)
					{
						dealError("LJTempFeeClass", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "004",
								mVoucherType+mMatchID+k, "暂收分类金额为非正数");
						continue;
					}

					mSegment2 = tLJTempFeeSchema.getManageCom();
					mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 6); // 所有支票都转入商业银行账户
					tmoney = new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema.getPayMoney()));

					if(tLJTempFeeClassSchema.getPayMode().equals("A"))
					{
						mHeadDescription = "应收保费！";
						mTransDate = tLJTempFeeSchema.getEnterAccDate();
						//如果是邮保通的单子，先进行判断，如果保单不存在则直接跳出
						if(!setPolInfo(mPolNo,mRiskCode, "LJTempFeeClass", mVoucherType+mMatchID+k))
						{
							tFailFlag = "1";
							continue;
						}

					}
					else
					{
						mHeadDescription = "分类收暂收保费！";
					}

					String tBankCom = getTBankCom(tLJTempFeeClassSchema.getBankCode(), mVoucherType+mMatchID+k);
					for(int t = 1; t <= 2; t++)
					{
						if(t == 1)
						{
							if(tFailFlag.equals("1"))
								break;
							// 判断是否是分公司代支公司集中扣款
							if(!tLJTempFeeClassSchema.getPayMode().equals("1")
									&& !tLJTempFeeClassSchema.getPayMode().equals("5"))
							{
//								//如果是邮保通的单子，先进行判断，如果保单不存在则直接跳出
//								if(tLJTempFeeClassSchema.getPayMode().toUpperCase().equals("A")) // 邮保通保费核销
//								{
//									if(!setPolInfo(mPolNo,mRiskCode, "LJTempFeeClass", mVoucherType+mMatchID+k))
//									{
//										tFailFlag = "1";
//										continue;
//									}
//								}
								// 支公司
								if(!tBankCom.equals(mManageCom))
								{
									mBillID = "0105";
									mAccountCode = "其他应收款-资金中转-集中批收"; // 集中批收的处理
									mAccountSubCode = "C"+ tLJTempFeeSchema.getManageCom().substring(2, 4).concat("0000"); 
									// 分公司代码转化，如86513400分公司记为C510100
									mEnteredDR = String.valueOf(tmoney);
									mEnteredCR = "";
									if(isExitInTab(mBussNo, null, mBillID))
									{
										tFailFlag = "1";
										break;
									}

									OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
									if(tOFInterfaceSchema != null)
										mOFInterfaceSet.add(tOFInterfaceSchema);

									dealLITranInfo();
								}

								if(!("B".equals(tLJTempFeeClassSchema.getPayMode())))
								{
									mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 4).concat("00");
									mSegment2 = mManageCom.concat("00"); // 分公司帐套记录
									if(("8637".equals(mManageCom.substring(0, 4)))
											&& "2227".equals(tLJTempFeeClassSchema.getBankCode())) // 针对山东分公司集中批收
									{
										mBillID = "0115";
										mAccountCode = "其他应收款-资金中转-邮政储蓄";
									}
									else
									{
										if(tLJTempFeeClassSchema.getBankCode() == null) // 银行托收银行代码为空
										{
											dealError("LJTempFeeClass", tLJTempFeeClassSchema.getTempFeeNo(),
													tLJTempFeeClassSchema.getTempFeeNo(), "008", mVoucherType+mMatchID+k, 
													"银行集中批收的银行编码为空");
											tFailFlag = "1";
											break;
										}
										mBillID = "0107";
										mAccountCode = "银行存款-活期-人民币"; // 银行存款
										mAccountSubCode = "收" + tLJTempFeeClassSchema.getBankCode();
									}
								}
							}
							mEnteredDR = String.valueOf(tmoney);
							mEnteredCR = "";
						}
						else
						{
							// 贷方如果有问题，则借方不再处理
							if(tFailFlag.equals("1"))
								break;

							if(!("B".equals(tLJTempFeeClassSchema.getPayMode()) && "CBC".equals(tLJTempFeeClassSchema.getOperator())))
							{
								mBillID = "0108";
								mAccountCode = "其他应付款-资金中转-集中批收"; // 暂收
								mAccountSubCode = "C" + tLJTempFeeSchema.getManageCom().substring(2, 6).concat("00");
								// 支公司的科目改变，如86513400－C513400
								mEnteredDR = "";
								mEnteredCR = String.valueOf(tmoney);
								if(isExitInTab(mBussNo, null, mBillID))
								{
									tFailFlag = "1";
									break;
								}

								OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
								if(tOFInterfaceSchema != null)
									mOFInterfaceSet.add(tOFInterfaceSchema);

								dealLITranInfo();
							}

							mSegment2 = tLJTempFeeSchema.getManageCom();
							mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 6); // 所有支票都转入商业银行账户
							if(!tBankCom.equals(mManageCom))
							{
								if(tLJTempFeeClassSchema.getPayMode().toUpperCase().equals("A")) // 邮保通保费核销
								{
									mBillID = "0113";
									mAccountCode = "应收保费-首期-邮保通";
									mEnteredCR = String.valueOf(tmoney);
									mHeadDescription = "应收保费";
									mAccountSubCode = "NA";
								}
								else
								{
									mBillID = "0109";
									mAccountCode = "暂收保费"; // 暂收
									mAccountSubCode = "NA";
									mHeadDescription = "收暂收保费！";
									mEnteredDR = "";
									mEnteredCR = String.valueOf(tmoney);
								}
							}
						}

						if(isExitInTab(mBussNo, null, mBillID))
						{
							tFailFlag = "1";
							break;
						}

						OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
						if(tOFInterfaceSchema != null)
							mOFInterfaceSet.add(tOFInterfaceSchema);

						dealLITranInfo();
					}
				}
			}
		}

		// 提取银保通单子的凭证(PAYMODE='B') ！
		// 2008-11-08 zy 增加对建行银保通的处理 由于工商银行的银保通银行编码同一为011，无法获取明细银行编码，所以需要对建行和工行做特殊处理
		// 2008-11-26 为了保证日结单的提取，要求工行的银行编码获取到最明细的银行编码
		//2009-12-21 zy 新总对总邮储的暂收凭证记账规则等同于银保通的记账规则
		/*String ySql = "select distinct(tempfeeno) from LJTempFee a where Confmakedate='"+mToday+"' "
				    + "and ManageCom like '"+cManageCom+"%' and TempFeeType='1' and exists( "
				    + "select 1 from ljtempfeeclass b where tempfeeno = a.tempfeeno and paymode='B' "
				    + "and ((operator='CBC' and exists(select 1 from ldcode where codetype='bank' and "
				    + "code = b.bankcode and rpad(trim(comcode),6,'0') = substr(b.managecom,0,6))) or operator<>'CBC' ))";

		logger.debug(ySql);

		SSRS ySSRS = tExeSQL.execSQL(ySql);
		for(int i = 1; i <= ySSRS.MaxRow; i++)
		{
			String tTempfeeno = ySSRS.GetText(i, 1);
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
			String tSql1 = "select * from LJTempFee where tempfeeno='" + tTempfeeno.trim() + "' order by paymoney desc";
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(tSql1);
			String tSql2 = "select * from LJTempFeeClass where tempfeeno='" + tTempfeeno.trim()
					+ "' order by paymoney desc";
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(tSql2);
			double tLJTempFeeSum = 0;
			double tLJTempFeeClassSum = 0;
			for(int k = 1; k <= tLJTempFeeSet.size(); k++)
			{
				tLJTempFeeSum = tLJTempFeeSum + tLJTempFeeSet.get(k).getPayMoney();
			}
			for(int k = 1; k <= tLJTempFeeClassSet.size(); k++)
			{
				tLJTempFeeClassSum = tLJTempFeeClassSum + tLJTempFeeClassSet.get(k).getPayMoney();
			}
			if(Math.abs((tLJTempFeeSum - tLJTempFeeClassSum)) >= 0.001)
			{
				dealError("LJTempFee", tTempfeeno, tTempfeeno, "006", mVoucherType+mMatchID+i, "暂收费与暂收费分类金额不一致");
				continue;
			}

			for(int j = 1; j <= tLJTempFeeSet.size(); j++)
			{
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema = tLJTempFeeSet.get(j);
				if(tLJTempFeeSchema.getPayMoney() <= 0)
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "004",
							mVoucherType+mMatchID+j, "暂收费金额为非正数");
					continue;
				}
				if(tLJTempFeeSchema.getManageCom().trim().length() == 2)
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "007",
							mVoucherType+mMatchID+j, "管理机构为2位，不予提取");
					continue;
				}
				if("1".equals(tLJTempFeeSchema.getRiskType()))
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "013",
							mVoucherType+mMatchID+j, "该保单为健康委托产品，不在此进行凭证的提取");
					continue;
				}

				initVar();
				mMatchID++;
				mPolNo = tLJTempFeeSchema.getOtherNo();
				mContNo = tLJTempFeeSchema.getOtherNo();
				mBussNo = tLJTempFeeSchema.getTempFeeNo();
				mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
				mTransDate = tLJTempFeeSchema.getConfMakeDate();
				mSegment2 = tLJTempFeeSchema.getManageCom();
				mRiskCode = tLJTempFeeSchema.getRiskCode();
				mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 6);

				String tFailFlag = "0";
				// 取值
				LJTempFeeClassSet ttLJTempFeeClassSet = this.getAccTemp(1, tLJTempFeeSchema.getPayMoney(),
						tLJTempFeeClassSet);
				for(int k = 1; k <= ttLJTempFeeClassSet.size(); k++)
				{
					LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
					tLJTempFeeClassSchema.setSchema(ttLJTempFeeClassSet.get(k));
					tLJTempFeeClassSchema.setPayMoney(new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema
							.getPayMoney())));
					if(tLJTempFeeClassSchema.getPayMoney() < 0.01)
					{
						dealError("LJTempFeeClass", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "004",
								mVoucherType+mMatchID+k, "暂收分类金额为非正数");
						continue;
					}
					mHeadDescription = "分类收暂收保费！";
					tmoney = new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema.getPayMoney()));

					for(int t = 1; t <= 2; t++)
					{
						if(t == 1)
						{
							//建行分公司处理
							if("CBC".equals(tLJTempFeeClassSchema.getOperator()))
							{
								mBillID = "0103";
								mAccountCode = "银行存款-活期-人民币"; // 银行存款
								mAccountSubCode = "收" + tLJTempFeeClassSchema.getBankCode();

							}
							else
							{  //工行的处理
								if(tLJTempFeeClassSchema.getManageCom().substring(0, 4).concat("0000").equals(
										tLJTempFeeClassSchema.getManageCom())) // 如果是分公司直接借：银行存款
								{
									mBillID = "0117";
									mAccountCode = "银行存款-活期-人民币"; // 银行存款
									mAccountSubCode = "收"+ tLJTempFeeClassSchema.getBankCode();
//									mAccountSubCode = "收"+ getYBankCode(
//											tLJTempFeeClassSchema.getBankCode(), tLJTempFeeClassSchema.getManageCom());
								}
								else
								{

									mBillID = "0118";
									mAccountCode = "其他应收款-资金中转-工行银保通";
									mAccountSubCode = "C"
											+ tLJTempFeeSchema.getManageCom().substring(2, 4).concat("0000");
									// 如果是支公司，则借其他应收科目，然后分公司手工做帐
								}
							}
							mEnteredDR = String.valueOf(tmoney);
							mEnteredCR = "";
						}
						else
						{
							// 贷方如果有问题，则借方不再处理
							if(tFailFlag.equals("1"))
								continue;

							mBillID = "0104";
							mAccountCode = "暂收保费"; // 暂收
							mAccountSubCode = "NA";
							mHeadDescription = "收暂收保费！";

							mEnteredDR = "";
							mEnteredCR = String.valueOf(tmoney);

						}

						if(isExitInTab(mBussNo, null, mBillID))
						{
							tFailFlag = "1";
							break;
						}

						OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
						if(tOFInterfaceSchema != null)
							mOFInterfaceSet.add(tOFInterfaceSchema);

						dealLITranInfo();
					}
				}
			}
		}// end of 银保通处理分公司的处理
*/
		// 增加对建行银保通支公司的处理
//		String jySql = "select distinct(tempfeeno) from LJTempFee a where Confmakedate='"+mToday+"' "
//					 + "and ManageCom like '"+cManageCom+"%' and TempFeeType='1' and exists( "
//					 + "select 1 from ljtempfeeclass b where tempfeeno = a.tempfeeno and paymode='B' and operator='CBC' "
//					 + "and exists(select 1 from ldcode where codetype='bank' and code = b.bankcode "
//					 + "and rpad(trim(comcode),6,'0') <> substr(b.managecom,0,6)))";
		// 增加对建行银保通支公司的处理
		String jySql = "select distinct(tempfeeno) from LJTempFee a where Confmakedate='?mToday?' "
					 + "and ManageCom like concat('?cManageCom?','%') and TempFeeType='1' and exists( "
					 + "select 1 from ljtempfeeclass b where tempfeeno = a.tempfeeno and paymode='B' )";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(jySql);
		sqlbv7.put("mToday", mToday);
		sqlbv7.put("cManageCom", cManageCom);

		logger.debug(jySql);

		SSRS jySSRS = tExeSQL.execSQL(sqlbv7);
		for(int i = 1; i <= jySSRS.MaxRow; i++)
		{
			String tTempfeeno = jySSRS.GetText(i, 1);
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
			String tSql1 = "select * from LJTempFee where tempfeeno='?tempfeeno?' order by paymoney desc";
			SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
			sqlbv8.sql(tSql1);
			sqlbv8.put("tempfeeno",tTempfeeno.trim());
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv8);
			String tSql2 = "select * from LJTempFeeClass where tempfeeno='?tempfeeno?' order by paymoney desc";
			SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
			sqlbv9.sql(tSql2);
			sqlbv9.put("tempfeeno", tTempfeeno.trim());
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv9);
			double tLJTempFeeSum = 0;
			double tLJTempFeeClassSum = 0;
			for(int k = 1; k <= tLJTempFeeSet.size(); k++)
			{
				tLJTempFeeSum = tLJTempFeeSum + tLJTempFeeSet.get(k).getPayMoney();
			}
			for(int k = 1; k <= tLJTempFeeClassSet.size(); k++)
			{
				tLJTempFeeClassSum = tLJTempFeeClassSum + tLJTempFeeClassSet.get(k).getPayMoney();
			}
			if(Math.abs((tLJTempFeeSum - tLJTempFeeClassSum)) >= 0.001)
			{
				dealError("LJTempFee", tTempfeeno, tTempfeeno, "006", mVoucherType+mMatchID+i, "暂收费与暂收费分类金额不一致");
				continue;
			}
			

			for(int j = 1; j <= tLJTempFeeSet.size(); j++)
			{
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema = tLJTempFeeSet.get(j);
				if(tLJTempFeeSchema.getPayMoney() <= 0)
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "004",
							mVoucherType+mMatchID+j, "暂收费金额为非正数");
					continue;
				}
				if(tLJTempFeeSchema.getManageCom().trim().length() == 2)
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "007",
							mVoucherType+mMatchID+j, "管理机构为2位，不予提取");
					continue;
				}

				if("1".equals(tLJTempFeeSchema.getRiskType()))
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "013",
							mVoucherType+mMatchID+j, "该保单为健康委托产品，不在此进行凭证的提取");
					continue;
				}
				initVar();
				mMatchID++;
				mPolNo = tLJTempFeeSchema.getOtherNo();
				mContNo = tLJTempFeeSchema.getOtherNo();
				mBussNo = tLJTempFeeSchema.getTempFeeNo();
				mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
				mTransDate = tLJTempFeeSchema.getConfMakeDate();
				mSegment2 = tLJTempFeeSchema.getManageCom();
				mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 6); // 所有支票都转入商业银行账户
				mRiskCode = tLJTempFeeSchema.getRiskCode();


				String tFailFlag = "0";
				// 取值
				LJTempFeeClassSet ttLJTempFeeClassSet = this.getAccTemp(1, tLJTempFeeSchema.getPayMoney(),
						tLJTempFeeClassSet);
				for(int k = 1; k <= ttLJTempFeeClassSet.size(); k++)
				{
					LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
					tLJTempFeeClassSchema.setSchema(ttLJTempFeeClassSet.get(k));
					tLJTempFeeClassSchema.setPayMoney(new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema
							.getPayMoney())));
					if(tLJTempFeeClassSchema.getPayMoney() < 0.01)
					{
						dealError("LJTempFeeclass", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "004",
								mVoucherType+mMatchID+k, "暂收分类金额为非正数");
						continue;
					}

					mHeadDescription = "分类收暂收保费！";
					tmoney = new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema.getPayMoney()));

					String tBankCom = getTBankCom(tLJTempFeeClassSchema.getBankCode(), mVoucherType+mMatchID+k);
					for(int t = 1; t <= 2; t++)
					{
						if(t == 1)
						{
							if("CBC".equals(tLJTempFeeClassSchema.getOperator()))
							{
							
								if(!tBankCom.equals(mManageCom))
								{
									mBillID = "0115";
									mAccountCode = "其他应收款-资金中转-建行银保通"; // 银行存款
									mAccountSubCode = "C"
											+ tLJTempFeeSchema.getManageCom().substring(2, 4).concat("0000");
								}	
								else
								{
//									dealError("LJTempFeeClass", mBussNo, mPolNo, "005", mVoucherType+mMatchID+k, 
//									"银保通对应的管理机构为分公司，不进行凭证提取");
//			            			tFailFlag="1";
//			            			break;
									mBillID = "0103";
									mAccountCode = "银行存款-活期-人民币"; // 银行存款
									mAccountSubCode = "收" + tLJTempFeeClassSchema.getBankCode();
								}
							}
							else if("ICBC".equals(tLJTempFeeClassSchema.getOperator()))
							{
								if(!tBankCom.equals(mManageCom))
								{
									mBillID = "0118";
									mAccountCode = "其他应收款-资金中转-工行银保通";
									mAccountSubCode = "C"
											+ tLJTempFeeSchema.getManageCom().substring(2, 4).concat("0000");
								}
								else
								{

									mBillID = "0117";
									mAccountCode = "银行存款-活期-人民币"; // 银行存款
									mAccountSubCode = "收"+ tLJTempFeeClassSchema.getBankCode();
								}
							}
							else if("YBT".equals(tLJTempFeeClassSchema.getOperator()))
							{
								if(!tBankCom.equals(mManageCom))
								{
									mBillID = "01151";
									mAccountCode = "其他应收款-资金中转-邮储银保通"; // 银行存款
									mAccountSubCode = "C"
											+ tLJTempFeeSchema.getManageCom().substring(2, 4).concat("0000");
								}
								else
								{

									mBillID = "0119";
									mAccountCode = "银行存款-活期-人民币"; // 银行存款
									mAccountSubCode = "收"+ tLJTempFeeClassSchema.getBankCode();
								}
							}
							else  // 分公司手工做账
							{
								dealError("LJTempFeeClass", mBussNo, mPolNo, "005", mVoucherType+mMatchID+k, 
										"不支持的凭证提取");
								tFailFlag = "1";
								break;
							}

								
							mEnteredDR = String.valueOf(tmoney);
							mEnteredCR = "";
						}
						else
						{
							// 贷方如果有问题，则借方不再处理
							if(tFailFlag.equals("1"))
								continue;

//							// 排除分公司处理
//							if(!tBankCom.equals(mManageCom))
//							{
								mBillID = "0104";
								mAccountCode = "暂收保费"; // 暂收
								mAccountSubCode = "NA";
								mHeadDescription = "收暂收保费！";
//							}

							mEnteredDR = "";
							mEnteredCR = String.valueOf(tmoney);

						}

						if(isExitInTab(mBussNo, null, mBillID))
						{
							tFailFlag = "1";
							break;
						}

						OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
						if(tOFInterfaceSchema != null)
							mOFInterfaceSet.add(tOFInterfaceSchema);

						dealLITranInfo();
					}
				}
			}
		}

		return true;
	}

	
	
	/**
	 * 预收凭证的提取
 **/
	private boolean getFeeDataAdvance() 
	{
		// 暂收费
		String tSql = "select distinct(tempfeeno) from LJTempFee a "
					+ "where Confmakedate='?mToday?' and ManageCom like concat('?cManageCom?','%') "
					+ "and TempFeeType<>'1' and exists( " // 当为银行代收时分开处理集中代收和独立代收
					+ "select 1 from ljtempfeeclass b where tempfeeno = a.tempfeeno and ((paymode = '4' "
					+ "and exists(select 1 from ldbank where bankcode = b.bankcode and "
					+ "rpad(trim(comcode),6,'0') = substr(b.managecom,1,6))) or paymode <> '4'))";
		SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
		sqlbv10.sql(tSql);
		sqlbv10.put("mToday", mToday);
		sqlbv10.put("cManageCom", cManageCom);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv10);
		for(int i = 1; i <= tSSRS.MaxRow; i++)
		{
			String tTempfeeno = tSSRS.GetText(i, 1);
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
			String tSql1 = "select * from LJTempFee " + "where tempfeeno='?tempfeeno?'  order by paymoney desc";
			SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
			sqlbv11.sql(tSql1);
			sqlbv11.put("tempfeeno", tTempfeeno.trim());
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv11);
			String tSql2 = "select * from LJTempFeeClass " + "where tempfeeno='?tempfeeno?'  order by paymoney desc";
			SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
			sqlbv12.sql(tSql2);
			sqlbv12.put("tempfeeno", tTempfeeno.trim());
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv12);
			double tLJTempFeeSum = 0;
			double tLJTempFeeClassSum = 0;
			for(int k = 1; k <= tLJTempFeeSet.size(); k++)
			{
				tLJTempFeeSum = tLJTempFeeSum + tLJTempFeeSet.get(k).getPayMoney();
			}
			for(int k = 1; k <= tLJTempFeeClassSet.size(); k++)
			{
				tLJTempFeeClassSum = tLJTempFeeClassSum + tLJTempFeeClassSet.get(k).getPayMoney();
			}
			if(Math.abs((tLJTempFeeSum - tLJTempFeeClassSum)) >= 0.001)
			{
				dealError("LJTempFee", tTempfeeno, tTempfeeno, "006",mVoucherType+mMatchID+i, "暂收费与暂收费分类金额不一致");
				continue;
			}

			for(int j = 1; j <= tLJTempFeeSet.size(); j++)
			{
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema = tLJTempFeeSet.get(j);
				if(tLJTempFeeSchema.getPayMoney() <= 0)
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "004",
							mVoucherType+mMatchID+j, "暂收费金额为非正数");
					continue;
				}
				if(tLJTempFeeSchema.getManageCom().trim().length() == 2)
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "007",
							mVoucherType+mMatchID+j, "管理机构为2位，不予提取");
					continue;
				}

//				if("1".equals(tLJTempFeeSchema.getTempFeeNoType()))
				//zy 2010-02-22 判断健康委托产品的字段应为risktype
				if("1".equals(tLJTempFeeSchema.getRiskType()))
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "013",
							mVoucherType+mMatchID+j, "该保单为健康委托产品，不在此进行凭证的提取");
					continue;
				}
				if(tLJTempFeeSchema.getPayMoney() > 0)
				{
					initVar();
					mMatchID++;
					mPolNo = tLJTempFeeSchema.getOtherNo();
					mContNo = tLJTempFeeSchema.getOtherNo();
					mBussNo = tLJTempFeeSchema.getTempFeeNo();
					mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
					mTransDate = tLJTempFeeSchema.getConfMakeDate();
					mSegment2 = tLJTempFeeSchema.getManageCom();
					mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 6);
					mRiskCode = tLJTempFeeSchema.getRiskCode();

					// 取值
					String tFailFlag = "0";
					LJTempFeeClassSet ttLJTempFeeClassSet = this.getAccTemp(1, tLJTempFeeSchema.getPayMoney(),
							tLJTempFeeClassSet);
					for(int k = 1; k <= ttLJTempFeeClassSet.size(); k++)
					{
						LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
						tLJTempFeeClassSchema.setSchema(ttLJTempFeeClassSet.get(k));

						tmoney = new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema.getPayMoney()));
						if(Double.parseDouble(tmoney) < 0.01)
						{
							dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(),
									"004", mVoucherType+mMatchID+k, "暂收分类金额为非正数");
							continue;
						}

						for(int t = 1; t <= 2; t++)
						{
							if(t == 1)
							{
								// 如果贷方有问题，则借方不再处理
								if(tFailFlag.equals("1"))
									break;
								if(mManageCom.substring(0, 4).equals("8637")
										&& "2227".equals(tLJTempFeeClassSchema.getBankCode()))
								{
									mBillID = "0215";
									mAccountCode = "其他应收款-资金中转-邮政储蓄";

								}
								else
								{
									if(tLJTempFeeClassSchema.getPayMode().equals("1"))
									{
										mBillID = "0200";
										mAccountCode = "库存现金-人民币-业务";
									}
									else if(tLJTempFeeClassSchema.getPayMode().equals("5"))
									{
										mBillID = "0201";
										mAccountCode = "应付业务支出-内部转帐"; // 应付业务支出??
									}
									else if(tLJTempFeeClassSchema.getPayMode().equals("3"))
									{
										mBillID = "0202";
										mAccountCode = "银行存款-活期-人民币"; // 银行支票
										mAccountSubCode = "支" + mManageCom;
									}
									else
									{
										if(tLJTempFeeClassSchema.getBankCode() == null) // 银行托收银行代码为空
										{
											dealError("LJTempFeeClass", tLJTempFeeClassSchema.getTempFeeNo(),
													tLJTempFeeClassSchema.getTempFeeNo(), "008", mVoucherType+mMatchID+k,
													"银行托收/现金支票的银行编码为空");
											tFailFlag = "1";
											break;
										}
										mBillID = "0203";
										mAccountCode = "银行存款-活期-人民币";
										mAccountSubCode = "收" + tLJTempFeeClassSchema.getBankCode();
									}
								}
								mEnteredDR = String.valueOf(tmoney);
								mEnteredCR = "";
								mHeadDescription = "分类收预收保费！";
							}
							else
							{
								// 如果贷方有问题，则借方不再处理
								if(tFailFlag.equals("1"))
									continue;

								mBillID = "0204";
								mAccountCode = "预收保费";
								mAccountSubCode = "NA";
								mHeadDescription = "收预收保费！";
								mEnteredDR = "";
								mEnteredCR = String.valueOf(tmoney);
							}

							if(isExitInTab(mBussNo, null, mBillID))
							{
								tFailFlag = "1";
								break;
							}
							mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 6);
							OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
							if(tOFInterfaceSchema != null)
								mOFInterfaceSet.add(tOFInterfaceSchema);

							dealLITranInfo();
						}
					}
				}
			}
		}

		
		
		// 处理集中代收的数据
		String sql = "select distinct(tempfeeno) from LJTempFee a "
				+ "where Confmakedate='?mToday?' and ManageCom like concat('?cManageCom?','%') "
				+ "and TempFeeType<>'1' and exists( select 1 from ljtempfeeclass b " // 当为银行代收时分开处理集中代收和独立代收
				+ " where tempfeeno = a.tempfeeno and bankcode is not null and paymode = '4' "
				+ "and exists(select 1 from ldbank where bankcode = b.bankcode and "
				+ "rpad(trim(comcode),6,'0') <> substr(b.managecom,1,6)))";
		SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
		sqlbv13.sql(sql);
		sqlbv13.put("mToday", mToday);
		sqlbv13.put("cManageCom", cManageCom);
		logger.debug(sql);
		tExeSQL = new ExeSQL();
		SSRS ssrs = tExeSQL.execSQL(sqlbv13);
		for(int i = 1; i <= ssrs.MaxRow; i++)
		{
			String tTempfeeno = ssrs.GetText(i, 1);
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();

			String tSql1 = "select * from LJTempFee " + "where tempfeeno='?tempfeeno?' order by paymoney desc";
			String tSql2 = "select * from LJTempFeeClass " + "where tempfeeno='?tempfeeno?' order by paymoney desc";
			SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
			sqlbv14.sql(tSql1);
			sqlbv14.put("tempfeeno", tTempfeeno.trim());
			SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
			sqlbv15.sql(tSql2);
			sqlbv15.put("tempfeeno", tTempfeeno.trim());
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv14);
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv15);
			double tLJTempFeeSum = 0;
			double tLJTempFeeClassSum = 0;
			for(int k = 1; k <= tLJTempFeeSet.size(); k++)
			{
				tLJTempFeeSum = tLJTempFeeSum + tLJTempFeeSet.get(k).getPayMoney();
			}
			for(int k = 1; k <= tLJTempFeeClassSet.size(); k++)
			{
				tLJTempFeeClassSum = tLJTempFeeClassSum + tLJTempFeeClassSet.get(k).getPayMoney();
			}
			if(Math.abs((tLJTempFeeSum - tLJTempFeeClassSum)) >= 0.001)
			{
				dealError("LJTempFee", tTempfeeno, tTempfeeno, "006",mVoucherType+mMatchID+i, "暂收费与暂收费分类金额不一致");
				continue;
			}

			for(int j = 1; j <= tLJTempFeeSet.size(); j++)
			{
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema = tLJTempFeeSet.get(j);
				if(tLJTempFeeSchema.getPayMoney() <= 0)
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(),
							"004", mVoucherType+mMatchID+j, "暂收金额为非正数");
					continue;
				}

				initVar();
				mMatchID++;
				mPolNo = tLJTempFeeSchema.getOtherNo();
				mContNo = tLJTempFeeSchema.getOtherNo();
				mBussNo = tLJTempFeeSchema.getTempFeeNo();
				mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
				mTransDate = tLJTempFeeSchema.getConfMakeDate();
				mRiskCode = tLJTempFeeSchema.getRiskCode();

				String tFailFlag = "0";
				// 取值
				LJTempFeeClassSet ttLJTempFeeClassSet = this.getAccTemp(1, tLJTempFeeSchema.getPayMoney(),
						tLJTempFeeClassSet);
				for(int k = 1; k <= ttLJTempFeeClassSet.size(); k++)
				{
					LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
					tLJTempFeeClassSchema.setSchema(ttLJTempFeeClassSet.get(k));
					tLJTempFeeClassSchema.setPayMoney(new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema
							.getPayMoney())));
					if(tLJTempFeeClassSchema.getPayMoney() <= 0)
					{
						dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "004",
								mVoucherType+mMatchID+k, "暂收费金额为非正数");
						continue;
					}
					if(tLJTempFeeSchema.getManageCom().trim().length() == 2)
					{
						dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "007",
								mVoucherType+mMatchID+k, "管理机构为2位，不予提取");
						continue;
					}

//					if("1".equals(tLJTempFeeSchema.getTempFeeNoType()))
					//zy 2010-02-22 判断健康委托产品的字段应为risktype
					if("1".equals(tLJTempFeeSchema.getRiskType()))
					{
						dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "013",
								mVoucherType+mMatchID+k, "该保单为健康委托产品，不在此进行凭证的提取");
						continue;
					}
					mSegment2 = tLJTempFeeSchema.getManageCom();
					mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 6); // 所有支票都转入商业银行账户
					mHeadDescription = "分类收预收保费！";
					tmoney = new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema.getPayMoney()));

					String tBankCom = getBankCom(tLJTempFeeClassSchema.getBankCode(), "002");
					for(int t = 1; t <= 2; t++)
					{
						if(t == 1)
						{
							// 判断是否是分公司代支公司集中扣款
							if(!tLJTempFeeClassSchema.getPayMode().equals("1")
									&& !tLJTempFeeClassSchema.getPayMode().equals("5"))
							{
								if(!tBankCom.equals(mManageCom))
								{
									mBillID = "0209";
									mAccountCode = "其他应收款-资金中转-集中批收"; // 集中批收的处理
									mAccountSubCode = "C" + mManageCom.substring(2, 4).concat("0000");//支公司的科目改变，如86513400－C513400
									mEnteredDR = String.valueOf(tmoney);
									mEnteredCR = "";
									if(isExitInTab(mBussNo, null, mBillID))
									{
										tFailFlag = "1";
										break;
									}

									OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
									if(tOFInterfaceSchema != null)
										mOFInterfaceSet.add(tOFInterfaceSchema);

									dealLITranInfo();
								}

								mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 4).concat("00");
								mSegment2 = mManageCom.concat("00"); // 分公司帐套记录
								if(mManageCom.substring(0, 4).equals("8637")
										&& "2227".equals(tLJTempFeeClassSchema.getBankCode())) // 针对山东分公司集中批收
								{
									mBillID = "0214";
									mAccountCode = "其他应收款-资金中转-邮政储蓄"; // 银行支票
								}
								else
								{
									if(tLJTempFeeClassSchema.getPayMode().equals("3"))
									{
										mBillID = "0210";
										mAccountCode = "银行存款-活期-人民币"; // 银行支票
										mAccountSubCode = "支" + mManageCom;
									}
									else
									{
										if(tLJTempFeeClassSchema.getBankCode() == null) // 银行托收银行代码为空
										{
											dealError("LJTempFeeClass", tLJTempFeeClassSchema.getTempFeeNo(),
													tLJTempFeeSchema.getOtherNo(), "009", mVoucherType+mMatchID+k, 
													"银行托收/现金支票的银行编码为空");
											tFailFlag = "1";
											break;
										}
										mBillID = "0211";
										mAccountCode = "银行存款-活期-人民币"; // 银行存款
										mAccountSubCode = "收" + tLJTempFeeClassSchema.getBankCode();
									}
								}
							}
							mEnteredDR = String.valueOf(tmoney);
							mEnteredCR = "";
						}
						else
						{
							// 贷方如果有问题，则借方不再处理
							if(tFailFlag.equals("1"))
								continue;

							mBillID = "0212";
							mAccountCode = "其他应付款-资金中转-集中批收"; // 预收
							mAccountSubCode = "C" + tLJTempFeeSchema.getManageCom().substring(2, 6).concat("00");
							mEnteredDR = "";
							mEnteredCR = String.valueOf(tmoney);
							if(isExitInTab(mBussNo, null, mBillID))
							{
								tFailFlag = "1";
								break;
							}

							OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
							if(tOFInterfaceSchema != null)
								mOFInterfaceSet.add(tOFInterfaceSchema);

							dealLITranInfo();

							mSegment2 = tLJTempFeeSchema.getManageCom();
							mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 6); // 所有支票都转入商业银行账户
							if(!tBankCom.equals(mManageCom))
							{
								mBillID = "0213";
								mAccountCode = "预收保费"; // 预收
								mAccountSubCode = "NA";
								mHeadDescription = "收预收保费！";
								mEnteredDR = "";
								mEnteredCR = String.valueOf(tmoney);
							}
						}

						if(isExitInTab(mBussNo, null, mBillID))
						{
							tFailFlag = "1";
							break;
						}

						OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
						if(tOFInterfaceSchema != null)
							mOFInterfaceSet.add(tOFInterfaceSchema);

						dealLITranInfo();
					}
				}
			}
		}

		tSql = "select * from LJAPayPerson where ConfDate='?mToday?'" 
			 + " and ManageCom like concat('?cManageCom?','%') " 
			 + "and paytype='YET' and paycount=1";
		SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
		sqlbv16.sql(tSql);
		sqlbv16.put("mToday", mToday);
		sqlbv16.put("cManageCom", cManageCom);
		logger.debug(tSql);
		LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
		LJAPayPersonSet tLJAPayPersonSet = tLJAPayPersonDB.executeQuery(sqlbv16);
		for(int i = 1; i <= tLJAPayPersonSet.size(); i++)
		{
			LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
			tLJAPayPersonSchema.setSchema(tLJAPayPersonSet.get(i));
			if(tLJAPayPersonSchema.getSumActuPayMoney() == 0)
			{
				dealError("LJAPayPerson", tLJAPayPersonSchema.getPayNo(), tLJAPayPersonSchema.getPolNo(), "004",
						mVoucherType+mMatchID+i, "实收个人表金额为非正数");
				continue;
			}

			initVar();
			mMatchID++;
			mPolNo = tLJAPayPersonSchema.getPolNo();
			mContNo = tLJAPayPersonSchema.getContNo();
			mRiskCode = tLJAPayPersonSchema.getRiskCode();
			mBussNo = tLJAPayPersonSchema.getPayNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAPayPersonSchema.getConfDate();
			mSegment2 = tLJAPayPersonSchema.getManageCom();
			mManageCom = tLJAPayPersonSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAPayPersonSchema.getSumActuPayMoney()));
			if(!this.setPolInfo(mPolNo,mRiskCode, "LJAPayPerson", "002"))
			{
				mHeadDescription = "暂收余额转预收" + mInsuredName + "保费";
				continue;
			}
			else
				mHeadDescription = "暂收余额转预收";

			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					mBillID = "0205";
					mAccountCode = "暂收保费"; // 借暂收
					mEnteredDR = String.valueOf(tmoney);
					mEnteredCR = "";
				}
				else
				{
					mBillID = "0206";
					mAccountCode = "预收保费";
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
				}
				if(isExitInTab(mBussNo, null, mBillID))
					break;

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);

				dealLITranInfo();
			}
		}

		tSql = "select * from LJAPayGrp " 
			+ "where ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') "
			+ "and paytype='YET' and PayCount=1";
		SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
		sqlbv17.sql(tSql);
		sqlbv17.put("mToday", mToday);
		sqlbv17.put("cManageCom", cManageCom);
		logger.debug(tSql);
		LJAPayGrpDB tLJAPayGrpDB = new LJAPayGrpDB();
		LJAPayGrpSet tLJAPayGrpSet = tLJAPayGrpDB.executeQuery(sqlbv17);
		for(int i = 1; i <= tLJAPayGrpSet.size(); i++)
		{
			LJAPayGrpSchema tLJAPayGrpSchema = new LJAPayGrpSchema();
			tLJAPayGrpSchema.setSchema(tLJAPayGrpSet.get(i));
			if(tLJAPayGrpSchema.getSumActuPayMoney() == 0)
			{
				dealError("LJAPayGrp", tLJAPayGrpSchema.getPayNo(), tLJAPayGrpSchema.getGrpPolNo(), "004",
						mVoucherType+mMatchID+i, "实收个人表金额为非正数");
				continue;
			}

			initVar();
			mMatchID++;
			mBussNo = tLJAPayGrpSchema.getPayNo();
			mContNo = tLJAPayGrpSchema.getGrpContNo();
			mPolNo = tLJAPayGrpSchema.getGrpPolNo();
			mRiskCode = tLJAPayGrpSchema.getRiskCode();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAPayGrpSchema.getConfDate();
			mRiskCode = tLJAPayGrpSchema.getRiskCode();
			mSegment2 = tLJAPayGrpSchema.getManageCom();
			mManageCom = tLJAPayGrpSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAPayGrpSchema.getSumActuPayMoney()));
			if(!this.setPolInfo(mPolNo,mRiskCode, "LJAPayGrp", mVoucherType+mMatchID+i))
			{
				mHeadDescription = "暂收余额转预收" + mInsuredName + "保费";
				continue;
			}
			else
				mHeadDescription = "暂收余额转预收,收据号：" + tLJAPayGrpSchema.getPayNo();

			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					mBillID = "0207";
					mAccountCode = "暂收保费"; // 借暂收
					mEnteredDR = String.valueOf(tmoney);
					mEnteredCR = "";
				}
				else
				{
					mBillID = "0208";
					mAccountCode = "预收保费";
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
				}
				if(isExitInTab(mBussNo, null, mBillID))
					break;

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);

				dealLITranInfo();
			}
		}

		return true;
	}



	/**
	 * 保费收入凭证的提取
	 */

	private boolean getFeeDataPrem()
	{
		// 个人实收
		String tSql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
		tSql = "select /*+index (ljapayperson.IDX_LJAPAYPERSON_CONFDATE)+*/ * from LJAPayPerson a "
					+ "where ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') and trim(Grppolno)='00000000000000000000' "
					+ "and not exists (select actugetno from ljaget where othernotype='10' and actugetno=a.payno) "
					+ "and not exists (select payno from ljapay where incometype='10' and payno=a.payno)";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tSql = "select * from LJAPayPerson a "
					+ "where ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') and trim(Grppolno)='00000000000000000000' "
					+ "and not exists (select actugetno from ljaget where othernotype='10' and actugetno=a.payno) "
					+ "and not exists (select payno from ljapay where incometype='10' and payno=a.payno)";	
		}
		logger.debug("个单保费收入查询： " + tSql);
		SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
		sqlbv18.sql(tSql);
		sqlbv18.put("mToday", mToday);
		sqlbv18.put("cManageCom", cManageCom);

		LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
		LJAPayPersonSet tLJAPayPersonSet = tLJAPayPersonDB.executeQuery(sqlbv18);
		for(int i = 1; i <= tLJAPayPersonSet.size(); i++)
		{
			LJAPayPersonSchema tLJAPayPersonSchema = new LJAPayPersonSchema();
			tLJAPayPersonSchema = tLJAPayPersonSet.get(i);

			if(!tLJAPayPersonSchema.getPayType().equals("ZC"))
			{
				dealError("LJAPayPerson", tLJAPayPersonSchema.getPayNo(), tLJAPayPersonSchema.getPolNo(), "005",
						mVoucherType+mMatchID+i,"交费类型为" + tLJAPayPersonSchema.getPayType() + ",不属于凭证提取范围");
				continue;
			}
			if(tLJAPayPersonSchema.getSumActuPayMoney() == 0)
			{
				dealError("LJAPayPerson", tLJAPayPersonSchema.getPayNo(), tLJAPayPersonSchema.getPolNo(), "004",
						mVoucherType+mMatchID+i,"实收个人表交费金额为零");
				continue;
			}

			// 为变量附值
			initVar();
			mMatchID++;
			mPolNo = tLJAPayPersonSchema.getPolNo();
			mContNo = tLJAPayPersonSchema.getContNo();
			mBussNo = tLJAPayPersonSchema.getPayNo();
			mTransDate = tLJAPayPersonSchema.getConfDate();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mSegment2 = tLJAPayPersonSchema.getManageCom();
			mManageCom = tLJAPayPersonSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAPayPersonSchema.getSumActuPayMoney()));

			LCPolBL tLCPolBL = (LCPolBL)getPol(mPolNo, mVoucherType+mMatchID+i);
			if(tLCPolBL == null)
				continue;

			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					//zy 2009-12-18 调整邮保通记账规则
					if("ybt".equals(tLJAPayPersonSchema.getOperator().toLowerCase())||"hnyz".equals(tLJAPayPersonSchema.getOperator().toLowerCase())) // 邮保通网点出单
					{
						String mYType=getYBTPayMode(tLJAPayPersonSchema.getPayNo());
						if("A".equals(mYType))
						{
							mBillID = "0325";
							mAccountCode = "应收保费-首期-邮保通";
						}
						else if("B".equals(mYType))
						{
							if(tLJAPayPersonSchema.getPayCount() == 1 && (tLCPolBL.getRenewCount()<=0))
							{
								mBillID = "0310";
								mAccountCode = "暂收保费"; // 新保暂转
							}
							else
							{
								mBillID = "0311";
								mAccountCode = "预收保费"; // 续期暂转
							}
						}
						else
						{
							dealError("LJAPayPerson", tLJAPayPersonSchema.getPayNo(), tLJAPayPersonSchema.getPolNo(), "005",
									mVoucherType+mMatchID+i,"不支持的邮保通凭证提取");
							break;
						}
					}
					else if("04".equals(mSaleChnl))
					{
						mBillID = "0312";
						mAccountCode = "其他应收款-电子商务保单"; // 电子商务保单应收款
					}
					else
					{
						if(tLJAPayPersonSchema.getPayCount() == 1 && (tLCPolBL.getRenewCount()<=0))
						{
							mBillID = "0310";
							mAccountCode = "暂收保费"; // 新保暂转
						}
						else
						{
							mBillID = "0311";
							mAccountCode = "预收保费"; // 续期暂转
						}
					}

					mEnteredDR = String.valueOf(tmoney); // 借
					mEnteredCR = ""; // 贷
					mHeadDescription = "转" + mInsuredName + "保费";
				}
				else
				{
					if(tLJAPayPersonSchema.getPayIntv() == 0 || tLJAPayPersonSchema.getPayIntv() == -1)
					{
						mBillID = "0313";
						mAccountCode = "保费收入-趸缴"; // 趸缴
					}
					else
					{
						if(tLJAPayPersonSchema.getPayCount() == 1 )
						{
							mBillID = "0314";
							mAccountCode = "保费收入-首年首期"; // 首年首期
						}
						else
						{
							String tPolDate = PubFun.calDate(tLCPolBL.getCValiDate(), 1, "Y", tLCPolBL.getCValiDate());
							if(PubFun.calInterval(tPolDate, tLJAPayPersonSchema.getCurPayToDate(), "D") > 0)
							{
								mBillID = "0315";
								mAccountCode = "保费收入-续年续期"; // 续年续期
							}
							else
							{
								mBillID = "0316";
								mAccountCode = "保费收入-首年续期"; // 首年续期
							}
						}
					}
					mEnteredDR = ""; // 借
					mEnteredCR = String.valueOf(tmoney); // 贷
					mHeadDescription = "收" + mInsuredName + "保费";
				}

				if(isExitInTab(mBussNo, mPolNo, mBillID))
					break;

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);

				dealLITranInfo();
			}
		}

		
		//团单的保费收入提取
		tSql = "select * from LJAPayGrp g " 
			 + "where ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%')" 
			 + "and not exists (select actugetno from ljaget where othernotype='10' and actugetno=g.payno)"
			 + "and not exists (select payno from ljapay where incometype='10' and payno=g.payno)";
		SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
		sqlbv19.sql(tSql);
		sqlbv19.put("mToday", mToday);
		sqlbv19.put("cManageCom", cManageCom);
		logger.debug("团单保费查询语句： " + tSql);

		LJAPayGrpDB tLJAPayGrpDB = new LJAPayGrpDB();
		LJAPayGrpSet tLJAPayGrpSet = tLJAPayGrpDB.executeQuery(sqlbv19);
		for(int i = 1; i <= tLJAPayGrpSet.size(); i++)
		{
			LJAPayGrpSchema tLJAPayGrpSchema = new LJAPayGrpSchema();
			tLJAPayGrpSchema = tLJAPayGrpSet.get(i);

			if(!tLJAPayGrpSchema.getPayType().equals("ZC"))
			{
				dealError("LJAPayGrp", tLJAPayGrpSchema.getPayNo(), tLJAPayGrpSchema.getGrpPolNo(), "005",
						mVoucherType+mMatchID+i,"交费类型为" + tLJAPayGrpSchema.getPayType() + ",不属于提取范围");
				continue;
			}

			if(tLJAPayGrpSchema.getSumActuPayMoney() == 0)
			{
				dealError("LJAPayGrp", tLJAPayGrpSchema.getPayNo(), tLJAPayGrpSchema.getGrpPolNo(), "004",
						mVoucherType+mMatchID+i,"实收集体表交费金额为零");
				continue;
			}

			initVar();
			mMatchID++;
			mPolNo = tLJAPayGrpSchema.getGrpPolNo();
			mContNo = tLJAPayGrpSchema.getGrpContNo();
			mBussNo = tLJAPayGrpSchema.getPayNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAPayGrpSchema.getConfDate();
			mSegment2 = tLJAPayGrpSchema.getManageCom();
			mManageCom = tLJAPayGrpSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAPayGrpSchema.getSumActuPayMoney()));

			LCGrpPolBL tLCGrpPolBL = (LCGrpPolBL)getGrpPol(mPolNo, mVoucherType+mMatchID+i);
			if(tLCGrpPolBL == null)
				continue;

			double douAirPolPrem = getPremAirPol(mContNo,tLJAPayGrpSchema.getRiskCode().trim());
            if(douAirPolPrem > 0)
                mVoucherType = "1";                //如果这笔记录有航意险信息，记在暂收凭证中－视作借贷相等，专款专用

			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					if(tLJAPayGrpSchema.getPayCount() == 1)
					{
						mBillID = "0317";
						mAccountCode = "暂收保费"; // 新保暂转
					}
					else
					{
						mBillID = "0318";
						mAccountCode = "预收保费"; // 续期暂转
					}

					if(mSaleChnl.equals("04"))
					{
						mBillID = "0319";
						mAccountCode = "其他应收款-电子商务保单"; // 电子商务保单应收款
					}

					mEnteredDR = String.valueOf(tmoney); // 借
					mEnteredCR = ""; // 贷
					mHeadDescription = "暂收保费";
				}
				else
				{
                    if(douAirPolPrem > 0)                                   //判断是否是航意险保单交费
                    {
                        mBillID = "0324";
                        mAccountCode = "应收保费-首期-中介代理";
                        mEnteredDR = "";                                    //借
                        mEnteredCR = String.valueOf(douAirPolPrem);         //贷航意险保费这部金额
                        mHeadDescription = "应收保费";
                        if (isExitInTab(mBussNo,mPolNo,mBillID))
                            break;

                        OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                        if(tOFInterfaceSchema != null)
                            mOFInterfaceSet.add(tOFInterfaceSchema);
                        dealLITranInfo();

                        //如果扣除航意险这部分保费还有多交的部分（或者交费保单比应收保费多，则记入后面的保费收入科目）
                        double tRemainMoney = Double.parseDouble(tmoney) - douAirPolPrem;
                        tmoney = (tRemainMoney >= 0) ? String.valueOf(tRemainMoney) : "0";
                    }
                    
					if(tLJAPayGrpSchema.getPayIntv() == 0 || tLJAPayGrpSchema.getPayIntv() == -1)
					{
						mBillID = "0320";
						mAccountCode = "保费收入-趸缴"; // 趸交
					}
					else
					{
						if(tLJAPayGrpSchema.getPayCount() == 1)
						{
							mBillID = "0321";
							mAccountCode = "保费收入-首年首期"; // 首年首期
						}
						else
						{
							String tPolDate = PubFun.calDate(tLCGrpPolBL.getCValiDate(), 1, "Y", tLCGrpPolBL
									.getCValiDate());
							if(PubFun.calInterval(tPolDate, tLJAPayGrpSchema.getCurPayToDate(), "D") > 0)
							{
								mBillID = "0322";
								mAccountCode = "保费收入-续年续期"; // 续年续期
							}
							else
							{
								mBillID = "0323";
								mAccountCode = "保费收入-首年续期"; // 首年续期
							}
						}
					}
					mEnteredDR = ""; // 借
					mEnteredCR = String.valueOf(tmoney); // 贷
					mHeadDescription = "收" + mInsuredName + "保费";
				}
				if(isExitInTab(mBussNo, mPolNo, mBillID))
					break;

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null && Double.parseDouble(tmoney) > 0.0)
					mOFInterfaceSet.add(tOFInterfaceSchema);
				dealLITranInfo();
			}
		}
		return true;
	}

	
	

    /**
     * 获取该团体航意险保单的保费金额/交费金额
     * @param cGrpPolNo
     * @return
     */
    private double getPremAirPol(String cGrpPolNo,String cRiskCode)
    {
    	//由于航意险的升级暂时未决定（sequenceno）不存在了，所以暂时不做调整
    	//航意险升级在lcpol中contno为lcairpol表的polno
    	double prem=0.00;
    	if("141801".equals(cRiskCode) || "141807".equals(cRiskCode)|| "141810".equals(cRiskCode)|| "141813".equals(cRiskCode)
    			|| "211603".equals(cRiskCode) || "211609".equals(cRiskCode))
    	{
    		//由于满期终止可能导致保单失效，且终止时间正好卡在财务接口提取时间导致获取保单信息失败，所以屏蔽appflag='1'条件  
	        String sql = "select (case when sum(prem) is not null then sum(prem) else 0 end) from lcpol a "
	                   + "where grpcontno = '?cGrpPolNo?' and appflag in ('1','4')  and exists (select 1 from ofinterface  "
	                   + " where vouchertype='3' and managecom like concat('?cManageCom?','%') and polno=a.contno) ";
	        logger.debug(sql);
	        SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
	        sqlbv20.sql(sql);
	        sqlbv20.put("cGrpPolNo",cGrpPolNo);
	        sqlbv20.put("mManageCom", mManageCom);
	        SSRS ssrs = new ExeSQL().execSQL(sqlbv20);
	        
	        if(ssrs == null || ssrs.getMaxRow() <= 0)
	        	prem=0.00;
	        else
	        	prem= Double.parseDouble(ssrs.GetText(1,1));
	    }
    	return prem;
    }
	/**
	 * 理赔应付凭证 待完善的部分：跨机构理赔的财务凭证是否由系统提取
	 */
	private boolean PayFeeDataClaimJ()
	{
		String tSql = "select * from LJAGetClaim where MakeDate='?mToday?' and ManageCom like concat('?cManageCom?','%') " 
					+ "and FeeFinaType not in ( 'HDLX', 'HKLX','BF','HK', 'HD','TM','CM','HLRB','YFRB','HLTF','DJTF','XJTF','LJTF')";
		logger.debug(tSql);
		SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
		sqlbv21.sql(tSql);
		sqlbv21.put("mToday", mToday);
		sqlbv21.put("cManageCom", cManageCom);
		LJAGetClaimDB tLJAGetClaimDB = new LJAGetClaimDB();
		LJAGetClaimSet tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv21);

		for(int i = 1; i <= tLJAGetClaimSet.size(); i++)
		{
			LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
			tLJAGetClaimSchema = tLJAGetClaimSet.get(i);
			if(tLJAGetClaimSchema.getPay() == 0)
			{
				dealError("LJAGetClaim", tLJAGetClaimSchema.getActuGetNo(), tLJAGetClaimSchema.getPolNo(), "004",
						mVoucherType+mMatchID+i, "理赔金额为零");
				continue;
			}
			initVar();
			mMatchID++;
			mPolNo = tLJAGetClaimSchema.getPolNo();
			mContNo = tLJAGetClaimSchema.getContNo();
			mBussNo = tLJAGetClaimSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetClaimSchema.getMakeDate();
			mSegment2 = tLJAGetClaimSchema.getManageCom();
			mManageCom = tLJAGetClaimSchema.getManageCom().substring(0, 6);
			
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetClaimSchema.getPay()));
			LCPolBL tLCPolBL = new LCPolBL();
			tLCPolBL = getPol(mPolNo, mVoucherType+mMatchID+i);
			if(tLCPolBL == null)
				continue;
			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					if("TF".equals(tLJAGetClaimSchema.getFeeFinaType()))
					{
                    	if("0".equals(String.valueOf(tLCPolBL.getPayIntv())) || "-1".equals(String.valueOf(tLCPolBL.getPayIntv())))
                    	{
                        mBillID = "04090";
                        mAccountCode = "保费收入-趸缴";                      //保全退费
                    	}
                    	else
                    	{
                            mBillID = "04091";
                            mAccountCode = "保费收入-首年首期";  
                    	}
//                        this.tmoney = "-" + this.tmoney;
                    	tmoney = new DecimalFormat("0.00").format(new Double(0-tLJAGetClaimSchema.getPay()));
                        mEnteredDR = "";
                        mEnteredCR = String.valueOf(tmoney);
					}
					else
					{
	
						if("SWPK".equals(tLJAGetClaimSchema.getFeeFinaType()))
						{
							mBillID = "0400";
							mAccountCode = "死亡给付"; // 死亡给付金
							mHeadDescription = mAccountCode + tLCPolBL.getInsuredName().trim() + "赔付费，保单号："
							+ tLCPolBL.getContNo();
						}
						else if("YLPK".equals(tLJAGetClaimSchema.getFeeFinaType()))
						{
							mBillID = "0401";
							mAccountCode = "医疗给付"; // 医疗给付金
							mHeadDescription = mAccountCode + tLCPolBL.getInsuredName().trim() + "赔付费，保单号："
							+ tLCPolBL.getContNo();
						}
						else if("SCPK".equals(tLJAGetClaimSchema.getFeeFinaType()))
						{
							mBillID = "0402";
							mAccountCode = "伤残给付"; // 伤残给付金 
							mHeadDescription = mAccountCode + tLCPolBL.getInsuredName().trim() + "赔付费，保单号："
							+ tLCPolBL.getContNo();
						}
						else if("DQPK".equals(tLJAGetClaimSchema.getFeeFinaType()))
						{
							mBillID = "0403";
							mAccountCode = "赔款支出";
							mHeadDescription = mAccountCode + tLCPolBL.getInsuredName().trim() + "赔付费，保单号："
							+ tLCPolBL.getContNo();
						}
	
						//取消预付赔付款科目
	//					else if("YFPK".equals(tLJAGetClaimSchema.getFeeFinaType()) )
	//					{
	//						//二次赔付时产生的与原预付记录相反的数据
	//						if("A".equals(tLJAGetClaimSchema.getSubFeeOperationType()) )
	//						{
	//							break;
	//						}
	//						//预付记录
	//						if("B".equals(tLJAGetClaimSchema.getSubFeeOperationType()))
	//						{
	//							mBillID = "0404";
	//							mAccountCode = "预付赔付款";
	//						}
	//					}
						else if("TB".equals(tLJAGetClaimSchema.getFeeFinaType()))
						{
							mBillID = "0405";
							mAccountCode = "退保金"; // 理赔退保金
							mHeadDescription = tLCPolBL.getInsuredName().trim()+mAccountCode;
						}
						else if("YCLX".equals(tLJAGetClaimSchema.getFeeFinaType()))
						{
							mBillID = "0408";
							mAccountCode = "利息支出-延滞赔款利息";
							mHeadDescription = mAccountCode + tLCPolBL.getInsuredName().trim() + "赔付费，保单号："
							+ tLCPolBL.getContNo();
						}
						else if ("YF".equals(tLJAGetClaimSchema.getFeeFinaType()))
			            {
			                mBillID = "0413";
			                mAccountCode = "年金给付";	
			                mEnteredDR = String.valueOf(tmoney);
			                mEnteredCR = "";
			                mHeadDescription = mAccountCode + mInsuredName + "生存领取费";
			            }
						
						else if("YFLX".equals(tLJAGetClaimSchema.getFeeFinaType()))
						{
							mBillID = "0417";
							mAccountCode = "利息支出-延滞领取利息";
			                mEnteredDR = String.valueOf(tmoney);
			                mEnteredCR = "";
			                mHeadDescription = mAccountCode + mInsuredName + "生存领取费";
						}
						else if ("EF".equals(tLJAGetClaimSchema.getFeeFinaType()))
			            {
			                mBillID = "0414";
			                mAccountCode = "应付业务支出-满期";	
			                mEnteredDR = String.valueOf(tmoney);
			                mEnteredCR = "";
			                mHeadDescription = mAccountCode + mInsuredName + "生存领取费";
			            }
	
						else if("YE".equals(tLJAGetClaimSchema.getFeeFinaType()) )
						{
	                        mBillID = "0410";
	                        mAccountCode = "预收保费";                           //退余额
	                        mHeadDescription = tLCPolBL.getInsuredName().trim()+mAccountCode;
						}
						else
						{
							dealError("LJAGetClaim", mBussNo, mPolNo, "005", mVoucherType+mMatchID+i, "未描述的财务类型"+tLJAGetClaimSchema.getFeeFinaType()+"");
							break;          //待补充
						}
	
						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";
					}
				}
				else
				{
					if("SWPK".equals(tLJAGetClaimSchema.getFeeFinaType()))
					{
						mBillID = "04071";
						mAccountCode = "应付业务支出-死亡给付"; // 死亡给付金
						mHeadDescription = mAccountCode + tLCPolBL.getInsuredName().trim() + "赔付费，保单号："
						+ tLCPolBL.getContNo();
					}
					else if("YLPK".equals(tLJAGetClaimSchema.getFeeFinaType()))
					{
						mBillID = "04072";
						mAccountCode = "应付业务支出-医疗给付"; // 医疗给付金
						mHeadDescription = mAccountCode + tLCPolBL.getInsuredName().trim() + "赔付费，保单号："
						+ tLCPolBL.getContNo();
					}
					else if("SCPK".equals(tLJAGetClaimSchema.getFeeFinaType()))
					{
						mBillID = "04073";
						mAccountCode = "应付业务支出-伤残给付"; // 伤残给付金 
						mHeadDescription = mAccountCode + tLCPolBL.getInsuredName().trim() + "赔付费，保单号："
						+ tLCPolBL.getContNo();
					}
					else if("DQPK".equals(tLJAGetClaimSchema.getFeeFinaType()))
					{
						mBillID = "04074";
						mAccountCode = "应付业务支出-赔款支出";
						mHeadDescription = mAccountCode + tLCPolBL.getInsuredName().trim() + "赔付费，保单号："
						+ tLCPolBL.getContNo();
					}
					else if("TB".equals(tLJAGetClaimSchema.getFeeFinaType()))
					{
						mBillID = "0411";
						mAccountCode = "应付业务支出-退保金"; // 理赔退保金
						mHeadDescription = tLCPolBL.getInsuredName().trim() + mAccountCode;
					}
					else if("YCLX".equals(tLJAGetClaimSchema.getFeeFinaType()))
					{
						mBillID = "0412";
						mAccountCode = "应付业务支出-理赔";
						mHeadDescription = tLCPolBL.getInsuredName().trim() + mAccountCode;
					}

					else if("TF".equals(tLJAGetClaimSchema.getFeeFinaType()) || "YE".equals(tLJAGetClaimSchema.getFeeFinaType()))
					{
						if(getRiskPeriod(mRiskCode, "005").equals("M")
								|| getRiskPeriod(mRiskCode, "005").equals("S"))
						{
							mBillID = "0415";
							mAccountCode = "应付业务支出-短期险退保";
						}
						else
						{
							mBillID = "04150";
							mAccountCode = "应付业务支出-其他";
						}
						mHeadDescription = tLCPolBL.getInsuredName().trim() + mAccountCode;
					}
					else if ("YF".equals(tLJAGetClaimSchema.getFeeFinaType()) || "YFLX".equals(tLJAGetClaimSchema.getFeeFinaType()))
		            {
		                mBillID = "0416";
		                mAccountCode = "应付业务支出-年金";	
		                mHeadDescription = mAccountCode + mInsuredName + "生存领取费";
		            }
					else
					{
						dealError("LJAGetClaim", mBussNo, mPolNo, "005", mVoucherType+mMatchID+i, "未描述的财务类型"+tLJAGetClaimSchema.getFeeFinaType()+"");
						break;          //待补充
					}
					
					tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetClaimSchema.getPay()));
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
				}


				if(isExitInTab(tLJAGetClaimSchema.getActuGetNo(), tLJAGetClaimSchema.getPolNo(), mBillID))
					break;

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);
				dealLITranInfo();
			}
		
		}
		return true;
	}

	/**
	 * 判断银行代码对应的管理机构
	 * 
	 * @param cBankCode
	 * @return ComCode-6位
	 */
	private String getBankCom(String cBankCode, String tID)
	{
        if(cBankCode == null || cBankCode.equals(""))
            return "";

        LDBankDB tLDBankDB = new LDBankDB();
        tLDBankDB.setBankCode(cBankCode);
        if(!tLDBankDB.getInfo())
        {
        	dealError("ldbank", mBussNo, mPolNo, "009", tID, "无法获取银行" + cBankCode + "的机构信息！");
            return "";
        }

        return PubFun.RCh(tLDBankDB.getComCode(),"0",6);
	}

	/**
	 * 判断银行代码对应的管理机构,针对暂收银行编码在ldbank中没有描述的银行
	 * 
	 * @param cBankCode
	 * @return ComCode-6位
	 */
	private String getTBankCom(String cBankCode, String tID)
	{
        if(cBankCode == null || cBankCode.equals(""))
            return "";


        LDCodeDB tLDCodeDB = new LDCodeDB();
        tLDCodeDB.setCodeType("bank");
        tLDCodeDB.setCode(cBankCode.trim());
        if(!tLDCodeDB.getInfo())
        {
        	dealError("LDCode", mBussNo, mPolNo, "009", tID, "无法获取银行" + cBankCode + "的机构信息！");
            return "";
        }

        return PubFun.RCh(tLDCodeDB.getComCode(),"0",6);
	}
	/*******************************************************************************************************************
	 * PayFeeData()分为以下几个部分； PayFeeDataEndordseJ()批改补退费表的业务付费部分；
	 *  PayFeeDataEndordseD()批改补退费的财务付费部分；
	 *  PayFeeDataDrawJ()生存领取表的业务付费部分； PayFeeDataDrawD()生存领取的财务付费部分；
	 * PayFeeDataOtherJ()其它退费表的业务付费操作 PayFeeDataOtheD()退费表的财务付费操作 PayFeeDataClaimJ()
	 * 赔付退费表的业务付费操作 PayFeeDataClaimD()赔付退费表的财务付费操作
	 * PayFeeDataBonusGetJ()红利给付实付表的业务付费操作； PayFeeDataBonusGetD()红利给付实付表的财务付费操作； **************
	 */
	private boolean PayFeeDataD()
	{
		if(!getEndorsePay())
		{
			return false;
		}
		//由于生存金领取涉及贷款的清偿，所以部分放到保全实付凭证中进行提取
		if(!PayFeeDataDrawD())
		{
			return false;
		}
		if(!PayFeeDataTempD())
		{
			return false;
		}
		if(!PayFeeDataClaimD())
		{
			return false;
		}
		if(!PayFeeDataBonusGetD())
		{
			return false;
		}
		if(!PayFeeDataOtherGetD())
		{
			return false;
		}

		return true;
	}

	private boolean PayFeeDataJ()
	{
		if(!PayFeeDataTempJ())
		{
			return false;
		}
		if(!PayFeeDataBonusGetJ())
		{
			return false;
		}
		if(!PayFeeDataOtherJ())
		{
			return false;
		}

		return true;
	}

	// 对批改补退费表业务付费的操作
	private boolean PayFeeDataEndordseJ()
	{
		String tSql = "select * from LJAGetEndorse " 
					+ "where MakeDate='?mToday?' and ManageCom like concat('?cManageCom?','%') " 
					+ "and FeeFinaType not in ( 'LX', 'BF', 'GB', 'HK', 'HD', 'RV','TM','CM','BD') ";
		SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
		sqlbv22.sql(tSql);
		sqlbv22.put("mToday", mToday);
		sqlbv22.put("cManageCom", cManageCom);
		logger.debug(tSql);
		LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
		LJAGetEndorseSet tLJAGetEndorseSet = tLJAGetEndorseDB.executeQuery(sqlbv22);
		for(int i = 0; i < tLJAGetEndorseSet.size(); i++)
		{
			LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
			tLJAGetEndorseSchema = tLJAGetEndorseSet.get(i + 1);
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJAGetEndorseSchema.getActuGetNo());

			if(tLJAGetEndorseSchema.getGetMoney() == 0)
			{
				dealError("LJAGetEndorse", tLJAGetEndorseSchema.getActuGetNo(), tLJAGetEndorseSchema.getPolNo(), "004",
						mVoucherType+mMatchID+i, "保全应付金额为零");
				continue;
			}
			//由于保全的回滚业务产生相应的金额为负数，所以该校验屏蔽掉
			initVar();
			mMatchID++;
			mPolNo = tLJAGetEndorseSchema.getPolNo();
			mContNo = tLJAGetEndorseSchema.getContNo();
			mBussNo = tLJAGetEndorseSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetEndorseSchema.getMakeDate();
			mSegment2 = tLJAGetEndorseSchema.getManageCom();
			mManageCom = tLJAGetEndorseSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetEndorseSchema.getGetMoney()));
			double tYHSL = 0;
			//针对保全团单不到个单的特殊处理
			if("00000000000000000000".equals(tLJAGetEndorseSchema.getContNo()))
			{
//				if(getGrpPol(tLJAGetEndorseSchema.getGrpPolNo(),mVoucherType+mMatchID+i)==null)
				//zy  2009-07-27 由于保全有些项目针对合同，grppolno记得是20个0，所以调整查询逻辑
				if(!getEGrpPol(tLJAGetEndorseSchema.getGrpContNo(),tLJAGetEndorseSchema.getRiskCode(),mVoucherType+mMatchID+i))
						continue;
			}	
			else
			{
			if(!this.setPolInfo(mContNo, tLJAGetEndorseSchema.getRiskCode(),"LJAGetEndorse", mVoucherType+mMatchID+i))
				
				continue;
			}
			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					if(tLJAGetEndorseSchema.getFeeFinaType().equals("TF"))
					{
						if(mPayIntv == 0 || mPayIntv == -1)
						{
							mBillID = "05010";
							mAccountCode = "保费收入-趸缴"; // 保全退费
						}
						else
						{
							mBillID = "05011";
							mAccountCode = "保费收入-首年首期";
						}
//						String ttmoney = "-" + this.tmoney;
						String ttmoney =new DecimalFormat("0.00").format(new Double(0-tLJAGetEndorseSchema.getGetMoney()));
						mEnteredDR = "";
						mEnteredCR = String.valueOf(ttmoney);
					}
					else
					{
						if(tLJAGetEndorseSchema.getFeeFinaType().equals("TB"))
						{
							mBillID = "0500";
							mAccountCode = "退保金"; // 保全退保金
						}

						else if(tLJAGetEndorseSchema.getFeeFinaType().equals("YE"))
						{
							mBillID = "0502";
							mAccountCode = "预收保费"; // 退余额
						}

						else if(tLJAGetEndorseSchema.getFeeFinaType().equals("DK")) // 借款
						{
							mBillID = "0503";
							mAccountCode = "贷款-保户质押贷款－本金";
						}

						else if(tLJAGetEndorseSchema.getFeeFinaType().equals("DJ")) // 垫交
						{
							mBillID = "0504";
							mAccountCode = "垫交保费";
						}
						else if ("LJTF".equals(tLJAGetEndorseSchema.getFeeFinaType())) //红利
			            {
			                mBillID = "0511";
			                mAccountCode = "应付保单红利-累计生息";		                   
			            }
						else
						{
							dealError("LJAGetEndorse", mBussNo, mPolNo, "004", mVoucherType+mMatchID+i, "保全财务类型为"
									+ tLJAGetEndorseSchema.getFeeFinaType() + "，不支持该类型的凭证提取");
							break;
						}

						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";
					}
					mHeadDescription = mInsuredName + mAccountCode;
				}
				else
				{
					if(tLJAGetEndorseSchema.getFeeFinaType().equals("TB"))
					{
						mBillID = "0505";
						mAccountCode = "应付业务支出-退保金";
					}
					else if(tLJAGetEndorseSchema.getFeeFinaType().equals("DK"))
					{
						String rSql = "select (case when sum(getmoney) is not null then sum(getmoney) else 0 end) from ljagetendorse where "
									+ "ActuGetNo='?ActuGetNo?' " 
									+ "and PolNo='?PolNo?' "
									+ "and EndorsementNo='?EndorsementNo?' "
									+ "and FeeOperationType='LN' and FeeFinaType='RV'"; // 印花税
						SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
						sqlbv23.sql(rSql);
						sqlbv23.put("ActuGetNo", tLJAGetEndorseSchema.getActuGetNo());
						sqlbv23.put("PolNo", tLJAGetEndorseSchema.getPolNo());
						sqlbv23.put("EndorsementNo", tLJAGetEndorseSchema.getEndorsementNo());
						ExeSQL tExeSQL = new ExeSQL();
						tYHSL = Double.valueOf(tExeSQL.execSQL(sqlbv23).GetText(1, 1)).doubleValue();
						mBillID = "0506";
						mAccountCode = "应付业务支出-保单贷款";
						tmoney = new DecimalFormat("0.00")
								.format(new Double(tLJAGetEndorseSchema.getGetMoney() - tYHSL));
						if(Double.parseDouble(this.tmoney) ==0)
						{
							dealError("ljagetendorse", tLJAGetEndorseSchema.getActuGetNo(), tLJAGetEndorseSchema
									.getPolNo(), "004", mVoucherType+mMatchID+i, "贷款金额合计为零");
							continue;
						}
					}
					else
					// TF,YE,DJ 都贷保全其它应付款
					{
						if("TF".equals(tLJAGetEndorseSchema.getFeeFinaType()) || "YE".equals(tLJAGetEndorseSchema.getFeeFinaType())
								|| "DJ".equals(tLJAGetEndorseSchema.getFeeFinaType()))
						{
							if("WT".equals(tLJAGetEndorseSchema.getFeeOperationType()))
							{
								mBillID = "0509";
								mAccountCode = "应付业务支出-犹豫期撤单";
	
							}
							else if(getRiskPeriod(mRiskCode, "005").equals("M")
									|| getRiskPeriod(mRiskCode, "005").equals("S"))
							{
								mBillID = "0510";
								mAccountCode = "应付业务支出-短期险退保";
							}
							else 
							{
								mBillID = "0507";
								mAccountCode = "应付业务支出-其他";
							}
						}
						else if ("LJTF".equals(tLJAGetEndorseSchema.getFeeFinaType())) //红利
			            {
			                mBillID = "0512";
			                mAccountCode = "应付业务支出-红利";		                   
			            }
						else
						{
							dealError("LJAGetEndorse", mBussNo, mPolNo, "004", mVoucherType+mMatchID+i, "保全财务类型为"
									+ tLJAGetEndorseSchema.getFeeFinaType() + "，不支持该类型的凭证提取");
						}
					}

					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
					mHeadDescription = mInsuredName + mAccountCode;
				}

				if(isExitInTab(tLJAGetEndorseSchema.getActuGetNo(), tLJAGetEndorseSchema.getPolNo(), mBillID))
					break;

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);
				dealLITranInfo();
			}
			if(tLJAGetEndorseSchema.getFeeFinaType().equals("DK"))
			{
				tmoney = new DecimalFormat("0.00").format(new Double(tYHSL));
				if(Double.parseDouble(this.tmoney) ==0)
				{
					dealError("ljagetendorse", tLJAGetEndorseSchema.getActuGetNo(), tLJAGetEndorseSchema.getPolNo(),
							"004", mVoucherType+mMatchID+i, "贷款金额合计为零");
					continue;
				}
				mBillID = "0508";
				mAccountCode = "应交税费-印花税";
				mEnteredDR = "";
				mEnteredCR = String.valueOf(tmoney);
				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);
			}
		}
		return true;
	}

	private LJAGetEndorseSet getAccEndorse(int Flag, double tSumMoney, LJAGetEndorseSet tLJAGetEndorseSet)
	{
		LJAGetEndorseSet tReturnEndorseSet = new LJAGetEndorseSet();
		if(tSumMoney == 0.00)
		{
			return tReturnEndorseSet;
		}
		double[] a = new double[tLJAGetEndorseSet.size()];
		for(int i = 0; i < tLJAGetEndorseSet.size(); i++)
			a[i] = tLJAGetEndorseSet.get(i + 1).getGetMoney();
		Arrays.sort(a);
		if(Flag == 1) // 降序排列
		{
			double[] b = new double[tLJAGetEndorseSet.size()];
			for(int i = 0; i < a.length; i++)
			{
				b[i] = a[i];
			}
			for(int i = 0; i < a.length; i++)
			{
				a[i] = b[a.length - i - 1];
			}
		}

		double tSumArr = 0;
		for(int i = 0; i < a.length; i++)
		{
			LJAGetEndorseSchema tLJAGetEndorseSchema = null;
			for(int j = 1; j <= tLJAGetEndorseSet.size(); j++)
			{
				tLJAGetEndorseSchema = tLJAGetEndorseSet.get(j);
				if(tLJAGetEndorseSchema.getGetMoney() == a[i])
				{
					tLJAGetEndorseSet.remove(tLJAGetEndorseSchema);
					break;
				}
			}

			tSumArr = tSumArr + a[i];
			if(tSumArr > tSumMoney)
			{
				double ttBalance = tSumArr - tSumMoney;
				double tBalance = tLJAGetEndorseSchema.getGetMoney() - ttBalance;
				if(tBalance < 0.01)
					break;
				tLJAGetEndorseSchema.setGetMoney(tBalance);
				tReturnEndorseSet.add(tLJAGetEndorseSchema);
				LJAGetEndorseSchema ttLJAGetEndorseSchema = new LJAGetEndorseSchema();
				ttLJAGetEndorseSchema.setSchema(tLJAGetEndorseSchema);
				ttLJAGetEndorseSchema.setGetMoney(ttBalance);
				tLJAGetEndorseSet.add(ttLJAGetEndorseSchema);
				break;
			}
			else
			{
				tReturnEndorseSet.add(tLJAGetEndorseSchema);
			}
		}

		return tReturnEndorseSet;
	}

	private LJTempFeeClassSet getAccTemp(int Flag, double tSumMoney, LJTempFeeClassSet tLJTempFeeClassSet)
	{
		LJTempFeeClassSet tReturnTempSet = new LJTempFeeClassSet();
		if(tSumMoney == 0.00)
		{
			return tReturnTempSet;
		}
		double[] a = new double[tLJTempFeeClassSet.size()];
		for(int i = 0; i < tLJTempFeeClassSet.size(); i++)
			a[i] = tLJTempFeeClassSet.get(i + 1).getPayMoney();
		Arrays.sort(a);
		if(Flag == 1) // 降序排列
		{
			double[] b = new double[tLJTempFeeClassSet.size()];
			for(int i = 0; i < a.length; i++)
			{
				b[i] = a[i];
			}
			for(int i = 0; i < a.length; i++)
			{
				a[i] = b[a.length - i - 1];
			}

		}

		double tSumArr = 0;
		for(int i = 0; i < a.length; i++)
		{
			LJTempFeeClassSchema tLJTempFeeClassSchema = null;
			for(int j = 1; j <= tLJTempFeeClassSet.size(); j++)
			{
				tLJTempFeeClassSchema = tLJTempFeeClassSet.get(j);
				if(tLJTempFeeClassSchema.getPayMoney() == a[i])
				{
					tLJTempFeeClassSet.remove(tLJTempFeeClassSchema);
					break;
				}
			}

			tSumArr = tSumArr + a[i];
			if(tSumArr > tSumMoney)
			{
				double ttBalance = tSumArr - tSumMoney;
				double tBalance = tLJTempFeeClassSchema.getPayMoney() - ttBalance;
				tLJTempFeeClassSchema.setPayMoney(tBalance);
				tReturnTempSet.add(tLJTempFeeClassSchema);
				LJTempFeeClassSchema ttLJTempFeeClassSchema = new LJTempFeeClassSchema();
				ttLJTempFeeClassSchema.setSchema(tLJTempFeeClassSchema);
				ttLJTempFeeClassSchema.setPayMoney(ttBalance);
				tLJTempFeeClassSet.add(ttLJTempFeeClassSchema);
				break;
			}
			else
			{
				tReturnTempSet.add(tLJTempFeeClassSchema);
			}
		}

		return tReturnTempSet;
	}

	// 保全实付
	private boolean getEndorsePay()
	{
		//zy 增加网银代付的处理，处理规则与银行转账一致
//		String tSql = "select * from LJAGet a where "
//					+ "ConfDate='"+ mToday+ "' and ManageCom like '"+ cManageCom+ "%' "
//					+ "and OtherNoType='10' and not exists(select 1 from ljabonusget where actugetno=a.actugetno) "
//					+ " and (paymode not in ( '4','9') or (paymode in ( '4','9') and exists(select 1 from ldbank where "
//					+ "bankcode = a.bankcode and rpad(trim(comcode),6,'0') = substr(a.managecom,0,6)))) ";
		//zy 2009-06-26 由于网银代付的银行在ldbank中没有描述，所以查询逻辑同一调整为ldcode
		String tSql = "select * from LJAGet a where "
			+ "ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') "
			+ "and OtherNoType='10' and not exists(select 1 from ljabonusget where actugetno=a.actugetno) "
			+ " and (paymode not in ( '4','9') or (paymode in ( '4','9') and exists(select 1 from ldcode where "
			+ "codetype='bank' and trim(code) = trim(a.bankcode) and rpad(trim(comcode),6,'0') = substr(a.managecom,1,6)))) ";
		logger.debug(tSql);
		SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
		sqlbv24.sql(tSql);
		sqlbv24.put("mToday", mToday);
		sqlbv24.put("cManageCom", cManageCom);
		LJAGetDB tLJAGetDB = new LJAGetDB();
		LJAGetSet tLJAGetSet = new LJAGetSet();
		tLJAGetSet = tLJAGetDB.executeQuery(sqlbv24);
		for(int i = 1; i <= tLJAGetSet.size(); i++)
		{
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			tLJAGetSchema.setSchema(tLJAGetSet.get(i));
			this.initVar();
			mMatchID++;
			mBussNo = tLJAGetSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetSchema.getConfDate();
			mSegment2 = tLJAGetSchema.getManageCom();
			mManageCom = tLJAGetSchema.getManageCom().substring(0, 6);
//			if(tLJAGetSchema.getSumGetMoney() == 0)
//			{
//				dealError("LJAGet", mBussNo, tLJAGetSchema.getOtherNo(), "004", mVoucherType+mMatchID+i, "保全实付金额为0");
//				continue;
//			}            
			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					if(!getEndorsePayCode(tLJAGetSchema.getActuGetNo(),tLJAGetSchema.getManageCom(),tLJAGetSchema.getConfDate(),"G"))
						break;

				}
				else
				{
					//当出现坏账类型的财务数据时，总表的数据为0，不做下列科目的处理
					if(tLJAGetSchema.getSumGetMoney() > 0 || tLJAGetSchema.getSumGetMoney() < 0)
					{
						if(tLJAGetSchema.getPayMode() != null)
						{
							if("1".equals(tLJAGetSchema.getPayMode()))
							{
								mBillID = "0805";
								mAccountCode = "库存现金-人民币-业务";
							}
							else
							{
								if(tLJAGetSchema.getPayMode().equals("5")) // 内部转账
								{
									mBillID = "0803";
									mAccountCode = "应付业务支出-内部转帐"; // 内部转账贷
								}
								else
								{
									mBillID = "0804";
									mAccountCode = "银行存款-活期-人民币"; // 银行存款支
									mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
								}
							}
						}
						else
						{
							mBillID = "0806";
							mAccountCode = "库存现金-人民币-业务";
						}
			            tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetSchema.getSumGetMoney()) );
						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
						mHeadDescription = mAccountCode + mInsuredName;
					
						if(isExitInTab(tLJAGetSchema.getActuGetNo(), mPolNo, mBillID))
							continue;
		
						OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
						if(tOFInterfaceSchema != null)
							mOFInterfaceSet.add(tOFInterfaceSchema);
						dealLITranInfo();
					}
				}
			}


		}

//		String sql = "select * from LJAGet a where "
//				   + "ConfDate='"+ mToday+ "' and ManageCom like '"+ cManageCom+ "%' "
//				   + "and OtherNoType='10'  and not exists(select 1 from ljabonusget where actugetno=a.actugetno)  "
//				   + "and paymode in ( '4','9') and bankcode is not null and exists(select 1 from ldbank where bankcode = a.bankcode and "
//				   + "rpad(trim(comcode),6,'0') <> substr(a.managecom,0,6)) ";
		String sql = "select * from LJAGet a where "
			   + "ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') "
			   + "and OtherNoType='10'  and not exists(select 1 from ljabonusget where actugetno=a.actugetno)  "
			   + "and paymode in ( '4','9') and bankcode is not null and exists(select 1 from ldcode where codetype='bank' and trim(code) = trim(a.bankcode) and "
			   + "rpad(trim(comcode),6,'0') <> substr(a.managecom,1,6)) ";
		SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
		sqlbv25.sql(sql);
		sqlbv25.put("mToday", mToday);
		sqlbv25.put("cManageCom", cManageCom);
		logger.debug(sql);
		tLJAGetDB = new LJAGetDB();
		tLJAGetSet = new LJAGetSet();
		tLJAGetSet = tLJAGetDB.executeQuery(sqlbv25);
		for(int i = 1; i <= tLJAGetSet.size(); i++)
		{
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			tLJAGetSchema.setSchema(tLJAGetSet.get(i));
//			if(tLJAGetSchema.getSumGetMoney() == 0)
//			{
//				dealError("LJAGet", tLJAGetSchema.getActuGetNo(), tLJAGetSchema.getOtherNo(), "004", mVoucherType+mMatchID+i, "保全实付金额为0");
//				continue;
//			}  


			this.initVar();
			mMatchID++;
			mBussNo = tLJAGetSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetSchema.getConfDate();
			mSegment2 = tLJAGetSchema.getManageCom();
			mManageCom = tLJAGetSchema.getManageCom().substring(0, 6);
			//支公司科目
			if(!getEndorsePayCode(tLJAGetSchema.getActuGetNo(),tLJAGetSchema.getManageCom(),tLJAGetSchema.getConfDate(),"G"))
				continue;

			if(tLJAGetSchema.getSumGetMoney() > 0 || tLJAGetSchema.getSumGetMoney() < 0)
			{
				tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetSchema.getSumGetMoney()));
				if(tLJAGetSchema.getPayMode() != null)
				{
					if(!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
					{
						if(tLJAGetSchema.getPayMode().equals("5")) // 内部转账
						{
							mBillID = "0863";
							mAccountCode = "应付业务支出-内部转帐"; 
						}
						else
						{
							mBillID = "0864";
							mAccountCode = "其他应付款-资金中转-集中批付"; // 银行存款支
							mAccountSubCode = "C" + mManageCom.substring(2, 4).concat("0000");
						}
					}
				}
				mEnteredDR = "";
				mEnteredCR = String.valueOf(tmoney);
				mHeadDescription = mAccountCode + mInsuredName;
				if(isExitInTab(tLJAGetSchema.getActuGetNo(), mPolNo, mBillID))
					continue;
	
				OFInterfaceSchema ttOFInterfaceSchema = new OFInterfaceSchema();
				ttOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(ttOFInterfaceSchema != null)
					mOFInterfaceSet.add(ttOFInterfaceSchema);
	
				dealLITranInfo();
	
				// 分公司借记科目
				// 取得机构对应的分公司
				mManageCom = tLJAGetSchema.getManageCom().substring(0, 4).concat("00");
				mSegment2 = mManageCom.concat("00");
				mBillID = "0865";
				mAccountCode = "其他应收款-资金中转-集中批付"; // 银行存款支
				mAccountSubCode = "C" + tLJAGetSchema.getManageCom().substring(2, 6).concat("00");
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = String.valueOf(tmoney);
				mEnteredCR = "";
				if(isExitInTab(tLJAGetSchema.getActuGetNo(), mPolNo, mBillID))
					continue;
	
				OFInterfaceSchema t1OFInterfaceSchema = new OFInterfaceSchema();
				t1OFInterfaceSchema = (OFInterfaceSchema)entry();
				if(t1OFInterfaceSchema != null)
					mOFInterfaceSet.add(t1OFInterfaceSchema);
	
				dealLITranInfo();
	
				// 分公司贷记科目
				mBillID = "0866";
				mAccountCode = "银行存款-活期-人民币"; // 银行存款支
				mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = "";
				mEnteredCR = String.valueOf(tmoney);
				if(isExitInTab(tLJAGetSchema.getActuGetNo(),mPolNo, mBillID))
					continue;
				OFInterfaceSchema t2OFInterfaceSchema = new OFInterfaceSchema();
				t2OFInterfaceSchema = (OFInterfaceSchema)entry();
				if(t2OFInterfaceSchema != null)
					mOFInterfaceSet.add(t2OFInterfaceSchema);
	
				dealLITranInfo();
			}
			
		}
        //查询实收总表
        String pSql = "select * from LJAPay where ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') and IncomeType = '10'";
		SQLwithBindVariables sqlbv26=new SQLwithBindVariables();
		sqlbv26.sql(pSql);
		sqlbv26.put("mToday", mToday);
		sqlbv26.put("cManageCom", cManageCom);
        logger.debug(pSql);
        LJAPayDB tLJAPayDB = new LJAPayDB();
        LJAPaySet tLJAPaySet = new LJAPaySet();
        tLJAPaySet=tLJAPayDB.executeQuery(sqlbv26);
        for (int i=1;i<=tLJAPaySet.size();i++)
        {
            LJAPaySchema tLJAPaySchema = new LJAPaySchema();
            tLJAPaySchema.setSchema(tLJAPaySet.get(i));
//			if(tLJAPaySchema.getSumActuPayMoney() == 0)
//			{
//				dealError("LJAGet", tLJAPaySchema.getPayNo(), tLJAPaySchema.getOtherNo(), "004", mVoucherType+mMatchID+i, "实收金额为0");
//				continue;
//			}  
            initVar();
            mMatchID ++ ;
            mBussNo = tLJAPaySchema.getPayNo();
            mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
            mTransDate = tLJAPaySchema.getConfDate();
            mSegment2 = tLJAPaySchema.getManageCom();
            mManageCom = tLJAPaySchema.getManageCom().substring(0,6);

			if(!getEndorsePayCode(tLJAPaySchema.getPayNo(),tLJAPaySchema.getManageCom(),tLJAPaySchema.getConfDate(),"P"))
				continue;

			if(tLJAPaySchema.getSumActuPayMoney()>0 || tLJAPaySchema.getSumActuPayMoney()<0)
			{
	            mBillID = "0909";
	            mAccountCode = "预收保费";                                       //续期暂转
	            mHeadDescription = "转预收保费";
	            tmoney = new DecimalFormat("0.00").format(new Double(tLJAPaySchema.getSumActuPayMoney()));
	            tmoney = String.valueOf(Double.parseDouble(tmoney) - tYHSLMoney);
	            mEnteredDR = String.valueOf(tmoney);
	            mEnteredCR = "";
	
	            if (isExitInTab(tLJAPaySchema.getPayNo(),mPolNo,mBillID))
	            	continue;
	            
	            OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
	            if(tOFInterfaceSchema != null)
	                mOFInterfaceSet.add(tOFInterfaceSchema);
	            dealLITranInfo();
           }
            
        }
		return true;
	}

	// 生存领取表的业务操作
	private boolean PayFeeDataDrawJ()
	{
		// 生存金的应付处理
		String tSql = "select * from LCInsureAccTrace where MakeDate='?mToday?' and ManageCom like concat('?cManageCom?','%') " 
					+ "and grpcontno='00000000000000000000' and moneytype in ('YF','EF','YFLX','EFLX')";
		SQLwithBindVariables sqlbv27=new SQLwithBindVariables();
		sqlbv27.sql(tSql);
		sqlbv27.put("mToday", mToday);
		sqlbv27.put("cManageCom", cManageCom);
		logger.debug(tSql);
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		LCInsureAccTraceSet tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv27);

		for(int i = 1; i <= tLCInsureAccTraceSet.size(); i++)
		{
			LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(i);
			if(tLCInsureAccTraceSchema.getMoney() == 0)
			{
				dealError("LCInsureAccTrace", tLCInsureAccTraceSchema.getContNo(), tLCInsureAccTraceSchema.getRiskCode(), "004",
						mVoucherType+mMatchID+i, "生存金应付金额为非正数！");
				continue;
			}

			initVar();
			mMatchID++;
			mPolNo = tLCInsureAccTraceSchema.getPolNo();
			mContNo = tLCInsureAccTraceSchema.getContNo();
			mBussNo = tLCInsureAccTraceSchema.getSerialNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLCInsureAccTraceSchema.getMakeDate();
			mSegment2 = tLCInsureAccTraceSchema.getManageCom();
			mManageCom = tLCInsureAccTraceSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLCInsureAccTraceSchema.getMoney()));
			if(!this.setPolInfo(mContNo,tLCInsureAccTraceSchema.getRiskCode(), "LCInsureAccTrace", mVoucherType+mMatchID+i))
				continue;
			LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
			mLMRiskAppDB.setRiskCode(mRiskCode);
			if(mLMRiskAppDB.getInfo())
			{
				if("1".equals(mLMRiskAppDB.getHealthType()))
				{
					dealError("LCInsureAccTrace", mBussNo, mPolNo, "013",mVoucherType+mMatchID+i, "健康委托产品不在该处进行凭证提取！");
					continue;
				}
			}
			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					if("YF".equals(tLCInsureAccTraceSchema.getMoneyType())) // 红利累计生息账户中的累计生息利息
					{
						mBillID = "0602";
						mAccountCode = "年金给付";
					}
					if("YFLX".equals(tLCInsureAccTraceSchema.getMoneyType()))
					{
						mBillID = "0601";
						mAccountCode = "利息支出-延滞领取利息";
					}
					if("EF".equals(tLCInsureAccTraceSchema.getMoneyType()))
					{
						mBillID = "0600";
						mAccountCode = "满期给付";
					}
					mEnteredDR = String.valueOf(tmoney);
					mEnteredCR = "";
				}
				else
				{
					if("YF".equals(tLCInsureAccTraceSchema.getMoneyType()) || 
						"YFLX".equals(tLCInsureAccTraceSchema.getMoneyType()) )
					{
						mBillID = "0605";
						mAccountCode = "应付业务支出-年金";
					}
					else
					{
						mBillID = "0603";
						mAccountCode = "应付业务支出-满期";
					}
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
				}

				mHeadDescription = mAccountCode + "," + mInsuredName + "生存领取费";
				if(isExitInTab(tLCInsureAccTraceSchema.getSerialNo(), tLCInsureAccTraceSchema.getPolNo(), mBillID))
					break;

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);

				dealLITranInfo();
			}
		}		
		return true;
	}

	// 生存领取表的财务付费操作
	private boolean PayFeeDataDrawD()
	{
		//zy 需要兼容5.3的生存领取数据，即ljaget表中othernotype为1
//		String tSql = "select * from LJAGet a where "
//			+ "ConfDate='"+ mToday+ "' and ManageCom like '"+ cManageCom+ "%' and OtherNoType in ('1','2') "
//			+ " and (paymode not in ( '4','9') or (paymode in ( '4','9') and exists(select 1 from ldbank where "
//			+ "bankcode = a.bankcode and rpad(trim(comcode),6,'0') = substr(a.managecom,0,6)))) ";
		String tSql = "select * from LJAGet a where "
			+ "ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') and OtherNoType in ('1','2') "
			+ " and (paymode not in ( '4','9') or (paymode in ( '4','9') and exists(select 1 from ldcode where "
				+ "codetype='bank' and trim(code) = trim(a.bankcode) and rpad(trim(comcode),6,'0') = substr(a.managecom,1,6)))) ";
		SQLwithBindVariables sqlbv28=new SQLwithBindVariables();
		sqlbv28.sql(tSql);
		sqlbv28.put("mToday", mToday);
		sqlbv28.put("cManageCom", cManageCom);
		logger.debug(tSql);
		LJAGetDB tLJAGetDB = new LJAGetDB();
		LJAGetSet tLJAGetSet = tLJAGetDB.executeQuery(sqlbv28);
		for(int i = 1; i <= tLJAGetSet.size(); i++)
		{
			LJAGetSchema tLJAGetSchema = tLJAGetSet.get(i);
//			if(tLJAGetSchema.getSumGetMoney()==0)
//			{
//				dealError("LJAGet", tLJAGetSchema.getActuGetNo(), tLJAGetSchema.getOtherNo(), "004", mVoucherType+mMatchID+i, "生存领取给付金额为非正数");
//				continue;
//			}
			initVar();
			mMatchID++;
			mBussNo = tLJAGetSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetSchema.getConfDate();
			mSegment2 = tLJAGetSchema.getManageCom();
			mManageCom = tLJAGetSchema.getManageCom().substring(0, 6);			
			if(!this.getDrawCode(mBussNo))
				continue;

			if(tLJAGetSchema.getSumGetMoney() > 0 || tLJAGetSchema.getSumGetMoney() < 0)
			{
				tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetSchema.getSumGetMoney()));
				if(tLJAGetSchema.getPayMode() != null)
				{
					if(!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
					{
						if(tLJAGetSchema.getPayMode().equals("5"))
						{
							mBillID = "0813";
							mAccountCode = "应付业务支出-内部转帐"; // 内部转账贷
						}
						else
						{
							mBillID = "0814";
							mAccountCode = "银行存款-活期-人民币"; // 银行存款支
							mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
						}
					}
					else
					{
						mBillID = "0815";
						mAccountCode = "库存现金-人民币-业务";
					}
				}
				else
				{
					mBillID = "0816";
					mAccountCode = "库存现金-人民币-业务";
				}
	
				mEnteredDR = "";
				mEnteredCR = String.valueOf(tmoney);
				mHeadDescription = mAccountCode + mInsuredName + "生存领取费";
	
	
				if(isExitInTab(tLJAGetSchema.getActuGetNo(), mPolNo, mBillID))
					break;	
				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);
	
				dealLITranInfo();
			}

		}

//		String sql = "select * from LJAGet a where "
//			   + "ConfDate='"+ mToday+ "' and ManageCom like '"+ cManageCom+ "%' and OtherNoType in ('1','2')  "
//			   + "and paymode in ( '4','9') and bankcode is not null and exists(select 1 from ldbank where bankcode = a.bankcode and "
//			   + "rpad(trim(comcode),6,'0') <> substr(a.managecom,0,6)) ";
		String sql = "select * from LJAGet a where "
			   + "ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') and OtherNoType in ('1','2')  "
			   + "and paymode in ( '4','9') and bankcode is not null and exists(select 1 from ldcode where codetype='bank' and trim(code) = trim(a.bankcode) and "
			   + "rpad(trim(comcode),6,'0') <> substr(a.managecom,1,6)) ";
		SQLwithBindVariables sqlbv29=new SQLwithBindVariables();
		sqlbv29.sql(sql);
		sqlbv29.put("mToday", mToday);
		sqlbv29.put("cManageCom", cManageCom);
		logger.debug(sql);
		tLJAGetDB = new LJAGetDB();
		tLJAGetSet = new LJAGetSet();
		tLJAGetSet = tLJAGetDB.executeQuery(sqlbv29);
		for(int i = 1; i <= tLJAGetSet.size(); i++)
		{
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			tLJAGetSchema.setSchema(tLJAGetSet.get(i));

			initVar();
			mMatchID++;
			mBussNo = tLJAGetSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetSchema.getConfDate();
			mSegment2 = tLJAGetSchema.getManageCom();
			mManageCom = tLJAGetSchema.getManageCom().substring(0, 6);
			if(!this.getDrawCode(mBussNo))
				continue;

			if(tLJAGetSchema.getSumGetMoney() > 0 || tLJAGetSchema.getSumGetMoney() < 0)
			{
				tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetSchema.getSumGetMoney()));
				if(tLJAGetSchema.getPayMode() != null)
				{
					if(!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
					{
						if(tLJAGetSchema.getPayMode().equals("5"))
						{
							mBillID = "0870";
							mAccountCode = "应付业务支出-内部转帐"; // 内部转账贷
						}
						else
						{
							mBillID = "0871";
							mAccountCode = "其他应付款-资金中转-集中批付"; // 银行存款支
							mAccountSubCode = "C" + mManageCom.substring(2, 4).concat("0000");
						}
					}
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
					mHeadDescription = mAccountCode + mInsuredName + "生存领取费";
					if(isExitInTab(tLJAGetSchema.getActuGetNo(), mPolNo, mBillID))
						break;

					OFInterfaceSchema ttOFInterfaceSchema = (OFInterfaceSchema)entry();
					if(ttOFInterfaceSchema != null)
						mOFInterfaceSet.add(ttOFInterfaceSchema);

					dealLITranInfo();
				}
			

				// 分公司借记科目
				// 取得机构对应的分公司
				mManageCom = tLJAGetSchema.getManageCom().substring(0, 4).concat("00");
				mSegment2 = mManageCom.concat("00");
				mBillID = "0872";
				mAccountCode = "其他应收款-资金中转-集中批付"; // 银行存款支
				mAccountSubCode = "C" + tLJAGetSchema.getManageCom().substring(2, 6).concat("00");
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = String.valueOf(tmoney);
				mEnteredCR = "";
				if(isExitInTab(tLJAGetSchema.getActuGetNo(),mPolNo, mBillID))
					continue;
	
				OFInterfaceSchema t1OFInterfaceSchema = new OFInterfaceSchema();
				t1OFInterfaceSchema = (OFInterfaceSchema)entry();
				if(t1OFInterfaceSchema != null)
					mOFInterfaceSet.add(t1OFInterfaceSchema);
	
				dealLITranInfo();
	
				// 分公司贷记科目
				mBillID = "0873";
				mAccountCode = "银行存款-活期-人民币"; // 银行存款支
				mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = "";
				mEnteredCR = String.valueOf(tmoney);
				if(isExitInTab(tLJAGetSchema.getActuGetNo(), mPolNo, mBillID))
					continue;
	
				OFInterfaceSchema t2OFInterfaceSchema = new OFInterfaceSchema();
				t2OFInterfaceSchema = (OFInterfaceSchema)entry();
				if(t2OFInterfaceSchema != null)
					mOFInterfaceSet.add(t2OFInterfaceSchema);
	
				dealLITranInfo();
		}
		}
		return true;
	}

	// 临时退费表的借操作
	private boolean PayFeeDataTempJ()
	{
		String tSql = "select * from LJAGetTempFee " 
					+ "where MakeDate='?mToday?' and ManageCom like concat('?cManageCom?','%')";
		SQLwithBindVariables sqlbv30=new SQLwithBindVariables();
		sqlbv30.sql(tSql);
		sqlbv30.put("mToday", mToday);
		sqlbv30.put("cManageCom", cManageCom);
		logger.debug(tSql);
		LJAGetTempFeeDB tLJAGetTempFeeDB = new LJAGetTempFeeDB();
		LJAGetTempFeeSet tLJAGetTempFeeSet = tLJAGetTempFeeDB.executeQuery(sqlbv30);

		for(int i = 1; i <= tLJAGetTempFeeSet.size(); i++)
		{
			LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
			tLJAGetTempFeeSchema = tLJAGetTempFeeSet.get(i);

			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			tLJTempFeeDB.setTempFeeNo(tLJAGetTempFeeSchema.getTempFeeNo());
			tLJTempFeeDB.setTempFeeType(tLJAGetTempFeeSchema.getTempFeeType());
			tLJTempFeeDB.setRiskCode(tLJAGetTempFeeSchema.getRiskCode());
			if(!tLJTempFeeDB.getInfo())
			{
				dealError("LJAGetTempFee", tLJAGetTempFeeSchema.getActuGetNo(), tLJAGetTempFeeSchema.getTempFeeNo(), "002", mVoucherType+mMatchID+i, "暂收退费对应的收据号为"
						+ tLJAGetTempFeeSchema.getTempFeeNo() + "的暂收信息不存在！");
				continue;
			}
			if("CM".equals(tLJAGetTempFeeSchema.getFeeFinaType())) // 排除健康委托产品
			{
				dealError("LJAGetTempFee", tLJAGetTempFeeSchema.getActuGetNo(), tLJAGetTempFeeSchema.getTempFeeNo(), "013", mVoucherType+mMatchID+i, "健康委托产品退费不在该处提取凭证！");
				continue;
			}
			if(tLJAGetTempFeeSchema.getGetMoney() <= 0)
			{
				dealError("LJAGetTempFee", tLJAGetTempFeeSchema.getActuGetNo(), tLJTempFeeDB.getOtherNo(), "004",
						"007", "暂收退费的金额为非正数！");
				continue;
			}
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJAGetTempFeeSchema.getActuGetNo());
			if(!tLJAGetDB.getInfo())
			{
				dealError("LJAGetTempFee", tLJAGetTempFeeSchema.getActuGetNo(), tLJAGetTempFeeSchema.getTempFeeNo(), "002", mVoucherType+mMatchID+i,  
						tLJAGetTempFeeSchema.getActuGetNo() + "的实付总表信息不存在！");
				continue;
			}
			initVar();
			mMatchID++;
			mPolNo = tLJTempFeeDB.getOtherNo();
			mContNo = tLJTempFeeDB.getOtherNo();
			mBussNo = tLJAGetTempFeeSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetTempFeeSchema.getMakeDate();
			mRiskCode = tLJAGetTempFeeSchema.getRiskCode();
			mSegment2 = tLJAGetTempFeeSchema.getManageCom();
			mManageCom = tLJAGetTempFeeSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetTempFeeSchema.getGetMoney()));

			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					if("1".equals(tLJAGetTempFeeSchema.getTempFeeType()))
					{
						mBillID = "0700";
						mAccountCode = "暂收保费"; // 退首期保费
					}
					else
					{
						mBillID = "0701";
						mAccountCode = "预收保费"; // 退续期保费
					}

					mEnteredDR = String.valueOf(tmoney);
					mEnteredCR = "";
				}
				else
				{
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
					mBillID = "0702";
					mAccountCode = "应付业务支出-暂收退费";
				}

				if(tLJAGetTempFeeSchema.getAPPntName() != null)
					mHeadDescription = mAccountCode + mInsuredName;
				else
					mHeadDescription = mAccountCode;

				if(isExitInTab(mBussNo, mPolNo, mBillID))
					break;

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);

				dealLITranInfo();
			}
		}
		return true;
	}

	// 临时退费表的代操作
	private boolean PayFeeDataTempD()
	{
//		String tSql = "select * from LJAGetTempFee a "
//					+ "where ConfDate='"+ mToday+ "' and ManageCom like '"+ cManageCom+ "%' "
//					+ "and exists(select 1 from ljaget b where actugetno = a.actugetno "
//					+ "and (paymode not in ( '4','9') or (paymode in ( '4','9') and exists(select 1 from ldbank "
//					+ "where bankcode = b.bankcode and rpad(trim(comcode),6,'0') = substr(b.managecom,0,6)))))";
		String tSql = "select * from LJAGetTempFee a "
			+ "where ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') "
			+ "and exists(select 1 from ljaget b where actugetno = a.actugetno "
			+ "and (paymode not in ( '4','9') or (paymode in ( '4','9') and exists(select 1 from ldcode "
			+ "where codetype='bank' and trim(code) = trim(b.bankcode) and rpad(trim(comcode),6,'0') = substr(b.managecom,1,6)))))";
		logger.debug(tSql);
		SQLwithBindVariables sqlbv31=new SQLwithBindVariables();
		sqlbv31.sql(tSql);
		sqlbv31.put("mToday", mToday);
		sqlbv31.put("cManageCom", cManageCom);
		LJAGetTempFeeDB tLJAGetTempFeeDB = new LJAGetTempFeeDB();
		LJAGetTempFeeSet tLJAGetTempFeeSet = tLJAGetTempFeeDB.executeQuery(sqlbv31);
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		for(int i = 0; i < tLJAGetTempFeeSet.size(); i++)
		{
			LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
			tLJAGetTempFeeSchema = tLJAGetTempFeeSet.get(i + 1);
			if(tLJAGetTempFeeSchema.getGetMoney() <= 0)
			{
				dealError("LJAGetTempFee", tLJAGetTempFeeSchema.getActuGetNo(), tLJAGetTempFeeSchema.getTempFeeNo(), "004",
						mVoucherType+mMatchID+i,"暂收退费金额为非正数");
				continue;
			}
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJAGetTempFeeSchema.getActuGetNo());
			if(!tLJAGetDB.getInfo())
			{
				dealError("LJAGetTempFee", tLJAGetTempFeeSchema.getActuGetNo(), tLJAGetTempFeeSchema.getTempFeeNo(), "002",
						mVoucherType+mMatchID+i, "LJAGet不存在LJAGetTempFee相应的记录");
				continue;
			}
			tLJAGetSchema.setSchema(tLJAGetDB.getSchema());

			LJTempFeeDB mLJTempFeeDB = new LJTempFeeDB();
			mLJTempFeeDB.setTempFeeNo(tLJAGetTempFeeSchema.getTempFeeNo());
			mLJTempFeeDB.setTempFeeType(tLJAGetTempFeeSchema.getTempFeeType());
			mLJTempFeeDB.setRiskCode(tLJAGetTempFeeSchema.getRiskCode());
			if(!mLJTempFeeDB.getInfo())
			{
				dealError("LJAGetTempFee", tLJAGetTempFeeSchema.getActuGetNo(), tLJAGetTempFeeSchema.getTempFeeNo(), "002",
						mVoucherType+mMatchID+i, "LJTempFee不存在LJAGetTempFee对应的记录");
				continue;
			}

			initVar();
			mMatchID++;
			mBussNo = tLJAGetTempFeeSchema.getActuGetNo();
			mPolNo = mLJTempFeeDB.getOtherNo();
			mContNo = mLJTempFeeDB.getOtherNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetTempFeeSchema.getConfDate();
			mSegment2 = tLJAGetTempFeeSchema.getManageCom();
			mManageCom = tLJAGetTempFeeSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetTempFeeSchema.getGetMoney()));
			mSaleChnl = tranSaleChnl(tLJAGetSchema.getSaleChnl(), tLJAGetSchema.getAgentCode(), mLJTempFeeDB.getOtherNo());
			mRiskCode = tLJAGetTempFeeSchema.getRiskCode();

			if(tLJAGetTempFeeSchema.getGetMoney() > 0)
			{
				for(int j = 1; j <= 2; j++)
				{
					if(j == 1)
					{
						mAccountCode = "应付业务支出-暂收退费";
						mBillID = "0820";
						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";
					}
					else
					{
						if(tLJAGetSchema.getPayMode() != null)
						{
							if(!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
							{
								if(tLJAGetSchema.getPayMode().equals("5"))
								{
									mBillID = "0821";
									mAccountCode = "应付业务支出-内部转帐"; // 内部转账贷
								}
								else
								{
									mBillID = "0822";
									mAccountCode = "银行存款-活期-人民币"; // 银行存款支
									mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
								}
							}
							else
							{
								mBillID = "0823";
								mAccountCode = "库存现金-人民币-业务";
							}
						}
						else
						{
							mBillID = "0824";
							mAccountCode = "库存现金-人民币-业务";
						}

						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
					}
					mHeadDescription = mAccountCode;
					if(isExitInTab(tLJAGetTempFeeSchema.getActuGetNo(), mPolNo, mBillID))
						break;

					OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
					if(tOFInterfaceSchema != null)
						mOFInterfaceSet.add(tOFInterfaceSchema);

					dealLITranInfo();
				}
			}
		}

//		String sql = "select * from LJAGetTempFee a "
//					+ "where ConfDate='"+ mToday+ "' and ManageCom like '"+ cManageCom+ "%' "
//					+ "and exists(select 1 from ljaget b where actugetno = a.actugetno and paymode in ( '4','9') "
//					+ "and (bankcode is not null and exists(select 1 from ldbank where bankcode = b.bankcode "
//					+ "and rpad(trim(comcode),6,'0') <> substr(b.managecom,0,6))))";
		String sql = "select * from LJAGetTempFee a "
			+ "where ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') "
			+ "and exists(select 1 from ljaget b where actugetno = a.actugetno and paymode in ( '4','9') "
			+ "and (bankcode is not null and exists(select 1 from ldcode where codetype='bank' and trim(code) = trim(b.bankcode) "
			+ "and rpad(trim(comcode),6,'0') <> substr(b.managecom,1,6))))";
		SQLwithBindVariables sqlbv32=new SQLwithBindVariables();
		sqlbv32.sql(sql);
		sqlbv32.put("mToday", mToday);
		sqlbv32.put("cManageCom", cManageCom);
		logger.debug(sql);
		tLJAGetTempFeeDB = new LJAGetTempFeeDB();
		tLJAGetTempFeeSet = tLJAGetTempFeeDB.executeQuery(sqlbv32);
		tLJAGetSchema = new LJAGetSchema();
		for(int i = 0; i < tLJAGetTempFeeSet.size(); i++)
		{
			LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
			tLJAGetTempFeeSchema = tLJAGetTempFeeSet.get(i + 1);
			if(tLJAGetTempFeeSchema.getGetMoney() <= 0)
			{
				dealError("LJAGetTempFee", tLJAGetTempFeeSchema.getActuGetNo(), tLJAGetTempFeeSchema.getTempFeeNo(), "004",
						mVoucherType+mMatchID+i,"暂收退费金额为非正数");
				continue;
			}
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJAGetTempFeeSchema.getActuGetNo());
			if(!tLJAGetDB.getInfo())
			{
				dealError("LJAGetTempFee", tLJAGetTempFeeSchema.getActuGetNo(), tLJAGetTempFeeSchema.getTempFeeNo(), "002",
						mVoucherType+mMatchID+i, "LJAGet不存在LJAGetTempFee相应的记录");
				continue;
			}
			tLJAGetSchema.setSchema(tLJAGetDB.getSchema());

			LJTempFeeDB mLJTempFeeDB = new LJTempFeeDB();
			mLJTempFeeDB.setTempFeeNo(tLJAGetTempFeeSchema.getTempFeeNo());
			mLJTempFeeDB.setTempFeeType(tLJAGetTempFeeSchema.getTempFeeType());
			mLJTempFeeDB.setRiskCode(tLJAGetTempFeeSchema.getRiskCode());
			if(!mLJTempFeeDB.getInfo())
			{
				dealError("LJAGetTempFee", tLJAGetTempFeeSchema.getActuGetNo(), tLJAGetTempFeeSchema.getTempFeeNo(), "002",
						mVoucherType+mMatchID+i, "LJTempFee不存在LJAGetTempFee对应的记录");
				continue;
			}

			initVar();
			mMatchID++;
			mBussNo = tLJAGetTempFeeSchema.getActuGetNo();
			mPolNo = mLJTempFeeDB.getOtherNo();
			mContNo = mLJTempFeeDB.getOtherNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetTempFeeSchema.getConfDate();
			mSegment2 = tLJAGetTempFeeSchema.getManageCom();
			mManageCom = tLJAGetTempFeeSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetTempFeeSchema.getGetMoney()));
			mSaleChnl = tranSaleChnl(tLJAGetSchema.getSaleChnl(), tLJAGetSchema.getAgentCode(), mLJTempFeeDB.getOtherNo());
			mRiskCode = tLJAGetTempFeeSchema.getRiskCode();

			if(tLJAGetTempFeeSchema.getGetMoney() > 0)
			{
				for(int j = 1; j <= 2; j++)
				{
					if(j == 1)
					{
						mAccountCode = "应付业务支出-暂收退费";
						mBillID = "0874";
						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";
					}
					else
					{
						if(tLJAGetSchema.getPayMode() != null)
						{
							if(!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
							{
								if(tLJAGetSchema.getPayMode().equals("5"))
								{
									mBillID = "0875";
									mAccountCode = "应付业务支出-内部转帐"; // 内部转账贷
								}
								else
								{
									mBillID = "0876";
									mAccountCode = "其他应付款-资金中转-集中批付"; // 银行存款支
									mAccountSubCode = "C" + mManageCom.substring(2, 4).concat("0000");
								}
							}
						}

						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
					}
					mHeadDescription = mAccountCode;
					if(isExitInTab(tLJAGetTempFeeSchema.getActuGetNo(), mPolNo, mBillID))
						break;

					OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
					if(tOFInterfaceSchema != null)
						mOFInterfaceSet.add(tOFInterfaceSchema);

					dealLITranInfo();
				}

				// 分公司借记科目
				// 取得机构对应的分公司
				mManageCom = tLJAGetTempFeeSchema.getManageCom().substring(0, 4).concat("00");
				mSegment2 = mManageCom.concat("00");
				mBillID = "0877";
				mAccountCode = "其他应收款-资金中转-集中批付"; // 银行存款支
				mAccountSubCode = "C" + tLJAGetTempFeeSchema.getManageCom().substring(2, 6).concat("00");
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = String.valueOf(tmoney);
				mEnteredCR = "";
				if(isExitInTab(mBussNo, mPolNo, mBillID))
					continue;

				OFInterfaceSchema t1OFInterfaceSchema = (OFInterfaceSchema)entry();
				if(t1OFInterfaceSchema != null)
					mOFInterfaceSet.add(t1OFInterfaceSchema);

				dealLITranInfo();

				// 分公司贷记科目
				mBillID = "0878";
				mAccountCode = "银行存款-活期-人民币"; // 银行存款支
				mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = "";
				mEnteredCR = String.valueOf(tmoney);
				if(isExitInTab(mBussNo, mPolNo, mBillID))
					continue;

				OFInterfaceSchema t2OFInterfaceSchema = new OFInterfaceSchema();
				t2OFInterfaceSchema = (OFInterfaceSchema)entry();
				if(t2OFInterfaceSchema != null)
					mOFInterfaceSet.add(t2OFInterfaceSchema);

				dealLITranInfo();
			}
		}
		return true;
	}

	// 理赔退费表的代操作
	private boolean PayFeeDataClaimD()
	{
//		String tSql = "select * from LJAGet a "
//					+ "where ConfDate='"+ mToday+ "' and ManageCom like '"+ cManageCom+ "%' "
//					+ "and OtherNoType='5' "
//					+ "and (paymode not in ( '4','9') or (paymode in ( '4','9') and exists(select 1 from ldbank "
//					+ "where bankcode = a.bankcode and rpad(trim(comcode),6,'0') = substr(a.managecom,0,6))))";
		String tSql = "select * from LJAGet a "
			+ "where ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') "
			+ "and OtherNoType='5' "
			+ "and (paymode not in ( '4','9') or (paymode in ( '4','9') and exists(select 1 from ldcode "
			+ "where codetype='bank' and trim(code) = trim(a.bankcode) and rpad(trim(comcode),6,'0') = substr(a.managecom,1,6))))";
		SQLwithBindVariables sqlbv33=new SQLwithBindVariables();
		sqlbv33.sql(tSql);
		sqlbv33.put("mToday", mToday);
		sqlbv33.put("cManageCom", cManageCom);
		logger.debug(tSql);
		LJAGetSet tLJAGetSet = new LJAGetSet();
		LJAGetDB tLJAGetDB = new LJAGetDB();
		tLJAGetSet = tLJAGetDB.executeQuery(sqlbv33);


		for(int i = 1; i <= tLJAGetSet.size(); i++)
		{
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			tLJAGetSchema = tLJAGetSet.get(i);
			initVar();
			mMatchID++;

			mBussNo = tLJAGetSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetSchema.getConfDate();
			mSegment2 = tLJAGetSchema.getManageCom();
			mManageCom = tLJAGetSchema.getManageCom().substring(0, 6);

//			if(tLJAGetSchema.getSumGetMoney() == 0)
//			{
//				dealError("LJAGet", mBussNo, tLJAGetSchema.getOtherNo(), "004", mVoucherType+mMatchID+i, "理赔实付金额为0");
//				continue;
//			}   


			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					if(!getClaimCode(tLJAGetSchema.getActuGetNo(),tLJAGetSchema.getManageCom(),tLJAGetSchema.getConfDate(),"G"))
						break;
				}
				else
				{
					//当出现豁免垫交部分垫交的财务数据时，总表的数据为0，不做下列科目的处理
					if(tLJAGetSchema.getSumGetMoney() > 0 || tLJAGetSchema.getSumGetMoney() < 0)
					{
						if(tLJAGetSchema.getPayMode() != null)
						{
							if(!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
							{
								if(tLJAGetSchema.getPayMode().equals("5"))
								{
									mBillID = "0831";
									mAccountCode = "应付业务支出-内部转帐"; // 内部转账贷
								}
								else
								{
									mBillID = "0832";
									mAccountCode = "银行存款-活期-人民币"; // 银行存款支
									mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
								}
							}
							else
							{
								mBillID = "0833";
								mAccountCode = "库存现金-人民币-业务";
							}
						}
						else
						{
							mBillID = "0834";
							mAccountCode = "库存现金-人民币-业务";
						}
						tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetSchema.getSumGetMoney()));
						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
						mHeadDescription = mAccountCode + mInsuredName.trim();
	
						if(isExitInTab(tLJAGetSchema.getActuGetNo(), mPolNo, mBillID))
							continue;
						OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
						if(tOFInterfaceSchema != null)
							mOFInterfaceSet.add(tOFInterfaceSchema);
						dealLITranInfo();
					}
				}


			}

		}

//		String sql = "select * from LJAGet a "
//				   + "where ConfDate='"+ mToday+ "' and ManageCom like '"+ cManageCom+ "%' "
//				   + "and OtherNoType='5' and paymode in ( '4','9') "
//				   + "and bankcode is not null and exists(select 1 from ldbank where bankcode = a.bankcode "
//				   + "and rpad(trim(comcode),6,'0') <> substr(a.managecom,0,6))";
		String sql = "select * from LJAGet a "
			   + "where ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') "
			   + "and OtherNoType='5' and paymode in ( '4','9') "
			   + "and bankcode is not null and exists(select 1 from ldcode where codetype='bank' and trim(code) = trim(a.bankcode) "
			   + "and rpad(trim(comcode),6,'0') <> substr(a.managecom,1,6))";
		SQLwithBindVariables sqlbv34=new SQLwithBindVariables();
		sqlbv34.sql(sql);
		sqlbv34.put("mToday", mToday);
		sqlbv34.put("cManageCom", cManageCom);
		logger.debug(sql);
		tLJAGetSet = new LJAGetSet();
		tLJAGetDB = new LJAGetDB();
		tLJAGetSet = tLJAGetDB.executeQuery(sqlbv34);

		for(int i = 1; i <= tLJAGetSet.size(); i++)
		{
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			tLJAGetSchema = tLJAGetSet.get(i);
			initVar();
			mMatchID++;
			boolean cFlag=true;

			mBussNo = tLJAGetSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetSchema.getConfDate();
			mSegment2 = tLJAGetSchema.getManageCom();
			mManageCom = tLJAGetSchema.getManageCom().substring(0, 6);

//			if(tLJAGetSchema.getSumGetMoney() == 0)
//			{
//				dealError("LJAGet", mBussNo, tLJAGetSchema.getOtherNo(), "004", mVoucherType+mMatchID+i, "理赔实付金额为0");
//				continue;
//			} 


			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					if(!getClaimCode(tLJAGetSchema.getActuGetNo(),tLJAGetSchema.getManageCom(),tLJAGetSchema.getConfDate(),"G"))
					{
						cFlag=false;
						break;
					}
				}
				else
				{
					if(tLJAGetSchema.getSumGetMoney() > 0 || tLJAGetSchema.getSumGetMoney() < 0)
					{
						tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetSchema.getSumGetMoney()));
						mBillID = "0882";
						mAccountCode = "其他应付款-资金中转-集中批付"; // 银行存款支
						mAccountSubCode = "C" + mManageCom.substring(2, 4).concat("0000");
						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
						mHeadDescription = mAccountCode + mInsuredName.trim();
	
						if(isExitInTab(tLJAGetSchema.getActuGetNo(), mPolNo,  mBillID))
						{
							cFlag=false;
							break;
						}
	
						OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
						if(tOFInterfaceSchema != null)
							mOFInterfaceSet.add(tOFInterfaceSchema);
	
						dealLITranInfo();
					}
				}
			}

			// 分公司借记科目
			// 取得机构对应的分公司
			if(cFlag)
			{
				
				if(tLJAGetSchema.getSumGetMoney() > 0 || tLJAGetSchema.getSumGetMoney() < 0)
				{
					tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetSchema.getSumGetMoney()));
					mManageCom = tLJAGetSchema.getManageCom().substring(0, 4).concat("00");
					mSegment2 = mManageCom.concat("00");
					mBillID = "0883";
					mAccountCode = "其他应收款-资金中转-集中批付"; // 银行存款支
					mAccountSubCode = "C" + tLJAGetSchema.getManageCom().substring(2, 6).concat("00");
					mHeadDescription = mAccountCode + mInsuredName;
					mEnteredDR = String.valueOf(tmoney);
					mEnteredCR = "";
					if(isExitInTab(tLJAGetSchema.getActuGetNo(), mPolNo, mBillID))
						continue;
		
					OFInterfaceSchema t1OFInterfaceSchema = (OFInterfaceSchema)entry();
					if(t1OFInterfaceSchema != null)
						mOFInterfaceSet.add(t1OFInterfaceSchema);
		
					dealLITranInfo();
		
					// 分公司贷记科目
					mBillID = "0884";
					mAccountCode = "银行存款-活期-人民币"; // 银行存款支
					mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
					mHeadDescription = mAccountCode + mInsuredName;
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
					if(isExitInTab(tLJAGetSchema.getActuGetNo(), mPolNo, mBillID))
						continue;
		
					OFInterfaceSchema t2OFInterfaceSchema = new OFInterfaceSchema();
					t2OFInterfaceSchema = (OFInterfaceSchema)entry();
					if(t2OFInterfaceSchema != null)
						mOFInterfaceSet.add(t2OFInterfaceSchema);
		
					dealLITranInfo();
				}
		 }
		}
		//主要针对理赔回滚产生的实收总表，理赔加费核销的保费收入在保费收入凭证中进行提取
        //查询实收总表
        String pSql = "select * from LJAPay a where confdate='?mToday?' and managecom like concat('?cManageCom?','%') and incometype = '5' "
        			+ "and not exists(select 1 from ljapayperson where payno=a.payno)";
    	SQLwithBindVariables sqlbv35=new SQLwithBindVariables();
    	sqlbv35.sql(pSql);
    	sqlbv35.put("mToday", mToday);
    	sqlbv35.put("cManageCom", cManageCom);

        logger.debug(pSql);
        LJAPayDB tLJAPayDB = new LJAPayDB();
        LJAPaySet tLJAPaySet = new LJAPaySet();
        tLJAPaySet=tLJAPayDB.executeQuery(sqlbv35);
        for (int i=1;i<=tLJAPaySet.size();i++)
        {
            LJAPaySchema tLJAPaySchema = new LJAPaySchema();
            tLJAPaySchema.setSchema(tLJAPaySet.get(i));
//			if(tLJAPaySchema.getSumActuPayMoney() == 0)
//			{
//				dealError("LJAPay", tLJAPaySchema.getPayNo(), tLJAPaySchema.getOtherNo(), "004", mVoucherType+mMatchID+i, "理赔实收金额为0");
//				continue;
//			}  
            initVar();
            mMatchID ++ ;
            mBussNo = tLJAPaySchema.getPayNo();
            mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
            mTransDate = tLJAPaySchema.getConfDate();
            mSegment2 = tLJAPaySchema.getManageCom();
            mManageCom = tLJAPaySchema.getManageCom().substring(0,6);

			if(!getClaimCode(tLJAPaySchema.getPayNo(),tLJAPaySchema.getManageCom(),tLJAPaySchema.getConfDate(),"P"))
				continue;

			if(tLJAPaySchema.getSumActuPayMoney()>0 || tLJAPaySchema.getSumActuPayMoney()<0)
			{
	            mBillID = "08116";
	            mAccountCode = "预收保费";                                       //续期暂转
	            mHeadDescription = "转预收保费";
	            tmoney = new DecimalFormat("0.00").format(new Double(tLJAPaySchema.getSumActuPayMoney()));
	            mEnteredDR = String.valueOf(tmoney);
	            mEnteredCR = "";
	
	            if (isExitInTab(tLJAPaySchema.getPayNo(),mPolNo,mBillID))
	            	continue;
	            
	            OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
	            if(tOFInterfaceSchema != null)
	                mOFInterfaceSet.add(tOFInterfaceSchema);
	            dealLITranInfo();
			}
            
        }
		return true;
	}

	// 对红利给付实付表的业务操作；
	private boolean PayFeeDataBonusGetJ()
	{
		// 处理现金领取、抵交保费、增额交清、累计生息的应付处理
		String tSql = "select * from LCInsureAccTrace where MakeDate='?mToday?' and ManageCom like concat('?cManageCom?','%') " 
					+ "and grpcontno='00000000000000000000' and moneytype in ('HL','HLLX','DJLX','YCLX')";
		SQLwithBindVariables sqlbv36=new SQLwithBindVariables();
		sqlbv36.sql(tSql);
		sqlbv36.put("mToday", mToday);
		sqlbv36.put("cManageCom", cManageCom);
		logger.debug(tSql);
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		LCInsureAccTraceSet tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(sqlbv36);

		for(int i = 1; i <= tLCInsureAccTraceSet.size(); i++)
		{
			LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(i);
			if(tLCInsureAccTraceSchema.getMoney() == 0)
			{
				dealError("LCInsureAccTrace", tLCInsureAccTraceSchema.getContNo(), tLCInsureAccTraceSchema.getRiskCode(), "004",
						mVoucherType+mMatchID+i, "红利应付金额为非正数！");
				continue;
			}

			initVar();
			mMatchID++;
			mPolNo = tLCInsureAccTraceSchema.getPolNo();
			mContNo = tLCInsureAccTraceSchema.getContNo();
			mBussNo = tLCInsureAccTraceSchema.getSerialNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLCInsureAccTraceSchema.getMakeDate();
			mSegment2 = tLCInsureAccTraceSchema.getManageCom();
			mManageCom = tLCInsureAccTraceSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLCInsureAccTraceSchema.getMoney()));
			if(!this.setPolInfo(mContNo,tLCInsureAccTraceSchema.getRiskCode(), "LCInsureAccTrace", mVoucherType+mMatchID+i))
				continue;
			LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
			mLMRiskAppDB.setRiskCode(mRiskCode);
			if(mLMRiskAppDB.getInfo())
			{
				if("1".equals(mLMRiskAppDB.getHealthType()))
				{
					dealError("LCInsureAccTrace", mBussNo, mPolNo, "013",mVoucherType+mMatchID+i, "健康委托产品不在该处进行凭证提取！");
					continue;
				}
			}
			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					if("HLLX".equals(tLCInsureAccTraceSchema.getMoneyType())) // 红利累计生息账户中的累计生息利息
					{
						mBillID = "0714";
//						mAccountCode = "保户红利支出-红利利息";
						mAccountCode = "保单红利支出-红利利息";
					}
					if("DJLX".equals(tLCInsureAccTraceSchema.getMoneyType()) || "YCLX".equals(tLCInsureAccTraceSchema.getMoneyType()))
					{
						mBillID = "0719";
						mAccountCode = "利息支出-延滞领取利息";
					}
					if("HL".equals(tLCInsureAccTraceSchema.getMoneyType()))
					{
						mBillID = "0711";
						mAccountCode = "应付保单红利-保单红利";
					}
					mEnteredDR = String.valueOf(tmoney);
					mEnteredCR = "";
				}
				else
				{
					if("HLLX".equals(tLCInsureAccTraceSchema.getMoneyType()) || 
						("HL".equals(tLCInsureAccTraceSchema.getMoneyType()) && "000001".equals(tLCInsureAccTraceSchema.getInsuAccNo())))
					{
						mBillID = "0715";
						mAccountCode = "应付保单红利-累计生息";
					}
					else
					{
						mBillID = "0712";
						mAccountCode = "应付业务支出-红利";
					}
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
				}

				mHeadDescription = mAccountCode + mInsuredName + "，保单号" + mContNo;
				if(isExitInTab(tLCInsureAccTraceSchema.getSerialNo(), tLCInsureAccTraceSchema.getPolNo(), mBillID))
					break;

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);

				dealLITranInfo();
			}
		}

	//处理累计生息的红利和增额缴清的红利
		tSql = "select * from LJABonusGet where MakeDate='?mToday?' and ManageCom like concat('?cManageCom?','%') and feefinatype in ('LJTF','HLTF')";
		SQLwithBindVariables sqlbv37=new SQLwithBindVariables();
		sqlbv37.sql(tSql);
		sqlbv37.put("mToday", mToday);
		sqlbv37.put("cManageCom", cManageCom);
		LJABonusGetDB tLJABonusGetDB = new LJABonusGetDB();
		LJABonusGetSet tLJABonusGetSet = tLJABonusGetDB.executeQuery(sqlbv37);	
		for (int i =0;i<tLJABonusGetSet.size();i++)
		{
	       LJABonusGetSchema tLJABonusGetSchema = new LJABonusGetSchema();
	       tLJABonusGetSchema = tLJABonusGetSet.get(i+1);
	       if (tLJABonusGetSchema.getGetMoney()==0)
	       {
				dealError("LJABonusGet", tLJABonusGetSchema.getContNo(), tLJABonusGetSchema.getRiskCode(), "004",
						mVoucherType+mMatchID+i, "红利应付金额为非正数！");
				continue;
	       }
	
	       initVar();
	       mMatchID ++ ;
	       mPolNo = tLJABonusGetSchema.getPolNo();
	       mBussNo =tLJABonusGetSchema.getActuGetNo();
	       mContNo =tLJABonusGetSchema.getContNo();
	       mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
	       mTransDate = tLJABonusGetSchema.getMakeDate();
	       mSegment2 = tLJABonusGetSchema.getManageCom();
	       mManageCom = tLJABonusGetSchema.getManageCom().substring(0,6);
	       tmoney = new DecimalFormat("0.00").format(new Double(tLJABonusGetSchema.getGetMoney()) );
	       LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
	       mLMRiskAppDB.setRiskCode(tLJABonusGetSchema.getRiskCode());
	       if(mLMRiskAppDB.getInfo())
	       {
	    	   if("1".equals(mLMRiskAppDB.getHealthType()))
	    	   {
	    		   dealError("LJABonusGet", mBussNo, mPolNo, "013",mVoucherType+mMatchID+i, "健康委托产品不在该处进行凭证提取！");
	    		   continue;
	    	   }
	       }
	       if (!setPolInfo(mContNo,tLJABonusGetSchema.getRiskCode(), "LJABonusGet", mVoucherType+mMatchID+i)) 
	    	   continue;	
	       for ( int j = 1; j <=2; j++)
	       {
	           if (j == 1)
	           {
	        	   if("HLTF".equals(tLJABonusGetSchema.getFeeFinaType()))
                   {
                       mBillID = "0711";
//                       mAccountCode = "应付保户红利-保单红利";               //应付保户红利保单红利
                       mAccountCode = "应付保单红利-保单红利";
		               mEnteredDR = String.valueOf(tmoney);
		               mEnteredCR = "";
                   }
	        	   if("LJTF".equals(tLJABonusGetSchema.getFeeFinaType()))
	        	   //红利累计生息账户中的红利领取借方为应付保户红利－－累计生息红利
	        	   {
	                   mBillID = "0710";
	                   mAccountCode = "应付保单红利-累计生息";
		               mEnteredDR = String.valueOf(tmoney);
		               mEnteredCR = "";
	        	   }
	           }
	           else
	           {

		               mBillID = "0712";
		               mAccountCode = "应付业务支出-红利";
		               mEnteredDR = "";
		               mEnteredCR = String.valueOf(tmoney);
	           }
	
	           mHeadDescription = mAccountCode + mInsuredName + "，保单号" + mContNo;
	           if (isExitInTab(mBussNo,mPolNo,mBillID))
	               break;
	
	           OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
	           if(tOFInterfaceSchema != null)
	               mOFInterfaceSet.add(tOFInterfaceSchema);
	
	           dealLITranInfo();
	       }
		}
		//处理清算累计生息的红利
//		tSql = "select * from LJAGetEndorse where MakeDate='"+mToday+"' and ManageCom like '"+cManageCom+"%' and feefinatype='LJTF'";
//
//		LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
//		LJAGetEndorseSet tLJAGetEndorseSet =tLJAGetEndorseDB.executeQuery(tSql);
//		for (int i =0;i<tLJAGetEndorseSet.size();i++)
//		{
//			LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
//			tLJAGetEndorseSchema = tLJAGetEndorseSet.get(i+1);
//	       if (tLJAGetEndorseSchema.getGetMoney()==0)
//	       {
//				dealError("LJAGetEndorse", tLJAGetEndorseSchema.getContNo(), tLJAGetEndorseSchema.getRiskCode(), "004",
//						mVoucherType+mMatchID+i, "红利应付金额为非正数！");
//				continue;
//	       }
//	
//	       initVar();
//	       mMatchID ++ ;
//	       mPolNo = tLJAGetEndorseSchema.getPolNo();
//	       mBussNo =tLJAGetEndorseSchema.getActuGetNo();
//	       mContNo =tLJAGetEndorseSchema.getContNo();
//	       mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
//	       mTransDate = tLJAGetEndorseSchema.getMakeDate();
//	       mSegment2 = tLJAGetEndorseSchema.getManageCom();
//	       mManageCom = tLJAGetEndorseSchema.getManageCom().substring(0,6);
//	       tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetEndorseSchema.getGetMoney()) );
//	       LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
//	       mLMRiskAppDB.setRiskCode(tLJAGetEndorseSchema.getRiskCode());
//	       if(mLMRiskAppDB.getInfo())
//	       {
//	    	   if("1".equals(mLMRiskAppDB.getHealthType()))
//	    	   {
//	    		   dealError("LJAGetEndorse", mBussNo, mPolNo, "013",mVoucherType+mMatchID+i, "健康委托产品不在该处进行凭证提取！");
//	    		   continue;
//	    	   }
//	       }
//	       if (!setPolInfo(mContNo,"", "LJAGetEndorse", mVoucherType+mMatchID+i)) 
//	    	   continue;	
//	       for ( int j = 1; j <=2; j++)
//	       {
//	           if (j == 1)
//	           {
//	        	   //红利累计生息账户中的红利领取借方为应付保户红利－－累计生息红利
//                   mBillID = "0710";
//                   mAccountCode = "应付保单红利-累计生息";
//	               mEnteredDR = String.valueOf(tmoney);
//	               mEnteredCR = "";
//	           }
//	           else
//	           {
//	               mBillID = "0712";
//	               mAccountCode = "应付业务支出-红利";
//	               mEnteredDR = "";
//	               mEnteredCR = String.valueOf(tmoney);
//	           }
//	
//	           mHeadDescription = mAccountCode + mInsuredName + "，保单号" + mContNo;
//	           if (isExitInTab(tLJAGetEndorseSchema.getActuGetNo(),tLJAGetEndorseSchema.getPolNo(),mBillID))
//	               break;
//	
//	           OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
//	           if(tOFInterfaceSchema != null)
//	               mOFInterfaceSet.add(tOFInterfaceSchema);
//	
//	           dealLITranInfo();
//	       }
//		}
		// 团单和个单的红利分开处理，团单作为汇总数据记入凭证 modify by wentao 2007-06-20
		tSql = "select grpcontno,riskcode,managecom,moneytype,sum(money) m from LCInsureAccTrace a " 
			 + "where MakeDate='?mToday?' and grppolno <> '00000000000000000000' " 
			 + "and ManageCom like concat('?cManageCom?','%') and MoneyType in('HL','HLLX') "
			 + "and not exists(select 1 from lmriskapp where riskcode=a.riskcode and healthtype='1')"
			 + "group by grpcontno,riskcode,managecom,moneytype  ";
		SQLwithBindVariables sqlbv38=new SQLwithBindVariables();
		sqlbv38.sql(tSql);
		sqlbv38.put("mToday", mToday);
		sqlbv38.put("cManageCom", cManageCom);
		logger.debug(tSql);
		SSRS tssrs = new ExeSQL().execSQL(sqlbv38);
		if(tssrs == null)
		{
			dealError("LCInsureAccTrace", "null", "null", "002", "007","查询团单帐户红利失败" + tssrs.mErrors.getFirstError());
			return true; // 继续提取别的科目段数据
		}

		for(int i = 1; i <= tssrs.getMaxRow(); i++)
		{
			initVar();
			mMatchID++;
			mPolNo = tssrs.GetText(i, 3) + "(" + mToday + ")"; // 因多期红利之间只有serialno能区分开各期数据，此时借用日期+金额类型来区分
			mContNo = tssrs.GetText(i, 1);
			mBussNo = tssrs.GetText(i, 1);
			mSegment2 = tssrs.GetText(i, 3);
			mManageCom = tssrs.GetText(i, 3).substring(0, 6);
			mAccountingDate = mToday;
			mTransDate = mToday;
			tmoney = new DecimalFormat("0.00").format(new Double(tssrs.GetText(i, 5)));
			if(Double.parseDouble(tmoney) <= 0)
			{
				dealError("LCInsureAccTrace", mBussNo, mPolNo, "004", mVoucherType+mMatchID+i,
						"团单帐户红利金额为非正数");
				continue;
			}

//			LCGrpPolBL tLCGrpPolBL = new LCGrpPolBL();
			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
//			tLCGrpPolBL.setGrpContNo(mContNo);
			String gSql="select * from lcgrppol where grpcontno='?mContNo?' and riskcode='?riskcode?'";
			SQLwithBindVariables sqlbv39=new SQLwithBindVariables();
			sqlbv39.sql(gSql);
			sqlbv39.put("mContNo", mContNo);
			sqlbv39.put("riskcode", tssrs.GetText(i, 2));
			if(tLCGrpPolDB.executeQuery(sqlbv39).size()<=0)
			{
				dealError("LCGRPPOL", mBussNo, mPolNo, "001", mVoucherType+mMatchID+i,"保单号为" + mContNo + "对应保单信息不存在");
				continue;
			}
			else
			{
				LCGrpPolSchema tLCGrpPolSchema = tLCGrpPolDB.executeQuery(sqlbv39).get(1);
				mRiskCode = tLCGrpPolSchema.getRiskCode();
	
				mSaleChnl = tranSaleChnl(tLCGrpPolSchema.getSaleChnl(), tLCGrpPolSchema.getAgentCode(), tLCGrpPolSchema.getGrpContNo());
				mInsuredName = tLCGrpPolSchema.getGrpName();
			}

			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					if(tssrs.GetText(i, 3).equals("HL"))
					{
						mBillID = "0716";
						mAccountCode = "应付保单红利-保单红利";
					}
					else
					{
						mBillID = "0717";
//						mAccountCode = "保户红利支出-红利利息"; // 保户红利利息
						mAccountCode = "保单红利支出-红利利息";
					}
					mEnteredDR = String.valueOf(tmoney);
					mEnteredCR = "";
				}
				else
				{
					mBillID = "0718";
					mAccountCode = "应付保单红利-累计生息";
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
				}

				mHeadDescription = mAccountCode + mInsuredName;
				if(isExitInTab(mBussNo, mPolNo, mBillID))
					break;

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);

				dealLITranInfo();
			}
		}
		
		return true;
	}

	// 对其他给付实付表的业务操作；
	private boolean PayFeeDataOtherJ()
	{
		String tSql = "select * from LJAGetOther " 
			 		+ "where MakeDate='?mToday?' and ManageCom like concat('?cManageCom?','%')";
		SQLwithBindVariables sqlbv40=new SQLwithBindVariables();
		sqlbv40.sql(tSql);
		sqlbv40.put("mToday", mToday);
		sqlbv40.put("cManageCom", cManageCom);
		logger.debug(tSql);
		LJAGetOtherDB tLJAGetOtherDB = new LJAGetOtherDB();
		LJAGetOtherSet tLJAGetOtherSet = tLJAGetOtherDB.executeQuery(sqlbv40);

		for(int i = 0; i < tLJAGetOtherSet.size(); i++)
		{
			LJAGetOtherSchema tLJAGetOtherSchema = new LJAGetOtherSchema();
			tLJAGetOtherSchema = tLJAGetOtherSet.get(i + 1);

			if(tLJAGetOtherSchema.getGetMoney() <= 0)
			{
				dealError("LJAGetOther", tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), "004",
						mVoucherType+mMatchID+i, "溢交应付金额为非正数！");
				continue;
			}
			if("CM".equals(tLJAGetOtherSchema.getFeeFinaType()))
			{
				dealError("LJAGetOther", tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), "013", mVoucherType+mMatchID+i, 
						"该产品为健康委托产品，不在此进行凭证提取！");
				continue;
			}
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJAGetOtherSchema.getActuGetNo());
			if(!tLJAGetDB.getInfo())
			{
				dealError("LJAGetOther", tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), "002",
						mVoucherType+mMatchID+i, tLJAGetOtherSchema.getActuGetNo()+"实付总表记录不存在！");
				continue;
			}
			initVar();
			mMatchID++;
			mPolNo = tLJAGetOtherSchema.getOtherNo();
			mContNo = tLJAGetOtherSchema.getOtherNo();
			mBussNo = tLJAGetOtherSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetOtherSchema.getMakeDate();
			mSegment2 = tLJAGetOtherSchema.getManageCom();
			mManageCom = tLJAGetOtherSchema.getManageCom().substring(0, 6);
			if(!this.setPolInfo(mContNo,"", "LJAGetOther", mVoucherType+mMatchID+i))
				continue;
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetOtherSchema.getGetMoney()));

			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					mBillID = "0720";
					mAccountCode = "预收保费"; // 其它支出
					mEnteredDR = String.valueOf(tmoney);
					mEnteredCR = "";
					mHeadDescription = mInsuredName + "的其它给付";
				}
				else
				{
					mBillID = "0721";
					mAccountCode = "应付业务支出-溢额退费";
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
					mHeadDescription = mInsuredName + "的业务其它给付";
				}

				if(isExitInTab(tLJAGetOtherSchema.getActuGetNo(), mPolNo, mBillID))
					break;

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);

				dealLITranInfo();
			}
		}

		return true;
	}

	// 对红利给付实付表的财务付费操作；
	private boolean PayFeeDataBonusGetD()
	{
//		String tSql = "select * from LJABonusGet a "
//					+ "where ConfDate='"+ mToday+ "' and ManageCom like '"+ cManageCom+ "%' "
//					+ "and exists(select 1 from ljaget b where actugetno = a.actugetno "
//					+ "and (paymode not in ( '4','9') or (paymode in ( '4','9') and exists(select 1 from ldbank "
//					+ "where bankcode = b.bankcode and rpad(trim(comcode),6,'0') = substr(b.managecom,0,6)))))";
		String tSql = "select * from LJABonusGet a "
			+ "where ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') "
			+ "and exists(select 1 from ljaget b where actugetno = a.actugetno "
			+ "and (paymode not in ( '4','9') or (paymode in ( '4','9') and exists(select 1 from ldcode "
			+ "where codetype='bank' and trim(code) = trim(b.bankcode) and rpad(trim(comcode),6,'0') = substr(b.managecom,1,6)))))";
		SQLwithBindVariables sqlbv41=new SQLwithBindVariables();
		sqlbv41.sql(tSql);
		sqlbv41.put("mToday", mToday);
		sqlbv41.put("cManageCom", cManageCom);
		logger.debug(tSql);
		LJABonusGetDB tLJABonusGetDB = new LJABonusGetDB();
		LJABonusGetSet tLJABonusGetSet = tLJABonusGetDB.executeQuery(sqlbv41);
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();

		for(int i = 0; i < tLJABonusGetSet.size(); i++)
		{
			LJABonusGetSchema tLJABonusGetSchema = new LJABonusGetSchema();
			tLJABonusGetSchema = tLJABonusGetSet.get(i + 1);
			if(tLJABonusGetSchema.getGetMoney() == 0)
			{
				dealError("LJABonusGet", tLJABonusGetSchema.getActuGetNo(), tLJABonusGetSchema.getPolNo(), "004",
						mVoucherType+mMatchID+i, "红利给付金额为非正数");
				continue;
			}
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJABonusGetSchema.getActuGetNo());
			if(!tLJAGetDB.getInfo())
			{
				dealError("LJABonusGet", tLJABonusGetSchema.getActuGetNo(), tLJABonusGetSchema.getPolNo(), "002",
						mVoucherType+mMatchID+i, "LJAGet不存在相应的记录");
				continue;
			}
			tLJAGetSchema.setSchema(tLJAGetDB.getSchema());
			initVar();
			mMatchID++;
			mPolNo = tLJABonusGetSchema.getPolNo();
			mContNo = tLJABonusGetSchema.getContNo();
			mBussNo = tLJABonusGetSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJABonusGetSchema.getConfDate();
			mSegment2 = tLJABonusGetSchema.getManageCom();
			mManageCom = tLJABonusGetSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLJABonusGetSchema.getGetMoney()));

			if(!setPolInfo(mContNo,tLJABonusGetSchema.getRiskCode(), "LJABonusGet", mVoucherType+mMatchID+i))
				continue;

			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					mBillID = "0840";
					mAccountCode = "应付业务支出-红利";
					mEnteredDR = String.valueOf(tmoney);
					mEnteredCR = "";
				}
				else
				{
					if(tLJAGetSchema.getPayMode() != null)
					{
						if(!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
						{
							if(tLJAGetSchema.getPayMode().equals("5"))
							{
								mBillID = "0841";
								mAccountCode = "应付业务支出-内部转帐"; // 内部转账贷
							}
							else
							{
								mBillID = "0842";
								mAccountCode = "银行存款-活期-人民币"; // 银行存款支
								mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
							}
						}
						else
						{
							mBillID = "0843";
							mAccountCode = "库存现金-人民币-业务";
						}
					}
					else
					{
						mBillID = "0844";
						mAccountCode = "库存现金-人民币-业务";
					}
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
				}

				mHeadDescription = mAccountCode + mInsuredName;
				if(isExitInTab(mBussNo, mPolNo, mBillID))
					break;

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);

				dealLITranInfo();
			}
		}


//		String sql = "select * from LJABonusGet a "
//				   + "where ConfDate='"+ mToday+ "' and ManageCom like '"+ cManageCom+ "%' "
//				   + "and exists(select 1 from ljaget b where actugetno = a.actugetno and paymode in ( '4','9') "
//				   + "and bankcode is not null and exists(select 1 from ldbank where bankcode = b.bankcode "
//				   + "and rpad(trim(comcode),6,'0') <> substr(b.managecom,0,6)))";
		String sql = "select * from LJABonusGet a "
			   + "where ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') "
			   + "and exists(select 1 from ljaget b where actugetno = a.actugetno and paymode in ( '4','9') "
			   + "and bankcode is not null and exists(select 1 from ldcode where codetype='bank' and trim(code) = trim(b.bankcode) "
			   + "and rpad(trim(comcode),6,'0') <> substr(b.managecom,1,6)))";
		SQLwithBindVariables sqlbv42=new SQLwithBindVariables();
		sqlbv42.sql(sql);
		sqlbv42.put("mToday", mToday);
		sqlbv42.put("cManageCom", cManageCom);
		logger.debug(sql);
		tLJABonusGetDB = new LJABonusGetDB();
		tLJABonusGetSet = tLJABonusGetDB.executeQuery(sqlbv42);
		tLJAGetSchema = new LJAGetSchema();
		for(int i = 0; i < tLJABonusGetSet.size(); i++)
		{
			LJABonusGetSchema tLJABonusGetSchema = new LJABonusGetSchema();
			tLJABonusGetSchema = tLJABonusGetSet.get(i + 1);

			if(tLJABonusGetSchema.getGetMoney() == 0)
			{
				dealError("LJABonusGet", tLJABonusGetSchema.getActuGetNo(), tLJABonusGetSchema.getPolNo(), "004",
						mVoucherType+mMatchID+i, "红利给付金额为非正数");
				continue;
			}

			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJABonusGetSchema.getActuGetNo());
			if(!tLJAGetDB.getInfo())
			{
				dealError("LJABonusGet", tLJABonusGetSchema.getActuGetNo(), tLJABonusGetSchema.getPolNo(), "002",
						mVoucherType+mMatchID+i, "LJAGet不存在相应的记录");
				continue;
			}
			tLJAGetSchema.setSchema(tLJAGetDB.getSchema());
			initVar();
			mMatchID++;
			mPolNo = tLJABonusGetSchema.getPolNo();
			mContNo = tLJABonusGetSchema.getContNo();
			mBussNo = tLJABonusGetSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJABonusGetSchema.getConfDate();
			mSegment2 = tLJABonusGetSchema.getManageCom();
			mManageCom = tLJABonusGetSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLJABonusGetSchema.getGetMoney()));


			if(!setPolInfo(mContNo,tLJABonusGetSchema.getRiskCode(), "LJABonusGet", mVoucherType+mMatchID+i))
				continue;

			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					mBillID = "0885";
					mAccountCode = "应付业务支出-红利";
					mEnteredDR = String.valueOf(tmoney);
					mEnteredCR = "";
				}
				else
				{
					if(tLJAGetSchema.getPayMode() != null)
					{
						if(!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
						{
							if(tLJAGetSchema.getPayMode().equals("5"))
							{
								mBillID = "0886";
								mAccountCode = "应付业务支出-内部转帐"; // 内部转账贷
							}
							else
							{
								mBillID = "0887";
								mAccountCode = "其他应付款-资金中转-集中批付"; // 银行存款支
								mAccountSubCode = "C" + mManageCom.substring(2, 4).concat("0000");
							}
						}
					}
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
				}

				mHeadDescription = mAccountCode + mInsuredName;
				if(isExitInTab(tLJABonusGetSchema.getActuGetNo(), mPolNo, mBillID))
					break;

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);

				dealLITranInfo();
			}

			// 分公司借记科目
			// 取得机构对应的分公司
			mManageCom = tLJABonusGetSchema.getManageCom().substring(0, 4).concat("00");
			mSegment2 = mManageCom.concat("00");
			mBillID = "0888";
			mAccountCode = "其他应收款-资金中转-集中批付"; // 银行存款支
			mAccountSubCode = "C" + tLJABonusGetSchema.getManageCom().substring(2, 6).concat("00");
			mHeadDescription = mAccountCode + mInsuredName;
			mEnteredDR = String.valueOf(tmoney);
			mEnteredCR = "";
			if(isExitInTab(mBussNo, mPolNo, mBillID))
				continue;

			OFInterfaceSchema t1OFInterfaceSchema = (OFInterfaceSchema)entry();
			if(t1OFInterfaceSchema != null)
				mOFInterfaceSet.add(t1OFInterfaceSchema);

			dealLITranInfo();

			// 分公司贷记科目
			mBillID = "0889";
			mAccountCode = "银行存款-活期-人民币"; // 银行存款支
			mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
			mHeadDescription = mAccountCode + mInsuredName;
			mEnteredDR = "";
			mEnteredCR = String.valueOf(tmoney);
			if(isExitInTab(mBussNo, mPolNo, mBillID))
				continue;

			OFInterfaceSchema t2OFInterfaceSchema = new OFInterfaceSchema();
			t2OFInterfaceSchema = (OFInterfaceSchema)entry();
			if(t2OFInterfaceSchema != null)
				mOFInterfaceSet.add(t2OFInterfaceSchema);

			dealLITranInfo();

		}
		return true;
	}

	// 对其它给付实付表的财务付费操作；
	private boolean PayFeeDataOtherGetD()
	{
//		String tSql = "select * from LJAGetOther a "
//					+ "where ConfDate='"+ mToday+ "' and ManageCom like '"+ cManageCom+ "%' "
//					+ "and exists(select 1 from ljaget b where actugetno = a.actugetno "
//					+ "and (paymode not in ( '4','9') or (paymode in ( '4','9') and exists(select 1 from ldbank "
//					+ "where bankcode = b.bankcode and rpad(trim(comcode),6,'0') = substr(b.managecom,0,6)))))";
		String tSql = "select * from LJAGetOther a "
			+ "where ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') "
			+ "and exists(select 1 from ljaget b where actugetno = a.actugetno "
			+ "and (paymode not in ( '4','9') or (paymode in ( '4','9') and exists(select 1 from ldcode "
			+ "where codetype='bank' and trim(code) = trim(b.bankcode) and rpad(trim(comcode),6,'0') = substr(b.managecom,1,6)))))";
		SQLwithBindVariables sqlbv43=new SQLwithBindVariables();
		sqlbv43.sql(tSql);
		sqlbv43.put("mToday", mToday);
		sqlbv43.put("cManageCom", cManageCom);
		logger.debug(tSql);
		LJAGetOtherDB tLJAGetOtherDB = new LJAGetOtherDB();
		LJAGetOtherSet tLJAGetOtherSet = tLJAGetOtherDB.executeQuery(sqlbv43);
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();

		for(int i = 0; i < tLJAGetOtherSet.size(); i++)
		{
			LJAGetOtherSchema tLJAGetOtherSchema = new LJAGetOtherSchema();
			tLJAGetOtherSchema = tLJAGetOtherSet.get(i + 1);
			if(tLJAGetOtherSchema.getGetMoney() <= 0)
			{
				dealError("LJAGetOther", tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), "004",
						mVoucherType+mMatchID+i, "溢交退费金额为非正数");
				continue;
			}
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJAGetOtherSchema.getActuGetNo());
			if(!tLJAGetDB.getInfo())
			{
				dealError("LJAOtherGet", tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), "002",
						mVoucherType+mMatchID+i, "LJAGet不存在对应的记录");
				continue;
			}
			tLJAGetSchema.setSchema(tLJAGetDB.getSchema());
			initVar();
			mMatchID++;
			mPolNo = tLJAGetOtherSchema.getOtherNo();
			mContNo = tLJAGetOtherSchema.getOtherNo();
			mBussNo = tLJAGetOtherSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetOtherSchema.getConfDate();
			mSegment2 = tLJAGetOtherSchema.getManageCom();
			mManageCom = tLJAGetOtherSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetOtherSchema.getGetMoney()));

			if(tLJAGetOtherSchema.getGetMoney() > 0)
			{
				if(!this.setPolInfo(mPolNo,"", "LJAGetOther",mVoucherType+mMatchID+i))
					continue;

				for(int j = 1; j <= 2; j++)
				{
					if(j == 1)
					{
						mBillID = "0850";
						mAccountCode = "应付业务支出-溢额退费";
						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";
					}
					else
					{
						if(tLJAGetSchema.getPayMode() != null)
						{
							if(!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
							{
								if(tLJAGetSchema.getPayMode().equals("5"))
								{
									mBillID = "0851";
									mAccountCode = "应付业务支出-内部转帐"; // 内部转账贷
								}
								else
								{
									mBillID = "0852";
									mAccountCode = "银行存款-活期-人民币"; // 银行存款支
									mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
								}
							}
							else
							{
								mBillID = "0853";
								mAccountCode = "库存现金-人民币-业务";
							}
						}
						else
						{
							mBillID = "0854";
							mAccountCode = "库存现金-人民币-业务";
						}
						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
					}
					mHeadDescription = mInsuredName + "的其它给付退费";
					if(isExitInTab(tLJAGetOtherSchema.getActuGetNo(), mPolNo, mBillID))
						break;

					OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
					if(tOFInterfaceSchema != null)
						mOFInterfaceSet.add(tOFInterfaceSchema);

					dealLITranInfo();
				}
			}
		}

//		String sql = "select * from LJAGetOther a "
//				   + "where ConfDate='"+ mToday+ "' and ManageCom like '"+ cManageCom+ "%' "
//				   + "and exists(select 1 from ljaget b where actugetno = a.actugetno and paymode in ( '4','9') "
//				   + "and bankcode is not null and exists(select 1 from ldbank where bankcode = b.bankcode "
//				   + "and rpad(trim(comcode),6,'0') <> substr(b.managecom,0,6)))";
		String sql = "select * from LJAGetOther a "
			   + "where ConfDate='?mToday?' and ManageCom like concat('?cManageCom?','%') "
			   + "and exists(select 1 from ljaget b where actugetno = a.actugetno and paymode in ( '4','9') "
			   + "and bankcode is not null and exists(select 1 from ldcode where codetype='bank' and trim(code) = trim(b.bankcode) "
			   + "and rpad(trim(comcode),6,'0') <> substr(b.managecom,1,6)))";
		SQLwithBindVariables sqlbv44=new SQLwithBindVariables();
		sqlbv44.sql(sql);
		sqlbv44.put("mToday", mToday);
		sqlbv44.put("cManageCom", cManageCom);

		logger.debug(sql);
		tLJAGetOtherDB = new LJAGetOtherDB();
		tLJAGetOtherSet = tLJAGetOtherDB.executeQuery(sqlbv44);
		tLJAGetSchema = new LJAGetSchema();

		for(int i = 0; i < tLJAGetOtherSet.size(); i++)
		{
			LJAGetOtherSchema tLJAGetOtherSchema = new LJAGetOtherSchema();
			tLJAGetOtherSchema = tLJAGetOtherSet.get(i + 1);
			if(tLJAGetOtherSchema.getGetMoney() <= 0)
			{
				dealError("LJAGetOther", tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), "004",
						mVoucherType+mMatchID+i, "溢交退费金额为非正数");
				continue;
			}
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJAGetOtherSchema.getActuGetNo());
			if(!tLJAGetDB.getInfo())
			{
				dealError("LJAOtherGet", tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), "002",
						mVoucherType+mMatchID+i, "LJAGet不存在对应的记录");
				continue;
			}
			tLJAGetSchema.setSchema(tLJAGetDB.getSchema());
			initVar();
			mMatchID++;
			mPolNo = tLJAGetOtherSchema.getOtherNo();
			mContNo = tLJAGetOtherSchema.getOtherNo();
			mBussNo = tLJAGetOtherSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetOtherSchema.getConfDate();
			mSegment2 = tLJAGetOtherSchema.getManageCom();
			mManageCom = tLJAGetOtherSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetOtherSchema.getGetMoney()));

			if(tLJAGetOtherSchema.getGetMoney() > 0)
			{
				if(!this.setPolInfo(mPolNo, "","LJAGetOther", mVoucherType+mMatchID+i))
					continue;

				for(int j = 1; j <= 2; j++)
				{
					if(j == 1)
					{
						mBillID = "0890";
						mAccountCode = "应付业务支出-溢额退费";
						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";
					}
					else
					{
						if(tLJAGetSchema.getPayMode() != null)
						{
							if(!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
							{
								if(tLJAGetSchema.getPayMode().equals("5"))
								{
									mBillID = "0891";
									mAccountCode = "应付业务支出-内部转帐"; // 内部转账贷
								}
								else
								{
									mBillID = "0892";
									mAccountCode = "其他应付款-资金中转-集中批付"; // 银行存款支
									mAccountSubCode = "C" + mManageCom.substring(2, 4).concat("0000");
								}
							}
						}
						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
					}
					mHeadDescription = mInsuredName + "的其它给付退费";
					if(isExitInTab(tLJAGetOtherSchema.getActuGetNo(), mPolNo, mBillID))
						break;

					OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
					if(tOFInterfaceSchema != null)
						mOFInterfaceSet.add(tOFInterfaceSchema);

					dealLITranInfo();
				}

				// 分公司借记科目
				// 取得机构对应的分公司
				mManageCom = tLJAGetSchema.getManageCom().substring(0, 4).concat("00");
				mSegment2 = mManageCom.concat("00");
				mBillID = "0893";
				mAccountCode = "其他应收款-资金中转-集中批付"; // 银行存款支
				mAccountSubCode = "C" + tLJAGetSchema.getManageCom().substring(2, 6).concat("00");
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = String.valueOf(tmoney);
				mEnteredCR = "";
				if(isExitInTab(mBussNo, mPolNo, mBillID))
					continue;

				OFInterfaceSchema t1OFInterfaceSchema = (OFInterfaceSchema)entry();
				if(t1OFInterfaceSchema != null)
					mOFInterfaceSet.add(t1OFInterfaceSchema);

				dealLITranInfo();

				// 分公司贷记科目
				mBillID = "0894";
				mAccountCode = "银行存款-活期-人民币"; // 银行存款支
				mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = "";
				mEnteredCR = String.valueOf(tmoney);
				if(isExitInTab(mBussNo, mPolNo, mBillID))
					continue;

				OFInterfaceSchema t2OFInterfaceSchema = new OFInterfaceSchema();
				t2OFInterfaceSchema = (OFInterfaceSchema)entry();
				if(t2OFInterfaceSchema != null)
					mOFInterfaceSet.add(t2OFInterfaceSchema);

				dealLITranInfo();
			}
		}
		return true;
	}
	
	//获取保全项目详细科目代码
	private  boolean getEndorsePayCode(String mActugetno,String mManageCom,String mConfDate,String mFlag)
	{

		LJAGetEndorseSet tLJAGetEndorseSet = new LJAGetEndorseSet();
		LJAGetEndorseDB  tLJAGetEndorseDB = new LJAGetEndorseDB();
		tLJAGetEndorseDB.setActuGetNo(mActugetno);
		tLJAGetEndorseSet = (LJAGetEndorseSet)tLJAGetEndorseDB.query();
		for(int k = 1; k <= tLJAGetEndorseSet.size(); k++)
		{
			LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
			tLJAGetEndorseSchema.setSchema(tLJAGetEndorseSet.get(k));
			if("CM".equals(tLJAGetEndorseSchema.getFeeFinaType())) // 排除健康委托产品
			{
				dealError("LJAGetEndorse", tLJAGetEndorseSchema.getActuGetNo(), tLJAGetEndorseSchema.getContNo(), "013", mVoucherType+mMatchID+k, "健康委托产品退费不在该处提取凭证！");
				continue;
			}
			mPolNo = tLJAGetEndorseSchema.getPolNo();
			mContNo = tLJAGetEndorseSchema.getContNo();
			if(tLJAGetEndorseSchema.getGetMoney() == 0)
			{
				dealError("LJAGetEndorse", mBussNo, mPolNo, "004", mVoucherType+mMatchID+k, "保全实付金额为0");
				continue;
			}
			//针对保全团单不到个单的特殊处理
			if("00000000000000000000".equals(tLJAGetEndorseSchema.getContNo()))
			{
//				if(getGrpPol(tLJAGetEndorseSchema.getGrpPolNo(),"008")==null)
				//zy  2009-07-27 由于保全有些项目针对合同，grppolno记得是20个0，所以调整查询逻辑
				if(!getEGrpPol(tLJAGetEndorseSchema.getGrpContNo(),tLJAGetEndorseSchema.getRiskCode(),"008"))
					return	false;
			}
			else
			{
				if(!this.setPolInfo(mContNo,tLJAGetEndorseSchema.getRiskCode(), "LJAGetEndorse", "008"))
				return false;
			}

            tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetEndorseSchema.getGetMoney()) );

			if("TB".equals(tLJAGetEndorseSchema.getFeeFinaType()))
			{
				mAccountCode = "应付业务支出-退保金";		
            	mBillID = "0800";
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                if("P".equals(mFlag))
                {
                	mBillID = "0902";
                	mHeadDescription = "借" + mInsuredName + "业务应付退保金";
                }
                if("G".equals(mFlag))
                {
                	 mHeadDescription = "其它应付退保" + mInsuredName ;
                }
			}
			else if("DK".equals(tLJAGetEndorseSchema.getFeeFinaType())) // 借款
			{
	            double tYHSLMoney = 0.0;

                LJAGetEndorseDB YLJAGetEndorseDB = new LJAGetEndorseDB();
                YLJAGetEndorseDB.setActuGetNo(tLJAGetEndorseSchema.getActuGetNo());
                YLJAGetEndorseDB.setPolNo(tLJAGetEndorseSchema.getPolNo());
                YLJAGetEndorseDB.setEndorsementNo(tLJAGetEndorseSchema.getEndorsementNo());
                YLJAGetEndorseDB.setFeeOperationType("LN");
                YLJAGetEndorseDB.setFeeFinaType("RV");
                LJAGetEndorseSet YLJAGetEndorseSet = YLJAGetEndorseDB.query();
                for (int jj=1;jj<=YLJAGetEndorseSet.size();jj++)
                {
                	tYHSLMoney = tYHSLMoney + YLJAGetEndorseSet.get(jj).getGetMoney();
                }
				mAccountCode = "应付业务支出-保单贷款";
				mBillID = "0801";
                tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetEndorseSchema.getGetMoney()-tYHSLMoney) );
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                if("P".equals(mFlag))
                {
                	mBillID = "0903";
                	mHeadDescription = "借" + mInsuredName + "业务应付借款";
                }
                if("G".equals(mFlag))
                {
                	mHeadDescription = "其它应付借款" + mInsuredName ;
                }
			}
			else if("TF".equals(tLJAGetEndorseSchema.getFeeFinaType()) || "YE".equals(tLJAGetEndorseSchema.getFeeFinaType())
					 ||"DJ".equals(tLJAGetEndorseSchema.getFeeFinaType()))
			{
				if("WT".equals(tLJAGetEndorseSchema.getFeeOperationType()))
				{
					mBillID = "0895";
					mAccountCode = "应付业务支出-犹豫期撤单";
				}
				else if("M".equals(getRiskPeriod(mRiskCode, "008"))
						|| "S".equals(getRiskPeriod(mRiskCode, "008")))
				{
					mBillID = "0896";
					mAccountCode = "应付业务支出-短期险退保";
				}
				else
				{
					mAccountCode = "应付业务支出-其他";
					mBillID = "0802";
				}
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                mHeadDescription = "借" + mInsuredName + "业务其它应付";
			}

			else if ("BF".equals(tLJAGetEndorseSchema.getFeeFinaType()))
            {
                mBillID = "0900";
                mAccountCode = "保费收入-趸缴";                               //实交
                mHeadDescription = "收" + mInsuredName + "保费";
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);                       
            }
			else if("LX".equals(tLJAGetEndorseSchema.getFeeFinaType()))
            {
            	if("RE".equals(tLJAGetEndorseSchema.getFeeOperationType()))
            	{
                    mBillID = "09010";
                    mAccountCode = "利息收入-保单复效利息收入";                           //利息收入                		
            	}
            	else
            	{
                mBillID = "0901";
                mAccountCode = "利息收入-垫交保费";                           //利息收入
            	}
                mHeadDescription = "收" + mInsuredName + "利息";
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
            }	
			else if ("HK".equals(tLJAGetEndorseSchema.getFeeFinaType()))
            {
                mBillID = "0904";
                mAccountCode = "贷款-保户质押贷款－本金";
                mHeadDescription = "贷" + mInsuredName + "业务还款";
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
            }
			else if ("HD".equals(tLJAGetEndorseSchema.getFeeFinaType()))
            {
                mBillID = "0905";
                mAccountCode = "垫交保费";                                   //贷垫交
                mHeadDescription = mInsuredName + "垫交";
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
            }
			else if ("GB".equals(tLJAGetEndorseSchema.getFeeFinaType()))
            {
                mBillID = "0906";
                mAccountCode = "其他业务收入-补发保单款";
                mHeadDescription = mInsuredName + "工本费";
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
            }

//			else if ("LJTF".equals(tLJAGetEndorseSchema.getFeeFinaType())) //累计生息红利
//            {
//                mBillID = "0906";
//                mAccountCode = "应付保单红利-累计生息";		                   
//                mEnteredDR = String.valueOf(tmoney);
//                mEnteredCR = "";
//            }
			else if ("XJTF".equals(tLJAGetEndorseSchema.getFeeFinaType()) || "DJTF".equals(tLJAGetEndorseSchema.getFeeFinaType())
            		|| "HLTF".equals(tLJAGetEndorseSchema.getFeeFinaType()) || "LJTF".equals(tLJAGetEndorseSchema.getFeeFinaType())) //红利
            {
                mBillID = "0840";
                mAccountCode = "应付业务支出-红利";		                   
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                mHeadDescription = mAccountCode + mInsuredName ;
            }
			else  if ("YHSL".equals(tLJAGetEndorseSchema.getFeeFinaType()))
            {
				tYHSLMoney = Double.parseDouble(tmoney);
                continue;                                                   //该凭证中不处理印花税率
            }
			else if ("YF".equals(tLJAGetEndorseSchema.getFeeFinaType()) || "YFLX".equals(tLJAGetEndorseSchema.getFeeFinaType()))
            {
                mBillID = "0812";
                mAccountCode = "应付业务支出-年金";	
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                mHeadDescription = mAccountCode + mInsuredName + "生存领取费";
            }
			else if ("EF".equals(tLJAGetEndorseSchema.getFeeFinaType()))
            {
                mBillID = "0810";
                mAccountCode = "应付业务支出-满期";	
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                mHeadDescription = mAccountCode + mInsuredName + "生存领取费";
            }
			else if("BD".equals(tLJAGetEndorseSchema.getFeeFinaType()))
			{
                mBillID = "0907";
//                mAccountCode = "资产减值损失-客户贷（垫）款";
                mAccountCode = "资产减值损失-垫交保费";//zy 2009-06-30 调整科目名称
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                mHeadDescription = mAccountCode + mInsuredName ;
			}
			else				//以后新增保全财务类型，待以后补充
			{
				dealError("LJAGetEndorse", mBussNo, mPolNo, "005", mVoucherType+mMatchID+k, "未描述的财务类型"+tLJAGetEndorseSchema.getFeeFinaType()+"");
				continue;
			}

			if(isExitInTab(tLJAGetEndorseSchema.getActuGetNo(), tLJAGetEndorseSchema.getPolNo(), mBillID))
				return false;
	
			OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
			if(tOFInterfaceSchema != null)
				mOFInterfaceSet.add(tOFInterfaceSchema);
			dealLITranInfo();
		}
		
		
		LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();
		tLJAGetDrawDB.setActuGetNo(mActugetno);
		LJAGetDrawSet tLJAGetDrawSet =(LJAGetDrawSet) tLJAGetDrawDB.query();
		for(int j=1;j<=tLJAGetDrawSet.size();j++)
		{
			LJAGetDrawSchema tLJAGetDrawSchema = tLJAGetDrawSet.get(j);
			if(tLJAGetDrawSchema.getGetMoney() == 0)
			{
				dealError("tLJAGetDrawSchema", mBussNo, tLJAGetDrawSchema.getPolNo(), "004", mVoucherType+mMatchID+j, "生存领取实付金额为0");
				continue;
			}
			mPolNo = tLJAGetDrawSchema.getPolNo();
			mContNo = tLJAGetDrawSchema.getContNo();
			if(!this.setPolInfo(mContNo,tLJAGetDrawSchema.getRiskCode(), "LJAGetDraw", "008"))
				return false;
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetDrawSchema.getGetMoney()) );
            if ("YF".equals(tLJAGetDrawSchema.getFeeFinaType()) || "YFLX".equals(tLJAGetDrawSchema.getFeeFinaType()))
            {
                mBillID = "0812";
                mAccountCode = "应付业务支出-年金";	
            }
            if ("EF".equals(tLJAGetDrawSchema.getFeeFinaType()))
            {
                mBillID = "0810";
                mAccountCode = "应付业务支出-满期";	
            }
            mEnteredDR = String.valueOf(tmoney);
            mEnteredCR = "";
            mHeadDescription = mAccountCode + mInsuredName + "生存领取费";
			if(isExitInTab(mActugetno, tLJAGetDrawSchema.getPolNo(), mBillID))
				return false;

			OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
			if(tOFInterfaceSchema != null)
				mOFInterfaceSet.add(tOFInterfaceSchema);
			dealLITranInfo();
				
		}
		
		
		LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
		tLJAPayPersonDB.setPayNo(mActugetno);
		LJAPayPersonSet tLJAPayPersonSet =tLJAPayPersonDB.query();
		for(int i=1;i<=tLJAPayPersonSet.size();i++)
		{
			LJAPayPersonSchema tLJAPayPersonSchema =tLJAPayPersonSet.get(i);
            if(!"ZC".equals(tLJAPayPersonSchema.getPayType()))
            {
				dealError("LJAPayPerson", tLJAPayPersonSchema.getPayNo(), tLJAPayPersonSchema.getPolNo(), "005",
						mVoucherType+mMatchID+i,"交费类型为" + tLJAPayPersonSchema.getPayType() + ",不属于凭证提取范围");
                continue;
            }

            if(tLJAPayPersonSchema.getSumActuPayMoney()==0)
            {
				dealError("LJAPayPerson", mBussNo, tLJAPayPersonSchema.getPolNo(), "004", mVoucherType+mMatchID+i, "保费收入金额为0");
				continue;
            }
            
            mPolNo = tLJAPayPersonSchema.getPolNo();
            mContNo = tLJAPayPersonSchema.getContNo();
			if(!this.setPolInfo(mContNo,tLJAPayPersonSchema.getRiskCode(), "LJAPayPerson", "008"))
				return false;

			tmoney = new DecimalFormat("0.00").format(new Double(tLJAPayPersonSchema.getSumActuPayMoney()));
            mBillID = "0918";
            mAccountCode = "保费收入-趸缴";                              //实交
            mHeadDescription = "收" + mInsuredName + "保费";
            mEnteredDR = "";
            mEnteredCR = String.valueOf(tmoney);

            if (isExitInTab(mActugetno,tLJAPayPersonSchema.getPolNo(),mBillID))
            	return false;
            OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
            if(tOFInterfaceSchema != null)
                mOFInterfaceSet.add(tOFInterfaceSchema);
            dealLITranInfo();

		}

		return true;
	}
	
	
	/**
	 * 生存领取涉及的科目
	 **/
	private boolean getDrawCode(String mActugetno)
	{
		LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();
		tLJAGetDrawDB.setActuGetNo(mActugetno);
		LJAGetDrawSet tLJAGetDrawSet =(LJAGetDrawSet) tLJAGetDrawDB.query();
		for(int j=1;j<=tLJAGetDrawSet.size();j++)
		{
			LJAGetDrawSchema tLJAGetDrawSchema = tLJAGetDrawSet.get(j);
			if(tLJAGetDrawSchema.getGetMoney() == 0)
			{
				dealError("tLJAGetDrawSchema", mBussNo, tLJAGetDrawSchema.getPolNo(), "004", mVoucherType+mMatchID+j, "生存领取实付金额为0");
				continue;
			}
			mPolNo = tLJAGetDrawSchema.getPolNo();
			mContNo = tLJAGetDrawSchema.getContNo();
			if(!this.setPolInfo(mContNo,tLJAGetDrawSchema.getRiskCode(), "LJAGetDraw", "008"))
				return false;
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetDrawSchema.getGetMoney()) );

//            if ("YF".equals(tLJAGetDrawSchema.getFeeFinaType()) || "YFLX".equals(tLJAGetDrawSchema.getFeeFinaType()))
//            {
//                mBillID = "0812";
//                mAccountCode = "应付业务支出-年金";	
//            }
            if ("EF".equals(tLJAGetDrawSchema.getFeeFinaType()))
            {
                mBillID = "0810";
                mAccountCode = "应付业务支出-满期";	
            }
            else  //为了兼容历史数据，将LX纳入年金科目中
            {
            	mBillID = "0812";
              mAccountCode = "应付业务支出-年金";	
            }
            mEnteredDR = String.valueOf(tmoney);
            mEnteredCR = "";
            mHeadDescription = mAccountCode + mInsuredName + "生存领取费";
			if(isExitInTab(mActugetno, tLJAGetDrawSchema.getPolNo(), mBillID))
				return false;

			OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
			if(tOFInterfaceSchema != null)
				mOFInterfaceSet.add(tOFInterfaceSchema);
			dealLITranInfo();
				
		}
	
		LJAGetEndorseSet tLJAGetEndorseSet = new LJAGetEndorseSet();
		LJAGetEndorseDB  tLJAGetEndorseDB = new LJAGetEndorseDB();
		tLJAGetEndorseDB.setActuGetNo(mActugetno);
		tLJAGetEndorseSet = (LJAGetEndorseSet)tLJAGetEndorseDB.query();
		for(int k = 1; k <= tLJAGetEndorseSet.size(); k++)
		{
			LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
			tLJAGetEndorseSchema.setSchema(tLJAGetEndorseSet.get(k));
			if("CM".equals(tLJAGetEndorseSchema.getFeeFinaType())) // 排除健康委托产品
			{
				dealError("LJAGetEndorse", tLJAGetEndorseSchema.getActuGetNo(), tLJAGetEndorseSchema.getContNo(), "013", mVoucherType+mMatchID+k, "健康委托产品退费不在该处提取凭证！");
				continue;
			}
			mPolNo = tLJAGetEndorseSchema.getPolNo();
			mContNo = tLJAGetEndorseSchema.getContNo();
			if(tLJAGetEndorseSchema.getGetMoney() == 0)
			{
				dealError("LJAGetEndorse", mBussNo, mPolNo, "004", mVoucherType+mMatchID+k, "保全实付金额为0");
				continue;
			}
			if(!this.setPolInfo(mContNo,tLJAGetEndorseSchema.getRiskCode(), "LJAGetEndorse", "008"))
				return false;

            tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetEndorseSchema.getGetMoney()) );



			if("LX".equals(tLJAGetEndorseSchema.getFeeFinaType()))
            {
                mBillID = "0901";
                mAccountCode = "利息收入-垫交保费";                           //利息收入
                mHeadDescription = "收" + mInsuredName + "利息";
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
            }	
			else if ("HK".equals(tLJAGetEndorseSchema.getFeeFinaType()))
            {
                mBillID = "0904";
                mAccountCode = "贷款-保户质押贷款－本金";
                mHeadDescription = "贷" + mInsuredName + "业务还款";
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
            }
			else if ("HD".equals(tLJAGetEndorseSchema.getFeeFinaType()))
            {
                mBillID = "0905";
                mAccountCode = "垫交保费";                                   //贷垫交
                mHeadDescription = mInsuredName + "垫交";
                mEnteredDR = "";
                mEnteredCR = String.valueOf(tmoney);
            }
			else				//生存金领取涉及清偿贷款
			{
				dealError("LJAGetEndorse", mBussNo, mPolNo, "005", mVoucherType+mMatchID+k, "未描述的财务类型"+tLJAGetEndorseSchema.getFeeFinaType()+"");
				continue;
			}

			if(isExitInTab(tLJAGetEndorseSchema.getActuGetNo(), tLJAGetEndorseSchema.getPolNo(), mBillID))
				return false;
	
			OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
			if(tOFInterfaceSchema != null)
				mOFInterfaceSet.add(tOFInterfaceSchema);
			dealLITranInfo();
		}	
		return true;
	
	}
	
	/**
	 * 理赔实付及理赔清算涉及的科目定义
	 **/
	private  boolean getClaimCode(String mActugetno,String mManageCom,String mConfDate,String mFlag)
	{


		LJAGetClaimSet tLJAGetClaimSet = new LJAGetClaimSet();
		LJAGetClaimDB  tLJAGetClaimDB = new LJAGetClaimDB();
		tLJAGetClaimDB.setActuGetNo(mActugetno);
		tLJAGetClaimSet = (LJAGetClaimSet)tLJAGetClaimDB.query();
		for(int k = 1; k <= tLJAGetClaimSet.size(); k++)
		{
			LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
			tLJAGetClaimSchema.setSchema(tLJAGetClaimSet.get(k));
			if("CM".equals(tLJAGetClaimSchema.getFeeFinaType())) // 排除健康委托产品
			{
				dealError("LJAGetClaim", tLJAGetClaimSchema.getActuGetNo(), tLJAGetClaimSchema.getContNo(), "013", mVoucherType+mMatchID+k, "健康委托产品退费不在该处提取凭证！");
				continue;
			}
			mPolNo = tLJAGetClaimSchema.getPolNo();
			mContNo = tLJAGetClaimSchema.getContNo();
			if(tLJAGetClaimSchema.getPay() == 0)
			{
				dealError("LJAGetClaim", mBussNo, mPolNo, "004", mVoucherType+mMatchID+k, "理赔实付金额为0");
				continue;
			}
			if(!this.setPolInfo(mContNo,tLJAGetClaimSchema.getRiskCode(), "LJAGetClaim", "008"))
				return false;

            tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetClaimSchema.getPay()) );
            
			if("SWPK".equals(tLJAGetClaimSchema.getFeeFinaType()))
			{
				mBillID = "08301";
				mAccountCode = "应付业务支出-死亡给付"; // 死亡给付金
				mEnteredDR = String.valueOf(tmoney);
				mEnteredCR = "";
				mHeadDescription = mAccountCode + mInsuredName.trim()+"赔付退费";
			}
			else if("YLPK".equals(tLJAGetClaimSchema.getFeeFinaType()))
			{
				mBillID = "08302";
				mAccountCode = "应付业务支出-医疗给付"; // 医疗给付金
				mEnteredDR = String.valueOf(tmoney);
				mEnteredCR = "";
				mHeadDescription = mAccountCode + mInsuredName.trim()+"赔付退费";
			}
			else if("SCPK".equals(tLJAGetClaimSchema.getFeeFinaType()))
			{
				mBillID = "08303";
				mAccountCode = "应付业务支出-伤残给付"; // 伤残给付金 
				mEnteredDR = String.valueOf(tmoney);
				mEnteredCR = "";
				mHeadDescription = mAccountCode + mInsuredName.trim()+"赔付退费";
			}
			else if("DQPK".equals(tLJAGetClaimSchema.getFeeFinaType()))
			{
				mBillID = "08304";
				mAccountCode = "应付业务支出-赔款支出";
				mEnteredDR = String.valueOf(tmoney);
				mEnteredCR = "";
				mHeadDescription = mAccountCode + mInsuredName.trim()+"赔付退费";
			}
			else if("YCLX".equals(tLJAGetClaimSchema.getFeeFinaType()))
			{
				mAccountCode = "应付业务支出-理赔";
				mBillID = "0830";
				mEnteredDR = String.valueOf(tmoney);
				mEnteredCR = "";
				mHeadDescription = mAccountCode + mInsuredName.trim()+"赔付退费";
			}
			else if("TB".equals(tLJAGetClaimSchema.getFeeFinaType()))
			{
				mAccountCode = "应付业务支出-退保金";		
            	mBillID = "08100";
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                if("P".equals(mFlag))
                {
                	mBillID = "08101";
                	mHeadDescription = "借" + mInsuredName + "业务应付退保金";
                }
                if("G".equals(mFlag))
                {
                	 mHeadDescription = "其它应付退保" + mInsuredName ;
                }
			}
			else if("TF".equals(tLJAGetClaimSchema.getFeeFinaType()) || "YE".equals(tLJAGetClaimSchema.getFeeFinaType()))
			{
				if("M".equals(getRiskPeriod(mRiskCode, "008"))
						|| "S".equals(getRiskPeriod(mRiskCode, "008")))
				{
					mBillID = "08102";
					mAccountCode = "应付业务支出-短期险退保";
				}
				else
				{
					mAccountCode = "应付业务支出-其他";
					mBillID = "08103";
				}
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                mHeadDescription = "借" + mInsuredName + "业务其它应付";
			}
			else if ("BF".equals(tLJAGetClaimSchema.getFeeFinaType()))
            {
                mBillID = "08104";
                mAccountCode = "保费收入-趸缴";                               //实交
                mHeadDescription = "收" + mInsuredName + "保费";
                mEnteredDR = "";
//                mEnteredCR = String.valueOf(tmoney);    
                //理赔业务记录的是负值
                mEnteredCR = String.valueOf( new DecimalFormat("0.00").format(new Double(0-tLJAGetClaimSchema.getPay())));
            }
			else if("HDLX".equals(tLJAGetClaimSchema.getFeeFinaType()) || "HKLX".equals(tLJAGetClaimSchema.getFeeFinaType()))
            {
                mBillID = "08105";
                mAccountCode = "利息收入-垫交保费";                           //利息收入
                mHeadDescription = "收" + mInsuredName + "利息";
                mEnteredDR = "";
//                mEnteredCR = String.valueOf(tmoney);
                mEnteredCR = String.valueOf( new DecimalFormat("0.00").format(new Double(0-tLJAGetClaimSchema.getPay())));
            }	
			else if ("HK".equals(tLJAGetClaimSchema.getFeeFinaType()))
            {
                mBillID = "08106";
                mAccountCode = "贷款-保户质押贷款－本金";
                mHeadDescription = "贷" + mInsuredName + "业务还款";
                mEnteredDR = "";
//                mEnteredCR = String.valueOf(tmoney);
                mEnteredCR = String.valueOf( new DecimalFormat("0.00").format(new Double(0-tLJAGetClaimSchema.getPay())));
            }
			else if ("HD".equals(tLJAGetClaimSchema.getFeeFinaType()))
            {
                mBillID = "08107";
                mAccountCode = "垫交保费";                                   //贷垫交
                mHeadDescription = mInsuredName + "垫交";
                mEnteredDR = "";
//                mEnteredCR = String.valueOf(tmoney);
                mEnteredCR = String.valueOf( new DecimalFormat("0.00").format(new Double(0-tLJAGetClaimSchema.getPay())));
            }

			else if ("LJTF".equals(tLJAGetClaimSchema.getFeeFinaType())) //累计生息红利
            {
                mBillID = "08108";
                mAccountCode = "应付保单红利-累计生息";		                   
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                mHeadDescription = mAccountCode + mInsuredName ;
            }
			else if ("XJTF".equals(tLJAGetClaimSchema.getFeeFinaType()) || "DJTF".equals(tLJAGetClaimSchema.getFeeFinaType())
            		|| "HLTF".equals(tLJAGetClaimSchema.getFeeFinaType())) //红利
            {
                mBillID = "08109";
                mAccountCode = "应付业务支出-红利";		                   
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                mHeadDescription = mAccountCode + mInsuredName ;
            }
			else if ("YF".equals(tLJAGetClaimSchema.getFeeFinaType()) || "YFLX".equals(tLJAGetClaimSchema.getFeeFinaType()))
            {
                mBillID = "08110";
                mAccountCode = "应付业务支出-年金";	
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                mHeadDescription = mAccountCode + mInsuredName + "生存领取费";
            }
			else if ("EF".equals(tLJAGetClaimSchema.getFeeFinaType()))
            {
                mBillID = "08111";
                mAccountCode = "应付业务支出-满期";	
                mEnteredDR = String.valueOf(tmoney);
                mEnteredCR = "";
                mHeadDescription = mAccountCode + mInsuredName + "生存领取费";
            }
			else if ("HLRB".equals(tLJAGetClaimSchema.getFeeFinaType())) //红利追回
            {
                mBillID = "08112";
                mAccountCode = "保单红利支出-清算收入";		                   
                mEnteredDR ="" ;
//                mEnteredCR = String.valueOf(tmoney);
                //zy 理赔清算时该数据存放的是负值，所以财务接口取相反值
                mEnteredCR = String.valueOf(new DecimalFormat("0.00").format(new Double(0-tLJAGetClaimSchema.getPay())));
                mHeadDescription = mAccountCode + mInsuredName ;
            }
			else if ("YFRB".equals(tLJAGetClaimSchema.getFeeFinaType()))//年金追回
            {
                mBillID = "08113";
                mAccountCode = "年金-清算收入";	
                mEnteredDR = "";
                mEnteredCR = String.valueOf( new DecimalFormat("0.00").format(new Double(0-tLJAGetClaimSchema.getPay())));
                mHeadDescription = mAccountCode + mInsuredName + "生存领取费";
            }
			else				//以后新增保全财务类型，待以后补充
			{
				dealError("LJAGetClaim", mBussNo, mPolNo, "005", mVoucherType+mMatchID+k, "未描述的财务类型"+tLJAGetClaimSchema.getFeeFinaType()+"");
				continue;
			}

			if(isExitInTab(tLJAGetClaimSchema.getActuGetNo(), tLJAGetClaimSchema.getPolNo(), mBillID))
				return false;
	
			OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
			if(tOFInterfaceSchema != null)
				mOFInterfaceSet.add(tOFInterfaceSchema);
			dealLITranInfo();
		}
		return true;
	}


	/*****************************************************************************************************************************************************
	 * 错误处理函数开始
	 */
	private void dealError(String TabName, String RecNo, String PNo, String ErrNo, String BID, String ErrDesc)
	{
		LITranErrSchema tLITranErrSchema = new LITranErrSchema();
		tLITranErrSchema.setSerialNo("x"+PubFun1.CreateMaxNo("LITranErrNo",19));
		tLITranErrSchema.setBatchNo(mBatchNo);
		tLITranErrSchema.setBussDate(mToday);
		tLITranErrSchema.setTableName(TabName);
		tLITranErrSchema.setBussNo(RecNo);
		tLITranErrSchema.setErrFlag(ErrNo);
		tLITranErrSchema.setErrDes(ErrDesc);
		tLITranErrSchema.setPolicyNo(PNo);
		tLITranErrSchema.setBillId(BID);
		tLITranErrSchema.setManageCom(cManageCom);
		tLITranErrSchema.setMakeDate(PubFun.getCurrentDate());
		tLITranErrSchema.setMakeTime(PubFun.getCurrentTime());
		tLITranErrSchema.setOperator(mGlobalInput.Operator);
		if((tLITranErrSchema.getBussNo() == null) || (tLITranErrSchema.getPolicyNo() == null))
			return;
		mLITranErrSet.add(tLITranErrSchema);
	}

	private void dealLITranInfo()
	{
		LITranInfoSchema tLITranInfoSchema = new LITranInfoSchema();
		tLITranInfoSchema.setBatchNo(mBatchNo);
		tLITranInfoSchema.setBillId(mBillID);
		tLITranInfoSchema.setBussNo(mBussNo);
		tLITranInfoSchema.setPolNo(mPolNo);
		tLITranInfoSchema.setMakeDate(PubFun.getCurrentDate());
		tLITranInfoSchema.setMakeTime(PubFun.getCurrentTime());
		tLITranInfoSchema.setTransDate(this.mToday);
		tLITranInfoSchema.setVoucherType(this.mTime);
		tLITranInfoSchema.setManageCom(mManageCom);
		tLITranInfoSchema.setMatchID(mMatchID);
		if(!isExistInSet(tLITranInfoSchema))
			mLITranInfoSet.add(tLITranInfoSchema);
	}

	private OFInterfaceSchema entry()
	{
		String tMakeDate = PubFun.getCurrentDate();
		String tMakeTime = PubFun.getCurrentTime();
		mLineDescription = mManageCom + "||" + mBussNo + "||" + mInsuredName + "||" + mAttribute5 + "||" + mBillID; 
		// 公司段说明||收付单据号||保户人姓名||类别明细||BillID
		String[] tVerSubject = { mAccountSubCode, mManageCom, mRiskCode, mSaleChnl };

		try
		{
			tVerSubject = vertifySubject(mAccountCode); // 校验科目代码
			String tRecordID = PubFun1.CreateMaxNo("OTOF", "RECORD1");

			OFInterfaceSchema rOFInterfaceSchema = new OFInterfaceSchema();
			rOFInterfaceSchema.setRecordID(tRecordID); // 记录行ID
			rOFInterfaceSchema.setReversedStatus(mReversedStatus); // 冲消状态
			rOFInterfaceSchema.setOrigRowID(mOrigRowID); // 被冲消的行
			rOFInterfaceSchema.setReversedRowID(mReversedRowID); // 冲消生成的行
			rOFInterfaceSchema.setCurrencyCode(mCurrencyCode); // 币别
			rOFInterfaceSchema.setVoucherType(mVoucherType); // 凭证类别
			rOFInterfaceSchema.setManageCom(tVerSubject[1]); // 核算单位代码
			rOFInterfaceSchema.setSegment2(mSegment2); // 成本中心
			rOFInterfaceSchema.setAccountCode(mAccountCode); // 科目代码
			rOFInterfaceSchema.setAccountSubCode(tVerSubject[0]); // 科目明细代码
			rOFInterfaceSchema.setSaleChnl(tVerSubject[3]); // 销售渠道
			rOFInterfaceSchema.setRiskCode(tVerSubject[2]); // 保险产品代码
			rOFInterfaceSchema.setSegment7("NA"); // 备用段1
			rOFInterfaceSchema.setSegment8("NA"); // 备用段2
			rOFInterfaceSchema.setTransDate(mTransDate); // 事务日期
			rOFInterfaceSchema.setAccountingDate(mAccountingDate); // 记帐日期
			rOFInterfaceSchema.setMakeDate(tMakeDate); // 创建日期
			rOFInterfaceSchema.setMakeTime(tMakeTime); // 创建时间
			rOFInterfaceSchema.setModifyDate(tMakeDate); // 最后一次修改日期
			rOFInterfaceSchema.setModifyTime(tMakeTime); // 最后一次修改时间
			rOFInterfaceSchema.setMatchID(mMatchID); // 借贷关系key值
			rOFInterfaceSchema.setBatchNo(mBatchNo); // 批次号
			rOFInterfaceSchema.setEnteredDR(mEnteredDR); // 事务借计金额
			rOFInterfaceSchema.setEnteredCR(mEnteredCR); // 事务贷计金额
			rOFInterfaceSchema.setHeadDescription(mHeadDescription); // 日记帐摘要
			rOFInterfaceSchema.setLineDescription(mLineDescription); // 行记帐摘要
			rOFInterfaceSchema.setCurrencyConversionDate(""); // 汇率日期
			rOFInterfaceSchema.setCurrencyConversionRate(""); // 汇率
			rOFInterfaceSchema.setAccountedDR(""); // 记帐借计金额
			rOFInterfaceSchema.setAccountedCR(""); // 记帐贷计金额
			rOFInterfaceSchema.setAttribute1(""); // 空闲属性1
			rOFInterfaceSchema.setPolNo(mContNo); // 保单号
			rOFInterfaceSchema.setInsuredName(mInsuredName); // 被保险人姓名
			rOFInterfaceSchema.setBussNo(mBussNo); // 收付款单据号
			rOFInterfaceSchema.setAttribute5(mAttribute5); // 行为明细类别
			
			if("02".equals(mSaleChnl))  //个险
			{
				rOFInterfaceSchema.setAttribute6(mAgentName); //代理人名称
			}
			if("03".equals(mSaleChnl)) //银代
			{
				rOFInterfaceSchema.setAttribute6(mAgentCom); //焦谨让修改为代理机构（原来是银行编码）
			}
			if("7".equals(mBranchType)) //中介
			{
				rOFInterfaceSchema.setAttribute6(mAgentCom); //代理机构
			}
			rOFInterfaceSchema.setAttribute7("NA"); // 空闲属性7
			rOFInterfaceSchema.setAttribute8("NA"); // 空闲属性8
			if(mAccountCode.startsWith("保费收入"))
			{
				rOFInterfaceSchema.setAttribute9(mEndYear); //保险期间及单位		
			}
			rOFInterfaceSchema.setVoucherID("-1"); // 总帐凭证号回写
			rOFInterfaceSchema.setVoucherFlag("NA"); // 回写标志
			rOFInterfaceSchema.setVoucherDate(""); // 回写导入日期

			return rOFInterfaceSchema;
		}
		catch(Exception ex)
		{
			logger.debug("Exception in entry " + ex.toString());
			dealError("OFInterface", mBussNo, mPolNo, "012", String.valueOf(mMatchID), "接口表赋值错误");
			CError.buildErr(this, ex.toString());
			return null;
		}
	}

	/**
	 * 校验科目约束性
	 */
	private String[] vertifySubject(String cSubjectName)
	{
		String[] rSubject = { mAccountSubCode, mManageCom, mRiskCode, mSaleChnl };
		LIVertifySchema tLIVertifySchema = new LIVertifySchema();
        for(int i = 1 ; i <= mLIVertifySet.size() ; i ++)
        {
            tLIVertifySchema = (LIVertifySchema)mLIVertifySet.get(i);
            if(tLIVertifySchema.getSubjectName().equals(cSubjectName))
            {
                if(tLIVertifySchema.getSegAccountSub().equals("N"))
                    rSubject[0] = "NA";
                if(tLIVertifySchema.getSegComCode().equals("N"))
                    rSubject[1] = "NA";
                if(tLIVertifySchema.getSegRiskCode().equals("N"))
                    rSubject[2] = "NA";
                if(tLIVertifySchema.getSegSaleChnl().equals("N"))
                    rSubject[3] = "NA";

                break;
            }
        }
		return rSubject;
	}



	public static void main(String[] args)
	{
		OtoFUI tOtoFUI = new OtoFUI();
		VData vData = new VData();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86110000";
		String bdate = "2010-06-10";
		String edate = "2010-06-10";
		String tDateFlag = "0";

		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("mStartDate",bdate );
		mTransferData.setNameAndValue("cManageCom", tG.ManageCom);
		mTransferData.setNameAndValue("mEndDate", edate);
		mTransferData.setNameAndValue("itemp", 6);
		mTransferData.setNameAndValue("DateFlag",tDateFlag );
		mTransferData.setNameAndValue("mInputDate", "");
		vData.addElement(tG);
		vData.addElement(mTransferData);
		if(tOtoFUI.submitData(vData, "Buss")){
			System.out.println("正确");
		}
	}
	// 预留出对日志表的操作
	private boolean isExistInSet(LITranInfoSchema tLITranInfoSchema)
	{
		if (!mLITranInfoSetHashtable
				.contains((tLITranInfoSchema.getBillId().trim() + ":"
						+ tLITranInfoSchema.getBussNo().trim() + ":" + tLITranInfoSchema
						.getPolNo().trim()))) {
			mLITranInfoSetHashtable
					.add((tLITranInfoSchema.getBillId().trim() + ":"
							+ tLITranInfoSchema.getBussNo().trim() + ":" + tLITranInfoSchema
							.getPolNo().trim()));
			return false;
		}
		return true;
				
//		for(int i = 1; i <= mLITranInfoSet.size(); i++)
//		{
//			LITranInfoSchema ttLITranInfoSchema = new LITranInfoSchema();
//			ttLITranInfoSchema.setSchema(mLITranInfoSet.get(i));
//			if((ttLITranInfoSchema.getBillId().trim().equals(tLITranInfoSchema.getBillId().trim()))
//					&& (ttLITranInfoSchema.getBussNo().trim().equals(tLITranInfoSchema.getBussNo().trim()))
//					&& (ttLITranInfoSchema.getPolNo().trim().equals(tLITranInfoSchema.getPolNo().trim())))
//			{
//				return true;
//			}
//		}
//		return false;
	}

	private boolean isExitInTab(String tBussNo, String tPolNo, String tBillId)
	{
		if(tBillId == null || tBillId.equals(""))
		{
			dealError("LITranInfo", tBussNo, tPolNo, "010", "001", "科目代码为空");
			return true;
		}
		if(mContNo==null || "".equals(mContNo) || mPolNo==null || "".equals(mPolNo) )
		{
			dealError("LITranInfo", tBussNo, tBussNo, "010", "001", "保单号或者合同号为空");
			return true;
		}
		LITranInfoDB tLITranInfoDB = new LITranInfoDB();
		tLITranInfoDB.setBussNo(tBussNo);
		tLITranInfoDB.setBillId(tBillId);
		tLITranInfoDB.setPolNo(tPolNo);
		if(tLITranInfoDB.getCount() > 0)
		{
			dealError("LITranInfo", tBussNo, tPolNo, "011", "001", "该保单已经提取过相应的凭证");
			return true;
		}
		return false;
	}

	// 预留出对接口流转表的操作
	private void prepareOutputData()
	{
		MMap mp = new MMap();
		mp.put(mLITranInfoSet, "INSERT");
		mp.put(mLITranErrSet, "INSERT");
		mp.put(mOFInterfaceSet, "INSERT");
    	mp.put(tFinaLogSchema, "UPDATE");	
		mInputData.clear();
		mInputData.add(mp);
	}

	private boolean setPolInfo(String tPolNo, String tRiskCode,String tTableName, String tId)
	{

		String pSql ="";
		SQLwithBindVariables sqlbv45=new SQLwithBindVariables();
		//针对险种为000000的处理
		if(!( tRiskCode==null||tRiskCode.equals("") || "000000".equals(tRiskCode)))
		{
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			pSql = "select riskcode,salechnl,insuredname,conttype,payintv,grppolno,contno,agentcode,PayEndYear,PayEndYearFlag,(select branchtype from laagent where agentcode=lcpol.AgentCode),(select name from laagent where agentcode=lcpol.AgentCode),(select BankCode from LCCont where contno=lcpol.contno),AgentCom from lcpol where " 
		         + "polno='?tPolNo?' and riskcode='?tRiskCode?' and rownum=1  " 
		         + "union  "
			     + "select riskcode,salechnl,insuredname,conttype,payintv,grppolno,contno,agentcode,PayEndYear,PayEndYearFlag,(select branchtype from laagent where agentcode=lcpol.AgentCode),(select name from laagent where agentcode=lcpol.AgentCode),(select BankCode from LCCont where contno=lcpol.contno),AgentCom from lcpol where " 
			     + "contno='?tPolNo?' and riskcode='?tRiskCode?' and rownum=1 ";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				pSql = "select riskcode,salechnl,insuredname,conttype,payintv,grppolno,contno,agentcode,PayEndYear,PayEndYearFlag,(select branchtype from laagent where agentcode=lcpol.AgentCode),(select name from laagent where agentcode=lcpol.AgentCode),(select BankCode from LCCont where contno=lcpol.contno),AgentCom from lcpol where " 
				         + "polno='?tPolNo?' and riskcode='?tRiskCode?' limit 0,1  " 
				         + "union  "
					     + "select riskcode,salechnl,insuredname,conttype,payintv,grppolno,contno,agentcode,PayEndYear,PayEndYearFlag,(select branchtype from laagent where agentcode=lcpol.AgentCode),(select name from laagent where agentcode=lcpol.AgentCode),(select BankCode from LCCont where contno=lcpol.contno),AgentCom from lcpol where " 
					     + "contno='?tPolNo?' and riskcode='?tRiskCode?' limit 0,1 ";
			}
			sqlbv45.sql(pSql);
			sqlbv45.put("tPolNo", tPolNo);
			sqlbv45.put("tRiskCode",tRiskCode);
		}
		else			
			
		{
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			pSql = "select riskcode,salechnl,insuredname,conttype,payintv,grppolno,contno,agentcode,PayEndYear,PayEndYearFlag,(select branchtype from laagent where agentcode=lcpol.AgentCode),(select name from laagent where agentcode=lcpol.AgentCode),(select BankCode from LCCont where contno=lcpol.contno),AgentCom from lcpol where " 
		         + "polno='?tPolNo?'  and rownum=1  " 
		         + "union  "
			     + "select riskcode,salechnl,insuredname,conttype,payintv,grppolno,contno,agentcode,PayEndYear,PayEndYearFlag,(select branchtype from laagent where agentcode=lcpol.AgentCode),(select name from laagent where agentcode=lcpol.AgentCode),(select BankCode from LCCont where contno=lcpol.contno),AgentCom from lcpol where " 
			     + "contno='?tPolNo?' and rownum=1 ";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				pSql = "select riskcode,salechnl,insuredname,conttype,payintv,grppolno,contno,agentcode,PayEndYear,PayEndYearFlag,(select branchtype from laagent where agentcode=lcpol.AgentCode),(select name from laagent where agentcode=lcpol.AgentCode),(select BankCode from LCCont where contno=lcpol.contno),AgentCom from lcpol where " 
				         + "polno='?tPolNo?' limit 0,1 "
				         + "union  "
					     + "select riskcode,salechnl,insuredname,conttype,payintv,grppolno,contno,agentcode,PayEndYear,PayEndYearFlag,(select branchtype from laagent where agentcode=lcpol.AgentCode),(select name from laagent where agentcode=lcpol.AgentCode),(select BankCode from LCCont where contno=lcpol.contno),AgentCom from lcpol where " 
					     + "contno='?tPolNo?' limit 0,1 ";
			}
			sqlbv45.sql(pSql);
			sqlbv45.put("tPolNo", tPolNo);
			}


		
		ExeSQL mExeSQL = new ExeSQL();
		SSRS mSSRS = new SSRS();
		mSSRS = mExeSQL.execSQL(sqlbv45);
		if(mSSRS.getMaxRow() <= 0)
		{
			SQLwithBindVariables sqlbv46=new SQLwithBindVariables();
			String gpSql = "";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				gpSql = "select riskcode,salechnl,grpname,payintv,agentcode from lcgrppol where " 
				         + "grppolno='?tPolNo?' and rownum=1 " ;
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				gpSql = "select riskcode,salechnl,grpname,payintv,agentcode from lcgrppol where " 
				         + "grppolno='?tPolNo?' limit 0,1 ";
			}
		 sqlbv46.sql(gpSql);
		 sqlbv46.put("tPolNo", tPolNo);
			
			SSRS tSSRS = new SSRS();
			tSSRS = mExeSQL.execSQL(sqlbv46);
			if(tSSRS == null || tSSRS.getMaxRow() <= 0)
			{
				SQLwithBindVariables sqlbv47=new SQLwithBindVariables();
				String gSql="";
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					gSql = "select riskcode,salechnl,grpname,payintv,agentcode from lcgrppol where " 
					         + "grpcontno='?tPolNo?' and rownum=1 " ;
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					gSql = "select riskcode,salechnl,grpname,payintv,agentcode from lcgrppol where " 
					         + "grpcontno='?tPolNo?' limit 0,1 ";
				}
				sqlbv47.sql(gSql);
				sqlbv47.put("tPolNo", tPolNo);
				SSRS gSSRS = new SSRS();
				gSSRS = mExeSQL.execSQL(sqlbv47);
				if(gSSRS == null || gSSRS.getMaxRow() <= 0)
				{
					dealError(tTableName, mBussNo, tPolNo, "001", tId, "lcgrppol表对应的保单信息不存在");
					return false;
				}
				else
				{
					mRiskCode = gSSRS.GetText(1, 1);
					mSaleChnl =tranSaleChnl(gSSRS.GetText(1, 2),gSSRS.GetText(1, 5),"");
					mInsuredName = gSSRS.GetText(1, 3).trim();
					mPayIntv = Integer.parseInt(gSSRS.GetText(1, 4));
					if(mInsuredName.length() > 20)
						mInsuredName = mInsuredName.substring(0, 20);
				}
			}
			else
			{
				mRiskCode = tSSRS.GetText(1, 1);
//				mSaleChnl = mSSRS.GetText(1, 2);
				mSaleChnl =tranSaleChnl(tSSRS.GetText(1, 2),tSSRS.GetText(1, 5),"");
				mInsuredName = tSSRS.GetText(1, 3).trim();
				mPayIntv = Integer.parseInt(tSSRS.GetText(1, 4));
				if(mInsuredName.length() > 20)
					mInsuredName = mInsuredName.substring(0, 20);
			}
		}
		else
		{
				mRiskCode = mSSRS.GetText(1, 1);
//				mSaleChnl = mSSRS.GetText(1, 2);
				mSaleChnl = tranSaleChnl(mSSRS.GetText(1, 2),mSSRS.GetText(1, 8),mSSRS.GetText(1, 7));
				mInsuredName = mSSRS.GetText(1, 3);
				mPayIntv = Integer.parseInt(mSSRS.GetText(1, 5));		
				mEndYear = mSSRS.GetText(1, 9) + mSSRS.GetText(1, 10);	
				mBranchType = mSSRS.GetText(1, 11);
		    	mAgentName = mSSRS.GetText(1, 12);
		    	mBankCode  = mSSRS.GetText(1, 13);
		     	mAgentCom = mSSRS.GetText(1, 14);

		}
		return true;
	}

	private LCPolBL getPol(String tPolNo, String tId)
	{
		LCPolBL tLCPolBL = new LCPolBL();
		tLCPolBL.setPolNo(tPolNo);
		if(!tLCPolBL.getInfo())
		{
			dealError("LCPOL", mBussNo, tPolNo, "001", tId, "保单号为" + tPolNo + "对应保单信息不存在");
			return null;
		}
		mRiskCode = tLCPolBL.getRiskCode();
//		mSaleChnl = tLCPolBL.getSaleChnl();
		mSaleChnl = tranSaleChnl(tLCPolBL.getSaleChnl(), tLCPolBL.getAgentCode(), tLCPolBL.getContNo());
		mInsuredName = tLCPolBL.getInsuredName();
        mEndYear = tLCPolBL.getPayEndYear()+tLCPolBL.getPayEndYearFlag();
        
        String aSql ="select branchtype,name from laagent where agentcode='?agentcode?'";
        SQLwithBindVariables sqlbv48=new SQLwithBindVariables();
        sqlbv48.sql(aSql);
        sqlbv48.put("agentcode", tLCPolBL.getAgentCode());
    	ExeSQL aExeSQL = new ExeSQL();
    	SSRS aSsrs = aExeSQL.execSQL(sqlbv48);
    	mBranchType = aSsrs.GetText(1, 1);
    	mAgentName = aSsrs.GetText(1, 2);
    	
    	String bSql ="select BankCode from LCCont where contno='?contno?'";
    	SQLwithBindVariables sqlbv49=new SQLwithBindVariables();
    	sqlbv49.sql(bSql);
    	sqlbv49.put("contno", tLCPolBL.getContNo());
     	ExeSQL bExeSQL = new ExeSQL();
     	SSRS bSsrs = bExeSQL.execSQL(sqlbv49);
     	mBankCode  = bSsrs.GetText(1, 1);
    	
     	mAgentCom = tLCPolBL.getAgentCom();
		
		return tLCPolBL;
	}

	private LCGrpPolBL getGrpPol(String tPolNo, String tId)
	{
		LCGrpPolBL tLCGrpPolBL = new LCGrpPolBL();
		tLCGrpPolBL.setGrpPolNo(tPolNo);
		if(!tLCGrpPolBL.getInfo())
		{
			dealError("LCGRPPOL", mBussNo, tPolNo, "001", tId, "保单号为" + tPolNo + "对应保单信息不存在");
			return null;
		}
		mRiskCode = tLCGrpPolBL.getRiskCode();
//		mSaleChnl = tLCGrpPolBL.getSaleChnl();
		mSaleChnl = tranSaleChnl(tLCGrpPolBL.getSaleChnl(), tLCGrpPolBL.getAgentCode(), tLCGrpPolBL.getGrpContNo());
		mInsuredName = tLCGrpPolBL.getGrpName();
		return tLCGrpPolBL;
	}

	/**
	 * 由于保全有些项目针对合同，所以相应的grppolno就为20个0，所以针对这种情况则查询相应的合同下的任一条保单信息
	 * 
	 * **/
	private boolean getEGrpPol(String tGrpContno,String tRiskcode, String tId)
	{
		String eqSql="";
		SQLwithBindVariables sqlbv50=new SQLwithBindVariables();
		if("".equals(tRiskcode) || tRiskcode==null || "000000".equals(tRiskcode))
		{
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			eqSql= "select riskcode,salechnl,grpname,agentcode,grppolno from lcgrppol where grpcontno='?tGrpContno?' and rownum=1 " ;
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				eqSql= "select riskcode,salechnl,grpname,agentcode,grppolno from lcgrppol where grpcontno='?tGrpContno?' limit 0,1 " ;	
			}
			sqlbv50.sql(eqSql);
			sqlbv50.put("tGrpContno", tGrpContno);
		}
			
		else{
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				eqSql= "select riskcode,salechnl,grpname,agentcode,grppolno from lcgrppol where grpcontno='?tGrpContno?' and riskcode='?tRiskcode?' and rownum=1 " ;
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				eqSql= "select riskcode,salechnl,grpname,agentcode,grppolno from lcgrppol where grpcontno='?tGrpContno?' and riskcode='?tRiskcode?' limit 0,1 " ;	
			}
			sqlbv50.sql(eqSql);
			sqlbv50.put("tGrpContno", tGrpContno);
			sqlbv50.put("tRiskcode", tRiskcode);
		}
			
		SSRS tSSRS = new SSRS();
		ExeSQL gExeSQL = new ExeSQL();
		tSSRS = gExeSQL.execSQL(sqlbv50);
		if(tSSRS == null || tSSRS.getMaxRow() <= 0)
		{
			dealError("lcgrppol", mBussNo, tGrpContno, "001", tId, "lcgrppol表对应的保单信息不存在");
			return false;
		}
		else
		{
			mContNo = tGrpContno;
			mPolNo  =  tSSRS.GetText(1,5);
			mRiskCode = tSSRS.GetText(1, 1);
			mSaleChnl =tranSaleChnl(tSSRS.GetText(1, 2),tSSRS.GetText(1, 4),tGrpContno);
			mInsuredName = tSSRS.GetText(1, 3).trim();
			if(mInsuredName.length() > 20)
				mInsuredName = mInsuredName.substring(0, 20);
		}
		return true;
	}
	
	private void initVar()
	{
		mReversedStatus = "0"; // 冲消状态
		mOrigRowID = ""; // 被冲消的行
		mReversedRowID = ""; // 冲消生成的行
		mCurrencyCode = "CNY"; // 币别
		mVoucherType = String.valueOf(mTime); // 凭证类别
		mManageCom = "NA"; // 核算单位代码
		mSegment2 = "NA"; // 成本中心
		mAccountCode = "NA"; // 科目代码
		mAccountSubCode = "NA"; // 科目明细代码
		mSaleChnl = "NA"; // 销售渠道
		mRiskCode = "NA"; // 保险产品代码
		mTransDate = ""; // 事务日期
		mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate; // 记帐日期
		mEnteredDR = ""; // 事务借计金额
		mEnteredCR = ""; // 事务贷计金额
		mHeadDescription = "NA"; // 日记帐摘要
		mLineDescription = "NA"; // 行记帐摘要
		mPolNo = ""; // 保单号
		mInsuredName = ""; // 被保险人姓名
		mBussNo = ""; // 收付款单据号
		mAttribute5 = "核心业务系统"; // 行为明细类别－来源
		mAttribute6 = "NA";
	}

	private String getRiskPeriod(String riskcode, String tID)
	{
		String rpSql = "select RiskPeriod from lmriskapp where riskcode='?riskcode?'";
		SQLwithBindVariables sqlbv51=new SQLwithBindVariables();
		sqlbv51.sql(rpSql);
		sqlbv51.put("riskcode", riskcode);
		SSRS tssrs = new ExeSQL().execSQL(sqlbv51);
		if(tssrs.mErrors.needDealError() == true || tssrs.getMaxRow() <= 0)
		{
			dealError("lmriskapp", mBussNo, mPolNo, "014", tID, "获取险种的长短期类型失败！");
			return "";
		}
		String mRiskPeriod = tssrs.GetText(1, 1).trim();
		return mRiskPeriod;

	}

    /**
     * 核心系统渠道的升级：
     * @param salechnl
     * 个险、团单、银代、中介、续收、联办、收展
     */
    private String tranSaleChnl(String mSaleChnl,String mAgentCode,String mContno)
    {
    	String rSaleChnl=mSaleChnl;
    	String rSql ="select branchtype,name from laagent where agentcode='?mAgentCode?'";
    	SQLwithBindVariables sqlbv52=new SQLwithBindVariables();
    	sqlbv52.sql(rSql);
    	sqlbv52.put("mAgentCode", mAgentCode);
    	ExeSQL tExeSQL = new ExeSQL();
    	SSRS rSsrs = tExeSQL.execSQL(sqlbv52);

        //团险的拆分
        if("01".equals(rSaleChnl))
        {
        	if(rSsrs.MaxRow>0)
        	{
	            if("2".equals(rSsrs.GetText(1, 1))  && ("LB".equals(rSsrs.GetText(1, 2).substring(0,2))))
	            {
	            	rSaleChnl="07";   //联办
	            }
        	}

        }
        //专业的拆分
        if("05".equals(rSaleChnl) || "06".equals(rSaleChnl) || "08".equals(rSaleChnl) || "09".equals(rSaleChnl))
        {
        	String cSql ="select agentcom from lccont where contno='?mContno?' and grpcontno='00000000000000000000' ";
        	SQLwithBindVariables sqlbv53=new SQLwithBindVariables();
        	sqlbv53.sql(cSql);
        	sqlbv53.put("mContno", mContno);
        	SSRS cSSrs = tExeSQL.execSQL(sqlbv53);
        	if(cSSrs.MaxRow>0)
        	{
	        	if((cSSrs.GetText(1, 1).trim().length()==13 || cSSrs.GetText(1, 1).trim().length()==16)
	        			&&("86".equals(cSSrs.GetText(1, 1).trim().substring(0,2))) && ("8".equals(cSSrs.GetText(1, 1).trim().substring(8,9))))
	        	{
	        		rSaleChnl="07";   //联办
	        	}
        	}

        }       
        return rSaleChnl;
    } 
//	private String getYBankCode(String bankcode, String managecom)
//	{
//		String bSql = "select code from ldcode where codetype='bank' and code like '01%' and trim(comcode)=substr("
//				+ managecom + ",0,4) and rownum=1";
//		ExeSQL tExeSQL = new ExeSQL();
//		String mBankCode = tExeSQL.getOneValue(bSql);
//		return mBankCode;
//
//	}
	private String getYBTPayMode(String tPayNo)
	{
        if(tPayNo == null || tPayNo.equals(""))
            return "";
        String tSql = "select paymode from ljtempfeeclass where otherno='?tPayNo?' ";
        SQLwithBindVariables sqlbv54=new SQLwithBindVariables();
        sqlbv54.sql(tSql);
        sqlbv54.put("tPayNo", tPayNo);
        ExeSQL mExeSQL = new ExeSQL();
        String tPayMode=mExeSQL.getOneValue(sqlbv54);
        if(tPayMode==null || "".equals(tPayMode))
        	tPayMode="A";

        return tPayMode;
	}
}
