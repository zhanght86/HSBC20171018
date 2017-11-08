<%
//**************************************************************************************************
//Name：LLPRTCertificatePutOutSaveNew.jsp
//Function：理赔单证通知书打印
//Author：wood
//Date:   2007-07-19
//**************************************************************************************************
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="java.io.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.claimgrp.ManagePrtBill" %>
<%@page import="com.sinosoft.lis.claimgrp.ClaimSuoPei" %>
<%@page import="com.sinosoft.service.*" %>

<%
	 //准备通用参数
    CError cError = new CError();
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    
    if(tG == null) 
    {
        loggerDebug("LLPRTCertificatePutOutSaveNew","登录信息没有获取!!!");
       return;
     }
    String ManageCom=tG.ComCode;
    String Operator=tG.Operator; 
	 //初始化
    ManagePrtBill myManagePrtBill=new ManagePrtBill();
    //出错信息
    String strErrInfo = "";
    //客户端IP
    String ClientIP=request.getRemoteAddr();
    //客户端机器名
    String ClientHost=request.getRemoteHost();
   //服务器IP(现在写的是固定值,在类里写死了。)
   String ServerIP=myManagePrtBill.getServerIP();
    //获取realpath
    String RealPath =application.getRealPath("/").replace('\\','/');

    //取session中的登录用户名及管理机构
     
     String RptNo = request.getParameter("ClmNo");
     String filename="";
     String filename1="";
     filename1=myManagePrtBill.setPrintPath(RptNo,43,ClientIP,".pdf",RealPath);
     filename=RealPath+filename1;
     loggerDebug("LLPRTCertificatePutOutSaveNew","=====filename=======:"+filename+"=========================");
     loggerDebug("LLPRTCertificatePutOutSaveNew","############################");
    //准备数据容器信息
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("RptNo",RptNo);
    //tTransferData.setNameAndValue("CustNo",request.getParameter("customerNo"));
	  tTransferData.setNameAndValue("flag","1");
	  tTransferData.setNameAndValue("filename",filename);
	  tTransferData.setNameAndValue("RealPath",RealPath);
 
    // 准备传输数据 VData
    VData tVData = new VData();
    VData tResult = new VData();
   
     
    tVData.add( tG );        
    tVData.add( tTransferData );
//     ClaimSuoPei tClaimSuoPei = new ClaimSuoPei(); 
//     if (tClaimSuoPei.submitData(tVData,"") == false)
//    {
//        int n = tClaimSuoPei.mErrors.getErrorCount();
//        for (int i = 0; i < n; i++)
//        {
//            strErrInfo = strErrInfo + "原因是: " + tClaimSuoPei.mErrors.getError(i).errorMessage;
//            FlagStr = "FAIL";
//        }      
//    }
String busiName="grpClaimSuoPei";
BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();

if(!tBusinessDelegate.submitData(tVData,"",busiName))
    {    
        if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
        { 
        	int n = tBusinessDelegate.getCErrors().getErrorCount();
	        for (int i = 0; i < n; i++)
	        {
	            //loggerDebug("LLPRTCertificatePutOutSaveNew","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
	            strErrInfo = strErrInfo + "原因是: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
	        }
       		FlagStr = "Fail";				   
		}
		else
		{
		   FlagStr = "Fail";				
		} 
}

    else{
             int printtimes=myManagePrtBill.getMAXPrtTimes(RptNo,20)+1;
             //写文件日志
             String tmpsql="insert into print values('"+RptNo+"',"+43+",'索赔资料',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
//             loggerDebug("LLPRTCertificatePutOutSaveNew","写print:"+tmpsql);
             
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
    %>
<html>
<script language="javascript">
var FlagStr='<%=FlagStr%>';
var content='<%=strErrInfo%>';
if (FlagStr == "FAIL" )
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

  	    //top.close();
    }
</script>
</html>

