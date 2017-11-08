package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

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
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 统计报表打印：操作人员时效统计--LLPRROpEfficiency.vts
 * </p>
 * 统计界面：机构统计范围选项（总公司、某分公司、某中支）、统计时间段、案件类型选项（普通、投诉、诉讼、
 * 疑难案件、简易案件、全部案件类型）、理赔结论选项（拒付、给付、公司撤案、客户撤案、全部结论） 表头：报表名称、统计条件、统计日期
 * 数据项：用户、件均报案处理时效、件均立案处理时效、件均单证扫描处理时效（立案）、件均呈报处理时效、
 * 件均审核处理时效、件均审批处理时效、件均调查处理时效、件均结案处理时效 算法：按照选择的条件统计各项数据 排序：用户 表尾：无
 * 备注：操作人员时效均以精确到分钟
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author niuzj,2005-09-26
 * @version 1.0 Modify by ZHaoRx,2005-10-26 Modify by niuzj，2006-04-13，兼容团险
 */

public class LLPRROpEfficiencyBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRROpEfficiencyBL.class);
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
	private String mStartDate = ""; // 统计起期
	private String mEndDate = ""; // 统计止期
	private String mCaseType = ""; // 案件类型
	private String mPayCon = ""; // 理赔结论
	private String mNCLType = ""; // 申请类型

	public LLPRROpEfficiencyBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("----------统计报表打印：操作人员时效统计-----LLPRROpEfficiencyBL测试-----开始----------");

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
		logger.debug("----------统计报表打印：操作人员时效统计-----LLPRROpEfficiencyBL测试-----结束----------");
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
		this.mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
		this.mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		this.mCaseType = (String) mTransferData.getValueByName("CaseType"); // 案件类型
		this.mPayCon = (String) mTransferData.getValueByName("PayCon"); // 理赔结论
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
		tXmlExport.createDocument("LLPRROpEfficiency.vts", ""); // 所用模板名称
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		ListTable tListTable = new ListTable(); // 新建一个ListTable的实例,给模板的每一列赋值
		tListTable.setName("OE");

		// 定义列表标题，共9列
		String[] Title = new String[9];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";
		Title[8] = "";

		logger.debug("********************************************");
		logger.debug("---以下 查询列表$*/OE/ROW/COL内容，并为列表赋值--");

		// 先查询出管理机构下的用户
		String strUserSql = " select usercode,UserName from llclaimuser "
				+ " where ComCode like concat('" + "?mngcom?" + "','%') "
				+ " order by usercode";
		logger.debug("查询用户的SQL语句为:" + strUserSql);
		SQLwithBindVariables sqlbv =new SQLwithBindVariables();
		sqlbv.sql(strUserSql);
		sqlbv.put("mngcom", mManageCom);
		ExeSQL exeUserSQL = new ExeSQL();
		SSRS ssrsUser = exeUserSQL.execSQL(sqlbv); // 执行查询语句strUserSql

		// 再分别查询,给每个用户赋值
		if (ssrsUser.getMaxRow() != 0) {
			for (int i = 1; i <= ssrsUser.getMaxRow(); i++) {
				String tUserCode = ssrsUser.GetText(i, 1); // 用户代码
				String tUserName = ssrsUser.GetText(i, 2); // 用户名称

				/** **************************以下是几个公用的查询SQL语句************************************** */
				/**
				 * *用来计算日期时间之间的差,精确到分钟,保留两位小数 round(sum((
				 * a.outdate-nvl(a.indate,a.modifydate))*24*60 +
				 * to_number(to_date(a.outtime,'HH24:MI:SS')-to_date(nvl(a.intime,a.modifytime),'HH24:MI:SS'))*60*24)/count(*),2)
				 */
				String tTIMEs = " between '" + "?startdate?" + "' and '"
						+ "?enddate?" + "' ";

				// 业务类型判断llregister.rgtobj：1-个险 2-团险
				String tNCLType = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where a.missionprop1 = trim(llregister.rgtno) and llregister.rgtobj = '1' ) "
						: " and exists (select 'X' from llregister where a.missionprop1 = trim(llregister.rgtno) and llregister.rgtobj = '2' ) ";
				String tAplType1 = mNCLType.trim().equals("1") ? " and b.rgtobj='1' "
						: " and b.rgtobj='2' ";
				String tAplType2 = mNCLType.trim().equals("1") ? " and a.rgtobj='1' "
						: " and a.rgtobj='2' ";

				// 案件类型和理赔结论都为空
				//start oracle支持      zhangyingfeng  2016-04-15
				String sqlselect1=" select count(*),nvl(round(sum(( a.outdate-nvl(a.indate,a.modifydate))*24*60 "
						+ " + to_number(to_date(a.outtime,'HH24:MI:SS')-to_date(nvl(a.intime,a.modifytime),'HH24:MI:SS'))*60*24)/count(*),2),0) ";
				String sqlselect2=" select count(*),nvl(round(sum(( b.endcasedate-b.makedate)*24*60 "// 求结案件均时效(单独求是因为可能有已结案件,工作流中没有置相应状态)
						+ " + to_number(to_date(b.modifytime,'HH24:MI:SS')-to_date(b.maketime,'HH24:MI:SS'))*60*24)/count(*),2),0) ";
				String sqlselect3=" select count(*),nvl(round(sum(( a.endcasedate-a.makedate)*24*60 "// 求结案件均时效Modify
																									// by
																									// zhaorx
																									// 2006-10-11
						+ " + to_number(to_date(a.modifytime,'HH24:MI:SS')-to_date(a.maketime,'HH24:MI:SS'))*60*24)/count(*),2),0) ";
				if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
					sqlselect1=" select count(*),(CASE WHEN ROUND(SUM(DATEDIFF(a.outdate,(CASE WHEN a.indate IS NOT NULL "
                        +"THEN a.indate ELSE a.modifydate END)) * 24 * 60 + to_number ("
                        +"TIME_TO_SEC(to_date(a.outtime,'HH24:MI:SS')) - TIME_TO_SEC(to_date(CASE WHEN a.intime IS NOT NULL " 
                        +"THEN a.intime ELSE a.modifytime END,'HH24:MI:SS')))/60) / COUNT(*),2) IS NOT NULL "
                        +"THEN ROUND(SUM(DATEDIFF(a.outdate,(CASE WHEN a.indate IS NOT NULL "
                        +"THEN a.indate ELSE a.modifydate END)) * 24 * 60 + to_number ("
                        +"TIME_TO_SEC(to_date(a.outtime,'HH24:MI:SS')) - TIME_TO_SEC(to_date(CASE WHEN a.intime IS NOT NULL " 
                        +"THEN a.intime ELSE a.modifytime END,'HH24:MI:SS')))/60) / COUNT(*),2) ELSE 0 END) ";
                    sqlselect2="SELECT COUNT(*),(CASE WHEN ROUND(SUM(DATEDIFF(b.endcasedate, b.makedate) * 24 * 60 + to_number ("
                    	+"TIME_TO_SEC(to_date(b.modifytime,'HH24:MI:SS')) - TIME_TO_SEC(to_date(b.maketime,'HH24:MI:SS')))/60) / COUNT(*),2) IS NOT NULL " 
                    	+"THEN ROUND(SUM(DATEDIFF(b.endcasedate, b.makedate) * 24 * 60 + to_number ("
                    	+"TIME_TO_SEC(to_date(b.modifytime,'HH24:MI:SS')) - TIME_TO_SEC(to_date(b.maketime,'HH24:MI:SS')))/60) / COUNT(*),2) ELSE 0 END) ";
					sqlselect3="SELECT COUNT(*),(CASE WHEN ROUND(SUM(DATEDIFF(a.endcasedate, a.makedate) * 24 * 60 + to_number ("
	                    	+"TIME_TO_SEC(to_date(a.modifytime,'HH24:MI:SS')) - TIME_TO_SEC(to_date(a.maketime,'HH24:MI:SS')))/60) / COUNT(*),2) IS NOT NULL " 
	                    	+"THEN ROUND(SUM(DATEDIFF(a.endcasedate, a.makedate) * 24 * 60 + to_number ("
	                    	+"TIME_TO_SEC(to_date(a.modifytime,'HH24:MI:SS')) - TIME_TO_SEC(to_date(a.maketime,'HH24:MI:SS')))/60) / COUNT(*),2) ELSE 0 END) ";
				}
				// end
				// 替换
				String strSqlA = sqlselect1
						+ " from lbmission a "
						+ " where 1=1 "
						+ " and a.processid='0000000005' " // 理赔
						+ tNCLType
						+ " and ((case when trim(a.DEFAULTOPERATOR) is not null then trim(a.DEFAULTOPERATOR) else a.missionprop19 end)='"
						+ "?usercode?"
						+ "' or (case when trim(a.DEFAULTOPERATOR) is not null then trim(a.DEFAULTOPERATOR) else a.missionprop19 end)='"
						+ "?username?"
						+ "') "
						+ " and ((case when a.indate is not null then a.indate else a.modifydate end) "
						+ tTIMEs
						+ " ) "
						+ " and (a.outdate " + tTIMEs + " ) ";
				String tSQLAA = sqlselect2
						+ " from llregister b"
						+ " where b.clmstate = '60' "// 结案
						+ tAplType1
						+ " and b.Operator='"
						+ "?usercode?"
						+ "' "
						+ " and b.endcasedate "
						+ tTIMEs
						+ " and b.makedate "
						+ tTIMEs;
				// 案件类型不为空,理赔结论为空
				String strSqlB = sqlselect1
						+ " from lbmission a,llregister b "
						+ " where 1=1 "
						+ " and a.missionprop1 = trim(b.rgtno) "
						+ " and a.processid='0000000005' " // 理赔
						+ tAplType1
						+ " and ((case when trim(a.DEFAULTOPERATOR) is not null then trim(a.DEFAULTOPERATOR) else a.missionprop19 end)='"
						+ "?usercode?"
						+ "' or (case when trim(a.DEFAULTOPERATOR) is not null then trim(a.DEFAULTOPERATOR) else a.missionprop19 end)='"
						+ "?username?"
						+ "') "
						+ " and ((case when a.indate is not null then a.indate else a.modifydate end) "
						+ tTIMEs
						+ " ) "
						+ " and (a.outdate " + tTIMEs + " ) ";
				String tSQLBB = tSQLAA;// 求结案件均时效
				// 案件类型为空,理赔结论不为空
				String strSqlC = sqlselect1
						+ " from lbmission a,llclaim c "
						+ " where 1=1 "
						+ " and a.missionprop1 = trim(c.clmno) "
						+ " and a.processid='0000000005' " // 理赔
						+ tNCLType
						+ " and ((case when trim(a.DEFAULTOPERATOR) is not null then trim(a.DEFAULTOPERATOR) else a.missionprop19 end)='"
						+ "?usercode?"
						+ "' or (case when trim(a.DEFAULTOPERATOR) is not null then trim(a.DEFAULTOPERATOR) else a.missionprop19 end)='"
						+ "?username?"
						+ "') "
						+ " and ((case when a.indate is not null then a.indate else a.modifydate end) "
						+ tTIMEs
						+ " ) "
						+ " and (a.outdate " + tTIMEs + " ) ";
				String tSQLCC = sqlselect3
						+ " from llregister a,llclaim c "
						+ " where c.clmno = a.rgtno "
						+ " and a.clmstate = '60' "
						+ tAplType2
						+ " and a.Operator='"
						+ "?usercode?"
						+ "' "
						+ " and a.endcasedate "
						+ tTIMEs
						+ " and a.makedate "
						+ tTIMEs;
				// 案件类型和理赔结论都不为空
				String strSqlD =sqlselect1
						+ " from lbmission a,llregister b,llclaim c "
						+ " where 1=1 "
						+ " and a.missionprop1 = trim(b.rgtno) "
						+ " and a.missionprop1 = trim(c.clmno) "
						+ " and a.processid='0000000005' " // 理赔
						+ tAplType1
						+ " and ((case when trim(a.DEFAULTOPERATOR) is not null then trim(a.DEFAULTOPERATOR) else a.missionprop19 end)='"
						+ "?usercode?"
						+ "' or (case when trim(a.DEFAULTOPERATOR) is not null then trim(a.DEFAULTOPERATOR) else a.missionprop19 end)='"
						+ "?username?"
						+ "') "
						+ " and ((case when a.indate is not null then a.indate else a.modifydate end) "
						+ tTIMEs
						+ " ) "
						+ " and (a.outdate " + tTIMEs + " ) ";
				String tSQLDD = sqlselect2
						+ " from llregister b,llclaim c "
						+ " where c.clmno = b.rgtno "
						+ " and b.clmstate = '60' "
						+ tAplType1
						+ " and b.Operator='"
						+ "?usercode?"
						+ "' "
						+ " and b.endcasedate "
						+ tTIMEs
						+ " and b.makedate "
						+ tTIMEs;
				String strSql1 = "";
				String strSql2 = "";
				String strSql3 = "";
				String strSql4 = "";
				String strSql5 = "";
				String strSql6 = "";
				String strSql7 = "";
				/** ************************拼出最后的SQL语句**************************************************** */

				// 用案件类型去拼SQL语句
				if (mCaseType.equals("")) // 全部案件类型
				{
					// 用理赔结论再拼SQL语句
					if (mPayCon.equals(""))// 全部理赔结论
					{
						strSqlB = "";
						strSqlC = "";
						strSqlD = "";
						tSQLBB = "";
						tSQLCC = "";
						tSQLDD = "";
					} else {
						switch (Integer.parseInt(mPayCon)) {
						case 0:// 给付
							strSqlC = strSqlC + " and c.givetype='0' ";
							strSqlA = "";
							strSqlB = "";
							strSqlD = "";
							tSQLCC = tSQLCC + " and c.givetype='0' ";
							tSQLAA = "";
							tSQLBB = "";
							tSQLDD = "";
							break;
						case 1:// 拒付
							strSqlC = strSqlC + " and c.givetype='1' ";
							strSqlA = "";
							strSqlB = "";
							strSqlD = "";
							tSQLCC = tSQLCC + " and c.givetype='1' ";
							tSQLAA = "";
							tSQLBB = "";
							tSQLDD = "";
							break;
						case 2:// 客户撤案
							strSqlC = strSqlC + " and c.givetype='2' ";
							strSqlA = "";
							strSqlB = "";
							strSqlD = "";
							tSQLCC = tSQLCC + " and c.givetype='2' ";
							tSQLAA = "";
							tSQLBB = "";
							tSQLDD = "";
							break;
						case 3:// 公司撤案
							strSqlC = strSqlC + " and c.givetype='3' ";
							strSqlA = "";
							strSqlB = "";
							strSqlD = "";
							tSQLCC = tSQLCC + " and c.givetype='3' ";
							tSQLAA = "";
							tSQLBB = "";
							tSQLDD = "";
							break;
						default:
							break;
						}
					}
				} else {
					switch (Integer.parseInt(mCaseType))// 单个案件类型
					{
					case 1:
						// 用理赔结论再拼SQL语句
						if (mPayCon.equals(""))// 全部理赔结论
						{
							strSqlB = strSqlB + " and b.rgtstate='01' ";
							strSqlA = "";
							strSqlD = "";
							strSqlC = "";
							tSQLBB = tSQLBB + " and b.rgtstate='01' ";
							tSQLAA = "";
							tSQLDD = "";
							tSQLCC = "";
						} else// 单个理赔结论
						{
							switch (Integer.parseInt(mPayCon)) {
							case 0:// 给付
								strSqlD = strSqlD
										+ " and b.rgtstate='01' and c.givetype='0' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='01' and c.givetype='0' ";// 求结案件均时效
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 1:// 拒付
								strSqlD = strSqlD
										+ " and b.rgtstate='01' and c.givetype='1' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='01' and c.givetype='1' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 2:// 客户撤案
								strSqlD = strSqlD
										+ " and b.rgtstate='01' and c.givetype='2' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='01' and c.givetype='2' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 3:// 公司撤案
								strSqlD = strSqlD
										+ " and b.rgtstate='01' and c.givetype='3' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='01' and c.givetype='3' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							default:
								break;
							}
						}
						break;
					case 11:// 普通案件
						// 用理赔结论再拼SQL语句
						if (mPayCon.equals(""))// 全部理赔结论
						{
							strSqlB = strSqlB + " and b.rgtstate='11' ";
							strSqlA = "";
							strSqlD = "";
							strSqlC = "";
							tSQLBB = tSQLBB + " and b.rgtstate='11' ";
							tSQLAA = "";
							tSQLDD = "";
							tSQLCC = "";
						} else// 单个理赔结论
						{
							switch (Integer.parseInt(mPayCon)) {
							case 0:// 给付
								strSqlD = strSqlD
										+ " and b.rgtstate='11' and c.givetype='0' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='11' and c.givetype='0' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 1:// 拒付
								strSqlD = strSqlD
										+ " and b.rgtstate='11' and c.givetype='1' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='11' and c.givetype='1' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 2:// 客户撤案
								strSqlD = strSqlD
										+ " and b.rgtstate='11' and c.givetype='2' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='11' and c.givetype='2' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 3:// 公司撤案
								strSqlD = strSqlD
										+ " and b.rgtstate='11' and c.givetype='3' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='11' and c.givetype='3' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							default:
								break;
							}
						}
						break;
					case 12:// 申诉案件
						// 用理赔结论再拼SQL语句
						if (mPayCon.equals(""))// 全部理赔结论
						{
							strSqlB = strSqlB + " and b.rgtstate='12' ";
							strSqlA = "";
							strSqlD = "";
							strSqlC = "";
							tSQLBB = tSQLBB + " and b.rgtstate='12' ";
							tSQLAA = "";
							tSQLDD = "";
							tSQLCC = "";
						} else// 单个理赔结论
						{
							switch (Integer.parseInt(mPayCon)) {
							case 0:// 给付
								strSqlD = strSqlD
										+ " and b.rgtstate='12' and c.givetype='0' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='12' and c.givetype='0' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 1:// 拒付
								strSqlD = strSqlD
										+ " and b.rgtstate='12' and c.givetype='1' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='12' and c.givetype='1' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 2:// 客户撤案
								strSqlD = strSqlD
										+ " and b.rgtstate='12' and c.givetype='2' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='12' and c.givetype='2' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 3:// 公司撤案
								strSqlD = strSqlD
										+ " and b.rgtstate='12' and c.givetype='3' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='12' and c.givetype='3' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							default:
								break;
							}
						}
						break;
					case 13:// 诉讼案件
						// 用理赔结论再拼SQL语句
						if (mPayCon.equals(""))// 全部理赔结论
						{
							strSqlB = strSqlB + " and b.rgtstate='13' ";
							strSqlA = "";
							strSqlD = "";
							strSqlC = "";
							tSQLBB = tSQLBB + " and b.rgtstate='13' ";
							tSQLAA = "";
							tSQLDD = "";
							tSQLCC = "";
						} else// 单个理赔结论
						{
							switch (Integer.parseInt(mPayCon)) {
							case 0:// 给付
								strSqlD = strSqlD
										+ " and b.rgtstate='13' and c.givetype='0' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='13' and c.givetype='0' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 1:// 拒付
								strSqlD = strSqlD
										+ " and b.rgtstate='13' and c.givetype='1' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='13' and c.givetype='1' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 2:// 客户撤案
								strSqlD = strSqlD
										+ " and b.rgtstate='13' and c.givetype='2' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='13' and c.givetype='2' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 3:// 公司撤案
								strSqlD = strSqlD
										+ " and b.rgtstate='13' and c.givetype='3' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='13' and c.givetype='3' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							default:
								break;
							}
						}
						break;
					case 14:// 疑难案件
						// 用理赔结论再拼SQL语句
						if (mPayCon.equals(""))// 全部理赔结论
						{
							strSqlB = strSqlB + " and b.rgtstate='14' ";
							strSqlA = "";
							strSqlD = "";
							strSqlC = "";
							tSQLBB = tSQLBB + " and b.rgtstate='14' ";
							tSQLAA = "";
							tSQLDD = "";
							tSQLCC = "";
						} else// 单个理赔结论
						{
							switch (Integer.parseInt(mPayCon)) {
							case 0:// 给付
								strSqlD = strSqlD
										+ " and b.rgtstate='14' and c.givetype='0' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='14' and c.givetype='0' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 1:// 拒付
								strSqlD = strSqlD
										+ " and b.rgtstate='14' and c.givetype='1' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='14' and c.givetype='1' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 2:// 客户撤案
								strSqlD = strSqlD
										+ " and b.rgtstate='14' and c.givetype='2' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='14' and c.givetype='2' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							case 3:// 公司撤案
								strSqlD = strSqlD
										+ " and b.rgtstate='14' and c.givetype='3' ";
								strSqlA = "";
								strSqlB = "";
								strSqlC = "";
								tSQLDD = tSQLDD
										+ " and b.rgtstate='14' and c.givetype='3' ";
								tSQLAA = "";
								tSQLBB = "";
								tSQLCC = "";
								break;
							default:
								break;
							}
						}
						break;
					default:
						break;
					}
				}

				if (mNCLType.trim().equals("1"))// 个险
				{
					strSql1 = strSqlA + strSqlB + strSqlC + strSqlD
							+ " and a.activityid='0000005005' "; // 报案
					strSql2 = strSqlA + strSqlB + strSqlC + strSqlD
							+ " and a.activityid='0000005015' "; // 立案
				} else if (mNCLType.trim().equals("2"))// 团险
				{
					strSql1 = strSqlA + strSqlB + strSqlC + strSqlD
							+ " and a.activityid='0000005205' "; // 报案
					strSql2 = strSqlA + strSqlB + strSqlC + strSqlD
							+ " and a.activityid='0000005215' "; // 立案
				}
				strSql3 = strSqlA + strSqlB + strSqlC + strSqlD
						+ " and a.activityid='0000005105' "; // 呈报
				strSql4 = strSqlA + strSqlB + strSqlC + strSqlD
						+ " and a.activityid='0000005035' "; // 审核
				strSql5 = strSqlA + strSqlB + strSqlC + strSqlD
						+ " and a.activityid='0000005055' "; // 审批
				strSql6 = strSqlA
						+ strSqlB
						+ strSqlC
						+ strSqlD
						+ " and (a.activityid='0000005145' or a.activityid='0000005165') "; // 调查
				strSql6 = getReplaceAStr("(*)", "(distinct missionprop1)",
						strSql6);
				// strSql7 = tSQLAA + tSQLBB + tSQLCC + tSQLDD ; //结案
				strSql7 = strSqlA + strSqlB + strSqlC + strSqlD
						+ " and a.activityid='0000005075' "; // 结案
				/** ********************分别执行SQL语句,给模板赋值************************************************* */
				ExeSQL exSql1_7 = new ExeSQL();
				SSRS ssSql1 = new SSRS();
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(strSql1);
				sqlbv1.put("usercode", tUserCode);
				sqlbv1.put("username", tUserName);
				sqlbv1.put("startdate", mStartDate);
				sqlbv1.put("enddate", mEndDate);
				ssSql1 = exSql1_7.execSQL(sqlbv1);
				String t1 = ssSql1.GetText(1, 2); // 件均报案处理时效
				
				SSRS ssSql2 = new SSRS();
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(strSql2);
				sqlbv2.put("usercode", tUserCode);
				sqlbv2.put("username", tUserName);
				sqlbv2.put("startdate", mStartDate);
				sqlbv2.put("enddate", mEndDate);
				ssSql2 = exSql1_7.execSQL(sqlbv2);
				String t2 = ssSql2.GetText(1, 2); // 件均立案处理时效

				SSRS ssSql3 = new SSRS();
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(strSql3);
				sqlbv3.put("usercode", tUserCode);
				sqlbv3.put("username", tUserName);
				sqlbv3.put("startdate", mStartDate);
				sqlbv3.put("enddate", mEndDate);
				ssSql3 = exSql1_7.execSQL(sqlbv3);
				String t3 = ssSql3.GetText(1, 2); // 件均呈报处理时效

				SSRS ssSql4 = new SSRS();
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(strSql4);
				sqlbv4.put("usercode", tUserCode);
				sqlbv4.put("username", tUserName);
				sqlbv4.put("startdate", mStartDate);
				sqlbv4.put("enddate", mEndDate);
				ssSql4 = exSql1_7.execSQL(sqlbv4);
				String t4 = ssSql4.GetText(1, 2); // 件均审核处理时效

				SSRS ssSql5 = new SSRS();
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(strSql5);
				sqlbv5.put("usercode", tUserCode);
				sqlbv5.put("username", tUserName);
				sqlbv5.put("startdate", mStartDate);
				sqlbv5.put("enddate", mEndDate);
				ssSql5 = exSql1_7.execSQL(sqlbv5);
				String t5 = ssSql5.GetText(1, 2); // 件均审批处理时效

				SSRS ssSql6 = new SSRS();
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql(strSql6);
				sqlbv6.put("usercode", tUserCode);
				sqlbv6.put("username", tUserName);
				sqlbv6.put("startdate", mStartDate);
				sqlbv6.put("enddate", mEndDate);
				ssSql6 = exSql1_7.execSQL(sqlbv6);
				String t6 = ssSql6.GetText(1, 2); // 件均调查处理时效

				SSRS ssSql7 = new SSRS();
				SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
				sqlbv7.sql(strSql7);
				sqlbv7.put("usercode", tUserCode);
				sqlbv7.put("username", tUserName);
				sqlbv7.put("startdate", mStartDate);
				sqlbv7.put("enddate", mEndDate);
				ssSql7 = exSql1_7.execSQL(sqlbv7);
				String t7 = ssSql7.GetText(1, 2); // 件均结案处理时效

				/** ********************************给模板中的列赋值**************************************************** */

				String[] Stra = new String[10]; // 给模板中的每一列赋值
				Stra[0] = tUserName; // 用户名称
				Stra[1] = t1; // 件均报案处理时效
				Stra[2] = t2; // 件均立案处理时效
				Stra[3] = "0.00"; // 件均单证扫描处理时效（立案）
				Stra[4] = t3; // 件均呈报处理时效
				Stra[5] = t4; // 件均审核处理时效
				Stra[6] = t5; // 件均审批处理时效
				Stra[7] = t6; // 件均调查处理时效
				Stra[8] = t7; // 件均结案处理时效
				Stra[9] = tUserCode; // 用户代码
				tListTable.add(Stra);
			}
		} else // 没有查询到用户
		{
			CError tError = new CError();
			tError.moduleName = "LLPRROpEfficiencyBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		/** ********************************给模板中的单个变量赋值************************************************ */
		logger.debug("----------以下查询是为单个变量赋值-----------");
		// 统计日期,默认为系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		tTextTag.add("OEStaDate", SysDate); // 统计日期: $=/OEStaDate$

		// 统计时间段:$=/OEStaTimes$
		String tStatTimes = mStartDate + "至" + mEndDate;
		tTextTag.add("OEStaTimes", tStatTimes);

		// 统计机构:$=/OEStatSer$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("OEStatSer", tMngCom);

		// 案件类型:$=/OECaseType$
		String tCaseTypeName = "";
		if (mCaseType.equals("")) {
			tCaseTypeName = "全部案件类型";
		} else {
			switch (Integer.parseInt(mCaseType)) {
			case 1:
				tCaseTypeName = "简易案件";
				break;
			case 11:
				tCaseTypeName = "普通案件";
				break;
			case 12:
				tCaseTypeName = "申诉案件";
				break;
			case 13:
				tCaseTypeName = "诉讼案件";
				break;
			case 14:
				tCaseTypeName = "疑难案件";
				break;
			default:
				tCaseTypeName = "";
				break;
			}
		}
		tTextTag.add("OECaseType", tCaseTypeName);

		// 理赔结论:$=/OEConclu$
		String tPayConName = "";
		if (mPayCon.equals("")) {
			tPayConName = "全部理赔结论";
		} else {
			switch (Integer.parseInt(mPayCon)) {
			case 0:
				tPayConName = "给付";
				break;
			case 1:
				tPayConName = "拒付";
				break;
			case 2:
				tPayConName = "客户撤案";
				break;
			case 3:
				tPayConName = "公司撤案";
				break;
			default:
				break;
			}
		}
		tTextTag.add("OEConclu", tPayConName);

		// 申请类型判断
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 ";
		tTextTag.add("OEApplType", tApplTypeName); // 申请类型$=/OEApplType$

		/** ****************************************************************************************** */

		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		/* ##################### 后台调试部分，生成临时文件############################ */
		// String strTemplatePath="E:/lis/ui/f1print/NCLtemplate/";
		// String strVFPathName=strTemplatePath+"test.vts"; //生成临时文件名
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

	/**
	 * 用于将一个字符串中的部分字符串替换成想要的字符串，返回最后的结果。 version : 0.1 zhaorx,2007-04-24
	 * 
	 * @param a
	 *            String要替换掉的
	 * @param b
	 *            String替换成的
	 * @param c
	 *            String最终结果字符串
	 * @return String
	 */
	public String getReplaceAStr(String a, String b, String c) {
		int alength = a.length();
		int blength = b.length();
		int clength = c.length();
		if (alength > clength) {
			mErrors
					.addOneError("要替换掉的字符串" + a
							+ "的长度超过最终结果字符串(C)的长度，不能进行替换!!!");
			return c;
		}
		int i = 0;
		for (i = 0; i <= clength - alength; i++) {
			if (a.equals(c.substring(i, i + alength))) {
				c = c.substring(0, i) + b + c.substring(i + alength, clength);
				clength = c.length();
				i = i + blength - 1;
			}
		}
		return c;
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

		tTransferData.setNameAndValue("StartDate", "2005-1-1"); // 统计起期
		tTransferData.setNameAndValue("EndDate", "2005-9-23"); // 统计止期
		tTransferData.setNameAndValue("ManageCom", "8632"); // 统计机构
		tTransferData.setNameAndValue("CaseType", ""); // 案件类型
		tTransferData.setNameAndValue("PayCon", ""); // 理赔结论

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRROpEfficiencyBL tLLPRROpEfficiencyBL = new LLPRROpEfficiencyBL();
		if (tLLPRROpEfficiencyBL.submitData(tVData, "") == false) {
			logger.debug("-------统计报表打印：操作人员时效统计---失败----");
		}
		int n = tLLPRROpEfficiencyBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRROpEfficiencyBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
