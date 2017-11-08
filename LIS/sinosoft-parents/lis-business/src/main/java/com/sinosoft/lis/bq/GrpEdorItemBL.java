package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: BL层业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author HST
 * @version 1.0
 * @date 2002-09-25
 */
public class GrpEdorItemBL {
private static Logger logger = Logger.getLogger(GrpEdorItemBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	private MMap map = new MMap();
	TransferData tTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
	private LPGrpEdorItemSet mLPGrpEdorItemSet = new LPGrpEdorItemSet();
	private GlobalInput mGlobalInput = new GlobalInput();

	// @Constructor
	public GrpEdorItemBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false)
			return false;
		logger.debug("---getInputData---");

		// 校验传入的数据
		if (!mOperate.equals("DELETE||GRPEDORITEM")) {
			if (this.checkData() == false)
				return false;
			logger.debug("---checkData---");
		}

		// 根据业务逻辑对数据进行处理
		if (!mOperate.equals("DELETE||GRPEDORITEM")) {
			if (this.dealData() == false)
				return false;
		} else {
			if (this.deleteData() == false)
				return false;
		}

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");
		// 数据提交、保存
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start GrpEdorItemBL Submit...");

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "GrpEdorItemBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---commitData---");

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {

		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// 团体批改主表
		mLPGrpEdorMainSchema.setSchema((LPGrpEdorMainSchema) mInputData
				.getObjectByObjectName("LPGrpEdorMainSchema", 0));

		// 批改项目表
		mLPGrpEdorItemSet.set((LPGrpEdorItemSet) mInputData
				.getObjectByObjectName("LPGrpEdorItemSet", 0));
		// tTransferData = (TransferData) mInputData.
		// getObjectByObjectName("TransferData", 0);

		if (mLPGrpEdorMainSchema == null || mLPGrpEdorItemSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "GrpEdorItemBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "在接受数据时没有得到团体批改主表或者批改项目表!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		if (!mOperate.equals("INSERT||GRPEDORITEM")
				&& !mOperate.equals("DELETE||GRPEDORITEM")) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorItemBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "只能新增或者删除!";
			this.mErrors.addOneError(tError);
			return false;

		}
		if (mOperate.equals("INSERT||GRPEDORITEM")) {
			LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
			tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorMainSchema.getGrpContNo());
			tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorMainSchema.getEdorNo());
			tLPGrpEdorItemDB.setEdorAcceptNo(mLPGrpEdorMainSchema
					.getEdorAcceptNo());
			tLPGrpEdorItemDB
					.setEdorType(mLPGrpEdorItemSet.get(1).getEdorType());
			int tCount = tLPGrpEdorItemDB.getCount();
			if (tLPGrpEdorItemDB.mErrors.needDealError()) {
				CError tError = new CError();
				tError.moduleName = "GrpEdorItemBL";
				tError.functionName = "checkData";
				tError.errorMessage = "查询保全项目失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tCount > 0) {
				CError tError = new CError();
				tError.moduleName = "GrpEdorItemBL";
				tError.functionName = "checkData";
				tError.errorMessage = "该批单下已经添加过该项目!";
				this.mErrors.addOneError(tError);
				return false;
			}

		}

		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {
		// 产生集体投保单号码
		if (mOperate.equals("INSERT||GRPEDORITEM")) {
			for (int i = 1; i <= mLPGrpEdorItemSet.size(); i++) {
				mLPGrpEdorItemSet.get(i).setEdorState("1");
				mLPGrpEdorItemSet.get(i).setModifyDate(PubFun.getCurrentDate());
				mLPGrpEdorItemSet.get(i).setModifyTime(PubFun.getCurrentTime());
				mLPGrpEdorItemSet.get(i).setOperator(mGlobalInput.Operator);
				mLPGrpEdorItemSet.get(i).setMakeDate(PubFun.getCurrentDate());
				mLPGrpEdorItemSet.get(i).setMakeTime(PubFun.getCurrentTime());
			}
			map.put(mLPGrpEdorItemSet, "INSERT");
		}

		return true;
	}

	/**
	 * 删除传入的险种
	 * 
	 * @param: 无
	 * @return: void
	 */
	private boolean deleteData() {

		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		mResult.clear();
		mResult.add(mLPGrpEdorItemSet);
		mInputData.clear();
		mInputData.add(map);

	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */

	public VData getResult() {
		return mResult;
	}

}
