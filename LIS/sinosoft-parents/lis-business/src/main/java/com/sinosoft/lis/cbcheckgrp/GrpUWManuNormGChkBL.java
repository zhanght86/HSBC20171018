package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGUWMasterDB;
import com.sinosoft.lis.db.LCGUWSubDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDUWGradePersonDB;
import com.sinosoft.lis.db.LDUWUserDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGUWMasterSchema;
import com.sinosoft.lis.schema.LCGUWSubSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.vschema.LCGUWMasterSet;
import com.sinosoft.lis.vschema.LCGUWSubSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LDUWGradePersonSet;
import com.sinosoft.lis.vschema.LDUWUserSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: 团单在人工核保时为集体险种下核保结论
 * </p>
 * <p>
 * Description: 团单在人工核保时为集体险种下核保结论
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author 张星
 * @version 1.0
 */

public class GrpUWManuNormGChkBL {
private static Logger logger = Logger.getLogger(GrpUWManuNormGChkBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 往工作流引擎中传输数据的容器 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// 集体保单险种表
	private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();

	// 核保主表
	private LCGUWMasterSet mLCGUWMasterSet = new LCGUWMasterSet();
	// 集体核保轨迹表
	private LCGUWSubSet mLCGUWSubSet = new LCGUWSubSet();
	private MMap mMap;

	/** 数据操作字符串 */
	private String mOperator;
	private String mManageCom;
	private String mUWFlag = ""; // 核保标志
	private String mUWIdea = ""; // 核保结论
	private String mGrpPolNo = ""; // 集体险种保单号
	private String mAppGrade = ""; // 上报级别
	private String mUPUWCode = "";
	private String mgrpContno = "";
	private String riskcode = "";

	public GrpUWManuNormGChkBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}

		// 校验
		if (!checkData()) {
			return false;
		}

		logger.debug("Start  dealData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("dealData successful!");

		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		logger.debug("Start  Submit...");

		// 提交数据

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCInsuredUWBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备返回前台统一存储数据 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mResult.clear();
		MMap map = new MMap();
		map.put(mLCGrpPolSchema, "UPDATE");
		map.put(mLCGUWMasterSet, "DELETE&INSERT");
		map.put(mLCGUWSubSet, "INSERT");
		if (mUWFlag.equals("1")) {
			map.add(this.mMap);
		}

		mResult.clear();

		mResult.add(map);
		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		// 校验该投保单是否已复核过,是则可进行核保,否则不能进行，还要校验是否所有的个单险种已经下了结论
		if (!checkLcpol()) {
			return false;
		}
		// if (!checkApprove()) {
		// return false;
		// }
		if (!checkUWGrade()) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		// 从输入数据中得到所有对象
		// 获得全局公共数据
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mGlobalInput == null) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得操作员编码
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 获得管理机构编码
		mManageCom = mGlobalInput.ManageCom;
		if (mManageCom == null || mManageCom.trim().equals("")) {
			// @@错误处理
			// this.mErrors.copyAllErrors( tLCGrpPolDB.mErrors );
			CError tError = new CError();
			tError.moduleName = "LCInsuredBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "前台传输全局公共数据mOperator失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 取投保单

		mLCGrpPolSchema.setSchema((LCGrpPolSchema) cInputData
				.getObjectByObjectName("LCGrpPolSchema", 0));

		mUWFlag = mLCGrpPolSchema.getUWFlag();
		mGrpPolNo = mLCGrpPolSchema.getGrpPolNo();
		mUWIdea = mLCGrpPolSchema.getRemark();
		mgrpContno = mLCGrpPolSchema.getGrpContNo();
		riskcode = mLCGrpPolSchema.getRiskCode();
		logger.debug("集体合同号=" + mgrpContno);
		logger.debug("核保结论=" + mUWFlag);
		logger.debug("险种编码=" + riskcode);

		// 校验是不是以下核保结论
		if ((mUWFlag == null) || mUWFlag.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有选择核保结论";
			this.mErrors.addOneError(tError);

			return false;
		}

		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpPolNo(mGrpPolNo);
		if (tLCGrpPolDB.getInfo() == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGrpPolDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "getInputData";
			tError.errorMessage = mGrpPolNo + "投保单查询失败!";
			this.mErrors.addOneError(tError);

			return false;
		} else {
			mLCGrpPolSchema.setSchema(tLCGrpPolDB);

		}

		return true;
	}

	/**
	 * 校验投保单是否复核 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkApprove() {
		if ((mLCGrpPolSchema.getApproveCode() == null)
				|| mLCGrpPolSchema.getApproveDate().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "checkApprove";
			tError.errorMessage = "投保单尚未进行复核操作，不能核保!（投保单号：" + mGrpPolNo.trim()
					+ "）";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 校验是不是该集体险种下的个单险种的结论已经下过 输出：如果发生错误则返回false,否则返回true
	 */

	private boolean checkLcpol() {

		LCPolDB tLCPolDB = new LCPolDB();
		String sql = "select * from lcpol where grppolno='"
				+ mLCGrpPolSchema.getGrpPolNo() + "'";
		logger.debug(sql);
		LCPolSet tLCPolSet = new LCPolSet();
		tLCPolSet = tLCPolDB.executeQuery(sql);
		if (tLCPolSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "checkApprove";
			tError.errorMessage = "数据库中丢失数据，没有找到该集体险种下的个单的险种";
			this.mErrors.addOneError(tError);

			return false;

		}
		for (int j = 1; j <= tLCPolSet.size(); j++) {
			if (!tLCPolSet.get(j).getUWFlag().equals("4")
					&& !tLCPolSet.get(j).getUWFlag().equals("9")
					&& !tLCPolSet.get(j).getUWFlag().equals("1")
					&& !tLCPolSet.get(j).getUWFlag().equals("z")) {
				CError tError = new CError();
				tError.moduleName = "UWManuNormGChkBL";
				tError.functionName = "checkApprove";
				tError.errorMessage = "该险种下的个人险种保单还有未通过自核的单子，请先下个单核保结论！";
				this.mErrors.addOneError(tError);

				return false;

			}
		}
		return true;
	}

	/**
	 * 校验该核保师是否有权限下次核保结论 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkUWGrade() {

		LDUWUserDB tLDUWUserDB = new LDUWUserDB();
		String sql = "select * From lduwuser where usercode='" + mOperator
				+ "'";
		LDUWUserSet tLDUWUserSet = new LDUWUserSet();
		tLDUWUserSet = tLDUWUserDB.executeQuery(sql);

		// tLDUWGradeDB.setUWState(mUWFlag);

		if (tLDUWUserSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "UWManuNormChkBL";
			tError.functionName = "checkData";
			tError.errorMessage = "核保师:" + mOperator + "没有进行核保权限配置!";
			this.mErrors.addOneError(tError);
			return false;

		}
		String uwpopedom = tLDUWUserSet.get(1).getUWPopedom();
		LDUWGradePersonDB tLDUWGradePersonDB = new LDUWGradePersonDB();
		sql = "select * From lduwgradeperson where uwpopedom='" + uwpopedom
				+ "' and uwkind='10'";
		LDUWGradePersonSet tLDUWGradePersonSet = new LDUWGradePersonSet();
		tLDUWGradePersonSet = tLDUWGradePersonDB.executeQuery(sql);
		if (tLDUWGradePersonSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "UWManuNormChkBL";
			tError.functionName = "checkData";
			tError.errorMessage = "数据库描述数据不全！";
			this.mErrors.addOneError(tError);
			return false;

		}
		if (mUWFlag.equals("1")
				&& tLDUWGradePersonSet.get(1).getStandFlag().equals("N")) {
			CError tError = new CError();
			tError.moduleName = "UWManuNormChkBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该核保员没有拒保的权限！";
			this.mErrors.addOneError(tError);
			return false;

		}

		if (mUWFlag.equals("2")
				&& tLDUWGradePersonSet.get(1).getDelayFlag().equals("N")) {
			CError tError = new CError();
			tError.moduleName = "UWManuNormChkBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该核保员没有延期的权限！";
			this.mErrors.addOneError(tError);
			return false;

		}
		if (mUWFlag.equals("4")) {
			logger.debug("集体合同号=" + mgrpContno);
			ExeSQL exeSql = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = exeSql
					.execSQL("select sum(prem) from lcprem where grpcontno='"
							+ mgrpContno
							+ "' and PayPlanType!='0' and dutycode in (select dutycode from lmriskduty where riskcode='"
							+ riskcode + "') group by grpcontno");
			if (tSSRS.MaxRow == 0) {

				CError tError = new CError();
				tError.moduleName = "UWManuNormChkBL";
				tError.functionName = "checkData";
				tError.errorMessage = "请选择正常承保！";
				this.mErrors.addOneError(tError);
				return false;

			}
		}
		if (mUWFlag.equals("9")) {

			logger.debug("集体合同号=" + mgrpContno);
			ExeSQL exeSql = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = exeSql
					.execSQL("select sum(prem) from lcprem where grpcontno='"
							+ mgrpContno
							+ "' and PayPlanType!='0' and dutycode in (select dutycode from lmriskduty where riskcode='"
							+ riskcode + "') group by grpcontno");
			if (tSSRS.MaxRow > 0) {
				CError tError = new CError();
				tError.moduleName = "UWManuNormChkBL";
				tError.functionName = "checkData";
				tError.errorMessage = "已经有加费，只能进行通融承保！";
				this.mErrors.addOneError(tError);
				return false;

			}

		}

		return true;
	}

	private float parseFloat(String s) {
		float f1 = 0;
		String tmp = "";
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			s1 = s.substring(i, i + 1);
			if (s1.equals("0") || s1.equals("1") || s1.equals("2")
					|| s1.equals("3") || s1.equals("4") || s1.equals("5")
					|| s1.equals("6") || s1.equals("7") || s1.equals("8")
					|| s1.equals("9") || s1.equals(".")) {
				tmp = tmp + s1;
			} else if (tmp.length() > 0) {
				break;
			}
		}
		f1 = Float.parseFloat(tmp);
		// logger.debug("tmp:"+tmp+" f1:"+f1);
		return f1;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (preparePol() == false) {
			return false;
		}

		// 核保信息
		if (prepareUW() == false) {
			return false;
		}
		// 如果该集体险种保单的结论为拒保，那么该集体险种下的险种单的核保结论都应该为拒保
		if (mUWFlag.equals("1")) {
			LCPolSet tLCPolSet = new LCPolSet();
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setGrpPolNo(mGrpPolNo);
			tLCPolSet = tLCPolDB.query();
			if (tLCPolSet.size() == 0) {

				CError tError = new CError();
				tError.moduleName = "UWManuNormGChkBL";
				tError.functionName = "prepareUW";
				tError.errorMessage = "LCGUWMaster表取数失败!";
				this.mErrors.addOneError(tError);

				return false;

			}

			// 循环调用UWManuNormGChkBL
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				// 个人保单表
				LCPolSchema tLCPolSchema = new LCPolSchema();
				LCPolSet aLCPolSet = new LCPolSet();
				// 个单核保主表
				LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();

				tLCPolSchema.setPolNo(tLCPolSet.get(i).getPolNo());
				tLCPolSchema.setUWFlag(mUWFlag);
				tLCPolSchema.setRemark(mUWIdea);

				aLCPolSet.add(tLCPolSchema);

				tLCUWMasterSchema.setUWIdea(mUWIdea);
				UWManuNormGChkBL tUWManuNormGChkBL = new UWManuNormGChkBL();
				VData aVData = new VData();
				aVData.add(this.mGlobalInput);
				aVData.add(aLCPolSet);
				aVData.add(tLCUWMasterSchema);
				boolean tResult = tUWManuNormGChkBL
						.submitData(aVData, "submit");
				logger.debug("after!tResult===" + tResult);
				if (tResult) {
					mMap = (MMap) tUWManuNormGChkBL.getResult()
							.getObjectByObjectName("MMap", 0);
				}
			}
		}

		return true;
	}

	/**
	 * 准备保单信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean preparePol() {
		mLCGrpPolSchema.setUWFlag(mUWFlag);
		mLCGrpPolSchema.setUWOperator(mOperator);
		mLCGrpPolSchema.setUWDate(PubFun.getCurrentDate());
		mLCGrpPolSchema.setUWTime(PubFun.getCurrentTime());
		mLCGrpPolSchema.setOperator(mOperator);
		mLCGrpPolSchema.setModifyDate(PubFun.getCurrentDate());
		mLCGrpPolSchema.setModifyTime(PubFun.getCurrentTime());
		mLCGrpPolSchema.setUWDate(PubFun.getCurrentDate());

		return true;
	}

	/**
	 * 准备核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareUW() {
		LCGUWMasterSchema tLCGUWMasterSchema = new LCGUWMasterSchema();
		LCGUWMasterDB tLCGUWMasterDB = new LCGUWMasterDB();
		tLCGUWMasterDB.setGrpPolNo(mGrpPolNo);

		LCGUWMasterSet tLCGUWMasterSet = new LCGUWMasterSet();
		tLCGUWMasterSet = tLCGUWMasterDB.query();

		if (tLCGUWMasterDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCGUWMaster表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int n = tLCGUWMasterSet.size();
		logger.debug("该投保单的核保主表记录条数:   " + n);

		if (n == 1) {
			tLCGUWMasterSchema = tLCGUWMasterSet.get(1);
			// 人工核保后uwno加一
			int uwno = tLCGUWMasterSet.get(1).getUWNo();
			uwno++;
			tLCGUWMasterSchema.setUWNo(uwno);

			tLCGUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCGUWMasterSchema.setState(mUWFlag);
			tLCGUWMasterSchema.setUWIdea(mUWIdea);
			tLCGUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保

			// tLCGUWMasterSchema.setUWGrade(mUWPopedom);
			tLCGUWMasterSchema.setAppGrade(mAppGrade);

			if (mUWFlag.equals("6")) {
				tLCGUWMasterSchema.setOperator(mUPUWCode); // 上报核保指定核保师功能的实现借助将当前核保师改为待核保师方式实现
			} else {
				tLCGUWMasterSchema.setOperator(mOperator);
			}
			// 操作员

			tLCGUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGUWMasterSchema.setModifyTime(PubFun.getCurrentTime());

		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWMasterDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCGUWMaster表取数据不唯一!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCGUWMasterSet.clear();
		mLCGUWMasterSet.add(tLCGUWMasterSchema);

		// 核保轨迹表
		LCGUWSubSchema tLCGUWSubSchema = new LCGUWSubSchema();
		LCGUWSubDB tLCGUWSubDB = new LCGUWSubDB();
		tLCGUWSubDB.setGrpPolNo(mGrpPolNo);

		LCGUWSubSet tLCGUWSubSet = new LCGUWSubSet();
		tLCGUWSubSet = tLCGUWSubDB.query();

		if (tLCGUWSubDB.mErrors.needDealError()) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCGUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int m = tLCGUWSubSet.size();
		logger.debug("subcount=" + m);

		if (m > 0) {
			m++; // 核保次数
			tLCGUWSubSchema = new LCGUWSubSchema();
			tLCGUWSubSchema.setUWNo(m); // 第几次核保
			tLCGUWSubSchema.setGrpPolNo(mGrpPolNo);
			tLCGUWSubSchema.setGrpProposalNo(mGrpPolNo);
			tLCGUWSubSchema.setGrpContNo(tLCGUWMasterSchema.getGrpContNo());
			tLCGUWSubSchema.setProposalGrpContNo(tLCGUWMasterSchema
					.getGrpContNo());
			tLCGUWSubSchema.setInsuredNo(tLCGUWMasterSchema.getInsuredNo());
			tLCGUWSubSchema.setInsuredName(tLCGUWMasterSchema.getInsuredName());
			tLCGUWSubSchema.setAppntNo(tLCGUWMasterSchema.getAppntNo());
			tLCGUWSubSchema.setAppntName(tLCGUWMasterSchema.getAppntName());
			tLCGUWSubSchema.setAgentCode(tLCGUWMasterSchema.getAgentCode());
			tLCGUWSubSchema.setAgentGroup(tLCGUWMasterSchema.getAgentGroup());
			tLCGUWSubSchema.setUWGrade(tLCGUWMasterSchema.getUWGrade()); // 核保级别
			tLCGUWSubSchema.setAppGrade(tLCGUWMasterSchema.getAppGrade()); // 申请级别
			tLCGUWSubSchema.setAutoUWFlag(tLCGUWMasterSchema.getAutoUWFlag());
			tLCGUWSubSchema.setState(tLCGUWMasterSchema.getState());
			tLCGUWSubSchema.setPassFlag(tLCGUWMasterSchema.getState());
			tLCGUWSubSchema.setPostponeDay(tLCGUWMasterSchema.getPostponeDay());
			tLCGUWSubSchema.setPostponeDate(tLCGUWMasterSchema
					.getPostponeDate());
			tLCGUWSubSchema.setUpReportContent(tLCGUWMasterSchema
					.getUpReportContent());
			tLCGUWSubSchema.setHealthFlag(tLCGUWMasterSchema.getHealthFlag());
			tLCGUWSubSchema.setSpecFlag(tLCGUWMasterSchema.getSpecFlag());
			tLCGUWSubSchema.setSpecReason(tLCGUWMasterSchema.getSpecReason());
			tLCGUWSubSchema.setQuesFlag(tLCGUWMasterSchema.getQuesFlag());
			tLCGUWSubSchema.setReportFlag(tLCGUWMasterSchema.getReportFlag());
			tLCGUWSubSchema.setChangePolFlag(tLCGUWMasterSchema
					.getChangePolFlag());
			tLCGUWSubSchema.setChangePolReason(tLCGUWMasterSchema
					.getChangePolReason());
			tLCGUWSubSchema.setAddPremReason(tLCGUWMasterSchema
					.getAddPremReason());
			tLCGUWSubSchema.setPrintFlag(tLCGUWMasterSchema.getPrintFlag());
			tLCGUWSubSchema.setPrintFlag2(tLCGUWMasterSchema.getPrintFlag2());
			tLCGUWSubSchema.setUWIdea(tLCGUWMasterSchema.getUWIdea());
			tLCGUWSubSchema.setOperator(tLCGUWMasterSchema.getOperator()); // 操作员
			tLCGUWSubSchema.setManageCom(tLCGUWMasterSchema.getManageCom());
			tLCGUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCGUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCGUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCGUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCGUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		mLCGUWSubSet.clear();
		mLCGUWSubSet.add(tLCGUWSubSchema);

		return true;
	}

	/**
	 * 返回结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误
	 * 
	 * @return VData
	 */
	public CErrors getErrors() {
		return mErrors;
	}

}
