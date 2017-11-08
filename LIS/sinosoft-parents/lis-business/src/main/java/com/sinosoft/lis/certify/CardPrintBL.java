/*
 * <p>ClassName: CardPrintBL </p>
 * <p>Description: CardPrintBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2002-10-12
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sinosoft.lis.db.LMCertifyDesDB;
import com.sinosoft.lis.db.LZCardPlanDB;
import com.sinosoft.lis.db.LZCardPrintDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LZCardPrintSchema;
import com.sinosoft.lis.vschema.LZCardPlanSet;
import com.sinosoft.lis.vschema.LZCardPrintSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLString;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class CardPrintBL {
private static Logger logger = Logger.getLogger(CardPrintBL.class);

	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String strOperate;

	/** 业务处理相关变量 */
	private LZCardPrintSchema mLZCardPrintSchema = new LZCardPrintSchema();

	private LZCardPrintSet mLZCardPrintSet = new LZCardPrintSet();

	private String m_strWhere = "";

	/** 往后面传输数据的容器 */
	private MMap map = new MMap();

	private VData mResult = new VData();

	public CardPrintBL() {
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
			buildError("submitData", "不支持的操作字符串!");
			return false;
		}

		if (!getInputData(cInputData))
			return false;

		if (!dealData())
			return false;

		if (!prepareOutputData())
			return false;

		// 数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mResult, "")) {
			buildError("submitData", "数据提交失败!");
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			return false;
		}

		return true;
	}

	private String verifyOperate(String szOperate) {
		String szReturn = "";
		String szOperates[] = { "INSERT", "UPDATE", "QUERY||MAIN" };

		for (int nIndex = 0; nIndex < szOperates.length; nIndex++) {
			if (szOperate.equals(szOperates[nIndex])) {
				szReturn = szOperate;
			}
		}

		return szReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		try {
			this.mLZCardPrintSchema = (LZCardPrintSchema) cInputData.getObjectByObjectName(
					"LZCardPrintSchema", 0);
			this.mLZCardPrintSet = (LZCardPrintSet) cInputData.getObjectByObjectName("LZCardPrintSet", 0);
			this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));

			m_strWhere = (String) cInputData.getObjectByObjectName("String", 0);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("getInputData", "发生异常!");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		if (strOperate.equals("INSERT")) {
			return saveCardPrint(mLZCardPrintSet, mGlobalInput);
		}

		if (strOperate.equals("UPDATE")) {
			return confirmCardPrint(mLZCardPrintSet, mGlobalInput);
		}

		if (strOperate.equals("QUERY||MAIN")) {
			this.submitquery();
		}

		return true;
	}

	/**
	 * 印刷计划汇总
	 */
	private boolean saveCardPrint(LZCardPrintSet tLZCardPrintSet, GlobalInput globalInput) {
		boolean bReturn = true;

		try {
			for (int i = 1; i <= tLZCardPrintSet.size(); i++) {
				LZCardPrintSchema schemaLZCardPrint = tLZCardPrintSet.get(i);

				LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
				tLMCertifyDesDB.setCertifyCode(schemaLZCardPrint.getCertifyCode());
				if (!tLMCertifyDesDB.getInfo()) {
					buildError("saveCardPrint", "无效的单证编码！");
					return false;
				}
				String tPrtNo = (globalInput.ComCode + "00").substring(0, 4)
						+ PubFun.getCurrentDate().substring(2, 4) + PubFun.getCurrentDate().substring(5, 7)
						+ PubFun1.CreateMaxNo("PrtNo", 4);

				// 更新汇总的计划的管理印刷号码，存入印刷批次号
				String strSql = "select a.* "
						+ " from lzcardplan a where a.planstate = 'R' and a.relaplan is null and a.relaprint is null "
						+ " and a.CertifyCode='" + "?CertifyCode?" + "' and a.PlanType='"
						+ "?PlanType?" + "'";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(strSql);
				sqlbv.put("CertifyCode", schemaLZCardPrint.getCertifyCode());
				sqlbv.put("PlanType", schemaLZCardPrint.getPlanType());
				LZCardPlanDB tLZCardPlanDB = new LZCardPlanDB();
				LZCardPlanSet tLZCardPlanSet = new LZCardPlanSet();
				tLZCardPlanSet = tLZCardPlanDB.executeQuery(sqlbv);
				if (tLZCardPlanSet == null || tLZCardPlanSet.size() <= 0) {
					logger.debug("查询到待汇总计划:" + strSql);
					buildError("saveCardPrint", "没有查询到待汇总计划！");
					return false;
				} else {
					for (int j = 1; j <= tLZCardPlanSet.size(); j++) {
						tLZCardPlanSet.get(j).setRelaPrint(tPrtNo);
						tLZCardPlanSet.get(j).setUpdateDate(PubFun.getCurrentDate());
						tLZCardPlanSet.get(j).setUpdateTime(PubFun.getCurrentTime());
					}
				}
				map.put(tLZCardPlanSet, "UPDATE");

				// 保存单证印刷表：待印刷单证
				schemaLZCardPrint.setPrtNo(tPrtNo);
				schemaLZCardPrint.setInputOperator(globalInput.Operator);
				schemaLZCardPrint.setInputDate(PubFun.getCurrentDate());
				schemaLZCardPrint.setModifyDate(PubFun.getCurrentDate());
				schemaLZCardPrint.setModifyTime(PubFun.getCurrentTime());
				schemaLZCardPrint.setState("0");// 计划汇总后，待印刷

				// 自印单证，需要校验起始号和终止号
				if ("6".equals(schemaLZCardPrint.getPlanType())) {// 6:自印单证
					if (!checkNo(schemaLZCardPrint)) {
						return false;
					}
				} else {
					schemaLZCardPrint.setManageCom("86");// 非自印单证印刷机构为86
				}

				map.put(schemaLZCardPrint, "INSERT");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("saveCardPrint", ex.getMessage());
			bReturn = false;
		}
		return bReturn;
	}

	/**
	 * 单证印刷
	 */
	private boolean confirmCardPrint(LZCardPrintSet tLZCardPrintSet, GlobalInput globalInput) {
		boolean bReturn = true;

		try {
			LZCardPrintSchema schemaLZCardPrint = new LZCardPrintSchema();
			for (int i = 1; i <= tLZCardPrintSet.size(); i++) {
				schemaLZCardPrint = tLZCardPrintSet.get(i);

				// 校验使用截止日期
				if (schemaLZCardPrint.getMaxDate() != null && !schemaLZCardPrint.getMaxDate().equals("")) {
					logger.debug(schemaLZCardPrint.getMaxDate());
					if (!isValidDate(schemaLZCardPrint.getMaxDate())) {
						buildError("confirmCardPrint", "印刷批次号" + schemaLZCardPrint.getPrtNo()
								+ ",请输入正确格式的使用截止日期：yy-mm-dddd！");
						return false;
					} else if (PubFun.checkDate(schemaLZCardPrint.getMaxDate(), PubFun.getCurrentDate())) {
						buildError("confirmCardPrint", "印刷批次号" + schemaLZCardPrint.getPrtNo()
								+ ",使用截止日期早于于系统当前日期，请更改！");
						return false;
					}
				}

				// 校验印刷批次号码
				LZCardPrintDB tLZCardPrintDB = new LZCardPrintDB();
				tLZCardPrintDB.setPrtNo(schemaLZCardPrint.getPrtNo());
				if (!tLZCardPrintDB.getInfo()) {
					buildError("confirmCardPrint", "找不到印刷批次号为" + schemaLZCardPrint.getPrtNo() + "的记录！");
					return false;
				}

				// 校验登录机构
				if (!tLZCardPrintDB.getManageCom().equals(globalInput.ComCode)) {
					buildError("confirmCardPrint", "印刷批次号为" + schemaLZCardPrint.getPrtNo()
							+ "的管理机构与当前登录机构不符！");
					return false;
				}

				// 校验单证印刷状态
				if (!tLZCardPrintDB.getState().equals("0")) {
					buildError("confirmCardPrint", "印刷批次号为" + schemaLZCardPrint.getPrtNo()
							+ "的单证不是计划汇总完成待印刷状态！");
					return false;
				}

				// 校验自印单证起止号码
				if ("6".equals(tLZCardPrintDB.getPlanType())) {
					if (!tLZCardPrintDB.getStartNo().equals(schemaLZCardPrint.getStartNo())
							|| !tLZCardPrintDB.getEndNo().equals(schemaLZCardPrint.getEndNo())) {
						buildError("confirmCardPrint", "自印单证不能更改总公司指定的印刷单证起止号码！");
						return false;
					}
				}

				// 校验单证状态
				LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
				tLMCertifyDesDB.setCertifyCode(tLZCardPrintDB.getCertifyCode());
				if (!tLMCertifyDesDB.getInfo()) {
					buildError("saveCardPrint", "无效的单证编码【" + tLZCardPrintDB.getCertifyCode() + "】！");
					return false;
				} else if (tLMCertifyDesDB.getState().equals("1")) {
					buildError("saveCardPrint", "单证【" + tLZCardPrintDB.getCertifyCode() + "】已停用，不允许印刷！");
					return false;
				}

				// 校验单证号码长度
				if (schemaLZCardPrint.getStartNo().length() != tLMCertifyDesDB.getCertifyLength()
						|| schemaLZCardPrint.getEndNo().length() != tLMCertifyDesDB.getCertifyLength()) {
					buildError("saveCardPrint", "单证【" + tLZCardPrintDB.getCertifyCode() + "】的号码长度应该为"
							+ tLMCertifyDesDB.getCertifyLength() + "，请检查起止号码长度！");
					return false;
				}

				// 校验印刷数量
				if (((int) CertifyFunc.bigIntegerDiff(schemaLZCardPrint.getEndNo(), schemaLZCardPrint
						.getStartNo()) + 1) != tLZCardPrintDB.getSumCount()) {
					buildError("saveCardPrint", "单证【" + tLZCardPrintDB.getCertifyCode() + "】印刷总量为"
							+ tLZCardPrintDB.getSumCount() + "，请检查起止号码！");
					return false;
				}

				// 校验起止号码是否已经印刷过
				String strSql = "select * from lzcardprint a where a.prtno !='"
						+ "?prtno?" + "' and a.certifycode = '"
						+ "?certifycode?" + "' and a.startno <= '"
						+ "?startno?" + "' and a.endno >= '"
						+ "?startno?" + "'";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSql);
				sqlbv1.put("prtno", tLZCardPrintDB.getPrtNo());
				sqlbv1.put("certifycode", tLZCardPrintDB.getCertifyCode());
				sqlbv1.put("startno", schemaLZCardPrint.getEndNo());
				sqlbv1.put("endno", schemaLZCardPrint.getStartNo());
				LZCardPrintSet mLZCardPrintSet = new LZCardPrintSet();
				mLZCardPrintSet = new LZCardPrintDB().executeQuery(sqlbv1);
				if (mLZCardPrintSet != null && mLZCardPrintSet.size() >= 1) {
					buildError("saveCardPrint", "单证【" + tLZCardPrintDB.getCertifyCode() + "】起止号码已印刷过,"
							+ "请检查起止号码！");
					return false;
				}

				tLZCardPrintDB.setStartNo(schemaLZCardPrint.getStartNo());
				tLZCardPrintDB.setEndNo(schemaLZCardPrint.getEndNo());
				tLZCardPrintDB.setCertifyPrice(schemaLZCardPrint.getCertifyPrice());

				tLZCardPrintDB.setPrintery(schemaLZCardPrint.getPrintery());
				tLZCardPrintDB.setPhone(schemaLZCardPrint.getPhone());
				tLZCardPrintDB.setLinkMan(schemaLZCardPrint.getLinkMan());
				tLZCardPrintDB.setMaxDate(schemaLZCardPrint.getMaxDate());

				tLZCardPrintDB.setPrintMan(globalInput.Operator);
				tLZCardPrintDB.setPrintDate(PubFun.getCurrentDate());
				tLZCardPrintDB.setPrintOperator(globalInput.Operator);
				tLZCardPrintDB.setModifyDate(PubFun.getCurrentDate());
				tLZCardPrintDB.setModifyTime(PubFun.getCurrentTime());
				tLZCardPrintDB.setState("1");// 1---印刷完成，可以入库的单证状态。

				if (!"6".equals(tLZCardPrintDB.getPlanType())) {// 非自印单证
					if (!checkNo(tLZCardPrintDB)) {
						return false;
					}
				}
				map.put(tLZCardPrintDB.getSchema(), "UPDATE");

				tLMCertifyDesDB.setCertifyPrice(tLZCardPrintDB.getCertifyPrice()); // 操作印刷后，更新单证定义的单证单价
				tLMCertifyDesDB.setMaxPrintNo(tLZCardPrintDB.getEndNo());// 操作印刷后，更新单证定义的印刷最后号码
				map.put(tLMCertifyDesDB.getSchema(), "UPDATE");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("confirmCardPrint", ex.getMessage());
			bReturn = false;
		}
		return bReturn;
	}

	/**
	 * 校验单证号码 根据单证描述表中的信息:如果是无号单证，则根据输入的数量，自动产生起始号和终止号；如果是有号单证，校验单证号码的正确性。
	 */
	private boolean checkNo(LZCardPrintSchema aLZCardPrintSchema) {
		try {
			LMCertifyDesDB tLMCertifyDesDB = new LMCertifyDesDB();
			tLMCertifyDesDB.setCertifyCode(aLZCardPrintSchema.getCertifyCode());
			if (!tLMCertifyDesDB.getInfo()) {
				buildError("checkNo", "无效的单证编码！");
				return false;
			}

			int nLen = (int) tLMCertifyDesDB.getCertifyLength();

			if (tLMCertifyDesDB.getHaveNumber().equals("N")) {// 如果是无号单证,自动产生起始号和终止号
				if (aLZCardPrintSchema.getSumCount() < 1) {
					buildError("checkNo", "对于无号单证，请输入正确的单证数量!");
					return false;
				}

				String strSQL = "SELECT (case when TRIM(MAX(EndNo) + 1) is null then '1' else TRIM(MAX(EndNo) + 1) end) FROM LZCardPrint WHERE CertifyCode = '"
						+ "?CertifyCode?" + "'";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(strSQL);
				sqlbv2.put("CertifyCode", aLZCardPrintSchema.getCertifyCode());
				String strStartNo = new ExeSQL().getOneValue(sqlbv2);
				strStartNo = CertifyFunc.bigIntegerPlus(strStartNo, "0", nLen);
				String strEndNo = CertifyFunc.bigIntegerPlus(strStartNo, String.valueOf(aLZCardPrintSchema
						.getSumCount() - 1), nLen);

				aLZCardPrintSchema.setStartNo(strStartNo);
				aLZCardPrintSchema.setEndNo(strEndNo);
			} else {// 如果是有号单证
				if (aLZCardPrintSchema.getStartNo().length() != nLen
						|| aLZCardPrintSchema.getEndNo().length() != nLen) {
					buildError("checkNo", "单证号的长度应该为" + String.valueOf(nLen));
					return false;
				}

				if (CertifyFunc
						.bigIntegerDiff(aLZCardPrintSchema.getStartNo(), aLZCardPrintSchema.getEndNo()) > 0) {
					buildError("checkNo", "起始单证号大于终止单证号，请重新录入起止号码！");
					return false;
				}

				logger.debug(CertifyFunc.bigIntegerDiff(aLZCardPrintSchema.getEndNo(),
						aLZCardPrintSchema.getStartNo()) + 1);
				logger.debug(aLZCardPrintSchema.getSumCount());
				if ((CertifyFunc.bigIntegerDiff(aLZCardPrintSchema.getEndNo(), aLZCardPrintSchema
						.getStartNo()) + 1) != aLZCardPrintSchema.getSumCount()) {
					buildError("checkNo", "起止号码之差与印刷总量不相等，请重新录入起止号码！");
					return false;
				}


				String strSql = "select * from lzcardprint " + "where startno <= '"
						+ "?startno?" + "' " + "and endno >= '"
						+ "?endno?" + "' " + "and certifycode = '"
						+ "?certifycode?" + "'";
				logger.debug(strSql);
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(strSql);
				sqlbv3.put("startno", aLZCardPrintSchema.getEndNo());
				sqlbv3.put("endno", aLZCardPrintSchema.getStartNo());
				sqlbv3.put("certifycode", aLZCardPrintSchema.getCertifyCode());
				if (new LZCardPrintDB().executeQuery(sqlbv3).size() > 0) {
					buildError("checkNo", "印刷批次号为" + aLZCardPrintSchema.getPrtNo() + "的单证起止号码与已印刷的单证重复了,请更改!");
					return false;
				}
			}
		} catch (NumberFormatException ex) {
			buildError("checkNo", "不是数值型数据!");
			ex.printStackTrace();
			return false;
		} catch (Exception ex) {
			buildError("checkNo", ex.getMessage());
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery() {
		mResult.clear();
		logger.debug("Start CardPrintBL Submit...");

		LZCardPrintDB dbLZCardPrint = new LZCardPrintDB();

		SQLString sqlObj = new SQLString("LZCardPrint");

		sqlObj.setSQL(5, mLZCardPrintSchema);

		String strSql = sqlObj.getSQL();
		String strSqlAppend = " ManageCom = '" + "?ManageCom?" + "' ";

		if (m_strWhere != null && !m_strWhere.equals("")) {
			strSqlAppend += " AND " + m_strWhere;
		}

		if (strSql.toUpperCase().indexOf("WHERE") != -1) {
			strSql += " AND " + strSqlAppend;
		} else {
			strSql += " WHERE " + strSqlAppend;
		}

		logger.debug(strSql);
		SQLwithBindVariables sqlbva = new SQLwithBindVariables();
		sqlbva.sql(strSql);
		sqlbva.put("ManageCom", mGlobalInput.ComCode);
		mResult.add(dbLZCardPrint.executeQuery(sqlbva));

		if (dbLZCardPrint.mErrors.needDealError()) {
			this.mErrors.copyAllErrors(dbLZCardPrint.mErrors);
			buildError("submitData", "数据提交失败!");
			return false;
		}

		return true;
	}

	private boolean prepareOutputData() {
		try {
			mResult.clear();
			mResult.add(map);
		} catch (Exception ex) {
			buildError("prepareData", "在准备往后层处理所需要的数据时出错!");
			return false;
		}
		return true;
	}

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "CardPrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		mResult.clear();
		return this.mResult;
	}

	public static boolean isValidDate(String sDate) {
		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if ((sDate != null)) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(sDate);
			if (match.matches()) {
				pattern = Pattern.compile(datePattern2);
				match = pattern.matcher(sDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
	}

	public static void main(String[] args) {

		logger.debug(isValidDate("2008-02-30"));

		logger.debug(PubFun.checkDate("2009-03-05", PubFun.getCurrentDate()));

	}
}
