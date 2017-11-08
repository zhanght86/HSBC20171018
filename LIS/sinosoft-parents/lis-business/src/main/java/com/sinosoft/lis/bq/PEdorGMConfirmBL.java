package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
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
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 领取方式变更（保全项目代码：GM）保全确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WuHao
 * @version 1.0
 */
public class PEdorGMConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorGMConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	public PEdorGMConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 检查数据
		if (!checkData()) {
			return false;
		}

		// 数据操作
		if (!dealData()) {
			return false;
		}

		// 输出数据准备
		if (!prepareOutputData()) {
			return false;
		}

		this.setOperate("CONFIRM||GM");

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
			mErrors.addOneError(new CError("查询批改项目信息失败！"));
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * 准备需要保存的数据 C、P表数据互换
	 */
	private boolean dealData() {
		LCGetSet aLCGetSet = new LCGetSet();
		LPGetSet aLPGetSet = new LPGetSet();

		Reflections tRef = new Reflections();
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
			// /***************把lcget表的催付标志一律改为催付状态,可以参与以后的催付(115)**************************\
			if (tLCGetSchema.getUrgeGetFlag() != null
					&& tLCGetSchema.getUrgeGetFlag().trim().equals("N")
					&& tLCGetSchema.getLiveGetType() != null
					&& tLCGetSchema.getLiveGetType().equals("0")) {
				tLCGetSchema.setUrgeGetFlag("Y");
			}
			aLCGetSet.add(tLCGetSchema);
		}

		LCGetSet tLCGetSet = new LCGetSet();
		// 原给付项信息
		if (tLPGetSet != null && tLPGetSet.size() > 0) {
			LCGetDB tLCGetDB = new LCGetDB();
			tLCGetDB.setPolNo(mLPEdorItemSchema.getPolNo());
			tLCGetSet = tLCGetDB.query();
			for (int j = 1; j <= tLCGetSet.size(); j++) {
				LPGetSchema tLPGetSchema = new LPGetSchema();
				tRef.transFields(tLPGetSchema, tLCGetSet.get(j).getSchema());
				tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				aLPGetSet.add(tLPGetSchema);
			}
		}

		// 删除应付记录
		String delsGetdraw = " delete from ljsgetdraw where contno = '?contno?'";
		String delsGet = " delete from ljsget where othernotype = '2' and otherno = '?otherno?' ";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(delsGetdraw);
		sbv1.put("contno", mLPEdorItemSchema.getContNo());
		mMap.put(sbv1, "DELETE");
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(delsGet);
		sbv2.put("otherno", mLPEdorItemSchema.getContNo());
		mMap.put(sbv2, "DELETE");

		// //得到当前LPEdorItem保单信息主表的状态，并更新状态为申请确认。
		// mLPEdorItemSchema.setEdorState("0");
		// mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		// mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		// mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		// mMap.put(mLPEdorItemSchema, "UPDATE");

		mMap.put(aLCGetSet, "UPDATE");
		mMap.put(aLPGetSet, "UPDATE");

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
			LCPolSchema tLCPolSchema = null;
			try {
				for (int i = 1; i <= tLPPolSet.size(); i++) {
					tLCPolSchema = new LCPolSchema();
					tRef.transFields(tLCPolSchema, tLPPolSet.get(i));
					tLCPolSchema.setOperator(mGlobalInput.Operator);
					tLCPolSchema.setModifyDate(sCurrentDate);
					tLCPolSchema.setModifyTime(sCurrentTime);
					mMap.put(tLCPolSchema, "DELETE&INSERT");
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
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
			LCPolSet tLCPolSet = new LCPolSet();
			try {
				tLCPolSet = tLCPolDB.query();
			} catch (Exception ex) {
				CError.buildErr(this, "查询契约险种表是否含有 128 和 145 险种出错！");
				return false;
			}
			// 拷贝 LCPol 到 LPPol
			if (tLCPolSet.size() > 0) {
				LPPolSchema tLPPolSchema = null;
				try {
					for (int i = 1; i <= tLCPolSet.size(); i++) {
						tLPPolSchema = new LPPolSchema();
						tRef.transFields(tLPPolSchema, tLCPolSet.get(i));
						tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
						tLPPolSchema.setEdorType(mLPEdorItemSchema
								.getEdorType());
						tLPPolSchema.setOperator(mGlobalInput.Operator);
						tLPPolSchema.setModifyDate(sCurrentDate);
						tLPPolSchema.setModifyTime(sCurrentTime);
						mMap.put(tLPPolSchema, "DELETE&INSERT");
					}
				} catch (Exception ex) {
					CError.buildErr(this, "备份契约险种表到保全险种表失败！");
					ex.printStackTrace();
					return false;
				}
				// 垃圾处理
				tLPPolSchema = null;
			}
			// 垃圾处理
			tLCPolSet = null;
			tLCPolDB = null;
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
			LCDutySchema tLCDutySchema = null;
			try {
				for (int i = 1; i <= tLPDutySet.size(); i++) {
					tLCDutySchema = new LCDutySchema();
					tRef.transFields(tLCDutySchema, tLPDutySet.get(i));
					tLCDutySchema.setOperator(mGlobalInput.Operator);
					tLCDutySchema.setModifyDate(sCurrentDate);
					tLCDutySchema.setModifyTime(sCurrentTime);
					mMap.put(tLCDutySchema, "DELETE&INSERT");
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
			LCDutyDB tLCDutyDB = new LCDutyDB();
			tLCDutyDB.setPolNo(mLPEdorItemSchema.getPolNo());
			LCDutySet tLCDutySet = new LCDutySet();
			try {
				tLCDutySet = tLCDutyDB.query();
			} catch (Exception ex) {
				CError.buildErr(this, "查询契约责任表是否含有 128 和 145 险种出错！");
				return false;
			}
			// 拷贝 LCDuty 到 LPDuty
			if (tLCDutySet.size() > 0) {
				LPDutySchema tLPDutySchema;
				try {
					for (int i = 1; i <= tLCDutySet.size(); i++) {
						tLPDutySchema = new LPDutySchema();
						tRef.transFields(tLPDutySchema, tLCDutySet.get(i));
						tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
						tLPDutySchema.setEdorType(mLPEdorItemSchema
								.getEdorType());
						tLPDutySchema.setOperator(mGlobalInput.Operator);
						tLPDutySchema.setModifyDate(sCurrentDate);
						tLPDutySchema.setModifyTime(sCurrentTime);
						mMap.put(tLPDutySchema, "DELETE&INSERT");
					}
				} catch (Exception ex) {
					CError.buildErr(this, "备份契约责任表到保全责任表失败！");
					ex.printStackTrace();
					return false;
				}
				// 垃圾处理
				tLPDutySchema = null;
			}
			// 垃圾处理
			tLCDutySet = null;
			tLCDutyDB = null;
		}

		// <XinYQ added on 2006-02-17 : 128 和 145 的特殊处理 : END -->

		return true;
	}

	/**
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(mMap);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorGMConfirmBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		PEdorGMConfirmBL pedorgbcomfirmbl = new PEdorGMConfirmBL();
	}
}
