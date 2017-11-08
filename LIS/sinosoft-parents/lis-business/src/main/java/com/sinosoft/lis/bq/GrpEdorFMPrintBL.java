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
 * Title: 团体缴费方式及期限变更批单和人名清单打印类
 * </p>
 * 
 * <p>
 * Description: 团体保全
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: SinoSoft Co. Ltd
 * </p>
 * 
 * @author QianLy
 * @version 6.0
 * @Rewrited By QianLy on 2006-10-10
 */
public class GrpEdorFMPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorFMPrintBL.class);
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
	public static final String VTS_NAME = "P002320.vts"; // 模板名称

	public GrpEdorFMPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("FM数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("FM传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("FM数据打印失败!");
			return false;
		}

		// 打印人名清单数据处理
		if (!buildBill()) {
			mErrors.addOneError("FM人名清单数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("FM准备数据失败!");
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
		if (!mOperate.equals("PRINTFM")) {
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
		ExeSQL tExeSQL = new ExeSQL();
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
		// Modified By QianLy on 2006-10-10--------
		String strsql = "select nvl(sum(getmoney),0) " + " from Ljsgetendorse"
				+ " where endorsementno = '" + mLPGrpEdorItemSchema.getEdorNo()
				+ "' " + " and feeoperationtype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' "
				+ " and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo()
				+ "' " + "";
		// 张涛说即使有多主险,变更后的方式也要一致,所以只查一条即可 2007-01-22 QianLy
		String sgrppol = "select grppolno from lpgrppol where 1 = 1"
				+ " and edorno = '" + mLPGrpEdorItemSchema.getEdorNo() + "' "
				+ " and edortype = '" + mLPGrpEdorItemSchema.getEdorType()
				+ "' " + " and grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "' "
				+ " and rownum = 1";
		// ---------------
		SSRS tSSRS = new SSRS();
		String gmoney = tExeSQL.getOneValue(strsql);
		if (gmoney == null || gmoney.equals("")) {
			gmoney = "0";
		}
		String GrpPolNo = tExeSQL.getOneValue(sgrppol);
		if (GrpPolNo == null || "".equals(GrpPolNo)) {
			CError.buildErr(this, "未查到做过交费期限变更的险种.");
			return false;
		}
		strsql = "select (case when a.payintv = '0' then '趸交'"
				+ "        else to_char(a.payendyear) || '年交' end)"
				+ " from (select payintv, payendyear" + "         from lppol"
				+ "         where grppolno = '" + GrpPolNo + "'"
				+ "          and edorno = '" + mLPGrpEdorItemSchema.getEdorNo()
				+ "'" + "          and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "'"
				+ "          and rownum = 1) a" + "";
		mTextTag.add("FM.Getmode", tExeSQL.getOneValue(strsql));// 缴费方式
		strsql = "select count(1) from lppol " + " where grppolno = '"
				+ GrpPolNo + "'" + " and edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' " + " and edortype = '"
				+ mLPGrpEdorItemSchema.getEdorType() + "' ";
		mTextTag.add("FM.GetNum", tExeSQL.getOneValue(strsql));// 人
		mTextTag.add("FM.Getmoney", gmoney);// 补费金额

		xmlexport.addDisplayControl("displayFM");
		xmlexport.addDisplayControl("displayTail");
		String[] allArr = BqNameFun
				.getAllNames(tLCGrpContSchema.getAgentCode());
		mTextTag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate());
		mTextTag.add("ApproveOperator", mLPGrpEdorItemSchema
				.getApproveOperator());
		mTextTag.add("Operator", mLPGrpEdorItemSchema.getOperator());
		mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		mTextTag.add("LAAgent.AgentCode", tLCGrpContSchema.getAgentCode());
		xmlexport.addTextTag(mTextTag);

		return true;
	}

	private boolean buildBill() {
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
		texttag.add("FMbill.GrpName", tLCGrpContSchema.getGrpName()); // 现单位名称
		texttag.add("FMbill.EdorNo", mLPGrpEdorItemSchema.getEdorNo()); // 团体批单号
		texttag.add("FMbill.GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号
		texttag.add("FMbill.ValiDate", mLPGrpEdorItemSchema.getEdorValiDate()); // 生效日
		texttag.add("FMbill.Operator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getOperator())); // 经办人
		texttag.add("FMbill.ApproveOperator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getApproveOperator())); // 复核人
		texttag.add("FMbill.AgentName", allArr[BqNameFun.AGENT_NAME]); // 业务员姓名
		texttag.add("FMbill.AgentCode", tLCGrpContSchema.getAgentCode()); // 业务员编号
		// Modified By QianLy on 2006-10-10-------------------
		String strsql1 = "";
		strsql1 = "select a.customerseqno,"
				+ " a.insuredno,"
				+ " a.Name,"
				+ " (Select codename From ldcode Where codetype = 'idtype' And code = a.idtype),"
				+ " a.IDNo,"
				+ " (Select codename From ldcode Where codetype = 'sex' And code = a.sex),"
				+ " a.birthday,"
				+ " (select ParamsName from LMRiskParamsDef Where paramstype = 'payintv' and ParamsCode = to_char(b.payintv) and riskcode = b.riskcode),"
				+ " (select nvl(sum(getmoney), 0) from Ljsgetendorse Where endorsementno = b.edorno and feeoperationtype = b.edortype and polno = b.polno)"
				+ " from LCInsured a, lppol b" + " where b.edorno = '"
				+ mLPGrpEdorItemSchema.getEdorNo() + "' "
				+ " and b.edortype = '" + mLPGrpEdorItemSchema.getEdorType()
				+ "' " + " and b.grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "' "
				+ " and a.insuredno = b.insuredno"
				+ " order by a.customerseqno asc";
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

		double AllFee = 0.00;
		int j;// 计数
		for (int i = 1; i <= tssrs1.getMaxRow(); i++) {
			strLine = new String[9];
			for (j = 0; j < 9; j++) {
				strLine[j] = String.valueOf(tssrs1.GetText(i, j + 1));
			}
			AllFee += Double.parseDouble(strLine[8]);
			tContListTable.add(strLine);
		}
		texttag.add("FMbill.AllPeople", String.valueOf(tssrs1.getMaxRow())); // 总人数
		// -------------------
		texttag.add("FMbill.AllFee", BqNameFun.chgMoney(AllFee) + "元"); // 总金额
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
