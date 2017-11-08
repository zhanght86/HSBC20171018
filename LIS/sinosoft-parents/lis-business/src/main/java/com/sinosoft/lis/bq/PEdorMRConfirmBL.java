package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LOEngBonusPolDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEngBonusPolSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LOEngBonusPolSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEngBonusPolSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 主险续保处理
 * </p>
 * 
 * <p>
 * Description: 主险续保保全确认类
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
 * @version 1.0
 */
public class PEdorMRConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorMRConfirmBL.class);
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

	public PEdorMRConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作符
	 * @return: boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("===PEdorMRConfirmBL===");
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

		// 准备提交后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("after prepareOutputData...");

		return true;
	}

	/**
	 * C、P表数据互换
	 * 
	 * @return: boolean
	 */
	private boolean dealData() {
		Reflections tRef = new Reflections();
		// C表
		LCContSchema aLCContSchema = new LCContSchema();
		LCPolSet aLCPolSet = new LCPolSet();
		LCDutySet aLCDutySet = new LCDutySet();
		LCPremSet aLCPremSet = new LCPremSet();
		LCGetSet aLCGetSet = new LCGetSet();

		// P表
		LPContSchema aLPContSchema = new LPContSchema();
		LPPolSet aLPPolSet = new LPPolSet();
		LPDutySet aLPDutySet = new LPDutySet();
		LPPremSet aLPPremSet = new LPPremSet();
		LPGetSet aLPGetSet = new LPGetSet();

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			mErrors.addOneError("查询批改保单信息!");
			return false;
		}
		tRef.transFields(aLCContSchema, tLPContDB.getSchema());
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			mErrors.addOneError("查询保单信息失败!");
		}
		tRef.transFields(aLPContSchema, tLCContDB.getSchema());
		aLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());

		// 查询P表数据[险种保单]
		String NewPolNo = "";
		String OldPolNo = "";
		LPPolSet tLPPolSet = new LPPolSet();
		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSet.set(tLPPolDB.query());
		NewPolNo = tLPPolSet.get(1).getPolNo();
		for (int i = 1; i <= tLPPolSet.size(); i++) {
			// 将P表中数据放到C表中[险种保单]
			LCPolSchema tLCPolSchema = new LCPolSchema();
			tRef.transFields(tLCPolSchema, tLPPolSet.get(i));
			aLCPolSet.add(tLCPolSchema);
		}

		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCPolDB.setRiskCode("00609000");
		tLCPolDB.setAppFlag("1");
		tLCPolSet = tLCPolDB.query();
		if (tLCPolSet == null) {
			mErrors.copyAllErrors(tLCPolDB.mErrors);
			mErrors.addOneError(new CError("查询险种保单失败!"));
			return false;
		}

		// 将C表中数据放到P表中[险种保单]
		OldPolNo = tLCPolSet.get(1).getPolNo();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		tRef.transFields(tLPPolSchema, tLCPolSet.get(1));
		tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSchema.setPolNo(NewPolNo);
		tLPPolSchema.setMainPolNo(NewPolNo);
		tLPPolSchema.setAppFlag("4");
		aLPPolSet.add(tLPPolSchema);

		aLCPolSet.get(1).setPolNo(OldPolNo);
		aLCPolSet.get(1).setMainPolNo(OldPolNo);// 保持原险种号

		// 查询P表数据[保费项]
		LPPremSet tLPPremSet = new LPPremSet();
		LPPremDB tLPPremDB = new LPPremDB();
		tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPremDB.setPolNo(NewPolNo);
		tLPPremSet.set(tLPPremDB.query());
		for (int j = 1; j <= tLPPremSet.size(); j++) {
			// 将P表中数据放到C表中[保费项]
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tRef.transFields(tLCPremSchema, tLPPremSet.get(j).getSchema());
			tLCPremSchema.setPolNo(OldPolNo);
			tLCPremSchema.setModifyDate(PubFun.getCurrentDate());
			tLCPremSchema.setModifyTime(PubFun.getCurrentTime());
			aLCPremSet.add(tLCPremSchema);
		}

		// 查询C表数据[保费项]
		LCPremSet tLCPremSet = new LCPremSet();
		LCPremDB tLCPremDB = new LCPremDB();
		tLCPremDB.setPolNo(OldPolNo);
		tLCPremSet = tLCPremDB.query();
		for (int j = 1; j <= tLCPremSet.size(); j++) {
			// 将C表中数据放到P表中[保费项]
			LPPremSchema tLPPremSchema = new LPPremSchema();
			tRef.transFields(tLPPremSchema, tLCPremSet.get(j).getSchema());
			tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPremSchema.setPolNo(NewPolNo);
			tLPPremSchema.setModifyDate(PubFun.getCurrentDate());
			tLPPremSchema.setModifyTime(PubFun.getCurrentTime());
			aLPPremSet.add(tLPPremSchema);
		}

		// 查询P表数据[责任信息]
		LPDutySet tLPDutySet = new LPDutySet();
		LPDutyDB tLPDutyDB = new LPDutyDB();
		tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPDutyDB.setPolNo(NewPolNo);
		tLPDutySet = tLPDutyDB.query();
		for (int j = 1; j <= tLPDutySet.size(); j++) {
			// 将P表中数据放到C表中[责任信息]
			LCDutySchema tLCDutySchema = new LCDutySchema();
			tRef.transFields(tLCDutySchema, tLPDutySet.get(j).getSchema());
			tLCDutySchema.setPolNo(OldPolNo);
			tLCDutySchema.setModifyDate(PubFun.getCurrentDate());
			tLCDutySchema.setModifyTime(PubFun.getCurrentTime());
			aLCDutySet.add(tLCDutySchema);
		}

		// 查询C表数据[责任信息]
		LCDutySet tLCDutySet = new LCDutySet();
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(OldPolNo);
		tLCDutySet = tLCDutyDB.query();
		for (int j = 1; j <= tLCDutySet.size(); j++) {
			// 将C表中数据放到P表中[责任信息]
			LPDutySchema tLPDutySchema = new LPDutySchema();
			tRef.transFields(tLPDutySchema, tLCDutySet.get(j).getSchema());
			tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPDutySchema.setPolNo(NewPolNo);
			tLPDutySchema.setModifyDate(PubFun.getCurrentDate());
			tLPDutySchema.setModifyTime(PubFun.getCurrentTime());
			aLPDutySet.add(tLPDutySchema);
		}

		// 查询P表数据[给付项信息]
		LPGetDB tLPGetDB = new LPGetDB();
		LPGetSet tLPGetSet = new LPGetSet();
		tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPGetDB.setPolNo(NewPolNo);
		tLPGetSet = tLPGetDB.query();
		for (int j = 1; j <= tLPGetSet.size(); j++) {
			// 将P表中数据放到C表中[给付项信息]
			LCGetSchema tLCGetSchema = new LCGetSchema();
			tRef.transFields(tLCGetSchema, tLPGetSet.get(j).getSchema());
			tLCGetSchema.setPolNo(OldPolNo);
			tLCGetSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGetSchema.setModifyTime(PubFun.getCurrentTime());
			aLCGetSet.add(tLCGetSchema);
		}

		// 查询C表数据[给付项]
		LCGetSet tLCGetSet = new LCGetSet();
		LCGetDB tLCGetDB = new LCGetDB();
		tLCGetDB.setPolNo(OldPolNo);
		tLCGetSet = tLCGetDB.query();
		for (int j = 1; j <= tLCGetSet.size(); j++) {
			// 将C表中数据放到P表中[给付项]
			LPGetSchema tLPGetSchema = new LPGetSchema();
			tRef.transFields(tLPGetSchema, tLCGetSet.get(j).getSchema());
			tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPGetSchema.setPolNo(NewPolNo);
			tLPGetSchema.setModifyDate(PubFun.getCurrentDate());
			tLPGetSchema.setModifyTime(PubFun.getCurrentTime());
			aLPGetSet.add(tLPGetSchema);
		}

		// 红利表处理
		LPEngBonusPolSet tLPEngBonusPolSet = new LPEngBonusPolSet();

		LOEngBonusPolDB tLOEngBonusPolDB = new LOEngBonusPolDB();
		LOEngBonusPolSet tLOEngBonusPolSet = new LOEngBonusPolSet();
		tLOEngBonusPolDB.setPolNo(OldPolNo);
		tLOEngBonusPolSet = tLOEngBonusPolDB.query();
		if (tLOEngBonusPolSet.size() > 0) {
			for (int i = 1; i <= tLOEngBonusPolSet.size(); i++) {
				LPEngBonusPolSchema tLPEngBonusPolSchema = new LPEngBonusPolSchema();
				tRef
						.transFields(tLPEngBonusPolSchema, tLOEngBonusPolSet
								.get(i));
				tLPEngBonusPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPEngBonusPolSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				tLPEngBonusPolSchema.setModifyDate(PubFun.getCurrentDate());
				tLPEngBonusPolSchema.setModifyTime(PubFun.getCurrentTime());
				tLPEngBonusPolSet.add(tLPEngBonusPolSchema);
			}
		}
		map.put(tLPEngBonusPolSet, "DELETE&INSERT");
		map.put(tLOEngBonusPolSet, "DELETE");

		String dellcpol = "delete from lcpol where polno = '?OldPolNo?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(dellcpol);
		sbv1.put("OldPolNo", OldPolNo);
		String dellcduty = "delete from lcduty where polno = '?OldPolNo?'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(dellcduty);
		sbv2.put("OldPolNo", OldPolNo);
		String dellcprem = "delete from lcprem where polno = '?OldPolNo?'";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(dellcprem);
		sbv3.put("OldPolNo", OldPolNo);
		String dellcget = "delete from lcget where polno = '?OldPolNo?'";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(dellcget);
		sbv4.put("OldPolNo", OldPolNo);
		String dellppol = "delete from lppol where polno = '?NewPolNo?' and edortype = 'MR'";
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(dellppol);
		sbv5.put("NewPolNo", NewPolNo);
		String dellpduty = "delete from lpduty where polno = '?NewPolNo?' and edortype = 'MR'";
		SQLwithBindVariables sbv6=new SQLwithBindVariables();
		sbv6.sql(dellpduty);
		sbv6.put("NewPolNo", NewPolNo);
		String dellpprem = "delete from lpprem where polno = '?NewPolNo?' and edortype = 'MR'";
		SQLwithBindVariables sbv7=new SQLwithBindVariables();
		sbv7.sql(dellpprem);
		sbv7.put("NewPolNo", NewPolNo);
		String dellpget = "delete from lpget where polno = '?NewPolNo?' and edortype = 'MR'";
		SQLwithBindVariables sbv8=new SQLwithBindVariables();
		sbv8.sql(dellpget);
		sbv8.put("NewPolNo", NewPolNo);
		
		map.put(sbv1, "DELETE");
		map.put(sbv2, "DELETE");
		map.put(sbv3, "DELETE");
		map.put(sbv4, "DELETE");
		map.put(sbv5, "DELETE");
		map.put(sbv6, "DELETE");
		map.put(sbv7, "DELETE");
		map.put(sbv8, "DELETE");

		map.put(aLCContSchema, "DELETE&INSERT");
		map.put(aLCPolSet, "DELETE&INSERT");
		map.put(aLCDutySet, "DELETE&INSERT");
		map.put(aLCPremSet, "DELETE&INSERT");
		map.put(aLCGetSet, "DELETE&INSERT");

		map.put(aLPContSchema, "DELETE&INSERT");
		map.put(aLPPolSet, "DELETE&INSERT");
		map.put(aLPDutySet, "DELETE&INSERT");
		map.put(aLPPremSet, "DELETE&INSERT");
		map.put(aLPGetSet, "DELETE&INSERT");

		// //更新保全项目状态为保全确认
		// mLPEdorItemSchema.setEdorState("0");
		// mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
		// mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		// mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
		//
		// map.put(mLPEdorItemSchema, "UPDATE");

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
	 * 准备提交后台的数据
	 * 
	 * @return: boolean
	 */
	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorMRDetailBLF";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错:" + ex.toString();
			mErrors.addOneError(tError);
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
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "lee";
		LPEdorItemSchema tlp = new LPEdorItemSchema();
		LPEdorItemDB dd = new LPEdorItemDB();
		dd.setEdorAcceptNo("6120050824000019");
		tlp = dd.query().get(1);
		tlp.setOperator(tG.Operator);
		VData avd = new VData();
		avd.add(tG);
		avd.add(tlp);
		PEdorMRConfirmBL mr = new PEdorMRConfirmBL();
		mr.submitData(avd, "");
		PubSubmit tSubmit = new PubSubmit();
		VData avdd = new VData();
		avdd = mr.getResult();
		if (!tSubmit.submitData(avdd, "")) {

		}
	}

}
