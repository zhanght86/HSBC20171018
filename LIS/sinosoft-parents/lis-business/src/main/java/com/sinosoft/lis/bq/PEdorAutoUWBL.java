package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 个人保全自动核保框架
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @ReWrite ZhangRong
 * @version 1.0
 */
public class PEdorAutoUWBL {
private static Logger logger = Logger.getLogger(PEdorAutoUWBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private VData pInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mUWState = "9";
	private String mUWGrade = "A";

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	TransferData mTransferData = new TransferData();
	private MMap mMap = new MMap();

	public PEdorAutoUWBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		pInputData = new VData();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据准备操作（preparedata())
		if (!dealData()) {
			return false;
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
		// 得到保单号
		mLPEdorMainSet = (LPEdorMainSet) cInputData.getObjectByObjectName(
				"LPEdorMainSet", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mLPEdorMainSet == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		if ((mLPEdorMainSchema.getContNo() == null || mLPEdorMainSchema
				.getContNo().trim().equals(""))
				&& (mLPEdorMainSchema.getEdorNo() == null || mLPEdorMainSchema
						.getEdorNo().trim().equals(""))) {
			mErrors.addOneError(new CError("申请批单数据不完全！"));
			return false;
		}
		return true;

	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		int n = mLPEdorMainSet.size();
		for (int i = 1; i <= n; i++) {
			mLPEdorMainSchema = mLPEdorMainSet.get(i);

			// 数据校验操作（checkdata)
			if (!checkData()) {
				mErrors.addOneError(new CError("申请批单"
						+ mLPEdorMainSchema.getEdorNo() + "自动核保失败！"));
				continue;
			}

			pInputData.clear();
			pInputData.addElement("I");
			pInputData.addElement(mGlobalInput);
			pInputData.addElement(mLPEdorMainSchema);
			pInputData.addElement(mTransferData);

			// 内含保全财务合计处理，将批改补退费表数据合计到应收总表
			EdorAutoUWBL tEdorAutoUWBL = new EdorAutoUWBL();
			if (!tEdorAutoUWBL.submitData(pInputData, "")) {
				mErrors.copyAllErrors(tEdorAutoUWBL.mErrors);
				mErrors.addOneError(new CError("批单"
						+ mLPEdorMainSchema.getEdorNo() + "自动核保失败！"));
				continue;
			}

			MMap map = new MMap();
			map = (MMap) tEdorAutoUWBL.getResult().getObjectByObjectName(
					"MMap", 0);
			TransferData tTransferData = new TransferData();
			tTransferData = (TransferData) tEdorAutoUWBL.getResult()
					.getObjectByObjectName("TransferData", 0);
			String tUWState = (String) tTransferData.getValueByName("UWFlag");
			String tUWGrade = (String) tTransferData.getValueByName("UWGrade");
			if (!"9".equals(tUWState)) {
				mUWState = tUWState;
			}
			if (tUWGrade.compareTo(mUWGrade) > 0) {
				mUWGrade = tUWGrade;
			}

			mMap.add(map);

		}
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("UWFlag", mUWState);
		tTransferData.setNameAndValue("UWGrade", mUWGrade);
		// tTransferData.setNameAndValue("PayPrintParams", mPayPrintParams);
		mResult.add(tTransferData);
		mResult.add(mMap);

		if (mErrors.needDealError()) {
			return false;
		}

		return true;
	}

}
