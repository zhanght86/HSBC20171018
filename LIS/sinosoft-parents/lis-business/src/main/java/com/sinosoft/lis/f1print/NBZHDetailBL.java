package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

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
 * Company: SinoSoft
 * </p>
 * 
 * @author Gaoht
 * @version 1.0
 */
public class NBZHDetailBL implements PrintService {
private static Logger logger = Logger.getLogger(NBZHDetailBL.class);
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	private SSRS RiskSSRS = new SSRS();
	private TransferData mTransferData = new TransferData();
	private GlobalInput mGlobalInput = new GlobalInput();
	private String mStartDate = "";
	private String mEndDate = "";
	private String mManageCom = "";
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private DecimalFormat mDf = new DecimalFormat("0.00");

	public NBZHDetailBL() {
	}

	public static void main(String[] args) {
		NBZHDetailBL nbzhdetailbl = new NBZHDetailBL();
		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("StartDate", "20061215");
		tTransferData.setNameAndValue("EndDate", "20061216");
		tTransferData.setNameAndValue("ManageCom", "862100");
		VData tVData = new VData();
		GlobalInput tG = new GlobalInput();
		;
		tG.ManageCom = "86";
		tVData.addElement(tTransferData);
		tVData.addElement(tG);
		nbzhdetailbl.submitData(tVData, "");
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mGlobalInput == null) {
			CError.buildErr(this, "页面失效请重新登陆");
			return false;
		}
		mTransferData = ((TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0));
		if (mTransferData == null) {
			CError.buildErr(this, "传入参数为空");
			return false;
		}
		mStartDate = ((String) mTransferData.getValueByName("StartDate"));
		if (mStartDate.equals("") || mStartDate == null) {
			CError.buildErr(this, "传入统计起期为空");
			return false;
		}
		mEndDate = ((String) mTransferData.getValueByName("EndDate"));
		if (mEndDate.equals("") || mEndDate == null) {
			CError.buildErr(this, "传入统计止期为空");
			return false;
		}
		mManageCom = ((String) mTransferData.getValueByName("ManageCom"));
		if (mManageCom.equals("") || mManageCom == null) {
			CError.buildErr(this, "传入管理机构为空");
			return false;
		}
		return true;
	}

	private boolean getPrintData() {
		/*---------------------报表头、报表尾信息-----------------------------*/

		double tTotalMoney = 0;
		int tTotalInit = 0;
		String tSql = "select a.name from ldcom a where comcode ='"
				+ "?mManageCom?" + "'";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("mManageCom", mManageCom);
		ExeSQL tempExeSQL = new ExeSQL();
		SSRS TwoSSRS = new SSRS();
		TwoSSRS = tempExeSQL.execSQL(sqlbv1);
		String tPolicyCom = TwoSSRS.GetText(1, 1);
		tSql = "select a.name from ldcom a where comcode ='"
				+ "?mGlobalInput?" + "'";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("mGlobalInput", mGlobalInput.ManageCom);
		tempExeSQL = new ExeSQL();
		TwoSSRS = new SSRS();
		TwoSSRS = tempExeSQL.execSQL(sqlbv2);
		String tManageCom = TwoSSRS.GetText(1, 1);

		/*--------------------END 报表头、报表尾信息--------------------------------*/

		/*--------------------内容列表----------------------*/

		ListTable ListTable = new ListTable();
		ListTable.setName("RISK");

		String[] Title = new String[8];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";
		tSql = "select ( select contplancode from ldplan where ldplan.contplancode in ( select d.contplancode from lcinsured d where contno=a.contno ) ) a ,"
				+ " ( select contplanname from ldplan where ldplan.contplancode in ( select d.contplancode from lcinsured d where contno=a.contno ) ) b ,"
				+ " (select appntname from lccont where contno=a.contno),"
				+ " case (select count(1) from lcgrpcont where grpcontno = a.grpcontno) when 0 then contno when 1 then grpcontno else contno end ,"
				+ " agentcode,"
				+ "sum( a.sumduepaymoney ) e ,"
				+ " (select codename from ldcode where trim(code) in (select trim(laagent.branchtype2) from laagent where agentcode=trim(a.agentcode)"
				+ " ) and codetype='branchtype2') c "
				+ " from ljapayperson a where 1=1"
				+ " and makedate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "'"
				+ " and managecom like concat('"
				+ "?mManageCom?"
				+ "','%')"
				+ " and paytype='ZC'"
				+ " and paycount='1'"
				+ " and exists ( select 1 from lmrisksort s where s.riskcode=a.riskcode and s.risksorttype='22' )"
				+ " group by grpcontno,contno , agentcode , payintv"
				+ " order by a,b,c"; // 此oder by 条件不能去掉
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(tSql);
		sqlbv3.put("mStartDate", mStartDate);
		sqlbv3.put("mEndDate", mEndDate);
		sqlbv3.put("mManageCom", mManageCom);
		RiskSSRS = tempExeSQL.execSQL(sqlbv3);
		double sum = 0;
		int j = 1;
		String tTempString = "";
		for (int i = 1; i <= RiskSSRS.getMaxRow(); i++) {
			if (RiskSSRS.GetText(i, 1) == null
					|| RiskSSRS.GetText(i, 1).equals("")) {
				continue;
			}

			tTotalMoney += Double.parseDouble(RiskSSRS.GetText(i, 6));

			logger.debug("==========" + i + "=========组合名称=========" + i
					+ "=========" + RiskSSRS.GetText(i, 2));

			if (i == 1
					|| (!RiskSSRS.GetText(i, 1).equals(
							RiskSSRS.GetText(i - 1, 1)))) {
				String[] str2 = new String[8]; // 空行
				str2[0] = "   ";
				str2[1] = "   ";
				str2[2] = "   ";
				str2[3] = "   ";
				str2[4] = "   ";
				str2[5] = "   ";
				str2[6] = "   ";
				str2[7] = "   ";
				ListTable.add(str2);

				// 同一组合求和
				sum = SumCombination(1, RiskSSRS.GetText(i, 1), 6);

				String[] str4 = new String[8]; // 合计件数
				str4[0] = "※ 产品组合代码:";
				str4[1] = RiskSSRS.GetText(i, 1);
				str4[2] = "产品组合名称:";
				str4[3] = RiskSSRS.GetText(i, 2);
				str4[4] = "合计:";
				str4[5] = mDf.format(sum) + "※";
				str4[6] = "";
				str4[7] = " ";
				ListTable.add(str4);

				String[] str5 = new String[8]; // 空行
				str5[0] = "   ";
				str5[1] = "   ";
				str5[2] = "   ";
				str5[3] = "   ";
				str5[4] = "   ";
				str5[5] = "   ";
				str5[6] = "   ";
				str5[7] = "   ";
				ListTable.add(str5);

				String[] strTitle = new String[8]; // 子表表头

				strTitle[0] = "序号";
				strTitle[1] = "组合代码";
				strTitle[2] = "投保单位/投保人";
				strTitle[3] = "保单号码";
				strTitle[4] = "业务员代码";
				strTitle[5] = "承保保费";
				strTitle[6] = "销售方式";
				ListTable.add(strTitle);
				j = 1;
			}

			sum = 0;
			if (RiskSSRS.GetText(i, 4).equals(tTempString)) {
				continue;
			} else {
				String[] stra = new String[8];
				stra[0] = String.valueOf(j++);
				// 同一合同求和
				sum = SumCombination(4, RiskSSRS.GetText(i, 4), 6);
				stra[1] = RiskSSRS.GetText(i, 1);
				stra[2] = RiskSSRS.GetText(i, 3);
				stra[3] = RiskSSRS.GetText(i, 4);
				stra[4] = RiskSSRS.GetText(i, 5);
				stra[5] = mDf.format(sum);
				stra[6] = RiskSSRS.GetText(i, 7);
				ListTable.add(stra);
				tTotalInit++;
			}
			tTempString = RiskSSRS.GetText(i, 4);
		}

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("NBZHDetail.vts", "");

		texttag.add("PrintDate", CurrentDate);
		texttag.add("PrintTime", CurrentTime);
		texttag.add("ManageCom", mGlobalInput.ComCode);
		texttag.add("PilicyCom", mManageCom);
		texttag.add("Date", mStartDate + "至" + mEndDate);
		texttag.add("JianShu", String.valueOf(tTotalInit));
		texttag.add("Sum", mDf.format(tTotalMoney));

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
			xmlexport.addListTable(ListTable, Title);
		}
		mResult.clear();
		mResult.addElement(xmlexport);
		logger.debug("xmlexport=" + xmlexport);
		return true;
	}

	private double SumCombination(int cCol, String cColValue, int cSumCol) {
		double tSum = 0;
		for (int i = 1; i <= RiskSSRS.getMaxRow(); i++) {
			if (RiskSSRS.GetText(i, cCol).equals(cColValue)) {
				tSum = tSum + Double.parseDouble(RiskSSRS.GetText(i, cSumCol));
			} else {
				continue;
			}
		}
		return tSum;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
