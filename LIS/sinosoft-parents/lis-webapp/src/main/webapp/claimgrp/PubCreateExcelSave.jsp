<html>
<%@page contentType="text/html; charset=GBK" %>
<%@page import="java.io.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.sinosoft.lis.claimgrp.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.service.*" %>



<%
	String flag="";
	String strErrInfo="";
   GlobalInput tG = (GlobalInput)session.getValue("GI");
   //��վ
   String OPManageCom=tG.ComCode;
   //����Ա
   String OPerator=tG.Operator;
   //�ͻ���IP
   String clientIP=request.getRemoteAddr();
   //�ͻ��˵Ļ�����
   String RemoteHostName=request.getRemoteHost();
   
   //���յĲ���
   String tSQL = request.getParameter("tSQL");
   String Title = request.getParameter("Title");
   String ColName = request.getParameter("ColName");
   String SheetName = request.getParameter("SheetName");
   
   //System.out.println("true ot false"+StrTool.isUnicodeString(ColName));
   ColName=StrTool.unicodeToGBK(ColName);
   System.out.println("ColName:"+ColName);
    //��ȡrealpath
       String RealPath = application.getRealPath("/").replace('\\','/');
   
   
   
   
       TransferData mTransferData = new TransferData(); //����ҳ����Ϣ
       mTransferData.setNameAndValue("Subject",Title)  ;
       mTransferData.setNameAndValue("Title",ColName);
       mTransferData.setNameAndValue("SheetName",SheetName);
       

	java.util.Date myDate=new java.util.Date();
   	String FNDate=String.valueOf(myDate.getTime());
   	String FileName = RealPath+"//temp//other"+clientIP+FNDate+".xls";
        mTransferData.setNameAndValue("FileName",FileName);
        mTransferData.setNameAndValue("WherePart","");
        
             VData tdata=new VData();
             ExeSQL myExeSQL=new ExeSQL();
             SSRS tSSRS=myExeSQL.execSQL(tSQL);
                  tdata.add(mTransferData);
		     tdata.add(tSSRS);

 try
    {
        PublicCreateExcel myPublicCreateExcel=new PublicCreateExcel();
        if (!myPublicCreateExcel.SubmitData(tdata))
        {
            strErrInfo="����Excel�ļ�ʧ�ܣ�";
            flag="Flag";  
        }
//String busiName="grpPublicCreateExcel";
//BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//if(!tBusinessDelegate.submitData(tVData,"",busiName))
//{    
//    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
//    { 
//    	strErrInfo="����Excel�ļ�ʧ�ܣ�";
//		//Content = "����Excel�ļ�ʧ�ܣ�ԭ����:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
//		flag="Flag";  
//	}
//	else
//	{
//		//Content = "����Excel�ļ�ʧ��";
//		strErrInfo="����Excel�ļ�ʧ�ܣ�";
//		flag="Flag";  		
//	}
//}

        else
        {
        	flag="Succ";
        	String mStrRealPath = application.getRealPath("/").replace('\\', '/');
        	System.out.println("mStrRealPath=======>  "+mStrRealPath);
      		//response.sendRedirect(mStrRealPath+"\\temp\\other"+clientIP+FNDate+".xls");
      		//response.sendRedirect(mStrRealPath+"temp\\other"+clientIP+FNDate+".xls");
      		response.sendRedirect("../"+"/temp\\other"+clientIP+FNDate+".xls");

        }
    }catch(Exception e1)
    {
        strErrInfo="����excel�ļ�����"+e1.getMessage();
        flag="Fail";
    }

%>
      <!--p align="center"><font color="blue" size=3"><%=strErrInfo%></font></p-->
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
    
	    content = "�����ɹ�!";
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

<body>
</body>
</html>
