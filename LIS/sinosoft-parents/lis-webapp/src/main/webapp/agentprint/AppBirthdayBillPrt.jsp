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
int tType = -1;      //��������

GlobalInput tG = new GlobalInput();
tG = (GlobalInput)session.getValue("GI");
CError cError = new CError( );
CErrors tError = null;
//����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��	  
String  FlagStr = "sucss";
String  Content = "����ɹ���";
tManageCom=request.getParameter("ManageCom");
tSaleChnl=request.getParameter("SaleChnl");
tPayIntv=request.getParameter("PayIntv");
tPolType=request.getParameter("PolType");
tStartDate=request.getParameter("StartDate");
tEndDate =request.getParameter("EndDate"); 

//��ȡģ��·��
  String templatepath = "";
  LDSysVarDB tLDSysVarDB2 = new LDSysVarDB();
  tLDSysVarDB2.setSysVar("XSEXCelTemplate");
  if (!tLDSysVarDB2.getInfo()) 
  {
  	loggerDebug("AppBirthdayBillPrt","��ȡģ��·��ʧ��");
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
   
   
String CurrentDate = PubFun.getCurrentDate();  //���ϵͳʱ�� 
String action = request.getParameter("fmtransact"); //��ñ�־�����ж������ɻ�������
String url=request.getParameter("Url");
String filename=request.getParameter("FileName");
File tempFile= new File(url+filename);
int flag =0;
loggerDebug("AppBirthdayBillPrt",url+filename);
if (action.equals("download")) 
{
  loggerDebug("AppBirthdayBillPrt","66666666666");
   if(!tempFile.exists())       //�жϸ��ļ��Ƿ����
     {
	      loggerDebug("AppBirthdayBillPrt","NO");
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
   loggerDebug("AppBirthdayBillPrt","111111111111111111111111");
   
	 if(tempFile.exists())
    {
	      loggerDebug("AppBirthdayBillPrt","NO");
	      Content = " �ñ����Ѿ����ڣ���ֱ�����أ� ";   
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
				 Content = "ʧ�ܣ�ԭ����:" + ex.toString();
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


