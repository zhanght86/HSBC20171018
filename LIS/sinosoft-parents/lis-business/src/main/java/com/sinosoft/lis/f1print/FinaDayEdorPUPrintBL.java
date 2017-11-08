package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description:减额缴清日结打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：liurx
 * @version：1.0
 * @CreateDate：2005-12-26
 */
public class FinaDayEdorPUPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(FinaDayEdorPUPrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private String mManageCom = "";
	private String mStartDate = "";
	private String mEndDate = "";
	private String mCurrentDate = PubFun.getCurrentDate(); // 当前时间
	public static final String VTS_NAME = "EdorPUDayFina.vts"; // 模板名称

	public FinaDayEdorPUPrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 准备需要打印的数据
		if (!preparePrintData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mGlobalInput == null || mTransferData == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mStartDate = (String) mTransferData.getValueByName("StartDate");
		if (mStartDate == null || mStartDate.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:统计起期不能为空！"));
			return false;
		}
		mEndDate = (String) mTransferData.getValueByName("EndDate");
		if (mEndDate == null || mEndDate.trim().equals("")) {
			mErrors.addOneError(new CError("没有得到足够的信息:统计止期不能为空！"));
			return false;
		}
		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		if (mManageCom == null || mManageCom.trim().equals("")) {
			mManageCom = mGlobalInput.ManageCom;
		}
		return true;
	}

	private boolean preparePrintData() {
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		TextTag texttag = new TextTag();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		// 按险种小计
		strsql = "select a.edorno,a.polno,(select riskname from lmrisk where riskcode = b.riskcode),(select riskname from lmrisk where riskcode = c.riskcode),"
				+ " ((case when (select prem from lppol b where 1=1 and b.edorno<>a.edorno and exists(select 'X' from lpedoritem where (edorvalidate > a.edorvalidate or (edorvalidate = a.edorvalidate and to_date(concat(to_char(ModifyDate,'YYYY-MM-DD'),ModifyTime),'YYYY-MM-DD hh24:mi:ss')>to_date(concat(to_char(a.ModifyDate,'YYYY-MM-DD'),a.ModifyTime),'YYYY-MM-DD hh24:mi:ss'))) and edorstate = '0' and edorno = b.edorno) and b.polno = a.polno) is not null then (select prem from lppol b where 1=1 and b.edorno<>a.edorno and exists(select 'X' from lpedoritem where (edorvalidate > a.edorvalidate or (edorvalidate = a.edorvalidate and to_date(concat(to_char(ModifyDate,'YYYY-MM-DD'),ModifyTime),'YYYY-MM-DD hh24:mi:ss')>to_date(concat(to_char(a.ModifyDate,'YYYY-MM-DD'),a.ModifyTime),'YYYY-MM-DD hh24:mi:ss'))) and edorstate = '0' and edorno = b.edorno) and b.polno = a.polno)  else c.prem end)) "
				+ " from lcpol c,lppol b,lpedoritem a where 1=1 "
				// Deleted By QianLy on 2007-02-08 for QC 8435
				// Opened by XinYQ on 2007-04-06 : QC 9138, 已经与业务确认,
				// 按 9138 修改! 日! 又要改回去!!
				+ " and exists(select 'X' from lmriskapp where bonusflag = '2' and riskcode = b.riskcode) "
				+ " and c.polno = a.polno and b.edorno = a.edorno and b.edortype = a.edortype and b.polno = a.polno and a.edorstate = '0'  "
				+ " and b.managecom like concat('"
				+ "?mManageCom?"
				+ "','%') "
				+ " and exists(select 'X' from lpedorapp where edoracceptno = a.edoracceptno and confdate between '"
				+ "?mStartDate?"
				+ "' and '"
				+ "?mEndDate?"
				+ "') "
				+ " and a.edortype = 'PU' ";
		sqlbv1.sql(strsql);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		tssrs = texesql.execSQL(sqlbv1);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, mStartDate + "至" + mEndDate + "之间没有做过减额缴清！");
			return false;
		}
		int tCount = tssrs.getMaxRow();
		String[] riskArr = new String[tCount];
		String[] curRiskArr = new String[tCount];
		double[][] moneyArr = new double[tCount][1];
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			riskArr[i - 1] = tssrs.GetText(i, 3); // 缴清前险种
			curRiskArr[i - 1] = tssrs.GetText(i, 4); // 缴清前险种
			moneyArr[i - 1][0] = Double.parseDouble(tssrs.GetText(i, 5));
		}
		String[][] preGroupRiskArr = BqNameFun.getGroupBy(riskArr, moneyArr);
		String[][] curGroupRiskArr = BqNameFun.getGroupBy(curRiskArr, moneyArr);
		if (preGroupRiskArr == null || preGroupRiskArr.length < 1
				|| curGroupRiskArr == null || curGroupRiskArr.length < 1) {
			CError.buildErr(this, "在按险种小计时发生错误！");
			return false;
		}
		String[] tContListTitle = new String[4];
		for (int iTitle = 0; iTitle < 4; iTitle++) {
			tContListTitle[iTitle] = "preRisk" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("Risk");
		String strLine[] = null;
		double riskPrem = 0;
		int riskNum = 0;
		for (int t = 0; t < preGroupRiskArr.length; t++) {
			strLine = new String[4];
			strLine[0] = String.valueOf(t + 1);
			strLine[1] = preGroupRiskArr[t][0];
			strLine[2] = preGroupRiskArr[t][1];
			strLine[3] = preGroupRiskArr[t][2];

			riskPrem += Double.parseDouble(strLine[2]);
			riskNum += Integer.parseInt(strLine[3]);
			tContListTable.add(strLine);
		}

		String tRiskPrem = BqNameFun.getPartRound(riskPrem);
		texttag.add("RiskPrem", tRiskPrem);
		texttag.add("RiskNum", riskNum);

		String[] tTotalListTitle = new String[4];
		for (int tTitle = 0; tTitle < 4; tTitle++) {
			tTotalListTitle[tTitle] = "curRisk" + String.valueOf(tTitle);
		}
		ListTable tTotalListTable = new ListTable();
		tTotalListTable.setName("CurRisk");
		String curLine[] = null;
		double curRiskPrem = 0;
		int curRiskNum = 0;
		for (int t = 0; t < curGroupRiskArr.length; t++) {
			curLine = new String[4];
			curLine[0] = String.valueOf(t + 1);
			curLine[1] = curGroupRiskArr[t][0];
			curLine[2] = curGroupRiskArr[t][1];
			curLine[3] = curGroupRiskArr[t][2];

			curRiskPrem += Double.parseDouble(curLine[2]);
			curRiskNum += Integer.parseInt(curLine[3]);
			tTotalListTable.add(curLine);
		}

		String tCurRiskPrem = BqNameFun.getPartRound(curRiskPrem);
		texttag.add("CurRiskPrem", tCurRiskPrem);
		texttag.add("CurRiskNum", curRiskNum);

		String tManageCom = BqNameFun.getCBranch(mManageCom);
		mStartDate = BqNameFun.getFDate(mStartDate);
		mEndDate = BqNameFun.getFDate(mEndDate);
		mCurrentDate = BqNameFun.getFDate(mCurrentDate);
		// 模版自上而下的元素
		texttag.add("ManageCom", tManageCom);
		texttag.add("StartDate", mStartDate);
		texttag.add("EndDate", mEndDate);
		texttag.add("CurrentDate", mCurrentDate);
		texttag.add("CurrentTime", StrTool.getHour() + "时"
				+ StrTool.getMinute() + "分" + StrTool.getSecond() + "秒");
		texttag.add("Operator", CodeNameQuery
				.getOperator(mGlobalInput.Operator));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		tXmlExport.addListTable(tContListTable, tContListTitle);
		tXmlExport.addListTable(tTotalListTable, tTotalListTitle);
		// tXmlExport.outputDocumentToFile("d:\\", "P001260"); //测试用
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}
}
