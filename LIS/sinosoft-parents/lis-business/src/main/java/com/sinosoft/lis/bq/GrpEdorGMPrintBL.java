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
 * Title: 团体领取方式变更批单人名清单打印类
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
 * @Rewrited By QianLy on 2006-10-11
 */
public class GrpEdorGMPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorGMPrintBL.class);
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
	public static final String VTS_NAME = "P002380.vts"; // 模板名称

	public GrpEdorGMPrintBL() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("GM数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("GM传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("GM数据打印失败!");
			return false;
		}

		// 打印人名清单数据处理
		if (!bulidBill()) {
			mErrors.addOneError("GM人名清单数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("GM准备数据失败!");
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
		if (!mOperate.equals("PRINTGM")) {
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

		ExeSQL tes = new ExeSQL();
		String strsql = "";
		strsql = "select count(*) " + " from LPEdorItem " + " where edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' " + " and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' "
				+ " and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "' ";
		String count = tes.getOneValue(strsql);

		mTextTag.add("GM.No", count); // 人数
		xmlexport.addDisplayControl("displayGM");

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
		texttag.add("GMbill.GrpName", tLCGrpContSchema.getGrpName()); // 现单位名称
		texttag.add("GMbill.EdorNo", mLPGrpEdorItemSchema.getEdorNo()); // 团体批单号
		texttag.add("GMbill.GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号
		texttag.add("GMbill.ValiDate", mLPGrpEdorItemSchema.getEdorValiDate()); // 生效日
		texttag.add("GMbill.Operator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getOperator())); // 经办人
		texttag.add("GMbill.ApproveOperator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getApproveOperator())); // 复核人
		texttag.add("GMbill.AgentName", allArr[BqNameFun.AGENT_NAME]); // 业务员姓名
		texttag.add("GMbill.AgentCode", tLCGrpContSchema.getAgentCode()); // 业务员编号

		String strsql1 = "";
		strsql1 = "select a.CustomerSeqNo,a.InsuredNo,a.Name,(Select codename From ldcode Where codetype = 'idtype' And code = a.idtype),a.IDNo,(Select codename From ldcode Where codetype = 'sex' And code = a.sex),a.birthday,(select ParamsName from LMRiskParamsDef where ParamsType = 'getdutykind' and (Paramscode,dutycode) = (Select trim(Getdutykind),dutycode From Lpget c Where c.edorno = b.edorno And c.edortype = b.edortype And c.polno=b.polno and c.contno = b.contno) and rownum = 1), "
				+ " (select getstartdate from lcget where edorno = b.edorno and grpcontno = b.grpcontno and contno = b.contno and rownum = 1) "
				+ " from LCInsured a,LPEdorItem b "
				+ " where b.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo()
				+ "' "
				+ " and b.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType()
				+ "' "
				+ " and b.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo()
				+ "' "
				+ " and a.grpcontno = b.grpcontno"
				+ " and a.contno = b.contno"
				+ " and a.insuredno = b.insuredno "
				+ " order by a.CustomerSeqNo asc";

		SSRS tssrs1 = new SSRS();
		ExeSQL texesql = new ExeSQL();
		tssrs1 = texesql.execSQL(strsql1);
		if (tssrs1 == null || tssrs1.getMaxRow() <= 0) {
			CError.buildErr(this, "查询被保人信息变更查询失败");
			return false;
		}

		String[] tContListTitle = new String[9];
		for (int iTitle = 0; iTitle < 9; iTitle++) {
			tContListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;

		int j;// 计数
		for (int i = 1; i <= tssrs1.getMaxRow(); i++) {
			strLine = new String[9];
			for (j = 0; j < 9; j++) {
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

	private void jbInit() throws Exception {
	}
}
