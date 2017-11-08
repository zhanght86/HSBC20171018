package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保单遗失补发项目明细</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Alex
 * @version 1.0
 */

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LPContStateSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PEdorRCDetailBL {
private static Logger logger = Logger.getLogger(PEdorRCDetailBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate = null;
	/** 校验 */
	private BqCalBase mBqCalBase = new BqCalBase();
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorItemSchema mLPEdorItemSchema_input = new LPEdorItemSchema();
	private LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LPContSchema mLPContSchema = new LPContSchema();
	private Reflections mReflections = new Reflections();
	private String mRemindMode = "";

	public PEdorRCDetailBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareData()) {
			return false;
		}

		 logger.debug("PEdorRCDetailBL End PubSubmit");
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema_input = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mRemindMode = mLPEdorItemSchema_input.getStandbyFlag1();

		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "PEdorLRDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError tError = new CError();
			tError.moduleName = "PEdorLRDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "输入数据有误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		Reflections tReflections = new Reflections();



		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema_input.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema_input.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema_input.getContNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema_input.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
			// @@错误处理
			mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorLRDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "无保全申请数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPEdorItemSchema = tLPEdorItemSet.get(1);
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		mLPEdorItemSchema.setStandbyFlag1(mRemindMode);

		// 生成本次新的LPCont
		mLPContSchema = new LPContSchema();
		// 将C表数据拷贝到P表
		tReflections.transFields(mLPContSchema, mLCContSchema);
		mLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		mLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		mLPContSchema.setXQremindflag(mRemindMode);
		mLPContSchema.setModifyDate(PubFun.getCurrentDate());
		mLPContSchema.setModifyTime(PubFun.getCurrentTime());
		mMap.put(mLPContSchema, "DELETE&INSERT");



		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareData() {
		mMap.put(mLPEdorItemSchema, "UPDATE");
		mBqCalBase.setContNo(mLPEdorItemSchema.getContNo());
		mResult.clear();
		mResult.add(mBqCalBase);
		mResult.add(mMap);
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


	/**
	 * 生成交退费记录
	 * 
	 * @return
	 */

}
