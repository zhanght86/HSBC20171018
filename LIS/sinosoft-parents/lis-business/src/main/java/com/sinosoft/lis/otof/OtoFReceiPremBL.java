package com.sinosoft.lis.otof;
import org.apache.log4j.Logger;

/**
 * <p>Title: Lis_New</p>
 *
 * <p>Description:应收保费的提取 </p>
 *
 * <p>Copyright: Copyright (c) 2002</p>
 *
 * <p>Company: </p>
 *
 * @author Sinosoft
 * @version 1.0
 */
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

import java.util.*;
import java.text.*;

public class OtoFReceiPremBL
{
private static Logger logger = Logger.getLogger(OtoFReceiPremBL.class);

	public OtoFReceiPremBL()
	{
	}

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 数据操作字符串 */
	private String mOperate;

	private String mToday = "";
	private int mTime = 0;
	private String bDate = "";
	private String eDate = "";
	private String mInputDate = ""; //界面传入的记帐日期
	private String tManageCom = ""; //提取机构
	private String Flag = "";
	private VData vd = new VData();
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
	private String mInsuredName = ""; //被保险人姓名
	private String mBussNo = ""; //收付款单据号
	private String mAttribute5 = ""; //行为明细类别
	private String mAttribute6 = ""; //代理机构（航意险应收凭证）
	private GlobalInput mGlobalInput = new GlobalInput();
	private LIVertifySet mLIVertifySet = new LIVertifySet();         //科目约束性
	//对表的定义
	OFInterfaceSet mOFInterfaceSet = new OFInterfaceSet();
	LITranInfoSet mLITranInfoSet = new LITranInfoSet();
	LITranErrSet mLITranErrSet = new LITranErrSet();
	private PubConcurrencyLock mLock = new PubConcurrencyLock();
	private String tRecordNo;     //进行锁表的管理机构
	/**
	 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		logger.debug("--- OtoFReceiPremBL begin ---");
		mInputData = (VData)cInputData.clone();
		this.mOperate = cOperate;

		try
		{

			if(cOperate.equals(""))
			{
				CError.buildErr(this, "不支持的业务凭证提取操作字符串");
				return false;
			}

			initVar(); //避免getinputdata中的数据被冲掉
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
			mLock.unLock();
		}
		return true;
	}



	private boolean getInputData(VData cInputData)
	{
		mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0));

		TransferData tTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
		bDate = (String)tTransferData.getValueByName("bDate"); //提取起始日期
		eDate = (String)tTransferData.getValueByName("eDate"); //提取终止日期
		mInputDate = (String)tTransferData.getValueByName("accountDate"); //记账日期
		Integer itemp = (Integer)tTransferData.getValueByName("itemp"); //凭证类别（应收保费61）
		mTime = itemp.intValue();
		tManageCom = (String)tTransferData.getValueByName("Managecom"); //管理机构
		Flag = (String)tTransferData.getValueByName("Flag"); //手工提取标志

		if(bDate.equals(""))
		{
			CError.buildErr(this, "没有提取起始日期!");
			return false;
		}
		if(bDate.length() != 10)
		{
			CError.buildErr(this, "请检查本地机器的时间格式设置，请采用YYYY-MM-DD格式");
			return false;
		}
		if(eDate.equals(""))
		{
			CError.buildErr(this, "没有提取终止日期!");
			return false;
		}
		if(eDate.length() != 10)
		{
			CError.buildErr(this, "请检查本地机器的时间格式设置，请采用YYYY-MM-DD格式");
			return false;
		}

		if(mGlobalInput == null)
		{
			CError.buildErr(this, "没有得到足够的信息！");
			return false;
		}

		if(tManageCom == null)
		{
			CError.buildErr(this, "没有提取机构的信息！");
			return false;
		}

		return true;
	}

	private boolean checkData()
	{
		FDate chgdate = new FDate();
		Date dedate = chgdate.getDate(eDate);
		mToday = chgdate.getString(dedate);
		mLITranErrSet.clear();
		mLITranInfoSet.clear();
		tRecordNo=tManageCom+new SimpleDateFormat("yyyyMMdd").format(dedate)+String.valueOf(mTime);
		LIVertifyDB tLIVertifyDB = new LIVertifyDB();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("select * from livertify");
		mLIVertifySet = (LIVertifySet)tLIVertifyDB.executeQuery(sqlbv);

		return true;
	}
	
	

	private boolean dealData()
	{
		
	        
    	logger.debug("tRecordNo---------"+tRecordNo);
    	//调用并发控制程序
    	//调用并发控制程序
    	if(!mLock.lock(tRecordNo, "LF1001", mGlobalInput.Operator))
    	{
			CError tError = new CError(mLock.mErrors.getLastError());
			this.mErrors.addOneError(tError);
			return false;
    	}

		//提取本月的应收保费数据
		if(mOperate.equals("Prem"))
		{
			ReceiDataBL pReceiDataBL = new ReceiDataBL();

			if(!pReceiDataBL.submitData(mInputData, "Prem"))
			{
				CError.buildErr(this, "获取应收保费失败");
				return false;
			}
		}

		//提取本月应收保费凭证
		if(!getPrem())
		{
			CError.buildErr(this, "提取应收保费错误");
			return false;
		}
		


		return true;
	}
	
	private boolean getPrem()
	{

		mBatchNo = PubFun1.CreateMaxNo("OTOF", "20");
		//由于bussno是20位长度，该张凭证每月提取一次，且每年会进行系统表的转储，所以只获取年的后两位和月份
		String tDate= mToday.substring(2,4)+mToday.substring(5,7);
		/*应收保费-首年续期，应收保费续年续期统一调整为应收保费-续期科目调整*/
		//首年续期应收保费
		String fsql = "select * from OfinaStorData where vouchertype='61' and "
					+ "transdate='" + "?transdate?"+ "' and type='S' and managecom like concat('" + "?like?" + "','%')";
		   SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		    sqlbv1.sql(fsql);
		    sqlbv1.put("transdate", mToday);
		    sqlbv1.put("like", tManageCom);
		logger.debug("查询首期续收应收保费---------" + fsql);
		OfinaStorDataDB sOfinaStorDataDB = new OfinaStorDataDB();
		OfinaStorDataSet sOfinaStorDataSet = sOfinaStorDataDB.executeQuery(sqlbv1);
		for(int i = 1; i <= sOfinaStorDataSet.size(); i++)
		{
			OfinaStorDataSchema sOfinaStorDataSchema = new OfinaStorDataSchema();
			sOfinaStorDataSchema = sOfinaStorDataSet.get(i);
			if(sOfinaStorDataSchema.getMoney() <= 0.0)
			{
				dealError("OfinaStorData", sOfinaStorDataSchema.getManageCom(), sOfinaStorDataSchema.getRiskCode(),
						"006", "001", "首期续收应收保费金额为非正数");
				continue;
			}
			//为变量附值
			initVar();
			mMatchID++;
			mTransDate = mToday;
			mSegment2 = sOfinaStorDataSchema.getManageCom();
			mManageCom = sOfinaStorDataSchema.getManageCom().substring(0, 6);
			logger.debug("tDate--"+tDate);
			mBussNo = tDate+String.valueOf(mTime) + mSegment2 + mMatchID;
			mPolNo = mBussNo;
			tmoney = new DecimalFormat("0.00").format(new Double(sOfinaStorDataSchema.getMoney()));
			mSaleChnl = sOfinaStorDataSchema.getSaleChnl();
			mRiskCode = sOfinaStorDataSchema.getRiskCode();
			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					mBillID = "6101";
					mAccountCode = "应收保费-续期";
					mEnteredDR = String.valueOf(tmoney); //借
					mEnteredCR = ""; //贷
					mHeadDescription = "提取" + mSegment2 + "应收保费";
				}
				else
				{
					mBillID = "6102";
					mAccountCode = "保费收入-首年续期";
					mEnteredDR = "";
					mEnteredCR = String.valueOf(tmoney);
					mHeadDescription = "收" + mSegment2 + "保费";
				}

				if(isExitInTab(mBussNo, mPolNo, mBillID))
				{
					break;
				}

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
				{
					mOFInterfaceSet.add(tOFInterfaceSchema);
				}
				dealLITranInfo();

			}
		}

		//续年续期应收保费
		String xsql = "select * from OfinaStorData where vouchertype='61' and transdate='" + "?transdate1?"+ "' "
					+ "and type='X' and managecom like concat('" + "?like1?" + "','%')";
		 SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		    sqlbv2.sql(xsql);
		    sqlbv2.put("transdate1", mToday);
		    sqlbv2.put("like1", tManageCom);
		logger.debug("查询续期续收应收保费---------" + xsql);
		OfinaStorDataDB xOfinaStorDataDB = new OfinaStorDataDB();
		OfinaStorDataSet xOfinaStorDataSet = xOfinaStorDataDB.executeQuery(sqlbv2);
		for(int i = 1; i <= xOfinaStorDataSet.size(); i++)
		{
			OfinaStorDataSchema xOfinaStorDataSchema = new OfinaStorDataSchema();
			xOfinaStorDataSchema = xOfinaStorDataSet.get(i);
			if(xOfinaStorDataSchema.getMoney() <= 0.0)
			{
				dealError("OfinaStorData", xOfinaStorDataSchema.getManageCom(), xOfinaStorDataSchema.getRiskCode(),
						"006", "001", "续期续收应收保费金额为非正数");
				continue;
			}
			//为变量附值
			initVar();
			mMatchID++;
			mTransDate = mToday;
			mSegment2 = xOfinaStorDataSchema.getManageCom();
			mManageCom = xOfinaStorDataSchema.getManageCom().substring(0, 6);
			mBussNo =  tDate+String.valueOf(mTime) + mSegment2 + mMatchID;
			mPolNo = mBussNo;
			tmoney = new DecimalFormat("0.00").format(new Double(xOfinaStorDataSchema.getMoney()));
			mSaleChnl = xOfinaStorDataSchema.getSaleChnl();
			mRiskCode = xOfinaStorDataSchema.getRiskCode();
			for(int j = 1; j <= 2; j++)
			{
				if(j == 1)
				{
					mBillID = "6103";
					mAccountCode = "应收保费-续期";
					mEnteredDR = String.valueOf(tmoney);
					mEnteredCR = "";
					mHeadDescription = "提取" + mSegment2 + "应收保费";
				}
				else
				{
					mBillID = "6104";
					mAccountCode = "保费收入-续年续期";
					mEnteredDR = ""; //借
					mEnteredCR = String.valueOf(tmoney); //贷
					mHeadDescription = "收" + mSegment2 + "保费";
				}

				if(isExitInTab(mBussNo, mPolNo, mBillID))
				{
					break;
				}

				OFInterfaceSchema tOFInterfaceSchema = (OFInterfaceSchema)entry();
				if(tOFInterfaceSchema != null)
				{
					mOFInterfaceSet.add(tOFInterfaceSchema);
				}
				dealLITranInfo();
			}
		}

		return true;
	}
	
	private void initVar()
	{
		mReversedStatus = "0"; //冲消状态
		mOrigRowID = ""; //被冲消的行
		mReversedRowID = ""; //冲消生成的行
		mCurrencyCode = "CNY"; //币别
		mVoucherType = String.valueOf(mTime); //凭证类别
		mManageCom = "NA"; //核算单位代码
		mSegment2 = "NA"; //成本中心
		mAccountCode = "NA"; //科目代码
		mAccountSubCode = "NA"; //科目明细代码
		mSaleChnl = "NA"; //销售渠道
		mRiskCode = "NA"; //保险产品代码
		mTransDate = ""; //事务日期
		mAccountingDate = (mInputDate.equals("")) ? PubFun.getCurrentDate() : mInputDate; //记帐日期
		mEnteredDR = ""; //事务借计金额
		mEnteredCR = ""; //事务贷计金额
		mHeadDescription = "NA"; //日记帐摘要
		mLineDescription = "NA"; //行记帐摘要
		mPolNo = ""; //保单号
		mInsuredName = ""; //被保险人姓名
		mBussNo = ""; //收付款单据号
		mAttribute5 = "核心业务系统"; //行为明细类别－来源
		mAttribute6 = "NA";
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
			rOFInterfaceSchema.setPolNo(mPolNo); // 保单号
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

	private HashSet mLITranInfoSetHashtable=new HashSet();
	//预留出对日志表的操作
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
		tLITranErrSchema.setManageCom(tManageCom);
		tLITranErrSchema.setMakeDate(PubFun.getCurrentDate());
		tLITranErrSchema.setMakeTime(PubFun.getCurrentTime());
		tLITranErrSchema.setOperator(mGlobalInput.Operator);
		if((tLITranErrSchema.getBussNo() == null) || (tLITranErrSchema.getPolicyNo() == null))
			return;
		mLITranErrSet.add(tLITranErrSchema);
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
	
	public static void main(String[] args)
	{
		OtoFReceiPremBL tOtoFReceiPremBL = new OtoFReceiPremBL();
		VData vData = new VData();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		vData.addElement(tG);
		TransferData tTransferData = new TransferData();
		String bDate = "2007-11-25";
		String accountDate = "2007-11-19";
		Integer itemp = new Integer(61);
		String Managecom = "86";
		tTransferData.setNameAndValue("bDate", bDate);
		tTransferData.setNameAndValue("accountDate", accountDate);
		tTransferData.setNameAndValue("itemp", itemp);
		tTransferData.setNameAndValue("Managecom", Managecom);
		vData.addElement(tTransferData);

		tOtoFReceiPremBL.submitData(vData, "Prem");
	}

}
