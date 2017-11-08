/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimnb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpAddressDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LLUWPENoticeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.f1print.PrintTool;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGrpAddressSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LLUWPENoticeSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LLUWPENoticeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

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
public class LLUWBodyCheckPrintBL implements PrintService {
private static Logger logger = Logger.getLogger(LLUWBodyCheckPrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	// 取得的保单号码
	// private String mContNo = "";
	private String HealthName = "";

	private String Remark = "";

	// 输入的查询sql语句
	private String mSql = "";
	// 取得的延期承保原因
	// private String mUWError = "";
	// 取得的代理人编码
	private String mAgentCode = "";
	private String hospitalAdress = "";

	// 业务处理相关变量
	/** 全局数据 */

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();
	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();
	private LCGrpAddressSchema mLCGrpAddressSchema = new LCGrpAddressSchema();
	private LDPersonSchema mLDPersonSchema = new LDPersonSchema();

	public LLUWBodyCheckPrintBL() {
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
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();
		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
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
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		if (mLOPRTManagerSchema == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null) {
			buildError("getInputData", "没有得到足够的信息:印刷号不能为空！");
			return false;
		}
		return true;
	}

	/**
	 * 返回处理信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 返回错误信息
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCContF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 打印处理
	 * 
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	private boolean getPrintData() {
		// 根据印刷号查询打印队列中的纪录
		String PrtSeq = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号

		logger.debug("PrtNo=" + PrtSeq);
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		String OldPrtNo = mLOPRTManagerSchema.getOldPrtSeq(); // 打印流水号
		@SuppressWarnings("unused")
		boolean PEFlag = false; // 打印体检件部分的判断标志
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		if (!tLCContDB.getInfo()) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			buildError("outputXML", "在取得LCCont的数据时发生错误");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();
		// 投保人地址和邮编
		if (mLCContSchema.getContType().equals("1")) {
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAppntDB.mErrors);
				buildError("outputXML", "在取得打印队列中数据时发生错误");
				return false;
			}
			LCAddressDB tLCAddressDB = new LCAddressDB();
			tLCAddressDB.setCustomerNo(mLCContSchema.getAppntNo());
			tLCAddressDB.setAddressNo(tLCAppntDB.getAddressNo());
			if (tLCAddressDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCAddressDB.mErrors);
				buildError("outputXML", "在取得打印队列中数据时发生错误");
				return false;
			}
			mLCAddressSchema = tLCAddressDB.getSchema();
		} else if (mLCContSchema.getContType().equals("2")) {
			LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
			tLCGrpAppntDB.setGrpContNo(mLCContSchema.getGrpContNo());
			if (tLCGrpAppntDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCGrpAppntDB.mErrors);
				buildError("outputXML", "在取得打印队列中数据时发生错误");
				return false;
			}
			LCGrpAddressDB tLCGrpAddressDB = new LCGrpAddressDB();
			tLCGrpAddressDB.setCustomerNo(mLCContSchema.getAppntNo());
			tLCGrpAddressDB.setAddressNo(tLCGrpAppntDB.getAddressNo());
			if (tLCGrpAddressDB.getInfo() == false) {
				mErrors.copyAllErrors(tLCGrpAddressDB.mErrors);
				buildError("outputXML", "在取得打印队列中数据时发生错误");
				return false;
			}
			mLCGrpAddressSchema = tLCGrpAddressDB.getSchema();
		}

		mAgentCode = mLCContSchema.getAgentCode();
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgent的数据时发生错误");
			return false;
		}
		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息

		// 2-体检信息
		// 2-1 查询体检主表
		LLUWPENoticeDB tLLUWPENoticeDB = new LLUWPENoticeDB();
		LLUWPENoticeSchema tLLUWPENoticeSchema = new LLUWPENoticeSchema();
		LLUWPENoticeSet tLLUWPENoticeSet = new LLUWPENoticeSet();
		tLLUWPENoticeDB.setPrtSeq(OldPrtNo);
		tLLUWPENoticeSet = tLLUWPENoticeDB.query();
		String StrCont[] = null;
		ListTable tListTable = new ListTable();
		String[] tTitle = new String[1];
		tTitle[0] = "ContNo";
		tListTable.setName("MAIN");

		for (int j = 1; j <= tLLUWPENoticeSet.size(); j++) {
			StrCont = new String[1];
			StrCont[0] = tLLUWPENoticeSet.get(j).getProposalContNo();
			tListTable.add(StrCont);
		}
		String PEAddress = "";
		String PEHospitName = "";
		String PEDate = "";
		String NeedLimosis = "";
		if (tLLUWPENoticeSet.size() >= 1) {
			tLLUWPENoticeSchema.setSchema(tLLUWPENoticeSet.get(1).getSchema());
			Remark = tLLUWPENoticeSchema.getRemark(); // 保存特殊要求
			HealthName = tLLUWPENoticeSchema.getName(); // 保存体检人名称
			PEDate = tLLUWPENoticeSchema.getPEDate(); // 保存体检日期

			if (tLLUWPENoticeSchema.getPEBeforeCond().equals("Y")) { // 是否需要空腹
				NeedLimosis = "是";
			} else {
				NeedLimosis = "否";
			}

		} else {
			mErrors.copyAllErrors(tLLUWPENoticeSet.mErrors);
			buildError("outputXML", "在取得体检信息的数据时发生错误");
			return false;
		}

		// 准备体检医院信息

		String[] HosTitle = new String[3];
		HosTitle[0] = "name";
		HosTitle[1] = "address";
		HosTitle[2] = "phone";
		ListTable tHosListTable = new ListTable();
		String strHos[] = null;
		tHosListTable.setName("Hospital"); // 对应模版体检部分的行对象名
		String strSql = "";
		logger.debug("分公司代码："
				+ mLCContSchema.getManageCom().substring(0, 4));
//		if (mLCContSchema.getManageCom().substring(0, 4).equals("8632")
//				|| mLCContSchema.getManageCom().substring(0, 4).equals("8651")
//				|| mLCContSchema.getManageCom().substring(0, 4).equals("8637")
//				|| mLCContSchema.getManageCom().substring(0, 4).equals("8638")) // Modify
//																				// by
//																				// zhaorx
//																				// 2006-03-28这是很愚蠢的！
//		{
//			strSql = "select hospitalname,remark,phone from ldhospital where 1=1 "
//					+ "and mngcom like '' || substr((select managecom from lccont where ContNo = '"
//					+ mLOPRTManagerSchema.getOtherNo() + "'),0,6) || '%%' ";
//
//		} else {
//			strSql = "select hospitalname,remark,phone from ldhospital where 1=1 "
//					+ "and mngcom like '' || substr((select managecom from lccont where ContNo = '"
//					+ mLOPRTManagerSchema.getOtherNo() + "'),0,8) || '%%' ";
//		}
		strSql = "select hospitalname,address from ldhospital where 1=1 "
			+ "and mngcom like concat(concat('' , substr("+"?mngcom?"
			+",0,8)) , '%%') order by char_length(trim(hospitalname))";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSql);
		sqlbv.put("mngcom", mLCContSchema.getManageCom());
		SSRS hosSSRS = new SSRS();
		ExeSQL hosExeSQL = new ExeSQL();
		hosSSRS = hosExeSQL.execSQL(sqlbv);
		if (hosSSRS.getMaxRow() == 0) {
//		mErrors.copyAllErrors(tLCPENoticeSet.mErrors);
//		buildError("outputXML", "在取得体检医院信息发生错误");
//		return false;
		} else {
			for (int j = 1; j <= hosSSRS.getMaxRow(); j++) {
				strHos = new String[2];
				strHos[0] = hosSSRS.GetText(j, 1);
				strHos[1] = hosSSRS.GetText(j, 2); // 序号对应的内容
				tHosListTable.add(strHos);
				PEAddress = hosSSRS.GetText(j, 2);
				PEHospitName = hosSSRS.GetText(j, 1);
				break;
			}		

		}

		// 2-1 查询体检子表
		String PEITEM = "";
		String[] PETitle = new String[2];
		PETitle[0] = "ID";
		PETitle[1] = "CHECKITEM";
		ListTable tPEListTable = new ListTable();
		ListTable tPEItemListTable = new ListTable();
		String[] strRiskInfo = null;
		tPEListTable.setName("CHECKITEM"); // 对应模版体检部分的行对象名

		String ItemSql = "select PEItemName  from lluwpenoticeitem where prtseq = '"
				+ "?prtno?"
				+ "' and contno = '"
				+ "?contno?"
				+ "'" //+ " and FreePE = 'N' "
				+" order by peitemcode";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(ItemSql);
		sqlbv1.put("prtno", OldPrtNo);
		sqlbv1.put("contno", mLCContSchema.getContNo());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		/*if (tSSRS.getMaxRow() == 0) {
			PEFlag = false;
		} else {
			PEFlag = true;

			for (int p = 1; p <= tSSRS.getMaxRow(); p++) {
				if (tSSRS.GetText(p, 1).equals("身高")
						|| tSSRS.GetText(p, 1).equals("体重")
						|| tSSRS.GetText(p, 1).equals("血压")
						|| tSSRS.GetText(p, 1).equals("内科检查")
						|| tSSRS.GetText(p, 1).equals("外科检查")) {
					Phisical = true;
				}
			}
			if (Phisical == true) {
				PEITEM = "1.临床物理检查  ";
				for (i = 2; i <= tSSRS.getMaxRow() - 4; i++) {
					PEITEM = PEITEM + i + "." + tSSRS.GetText(i + 4, 1) + "  ";
					logger.debug(tSSRS.GetText(i + 4, 1));
				}
			} else {
				for (i = 1; i <= tSSRS.getMaxRow(); i++) {
					PEITEM = PEITEM + i + "." + tSSRS.GetText(i, 1) + "  ";
					strRiskInfo = new String[2];
					strRiskInfo[0] = String.valueOf(i);
					strRiskInfo[1] = tSSRS.GetText(i, 1);
					tPEItemListTable.add(strRiskInfo);
				}
			}
		}*/
		if (tSSRS.getMaxRow() == 0) {
			PEFlag = false;
		}
		else
		{
			PEFlag = true;
//			if(tSSRS.getMaxRow() == 1)
//			{
//				getContent(tPEItemListTable,tSSRS.GetText(1, 1),50);
//			}
//			else
//			{
//				for (int p = 1; p <= tSSRS.getMaxRow(); p++) {
//				String	content = p + "." + tSSRS.GetText(p, 1); // 问题件内容
//					getContent(tPEItemListTable, content ,50);
//				}
//			}
			//zy 2009-05-15 应要求一行显示
			String	content="";
			if(tSSRS.getMaxRow() == 1)
					content =tSSRS.GetText(1, 1);	
			else
			{
				for (int p = 1; p <= tSSRS.getMaxRow(); p++)
				{
					if(p==1)
						content=p + "." + tSSRS.GetText(p, 1); 
					else
					content = content+";"+p + "." + tSSRS.GetText(p, 1); 
				}
			}
			getContent(tPEItemListTable, content ,50);
		}

		// 营业部及营销服务部。
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
		if (tLABranchGroupDB.getInfo() == false) {
			mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
			buildError("outputXML", "在取得营业部及营销服务部数据时发生错误");
			return false;
		}

		// 体检人性别和年龄
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(tLLUWPENoticeSchema.getCustomerNo());
		if (tLDPersonDB.getInfo() == false) {
			mErrors.copyAllErrors(tLDPersonDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLDPersonSchema = tLDPersonDB.getSchema();
		FDate fdate = new FDate();
		fdate.getDate(PubFun.getCurrentDate());
		// 其它模版上单独不成块的信息
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew xmlExport = new XmlExportNew(); // 新建一个XmlExport的实例
//		xmlexport.createDocument("LLNPENotice.vts", "printer"); // 最好紧接着就初始化xml文档
		xmlExport.createDocument("理赔体检通知书");
		// 生成-年-月-日格式的日期
		// StrTool tSrtTool = new StrTool();
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		PEDate = PEDate + "-";
		@SuppressWarnings("unused")
		String CheckDate = StrTool.decodeStr(PEDate, "-", 1) + "年"
				+ StrTool.decodeStr(PEDate, "-", 2) + "月"
				+ StrTool.decodeStr(PEDate, "-", 3) + "日";
		String tSql = " select riskprop from lmriskapp where riskcode "
				+ " in (select riskcode from lcpol where contno='"
				+ "?contno?" + "' and mainpolno = polno)";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("contno", mLCContSchema.getContNo());
		String tRiskType = tExeSQL.getOneValue(sqlbv2);
		if (tRiskType != null && tRiskType.equals("Y")) {
			LAComSchema mLAComSchema = new LAComSchema();
			LAComDB mLAComDB = new LAComDB();
			mLAComDB.setAgentCom(mLCContSchema.getAgentCom());
			if (!mLAComDB.getInfo()) {
				mErrors.copyAllErrors(mLAComDB.mErrors);
				buildError("outputXML", "在取得LACom的数据时发生错误");
				return false;
			}
			mLAComSchema = mLAComDB.getSchema(); // 保存银行代码信息

			texttag.add("LCCont.BankCode", mLAComSchema.getName()); // 代理机构
			texttag.add("LCCont.AgentType", tLABranchGroupDB.getName()); // 业务分布及业务组.
		}

		xmlExport.addListTable(tHosListTable, HosTitle); // 体检医院信息

		String sql = "select "
//				+"a.sex,get_age(a.birthday,c.polapplydate),a.idno,"
				+"b.mobile from lcinsured a,lcaddress b,lccont c where a.contno=c.contno and a.insuredno = b.customerno and a.addressno = b.addressno "
				+ " and a.insuredno='"+"?customerno?"+"' and a.contno='"+ "?contno?" +"'";
//		if(tLLUWPENoticeSchema.getCustomerType().equals("A"))
		if("A".equals(tLLUWPENoticeSchema.getCustomerType())) // modify wyc
		{
			sql = "select "
//					+"a.sex,a.age,a.idno,"
					+"b.mobile from lcappnt a,lcaddress b where a.appntno = b.customerno and a.addressno = b.addressno "
					+ " and a.contno='"+ "?contno?" +"'";
		}
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("customerno", tLLUWPENoticeSchema.getCustomerNo());
		sqlbv3.put("contno", tLLUWPENoticeSchema.getContNo());
		tSSRS = new SSRS();
		tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv3);
		String Mobile ="";
		if (tSSRS.getMaxRow() > 0) {
//			Sex =tSSRS.GetText(1, 1);
//			Age =tSSRS.GetText(1, 1);
//			IDNo =tSSRS.GetText(1, 1);
			Mobile =tSSRS.GetText(1, 1);
		}
		
		/**
		 * ==========================================================================
		 * 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
		 * LABranchGroup.Name：取8位编码的营业销售部
		 * ==========================================================================
		 */
		// 中支机构名称
		String tManageComCode = mLCContSchema.getManageCom().substring(0, 6);
		String tManageComName = getComName(tManageComCode);
		// 营业销售部名称
		String tBranchGroupCode = mLCContSchema.getManageCom();
		String tBranchGroupName = getComName(tBranchGroupCode);
		//计算下年度保单生效日期 并与当前日期+10天相比，哪个早用哪个
		String date1 = PubFun.getCurrentDate();
		String date2 = mLCContSchema.getCValiDate();
		int d = PubFun.calInterval3(date2, date1, "Y");
		String date3 = PubFun.calDate(date2, d+1, "Y",date2);
		logger.debug("i===================="+d);
		logger.debug("下年度生效日期===================="+date3);
		//当前日期+10天
		String ttSql = "SELECT ADDDATE(NOW(),10) FROM DUAL";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(ttSql);
		SSRS ttSSRS = new SSRS();
		ttSSRS = tExeSQL.execSQL(sqlbv5);
		String tDate = ttSSRS.GetText(1, 1);
		logger.debug(tDate);
		boolean TFDate = PubFun.checkDate(date3, tDate);
		String xCvalidate = "";
		if(TFDate){
			xCvalidate = StrTool.decodeStr(date3, "-", 1) + "年"
						+ StrTool.decodeStr(date3, "-", 2) + "月"
						+ StrTool.decodeStr(date3, "-", 3) + "日";
		}else{
			String tArr[] = tDate.split("-");
			String tempStr = tArr[2];
			String tRi = tempStr.substring(0, 2);
			xCvalidate = StrTool.decodeStr(tDate, "-", 1) + "年"
						+ StrTool.decodeStr(tDate, "-", 2) + "月"
						+ tRi + "日";
		}
		
		PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq());
		// 模版自上而下的元素
//		texttag.add("BarCode1", mLOPRTManagerSchema.getPrtSeq());
//		texttag
//				.add(
//						"BarCodeParam1",
//						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
//		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
//		texttag
//				.add(
//						"BarCodeParam2",
//						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		String tZipCode = "";
		String tAddress = "";
		if (mLCContSchema.getContType().equals("1")) {
			tZipCode = mLCAddressSchema.getZipCode();
			tAddress = mLCAddressSchema.getPostalAddress();
		}
		tZipCode = mLCGrpAddressSchema.getGrpZipCode();
		tAddress = mLCGrpAddressSchema.getGrpAddress();
		texttag.add("Post", tZipCode); // 投保人邮政编码
		texttag.add("Address", tAddress); // 投保人地址
		texttag.add("xCvalidate", xCvalidate);
		texttag.add("AppntName", mLCContSchema.getAppntName());
		texttag.add("LCCont.AppntName", tLLUWPENoticeSchema.getName()); // 投保人名称
		texttag.add("IDNo", getIDNO(tLLUWPENoticeSchema.getCustomerNo()));
//		texttag.add("LCCont.ContNo", mLCContSchema.getContNo()); // 投保单号
		texttag.add("ContNo", mLCContSchema.getContNo()); // 投保单号
		String sCheckDate = PubFun.calDate(PubFun.getCurrentDate(), 7, "D",PubFun.getCurrentDate()) + "-";
		sCheckDate = StrTool.decodeStr(sCheckDate, "-", 1) + "年"
		+ StrTool.decodeStr(sCheckDate, "-", 2) + "月"
		+ StrTool.decodeStr(sCheckDate, "-", 3) + "日";
		texttag.add("CheckDate", sCheckDate); // 体检日期(后第十日)
//		texttag.add("CheckDate", CheckDate); // 体检日期
		texttag.add("InsuredName", HealthName); // 被保险人名称
		texttag.add("Sex", getSexName(mLDPersonSchema.getSex())); // 体检人性别
		texttag.add("Age", PubFun.calInterval(mLDPersonSchema.getBirthday(),
				PubFun.getCurrentDate(), "Y")); // 体检人年龄
		texttag.add("Birthday", mLDPersonSchema.getBirthday()); // 体检人的出生日期
		texttag.add("WorkType", getWork(mLDPersonSchema.getOccupationCode())); // 体检人的职业
		texttag.add("SpecRequire", Remark); // 特殊要求
		logger.debug("Remark=" + Remark);
		
		String PEDownDate = mLOPRTManagerSchema.getMakeDate() + "-";
		PEDownDate = StrTool.decodeStr(PEDownDate, "-", 1) + "年"
				+ StrTool.decodeStr(PEDownDate, "-", 2) + "月"
				+ StrTool.decodeStr(PEDownDate, "-", 3) + "日";
		texttag.add("PEDownDate", PEDownDate);//体检下发日期
		texttag.add("PEBefore", NeedLimosis); // 是否需要空腹
		texttag.add("HospitName", PEHospitName); // 体检地点
		texttag.add("HospitAddress", PEAddress); // 体检地点
		texttag.add("PEBrachAddress", hospitalAdress);
//		texttag.add("LAAgent.Name", mLAAgentSchema.getName()); // 代理人姓名
		texttag.add("Name", mLAAgentSchema.getName()); // 代理人姓名
//		texttag.add("LAAgent.AgentCode", mLCContSchema.getAgentCode()); // 代理人业务号
		texttag.add("AgentCode", mLCContSchema.getAgentCode()); // 代理人业务号
//		texttag.add("LCCont.ManageCom", tManageComName); // 营业机构
		texttag.add("ManageCom", tManageComName); // 营业机构
		texttag.add("LABranchGroup.Name", tBranchGroupName + " "
				+ getUpComName(mLAAgentSchema.getBranchCode())); // 营业部
		texttag.add("PrtSeq", PrtSeq); // 流水号
		texttag.add("LCCont.PrtNo", mLCContSchema.getPrtNo()); // 印刷号
//		texttag.add("LCCont.UWOperator", tLLUWPENoticeSchema.getOperator()); // 核保师代码
		texttag.add("UWOperator", tLLUWPENoticeSchema.getOperator()); // 核保师代码
		texttag.add("SysDate", SysDate);
		texttag.add("Mobile", Mobile);
		texttag.add("CHECKITEM", PEITEM);
		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}

		xmlExport.addListTable(tListTable, tTitle);
		strRiskInfo = new String[2];
		strRiskInfo[0] = "Index";
		strRiskInfo[1] = "PEItem";

		tPEItemListTable.setName("PEItem");

		xmlExport.addListTable(tPEItemListTable, strRiskInfo);
		String contNo = mLCContSchema.getContNo();
		PrintTool.setCustomerLanguage(xmlExport, contNo);
		// xmlexport.outputDocumentToFile("e:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlExport);
		return true;
	}

	/**
	 * 得到通过机构代码得到机构名称
	 * 
	 * @param strComCode
	 *            String
	 * @return String
	 */
	private String getComName(String strComCode) {
		mSql = "select CodeName from LDcode where Code = '" + "?comcode?"
				+ "' and CodeType = 'station'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(mSql);
		sqlbv6.put("comcode", strComCode);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv6);

	}

	private String getIDNO(String customerno) {
		mSql = "select idno from ldperson where customerno = '" + "?customerno?"
				+ "'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(mSql);
		sqlbv7.put("customerno", customerno);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv7);

	}

	/**
	 * 获取性别
	 * 
	 * @param StrSex
	 *            String
	 * @return String
	 */
	private String getSexName(String StrSex) {
		mSql = "select CodeName from LDcode where Code = '" + "?StrSex?"
				+ "' and CodeType = 'sex'";
		SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
		sqlbv8.sql(mSql);
		sqlbv8.put("StrSex", StrSex);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv8);

	}

	public String getUpComName(String comcode) {
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupDB ttLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(comcode);
		if (!tLABranchGroupDB.getInfo()) {
			this.buildError("getUpComName", comcode + "机构不存在！");
			return null;
		} else {
			ttLABranchGroupDB.setAgentGroup(tLABranchGroupDB.getUpBranch());
			if (!ttLABranchGroupDB.getInfo()) {
				this.buildError("getUpComName", "在查找comcode所属的营业部时出错！");
				return null;
			} else {
				return ttLABranchGroupDB.getName();
			}
		}
	}

	/**
	 * 获取体检人职业
	 */
	private String getWork(String StrWork) {
		mSql = "select occupationname from ldoccupation where occupationcode='"
				+ "?StrWork?" + "' ";
		SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
		sqlbv9.sql(mSql);
		sqlbv9.put("StrWork", StrWork);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv9);

	}
	
	/** 
	 * 对打印的文字过长一行显示不完时作调整
	 * 
	 * @param tMainPolNo
	 * @return LCPolSchema
	 */
	private void getContent(ListTable tListTable, String content ,int nMaxCharsInOneLine) {
		int nSpecReasonLen = content.length();
		String strSpecReason = content;
		String[] strArr ;
		while (nSpecReasonLen > nMaxCharsInOneLine) {
			content = strSpecReason.substring(0, nMaxCharsInOneLine);
			strSpecReason = strSpecReason.substring(nMaxCharsInOneLine);
			nSpecReasonLen -= nMaxCharsInOneLine;
			
			strArr = new String[1];
			strArr[0] = content;
			tListTable.add(strArr);
		}

		if (nSpecReasonLen > 0) {
			strArr = new String[1];
			strArr[0] = strSpecReason;
			tListTable.add(strArr);
		}
	}

}
