package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCUWMasterDB;
import com.sinosoft.lis.db.LCUWSubDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCUWMasterSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCUWMasterSet;
import com.sinosoft.lis.vschema.LCUWSubSet;
import com.sinosoft.lis.vschema.LMRiskAppSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
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
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class UWManuNormPolChkBL {
private static Logger logger = Logger.getLogger(UWManuNormPolChkBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperator; // 操作员
	private String mManageCom;

	/** 业务处理相关变量 */
	private LCPolSet mLCPolSet = new LCPolSet();
	private String mContType = "";
	private String mGrpContNo = "";
	private String mContNo = "";
	private String mUWFlag = ""; // 核保标志
	private String mRiskCode = "";
	private String mUWIdea = ""; // 核保结论
	private String mUPUWCode = "";

	private String mpostday; // 延长天数
	private String mvalidate; // 延长天数
	private String mReason = ""; // 加费原因
	private String mSpecReason = ""; // 特约原因

	private String mAppGrade = ""; // 上报级别

	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	private boolean isAll = true; // 是否为集体层下核保结论
	String mCount = "0";
	private boolean isMainRisk = true; // 是否为主险

	public UWManuNormPolChkBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!this.getInputData(cInputData, cOperate)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		prepareOutputData();

		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		String tUWFlag = "";

		mInputData = (VData) cInputData.clone();
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperator = mGlobalInput.Operator;
		mManageCom = mGlobalInput.ManageCom;

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mContType = (String) mTransferData.getValueByName("ContType");
		if (mContType == null || "".equals(mContType)) {
			mContType = "1"; // 默认为个险下核保结论
		}

		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
		mContNo = (String) mTransferData.getValueByName("ContNo");
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");
		mpostday = (String) mTransferData.getValueByName("PostDay");
		mvalidate = (String) mTransferData.getValueByName("ValiDate");
		mReason = (String) mTransferData.getValueByName("Reason");
		mSpecReason = (String) mTransferData.getValueByName("SpecReason");
		mAppGrade = (String) mTransferData.getValueByName("AppGrade");
		mUPUWCode = (String) mTransferData.getValueByName("UPUWCode");

		// 险种核保结论分两类：
		// 一、集体层险种下核保结论
		// 二、对个单层个别被保人下险种核保结论
		// 传入个单保单号，就是对个单层个别被保人下险种核保结论
		if (mContNo != null && !"".equals(mContNo)) {
			isAll = false;
		}

		tUWFlag = (String) mTransferData.getValueByName("UWFlag");
		mUWIdea = (String) mTransferData.getValueByName("UWIdea");
		if (tUWFlag == null || "".equals(tUWFlag)) {
			buildError("getInputData", "没有传入核保结论标志！");
			return false;
		}

		mUWFlag = tUWFlag;
		if (mContType.equals("2")) {
			// 集体单险种下核保结论，同时对所有个单层面下险种核保结论
			// 团险有的核保结论，个单层面没有，如果团单层面核保结论为拒保，个单层面也为拒保
			// 否则，为标准承保
			if (isAll) {
				if (!tUWFlag.equals("1")) {
					mUWFlag = "9";
				}
			}
		}

		return true;
	}

	private boolean checkData() {
		if (mRiskCode == null || "".equals(mRiskCode)) {
			buildError("checkData", "没有传入险种编码！");
			return false;
		}

		LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
		LMRiskAppSet tLMRiskAppSet = new LMRiskAppSet();
		LMRiskAppSchema tLMRiskAppSchema = new LMRiskAppSchema();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql("select * from lmriskapp where riskcode='"
				+ "?riskcode?" + "'");
		sqlbv.put("riskcode", mRiskCode);
		tLMRiskAppSet = tLMRiskAppDB
				.executeQuery(sqlbv);
		if (tLMRiskAppSet == null || tLMRiskAppSet.size() == 0) {
			buildError("checkData", "险种查询失败!");
			return false;
		}
		tLMRiskAppSchema = tLMRiskAppSet.get(1);
		if ("S".equals(tLMRiskAppSchema.getSubRiskFlag())) {
			isMainRisk = false;
		} else {
			isMainRisk = true;
		}

		if (mContType.equals("2")) {
			String tSQL = "";
			ExeSQL tExeSQL = new ExeSQL();
			String tCount = "0";
			if (mGrpContNo == null || "".equals(mGrpContNo)) {
				buildError("checkData", "没有传入集体保单号！");
				return false;
			}

			tSQL = "select count(*) from lcgrpcont where grpcontno='"
					+ "?grpcontno?" + "'";
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tSQL);
			sqlbv1.put("grpcontno", mGrpContNo);
			tCount = tExeSQL.getOneValue(sqlbv1);
			if (tCount.equals("0")) {
				buildError("checkData", "团单查询失败！");
				return false;
			}

			if (!isAll) {
				// 对个别个单下核保结论

				// 对单个个单下核保结论，要判断是否传入个单保单号
				if (mContNo == null || "".equals(mContNo)) {
					buildError("checkData", "没有传入保单号！");
					return false;
				}

				// 校验集体层险种核保结论
				// 如果对应集体层险种核保结论为拒保，则不能再对个单下核保结论
				SQLwithBindVariables sqlbv2  = new SQLwithBindVariables();
				sqlbv2.sql("select uwflag from lcgrppol where grpcontno='"
						+ "?grpcontno?"
						+ "' and riskcode='"
						+ "?riskcode?"
						+ "'");
				sqlbv2.put("grpcontno", mGrpContNo);
				sqlbv2.put("riskcode", mRiskCode);
				String tGrpUWFlag = tExeSQL
						.getOneValue(sqlbv2);
				if (tGrpUWFlag != null && !"".equals(tGrpUWFlag)
						&& "1".equals(tGrpUWFlag)) {
					buildError("checkData", "团单核保结论为拒保，个单不能下核保结论！");
					return false;
				}

				// 校验个单层附加险对应主险是否下拒保结论
				// 如果主险下拒保结论，则附加险不能下其它结论
				if (!isMainRisk && !"1".equals(mUWFlag)) {
					tSQL = "select 'X' from lcpol a " + "where a.contno='"
							+ "?contno?"
							+ "' and a.polno=(select mainpolno from lcpol "
							+ "where riskcode='" + "?riskcode?" + "' and contno='"
							+ "?contno?" + "') and "
							+ "a.polno=a.mainpolno and uwflag='1'";
					SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
					sqlbv3.sql(tSQL);
					sqlbv3.put("contno", mContNo);
					sqlbv3.put("riskcode", mRiskCode);
					String tResult = tExeSQL.getOneValue(sqlbv3);
					if ("X".equals(tResult)) {
						buildError("checkData", "该个单主险下拒保，附加险不能下其它核保结论！");
						return false;
					}
				}
			} else {
				// LCPolDB tLCPolDB = new LCPolDB();
				if (!mUWFlag.equals("1")) {
					// 查询个单层面没有下核保结论的保单
					tSQL = "select count(*) from lcpol where grpcontno='"
							+ "?grpcontno?" + "' and riskcode='" + "?riskcode?"
							+ "' and uwflag in('0','5','z')";
				} else {
					// 查询个单层面所有保单
					tSQL = "select count(*) from lcpol where grpcontno='"
							+ "?grpcontno?" + "' and riskcode='" + "?riskcode?" + "'";
				}
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tSQL);
				sqlbv4.put("grpcontno", mGrpContNo);
				sqlbv4.put("riskcode", mRiskCode);
				mCount = tExeSQL.getOneValue(sqlbv4);
				// mLCPolSet = tLCPolDB.executeQuery(tSQL);
			}
		} else {
			if (mContNo == null || "".equals(mContNo)) {
				buildError("getInputData", "没有传入保单号！");
				return false;
			}
		}
		return true;
	}

	private boolean dealData() {
		if (mContType.equals("2")) {
			if (isAll) {
				if (mCount.equals("0")) {
					buildError("dealData", "没有符合条件的个单层，不用下核保结论！");
					return true;
				}
				// 对集体单下所有个单层面险种下核保结论
				if (!prepareAllPol()) {
					return false;
				}
			} else {
				// 对一个险种单下核保结论
				if (!prepareSinglePol(mContNo, mRiskCode)) {
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mResult.clear();
		mResult.add(mMap);
	}

	/**
	 * 返回处理后的结果
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	private void buildError(String mFunctionName, String mMsg) {
		// @@错误处理
		CError tError = new CError();
		tError.moduleName = "UWManuNormPolChkBL";
		tError.functionName = mFunctionName;
		tError.errorMessage = mMsg;
		this.mErrors.addOneError(tError);
	}

	private boolean prepareAllPol() {
		// 先向核保结论表操作
		// 用的是同一事务，且为sql语句，如果先对险种保单表进行处理，
		// 提交时就先对险种保单表进行修改操作，uwflag就进行了变更
		// 然后对核保结论表操作，查询没有下核保结论的险种保单，就会没有符合条件的数据
		// 这样就造成了不会对核保结论表进行修改、插入操作，核保结论数据不一致
		// 由此，先对核保结论表进行操作
		prepareUW();

		// 对险种保单表操作
		String tSQL = "";
		if (!mUWFlag.equals("1")) {
			// 对个单层面没有下核保结论的保单下核保结论
			tSQL = "update lcpol set uwflag='" + "?uwflag?" + "',uwdate='"
					+ "?CurrentDate?" + "',uwtime='"
					+ "?CurrentTime?" + "',uwcode='" + "?operator?"
					+ "',operator='" + "?operator?" + "',modifydate='"
					+ "?CurrentDate?" + "',modifytime='"
					+ "?CurrentTime?" + "' where grpcontno='"
					+ "?grpcontno?" + "' and riskcode='" + "?riskcode?"
					+ "' and uwflag in('5','z','0')";
		} else {
			// 对所有个单层面对应险种保单下拒保结论
			// 如果是主险，则同时对个单层此险种及其对应的附加险保单下拒保结论
			// 否则，只对个单层此险种对应保单下拒保结论
			if (isMainRisk) {
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSQL = "update lcpol set uwflag='" + "?uwflag?" + "',uwdate='"
						+ "?CurrentDate?" + "',uwtime='"
						+ "?CurrentTime?" + "',uwcode='" + "?operator?"
						+ "',operator='" + "?operator?" + "',modifydate='"
						+ "?CurrentDate?" + "',modifytime='"
						+ "?CurrentTime?" + "' where grpcontno='"
						+ "?grpcontno?" + "' and mainpolno in "
						+ "(select distinct polno from lcpol "
						+ "where grpcontno='" + "?grpcontno?" + "' and riskcode='"
						+ "?riskcode?" + "') ";
				}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					tSQL = "update lcpol set uwflag='" + "?uwflag?" + "',uwdate='"
							+ "?CurrentDate?" + "',uwtime='"
							+ "?CurrentTime?" + "',uwcode='" + "?operator?"
							+ "',operator='" + "?operator?" + "',modifydate='"
							+ "?CurrentDate?" + "',modifytime='"
							+ "?CurrentTime?" + "' where grpcontno='"
							+ "?grpcontno?" + "' and mainpolno in "
							+ "(select polno from (select distinct polno from lcpol "
							+ "where grpcontno='" + "?grpcontno?" + "' and riskcode='"
							+ "?riskcode?" + "') g ) ";
				}
			} else {
				tSQL = "update lcpol set uwflag='" + "?uwflag?" + "',uwdate='"
						+ "?CurrentDate?" + "',uwtime='"
						+ "?CurrentTime?" + "',uwcode='" + "?operator?"
						+ "',operator='" + "?operator?" + "',modifydate='"
						+ "?CurrentDate?" + "',modifytime='"
						+ "?CurrentTime?" + "' where grpcontno='"
						+ "?grpcontno?" + "' and riskcode='" + "?riskcode?" + "'";
			}
		}
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(tSQL);
		sqlbv5.put("uwflag", mUWFlag);
		sqlbv5.put("CurrentTime", PubFun.getCurrentTime());
		sqlbv5.put("CurrentDate", PubFun.getCurrentDate());
		sqlbv5.put("operator", mOperator);
		sqlbv5.put("grpcontno", mGrpContNo);
		sqlbv5.put("riskcode", mRiskCode);
		mMap.put(sqlbv5, "UPDATE");
		return true;
	}

	/**
	 * 准备核保信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareUW() {
		// String tPolNo = "";
		String tUpSQL = "";
		String tInSQL = "";
		String tPolCondPart = "";
		if ("1".equals(mUWFlag)) {
			// 整单为拒保
			// 如果是主险，则同时修改个单层所有该主险及其附加险保单核保结论
			// 附加险只修改此险种对应的所有个单险种保单核保结论
			if (isMainRisk) {
				tPolCondPart = "in (select distinct polno from lcpol where grpcontno='"
						+ "?grpcontno?"
						+ "' and mainpolno in (select polno from lcpol "
						+ "where grpcontno='"
						+ "?grpcontno?"
						+ "' and riskcode='"
						+ "?riskcode?" + "'))";
			} else {
				tPolCondPart = "in (select distinct polno from lcpol where grpcontno='"
						+ "?grpcontno?" + "' and riskcode='" + "?riskcode?" + "')";
			}
		} else {
			// 非拒保结论，修改个单层此险种对应的没有下结论的保单
			tPolCondPart = "in (select distinct polno from lcpol where grpcontno='"
					+ "?grpcontno?"
					+ "' and riskcode='"
					+ "?riskcode?"
					+ "' and uwflag in('0','5','z'))";
		}

		// 为避免人数多造成内存溢出，使用SQL语句进行修改！
		tUpSQL = "update LCUWMaster a set uwno=uwno+1,uwidea='" + "?uwidea?"
				+ "',AutoUWFlag='2',AppGrade='" + "?AppGrade?"
				+ "',operator=(case '" + "?uwflag?" + "' when '6' then '"
				+ "?uwcode?" + "' else '" + "?operator?" + "' end),modifydate='"
				+ "?CurrentDate?" + "',modifytime='"
				+ "?CurrentTime?" + "',PassFlag='" + "?uwflag?"
				+ "',State='" + "?uwflag?" + "' " + "where a.grpcontno='"
				+ "?grpcontno?" + "' and a.polno " + tPolCondPart;

		tInSQL = "insert into lcuwsub(uwno,contno,PolNo,grpcontno,proposalcontno,ProposalNo,"
				+ "insuredno,insuredname,appntno,appntname,agentcode,agentgroup,"
				+ "uwgrade,appgrade,autouwflag,state,passflag,postponeday,postponedate,"
				+ "upreportcontent,healthflag,specflag,specreason,quesflag,changepolflag,"
				+ "reportflag,changepolreason,addpremreason,printflag,printflag2,uwidea,"
				+ "operator,managecom,makedate,maketime,modifydate,modifytime)"
				+ "(select uwno,contno,polno,grpcontno,proposalcontno,proposalno,"
				+ "insuredno,insuredname,appntno,appntname,agentcode,agentgroup,"
				+ "uwgrade,appgrade,'2','"
				+ "?uwflag?"
				+ "','"
				+ "?uwflag?"
				+ "',postponeday,postponedate,"
				+ "upreportcontent,healthflag,specflag,specreason,quesflag,changepolflag,"
				+ "reportflag,changepolreason,addpremreason,printflag,printflag2,'"
				+ "?uwidea?"
				+ "',"
				+ "(case '"
				+ "?uwflag?"
				+ "' when '6' then '"
				+ "?uwcode?"
				+ "' else '"
				+ "?operator?"
				+ "' end),managecom,'"
				+ "?CurrentDate?"
				+ "','"
				+ "?CurrentTime?"
				+ "','"
				+ "?CurrentDate?"
				+ "','"
				+ "?CurrentTime?"
				+ "' from lcuwmaster a "
				+ "where a.grpcontno='"
				+ "?grpcontno?"
				+ "' and a.polno "
				+ tPolCondPart + ")";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(tUpSQL);
		sqlbv6.put("grpcontno", mGrpContNo);
		sqlbv6.put("riskcode", mRiskCode);
		sqlbv6.put("uwidea", mUWIdea);
		sqlbv6.put("AppGrade", mAppGrade);
		sqlbv6.put("uwflag", mUWFlag);
		sqlbv6.put("uwcode", mUPUWCode);
		sqlbv6.put("operator", mOperator);
		sqlbv6.put("CurrentDate", PubFun.getCurrentDate());
		sqlbv6.put("CurrentTime", PubFun.getCurrentTime());
		mMap.put(sqlbv6, "UPDATE");
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(tInSQL);
		sqlbv7.put("grpcontno", mGrpContNo);
		sqlbv7.put("riskcode", mRiskCode);
		sqlbv7.put("uwidea", mUWIdea);
		sqlbv7.put("AppGrade", mAppGrade);
		sqlbv7.put("uwflag", mUWFlag);
		sqlbv7.put("uwcode", mUPUWCode);
		sqlbv7.put("operator", mOperator);
		sqlbv7.put("CurrentDate", PubFun.getCurrentDate());
		sqlbv7.put("CurrentTime", PubFun.getCurrentTime());
		mMap.put(sqlbv7, "INSERT");

		return true;
	}

	private boolean prepareSinglePol(String tContNo, String tRiskCode) {
		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSet tLCPolSet = new LCPolSet();
		String tSQL = "";
		if ("1".equals(mUWFlag) && isMainRisk) {
			// 如果主险为拒保，则同时对其对应的附加险下拒保
			tSQL = "select * from lcpol where " + "contno='" + "?contno?"
					+ "' and mainpolno in "
					+ "(select distinct polno from lcpol " + "where contno='"
					+ "?contno?" + "' and riskcode='" + "?riskcode1?" + "')";
		} else {
			tSQL = "select * from lcpol where " + "contno='" + "?contno?"
					+ "' and riskcode='" + "?riskcode2?" + "'";
		}
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(tSQL);
		sqlbv8.put("contno", tContNo);
		sqlbv8.put("riskcode1", mRiskCode);
		sqlbv8.put("riskcode2", tRiskCode);
		tLCPolSet = tLCPolDB.executeQuery(sqlbv8);
		if (tLCPolSet == null || tLCPolSet.size() == 0) {
			buildError("prepareSinglePol", "险种保单查询失败!");
			return false;
		}

		if (tLCPolSet != null && tLCPolSet.size() > 0) {
			for (int i = 1; i <= tLCPolSet.size(); i++) {
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema = tLCPolSet.get(i).getSchema();
				tLCPolSchema.setUWFlag(mUWFlag);
				tLCPolSchema.setUWCode(mOperator);
				tLCPolSchema.setUWDate(PubFun.getCurrentDate());
				tLCPolSchema.setUWTime(PubFun.getCurrentTime());
				tLCPolSchema.setOperator(mOperator);
				tLCPolSchema.setModifyDate(PubFun.getCurrentDate());
				tLCPolSchema.setModifyTime(PubFun.getCurrentTime());
				tLCPolSchema.setUWDate(PubFun.getCurrentDate());
				mMap.put(tLCPolSchema, "UPDATE");

				if (!prepareSingleUW(tLCPolSchema.getPolNo(), tContNo)) {
					return false;
				}
			}
		}

		logger.debug("tUWManuNormPolChkBL ----- end preapre singlePol");
		return true;
	}

	private boolean prepareSingleUW(String tPolNo, String tContNo) {
		logger.debug("prepareSingleUW ----- Begiin preapre singlePol");
		int uwno = 0;
		LCUWMasterSchema tLCUWMasterSchema = new LCUWMasterSchema();
		LCUWMasterDB tLCUWMasterDB = new LCUWMasterDB();

		LCUWMasterSet tLCUWMasterSet = new LCUWMasterSet();
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql("select * from lcuwmaster "
				+ "where polno='" + "?polno?" + "'");
		sqlbv9.put("polno", tPolNo);
		tLCUWMasterSet = tLCUWMasterDB.executeQuery(sqlbv9);

		if (tLCUWMasterSet == null || tLCUWMasterSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			this.buildError("prepareUW", "LCUWMaster表取数失败!");
			return false;
		}

		int n = tLCUWMasterSet.size();
		logger.debug("该投保单的核保主表记录条数:   " + n);

		if (n == 1) {
			tLCUWMasterSchema = tLCUWMasterSet.get(1);
			// 人工核保后uwno加一
			uwno = tLCUWMasterSet.get(1).getUWNo();
			uwno++;
			tLCUWMasterSchema.setUWNo(uwno);
			tLCUWMasterSchema.setPassFlag(mUWFlag); // 通过标志
			tLCUWMasterSchema.setState(mUWFlag);
			tLCUWMasterSchema.setUWIdea(mUWIdea);
			tLCUWMasterSchema.setAutoUWFlag("2"); // 1 自动核保 2 人工核保

			tLCUWMasterSchema.setAppGrade(mAppGrade);
			if (mUWFlag.equals("6")) {
				tLCUWMasterSchema.setOperator(mUPUWCode); // 上报核保指定核保师功能的实现借助将当前核保师改为待核保师方式实现
			} else {
				tLCUWMasterSchema.setOperator(mOperator);
			}

			// 延期
			if (mUWFlag.equals("2")) { // 此代码冗余
				tLCUWMasterSchema.setPostponeDay(mpostday);
				tLCUWMasterSchema.setPostponeDate(mvalidate);
			}

			// 条件承保
			if (mUWFlag.equals("3")) {
				tLCUWMasterSchema.setSpecReason(mSpecReason); // 特约原因
				tLCUWMasterSchema.setAddPremReason(mReason);
			}

			if (mUWFlag.equals("3")) {
				if (tLCUWMasterSchema.getPrintFlag().equals("1")) {
					CError tError = new CError();
					tError.moduleName = "UWManuNormGChkBL";
					tError.functionName = "prepareUW";
					tError.errorMessage = "已经发核保通知不可录入!";
					this.mErrors.addOneError(tError);

					return false;
				}

				tLCUWMasterSchema.setSpecFlag("1");
			}

			// 操作员
			tLCUWMasterSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWMasterSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWMasterDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWMaster表取数据不唯一!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 核保轨迹表
		LCUWSubSchema tLCUWSubSchema = new LCUWSubSchema();
		LCUWSubDB tLCUWSubDB = new LCUWSubDB();
		LCUWSubSet tLCUWSubSet = new LCUWSubSet();
		SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
		sqlbv10.sql("select * from lcuwsub where polno='" + "?polno?"
				+ "'");
		sqlbv10.put("polno", tPolNo);
		tLCUWSubSet = tLCUWSubDB
				.executeQuery(sqlbv10);

		if (tLCUWSubSet == null || tLCUWSubSet.size() == 0) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}

		int m = tLCUWSubSet.size();
		logger.debug("subcount=" + m);

		if (m > 0) {
			m++; // 核保次数
			tLCUWSubSchema = new LCUWSubSchema();
			tLCUWSubSchema.setUWNo(m); // 第几次核保
			tLCUWSubSchema.setContNo(tContNo);
			tLCUWSubSchema.setPolNo(tPolNo);
			tLCUWSubSchema.setGrpContNo(tLCUWMasterSchema.getGrpContNo());
			tLCUWSubSchema.setProposalContNo(tLCUWMasterSchema
					.getProposalContNo());
			tLCUWSubSchema.setProposalNo(tLCUWMasterSchema.getProposalNo());
			tLCUWSubSchema.setInsuredNo(tLCUWMasterSchema.getInsuredNo());
			tLCUWSubSchema.setInsuredName(tLCUWMasterSchema.getInsuredName());
			tLCUWSubSchema.setAppntNo(tLCUWMasterSchema.getAppntNo());
			tLCUWSubSchema.setAppntName(tLCUWMasterSchema.getAppntName());
			tLCUWSubSchema.setAgentCode(tLCUWMasterSchema.getAgentCode());
			tLCUWSubSchema.setAgentGroup(tLCUWMasterSchema.getAgentGroup());
			tLCUWSubSchema.setUWGrade(tLCUWMasterSchema.getUWGrade()); // 核保级别
			tLCUWSubSchema.setAppGrade(tLCUWMasterSchema.getAppGrade()); // 申请级别
			tLCUWSubSchema.setAutoUWFlag(tLCUWMasterSchema.getAutoUWFlag());
			tLCUWSubSchema.setState(tLCUWMasterSchema.getState());
			tLCUWSubSchema.setPassFlag(tLCUWMasterSchema.getState());
			tLCUWSubSchema.setPostponeDay(tLCUWMasterSchema.getPostponeDay());
			tLCUWSubSchema.setPostponeDate(tLCUWMasterSchema.getPostponeDate());
			tLCUWSubSchema.setUpReportContent(tLCUWMasterSchema
					.getUpReportContent());
			tLCUWSubSchema.setHealthFlag(tLCUWMasterSchema.getHealthFlag());
			tLCUWSubSchema.setSpecFlag(tLCUWMasterSchema.getSpecFlag());
			tLCUWSubSchema.setSpecReason(tLCUWMasterSchema.getSpecReason());
			tLCUWSubSchema.setQuesFlag(tLCUWMasterSchema.getQuesFlag());
			tLCUWSubSchema.setReportFlag(tLCUWMasterSchema.getReportFlag());
			tLCUWSubSchema.setChangePolFlag(tLCUWMasterSchema
					.getChangePolFlag());
			tLCUWSubSchema.setChangePolReason(tLCUWMasterSchema
					.getChangePolReason());
			tLCUWSubSchema.setAddPremReason(tLCUWMasterSchema
					.getAddPremReason());
			tLCUWSubSchema.setPrintFlag(tLCUWMasterSchema.getPrintFlag());
			tLCUWSubSchema.setPrintFlag2(tLCUWMasterSchema.getPrintFlag2());
			tLCUWSubSchema.setUWIdea(tLCUWMasterSchema.getUWIdea());
			tLCUWSubSchema.setOperator(tLCUWMasterSchema.getOperator()); // 操作员
			tLCUWSubSchema.setManageCom(tLCUWMasterSchema.getManageCom());
			tLCUWSubSchema.setMakeDate(PubFun.getCurrentDate());
			tLCUWSubSchema.setMakeTime(PubFun.getCurrentTime());
			tLCUWSubSchema.setModifyDate(PubFun.getCurrentDate());
			tLCUWSubSchema.setModifyTime(PubFun.getCurrentTime());
		} else {
			// @@错误处理
			this.mErrors.copyAllErrors(tLCUWSubDB.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWManuNormGChkBL";
			tError.functionName = "prepareUW";
			tError.errorMessage = "LCUWSub表取数失败!";
			this.mErrors.addOneError(tError);

			return false;
		}
		logger.debug("prepareSingleUW ----- Begiin preapre singlePol");
		mMap.put(tLCUWMasterSchema, "DELETE&INSERT");
		mMap.put(tLCUWSubSchema, "INSERT");
		logger.debug("prepareSingleUW ----- End preapre singlePol");
		return true;
	}

	public static void main(String[] args) {
		UWManuNormPolChkBL uwmanunormpolchkbl = new UWManuNormPolChkBL();
	}
}
