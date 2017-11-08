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
public class GrpEdorLRPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorLRPrintBL.class);
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
	public static final String VTS_NAME = "P004220.vts"; // 模板名称

	public GrpEdorLRPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("LR数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("LR传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("LR数据打印失败!");
			return false;
		}

		// 打印人名清单数据处理
		if (!bulidBill()) {
			mErrors.addOneError("LR人名清单数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("LR准备数据失败!");
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
		if (!mOperate.equals("PRINTLR")) {
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
		strsql = "select count(*) " + " from LPEdorItem b "
				+ " where b.edorno = '" + mLPGrpEdorItemSchema.getEdorNo()
				+ "' " + " and b.edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' "
				+ " and b.grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "' ";
		// + " and a.grpcontno = b.grpcontno and a.insuredno = b.insuredno ";

		// strsql = "select
		// b.edorreason,a.CustomerSeqNo,a.InsuredNo,a.Name,a.IDNo "
		// + " from LCInsured a,LPEdorItem b "
		// + " where b.edorno = '" + mLPGrpEdorItemSchema.getEdorNo() + "' "
		// + " and b.edortype = '" +mLPGrpEdorItemSchema.getEdorType() + "' "
		// + " and b.grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo() + "' "
		// + " and a.grpcontno = b.grpcontno and a.insuredno = b.insuredno ";

		tssrs = texesql.execSQL(strsql);
		if (tssrs == null || tssrs.getMaxRow() <= 0) {
			CError.buildErr(this, "查询被保人信息变更人数查询失败");
			return false;
		}
		mTextTag.add("LR.Peason", tssrs.GetText(1, 1)); // 补发人数
		// mTextTag.add("LR.Reason", tssrs.GetText(1,1)); //补发原因
		// mTextTag.add("LR.CustomerSeqNo", tssrs.GetText(1,2)); //员工序号
		// mTextTag.add("LR.InsuredNo", tssrs.GetText(1,3)); //客户号
		// mTextTag.add("LR.Name", tssrs.GetText(1,4)); //被保人姓名
		// mTextTag.add("LR.IDNo", tssrs.GetText(1,5)); //被保人证件号

		xmlexport.addDisplayControl("displayLR");

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

		String strsql1;

		strsql1 = "select distinct a.CustomerSeqNo ," + "       a.ContNo,"
				+ "       a.InsuredNo," + "       a.Name," + "       a.IDNo,"
				+ "       b.EdorReason," + "       a.ManageCom "
				+ "  from LCInsured a, LPEdorItem b" + " where b.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "'"
				+ "   and b.edortype = '" + mLPGrpEdorItemSchema.getEdorType()
				+ "'" + "   and b.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "'"
				+ "   and a.grpcontno = b.grpcontno"
				+ "   and a.insuredno = b.insuredno"
				+ "   order by a.CustomerSeqNo asc";

		SSRS tssrs1 = new SSRS();
		ExeSQL texesql = new ExeSQL();
		tssrs1 = texesql.execSQL(strsql1);

		if (tssrs1 == null || tssrs1.getMaxRow() <= 0) {
			CError.buildErr(this, "更换被保险人信息查询失败");
			return false;
		}

		String[] tContListTitle = new String[7];
		for (int iTitle = 0; iTitle < 7; iTitle++) {
			tContListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strLine[] = null;

		int j;// 计数
		for (int i = 1; i <= tssrs1.getMaxRow(); i++) {
			strLine = new String[7];
			for (j = 0; j < 7; j++) {
				strLine[j] = tssrs1.GetText(i, j + 1);
			}
			tContListTable.add(strLine);
		}
		texttag.add("Person", tssrs1.getMaxRow()); // 合计人数
		tXmlExport.addListTable(tContListTable, tContListTitle);
		tXmlExport.addTextTag(texttag);
		// logger.debug("\t@> LR人名清单打印成功");
		// tXmlExport.outputDocumentToFile("D:\\print\\", "GrpLRNameList");

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
	// mGlobalInput.ManageCom="86210000";
	// mGlobalInput.Operator="001";
	// VData tVData=new VData();
	// tVData.add(mGlobalInput);
	//
	// LPGrpEdorItemDB tLPGrpEdorItemDB=new LPGrpEdorItemDB();
	// tLPGrpEdorItemDB.setEdorAcceptNo("6120070405000002");
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
	// GrpEdorLRPrintBL tGrpEdorPrintBL = new GrpEdorLRPrintBL();
	// if(tGrpEdorPrintBL.submitData(tVData,"PRINTLR")){
	// logger.debug("OK了哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
	// }
	// }

}
