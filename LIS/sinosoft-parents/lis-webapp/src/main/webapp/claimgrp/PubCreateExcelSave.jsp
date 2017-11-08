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
   //区站
   String OPManageCom=tG.ComCode;
   //操作员
   String OPerator=tG.Operator;
   //客户端IP
   String clientIP=request.getRemoteAddr();
   //客户端的机器名
   String RemoteHostName=request.getRemoteHost();
   
   //接收的参数
   String tSQL = request.getParameter("tSQL");
   String Title = request.getParameter("Title");
   String ColName = request.getParameter("ColName");
   String SheetName = request.getParameter("SheetName");
   
   //System.out.println("true ot false"+StrTool.isUnicodeString(ColName));
   ColName=StrTool.unicodeToGBK(ColName);
   System.out.println("ColName:"+ColName);
    //获取realpath
       String RealPath = application.getRealPath("/").replace('\\','/');
   
   
   
   
       TransferData mTransferData = new TransferData(); //传入页面信息
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
            strErrInfo="生成Excel文件失败！";
            flag="Flag";  
        }
//String busiName="grpPublicCreateExcel";
//BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
//if(!tBusinessDelegate.submitData(tVData,"",busiName))
//{    
//    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
//    { 
//    	strErrInfo="生成Excel文件失败！";
//		//Content = "生成Excel文件失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
//		flag="Flag";  
//	}
//	else
//	{
//		//Content = "生成Excel文件失败";
//		strErrInfo="生成Excel文件失败！";
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
        strErrInfo="生成excel文件出错！"+e1.getMessage();
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
    
	    content = "操作成功!";
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

<body>
</body>
</html>
