<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�
//�����ܣ���������Ϣ����
//�������ڣ�2008-8-12
//������  ��
//���¼�¼��  
%>
<%
	String mKeyID = (String) request.getParameter("KeyID");
	String mEditFlag = request.getParameter("EditFlag")==null?"":(String) request.getParameter("EditFlag");
	String mMsgType = (String) request.getParameter("MsgType");
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
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <script src="../common/javascript/jquery.js"></script>
  <SCRIPT src="LDMsgResultInput.js"></SCRIPT>
  <SCRIPT src="LDMsgInfoInput.js"></SCRIPT>
  <%@include file="LDMsgInfoInputInit.jsp"%>


</head>
<script type="text/javascript">
	var jKeyID = '<%=mKeyID%>';
	var jEditFlag = '<%=mEditFlag%>';
</script>	
<body  onload="initForm();" >

<form action="./LDMsgInfoInputSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <Div  id= "divQueryGrp" style= "display: ''">
    <table>
	  <td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img>
	  </td>
      <td class= titleImg>
    	 ��Ϣ�б�
      </td>
    </table>

     <table  class= common>
        <tr>
    	  	<td style="text-align: left" colSpan=1>
	         <span id="spanQueryGrpGrid" ></span>
		</td>
		<TD class=input style="display: none">					
			
		</TD>
	</tr>
     </table>

</div>
<br/><br/><br/>


 <Table>
	<TR>		
		<TD class=input style="display: none">
         <Input class="common" name=itemAction>
       </TD>	
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divItemCondition);"></TD>
		<TD class=titleImg>��Ϣ��ϸ</TD>
	</TR>
</Table>

 <Div id=divItemCondition class=maxbox1 style="display: ''">
    <table class=common align=center>
	<TR class=common>
		<TD class=title>��Ϣ����</TD>
		<TD class=input><Input class=codeno readonly name=MsgType id=MsgType value='<%=mMsgType%>' ><input
			class=codename name="MsgTypeName" id=MsgTypeName readonly=true></TD>
		<TD class=title>����</TD>
		<TD class=input><Input class=codeno name=MsgLan id=MsgLan style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('language',[this,MsgLanName],[0,1]);"
			onkeyup="return showCodeListKey('language',[this,MsgLanName],[0,1]);"><input
			class=codename name="MsgLanName" id=MsgLanName readonly=true></TD>
		<TD class=input></TD>
		<TD class=title></TD>
	</TR>
	 
</Table>
<br />
<table class=common align=center>
		<TR class="common">
         <td class="common">
             <textarea rows="4" cols="100" name="MsgDetail" class=common value=""></textarea>
         </td>
   	</TR>
</Table>
</div>

</br>
</br>
<div id="buttonForm" style="display: none"  >
<input type="button" class=cssButton name="submitData" value="��  ��" onclick="submitForm()" />

<input type="button" class=cssButton name="submitData" value="ɾ  ��" onclick="deleteForm()" />
</div>


<input
	type="hidden" name="hiddenKeyID" value='<%=mKeyID%>'></input>

<input
	type="hidden" name="hiddenAction" value=''></input>
</form>

 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
