<%
//程序名称:承保报表打印
//程序功能：
//创建日期：2008-1-24
//创建人  ：liuqh
//更新记录：  更新人    更新日期     更新原因/内容
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

    //报表种类
    String Code = request.getParameter("Code");
    loggerDebug("AppF1Print","报表类型代码:"+Code);
    //险种
    String mRiskCode="";
    //mRiskCode=request.getParameter("RiskCode");
    if(mRiskCode==null) mRiskCode="";
    //主险标志
    String mRiskFlag="";
    //mRiskFlag=request.getParameter("RiskFlag");
    if(mRiskFlag==null) mRiskFlag="";
    //时间
    
    String tStartDate=request.getParameter("StartTime");
    String tEndDate=request.getParameter("EndTime");
    String tManageCom =request.getParameter("ManageCom");
    //机构
    String gi="";
    //gi=request.getParameter("StationCode");
    //操作
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
    //最高核保师能查询其它任意核保师的核保统计
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
      cError.errorMessage = "AppCheckUI发生错误，但是没有提供详细的出错信息";
      mErrors.addOneError(cError);
    }
    mResult = tAppCheckUI.getResult();
    txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
    try{
    if (txmlExport==null){
    	operFlag=false;
  		Content="没有得到要显示的数据文件";
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
    
