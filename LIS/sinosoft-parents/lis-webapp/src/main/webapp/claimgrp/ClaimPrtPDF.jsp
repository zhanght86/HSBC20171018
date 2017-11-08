
<%@page contentType="text/html; charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="java.io.*"%>
<%@page import="java.sql.*"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.bl.*"%>
<%@page import="com.sinosoft.lis.vbl.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*" %>
<%@page import="com.sinosoft.service.*" %>



<%@ page import="java.io.*,java.awt.Color,java.awt.font.*"%>
<html>
<body >

<%
/****************************************理赔打印**********************************************/
    //初始化
    ManagePrtBill myManagePrtBill=new ManagePrtBill();
      //出错信息
    String strErrInfo = "";
    String ClientIP="";
    String ClientHost="";
    String ManageCom="";
    String OPManageCom="";
    String option="";
    String RealPath="";
    String filename1="";
    String filename="";
    //操作员    
    String Operator="";
        //理赔批单号
    String RgtNo ="";
   CreateClaim MYCreateClaim=new CreateClaim();
   String ServerIP="";
        
    //获取realpath
       RealPath = application.getRealPath("/").replace('\\','/');
       RealPath = RealPath+"/";

    try{
    //客户端IP
    ClientIP=request.getRemoteAddr();
    //客户端机器名
    ClientHost=request.getRemoteHost();
    //登录用户名及管理机构
     GlobalInput tG = new GlobalInput();
     tG=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
     //服务器IP(现在写的是固定值,在类里写死了。)
     ServerIP = myManagePrtBill.getServerIP();
     
     //用户操作查询时的管理机构：
    ManageCom=request.getParameter("ManageCom");

    //赔案类
   
     if (ManageCom==null)
     {
           ManageCom="";
     }
     OPManageCom=tG.ComCode;
     if (ManageCom.trim().length()==0)
     {
         ManageCom=OPManageCom;
     }
     
     //操作员    
     Operator=tG.Operator;
    //理赔批单号
    RgtNo= request.getParameter("RptNo");
    if(RgtNo==null||"".equals(RgtNo)){
	    RgtNo  = request.getParameter("RgtNo");
    }
    // 传入的操作
   option=request.getParameter("operator");

    Stringtools.log("","=理赔相关打印：传入参数=RgtNo="+RgtNo+"操作="+option,"");  //ClientIP+"@"+ClientHost+"user="+operator);

    }catch(Exception e)
    {
    	strErrInfo="ClaimPrtPDF.jsp===>业务程序处理出错！";
    }
    boolean yn=MYCreateClaim.getRefuse(RgtNo);
    Stringtools.log("","是否拒赔=="+yn,"");
    //判断是否拒赔
    if (yn)   //拒赔
     {
     try{
     /********************拒赔*******************************/
      Stringtools.log("","拒赔批单","");
      filename1=myManagePrtBill.setPrintPath(RgtNo,25,ClientIP,".pdf",RealPath);
      filename=RealPath+filename1;
      strErrInfo=MYCreateClaim.RefuseClaimBatch(filename,RgtNo,ManageCom).trim();
                if (strErrInfo.length()==0)
             	{     
                  int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,25);
                  //写文件日志
 		  String tmpsql="insert into print values('"+RgtNo+"',25,'理赔拒赔批单',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            		try{
             			ExeSQL MYExeSQL=new ExeSQL();
             			MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             			strErrInfo=strErrInfo+"写Print表出错!";
             			}
             		if (strErrInfo.length()==0)
             		{     
	     			//转向显示PDF文件          
             			response.sendRedirect("../"+filename1);
             		}
 	        }
                else
               {
                       Stringtools.log("","拒赔批单打印===>"+RgtNo+"出错信息："+strErrInfo,"");
                }

     
     /*******************拒赔结束********************************/
     }catch(Exception e)
     {
     	strErrInfo="拒赔批单打印失败！赔案号："+RgtNo;
     	Stringtools.log("",strErrInfo,"");
     }
     }
     else
     {

      try{	  
     	   if(Stringtools.stringequals(option,"batch"))   //理赔批单打印 
	   {  
        	loggerDebug("ClaimPrtPDF","======理赔===批单打印="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,25,ClientIP,".pdf",RealPath);
                filename=RealPath+filename1;
                loggerDebug("ClaimPrtPDF","=====filename1=======:"+filename1+"=========================");
                strErrInfo=MYCreateClaim.CreateClaimBatch(filename,RgtNo,ManageCom).trim();
             	if (strErrInfo.length()==0)
             	{     
                  int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,25);
                   //写文件日志
 		   String tmpsql="insert into print values('"+RgtNo+"',25,'理赔批单',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            		try{
             			ExeSQL MYExeSQL=new ExeSQL();
             			MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             			strErrInfo=strErrInfo+"写Print表出错!";
             			}
             		if (strErrInfo.length()==0)
             		{     
	     			//转向显示PDF文件          
             			response.sendRedirect("../"+filename1);
             		}
	        }
                else
               {
                       Stringtools.log("","理赔批单打印===>"+RgtNo+"出错信息："+strErrInfo,"");
                }
   	 }
   	}catch(Exception e1)
   	{
   	 strErrInfo="理赔批单打印出错！===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}
//打印赔款清单
      try{	  
     	   if(Stringtools.stringequals(option,"GetBill"))   //打印赔款清单
	 {  
	        
                filename1=myManagePrtBill.setPrintPath(RgtNo,26,ClientIP,".pdf",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","======理赔===赔款清单打印="+RgtNo+"=================");
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
              strErrInfo=MYCreateClaim.CreatePayBill(filename,RgtNo,ManageCom).trim();
             	if (strErrInfo.length()==0)
             	{     
             	   RgtNo=RgtNo+"_1";
                   int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,26);
                   //写文件日志
 		   String tmpsql="insert into print values('"+RgtNo+"',26,'赔款清单',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            		try{
             			ExeSQL MYExeSQL=new ExeSQL();
             			MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             			strErrInfo=strErrInfo+"写Print表出错!";
             			}
             		if (strErrInfo.length()==0)
             		{     
	     			//转向显示PDF文件          
             			response.sendRedirect("../"+filename1);
             		}
	        }
                else
               {
                       Stringtools.log("","理赔赔款清单打印===>"+RgtNo+"出错信息："+strErrInfo,"");
                }
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="理赔赔款清单打印出错！===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}
   
//理赔支付清单
      try{	  
     	   if(Stringtools.stringequals(option,"PayBill"))   //理赔支付清单
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======理赔===理赔支付清单打印="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,27,ClientIP,".pdf",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
              strErrInfo=CreateClaim.CreateOutBill(filename,RgtNo,ManageCom).trim();
             	if (strErrInfo.length()==0)
             	{     
             	   RgtNo=RgtNo+"_2";
                   int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,27);
                   //写文件日志
 		   String tmpsql="insert into print values('"+RgtNo+"',27,'理赔支付清单',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            		try{
             			ExeSQL MYExeSQL=new ExeSQL();
             			MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             			strErrInfo=strErrInfo+"写Print表出错!";
             			}
             		if (strErrInfo.length()==0)
             		{     
	     			//转向显示PDF文件          
             			response.sendRedirect("../"+filename1);
             		}
	        }
                else
               {
                       Stringtools.log("","理赔理赔支付清单打印===>"+RgtNo+"出错信息："+strErrInfo,"");
                }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="理赔理赔支付清单打印出错！===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}

//理赔支付清单Exce版
      try{	  
     	   if(Stringtools.stringequals(option,"PayBillExcel"))   //理赔支付清单EXCEL版
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======理赔===理赔支付清单打印EXCEL版="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,28,ClientIP,".xls",RealPath);
                filename=RealPath+filename1;
             	loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
              	String [] reportdata =CreateClaim.CreateOutBillExcel(filename,RgtNo,ManageCom);
             	if (!Stringtools.stringequals(reportdata[0],"0"))
             	{     
	     		//转向显示Excel文件
	     		strErrInfo=CreateExcel.Crtexcel(filename,reportdata,7);        
	     		if (Stringtools.stringequals(strErrInfo,"1"))
	     		{
	     			strErrInfo="";
             	   		RgtNo=RgtNo+"_3";
        	           	int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,28);
                	   	//写文件日志
 		   		String tmpsql="insert into print values('"+RgtNo+"',28,'理赔支付清单EXCEL版',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				ExeSQL MYExeSQL=new ExeSQL();
             				MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"写Print表出错!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{     
	     				//转向显示PDF文件          
             				response.sendRedirect("../"+filename1);
	             		}
	     		}
             		
	        }
                else
               {
                       strErrInfo=reportdata[1];
                       Stringtools.log("","理赔理赔支付清单打印EXCEL版===>"+RgtNo+"出错信息："+strErrInfo,"");
                }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="理赔理赔支付清单打印出错！EXCEL版===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}
/*************************88费用清单Excel版*****************************/
      try{	  
     	   if(Stringtools.stringequals(option,"FeeBill"))   
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======理赔===费用清单EXCEL版="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,29,ClientIP,".xls",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
             loggerDebug("ClaimPrtPDF","=理赔=费用清单==打印=filename=EXCEL版=="+filename);
              strErrInfo = MYCreateClaim.CreateFeeBill(filename,RgtNo,ManageCom);
             	if (strErrInfo.length()<=1)
             	{     
	     			strErrInfo="";
             	   		RgtNo=RgtNo+"_4";
        	           	int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,29);
                	   	//写文件日志
 		   		String tmpsql="insert into print values('"+RgtNo+"',29,'理赔支付清单EXCEL版',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				ExeSQL MYExeSQL=new ExeSQL();
             				MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"写Print表出错!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{     
	     				//转向显示PDF文件          
             				response.sendRedirect("../"+filename1);
	             		}
             		
	        }
                else
               {
                       
                       Stringtools.log("","理赔费用清单打印EXCEL版===>"+RgtNo+"出错信息："+strErrInfo,"");
                }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="理赔费用清单打印出错！EXCEL版===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}
/**********************************************************************************/
/*************************赔款清单Excel版*****************************/
      try{	  
     	   if(Stringtools.stringequals(option,"claimBillExcel"))   
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======理赔===赔款清单EXCEL版="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,30,ClientIP,".xls",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
             loggerDebug("ClaimPrtPDF","=理赔=赔款清单Excel版==打印=filename=EXCEL版=="+filename);
              strErrInfo = MYCreateClaim.CreatePayBillExcel(filename,RgtNo,ManageCom);
             	if (strErrInfo.length()<=1)
             	{     
	     			strErrInfo="";
             	   		RgtNo=RgtNo+"_5";
        	           	int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,30);
                	   	//写文件日志
 		   		String tmpsql="insert into print values('"+RgtNo+"',30,'理赔赔款清单EXCEL版',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				ExeSQL MYExeSQL=new ExeSQL();
             				MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"写Print表出错!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{     
	     				//转向显示PDF文件          
             				response.sendRedirect("../"+filename1);
	             		}
             		
	        }
                else
               {
                       
                       Stringtools.log("","理赔赔款清单打印EXCEL版===>"+RgtNo+"出错信息："+strErrInfo,"");
                }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="理赔赔款清单打印出错！EXCEL版===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}
/**********************************************************************************/


/*************************理赔个人清单*****(程序模板全部由团险部提供)***********************/
      try{	  
     	   if(Stringtools.stringequals(option,"ClaimPer"))   
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======理赔===个人清单="+RgtNo+"=================");
             filename = RealPath+"pdffiles\\ClaimClaimPer"+RgtNo.trim()+".pdf";
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
             //模板文件名
             String TemplateFilename=RealPath+"pdftemplate\\ClaimPer.pdf";
             
             Stringtools.log("","=理赔个人清单==打印=filename=EXCEL版=="+filename,"");

              
              strErrInfo = MYCreateClaim.CreateClaimPer(filename,RgtNo,TemplateFilename);
             	if (strErrInfo.length()<=1)
             	{     
	     			strErrInfo="";
             	   		RgtNo=RgtNo+"_6";
        	           	int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,31);
                	   	//写文件日志
 		   		String tmpsql="insert into print values('"+RgtNo+"',31,'理赔领款收据',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				ExeSQL MYExeSQL=new ExeSQL();
             				MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"写Print表出错!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{     
	     				//转向显示PDF文件          
             				response.sendRedirect("../"+filename1);
	             		}
             		
	        }
                else
               {
                       Stringtools.log("","理赔个人清单打印===>"+RgtNo+"出错信息："+strErrInfo,"");
                }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="理赔个人清单打印出错！===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}


/**********************************************************************************/

//理赔领款收据          -------------------------------------
      try{	  
     	   if(Stringtools.stringequals(option,"lksj"))   //理赔领款收据
	 {  
             Stringtools.log("","======理赔领款收据="+RgtNo+"=================","");
                filename1=myManagePrtBill.setPrintPath(RgtNo,31,ClientIP,".pdf",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
             loggerDebug("ClaimPrtPDF","=理赔领款收据==打印=filename==="+filename);
             TemplateToPdf mybilldata=new TemplateToPdf();
            String [] myData=mybilldata.CreateGetBill(RgtNo);     
            loggerDebug("ClaimPrtPDF"," myData[0]====="+ myData[0]);  
            Stringtools.log(""," myData[0]====="+ myData[0],"");  
            if (myData[0].equals("0"))
               {
               	       strErrInfo=myData[1];
                       Stringtools.log("","理赔领款收据打印===>"+RgtNo+"出错信息："+strErrInfo,"");
                       //loggerDebug("ClaimPrtPDF","======长度===="+strErrInfo.trim().length());
                }
                else
                {
                
                    String TemplateFilename=RealPath+"pdftemplate\\EdorGetbill.pdf";
                    if (mybilldata.CreatePDF(myData,filename,TemplateFilename))  
                	{
	     			strErrInfo="";
             	   		RgtNo=RgtNo+"_6";
        	           	int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,31);
                	   	//写文件日志
 		   		String tmpsql="insert into print values('"+RgtNo+"',31,'理赔领款收据',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				ExeSQL MYExeSQL=new ExeSQL();
             				MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"写Print表出错!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{     
	     				//转向显示PDF文件          
             				response.sendRedirect("../"+filename1);
	             		}
             		}
             		else
             		{
             		strErrInfo="生成文件失败！！";
             		}
	        }
         }     
   	}catch(Exception e1)
   	{
 	 strErrInfo="理赔领款收据打印出错！===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}
      }   
/*******************************************************************/
/*************************社保帐单费用清单(Excel)*****************************/
      try{	  
     	   if(Stringtools.stringequals(option,"PrintCaseReceiptClass"))   
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======理赔===社保帐单费用清单="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,40,ClientIP,".xls",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
             loggerDebug("ClaimPrtPDF","=理赔=社保帐单费用清单==打印=filename=社保费用清单EXCEL版=="+filename);
             LLCaseReceiptClassExcel myLLCaseReceiptClassExcel=new LLCaseReceiptClassExcel();
              strErrInfo = myLLCaseReceiptClassExcel.CreateReceiptExcel(filename,RgtNo);
             	if (strErrInfo.length()<=1)
             	{     
	     			strErrInfo="";
             	   		RgtNo=RgtNo+"_5";
        	           	int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,40);
                	   	//写文件日志
 		   		String tmpsql="insert into print values('"+RgtNo+"',40,'社保费用清单EXCEL版',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				ExeSQL MYExeSQL=new ExeSQL();
             				MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"写Print表出错!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{     
	     				//转向显示PDF文件          
             				response.sendRedirect("../"+filename1);
	             		}
             		
	        }
                else
               {
                       
                       Stringtools.log("","理赔赔款清单打印EXCEL版===>"+RgtNo+"出错信息："+strErrInfo,"");
                }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="理赔赔款清单打印出错！EXCEL版===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}
/********************************************/

/*************************报案信息导出(Excel)*****************************/
      try{	  
     	   if(Stringtools.stringequals(option,"PrintReportClass"))   
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======理赔===报案信息导出="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,42,ClientIP,".xls",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","=====RealPath=======:"+RealPath+"");
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
             loggerDebug("ClaimPrtPDF","=理赔=报案信息导出==打印=filename==="+filename);
             CreateClaimTemplate tCreateClaimTemplate=new CreateClaimTemplate();
             boolean ErrInfo = tCreateClaimTemplate.CreateSimpleClaimTemplate(RgtNo,filename);
             
             	if (ErrInfo=true)             	
            {
	     			
             	  RgtNo=RgtNo+"_5";
        	      //int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,42);
                //写文件日志
 		   		      //String tmpsql="insert into print values('"+RgtNo+"',42,'报案信息清单EXCEL版',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				//ExeSQL MYExeSQL=new ExeSQL();
             				//MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"写Print表出错!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{
	     				  //转向显示PDF文件          
             				response.sendRedirect("../"+filename1);
	             		}
             		
	          }
               else
               {                      
                    Stringtools.log("","报案信息导出===>"+RgtNo+"出错信息："+strErrInfo,"");
               }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="报案信息导出===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}   	
/********************************************/

/*************************报案信息导出2(Excel)*****************************/
      try{	  
     	   if(Stringtools.stringequals(option,"PrintAccReportClass"))   
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======理赔===报案信息导出="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,42,ClientIP,".xls",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
             loggerDebug("ClaimPrtPDF","=理赔=报案信息导出==打印=filename==="+filename);
             CreateClaimTemplate tCreateClaimTemplate=new CreateClaimTemplate();
             boolean ErrInfo = tCreateClaimTemplate.CreateAccClaimTemplate(RgtNo,filename);
             
             	if (ErrInfo=true)             	
            {     
	     			
             	  RgtNo=RgtNo+"_5";
        	      int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,42);
                //写文件日志
 		   		      String tmpsql="insert into print values('"+RgtNo+"',42,'报案信息清单EXCEL版',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				ExeSQL MYExeSQL=new ExeSQL();
             				MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"写Print表出错!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{     
	     				  //转向显示PDF文件          
             				response.sendRedirect("../"+filename1);
	             		}
             		
	          }
               else
               {                      
                    Stringtools.log("","报案信息导出===>"+RgtNo+"出错信息："+strErrInfo,"");
               }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="报案信息导出===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}   	
/********************************************/
      
   	String flag="Sucess";
	out.println(strErrInfo);
	if (strErrInfo.trim().length()>1)
	{ 	
	 	flag="Fail";
	}	 	 
%>
<script language="javascript">
var FlagStr='<%=flag%>';
var content='<%=strErrInfo%>';
if (FlagStr == "Fail" )
	{
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//		showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		
		showInfo.focus();

		top.close();
	}
	else
	{
	    content = "生成文件成功！";
	    var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//	    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  	   var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		
		showInfo.focus();

  	    top.close();
    }
</script>
</body>
</html>

