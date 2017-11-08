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
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author not attributable
 * @version 6.0
 */
public class GrpEdorBCPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorBCPrintBL.class);
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
	public static final String VTS_NAME = "GrpEdorNameListBC.vts"; // 模板名称

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		// logger.debug("Start preparing the data to print ====>" +
		// mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("BC数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("BC传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("BC数据打印失败!");
			return false;
		}

		// 打印人名清单数据处理
		if (!bulidBill()) {
			mErrors.addOneError("BC人名清单数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("BC准备数据失败!");
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
		if (!mOperate.equals("PRINTBC")) {
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

		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		strsql = "select count(*) " + " from LPEdorItem a "
				+ " where a.edorno = '" + mLPGrpEdorItemSchema.getEdorNo()
				+ "' " + " and a.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' "
				+ " and a.grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "' ";
		tssrs = texesql.execSQL(strsql);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "查询被保人信息变更人数查询失败");
			return false;
		}
		mTextTag.add("BC.GrpNo", tssrs.GetText(1, 1)); // 被保人变更人数

		xmlexport.addDisplayControl("displayBC");

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
			CError.buildErr(this, "查询原保单信息失败!");
			return false;
		}
		tLCGrpContSchema = tLCGrpContDB.getSchema();

		String[] allArr = BqNameFun
				.getAllNames(tLCGrpContSchema.getAgentCode());
		texttag.add("BCbill.GrpName", tLCGrpContSchema.getGrpName()); // 现单位名称
		texttag.add("BCbill.EdorNo", mLPGrpEdorItemSchema.getEdorNo()); // 团体批单号
		texttag.add("BCbill.GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号
		texttag.add("BCbill.ValiDate", mLPGrpEdorItemSchema.getEdorValiDate()); // 生效日
		texttag.add("BCbill.Operator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getOperator())); // 经办人
		texttag.add("BCbill.ApproveOperator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getApproveOperator())); // 复核人
		texttag.add("BCbill.AgentName", allArr[BqNameFun.AGENT_NAME]); // 业务员姓名
		texttag.add("BCbill.AgentCode", tLCGrpContSchema.getAgentCode()); // 业务员编号

		// XinYQ modified on 2007-03-21 : 完全重写查询语句, 避免重复打印同条记录并显示法定受益人
		String QuerySQL = new String("");
		QuerySQL = "select z.CustomerSeqNo, "
				+ "z.InsuredNo, "
				+ "z.InsuredName, "
				+ "z.InsuredIDType, "
				+ "z.InsuredIDNo, "
				+ "z.InsuredSex, "
				+ "z.InsuredBirthday, "
				+ "z.BnfName, "
				+ "z.BnfIDType, "
				+ "z.BnfIDNo, "
				+ "z.BnfGrade, "
				+ "z.BnfLot "
				+ "from (select b.CustomerSeqNo as CustomerSeqNo, "
				+ "b.InsuredNo as InsuredNo, "
				+ "b.Name as InsuredName, "
				+ "(select CodeName "
				+ "from LDCode "
				+ "where 1 = 1 "
				+ "and CodeType = 'idtype' "
				+ "and Code = b.IDType) as InsuredIDType, "
				+ "b.IDNo as InsuredIDNo, "
				+ "(select CodeName "
				+ "from LDCode "
				+ "where 1 = 1 "
				+ "and CodeType = 'sex' "
				+ "and Code = b.Sex) as InsuredSex, "
				+ "b.Birthday as InsuredBirthday, "
				+ "a.Name as BnfName, "
				+ "(select CodeName "
				+ "from LDCode "
				+ "where 1 = 1 "
				+ "and CodeType = 'idtype' "
				+ "and Code = a.IDType) as BnfIDType, "
				+ "a.IDNo as BnfIDNo, "
				+ "a.BnfGrade as BnfGrade, "
				+ "(case when a.BnfLot = 1 then to_char(a.BnfLot) else '0' || a.BnfLot end) as BnfLot "
				+ "from LPBnf a, LCInsured b " + "where 1 = 1 " + "and exists "
				+ "(select 'X' " + "from LPEdorItem " + "where 1 = 1 "
				+ "and EdorNo = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' "
				+ "and EdorType = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' "
				+ "and PolNo = a.PolNo "
				+ "and InsuredNo = a.InsuredNo) "
				+ "and a.EdorNo = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' "
				+ "and a.EdorType = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' "
				+ "and b.InsuredNo = a.InsuredNo "
				+ "and b.ContNo = a.ContNo "
				+ "union "
				+ "select b.CustomerSeqNo as CustomerSeqNo, "
				+ "b.InsuredNo as InsuredNo, "
				+ "b.Name as InsuredName, "
				+ "(select CodeName "
				+ "from LDCode "
				+ "where 1 = 1 "
				+ "and CodeType = 'idtype' "
				+ "and Code = b.IDType) as InsuredIDType, "
				+ "b.IDNo as InsuredIDNo, "
				+ "(select CodeName "
				+ "from LDCode "
				+ "where 1 = 1 "
				+ "and CodeType = 'sex' "
				+ "and Code = b.Sex) as InsuredSex, "
				+ "b.Birthday as InsuredBirthday, "
				+ "'法定' as BnfName, "
				+ "'' as BnfIDType, "
				+ "'' as BnfIDNo, "
				+ "'' as BnfGrade, "
				+ "'' as BnfLot "
				+ "from LCBnf a, LCInsured b "
				+ "where 1 = 1 "
				+ "and exists "
				+ "(select 'X' "
				+ "from LPEdorItem "
				+ "where 1 = 1 "
				+ "and EdorNo = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' "
				+ "and EdorType = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' "
				+ "and PolNo = a.PolNo "
				+ "and InsuredNo = a.InsuredNo) "
				+ "and not exists "
				+ "(select 'X' "
				+ "from LPBnf "
				+ "where 1 = 1 "
				+ "and EdorNo = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' "
				+ "and EdorType = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' "
				+ "and PolNo = a.PolNo "
				+ "and InsuredNo = a.InsuredNo) "
				+ "and b.InsuredNo = a.InsuredNo "
				+ "and b.ContNo = a.ContNo) z "
				+ "order by z.CustomerSeqNo asc, z.InsuredNo asc";
		// logger.debug(QuerySQL);

		SSRS tssrs1 = new SSRS();
		ExeSQL texesql = new ExeSQL();
		tssrs1 = texesql.execSQL(QuerySQL);
		if (tssrs1 == null || tssrs1.getMaxRow() <= 0) {
			CError.buildErr(this, "查询被保人信息变更查询失败");
			return false;
		}

		String[] tContListTitle = new String[15];
		for (int iTitle = 0; iTitle < 15; iTitle++) {
			tContListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;

		int j;// 计数
		// String cSex;// 性别
		for (int i = 1; i <= tssrs1.getMaxRow(); i++) {
			strLine = new String[15];
			for (j = 0; j < 12; j++) {

				strLine[j] = tssrs1.GetText(i, j + 1);
			}
			tContListTable.add(strLine);
		}
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
