/*
 * @(#)GrpEdorWSConfirmBL.java      Apr 26, 2006
 *
 * Copyright 2006 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */
package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJAPayPersonDB;
import com.sinosoft.lis.db.LPCustomerImpartDB;
import com.sinosoft.lis.db.LPCustomerImpartParamsDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LJAPayGrpSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpPolSchema;
import com.sinosoft.lis.tb.ProposalSignBL;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LJAPayGrpSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：lizhuo
 * @CreateDate: Apr 26, 2006
 * @version：1.0
 */
public class GrpEdorWSConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorWSConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!getInputData(mInputData)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		try {
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) cInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "GrpEdorWSConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLPGrpEdorItemSchema == null || mGlobalInput == null) {
			CError.buildErr(this, "接收数据不完整!");
		}
		return true;
	}

	private boolean checkData() {
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		tLPGrpEdorItemDB
				.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		if (!tLPGrpEdorItemDB.getInfo()) {
			CError.buildErr(this, "查询团体保全项目信息失败!");
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemDB.getSchema());
		return true;
	}

	private boolean dealData() {
		LCGrpPolSet aLCGrpPolSet = new LCGrpPolSet();

		LJAPayGrpSet aLJAPayGrpSet = new LJAPayGrpSet();

		LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
		LPGrpPolSet tLPGrpPolSet = new LPGrpPolSet();
		tLPGrpPolDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpPolDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpPolDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpPolSet = tLPGrpPolDB.query();
		for (int i = 1; i <= tLPGrpPolSet.size(); i++) {
			LCPolDB tLCPolDB = new LCPolDB();
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolDB.setGrpContNo(tLPGrpPolSet.get(i).getGrpContNo());
			tLCPolDB.setGrpPolNo(tLPGrpPolSet.get(i).getGrpPolNo());
			tLCPolSet = tLCPolDB.query();
			if (tLCPolSet == null || tLCPolSet.size() == 0) {
				CError.buildErr(this, "查询新增个人险种信息失败!");
				return false;
			}

			for (int j = 1; j <= tLCPolSet.size(); j++) {
				LCPolSet cLCPolSet = new LCPolSet();
				cLCPolSet.add(tLCPolSet.get(j));
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("EdorNo", mLPGrpEdorItemSchema
						.getEdorNo());
				tTransferData.setNameAndValue("EdorType", mLPGrpEdorItemSchema
						.getEdorType());
				ProposalSignBL tProposalSignBL = new ProposalSignBL();
				if (mGlobalInput.ComCode == null
						|| mGlobalInput.ComCode.equals("")) {
					mGlobalInput.ComCode = mGlobalInput.ManageCom;
				}
				mInputData.clear();
				mInputData.add(mGlobalInput);
				mInputData.add(cLCPolSet);
				mInputData.add(tLCPolSet.get(j).getContNo());
				mInputData.add(tTransferData);
				if (!tProposalSignBL.submitData(mInputData, "")) {
					mErrors.copyAllErrors(tProposalSignBL.mErrors);
					mErrors.addOneError("险种签单失败!");
					return false;
				}
				VData tResult = new VData();
				tResult = tProposalSignBL.getResult();
				MMap map = (MMap) tResult.getObjectByObjectName("MMap", 0);
				if (map != null) {
					mMap.add(map);
				}

				cLCPolSet = null;
				tTransferData = null;
				tProposalSignBL = null;
				tResult = null;
				map = null;
			}

			LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
			tLCGrpPolDB.setGrpPolNo(tLPGrpPolSet.get(i).getGrpPolNo());
			if (!tLCGrpPolDB.getInfo()) {
				CError.buildErr(this, "查询新增险种失败!险种号："
						+ tLPGrpPolSet.get(i).getGrpPolNo());
				return false;
			}

			String OldGrpPolNo = tLCGrpPolDB.getGrpPolNo();
			String GrpPolNo = PubFun1.CreateMaxNo("GRPPOLNO", PubFun
					.getNoLimit(tLCGrpPolDB.getManageCom()));
			if (GrpPolNo == null || "".equals(GrpPolNo)) {
				CError.buildErr(this, "产生险种号失败!");
				return false;
			}

			UpGrpPolNo(OldGrpPolNo, GrpPolNo);

			tLCGrpPolDB.setGrpPolNo(GrpPolNo);
			tLCGrpPolDB.setAppFlag("1");
			tLCGrpPolDB.setState("00019999");
			aLCGrpPolSet.add(tLCGrpPolDB.getSchema());

			LJAPayGrpSchema tLJAPayGrpSchema = initLJAPayGrp(tLPGrpPolSet
					.get(i));
			if (tLJAPayGrpSchema == null) {
				return false;
			}
			tLJAPayGrpSchema.setGrpPolNo(GrpPolNo);
			aLJAPayGrpSet.add(tLJAPayGrpSchema);

			mMap.put("delete from lcgrppol where grppolno = '" + OldGrpPolNo
					+ "'", "DELETE");
		}

		mMap.put(aLCGrpPolSet, "INSERT");
		mMap.put(aLJAPayGrpSet, "INSERT");

		Reflections tRef = new Reflections();
		LCCustomerImpartSet aLCCustomerImpartSet = new LCCustomerImpartSet();
		LCCustomerImpartParamsSet aLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
		LPCustomerImpartSet tLPCustomerImpartSet = new LPCustomerImpartSet();
		LPCustomerImpartDB tLPCustomerImpartDB = new LPCustomerImpartDB();
		tLPCustomerImpartDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPCustomerImpartDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPCustomerImpartSet = tLPCustomerImpartDB.query();
		if (tLPCustomerImpartSet.size() > 0) {
			for (int i = 1; i <= tLPCustomerImpartSet.size(); i++) {
				LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
				tRef.transFields(tLCCustomerImpartSchema, tLPCustomerImpartSet
						.get(i));
				tLCCustomerImpartSchema.setModifyDate(CurrDate);
				tLCCustomerImpartSchema.setModifyTime(CurrTime);
				aLCCustomerImpartSet.add(tLCCustomerImpartSchema);
			}
			mMap.put(aLCCustomerImpartSet, "DELETE&INSERT");
		}

		LPCustomerImpartParamsSet tLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();

		LPCustomerImpartParamsDB tLPCustomerImpartParamsDB = new LPCustomerImpartParamsDB();
		tLPCustomerImpartParamsDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPCustomerImpartParamsDB.setEdorType(mLPGrpEdorItemSchema
				.getEdorType());
		tLPCustomerImpartParamsSet = tLPCustomerImpartParamsDB.query();
		if (tLPCustomerImpartParamsSet.size() > 0) {
			for (int i = 1; i <= tLPCustomerImpartParamsSet.size(); i++) {
				LCCustomerImpartParamsSchema tLCCustomerImpartParamsSchema = new LCCustomerImpartParamsSchema();
				tRef.transFields(tLCCustomerImpartParamsSchema,
						tLPCustomerImpartParamsSet.get(i));
				tLCCustomerImpartParamsSchema.setModifyDate(CurrDate);
				tLCCustomerImpartParamsSchema.setModifyTime(CurrTime);
				aLCCustomerImpartParamsSet.add(tLCCustomerImpartParamsSchema);
			}
			mMap.put(aLCCustomerImpartParamsSet, "DELETE&INSERT");
		}

		return true;
	}

	private LJAPayGrpSchema initLJAPayGrp(LPGrpPolSchema schema) {
		LJAPayGrpSchema tLJAPayGrpSchema = new LJAPayGrpSchema();
		LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
		LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
		tLJAPayPersonDB.setGrpContNo(schema.getGrpContNo());
		tLJAPayPersonDB.setGrpPolNo(schema.getGrpPolNo());
		tLJAPayPersonDB.setPayType(schema.getEdorType());
		tLJAPayPersonSet = tLJAPayPersonDB.query();
		if (tLJAPayPersonSet == null || tLJAPayPersonSet.size() == 0) {
			CError.buildErr(this, "查询险种缴费信息失败!");
			return null;
		}
		Reflections tRef = new Reflections();
		tRef.transFields(tLJAPayGrpSchema, tLJAPayPersonSet.get(1));

		ExeSQL tES = new ExeSQL();
		tLJAPayGrpSchema
				.setSumActuPayMoney(tES
						.getOneValue("select nvl(sum(SumActuPayMoney),0) from ljapayperson where grppolno = '"
								+ schema.getGrpPolNo() + "'"));
		tLJAPayGrpSchema.setSumDuePayMoney(tLJAPayGrpSchema
				.getSumActuPayMoney());
		tLJAPayGrpSchema.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
		return tLJAPayGrpSchema;
	}

	private void UpGrpPolNo(String oldGrpPolNo, String grpPolNo) {
		String[] tab = { "LJAPayPerson", "LCPol" };
		for (int i = 0; i < tab.length; i++) {
			mMap.put("update " + tab[i] + " set GrpPolNo = '" + grpPolNo
					+ "' where GrpPolNo = '" + oldGrpPolNo + "'", "UPDATE");
		}
	}

	private boolean prepareData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
