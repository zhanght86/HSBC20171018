package com.sinosoft.lis.bq;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LOLoanDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.AccountManage;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LOLoanSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPReturnLoanSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LOLoanSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPReturnLoanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保单质押贷款清偿BL</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Nicholas PST
 * @version 1.0   2.0
 */
import java.util.HashMap;
import java.util.Iterator;

public class PEdorRFDetailBL {
private static Logger logger = Logger.getLogger(PEdorRFDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 改变保单状态相关 */
	private Reflections mReflections = new Reflections();
	private LPContStateSet mLPContStateSet = new LPContStateSet();

	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private TransferData mTransferData = new TransferData();
	/** 从页面传入来的Set 记录数大小均为1 */
	private LOLoanSet tLOLoanSet = new LOLoanSet();
	/** 从需要处理的Set，里面包含页面传来的要还的钱 记录数大小均为1 */
	private LOLoanSet mLOLoanSet = new LOLoanSet();

	/**险种编码*/ 
	private String tRiskCode = "";
	
	private String tPayOffFlag="";

	// 系统当前时间
	private String  mCurrentDate= PubFun.getCurrentDate();

	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorRFDetailBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据查询，初始化界面时初始化MulLine用
		if (mOperate.equals("QUERY||MAIN")) {
			if (!getInterfaceData()) {
				return false;
			} else {
				return true;
			}
		}
		// 数据校验操作
		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			// 保全项目校验
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			if(mLPEdorItemSchema==null ||  "".equals(mLPEdorItemSchema.getEdorAcceptNo()) || mLPEdorItemSchema.getEdorAcceptNo()==null)
			{
				CError tError = new CError();
				tError.moduleName = "PEdorRFDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "接收数据失败！";
				this.mErrors.addOneError(tError);
				return false;
			}else
			{

				LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
				tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
				tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());

				mLPEdorItemSchema = tLPEdorItemDB.query().get(1);	
			}

			
			// 如果是查询
			if (mOperate.equals("QUERY||MAIN")) {
				return true;
			}
			// 不是查询
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			tLOLoanSet = (LOLoanSet) mInputData.getObjectByObjectName(
					"LOLoanSet", 0);
			if (tLOLoanSet == null || tLOLoanSet.size() < 1) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorRFDetailBL";
				tError.functionName = "getInputData";
				tError.errorMessage = "还款数据传入不足！";
				this.mErrors.addOneError(tError);
				return false;
			}
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);
			tPayOffFlag=(String)mTransferData.getValueByName("PayOffFlag");
			return true;
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorRFDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		if (!mOperate.equals("QUERY||MAIN")) {
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setSchema(mLPEdorItemSchema);
			mLPEdorItemSchema.setSchema(tLPEdorItemDB.query().get(1));	
			
    		LOLoanDB mLOLoanDB = new LOLoanDB();
			for (int i = 1; i <= tLOLoanSet.size(); i++) {
				LOLoanSchema mLOLoanSchema = new LOLoanSchema();
				LOLoanSchema tLOLoanSchema = new LOLoanSchema();
				LOLoanSet rLOLoanSet = new LOLoanSet();
				mLOLoanSchema = tLOLoanSet.get(i);
				double tLoanMoney = mLOLoanSchema.getSumMoney(); // 页面输入的值

				String tSQL = "select * from LoLoan where contno='?contno?' and EdorNo='?EdorNo?' and payoffflag='0' and LoanType='0' and currency = '?currency?'";
				logger.debug(tSQL);
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSQL);
				sqlbv.put("contno", mLOLoanSchema.getContNo());
				sqlbv.put("EdorNo", mLOLoanSchema.getEdorNo());
				sqlbv.put("currency", mLOLoanSchema.getCurrency());
				rLOLoanSet = mLOLoanDB.executeQuery(sqlbv);
				if (rLOLoanSet.size() < 1) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorRFDetailBL";
					tError.functionName = "checkData";
					tError.errorMessage = "获得险种借款数据失败！";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLOLoanSchema = rLOLoanSet.get(1);

				// 实际要还的值
				tLOLoanSchema.setLastSumLoanMoney(tLoanMoney);	
				//存放截止本次还款的利息
				tLOLoanSchema.setPreInterest(mLOLoanSchema.getPreInterest());
				// 欠款总额
				tLOLoanSchema.setLeaveMoney(mLOLoanSchema.getLeaveMoney());				

				mLOLoanSet.add(tLOLoanSchema);
			}
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			// 获得此时的日期和时间
			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();
			
			//由于界面多次保存的原因会出现误还的情况
			String tDelStr="delete from LPReturnLoan where contno='?contno?' and  edorno='?edorno?' and edortype='?edortype?'";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(tDelStr);
			sbv1.put("contno", mLPEdorItemSchema.getContNo());
			sbv1.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv1.put("edortype", mLPEdorItemSchema.getEdorType());
			mMap.put(sbv1, "DELETE");
			
			String tDelStrContState="delete from LPContState where contno='?contno?' and  edorno='?edorno?' and edortype='?edortype?'";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(tDelStrContState);
			sbv2.put("contno", mLPEdorItemSchema.getContNo());
			sbv2.put("edorno", mLPEdorItemSchema.getEdorNo());
			sbv2.put("edortype", mLPEdorItemSchema.getEdorType());
			mMap.put(sbv2, "DELETE");
			

			String DelEndorseStr="delete from LJSGetEndorse where contno='?contno?' and Endorsementno='?Endorsementno?' "
            + " and  Feeoperationtype='?Feeoperationtype?'";
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(DelEndorseStr);
			sbv3.put("contno", mLPEdorItemSchema.getContNo());
			sbv3.put("Endorsementno", mLPEdorItemSchema.getEdorNo());
			sbv3.put("Feeoperationtype", mLPEdorItemSchema.getEdorType());
			mMap.put(sbv3, "DELETE");
			// 生成“保全还款业务表”数据-------------------->
			LPReturnLoanSet tLPReturnLoanSet = new LPReturnLoanSet();
			LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
			
			Map curReturnMap = new HashMap();
			Map curIntrestMap = new HashMap();
			Map curPolNoMap = new HashMap();
			// 循环这个险种执行每条借款记录
			double dSumMoney=0;
			for (int i = 1; i <= mLOLoanSet.size(); i++) {
				LOLoanSchema tLOLoanSchema = new LOLoanSchema();
				LPReturnLoanSchema tLPReturnLoanSchema = new LPReturnLoanSchema();
				tLOLoanSchema = mLOLoanSet.get(i);
				
				// 组织数据
				tLPReturnLoanSchema.setContNo(mLPEdorItemSchema.getContNo());
				tLPReturnLoanSchema.setPolNo(tLOLoanSchema.getPolNo());
				tLPReturnLoanSchema.setEdorType("RF");
				tLPReturnLoanSchema.setSerialNo(tLOLoanSchema.getSerialNo()); 
				tLPReturnLoanSchema.setActuGetNo(tLOLoanSchema.getActuGetNo()); 
				tLPReturnLoanSchema.setLoanType(tLOLoanSchema.getLoanType()); 
				tLPReturnLoanSchema.setOrderNo(tLOLoanSchema.getOrderNo()); 
				tLPReturnLoanSchema.setLoanDate(tLOLoanSchema.getLoanDate()); 
				
				tLPReturnLoanSchema.setSumMoney(tLOLoanSchema.getSumMoney()); 
				tLPReturnLoanSchema.setInputFlag(tLOLoanSchema.getInputFlag()); 
				tLPReturnLoanSchema.setInterestType(tLOLoanSchema.getInterestType()); 
				tLPReturnLoanSchema.setInterestRate(tLOLoanSchema.getInterestRate()); 
				tLPReturnLoanSchema.setInterestMode(tLOLoanSchema.getInterestMode());
				tLPReturnLoanSchema.setRateCalType(tLOLoanSchema.getRateCalType()); 
				tLPReturnLoanSchema.setRateCalCode(tLOLoanSchema.getRateCalCode()); 
				tLPReturnLoanSchema.setSpecifyRate(tLOLoanSchema.getSpecifyRate());
				tLPReturnLoanSchema.setCurrency(tLOLoanSchema.getCurrency());
				tLPReturnLoanSchema.setLeaveMoney(tLOLoanSchema.getLeaveMoney()); // 余额
				if (tLOLoanSchema.getLeaveMoney() == 0) // 还清,改变险种状态
				{
					tLPReturnLoanSchema.setPayOffFlag("1");
					tLPReturnLoanSchema.setPayOffDate(mLPEdorItemSchema.getEdorAppDate());
				} else {
					tLPReturnLoanSchema.setPayOffFlag("0");
					tLPReturnLoanSchema.setPayOffDate("");
				}
				tLPReturnLoanSchema.setOperator(this.mGlobalInput.Operator);
				tLPReturnLoanSchema.setMakeDate(strCurrentDate);
				tLPReturnLoanSchema.setMakeTime(strCurrentTime);
				tLPReturnLoanSchema.setModifyDate(strCurrentDate);
				tLPReturnLoanSchema.setModifyTime(strCurrentTime);
				tLPReturnLoanSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPReturnLoanSchema.setLoanNo(tLOLoanSchema.getEdorNo()); // 原批单号，界面传入,关键字段，对应那次还款，由于可以多次还款，则可以出现
				tLPReturnLoanSchema.setReturnMoney(tLOLoanSchema.getLastSumLoanMoney()-tLOLoanSchema.getPreInterest()); // 放入还款的本金
				tLPReturnLoanSchema.setReturnInterest(tLOLoanSchema.getPreInterest()); //存放还款的利息
				
				double dReturn = 0.0;
				double dIntrest = 0.0;
				if(curReturnMap.containsKey(tLOLoanSchema.getCurrency())){
					dReturn = (Double)curReturnMap.get(tLOLoanSchema.getCurrency());
					dIntrest = (Double)curIntrestMap.get(tLOLoanSchema.getCurrency());
				}
				
				dReturn+=tLOLoanSchema.getLastSumLoanMoney();
				dSumMoney+=tLOLoanSchema.getLastSumLoanMoney();
				dIntrest+=tLOLoanSchema.getPreInterest();
				
				curReturnMap.put(tLOLoanSchema.getCurrency(), dReturn);
				curIntrestMap.put(tLOLoanSchema.getCurrency(), dIntrest);
				curPolNoMap.put(tLOLoanSchema.getCurrency(), tLOLoanSchema.getPolNo());
				
				tLPReturnLoanSet.add(tLPReturnLoanSchema);				  
			}
			
			String tSql = "";
			ExeSQL tExeSQL = new ExeSQL();
			Iterator curIT = curReturnMap.keySet().iterator();
			while(curIT.hasNext()){
				// 生成“批改补退费表（应收/应付）”数据-------------------->
				// 本金记录和利息记录，利息为初始利息和逾期利息的和
				// 查询“单位名称”
				String tCurrency = (String)curIT.next();
				String tPolNo = (String)curPolNoMap.get(tCurrency);
				double dReturn=  (Double)curReturnMap.get(tCurrency);
				double dIntrest = (Double)curIntrestMap.get(tCurrency);
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(tPolNo);
				if(!tLCPolDB.getInfo()){
					CError.buildErr(this, "查询保单险种信息失败！");
					return false;
				}
				LCPolSchema tLCPolSchema = tLCPolDB.getSchema();
				String tGrpName = "";
				tSql = "SELECT a.GrpName" + " FROM LCAddress a,LCInsured b,LCPol c"
				+ " WHERE a.CustomerNo=b.InsuredNo"
				+ " and a.AddressNo=b.AddressNo"
				+ " and b.InsuredNo=c.InsuredNo" + " and b.ContNo=c.ContNo"
				+ " and c.PolNo='?PolNo?'";
				SQLwithBindVariables sbv4=new SQLwithBindVariables();
				sbv4.sql(tSql);
				sbv4.put("PolNo", tLCPolSchema.getPolNo());
				SSRS tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sbv4);
				if (tSSRS != null && tSSRS.MaxRow > 0) {
					tGrpName = tSSRS.GetText(1, 1);
				}
				LJSGetEndorseSchema tLJSGetEndorseSchema = null;

				// 生成“批改补退费表（应收/应付）”数据（本金）
				tLJSGetEndorseSchema = new LJSGetEndorseSchema();
				tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema
						.getEdorAcceptNo());
				tLJSGetEndorseSchema
				.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
				tLJSGetEndorseSchema.setFeeOperationType("RF");
				tLJSGetEndorseSchema.setGetFlag("0"); // 相当于补费

				// 查询“补/退费财务类型”
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
					tSql = "SELECT CodeName" + " FROM ((select '1' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='RF' and Code='HK' and Code1='?Code1?')" + " union"
							+ " (select '2' as flag,CodeName" + " from LDCode1"
							+ " where CodeType='RF' and Code='HK' and Code1='000000'))"
							+ " WHERE rownum=1" + " ORDER BY flag";	
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					tSql = "SELECT CodeName" + " FROM ((select '1' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='RF' and Code='HK' and Code1='?Code1?')" + " union"
							+ " (select '2' as flag,CodeName" + " from LDCode1"
							+ " where CodeType='RF' and Code='HK' and Code1='000000')) g"
							+ " ORDER BY flag"+ " limit 0,1 " ;	
				}
				
				// 查询
				SQLwithBindVariables sbv5=new SQLwithBindVariables();
				sbv5.sql(tSql);
				sbv5.put("Code1", tLCPolSchema.getRiskCode());
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sbv5);
				if (tSSRS == null && tSSRS.MaxRow <= 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorRFDetailBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询还款的“补/退费财务类型”时产生错误！";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLJSGetEndorseSchema.setFeeFinaType(tSSRS.GetText(1, 1));

				tLJSGetEndorseSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
				tLJSGetEndorseSchema.setContNo(tLCPolSchema.getContNo());
				tLJSGetEndorseSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
				tLJSGetEndorseSchema.setPolNo(tLCPolSchema.getPolNo());
				tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
				tLJSGetEndorseSchema.setOtherNoType("3");
				tLJSGetEndorseSchema.setDutyCode("000000");
				tLJSGetEndorseSchema.setPayPlanCode("000000");
				tLJSGetEndorseSchema.setAppntNo(tLCPolSchema.getAppntNo());
				tLJSGetEndorseSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
				tLJSGetEndorseSchema.setGetDate(strCurrentDate);
				tLJSGetEndorseSchema.setGetMoney(dReturn-dIntrest);
				tLJSGetEndorseSchema.setKindCode(tLCPolSchema.getKindCode());
				tLJSGetEndorseSchema.setRiskCode(tLCPolSchema.getRiskCode());
				tLJSGetEndorseSchema.setRiskVersion(tLCPolSchema.getRiskVersion());
				tLJSGetEndorseSchema.setManageCom(tLCPolSchema.getManageCom());
				tLJSGetEndorseSchema.setAgentCom(tLCPolSchema.getAgentCom());
				tLJSGetEndorseSchema.setAgentType(tLCPolSchema.getAgentType());
				tLJSGetEndorseSchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLJSGetEndorseSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
				tLJSGetEndorseSchema.setGrpName(tGrpName);
				tLJSGetEndorseSchema.setHandler(""); // 暂时不知道如何处理
				tLJSGetEndorseSchema.setPolType("1");
				tLJSGetEndorseSchema.setSerialNo(""); // 暂时不知道如何处理
				tLJSGetEndorseSchema.setCurrency(tCurrency);
				tLJSGetEndorseSchema.setOperator(this.mGlobalInput.Operator);
				tLJSGetEndorseSchema.setMakeDate(strCurrentDate);
				tLJSGetEndorseSchema.setMakeTime(strCurrentTime);
				tLJSGetEndorseSchema.setModifyDate(strCurrentDate);
				tLJSGetEndorseSchema.setModifyTime(strCurrentTime);
				tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_LoanCorpus);

				tLJSGetEndorseSet.add(tLJSGetEndorseSchema);


				//生成“批改补退费表（应收/应付）”数据（利息）
				tLJSGetEndorseSchema = new LJSGetEndorseSchema();
				tLJSGetEndorseSchema.setGetNoticeNo(mLPEdorItemSchema.getEdorAcceptNo());
				tLJSGetEndorseSchema.setEndorsementNo(mLPEdorItemSchema.getEdorNo());
				tLJSGetEndorseSchema.setFeeOperationType("RF");

				//查询“补/退费财务类型”
				//组织SQL语句
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSql = "SELECT CodeName"
					+ " FROM ((select '1' as flag,CodeName"
					+ " from LDCode1"
					+ " where CodeType='RF' and Code='LX' and Code1='?Code1?')"
					+ " union"
					+ " (select '2' as flag,CodeName"
					+ " from LDCode1"
					+ " where CodeType='RF' and Code='LX' and Code1='000000'))"
					+ " WHERE rownum=1"
					+ " ORDER BY flag";
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					tSql = "SELECT CodeName"
							+ " FROM ((select '1' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='RF' and Code='LX' and Code1='?Code1?')"
							+ " union"
							+ " (select '2' as flag,CodeName"
							+ " from LDCode1"
							+ " where CodeType='RF' and Code='LX' and Code1='000000')) g"
							+ " ORDER BY flag"
							+ " limit 0,1";
				}
				//查询
				SQLwithBindVariables sbv6=new SQLwithBindVariables();
				sbv6.sql(tSql);
				sbv6.put("Code1", tLCPolSchema.getRiskCode());
				tSSRS = new SSRS();
				tSSRS = tExeSQL.execSQL(sbv6);
				if (tSSRS == null && tSSRS.MaxRow <= 0)
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorRFDetailBL";
					tError.functionName = "dealData";
					tError.errorMessage = "查询利息的“补/退费财务类型”时产生错误！";
					this.mErrors.addOneError(tError);
					return false;
				}
				tLJSGetEndorseSchema.setFeeFinaType(tSSRS.GetText(1, 1));

				tLJSGetEndorseSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
				tLJSGetEndorseSchema.setContNo(tLCPolSchema.getContNo());
				tLJSGetEndorseSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
				tLJSGetEndorseSchema.setPolNo(tLCPolSchema.getPolNo());
				tLJSGetEndorseSchema.setOtherNo(mLPEdorItemSchema.getEdorNo());
				tLJSGetEndorseSchema.setOtherNoType("3");
				tLJSGetEndorseSchema.setDutyCode("000000");
				tLJSGetEndorseSchema.setPayPlanCode("000000");
				tLJSGetEndorseSchema.setAppntNo(tLCPolSchema.getAppntNo());
				tLJSGetEndorseSchema.setInsuredNo(tLCPolSchema.getInsuredNo());
				tLJSGetEndorseSchema.setGetDate(strCurrentDate);
				tLJSGetEndorseSchema.setGetMoney(dIntrest);
				tLJSGetEndorseSchema.setKindCode(tLCPolSchema.getKindCode());
				tLJSGetEndorseSchema.setRiskCode(tLCPolSchema.getRiskCode());
				tLJSGetEndorseSchema.setRiskVersion(tLCPolSchema.getRiskVersion());
				tLJSGetEndorseSchema.setManageCom(tLCPolSchema.getManageCom());
				tLJSGetEndorseSchema.setAgentCom(tLCPolSchema.getAgentCom());
				tLJSGetEndorseSchema.setAgentType(tLCPolSchema.getAgentType());
				tLJSGetEndorseSchema.setAgentCode(tLCPolSchema.getAgentCode());
				tLJSGetEndorseSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
				tLJSGetEndorseSchema.setGrpName(tGrpName);
				tLJSGetEndorseSchema.setHandler(""); //暂时不知道如何处理
				tLJSGetEndorseSchema.setPolType("1");
				tLJSGetEndorseSchema.setGetFlag("0");
				tLJSGetEndorseSchema.setSerialNo(""); //暂时不知道如何处理
				tLJSGetEndorseSchema.setCurrency(tCurrency);
				tLJSGetEndorseSchema.setOperator(this.mGlobalInput.Operator);
				tLJSGetEndorseSchema.setMakeDate(strCurrentDate);
				tLJSGetEndorseSchema.setMakeTime(strCurrentTime);
				tLJSGetEndorseSchema.setModifyDate(strCurrentDate);
				tLJSGetEndorseSchema.setModifyTime(strCurrentTime);
				tLJSGetEndorseSchema.setSubFeeOperationType(BqCode.Pay_LoanCorpusInterest);
				tLJSGetEndorseSet.add(tLJSGetEndorseSchema);    


			}
	          //营改增 add zhangyingfeng 2016-07-13
	          //价税分离 计算器
	          TaxCalculator.calBySchemaSet(tLJSGetEndorseSet);
	          //end zhangyingfeng 2016-07-13
			mMap.put(tLJSGetEndorseSet, "DELETE&INSERT");



			// 修改“个险保全项目表”相应信息
			mLPEdorItemSchema.setEdorState("3");
			mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
			mLPEdorItemSchema.setModifyDate(strCurrentDate);
			mLPEdorItemSchema.setModifyTime(strCurrentTime);
			mLPEdorItemSchema.setGetMoney(dSumMoney);
			//本次还款
			
			

			String tSLoan = "SELECT leavemoney,newloandate,Currency"
				+ " FROM loloan a  WHERE ContNo='?ContNo?' and PayOffFlag='0' and  loantype='0'";

			SQLwithBindVariables sbv7=new SQLwithBindVariables();
			sbv7.sql(tSLoan);
			sbv7.put("ContNo", this.mLPEdorItemSchema.getContNo());
			SSRS rSSRS = new SSRS();
			rSSRS = tExeSQL.execSQL(sbv7);
			if (rSSRS == null || rSSRS.MaxRow == 0) {
				// 没有险种信息
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorRFDetailBL";
				tError.functionName = "getArrayForFormatPage";
				tError.errorMessage = "没有查询到险种项目信息！";
				this.mErrors.addOneError(tError);
				return false;
			}
			Map sCurReturnMap = new HashMap();
			Map sCurIntrestMap = new HashMap();
			double dSumShouldReturn=0;
			double dSumShouldIntrest=0;
			for(int k=0;k<rSSRS.MaxRow;k++)
			{
				double dShouldReturn = 0;
				double tShouldIntrest = 0;
				String tCurrency = rSSRS.GetText(k+1, 3);
				if(sCurReturnMap.containsKey(tCurrency)){
					dShouldReturn = (Double)sCurReturnMap.get(tCurrency);
					tShouldIntrest = (Double)sCurIntrestMap.get(tCurrency);
				}
				double tRate = AccountManage.calMultiRateMS(rSSRS.GetText(k+1, 2),  mLPEdorItemSchema.getEdorAppDate(), "000000","00","L","C","Y",tCurrency);
				if (tRate+1==0) {
					CError tError = new CError();
					tError.moduleName = "PEdorRFDetailBL";
					tError.functionName = "getArrayForFormatPage";
					tError.errorMessage = "没有查询到险种项目信息！";
					this.mErrors.addOneError(tError);
					return false;
				}
				//累计计息
				double rIntrest=Double.parseDouble(rSSRS.GetText(k+1, 1))*tRate;	
				tShouldIntrest+=PubFun.round(rIntrest, 2);	
				dShouldReturn+=Double.parseDouble(rSSRS.GetText(k+1, 1));
				sCurReturnMap.put(tCurrency, dShouldReturn);
				sCurIntrestMap.put(tCurrency, tShouldIntrest);
				dSumShouldReturn+=dShouldReturn;
				dSumShouldIntrest+=tShouldIntrest;
			}
			
			boolean isPayoff = true;
			if(curReturnMap.keySet().size() == sCurReturnMap.keySet().size()){
				Iterator cit = curReturnMap.keySet().iterator();
				while(cit.hasNext()){
					String tCurrency = (String)cit.next();
					double thisReturn = (Double)curReturnMap.get(tCurrency);//本次还的本息
					double shouldReturn = (Double)sCurReturnMap.get(tCurrency);//应该还的本金
					double shouldIntrest =  (Double)sCurIntrestMap.get(tCurrency);//应该还的利息
					if((Math.abs(shouldReturn+shouldIntrest-thisReturn)>0.01)){
						isPayoff = false;
					}
				}
			}
			// 只有所有借款都还清才修改保单状态为未贷款状态
            //不再从前台传入，前台可能有问题
			if (isPayoff){
				if (!changeContState(mLPEdorItemSchema.getContNo(), "Loan",
						"0", mLPEdorItemSchema.getEdorAppDate(), 
								"000000")) {
					return false;
				}
			}
			
			//add by jiaqiangli 2009-04-24 增加校验以防止主键冲突
			if (mLPContStateSet.size() >= 2) {
				if (mLPContStateSet.get(1).getStartDate() != null && mLPContStateSet.get(2).getStartDate() != null) {
					//这里只是简单地判断startdate是否相同，默认主键中的其他字段都置成相同
					if (mLPContStateSet.get(1).getStartDate().equals(mLPContStateSet.get(2).getStartDate())) {
						CError.buildErr(this, "当天借款当天还清的保全操作不允许，反之亦然！");
						return false;
					}
				}
			}
			//add by jiaqiangli 2009-04-24 增加校验以防止主键冲突
			
			//上次本金
			//begin zbx 20110517
			//mLPEdorItemSchema.setStandbyFlag1("0.0");
			dSumShouldReturn=PubFun.setPrecision(dSumShouldReturn, "0.00");
			mLPEdorItemSchema.setStandbyFlag1(String.valueOf(dSumShouldReturn));
			//上次利息
			//mLPEdorItemSchema.setStandbyFlag2("0.0");
			dSumShouldIntrest=PubFun.setPrecision(dSumShouldIntrest, "0.00");
			mLPEdorItemSchema.setStandbyFlag2(String.valueOf(dSumShouldIntrest));
			//保存是否还清的标记
			mLPEdorItemSchema.setStandbyFlag3(tPayOffFlag);
			mMap.put(tLPReturnLoanSet, "DELETE&INSERT");
			mMap.put(mLPEdorItemSchema, "UPDATE");
			mMap.put(mLPContStateSet, "DELETE&INSERT");

			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorRFDetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误！" + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 获得界面显示的数据 包括：贷款本金，贷款起期，初始利率，初始利息，逾期期间利率，逾期期间利息
	 * 
	 * @return true--Success,false--Fail
	 */
	private boolean getInterfaceData() {

		ExeSQL tExeSQL = new ExeSQL();
		String tSLoan = "SELECT "
			+ " EdorNo,LoanDate,PayOffDate,leavemoney,"
			+ " case when SpecifyRate='1' then '固定复利' when SpecifyRate='2'  then '浮动复利' end,polno,OrderNo,newloandate,currency"
			+ " FROM loloan a" + " WHERE ContNo='?ContNo?' and PayOffFlag='0' and  loantype='0'";

		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSLoan);
		sqlbv.put("ContNo", this.mLPEdorItemSchema.getContNo());
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.MaxRow == 0) {
			// 没有险种信息
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorRFDetailBL";
			tError.functionName = "getArrayForFormatPage";
			tError.errorMessage = "没有查询到险种项目信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		String rStrArray[][]= new String[tSSRS.MaxRow][9];;
		for(int k=0;k<tSSRS.MaxRow;k++)
		{
			
			// 上次借款的批单号
			rStrArray[k][0] =tSSRS.GetText(k+1, 1);
			// 借款日期
			rStrArray[k][1] =tSSRS.GetText(k+1, 2);
			// 最大还款日期
			rStrArray[k][2] =PubFun.calDate(tSSRS.GetText(k+1, 2) , 6, "M", "");

			double tRate = AccountManage.calMultiRateMS(tSSRS.GetText(k+1, 8),  mLPEdorItemSchema.getEdorAppDate(), "000000","00","L","C","Y",tSSRS.GetText(k+1, 9));
			if (tRate+1==0) {
				CError tError = new CError();
				tError.moduleName = "PEdorRFDetailBL";
				tError.functionName = "getArrayForFormatPage";
				tError.errorMessage = "没有查询到险种项目信息！";
				this.mErrors.addOneError(tError);
				return false;
			}
			//累计计息
			double tIntrest=Double.parseDouble(tSSRS.GetText(k+1, 4))*tRate;

			tIntrest=PubFun.round(tIntrest, 2);
			// 截止还款日期的本金
			rStrArray[k][3] =tSSRS.GetText(k+1, 4) ;
			// 截止还款日期的利息
			rStrArray[k][4] = String.valueOf(tIntrest);
			rStrArray[k][5] =tSSRS.GetText(k+1, 5) ; 
			rStrArray[k][6] ="";		
			rStrArray[k][7] =tSSRS.GetText(k+1, 6);	
			rStrArray[k][8] =tSSRS.GetText(k+1, 9);			
		}

		mResult.clear();		
		// 数组
		mResult.add(rStrArray);
		return true;
	}	
	/**
	 * 改变保单的状态（注：是险种级状态）
	 * 
	 * @param tContNo
	 * @param tStateType
	 * @param tState
	 * @param tNewStateDate
	 * @return boolean true--成功，false--失败。结果放在mLPContStateSet变量中（累计）
	 * Terminate: 01 满期终止 02 退保终止 03 解约终止 04 理赔终止 05 失效终止 06 犹腿终止 07  其他终止
	 * @param tPolNo
	 *            由保单层落实到险种层，主险状态
	 */
	private boolean changeContState(String tContNo, String tStateType,
			String tState, String tNewStateDate, String tPolNo) {
		try {
			// 当前日期时间
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			LCContStateSchema tLCContStateSchema = null;
			LPContStateSchema tLPContStateSchema = null;
			// 先查询当前状态是否是要改变的状态，如果是，则保持
			String tSql = "SELECT *" + " FROM LCContState" + " WHERE ContNo='?tContNo?'" + " and StateType='?tStateType?' and PolNo='?tPolNo?'" + " and State='?tState?'" + " and EndDate is null";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSql);
			sqlbv.put("tContNo", tContNo);
			sqlbv.put("tStateType", tStateType);
			sqlbv.put("tPolNo", tPolNo);
			sqlbv.put("tState", tState);
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				// 现在的状态就是要改变后的状态，所以，保持
				return true;
			}

			// 按张容所说，如果新旧状态主键重复（指在C表中重复），则直接用新状态把旧状态替掉。2005-09-07日修改。
			LCContStateDB tLCContStateDB = new LCContStateDB();
			tLCContStateDB.setContNo(tContNo);
			tLCContStateDB.setPolNo(tPolNo);
			tLCContStateDB.setStateType(tStateType);
			tLCContStateDB.setStartDate(tNewStateDate);
			if (!tLCContStateDB.getInfo()) {
				// 查询现在状态，将此状态结束
				tSql = "SELECT *" + " FROM LCContState" + " WHERE ContNo='?tContNo?'" + " and StateType='?tStateType?'"
						+ " and PolNo='?tPolNo?'"
						+ " and EndDate is null";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("tContNo", tContNo);
				sqlbv.put("tStateType", tStateType);
				sqlbv.put("tPolNo", tPolNo);
				tLCContStateDB = new LCContStateDB();
				LCContStateSet tLCContStateSet = new LCContStateSet();
				tLCContStateSet = tLCContStateDB.executeQuery(sqlbv);
				if (tLCContStateSet != null && tLCContStateSet.size() > 0) {
					tLCContStateSchema = new LCContStateSchema();
					tLCContStateSchema = tLCContStateSet.get(1);
					tLCContStateSchema.setEndDate(PubFun.calDate(tNewStateDate,
							-1, "D", null)); // 状态在前一天结束
					tLCContStateSchema.setOperator(mGlobalInput.Operator);
					tLCContStateSchema.setModifyDate(tCurrentDate);
					tLCContStateSchema.setModifyTime(tCurrentTime);
					tLPContStateSchema = new LPContStateSchema();
					this.mReflections.transFields(tLPContStateSchema,
							tLCContStateSchema);
					tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
					tLPContStateSchema.setEdorType(mLPEdorItemSchema
							.getEdorType());
					mLPContStateSet.add(tLPContStateSchema);
				}
			}
			// 新状态信息
			tLPContStateSchema = new LPContStateSchema();
			tLPContStateSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPContStateSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPContStateSchema.setContNo(tContNo);
			tLPContStateSchema.setInsuredNo("000000");
			tLPContStateSchema.setPolNo(tPolNo);
			tLPContStateSchema.setStateType(tStateType);
			tLPContStateSchema.setState(tState);
			tLPContStateSchema.setStartDate(tNewStateDate);
			tLPContStateSchema.setOperator(mGlobalInput.Operator);
			tLPContStateSchema.setMakeDate(tCurrentDate);
			tLPContStateSchema.setMakeTime(tCurrentTime);
			tLPContStateSchema.setModifyDate(tCurrentDate);
			tLPContStateSchema.setModifyTime(tCurrentTime);
			mLPContStateSet.add(tLPContStateSchema);

			return true;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PEdorRFDetailBL";
			tError.functionName = "changeContState";
			tError.errorMessage = "修改保单状态时产生错误！保单号：" + tContNo;
			this.mErrors.addOneError(tError);
			return false;
		}
	}
}
