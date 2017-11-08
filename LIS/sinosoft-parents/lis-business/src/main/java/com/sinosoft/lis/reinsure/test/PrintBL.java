

package com.sinosoft.lis.reinsure.test;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class PrintBL {
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();

	public boolean submitData(VData cInputData, String cOperater) {
		if (!getInputData(cInputData)) {
			buildError("getInputData", "前台数据获取出错!");
			return false;
		}
		if (!dealData()) {
			buildError("dealData", "业务处理出错!");
			return false;
		}
		return true;
	}

	public boolean getInputData(VData cInputData) {

		return true;
	}

	public boolean dealData() {

		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("test.vts", "printer");
		// 变量
		TextTag texttag = new TextTag();
		// 行插入
		ListTable multTable = new ListTable();
		// 名字
		multTable.setName("MULT");
		// 报表的列数
		String[] title = new String[5];

		try {
			String sql = " select a.batchno,a.proposalno,a.insuredname,a.riskamnt,a.accamnt from ripolrecordbake a";
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sql);
			int count = tSSRS.getMaxRow();
			String temp[][] = tSSRS.getAllData();
			String[] strCol;
			System.out.println("count========" + count);
			for (int i = 0; i < count; i++) {
				strCol = new String[5];
				strCol[0] = temp[i][0];
				strCol[1] = temp[i][1];
				strCol[2] = temp[i][2];
				strCol[3] = temp[i][3];
				strCol[4] = temp[i][4];
				multTable.add(strCol);
			}
			texttag.add("name", "caosg");
			texttag.add("age", "24");
			if (texttag.size() > 0) {
				xmlexport.addTextTag(texttag);
			}
			xmlexport.addListTable(multTable, title);
			mResult.clear();
			mResult.addElement(xmlexport);
		} catch (Exception ex) {
			buildError("dealData", "数据准备出错," + ex.getMessage());
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	public void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "PrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		PrintBL tPrintBL = new PrintBL();
		VData cInputData = new VData();
		tPrintBL.submitData(cInputData, "");
	}
}
