<%
//程序名称:保全报表打印
//程序功能：
//创建日期：2003-11-17
//创建人  ：guoxiang
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
    String busiName="f1printBQCheckUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    
    loggerDebug("BQF1Print","开始执行打印操作");
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");
    //报表种类
    String Code = request.getParameter("Code");
    loggerDebug("BQF1Print","报表类型代码:"+Code);
    //时间
    String mDay[]=new String[2];
    mDay[0]=request.getParameter("StartTime");
    mDay[1]=request.getParameter("EndTime");
    //机构
    String gi="";
    gi=request.getParameter("StationCode");
    //保全项目
    String bqcode="";
    bqcode=request.getParameter("bqcode");
    //操作
    String strOperation ="PRINTPAY";
    //操作员工号
     String opt="";
     opt=request.getParameter("opt");
    
    //
    String tAppOperator=request.getParameter("AppOperator");
    String tConfOperator=request.getParameter("ConfOperator");
    VData tVData = new VData();
    VData mResult = new VData();
    tVData.addElement(mDay);
    tVData.addElement(gi);
    tVData.addElement(bqcode);
    tVData.addElement(Code);
    tVData.addElement(opt);
    tVData.addElement(tAppOperator);
    tVData.addElement(tConfOperator);
    tVData.addElement(tG);

    CError cError = new CError( );
    CErrors mErrors = new CErrors();
    XmlExport txmlExport = new XmlExport();
    
    
    if(!tBusinessDelegate.submitData(tVData,strOperation,busiName)){
      mErrors.copyAllErrors(tBusinessDelegate.getCErrors());
      cError.moduleName = "BQF1Print";
      cError.functionName = "submitData";
      cError.errorMessage = "tBQCheckUI发生错误，但是没有提供详细的出错信息";
      mErrors.addOneError(cError);
    }
    mResult = tBusinessDelegate.getResult();
    txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
    if (txmlExport==null){
       loggerDebug("BQF1Print","null没有正确的打印数据！！！");
    }else{
       session.putValue("PrintStream", txmlExport.getInputStream());
       loggerDebug("BQF1Print","put session value");
       response.sendRedirect("../f1print/GetF1Print.jsp");
    }

%> 
<html>
  <script language="javascript">
    top.opener.focus();
    top.close();
   </script>
</html>

