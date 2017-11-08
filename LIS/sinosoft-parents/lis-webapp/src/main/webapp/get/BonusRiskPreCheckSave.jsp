<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.sys.*"%>
<%@page import="com.sinosoft.service.*" %>
<%
  String busiName="sysBounsRiskCheckPreUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  
  loggerDebug("BonusRiskPreCheckSave","start BounsRiskCheckPreUI.jsp ");
  //需要传入后台参数（最大集合）

  String tCalYear= request.getParameter("BonusCalYear")==null?"":request.getParameter("BonusCalYear").trim();
  loggerDebug("BonusRiskPreCheckSave","tCalYear["+tCalYear+"]");
  String Content = "";    //处理结果
  String Flag="";  
  String tAction = request.getParameter("transact");
 
  String tOtherSQL = "";
  GlobalInput tGI = new GlobalInput();
  tGI=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。

  //需要保存或删除的授权信息
  LoBonusRiskRemSet tLoBonusRiskRemSet = new LoBonusRiskRemSet();



 if(tAction.equals("UPDATE"))
  {

     String tRadio[] = request.getParameterValues("InpNoBonusRiskGridChk");  
                         //参数格式=" Inp+MulLine对象名+Sel" 
     String[] tRiskCode= request.getParameterValues("NoBonusRiskGrid1"); //险种编码
  
      for (int index=0; index< tRadio.length;index++)
      {
         LoBonusRiskRemSchema tempLoBonusRiskRemSchema = new LoBonusRiskRemSchema();
        if(tRadio[index].equals("1"))
        {  
           loggerDebug("BonusRiskPreCheckSave","该行被选中");
           tempLoBonusRiskRemSchema.setFisCalYear(tCalYear);
           tempLoBonusRiskRemSchema.setRiskCode(tRiskCode[index]);
           tempLoBonusRiskRemSchema.setState("0");
           tLoBonusRiskRemSet.add(tempLoBonusRiskRemSchema);
        } 
        if(tRadio[index].equals("0"))
           loggerDebug("BonusRiskPreCheckSave","该行未被选中");
      }
  }
  VData tVData = new VData();
  tVData.add(tCalYear);
  tVData.add(tLoBonusRiskRemSet);
  tVData.add(tGI);

  
  Flag="Succ";
  Content = "保存成功";
  if(!tBusinessDelegate.submitData(tVData,tAction,busiName))
  {
    loggerDebug("BonusRiskPreCheckSave","数据处理错误");
    Flag="Fail";
    Content = " 处理失败，原因是:" +tBusinessDelegate.getCErrors().getFirstError();
  }
   loggerDebug("BonusRiskPreCheckSave",Content + "\n" + Flag + "\n---数据处理完毕 End---\n\n");
%>
 
<html>
<script language="javascript"> 
   parent.fraInterface.afterSubmit("<%=Flag%>","<%=Content%>");
</script>
</html> 
  
 
