package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;

import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LMRiskDB;
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
 * Title: 团体减人批单人名清单打印类
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
 */
public class GrpEdorZTPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorZTPrintBL.class);

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
	public static final String VTS_NAME = "GrpEdorNameListZT.vts"; // 模板名称
	private String sRiskType = "";// Add By QianLy on 2006-10-9
	// public static final String FormulaOneVTS = "PrtGrpEndorsement.vts";//Add
	// By QianLy For Test on 2006-9-27

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("ZT数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("ZT传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("ZT数据打印失败!");
			return false;
		}

		// 打印人名清单数据处理
		if (!bulidBill()) {
			mErrors.addOneError("ZT人名清单数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("ZT准备数据失败!");
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
		if (!mOperate.equals("PRINTZT")) {
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
		xmlexport.addDisplayControl("displayZT");

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

		SSRS tssrs = new SSRS(); // 查询减人涉及的险种信息
		ExeSQL texesql = new ExeSQL();
		String strsql;
		strsql = "select riskcode,(select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' and feeoperationtype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' and grppolno = a.grppolno and grpcontno = a.grpcontno)"
				+ " from lcgrppol a where 1=1 "
				+ " and appflag = '1'"
				+ " and grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "'";
		tssrs = texesql.execSQL(strsql);

		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "查询减人险种信息失败");
			return false;
		}

		String riskName = ""; // 所有减人涉及的险种名称
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			if (i == tssrs.getMaxRow()) {
				riskName = riskName + getRiskName(tssrs.GetText(i, 1));
			} else {
				riskName = riskName + getRiskName(tssrs.GetText(i, 1)) + "、";
			}
		}
		mTextTag.add("ZT.RiskName", riskName); // 批单添加减人涉及的险种名称

		String[] ztContListTitle = new String[2];//
		for (int iTitle = 0; iTitle < 2; iTitle++) {
			ztContListTitle[iTitle] = "Billzt" + String.valueOf(iTitle);
		}

		ListTable ztContListTable = new ListTable();
		ztContListTable.setName("BILLzt");
		String strLine[] = null;

		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			strLine = new String[2];
			strLine[0] = "险种名称  " + getRiskName(tssrs.GetText(i, 1));
			strLine[1] = "退保金额为 " + BqNameFun.chgMoney(tssrs.GetText(i, 2))
					+ " 元";// Modified By QianLy on 2006-9-27
			ztContListTable.add(strLine);
		}
		xmlexport.addListTable(ztContListTable, ztContListTitle);// 添加减人涉及的险种信息

		SSRS tssrs2 = new SSRS();
		ExeSQL texesql2 = new ExeSQL();
		String strsql2 = "";
		strsql2 = "select count(contno) " + " from lpedoritem  "
				+ " where edorno = '" + mLPGrpEdorItemSchema.getEdorNo() + "' "
				+ " and edortype = '" + mLPGrpEdorItemSchema.getEdorType()
				+ "' " + " and grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "' ";

		tssrs2 = texesql2.execSQL(strsql2);
		// if(tssrs2 == null || tssrs2.getMaxRow()<=0) {
		// CError.buildErr(this, "查询减少人数失败");
		// return false;
		// }
		mTextTag.add("ZT.DelPersonNo", tssrs2.GetText(1, 1)); // 批单减少人数

		String[] allArr = BqNameFun
				.getAllNames(tLCGrpContSchema.getAgentCode());
		mTextTag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate());
		mTextTag.add("ApproveOperator", mLPGrpEdorItemSchema
				.getApproveOperator());
		mTextTag.add("Operator", mLPGrpEdorItemSchema.getOperator());
		mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		mTextTag.add("LAAgent.AgentCode", tLCGrpContSchema.getAgentCode());
		xmlexport.addTextTag(mTextTag);
		// xmlexport.outputDocumentToFile("D:\\qly\\XMLDoc\\", "QlyGrpZT");//Add
		// By QianLy for test on 2006-9-27
		xmlexport.addDisplayControl("displayTail");

		return true;
	}

	private boolean bulidBill() {
		// 人名清单
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		TextTag texttag = new TextTag();

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (tLCGrpContDB.query() == null || tLCGrpContDB.query().size() < 1) {
			CError.buildErr(this, "原保单信息查询失败!");
			return false;
		}
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		tLCGrpContSchema = tLCGrpContDB.query().get(1);

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

		ExeSQL tES = new ExeSQL();

		// Add By QianLy on 2006-09-26----------------
		// 以险种做划分，以此判断显示哪个表格，“00341000”的是驾乘险
		String ssql = "select 'X' from dual where '00341000' in (select distinct riskcode from lpgrppol where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "')";
		sRiskType = tES.getOneValue(ssql);
		if (sRiskType.equals("X")) {
			texttag.add("Title", "驾乘安心团体意外伤害保险增加投保机动车辆清单"); // 清单题目
			tXmlExport.addDisplayControl("displayDrive");

			String CarNo = BqNameFun.mapped("LCPolOtherFieldDesc", "CarNo",
					"LCPolOther");
			String ShelfNo = BqNameFun.mapped("LCPolOtherFieldDesc", "ShelfNo",
					"LCPolOther");
			String SeatCount = BqNameFun.mapped("LCPolOtherFieldDesc",
					"SeatCount", "LCPolOther");
			String sSql = "Select p."
					+ CarNo
					+ ", p."
					+ ShelfNo
					+ ", p."
					+ SeatCount
					+ ", "
					+ " " // 需要增加基本险应退金额，附加险应退金额
					+ " from LPPolOther p,LJSGetEndorse g,LCPol c"
					+ " where p.edorno = '" + mLPGrpEdorItemSchema.getEdorNo()
					+ "'" + "   and p.edortype = '"
					+ mLPGrpEdorItemSchema.getEdorType() + "'"
					+ "   and p.grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo() + "'"
					+ "   and p.grpcontno = g.grpcontno"
					+ "   and p.edorno = g.endorsementno"
					+ "   and p.edortype = g.feeoperationtype"
					+ "   and p.polno = g.polno" + "";

			SSRS mSS = new SSRS();
			mSS = tES.execSQL(sSql);
			if (mSS == null || mSS.getMaxRow() < 1) {
				CError.buildErr(this, "查询人名清单信息失败!");
				return false;
			}

			String[] tCLT = new String[7];
			for (int iTitle = 0; iTitle < 7; iTitle++) {
				tCLT[iTitle] = "Drive" + String.valueOf(iTitle);
			}

			ListTable mContListTable = new ListTable();
			mContListTable.setName("DRIVE");
			String strLine[] = null;

			double MainPolMoney = 0.00;
			double SubPolMoney = 0.00;
			double SumMoney = 0.00;

			for (int i = 1; i <= mSS.getMaxRow(); i++) {
				strLine = new String[10];
				strLine[0] = String.valueOf(i);
				strLine[1] = mSS.GetText(i, 1);
				strLine[2] = mSS.GetText(i, 2);
				strLine[3] = mSS.GetText(i, 3);
				strLine[4] = mSS.GetText(i, 4);
				strLine[5] = mSS.GetText(i, 5);
				// .........
				strLine[7] = String.valueOf(Double.parseDouble(mSS
						.GetText(i, 4))
						+ Double.parseDouble(mSS.GetText(i, 5)));
				mContListTable.add(strLine);
				MainPolMoney += Double.parseDouble(mSS.GetText(i, 6));
				SubPolMoney += Double.parseDouble(mSS.GetText(i, 9));
			}
			tXmlExport.addListTable(mContListTable, tCLT);

			SumMoney = MainPolMoney + SubPolMoney;
			texttag.add("MainPolMoney", String.valueOf(MainPolMoney));
			texttag.add("SubPolMoney", String.valueOf(SubPolMoney));
			texttag.add("CTDrive.SumMoney", String.valueOf(SumMoney));
			tXmlExport.addTextTag(texttag);

			return true;
		} else {
			// texttag.add("Title", "增加被保险人人名清单"); //清单题目
			tXmlExport.addDisplayControl("displayBill");
			// --------------------------
			SSRS tSSRS = new SSRS();
			Hashtable RiskTable = new Hashtable();
			String isMulProd = BqNameFun.isMulProd(mLPGrpEdorItemSchema
					.getGrpContNo());
			if (isMulProd.toUpperCase().equals("FAILED")) {
				CError.buildErr(this, "查询团单是否是险种组合失败!");
				return false;
			}
			String SQL = "";
			if (isMulProd.toUpperCase().equals("FALSE")) {
				SQL = "select yx,kh,xm,xz,jb,lj,zl,jf,jb+lj+zl+jf xj,jg from ("
						+ " select (select CustomerSeqNo from lcinsured where contno = b.contno and insuredno = b.insuredno) yx,"
						+ " b.insuredno kh,"
						+ " b.insuredname xm,"
						+ " (select origriskcode || riskshortname from lmrisk where riskcode = a.riskcode) xz,"
						+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and polno = a.polno and subfeeoperationtype like '%"
						+ BqCode.Get_BaseCashValue
						+ "%') jb," // 基本保额应退金额
						+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and polno = a.polno and subfeeoperationtype like '%"
						+ BqCode.Get_BonusCashValue
						+ "%') lj," // 累计红利保额应退金额
						+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and polno = a.polno and subfeeoperationtype like '%"
						+ BqCode.Get_FinaBonus
						+ "%') zl," // 终了红利
						+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and polno = a.polno and (subfeeoperationtype like '%"
						+ BqCode.Get_AddPremHealth
						+ "%' or subfeeoperationtype like '%"
						+ BqCode.Get_AddPremOccupation
						+ "%')) jf," // 加费
						+ " b.managecom jg"
						+ " from lppol a,lpcont b"
						+ " where 1=1 "
						+ " and b.edorno = '"
						+ mLPGrpEdorItemSchema.getEdorNo()
						+ "'"
						+ " and b.edortype = '"
						+ mLPGrpEdorItemSchema.getEdorType()
						+ "'"
						+ " and b.grpcontno = '"
						+ mLPGrpEdorItemSchema.getGrpContNo()
						+ "'"
						+ " and a.edorno = b.edorno"
						+ " and a.contno = b.contno"
						+ " order by yx asc"
						+ " )";
			} else if (isMulProd.toUpperCase().equals("TRUE")) {
				SQL = "select c1,c2,c3,c4,nvl(c5,0),nvl(c6,0),nvl(c7,0),nvl(c8,0),nvl(c5,0)+nvl(c6,0)+nvl(c7,0)+nvl(c8,0) c9,c10"
						+ " from (select (select CustomerSeqNo"
						+ "          from lcinsured"
						+ "         where contno = b.contno"
						+ "           and insuredno = b.insuredno) c1,"
						+ "       b.insuredno c2,"
						+ "       b.insuredname c3,"
						+ "       (select dutyname from lmduty where dutycode = y.dutycode) c4,"
						+ "       (select nvl(sum(getmoney), 0)"
						+ "          from ljsgetendorse"
						+ "         where endorsementno = a.edorno"
						+ "           and feeoperationtype = a.edortype"
						+ "           and polno = a.polno"
						+ "           and dutycode = y.dutycode"
						+ "           and subfeeoperationtype like '%"
						+ BqCode.Get_BaseCashValue
						+ "%') c5,"
						+ "       (select nvl(sum(getmoney), 0)"
						+ "          from ljsgetendorse"
						+ "         where endorsementno = a.edorno"
						+ "           and feeoperationtype = a.edortype"
						+ "           and polno = a.polno"
						+ "           and dutycode = y.dutycode"
						+ "           and subfeeoperationtype like '%"
						+ BqCode.Get_BonusCashValue
						+ "%') c6,"
						+ "       (select nvl(sum(getmoney), 0)"
						+ "          from ljsgetendorse"
						+ "         where endorsementno = a.edorno"
						+ "           and feeoperationtype = a.edortype"
						+ "           and polno = a.polno"
						+ "           and dutycode = y.dutycode"
						+ "           and subfeeoperationtype like '%"
						+ BqCode.Get_FinaBonus
						+ "%') c7,"
						+ "       (select nvl(sum(getmoney), 0)"
						+ "          from ljsgetendorse"
						+ "         where endorsementno = a.edorno"
						+ "           and feeoperationtype = a.edortype"
						+ "           and polno = a.polno"
						+ "           and dutycode = y.dutycode"
						+ "           and (subfeeoperationtype like '%"
						+ BqCode.Get_AddPremHealth
						+ "%' or"
						+ "               subfeeoperationtype like '%"
						+ BqCode.Get_AddPremOccupation
						+ "%')) c8,"
						+ "       b.managecom c10"
						+ "  from lppol a, lpcont b,lcduty y"
						+ "  where 1 = 1"
						+ "   and b.edorno = '"
						+ mLPGrpEdorItemSchema.getEdorNo()
						+ "'"
						+ "   and b.edortype = '"
						+ mLPGrpEdorItemSchema.getEdorType()
						+ "'"
						+ "   and b.grpcontno = '"
						+ mLPGrpEdorItemSchema.getGrpContNo()
						+ "'"
						+ "   and a.edorno = b.edorno"
						+ "   and a.contno = b.contno"
						+ "   and a.polno = y.polno"
						+ "   order by c1 asc)"
						+ "";
			}
			tSSRS = tES.execSQL(SQL);
			if (tSSRS == null || tSSRS.MaxRow < 1) {
				CError.buildErr(this, "查询被保人退保详细信息失败!");
				return false;
			}
			int VTS_COLUMN = 10;// 模板中的横向字段个数，包括序号列,在此定义,便于维护
			String[] tContListTitle = new String[VTS_COLUMN];
			for (int iTitle = 0; iTitle < VTS_COLUMN; iTitle++) {
				tContListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
			}

			ListTable tContListTable = new ListTable();
			tContListTable.setName("BILL");
			String strLine[] = null;

			Vector vct = new Vector();// 用来保存险种名称，以此去哈希表取值。处理小计
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				strLine = new String[VTS_COLUMN];
				for (int j = 0; j < VTS_COLUMN; j++) {
					if (j >= 4 && j <= 8) {
						strLine[j] = BqNameFun.getRoundMoney(tSSRS.GetText(i,
								j + 1));
					} else {
						strLine[j] = tSSRS.GetText(i, j + 1);
					}
				}
				// 添加到小计处理
				if (!vct.contains(strLine[3])) {
					vct.add(strLine[3]);
				}
				// ------------------------------------------------------------
				if (!BqNameFun.dealHashTable(RiskTable, strLine[3]
						+ "BaseCashValue", strLine[4])
						|| !BqNameFun.dealHashTable(RiskTable, strLine[3]
								+ "BonusCashValue", strLine[5])
						|| !BqNameFun.dealHashTable(RiskTable, strLine[3]
								+ "FinaBonus", strLine[6])
						|| !BqNameFun.dealHashTable(RiskTable, strLine[3]
								+ "AddFee", strLine[7])
						|| !BqNameFun.dealHashTable(RiskTable, strLine[3]
								+ "GetMoney", strLine[8])
						|| !BqNameFun.dealHashTable(RiskTable, "SumGetMoney",
								strLine[8])) {
					return false;
				}
				tContListTable.add(strLine);
			}
			tXmlExport.addListTable(tContListTable, tContListTitle);

			// 处理小计信息
			int VTS_COLUMN_XJ = 6;// 小计中的列数
			String[] xContListTitle = new String[VTS_COLUMN_XJ];
			for (int tTitle = 0; tTitle < VTS_COLUMN_XJ; tTitle++) {
				xContListTitle[tTitle] = "Billrisk" + String.valueOf(tTitle);
			}
			ListTable xContListTable = new ListTable();
			xContListTable.setName("BILLRISK");
			String[] riskLine;

			for (int i = 1; i <= vct.size(); i++) {
				riskLine = new String[VTS_COLUMN_XJ];
				riskLine[0] = vct.get(i - 1).toString();
				riskLine[1] = BqNameFun
						.getRoundMoney((String) RiskTable.get(riskLine[0]
								+ "BaseCashValue") == null ? "0.00"
								: (String) RiskTable.get(riskLine[0]
										+ "BaseCashValue"));
				riskLine[2] = BqNameFun.getRoundMoney((String) RiskTable
						.get(riskLine[0] + "BonusCashValue") == null ? "0.00"
						: (String) RiskTable
								.get(riskLine[0] + "BonusCashValue"));
				riskLine[3] = BqNameFun.getRoundMoney((String) RiskTable
						.get(riskLine[0] + "FinaBonus") == null ? "0.00"
						: (String) RiskTable.get(riskLine[0] + "FinaBonus"));
				riskLine[4] = BqNameFun.getRoundMoney((String) RiskTable
						.get(riskLine[0] + "AddFee") == null ? "0.00"
						: (String) RiskTable.get(riskLine[0] + "AddFee"));
				riskLine[5] = BqNameFun.getRoundMoney((String) RiskTable
						.get(riskLine[0] + "GetMoney") == null ? "0.00"
						: (String) RiskTable.get(riskLine[0] + "GetMoney"));
				xContListTable.add(riskLine);
			}
			tXmlExport.addListTable(xContListTable, xContListTitle);

			texttag.add("SumGetMoney", BqNameFun.chgMoney((String) RiskTable
					.get("SumGetMoney") == null ? "0.00" : (String) RiskTable
					.get("SumGetMoney")));
			tXmlExport.addTextTag(texttag);
			vct = null;
			RiskTable = null;
			// tXmlExport.outputDocumentToFile("D:\\qly\\XMLDoc\\",
			// "QlyGrpZTNameList");
		}

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
		map.put("delete from LPEdorPrint2 where EdorNo='"
				+ mLPGrpEdorItemSchema.getEdorNo() + "'", "DELETE");
		map.put(tLPEdorPrint2Main, "BLOBINSERT");
		mResult.add(map);

		return true;
	}

	private String getRiskName(String RiskCode) {
		try {
			LMRiskDB tLMRiskDB = new LMRiskDB();
			tLMRiskDB.setRiskCode(RiskCode);
			if (tLMRiskDB.getInfo())
				return tLMRiskDB.getRiskShortName();
			else
				return RiskCode;
		} catch (Exception e) {
			e.printStackTrace();
			return RiskCode;
		}
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
	// tLPGrpEdorItemDB.setEdorAcceptNo("6120061201000001");
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
	// GrpEdorZTPrintBL tGrpEdorZTPrintBL = new GrpEdorZTPrintBL();
	// if(tGrpEdorZTPrintBL.submitData(tVData,"PRINTZT")){
	// logger.debug("OK了哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
	// }
	// }
}
