<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�CommandMain.jsp
//�����ܣ����������
//�������ڣ�2008-9-17
//������  ��
//���¼�¼��  
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="CommandMain.js"></SCRIPT>
  <%@include file="CommandInit.jsp"%>

<%
//==================================================================BEGIN
	String BranchType = request.getParameter("BranchType");
//===================================================================END
%>
</head>
<body  onload="initForm();hiddenButton();" >

<form action="./CommandSave.jsp" method=post name=fm id=fm target="fraSubmit">
     <Table>
	<TR>
		<TD class=input style="display: none">
         <Input class="common" name=Transact>
       </TD>	  
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divQueryCondition);"></TD>
		<TD class=titleImg>�������ѯ</TD>
	</TR>
</Table>
<Div  id= "divQueryCondition" style= "display: ''" class="maxbox">
<table class=common>
	<TR class=common>
		<TD class=title5>���������</TD>
		<TD class=input5><Input class="wid" class=common name=CommandName id=CommandName
			elementtype=nacessary verify="���������|NOTNULL" ></TD>		
		<TD class=title5>��������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=CommandType id=CommandType class=codeno
			ondblclick="return showCodeList('IbrmsCommandType',[this,CommandTypeName],[0,1]);"
            onclick="return showCodeList('IbrmsCommandType',[this,CommandTypeName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsCommandType',[this,CommandTypeName],[0,1]);"><input
			class=codename name="CommandTypeName" id="CommandTypeName" readonly=true></TD></TR>
          <TR class=common>  
		<TD class=title5>��Ч��</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Valid id=Valid class=codeno
			ondblclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
            onclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsValid',[this,ValidName],[0,1]);"><input
			class=codename name="ValidName" id="ValidName" readonly=true></TD>
            <TD class=title5>���������</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=CommType id=CommType class=codeno
			ondblclick="return showCodeList('IbrmsCommType',[this,CommTypeName],[0,1]);"
            onclick="return showCodeList('IbrmsCommType',[this,CommTypeName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsCommType',[this,CommTypeName],[0,1]);"><input
			class=codename name="CommTypeName" readonly=true></TD>
	</TR>	
</Table>
</Div>
  <!--<p>
  <Div id= divCmdButton style="display: ''">-->
     <!--<INPUT VALUE="��ѯ�����" TYPE=button class="cssButton" onclick="queryClick()">
     <INPUT VALUE="���������" TYPE=button name=ic class="cssButton" onclick="insertClick()">
	<INPUT VALUE="�޸������" TYPE=button name=uc class="cssButton" onclick="updateClick()">
     <INPUT VALUE="ɾ�������" TYPE=button name=dc class="cssButton" onclick="deleteClick()">--><br>
      <a href="javascript:void(0);" class="button" onClick="queryClick();">��ѯ�����</a>
     <a href="javascript:void(0);" class="button" onClick="insertClick();">���������</a>
     <a href="javascript:void(0);" class="button" onClick="updateClick();">�޸������</a>
     <a href="javascript:void(0);" class="button" onClick="deleteClick();">ɾ�������</a>
<!--  </Div>-->

  <Div  id= "divQueryGrp" style= "display: ''">
    <table>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
      <td class= titleImg>
    	������б�
      </td>
    </table>
	<Div  id= "divPayPlan1" style= "display: ''">
     <table  class= common>
		<tr class="common">
			<td><input type=hidden name=BranchTyp value=<%=BranchType%>></td>
		</tr>
        <tr>
    	  	<td text-align: left colSpan=1>
	         <span id="spanQueryGrpGrid" ></span>
		</td>
		<TD class=input style="display: none">					
			
		</TD>
	</tr>
     </table>
       
</div></Div>
</form>

 <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
