<%@include file="/i18n/language.jsp"%>
   <%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <%@page import="com.sinosoft.lis.fininterface.*"%>
 <%@page import="com.sinosoft.lis.db.*"%>
 <%@page import="com.sinosoft.lis.vdb.*"%>
 <%@page import = "com.sinosoft.lis.pubfun.*"%>
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import="java.net.*"%>
 <%@page import="java.util.*"%>
 <%@page import="java.io.*"%>

<%
  CErrors tError = new CErrors();
  String FlagStr = "";
  String Content = "";
  String flag = "true";
  String bdate = request.getParameter("Bdate");
  String edate = request.getParameter("Edate");
  loggerDebug("OtoF",bdate);
  loggerDebug("OtoF",edate);  
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");


  FDate chgdate = new FDate();
  Date dbdate = chgdate.getDate(bdate);
  Date dedate = chgdate.getDate(edate);
  if(dbdate.compareTo(dedate) > 0)
  {
       flag = "false";
       FlagStr = "Fail";
       Content=bundle.getString("M0000063554");
  }

  if(flag.equals("true"))
  {
       loggerDebug("OtoF","�?始提�?");
	   
       FInDealEngine tFInDealEngine = new FInDealEngine();  
       try 
       {
           
    	   VData vData = new VData();
           vData.addElement(tGI);
  	       vData.addElement(bdate);
  	       vData.addElement(edate);
		   //标识跑批的类�? 由于嘉禾已经定型仅仅采用表lioperationdataclassdef中的字段DistillClass来处�?

		   //1 为正常跑批的处理 2 为续期应收记录的处理
  	       vData.addElement("1");//界面采用写定的�?�来跑批------正常跑批处理  	       
           if(!tFInDealEngine.dealProcess(vData,false))
           {
             loggerDebug("OtoF",tFInDealEngine.mErrors.getFirstError());
             if(!tFInDealEngine.WriteErrLog())
             {
               loggerDebug("OtoF",tFInDealEngine.mErrors.getFirstError());
             }
             FlagStr = "Fail";
             Content = bundle.getString("M0000251210") + tFInDealEngine.mErrors.getFirstError();                          
           }
          else
          {
             FlagStr = "Succ";
             Content = bundle.getString("M0000251206");            
          }          	                     
       }
       catch(Exception ex) 
       {       
             FlagStr = "Fail";
             Content=bundle.getString("M0000250558") + ex.getMessage();   
             loggerDebug("OtoF",Content);             
       }finally{
    	   //�?后都要处理日志信�?

    	   if(!tFInDealEngine.m_bServiceFlag){
    		   //�?要更新日�?

    		   if(!tFInDealEngine.updateLITranlog()){
    	             FlagStr = "Fail";
    	             Content=bundle.getString("M0000251211");   
    	             loggerDebug("OtoF",Content); 
    		   }
    	   }  	       	   
       }
  	   loggerDebug("OtoF","提数结束");
  	  
  }


%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

