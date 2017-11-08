package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LYReturnFromBankBDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LABranchGroupSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LYReturnFromBankBSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LYReturnFromBankBSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
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
 * Company: Sinosoft
 * </p>
 * 
 * @author Lizhuo
 * @version 1.0
 */
public class GetBankSuccNoticePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(GetBankSuccNoticePrintBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	public static final String VTS_NAME = "BankSucessP002210.vts";

	private String mEdorAcceptNo;
	private String mContNo;
	private LCContSchema mLCContSchema = new LCContSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();

	public GetBankSuccNoticePrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		if (!mOperate.equals("PRINT")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InfoNoticePrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);

			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		if (!dealData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) mInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null || mLOPRTManagerSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			mErrors.addOneError(new CError("没有得到足够的信息:流水号不能为空！"));
			return false;
		}
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		String mNoType = mLOPRTManagerSchema.getOtherNoType();

		if (mNoType == null || mNoType.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InfoNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取OtherNoType失败！";
			this.mErrors.addOneError(tError);
			return false;
		} else if (!mNoType.trim().equals(PrintManagerBL.ONT_EDORACCEPT)) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InfoNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "错误的OtherNo类型！";
			this.mErrors.addOneError(tError);

			return false;
		}

		mEdorAcceptNo = mLOPRTManagerSchema.getOtherNo();

		if (mEdorAcceptNo == null || mEdorAcceptNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "InfoNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保全受理号失败！";
			this.mErrors.addOneError(tError);

			return false;
		}

		return true;
	}

	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1) {
			CError tError = new CError();
			tError.moduleName = "GetBankSuccNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "获取保全受理信息失败！";
			this.mErrors.addOneError(tError);

		}
		mLPEdorItemSet.clear();
		mLPEdorItemSet.add(tLPEdorItemSet);
		mLPEdorItemSchema = tLPEdorItemSet.get(1);

		mContNo = mLPEdorItemSchema.getContNo();

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("查询保单信息失败!");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();
		return true;
	}

	private boolean dealData() {
		XmlExport tXmlExport = new XmlExport();
		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档

		TextTag texttag = new TextTag();
		ListTable tListTable = new ListTable();
		tListTable.setName("Info");

		LCAppntDB tLCAppntDB = new LCAppntDB();
		tLCAppntDB.setContNo(mLCContSchema.getContNo());
		if (!tLCAppntDB.getInfo()) {
			mErrors.addOneError("查询投保人信息失败!");
			return false;
		}
		LCAppntSchema tLCAppntSchema = new LCAppntSchema();
		tLCAppntSchema = tLCAppntDB.getSchema();

		LCAddressDB tLCAddressDB = new LCAddressDB();
		tLCAddressDB.setAddressNo(tLCAppntSchema.getAddressNo());
		tLCAddressDB.setCustomerNo(tLCAppntSchema.getAppntNo());
		if (!tLCAddressDB.getInfo()) {
			mErrors.addOneError("查询地址信息失败!");
			return false;
		}
		LCAddressSchema tLCAddressSchema = new LCAddressSchema();
		tLCAddressSchema = tLCAddressDB.getSchema();

		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSchema tLAAgentSchema = new LAAgentSchema();
		tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取业务机构信息失败!");
		}
		tLAAgentSchema = tLAAgentDB.getSchema();
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupSchema tLABranchGroupSchema = new LABranchGroupSchema();
		tLABranchGroupDB.setAgentGroup(tLAAgentSchema.getAgentGroup());
		if (!tLABranchGroupDB.getInfo()) {
			CError.buildErr(this, "打印生成数据时，取业务机构信息失败!");
			return false;
		}
		tLABranchGroupSchema = tLABranchGroupDB.getSchema();

		texttag.add("ZipCode", tLCAddressSchema.getZipCode());
		texttag.add("Address", tLCAddressSchema.getPostalAddress());
		String AppntName;
		if (StrTool.compareString(mLCContSchema.getAppntSex(), "0")) {
			AppntName = mLCContSchema.getAppntName() + "先生";
		} else if (StrTool.compareString(mLCContSchema.getAppntSex(), "1")) {
			AppntName = mLCContSchema.getAppntName() + "女士";
		} else {
			AppntName = mLCContSchema.getAppntName();
		}
		texttag.add("AppntName", AppntName);
		texttag.add("AppntName1", AppntName);
		texttag.add("LAAgent.AgentCode", mLCContSchema.getAgentCode()); // 代理人编码
		texttag.add("LABranchGroup.Name", getComName(tLABranchGroupSchema
				.getManageCom(), "N")); // 营销服务部

		String[] allArr = BqNameFun.getAllNames(mLCContSchema.getAgentCode());

		texttag.add("ManageCom", allArr[BqNameFun.SALE_SERVICE]);
		texttag.add("Zone", allArr[BqNameFun.DISTRICT]); // 区
		texttag.add("Dep", allArr[BqNameFun.DEPART]); // 部
		texttag.add("Department", allArr[BqNameFun.DEPART]); // 部
		texttag.add("Group", allArr[BqNameFun.TEAM]); // 组
		texttag.add("Agent", tLAAgentSchema.getName()); // 代理人姓名
		texttag.add("LAAgent.Name", tLAAgentSchema.getName()); // 代理人姓名

		String CenterBranch = getComName(tLABranchGroupSchema.getManageCom()
				.substring(0, 6), "N"); // 中心支公司
		String Com = getComName(tLABranchGroupSchema.getManageCom().substring(
				0, 4), "N"); // 分公司

		texttag.add("ManageComName", Com + CenterBranch);

		String str = "";
		String str1;
		String str2;
		ExeSQL tes = new ExeSQL();

		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
		tLPEdorItemSet = tLPEdorItemDB.query();

		String[] strArray = new String[3];
		if (tLPEdorItemSet.size() > 0) {
			int iRow = tLPEdorItemSet.size() / 3;
			for (int i = 0; i < iRow + 1; i++) {
				strArray = new String[3];
				if (i * 3 + 1 <= tLPEdorItemSet.size()) {
					strArray[0] = tLPEdorItemSet.get(i * 3 + 1).getContNo();
				}
				if (i * 3 + 2 <= tLPEdorItemSet.size()) {
					strArray[1] = tLPEdorItemSet.get(i * 3 + 2).getContNo();
				} else {
					strArray[1] = "";
				}
				if (i * 3 + 3 <= tLPEdorItemSet.size()) {
					strArray[2] = tLPEdorItemSet.get(i * 3 + 3).getContNo();
				} else {
					strArray[2] = "";
				}
				tListTable.add(strArray);
			}
			String[] b_str = new String[3];
			tXmlExport.addListTable(tListTable, b_str);
		}

		texttag.add("EdorAcceptNo", mEdorAcceptNo);
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			str = "select edorname from lmedoritem where edorcode = '"
					+ "?edorcode?" + "' and rownum = '1'";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			str = "select edorname from lmedoritem where edorcode = '"
					+ "?edorcode?" + "' limit 0,1";
		}
		sqlbv1.sql(str);
		sqlbv1.put("edorcode", mLPEdorItemSchema.getEdorType());
		texttag.add("EdorType", tes.getOneValue(sqlbv1));
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		str1 = "select getnoticeno from ljapay where othernotype = '10' and otherno = '"
				+ "?mEdorAcceptNo?" + "'";
		sqlbv2.sql(str1);
		sqlbv2.put("mEdorAcceptNo", mEdorAcceptNo);
		LYReturnFromBankBDB tLYReturnFromBankBDB = new LYReturnFromBankBDB();
		LYReturnFromBankBSet tLYReturnFromBankBSet = new LYReturnFromBankBSet();
		tLYReturnFromBankBDB.setDealType("S");
		String tGetNoticeNo = tes.getOneValue(sqlbv2);
		if (tGetNoticeNo == null || tGetNoticeNo.equals("")) {
			CError.buildErr(this, "查询通知书号失败!");
			return false;
		}
		tLYReturnFromBankBDB.setPayCode(tGetNoticeNo);
		tLYReturnFromBankBDB.setBankSuccFlag("0000");
		tLYReturnFromBankBSet = tLYReturnFromBankBDB.query();
		LYReturnFromBankBSchema tLYReturnFromBankBSchema = new LYReturnFromBankBSchema();
		if (tLYReturnFromBankBSet == null || tLYReturnFromBankBSet.size() < 1) {
			mErrors.addOneError("查询发送银行盘记录备份表失败!");
			return false;
		}
		tLYReturnFromBankBSchema = tLYReturnFromBankBSet.get(1);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		str2 = "select bankname from ldbank where bankcode = '"
				+ "?bankcode?" + "'";
		sqlbv3.sql(str2);
		sqlbv3.put("bankcode", tLYReturnFromBankBSchema.getBankCode());
		texttag.add("BankCode", tes.getOneValue(sqlbv3));
		texttag.add("AccNo", tLYReturnFromBankBSchema.getAccNo());
		texttag.add("PayMoney", String.valueOf(tLYReturnFromBankBSchema
				.getPayMoney()));
		texttag.add("BankDealDate", tLYReturnFromBankBSchema.getBankDealDate());

		texttag.add("Operator", CodeNameQuery.getOperator(mLPEdorItemSchema
				.getOperator()));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}

		// tXmlExport.outputDocumentToFile("d:\\", "P002210"); //测试用
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}

	private String getComName(String aComCode, String aType) {
		String str;
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		if (aType.equals("N")) {
			str = "select name from ldcom where comcode = '" + "?aComCode?"+ "'";
		} else if (aType.equals("A")) {
			str = "select address from ldcom where comcode = '" +"?aComCode?"
					+ "'";
		} else {
			str = "select zipcode from ldcom where comcode = '" + "?aComCode?"
					+ "'";
		}
		sqlbv4.sql(str);
		sqlbv4.put("aComCode", aComCode);
		ExeSQL tES = new ExeSQL();
		return tES.getOneValue(sqlbv4);
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.ManageCom = "86";
		tG.Operator = "lee";
		LOPRTManagerSchema tt = new LOPRTManagerSchema();
		LOPRTManagerDB ttt = new LOPRTManagerDB();
		ttt.setPrtSeq("8100000105096");
		ttt.getInfo();
		tt.setSchema(ttt.getSchema());
		VData td = new VData();

		GetBankSuccNoticePrintBL gt = new GetBankSuccNoticePrintBL();

		td.add(tG);
		td.add(tt);
		gt.submitData(td, "PRINT");

	}

}
