


<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.agentprint.*"%>
<%@page import="java.io.*"%>


<%
    loggerDebug("AdvancePremPrt","start");
	String tManageCom="";
    String tComLevel="";
	String tdate="";
	String tBranchType="";
	String tGetType="";
	 int tType = -1;      //处理类型

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	CError cError = new CError( );
	CErrors tError = null;
	//后面要执行的动作：添加，修改，删除	  
	String  FlagStr = "sucss";
	String  Content = "一切处理完毕，欢迎使用";
	tManageCom=request.getParameter("ManageCom");
    tComLevel=request.getParameter("ComLevel");
	tdate=request.getParameter("tdate");
	tBranchType =request.getParameter("BranchType"); 
	tGetType = request.getParameter("GetType");
	//获取模板路径
	String templatepath = "";
    LDSysVarDB tLDSysVarDB2 = new LDSysVarDB();
    tLDSysVarDB2.setSysVar("XSEXCelTemplate");
    if (!tLDSysVarDB2.getInfo()) 
    {
    	loggerDebug("AdvancePremPrt","获取模板路径失败");
    }
    templatepath = tLDSysVarDB2.getSysVarValue();
	String strTemplatePath = application.getRealPath(templatepath)+"/";
	//loggerDebug("AdvancePremPrt","66666666666");
	
	VData tVData = new VData();

	CErrors mErrors = new CErrors();
	tVData.addElement(tManageCom);
    tVData.addElement(tComLevel);
    tVData.addElement(tdate);	
    tVData.addElement(tBranchType);	
    tVData.addElement(strTemplatePath);	
    
    tVData.addElement(tG);
	   
	   
 String CurrentDate = PubFun.getCurrentDate();  //获得系统时间 
 String action = request.getParameter("fmtransact"); //获得标志用以判断是生成还是下载
 String url=request.getParameter("Url");
 String filename=request.getParameter("FileName");
 File tempFile= new File(url+filename);
  int flag =0;
  loggerDebug("AdvancePremPrt",url+filename);
  if (action.equals("download")) 
	{
	  loggerDebug("AdvancePremPrt","66666666666");
	   if(!tempFile.exists())       //判断该文件是否存在
	     {
		      loggerDebug("AdvancePremPrt","NO");
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
	   loggerDebug("AdvancePremPrt","111111111111111111111111");
	   loggerDebug("AdvancePremPrt","tdate:"+tdate);
	   loggerDebug("AdvancePremPrt","CurrentDate:"+CurrentDate);
		
 
	//loggerDebug("AdvancePremPrt","tdate"+tdate);
	//loggerDebug("AdvancePremPrt","tBranchType"+tBranchType);
   
	if(flag==0){
	AdvancePremQueryUI tAdvancePremQueryUI = new AdvancePremQueryUI();
    try{
    	if(!tAdvancePremQueryUI.submitData(tVData,""))
    	{
    	     //loggerDebug("AdvancePremPrt","11111111@@@@@@@@@@@@@$$$$$$$$$$");
             mErrors.copyAllErrors(tAdvancePremQueryUI.mErrors);
      	     cError.moduleName = "ReAdvancePremPrt.jsp";
             cError.functionName = "submitData";
             cError.errorMessage = "tReAdvancePremQueryUI";
             mErrors.addOneError(cError);          }
    	}
  catch(Exception ex)
	{
	ex.printStackTrace();
		Content = "失败，原因是:" + ex.toString();
  		FlagStr = "Fail";
}

tError = tAdvancePremQueryUI.mErrors;}
	}

//loggerDebug("AdvancePremPrt","haoaolo");
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


