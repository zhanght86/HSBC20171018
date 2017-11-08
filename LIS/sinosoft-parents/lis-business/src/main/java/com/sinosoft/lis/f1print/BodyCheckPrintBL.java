/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPENoticeDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LABranchGroupSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPENoticeSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.SysConst;
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
public class BodyCheckPrintBL implements PrintService {
	private static Logger logger = Logger.getLogger(BodyCheckPrintBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 取得的保单号码
	// private String mContNo = "";
	private String HealthName = "";

	private String Remark = "";

	// 输入的查询sql语句
	private String mSql = "";
	SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
	// 取得的延期承保原因
	// private String mUWError = "";
	// 取得的代理人编码
	private String mAgentCode = "";

	private String hospitalAdress = "";

//	private String hospitalName = "";
//
//	private String hospitalTel = "";
//
//	private boolean Phisical = false;

	// 业务处理相关变量
	/** 全局数据 */

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private LCContSchema mLCContSchema = new LCContSchema();

	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();

	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();

	private LDPersonSchema mLDPersonSchema = new LDPersonSchema();

	@SuppressWarnings("unused")
	private LABranchGroupSchema mLABranchGroupSchema = new LABranchGroupSchema();

	private GlobalInput mGlobalInput;

	public BodyCheckPrintBL() {
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
		mGlobalInput = (GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0);
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
		try {
			String PrtNo = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号

			logger.debug("PrtNo=" + PrtNo);
			LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
			tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
			if (tLOPRTManagerDB.getInfo() == false) {
				mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
				buildError("outputXML", "在取得打印队列中数据时发生错误");
				return false;
			}
			mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
			String OldPrtNo = mLOPRTManagerSchema.getOldPrtSeq(); // 打印流水号
			// 其他号码类型其中00代表是团单下的个单
			String othernotype = mLOPRTManagerSchema.getOtherNoType();

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
			if (!othernotype.equals("") && !othernotype.equals(null)
					&& othernotype.equals("00")) {

			} else {
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

			}
			mAgentCode = mLCContSchema.getAgentCode();
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(mAgentCode);

			mLAAgentSchema = tLAAgentDB.query().get(1).getSchema();
			// 2-体检信息
			// 2-1 查询体检主表
			LCPENoticeDB tLCPENoticeDB = new LCPENoticeDB();
			LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
			LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
			tLCPENoticeDB.setPrtSeq(OldPrtNo);
			tLCPENoticeSet = tLCPENoticeDB.query();
			String StrCont[] = null;
			ListTable tListTable = new ListTable();
			String[] tTitle = new String[1];
			tTitle[0] = "ContNo";
			tListTable.setName("MAIN");

			for (int j = 1; j <= tLCPENoticeSet.size(); j++) {
				StrCont = new String[1];
				StrCont[0] = tLCPENoticeSet.get(j).getProposalContNo();
				tListTable.add(StrCont);
			}
			String PEAddress = "";
			String PEHospitName = "";
			String PEDate = "";
			@SuppressWarnings("unused")
			String NeedLimosis = "";
			if (tLCPENoticeSet.size() >= 1) {
				tLCPENoticeSchema.setSchema(tLCPENoticeSet.get(1).getSchema());
				Remark = tLCPENoticeSchema.getRemark(); // 保存特殊要求
				HealthName = tLCPENoticeSchema.getName(); // 保存体检人名称
				PEDate = tLCPENoticeSchema.getPEDate(); // 保存体检日期

				if (tLCPENoticeSchema.getPEBeforeCond().equals("Y")) { // 是否需要空腹
					NeedLimosis = "是";
				} else {
					NeedLimosis = "否";
				}

			} else {
				mErrors.copyAllErrors(tLCPENoticeSet.mErrors);
				buildError("outputXML", "在取得体检信息的数据时发生错误");
				return false;
			}

			// 准备体检医院信息

			String[] HosTitle = new String[2];
			HosTitle[0] = "name";
			HosTitle[1] = "address";
			ListTable tHosListTable = new ListTable();
			String strHos[] = null;
			tHosListTable.setName("Hospital"); // 对应模版体检部分的行对象名
			String strSql = "";
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			logger.debug("分公司代码："
					+ mLCContSchema.getManageCom().substring(0, 4));

			strSql = "select hospitalname,address from ldhospital where 1=1 and hosstate='0' " // 增加对医院有效状态的判断
																								// modify
																								// by
																								// fuqx
																								// 2009-11-16
					+ "and mngcom like concat('',concat(concat('',substr((select managecom from lccont where ContNo = '"+ "?ContNo?"+ "'),1,8)),'%%')) order by char_length(trim(hospitalname))";
			sqlbv.sql(strSql);
			sqlbv.put("ContNo", mLOPRTManagerSchema.getOtherNo());
			SSRS hosSSRS = new SSRS();
			ExeSQL hosExeSQL = new ExeSQL();
			hosSSRS = hosExeSQL.execSQL(sqlbv);
			if (hosSSRS.getMaxRow() == 0) {
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
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			String ItemSql = "select PEItemName  from lcpenoticeitem where prtseq = '"
					+ "?OldPrtNo?"
					+ "' and contno = '"
					+ "?contno?"
					+ "'" + " order by peitemcode";
			sqlbv1.sql(ItemSql);
			sqlbv1.put("OldPrtNo", OldPrtNo);
			sqlbv1.put("contno", mLCContSchema.getContNo());
			SSRS tSSRS = new SSRS();
			ExeSQL tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv1);

			// zy 2009-05-14 调整体检内容的显示
			if (tSSRS.getMaxRow() == 0) {
				PEFlag = false;
			} else {
				PEFlag = true;
				// zy 2009-05-15 应要求一行显示
				String content = "";
				if (tSSRS.getMaxRow() == 1)
					content = tSSRS.GetText(1, 1);
				else {
					for (int p = 1; p <= tSSRS.getMaxRow(); p++) {
						if (p == 1)
							content = p + "." + tSSRS.GetText(p, 1);
						else
							content = content + ";" + p + "."
									+ tSSRS.GetText(p, 1);
					}
				}
				getContent(tPEItemListTable, content, 47);
			}

			// 营业部及营销服务部。
			LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
			tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());

			mLABranchGroupSchema = tLABranchGroupDB.query().get(1).getSchema();

			// 体检人性别和年龄
			LDPersonDB tLDPersonDB = new LDPersonDB();
			tLDPersonDB.setCustomerNo(tLCPENoticeSchema.getCustomerNo());
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
			String tPrintName = "体检通知书";
			XmlExportNew xmlExport = new XmlExportNew(); // 新建一个XmlExportNew的实例
			xmlExport.createDocument(tPrintName);//初始化数据存储类
			PrintTool.setBarCode(xmlExport, mLOPRTManagerSchema.getPrtSeq(),"其他内容一其他内容2其他内容3其他内容4其他内容");//条形码
			String uLanguage = PrintTool.getCustomerLanguage(tLCPENoticeSchema.getCustomerNo());
			if (uLanguage != null && !"".equals(uLanguage)) 
				xmlExport.setUserLanguage(uLanguage);//用户语言
			xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言

			// 生成-年-月-日格式的日期
			// StrTool tSrtTool = new StrTool();
			String SysDate = new SimpleDateFormat("yyyy年MM月dd日").format(new Date());
			PEDate = PEDate + "-";
			String CheckDate = StrTool.decodeStr(PEDate, "-", 1) + "年"
					+ StrTool.decodeStr(PEDate, "-", 2) + "月"
					+ StrTool.decodeStr(PEDate, "-", 3) + "日";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			String tSql = " select riskprop from lmriskapp where riskcode "
					+ " in (select riskcode from lcpol where contno='"
					+ "?contno?" + "' and mainpolno = polno)";
			sqlbv2.sql(tSql);
			sqlbv2.put("contno", mLCContSchema.getContNo());
			String tRiskType = tExeSQL.getOneValue(sqlbv2);
			if (tRiskType != null && tRiskType.equals("Y")) {
				LAComSchema mLAComSchema = new LAComSchema();
				LAComDB mLAComDB = new LAComDB();
				mLAComDB.setAgentCom(mLCContSchema.getAgentCom());
				mLAComSchema = mLAComDB.query().get(1).getSchema();

				texttag.add("LCCont.BankCode", mLAComSchema.getName()); // 代理机构
				texttag.add("LCCont.AgentType", tLABranchGroupDB.getName()); // 业务分布及业务组.
			}

			xmlExport.addListTable(tHosListTable, HosTitle); // 体检医院信息

			/**
			 * ==========================================================================
			 * 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
			 * LABranchGroup.Name：取8位编码的营业销售部
			 * ==========================================================================
			 */
			// 中支机构名称
			// 09-06-25 此处修改为直接取八位机构名称

			String tManageComCode = mLCContSchema.getManageCom();
			String tManageComName = getComName(tManageComCode);
			// 营业销售部名称
			String tBranchGroupCode = mLCContSchema.getManageCom();
			String tBranchGroupName = getComName(tBranchGroupCode);
			// zy 2009-05-14 回复日期
			String replyDate = PubFun.calDate(
					mLOPRTManagerSchema.getMakeDate(), 15, "D", "");
			String ReplyYear = replyDate.substring(0, 4);
			String ReplyMon = replyDate.substring(5, 7);
			String ReplyDay = replyDate.substring(8, 10);

			texttag.add(PrintTool.getTagName("投保人邮政编码"), mLCAddressSchema.getZipCode()); // 投保人邮政编码
			texttag.add("Address", mLCAddressSchema.getPostalAddress()); // 投保人地址


			// 体检人姓名
			String thead = PrintTool.getHead(mLCContSchema.getAppntName(),mLCContSchema.getAppntSex(),uLanguage);
			
			texttag.add("LCCont.AppntName", thead); // 投保人
			texttag.add("IDNO", getIDNO(tLCPENoticeSchema.getCustomerNo()));
			texttag.add(PrintTool.getTagName("投保单号"), mLCContSchema.getContNo()); // 投保单号
			texttag.add("CheckDate", CheckDate); // 体检日期
			texttag.add("HealthName", HealthName); // 被保险人名称
			texttag.add("Sex", getSexName(mLDPersonSchema.getSex())); // 体检人性别
			texttag.add("Age", PubFun
					.calInterval(mLDPersonSchema.getBirthday(), PubFun
							.getCurrentDate(), "Y")); // 体检人年龄
			texttag.add("Birthday", mLDPersonSchema.getBirthday()); // 体检人的出生日期
			texttag.add("WorkType",
					getWork(mLDPersonSchema.getOccupationCode())); // 体检人的职业

			// texttag.add("NeedLimosis", NeedLimosis); // 是否需要空腹
			texttag.add(PrintTool.getTagName("体检医院"), PEHospitName); // 体检医院
			texttag.add(PrintTool.getTagName("体检医院地址"), PEAddress); // 体检医院地址
			if (!"".equals(StrTool.cTrim(PEHospitName))) // 体检医院信息不为空
			{
				xmlExport.addDisplayControl("displayhospit"); // 显示体检医院信息
			}
			// 累计风险保额超过30万
			double tAmnt = 0;
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			String tsql = "";
//			String tsql = " SELECT (case when healthyamnt2('"
//					+ "?var?"
//					+ "','1','1') is not null then healthyamnt2('"
//					+ "?var?"
//					+ "','1','1')  else 0 end) from dual";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tsql = " SELECT (case when healthyamnt2('"
				+ "?var?"
				+ "','1','1') is not null then healthyamnt2('"
				+ "?var?"
				+ "','1','1')  else 0 end) from dual";
		 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tsql ="{ call healthyamnt2(?#ddd#?,'" +"?var?" + "','1','1') }";
		 }
			sqlbv3.sql(tsql);
			sqlbv3.put("var", tLCPENoticeSchema.getCustomerNo());
			String tAppntSumLifeAmnt = tExeSQL.getOneValue(sqlbv3);
			if (tAppntSumLifeAmnt != null)
				tAmnt = tAmnt + Double.parseDouble(tAppntSumLifeAmnt);
//			tsql = " SELECT (case when healthyamnt2('"
//					+ "?var?"
//					+ "','2','1') is not null then healthyamnt2('"
//					+ "?var?"
//					+ "','2','1')  else 0 end) from dual";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tsql = " SELECT (case when healthyamnt2('"
				+ "?var?"
				+ "','2','1') is not null then healthyamnt2('"
				+ "?var?"
				+ "','2','1')  else 0 end) from dual";
		 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tsql ="{ call healthyamnt2(?#ddd#?,'" +"?var?" + "','2','1') }";
		 }
			sqlbv3.sql(tsql);
			sqlbv3.put("var", tLCPENoticeSchema.getCustomerNo());
			String tAppntSumHealthAmnt = tExeSQL.getOneValue(sqlbv3);
			if (tAppntSumHealthAmnt != null)
				tAmnt = tAmnt + Double.parseDouble(tAppntSumHealthAmnt);
//			tsql = " SELECT (case when healthyamnt2('"
//					+ "?var?"
//					+ "','4','1') is not null then healthyamnt2('"
//					+ "?var?"
//					+ "','4','1')  else 0 end) from dual";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tsql = " SELECT (case when healthyamnt2('"
				+ "?var?"
				+ "','4','1') is not null then healthyamnt2('"
				+ "?var?"
				+ "','4','1')  else 0 end) from dual";
		 }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			 tsql ="{ call healthyamnt2(?#ddd#?,'" +"?var?" + "','4','1') }";
		 }
			sqlbv3.sql(tsql);
			sqlbv3.put("var", tLCPENoticeSchema.getCustomerNo());
			String tAppntAccidentAmnt = tExeSQL.getOneValue(sqlbv3);
			if (tAppntAccidentAmnt != null)
				tAmnt = tAmnt + Double.parseDouble(tAppntAccidentAmnt);
			if ("".equals(StrTool.cTrim(PEHospitName)) || (tAmnt - 300000 > 0))// A该单四级机构无定点医院;B体检件中累计风险保额超过30万元
			{
				xmlExport.addDisplayControl("displaydescsub"); // 显示特殊体检说明信息
				if (Remark != null && !Remark.equals(""))
					Remark = "2." + Remark;
				else
					Remark = "";
			}
			if (!"".equals(StrTool.cTrim(Remark))
					|| StrTool.cTrim(PEHospitName).equals("")
					|| (tAmnt - 300000 > 0)) // 体检说明不为空或体检医院为空
			{
				xmlExport.addDisplayControl("displaydesc"); // 显示体检说明信息
			}
			if (Remark != null && !"".equals(Remark)) {
				texttag.add("SpecRequire", Remark); // 特殊要求
			}
			logger.debug("Remark=" + Remark);
			texttag.add("PEBrachAddress", hospitalAdress);
			texttag.add(PrintTool.getTagName("代理人姓名"), mLAAgentSchema.getName()); // 代理人姓名
			texttag.add(PrintTool.getTagName("代理人业务号"), mLCContSchema.getAgentCode()); // 代理人业务号
			texttag.add(PrintTool.getTagName("营业机构"), tManageComName); // 营业机构
			texttag.add("LABranchGroup.Name", tBranchGroupName + " "
					+ getUpComName(mLAAgentSchema.getBranchCode())); // 营业部
			texttag.add("PrtNo", PrtNo); //
			texttag.add("LCCont.PrtNo", mLCContSchema.getPrtNo()); // 印刷号
			texttag.add("LCCont.UWOperator", mLOPRTManagerSchema
					.getReqOperator()); // 核保师代码
			texttag.add("SysDate", SysDate);
			texttag.add("CHECKITEM", PEITEM);
			// zy 2009-05-14 回复日期
			texttag.add("ReplyYear", ReplyYear);
			texttag.add("ReplyMon", ReplyMon);
			texttag.add("ReplyDay", ReplyDay);
			String PEBefore = "否";
			if (StrTool.cTrim(tLCPENoticeSet.get(1).getPEBeforeCond()).equals(
					"Y"))
				PEBefore = "是";
			texttag.add("PEBefore", PEBefore);// 是否空腹
			texttag.add("InsuredNo", tLCPENoticeSet.get(1).getCustomerNo());// 体检人-被保险人

			String tinsuredName = tLCPENoticeSet.get(1).getName();
			if (tLCPENoticeSet.get(1).getCustomerType() != null
					&& tLCPENoticeSet.get(1).getCustomerType().equals("A"))// 投保人
				tinsuredName = "投保人" + tinsuredName;
			else
				// 被保险人
				tinsuredName = "被保险人" + tinsuredName;
			texttag.add("InsuredName1", tinsuredName); // 体检人
			texttag.add("InsuredName", tLCPENoticeSet.get(1).getName()); // 体检人
			String PEDownDate = mLOPRTManagerSchema.getMakeDate() + "-";
			PEDownDate = StrTool.decodeStr(PEDownDate, "-", 1) + "年"
					+ StrTool.decodeStr(PEDownDate, "-", 2) + "月"
					+ StrTool.decodeStr(PEDownDate, "-", 3) + "日";
			texttag.add("PEDownDate", PEDownDate);// 体检下发日期
			if (!mLOPRTManagerSchema.getPrtSeq().equals(
					mLOPRTManagerSchema.getOldPrtSeq()))
				texttag.add("RePrintDate", SysDate);
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			String sql = "select a.sex,get_age(a.birthday,c.polapplydate),a.idno,(select mobile from lcaddress where customerno=a.insuredno and addressno = a.addressno) from lcinsured a,lccont c where a.contno=c.contno "
					+ " and a.insuredno='"
					+ "?insuredno?"
					+ "' and a.contno='"
					+ "?contno?"
					+ "'";
			//System.out.println("aeon:"+(tLCPENoticeSet.get(1).getCustomerType()));
			if (tLCPENoticeSet.get(1).getCustomerType() != null&&tLCPENoticeSet.get(1).getCustomerType().equals("A")) {
				sql = "select a.appntsex,get_age(a.appntbirthday,'"
						+ "?var?"
						+ "'),a.idno,(select mobile from lcaddress where customerno=a.appntno and addressno = a.addressno) from lcappnt a where 1=1 "
						+ " and a.contno='" + "?tcontno?"
						+ "'";
			}
			sqlbv4.sql(sql);
			sqlbv4.put("insuredno", tLCPENoticeSet.get(1).getCustomerNo());
			sqlbv4.put("contno", tLCPENoticeSet.get(1).getContNo());
			sqlbv4.put("var", PubFun.getCurrentDate());
			sqlbv4.put("tcontno", tLCPENoticeSet.get(1).getContNo());
			tSSRS = new SSRS();
			tExeSQL = new ExeSQL();
			tSSRS = tExeSQL.execSQL(sqlbv4);
			String Sex = "";
			String Age = "";
			String IDNo = "";
			String Mobile = "";
			if (tSSRS.getMaxRow() > 0) {
				Sex = tSSRS.GetText(1, 1);
				Age = tSSRS.GetText(1, 2);
				IDNo = tSSRS.GetText(1, 3);
				Mobile = tSSRS.GetText(1, 4);
			}
			texttag.add("Sex", Sex); //
			texttag.add("Age", Age); // 
			texttag.add("IDNo", IDNo); // 
			texttag.add("Mobile", Mobile); // 		
			texttag.add("PrtSeq", mLOPRTManagerSchema.getPrtSeq()); // 流水号

			if (texttag.size() > 0) {
				xmlExport.addTextTag(texttag);
			}

			xmlExport.addListTable(tListTable, tTitle);
			// 设置列表名字和字段名
			strRiskInfo = new String[2];
			strRiskInfo[0] = "Index";
			strRiskInfo[1] = "PEItem";

			tPEItemListTable.setName("PEItem");

			xmlExport.addListTable(tPEItemListTable, strRiskInfo);


			mResult.clear();
			mResult.addElement(xmlExport);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "准备打印数据时出错！");
			return false;
		}
	}

	/**
	 * 得到通过机构代码得到机构名称
	 * 
	 * @param strComCode
	 *            String
	 * @return String
	 */
	private String getComName(String strComCode) {
		mSql = "select CodeName from LDcode where Code = '" + "?strComCode?"
				+ "' and CodeType = 'station'";
		sqlbv5.sql(mSql);
		sqlbv5.put("strComCode", strComCode);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv5);

	}

	private String getIDNO(String customerno) {
		mSql = "select idno from ldperson where customerno = '" + "?customerno?"
				+ "'";
		sqlbv5.sql(mSql);
		sqlbv5.put("customerno", customerno);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv5);

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
		sqlbv5.sql(mSql);
		sqlbv5.put("StrSex", StrSex);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv5);

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
		sqlbv5.sql(mSql);
		sqlbv5.put("StrWork", StrWork);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv5);

	}

	/*
	 * 对打印的文字过长一行显示不完时作调整
	 * 
	 * @param tMainPolNo @return LCPolSchema
	 */
	private void getContent(ListTable tListTable, String content,
			int nMaxCharsInOneLine) {
		int nSpecReasonLen = content.length();
		String strSpecReason = content;
		String[] strArr;
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
