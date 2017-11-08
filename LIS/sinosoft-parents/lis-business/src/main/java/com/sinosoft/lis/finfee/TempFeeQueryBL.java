/******************************************
 * 由于本表数据太大,常常造成内存溢出,故限制记录条数为2000
 * Yang Yalin 2005/11/08
 ******************************************/

package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 暂收费查询逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 */
public class TempFeeQueryBL {
private static Logger logger = Logger.getLogger(TempFeeQueryBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	/** 暂交费表 */
	private LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
	private LJTempFeeSchema mLJTempFeeSchema = new LJTempFeeSchema();
	private LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
	private LJTempFeeClassSchema mLJTempFeeClassSchema = new LJTempFeeClassSchema();
	private String mStartDate;
	private String mEndDate;
	private String mTempFeeStatus;
	private String mAgentCode;
	private String mPrtNo;
	private String mMaxMoney;
	private String mMinMoney;
	private String mEnterAccStartDate;
	private String mEnterAccEndDate;
	private String mManageCom;
	// private String mChequeNo;
	// private String mChequeDate;

	private GlobalInput mGlobalInput = new GlobalInput();

	public TempFeeQueryBL() {
	}

	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "001";
		// VData tVData = new VData();
		// LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
		// LJTempFeeClassSchema tLJTempFeeClassSchema = new
		// LJTempFeeClassSchema();
		// tVData.addElement(tG);
		// String StartDate = "";
		// String EndDate = "";
		// String TempFeeStatus = "";
		// String ChequeNo = "";
		// String AgentCode = "";
		// tLJTempFeeSchema = new LJTempFeeSchema();
		// tLJTempFeeSchema.setTempFeeNo("8000000045");
		// tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		// tVData.add(tLJTempFeeSchema);
		// //tVData.add(tLJTempFeeClassSchema);
		// tVData.addElement(StartDate);
		// tVData.addElement(EndDate);
		// tVData.addElement(TempFeeStatus);
		// tVData.addElement(AgentCode);
		// //tVData.addElement(ChequeNo);
		//
		//
		// TempFeeQueryUI tTempFeeQueryUI = new TempFeeQueryUI();
		// tTempFeeQueryUI.submitData(tVData, "QUERYUpd");
//		LJTempFeeClassSet mLJTempFeeClassSet;
		LJTempFeeClassSchema tLJTempFeeClassSchema;
		VData tVData = new VData();

		tVData.addElement(tG);
		tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		// tLJTempFeeClassSchema.setPayMode(PayMode);
		// tLJTempFeeClassSchema.setChequeNo(ChequeNo);
		// tLJTempFeeClassSchema.setChequeDate(ChequeDate);
		// tLJTempFeeClassSchema.setOperator(Operator);
		// tLJTempFeeClassSchema.setTempFeeNo(TempFeeNo);
		// tLJTempFeeClassSchema.set
		//
		// logger.debug("managecom=="+tManageCom );
		// logger.debug("tMaxMoney=="+tMaxMoney );
		// logger.debug("MinMoney=="+tMinMoney );

		tVData.add(tLJTempFeeClassSchema);
		tVData.addElement("");
		tVData.addElement("");
		tVData.addElement("");
		tVData.addElement("");
		tVData.addElement("");
		tVData.addElement("");
		tVData.addElement("");
		tVData.addElement("863200");
		// tVData.addElement(tTransferData);
		TempFeeQueryBL tTempFeeQueryBL = new TempFeeQueryBL();
		if (!tTempFeeQueryBL.submitData(tVData, "CHEQUE||QUERY")) {
			logger.debug("出错了!!!!!!!!!!!!!!!!!!!!!!");
		}

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;

		logger.debug("---getInputData---");
//		logger.debug("operate:" + mOperate);

		// 进行业务处理
		if (mOperate.equals("QUERY")) {
			if (!queryLJTempFee())
				return false;
			logger.debug("---queryLJTempFee---");
		}
		if (mOperate.equals("QUERYUpd")) {
			if (!queryLJTempFeeForUp())
				return false;
			logger.debug("---queryLJTempFee---");
		}
		if (mOperate.equals("CHEQUE||QUERY")) {
			if (!queryLJTempFeeClass())
				return false;
			logger.debug("---queryLJTempFeeClass---");
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 检验查询条件
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		TransferData tTransferData = new TransferData();
		tTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		if (tTransferData == null) 
		{
			CError.buildErr(this, "数据传输失败!");
			mLJTempFeeSet.clear();
			return false;
		}
		mMaxMoney = (String) tTransferData.getValueByName("MaxMoney");
		mMinMoney = (String) tTransferData.getValueByName("MinMoney");
		mEnterAccStartDate = (String) tTransferData.getValueByName("EnterAccStartDate");
		mEnterAccEndDate = (String) tTransferData.getValueByName("EnterAccEndDate");
		mManageCom = (String) tTransferData.getValueByName("ManageCom");
		
		if (this.mOperate.equals("QUERY") || this.mOperate.equals("QUERYUpd")) 
		{
			mLJTempFeeSchema.setSchema((LJTempFeeSchema) cInputData.getObjectByObjectName("LJTempFeeSchema", 0));
			if (this.mOperate.equals("QUERY")) 
			{
				// mStartDate = (String) cInputData.get(2);
				// mEndDate = (String) cInputData.get(3);
				// mTempFeeStatus = (String) cInputData.get(4);
				mStartDate = (String) tTransferData.getValueByName("StartDate");
				mEndDate = (String) tTransferData.getValueByName("EndDate");
				mTempFeeStatus = (String) tTransferData.getValueByName("TempFeeStatus");

			}
		}
		if (this.mOperate.equals("CHEQUE||QUERY")) 
		{
			mLJTempFeeClassSchema.setSchema((LJTempFeeClassSchema) cInputData.getObjectByObjectName("LJTempFeeClassSchema", 0));
			mStartDate = (String) cInputData.get(2);
			mEndDate = (String) cInputData.get(3);
			mTempFeeStatus = (String) cInputData.get(4);
			mPrtNo = (String) cInputData.get(5);
			mAgentCode = (String) cInputData.get(6);
			mEnterAccStartDate = (String) cInputData.get(7);
			mEnterAccEndDate = (String) cInputData.get(8);
			mManageCom = (String) cInputData.get(9);
		}
		if (mTempFeeStatus == null || mTempFeeStatus.length() == 0)
			mTempFeeStatus = "6";
		logger.debug("mTempFeeStatus:" + mTempFeeStatus);
		logger.debug("从前台获得的到帐日期从" + mEnterAccStartDate + "至"+ mEnterAccEndDate);
		return true;
	}

	/**
	 * 查询暂交费表信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean queryLJTempFee() {
		// 保单信息
		logger.debug("mTempFeeStatus:" + mTempFeeStatus);
		Integer iTemp = new Integer(mTempFeeStatus);
		int iTempFeeStatus = iTemp.intValue();
		String tSql = "select * from LJTempFee where 1=1 ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		if (mManageCom != null && mManageCom.length() != 0) {
			tSql = tSql + " and ManageCom like concat('?mManageCom?','%')";
			sqlbv.put("mManageCom", mManageCom);
		}
		if (mMinMoney != null && mMinMoney.length() != 0) {
			tSql = tSql + " and paymoney >=?mMinMoney?";
			sqlbv.put("mMinMoney", mMinMoney);
		}
		if (mMaxMoney != null && mMaxMoney.length() != 0) {
			tSql = tSql + " and paymoney <=?mMaxMoney?";
			sqlbv.put("mMaxMoney", mMaxMoney);
		}
		switch (iTempFeeStatus) {
		case 0:
			tSql = tSql + " and EnterAccDate is null ";
			break;
		case 1:
			tSql = tSql + " and ConfFlag='0' ";
			break;
		case 2:
			tSql = tSql + " and ConfFlag='1' ";
			break;
		case 3:
//			tSql = tSql
//					+ " and (tempfeeno,riskcode) in(select tempfeeno,riskcode tempfeeno from LJAGetTempFee)";
			tSql = tSql +"and exists (select 1 from ljagettempfee where tempfeeno=ljtempfee.tempfeeno) ";
			break;
		case 4:
			tSql = tSql + " and ConfFlag='0' and TempFeeType in('1')";
			break;
		case 5:
			tSql = tSql + " and ConfFlag='0' and TempFeeType not in('1')";
			break;
		case 6:
			break;
		}

		if (mStartDate != null && mStartDate.length() != 0){
			tSql = tSql + " and MakeDate >='?mStartDate?' ";
			sqlbv.put("mStartDate", mStartDate);
		}

		if (mEndDate != null && mEndDate.length() != 0){
			tSql = tSql + " and MakeDate <='?mEndDate?' ";
			sqlbv.put("mEndDate", mEndDate);
		}

		if (mLJTempFeeSchema.getRiskCode() != null&& mLJTempFeeSchema.getRiskCode().length() != 0){
			tSql = tSql + " and RiskCode ='?RiskCode?' ";
			sqlbv.put("RiskCode", mLJTempFeeSchema.getRiskCode());
		}

		if (mLJTempFeeSchema.getOperator() != null&& mLJTempFeeSchema.getOperator().length() != 0){
			tSql = tSql + " and Operator ='?Operator?' ";
		    sqlbv.put("Operator", mLJTempFeeSchema.getOperator()); 
		}

		if ((mLJTempFeeSchema.getOtherNo() == null || mLJTempFeeSchema.getOtherNo().length() == 0)
				&&(mLJTempFeeSchema.getTempFeeNo() == null || mLJTempFeeSchema.getTempFeeNo().length() == 0)
				&& mOperate.equals("QUERYUpd")) 
		{
			CError.buildErr(this, "请录入暂收据号或印刷号");
			mLJTempFeeSet.clear();
			return false;
		}

		else 
		{
			if (mLJTempFeeSchema.getTempFeeNo() != null&& mLJTempFeeSchema.getTempFeeNo().length() != 0) 
			{
				tSql = tSql + " and tempfeeno='?tempfeeno?' ";
				sqlbv.put("tempfeeno", mLJTempFeeSchema.getTempFeeNo());			}
			if (mLJTempFeeSchema.getOtherNo() != null&& mLJTempFeeSchema.getOtherNo().length() != 0) 
			{
				tSql = tSql + " and OtherNo='?OtherNo?'";
				sqlbv.put("OtherNo", mLJTempFeeSchema.getOtherNo());
			}

		}

		if (mLJTempFeeSchema.getAgentCode() != null&& mLJTempFeeSchema.getAgentCode().length() != 0){
			tSql = tSql + " and AgentCode='?AgentCode?' ";
			sqlbv.put("AgentCode", mLJTempFeeSchema.getAgentCode());
		}
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
		tSql = tSql + " and ManageCom like concat('?ManageCom?','%') and rownum<=2000";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tSql = tSql + " and ManageCom like concat('?ManageCom?','%') limit 0,2000";	
		}
		sqlbv.put("ManageCom", mGlobalInput.ManageCom);
		sqlbv.sql(tSql);

		logger.debug(tSql);

		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		mLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv);

		if (tLJTempFeeDB.mErrors.needDealError() == true) {
			// @@错误处理
			CError.buildErr(this, "暂交费查询失败!");
			mLJTempFeeSet.clear();
			return false;
		}
		if (mLJTempFeeSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "未找到相关数据!");
			mLJTempFeeSet.clear();
			return false;
		}
		if (this.mOperate.equals("QUERY")) {
			for (int i = 1; i <= mLJTempFeeSet.size(); i++) {
				LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
				tLJTempFeeSchema.setSchema(mLJTempFeeSet.get(i));
				if (iTempFeeStatus == 3)
					tLJTempFeeSchema.setState("已退费");
				else if (tLJTempFeeSchema.getConfMakeDate() == null)
					tLJTempFeeSchema.setState("未到账");
				else if (tLJTempFeeSchema.getConfFlag().equals("0") && tLJTempFeeSchema.getConfMakeDate()!=null)
					tLJTempFeeSchema.setState("到账未核销");
				else if(tLJTempFeeSchema.getConfFlag().equals("1"))
					tLJTempFeeSchema.setState("已核销");
				else
					tLJTempFeeSchema.setState("");
				mLJTempFeeSet.set(i, tLJTempFeeSchema);
			}
		}

		logger.debug("LJTempFee num=" + mLJTempFeeSet.size());
		mResult.clear();
		mResult.add(mLJTempFeeSet);

		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();
		if (this.mOperate.equals("QUERYUpd")) {
			LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
			tLJTempFeeClassDB.setTempFeeNo(mLJTempFeeSchema.getTempFeeNo());
			tLJTempFeeClassSet = tLJTempFeeClassDB.query();
			mResult.add(tLJTempFeeClassSet);
		}
		return true;
	}

	private boolean queryLJTempFeeForUp() {

		// 保单信息
		String temfeeno = "";
		//取消对新单的限制 zy
		String tSql = "select * from LJTempFee where 1=1 ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();

		if (mLJTempFeeSchema.getTempFeeNo() != null && mLJTempFeeSchema.getTempFeeNo().length() != 0) 
		{
			tSql = tSql + " and tempfeeno='?tempfeeno?' ";
			sqlbv1.put("tempfeeno", mLJTempFeeSchema.getTempFeeNo());
//			temfeeno = mLJTempFeeSchema.getTempFeeNo();
//			logger.debug("测试＝＝＝＝＝" + temfeeno);

		}

		if (mLJTempFeeSchema.getOtherNo() != null&& mLJTempFeeSchema.getOtherNo().length() != 0) 
		{
			tSql = tSql + " and OtherNo='?OtherNo?'";
			sqlbv1.put("OtherNo", mLJTempFeeSchema.getOtherNo());
		}
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
		tSql = tSql + " and ManageCom like concat('?ManageCom?','%') and rownum<=2000 order by riskcode";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tSql = tSql + " and ManageCom like concat('?ManageCom?','%') order by riskcode limit 0,2000";
		}
		sqlbv1.put("ManageCom", mGlobalInput.ManageCom);
		sqlbv1.sql(tSql);
		logger.debug(tSql);// 查询的SQL

		LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
		mLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv1);

		if (tLJTempFeeDB.mErrors.needDealError() == true) {
			// @@错误处理
			CError.buildErr(this,  "暂交费查询失败!");
			mLJTempFeeSet.clear();
			return false;
		}
		// ////////////准备结果集合、、、、yaory
		if (mLJTempFeeSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this,  "未找到相关数据!");
			mLJTempFeeSet.clear();
			return false;
		}
//		logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
		temfeeno = mLJTempFeeSet.get(1).getTempFeeNo();
		logger.debug("******************************************"+ temfeeno);
//		logger.debug("LJTempFee num=" + mLJTempFeeSet.size());
		mResult.clear();
		mResult.add(mLJTempFeeSet);

		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();

		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		tLJTempFeeClassDB.setTempFeeNo(temfeeno);// 需要修改
		tLJTempFeeClassSet = tLJTempFeeClassDB.query();
		mResult.add(tLJTempFeeClassSet);

		return true;

	}

	/**
	 * 查询暂交费分类表信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean queryLJTempFeeClass() {
		// 保单信息
		int iTempFeeStatus = 0;
		if (mTempFeeStatus != null && mTempFeeStatus.length() != 0) {
			logger.debug("mTempFeeStatus:" + mTempFeeStatus);
			Integer iTemp = new Integer(mTempFeeStatus);
			iTempFeeStatus = iTemp.intValue();
		}

		String tSql = "select * from LJTempFeeClass where 1=1 ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		if (mManageCom != null && mManageCom.length() != 0) {
			tSql = tSql + " and ManageCom like concat('?mManageCom?','%')";
			sqlbv2.put("mManageCom", mManageCom);
		}
		if (mMinMoney != null && mMinMoney.length() != 0) {
			tSql = tSql + " and paymoney >='?mMinMoney?'";
			sqlbv2.put("mMinMoney", mMinMoney);
		}
		if (mMaxMoney != null && mMaxMoney.length() != 0) {
			tSql = tSql + " and paymoney <='?mMaxMoney?'";
			sqlbv2.put("mMaxMoney", mMaxMoney);
		}
		if (mEnterAccStartDate != null && mEnterAccStartDate.length() != 0) {
			tSql = tSql + " and enteraccdate >='?mEnterAccStartDate?'";
			sqlbv2.put("mEnterAccStartDate", mEnterAccStartDate);
		}
		if (mEnterAccEndDate != null && mEnterAccEndDate.length() != 0) {
			tSql = tSql + " and enteraccdate <='?mEnterAccEndDate?'";
			sqlbv2.put("mEnterAccEndDate", mEnterAccEndDate);
		}
		switch (iTempFeeStatus) {
		case 0:
			tSql = tSql + " and EnterAccDate is null ";
			break;
		case 1:
			tSql = tSql + " and ConfFlag='0' ";
			break;
		case 2:
			tSql = tSql + " and ConfFlag='1' ";
			break;
		case 3:
			tSql = tSql
					+ " and tempfeeno in(select tempfeeno from LJAGetTempFee)";
			break;
		case 4:
			tSql = tSql + " and ConfFlag='0' and TempFeeType in('1','5')";
			break;
		case 5:
			tSql = tSql + " and ConfFlag='0' and TempFeeType not in('1','5')";
			break;
		case 6:
			break;
		}

		if (mStartDate != null && mStartDate.length() != 0){
			tSql = tSql + " and PayDate >='?mStartDate?' ";
			sqlbv2.put("mStartDate", mStartDate);
		}

		if (mEndDate != null && mEndDate.length() != 0){
			tSql = tSql + " and PayDate <='?mEndDate?' ";
			sqlbv2.put("mEndDate", mEndDate);
		}

		if (mLJTempFeeClassSchema.getOperator() != null
				&& mLJTempFeeClassSchema.getOperator().length() != 0){
			tSql = tSql + " and Operator ='?Operator?' ";
			sqlbv2.put("Operator", mLJTempFeeClassSchema.getOperator());
		}

		if (mLJTempFeeClassSchema.getTempFeeNo() != null
				&& mLJTempFeeClassSchema.getTempFeeNo().length() != 0){
			tSql = tSql + " and tempfeeno='?tempfeeno?' ";
			sqlbv2.put("tempfeeno", mLJTempFeeClassSchema.getTempFeeNo());
		}
		else {
			if (mOperate.equals("QUERYUpd")) {
				CError tError = new CError();
				tError.moduleName = "TempFeeQueryBL";
				tError.functionName = "queryData";
				tError.errorMessage = "请录入暂收据号!";
				this.mErrors.addOneError(tError);
				mLJTempFeeSet.clear();
				return false;
			}
		}
		if (mAgentCode != null && mAgentCode.length() != 0){
			tSql = tSql
					+ " and TempFeeNo in (select tempfeeno from LJTempFee where AgentCode='?mAgentCode?') ";
			sqlbv2.put("mAgentCode", mAgentCode);
		}

		// logger.debug("----prtNo:"+mPrtNo+" AgentCode:"+mAgentCode);
		if (mPrtNo != null && !mPrtNo.trim().equals("")) {
			tSql = tSql
					+ " and TempFeeNo in (select TempFeeNo from LJTempFee where OtherNoType='4' and OtherNo='?mPrtNo?')";
			sqlbv2.put("mPrtNo", mPrtNo);
		}
		if (mLJTempFeeClassSchema.getChequeNo() != null
				&& !mLJTempFeeClassSchema.getChequeNo().trim().equals("")) {
			tSql = tSql + " and ChequeNo='?ChequeNo?'";
			sqlbv2.put("ChequeNo", mLJTempFeeClassSchema.getChequeNo());
		}
		if (mLJTempFeeClassSchema.getPayMode() != null
				&& !mLJTempFeeClassSchema.getPayMode().trim().equals("")) {
			tSql = tSql + " and PayMode='?PayMode?'";
			sqlbv2.put("PayMode", mLJTempFeeClassSchema.getPayMode());
		}
		if (mLJTempFeeClassSchema.getChequeDate() != null
				&& !mLJTempFeeClassSchema.getChequeDate().trim().equals("")){
			tSql = tSql + " and ChequeDate='?ChequeDate?'";
			sqlbv2.put("ChequeDate", mLJTempFeeClassSchema.getChequeDate());
		}

		// tSql = tSql + " and ManageCom like '" + mGlobalInput.ManageCom +
		// "%'";

		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tSql = tSql + " and rownum<=2000 ";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tSql = tSql + " limit 0,2000 ";
		}
		sqlbv2.sql(tSql);

		logger.debug(tSql);

		LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
		mLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sqlbv2);
		// mLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(tSql);

		if (tLJTempFeeClassDB.mErrors.needDealError() == true) {
			// @@错误处理
			CError.buildErr(this, "暂交费查询失败!");
			mLJTempFeeSet.clear();
			return false;
		}
		if (mLJTempFeeClassSet.size() == 0) {
			// @@错误处理
			CError.buildErr(this, "未找到相关数据!");
			mLJTempFeeSet.clear();
			return false;
		}

		logger.debug("LJTempFeeClass num=" + mLJTempFeeClassSet.size());
		mResult.clear();
		mResult.add(mLJTempFeeClassSet);

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
//	private boolean prepareOutputData() {
//		mResult.clear();
//		try {
//			mResult.add(mLJTempFeeSet);
//		} catch (Exception ex) {
//			// @@错误处理
//			CError tError = new CError();
//			tError.moduleName = "TempFeeQueryBL";
//			tError.functionName = "prepareOutputData";
//			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
//		return true;
//	}
}
