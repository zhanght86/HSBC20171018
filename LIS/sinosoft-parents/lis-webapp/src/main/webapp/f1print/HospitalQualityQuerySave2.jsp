<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%
  loggerDebug("HospitalQualityQuerySave2","****��ʼִ�д�ӡ����");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  try
  {
    String mOperate = "PRINT";  //�����̨�Ĳ�������
    //��ʼ��ȫ�ֱ�������ǰ̨�н�����
    String StartDate=request.getParameter("StartDate");             //��ʼ����
    String EndDate=request.getParameter("EndDate");                 //��������
    String AppManageType=request.getParameter("AppManageType");                 //ͳ������
    String HQReportType = request.getParameter("HQReportType");         //ͳ�Ʊ�������
    
    loggerDebug("HospitalQualityQuerySave2","****��ʼ����: "+StartDate); 
    loggerDebug("HospitalQualityQuerySave2","****��������: "+EndDate);   
    loggerDebug("HospitalQualityQuerySave2","****ͳ�Ʊ�������: "+HQReportType);          
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    
    HospitalQualityQuery2UI tHospitalQualityQuery2UI = new HospitalQualityQuery2UI();
    
    VData tVData = new VData();
    VData mResult = new VData();

    tVData.addElement(StartDate);
    tVData.addElement(EndDate);
    tVData.addElement(AppManageType);
    tVData.addElement(HQReportType);
    tVData.addElement(tG);
    loggerDebug("HospitalQualityQuerySave2","Start PolAppStatUI...");      
    if(!tHospitalQualityQuery2UI.submitData(tVData,mOperate))
    {
      FlagStr="Fail";
      Content = " ����ʧ�ܣ�ԭ����:" +tHospitalQualityQuery2UI.mErrors.getFirstError();
    }  
    else
    { 
    mResult = tHospitalQualityQuery2UI.getResult();
    XmlExport txmlExport = new XmlExport();
    txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
    if (txmlExport==null)
    {
      loggerDebug("HospitalQualityQuerySave2","null");
      tError = tHospitalQualityQuery2UI.mErrors;
      Content = "��ӡʧ��,ԭ����û����Ҫ��ӡ��������Ϣ��";
      FlagStr = "Fail";
    }
    else
    {
  	   session.putValue("PrintStream", txmlExport.getInputStream());
  	   loggerDebug("HospitalQualityQuerySave2","put session value");
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
