package com.sinosoft.lis.bq;

import java.io.InputStream;
import java.util.Hashtable;

import org.apache.log4j.Logger;

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
 * Title: 养老金给付批单人名清单打印类
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
 * @author not attributable
 * @version 6.0 Modified By QianLy on 2006-10-23
 */
public class GrpEdorAGPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(GrpEdorAGPrintBL.class);

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

	public static final String VTS_NAME = "P002220G.vts"; // 模板名称

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("AG数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("AG传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("AG数据打印失败!");
			return false;
		}

		// 打印人名清单数据处理
		if (!bulidBill()) {
			mErrors.addOneError("AG人名清单数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("AG准备数据失败!");
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
		if (!mOperate.equals("PRINTAG")) {
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
		xmlexport.addDisplayControl("displayAG");

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
		strsql = "select count(distinct contno),sum(getmoney) from ljsgetendorse"
				+ " where EndorsementNo = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' "
				+ " and FeeOperationType = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' "
				+ " and grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' ";

		tssrs = texesql.execSQL(strsql);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "查询失败");
			return false;
		}
		mTextTag.add("AG.SumPeople", tssrs.GetText(1, 1)); // 总领取人数
		mTextTag.add("AG.SumMoney", BqNameFun.chgMoney(tssrs.GetText(1, 2))); // 总领取金额

		String[] allArr = BqNameFun
				.getAllNames(tLCGrpContSchema.getAgentCode());
		mTextTag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate());
		mTextTag.add("ApproveOperator", mLPGrpEdorItemSchema
				.getApproveOperator());
		mTextTag.add("Operator", mLPGrpEdorItemSchema.getOperator());
		mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		mTextTag.add("LAAgent.AgentCode", tLCGrpContSchema.getAgentCode());
		xmlexport.addDisplayControl("displayTail");

		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean bulidBill() {

		XmlExport aXmlExport = new XmlExport();
		aXmlExport.createDocument(VTS_NAME, "printer");

		TextTag aTextTag = new TextTag();

		LCGrpContDB aLCGrpContDB = new LCGrpContDB();
		LCGrpContSchema aLCGrpContSchema = new LCGrpContDB();
		aLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());

		if (!aLCGrpContDB.getInfo()) {
			CError.buildErr(this, "原保单信息查询失败!");
			return false;
		}

		aLCGrpContSchema = aLCGrpContDB.getSchema();
		String[] allArr = BqNameFun
				.getAllNames(aLCGrpContSchema.getAgentCode());
		aTextTag.add("AGbill.GrpName", aLCGrpContSchema.getGrpName());
		aTextTag.add("AGbill.EdorNo", mLPGrpEdorItemSchema.getEdorNo());
		aTextTag.add("AGbill.GrpContNo", mLPGrpEdorItemSchema.getGrpContNo());
		aTextTag.add("AGbill.ValiDate", mLPGrpEdorItemSchema.getEdorValiDate());
		aTextTag.add("AGbill.Operator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getOperator()));
		aTextTag.add("AGbill.ApproveOperator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getOperator()));
		aTextTag.add("AGbill.AgentName", allArr[BqNameFun.AGENT_NAME]);
		aTextTag.add("AGbill.AgentCode", aLCGrpContSchema.getAgentCode());

		SSRS tSSRS = new SSRS();
		ExeSQL tES = new ExeSQL();
		Hashtable riskTable = new Hashtable();
		String[] tContListTitle = new String[16];
		for (int iTitle = 0; iTitle < 16; iTitle++) {
			tContListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;

		// 查询被保人的信息及领取信息
		String SQL = "select (select CustomerSeqNo from lcinsured where contno = a.contno and insuredno = a.insuredno) yx,"
				+ " a.insuredname,"
				+ " (select codename from ldcode where codetype = 'sex' and code = a.InsuredSex),"
				+ " a.insuredbirthday,"
				+ " (select codename from ldcode where codetype = 'idtype' and code = (select insuredidtype from lccont where contno = a.contno)),"
				+ " (select InsuredIDNo from lccont where contno = a.contno),"
				+ " (select getdutyname from lmdutyget where getdutycode = b.getdutycode),"
				+ " b.GetStartDate,"
				+ " (select getdutyname from lmdutygetalive where getdutycode = b.getdutycode and getdutykind = b.getdutykind),"
				+ " a.GetYear,"
				+ " b.StandMoney,"
				+ " case when a.GetForm is null then '无' else (select codename from ldcode where codetype = 'getlocation' and code = a.GetForm) end,"
				+ " case when a.GetBankCode is null then '无' else (select bankname from ldbank where bankcode = a.GetBankCode) end,"
				+ " nvl(a.GetBankAccNo,'无'),"
				+ " nvl(a.GetAccName,'无'),"
				+ " b.managecom"
				+ " from lppol a,lpget b where 1=1 "
				+ " and a.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "'"
				+ " and a.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "'"
				+ " and a.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "'"
				+ " and a.edorno = b.edorno"
				+ " and a.polno = b.polno"
				+ " order by yx asc";
		tSSRS = tES.execSQL(SQL);
		if (tSSRS == null || tSSRS.MaxRow < 1) {
			CError.buildErr(this, "查询被保人保全信息失败!");
			return false;
		}
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			strLine = new String[16];
			for (int j = 0; j <= 15; j++) {
				if (j == 10) {
					strLine[j] = BqNameFun.getRound(tSSRS.GetText(i, j + 1));// 金额
				} else {
					strLine[j] = tSSRS.GetText(i, j + 1);
				}
			}

			// 添加到小计处理
			if (!BqNameFun.dealHashTable(riskTable, "SumMoney", strLine[10])
					|| !BqNameFun.dealHashTable(riskTable, "PpCount", "1")) {
				return false;
			}
			tContListTable.add(strLine);
		}
		aTextTag.add("SumMoney", BqNameFun.getRoundMoney(riskTable
				.get("SumMoney") == null ? "0.00" : (String) riskTable
				.get("SumMoney")));
		aTextTag.add("PpCount", BqNameFun.formatDoubleToInt(riskTable
				.get("PpCount") == null ? "0" : (String) riskTable
				.get("PpCount")));

		aXmlExport.addListTable(tContListTable, tContListTitle);
		aXmlExport.addTextTag(aTextTag);

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

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
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
