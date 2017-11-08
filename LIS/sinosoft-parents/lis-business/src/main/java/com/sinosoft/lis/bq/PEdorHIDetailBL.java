package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

/**
 * <p>
 * Title: Web 业务系统
 * </p>
 * <p>
 * Description: 增补告知
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * @author pst
 * @version 1.0
 */


import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCCSpecDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.f1print.PrintManagerBL;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCCustomerImpartSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPCSpecSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPCustomerImpartParamsSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCCSpecSet;
import com.sinosoft.lis.vschema.LCCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPCSpecSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPCustomerImpartParamsSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.tb.CustomerImpartBL;

public class PEdorHIDetailBL {
private static Logger logger = Logger.getLogger(PEdorHIDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private MMap mMap = new MMap();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private Reflections mReflections = new Reflections();

	/** 计算要素 */
	private BqCalBase mBqCalBase = new BqCalBase();

	/** 全局数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet(); // 因为是客户层，所以根据保全受理号去做

	private LPCustomerImpartSet mLPCustomerImpartSet = new LPCustomerImpartSet();

	private GlobalInput mGlobalInput = new GlobalInput();
	/**客户角色 0 投保人，1 被保人*/
	private String tCustomerRole="";

	public PEdorHIDetailBL() {
		logger.debug("constructor");
	}

	public static void main(String[] args) {
		PEdorHIDetailBL tPEdorHIDetailBL = new PEdorHIDetailBL();
	}
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

	public VData getResult() {
		return mResult;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mLPCustomerImpartSet = (LPCustomerImpartSet) mInputData
					.getObjectByObjectName("LPCustomerImpartSet", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			tCustomerRole=mLPEdorItemSchema.getStandbyFlag1();
			LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
			tLPEdorAppDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
			if (!tLPEdorAppDB.getInfo()) {
				mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
				CError tError = new CError();
				tError.errorMessage = "保全受理信息查询失败!";
				mErrors.addOneError(tError);
				return false;
			}
			mLPEdorAppSchema.setSchema(tLPEdorAppDB.getSchema());
		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败！");
			return false;
		}

		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		if (mLPCustomerImpartSet == null || mLPCustomerImpartSet.size() <= 0) {
			CError tError = new CError();
			tError.moduleName = "PEdorHIDetailBL";
			tError.functionName = "checkData";
			tError.errorMessage = "未获得健康告知信息！";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备需要保存的数据 注：界面不传ContNo，这里根据不同ContNo从一组健康告知生成多组
	 */
	private boolean dealData() {
		// 获得此时的日期和时间
		String strCurrentDate = PubFun.getCurrentDate();
		String strCurrentTime = PubFun.getCurrentTime();
		String tSql = "";
		SSRS tSSRS = null;
		ExeSQL tExeSQL = new ExeSQL();
		CustomerImpartBL tCustomerImpartBL = null;
		VData tempVData = null;
		Reflections tRef = new Reflections();
		// 最新批次号
		int newPatchNo = 0;
		// 需要更新的记录
		LPCustomerImpartSet allLPCustomerImpartSet = new LPCustomerImpartSet();
		LPCustomerImpartSchema tLPCustomerImpartSchema = null;
		LPCustomerImpartParamsSet allLPCustomerImpartParamsSet = new LPCustomerImpartParamsSet();
		LPCustomerImpartParamsSchema tLPCustomerImpartParamsSchema = null;
		LCCustomerImpartSchema tLCCustomerImpartSchema = null;

		// 一组ContNo相同的记录
		LCCustomerImpartSet tLCCustomerImpartSet = null;
		LCCustomerImpartParamsSet tLCCustomerImpartParamsSet = null;


		

		
		// 查询客户所有相关保单（客户为投保人）
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = null;



		// 为了人工核保能查到数据，这里将被告知人涉及的所有保单和险种数据复制到P表
		LPContSet tLPContSet = new LPContSet();
		LPContSchema tLPContSchema = null;
		LCPolDB tLCPolDB = new LCPolDB();
		LPPolSet tLPPolSet = new LPPolSet();
		LCPolSet tContLCPolSet = null;
		LPPolSchema tLPPolSchema = null;
		LCDutyDB tLCDutyDB = new LCDutyDB();
		LPDutySet tLPDutySet = new LPDutySet();
		LCDutySet tContLCDutySet = null;
		LPDutySchema tLPDutySchema = null;
		LCPremDB tLCPremDB = new LCPremDB();
		LPPremSet tLPPremSet = new LPPremSet();
		LCPremSet tContLCPremSet = null;
		LPPremSchema tLPPremSchema = null;
		LCGetDB tLCGetDB = new LCGetDB();
		LPGetSet tLPGetSet = new LPGetSet();
		LCGetSet tContLCGetSet = null;
		LPGetSchema tLPGetSchema = null;

		
		//投保人
		LCAppntDB tLCAppntDB = new LCAppntDB();
		LCAppntSchema tLCAppntSchema = null;
		LPAppntSet tLPAppntSet = new LPAppntSet();
		LPAppntSchema tLPAppntSchema = null;
		
		tLCAppntDB.setContNo(mLPEdorItemSchema.getContNo());		
		tLCAppntSchema=tLCAppntDB.query().get(1);
		tLPAppntSchema = new LPAppntSchema();
		mReflections.transFields(tLPAppntSchema, tLCAppntSchema);
		tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntSet.add(tLPAppntSchema);
		
		//被保人
		// 查询客户所有相关保单（客户为投保人）
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		LCInsuredSchema tLCInsuredSchema = null;
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();
		LPInsuredSchema tLPInsuredSchema = null;
		
		
		tLCInsuredDB.setContNo(mLPEdorItemSchema.getContNo());		
		tLCInsuredSchema=tLCInsuredDB.query().get(1);
		tLPInsuredSchema = new LPInsuredSchema();
		mReflections.transFields(tLPInsuredSchema, tLCInsuredSchema);
		tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredSet.add(tLPInsuredSchema);
		
		
		//为核保准备数据
		LPCSpecSet mLPCSpecSet = new LPCSpecSet();
		LCCSpecDB tLCCSpecDB = new LCCSpecDB();
		LCCSpecSet tLCCSpecSet = new LCCSpecSet();
		tLCCSpecDB.setContNo(mLPEdorItemSchema.getContNo());
		tLCCSpecSet = tLCCSpecDB.query();
		if(tLCCSpecSet.size()>0)
		{
			for(int k=1;k<=tLCCSpecSet.size();k++)
			{
				LPCSpecSchema mLPCSpecSchema = new LPCSpecSchema();
				tRef.transFields(mLPCSpecSchema, tLCCSpecSet.get(k));	
				mLPCSpecSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				mLPCSpecSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				mLPCSpecSet.add(mLPCSpecSchema);				
			}
			mMap.put(mLPCSpecSet, "DELETE&INSERT");
		}
		
		// 获取相应保单信息
		tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorHIDetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询保单信息失败！保单号："
					+ mLPEdorItemSchema.getContNo();
			this.mErrors.addOneError(tError);
			return false;
		}
		tLCContSchema.setSchema(tLCContDB.getSchema());

		tLCCustomerImpartSet = new LCCustomerImpartSet();
		// 复制数据（完全复制）
		for (int m = 1; m <= mLPCustomerImpartSet.size(); m++) {
			tLCCustomerImpartSchema = new LCCustomerImpartSchema();
			tLCCustomerImpartSchema.setContNo(mLPCustomerImpartSet.get(m)
					.getContNo());
			tLCCustomerImpartSchema.setCustomerNo(mLPCustomerImpartSet.get(m)
					.getCustomerNo());
			tLCCustomerImpartSchema.setCustomerNoType(mLPCustomerImpartSet.get(
					m).getCustomerNoType());
			tLCCustomerImpartSchema.setGrpContNo(mLPCustomerImpartSet.get(m)
					.getGrpContNo());
			tLCCustomerImpartSchema.setImpartCode(mLPCustomerImpartSet.get(m)
					.getImpartCode());
			tLCCustomerImpartSchema.setImpartContent(mLPCustomerImpartSet
					.get(m).getImpartContent());
			tLCCustomerImpartSchema.setImpartParamModle(mLPCustomerImpartSet
					.get(m).getImpartParamModle());
			tLCCustomerImpartSchema.setImpartVer(mLPCustomerImpartSet.get(m)
					.getImpartVer());
			tLCCustomerImpartSchema.setMakeDate(mLPCustomerImpartSet.get(m)
					.getMakeDate());
			tLCCustomerImpartSchema.setMakeTime(mLPCustomerImpartSet.get(m)
					.getMakeTime());
			tLCCustomerImpartSchema.setModifyDate(mLPCustomerImpartSet.get(m)
					.getModifyDate());
			tLCCustomerImpartSchema.setModifyTime(mLPCustomerImpartSet.get(m)
					.getModifyTime());
			tLCCustomerImpartSchema.setOperator(mLPCustomerImpartSet.get(m)
					.getOperator());
			tLCCustomerImpartSchema.setPatchNo(mLPCustomerImpartSet.get(m)
					.getPatchNo());
			tLCCustomerImpartSchema.setProposalContNo(mLPCustomerImpartSet.get(
					m).getProposalContNo());
			tLCCustomerImpartSchema.setPrtFlag(mLPCustomerImpartSet.get(m)
					.getPrtFlag());
			tLCCustomerImpartSchema.setPrtNo(mLPCustomerImpartSet.get(m)
					.getPrtNo());
			tLCCustomerImpartSchema.setUWClaimFlg(mLPCustomerImpartSet.get(m)
					.getUWClaimFlg());
			tLCCustomerImpartSchema.setCustomerNoType(mLPCustomerImpartSet.get(
					m).getCustomerNoType());
			tLCCustomerImpartSet.add(tLCCustomerImpartSchema);
		}
		// 设置所有告知信息的客户号码
		for (int j = 1; j <= tLCCustomerImpartSet.size(); j++) {
			tLCCustomerImpartSet.get(j).setContNo(tLCContSchema.getContNo());
			tLCCustomerImpartSet.get(j).setGrpContNo(
					tLCContSchema.getGrpContNo());
			tLCCustomerImpartSet.get(j).setPrtNo(tLCContSchema.getPrtNo());
			tLCCustomerImpartSet.get(j).setProposalContNo(
					tLCContSchema.getProposalContNo());
			
			// 获得最新批次号（从0开始）
			tSql = "select (case when (SELECT max(PatchNo) FROM LCCustomerImpart WHERE CustomerNo='?CustomerNo?' and ImpartVer='?ImpartVer?' and ImpartCode='?ImpartCode?') is not null then (SELECT max(PatchNo) FROM LCCustomerImpart WHERE CustomerNo='?CustomerNo?' and ImpartVer='?ImpartVer?' and ImpartCode='?ImpartCode?') else 0 end) from dual";
		    SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		    sqlbv.sql(tSql);
		    sqlbv.put("CustomerNo", mLPEdorItemSchema.getInsuredNo());
		    sqlbv.put("ImpartVer", tLCCustomerImpartSet.get(j).getImpartVer());
		    sqlbv.put("ImpartCode", tLCCustomerImpartSet.get(j).getImpartCode());
			tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv);
			if (tSSRS != null && tSSRS.MaxRow > 0) {
				newPatchNo = Integer.parseInt(tSSRS.GetText(1, 1));
				newPatchNo++;
			}
			
			tLCCustomerImpartSet.get(j).setPatchNo(newPatchNo); // 最新批次号
		}
		tCustomerImpartBL = new CustomerImpartBL();
		tempVData = new VData();
		tempVData.add(tLCCustomerImpartSet);
		tempVData.add(mGlobalInput);
		try {
			tCustomerImpartBL.submitData(tempVData, "IMPART||DEAL");
			if (tCustomerImpartBL.mErrors.needDealError()) {
				this.mErrors.copyAllErrors(tCustomerImpartBL.getErrors());
				return false;
			}
		} catch (Exception e) {
			this.mErrors.copyAllErrors(tCustomerImpartBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "PEdorHIDetailBL";
			tError.functionName = "dealData";
			tError.errorMessage = "参数处理错误，请检查输入参数是否正确！";
			this.mErrors.addOneError(tError);
			return false;
		}
		tempVData.clear();
		tempVData = tCustomerImpartBL.getResult();
		if (null != (LCCustomerImpartSet) tempVData.getObjectByObjectName(
				"LCCustomerImpartSet", 0)) {
			tLCCustomerImpartSet = (LCCustomerImpartSet) tempVData
					.getObjectByObjectName("LCCustomerImpartSet", 0);
		}

		if (null != (LCCustomerImpartParamsSet) tempVData
				.getObjectByObjectName("LCCustomerImpartParamsSet", 0)) {
			tLCCustomerImpartParamsSet = new LCCustomerImpartParamsSet();
			tLCCustomerImpartParamsSet = (LCCustomerImpartParamsSet) tempVData
					.getObjectByObjectName("LCCustomerImpartParamsSet", 0);
		}
		// 获得P表数据
		if (tLCCustomerImpartSet == null || tLCCustomerImpartSet.size() <= 0) {
			// XinYQ added on 2007-05-28
			CError.buildErr(this, "请选择添加告知项目！");
			return false;
		} else {
			for (int k = 1; k <= tLCCustomerImpartSet.size(); k++) {
				tLPCustomerImpartSchema = new LPCustomerImpartSchema();
				tRef.transFields(tLPCustomerImpartSchema, tLCCustomerImpartSet
						.get(k));
				tLPCustomerImpartSchema
						.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPCustomerImpartSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());
				allLPCustomerImpartSet.add(tLPCustomerImpartSchema);
			}
		}

		if (tLCCustomerImpartParamsSet != null
				&& tLCCustomerImpartParamsSet.size() > 0) {
			for (int i = 1; i <= tLCCustomerImpartParamsSet.size(); i++) {
				tLPCustomerImpartParamsSchema = new LPCustomerImpartParamsSchema();
				tRef.transFields(tLPCustomerImpartParamsSchema,
						tLCCustomerImpartParamsSet.get(i));

				tLPCustomerImpartParamsSchema.setEdorNo(mLPEdorItemSchema
						.getEdorNo());
				tLPCustomerImpartParamsSchema.setEdorType(mLPEdorItemSchema
						.getEdorType());

				allLPCustomerImpartParamsSet.add(tLPCustomerImpartParamsSchema);
			}
		}




		// 复制保单数据到P表，为人工核保
		tLPContSchema = new LPContSchema();
		mReflections.transFields(tLPContSchema, tLCContSchema);
		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSet.add(tLPContSchema);
		// 复制保单险种数据到P表，为人工核保
		tLCPolDB.setContNo(tLCContSchema.getContNo());
		tLCPolDB.setAppFlag("1");
		tContLCPolSet = new LCPolSet();
		tContLCPolSet = tLCPolDB.query();
		if (tContLCPolSet != null && tContLCPolSet.size() > 0) {
			for (int m = 1; m <= tContLCPolSet.size(); m++) {
				tLPPolSchema = new LPPolSchema();
				mReflections.transFields(tLPPolSchema, tContLCPolSet.get(m));
				tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
				tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
				tLPPolSet.add(tLPPolSchema);
				// 复制保单险种责任数据到P表，为人工核保
				tLCDutyDB.setPolNo(tLPPolSchema.getPolNo());
				tContLCDutySet = new LCDutySet();
				tContLCDutySet = tLCDutyDB.query();
				if (tContLCDutySet != null && tContLCDutySet.size() > 0) {
					for (int k = 1; k <= tContLCDutySet.size(); k++) {
						tLPDutySchema = new LPDutySchema();
						mReflections.transFields(tLPDutySchema, tContLCDutySet.get(k));
						tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
						tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
						tLPDutySet.add(tLPDutySchema);
					}
				}
				// 复制保单险种给付责任数据到P表，为人工核保
				tLCPremDB.setPolNo(tLPPolSchema.getPolNo());
				tContLCPremSet = new LCPremSet();
				tContLCPremSet = tLCPremDB.query();
				if (tContLCPremSet != null && tContLCPremSet.size() > 0) {
					for (int i = 1; i <= tContLCPremSet.size(); i++) {
						tLPPremSchema = new LPPremSchema();
						mReflections.transFields(tLPPremSchema, tContLCPremSet.get(i));
						tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
						tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
						tLPPremSet.add(tLPPremSchema);
					}
				}
				// 复制保单险种领取责任数据到P表，为人工核保
				tLCGetDB.setPolNo(tLPPolSchema.getPolNo());
				tContLCGetSet = new LCGetSet();
				tContLCGetSet = tLCGetDB.query();
				if (tContLCGetSet != null && tContLCGetSet.size() > 0) {
					for (int l = 1; l <= tContLCGetSet.size(); l++) {
						tLPGetSchema = new LPGetSchema();
						mReflections.transFields(tLPGetSchema, tContLCGetSet.get(l));
						tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
						tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
						tLPGetSet.add(tLPGetSchema);
					}
				}
			}
		}

		String tSqla = "DELETE FROM LPCustomerImpart WHERE LPCustomerImpart.EdorType='?EdorType?' and exists(select 'X' from LPEdorItem where EdorAcceptNo='?EdorAcceptNo?' and EdorNo=LPCustomerImpart.EdorNo)";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(tSqla);
		sbv1.put("EdorType", mLPEdorItemSchema.getEdorType());
		sbv1.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
		mMap.put(sbv1, "DELETE");
	   String tSqlb = "DELETE FROM LPCustomerImpartParams WHERE LPCustomerImpartParams.EdorType='?EdorType?' and exists(select 'X' from LPEdorItem where EdorAcceptNo='?EdorAcceptNo?' and EdorNo=LPCustomerImpartParams.EdorNo)";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(tSqlb);
		sbv2.put("EdorType", mLPEdorItemSchema.getEdorType());
		sbv2.put("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo());
	   mMap.put(sbv2, "DELETE");
		
		// 修改“个险保全项目表”相应信息
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setGetMoney(0);
		mLPEdorItemSchema.setStandbyFlag1(tCustomerRole);
		mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
		mLPEdorItemSchema.setModifyDate(strCurrentDate);
		mLPEdorItemSchema.setModifyTime(strCurrentTime);
		
	    String tSqlP = "UPDATE LPEdorItem  set StandbyFlag1='?tCustomerRole?',GetMoney=0,EdorState=3  WHERE EdorType='?EdorType?' and edorno='?edorno?' ";
        SQLwithBindVariables sbv3=new SQLwithBindVariables();
        sbv3.sql(tSqlP);
        sbv3.put("tCustomerRole", tCustomerRole);
        sbv3.put("EdorType", mLPEdorItemSchema.getEdorType());
        sbv3.put("edorno", mLPEdorItemSchema.getEdorNo());
	    mMap.put(sbv3, "UPDATE");
	   
		if (allLPCustomerImpartSet.size() > 0) {
			mMap.put(allLPCustomerImpartSet, "DELETE&INSERT");
		}
		if (allLPCustomerImpartParamsSet.size() > 0) {
			mMap.put(allLPCustomerImpartParamsSet, "DELETE&INSERT");
		}
		
		String tSqlc = "DELETE FROM LOPRTManager WHERE LOPRTManager.otherno='?otherno?' and LOPRTManager.StandbyFlag2='?StandbyFlag2?'";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(tSqlc);
		sbv4.put("otherno", mLPEdorItemSchema.getEdorAcceptNo());
		sbv4.put("StandbyFlag2", mLPEdorItemSchema.getEdorNo());
		mMap.put(sbv4, "DELETE");
		mLPEdorItemSchema.setManageCom(tLCContSchema.getManageCom());
		if (!InsertHIPRT(mLPEdorItemSchema)) {
			CError.buildErr(this, "插入保全增补健康告知信息通知书失败!");
			return false;
		}

		mMap.put(tLPInsuredSet, "DELETE&INSERT");
		mMap.put(tLPAppntSet, "DELETE&INSERT");
		mMap.put(tLPContSet, "DELETE&INSERT");
		mMap.put(tLPPolSet, "DELETE&INSERT");
		mMap.put(tLPDutySet, "DELETE&INSERT");
		mMap.put(tLPPremSet, "DELETE&INSERT");
		mMap.put(tLPGetSet, "DELETE&INSERT");

		mResult.clear();
		mResult.add(mMap);
		mResult.add(mBqCalBase);

		return true;
	}
	/**
	 * 往打印管理表插入个险审批通知书
	 * 
	 * @return boolean
	 */
	private boolean InsertHIPRT(LPEdorItemSchema mLPEdorItemSchema) {
		LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
		LOPRTManagerSchema mmLOPRTManagerSchema;
		try {
			String Code = PrintManagerBL.CODE_PEdorHIInfo;// 保全增补健康告知信息通知书

			mmLOPRTManagerSchema = new LOPRTManagerSchema();
			String strNoLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
			String sPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", strNoLimit); // 生成打印流水号
			mmLOPRTManagerSchema.setPrtSeq(sPrtSeq);
			mmLOPRTManagerSchema.setOtherNo(mLPEdorItemSchema.getEdorAcceptNo());
			mmLOPRTManagerSchema.setOtherNoType(PrintManagerBL.ONT_EDORACCEPT); // 保单号
			mmLOPRTManagerSchema.setCode(Code); // 打印类型
			mmLOPRTManagerSchema.setManageCom(mLPEdorItemSchema.getManageCom()); // 管理机构
			mmLOPRTManagerSchema.setAgentCode(""); // 代理人编码
			mmLOPRTManagerSchema.setReqCom(mGlobalInput.ComCode);
			mmLOPRTManagerSchema.setReqOperator(mGlobalInput.Operator);
			mmLOPRTManagerSchema.setPrtType(PrintManagerBL.PT_FRONT); // 前台打印
			mmLOPRTManagerSchema.setStateFlag("0"); // 打印状态
			mmLOPRTManagerSchema.setPatchFlag("0"); // 补打标志
			mmLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
			mmLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
			mmLOPRTManagerSchema.setOldPrtSeq(sPrtSeq);
			mmLOPRTManagerSchema.setStandbyFlag1(mLPEdorItemSchema.getContNo());//保单号
			mmLOPRTManagerSchema.setStandbyFlag2(mLPEdorItemSchema.getEdorNo());//
			
			mmLOPRTManagerSchema.setStandbyFlag3(mLPEdorAppSchema.getOperator());//操作员
			mmLOPRTManagerSchema.setStandbyFlag4(mLPEdorAppSchema.getEdorAppName());//申请人
			mmLOPRTManagerSchema.setStandbyFlag5(mLPEdorAppSchema.getMakeDate());//申请时间
			mmLOPRTManagerSchema.setStandbyFlag6(mLPEdorAppSchema.getApproveOperator());//复核人
			mmLOPRTManagerSchema.setStandbyFlag7(mLPEdorAppSchema.getApproveDate());//复核时间

			mLOPRTManagerSet.add(mmLOPRTManagerSchema);
			mMap.put(mLOPRTManagerSet, "DELETE&INSERT");
		} catch (Exception e) {
			CError.buildErr(this, "插入保全收费通知书失败!");
			return false;
		}

		return true;
	}
}
