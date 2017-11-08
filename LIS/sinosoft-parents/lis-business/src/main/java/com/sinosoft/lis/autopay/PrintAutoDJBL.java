package com.sinosoft.lis.autopay;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
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
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 承保暂交费业务逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author LiuYansong
 * @version 1.0
 */
public class PrintAutoDJBL {
private static Logger logger = Logger.getLogger(PrintAutoDJBL.class);
	public CErrors mErrors = new CErrors();
	private VData mInputData;
	private VData mResult = new VData();
	private String mOperate;

	String mEdorNo = "";
	String Date1 = ""; // 借款年
	String Date2 = ""; // 借款月
	String Date3 = "";// 当前日期
	String Phone = ""; // 电话
	String ComName = ""; // 分公司名称
	int SumCount = 0;// 件数
	double SumMoney = 0.00;
	double MainMoney = 0.00;
	double FuMoney = 0.00;
	String strManageCom = "";
	String AppntName = "";
	String MainPolNo = "";
	String CValiDate = ""; // 生效日期

	private GlobalInput mG = new GlobalInput();

	public PrintAutoDJBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		if (!getInputData(cInputData))
			return false;
		if (cOperate.equals("PRINT")) {
			if (!getPrintData())
				return false;
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean getInputData(VData cInputData) {
		mEdorNo = (String) cInputData.get(0);
		Date1 = (String) cInputData.get(1);
		logger.debug("交费通知书号码是" + mEdorNo);
		logger.debug("借款日期是" + Date1);
		mG.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		return true;
	}

	private boolean getPrintData() {
		ListTable tlistTable = new ListTable();
		tlistTable.setName("MODE");

		TextTag texttag = new TextTag();// 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport();// 新建一个XmlExport的实例
		xmlexport.createDocument("NewAutoDJ1.vts", "printer");

		ListTable alistTable = new ListTable();
		alistTable.setName("INFO");
		// 查询的对应的关系。查询出主险和附加险分别的金额
		String main_sql = "select polno ,sum(sumactupaymoney) ,PayDate from ljapayperson where getnoticeno = '"
				+ "?getnoticeno?" + "' group by polno ,paydate order by polno   ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(main_sql);
		sqlbv1.put("getnoticeno", mEdorNo);
		logger.debug("查询主表的查询语句是" + main_sql);
		ExeSQL main_exesql = new ExeSQL();
		SSRS main_ssrs = main_exesql.execSQL(sqlbv1);
		if (main_ssrs.getMaxRow() == 0) {
			this.buildError("submitData", "没有主险信息！");
		} else {
			// 对主险和附加险查询出明细的信息
			for (int main_count = 1; main_count <= main_ssrs.getMaxRow(); main_count++) {
				SumCount = SumCount + 1;
				SumMoney = SumMoney
						+ Double.parseDouble(main_ssrs.GetText(main_count, 2));
				logger.debug("总金额是" + SumMoney);
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(main_ssrs.GetText(main_count, 1));

				if (!tLCPolDB.getInfo()) {
					this.mErrors.copyAllErrors(tLCPolDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "RegisterBL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据提交失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				if (main_count == 1) {
					AppntName = tLCPolDB.getSchema().getAppntName();
					// Date2 =
					// CaseFunPub.Display_Year(main_ssrs.GetText(main_count,3));//获得交费日期

					CValiDate = tLCPolDB.getSchema().getCValiDate();// 获得生效日期
					// CValiDate = CaseFunPub.Display_Year(CValiDate);

				}
				// 确定险种名称的函数
				logger.debug("Date2");
				LMRiskDB tLMRiskDB = new LMRiskDB();
				tLMRiskDB.setRiskCode(tLCPolDB.getSchema().getRiskCode());
				tLMRiskDB.getInfo();
				String[] cols = new String[2];
				cols[0] = tLMRiskDB.getSchema().getRiskName();// 险种名称
				cols[1] = main_ssrs.GetText(main_count, 2);// 保费
				logger.debug("险种名称是" + cols[0]);
				logger.debug("保额是" + cols[1]);
				alistTable.add(cols);
			}
			MainPolNo = main_ssrs.GetText(1, 1);

			// Date1 = CaseFunPub.Display_Year(Date1);//借款日期，需要前台传入
			Date3 = PubFun.getCurrentDate();

			// 查询公司信息的函数
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setPolNo(MainPolNo);
			tLCPolDB.getInfo();
			LDComDB tLDComDB = new LDComDB();
			tLDComDB.setComCode(tLCPolDB.getSchema().getManageCom());// 管理机构
			tLDComDB.getInfo();
			Phone = tLDComDB.getPhone();
			ComName = tLDComDB.getName();
		}
		logger.debug("开始执行最外部分的循环");
		String[] m_col = new String[2];
		xmlexport.addDisplayControl("displayinfo");
		xmlexport.addListTable(alistTable, m_col);
		texttag.add("AppntName", AppntName);
		texttag.add("MainPolNo", MainPolNo);
		texttag.add("CValiDate", CValiDate);// 生效日期
		texttag.add("Date1", Date1);
		// texttag.add("Date2",Date2);
		// texttag.add("Date3",Date2);
		// texttag.add("Date3",CaseFunPub.Display_Year(PubFun.getCurrentDate()));
		texttag.add("Phone", Phone);
		texttag.add("ComName", ComName);
		texttag.add("SumCount", SumCount);
		texttag.add("SumMoney", SumMoney);

		if (texttag.size() > 0)
			xmlexport.addTextTag(texttag);
		// xmlexport.outputDocumentToFile("e:\\","PrintAutoDJBL");//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	public void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "PayNoticeF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		String mEdorNo = "86110020040310002037"; // 开始日期
		String aDate = "2004-06-11";
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		VData tVData = new VData();
		tVData.addElement(mEdorNo);
		tVData.addElement(aDate);
		tVData.addElement(tG);

		PrintAutoDJBL tPrintAutoDJBL = new PrintAutoDJBL();
		if (tPrintAutoDJBL.submitData(tVData, "PRINT")) {
			logger.debug("执行完毕");
		}
	}
}
