/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author lh
 * @version 1.0
 */
public class FinChargeDayContF1PBL {
private static Logger logger = Logger.getLogger(FinChargeDayContF1PBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	// 取得的时间
	private String mDay[] = null;
	// 输入的查询sql语句
	private String msql = "";
	private String nsql = "";
	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public FinChargeDayContF1PBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		// 得到外部传入的数据，将数据备份到本类中
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

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		// String mDay[] = new String[2];
		// GlobalInput tG = new GlobalInput();
		// tG.Operator = "fm1";
		// tG.ManageCom = "8611";
		// tG.ComCode = "1";
		// mDay[0] = "2003-4-9";
		// mDay[1] = "2003-4-10";
		// VData tVData = new VData();
		// tVData.addElement(mDay);
		// tVData.addElement(tG);
		// FinChargeDayContF1PBL tFinChargeDayContF1PBL = new
		// FinChargeDayContF1PBL();
		// tFinChargeDayContF1PBL.submitData(tVData, "PRINT");
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		mDay = (String[]) cInputData.get(0);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	/**
	 * 获取返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "LCPolF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	private boolean getPrintData() {
		SSRS tSSRS = new SSRS();
		SSRS nSSRS = new SSRS();
		double SumMoney = 0;
		long SumNumber = 0;
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		msql = "select OtherNo,sum(PayMoney),sum(1) from LJTempFee where MakeDate>='"
				+ "?date1?"
				+ "' and MakeDate<='"
				+ "?date2?"
				+ "' and Operator='"
				+ "?Operator?" + "' Group By OtherNo order by OtherNo";
		// 判断操作员位数是否为8位
		sqlbv1.sql(msql);
		sqlbv1.put("date1", mDay[0]);
		sqlbv1.put("date2",  mDay[1]);
		sqlbv1.put("Operator", mGlobalInput.Operator);
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		ListTable tlistTable = new ListTable();
		String strArr[] = null;
		tlistTable.setName("MODE");
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			strArr = new String[3];
			for (int j = 1; j <= tSSRS.MaxCol; j++) {
				if (j == 1) {
					strArr[j - 1] = tSSRS.GetText(i, j);
				}
				if (j == 2) {
					strArr[j - 1] = tSSRS.GetText(i, j);
					String strSum = new DecimalFormat("0.00").format(Double
							.valueOf(strArr[j - 1]));
					strArr[j - 1] = strSum;
					SumMoney = SumMoney + Double.parseDouble(strArr[j - 1]);
				}
				if (j == 3) {
					strArr[j - 1] = tSSRS.GetText(i, j);
					SumNumber = SumNumber
							+ Long.valueOf(strArr[j - 1]).longValue();
				}
			}
			tlistTable.add(strArr);
		}
		String tSumMoney = String.valueOf(SumMoney);
		tSumMoney = new DecimalFormat("0.00").format(Double.valueOf(tSumMoney));
		strArr = new String[3];
		strArr[0] = "Mode";
		strArr[1] = "Money";
		strArr[2] = "Mult";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		nsql = "select Name from LDCom where ComCode='"
				+ "?ManageCom?" + "'";
		sqlbv2.sql(nsql);
		sqlbv2.put("ManageCom", mGlobalInput.ManageCom);
		ExeSQL nExeSQL = new ExeSQL();
		nSSRS = nExeSQL.execSQL(sqlbv2);
		String manageCom = nSSRS.GetText(1, 1);

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("FinChargeDayCont.vts", "printer"); // 最好紧接着就初始化xml文档
		texttag.add("StartDate", mDay[0]);
		texttag.add("EndDate", mDay[1]);
		texttag.add("ManageCom", manageCom);
		texttag.add("Operator", mGlobalInput.Operator);
		texttag.add("SumMoney", tSumMoney);
		texttag.add("SumNumber", SumNumber);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		xmlexport.addListTable(tlistTable, strArr);
		// xmlexport.outputDocumentToFile("e:\\","test");//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}
}
