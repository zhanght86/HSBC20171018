package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.io.InputStream;

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
 * Title:
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
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class GrpEdorREPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorREPrintBL.class);

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

	public static final String VTS_NAME = "P002080.vts"; // 模板名称

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("RE数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("RE传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("RE数据打印失败!");
			return false;
		}

		// 打印人名清单数据处理
		if (!bulidBill()) {
			mErrors.addOneError("RE人名清单数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("RE准备数据失败!");
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
		if (!mOperate.equals("PRINTRE")) {
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
		xmlexport.addDisplayControl("displayRE");

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
		String strsql;
		strsql = "select count(*) " + " from LPEdorItem " + " where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' " + " and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' "
				+ " and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "' ";

		tssrs = texesql.execSQL(strsql);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "保险合同效力恢复人数查询失败");
			return false;
		}
		mTextTag.add("RE.PersonNo", tssrs.GetText(1, 1)); // 保险合同效力恢复人数

		SSRS tssrs2 = new SSRS();
		ExeSQL texesql2 = new ExeSQL();
		String strsql2;
		strsql2 = "select nvl(sum(getmoney),0) from ljsgetendorse "
				+ " where subfeeoperationtype like '" + BqCode.Pay_Prem + "%'"
				+ " and endorsementno = '" + mLPGrpEdorItemSchema.getEdorNo()
				+ "'" + " and feeoperationtype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "'"
				+ " and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "'";

		tssrs2 = texesql2.execSQL(strsql2);
		if (tssrs2 == null || tssrs2.getMaxRow() <= 0) {
			CError.buildErr(this, "查询补交保费失败");
			return false;
		}
		mTextTag.add("RE.PremMoney", BqNameFun.chgMoney(tssrs2.GetText(1, 1))); // 补交保费

		SSRS tssrs3 = new SSRS();
		ExeSQL texesql3 = new ExeSQL();
		String strsql3;
		strsql3 = "select nvl(sum(getmoney),0) from ljsgetendorse "
				+ " where subfeeoperationtype like '" + BqCode.Pay_PremInterest
				+ "%'" + " and endorsementno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "'"
				+ " and feeoperationtype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "'"
				+ " and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "'";

		tssrs3 = texesql3.execSQL(strsql3);
		if (tssrs3 == null || tssrs2.getMaxRow() <= 0) {
			CError.buildErr(this, "查询补交利息失败");
			return false;
		}
		mTextTag.add("RE.PremInterest", BqNameFun
				.chgMoney(tssrs3.GetText(1, 1))); // 补交利息

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

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "原保单信息查询失败!");
			return false;
		}
		tLCGrpContSchema = tLCGrpContDB.getSchema();

		String[] allArr = BqNameFun
				.getAllNames(tLCGrpContSchema.getAgentCode());
		texttag.add("REbill.GrpName", tLCGrpContSchema.getGrpName()); // 现单位名称
		texttag.add("REbill.EdorNo", mLPGrpEdorItemSchema.getEdorNo()); // 团体批单号
		texttag.add("REbill.GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号
		texttag.add("REbill.ValiDate", mLPGrpEdorItemSchema.getEdorValiDate()); // 生效日
		texttag.add("REbill.Operator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getOperator())); // 经办人
		texttag.add("REbill.ApproveOperator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getApproveOperator())); // 复核人
		texttag.add("REbill.AgentName", allArr[BqNameFun.AGENT_NAME]); // 业务员姓名
		texttag.add("REbill.AgentCode", tLCGrpContSchema.getAgentCode()); // 业务员编号

		String strsql1 = "";
		strsql1 = "select (select CustomerSeqNo from lcinsured where contno = a.contno and insuredno = a.insuredno) yx, "
				+ " a.insuredno,"
				+ " (select name from lcinsured where contno = a.contno and insuredno = a.insuredno), "
				+ " (select managecom from lccont where contno = a.contno),"
				+ " (select idno from lcinsured where contno = a.contno and insuredno = a.insuredno), "
				+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and grpcontno = a.grpcontno and subfeeoperationtype like '"
				+ BqCode.Pay_Prem
				+ "%'),"
				+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and grpcontno = a.grpcontno and subfeeoperationtype like '"
				+ BqCode.Pay_InsurAddPremHealth
				+ "%'),"
				+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and grpcontno = a.grpcontno and subfeeoperationtype like '"
				+ BqCode.Pay_InsurAddPremOccupation
				+ "%'),"
				+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and grpcontno = a.grpcontno and subfeeoperationtype like '"
				+ BqCode.Pay_PremInterest
				+ "%'),"
				+ " (select nvl(sum(getmoney),0) from ljsgetendorse where endorsementno = a.edorno and feeoperationtype = a.edortype and grpcontno = a.grpcontno)"
				+ " from lpedoritem a"
				+ " where 1=1"
				+ " and a.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "'"
				+ " and a.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "'"
				+ " and a.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "'" + " order by yx asc";

		SSRS tssrs1 = new SSRS();
		ExeSQL texesql = new ExeSQL();
		tssrs1 = texesql.execSQL(strsql1);

		if (tssrs1 == null || tssrs1.getMaxRow() <= 0) {
			CError.buildErr(this, "保险合同效力恢复信息查询失败");
			return false;
		}

		double allSumMoney = 0.0; // 补交金额合计
		double allPrem = 0.0; // 补交保费合计
		double allInsurAddPremHealth = 0.0; // 补交健康加费合计
		double allInsurAddPremOccupation = 0.0; // 补交职业加费合计
		double allPremInterest = 0.0; // 补交利息合计

		String[] tContListTitle = new String[10];
		for (int iTitle = 0; iTitle < 10; iTitle++) {
			tContListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;

		int j;// 计数
		for (int i = 1; i <= tssrs1.getMaxRow(); i++) {
			strLine = new String[10];
			for (j = 0; j < 10; j++) {
				strLine[j] = tssrs1.GetText(i, j + 1);
			}
			allPrem = allPrem + Double.parseDouble(tssrs1.GetText(i, 6));
			allInsurAddPremHealth = allInsurAddPremHealth
					+ Double.parseDouble(tssrs1.GetText(i, 7));
			allInsurAddPremOccupation = allInsurAddPremOccupation
					+ Double.parseDouble(tssrs1.GetText(i, 8));
			allPremInterest = allPremInterest
					+ Double.parseDouble(tssrs1.GetText(i, 9));
			allSumMoney = allSumMoney
					+ Double.parseDouble(tssrs1.GetText(i, 10));
			tContListTable.add(strLine);
		}

		texttag.add("REbill.PersonNo", tssrs1.MaxRow);

		texttag.add("REbill.AllPrem", BqNameFun.chgMoney(allPrem));
		texttag.add("REbill.AllInsurAddPremHealth", BqNameFun
				.chgMoney(allInsurAddPremHealth));
		texttag.add("REbill.AllInsurAddPremOccupation", BqNameFun
				.chgMoney(allInsurAddPremOccupation));
		texttag.add("REbill.AllPremInterest", BqNameFun
				.chgMoney(allPremInterest));
		texttag.add("REbill.AllSumMoney", BqNameFun.chgMoney(allSumMoney));

		tXmlExport.addListTable(tContListTable, tContListTitle);
		tXmlExport.addTextTag(texttag);

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
}
