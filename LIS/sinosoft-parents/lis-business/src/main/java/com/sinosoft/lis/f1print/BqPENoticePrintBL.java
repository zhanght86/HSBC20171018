package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPPENoticeDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPPENoticeSchema;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPPENoticeSet;
import com.sinosoft.lis.vschema.LWMissionSet;
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
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全体检通知书打印类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author：liurx
 * @version：1.0
 * @CreateDate：2005-08-23
 */
public class BqPENoticePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(BqPENoticePrintBL.class);
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
	private LPContSchema mLPContSchema = new LPContSchema();
	private LPPENoticeSchema mLPPENoticeSchema = new LPPENoticeSchema();

	private String mContNo; // 合同号
	private String mCustomerNo; // 客户号
	private String mName; // 客户姓名
	private String mBirthDay;
	private String mOccupation;
	private boolean specFlag = false; // 是否显示特殊要求
	private String mSpec; // 特殊要求
	private String mPEDate; // 体检日期
	private String mAddress = ""; // 体检地址(体检医院地址+体检医院名称)
	private String mTel = ""; // 体检医院电话
	private String mUWOperator = ""; // 核保师
	private String mMakeDate; // 生成通知书时间
	private String mAgentCode; // 业务员编码
	private String mBank = "";
	private String mPrtSeq = ""; // 体检表的流水号
	private String mOldPrtSeq = ""; // 体检表的流水号
	private String mSaleChnl = "";
	private String mNameOnly = "";
	private String mIDNo = "";
	private String mMissionID = "";
	private String mSubMissionID = "";
	private String mEdorNo = "";

	public BqPENoticePrintBL() {
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

		if (!mOperate.equals("PRINT")) {
			CError.buildErr(this, "不支持的操作字符串！") ;
			return false;
		}

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据校验操作（checkdata)
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

	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));
		TransferData tTransferData = new TransferData();
		tTransferData = (TransferData)cInputData.getObjectByObjectName(
				"TransferData", 0);
		
		mMissionID = (String)tTransferData.getValueByName("MissionID");
		mSubMissionID = (String)tTransferData.getValueByName("SubMissionID");
		if (mGlobalInput == null || mLOPRTManagerSchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mPrtSeq = mLOPRTManagerSchema.getPrtSeq();
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

		mContNo = mLOPRTManagerSchema.getOtherNo();

		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "获取保单号失败！") ;
			return false;
		}

		mCustomerNo = mLOPRTManagerSchema.getStandbyFlag1();
		if (mCustomerNo == null || mCustomerNo.trim().equals("")) {
			// @@错误处理
			CError.buildErr(this, "获取体检人客户号失败！") ;
			return false;
		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = new LWMissionSet();
		tLWMissionDB.setMissionID(mMissionID);
		tLWMissionDB.setSubMissionID(mSubMissionID);
		tLWMissionDB.setActivityID("0000000302");
		tLWMissionSet = tLWMissionDB.query();
		if(tLWMissionSet == null || tLWMissionSet.size()!=1)
		{
			CError.buildErr(this, "保全打印体检通知书工作流查询失败！");
			return false;
		}
		mOldPrtSeq = tLWMissionSet.get(1).getMissionProp14();
		mEdorNo=tLWMissionSet.get(1).getMissionProp8();
		LPContDB tLPContDB = new LPContDB();
		LPContSet tLPContSet = new LPContSet();
		tLPContDB.setEdorNo(mEdorNo);
		tLPContDB.setContNo(mContNo);
		tLPContSet = tLPContDB.query();
		if (tLPContSet == null || tLPContSet.size()!=1) {
			CError.buildErr(this, "无保单信息！");
			return false;
		}
		mLPContSchema = tLPContSet.get(1);

		return true;
	}

	private boolean getBaseData() {
		return true;
	}

	private boolean preparePrintData() {
		XmlExportNew tXmlExport = new XmlExportNew();
		tXmlExport.createDocument("保全体检通知书");// 新建一个XmlExport的实例
		PrintTool.setBarCode(tXmlExport, mLOPRTManagerSchema.getPrtSeq());//条形码
		String uLanguage = PrintTool.getCustomerLanguage(mCustomerNo);
		if (uLanguage != null && !"".equals(uLanguage)) 
			tXmlExport.setUserLanguage(uLanguage);
		tXmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言
		
		mAgentCode = mLPContSchema.getAgentCode();
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mAgentCode);
		LAAgentSchema tLAAgentSchema = tLAAgentDB.query().get(1).getSchema();
		
		// 2-体检信息
		// 2-1 查询体检主表
		LPPENoticeDB tLPPENoticeDB = new LPPENoticeDB();
		LPPENoticeSchema tLPPENoticeSchema = new LPPENoticeSchema();
		LPPENoticeSet tLPPENoticeSet = new LPPENoticeSet();
		if(mOldPrtSeq!=null && !mOldPrtSeq.equals(""))
			tLPPENoticeDB.setPrtSeq(mOldPrtSeq);
		else
			tLPPENoticeDB.setPrtSeq(mPrtSeq);
		tLPPENoticeSet = tLPPENoticeDB.query();
		String PEAddress = "";
		String PEHospitName = "";
		String PEDate = "";
		String NeedLimosis = "";
		String Remark = "";
		if (tLPPENoticeSet.size() >= 1) {
			tLPPENoticeSchema.setSchema(tLPPENoticeSet.get(1).getSchema());
			Remark = tLPPENoticeSchema.getRemark(); // 保存特殊要求
			PEDate = tLPPENoticeSchema.getPEDate(); // 保存体检日期

			if (tLPPENoticeSchema.getPEBeforeCond()!=null && tLPPENoticeSchema.getPEBeforeCond().equals("Y")) { // 是否需要空腹
				NeedLimosis = "是";
			} else {
				NeedLimosis = "否";
			}

		} else {
			mErrors.copyAllErrors(tLPPENoticeSet.mErrors);
			CError.buildErr(this, "在取得体检信息的数据时发生错误");
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
		logger.debug("分公司代码："
				+ mLPContSchema.getManageCom().substring(0, 4));
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		strSql = "select hospitalname,address from ldhospital where 1=1 "
					+ "and mngcom like concat('%',concat(concat('',substr("+"?ManageCom?"+",1,8)),'%')) order by char_length(trim(hospitalname))";
		sqlbv1.sql(strSql);
		sqlbv1.put("ManageCom", mLPContSchema.getManageCom());
		SSRS hosSSRS = new SSRS();
		ExeSQL hosExeSQL = new ExeSQL();
		hosSSRS = hosExeSQL.execSQL(sqlbv1);
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

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		SSRS tssrs = new SSRS();
		ExeSQL texesql = new ExeSQL();
		String strsql = "";
		// 2-1 查询体检子表
		String PEITEM = "";
		String[] PETitle = new String[2];
		PETitle[0] = "ID";
		PETitle[1] = "CHECKITEM";
		ListTable tPEListTable = new ListTable();
		ListTable tPEItemListTable = new ListTable();
		String[] strRiskInfo = null;
		String strPE[] = null;
		tPEListTable.setName("CHECKITEM"); // 对应模版体检部分的行对象名
		SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		String ItemSql = "select PEItemName  from lppenoticeitem where prtseq = '";
			if(mOldPrtSeq!=null && !mOldPrtSeq.equals(""))
				ItemSql = ItemSql+ "?mOldPrtSeq?";
			else
				ItemSql = ItemSql+ "?mPrtSeq?";
			ItemSql = ItemSql+ "' and contno = '"
				+ "?contno?"
				+ "'" 
				//+ " and FreePE = 'N' "
				+ " order by peitemcode";
			sqlbv2.sql(ItemSql);
			sqlbv2.put("mOldPrtSeq", mOldPrtSeq);
			sqlbv2.put("mPrtSeq", mPrtSeq);
			sqlbv2.put("contno", mLPContSchema.getContNo());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv2);
		/*if (tSSRS.getMaxRow() == 0) {
		} else {
			for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
					PEITEM = PEITEM + i + "." + tSSRS.GetText(i, 1) + "  ";
					strRiskInfo = new String[2];
					strRiskInfo[0] = String.valueOf(i);
					strRiskInfo[1] = tSSRS.GetText(i, 1);
					tPEItemListTable.add(strRiskInfo);
			}
		}*/
		//ln 2009-05-14 调整体检内容的显示
		if (tSSRS.getMaxRow() == 0) {
		}
		else
		{
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
		
		String tCom = mLPContSchema.getManageCom();
		if (tCom != null && tCom.length() >= 4) {
			tCom = mLPContSchema.getManageCom().substring(0, 4);
		}	
		// 体检人性别和年龄
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(tLPPENoticeSchema.getCustomerNo());
		if (tLDPersonDB.getInfo() == false) {
			mErrors.copyAllErrors(tLDPersonDB.mErrors);
			CError.buildErr(this, "在取得打印队列中数据时发生错误");
			return false;
		}
		LDPersonSchema mLDPersonSchema = tLDPersonDB.getSchema();
		PEDate = PEDate + "-";
		/*String CheckDate = StrTool.decodeStr(PEDate, "-", 1) + "年"
				+ StrTool.decodeStr(PEDate, "-", 2) + "月"
				+ StrTool.decodeStr(PEDate, "-", 3) + "日";*/
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
		+ StrTool.getDay() + "日";
		
		//zy 2009-05-14 回复日期 为发送日期10日后和保单生效日期最小者
		String replyDate1 = PubFun.calDate(mLOPRTManagerSchema.getMakeDate(), 10, "D","" );
//		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
//		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
//		tLPEdorItemDB.setEdorNo(mLPContSchema.getEdorNo());
//		tLPEdorItemDB.setContNo(mLPContSchema.getContNo());
//		tLPEdorItemSet = tLPEdorItemDB.query();
//		if(tLPEdorItemSet == null || tLPEdorItemSet.size()<1)
//		{
//			CError.buildErr(this, "LPEdorItem查询失败！");
//			return false;
//		}
//		String replyDate2 = tLPEdorItemSet.get(1).getEdorValiDate();//生效日期
//		String ReplyYear = replyDate.substring(0,4);
//		String ReplyMon =  replyDate.substring(5,7);
//		String ReplyDay =  replyDate.substring(8,10);
		String today = PubFun.getCurrentDate();
		String replyDate = replyDate1;
		String replyDate2 = PubFun.calDate(mLPContSchema.getCValiDate(), PubFun.calPolYear(mLPContSchema.getCValiDate(), today), "Y","" );//生效日期
		String replyDateA = PubFun.getBeforeDate(mLOPRTManagerSchema.getMakeDate(),replyDate2) ;
		if(!replyDateA.equals(replyDate2))
			replyDate = PubFun.getBeforeDate(replyDate1,replyDate2) ;

		replyDate = replyDate + "-";
		String CheckDate = StrTool.decodeStr(replyDate, "-", 1) + "年"
			+ StrTool.decodeStr(replyDate, "-", 2) + "月"
			+ StrTool.decodeStr(replyDate, "-", 3) + "日";
		tXmlExport.addListTable(tHosListTable, HosTitle); // 体检医院信息
		texttag.add("ContNo", mContNo);
		texttag.add("AppntName", mLPContSchema.getAppntName());
		String date = mLPContSchema.getCValiDate();
		date = date + "-";
		String ldate = StrTool.decodeStr(date, "-", 1) + "年"
		+ StrTool.decodeStr(date, "-", 2) + "月"
		+ StrTool.decodeStr(date, "-", 3) + "日";
		texttag.add("Date", ldate);
		texttag.add("WorkType", mOccupation);
		/*String sCheckDate = PubFun.calDate(PubFun.getCurrentDate(), 7, "D",PubFun.getCurrentDate()) + "-";
		sCheckDate = StrTool.decodeStr(sCheckDate, "-", 1) + "年"
		+ StrTool.decodeStr(sCheckDate, "-", 2) + "月"
		+ StrTool.decodeStr(sCheckDate, "-", 3) + "日";
		texttag.add("CheckDate", sCheckDate); // 体检日期(后第十日)*/
		texttag.add("CheckDate", CheckDate);
		texttag.add("AgentCode", mAgentCode);
		texttag.add("InsuredName", tLPPENoticeSchema.getName());//体检人-被保险人
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		String sql = "select a.sex,get_age(a.birthday,c.polapplydate),a.idno,(select mobile from lpaddress where edorno=a.edorno and customerno=a.insuredno and addressno = a.addressno) from lpinsured a,lpcont c where a.contno=c.contno and a.edorno=c.edorno "
		       + " and a.insuredno='"+"?insuredno?"+"' and a.edorno='"+"?mEdorNo?"+"' and a.contno='"+ "?contno?" +"'";			
		if("A".equals(tLPPENoticeSchema.getCustomerNoType()))
		{
			sql = "select a.appntsex,get_age(a.appntbirthday,'"+"?CurrentDate?"+"'),a.idno,(select mobile from lpaddress where edorno=a.edorno and customerno=a.appntno and addressno = a.addressno) from lpappnt a where 1=1 "
			       + " and a.edorno='"+"?mEdorNo?"+"' and a.contno='"+ "?contno?" +"'";
		}
		sqlbv3.sql(sql);
		sqlbv3.put("insuredno", tLPPENoticeSchema.getCustomerNo());
		sqlbv3.put("mEdorNo", mEdorNo);
		sqlbv3.put("contno", tLPPENoticeSchema.getContNo());
		sqlbv3.put("CurrentDate", PubFun.getCurrentDate());
		
		tSSRS = new SSRS();
		tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv3);
		String Sex ="";
		String Age ="";
		String IDNo ="";
		String Mobile ="";
		if (tSSRS.getMaxRow() > 0) {
			Sex =tSSRS.GetText(1, 1);
			Age =tSSRS.GetText(1, 2);
			IDNo =tSSRS.GetText(1, 3);
			Mobile =tSSRS.GetText(1, 4);
		}		

		texttag.add("Sex", getSexName(Sex)); //
		texttag.add("Age", Age); // 
		texttag.add("IDNo", IDNo); // 
		texttag.add("Mobile", Mobile); // 		
		texttag.add("PrtSeq", mPrtSeq);	//流水号
		String PEBefore = "";
		if(tLPPENoticeSchema.getPEBeforeCond()!=null )
		{
			if(tLPPENoticeSchema.getPEBeforeCond().equals("Y"))
				PEBefore = "是";
			else
				PEBefore = "否";
		}			
		texttag.add("PEBefore", PEBefore);//是否空腹
		texttag.add("HospitName", PEHospitName); // 体检地点
		texttag.add("HospitAddress", PEAddress); // 体检地点	
		texttag.add("Name", tLAAgentSchema.getName()); // 代理人姓名
		texttag.add("AgentCode", mAgentCode); // 代理人业务号
		String tManageComCode = mLPContSchema.getManageCom().substring(0, 6);
		String tManageComName = getComName(tManageComCode);
		texttag.add("ManageCom", tManageComName); // 营业机构
		texttag.add("UWOperator", mLOPRTManagerSchema.getReqOperator()); // 核保师代码
		//add 保全员工号
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		String BQOperator_sql="select operator from lpedoritem where edorno='"+"?mEdorNo?"+"'";
		sqlbv5.sql(BQOperator_sql);
		sqlbv5.put("mEdorNo", mEdorNo);
		texttag.add("BQOperator", tExeSQL.getOneValue(sqlbv5)); // 保全员工号
		texttag.add("RePrintDate", SysDate);
		texttag.add("SysDate", SysDate);
		String PEDownDate = mLOPRTManagerSchema.getMakeDate() + "-";
		PEDownDate = StrTool.decodeStr(PEDownDate, "-", 1) + "年"
				+ StrTool.decodeStr(PEDownDate, "-", 2) + "月"
				+ StrTool.decodeStr(PEDownDate, "-", 3) + "日";
		texttag.add("PEDownDate", PEDownDate);//体检下发日期
		
		if (texttag.size() > 0) {
			tXmlExport.addTextTag(texttag);
		}
		// 设置列表名字和字段名
		strRiskInfo = new String[2];
		strRiskInfo[0] = "Index";
		strRiskInfo[1] = "PEItem";

		tPEItemListTable.setName("PEItem");

		tXmlExport.addListTable(tPEItemListTable, strRiskInfo);
		mResult.clear();
		mResult.addElement(tXmlExport);

		return true;

	}
	
	/**
	 * 获取性别
	 * 
	 * @param StrSex
	 *            String
	 * @return String
	 */
	private String getSexName(String StrSex) {
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		String mSql = "select CodeName from LDcode where Code = '" + "?StrSex?"
				+ "' and CodeType = 'sex'";
		sqlbv6.sql(mSql);
		sqlbv6.put("StrSex", StrSex);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv6);

	}
	
	 /* 对打印的文字过长一行显示不完时作调整
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
	
	/**
	 * 得到通过机构代码得到机构名称
	 * 
	 * @param strComCode
	 *            String
	 * @return String
	 */
	private String getComName(String strComCode) {
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		String mSql = "select CodeName from LDcode where Code = '" + "?strComCode?"
				+ "' and CodeType = 'station'";
		sqlbv7.sql(mSql);
		sqlbv7.put("strComCode", strComCode);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv7);

	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public static void main(String[] args) {
		logger.debug("test begin:");
		BqPENoticePrintBL tBqPENoticePrintBL = new BqPENoticePrintBL();

		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "001";

		String tPrtSeq = "810000000019170";
		LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
		tLOPRTManagerSchema.setPrtSeq(tPrtSeq);

		tVData.add(tGlobalInput);
		tVData.add(tLOPRTManagerSchema);
		if (!tBqPENoticePrintBL.submitData(tVData, "PRINT")) {
			logger.debug("test failed");
		}
		logger.debug("test end");
	}
}
