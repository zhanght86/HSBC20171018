<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�bomMain.jsp
//�����ܣ�BOM�������
//�������ڣ�2008-8-12
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
  <SCRIPT src="bomMain.js"></SCRIPT>
  <%@include file="bomFunInit.jsp"%>

<%
//==============================================================BEGIN
	String BranchTyp = request.getParameter("BranchTyp");
//===============================================================END
%>

</head>
<body  onload="initForm();hiddenButton();" >

<form action="./bomFunMan.jsp" method=post name=fm id=fm target="fraSubmit">
     <Table>
	<TR>
		<TD class=input style="display: none">
         <Input class="common" name=Action>
       </TD>	  
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divQueryCondition);"></TD>
		<TD class=titleImg>BOM�����ѯ</TD>
	</TR>
</Table>
<Div  id= "divQueryCondition" style= "display: ''" class="maxbox1">
<table class=common align=center>
	<TR class=common>
		<TD class=title5>BOMӢ����</TD>
		<TD class=input5><Input class="wid" class=common name=eName id=eName
			elementtype=nacessary verify="BOMӢ����|NOTNULL" ></TD>
		<TD class=title5>BOM������</TD>
		<TD class=input5><Input class="wid" class=common name=cName id=cName
			elementtype=nacessary verify="BOM������|NOTNULL"></TD>

	</TR>
	<TR class=common>
		<TD class=title5>BOMҵ��ģ��</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Business id=Business class=codeno
			ondblclick="return showCodeList('IbrmsBusiness',[this,BusinessName],[0,1]);"
            onclick="return showCodeList('IbrmsBusiness',[this,BusinessName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsBusiness',[this,BusinessName],[0,1]);"><input
			class=codename name="BusinessName" id="BusinessName" readonly=true></TD>
		<TD class=title5>BOM��Ч��</TD>
		<TD class=input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Valid id=Valid class=codeno
			ondblclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
            onclick="return showCodeList('IbrmsValid',[this,ValidName],[0,1]);"
			onkeyup="return showCodeListKey('IbrmsValid',[this,ValidName],[0,1]);"><input
			class=codename name="ValidName" id="ValidName" readonly=true></TD>
	</TR>
</Table>
</Div>
  
  <!--<Div id= divCmdButton style="display: ''">-->
    <!--<INPUT VALUE="��ѯBOM" TYPE=button class="cssButton" onclick="queryClick()">
     <INPUT VALUE="����BOM" TYPE=button name=ic class="cssButton" onclick="insertClick()">
	<INPUT VALUE="�޸�BOM" TYPE=button name=uc class="cssButton" onclick="updateClick()">
     <INPUT VALUE="ɾ��BOM" TYPE=button name=dc class="cssButton" onclick="deleteClick()">-->
   <a href="javascript:void(0);" class="button" onClick="queryClick();">��ѯBOM</a>
     <a href="javascript:void(0);" class="button" onClick="insertClick();">����BOM</a>
     <a  href="javascript:void(0);" class="button" onClick="updateClick();">�޸�BOM</a>
     <a  href="javascript:void(0);" class="button" onClick="deleteClick();">ɾ��BOM</a>
<!--  </Div>-->

  <Div  id= "divQueryGrp" style= "display: ''">
    <table>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
      <td class= titleImg>
    	 BOM�б�
      </td>
    </table>
<Div  id= "divPayPlan1" style= "display: ''">
     <table  class= common>
        <tr>
    	  	<td text-align: left colSpan=1>
	         <span id="spanQueryGrpGrid" ></span>
		</td>
		<TD class=input style="display: none">					
			
		</TD>
	</tr>
     </table>
       
</div></Div>
<br/>


 <!-- ����ҳ�� -->
 <Table>
	<TR>		
		<TD class=input style="display: none">
         <Input class="common" name=itemAction>
       </TD>	
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divItemCondition);"></TD>
		<TD class=titleImg>������Ϣ</TD>
	</TR>
</Table>

 <!--<Div id=divItemCondition style="display: ''">
     <INPUT VALUE="��ѯ����" TYPE=button class="cssButton" onclick="itemQuery()">
     <INPUT VALUE="���Ӵ���" TYPE=button name=ii class="cssButton" onclick="itemInsert()">
	<INPUT VALUE="�޸Ĵ���" TYPE=button name=iu class="cssButton" onclick="itemUpdate()">
     <INPUT VALUE="ɾ������" TYPE=button name=id class="cssButton" onclick="itemdelete()">-->
<a href="javascript:void(0);" class="button" onClick="itemQuery();">��ѯ����</a>
     <a  href="javascript:void(0);" class="button" onClick="itemInsert();">���Ӵ���</a>
     <a  href="javascript:void(0);" class="button" onClick="itemUpdate();">�޸Ĵ���</a>
     <a  href="javascript:void(0);" class="button" onClick="itemdelete();">ɾ������</a>
  </Div>

  <Div  id= "divItemQueryGrp" style= "display: ''">
    <table>
    <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,poop);"></td>
      <td class= titleImg> �����б�   </td>
    </table>
<Div  id= "poop" style= "display: ''">
     <table  class= common>
	<tr class="common">
		<td><input type=hidden name=BranchTyp value=<%=BranchTyp%>></td>
	</tr>
        <tr>
    	  	<td text-align: left colSpan=1>
	         <span id="spanItemGrid" ></span>
		</td>
	</tr>
     </table>
       
</div></Div>
</form>

 <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
