<%
//��������:�б������ӡ
//�����ܣ�
//�������ڣ�2003-10-15
//������  ��guoxiang
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
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

    //��������
    String Code = request.getParameter("Code");
    System.out.println("�������ʹ���:"+Code);
    //����
    String mRiskCode="";
    mRiskCode=request.getParameter("RiskCode");
    if(mRiskCode==null) mRiskCode="";
    //�������
    String mManageCom ="";
    mManageCom =request.getParameter("ManageCom");
    if(mManageCom==null) mManageCom="";
    //���ձ�־
    String mRiskFlag="";
    mRiskFlag=request.getParameter("RiskFlag");
    if(mRiskFlag==null) mRiskFlag="";
    //ʱ��
    String mDay[]=new String[2];
    mDay[0]=request.getParameter("StartTime");
    mDay[1]=request.getParameter("EndTime");
    //����
    String gi="";
    gi=request.getParameter("StationCode");
    //��������
    String mPolType ="";
    mPolType =request.getParameter("state");
    if(mPolType==null) mPolType ="";
    //��������
    String mSaleChnl="";
    mSaleChnl =request.getParameter("SaleChnl");
    if(mSaleChnl ==null) mSaleChnl ="";
    //����
    String strOperation ="PRINTPAY";
    VData tVData = new VData();
    VData mResult = new VData();
    tVData.addElement(mRiskCode);
    tVData.addElement(mRiskFlag);
    tVData.addElement(mDay);
    tVData.addElement(gi);
    tVData.addElement(Code);
    tVData.addElement(mManageCom);
    tVData.addElement(mPolType);
    tVData.addElement(mSaleChnl);
    //��ߺ˱�ʦ�ܲ�ѯ��������˱�ʦ�ĺ˱�ͳ��
    String UserCode="";
    if(Code.equals("uw12"))
    {
    	UserCode=request.getParameter("UserCode");
    	System.out.println("UserCode:"+UserCode);
    	tVData.addElement(UserCode);
    }else if(Code.equals("uw10"))
    {
    	UserCode=tG.Operator;
    	tVData.addElement(UserCode);
    }
    tVData.addElement(tG);  
    System.out.println("tVData:"+tVData.size());
    CError cError = new CError( );
    CErrors mErrors = new CErrors();
    XmlExport txmlExport = new XmlExport();
    System.out.println("uwstart");
    UWCheckUI tUWCheckUI = new UWCheckUI();
    if(!tUWCheckUI.submitData(tVData,strOperation)){
      mErrors.copyAllErrors(tUWCheckUI.mErrors);
      cError.moduleName = "ClaimReportF1Print";
      cError.functionName = "submitData";
      cError.errorMessage = "UWCheckUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
      mErrors.addOneError(cError);
    }
    mResult = tUWCheckUI.getResult();
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