/*
 * <p>ClassName: CardPrintUI </p>
 * <p>Description: CardPrintUI类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: kevin
 * @CreateDate：2003-10-23
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.db.LZCardPlanDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LZCardPlanSchema;
import com.sinosoft.lis.vschema.LZCardPlanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;

public class CardPlanUI implements BusinessService {
private static Logger logger = Logger.getLogger(CardPlanUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();


	// 业务处理相关变量
	/** 全局数据 */

	public CardPlanUI() {
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

		CardPlanBL tCardPlanBL = new CardPlanBL();
		if (!tCardPlanBL.submitData(cInputData, cOperate)) {
			if (tCardPlanBL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tCardPlanBL.mErrors);
			} else {
				buildError("submitData", "tCardPlanBL发生错误，但是没有提供详细信息！");
			}
			return false;
		}
		mResult = tCardPlanBL.getResult();
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

		cError.moduleName = "CardPlanUI";
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
		globalInput.ComCode = "8611";
		globalInput.Operator = "001";

		LZCardPlanSet tLZCardPlanSet = new LZCardPlanSet();
		LZCardPlanSchema tLZCardPlanSchema = null;

		LZCardPlanDB tLZCardPlanDB = new LZCardPlanDB();
		tLZCardPlanSet = tLZCardPlanDB.query();

		// prepare main plan
		tLZCardPlanSchema = new LZCardPlanSchema();
		tLZCardPlanSchema.setPlanID("");
		tLZCardPlanSchema.setCertifyCode("1101");
		tLZCardPlanSchema.setAppCount("100");
		tLZCardPlanSchema.setRetState("Y");

		// 准备传输数据 VData
		VData vData = new VData();

		vData.addElement(tLZCardPlanSchema);
		vData.addElement(tLZCardPlanSet);
		vData.add(globalInput);

		Hashtable hashParams = new Hashtable();
		vData.add(hashParams);

		try {
			CardPlanUI tCardPlanUI = new CardPlanUI();
			if (!tCardPlanUI.submitData(vData, "QUERY||BUGET")) {
				if (tCardPlanUI.mErrors.needDealError()) {
					logger.debug(tCardPlanUI.mErrors.getFirstError());
				} else {
					logger.debug("保存失败，但是没有详细的原因");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
