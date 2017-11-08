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
<Div  id="DutyGetCalCodeDefMainTitle" style= "display: ''">
<table class=common border=0>
  <tr class=common>
  	<td class=title5>算法类型</td>
  	<td class=common>
			<input type = "radio"  value = "Y"  name = "DutyGetCalCodeSwitchFlag" onclick="DutyGetchangeRadioValue('Y')" checked />规则引擎
			<input type = "radio"  value = "N"  name = "DutyGetCalCodeSwitchFlag"   onclick="DutyGetchangeRadioValue('N')" />SQL
    	<!--input value="进入算法定义" onclick="InputCalCodeDefFace()" class="cssButton" type="button" -->
    </td>
  </tr>
  <input type= "hidden" name= "hiddenDutyGetCalCode" id= "hiddenDutyGetCalCode" value="">
  <input type= "hidden" name= "hiddenDutyGetRiskCode" id= "hiddenDutyGetRiskCode"  value="">
</table>
</Div>
 
 
 <script>
 	var tDutyGetCalCodeOperator = "<%=mOperator%>";
 	//进入算法定义界面
 	function InputDutyGetCalCodeDefFace(business)
 	{
 		var tRadioValue = document.getElementById("DutyGetCalCodeSwitchFlag").value;
 	//	alert(tRadioValue);
 		if(tRadioValue==null||tRadioValue=='')
 		{
 			myAlert('请选择算法类型!');
 			return;
 		}
 		var RiskCode = document.getElementById("hiddenDutyGetRiskCode").value;
 		
 		if(RiskCode==null||RiskCode=='')
 		{
 			myAlert('险种编码为空!');
 			return;
 		}

 		var CalCodeCode = document.getElementById("hiddenDutyGetCalCode").value;
 		
 		if(CalCodeCode==null||CalCodeCode=='')
 		{
 			myAlert('算法编码为空!');
 			return;
 		}
		var tUrl = "PDAlgoDefiMain.jsp?pdflag=<%=request.getParameter("pdflag")%>&riskcode=" + RiskCode+ "&AlgoCode=" + CalCodeCode;
 		
 		if(DutyGetswitchCalCodeType(CalCodeCode))
 		{
 			if(tRadioValue!='Y')
 			{
 				myAlert('算法类型与编码规则不一致,请修改后再定制!');
 				return false;
 			}

 			var Business='01';
 			try{
 			if(business==null||business==''||business=='undefined')Business='01';
 			else Business=business;
 			}catch(ex){
 			}
 			 tUrl =  "../ibrms/IbrmsPDAlgoDefiMain.jsp?pdflag=<%=request.getParameter("pdflag")%>&riskcode="+RiskCode
             + "&RuleName="+CalCodeCode
             + "&RuleDes="+RiskCode
             + "&CreateModul=1"
             + "&Creator="+tDutyGetCalCodeOperator
             //+ "&RuleStartDate="+document.getElementById("RequDate").value
             + "&Business="+Business+"&State=0&RuleType=1";
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
 	function DutyGetswitchCalCodeType(tCalCode)
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
 	function initDutyGetCalCodeMain(tRiskCode,tCalCode)
 	{
 		try
 		{
 			document.getElementById("hiddenDutyGetCalCode").value = tCalCode;
 			document.getElementById("hiddenDutyGetRiskCode").value = tRiskCode;
 		if(tCalCode==null||tCalCode=='')
 		{
 			return ;
 		}
 		if(tCalCode.length>=2&&tCalCode.substring(0,2).toUpperCase() == 'RU')
 		{
 			document.all("DutyGetCalCodeSwitchFlag")[0].checked = true;
 			//document.getElementById("CalCodeSwitchFlag").value  = 'Y';
 			DutyGetchangeRadioValue('Y');
 		}
 		else
 		{
 			document.all("DutyGetCalCodeSwitchFlag")[1].checked = true;
 			//document.getElementById("CalCodeSwitchFlag").value  = 'N';
 			DutyGetchangeRadioValue('N');
 		}
 		
 		}
 		catch(e)
 		{
 			myAlert('初始算法信息失败!');
 		}
 	}
 	
 	//修改Radio的编码
 	function DutyGetchangeRadioValue(tValue)
 	{
 		//alert("tValue:"+tValue);
 		document.getElementById("DutyGetCalCodeSwitchFlag").value = tValue;
 	}
 </script>