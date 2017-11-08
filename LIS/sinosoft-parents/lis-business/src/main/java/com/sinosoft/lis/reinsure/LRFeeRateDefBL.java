

/*
 * <p>ClassName: LRContManageBL </p>
 * <p>Description: LRContManageBL类文件 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: sinosoft </p>
 * @Database: Zhang Bin
 * @CreateDate：2006-07-30
 */
package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIFeeRateTableClumnDefDB;
import com.sinosoft.lis.db.RIFeeRateTableDefDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIFeeRateTableClumnDefSchema;
import com.sinosoft.lis.schema.RIFeeRateTableDefSchema;
import com.sinosoft.lis.vschema.RIFeeRateTableClumnDefSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LRFeeRateDefBL {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private RIFeeRateTableDefSchema mRIFeeRateTableDefSchema = new RIFeeRateTableDefSchema();
	private RIFeeRateTableClumnDefSet mRIFeeRateTableClumnDefSet = new RIFeeRateTableClumnDefSet();

	/** 数据操作字符串 */
	private String strOperate;
	private MMap mMap = new MMap();

	private PubSubmit tPubSubmit = new PubSubmit();

	// 业务处理相关变量
	/** 全局数据 */

	public LRFeeRateDefBL() {
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
			LRFeeRateDefBL tLRFeeRateDefBL = new LRFeeRateDefBL();
			VData tVData = new VData();
			GlobalInput globalInput = new GlobalInput();
			globalInput.Operator = "001";
			tVData.addElement(tRIFeeRateTableDefSchema);
			tVData.addElement(globalInput);
			tLRFeeRateDefBL.submitData(tVData, "INSERT");
			System.out.println(" ex: "
					+ tLRFeeRateDefBL.mErrors.getFirstError());
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
		RIFeeRateTableDefDB tRIFeeRateTableDefDB = new RIFeeRateTableDefDB();
		tRIFeeRateTableDefDB.setFeeTableNo(mRIFeeRateTableDefSchema
				.getFeeTableNo());
		if (!tRIFeeRateTableDefDB.getInfo()) {
			buildError("deleteData", "该费率表不存在!");
			return false;
		}
		String strSQL1 = "";
		String strSQL2 = "";
		String strSQL3 = "";
		String strSQL4 = "";
		strSQL1 = "delete from RIFeeRateTableDef where FeeTableNo='"
				+ mRIFeeRateTableDefSchema.getFeeTableNo() + "'";
		strSQL2 = "delete from RIFeeRateTableTrace where FeeTableNo='"
				+ mRIFeeRateTableDefSchema.getFeeTableNo() + "' ";
		strSQL3 = "delete from RIFeeRateTableClumnDef where FeeTableNo='"
				+ mRIFeeRateTableDefSchema.getFeeTableNo() + "' ";
		strSQL4 = "delete from RIFeeRateTable where FeeTableNo='"
				+ mRIFeeRateTableDefSchema.getFeeTableNo() + "' ";
		mMap.put(strSQL1, "DELETE");
		mMap.put(strSQL2, "DELETE");
		mMap.put(strSQL3, "DELETE");
		mMap.put(strSQL4, "DELETE");
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
		RIFeeRateTableDefDB tRIFeeRateTableDefDB = new RIFeeRateTableDefDB();
		tRIFeeRateTableDefDB.setFeeTableNo(mRIFeeRateTableDefSchema
				.getFeeTableNo());
		if (!tRIFeeRateTableDefDB.getInfo()) {
			buildError("updateData", "该费率表不存在!");
			return false;
		}
		PubFun tPubFun = new PubFun();
		String currentDate = tPubFun.getCurrentDate();
		String currentTime = tPubFun.getCurrentTime();

		mRIFeeRateTableDefSchema.setMakeDate(currentDate);
		mRIFeeRateTableDefSchema.setMakeTime(currentTime);
		mRIFeeRateTableDefSchema.setOperator(globalInput.Operator);
		mMap.put(mRIFeeRateTableDefSchema, "UPDATE");

		RIFeeRateTableClumnDefDB tRIFeeRateTableClumnDefDB = new RIFeeRateTableClumnDefDB();
		tRIFeeRateTableClumnDefDB.setFeeTableNo(mRIFeeRateTableDefSchema
				.getFeeTableNo());
		RIFeeRateTableClumnDefSet tRIFeeRateTableClumnDefSet = tRIFeeRateTableClumnDefDB
				.query();
		mMap.put(tRIFeeRateTableClumnDefSet, "DELETE");
		if (mRIFeeRateTableClumnDefSet.size() > 0) {
			RIFeeRateTableClumnDefSchema tRIFeeRateTableClumnDefSchema = new RIFeeRateTableClumnDefSchema();
			for (int i = 1; i <= mRIFeeRateTableClumnDefSet.size(); i++) {
				tRIFeeRateTableClumnDefSchema = mRIFeeRateTableClumnDefSet
						.get(i);
				tRIFeeRateTableClumnDefSchema.setFeeClumnNo(i);
			}
			mMap.put(mRIFeeRateTableClumnDefSet, "INSERT");
		}

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
		RIFeeRateTableDefDB tRIFeeRateTableDefDB = new RIFeeRateTableDefDB();
		tRIFeeRateTableDefDB.setFeeTableNo(mRIFeeRateTableDefSchema
				.getFeeTableNo());
		if (tRIFeeRateTableDefDB.getInfo()) {
			buildError("insertData", "该费率表定义已经存在!");
			return false;
		}
		PubFun tPubFun = new PubFun();
		String currentDate = tPubFun.getCurrentDate();
		String currentTime = tPubFun.getCurrentTime();
		mRIFeeRateTableDefSchema.setMakeDate(currentDate);
		mRIFeeRateTableDefSchema.setMakeTime(currentTime);
		mRIFeeRateTableDefSchema.setOperator(globalInput.Operator);
		mMap.put(mRIFeeRateTableDefSchema, "INSERT");

		if (mRIFeeRateTableClumnDefSet.size() > 0) {
			RIFeeRateTableClumnDefSchema tRIFeeRateTableClumnDefSchema = new RIFeeRateTableClumnDefSchema();
			for (int i = 1; i <= mRIFeeRateTableClumnDefSet.size(); i++) {
				tRIFeeRateTableClumnDefSchema = mRIFeeRateTableClumnDefSet
						.get(i);
				tRIFeeRateTableClumnDefSchema.setFeeClumnNo(i);
			}
			mMap.put(mRIFeeRateTableClumnDefSet, "INSERT");
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
		this.mRIFeeRateTableDefSchema
				.setSchema((RIFeeRateTableDefSchema) cInputData
						.getObjectByObjectName("RIFeeRateTableDefSchema", 0));
		this.mRIFeeRateTableClumnDefSet
				.set((RIFeeRateTableClumnDefSet) cInputData
						.getObjectByObjectName("RIFeeRateTableClumnDefSet", 0));
		return true;
	}

	public VData getResult() {
		TransferData t = new TransferData();
		t.setNameAndValue("FeeRateNo", mRIFeeRateTableDefSchema.getFeeTableNo());
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
