package com.sinosoft.lis.f1print;

import org.apache.log4j.Logger;

import com.sinosoft.lis.bq.CodeNameQuery;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPCustomerImpartDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPCustomerImpartSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LPCustomerImpartSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 信息通知书打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：pst
 * @version：1.0
 * @CreateDate：2009-02-19
 */
public class InfoNoticePrintBL implements PrintService {
	private static Logger logger = Logger.getLogger(InfoNoticePrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	@SuppressWarnings("unused")
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private XmlExportNew tXmlExport = new XmlExportNew();

	private LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();

	private LCContSchema tLCContSchema = new LCContSchema();

	private String mEdorAcceptNo = ""; // 保全受理号

	private TextTag texttag = new TextTag(); // 新建一个TextTag的实例

	private ListTable tHIListTable = new ListTable();

	// 获得此时的日期和时间
	private String strCurrentDate = PubFun.getCurrentDate();

	public InfoNoticePrintBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据
	 * @param: cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 数据校验操作
		if (!checkData()) {
			return false;
		}

		// 从数据库获得数据
		if (!getBaseData()) {
			return false;
		}

		// 准备需要打印的数据
		if (!preparePrintData()) {
			return false;
		}
		return true;
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
			mErrors.addOneError(new CError("没有得到足够的信息:打印流水号不能为空！"));
			return false;
		}
		// 取打印通知书记录
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (!tLOPRTManagerDB.getInfo()) {
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
		// 取打印通知书类型
		String tEdorItem = mLOPRTManagerSchema.getCode();
		if (tEdorItem == null || tEdorItem.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "InfoNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!tEdorItem.trim().equals(PrintManagerBL.CODE_PEdorHIInfo)) {
			CError tError = new CError();
			tError.moduleName = "InfoNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "单据类型错误！";
			this.mErrors.addOneError(tError);
			return false;
		}
		mEdorAcceptNo = mLOPRTManagerSchema.getOtherNo();
		if (mEdorAcceptNo == null || mEdorAcceptNo.trim().equals("")) {
			CError tError = new CError();
			tError.moduleName = "BqRefuseNoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取单据类型失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
		tLPEdorItemDB.setContNo(mLOPRTManagerSchema.getStandbyFlag1());
		tLPEdorItemSchema = tLPEdorItemDB.query().get(1);

		if (tLPEdorItemSchema == null) {
			CError tError = new CError();
			tError.moduleName = "InfoNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "获取数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getStandbyFlag1());
		tLCContSchema = tLCContDB.query().get(1);
		if (tLCContSchema == null) {
			CError tError = new CError();
			tError.moduleName = "InfoNoticePrintBL";
			tError.functionName = "checkData";
			tError.errorMessage = "获取数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	// 获取数据库数据
	private boolean getBaseData() {
		String tPrintName = "增补告知信息通知书"; 
		tXmlExport.createDocument(tPrintName);//初始化数据存储类
		PrintTool.setBarCode(tXmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(tLCContSchema.getAppntNo());
		if (uLanguage != null && !"".equals(uLanguage)) 
			tXmlExport.setUserLanguage(uLanguage);
		tXmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		LAAgentDB tLAAgentDB = new LAAgentDB();
		LAAgentSchema tLAAgentSchema = new LAAgentSchema();
		tLAAgentDB.setAgentCode(tLCContSchema.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			CError tError = new CError();
			tError.moduleName = "InfoNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "获取数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLAAgentSchema = tLAAgentDB.getSchema();
		// 模版自上而下的元素
		texttag.add("AgentName", tLAAgentSchema.getName()); // 代理人
		texttag.add("AgentCode", tLCContSchema.getAgentCode()); // 代理人编码
		texttag.add("ManageCom", tLPEdorItemSchema.getManageCom()); // 被保人
		texttag.add("ApplyName", mLOPRTManagerSchema.getStandbyFlag4()); // 申请人
		texttag.add("Date", strCurrentDate); // 打印时间
		texttag.add("ApplyDate", mLOPRTManagerSchema.getStandbyFlag5()); // 申请日期
		texttag.add("ApplyCnDate", BqNameFun.getChDate(mLOPRTManagerSchema
				.getStandbyFlag5())); // 中文申请日期
		texttag.add("EdorName", BqNameFun.getEdorName(tLPEdorItemSchema)); // 项目名称
		texttag.add("InsuredName", tLCContSchema.getInsuredName());
		texttag.add("ContNo", mLOPRTManagerSchema.getStandbyFlag1()); // 保单号
		texttag.add("ApproveNo", mLOPRTManagerSchema.getPrtSeq()); // 通知书号
		texttag.add("ApproveDate", mLOPRTManagerSchema.getStandbyFlag7()); // 审核时间
		texttag.add("Operator", CodeNameQuery.getOperator(mLOPRTManagerSchema
				.getStandbyFlag3())); // 保险公司经办人
		texttag.add("Approver", CodeNameQuery.getOperator(mLOPRTManagerSchema
				.getStandbyFlag6())); // 审核人

		texttag.add("ManageComAdd", BqNameFun.getComAddRess(tLCContSchema
				.getManageCom())); // 审核人

		tHIListTable.setName("HI");
		LPCustomerImpartSet allLPCustomerImpartSet = new LPCustomerImpartSet();
		LPCustomerImpartDB allLPCustomerImpartDB = new LPCustomerImpartDB();
		allLPCustomerImpartDB.setContNo(tLPEdorItemSchema.getContNo());
		// allLPCustomerImpartDB.setCustomerNo(mLPEdorItemSchema.getInsuredNo());
		allLPCustomerImpartDB.setEdorNo(tLPEdorItemSchema.getEdorNo());
		allLPCustomerImpartDB.setEdorType(tLPEdorItemSchema.getEdorType());
		allLPCustomerImpartSet = allLPCustomerImpartDB.query();
		if (allLPCustomerImpartSet.size() > 0) {
			for (int k = 1; k <= allLPCustomerImpartSet.size(); k++) {
				LPCustomerImpartSchema tLPCustomerImpartSchema = new LPCustomerImpartSchema();
				tLPCustomerImpartSchema = allLPCustomerImpartSet.get(k);
				String[] strNSS = new String[3];
				strNSS[0] = BqNameFun.getCodeName(tLPCustomerImpartSchema
						.getImpartVer(), "ImpartVer");
				strNSS[1] = tLPCustomerImpartSchema.getImpartCode();
				strNSS[2] = " 询问事项："
						+ tLPCustomerImpartSchema.getImpartContent() + " 告知内容："
						+ tLPCustomerImpartSchema.getImpartParamModle();
				tHIListTable.add(strNSS);
			}
		}

		ExeSQL tExeSQL = new ExeSQL();
		/** 投被保人标记 AppntIsInsuredFlag 投保人还是被保人0,投保人，1，被保人，2 两者兼之,但是注意，只针对某一保单 */
		String tAppntIsInsuredFlag = "";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String sql = " select 1 from lcAppnt where contno = '"
				+ "?contno?" + "' and AppntNo ='"
				+ "?AppntNo?" + "'";
		sqlbv1.sql(sql);
		sqlbv1.put("contno", tLCContSchema.getContNo());
		sqlbv1.put("AppntNo", tLPEdorItemSchema.getInsuredNo());
		String tFlag = tExeSQL.getOneValue(sqlbv1);
		if ("1".equals(tFlag)) {
			tAppntIsInsuredFlag = "0";
		}
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sql = " select 1 from lcinsured where contno = '"
				+ "?contno?" + "' and insuredno ='"
				+ "?insuredno?" + "'";
		sqlbv2.sql(sql);
		sqlbv2.put("contno", tLCContSchema.getContNo());
		sqlbv2.put("insuredno", tLPEdorItemSchema.getInsuredNo());
		tFlag = tExeSQL.getOneValue(sqlbv2);
		if ("1".equals(tFlag)) {
			tAppntIsInsuredFlag = "1";
		}
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sql = " select 1 from lcinsured where contno = '"
				+ "?contno?"
				+ "' and insuredno = (select appntno from lccont where contno = '"
				+ "?contno?" + "')";
		sqlbv3.sql(sql);
		sqlbv3.put("contno", tLCContSchema.getContNo());
		tFlag = tExeSQL.getOneValue(sqlbv3);
		if ("1".equals(tFlag)) {
			tAppntIsInsuredFlag = "2";
		}

		// 取出虽有的保单
		String tSumConNo = "";
		LCContDB rLCContDB = new LCContDB();
		LCContSet tLCContSet = new LCContSet();
		if ("0".equals(tAppntIsInsuredFlag)) {
			rLCContDB.setAppntNo(tLPEdorItemSchema.getInsuredNo());
		} else {
			rLCContDB.setInsuredNo(tLPEdorItemSchema.getInsuredNo());
		}

		rLCContDB.setAppFlag("1");
		tLCContSet = rLCContDB.query();
		if (tLCContSet.size() > 0) {
			for (int k = 1; k <= tLCContSet.size(); k++) {
				if (!tLPEdorItemSchema.getContNo().equals(
						tLCContSet.get(k).getContNo())) {
					tSumConNo += tLCContSet.get(k).getContNo() + ",";
				}
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "InfoNoticePrintBL";
			tError.functionName = "getBaseData";
			tError.errorMessage = "获取客户保单信息失败！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (!"".equals(tSumConNo)) {
			tSumConNo = tSumConNo.substring(0, tSumConNo.lastIndexOf(","));
			texttag.add("SumContNo", "您需要同时申请补充告知的保单如下:\n" + tSumConNo);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean preparePrintData() {
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		if (tHIListTable.size() > 0) {
			String[] c_strNS = new String[3];
			tXmlExport.addListTable(tHIListTable, c_strNS);
		}
		mResult.add(tXmlExport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 测试主程序业务方法
	 * 
	 * @param String[]
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86";
		tGlobalInput.Operator = "wangle";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("8104200090990");
		VData tVData = new VData();
		tVData.add(tGlobalInput);
		tVData.add(tLOPRTManagerSchema);
		InfoNoticePrintBL tInfoNoticePrintBL = new InfoNoticePrintBL();
		if (!tInfoNoticePrintBL.submitData(tVData, "PRINT")) {
			logger.debug("\t@> InfoNoticePrintBL 打印失败");
		}
	} // function main end
}
