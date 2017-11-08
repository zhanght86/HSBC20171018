package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDPlanDB;
import com.sinosoft.lis.db.LDPlanDutyParamDB;
import com.sinosoft.lis.db.LDPlanRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCContPlanDutyParamSchema;
import com.sinosoft.lis.schema.LCContPlanRiskSchema;
import com.sinosoft.lis.schema.LCContPlanSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LDPlanDutyParamSchema;
import com.sinosoft.lis.schema.LDPlanRiskSchema;
import com.sinosoft.lis.schema.LDPlanSchema;
import com.sinosoft.lis.vschema.LCContPlanDutyParamSet;
import com.sinosoft.lis.vschema.LCContPlanRiskSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LDPlanDutyParamSet;
import com.sinosoft.lis.vschema.LDPlanRiskSet;
import com.sinosoft.lis.vschema.LDPlanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团险保单产品组合录入
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company:Sinosoft
 * </p>
 * 
 * @author:Chenrong
 * @version 1.0
 */
public class GrpPlanRiskBL {
private static Logger logger = Logger.getLogger(GrpPlanRiskBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();
	private MMap map = new MMap();
	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 业务处理相关变量 */
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
	private LCContPlanSchema mLCContPlanSchema = new LCContPlanSchema();
	private LCContPlanRiskSet mLCContPlanRiskSet = new LCContPlanRiskSet();
	private LCContPlanDutyParamSet mLCContPlanDutyParamSet = new LCContPlanDutyParamSet();
	private LDPlanSchema mLDPlanSchema = new LDPlanSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mCValidate;
	private double mMult;

	public GrpPlanRiskBL() {

	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("团险录单产品组合录入->GrpPlanRiskBL->submitData-beg");
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("团险录单产品组合录入->GrpPlanRiskBL->submitData-cOperate:"
				+ mOperate);

		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false) {
			return false;
		}

		// 校验传入的数据
		if (this.checkData() == false) {
			return false;
		}

		// 根据业务逻辑对数据进行处理
		if (this.dealData() == false) {
			return false;
		}

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();

		// 数据提交、保存
		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {

		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// 集体合同
		mLCGrpContSchema.setSchema((LCGrpContSchema) mInputData
				.getObjectByObjectName("LCGrpContSchema", 0));
		mLDPlanSchema.setSchema((LDPlanSchema) mInputData
				.getObjectByObjectName("LDPlanSchema", 0));
		if (mLCGrpContSchema == null || mLDPlanSchema == null) {
			// @@错误处理
			buildError("getInputData", "在接受数据时没有得到集体合同或者产品组合信息!");
			return false;
		}

		mMult = mLCGrpContSchema.getMult(); // 取出在LCGrpContSchema暂存的份数

		return true;
	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();

		LCGrpContDB mLCGrpContDB = new LCGrpContDB();
		mLCGrpContDB.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		if (mLCGrpContDB.getInfo() == false) {
			// @@错误处理
			buildError("checkData", "没有查到该集体合同!");
			return false;
		}
		mLCGrpContSchema.setSchema(mLCGrpContDB);

		if (!mOperate.equals("DELETE||PLANRISK")) {

			mCValidate = mLCGrpContSchema.getCValiDate();

			LDPlanDB tLDPlanDB = new LDPlanDB();
			LDPlanSet tLDPlanSet = new LDPlanSet();
			tLDPlanSet = tLDPlanDB.executeQuery("select * from ldplan where "
					+ "contplancode='" + mLDPlanSchema.getContPlanCode() + "'");
			if (tLDPlanSet == null || tLDPlanSet.size() <= 0) {
				// @@错误处理
				buildError("checkData", "没有查到该产品组合信息!");
				return false;
			}
			mLDPlanSchema.setSchema(tLDPlanSet.get(1));
			tLDPlanSet = null;

			// tSSRS = tExeSQL.execSQL(
			// "select 'X' from lccontplan where grpcontno='" +
			// mLCGrpContSchema.getGrpContNo() +
			// "' and plantype='6' and contplancode='" +
			// mLDPlanSchema.getContPlanCode() + "'");

			// 一个集体单只能添加一个产品组合
			tSSRS = tExeSQL
					.execSQL("select 'X' from lccontplan where grpcontno='"
							+ mLCGrpContSchema.getGrpContNo()
							+ "' and plantype='6'");
			if (tSSRS != null && tSSRS.getMaxRow() > 0) {
				buildError("checkData", "集体保单已经添加产品组合，不能再继续添加!");
				return false;
			}
		} else {
			tSSRS = tExeSQL
					.execSQL("select 'X' from lcinsured where grpcontno='"
							+ mLCGrpContSchema.getGrpContNo()
							+ "' and contplancode='"
							+ mLDPlanSchema.getContPlanCode() + "'");
			if (tSSRS != null && tSSRS.getMaxRow() > 0) {
				buildError("checkData", "被保人中已经添加此产品组合信息，不能进行删除操作!");
				return false;
			}

		}
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {
		// 产生集体投保单号码
		LDPlanRiskSet tLDPlanRiskSet = new LDPlanRiskSet();
		LDPlanRiskDB tLDPlanRiskDB = new LDPlanRiskDB();
		tLDPlanRiskSet = tLDPlanRiskDB.executeQuery("select * from ldplanrisk"
				+ " where contplancode='" + mLDPlanSchema.getContPlanCode()
				+ "'");
		if (tLDPlanRiskSet == null || tLDPlanRiskSet.size() <= 0) {
			buildError("dealData", "产品组合险种信息查询失败！");
			return false;
		}
		if (mOperate.equals("INSERT||PLANRISK")) {
			if (!dealGrpPol(tLDPlanRiskSet)) {
				return false;
			}

			if (!dealContPlan(tLDPlanRiskSet)) {
				return false;
			}
		} else if (mOperate.equals("DELETE||PLANRISK")) {
			if (!deleteData(tLDPlanRiskSet)) {
				return false;
			}
		}

		return true;
	}

	private boolean dealGrpPol(LDPlanRiskSet cLDPlanRiskSet) {
		LDPlanRiskSet tLDPlanRiskSet = cLDPlanRiskSet;
		mLCGrpPolSet.clear();
		for (int i = 1; i <= tLDPlanRiskSet.size(); i++) {
			LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
			String tRiskCode = tLDPlanRiskSet.get(i).getRiskCode();
			LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
			String tSQL = "select grppolno from lcgrppol where grpcontno='"
					+ mLCGrpContSchema.getGrpContNo() + "' and riskcode='"
					+ tRiskCode + "'";
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(tSQL);
			// 若已存在该险种对应的lcgrppol记录，则不生成,缓存存在的险种信息
			// 用于保险计划参数表取GrpPolNo
			if (tSSRS.getMaxRow() > 0) {
				tLCGrpPolSchema.setGrpPolNo(tSSRS.GetText(1, 1));
				tLCGrpPolSchema.setRiskCode(tRiskCode);
				tLCGrpPolSchema.setGrpContNo(mLCGrpContSchema.getGrpContNo());
				mLCGrpPolSet.add(tLCGrpPolSchema);
				continue;
			}
			tLCGrpPolSchema.setGrpContNo(mLCGrpContSchema.getGrpContNo());
			tLCGrpPolSchema.setExpPeoples(mLCGrpContSchema.getPeoples());
			tLCGrpPolSchema.setRiskCode(tRiskCode);
			tLCGrpPolSchema.setPayIntv("0");
			tLCGrpPolSchema.setCValiDate(this.mCValidate);
			tLCGrpPolSet.add(tLCGrpPolSchema);
			if (tLCGrpPolSet != null && tLCGrpPolSet.size() > 0) {
				VData tVData = new VData();
				tVData.add(tLCGrpPolSet);
				tVData.add(mLCGrpContSchema);
				tVData.add(mTransferData);
				tVData.add(mGlobalInput);

				GroupRiskBL tGroupRiskBL = new GroupRiskBL();
				if (!tGroupRiskBL
						.prepareSubmitData(tVData, "INSERT||GROUPRISK")) {
					this.mErrors.copyAllErrors(tGroupRiskBL.mErrors);
					return false;
				}
				VData tResult = new VData();
				tResult = tGroupRiskBL.getResult();
				tLCGrpPolSet = (LCGrpPolSet) tResult.getObjectByObjectName(
						"LCGrpPolSet", 0);

				// 将刚生成的险种保单数据进行缓存
				// 这样即此产品组合全部保单险种信息
				mLCGrpPolSet.add(tLCGrpPolSet);

				mTransferData = (TransferData) tResult.getObjectByObjectName(
						"TransferData", 0);

				map.add((MMap) tResult.getObjectByObjectName("MMap", 0));
			}

		}

		return true;
	}

	private boolean dealContPlan(LDPlanRiskSet cLDPlanRiskSet) {
		LDPlanRiskSet tLDPlanRiskSet = cLDPlanRiskSet;
		for (int i = 1; i <= tLDPlanRiskSet.size(); i++) {
			Reflections tRef = new Reflections();
			LDPlanRiskSchema tLDPlanRiskSchema = tLDPlanRiskSet.get(i)
					.getSchema();
			LCContPlanRiskSchema tLCContPlanRiskSchema = new LCContPlanRiskSchema();
			tRef.transFields(tLCContPlanRiskSchema, tLDPlanRiskSchema);
			tLCContPlanRiskSchema.setGrpContNo(mLCGrpContSchema.getGrpContNo());
			tLCContPlanRiskSchema.setProposalGrpContNo(mLCGrpContSchema
					.getProposalGrpContNo());
			tLCContPlanRiskSchema.setPlanType("6");
			tLCContPlanRiskSchema.setModifyDate(PubFun.getCurrentDate());
			tLCContPlanRiskSchema.setModifyTime(PubFun.getCurrentTime());
			tLCContPlanRiskSchema.setSysContPlanMult(mMult);
			mLCContPlanRiskSet.add(tLCContPlanRiskSchema);
		}

		LDPlanDutyParamDB tLDPlanDutyParamDB = new LDPlanDutyParamDB();
		LDPlanDutyParamSet tLDPlanDutyParamSet = new LDPlanDutyParamSet();
		tLDPlanDutyParamSet = tLDPlanDutyParamDB
				.executeQuery("select * from ldplandutyparam where contplancode='"
						+ mLDPlanSchema.getContPlanCode() + "'");
		if (tLDPlanDutyParamSet == null || tLDPlanDutyParamSet.size() <= 0) {
			buildError("dealContPlan", "查询产品组合参数失败！");
			return false;
		}
		for (int i = 1; i <= tLDPlanDutyParamSet.size(); i++) {
			String tGrpPolNo = "";
			Reflections tRef = new Reflections();
			LDPlanDutyParamSchema tLDPlanDutyParamSchema = tLDPlanDutyParamSet
					.get(i).getSchema();
			LCContPlanDutyParamSchema tLCContPlanDutyParamSchema = new LCContPlanDutyParamSchema();
			tRef
					.transFields(tLCContPlanDutyParamSchema,
							tLDPlanDutyParamSchema);
			for (int j = 1; j <= mLCGrpPolSet.size(); j++) {
				if (tLDPlanDutyParamSchema.getRiskCode().equals(
						mLCGrpPolSet.get(j).getRiskCode())) {
					tGrpPolNo = mLCGrpPolSet.get(j).getGrpPolNo();
				}
			}
			tLCContPlanDutyParamSchema.setGrpContNo(mLCGrpContSchema
					.getGrpContNo());
			tLCContPlanDutyParamSchema.setProposalGrpContNo(mLCGrpContSchema
					.getProposalGrpContNo());
			tLCContPlanDutyParamSchema.setGrpPolNo(tGrpPolNo);
			tLCContPlanDutyParamSchema.setPlanType("6");
			tLCContPlanDutyParamSchema
					.setSysContPlanCode(tLDPlanDutyParamSchema
							.getContPlanCode());
			mLCContPlanDutyParamSet.add(tLCContPlanDutyParamSchema);
		}

		Reflections tRef = new Reflections();
		tRef.transFields(mLCContPlanSchema, mLDPlanSchema);
		mLCContPlanSchema.setGrpContNo(mLCGrpContSchema.getGrpContNo());
		mLCContPlanSchema.setProposalGrpContNo(mLCGrpContSchema
				.getProposalGrpContNo());
		mLCContPlanSchema.setPeoples2(mLCGrpContSchema.getPeoples());
		mLCContPlanSchema.setPlanType("6");

		return true;
	}

	/**
	 * 删除传入的险种
	 * 
	 * @param: 无
	 * @return: void
	 */
	private boolean deleteData(LDPlanRiskSet cLDPlanRiskSet) {
		logger.debug("团险录单产品组合录入->GrpPlanRiskBL->deleteData-beg");
		String tDelPlanSQL = "";
		tDelPlanSQL = "delete from lccontplan where grpcontno='"
				+ mLCGrpContSchema.getGrpContNo()
				+ "' and plantype='6' and contplancode='"
				+ mLDPlanSchema.getContPlanCode() + "'";
		map.put(tDelPlanSQL, "DELETE");

		String tDelPlanRiskSQL = "";
		tDelPlanRiskSQL = "delete from lccontplanrisk where grpcontno='"
				+ mLCGrpContSchema.getGrpContNo()
				+ "' and plantype='6' and contplancode='"
				+ mLDPlanSchema.getContPlanCode() + "'";
		map.put(tDelPlanRiskSQL, "DELETE");

		String tDelPlanParamSQL = "";
		tDelPlanParamSQL = "delete from lccontplandutyparam where grpcontno='"
				+ mLCGrpContSchema.getGrpContNo()
				+ "' and plantype='6' and contplancode='"
				+ mLDPlanSchema.getContPlanCode() + "'";
		map.put(tDelPlanParamSQL, "DELETE");

		LDPlanRiskSet tLDPlanRiskSet = cLDPlanRiskSet;
		for (int i = 1; i <= tLDPlanRiskSet.size(); i++) {
			String tRiskCode = tLDPlanRiskSet.get(i).getRiskCode();
			String tSQL = "select 'X' from lccontplanrisk where grpcontno='"
					+ mLCGrpContSchema.getGrpContNo() + "' and riskcode='"
					+ tRiskCode + "' and plantype='6' and contplancode != '"
					+ mLDPlanSchema.getContPlanCode() + "'";
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(tSQL);
			if (tSSRS.getMaxRow() > 0) {
				continue;
			}
			String tDelSQL = "delete from lcgrppol where grpcontno='"
					+ mLCGrpContSchema.getGrpContNo() + "' and riskcode='"
					+ tRiskCode + "'";
			map.put(tDelSQL, "DELETE");
		}

		String fromPart = "from LCPol where GrpContNo='"
				+ mLCGrpContSchema.getGrpContNo() + "')";
		map
				.put(
						"update LCGrpCont set "
								+ "Prem=(select SUM(Prem) "
								+ fromPart
								+ ", Amnt=(select SUM(Amnt) "
								+ fromPart
								+ ", SumPrem=(select SUM(SumPrem) "
								+ fromPart
								+ ", Mult=(select SUM(Mult) "
								+ fromPart
								+ ", Peoples2=(select sum(Peoples) from LCCont where GrpContNo= '"
								+ mLCGrpContSchema.getGrpContNo() + "')"
								+ " where GrpContNo='"
								+ mLCGrpContSchema.getGrpContNo() + "'",
						"UPDATE");
		logger.debug("团险录单产品组合录入->GrpPlanRiskBL->deleteData-beg");

		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		if (mOperate.equals("INSERT||PLANRISK")) {
			logger.debug("团险录单产品组合录入->GrpPlanRiskBL->prepareOutputData-beg-Insert");
			map.put(mLCContPlanSchema, "INSERT");
			map.put(mLCContPlanRiskSet, "INSERT");
			map.put(mLCContPlanDutyParamSet, "INSERT");
		}
		mInputData.clear();
		mInputData.add(map);
		mResult.clear();
		mResult.add(mLCGrpPolSet);
		mResult.add(mLCContPlanSchema);
		mResult.add(mTransferData);
	}

	private void buildError(String tFunName, String tMsg) {
		// @@错误处理
		CError tError = new CError();
		tError.moduleName = "GrpContBL";
		tError.functionName = tFunName;
		tError.errorMessage = tMsg;
		this.mErrors.addOneError(tError);
	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		LDPlanSchema tLDPlanSchema = new LDPlanSchema();

		TransferData tTransferData = new TransferData();

		GrpPlanRiskBL grpplanriskbl = new GrpPlanRiskBL();
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "8621";
		tGI.Operator = "001";

		VData tVData = new VData();
		tLCGrpContSchema.setGrpContNo("20061124000003");
		tLCGrpContSchema.setPrtNo("20061124000003");
		tLCGrpContSchema.setMult("1");
		tLDPlanSchema.setContPlanCode("86000007");

		tVData.add(tLDPlanSchema);
		tVData.add(tLCGrpContSchema);
		tVData.add(tTransferData);
		tVData.add(tGI);

		if (!grpplanriskbl.submitData(tVData, "INSERT||PLANRISK")) {
			logger.debug(grpplanriskbl.mErrors.getFirstError());
		}

	}
}
