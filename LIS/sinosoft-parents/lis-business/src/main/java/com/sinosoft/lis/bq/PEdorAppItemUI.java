package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: UI功能类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 * @date 2002-09-25
 */
public class PEdorAppItemUI implements BusinessService{
private static Logger logger = Logger.getLogger(PEdorAppItemUI.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	// @Constructor
	public PEdorAppItemUI() {
	}

	// @Main
	public static void main(String[] args) {

		GlobalInput mGlobalInput = new GlobalInput();
		mGlobalInput.ManageCom = "86";
		mGlobalInput.Operator = "001";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("DisplayType", "1");
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemSchema.setEdorAcceptNo("86000000002029");
		tLPEdorItemSchema.setGrpContNo("");
		tLPEdorItemSchema.setDisplayType("1");
		tLPEdorItemSchema.setEdorType("CM");
		tLPEdorItemSchema.setContNo("");
		tLPEdorItemSchema.setInsuredNo("000000");
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setEdorValiDate("2005-07-14");
		tLPEdorItemSchema.setEdorAppDate("2005-07-14");

		mLPEdorItemSet.add(tLPEdorItemSchema);

		VData tVData = new VData();
		tVData.add(mGlobalInput);
		tVData.add(mLPEdorItemSet);
		tVData.add(tTransferData);

		PEdorAppItemUI tPEdorAppItemUI = new PEdorAppItemUI();
		tPEdorAppItemUI.submitData(tVData, "INSERT||EDORITEM");

	}

	// @Method
	/**
	 * 数据提交方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 数据操作字符串拷贝到本类中
		this.mOperate = cOperate;

		PEdorAppItemBL tPEdorAppItemBL = new PEdorAppItemBL();

		logger.debug("---UI BEGIN---");
		if (tPEdorAppItemBL.submitData(cInputData, mOperate) == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPEdorAppItemBL.mErrors);
			return false;
		} else {
			mResult = tPEdorAppItemBL.getResult();
		}
		logger.debug(mErrors.toString());
		return true;
	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */
	public VData getResult() {
		return mResult;
	}
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
}
