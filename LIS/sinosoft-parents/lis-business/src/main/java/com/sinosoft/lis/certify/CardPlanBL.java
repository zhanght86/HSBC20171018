/*
 * <p>ClassName: CardPlanBL </p>
 * <p>Description: CardPlanBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2003-10-23
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import java.util.Hashtable;

import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LMCertifyDesDB;
import com.sinosoft.lis.db.LZCardBugetDB;
import com.sinosoft.lis.db.LZCardPlanDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LZCardBugetSchema;
import com.sinosoft.lis.schema.LZCardPlanSchema;
import com.sinosoft.lis.vschema.LZCardBugetSet;
import com.sinosoft.lis.vschema.LZCardPlanSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLString;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

public class CardPlanBL {
private static Logger logger = Logger.getLogger(CardPlanBL.class);
	// 一些常量定义
	public static String STATE_APP = "A";// 申请,单证计划申请后的状态

	public static String STATE_CON = "C";// 提交，只含本级计划，计划提交后的状态

	public static String STATE_PACK = "P";// 汇总，包含本级和下级计划，

	public static String STATE_Sub = "S";// 汇总提交，汇总提交后的状态

	public static String STATE_RET = "R";// 批复，本部对分公司的S状态可以批复，分公司对支公司C状态的可以批复，逐级批复即只有上级批复了才可以批复下级的计划

	public static String RET_STATE_PASS = "Y";// Y -- 批复通过,批复数量大于0

	public static String RET_STATE_REFUSE = "N";// N -- 批复不通过，批复数量等于0

	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private MMap map = new MMap();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String strOperate;

	/** 业务处理相关变量 */
	private LZCardPlanSchema mLZCardPlanSchema = new LZCardPlanSchema();

	private LZCardPlanSet mLZCardPlanSet = new LZCardPlanSet();

	private String m_strWhere = "";

	private Hashtable m_hashParams = null;

	public CardPlanBL() {
	}

	public static void main(String[] args) {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		strOperate = verifyOperate(cOperate);
		if (strOperate.equals("")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			buildError("submitData", "数据提交失败!");
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		try {
			this.mLZCardPlanSchema = (LZCardPlanSchema) cInputData.getObjectByObjectName("LZCardPlanSchema",
					0);
			this.mLZCardPlanSet = (LZCardPlanSet) cInputData.getObjectByObjectName("LZCardPlanSet", 0);
			this.mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		} catch (Exception ex) {
			buildError("getInputData", "获取数据发生异常");
			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 计划申请
		if (strOperate.equals("INSERT||APPLY")) {
			return saveCardPlan(mLZCardPlanSet, mGlobalInput);
		}

		// 计划修改
		if (strOperate.equals("UPDATE||APPLY")) {
			return updateCardPlan(mLZCardPlanSet, mGlobalInput);
		}

		// 计划删除
		if (strOperate.equals("DELETE||APPLY")) {
			return deleteCardPlan(mLZCardPlanSet, mGlobalInput);
		}

		// 计划直接提交
		if (strOperate.equals("UPDATE||CONF2")) {
			return confCardPlan2(mLZCardPlanSet, mGlobalInput);
		}

		// 计划调整保存
		if (strOperate.equals("UPDATE||CONF")) {
			return confCardPlan(mLZCardPlanSet, mGlobalInput);
		}

		// 计划汇总提交
		if (strOperate.equals("INSERT||PACK")) {
			return packCardPlan(mLZCardPlanSchema, mGlobalInput);
		}

		// 计划批复
		if (strOperate.equals("UPDATE||RETURN")) {
			return retCardPlan(mLZCardPlanSchema, mLZCardPlanSet, mGlobalInput);
		}

		// 库存分配
		if (strOperate.equals("UPDATE||ASSIGN")) {
			return assignCardPlan(mLZCardPlanSchema, mLZCardPlanSet, mGlobalInput);
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		mResult.clear();

		SQLString sqlObj = new SQLString("LZCardPlan");

		sqlObj.setSQL(5, mLZCardPlanSchema);

		String strSql = sqlObj.getSQL();
		String strSqlAppend = "1=1";

		// process two extra parameters
		String strTemp = "";

		strTemp = (String) m_hashParams.get("MakeDateB");
		if ((strTemp != null) && !strTemp.equals("")) {
			strSqlAppend += (" AND MakeDate >= '" + "?strTemp?" + "'");
		}

		strTemp = (String) m_hashParams.get("MakeDateE");
		if ((strTemp != null) && !strTemp.equals("")) {
			strSqlAppend += (" AND MakeDate <= '" + "?strTemp?" + "'");
		}

		// process m_strwhere
		if ((m_strWhere != null) && !m_strWhere.equals("")) {
			strSqlAppend += (" AND " + m_strWhere);
		}

		if (strSql.toUpperCase().indexOf("WHERE") != -1) {
			strSql += (" AND " + strSqlAppend);
		} else {
			strSql += (" WHERE " + strSqlAppend);
		}

		// 如果没有传入起始页和每页的记录数，则查询出所有满足条件的记录。
		int nCurrentPage = Integer.parseInt((String) m_hashParams.get("CurrentPage"));
		int nRecordNum = Integer.parseInt((String) m_hashParams.get("RecordNum"));

		try {
			nCurrentPage = Integer.parseInt((String) m_hashParams.get("CurrentPage"));
			nRecordNum = Integer.parseInt((String) m_hashParams.get("RecordNum"));
		} catch (Exception ex) {
			nCurrentPage = -1;
		}

		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs = null;
        SQLwithBindVariables sqlbva = new SQLwithBindVariables();
        sqlbva.sql(strSql);
        sqlbva.put("strTemp", strTemp);
		if ((nCurrentPage >= 0) && (nRecordNum > 0)) {
			ssrs = exeSQL.execSQL(sqlbva, (nCurrentPage * nRecordNum) + 1, nRecordNum);
		} else {
			ssrs = exeSQL.execSQL(sqlbva);
		}

		if (exeSQL.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(exeSQL.mErrors);
			buildError("submitData", "数据提交失败");

			return false;
		}

		mResult.add(String.valueOf(getPageCount(strSql, nRecordNum)));
		mResult.add(ssrs);

		return true;
	}

	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			buildError("prepareData", "在准备往后层处理所需要的数据时出错。");
			return false;
		}
		return true;
	}

	public VData getResult() {
		mResult.clear();
		return this.mResult;
	}

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "CardPlanBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
	
	private String sfun(String str1 , String str2){
		if(str1==null || str1.equals("")) return null;
		return "?"+str2+"?";
	}
	
	private String verifyOperate(String szOperate) {
		String szReturn = "";
		String[] szOperates = { "INSERT||APPLY", "UPDATE||APPLY", "DELETE||APPLY", "INSERT||PACK",
				"UPDATE||CONF", "UPDATE||CONF2", "UPDATE||RETURN", "UPDATE||ASSIGN", "QUERY||MAIN",
				"QUERY||BUGET" };
		for (int nIndex = 0; nIndex < szOperates.length; nIndex++) {
			if (szOperate.equals(szOperates[nIndex])) {
				szReturn = szOperate;
			}
		}

		return szReturn;
	}

	/**
	 * query CardPlan Buget refactor for method by guoxiang at 2004-5-12
	 * 
	 * @param aLZCardPlanSchema
	 * @param aLZCardPlanSet
	 * @param globalInput
	 * @return
	 */
	protected boolean queryPlanBuget(LZCardPlanSchema aLZCardPlanSchema, GlobalInput globalInput) {
		mResult.clear();

		boolean OverBuget = false;

		// check whether over card buget . add by guoxiang at 2004-04-03
		// first set switch var=1 or 0 for function control
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("CheckCardBuget");
		if (!tLDSysVarDB.getInfo()) {
			mErrors.copyAllErrors(tLDSysVarDB.mErrors);

			return false;
		}

		String strCheckCardBuget = tLDSysVarDB.getSysVarValue();
		if (strCheckCardBuget.equals("1")) { // Switch on

			// get price from lzcardprint put into hashtalbe
			Hashtable hashPrice = new Hashtable();
			String strSQL = "SELECT CertifyCode, CertifyPrice FROM LZCardPrint A"
					+ " WHERE PrtNo = (SELECT MAX(PrtNo) FROM LZCardPrint B"
					+ " WHERE B.CertifyCode = A.CertifyCode)" + " ORDER BY CertifyCode";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(strSQL);
			ExeSQL es = new ExeSQL();
			SSRS ssrs = es.execSQL(sqlbv);
			if (ssrs.mErrors.needDealError()) {
				buildError("queryPlanBuget", "取单证印刷费用失败！");
				mErrors.copyAllErrors(ssrs.mErrors);
			}

			String pCertifyCode = "";
			for (int nIndex = 0; nIndex < ssrs.getMaxRow(); nIndex++) {
				pCertifyCode = ssrs.GetText(nIndex + 1, 1);
				hashPrice.put(pCertifyCode, ssrs.GetText(nIndex + 1, 2));
			}

			String strCertifyCode = aLZCardPlanSchema.getCertifyCode();

			// 新增或查询该计划的对应的最终日期，时间,机构
			// 以及批复数量和印刷费用
			String newPlanDate = aLZCardPlanSchema.getUpdateDate();
			String newPlanTime = aLZCardPlanSchema.getUpdateTime();
			String newAppCom = aLZCardPlanSchema.getAppCom();
			String newReState = aLZCardPlanSchema.getRetState();
			double newPlanCount = aLZCardPlanSchema.getAppCount();
			String sCertifyPrice = (String) hashPrice.get(strCertifyCode);
			double newPlanMoney = newPlanCount * ReportPubFun.functionDouble(sCertifyPrice);

			// 查询预算表,使得该计划的时间和申请单位进入预算表，取得的费用
			LZCardBugetDB tLZCardBugetDB = new LZCardBugetDB();
			String SSQL = "select * from LZCardBuget where 1=1 "
					+ ReportPubFun.getWherePart("SDate", "", sfun(newPlanDate,"pdate"), 1)
					+ ReportPubFun.getWherePart("EDate", sfun(newPlanDate,"pdate"), "", 1)
					+ ReportPubFun.getWherePart("ManageCom", sfun(newAppCom,"appcom"));
			SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
			sqlbv7.sql(SSQL);
			sqlbv7.put("pdate", newPlanDate);
			sqlbv7.put("appcom", newAppCom);
			LZCardBugetSet tLZCardBugetSet = tLZCardBugetDB.executeQuery(sqlbv7);
			String ttSDate = "";
			String ttEDate = "";
			double BugetMoney = 0;
			for (int nIndex = 0; nIndex < tLZCardBugetSet.size(); nIndex++) {
				LZCardBugetSchema tLZCardBugetSchema = tLZCardBugetSet.get(nIndex + 1);
				BugetMoney = tLZCardBugetSchema.getBuget(); // 预算表的费用
				ttSDate = tLZCardBugetSchema.getSDate(); // 预算开始时间
				ttEDate = tLZCardBugetSchema.getEDate(); // 预算结束时间

				break;
			}

			// 新增或查询计划的时间段内和单位对应的计划表中的所有在的这个计划
			// 最终系统时间之前的已批付的计划的费用总和
			String mCertifyCodePrice = "";
			double mAppCount = 0;
			double mMoney = 0;
			double sMoney = 0;

			LZCardPlanDB mLZCardPlanDB = new LZCardPlanDB();
			String StrSQL = "select * from lzcardplan where retstate='Y'"// 已批付的
					+ " AND (updatedate<'" + "?pdate?" + "'" + " OR (updatedate='"
					+ "?pdate?"
					+ "' AND updatetime<'" + "?ptime?"
					+ "'))"
					+ ReportPubFun.getWherePart("updatedate", sfun(ttSDate,"sdate"), sfun(ttEDate,"edate"), 1)// 新增或查询计划的时间段内
					+ ReportPubFun.getWherePart("AppCom", sfun(newAppCom,"appcom"));// 单位对应
			logger.debug("print sql" + StrSQL);
			SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
			sqlbv8.sql(StrSQL);
			sqlbv8.put("ptime", newPlanTime);
			sqlbv8.put("pdate", newPlanDate);
			sqlbv8.put("appcom", newAppCom);
			sqlbv8.put("sdate", ttSDate);
			sqlbv8.put("edate", ttEDate);
			LZCardPlanSet mLZCardPlanSet = mLZCardPlanDB.executeQuery(sqlbv8);

			for (int nIndex = 0; nIndex < mLZCardPlanSet.size(); nIndex++) {
				LZCardPlanSchema tLZCardPlanSchema = mLZCardPlanSet.get(nIndex + 1);
				mCertifyCodePrice = (String) hashPrice.get(tLZCardPlanSchema.getCertifyCode());
				mAppCount = tLZCardPlanSchema.getAppCount();
				mMoney = mAppCount * ReportPubFun.functionDouble(mCertifyCodePrice);
				logger.debug("第" + (nIndex + 1) + "已批付的计划的印刷费用" + mMoney);
				sMoney += mMoney;
			}

			String message = "";

			if (((sMoney + newPlanMoney) - BugetMoney) > 0) {
				message = "超过预算!\n";
				OverBuget = true;
			} else {
				message = "没有超过预算!\n";
				OverBuget = false;
			}

			message += ("费用名细如下：\n");
			message += (" 1.计划批复状态为" + newReState + "的该计划印刷费用：" + newPlanMoney + "元；\n");
			message += (" 2.在" + ttSDate + "至" + ttEDate + "时间段内并且时间在" + newPlanDate + "-" + newPlanTime + "之前，\n");
			message += ("申请机构为" + newAppCom + "的已批付计划的总印刷费用：" + sMoney + "元；\n");
			message += (" 3.在" + ttSDate + "至" + ttEDate + "时间段内\n");
			message += ("申请机构为" + newAppCom + "的单证预算：" + BugetMoney + "元；\n");
			logger.debug(message);
			mResult.add(message);
		}

		return OverBuget;
	}

	/**
	 * create a new card plan
	 * 
	 * @param aLZCardPlanSchema
	 * @param aLZCardPlanSet
	 * @param globalInput
	 * @return
	 */
	private boolean saveCardPlan(LZCardPlanSet aLZCardPlanSet, GlobalInput globalInput) {
		boolean bReturn = true;
		try {
			for (int i = 1; i <= aLZCardPlanSet.size(); i++) {
				LZCardPlanSchema tLZCardPlanSchema = aLZCardPlanSet.get(i);

				String strCertifyCode = tLZCardPlanSchema.getCertifyCode();
				LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
				tLMCertifyDesDB.setCertifyCode(strCertifyCode);
				if (!tLMCertifyDesDB.getInfo()) {
					mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
					return false;
				}

				// String strManageCom = tLMCertifyDesDB.getManageCom();
				// if ((strManageCom != null) && !strManageCom.equals("")) {
				// if (strManageCom.equals(globalInput.ComCode)) {
				// throw new Exception("所选单证不需要上报上级公司");
				// }
				// }

				String strState = tLMCertifyDesDB.getState();
				if (strState != null && strState.equals("1")) {
					throw new Exception("所选单证【" + strCertifyCode + "】处于停用状态，不能申请计划!");
				}

				// 同一机构同一计划类型同一单证，未批复前不能再次申请
				String sql = "select a.* from lzcardplan a where a.certifycode = '"
						+ "?certifycode?" + "' and a.plantype = '"
						+ "?plantype?" + "' and a.appcom = '" + "?appcom?"
						+ "' and a.planstate != 'R'";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(sql);
				sqlbv1.put("certifycode", tLZCardPlanSchema.getCertifyCode());
				sqlbv1.put("plantype", tLZCardPlanSchema.getPlanType());
				sqlbv1.put("appcom", globalInput.ComCode);
				LZCardPlanDB tLZCardPlanDB = new LZCardPlanDB();
				LZCardPlanSet tLZCardPlanSet = tLZCardPlanDB.executeQuery(sqlbv1);
				if (tLZCardPlanSet != null && tLZCardPlanSet.size() >= 1) {
					buildError("saveCardPlan", "此计划已保存,不能重复申请：计划类型【" + tLZCardPlanSchema.getPlanType()
							+ "】单证编码【" + tLZCardPlanSchema.getCertifyCode() + "】");
					return false;
				}

				// 同一计划类型同一单证,当分公司已经汇总计划提交至总公司且未批复时，分公司和其支公司不允许再次申请
				sql = "select a.* from lzcardplan a where a.certifycode = '"
						+ "?certifycode?" + "' and a.plantype = '"
						+ "?plantype?" + "' and a.appcom = substr('" + "?appcom?"
						+ "',1,4) and a.planstate = 'S'";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(sql);
				sqlbv2.put("certifycode", tLZCardPlanSchema.getCertifyCode());
				sqlbv2.put("plantype", tLZCardPlanSchema.getPlanType());
				sqlbv2.put("appcom", globalInput.ComCode);
				tLZCardPlanDB = new LZCardPlanDB();
				tLZCardPlanSet = tLZCardPlanDB.executeQuery(sqlbv2);
				if (tLZCardPlanSet != null && tLZCardPlanSet.size() >= 1) {
					buildError("saveCardPlan", "同一计划类型同一单证,当分公司已经汇总计划提交至总公司且未批复时，分公司和其支公司不允许再次申请：计划类型【"
							+ tLZCardPlanSchema.getPlanType() + "】单证编码【" + tLZCardPlanSchema.getCertifyCode()
							+ "】");
					return false;
				}

				tLZCardPlanSchema.setPlanID(PubFun.getCurrentDate().split("-")[0]
						+ PubFun.getCurrentDate().split("-")[1] + PubFun.getCurrentDate().split("-")[2]
						+ PubFun1.CreateMaxNo("CARDPLAN", 12));
				tLZCardPlanSchema.setAppCom(globalInput.ComCode);
				tLZCardPlanSchema.setAppOperator(globalInput.Operator);
				tLZCardPlanSchema.setMakeDate(PubFun.getCurrentDate());
				tLZCardPlanSchema.setMakeTime(PubFun.getCurrentTime());
				tLZCardPlanSchema.setUpdateDate(PubFun.getCurrentDate());
				tLZCardPlanSchema.setUpdateTime(PubFun.getCurrentTime());
				tLZCardPlanSchema.setPlanState(CardPlanBL.STATE_APP);
			}
			map.put(aLZCardPlanSet, "INSERT");
		} catch (Exception ex) {
			buildError("saveCardPlan", ex.getMessage());
			bReturn = false;
		}

		return bReturn;
	}

	/**
	 * update a card plan
	 * 
	 * @param aLZCardPlanSet
	 * @param globalInput
	 * @return
	 */
	private boolean updateCardPlan(LZCardPlanSet aLZCardPlanSet, GlobalInput globalInput) {
		boolean bReturn = true;
		try {
			LZCardPlanSet tLZCardPlanSet = new LZCardPlanSet();
			LZCardPlanSchema tLZCardPlanSchema = new LZCardPlanSchema();

			for (int nIndex = 1; nIndex <= aLZCardPlanSet.size(); nIndex++) {
				tLZCardPlanSchema = aLZCardPlanSet.get(nIndex);

				LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
				tLMCertifyDesDB.setCertifyCode(tLZCardPlanSchema.getCertifyCode());
				if (!tLMCertifyDesDB.getInfo()) {
					mErrors.copyAllErrors(tLMCertifyDesDB.mErrors);
					return false;
				}

				// 校验计划状态，只有申请状态的单证才可以修改
				LZCardPlanDB tLZCardPlanDB = new LZCardPlanDB();
				tLZCardPlanDB.setPlanID(tLZCardPlanSchema.getPlanID());
				if (!tLZCardPlanDB.getInfo()) {
					mErrors.copyAllErrors(tLZCardPlanDB.mErrors);
					return false;
				}
				if (!tLZCardPlanDB.getPlanState().equals(CardPlanBL.STATE_APP)) {
					throw new Exception("该单证计划不是处于申请状态，不能修改。");
				}

				tLZCardPlanDB.setCertifyCode(tLZCardPlanSchema.getCertifyCode());
				tLZCardPlanDB.setPlanType(tLZCardPlanSchema.getPlanType());
				tLZCardPlanDB.setAppCount(tLZCardPlanSchema.getAppCount());
				tLZCardPlanDB.setConCount(tLZCardPlanSchema.getConCount());
				tLZCardPlanDB.setUpdateDate(PubFun.getCurrentDate());
				tLZCardPlanDB.setUpdateTime(PubFun.getCurrentTime());
				tLZCardPlanSet.add(tLZCardPlanDB.getSchema());
			}
			map.put(tLZCardPlanSet, "UPDATE");
		} catch (Exception ex) {
			buildError("updateCardPlan", ex.getMessage());
			bReturn = false;
		}

		return bReturn;
	}

	/**
	 * deleteCardPlan
	 * 
	 * @param aLZCardPlanSchema
	 * @param globalInput
	 * @return
	 */
	private boolean deleteCardPlan(LZCardPlanSet aLZCardPlanSet, GlobalInput globalInput) {
		boolean bReturn = true;
		try {
			LZCardPlanSet tLZCardPlanSet = new LZCardPlanSet();
			LZCardPlanSchema tLZCardPlanSchema = new LZCardPlanSchema();

			for (int nIndex = 1; nIndex <= aLZCardPlanSet.size(); nIndex++) {
				tLZCardPlanSchema = aLZCardPlanSet.get(nIndex);

				// 校验计划状态，只有申请状态的单证才可以删除
				LZCardPlanDB tLZCardPlanDB = new LZCardPlanDB();
				tLZCardPlanDB.setPlanID(tLZCardPlanSchema.getPlanID());
				if (!tLZCardPlanDB.getInfo()) {
					mErrors.copyAllErrors(tLZCardPlanDB.mErrors);
					return false;
				}
				if (!tLZCardPlanDB.getPlanState().equals(CardPlanBL.STATE_APP)) {
					throw new Exception("该单证计划不是处于申请状态，不能删除。");
				}

				tLZCardPlanSet.add(tLZCardPlanDB.getSchema());
			}
			map.put(tLZCardPlanSet, "DELETE");
		} catch (Exception ex) {
			buildError("deleteCardPlan", ex.getMessage());
			bReturn = false;
		}

		return bReturn;
	}

	/**
	 * package a card plan
	 * 
	 * @param aLZCardPlanSchema
	 * @param aLZCardPlanSet
	 * @param globalInput
	 * @return
	 */
	private boolean packCardPlan(LZCardPlanSchema aLZCardPlanSchema, GlobalInput globalInput) {
		boolean bReturn = true;
		try {
			LZCardPlanSet tInsertLZCardPlanSet = new LZCardPlanSet();
			LZCardPlanSet tUpdateLZCardPlanSet = new LZCardPlanSet();
			LZCardPlanSchema tLZCardPlanSchema = null;

			String strSql = "select a.plantype, a.certifycode,sum(a.concount) "
					+ " from lzcardplan a where a.relaplan is null and a.planstate = 'C' "
					+ " and a.appcom like " + "concat('?appcom?','%')"  + " and a.PlanType = '"
					+ "?PlanType?" + "'"
					+ " group by a.plantype,a.certifycode order by a.certifycode";
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(strSql);
			sqlbv3.put("appcom", aLZCardPlanSchema.getAppCom());
			sqlbv3.put("PlanType", aLZCardPlanSchema.getPlanType());
			ExeSQL exeSQL = new ExeSQL();
			SSRS rs = exeSQL.execSQL(sqlbv3);
			if (rs.getMaxRow() <= 0) {
				buildError("submitCardPlan", "机构【" + aLZCardPlanSchema.getAppCom() + "】没有待汇总的计划！");
				return false;
			} else {
				for (int i = 1; i <= rs.getMaxRow(); i++) {
					String tRelaPlan = PubFun.getCurrentDate().split("-")[0]
							+ PubFun.getCurrentDate().split("-")[1] + PubFun.getCurrentDate().split("-")[2]
							+ PubFun1.CreateMaxNo("CARDPLAN", 12);
					tLZCardPlanSchema = new LZCardPlanSchema();
					tLZCardPlanSchema.setPlanID(tRelaPlan);
					tLZCardPlanSchema.setPlanType(rs.GetText(i, 1));
					tLZCardPlanSchema.setCertifyCode(rs.GetText(i, 2));
					tLZCardPlanSchema.setAppCount(rs.GetText(i, 3));
					tLZCardPlanSchema.setConCount(rs.GetText(i, 3));// 分公司汇总提交的计划，总公司不做调整，所以调整数量默认等于申请数量
					tLZCardPlanSchema.setAppOperator(aLZCardPlanSchema.getAppOperator());
					tLZCardPlanSchema.setAppCom(aLZCardPlanSchema.getAppCom());
					logger.debug("汇总提交数据" + i);
					logger.debug("PlanID=" + tRelaPlan);
					logger.debug("PlanType=" + rs.GetText(i, 1));
					logger.debug("CertifyCode=" + rs.GetText(i, 2));
					logger.debug("AppCount=" + rs.GetText(i, 3));

					tLZCardPlanSchema.setMakeDate(PubFun.getCurrentDate());
					tLZCardPlanSchema.setMakeTime(PubFun.getCurrentTime());
					tLZCardPlanSchema.setUpdateDate(PubFun.getCurrentDate());
					tLZCardPlanSchema.setUpdateTime(PubFun.getCurrentTime());
					tLZCardPlanSchema.setPlanState("S");// 汇总提交状态 S[ubmit]
					tInsertLZCardPlanSet.add(tLZCardPlanSchema);// 保存分公司汇总的计划

					LZCardPlanDB tLZCardPlanDB = new LZCardPlanDB();
					String strSql2 = "select a.* from lzcardplan a where a.relaplan is null and a.planstate = 'C' "
							+ "and a.plantype='"
							+ "?plantype?"
							+ "' and a.certifycode='"
							+ "?certifycode?"
							+ "' and a.appcom like "
							+ "concat('?appcom?','%')"
							+ "";
					SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
					sqlbv4.sql(strSql2);
					sqlbv4.put("plantype", rs.GetText(i, 1));
					sqlbv4.put("certifycode", rs.GetText(i, 2));
					sqlbv4.put("appcom", aLZCardPlanSchema.getAppCom());
					LZCardPlanSet tLZCardPlanSet = new LZCardPlanSet();
					tLZCardPlanSet = tLZCardPlanDB.executeQuery(sqlbv4);
					logger.debug("汇总提交更新支公司RelaPlan:" + tRelaPlan);
					for (int j = 1; j <= tLZCardPlanSet.size(); j++) {
						tLZCardPlanSet.get(j).setRelaPlan(tRelaPlan);
						logger.debug("更新的支公司PlanID=" + tLZCardPlanSet.get(j).getPlanID());
					}
					tUpdateLZCardPlanSet.add(tLZCardPlanSet);// 更新支公司计划的RelaPlan，使之于汇总的计划关联起来
				}
			}

			map.put(tInsertLZCardPlanSet, "INSERT");
			map.put(tUpdateLZCardPlanSet, "UPDATE");
		} catch (Exception ex) {
			buildError("packCardPlan", ex.getMessage());
			bReturn = false;
		}

		return bReturn;
	}

	// // 计划调整保存
	private boolean confCardPlan(LZCardPlanSet aLZCardPlanSet, GlobalInput globalInput) {
		boolean bReturn = true;
		try {
			for (int i = 1; i <= aLZCardPlanSet.size(); i++) {
				// 校验计划状态，只有已提交的单证才可以调整
				LZCardPlanDB tLZCardPlanDB = new LZCardPlanDB();
				tLZCardPlanDB.setPlanID(aLZCardPlanSet.get(i).getPlanID());
				if (!tLZCardPlanDB.getInfo()) {
					mErrors.copyAllErrors(tLZCardPlanDB.mErrors);
					return false;
				}
				if (!tLZCardPlanDB.getPlanState().equals(CardPlanBL.STATE_CON)) {
					throw new Exception("该单证计划状态不合法，不能提交。");
				}
				tLZCardPlanDB.setConCount(aLZCardPlanSet.get(i).getConCount());
				tLZCardPlanDB.setConCom(aLZCardPlanSet.get(i).getConCom());
				tLZCardPlanDB.setConOperator(aLZCardPlanSet.get(i).getConOperator());
				tLZCardPlanDB.setUpdateDate(PubFun.getCurrentDate());
				tLZCardPlanDB.setUpdateTime(PubFun.getCurrentTime());

				map.put(tLZCardPlanDB.getSchema(), "UPDATE");
			}
		} catch (Exception ex) {
			buildError("submitCardPlan", ex.getMessage());
			bReturn = false;
		}

		return bReturn;
	}

	private boolean confCardPlan2(LZCardPlanSet aLZCardPlanSet, GlobalInput globalInput) {
		boolean bReturn = true;
		try {
			LZCardPlanDB tLZCardPlanDB;
			for (int i = 1; i <= aLZCardPlanSet.size(); i++) {
				// 校验计划状态，只有申请状态的单证才可以直接提交
				tLZCardPlanDB = new LZCardPlanDB();
				tLZCardPlanDB.setPlanID(aLZCardPlanSet.get(i).getPlanID());
				if (!tLZCardPlanDB.getInfo()) {
					mErrors.copyAllErrors(tLZCardPlanDB.mErrors);
					return false;
				}
				if (!tLZCardPlanDB.getPlanState().equals(CardPlanBL.STATE_APP)) {
					throw new Exception("该单证计划状态不合法，不能提交。");
				}
				tLZCardPlanDB.setPlanState(CardPlanBL.STATE_CON);// 直接提交
				tLZCardPlanDB.setUpdateDate(PubFun.getCurrentDate());
				tLZCardPlanDB.setUpdateTime(PubFun.getCurrentTime());

				map.put(tLZCardPlanDB.getSchema(), "UPDATE");
			}
		} catch (Exception ex) {
			buildError("confCardPlan2", ex.getMessage());
			bReturn = false;
		}

		return bReturn;
	}

	/**
	 * ret a card plan
	 * 
	 * @param aLZCardPlanSchema
	 * @param aOldLZCardPlanSet
	 * @param aNewLZCardPlanSet
	 * @param globalInput
	 * @return
	 */
	private boolean retCardPlan(LZCardPlanSchema aLZCardPlanSchema, LZCardPlanSet aLZCardPlanSet,
			GlobalInput globalInput) {
		boolean bReturn = true;

		try {
			LZCardPlanSchema tLZCardPlanSchema = null;
			LZCardPlanDB tLZCardPlanDB = null;

			for (int nIndex = 0; nIndex < aLZCardPlanSet.size(); nIndex++) {
				tLZCardPlanSchema = aLZCardPlanSet.get(nIndex + 1);

				tLZCardPlanDB = new LZCardPlanDB();
				tLZCardPlanDB.setPlanID(tLZCardPlanSchema.getPlanID());
				tLZCardPlanDB.getInfo();

				// 校验上级计划是否批复，上级计划未批复不能批复下级计划
				if (tLZCardPlanDB.getRelaPlan() != null && tLZCardPlanDB.getRelaPlan() != "") {
					LZCardPlanDB aLZCardPlanDB = new LZCardPlanDB();
					aLZCardPlanDB.setPlanID(tLZCardPlanDB.getRelaPlan());
					if (!aLZCardPlanDB.getInfo()) {
						throw new Exception("单证关联计划【" + tLZCardPlanDB.getRelaPlan() + "】查询失败");
					} else if (aLZCardPlanDB.getPlanType().equals("R")) {
						throw new Exception("单证上级计划【" + tLZCardPlanDB.getRelaPlan() + "】未批复，不能批复下级计划");
					}

					String sql = "select (case when sum(a.retcount) is null then 0 else sum(a.retcount) end) from lzcardplan a where a.relaplan = '"
							+ "?relaplan?" + "' and a.planstate = 'R'";
					SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
					sqlbv5.sql(sql);
					sqlbv5.put("relaplan", tLZCardPlanDB.getRelaPlan());
					ExeSQL tExeSQL = new ExeSQL();
					int sumRetcount = Integer.parseInt(tExeSQL.getOneValue(sqlbv5));

					if (tLZCardPlanSchema.getRetCount() + sumRetcount > aLZCardPlanDB.getRetCount()) {
						throw new Exception("单证【" + aLZCardPlanDB.getCertifyCode() + "】超出上级批复数量，上级批复数量【"
								+ aLZCardPlanDB.getRetCount() + "】，已经向下级批复数量【" + sumRetcount + "】，本次批复数【"
								+ tLZCardPlanSchema.getRetCount() + "】");
					}
				}

				tLZCardPlanDB.setRetCount(tLZCardPlanSchema.getRetCount());
				if (tLZCardPlanDB.getRetCount() > 0) {
					tLZCardPlanDB.setRetState(CardPlanBL.RET_STATE_PASS);
				} else {
					tLZCardPlanDB.setRetState(CardPlanBL.RET_STATE_REFUSE);
				}
				tLZCardPlanDB.setRetState(tLZCardPlanSchema.getRetState());
				tLZCardPlanDB.setRetOperator(tLZCardPlanSchema.getRetOperator());
				tLZCardPlanDB.setRetCom(tLZCardPlanSchema.getRetCom());
				tLZCardPlanDB.setUpdateDate(PubFun.getCurrentDate());
				tLZCardPlanDB.setUpdateTime(PubFun.getCurrentTime());
				tLZCardPlanDB.setPlanState(CardPlanBL.STATE_RET);

				tLZCardPlanSchema.setSchema(tLZCardPlanDB);
			}
			map.put(aLZCardPlanSet, "UPDATE");
		} catch (Exception ex) {
			buildError("retCardPlan", ex.getMessage());
			bReturn = false;
		}

		return bReturn;
	}

	/**
	 * assign a card plan
	 * 
	 * @param aLZCardPlanSchema
	 * @param aOldLZCardPlanSet
	 * @param aNewLZCardPlanSet
	 * @param globalInput
	 * @return
	 */
	private boolean assignCardPlan(LZCardPlanSchema aLZCardPlanSchema, LZCardPlanSet aLZCardPlanSet,
			GlobalInput globalInput) {
		boolean bReturn = true;

		try {
			LZCardPlanSchema tLZCardPlanSchema = null;
			LZCardPlanDB tLZCardPlanDB = null;
			int addCount = 0;// 记录减少或者增加的分配数量，不能大于页面分配后结余即总公司库存

			for (int nIndex = 0; nIndex < aLZCardPlanSet.size(); nIndex++) {
				tLZCardPlanSchema = aLZCardPlanSet.get(nIndex + 1);

				tLZCardPlanDB = new LZCardPlanDB();
				tLZCardPlanDB.setPlanID(tLZCardPlanSchema.getPlanID());
				tLZCardPlanDB.getInfo();

				// 状态为S的分公司计划才可以分配库存
				if (tLZCardPlanDB.getRelaPlan() != null && tLZCardPlanDB.getPlanState() != "S") {
					throw new Exception("单证计划【" + tLZCardPlanDB.getRelaPlan() + "】状态不合法，不能分配库存");
				}

				addCount += (tLZCardPlanSchema.getAssignCount() - tLZCardPlanDB.getAssignCount());

				tLZCardPlanDB.setAssignCount(tLZCardPlanSchema.getAssignCount());
				tLZCardPlanDB.setUpdateDate(PubFun.getCurrentDate());
				tLZCardPlanDB.setUpdateTime(PubFun.getCurrentTime());

				tLZCardPlanSchema.setSchema(tLZCardPlanDB);
			}
			logger.debug("此次库存分配增加分配量：" + addCount);
			logger.debug("分配前库存量：" + aLZCardPlanSchema.getAppCount());
			if (addCount > aLZCardPlanSchema.getAppCount()) {// 暂时借用此字段用来记录分配后结余
				throw new Exception("各分公司分配量合计，不能大于库存量，请重新录入!");
			}
			map.put(aLZCardPlanSet, "UPDATE");
		} catch (Exception ex) {
			buildError("assignCardPlan", ex.getMessage());
			bReturn = false;
		}

		return bReturn;
	}

	/**
	 * query ldcom, get superior com of specified com
	 * 
	 * @param strCom
	 * @return
	 */
	private String getSuperior(String strCom) {
		String strSQL = "select code from ldcode" + " where codetype = 'station'" + " and '" + "?code?"
				+ "' like "+"concat(concat('%' , trim(code) ), '%')" + " and '" + "?code?" + "' <> code" + " order by code desc";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(strSQL);
		sqlbv6.put("code", strCom);
		ExeSQL exeSQL = new ExeSQL();
		SSRS rs = exeSQL.execSQL(sqlbv6);

		if (rs.getMaxRow() != 0) {
			return rs.GetText(1, 1);
		}

		return "";
	}

	private int getPageCount(String strSQL, int nRecordNum) {
		String sSQL = strSQL.toLowerCase();

		sSQL = "select count(*) " + strSQL.substring(strSQL.indexOf("from"));
        SQLwithBindVariables sqlbva = new SQLwithBindVariables();
        sqlbva.sql(sSQL);
		ExeSQL exeSQL = new ExeSQL();
		SSRS ssrs = exeSQL.execSQL(sqlbva);

		int nRecordCount = Integer.parseInt(ssrs.GetText(1, 1));

		return ((nRecordCount + nRecordNum) - 1) / nRecordNum;
	}
}
