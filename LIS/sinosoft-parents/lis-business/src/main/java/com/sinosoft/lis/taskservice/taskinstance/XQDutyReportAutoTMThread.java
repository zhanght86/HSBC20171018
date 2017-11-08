package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.f1j.ss.BookModelImpl;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.db.LMEdorCalDB;
import com.sinosoft.lis.operfee.IndiDueFeePartQuery;
import com.sinosoft.lis.operfee.RnDealBL;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubConcurrencyLock;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reagent.LRPolListCDownUI;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LMEdorCalSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.RSWrapper;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.workflow.bq.EdorWorkFlowUI;
/**
  *
  * 续期应收清单自动生成 -入口
  * author zhangfh
**/
public class XQDutyReportAutoTMThread extends TaskThread {
	/** 公共锁定号码类 */
	private PubConcurrencyLock mPubLock = new PubConcurrencyLock();	
	private static Logger logger = Logger.getLogger(XQDutyReportAutoTMThread.class);
	public CErrors mErrors = new CErrors();
    private GlobalInput tG = new GlobalInput();
    private String mCurrentDate = PubFun.getCurrentDate();
    private SSRS mSSRS=new SSRS();
    String mDelFlag="";
	private String mManageCom = "";
	private String mFilePath = ""; //通过描述得到的文件路径
	private String mAgentCode = "";
	private String mStartDate = "";
	private String mEndDate = "";
	private String mFileModeDesc = "ReAgentAnyDown1.xls"; //描述的模版名称
	private String mType1 = "";   //1：孤儿单；2：在职单；3：不区分
	private String mType2 = "";   //1：二次；2：三次；3：不区分
	private String mType3 = "";   //1：续期件；2：续保件；3：不区分
	private String mType4 = "";   //1：年交；0：非年交
	private ExeSQL mExeSQL = new ExeSQL();
	private String mFileName = ""; //要生成的文件名
	private String currentdate = PubFun.getCurrentDate();
	private String currenttime = PubFun.getCurrentTime();
	private String mFilePathDesc = "XSCreatListPath"; //描述的生成结果文件存放路径
	private String payintv_sql = "";//交费类型sql
	private BookModelImpl m_book = new BookModelImpl();
	private String mBranchType = "";   //4：续收外勤；99：收展
	private int mCurrentRow = 1; //行数
	public XQDutyReportAutoTMThread() {
	}

	public boolean dealMain() {
		/* 业务处理逻辑 */
		logger.debug("进入业务逻辑处理!--保续期应收清单自动生成批处理" + PubFun.getCurrentDate()
				+ "**" + PubFun.getCurrentTime());
		tG.ComCode = "86";
		tG.Operator = "AUTO";
		tG.ManageCom = "86";
		
		 //日志监控,初始化           	
		tG.LogID[0]="TASK"+(String)mParameters.get("TaskCode"); 		
		tG.LogID[1]=(String)mParameters.get("SerialNo"); 	
	  	PubFun.logTrack(tG,tG.LogID[1],"续期应收清单自动生成开始");
//		日志监控，性能监控
		PubFun.logPerformance (tG,tG.LogID[1],"续期应收清单自动生成","1");
		
		//启动时间        	
    	Date  dNow =  new Date();         	
    	long initTime =dNow.getTime();   
		
		// 每次最大取数量,默认是100条
		int tConutMax = 100;
		String tResult = "";
		String tSQL_Count = "select sysvarvalue from ldsysvar where sysvar='XQDutyReportAutoCount' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL_Count);
		ExeSQL tExeSQL = new ExeSQL();
		tResult = tExeSQL.getOneValue(sqlbv);
		// 如果有描述的话,取描述的数据
		if (tResult != null && !tResult.equals("")) {
			tConutMax = Integer.parseInt(tResult);
		}
		Vector mTaskWaitList = new Vector();
		logger.debug("续期应收清单自动生成开始：RnewPolAuto...");

        /*业务处理逻辑*/
        logger.debug("start XQDutyReportAuto!!");
        logger.debug("进入业务逻辑处理,开始提数计算。!");

        String date_sql = "";
        String YearMonth = "";
        
        String StartDate = "";  //统计起期
        String EndDate = "";    //统计止期
        
        String LLast_StartDate = "";  //宽末起期
        String LLast_EndDate = "";    //宽末止期
        
        String Last_StartDate = "";  //宽一起期
        String Last_EndDate = "";    //宽一止期
        
        String Cur_StartDate = "";  //当月起期
        String Cur_EndDate = "";    //当月止期
        
        String Next_StartDate = "";  //下月起期
        String Next_EndDate = "";    //下月止期
        
        tExeSQL = new ExeSQL();
        //获取模板路径
        String mModePath = "";
        LDSysVarDB tLDSysVarDB2 = new LDSysVarDB();
        tLDSysVarDB2.setSysVar("LAAutoReport");
        if (!tLDSysVarDB2.getInfo()) 
        {
        	logger.debug("获取模板路径失败");
        }
        mModePath = tLDSysVarDB2.getSysVarValue();
        
        String str_com=" select comcode from ldcom where comcode like '86%' and char_length(comcode)=6  order by comcode ";
        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
        sqlbv1.sql(str_com);
        //str_com="select '86110000' from dual";
        SSRS SSRS_com = new SSRS();
        SSRS_com = tExeSQL.execSQL(sqlbv1);

        //宽末报表运行   月初每周三、周五自动运行  	25号后，天天运行
        int LLast_count =0;
        String LLast_check1=" select (select count(*) from dual where to_char(now(),'D')=4 ) "
         + "+ (select count(*) from dual  where to_char(now(),'D')=6 ) "
         + "+ (select count(*) from dual where substr(now(),9,2)>25)  from dual";
        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
        sqlbv2.sql(LLast_check1);
        LLast_count = Integer.parseInt(tExeSQL.getOneValue(sqlbv2));
        int xLength = 0;
        if(LLast_count>0)
        {     //符合条件的话需要生成相应的宽末报表
        	  
      	      logger.debug("开始宽末应收报表自动生成！");
      	      //获取宽末起止期
      	      date_sql=" select startdate,enddate  from lastatsegment where stattype='1' and yearmonth=to_char(add_months(now(),-2),'yyyymm') " ;
	          SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
	          sqlbv3.sql(date_sql);
      	      SSRS date_ssrs = new SSRS();
	          date_ssrs = tExeSQL.execSQL(sqlbv3);
	          StartDate = date_ssrs.GetText(1, 1);
	          EndDate = date_ssrs.GetText(1, 2);
	          logger.debug("StartDate:"+StartDate );
	          logger.debug("EndDate:"+EndDate );

			   if (date_ssrs == null ||date_ssrs.MaxRow <= 0) {
					
			   }
			   else
			   {
		       xLength=SSRS_com.getMaxRow();
	           for(int i=1;i<=SSRS_com.getMaxRow();i++)
	           {
	        	  try {
	              //获取文件生成路径以及文件名
	              if (!checkDesc(mDelFlag))
	              {
	                  return false;
	        	  }
	        	  m_book.read(mModePath + this.mFileModeDesc,new com.f1j.ss.ReadParams());
	        	  m_book.setSheetSelected(0, true);
	        	  m_book.setCol(0);
	        	  mCurrentRow = 1;
	        	  mBranchType = "4";   //4：续收外勤；99：收展
	              String tManageCom = SSRS_com.GetText(i, 1);
	              TransferData tTransferData=new TransferData();
	          
	              tTransferData.setNameAndValue("ManageCom",tManageCom);
	              tTransferData.setNameAndValue("AgentCode","");
	              tTransferData.setNameAndValue("StartDate",StartDate); 
	              tTransferData.setNameAndValue("EndDate",EndDate);
	              tTransferData.setNameAndValue("Type1","3");
	              tTransferData.setNameAndValue("Type2","3");
	              tTransferData.setNameAndValue("Type3","3");
	              tTransferData.setNameAndValue("Type4","1");
	              tTransferData.setNameAndValue("BranchType","4");
	              tTransferData.setNameAndValue("Operator",tG.Operator);
	              tTransferData.setNameAndValue("XSExcTemplate",mModePath);
	              
	          	  String mManageCom = tManageCom;
	        	  String mAgentCode = "";
	        	  String mStartDate = StartDate;
	        	  String mEndDate = EndDate;
	        	  String mType1 = "3";   //1：孤儿单；2：在职单；3：不区分
	        	  String mType2 = "3";   //1：二次；2：三次；3：不区分
	        	  String mType3 = "3";   //1：续期件；2：续保件；3：不区分
	        	  String mType4 = "1";   //1：年交；0：非年交
	        	  ExeSQL mExeSQL = new ExeSQL();
	        	  String currentdate = PubFun.getCurrentDate();
	        	  String currenttime = PubFun.getCurrentTime();
	              
	        	  payintv_sql="";
	              if(mType4.equals("1"))
	              {
	            	  payintv_sql=" and a.payintv=12 ";
	              }
	              else
	              {
	            	  payintv_sql=" and a.payintv in (1,3,6) ";
	              }
	              logger.debug("交费类型语句："+payintv_sql);
	              
	              VData tVData = new VData();
	              tVData.addElement(tTransferData);
	              tVData.addElement(tG);
	              if(getSql(tVData))
	              {}
	              
	              
	      		 
	      			SSRS hSSRS=new SSRS();
	      			SSRS tSSRS=new SSRS();
					// 循环对每个保单情况进行区分，并进行抽档操作，每个保单分别提交
					VData mVData = new VData();
					 if (mSSRS == null ||mSSRS.MaxRow <= 0) {
							
					 }
					 else
					 {
	                for(int j=1; j <= mSSRS.getMaxRow(); j++)
	        	    {
	                	if(((mType1.equals("1"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("1"))||(mType2.equals("3"))) )  //孤儿单
	        	        {
//	                		   if(!DealReConTwo(tAgentCode))
//	                           {
//	                               return false;
//	                           }
	                		 tSSRS=getDealReConSql(mSSRS.GetText(i,1));
	                		 
	        	        }
	                   	if(((mType1.equals("1"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("2"))||(mType2.equals("3"))) )  //孤儿单
	        	        {
//	                		   if(!DealReConTwo(tAgentCode))
//	                           {
//	                               return false;
//	                           }
	                		 tSSRS=getDealReConThreeSql(mSSRS.GetText(i,1));
	                		   
	        	        }
	                	if(((mType1.equals("1"))||(mType1.equals("3")))&&((mType3.equals("2"))||(mType3.equals("3"))) )  //孤儿单
	                	{
	  	                  //3--孤儿单需要续保的主险清单
//	  	                  if(!DealReMainPol(tAgentCode))
//	  	                   {
//	  	                      return false;
//	  	                   }
	                		tSSRS=getDealReMainPolSql(mSSRS.GetText(i,1));

//	  	                   //4--孤儿单需要续保的附加险清单
//	  	                  if(!DealReSubPol(tAgentCode))
//	  	                   {
//	  	                      return false;
//	  	                   }
//	                		hSSRS=getDealReSubPolSql(mSSRS.GetText(i,1));
	                	}
	                	
	                	if(((mType1.equals("2"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("1"))||(mType2.equals("3"))) )  //孤儿单
	                	{
//	                		 if(!DealAdimConTwo(tAgentCode))
	                		tSSRS=getDealAdimConTwoSql(mSSRS.GetText(i,1));
	                	}
	                	if(((mType1.equals("2"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("2"))||(mType2.equals("3"))) )  //孤儿单
	                	{
//	                		  if(!DealAdimConThree(tAgentCode))
	                		tSSRS=getDealAdimConThreeSql(mSSRS.GetText(i,1));
	                	}
	                	if(((mType1.equals("2"))||(mType1.equals("3")))&&((mType3.equals("2"))||(mType3.equals("3"))) )  //孤儿单
	                	{
//	                		if(!DealAdimMainPol(tAgentCode))
//		                     {
//		                         return false;
//		                     }
	                		tSSRS=getDealAdimMainPolSql(mSSRS.GetText(i,1));
		                    //8--在职单需要续保的附加险
//		                   if(!DealAdimSubPol(tAgentCode))
//		                     {
//		                         return false;
//		                     }
//	                		hSSRS=getDealAdimSubPolSql(mSSRS.GetText(i,1));

	                	}
	                	xLength=tSSRS.getMaxRow();
	           		 if (tSSRS == null ||tSSRS.MaxRow <= 0) {
							
					 }
					 else
					 {
						 tVData.clear();
					 
	                  for(int x=1;x<=tSSRS.getMaxRow();x++)
	                  {
	                	  LCPolSchema tLCPolSchema = new LCPolSchema();
	                	  LCPolDB tLCPolDB=new LCPolDB();
	              		  tLCPolDB = new LCPolDB();
	              		  tLCPolDB.setPolNo(tSSRS.GetText(i, 1));
	              		  tLCPolDB.getInfo();
	              		  tLCPolSchema.setSchema(tLCPolDB.getSchema());
	                	  mVData.clear();
	                	  
	                  	  Map map = new HashMap();
	    				  map.put("LCPolSchema", tLCPolSchema);
	    				  map.put("TransferData", tTransferData);
	    				  map.put("GlobalInput", tG);
	    				  map.put("tSSRS",tSSRS);
	    				  map.put("m_book",m_book);
	    				  tVData.add(map);
	                	  if(x%tConutMax==0)
	                	  {
	                		  mTaskWaitList.add(mVData);
	                		  tVData = new VData();
	                	  }
						
	                	  if(x%tConutMax!=0&&x==tSSRS.getMaxRow())
	                	  {
	                		  mTaskWaitList.add(mVData);
	                		  tVData = new VData();
	                	  } 
	                  }
	        	    }
	        	    }
	               }
	      		  
	      		  
	     	String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
					+ (String) this.mParameters.get("TaskCode") + ":"
					+ (String) this.mParameters.get("TaskPlanCode");
			// tongmeng 2009-05-21 add
			// 自适应线程数
			int tThreadCount = 5;
			tExeSQL = new ExeSQL();
			String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end ) from ldsysvar where sysvar ='XQDutyReportAutoThread'";
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(tSQL_ThreadCount);
			String tSignMThreadCount = tExeSQL.getOneValue(sqlbv4);
			if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
					&& Integer.parseInt(tSignMThreadCount) > 0) {
				tThreadCount = Integer.parseInt(tSignMThreadCount);
			}
			if (xLength < tThreadCount) {
				tThreadCount =xLength;
			}
			
			this.mServiceA = new ServiceA(
					"com.sinosoft.lis.reagent.LRPolListCDownBLTMThread", mTaskWaitList,
					tThreadCount, 10, TaskCode);
			this.mServiceA.start();
			
		    m_book.setEntry(mCurrentRow,  0, "制表人:"+tG.Operator); //制表人
		    m_book.setEntry(mCurrentRow,  1, "制表日期:"+currentdate); //制表时间
		      //mFilePath = "D:\\TEMP\\";//本地测试用
		    logger.debug("@@"+mFilePath +"##"+mFileName);
		      //生成文件
		    m_book.write(mFilePath + mFileName,new com.f1j.ss.WriteParams(com.f1j.ss.BookModelImpl.eFileExcel97));
	       }catch (Exception e) {
	      	   e.printStackTrace();
	      	   CError.buildErr(this, e.toString());
	      	   return false;
	      } finally {
	      	mPubLock.unLock();// 解锁
	      }
        }
		}    
      }
        //完成时间
    	Date  dend = new Date();         	
    	long  endTime = dend.getTime(); 
    	long tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	
    	PubFun.logResult(tG,tG.LogID[1], "续期应收清单自动生成批处理下月报表  末报表运行   月初每周三、周五自动运行  	25号后，天天运行"+String.valueOf(xLength)+"笔,共花费"+tt+"秒");
    	

        //然后是宽一报表  每周四 一次	
        int Last_count =0;
        initTime =dNow.getTime();
         xLength = 0;
        String Last_check=" select count(*) from dual where to_char(now(),'D')=5  ";
        SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
        sqlbv5.sql(Last_check);
        Last_count = Integer.parseInt(tExeSQL.getOneValue(sqlbv5));
        if(Last_count>0)
        {  //符合条件的话需要生成相应的宽一报表
      	  
      	  logger.debug("开始宽一应收报表自动生成！");
      	  //获取起止期
      	  date_sql=" select startdate,enddate  from lastatsegment where stattype='1' and yearmonth=to_char(add_months(now(),-1),'yyyymm') " ;
      	  SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
      	  sqlbv6.sql(date_sql);
	          SSRS date_ssrs = new SSRS();
	          date_ssrs = tExeSQL.execSQL(sqlbv6);
	          StartDate = date_ssrs.GetText(1, 1);
	          EndDate = date_ssrs.GetText(1, 2);
	          logger.debug("StartDate:"+StartDate );
	          logger.debug("EndDate:"+EndDate );
	          
      	  for(int i=1;i<=SSRS_com.getMaxRow();i++)
	          {
      		try {
         		 if (!checkDesc(mDelFlag))
                {
                    return false;
          	  	 }
         		 m_book.read(mModePath + this.mFileModeDesc,new com.f1j.ss.ReadParams());
         		 m_book.setSheetSelected(0, true);
         		 m_book.setCol(0);
          	  	mCurrentRow = 1;
          	  	mBranchType = "4";   //4：续收外勤；99：收展
      		  	
	              String tManageCom = SSRS_com.GetText(i, 1);
	              TransferData tTransferData=new TransferData();
	          
	              tTransferData.setNameAndValue("ManageCom",tManageCom);
	              tTransferData.setNameAndValue("AgentCode","");
	              tTransferData.setNameAndValue("StartDate",StartDate); 
	              tTransferData.setNameAndValue("EndDate",EndDate);
	              tTransferData.setNameAndValue("Type1","3");
	              tTransferData.setNameAndValue("Type2","3");
	              tTransferData.setNameAndValue("Type3","3");
	              tTransferData.setNameAndValue("Type4","1");
	              tTransferData.setNameAndValue("BranchType","4");
	              tTransferData.setNameAndValue("Operator",tG.Operator);
	              tTransferData.setNameAndValue("XSExcTemplate",mModePath);
	              
	              String mManageCom = tManageCom;
	        	  String mAgentCode = "";
	        	  String mStartDate = StartDate;
	        	  String mEndDate = EndDate;
	        	  String mType1 = "3";   //1：孤儿单；2：在职单；3：不区分
	        	  String mType2 = "3";   //1：二次；2：三次；3：不区分
	        	  String mType3 = "3";   //1：续期件；2：续保件；3：不区分
	        	  String mType4 = "1";   //1：年交；0：非年交
	        	  ExeSQL mExeSQL = new ExeSQL();
	        	  String currentdate = PubFun.getCurrentDate();
	        	  String currenttime = PubFun.getCurrentTime();
	              
	        	  payintv_sql="";
	              if(mType4.equals("1"))
	              {
	            	  payintv_sql=" and a.payintv=12 ";
	              }
	              else
	              {
	            	  payintv_sql=" and a.payintv in (1,3,6) ";
	              }
	              logger.debug("交费类型语句："+payintv_sql);
	              
	              VData tVData = new VData();
	              tVData.addElement(tTransferData);
	              tVData.addElement(tG);
	              if(getSql(tVData))
	              {}
	              
	              SSRS hSSRS=new SSRS();
	      			SSRS tSSRS=new SSRS();
					// 循环对每个保单情况进行区分，并进行抽档操作，每个保单分别提交
					VData mVData = new VData();
					 if (mSSRS == null ||mSSRS.MaxRow <= 0) {
							
					 }
					 else{
	                for(int j=1; j <= mSSRS.getMaxRow(); j++)
	        	    {
	                	if(((mType1.equals("1"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("1"))||(mType2.equals("3"))) )  //孤儿单
	        	        {
//	                		   if(!DealReConTwo(tAgentCode))
//	                           {
//	                               return false;
//	                           }
	                		 tSSRS=getDealReConSql(mSSRS.GetText(i,1));
	                		 
	        	        }
	                   	if(((mType1.equals("1"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("2"))||(mType2.equals("3"))) )  //孤儿单
	        	        {
//	                		   if(!DealReConTwo(tAgentCode))
//	                           {
//	                               return false;
//	                           }
	                		 tSSRS=getDealReConThreeSql(mSSRS.GetText(i,1));
	                		   
	        	        }
	                	if(((mType1.equals("1"))||(mType1.equals("3")))&&((mType3.equals("2"))||(mType3.equals("3"))) )  //孤儿单
	                	{
	  	                  //3--孤儿单需要续保的主险清单
//	  	                  if(!DealReMainPol(tAgentCode))
//	  	                   {
//	  	                      return false;
//	  	                   }
	                		tSSRS=getDealReMainPolSql(mSSRS.GetText(i,1));

//	  	                   //4--孤儿单需要续保的附加险清单
//	  	                  if(!DealReSubPol(tAgentCode))
//	  	                   {
//	  	                      return false;
//	  	                   }
//	                		hSSRS=getDealReSubPolSql(mSSRS.GetText(i,1));
	                	}
	                	
	                	if(((mType1.equals("2"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("1"))||(mType2.equals("3"))) )  //孤儿单
	                	{
//	                		 if(!DealAdimConTwo(tAgentCode))
	                		tSSRS=getDealAdimConTwoSql(mSSRS.GetText(i,1));
	                	}
	                	if(((mType1.equals("2"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("2"))||(mType2.equals("3"))) )  //孤儿单
	                	{
//	                		  if(!DealAdimConThree(tAgentCode))
	                		tSSRS=getDealAdimConThreeSql(mSSRS.GetText(i,1));
	                	}
	                	if(((mType1.equals("2"))||(mType1.equals("3")))&&((mType3.equals("2"))||(mType3.equals("3"))) )  //孤儿单
	                	{
//	                		if(!DealAdimMainPol(tAgentCode))
//		                     {
//		                         return false;
//		                     }
	                		tSSRS=getDealAdimMainPolSql(mSSRS.GetText(i,1));
		                    //8--在职单需要续保的附加险
//		                   if(!DealAdimSubPol(tAgentCode))
//		                     {
//		                         return false;
//		                     }
//	                		hSSRS=getDealAdimSubPolSql(mSSRS.GetText(i,1));

	                	}
	                	xLength=tSSRS.getMaxRow();
	                	tVData.clear();
	   				 if (tSSRS == null ||tSSRS.MaxRow <= 0) {}
					 else{
	                  for(int x=1;x<=tSSRS.getMaxRow();x++)
	                  {
	                	  LCPolSchema tLCPolSchema = new LCPolSchema();
	                	  LCPolDB tLCPolDB=new LCPolDB();
	              		  tLCPolDB = new LCPolDB();
	              		  tLCPolDB.setPolNo(tSSRS.GetText(i, 1));
	              		  tLCPolDB.getInfo();
	              		  tLCPolSchema.setSchema(tLCPolDB.getSchema());
	                	  mVData.clear();
	                	  Map map = new HashMap();
	    				  map.put("LCPolSchema", tLCPolSchema);
	    				  map.put("TransferData", tTransferData);
	    				  map.put("GlobalInput", tG);
	    				  map.put("tSSRS",tSSRS);
	    				  map.put("m_book",m_book);
	    				  tVData.add(map);
	                	  if(x%tConutMax==0)
	                	  {
	                		  mTaskWaitList.add(tVData);
	                		  tVData = new VData();
	                	  }
						
	                	  if(x%tConutMax!=0&&x==tSSRS.getMaxRow())
	                	  {
	                		  mTaskWaitList.add(tVData);
	                		  tVData = new VData();
	                	  } 
	                  }
	        	    }
	                }
				}
	      		  
	     	String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
					+ (String) this.mParameters.get("TaskCode") + ":"
					+ (String) this.mParameters.get("TaskPlanCode");
			// tongmeng 2009-05-21 add
			// 自适应线程数
			int tThreadCount = 5;
			tExeSQL = new ExeSQL();
			String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='XQDutyReportAutoThreadCount'";
			SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
			sqlbv7.sql(tSQL_ThreadCount);
			String tSignMThreadCount = tExeSQL.getOneValue(sqlbv7);
			if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
					&& Integer.parseInt(tSignMThreadCount) > 0) {
				tThreadCount = Integer.parseInt(tSignMThreadCount);
			}
			if (xLength < tThreadCount) {
				tThreadCount =xLength;
			}
			
			this.mServiceA = new ServiceA(
					"com.sinosoft.lis.reagent.LRPolListCDownBLTMThread", mTaskWaitList,
					tThreadCount, 10, TaskCode);
			this.mServiceA.start();
			
		    m_book.setEntry(mCurrentRow,  0, "制表人:"+tG.Operator); //制表人
		    m_book.setEntry(mCurrentRow,  1, "制表日期:"+currentdate); //制表时间
		      //mFilePath = "D:\\TEMP\\";//本地测试用
		    logger.debug("@@"+mFilePath +"##"+mFileName);
		      //生成文件
		    m_book.write(mFilePath + mFileName,new com.f1j.ss.WriteParams(com.f1j.ss.BookModelImpl.eFileExcel97));
	       
	      }catch (Exception e) {
	      	   e.printStackTrace();
	      	   CError.buildErr(this, e.toString());
	      	   return false;
	      } finally {
	      	mPubLock.unLock();// 解锁
	      }
      	}
       }
        
        
        //完成时间
    	dend = new Date();         	
    	endTime = dend.getTime(); 
    	tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	
    	PubFun.logResult(tG,tG.LogID[1], "续期应收清单自动生成批处理下月报表  宽一报表  每周四 一次"+String.valueOf(xLength)+"笔,共花费"+tt+"秒");
    	
        
        //然后是当月报表  每周一 一次	
        
        initTime =dNow.getTime();
        int Cur_count =0;
        String Cur_check=" select count(*) from dual where to_char(now(),'D')=2  ";
        SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
        sqlbv8.sql(Cur_check);
        Cur_count = Integer.parseInt(tExeSQL.getOneValue(sqlbv8));
         xLength = 0;
        if(Cur_count>0)
        {  //符合条件的话需要生成相应的当月报表
          
      	  logger.debug("开始当月应收报表自动生成！");
      	  //获取起止期
      	  date_sql=" select startdate,enddate  from lastatsegment where stattype='1' and yearmonth=to_char(now(),'yyyymm') " ;
      	  SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
      	  sqlbv9.sql(date_sql);
	          SSRS date_ssrs = new SSRS();
	          date_ssrs = tExeSQL.execSQL(sqlbv9);
	          StartDate = date_ssrs.GetText(1, 1);
	          EndDate = date_ssrs.GetText(1, 2);
	          logger.debug("StartDate:"+StartDate );
	          logger.debug("EndDate:"+EndDate );
		  if (SSRS_com != null && !SSRS_com.equals("")) {}
		 else{
      	  for(int i=1;i<=SSRS_com.getMaxRow();i++)
	      {
      		 try {
              	 	if (!checkDesc(mDelFlag))
              	 	{
                     return false;
              	 	}
                  m_book.read(mModePath + this.mFileModeDesc,new com.f1j.ss.ReadParams());
                  m_book.setSheetSelected(0, true);
                  m_book.setCol(0);
               	  mCurrentRow = 1;
                  mBranchType = "4";   //4：续收外勤；99：收展
      		  	  
	              String tManageCom = SSRS_com.GetText(i, 1);
	              TransferData tTransferData=new TransferData();
	          
	              tTransferData.setNameAndValue("ManageCom",tManageCom);
	              tTransferData.setNameAndValue("AgentCode","");
	              tTransferData.setNameAndValue("StartDate",StartDate); 
	              tTransferData.setNameAndValue("EndDate",EndDate);
	              tTransferData.setNameAndValue("Type1","3");
	              tTransferData.setNameAndValue("Type2","3");
	              tTransferData.setNameAndValue("Type3","3");
	              tTransferData.setNameAndValue("Type4","1");
	              tTransferData.setNameAndValue("BranchType","4");
	              tTransferData.setNameAndValue("Operator",tG.Operator);
	              tTransferData.setNameAndValue("XSExcTemplate",mModePath);
	              
	              String mManageCom = tManageCom;
	        	  String mAgentCode = "";
	        	  String mStartDate = StartDate;
	        	  String mEndDate = EndDate;
	        	  String mType1 = "3";   //1：孤儿单；2：在职单；3：不区分
	        	  String mType2 = "3";   //1：二次；2：三次；3：不区分
	        	  String mType3 = "3";   //1：续期件；2：续保件；3：不区分
	        	  String mType4 = "1";   //1：年交；0：非年交
	        	  ExeSQL mExeSQL = new ExeSQL();
	        	  String currentdate = PubFun.getCurrentDate();
	        	  String currenttime = PubFun.getCurrentTime();
	              
	        	  payintv_sql="";
	              if(mType4.equals("1"))
	              {
	            	  payintv_sql=" and a.payintv=12 ";
	              }
	              else
	              {
	            	  payintv_sql=" and a.payintv in (1,3,6) ";
	              }
	              logger.debug("交费类型语句："+payintv_sql);
	              
	              VData tVData = new VData();
	              tVData.addElement(tTransferData);
	              tVData.addElement(tG);
	              if(getSql(tVData))
	              {}
	              
	              SSRS hSSRS=new SSRS();
	      		  SSRS tSSRS=new SSRS();
					// 循环对每个保单情况进行区分，并进行抽档操作，每个保单分别提交
					VData mVData = new VData();
				  if (mSSRS == null ||mSSRS.MaxRow <= 0) {}
				  else{
	                for(int j=1; j <= mSSRS.getMaxRow(); j++)
	        	    {
	                	if(((mType1.equals("1"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("1"))||(mType2.equals("3"))) )  //孤儿单
	        	        {
//	                		   if(!DealReConTwo(tAgentCode))
//	                           {
//	                               return false;
//	                           }
	                		 tSSRS=getDealReConSql(mSSRS.GetText(i,1));
	                		 
	        	        }
	                   	if(((mType1.equals("1"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("2"))||(mType2.equals("3"))) )  //孤儿单
	        	        {
//	                		   if(!DealReConTwo(tAgentCode))
//	                           {
//	                               return false;
//	                           }
	                		 tSSRS=getDealReConThreeSql(mSSRS.GetText(i,1));
	                		   
	        	        }
	                	if(((mType1.equals("1"))||(mType1.equals("3")))&&((mType3.equals("2"))||(mType3.equals("3"))) )  //孤儿单
	                	{
	  	                  //3--孤儿单需要续保的主险清单
//	  	                  if(!DealReMainPol(tAgentCode))
//	  	                   {
//	  	                      return false;
//	  	                   }
	                		tSSRS=getDealReMainPolSql(mSSRS.GetText(i,1));

//	  	                   //4--孤儿单需要续保的附加险清单
//	  	                  if(!DealReSubPol(tAgentCode))
//	  	                   {
//	  	                      return false;
//	  	                   }
//	                		hSSRS=getDealReSubPolSql(mSSRS.GetText(i,1));
	                	}
	                	
	                	if(((mType1.equals("2"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("1"))||(mType2.equals("3"))) )  //孤儿单
	                	{
//	                		 if(!DealAdimConTwo(tAgentCode))
	                		tSSRS=getDealAdimConTwoSql(mSSRS.GetText(i,1));
	                	}
	                	if(((mType1.equals("2"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("2"))||(mType2.equals("3"))) )  //孤儿单
	                	{
//	                		  if(!DealAdimConThree(tAgentCode))
	                		tSSRS=getDealAdimConThreeSql(mSSRS.GetText(i,1));
	                	}
	                	if(((mType1.equals("2"))||(mType1.equals("3")))&&((mType3.equals("2"))||(mType3.equals("3"))) )  //孤儿单
	                	{
//	                		if(!DealAdimMainPol(tAgentCode))
//		                     {
//		                         return false;
//		                     }
	                		tSSRS=getDealAdimMainPolSql(mSSRS.GetText(i,1));
		                    //8--在职单需要续保的附加险
//		                   if(!DealAdimSubPol(tAgentCode))
//		                     {
//		                         return false;
//		                     }
//	                		hSSRS=getDealAdimSubPolSql(mSSRS.GetText(i,1));

	                	}
	                	xLength=tSSRS.getMaxRow();
	                	tVData.clear();
	  				  if (tSSRS == null ||tSSRS.MaxRow <= 0) {}
					  else{
	                  for(int x=1;x<=tSSRS.getMaxRow();x++)
	                  {
	                	  LCPolSchema tLCPolSchema = new LCPolSchema();
	                	  LCPolDB tLCPolDB=new LCPolDB();
	              		  tLCPolDB = new LCPolDB();
	              		  tLCPolDB.setPolNo(tSSRS.GetText(i, 1));
	              		  tLCPolDB.getInfo();
	              		  tLCPolSchema.setSchema(tLCPolDB.getSchema());
	                	  mVData.clear();
	                	  Map map = new HashMap();
	    				  map.put("LCPolSchema", tLCPolSchema);
	    				  map.put("TransferData", tTransferData);
	    				  map.put("GlobalInput", tG);
	    				  map.put("tSSRS",tSSRS);
	    				  map.put("m_book",m_book);
	    				  tVData.add(map);
	                	  if(x%tConutMax==0)
	                	  {
	                		  mTaskWaitList.add(tVData);
	                		  tVData = new VData();
	                	  }
						
	                	  if(x%tConutMax!=0&&x==tSSRS.getMaxRow())
	                	  {
	                		  mTaskWaitList.add(tVData);
	                		  tVData = new VData();
	                	  } 
	                  }
	        	    }
	        	   }
				  }
	      		  
	     	String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
					+ (String) this.mParameters.get("TaskCode") + ":"
					+ (String) this.mParameters.get("TaskPlanCode");
			// tongmeng 2009-05-21 add
			// 自适应线程数
			int tThreadCount = 5;
			tExeSQL = new ExeSQL();
			String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='XQDutyReportAutoThreadCount'";
			SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
			sqlbv10.sql(tSQL_ThreadCount);
			String tSignMThreadCount = tExeSQL.getOneValue(sqlbv10);
			if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
					&& Integer.parseInt(tSignMThreadCount) > 0) {
				tThreadCount = Integer.parseInt(tSignMThreadCount);
			}
			if (xLength < tThreadCount) {
				tThreadCount =xLength;
			}
			
			this.mServiceA = new ServiceA(
					"com.sinosoft.lis.reagent.LRPolListCDownBLTMThread", mTaskWaitList,
					tThreadCount, 10, TaskCode);
			this.mServiceA.start();
			
		    m_book.setEntry(mCurrentRow,  0, "制表人:"+tG.Operator); //制表人
		    m_book.setEntry(mCurrentRow,  1, "制表日期:"+currentdate); //制表时间
		      //mFilePath = "D:\\TEMP\\";//本地测试用
		    logger.debug("@@"+mFilePath +"##"+mFileName);
		      //生成文件
		    m_book.write(mFilePath + mFileName,new com.f1j.ss.WriteParams(com.f1j.ss.BookModelImpl.eFileExcel97));
	       
	      }catch (Exception e) {
	      	   e.printStackTrace();
	      	   CError.buildErr(this, e.toString());
	      	   return false;
	      } finally {
	      	mPubLock.unLock();// 解锁
	      }
        }
		 }
       }
        //完成时间
    	  dend = new Date();         	
    	  endTime = dend.getTime(); 
    	 tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	
    	PubFun.logResult(tG,tG.LogID[1], "续期应收清单自动生成批处理下月报表  当月报表"+String.valueOf(xLength)+"笔,共花费"+tt+"秒");
        
        //然后是下月报表  每月21号、28号一次	
         initTime =dNow.getTime();   
        int Next_count =0;
        String Next_check=" select (select count(*) from dual where substr(now(),9,2)=21) "
            + "+ (select count(*) from dual where substr(now(),9,2)=28)  from dual ";
        SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
        sqlbv11.sql(Next_check);
        Next_count = Integer.parseInt(tExeSQL.getOneValue(sqlbv11));
        xLength = 0;
        if(Next_count>0)
        {  //符合条件的话需要生成相应的下月报表
      	  logger.debug("开始下月应收报表自动生成！");
      	  //获取起止期
      	  date_sql=" select startdate,enddate  from lastatsegment where stattype='1' and yearmonth=to_char(add_months(now(),1),'yyyymm') " ;
	      SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
	      sqlbv12.sql(date_sql);
      	  SSRS date_ssrs = new SSRS();
	          date_ssrs = tExeSQL.execSQL(sqlbv12);
	          StartDate = date_ssrs.GetText(1, 1);
	          EndDate = date_ssrs.GetText(1, 2);
	          logger.debug("StartDate:"+StartDate );
	          logger.debug("EndDate:"+EndDate );
	       if (SSRS_com == null ||SSRS_com.MaxRow <= 0) {}
		   else{
      	  for(int i=1;i<=SSRS_com.getMaxRow();i++)
	          {
      		 try {
           	 	if (!checkDesc(mDelFlag))
           	 	{
                  return false;
                }
                  m_book.read(mModePath + this.mFileModeDesc,new com.f1j.ss.ReadParams());
                  m_book.setSheetSelected(0, true);
                  m_book.setCol(0);
            	  mCurrentRow = 1;
                  mBranchType = "4";   //4：续收外勤；99：收展
   		  	  	  
	              String tManageCom = SSRS_com.GetText(i, 1);
	              TransferData tTransferData=new TransferData();
	          
	              tTransferData.setNameAndValue("ManageCom",tManageCom);
	              tTransferData.setNameAndValue("AgentCode","");
	              tTransferData.setNameAndValue("StartDate",StartDate); 
	              tTransferData.setNameAndValue("EndDate",EndDate);
	              tTransferData.setNameAndValue("Type1","3");
	              tTransferData.setNameAndValue("Type2","3");
	              tTransferData.setNameAndValue("Type3","3");
	              tTransferData.setNameAndValue("Type4","1");
	              tTransferData.setNameAndValue("BranchType","4");
	              tTransferData.setNameAndValue("Operator",tG.Operator);
	              tTransferData.setNameAndValue("XSExcTemplate",mModePath);
	              
	              String mManageCom = tManageCom;
	        	  String mAgentCode = "";
	        	  String mStartDate = StartDate;
	        	  String mEndDate = EndDate;
	        	  String mType1 = "3";   //1：孤儿单；2：在职单；3：不区分
	        	  String mType2 = "3";   //1：二次；2：三次；3：不区分
	        	  String mType3 = "3";   //1：续期件；2：续保件；3：不区分
	        	  String mType4 = "1";   //1：年交；0：非年交
	        	  ExeSQL mExeSQL = new ExeSQL();
	        	  String currentdate = PubFun.getCurrentDate();
	        	  String currenttime = PubFun.getCurrentTime();
	              
	        	  payintv_sql="";
	              if(mType4.equals("1"))
	              {
	            	  payintv_sql=" and a.payintv=12 ";
	              }
	              else
	              {
	            	  payintv_sql=" and a.payintv in (1,3,6) ";
	              }
	              logger.debug("交费类型语句："+payintv_sql);
	              
	              VData tVData = new VData();
	              tVData.addElement(tTransferData);
	              tVData.addElement(tG);
	              if(getSql(tVData))
	              {}
	              
	              SSRS hSSRS=new SSRS();
	      			SSRS tSSRS=new SSRS();
					// 循环对每个保单情况进行区分，并进行抽档操作，每个保单分别提交
					VData mVData = new VData();
					if (mSSRS == null ||mSSRS.MaxRow <= 0) {}
					  else{
	                for(int j=1; j <= mSSRS.getMaxRow(); j++)
	        	    {
	                	if(((mType1.equals("1"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("1"))||(mType2.equals("3"))) )  //孤儿单
	        	        {
//	                		   if(!DealReConTwo(tAgentCode))
//	                           {
//	                               return false;
//	                           }
	                		 tSSRS=getDealReConSql(mSSRS.GetText(i,1));
	                		 
	        	        }
	                   	if(((mType1.equals("1"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("2"))||(mType2.equals("3"))) )  //孤儿单
	        	        {
//	                		   if(!DealReConTwo(tAgentCode))
//	                           {
//	                               return false;
//	                           }
	                		 tSSRS=getDealReConThreeSql(mSSRS.GetText(i,1));
	                		   
	        	        }
	                	if(((mType1.equals("1"))||(mType1.equals("3")))&&((mType3.equals("2"))||(mType3.equals("3"))) )  //孤儿单
	                	{
	  	                  //3--孤儿单需要续保的主险清单
//	  	                  if(!DealReMainPol(tAgentCode))
//	  	                   {
//	  	                      return false;
//	  	                   }
	                		tSSRS=getDealReMainPolSql(mSSRS.GetText(i,1));

//	  	                   //4--孤儿单需要续保的附加险清单
//	  	                  if(!DealReSubPol(tAgentCode))
//	  	                   {
//	  	                      return false;
//	  	                   }
//	                		hSSRS=getDealReSubPolSql(mSSRS.GetText(i,1));
	                	}
	                	
	                	if(((mType1.equals("2"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("1"))||(mType2.equals("3"))) )  //孤儿单
	                	{
//	                		 if(!DealAdimConTwo(tAgentCode))
	                		tSSRS=getDealAdimConTwoSql(mSSRS.GetText(i,1));
	                	}
	                	if(((mType1.equals("2"))||(mType1.equals("3")))&&((mType3.equals("1"))||(mType3.equals("3")))&&((mType2.equals("2"))||(mType2.equals("3"))) )  //孤儿单
	                	{
//	                		  if(!DealAdimConThree(tAgentCode))
	                		tSSRS=getDealAdimConThreeSql(mSSRS.GetText(i,1));
	                	}
	                	if(((mType1.equals("2"))||(mType1.equals("3")))&&((mType3.equals("2"))||(mType3.equals("3"))) )  //孤儿单
	                	{
//	                		if(!DealAdimMainPol(tAgentCode))
//		                     {
//		                         return false;
//		                     }
	                		tSSRS=getDealAdimMainPolSql(mSSRS.GetText(i,1));
		                    //8--在职单需要续保的附加险
//		                   if(!DealAdimSubPol(tAgentCode))
//		                     {
//		                         return false;
//		                     }
//	                		hSSRS=getDealAdimSubPolSql(mSSRS.GetText(i,1));

	                	}
	                	xLength=tSSRS.getMaxRow();
	                	tVData.clear();
					 if (tSSRS == null ||tSSRS.MaxRow <= 0) {}
					  else{
	                  for(int x=1;x<=tSSRS.getMaxRow();x++)
	                  {
	                	  LCPolSchema tLCPolSchema = new LCPolSchema();
	                	  LCPolDB tLCPolDB=new LCPolDB();
	              		  tLCPolDB = new LCPolDB();
	              		  tLCPolDB.setPolNo(tSSRS.GetText(i, 1));
	              		  tLCPolDB.getInfo();
	              		  tLCPolSchema.setSchema(tLCPolDB.getSchema());

	                	  Map map = new HashMap();
	    				  map.put("LCPolSchema", tLCPolSchema);
	    				  map.put("TransferData", tTransferData);
	    				  map.put("GlobalInput", tG);
	    				  map.put("tSSRS",tSSRS);
	    				  map.put("m_book",m_book);
	    				  tVData.add(map);
	                	  if(x%tConutMax==0)
	                	  {
	                		  mTaskWaitList.add(tVData);
	                		  tVData = new VData();
	                	  }
						
	                	  if(x%tConutMax!=0&&x==tSSRS.getMaxRow())
	                	  {
	                		  mTaskWaitList.add(tVData);
	                		  tVData = new VData();
	                	  } 
	                  }
					  }
	        	    }
				  }
	      		  
	     	String TaskCode = (String) this.mParameters.get("TaskGroupCode") + ":"
					+ (String) this.mParameters.get("TaskCode") + ":"
					+ (String) this.mParameters.get("TaskPlanCode");
			// tongmeng 2009-05-21 add
			// 自适应线程数
			int tThreadCount = 5;
			tExeSQL = new ExeSQL();
			String tSQL_ThreadCount = "select (case when sysvarvalue is not null then sysvarvalue else '0' end) from ldsysvar where sysvar ='XQDutyReportAutoThreadCount'";
			SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
			sqlbv13.sql(tSQL_ThreadCount);
			String tSignMThreadCount = tExeSQL.getOneValue(sqlbv13);
			if (tSignMThreadCount != null && !"".equals(tSignMThreadCount)
					&& Integer.parseInt(tSignMThreadCount) > 0) {
				tThreadCount = Integer.parseInt(tSignMThreadCount);
			}
			if (xLength < tThreadCount) {
				tThreadCount =xLength;
			}
			
			this.mServiceA = new ServiceA(
					"com.sinosoft.lis.reagent.LRPolListCDownBLTMThread", mTaskWaitList,
					tThreadCount, 10, TaskCode);
			this.mServiceA.start();
			
		    m_book.setEntry(mCurrentRow,  0, "制表人:"+tG.Operator); //制表人
		    m_book.setEntry(mCurrentRow,  1, "制表日期:"+currentdate); //制表时间
		      //mFilePath = "D:\\TEMP\\";//本地测试用
		    logger.debug("@@"+mFilePath +"##"+mFileName);
		      //生成文件
		    m_book.write(mFilePath + mFileName,new com.f1j.ss.WriteParams(com.f1j.ss.BookModelImpl.eFileExcel97));
	       
	      }catch (Exception e) {
	      	   e.printStackTrace();
	      	   CError.buildErr(this, e.toString());
	      	   return false;
	      } finally {
	      	mPubLock.unLock();// 解锁
	      }
        }
	   }
       }
        logger.debug("end XQDutyReportAuto!!");
        
        //完成时间
    	dend = new Date();         	
    	endTime = dend.getTime(); 
    	tt=(endTime-initTime)/1000; 
    	//日志监控,结果监控 
    	
    	PubFun.logResult(tG,tG.LogID[1], "续期应收清单自动生成批处理下月报表  每月21号、28号一次"+String.valueOf(xLength)+"笔,共花费"+tt+"秒");
    	PubFun.logResult(tG,tG.LogID[1], "续期应收清单自动生成批处理完毕");
		return true;
	}
	
	
	private SSRS getDealAdimMainPolSql(String tAgentCode)
	{
		 logger.debug("在职单需要续保的主险清单处理");
		    //首先取出续收外勤自己收展的保单
		    String sqlhead1 = "select z.polno no,z.curpaytodate,'9999' xdate from lacommision z where agentcode='?tAgentCode?'"
		     +  " and z.mainpolno = z.polno and z.payintv=0 and z.payyear = 0 and z.commdire = '1' and z.transmoney > 0";

		    //然后是续收外勤名下的在职单
		    String sqlhead2 = "select z.polno no,z.paytodate,'9999' xdate from LRAdimAscription z where z.polno=z.mainpolno "
		     +" and z.agentcode='?tAgentCode?' and z.ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01') ";

		    String sqlStr = " and exists (select 1 from lcprem a,LCPol b ";
		    sqlStr = sqlStr +  " where a.polno=b.polno and a.PaytoDate>='?mStartDate?' and a.PaytoDate<='?mEndDate?' and a.PaytoDate=a.PayEndDate   ";
		    sqlStr = sqlStr + " and b.AppFlag='1' and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) and char_length(trim(a.dutycode))=6  and char_length(trim(a.payplancode))=6 ";
		    sqlStr = sqlStr + " and b.PolNo=b.MainPolNo  ";
		    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1'  and a.contno=z.contno and b.contno=z.contno ";
		    sqlStr = sqlStr + " and b.RnewFlag=-1  and exists (select 1 from lmrisk where riskcode=b.riskcode and rnewflag='Y')) "
		    + " and not exists(select 1 from lrascription where contno=z.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01') "
			     + " union select 1 from lrascriptionb where contno=z.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01')) ";

		    if(mManageCom != null &&(!mManageCom.equals("")))
		    {
		      sqlStr = sqlStr + " and z.ManageCom like concat('?mManageCom?','%')";
		    }

		    String SQL_Count = sqlhead1 + sqlStr+" union "+sqlhead2 + sqlStr;
		    logger.debug("查询在职单需要续保的主险清单:" + SQL_Count);

		    String SQL_PolNo = "select q.no from ("+SQL_Count
				     + " ) q order by q.xdate ";

		    
		    logger.debug("在职单需要续保的附加险清单处理");
		    //首先取出续收外勤自己收展的保单
		    String sqlhead = "select z.polno no,z.curpaytodate xdate,'5555' from lacommision z where agentcode='?tAgentCode?'"
		     +  " and z.mainpolno = z.polno and z.payintv=0 and z.payyear = 0 and z.commdire = '1' and z.transmoney > 0";

		    //然后是续收外勤名下的在职单
		    String sqlhead3 = "select z.polno no,z.paytodate xdate,'5555' from LRAdimAscription z where z.polno=z.mainpolno "
		     +" and z.agentcode='?tAgentCode?' and z.ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01') ";
		    //确定查询逻辑
		    String sqlStr3 = " and exists (select 1 from lcprem a,LCPol b ";
		    sqlStr3 =sqlStr3 +  " where a.polno=b.polno and  a.PaytoDate>='?mStartDate?' and a.PaytoDate<='?mEndDate?' and a.PaytoDate=a.PayEndDate ";
		    sqlStr3 = sqlStr3 + " and b.AppFlag='1' and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) and char_length(trim(a.dutycode))=6  and char_length(trim(a.payplancode))=6  "
			   +" and not exists(select 1 from lcrnewstatelog where polno=b.mainpolno union select 1 from lcpol x,lcrnewstatehistory y where x.contno=b.contno and x.polno=x.mainpolno and x.contno=y.contno and x.riskcode=y.riskcode) "//主险未做过续保
			   +" and exists (select 1 from lcpol c where polno=b.mainpolno and payintv=0 and not exists(select 1 from lmrisk where riskcode=c.riskcode and rnewflag='Y')) ";//主险趸交非续保险种
		    sqlStr3 = sqlStr3 + " and b.PolNo!=b.MainPolNo and exists (select 1 from lmrisk where riskcode=b.riskcode and rnewflag='Y') ";
		    sqlStr3 = sqlStr3 + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1' and a.contno=z.contno and b.contno=z.contno ";
		    sqlStr3 = sqlStr3 + " and RnewFlag=-1 ) "
		           + " and not exists(select 1 from lrascription where contno=z.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01') "
			            + " union select 1 from lrascriptionb where contno=z.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01')) ";

		    if(mManageCom != null &&(!mManageCom.equals("")))
		    {
		    	sqlStr3 = sqlStr3 + " and z.ManageCom like concat('?mManageCom?','%')";
		    }

		    String SQL_Count1 = sqlhead + sqlStr3+" union "+sqlhead3 + sqlStr3;
		    logger.debug("查询在职单需要续保的附加险清单:" + SQL_Count);

		    String SQL_PolNo1 = "select q.no from ("+SQL_Count1
				     + " ) q order by q.xdate ";
		    
		    String SQL_PolNo3 ="select no from ("+SQL_PolNo1+") j union select no from("+SQL_PolNo+") k";
		    SQLwithBindVariables sqlbv14=new SQLwithBindVariables();
		    sqlbv14.sql(SQL_PolNo3);
		    sqlbv14.put("tAgentCode", tAgentCode);
		    sqlbv14.put("mStartDate", mStartDate);
		    sqlbv14.put("mEndDate", mEndDate);
		    sqlbv14.put("mStartDate1", this.mStartDate);
		    sqlbv14.put("mManageCom", mManageCom);
		  
		    logger.debug("@@@@SQL_PolNo:"+SQL_PolNo3);
		    SSRS tSSRS = mExeSQL.execSQL(sqlbv14);

		    if (tSSRS == null ||tSSRS.getMaxRow()==0)
		    {
		        logger.debug(tAgentCode+"在职单续期二次主险无数据，跳过");
		        return null;
		    }
		    if (tSSRS.getMaxRow() > 0)
		    {
//		      addPrompt("***以下是在职单主险自动续保数据***");
		    }
		    return tSSRS;
	}
	
	private SSRS getDealAdimSubPolSql(String tAgentCode)
	{
		logger.debug("在职单需要续保的附加险清单处理");
	    //首先取出续收外勤自己收展的保单
	    String sqlhead1 = "select z.polno no,z.curpaytodate xdate from lacommision z where agentcode='?tAgentCode?'"
	     +  " and z.mainpolno = z.polno and z.payintv=0 and z.payyear = 0 and z.commdire = '1' and z.transmoney > 0";

	    //然后是续收外勤名下的在职单
	    String sqlhead2 = "select z.polno no,z.paytodate xdate from LRAdimAscription z where z.polno=z.mainpolno "
	     +" and z.agentcode='?tAgentCode?' and z.ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01') ";
	    //确定查询逻辑
	    String sqlStr = " and exists (select 1 from lcprem a,LCPol b ";
	    sqlStr =sqlStr +  " where a.polno=b.polno and  a.PaytoDate>='?mStartDate?' and a.PaytoDate<='?mEndDate?' and a.PaytoDate=a.PayEndDate ";
	    sqlStr = sqlStr + " and b.AppFlag='1' and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) and char_length(trim(a.dutycode))=6  and char_length(trim(a.payplancode))=6  "
		   +" and not exists(select 1 from lcrnewstatelog where polno=b.mainpolno union select 1 from lcpol x,lcrnewstatehistory y where x.contno=b.contno and x.polno=x.mainpolno and x.contno=y.contno and x.riskcode=y.riskcode) "//主险未做过续保
		   +" and exists (select 1 from lcpol c where polno=b.mainpolno and payintv=0 and not exists(select 1 from lmrisk where riskcode=c.riskcode and rnewflag='Y')) ";//主险趸交非续保险种
	    sqlStr = sqlStr + " and b.PolNo!=b.MainPolNo and exists (select 1 from lmrisk where riskcode=b.riskcode and rnewflag='Y') ";
	    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1' and a.contno=z.contno and b.contno=z.contno ";
	    sqlStr = sqlStr + " and RnewFlag=-1 ) "
	           + " and not exists(select 1 from lrascription where contno=z.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01') "
		            + " union select 1 from lrascriptionb where contno=z.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01')) ";

	    if(mManageCom != null &&(!mManageCom.equals("")))
	    {
	      sqlStr = sqlStr + " and z.ManageCom like concat('?mManageCom?','%')";
	    }

	    String SQL_Count = sqlhead1 + sqlStr+" union "+sqlhead2 + sqlStr;
	    logger.debug("查询在职单需要续保的附加险清单:" + SQL_Count);

	    String SQL_PolNo = "select q.no from ("+SQL_Count
			     + " ) q order by q.xdate ";

	    logger.debug("@@@SQL_PolNo:"+SQL_PolNo);
	    SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
	    sqlbv15.sql(SQL_PolNo);
	    sqlbv15.put("tAgentCode", tAgentCode);
	    sqlbv15.put("mStartDate", mStartDate);
	    sqlbv15.put("mEndDate", mEndDate);
	    sqlbv15.put("mStartDate1", this.mStartDate);
	    sqlbv15.put("mManageCom", mManageCom);
	    SSRS tSSRS = mExeSQL.execSQL(sqlbv15);

	    if (tSSRS == null ||tSSRS.getMaxRow()==0)
	    {
	        logger.debug(tAgentCode+"在职单需要续保的附加险无数据，跳过");
	        return null;
	    }
	    if (tSSRS.getMaxRow() > 0)
	    {
//	        addPrompt("***以下是在职单附加险自动续保数据(其主险不需要续期缴费和续保)***");
	    }
	    return tSSRS;
	}
	
	private SSRS getDealAdimConThreeSql(String tAgentCode)
	{

	    logger.debug("在职单续期三次清单处理！");
	    //首先取出续收外勤自己收展的保单
	    String sqlhead1 = "select z.polno no,z.curpaytodate xdate from lacommision z where agentcode='?tAgentCode?'"
	     +  " and z.mainpolno = z.polno and z.payintv=12 and z.payyear = 0 and z.commdire = '1' and z.transmoney > 0";

	    //然后是续收外勤名下的在职单
	    String sqlhead2 = "select z.polno no,z.paytodate xdate from LRAdimAscription z where z.polno=z.mainpolno "
	     +" and z.agentcode='?tAgentCode?' and z.ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01') ";

	    //确定查询逻辑
	    String sqlStr = " and exists(select 1 from lcprem a,LCPol b where a.polno=b.polno and  a.PaytoDate>='?mStartDate?' and a.PaytoDate<='?mEndDate?' and a.PaytoDate<a.PayEndDate  ";
	    sqlStr = sqlStr + payintv_sql + "  and a.paytimes >=2 and b.appflag='1'  and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) ";
	    sqlStr = sqlStr + " and b.PolNo=b.MainPolNo and char_length(trim(a.dutycode))=6 and char_length(trim(a.payplancode))=6 ";
	    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1' and a.contno=z.contno and b.contno=z.contno )";
	   // sqlStr = sqlStr + " and SUBSTR (b.polstate, 0, 2)  IN ('01', '00')"; //只保留有效件
	    sqlStr = sqlStr +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
	    + " and substr(y.ReasonCode, 2) = '02' and x.contno =z.contno  "
	    + " union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
	    + " and substr(y.ReasonCode, 2) = '02' and x.contno =z.contno "
	    +"  union select 1 from LLReportReason t,lcpol o  where (t.customerno =o.appntno or t.customerno=o.insuredno)"
	    +"   and o.contno=z.contno and o.polno=o.mainpolno and substr(t.ReasonCode, 2) = '02') ";//排除死亡报案
	    sqlStr = sqlStr + " and not exists(select 1 from lrascription where contno=z.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01') "
		   + " union select 1 from lrascriptionb where contno=z.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01')) ";

	    if(mManageCom != null &&(!mManageCom.equals("")))
	    {
	      sqlStr = sqlStr + " and z.ManageCom like concat('?mManageCom?','%')";
	    }

	    String SQL_Count = sqlhead1 + sqlStr+" union "+sqlhead2 + sqlStr;
	    logger.debug("查询在职单续期三次主险:" + SQL_Count);

	    String SQL_PolNo = "select q.no from ("+SQL_Count
			     + " ) q order by q.xdate ";
	    logger.debug("SQL_PolNO: "+SQL_PolNo);
	    SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
	    sqlbv16.sql(SQL_PolNo);
	    sqlbv16.put("tAgentCode", tAgentCode);
	    sqlbv16.put("mStartDate", mStartDate);
	    sqlbv16.put("mEndDate", mEndDate);
	    sqlbv16.put("mStartDate1", this.mStartDate);
	    sqlbv16.put("mManageCom", mManageCom);
	    SSRS tSSRS = mExeSQL.execSQL(sqlbv16);

	    if (tSSRS == null ||tSSRS.getMaxRow()==0)
	    {
	        logger.debug(tAgentCode+"在职单续期三次主险无数据，跳过");
	        return null;
	    }
	    if (tSSRS.getMaxRow() > 0)
	    {
//	      addPrompt("***以下是在职单主险续期三次数据***");
	    }

	    LCPolDB tLCPolDB = new LCPolDB();
	    LCPolSchema tLCPolSchema = new LCPolSchema();
	    mExeSQL = new ExeSQL();

	    logger.debug("查询保单纪录数tSSRS.getMaxRow()："+tSSRS.getMaxRow());
	    return tSSRS;
	}
	private SSRS getDealAdimConTwoSql(String tAgentCode)
	{
		logger.debug("在职单续期二次清单处理！");
	    //首先取出续收外勤自己收展的保单
	    String sqlhead1 = "select z.polno no,z.curpaytodate xdate from lacommision z where agentcode='?tAgentCode?'"
	     +  " and z.mainpolno = z.polno and z.payintv=12 and z.payyear = 0 and z.commdire = '1' and z.transmoney > 0";

	    //然后是续收外勤名下的在职单
	    String sqlhead2 = "select z.polno no,z.paytodate xdate from LRAdimAscription z where z.polno=z.mainpolno "
	     +" and z.agentcode='?tAgentCode?' and z.ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01') ";

	    //确定查询逻辑
	    String sqlStr = " and exists(select 1 from lcprem a,LCPol b where a.polno=b.polno and  a.PaytoDate>='?mStartDate?' and a.PaytoDate<='?mEndDate?' and a.PaytoDate<a.PayEndDate  ";
	    sqlStr = sqlStr + payintv_sql + "  and a.paytimes = 1 and b.appflag='1'  and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) ";
	    sqlStr = sqlStr + " and b.PolNo=b.MainPolNo and char_length(trim(a.dutycode))=6 and char_length(trim(a.payplancode))=6 ";
	    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1' and a.contno=z.contno and b.contno=z.contno )";
	   // sqlStr = sqlStr + " and SUBSTR (b.polstate, 0, 2)  IN ('01', '00')"; //只保留有效件
	    sqlStr = sqlStr +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
	    + " and substr(y.ReasonCode, 2) = '02' and x.contno =z.contno  "
	    + " union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
	    + " and substr(y.ReasonCode, 2) = '02' and x.contno =z.contno "
	    +"  union select 1 from LLReportReason t,lcpol o  where (t.customerno =o.appntno or t.customerno=o.insuredno)"
	    +"   and o.contno=z.contno and o.polno=o.mainpolno and substr(t.ReasonCode, 2) = '02') ";//排除死亡报案
	    sqlStr = sqlStr + " and not exists(select 1 from lrascription where contno=z.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01') "
		   + " union select 1 from lrascriptionb where contno=z.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01')) ";

	    if(mManageCom != null &&(!mManageCom.equals("")))
	    {
	      sqlStr = sqlStr + " and z.ManageCom like concat('?mManageCom?','%')";
	    }

	    String SQL_Count = sqlhead1 + sqlStr+" union "+sqlhead2 + sqlStr;
	    logger.debug("查询在职单续期二次主险:" + SQL_Count);

	    String SQL_PolNo = "select q.no from ("+SQL_Count
			     + " ) q order by q.xdate ";
	    logger.debug("SQL_PolNO: "+SQL_PolNo);
	    SQLwithBindVariables sqlbv17=new SQLwithBindVariables();
	    sqlbv17.sql(SQL_PolNo);
	    sqlbv17.put("tAgentCode", tAgentCode);
	    sqlbv17.put("mStartDate", mStartDate);
	    sqlbv17.put("mEndDate", mEndDate);
	    sqlbv17.put("mStartDate1", this.mStartDate);
	    sqlbv17.put("mManageCom", mManageCom);
	    SSRS tSSRS = mExeSQL.execSQL(sqlbv17);

	    if (tSSRS == null ||tSSRS.getMaxRow()==0)
	    {
	        logger.debug(tAgentCode+"在职单续期二次主险无数据，跳过");
	        return null;
	    }
	    if (tSSRS.getMaxRow() > 0)
	    {
//	      addPrompt("***以下是在职单主险续期二次数据***");
	    }

	    LCPolDB tLCPolDB = new LCPolDB();
	    LCPolSchema tLCPolSchema = new LCPolSchema();
	    mExeSQL = new ExeSQL();

	    logger.debug("查询保单纪录数tSSRS.getMaxRow()："+tSSRS.getMaxRow());
	    return tSSRS;
	}
	
	private SSRS getDealReSubPolSql(String tAgentCode)
	{
		logger.debug("孤儿单需要续保的附加险清单处理");
	    String sqlhead = "select b.polno from lcprem a,LCPol b ";
	    String sqlStr = " where a.polno=b.polno and  a.PaytoDate>='?mStartDate?' and a.PaytoDate<='?mEndDate?' and a.PaytoDate=a.PayEndDate and b.agentcode = '?tAgentCode?'";
	    sqlStr = sqlStr + " and b.AppFlag='1' and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) "
	                     +" and char_length(trim(a.dutycode))=6  and char_length(trim(a.payplancode))=6  "
		   +" and not exists(select 1 from lcrnewstatelog where polno=b.mainpolno union select 1 from lcpol x,lcrnewstatehistory y where x.contno=b.contno and x.polno=x.mainpolno and x.contno=y.contno and x.riskcode=y.riskcode ) "//主险未做过续保
		   +" and exists (select 1 from lcpol c where polno=b.mainpolno and payintv=0 and not exists(select 1 from lmrisk where riskcode=c.riskcode and rnewflag='Y')) ";//主险趸交非续保险种
	    sqlStr = sqlStr + " and b.PolNo!=b.MainPolNo and exists (select 1 from lmrisk where riskcode=b.riskcode and rnewflag='Y') ";
	    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1'";
	    sqlStr = sqlStr + " and RnewFlag=-1 ";
	    sqlStr = sqlStr + " and exists(select '1' from lrascription where contno=b.contno union select '1' from lrascriptionb where contno=b.contno )";
	    if(mManageCom != null &&(!mManageCom.equals("")))
	    {
	      sqlStr = sqlStr + " and b.ManageCom like concat('?mManageCom?','%')";
	    }

	    String SQL_PolNo = sqlhead + sqlStr+ " order by b.paytodate ,b.AgentGroup";
	    logger.debug("查询孤儿单附加险自动续保:" + SQL_PolNo);
	    mExeSQL = new ExeSQL();
	    SQLwithBindVariables sqlbv18=new SQLwithBindVariables();
	    sqlbv18.sql(SQL_PolNo);
	    sqlbv18.put("tAgentCode", tAgentCode);
	    sqlbv18.put("mStartDate", mStartDate);
	    sqlbv18.put("mEndDate", mEndDate);
	    sqlbv18.put("mManageCom", mManageCom);
	    SSRS tSSRS = mExeSQL.execSQL(sqlbv18);
	    logger.debug("SQL_PolNO: "+SQL_PolNo);

	    LCPolDB tLCPolDB = new LCPolDB();
	    LCPolSchema tLCPolSchema = new LCPolSchema();
	    if (tSSRS == null ||tSSRS.getMaxRow()==0)
	    {
	        logger.debug(tAgentCode+"孤儿单附加险自动续保无数据，跳过");
	        return null;
	    }
	    if (tSSRS.getMaxRow() > 0)
	    {
//	      addPrompt("***以下是孤儿单附加险自动续保数据(其主险不需要续期缴费和续保)***");
	    }
	    return tSSRS;
	}
	
	private SSRS getDealReMainPolSql(String tAgentCode)
	{
		logger.debug("孤儿单需要续保的主险清单处理");
	    String sqlhead = "select b.polno from lcprem a,LCPol b ";
	    String sqlStr = " where a.polno=b.polno and a.PaytoDate>='?mStartDate?' and a.PaytoDate<='?mEndDate?' and a.PaytoDate=a.PayEndDate   ";
	    sqlStr = sqlStr + " and b.AppFlag='1' and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) "
	                     +" and char_length(trim(a.dutycode))=6  and char_length(trim(a.payplancode))=6 ";
	    sqlStr = sqlStr + " and b.PolNo=b.MainPolNo and b.agentcode='?tAgentCode?'   ";
	    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1'";
	    sqlStr = sqlStr + " and b.RnewFlag=-1  and exists (select 1 from lmrisk where riskcode=b.riskcode and rnewflag='Y') ";
	    sqlStr = sqlStr + " and exists(select '1' from lrascription where contno=b.contno union select '1' from lrascriptionb where contno=b.contno )";
	    if(mManageCom != null &&(!mManageCom.equals("")))
	    {
	      sqlStr = sqlStr + " and b.ManageCom like concat('?mManageCom?','%')";
	    }
	    String SQL_PolNo = sqlhead + sqlStr+ " order by b.paytodate ,b.AgentGroup";
	    

	    logger.debug("在职单需要续保的附加险清单处理");
	    //首先取出续收外勤自己收展的保单
	    String sqlhead1 = "select z.polno no,z.curpaytodate xdate from lacommision z where agentcode='?tAgentCode?'"
	     +  " and z.mainpolno = z.polno and z.payintv=0 and z.payyear = 0 and z.commdire = '1' and z.transmoney > 0";

	    //然后是续收外勤名下的在职单
	    String sqlhead2 = "select z.polno no,z.paytodate xdate from LRAdimAscription z where z.polno=z.mainpolno "
	     +" and z.agentcode='?tAgentCode?' and z.ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01') ";
	    //确定查询逻辑
	    String sqlStr1 = " and exists (select 1 from lcprem a,LCPol b ";
	    sqlStr1 =sqlStr1 +  " where a.polno=b.polno and  a.PaytoDate>='?mStartDate?' and a.PaytoDate<='?mEndDate?' and a.PaytoDate=a.PayEndDate ";
	    sqlStr1 = sqlStr1 + " and b.AppFlag='1' and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) and char_length(trim(a.dutycode))=6  and char_length(trim(a.payplancode))=6  "
		   +" and not exists(select 1 from lcrnewstatelog where polno=b.mainpolno union select 1 from lcpol x,lcrnewstatehistory y where x.contno=b.contno and x.polno=x.mainpolno and x.contno=y.contno and x.riskcode=y.riskcode) "//主险未做过续保
		   +" and exists (select 1 from lcpol c where polno=b.mainpolno and payintv=0 and not exists(select 1 from lmrisk where riskcode=c.riskcode and rnewflag='Y')) ";//主险趸交非续保险种
	    sqlStr1 = sqlStr1 + " and b.PolNo!=b.MainPolNo and exists (select 1 from lmrisk where riskcode=b.riskcode and rnewflag='Y') ";
	    sqlStr1 = sqlStr1 + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1' and a.contno=z.contno and b.contno=z.contno ";
	    sqlStr1 = sqlStr1 + " and RnewFlag=-1 ) "
	           + " and not exists(select 1 from lrascription where contno=z.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01') "
		            + " union select 1 from lrascriptionb where contno=z.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01')) ";

	    if(mManageCom != null &&(!mManageCom.equals("")))
	    {
	    	sqlStr1 = sqlStr1 + " and z.ManageCom like concat('?mManageCom?','%')";
	    }

	    String SQL_Count = sqlhead1 + sqlStr1+" union "+sqlhead2 + sqlStr1;
	    logger.debug("查询在职单需要续保的附加险清单:" + SQL_Count);

	    String SQL_PolNo1 = "select q.no from ("+SQL_Count
			     + " ) q order by q.xdate ";

	    String SQL_PolNo3="("+SQL_PolNo1+") union ("+SQL_PolNo+")";
	    
	    
	    logger.debug("查询孤儿单需要续保的主险:" + SQL_PolNo3);
	    mExeSQL = new ExeSQL();
	    SQLwithBindVariables sqlbv19=new SQLwithBindVariables();
	    sqlbv19.sql(SQL_PolNo3);
	    sqlbv19.put("tAgentCode", tAgentCode);
	    sqlbv19.put("mStartDate", mStartDate);
	    sqlbv19.put("mEndDate", mEndDate);
	    sqlbv19.put("mStartDate1", this.mStartDate);
	    sqlbv19.put("mManageCom", mManageCom);
	    SSRS tSSRS = mExeSQL.execSQL(sqlbv19);
	    logger.debug("SQL_PolNo3: "+SQL_PolNo3);

	    LCPolDB tLCPolDB = new LCPolDB();
	    LCPolSchema tLCPolSchema = new LCPolSchema();
	    if (tSSRS == null ||tSSRS.getMaxRow()==0)
	    {
	        logger.debug(tAgentCode+"孤儿单需要续保的主险无数据，跳过");
	        return null;
	    }
	    if (tSSRS.getMaxRow() > 0)
	    {
//	      addPrompt("***以下是孤儿单主险自动续保数据***");
	    }
	    return tSSRS;
	}
	private SSRS getDealReConThreeSql(String tAgentCode)
	{
		logger.debug("孤儿单续期三次清单处理！");
	    String sqlhead = "select b.polno from lcprem a,LCPol b ";
	    String sqlStr = " where a.polno=b.polno and  a.PaytoDate>='?mStartDate?' and a.PaytoDate<='?mEndDate?' and a.PaytoDate<a.PayEndDate  ";
	    sqlStr = sqlStr + payintv_sql + " and a.paytimes >= 2 and b.appflag='1' and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol)))  ";
	    sqlStr = sqlStr + " and b.PolNo=b.MainPolNo and char_length(trim(a.dutycode))=6 and char_length(trim(a.payplancode))=6 ";
	    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1' ";
	    //sqlStr = sqlStr + " and SUBSTR (b.polstate, 0, 2)  IN ('01', '00')"; //只保留有效件
	    sqlStr = sqlStr +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
	    + " and substr(y.ReasonCode, 2) = '02' and x.contno =b.contno  "
	    + " union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
	    + " and substr(y.ReasonCode, 2) = '02' and x.contno =b.contno "
	    + " union select 1 from LLReportReason t where (customerno =b.appntno or customerno=b.insuredno) "
		+ " and substr(ReasonCode, 2) = '02')" ;//排除当前状态下发生死亡报案
	    sqlStr = sqlStr + " and b.agentcode = '?tAgentCode?' and exists(select 1 from lrascription where contno=a.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01') "
		   + " union select 1 from lrascriptionb where contno=a.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01')) ";
	    if(mManageCom != null &&(!mManageCom.equals("")))
	    {
	      sqlStr = sqlStr + " and b.ManageCom like concat('?mManageCom?','%')";
	    }

	    String SQL_PolNo = sqlhead + sqlStr+ " order by b.paytodate ,b.AgentGroup";
	    logger.debug("查询孤儿单续期三次主险:" + SQL_PolNo);
	    mExeSQL = new ExeSQL();
	    SQLwithBindVariables sqlbv20=new SQLwithBindVariables();
	    sqlbv20.sql(SQL_PolNo);
	    sqlbv20.put("tAgentCode", tAgentCode);
	    sqlbv20.put("mStartDate", mStartDate);
	    sqlbv20.put("mEndDate", mEndDate);
	    sqlbv20.put("mStartDate1", this.mStartDate);
	    sqlbv20.put("mManageCom", mManageCom);
	    SSRS tSSRS = mExeSQL.execSQL(sqlbv20);
	    LCPolDB tLCPolDB = new LCPolDB();
	    LCPolSchema tLCPolSchema = new LCPolSchema();
	    if (tSSRS == null ||tSSRS.getMaxRow()==0)
	    {
	        logger.debug(tAgentCode+"孤儿单续期三次主险无数据，跳过");
	        return null;
	    }
	    if (tSSRS.getMaxRow() > 0)
	    {
//	      addPrompt("***以下是孤儿单主险续期三次数据***");
	    }
	    logger.debug("查询保单纪录数tSSRS.getMaxRow()："+tSSRS.getMaxRow());
	    return tSSRS;
	   
	}
	
	private SSRS getDealReConSql(String tAgentCode)
	{
		 logger.debug("孤儿单续期二次清单处理！");

		    String sqlhead = "select b.polno from lcprem a,LCPol b ";
		    String sqlStr = " where a.polno=b.polno and  a.PaytoDate>='?mStartDate?' and a.PaytoDate<='?mEndDate?' and a.PaytoDate<a.PayEndDate  ";
		    sqlStr = sqlStr + payintv_sql + "  and a.paytimes = 1 and b.appflag='1' and (b.salechnl in ('02','10') or ( b.salechnl in ('03','05','06','08','09') and b.polno in (select polno from LAAgentComOrphanPol))) ";
		    sqlStr = sqlStr + " and b.PolNo=b.MainPolNo and char_length(trim(a.dutycode))=6 and char_length(trim(a.payplancode))=6 ";
		    sqlStr = sqlStr + " and (a.FreeFlag != '1' or a.FreeFlag is null) and b.conttype='1' ";
		    //sqlStr = sqlStr + " and SUBSTR (b.polstate, 0, 2)  IN ('01', '00')"; //只保留有效件
		    sqlStr = sqlStr +" and not exists(select 1 from lcconthangupstate x, LLReportReason y where x.hangupno = y.RpNo  "
		        + " and substr(y.ReasonCode, 2) = '02' and x.contno =b.contno  "
		        + " union select 1 from llclaimpolicy x, LLAppClaimReason y where x.clmno = y.CaseNo  "
		        + " and substr(y.ReasonCode, 2) = '02' and x.contno =b.contno "
		        + " union select 1 from LLReportReason t where (customerno =b.appntno or customerno=b.insuredno) "
				+ " and substr(ReasonCode, 2) = '02')" ;//排除当前状态下发生死亡报案
		    sqlStr = sqlStr + " and b.agentcode = '?tAgentCode?' and exists(select 1 from lrascription where contno=a.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01') "
			   + " union select 1 from lrascriptionb where contno=a.contno and ascriptiondate=concat(substr(to_date('?mStartDate1?','yyyy-mm-dd'),1,7),'-01')) ";
		    if(mManageCom != null &&(!mManageCom.equals("")))
		    {
		      sqlStr = sqlStr + " and b.ManageCom like concat('?mManageCom?','%')";
		    }

		    String SQL_PolNo = sqlhead + sqlStr+ " order by b.paytodate ,b.AgentGroup";
		    logger.debug("查询孤儿单续期二次主险:" + SQL_PolNo);
		    mExeSQL = new ExeSQL();
		    SQLwithBindVariables sqlbv21=new SQLwithBindVariables();
		    sqlbv21.sql(SQL_PolNo);
		    sqlbv21.put("tAgentCode", tAgentCode);
		    sqlbv21.put("mStartDate", mStartDate);
		    sqlbv21.put("mEndDate", mEndDate);
		    sqlbv21.put("mStartDate1", this.mStartDate);
		    sqlbv21.put("mManageCom", mManageCom);
		    SSRS tSSRS = mExeSQL.execSQL(sqlbv21);
		    logger.debug("SQL_PolNO: "+SQL_PolNo);

		    LCPolDB tLCPolDB = new LCPolDB();
		    LCPolSchema tLCPolSchema = new LCPolSchema();
		    if (tSSRS == null ||tSSRS.getMaxRow()==0)
		    {
		        logger.debug(tAgentCode+"孤儿单续期二次主险无数据，跳过");
		        return null;
		    }
		    if (tSSRS.getMaxRow() > 0)
		    {
//		      addPrompt("***以下是孤儿单主险续期二次数据***");
		    }

		   logger.debug("查询保单纪录数tSSRS.getMaxRow()："+tSSRS.getMaxRow());
		   return tSSRS;
	}
	 /**
	   * 填充数据前的准备工作：获取文件生成路径和生成文件名
	   * @return
	   */
	  private boolean checkDesc(String mDelFlag)
	  {
	    LDSysVarDB tLDSysVarDB = new LDSysVarDB();

	    //取清单模版文件存放路径（即要生成文件的存放路径）
	    tLDSysVarDB = new LDSysVarDB();
	    tLDSysVarDB.setSysVar(mFilePathDesc);
	    if (tLDSysVarDB.getInfo() == false)
	    {
	      buildError("checkDesc", "LDSysVar取文件路径(" + mFilePathDesc + ")描述失败");
	      return false;
	    }
	    mFilePath = tLDSysVarDB.getSysVarValue();
	    //mFilePath = "D:\\TEMP\\";//本地测试用
	    mFileName = getFileName(mDelFlag);
	    return true;
	  }
	  /**
	   * 生成文件名：ReAgentAnyDown1_86110000_200709.xls
	   * @param cAgentCode
	   * @return
	   */
	  private String getFileName(String mDelFlag)
	  {
	    String filename = "";
	    if(this.mBranchType.equals("4"))
	    {
	    	filename = "ReAgentAnyDown1_";
	    }
	    else
	    {
	    	filename = "ReAgentAnyDown99_";
	    }

	    if(mDelFlag.equals("0"))
	    {
	      filename = filename + mManageCom + "_";
	    }
	    else
	    {
	      filename = filename + mAgentCode + "_";
	    }
	    String  startdate_Sql = " select substr(to_char(to_date('?mStartDate?','yyyy-mm-dd'),'yyyy-mm-dd'),1,10) from dual";
		String  enddate_Sql = " select substr(to_char(to_date('?mEndDate?','yyyy-mm-dd'),'yyyy-mm-dd'),1,10) from dual";
		SQLwithBindVariables sqlbv22=new SQLwithBindVariables();
		sqlbv22.sql(startdate_Sql);
		sqlbv22.put("mStartDate", mStartDate);
		SQLwithBindVariables sqlbv23=new SQLwithBindVariables();
		sqlbv23.sql(enddate_Sql);
		sqlbv23.put("mEndDate", mEndDate);
		ExeSQL ttExeSQL = new ExeSQL();
		mStartDate=ttExeSQL.getOneValue(sqlbv22);
		mEndDate=ttExeSQL.getOneValue(sqlbv23);

	    filename=filename+mStartDate+"_"+mEndDate+"_"+mType1+"_"+mType2+"_"+mType3+"_"+mType4;
	    filename = filename + ".xls";
	    logger.debug("生成文件名:" + filename);
	    return filename;
	  }
    /**
     *错误生成函数
     */
    private void buildError(String szFunc, String szErrMsg)
    {
       CError cError = new CError();
       cError.moduleName = "XQDutyReportAuto";
       cError.functionName = szFunc;
       cError.errorMessage = szErrMsg;
       this.mErrors.addOneError(cError);
    }

    private boolean getSql(VData sInputData)
    {
      TransferData mTransferData = (TransferData) sInputData.getObjectByObjectName("TransferData",0);
      GlobalInput mGlobalInput = (GlobalInput) sInputData.getObjectByObjectName("GlobalInput",0);

      if ((mTransferData == null) || (mGlobalInput == null))
      {
        this.buildError("getInputData", "没有得到传入的数据");
        return false;
      }
      String mManageCom = (String) mTransferData.getValueByName("ManageCom");
      String mAgentCode = (String) mTransferData.getValueByName("AgentCode");
      String mStartDate = (String) mTransferData.getValueByName("StartDate");
      String mEndDate = (String) mTransferData.getValueByName("EndDate");
      String mType1 = (String) mTransferData.getValueByName("Type1");
      String mType2 = (String) mTransferData.getValueByName("Type2");
      String mType3 = (String) mTransferData.getValueByName("Type3");
      String mType4 = (String) mTransferData.getValueByName("Type4");
      String mBranchType = (String) mTransferData.getValueByName("BranchType");
      String mXSExcTemplate = (String) mTransferData.getValueByName("XSExcTemplate");
      if(mType3.equals("2"))
      {
         mType2="3";
      }
      String  mOperate = (String) mTransferData.getValueByName("Operator");
      //若前台只录入了管理机构，则生成该管理机构下所有代理人的应收清单，首先取出代理人编码
//      String sql = "select distinct(b.agentcode) from lcprem a, LCPol b where a.polno = b.polno"
//  	       +" and a.PaytoDate >= '"+this.mStartDate+"'and a.PaytoDate <= '"+this.mEndDate+"' "
//  	       +" and b.appflag = '1' and b.salechnl = '02' and length(trim(a.dutycode)) = 6 and length(trim(a.payplancode)) = 6"
//  	       +" and (b.StopFlag = '0' or b.StopFlag is null) and a.GrpPolNo = '00000000000000000000'";

      String sql =" select a.agentnew from lrascription a where  "
      +" a.paytodate>='?mStartDate?' and a.paytodate<='?mEndDate?' "
      +" and exists(select 1 from laagent where agentcode=a.agentnew and branchtype='?mBranchType?')";

      String sql2 =" select a.agentcode from lradimascription a where  "
      +" a.paytodate>='?mStartDate?' and a.paytodate<='?mEndDate?' "
      +" and exists(select 1 from laagent where agentcode=a.agentcode and branchtype='?mBranchType?')";


      SQLwithBindVariables sqlbv24=new SQLwithBindVariables();
      sqlbv24.put("mStartDate", mStartDate);
      sqlbv24.put("mEndDate", mEndDate);
      sqlbv24.put("mBranchType", mBranchType);
      if(mManageCom != null &&(!mManageCom.equals("")))
      {
        sql = sql + " and a.ManageCom like concat('?mManageCom?','%') ";
        sql2 = sql2 + " and a.ManageCom like concat('?mManageCom?','%') ";
        mDelFlag="0";
        sqlbv24.put("mManageCom", mManageCom);
      }
      if(mAgentCode != null && (!mAgentCode.equals("")))
      {
         sql = sql + "and a.agentnew = '?mAgentCode?'";
         sql2 = sql2 + "and a.agentcode = '?mAgentCode?'";
         sqlbv24.put("mAgentCode", mAgentCode);
        mDelFlag="1";
      }
      ExeSQL mExeSQL=new ExeSQL();
      sql = sql+" union "+sql2;
      sqlbv24.sql(sql);
      mSSRS = mExeSQL.execSQL(sqlbv24);
      logger.debug("需要处理mSSRS.getMaxRow()："+mSSRS.getMaxRow());

      if (mSSRS.getMaxRow()==0)
      {
          buildError("getInputData", "没有符合条件的员工！");
          return false;
      }
      return true;
    }
	 /**
     * 保全未使用无扫描申请数据清理批处理
     */
    private void EdorUnuseNoScanApply()
    {
        logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理启动");
        //日志监控,过程监控        
	  	PubFun.logTrack(tG,tG.LogID[1],"保全未使用无扫描申请垃圾数据清理批处理启动");

        //if (!(mCurrentTime.compareTo("23:00:00") >= 0 && mCurrentTime.compareTo("23:59:59") <= 0))
        //{
        //    return;    //在晚上 11 点以后运行
        //}
        //if (!(mCurrentTime.compareTo("00:00:00") >= 0 && mCurrentTime.compareTo("05:59:59") <= 0))
        //{
        //    return;    //在凌晨 06 点以前运行
        //}

        //查询逾期终止天数
        String sTimeoutDays = getEdorTypeTimeoutDays("WFOverDate");
        if (sTimeoutDays == null || sTimeoutDays.trim().equals(""))
        {
            logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理, 查询逾期终止天数失败! ");
            //日志监控,过程监控        
    	  	PubFun.logTrack(tG,tG.LogID[1],"保全未使用无扫描申请垃圾数据清理批处理, 系统未设置逾期终止天数");
            return;
        }

        //选取需要清理的数据
        String DeleteSQL = new String("");
        DeleteSQL = "delete from LWMission "
                  +  "where ActivityID = '0000000002' "
                  +    "and MissionProp3 is null "
                  +    "and MakeDate <= subdate(to_date('?mCurrentDate?','yyyy-mm-dd') , ?sTimeoutDays?)";
        //logger.debug(DeleteSQL);
        SQLwithBindVariables sqlbv25=new SQLwithBindVariables();
        sqlbv25.sql(DeleteSQL);
        sqlbv25.put("mCurrentDate", mCurrentDate);
        sqlbv25.put("sTimeoutDays", sTimeoutDays);

        MMap tMap = new MMap();
        tMap.put(sqlbv25, "DELETE");
        VData tVData = new VData();
        tVData.add(tMap);
        tMap = null;

        //PubSubmit
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(tVData, ""))
        {
            logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理, 删除数据失败！");
            return;
        }
        tPubSubmit = null;
        tVData = null;

        logger.debug("\t@> PEdorValidTask.EdorUnuseNoScanApply() : 保全未使用无扫描申请垃圾数据清理批处理完毕");
        //日志监控,过程监控        
	  	PubFun.logTrack(tG,tG.LogID[1],"保全未使用无扫描申请垃圾数据清理批处理完毕");
    }  //function EdorUnuseNoScanApply end

    /**
     * 获取各种保全逾期终止批处理的逾期天数
     */
    private String getEdorTypeTimeoutDays(String sEdorType)
    {
        String sResultTimeoutDays = new String("");

        //查询逾期天数计算代码
        String QuerySQL = "select * from LMEdorCal where CalType = '?sEdorType?'";
        SQLwithBindVariables sqlbv26=new SQLwithBindVariables();
        sqlbv26.sql(QuerySQL);
        sqlbv26.put("sEdorType", sEdorType);
        LMEdorCalDB tLMEdorCalDB = new LMEdorCalDB();
        LMEdorCalSet tLMEdorCalSet = new LMEdorCalSet();
        try
        {
            tLMEdorCalSet = tLMEdorCalDB.executeQuery(sqlbv26);
        }
        catch (Exception ex)
        {
            logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 执行 SQL 查询出错");
            logger.debug("\t   错误原因 : " + tLMEdorCalDB.mErrors.getFirstError());
            return null;
        }
        if (tLMEdorCalSet == null || tLMEdorCalSet.size() <= 0)
        {
            logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 未知的保全类型！ 查询逾期天数失败！");
            return null;
        }
        else
        {
            String sTempCalCode = tLMEdorCalSet.get(1).getCalCode();
            if (sTempCalCode == null || sTempCalCode.trim().equals(""))
            {
                logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 查询逾期天数计算代码失败！");
                return null;
            }
            else
            {
                //根据计算代码计算逾期天数
                Calculator tCalculator = new Calculator();
                tCalculator.setCalCode(sTempCalCode);
                sResultTimeoutDays = tCalculator.calculate();
                if (sResultTimeoutDays == null || sResultTimeoutDays.trim().equals(""))
                {
                    logger.debug("\t@> PEdorValidTask.getEdorTypeTimeoutDays() : 根据计算代码计算逾期天数失败！");
                    return null;
                }
                tCalculator = null;
            }
        }
        tLMEdorCalDB = null;
        tLMEdorCalSet = null;

        return sResultTimeoutDays;
    } //function getEdorTypeTimeoutDays end

    //==========================================================================

	public static void main(String[] args) {
		XQDutyReportAutoTMThread tXQDutyReportAutoTMThread = new XQDutyReportAutoTMThread();
		tXQDutyReportAutoTMThread.dealMain();
	}
}
