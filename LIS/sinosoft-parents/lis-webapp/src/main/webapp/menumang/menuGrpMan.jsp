<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.LDMenuGrpSchema"%>
<%@page import="com.sinosoft.lis.menumang.LDMenuGrpUI"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%@page import="com.sinosoft.service.*" %>
<%
//loggerDebug("menuGrpMan","start jsp");
String FlagStr = "Fail";
String actionStr = request.getParameter("Action");
//loggerDebug("menuGrpMan","actionStr:" + actionStr);
int action = -1;
if (actionStr.compareTo("query") == 0)
	action = 2;
if (actionStr.compareTo("insert") == 0)
	action = 0;
if (actionStr.compareTo("update") == 0)
	action = 1;
if (actionStr.compareTo("delete") == 0)
	action = 3;
//loggerDebug("menuGrpMan","action num: " + action);

switch (action)
{
	case 1:
	
	case 0:  
		//insert
		String Operator = request.getParameter("Operator");
		String MenuGrpCode = request.getParameter("MenuGrpCode");
		String MenuGrpName = request.getParameter("MenuGrpName");
		String MenuSign = request.getParameter("MenuSign");
		String MenuGrpDescription = request.getParameter("MenuGrpDescription");

//		loggerDebug("menuGrpMan","MenuGrpCode:" + MenuGrpCode);
//		loggerDebug("menuGrpMan","MenuGrpDescription:" + MenuGrpCode);
//		loggerDebug("menuGrpMan","Operator:" + Operator);

		String menuSetString = request.getParameter("hideString");
//		loggerDebug("menuGrpMan","menuSetString : " + menuSetString);

		String removeSetString = request.getParameter("hideRemoveString");
//		loggerDebug("menuGrpMan","hideRemoveString is " + removeSetString);

		LDMenuGrpSchema tGrpSchema = new LDMenuGrpSchema();
		tGrpSchema.setMenuGrpCode(MenuGrpCode);
		tGrpSchema.setMenuGrpName(MenuGrpName);
		tGrpSchema.setMenuGrpDescription(MenuGrpDescription);
		tGrpSchema.setMenuSign(MenuSign);
		tGrpSchema.setOperator(Operator);
		if (action == 0)
		{
			String curDate = PubFun.getCurrentDate();
			String curTime = PubFun.getCurrentTime();
			tGrpSchema.setMakeTime(curTime);
			tGrpSchema.setMakeDate(curDate);
		}

		//LDMenuGrpUI tLDMenuGrpUI = new LDMenuGrpUI();
		String busiName="LDMenuGrpUI";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		
		VData tData = new VData();
		tData.add(menuSetString);
		tData.add(removeSetString);
		tData.add(tGrpSchema);
//		if (tLDMenuGrpUI.submitData(tData,actionStr))
//			FlagStr = "success";
//		else
//			FlagStr = "fail";
		if(!tBusinessDelegate.submitData(tData,actionStr,busiName))
		{    
		    if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		    { 
				//Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
				FlagStr = "Fail";
			}
			else
			{
				//Content = "保存失败";
				FlagStr = "Fail";				
			}
		}
		else{
			FlagStr = "success";		
		}
//		loggerDebug("menuGrpMan","FlagStr : " + FlagStr);
		break;

	case 2:
		FlagStr = "success";
		break;

	case 3:
//		loggerDebug("menuGrpMan","here");
		tGrpSchema = new LDMenuGrpSchema();
		String tRadio[] = request.getParameterValues("InpQueryGrpGridSel");

//		if (tRadio == null)
//			loggerDebug("menuGrpMan","tRadio is null");

		if (tRadio != null)
		{
//			loggerDebug("menuGrpMan","tRadio is not null");
			int index = 0;
			for (; index< tRadio.length;index++)
			{
				if(tRadio[index].equals("1"))
					break;
			}
			if (index == tRadio.length)
			{
				loggerDebug("menuGrpMan","没有选中对象!");
			}
			else{
				String tMenuGrpName[] = request.getParameterValues("QueryGrpGrid1");
				String tMenuGrpCode[] = request.getParameterValues("QueryGrpGrid2");
				String tMenuSign[] = request.getParameterValues("QueryGrpGrid3");
				String tMenuGrpDescription[] = request.getParameterValues("QueryGrpGrid4");
				tGrpSchema.setMenuGrpCode(tMenuGrpCode[index]);
				tGrpSchema.setMenuGrpName(tMenuGrpName[index]);
				tGrpSchema.setMenuGrpDescription(tMenuGrpDescription[index]);
				tGrpSchema.setMenuSign(tMenuSign[index]);
			}
			//tLDMenuGrpUI = new LDMenuGrpUI();
			busiName="LDMenuGrpUI";
			BusinessDelegate tBusinessDelegate1=BusinessDelegate.getBusinessDelegate();
			tData = new VData();
			tData.add(tGrpSchema);
//			if (tLDMenuGrpUI.submitData(tData,actionStr))
//				FlagStr = "success";
//			else
//				FlagStr = "fail";
			if(!tBusinessDelegate1.submitData(tData,actionStr,busiName))
			{    
			    if(tBusinessDelegate1.getCErrors()!=null&&tBusinessDelegate1.getCErrors().getErrorCount()>0)
			    { 
					//Content = "保存失败，原因是:" + tBusinessDelegate.getCErrors().getError(0).errorMessage;
					FlagStr = "Fail";
				}
				else
				{
					//Content = "保存失败";
					FlagStr = "Fail";				
				}
			}
			else{
				FlagStr = "success";		
			}				
				
		}
		break;
}
//loggerDebug("menuGrpMan",FlagStr);
//loggerDebug("menuGrpMan","end of jsp");
%>
<script>
parent.fraInterface.afterSubmit("<%=FlagStr%>");
</script>
