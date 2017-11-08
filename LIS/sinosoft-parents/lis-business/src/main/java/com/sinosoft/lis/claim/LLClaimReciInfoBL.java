package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 录入收件人信息
 * </p>
 * <p>
 * Description: 收件人信息录入完毕后,执行"保存"操作
 * </p>
 * 更新llregister表中有关数据
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author niuzj,2005-10-25
 * @version 1.0
 */

public class LLClaimReciInfoBL {
private static Logger logger = Logger.getLogger(LLClaimReciInfoBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private VData mIputData = new VData();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mOperate;
	private String mClmNo; // 赔案号
	private String mReciCode; // 收件人代码
	private String mReciName; // 收件人姓名
	private String mRelation; // 收件人与出险人关系
	private String mReciAddress; // 收件人地址
	private String mReciPhone; // 收件人电话
	private String mReciMobile; // 收件人手机
	private String mReciZip; // 收件人邮编
	private String mReciSex; // 收件人性别
	private String mReciEmail; // 收件人Email
	private String mRemark; // 收件人细节

	public LLClaimReciInfoBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(mInputData, mOperate)) {
			return false;
		}

		// 校验外部传入的数据
		if (!checkData()) {
			return false;
		}

		logger.debug("---LLClaimReciInfoBL dealData Start---");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("---LLClaimReciInfoBL dealData End---");

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LLClaimReciInfoBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;

		return true;
	}

	/**
	 * dealData 处理数据
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
		LLRegisterDB tLLRegisterDB = new LLRegisterDB();
		tLLRegisterDB.setRgtNo(mClmNo); // 根据赔案号找到这条记录
		// tLLRegisterDB.setRecipients(mReciCode);
		// tLLRegisterDB.setReciName(mReciName);
		// tLLRegisterDB.setRelation(mRelation);
		// tLLRegisterDB.setReciAddress(mReciAddress);
		// tLLRegisterDB.setReciDetails(mRemark);

		tLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
		if (tLLRegisterSchema.getRgtNo() == null
				&& tLLRegisterSchema.getRgtNo().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimReciInfoBL";
			tError.functionName = "dealData";
			tError.errorMessage = "没有此赔案号对应的记录!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 如果有对应的赔案号,则可以更新收件人信息
		// tLLRegisterSchema.setRecipients(mReciCode);
		// tLLRegisterSchema.setReciName(mReciName);
		// tLLRegisterSchema.setRelation(mRelation);
		// tLLRegisterSchema.setReciAddress(mReciAddress);
		// tLLRegisterSchema.setReciDetails(mRemark);

		// map.put(tLLRegisterSchema, "UPDATE");
		// 如果用上面map语句,则拼出来的SQL语句为:update llregister set() values()形式.

		String strSql = " update llregister a set a.recipients='" + "?recicode?"
				+ "', " + " a.reciname='" + "?reciname?" + "', " + " a.recirela='"
				+ "?recirela?" + "', " + " a.reciaddress='" + "?reciaddress?" + "', "
				+ " a.recidetails='" + "?recidetails?" + "', " + " a.reciphone='"
				+ "?reciphone?" + "', " + " a.recimobile='" + "?recimobile?" + "', "
				+ " a.recizip='" + "?recizip?" + "', " + " a.recisex='" + "?recisex?"
				+ "', " + " a.reciemail='" + "?reciemail?" + "' "
				+ " where a.rgtno='" + "?rgtno?" + "' ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("recicode", mReciCode);
		sqlbv.put("reciname", mReciName);
		sqlbv.put("recirela", mRelation);
		sqlbv.put("reciaddress", mReciAddress);
		sqlbv.put("recidetails", mRemark);
		sqlbv.put("reciphone", mReciPhone);
		sqlbv.put("recimobile", mReciMobile);
		sqlbv.put("recizip", mReciZip);
		sqlbv.put("recisex", mReciSex);
		sqlbv.put("reciemail", mReciEmail);
		sqlbv.put("rgtno", mClmNo);
		map.put(sqlbv, "UPDATE");

		return true;
	}

	/**
	 * checkData
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * prepareOutputData
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimReciInfoBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * getInputData 得到前台传输的数据
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {

		// 获得全局公共数据
		// mGlobalInput.setSchema((GlobalInput)
		// cInputData.getObjectByObjectName("GlobalInput", 0));
		// if (mGlobalInput == null)
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCContDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "LLClaimReciInfoBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输全局公共数据失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// //获得操作员编码
		// mOperator = mGlobalInput.Operator;
		// if (mOperator == null || mOperator.trim().equals(""))
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCContDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "LLClaimReciInfoBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输全局公共数据Operate失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }
		//
		// mManageCom = mGlobalInput.Operator;
		// if (mManageCom == null || mManageCom.trim().equals(""))
		// {
		// // @@错误处理
		// //this.mErrors.copyAllErrors( tLCContDB.mErrors );
		// CError tError = new CError();
		// tError.moduleName = "LLClaimReciInfoBL";
		// tError.functionName = "getInputData";
		// tError.errorMessage = "前台传输全局公共数据mManageCom失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		// 从输入数据中得到所有对象
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		// 赔案号不能为空
		mClmNo = (String) mTransferData.getValueByName("ClmNo");
		if (mClmNo == null || mClmNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LLClaimReciInfoBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输赔案号数据ClmNo失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 收件人信息都可以为空
		mReciCode = (String) mTransferData.getValueByName("ReciCode");
		if (mReciCode == null || mReciCode.trim().equals("")) {
			mReciCode = "";
		}

		mReciName = (String) mTransferData.getValueByName("ReciName");
		if (mReciName == null || mReciName.trim().equals("")) {
			mReciName = "";
		}

		mRelation = (String) mTransferData.getValueByName("Relation");
		if (mRelation == null || mRelation.trim().equals("")) {
			mRelation = "";
		}

		mReciAddress = (String) mTransferData.getValueByName("ReciAddress");
		if (mReciAddress == null || mReciAddress.trim().equals("")) {
			mReciAddress = "";
		}

		mReciPhone = (String) mTransferData.getValueByName("ReciPhone");
		if (mReciPhone == null || mReciPhone.trim().equals("")) {
			mReciPhone = "";
		}

		mReciMobile = (String) mTransferData.getValueByName("ReciMobile");
		if (mReciMobile == null || mReciMobile.trim().equals("")) {
			mReciMobile = "";
		}

		mReciZip = (String) mTransferData.getValueByName("ReciZip");
		if (mReciZip == null || mReciZip.trim().equals("")) {
			mReciZip = "";
		}

		mReciSex = (String) mTransferData.getValueByName("ReciSex");
		if (mReciSex == null || mReciSex.trim().equals("")) {
			mReciSex = "";
		}

		mReciEmail = (String) mTransferData.getValueByName("ReciEmail");
		if (mReciEmail == null || mReciEmail.trim().equals("")) {
			mReciEmail = "";
		}

		mRemark = (String) mTransferData.getValueByName("Remark");
		if (mRemark == null || mRemark.trim().equals("")) {
			mRemark = "";
		}

		return true;
	}

}
