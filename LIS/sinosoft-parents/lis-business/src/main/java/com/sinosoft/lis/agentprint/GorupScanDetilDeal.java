package com.sinosoft.lis.agentprint;
import org.apache.log4j.Logger;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.util.*;
import java.sql.Connection;
import java.text.*;

	public class GorupScanDetilDeal {
private static Logger logger = Logger.getLogger(GorupScanDetilDeal.class);
		public GlobalInput mGI = new GlobalInput();

		private	String tManageCom ="";
		private	String tPrtNO ="";
		private   String tStartDay ="";
		private	String tEndDay  ="";
		private   String ttjtype  ="";
        private String mFilePathDesc = "TKReportCreat"; //生成文件的路径,待修改
		private String mTemplatePath="";
		private String mFilePath = ""; //生成文件路径
		private String mModePath = "";//模版路径
		public VData mResult = new VData();
		public CErrors mErrors = new CErrors();

		public GorupScanDetilDeal() {
		}



		public boolean getInputData(VData tInputData) {
			try  {				
				this.tManageCom = (String)tInputData.getObjectByObjectName("String",0);
				this.tPrtNO = (String)tInputData.getObjectByObjectName("String",1);
				this.tStartDay=(String)tInputData.getObjectByObjectName("String",2);
				this.tEndDay = (String)tInputData.getObjectByObjectName("String",3);
				this.ttjtype = (String)tInputData.getObjectByObjectName("String",4);
		        this.mGI= (GlobalInput)tInputData.getObjectByObjectName("GlobalInput",5);
		        this.mTemplatePath=(String)tInputData.getObjectByObjectName("String",6);;
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
			
		String	tmanagesql="";
		String	tprtnosql="";
		String	tstartsql="";
		String	tendsql="";
		String	ttjtypesql="";
		String  keysql="";
		String  tCreatePath = "";
		String  tModelPath = "";
		String[][] result=null;
		
		
		   //只取首期业绩	.
			if (tManageCom!=null && !tManageCom.equals(""))
				
				{
				tmanagesql=" and managecom like concat('"+"?like?"+"','%')";}
			    
			if (tPrtNO!=null && !tPrtNO.equals(""))
			   {
				tprtnosql=" and a.doccode='"+"?doccode?"+"'";
				
			   }
			
			if (tStartDay!=null && !tStartDay.equals(""))
			   {
				tstartsql=" and a.makedate>='"+"?makedate?"+"'";
				
			   }
			
			if (tEndDay!=null && !tEndDay.equals(""))
			   {
				tendsql=" and a.makedate<='"+"?makedate1?"+"'";
				
			   }

			
			if (ttjtype!=null && !ttjtype.equals("")&&ttjtype.equals("1"))
			{
				ttjtypesql="and exists ( select '1' from lcgrpcont where a.doccode = prtno  and appflag='"+"?appflag?"+"')";
			}
			/*keysql="select a.doccode,a.managecom,a.makedate,"
                  +" (select decode(appflag, 0, '未签单', '已签单')  from lcgrpcont where a.doccode = prtno)"
	              +" from es_doc_main a where subtype = 'UA002'"
	              +" and exists (select 1  from laagent b, lcgrpcont c 	where b.branchtype = '2'"
				  +" and a.doccode = c.prtno and c.agentcode = b.agentcode) "+tmanagesql+tprtnosql+tstartsql+tendsql
                  +ttjtypesql;*/
			
			 keysql="select a.doccode,a.managecom,a.makedate,"
	                  +" (select (case appflag  when '0' then '未签单' else '已签单' end) from lcgrpcont where a.doccode = prtno)"
		              +" from es_doc_main a where subtype = 'UA002'"
		              +" and exists (select 1  from laagent b, lcgrpcont c 	where b.branchtype = '2'"
					  +" and a.doccode = c.prtno and c.agentcode = b.agentcode) "+tmanagesql+tprtnosql+tstartsql+tendsql
	                  +ttjtypesql;     
			 SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			    sqlbv1.sql(keysql);
			    sqlbv1.put("like", tManageCom);
				sqlbv1.put("doccode",tPrtNO);
				sqlbv1.put("makedate",tStartDay);
				sqlbv1.put("makedate1",tEndDay);
				sqlbv1.put("appflag",ttjtype);

	        ZHashReport tZHashReport = new ZHashReport(sqlbv1);
	
			tZHashReport.setColumnType(1, tZHashReport.StringType); // 代理人姓名
			tZHashReport.setColumnType(2, tZHashReport.StringType); // 代理人职级
			tZHashReport.setColumnType(3, tZHashReport.StringType);// 展业机构代码;
	

			tZHashReport.setDoformat(false);
			tZHashReport.setSumColumn(false);
			 result = tZHashReport.calItem();

			tCreatePath ="GorupScanDetil_"+this.tManageCom+"_"+this.tPrtNO+"_"+this.tStartDay+"_"+this.tEndDay+"_"+this.ttjtype+".xls";
			tModelPath = this.mModePath+"GorupScanDetil.xls";
			tCreatePath =this.mFilePath+tCreatePath;   ////test by jieyh
			logger.debug("ded"+tCreatePath);
			logger.debug("ccccc"+tModelPath);
			
	


					TransferData tTitleTransferData = new TransferData();
					String sql="select shortname from ldcom where comcode='"+"?tManageCom?"+"' ";
					SQLwithBindVariables sqlbva = new SQLwithBindVariables();
					sqlbva.sql(sql);
					sqlbva.put("tManageCom", this.tManageCom);
					ExeSQL aExeSQL = new ExeSQL();
					tTitleTransferData.setNameAndValue("ManageCom",aExeSQL.getOneValue(sqlbva));
					tTitleTransferData.setNameAndValue("Prtno",tPrtNO);
					tTitleTransferData.setNameAndValue("StartDay",this.tStartDay);
					tTitleTransferData.setNameAndValue("EndDay",this.tEndDay);
					tTitleTransferData.setNameAndValue("Operator",this.mGI.Operator);//制表人
					tTitleTransferData.setNameAndValue("CurrentDT",PubFun.getCurrentDate()+" "+PubFun.getCurrentTime());//制表日期时间
				
					

					HashReport tHashReport = new HashReport();
					//生成路径，模版路径，多维数组
				
					//生成文件名命名规范：报表名_级别_月份.xls
					//注意：此处还需要在生成文件名加些参数。。。。


					//有表头
					tHashReport.outputArrayToFile1(tCreatePath,tModelPath,result,tTitleTransferData);
  
				}
				
			
			catch(Exception ex)
			{
				ex.printStackTrace();
				this.dealError("prepareData",ex.toString());
				return false;
			}
			return true;
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
			// mFileName = getFileName();
		 // mFilePath="D:/test/";
			logger.debug("mFilePath:"+mFilePath);
			//取模版路径
		
			this.mModePath=mTemplatePath;
			//mModePath="D:/zhujc/";
			logger.debug("mModePath:"+mModePath);
			return true;
		}

	}

	
	
	
	


