package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LPEdorPrint2Schema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: 团体职业类别变更批单人名清单打印类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft Co. Ltd
 * </p>
 * 
 * @Modified By 周磊 on 2007-1-10 for ST 576：产品组合输出
 * @direction: 团体保全职业类别变更(IO)批单和人名清单打印
 */
public class GrpEdorIOPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorIOPrintBL.class);
	// 公共数据
	private VData mResult = new VData();
	public CErrors mErrors = new CErrors();
	// 全局变量
	private String mOperate;
	private VData mInputData = new VData();
	private TextTag mTextTag = new TextTag();
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();
	private XmlExport xmlexport = new XmlExport();
	public static final String VTS_NAME = "GrpEdorNameListIO.vts"; // 模板名称

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		// logger.debug("Start preparing the data to print ====>" +
		// mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("IO数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("IO传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("IO数据打印失败!");
			return false;
		}

		// 打印人名清单数据处理
		if (!bulidBill()) {
			mErrors.addOneError("IO人名清单数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("IO准备数据失败!");
			return false;
		}

		return true;
	}

	private boolean getInputData() {
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		xmlexport = (XmlExport) mInputData
				.getObjectByObjectName("XmlExport", 0);
		if (mLPGrpEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTIO")) {
			return false;
		}
		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemSet.size() < 1) {
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1));

		return true;
	}

	private boolean dealData() {
		xmlexport.addDisplayControl("displayHead");
		xmlexport.addDisplayControl("displayIO");

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "查询原保单信息失败!");
			return false;
		}
		tLCGrpContSchema = tLCGrpContDB.getSchema();
		mTextTag.add("GrpName", tLCGrpContSchema.getGrpName()); // 现单位名称
		mTextTag.add("EdorNo", mLPGrpEdorItemSchema.getEdorNo()); // 团体批单号
		mTextTag.add("GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号

		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		strsql = "select (select riskname from lmrisk where riskcode = a.riskcode),"
				+ " (select nvl(sum(getmoney), 0)"
				+ " from ljsgetendorse"
				+ " where grpcontno = a.grpcontno"
				+ " and riskcode = a.riskcode"
				+ " and grppolno = a.grppolno"
				+ " and endorsementno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' "
				+ " and feeoperationtype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "')"
				+ " from lcgrppol a"
				+ " where grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "' ";

		tssrs = texesql.execSQL(strsql);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "职业类别变更险种信息查询失败");
			return false;
		}

		String[] oContListTitle = new String[2];
		for (int iTitle = 0; iTitle < 2; iTitle++) {
			oContListTitle[iTitle] = "Billo" + String.valueOf(iTitle);
		}

		ListTable oContListTable = new ListTable();
		oContListTable.setName("BILLo");
		String strLine[] = null;

		double summoney = 0.0; // 批单补退费金额合计
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[2];

			strLine[0] = "险种名称：" + tssrs.GetText(i, 1);
			strLine[1] = "补退费金额为 " + BqNameFun.chgMoney(tssrs.GetText(i, 2))
					+ " 元";
			summoney = summoney + Double.parseDouble(tssrs.GetText(i, 2));
			oContListTable.add(strLine);
		}
		xmlexport.addListTable(oContListTable, oContListTitle);

		mTextTag.add("IO.SumMoney", BqNameFun.chgMoney(summoney));

		double money = 0.0; // 批单保费标准
		SSRS tssrs2 = new SSRS();
		ExeSQL texesql2 = new ExeSQL();
		String strsql2 = "";
		strsql2 = "select nvl(sum(prem),0) " + " from lcpol a "
				+ " where grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "' " + " and appflag = '1' " + " and not exists (select 1 "
				+ " from lpedoritem " + " where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' " + " and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "')"
				+ " and contno = a.contno ";

		tssrs2 = texesql2.execSQL(strsql2);

		SSRS tssrs3 = new SSRS();
		ExeSQL texesql3 = new ExeSQL();
		String strsql3 = "";
		strsql3 = " select nvl(sum(prem),0) from lppol " + " where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' " + " and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "'"
				+ " and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "' ";

		tssrs3 = texesql3.execSQL(strsql3);

		money = money + Double.parseDouble(tssrs3.GetText(1, 1))
				+ Double.parseDouble(tssrs2.GetText(1, 1));
		mTextTag.add("IO.Money", BqNameFun.chgMoney(money));

		String[] allArr = BqNameFun
				.getAllNames(tLCGrpContSchema.getAgentCode());
		mTextTag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate());
		mTextTag.add("ApproveOperator", mLPGrpEdorItemSchema
				.getApproveOperator());
		mTextTag.add("Operator", mLPGrpEdorItemSchema.getOperator());
		mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		mTextTag.add("LAAgent.AgentCode", tLCGrpContSchema.getAgentCode());
		xmlexport.addTextTag(mTextTag);
		xmlexport.addDisplayControl("displayTail");

		return true;
	}

	private boolean bulidBill() {
		// 人名清单
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		TextTag texttag = new TextTag();
		Hashtable RiskTable = new Hashtable();

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "查询原保单信息失败!");
			return false;
		}
		tLCGrpContSchema = tLCGrpContDB.getSchema();

		String[] allArr = BqNameFun
				.getAllNames(tLCGrpContSchema.getAgentCode());
		texttag.add("GrpName", tLCGrpContSchema.getGrpName()); // 现单位名称
		texttag.add("EdorNo", mLPGrpEdorItemSchema.getEdorNo()); // 团体批单号
		texttag.add("GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号
		texttag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate()); // 生效日
		texttag.add("Operator", CodeNameQuery.getOperator(mLPGrpEdorItemSchema
				.getOperator())); // 经办人
		texttag.add("ApproveOperator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getApproveOperator())); // 复核人
		texttag.add("AgentName", allArr[BqNameFun.AGENT_NAME]); // 业务员姓名
		texttag.add("AgentCode", tLCGrpContSchema.getAgentCode()); // 业务员编号

		String strsql4 = null;

		// ----------------------------------------------------------------------
		// 判断是否险种组合
		if (checkContPlan(mLPGrpEdorItemSchema.getGrpContNo())) {
			// ----------------------------------------------------------------------
			// 产品组合
			strsql4 = " select distinct CSNo,ISNo,ISName,Duty,YOT,XOT,OTName,Fee,Prem from (select"
					+ " (select CustomerSeqNo from lcinsured where contno = b.contno and insuredno = b.insuredno) CSNo,"
					+ " b.insuredno ISNo,"
					+ " b.insuredname ISName,"
					+ " (select trim(dutycode) || '-' || trim(dutyname) from lmduty where dutycode = c.dutycode) Duty,"
					+ " (select occupationtype from lcpol where polno = a.polno) YOT,"
					+ " a.occupationtype XOT, "
					+ " (select occupationname from ldoccupation where occupationcode = (select occupationcode from lpinsured where edorno = a.edorno and edortype = a.edortype and contno = a.contno and insuredno = a.insuredno)) OTName,"
					+ " (select nvl(sum(getmoney),0) from ljsgetendorse where feeoperationtype = a.edortype and endorsementno = a.edorno and grpcontno = a.grpcontno and a.polno = polno) Fee,"
					+ " a.prem Prem"
					+ "   from LPPol a,LPCont b,LPDuty c"
					+ "  where 1=1 "
					+ " and c.edorno = '"
					+ mLPGrpEdorItemSchema.getEdorNo()
					+ "'"
					+ " and c.edortype = '"
					+ mLPGrpEdorItemSchema.getEdorType()
					+ "'"
					+ " and a.grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo()
					+ "'"
					+ " and a.edorno = b.edorno "
					+ " and a.contno = b.contno "
					+ " and a.contno = c.contno " + " )" + " order by CSNo asc";

		} else {
			// ----------------------------------------------------------------------
			// 非产品组合
			strsql4 = " select distinct CSNo,ISNo,ISName,Risk,YOT,XOT,OTName,Fee,Prem from (select"
					+ " (select CustomerSeqNo from lcinsured where contno = b.contno and insuredno = b.insuredno) CSNo,"
					+ " b.insuredno ISNo,"
					+ " b.insuredname ISName,"
					+ " (select origriskcode || '-' || riskshortname from lmrisk where riskcode = a.riskcode) Risk,"
					+ " (select occupationtype from lcpol where polno = a.polno) YOT,"
					+ " a.occupationtype XOT, "
					+ " (select occupationname from ldoccupation where occupationcode = (select occupationcode from lpinsured where edorno = a.edorno and edortype = a.edortype and contno = a.contno and insuredno = a.insuredno)) OTName,"
					+ " (select nvl(sum(getmoney),0) from ljsgetendorse where feeoperationtype = a.edortype and endorsementno = a.edorno and grpcontno = a.grpcontno and a.polno = polno) Fee,"
					+ " a.prem Prem"
					+ "   from LPPol a,LPCont b"
					+ "  where 1=1 "
					+ " and a.edorno = '"
					+ mLPGrpEdorItemSchema.getEdorNo()
					+ "'"
					+ " and a.edortype = '"
					+ mLPGrpEdorItemSchema.getEdorType()
					+ "'"
					+ " and a.grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo()
					+ "'"
					+ " and a.edorno = b.edorno "
					+ " and a.contno = b.contno "
					+ " )" + " order by CSNo asc";
		}

		SSRS tssrs4 = new SSRS();
		ExeSQL texesq4 = new ExeSQL();
		tssrs4 = texesq4.execSQL(strsql4);
		if (tssrs4 == null || tssrs4.getMaxRow() <= 0) {
			CError.buildErr(this, "职业类别变更清单信息查询失败");
			return false;
		}

		// texttag.add("IObill.No",tssrs4.getMaxRow()); //清单件数

		int VTS_COLUMN = 9;// 模板中的横向字段个数，包括序号列,在此定义,便于维护
		String[] tContListTitle = new String[VTS_COLUMN];
		for (int iTitle = 0; iTitle < VTS_COLUMN; iTitle++) {
			tContListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;

		Vector vct = new Vector();// 用来保存险种名称，以此去哈希表取值。处理小计
		for (int i = 1; i <= tssrs4.MaxRow; i++) {
			strLine = new String[VTS_COLUMN];
			for (int j = 0; j < VTS_COLUMN; j++) {
				if (j >= 7 && j <= 8) {
					strLine[j] = BqNameFun.getRound(tssrs4.GetText(i, j + 1));
				} else {
					strLine[j] = tssrs4.GetText(i, j + 1);
				}
			}
			// 添加到小计处理
			if (!vct.contains(strLine[3])) {
				vct.add(strLine[3]);
			}
			// ------------------------------------------------------------
			if (!BqNameFun.dealHashTable(RiskTable, strLine[3] + "Fee",
					strLine[7])
					|| !BqNameFun.dealHashTable(RiskTable, strLine[3] + "Prem",
							strLine[8])
					|| !BqNameFun.dealHashTable(RiskTable, "SumMoney",
							strLine[7])) {
				return false;
			}
			tContListTable.add(strLine);
		}
		tXmlExport.addListTable(tContListTable, tContListTitle);

		// 处理小计信息
		int VTS_COLUMN_XJ = 3; // 小计中的列数
		String[] xContListTitle = new String[VTS_COLUMN_XJ];
		for (int tTitle = 0; tTitle < VTS_COLUMN_XJ; tTitle++) {
			xContListTitle[tTitle] = "Billrisk" + String.valueOf(tTitle);
		}
		ListTable xContListTable = new ListTable();
		xContListTable.setName("BILLRISK");
		String[] riskLine;

		for (int i = 0; i < vct.size(); i++) {
			riskLine = new String[VTS_COLUMN_XJ];
			riskLine[0] = vct.get(i).toString();
			riskLine[1] = BqNameFun.getRound((String) RiskTable.get(riskLine[0]
					+ "Fee") == null ? "0.00" : (String) RiskTable
					.get(riskLine[0] + "Fee"));
			riskLine[2] = BqNameFun.getRound((String) RiskTable.get(riskLine[0]
					+ "Prem") == null ? "0.00" : (String) RiskTable
					.get(riskLine[0] + "Prem"));

			xContListTable.add(riskLine);
		}
		tXmlExport.addListTable(xContListTable, xContListTitle);

		texttag.add("SumGetMoney", BqNameFun.getRound((String) RiskTable
				.get("SumMoney") == null ? "0.00" : (String) RiskTable
				.get("SumMoney")));
		tXmlExport.addTextTag(texttag);
		// tXmlExport.outputDocumentToFile("D:\\print\\", "GrpIONameList");

		// 垃圾处理
		vct = null;
		RiskTable = null;

		LPEdorPrint2Schema tLPEdorPrint2Main = new LPEdorPrint2Schema();
		tLPEdorPrint2Main.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPEdorPrint2Main.setManageCom(mLPGrpEdorItemSchema.getManageCom());
		tLPEdorPrint2Main.setPrtFlag("N");
		tLPEdorPrint2Main.setPrtTimes(0);
		tLPEdorPrint2Main.setMakeDate(PubFun.getCurrentDate());
		tLPEdorPrint2Main.setMakeTime(PubFun.getCurrentTime());
		tLPEdorPrint2Main.setOperator(mGlobalInput.Operator);
		tLPEdorPrint2Main.setModifyDate(PubFun.getCurrentDate());
		tLPEdorPrint2Main.setModifyTime(PubFun.getCurrentTime());
		InputStream ins = tXmlExport.getInputStream();
		tLPEdorPrint2Main.setEdorInfo(ins);

		MMap map = new MMap();
		map.put("delete from LPEdorPrint2 where EdorNo = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "'", "DELETE");
		map.put(tLPEdorPrint2Main, "BLOBINSERT");
		mResult.add(map);

		return true;
	}

	// ==========================================================================
	/**
	 * 判断是否是组合产品
	 * 
	 * @param String
	 * @return boolean
	 * @throws Exception
	 */
	private boolean checkContPlan(String GrpContNo) {
		// logger.debug("\t@> GrpEdorWSPrintBL.checkContPlan() 开始");

		String QuerySQL = new String("");
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();

		QuerySQL = " Select PlanType " + " From LCContPlan "
				+ " Where GrpContNo='" + GrpContNo + "'";

		tSSRS = tExeSQL.execSQL(QuerySQL);

		if (tSSRS != null && tSSRS.getMaxRow() > 0) {
			if (tSSRS.GetText(1, 1).equals("6")) // 6为组合险种，返回true

				// logger.debug("\t@> GrpEdorWSPrintBL.checkContPlan()
				// 成功");
				return true;
		}
		return false;// 其他情况都不是组合险种，返回false
	}// function checkContPlan end

	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
			return false;
		}
		mResult.add(xmlexport);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	// public static void main(String[] args)
	// {
	// GlobalInput mGlobalInput = new GlobalInput();
	// mGlobalInput.ManageCom = "86";
	// mGlobalInput.Operator = "001";
	// VData tVData = new VData();
	// tVData.add(mGlobalInput);
	//
	// LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
	// tLPGrpEdorItemDB.setEdorAcceptNo("6120070405000018");
	// LPGrpEdorItemSchema tLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	// tLPGrpEdorItemSchema = tLPGrpEdorItemDB.query().get(1);
	// tVData.add(tLPGrpEdorItemSchema);
	//
	// XmlExport tXmlExport = new XmlExport();
	// try
	// {
	// tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化 XML 文档
	// }
	// catch (Exception ex)
	// {
	// logger.debug("\t@> PEdorGCGetNoticeBL.printData() : 设置 FormulaOne VTS
	// 文件异常！");
	// }
	// tVData.add(tXmlExport);
	//
	// GrpEdorIOPrintBL tGrpEdorIOPrintBL = new GrpEdorIOPrintBL();
	// if (tGrpEdorIOPrintBL.submitData(tVData, "PRINTIO"))
	// {
	// logger.debug("OK了哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
	// }
	// }
}
