<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%
  loggerDebug("HospitalQualityQuerySave1","****��ʼִ�д�ӡ����");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  try
  {
    String mOperate = "PRINT";  //�����̨�Ĳ�������
    //��ʼ��ȫ�ֱ�������ǰ̨�н�����
    String StartDate=request.getParameter("StartDate");             //��ʼ����
    String EndDate=request.getParameter("EndDate");                 //��������
    String AppSalechnl=request.getParameter("AppSalechnl");                 //��������
    String HQReportType = request.getParameter("HQReportType");         //ͳ�Ʊ�������
    
    loggerDebug("HospitalQualityQuerySave1","****��ʼ����: "+StartDate); 
    loggerDebug("HospitalQualityQuerySave1","****��������: "+EndDate);   
    loggerDebug("HospitalQualityQuerySave1","****ͳ�Ʊ�������: "+HQReportType);          
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    
    HospitalQualityQuery1UI tHospitalQualityQuery1UI = new HospitalQualityQuery1UI();
    
    VData tVData = new VData();
    VData mResult = new VData();

    tVData.addElement(StartDate);
    tVData.addElement(EndDate);
    tVData.addElement(AppSalechnl);
    tVData.addElement(HQReportType);
    tVData.addElement(tG);
    loggerDebug("HospitalQualityQuerySave1","Start PolAppStatUI...");      
    if(!tHospitalQualityQuery1UI.submitData(tVData,mOperate))
    {
      FlagStr="Fail";
      Content = " ����ʧ�ܣ�ԭ����:" +tHospitalQualityQuery1UI.mErrors.getFirstError();
    }  
    else
    { 
    mResult = tHospitalQualityQuery1UI.getResult();
    XmlExport txmlExport = new XmlExport();
    txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
    if (txmlExport==null)
    {
      loggerDebug("HospitalQualityQuerySave1","null");
      tError = tHospitalQualityQuery1UI.mErrors;
      Content = "��ӡʧ��,ԭ����û����Ҫ��ӡ��������Ϣ��";
      FlagStr = "Fail";
    }
    else
    {
  	   session.putValue("PrintStream", txmlExport.getInputStream());
  	   loggerDebug("HospitalQualityQuerySave1","put session value");
  	   response.sendRedirect("../f1print/GetF1Print.jsp");
    }
   }
  }
  catch(Exception ex)
  {
    Content = "PRINT"+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  %>
  <html>
  <%if("Fail".equals(FlagStr))
  {
  %>
  <script language="javascript">
	alert("<%=Content%>");    
	window.close();
   </script>
   <%
   }
   %>
</html>
