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
    String ManageType = request.getParameter("UWManageType");         //�����������    
    String UWSalechnl = request.getParameter("UWSalechnl");     //������������ 
    String UWReportType = request.getParameter("UWReportType");         //ͳ�Ʊ�������
    
    System.out.println("****��ʼ����: "+StartDate); 
    System.out.println("****��������: "+EndDate);   
    System.out.println("****��������: "+ManageType);  
    System.out.println("****������������: "+UWSalechnl);
    System.out.println("****ͳ�Ʊ�������: "+UWReportType);          
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI");
    
    PolUWFlowQueryUI tPolUWFlowQueryUI = new PolUWFlowQueryUI();
    
    VData tVData = new VData();
    VData mResult = new VData();

    tVData.addElement(StartDate);
    tVData.addElement(EndDate);
    tVData.addElement(ManageType);
    tVData.addElement(UWSalechnl);
    tVData.addElement(UWReportType);
    tVData.addElement(tG);
    System.out.println("Start PolAppStatUI...");      
    if(!tPolUWFlowQueryUI.submitData(tVData,mOperate))
    {
      FlagStr="Fail";
      Content = " ����ʧ�ܣ�ԭ����:" +tPolUWFlowQueryUI.mErrors.getFirstError();
    }  
    else
    { 
    mResult = tPolUWFlowQueryUI.getResult();
    XmlExport txmlExport = new XmlExport();
    txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
    if (txmlExport==null)
    {
      System.out.println("null");
      tError = tPolUWFlowQueryUI.mErrors;
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