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
<Div  id="DutyPayCalCodeDefMainTitle" style= "display: ''">
<table class=common border=0>
  <tr class=common>
  	<td class=title5>算法类型</td>
  	<td class=common>
			<input type = "radio"  value = "Y"  name = "DutyPayCalCodeSwitchFlag" onclick="DutyPaychangeRadioValue('Y')" checked />规则引擎
			<input type = "radio"  value = "N"  name = "DutyPayCalCodeSwitchFlag"   onclick="DutyPaychangeRadioValue('N')" />SQL
    	<!--input value="进入算法定义" onclick="InputCalCodeDefFace()" class="cssButton" type="button" -->
    </td>
  </tr>
  <input type= "hidden" name= "hiddenDutyPayCalCode" id= "hiddenDutyPayCalCode" value="">
  <input type= "hidden" name= "hiddenDutyPayRiskCode" id= "hiddenDutyPayRiskCode"  value="">
</table>
</Div>
 
 
 <script>
 	var tDutyPayCalCodeOperator = "<%=mOperator%>";
 	//进入算法定义界面
 	function InputDutyPayCalCodeDefFace(business)
 	{ 			
 		var tRadioValue = document.getElementById("DutyPayCalCodeSwitchFlag").value;
 	//	alert(tRadioValue);
 		if(tRadioValue==null||tRadioValue=='')
 		{
 			myAlert('请选择算法类型!');
 			return;
 		}
 		var RiskCode = document.getElementById("hiddenDutyPayRiskCode").value;
 		
 		if(RiskCode==null||RiskCode=='')
 		{
 			myAlert('险种编码为空!');
 			return;
 		}

 		var CalCodeCode = document.getElementById("hiddenDutyPayCalCode").value;
 		
 		if(CalCodeCode==null||CalCodeCode=='')
 		{
 			myAlert('算法编码为空!');
 			return;
 		}
		var tUrl = "PDAlgoDefiMain.jsp?pdflag=<%=request.getParameter("pdflag")%>&riskcode=" + RiskCode+ "&algocode=" + CalCodeCode;
 		
 	/*	if(DutyPayswitchCalCodeType(CalCodeCode))
 		{
 			if(tRadioValue!='Y')
 			{
 				alert('算法类型与编码规则不一致,请修改后再定制!');
 				return false;
 			}
 			var Business='01';
 			try{
 			if(business==null||business==''||business=='undefined')Business='01';
 			else Business=business;
 			}catch(ex){
 			Business='01';
 			}
 			 tUrl =  "../ibrms/IbrmsPDAlgoDefiMain.jsp?riskcode="+RiskCode
             + "&RuleName="+CalCodeCode
             + "&RuleDes="+RiskCode
             + "&Creator="+tDutyPayCalCodeOperator
             //+ "&RuleStartDate="+document.getElementById("RequDate").value
             + "&Business="+Business+"&State=0&RuleType=1";
 		}
 		else
 		{
 			if(tRadioValue!='N')
 			{
 				alert('算法类型与编码规则不一致,请修改后再定制!');
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
 		
 		*/
 		if(tRadioValue=='Y'){
 		 			var Business='01';
 			try{
 			if(business==null||business==''||business=='undefined')Business='01';
 			else Business=business;
 			}catch(ex){
 			Business='01';
 			}
 			 tUrl =  "../ibrms/IbrmsPDAlgoDefiMain.jsp?pdflag=<%=request.getParameter("pdflag")%>&riskcode="+RiskCode
             + "&RuleName="+CalCodeCode
             + "&RuleDes="+RiskCode
             + "&CreateModul=1"
             + "&Creator="+tDutyPayCalCodeOperator
             + "&Business="+Business+"&State=0&RuleType=1";
 		}else if(tRadioValue=='N'){//AlgoCode
 			var tUrl = "PDAlgoDefiMain.jsp?pdflag=<%=request.getParameter("pdflag")%>&riskcode=" + RiskCode+ "&AlgoCode=" + CalCodeCode;
 		}
 		else {
 		myAlert("请选择算法引擎类型！");
 		}
		showInfo = window.open(tUrl);

 	}
 	
 	
 	//按照算法编码规则判断算法类型
 	function DutyPayswitchCalCodeType(tCalCode)
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
 	function initDutyPayCalCodeMain(tRiskCode,tCalCode)
 	{
 		try
 		{
 			document.getElementById("hiddenDutyPayCalCode").value = tCalCode;
 			document.getElementById("hiddenDutyPayRiskCode").value = tRiskCode;
 		if(tCalCode==null||tCalCode=='')
 		{
 			return ;
 		}
 		if(tCalCode.length>=2&&tCalCode.substring(0,2).toUpperCase() == 'RU')
 		{
 			document.all("DutyPayCalCodeSwitchFlag")[0].checked = true;
 			//document.getElementById("CalCodeSwitchFlag").value  = 'Y';
 			DutyPaychangeRadioValue('Y');
 		}
 		else
 		{
 			document.all("DutyPayCalCodeSwitchFlag")[1].checked = true;
 			//document.getElementById("CalCodeSwitchFlag").value  = 'N';
 			DutyPaychangeRadioValue('N');
 		}
 		
 		}
 		catch(e)
 		{
 			myAlert('初始算法信息失败!');
 		}
 	}
 	
 	//修改Radio的编码
 	function DutyPaychangeRadioValue(tValue)
 	{
 		//alert("tValue:"+tValue);
 		document.getElementById("DutyPayCalCodeSwitchFlag").value = tValue;
 	}
 </script>