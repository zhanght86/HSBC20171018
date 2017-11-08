package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LPContBL;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 保全项目基本信息变更备注项明细
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * </p>
 * 
 * @author changpeng
 * @version 1.0
 */
public class PEdorMCDetailBL {
private static Logger logger = Logger.getLogger(PEdorMCDetailBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = null;
	/** 封装要操作的数据，以便一次提交 */
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
	private LPContSchema mLPContSchema = new LPContSchema();
	private LPContSet mLPContSet = new LPContSet();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private Reflections ref = new Reflections();

	public PEdorMCDetailBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		// 数据校验操作
		if (!checkData()) {
			return false;
		}
		// 数据准备操作
		if (!dealData()) {
			return false;
		}
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	/** 获取前台数据 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mLPContSchema = (LPContSchema) mInputData.getObjectByObjectName(
					"LPContSchema", 0);
			if (mGlobalInput == null || mLPEdorItemSchema == null
					|| mLPContSchema == null) {
				return false;
			}
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		boolean flag = true;
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();

		if (this.getOperate().equals("UPDATE||MAIN")) {
			tLPEdorItemDB.setSchema(mLPEdorItemSchema);
			LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
			if (tLPEdorItemDB.getInfo()) {
				tLPEdorItemSet.add(tLPEdorItemDB.getSchema());
				if (tLPEdorItemSet == null || tLPEdorItemSet.size() <= 0) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "PEdorMCDetailBL";
					tError.functionName = "Preparedata";
					tError.errorMessage = "无保全申请数据!";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
			// 获取保全主表数据，节省查询次数
			mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1).getSchema());
			if (!mLPEdorItemSchema.getEdorState().trim().equals("1")
					&& !mLPEdorItemSchema.getEdorState().trim().equals("3")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorMCDetailBL";
				tError.functionName = "Preparedata";
				tError.errorMessage = "该保全已经申请确认不能修改!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return flag;

	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		boolean flag = true;
		LPContBL tLPContBL = new LPContBL();
		if (!tLPContBL.queryLPCont(mLPEdorItemSchema)) {
			CError.buildErr(this, "查询合同保单信息失败!");
			return false;
		}
		tLPContBL.setEdorNo(mLPContSchema.getEdorNo());
		tLPContBL.setEdorType(mLPContSchema.getEdorType());
		tLPContBL.setContNo(mLPContSchema.getContNo());

		tLPContBL.setRemark(mLPContSchema.getRemark());
		tLPContBL.setModifyDate(PubFun.getCurrentDate());
		tLPContBL.setModifyTime(PubFun.getCurrentTime());

		mLPContSet.add(tLPContBL.getSchema());
		mMap.put(mLPContSet, "DELETE&INSERT"); // 加入Map

		LPEdorMainDB tLPEdorMainDB = new LPEdorMainDB();
		tLPEdorMainDB.setContNo(mLPContSchema.getContNo());
		tLPEdorMainDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorMainDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		if (!tLPEdorMainDB.getInfo()) {
			if (tLPEdorMainDB.mErrors.needDealError()) {
				CError.buildErr(this, "查询保单号为" + tLPEdorMainDB.getContNo()
						+ "的保单批单信息时失败!");
				return false;
			} else {
				LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
				ref.transFields(tLPEdorMainSchema, mLPEdorItemSchema);
				String strLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
				String strEdorNo = PubFun1.CreateMaxNo("EdorAppNo", strLimit);
				if (StrTool.compareString(strEdorNo, "")) {
					CError.buildErr(this, "生成保全批单号错误！");
					return false;
				}
				tLPEdorMainSchema.setEdorNo(strEdorNo);
				tLPEdorMainDB.setEdorNo(strEdorNo);
				tLPEdorMainSchema.setEdorAppNo(strEdorNo);
				tLPEdorMainSchema.setContNo(mLPContSchema.getContNo());
				tLPEdorMainSchema.setUWState("0");
				mLPEdorMainSet.add(tLPEdorMainSchema);
				mMap.put(mLPEdorMainSet, "INSERT");
			}
		}

		return flag;

	}

	/**
	 * getOperate
	 * 
	 * @return Object
	 */
	private Object getOperate() {
		return mOperate;
	}

	/**
	 * getResult
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}
}
