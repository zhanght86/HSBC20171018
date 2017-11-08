package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: web业务系统
 * </p>
 * 
 * <p>
 * Description: 团体保全
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: sinosfot
 * </p>
 * 
 * @author 梁聪
 * @version 1.0
 */
public class GrpEdorLRConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorLRConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap mMap = new MMap();

	public GrpEdorLRConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-------Grp LR Confirm----");

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		// 数据校验
		if (!checkData()) {
			return false;
		}
		// 处理数据
		if (!dealData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData() {
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mLPGrpEdorItemSchema == null || mGlobalInput == null) {
			CError.buildErr(this, "输入数据不完整!");
			return false;
		}
		return true;
	}

	/**
	 * 对业务数据校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpEdorItemDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询团险保全项目信息失败!");
			return false;
		}
		if (tLPGrpEdorItemSet == null || tLPGrpEdorItemSet.size() < 1) {
			CError.buildErr(this, "团险保全项目信息不存在!");
			return false;
		}
		mLPGrpEdorItemSchema = tLPGrpEdorItemSet.get(1);

		return true;
	}

	private boolean dealData() {
		// 检查个单保全项目明细表
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		try {
			tLPEdorItemSet = tLPEdorItemDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询个单保全项目明细表出现异常！");
			return false;
		}
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
			CError.buildErr(this, "在个单保全项目明细表中找不到待操作批单的纪录！");
			return false;
		}

		// 循环调用个单保全
		VData tVData = new VData();
		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			tVData.clear();
			tVData.add(mGlobalInput);
			tVData.add(tLPEdorItemSet.get(i));
			PEdorLRConfirmBL tPEdorLRConfirmBL = new PEdorLRConfirmBL();
			if (!tPEdorLRConfirmBL.submitData(tVData, mOperate)) {
				mErrors.copyAllErrors(tPEdorLRConfirmBL.mErrors);
				CError.buildErr(this, "处理提交的数据失败！");
				logger.debug("\t@> GrpEdorICDetailBLF.dealData() : PEdorLRConfirmBL.submitData() 失败！");
				logger.debug("\t                出错的被保人号是 : "
						+ tLPEdorItemSet.get(i).getInsuredNo());
				return false;
			} else {
				tVData = tPEdorLRConfirmBL.getResult();
				MMap tMap = new MMap();
				tMap = (MMap) tVData.getObjectByObjectName("MMap", 0);
				if (tMap == null) {
					CError.buildErr(this, "处理个人保全项目数据失败！");
					logger.debug("\t                出错的被保人号是 : "
							+ tLPEdorItemSet.get(i).getInsuredNo());
				} else
					mMap.add(tMap);
			}
			tPEdorLRConfirmBL = null;
		}

		String sql = "update lccont set printcount = 10 where contno in"
				+ "(select contno from lpedoritem where" + " EdorType='"
				+ mLPGrpEdorItemSchema.getEdorType() + "'"
				+ " and EdorAcceptNo='"
				+ mLPGrpEdorItemSchema.getEdorAcceptNo() + "'"
				+ " and EdorNo='" + mLPGrpEdorItemSchema.getEdorNo() + "')";
		mMap.put(sql, "UPDATE");

		mResult.add(mMap);
		return true;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回处理结果
	 * 
	 * @return: VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
