package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:个单投保人资料变更UI类
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
public class PEdorACDetailUI {
private static Logger logger = Logger.getLogger(PEdorACDetailUI.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	public PEdorACDetailUI() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		PEdorACDetailBLF tPEdorACDetailBLF = new PEdorACDetailBLF();
		logger.debug("---AC UI BEGIN---" + mOperate);
		// printTheInputDate(cInputData);
		if (tPEdorACDetailBLF.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorACDetailBLF.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorACDetailUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据查询失败!";
			this.mErrors.addOneError(tError);
			mResult.clear();
			return false;
		} else {
			mResult = tPEdorACDetailBLF.getResult();
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";
		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

		// tLPEdorItemSchema.setEdorAcceptNo("86000000000372");
		// tLPEdorItemSchema.setPolNo("000000");
		// tLPEdorItemSchema.setEdorNo("410000000000394");
		// tLPEdorItemSchema.setEdorType("AC");
		// tLPEdorItemSchema.setContNo("230110000000461");
		// tLPEdorItemSchema.setInsuredNo("000000");
		// tLPEdorItemSchema.setPolNo("000000");
		//
		// tLPAppntSchema.setEdorNo("410000000000394");
		// tLPAppntSchema.setEdorType("AC");
		// tLPAppntSchema.setContNo("230110000000461");
		// tLPAppntSchema.setAppntName("asdfasdf");
		// tLPAppntSchema.setAppntSex("0");
		// tLPAppntSchema.setAppntBirthday("1980-1-1");
		// tLPAppntSchema.setIDType("8");
		// tLPAppntSchema.setIDNo("12312312");
		//
		// tLPAddressSchema.setEdorNo("410000000000394");
		// tLPAddressSchema.setEdorType("AC");
		// tLPAddressSchema.setCustomerNo("0000491300");
		// tLPAddressSchema.setAddressNo("23");

		tLPEdorItemSchema.setEdorAcceptNo("86000000001714");
		tLPEdorItemSchema.setContNo("230110000001825");
		tLPEdorItemSchema.setEdorNo("410000000001395");
		tLPEdorItemSchema.setEdorType("AC");
		tLPEdorItemSchema.setInsuredNo("000000");
		tLPEdorItemSchema.setPolNo("000000");

		tLPAppntSchema.setContNo("230110000001825");
		tLPAppntSchema.setEdorNo("410000000001395");
		tLPAppntSchema.setEdorType("AC");
		tLPAppntSchema.setAppntNo("0000503370");
		tLPAppntSchema.setAppntName("保全测试7");
		tLPAppntSchema.setAppntSex("1");
		tLPAppntSchema.setAppntBirthday("1980-09-08");
		tLPAppntSchema.setIDType("8");
		tLPAppntSchema.setIDNo("1234");
		tLPAppntSchema.setOccupationType("");
		tLPAppntSchema.setOccupationCode("A101");
		tLPAppntSchema.setMarriage("0");
		tLPAppntSchema.setAddressNo("1");
		// tLPAppntSchema.setNationality(request.getParameter("Nationality"));

		tLPAddressSchema.setProvince("1");
		tLPAddressSchema.setCity("1");
		tLPAddressSchema.setCounty("1");
		tLPAddressSchema.setPostalAddress("sdfasdfsdaf");
		tLPAddressSchema.setZipCode("111111");
		tLPAddressSchema.setPhone("");
		tLPAddressSchema.setMobile("");
		tLPAddressSchema.setEMail("");
		tLPAddressSchema.setAddressNo("9");
		tLPAddressSchema.setEdorNo("410000000001395");
		tLPAddressSchema.setEdorType("AC");
		tLPAddressSchema.setCustomerNo("0000503370");

		// 统一更新日期，时间
		// String theCurrentDate = PubFun.getCurrentDate();
		// String theCurrentTime = PubFun.getCurrentTime();
		// tLPEdorItemSchema.setMakeDate(theCurrentDate);
		// tLPEdorItemSchema.setMakeTime(theCurrentTime);
		// tLPEdorItemSchema.setModifyDate(theCurrentDate);
		// tLPEdorItemSchema.setModifyTime(theCurrentTime);
		// tLPEdorItemSchema.setOperator("001");
		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLPEdorItemSchema);
		tVData.add(tLPAppntSchema);
		tVData.add(tLPAddressSchema);
		PEdorACDetailUI tPEdorACDetailUI = new PEdorACDetailUI();
		boolean tag = tPEdorACDetailUI.submitData(tVData, "");
		if (tag) {
			logger.debug("Successful");
		} else {
			logger.debug("Fail");
		}
	}

	// private void printTheInputDate(VData cInputData)
	// {
	// LPAppntSchema tLPAppntSchema = (LPAppntSchema)
	// cInputData.getObjectByObjectName("LPAppntSchema",0);
	// LPAddressSchema tLPAddressSchema = (LPAddressSchema)
	// cInputData.getObjectByObjectName("LPAddressSchema",0);
	// LPEdorItemSchema tLPEdorItemSchema = (LPEdorItemSchema)
	// cInputData.getObjectByObjectName("LPEdorItemSchema",0);
	// GlobalInput tGlobalInput = (GlobalInput)
	// cInputData.getObjectByObjectName("GlobalInput",0);
	//
	// logger.debug("GlobalInput的内容:");
	// logger.debug("Operator：" + tGlobalInput.Operator);
	// logger.debug("ManageCom：" + tGlobalInput.ManageCom);
	// logger.debug("ComCode：" + tGlobalInput.ComCode);
	//
	// logger.debug("LPEdorItemSchema的内容:");
	// logger.debug("getEdorAcceptNo：" + tLPEdorItemSchema.getEdorAcceptNo());
	// logger.debug("getContNo：" + tLPEdorItemSchema.getContNo());
	// logger.debug("getEdorNo：" + tLPEdorItemSchema.getEdorNo());
	// logger.debug("getEdorType：" + tLPEdorItemSchema.getEdorType());
	// logger.debug("getInsuredNo：" + tLPEdorItemSchema.getInsuredNo());
	// logger.debug("getPolNo：" + tLPEdorItemSchema.getPolNo());
	//
	// logger.debug("LPAppntSchema的内容:");
	// logger.debug("getContNo：" + tLPAppntSchema.getContNo());
	// logger.debug("getEdorNo：" + tLPAppntSchema.getEdorNo());
	// logger.debug("getEdorType：" + tLPAppntSchema.getEdorType());
	// logger.debug("getAppntNo：" + tLPAppntSchema.getAppntNo());
	// logger.debug("getAppntName：" + tLPAppntSchema.getAppntName());
	// logger.debug("getAppntSex：" + tLPAppntSchema.getAppntSex());
	// logger.debug("getAppntBirthday：" + tLPAppntSchema.getAppntBirthday());
	// logger.debug("getIDType：" + tLPAppntSchema.getIDType());
	// logger.debug("getIDNo：" + tLPAppntSchema.getIDNo());
	// logger.debug("getOccupationType：" +
	// tLPAppntSchema.getOccupationType());
	// logger.debug("getOccupationCode：" +
	// tLPAppntSchema.getOccupationCode());
	// logger.debug("getMarriage：" + tLPAppntSchema.getMarriage());
	// logger.debug("getAddressNo：" + tLPAppntSchema.getAddressNo());
	//
	// logger.debug("LPAddressSchema的内容:");
	// logger.debug("getPostalAddress：" +
	// tLPAddressSchema.getPostalAddress());
	// logger.debug("getZipCode：" + tLPAddressSchema.getZipCode());
	// logger.debug("getPhone：" + tLPAddressSchema.getPhone());
	// logger.debug("getMobile：" + tLPAddressSchema.getMobile());
	// logger.debug("getEMail：" + tLPAddressSchema.getEMail());
	// logger.debug("getAddressNo：" + tLPAddressSchema.getAddressNo());
	// logger.debug("getEdorNo：" + tLPAddressSchema.getEdorNo());
	// logger.debug("getEdorType：" + tLPAddressSchema.getEdorType());
	// logger.debug("getCustomerNo：" + tLPAddressSchema.getCustomerNo());
	//
	// return;
	// }
}
