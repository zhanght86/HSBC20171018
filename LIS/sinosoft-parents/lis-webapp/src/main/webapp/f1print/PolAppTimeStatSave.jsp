<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�PolAppStatSave.jsp
//�����ܣ��б�����ͳ��
//�������ڣ�2007-04-05 15:01
//������  ��Fuqx
%>
<!--�û�У����-->

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%
  System.out.println("****��ʼִ�д�ӡ����");
  String Content = "";
  CErrors tError = null;
  String FlagStr = "Fail";
  try
  {
    String mOperate = "PRINT";  //�����̨�Ĳ�������
    //��ʼ��ȫ�ֱ�������ǰ̨�н�����
    String StartDate=request.getParameter("StartDate");             //��ʼ����
    String EndDate=request.getParameter("EndDate");                 //��������
    String ReportType = request.getParameter("AppReportType");         //ͳ�Ʊ�������
    String StartTime=request.getParameter("StartTime");             //��ʼʱ��
    String EndTime=request.getParameter("EndTime");                 //����ʱ��
    
    System.out.println("****��ʼ����: "+StartDate); 
    System.out.println("****��������: "+EndDate);
    System.out.println("****��ʼʱ��: "+StartTime); 
    System.out.println("****����ʱ��: "+EndTime); 
    System.out.println("****ͳ�Ʊ�������: "+ReportType); 
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    
    PolAppTimeStatUI tPolAppTimeStatUI = new PolAppTimeStatUI();
    
    VData tVData = new VData();
    VData mResult = new VData();

    tVData.addElement(StartDate);
    tVData.addElement(EndDate);
    tVData.addElement(ReportType);
    tVData.addElement(StartTime);
    tVData.addElement(EndTime);
    tVData.addElement(tG);
    
    System.out.println("Start PolAppTimeStatUI...");      
    if(!tPolAppTimeStatUI.submitData(tVData,mOperate))
    {
      FlagStr="Fail";
      Content = " ����ʧ�ܣ�ԭ����:" +tPolAppTimeStatUI.mErrors.getFirstError();
    }  
    else
    { 
    mResult = tPolAppTimeStatUI.getResult();
    XmlExport txmlExport = new XmlExport();
    txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
    if (txmlExport==null)
    {
      System.out.println("null");
      tError = tPolAppTimeStatUI.mErrors;
      Content = "��ӡʧ��,ԭ����û����Ҫ��ӡ��������Ϣ��";
      FlagStr = "Fail";
    }
    else
    {
  	   session.putValue("PrintStream", txmlExport.getInputStream());
  	   System.out.println("put session value");
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