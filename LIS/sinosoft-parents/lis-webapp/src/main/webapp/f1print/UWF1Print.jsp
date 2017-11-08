<%
//程序名称:承保报表打印
//程序功能：
//创建日期：2003-10-15
//创建人  ：guoxiang
//更新记录：  更新人    更新日期     更新原因/内容
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

    //报表种类
    String Code = request.getParameter("Code");
    System.out.println("报表类型代码:"+Code);
    //险种
    String mRiskCode="";
    mRiskCode=request.getParameter("RiskCode");
    if(mRiskCode==null) mRiskCode="";
    //管理机构
    String mManageCom ="";
    mManageCom =request.getParameter("ManageCom");
    if(mManageCom==null) mManageCom="";
    //主险标志
    String mRiskFlag="";
    mRiskFlag=request.getParameter("RiskFlag");
    if(mRiskFlag==null) mRiskFlag="";
    //时间
    String mDay[]=new String[2];
    mDay[0]=request.getParameter("StartTime");
    mDay[1]=request.getParameter("EndTime");
    //机构
    String gi="";
    gi=request.getParameter("StationCode");
    //保单性质
    String mPolType ="";
    mPolType =request.getParameter("state");
    if(mPolType==null) mPolType ="";
    //销售渠道
    String mSaleChnl="";
    mSaleChnl =request.getParameter("SaleChnl");
    if(mSaleChnl ==null) mSaleChnl ="";
    //操作
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
    //最高核保师能查询其它任意核保师的核保统计
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
      cError.errorMessage = "UWCheckUI发生错误，但是没有提供详细的出错信息";
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