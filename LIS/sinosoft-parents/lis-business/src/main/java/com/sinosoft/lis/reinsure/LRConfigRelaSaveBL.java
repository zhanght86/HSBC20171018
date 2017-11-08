

/*
 * <p>ClassName: LRContManageBL </p>
 * <p>Description: LRContManageBL类文件 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database: Zhang Bin
 * @CreateDate：2006-07-30
 */
package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RICalDefDB;
import com.sinosoft.lis.db.RIUnionAccumulateDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIAccumulateDefSchema;
import com.sinosoft.lis.schema.RIUnionAccumulateSchema;
import com.sinosoft.lis.vschema.RIUnionAccumulateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LRConfigRelaSaveBL {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private RICalDefDB tRICalDefDB = new RICalDefDB();

	private RIUnionAccumulateSet mRIUnionAccumulateSet = new RIUnionAccumulateSet();

	/** 数据操作字符串 */
	private String strOperate;
	private MMap mMap = new MMap();

	private PubSubmit tPubSubmit = new PubSubmit();

	// 业务处理相关变量
	/** 全局数据 */

	public LRConfigRelaSaveBL() {
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
		if (!getInputData(cInputData))
			return false;

		if (!dealData()) {
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		GlobalInput globalInput = new GlobalInput();
		globalInput.ComCode = "8611";
		globalInput.Operator = "001";

		LRConfigRelaSaveBL tLRConfigRelaSaveBL = new LRConfigRelaSaveBL();
		VData tVData = new VData();

		RIAccumulateDefSchema tRIAccumulateDefSchema = new RIAccumulateDefSchema();
		tRIAccumulateDefSchema.setAccumulateDefNO("aa");
		tRIAccumulateDefSchema.setAccumulateDefName("bbbb");
		tRIAccumulateDefSchema.setDeTailFlag("01");
		tRIAccumulateDefSchema.setAccumulateMode("01");
		tRIAccumulateDefSchema.setRiskAmntFlag("01");
		tRIAccumulateDefSchema.setArithmeticID("11");

		RIUnionAccumulateSet tRIUnionAccumulateSet = new RIUnionAccumulateSet();
		RIUnionAccumulateSchema tRIUnionAccumulateSchema = new RIUnionAccumulateSchema();

		tRIUnionAccumulateSchema.setAccumulateDefNO("aa");
		tRIUnionAccumulateSchema.setAssociatedCode("ccc");
		tRIUnionAccumulateSet.add(tRIUnionAccumulateSchema);
		tVData.add(tRIAccumulateDefSchema);
		tVData.add(tRIUnionAccumulateSet);
		tVData.add(globalInput);
		PubSubmit tPubSubmit = new PubSubmit();
		tPubSubmit.submitData(tVData, "");
		try {
			tLRConfigRelaSaveBL.submitData(tVData, "INSERT");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
				// buildError("insertData", "添加数据时出现错误!");
				return false;
			}
		}
		// 对数据进行修改操作
		if (this.strOperate.equals("UPDATE")) {
			if (!updateData()) {
				// @@错误处理
				// buildError("insertData", "修改时出现错误!");
				return false;
			}
		}
		// 对数据进行删除操作
		if (this.strOperate.equals("DELETE")) {
			if (!deleteData()) {
				// @@错误处理
				// buildError("insertData", "修改时出现错误!");
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
		RIUnionAccumulateDB tRIUnionAccumulateDB = new RIUnionAccumulateDB();
		tRIUnionAccumulateDB.setAccumulateDefNO(this.mRIUnionAccumulateSet.get(
				1).getAccumulateDefNO());
		tRIUnionAccumulateDB.setUnionAccNo(this.mRIUnionAccumulateSet.get(1)
				.getUnionAccNo());
		if (tRIUnionAccumulateDB.getCount() == 0) {
			buildError("updateData", "该联合累计编号不存在!");
			return false;
		}
		String strSQL = "delete from RIUnionAccumulate where AccumulateDefNO='"
				+ mRIUnionAccumulateSet.get(1).getAccumulateDefNO()
				+ "' and UNIONACCNO='"
				+ mRIUnionAccumulateSet.get(1).getUnionAccNo() + "'";
		mMap.put(strSQL, "DELETE");

		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				// @@错误处理
				buildError("DeleteData", "删除时出现错误!");
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
		RIUnionAccumulateDB tRIUnionAccumulateDB = new RIUnionAccumulateDB();
		tRIUnionAccumulateDB.setAccumulateDefNO(this.mRIUnionAccumulateSet.get(
				1).getAccumulateDefNO());
		tRIUnionAccumulateDB.setUnionAccNo(this.mRIUnionAccumulateSet.get(1)
				.getUnionAccNo());
		if (tRIUnionAccumulateDB.getCount() == 0) {
			buildError("updateData", "该联合累计编号不存在!");
			return false;
		}

		// 插入再保计算要素值表
		String strSQL = "delete from RIUnionAccumulate where AccumulateDefNO='"
				+ mRIUnionAccumulateSet.get(1).getAccumulateDefNO()
				+ "' and UNIONACCNO='"
				+ mRIUnionAccumulateSet.get(1).getUnionAccNo() + "'";
		mMap.put(strSQL, "DELETE");
		if (mRIUnionAccumulateSet.size() > 0) {
			mMap.put(mRIUnionAccumulateSet, "INSERT");
		}

		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				// @@错误处理
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
		RIUnionAccumulateDB tRIUnionAccumulateDB = new RIUnionAccumulateDB();
		tRIUnionAccumulateDB.setAccumulateDefNO(mRIUnionAccumulateSet.get(1)
				.getAccumulateDefNO());
		tRIUnionAccumulateDB.setUnionAccNo(mRIUnionAccumulateSet.get(1)
				.getUnionAccNo());
		if (tRIUnionAccumulateDB.getCount() > 0) {
			buildError("insertData", "该联合累计编码已经存在!");
			return false;
		}
		// for(int i=1;i<=mRIUnionAccumulateSet.size();i++){
		// RIUnionAccumulateDB tRIUnionAccumulateDB= new RIUnionAccumulateDB();
		// tRIUnionAccumulateDB.setAccumulateDefNO(mRIUnionAccumulateSet.get(i).getAccumulateDefNO());
		// tRIUnionAccumulateDB.setAssociatedCode(mRIUnionAccumulateSet.get(i).getAssociatedCode());
		// tRIUnionAccumulateDB.setUnionAccNo(mRIUnionAccumulateSet.get(i).getUnionAccNo());
		// if(tRIUnionAccumulateDB.getInfo()){
		// buildError("insertData", "该再保合同编号已经存在!");
		// return false;
		// }
		// }

		mMap.put(mRIUnionAccumulateSet, "INSERT");

		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				// @@错误处理
				buildError("insertData", "保存联合累计定义时出现错误!");
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
		this.mRIUnionAccumulateSet.set((RIUnionAccumulateSet) cInputData
				.getObjectByObjectName("RIUnionAccumulateSet", 0));
		return true;
	}

	public String getResult() {
		return mRIUnionAccumulateSet.get(1).getUnionAccNo();
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
