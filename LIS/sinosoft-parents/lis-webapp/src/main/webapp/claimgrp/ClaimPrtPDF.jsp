
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
/****************************************�����ӡ**********************************************/
    //��ʼ��
    ManagePrtBill myManagePrtBill=new ManagePrtBill();
      //������Ϣ
    String strErrInfo = "";
    String ClientIP="";
    String ClientHost="";
    String ManageCom="";
    String OPManageCom="";
    String option="";
    String RealPath="";
    String filename1="";
    String filename="";
    //����Ա    
    String Operator="";
        //����������
    String RgtNo ="";
   CreateClaim MYCreateClaim=new CreateClaim();
   String ServerIP="";
        
    //��ȡrealpath
       RealPath = application.getRealPath("/").replace('\\','/');
       RealPath = RealPath+"/";

    try{
    //�ͻ���IP
    ClientIP=request.getRemoteAddr();
    //�ͻ��˻�����
    ClientHost=request.getRemoteHost();
    //��¼�û������������
     GlobalInput tG = new GlobalInput();
     tG=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����
     //������IP(����д���ǹ̶�ֵ,������д���ˡ�)
     ServerIP = myManagePrtBill.getServerIP();
     
     //�û�������ѯʱ�Ĺ��������
    ManageCom=request.getParameter("ManageCom");

    //�ⰸ��
   
     if (ManageCom==null)
     {
           ManageCom="";
     }
     OPManageCom=tG.ComCode;
     if (ManageCom.trim().length()==0)
     {
         ManageCom=OPManageCom;
     }
     
     //����Ա    
     Operator=tG.Operator;
    //����������
    RgtNo= request.getParameter("RptNo");
    if(RgtNo==null||"".equals(RgtNo)){
	    RgtNo  = request.getParameter("RgtNo");
    }
    // ����Ĳ���
   option=request.getParameter("operator");

    Stringtools.log("","=������ش�ӡ���������=RgtNo="+RgtNo+"����="+option,"");  //ClientIP+"@"+ClientHost+"user="+operator);

    }catch(Exception e)
    {
    	strErrInfo="ClaimPrtPDF.jsp===>ҵ����������";
    }
    boolean yn=MYCreateClaim.getRefuse(RgtNo);
    Stringtools.log("","�Ƿ����=="+yn,"");
    //�ж��Ƿ����
    if (yn)   //����
     {
     try{
     /********************����*******************************/
      Stringtools.log("","��������","");
      filename1=myManagePrtBill.setPrintPath(RgtNo,25,ClientIP,".pdf",RealPath);
      filename=RealPath+filename1;
      strErrInfo=MYCreateClaim.RefuseClaimBatch(filename,RgtNo,ManageCom).trim();
                if (strErrInfo.length()==0)
             	{     
                  int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,25);
                  //д�ļ���־
 		  String tmpsql="insert into print values('"+RgtNo+"',25,'�����������',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            		try{
             			ExeSQL MYExeSQL=new ExeSQL();
             			MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             			strErrInfo=strErrInfo+"дPrint�����!";
             			}
             		if (strErrInfo.length()==0)
             		{     
	     			//ת����ʾPDF�ļ�          
             			response.sendRedirect("../"+filename1);
             		}
 	        }
                else
               {
                       Stringtools.log("","����������ӡ===>"+RgtNo+"������Ϣ��"+strErrInfo,"");
                }

     
     /*******************�������********************************/
     }catch(Exception e)
     {
     	strErrInfo="����������ӡʧ�ܣ��ⰸ�ţ�"+RgtNo;
     	Stringtools.log("",strErrInfo,"");
     }
     }
     else
     {

      try{	  
     	   if(Stringtools.stringequals(option,"batch"))   //����������ӡ 
	   {  
        	loggerDebug("ClaimPrtPDF","======����===������ӡ="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,25,ClientIP,".pdf",RealPath);
                filename=RealPath+filename1;
                loggerDebug("ClaimPrtPDF","=====filename1=======:"+filename1+"=========================");
                strErrInfo=MYCreateClaim.CreateClaimBatch(filename,RgtNo,ManageCom).trim();
             	if (strErrInfo.length()==0)
             	{     
                  int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,25);
                   //д�ļ���־
 		   String tmpsql="insert into print values('"+RgtNo+"',25,'��������',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            		try{
             			ExeSQL MYExeSQL=new ExeSQL();
             			MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             			strErrInfo=strErrInfo+"дPrint�����!";
             			}
             		if (strErrInfo.length()==0)
             		{     
	     			//ת����ʾPDF�ļ�          
             			response.sendRedirect("../"+filename1);
             		}
	        }
                else
               {
                       Stringtools.log("","����������ӡ===>"+RgtNo+"������Ϣ��"+strErrInfo,"");
                }
   	 }
   	}catch(Exception e1)
   	{
   	 strErrInfo="����������ӡ����===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}
//��ӡ����嵥
      try{	  
     	   if(Stringtools.stringequals(option,"GetBill"))   //��ӡ����嵥
	 {  
	        
                filename1=myManagePrtBill.setPrintPath(RgtNo,26,ClientIP,".pdf",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","======����===����嵥��ӡ="+RgtNo+"=================");
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
              strErrInfo=MYCreateClaim.CreatePayBill(filename,RgtNo,ManageCom).trim();
             	if (strErrInfo.length()==0)
             	{     
             	   RgtNo=RgtNo+"_1";
                   int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,26);
                   //д�ļ���־
 		   String tmpsql="insert into print values('"+RgtNo+"',26,'����嵥',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            		try{
             			ExeSQL MYExeSQL=new ExeSQL();
             			MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             			strErrInfo=strErrInfo+"дPrint�����!";
             			}
             		if (strErrInfo.length()==0)
             		{     
	     			//ת����ʾPDF�ļ�          
             			response.sendRedirect("../"+filename1);
             		}
	        }
                else
               {
                       Stringtools.log("","��������嵥��ӡ===>"+RgtNo+"������Ϣ��"+strErrInfo,"");
                }
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="��������嵥��ӡ����===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}
   
//����֧���嵥
      try{	  
     	   if(Stringtools.stringequals(option,"PayBill"))   //����֧���嵥
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======����===����֧���嵥��ӡ="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,27,ClientIP,".pdf",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
              strErrInfo=CreateClaim.CreateOutBill(filename,RgtNo,ManageCom).trim();
             	if (strErrInfo.length()==0)
             	{     
             	   RgtNo=RgtNo+"_2";
                   int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,27);
                   //д�ļ���־
 		   String tmpsql="insert into print values('"+RgtNo+"',27,'����֧���嵥',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            		try{
             			ExeSQL MYExeSQL=new ExeSQL();
             			MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             			strErrInfo=strErrInfo+"дPrint�����!";
             			}
             		if (strErrInfo.length()==0)
             		{     
	     			//ת����ʾPDF�ļ�          
             			response.sendRedirect("../"+filename1);
             		}
	        }
                else
               {
                       Stringtools.log("","��������֧���嵥��ӡ===>"+RgtNo+"������Ϣ��"+strErrInfo,"");
                }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="��������֧���嵥��ӡ����===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}

//����֧���嵥Exce��
      try{	  
     	   if(Stringtools.stringequals(option,"PayBillExcel"))   //����֧���嵥EXCEL��
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======����===����֧���嵥��ӡEXCEL��="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,28,ClientIP,".xls",RealPath);
                filename=RealPath+filename1;
             	loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
              	String [] reportdata =CreateClaim.CreateOutBillExcel(filename,RgtNo,ManageCom);
             	if (!Stringtools.stringequals(reportdata[0],"0"))
             	{     
	     		//ת����ʾExcel�ļ�
	     		strErrInfo=CreateExcel.Crtexcel(filename,reportdata,7);        
	     		if (Stringtools.stringequals(strErrInfo,"1"))
	     		{
	     			strErrInfo="";
             	   		RgtNo=RgtNo+"_3";
        	           	int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,28);
                	   	//д�ļ���־
 		   		String tmpsql="insert into print values('"+RgtNo+"',28,'����֧���嵥EXCEL��',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				ExeSQL MYExeSQL=new ExeSQL();
             				MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"дPrint�����!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{     
	     				//ת����ʾPDF�ļ�          
             				response.sendRedirect("../"+filename1);
	             		}
	     		}
             		
	        }
                else
               {
                       strErrInfo=reportdata[1];
                       Stringtools.log("","��������֧���嵥��ӡEXCEL��===>"+RgtNo+"������Ϣ��"+strErrInfo,"");
                }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="��������֧���嵥��ӡ����EXCEL��===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}
/*************************88�����嵥Excel��*****************************/
      try{	  
     	   if(Stringtools.stringequals(option,"FeeBill"))   
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======����===�����嵥EXCEL��="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,29,ClientIP,".xls",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
             loggerDebug("ClaimPrtPDF","=����=�����嵥==��ӡ=filename=EXCEL��=="+filename);
              strErrInfo = MYCreateClaim.CreateFeeBill(filename,RgtNo,ManageCom);
             	if (strErrInfo.length()<=1)
             	{     
	     			strErrInfo="";
             	   		RgtNo=RgtNo+"_4";
        	           	int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,29);
                	   	//д�ļ���־
 		   		String tmpsql="insert into print values('"+RgtNo+"',29,'����֧���嵥EXCEL��',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				ExeSQL MYExeSQL=new ExeSQL();
             				MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"дPrint�����!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{     
	     				//ת����ʾPDF�ļ�          
             				response.sendRedirect("../"+filename1);
	             		}
             		
	        }
                else
               {
                       
                       Stringtools.log("","��������嵥��ӡEXCEL��===>"+RgtNo+"������Ϣ��"+strErrInfo,"");
                }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="��������嵥��ӡ����EXCEL��===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}
/**********************************************************************************/
/*************************����嵥Excel��*****************************/
      try{	  
     	   if(Stringtools.stringequals(option,"claimBillExcel"))   
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======����===����嵥EXCEL��="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,30,ClientIP,".xls",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
             loggerDebug("ClaimPrtPDF","=����=����嵥Excel��==��ӡ=filename=EXCEL��=="+filename);
              strErrInfo = MYCreateClaim.CreatePayBillExcel(filename,RgtNo,ManageCom);
             	if (strErrInfo.length()<=1)
             	{     
	     			strErrInfo="";
             	   		RgtNo=RgtNo+"_5";
        	           	int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,30);
                	   	//д�ļ���־
 		   		String tmpsql="insert into print values('"+RgtNo+"',30,'��������嵥EXCEL��',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				ExeSQL MYExeSQL=new ExeSQL();
             				MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"дPrint�����!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{     
	     				//ת����ʾPDF�ļ�          
             				response.sendRedirect("../"+filename1);
	             		}
             		
	        }
                else
               {
                       
                       Stringtools.log("","��������嵥��ӡEXCEL��===>"+RgtNo+"������Ϣ��"+strErrInfo,"");
                }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="��������嵥��ӡ����EXCEL��===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}
/**********************************************************************************/


/*************************��������嵥*****(����ģ��ȫ�������ղ��ṩ)***********************/
      try{	  
     	   if(Stringtools.stringequals(option,"ClaimPer"))   
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======����===�����嵥="+RgtNo+"=================");
             filename = RealPath+"pdffiles\\ClaimClaimPer"+RgtNo.trim()+".pdf";
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
             //ģ���ļ���
             String TemplateFilename=RealPath+"pdftemplate\\ClaimPer.pdf";
             
             Stringtools.log("","=��������嵥==��ӡ=filename=EXCEL��=="+filename,"");

              
              strErrInfo = MYCreateClaim.CreateClaimPer(filename,RgtNo,TemplateFilename);
             	if (strErrInfo.length()<=1)
             	{     
	     			strErrInfo="";
             	   		RgtNo=RgtNo+"_6";
        	           	int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,31);
                	   	//д�ļ���־
 		   		String tmpsql="insert into print values('"+RgtNo+"',31,'��������վ�',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				ExeSQL MYExeSQL=new ExeSQL();
             				MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"дPrint�����!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{     
	     				//ת����ʾPDF�ļ�          
             				response.sendRedirect("../"+filename1);
	             		}
             		
	        }
                else
               {
                       Stringtools.log("","��������嵥��ӡ===>"+RgtNo+"������Ϣ��"+strErrInfo,"");
                }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="��������嵥��ӡ����===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}


/**********************************************************************************/

//��������վ�          -------------------------------------
      try{	  
     	   if(Stringtools.stringequals(option,"lksj"))   //��������վ�
	 {  
             Stringtools.log("","======��������վ�="+RgtNo+"=================","");
                filename1=myManagePrtBill.setPrintPath(RgtNo,31,ClientIP,".pdf",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
             loggerDebug("ClaimPrtPDF","=��������վ�==��ӡ=filename==="+filename);
             TemplateToPdf mybilldata=new TemplateToPdf();
            String [] myData=mybilldata.CreateGetBill(RgtNo);     
            loggerDebug("ClaimPrtPDF"," myData[0]====="+ myData[0]);  
            Stringtools.log(""," myData[0]====="+ myData[0],"");  
            if (myData[0].equals("0"))
               {
               	       strErrInfo=myData[1];
                       Stringtools.log("","��������վݴ�ӡ===>"+RgtNo+"������Ϣ��"+strErrInfo,"");
                       //loggerDebug("ClaimPrtPDF","======����===="+strErrInfo.trim().length());
                }
                else
                {
                
                    String TemplateFilename=RealPath+"pdftemplate\\EdorGetbill.pdf";
                    if (mybilldata.CreatePDF(myData,filename,TemplateFilename))  
                	{
	     			strErrInfo="";
             	   		RgtNo=RgtNo+"_6";
        	           	int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,31);
                	   	//д�ļ���־
 		   		String tmpsql="insert into print values('"+RgtNo+"',31,'��������վ�',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				ExeSQL MYExeSQL=new ExeSQL();
             				MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"дPrint�����!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{     
	     				//ת����ʾPDF�ļ�          
             				response.sendRedirect("../"+filename1);
	             		}
             		}
             		else
             		{
             		strErrInfo="�����ļ�ʧ�ܣ���";
             		}
	        }
         }     
   	}catch(Exception e1)
   	{
 	 strErrInfo="��������վݴ�ӡ����===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}
      }   
/*******************************************************************/
/*************************�籣�ʵ������嵥(Excel)*****************************/
      try{	  
     	   if(Stringtools.stringequals(option,"PrintCaseReceiptClass"))   
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======����===�籣�ʵ������嵥="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,40,ClientIP,".xls",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
             loggerDebug("ClaimPrtPDF","=����=�籣�ʵ������嵥==��ӡ=filename=�籣�����嵥EXCEL��=="+filename);
             LLCaseReceiptClassExcel myLLCaseReceiptClassExcel=new LLCaseReceiptClassExcel();
              strErrInfo = myLLCaseReceiptClassExcel.CreateReceiptExcel(filename,RgtNo);
             	if (strErrInfo.length()<=1)
             	{     
	     			strErrInfo="";
             	   		RgtNo=RgtNo+"_5";
        	           	int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,40);
                	   	//д�ļ���־
 		   		String tmpsql="insert into print values('"+RgtNo+"',40,'�籣�����嵥EXCEL��',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				ExeSQL MYExeSQL=new ExeSQL();
             				MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"дPrint�����!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{     
	     				//ת����ʾPDF�ļ�          
             				response.sendRedirect("../"+filename1);
	             		}
             		
	        }
                else
               {
                       
                       Stringtools.log("","��������嵥��ӡEXCEL��===>"+RgtNo+"������Ϣ��"+strErrInfo,"");
                }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="��������嵥��ӡ����EXCEL��===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}
/********************************************/

/*************************������Ϣ����(Excel)*****************************/
      try{	  
     	   if(Stringtools.stringequals(option,"PrintReportClass"))   
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======����===������Ϣ����="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,42,ClientIP,".xls",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","=====RealPath=======:"+RealPath+"");
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
             loggerDebug("ClaimPrtPDF","=����=������Ϣ����==��ӡ=filename==="+filename);
             CreateClaimTemplate tCreateClaimTemplate=new CreateClaimTemplate();
             boolean ErrInfo = tCreateClaimTemplate.CreateSimpleClaimTemplate(RgtNo,filename);
             
             	if (ErrInfo=true)             	
            {
	     			
             	  RgtNo=RgtNo+"_5";
        	      //int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,42);
                //д�ļ���־
 		   		      //String tmpsql="insert into print values('"+RgtNo+"',42,'������Ϣ�嵥EXCEL��',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				//ExeSQL MYExeSQL=new ExeSQL();
             				//MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"дPrint�����!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{
	     				  //ת����ʾPDF�ļ�          
             				response.sendRedirect("../"+filename1);
	             		}
             		
	          }
               else
               {                      
                    Stringtools.log("","������Ϣ����===>"+RgtNo+"������Ϣ��"+strErrInfo,"");
               }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="������Ϣ����===>"+e1.getMessage();
	 Stringtools.log("",strErrInfo,"");  
   	}   	
/********************************************/

/*************************������Ϣ����2(Excel)*****************************/
      try{	  
     	   if(Stringtools.stringequals(option,"PrintAccReportClass"))   
	 {  
	
        	  loggerDebug("ClaimPrtPDF","======����===������Ϣ����="+RgtNo+"=================");
                filename1=myManagePrtBill.setPrintPath(RgtNo,42,ClientIP,".xls",RealPath);
                filename=RealPath+filename1;
             loggerDebug("ClaimPrtPDF","=====filename=======:"+filename+"=========================");
             loggerDebug("ClaimPrtPDF","=����=������Ϣ����==��ӡ=filename==="+filename);
             CreateClaimTemplate tCreateClaimTemplate=new CreateClaimTemplate();
             boolean ErrInfo = tCreateClaimTemplate.CreateAccClaimTemplate(RgtNo,filename);
             
             	if (ErrInfo=true)             	
            {     
	     			
             	  RgtNo=RgtNo+"_5";
        	      int printtimes=myManagePrtBill.getMAXPrtTimes(RgtNo,42);
                //д�ļ���־
 		   		      String tmpsql="insert into print values('"+RgtNo+"',42,'������Ϣ�嵥EXCEL��',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
            			try{
             				ExeSQL MYExeSQL=new ExeSQL();
             				MYExeSQL.execUpdateSQL(tmpsql);
             			}catch(Exception e)
             			{
             				strErrInfo=strErrInfo+"дPrint�����!";
             			}
             			
             			if (strErrInfo.length()==0)
             			{     
	     				  //ת����ʾPDF�ļ�          
             				response.sendRedirect("../"+filename1);
	             		}
             		
	          }
               else
               {                      
                    Stringtools.log("","������Ϣ����===>"+RgtNo+"������Ϣ��"+strErrInfo,"");
               }
                
   	 }
   	}catch(Exception e1)
   	{
 	 strErrInfo="������Ϣ����===>"+e1.getMessage();
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
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		
		showInfo.focus();

		top.close();
	}
	else
	{
	    content = "�����ļ��ɹ���";
	    var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//	    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  	   var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		
		showInfo.focus();

  	    top.close();
    }
</script>
</body>
</html>

