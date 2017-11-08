<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.agentprint.*"%>
<%@page import="java.io.*"%>
<%
	loggerDebug("EdorOutDayDoneSave","start EdorOutDayDoneSave.jsp");
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	CError cError = new CError( );
	//后面要执行的动作：添加，修改，删除	  
	String FlagStr = "";
	String Content = "";
    String tManageCom = request.getParameter("ManageCom");
    String tSaleChnl = request.getParameter("SaleChnl");
    String tStartDate = request.getParameter("StartDate");
    String tEndDate = request.getParameter("EndDate");
	//得到模板路径的描述.
    LDSysVarDB tLDSysVarDB =  new LDSysVarDB();;

    tLDSysVarDB.setSysVar("XSEXCelTemplate");
   if (tLDSysVarDB.getInfo() == false)
   {
    loggerDebug("EdorOutDayDoneSave","LDSysVar取文件路径XSEXCelTemplate描述失败");
   
   }
    String mFilePath = tLDSysVarDB.getSysVarValue();
   String TemplatePath = application.getRealPath(mFilePath)+"/";

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
	tVData.addElement(tStartDate);
	tVData.addElement(tEndDate);
	tVData.addElement(tManageCom);
	tVData.addElement(tSaleChnl);
    tVData.addElement(tG);
    tVData.addElement(TemplatePath);

	CErrors tError = null;
	
	String strErrMsg = "";
	
	
    EdorOutDayDoneUI  tPrt = new EdorOutDayDoneUI ();
    

    
	int tDelType = -1;      //处理类型

  String Flag="";  
   
  String action = request.getParameter("fmtransact");
  loggerDebug("EdorOutDayDoneSave","action = " + action);
  
  String myconfirm = request.getParameter("myconfirm");
	
  if (action.equals("download")) {
	String url=request.getParameter("Url");
	String filename=request.getParameter("FileName");
	File tempFile= new File(url+filename);
	loggerDebug("EdorOutDayDoneSave",url+filename);
    	if(!tempFile.exists())
    	{
    	    loggerDebug("EdorOutDayDoneSave","NO");
    	    Flag="Fail";
            Content = " 不存在你要下载的文件！请先生成再下载！ ";   
            tDelType=1;
    	}
    	else
    	{
            tDelType = 0;
        }
	} else {
	
	String url=request.getParameter("Url");
	String filename=request.getParameter("FileName");
	File tempFile= new File(url+filename);
	loggerDebug("EdorOutDayDoneSave","url+filename["+(url+filename)+"]");
	
	loggerDebug("EdorOutDayDoneSave","exists["+tempFile.exists()+"]");
	loggerDebug("EdorOutDayDoneSave","myconfirm["+myconfirm+"]");
	//myconfirm 起到一个开关的作用
	if (tempFile.exists() && !myconfirm.equals("1"))
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
        if(!tPrt.submitData(tVData,"Create")) {
            Flag="Fail";
           Content = " 没有符合条件的数据！！";
		if( tPrt.mErrors.needDealError() ) {
			strErrMsg = tPrt.mErrors.getFirstError();
		} else {
			strErrMsg = "AgentNewContactReport发生错误，但是没有提供详细的出错信息";
		}
  }
        else 
        { Flag="Success";
           Content = " 处理成功 ";
        }
        loggerDebug("EdorOutDayDoneSave",Content + "\n" + Flag + "\n---生成完毕 End---\n\n");
    }
  }
%>
 
<html>
<script language="javascript"> 
  <!--alert('<%=tDelType%>');-->
	if (<%=tDelType%> == 1) {
		 <!--alert('<%=Content%>');-->
		 <!--将document.all('myconfirm').value 重新置为初始状态 "0"	起到开关作用	 --> 
		 parent.fraInterface.document.all('myconfirm').value = '0';
		 parent.fraInterface.afterSubmit("<%=Flag%>","<%=Content%>");
	} else {
	  <!-- tDelType = 0; -->
	  <!-- 计算界面不允许出现下载 -->
	  if (<%=tDelType%> != -1)
	  parent.fraInterface.downAfterSubmit(parent.fraInterface.fm.Url.value,parent.fraInterface.fm.FileName.value );
	}
</script>
</html>
