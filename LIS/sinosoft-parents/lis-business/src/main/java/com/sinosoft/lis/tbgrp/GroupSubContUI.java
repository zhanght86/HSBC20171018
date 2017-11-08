package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGeneralSchema;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:团单分单定制UI层
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author wzw
 * @version 1.0
 */
public class GroupSubContUI {
private static Logger logger = Logger.getLogger(GroupSubContUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public GroupSubContUI() {
	}

	/**
	 * 不执行任何操作，只传递数据给下一层
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		GroupSubContBL tGroupSubContBL = new GroupSubContBL();

		if (tGroupSubContBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGroupSubContBL.mErrors);

			return false;
		} else {
			mResult = tGroupSubContBL.getResult();
		}

		return true;
	}

	/**
	 * 获取从BL层取得的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] agrs) {
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema(); // 集体保单
		LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema(); // 团单投保人
		LDGrpSchema tLDGrpSchema = new LDGrpSchema(); // 团体客户
		LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema(); // 团体客户地址
		LCGeneralSchema tLCGeneralSchema = new LCGeneralSchema(); // 团体分单表
		GlobalInput mGlobalInput = new GlobalInput();

		mGlobalInput.ManageCom = "86";
		mGlobalInput.Operator = "001";

		tLDGrpSchema.setCustomerNo("0000003470");
		tLDGrpSchema.setGrpName("河北电子信息公司");
		tLDGrpSchema.setBankAccNo("1010101010");

		tLCGrpAddressSchema.setCustomerNo("0000003470");
		tLCGrpAddressSchema.setGrpAddress("河北省");

		tLCGeneralSchema.setGrpContNo("140110000000050");
		tLCGeneralSchema.setPrtNo("86861234567890");
		tLCGeneralSchema.setCustomerNo("0000003470");
		tLCGeneralSchema.setExecuteCom("86130000");
		tLCGeneralSchema.setAddressNo("");

		VData tVData = new VData();
		tVData.add(tLDGrpSchema);
		tVData.add(tLCGrpAddressSchema);
		tVData.add(tLCGeneralSchema);
		tVData.add(mGlobalInput);

		GroupSubContBL tgrlbl = new GroupSubContBL();
		tgrlbl.submitData(tVData, "UPDATE");

		if (tgrlbl.mErrors.needDealError()) {
			logger.debug(tgrlbl.mErrors.getFirstError());
		}
	}
}
