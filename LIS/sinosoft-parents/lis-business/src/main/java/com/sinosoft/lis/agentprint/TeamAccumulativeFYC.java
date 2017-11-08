package com.sinosoft.lis.agentprint;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.util.*;

/**
 * <p>Title: lis</p>
 * <p>Description:团队累计标保FYC统计表</p>
 * <p>Copyright: Copyright (c) 2008-07-17</p>
 * <p>Company: sinosoft</p>
 * @author jieyh
 * @version 1.0
 */
public class TeamAccumulativeFYC {
private static Logger logger = Logger.getLogger(TeamAccumulativeFYC.class);
	public GlobalInput mGI = new GlobalInput();
	private String mTJStartDate = "";
	private String mTJEndDate = "";
	private String mManageCom = "";
	private String mBranchLevel = "";
	private String mAgentGroup = "";
	private String tAgentGrade = "";
	//交单止期对应的佣金月是否算过佣金，默认为0
	//没算过佣金，取当前数据，算过佣金，取备份表数据
	//  private String mIsCalWage = "0";

	//表头数组
	private String strArr[] = null;

	private int tResultCount = 0;

	private String mFilePathDesc = "TKReportCreat"; //生成文件的路径,待修改
	private String mFilePath = ""; //生成文件路径
	private String mModePath = "";//模版路径
	private String mFileName = "";
	private String mTemplatePath="";
	public VData mResult = new VData();
	public CErrors mErrors = new CErrors();

	public TeamAccumulativeFYC() {
	}

	public static void main(String[] args) {
	String tTJStartDate = "200805";
	String tTJEndDate = "200806";
	String tManageCom = "8613";
	String tAgentGroup = "";
	String tBranchLevel = "01";
	GlobalInput tG = new GlobalInput();
	tG.ManageCom = "8633";
	tG.Operator = "DEV";
	VData tVData = new VData();
	tVData.clear();
	tVData.addElement(tTJStartDate);
	tVData.addElement(tTJEndDate);
	tVData.addElement(tManageCom);
                tVData.addElement(tBranchLevel);
	tVData.addElement(tAgentGroup);
	tVData.addElement(tG);

	TeamAccumulativeFYC tTeamAccumulativeFYC = new TeamAccumulativeFYC();
	tTeamAccumulativeFYC.getInputData(tVData);
	tTeamAccumulativeFYC.prepareData();
	}

	public boolean getInputData(VData tInputData) {
		try  {
			this.mTJStartDate = (String)tInputData.getObjectByObjectName("String",0);
			this.mTJEndDate = (String)tInputData.getObjectByObjectName("String",1);
			this.mManageCom = (String)tInputData.getObjectByObjectName("String",2);
			this.mBranchLevel = (String)tInputData.getObjectByObjectName("String",3);
			this.mAgentGroup = (String)tInputData.getObjectByObjectName("String",4);
			this.mGI = (GlobalInput)tInputData.getObjectByObjectName("GlobalInput",5);
			mTemplatePath=(String)tInputData.getObjectByObjectName("String",6);;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			dealError("getInputData",ex.toString());
			return false;
		}

		//获取模版和生成文件的路径
		if(!getTempPath())
		{
			logger.debug("获取模版和生成文件的路径出错");
			return false;
		}
		return true;
	}

	public boolean prepareData()  {

		try {
			int tLength = 0;

                                                if (this.mAgentGroup != null && !"".equals(this.mAgentGroup)) {
                                                  tLength = this.mAgentGroup.length();
                                                  if (tLength == 18)
                                                    tAgentGrade = "A05";
                                                  if (tLength == 15)
                                                    tAgentGrade = "A07";
                                                  if (tLength == 12)
                                                    tAgentGrade = "A08";
                                                  if (tLength == 10)
                                                    tAgentGrade = "A09";

                                                }
                                                else {
                                                  if (this.mBranchLevel.equals("01")) {
                                                    tLength = 18;
                                                    tAgentGrade = "A05";
                                                  }
                                                  if (this.mBranchLevel.equals("02")) {
                                                    tLength = 15;
                                                    tAgentGrade = "A07";
                                                  }
                                                  if (this.mBranchLevel.equals("03")) {
                                                    tLength = 12;
                                                    tAgentGrade = "A08";
                                                  }
                                                  if (this.mBranchLevel.equals("04")) {
                                                    tLength = 10;
                                                    tAgentGrade = "A09";
                                                  }
                                                }

			String keySql = null;
			String baseSql = null;
			String tFYC = null;
			String branchname = null;
			String agentcode = null;
			String agentname = null;
                                                String agentgrade = null;
                                                String employdate = null;
                                                String startdate = null;

			keySql = "select distinct substr(a.branchattr,1,?tLength?) attr from laindexinfo a where a.indexcalno >= '?mTJStartDate?' and a.indexcalno <= '?mTJEndDate?' "
			+"and a.managecom like concat('?mManageCom?','%') "
			+"and a.indextype = '00' and a.AgentGrade = '?tAgentGrade?' ";

                                            if (this.mAgentGroup != null && !"".equals(this.mAgentGroup))
                                              keySql = keySql + "and a.branchattr like concat('mAgentGroup','%') ";

			keySql = keySql+"and not exists (select 1 from laagent b,labranchgroup c where b.agentcode=a.agentcode "
			+"and b.branchcode=c.agentgroup and c.state='1' ) "
			+"group by substr(a.branchattr,1,?tLength?) ";

			logger.debug("jie d keySql = "+keySql);

			
			 SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			    sqlbv1.sql(keySql);
			    sqlbv1.put("tLength",tLength);
			    sqlbv1.put("mTJStartDate",this.mTJStartDate);
			    sqlbv1.put("mTJEndDate",this.mTJEndDate);
			    sqlbv1.put("mManageCom",this.mManageCom);
			    sqlbv1.put("mAgentGroup",this.mAgentGroup);
			    sqlbv1.put("tAgentGrade",this.tAgentGrade);
			//标志列置为0[用于进行高峰会的筛选置1]同时应用于排名列 展业机构姓名 主管姓名 主管职级
			//累计标保费统计
			baseSql = "select substr(a.branchattr,1,?tLength?) attr,sum(a.t47) yeji "
			+"from laindexinfo a "
			+"where a.indexcalno >='?mTJStartDate?' and a.indexcalno <= '?mTJEndDate?' "
			+"and a.managecom like concat('?mManageCom?','%') "
			+"and a.indextype = '00' and a.AgentGrade like 'A%' ";

			if(this.mAgentGroup != null && !"".equals(this.mAgentGroup))
				baseSql = baseSql+"and a.branchattr like concat('?mAgentGroup?','%') ";

			baseSql = baseSql +"and not exists (select 1 from laagent b,labranchgroup c where b.agentcode=a.agentcode "
			+"and b.branchcode=c.agentgroup and c.state='1' ) "
			+"group by substr(a.branchattr,1,?tLength?) ";

			logger.debug("baseSql["+baseSql+"]");

			 SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			 sqlbv2.sql(baseSql);
			 sqlbv2.put("tLength",tLength);
			 sqlbv2.put("mTJStartDate",this.mTJStartDate);
			 sqlbv2.put("mTJEndDate",this.mTJEndDate);
			 sqlbv2.put("mManageCom",this.mManageCom);
			 sqlbv2.put("mAgentGroup",this.mAgentGroup);
			//FYC累计统计
			tFYC = "select substr(a.branchattr,1,?tLength?) attr,sum(a.f04) yeji "
			+"from lawage a where a.indexcalno >= '?mTJStartDate?' "
			+"and a.indexcalno <= '?mTJEndDate?' "
			+"and a.managecom like concat('?mManageCom?','%') "
			+"and a.AgentGrade like 'A%' ";
			if(this.mAgentGroup != null && !"".equals(this.mAgentGroup))
                                                  tFYC=tFYC+"and a.branchattr like concat('?mAgentGroup?','%') ";

			tFYC = tFYC+" and a.state = '1' group by substr(a.branchattr,1,?tLength?) ";

			logger.debug("jie yunheng tFYC = "+ tFYC);
			 SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			 sqlbv3.sql(tFYC);
			 sqlbv3.put("tLength",tLength);
			 sqlbv3.put("mTJStartDate",this.mTJStartDate);
			 sqlbv3.put("mTJEndDate",this.mTJEndDate);
			 sqlbv3.put("mManageCom",this.mManageCom);
			 sqlbv3.put("mAgentGroup",this.mAgentGroup);
			// 展业机构名字
                                          	branchname ="select attr,(select name from labranchgroup where branchtype ='1' and branchattr=attr) from (select distinct substr(branchattr,1,?tLength?) attr from laindexinfo a where a.indexcalno >= '?mTJStartDate?' and a.indexcalno <= '?mTJEndDate?' "
			+"and a.managecom like concat('?mManageCom?','%') and a.indextype = '00' and a.AgentGrade = '?tAgentGrade?' ";

			if(this.mAgentGroup != null && !"".equals(this.mAgentGroup))
			branchname=branchname+"and a.branchattr like concat('?mAgentGroup?','%') ";

			branchname = branchname+" and not exists "+"(select 1 from laagent b,labranchgroup c"
			+" where b.agentcode=a.agentcode "
			+" and b.branchcode=c.agentgroup and c.state='1' ) "
			+"group by substr(a.branchattr,1,?tLength?)) a ";

			logger.debug("jie yunheng branchname: = "+ branchname);
			 SQLwithBindVariables sqlbv4= new SQLwithBindVariables();
			 sqlbv4.sql(branchname);
			 sqlbv4.put("tLength",tLength);
			 sqlbv4.put("mTJStartDate",this.mTJStartDate);
			 sqlbv4.put("mTJEndDate",this.mTJEndDate);
			 sqlbv4.put("mManageCom",this.mManageCom);
			 sqlbv4.put("mAgentGroup",this.mAgentGroup);
			 sqlbv4.put("tAgentGrade",this.tAgentGrade);
			 
			// 代理人编码select name,employdate from laagent where agentcode=
			agentcode="select attr,(select agentcode from laindexinfo where indexcalno = '?mTJEndDate?' and indextype = '00' and managecom like concat('?mManageCom?','%') and AgentGrade = '?tAgentGrade?' and branchattr like concat(attr,'%')) "
			+"from (select  distinct substr(a.branchattr,1,?tLength?) attr from laindexinfo a where a.indexcalno >= '?mTJStartDate?' and a.indexcalno <= '?mTJEndDate?' and a.managecom like concat('?mManageCom?','%') "
			+"and a.indextype = '00' and a.AgentGrade = '?tAgentGrade?' ";

			if(this.mAgentGroup != null && !"".equals(this.mAgentGroup))
			agentcode=agentcode+"and a.branchattr like concat('?mAgentGroup?','%') ";

			agentcode = agentcode+" and not exists "+"(select 1 from laagent b,labranchgroup c"
			+" where b.agentcode=a.agentcode "
			+" and b.branchcode=c.agentgroup and c.state='1' ) "
			+"group by substr(a.branchattr,1,?tLength?) ) a";
			SQLwithBindVariables sqlbv5= new SQLwithBindVariables();
			sqlbv5.sql(agentcode);
			sqlbv5.put("tLength",tLength);
			sqlbv5.put("mTJStartDate",this.mTJStartDate);
			sqlbv5.put("mTJEndDate",this.mTJEndDate);
			sqlbv5.put("mManageCom",this.mManageCom);
			sqlbv5.put("mAgentGroup",this.mAgentGroup);
			sqlbv5.put("tAgentGrade",this.tAgentGrade);
			 
			logger.debug("jie yunheng agentcode: = "+ agentcode);


                                                agentname = "select attr,(select name from laagent where agentcode=code) from (select attr,(select agentcode from laindexinfo where indexcalno = '?mTJEndDate?' and indextype = '00' and managecom like concat('?mManageCom?','%') and AgentGrade = '?tAgentGrade?' and branchattr like concat(attr,'%') ) code "
                                                +"from (select  distinct substr(a.branchattr,1,?tLength?) attr from laindexinfo a where a.indexcalno >= '?mTJStartDate?' and a.indexcalno <= '?mTJEndDate?' and a.managecom like concat('?mManageCom?','%') "
                                                +"and a.indextype = '00' and a.AgentGrade = '?tAgentGrade?' ";

                                                if(this.mAgentGroup != null && !"".equals(this.mAgentGroup))
                                                agentname=agentname+"and a.branchattr like concat('?mAgentGroup?','%') ";

                                                agentname = agentname+" and not exists "+"(select 1 from laagent b,labranchgroup c"
                                                +" where b.agentcode=a.agentcode "
                                                +" and b.branchcode=c.agentgroup and c.state='1' ) "
                                                +"group by substr(a.branchattr,1,?tLength?) ) a) b";

                                                logger.debug("jie yunheng agentname: = "+ agentname);
                                            	SQLwithBindVariables sqlbv6= new SQLwithBindVariables();
                                            	sqlbv6.sql(agentname);
                                            	sqlbv6.put("tLength",tLength);
                                            	sqlbv6.put("mTJStartDate",this.mTJStartDate);
                                            	sqlbv6.put("mTJEndDate",this.mTJEndDate);
                                            	sqlbv6.put("mManageCom",this.mManageCom);
                                            	sqlbv6.put("mAgentGroup",this.mAgentGroup);
                                            	sqlbv6.put("tAgentGrade",this.tAgentGrade);

                                                agentgrade = "select attr,(select agentgrade1 from latree where agentcode=code) from (select attr,(select agentcode from laindexinfo where indexcalno = '?mTJEndDate?' and indextype = '00' and managecom like concat('?mManageCom?','%')  and AgentGrade = '?tAgentGrade?' and branchattr like concat(attr,'%') ) code "
                                                +"from (select  distinct substr(a.branchattr,1,?tLength?) attr from laindexinfo a where a.indexcalno >= '?mTJStartDate?' and a.indexcalno <= '?mTJEndDate?' and a.managecom like concat('?mManageCom?','%')  "
                                                +"and a.indextype = '00' and a.AgentGrade = '?tAgentGrade?' ";

                                                if(this.mAgentGroup != null && !"".equals(this.mAgentGroup))
                                                agentgrade=agentgrade+"and a.branchattr like concat('?mAgentGroup?','%') ";

                                                agentgrade = agentgrade+" and not exists "+"(select 1 from laagent b,labranchgroup c"
                                                +" where b.agentcode=a.agentcode "
                                                +" and b.branchcode=c.agentgroup and c.state='1' ) "
                                                +"group by substr(a.branchattr,1,?tLength?) ) a) b";

                                                logger.debug("jie yunheng agentgrade: = "+ agentgrade);
                                            	SQLwithBindVariables sqlbv7= new SQLwithBindVariables();
                                            	sqlbv7.sql(agentgrade);
                                            	sqlbv7.put("tLength",tLength);
                                            	sqlbv7.put("mTJStartDate",this.mTJStartDate);
                                            	sqlbv7.put("mTJEndDate",this.mTJEndDate);
                                            	sqlbv7.put("mManageCom",this.mManageCom);
                                            	sqlbv7.put("mAgentGroup",this.mAgentGroup);
                                            	sqlbv7.put("tAgentGrade",this.tAgentGrade);

                                                employdate = "select attr,(select employdate from laagent where agentcode=code) from (select attr,(select agentcode from laindexinfo where indexcalno = '?mTJEndDate?' and indextype = '00' and managecom like concat('?mManageCom?','%')  and AgentGrade = '?tAgentGrade?' and branchattr like concat(attr,'%') ) code "
                                                +"from (select  distinct substr(a.branchattr,1,?tLength?) attr from laindexinfo a where a.indexcalno >= '?mTJStartDate?' and a.indexcalno <= '?mTJEndDate?' and a.managecom like concat('?mManageCom?','%')  "
                                                +"and a.indextype = '00' and a.AgentGrade = '?tAgentGrade?' ";

                                                if(this.mAgentGroup != null && !"".equals(this.mAgentGroup))
                                                employdate=employdate+"and a.branchattr like concat('?mAgentGroup?','%') ";

                                                employdate = employdate+" and not exists "+"(select 1 from laagent b,labranchgroup c"
                                                +" where b.agentcode=a.agentcode "
                                                +" and b.branchcode=c.agentgroup and c.state='1' ) "
                                                +"group by substr(a.branchattr,1,?tLength?)  ) a) b";

                                                logger.debug("jie yunheng employdate: = "+ employdate);
                                                SQLwithBindVariables sqlbv8= new SQLwithBindVariables();
                                                sqlbv8.sql(employdate);
                                                sqlbv8.put("tLength",tLength);
                                                sqlbv8.put("mTJStartDate",this.mTJStartDate);
                                                sqlbv8.put("mTJEndDate",this.mTJEndDate);
                                                sqlbv8.put("mManageCom",this.mManageCom);
                                                sqlbv8.put("mAgentGroup",this.mAgentGroup);
                                                sqlbv8.put("tAgentGrade",this.tAgentGrade);
                                                startdate = "select attr,(select startdate from latree where agentcode=code) from (select attr,(select agentcode from laindexinfo where indexcalno = '?mTJEndDate?' and indextype = '00' and managecom like concat('?mManageCom?','%')  and AgentGrade = '?tAgentGrade?' and branchattr like concat(attr,'%') ) code "
                                                +"from (select  distinct substr(a.branchattr,1,?tLength?) attr from laindexinfo a where a.indexcalno >= '?mTJStartDate?' and a.indexcalno <= '?mTJEndDate?' and a.managecom like concat('?mManageCom?','%')  "
                                                +"and a.indextype = '00' and a.AgentGrade = '?tAgentGrade?' ";

                                                if(this.mAgentGroup != null && !"".equals(this.mAgentGroup))
                                                startdate=startdate+"and a.branchattr like concat('?mAgentGroup?','%') ";

                                                startdate = startdate+" and not exists "+"(select 1 from laagent b,labranchgroup c"
                                                +" where b.agentcode=a.agentcode "
                                                +" and b.branchcode=c.agentgroup and c.state='1' ) "
                                                +"group by substr(a.branchattr,1,?tLength?) ) t) g";

                                                logger.debug("jie yunheng startdate: = "+ startdate);
                                                SQLwithBindVariables sqlbv9= new SQLwithBindVariables();
                                                sqlbv9.sql(startdate);
                                                sqlbv9.put("tLength",tLength);
                                                sqlbv9.put("mTJStartDate",this.mTJStartDate);
                                                sqlbv9.put("mTJEndDate",this.mTJEndDate);
                                                sqlbv9.put("mManageCom",this.mManageCom);
                                                sqlbv9.put("mAgentGroup",this.mAgentGroup);
                                                sqlbv9.put("tAgentGrade",this.tAgentGrade);


			ZHashReport tZHashReport = new ZHashReport(sqlbv1);
			tZHashReport.addSql(sqlbv4);
                                                tZHashReport.addSql(sqlbv6);
			tZHashReport.addSql(sqlbv5);
                                                tZHashReport.addSql(sqlbv7);
                                                tZHashReport.addSql(sqlbv8);
                                                tZHashReport.addSql(sqlbv9);
			tZHashReport.addSql(sqlbv3);
                                                tZHashReport.addSql(sqlbv2);

			tZHashReport.setColumnType(1,tZHashReport.StringType); //标志列
			tZHashReport.setColumnType(2,tZHashReport.StringType); //展业机构名称
			tZHashReport.setColumnType(3,tZHashReport.StringType); //主管姓名
			tZHashReport.setColumnType(4,tZHashReport.StringType); //主管职级
			tZHashReport.setColumnType(5,tZHashReport.StringType);
			tZHashReport.setColumnType(6,tZHashReport.StringType);

			tZHashReport.setDoformat(false);
			tZHashReport.setSumColumn(false);

			//设置排序 标准保费
			// tZHashReport.setCompareStr("5:desc");

			String[][] result = tZHashReport.calItem();

			//表头

			TransferData tTitleTransferData = new TransferData();
			tTitleTransferData.setNameAndValue("TJStartDate",this.mTJStartDate);
			tTitleTransferData.setNameAndValue("TJEndDate",this.mTJEndDate);
			tTitleTransferData.setNameAndValue("AgentGroup",this.mAgentGroup);
			tTitleTransferData.setNameAndValue("Operator",this.mGI.Operator);//制表人
			tTitleTransferData.setNameAndValue("CurrentDT",PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());//制表日期时间

			String sql="select shortname from ldcom where comcode='"+ "?mManageCom?" +"' ";
			SQLwithBindVariables sqlbva = new SQLwithBindVariables();
			sqlbva.sql(sql);
			sqlbva.put("mManageCom", this.mManageCom);
			ExeSQL aExeSQL = new ExeSQL();
			tTitleTransferData.setNameAndValue("ManageCom",aExeSQL.getOneValue(sqlbva));
			if (this.mBranchLevel.equals("01")) tTitleTransferData.setNameAndValue("BranchLevel","营业组");
			if (this.mBranchLevel.equals("02")) tTitleTransferData.setNameAndValue("BranchLevel","营业部");
			if (this.mBranchLevel.equals("03")) tTitleTransferData.setNameAndValue("BranchLevel","督导区");
			if (this.mBranchLevel.equals("04")) tTitleTransferData.setNameAndValue("BranchLevel","总监区");

			HashReport tHashReport = new HashReport();
			//生成路径，模版路径，多维数组
			String tCreatePath = "";
			String tModelPath = "";
			//生成文件名命名规范：报表名_级别_月份.xls
			//注意：此处还需要在生成文件名加些参数。。。。


			tCreatePath = this.mTJStartDate+"_"+this.mTJEndDate+"_"+this.mManageCom+"_"
			+this.mBranchLevel+"_"+this.mAgentGroup+"_TeamFYC.xls";
			tModelPath = this.mModePath+"TeamAccumulativeFYC.xls";
			tCreatePath =this.mFilePath+tCreatePath;   ////test by jieyh
			logger.debug("ded"+tCreatePath);
			logger.debug("ccccc"+tModelPath);
			//有表头
			tHashReport.outputArrayToFile1(tCreatePath,tModelPath,result,tTitleTransferData);


			logger.debug("------prepareData end-----");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			this.dealError("prepareData",ex.toString());
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void dealError(String FuncName,String ErrMsg) {
		CError error = new CError();
		error.moduleName = "TeamAccumulativeFYC";
		error.errorMessage = ErrMsg.trim();
		error.functionName = FuncName.trim();
		this.mErrors.addOneError(error);
	}
	/**
	 * 获取模版和生成文件的路径
	 * @return
	 */
	private boolean getTempPath()
	{
		//获取生成文件路径
		if(!checkDesc())
		{
			return false;
		}
		return true;
	}

	/**
	 * 取模版存放路径
	 * @return
	 */
	private boolean checkDesc()
	{
		logger.debug("@@@@@@@@@@@@@@@@@@@@@");
		LDSysVarDB tLDSysVarDB = null;

		//生成文件的存放路径
		tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar(mFilePathDesc);
		if (tLDSysVarDB.getInfo() == false)
		{
			dealError("checkDesc", "LDSysVar取文件路径(" + mFilePathDesc + ")描述失败");
			return false;
		}
		//生成文件路径
		mFilePath = tLDSysVarDB.getSysVarValue();
	 //mFilePath="D:/test/";
		// mFileName = getFileName();
		logger.debug("mFilePath:"+mFilePath);
	
		//模版路径
		this.mModePath=mTemplatePath;
		//mModePath="D:/test/";
		logger.debug("mModePath:"+mModePath);
		return true;
	}

}
