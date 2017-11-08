/*
 * <p>ClassName: CardPrintUI </p>
 * <p>Description: CardPrintUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: kevin
 * @CreateDate：2002-10-12
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LZCardPrintSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

public class CardPrintUI implements BusinessService {
private static Logger logger = Logger.getLogger(CardPrintUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	public CardPrintUI() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;

		// 进行业务处理
		if (!dealData())
			return false;

		// 准备往后台的数据
		if (!prepareOutputData())
			return false;

		CardPrintBL tCardPrintBL = new CardPrintBL();

		if (!tCardPrintBL.submitData(cInputData, cOperate)) {
			if (tCardPrintBL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tCardPrintBL.mErrors);
			} else {
				buildError("submitData", "CardPrintBL发生错误，但是没有提供详细信息！");
			}
			return false;
		}

		mResult = tCardPrintBL.getResult();

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "CardPrintUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
	
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public static void main(String[] args) {
		GlobalInput globalInput = new GlobalInput();

		globalInput.ComCode = "86";
		globalInput.Operator = "001";

		LZCardPrintSchema tLZCardPrintSchema = new LZCardPrintSchema();

		tLZCardPrintSchema.setCertifyCode("01");
		tLZCardPrintSchema.setStartNo("startnostartno");
		tLZCardPrintSchema.setEndNo("startnostartno");
		tLZCardPrintSchema.setSumCount(100);

		// 准备传输数据 VData
		VData vData = new VData();

		vData.addElement(tLZCardPrintSchema);
		vData.add(globalInput);

		Hashtable hashParams = new Hashtable();

		hashParams.put("CertifyClass", CertifyFunc.CERTIFY_CLASS_CERTIFY);

		vData.add(hashParams);

		try {
			CardPrintUI tCardPrintUI = new CardPrintUI();

			if (!tCardPrintUI.submitData(vData, "INSERT||REQUEST")) {
				if (tCardPrintUI.mErrors.needDealError()) {
					logger.debug(tCardPrintUI.mErrors.getFirstError());
				} else {
					logger.debug("保存失败，但是没有详细的原因");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
