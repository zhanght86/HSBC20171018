<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.agentprint.*"%>
<%@page import="java.io.*"%>



<%
loggerDebug("AppBirthdayBillPrt","start");
String tManageCom="";
String tSaleChnl="";
String tPayIntv="";
String tPolType="";
String tStartDate="";
String tEndDate="";
int tType = -1;      //处理类型

GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");
CError cError = new CError( );
CErrors tError = null;
//后面要执行的动作：添加，修改，删除	  
String  FlagStr = "sucss";
String  Content = "处理成功！";
tManageCom=request.getParameter("ManageCom");
tSaleChnl=request.getParameter("SaleChnl");
tPayIntv=request.getParameter("PayIntv");
tPolType=request.getParameter("PolType");
tStartDate=request.getParameter("StartDate");
tEndDate =request.getParameter("EndDate"); 

//获取模板路径
  String templatepath = "";
  LDSysVarDB tLDSysVarDB2 = new LDSysVarDB();
  tLDSysVarDB2.setSysVar("XSEXCelTemplate");
  if (!tLDSysVarDB2.getInfo()) 
  {
  	loggerDebug("AppBirthdayBillPrt","获取模板路径失败");
  }
  templatepath = tLDSysVarDB2.getSysVarValue();
  String strTemplatePath = application.getRealPath(templatepath)+"/";
//loggerDebug("AppBirthdayBillPrt","66666666666");

VData tVData = new VData();

CErrors mErrors = new CErrors();
tVData.addElement(tManageCom);
tVData.addElement(tSaleChnl);
tVData.addElement(tPayIntv);
tVData.addElement(tPolType);
tVData.addElement(tStartDate);	
tVData.addElement(tEndDate);	
tVData.addElement(strTemplatePath);	
tVData.addElement(tG);
   
   
String CurrentDate = PubFun.getCurrentDate();  //获得系统时间 
String action = request.getParameter("fmtransact"); //获得标志用以判断是生成还是下载
String url=request.getParameter("Url");
String filename=request.getParameter("FileName");
File tempFile= new File(url+filename);
int flag =0;
loggerDebug("AppBirthdayBillPrt",url+filename);
if (action.equals("download")) 
{
  loggerDebug("AppBirthdayBillPrt","66666666666");
   if(!tempFile.exists())       //判断该文件是否存在
     {
	      loggerDebug("AppBirthdayBillPrt","NO");
	      FlagStr="Fail";
	      tType=0;
	      Content = " 不存在你要下载的文件！请先生成再下载！ ";   
	
     }
   else{ 
	   
	   tType=1;
	   
	   Content="正在下载，请稍后...";
   
     
     
     
   }
}
else  
{
   
   tType=0;
   loggerDebug("AppBirthdayBillPrt","111111111111111111111111");
   
	 if(tempFile.exists())
    {
	      loggerDebug("AppBirthdayBillPrt","NO");
	      Content = " 该报表已经存在，请直接下载！ ";   
	      flag=1;
    }
	  
	     
	 if(flag==0)
	  {
	    AppBirthdayBillUI tAppBirthdayBillUI = new AppBirthdayBillUI();
		  try
		  {
		    	if(!tAppBirthdayBillUI.submitData(tVData,""))
		    	 {
		    	     //loggerDebug("AppBirthdayBillPrt","11111111@@@@@@@@@@@@@$$$$$$$$$$");
		             mErrors.copyAllErrors(tAppBirthdayBillUI.mErrors);
		      	     cError.moduleName = "XAdvanceAppPrt.jsp";
		             cError.functionName = "submitData";
		             cError.errorMessage = "tAppBirthdayBillUI";
		             mErrors.addOneError(cError);          
		       }
		   }
		  catch(Exception ex)
			{
			   ex.printStackTrace();
				 Content = "失败，原因是:" + ex.toString();
		  	 FlagStr = "Fail";
		  }

      tError = tAppBirthdayBillUI.mErrors;}
     
}

//loggerDebug("AppBirthdayBillPrt","haoaolo");
%>
<html>
<script language="javascript">
//alert("3333333333333333");
    if(<%=tType %>==0)
		{parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");}
    else 
    {
     parent.fraInterface.downAfterSubmit(parent.fraInterface.fm.Url.value,parent.fraInterface.fm.FileName.value );
     }		
</script>
</html>


