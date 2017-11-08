package com.sinosoft.lis.bqgrp;

import java.io.InputStream;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.bq.EdorPrint;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
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
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title:投保人基本信息变更批单数据生成
 * </p>
 * 
 * <p>
 * Description:生成保全项目被保险人基本信息变更的批单数据
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author wangyan
 * 
 * @version 1.0
 */

public class GrpEdorAXPrintBL implements EdorPrint {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(GrpEdorAXPrintBL.class);

	// 全局变量
	private VData mResult = new VData();

	public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;

	private String mNum = "";

	private String mSumPrem = "";

	private boolean mEdorConfirm = false;

	private String mConfNo = "";

	private VData mInputData = new VData();

	private TextTag mTextTag = new TextTag();

	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private GlobalInput mGlobalInput = new GlobalInput();

	private XmlExportNew xmlexport = new XmlExportNew();

	public static final String VTS_NAME = "GrpEdorNameListAX.vts"; // 模板名称

	public GrpEdorAXPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 数据传输
		if (!getInputData()) {
			CError.buildErr(this, "AX数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			CError.buildErr(this, "AX数据提取失败!");
			return false;
		}
		// 打印人名清单数据处理
		if (!bulidBill()) {
			mErrors.addOneError("AX人名清单数据打印失败!");
			return false;
		}
		// 准备数据
		if (!prepareData()) {
			CError.buildErr(this, "AX数据生成失败!");
			return false;
		}
		return true;
	}

	private boolean getInputData() {
		mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
				.getObjectByObjectName("LPGrpEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		xmlexport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		if (mLPGrpEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTAX")) {
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
		// xmlexport.createDocument("PrtAppEndorsement.vts", "printer");

		xmlexport.addDisplayControl("displayHead");
		xmlexport.addDisplayControl("displayAX");

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "查询原保单信息失败!");
			return false;
		}
		tLCGrpContSchema = tLCGrpContDB.getSchema();

		mTextTag.add("GrpName", tLCGrpContSchema.getGrpName()); // 单位名称
		if (mLPEdorAppSchema != null) {
			mEdorConfirm = true;
			mTextTag.add("EdorNo", mLPEdorAppSchema.getEdorConfNo()); // 团体批单
			mConfNo = mLPEdorAppSchema.getEdorConfNo();
		} else {
			mTextTag.add("EdorNo", mLPGrpEdorItemSchema.getEdorNo());
		}
		mTextTag.add("EdorAcceptNo", mLPGrpEdorItemSchema.getEdorAcceptNo()); // 保全受理号
		mTextTag.add("GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号
		mTextTag.add("EdorAppDate", mLPGrpEdorItemSchema.getEdorAppDate()); // 团体保单号

		ListTable tAXListTable = new ListTable();
		tAXListTable.setName("AX");

		String dNum = "";// 变动的人数
		ExeSQL tExeSQL = new ExeSQL();
		String tSql = "select count(distinct InsuredNo)  from LPEdorItem where EdorNo = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "'";
		dNum = tExeSQL.getOneValue(tSql);
		if (dNum == null || dNum.equals("")) {
			CError.buildErr(this, "统计增加的人数失败!");
			return false;
		}
		mNum = dNum;
		String dSumMoney = "";// 总合计交费
		tSql = "select NVL(sum(GetMoney),0) from LJsgetendorse where endorsementno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "'";
		dSumMoney = tExeSQL.getOneValue(tSql);
		mSumPrem = dSumMoney;
		// logger.debug("险种总保费为：" + dBF);
		tSql = " select k.riskname, '退费', '0', sum(j.getmoney) "
				+ " from ljsgetendorse j, lmrisk k"
				+ " where k.riskcode = j.riskcode " + " and endorsementno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' group by k.riskname";
		SSRS rSSRS = new SSRS();
		ExeSQL rExeSQL = new ExeSQL();
		rSSRS = rExeSQL.execSQL(tSql);
		if (rSSRS == null || rSSRS.MaxRow < 1) {
			String[] strAX = new String[4];
			for (int k = 1; k <= strAX.length; k++) {
				strAX[k - 1] = "--";
			}
			tAXListTable.add(strAX);
		} else {

			int tArrLen = rSSRS.MaxRow;
			for (int i = 1; i <= tArrLen; i++) {
				String[] strAX = new String[4];
				for (int k = 1; k <= strAX.length; k++) {
					strAX[k - 1] = rSSRS.GetText(i, k);
				}
				tAXListTable.add(strAX);
			}
		}
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		mLPEdorAppSchema = tLPEdorAppDB.query().get(1);

		mTextTag.add("AXNUM", dNum);
		mTextTag.add("AXSUM", dSumMoney);
		String tUserCode = mLPEdorAppSchema.getOperator();
		mTextTag.add("EdorAppName", CodeNameQuery.getOperator(tUserCode));
		String tApproveOperator = mLPEdorAppSchema.getApproveOperator();
		String tApproveName = "";
		if (tApproveOperator == null || "".equals(tApproveOperator)) {
			tApproveName = "";
		} else {
			tApproveName = CodeNameQuery.getOperator(tApproveOperator);
		}
		mTextTag.add("ApproveOperator", tApproveName);
		mTextTag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate());
		mTextTag.add("ApproveDate", mLPEdorAppSchema.getApproveDate());
		mTextTag.add("Operator", mLPGrpEdorItemSchema.getOperator());
		String[] b_strAX = new String[2];
		xmlexport.addListTable(tAXListTable, b_strAX);
		xmlexport.addTextTag(mTextTag);
		xmlexport.addDisplayControl("displayVD");
		xmlexport.addDisplayControl("displayTail");
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean bulidBill() {
		// 人名清单
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档
		if (mEdorConfirm) {

			tXmlExport.addDisplayControl("displayHead");// 保全确认
		} else {
			tXmlExport.addDisplayControl("displayHead1");// 申请确认
		}
		tXmlExport.addDisplayControl("displayAX");
		TextTag texttag = new TextTag();
		SSRS tssrs1 = new SSRS();
		ExeSQL texesql = new ExeSQL();

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "查询原保单信息失败!");
			return false;
		}
		tLCGrpContSchema = tLCGrpContDB.getSchema();

		texttag.add("GrpName", tLCGrpContSchema.getGrpName()); // 单位名称
		if (mLPEdorAppSchema != null) {
			texttag.add("EdorNo", mConfNo); // 团体批单
		} else {
			texttag.add("EdorNo", mLPGrpEdorItemSchema.getEdorNo());
		}
		texttag.add("EdorAcceptNo", mLPGrpEdorItemSchema.getEdorAcceptNo()); // 保全受理号
		texttag.add("GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号
		texttag.add("EdorAppDate", mLPGrpEdorItemSchema.getEdorAppDate()); // 团体保单号

		String strsql1 = "";
		strsql1 = "select a.name,"
				+ " decode(a.sex, '0', '男', '1', '女', '不详'), "
				+ " a.birthday,a.idno,"
				+ " (select riskname from lmrisk where riskcode = b.riskcode), "
				+ " (select sum(sumactupaymoney) "
				+ " from ljapayperson "
				+ " where contno = b.contno), "
				+ " nvl((select d.getmoney from ljsgetendorse d where  d.endorsementno = b.edorno and d.polno = b.polno),0), "
				+ " '"
				+ mLPGrpEdorItemSchema.getEdorValiDate()
				+ "',"
				+ " (select nvl2(c.bankcode,(select bankname from ldbank where bankcode = c.bankcode),'--') from lpbnf c where c.edorno = b.edorno and c.contno = b.contno and rownum=1), "
				+ " (select (nvl(c.BankAccNo, '--') ) from lpbnf c where c.edorno = b.edorno and c.contno = b.contno and rownum=1), "
				+ " (select nvl(c.AccName, '--') from  lpbnf c where c.edorno = b.edorno and c.contno = b.contno and rownum=1) "
				+ " from lcinsured a, lppol b "
				+ " where a.contno = b.contno and a.insuredno = b.insuredno and a.grpcontno = b.grpcontno  "
				+ " and b.edorno = '" + mLPGrpEdorItemSchema.getEdorNo() + "'";

		tssrs1 = texesql.execSQL(strsql1);
		if (tssrs1 == null || tssrs1.getMaxRow() <= 0) {
			CError.buildErr(this, "查询被保人信息变更查询失败");
			return false;
		}
		ListTable tContListTable = new ListTable();
		tContListTable.setName("AX");
		int VTS_COLUMN = 11;// 模板中的列数
		// 临时用来保存结果的数组，用他来填充下面的strLine
		for (int i = 0; i < tssrs1.getMaxRow(); i++) {
			// 赋值,如果为空用两个横线填充－－－－－－－－－
			String[] tmp = new String[VTS_COLUMN];
			for (int p = 0; p < VTS_COLUMN; p++) {
				tmp[p] = tssrs1.GetText(i + 1, p + 1) == "" ? "－－" : tssrs1
						.GetText(i + 1, p + 1);
			}
			tContListTable.add(tmp);

		}
		String[] tContListTitle = new String[VTS_COLUMN];

		texttag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate());
		texttag.add("AXSUM", mSumPrem);
		texttag.add("AXNUM", mNum);
		tXmlExport.addListTable(tContListTable, tContListTitle);
		tXmlExport.addTextTag(texttag);

		LPEdorPrint2Schema tLPEdorPrint2Main = new LPEdorPrint2Schema();
		// if(mEdorConfirm){
		//			
		// tLPEdorPrint2Main.setEdorNo(mConfNo);;//保全确认
		// }else {
		tLPEdorPrint2Main.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		;// 申请确认
		// }

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

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			return false;
		}
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
