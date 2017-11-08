

/*
 * <p>ClassName: LRContManageBL </p>
 * <p>Description: LRContManageBL类文件 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database: Zhang Bin
 * @CreateDate：2006-07-30
 */
package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIBarGainInfoDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIBarGainInfoSchema;
import com.sinosoft.lis.schema.RIBarGainSignerSchema;
import com.sinosoft.lis.schema.RICalFactorValueSchema;
import com.sinosoft.lis.vschema.RIBarGainSignerSet;
import com.sinosoft.lis.vschema.RICalFactorValueSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LRContManageBL {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private TransferData mTransferData = new TransferData();

	private RIBarGainInfoSchema mRIBarGainInfoSchema = new RIBarGainInfoSchema();
	private RIBarGainSignerSet mRIBarGainSignerSet = new RIBarGainSignerSet();
	private RICalFactorValueSet mRICalFactorValueSet = new RICalFactorValueSet();
	/** 数据操作字符串 */
	private String strOperate;
	// private String mModType;
	private MMap mMap = new MMap();

	private PubSubmit tPubSubmit = new PubSubmit();

	// 业务处理相关变量
	/** 全局数据 */
	public LRContManageBL() {
	}

	/**
	 * 提交数据处理方法
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.strOperate = cOperate;
		if (strOperate.equals("")) {
			buildError("verifyOperate", "不支持的操作字符串");
			return false;
		}
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		GlobalInput globalInput = new GlobalInput();
		globalInput.ComCode = "86";
		globalInput.Operator = "001";
		LRContManageBL tLRContManageBL = new LRContManageBL();
		VData vData = new VData();

		RIBarGainInfoSchema mRIBarGainInfoSchema = new RIBarGainInfoSchema();
		RIBarGainSignerSet mRIBarGainSignerSet = new RIBarGainSignerSet();
		RIBarGainSignerSchema mRIBarGainSignerSchema = new RIBarGainSignerSchema();
		RIBarGainSignerSchema tRIBarGainSignerSchema = new RIBarGainSignerSchema();
		RIBarGainSignerSchema SRIBarGainSignerSchema = new RIBarGainSignerSchema();

		System.out.println("开始进行获取数据的操作！！！");
		mRIBarGainInfoSchema.setRIContNo("C000000004");
		mRIBarGainInfoSchema.setContType("F");
		mRIBarGainInfoSchema.setRiskType("e");
		// mRIBarGainInfoSchema.setReInsuranceType("2007-6-11");
		mRIBarGainInfoSchema.setCValiDate("2007-6-12");
		mRIBarGainInfoSchema.setEndDate("2007-6-27");
		mRIBarGainInfoSchema.setOperator("li");
		mRIBarGainInfoSchema.setMakeDate("2007-6-14");
		mRIBarGainInfoSchema.setMakeTime("11:50:00");
		mRIBarGainInfoSchema.setRIContName("12121888888888823");

		// 手动输入鉴定人信息
		mRIBarGainSignerSchema.setRIContNo("C000000004");
		mRIBarGainSignerSchema.setRelaName("g");
		mRIBarGainSignerSchema.setReComCode("1");
		mRIBarGainSignerSchema.setDuty("1");
		mRIBarGainSignerSchema.setRelaTel("01025463254");
		mRIBarGainSignerSchema.setMobileTel("13245632222");
		mRIBarGainSignerSchema.setFaxNo("01033254658");
		mRIBarGainSignerSchema.setEmail("liyang@163.com");
		mRIBarGainSignerSchema.setMakeDate("2007-6-14");
		mRIBarGainSignerSchema.setMakeTime("2007-6-14");
		mRIBarGainSignerSchema.setOperator("li");
		mRIBarGainSignerSchema.setRelaCode("12");

		mRIBarGainSignerSet.add(mRIBarGainSignerSchema);

		tRIBarGainSignerSchema.setRIContNo("C000000004");
		tRIBarGainSignerSchema.setRelaName("5");
		tRIBarGainSignerSchema.setReComCode("9");
		tRIBarGainSignerSchema.setDuty("5");
		tRIBarGainSignerSchema.setRelaTel("01025463254");
		tRIBarGainSignerSchema.setMobileTel("13245632222");
		tRIBarGainSignerSchema.setFaxNo("01033254658");
		tRIBarGainSignerSchema.setEmail("liyang@163.com");
		tRIBarGainSignerSchema.setMakeDate("2007-6-18");
		tRIBarGainSignerSchema.setMakeTime("2007-6-18");
		tRIBarGainSignerSchema.setOperator("li");
		tRIBarGainSignerSchema.setRelaCode("22");

		mRIBarGainSignerSet.add(tRIBarGainSignerSchema);

		// 3
		SRIBarGainSignerSchema.setRIContNo("C000000004");
		SRIBarGainSignerSchema.setRelaName("3");
		SRIBarGainSignerSchema.setReComCode("4");
		SRIBarGainSignerSchema.setDuty("5");
		SRIBarGainSignerSchema.setRelaTel("01025463254");
		SRIBarGainSignerSchema.setMobileTel("13245632222");
		SRIBarGainSignerSchema.setFaxNo("01033254658");
		SRIBarGainSignerSchema.setEmail("liyang@163.com");

		SRIBarGainSignerSchema.setMakeDate("2007-6-18");
		SRIBarGainSignerSchema.setMakeTime("2007-6-18");
		SRIBarGainSignerSchema.setOperator("li");
		SRIBarGainSignerSchema.setRelaCode("32");

		mRIBarGainSignerSet.add(SRIBarGainSignerSchema);

		RICalFactorValueSchema tRICalFactorValueSchema = new RICalFactorValueSchema();
		RICalFactorValueSet tRICalFactorValueSet = new RICalFactorValueSet();
		tRICalFactorValueSchema.setReContCode("C000000001");
		tRICalFactorValueSchema.setRIPreceptNo("S000000000");
		tRICalFactorValueSchema.setFactorCode("123545");
		tRICalFactorValueSchema.setMakeDate("2007-1-8");
		tRICalFactorValueSchema.setMakeTime("10:30");
		tRICalFactorValueSchema.setModifyDate("2007-1-8");
		tRICalFactorValueSchema.setModifyTime("10:30:00");
		tRICalFactorValueSchema.setOperator("123");
		tRICalFactorValueSchema.setManageCom("456");
		tRICalFactorValueSet.add(tRICalFactorValueSchema);

		// 将输入信息全部封装到vdata中
		vData.addElement(globalInput);
		vData.addElement(mRIBarGainInfoSchema);
		vData.addElement(mRIBarGainSignerSet);
		vData.addElement(tRICalFactorValueSet);
		TransferData tTransferData = new TransferData();
		String tModType = "1";
		String tModRIContNo = "C0001";
		tTransferData.setNameAndValue("ModType", tModType);
		tTransferData.setNameAndValue("ModRIContNo", tModRIContNo);
		vData.addElement(tTransferData);

		try {
			tLRContManageBL.submitData(vData, "INSERT");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		System.out.println("完毕");
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(mMap);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 进行插入数据
		if (this.strOperate.equals("INSERT")) {
			if (!insertData()) {
				// @@错误处理
				return false;
			}
		}
		// 对数据进行修改操作
		if (this.strOperate.equals("UPDATE")) {
			if (!updateData()) {
				// @@错误处理
				return false;
			}
		}
		// 对数据进行删除操作
		if (this.strOperate.equals("DELETE")) {
			if (!deleteData()) {
				// @@错误处理
				return false;
			}
		}
		return true;
	}

	/**
	 * deleteData
	 * 
	 * @return boolean
	 */
	private boolean deleteData() {
		RIBarGainInfoDB tRIBarGainInfoDB = new RIBarGainInfoDB();
		tRIBarGainInfoDB.setRIContNo(mRIBarGainInfoSchema.getRIContNo());
		if (!tRIBarGainInfoDB.getInfo()) {
			buildError("deleteData", "该再保合同编号不存在!");
			return false;
		}
		String strSQL = "select 1 from dual where exists (select * from rirecordtrace where RIPreceptNo in (select RIPreceptNo from RIPrecept where RIContNo='"
				+ mRIBarGainInfoSchema.getRIContNo() + "')) ";
		ExeSQL tExeSQL = new ExeSQL();
		String str = tExeSQL.getOneValue(strSQL);
		if (str != null && !str.equals("")) {
			buildError("deleteData", "该再保合同正在被引用,不能删除!");
			return false;
		}

		// String strSQL8=
		// " delete from RIAssociateFeeTable a where a.ripreceptno in ( select RIPreceptNo from riprecept a where a.RIContNo='"+
		// mRIBarGainInfoSchema.getRIContNo() + "') ";
		// mMap.put(strSQL8, "DELETE");
		String strSQL1 = "delete from RIbargaininfo where RIContNo='"
				+ mRIBarGainInfoSchema.getRIContNo() + "'";
		mMap.put(strSQL1, "DELETE");
		String strSQL2 = "delete from RICalFactorValue where ReContCode='"
				+ mRIBarGainInfoSchema.getRIContNo() + "'";
		mMap.put(strSQL2, "DELETE");
		String strSQL3 = "delete from RIPrecept where RIContNo='"
				+ mRIBarGainInfoSchema.getRIContNo() + "'";
		mMap.put(strSQL3, "DELETE");
		String strSQL4 = "delete from RIDivisionLineDef where RIContNo='"
				+ mRIBarGainInfoSchema.getRIContNo() + "'";
		mMap.put(strSQL4, "DELETE");
		String strSQL5 = "delete from RIIncomeCompany where RIContNo='"
				+ mRIBarGainInfoSchema.getRIContNo() + "'";
		mMap.put(strSQL5, "DELETE");
		String strSQL6 = "delete from RIRiskDivide where RIContNo='"
				+ mRIBarGainInfoSchema.getRIContNo() + "'";
		mMap.put(strSQL6, "DELETE");
		String strSQL7 = " delete from RIBarGainSigner where RIContNo='"
				+ mRIBarGainInfoSchema.getRIContNo() + "'";
		mMap.put(strSQL7, "DELETE");

		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				// @@错误处理
				buildError("updateData", "修改时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;
		return true;
	}

	/**
	 * updateData
	 * 
	 * @return boolean
	 */
	private boolean updateData() {
		RIBarGainInfoDB tRIBarGainInfoDB = new RIBarGainInfoDB();
		tRIBarGainInfoDB.setRIContNo(this.mRIBarGainInfoSchema.getRIContNo());

		if (!tRIBarGainInfoDB.getInfo()) {
			buildError("updateData", "该再保合同编号不存在!");
			return false;
		}

		RIBarGainInfoSchema tRIBarGainInfoSchema = tRIBarGainInfoDB.getSchema();
		tRIBarGainInfoSchema.setRIContNo(mRIBarGainInfoSchema.getRIContNo());
		tRIBarGainInfoSchema
				.setRIContName(mRIBarGainInfoSchema.getRIContName());
		tRIBarGainInfoSchema.setContType(mRIBarGainInfoSchema.getContType());
		tRIBarGainInfoSchema.setRiskType(mRIBarGainInfoSchema.getRiskType());
		// tRIBarGainInfoSchema.setReInsuranceType(mRIBarGainInfoSchema.getReInsuranceType());
		tRIBarGainInfoSchema.setCValiDate(mRIBarGainInfoSchema.getCValiDate());
		tRIBarGainInfoSchema.setEndDate(mRIBarGainInfoSchema.getEndDate());
		tRIBarGainInfoSchema.setState(mRIBarGainInfoSchema.getState());
		tRIBarGainInfoSchema.setMatchMode(mRIBarGainInfoSchema.getMatchMode());
		mMap.put(tRIBarGainInfoSchema, "UPDATE");
		PubFun tPubFun = new PubFun();
		String currentDate = tPubFun.getCurrentDate();
		String currentTime = tPubFun.getCurrentTime();

		// 合同签订人
		String strSQL = "delete from RIBarGainSigner where RIContNo='"
				+ mRIBarGainInfoSchema.getRIContNo() + "'";
		mMap.put(strSQL, "DELETE");

		if (mRIBarGainSignerSet.size() > 0) {
			RIBarGainSignerSchema tRIBarGainSignerSchema = new RIBarGainSignerSchema();
			for (int i = 1; i <= mRIBarGainSignerSet.size(); i++) {
				tRIBarGainSignerSchema = mRIBarGainSignerSet.get(i);
				tRIBarGainSignerSchema.setSerialNo(PubFun1.CreateMaxNo(
						"RIBARGAINSIGNERSN", 10));
				tRIBarGainSignerSchema.setRelaCode(PubFun1.CreateMaxNo(
						"RIBARGAINSIGNERRELA", 10));
				tRIBarGainSignerSchema.setOperator(globalInput.Operator);
				tRIBarGainSignerSchema.setMakeDate(currentDate);
				tRIBarGainSignerSchema.setMakeTime(currentDate);
			}
		}
		if (mRIBarGainSignerSet.size() > 0) {
			mMap.put(mRIBarGainSignerSet, "INSERT");
		}
		String strSQL2 = "delete from RICalFactorValue where ReContCode='"
				+ mRIBarGainInfoSchema.getRIContNo() + "'";
		mMap.put(strSQL2, "DELETE");
		if (mRICalFactorValueSet.size() > 0) {
			RICalFactorValueSchema tmRICalFactorValueSchema = new RICalFactorValueSchema();
			for (int i = 1; i <= mRICalFactorValueSet.size(); i++) {
				tmRICalFactorValueSchema = mRICalFactorValueSet.get(i);
				tmRICalFactorValueSchema.setMakeDate(currentDate);
				tmRICalFactorValueSchema.setMakeTime(currentTime);
				tmRICalFactorValueSchema.setModifyDate(currentDate);
				tmRICalFactorValueSchema.setModifyTime(currentTime);
				tmRICalFactorValueSchema.setOperator(globalInput.Operator);
				tmRICalFactorValueSchema.setManageCom(globalInput.ComCode);
			}
		}
		if (mRICalFactorValueSet.size() > 0) {
			mMap.put(mRICalFactorValueSet, "INSERT");
		}
		String modRIContNo = (String) mTransferData
				.getValueByName("ModRIContNo");
		RIBarGainInfoSchema uRIBarGainInfoSchema = new RIBarGainInfoSchema();
		RIBarGainInfoDB updateRIBarGainInfoDB = new RIBarGainInfoDB();

		// if(mModType.equals("1")){
		// updateRIBarGainInfoDB.setRIContNo(modRIContNo);
		// if(!updateRIBarGainInfoDB.getInfo()){
		// buildError("insertData", "未找到要修改的合同!");
		// return false;
		// }
		// uRIBarGainInfoSchema.setSchema(updateRIBarGainInfoDB.getSchema());
		// uRIBarGainInfoSchema.setEndDate(PubFun.getLastDate(mRIBarGainInfoSchema.getCValiDate(),-1,"D"));
		// mMap.put(uRIBarGainInfoSchema,"UPDATE");
		// }

		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("updateData", "修改信息时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;
		return true;
	}

	/**
	 * insertData
	 * 
	 * @return boolean
	 */
	private boolean insertData() {
		RIBarGainInfoDB tRIBarGainInfoDB = new RIBarGainInfoDB();
		tRIBarGainInfoDB.setRIContNo(mRIBarGainInfoSchema.getRIContNo());
		if (tRIBarGainInfoDB.getInfo()) {
			buildError("insertData", "该再保合同编号已经存在!");
			return false;
		}
		PubFun tPubFun = new PubFun();
		String currentDate = tPubFun.getCurrentDate();
		String currentTime = tPubFun.getCurrentTime();
		mRIBarGainInfoSchema.setOperator(globalInput.Operator);
		mRIBarGainInfoSchema.setMakeDate(currentDate);
		mRIBarGainInfoSchema.setMakeTime(currentTime);
		RIBarGainSignerSchema tRIBarGainSignerSchema;
		int length = mRIBarGainSignerSet.size();
		for (int i = 1; i <= length; i++) {
			tRIBarGainSignerSchema = mRIBarGainSignerSet.get(i);
			tRIBarGainSignerSchema.setRIContNo(mRIBarGainInfoSchema
					.getRIContNo());
			tRIBarGainSignerSchema.setSerialNo(PubFun1.CreateMaxNo(
					"RIBARGAINSIGNERSN", 10));
			tRIBarGainSignerSchema.setRelaCode(PubFun1.CreateMaxNo(
					"RIBARGAINSIGNERRELA", 10));
			tRIBarGainSignerSchema.setOperator(globalInput.Operator);
			tRIBarGainSignerSchema.setMakeDate(currentDate);
			tRIBarGainSignerSchema.setMakeTime(currentDate);
		}
		RICalFactorValueSchema tRICalFactorValueSchema;
		int length2 = mRICalFactorValueSet.size();
		for (int i = 1; i <= length2; i++) {
			tRICalFactorValueSchema = mRICalFactorValueSet.get(i);
			tRICalFactorValueSchema.setReContCode(mRIBarGainInfoSchema
					.getRIContNo());
			tRICalFactorValueSchema.setRIPreceptNo("S000000000");
			tRICalFactorValueSchema.setMakeDate(currentDate);
			tRICalFactorValueSchema.setMakeTime(currentTime);
			tRICalFactorValueSchema.setModifyDate(currentDate);
			tRICalFactorValueSchema.setModifyTime(currentTime);
			tRICalFactorValueSchema.setOperator(globalInput.Operator);
			tRICalFactorValueSchema.setManageCom(globalInput.ComCode);
		}
		String modRIContNo = (String) mTransferData
				.getValueByName("ModRIContNo");
		RIBarGainInfoSchema tRIBarGainInfoSchema = new RIBarGainInfoSchema();
		RIBarGainInfoDB updateRIBarGainInfoDB = new RIBarGainInfoDB();
		// if(mModType.equals("1")){
		// updateRIBarGainInfoDB.setRIContNo(modRIContNo);
		// if(!updateRIBarGainInfoDB.getInfo()){
		// buildError("insertData", "未找到要修改的合同!");
		// return false;
		// }
		// tRIBarGainInfoSchema.setSchema(updateRIBarGainInfoDB.getSchema());
		// tRIBarGainInfoSchema.setEndDate(PubFun.getLastDate(mRIBarGainInfoSchema.getCValiDate(),-1,"D"));
		// mMap.put(tRIBarGainInfoSchema,"UPDATE");
		// }
		// 插入基本信息
		mMap.put(mRIBarGainInfoSchema, "INSERT");
		// 插入鉴定人信息
		mMap.put(mRIBarGainSignerSet, "INSERT");
		// 插入合同级要素信息
		mMap.put(mRICalFactorValueSet, "INSERT");

		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存再保合同信息时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		this.mRIBarGainInfoSchema.setSchema((RIBarGainInfoSchema) cInputData
				.getObjectByObjectName("RIBarGainInfoSchema", 0));
		this.mRIBarGainSignerSet.set((RIBarGainSignerSet) cInputData
				.getObjectByObjectName("RIBarGainSignerSet", 0));
		this.mRICalFactorValueSet.set((RICalFactorValueSet) cInputData
				.getObjectByObjectName("RICalFactorValueSet", 0));
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		// this.mModType = (String)mTransferData.getValueByName("ModType");
		return true;
	}

	public VData getResult() {
		TransferData t = new TransferData();
		t.setNameAndValue("ContNo", mRIBarGainInfoSchema.getRIContNo());
		this.mResult.add(t);
		return this.mResult;
	}

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "ReComManageBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
