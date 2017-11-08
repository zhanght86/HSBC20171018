
<%
//程序名称：CertifyDescSave.jsp
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
  loggerDebug("CertifyDescSave","开始执行Save页面");
  LMCertifyDesSchema mLMCertifyDesSchema = new LMCertifyDesSchema();
  LMCertifyDesSet mLMCertifyDesSet = new LMCertifyDesSet();
  //LMCardRiskSet mLMCardRiskSet = new LMCardRiskSet();
  //CertifyDescUI mCertifyDescUI = new CertifyDescUI();
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  
  CErrors tError = null;
  String mOperateType = request.getParameter("OperateType");
  loggerDebug("CertifyDescSave","操作的类型是 "+mOperateType);
  
  String tRela  = "";
  String FlagStr = "";
  String Content = "";
  String mDescType = ""; //将操作标志的英文转换成汉字的形式
  String mCertifyClass = request.getParameter("CertifyClass"); //记录单证的类型
  
  loggerDebug("CertifyDescSave","开始进行获取数据的操作！！！");
  mLMCertifyDesSchema.setCertifyCode(request.getParameter("CertifyCode"));
  mLMCertifyDesSchema.setCertifyName(request.getParameter("CertifyName"));
  mLMCertifyDesSchema.setCertifyClass(request.getParameter("CertifyClass"));
  mLMCertifyDesSchema.setCertifyClass2(request.getParameter("CertifyClass2"));
  mLMCertifyDesSchema.setState(request.getParameter("State"));
  //mLMCertifyDesSchema.setHavePrice(request.getParameter("HavePrice"));
  mLMCertifyDesSchema.setHaveNumber(request.getParameter("HaveNumber"));
  mLMCertifyDesSchema.setCertifyLength(request.getParameter("CertifyLength"));  
  mLMCertifyDesSchema.setTackBackFlag(request.getParameter("TackBackFlag"));
  mLMCertifyDesSchema.setCertifyPrice(request.getParameter("CertifyPrice"));  
  mLMCertifyDesSchema.setUnit(request.getParameter("Unit"));  
  mLMCertifyDesSchema.setMaxPrintNo(request.getParameter("MaxPrintNo"));
  mLMCertifyDesSchema.setHaveLimit(request.getParameter("HaveLimit"));
  mLMCertifyDesSchema.setMaxUnit1(request.getParameter("MaxUnit1"));
  mLMCertifyDesSchema.setMaxUnit2(request.getParameter("MaxUnit2"));
  mLMCertifyDesSchema.setHaveValidate(request.getParameter("HaveValidate"));
  mLMCertifyDesSchema.setValidate1(request.getParameter("Validate1"));
  mLMCertifyDesSchema.setValidate2(request.getParameter("Validate2"));
  mLMCertifyDesSchema.setNote(request.getParameter("Note"));
  //mLMCertifyDesSchema.setState(request.getParameter("State"));
  //mLMCertifyDesSchema.setManageCom(request.getParameter("ManageCom"));  
  mLMCertifyDesSet.add(mLMCertifyDesSchema);
   
  /****************************************************************************
   * 判断单证的类型，若是定额单证则要对定额单险种信息表进行描述
   ***************************************************************************/
  /**if(mCertifyClass.equals("D"))
  {
    String[] strNumber = request.getParameterValues("CardRiskGridNo");
    String[] strRiskCode = request.getParameterValues("CardRiskGrid1");
    String[] strPrem = request.getParameterValues("CardRiskGrid2");
    String[] strPremProp = request.getParameterValues("CardRiskGrid3");
    String[] strPremLot = request.getParameterValues("CardRiskGrid4");
    int tLength = strNumber.length;
    if(strNumber!=null)
    {
      for(int i = 0 ;i < tLength ;i++)
      {
        LMCardRiskSchema tLMCardRiskSchema = new LMCardRiskSchema();
        tLMCardRiskSchema.setCertifyCode(request.getParameter("CertifyCode"));
        tLMCardRiskSchema.setRiskCode(strRiskCode[i]);
        tLMCardRiskSchema.setPrem(strPrem[i]);
        tLMCardRiskSchema.setPremProp(strPremProp[i]);
        tLMCardRiskSchema.setPremLot(String.valueOf(strPremLot[i]));
        mLMCardRiskSet.add(tLMCardRiskSchema);
      }
    }
  }**/

  if(mOperateType.equals("INSERT"))
  {
    mDescType = "新增";
  }
  if(mOperateType.equals("UPDATE"))
  {
    mDescType = "修改";
    mLMCertifyDesSchema.setRiskVersion(request.getParameter("CertifyCode_1"));
    loggerDebug("CertifyDescSave","修改时的校验单证号码是"+mLMCertifyDesSchema.getRiskVersion());
  }
  if(mOperateType.equals("DELETE"))
  {
    mDescType = "删除";
  }

  VData tVData = new VData();
    tVData.addElement(mCertifyClass);
    tVData.addElement(tG);
    tVData.addElement(mLMCertifyDesSet);
    //if(mCertifyClass.equals("D"))
    //{
    //  tVData.addElement(mLMCardRiskSet);
    //}
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
