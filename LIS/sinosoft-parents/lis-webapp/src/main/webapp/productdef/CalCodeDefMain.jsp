<%@include file="../i18n/language.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%

 GlobalInput tGI = new GlobalInput();
 tGI = (GlobalInput)session.getAttribute("GI");
 
 String mOperator = tGI.Operator;
  
 String mRuleType= request.getParameter("RuleType");
 if(mRuleType==null||mRuleType.equals("")||mRuleType.equals("null"))
 {
  mRuleType = "1";
 }
%>


<!--Div  id="CalCodeDefMainDiv"  style= "display: ''">
    	<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
    		</td>
    		<td class= titleImg>
    			算法类型
    		</td>
    	 </tr>
       </table>	
</Div-->
<Div  id="CalCodeDefMainTitle" style= "display: ''">
<table class=common border=0>
  <tr class=common>
  	<td class=title5>算法类型</td>
  	<td class=common>
			<input type = "radio"  value = "Y"  name = "CalCodeSwitchFlag" onclick="changeRadioValue('Y')" checked />规则引擎
			<input type = "radio"  value = "N"  name = "CalCodeSwitchFlag"   onclick="changeRadioValue('N')" />SQL
    	<!--input value="进入算法定义" onclick="InputCalCodeDefFace()" class="cssButton" type="button" -->
    </td>
  </tr>
  <input type= "hidden" name= "hiddenCalCode" id= "hiddenCalCode" value="">
  <input type= "hidden" name= "hiddenRiskCode" id= "hiddenRiskCode"  value="">
</table>
</Div>
 
 
 <script>
 	var tCalCodeOperator = "<%=mOperator%>";
 	var jRuleType = <%=mRuleType%>;
 	//进入算法定义界面
 	function InputCalCodeDefFace(value)
 	{
 	try{
 	if(value==null||value==''||value=='null')value='01';
 	else if(value=='02')value='02';
 	else if(value=='01')value='01';
 	else if(value=='99')value='99';
 	}catch(ex){value='01';}
 	
 		var tRadioValue = document.getElementById("CalCodeSwitchFlag").value;
 	//	alert(tRadioValue);
 		if(tRadioValue==null||tRadioValue=='')
 		{
 			myAlert('请选择算法类型!');
 			return;
 		}
 		var RiskCode = document.getElementById("hiddenRiskCode").value;
 		
 		if(RiskCode==null||RiskCode=='')
 		{
 			myAlert('险种编码为空!');
 			return;
 		}

 		var CalCodeCode = document.getElementById("hiddenCalCode").value;
 		
 		if(CalCodeCode==null||CalCodeCode=='')
 		{
 			myAlert('算法编码为空!');
 			return;
 		}
		var tUrl = "PDAlgoDefiMain.jsp?pdflag="+"<%=request.getParameter("pdflag")%>"+"&riskcode=" + RiskCode+ "&AlgoCode=" + CalCodeCode;
 		
 		if(switchCalCodeType(CalCodeCode))
 		{
 			if(tRadioValue!='Y')
 			{
 				myAlert('算法类型与编码规则不一致,请修改后再定制!');
 				return false;
 			}
 			 tUrl =  "../ibrms/IbrmsPDAlgoDefiMain.jsp?riskcode="+RiskCode
             + "&RuleName="+CalCodeCode
             + "&RuleDes="+RiskCode
             + "&prdFlag=1"
             + "&CreateModul=1"
             + "&Creator="+tCalCodeOperator
             //+ "&RuleStartDate="+document.getElementById("RequDate").value
             + "&Business="+value+"&State=0&pdflag="+"<%=request.getParameter("pdflag")%>"+"&RuleType="+jRuleType;
 		}
 		else
 		{
 			if(tRadioValue!='N')
 			{
 				myAlert('算法类型与编码规则不一致,请修改后再定制!');
 				return false;
 			}
 		}
 			
 		
 		//tongmeng 2011-07-11 modify
 		//beforeCheck
 		try
 		{
 			if(!beforeCheckCalCodeMain(RiskCode,CalCodeCode))
 			{
 				return false;	
 			}
 		}
 		catch(Ex)
 		{
 			
 		} 
 		
 		
		showInfo = window.open(tUrl);

 	}
 	
 	
 	//按照算法编码规则判断算法类型
 	function switchCalCodeType(tCalCode)
 	{
 		if(tCalCode.length>=2&&tCalCode.substring(0,2).toUpperCase() == 'RU')
 		{
 			return true;
 		}
 		else
 		{
			return false;
 		}
 	}
 	
 	//初始化算法编码和险种编码的信息
 	function initCalCodeMain(tRiskCode,tCalCode)
 	{
 		try
 		{
 			document.getElementById("hiddenCalCode").value = tCalCode;
 			document.getElementById("hiddenRiskCode").value = tRiskCode;
 		if(tCalCode==null||tCalCode=='')
 		{
 			return ;
 		}
 		if(tCalCode.length>=2&&tCalCode.substring(0,2).toUpperCase() == 'RU')
 		{
 			document.all("CalCodeSwitchFlag")[0].checked = true;
 			//document.getElementById("CalCodeSwitchFlag").value  = 'Y';
 			changeRadioValue('Y');
 		}
 		else
 		{
 			document.all("CalCodeSwitchFlag")[1].checked = true;
 			//document.getElementById("CalCodeSwitchFlag").value  = 'N';
 			changeRadioValue('N');
 		}
 		
 		}
 		catch(e)
 		{
 			myAlert('初始算法信息失败!');
 		}
 	}
 	
 	//修改Radio的编码
 	function changeRadioValue(tValue)
 	{
 		//alert("tValue:"+tValue);
 		document.getElementById("CalCodeSwitchFlag").value = tValue;
 	}
 </script>