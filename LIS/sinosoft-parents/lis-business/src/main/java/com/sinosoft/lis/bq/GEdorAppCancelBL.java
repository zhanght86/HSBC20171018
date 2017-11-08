package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LOBEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.vschema.LPGrpEdorMainSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全删除业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author lh
 * @version 1.0
 */

// 功能：查询出个人批改主表中本次申请的批改类型
// 入口参数：个单的保单号、批单号
// 出口参数：每条记录的个单的保单号、批单号和批改类型
public class GEdorAppCancelBL {
private static Logger logger = Logger.getLogger(GEdorAppCancelBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();

	/** 数据操作字符串 */
	private String mOperate;
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private LPGrpEdorMainSchema mLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	/** 撤销申请原因 */
	private String delReason;
	private String reasonCode;

	public GEdorAppCancelBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		if (!prepareData()) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) cInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		// 撤销申请原因

		this.delReason = (String) mTransferData.getValueByName("DelReason");
		this.reasonCode = (String) mTransferData.getValueByName("ReasonCode");

		return true;
	}

	/**
	 * 检查数据的合法性
	 */
	private boolean checkData() {
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		if (!tLPEdorAppDB.getInfo()) {
			CError.buildErr(this, "查询保全申请信息失败!");
			return false;
		}
		if ("0".equals(tLPEdorAppDB.getEdorState())) {
			CError.buildErr(this, "保全已经生效不能撤销!");
			return false;
		}
		mLPEdorAppSchema.setSchema(tLPEdorAppDB.getSchema());

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean prepareData() {
		LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
		tLPGrpEdorMainDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		LPGrpEdorMainSet tLPGrpEdorMainSet = tLPGrpEdorMainDB.query();
		if (tLPGrpEdorMainDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPGrpEdorMainDB.mErrors);
			CError.buildErr(this, "查询团体保全主表信息失败!");
			return false;
		}
		if (tLPGrpEdorMainSet != null && tLPGrpEdorMainSet.size() > 0) {
			for (int i = 1; i <= tLPGrpEdorMainSet.size(); i++) {
				VData vData = new VData();
				vData.add(mGlobalInput);
				vData.add(mTransferData);
				vData.add(tLPGrpEdorMainSet.get(i));
				GEdorMainCancelBL tGEdorMainCancelBL = new GEdorMainCancelBL();
				if (!tGEdorMainCancelBL.submitData(vData, "")) {
					mErrors.copyAllErrors(tGEdorMainCancelBL.mErrors);
					CError.buildErr(this, "删除团体保全项目失败!");
				}
				mMap.add(tGEdorMainCancelBL.getMap());
			}
		}

		LOBEdorAppSchema tLOBEdorAppSchema = new LOBEdorAppSchema();
		Reflections tRef = new Reflections();
		tRef.transFields(tLOBEdorAppSchema, mLPEdorAppSchema);
		tLOBEdorAppSchema.setReason(delReason);
		tLOBEdorAppSchema.setReasonCode(reasonCode);
		tLOBEdorAppSchema.setMakeDate(PubFun.getCurrentDate());
		tLOBEdorAppSchema.setMakeTime(PubFun.getCurrentTime());
		mMap.put(tLOBEdorAppSchema, "DELETE&INSERT");
        SQLwithBindVariables sbv1=new SQLwithBindVariables();
        sbv1.sql("delete from LJSGet where otherno='"
				+ "?otherno?"
				+ "' and othernotype='10'");
        sbv1.put("otherno", mLPEdorAppSchema.getEdorAcceptNo());
		mMap.put(sbv1, "DELETE");
        SQLwithBindVariables sbv2=new SQLwithBindVariables();
        sbv2.sql("delete from LJSPay where otherno='"
				+ "?otherno?"
				+ "' and othernotype='10'");
        sbv2.put("otherno", mLPEdorAppSchema.getEdorAcceptNo());
		mMap.put(sbv2, "DELETE");

		mMap.put(mLPEdorAppSchema, "DELETE");

		// 保单解挂
		BqContHangUpBL tContHangUpBL = new BqContHangUpBL(mGlobalInput);
		if (!tContHangUpBL.hangUpEdorAccept(mLPEdorAppSchema.getEdorAcceptNo(),
				"0")) {
			CError.buildErr(this, "保全保单解挂失败!");
			return false;
		} else {
			MMap tMap = tContHangUpBL.getMMap();
			mMap.add(tMap); // del because DB hs not update zhangtao 2005-08-02
		}

		return true;
	}

	public MMap getMap() {
		return mMap;
	}

	public static void main(String[] args) {
		/*
		 * PEdorAppCancelBL tPEdorAppCancelBL=new PEdorAppCancelBL(); VData
		 * tVData=new VData(); LPEdorMainSchema tLPEdorMainSchema=new
		 * LPEdorMainSchema();
		 * tLPEdorMainSchema.setEdorNo("00000120020420000083");
		 * tLPEdorMainSchema.setPolNo("00000120020210000016");
		 * tLPEdorMainSchema.setEdorState("1");
		 * tVData.addElement(tLPEdorMainSchema);
		 * tPEdorAppCancelBL.submitData(tVData,"DELETE||EDOR");
		 */
	}
}
