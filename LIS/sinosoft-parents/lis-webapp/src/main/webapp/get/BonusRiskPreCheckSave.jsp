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
  //��Ҫ�����̨��������󼯺ϣ�

  String tCalYear= request.getParameter("BonusCalYear")==null?"":request.getParameter("BonusCalYear").trim();
  loggerDebug("BonusRiskPreCheckSave","tCalYear["+tCalYear+"]");
  String Content = "";    //������
  String Flag="";  
  String tAction = request.getParameter("transact");
 
  String tOtherSQL = "";
  GlobalInput tGI = new GlobalInput();
  tGI=(GlobalInput)session.getValue("GI"); //���ҳ��ؼ��ĳ�ʼ����

  //��Ҫ�����ɾ������Ȩ��Ϣ
  LoBonusRiskRemSet tLoBonusRiskRemSet = new LoBonusRiskRemSet();



 if(tAction.equals("UPDATE"))
  {

     String tRadio[] = request.getParameterValues("InpNoBonusRiskGridChk");  
                         //������ʽ=" Inp+MulLine������+Sel" 
     String[] tRiskCode= request.getParameterValues("NoBonusRiskGrid1"); //���ֱ���
  
      for (int index=0; index< tRadio.length;index++)
      {
         LoBonusRiskRemSchema tempLoBonusRiskRemSchema = new LoBonusRiskRemSchema();
        if(tRadio[index].equals("1"))
        {  
           loggerDebug("BonusRiskPreCheckSave","���б�ѡ��");
           tempLoBonusRiskRemSchema.setFisCalYear(tCalYear);
           tempLoBonusRiskRemSchema.setRiskCode(tRiskCode[index]);
           tempLoBonusRiskRemSchema.setState("0");
           tLoBonusRiskRemSet.add(tempLoBonusRiskRemSchema);
        } 
        if(tRadio[index].equals("0"))
           loggerDebug("BonusRiskPreCheckSave","����δ��ѡ��");
      }
  }
  VData tVData = new VData();
  tVData.add(tCalYear);
  tVData.add(tLoBonusRiskRemSet);
  tVData.add(tGI);

  
  Flag="Succ";
  Content = "����ɹ�";
  if(!tBusinessDelegate.submitData(tVData,tAction,busiName))
  {
    loggerDebug("BonusRiskPreCheckSave","���ݴ������");
    Flag="Fail";
    Content = " ����ʧ�ܣ�ԭ����:" +tBusinessDelegate.getCErrors().getFirstError();
  }
   loggerDebug("BonusRiskPreCheckSave",Content + "\n" + Flag + "\n---���ݴ������ End---\n\n");
%>
 
<html>
<script language="javascript"> 
   parent.fraInterface.afterSubmit("<%=Flag%>","<%=Content%>");
</script>
</html> 
  
 
