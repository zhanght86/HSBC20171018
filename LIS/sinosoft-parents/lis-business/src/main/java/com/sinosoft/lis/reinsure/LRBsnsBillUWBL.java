

/**
 * LRBsnsBillBL.java
 * com.sinosoft.lis.reinsure
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 Nov 4, 2010 		曹淑国
 *
 * Copyright (c) 2010, TNT All Rights Reserved.
 */

package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIBsnsBillBatchDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIBsnsBillBatchSchema;
import com.sinosoft.lis.vschema.RIBsnsBillBatchSet;
import com.sinosoft.lis.vschema.RIBsnsBillDetailsSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * ClassName:LRBsnsBillBL Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author 曹淑国
 * @version
 * 
 */
public class LRBsnsBillUWBL {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private TransferData mTransferData = new TransferData();
	/** 数据操作字符串 */
	private String strOperate;

	private String mModType;

	private MMap mMap = new MMap();

	private ExeSQL mExeSQL = new ExeSQL();

	private PubSubmit tPubSubmit = new PubSubmit();

	private RIBsnsBillBatchSchema mRIBsnsBillBatchSchema = new RIBsnsBillBatchSchema();

	private RIBsnsBillDetailsSet mRIBsnsBillDetailsSet = new RIBsnsBillDetailsSet();

	// 业务处理相关变量
	/** 全局数据 */
	public LRBsnsBillUWBL() {
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

		if (!getInputData(cInputData)) {
			return false;
		}
		if (!dealData()) {
			return false;
		}

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
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// =====================获取前台数据

		if ("".equals(strOperate) || strOperate == null) {
			buildError("getInputData", "获取前台操作数据失败!");
			return false;
		}
		// 账单修改校验
		if ("billupdate".equals(strOperate)) {
			mRIBsnsBillDetailsSet = (RIBsnsBillDetailsSet) cInputData
					.getObjectByObjectName("RIBsnsBillDetailsSet", 0);
			if (mRIBsnsBillDetailsSet == null
					|| mRIBsnsBillDetailsSet.size() < 1) {
				buildError("getInputData--billupdate", "获取前台操作数据失败!");
				return false;
			}
		}
		// 数据下载及账单审核校验
		if ("download".equals(strOperate) || "conclusion".equals(strOperate)) {
			mRIBsnsBillBatchSchema = (RIBsnsBillBatchSchema) cInputData
					.getObjectByObjectName("RIBsnsBillBatchSchema", 0);
			if (mRIBsnsBillBatchSchema == null
					|| "".equals(mRIBsnsBillBatchSchema.getBillNo())) {
				buildError("getInputData--download", "获取前台操作数据失败!");
				return false;
			}
			if ("conclusion".equals(strOperate)
					&& "".equals(mRIBsnsBillBatchSchema.getState())) {
				buildError("getInputData--conclusion", "获取前台操作数据失败!");
				return false;
			}
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// ========================业务逻辑处理
		// 账单修改
		if ("billupdate".equals(strOperate)) {
			if (!billUpdate()) {
				buildError("dealData--billupdate", "账单修改操作失败!");
				return false;
			}
		}
		// 账单审核结论保存
		if ("conclusion".equals(strOperate)) {
			if (!saveConclusion()) {
				buildError("dealData--billupdate", "账单修改操作失败!");
				return false;
			}
		}

		return true;
	}

	/**
	 * billUpdate:(账单明细数据修改)
	 * 
	 * @param @return 设定文件
	 * @return boolean DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */
	private boolean billUpdate() {
		int maxSerialNo;
		String tSQL = " select max(SerialNo) from RIBsnsBillDetails where 1=1 ";
		for (int i = 1; i <= mRIBsnsBillDetailsSet.size(); i++) {
			// tSQL = tSQL +
			// " and Details = '"+mRIBsnsBillDetailsSet.get(i).getDetails()+"' and DebCre='"
			// +mRIBsnsBillDetailsSet.get(i).getDebCre()+"' and BillNo='"+mRIBsnsBillDetailsSet.get(i).getBillNo()+"' "
			// ;

			maxSerialNo = Integer.valueOf(mExeSQL.getOneValue(tSQL)) + 1;

			// mRIBsnsBillDetailsSet.get(i).setSerialNo(String.valueOf(maxSerialNo))
			// ;
		}

		mMap.put(mRIBsnsBillDetailsSet, "INSERT");

		return true;
	}

	/**
	 * saveConclusion:(账单审核结论保存)
	 * 
	 * @param @return 设定文件
	 * @return boolean DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */
	private boolean saveConclusion() {
		String billNo = mRIBsnsBillBatchSchema.getBillNo();
		String state = mRIBsnsBillBatchSchema.getState();

		String tSQL = " select * from RIBsnsBillBatch where BillNo= '" + billNo
				+ "' ";
		RIBsnsBillBatchDB tRIBsnsBillBatchDB = new RIBsnsBillBatchDB();
		RIBsnsBillBatchSet tRIBsnsBillBatchSet = tRIBsnsBillBatchDB
				.executeQuery(tSQL);
		System.out.println("============saveConclusion===state:::" + state);
		if (tRIBsnsBillBatchSet != null && tRIBsnsBillBatchSet.size() > 0) {
			tRIBsnsBillBatchSet.get(1).setState(state);
			tRIBsnsBillBatchSet.get(1).setUWOperator(globalInput.Operator);
			tRIBsnsBillBatchSet.get(1).setModifyDate(PubFun.getCurrentDate());
			tRIBsnsBillBatchSet.get(1).setModifyTime(PubFun.getCurrentTime());

			mMap.put(tRIBsnsBillBatchSet, "UPDATE");
		}

		return true;
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

	public String getResult() {
		return "";
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

	/**
	 * main:(这里用一句话描述这个方法的作用)
	 * 
	 * @param @param args 设定文件
	 * @return void DOM对象
	 * @throws
	 * @since CodingExample　Ver 1.1
	 */

	public static void main(String[] args) {

		// TODO Auto-generated method stub

	}

}
