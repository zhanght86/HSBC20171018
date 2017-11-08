package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpPolRiskElementSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class GrpRiskElenemtAdjust {
private static Logger logger = Logger.getLogger(GrpRiskElenemtAdjust.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 数据操作字符串 */
	private String mOperate;
	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	private TransferData mTransferData = new TransferData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private String riskcode = "";
	private String grpcontno = "";
	private String rate1 = "";
	private String rate2 = "";
	private String rate3 = "";
	private String rate4 = "";
	private String rate5 = "";
	private String rate6 = "";
	private String operator = "";

	public GrpRiskElenemtAdjust() {

	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		this.prepareOutputData();

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @param vData
	 *            VData
	 * @return boolean
	 */
	private void prepareOutputData() {

		mInputData.clear();
		mInputData.add(map);

	}

	private boolean prepareOutputData(VData vData) {
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		// 先删除，在添加，免除修改
		LCGrpPolRiskElementSchema tCGrpPolRiskElement = new LCGrpPolRiskElementSchema();
		tCGrpPolRiskElement.setGrpPolNo(grpcontno);
		tCGrpPolRiskElement.setGrpContNo(grpcontno);
		tCGrpPolRiskElement.setRiskCode(riskcode);
		tCGrpPolRiskElement.setOperator(operator);
		tCGrpPolRiskElement.setRiskElement("1");
		tCGrpPolRiskElement.setRiskElementRate(rate1);
		tCGrpPolRiskElement.setMakeDate(theCurrentDate);
		tCGrpPolRiskElement.setMakeTime(theCurrentTime);
		tCGrpPolRiskElement.setModifyDate(theCurrentDate);
		tCGrpPolRiskElement.setModifyTime(theCurrentTime);
		map.put(tCGrpPolRiskElement, "DELETE&INSERT");

		tCGrpPolRiskElement = new LCGrpPolRiskElementSchema();
		tCGrpPolRiskElement.setGrpPolNo(grpcontno);
		tCGrpPolRiskElement.setGrpContNo(grpcontno);
		tCGrpPolRiskElement.setRiskCode(riskcode);
		tCGrpPolRiskElement.setOperator(operator);
		tCGrpPolRiskElement.setRiskElement("2");
		tCGrpPolRiskElement.setMakeDate(theCurrentDate);
		tCGrpPolRiskElement.setMakeTime(theCurrentTime);
		tCGrpPolRiskElement.setModifyDate(theCurrentDate);
		tCGrpPolRiskElement.setModifyTime(theCurrentTime);

		tCGrpPolRiskElement.setRiskElementRate(rate2);
		map.put(tCGrpPolRiskElement, "DELETE&INSERT");

		tCGrpPolRiskElement = new LCGrpPolRiskElementSchema();
		tCGrpPolRiskElement.setGrpPolNo(grpcontno);
		tCGrpPolRiskElement.setGrpContNo(grpcontno);
		tCGrpPolRiskElement.setRiskCode(riskcode);
		tCGrpPolRiskElement.setOperator(operator);
		tCGrpPolRiskElement.setRiskElement("3");
		tCGrpPolRiskElement.setMakeDate(theCurrentDate);
		tCGrpPolRiskElement.setMakeTime(theCurrentTime);
		tCGrpPolRiskElement.setModifyDate(theCurrentDate);
		tCGrpPolRiskElement.setModifyTime(theCurrentTime);

		tCGrpPolRiskElement.setRiskElementRate(rate3);
		map.put(tCGrpPolRiskElement, "DELETE&INSERT");

		tCGrpPolRiskElement = new LCGrpPolRiskElementSchema();
		tCGrpPolRiskElement.setGrpPolNo(grpcontno);
		tCGrpPolRiskElement.setGrpContNo(grpcontno);
		tCGrpPolRiskElement.setRiskCode(riskcode);
		tCGrpPolRiskElement.setOperator(operator);
		tCGrpPolRiskElement.setRiskElement("4");
		tCGrpPolRiskElement.setMakeDate(theCurrentDate);
		tCGrpPolRiskElement.setMakeTime(theCurrentTime);
		tCGrpPolRiskElement.setModifyDate(theCurrentDate);
		tCGrpPolRiskElement.setModifyTime(theCurrentTime);

		tCGrpPolRiskElement.setRiskElementRate(rate4);
		map.put(tCGrpPolRiskElement, "DELETE&INSERT");

		tCGrpPolRiskElement = new LCGrpPolRiskElementSchema();
		tCGrpPolRiskElement.setGrpPolNo(grpcontno);
		tCGrpPolRiskElement.setGrpContNo(grpcontno);
		tCGrpPolRiskElement.setRiskCode(riskcode);
		tCGrpPolRiskElement.setOperator(operator);
		tCGrpPolRiskElement.setRiskElement("5");
		tCGrpPolRiskElement.setMakeDate(theCurrentDate);
		tCGrpPolRiskElement.setMakeTime(theCurrentTime);
		tCGrpPolRiskElement.setModifyDate(theCurrentDate);
		tCGrpPolRiskElement.setModifyTime(theCurrentTime);

		tCGrpPolRiskElement.setRiskElementRate(rate5);
		map.put(tCGrpPolRiskElement, "DELETE&INSERT");

		tCGrpPolRiskElement = new LCGrpPolRiskElementSchema();
		tCGrpPolRiskElement.setGrpPolNo(grpcontno);
		tCGrpPolRiskElement.setGrpContNo(grpcontno);
		tCGrpPolRiskElement.setRiskCode(riskcode);
		tCGrpPolRiskElement.setOperator(operator);
		tCGrpPolRiskElement.setRiskElement("6");
		tCGrpPolRiskElement.setMakeDate(theCurrentDate);
		tCGrpPolRiskElement.setMakeTime(theCurrentTime);
		tCGrpPolRiskElement.setModifyDate(theCurrentDate);
		tCGrpPolRiskElement.setModifyTime(theCurrentTime);

		tCGrpPolRiskElement.setRiskElementRate(rate6);
		map.put(tCGrpPolRiskElement, "DELETE&INSERT");

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			CError tError = new CError();
			tError.moduleName = "GrpRiskElenemtAdjust";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理mGlobalInput出错!";
			this.mErrors.addOneError(tError);

		}
		operator = mGlobalInput.Operator;
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null) {
			CError tError = new CError();
			tError.moduleName = "GrpRiskElenemtAdjust";
			tError.functionName = "getInputData";
			tError.errorMessage = "处理TransferData出错!";
			this.mErrors.addOneError(tError);

		}
		operator = mGlobalInput.Operator;
		grpcontno = (String) mTransferData.getValueByName("grpcontno");
		riskcode = (String) mTransferData.getValueByName("riskcode");
		rate1 = (String) mTransferData.getValueByName("rate1");
		rate2 = (String) mTransferData.getValueByName("rate2");
		rate3 = (String) mTransferData.getValueByName("rate3");
		rate4 = (String) mTransferData.getValueByName("rate4");
		rate5 = (String) mTransferData.getValueByName("rate5");
		rate6 = (String) mTransferData.getValueByName("rate6");
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "DerferAppF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("rate1", "1");
		tTransferData.setNameAndValue("rate2", "2");
		tTransferData.setNameAndValue("rate3", "2");
		tTransferData.setNameAndValue("rate4", "2");
		tTransferData.setNameAndValue("rate5", "2");
		tTransferData.setNameAndValue("rate6", "2");

		tTransferData.setNameAndValue("riskcode", "2");
		tTransferData.setNameAndValue("grpcontno", "2");
		VData tVData = new VData();
		tVData.add(tG);
		tVData.addElement(tTransferData);
		GrpRiskElenemtAdjust tGrpRiskElenemtAdjust = new GrpRiskElenemtAdjust();
		if (tGrpRiskElenemtAdjust.submitData(tVData, "Insert") == false) {
			logger.debug("failed");

		}

	}

}
