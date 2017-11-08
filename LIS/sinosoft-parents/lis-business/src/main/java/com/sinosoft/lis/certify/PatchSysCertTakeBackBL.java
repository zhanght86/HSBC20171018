/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LZSysCertifySchema;
import com.sinosoft.lis.vschema.LZSysCertifySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/*
 * <p>ClassName: PatchSysCertTakeBackBL </p> <p>Description:
 * PatchSysCertTakeBackBL类文件 </p> <p>Copyright: Copyright (c) 2002</p> <p>Company:
 * sinosoft </p> @Database: zhangxing @CreateDate：2005-09-27
 */
public class PatchSysCertTakeBackBL {
private static Logger logger = Logger.getLogger(PatchSysCertTakeBackBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperation;

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput globalInput = new GlobalInput();
	private LZSysCertifySchema mLZSysCertifySchema = new LZSysCertifySchema();
	private LZSysCertifySet mLZSysCertifySet = new LZSysCertifySet();
	private String m_strWhere = "";
	private String mCertifyCode = "";
	/** 错误记录数组 */
	String strErro[] = null;
	int j = 0;// 记录错误的个数

	public PatchSysCertTakeBackBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperation = verifyOperate(cOperate);

		if (mOperation.equals("")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		// 循环调用个单回执的程序SysCertTakeBackBL
		for (int i = 1; i <= mLZSysCertifySet.size(); i++) {
			mLZSysCertifySchema = mLZSysCertifySet.get(i).getSchema();
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(mLZSysCertifySchema.getCertifyNo());
			if (!tLCContDB.getInfo()) {
				mErrors.copyAllErrors(tLCContDB.mErrors);
				buildError("submitData", "查询保单表失败");
			}
			String tOutSendCom = "D" + tLCContDB.getAgentCode();
			String tReceiveCom = "A" + tLCContDB.getManageCom();

			mLZSysCertifySchema.setReceiveCom(tReceiveCom);
			mLZSysCertifySchema.setSendOutCom(tOutSendCom);
			// 准备往后台的数据
			VData vData = new VData();
			vData.add(globalInput);
			vData.add(mLZSysCertifySchema);

			mCertifyCode = mLZSysCertifySchema.getCertifyCode();
			logger.debug("mCertifyCode======" + mCertifyCode);

			if (mCertifyCode.equals("9995") || "9995".equals(mCertifyCode)
					|| mCertifyCode == "9995") {
				ContTakeBackBL mContTakeBackBL = new ContTakeBackBL();
				logger.debug("银代回单======");
				logger.debug("Start ContTakeBackBL Submit...");

				if (!mContTakeBackBL.submitData(vData, mOperation)) {

					mErrors.copyAllErrors(tLCContDB.mErrors);
					buildError("submitData", "单证"
							+ mLZSysCertifySchema.getCertifyNo() + "回收失败");
					strErro = new String[j + 1];
					strErro[j] = mLZSysCertifySchema.getCertifyNo();
					mResult.addElement(strErro[j]);
					j++;

				}

			} else {
				SysCertTakeBackBL sysCertTakeBackBL = new SysCertTakeBackBL();

				logger.debug("Start SysCertTakeBack BL Submit...");

				if (!sysCertTakeBackBL.submitData(vData, mOperation)) {

					mErrors.copyAllErrors(tLCContDB.mErrors);
					buildError("submitData", "单证"
							+ mLZSysCertifySchema.getCertifyNo() + "回收失败");
					strErro = new String[j + 1];
					strErro[j] = mLZSysCertifySchema.getCertifyNo();
					mResult.addElement(strErro[j]);
					j++;

				}

			}

		}
		if (mResult != null && mResult.size() != 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		try {
			globalInput.setSchema((GlobalInput) cInputData
					.getObjectByObjectName("GlobalInput", 0));

			mLZSysCertifySet = (LZSysCertifySet) cInputData
					.getObjectByObjectName("LZSysCertifySet", 0);
			m_strWhere = (String) cInputData.getObjectByObjectName("String", 0);

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("getInputData", "在获取输入信息的时候出错");
			return false;
		}
	}

	public VData getResult() {
		return mResult;
	}

	/*
	 * add by kevin, 2002-09-23
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "SysCertTakeBackUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private String verifyOperate(String szOperate) {
		String szReturn = "";
		String szOperates[] = { "INSERT||MAIN", "DELETE||MAIN", "UPDATE||MAIN",
				"QUERY||MAIN" };

		for (int nIndex = 0; nIndex < szOperates.length; nIndex++) {
			if (szOperate.equals(szOperates[nIndex])) {
				szReturn = szOperate;
			}
		}

		return szReturn;
	}

	public static void main(String[] args) {
		PatchSysCertTakeBackBL tPatchSysCertTakeBackBL = new PatchSysCertTakeBackBL();
		LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();
		LZSysCertifySet tLZSysCertifySet = new LZSysCertifySet();
		tLZSysCertifySchema.setCertifyCode("9995");
		tLZSysCertifySchema.setCertifyNo("000000112838");
		tLZSysCertifySchema.setTakeBackDate("2005-09-28");
		tLZSysCertifySet.add(tLZSysCertifySchema);
		tLZSysCertifySchema.setCertifyCode("9995");
		tLZSysCertifySchema.setCertifyNo("000000116409");
		tLZSysCertifySchema.setTakeBackDate("2005-09-28");
		tLZSysCertifySet.add(tLZSysCertifySchema);
		GlobalInput globalInput = new GlobalInput();
		globalInput.ComCode = "86";
		globalInput.ManageCom = "86";
		globalInput.Operator = "001";
		VData tVData = new VData();
		tVData.add(globalInput);
		tVData.add(tLZSysCertifySet);
		tPatchSysCertTakeBackBL.submitData(tVData, "INSERT||MAIN");

	}

}
