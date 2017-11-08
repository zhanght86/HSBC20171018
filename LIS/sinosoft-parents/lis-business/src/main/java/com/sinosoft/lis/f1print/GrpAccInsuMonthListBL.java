package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * function :
 * @version 1.0
 * @date 2003-04-04
 */
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GrpAccInsuMonthListBL {
private static Logger logger = Logger.getLogger(GrpAccInsuMonthListBL.class);
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();

	private GlobalInput mGI = new GlobalInput();// 操作员信息
	private TransferData mTransferData = new TransferData();// 传递非SCHEMA信息

	private String mManageCom;// 管理机构
	private String mStartDate;// 开始日期
	private String mEndDate;// 中止日期
	private String mPrintDate;// 打印日期

	private String mCurrentDate = PubFun.getCurrentDate();// 当前日期

	private GlobalInput mGlobalInput = new GlobalInput();

	public GrpAccInsuMonthListBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();

		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		if (mGlobalInput == null) {
			buildError("getInputData", "获取传入的操作员登录信息失败。");
			return false;
		}

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			buildError("getInputData", "获取传入的管理机构及日期信息失败。");
			return false;
		}

		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 管理机构
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 开始日期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 结束日期
		// mPrintDate = (String) mTransferData.getValueByName("PrintDate");
		// //打印日期

		if (mManageCom == null || mManageCom.trim().equals("")) {
			buildError("getInputData", "传入的管理机构信息为空。");
			return false;
		}
		if (mStartDate == null || mStartDate.trim().equals("")) {
			buildError("getInputData", "传入的起始日期信息为空。");
			return false;
		}
		if (mEndDate == null || mEndDate.trim().equals("")) {
			buildError("getInputData", "传入的终止日期信息为空。");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/***************************************************************************
	 * 根据传入的代码获取险种类型
	 */
	private String getRiskType(String RiskType) {
		String strRiskType = "";
		if (RiskType == null) {
			return strRiskType;
		}
		// L--寿险(Life)、A--意外险(Accident)、H--健康险(Health)、P--年金险(Pension)
		if (RiskType.equals("L")) {
			strRiskType = "寿险";
		} else if (RiskType.equals("A")) {
			strRiskType = "意外险";
		} else if (RiskType.equals("H")) {
			strRiskType = "健康险";
		} else if (RiskType.equals("P")) {
			strRiskType = "年金险";
		} else {
			strRiskType = "";
		}
		return strRiskType;
	}

	/***************************************************************************
	 * 根据传入的代码获取业务类型--add by haopan
	 */
	private String getOtherNoType(String OtherNoType) {
		String strOtherNoType = "";
		if (OtherNoType == null) {
			return strOtherNoType;
		}
		if (OtherNoType.equals("6")) {
			strOtherNoType = "新契约个人";
		}
		if (OtherNoType.equals("4")) {
			strOtherNoType = "新契约团体";
		}
		if (OtherNoType.equals("7")) {
			strOtherNoType = "新契约银代";
		}
		if (OtherNoType.equals("2")) {
			strOtherNoType = "续期交费（渠道—个人）";
		}
		if (OtherNoType.equals("3")) {
			strOtherNoType = "续期交费（渠道—银代）";
		}
		if (OtherNoType.equals("10")) {
			strOtherNoType = "保全交费";
		}
		if (OtherNoType.equals("01")) {
			strOtherNoType = "预收续期保费团体";
		}
		if (OtherNoType.equals("02")) {
			strOtherNoType = "预收续期保费个人";
		}
		if (OtherNoType.equals("3")) {
			strOtherNoType = "预收续期保费银代";
		}
		if (OtherNoType.equals("9")) {
			strOtherNoType = "理赔收费";
		}
		if (OtherNoType.equals("5")) {
			strOtherNoType = "客户预存";
		}
		return strOtherNoType;
	}

	private boolean getPrintData() {
		// 根据传入的管理机构查询中心支公司。
		String strComSQL = "select a.comcode,a.name from ldcom a where a.comgrade='02'"
				+ " and a.comcode like '"
				+ mManageCom
				+ "%'"
				+ " order by a.comcode";
		ExeSQL ComExeSQL = new ExeSQL();
		SSRS ComSSRS = new SSRS();
		ComSSRS = ComExeSQL.execSQL(strComSQL);
		int Count_ComSSRS = ComSSRS.getMaxRow();
		if (Count_ComSSRS <= 0) {
			buildError("getInputData", "查询分公司机构代码失败，请检查传入的管理机构是否正确。");
			return false;
		}
		// 循环所有机构，分公司
		for (int iCount = 1; iCount <= Count_ComSSRS; iCount++) {
			XmlExport xmlexport = new XmlExport();
			xmlexport.createDocument("GrpAccInsuMonthList.vts", "printer");
			TextTag aTextTag = new TextTag();
			ListTable aListTable = new ListTable();
			aListTable.setName("RISK");
			String FilialeComCode = ComSSRS.GetText(iCount, 1);// 分公司代码
			String FilialeComName = ComSSRS.GetText(iCount, 2);// 分公司名称
			// 查询每个分公司
			String strAccSQL = "select b.confdate as sAccDate,"
					+ " (select l.risktype from lmriskapp l where l.riskcode=a.riskcode) as sRiskType, a.riskcode as sRiskCode,"
					+ " a.grpcontno as sGrpContNo,(select c.name from lcgrpappnt c where c.grpcontno=a.grpcontno) as sGrpName,"
					+ " a.sumduepaymoney as sDueMoney,a.sumactupaymoney as sActMoney,"
					+ " (select nvl(sum(d.amnt),0) from lcpol d where d.grpcontno=a.grpcontno and d.grppolno=a.grppolno) as sSumAmnt,"
					+ " (select nvl(min(d.amnt),0) from lcpol d where d.grpcontno=a.grpcontno and d.grppolno=a.grppolno) as sMinAmnt,"
					+ " (select nvl(max(d.amnt),0) from lcpol d where d.grpcontno=a.grpcontno and d.grppolno=a.grppolno) as sMaxAmnt,"
					+ " (select Nvl(sum(nvl(d.Insuredpeoples,1)), 0) from lcpol d where d.grpcontno=a.grpcontno and d.grppolno=a.grppolno) as sInsuPeoples,"
					+ " (select codename From ldcode where codetype='grpuwstate' and code in "
					+ " (select p.passflag from lcguwmaster p where p.grppolno=a.grppolno)) as sPassFlag,"
					+ " (select p.uwidea from lcguwmaster p where p.grppolno=a.grppolno) as sUwidea,"
					+ " b.othernotype as sOpType, "
					+ " case UpReportFlag(a.grpcontno) when 'N' then '权限内业务' when 'Y' then '权限外业务' end, " // add
					// by
					// haopan
					+ " (select nvl(lcgrppol.standbyflag2,0) from lcgrppol  where lcgrppol.grppolno=a.grppolno) as sManageFee, "
					+ " (select nvl(lcgrppol.standbyflag3,0) from lcgrppol where lcgrppol.grppolno=a.grppolno) as sSaleFee "
					+ " from ljapaygrp a,ljapay b where a.payno=b.payno "
					+ " and b.confdate between '" + mStartDate + "' and '"
					+ mEndDate + "'" + " and b.managecom like '" + mManageCom
					+ "%'" + " order by sOpType,sAccDate,sRiskType,sRiskCode";
			ExeSQL AccExeSQL = new ExeSQL();
			SSRS AccSSRS = new SSRS();
			AccSSRS = AccExeSQL.execSQL(strAccSQL);
			int IssueCount = AccSSRS.getMaxRow();
			if (IssueCount <= 0) {
				logger.debug("分公司：" + FilialeComCode + "("
						+ FilialeComName + ")下无数据。");
				continue;
			}
			//            　

			// 将每个分公司查询处的数据循环放入模版中
			for (int j = 1; j <= IssueCount; j++) {
				String[] cols = new String[17];
				cols[0] = AccSSRS.GetText(j, 1);// 承保日期
				cols[1] = getRiskType(AccSSRS.GetText(j, 2));// 险种类型
				cols[2] = AccSSRS.GetText(j, 3);// 险种代码
				cols[3] = AccSSRS.GetText(j, 4);// 保单号码
				cols[4] = AccSSRS.GetText(j, 5);// 投保单位名称
				cols[5] = AccSSRS.GetText(j, 6);// 标准保费
				cols[6] = AccSSRS.GetText(j, 7);// 实收保费
				cols[7] = AccSSRS.GetText(j, 8);// 总保额
				cols[8] = AccSSRS.GetText(j, 9);// 最低保额
				cols[9] = AccSSRS.GetText(j, 10);// 最高保额
				cols[10] = AccSSRS.GetText(j, 11);// 投保人数
				cols[11] = AccSSRS.GetText(j, 12);// 核保结论
				cols[12] = AccSSRS.GetText(j, 13);// 核保备注
				cols[13] = getOtherNoType(AccSSRS.GetText(j, 14));// 业务来源
				cols[14] = AccSSRS.GetText(j, 15);// 业务类型
				cols[15] = AccSSRS.GetText(j, 16);// 管理费用率
				cols[16] = AccSSRS.GetText(j, 17);// 销售费用率
				aListTable.add(cols);
			}
			String[] col = new String[14];
			xmlexport.addListTable(aListTable, col);

			aTextTag.add("StartDate", StrTool.getChnDate(mStartDate));
			aTextTag.add("EndDate", StrTool.getChnDate(mEndDate));
			aTextTag.add("FilialeComName", FilialeComName);
			if (aTextTag.size() > 0) {
				xmlexport.addTextTag(aTextTag);
			}
			if (iCount == 1) {
				xmlexport.addDisplayControl("displayTitle");
			}
			mResult.addElement(xmlexport);
		}
		if (mResult.size() <= 0) {
			buildError("getInputData", "没有可以打印的数据");
			return false;
		}
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "GrpAccInsuMonthListBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/*
	 * @function :in order to debug
	 */
	public static void main(String[] args) {
		// GlobalInput tGI = new GlobalInput();
		// tGI.ComCode="86";
		// tGI.ManageCom="86";
		// tGI.Operator="001";
		//
		// //传递非SCHEMA信息
		// TransferData tTransferData = new TransferData();
		// tTransferData.setNameAndValue("StartDate", "2006-07-01");
		// tTransferData.setNameAndValue("EndDate", "2006-07-20");
		// tTransferData.setNameAndValue("ManageCom", "86");
		// tTransferData.setNameAndValue("PrintDate", "2006-07-21");
		//
		// VData tVData = new VData();
		// tVData.addElement(tGI);
		// tVData.addElement(tTransferData);
		//
		// GrpAccInsuMonthListBL tGrpAccInsuMonthListBL= new GrpAccInsuMonthListBL();
		// if(tGrpAccInsuMonthListBL.submitData(tVData,"PRINT"))
		// {
		// VData RResult = new VData();
		// RResult = tGrpAccInsuMonthListBL.getResult();
		// String[] strVFPathName = new String[RResult.size()]; //临时文件个数。
		// for (int k=0;k<RResult.size();k++)
		// {
		// XmlExport txmlExport = new XmlExport();
		// txmlExport = (XmlExport) RResult.getObjectByObjectName("XmlExport", k);
		// CombineVts tcombineVts = null;
		// String strTemplatePath = "D:/lis/ui/f1print/NCLtemplate/";
		// tcombineVts = new CombineVts(txmlExport.getInputStream(), strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// strVFPathName[k] = "D:/lis/ui/vtsfile/001_" + k + "_"
		// +"GrpAccInsuMonthList.vts";
		// AccessVtsFile.saveToFile(dataStream, strVFPathName[k]);
		// }
		// VtsFileCombine vtsfilecombine = new VtsFileCombine();
		// try {
		// BookModelImpl tb = vtsfilecombine.dataCombine(strVFPathName);
		// vtsfilecombine.write(tb, "D:/lis/ui/vtsfile/001_newGrpAccInsuMonthList.vts");
		// }
		// catch (IOException ex) {
		// }
		// catch (F1Exception ex) {
		// }
		// catch (Exception ex) {
		// }
		//
		// }

	}
}
