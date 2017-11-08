package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

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
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

public class YBTDetailFailBL {
private static Logger logger = Logger.getLogger(YBTDetailFailBL.class);
	String mStrSql = "";
	SQLwithBindVariables sqlbvstr = new SQLwithBindVariables();

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	// 业务处理相关变量
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();

	public YBTDetailFailBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 传输数据的公共方法
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
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		// mDay = (String[]) cInputData.get(0);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		if(mTransferData.getValueByName("strSql") instanceof SQLwithBindVariables){
			sqlbvstr = (SQLwithBindVariables)mTransferData.getValueByName("strSql");
		}else{
			mStrSql = (String) mTransferData.getValueByName("strSql");
			sqlbvstr.sql(mStrSql);
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
		ExeSQL exeSql = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = exeSql.execSQL(sqlbvstr);

		String[] ContractTitle = new String[7]; // 随意定义的与显示无关
		ContractTitle[0] = "受益人";
		ContractTitle[1] = "证件号";
		ContractTitle[2] = "受益顺序";
		ContractTitle[3] = "受益份额";
		ContractTitle[4] = "受益人";
		ContractTitle[5] = "证件号";
		ContractTitle[6] = "原因";
		ListTable tlistTable = new ListTable();
		tlistTable.setName("RISK");
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("YBTDetailFailBill.vts", "PRINT"); // 最好紧接着就初始化xml文档
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			String[] strArr = new String[7];
			for (int j = 1; j <= tSSRS.MaxCol; j++) {
				strArr[j - 1] = tSSRS.GetText(i, j);
			}
			tlistTable.add(strArr);
		}
		xmlexport.addListTable(tlistTable, ContractTitle);
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		texttag.add("Sum", tSSRS.MaxRow);
		xmlexport.addTextTag(texttag);
		// xmlexport.outputDocumentToFile("e:\\", "atest1"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	public static void main(String[] args) {
		String sql = "select a.transrno,a.appntname,a.trandate,a.bankcode,a.brno, (select b.codename from ldcode b where b.codetype = 'ybttranstype' and b.code = a.funcflag)  from lkbalancedetail a where 1=1  and a.TranDate='2005-08-08'  and a.BankCode='32132'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(sql);
		GlobalInput tGI = new GlobalInput();
		TransferData tTD = new TransferData();
		tTD.setNameAndValue("strSql", sqlbv);
		VData tVD = new VData();
		tVD.add(tGI);
		tVD.add(tTD);
		YBTDetailFailBL bl = new YBTDetailFailBL();
		bl.submitData(tVD, "PRINT");
	}

	private void jbInit() throws Exception {
	}
}
