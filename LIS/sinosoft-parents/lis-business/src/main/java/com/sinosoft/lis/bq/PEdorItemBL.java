package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LMEdorItemDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
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
public class PEdorItemBL {
private static Logger logger = Logger.getLogger(PEdorItemBL.class);
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
	private LPEdorMainSchema mLPEdorMainSchema = new LPEdorMainSchema();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private GlobalInput mGlobalInput = new GlobalInput();
	private LMEdorItemDB mLMEdorItemDB = new LMEdorItemDB();

	// @Constructor
	public PEdorItemBL() {
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
		if (!mOperate.equals("DELETE||EdorITEM")) {
			if (this.checkData() == false)
				return false;
			logger.debug("---checkData---");
		}

		// 根据业务逻辑对数据进行处理
		if (!mOperate.equals("DELETE||EdorITEM")) {
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
		logger.debug("Start PEdorItemBL Submit...");

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "PEdorItemBL";
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
		// 个人批改主表
		mLPEdorMainSchema.setSchema((LPEdorMainSchema) mInputData
				.getObjectByObjectName("LPEdorMainSchema", 0));

		// 批改项目表
		mLPEdorItemSet.set((LPEdorItemSet) mInputData.getObjectByObjectName(
				"LPEdorItemSet", 0));
		// tTransferData = (TransferData) mInputData.
		// getObjectByObjectName("TransferData", 0);

		if (mLPEdorMainSchema == null || mLPEdorItemSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorItemBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "在接受数据时没有得到个人批改主表或者批改项目表!";
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
		if (!mOperate.equals("INSERT||EDORITEM")
				&& !mOperate.equals("DELETE||EDORITEM")) {
			CError tError = new CError();
			tError.moduleName = "PEdorItemBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "只能新增或者删除!";
			this.mErrors.addOneError(tError);
			return false;

		}
		if (mOperate.equals("INSERT||EDORITEM")) {
			String i_sql = "";
			i_sql = "select EdorType from LPEdorItem where LPEdorItem.EdorAcceptNo = ?EdorAcceptNo?";// 当前在EdorItem表中同一个保全受理号所含的EdorType
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(i_sql);
			sbv1.put("EdorAcceptNo", mLPEdorMainSchema.getEdorAcceptNo());
			ExeSQL i_exesql = new ExeSQL();
			SSRS i_ssrs = new SSRS();
			i_ssrs = i_exesql.execSQL(sbv1);

			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setContNo(mLPEdorMainSchema.getContNo());
			tLPEdorItemDB.setEdorNo(mLPEdorMainSchema.getEdorNo());
			tLPEdorItemDB.setEdorAcceptNo(mLPEdorMainSchema.getEdorAcceptNo());
			for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
				String t_sql = "";
				String tItemType = "";
				String tEdorConstraints = "";
				SSRS t_ssrs = new SSRS();
				tItemType = mLPEdorItemSet.get(i).getEdorType();
				t_sql = "select EdorConstraints from LMEdorItem where EdorCode = '?tItemType?'";
				SQLwithBindVariables sbv2=new SQLwithBindVariables();
				sbv2.sql(t_sql);
				sbv2.put("tItemType", tItemType);
				ExeSQL t_exesql = new ExeSQL();
				t_ssrs = t_exesql.execSQL(sbv2);
				tEdorConstraints = t_ssrs.GetText(1, 1);
				logger.debug(tEdorConstraints);// 当前选择的保全项目的互斥保全项目
				for (int j = 1; j <= i_ssrs.getMaxRow(); j++) {
					logger.debug(i_ssrs.GetText(j, 1));// 已经存在的保全项目

					logger.debug(tEdorConstraints.indexOf(i_ssrs.GetText(
							j, 1)));
					String gEdorType = "";
					gEdorType = i_ssrs.GetText(j, 1);
					if (tEdorConstraints.indexOf(gEdorType) > -1) {
						CError tError = new CError();
						tError.moduleName = "PEdorItemBL";
						tError.functionName = "checkData";
						tError.errorMessage = "要增加保全项目与已有的保全项目矛盾！";
						this.mErrors.addOneError(tError);
						return false;
					}
				}
				tLPEdorItemDB.setEdorType(mLPEdorItemSet.get(i).getEdorType());
				tLPEdorItemDB
						.setInsuredNo(mLPEdorItemSet.get(i).getInsuredNo());
				tLPEdorItemDB.setPolNo(mLPEdorItemSet.get(i).getPolNo());
				int tCount = tLPEdorItemDB.getCount();
				if (tLPEdorItemDB.mErrors.needDealError()) {
					CError tError = new CError();
					tError.moduleName = "PEdorItemBL";
					tError.functionName = "checkData";
					tError.errorMessage = "查询保全项目失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				if (tCount > 0) {
					CError tError = new CError();
					tError.moduleName = "PEdorItemBL";
					tError.functionName = "checkData";
					tError.errorMessage = "该批单下已经添加过该项目!";
					this.mErrors.addOneError(tError);
					return false;
				}
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
		if (mOperate.equals("INSERT||EDORITEM")) {
			for (int i = 1; i <= mLPEdorItemSet.size(); i++) {
				mLPEdorItemSet.get(i).setEdorState("1");
				mLPEdorItemSet.get(i).setModifyDate(PubFun.getCurrentDate());
				mLPEdorItemSet.get(i).setModifyTime(PubFun.getCurrentTime());
				mLPEdorItemSet.get(i).setManageCom(mGlobalInput.ManageCom);
				mLPEdorItemSet.get(i).setOperator(mGlobalInput.Operator);
				mLPEdorItemSet.get(i).setMakeDate(PubFun.getCurrentDate());
				mLPEdorItemSet.get(i).setMakeTime(PubFun.getCurrentTime());
			}
			map.put(mLPEdorItemSet, "INSERT");
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
		mResult.add(mLPEdorItemSet);
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
