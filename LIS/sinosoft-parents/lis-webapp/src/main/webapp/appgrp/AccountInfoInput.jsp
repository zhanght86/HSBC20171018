<!--
//*******************************************************
//* �������ƣ�AccountInfoInput.jsp
//* �����ܣ�ҳ���� �ӹ�˾¼�� ��ť
//* �������ڣ�2010-09-09
//*	������:	 wangxw@sinosoft.com.cn 
//* ���¼�¼��  ������    ��������     ����ԭ��/����
//******************************************************
-->


<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
String GrpContNo = request.getParameter("GrpContNo");
String PrtNo = request.getParameter("PrtNo");
String ManageCom = request.getParameter("ManageCom");
String LoadFlag = request.getParameter("LoadFlag");
String CValiDate=request.getParameter("CValiDate");
String SupGrpNo = request.getParameter("SupGrpNo");
String SupGrpName = request.getParameter("SupGrpName");

%>
<head>
<script>
var MissionID = "<%=request.getParameter("MissionID")%>";
var scantype  = "<%=request.getParameter("scantype")%>";
var SubMissionID ="<%=request.getParameter("SubMissionID")%>";
var ActivityID = "<%=request.getParameter("ActivityID")%>";
var NoType = "<%=request.getParameter("NoType")%>";
var CValiDate="<%=request.getParameter("CValiDate")%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <script src="AccountInfoInput.js"></SCRIPT>
    <%@include file="AccountInfoInit.jsp"%>
  </head>
<body  onload="initForm();initElementtype()" >
<Form action="./AccountInfoSave.jsp" method=post name=fm id="fm" target="fraSubmit">
	
<!-- ��ͬ��Ϣ -->
<Div  id= "divConttInfo" style= "display:  " >
<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;">
				</td>
				<td class= titleImg>��ͬ��Ϣ</td>
			</tr>
</table>
<div class="maxbox">
		<table  class= common align=center>
			<TR  class= common>
				<TD  class= title>Ͷ��������</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=GrpContNo id="GrpContNo" value="<%=GrpContNo%>">
					<Input type=hidden name=PrtNo value="<%=PrtNo%>">
					<input type=hidden name=mOperate>
					<input type=hidden name=CValiDate value="<%=CValiDate%>">
					<input type=hidden name=mFlagStr>
				</TD>
				<TD  class= title>�������</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=ManageCom id="ManageCom" value="<%=ManageCom%>">
				</TD>
				<TD  class= title>�ܹ�˾�ͻ���</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=SupGrpNo id="SupGrpNo" value="<%=SupGrpNo%>">
				</TD>
			</TR>
			<TR  class= common>
				
				<TD  class= title>�ܹ�˾��λ����</TD>
				<TD  class= input>
					<Input class="readonly wid" readonly name=SupGrpName id="SupGrpName"  value="<%=SupGrpName%>"
				</TD>
			</TR>
		</table>
</div>

<!-- �ӹ�˾��Ϣ -->

<table>
			<tr>
				<td class=common>
					<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
				</td>
				<td class= titleImg>�ӹ�˾��Ϣ</td>
			</tr>
</table>
<Div  id= "divAccountInfo" class="maxbox1" style= "display:  " >
			<table class=common align=center>
				<tr>
					<td class=title>��λ����</td>
					<td class=input>
						<input class="common wid" name=GrpName id="GrpName" elementtype=nacessary onchange=checkuseronly(this.value) verify="��λ����|notnull&len<=60">
					</td>
					<td class=title>�ʲ��ܶ�(Ԫ)</td>
					<td class=input>
						<input class="common wid" name=Asset id="Asset">
					</td>
					<td class=title>��λ����</td>
					<td class=input>
						<input class=codeno name=GrpNature id="GrpNature" style="background: url(../common/images/select--bg_03.png) no-repeat center; " verify="��λ����|code:grpNature&len<=10" onClick="showCodeList('GrpNature',[this,GrpNatureName],[0,1],null,null,null,null,100);" onDblClick="showCodeList('GrpNature',[this,GrpNatureName],[0,1],null,null,null,null,100);" onKeyUp="showCodeListKey('GrpNature',[this,GrpNatureName],[0,1],null,null,null,null,100);"><input class=codename name=GrpNatureName id="GrpNatureName" readonly=true >
					</td>
				</tr>				
				<tr class=common>
					<td class=title>��ҵ���</td>
					<td class=input>
						<input class=codeno name=BusinessType id="BusinessType" style="background: url(../common/images/select--bg_03.png) no-repeat center; " verify="��ҵ���|notnull&code:BusinessType&len<=20"  onClick="return showCodeList('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null);" onDblClick="return showCodeList('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null);" onKeyUp="return showCodeListKey('BusinessType',[this,BusinessTypeName],[0,1],null,null,null,null);"><input class=codename name=BusinessTypeName id="BusinessTypeName" readonly=true elementtype=nacessary >
					</td>
					<td class=title>Ա������</td>
					<td class=input>
						<input class="common wid" name=Peoples id="Peoples"   verify="��λ������|int">
					</td>
					<td class=title>��λ����</td>
					<td class=input>
						<input class="common wid" name=Fax id="Fax">
					</td>
				</tr>
				<tr>
					<td class=title>��λ���˴���</td>
					<td class=input>
						<input class="common wid" name=Corporation id="Corporation" verify="��λ���˴���|len<=20">
					</td>
				</tr>
				<tr>
					<td class=title>��λ��ַ����</td>
					<td class=input>
						<input class="code" name="GrpAddressNo" id="GrpAddressNo" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" onClick="getaddresscodedata();return showCodeListEx('GetGrpAddressNo',[this],[0], ,  ,  , true);"  ondblclick="getaddresscodedata();return showCodeListEx('GetGrpAddressNo',[this],[0], ,  ,  , true);" onKeyUp="getaddresscodedata();return showCodeListKeyEx('GetGrpAddressNo',[this],[0], ,  ,  , true);">
					</td>
				</tr>
				<tr class=common>
					<td class=title>��λ��ַ</td>
					<td class=input colspan="3">
						<input class="common3" name=GrpAddress id="GrpAddress"  verify="��λ��ַ|len<=60">
					</td>
					<td class=title>��������</td>
					<td class=input>
						<input class="common wid" name=GrpZipCode id="GrpZipCode" maxlength=6  verify="��������|zipcode">
					</td>
				</tr>

				<tr class=common>
					<td class=title>������ϵ��һ</td>
				</tr>
				<tr class=common>
					<td class=title>��  ��</td>
					<td class=input>
						<input class="common wid" name=LinkMan1 id="LinkMan1"  verify="������ϵ��һ����|len<=10">
					</td>
					<td class=title>��ϵ�绰</td>
					<td class=input>
						<input class="common wid" name=Phone1 id="Phone1"  verify="������ϵ��һ��ϵ�绰|len<=30">
					</td>
					<td class=title>E-MAIL</td>
					<td class=input>
						<input class="common wid" name=E_Mail1 id="E_Mail1" verify="������ϵ��һE-MAIL|len<=60&Email">
					</td>
				</tr>
				<tr class=common>
					<td class=title>��  ��</td>
					<td class=input colspan=3>
						<input class="common wid"3 name=Department1 id="Department1" verify="������ϵ��һ����|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>������ϵ�˶�</td>
				</tr>
				<tr class=common>
					<td class=title>��  ��</td>
					<td class=input>
						<input class="common wid" name=LinkMan2 id="LinkMan2" verify="������ϵ�˶�����|len<=10">
					</td>
					<td class=title>��ϵ�绰</td>
					<td class=input>
						<input class="common wid" name=Phone2 id="Phone2" verify="������ϵ�˶���ϵ�绰|len<=30">
					</td>
					<td class=title>E-MAIL</td>
					<td class=input>
						<input class="common wid" name=E_Mail2 id="E_Mail2" verify="������ϵ�˶�E-MAIL|len<=60&Email">
					</td>
				</tr>
				<tr class=common>
					<td class=title>��  ��</td>
					<td class=input colspan=3>
						<input class="common wid"3 name=Department2 id="Department2" verify="������ϵ�˶�����|len<=30">
					</td>
				</tr>
				<tr class=common>
					<td class=title>���ʽ</td>
					<td class=input>
						<input class=codeno name=GetFlag id="GetFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onClick="return showCodeList('paymode',[this,GetFlagName],[0,1]);"  ondblclick="return showCodeList('paymode',[this,GetFlagName],[0,1]);" onKeyUp="return showCodeListKey('PayMode',[this,GetFlagName],[0,1]);"><input class=codename name=GetFlagName id="GetFlagName" readonly=true >
					</td>
					<td class=title>��������</td>
					<td class=input>
            <Input NAME=BankCode id="BankCode" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center;" VALUE="" CLASS="code" MAXLENGTH=20 readonly onClick="return queryBank();"  ondblclick="return queryBank();" onKeyUp=" queryBank();" > 						
					</td>
					<td class=title>��  ��</td>
					<td class=input>
						<input class="common wid" name=BankAccNo id="BankAccNo" verify="�ʺ�|len>=6&len<=40">
					</td>
				</tr>
	
		</table>
</Div>
</br>
<Div  id= "divAddDelButton" style= "display:  " style="float: right">
    <input type =button class=cssButton value="����ӹ�˾" onClick="addRecord();"> 
    <input type =button class=cssButton value="ɾ���ӹ�˾" onClick="deleteRecord();"> 
    <input type =hidden class=cssButton value="�޸��ӹ�˾" onClick="modifyRecord();">
</DIV>
</br></br>


<div  id= "divLCImpart1" style= "display: ">
<table  class= common>
	<tr  class= common>
		<td text-align: left colSpan=1>
			<span id="spanAccountInfoGrid" >
			</span>
		</td>
	</tr>
</table>
</div>
</br></br>
 <hr class="line">
    <DIV id = "divaddPerButton" style = "display: " style="float: left">
      <INPUT class=cssButton id="riskbutton9" VALUE="��һ��" TYPE=button onClick="returnparent();">      
    </DIV>     
        <input type=hidden id="" name="autoMoveFlag">
        <input type=hidden id="" name="autoMoveValue">
        <input type=hidden id="" name="pagename" value="">

      </div>
	<input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<INPUT  type= "hidden" class= Common name= MissionID value= ""><!-- ������������� -->
  <INPUT  type= "hidden" class= Common name= SubMissionID value= "">
  <INPUT  type= "hidden" class= Common name= CustomerNo value= "">    
  <INPUT  type= "hidden" class= Common name= AddressNo value= "">
  <!--����������ʱ�õ���ԭ��������ͬ��-->
  <INPUT  type= "hidden" class= Common name= vContNo value= "">
  <input type=hidden name=BQFlag>
  <input type=hidden name=EdorType>
  <input type=hidden name=EdorTypeCal>
  <input type=hidden name=EdorValiDate>
</Form>
<Br><Br><Br><Br><Br>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
