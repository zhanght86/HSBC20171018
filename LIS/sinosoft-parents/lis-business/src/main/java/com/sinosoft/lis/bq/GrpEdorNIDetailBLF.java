package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 */
public class GrpEdorNIDetailBLF implements EdorDetail {
private static Logger logger = Logger.getLogger(GrpEdorNIDetailBLF.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */

	public GrpEdorNIDetailBLF() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		// 数据准备操作
		if (!dealData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpEdorNIDetailBLF";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	private boolean getInputData() {
		return true;
	}

	private boolean checkData() {
		return true;
	}

	private boolean dealData() {
		GrpEdorNIDetailBL tGrpEdorNIDetailBL = new GrpEdorNIDetailBL();
		if (!tGrpEdorNIDetailBL.submitData(mInputData, mOperate)) {
			this.mErrors.copyAllErrors(tGrpEdorNIDetailBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpEdorNIDetailBLF";
			tError.functionName = "dealData";
			tError.errorMessage = "加人保全明细保存失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mResult.clear();
		mResult = tGrpEdorNIDetailBL.getResult();
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		VData tVD = new VData();
		GlobalInput tGI = new GlobalInput();
		tGI.Operator = "lee";
		tGI.ManageCom = "86";
		LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
		tLPGrpEdorItemSchema.setEdorNo("6020051212000001");
		tLPGrpEdorItemSchema.setEdorAcceptNo("6120051009000002");
		tLPGrpEdorItemSchema.setEdorType("NI");
		tLPGrpEdorItemSchema.setGrpContNo("240110000000243");
		tVD.add(tGI);
		tVD.add(tLPGrpEdorItemSchema);
		GrpEdorNIDetailBLF tNI = new GrpEdorNIDetailBLF();
		tNI.submitData(tVD, "");

	}
}
