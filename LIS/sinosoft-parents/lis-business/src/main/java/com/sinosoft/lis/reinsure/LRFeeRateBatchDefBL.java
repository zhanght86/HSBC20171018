

/*
 * <p>ClassName: LRContManageBL </p>
 * <p>Description: LRContManageBL类文件 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database: Zhang Bin
 * @CreateDate：2008-06-05
 */
package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIFeeRateTableTraceDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIFeeRateTableDefSchema;
import com.sinosoft.lis.schema.RIFeeRateTableTraceSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LRFeeRateBatchDefBL {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private RIFeeRateTableTraceSchema mRIFeeRateTableTraceSchema = new RIFeeRateTableTraceSchema();

	private RIFeeRateTableTraceSchema mRIFeeRateTableTraceSchema2 = new RIFeeRateTableTraceSchema();
	/** 数据操作字符串 */
	private String strOperate;
	private MMap mMap = new MMap();
	private PubSubmit tPubSubmit = new PubSubmit();

	public LRFeeRateBatchDefBL() {
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
		try {
			RIFeeRateTableDefSchema tRIFeeRateTableDefSchema = new RIFeeRateTableDefSchema();
			tRIFeeRateTableDefSchema.setFeeTableNo("F000003");
			tRIFeeRateTableDefSchema.setFeeTableType("01");
			tRIFeeRateTableDefSchema.setMakeDate("2008-1-1");
			tRIFeeRateTableDefSchema.setMakeTime("00:00:00");
			tRIFeeRateTableDefSchema.setOperator("01");
			LRFeeRateBatchDefBL tLRFeeRateBatchDefBL = new LRFeeRateBatchDefBL();
			VData tVData = new VData();
			GlobalInput globalInput = new GlobalInput();
			globalInput.Operator = "001";
			tVData.addElement(tRIFeeRateTableDefSchema);
			tVData.addElement(globalInput);
			tLRFeeRateBatchDefBL.submitData(tVData, "INSERT");
			System.out.println(" ex: "
					+ tLRFeeRateBatchDefBL.mErrors.getFirstError());
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
			tError.moduleName = "LRFeeRateDefBL";
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
				return false;
			}
		}
		// 对数据进行修改操作
		if (this.strOperate.equals("UPDATE")) {
			if (!updateData()) {
				return false;
			}
		}
		// 对数据进行删除操作
		if (this.strOperate.equals("DELETE")) {
			if (!deleteData()) {
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
		RIFeeRateTableTraceDB tRIFeeRateTableTraceDB = new RIFeeRateTableTraceDB();
		tRIFeeRateTableTraceDB.setFeeTableNo(mRIFeeRateTableTraceSchema
				.getFeeTableNo());
		tRIFeeRateTableTraceDB.setBatchNo(mRIFeeRateTableTraceSchema
				.getBatchNo());
		if (!tRIFeeRateTableTraceDB.getInfo()) {
			buildError("insertData", "该费率表批次定义不存在!");
			return false;
		}
		String strSQL1 = "";
		String strSQL2 = "";
		strSQL1 = "delete from RIFeeRateTableTrace a where a.FeeTableNo='"
				+ mRIFeeRateTableTraceSchema.getFeeTableNo()
				+ "' and a.BatchNo='" + mRIFeeRateTableTraceSchema.getBatchNo()
				+ "' ";
		strSQL2 = "delete from RIFeeRateTable a where a.FeeTableNo='"
				+ mRIFeeRateTableTraceSchema.getFeeTableNo()
				+ "' and a.BatchNo='" + mRIFeeRateTableTraceSchema.getBatchNo()
				+ "' ";
		mMap.put(strSQL1, "DELETE");
		mMap.put(strSQL2, "DELETE");
		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
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
		RIFeeRateTableTraceDB tRIFeeRateTableTraceDB = new RIFeeRateTableTraceDB();
		tRIFeeRateTableTraceDB.setFeeTableNo(mRIFeeRateTableTraceSchema
				.getFeeTableNo());
		tRIFeeRateTableTraceDB.setBatchNo(mRIFeeRateTableTraceSchema
				.getBatchNo());
		if (!tRIFeeRateTableTraceDB.getInfo()) {
			buildError("insertData", "该费率表批次定义不存在!");
			return false;
		}
		PubFun tPubFun = new PubFun();
		String currentDate = tPubFun.getCurrentDate();
		String currentTime = tPubFun.getCurrentTime();

		mRIFeeRateTableTraceSchema.setMakeDate(currentDate);
		mRIFeeRateTableTraceSchema.setMakeTime(currentTime);
		mRIFeeRateTableTraceSchema.setOperator(globalInput.Operator);
		mMap.put(mRIFeeRateTableTraceSchema, "UPDATE");

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
		RIFeeRateTableTraceDB tRIFeeRateTableTraceDB = new RIFeeRateTableTraceDB();
		tRIFeeRateTableTraceDB.setFeeTableNo(mRIFeeRateTableTraceSchema
				.getFeeTableNo());
		tRIFeeRateTableTraceDB.setBatchNo(mRIFeeRateTableTraceSchema
				.getBatchNo());
		if (tRIFeeRateTableTraceDB.getInfo()) {
			buildError("insertData", "该费率表定义已经存在!");
			return false;
		}
		PubFun tPubFun = new PubFun();
		String currentDate = tPubFun.getCurrentDate();
		String currentTime = tPubFun.getCurrentTime();
		mRIFeeRateTableTraceSchema.setMakeDate(currentDate);
		mRIFeeRateTableTraceSchema.setMakeTime(currentTime);
		mRIFeeRateTableTraceSchema.setOperator(globalInput.Operator);
		mMap.put(mRIFeeRateTableTraceSchema, "INSERT");
		if (mRIFeeRateTableTraceSchema2 != null) {
			String tDate = PubFun.calDate(
					mRIFeeRateTableTraceSchema.getInureDate(), -1, "D", null);
			String strSQL2 = "update rifeeratetabletrace set LapseDate='"
					+ tDate + "' where FeeTableNo='"
					+ mRIFeeRateTableTraceSchema2.getFeeTableNo()
					+ "' and BatchNo='"
					+ mRIFeeRateTableTraceSchema2.getBatchNo() + "'";
			mMap.put(strSQL2, "UPDATE");
		}
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
		this.mRIFeeRateTableTraceSchema
				.setSchema((RIFeeRateTableTraceSchema) cInputData
						.getObjectByObjectName("RIFeeRateTableTraceSchema", 1));
		if (cInputData.size() > 2) {
			this.mRIFeeRateTableTraceSchema2
					.setSchema((RIFeeRateTableTraceSchema) cInputData
							.getObjectByObjectName("RIFeeRateTableTraceSchema",
									2));
		} else {
			this.mRIFeeRateTableTraceSchema2 = null;
		}
		return true;
	}

	public VData getResult() {
		TransferData t = new TransferData();
		t.setNameAndValue("FeeRateNo",
				mRIFeeRateTableTraceSchema.getFeeTableNo());
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
