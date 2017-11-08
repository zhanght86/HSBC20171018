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
  String busiName="sysBounsRiskPreUI";
  BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  //需要传入后台参数（最大集合）
  String tCalYear= request.getParameter("BonusCalYear")==null?"":request.getParameter("BonusCalYear").trim();
  int tDelType = -1;      //处理类型
  String Content = "";    //处理结果
  String Flag="";  
  String tAction = request.getParameter("hideaction");
 
  String tOtherSQL = "";
  GlobalInput tGI = new GlobalInput();
  tGI=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。

  //需要保存或删除的授权信息
  LoBonusRiskRemSet tLoBonusRiskRemSet = new LoBonusRiskRemSet();
  //保存操作
  if(tAction.equals("INSERT"))
  {
  String[] tChk = request.getParameterValues("InpBonusRiskGridChk"); 
  String[] tRiskCode= request.getParameterValues("BonusRiskGrid1"); //险种编码
  
 for(int index=0;index<tChk.length;index++)
        {
           LoBonusRiskRemSchema tempLoBonusRiskRemSchema = new LoBonusRiskRemSchema();
          if(tChk[index].equals("1"))           
          {
                tempLoBonusRiskRemSchema.setFisCalYear(tCalYear);
                tempLoBonusRiskRemSchema.setRiskCode(tRiskCode[index]);
                tempLoBonusRiskRemSchema.setState("0");
                tLoBonusRiskRemSet.add(tempLoBonusRiskRemSchema);
           }
        }               

  }
  else if(tAction.equals("DELETE"))
  {
   String tRadio[] = request.getParameterValues("InpNoBonusRiskGridSel");  
   String[] tRiskCode= request.getParameterValues("NoBonusRiskGrid1"); //险种编码
      for (int index=0; index< tRadio.length;index++)
      {
         LoBonusRiskRemSchema tempLoBonusRiskRemSchema = new LoBonusRiskRemSchema();
        if(tRadio[index].equals("1"))
        {  
           loggerDebug("BonusRiskPreSave","该行被选中");
           tempLoBonusRiskRemSchema.setFisCalYear(tCalYear);
           tempLoBonusRiskRemSchema.setRiskCode(tRiskCode[index]);
           tempLoBonusRiskRemSchema.setState("0");
           tLoBonusRiskRemSet.add(tempLoBonusRiskRemSchema);
        } 
      }
  }
  VData tVData = new VData();
  VData mResult = new VData();
  tVData.add(0,tCalYear);
  tVData.add(1,tLoBonusRiskRemSet);
  tVData.add(2,tOtherSQL);
  tVData.add(3,tGI);


 
  
  Flag="Succ";
  Content = "保存成功";
  if(!tBusinessDelegate.submitData(tVData,tAction,busiName))
  {
    loggerDebug("BonusRiskPreSave","数据处理错误");
    Flag="Fail";
    Content = " 处理失败，原因是:" +tBusinessDelegate.getCErrors().getFirstError();
  }
   loggerDebug("BonusRiskPreSave",Content + "\n" + Flag + "\n---数据处理完毕 End---\n\n");
%>
 
<html>
<script language="javascript"> 
   parent.fraInterface.afterSubmit("<%=Flag%>","<%=Content%>");
</script>
</html> 
  
 
