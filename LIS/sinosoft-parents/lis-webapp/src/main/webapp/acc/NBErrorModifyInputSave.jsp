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
<%@page import="com.sinosoft.service.*" %>
<%
//接收信息，并作校验处理。
//输入参数

LCPerInvestPlanSchema tLCPerInvestPlanSchema = new LCPerInvestPlanSchema();
LCPerInvestPlanSet tLCPerInvestPlanSet = new LCPerInvestPlanSet();
//NBErrorModifyUI tNBErrorModifyUI = new NBErrorModifyUI();

//输出参数
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "";
String Content = "";

//全局变量
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("NBErrorModifyInputSave","begin ...");
String InputMode ="1";	//投资录入方式
loggerDebug("NBErrorModifyInputSave","InputMode==="+InputMode);
String ContNo = request.getParameter("QureyContNo");
String PolNo = request.getParameter("PolNo");
String GrpPolNo = "00000000000000000000";
String GrpContNo = "00000000000000000000";
String RiskCode= request.getParameter("RiskCode");
int lineCount = 0;
String arrCount[]=null;
if(InputMode.equals("1"))
{
  loggerDebug("NBErrorModifyInputSave","按投资比例=====");
 arrCount=request.getParameterValues("NewPlanGridNo");
    loggerDebug("NBErrorModifyInputSave","NewPlanGrid 1="+ arrCount[0]);
  loggerDebug("NBErrorModifyInputSave","arrCount.length=====:"+arrCount.length);

if (arrCount != null)
{
	String tInsuAccNo[] = request.getParameterValues("NewPlanGrid1");
	//String tPayPlanCode[] = request.getParameterValues("InvestPlanRate6");
	String tInvestMaxRate[] = request.getParameterValues("NewPlanGrid3");
	String tInvestMinRate[] = request.getParameterValues("NewPlanGrid4");
	String tInvestRate[] = request.getParameterValues("NewPlanGrid5");
	String tCurrency[] = request.getParameterValues("NewPlanGrid7");
	lineCount = arrCount.length; //行数
    ExeSQL tExeSQL = new ExeSQL();
	
    String tSQL="Select Distinct currency From lmriskinsuacc a Where Exists(Select 1 From lmriskaccpay Where insuaccno =a.insuaccno And riskcode = '"+RiskCode+"')";
    SSRS ss = new SSRS();
    ss = tExeSQL.execSQL(tSQL);
    if(ss != null && !"".equals(ss)){
    	for(int s = 1; s<=ss.MaxRow;s++){
    		double a=0;
    		tSQL="Select Distinct insuaccno From lmriskinsuacc a Where currency = '"+ss.GetText(s,1)+"' and Exists(Select 1 From lmriskaccpay Where insuaccno =a.insuaccno And riskcode = '"+RiskCode+"')";
    		SSRS is = new SSRS();
    		is = tExeSQL.execSQL(tSQL);
    		if(is != null && !"".equals(is)){
    			for(int k = 1;k <=is.MaxRow;k++){
    				for(int i=0;i<lineCount;i++){
    					if(!tInsuAccNo[i].equals(is.GetText(k,1))){
    						continue;
    					}
    					a += Double.parseDouble(tInvestRate[i]);
    				}
    			}
    			if (Arith.round(a, 5)!= 1.00){
    	             FlagStr="Fail";
    	             Content = " 操作失败，所有投资帐户的投资比例之和不为1,请重新录入";
    	             //return false;
    	        }
    		}
    	}
    }

    
    tSQL="select Distinct insuaccno,PayPlanCode from lmriskaccpay a where riskcode='"+RiskCode+"'";
	SSRS is = new SSRS();
	is = tExeSQL.execSQL(tSQL);
	if(is != null && !"".equals(is)){
		for(int k =1;k <=is.MaxRow;k++){
			for(int i=0;i<lineCount;i++){
				if(!tInsuAccNo[i].equals(is.GetText(k,1))){
					continue;
				}
		        tLCPerInvestPlanSchema = new LCPerInvestPlanSchema();
		        tLCPerInvestPlanSchema.setContNo(ContNo);
		        tLCPerInvestPlanSchema.setPolNo(PolNo);
		        tLCPerInvestPlanSchema.setInputMode(InputMode);
		        tLCPerInvestPlanSchema.setGrpContNo(GrpContNo);
		        tLCPerInvestPlanSchema.setGrpPolNo(GrpPolNo);
		       // tLCPerInvestPlanSchema.setInsuredNo(InsuredNo);
		        tLCPerInvestPlanSchema.setRiskCode(RiskCode);
		        tLCPerInvestPlanSchema.setInsuAccNo(tInsuAccNo[i]);
		        tLCPerInvestPlanSchema.setPayPlanCode(is.GetText(k,2));
		        tLCPerInvestPlanSchema.setInvestMaxRate(tInvestMaxRate[i]);
		        tLCPerInvestPlanSchema.setInvestMinRate(tInvestMinRate[i]);

		        if(!tInvestRate[i].equals(""))
		        {
		        	//loggerDebug("NBErrorModifyInputSave","tInvestRate[i]："+tInvestRate[i]);
		          if(!PubFun.isNumeric(tInvestRate[i]))
		          {
		            FlagStr="Fail";
		            Content = " 操作失败，原因是:投资比例的录入格式有误";
		          }else{
		            tLCPerInvestPlanSchema.setInvestRate(tInvestRate[i]);
		            //a=a+tLCPerInvestPlanSchema.getInvestRate();
		          }
		        }
		        tLCPerInvestPlanSet.add(tLCPerInvestPlanSchema);
			}
		}
	}
}
}
	if(FlagStr.equals("")){
	// 准备传输数据 VData
	VData tVData = new VData();
	FlagStr="";

	tVData.add(0,tG);
	tVData.add(1,tLCPerInvestPlanSet);
	tVData.add(2,ContNo);
	tVData.add(3,PolNo);
	tVData.add(4,RiskCode);

  try
  {
    loggerDebug("NBErrorModifyInputSave","========submitData ====== !!");
    //tNBErrorModifyUI.submitData(tVData,"UPDATE");
    String busiName="NBErrorModifyUI";
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if (!tBusinessDelegate.submitData(tVData, "UPDATE",busiName)) 
    {
    	Content = " 操作失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
		FlagStr = "Fail";
    }
    else
    {
    	 	tError = tBusinessDelegate.getCErrors();
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
  catch(Exception ex)
  {
    Content = "保存失败，原因是:" + ex.toString();
    FlagStr = "Fail";
  }

}
%>
<html>
<script language="javascript">
  parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
