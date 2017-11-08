package com.sinosoft.lis.f1print;
import java.util.Hashtable;

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
 * Description:903退费日结打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：QianLy
 * @version：1.0
 * @CreateDate：2006-12-25
 */
public class FinaDayEdorRtnFeePrintBL903 implements PrintService {
private static Logger logger = Logger.getLogger(FinaDayEdorRtnFeePrintBL903.class);
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
	public static final String VTS_NAME = "EdorRtnFeeDayFina903.vts"; // 模板名称

	public FinaDayEdorRtnFeePrintBL903() {
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

		if (!mOperate.equals("PRINT")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FinaDayEdorRtnFeePrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 准备需要打印的数据
		if (!preparePrintData()) {
			return false;
		}

		return true;
	}

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
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
		Hashtable RiskTable = new Hashtable();// 小计处理
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		// 按险种小计
		strsql = "select (select riskname from lmrisk where riskcode = b.riskcode),"
				+ "       (case when b.salechnl = '3' then '代理' else (case when b.salechnl = '2' then '团体' else '个人' end) end),"
				+ "       substr(b.agentcom, 1, 2),"
				+ "       (case when b.payintv = '0' then '趸交' else trim(cast(b.payyears as char(20))) end),"
				+ "       sum(a.bf),"
				+ "       sum(a.cf),"
				+ "       abs(sum(a.zf)),"
				+ "       count(distinct a.endorsementno)"
				+ "  from (select endorsementno,"
				+ "               polno,"
				+ "          (case when (case when sum(abs(getmoney)) > 50000 then sum(abs(getmoney)) - 50000 else 0.00 end) is not null then (case when sum(abs(getmoney)) > 50000 then sum(abs(getmoney)) - 50000 else 0.00 end)  else 0 end) bf,"
				+ "           (case when (case when sum(abs(getmoney)) > 50000 then 50000 else sum(abs(getmoney)) end) is not null then (case when sum(abs(getmoney)) > 50000 then 50000 else sum(abs(getmoney)) end)  else 0 end) cf,"
				+ "               sum(abs(getmoney)) zf"
				+ "          from ljagetendorse j"
				+ "         where feefinatype = 'TF'"
				+ "           and j.riskcode = '00903000'"
				+ "           and managecom like concat('"
				+ "?mManageCom?"
				+ "','%')"
				+ "           and makedate between to_date('"+ "?mStartDate?" + "','yyyy-mm-dd') and to_date('"+ "?mEndDate?" + "','yyyy-mm-dd')"
				+ "         group by j.endorsementno, j.polno) a,"
				+ "       lcpol b"
				+ " where a.polno = b.polno"
				+ " group by b.riskcode,"
				+ "       (case when b.salechnl = '3' then '代理' else (case when b.salechnl = '2' then '团体' else '个人' end) end),"
				+ "       substr(b.agentcom, 1, 2),"
				+ "       (case when b.payintv = '0' then '趸交' else trim(cast(b.payyears as char(20))) end)"
				+ "";
		sqlbv1.sql(strsql);
		sqlbv1.put("mManageCom", mManageCom);
		sqlbv1.put("mStartDate", mStartDate);
		sqlbv1.put("mEndDate", mEndDate);
		tssrs = texesql.execSQL(sqlbv1);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, mStartDate + "至" + mEndDate + "之间没有903退费！");
			return false;
		}
		int VTS_COLUMN_RISK = 9;// 模板中的列数
		String[] tContListTitle = new String[VTS_COLUMN_RISK];
		for (int iTitle = 0; iTitle < VTS_COLUMN_RISK; iTitle++) {
			tContListTitle[iTitle] = "TR" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("Risk");
		String strLine[] = null;

		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[VTS_COLUMN_RISK];
			strLine[0] = String.valueOf(i);
			strLine[1] = tssrs.GetText(i, 1);
			strLine[2] = tssrs.GetText(i, 2);
			strLine[3] = tssrs.GetText(i, 3);
			strLine[4] = tssrs.GetText(i, 4);
			strLine[5] = BqNameFun.getRoundMoney(tssrs.GetText(i, 5));
			strLine[6] = BqNameFun.getRoundMoney(tssrs.GetText(i, 6));
			strLine[7] = BqNameFun.getRoundMoney(tssrs.GetText(i, 7));
			strLine[8] = tssrs.GetText(i, 8);

			if (!BqNameFun.dealHashTable(RiskTable, "RiskPremUp", strLine[5])
					|| !BqNameFun.dealHashTable(RiskTable, "RiskPremDown",
							strLine[6])
					|| !BqNameFun.dealHashTable(RiskTable, "RiskPrem",
							strLine[7])
					|| !BqNameFun.dealHashTable(RiskTable, "RiskNum",
							strLine[8])) {
				return false;
			}
			tContListTable.add(strLine);
		}

		texttag.add("RiskPremUp", BqNameFun.getRoundMoney((String) RiskTable
				.get("RiskPremUp") == null ? "0.00" : (String) RiskTable
				.get("RiskPremUp")));
		texttag.add("RiskPremDown", BqNameFun.getRoundMoney((String) RiskTable
				.get("RiskPremDown") == null ? "0.00" : (String) RiskTable
				.get("RiskPremDown")));
		texttag.add("RiskPrem", BqNameFun.getRoundMoney((String) RiskTable
				.get("RiskPrem") == null ? "0.00" : (String) RiskTable
				.get("RiskPrem")));
		texttag.add("RiskNum", BqNameFun.formatDoubleToInt((String) RiskTable
				.get("RiskNum") == null ? "0" : (String) RiskTable
				.get("RiskNum")));

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
