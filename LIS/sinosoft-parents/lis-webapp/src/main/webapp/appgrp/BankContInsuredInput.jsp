<%
//�������ƣ�TempFeeInput.jsp
//�����ܣ������շѵ�����
//�������ڣ�2002-07-12 
//������  ��
//���¼�¼��  ������     ��������     ����ԭ��/����
%>
<!--%@include file="../common/jsp/UsrCheck.jsp"%-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<head >
<script>
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var prtNo = "<%=request.getParameter("prtNo")%>";
	if (prtNo == null)
	    prtNo="";	
	var checktype = "<%=request.getParameter("checktype")%>";	    
	var scantype = "<%=request.getParameter("scantype")%>";
	var LoadFlag = "<%=request.getParameter("LoadFlag")%>";  //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����
	if (LoadFlag == null)
	    Loadflag=1
        var ContType = "<%=request.getParameter("ContType")%>";  //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����
	if (ContType == "null")
	    ContType=1
	var Auditing = "<%=request.getParameter("Auditing")%>";  //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����
	if (Auditing == "null")
	    Auditing=0;
	var MissionID = "<%=request.getParameter("MissionID")%>";
	var SubMissionID = "<%=request.getParameter("SubMissionID")%>";
	var AppntNo = "<%=request.getParameter("AppntNo")%>";
	var AppntName = "<%=request.getParameter("AppntName")%>";
	var BQFlag = "<%=request.getParameter("BQFlag")%>";	
	var EdorType = "<%=request.getParameter("EdorType")%>";	
	var param="";	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
        <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>	
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	 <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="ProposalAutoMove.js"></SCRIPT> 	
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
 
  <SCRIPT src="BankContInsuredInput.js"></SCRIPT>
  <%@include file="BankContInsuredInit.jsp"%>
  <%if(request.getParameter("scantype")!=null&&request.getParameter("scantype").equals("scan")){%>  
  <SCRIPT src="../common/EasyScanQuery/ShowPicControl.js"></SCRIPT>
  <SCRIPT>window.document.onkeydown = document_onkeydown;</SCRIPT>
  <%}%>   
</head>
<body  onload="initForm();initElementtype();" >
<Form action="./BankContInsuredSave.jsp" method=post name=fm target="fraSubmit">
<input type=hidden id="fmAction" name="fmAction">
<input type=hidden id="ContType" name="ContType">
<input type=hidden id="GrpContNo" name="GrpContNo">

    <Div  id= "divFamilyType" style="display:'none'">
    <table class=common>
       <tr class=common>
          <TD  class= title>
            ���˺�ͬ����
          </TD>
          <TD  class= input>
            <Input class="code" name=FamilyType ondblclick="showCodeList('FamilyType',[this]);" onkeyup="return showCodeListKey('FamilyType',[this]);" onfocus="choosetype();">
          </TD>
       </tr>
    </table>
    </Div>
    <!--table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divGroupPol1);">
    		</td>
    		<td class= titleImg>
    			 ��ͬ��Ϣ
    		</td>
    	</tr>
    </table-->

       
    <Input type= "hidden" class="common" readonly name=ContNo >

       
    <DIV id="divPrtNo" style="display:'none'">
    	<table class=common>
    		<TR>
    			<TD  class= title>
    			  Ͷ��������
    			</TD>
    			<TD  class= input>
    			  <Input class="common" readonly name=ProposalContNo >
    			</TD>
    			<TD  class= title>
    			  ӡˢ����
    			</TD>
    			<TD  class= input>
    			  <Input class="common" readonly name=PrtNo verify="ӡˢ����|notnull" >
    			</TD>
    		</TR>
    	</table>
    </DIV>
    <Div  id= "divTempFeeInput" style= "display: ''">
    	<Table  class= common>
    		<tr>
    			<td text-align: left colSpan=1>
	  				<span id="spanInsuredGrid" >
	  				</span> 
	  			</td>
    		</tr>
    	</Table>
    </div>

   <%@include file="BankComplexInsured.jsp"%> 
       
     
  <!--table class=common>
   <TR  class= common> 
      <TD  class= title> �ر�Լ������ע </TD>
    </TR>
    <TR  class= common>
      <TD  class= title>
      <textarea name="GrpSpec" cols="120" rows="3" class="common" >
      </textarea></TD>
    </TR>
  </table-->  
	  
<DIV id=DivLCImpart STYLE="display:''">
<!-- ��֪��Ϣ���֣��б� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
</td>
<td class= titleImg>
�������˸�֪��Ϣ
</td>
</tr>
</table>

<div  id= "divLCImpart1" style= "display: ''">
<table  class= common>
<tr  class= common>
<td text-align: left colSpan=1>
<span id="spanImpartGrid" >
</span>
</td>
</tr>
</table>
</div>
</DIV>
<DIV id=DivLCImpart STYLE="display:''">
<!-- ��֪��Ϣ���֣��б� -->
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart2);">
</td>
<td class= titleImg>
�������˸�֪��ϸ��Ϣ
</td>
</tr>
</table>

<div  id= "divLCImpart2" style= "display: ''">
<table  class= common>
<tr  class= common>
<td text-align: left colSpan=1>
<span id="spanImpartDetailGrid" >
</span>
</td>
</tr>
</table>
</div>
</DIV>
<Div  id= "divAddDelButton" style= "display: ''" align=right>
    <input type =button class=cssButton value="��ӱ�������" onclick="addRecord();"> </TD>
    <input type =button class=cssButton value="ɾ����������" onclick="deleteRecord();"> </TD>
    <input type =button class=cssButton value="�޸ı�������" onclick="modifyRecord();"></TD>
</DIV>
<hr/>
<!-- ������������Ϣ���� -->
<DIV id=DivLCPol STYLE="display:''">
<table>
<tr>
<td class=common>
<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
</td>
<td class= titleImg>
��������������Ϣ
</td>
</tr>
</table>

<div  id= "divLCPol1" style= "display: ''">
<table  class= common>
<tr  class= common>
<td text-align: left colSpan=1>
<span id="spanPolGrid" >
</span>
</td>
</tr>
</table>
</div>
</DIV>
 <hr>
    <Div  id= "divGrpInputContButton" style= "display: 'none'" style="float: left"> 	
      <INPUT class=cssButton id="riskbutton4" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
      <INPUT class=cssButton VALUE=�������ѯ TYPE=button onclick="QuestQuery();">
      <INPUT class=cssButton id="riskbutton1" VALUE="��һ��" TYPE=button onclick="returnparent();">        
      <INPUT class=cssButton id="riskbutton3" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">        
   <HR align="LEFT" size="2" width="100%" > 
    </DIV>
     <Div  id= "divGrpInputoverButton" style= "display: 'none'" style="float: right"> 
      <INPUT class=cssButton id="riskbutton2" VALUE="¼�����"  TYPE=button onclick="GrpInputConfirm(1);">
      </Div>
      
    <Div  id= "divInputContButton" style= "display: ''" style="float: left"> 	
    <INPUT class=cssButton id="riskbutton4" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
      <INPUT class=cssButton VALUE=�������ѯ TYPE=button onclick="QuestQuery();">
      <INPUT class=cssButton id="riskbutton1" VALUE="��һ��" TYPE=button onclick="returnparent();">        
      <!--INPUT class=cssButton id="riskbutton2" VALUE="¼�����"  TYPE=button onclick="inputConfirm(1);"-->      
      <INPUT class=cssButton id="riskbutton3" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">        	
      <INPUT class=cssButton id="riskbutton33" VALUE="����ɾ��" TYPE=button onclick="DelRiskInfo();">        
    </DIV>
    <DIV id = "divApproveContButton" style = "display:'none'" style="float: left">
      <INPUT class=cssButton id="riskbutton5" VALUE="�����¼��" TYPE=button onClick="QuestInput();">
      <INPUT class=cssButton VALUE=�������ѯ TYPE=button onclick="QuestQuery();">
      <INPUT class=cssButton id="riskbutton5" VALUE="��һ��" TYPE=button onclick="returnparent();"> 
     <DIV id = "divCheckInsuredButton" style = "display:''" style="float: left">
      <INPUT class=cssButton VALUE=������У�� TYPE=button onclick='InsuredChk();'>     
     </DIV>   
    	<!--INPUT class=cssButton id="riskbutton6" VALUE="�������" TYPE=button onclick="inputConfirm(2);"-->
    	<INPUT class=cssButton id="riskbutton7" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">
    	<INPUT class=cssButton id="riskbutton33" VALUE="����ɾ��" TYPE=button onclick="DelRiskInfo();">       	
    </DIV>
    <DIV id = "divApproveModifyContButton" style = "display:'none'" style="float: left">
      <INPUT class=cssButton id="riskbutton12" VALUE="�����¼��" TYPE=button onClick="QuestInput();"> 
      <INPUT class=cssButton VALUE=�������ѯ TYPE=button onclick="QuestQuery();">
      <INPUT class=cssButton id="riskbutton9" VALUE="��һ��" TYPE=button onclick="returnparent();"> 
    	<!--INPUT class=cssButton id="riskbutton10" VALUE="�����޸����" TYPE=button onclick="inputConfirm(3);"-->
    	<INPUT class=cssButton id="riskbutton11" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">     	
    </DIV>
    <DIV id = "divQueryButton" style = "display:'none'" style="float: left">
      <INPUT class=cssButton id="riskbutton9" VALUE="��һ��" TYPE=button onclick="returnparent();"> 
      <INPUT class=cssButton id="riskbutton11" VALUE="����������Ϣ" TYPE=button onclick="intoRiskInfo();">     	
    </DIV>
      <div id="autoMoveButton" style="display: none">
	<input type="button" name="autoMoveInput" value="�涯����ȷ��" onclick="submitAutoMove(''+param+'');" class=cssButton>
        <INPUT class=cssButton id="riskbutton9" VALUE="����Ͷ����ҳ��" TYPE=button onclick="returnparent();"> 	
	<input type="button" name="AutoMovePerson" value="���Ƶڶ���������" onclick="AutoMoveForNext();" class=cssButton>
	<input type="button" name="Next" value="��������ҳ��" onclick="location.href='ProposalInput.jsp?LoadFlag='+LoadFlag+'&prtNo='+prtNo+'&scantype='+scantype+'&checktype='+checktype" class=cssButton>		
        <input type=hidden id="" name="autoMoveFlag">
        <input type=hidden id="" name="autoMoveValue"> 
        <input type=hidden id="" name="pagename" value="">      
                
      </div>          
	<input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<INPUT  type= "hidden" class= Common name= MissionID value= ""><!-- ������������� -->
  <INPUT  type= "hidden" class= Common name= SubMissionID value= "">
  <INPUT  type= "hidden" class= Common name= ProposalGrpContNo value= "">
  <INPUT  type= "hidden" class= Common name= AppntNo value= "">
  <INPUT  type= "hidden" class= Common name= AppntName value= "">
  <INPUT  type= "hidden" class= Common name= SelPolNo value= "">
  <INPUT  type= "hidden" class= Common name= SaleChnl value= "">
  <INPUT  type= "hidden" class= Common name= ManageCom value= "">
  <INPUT  type= "hidden" class= Common name= AgentCode value= "">
  <INPUT  type= "hidden" class= Common name= AgentGroup value= "">
  <INPUT  type= "hidden" class= Common name= GrpName value= "">
  <INPUT  type= "hidden" class= Common name= CValiDate value= "">
  <input type=hidden name=BQFlag>  
</Form> 
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
