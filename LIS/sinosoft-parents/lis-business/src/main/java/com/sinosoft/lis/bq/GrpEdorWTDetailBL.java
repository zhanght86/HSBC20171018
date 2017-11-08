package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保单遗失补发项目明细</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * ReWrite ZhangRong
 * @version 1.0
 */

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

public class GrpEdorWTDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorWTDetailBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema_in = new LPGrpEdorItemSchema();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
	private LJSGetEndorseSchema mLJSGetEndorseSchema = new LJSGetEndorseSchema();
	private LPGrpContSchema mLPGrpContSchema = new LPGrpContSchema();
	private LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LPContSet mLPContSet = new LPContSet();
	private LPPolSet mLPPolSet = new LPPolSet();

	public GrpEdorWTDetailBL() {
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
		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, "")) // 数据提交
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "GrpEdorWTDetailBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("GrpEdorWTDetailBL End PubSubmit");
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
			mLPGrpEdorItemSchema_in = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);

		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "GrpEdorWTDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mGlobalInput == null || mLPGrpEdorItemSchema_in == null) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorWTDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "输入数据有误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema_in.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema_in.getEdorType());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema_in.getGrpContNo());
		LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();

		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorWTDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "输入数据有误,LPGrpEdorItem中没有相关数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);

		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		tLPGrpEdorMainDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorMainDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		if (!tLPGrpEdorMainDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorWTDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "输入数据有误,LPGrpEdorMain中没有相关数据!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLPGrpEdorMainSchema = tLPGrpEdorMainDB.getSchema();

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 设置批改补退费表，产生犹豫期退保的工本费用
		mLJSGetEndorseSchema.setGetNoticeNo(mLPGrpEdorItemSchema.getEdorNo()); // 给付通知书号码
		mLJSGetEndorseSchema.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
		mLJSGetEndorseSchema.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		mLJSGetEndorseSchema.setPolNo("000000");
		mLJSGetEndorseSchema.setFeeOperationType(mLPGrpEdorItemSchema
				.getEdorType());
		mLJSGetEndorseSchema.setGetDate(mLPGrpEdorItemSchema.getEdorValiDate());
		mLJSGetEndorseSchema.setGetMoney(mLPGrpEdorItemSchema_in.getGetMoney());
		mLJSGetEndorseSchema.setFeeOperationType("WT");
		BqCalBL tBqCalBl = new BqCalBL();
		String feeFinaType = tBqCalBl.getFinType("WT", "GB",
				mLPGrpEdorItemSchema.getGrpContNo());
		if (feeFinaType.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.errorMessage = "在LDCode1中缺少保全财务接口转换类型编码!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLJSGetEndorseSchema.setFeeFinaType(feeFinaType);
		mLJSGetEndorseSchema.setPayPlanCode("00000000"); // 无作用
		mLJSGetEndorseSchema.setDutyCode("0"); // 无作用，但一定要，转ljagetendorse时非空
		mLJSGetEndorseSchema.setOtherNo(mLPGrpEdorItemSchema.getEdorNo()); // 无作用
		mLJSGetEndorseSchema.setOtherNoType("3");
		mLJSGetEndorseSchema.setGetFlag("0");
		mLJSGetEndorseSchema.setOperator(mGlobalInput.Operator);
		mLJSGetEndorseSchema.setManageCom(mGlobalInput.ManageCom);
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (tLCGrpContDB.getInfo()) {
			LCGrpContSchema tLCGrpContSchema = tLCGrpContDB.getSchema();
			Reflections ref = new Reflections();
			ref.transFields(mLPGrpContSchema, tLCGrpContSchema);
			mLPGrpContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
			mLPGrpContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());

			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
			tLCGrpPolDB.setGrpContNo(tLCGrpContSchema.getGrpContNo());
			tLCGrpPolSet = tLCGrpPolDB.query();
			for (int j = 0; j < tLCGrpPolSet.size(); j++) {
				LCGrpPolSchema tLCGrpPolSchema = tLCGrpPolSet.get(j + 1);
				LPGrpPolSchema tLPGrpPolSchema = new LPGrpPolSchema();
				ref.transFields(tLPGrpPolSchema, tLCGrpPolSchema);
				tLPGrpPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
				tLPGrpPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
				mLPGrpPolSet.add(tLPGrpPolSchema);
			}

			mLJSGetEndorseSchema.setAgentCode(tLCGrpContDB.getAgentCode());
			mLJSGetEndorseSchema.setAgentCom(tLCGrpContDB.getAgentCom());
			mLJSGetEndorseSchema.setAgentGroup(tLCGrpContDB.getAgentGroup());
			mLJSGetEndorseSchema.setAgentType(tLCGrpContDB.getAgentType());
		} else {
			CError tError = new CError();
			tError.errorMessage = "不存在集体合同号为"
					+ mLPGrpEdorItemSchema.getGrpContNo() + "的合同!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLJSGetEndorseSchema.setMakeDate(PubFun.getCurrentDate());
		mLJSGetEndorseSchema.setMakeTime(PubFun.getCurrentTime());
		mLJSGetEndorseSchema.setModifyDate(PubFun.getCurrentDate());
		mLJSGetEndorseSchema.setModifyTime(PubFun.getCurrentTime());

		mLPGrpEdorItemSchema.setGetMoney(mLJSGetEndorseSchema.getGetMoney());
		mLPGrpEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
		mLPGrpEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
		mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());

		// 在P表中不存集体下个个人合同和险种
		// LCContDB tLCContDB = new LCContDB();
		// tLCContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		// LCContSet tLCContSet = tLCContDB.query();
		// for (int i = 0; i < tLCContSet.size(); i++)
		// {
		// Reflections ref = new Reflections();
		// LCContSchema tLCContSchema = tLCContSet.get(i + 1);
		// LPContSchema tLPContSchema=new LPContSchema();
		// ref.transFields(tLPContSchema, tLCContSchema);
		// tLPContSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		// tLPContSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		// mLPContSet.add(tLPContSchema);
		//
		// LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		// ref.transFields(tLPEdorMainSchema, mLPGrpEdorMainSchema);
		// tLPEdorMainSchema.setContNo(tLCContSchema.getContNo());
		// mLPEdorMainSet.add(tLPEdorMainSchema);
		//
		// LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		// ref.transFields(tLPEdorItemSchema, mLPGrpEdorItemSchema);
		// tLPEdorItemSchema.setContNo(tLCContSchema.getContNo());
		// tLPEdorItemSchema.setInsuredNo("000000");
		// tLPEdorItemSchema.setPolNo("000000");
		// tLPEdorItemSchema.setEdorState("1");
		// mLPEdorItemSet.add(tLPEdorItemSchema);
		//
		// LCPolDB tLCPolDB=new LCPolDB();
		// LCPolSet tLCPolSet=new LCPolSet();
		// tLCPolDB.setContNo(tLCContSchema.getContNo());
		// tLCPolSet=tLCPolDB.query();
		// for(int j=0;j<tLCPolSet.size();j++){
		// LCPolSchema tLCPolSchema=tLCPolSet.get(j+1);
		// LPPolSchema tLPPolSchema=new LPPolSchema();
		// ref.transFields(tLPPolSchema, tLCPolSchema);
		// tLPPolSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		// tLPPolSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		// mLPPolSet.add(tLPPolSchema);
		// }
		//
		// }

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareData() {
		// mMap.put(mLPEdorMainSet,"DELETE&INSERT");
		// mMap.put(mLPEdorItemSet,"DELETE&INSERT");
		// mMap.put(mLPContSet,"DELETE&INSERT");
		// mMap.put(mLPPolSet,"DELETE&INSERT");

		mMap.put(mLJSGetEndorseSchema, "DELETE&INSERT");
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");
		mMap.put(mLPGrpContSchema, "DELETE&INSERT");
		mMap.put(mLPGrpPolSet, "DELETE&INSERT");
		mResult.clear();
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

}
