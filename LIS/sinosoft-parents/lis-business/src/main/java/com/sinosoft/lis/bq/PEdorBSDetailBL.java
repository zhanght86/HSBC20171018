package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保单效力确认
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author pst
 * @version 1.0
 */
public class PEdorBSDetailBL {
private static Logger logger = Logger.getLogger(PEdorBSDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 全局数据 */
	TransferData mTransferData = new TransferData();
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 业务数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private String tName = "";
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中

		logger.debug("befro getInputData");
		if (!getInputData(cInputData)) {
			return false;
		}

		logger.debug("befro checkData");

		// 数据校验操作（checkdata)
		// if (!checkData())
		// {
		// return false;
		// }

		// 数据操作业务处理()
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {

		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);

		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());

		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorBSDetailBL";
			tError.functionName = "checkdata";
			tError.errorMessage = "无保全申请数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		Reflections tRef = new Reflections();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSchema rLPEdorItemSchema = new LPEdorItemSchema();
		/*
		 * 设置更新LPEdorItem的状态
		 */
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());

		tLPEdorItemSchema = tLPEdorItemDB.query().get(1);
		tRef.transFields(rLPEdorItemSchema, tLPEdorItemSchema);
		rLPEdorItemSchema.setStandbyFlag1(mLPEdorItemSchema.getStandbyFlag1());
		rLPEdorItemSchema.setStandbyFlag2(mLPEdorItemSchema.getStandbyFlag2());
		rLPEdorItemSchema.setStandbyFlag3(mLPEdorItemSchema.getStandbyFlag3());
		rLPEdorItemSchema.setModifyDate(theCurrentDate);
		rLPEdorItemSchema.setModifyTime(theCurrentTime);
		rLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		mMap.put(rLPEdorItemSchema, "UPDATE");
		return true;
	}

	private boolean prepareOutputData() {

		mResult.clear();
		mResult.add(mMap);

		return true;
	}

	public static void main(String[] args) {
		logger.debug("-------------PEdorBSDetailBL test start---------------");

		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setEdorAcceptNo("6120070305000001");
		tLPEdorItemSchema.setContNo("880000000248");
		tLPEdorItemSchema.setEdorNo("6020070313000009");
		tLPEdorItemSchema.setEdorType("PS");
		tLPEdorItemSchema.setInsuredNo("000000");
		tLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		tLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		tLPEdorItemSchema.setOperator(tG.Operator);
		tLPEdorItemSchema.setStandbyFlag1("psen");
		logger.debug(tLPEdorItemSchema.getStandbyFlag3());

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tLPEdorItemSchema);

		PEdorBSDetailBL tPEdorBSDetailBL = new PEdorBSDetailBL();

		if (!tPEdorBSDetailBL.submitData(tVData, "")) {
			logger.debug(tPEdorBSDetailBL.mErrors.getError(0).errorMessage);
		}
		logger.debug("-------------PEdorBSDetailBL test end---------------");
	}
}
