<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.acc.*"%>
<%@page  import=" com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.Arith"%>
<%
//接收信息，并作校验处理。
//输入参数

LCPerInvestPlanSchema tLCPerInvestPlanSchema = new LCPerInvestPlanSchema();
LCPerInvestPlanSet tLCPerInvestPlanSet = new LCPerInvestPlanSet();
LCPerInvestPlanUI tLCPerInvestPlanUI = new LCPerInvestPlanUI();

//输出参数
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "";
String Content = "";

//全局变量
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("InvestPlanInputSave","begin ...");

String tOperate=request.getParameter("mPlanOperate");	//操作模式
loggerDebug("InvestPlanInputSave","操作方式:"+tOperate);

String UintLinkValiFlag= request.getParameter("UintLinkValiFlag"); //投连账户生效日标志
String InputMode ="1";	//投资录入方式
loggerDebug("InvestPlanInputSave","InputMode==="+InputMode);
String ContNo = request.getParameter("ContNo");
String PolNo = request.getParameter("PolNo");
String GrpPolNo = request.getParameter("GrpPolNo");
String GrpContNo = request.getParameter("GrpContNo");
String InsuredNo = request.getParameter("InsuredNo");
String  RiskCode= request.getParameter("RiskCode");
int lineCount = 0;
String arrCount[]=null;
if(InputMode.equals("1"))
{
  loggerDebug("InvestPlanInputSave","按投资比例=====");
 arrCount=request.getParameterValues("InvestPlanRateNo");
    loggerDebug("InvestPlanInputSave","InvestPlanRate 1="+ arrCount[0]);
  loggerDebug("InvestPlanInputSave","arrCount.length=====:"+arrCount.length);

if (arrCount != null)
{
	String tInsuAccNo[] = request.getParameterValues("InvestPlanRate1");
	//String tPayPlanCode[] = request.getParameterValues("InvestPlanRate6");
	String tInvestMaxRate[] = request.getParameterValues("InvestPlanRate3");
	String tInvestMinRate[] = request.getParameterValues("InvestPlanRate4");
	String tInvestRate[] = request.getParameterValues("InvestPlanRate5");
	String tInverstCurrency[] = request.getParameterValues("InvestPlanRate7");
	lineCount = arrCount.length; //行数
 String tSQL="";
               tSQL="select  distinct PayPlanCode,insuaccno from lmriskaccpay where riskcode='"
                    +RiskCode+"'";
          SSRS tSSRS = new SSRS();
          ExeSQL tExeSQL = new ExeSQL();
          loggerDebug("InvestPlanInputSave",tSQL);
          tSSRS =tExeSQL.execSQL(tSQL);
  if(tSSRS!=null||tSSRS.MaxRow>0)
  {
    for(int j=1;j<=tSSRS.MaxRow;j++)
    {
        double a=0;
        
        tLCPerInvestPlanSchema = new LCPerInvestPlanSchema();
        tLCPerInvestPlanSchema.setContNo(ContNo);
        tLCPerInvestPlanSchema.setPolNo(PolNo);
        tLCPerInvestPlanSchema.setInputMode(InputMode);
        tLCPerInvestPlanSchema.setGrpContNo(GrpContNo);
        tLCPerInvestPlanSchema.setGrpPolNo(GrpPolNo);
        tLCPerInvestPlanSchema.setInsuredNo(InsuredNo);
        tLCPerInvestPlanSchema.setRiskCode(RiskCode);
        tLCPerInvestPlanSchema.setInsuAccNo(tSSRS.GetText(j,2));
        tLCPerInvestPlanSchema.setPayPlanCode(tSSRS.GetText(j,1));
      for(int i=0;i<lineCount;i++)
      {
		if(tSSRS.GetText(j,2).equals(tInsuAccNo[i]))
		{
        tLCPerInvestPlanSchema.setInvestMaxRate(tInvestMaxRate[i]);
        tLCPerInvestPlanSchema.setInvestMinRate(tInvestMinRate[i]);
		//tongmeng 2010-11-30 modify
		if(tInverstCurrency[i]!=null&&!tInverstCurrency[i].equals(""))
		{
			tLCPerInvestPlanSchema.setCurrency(tInverstCurrency[i]);
		}
        if(!tInvestRate[i].equals(""))
        {
        	loggerDebug("InvestPlanInputSave","tInvestRate[i]："+tInvestRate[i]);
          if(!PubFun.isNumeric(tInvestRate[i]))
          {
            FlagStr="Fail";
            Content = " 操作失败，原因是:投资比例的录入格式有误";
          }else{
            tLCPerInvestPlanSchema.setInvestRate(tInvestRate[i]);
            a=a+tLCPerInvestPlanSchema.getInvestRate();
          }
        }else{
        	if(!tOperate.equals("DELETE")){
             FlagStr="Fail";
            Content = " 操作失败，原因是:投资比例不能为空！";}
        }
		}
       
        loggerDebug("InvestPlanInputSave","记录"+i+"放入Set！");
      }
      tLCPerInvestPlanSet.add(tLCPerInvestPlanSchema);
      /*
         if (Arith.round(a, 5)!= 1.00)
         {
                FlagStr="Fail";
               Content = " 操作失败，所有投资帐户的投资比例之和不为1,请重新录入";
             //return false;
          }
      */
    }
  }
}
}else
{
  //按投资金额
  loggerDebug("InvestPlanInputSave","按投资金额=====");
  arrCount = request.getParameterValues("InvestPlanMoneyNo");
  loggerDebug("InvestPlanInputSave","arrCount.length=====:"+arrCount.length);

if (arrCount != null)
{

	String tInsuAccNo[] = request.getParameterValues("InvestPlanMoney1");
	String tPayPlanCode[] = request.getParameterValues("InvestPlanMoney6");
	String tInvestMaxRate[] = request.getParameterValues("InvestPlanMoney3");
	String tInvestMinRate[] = request.getParameterValues("InvestPlanMoney4");
	String tInvestMoney[] = request.getParameterValues("InvestPlanMoney5");
	lineCount = arrCount.length; //行数

	for(int i=0;i<lineCount;i++)
	{

                tLCPerInvestPlanSchema = new LCPerInvestPlanSchema();
                tLCPerInvestPlanSchema.setContNo(ContNo);
                tLCPerInvestPlanSchema.setPolNo(PolNo);
                tLCPerInvestPlanSchema.setGrpContNo(GrpContNo);
                tLCPerInvestPlanSchema.setGrpPolNo(GrpPolNo);
                tLCPerInvestPlanSchema.setInsuredNo(InsuredNo);
                tLCPerInvestPlanSchema.setInputMode(InputMode);
                    tLCPerInvestPlanSchema.setRiskCode(RiskCode);

		tLCPerInvestPlanSchema.setInsuAccNo(tInsuAccNo[i]);
                tLCPerInvestPlanSchema.setPayPlanCode( tPayPlanCode[i]);
		tLCPerInvestPlanSchema.setInvestMaxRate(tInvestMaxRate[i]);
		tLCPerInvestPlanSchema.setInvestMinRate(tInvestMinRate[i]);
                 if(!tInvestMoney[i].equals(""))
                {
                  if(!PubFun.isNumeric(tInvestMoney[i]))
                  {
                    FlagStr="Fail";
                    Content = " 操作失败，原因是:投资金额的录入格式有误";
                  }else{
                     tLCPerInvestPlanSchema.setInvestMoney(tInvestMoney[i]);
                  }
                }else{
                  tLCPerInvestPlanSchema.setInvestMoney(tInvestMoney[i]);
                }
		tLCPerInvestPlanSet.add(tLCPerInvestPlanSchema);
		loggerDebug("InvestPlanInputSave","记录"+i+"放入Set！");
	}
 }
}
if(FlagStr.equals("")){
// 准备传输数据 VData
VData tVData = new VData();
FlagStr="";

tVData.add(tG);
tVData.addElement(tLCPerInvestPlanSet);
tVData.addElement(UintLinkValiFlag);

  try
  {
    loggerDebug("InvestPlanInputSave","========submitData ====== !!");
    tLCPerInvestPlanUI.submitData(tVData,tOperate);
  }
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

  if (!FlagStr.equals("Fail"))
  {
    tError = tLCPerInvestPlanUI.mErrors;
    if (!tError.needDealError())
    {
      Content = " 保存成功! ";
      FlagStr = "Succ";
    }
    else
    {
      Content = " 保存失败，原因是:" + tError.getFirstError();
      FlagStr = "Fail";
    }
  }
}
String LoadFlag2=request.getParameter("LoadFlag2");
LoadFlag2 = "2";
loggerDebug("InvestPlanInputSave","LoadFlag2=="+LoadFlag2);
%>
<html>
<%
if(LoadFlag2.equals("1")){
%>
<script language="javascript">
  parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
<%
}
if(LoadFlag2.equals("2")){
%>
<script language="javascript">
//	parent.close();
 parent.fraInterface.afterSubmita("<%=FlagStr%>","<%=Content%>");
</script>
<% }%>

</html>
