<%
//��������:�б������ӡ
//�����ܣ�
//�������ڣ�2008-1-24
//������  ��liuqh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@include file="../../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%

	boolean operFlag=true;
	String FlagStr = "";
	String Content = "";
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");

    //��������
    String Code = request.getParameter("Code");
    loggerDebug("AppF1Print","�������ʹ���:"+Code);
    //����
    String mRiskCode="";
    //mRiskCode=request.getParameter("RiskCode");
    if(mRiskCode==null) mRiskCode="";
    //���ձ�־
    String mRiskFlag="";
    //mRiskFlag=request.getParameter("RiskFlag");
    if(mRiskFlag==null) mRiskFlag="";
    //ʱ��
    
    String tStartDate=request.getParameter("StartTime");
    String tEndDate=request.getParameter("EndTime");
    String tManageCom =request.getParameter("ManageCom");
    //����
    String gi="";
    //gi=request.getParameter("StationCode");
    //����
    String UserCode=request.getParameter("UserCode");
    String OperaterNo = request.getParameter("OperaterNo");
    loggerDebug("AppF1Print","UserCode:"+UserCode);
    loggerDebug("AppF1Print","OperaterNo:"+OperaterNo);
    String strOperation ="PRINT";
    VData tVData = new VData();
    VData mResult = new VData();
    TransferData tTransferData = new TransferData(); 
    tTransferData.setNameAndValue("ManageCom",tManageCom);                                                                                                                    
    tTransferData.setNameAndValue("StartDate",tStartDate); 
    tTransferData.setNameAndValue("EndDate",tEndDate);     
    tTransferData.setNameAndValue("OperatorNo",OperaterNo);     
    //tTransferData.setNameAndValue("PrtNo",tPrtNo);     
    //tTransferData.setNameAndValue("InsuredName",tInsuredName);     
    //tTransferData.setNameAndValue("RiskCode",tRiskCode);     
    tTransferData.setNameAndValue("UserCode",UserCode);     
    //tTransferData.setNameAndValue("AppCode","app2");     
    //tTransferData.setNameAndValue("PrintDate",tPrintDate);
    tVData.addElement(tTransferData);  
    //tVData.add(tG);
    //tVData.addElement(mRiskCode);
    //tVData.addElement(mRiskFlag);
    //tVData.addElement(gi);
    //tVData.addElement(UserCode);
    //��ߺ˱�ʦ�ܲ�ѯ��������˱�ʦ�ĺ˱�ͳ��
    String tStatisticType = "";
    if(Code.equals("app1"))
    {
    	tStatisticType = request.getParameter("StatisticType");
    	tTransferData.setNameAndValue("AppCode","app1");
    	tVData.addElement(tStatisticType);
    }else if(Code.equals("app2"))
    {
    	UserCode=tG.Operator;
    	tTransferData.setNameAndValue("AppCode","app2");
    	//tVData.addElement(UserCode);
    }
    tVData.addElement(tG);  
    loggerDebug("AppF1Print","tVData:"+tVData.size());
    CError cError = new CError( );
    CErrors mErrors = new CErrors();
    XmlExport txmlExport = new XmlExport();
    loggerDebug("AppF1Print","uwstart");
    AppCheckUI tAppCheckUI = new AppCheckUI();
    if(!tAppCheckUI.submitData(tVData,strOperation)){
      mErrors.copyAllErrors(tAppCheckUI.mErrors);
      cError.moduleName = "ClaimReportF1Print";
      cError.functionName = "submitData";
      cError.errorMessage = "AppCheckUI�������󣬵���û���ṩ��ϸ�ĳ�����Ϣ";
      mErrors.addOneError(cError);
    }
    mResult = tAppCheckUI.getResult();
    txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
    try{
    if (txmlExport==null){
    	operFlag=false;
  		Content="û�еõ�Ҫ��ʾ�������ļ�";
       //return;
    }
    if(operFlag==true){
    session.putValue("PrintStream", txmlExport.getInputStream());
%> 
<script language="javascript">
    window.open("../f1print/GetF1Print.jsp"); 
    window.close();
</script>
<%
    }else{
 %>   	
 <script language="javascript">
        alert("<%=Content%>");
	    top.close();
    </script>
<%
    }}catch(Exception ex){
    	
    }
%>
    
