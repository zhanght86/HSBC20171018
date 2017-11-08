package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCDelPolLogSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class ScanContDeleteBL {
private static Logger logger = Logger.getLogger(ScanContDeleteBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 传输到后台处理的map */
	private MMap mMap = new MMap();

	private LCDelPolLogSchema mLCDelPolLogSchema = new LCDelPolLogSchema();

	/** 数据操作字符串 */
	private String mOperate;
	private String mOperator;
	private String mManageCom;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	// 保单数据打包字符串
	private String mDelReason = "";
	private String mRemark = "";
	private String mPrtNo = "";
	// private String mDelReasonName = "";

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public ScanContDeleteBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {

		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		if (getInputData() == false) {
			return false;
		}

		if (checkData() == false) {
			return false;
		}

		if (dealData() == false) {
			return false;
		}

		this.prepareOutputData();

		try {
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mResult, mOperate)) {
				// 错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				return false;
			}

			return true;
		} catch (Exception ex) {
			this.mErrors.addOneError("删除失败，原因是：" + ex.toString());
			return false;
		}

	}

	/**
	 * 将UI层传输来得数据根据业务还原成具体的类
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		logger.debug("ScanContDeleteBL->getInputData()");
		// 全局变量实例
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			mErrors.addOneError(new CError("没有得到全局量信息"));
			return false;
		}

		this.mOperator = mGlobalInput.Operator;
		this.mManageCom = mGlobalInput.ManageCom;

		this.mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mPrtNo = (String) mTransferData.getValueByName("PrtNo");
		if (mPrtNo == null || mPrtNo.equals("")) {
			mErrors.addOneError(new CError("没有得到投保单号码数据！"));
			return false;
		}

		mDelReason = (String) mTransferData.getValueByName("DelReason");
		// mDelReasonName = (String)
		// mTransferData.getValueByName("DelReasonName");
		if (mDelReason == null || mDelReason.equals("")) {
			mErrors.addOneError(new CError("没有得到保单删除原因数据！"));
			return false;
		}
		mRemark = (String) mTransferData.getValueByName("Remark");
		if (mRemark == null) {
			mRemark = "";
		}

		return true;
	}

	private boolean checkData() {
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		String tSQL = "select 'X' from lccont where prtno='" + "?mPrtNo?" + "'";
		sqlbv.sql(tSQL);
		sqlbv.put("mPrtNo", mPrtNo);
		ExeSQL tExeSQL = new ExeSQL();
		String tValue = "";
		tValue = tExeSQL.getOneValue(sqlbv);
		if (tValue != null && !"".equals(tValue)) {
			mErrors.addOneError(new CError("此保单已经录入，不能删除！"));
			return false;
		}
		return true;
	}

	private boolean dealData() {
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		// 删除工作流
		sqlbv1.sql("delete from lwmission where missionprop1='" + "?mPrtNo?" + "'");
		sqlbv1.put("mPrtNo", mPrtNo);
		mMap.put(sqlbv1,
				"DELETE");

		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql("delete from ES_DOC_PAGES "
				+ "where DocId in (select DocId from ES_DOC_RELATION "
				+ "where BussNo = '" + "?mPrtNo?" + "')");
		sqlbv2.put("mPrtNo", mPrtNo);
		// 影像信息删除
		mMap.put(sqlbv2, "DELETE");

		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql("delete from ES_DOC_MAIN "
				+ "where DocId in (select DocId from ES_DOC_RELATION "
				+ "where BussNo = '" + "?mPrtNo?" + "')");
		sqlbv3.put("mPrtNo", mPrtNo);
		mMap.put(sqlbv3, "DELETE");
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		sqlbv4.sql("delete from ES_DOC_RELATION where BussNo = '" + "?mPrtNo?" + "'");
		sqlbv4.put("mPrtNo", mPrtNo);
		mMap.put(sqlbv4,
				"DELETE");

		// 添加删除日志信息
		mLCDelPolLogSchema.setOtherNo(mPrtNo);
		mLCDelPolLogSchema.setOtherNoType("11");
		mLCDelPolLogSchema.setPrtNo(mPrtNo);
		mLCDelPolLogSchema.setIsPolFlag("0");
		mLCDelPolLogSchema.setOperator(mOperator);
		mLCDelPolLogSchema.setManageCom(mManageCom);
		mLCDelPolLogSchema.setMakeDate(theCurrentDate);
		mLCDelPolLogSchema.setMakeTime(theCurrentTime);
		mLCDelPolLogSchema.setModifyDate(theCurrentDate);
		mLCDelPolLogSchema.setModifyTime(theCurrentTime);
		mLCDelPolLogSchema.setDelReason(mDelReason);
		mLCDelPolLogSchema.setRemark(mRemark);
		mMap.put(mLCDelPolLogSchema, "INSERT");

		return true;
	}

	/**
	 * 准备数据，重新填充数据容器中的内容
	 */
	private void prepareOutputData() {
		// 记录当前操作员
		mResult.clear();
		mResult.add(mMap);
	}

	/**
	 * 操作结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		ScanContDeleteBL scancontdeletebl = new ScanContDeleteBL();
	}
}
