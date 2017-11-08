package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

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
public class BankPayNoSuccNoticePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(BankPayNoSuccNoticePrintBL.class);
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

	private LCContSchema mLCContSchema = new LCContSchema();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private String mEdorAcceptNo;
	private String mContNo;
	public static final String VTS_NAME = "BqfkyhhkwcgsqhP000230.vts";

	public BankPayNoSuccNoticePrintBL() {
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

		texttag.add("Post", tLCAddressSchema.getZipCode());
		texttag.add("Address", tLCAddressSchema.getPostalAddress());
		if (StrTool.compareString(mLCContSchema.getAppntSex(), "0")) {
			texttag.add("AppntName", mLCContSchema.getAppntName() + "先生");
		} else if (StrTool.compareString(mLCContSchema.getAppntSex(), "1")) {
			texttag.add("AppntName", mLCContSchema.getAppntName() + "女士");
		} else {
			texttag.add("AppntName", mLCContSchema.getAppntName());
		}
		texttag.add("AgentCode", mLCContSchema.getAgentCode());

		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSchema tLAAgentSchema = new LAAgentSchema();
		tLAAgentDB.setAgentCode(mLCContSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取业务机构信息失败!");
			return false;
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

		String[] allArr = BqNameFun.getAllNames(mLCContSchema.getAgentCode());

		// texttag.add("ManageCom",allArr[BqNameFun.SALE_SERVICE]);//营销服务部
		texttag.add("Zone", allArr[BqNameFun.DISTRICT]);// 区
		texttag.add("Department", allArr[BqNameFun.DEPART]);// 部
		texttag.add("Group", allArr[BqNameFun.TEAM]);// 组

		// String str11 = "select name from LABranchGroup where agentgroup = '"
		// + tLABranchGroupSchema.getUpBranch() + "'";
		// ExeSQL tSQL = new ExeSQL();
		// texttag.add("Department",tSQL.getOneValue(str11));
		// texttag.add("Group",tLABranchGroupSchema.getName());
		texttag.add("Agent", tLAAgentSchema.getName());
		// String str23 = "select name from LABranchGroup a, LABranchGroup b
		// where b.agentgroup = a.upbranch and a.agentgroup = '" +
		// tLABranchGroupSchema.getUpBranch() + "'";
		// texttag.add("Zone",tSQL.getOneValue(str23));

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
		ExeSQL tes = new ExeSQL();
		texttag.add("EdorAcceptNo", mEdorAcceptNo);
		String str = "";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			 str = "select edorname from lmedoritem where edorcode = '"
					+ "?edorcode?" + "' and rownum = '1'";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 str = "select edorname from lmedoritem where edorcode = '"
					+ "?edorcode?" + "'  limit 0,1";
		}
		sqlbv.sql(str);
		sqlbv.put("edorcode", mLPEdorItemSchema.getEdorType());
		texttag.add("EdorType", tes.getOneValue(sqlbv));

		LYReturnFromBankBDB tLYReturnFromBankBDB = new LYReturnFromBankBDB();
		LYReturnFromBankBSet tLYReturnFromBankBSet = new LYReturnFromBankBSet();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String Str = "select * from LYReturnFromBankB where polno = '"
				+ "?mEdorAcceptNo?"
				+ "' and dealtype = 'F' and banksuccflag <> '0000'";
		sqlbv1.sql(Str);
		sqlbv1.put("mEdorAcceptNo", mEdorAcceptNo);
		tLYReturnFromBankBSet = tLYReturnFromBankBDB.executeQuery(sqlbv1);

		if (tLYReturnFromBankBSet == null || tLYReturnFromBankBSet.size() < 1) {
			mErrors.addOneError("查询保全付费划款不成功保单信息失败!");
			return false;
		}
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		String str2 = "select bankname from ldbank where bankcode = '"
				+ "?bankcode?" + "'";
		sqlbv2.sql(str2);
		sqlbv2.put("bankcode", tLYReturnFromBankBSet.get(1).getBankCode());
		texttag.add("BankCode", tes.getOneValue(sqlbv2));
		texttag.add("AccNo", tLYReturnFromBankBSet.get(1).getAccNo());
		texttag.add("PayMoney", String.valueOf(tLYReturnFromBankBSet.get(1)
				.getPayMoney()));

		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}

		// tXmlExport.outputDocumentToFile("d:\\", "P000230"); //测试用
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;
	}

}
