<%
//程序名称:承保报表打印
//程序功能：
//创建日期：2003-10-15
//创建人  ：guoxiang
//更新记录：  更新人    更新日期     更新原因/内容
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
    //管理机构
    String mManageCom ="";
    mManageCom =request.getParameter("ManageCom");
    if(mManageCom==null) mManageCom="";
    // 个人工号
    String UserCode ="";
    UserCode =request.getParameter("UserCode");
    if(UserCode==null) UserCode="";
    //时间
    String mDay[]=new String[2];
    mDay[0]=request.getParameter("StartTime");
    mDay[1]=request.getParameter("EndTime");

    //操作
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
      cError.errorMessage = "WorkLoadUI发生错误，但是没有提供详细的出错信息";
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