package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LJSGetDrawSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LJSGetDrawSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:年金，满期金给付UI类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Nicholas
 * @version 1.0
 */

public class PEdorAGDetailUI {
private static Logger logger = Logger.getLogger(PEdorAGDetailUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PEdorAGDetailUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		PEdorAGDetailBLF tPEdorAGDetailBLF = new PEdorAGDetailBLF();
		logger.debug("---AG UI BEGIN---" + mOperate);
		// printTheInputDate(cInputData);
		if (tPEdorAGDetailBLF.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorAGDetailBLF.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorAGDetailUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tPEdorAGDetailBLF.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		// 统一更新日期，时间
		String theCurrentDate = PubFun.getCurrentDate();
		String theCurrentTime = PubFun.getCurrentTime();

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LJSGetDrawSchema tLJSGetDrawSchema = null;
		LJSGetDrawSet tLJSGetDrawSet = new LJSGetDrawSet();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		PEdorAGDetailUI tPEdorAGDetailUI = new PEdorAGDetailUI();

		CErrors tError = null;
		// 后面要执行的动作：添加

		String tRela = "";
		String FlagStr = "";
		String Content = "";
		String transact = "";
		String Result = "";

		transact = "";
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		tGlobalInput.ComCode = "86";

		// 个人保单批改信息
		tLPEdorItemSchema.setEdorAcceptNo("86000000001190");
		tLPEdorItemSchema.setEdorNo("410000000000959");
		tLPEdorItemSchema.setEdorType("AG");
		tLPEdorItemSchema.setContNo("230110000000520");
		tLPEdorItemSchema.setInsuredNo("0000497650");
		tLPEdorItemSchema.setPolNo("210110000001052");
		tLPEdorItemSchema.setEdorAppDate(theCurrentDate);
		tLPEdorItemSchema.setEdorValiDate(theCurrentDate);

		// 个人险种信息，只可修改领取方式和帐户信息
		tLPPolSchema.setEdorNo("410000000000959");
		tLPPolSchema.setEdorType("AG");
		tLPPolSchema.setPolNo("210110000001052");
		tLPPolSchema.setGetForm("0");
		if ("0".equals("0")) {
			tLPPolSchema.setGetBankCode("0101");
			tLPPolSchema.setGetBankAccNo("7777");
			tLPPolSchema.setGetAccName("苏平");
		}

		try {
			tLJSGetDrawSchema = new LJSGetDrawSchema();
			tLJSGetDrawSchema.setGetNoticeNo("11111");
			tLJSGetDrawSchema.setDutyCode("33333");
			tLJSGetDrawSchema.setGetDutyKind("0");
			tLJSGetDrawSchema.setGetDutyCode("270201");
			tLJSGetDrawSchema.setPolNo("210110000001052");
			tLJSGetDrawSet.add(tLJSGetDrawSchema);
		} catch (Exception e) {
			logger.debug(e.toString());
		}

		try {
			tLPEdorItemSchema.setModifyDate(theCurrentDate);
			tLPEdorItemSchema.setModifyTime(theCurrentTime);

			// 准备传输数据 VData
			VData tVData = new VData();

			// 保存个人保单信息(保全)
			tVData.add(tGlobalInput);
			tVData.add(tLPEdorItemSchema);
			tVData.add(tLJSGetDrawSet);

			boolean tag = tPEdorAGDetailUI.submitData(tVData, "");
			if (tag) {
				logger.debug("Successful");
			} else {
				logger.debug("Fail");
			}
			// logger.debug("6$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$\r\n$$$$$$$$$$$$$$");
		} catch (Exception ex) {
			Content = transact + "失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}
		// 如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr.equals("")) {
			tError = tPEdorAGDetailUI.mErrors;
			if (!tError.needDealError()) {
				Content = " 保存成功";
				FlagStr = "Success";
				if (transact.equals("QUERY||MAIN")
						|| transact.equals("QUERY||DETAIL")) {
					if (tPEdorAGDetailUI.getResult() != null
							&& tPEdorAGDetailUI.getResult().size() > 0) {
						Result = (String) tPEdorAGDetailUI.getResult().get(0);
						if (Result == null || Result.trim().equals("")) {
							FlagStr = "Fail";
							Content = "提交失败!!";
						}
					}
				}
			} else {
				Content = " 保存失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}
	}
}
