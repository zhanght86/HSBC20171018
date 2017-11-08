package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p/> Title:
 * </p>
 * <p/> <p/> Description:
 * </p>
 * <p/> <p/> Copyright: Copyright (c) 2005
 * </p>
 * <p/> <p/> Company: Sinosoft <S/p>
 * 
 * @author cc
 * @version 1.0
 */
public class IndividuaAcceptBL implements PrintService {
private static Logger logger = Logger.getLogger(IndividuaAcceptBL.class);
	/**
	 * 错误处理类，每个需要错误处理的类中都放置该类
	 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private String mAgentCode = "";

	// 业务处理相关变量
	/**
	 * 全局数据
	 */

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private LCContSchema mLCContSchema = new LCContSchema();

	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();

	public IndividuaAcceptBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
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
		if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
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
		mTransferData = ((TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0));
		if (mTransferData == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		return true;
	}

	/**
	 * 返回处理信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
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
		cError.moduleName = "ReinsureNPrintBL";
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
		// 根据印刷号查询打印队列中的纪录
		String StartDate = ((String) mTransferData.getValueByName("StartDate"));
		String EndDate = ((String) mTransferData.getValueByName("EndDate"));
		String ManageCom = ((String) mTransferData.getValueByName("ManageCom"));
		String Salechnl = ((String) mTransferData.getValueByName("Salechnl"));
		double Total = 0;
		int JianShu = 0;
		ListTable ListTable = new ListTable();
		ListTable.setName("RISK");
		String[] Title = new String[9];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";
		Title[8] = "";
		String FenCom = "";
		String tSql = "";

		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		tSql = "select a.name from ldcom a where comcode ='" + "?ManageCom?" + "'";
		sqlbv1.sql(tSql);
		sqlbv1.put("ManageCom", ManageCom);
		ExeSQL tempExeSQL = new ExeSQL();
		SSRS TwoSSRS = new SSRS();
		TwoSSRS = tempExeSQL.execSQL(sqlbv1);
		FenCom = TwoSSRS.GetText(1, 1);
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		if (Salechnl != null && !Salechnl.equals("4") && !Salechnl.equals("5")) {
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSql = "select riskcode,"
						+ " (select c.tempfeeno"
						+ " from ljtempfee c"
						+ " where c.otherno = a.contno"
						+ " and rownum = 1"
						+ " and c.makedate ="
						+ " (select min(makedate) from ljtempfee where otherno = a.contno)),"
						+ " (select prtno from lcpol where polno = a.polno),"
						+ " contno,"
						+ " (select appntname from lcpol where polno = a.polno),"
						+ " (select insuredname from lcpol where polno = a.polno),"
						+ " sum(sumactupaymoney),"
						+ " agentcode,"
						+ " (select name from laagent where agentcode = a.agentcode),"
						+ " polno" + " from ljapayperson a"
						+ " where paytype = 'ZC'" + " and paycount = 1"
						+ " and exists" + " (select 'x'" + " from lccont"
						+ " where contno = a.contno" + " and signdate between '"
						+ "?StartDate?" + "' and " + " '" + "?EndDate?" + "'"
						+ " and (paymode is null or paymode <> 'A')"
						+ " and salechnl = '" + "?Salechnl?"
						+ "' and conttype='1')"
						// + " and renewcount = 0"
						+ " and a.managecom like concat('" + "?ManageCom?" + "','%')"
						+ " group by polno, riskcode, contno, agentcode"
						+ " order by riskcode";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSql = "select riskcode,"
						+ " (select c.tempfeeno"
						+ " from ljtempfee c"
						+ " where c.otherno = a.contno"
						+ " and c.makedate ="
						+ " (select min(makedate) from ljtempfee where otherno = a.contno) limit 0,1),"
						+ " (select prtno from lcpol where polno = a.polno),"
						+ " contno,"
						+ " (select appntname from lcpol where polno = a.polno),"
						+ " (select insuredname from lcpol where polno = a.polno),"
						+ " sum(sumactupaymoney),"
						+ " agentcode,"
						+ " (select name from laagent where agentcode = a.agentcode),"
						+ " polno" + " from ljapayperson a"
						+ " where paytype = 'ZC'" + " and paycount = 1"
						+ " and exists" + " (select 'x'" + " from lccont"
						+ " where contno = a.contno" + " and signdate between '"
						+ "?StartDate?" + "' and " + " '" + "?EndDate?" + "'"
						+ " and (paymode is null or paymode <> 'A')"
						+ " and salechnl = '" + "?Salechnl?"
						+ "' and conttype='1')"
						// + " and renewcount = 0"
						+ " and a.managecom like concat('" + "?ManageCom?" + "','%')"
						+ " group by polno, riskcode, contno, agentcode"
						+ " order by riskcode";
			}
		} else if (Salechnl != null && Salechnl.equals("5")) {
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSql = "select riskcode,"
						+ " (select c.tempfeeno"
						+ " from ljtempfee c"
						+ " where c.otherno = a.contno"
						+ " and rownum = 1"
						+ " and c.makedate ="
						+ " (select min(makedate) from ljtempfee where otherno = a.contno)),"
						+ " (select prtno from lcpol where polno = a.polno),"
						+ " contno,"
						+ " (select appntname from lcpol where polno = a.polno),"
						+ " (select insuredname from lcpol where polno = a.polno),"
						+ " sum(sumactupaymoney),"
						+ " agentcode,"
						+ " (select name from laagent where agentcode = a.agentcode),"
						+ " polno"
						+ " from ljapayperson a"
						+ " where paytype = 'ZC'"
						+ " and paycount = 1"
						+ " and exists"
						+ " (select 'x'"
						+ " from lccont"
						+ " where contno = a.contno"
						+ " and signdate between '"
						+ "?StartDate?"
						+ "' and "
						+ " '"
						+ "?EndDate?"
						+ "'"
						+ " and (paymode is null or paymode <> 'A')"
						+ " and conttype='1' "
						+ " and exists(select 'x' from lmriskapp m where m.riskcode = a.riskcode and m.riskprop = 'T'))"
						// + " and renewcount = 0"
						+ " and a.managecom like concat('" + "?ManageCom?" + "','%')"
						+ " group by polno, riskcode, contno, agentcode"
						+ " order by riskcode";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSql = "select riskcode,"
						+ " (select c.tempfeeno"
						+ " from ljtempfee c"
						+ " where c.otherno = a.contno"
						+ " and c.makedate ="
						+ " (select min(makedate) from ljtempfee where otherno = a.contno) limit 0,1),"
						+ " (select prtno from lcpol where polno = a.polno),"
						+ " contno,"
						+ " (select appntname from lcpol where polno = a.polno),"
						+ " (select insuredname from lcpol where polno = a.polno),"
						+ " sum(sumactupaymoney),"
						+ " agentcode,"
						+ " (select name from laagent where agentcode = a.agentcode),"
						+ " polno"
						+ " from ljapayperson a"
						+ " where paytype = 'ZC'"
						+ " and paycount = 1"
						+ " and exists"
						+ " (select 'x'"
						+ " from lccont"
						+ " where contno = a.contno"
						+ " and signdate between '"
						+ "?StartDate?"
						+ "' and "
						+ " '"
						+ "?EndDate?"
						+ "'"
						+ " and (paymode is null or paymode <> 'A')"
						+ " and conttype='1' "
						+ " and exists(select 'x' from lmriskapp m where m.riskcode = a.riskcode and m.riskprop = 'T'))"
						// + " and renewcount = 0"
						+ " and a.managecom like concat('" + "?ManageCom?" + "','%')"
						+ " group by polno, riskcode, contno, agentcode"
						+ " order by riskcode";
			}
		} else {
			if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
				tSql = "select riskcode,"
						+ " (select c.tempfeeno"
						+ " from ljtempfee c"
						+ " where c.otherno = a.contno"
						+ " and rownum = 1"
						+ " and c.makedate ="
						+ " (select min(makedate) from ljtempfee where otherno = a.contno)),"
						+ " (select prtno from lcpol where polno = a.polno),"
						+ " contno,"
						+ " (select appntname from lcpol where polno = a.polno),"
						+ " (select insuredname from lcpol where polno = a.polno),"
						+ " sum(sumactupaymoney),"
						+ " agentcode,"
						+ " (select name from laagent where agentcode = a.agentcode),"
						+ " polno" + " from ljapayperson a"
						+ " where paytype = 'ZC'" + " and paycount = 1"
						+ " and exists" + " (select 'x'" + " from lccont"
						+ " where contno = a.contno" + " and signdate between '"
						+ "?StartDate?" + "' and " + " '" + "?EndDate?"
						+ "'"
						+ " and paymode = 'A'"
						+ " and salechnl = '3' and conttype='1')"
						// + " and renewcount = 0"
						+ " and a.managecom like concat('" + "?ManageCom?" + "','%')"
						+ " group by polno, riskcode, contno, agentcode"
						+ " order by riskcode";
			}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
				tSql = "select riskcode,"
						+ " (select c.tempfeeno"
						+ " from ljtempfee c"
						+ " where c.otherno = a.contno"
						+ " and c.makedate ="
						+ " (select min(makedate) from ljtempfee where otherno = a.contno) limit 0,1),"
						+ " (select prtno from lcpol where polno = a.polno),"
						+ " contno,"
						+ " (select appntname from lcpol where polno = a.polno),"
						+ " (select insuredname from lcpol where polno = a.polno),"
						+ " sum(sumactupaymoney),"
						+ " agentcode,"
						+ " (select name from laagent where agentcode = a.agentcode),"
						+ " polno" + " from ljapayperson a"
						+ " where paytype = 'ZC'" + " and paycount = 1"
						+ " and exists" + " (select 'x'" + " from lccont"
						+ " where contno = a.contno" + " and signdate between '"
						+ "?StartDate?" + "' and " + " '" + "?EndDate?"
						+ "'"
						+ " and paymode = 'A'"
						+ " and salechnl = '3' and conttype='1')"
						// + " and renewcount = 0"
						+ " and a.managecom like concat('" + "?ManageCom?" + "','%')"
						+ " group by polno, riskcode, contno, agentcode"
						+ " order by riskcode";
			}
		}
		sqlbv2.sql(tSql);
		sqlbv2.put("ManageCom", ManageCom);
		sqlbv2.put("StartDate", StartDate);
		sqlbv2.put("EndDate", EndDate);
		sqlbv2.put("Salechnl", Salechnl);
		SSRS RiskSSRS = new SSRS();
		RiskSSRS = tempExeSQL.execSQL(sqlbv2);

		double sum = 0;
		int j = 1;
		for (int k = 1; k <= RiskSSRS.getMaxRow(); k++) { // 按险种循环。
			String[] str3 = new String[9];
			if ((k == 1)
					|| (!RiskSSRS.GetText(k, 1).equals(
							RiskSSRS.GetText(k - 1, 1)))) {
				str3[0] = "序号";
				str3[1] = "协议书号";
				str3[2] = "印刷号";
				str3[3] = "保单号";
				str3[4] = "投保人";
				str3[5] = "被保险人";
				str3[6] = "保费";
				str3[7] = "业务员号";
				str3[8] = "业务员";
				ListTable.add(str3);
				sum = 0;
				j = 1;
			} else {
				j++;
			}

			String[] stra = new String[9];
			stra[0] = j + "";
			stra[1] = RiskSSRS.GetText(k, 2);
			stra[2] = RiskSSRS.GetText(k, 3);
			stra[3] = RiskSSRS.GetText(k, 4);
			stra[4] = RiskSSRS.GetText(k, 5);
			stra[5] = RiskSSRS.GetText(k, 6);
			stra[6] = RiskSSRS.GetText(k, 7);
			stra[7] = RiskSSRS.GetText(k, 8);
			stra[8] = RiskSSRS.GetText(k, 9);

			ListTable.add(stra);

			sum += Double.parseDouble(RiskSSRS.GetText(k, 7));

			if (RiskSSRS.getMaxRow() == k
					|| (!RiskSSRS.GetText(k, 1).equals(
							RiskSSRS.GetText(k + 1, 1)))) {
				String[] str4 = new String[9]; // 合计件数
				str4[0] = "险种:";
				str4[1] = RiskSSRS.GetText(k, 1);
				str4[2] = "件数:" + j;
				str4[3] = "保费:" + sum;
				str4[4] = "  ";
				str4[5] = "  ";
				str4[6] = "  ";
				str4[7] = "  ";
				str4[8] = "  ";
				ListTable.add(str4);

				String[] str5 = new String[9]; // 空行
				str5[0] = "  ";
				str5[1] = "  ";
				str5[2] = "  ";
				str5[3] = "  ";
				str5[4] = "  ";
				str5[5] = "  ";
				str5[6] = "  ";
				str5[7] = "  ";
				str5[8] = "  ";
				ListTable.add(str5);

				Total += sum;
				JianShu += j;
			}
		}

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例

		xmlexport.createDocument("UnderWritingListt.vts", "");

		// texttag.add("BarCode1", mLOPRTManagerSchema.getCode());
		// texttag.add("BarCodeParam1",
		// "BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		// texttag.add("BarCode2",mLOPRTManagerSchema.getPrtSeq());
		// texttag.add("BarCodeParam2","BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		if (Salechnl != null && Salechnl.equals("1")) {
			texttag.add("ListTitle", "个人业务承保清单");
		} else if (Salechnl != null && Salechnl.equals("3")) {
			texttag.add("ListTitle", "银行代理业务承保清单");
		} else if (Salechnl != null && Salechnl.equals("4")) {
			texttag.add("ListTitle", "银保通业务承保清单");
		} else if (Salechnl != null && Salechnl.equals("5")) {
			texttag.add("ListTitle", "直销承保清单");
		}

		texttag.add("PrintDate", SysDate);
		texttag.add("ManageCom", FenCom);
		texttag.add("Date", StartDate + "至" + EndDate);
		texttag.add("JianShu", JianShu);
		texttag.add("Sum", Total);

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
			xmlexport.addListTable(ListTable, Title);
		}
		// xmlexport.outputDocumentToFile("d:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		logger.debug("xmlexport=" + xmlexport);
		return true;
	}

	private String getComName(String strComCode) {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			buildError("outputXML", "在取得Ldcom的数据时发生错误");
		}
		return tLDComDB.getName();
	}

	private void jbInit() throws Exception {
	}

}
