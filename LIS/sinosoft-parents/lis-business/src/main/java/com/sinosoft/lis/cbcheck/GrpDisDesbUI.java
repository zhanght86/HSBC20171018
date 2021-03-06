package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCPENoticeItemSchema;
import com.sinosoft.lis.schema.LCPENoticeResultSchema;
import com.sinosoft.lis.vschema.LCPENoticeItemSet;
import com.sinosoft.lis.vschema.LCPENoticeResultSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author HYQ
 * @version 1.0
 */

public class GrpDisDesbUI {
private static Logger logger = Logger.getLogger(GrpDisDesbUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	private VData mInputData;

	/** 数据操作字符串 */
	private String mOperate;

	public GrpDisDesbUI() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;

		GrpDisDesbBL tGrpDisDesbBL = new GrpDisDesbBL();

		logger.debug("--------DisDesb Start!---------");
		tGrpDisDesbBL.submitData(cInputData, cOperate);
		logger.debug("--------DisDesb End!---------");

		// 如果有需要处理的错误，则返回
		if (tGrpDisDesbBL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(tGrpDisDesbBL.mErrors);
		}
		logger.debug("error num=" + mErrors.getErrorCount());
		mInputData = null;
		mResult = tGrpDisDesbBL.getResult();

		return true;
	}

	/**
	 * 返回结果方法
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		VData tVData = new VData();
		GlobalInput mGlobalInput = new GlobalInput();

		/** 全局变量 */
		mGlobalInput.Operator = "001";
		// mGlobalInput.ComCode = "86";
		mGlobalInput.ManageCom = "86";

		LCPENoticeResultSet tLCPENoticeResultSet = new LCPENoticeResultSet();
		LCPENoticeResultSchema tLCPENoticeResultSchema = new LCPENoticeResultSchema();
		LCPENoticeResultSchema tLCPENoticeResultSchema1 = new LCPENoticeResultSchema();
		tLCPENoticeResultSchema.setContNo("130210000000724");
		tLCPENoticeResultSchema.setProposalContNo("130210000000724");
		tLCPENoticeResultSchema.setPrtSeq("8100000000074");
		tLCPENoticeResultSchema.setCustomerNo("0000488540");
		tLCPENoticeResultSchema.setDisDesb("sdfsdf");
		tLCPENoticeResultSchema.setDisResult("sdfsdf");
		tLCPENoticeResultSchema.setICDCode("asdfsdf");
		tLCPENoticeResultSet.add(tLCPENoticeResultSchema);
		tLCPENoticeResultSchema1.setContNo("130210000000724");
		tLCPENoticeResultSchema1.setProposalContNo("130210000000724");
		tLCPENoticeResultSchema1.setPrtSeq("8100000000074");
		tLCPENoticeResultSchema1.setCustomerNo("0000488540");
		tLCPENoticeResultSchema1.setDisDesb("sd888fsdf");
		tLCPENoticeResultSchema1.setDisResult("sdf888sdf");
		tLCPENoticeResultSchema1.setICDCode("asd8888fsdf");
		tLCPENoticeResultSet.add(tLCPENoticeResultSchema1);

		LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
		LCPENoticeItemSchema tLCPENoticeItemSchema = new LCPENoticeItemSchema();

		tVData.add(mGlobalInput);
		tVData.add(tLCPENoticeResultSet);
		tVData.add(tLCPENoticeItemSet);

		GrpDisDesbBL tGrpDisDesbBL = new GrpDisDesbBL();
		try {
			if (tGrpDisDesbBL.submitData(tVData, "")) {

			} else {
				logger.debug("error:"
						+ tGrpDisDesbBL.mErrors.getError(0).errorMessage);
			}
		} catch (Exception e) {
			logger.debug("error:" + e);
		}

	}

}
