package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web 业务系统</p>
 * <p>Description: 客户地址信息变更BL</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Nicholas
 * @version 1.0
 */

/*import com.sinosoft.lis.bl.*;
 import com.sinosoft.lis.db.*;*/
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPAddressSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class PEdorCDDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(PEdorCDDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	public PEdorCDDetailBLF() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorCDDetailBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 准备需要保存的数据 调用个人保全投保人信息变更业务逻辑处理类PEdorACBL进行处理
	 */
	private boolean dealData() {
		PEdorCDDetailBL tPEdorCDDetailBL = new PEdorCDDetailBL();
		if (!tPEdorCDDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tPEdorCDDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorCDDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "数据提交失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tPEdorCDDetailBL.getResult();
		return true;
	}

	/**
	 * 返回处理错误
	 * 
	 * @return: CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		PEdorCDDetailBLF tPEdorCDDetailBLF = new PEdorCDDetailBLF();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPAddressSchema tLPAddressSchema = new LPAddressSchema();

		tLPEdorItemSchema
				.decode("6120051208000031|||CD||||0002243010|||2005-12-08|2005-12-08||||||0|0|0|0|||||||||||||||");
		tLPAddressSchema
				.decode("|CD|0002243010|4|郁金香路12号4-403|210000|||||||||||13585206357|||||||||||||省劳教局|320000|320100|320101");
		// tLPEdorItemSchema.setEdorAcceptNo("");
		// tLPEdorItemSchema.setEdorType("");
		// tLPEdorItemSchema.setInsuredNo("");
		// tLPEdorItemSchema.setEdorAppDate("");
		// tLPEdorItemSchema.setEdorValiDate("");
		//
		// tLPAddressSchema.setEdorType("");
		// tLPAddressSchema.setCustomerNo("");
		// tLPAddressSchema.setAddressNo("");
		// tLPAddressSchema.setProvince("");
		// tLPAddressSchema.setCity("");
		// tLPAddressSchema.setCounty("");
		// tLPAddressSchema.setPostalAddress("");
		// tLPAddressSchema.setZipCode("");
		// tLPAddressSchema.setMobile("");
		// tLPAddressSchema.setCompanyPhone("");
		// tLPAddressSchema.setFax("");
		// tLPAddressSchema.setHomePhone("");
		// tLPAddressSchema.setEMail("");
		// tLPAddressSchema.setGrpName("");

		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.Operator = "001";
		mGlobalInput.ManageCom = "86";

		VData tVData = new VData();
		tVData.add(mGlobalInput);
		tVData.add(tLPEdorItemSchema);
		tVData.add(tLPAddressSchema);
		tPEdorCDDetailBLF.submitData(tVData, "");
	}
}
