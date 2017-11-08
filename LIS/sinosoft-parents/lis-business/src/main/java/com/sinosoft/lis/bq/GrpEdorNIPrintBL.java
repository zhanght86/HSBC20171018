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
 * Title: 增加被保人批单人名清单打印处理类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft Co.,Ltd
 * </p>
 * 
 * @author QianLy
 * @version 1.2
 * @Rewrited By QianLy on 2006-12-18 支持产品组合
 */
public class GrpEdorNIPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorNIPrintBL.class);
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
	private String sRiskType = "";// Add By QianLy on 2006-9-26

	public static final String VTS_NAME = "GrpEdorNameListNI.vts"; // 模板名称

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("NI数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("NI传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("NI数据打印失败!");
			return false;
		}

		// 打印人名清单数据处理
		if (!bulidBill()) {
			mErrors.addOneError("NI人名清单数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("NI准备数据失败!");
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
		if (!mOperate.equals("PRINTNI")) {
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
		xmlexport.addDisplayControl("displayNI");

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

		ExeSQL texesql = new ExeSQL();
		String strsql;
		strsql = "select nvl(sum(getmoney),0) " + " from ljsgetendorse "
				+ " where endorsementno = '" + mLPGrpEdorItemSchema.getEdorNo()
				+ "' " + " and feeoperationtype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' "
				+ " and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "' " + "";
		String sp = "";
		sp = texesql.getOneValue(strsql);
		if (sp == null || sp.equals("")) {
			CError.buildErr(this, "增加被保险人新增保费共计查询失败");
			return false;
		}
		mTextTag.add("NI.SumPrem", BqNameFun.chgMoney(Double.parseDouble(sp))); // 批单新增保费共计

		String strsql2 = "";
		strsql2 = "select count(1) " + " from lpedoritem  "
				+ " where edorno = '" + mLPGrpEdorItemSchema.getEdorNo() + "' "
				+ " and edortype = '" + mLPGrpEdorItemSchema.getEdorType()
				+ "' " + " and grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "' " + "";
		String sc = "0";
		sc = texesql.getOneValue(strsql2);
		if (sc == null || sc.equals("")) {
			CError.buildErr(this, "增加被保险人人数查询失败");
			return false;
		}
		mTextTag.add("NI.AddPersonNo", sc); // 批单增加人数

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
		ExeSQL tES = new ExeSQL();
		SSRS mSS = new SSRS();

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (tLCGrpContDB.query() == null || tLCGrpContDB.query().size() < 1) {
			CError.buildErr(this, "原保单信息查询失败!");
			return false;
		}
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		tLCGrpContSchema = tLCGrpContDB.query().get(1);

		// Add By QianLy on 2006-09-26----------------
		// 以险种做划分，以此判断显示哪个表格，“00341000”的是驾乘险
		String ssql = "select 'X' from dual where '00341000' in (select distinct riskcode from lpgrppol where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "')";
		sRiskType = tES.getOneValue(ssql);
		if (sRiskType.equals("X")) {
			tXmlExport.addDisplayControl("displayDrive");

			String[] allArr = BqNameFun.getAllNames(tLCGrpContSchema
					.getAgentCode());
			texttag.add("NIbill.GrpName", tLCGrpContSchema.getGrpName()); // 现单位名称
			texttag.add("NIbill.EdorNo", mLPGrpEdorItemSchema.getEdorNo()); // 团体批单号
			texttag
					.add("NIbill.GrpContNo", mLPGrpEdorItemSchema
							.getGrpContNo()); // 团体保单号
			texttag.add("NIbill.ValiDate", mLPGrpEdorItemSchema
					.getEdorValiDate()); // 生效日
			texttag.add("NIbill.Operator", CodeNameQuery
					.getOperator(mLPGrpEdorItemSchema.getOperator())); // 经办人
			texttag.add("NIbill.ApproveOperator", CodeNameQuery
					.getOperator(mLPGrpEdorItemSchema.getApproveOperator())); // 复核人
			texttag.add("NIbill.AgentName", allArr[BqNameFun.AGENT_NAME]); // 业务员姓名
			texttag.add("NIbill.AgentCode", tLCGrpContSchema.getAgentCode()); // 业务员编号

			String CarNo = BqNameFun.mapped("LCPolOtherFieldDesc", "CarNo",
					"LCPolOther");
			String ShelfNo = BqNameFun.mapped("LCPolOtherFieldDesc", "ShelfNo",
					"LCPolOther");
			String SeatCount = BqNameFun.mapped("LCPolOtherFieldDesc",
					"SeatCount", "LCPolOther");
			String sSql = "select c." + CarNo
					+ ", c."
					+ ShelfNo
					+ ", c."
					+ SeatCount
					+ ", "
					+ " "// 需要增加基本险应退金额，附加险应退金额
					+ " from LPPolOther c,LPPol p" + " where p.edorno = '"
					+ mLPGrpEdorItemSchema.getEdorNo() + "'"
					+ " and p.edortype = '"
					+ mLPGrpEdorItemSchema.getEdorType() + "'"
					+ " and p.grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo() + "'" + "";

			mSS = tES.execSQL(sSql);
			if (mSS == null || mSS.getMaxRow() < 1) {
				CError.buildErr(this, "查询人名清单信息失败!");
				return false;
			}

			String[] tCLT = new String[10];
			for (int iTitle = 0; iTitle < 10; iTitle++) {
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
				strLine[10] = String.valueOf(Double.parseDouble(mSS.GetText(i,
						4))
						+ Double.parseDouble(mSS.GetText(i, 5)));
				mContListTable.add(strLine);
				MainPolMoney += Double.parseDouble(mSS.GetText(i, 6));
				SubPolMoney += Double.parseDouble(mSS.GetText(i, 9));
			}
			tXmlExport.addListTable(mContListTable, tCLT);

			SumMoney = MainPolMoney + SubPolMoney;
			texttag.add("MainPolMoney", BqNameFun.chgMoney(MainPolMoney));
			texttag.add("SubPolMoney", BqNameFun.chgMoney(SubPolMoney));
			texttag.add("CTDrive.SumMoney", BqNameFun.chgMoney(SumMoney));
			tXmlExport.addTextTag(texttag);

			return true;
		} else {
			tXmlExport.addDisplayControl("displayBill");
			String[] allArr = BqNameFun.getAllNames(tLCGrpContSchema
					.getAgentCode());
			texttag.add("GrpName", tLCGrpContSchema.getGrpName()); // 现单位名称
			texttag.add("EdorNo", mLPGrpEdorItemSchema.getEdorNo()); // 团体批单号
			texttag.add("GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号
			texttag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate()); // 生效日
			texttag.add("Operator", CodeNameQuery
					.getOperator(mLPGrpEdorItemSchema.getOperator())); // 经办人
			texttag.add("ApproveOperator", CodeNameQuery
					.getOperator(mLPGrpEdorItemSchema.getApproveOperator())); // 复核人
			texttag.add("AgentName", allArr[BqNameFun.AGENT_NAME]); // 业务员姓名
			texttag.add("AgentCode", tLCGrpContSchema.getAgentCode()); // 业务员编号
			// --------------------------
			String isMulProd = BqNameFun.isMulProd(tLCGrpContSchema
					.getGrpContNo());
			if (isMulProd.toUpperCase().equals("FAILED")) {
				CError.buildErr(this, "查询团单是否是险种组合失败!");
				return false;
			}
			String strsql1 = "";
			if (isMulProd.toUpperCase().equals("FALSE")) {
				strsql1 = " select distinct yx,kh,xm,xb,sr,zj,hm,zy,xz,fs,nl,bj,Amnt,jb,jf,lx,jb+lx+jf,jg from"
						+ "  ("
						+ "  select (select customerseqno from lcinsured where contno = a.contno and insuredno = a.insuredno) yx,"
						+ "       a.insuredno kh,"
						+ "       a.insuredname xm,"
						+ "       (select codename from ldcode where codetype = 'sex' and code = a.insuredsex) xb,"
						+ "       a.insuredbirthday sr,"
						+ "       (select codename from ldcode where codetype = 'idtype' and code = a.insuredidtype) zj,"
						+ "       a.insuredidno hm,"
						+ "       (select trim(occupationtype) || '类' from lcinsured where a.contno = contno and a.insuredno = insuredno) zy,"
						+ "       (select origriskcode || riskshortname from lmrisk where riskcode = p.riskcode) xz,"
						+ "       case  when exists (select 1 from lmdutyget where getdutycode = g.getdutycode and gettype2 = '1') then (select ParamsName from LMRiskParamsDef where riskcode = p.riskcode and ParamsCode = trim(g.getdutykind) and ParamsType='getdutykind') else '－－' end fs,"
						+ "       case  when exists (select 1 from lmdutyget where getdutycode = g.getdutycode and gettype2 = '1') then to_char(p.getyear) else '－－' end nl,"
						+ "       (select codename from ldcode where codetype = 'payintv' and code = a.payintv) bj,"
						+ "	   (case c.amntflag when '1' then p.amnt else p.mult end) Amnt, "
						+ "       (select nvl(sum(getmoney), 0) from ljsgetendorse where feeoperationtype = a.edortype and endorsementno = a.edorno and grpcontno = a.grpcontno and polno = p.polno and feefinatype = 'BF' and subfeeoperationtype like '%"
						+ BqCode.Pay_Prem
						+ "%') jb,"
						+ "       (select nvl(sum(getmoney), 0) from ljsgetendorse where feeoperationtype = a.edortype and endorsementno = a.edorno and grpcontno = a.grpcontno and polno = p.polno and feefinatype = 'BF' and (subfeeoperationtype like '%"
						+ BqCode.Pay_InsurAddPremHealth
						+ "%' or subfeeoperationtype like '%"
						+ BqCode.Pay_InsurAddPremOccupation
						+ "%')) jf,"
						+ "       (select nvl(sum(getmoney), 0) from ljsgetendorse where feeoperationtype = a.edortype and endorsementno = a.edorno and grpcontno = a.grpcontno and polno = p.polno and feefinatype = 'LX' and subfeeoperationtype like '%"
						+ BqCode.Pay_PremInterest
						+ "%') lx,"
						+ "       a.managecom jg"
						+ "  from lpcont a ,lppol p, lpget g,lcduty y,LMDuty c"
						+ "  where a.edorno = '"
						+ mLPGrpEdorItemSchema.getEdorNo()
						+ "'"
						+ "   and a.edortype = '"
						+ mLPGrpEdorItemSchema.getEdorType()
						+ "'"
						+ "   and a.grpcontno = '"
						+ mLPGrpEdorItemSchema.getGrpContNo()
						+ "'"
						+ "   and p.ContNo = a.contno"
						+ "   and p.edorno = g.edorno"
						+ "   and p.edorno = a.edorno"
						+ "   and p.edortype = g.edortype"
						+ "   and p.polno = g.polno"
						+ "   and p.polno = y.polno"
						+ "   and c.dutycode = y.dutycode"
						+ "   and g.state='Y'" + "  order by yx asc)" + "";
			} else if (isMulProd.toUpperCase().equals("TRUE")) {
				strsql1 = " select distinct yx,kh,xm,xb,sr,zj,hm,zy,xz,fs,nl,bj,Amnt,jb,jf,lx,jb+lx+jf,jg from"
						+ "  ("
						+ "  select (select customerseqno from lcinsured where contno = a.contno and insuredno = a.insuredno) yx,"
						+ "       a.insuredno kh,"
						+ "       a.insuredname xm,"
						+ "       (select codename from ldcode where codetype = 'sex' and code = a.insuredsex) xb,"
						+ "       a.insuredbirthday sr,"
						+ "       (select codename from ldcode where codetype = 'idtype' and code = a.insuredidtype) zj,"
						+ "       a.insuredidno hm,"
						+ "       (select trim(occupationtype) || '类' from lcinsured where a.contno = contno and a.insuredno = insuredno) zy,"
						+ "       (select trim(dutycode) || '-' || trim(dutyname) from lmduty where dutycode = y.dutycode) xz,"
						+ "       case  when exists (select 1 from lmdutyget where getdutycode = g.getdutycode and gettype2 = '1') then (select ParamsName from LMRiskParamsDef where riskcode = p.riskcode and ParamsCode = trim(g.getdutykind) and ParamsType='getdutykind') else '－－' end fs,"
						+ "       case  when exists (select 1 from lmdutyget where getdutycode = g.getdutycode and gettype2 = '1') then to_char(p.getyear) else '－－' end nl,"
						+ "       (select codename from ldcode where codetype = 'payintv' and code = a.payintv) bj,"
						+ "	    (case c.amntflag when '1' then y.amnt else y.mult end) Amnt, "
						+ "       (select nvl(sum(getmoney), 0) from ljsgetendorse where feeoperationtype = a.edortype and endorsementno = a.edorno and grpcontno = a.grpcontno and polno = p.polno and dutycode = y.dutycode and feefinatype = 'BF' and subfeeoperationtype like '%"
						+ BqCode.Pay_Prem
						+ "%') jb,"
						+ "       (select nvl(sum(getmoney), 0) from ljsgetendorse where feeoperationtype = a.edortype and endorsementno = a.edorno and grpcontno = a.grpcontno and polno = p.polno and dutycode = y.dutycode and feefinatype = 'LX' and subfeeoperationtype like '%"
						+ BqCode.Pay_PremInterest
						+ "%') lx,"
						+ "       (select nvl(sum(getmoney), 0) from ljsgetendorse where feeoperationtype = a.edortype and endorsementno = a.edorno and grpcontno = a.grpcontno and polno = p.polno and dutycode = y.dutycode and feefinatype = 'BF' and (subfeeoperationtype like '%"
						+ BqCode.Pay_InsurAddPremHealth
						+ "%' or subfeeoperationtype like '%"
						+ BqCode.Pay_InsurAddPremOccupation
						+ "%')) jf,"
						+ "       a.managecom jg"
						+ "  from lpcont a ,lppol p, lpget g,lcduty y,LMDuty c"
						+ "  where a.edorno = '"
						+ mLPGrpEdorItemSchema.getEdorNo()
						+ "'"
						+ "   and a.edortype = '"
						+ mLPGrpEdorItemSchema.getEdorType()
						+ "'"
						+ "   and a.grpcontno = '"
						+ mLPGrpEdorItemSchema.getGrpContNo()
						+ "'"
						+ "   and p.ContNo = a.contno"
						+ "   and p.edorno = g.edorno"
						+ "   and p.edorno = a.edorno"
						+ "   and p.edortype = g.edortype"
						+ "   and p.polno = g.polno"
						+ "   and p.polno = y.polno"
						+ "   and c.dutycode = y.dutycode"
						+ "   and g.state='Y'" + "  order by yx asc)" + "";
			}
			mSS.Clear();
			mSS = tES.execSQL(strsql1);
			if (mSS == null || mSS.getMaxRow() <= 0) {
				CError.buildErr(this, "增加被保险人信息查询失败");
				return false;
			}

			Hashtable tHashTable = new Hashtable();
			Vector vct = new Vector();// 用来保存险种名称，以此去哈希表取值。处理小计
			int VTS_COLUMN_KH = 18;// 模板中的列数

			String[] tContListTitle = new String[VTS_COLUMN_KH];
			for (int iTitle = 0; iTitle < VTS_COLUMN_KH; iTitle++) {
				tContListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
			}

			ListTable tContListTable = new ListTable();
			tContListTable.setName("BILL");
			String strLine[] = null;

			for (int i = 1; i <= mSS.getMaxRow(); i++) {
				strLine = new String[VTS_COLUMN_KH];
				for (int j = 0; j < VTS_COLUMN_KH; j++) {
					if (j >= 12 && j <= 16) {
						strLine[j] = BqNameFun.getRoundMoney(mSS.GetText(i,
								j + 1));
					} else {
						strLine[j] = mSS.GetText(i, j + 1);
					}
				}
				// 添加到小计处理
				if (!vct.contains(strLine[8])) {
					vct.add(strLine[8]);
				}
				if (!BqNameFun.dealHashTable(tHashTable, strLine[8] + "补交保费",
						strLine[13])
						|| !BqNameFun.dealHashTable(tHashTable, strLine[8]
								+ "补交加费", strLine[14])
						|| !BqNameFun.dealHashTable(tHashTable, strLine[8]
								+ "补交利息", strLine[15])
						|| !BqNameFun.dealHashTable(tHashTable, strLine[8]
								+ "补费小计", strLine[16])
						|| !BqNameFun.dealHashTable(tHashTable, "合计",
								strLine[16])) {
					return false;
				}
				tContListTable.add(strLine);
			}
			tXmlExport.addListTable(tContListTable, tContListTitle);

			// 处理小计信息
			int VTS_COLUMN_XJ = 5;// 小计中的列数
			String[] xContListTitle = new String[VTS_COLUMN_XJ];
			for (int tTitle = 0; tTitle < VTS_COLUMN_XJ; tTitle++) {
				xContListTitle[tTitle] = "Billrisk" + String.valueOf(tTitle);
			}
			ListTable xContListTable = new ListTable();
			xContListTable.setName("RISK");
			String[] riskLine;

			for (int i = 0; i < vct.size(); i++) {
				riskLine = new String[VTS_COLUMN_XJ];
				riskLine[0] = vct.get(i).toString();
				riskLine[1] = BqNameFun.getRoundMoney((String) tHashTable
						.get(riskLine[0] + "补交保费") == null ? "0.00"
						: (String) tHashTable.get(riskLine[0] + "补交保费"));
				riskLine[2] = BqNameFun.getRoundMoney((String) tHashTable
						.get(riskLine[0] + "补交加费") == null ? "0.00"
						: (String) tHashTable.get(riskLine[0] + "补交加费"));
				riskLine[3] = BqNameFun.getRoundMoney((String) tHashTable
						.get(riskLine[0] + "补交利息") == null ? "0.00"
						: (String) tHashTable.get(riskLine[0] + "补交利息"));
				riskLine[4] = BqNameFun.getRoundMoney((String) tHashTable
						.get(riskLine[0] + "补费小计") == null ? "0.00"
						: (String) tHashTable.get(riskLine[0] + "补费小计"));
				xContListTable.add(riskLine);
			}
			tXmlExport.addListTable(xContListTable, xContListTitle);

			texttag.add("SumMoney", BqNameFun
					.chgMoney((String) tHashTable.get("合计") == null ? "0.00"
							: (String) tHashTable.get("合计")));
			tXmlExport.addTextTag(texttag);
			vct = null;
			tHashTable = null;
			// tXmlExport.outputDocumentToFile("D:\\print\\", "GrpNINameList");
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
	// tLPGrpEdorItemDB.setEdorAcceptNo("6120070119000005");
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
	// GrpEdorNIPrintBL tGEdorPrintBL = new GrpEdorNIPrintBL();
	// if(tGEdorPrintBL.submitData(tVData,"PRINTNI")){
	// logger.debug("OK了哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
	// }
	// }
}
