<%@include file="../i18n/language.jsp"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1print.*"%>
<%@page import="com.sinosoft.productdef.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.service.*" %>
<%@page contentType="text/html;charset=GBK" %>
<%
String tTempleteID = request.getParameter("TempleteID");
System.out.println(tTempleteID);
String tBusiName = "RiskTestCal";
BusinessDelegate tBusinessDelegate = BusinessDelegate.getBusinessDelegate();
TransferData tTransferData = new TransferData();
String Content ="";
Enumeration paramNames = request.getParameterNames();
while (paramNames.hasMoreElements()) 
{
	
	String paramName = (String) paramNames.nextElement();

	String[] paramValues = request.getParameterValues(paramName);
	
	System.out.println("paramName:"+paramName+":paramValues.length:"+paramValues.length);
	if (paramValues.length == 1) 
	{
		//
		String paramValue = paramValues[0];
		tTransferData.setNameAndValue(paramName,paramValue);
	}
	else
	{
		//MultLine 
	}
}
/////////////////////////////////////////////////////////////////////////////////////////////////////
		LCPremSet tLCPremSet=new LCPremSet();
        LCPremSchema tLCPremSchema1=new LCPremSchema();
        try
        { 
        	 String tPremDutyCode[] =request.getParameterValues("PremGrid1");
        if (tPremDutyCode!=null) {       
        String tPremChk[] = request.getParameterValues("InpPremGridChk");
     
        String tPremPayCode[] =request.getParameterValues("PremGrid2");
        String tPremPayCodeName[] =request.getParameterValues("PremGrid3");
        String tPremStandPrem[] =request.getParameterValues("PremGrid4");

        int PremCount = 0;
        if (tPremDutyCode != null) PremCount = tPremDutyCode.length;
        for (int i = 0; i < PremCount; i++)
        {
          if(tPremDutyCode[i]==null || tPremDutyCode[i].equals("")) break;

          tLCPremSchema1= new LCPremSchema();
        
          if(!tPremDutyCode[i].equals("") && tPremChk[i].equals("1"))
          {
            System.out.println(" tPremDutyCode:"+tPremDutyCode[i]);
            //tLCDutySchema.setDutyCode(tPremDutyCode[i]);
            tLCPremSchema1.setDutyCode(tPremDutyCode[i]);
            tLCPremSchema1.setPayPlanCode(tPremPayCode[i]);

            tLCPremSchema1.setStandPrem(tPremStandPrem[i]);  
            tLCPremSchema1.setPrem(tPremStandPrem[i]);
            System.out.println("--------------prem"+i+":"+tPremStandPrem[i]);
            tLCPremSet.add(tLCPremSchema1);

          } 
        } 
      } 

    } catch(Exception ex){System.out.println("set schema...error !!!");}

    System.out.println("end set schema ...");
////////////////////////////////////////////////////////////////////////////////////////////////////////



VData tVData=new VData();
tVData.add(0,tTransferData);
tVData.add(1,tLCPremSet);

//RiskTestCal tRiskTestCal = new RiskTestCal();
//tRiskTestCal.submitData(tVData,"");
if(!tBusinessDelegate.submitData(tVData,"",tBusiName))
{
	if(tBusinessDelegate.getCErrors()!=null && tBusinessDelegate.getCErrors().getErrorCount()>0)
	{
		Content = ""+"处理失败"+" :" + tBusinessDelegate.getCErrors().getFirstError();
	}
	else
	{
		Content = ""+"处理失败"+"";
	}	
}
else
{
	Content = ""+"计算成功"+"";
	String tPrem = String.valueOf((Double)tBusinessDelegate.getResult().get(0));
	Content = Content +  ":" + ""+"保费是:"+""+tPrem;
}
%>
<html>
  <script type="text/javascript">
     myAlert("<%=Content%>");
  </script>    
</html>