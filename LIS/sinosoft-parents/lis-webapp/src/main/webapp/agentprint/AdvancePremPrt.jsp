


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
	 int tType = -1;      //��������

	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");
	CError cError = new CError( );
	CErrors tError = null;
	//����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��	  
	String  FlagStr = "sucss";
	String  Content = "һ�д�����ϣ���ӭʹ��";
	tManageCom=request.getParameter("ManageCom");
    tComLevel=request.getParameter("ComLevel");
	tdate=request.getParameter("tdate");
	tBranchType =request.getParameter("BranchType"); 
	tGetType = request.getParameter("GetType");
	//��ȡģ��·��
	String templatepath = "";
    LDSysVarDB tLDSysVarDB2 = new LDSysVarDB();
    tLDSysVarDB2.setSysVar("XSEXCelTemplate");
    if (!tLDSysVarDB2.getInfo()) 
    {
    	loggerDebug("AdvancePremPrt","��ȡģ��·��ʧ��");
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
	   
	   
 String CurrentDate = PubFun.getCurrentDate();  //���ϵͳʱ�� 
 String action = request.getParameter("fmtransact"); //��ñ�־�����ж������ɻ�������
 String url=request.getParameter("Url");
 String filename=request.getParameter("FileName");
 File tempFile= new File(url+filename);
  int flag =0;
  loggerDebug("AdvancePremPrt",url+filename);
  if (action.equals("download")) 
	{
	  loggerDebug("AdvancePremPrt","66666666666");
	   if(!tempFile.exists())       //�жϸ��ļ��Ƿ����
	     {
		      loggerDebug("AdvancePremPrt","NO");
		      FlagStr="Fail";
		      tType=0;
		      Content = " ��������Ҫ���ص��ļ����������������أ� ";   
		
	     }
	   else{ 
		   
		   tType=1;
		   
		   Content="�������أ����Ժ�...";
       
	     
	     
	     
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
		Content = "ʧ�ܣ�ԭ����:" + ex.toString();
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


