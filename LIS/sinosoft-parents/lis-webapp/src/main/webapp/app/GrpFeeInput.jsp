<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�GrpFeeInput.jsp
//�����ܣ�
//�������ڣ�2002-08-15 11:48:42
//������ ��CrtHtml���򴴽�
//���¼�¼�� ������  ��������   ����ԭ��/����
%>
<%
String RiskName = request.getParameter("RiskName");
String GrpPolNo = request.getParameter("GrpPolNo");
String LoadFlag = request.getParameter("LoadFlag");
%>
<script>
	var ProposalGrpContNo = "<%=request.getParameter("ProposalGrpContNo")%>";
	var LoadFlag ="<%=request.getParameter("LoadFlag")%>";
	var scantype = "<%=request.getParameter("scantype")%>";
	var prtNo = "<%=request.getParameter("prtNo")%>";
  var polNo = "<%=request.getParameter("polNo")%>";
  var MissionID = "<%=request.getParameter("MissionID")%>";
  var SubMissionID = "<%=request.getParameter("SubMissionID")%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="GrpFeeInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GrpFeeInit.jsp"%>

<SCRIPT src="ProposalAutoMove5.js"></SCRIPT>
	<%
		if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan"))
		{
		  loggerDebug("GrpFeeInput","ɨ������"+request.getParameter("scantype"));
	%>
      <SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
      <SCRIPT>window.document.onkeydown =document_onkeydown;</SCRIPT>
	<%
	  }
	%>

</head>
<body onload="initForm();">
	<form method=post name=fm id="fm" target="fraSubmit" action="GrpFeeSave.jsp">
		<table>
			<tr>
				<td class=common>
    				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
				</td>
				<td class=titleImg align=center>��ͬ������Ϣ</td>
			</tr>
		</table>
		<div class="maxbox1">
		<div  id= "divFCDay" style= "display: ''"> 
		<table class=common>
			<TR class=common>
				<TD class=title>
					�����ͬ��
				</TD>
				<TD class=input>
					<Input class="readonly wid" readonly name=ProposalGrpContNo id="ProposalGrpContNo" value="">
					<input type=hidden name=GrpContNo id="GrpContNo" value="">					
				</TD>
				<td >
					<a href="javascript:void(0)" class=button onclick="RiskQueryClick();">������Ϣ��ѯ</a>
					<!-- <input type=button class="cssButton" value="������Ϣ��ѯ" onclick="RiskQueryClick();"> -->
				</td>
			</tr>
			<tr class=common>
				<TD class=title>
					���ֱ���
				</TD>
				<TD class=input>
					<Input style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" class=codeno name=RiskCode id="RiskCode" onchange="CheckRisk();"  onclick="return showCodeList('GrpRisk',[this,RiskCodeName,RiskFlag],[0,1,3],null,fm.GrpContNo.value,'b.GrpContNo',1);" ondblclick="return showCodeList('GrpRisk',[this,RiskCodeName,RiskFlag],[0,1,3],null,fm.GrpContNo.value,'b.GrpContNo',1);" onkeyup="return showCodeListKey('GrpRisk',[this,RiskCodeName,RiskFlag],[0,1,3],null,fm.GrpContNo.value,'b.GrpContNo');"><input class=codename name=RiskCodeName id="RiskCodeName" readonly=true>
					<input type=hidden name=RiskFlag id="RiskFlag">
				</TD>
			
				<TD id="divmainriskname" style="display:none" class=title>
					���ձ���
				</TD>
				<TD id="divmainrisk" style="display:none" class=input>
					<Input class=codeno maxlength=6 name=MainRiskCode id="MainRiskCode" ondblclick="getriskcode();return showCodeList('GrpMainRisk',[this,MainRiskCodeName],[0,1],null,acodeSql,'b.GrpContNo',1);" onkeyup="getriskcode();return showCodeListKey('GrpMainRisk',[this,MainRiskCodeName],[0,1],null,acodeSql,'b.GrpContNo',1);"><Input class=codename name="MainRiskCodeName" id="MainRiskCodeName" readonly=true>
				</TD>
				<td>
					<a href="javascript:void(0)" class=button onclick="QueryDutyClick();">�������β�ѯ</a>
					<!-- <Input VALUE="�������β�ѯ" class=cssButton TYPE=button onclick="QueryDutyClick();"> -->
				</td>
			</TR>
		</table>
		</div>
		</div>
		<table  class= common>
			<tr  class= common>
				<td text-align: left colSpan=1>
					<span id="spanContPlanDutyGrid" ></span>
				</td>
			</tr>
		</table>  
		<div id="divRiskAdd" style= "display: ''" align= left> 
			<a href="javascript:void(0)" class=button onclick="RiskAddClick();">��������Ҫ��</a>
		    <!-- <Input VALUE="��������Ҫ��" class=cssButton  TYPE=button onclick="RiskAddClick();"> -->
		</div>
		<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divContPlanGrid);">
				</td>
				<td class= titleImg>�����ر���Ϣ</td>
			</tr>
		</table>
		<div id="divContPlanGrid" style="display: ''" align=center>
    		<table  class= common>
    			<tr  class= common>
    				<td text-align: left colSpan=1>
    					<span id="spanContPlanGrid" ></span>
    				</td>
    			</tr>
    		</table>
    		<Div  id = "divPage" align=center style = "display: 'none' ">
    			<INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();">
    			<INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
    			<INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();">
    			<INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">
		    </Div>
		</div>
<!--
		<br>
		<input type=button class="cssButton" value="��  ѯ" onclick="easyQueryClick();">
-->
		
	
		<!--table>
			<tr>
				<td class=common>
					<IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divGrpFee);">
				</td>
				<td class=titleImg>
					���ֹ������ϸ
				</td>
			</tr>
		</table>
		<Div id="divGrpFee" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanGrpFeeGrid">
						</span>
					</td>
				</tr>
			</table>
		</div>
		<br-->
		<Div  id= "divRiskSave" style= "display: ''" align= right> 
			<a href="javascript:void(0)" class=button onclick="submitForm();">������Ϣ����</a>
			<a href="javascript:void(0)" class=button onclick="UptContClick();">������Ϣ����</a>
			<a href="javascript:void(0)" class=button onclick="DelContClick();">������Ϣɾ��</a>
			<!-- <input type=button class="cssButton" value="������Ϣ����" onclick="submitForm();">
			<input type=button class="cssButton" value="������Ϣ����" onclick="UptContClick();">
			<input type=button class="cssButton" value="������Ϣɾ��" onclick="DelContClick();"> -->
		</Div>
		<hr class="line">
		<Div id="divPubAccGrid0" style="display:''">
			<div>
				<br>
				<a href="javascript:void(0)" style="display:none" class=button name="btnDefPubAcc" onclick="ShowPubAcc();">���幫���ʻ�</a>
		    	<a href="javascript:void(0)" class=button onclick="QureyPubAcc();">��ѯ�����ʻ�</a>
				
			</div>
		    <br>
    		<!-- <input type=button class="cssButton" name="btnDefPubAcc" value="���幫���ʻ�" onclick="ShowPubAcc();">
    		<input type=button class="cssButton" value="��ѯ�����ʻ�" onclick="QureyPubAcc();">  -->
		</Div> 
		<Div id="divPubAccGrid" style="display:none">
            <table class=common>
            	<tr class=common>
            		<td text-align: left colSpan=1><span id="spanPubAccGrid"></span></td>
            	</tr>
            </table>
			<Div  id= "PubAccSave" style= "display: ''" align= right>
				<a href="javascript:void(0)" class=button onclick="SavePubAcc();">�����ʻ�����</a>
				<a href="javascript:void(0)" class=button onclick="UpdatePubAcc();">�����ʻ�����</a>
				<a href="javascript:void(0)" class=button onclick="DelPubAcc();">�����ʻ�ɾ��</a>
				<a href="javascript:void(0)" class=button onclick="CompPubAcc();">�����ʻ����</a>
    			<!-- <input type=button class="cssButton" value="�����ʻ�����" onclick="SavePubAcc();">
    			<input type=button class="cssButton" value="�����ʻ�����" onclick="UpdatePubAcc();">
    			<input type=button class="cssButton" value="�����ʻ�ɾ��" onclick="DelPubAcc();">
    			<input type=button class="cssButton" value="�����ʻ����" onclick="CompPubAcc();"> -->
			</Div>
		</Div>
		<Div id="divPubAmntGrid0" style="display:''">
		<hr class="line">
			<br>
			<div>
			<a href="javascript:void(0)" class=button style="display:none" name="btnDefPubAmnt" onclick="ShowPubAmnt();">���幫������</a>
			<a href="javascript:void(0)" class=button onclick="PubAmntQueryClick();">��ѯ��������</a>
			</div>
    		<!-- <input type=button class="cssButton" name="btnDefPubAmnt" value="���幫������" onclick="ShowPubAmnt();">
    		<input type=button class="cssButton" value="��ѯ��������" onclick="PubAmntQueryClick();"> -->
    		<br> 
		</Div> 
		<Div id="divPubAmntGrid" style="display:none">
            <table class=common>
                <tr class=common>
                	<td text-align: left colSpan=1><span id="spanPubAmntGrid"></span></td>
                </tr>
            </table>
			<Div  id= "PubAmntSave" style= "display: ''" align= right>
				<a href="javascript:void(0)" class=button onclick="SavePubAmnt();">���������</a>
				<a href="javascript:void(0)" class=button onclick="UptContClick();">�����������</a>
				<a href="javascript:void(0)" class=button onclick="DelPubAmnt();">��������ɾ��</a>
    			<!-- <input type=button class="cssButton" value="���������" onclick="SavePubAmnt();">
    			<input type=hidden class="cssButton" value="�����������" onclick="UptContClick();">
    			<input type=button class="cssButton" value="��������ɾ��" onclick="DelPubAmnt();"> -->			
			</Div>
		</Div>
		<input type=hidden name=PlanType>
		<hr class="line">
		<Div  id= "divRiskRela" style= "display: ''" align= left>
			<br>
			<a href="javascript:void(0)" class=button onclick="returnparent();">��һ��</a>
			<a href="javascript:void(0)" class=button onclick="GrpRiskFeeInfo();">����Ѷ���</a>
			<a href="javascript:void(0)" class=button onclick="PayRuleInfo();">�ɷѶ���</a>
			<a href="javascript:void(0)" class=button onclick="AscriptionRuleInfo();">����������</a>
			<a href="javascript:void(0)" class=button onclick="GrpAccTrigger();">�˻�������</a>
			<a href="javascript:void(0)" class=button style= "display: none" onclick="feeRecord();">�����Ѷ���</a>
			<a href="javascript:void(0)" class=button onclick="grpRiskPlanInfo();">���ռƻ�����</a>
			<a href="javascript:void(0)" class=button onclick="grpInsuList();">�������嵥</a>
			<!-- <input type=button class="cssButton" value="��һ��" onclick="returnparent();">
			<Input VALUE="����Ѷ���" class=cssButton  TYPE=button onclick="GrpRiskFeeInfo();">
			<Input VALUE="�ɷѶ���" class=cssButton  TYPE=button onclick="PayRuleInfo();">
		    <Input VALUE="����������" class=cssButton  TYPE=button onclick="AscriptionRuleInfo();">
			<Input VALUE="�˻�������" class=cssButton TYPE=button onclick="GrpAccTrigger();">
			<Input VALUE="�����Ѷ���" class=cssButton TYPE=button onclick="feeRecord();" style= "display: 'none'">
			<Input VALUE="���ռƻ�����" class=cssButton TYPE=button onclick="grpRiskPlanInfo();" >
			<Input VALUE="�������嵥" class=cssButton TYPE=button onclick="grpInsuList();"> -->
		</Div>
		<Input type=hidden name=AppntNo id="AppntNo" value="">
		<input type=hidden name=mOperate id="mOperate">
	</form>
	<br>
	<br>
	<br>
	<br>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
<script>
  var acodeSql ="";
function getriskcode()
{
 acodeSql ="#"+fm.GrpContNo.value+"# and a.riskcode in (select riskcode from lmriskrela where relariskcode =#"+fm.RiskCode.value+"#)";
}
</script>
</html>
