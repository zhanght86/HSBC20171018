<%
//�������ƣ�LOAccUnitPriceSave.jsp
//�����ܣ�
//�������ڣ�2002-08-19
//������  ��Kevin
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--�û�У����-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
   <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
loggerDebug("PriceInfoIputSave","Flag-1");
  //������Ϣ������У�鴦��
  //�������
  LOAccUnitPriceSchema tLOAccUnitPriceSchema   = new LOAccUnitPriceSchema();
//  LOAccUnitPriceUI tLOAccUnitPriceUI   = new LOAccUnitPriceUI();
  //  LOAccUnitPriceSet tLOAccUnitPriceSet=new LOAccUnitPriceSet();
     GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getAttribute("GI");
  //�������
  CErrors tError =new CErrors();
  String tBmCert = "";
  //����Ҫִ�еĶ�������ӣ��޸ģ�ɾ��
  String transact = "";

  String tRela  = "";
  String FlagStr = "";
  String Content = "";
//if( !request.getParameter("InsuTotalMoney").equals("")){
//if(!PubFun.isNumeric(request.getParameter("InsuTotalMoney")))
//{
//  FlagStr="fail";
//  Content = transact+" ʧ�ܣ�ԭ����:"+"�Ʋ����ʲ��������ʽ����";
//}
//}
// if(!PubFun.isNumeric(request.getParameter("Liabilities")))
//{
//   FlagStr="fail";
//   Content = transact+" ʧ�ܣ�ԭ����:"+"��ծ�������ʽ����";
//}
//if(!PubFun.isNumeric(request.getParameter("ComChgUnitCount")))
//{
//  FlagStr="fail";
//   Content = transact+" ʧ�ܣ�ԭ����:"+"���α䶯��λ���������ʽ����";
//}
   //if(!request.getParameter("RedeemRate").equals("")){
   //  if(!PubFun.isNumeric(request.getParameter("RedeemRate")))
   //  {
   //    FlagStr="fail";
   //    Content = transact+" ʧ�ܣ�ԭ����:"+"��ر����������ʽ����";
   //  }
   //}
   loggerDebug("PriceInfoIputSave","Flag-6");
   //if(!request.getParameter("RedeemMoney").equals("")){loggerDebug("PriceInfoIputSave","Flag-7");
   //  if(!PubFun.isNumeric(request.getParameter("RedeemMoney")))
   //  {loggerDebug("PriceInfoIputSave","Flag-8");
   //    FlagStr="fail";
   //    Content = transact+" ʧ�ܣ�ԭ����:"+"��ؽ��������ʽ����";
   //  }
   //}
loggerDebug("PriceInfoIputSave","Flag-9");
if(FlagStr=="")
{
    //tLOAccUnitPriceSchema.setRiskCode(request.getParameter("RiskCode"));

    tLOAccUnitPriceSchema.setInsuAccNo(request.getParameter("InsuAccNo"));

    tLOAccUnitPriceSchema.setStartDate(request.getParameter("StartDate"));

    tLOAccUnitPriceSchema.setEndDate(request.getParameter("StartDate"));

    tLOAccUnitPriceSchema.setInvestFlag(request.getParameter("InvestFlag"));

    tLOAccUnitPriceSchema.setSRateDate(request.getParameter("StartDate"));

    tLOAccUnitPriceSchema.setARateDate(request.getParameter("StartDate"));
    
    tLOAccUnitPriceSchema.setCompanyUnitCount(request.getParameter("CompanyUnitCount"));
    
    tLOAccUnitPriceSchema.setComChgUnitCount(request.getParameter("ComChgUnitCount"));
    
    tLOAccUnitPriceSchema.setCustomersUnitCount(request.getParameter("CustomersUnitCount"));
    
    tLOAccUnitPriceSchema.setSKFlag(request.getParameter("SKFlag"));


    //��ʱ���������ֶδ�����-³��20070824
    tLOAccUnitPriceSchema.setInsuTotalMoney(request.getParameter("InsuTotalMoney"));

    tLOAccUnitPriceSchema.setLiabilities(request.getParameter("Liabilities"));

    //tLOAccUnitPriceSchema.setRedeemRate(request.getParameter("RedeemRate"));

    tLOAccUnitPriceSchema.setState(request.getParameter("State"));

    //tLOAccUnitPriceSchema.setRedeemMoney(request.getParameter("RedeemMoney"));

    tLOAccUnitPriceSchema.setOperator(tGI.Operator);


    transact=request.getParameter("Transact");

    String busiName="LOAccUnitPriceUI";
	BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
  try
  {
    // ׼���������� VData

    //ִ�ж�����insert ��Ӽ�¼��update �޸ļ�¼��delete ɾ����¼
    VData tVData=new VData();
    tVData.add(tLOAccUnitPriceSchema);
    tVData.add(tGI);
    //tLOAccUnitPriceUI.submitData(tVData,transact);
    tBusinessDelegate.submitData(tVData, transact,busiName);
  }
  catch(Exception ex)
  {
    Content = transact+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }
  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  if (FlagStr=="")
  {
    //tError = tLOAccUnitPriceUI.mErrors;
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
      if(transact.equals("INSERT"))
      {
        transact="¼��";
      }
      if(transact.equals("UPDATE"))
      {
        transact="�޸�";
      }
      if(transact.equals("DELETE"))
      {
        transact="ɾ��";
      }
      Content = transact+"�ɹ�";
      FlagStr = "Succ";
    }
    else
    {
      Content = transact+" ʧ�ܣ�ԭ����:" + tError.getFirstError();
      FlagStr = "Fail";
    }
  }
}else{


}

  //��Ӹ���Ԥ����
%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

