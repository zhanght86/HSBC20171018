package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContStateDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContStateSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorMainSchema;
import com.sinosoft.lis.schema.LPGrpContStateSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LCGrpContStateSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */
public class GrpEdorREDetailBL {
private static Logger logger = Logger.getLogger(GrpEdorREDetailBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();
	/** 传出数据的容器 */
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 错误处理类 */
	public CErrors mErrors = new CErrors();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private String CurrDate = PubFun.getCurrentDate();
	private String CurrTime = PubFun.getCurrentTime();
	private String InsuredNo;

	public boolean submitData(VData cInputData, String cOperate) {
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
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
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
		LCGrpContStateSet tLCGrpContStateSet = dealGrpContState();
		if (tLCGrpContStateSet == null) {
			return false;
		}

		// String GrpPolNo = "";
		// for(int i = 1; i <= tLCGrpContStateSet.size(); i++){
		// if("1".equals(tLCGrpContStateSet.get(i).getState()) &&
		// ("".equals(tLCGrpContStateSet.get(i).getEndDate()) ||
		// tLCGrpContStateSet.get(i).getEndDate() == null)){
		// if(!ResumeGrpPol(tLCGrpContStateSet.get(i))) return false;
		// if("".equals(GrpPolNo)){
		// GrpPolNo = "'" + tLCGrpContStateSet.get(i).getGrpPolNo() + "'";
		// }
		// else{
		// GrpPolNo = ",'" + tLCGrpContStateSet.get(i).getGrpPolNo() + "'";
		// }
		// }
		// }
		//        
		// String sql = "select distinct contno,insuredno from lcpol a where grppolno in
		// (" + GrpPolNo + ") and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo() +
		// "' and appflag = '1'"
		// + " and exists(select 1 from ljspayperson where polno = a.polno)";
		String sql = "select distinct contno,insuredno from lcpol a where grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' and appflag = '1'"
				+ " and exists(select 1 from ljspayperson where polno = a.polno)";
		ExeSQL tES = new ExeSQL();
		SSRS tSSRS = tES.execSQL(sql);
		if (tSSRS == null || tSSRS.MaxRow < 1) {
			CError.buildErr(this, "");
			return false;
		}
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			InsuredNo = tSSRS.GetText(i, 2);
			if (!ResumeCont(tSSRS.GetText(i, 1)))
				return false;
		}

		sql = "update lpgrpedoritem a set getmoney = (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype),"
				+ " a.getinterest = (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and feefinatype = 'LX') "
				+ " where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "'"
				+ " and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "'";

		// 更新LPGrpEdorItem
		mLPGrpEdorItemSchema.setEdorState("3");
		mLPGrpEdorItemSchema.setModifyDate(CurrDate);
		mLPGrpEdorItemSchema.setModifyTime(CurrTime);
		mMap.put(mLPGrpEdorItemSchema, "UPDATE");

		mMap.put(sql, "UPDATE");

		mResult.add(mMap);
		return true;
	}

	private boolean ResumeCont(String ContNo) {
		Reflections tRef = new Reflections();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tRef.transFields(tLPEdorItemSchema, mLPGrpEdorItemSchema);
		tLPEdorItemSchema.setInsuredNo(InsuredNo);
		tLPEdorItemSchema.setPolNo("000000");
		tLPEdorItemSchema.setContNo(ContNo);

		VData tVD = new VData();
		tVD.add(mGlobalInput);
		tVD.add(tLPEdorItemSchema);
		PEdorREDetailBL tPEdorREDetailBL = new PEdorREDetailBL();
		if (!tPEdorREDetailBL.submitData(tVD, "NOQUERY")) {
			mErrors.copyAllErrors(tPEdorREDetailBL.mErrors);
			CError.buildErr(this, "复效处理失败!");
			return false;
		}
		MMap map = (MMap) tPEdorREDetailBL.getResult().getObjectByObjectName(
				"MMap", 0);
		if (map == null) {
			CError.buildErr(this, "获取处理结果失败!");
			return false;
		}
		mMap.add(map);

		map = null;
		tVD = null;
		tPEdorREDetailBL = null;
		tLPEdorItemSchema.setEdorState("1");
		LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
		tRef.transFields(tLPEdorMainSchema, mLPGrpEdorItemSchema);
		tRef.transFields(tLPEdorMainSchema, tLPEdorItemSchema);

		mMap.put(tLPEdorItemSchema, "DELETE&INSERT");
		mMap.put(tLPEdorMainSchema, "DELETE&INSERT");

		tRef = null;
		tLPEdorItemSchema = null;
		tLPEdorMainSchema = null;

		return true;
	}

	private boolean ResumeGrpPol(LCGrpContStateSchema cLCGrpContStateSchema) {
		return true;
	}

	private LCGrpContStateSet dealGrpContState() {
		Reflections ref = new Reflections();
		String sql;

		// 删除上次保存过的P表数据
		sql = " delete from lpgrpcontstate where 1=1 " + " and edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' " + " and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' "
				+ " and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "' ";
		mMap.put(sql, "DELETE");

		// 获取LCGrpContState
		LCGrpContStateDB tLCGrpContStateDB = new LCGrpContStateDB();
		tLCGrpContStateDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLCGrpContStateDB.setStateType("Available");
		LCGrpContStateSet tLCGrpContStateSet = tLCGrpContStateDB.query();
		if (tLCGrpContStateDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询团险保单状态信息失败!");
			return null;
		}

		// 更新LPGrpContState
		LPGrpContStateSchema tLPGrpContStateSchema;
		if (tLCGrpContStateSet != null && tLCGrpContStateSet.size() > 0) {
			// 将C表过期状态复制到P表
			for (int i = 1; i <= tLCGrpContStateSet.size(); i++) {
				tLPGrpContStateSchema = new LPGrpContStateSchema();
				ref.transFields(tLPGrpContStateSchema, tLCGrpContStateSet
						.get(i));

				tLPGrpContStateSchema.setEdorNo(mLPGrpEdorItemSchema
						.getEdorNo());
				tLPGrpContStateSchema.setEdorType(mLPGrpEdorItemSchema
						.getEdorType());
				// 结束上一状态
				if (tLPGrpContStateSchema.getEndDate() == null
						|| tLPGrpContStateSchema.getEndDate().trim().equals("")) {
					tLPGrpContStateSchema.setEndDate(PubFun.calDate(
							mLPGrpEdorItemSchema.getEdorValiDate(), -1, "D",
							null));
					tLPGrpContStateSchema.setModifyDate(CurrDate);
					tLPGrpContStateSchema.setModifyTime(CurrTime);
					tLPGrpContStateSchema.setOperator(mGlobalInput.Operator);
				}

				mMap.put(tLPGrpContStateSchema, "INSERT");
			}
		} else {
			CError.buildErr(this, "此团单没有失效险种!");
			return null;
		}

		// 将新状态插入P表
		LPGrpContStateSchema newLPGrpContStateSchema = new LPGrpContStateSchema();
		newLPGrpContStateSchema.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		newLPGrpContStateSchema.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		newLPGrpContStateSchema.setGrpContNo(mLPGrpEdorItemSchema
				.getGrpContNo());
		newLPGrpContStateSchema.setGrpPolNo("000000");
		newLPGrpContStateSchema.setStateType("Available");
		newLPGrpContStateSchema.setState("0");
		newLPGrpContStateSchema.setStartDate(mLPGrpEdorItemSchema
				.getEdorValiDate());
		newLPGrpContStateSchema.setEndDate("");
		newLPGrpContStateSchema.setOperator(mGlobalInput.Operator);
		newLPGrpContStateSchema.setMakeDate(CurrDate);
		newLPGrpContStateSchema.setMakeTime(CurrTime);
		newLPGrpContStateSchema.setModifyDate(CurrDate);
		newLPGrpContStateSchema.setModifyTime(CurrTime);
		mMap.put(newLPGrpContStateSchema, "INSERT");

		return tLCGrpContStateSet;
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
