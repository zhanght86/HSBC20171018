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
<Div  id="DutyGetCalCodeDefMainTitleBack" style= "display: ''">
<table class=common border=0>
  <tr class=common>
  	<td class=title5>�㷨����</td>
  	<td class=common>
			<input type = "radio"  value = "Y"  name = "DutyGetCalCodeSwitchFlagBack" onclick="DutyGetchangeRadioValueBack('Y')" checked />��������
			<input type = "radio"  value = "N"  name = "DutyGetCalCodeSwitchFlagBack"   onclick="DutyGetchangeRadioValueBack('N')" />SQL
    	<!--input value="�����㷨����" onclick="InputCalCodeDefFace()" class="cssButton" type="button" -->
    </td>
  </tr>
  <input type= "hidden" name= "hiddenDutyGetCalCodeBack" id= "hiddenDutyGetCalCodeBack" value="">
  <input type= "hidden" name= "hiddenDutyGetRiskCodeBack" id= "hiddenDutyGetRiskCodeBack"  value="">
</table>
</Div>
 
 
 <script>
 	var tDutyGetCalCodeOperatorBack = "<%=mOperator%>";
 	//�����㷨�������
 	function InputDutyGetCalCodeDefFaceBack(business)
 	{
 		var tRadioValue = document.getElementById("DutyGetCalCodeSwitchFlagBack").value;
 	//	alert(tRadioValue);
 		if(tRadioValue==null||tRadioValue=='')
 		{
 			myAlert('��ѡ���㷨����!');
 			return;
 		}
 		var RiskCode = document.getElementById("hiddenDutyGetRiskCodeBack").value;
 		
 		if(RiskCode==null||RiskCode=='')
 		{
 			myAlert('���ֱ���Ϊ��!');
 			return;
 		}

 		var CalCodeCode = document.getElementById("hiddenDutyGetCalCodeBack").value;
 		
 		if(CalCodeCode==null||CalCodeCode=='')
 		{
 			myAlert('�㷨����Ϊ��!');
 			return;
 		}
		var tUrl = "PDAlgoDefiMain.jsp?pdflag=<%=request.getParameter("pdflag")%>&riskcode=" + RiskCode+ "&algocode=" + CalCodeCode;
 		
 		if(DutyGetswitchCalCodeTypeBack(CalCodeCode))
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
 	
 	//��ʼ���㷨��������ֱ������Ϣ
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
 			myAlert('��ʼ�㷨��Ϣʧ��!');
 		}
 	}
 	
 	//�޸�Radio�ı���
 	function DutyGetchangeRadioValueBack(tValue)
 	{
 		//alert("tValue:"+tValue);
 		document.getElementById("DutyGetCalCodeSwitchFlagBack").value = tValue;
 	}
 </script>