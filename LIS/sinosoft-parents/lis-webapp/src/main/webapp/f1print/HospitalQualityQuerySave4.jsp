<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%
  loggerDebug("HospitalQualityQuerySave4","****��ʼִ�д�ӡ����");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  try
  {
    String mOperate = "PRINT";  //�����̨�Ĳ�������
    //��ʼ��ȫ�ֱ�������ǰ̨�н�����
    String StartDate=request.getParameter("StartDate");             //��ʼ����
    String EndDate=request.getParameter("EndDate");                 //��������
    String ManageCom = request.getParameter("ManageCom");         //ͳ�ƻ���
    String HQReportType = request.getParameter("HQReportType");         //ͳ�Ʊ�������
    
    loggerDebug("HospitalQualityQuerySave4","****��ʼ����: "+StartDate); 
    loggerDebug("HospitalQualityQuerySave4","****��������: "+EndDate);   
    loggerDebug("HospitalQualityQuerySave4","****ͳ�Ʊ�������: "+HQReportType);          
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    
    HospitalQualityQuery4UI tHospitalQualityQuery4UI = new HospitalQualityQuery4UI();
    
    VData tVData = new VData();
    VData mResult = new VData();

    tVData.addElement(StartDate);
    tVData.addElement(EndDate);
     tVData.addElement(ManageCom);
    tVData.addElement(HQReportType);
    tVData.addElement(tG);
    loggerDebug("HospitalQualityQuerySave4","Start PolAppStatUI...");      
    if(!tHospitalQualityQuery4UI.submitData(tVData,mOperate))
    {
      FlagStr="Fail";
      Content = " ����ʧ�ܣ�ԭ����:" +tHospitalQualityQuery4UI.mErrors.getFirstError();
    }  
    else
    { 
    mResult = tHospitalQualityQuery4UI.getResult();
    XmlExport txmlExport = new XmlExport();
    txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
    if (txmlExport==null)
    {
      loggerDebug("HospitalQualityQuerySave4","null");
      tError = tHospitalQualityQuery4UI.mErrors;
      Content = "��ӡʧ��,ԭ����û����Ҫ��ӡ��������Ϣ��";
      FlagStr = "Fail";
    }
    else
    {
  	   session.putValue("PrintStream", txmlExport.getInputStream());
  	   loggerDebug("HospitalQualityQuerySave4","put session value");
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
