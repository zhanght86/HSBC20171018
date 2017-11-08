package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全回退处理
 * </p>
 * 
 * <p>
 * Description: 领取方式变更保全回退确认类
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @CreateDate 2005-10-10
 * @version 1.0
 */
public class PEdorGMBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorGMBackConfirmBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	//public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();
	// 需要回退的保全项目
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorGMBackConfirmBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param cInputData
	 *            传入的数据对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		if (!prepareData()) {
			return false;
		}

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPEdorItemSchema = // 需要回退的保全项目
			(LPEdorItemSchema) mInputData.getObjectByObjectName(
					"LPEdorItemSchema", 0);
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "接收前台数据失败!");
			return false;
		}
		if (mGlobalInput == null || mLPEdorItemSchema == null) {
			CError.buildErr(this, "传入数据有误!");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		logger.debug("=== 领取方式变更回退确认生效 ===");
		Reflections tRef = new Reflections();

		LCGetSet aLCGetSet = new LCGetSet();
		LPGetSet aLPGetSet = new LPGetSet();

		//保全回退直接那回退项目的P表数据覆盖C表，无需对P表进行任何操作
		//因为MS不需要回退的回退
		// 得到给付项信息
		LPGetDB tLPGetDB = new LPGetDB();
		LPGetSet tLPGetSet = new LPGetSet();
		tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPGetDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPGetSet = tLPGetDB.query();
		for (int j = 1; j <= tLPGetSet.size(); j++) {
			LCGetSchema tLCGetSchema = new LCGetSchema();
			tRef.transFields(tLCGetSchema, tLPGetSet.get(j).getSchema());
			tLCGetSchema.setModifyDate(CurrDate);
			tLCGetSchema.setModifyTime(CurrTime);
			aLCGetSet.add(tLCGetSchema);
		}

//		LCGetSet tLCGetSet = new LCGetSet();
//		// 原给付项信息
//		if (tLPGetSet != null && tLPGetSet.size() > 0) {
//			LCGetDB tLCGetDB = new LCGetDB();
//			tLCGetDB.setPolNo(mLPEdorItemSchema.getPolNo());
//			tLCGetSet = tLCGetDB.query();
//			for (int j = 1; j <= tLCGetSet.size(); j++) {
//				LPGetSchema tLPGetSchema = new LPGetSchema();
//				tRef.transFields(tLPGetSchema, tLCGetSet.get(j).getSchema());
//				tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//				tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
//				tLPGetSchema.setModifyDate(CurrDate);
//				tLPGetSchema.setModifyTime(CurrTime);
//				aLPGetSet.add(tLPGetSchema);
//			}
//		}

		map.put(aLCGetSet, "UPDATE");
//		map.put(aLPGetSet, "UPDATE");

		// <XinYQ added on 2006-02-17 : 128 和 145 的特殊处理 : BGN -->

		String sCurrentDate = PubFun.getCurrentDate();
		String sCurrentTime = PubFun.getCurrentTime();

		// 检查 LPPol 表
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPPolSet tLPPolSet = new LPPolSet();
		try {
			tLPPolSet = tLPPolDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全险种表是否含有 128 和 145 险种出错！");
			return false;
		}
		// 拷贝 LPPol 到 LCPol
		if (tLPPolSet.size() > 0) {
			LCPolSchema tLCPolSchema;
			try {
				for (int i = 1; i <= tLPPolSet.size(); i++) {
					tLCPolSchema = new LCPolSchema();
					tRef.transFields(tLCPolSchema, tLPPolSet.get(i));
					tLCPolSchema.setOperator(mGlobalInput.Operator);
					tLCPolSchema.setModifyDate(sCurrentDate);
					tLCPolSchema.setModifyTime(sCurrentTime);
					map.put(tLCPolSchema, "DELETE&INSERT");
				}
			} catch (Exception ex) {
				CError.buildErr(this, "拷贝保全险种表到契约险种表失败！");
				ex.printStackTrace();
				return false;
			}
			// 垃圾处理
			tLCPolSchema = null;
			tLPPolSet = null;
			tLPPolDB = null;

			// 检查 LCPol 表
//			LCPolDB tLCPolDB = new LCPolDB();
//			tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
//			LCPolSet tLCPolSet = new LCPolSet();
//			try {
//				tLCPolSet = tLCPolDB.query();
//			} catch (Exception ex) {
//				CError.buildErr(this, "查询契约险种表是否含有 128 和 145 险种出错！");
//				return false;
//			}
//			// 拷贝 LCPol 到 LPPol
//			if (tLCPolSet.size() > 0) {
//				LPPolSchema tLPPolSchema;
//				try {
//					for (int i = 1; i <= tLCPolSet.size(); i++) {
//						tLPPolSchema = new LPPolSchema();
//						tRef.transFields(tLPPolSchema, tLCPolSet.get(i));
//						tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//						tLPPolSchema.setEdorType(mLPEdorItemSchema
//								.getEdorType());
//						tLPPolSchema.setOperator(mGlobalInput.Operator);
//						tLPPolSchema.setModifyDate(sCurrentDate);
//						tLPPolSchema.setModifyTime(sCurrentTime);
//						map.put(tLPPolSchema, "DELETE&INSERT");
//					}
//				} catch (Exception ex) {
//					CError.buildErr(this, "备份契约险种表到保全险种表失败！");
//					ex.printStackTrace();
//					return false;
//				}
//				// 垃圾处理
//				tLPPolSchema = null;
//			}
			// 垃圾处理
//			tLCPolSet = null;
//			tLCPolDB = null;
		}
		// 垃圾处理

		// 检查 LPDuty 表
		LPDutyDB tLPDutyDB = new LPDutyDB();
		tLPDutyDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPDutySet tLPDutySet = new LPDutySet();
		try {
			tLPDutySet = tLPDutyDB.query();
		} catch (Exception ex) {
			CError.buildErr(this, "查询保全责任表是否含有 128 和 145 险种出错！");
			return false;
		}
		// 拷贝 LPDuty 到 LCDuty
		if (tLPDutySet.size() > 0) {
			LCDutySchema tLCDutySchema;
			try {
				for (int i = 1; i <= tLPDutySet.size(); i++) {
					tLCDutySchema = new LCDutySchema();
					tRef.transFields(tLCDutySchema, tLPDutySet.get(i));
					tLCDutySchema.setOperator(mGlobalInput.Operator);
					tLCDutySchema.setModifyDate(sCurrentDate);
					tLCDutySchema.setModifyTime(sCurrentTime);
					map.put(tLCDutySchema, "DELETE&INSERT");
				}
			} catch (Exception ex) {
				CError.buildErr(this, "拷贝保全责任表到契约责任表失败！");
				ex.printStackTrace();
				return false;
			}
			// 垃圾处理
			tLCDutySchema = null;
			// 垃圾处理
			tLPDutySet = null;
			tLPDutyDB = null;

			// 检查 LCDuty 表
//			LCDutyDB tLCDutyDB = new LCDutyDB();
//			tLCDutyDB.setPolNo(mLPEdorItemSchema.getPolNo());
//			LCDutySet tLCDutySet = new LCDutySet();
//			try {
//				tLCDutySet = tLCDutyDB.query();
//			} catch (Exception ex) {
//				CError.buildErr(this, "查询契约责任表是否含有 128 和 145 险种出错！");
//				return false;
//			}
//			// 拷贝 LCDuty 到 LPDuty
//			if (tLCDutySet.size() > 0) {
//				LPDutySchema tLPDutySchema;
//				try {
//					for (int i = 1; i <= tLCDutySet.size(); i++) {
//						tLPDutySchema = new LPDutySchema();
//						tRef.transFields(tLPDutySchema, tLCDutySet.get(i));
//						tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
//						tLPDutySchema.setEdorType(mLPEdorItemSchema
//								.getEdorType());
//						tLPDutySchema.setOperator(mGlobalInput.Operator);
//						tLPDutySchema.setModifyDate(sCurrentDate);
//						tLPDutySchema.setModifyTime(sCurrentTime);
//						map.put(tLPDutySchema, "DELETE&INSERT");
//					}
//				} catch (Exception ex) {
//					CError.buildErr(this, "备份契约责任表到保全责任表失败！");
//					ex.printStackTrace();
//					return false;
//				}
//				// 垃圾处理
//				tLPDutySchema = null;
//			}
//			// 垃圾处理
//			tLCDutySet = null;
//			tLCDutyDB = null;
		}

		// <XinYQ added on 2006-02-17 : 128 和 145 的特殊处理 : END -->

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		PEdorGMBackConfirmBL pedorgmbackconfirmbl = new PEdorGMBackConfirmBL();
	}
}
