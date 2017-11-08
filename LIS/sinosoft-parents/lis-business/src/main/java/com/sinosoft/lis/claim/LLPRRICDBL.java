package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDComSchema;
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
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 统计报表打印：ICD10归类统计--LLPRRICD.vts
 * </p>
 * 统计界面：机构层面选项（总公司、分公司、中支公司、支公司）、机构统计范围选项（总公司、某分公司、某中支）、
 * 统计口径（事故日期、审批通过日期）、ICD10选项（ICD10主代码、ICD10子代码） 表头：报表名称、统计条件、统计日期
 * 数据项：机构、ICD10代码、ICD10名称、事故数量、机构全部有效被保人人数、整按给付案件数量、保项给付案件数量、
 * 整按拒付案件数量、保项拒付数量、整案拒付金额、事故出险率（事故数量/机构全部有效被保人人数）、
 * 整案拒付率（整案拒付案件数量/（整案给付案件数量+整案拒付案件数量））、 保项拒付率（保项拒付数量/（保项给付数量+保项拒付数量））、
 * ICD类别出险权重=对应类别事故数量/全部事故数量、给付金额 算法：按照选择的条件统计各项数据 排序：机构、ICD10代码 表尾：各项数据的合计
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangyang,2005-09-29
 * @version 1.0 modify by niuzj,ZHaoRx 2005-10-15
 */

public class LLPRRICDBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRICDBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate(); // 系统日期
	private String CurrentTime = PubFun.getCurrentTime(); // 系统时间

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private String mManageCom = ""; // 统计机构
	private String mLevel = ""; // 统计层面
	private String mStartDate = ""; // 统计起期
	private String mEndDate = ""; // 统计止期
	private String mStatKJ = ""; // ICD10选项
	private String mICDStAro = ""; // 统计口径
	private String mICDStAroName = "";
	private String mNCLType = ""; // 申请类型

	public LLPRRICDBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------统计报表打印：ICD10归类统计-----LLPRRICDBL测试-----开始----------");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("----------统计报表打印：ICD10归类统计-----LLPRRICDBL测试-----结束----------");
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		this.mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 统计机构
		this.mLevel = (String) mTransferData.getValueByName("Level"); // 统计层面
		this.mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
		this.mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		this.mStatKJ = (String) mTransferData.getValueByName("StatKJ"); // ICD10选项
		this.mICDStAro = (String) mTransferData.getValueByName("ICDStAro"); // 统计口径
		this.mICDStAroName = (String) mTransferData
				.getValueByName("ICDStAroName");
		this.mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMMap);
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		tXmlExport.createDocument("LLPRRICD.vts", ""); // 所用模板名称
		ListTable tListTable = new ListTable(); // 新建一个ListTable的实例,给模板的每一列赋值
		tListTable.setName("ICD");

		// 定义列表标题，共14列
		String[] Title = new String[14];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";
		Title[8] = "";
		Title[9] = "";
		Title[10] = "";
		Title[11] = "";
		Title[12] = "";
		Title[13] = "";

		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/ACCI/ROW/COL1内容，并为列表赋值--");

		// 用于统计各项数据的合计
		int tICDAdd3 = 0; // 事故数量
		int tICDAdd4 = 0; // 机构全部有效被保险人数
		int tICDAdd5 = 0; // 整案给付案件数量
		int tICDAdd6 = 0; // 保项给付案件数
		int tICDAdd7 = 0; // 整案拒付案件数量
		int tICDAdd8 = 0; // 保项拒付数量
		double tICDAdd9 = 0; // 整案拒付金额
		double tICDAdd10 = 0; // 事故出险率
		double tICDAdd11 = 0; // 整案拒付率
		double tICDAdd12 = 0; // 保项拒付率
		double tICDAdd15 = 0; // 给付金额

		String tAcc = "";
		String tAccCount = "";
		String tInsu = "";
		String tZAGF = "";
		String tBXGF = "";
		String tZAJF = "";
		String tZAJFJE = "";
		String tBXJF = "";
		double tAccRate = 0;
		double tZAJFRate = 0;
		double tBXJFRate = 0;
		String tICDRate = "";
		String applType1 = ""; // 申请类型
		String applType2 = mNCLType.trim().equals("1") ? " and a.rgtobj='1' "
				: " and a.rgtobj='2' ";
		if (mNCLType.trim().equals("1")) // 申请类型
		{
			applType1 = " and Exists (Select 'X' From Llregister Where Llregister.Rgtno = a.rgtno And Llregister.Rgtobj = '1')";
		} else {
			applType1 = " and Exists (Select 'X' From Llregister Where Llregister.Rgtno = a.rgtno And Llregister.Rgtobj = '2')";
		}

		/** ******************************查询统计机构层面,作为第一层循环************************************* */
		String strLevelSql = " select ComCode,Name from ldcom where ComCode like concat('"
				+ "?mngcom?"
				+ "','%') "
				+ " and trim(comgrade)='"
				+ "?level?"
				+ "' "
				+ " order by ComCode ";
		logger.debug("查询统计层面的SQL语句为:" + strLevelSql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strLevelSql);
		sqlbv.put("mngcom", mManageCom);
		sqlbv.put("level", mLevel);
		ExeSQL execLevelSQL = new ExeSQL();
		SSRS ssrsLevel = execLevelSQL.execSQL(sqlbv); // 执行查询语句strLevelSql

		if (ssrsLevel.getMaxRow() != 0) { // 查询有结果
			for (int i = 1; i <= ssrsLevel.getMaxRow(); i++) {
				String mngCode = ssrsLevel.GetText(i, 1);// 机构代码
				String tMngName = ssrsLevel.GetText(i, 2);// 机构名称

				/** *****************************查询ICD代码,作为第二层循环*************************************** */
				String tICDStAroTaICD = mICDStAro.equals("01") ? " ,llaccident d where c.caserelano = d.accno "
						: " ,llclaimuwmain x where x.clmno = c.caseno ";
				String tICDStAroTiICD = mICDStAro.equals("01") ? " and d.accdate "
						: " and x.examdate ";
				String strICD = " select distinct b.icdcode,b.icdname "
						+ " from llcase a,lddisease b,llcaserela c "
						+ tICDStAroTaICD + " and a.caseno = c.subrptno "
						+ tICDStAroTiICD + " between '" + "?startdate?"
						+ "' and '" + "?enddate?" + "' " + " and a.mngcom like concat('"
						+ "?mngcode?" + "','%') " + applType1;
				if (mStatKJ.equals("01")) // 统计口径为:ICD10主代码
				{
					strICD = strICD + " and b.icdlevel='0' "
							+ " and trim(a.accresult1)=trim(b.icdcode) "
							+ " order by b.icdcode ";
				} else // 统计口径为:ICD10子代码
				{
					strICD = strICD + " and b.icdlevel='1' "
							+ " and trim(a.accresult2)=trim(b.icdcode) "
							+ " order by b.icdcode ";
				}
				logger.debug("查询符合条件的ICD代码的SQL语句为:" + strICD);
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strICD);
				sqlbv1.put("startdate", mStartDate);
				sqlbv1.put("enddate", mEndDate);
				sqlbv1.put("mngcode", mngCode);
				ExeSQL strSQL = new ExeSQL();
				SSRS strICDS = new SSRS();
				strICDS = strSQL.execSQL(sqlbv1);

				if (strICDS.getMaxRow() != 0) // 该机构下对应的ICD代码不为空
				{
					for (int j = 1; j <= strICDS.getMaxRow(); j++) {
						String tICDCode = strICDS.GetText(j, 1);// ICD10代码
						String tICDName = strICDS.GetText(j, 2);// ICD10名称
						// 公用的SQL
						String tICDStAroTaPub = mICDStAro.equals("01") ? " , llaccident d  where c.caserelano = d.accno "
								: " ,llclaimuwmain r where r.clmno = a.rgtno ";// 统计口径_判断选表条件
						String tICDStAroTiPub = mICDStAro.equals("01") ? " and a.clmstate in('50','60') and d.accdate "
								: " and r.examconclusion='0' and r.examdate ";// 统计口径_时间条件
																				// 下同
						String strSql = " select count(distinct d.accno) from llaccident d, llcaserela c "
								+ " where c.caserelano = d.accno "
								+ " and c.caseno in "
								+ " (select a.rgtno "
								+ " from llregister a, llcase b, llcaserela c,lddisease e "
								+ tICDStAroTaPub
								+ " and a.rgtno = c.caseno "
								+ " and a.rgtno = b.rgtno "
								+ applType2 // 申请类型
								+ tICDStAroTiPub
								+ " between '"
								+ "?startdate?"
								+ "' and '"
								+ "?enddate?"
								+ "' "
								+ " and a.mngcom like concat('" + "?mngcode?" + "','%') ";
						// + " and a.clmstate = '60' ";
						String strSql1 = "";
						String strSql7 = "";
						if (mStatKJ.equals("01"))// 统计口径为:ICD10主代码
						{
							strSql1 = strSql// 事故数量(单个ICD10代码对应的事故数)
									+ " and trim(b.accresult1)=e.icdcode "
									+ " and b.accresult1='" + "?icdcode?"
									+ "' "
									+ " and e.icdlevel='0') ";
							strSql7 = strSql// 事故总数量
									+ " and trim(b.accresult1)=e.icdcode "
									+ " and e.icdlevel='0') ";
						} else// 统计口径为:ICD10子代码
						{
							strSql1 = strSql// 事故数量(单个ICD10代码对应的事故数)
									+ " and trim(b.accresult2)=e.icdcode "
									+ " and b.accresult2='" + "?icdcode?"
									+ "' "
									+ " and e.icdlevel='1') ";
							strSql7 = strSql// 事故总数量
									+ " and trim(b.accresult2)=e.icdcode "
									+ " and e.icdlevel='1') ";
						}
						logger.debug("查询事故数量的SQL语句为:" + strSql1);
						SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
						sqlbv2.sql(strSql1);
						sqlbv2.put("startdate", mStartDate);
						sqlbv2.put("enddate", mEndDate);
						sqlbv2.put("mngcode", mngCode);
						sqlbv2.put("icdcode", tICDCode);
						ExeSQL strExeSQL1 = new ExeSQL();
						SSRS strSSRS1 = new SSRS();
						strSSRS1 = strExeSQL1.execSQL(sqlbv2);
						tAcc = strSSRS1.GetText(1, 1);
						
						SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
						sqlbv3.sql(strSql7);
						sqlbv3.put("startdate", mStartDate);
						sqlbv3.put("enddate", mEndDate);
						sqlbv3.put("mngcode", mngCode);
						ExeSQL zrxExeSQL1 = new ExeSQL();
						SSRS zrxSSRS1 = new SSRS();
						zrxSSRS1 = zrxExeSQL1.execSQL(sqlbv3);
						tAccCount = zrxSSRS1.GetText(1, 1);
						// 机构全部有效被保险人数
						String strSql2 = " select count(*) from lcinsured a "
								+ " where 1=1 " + " and a.ManageCom like concat('"
								+ "?mngcode?" + "','%') ";
						logger.debug("查询机构全部有效被保险人数的SQL语句为:" + strSql2);
						SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
						sqlbv4.sql(strSql2);
						sqlbv4.put("mngcode", mngCode);
						ExeSQL strExeSQL2 = new ExeSQL();
						SSRS strSSRS2 = new SSRS();
						strSSRS2 = strExeSQL2.execSQL(sqlbv4);
						tInsu = strSSRS2.GetText(1, 1);
						if (tInsu.equals("null") || tInsu.equals("")
								|| tInsu == null) {
							tInsu = "0";
						}
						// 整案给付案件数量,llclaim
						String tICDStAroTaJFC = mICDStAro.equals("01") ? ",llaccident d where c.caserelano = d.accno "
								: ",llclaimuwmain z where z.clmno = a.rgtno ";
						String tICDStAroTiJFC = mICDStAro.equals("01") ? " and a.clmstate in ('50','60') and d.accdate "
								: " and z.examconclusion = '0' and z.examdate ";
						String strSql3 = " select count(f.clmno) "
								+ " from llregister a,llcase b,llcaserela c,lddisease e,llclaim f "
								+ tICDStAroTaJFC + " and a.rgtno=c.caseno "
								+ " and c.subrptno=b.caseno " + tICDStAroTiJFC
								+ " between '" + "?startdate?" + "' and '"
								+ "?enddate?" + "' " + " and a.mngcom like concat('"
								+ "mngcode" + "','%') "
								// + " and a.clmstate='60' " //已结案
								+ " and a.rgtno = f.clmno "
								+ " and f.givetype='0' " // 给付
								+ applType2 // 申请类型
								+ " and e.icdcode='" + "?icdcode?" + "' ";
						if (mStatKJ.equals("01")) { // 统计口径为:ICD10主代码
							strSql3 = strSql3 + " and b.accresult1='"
									+ "?icdcode?"  + "' "
									+ " and trim(b.accresult1)=e.icdcode "
									+ " and e.icdlevel='0' "
									+ " order by e.icdcode ";
						} else { // 统计口径为:ICD10子代码
							strSql3 = strSql3 + " and b.accresult2='"
									+ "?icdcode?" + "' "
									+ " and trim(b.accresult2)=e.icdcode "
									+ " and e.icdlevel='1' "
									+ " order by e.icdcode ";
						}
						logger.debug("查询整案给付案件数量的SQL语句为:" + strSql3);
						SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
						sqlbv5.sql(strSql3);
						sqlbv5.put("startdate", mStartDate);
						sqlbv5.put("enddate", mEndDate);
						sqlbv5.put("mngcode", mngCode);
						sqlbv5.put("icdcode", tICDCode);
						ExeSQL strExeSQL3 = new ExeSQL();
						SSRS strSSRS3 = new SSRS();
						strSSRS3 = strExeSQL3.execSQL(sqlbv5);
						tZAGF = strSSRS3.GetText(1, 1);
						if (tZAGF.equals("null") || tZAGF.equals("")
								|| tZAGF == null) {
							tZAGF = "0";
						}
						// 保项给付案件数量,llclaimdetail
						String tICDStAroTaBXC = mICDStAro.equals("01") ? ",llaccident d where c.caserelano = d.accno "
								: " ,llclaimuwmain w where w.clmno = a.rgtno ";
						String tICDStAroTiBXC = mICDStAro.equals("01") ? " and a.clmstate in ('50','60') and d.accdate "
								: " and w.examconclusion = '0' and w.examdate ";
						String strSql4 = " select count(f.clmno) "
								+ " from llregister a,llcase b,llcaserela c,lddisease e,llclaimdetail f "
								+ tICDStAroTaBXC + " and a.rgtno = c.caseno "
								+ " and c.subrptno= b.caseno " + tICDStAroTiBXC
								+ " between '" + "?startdate?" + "' and '"
								+ "?enddate?" + "' " + " and a.mngcom like concat('"
								+ "?mngcode?" + "','%') "
								// + " and a.clmstate='60' " //已结案
								+ " and a.rgtno= f.clmno "
								+ " and f.givetype='0' " // 给付
								+ applType2 // 申请类型
								+ " and e.icdcode='" + "?icdcode?" + "' ";
						if (mStatKJ.equals("01")) { // 统计口径为:ICD10主代码
							strSql4 = strSql4 + " and b.accresult1='"
									+ "?icdcode?" + "' "
									+ " and trim(b.accresult1)=e.icdcode "
									+ " and e.icdlevel='0' "
									+ " order by e.icdcode ";
						} else { // 统计口径为:ICD10子代码
							strSql4 = strSql4 + " and b.accresult2='"
									+ "?icdcode?" + "' "
									+ " and trim(b.accresult2)=e.icdcode "
									+ " and e.icdlevel='1' "
									+ " order by e.icdcode ";
						}
						logger.debug("查询保项给付案件数量的SQL语句为:" + strSql4);
						SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
						sqlbv6.sql(strSql4);
						sqlbv6.put("startdate", mStartDate);
						sqlbv6.put("enddate", mEndDate);
						sqlbv6.put("mngcode", mngCode);
						sqlbv6.put("icdcode", tICDCode);
						ExeSQL strExeSQL4 = new ExeSQL();
						SSRS strSSRS4 = new SSRS();
						strSSRS4 = strExeSQL4.execSQL(sqlbv6);
						tBXGF = strSSRS4.GetText(1, 1);
						if (tBXGF.equals("null") || tBXGF.equals("")
								|| tBXGF == null) {
							tBXGF = "0";
						}
						// 整案拒付案件数量,整案拒付金额,给付金额,llclaim
						String tICDStAroTaJFCM = mICDStAro.equals("01") ? ",llaccident d where c.caserelano = d.accno "
								: ",llclaimuwmain q where q.clmno = a.rgtno ";
						String tICDStAroTiJFCM = mICDStAro.equals("01") ? " and a.clmstate in ('50','60') and d.accdate "
								: " and q.examconclusion='0' and q.examdate ";
						String strSql5 = " select count(distinct f.clmno),CASE WHEN SUM(f.declinepay) IS NOT NULL THEN SUM(f.declinepay) ELSE 0 END "
								+ " from llregister a,llcase b,llcaserela c,lddisease e,llclaim f "
								+ tICDStAroTaJFCM
								+ " and a.rgtno = c.caseno "
								+ " and c.subrptno = b.caseno "
								+ " and a.rgtno = f.clmno "
								+ applType2 // 申请类型
								+ tICDStAroTiJFCM
								+ " between '"
								+ "?startdate?"
								+ "' and '"
								+ "?enddate?"
								+ "' "
								+ " and a.mngcom like concat('" + "?mngcode?" + "','%') "
								// + " and a.clmstate='60' " //已结案
								+ " and f.givetype='1' " // 拒付
								+ " and e.icdcode='" + "?icdcode?" + "' ";
						String strSqlJ = " select (case when sum(f.realpay) is not null then sum(f.realpay) else 0 end) "// 给付金额
								+ " from llregister a,llcase b,llcaserela c,lddisease e,llclaim f "
								+ tICDStAroTaJFCM
								+ " and a.rgtno = c.caseno "
								+ applType2 // 申请类型
								+ " and c.subrptno = b.caseno "
								+ " and a.rgtno = f.clmno "
								+ tICDStAroTiJFCM
								+ " between '" + "?startdate?"
								+ "' and '"
								+ "?enddate?" + "' "
								+ " and a.mngcom like concat('"
								+ "?mngcode?" + "','%') "
								// + " and a.clmstate='60' " //已结案
								+ " and f.givetype='0' " // 拒付
								+ " and e.icdcode='" + "?icdcode?" + "' ";
						if (mStatKJ.equals("01")) { // 统计口径为:ICD10主代码
							strSql5 = strSql5 + " and b.accresult1='"
									+ "?icdcode?" + "' " + " and e.icdlevel='0' "
									+ " order by e.icdcode ";
							strSqlJ = strSqlJ + " and b.accresult1='"
									+ "?icdcode?" + "' " + " and e.icdlevel='0' "
									+ " order by e.icdcode ";
						} else { // 统计口径为:ICD10子代码
							strSql5 = strSql5 + " and b.accresult2 ='"
									+ "?icdcode?" + "' " + " and e.icdlevel='1' "
									+ " order by e.icdcode ";
							strSqlJ = strSqlJ + " and b.accresult2 ='"
									+ "?icdcode?" + "' " + " and e.icdlevel='1' "
									+ " order by e.icdcode ";
						}
						logger.debug("查询整案拒付案件数量的SQL语句为:" + strSql5);
						ExeSQL strExeSQL5 = new ExeSQL();
						SSRS strSSRS5 = new SSRS();
						SSRS strSSRSJ = new SSRS();
						SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
						sqlbv7.sql(strSql5);
						sqlbv7.put("startdate", mStartDate);
						sqlbv7.put("enddate", mEndDate);
						sqlbv7.put("mngcode", mngCode);
						sqlbv7.put("icdcode", tICDCode);
						strSSRS5 = strExeSQL5.execSQL(sqlbv7);
						SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
						sqlbv8.sql(strSqlJ);
						sqlbv8.put("startdate", mStartDate);
						sqlbv8.put("enddate", mEndDate);
						sqlbv8.put("mngcode", mngCode);
						sqlbv8.put("icdcode", tICDCode);
						strSSRSJ = strExeSQL5.execSQL(sqlbv8);
						tZAJF = strSSRS5.GetText(1, 1);
						tZAJFJE = strSSRS5.GetText(1, 2);
						String tRealPay = strSSRSJ.GetText(1, 1);
						if (strSSRS5.getMaxRow() == 0) {
							tZAJF = "0";
							tZAJFJE = "0";
						}
						// 保项拒付数量,llclaimdetail
						String tICDStAroTaBXJFC = mICDStAro.equals("01") ? ",llaccident d where c.caserelano=d.accno "
								: " ,llclaimuwmain p where p.clmno = a.rgtno ";
						String tICDStAroTiBXJFC = mICDStAro.equals("01") ? " and a.clmstate in ('50','60') and d.accdate "
								: " and p.examconclusion = '0' and p.examdate ";
						String strSql6 = " select count(f.clmno) "
								+ " from llregister a,llcase b,llcaserela c,lddisease e,llclaimdetail f "
								+ tICDStAroTaBXJFC
								+ " and a.rgtno = c.caseno "
								+ " and c.subrptno = b.caseno "
								+ " and a.rgtno = f.clmno "
								+ applType2 // 申请类型
								+ tICDStAroTiBXJFC + " between '" + "?startdate?"
								+ "' and '" + "?enddate?" + "' "
								+ " and a.mngcom like concat('" + "?mngcode?" + "','%') "
								// + " and a.clmstate='60' " //已结案
								+ " and f.givetype='1' " // 拒付
								+ " and e.icdcode='" + "?icdcode?" + "' ";
						if (mStatKJ.equals("01")) { // 统计口径为:ICD10主代码
							strSql6 = strSql6 + " and b.accresult1='"
									+ "?icdcode?" + "' " + " and e.icdlevel='0' "
									+ " order by e.icdcode ";
						} else { // 统计口径为:ICD10子代码
							strSql6 = strSql6 + " and b.accresult2='"
									+ "?icdcode?" + "' " + " and e.icdlevel='1' "
									+ " order by e.icdcode ";
						}
						logger.debug("查询保项拒付数量的SQL语句为:" + strSql6);
						SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
						sqlbv9.sql(strSql6);
						sqlbv9.put("startdate", mStartDate);
						sqlbv9.put("enddate", mEndDate);
						sqlbv9.put("mngcode", mngCode);
						sqlbv9.put("icdcode", tICDCode);
						ExeSQL strExeSQL6 = new ExeSQL();
						SSRS strSSRS6 = new SSRS();
						strSSRS6 = strExeSQL6.execSQL(sqlbv9);
						tBXJF = strSSRS6.GetText(1, 1);
						if (tBXJF.equals("null") || tBXJF.equals("")
								|| tBXJF == null) {
							tBXJF = "0";
						}
						// 事故出险率（事故数量/机构全部有效被保险人数）
						String tSAccRate = "";
						if (Double.parseDouble(tInsu) != 0) {
							tAccRate = (Double.parseDouble(tAcc) / Double
									.parseDouble(tInsu)) * 10000;
							tSAccRate = new DecimalFormat("0.00000")
									.format(tAccRate)
									+ "/10000";
						} else {
							tSAccRate = "0.00000/10000";
						}
						// 整案拒付率〔整案拒付案件数量/（整案给付案件数量＋整案拒付案件数量）〕
						if ((Double.parseDouble(tZAGF) + Double
								.parseDouble(tZAJF)) != 0) {
							tZAJFRate = Double.parseDouble(tZAJF)
									/ (Double.parseDouble(tZAGF) + Double
											.parseDouble(tZAJF));
						} else {
							tZAJFRate = 0;
						}
						// 保项拒付率( 保项拒付数量/（保项给付数量＋保项拒付数量）)
						if ((Double.parseDouble(tBXGF) + Double
								.parseDouble(tBXJF)) != 0) {
							tBXJFRate = Double.parseDouble(tBXJF)
									/ (Double.parseDouble(tBXGF) + Double
											.parseDouble(tBXJF));
						} else {
							tBXJFRate = 0;
						}
						String tICDRateEvery = "";
						if (!tAccCount.equals("0")) {
							double tDICDRateEvery = Double.parseDouble(tAcc)
									/ Double.parseDouble(tAccCount);
							tICDRateEvery = new DecimalFormat("0.00")
									.format(tDICDRateEvery);
						} else {
							tICDRateEvery = "0.00";
						}
						// 给模板中的每一列赋值
						String[] Stra = new String[16];
						Stra[0] = tMngName; // 机构名称
						Stra[1] = tICDCode; // ICD10代码
						Stra[2] = tICDName; // ICD10名称
						Stra[3] = tAcc; // 事故数量
						Stra[4] = tInsu; // 机构全部有效被保险人数
						Stra[5] = tZAGF; // 整案给付案件数量
						Stra[6] = tBXGF; // 保项给付案件数
						Stra[7] = tZAJF; // 整案拒付案件数量
						Stra[8] = tBXJF; // 保项拒付数量
						Stra[9] = tZAJFJE; // 整案拒付金额
						Stra[10] = tSAccRate; // 事故出险率
						Stra[11] = String.valueOf(tZAJFRate); // 整案拒付率
						Stra[12] = String.valueOf(tBXJFRate); // 保项拒付率
						Stra[13] = tICDRateEvery; // ICD类别出险权重
						Stra[14] = tRealPay; // 给付金额
						Stra[15] = mngCode; // 机构代码
						tListTable.add(Stra);

						// 计算各项数据的合计
						tICDAdd3 = tICDAdd3 + Integer.parseInt(tAcc); // 事故数量
						tICDAdd5 = tICDAdd5 + Integer.parseInt(tZAGF); // 整案给付案件数量
						tICDAdd6 = tICDAdd6 + Integer.parseInt(tBXGF); // 保项给付案件数
						tICDAdd7 = tICDAdd7 + Integer.parseInt(tZAJF); // 整案拒付案件数量
						tICDAdd8 = tICDAdd8 + Integer.parseInt(tBXJF); // 保项拒付数量
						tICDAdd9 = tICDAdd9 + Double.parseDouble(tZAJFJE); // 整案拒付金额
						tICDAdd15 = tICDAdd15 + Double.parseDouble(tRealPay); // 给付金额
					}
					tICDAdd4 = tICDAdd4 + Integer.parseInt(tInsu); // 机构全部有效被保险人数
				} else // 该机构下对应的ICD代码为空
				{
					CError tError = new CError();
					tError.moduleName = "LLPRRICDBL";
					tError.functionName = "dealdata";
					tError.errorMessage = "该机构下没有要统计的ICD数据!";
					mErrors.addOneError(tError);
					return false;
				}
			}
		} else // 查询无结果
		{
			CError tError = new CError();
			tError.moduleName = "LLPRRICDBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询是为单个变量赋值-----------");
		// 统计日期,默认为系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		tTextTag.add("ICDDay", SysDate); // 统计日期: $=/ICDStaDate$

		// 统计时间段:$=/ICDStaTimes$
		String tStatTimes = mStartDate + "至" + mEndDate;
		tTextTag.add("ICDStaTimes", tStatTimes);

		// 统计机构名称:$=/ICDStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("ICDStatSer", tMngCom);
		// 申请类型
		String applTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 ";
		tTextTag.add("ApplType", applTypeName); // 申请类型
		// 统计层面:$=/ICDManage$
		String tLevelName = "";
		switch (Integer.parseInt(mLevel)) {
		case 1:
			tLevelName = "总公司";
			break;
		case 2:
			tLevelName = "分公司";
			break;
		case 3:
			tLevelName = "中支公司";
			break;
		case 4:
			tLevelName = "支公司";
			break;
		default:
			tLevelName = "";
			break;
		}
		tTextTag.add("ICDManage", tLevelName);

		// ICD10选项:$=/ICDStatAround$
		String tStatKJName = mStatKJ.equals("01") ? "ICD主代码" : "ICD子代码";
		tTextTag.add("ICDStatAround", tStatKJName);
		// 统计口径: $=/ICDStAround$
		tTextTag.add("ICDStAround", mICDStAroName);

		// 事故数量:$=/ICDAdd3$
		tTextTag.add("ICDAdd3", tICDAdd3);
		// 机构全部有效被保险人数:$=/ICDAdd4$
		tTextTag.add("ICDAdd4", tICDAdd4);
		// 整案给付案件数量:$=/ICDAdd5$
		tTextTag.add("ICDAdd5", tICDAdd5);
		// 保项给付案件数:$=/ICDAdd6$,格式化取两位小数
		tTextTag.add("ICDAdd6", tICDAdd6);
		// 整案拒付案件数量:$=/ICDAdd7$
		tTextTag.add("ICDAdd7", tICDAdd7);
		// 保项拒付数量:$=/ICDAdd8$
		tTextTag.add("ICDAdd8", tICDAdd8);
		// 整案拒付金额:$=/ICDAdd9$
		tTextTag.add("ICDAdd9", tICDAdd9);
		// 给付金额:$=/ICDAdd14$
		tTextTag.add("ICDAdd14", tICDAdd15);
		// 事故出险率:$=/ICDAdd10$,格式化取五位小数
		String tSICDAdd10 = "";
		if (tICDAdd4 != 0) {
			tICDAdd10 = (Double.parseDouble(String.valueOf(tICDAdd3)) / tICDAdd4) * 10000;
			tSICDAdd10 = new DecimalFormat("0.00000").format(tICDAdd10)
					+ "/10000";
		} else {
			tSICDAdd10 = "0.00";
		}
		tTextTag.add("ICDAdd10", tSICDAdd10);
		// 整案拒付率=整案拒付案件数量/（整案给付案件数量＋整案拒付案件数量）;$=/ICDAdd11$
		String tSICDAdd11 = "";
		if (tICDAdd5 + tICDAdd7 != 0) {
			tICDAdd11 = Double.parseDouble(String.valueOf(tICDAdd7))
					/ (tICDAdd5 + tICDAdd7);
			tSICDAdd11 = new DecimalFormat("0.00").format(tICDAdd11);
		} else {
			tSICDAdd11 = "0.00";
		}
		tTextTag.add("ICDAdd11", tSICDAdd11);
		// 保项拒付率:$=/ICDAdd12$
		tTextTag.add("ICDAdd12", tICDAdd12);
		// ICD类别出险权重＝对应类别事故数量/全部事故数量;$=/ICDAdd13$
		if (!tAccCount.equals("0")) {
			double tDICDRate = tICDAdd3 / Double.parseDouble(tAccCount);
			tICDRate = new DecimalFormat("0.00").format(tDICDRate);
		} else {
			tICDRate = "0.00";
		}
		tTextTag.add("ICDAdd13", tICDRate);

		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		// /*##################### 后台调试部分，生成临时文件############################*/
		// String strTemplatePath="E:/lis/ui/f1print/NCLtemplate/";
		// String strVFPathName=strTemplatePath+"Atest.vts"; //生成临时文件名
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(tXmlExport.getInputStream(),
		// strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);

		logger.debug("********************************************");
		logger.debug("---以下 将tXmlExport打包，返回前台--");

		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		return true;
	}

	// 错误处理
	public CErrors getErrors() {
		return mErrors;
	}

	// 得到结果集
	public VData getResult() {
		return mResult;
	}

	// 主函数,test
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";
		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("StartDate", "2004-01-01"); // 统计起期
		tTransferData.setNameAndValue("EndDate", "2004-01-02"); // 统计止期
		tTransferData.setNameAndValue("Level", "02"); // 统计层面
		tTransferData.setNameAndValue("ManageCom", "86"); // 统计机构
		tTransferData.setNameAndValue("StatKJ", "01"); // ICD10选项
		tTransferData.setNameAndValue("ICDStAro", "01"); // 统计口径 01 02

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRICDBL tLLPRRICDBL = new LLPRRICDBL();
		if (tLLPRRICDBL.submitData(tVData, "") == false) {
			logger.debug("-------统计报表打印：ICD10归类统计---失败----");
		}
		int n = tLLPRRICDBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRICDBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
