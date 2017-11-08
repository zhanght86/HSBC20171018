package com.sinosoft.lis.f1print;
import java.io.File;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;

/**
 * 对各类报表文件的统一管理,不同类型的报表保存在不同目录,能够根据不同操作员区分. 需要对不同的报表指定不同的类型.
 * 
 * @author not attributable
 * @version 1.0
 */
public class FileManager {
private static Logger logger = Logger.getLogger(FileManager.class);
	public static final String XQINCOMFEEDAILY = "01";
	public static final String DIMISSIONREPORT = "02";
	public static final String AGENTRNCOUNT = "03";
	public static final String AGENTRNCOUNTLIS = "04";
	public static final String CONTCONTINUE = "05";
	public static final String AGENTCONTCONTINUE = "06";
	public static final String RNEWPASSRATEPER = "07";
	public static final String RNEWPASSRATE = "08";
	public static final String PREMSTAT = "09";
	public static final String GRADECOUNT = "10";
	public static final String COMPEOPLESTAT = "11";
	public static final String TOLLSTRUC = "13";
	public static final String TOLLRNDETAILDAYRPT = "14";
	public static final String DIMISSIONREPORTMONTH = "15";
	public static final String LAFYCQUERY = "12";// 直接佣金查询导出文件

	public static final String INSERVICEREPORT = "16";
	public static final String WELFAREDETAILLIST = "17";
	public static final String PBUSI = "18";
	public static final String COMTEMP = "19";
	public static final String AGENTTEMP = "20";
	public static final String LAPEOPLESTAT = "21";
	public static final String LAWageStatBL = "22";
	public static final String AgentQuery = "23";// 人员信息查询
	public static final String LAWAGESTATBPBL = "24";// 工资单报盘文件
	public static final String LATAXDETAILPRINTBL = "25"; // 扣税明细报表
	public static final String THIRDFILETXT = "26"; // 第三方文件TXT
	public static final String ADDAGENT = "27"; //
	public static final String TXTVIPEXPORT = "28"; // VIP客户信息查询结果导出文件
	public static final String LJFINBALANCE = "29"; // 财务结算导出文件

	// 孤儿单导出
	public static final String ORPHANPOLOUT = "81";

	public static String WAGEDETAILLIST = "99"; // 工资清单改为打印方案打印，暂保留

	private static String root = null; // "C:/";

	public FileManager() {

	}

	public static String getFileName(String kind, String operator,
			String strStartDate, String strEndDate) {
		if (root == null) {
			root = getFileRoot();
		}

		String path = root + "txt_file/" + kind;

		File dic = new File(path);
		if (!dic.exists()) {
			dic.mkdirs();
		}
		String nameTail = PubFun.getCurrentDate().toString()
				.replaceAll(":", "")
				+ PubFun.getCurrentTime().toString().replaceAll(":", "");
		String file = path + "/" + operator + "_" + strStartDate + "_"
				+ strEndDate + "_" + nameTail + ".txtpre";

		return file;
	}

	public static String getFilePath(String kind) {
		if (root == null) {
			root = getFileRoot();
		}

		String path = root + "txt_file/" + kind;

		return path;
	}

	private static String getFileRoot() {
		ExeSQL exeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String tSql = "select SysVarValue from ldsysvar where Sysvar='VTSRealPath'";
		sqlbv1.sql(tSql);
		String tRealPath = exeSQL.getOneValue(sqlbv1);
		// 该设置为ui根目录,需调整到vts上
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		tSql = "select SysVarValue from ldsysvar where Sysvar='VTSFilePath'";
		sqlbv2.sql(tSql);
		tRealPath += exeSQL.getOneValue(sqlbv2);
		tRealPath = StrTool.replace(tRealPath, "//", "/");
		exeSQL = null;
		return tRealPath;

	}

	public static void main(String[] args) {
		String file = FileManager.getFileName("01", "001", "2005-01-01",
				"2005-01-25");
		logger.debug(file);
	}
}
