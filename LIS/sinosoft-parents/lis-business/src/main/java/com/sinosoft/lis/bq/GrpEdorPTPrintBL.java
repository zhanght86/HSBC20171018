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
 * Title: 团体减保批单人名清单打印类
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft Co. Ltd
 * </p>
 * 
 * @author QianLy
 * @version 6.0
 * @Rewrited By QianLy on 2006-10-13/14/20
 */
public class GrpEdorPTPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorPTPrintBL.class);

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
	private String Amnt;
	private EdorCalZT mEdorCalZT;
	private double mPercent;
	public static final String VTS_NAME = "GrpEdorNameListPT.vts"; // 模板名称
	// public static final String FormulaOneVTS = "PrtGrpEndorsement.vts";//Add
	// By QianLy For Test on 2006-9-27

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("PT数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("PT传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("PT数据打印失败!");
			return false;
		}

		// 打印人名清单数据处理
		if (!bulidBill()) {
			mErrors.addOneError("PT人名清单数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("PT准备数据失败!");
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
		if (!mOperate.equals("PRINTPT")) {
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
		xmlexport.addDisplayControl("displayPT");

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "原保单信息查询失败!");
			return false;
		}
		tLCGrpContSchema = tLCGrpContDB.getSchema();
		mTextTag.add("GrpName", tLCGrpContSchema.getGrpName()); // 现单位名称
		mTextTag.add("EdorNo", mLPGrpEdorItemSchema.getEdorNo()); // 团体批单号
		mTextTag.add("GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号

		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		strsql = "select sum(getmoney)" + " from ljsgetendorse"
				+ " where EndorsementNo = '" + mLPGrpEdorItemSchema.getEdorNo()
				+ "' " + " and FeeOperationType = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' "
				+ " and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "' ";

		tssrs = texesql.execSQL(strsql);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "查询失败");
			return false;
		}
		String mGetMoney = tssrs.GetText(1, 1);
		if (!mGetMoney.equals("")) {
			if (Double.parseDouble(mGetMoney) < 0) {
				mGetMoney = BqNameFun.getRound(mGetMoney.substring(1, mGetMoney
						.length()));
			}
		}
		mTextTag.add("PT.SumMoney", mGetMoney); // 总减保金额

		String ssql = "select count(*)" + " from lpedoritem"
				+ " where edorno = '" + mLPGrpEdorItemSchema.getEdorNo() + "' "
				+ " and edortype = '" + mLPGrpEdorItemSchema.getEdorType()
				+ "' " + " and grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "' ";
		if (texesql.getOneValue(ssql) != null) {
			mTextTag.add("PT.Count", texesql.getOneValue(ssql));// 总减保人数
		} else {
			CError.buildErr(this, "查询减保人数失败");
			return false;
		}

		String[] allArr = BqNameFun
				.getAllNames(tLCGrpContSchema.getAgentCode());
		mTextTag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate());
		mTextTag.add("ApproveOperator", mLPGrpEdorItemSchema
				.getApproveOperator());
		mTextTag.add("Operator", mLPGrpEdorItemSchema.getOperator());
		mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		mTextTag.add("LAAgent.AgentCode", tLCGrpContSchema.getAgentCode());
		xmlexport.addDisplayControl("displayTail");
		xmlexport.addTextTag(mTextTag);
		// xmlexport.outputDocumentToFile("D:\\qly\\XMLDoc\\", "QlyGrpZT");//Add
		// By QianLy for test on 2006-9-27
		return true;
	}

	private boolean bulidBill() {

		XmlExport aXmlExport = new XmlExport();
		aXmlExport.createDocument(VTS_NAME, "printer");

		TextTag aTextTag = new TextTag();
		SSRS SS = new SSRS();
		ExeSQL tES = new ExeSQL();
		Hashtable RiskTable = new Hashtable();// 处理小计用
		Vector vct = new Vector();// 用来保存险种名称，以此去哈希表取值。处理小计

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (tLCGrpContDB.query() == null || tLCGrpContDB.query().size() < 1) {
			CError.buildErr(this, "原保单信息查询失败!");
			return false;
		}
		LCGrpContSchema aLCGrpContSchema = new LCGrpContSchema();
		aLCGrpContSchema = tLCGrpContDB.query().get(1);

		String[] allArr = BqNameFun
				.getAllNames(aLCGrpContSchema.getAgentCode());
		aTextTag.add("GrpName", aLCGrpContSchema.getGrpName());
		aTextTag.add("EdorNo", mLPGrpEdorItemSchema.getEdorNo());
		aTextTag.add("GrpContNo", mLPGrpEdorItemSchema.getGrpContNo());
		aTextTag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate());
		aTextTag.add("Operator", CodeNameQuery.getOperator(mLPGrpEdorItemSchema
				.getOperator()));
		aTextTag.add("ApproveOperator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getOperator()));
		aTextTag.add("AgentName", allArr[BqNameFun.AGENT_NAME]);
		aTextTag.add("AgentCode", aLCGrpContSchema.getAgentCode());

		int VTS_COLUMN_RISK = 15;// 模板中的列数
		String[] tContListTitle = new String[VTS_COLUMN_RISK];
		for (int iTitle = 0; iTitle < VTS_COLUMN_RISK; iTitle++) {
			tContListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;

		String isMulProd = BqNameFun.isMulProd(aLCGrpContSchema.getGrpContNo());
		if (isMulProd.toUpperCase().equals("FAILED")) {
			CError.buildErr(this, "查询团单是否是险种组合失败!");
			return false;
		}
		String Sql = "";
		if (isMulProd.toUpperCase().equals("FALSE")) {
			Sql = "select c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15"
					+ "  from (select (select CustomerSeqNo"
					+ "                  from lcinsured"
					+ "                 where contno = b.contno"
					+ "                   and insuredno = b.insuredno) c1,"
					+ "               b.insuredno c2,"
					+ "               b.insuredname c3,"
					+ "               (select origriskcode || riskshortname from lmrisk where riskcode = a.riskcode) c4,"
					+ "               (case when exists(select 'x' from LCDuty d where ContNo=a.contno and PolNo=a.polno"
					+ "                                   and exists(select 'y' from LMDuty where DutyCode=d.DutyCode and AmntFlag='1'))"
					+ "                       then (c.Amnt - a.Amnt)"
					+ "                       else (c.Mult - a.Mult)"
					+ "                  end) c5,"
					+ "               (select nvl(sum(getmoney), 0)"
					+ "                  from ljsgetendorse"
					+ "                 where endorsementno = a.edorno"
					+ "                   and feeoperationtype = a.edortype"
					+ "                   and polno = a.polno"
					+ "                   and subfeeoperationtype like '%"
					+ BqCode.Get_BaseCashValue
					+ "%') c6,"
					+ "               (select nvl(sum(getmoney), 0)"
					+ "                  from ljsgetendorse"
					+ "                 where endorsementno = a.edorno"
					+ "                   and feeoperationtype = a.edortype"
					+ "                   and polno = a.polno"
					+ "                   and (subfeeoperationtype like '%"
					+ BqCode.Get_AddPremHealth
					+ "%' or"
					+ "                       subfeeoperationtype like '%"
					+ BqCode.Get_AddPremOccupation
					+ "%')) c7,"
					+ "              (case when exists(select 'x' from LCDuty d where ContNo=a.contno and PolNo=a.polno"
					+ "                                   and exists(select 'y' from LMDuty where DutyCode=d.DutyCode and AmntFlag='1'))"
					+ "                       then a.Amnt"
					+ "                       else a.Mult"
					+ "                  end) c8,"
					+ "               a.prem c9,"
					+ "               nvl((select ob.AvailableAmnt - pb.AvailableAmnt from loengbonuspol ob,lpengbonuspol pb where ob.polno = pb.polno and ob.FiscalYear = pb.FiscalYear and ob.FiscalYear = (select max(FiscalYear) from loengbonuspol where polno = ob.polno) and ob.polno =a.polno),0) c10,"
					+ "               (select nvl(sum(getmoney), 0)"
					+ "                  from ljsgetendorse"
					+ "                 where endorsementno = a.edorno"
					+ "                   and feeoperationtype = a.edortype"
					+ "                   and polno = a.polno"
					+ "                   and subfeeoperationtype like '%"
					+ BqCode.Get_BonusCashValue
					+ "%') c11,"
					+ "               nvl((select pb.AvailableAmnt from lpengbonuspol pb where pb.FiscalYear = (select max(FiscalYear) from loengbonuspol where polno = pb.polno) and pb.polno =a.polno),0) c12,"
					+ "               (select nvl(sum(getmoney), 0)"
					+ "                  from ljsgetendorse"
					+ "                 where endorsementno = a.edorno"
					+ "                   and feeoperationtype = a.edortype"
					+ "                   and polno = a.polno"
					+ "                   and subfeeoperationtype like '%"
					+ BqCode.Get_FinaBonus
					+ "%') c13,"
					+ "               (select nvl(sum(getmoney), 0)"
					+ "                  from ljsgetendorse"
					+ "                 where endorsementno = a.edorno"
					+ "                   and feeoperationtype = a.edortype"
					+ "                   and polno = a.polno) c14,"
					+ "               b.managecom c15"
					+ "          from lppol a, lpcont b,lcpol c"
					+ "         where 1 = 1"
					+ "           and b.edorno = '"
					+ mLPGrpEdorItemSchema.getEdorNo()
					+ "'"
					+ "           and b.edortype = '"
					+ mLPGrpEdorItemSchema.getEdorType()
					+ "'"
					+ "           and b.grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo()
					+ "'"
					+ "           and a.edorno = b.edorno"
					+ "           and a.contno = b.contno"
					+ "           and a.polno = c.polno"
					+ "         order by c1 asc)" + "";
		} else if (isMulProd.toUpperCase().equals("TRUE")) {
			Sql = "select c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15"
					+ "  from (select (select CustomerSeqNo"
					+ "                  from lcinsured"
					+ "                 where contno = b.contno"
					+ "                   and insuredno = b.insuredno) c1,"
					+ "               b.insuredno c2,"
					+ "               b.insuredname c3,"
					+ "               (select dutyname from lmduty where dutycode =y.dutycode) c4,"
					+ "               (case when exists(select 'x' from LCDuty d where ContNo=a.contno and PolNo=a.polno"
					+ "                                   and exists(select 'y' from LMDuty where DutyCode=d.DutyCode and AmntFlag='1'))"
					+ "                       then (c.Amnt - a.Amnt)"
					+ "                       else (c.Mult - a.Mult)"
					+ "                  end) c5,"
					+ "               (select nvl(sum(getmoney), 0)"
					+ "                  from ljsgetendorse"
					+ "                 where endorsementno = a.edorno"
					+ "                   and feeoperationtype = a.edortype"
					+ "                   and polno = a.polno"
					+ "                   and dutycode = y.dutycode"
					+ "                   and subfeeoperationtype like '%"
					+ BqCode.Get_BaseCashValue
					+ "%') c6,"
					+ "               (select nvl(sum(getmoney), 0)"
					+ "                  from ljsgetendorse"
					+ "                 where endorsementno = a.edorno"
					+ "                   and feeoperationtype = a.edortype"
					+ "                   and polno = a.polno"
					+ "                   and dutycode = y.dutycode"
					+ "                   and (subfeeoperationtype like '%"
					+ BqCode.Get_AddPremHealth
					+ "%' or"
					+ "                       subfeeoperationtype like '%"
					+ BqCode.Get_AddPremOccupation
					+ "%')) c7,"
					+ "              (case when exists(select 'x' from LCDuty d where ContNo=a.contno and PolNo=a.polno"
					+ "                                   and exists(select 'y' from LMDuty where DutyCode=d.DutyCode and AmntFlag='1'))"
					+ "                       then a.Amnt"
					+ "                       else a.Mult"
					+ "                  end) c8,"
					+ "               a.prem c9,"
					+ "               nvl((select ob.AvailableAmnt - pb.AvailableAmnt from loengbonuspol ob,lpengbonuspol pb where ob.polno = pb.polno and ob.FiscalYear = pb.FiscalYear and ob.FiscalYear = (select max(FiscalYear) from loengbonuspol where polno = ob.polno) and ob.polno =a.polno),0) c10,"
					+ "               (select nvl(sum(getmoney), 0)"
					+ "                  from ljsgetendorse"
					+ "                 where endorsementno = a.edorno"
					+ "                   and feeoperationtype = a.edortype"
					+ "                   and polno = a.polno"
					+ "                   and dutycode = y.dutycode"
					+ "                   and subfeeoperationtype like '%"
					+ BqCode.Get_BonusCashValue
					+ "%') c11,"
					+ "               nvl((select pb.AvailableAmnt from lpengbonuspol pb where pb.FiscalYear = (select max(FiscalYear) from loengbonuspol where polno = pb.polno) and pb.polno =a.polno),0) c12,"
					+ "               (select nvl(sum(getmoney), 0)"
					+ "                  from ljsgetendorse"
					+ "                 where endorsementno = a.edorno"
					+ "                   and feeoperationtype = a.edortype"
					+ "                   and polno = a.polno"
					+ "                   and dutycode = y.dutycode"
					+ "                   and subfeeoperationtype like '%"
					+ BqCode.Get_FinaBonus
					+ "%') c13,"
					+ "               (select nvl(sum(getmoney), 0)"
					+ "                  from ljsgetendorse"
					+ "                 where endorsementno = a.edorno"
					+ "                   and feeoperationtype = a.edortype"
					+ "                   and polno = a.polno"
					+ "                   and dutycode = y.dutycode) c14,"
					+ "               b.managecom c15"
					+ "          from lppol a, lpcont b,lcpol c,lcduty y"
					+ "         where 1 = 1"
					+ "           and b.edorno = '"
					+ mLPGrpEdorItemSchema.getEdorNo()
					+ "'"
					+ "           and b.edortype = '"
					+ mLPGrpEdorItemSchema.getEdorType()
					+ "'"
					+ "           and b.grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo()
					+ "'"
					+ "           and a.edorno = b.edorno"
					+ "           and a.contno = b.contno"
					+ "           and a.polno = c.polno"
					+ "           and a.polno = y.polno"
					+ "         order by c1 asc)" + "";
		}
		SS = tES.execSQL(Sql);
		if (SS == null || SS.MaxRow < 1) {
			CError.buildErr(this, "查询被保人退保详细信息失败!");
			return false;
		}
		for (int i = 1; i <= SS.MaxRow; i++) {
			strLine = new String[VTS_COLUMN_RISK];
			for (int j = 0; j < VTS_COLUMN_RISK; j++) {
				if (j >= 4 && j <= 13) {
					strLine[j] = BqNameFun.getRound(SS.GetText(i, j + 1));
				} else {
					strLine[j] = SS.GetText(i, j + 1);
				}
			}
			// 添加到小计处理
			if (!vct.contains(strLine[3])) {
				vct.add(strLine[3]);
			}
			// for(int p = 4;p<=13;p++){
			// if (!BqNameFun.dealHashTable(RiskTable,
			// strLine[3]+"小计"+p,strLine[p])) {
			// return false;
			// }
			// }
			if (!BqNameFun.dealHashTable(RiskTable, strLine[3] + "基本应退金额",
					strLine[5])
					|| !BqNameFun.dealHashTable(RiskTable, strLine[3]
							+ "基本应退加费", strLine[6])
					|| !BqNameFun.dealHashTable(RiskTable, strLine[3]
							+ "红利应退金额", strLine[10])
					|| !BqNameFun.dealHashTable(RiskTable, strLine[3] + "终了红利",
							strLine[12])
					|| !BqNameFun.dealHashTable(RiskTable, strLine[3] + "应退小计",
							strLine[13])) {
				return false;
			}

			if (!BqNameFun.dealHashTable(RiskTable, "总计", strLine[13])) {
				return false;
			}
			tContListTable.add(strLine);
		}
		aXmlExport.addListTable(tContListTable, tContListTitle);

		// 处理小计信息
		int VTS_COLUMN_XJ = 6;// 小计中的列数
		String[] xContListTitle = new String[VTS_COLUMN_XJ];
		for (int tTitle = 0; tTitle < VTS_COLUMN_XJ; tTitle++) {
			xContListTitle[tTitle] = "Billrisk" + String.valueOf(tTitle);
		}
		ListTable xContListTable = new ListTable();
		xContListTable.setName("XJ");
		String[] riskLine;

		for (int i = 1; i <= vct.size(); i++) {
			riskLine = new String[VTS_COLUMN_XJ];
			riskLine[0] = vct.get(i - 1).toString();
			riskLine[1] = BqNameFun.getRound((String) RiskTable.get(riskLine[0]
					+ "基本应退金额") == null ? "0.00" : (String) RiskTable
					.get(riskLine[0] + "基本应退金额"));
			riskLine[2] = BqNameFun.getRound((String) RiskTable.get(riskLine[0]
					+ "基本应退加费") == null ? "0.00" : (String) RiskTable
					.get(riskLine[0] + "基本应退加费"));
			riskLine[3] = BqNameFun.getRound((String) RiskTable.get(riskLine[0]
					+ "红利应退金额") == null ? "0.00" : (String) RiskTable
					.get(riskLine[0] + "红利应退金额"));
			riskLine[4] = BqNameFun.getRound((String) RiskTable.get(riskLine[0]
					+ "终了红利") == null ? "0.00" : (String) RiskTable
					.get(riskLine[0] + "终了红利"));
			riskLine[5] = BqNameFun.getRound((String) RiskTable.get(riskLine[0]
					+ "应退小计") == null ? "0.00" : (String) RiskTable
					.get(riskLine[0] + "应退小计"));

			xContListTable.add(riskLine);
		}
		aXmlExport.addListTable(xContListTable, xContListTitle);

		// 全部应退金额
		aTextTag.add("SumMoney", BqNameFun.getRound((String) RiskTable
				.get("总计") == null ? "0.00" : (String) RiskTable.get("总计")));
		aXmlExport.addTextTag(aTextTag);
		// aXmlExport.outputDocumentToFile("D:\\print\\", "GrpPTNameList");

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
		InputStream ins = aXmlExport.getInputStream();
		tLPEdorPrint2Main.setEdorInfo(ins);

		MMap map = new MMap();
		map.put("delete from LPEdorPrint2 where EdorNo='"
				+ mLPGrpEdorItemSchema.getEdorNo() + "'", "DELETE");
		map.put(tLPEdorPrint2Main, "BLOBINSERT");
		mResult.add(map);

		return true;
	}

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
	// public static void main(String[] args){
	// GlobalInput mGlobalInput=new GlobalInput();
	// mGlobalInput.ManageCom="86";
	// mGlobalInput.Operator="001";
	// VData tVData=new VData();
	// tVData.add(mGlobalInput);
	//
	// LPGrpEdorItemDB tLPGrpEdorItemDB=new LPGrpEdorItemDB();
	// tLPGrpEdorItemDB.setEdorAcceptNo("6120070417000005");
	// LPGrpEdorItemSchema tLPGrpEdorItemSchema=new LPGrpEdorItemSchema();
	// tLPGrpEdorItemSchema = tLPGrpEdorItemDB.query().get(1);
	// tVData.add(tLPGrpEdorItemSchema);
	//
	// XmlExport tXmlExport=new XmlExport();
	// try
	// {
	// tXmlExport.createDocument(VTS_NAME, "printer"); //初始化 XML 文档
	// }
	// catch (Exception ex)
	// {
	// logger.debug("\t@> PEdorGCGetNoticeBL.printData() : 设置 FormulaOne
	// VTS 文件异常！");
	// }
	// tVData.add(tXmlExport);
	//
	// GrpEdorPTPrintBL tGrpEdorPTPrintBL = new GrpEdorPTPrintBL();
	// if(tGrpEdorPTPrintBL.submitData(tVData,"PRINTPT")){
	// logger.debug("OK了哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
	// }
	// }
}
