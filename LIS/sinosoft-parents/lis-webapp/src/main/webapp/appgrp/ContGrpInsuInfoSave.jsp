<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.tbgrp.*"%>
<%@page import="java.io.*"%>
<%
	loggerDebug("ContGrpInsuInfoSave","start ContGrpInsuInfo.jsp");
	//String tStartDay="";
	//String tEndDay="";
	//String tCalManageCom="";
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	CError cError = new CError( );
	//后面要执行的动作：添加，修改，删除	  
	String FlagStr = "";
	String Content = "";
	String tGrppolno=request.getParameter("ProposalGrpContNo");
	String ManageCom=request.getParameter("ManageCom");
	loggerDebug("ContGrpInsuInfoSave","传入的管理机构是:  "+request.getParameter("ManageCom"));
	String tInsuredNo=request.getParameter("InsuredNo");
	String tName=request.getParameter("Name");
	String tIDNo=request.getParameter("IDNo");
	String tContPlanCode=request.getParameter("ContPlanCode");
	String url=request.getParameter("Url");
	String filename=request.getParameter("FileName");
	String mFinalPath = "";
	String mStrRealPath = application.getRealPath("/").replace('\\', '/');
	mFinalPath = mStrRealPath + "" + url + "" + filename;
	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tGrppolno);
	tVData.addElement(ManageCom);
	tVData.addElement(tInsuredNo);
	tVData.addElement(tName);
	tVData.addElement(tIDNo);
	tVData.addElement(tContPlanCode);
	tVData.addElement(mFinalPath);
	tVData.addElement(mStrRealPath);
	//tVData.addElement(tStartDay);
	//tVData.addElement(tEndDay);
	//tVData.addElement(tCalManageCom);
	//tVData.addElement(AgentA00);
    tVData.addElement(tG);

	CErrors tError = null;
	
	String strErrMsg = "";
	
	
	ContGrpInsuredExcelUI  tSExcel = new ContGrpInsuredExcelUI ();
    

    
	int tDelType = -1;      //处理类型

  String Flag="";  
   
  String action = request.getParameter("fmtransact");
  loggerDebug("ContGrpInsuInfoSave","action = " + action);
  
  String myconfirm = request.getParameter("myconfirm");

  
  
  if (action.equals("download")) {
	
	
//	File tempFile= new File(mFinalPath);
File tempFile= new File(mFinalPath);
	loggerDebug("ContGrpInsuInfoSave",mFinalPath);
    	if(!tempFile.exists())
    	{
    	    loggerDebug("ContGrpInsuInfoSave","NO");
    	    Flag="Fail";
            Content = " 不存在你要下载的文件！请先生成再下载！ ";   
            tDelType=1;
    	}
    	else
    	{
            tDelType = 0;
        }
	} else {
	loggerDebug("ContGrpInsuInfoSave","生成Excel文件!");
	//String url=request.getParameter("Url");
	//String filename=request.getParameter("FileName");
	File tempFile= new File(mFinalPath);
	loggerDebug("ContGrpInsuInfoSave","url+filename["+(mFinalPath)+"]");
	
	loggerDebug("ContGrpInsuInfoSave","exists["+tempFile.exists()+"]");
	loggerDebug("ContGrpInsuInfoSave","myconfirm["+myconfirm+"]");
	//myconfirm 起到一个开关的作用
	if (tempFile.exists()&&!myconfirm.equals("1") )
  {
  //下面的这段JavaScript代码回调ConfirmSelect并再次提交
	%>
		<script language="javascript">
	   parent.fraInterface.ConfirmSelect();
		</script>
  <%
	}
	else {
        //删除该文件 文件不存在或文件存在且需要重新计算时走此分支
        //comment by jiaqiangli 2007-07-06 校验不通过之后删除了文件
        //tempFile.delete();
        tDelType = 1;
        if(!tSExcel.submitData(tVData,"Create")) {
        Flag="Success";
           Content = " 处理成功 ";
           
		if( tSExcel.mErrors.needDealError() ) {
			strErrMsg = tSExcel.mErrors.getFirstError();
		} else {
			strErrMsg = "AgentNewContactReport发生错误，但是没有提供详细的出错信息";
		}
		return;
  }
        else 
        { Flag="Success";
           Content = " 处理成功 ";
        }
        loggerDebug("ContGrpInsuInfoSave",Content + "\n" + Flag + "\n---生成完毕 End---\n\n");
    }
  }
%>
 
<html>
<script language="javascript"> 
  <!--alert('<%=tDelType%>');-->
	if (<%=tDelType%> == 1) {
		 <!--alert('<%=Content%>');-->
		 <!--将fm.all('myconfirm').value 重新置为初始状态 "0"	起到开关作用	 --> 
		 parent.fraInterface.fm.all('myconfirm').value = '0';
		 parent.fraInterface.afterSubmit("<%=Flag%>","<%=Content%>");
	} else {
	  <!-- tDelType = 0; -->
	  <!-- 计算界面不允许出现下载 -->
	  if (<%=tDelType%> != -1)
	  parent.fraInterface.downAfterSubmit('<%=mFinalPath%>',parent.fraInterface.fm.FileName.value );
	}
</script>
</html>
