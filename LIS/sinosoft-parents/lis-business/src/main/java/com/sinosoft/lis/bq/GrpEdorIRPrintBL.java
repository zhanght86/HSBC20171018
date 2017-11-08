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
public class GrpEdorIRPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorIRPrintBL.class);
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

	public static final String VTS_NAME = "P002400.vts"; // 模板名称

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("IR数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("IR传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("IR数据打印失败!");
			return false;
		}

		// 打印人名清单数据处理
		if (!bulidBill()) {
			mErrors.addOneError("IR人名清单数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("IR准备数据失败!");
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
		if (!mOperate.equals("PRINTIR")) {
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
		xmlexport.addDisplayControl("displayIR");

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
		strsql = "select count(*) from lpedoritem a " + " where a.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' "
				+ " and a.edortype = '" + mLPGrpEdorItemSchema.getEdorType()
				+ "' " + " and a.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "' ";
		tssrs = texesql.execSQL(strsql);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "更换被保险人人数查询失败");
			return false;
		}
		mTextTag.add("IR.GrpNo", tssrs.GetText(1, 1)); // 被保人变更人数

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
		texttag.add("IRbill.GrpName", tLCGrpContSchema.getGrpName()); // 现单位名称
		texttag.add("IRbill.EdorNo", mLPGrpEdorItemSchema.getEdorNo()); // 团体批单号
		texttag.add("IRbill.GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号
		texttag.add("IRbill.ValiDate", mLPGrpEdorItemSchema.getEdorValiDate()); // 生效日
		texttag.add("IRbill.Operator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getOperator())); // 经办人
		texttag.add("IRbill.ApproveOperator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getApproveOperator())); // 复核人
		texttag.add("IRbill.AgentName", allArr[BqNameFun.AGENT_NAME]); // 业务员姓名
		texttag.add("IRbill.AgentCode", tLCGrpContSchema.getAgentCode()); // 业务员编号

		String strsql1;
		strsql1 = "select a.CustomerSeqNo ,"
				+ "       a.InsuredNo,"
				+ "       (select Name from LCInsured where a.contNo = contNo),"
				+ "       a.Name,"
				+ "       (select CodeName from ldcode where codetype = 'idtype' and code = a.idtype),"
				+ "       a.IDNo,"
				+ "       (select codename from ldcode where codetype = 'sex' and code = a.sex),"
				+ "       a.birthday," + "       b.PostalAddress,"
				+ "       b.ZipCode," + "       b.Phone,"
				+ "       a.OccupationType," + "       a.ManageCom,"
				+ "       a.ContNo" + "  from LPInsured a, LPAddress b"
				+ " where a.edorno = '" + mLPGrpEdorItemSchema.getEdorNo()
				+ "'" + "   and a.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "'"
				+ "   and a.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "'"
				+ "   and a.edorno = b.edorno"
				+ "   and a.edortype = b.edortype"
				+ "   and a.addressno = b.addressno"
				+ "   and a.insuredno = b.customerno"
				+ "   order by a.CustomerSeqNo asc";
		SSRS tssrs1 = new SSRS();
		ExeSQL texesql = new ExeSQL();
		tssrs1 = texesql.execSQL(strsql1);

		if (tssrs1 == null || tssrs1.getMaxRow() <= 0) {
			CError.buildErr(this, "更换被保险人信息查询失败");
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
		for (int i = 1; i <= tssrs1.getMaxRow(); i++) {
			strLine = new String[15];
			for (j = 0; j < 12; j++) {
				strLine[j] = tssrs1.GetText(i, j + 1);
			}
			strLine[14] = tssrs1.GetText(i, 13);

			String strsqll2 = "";// 查询 "领取年龄"、"领取方式" 字段
			strsqll2 = "select p.getyear,(select getdutyname from lmdutygetalive where getdutycode = g.getdutycode and getdutykind = g.getdutykind) from lppol p, lpget g"// Modified
																																											// By
																																											// QianLy
																																											// 2006-9-27
					+ " where p.edorno = g.edorno "
					+ " and p.edortype = g.edortype "
					+ " and p.edorno = '"
					+ mLPGrpEdorItemSchema.getEdorNo()
					+ "' "
					+ " and p.edortype = '"
					+ mLPGrpEdorItemSchema.getEdorType()
					+ "' "
					+ " and p.polno = g.polno "
					+ " and exists (select 1 "
					+ " from lmdutyget "
					+ " where getdutycode = g.getdutycode "
					+ " and gettype2 = '1') "
					+ " and p.ContNo = '"
					+ tssrs1.GetText(i, 14) + "'";

			SSRS tssrs12 = new SSRS();
			ExeSQL texesql2 = new ExeSQL();
			tssrs12 = texesql2.execSQL(strsqll2);

			if (tssrs12 != null && tssrs12.getMaxRow() > 0) {// 添加
																// "领取年龄"、"领取方式"
																// 字段
				strLine[12] = tssrs12.GetText(1, 1);
				strLine[13] = tssrs12.GetText(1, 2);
			} else {
				strLine[12] = "";
				strLine[13] = "";
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
