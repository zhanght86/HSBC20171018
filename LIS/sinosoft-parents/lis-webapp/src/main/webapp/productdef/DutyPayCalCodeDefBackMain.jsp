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
			<input type = "radio"  value = "Y"  name = "DutyPayCalCodeBackSwitchFlag" onclick="DutyPayBackchangeRadioValue('Y')" checked />��������
			<input type = "radio"  value = "N"  name = "DutyPayCalCodeBackSwitchFlag"   onclick="DutyPayBackchangeRadioValue('N')" />SQL
    	<!--input value="�����㷨����" onclick="InputCalCodeDefFace()" class="cssButton" type="button" -->
    </td>
  </tr>
  <input type= "hidden" name= "hiddenDutyPayCalCodeBack" id= "hiddenDutyPayCalCodeBack" value="">
  <input type= "hidden" name= "hiddenDutyPayRiskCodeBack" id= "hiddenDutyPayRiskCodeBack"  value="">
</table>
</Div>
 
 
 <script>
 	var tDutyPayCalCodeOperator = "<%=mOperator%>";
 	//�����㷨�������
 	function InputDutyPayCalCodeDefFaceBack(business)
 	{
 		var tRadioValue = document.getElementById("DutyPayCalCodeBackSwitchFlag").value;
 	//	alert(tRadioValue);
 		if(tRadioValue==null||tRadioValue=='')
 		{
 			myAlert('��ѡ���㷨����!');
 			return;
 		}
 		var RiskCode = document.getElementById("hiddenDutyPayRiskCodeBack").value;
 		
 		if(RiskCode==null||RiskCode=='')
 		{
 			myAlert('���ֱ���Ϊ��!');
 			return;
 		}

 		var CalCodeCode = document.getElementById("payCalCodeBack").value;
 		
 		if(CalCodeCode==null||CalCodeCode=='')
 		{
 			myAlert('�㷨����Ϊ��!');
 			return;
 		}
		var tUrl = "PDAlgoDefiMain.jsp?pdflag=<%=request.getParameter("pdflag")%>&riskcode=" + RiskCode+ "&algocode=" + CalCodeCode;
 		
 		if(DutyPayswitchCalCodeTypeBack(CalCodeCode))
 		{
 			if(tRadioValue!='Y')
 			{
 				myAlert('�㷨������������һ��,���޸ĺ��ٶ���!');
 				return false;
 			}
 			try{
 			var Business='01';
 			if(business==null||business==''||business=='undefined')Business='01';
 			else Business=business;
 			}catch(ex){
 			}
 			 tUrl =  "../ibrms/IbrmsPDAlgoDefiMain.jsp?pdflag=<%=request.getParameter("pdflag")%>&riskcode="+RiskCode
             + "&RuleName="+CalCodeCode
             + "&RuleDes="+RiskCode
             + "&CreateModul=1"
             + "&Creator="+tDutyPayCalCodeOperator
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
 	function DutyPayswitchCalCodeTypeBack(tCalCode)
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
 	function initDutyPayCalCodeBackMain(tRiskCode,tCalCode)
 	{
 		try
 		{
 			document.getElementById("hiddenDutyPayCalCodeBack").value = tCalCode;
 			document.getElementById("hiddenDutyPayRiskCodeBack").value = tRiskCode;
 		if(tCalCode==null||tCalCode=='')
 		{
 			return ;
 		}
 		if(tCalCode.length>=2&&tCalCode.substring(0,2).toUpperCase() == 'RU')
 		{
 			document.all("DutyPayCalCodeBackSwitchFlag")[0].checked = true;
 			//document.getElementById("CalCodeSwitchFlag").value  = 'Y';
 			DutyPayBackchangeRadioValue('Y');
 		}
 		else
 		{
 			document.all("DutyPayCalCodeBackSwitchFlag")[1].checked = true;
 			//document.getElementById("CalCodeSwitchFlag").value  = 'N';
 			DutyPayBackchangeRadioValue('N');
 		}
 		
 		}
 		catch(e)
 		{
 			myAlert('��ʼ�㷨��Ϣʧ��!');
 		}
 	}
 	
 	//�޸�Radio�ı���
 	function DutyPayBackchangeRadioValue(tValue)
 	{
 		//alert("tValue:"+tValue);
 		document.getElementById("DutyPayCalCodeBackSwitchFlag").value = tValue;
 	}
 </script>