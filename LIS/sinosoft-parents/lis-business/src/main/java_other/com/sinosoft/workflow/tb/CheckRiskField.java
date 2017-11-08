package com.sinosoft.workflow.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.CheckFieldCom;
import com.sinosoft.lis.pubfun.FieldCarrier;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LMCheckFieldSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LMCheckFieldSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 险种信息整体检验类:投保单录入进行险种整体检验
 * </p>
 * <p>
 * Description: 保单录入、问题件修改在险种录入完毕时，进行整体校验
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author: chenrong
 * @version 1.0
 */

public class CheckRiskField {
private static Logger logger = Logger.getLogger(CheckRiskField.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 保单 */
	private LCContSchema mLCContSchema = new LCContSchema();

	/** 主险号码 */
	private String mRiskCode = "";

	public CheckRiskField() {
	}

	public static void main(String[] args) {
		// 校验保单信息
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo("8632H0001463");
		if (!tLCContDB.getInfo()) {
			logger.debug("保单8632H0001463信息查询失败!");
			return;
		}
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContSchema.setSchema(tLCContDB);
		VData tInputData = new VData();
		tInputData.add(tLCContSchema);
		CheckRiskField tCheckRiskField = new CheckRiskField();
		logger.debug(tCheckRiskField.CheckTBField(tInputData,
				"TBALLINSERT"));
	}

	public boolean CheckTBField(VData mInputData, String operType) {
		if (!getInputData(mInputData))
			return false;
		if (!getMainRiskCode())
			return false;
		return CheckField(operType);
	}

	// 检验输入数据
	private boolean getInputData(VData mInputData) {
		if (mInputData == null) {
			CError tError = new CError();
			tError.moduleName = "CheckRiskField";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输数据为空！";
			this.mErrors.addOneError(tError);
			// logger.debug("前台传输数据为空");
			return false;
		}
		VData tInputData = (VData) mInputData.clone();
		// 参数载体传入
		mLCContSchema = (LCContSchema) tInputData.getObjectByObjectName(
				"LCContSchema", 0);
		if (mLCContSchema == null) {
			CError tError = new CError();
			tError.moduleName = "CheckRiskField";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输保单数据为空！";
			this.mErrors.addOneError(tError);
			// logger.debug("前台传输保单数据为空");
			return false;
		}
		return true;
	}

	// 获取主险编号
	private boolean getMainRiskCode() {
		String strSQL = "";
		strSQL = "select * from lcpol" + " where polno = mainpolno "
				+ " and contno = '" + "?contno?" + "'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(strSQL);
		sqlbv1.put("contno", mLCContSchema.getContNo());
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		// logger.debug(strSQL);
		mRiskCode = "";

		tLCPolSet = tLCPolDB.executeQuery(sqlbv1);
		if (tLCPolSet != null && tLCPolSet.size() > 0) {
			tLCPolSchema = tLCPolSet.get(1);
			mRiskCode = tLCPolSchema.getRiskCode();
		}

		if (mRiskCode.equals("")) {
			CError tError = new CError();
			tError.moduleName = "CheckRiskField";
			tError.functionName = "getMainRiskCode";
			tError.errorMessage = "获取主险号码失败！";
			this.mErrors.addOneError(tError);
			// logger.debug("获取主险号码失败");
			return false;
		}

		return true;
	}

	/**
	 * 校验字段
	 * 
	 * @return
	 */
	private boolean CheckField(String operType) {
		String strMsg = "";
		boolean MsgFlag = false;

		try {
			VData tVData = new VData();
			CheckFieldCom tCheckFieldCom = new CheckFieldCom();

			// logger.debug("FieldCarrier");
			// 计算要素
			FieldCarrier tFieldCarrier = new FieldCarrier();
			tFieldCarrier.setContNo(mLCContSchema.getContNo()); // 合同号码
			logger.debug(mLCContSchema.getContNo());
			tVData.add(tFieldCarrier);
			LMCheckFieldSchema tLMCheckFieldSchema = new LMCheckFieldSchema();
			tLMCheckFieldSchema.setFieldName(operType); // 投保
			tLMCheckFieldSchema.setRiskCode(mRiskCode);
			tVData.add(tLMCheckFieldSchema);
			if (tCheckFieldCom.CheckField(tVData) == false) {
				// logger.debug("CheckField(tVData)--false");
				this.mErrors.copyAllErrors(tCheckFieldCom.mErrors);
				return false;
			} else {
				// logger.debug("CheckField(tVData)--true");
				LMCheckFieldSet mLMCheckFieldSet = tCheckFieldCom
						.GetCheckFieldSet();
				for (int n = 1; n <= mLMCheckFieldSet.size(); n++) {
					LMCheckFieldSchema tField = mLMCheckFieldSet.get(n);
					if ((tField.getReturnValiFlag() != null)
							&& tField.getReturnValiFlag().equals("N")) {
						if ((tField.getMsgFlag() != null)
								&& tField.getMsgFlag().equals("Y")) {
							MsgFlag = true;
							strMsg = strMsg + tField.getMsg() + " ; ";

							break;
						}
					}
				}
				if (MsgFlag == true) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "CheckRiskField";
					tError.functionName = "CheckTBField";
					tError.errorMessage = "数据有误：" + strMsg;
					this.mErrors.addOneError(tError);
					// logger.debug("数据有误");
					return false;
				}
			}
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CheckRiskField";
			tError.functionName = "CheckTBField";
			tError.errorMessage = "发生错误，请检验CheckField模块:" + ex;
			this.mErrors.addOneError(tError);
			// logger.debug("发生错误" + ex.toString());
			return false;
		}
		return true;
	}

}
