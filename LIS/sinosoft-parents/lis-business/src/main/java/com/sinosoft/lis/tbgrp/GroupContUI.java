package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpAddressDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpAppntSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;
/**
 * <p>
 * Title:团单保存（IUD）UI层
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
public class GroupContUI  implements BusinessService{
private static Logger logger = Logger.getLogger(GroupContUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	public GroupContUI() {
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
		GroupContBL tGroupContBL = new GroupContBL();
		if (tGroupContBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tGroupContBL.mErrors);
			return false;
		} else {
			mResult = tGroupContBL.getResult();
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

	public static void main(String agrs[]) {
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema(); // 集体保单
		LCGrpAppntSchema tLCGrpAppntSchema = new LCGrpAppntSchema(); // 团单投保人
		LDGrpSchema tLDGrpSchema = new LDGrpSchema(); // 团体客户
		LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema(); // 团体客户地址
		LCGrpContDB tLCGrpContDB = new LCGrpContDB(); // 集体保单
		LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB(); // 团单投保人
		LDGrpDB tLDGrpDB = new LDGrpDB(); // 团体客户
		LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB(); // 团体客户地址

		GlobalInput mGlobalInput = new GlobalInput();
		TransferData tTransferData = new TransferData();

		mGlobalInput.ManageCom = "86";
		mGlobalInput.Operator = "001";
		tLCGrpContDB.setGrpContNo("120110000000064");
		tLCGrpContDB.getInfo();

		tLCGrpAppntDB.setGrpContNo("120110000000064");
		tLCGrpAppntDB.setCustomerNo("0000001660");
		tLCGrpAppntDB.getInfo();

		tLDGrpDB.setCustomerNo("0000001660");
		tLDGrpDB.getInfo();

		tLCGrpAddressDB.setAddressNo("86110000000169");
		tLCGrpAddressDB.setCustomerNo("0000001660");
		tLCGrpAddressDB.getInfo();

		tLCGrpAppntDB
				.setAddressNo("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");

		VData tVData = new VData();
		tVData.add(tLCGrpContDB.getSchema());
		tVData.add(tLCGrpAppntDB.getSchema());
		tVData.add(tLDGrpDB.getSchema());
		tVData.add(tLCGrpAddressDB.getSchema());
		tVData.add(mGlobalInput);

		GroupContBL tgrlbl = new GroupContBL();
		tgrlbl.submitData(tVData, "UPDATE||GROUPPOL");
		if (tgrlbl.mErrors.needDealError()) {
			logger.debug(tgrlbl.mErrors.getFirstError());
		}

	}

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

}
