package com.sinosoft.lis.f1print;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
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
 * Title: 保全扫描清单打印
 * </p>
 * <p>
 * Description: 保全扫描清单数据提取
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: NCL
 * </p>
 * 
 * @author：Wang Zhen
 * @version 1.0
 */
public class EdorScanBillPrintBL implements BqBill {
private static Logger logger = Logger.getLogger(EdorScanBillPrintBL.class);
	// 公共数据
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();
	private static final String VTS_NAME = "EdorScanBill.vts"; // 模板名称

	// 全局变量
	private String mOperate;
	private VData mInputData = new VData();
	private GlobalInput mGlobalInput;
	private TransferData mTransferData;
	private ListTable mListTable = new ListTable();

	private String mStartDate; // 统计起期
	private String mEndDate; // 统计止期
	private String mManageCom; // 机构
	private String mManageComName; // 机构名称
	private String mSubComName; // 中支/营业部
	private String mScanBatchNo; // 扫描批次号

	private String mSubTypeName; // 单证类型
	private String mDocCode; // 单证号码
	private String mOtherNo; // 保单号/客户号
	private String mMakeDate; // 扫描时间
	private String mCount; // 件数

	public EdorScanBillPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据传输
		if (!getInputData()) {
			CError.buildErr(this, "保全扫描清单数据传输失败!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			CError.buildErr(this, "保全扫描清单数据提取失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			CError.buildErr(this, "保全扫描清单数据生成失败!");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private boolean getInputData() {
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}

		mStartDate = (String) mTransferData.getValueByName("ScanStartDate"); // 统计起期
		mEndDate = (String) mTransferData.getValueByName("ScanEndDate"); // 统计止期
		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 机构
		mScanBatchNo = (String) mTransferData.getValueByName("ScanBatchNo"); // 扫描批次号

		if (mStartDate == null || mEndDate == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}

		if (mScanBatchNo == null)
			mScanBatchNo = "";
		return true;
	}

	private boolean dealData() {
		mListTable.setName("CL");
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		ExeSQL tExeSQL = new ExeSQL();
		// 查询扫描清单批次记录
		// wangzhen modified 2006-5-15
		// 将未做保全操作的扫描单也显示出来
		String tSql = "select b.subtypename, a.doccode, (select c.otherno from lpedorapp c "
				+ "where rpad(a.doccode,lislen('lpedorapp','edoracceptno'),' ') = (c.edoracceptno)),"
				+ " a.makedate, a.scanno"
				+ " from es_doc_main a, es_doc_def b"
				+ " where a.busstype='BQ' and a.busstype=b.busstype"
				+ " and a.subtype=b.subtype"
				+ " and a.makedate between to_date('"
				+ "?mStartDate?"
				+ "', 'YYYY-MM-DD')"
				+ " and to_date('"
				+ "?mEndDate?"
				+ "', 'YYYY-MM-DD')";

		if (!mScanBatchNo.equals(""))
			tSql += " and a.scanno='" + "?mScanBatchNo?" + "'";
		// String tSql = "select b.subtypename, a.doccode, c.otherno,"
		// + " a.makedate, a.scanno"
		// + " from es_doc_main a, es_doc_def b, lpedorapp c"
		// + " where a.busstype='BQ' and a.busstype=b.busstype"
		// + " and a.subtype=b.subtype"
		// //+ " and rpad(a.doccode) = rpad(c.edoracceptno)"
		// + " and rpad(a.doccode,lislen('lpedorapp','edoracceptno'),' ') =
		// (c.edoracceptno) "//modified by liurx 06-02-13
		// + " and a.makedate between to_date('" + mStartDate +
		// "', 'YYYY-MM-DD')"
		// + " and to_date('" + mEndDate + "', 'YYYY-MM-DD')";
		if (mManageCom != null && !mManageCom.trim().equals("")) {
			tSql += " and a.managecom like concat('" + "?mManageCom?" + "','%' )";
		}
		tSql += " order by a.makedate, a.scanno";
		sqlbv.sql(tSql);
		sqlbv.put("mStartDate", mStartDate);
		sqlbv.put("mEndDate", mEndDate);
		sqlbv.put("mScanBatchNo", mScanBatchNo);
		sqlbv.put("mManageCom", mManageCom);
		
		SSRS tSSRS = tExeSQL.execSQL(sqlbv);
		if (tSSRS == null || tSSRS.MaxRow < 1) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "EdorScanBillPrintBL";
			tError.functionName = "dealData";
			tError.errorMessage = "统计期间没有扫描清单信息";
			this.mErrors.addOneError(tError);
			return false;
		}

		mCount = String.valueOf(tSSRS.MaxRow);
		String prevScanNo = ""; // 上一条记录的扫描批次号
		int sum = 0;
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			String currScanNo = tSSRS.GetText(i, 5); // 当前记录的扫描批次号
			if (!prevScanNo.equals(currScanNo)) { // 第一条记录或者换了一个批次号
				if (i != 1) { // 换了批次号，需要打印合计并空出一行
					addSum(sum);
					sum = 0;
				}
				// 每一个新的批次号重新打出标题
				addTitle(currScanNo);
				prevScanNo = currScanNo;

			}
			mSubTypeName = tSSRS.GetText(i, 1); // 单证类型
			mDocCode = tSSRS.GetText(i, 2); // 单证号码
			mOtherNo = tSSRS.GetText(i, 3); // 保单号/客户号
			mMakeDate = tSSRS.GetText(i, 4); // 扫描时间

			String[] allArr = new String[6];
			allArr[0] = String.valueOf(++sum); // 序号
			allArr[1] = mOtherNo; // 保单号/客户号
			allArr[2] = mSubTypeName; // 单证类型
			allArr[3] = mDocCode; // 单证号码
			allArr[4] = "scan type"; // 扫描方式
			allArr[5] = mMakeDate; // 扫描日期

			mListTable.add(allArr);
		}
		addSum(sum);

		return true;
	}

	private boolean prepareData() {
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		// 模版自上而下的元素
		if (mManageCom == null || "".equals(mManageCom)) {
			mManageComName = getComName(mGlobalInput.ManageCom);
			mSubComName = getSubComName(mGlobalInput.ManageCom);
		} else {
			mManageComName = getComName(mManageCom);
			mSubComName = getSubComName(mManageCom);
		}
		texttag.add("Company", mManageComName); // 制表单位
		texttag.add("SubCompany", mSubComName);

		GregorianCalendar tCalendar = new GregorianCalendar();
		int tYear = tCalendar.get(Calendar.YEAR);
		int tMonth = tCalendar.get(Calendar.MONTH) + 1;
		int tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
		String today = tYear + "年" + tMonth + "月" + tDay + "日";
		texttag.add("PrintDate", today); // 制表日期

		FDate fDate = new FDate();
		tCalendar = new GregorianCalendar();
		tCalendar.setTime(fDate.getDate(mStartDate));
		tYear = tCalendar.get(Calendar.YEAR);
		tMonth = tCalendar.get(Calendar.MONTH) + 1;
		tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
		mStartDate = tYear + "年" + tMonth + "月" + tDay + "日";
		texttag.add("StartDate", mStartDate); // 统计起期

		tCalendar.setTime(fDate.getDate(mEndDate));
		tYear = tCalendar.get(Calendar.YEAR);
		tMonth = tCalendar.get(Calendar.MONTH) + 1;
		tDay = tCalendar.get(Calendar.DAY_OF_MONTH);
		mEndDate = tYear + "年" + tMonth + "月" + tDay + "日";
		texttag.add("EndDate", mEndDate); // 统计止期

		texttag.add("Count", mCount); // 件数
		if (texttag.size() > 0) {
			logger.debug(texttag.size());
			tXmlExport.addTextTag(texttag);
		}

		String[] strEN = new String[6];
		tXmlExport.addListTable(mListTable, strEN);

		mResult.clear();
		mResult.addElement(tXmlExport);
		return true;
	}

	/**
	 * 为表格添加一个标题
	 * 
	 * @param scanNo
	 *            int 扫描批次号
	 */
	private void addTitle(String scanNo) {
		String[] arr1 = new String[6];
		arr1[0] = "          扫描批次号：" + scanNo;
		arr1[1] = "";
		arr1[2] = "";
		arr1[3] = "";
		arr1[4] = "";
		arr1[5] = "";
		mListTable.add(arr1);

		String[] arr2 = new String[6];
		arr2[0] = "序号";
		arr2[1] = "保单号/客户号";
		arr2[2] = "单证类型";
		arr2[3] = "单证号码";
		arr2[4] = "扫描方式";
		arr2[5] = "扫描日期";
		mListTable.add(arr2);

	}

	/**
	 * 为表格添加一个总计项
	 * 
	 * @param sum
	 *            int 总计
	 */
	private void addSum(int sum) {
		// 打印此批单号的总计件数
		String[] sumarr = new String[6];
		sumarr[0] = "合计件数：" + sum + "件";
		sumarr[1] = "";
		sumarr[2] = "";
		sumarr[3] = "";
		sumarr[4] = "";
		sumarr[5] = "";
		mListTable.add(sumarr);

		// 打印一个空行
		String[] blank = new String[6];
		blank[0] = "";
		blank[1] = "";
		blank[2] = "";
		blank[3] = "";
		blank[4] = "";
		blank[5] = "";
		mListTable.add(blank);
	}

	/**
	 * 得到通过机构代码得到机构名称
	 * 
	 * @param strComCode
	 * @return
	 * @throws Exception
	 */
	private String getComName(String strComCode) {
		LDCodeDB tLDCodeDB = new LDCodeDB();

		tLDCodeDB.setCode(strComCode);
		tLDCodeDB.setCodeType("station");

		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			return "";
		}
		return tLDCodeDB.getCodeName();
	}

	private String getSubComName(String strComCode) {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);

		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			return "";
		}
		return tLDComDB.getName();
	}

	public static void main(String[] args) {
		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";

		TransferData tr = new TransferData();
		tr.setNameAndValue("StartDate", "2005-12-07");
		tr.setNameAndValue("EndDate", "2005-12-07");
		tr.setNameAndValue("ManageCom", "86");

		tVData.add(tGlobalInput);
		tVData.add(tr);

		EdorScanBillPrintBL t = new EdorScanBillPrintBL();
		t.submitData(tVData, "PRINT");
	}
}
