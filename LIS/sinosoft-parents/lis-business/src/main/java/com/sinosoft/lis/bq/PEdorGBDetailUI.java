package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 领取年龄变更（保全项目代码：GB）功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WuHao
 * @version 1.0
 */
public class PEdorGBDetailUI {
private static Logger logger = Logger.getLogger(PEdorGBDetailUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 重算后的领取标准 */
	private String mStandMoney;

	public PEdorGBDetailUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		PEdorGBDetailBLF tPEdorGBDetailBLF = new PEdorGBDetailBLF();
		if (!tPEdorGBDetailBLF.submitData(cInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorGBDetailBLF.mErrors);
			mResult.clear();
			return false;
		}
		mResult = tPEdorGBDetailBLF.getResult();
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	public String getStandMoney() {
		return this.mStandMoney;
	}

	public static void main(String[] args) {
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPGetSchema tLPGetSchema = new LPGetSchema();
		LPDutySchema tLPDutySchema = new LPDutySchema();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		// tLPEdorItemSchema.decode("86000000001527|||GB|||230110000000577|||||||||||0|0|0|0|bq|2005-07-01|13:50:16|2005
		// - 07 - 01 | 13 : 50 : 16 || || || | ");

		tLPEdorItemSchema.setEdorAcceptNo("86000000001847");
		tLPEdorItemSchema.setEdorNo("410000000001541");
		tLPEdorItemSchema.setEdorType("GB");
		tLPEdorItemSchema.setContNo("2301190000000045");
		tLPEdorItemSchema.setInsuredNo("0000314400");
		tLPEdorItemSchema.setOperator("001");
		tLPEdorItemSchema.setModifyDate("2005-07-11");
		tLPEdorItemSchema.setModifyTime("18:50:16");
		tLPEdorItemSchema.setPolNo("210110000001344");
		tLPEdorItemSchema.setMakeDate("2005-07-11");
		tLPEdorItemSchema.setMakeTime("13:50:16");

		// tLPGetSchema.decode("|||||145000|145040|0|||0|0|||0||||0|0|0|0|||||||||||");
		tLPGetSchema.setGetDutyCode("618041");
		tLPGetSchema.setDutyCode("618000");
		tLPGetSchema.setGetDutyKind("0");
		tLPGetSchema.setPolNo("210110000001344");
		tLPGetSchema.setEdorNo("410000000001541");
		tLPGetSchema.setEdorType("GB");

		// tLPDutySchema.decode("|||145000|230110000000577|0|0|0|0|0|0|0|0|0|0||0||||0||0||0||0||||0|||||||||0|0|0||||||||||||0");
		tLPDutySchema.setEdorNo("410000000001541");
		tLPDutySchema.setEdorType("GB");
		tLPDutySchema.setDutyCode("618000");
		tLPDutySchema.setPolNo("210110000001344");
		tLPDutySchema.setGetYear("70");
		// tLPPolSchema.decode("||||230110000000577|||||||||||||||||||||||0|0|||||||||||||||0||0||0||0|||||0|0|0|0|0||0|0|0|0|0|0|0|0|0|0|0||||||0||||||||||||||||||||||||||||||||||||||0|||||");

		tLPPolSchema.setEdorNo("410000000001541");
		tLPPolSchema.setEdorType("GB");
		tLPPolSchema.setPolNo("210110000001344");
		tLPPolSchema.setGetYear("70");

		GlobalInput tG = new GlobalInput();
		tG.Operator = "bq";
		tG.ManageCom = "86110000";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ChgAfterAge", "70");
		logger.debug("begin.....");

		VData tVData = new VData();
		tVData.addElement(tG);
		tVData.addElement(tLPEdorItemSchema);
		tVData.addElement(tLPGetSchema);
		tVData.addElement(tLPDutySchema);
		tVData.addElement(tLPPolSchema);
		tVData.addElement(tTransferData);

		PEdorGBDetailUI tPEdorGBDetailUI = new PEdorGBDetailUI();
		if (!tPEdorGBDetailUI.submitData(tVData, "UPDATE||MAIN")) {
			logger.debug("error");
		} else
			logger.debug("ok");
	}
}
