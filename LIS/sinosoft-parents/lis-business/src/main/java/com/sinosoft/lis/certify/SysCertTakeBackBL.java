/*
 * <p>ClassName: SysCertTakeBackBL </p>
 * <p>Description: SysCertTakeBackBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-10-29
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class SysCertTakeBackBL {
private static Logger logger = Logger.getLogger(SysCertTakeBackBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 全局数据 */
	private GlobalInput globalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperation = "";

	/** 业务处理相关变量 */
	private LZSysCertifySchema mLZSysCertifySchema = new LZSysCertifySchema();

	private String m_strWhere = "";

	private static final String VALID_AGENT_STATE = " IN ('01', '02') ";

	private LZSysCertifySchema mDeleteSchema = new LZSysCertifySchema();

	private LZSysCertifySchema mInsertSchema = new LZSysCertifySchema();

	public SysCertTakeBackBL() {
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
		mOperation = verifyOperate(cOperate);

		if (mOperation.equals("")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;

		if (mOperation.equals("QUERY||MAIN")) {
			submitquery();
			return true;
		}

		// 业务校验
		if (!checkDate()) {
			buildError("submitData", "数据处理失败SysCertTakeBackBL-->checkData!");
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			buildError("submitData", "数据处理失败SysCertTakeBackBL-->dealData!");
			return false;
		}

		// 准备往后台的数据
		VData vData = new VData();

		if (!prepareOutputData(vData))
			return false;

		if (mOperation.equals("INSERT||MAIN")) {
			SysCertTakeBackBLS sysCertTakeBackBLS = new SysCertTakeBackBLS();

			if (!sysCertTakeBackBLS.submitData(vData, mOperation)) {
				if (sysCertTakeBackBLS.mErrors.needDealError()) {
					mErrors.copyAllErrors(sysCertTakeBackBLS.mErrors);

				} else {
					buildError("submitData",
							"数据提交失败SysCertTakeBackBLS-->sutmitData，但是没有提供详细信息");
				}
				return false;
			}
		}

		return true;
	}

	/**
	 * 校验回收日期不能晚于当前日期
	 */
	private boolean checkDate() {
		if (PubFun.calInterval3(mLZSysCertifySchema.getTakeBackDate(), PubFun
				.getCurrentDate(), "D") < 0) {
			CError tError = new CError();
			tError.moduleName = "SysCertTakeBackBL";
			tError.functionName = "submitData";
			tError.errorMessage = "回收日期不能晚于当前日期，请您核实，该次操作被取消！！！！";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 保单回执
		if (mLZSysCertifySchema.getCertifyCode().equals("9995")) {
			String checkSql = "select signDate from lcpol where  polno='"
					+ "?polno?" + "'";
			logger.debug("校验待回收单证号是否能在保单表中查询到的sql:" + checkSql);
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(checkSql);
			sqlbv.put("polno", mLZSysCertifySchema.getCertifyNo());
			SSRS ttSSRS = new ExeSQL().execSQL(sqlbv);

			if (ttSSRS.getMaxRow() <= 0) {
				CError tError = new CError();
				tError.moduleName = "SysCertTakeBackBL";
				tError.functionName = "submitData";
				tError.errorMessage = "录入的单证号码有误，不是系统签发保单，无法进行回收！";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				if (PubFun.calInterval3(ttSSRS.GetText(1, 1),
						mLZSysCertifySchema.getTakeBackDate(), "D") < 0) {
					CError tError = new CError();
					tError.moduleName = "SysCertTakeBackBL";
					tError.functionName = "submitData";
					tError.errorMessage = "回执日期不能早于保单签发日期，请您核实！";
					this.mErrors.addOneError(tError);
					return false;
				}
			}

			ttSSRS = null;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		try {
			LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();

			String szCurDate = PubFun.getCurrentDate();
			String szCurTime = PubFun.getCurrentTime();

			tLZSysCertifySchema.setSchema(mLZSysCertifySchema);

			logger.debug("In SysCertTakeBackBL --- SendOutCom : "
					+ tLZSysCertifySchema.getSendOutCom());
			logger.debug("In SysCertTakeBackBL --- CertifyCode : "
					+ tLZSysCertifySchema.getCertifyCode());
			// 校验是否是个人保单

			// 校验是否是放开业务员（ｘｘｘ）已经离职或失效校验的单证
			String sql;

			sql = "SELECT * FROM LMCertifyDes";
			sql += (" WHERE CertifyCode = '"
					+ "?CertifyCode?" + "'");
			sql += (" AND CertifyClass = '" + CertifyFunc.CERTIFY_CLASS_SYS + "'")
					+ (" AND JugAgtFlag in (1,3)");

			logger.debug("sql:" + sql);
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("CertifyCode", tLZSysCertifySchema.getCertifyCode().trim());
			if (new LMCertifyDesDB().executeQuery(sqlbv1).size() == 1) {
				// 校验保单的代理人和录入的代理人是否一致
				if (tLZSysCertifySchema.getCertifyCode().trim().equals("9995")) {
					if (!CertifyFunc.verifyAgentCode(tLZSysCertifySchema
							.getCertifyNo(), tLZSysCertifySchema
							.getSendOutCom(), tLZSysCertifySchema
							.getReceiveCom())) {
						buildError("saveLZSysCertify", CertifyFunc.mErrors
								.getFirstError());
						return false;
					}
				}

				// 是个人保单的情况下，不校验业务员的状态
				if (!CertifyFunc.isComsExist2(tLZSysCertifySchema
						.getSendOutCom(), tLZSysCertifySchema.getReceiveCom())) {
					buildError("saveLZSysCertify", CertifyFunc.mErrors
							.getFirstError());
					return false;
				}
			} else {
				if (!CertifyFunc.isComsExist(tLZSysCertifySchema
						.getSendOutCom(), tLZSysCertifySchema.getReceiveCom())) {
					buildError("saveLZSysCertify", CertifyFunc.mErrors
							.getFirstError());
					return false;
				}
			}

			// 校验单证编码
			String strSql;

			strSql = "SELECT * FROM LZSysCertify";
			strSql += " WHERE CertifyCode = '"
					+ "?CertifyCode?" + "'";
			strSql += " AND CertifyNo = '" + "?CertifyNo?"
					+ "'";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables() ;
			sqlbv2.sql(strSql);
			sqlbv2.put("CertifyCode", tLZSysCertifySchema.getCertifyCode());
			sqlbv2.put("CertifyNo", tLZSysCertifySchema.getCertifyNo());
			logger.debug("Sql in SysCertTakeBackBL : " + strSql);
			LZSysCertifySet tLZSysCertifySet = new LZSysCertifyDB()
					.executeQuery(sqlbv2);

			if (tLZSysCertifySet.size() != 1) {
				if (!autoSendOutSysCert(tLZSysCertifySchema)) {
					return false;
				}
				logger.debug("------------------------------");
				tLZSysCertifySet = new LZSysCertifyDB().executeQuery(sqlbv2);

				if (tLZSysCertifySet.size() != 1) {
					buildError("saveLZSysCertify", "系统单证信息有误，找不到要回收的系统单证");
					return false;
				}
			}

			// 删除原来的记录
			mDeleteSchema.setSchema(tLZSysCertifySet.get(1));

			// 新的记录
			mInsertSchema.setSchema(tLZSysCertifySet.get(1));

			mInsertSchema.setModifyDate(szCurDate);
			mInsertSchema.setModifyTime(szCurTime);
			mInsertSchema.setOperator(globalInput.Operator);
			mInsertSchema.setStateFlag("1");

			// 使用查询出来的数据中没有这个值
			mInsertSchema
					.setTakeBackDate(mLZSysCertifySchema.getTakeBackDate());

			mInsertSchema.setTakeBackOperator(globalInput.Operator);
			mInsertSchema.setTakeBackMakeDate(szCurDate);
			mInsertSchema.setTakeBackMakeTime(szCurTime);

			if (!CertifyFunc.callCertifyService(mInsertSchema, globalInput)) {
				mErrors.copyAllErrors(CertifyFunc.mErrors);
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("dealData", "发生异常");
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLZSysCertifySchema.setSchema((LZSysCertifySchema) cInputData
				.getObjectByObjectName("LZSysCertifySchema", 0));
		m_strWhere = (String) cInputData.getObjectByObjectName("String", 0);

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		mResult.clear();

		logger.debug("Start query in SysCertTakeBackBL ...");

		LZSysCertifyDB dbLZSysCertify = new LZSysCertifyDB();

		SQLString sqlObj = new SQLString("LZSysCertify");

		sqlObj.setSQL(5, mLZSysCertifySchema);

		String strSQL = sqlObj.getSQL();

		if (!m_strWhere.equals("")) {
			if (strSQL.toUpperCase().indexOf("WHERE") != -1) {
				strSQL += " AND " + m_strWhere;
			} else {
				strSQL += " WHERE " + m_strWhere;
			}
		}

		logger.debug(strSQL);
        SQLwithBindVariables sqlbva = new SQLwithBindVariables();
        sqlbva.sql(strSQL);
		mResult.add(dbLZSysCertify.executeQuery(sqlbva));

		logger.debug("End query in SysCertTakeBackBL ...");

		// 如果有需要处理的错误，则返回
		if (dbLZSysCertify.mErrors.needDealError()) {
			buildError("submitquery", "查询失败");
			return false;
		}

		return true;
	}

	private boolean prepareOutputData(VData vData) {
		try {
			vData.add(globalInput);

			vData.add(mDeleteSchema);
			vData.add(mInsertSchema);

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "在准备往后层处理所需要的数据时出错");
			return false;
		}
	}

	public VData getResult() {
		return this.mResult;
	}

	/*
	 * add by kevin, 2002-09-23
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "SysCertTakeBackBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private String verifyOperate(String szOperate) {
		String szReturn = "";
		String szOperates[] = { "INSERT||MAIN", "DELETE||MAIN", "UPDATE||MAIN",
				"QUERY||MAIN" };

		for (int nIndex = 0; nIndex < szOperates.length; nIndex++) {
			if (szOperate.equals(szOperates[nIndex])) {
				szReturn = szOperate;
			}
		}

		return szReturn;
	}

	private boolean autoSendOutSysCert(LZSysCertifySchema aLZSysCertifySchema) {
		SysCertSendOutUI tSysCertSendOutUI = new SysCertSendOutUI();

		LZSysCertifySchema tLZSysCertifySchema = new LZSysCertifySchema();

		tLZSysCertifySchema
				.setCertifyCode(aLZSysCertifySchema.getCertifyCode());
		tLZSysCertifySchema.setCertifyNo(aLZSysCertifySchema.getCertifyNo());
		tLZSysCertifySchema.setSendOutCom(aLZSysCertifySchema.getReceiveCom());
		tLZSysCertifySchema.setReceiveCom(aLZSysCertifySchema.getSendOutCom());

		LZSysCertifySet tLZSysCertifySet = new LZSysCertifySet();

		tLZSysCertifySet.add(tLZSysCertifySchema);

		VData tVData = new VData();

		tVData.add(globalInput);
		tVData.add(tLZSysCertifySet);

		if (!tSysCertSendOutUI.submitData(tVData, "INSERT||MAIN")) {
			if (tSysCertSendOutUI.mErrors.needDealError()) {
				mErrors.copyAllErrors(tSysCertSendOutUI.mErrors);
			} else {
				buildError("autoSendOutSysCert", "自动发放系统单证失败，但是没有详细信息！");
			}
			return false;
		}

		return true;
	}
}
