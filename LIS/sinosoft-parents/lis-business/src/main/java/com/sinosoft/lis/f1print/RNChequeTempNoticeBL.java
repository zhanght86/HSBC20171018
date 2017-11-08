package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author guanwei
 * @version 1.0
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class RNChequeTempNoticeBL {
private static Logger logger = Logger.getLogger(RNChequeTempNoticeBL.class);
	private String mChequeNo = "";
	private String mOtherNo = "";
	private String mPayMoney = "";
	private String mAccName = "";
	private String mAgentName = "";
	private String mAgentCode = "";
	private String mMakeDate = "";
	private String mManageComName = "";

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String mSelString = "";

	// 业务处理相关变量
	/** 全局数据 */

	private GlobalInput mGlobalInput = new GlobalInput();

	private TransferData mTransferData = new TransferData();
	private boolean tFlag = false;
	private Object text;

	public RNChequeTempNoticeBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		mResult.clear();

		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
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
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mChequeNo = (String) mTransferData.getValueByName("ChequeNo"); // 支票号
		logger.debug("|||||||||ChequeNo||||||||" + mChequeNo);
		mOtherNo = (String) mTransferData.getValueByName("OtherNo"); // 保单号/投保单号/保全号
		logger.debug("|||||||||OtherNo||||||||" + mOtherNo);
		mPayMoney = (String) mTransferData.getValueByName("PayMoney"); // 金额
		logger.debug("|||||||||PayMoney||||||||" + mPayMoney);
		mAccName = (String) mTransferData.getValueByName("AccName"); // 出票单位
		logger.debug("|||||||||AccName||||||||" + mAccName);
		mAgentName = (String) mTransferData.getValueByName("AgentName"); // 业务员姓名
		logger.debug("|||||||||AgentName||||||||" + mAgentName);
		mAgentCode = (String) mTransferData.getValueByName("AgentCode"); // 业务员代码
		logger.debug("|||||||||AgentCode||||||||" + mAgentCode);
		mMakeDate = (String) mTransferData.getValueByName("MakeDate"); // 缴费日期
		logger.debug("|||||||||MakeDate||||||||" + mMakeDate);
		mManageComName = (String) mTransferData.getValueByName("ManageComName"); // 收费机构
		logger.debug("|||||||||ManageComName||||||||" + mManageComName);
		return true;
	}

	/**
	 * 获取返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RNChequeTempNoticeBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	private boolean getPrintData() {

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("RNChequeTempNotice.vts", "PRINT"); // 最好紧接着就初始化xml文档

		FYDate tFYDate = new FYDate(mMakeDate);
		String tMakeDate = tFYDate.getYear() + "年" + tFYDate.getMonth() + "月"
				+ tFYDate.getDateOfMonth() + "日";
		FYDate tcFYDate = new FYDate(PubFun.getCurrentDate());
		String tCurrentDate = tcFYDate.getYear() + "年" + tcFYDate.getMonth()
				+ "月" + tcFYDate.getDateOfMonth() + "日";

		texttag.add("ChequeNo", mChequeNo);
		texttag.add("OtherNo", mOtherNo);
		texttag.add("PayMoney", mPayMoney);
		texttag.add("PayMoneyBig", PubFun.getChnMoney(Double
				.parseDouble(mPayMoney)));
		texttag.add("AppntName", mAccName);
		texttag.add("AgentName", mAgentName);
		texttag.add("AgentCode", mAgentCode);
		texttag.add("MakeDate", tMakeDate);
		texttag.add("ManageComName", mManageComName);
		texttag.add("CurrentDate", tCurrentDate);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		mResult.addElement(xmlexport);
		return true;
	}

	private void jbInit() throws Exception {
	}
}
