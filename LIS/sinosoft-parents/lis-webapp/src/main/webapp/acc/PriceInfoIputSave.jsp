<%
//程序名称：LOAccUnitPriceSave.jsp
//程序功能：
//创建日期：2002-08-19
//创建人  ：Kevin
//更新记录：  更新人    更新日期     更新原因/内容
//
%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
   <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
loggerDebug("PriceInfoIputSave","Flag-1");
  //接收信息，并作校验处理。
  //输入参数
  LOAccUnitPriceSchema tLOAccUnitPriceSchema   = new LOAccUnitPriceSchema();
//  LOAccUnitPriceUI tLOAccUnitPriceUI   = new LOAccUnitPriceUI();
  //  LOAccUnitPriceSet tLOAccUnitPriceSet=new LOAccUnitPriceSet();
     GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getAttribute("GI");
  //输出参数
  CErrors tError =new CErrors();
  String tBmCert = "";
  //后面要执行的动作：添加，修改，删除
  String transact = "";

  String tRela  = "";
  String FlagStr = "";
  String Content = "";
//if( !request.getParameter("InsuTotalMoney").equals("")){
//if(!PubFun.isNumeric(request.getParameter("InsuTotalMoney")))
//{
//  FlagStr="fail";
//  Content = transact+" 失败，原因是:"+"财产总资产的输入格式出错";
//}
//}
// if(!PubFun.isNumeric(request.getParameter("Liabilities")))
//{
//   FlagStr="fail";
//   Content = transact+" 失败，原因是:"+"负债的输入格式出错";
//}
//if(!PubFun.isNumeric(request.getParameter("ComChgUnitCount")))
//{
//  FlagStr="fail";
//   Content = transact+" 失败，原因是:"+"本次变动单位数的输入格式出错";
//}
   //if(!request.getParameter("RedeemRate").equals("")){
   //  if(!PubFun.isNumeric(request.getParameter("RedeemRate")))
   //  {
   //    FlagStr="fail";
   //    Content = transact+" 失败，原因是:"+"赎回比例的输入格式出错";
   //  }
   //}
   loggerDebug("PriceInfoIputSave","Flag-6");
   //if(!request.getParameter("RedeemMoney").equals("")){loggerDebug("PriceInfoIputSave","Flag-7");
   //  if(!PubFun.isNumeric(request.getParameter("RedeemMoney")))
   //  {loggerDebug("PriceInfoIputSave","Flag-8");
   //    FlagStr="fail";
   //    Content = transact+" 失败，原因是:"+"赎回金额的输入格式出错";
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


    //暂时用这两个字段传参数-鲁哲20070824
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
    // 准备传输数据 VData

    //执行动作：insert 添加纪录，update 修改纪录，delete 删除纪录
    VData tVData=new VData();
    tVData.add(tLOAccUnitPriceSchema);
    tVData.add(tGI);
    //tLOAccUnitPriceUI.submitData(tVData,transact);
    tBusinessDelegate.submitData(tVData, transact,busiName);
  }
  catch(Exception ex)
  {
    Content = transact+"失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }
  //如果在Catch中发现异常，则不从错误类中提取错误信息
  if (FlagStr=="")
  {
    //tError = tLOAccUnitPriceUI.mErrors;
    tError = tBusinessDelegate.getCErrors();
    if (!tError.needDealError())
    {
      if(transact.equals("INSERT"))
      {
        transact="录入";
      }
      if(transact.equals("UPDATE"))
      {
        transact="修改";
      }
      if(transact.equals("DELETE"))
      {
        transact="删除";
      }
      Content = transact+"成功";
      FlagStr = "Succ";
    }
    else
    {
      Content = transact+" 失败，原因是:" + tError.getFirstError();
      FlagStr = "Fail";
    }
  }
}else{


}

  //添加各种预处理
%>
<%@page import="com.sinosoft.service.BusinessDelegate"%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>

