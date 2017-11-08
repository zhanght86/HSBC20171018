package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCGrpAddressDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDGrpDB;
import com.sinosoft.lis.db.LPGrpAddressDB;
import com.sinosoft.lis.db.LPGrpContDB;
import com.sinosoft.lis.db.LPGrpDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LDGrpSchema;
import com.sinosoft.lis.schema.LPGrpAddressSchema;
import com.sinosoft.lis.schema.LPGrpContSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpSchema;
import com.sinosoft.lis.vschema.LCGrpAddressSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: 投保单位信息变更批单打印类
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
 * @Rewrited By QianLy on 2006-10-9
 */
public class GrpEdorANPrintBL implements EdorPrint {
private static Logger logger = Logger.getLogger(GrpEdorANPrintBL.class);
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

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("AN数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("AN传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("AN数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("AN准备数据失败!");
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
		if (!mOperate.equals("PRINTAN")) {
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
		xmlexport.addDisplayControl("displayAN");

		ExeSQL tes = new ExeSQL();

		String[] tCLT = new String[2];
		for (int iTitle = 0; iTitle < 2; iTitle++) {
			tCLT[iTitle] = "ANbill" + String.valueOf(iTitle);
		}

		ListTable mContListTable = new ListTable();
		mContListTable.setName("ANbill");
		String strLine[] = null;
		// LPGrpCont LCGrpCont-------------------------------
		LPGrpContDB tLPGrpContDB = new LPGrpContDB();
		LPGrpContSchema tLPGrpContSchema = new LPGrpContSchema();
		tLPGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		tLPGrpContDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpContDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		if (!tLPGrpContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLPGrpContSchema = tLPGrpContDB.getSchema();

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
		tLCGrpContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
		if (!tLCGrpContDB.getInfo()) {
			CError.buildErr(this, "查询原保单信息失败!");
			return false;
		}
		tLCGrpContSchema = tLCGrpContDB.getSchema();
		// ----------
		// LPGrp LDGrp----------------------
		LPGrpDB tLPGrpDB = new LPGrpDB();
		LPGrpSchema tLPGrpSchema = new LPGrpSchema();
		tLPGrpDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpDB.setCustomerNo(tLPGrpContSchema.getAppntNo());
		if (!tLPGrpDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取单位信息失败!");
			return false;
		}
		tLPGrpSchema = tLPGrpDB.getSchema();

		LDGrpDB tLDGrpDB = new LDGrpDB();
		LDGrpSchema tLDGrpSchema = new LDGrpSchema();
		tLDGrpDB.setCustomerNo(tLPGrpContSchema.getAppntNo());
		if (!tLDGrpDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取原单位信息失败!");
			return false;
		}
		tLDGrpSchema = tLDGrpDB.getSchema();
		// -----------------
		// LPGrpAddress LCGrpAddress------------------
		LPGrpAddressDB tLPGrpAddressDB = new LPGrpAddressDB();
		LPGrpAddressSchema tLPGrpAddressSchema = new LPGrpAddressSchema();
		tLPGrpAddressDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		tLPGrpAddressDB.setEdorType(mLPGrpEdorItemSchema.getEdorType());
		tLPGrpAddressDB.setAddressNo(tLPGrpContSchema.getAddressNo());
		tLPGrpAddressDB.setCustomerNo(tLPGrpContSchema.getAppntNo());

		if (!tLPGrpAddressDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取客户新信息失败!");
			return false;
		}
		tLPGrpAddressSchema = tLPGrpAddressDB.getSchema();

		LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
		LCGrpAddressSchema tLCGrpAddressSchema = new LCGrpAddressSchema();
		tLCGrpAddressDB.setCustomerNo(tLCGrpContSchema.getAppntNo());
		tLCGrpAddressDB.setAddressNo(tLCGrpContSchema.getAddressNo());
		LCGrpAddressSet tLCGrpAddressSet = tLCGrpAddressDB.query();
		if (tLCGrpAddressSet.size() > 0) {
			tLCGrpAddressSchema = tLCGrpAddressSet.get(1);
		}
		// ------------------

		mTextTag.add("GrpName", tLDGrpSchema.getGrpName()); // 原单位名称
		mTextTag.add("EdorNo", mLPGrpEdorItemSchema.getEdorNo()); // 团体批单号
		mTextTag.add("GrpContNo", mLPGrpEdorItemSchema.getGrpContNo()); // 团体保单号
		// LPGrp和LDGrp中的信息比较，如果不同则输出----------
		if (!StrTool.compareString(tLPGrpSchema.getGrpName(), tLDGrpSchema
				.getGrpName())) {
			if (tLPGrpSchema.getCustomerNo().equals(
					tLDGrpSchema.getCustomerNo())) {
				strLine = new String[2];
				strLine[0] = "单位名称:";
				strLine[1] = tLPGrpSchema.getGrpName();
				mContListTable.add(strLine);
			}
		}
		if (!StrTool.compareString(String.valueOf(tLPGrpSchema.getAsset()),
				String.valueOf(tLDGrpSchema.getAsset()))) {
			strLine = new String[2];
			strLine[0] = "资金总额:";
			strLine[1] = BqNameFun.chgMoney(tLPGrpSchema.getAsset()) + "元";
			mContListTable.add(strLine);
		}
		if (!StrTool.compareString(tLPGrpSchema.getGrpNature(), tLDGrpSchema
				.getGrpNature())) {
			String sql = "select codename from ldcode where codetype = 'grpnature' and code ='"
					+ tLPGrpSchema.getGrpNature() + "'";
			String nature = tes.getOneValue(sql);
			strLine = new String[2];
			strLine[0] = "单位性质:";
			strLine[1] = nature;
			mContListTable.add(strLine);
		}
		if (!StrTool.compareString(tLPGrpSchema.getBusinessType(), tLDGrpSchema
				.getBusinessType())) {
			String sql = "select codename from ldcode where codetype = 'businesstype' and code ='"
					+ tLPGrpSchema.getBusinessType() + "'";
			String bt = tes.getOneValue(sql);
			strLine = new String[2];
			strLine[0] = "行业类别:";
			strLine[1] = bt;
			mContListTable.add(strLine);
		}
		// if (!StrTool.compareString(String.valueOf(tLPGrpSchema.getPeoples()),
		// String.valueOf(tLDGrpSchema.getPeoples()))) {
		// strLine = new String[2];
		// strLine[0] = "员工总数:";
		// strLine[1] = String.valueOf(tLPGrpSchema.getPeoples());
		// mContListTable.add(strLine);
		// }
		if (tLPGrpSchema.getPeoples() != tLDGrpSchema.getPeoples()) {
			strLine = new String[2];
			strLine[0] = "员工总数:";
			strLine[1] = String.valueOf(tLPGrpSchema.getPeoples());
			mContListTable.add(strLine);
		}
		if (!StrTool
				.compareString(tLPGrpSchema.getFax(), tLDGrpSchema.getFax())) {
			strLine = new String[2];
			strLine[0] = "单位传真:";
			strLine[1] = tLPGrpSchema.getFax();
			mContListTable.add(strLine);
		}
		// ---------------
		// LPGrpAddress和LCGrpAddress中的信息进行比较，如果不同则输出---------
		if (!StrTool.compareString(tLPGrpAddressSchema.getGrpAddress(),
				tLCGrpAddressSchema.getGrpAddress())) {
			strLine = new String[2];
			strLine[0] = "单位地址:";
			strLine[1] = tLPGrpAddressSchema.getGrpAddress();
			mContListTable.add(strLine);
		}
		if (!StrTool.compareString(tLPGrpAddressSchema.getGrpZipCode(),
				tLCGrpAddressSchema.getGrpZipCode())) {
			strLine = new String[2];
			strLine[0] = "邮政编码:";
			strLine[1] = tLPGrpAddressSchema.getGrpZipCode();
			mContListTable.add(strLine);
		}
		if (!StrTool.compareString(tLPGrpAddressSchema.getLinkMan1(),
				tLCGrpAddressSchema.getLinkMan1())) {
			strLine = new String[2];
			strLine[0] = "保险联系人一 姓名：";
			strLine[1] = tLPGrpAddressSchema.getLinkMan1();
			mContListTable.add(strLine);
		}
		if (!StrTool.compareString(tLPGrpAddressSchema.getPhone1(),
				tLCGrpAddressSchema.getPhone1())) {
			strLine = new String[2];
			strLine[0] = "保险联系人一 联系电话：";
			strLine[1] = tLPGrpAddressSchema.getPhone1();
			mContListTable.add(strLine);
		}
		if (!StrTool.compareString(tLPGrpAddressSchema.getE_Mail1(),
				tLCGrpAddressSchema.getE_Mail1())) {
			strLine = new String[2];
			strLine[0] = "保险联系人一 EMail：";
			strLine[1] = tLPGrpAddressSchema.getE_Mail1();
			mContListTable.add(strLine);
		}
		if (!StrTool.compareString(tLPGrpAddressSchema.getDepartment1(),
				tLCGrpAddressSchema.getDepartment1())) {
			strLine = new String[2];
			strLine[0] = "保险联系人一 部门：";
			strLine[1] = tLPGrpAddressSchema.getDepartment1();
			mContListTable.add(strLine);
		}
		if (!StrTool.compareString(tLPGrpAddressSchema.getLinkMan2(),
				tLCGrpAddressSchema.getLinkMan2())) {
			strLine = new String[2];
			strLine[0] = "保险联系人二 姓名：";
			strLine[1] = tLPGrpAddressSchema.getLinkMan2();
			mContListTable.add(strLine);
		}
		if (!StrTool.compareString(tLPGrpAddressSchema.getPhone2(),
				tLCGrpAddressSchema.getPhone2())) {
			strLine = new String[2];
			strLine[0] = "保险联系人二 联系电话：";
			strLine[1] = tLPGrpAddressSchema.getPhone2();
			mContListTable.add(strLine);
		}
		if (!StrTool.compareString(tLPGrpAddressSchema.getE_Mail2(),
				tLCGrpAddressSchema.getE_Mail2())) {
			strLine = new String[2];
			strLine[0] = "保险联系人二 EMail：";
			strLine[1] = tLPGrpAddressSchema.getE_Mail2();
			mContListTable.add(strLine);
		}
		if (!StrTool.compareString(tLPGrpAddressSchema.getDepartment2(),
				tLCGrpAddressSchema.getDepartment2())) {
			strLine = new String[2];
			strLine[0] = "保险联系人二 部门：";
			strLine[1] = tLPGrpAddressSchema.getDepartment2();
			mContListTable.add(strLine);
		}
		// --------------------
		// LPGrpCont和LCGrpCont进行比较，如果不同则输出－－－－－－－－－
		if (!StrTool.compareString(tLPGrpContSchema.getGetFlag(),
				tLCGrpContSchema.getGetFlag())) {
			String sql = "select codename from ldcode where codetype = 'paymode' and code = '"
					+ tLPGrpContSchema.getGetFlag() + "'";
			String paymode = tes.getOneValue(sql);
			strLine = new String[2];
			strLine[0] = "付款方式：";
			strLine[1] = paymode;
			mContListTable.add(strLine);
		}
		if (tLPGrpContSchema.getGetFlag().equals("2") || // 现金送款簿
				tLPGrpContSchema.getGetFlag().equals("3") || // 支票
				tLPGrpContSchema.getGetFlag().equals("4") || // 银行转帐（非制返盘）
				tLPGrpContSchema.getGetFlag().equals("6") || // POS收款
				tLPGrpContSchema.getGetFlag().equals("7") || // 银行代扣（制返盘）
				tLPGrpContSchema.getGetFlag().equals("8") || // 邮政业务
				tLPGrpContSchema.getGetFlag().equals("9")) { // 银行收款
			if (!StrTool.compareString(tLPGrpContSchema.getBankCode(),
					tLCGrpContSchema.getBankCode())) {
				String sql = "select bankname from ldbank where bankcode = '"
						+ tLPGrpContSchema.getBankCode() + "'";
				String bankname = tes.getOneValue(sql);
				strLine = new String[2];
				strLine[0] = "开户银行：";
				strLine[1] = bankname;
				mContListTable.add(strLine);
			}
			if (tLPGrpContSchema.getGetFlag().equals("2") || // 现金送款簿
					tLPGrpContSchema.getGetFlag().equals("3") || // 支票
					tLPGrpContSchema.getGetFlag().equals("4") || // 银行转帐（非制返盘）
					tLPGrpContSchema.getGetFlag().equals("6") || // POS收款
					tLPGrpContSchema.getGetFlag().equals("7")) {// 银行代扣（制返盘）
				if (!StrTool.compareString(tLPGrpContSchema.getBankAccNo(),
						tLCGrpContSchema.getBankAccNo())) {
					strLine = new String[2];
					strLine[0] = "帐    号：";
					strLine[1] = tLPGrpContSchema.getBankAccNo();
					mContListTable.add(strLine);
				}
			}
		}
		// ---------------------
		xmlexport.addListTable(mContListTable, tCLT);

		String[] allArr = BqNameFun
				.getAllNames(tLCGrpContSchema.getAgentCode());
		mTextTag.add("EdorValiDate", mLPGrpEdorItemSchema.getEdorValiDate());
		mTextTag.add("ApproveOperator", mLPGrpEdorItemSchema
				.getApproveOperator());
		mTextTag.add("Operator", mLPGrpEdorItemSchema.getOperator());
		mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		mTextTag.add("LAAgent.AgentCode", tLCGrpContSchema.getAgentCode());
		xmlexport.addDisplayControl("displayTail");
		xmlexport.addTextTag(mTextTag);
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
