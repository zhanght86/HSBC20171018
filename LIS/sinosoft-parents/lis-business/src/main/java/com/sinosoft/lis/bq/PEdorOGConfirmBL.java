package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:保全
 * </p>
 * <p>
 * Description:保险金一次性给付ConfirmBL层
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author TangPei
 * @version 1.0
 */

public class PEdorOGConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorOGConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 往后面传输的数据库操作 */
	private MMap map = new MMap();
	/** 用户登陆信息 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 传输数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorOGConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据
		if (!getInputData()) {
			return false;
		}

		logger.debug("after getInputData...");

		// 数据校验
		if (!checkData()) {
			return false;
		}

		logger.debug("after checkData...");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("after dealData...");

		return true;
	}

	/**
	 * 数据操作业务处理
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		// C、P表数据互换
		if (!exchangCPData()) {
			return false;
		}

		return true;
	}

	/**
	 * C、P表数据互换
	 * 
	 * @return: boolean
	 */
	private boolean exchangCPData() {
		Reflections tRef = new Reflections();

		// 当前日期时间
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();

		// C表
		LCGetSet aLCGetSet = new LCGetSet(); // UPDATE用的
		LCGetSet iLCGetSet = new LCGetSet(); // INSERT用的

		// P表
		LPGetSet aLPGetSet = new LPGetSet(); // DELETE&INSERT
		LPGetSet dLPGetSet = new LPGetSet(); // DELETE

		// 查询P表数据[领取项表]
		LPGetSet tLPGetSet = new LPGetSet();
		LPGetDB tLPGetDB = new LPGetDB();
		tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPGetSet.set(tLPGetDB.query());

		for (int i = 1; i <= tLPGetSet.size(); i++) {
			// 将P表中数据放到C表中[领取项表]
			LCGetSchema tLCGetSchema = new LCGetSchema();
			tRef.transFields(tLCGetSchema, tLPGetSet.get(i));
			tLCGetSchema.setOperator(mGlobalInput.Operator);
			tLCGetSchema.setModifyDate(tCurrentDate);
			tLCGetSchema.setModifyTime(tCurrentTime);

			// 查询C表数据[领取项表]
			LCGetDB tLCGetDB = new LCGetDB();
			tLCGetDB.setPolNo(tLCGetSchema.getPolNo());
			tLCGetDB.setDutyCode(tLCGetSchema.getDutyCode());
			tLCGetDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
			if (!tLCGetDB.getInfo()) {
				iLCGetSet.add(tLCGetSchema);
				dLPGetSet.add(tLPGetSet.get(i)); // 将P表比C表多的数据删除，保全回退时才可区分
			} else {
				// UPDATE的
				aLCGetSet.add(tLCGetSchema);
				// 将C表中数据放到P表中[领取项表]
				LPGetSchema tLPGetSchema = new LPGetSchema();
				tRef.transFields(tLPGetSchema, tLCGetDB.getSchema());
				tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPGetSchema.setOperator(mGlobalInput.Operator);
				tLPGetSchema.setModifyDate(tCurrentDate);
				tLPGetSchema.setModifyTime(tCurrentTime);
				aLPGetSet.add(tLPGetSchema);
			}
		}

		if (aLCGetSet != null && aLCGetSet.size() > 0) {
			map.put(aLCGetSet, "UPDATE");
		}
		if (iLCGetSet != null && iLCGetSet.size() > 0) {
			map.put(iLCGetSet, "DELETE&INSERT");
		}
		if (aLPGetSet != null && aLPGetSet.size() > 0) {
			map.put(aLPGetSet, "DELETE&INSERT");
		}
		if (dLPGetSet != null && dLPGetSet.size() > 0) {
			map.put(dLPGetSet, "DELETE");
		}

		// 变更领取方式
		// C表
		LCPolSet aLCPolSet = new LCPolSet(); // UPDATE用的
		LCPolSet iLCPolSet = new LCPolSet(); // INSERT用的

		// P表
		LPPolSet aLPPolSet = new LPPolSet();
		LPPolSet dLPPolSet = new LPPolSet(); // DELETE

		// 查询P表数据[领取项表]
		LPPolSet tLPPolSet = new LPPolSet();
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSet.set(tLPPolDB.query());

		for (int i = 1; i <= tLPPolSet.size(); i++) {
			// 将P表中数据放到C表中[领取项表]
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tRef.transFields(tLCPolSchema, tLPPolSet.get(i));
			tLCPolSchema.setOperator(mGlobalInput.Operator);
			tLCPolSchema.setModifyDate(tCurrentDate);
			tLCPolSchema.setModifyTime(tCurrentTime);

			// 查询C表数据[领取项表]
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLCPolSchema.getPolNo());
			if (!tLCPolDB.getInfo()) {
				iLCPolSet.add(tLCPolSchema);
				dLPPolSet.add(tLPPolSet.get(i)); // 将P表比C表多的数据删除，保全回退时才可区分
			} else {
				// UPDATE的
				aLCPolSet.add(tLCPolSchema);
				// 将C表中数据放到P表中[领取项表]
				LPPolSchema tLPPolSchema = new LPPolSchema();
				tRef.transFields(tLPPolSchema, tLCPolDB.getSchema());
				tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPPolSchema.setOperator(mGlobalInput.Operator);
				tLPPolSchema.setModifyDate(tCurrentDate);
				tLPPolSchema.setModifyTime(tCurrentTime);
				aLPPolSet.add(tLPPolSchema);
			}
		}

		if (aLCPolSet != null && aLCPolSet.size() > 0) {
			map.put(aLCPolSet, "UPDATE");
		}
		if (iLCPolSet != null && iLCPolSet.size() > 0) {
			map.put(iLCPolSet, "DELETE&INSERT");
		}
		if (aLPPolSet != null && aLPPolSet.size() > 0) {
			map.put(aLPPolSet, "DELETE&INSERT");
		}
		if (dLPPolSet != null && dLPPolSet.size() > 0) {
			map.put(dLPPolSet, "DELETE");
		}

		// //更新保全项目状态为保全确认
		// mLPEdorItemSchema.setEdorState("0");
		// mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		// mLPEdorItemSchema.setModifyDate(tCurrentDate);
		// mLPEdorItemSchema.setModifyTime(tCurrentTime);
		//
		// map.put(mLPEdorItemSchema, "UPDATE");
		mResult.add(map);
		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return: boolean
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		if (!tLPEdorItemDB.getInfo()) {
			mErrors.addOneError(new CError("查询批改项目信息失败!"));
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

		return true;
	}

	/**
	 * 得到外部传入的数据
	 * 
	 * @return: boolean
	 */
	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全!"));
			return false;
		}

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

	public static void main(String[] args) {
		PEdorOGConfirmBL tPEdorOGConfirmBL = new PEdorOGConfirmBL();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

		tLPEdorItemSchema.setEdorAcceptNo("6120070730000002");
		tLPEdorItemSchema.setContNo("230110000002966");
		tLPEdorItemSchema.setEdorNo("6020070730000002");
		tLPEdorItemSchema.setEdorType("OG");
		tLPEdorItemSchema.setInsuredNo("0000527040");
		tLPEdorItemSchema.setPolNo("210110000002591");

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.Operator = "001";
		tGlobalInput.ManageCom = "86";

		VData tVData = new VData();
		tVData.add(tLPEdorItemSchema);
		tVData.add(tGlobalInput);
		boolean tag = tPEdorOGConfirmBL.submitData(tVData, "UPDATE||MAIN");
		if (tag) {
			logger.debug("Successful");
		} else {
			logger.debug("Fail");
		}
	}
}
