<%
//��������:�б������ӡ
//�����ܣ�
//�������ڣ�2003-10-15
//������  ��guoxiang
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");    
    //�������
    String mManageCom ="";
    mManageCom =request.getParameter("ManageCom");
    if(mManageCom==null) mManageCom="";
    // ���˹���
    String UserCode ="";
    UserCode =request.getParameter("UserCode");
    if(UserCode==null) UserCode="";
    //ʱ��
    String mDay[]=new String[2];
    mDay[0]=request.getParameter("StartTime");
    mDay[1]=request.getParameter("EndTime");

    //����
    String strOperation ="PRINTPAY";
    VData tVData = new VData();
    VData mResult = new VData();
    tVData.addElement(mDay);
    tVData.addElement(mManageCom);
    tVData.addElement(UserCode);
    tVData.addElement(tG);  
    System.out.println("tVData:"+tVData.size());
    CError cError = new CError( );
    CErrors mErrors = new CErrors();
    XmlExport txmlExport = new XmlExport();
    System.out.println("uwstart");
    WorkLoadUI tWorkLoadUI = new WorkLoadUI();
    if(!tWorkLoadUI.submitData(tVData,strOperation)){
      mErrors.copyAllErrors(tWorkLoadUI.mErrors);
      cError.moduleName = "ClaimReportF1Print";
      cError.functionName = "submitData";
      cError.errorMessage = "WorkLoadUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
      mErrors.addOneError(cError);
    }
    mResult = tWorkLoadUI.getResult();
    txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
    if (txmlExport==null){
       return;
    }
    session.putValue("PrintStream", txmlExport.getInputStream());
%> 
<script language="javascript">
    window.open("../f1print/GetF1Print.jsp"); 
    window.close();
</script>