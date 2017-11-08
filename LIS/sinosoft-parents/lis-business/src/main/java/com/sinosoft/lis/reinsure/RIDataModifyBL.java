

/**
 * <p>ClassName: RIDataModifyBL.java </p>
 * <p>Description: 业务数据调整 </p>
 * <p>Copyright: Copyright (c) 2009 </p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2011-07-30
 */

//包名
//package com.sinosoft.lis.config;
package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIPolRecordDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIDataModifyLogSchema;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.vschema.RIDataModifyLogSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class RIDataModifyBL {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private RIPolRecordSchema mRIPolRecordSchema = new RIPolRecordSchema();
	private RIDataModifyLogSet mRIDataModifyLogSet = new RIDataModifyLogSet();

	private MMap map = new MMap();

	public RIDataModifyBL() {

	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = cInputData;
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		System.out.println("Start RIDataModifyBL Submit...");

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, null)) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "修改信息时出现错误!");
				return false;
			}
		}

		mInputData = null;
		System.out.println("End RIDataModifyBL Submit...");
		return true;

	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 */
	private boolean getInputData(VData cInputData) {
		this.mRIPolRecordSchema.setSchema((RIPolRecordSchema) cInputData
				.getObjectByObjectName("RIPolRecordSchema", 0));
		this.mRIDataModifyLogSet.set((RIDataModifyLogSet) cInputData
				.getObjectByObjectName("RIDataModifyLogSet", 0));
		return true;
	}

	private boolean checkData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {

		RIPolRecordDB tRIPolRecordDB = new RIPolRecordDB();
		tRIPolRecordDB.setSchema(mRIPolRecordSchema);
		tRIPolRecordDB.getInfo();
		mRIPolRecordSchema.setSchema(tRIPolRecordDB);

		for (int i = 1; i <= mRIDataModifyLogSet.size(); i++) {
			RIDataModifyLogSchema tRIDataModifyLogSchema = mRIDataModifyLogSet
					.get(i);
			tRIDataModifyLogSchema.setSerialNo(PubFun1.CreateMaxNo(
					"RIDATAMODIFTLOG", 9));
			tRIDataModifyLogSchema
					.setRIContNo(mRIPolRecordSchema.getRIContNo());
			tRIDataModifyLogSchema.setInsuredNo(mRIPolRecordSchema
					.getInsuredNo());
			tRIDataModifyLogSchema.setAccumulateDefNo(mRIPolRecordSchema
					.getAccumulateDefNO());
			tRIDataModifyLogSchema
					.setRiskCode(mRIPolRecordSchema.getRiskCode());
			tRIDataModifyLogSchema
					.setDutyCode(mRIPolRecordSchema.getDutyCode());

			mRIPolRecordSchema.setV(tRIDataModifyLogSchema.getClumnCode(),
					tRIDataModifyLogSchema.getClumnModifyValue());
		}

		map.put(mRIPolRecordSchema, "UPDATE");
		map.put(mRIDataModifyLogSet, "INSERT");

		return true;
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(map);
		} catch (Exception ex) {
			// @@错误处理
			buildError("prepareData", "在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}

	public static void main(String[] args) {

	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "RIDataModifyBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}
