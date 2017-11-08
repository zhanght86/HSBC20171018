<%@include file="../i18n/language.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%

 GlobalInput tGI = new GlobalInput();
 tGI = (GlobalInput)session.getAttribute("GI");
 
 String mOperator = tGI.Operator;
  
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
<Div  id="DutyGraceCalCodeDefMainTitle" style= "display: ''">
<table class=common border=0>
  <tr class=common>
  	<td class=title5>算法类型</td>
  	<td class=common>
			<input type = "radio"  value = "Y"  name = "DutyGraceCalCodeSwitchFlag" onclick="DutyGracechangeRadioValue('Y')" checked />缴费编码
			<input type = "radio"  value = "N"  name = "DutyGraceCalCodeSwitchFlag"   onclick="DutyGracechangeRadioValue('N')" />SQL
    	<!--input value="进入算法定义" onclick="InputCalCodeDefFace()" class="cssButton" type="button" -->
    </td>
  </tr>
  <input type= "hidden" name= "hiddenDutyGraceCalCode" id= "hiddenDutyGraceCalCode" value="">
  <input type= "hidden" name= "hiddenDutyGraceRiskCode" id= "hiddenDutyGraceRiskCode"  value="">
</table>
</Div>
 
 
 <script>
 	var tDutyGraceCalCodeOperator = "<%=mOperator%>";
 	//进入算法定义界面
 	function InputDutyGraceCalCodeDefFace()
 	{
 		var tRadioValue = document.getElementById("DutyGraceCalCodeSwitchFlag").value;
 	//	alert(tRadioValue);
 		if(tRadioValue==null||tRadioValue=='')
 		{
 			myAlert('有无名单标记');
 			return;
 		}
 		var RiskCode = document.getElementById("hiddenDutyGraceRiskCode").value;
 		
 		if(RiskCode==null||RiskCode=='')
 		{
 			myAlert('自动展期类型');
 			return;
 		}

 		var CalCodeCode = document.getElementById("hiddenDutyGraceCalCode").value;
 		
 		if(CalCodeCode==null||CalCodeCode=='')
 		{
 			myAlert('最小投保人年龄');
 			return;
 		}
		var tUrl = "PDAlgoDefiMain.jsp?riskcode=" + RiskCode+ "&algocode=" + CalCodeCode;
 		
 		if(DutyGraceswitchCalCodeType(CalCodeCode))
 		{
 			if(tRadioValue!='Y')
 			{
 				myAlert('最大投保人年龄');
 				return false;
 			}
 			 tUrl =  "../ibrms/IbrmsPDAlgoDefiMain.jsp?riskcode="+RiskCode
             + "&RuleName="+CalCodeCode
             + "&RuleDes="+RiskCode
             + "&CreateModul=1"
             + "&Creator="+tDutyGraceCalCodeOperator
             //+ "&RuleStartDate="+document.getElementById("RequDate").value
             + "&Business=01&State=0&RuleType=1";
 		}
 		else
 		{
 			if(tRadioValue!='N')
 			{
 				myAlert('最大投保人年龄');
 				return false;
 			}
 		}
 			
 		
 		//tongmeng 2011-07-11 modify
 		//beforeCheck
 		/*
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
 		
 		*/
		showInfo = window.open(tUrl);

 	}
 	
 	
 	//按照算法编码规则判断算法类型
 	function DutyGraceswitchCalCodeType(tCalCode)
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
 	function initDutyGraceCalCodeMain(tRiskCode,tCalCode)
 	{
 		try
 		{
 			document.getElementById("hiddenDutyGraceCalCode").value = tCalCode;
 			document.getElementById("hiddenDutyGraceRiskCode").value = tRiskCode;
 		if(tCalCode==null||tCalCode=='')
 		{
 			return ;
 		}
 		if(tCalCode.length>=2&&tCalCode.substring(0,2).toUpperCase() == 'RU')
 		{
 			document.all("DutyGraceCalCodeSwitchFlag")[0].checked = true;
 			//document.getElementById("CalCodeSwitchFlag").value  = 'Y';
 			DutyGracechangeRadioValue('Y');
 		}
 		else
 		{
 			document.all("DutyGraceCalCodeSwitchFlag")[1].checked = true;
 			//document.getElementById("CalCodeSwitchFlag").value  = 'N';
 			DutyGracechangeRadioValue('N');
 		}
 		
 		}
 		catch(e)
 		{
 			myAlert('最大被保人年龄');
 		}
 	}
 	
 	//修改Radio的编码
 	function DutyGracechangeRadioValue(tValue)
 	{
 		//alert("tValue:"+tValue);
 		document.getElementById("DutyGraceCalCodeSwitchFlag").value = tValue;
 	}
 </script>