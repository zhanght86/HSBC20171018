<%
//**************************************************************************************************
//Name��LLPRTCertificatePutOutSaveNew.jsp
//Function�����ⵥ֤֪ͨ���ӡ
//Author��wood
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
	 //׼��ͨ�ò���
    CError cError = new CError();
    String FlagStr = "FAIL";
    String Content = "";
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    
    if(tG == null) 
    {
        loggerDebug("LLPRTCertificatePutOutSaveNew","��¼��Ϣû�л�ȡ!!!");
       return;
     }
    String ManageCom=tG.ComCode;
    String Operator=tG.Operator; 
	 //��ʼ��
    ManagePrtBill myManagePrtBill=new ManagePrtBill();
    //������Ϣ
    String strErrInfo = "";
    //�ͻ���IP
    String ClientIP=request.getRemoteAddr();
    //�ͻ��˻�����
    String ClientHost=request.getRemoteHost();
   //������IP(����д���ǹ̶�ֵ,������д���ˡ�)
   String ServerIP=myManagePrtBill.getServerIP();
    //��ȡrealpath
    String RealPath =application.getRealPath("/").replace('\\','/');

    //ȡsession�еĵ�¼�û������������
     
     String RptNo = request.getParameter("ClmNo");
     String filename="";
     String filename1="";
     filename1=myManagePrtBill.setPrintPath(RptNo,43,ClientIP,".pdf",RealPath);
     filename=RealPath+filename1;
     loggerDebug("LLPRTCertificatePutOutSaveNew","=====filename=======:"+filename+"=========================");
     loggerDebug("LLPRTCertificatePutOutSaveNew","############################");
    //׼������������Ϣ
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("RptNo",RptNo);
    //tTransferData.setNameAndValue("CustNo",request.getParameter("customerNo"));
	  tTransferData.setNameAndValue("flag","1");
	  tTransferData.setNameAndValue("filename",filename);
	  tTransferData.setNameAndValue("RealPath",RealPath);
 
    // ׼���������� VData
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
//            strErrInfo = strErrInfo + "ԭ����: " + tClaimSuoPei.mErrors.getError(i).errorMessage;
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
	            strErrInfo = strErrInfo + "ԭ����: " + tBusinessDelegate.getCErrors().getError(i).errorMessage;
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
             //д�ļ���־
             String tmpsql="insert into print values('"+RptNo+"',"+43+",'��������',"+printtimes+",'"+ManageCom+"','','','"+ClientIP+"','"+ServerIP+"','"+filename+"','"+filename1+"','','','"+Operator+"',sysdate)";
//             loggerDebug("LLPRTCertificatePutOutSaveNew","дprint:"+tmpsql);
             
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
    %>
<html>
<script language="javascript">
var FlagStr='<%=FlagStr%>';
var content='<%=strErrInfo%>';
if (FlagStr == "FAIL" )
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

  	    //top.close();
    }
</script>
</html>

