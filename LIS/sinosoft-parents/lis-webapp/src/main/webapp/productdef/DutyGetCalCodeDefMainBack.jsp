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
<Div  id="DutyGetCalCodeDefMainTitleBack" style= "display: ''">
<table class=common border=0>
  <tr class=common>
  	<td class=title5>算法类型</td>
  	<td class=common>
			<input type = "radio"  value = "Y"  name = "DutyGetCalCodeSwitchFlagBack" onclick="DutyGetchangeRadioValueBack('Y')" checked />规则引擎
			<input type = "radio"  value = "N"  name = "DutyGetCalCodeSwitchFlagBack"   onclick="DutyGetchangeRadioValueBack('N')" />SQL
    	<!--input value="进入算法定义" onclick="InputCalCodeDefFace()" class="cssButton" type="button" -->
    </td>
  </tr>
  <input type= "hidden" name= "hiddenDutyGetCalCodeBack" id= "hiddenDutyGetCalCodeBack" value="">
  <input type= "hidden" name= "hiddenDutyGetRiskCodeBack" id= "hiddenDutyGetRiskCodeBack"  value="">
</table>
</Div>
 
 
 <script>
 	var tDutyGetCalCodeOperatorBack = "<%=mOperator%>";
 	//进入算法定义界面
 	function InputDutyGetCalCodeDefFaceBack(business)
 	{
 		var tRadioValue = document.getElementById("DutyGetCalCodeSwitchFlagBack").value;
 	//	alert(tRadioValue);
 		if(tRadioValue==null||tRadioValue=='')
 		{
 			myAlert('请选择算法类型!');
 			return;
 		}
 		var RiskCode = document.getElementById("hiddenDutyGetRiskCodeBack").value;
 		
 		if(RiskCode==null||RiskCode=='')
 		{
 			myAlert('险种编码为空!');
 			return;
 		}

 		var CalCodeCode = document.getElementById("hiddenDutyGetCalCodeBack").value;
 		
 		if(CalCodeCode==null||CalCodeCode=='')
 		{
 			myAlert('算法编码为空!');
 			return;
 		}
		var tUrl = "PDAlgoDefiMain.jsp?pdflag=<%=request.getParameter("pdflag")%>&riskcode=" + RiskCode+ "&algocode=" + CalCodeCode;
 		
 		if(DutyGetswitchCalCodeTypeBack(CalCodeCode))
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
 	function DutyGetswitchCalCodeTypeBack(tCalCode)
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
 	function initDutyGetCalCodeMainBack(tRiskCode,tCalCode)
 	{
 		try
 		{
 			document.getElementById("hiddenDutyGetCalCodeBack").value = tCalCode;
 			document.getElementById("hiddenDutyGetRiskCodeBack").value = tRiskCode;
 		if(tCalCode==null||tCalCode=='')
 		{
 			return ;
 		}
 		if(tCalCode.length>=2&&tCalCode.substring(0,2).toUpperCase() == 'RU')
 		{
 			document.all("DutyGetCalCodeSwitchFlagBack")[0].checked = true;
 			//document.getElementById("CalCodeSwitchFlag").value  = 'Y';
 			DutyGetchangeRadioValueBack('Y');
 		}
 		else
 		{
 			document.all("DutyGetCalCodeSwitchFlagBack")[1].checked = true;
 			//document.getElementById("CalCodeSwitchFlag").value  = 'N';
 			DutyGetchangeRadioValueBack('N');
 		}
 		
 		}
 		catch(e)
 		{
 			myAlert('初始算法信息失败!');
 		}
 	}
 	
 	//修改Radio的编码
 	function DutyGetchangeRadioValueBack(tValue)
 	{
 		//alert("tValue:"+tValue);
 		document.getElementById("DutyGetCalCodeSwitchFlagBack").value = tValue;
 	}
 </script>