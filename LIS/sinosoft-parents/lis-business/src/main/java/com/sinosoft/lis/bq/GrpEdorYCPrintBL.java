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
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LPEdorPrint2Schema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.vschema.LCInsuredSet;
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
 * Title 团体保险起期提前变更批单人名清单打印类
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author not attributable
 * @version 1.0
 */
public class GrpEdorYCPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorYCPrintBL.class);
	// 公共数据
	private VData mResult = new VData();

	public CErrors mErrors = new CErrors();

	// 全局变量
	private VData mInputData;

	private GlobalInput mGlobalInput;

	private LPGrpEdorItemSchema mLPGrpEdorItemSchema;

	private XmlExport xmlexport;

	private String mPeopleCount = "";

	public static final String VTS_NAME = "GrpEdorNameListYC.vts"; // 人名清单模板名称

	public GrpEdorYCPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		// 数据传输
		if (!getInputData()) {
			return false;
		}

		// 数据校验
		if (!checkData()) {
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			return false;
		}

		// 打印人名清单数据处理
		if (!bulidBill()) {
			return false;
		}

		// 准备数据
		if (!prepareData()) {
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
		if (mLPGrpEdorItemSchema == null || xmlexport == null
				|| mGlobalInput == null) {
			CError.buildErr(this, "没有足够的处理数据！");
			return false;
		}
		return true;
	}

	private boolean checkData() {

		LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
		LPGrpEdorItemSet tLPGrpEdorItemSet = new LPGrpEdorItemSet();
		tLPGrpEdorItemDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpEdorItemDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
		if (tLPGrpEdorItemSet.size() < 1) {
			CError.buildErr(this, "没有得到团体保全数据！");
			return false;
		}
		mLPGrpEdorItemSchema.setSchema(tLPGrpEdorItemSet.get(1));

		return true;
	}

	private boolean dealData() {
		TextTag mTextTag = new TextTag();

		// 头部信息
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

		// 其他信息(根据保全项目改变)
		xmlexport.addDisplayControl("displayYC");
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
			CError.buildErr(this, "保险期间变更补退费共计查询失败");
			return false;
		}
		mTextTag.add("SumMoney", sp); // 补退费共计

		strsql = "select count(distinct InsuredNo) " + " from lpedoritem  "
				+ " where edorno = '" + mLPGrpEdorItemSchema.getEdorNo() + "' "
				+ " and edortype = '" + mLPGrpEdorItemSchema.getEdorType()
				+ "' " + " and grpcontno = '"
				+ mLPGrpEdorItemSchema.getGrpContNo() + "' "
				+ " and EdorAcceptNo='"
				+ mLPGrpEdorItemSchema.getEdorAcceptNo() + "'";
		mPeopleCount = texesql.getOneValue(strsql);
		if (mPeopleCount == null || mPeopleCount.equals("")) {
			CError.buildErr(this, "变更人数查询失败");
			return false;
		}
		mTextTag.add("PeopleCount", mPeopleCount); // 保险期间变更变更人数

		// 其他信息(根据保全项目改变)

		// 尾部信息

		xmlexport.addDisplayControl("displayTail");
		String[] allArr = BqNameFun
				.getAllNames(tLCGrpContSchema.getAgentCode());
		mTextTag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate());
		mTextTag.add("ApproveOperator", mLPGrpEdorItemSchema
				.getApproveOperator());
		mTextTag.add("Operator", mLPGrpEdorItemSchema.getOperator());
		mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		mTextTag.add("LAAgent.AgentCode", tLCGrpContSchema.getAgentCode());
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
			return false;
		}
		xmlexport.addTextTag(mTextTag);
		return true;
	}

	private boolean bulidBill() {
		// 人名清单
		XmlExport tXmlExport = new XmlExport();
		TextTag texttag = new TextTag();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "查询原保单信息失败!");
			return false;
		}

		ExeSQL texesql = new ExeSQL();
		// 先根据团单下的一个被保人的LCInsured表中的ContPlanCode判断这张团单是按险种组合方式还是非险种组合方式
		LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
		tLCInsuredSchema.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		LCInsuredSet tLCInsuredSet = tLCInsuredSchema.getDB().query();
		if (tLCInsuredSet == null || tLCInsuredSet.size() <= 0) {
			CError.buildErr(this, "没有被保人信息！");
			return false;
		}
		// 查询得到该团单下的一个被保人
		tLCInsuredSchema = tLCInsuredSet.get(1);

		int VTS_COLUMN = 9; // 模板中的列数
		String[] tContListTitle = new String[VTS_COLUMN];
		for (int iTitle = 0; iTitle < VTS_COLUMN; iTitle++) {
			tContListTitle[iTitle] = "Bill" + String.valueOf(iTitle);
		}

		ListTable tContListTable = new ListTable();
		tContListTable.setName("BILL");
		String strsql = ""; // 表格数据查询sql
		String strsqlTotal = ""; // 小计部分的sql
		// 非险种组合方式，输出保单项下参与变更的险种信息
		if (tLCInsuredSchema.getContPlanCode() == null
				|| tLCInsuredSchema.getContPlanCode().equals("")) {
			strsql = " select  lpi.CustomerSeqNo  ,lpi.contno , lpp.insuredname , "
					+ "  (select   riskcode||'+'||riskshortname from lmrisk where riskcode=lpp.riskcode)  , "
					+ " lpp.cvalidate,lpp.enddate,lpp.prem , "
					+ "nvl( (select getmoney from  ljsgetendorse  lj where lj.endorsementno=lpp.edorno and polno=lpp.polno and lj.Feeoperationtype=lpp.edortype),0), "
					+ " ( select lpa.zipcode from  lcaddress lpa where lpa.customerno=lpi.insuredno and lpa.addressno=lpi.addressno) "
					+ " from lppol lpp ,lcinsured lpi   where 1=1 and lpp.grpcontno ='"
					+ mLPGrpEdorItemSchema.getGrpContNo()
					+ "' "
					+ " and lpp.edorno='"
					+ mLPGrpEdorItemSchema.getEdorNo()
					+ "' and lpp.edortype='"
					+ mLPGrpEdorItemSchema.getEdorType()
					+ "'"
					+ " and    lpi.insuredno=lpp.insuredno and lpi.contno=lpp.contno    order by  lpi.CustomerSeqNo ";

			strsqlTotal = "    select    '--','--','--', riskdesc,'--','--','--' ,sum(moneytoone),'--'  "
					+ "            from ( "
					+ " select  (select   riskcode||'+'||riskshortname from lmrisk where riskcode=lpp.riskcode) riskdesc, "
					+ " nvl((select sum(lj.getmoney) from  ljsgetendorse  lj where lj.endorsementno=lpp.edorno and lj.polno=lpp.polno  "
					+ " and lj.Feeoperationtype=lpp.edortype),0) moneytoone "
					+ " from  lppol lpp where 1=1 and lpp.grpcontno ='"
					+ mLPGrpEdorItemSchema.getGrpContNo()
					+ "' "
					+ " and lpp.edorno='"
					+ mLPGrpEdorItemSchema.getEdorNo()
					+ "' and lpp.edortype='"
					+ mLPGrpEdorItemSchema.getEdorType()
					+ "'"
					+ "  ) a group by  riskdesc ";

		}
		// 险种组合方式，输出其保单项下参与变更的责任信息
		else {
			strsql = " select  lpi.CustomerSeqNo, "
					+ " lpi.contno , lpp.insuredname ,(select dutycode||'+'||dutyname from lmduty where dutycode=lpd.dutycode)  , "
					+ " lpd.cvalidate,lpd.enddate,lpd.prem, "
					+ " nvl((select lj.getmoney from  ljsgetendorse  lj where lj.endorsementno=lpd.edorno and polno=lpd.polno and lj.Feeoperationtype=lpd.edortype and lj.dutycode=lpd.dutycode),0), "
					+ " ( select lpa.zipcode from  lcaddress lpa where lpa.customerno=lpi.insuredno and lpa.addressno=lpi.addressno) "
					+ " from  lpduty lpd ,lcinsured lpi  ,lppol lpp  where 1=1 and lpp.grpcontno = '"
					+ mLPGrpEdorItemSchema.getGrpContNo()
					+ "' "
					+ " and lpp.edorno='"
					+ mLPGrpEdorItemSchema.getEdorNo()
					+ "' and lpp.edortype='"
					+ mLPGrpEdorItemSchema.getEdorType()
					+ "' and    lpi.insuredno=lpp.insuredno "
					+ "   and lpi.contno=lpd.contno  "
					+ " and lpp.polno=lpd.polno and  lpp.edorno=lpd.edorno and lpp.edortype=lpd.edortype order by  lpi.CustomerSeqNo ";

			strsqlTotal = " select   '--' ,'--','--', dutydesc,'--' ,sum(moneytoone) from ( "
					+ " select  (select   dutycode||'+'||dutyname from lmduty where dutycode=lpd.dutycode) dutydesc, "
					+ " nvl((select sum(lj.getmoney) from  ljsgetendorse  lj where lj.endorsementno=lpd.edorno and lj.polno=lpd.polno  "
					+ " and lj.Feeoperationtype=lpd.edortype and lj.dutycode=lpd.dutycode),0) moneytoone "
					+ " from  lpduty lpd where 1=1 and lpd.edortype='YC' and lpd.edorno='"
					+ mLPGrpEdorItemSchema.getEdorNo()
					+ "' ) a group by  dutydesc ";
		}

		SSRS tssrs = new SSRS();
		tssrs = texesql.execSQL(strsql);
		if (tssrs == null || tssrs.MaxRow <= 0) {
			CError.buildErr(this, "没有查询到变更数据！");
			return false;
		}
		// 查询得到小计部分
		SSRS ttSSRS = texesql.execSQL(strsqlTotal);
		for (int i = 1; i <= tssrs.MaxRow; i++) {
			tContListTable.add(tssrs.getRowData(i));
		}

		// 以下把小计部分的第一行第一列置为'小计'
		String[] firstTotalRow = ttSSRS.getRowData(1);
		firstTotalRow[0] = "小计";
		double summoney = 0; // 计算补退费金额合计
		for (int i = 1; i <= ttSSRS.MaxRow; i++) {
			double oneRowMoney = 0;// 每个责任或者险种统计出来的补退费金额
			try {
				oneRowMoney = Double.parseDouble(ttSSRS.GetText(i, 8));
			} catch (Exception ex) {
				oneRowMoney = 0;
			}
			summoney += oneRowMoney;
			if (i != 1) {
				tContListTable.add(ttSSRS.getRowData(i));
			} else {
				tContListTable.add(firstTotalRow);
			}
		}
		tXmlExport.addListTable(tContListTable, tContListTitle);

		// 得到头部和尾部的信息
		String[] allArr = BqNameFun.getAllNames(tLCGrpContDB.getAgentCode());
		texttag.add("GrpName", tLCGrpContDB.getGrpName()); // 现单位名称
		texttag.add("EdorNo", mLPGrpEdorItemSchema.getEdorNo()); // 团体批单号
		texttag.add("GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号
		texttag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate()); // 生效日
		texttag.add("Operator", CodeNameQuery.getOperator(mLPGrpEdorItemSchema
				.getOperator())); // 经办人
		texttag.add("ApproveOperator", CodeNameQuery
				.getOperator(mLPGrpEdorItemSchema.getApproveOperator())); // 复核人
		texttag.add("AgentName", allArr[BqNameFun.AGENT_NAME]); // 业务员姓名
		texttag.add("AgentCode", tLCGrpContDB.getAgentCode()); // 业务员编号
		texttag.add("SumMoney", summoney); // 计算出补退费金额
		texttag.add("PeopleCount", mPeopleCount); // 加进去人数这个字段
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

	public VData getResult() {
		return mResult;
	}

	private boolean prepareData() {
		mResult.add(xmlexport);
		return true;
	}
}
