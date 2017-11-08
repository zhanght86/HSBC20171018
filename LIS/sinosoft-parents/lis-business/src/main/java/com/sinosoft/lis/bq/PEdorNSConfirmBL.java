package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCBnfDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LCSpecDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LPBnfDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPCustomerImpartDB;
import com.sinosoft.lis.db.LPCustomerImpartParamsDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.db.LPSpecDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.operfee.IndiDueFeeCancelBL;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCContStateSchema;
import com.sinosoft.lis.schema.LCCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCDutySchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LCSpecSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.schema.LPSpecSchema;
import com.sinosoft.lis.tb.ProposalSignBL;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCContStateSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.lis.vschema.LPSpecSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 新增附加险保全确认类
 * </p>
 * 
 * <p>
 * Description: 保全确认类
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
public class PEdorNSConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorNSConfirmBL.class);
	

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	
	/**新增附加险类型*/
	private String NewAddType;
	
	private ValidateEdorData2 mValidateEdorData = null;
	
	private String mEdorNo = null;
	private String mEdorType = null;
	private String mContNo = null;

	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorNSConfirmBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(mInputData)) {
			return false;
		}
		// 数据准备操作（preparedata())
		if (!prepareData()) {
			return false;
		}
		this.setOperate("CONFIRM||NS");
		logger.debug("---" + mOperate);

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}
		NewAddType=mLPEdorItemSchema.getStandbyFlag1();
		
	    mEdorNo = mLPEdorItemSchema.getEdorNo();
	    mEdorType = mLPEdorItemSchema.getEdorType();
	    mContNo = mLPEdorItemSchema.getContNo();
	    mValidateEdorData = new ValidateEdorData2(mGlobalInput, mEdorNo,mEdorType, mContNo, "ContNo");
	    
		return true;
	}

	private boolean prepareData() {
		String MainPolNo = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		MainPolNo = mLPEdorItemSchema.getPolNo();
		
	  	//采用新的方式进行 CP 互换
	    String[] chgTables = {"LCAppnt","LCInsured"};
	    mValidateEdorData.changeData(chgTables);
	    mMap.add(mValidateEdorData.getMap());
	    
	    //处理特约
	    mMap.add(BqNameFun.DealSpecData(mLPEdorItemSchema));
	    
		Reflections tRef = new Reflections();
		LCPolSet aLCPolSet = new LCPolSet();
		LCDutySet aLCDutySet = new LCDutySet();
		LCPremSet aLCPremSet = new LCPremSet();
		LCGetSet aLCGetSet = new LCGetSet();
		LCCustomerImpartSet aLCCustomerImpartSet = new LCCustomerImpartSet();
		LCCustomerImpartParamsSet aLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
		LCContStateSet aLCContStateSet = new LCContStateSet();
		LCBnfSet aLCBnfSet = new LCBnfSet();


		// Pol
		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet.size() < 1) {
			mErrors.addOneError("险种表信息不存在!");
			return false;
		}

		/***********************************************************************
		 * 险种签单 进行换号等校验处理，不再进行计算。
		 **********************************************************************/
		LCPolSet cLCPolSet = new LCPolSet();
		LCPolSet pLCPolSet = new LCPolSet();

		for (int i = 1; i <= tLPPolSet.size(); i++) {
			LCPolSchema cLCPolSchema = new LCPolSchema();
			LPPolSchema cLPPolSchema = new LPPolSchema();
			LPPolSchema aLPPolSchema = new LPPolSchema();
			aLPPolSchema = tLPPolSet.get(i);
			tRef.transFields(cLCPolSchema, tLPPolSet.get(i));
			cLCPolSchema.setSpecifyValiDate("Y");
			//cLCPolSet.clear();
			//cLCPolSet.add(cLCPolSchema);
			String tOldPolNo = cLCPolSchema.getPolNo();
			if (!SignPol(cLCPolSchema)) {
				return false;
			}
			//更新实收费表的保单号
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql("update ljapayperson set polno='?polno?' where polno='?tOldPolNo?' ");
			sbv.put("polno", mLCPolSchema.getPolNo());
			sbv.put("tOldPolNo", tOldPolNo);
			mMap.put(sbv, "UPDATE");
			logger.debug(aLPPolSchema.getCValiDate());
			logger.debug(mLCPolSchema.getCValiDate());
			String ConfDate = "";
			//对于预约新增，则无收费数据,
			String trSQL = "select confdate from ljapay where otherno = '?otherno?'";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(trSQL);
			sbv1.put("otherno", mLPEdorItemSchema.getEdorAcceptNo());

			ConfDate = tExeSQL.getOneValue(sbv1);
			if (!"".equals(ConfDate)){
				ConfDate = tSSRS.GetText(1, 1);
				mLCPolSchema.setSignDate(PubFun.getLaterDate(mLPEdorItemSchema
						.getEdorValiDate(), ConfDate));
				mLCPolSchema.setSignTime(PubFun.getCurrentTime());
			}else
			{
				mLCPolSchema.setSignDate(PubFun.getCurrentDate());
				mLCPolSchema.setSignTime(PubFun.getCurrentTime());
			}
			//置上复核信息以及核保信息
			mLCPolSchema.setApproveCode(mLPEdorItemSchema.getApproveOperator());
			mLCPolSchema.setApproveDate(mLPEdorItemSchema.getApproveDate());
			mLCPolSchema.setApproveTime(mLPEdorItemSchema.getApproveTime());
			mLCPolSchema.setUWCode(aLPPolSchema.getUWCode());
			mLCPolSchema.setUWFlag(aLPPolSchema.getUWFlag());
			mLCPolSchema.setUWDate(aLPPolSchema.getUWDate());
			mLCPolSchema.setUWTime(aLPPolSchema.getUWTime());
			mLCPolSchema.setApproveFlag(mLPEdorItemSchema.getApproveFlag());
			pLCPolSet.add(mLCPolSchema);


			logger.debug(cLPPolSchema.getCValiDate());
			LCContStateSchema tLCContStateSchema = new LCContStateSchema();
			tLCContStateSchema.setContNo(mLPEdorItemSchema.getContNo());
			tLCContStateSchema.setPolNo(mLCPolSchema.getPolNo());
			tLCContStateSchema.setInsuredNo("000000");
			tLCContStateSchema.setStateType("Available");
			tLCContStateSchema.setState("0");
			tLCContStateSchema.setStartDate(mLCPolSchema.getCValiDate());
			tLCContStateSchema.setEndDate("");
			tLCContStateSchema.setOperator(mGlobalInput.Operator);
			tLCContStateSchema.setMakeDate(PubFun.getCurrentDate());
			tLCContStateSchema.setMakeTime(PubFun.getCurrentTime());
			tLCContStateSchema.setModifyDate(PubFun.getCurrentDate());
			tLCContStateSchema.setModifyTime(PubFun.getCurrentTime());
			aLCContStateSet.add(tLCContStateSchema);
		}

		for (int j = 1; j <= tLPPolSet.size(); j++) {
			String PolNo = tLPPolSet.get(j).getPolNo();
			String delPol = "delete from lcpol where polno = '?PolNo?'";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(delPol);
			sbv2.put("PolNo", PolNo);
			String delDuty = "delete from lcduty where polno = '?PolNo?'";
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(delDuty);
			sbv3.put("PolNo", PolNo);
			String delPrem = "delete from lcprem where polno = '?PolNo?'";
			SQLwithBindVariables sbv4=new SQLwithBindVariables();
			sbv4.sql(delPrem);
			sbv4.put("PolNo", PolNo);
			String delGet = "delete from lcget where polno = '?PolNo?'";
			SQLwithBindVariables sbv5=new SQLwithBindVariables();
			sbv5.sql(delGet);
			sbv5.put("PolNo", PolNo);
			String delLCBnf = "delete from lcbnf where polno = '?PolNo?'";
			SQLwithBindVariables sbv6=new SQLwithBindVariables();
			sbv6.sql(delLCBnf);
			sbv6.put("PolNo", PolNo);

			mMap.put(sbv2, "DELETE");
			mMap.put(sbv3, "DELETE");
			mMap.put(sbv4, "DELETE");
			mMap.put(sbv5, "DELETE");
			mMap.put(sbv6, "DELETE");
			
			String delpPol = "delete from lppol where EdorNo='?EdorNo?' and EdorType='?EdorType?' and polno = '?PolNo?'";
			SQLwithBindVariables sbv7=new SQLwithBindVariables();
			sbv7.sql(delpPol);
			sbv7.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv7.put("EdorType", mLPEdorItemSchema.getEdorType());
			sbv7.put("PolNo", PolNo);
			String delpDuty = "delete from lpduty where EdorNo='?EdorNo?' and EdorType='?EdorType?' and polno = '?PolNo?'";
			SQLwithBindVariables sbv8=new SQLwithBindVariables();
			sbv8.sql(delpDuty);
			sbv8.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv8.put("EdorType", mLPEdorItemSchema.getEdorType());
			sbv8.put("PolNo", PolNo);
			String delpPrem = "delete from lpprem where EdorNo='?EdorNo?' and EdorType='?EdorType?' and polno = '?PolNo?'";
			SQLwithBindVariables sbv9=new SQLwithBindVariables();
			sbv9.sql(delpPrem);
			sbv9.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv9.put("EdorType", mLPEdorItemSchema.getEdorType());
			sbv9.put("PolNo", PolNo);
			String delpGet = "delete from lpget where EdorNo='?EdorNo?' and EdorType='?EdorType?' and polno = '?PolNo?'";
			SQLwithBindVariables sbv10=new SQLwithBindVariables();
			sbv10.sql(delpGet);
			sbv10.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv10.put("EdorType", mLPEdorItemSchema.getEdorType());
			sbv10.put("PolNo", PolNo);
			
		    String delpBnf = "delete from lpBnf where EdorNo='?EdorNo?' and EdorType='?EdorType?' and polno = '?PolNo?'";
			SQLwithBindVariables sbv11=new SQLwithBindVariables();
			sbv11.sql(delpBnf);
			sbv11.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv11.put("EdorType", mLPEdorItemSchema.getEdorType());
			sbv11.put("PolNo", PolNo);

			mMap.put(sbv7, "DELETE");
			mMap.put(sbv8, "DELETE");
			mMap.put(sbv9, "DELETE");
			mMap.put(sbv10, "DELETE");
			
			mMap.put(sbv11, "DELETE");

			LCPolSchema tLCPolSchema = new LCPolSchema();
			tRef.transFields(tLCPolSchema, pLCPolSet.get(j));
			tLCPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLCPolSchema.setModifyTime(PubFun.getCurrentTime());

			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(tLPPolSet.get(j).getPolNo());
			if (!tLCPolDB.getInfo()) {
				mErrors.addOneError("查询已有险种信息失败!");
				return false;
			}

			aLCPolSet.add(tLCPolSchema);

			// Duty
			LPDutyDB tLPDutyDB = new LPDutyDB();
			LPDutySet tLPDutySet = new LPDutySet();
			tLPDutyDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPDutyDB.setPolNo(PolNo);
			tLPDutySet = tLPDutyDB.query();
			if (tLPDutySet.size() < 1) {
				mErrors.addOneError("险种表信息不存在!");
				return false;
			}
			for (int i = 1; i <= tLPDutySet.size(); i++) {
				LCDutySchema tLCDutySchema = new LCDutySchema();
				tRef.transFields(tLCDutySchema, tLPDutySet.get(i));
				tLCDutySchema.setPolNo(pLCPolSet.get(j).getPolNo());
//				if("1".equals(NewAddType))
//				{
//					tLCDutySchema.setPaytoDate(pLCPolSet.get(j).getCValiDate());		
//				}else
//				{
//
//				}
				tLCDutySchema.setPaytoDate(pLCPolSet.get(j).getPaytoDate());	

				tLCDutySchema.setModifyDate(PubFun.getCurrentDate());
				tLCDutySchema.setModifyTime(PubFun.getCurrentTime());

				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setPolNo(tLPPolSet.get(j).getPolNo());
				tLCDutyDB.setDutyCode(tLCDutySchema.getDutyCode());
				if (!tLCDutyDB.getInfo()) {
					mErrors.addOneError("查询已有险种信息失败!");
					return false;
				}

				aLCDutySet.add(tLCDutySchema);
			}

			// Prem
			LPPremDB tLPPremDB = new LPPremDB();
			LPPremSet tLPPremSet = new LPPremSet();
			tLPPremDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPPremDB.setPolNo(PolNo);
			tLPPremSet = tLPPremDB.query();
			if (tLPPremSet.size() < 1) {
				mErrors.addOneError("险种表信息不存在!");
				return false;
			}
			for (int i = 1; i <= tLPPremSet.size(); i++) {
				LCPremSchema tLCPremSchema = new LCPremSchema();
				tRef.transFields(tLCPremSchema, tLPPremSet.get(i));
				tLCPremSchema.setPolNo(pLCPolSet.get(j).getPolNo());
//				if("1".equals(NewAddType))
//				{
//					tLCPremSchema.setPaytoDate(pLCPolSet.get(j).getCValiDate());	
//					tLCPremSchema.setPayTimes(0);
//				}else
//				{
//
//				}
				tLCPremSchema.setPaytoDate(pLCPolSet.get(j).getPaytoDate());	
				tLCPremSchema.setPayTimes(1);
				tLCPremSchema.setUrgePayFlag("Y");

				tLCPremSchema.setModifyDate(PubFun.getCurrentDate());
				tLCPremSchema.setModifyTime(PubFun.getCurrentTime());

				aLCPremSet.add(tLCPremSchema);
			}

			// Get
			LPGetDB tLPGetDB = new LPGetDB();
			LPGetSet tLPGetSet = new LPGetSet();
			tLPGetDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPGetDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPGetDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPGetDB.setPolNo(PolNo);
			tLPGetSet = tLPGetDB.query();
			if (tLPGetSet.size() < 1) {
				mErrors.addOneError("险种表信息不存在!");
				return false;
			}
			for (int i = 1; i <= tLPGetSet.size(); i++) {
				LCGetSchema tLCGetSchema = new LCGetSchema();
				tRef.transFields(tLCGetSchema, tLPGetSet.get(i));
				tLCGetSchema.setPolNo(pLCPolSet.get(j).getPolNo());
				tLCGetSchema.setModifyDate(PubFun.getCurrentDate());
				tLCGetSchema.setModifyTime(PubFun.getCurrentTime());

				LCGetDB tLCGetDB = new LCGetDB();
				tLCGetDB.setPolNo(tLPPolSet.get(j).getPolNo());
				tLCGetDB.setDutyCode(tLCGetSchema.getDutyCode());
				tLCGetDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
				if (!tLCGetDB.getInfo()) {
					mErrors.addOneError("查询已有险种信息失败!");
					return false;
				}
				aLCGetSet.add(tLCGetSchema);
			}
			
			//Bnf
			LPBnfDB tLPBnfDB = new LPBnfDB();
			LPBnfSet tLPBnfSet = new LPBnfSet();
			tLPBnfDB.setContNo(mLPEdorItemSchema.getContNo());
			tLPBnfDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
			tLPBnfDB.setEdorType(mLPEdorItemSchema.getEdorType());
			tLPBnfDB.setPolNo(PolNo);
			tLPBnfSet = tLPBnfDB.query();
			if (tLPBnfSet.size() >= 1) {
				for (int i = 1; i <= tLPBnfSet.size(); i++) {
					LCBnfSchema tLCBnfSchema = new LCBnfSchema();
					tRef.transFields(tLCBnfSchema, tLPBnfSet.get(i));
					tLCBnfSchema.setPolNo(pLCPolSet.get(j).getPolNo());
					tLCBnfSchema.setModifyDate(PubFun.getCurrentDate());
					tLCBnfSchema.setModifyTime(PubFun.getCurrentTime());

					// POLNO, INSUREDNO, BNFTYPE, BNFNO, BNFGRADE
					LCBnfDB tLCBnfDB = new LCBnfDB();
					tLCBnfDB.setPolNo(tLPBnfSet.get(i).getPolNo());
					tLCBnfDB.setInsuredNo(tLPBnfSet.get(i).getInsuredNo());
					tLCBnfDB.setBnfType(tLPBnfSet.get(i).getBnfType());
					tLCBnfDB.setBnfNo(tLPBnfSet.get(i).getBnfNo());
					tLCBnfDB.setBnfGrade(tLPBnfSet.get(i).getBnfGrade());
					if (!tLCBnfDB.getInfo()) {
						mErrors.addOneError("查询已有险种信息失败!");
						return false;
					}
					aLCBnfSet.add(tLCBnfSchema);
				}
			}



		}

		LPContSchema aLPContSchema = new LPContSchema();
		LCContSchema aLCContSchema = new LCContSchema();
		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
		if (!tLPContDB.getInfo()) {
			mErrors.addOneError("查询批改保单信息失败!");
			return false;
		}
		tRef.transFields(aLCContSchema, tLPContDB.getSchema());
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		tRef.transFields(aLPContSchema, tLCContDB.getSchema());
		aLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		mMap.put(aLCContSchema, "UPDATE");
		mMap.put(aLPContSchema, "UPDATE");

		LPCustomerImpartSet tLPCustomerImpartSet = new LPCustomerImpartSet();
		LPCustomerImpartSchema aLPCustomerImpartSchema = new LPCustomerImpartSchema();
		aLPCustomerImpartSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPCustomerImpartSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		LPCustomerImpartDB tLPCustomerImpartDB = new LPCustomerImpartDB();
		tLPCustomerImpartDB.setSchema(aLPCustomerImpartSchema);
		tLPCustomerImpartSet = tLPCustomerImpartDB.query();
		if (tLPCustomerImpartSet.size() > 0) {
			for (int i = 1; i <= tLPCustomerImpartSet.size(); i++) {
				LCCustomerImpartSchema tLCCustomerImpartSchema = new LCCustomerImpartSchema();
				LPCustomerImpartSchema tLPCustomerImpartSchema = new LPCustomerImpartSchema();
				tRef.transFields(tLCCustomerImpartSchema, tLPCustomerImpartSet
						.get(i));
				tLCCustomerImpartSchema.setModifyDate(PubFun.getCurrentDate());
				tLCCustomerImpartSchema.setModifyTime(PubFun.getCurrentTime());
				aLCCustomerImpartSet.add(tLCCustomerImpartSchema);
			}
			mMap.put(aLCCustomerImpartSet, "DELETE&INSERT");
		}

		LPCustomerImpartParamsSet tLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();
		LPCustomerImpartParamsSchema aLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
		aLPCustomerImpartParamsSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		aLPCustomerImpartParamsSchema.setEdorType(mLPEdorItemSchema
				.getEdorType());
		LPCustomerImpartParamsDB tLPCustomerImpartParamsDB = new LPCustomerImpartParamsDB();
		tLPCustomerImpartParamsDB.setSchema(aLPCustomerImpartParamsSchema);
		tLPCustomerImpartParamsSet = tLPCustomerImpartParamsDB.query();
		if (tLPCustomerImpartParamsSet.size() > 0) {
			for (int i = 1; i <= tLPCustomerImpartParamsSet.size(); i++) {
				LCCustomerImpartParamsSchema tLCCustomerImpartParamsSchema = new LCCustomerImpartParamsSchema();
				LPCustomerImpartParamsSchema tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
				tRef.transFields(tLCCustomerImpartParamsSchema,
						tLPCustomerImpartParamsSet.get(i));
				tLCCustomerImpartParamsSchema.setModifyDate(PubFun
						.getCurrentDate());
				tLCCustomerImpartParamsSchema.setModifyTime(PubFun
						.getCurrentTime());
				aLCCustomerImpartParamsSet.add(tLCCustomerImpartParamsSchema);
			}
			mMap.put(aLCCustomerImpartParamsSet, "DELETE&INSERT");
		}


		mMap.put(pLCPolSet, "DELETE&INSERT");
		mMap.put(aLCDutySet, "DELETE&INSERT");
		mMap.put(aLCPremSet, "DELETE&INSERT");
		mMap.put(aLCBnfSet, "DELETE&INSERT");
		mMap.put(aLCGetSet, "DELETE&INSERT");
		mMap.put(aLCContStateSet, "INSERT");
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	private boolean SignPol(LCPolSchema cLCPolSchema) {
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("EdorNo", mLPEdorItemSchema.getEdorNo());
		tTransferData.setNameAndValue("EdorType", "NS");
		tTransferData.setNameAndValue("CValiDate", cLCPolSchema.getCValiDate());
		ProposalSignBL tProposalSignBL = new ProposalSignBL();
		VData aVData = new VData();
		if (mGlobalInput.ComCode == null || mGlobalInput.ComCode.equals("")) {
			mGlobalInput.ComCode = mGlobalInput.ManageCom;
		}
		aVData.add(mGlobalInput);
		aVData.add(cLCPolSchema);
		aVData.add(mLPEdorItemSchema.getContNo());
		aVData.add(tTransferData);
		if (!tProposalSignBL.submitData(aVData, "")) {
			mErrors.copyAllErrors(tProposalSignBL.mErrors);
			mErrors.addOneError("险种签单失败!");
			return false;
		}
		VData tResult = new VData();
		tResult = tProposalSignBL.getResult();
		mLCPolSchema = new LCPolSchema();
		mLCPolSchema = (LCPolSchema) tResult.getObjectByObjectName(
				"LCPolSchema", 0);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	/**
	 * 主函数，测试用
	 */
	public static void main(String[] args) {
		PEdorNSConfirmBL tPEdorNSConfirmBL = new PEdorNSConfirmBL();
		VData tVData = new VData();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo("6120051028000033");
		tLPEdorItemDB.setEdorType("NS");
		tLPEdorItemSet = tLPEdorItemDB.query();
		tLPEdorItemSchema = tLPEdorItemSet.get(1);

		GlobalInput tG = new GlobalInput();
		tG.Operator = "Lee";
		tG.ManageCom = "86110000";
		tG.ComCode = "86110000";

		tVData.add(tLPEdorItemSchema);
		tVData.add(tG);
		tPEdorNSConfirmBL.submitData(tVData, "");

	}

}
