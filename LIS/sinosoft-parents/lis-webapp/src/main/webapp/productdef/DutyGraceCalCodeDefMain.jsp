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
    			�㷨����
    		</td>
    	 </tr>
       </table>	
</Div-->
<Div  id="DutyGraceCalCodeDefMainTitle" style= "display: ''">
<table class=common border=0>
  <tr class=common>
  	<td class=title5>�㷨����</td>
  	<td class=common>
			<input type = "radio"  value = "Y"  name = "DutyGraceCalCodeSwitchFlag" onclick="DutyGracechangeRadioValue('Y')" checked />�ɷѱ���
			<input type = "radio"  value = "N"  name = "DutyGraceCalCodeSwitchFlag"   onclick="DutyGracechangeRadioValue('N')" />SQL
    	<!--input value="�����㷨����" onclick="InputCalCodeDefFace()" class="cssButton" type="button" -->
    </td>
  </tr>
  <input type= "hidden" name= "hiddenDutyGraceCalCode" id= "hiddenDutyGraceCalCode" value="">
  <input type= "hidden" name= "hiddenDutyGraceRiskCode" id= "hiddenDutyGraceRiskCode"  value="">
</table>
</Div>
 
 
 <script>
 	var tDutyGraceCalCodeOperator = "<%=mOperator%>";
 	//�����㷨�������
 	function InputDutyGraceCalCodeDefFace()
 	{
 		var tRadioValue = document.getElementById("DutyGraceCalCodeSwitchFlag").value;
 	//	alert(tRadioValue);
 		if(tRadioValue==null||tRadioValue=='')
 		{
 			myAlert('�����������');
 			return;
 		}
 		var RiskCode = document.getElementById("hiddenDutyGraceRiskCode").value;
 		
 		if(RiskCode==null||RiskCode=='')
 		{
 			myAlert('�Զ�չ������');
 			return;
 		}

 		var CalCodeCode = document.getElementById("hiddenDutyGraceCalCode").value;
 		
 		if(CalCodeCode==null||CalCodeCode=='')
 		{
 			myAlert('��СͶ��������');
 			return;
 		}
		var tUrl = "PDAlgoDefiMain.jsp?riskcode=" + RiskCode+ "&algocode=" + CalCodeCode;
 		
 		if(DutyGraceswitchCalCodeType(CalCodeCode))
 		{
 			if(tRadioValue!='Y')
 			{
 				myAlert('���Ͷ��������');
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
 				myAlert('���Ͷ��������');
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
 	
 	
 	//�����㷨��������ж��㷨����
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
 	
 	//��ʼ���㷨��������ֱ������Ϣ
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
 			myAlert('��󱻱�������');
 		}
 	}
 	
 	//�޸�Radio�ı���
 	function DutyGracechangeRadioValue(tValue)
 	{
 		//alert("tValue:"+tValue);
 		document.getElementById("DutyGraceCalCodeSwitchFlag").value = tValue;
 	}
 </script>