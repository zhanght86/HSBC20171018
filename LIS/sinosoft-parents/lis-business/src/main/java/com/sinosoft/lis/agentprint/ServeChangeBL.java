/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.agentprint;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LDPersonSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
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
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author lh
 * @version 1.0
 */
public class ServeChangeBL implements PrintService {
private static Logger logger = Logger.getLogger(ServeChangeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	private VData mInputData = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private String mAppntNo = "";
	private String mAgentCode = "";

	private String mChgDate = "";

	private XmlExport xmlExportAll = null;

	public ServeChangeBL() {
	}

	public static void main(String[] args) {
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "8611";
		tGI.Operator = "aa";

		String ManageCom = "86110000";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq("00000000000000508948");

		tLOPRTManagerSchema.setMakeDate("2006-07-03");

		tLOPRTManagerSchema.setOtherNo("02220007");

		tLOPRTManagerSchema.setOtherNoType("0");
		tLOPRTManagerSchema.setCode("CH01");
		tLOPRTManagerSchema.setReqCom("86110000");
		tLOPRTManagerSchema.setReqOperator("001001002");
		tLOPRTManagerSchema.setPrtType("0");
		tLOPRTManagerSchema.setStateFlag("0");

		ServeChangeBL tServeChangeBL = new ServeChangeBL();

		VData vData = new VData();
		vData.add(tGI);
		vData.add(tLOPRTManagerSchema);

		if (!tServeChangeBL.submitData(vData, "")) {
			logger.debug("---Error: "
					+ tServeChangeBL.mErrors.getFirstError().toString());
		}

		logger.debug("success");
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            VData
	 * @param cOperate
	 *            String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @param vData
	 *            VData
	 * @return boolean
	 */
	private boolean prepareOutputData(VData vData) {
		try {
			vData.clear();
			vData.add(mGlobalInput);
			vData.add(mLOPRTManagerSchema);
		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		logger.debug("--------------------start------------------");

		mChgDate = mLOPRTManagerSchema.getMakeDate();

		mAgentCode = mLOPRTManagerSchema.getOtherNo(); // 取得新服务人员

		// 查询该服务人员所服务变更保单的所有投保人
		String sql = "select distinct a.appntno from lacommisiondetail b,lccont a where b.agentcode='"
				+ "?agentcode?"
				+ "'  And b.startserdate='"
				+ "?startserdate?"
				+ "' and b.poltype = '1' and a.contno = b.grpcontno";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("agentcode", mAgentCode);
		sqlbv1.put("startserdate", mChgDate);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = tExeSQL.execSQL(sqlbv1);
		if (tSSRS == null || tSSRS.MaxRow == 0) {
			CError tError = new CError();
			tError.moduleName = "ServeChangeBL";
			tError.functionName = "dealData";
			tError.errorMessage = "服务人员" + mAgentCode + "的保单并没有发生人员的变更!";
			this.mErrors.addOneError(tError);
			return false;
		}

		int num = tSSRS.MaxRow;

		xmlExportAll = new XmlExport();
		xmlExportAll.createDocuments("printer", mGlobalInput);
		xmlExportAll.setTemplateName("ServeChange.vts");

		for (int i = 1; i <= num; i++) {
			TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
			XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
			tXmlExport.createDocument("ServeChange.vts", "");

			mAppntNo = tSSRS.GetText(i, 1);

			// 查询变更服务人员所服务的投保人的保单
			// LAOrphanPolicyBDB tLAOrphanPolicyBDB = new LAOrphanPolicyBDB();
			// LAOrphanPolicyBSet tLAOrphanPolicyBSet = new LAOrphanPolicyBSet();
			//
			// tLAOrphanPolicyBDB.setAppntNo(mAppntNo);
			// tLAOrphanPolicyBSet = tLAOrphanPolicyBDB.query();
			ExeSQL tExe = new ExeSQL();
			String tSql = "select distinct contno from LAOrphanPolicyB"
					+ " where contno in (select contno from lccont where appntno='"
					+ "?appntno?" + "')";// 作过保全投保人变更以后，可能会查出多个contno，非孤儿单也给查出来了
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(tSql);
			sqlbv2.put("appntno", mAppntNo);
			SSRS pSSRS = new SSRS();
			pSSRS = tExe.execSQL(sqlbv2);

			if (pSSRS.MaxRow == 0) {
				CError tError = new CError();
				tError.moduleName = "ServeChangeBL";
				tError.functionName = "dealData";
				tError.errorMessage = "没有查询到" + mAppntNo + "的保单变更服务人员的记录!";
				this.mErrors.addOneError(tError);
				return false;
			}

			ListTable tListTable = new ListTable();
			tListTable.setName("CONT");
			for (int j = 1; j <= pSSRS.MaxRow; j++) {
				String[] ContNoArray = new String[1]; // 输出的保单号
				ContNoArray[0] = pSSRS.GetText(j, 1);
				tListTable.add(ContNoArray);

				// String[] spaceStr = new String[1];
				// spaceStr[0] = "";
				// tListTable.add(spaceStr); //加空行
			}
			String[] Title = new String[1];
			Title[0] = "";

			tXmlExport.addListTable(tListTable, Title);

			// 投保人信息(姓名和性别)---------------------------------------------------
			String appntnameSQL = "select * from ldperson where CustomerNo='"
					+ "?CustomerNo?" + "'";
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(appntnameSQL);
			sqlbv3.put("CustomerNo", mAppntNo);
			LDPersonDB tLDPersonDB = new LDPersonDB();
			LDPersonSet tLDPersonSet = tLDPersonDB.executeQuery(sqlbv3);
			if (tLDPersonSet == null || tLDPersonSet.size() == 0) {
				CError tError = new CError();
				tError.moduleName = "ServeChangeBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询投保人信息失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			String appntName = tLDPersonSet.get(1).getName();
			String sex = tLDPersonSet.get(1).getSex();
			if (sex == null) {
				sex = "";
			}

			// 投保人信息(地址，邮编，电话)----------------------------------------------
			String appntSql = "select county, postaladdress, zipcode, getappntphone(b.contno,'') from LCAddress a, lcappnt b, lccont c where a.customerno = '"
					+ "?customerno?"
					+ "' and a.addressno = b.addressno and b.appntno = a.customerno and c.contno = b.contno and c.appflag = '1'";
			// modified by hust e-mail:hushuntao@sinosoft.com.cn
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(appntnameSQL);
			sqlbv4.put("customerno", mAppntNo);
			ExeSQL appntSQL = new ExeSQL();
			SSRS appntSSRS = new SSRS();
			appntSSRS = appntSQL.execSQL(sqlbv4);

			String address = "";
			String zipCode = "";
			String phoneNumber = "";

			if (appntSSRS.MaxRow > 0) {

				// 正确提取该客户的相关信息
				ExeSQL t1ExeSQL = new ExeSQL();
				SSRS t1SSRS = new SSRS();
				String t1SQL = "select placename from ldaddress where "
						+ "placetype = '03' and trim(placecode) = '"
						+ "?where?" + "'";
				SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
				sqlbv5.sql(t1SQL);
				sqlbv5.put("where", appntSSRS.GetText(1, 1));
				String tAddressEX = "";
				t1SSRS = t1ExeSQL.execSQL(sqlbv5);
				if (t1SSRS.MaxRow > 0) {
					tAddressEX = t1SSRS.GetText(1, 1);
				}
				address = tAddressEX + appntSSRS.GetText(1, 2);
				zipCode = appntSSRS.GetText(1, 3);
				phoneNumber = appntSSRS.GetText(1, 4);

			}

			// 系统时间---------------------------------------------------------------
			String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
					+ StrTool.getDay() + "日";

			// 保单服务人员姓名、电话----------------------------------------------------
			LAAgentSchema tLAAgentSchema = new LAAgentSchema();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);
			if (!tLAAgentDB.getInfo()) {
				CError tError = new CError();
				tError.moduleName = "ServeChangeBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询服务人员信息失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLAAgentSchema = tLAAgentDB.getSchema();

			String ServerName = tLAAgentSchema.getName();
			;
			String telephoneNo1 = tLAAgentSchema.getPhone();
			String telephoneNo2 = tLAAgentSchema.getMobile();
			/*
			
			 */

			// ----------------------------------------------------------------------
			tTextTag.add("Address", address); // 投保人地址
			tTextTag.add("MailCode", zipCode); // 投保人邮编
			// 投保人姓名和称谓 模板上没有sex
			if (sex.equals("0")) {
				tTextTag.add("LCCont.AppntName", appntName + " 先生");
			} else {
				if (sex.equals("1")) {
					tTextTag.add("LCCont.AppntName", appntName + " 女士");
				} else {
					tTextTag.add("LCCont.AppntName", appntName);
				}
			}
			tTextTag.add("Telephone", phoneNumber); // 投保人联系电话

			tTextTag.add("ServerName", ServerName); // 服务人员
			tTextTag.add("Telephone1", telephoneNo1); // 服务人员联系电话
			tTextTag.add("Telephone2", telephoneNo2); // 服务人员手机

			if (tTextTag.size() > 0) {
				tXmlExport.addTextTag(tTextTag);
			}
			// tXmlExport.outputDocumentToFile("d:\\","test100");//输出xml文档到文件
			xmlExportAll.addDataSet(tXmlExport.getDocument().getRootElement());
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {

		// 全局变量
		this.mInputData = (VData) cInputData.clone();
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		// tWebPath = (String) cInputData.getObjectByObjectName("String",
		// 0);

		//
		return true;
	}

	public VData getResult() {
		mResult.clear();
		mResult.add(xmlExportAll);
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "MeetF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public CErrors getErrors() {
		return mErrors;
	}

}
