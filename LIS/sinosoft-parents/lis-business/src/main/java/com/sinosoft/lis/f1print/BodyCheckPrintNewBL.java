/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.f1print;
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
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
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
 * @author lh
 * @version 1.0
 */
public class BodyCheckPrintNewBL implements PrintService
{
private static Logger logger = Logger.getLogger(BodyCheckPrintNewBL.class);


	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 取得的保单号码
	// private String mContNo = "";
	private String HealthName = "";

	private String Pereason = "";

	// 输入的查询sql语句
	private String mSql = "";
	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
	// 取得的延期承保原因
	// private String mUWError = "";
	// 取得的代理人编码
	private String mAgentCode = "";

	private String hospitalAdress = "";

	private String hospitalName = "";

	private String hospitalTel = "";

	private boolean Phisical = false;

	// 业务处理相关变量
	/** 全局数据 */

	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();

	private LCContSchema mLCContSchema = new LCContSchema();

	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();

	private LCAddressSchema mLCAddressSchema = new LCAddressSchema();

	private LDPersonSchema mLDPersonSchema = new LDPersonSchema();

	private LABranchGroupSchema mLABranchGroupSchema = new LABranchGroupSchema();

	// add by nicole 2008.09.16 单证打印改造
	private TransferData mTransferData = null;

	public BodyCheckPrintNewBL()
	{}

	/**
	 * 传输数据的公共方法
	 * @param cInputData VData
	 * @param cOperate String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		if (!cOperate.equals("PRINT"))
		{
			buildError("submitData", "不支持的操作字符串");
			return false;
		}
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData))
		{
			return false;
		}
		mResult.clear();
		// 准备所有要打印的数据
		if (!getPrintData())
		{
			logger.debug("getprintdata 出错");
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * @param cInputData VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData)
	{
		// 全局变量
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData.getObjectByObjectName("LOPRTManagerSchema", 0));
		mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		if (mLOPRTManagerSchema == null)
		{
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		if (mLOPRTManagerSchema.getPrtSeq() == null)
		{
			buildError("getInputData", "没有得到足够的信息:印刷号不能为空！");
			return false;
		}
		return true;
	}

	/**
	 * 返回处理信息
	 * @return VData
	 */
	public VData getResult()
	{
		return mResult;
	}

	/**
	 * 返回错误信息
	 * @return CErrors
	 */
	public CErrors getErrors()
	{
		return mErrors;
	}

	/**
	 * 错误构建
	 * @param szFunc String
	 * @param szErrMsg String
	 */
	private void buildError(String szFunc, String szErrMsg)
	{
		CError cError = new CError();
		cError.moduleName = "LCContF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 打印处理
	 * @return boolean
	 */
	private boolean getPrintData()
	{
		// 根据印刷号查询打印队列中的纪录
		String PrtNo = mLOPRTManagerSchema.getPrtSeq(); // 打印流水号

		logger.debug("PrtNo=" + PrtNo);
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema);
		if (tLOPRTManagerDB.getInfo() == false)
		{
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		String OldPrtNo = mLOPRTManagerSchema.getOldPrtSeq(); // 打印流水号
		// 其他号码类型其中00代表是团单下的个单
		String othernotype = mLOPRTManagerSchema.getOtherNoType();

		boolean PEFlag = false; // 打印体检件部分的判断标志
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mLOPRTManagerSchema.getOtherNo());
		int m, i;
		if (!tLCContDB.getInfo())
		{
			mErrors.copyAllErrors(tLCContDB.mErrors);
			buildError("outputXML", "在取得LCCont的数据时发生错误");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();
		// 投保人地址和邮编
		if (!othernotype.equals("") && !othernotype.equals(null) && othernotype.equals("00"))
		{

		}
		else
		{
			LCAppntDB tLCAppntDB = new LCAppntDB();
			tLCAppntDB.setContNo(mLCContSchema.getContNo());
			if (tLCAppntDB.getInfo() == false)
			{
				mErrors.copyAllErrors(tLCAppntDB.mErrors);
				buildError("outputXML", "在取得打印队列中数据时发生错误");
				return false;
			}
			LCAddressDB tLCAddressDB = new LCAddressDB();
			tLCAddressDB.setCustomerNo(mLCContSchema.getAppntNo());
			tLCAddressDB.setAddressNo(tLCAppntDB.getAddressNo());
			if (tLCAddressDB.getInfo() == false)
			{
				mErrors.copyAllErrors(tLCAddressDB.mErrors);
				buildError("outputXML", "在取得打印队列中数据时发生错误");
				return false;
			}
			mLCAddressSchema = tLCAddressDB.getSchema();

		}
		mAgentCode = mLCContSchema.getAgentCode();
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mAgentCode);
		if (!tLAAgentDB.getInfo())
		{
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			buildError("outputXML", "在取得LAAgent的数据时发生错误");
			return false;
		}
		mLAAgentSchema = tLAAgentDB.getSchema(); // 保存代理人信息
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

		for (int j = 1; j <= tLCPENoticeSet.size(); j++)
		{
			StrCont = new String[1];
			StrCont[0] = tLCPENoticeSet.get(j).getProposalContNo();
			tListTable.add(StrCont);
		}
		String PEAddress = "";
		String PEDate = "";
		String NeedLimosis = "";
		if (tLCPENoticeSet.size() >= 1)
		{
			tLCPENoticeSchema.setSchema(tLCPENoticeSet.get(1).getSchema());
			Pereason = tLCPENoticeSchema.getPEReason(); // 保存体检原因
			HealthName = tLCPENoticeSchema.getName(); // 保存体检人名称
			PEDate = tLCPENoticeSchema.getPEDate(); // 保存体检日期

			if (tLCPENoticeSchema.getPEBeforeCond().equals("Y"))
			{ // 是否需要空腹
				NeedLimosis = " ";
			}
			else
			{
				NeedLimosis = "不";
			}

		}
		else
		{
			mErrors.copyAllErrors(tLCPENoticeSet.mErrors);
			buildError("outputXML", "在取得体检信息的数据时发生错误");
			return false;
		}

		// 2-1 查询体检子表
		String PEITEM = "";
		String[] PETitle = new String[2];
		PETitle[0] = "ID";
		PETitle[1] = "CHECKITEM";
		ListTable tPEListTable = new ListTable();
		String strPE[] = null;
		tPEListTable.setName("CHECKITEM"); // 对应模版体检部分的行对象名
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String ItemSql = "select PEItemName  from lcpenoticeitem where prtseq = '" + "?OldPrtNo?" + "' and contno = '"
				+ "?contno?" + "'" + " and FreePE = 'N' order by peitemcode";
		sqlbv1.sql(ItemSql);
		sqlbv1.put("OldPrtNo", OldPrtNo);
		sqlbv1.put("contno", mLCContSchema.getContNo());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		if (tSSRS.getMaxRow() == 0)
		{
			PEITEM = "";
		}
		else
		{
			for (int j = 1; j <= tSSRS.getMaxRow(); j++)
			{
				PEITEM = PEITEM + tSSRS.GetText(j, 1) + "  ";

			}
		}

		// 营业部及营销服务部。
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
		if (tLABranchGroupDB.getInfo() == false)
		{
			mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
			buildError("outputXML", "在取得营业部及营销服务部数据时发生错误");
			return false;
		}
		mLABranchGroupSchema = tLABranchGroupDB.getSchema();

		// 体检人性别和年龄
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(tLCPENoticeSchema.getCustomerNo());
		if (tLDPersonDB.getInfo() == false)
		{
			mErrors.copyAllErrors(tLDPersonDB.mErrors);
			buildError("outputXML", "在取得打印队列中数据时发生错误");
			return false;
		}
		mLDPersonSchema = tLDPersonDB.getSchema();
		FDate fdate = new FDate();
		fdate.getDate(PubFun.getCurrentDate());
		// 其它模版上单独不成块的信息
		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExportNew xmlexport = new XmlExportNew(); // 新建一个XmlExport的实例
//		xmlexport.createDocument("体检通知书", "NewPeNotice.vts", "0"); // 最好紧接着就初始化xml文档
		xmlexport.createDocument("体检通知函", "NewPeNotice.vts", "0" ,"0");

		
		// 生成-年-月-日格式的日期
		// StrTool tSrtTool = new StrTool();
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" + StrTool.getDay() + "日";
		PEDate = PEDate + "-";
		String CheckDate = StrTool.decodeStr(PEDate, "-", 1) + "年" + StrTool.decodeStr(PEDate, "-", 2) + "月"
				+ StrTool.decodeStr(PEDate, "-", 3) + "日";
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		String tSql = " select riskprop from lmriskapp where riskcode "
				+ " in (select riskcode from lcpol where contno='" + "?contno?"
				+ "' and mainpolno = polno)";
		sqlbv2.sql(tSql);
		sqlbv2.put("contno", mLCContSchema.getContNo());
		String tRiskType = tExeSQL.getOneValue(sqlbv2);
		if (tRiskType != null && tRiskType.equals("Y"))
		{
			LAComSchema mLAComSchema = new LAComSchema();
			LAComDB mLAComDB = new LAComDB();
			mLAComDB.setAgentCom(mLCContSchema.getAgentCom());
			if (!mLAComDB.getInfo())
			{
				mErrors.copyAllErrors(mLAComDB.mErrors);
				buildError("outputXML", "在取得LACom的数据时发生错误");
				return false;
			}
			mLAComSchema = mLAComDB.getSchema(); // 保存银行代码信息

			texttag.add("LCCont.BankCode", mLAComSchema.getName()); // 代理机构
			texttag.add("LCCont.AgentType", tLABranchGroupDB.getName()); // 业务分布及业务组.
		}

		// xmlexport.addListTable(tHosListTable, HosTitle); //体检医院信息

		/**
		 * ====================================================================
		 * ====== 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
		 * LABranchGroup.Name：取8位编码的营业销售部
		 * ========================================
		 * ==================================
		 */
		// 中支机构名称
		// String tManageComCode = mLCContSchema.getManageCom().substring(0, 6);
		// String tManageComName = getComName(tManageComCode);
		// 营业销售部名称
		// String tBranchGroupCode = mLCContSchema.getManageCom();
		// String tBranchGroupName = getComName(tBranchGroupCode);
		String tGroup = "";
		// 中支机构名称
		// 中支机构名称
		String tManageComCode = mLCContSchema.getManageCom();
		// if (mLCContSchema.getManageCom().length() > 6) {
		// tManageComCode = mLCContSchema.getManageCom().substring(0, 6);
		// }
		String tManageComName = getComName(tManageComCode);
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		String SQL = "select appntsex from lcappnt where contno='" + "?contno?" + "'";
		sqlbv3.sql(SQL);
		sqlbv3.put("contno", mLCContSchema.getContNo());
		String Sql = "select salechnl,agentcom from lccont where contno='" + "?contno?" + "'";
		sqlbv4.sql(Sql);
		sqlbv4.put("contno", mLCContSchema.getContNo());
		String tAppntSex = "";
		String tName = "";
		String tCode = "";
		logger.debug("SQL=" + SQL);
		logger.debug("Sql=====" + Sql);
		ExeSQL texeSQL = new ExeSQL();
		SSRS sexSSRS = new SSRS();
		SSRS nSSRS = new SSRS();
		sexSSRS = texeSQL.execSQL(sqlbv3);
		nSSRS = texeSQL.execSQL(sqlbv4);
		if (sexSSRS.GetText(1, 1).equals("0"))
		{
			tAppntSex = "先生";

		}
		else if (sexSSRS.GetText(1, 1).equals("1"))
		{
			tAppntSex = "女士";
		}

		if (nSSRS.GetText(1, 1).equals("1"))
		{
			// 营业部代码
			tGroup = "机构代码: " + mLCContSchema.getManageCom();
			// tBranchGroupName = getComName(tBranchGroupCode);
			tName = "业务员姓名及编码: " + mLAAgentSchema.getName();
			tCode = mLCContSchema.getAgentCode();

		}
		else if (nSSRS.GetText(1, 1).equals("3"))
		{
			tGroup = "银行及储蓄所编码: " + nSSRS.GetText(1, 2);
			tName = "客户经理姓名及代码: " + mLAAgentSchema.getName();
			tCode = mLCContSchema.getAgentCode();
		}

		// 模版自上而下的元素
		texttag.add("BarCode1", PrintManagerBL.CODE_PE);
		texttag.add("BarCodeParam1", "BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
		texttag.add("BarCodeParam2", "BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("Post", mLCAddressSchema.getZipCode()); // 投保人邮政编码
		texttag.add("Address", mLCAddressSchema.getPostalAddress()); // 投保人地址
		texttag.add("AppntName", mLCContSchema.getAppntName());
		texttag.add("LCCont.AppntName", tLCPENoticeSchema.getAppName()); // 投保人名称
		texttag.add("IDNO", getIDNO(tLCPENoticeSchema.getCustomerNo()));
		texttag.add("LCCont.ContNo", mLCContSchema.getContNo()); // 投保单号
		texttag.add("LCCont.InsuredName", mLCContSchema.getInsuredName());// 被保险人
		texttag.add("CheckDate", CheckDate); // 体检日期
		texttag.add("HealthName", HealthName); // 被保险人名称
		// texttag.add("Sex", getSexName(mLDPersonSchema.getSex())); //体检人性别
		texttag.add("Age", PubFun.calInterval(mLDPersonSchema.getBirthday(), PubFun.getCurrentDate(), "Y")); // 体检人年龄
		texttag.add("Birthday", mLDPersonSchema.getBirthday()); // 体检人的出生日期
//		texttag.add("WorkType", getWork(mLDPersonSchema.getOccupationCode())); // 体检人的职业
		texttag.add("CHECKREASON", Pereason); // 体检原因
		// logger.debug("Remark=" + Remark);

		texttag.add("NeedLimosis", NeedLimosis); // 是否需要空腹
		texttag.add("Hospital", PEAddress); // 体检地点
		texttag.add("PEBrachAddress", hospitalAdress);
		// texttag.add("LAAgent.AgentCode", mLCContSchema.getAgentCode());
		// //代理人业务号
		texttag.add("LCCont.Managecom", tManageComName); // 营业机构
		// texttag.add("LABranchGroup.Name",
		// tBranchGroupName + " " +
		// getUpComName(mLAAgentSchema.getBranchCode())); //营业部
		texttag.add("Group", tGroup);
		texttag.add("AppntSex", tAppntSex);
		texttag.add("LAAgent.Name", tName);
		texttag.add("LAAgent.AgentCode", tCode);
		texttag.add("PrtNo", PrtNo); // 刘水号
		texttag.add("LCCont.PrtNo", mLCContSchema.getPrtNo()); // 印刷号
		texttag.add("LCCont.UWOperator", tLCPENoticeSchema.getOperator()); // 核保师代码
		texttag.add("SysDate", SysDate);
		texttag.add("CHECKITEM", PEITEM);// 体检项目
		if (texttag.size() > 0)
		{
			xmlexport.addTextTag(texttag);
		}

		xmlexport.addListTable(tListTable, tTitle);
		
		

		// 测试用例开始
		xmlexport.createTitle("Company");
		xmlexport.addElement("Company", "ManageCom", mLCContSchema.getManageCom());
		xmlexport.addElement("Company", "ManageName", "安邦人寿成都新都支公司营销服务一部");
		xmlexport.addElement("Company", "Address", "");

		xmlexport.createTitle("Medical");
		xmlexport.addElement("Medical", "CheckItem", "物理检查  尿常规  心电图    ");
		xmlexport.addElement("Medical", "CheckReason", "达标体检");
		xmlexport.addElement("Medical", "NeedLimosis", "");

		tListTable = new ListTable();
		tTitle = new String[3];
		tTitle[0] = "Name";
		tTitle[1] = "Sex";
		tTitle[2] = "Phone";
		tListTable.setName("AppntInfo");

		for (int j = 1; j <= 2; j++)
		{
			StrCont = new String[3];
			StrCont[0] = "投保人姓名0" + j;
			StrCont[1] = "投保人性别1" + j;
			StrCont[2] = "投保人电话2" + j;
			tListTable.add(StrCont);
		}
		xmlexport.addListTable(tListTable, tTitle, "Appnt");

		tListTable = new ListTable();
		tTitle = new String[3];
		tTitle[0] = "Name";
		tTitle[1] = "Sex";
		tTitle[2] = "Phone";
		tListTable.setName("InsuredInfo");

		for (int j = 1; j <= 2; j++)
		{
			StrCont = new String[3];
			StrCont[0] = "被保人名称0" + j;
			StrCont[1] = "被保人性别1" + j;
			StrCont[2] = "被保人电话2" + j;
			tListTable.add(StrCont);
		}
		xmlexport.addListTable(tListTable, tTitle, "Insured");

		tListTable = new ListTable();
		tTitle = new String[3];
		tTitle[0] = "Code";
		tTitle[1] = "Param";
		tTitle[2] = "Name";
		tListTable.setName("BarCodeInfo");

		for (int j = 1; j <= 2; j++)
		{
			StrCont = new String[3];
			if (j == 1)
			{
				StrCont[0] = PrintManagerBL.CODE_PE;
				StrCont[2] = "CertifyCode";
			}
			else 
			{
				StrCont[0] = mLCContSchema.getContNo();
				StrCont[2] = "ContNo";
			}
			StrCont[1] = "BarHeight=20&amp;BarRation=3&amp;BarWidth=1&amp;BgColor=FFFFFF&amp;ForeColor=000000&amp;XMargin=10&amp;YMargin=10";
			tListTable.add(StrCont);
		}
		xmlexport.addListTable(tListTable, tTitle, "BarCode");

//		xmlexport.outputDocumentToFile("c:\\", "bodytest"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	/**
	 * 得到通过机构代码得到机构名称
	 * @param strComCode String
	 * @return String
	 */
	private String getComName(String strComCode)
	{
		mSql = "select CodeName from LDcode where Code = '" + "?strComCode?" + "' and CodeType = 'station'";
		sqlbv.sql(mSql);
		sqlbv.put("strComCode", strComCode);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv);

	}

	private String getIDNO(String customerno)
	{
		mSql = "select idno from ldperson where customerno = '" + "?customerno?" + "'";
		sqlbv.sql(mSql);
		sqlbv.put("customerno", customerno);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv);

	}

	/**
	 * 获取性别
	 * @param StrSex String
	 * @return String
	 */
	private String getSexName(String StrSex)
	{
		mSql = "select CodeName from LDcode where Code = '" + "?StrSex?" + "' and CodeType = 'sex'";
		sqlbv.sql(mSql);
		sqlbv.put("StrSex", StrSex);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv);

	}

	public String getUpComName(String comcode)
	{
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupDB ttLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(comcode);
		if (!tLABranchGroupDB.getInfo())
		{
			this.buildError("getUpComName", comcode + "机构不存在！");
			return null;
		}
		else
		{
			ttLABranchGroupDB.setAgentGroup(tLABranchGroupDB.getUpBranch());
			if (!ttLABranchGroupDB.getInfo())
			{
				this.buildError("getUpComName", "在查找comcode所属的营业部时出错！");
				return null;
			}
			else
			{
				return ttLABranchGroupDB.getName();
			}
		}
	}

	/**
	 * 获取体检人职业
	 */
	private String getWork(String StrWork)
	{
		mSql = "select occupationname from ldoccupation where occupationcode='" + "?StrWork?" + "' ";
		sqlbv.sql(mSql);
		sqlbv.put("StrWork", StrWork);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv);

	}

}
