package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDPersonDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.db.RnewPENoticeDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LDPersonSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.schema.RnewPENoticeSchema;
import com.sinosoft.lis.vschema.RnewPENoticeSet;
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
import com.sinosoft.utility.XmlExport;

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
public class RnewPENoticePrintBL implements PrintService {
private static Logger logger = Logger.getLogger(RnewPENoticePrintBL.class);
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
	private RnewPENoticeSchema mRnewPENoticeSchema = new RnewPENoticeSchema();

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
//	public static final String VTS_NAME = "RnewPENotice.vts"; // 模板名称
	public RnewPENoticePrintBL() {
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
			CError tError = new CError();
			tError.moduleName = "BqPENoticePrintBL";
			tError.functionName = "submitData";
			tError.errorMessage = "不支持的操作字符串！";
			this.mErrors.addOneError(tError);
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
		tLOPRTManagerDB.setPrtSeq(mPrtSeq);
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			mErrors.addOneError(new CError("在取得打印队列中数据时发生错误"));
			return false;
		}
		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema();
		mOldPrtSeq = mLOPRTManagerSchema.getOldPrtSeq();
		mContNo = mLOPRTManagerSchema.getOtherNo();

		if (mContNo == null || mContNo.trim().equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "BqPENoticePrintBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "获取保单号失败！";
			this.mErrors.addOneError(tError);

			return false;
		}
//不获取客户号码了。客户号码在服务类中已经被注掉
//		mCustomerNo = mLOPRTManagerSchema.getStandbyFlag1();
//		if (mCustomerNo == null || mCustomerNo.trim().equals("")) {
//			// @@错误处理
//			CError tError = new CError();
//			tError.moduleName = "BqPENoticePrintBL";
//			tError.functionName = "getInputData";
//			tError.errorMessage = "获取体检人客户号失败！";
//			this.mErrors.addOneError(tError);
//			return false;
//		}
		return true;
	}

	/**
	 * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkData() {
//		LWMissionDB tLWMissionDB = new LWMissionDB();
//		LWMissionSet tLWMissionSet = new LWMissionSet();
//		tLWMissionDB.setMissionID(mMissionID);
//		tLWMissionDB.setSubMissionID(mSubMissionID);
//		tLWMissionDB.setActivityID("0000007007");
//		tLWMissionSet = tLWMissionDB.query();
//		if(tLWMissionSet == null || tLWMissionSet.size()!=1)
//		{
//			CError.buildErr(this, "保全打印体检通知书工作流查询失败！");
//			return false;
//		}
//		mOldPrtSeq = tLWMissionSet.get(1).getMissionProp14();
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(mContNo);
		if (!tLCContDB.getInfo()) {
			CError.buildErr(this, "无保单信息！");
			return false;
		}
		mLCContSchema = tLCContDB.getSchema();

		return true;
	}

	private boolean getBaseData() {
		return true;
	}

	private boolean preparePrintData() {
		XmlExport tXmlExport = new XmlExport();
//		tXmlExport.createDocument(VTS_NAME, "printer"); // 初始化xml文档
		//ZY 2009-05-18 调整模板名称
		tXmlExport.createDocument("RnewPENotice_MS.vts", "printer"); // 初始化xml文档
		
		mAgentCode = mLCContSchema.getAgentCode();
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(mAgentCode);
		LAAgentSchema tLAAgentSchema = tLAAgentDB.query().get(1).getSchema();
		
		// 2-体检信息
		// 2-1 查询体检主表
		RnewPENoticeDB tRnewPENoticeDB = new RnewPENoticeDB();
		RnewPENoticeSchema tRnewPENoticeSchema = new RnewPENoticeSchema();
		RnewPENoticeSet tRnewPENoticeSet = new RnewPENoticeSet();
		if(mOldPrtSeq!=null && !"".equals(mOldPrtSeq))
			tRnewPENoticeDB.setPrtSeq(mOldPrtSeq);
		else
			tRnewPENoticeDB.setPrtSeq(mPrtSeq);
		
//		if(mPrtSeq!=null && !mPrtSeq.equals("")){
//			//二次进入时候将打印流水号码给更新了
//			//自己修改的，有问题-------
////			String tsql = "select * from RnewPENotice where 1=1 and Contno = '"
////				+ mContNo + "' and PrtSeq = '"+mLOPRTManagerSchema.getOldPrtSeq()+"'";
////			logger.debug("tSql==" + tsql);
////			RnewPENoticeSet mRnewPENoticeSet = tRnewPENoticeDB.executeQuery(tsql);
////			if (mRnewPENoticeSet.size() > 0 ) {
////				tsql = "update RnewPENotice set PrtSeq ='"+mPrtSeq+"' where Contno = '"
////				+ mContNo + "'";
////				RnewPENoticeSet mmRnewPENoticeSet = tRnewPENoticeDB.executeQuery(tsql);
////			}
//			tRnewPENoticeDB.setPrtSeq(mPrtSeq);
//			
//		}else{
//			tRnewPENoticeDB.setPrtSeq(mPrtSeq);}
		tRnewPENoticeSet = tRnewPENoticeDB.query();
		String PEAddress = "";
		String PEHospitName = "";
		String PEDate = "";
		String NeedLimosis = "";
		String Remark = "";
		if (tRnewPENoticeSet.size() >= 1) {
			tRnewPENoticeSchema.setSchema(tRnewPENoticeSet.get(1).getSchema());
			Remark = tRnewPENoticeSchema.getRemark(); // 保存特殊要求
			PEDate = tRnewPENoticeSchema.getPEDate(); // 保存体检日期

			if (tRnewPENoticeSchema.getPEBeforeCond()!=null && tRnewPENoticeSchema.getPEBeforeCond().equals("Y")) { // 是否需要空腹
				NeedLimosis = "是";
			} else {
				NeedLimosis = "否";
			}

		} else {
			mErrors.copyAllErrors(tRnewPENoticeSet.mErrors);
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
				+ mLCContSchema.getManageCom().substring(0, 4));
	
		strSql = "select hospitalname,address from ldhospital where 1=1 "
					+ "and mngcom like concat('%',concat(concat('' , substr("+"?mngcom?"+",1,8)) ,'%')) order by char_length(trim(hospitalname))";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(strSql);
		sqlbv1.put("mngcom", mLCContSchema.getManageCom());
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

		String ItemSql = "select PEItemName  from RnewPENoticeitem where prtseq = '";
			if(mPrtSeq!=null && !mPrtSeq.equals(""))
				ItemSql = ItemSql+ "?mPrtSeq?";
			else
				ItemSql = ItemSql+ "?mPrtSeq?";
			ItemSql = ItemSql+ "' and contno = '"
				+ "?contno?"
				+ "'" 
				//+ " and FreePE = 'N' "
				+ " order by peitemcode";
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(ItemSql);
			sqlbv2.put("mPrtSeq", mPrtSeq);
			sqlbv2.put("contno", mLCContSchema.getContNo());
		SSRS tSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv2);
		String	content="";
		if (tSSRS.getMaxRow() == 0) {
		} else {
			if(tSSRS.getMaxRow()==1)
				content=tSSRS.GetText(1, 1) + "  ";
			else
			{
				for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
	//					PEITEM = PEITEM + i + "." + tSSRS.GetText(i, 1) + "  ";
	//					strRiskInfo = new String[2];
	//					strRiskInfo[0] = String.valueOf(i);
	//					strRiskInfo[1] = tSSRS.GetText(i, 1);
	//					tPEItemListTable.add(strRiskInfo);
					//zy 2009-05-18 调整体检内容的显示
	
					if(i==1)
						content=i + "." + tSSRS.GetText(i, 1); 
					else
					content = content+";"+i + "." + tSSRS.GetText(i, 1); 
						
				}
			}
			getContent(tPEItemListTable, content ,50);
		}
		
		
		String tCom = mLCContSchema.getManageCom();
		if (tCom != null && tCom.length() >= 4) {
			tCom = mLCContSchema.getManageCom().substring(0, 4);
		}	
		// 体检人性别和年龄
		LDPersonDB tLDPersonDB = new LDPersonDB();
		tLDPersonDB.setCustomerNo(tRnewPENoticeSchema.getCustomerNo());
		if (tLDPersonDB.getInfo() == false) {
			mErrors.copyAllErrors(tLDPersonDB.mErrors);
			CError.buildErr(this, "在取得打印队列中数据时发生错误");
			return false;
		}
		LDPersonSchema mLDPersonSchema = tLDPersonDB.getSchema();
		PEDate = PEDate + "-";
		String CheckDate = StrTool.decodeStr(PEDate, "-", 1) + "年"
				+ StrTool.decodeStr(PEDate, "-", 2) + "月"
				+ StrTool.decodeStr(PEDate, "-", 3) + "日";
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
		+ StrTool.getDay() + "日";
		
		tXmlExport.addListTable(tHosListTable, HosTitle); // 体检医院信息
		texttag.add("BarCode1", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam2",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("ContNo", mContNo);
		texttag.add("AppntName", mLCContSchema.getAppntName());
		String date = mLCContSchema.getCValiDate();
		date = date + "-";
		String ldate = StrTool.decodeStr(date, "-", 1) + "年"
		+ StrTool.decodeStr(date, "-", 2) + "月"
		+ StrTool.decodeStr(date, "-", 3) + "日";
		texttag.add("Date", ldate);
		texttag.add("WorkType", mOccupation);
		String sCheckDate = PubFun.calDate(PubFun.getCurrentDate(), 7, "D",PubFun.getCurrentDate()) + "-";
		sCheckDate = StrTool.decodeStr(sCheckDate, "-", 1) + "年"
		+ StrTool.decodeStr(sCheckDate, "-", 2) + "月"
		+ StrTool.decodeStr(sCheckDate, "-", 3) + "日";
		texttag.add("CheckDate", sCheckDate); // 体检日期(后第十日)
		texttag.add("AgentCode", mAgentCode);
		texttag.add("InsuredName", tRnewPENoticeSchema.getName());//体检人-被保险人
		String sql = "select a.sex,get_age(a.birthday,c.polapplydate),a.idno,(select mobile from lcaddress where customerno=a.insuredno and addressno = a.addressno) from lcinsured a,lccont c where a.contno=c.contno  "
		       + " and a.insuredno='"+"?insuredno?"+"' and a.contno='"+ "?contno?" +"'";			
		if(tRnewPENoticeSchema.getCustomerType().equals("A"))
		{
			sql = "select a.appntsex,get_age(a.appntbirthday,'"+"?date?"+"'),a.idno,(select mobile from lcaddress where customerno=a.appntno and addressno = a.addressno) from lcappnt a where 1=1 "
			       + "  and a.contno='"+ "?contno?" +"'";
		}
		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("insuredno", tRnewPENoticeSchema.getCustomerNo());
		sqlbv3.put("contno", tRnewPENoticeSchema.getContNo());
		sqlbv3.put("date", PubFun.getCurrentDate());
		
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
		if(tRnewPENoticeSchema.getPEBeforeCond()!=null )
		{
			if(tRnewPENoticeSchema.getPEBeforeCond().equals("Y"))
				PEBefore = "是";
			else
				PEBefore = "否";
		}			
		texttag.add("PEBefore", PEBefore);//是否空腹
		texttag.add("HospitName", PEHospitName); // 体检地点
		texttag.add("HospitAddress", PEAddress); // 体检地点	
		texttag.add("Name", tLAAgentSchema.getName()); // 代理人姓名
		texttag.add("AgentCode", mAgentCode); // 代理人业务号
		String tManageComCode = mLCContSchema.getManageCom().substring(0, 6);
		String tManageComName = getComName(tManageComCode);
		texttag.add("ManageCom", tManageComName); // 营业机构
		ExeSQL xxExeSQL = new ExeSQL();
		SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
		String tSql = "";
		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tSql = " select operator from LCUWMaster where contno='" + "?contno?" + "' and uwtype='1' and rownum=1 ";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tSql = " select operator from LCUWMaster where contno='" + "?contno?" + "' and uwtype='1' limit 0,1 ";
		}
		sqlbv4.sql(tSql);
		sqlbv4.put("contno", mLCContSchema.getContNo());
		String xxOperator=xxExeSQL.getOneValue(sqlbv4);
		texttag.add("UWOperator", xxOperator);
		//texttag.add("UWOperator", tRnewPENoticeSchema.getOperator()); // 核保师代码
		texttag.add("RePrintDate", SysDate);
		texttag.add("SysDate", SysDate);
		String PEDownDate = mLOPRTManagerSchema.getMakeDate() + "-";
		PEDownDate = StrTool.decodeStr(PEDownDate, "-", 1) + "年"
				+ StrTool.decodeStr(PEDownDate, "-", 2) + "月"
				+ StrTool.decodeStr(PEDownDate, "-", 3) + "日";
		texttag.add("PEDownDate", PEDownDate);//体检下发日期
		//zy 2009-05-18 回复日期,如打印通知书的makedate之后10天与生效日比较的小值
		String replyDate = PubFun.calDate(mLOPRTManagerSchema.getMakeDate(), 10, "D","" );
		logger.debug("replyDate---"+replyDate);
		String CvaSql = "select max(cvalidate) from lcpol where contno='"+"?contno?"+"' and appflag='9' ";
		SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
		sqlbv5.sql(CvaSql);
		sqlbv5.put("contno", mLCContSchema.getContNo());
		tExeSQL = new ExeSQL();
		String reCvaliDate=tExeSQL.getOneValue(sqlbv5);
		logger.debug("CValiDate()---"+reCvaliDate);
		if(PubFun.calInterval(reCvaliDate, replyDate, "D")>0)
			replyDate=reCvaliDate;
		String ReplyYear = replyDate.substring(0,4);
		String ReplyMon =  replyDate.substring(5,7);
		String ReplyDay =  replyDate.substring(8,10);
		texttag.add("ReplyYear",ReplyYear);
		texttag.add("ReplyMon",ReplyMon);
		texttag.add("ReplyDay",ReplyDay);
		
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

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 获取性别
	 * 
	 * @param StrSex
	 *            String
	 * @return String
	 */
	private String getSexName(String StrSex) {
		String mSql = "select CodeName from LDcode where Code = '" + "?StrSex?"
				+ "' and CodeType = 'sex'";
		SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
		sqlbv6.sql(mSql);
		sqlbv6.put("StrSex", StrSex);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv6);

	}
	
	/**
	 * 得到通过机构代码得到机构名称
	 * 
	 * @param strComCode
	 *            String
	 * @return String
	 */
	private String getComName(String strComCode) {
		String mSql = "select CodeName from LDcode where Code = '" + "?strComCode?"
				+ "' and CodeType = 'station'";
		SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
		sqlbv7.sql(mSql);
		sqlbv7.put("strComCode", strComCode);
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(sqlbv7);

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
