<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<!--�û�У����-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.acc.*"%>
<%@page  import=" com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.Arith"%>
<%
//������Ϣ������У�鴦��
//�������

LCPerInvestPlanSchema tLCPerInvestPlanSchema = new LCPerInvestPlanSchema();
LCPerInvestPlanSet tLCPerInvestPlanSet = new LCPerInvestPlanSet();
LCPerInvestPlanUI tLCPerInvestPlanUI = new LCPerInvestPlanUI();

//�������
CErrors tError = null;
String tRearStr = "";
String tRela = "";
String FlagStr = "";
String Content = "";

//ȫ�ֱ���
GlobalInput tG = new GlobalInput();
tG=(GlobalInput)session.getValue("GI");

loggerDebug("InvestPlanInputSave","begin ...");

String tOperate=request.getParameter("mPlanOperate");	//����ģʽ
loggerDebug("InvestPlanInputSave","������ʽ:"+tOperate);

String UintLinkValiFlag= request.getParameter("UintLinkValiFlag"); //Ͷ���˻���Ч�ձ�־
String InputMode ="1";	//Ͷ��¼�뷽ʽ
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
  loggerDebug("InvestPlanInputSave","��Ͷ�ʱ���=====");
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
	lineCount = arrCount.length; //����
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
        	loggerDebug("InvestPlanInputSave","tInvestRate[i]��"+tInvestRate[i]);
          if(!PubFun.isNumeric(tInvestRate[i]))
          {
            FlagStr="Fail";
            Content = " ����ʧ�ܣ�ԭ����:Ͷ�ʱ�����¼���ʽ����";
          }else{
            tLCPerInvestPlanSchema.setInvestRate(tInvestRate[i]);
            a=a+tLCPerInvestPlanSchema.getInvestRate();
          }
        }else{
        	if(!tOperate.equals("DELETE")){
             FlagStr="Fail";
            Content = " ����ʧ�ܣ�ԭ����:Ͷ�ʱ�������Ϊ�գ�";}
        }
		}
       
        loggerDebug("InvestPlanInputSave","��¼"+i+"����Set��");
      }
      tLCPerInvestPlanSet.add(tLCPerInvestPlanSchema);
      /*
         if (Arith.round(a, 5)!= 1.00)
         {
                FlagStr="Fail";
               Content = " ����ʧ�ܣ�����Ͷ���ʻ���Ͷ�ʱ���֮�Ͳ�Ϊ1,������¼��";
             //return false;
          }
      */
    }
  }
}
}else
{
  //��Ͷ�ʽ��
  loggerDebug("InvestPlanInputSave","��Ͷ�ʽ��=====");
  arrCount = request.getParameterValues("InvestPlanMoneyNo");
  loggerDebug("InvestPlanInputSave","arrCount.length=====:"+arrCount.length);

if (arrCount != null)
{

	String tInsuAccNo[] = request.getParameterValues("InvestPlanMoney1");
	String tPayPlanCode[] = request.getParameterValues("InvestPlanMoney6");
	String tInvestMaxRate[] = request.getParameterValues("InvestPlanMoney3");
	String tInvestMinRate[] = request.getParameterValues("InvestPlanMoney4");
	String tInvestMoney[] = request.getParameterValues("InvestPlanMoney5");
	lineCount = arrCount.length; //����

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
                    Content = " ����ʧ�ܣ�ԭ����:Ͷ�ʽ���¼���ʽ����";
                  }else{
                     tLCPerInvestPlanSchema.setInvestMoney(tInvestMoney[i]);
                  }
                }else{
                  tLCPerInvestPlanSchema.setInvestMoney(tInvestMoney[i]);
                }
		tLCPerInvestPlanSet.add(tLCPerInvestPlanSchema);
		loggerDebug("InvestPlanInputSave","��¼"+i+"����Set��");
	}
 }
}
if(FlagStr.equals("")){
// ׼���������� VData
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
    Content = "����ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  if (!FlagStr.equals("Fail"))
  {
    tError = tLCPerInvestPlanUI.mErrors;
    if (!tError.needDealError())
    {
      Content = " ����ɹ�! ";
      FlagStr = "Succ";
    }
    else
    {
      Content = " ����ʧ�ܣ�ԭ����:" + tError.getFirstError();
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
