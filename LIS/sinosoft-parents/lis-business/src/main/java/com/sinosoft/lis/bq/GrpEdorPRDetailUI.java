package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPGrpAddressSchema;
import com.sinosoft.lis.schema.LPGrpAppntSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:被保险人地址变更功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Tjj
 * @version 1.0
 */
public class GrpEdorPRDetailUI {
private static Logger logger = Logger.getLogger(GrpEdorPRDetailUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public GrpEdorPRDetailUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		GrpEdorPRDetailBL tGrpEdorPRDetailBL = new GrpEdorPRDetailBL();
		logger.debug("---UI BEGIN---" + mOperate);
		if (tGrpEdorPRDetailBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGrpEdorPRDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpEdorPRDetailUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else
			mResult = tGrpEdorPRDetailBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		LPGrpAppntSchema tLPGrpAppntSchema = new LPGrpAppntSchema();
		LPGrpAddressSchema tLPGrpAddressSchema = new LPGrpAddressSchema();
		LPGrpContSchema tLPGrpContSchema = new LPGrpContSchema();
		GrpEdorPRDetailUI tGrpEdorPRDetailUI = new GrpEdorPRDetailUI();
		CErrors tError = null;
		String tRela = "";
		String FlagStr = "";
		String Content = "";
		String fmAction = "";
		String Result = "";

		GlobalInput tG = new GlobalInput();
		tG.ComCode = "86";
		tG.Operator = "001";
		// tG.

		// tLPGrpEdorItemSchema.setEdorAcceptNo(request.getParameter("EdorAcceptNo"));
		tLPGrpEdorItemSchema.setEdorNo("430140000000010");
		tLPGrpEdorItemSchema.setEdorType("PR");
		tLPGrpEdorItemSchema.setGrpContNo("240000000000014");
		tLPGrpEdorItemSchema.setEdorAcceptNo("86140000000011");
		// tLPGrpEdorItemSchema.setManageCom("8611");
		// tLPGrpEdorItemSchema.setEdorAppNo(request.getParameter("EdorAppNo"));

		// 团单投保人信息 LCGrpAppnt
		tLPGrpAppntSchema.setEdorNo("430140000000010");
		tLPGrpAppntSchema.setEdorType("PR");
		tLPGrpAppntSchema.setGrpContNo("240000000000014"); // 集体投保单号码
		tLPGrpAppntSchema.setCustomerNo("0000003330"); // 客户号码
		tLPGrpAppntSchema.setName("test");
		tLPGrpAppntSchema.setAddressNo("1");
		tLPGrpAppntSchema.setZipCode("100083");

		// 团单地址信息
		tLPGrpAddressSchema.setEdorNo("430140000000010");
		tLPGrpAddressSchema.setEdorType("PR");
		tLPGrpAddressSchema.setCustomerNo("0000003330");
		tLPGrpAddressSchema.setAddressNo("1");
		tLPGrpAddressSchema.setGrpAddress("上海闵行区");
		tLPGrpAddressSchema.setGrpZipCode("100083");
		tLPGrpAddressSchema.setLinkMan1("dddddd");
		tLPGrpAddressSchema.setPhone1("021-64235787");
		tLPGrpAddressSchema.setE_Mail1("web@test.com");

		// 团单合同信息
		tLPGrpContSchema.setEdorNo("430140000000010");
		tLPGrpContSchema.setEdorType("PR");
		tLPGrpContSchema.setGrpContNo("240000000000014");
		// tLPGrpContSchema.setProposalGrpContNo(request.getParameter("ProposalGrpContNo"));

		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.addElement(tG);

		tVData.addElement(tLPGrpEdorItemSchema);
		tVData.addElement(tLPGrpAppntSchema);
		tVData.addElement(tLPGrpAddressSchema);
		tVData.addElement("86320000");

		tGrpEdorPRDetailUI.submitData(tVData, fmAction);
	}

}
