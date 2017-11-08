package com.sinosoft.lis.otof;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

import java.text.*;
import java.util.*;

public class OtoFWTBL
{
private static Logger logger = Logger.getLogger(OtoFWTBL.class);


	/**
	 * @健康险委托产品凭证提取
	 */

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
	private String mInputDate = ""; //界面传入的记帐日期

	private String tmoney = ""; //>金额</money>


	private String mBillID = ""; //对应凭证科目，便于查找 == 凭证类别+凭证分类(一张凭证内部有多个分类的情况)+内部编码
	private String mReversedStatus = ""; //冲消状态
	private String mOrigRowID = ""; //被冲消的行
	private String mReversedRowID = ""; //冲消生成的行
	private String mCurrencyCode = ""; //币别
	private String mVoucherType = ""; //凭证类别
	private String mManageCom = ""; //核算单位代码
	private String mSegment2 = ""; //成本中心
	private String mAccountCode = ""; //科目代码
	private String mAccountSubCode = ""; //科目明细代码
	private String mSaleChnl = ""; //销售渠道
	private String mRiskCode = ""; //保险产品代码
	private String mTransDate = ""; //事务日期
	private String mAccountingDate = ""; //记帐日期
	private int mMatchID = 0; //借贷关系key值
	private String mBatchNo = ""; //批次号
	private String mEnteredDR = ""; //事务借计金额
	private String mEnteredCR = ""; //事务贷计金额
	private String mHeadDescription = ""; //日记帐摘要
	private String mLineDescription = ""; //行记帐摘要
	private String mPolNo = ""; //保单号
	private String mContNo="";
	private String mInsuredName = ""; //被保险人姓名
	private String mBussNo = ""; //收付款单据号
	private String mAttribute5 = ""; //行为明细类别
	private String mAttribute6 = ""; //代理机构（航意险应收凭证）
	private Date dbdate;
	private Date dedate;
	private GlobalInput mGlobalInput = new GlobalInput();
	private LIVertifySet mLIVertifySet = new LIVertifySet();         //科目约束性



	private String cManageCom = ""; //提取管理机构

	//对表的定义
	OFInterfaceSet mOFInterfaceSet = new OFInterfaceSet();
	LITranInfoSet mLITranInfoSet = new LITranInfoSet();
	LITranErrSet mLITranErrSet = new LITranErrSet();
	private OtoFLockBL tOtoFLockBL = new OtoFLockBL();
	private String tRecordNo;     //进行锁表的管理机构
	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		logger.debug("--- OtoFWTBL begin ---");
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

			if(!dealData())
			{
				return false;
			}

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
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql("select * from livertify");
        mLIVertifySet = (LIVertifySet)tLIVertifyDB.executeQuery(sqlbv);


		return true;
	}
	
	
	private boolean dealData()
	{
		while(dbdate.compareTo(dedate) <= 0)
		{
			FDate chgdate = new FDate();
			mToday = chgdate.getString(dbdate);
			
	        tRecordNo="WT"+cManageCom+new SimpleDateFormat("yyyyMMdd").format(dbdate)+String.valueOf(mTime);
      

	    	logger.debug("tRecordNo---------"+tRecordNo);
	    	//调用并发控制程序

	    	if(!tOtoFLockBL.lock(tRecordNo, cManageCom, String.valueOf(mTime),mStartDate,mGlobalInput))
	    	{
	    		CError.buildErr(this,  "自动提取加锁或者解锁失败");
	            return false;
	    	}
	    	else
	    	{   	  	 	
		        //针对自动运行的并发控制，且一天的一中类型的凭证的批次号一致
		        if("1".equals(DateFlag))
		        {
	        		OFinaLogDB tFinaLogDB = new OFinaLogDB();
	        		tFinaLogDB.setRecordNo(tRecordNo);
	        		mBatchNo = tFinaLogDB.query().get(1).getBatchNo(); 
	        		mMatchID = (int)tFinaLogDB.query().get(1).getMatchID();//一个批次下mMatchID不重复

		        }
		        else
		           mBatchNo = PubFun1.CreateMaxNo("OTOF","20");
	    	}
			// 准备所有要打印的数据
			if(!getVoucherData(mTime))
			{
				CError.buildErr(this, "凭证提取错误!");
				return false;
			}
			dbdate = PubFun.calDate(dbdate, 1, "D", null);
		}

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
				case 1: //暂收保费
					if (!getFeeDataTemp())
						return false;
					break;
				case 2:
					if (!getFeeDataAdvance())
						return false;
					break;
				case 3: //保费收入
					if (!getFeeDataPrem())
						return false;
					break;
				case 4: //赔款支出
					if (!PayFeeDataClaimJ())
						return false;
					break;
				case 5: //保全应付
					if (!PayFeeDataEndordseJ())
						return false;
					break;
				case 6:
					break;
				case 7: //其他应付
					if (!PayFeeDataJ())
						return false;
					break;
				case 8: //实付
					if (!PayFeeDataD())
						return false;
					break;
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
		//暂收费
		String tSql = "select distinct(tempfeeno) from LJTempFee a where Confmakedate='" + "?g1?" + "' and ManageCom "
					+ "like concat('" + "?g2?"+ "','%') and TempFeeType='1' and exists(select 1 from lmriskapp where "
				    + "riskcode=a.riskcode and risktype='H' and healthtype='1') and exists( "
				    + "select 1 from ljtempfeeclass b where tempfeeno = a.tempfeeno and "
				    + "(paymode not in ('4','A','B') or (paymode in ('4','A','B')  "//当银行代收时分开处理集中代收和独立代收
				    + "and exists(select 1 from ldcode where codetype='bank' and trim(code) = trim(b.bankcode) "
				    + "and rpad(trim(comcode),6,'0') = substr(b.managecom,1,6))) ))";
		  SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
          sqlbv1.sql(tSql);
          sqlbv1.put("g1", mToday);
          sqlbv1.put("g2", cManageCom);
		logger.debug(tSql);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv1);
		for (int i = 1; i <= tSSRS.MaxRow; i++)
		{
			String tTempfeeno = tSSRS.GetText(i, 1);
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
			String tSql1 = "select * from LJTempFee " + "where tempfeeno='" + "?a1?" + "' order by paymoney desc";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	          sqlbv2.sql(tSql1);
	          sqlbv2.put("a1", tTempfeeno.trim());
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv2);
			String tSql2 = "select * from LJTempFeeClass " + "where tempfeeno='" + "?a1?" + "' order by paymoney desc";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	          sqlbv3.sql(tSql2);
	          sqlbv3.put("a1", tTempfeeno.trim());
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv3);
			double tLJTempFeeSum = 0;
			double tLJTempFeeClassSum = 0;
			for (int k = 1; k <= tLJTempFeeSet.size(); k++)
			{
				tLJTempFeeSum = tLJTempFeeSum + tLJTempFeeSet.get(k).getPayMoney();
			}
			for (int k = 1; k <= tLJTempFeeClassSet.size(); k++)
			{
				tLJTempFeeClassSum = tLJTempFeeClassSum + tLJTempFeeClassSet.get(k).getPayMoney();
			}
			if (Math.abs((tLJTempFeeSum - tLJTempFeeClassSum)) >= 0.001)
			{
				dealError("LJTempFee", tTempfeeno, tTempfeeno, "006", "001", "暂收费与暂收费分类金额不一致");
				continue;
			}

			for (int j = 1; j <= tLJTempFeeSet.size(); j++)
			{
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema = tLJTempFeeSet.get(j);
				if (tLJTempFeeSchema.getPayMoney() == 0 )
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "004",
							"001", "暂收费金额为非正数");
					continue;
				}
				if(tLJTempFeeSchema.getManageCom().trim().length() == 2)
				{
					dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "007",
							"001", "管理机构为2位，不予提取");
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
				mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 6); //所有支票都转入商业银行账户
				mRiskCode = tLJTempFeeSchema.getRiskCode();



				String tFailFlag = "0";
				//取值
				LJTempFeeClassSet ttLJTempFeeClassSet = this.getAccTemp(1, tLJTempFeeSchema.getPayMoney(), tLJTempFeeClassSet);
				for (int k = 1; k <= ttLJTempFeeClassSet.size(); k++)
				{
					LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
					tLJTempFeeClassSchema.setSchema(ttLJTempFeeClassSet.get(k));
					tLJTempFeeClassSchema.setPayMoney(new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema.getPayMoney())));
					if (tLJTempFeeClassSchema.getPayMoney() < 0.01)
					{
						dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "004",
								"001", "暂收分类金额为非正数");
						continue;
					}

					mHeadDescription = "分类收健康保障委托管理资金!";
					tmoney = new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema.getPayMoney()));

					for (int t = 1; t <= 2; t++)
					{
						if (t == 1)
						{
							if (tLJTempFeeClassSchema.getPayMode().equals("1"))
							{
								mBillID = "0100";
								mAccountCode = "库存现金-人民币-业务";
							} else if (tLJTempFeeClassSchema.getPayMode().equals("5"))
							{
								mBillID = "0101";
								mAccountCode = "应付业务支出-内部转帐"; //内部转账借
							} else
							{
								if (tLJTempFeeClassSchema.getPayMode().equals("3"))
								{
									mBillID = "0102";
									mAccountCode = "银行存款-活期-人民币"; //银行支票
									mAccountSubCode = "支" + mManageCom;
								} else
								{
									if (tLJTempFeeClassSchema.getBankCode() == null) //银行托收银行代码为空
									{
										dealError("LJTempFeeClass", tLJTempFeeClassSchema.getTempFeeNo(),
												tLJTempFeeSchema.getOtherNo(), "008", "001", "银行托收/现金支票银行代码为空");
										tFailFlag = "1";
										break;
									}
									mBillID = "0103";
									mAccountCode = "银行存款-活期-人民币"; //银行存款
									mAccountSubCode = "收" + tLJTempFeeClassSchema.getBankCode();
								}
							}

							mEnteredDR = String.valueOf(tmoney);
							mEnteredCR = "";
						} else
						{
							//贷方如果有问题，则借方不再处理
							if (tFailFlag.equals("1"))
								continue;

							mBillID = "0112";
							mAccountCode = "其他应付款-健康保障委托管理业务-企事业团体补充医疗（进帐）"; //暂收
							mAccountSubCode="NA";
							mEnteredDR = "";
							mEnteredCR = String.valueOf(tmoney);

						}

						if (isExitInTab(mBussNo, null, mBillID))
						{
							tFailFlag = "1";
							break;
						}
						OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
						if (tOFInterfaceSchema != null)
							mOFInterfaceSet.add(tOFInterfaceSchema);

						dealLITranInfo();
					}
				}
			}
		}

		//处理集中代收的数据
		String sql = "select distinct(tempfeeno) from LJTempFee a where Confmakedate='" + "?b1?"+ "' and "
				   + "ManageCom like concat('"+ "?b2?"+ "','%') and TempFeeType='1' and exists(select 1 from lmriskapp where "
    		       + "riskcode=a.riskcode and risktype='H' and healthtype='1') and exists( select 1 from ljtempfeeclass b where "
				   + "tempfeeno = a.tempfeeno and bankcode is not null and paymode in( '4','A','B' ) " //当为银行代收时分开处理集中代收和独立代收
				   + "and exists(select 1 from ldcode  where  codetype='bank' and trim(code) = trim(b.bankcode) "
				   + "and rpad(trim(comcode),6,'0') <> substr(b.managecom,1,6)))";
		  SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
          sqlbv3.sql(sql);
          sqlbv3.put("b1", mToday);
          sqlbv3.put("b2", cManageCom);
		logger.debug(sql);
		tExeSQL = new ExeSQL();
		SSRS ssrs = tExeSQL.execSQL(sqlbv3);
		for (int i = 1; i <= ssrs.MaxRow; i++)
		{
			String tTempfeeno = ssrs.GetText(i, 1);
			LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();

			String tSql1 = "select * from LJTempFee " + "where tempfeeno='" + "?h1?" + "' order by paymoney desc";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
	          sqlbv4.sql(tSql1);
	          sqlbv4.put("h1", tTempfeeno.trim());
			String tSql2 = "select * from LJTempFeeClass " + "where tempfeeno='" + "?h2?" + "' order by paymoney desc";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
	          sqlbv5.sql(tSql2);
	          sqlbv5.put("h2", tTempfeeno.trim());
			LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv4);
			LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv5);
			double tLJTempFeeSum = 0;
			double tLJTempFeeClassSum = 0;
			for (int k = 1; k <= tLJTempFeeSet.size(); k++)
			{
				tLJTempFeeSum = tLJTempFeeSum + tLJTempFeeSet.get(k).getPayMoney();
			}
			for (int k = 1; k <= tLJTempFeeClassSet.size(); k++)
			{
				tLJTempFeeClassSum = tLJTempFeeClassSum + tLJTempFeeClassSet.get(k).getPayMoney();
			}
			if (Math.abs((tLJTempFeeSum - tLJTempFeeClassSum)) >= 0.001)
			{
				dealError("LJTempFee", tTempfeeno, tTempfeeno, "006", "001", "暂收费与暂收费分类金额不一致");
				continue;
			}

			for (int j = 1; j <= tLJTempFeeSet.size(); j++)
			{
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema = tLJTempFeeSet.get(j);
				if (tLJTempFeeSchema.getPayMoney() == 0 || tLJTempFeeSchema.getManageCom().trim().length() == 2)
					continue;

				initVar();
				mMatchID++;
				mPolNo = tLJTempFeeSchema.getOtherNo();
				mContNo = tLJTempFeeSchema.getOtherNo();
				mBussNo = tLJTempFeeSchema.getTempFeeNo();
				mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
				mTransDate = tLJTempFeeSchema.getConfMakeDate();
				mRiskCode = tLJTempFeeSchema.getRiskCode();

				String tFailFlag = "0";
				//取值
				LJTempFeeClassSet ttLJTempFeeClassSet = this.getAccTemp(1, tLJTempFeeSchema.getPayMoney(), tLJTempFeeClassSet);
				for (int k = 1; k <= ttLJTempFeeClassSet.size(); k++)
				{
					LJTempFeeClassSchema tLJTempFeeClassSchema = new LJTempFeeClassSchema();
					tLJTempFeeClassSchema.setSchema(ttLJTempFeeClassSet.get(k));
					tLJTempFeeClassSchema.setPayMoney(new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema.getPayMoney())));
					if (tLJTempFeeClassSchema.getPayMoney() < 0.01)
						continue;

					mSegment2 = tLJTempFeeSchema.getManageCom();
					mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 6); //所有支票都转入商业银行账户	
					mHeadDescription = "分类收健康保障委托管理资金!";
					tmoney = new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema.getPayMoney()));
					String tBankCom =getBankCom(tLJTempFeeClassSchema.getBankCode(), "001");
					for (int t = 1; t <= 2; t++)
					{
						if (t == 1)
						{
							//判断是否是分公司代支公司集中扣款
							if (!tLJTempFeeClassSchema.getPayMode().equals("1") && !tLJTempFeeClassSchema.getPayMode().equals("5"))
							{
								if (!tBankCom.equals(mManageCom))
								{
									mBillID = "0105";
									mAccountCode = "其他应收款-资金中转-集中批收"; //集中批收的处理
									mAccountSubCode = "C" + tLJTempFeeSchema.getManageCom().substring(2, 4).concat("0000"); //分公司代码转化，如86513400分公司记为C510100
									mEnteredDR = String.valueOf(tmoney);
									mEnteredCR = "";
									if (isExitInTab(mBussNo, null, mBillID))
									{
										tFailFlag = "1";
										break;
									}

									OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
									if (tOFInterfaceSchema != null)
										mOFInterfaceSet.add(tOFInterfaceSchema);

									dealLITranInfo();

								}

								mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 4).concat("00");
								mSegment2 = mManageCom.concat("00"); //分公司帐套记录
								if (tLJTempFeeClassSchema.getPayMode().equals("3"))
								{
									mBillID = "0106";
									mAccountCode = "银行存款-活期-人民币"; //银行支票
									mAccountSubCode = "支" + mManageCom;
								} else
								{
									if (tLJTempFeeClassSchema.getBankCode() == null) //银行托收银行代码为空
									{
										dealError("LJTempFeeClass", tLJTempFeeClassSchema.getTempFeeNo(),
												tLJTempFeeClassSchema.getTempFeeNo(), "008", "001", "银行集中批收的银行编码为空");	
										tFailFlag = "1";
										break;
									}
									mBillID = "0107";
									mAccountCode = "银行存款-活期-人民币"; //银行存款
									mAccountSubCode = "收" + tLJTempFeeClassSchema.getBankCode();
								}

							}
							mEnteredDR = String.valueOf(tmoney);
							mEnteredCR = "";
						} 
						else
						{
							//贷方如果有问题，则借方不再处理
							if (tFailFlag.equals("1"))
								continue;

							mBillID = "0108";
							mAccountCode = "其他应付款-资金中转-集中批收"; //暂收
							mAccountSubCode = "C" + tLJTempFeeSchema.getManageCom().substring(2, 6).concat("00"); //支公司的科目改变，如86513400－C513400
							mEnteredDR = "";
							mEnteredCR = String.valueOf(tmoney);
							if (isExitInTab(mBussNo, null, mBillID))
							{
								tFailFlag = "1";
								break;
							}

							OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
							if (tOFInterfaceSchema != null)
								mOFInterfaceSet.add(tOFInterfaceSchema);

							dealLITranInfo();

							mSegment2 = tLJTempFeeSchema.getManageCom();
							mManageCom = tLJTempFeeSchema.getManageCom().substring(0, 6); //所有支票都转入商业银行账户
							if (!tBankCom.equals(mManageCom))
							{

								mBillID = "0114";
								mAccountCode = "其他应付款-健康保障委托管理业务-企事业团体补充医疗（进帐）"; //暂收
								mAccountSubCode = "NA";
								mHeadDescription = "收暂收保费！";
								mEnteredDR = "";
								mEnteredCR = String.valueOf(tmoney);
							}
						}

						if (isExitInTab(mBussNo, null, mBillID))
						{
							tFailFlag = "1";
							break;
						}

						OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
						if (tOFInterfaceSchema != null)
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
        //暂收费
        String tSql = "select distinct(tempfeeno) from LJTempFee a where Confmakedate='"+"?n1?"+"' and ManageCom like concat('"+"?n2?"+"','%') "
                    + "and TempFeeType<>'1' and exists( select 1 from ljtempfeeclass b where tempfeeno = a.tempfeeno and "
                    + "((paymode = '4'  and exists(select 1 from ldcode where codetype='bank' and  "//当为银行代收时分开处理集中代收和独立代收
                    + "trim(code) = trim(b.bankcode) and rpad(trim(comcode),6,'0') = substr(b.managecom,1,6))) "
                    + "or paymode <> '4')) and exists(select 1 from lmriskapp where riskcode=a.riskcode and risktype='H' and healthtype='1')";
        SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
        sqlbv5.sql(tSql);
        sqlbv5.put("n1", mToday);
        sqlbv5.put("n2", cManageCom);
        
        logger.debug(tSql);
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(sqlbv5);
        for (int i=1;i<=tSSRS.MaxRow;i++)
        {
            String tTempfeeno = tSSRS.GetText(i,1);
            LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
            LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
            String tSql1 = "select * from LJTempFee "
                         + "where tempfeeno='"+"?n4?"+"'  order by paymoney desc";
            SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
            sqlbv6.sql(tSql1);
            sqlbv6.put("n4", tTempfeeno.trim());
            
            LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv6);
            String tSql2 = "select * from LJTempFeeClass "
                         + "where tempfeeno='"+"?tempfeeno?"+"'  order by paymoney desc";
            SQLwithBindVariables sqlbv=new SQLwithBindVariables();
            sqlbv.sql(tSql2);
            sqlbv.put("tempfeeno", tTempfeeno.trim());
            LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv);
            double tLJTempFeeSum=0;
            double tLJTempFeeClassSum = 0;
            for (int k = 1; k <= tLJTempFeeSet.size(); k++) 
            {
              tLJTempFeeSum = tLJTempFeeSum + tLJTempFeeSet.get(k).getPayMoney();
            }
            for (int k = 1; k <= tLJTempFeeClassSet.size(); k++) 
            {
              tLJTempFeeClassSum = tLJTempFeeClassSum +
                  tLJTempFeeClassSet.get(k).getPayMoney();
            }
            if (Math.abs( (tLJTempFeeSum - tLJTempFeeClassSum)) >= 0.001)			
            {
				dealError("LJTempFee", tTempfeeno, tTempfeeno, "006", "002", "暂收费与暂收费分类金额不一致");
				continue;
			}

            for (int j=1;j<=tLJTempFeeSet.size();j++)
            {
                LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
                tLJTempFeeSchema = tLJTempFeeSet.get(j);
                if (tLJTempFeeSchema.getPayMoney()==0 || tLJTempFeeSchema.getManageCom().trim().length()==2)
                    continue;

                if(tLJTempFeeSchema.getPayMoney() > 0)
                {
                    initVar();
                    mMatchID ++;
                    mPolNo = tLJTempFeeSchema.getOtherNo();
                    mContNo = tLJTempFeeSchema.getOtherNo();
                    mBussNo = tLJTempFeeSchema.getTempFeeNo();
                    mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
                    mTransDate = tLJTempFeeSchema.getConfMakeDate() ;
                    mSegment2 = tLJTempFeeSchema.getManageCom();
                    mManageCom = tLJTempFeeSchema.getManageCom().substring(0,6);
                    mRiskCode = tLJTempFeeSchema.getRiskCode();

                    //取值
                    String tFailFlag="0";
                    LJTempFeeClassSet ttLJTempFeeClassSet = this.getAccTemp(1,tLJTempFeeSchema.getPayMoney(),tLJTempFeeClassSet);
                    for (int k=1;k<=ttLJTempFeeClassSet.size();k++)
                    {
                        LJTempFeeClassSchema tLJTempFeeClassSchema=new LJTempFeeClassSchema();
                        tLJTempFeeClassSchema.setSchema(ttLJTempFeeClassSet.get(k));

                        tmoney = new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema.getPayMoney()) );
                        if (Double.parseDouble(tmoney) < 0.01)
						{
							dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(),
									"004", "002", "暂收分类金额为非正数");
							continue;
						}

                        for(int t = 1; t <= 2 ; t ++)
                        {
                            if(t == 1)
                            {

                                if (tLJTempFeeClassSchema.getPayMode().equals("1") )
                                {
                                    mBillID = "0200";
                                   mAccountCode = "库存现金-人民币-业务";
                                }
                                else if (tLJTempFeeClassSchema.getPayMode().equals("5"))
                                {
                                    mBillID = "0201";
                                    mAccountCode = "应付业务支出-内部转帐";                //应付业务支出??
                                }
                                else
                                {
                                    if (tLJTempFeeClassSchema.getPayMode().equals("3"))
                                    {
                                        mBillID = "0202";
                                        mAccountCode = "银行存款-活期-人民币";             //银行支票
                                        mAccountSubCode = "支" + mManageCom;
                                    }
                                    else
                                    {
                                        if (tLJTempFeeClassSchema.getBankCode() == null) //银行托收银行代码为空
                                        {
											dealError("LJTempFeeClass", tLJTempFeeClassSchema.getTempFeeNo(),
													tLJTempFeeClassSchema.getTempFeeNo(), "008", "002",
													"银行托收/现金支票的银行编码为空");
                                            tFailFlag="1";
                                            break;
                                        }
                                        mBillID = "0203";
                                        mAccountCode = "银行存款-活期-人民币";
                                        mAccountSubCode = "收" + tLJTempFeeClassSchema.getBankCode();
                                    }
                                }
                              
                                mEnteredDR = String.valueOf(tmoney);
                                mEnteredCR = "";
                                mHeadDescription = "分类收健康保障委托管理资金!";
                            }
                            else
                            {
                                //如果贷方有问题，则借方不再处理
                                if (tFailFlag.equals("1"))
                                    continue;

                                mBillID = "0216";
                                mAccountCode = "其他应付款-健康保障委托管理业务-企事业团体补充医疗（进帐）";
                                mAccountSubCode = "NA";
                                mHeadDescription = "分类收健康保障委托管理资金！";
                                mEnteredDR = "";
                                mEnteredCR = String.valueOf(tmoney);
                            }

                            if (isExitInTab(mBussNo,null,mBillID))
                            {
                                tFailFlag="1";
                                break;
                            }
                            mManageCom = tLJTempFeeSchema.getManageCom().substring(0,6);
                            OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                            if(tOFInterfaceSchema != null)
                                mOFInterfaceSet.add(tOFInterfaceSchema);

                            dealLITranInfo();
                        }
                    }
                }
            }
        }

        //处理集中代收的数据
        String sql = "select distinct(tempfeeno) from LJTempFee a where Confmakedate='"+"?Confmakedate?"+"' and ManageCom "
        		   + "like concat('"+"?a1?"+"','%') and TempFeeType<>'1' and exists( select 1 from ljtempfeeclass b where "
	               + "tempfeeno = a.tempfeeno and bankcode is not null and paymode = '4' "   //当为银行代收时分开处理集中代收和独立代收
	               + "and exists(select 1 from ldcode where codetype='bank' and trim(code)= trim(b.bankcode) "
	               + "and rpad(trim(comcode),6,'0') <> substr(b.managecom,1,6)))  "
	               + "and exists(select 1 from lmriskapp where riskcode=a.riskcode and risktype='H' and healthtype='1') ";
       SQLwithBindVariables sqlbv=new SQLwithBindVariables();
       sqlbv.sql(sql);
       sqlbv.put("Confmakedate", mToday);
       sqlbv.put("a1", cManageCom);

        logger.debug(sql);
        tExeSQL = new ExeSQL();
        SSRS ssrs = tExeSQL.execSQL(sqlbv);
        for (int i = 1 ; i <= ssrs.MaxRow ; i++)
        {
            String tTempfeeno = ssrs.GetText(i,1);
            LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
            LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();

            String tSql1 = "select * from LJTempFee "
                         + "where tempfeeno='"+"?tempfeeno?"+"' order by paymoney desc";
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(tSql1);
            sqlbv1.put("tempfeeno", tTempfeeno.trim());
            String tSql2 = "select * from LJTempFeeClass "
                         + "where tempfeeno='"+"?tempfeeno1?"+"' order by paymoney desc";
            SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
            sqlbv2.sql(tSql2);
            sqlbv2.put("tempfeeno1", tTempfeeno.trim());
            LJTempFeeSet tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv1);
            LJTempFeeClassSet tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv2);
            double tLJTempFeeSum=0;
            double tLJTempFeeClassSum = 0;
            for (int k = 1; k <= tLJTempFeeSet.size(); k++) 
            {
              tLJTempFeeSum = tLJTempFeeSum + tLJTempFeeSet.get(k).getPayMoney();
            }
            for (int k = 1; k <= tLJTempFeeClassSet.size(); k++) 
            {
              tLJTempFeeClassSum = tLJTempFeeClassSum +
                  tLJTempFeeClassSet.get(k).getPayMoney();
            }
            if (Math.abs( (tLJTempFeeSum - tLJTempFeeClassSum)) >= 0.001)
			{
				dealError("LJTempFee", tTempfeeno, tTempfeeno, "006", "002", "暂收费与暂收费分类金额不一致");
				continue;
			}


            for (int j = 1 ; j <= tLJTempFeeSet.size() ; j++)
            {
                LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
                tLJTempFeeSchema = tLJTempFeeSet.get(j);
                if (tLJTempFeeSchema.getPayMoney()==0)
                    continue;

                initVar();
                mMatchID ++;
                mPolNo = tLJTempFeeSchema.getOtherNo();
                mContNo = tLJTempFeeSchema.getOtherNo();
                mBussNo = tLJTempFeeSchema.getTempFeeNo();
                mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
                mTransDate = tLJTempFeeSchema.getConfMakeDate();
                mRiskCode = tLJTempFeeSchema.getRiskCode();

                String tFailFlag="0";
                //取值
                LJTempFeeClassSet ttLJTempFeeClassSet = this.getAccTemp(1,tLJTempFeeSchema.getPayMoney(),tLJTempFeeClassSet);
                for (int k=1;k<=ttLJTempFeeClassSet.size();k++)
                {
                    LJTempFeeClassSchema tLJTempFeeClassSchema=new LJTempFeeClassSchema();
                    tLJTempFeeClassSchema.setSchema(ttLJTempFeeClassSet.get(k));
                    tLJTempFeeClassSchema.setPayMoney(new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema.getPayMoney()) ));
					if(tLJTempFeeClassSchema.getPayMoney() <= 0)
					{
						dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "004",
								"002", "暂收费金额为非正数");
						continue;
					}
					if(tLJTempFeeSchema.getManageCom().trim().length() == 2)
					{
						dealError("LJTempFee", tLJTempFeeSchema.getTempFeeNo(), tLJTempFeeSchema.getOtherNo(), "007",
								"002", "管理机构为2位，不予提取");
						continue;
					}
                    mSegment2 = tLJTempFeeSchema.getManageCom();
                    mManageCom = tLJTempFeeSchema.getManageCom().substring(0,6);    //所有支票都转入商业银行账户
                    mHeadDescription = "分类收健康保障委托管理资金！";
                    tmoney = new DecimalFormat("0.00").format(new Double(tLJTempFeeClassSchema.getPayMoney()) );

                    String tBankCom = getBankCom(tLJTempFeeClassSchema.getBankCode(), "002");
                    for(int t = 1 ; t <= 2 ; t++)
                    {
                        if(t == 1)
                        {
                        //判断是否是分公司代支公司集中扣款
                            if (!tLJTempFeeClassSchema.getPayMode().equals("1") && !tLJTempFeeClassSchema.getPayMode().equals("5"))
                            {
                                if(!tBankCom.equals(mManageCom))
                                {
                                    mBillID = "0209";
                                    mAccountCode = "其他应收款-资金中转-集中批收";       //集中批收的处理
                                    mAccountSubCode = "C" + mManageCom.substring(2,4).concat("0000");    //支公司的科目改变，如86513400－C513400
                                    mEnteredDR = String.valueOf(tmoney);
                                    mEnteredCR = "";
                                    if (isExitInTab(mBussNo,null,mBillID))
                                    {
                                        tFailFlag="1";
                                        break;
                                    }

                                    OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                                    if(tOFInterfaceSchema != null)
                                        mOFInterfaceSet.add(tOFInterfaceSchema);

                                    dealLITranInfo();
                                }

                                mManageCom = tLJTempFeeSchema.getManageCom().substring(0,4).concat("00");
                                mSegment2 = mManageCom.concat("00");            //分公司帐套记录

                                if (tLJTempFeeClassSchema.getPayMode().equals("3"))
                                {
                                    mBillID = "0210";
                                    mAccountCode = "银行存款-活期-人民币";                 //银行支票
                                    mAccountSubCode = "支" + mManageCom ;
                                }
                                else
                                {
                                    if (tLJTempFeeClassSchema.getBankCode()==null)      //银行托收银行代码为空
                                    {
										dealError("LJTempFeeClass", tLJTempFeeClassSchema.getTempFeeNo(),
												tLJTempFeeSchema.getOtherNo(), "009", "002", "银行托收/现金支票的银行编码为空");
                                        tFailFlag="1";
                                        break;
                                    }
                                    mBillID = "0211";
                                    mAccountCode = "银行存款-活期-人民币";                //银行存款
                                    mAccountSubCode = "收" + tLJTempFeeClassSchema.getBankCode();
                                }
                                
                            }
                            mEnteredDR = String.valueOf(tmoney);
                            mEnteredCR = "";
                        }
                        else
                        {
                            //贷方如果有问题，则借方不再处理
                            if (tFailFlag.equals("1"))
                                continue;

                            mBillID = "0212";
                            mAccountCode = "其他应付款-资金中转-集中批收";          //预收
                            mAccountSubCode = "C" + tLJTempFeeSchema.getManageCom().substring(2,6).concat("00");
                            mEnteredDR = "";
                            mEnteredCR = String.valueOf(tmoney);
                            if (isExitInTab(mBussNo,null,mBillID))
                            {
                                tFailFlag="1";
                                break;
                            }

                            OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
                            if(tOFInterfaceSchema != null)
                                mOFInterfaceSet.add(tOFInterfaceSchema);

                            dealLITranInfo();

                            mSegment2 = tLJTempFeeSchema.getManageCom();
                            mManageCom = tLJTempFeeSchema.getManageCom().substring(0,6);    //所有支票都转入商业银行账户
                            if(!tBankCom.equals(mManageCom))
                            {
                                mBillID = "0217";
                                mAccountCode = "其他应付款-健康保障委托管理业务-企事业团体补充医疗（进帐）";                           //预收
                                mAccountSubCode="NA";
                                mHeadDescription = "分类收健康保障委托管理资金！";
                                mEnteredDR = "";
                                mEnteredCR = String.valueOf(tmoney);
                            }
                        }

                        if (isExitInTab(mBussNo,null,mBillID))
                        {
                            tFailFlag="1";
                            break;
                        }

                        OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
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
	 * 保费收入凭证的提取
	 */

	private boolean getFeeDataPrem()
	{
		//实收

		String tSql = "select * from LJAPayGrp where ConfDate='" + "?ConfDate?" + "' "
					+ "and ManageCom like concat('" + "?like?" + "','%') and paytype='TM' ";
       SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
       sqlbv3.sql(tSql);
       sqlbv3.put("ConfDate", mToday);
       sqlbv3.put("like", cManageCom);
		logger.debug(tSql);

		LJAPayGrpDB tLJAPayGrpDB = new LJAPayGrpDB();
		LJAPayGrpSet tLJAPayGrpSet = tLJAPayGrpDB.executeQuery(sqlbv3);
		for (int i = 1; i <= tLJAPayGrpSet.size(); i++)
		{
			LJAPayGrpSchema tLJAPayGrpSchema = new LJAPayGrpSchema();
			tLJAPayGrpSchema = tLJAPayGrpSet.get(i);

			if (tLJAPayGrpSchema.getSumActuPayMoney() > 0)
			{
				initVar();
				mMatchID++;
				mPolNo = tLJAPayGrpSchema.getGrpPolNo();
				mContNo = tLJAPayGrpSchema.getGrpContNo();
				mBussNo = tLJAPayGrpSchema.getPayNo();
				mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
				mTransDate = tLJAPayGrpSchema.getConfDate();
				mSegment2 = tLJAPayGrpSchema.getManageCom();
				mManageCom = tLJAPayGrpSchema.getManageCom().substring(0, 6);
				double feemoney = 0;
				LCGrpPolBL tLCGrpPolBL = (LCGrpPolBL)getGrpPol(mPolNo, "003");
				if(tLCGrpPolBL == null)
					continue;
				LJAPayDB tPayDB = new LJAPayDB();
				tPayDB.setPayNo(tLJAPayGrpSchema.getPayNo());
				if(!tPayDB.getInfo())
				{
					CError.buildErr(this, "查找"+tLJAPayGrpSchema.getPayNo()+"实收总表失败！");
				}
				LJAPaySchema tPaySchema =tPayDB.getSchema(); 
				//续保第一期不收管理费用
				if ("1".equals(tLCGrpPolBL.getAppFlag()) && "1".equals(tLCGrpPolBL.getStandbyFlag4()) 
						&& (tLJAPayGrpSchema.getPayCount() == 1) && (!"10".equals(tPaySchema.getIncomeType())))
					continue;
				//根据新的规则查询管理费用
			/*	"Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
				改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
             */
				String feeSql ="select (case when sum(fee) is not null then sum(fee) else 0 end)" +
						" from lcinsureaccfeetrace where grppolno='"+"?a2?"+"'" +
						" and makedate='"+"?a3?"+"' and feecode like '633%' group by grppolno";
				SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
				sqlbv4.sql(feeSql);
				sqlbv4.put("a2", tLJAPayGrpSchema.getGrpPolNo());
				sqlbv4.put("a3", mToday);
				ExeSQL feeExeSQL = new ExeSQL();
				String fee =feeExeSQL.getOneValue(sqlbv4);
				if("".equals(fee) || fee==null)
					feemoney=0;
				else
				feemoney = Double.parseDouble(fee);
				tmoney = new DecimalFormat("0.00").format(feemoney);
				if(Double.parseDouble(tmoney) == 0)
				{
					dealError("LJAPayGrp", tLJAPayGrpSchema.getPayNo(), tLJAPayGrpSchema.getGrpContNo(),
							"004", mVoucherType+mMatchID, "实收金额为0");
					continue;
				}
				for (int j = 1; j <= 2; j++)
				{
					if (j == 1)
					{

						mBillID = "0326";
						mAccountCode = "其他应付款-健康保障委托管理业务-企事业团体补充医疗（进帐）"; //新保暂转	   
						mAccountSubCode="NA";

						mEnteredDR = String.valueOf(tmoney); //借
						mEnteredCR = ""; //贷
						mHeadDescription = "健康保健委托管理手续费";
					}
					else
					{
						mBillID = "0327";
						mAccountCode = "其他业务收入－健康保障委托管理费收入－企事业团体补充医疗"; //首年续期

						mEnteredDR = ""; //借
						mEnteredCR = String.valueOf(tmoney); //贷
						mHeadDescription = "收" + mInsuredName + "手续费";
					}
					if (isExitInTab(mBussNo, mPolNo, mBillID))
						break;

					OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
					if (tOFInterfaceSchema != null && Double.parseDouble(tmoney) > 0.0)
						mOFInterfaceSet.add(tOFInterfaceSchema);
					dealLITranInfo();
				}
			}
		}
		return true;
	}

	
	
	/**
	 * 理赔应付凭证 待完善的部分：跨机构理赔的财务凭证是否由系统提取
	 */
	private boolean PayFeeDataClaimJ()
	{
		//健康委托产品
		String tSql = "select * from LJAGetClaim where MakeDate='" + "?a5?" + "' and "
					+ "ManageCom like concat('" + "?a6?" + "','%') and feefinatype='CM'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(tSql);
		sqlbv5.put("a5", mToday);
		sqlbv5.put("a6", cManageCom);
		logger.debug(tSql);
		LJAGetClaimDB tLJAGetClaimDB = new LJAGetClaimDB();
		LJAGetClaimSet tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv5);

		for (int i = 0; i < tLJAGetClaimSet.size(); i++)
		{
			LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
			tLJAGetClaimSchema = tLJAGetClaimSet.get(i + 1);

			initVar();
			mMatchID++;
			mPolNo = tLJAGetClaimSchema.getPolNo();
			mContNo = tLJAGetClaimSchema.getContNo();
			mBussNo = tLJAGetClaimSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetClaimSchema.getMakeDate();
			mSegment2 = tLJAGetClaimSchema.getManageCom();
			mManageCom = tLJAGetClaimSchema.getManageCom().substring(0, 6);
			if (tLJAGetClaimSchema.getPay() <= 0)
			{
				dealError("LJAGetClaim", mBussNo, mPolNo, "004", "004", "理赔应付金额为非正数！");
				continue;
			}
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetClaimSchema.getPay()));

			if (tLJAGetClaimSchema.getPay() > 0)
			{
				LCPolBL tLCPolBL = (LCPolBL)getPol(mPolNo, "004");
				if(tLCPolBL == null)
					continue;

				for (int j = 1; j <= 2; j++)
				{
					if (j == 1)
					{
						mBillID = "0414";
						mAccountCode = "其他应付款-健康保障委托管理业务-企事业团体补充医疗（补偿）";
						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";
					} else
					{

						mBillID = "0415";
						mAccountCode = "应付业务支出-健康保障委托管理业务";

						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
					}

					mHeadDescription = mAccountCode + tLCPolBL.getInsuredName().trim() + "赔付费，保单号：" + tLCPolBL.getPolNo();
				

					if (isExitInTab(tLJAGetClaimSchema.getActuGetNo(), tLJAGetClaimSchema.getPolNo(), mBillID))
						break;
	
					OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
					if (tOFInterfaceSchema != null)
						mOFInterfaceSet.add(tOFInterfaceSchema);
	
					dealLITranInfo();
				}
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

		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCodeType("bank");
		tLDCodeDB.setCode(cBankCode);
		if(!tLDCodeDB.getInfo())
		{
			dealError("ldcode", mBussNo, mPolNo, "009", tID, "无法获取银行" + cBankCode + "的机构信息！");
			return "";
		}
		return PubFun.RCh(tLDCodeDB.getComCode(), "01", 6);
	}

	/***********************************************************************************************************************************************************
	 * PayFeeData()分为以下几个部分； PayFeeDataEndordseJ()批改补退费表的业务付费部分； PayFeeDataEndordseD()批改补退费的财务付费部分； PayFeeDataDrawJ()生存领取表的业务付费部分； PayFeeDataDrawD()生存领取的财务付费部分；
	 * PayFeeDataOtherJ()其它退费表的业务付费操作 PayFeeDataOtheD()退费表的财务付费操作 PayFeeDataClaimJ()赔付退费表的业务付费操作 PayFeeDataClaimD()赔付退费表的财务付费操作
	 * PayFeeDataBonusGetJ()红利给付实付表的业务付费操作； PayFeeDataBonusGetD()红利给付实付表的财务付费操作； ***************************************************************************
	 */
	private boolean PayFeeDataD()
	{
		if (!getEndorsePay())
		{
			return false;
		}

		if (!PayFeeDataTempD())
		{
			return false;
		}
		if (!PayFeeDataClaimD())
		{
			return false;
		}

		if (!PayFeeDataOtherGetD())
		{
			return false;
		}

		return true;
	}

	private boolean PayFeeDataJ()
	{
		if (!PayFeeDataTempJ())
		{
			return false;
		}

		if (!PayFeeDataOtherJ())
		{
			return false;
		}

		return true;
	}

	// 对批改补退费表业务付费的操作
	private boolean PayFeeDataEndordseJ()
	{
		String tSql = "select * from LJAGetEndorse where MakeDate='" + "?a7?" + "' "
					+ "and ManageCom like '" + "?a8?" + "%' and FeeFinaType='CM'";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(tSql);
		sqlbv6.put("a7", mToday);
		sqlbv6.put("a8", cManageCom);
		logger.debug(tSql);
		LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
		LJAGetEndorseSet tLJAGetEndorseSet = tLJAGetEndorseDB.executeQuery(sqlbv6);

		for (int i = 0; i < tLJAGetEndorseSet.size(); i++)
		{
			LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
			tLJAGetEndorseSchema = tLJAGetEndorseSet.get(i + 1);
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJAGetEndorseSchema.getActuGetNo());

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

			if (tLJAGetEndorseSchema.getGetMoney() > 0)
			{
				if(!this.setPolInfo(mPolNo, "LJAGetEndorse", "005"))
					continue;

				for (int j = 1; j <= 2; j++)
				{
					tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetEndorseSchema.getGetMoney()));
					if (j == 1)
					{

						mBillID = "0511";
						mAccountCode = "其他应付款-健康保障委托管理业务-企事业团体补充医疗（进帐）"; //保全退保金
						mAccountSubCode="NA";
						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";

						mHeadDescription = mInsuredName + mAccountCode;
					} else
					{

						mBillID = "0512";
						mAccountCode = "应付业务支出-健康保障委托管理业务";

						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
						mHeadDescription = mInsuredName + mAccountCode;
					}

					if (isExitInTab(tLJAGetEndorseSchema.getActuGetNo(), tLJAGetEndorseSchema.getPolNo(), mBillID))
						break;

					OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
					if (tOFInterfaceSchema != null)
						mOFInterfaceSet.add(tOFInterfaceSchema);

					dealLITranInfo();
				}
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
		String tSql = "select * from LJAGet a  where ConfDate='" + "?a9?" + "' and ManageCom like concat('" + "?a10?" + "','%') "
					+ "and OtherNoType='10' and (paymode <> '4' or (paymode = '4' and exists(select 1 from ldbank where "
			    	+ " bankcode = a.bankcode and rpad(trim(comcode),6,'0') = substr(a.managecom,1,6)))) "
			    	+ "and exists(select 1 from ljagetendorse where actugetno=a.actugetno and feefinatype='CM')";
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(tSql);
		sqlbv7.put("a9", mToday);
		sqlbv7.put("a10", cManageCom);
		logger.debug(tSql);
		LJAGetDB tLJAGetDB = new LJAGetDB();
		LJAGetSet tLJAGetSet = new LJAGetSet();
		tLJAGetSet = tLJAGetDB.executeQuery(sqlbv7);
		for (int i = 1; i <= tLJAGetSet.size(); i++)
		{
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			tLJAGetSchema.setSchema(tLJAGetSet.get(i));
			double tGetMoney = tLJAGetSchema.getSumGetMoney();
			
			this.initVar();
			mMatchID++;
			mBussNo = tLJAGetSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetSchema.getConfDate();
			mSegment2 = tLJAGetSchema.getManageCom();
			mManageCom = tLJAGetSchema.getManageCom().substring(0, 6);
			
			tSql = "select * from LJAGetEndorse where ActuGetNo='" + "?a11?" + "' and GetMoney<>0 and feefinatype='CM' ";
			SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
			sqlbv8.sql(tSql);
			sqlbv8.put("a11", tLJAGetSchema.getActuGetNo());
			logger.debug(tSql);
			LJAGetEndorseSet ttLJAGetEndorseSet = new LJAGetEndorseSet();
			LJAGetEndorseDB ttLJAGetEndorseDB = new LJAGetEndorseDB();
			ttLJAGetEndorseSet.set(ttLJAGetEndorseDB.executeQuery(sqlbv8));
			LJAGetEndorseSet tttLJAGetEndorseSet = this.getAccEndorse(0, tGetMoney, ttLJAGetEndorseSet);
			for (int k = 1; k <= tttLJAGetEndorseSet.size(); k++)
			{
				LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
				tLJAGetEndorseSchema.setSchema(tttLJAGetEndorseSet.get(k));
				if(tLJAGetEndorseSchema.getGetMoney() == 0)
				{
					dealError("LJAGetEndorse", mBussNo, mPolNo, "004", "008", "保全实付金额为0");
					continue;
				}
				mPolNo = tLJAGetEndorseSchema.getPolNo();
				mContNo = tLJAGetEndorseSchema.getContNo();
				tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetEndorseSchema.getGetMoney()) );
				for (int j = 1; j <= 2; j++)
				{
					if (j == 1)
					{
						mAccountCode = "应付业务支出-健康保障委托管理业务";
						mBillID = "0807";
						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";
					}
					else
					{
						if (tLJAGetSchema.getPayMode() != null)
						{
							if (!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
							{
								if (tLJAGetSchema.getPayMode().equals("5"))
								{
									mBillID = "0803";
									mAccountCode = "应付业务支出-内部转帐"; //内部转账贷
								} else
								{
									mBillID = "0804";
									mAccountCode = "银行存款-活期-人民币"; //银行存款支
									mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
								}
							} else
							{
								mBillID = "0805";
								mAccountCode = "库存现金-人民币-业务";
							}
						} 
						else
						{
							mBillID = "0806";
							mAccountCode = "库存现金-人民币-业务";
						}
			
						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
					}
					mHeadDescription = mAccountCode;
					if (isExitInTab(tLJAGetEndorseSchema.getActuGetNo(), mPolNo, mBillID))
						break;
			
					OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
					if (tOFInterfaceSchema != null)
						mOFInterfaceSet.add(tOFInterfaceSchema);
			
					dealLITranInfo();
				}
		
			}
		}
		
		String sql = "select * from LJAGet a  where ConfDate='" + "?a13?" + "' and ManageCom like concat('" + "?a14?" + "','%') "
			   	   + "and OtherNoType='10' and paymode = '4' and bankcode is not null and exists(select 1 from  "
			   	   + "ldbank where bankcode = a.bankcode and rpad(trim(comcode),6,'0') <> substr(a.managecom,1,6)) "
			   	   + "and exists(select 1 from ljagetendorse where actugetno=a.actugetno and feefinatype='CM')";
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(sql);
		sqlbv9.put("a13", mToday);
		sqlbv9.put("a14", cManageCom);
		logger.debug(sql);
		tLJAGetDB = new LJAGetDB();
		tLJAGetSet = new LJAGetSet();
		tLJAGetSet = tLJAGetDB.executeQuery(sqlbv9);
		for (int i = 1; i <= tLJAGetSet.size(); i++)
		{
			LJAGetSchema tLJAGetSchema = new LJAGetSchema();
			tLJAGetSchema.setSchema(tLJAGetSet.get(i));
			double tGetMoney = tLJAGetSchema.getSumGetMoney();
			
			this.initVar();
			mMatchID++;
			mBussNo = tLJAGetSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetSchema.getConfDate();
			
			sql = "select * from LJAGetEndorse where ActuGetNo='" + "?a21?" + "' and GetMoney<>0 and feefinatype='CM' ";
			SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
			sqlbv10.sql(sql);
			sqlbv10.put("a21", tLJAGetSchema.getActuGetNo());
            logger.debug(sql);
			LJAGetEndorseSet ttLJAGetEndorseSet = new LJAGetEndorseSet();
			LJAGetEndorseDB ttLJAGetEndorseDB = new LJAGetEndorseDB();
			ttLJAGetEndorseSet.set(ttLJAGetEndorseDB.executeQuery(sqlbv10));
			LJAGetEndorseSet tttLJAGetEndorseSet = this.getAccEndorse(0, tGetMoney, ttLJAGetEndorseSet);
			for (int k = 1; k <= tttLJAGetEndorseSet.size(); k++)
			{
				LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
				tLJAGetEndorseSchema.setSchema(tttLJAGetEndorseSet.get(k));
				if(tLJAGetEndorseSchema.getGetMoney() == 0)
				{
					dealError("LJAGetEndorse", mBussNo, mPolNo, "004", "008", "保全实付金额为0");
					continue;
				}
			
				mSegment2 = tLJAGetSchema.getManageCom();
				mManageCom = tLJAGetSchema.getManageCom().substring(0, 6);
				mPolNo = tLJAGetEndorseSchema.getPolNo();
				mContNo = tLJAGetEndorseSchema.getContNo();
				if(!this.setPolInfo(mPolNo, "LJAGetEndorse", "008"))
					continue;
			
				mAccountCode = "应付业务支出-健康保障委托管理业务";
				mBillID = "0899";
			
				mAccountSubCode = "NA";
				mHeadDescription = mInsuredName + mAccountCode;
				tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetEndorseSchema.getGetMoney()));
				mEnteredDR = String.valueOf(tmoney);
				mEnteredCR = "";
			
				if (isExitInTab(tLJAGetEndorseSchema.getActuGetNo(), tLJAGetEndorseSchema.getPolNo(), mBillID))
					continue;
			
				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
				if (tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);
			
				dealLITranInfo();
			
				if (tLJAGetSchema.getPayMode() != null)
				{
					if (!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
					{
						if (tLJAGetSchema.getPayMode().equals("5")) //内部转账
						{
							mBillID = "0863";
							mAccountCode = "应付业务支出-内部转帐"; //内部转账贷
						} 
						else
						{
							mBillID = "0864";
							mAccountCode = "其他应付款-资金中转-集中批付"; //银行存款支
							mAccountSubCode = "C" + mManageCom.substring(2, 4).concat("0000");
						}
					}
				}
				mEnteredDR = "";
				mEnteredCR = String.valueOf(tmoney);
				mHeadDescription = mAccountCode + mInsuredName;
				if (isExitInTab(tLJAGetEndorseSchema.getActuGetNo(), tLJAGetEndorseSchema.getPolNo(), mBillID))
					continue;
			
				OFInterfaceSchema ttOFInterfaceSchema = new OFInterfaceSchema();
				ttOFInterfaceSchema = (OFInterfaceSchema) entry();
				if (ttOFInterfaceSchema != null)
					mOFInterfaceSet.add(ttOFInterfaceSchema);
			
				dealLITranInfo();
			
				//分公司借记科目
				//取得机构对应的分公司
				mManageCom = tLJAGetEndorseSchema.getManageCom().substring(0, 4).concat("00");
				mSegment2 = mManageCom.concat("00");
				mBillID = "0865";
				mAccountCode = "其他应收款-资金中转-集中批付"; //银行存款支
				mAccountSubCode = "C" + tLJAGetEndorseSchema.getManageCom().substring(2, 6).concat("00");
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = String.valueOf(tmoney);
				mEnteredCR = "";
				if (isExitInTab(tLJAGetEndorseSchema.getActuGetNo(), tLJAGetEndorseSchema.getPolNo(), mBillID))
					continue;
			
				OFInterfaceSchema t1OFInterfaceSchema = new OFInterfaceSchema();
				t1OFInterfaceSchema = (OFInterfaceSchema) entry();
				if (t1OFInterfaceSchema != null)
					mOFInterfaceSet.add(t1OFInterfaceSchema);
			
				dealLITranInfo();
			
				//分公司贷记科目
				mBillID = "0866";
				mAccountCode = "银行存款-活期-人民币"; //银行存款支
				mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = "";
				mEnteredCR = String.valueOf(tmoney);
				if (isExitInTab(tLJAGetEndorseSchema.getActuGetNo(), tLJAGetEndorseSchema.getPolNo(), mBillID))
					continue;
			
				OFInterfaceSchema t2OFInterfaceSchema = new OFInterfaceSchema();
				t2OFInterfaceSchema = (OFInterfaceSchema) entry();
				if (t2OFInterfaceSchema != null)
					mOFInterfaceSet.add(t2OFInterfaceSchema);
			
				dealLITranInfo();
			}
		}
		return true;
	}




	// 临时退费表的借操作
	private boolean PayFeeDataTempJ()
	{
		String tSql = "select * from LJAGetTempFee a where MakeDate='" + "?d1?" + "' and ManageCom like concat('" + "?d2?" + "','%') "
	    			+ "and exists (select 1 from lmriskapp where riskcode=a.riskcode and risktype='H' and healthtype='1')";
		SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
		sqlbv11.sql(tSql);
		sqlbv11.put("d1", mToday);
		sqlbv11.put("d2", cManageCom);
		logger.debug(tSql);
		LJAGetTempFeeDB tLJAGetTempFeeDB = new LJAGetTempFeeDB();
		LJAGetTempFeeSet tLJAGetTempFeeSet = tLJAGetTempFeeDB.executeQuery(sqlbv11);
		
		for (int i = 0; i < tLJAGetTempFeeSet.size(); i++)
		{
		LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
		tLJAGetTempFeeSchema = tLJAGetTempFeeSet.get(i + 1);
		if (tLJAGetTempFeeSchema.getGetMoney() <= 0)
			continue;
		
		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		tLJTempFeeDB.setTempFeeNo(tLJAGetTempFeeSchema.getTempFeeNo());
		tLJTempFeeDB.setTempFeeType(tLJAGetTempFeeSchema.getTempFeeType());
		tLJTempFeeDB.setRiskCode(tLJAGetTempFeeSchema.getRiskCode());
		if (!tLJTempFeeDB.getInfo())
		{
			dealError("LJAGetTempFee", mBussNo, mPolNo, "002", "070", "暂收退费对应的收据号为"
					+ tLJAGetTempFeeSchema.getTempFeeNo() + "的暂收信息不存在！");
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
		
		if (tLJAGetTempFeeSchema.getGetMoney() > 0)
		{
			for (int j = 1; j <= 2; j++)
			{
				if (j == 1)
				{
					mBillID = "0703";
					mAccountCode = "其他应付款-健康保障委托管理业务-企事业团体补充医疗（进帐）"; //退续期保费
					mAccountSubCode="NA";
					mEnteredDR = String.valueOf(tmoney);
					mEnteredCR = "";
				} else
				{
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
					mBillID = "0704";
					mAccountCode = "应付业务支出-健康保障委托管理业务";
				}
		
				if (tLJAGetTempFeeSchema.getAPPntName() != null)
					mHeadDescription = mAccountCode + mInsuredName;
				else
					mHeadDescription = mAccountCode;
		
				if (isExitInTab(mBussNo, mPolNo, mBillID))
					break;
		
				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
				if (tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);
		
				dealLITranInfo();
			}
		}
		}
		return true;
}

	// 临时退费表的代操作
	private boolean PayFeeDataTempD()
	{
		String tSql = "select * from LJAGetTempFee a  where ConfDate='"+ "?d3?" + "' and ManageCom like concat('"+ "?d4?" + "','%') "
		    		+ " and exists(select 1 from lmriskapp where riskcode=a.riskcode and risktype='H' and healthtype='1') "
		    		+ "and exists(select 1 from ljaget b where actugetno = a.actugetno and (paymode <> '4' or (paymode = '4' and exists("
		    		+ "select 1 from ldbank where bankcode = b.bankcode and rpad(trim(comcode),6,'0') = substr(b.managecom,1,6)))))";
		SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
		sqlbv12.sql(tSql);
		sqlbv12.put("d3", mToday);
		sqlbv12.put("d4", cManageCom);
		logger.debug(tSql);
		LJAGetTempFeeDB tLJAGetTempFeeDB = new LJAGetTempFeeDB();
		LJAGetTempFeeSet tLJAGetTempFeeSet = tLJAGetTempFeeDB.executeQuery(sqlbv12);
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		
		for (int i = 0; i < tLJAGetTempFeeSet.size(); i++)
		{
			LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
			tLJAGetTempFeeSchema = tLJAGetTempFeeSet.get(i + 1);
			if(tLJAGetTempFeeSchema.getGetMoney() <= 0)
			{
				dealError("LJAGetTempFee", tLJAGetTempFeeSchema.getActuGetNo(), tLJAGetTempFeeSchema.getTempFeeNo(), "004", "008",
						"暂收退费金额为非正数");
				continue;
			}
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJAGetTempFeeSchema.getActuGetNo());
			if(!tLJAGetDB.getInfo())
			{
				dealError("LJAGetTempFee", tLJAGetTempFeeSchema.getActuGetNo(), tLJAGetTempFeeSchema.getTempFeeNo(), "002",
						"008", "LJAGet不存在LJAGetTempFee相应的记录");
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
						"008", "LJTempFee不存在LJAGetTempFee对应的记录");
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
			mSaleChnl = tLJAGetSchema.getSaleChnl();
			mRiskCode = tLJAGetTempFeeSchema.getRiskCode();
		
			if (tLJAGetTempFeeSchema.getGetMoney() > 0)
			{
				for (int j = 1; j <= 2; j++)
				{
					if (j == 1)
					{
						mAccountCode = "应付业务支出-健康保障委托管理业务";
						mBillID = "0825";
						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";
					} 
					else
					{
						if (tLJAGetSchema.getPayMode() != null)
						{
							if (!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
							{
								if (tLJAGetSchema.getPayMode().equals("5"))
								{
									mBillID = "0821";
									mAccountCode = "应付业务支出-内部转帐"; //内部转账贷
								} 
								else
								{
									mBillID = "0822";
									mAccountCode = "银行存款-活期-人民币"; //银行存款支
									mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
								}
							} 
							else
							{
								mBillID = "0823";
								mAccountCode = "库存现金-人民币-业务";
							}
						} else
						{
							mBillID = "0824";
							mAccountCode = "库存现金-人民币-业务";
						}
		
						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
					}
					mHeadDescription = mAccountCode;
					if (isExitInTab(tLJAGetTempFeeSchema.getActuGetNo(), mPolNo, mBillID))
						break;
		
					OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
					if (tOFInterfaceSchema != null)
						mOFInterfaceSet.add(tOFInterfaceSchema);
		
					dealLITranInfo();
				}
			}
		}
		
		String sql = "select * from LJAGetTempFee a where ConfDate='"+ "?d5?"+ "' and ManageCom like concat('"+ "?d6?"+ "','%') "
				   + "and exists (select 1 from lmriskapp where riskcode=a.riskcode and risktype='H' and healthtype='1') "
				   + "and exists(select 1 from ljaget b where actugetno = a.actugetno and paymode = '4' "
				   + "and (bankcode is not null and exists(select 1 from ldbank where bankcode = b.bankcode "
				   + "and rpad(trim(comcode),6,'0') <> substr(b.managecom,1,6))))";
		SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
		sqlbv13.sql(sql);
		sqlbv13.put("d5", mToday);
		sqlbv13.put("d6", cManageCom);
		logger.debug(sql);
		tLJAGetTempFeeDB = new LJAGetTempFeeDB();
		tLJAGetTempFeeSet = tLJAGetTempFeeDB.executeQuery(sqlbv13);
		tLJAGetSchema = new LJAGetSchema();
		for (int i = 0; i < tLJAGetTempFeeSet.size(); i++)
		{
			LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
			tLJAGetTempFeeSchema = tLJAGetTempFeeSet.get(i + 1);
			if(tLJAGetTempFeeSchema.getGetMoney() <= 0)
			{
				dealError("LJAGetTempFee", tLJAGetTempFeeSchema.getActuGetNo(), tLJAGetTempFeeSchema.getTempFeeNo(), "004", "008",
						"暂收退费金额为非正数");
				continue;
			}
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJAGetTempFeeSchema.getActuGetNo());
			if(!tLJAGetDB.getInfo())
			{
				dealError("LJAGetTempFee", tLJAGetTempFeeSchema.getActuGetNo(), tLJAGetTempFeeSchema.getTempFeeNo(), "002",
						"008", "LJAGet不存在LJAGetTempFee相应的记录");
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
						"008", "LJTempFee不存在LJAGetTempFee对应的记录");
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
		
			mSaleChnl = tLJAGetSchema.getSaleChnl();
			mRiskCode = tLJAGetTempFeeSchema.getRiskCode();
		
			if (tLJAGetTempFeeSchema.getGetMoney() > 0)
			{
				for (int j = 1; j <= 2; j++)
				{
					if (j == 1)
					{
						mAccountCode = "应付业务支出-健康保障委托管理业务";
						mBillID = "0879";
						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";
					} 
					else
					{
						if (tLJAGetSchema.getPayMode() != null)
						{
							if (!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
							{
								if (tLJAGetSchema.getPayMode().equals("5"))
								{
									mBillID = "0875";
									mAccountCode = "应付业务支出-内部转帐"; //内部转账贷
								} 
								else
								{
									mBillID = "0876";
									mAccountCode = "其他应付款-资金中转-集中批付"; //银行存款支
									mAccountSubCode = "C" + mManageCom.substring(2, 4).concat("0000");
								}
							}
						}
		
						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
					}
					mHeadDescription = mAccountCode;
					if (isExitInTab(tLJAGetTempFeeSchema.getActuGetNo(), mPolNo, mBillID))
						break;
		
					OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
					if (tOFInterfaceSchema != null)
						mOFInterfaceSet.add(tOFInterfaceSchema);
		
					dealLITranInfo();
				}
		
				//分公司借记科目
				//取得机构对应的分公司
				mManageCom = tLJAGetTempFeeSchema.getManageCom().substring(0, 4).concat("00");
				mSegment2 = mManageCom.concat("00");
				mBillID = "0877";
				mAccountCode = "其他应收款-资金中转-集中批付"; //银行存款支
				mAccountSubCode = "C" + tLJAGetTempFeeSchema.getManageCom().substring(2, 6).concat("00");
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = String.valueOf(tmoney);
				mEnteredCR = "";
				if (isExitInTab(mBussNo, mPolNo, mBillID))
					continue;
		
				OFInterfaceSchema t1OFInterfaceSchema = (OFInterfaceSchema) entry();
				if (t1OFInterfaceSchema != null)
					mOFInterfaceSet.add(t1OFInterfaceSchema);
		
				dealLITranInfo();
		
				//分公司贷记科目
				mBillID = "0878";
				mAccountCode = "银行存款-活期-人民币"; //银行存款支
				mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = "";
				mEnteredCR = String.valueOf(tmoney);
				if (isExitInTab(mBussNo, mPolNo, mBillID))
					continue;
		
				OFInterfaceSchema t2OFInterfaceSchema = new OFInterfaceSchema();
				t2OFInterfaceSchema = (OFInterfaceSchema) entry();
				if (t2OFInterfaceSchema != null)
					mOFInterfaceSet.add(t2OFInterfaceSchema);
		
				dealLITranInfo();
			}
		}
		return true;
		}

	// 其它退费表的代操作
	private boolean PayFeeDataClaimD()
	{
		String tSql = "select * from LJAGetClaim a where ConfDate='"+ "?w1?"+ "' and ManageCom like concat('"+ "?w2?"+ "','%')  "
	    			+ "and feefinatype='CM' and exists(select 1 from ljaget b where actugetno = a.actugetno  "
	    			+ "and (paymode <> '4' or (paymode = '4' and exists(select 1 from ldbank where "
	    			+ "bankcode = b.bankcode and rpad(trim(comcode),6,'0') = substr(b.managecom,1,6)))))";
		SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
		sqlbv14.sql(tSql);
		sqlbv14.put("w1", mToday);
		sqlbv14.put("w2", cManageCom);
		logger.debug(tSql);
		LJAGetClaimDB tLJAGetClaimDB = new LJAGetClaimDB();
		LJAGetClaimSet tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv14);
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		
		for (int i = 0; i < tLJAGetClaimSet.size(); i++)
		{
			LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
			tLJAGetClaimSchema = tLJAGetClaimSet.get(i + 1);
			if(tLJAGetClaimSchema.getPay() <= 0)
			{
				dealError("LJAGetClaim", tLJAGetClaimSchema.getActuGetNo(), tLJAGetClaimSchema.getPolNo(), "004",
						"008", "理赔金额为非正数");
				continue;
			}
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJAGetClaimSchema.getActuGetNo());
			if(!tLJAGetDB.getInfo())
			{
				dealError("LJAGetClaim", mBussNo, mPolNo, "002", "008", "LJAGet不存在对应的记录");
				continue;
			}
			tLJAGetSchema.setSchema(tLJAGetDB.getSchema());
			initVar();
			mMatchID++;
			mPolNo = tLJAGetClaimSchema.getPolNo();
			mContNo = tLJAGetClaimSchema.getContNo();
			mBussNo = tLJAGetClaimSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetClaimSchema.getConfDate();
			mSegment2 = tLJAGetClaimSchema.getManageCom();
			mManageCom = tLJAGetClaimSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetClaimSchema.getPay()));
	
			
			if (tLJAGetClaimSchema.getPay() > 0)
			{
				if(!setPolInfo(mPolNo, "LJAGetClaim", "008"))
					continue;
			
				for (int j = 1; j <= 2; j++)
				{
					if (j == 1)
					{
						mAccountCode = "应付业务支出-健康保障委托管理业务";
						mBillID = "0835";
			
						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";
					} 
					else
					{
						if (tLJAGetSchema.getPayMode() != null)
						{
							if (!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
							{
								if (tLJAGetSchema.getPayMode().equals("5"))
								{
									mBillID = "0831";
									mAccountCode = "应付业务支出-内部转帐"; //内部转账贷
								} else
								{
									mBillID = "0832";
									mAccountCode = "银行存款-活期-人民币"; //银行存款支
									mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
								}
							} else
							{
								mBillID = "0833";
								mAccountCode = "库存现金-人民币-业务";
							}
						} else
						{
							mBillID = "0834";
							mAccountCode = "库存现金-人民币-业务";
						}
			
						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
					}
			
					mHeadDescription = mAccountCode + mInsuredName.trim() + "赔付退费";
			
					if (isExitInTab(tLJAGetClaimSchema.getActuGetNo(), tLJAGetClaimSchema.getPolNo(), mBillID))
						break;
			
					OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
					if (tOFInterfaceSchema != null)
						mOFInterfaceSet.add(tOFInterfaceSchema);
			
					dealLITranInfo();
				}
			}
		}
		
		String sql = "select * from LJAGetClaim a where ConfDate='"+ "?w3?"+ "' and ManageCom like concat('"+ "?w4?"+ "','%')  "
			   	   + "and feefinatype='CM' and exists(select 1 from ljaget b where actugetno = a.actugetno "
			   	   + "and paymode = '4' and bankcode is not null and exists(select 1 from ldbank where bankcode = b.bankcode "
			   	   + "and rpad(trim(comcode),6,'0') <> substr(b.managecom,1,6)))";
		SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
		sqlbv15.sql(sql);
		sqlbv15.put("w3", mToday);
		sqlbv15.put("w4", cManageCom);
		logger.debug(sql);
		tLJAGetClaimDB = new LJAGetClaimDB();
		tLJAGetClaimSet = tLJAGetClaimDB.executeQuery(sqlbv15);
		tLJAGetSchema = new LJAGetSchema();
		for (int i = 0; i < tLJAGetClaimSet.size(); i++)
		{
			LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
			tLJAGetClaimSchema = tLJAGetClaimSet.get(i + 1);
			if(tLJAGetClaimSchema.getPay() <= 0)
			{
				dealError("LJAGetClaim", tLJAGetClaimSchema.getActuGetNo(), tLJAGetClaimSchema.getPolNo(), "004",
						"008", "理赔给付金额为非正数");
				continue;
			}
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJAGetClaimSchema.getActuGetNo());
			if(!tLJAGetDB.getInfo())
			{
				dealError("LJAGetClaim", tLJAGetClaimSchema.getActuGetNo(), tLJAGetClaimSchema.getPolNo(), "002",
						"008", "LJAGet不存在对应的记录");
				continue;
			}
			tLJAGetSchema.setSchema(tLJAGetDB.getSchema());
			initVar();
			mMatchID++;
			mPolNo = tLJAGetClaimSchema.getPolNo();
			mContNo = tLJAGetClaimSchema.getContNo();
			mBussNo = tLJAGetClaimSchema.getActuGetNo();
			mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
			mTransDate = tLJAGetClaimSchema.getConfDate();
			mSegment2 = tLJAGetClaimSchema.getManageCom();
			mManageCom = tLJAGetClaimSchema.getManageCom().substring(0, 6);
			tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetClaimSchema.getPay()));

			
			if (tLJAGetClaimSchema.getPay() > 0)
			{
				if(!setPolInfo(mPolNo, "LJAGetClaim", "008"))
					continue;
			
				for (int j = 1; j <= 2; j++)
				{
					if (j == 1)
					{
			
						mAccountCode = "应付业务支出-健康保障委托管理业务";
						mBillID = "0880";
			
						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";
					} 
					else
					{
						if (tLJAGetSchema.getPayMode() != null)
						{
							if (!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
							{
								if (tLJAGetSchema.getPayMode().equals("5"))
								{
									mBillID = "0881";
									mAccountCode = "应付业务支出-内部转帐"; //内部转账贷
								} 
								else
								{
									mBillID = "0882";
									mAccountCode = "其他应付款-资金中转-集中批付"; //银行存款支
									mAccountSubCode = "C" + mManageCom.substring(2, 4).concat("0000");
								}
							}
						}
						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
					}
					mHeadDescription = mAccountCode + mInsuredName.trim() + "赔付退费";
			
					if (isExitInTab(tLJAGetClaimSchema.getActuGetNo(), tLJAGetClaimSchema.getPolNo(), mBillID))
						break;
			
					OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
					if (tOFInterfaceSchema != null)
						mOFInterfaceSet.add(tOFInterfaceSchema);
			
					dealLITranInfo();
				}
			
				//分公司借记科目
				//取得机构对应的分公司
				mManageCom = tLJAGetClaimSchema.getManageCom().substring(0, 4).concat("00");
				mSegment2 = mManageCom.concat("00");
				mBillID = "0883";
				mAccountCode = "其他应收款-资金中转-集中批付"; //银行存款支
				mAccountSubCode = "C" + tLJAGetClaimSchema.getManageCom().substring(2, 6).concat("00");
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = String.valueOf(tmoney);
				mEnteredCR = "";
				if (isExitInTab(mBussNo, mPolNo, mBillID))
					continue;
			
				OFInterfaceSchema t1OFInterfaceSchema = (OFInterfaceSchema) entry();
				if (t1OFInterfaceSchema != null)
					mOFInterfaceSet.add(t1OFInterfaceSchema);
			
				dealLITranInfo();
			
				//分公司贷记科目
				mBillID = "0884";
				mAccountCode = "银行存款-活期-人民币"; //银行存款支
				mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = "";
				mEnteredCR = String.valueOf(tmoney);
				if (isExitInTab(mBussNo, mPolNo, mBillID))
					continue;
			
				OFInterfaceSchema t2OFInterfaceSchema = new OFInterfaceSchema();
				t2OFInterfaceSchema = (OFInterfaceSchema) entry();
				if (t2OFInterfaceSchema != null)
					mOFInterfaceSet.add(t2OFInterfaceSchema);
			
				dealLITranInfo();
			}
		}
		return true;
}


	// 对其他给付实付表的业务操作；
	private boolean PayFeeDataOtherJ()
	{
		String tSql = "select * from LJAGetOther a where MakeDate='" + "?w5?" + "' and ManageCom like concat('" + "?w6?" + "','%') "
	    			+ "and  exists(select 1 from lcgrppol b where grppolno=a.otherno and exists(select 1 from lmriskapp "
	    			+ "where riskcode=b.riskcode and risktype='H' and healthtype='1' ))";
		SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
		sqlbv16.sql(tSql);
		sqlbv16.put("w5", mToday);
		sqlbv16.put("w6", cManageCom);
		logger.debug(tSql);
		LJAGetOtherDB tLJAGetOtherDB = new LJAGetOtherDB();
		LJAGetOtherSet tLJAGetOtherSet = tLJAGetOtherDB.executeQuery(sqlbv16);
		
		for (int i = 0; i < tLJAGetOtherSet.size(); i++)
		{
		LJAGetOtherSchema tLJAGetOtherSchema = new LJAGetOtherSchema();
		tLJAGetOtherSchema = tLJAGetOtherSet.get(i + 1);
		
		initVar();
		mMatchID++;
		mPolNo = tLJAGetOtherSchema.getOtherNo();
		mContNo = tLJAGetOtherSchema.getOtherNo();
		mBussNo = tLJAGetOtherSchema.getActuGetNo();
		mAccountingDate = (mInputDate.equals("")) ? mToday : mInputDate;
		mTransDate = tLJAGetOtherSchema.getMakeDate();
		mSegment2 = tLJAGetOtherSchema.getManageCom();
		mManageCom = tLJAGetOtherSchema.getManageCom().substring(0, 6);
		if(!this.setPolInfo("mPolNo", "LJAGetOther", "007"))
			continue;
		
		if (tLJAGetOtherSchema.getGetMoney() <= 0)
		{
			dealError("LJAGetOther", tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), "004",
					"007", "溢交应付金额为非正数！");
			continue;
		}
		tmoney = new DecimalFormat("0.00").format(new Double(tLJAGetOtherSchema.getGetMoney()));
		
		if (tLJAGetOtherSchema.getGetMoney() > 0)
		{
			for (int j = 1; j <= 2; j++)
			{
				if (j == 1)
				{
					mBillID = "0722";
					mAccountCode = "其他应付款-健康保障委托管理业务-企事业团体补充医疗（进帐）"; //其它支出
					mAccountSubCode="NA";
					mEnteredDR = String.valueOf(tmoney);
					mEnteredCR = "";
					mHeadDescription = mInsuredName + "的其它给付";
				} else
				{
					mBillID = "0723";
					mAccountCode = "应付业务支出-健康保障委托管理业务";
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
					mHeadDescription = mInsuredName + "的业务其它给付";
				}
		
				if (isExitInTab(tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), mBillID))
					break;
		
				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
				if (tOFInterfaceSchema != null)
					mOFInterfaceSet.add(tOFInterfaceSchema);
		
				dealLITranInfo();
			}
		}
		}
		
		return true;
}



	// 对其它给付实付表的财务付费操作；
	private boolean PayFeeDataOtherGetD()
	{
		String tSql = "select * from LJAGetOther a where ConfDate='"+ "?w7?"+ "' and ManageCom like concat('"+ "?w8?"+ "','%') "
					+ "and exists (select 1 from lcgrppol where grppolno=a.otherno and exists(select 1 from lmriskapp where "
					+ "riskcode=lcgrppol.riskcode and risktype='H' and healthtype='1')) and exists(select 1 from ljaget b where "
					+ "actugetno = a.actugetno and (paymode <> '4' or (paymode = '4' and exists(select 1 from ldbank "
					+ "where bankcode = b.bankcode and rpad(trim(comcode),6,'0') = substr(b.managecom,1,6)))))";
		SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
		sqlbv17.sql(tSql);
		sqlbv17.put("w7", mToday);
		sqlbv17.put("w8", cManageCom);
		logger.debug(tSql);
		LJAGetOtherDB tLJAGetOtherDB = new LJAGetOtherDB();
		LJAGetOtherSet tLJAGetOtherSet = tLJAGetOtherDB.executeQuery(sqlbv17);
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();
		
		for (int i = 0; i < tLJAGetOtherSet.size(); i++)
		{
			LJAGetOtherSchema tLJAGetOtherSchema = new LJAGetOtherSchema();
			tLJAGetOtherSchema = tLJAGetOtherSet.get(i + 1);
			if(tLJAGetOtherSchema.getGetMoney() <= 0)
			{
				dealError("LJAGetOther", tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), "004",
						"008", "溢交退费金额为非正数");
				continue;
			}
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJAGetOtherSchema.getActuGetNo());
			if(!tLJAGetDB.getInfo())
			{
				dealError("LJAOtherGet", tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), "002",
						"008", "LJAGet不存在对应的记录");
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

			if (tLJAGetOtherSchema.getGetMoney() > 0)
			{
				if(!this.setPolInfo(mPolNo, "LJAGetOther", "008"))
					continue;
		
				for (int j = 1; j <= 2; j++)
				{
					if (j == 1)
					{
						mBillID = "0855";
						mAccountCode = "应付业务支出-健康保障委托管理业务";
						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";
					} 
					else
					{
						if (tLJAGetSchema.getPayMode() != null)
						{
							if (!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
							{
								if (tLJAGetSchema.getPayMode().equals("5"))
								{
									mBillID = "0851";
									mAccountCode = "应付业务支出-内部转帐"; //内部转账贷
								} 
								else
								{
									mBillID = "0852";
									mAccountCode = "银行存款-活期-人民币"; //银行存款支
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
					if (isExitInTab(tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), mBillID))
						break;
		
					OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
					if (tOFInterfaceSchema != null)
						mOFInterfaceSet.add(tOFInterfaceSchema);
		
					dealLITranInfo();
				}
			}
		}
		
		String sql = "select * from LJAGetOther a where ConfDate='"+ "?w10?"+ "' and ManageCom like concat('"+ "?w11?"+ "','%') "
				   + "and exists (select 1 from lcgrppol where grppolno=a.otherno and exists ( "
				   + "select 1 from lmriskapp where riskcode=lcgrppol.riskcode and risktype='H' and healthtype='1' ))"
				   + "and exists(select 1 from ljaget b where actugetno = a.actugetno and paymode = '4' "
				   + "and bankcode is not null and exists(select 1 from ldbank where bankcode = b.bankcode "
				   + "and rpad(trim(comcode),6,'0') <> substr(b.managecom,1,6)))";
		SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
		sqlbv18.sql(sql);
		sqlbv18.put("w10", mToday);
		sqlbv18.put("w11", cManageCom);
		logger.debug(sql);
		tLJAGetOtherDB = new LJAGetOtherDB();
		tLJAGetOtherSet = tLJAGetOtherDB.executeQuery(sqlbv18);
		tLJAGetSchema = new LJAGetSchema();
		
		for (int i = 0; i < tLJAGetOtherSet.size(); i++)
		{
			LJAGetOtherSchema tLJAGetOtherSchema = new LJAGetOtherSchema();
			tLJAGetOtherSchema = tLJAGetOtherSet.get(i + 1);
			if(tLJAGetOtherSchema.getGetMoney() <= 0)
			{
				dealError("LJAGetOther", tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), "004",
						"008", "溢交退费金额为非正数");
				continue;
			}
			LJAGetDB tLJAGetDB = new LJAGetDB();
			tLJAGetDB.setActuGetNo(tLJAGetOtherSchema.getActuGetNo());
			if(!tLJAGetDB.getInfo())
			{
				dealError("LJAOtherGet", tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), "002",
						"008", "LJAGet不存在对应的记录");
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

			if (tLJAGetOtherSchema.getGetMoney() > 0)
			{
				if(!this.setPolInfo(mPolNo, "LJAGetOther", "008"))
					continue;
		
				for (int j = 1; j <= 2; j++)
				{
					if (j == 1)
					{
						mBillID = "08901";
						mAccountCode = "应付业务支出-健康保障委托管理业务";
						mEnteredDR = String.valueOf(tmoney);
						mEnteredCR = "";
					} 
					else
					{
						if (tLJAGetSchema.getPayMode() != null)
						{
							if (!tLJAGetSchema.getPayMode().equals("") && !tLJAGetSchema.getPayMode().equals("1"))
							{
								if (tLJAGetSchema.getPayMode().equals("5"))
								{
									mBillID = "0891";
									mAccountCode = "应付业务支出-内部转帐"; //内部转账贷
								} 
								else
								{
									mBillID = "0892";
									mAccountCode = "其他应付款-资金中转-集中批付"; //银行存款支
									mAccountSubCode = "C" + mManageCom.substring(2, 4).concat("0000");
								}
							}
						}
						mEnteredDR = "";
						mEnteredCR = String.valueOf(tmoney);
					}
					mHeadDescription = mInsuredName + "的其它给付退费";
					if (isExitInTab(tLJAGetOtherSchema.getActuGetNo(), tLJAGetOtherSchema.getOtherNo(), mBillID))
						break;
		
					OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema) entry();
					if (tOFInterfaceSchema != null)
						mOFInterfaceSet.add(tOFInterfaceSchema);
		
					dealLITranInfo();
				}
		
				//分公司借记科目
				//取得机构对应的分公司
				mManageCom = tLJAGetSchema.getManageCom().substring(0, 4).concat("00");
				mSegment2 = mManageCom.concat("00");
				mBillID = "0893";
				mAccountCode = "其他应收款-资金中转-集中批付"; //银行存款支
				mAccountSubCode = "C" + tLJAGetSchema.getManageCom().substring(2, 6).concat("00");
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = String.valueOf(tmoney);
				mEnteredCR = "";
				if (isExitInTab(mBussNo, mPolNo, mBillID))
					continue;
		
				OFInterfaceSchema t1OFInterfaceSchema = (OFInterfaceSchema) entry();
				if (t1OFInterfaceSchema != null)
					mOFInterfaceSet.add(t1OFInterfaceSchema);
		
				dealLITranInfo();
		
				//分公司贷记科目
				mBillID = "0894";
				mAccountCode = "银行存款-活期-人民币"; //银行存款支
				mAccountSubCode = "付" + tLJAGetSchema.getBankCode();
				mHeadDescription = mAccountCode + mInsuredName;
				mEnteredDR = "";
				mEnteredCR = String.valueOf(tmoney);
				if (isExitInTab(mBussNo, mPolNo, mBillID))
					continue;
		
				OFInterfaceSchema t2OFInterfaceSchema = new OFInterfaceSchema();
				t2OFInterfaceSchema = (OFInterfaceSchema) entry();
				if (t2OFInterfaceSchema != null)
					mOFInterfaceSet.add(t2OFInterfaceSchema);
		
				dealLITranInfo();
			}
		}
		return true;
}

	/***********************************************************************************************************************************************************
	 * 错误处理函数开始
	 */
	private void dealError(String TabName, String RecNo, String PNo, String ErrNo, String BID, String ErrDesc)
	{
		LITranErrSchema tLITranErrSchema = new LITranErrSchema();
		tLITranErrSchema.setSerialNo(PubFun1.CreateMaxNo("LITranErrNo",20));
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
		mLineDescription = mManageCom + "||" + mBussNo + "||" + mInsuredName + "||" + mAttribute5 + "||" + mBillID; // 公司段说明||收付单据号||保户人姓名||类别明细||BillID
		String[] tVerSubject = { mAccountSubCode, mManageCom, mRiskCode, mSaleChnl };

		try
		{
			tVerSubject = vertifySubject(mAccountCode); // 校验科目代码
			String tRecordID = PubFun1.CreateMaxNo("OTOF", "RECORD");

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
			rOFInterfaceSchema.setAttribute6(mAttribute6); // 代理机构
			rOFInterfaceSchema.setAttribute7("NA"); // 空闲属性7
			rOFInterfaceSchema.setAttribute8("NA"); // 空闲属性8
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

	private HashSet mLITranInfoSetHashtable=new HashSet();
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
		mInputData.clear();
		mInputData.add(mp);
	}

	private boolean setPolInfo(String tPolNo, String tTableName, String tId)
	{
		String pSql="";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			pSql = "select riskcode,salechnl,insuredname,conttype,payintv from lcpol where " 
			         + "polno='" + "?w12?"+ "'  limit 0,1  " 
			         + "union all "
				     + "select riskcode,salechnl,insuredname,conttype,payintv from lcpol where " 
				     + "contno='" + "?w13?"+ "'  limit 0,1 ";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			pSql = "select riskcode,salechnl,insuredname,conttype,payintv from lcpol where " 
			         + "polno='" + "?w12?"+ "'  and rownum=1  " 
			         + "union all "
				     + "select riskcode,salechnl,insuredname,conttype,payintv from lcpol where " 
				     + "contno='" + "?w13?"+ "'  and rownum=1 ";
		}
		SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
		sqlbv19.sql(pSql);
		sqlbv19.put("w12", tPolNo);
		sqlbv19.put("w13", tPolNo);
		ExeSQL mExeSQL = new ExeSQL();
		SSRS mSSRS = new SSRS();
		mSSRS = mExeSQL.execSQL(sqlbv19);
		if(mSSRS.getMaxRow() <= 0)
		{
			mSSRS = new SSRS();
			String gSql = "";
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				gSql = "select riskcode,salechnl,grpname,payintv from lcgrppol where " 
						 + "grppolno='" + "?w14?"+ "'  limit 0,1 " 
				         + "union all  " 
				         + "select riskcode,salechnl,grpname,payintv from lcgrppol where "
				         + "grpcontno='" + "?w15?" + "'  limit 0,1 ";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				gSql = "select riskcode,salechnl,grpname,payintv from lcgrppol where " 
						 + "grppolno='" + "?w14?"+"'  and rownum=1 "
				         + "union all  " 
				         + "select riskcode,salechnl,grpname,payintv from lcgrppol where "
				         + "grpcontno='" + "?w15?" + "'  and rownum=1 ";
			}
			SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
			sqlbv20.sql(pSql);
			sqlbv20.put("w14", tPolNo);
			sqlbv20.put("w15", tPolNo);
			mSSRS = mExeSQL.execSQL(sqlbv20);
			if(mSSRS == null || mSSRS.getMaxRow() <= 0)
			{
				dealError(tTableName, mBussNo, tPolNo, "001", tId, "lcgrppol表对应的保单信息不存在");
				return false;
			}
			else
			{
				mRiskCode = mSSRS.GetText(1, 1);
				mSaleChnl = mSSRS.GetText(1, 2);
				mInsuredName = mSSRS.GetText(1, 3).trim();
				if(mInsuredName.length() > 20)
					mInsuredName = mInsuredName.substring(0, 20);
			}
		}
		else
		{
			mRiskCode = mSSRS.GetText(1, 1);
			mSaleChnl = mSSRS.GetText(1, 2);
			mInsuredName = mSSRS.GetText(1, 3).trim();
			if(mInsuredName.length() > 20)
				mInsuredName = mInsuredName.substring(0, 20);
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
		mSaleChnl = tLCPolBL.getSaleChnl();
		mInsuredName = tLCPolBL.getInsuredName();

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
		mSaleChnl = tLCGrpPolBL.getSaleChnl();
		mInsuredName = tLCGrpPolBL.getGrpName();
		return tLCGrpPolBL;
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


	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		

	}

}
