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
<Div  id="DutyGetCalCodeDefMainTitle" style= "display: ''">
<table class=common border=0>
  <tr class=common>
  	<td class=title5>�㷨����</td>
  	<td class=common>
			<input type = "radio"  value = "Y"  name = "DutyGetCalCodeSwitchFlag" onclick="DutyGetchangeRadioValue('Y')" checked />��������
			<input type = "radio"  value = "N"  name = "DutyGetCalCodeSwitchFlag"   onclick="DutyGetchangeRadioValue('N')" />SQL
    	<!--input value="�����㷨����" onclick="InputCalCodeDefFace()" class="cssButton" type="button" -->
    </td>
  </tr>
  <input type= "hidden" name= "hiddenDutyGetCalCode" id= "hiddenDutyGetCalCode" value="">
  <input type= "hidden" name= "hiddenDutyGetRiskCode" id= "hiddenDutyGetRiskCode"  value="">
</table>
</Div>
 
 
 <script>
 	var tDutyGetCalCodeOperator = "<%=mOperator%>";
 	//�����㷨�������
 	function InputDutyGetCalCodeDefFace(business)
 	{
 		var tRadioValue = document.getElementById("DutyGetCalCodeSwitchFlag").value;
 	//	alert(tRadioValue);
 		if(tRadioValue==null||tRadioValue=='')
 		{
 			myAlert('��ѡ���㷨����!');
 			return;
 		}
 		var RiskCode = document.getElementById("hiddenDutyGetRiskCode").value;
 		
 		if(RiskCode==null||RiskCode=='')
 		{
 			myAlert('���ֱ���Ϊ��!');
 			return;
 		}

 		var CalCodeCode = document.getElementById("hiddenDutyGetCalCode").value;
 		
 		if(CalCodeCode==null||CalCodeCode=='')
 		{
 			myAlert('�㷨����Ϊ��!');
 			return;
 		}
		var tUrl = "PDAlgoDefiMain.jsp?pdflag=<%=request.getParameter("pdflag")%>&riskcode=" + RiskCode+ "&AlgoCode=" + CalCodeCode;
 		
 		if(DutyGetswitchCalCodeType(CalCodeCode))
 		{
 			if(tRadioValue!='Y')
 			{
 				myAlert('�㷨������������һ��,���޸ĺ��ٶ���!');
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
 				myAlert('�㷨������������һ��,���޸ĺ��ٶ���!');
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
 	
 	//��ʼ���㷨��������ֱ������Ϣ
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
 			myAlert('��ʼ�㷨��Ϣʧ��!');
 		}
 	}
 	
 	//�޸�Radio�ı���
 	function DutyGetchangeRadioValue(tValue)
 	{
 		//alert("tValue:"+tValue);
 		document.getElementById("DutyGetCalCodeSwitchFlag").value = tValue;
 	}
 </script>