package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全－重新出单
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author liurx
 * @version 1.0
 */
public class PEdorRNDetailBL {
private static Logger logger = Logger.getLogger(PEdorRNDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private String mEdorReasonCode = "";
	private String mEdorReason = "";
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private BqCalBase mBqCalBase = new BqCalBase();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorRNDetailBL() {

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}

		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
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
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mEdorReasonCode = mLPEdorItemSchema.getEdorReasonCode();
		mEdorReason = mLPEdorItemSchema.getEdorReason();

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

		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorRNDetailBL";
			tError.functionName = "checkdata";
			tError.errorMessage = "无保全项目数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		mLPEdorItemSchema.setEdorState("3"); // 设置状态为未录入状态
		mLPEdorItemSchema.setEdorReasonCode(mEdorReasonCode);
		mLPEdorItemSchema.setEdorReason(mEdorReason);
		mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());

		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());

		return true;
	}

	private boolean prepareOutputData() {
		mMap.put(mLPEdorItemSchema, "UPDATE");

		mResult.clear();
		mResult.add(mMap);
		mResult.add(mBqCalBase);

		return true;
	}

}
