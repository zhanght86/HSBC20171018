
<%
//程序名称：LLReportInput.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.db.*"%>
<%@page import="com.sinosoft.lis.vdb.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
  loggerDebug("CertifyDescUpdateSave","开始执行Save页面");
  LMCertifyDesSchema mLMCertifyDesSchema = new LMCertifyDesSchema();
  LMCertifyDesSet mLMCertifyDesSet = new LMCertifyDesSet();
  LMCardRiskSet mLMCardRiskSet = new LMCardRiskSet();
  //CertifyDescUI mCertifyDescUI = new CertifyDescUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  loggerDebug("CertifyDescUpdateSave","操作的类型是"+mOperateType);
  
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //将操作标志的英文转换成汉字的形式
  
  loggerDebug("CertifyDescUpdateSave","开始进行获取数据的操作！！！");
  mLMCertifyDesSchema.setCertifyCode(request.getParameter("CertifyCode"));
  mLMCertifyDesSchema.setCertifyName(request.getParameter("CertifyName"));
  mLMCertifyDesSchema.setState(request.getParameter("State"));
  
  mLMCertifyDesSchema.setHaveLimit(request.getParameter("HaveLimit"));
  mLMCertifyDesSchema.setMaxUnit1(request.getParameter("MaxUnit1"));
  mLMCertifyDesSchema.setMaxUnit2(request.getParameter("MaxUnit2"));
  
  mLMCertifyDesSchema.setHaveValidate(request.getParameter("HaveValidate"));
  mLMCertifyDesSchema.setValidate1(request.getParameter("Validate1"));
  mLMCertifyDesSchema.setValidate2(request.getParameter("Validate2"));
  mLMCertifyDesSchema.setNote(request.getParameter("Note"));
  
  mLMCertifyDesSet.add(mLMCertifyDesSchema);

  if(mOperateType.equals("INSERT"))
  {
    mDescType = "新增";
  }
  if(mOperateType.equals("UPDATE"))
  {
    mDescType = "修改";
  }
  if(mOperateType.equals("DELETE"))
  {
    mDescType = "删除";
  }
  if(mOperateType.equals("QUERY"))
  {
    mDescType = "查询";
  }

  String mCertifyClass="";
  VData tVData = new VData();

    tVData.addElement(mOperateType);
    tVData.addElement(mCertifyClass);
    tVData.addElement(tG);
    tVData.addElement(mLMCertifyDesSet);
    
    //mCertifyDescUI.submitData(tVData,mOperateType);

  String busiName="CertifyDescUI";
  if(!tBusinessDelegate.submitData(tVData,mOperateType,busiName))
  {    
       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
       { 
			Content = mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError();
			FlagStr = "Fail";
	   }
	   else
	   {
			Content = mDescType+"失败";
			FlagStr = "Fail";				
	   }
  }
  else
  {
     	Content = mDescType+"成功! ";
      	FlagStr = "Succ";  
  }

%>

<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
