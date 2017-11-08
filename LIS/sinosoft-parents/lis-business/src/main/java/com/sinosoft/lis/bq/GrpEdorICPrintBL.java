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
 * Title: 团体被保险人重要信息变更批单人名清单打印类
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
 * @author
 * @version 6.0
 * @Modified By QianLy on 2006-10-13,2007-01-04
 */
public class GrpEdorICPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorICPrintBL.class);
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
	public static final String VTS_NAME = "GrpEdorNameListIC.vts"; // 模板名称

	public GrpEdorICPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("IC数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("IC传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("IC数据打印失败!");
			return false;
		}

		// 打印人名清单数据处理
		if (!bulidBill()) {
			mErrors.addOneError("IC人名清单数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("IC准备数据失败!");
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
		if (!mOperate.equals("PRINTIC")) {
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
		xmlexport.addDisplayControl("displayIC");

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

		SSRS ss = new SSRS();
		ExeSQL tes = new ExeSQL();
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

		String isMulProd = BqNameFun.isMulProd(mLPGrpEdorItemSchema
				.getGrpContNo());
		if (isMulProd.toUpperCase().equals("FAILED")) {
			CError.buildErr(this, "查询团单是否是险种组合失败!");
			return false;
		}
		String strsql = "";
		if (isMulProd.toUpperCase().equals("FALSE")) {
			strsql = " select CustomerSeqNo,InsuredNo,Name,sex,birthday,idtype,IDNo,riskcode,enddate,prem,otherfee,addfee,lx,otherfee + addfee + lx sumfee,managecom from ("
					+ " select "
					+ "     a.CustomerSeqNo CustomerSeqNo,"
					+ "     a.InsuredNo InsuredNo,"
					+ "     a.Name Name,"
					+ "     (Select codename From ldcode Where codetype = 'sex' And code = a.sex) sex,"
					+ "     a.birthday birthday,"
					+ "     (Select codename From ldcode Where codetype = 'idtype' And code = a.idtype) idtype,"
					+ "     a.IDNo IDNo,"
					+ "      (case"
					+ "         when '6' = (select plantype from lccontplan where grpcontno = c.grpcontno and contplancode='A')"
					+ "         then (select dutyname from lmduty where dutycode = (select dutycode from lpduty where edorno = c.edorno and polno = b.polno and rownum = 1))"
					+ "         else (select origriskcode || riskshortname from lmrisk where riskcode = b.riskcode)"
					+ "       end) riskcode,"
					+ "     b.payendyear||Case b.payendyearflag When 'M' Then '月' When 'Y' Then '年' When 'D' Then '天' When 'A' Then '岁' Else '' End enddate,"
					+ "     b.prem prem,"
					+ "     (select nvl(Sum(getmoney),0) from ljsgetendorse Where endorsementno = a.edorno and feeoperationtype = a.edortype and (subfeeoperationtype like '%"
					+ BqCode.Pay_Prem
					+ "%' or subfeeoperationtype like '%"
					+ BqCode.Get_Prem
					+ "%') and ContNo = a.ContNo and insuredno = a.insuredno and polno = b.polno) otherfee,"
					+ "     (select nvl(Sum(getmoney),0) from ljsgetendorse Where endorsementno = a.edorno and feeoperationtype = a.edortype and (feefinatype = 'BF' or feefinatype = 'TF') and (subfeeoperationtype like '%"
					+ BqCode.Pay_InsurAddPremHealth
					+ "%' or subfeeoperationtype like '%"
					+ BqCode.Pay_InsurAddPremOccupation
					+ "%' or subfeeoperationtype like '%"
					+ BqCode.Get_AddPremHealth
					+ "%' or subfeeoperationtype like '%"
					+ BqCode.Get_AddPremOccupation
					+ "%') and ContNo = a.ContNo and insuredno = a.insuredno and polno = b.polno) addfee,"
					+ "     (select nvl(Sum(getmoney),0) from ljsgetendorse Where endorsementno = a.edorno and feeoperationtype = a.edortype and feefinatype = 'LX' and ContNo = a.ContNo and insuredno = a.insuredno and polno = b.polno) lx,"
					+ "     a.managecom managecom"
					+ " from LPInsured a,lppol b,lpcont c"
					+ " where a.edorno = '"
					+ mLPGrpEdorItemSchema.getEdorNo()
					+ "' "
					+ " and a.edortype = '"
					+ mLPGrpEdorItemSchema.getEdorType()
					+ "' "
					+ " and a.grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo()
					+ "' "
					+ " and b.edorno = a.edorno"
					+ " and b.edortype = a.edortype"
					+ " and b.contno = a.contno"
					+ " and b.grpcontno = a.grpcontno"
					+ " and b.edorno = c.edorno"
					+ " and b.contno = c.contno"
					+ " order by a.CustomerSeqNo asc)" + "";
		} else if (isMulProd.toUpperCase().equals("TRUE")) {
			// LiuZhao 2007-08-14 QC10113 c10取 y.prem
			strsql = "select c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,nvl(c11, 0),nvl(c12, 0),nvl(c13, 0),nvl(c11, 0) + nvl(c12, 0) + nvl(c13, 0),c15"
					+ "  from (select a.CustomerSeqNo c1,"
					+ "               a.InsuredNo c2,"
					+ "               a.Name c3,"
					+ "               (Select codename"
					+ "                  From ldcode"
					+ "                 Where codetype = 'sex'"
					+ "                   And code = a.sex) c4,"
					+ "               a.birthday c5,"
					+ "               (Select codename"
					+ "                  From ldcode"
					+ "                 Where codetype = 'idtype'"
					+ "                   And code = a.idtype) c6,"
					+ "               a.IDNo c7,"
					+ "               (select dutyname from lmduty where dutycode = y.dutycode) c8,"
					+ "               b.payendyear || Case b.payendyearflag"
					+ "                 When 'M' Then '月'"
					+ "                 When 'Y' Then '年'"
					+ "                 When 'D' Then '天'"
					+ "                 When 'A' Then '岁'"
					+ "                 Else ''"
					+ "               End c9,"
					+ "               y.prem c10,"
					+ "               (select nvl(Sum(getmoney), 0)"
					+ "                  from ljsgetendorse"
					+ "                 Where endorsementno = a.edorno"
					+ "                   and feeoperationtype = a.edortype"
					+ "                   and (subfeeoperationtype like '%"
					+ BqCode.Pay_Prem
					+ "%' or subfeeoperationtype like '%"
					+ BqCode.Get_Prem
					+ "%')"
					+ "                   and ContNo = a.ContNo"
					+ "                   and insuredno = a.insuredno and dutycode = y.dutycode) c11,"
					+ "               (select nvl(Sum(getmoney), 0)"
					+ "                  from ljsgetendorse"
					+ "                 Where endorsementno = a.edorno"
					+ "                   and feeoperationtype = a.edortype"
					+ "                   and (subfeeoperationtype like '%"
					+ BqCode.Pay_InsurAddPremHealth
					+ "%' or subfeeoperationtype like '%"
					+ BqCode.Pay_InsurAddPremOccupation
					+ "%' or subfeeoperationtype like '%"
					+ BqCode.Get_AddPremHealth
					+ "%' or subfeeoperationtype like '%"
					+ BqCode.Get_AddPremOccupation
					+ "%') "
					+ "                   and ContNo = a.ContNo"
					+ "                   and (feefinatype = 'BF' or feefinatype = 'TF') "
					+ "                   and insuredno = a.insuredno and dutycode = y.dutycode) c12,"
					+ "               (select nvl(Sum(getmoney), 0)"
					+ "                  from ljsgetendorse"
					+ "                 Where endorsementno = a.edorno"
					+ "                   and feeoperationtype = a.edortype"
					+ "                   and feefinatype = 'LX'"
					+ "                   and ContNo = a.ContNo"
					+ "                   and insuredno = a.insuredno and dutycode = y.dutycode) c13,"
					+ "               a.managecom c15"
					+ "          from LPInsured a, lppol b, lpcont c, lcduty y"
					+ "         where a.edorno = '"
					+ mLPGrpEdorItemSchema.getEdorNo()
					+ "' "
					+ "           and a.edortype = '"
					+ mLPGrpEdorItemSchema.getEdorType()
					+ "' "
					+ "           and a.grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo()
					+ "' "
					+ "           and b.edorno = a.edorno"
					+ "           and b.edortype = a.edortype"
					+ "           and b.contno = a.contno"
					+ "           and b.grpcontno = a.grpcontno"
					+ "           and b.edorno = c.edorno"
					+ "           and b.contno = c.contno"
					+ "           and b.polno = y.polno"
					+ "         order by a.CustomerSeqNo asc)" + "";

		}
		ss = tes.execSQL(strsql);
		if (ss == null || ss.getMaxRow() <= 0) {
			CError.buildErr(this, "查询被保人信息变更查询失败");
			return false;
		}

		int VTS_COLUMN = 15;// 模板中的横向字段个数，包括序号列,在此定义,便于维护
		String[] tContListTitle = new String[VTS_COLUMN];
		for (int iTitle = 0; iTitle < VTS_COLUMN; iTitle++) {
			tContListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;

		Hashtable RiskTable = new Hashtable();// 用以处理小计
		Vector vct = new Vector();// 用来保存险种名称，以此去哈希表取值。处理小计
		for (int i = 1; i <= ss.MaxRow; i++) {
			strLine = new String[VTS_COLUMN];
			for (int j = 0; j < VTS_COLUMN; j++) {
				if (j >= 9 && j <= 13) {
					strLine[j] = BqNameFun.getRound(ss.GetText(i, j + 1));
				} else {
					strLine[j] = ss.GetText(i, j + 1);
				}
			}
			// 添加到小计处理
			if (!vct.contains(strLine[7])) {
				vct.add(strLine[7]);
			}
			// ------------------------------------------------------------
			if (!BqNameFun.dealHashTable(RiskTable, strLine[7] + "Prem",
					strLine[9])
					|| !BqNameFun.dealHashTable(RiskTable, strLine[7]
							+ "RetnPrem", strLine[10])
					|| !BqNameFun.dealHashTable(RiskTable, strLine[7]
							+ "RetnAdd", strLine[11])
					|| !BqNameFun.dealHashTable(RiskTable, strLine[7]
							+ "AddInte", strLine[12])
					|| !BqNameFun.dealHashTable(RiskTable, strLine[7] + "XJ",
							strLine[13])
					|| !BqNameFun.dealHashTable(RiskTable, "SumGetMoney",
							strLine[13])) {
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
		xContListTable.setName("XJ");
		String[] riskLine;

		for (int i = 1; i <= vct.size(); i++) {
			riskLine = new String[VTS_COLUMN_XJ];
			riskLine[0] = vct.get(i - 1).toString();
			riskLine[1] = BqNameFun.getRound((String) RiskTable.get(riskLine[0]
					+ "Prem") == null ? "0.00" : (String) RiskTable
					.get(riskLine[0] + "Prem"));
			riskLine[2] = BqNameFun.getRound((String) RiskTable.get(riskLine[0]
					+ "RetnPrem") == null ? "0.00" : (String) RiskTable
					.get(riskLine[0] + "RetnPrem"));
			riskLine[3] = BqNameFun.getRound((String) RiskTable.get(riskLine[0]
					+ "RetnAdd") == null ? "0.00" : (String) RiskTable
					.get(riskLine[0] + "RetnAdd"));
			riskLine[4] = BqNameFun.getRound((String) RiskTable.get(riskLine[0]
					+ "AddInte") == null ? "0.00" : (String) RiskTable
					.get(riskLine[0] + "AddInte"));
			riskLine[5] = BqNameFun.getRound((String) RiskTable.get(riskLine[0]
					+ "XJ") == null ? "0.00" : (String) RiskTable
					.get(riskLine[0] + "XJ"));
			xContListTable.add(riskLine);
		}
		tXmlExport.addListTable(xContListTable, xContListTitle);

		texttag.add("SumGetMoney", BqNameFun.getRound((String) RiskTable
				.get("SumGetMoney") == null ? "0.00" : (String) RiskTable
				.get("SumGetMoney")));
		tXmlExport.addTextTag(texttag);
		vct = null;
		RiskTable = null;
		// tXmlExport.outputDocumentToFile("D:\\print\\", "GrpICNameList");

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
	// tLPGrpEdorItemDB.setEdorAcceptNo("6120070802000005");
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
	// logger.debug("\t@> PEdorGCGetNoticeBL.printData() : 设置 FormulaOne VTS
	// 文件异常！");
	// }
	// tVData.add(tXmlExport);
	//
	// GrpEdorICPrintBL tGEdorPrintBL = new GrpEdorICPrintBL();
	// if(tGEdorPrintBL.submitData(tVData,"PRINTIC")){
	// logger.debug("OK了哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
	// }
	// }

}
