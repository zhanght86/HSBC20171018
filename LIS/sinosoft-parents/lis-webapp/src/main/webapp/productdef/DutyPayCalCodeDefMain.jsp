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
<Div  id="DutyPayCalCodeDefMainTitle" style= "display: ''">
<table class=common border=0>
  <tr class=common>
  	<td class=title5>�㷨����</td>
  	<td class=common>
			<input type = "radio"  value = "Y"  name = "DutyPayCalCodeSwitchFlag" onclick="DutyPaychangeRadioValue('Y')" checked />��������
			<input type = "radio"  value = "N"  name = "DutyPayCalCodeSwitchFlag"   onclick="DutyPaychangeRadioValue('N')" />SQL
    	<!--input value="�����㷨����" onclick="InputCalCodeDefFace()" class="cssButton" type="button" -->
    </td>
  </tr>
  <input type= "hidden" name= "hiddenDutyPayCalCode" id= "hiddenDutyPayCalCode" value="">
  <input type= "hidden" name= "hiddenDutyPayRiskCode" id= "hiddenDutyPayRiskCode"  value="">
</table>
</Div>
 
 
 <script>
 	var tDutyPayCalCodeOperator = "<%=mOperator%>";
 	//�����㷨�������
 	function InputDutyPayCalCodeDefFace(business)
 	{ 			
 		var tRadioValue = document.getElementById("DutyPayCalCodeSwitchFlag").value;
 	//	alert(tRadioValue);
 		if(tRadioValue==null||tRadioValue=='')
 		{
 			myAlert('��ѡ���㷨����!');
 			return;
 		}
 		var RiskCode = document.getElementById("hiddenDutyPayRiskCode").value;
 		
 		if(RiskCode==null||RiskCode=='')
 		{
 			myAlert('���ֱ���Ϊ��!');
 			return;
 		}

 		var CalCodeCode = document.getElementById("hiddenDutyPayCalCode").value;
 		
 		if(CalCodeCode==null||CalCodeCode=='')
 		{
 			myAlert('�㷨����Ϊ��!');
 			return;
 		}
		var tUrl = "PDAlgoDefiMain.jsp?pdflag=<%=request.getParameter("pdflag")%>&riskcode=" + RiskCode+ "&algocode=" + CalCodeCode;
 		
 	/*	if(DutyPayswitchCalCodeType(CalCodeCode))
 		{
 			if(tRadioValue!='Y')
 			{
 				alert('�㷨������������һ��,���޸ĺ��ٶ���!');
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
 				alert('�㷨������������һ��,���޸ĺ��ٶ���!');
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
 		myAlert("��ѡ���㷨�������ͣ�");
 		}
		showInfo = window.open(tUrl);

 	}
 	
 	
 	//�����㷨��������ж��㷨����
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
 	
 	//��ʼ���㷨��������ֱ������Ϣ
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
 			myAlert('��ʼ�㷨��Ϣʧ��!');
 		}
 	}
 	
 	//�޸�Radio�ı���
 	function DutyPaychangeRadioValue(tValue)
 	{
 		//alert("tValue:"+tValue);
 		document.getElementById("DutyPayCalCodeSwitchFlag").value = tValue;
 	}
 </script>