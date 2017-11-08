package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title:保全 </p>
 * <p>Description:转养老金UI层 </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Nicholas
 * @version 1.0
 */

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPAGetDrawSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.vschema.LPAGetDrawSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class PEdorTADetailUI {
private static Logger logger = Logger.getLogger(PEdorTADetailUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 增加的养老金金额 */
	private String strSumAmnt;

	public PEdorTADetailUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		this.mInputData = (VData) cInputData.clone();
		PEdorTADetailBLF tPEdorTADetailBLF = new PEdorTADetailBLF();
		logger.debug("---TA BLF BEGIN---");
		if (!tPEdorTADetailBLF.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorTADetailBLF.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorTADetailUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult.clear();
			// mResult = tPEdorTADetailBLF.getResult();
			strSumAmnt = tPEdorTADetailBLF.getSumAmnt();
			mResult.add(strSumAmnt);
			return true;
		}
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	// 获得增加的养老金金额，可在保全操作完毕后由BLF层加入mResult
	public String getSumAmnt() {
		return this.strSumAmnt;
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.ComCode = "86";

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPAGetDrawSchema tLPAGetDrawSchema = new LPAGetDrawSchema(); // 删除记录用(可能有多个，放在Set中)
		LPAGetDrawSet tLPAGetDrawSet = new LPAGetDrawSet(); // 删除记录用
		LPDutySchema tLPDutySchema = new LPDutySchema(); // 插入用
		LPGetSchema tLPGetSchema = new LPGetSchema(); // 插入用
		PEdorTADetailUI tPEdorTADetailUI = new PEdorTADetailUI();

		CErrors tError = null;
		// 后面要执行的动作：删除，添加

		String tRela = "";
		String FlagStr = "";
		String Content = "";
		String Result = "";

		tLPEdorItemSchema.setEdorAcceptNo("6120060702000003");
		tLPEdorItemSchema.setEdorNo("6020060702000005");
		tLPEdorItemSchema.setEdorType("TA");
		tLPEdorItemSchema.setContNo("000000000892");
		tLPEdorItemSchema.setInsuredNo("0000548520");
		tLPEdorItemSchema.setPolNo("210110000004792");

		// “给付表(生存领取_实付)”对应记录
		tLPAGetDrawSchema.setEdorNo("6020060702000005");
		tLPAGetDrawSchema.setEdorType("TA");
		// tLPAGetDrawSchema.setGetNoticeNo("360110000001488");
		tLPAGetDrawSchema.setActuGetNo("370000000000181");
		tLPAGetDrawSchema.setPolNo("210110000004792");
		tLPAGetDrawSchema.setDutyCode("108300");
		tLPAGetDrawSchema.setGetDutyKind("1");
		tLPAGetDrawSchema.setGetDutyCode("108340");
		tLPAGetDrawSet.add(tLPAGetDrawSchema);

		tLPAGetDrawSchema = new LPAGetDrawSchema();
		tLPAGetDrawSchema.setEdorNo("6020060702000005");
		tLPAGetDrawSchema.setEdorType("TA");
		// tLPAGetDrawSchema.setGetNoticeNo("360110000001488");
		tLPAGetDrawSchema.setActuGetNo("370000000000182");
		tLPAGetDrawSchema.setPolNo("210110000004792");
		tLPAGetDrawSchema.setDutyCode("108300");
		tLPAGetDrawSchema.setGetDutyKind("1");
		tLPAGetDrawSchema.setGetDutyCode("108340");
		tLPAGetDrawSet.add(tLPAGetDrawSchema);

		// 添加“保全保单险种责任表”记录
		String theCurrentDate = PubFun.getCurrentDate();
		String theCurrentTime = PubFun.getCurrentTime();
		tLPDutySchema.setEdorNo("6020060702000005");
		tLPDutySchema.setEdorType("TA");
		tLPDutySchema.setPolNo("210110000002585");
		tLPDutySchema.setDutyCode("108300");

		// 添加“保全领取项表”记录
		tLPGetSchema.setEdorNo("6020060702000005");
		tLPGetSchema.setEdorType("TA");
		tLPGetSchema.setPolNo("210110000004792");
		tLPGetSchema.setDutyCode("108300");
		tLPGetSchema.setGetDutyCode("108344");

		try {
			// 准备传输数据 VData
			VData tVData = new VData();

			// 保存个人保单信息(保全)
			tVData.addElement(tGlobalInput);
			tVData.addElement(tLPEdorItemSchema);
			tVData.addElement(tLPAGetDrawSet);
			tVData.addElement(tLPDutySchema);
			tVData.addElement(tLPGetSchema);
			boolean tag = tPEdorTADetailUI.submitData(tVData, "");
			if (tag) {
				logger.debug("Successful");
				logger.debug("Amnt:" + tPEdorTADetailUI.getSumAmnt());
			} else {
				logger.debug("Fail");
			}
		} catch (Exception ex) {
			Content = "失败，原因是:" + ex.toString();
			FlagStr = "Fail";
		}
		// 如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr.equals("")) {
			logger.debug("------success");
			tError = tPEdorTADetailUI.mErrors;
			if (!tError.needDealError()) {
				Content = " 保存成功";
				FlagStr = "Success";
			} else {
				Content = " 保存失败，原因是:" + tError.getFirstError();
				FlagStr = "Fail";
			}
		}
		logger.debug("OK!");
	}
}
