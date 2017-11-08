<%@include file="../i18n/language.jsp"%>

<%@page contentType="text/html;charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//程序名称：
//程序功能：多语言信息管理
//创建日期：2008-8-12：  
	String mKeyID = (String) request.getParameter("KeyID");
	String mEditFlag = request.getParameter("EditFlag")==null?"":(String) request.getParameter("EditFlag");
	String mMsgType = (String) request.getParameter("MsgType");
%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/CustomDisplay.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>

 
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <script src="../common/javascript/jquery.js"></script>
  <SCRIPT src="LDMsgInput.js"></SCRIPT>
  <%@include file="LDMsgInputInit.jsp"%>

<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css><SCRIPT src="../common/laydate/laydate.js"></SCRIPT></head>
<script type="text/javascript">
	var jKeyID = '<%=mKeyID%>';
		var jMsgType = '<%=mMsgType%>';
	var jEditFlag = '<%=mEditFlag%>';
</script>	
<body  onload="initForm();" >

<form action="./LDMsgInputSave.jsp" method=post name=fm target="fraSubmit">
  <Div  id= "divQueryGrp" style= "display: ''">
    <table>
    <tr>
      <TD class=title5Img>
信息列表
      </TD>
      </tr>
    </table>

     <table  class= common>
        <TR>
    	  	<TD align=left colSpan=1>
	         <span id="spanQueryGrpGrid" ></span>
		</TD>
		<TD class=input5 style="display: none">					
			
		</TD>
	</TR>
     </table>

</div>
<br/><br/><br/>
 <Table>
	<TR>		
		<TD class=input5 style="display: none">
         <Input class="common" name=itemAction>
       </TD>	
		<TD class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divItemCondition);"></TD>
		<TD class=title5Img>信息明细</TD>
	</TR>
</Table>

 <Div id=divItemCondition style="display: ''">
    <table class=common align=center>
	<TR class=common>
		<TD class=title5>信息类型</TD>
		<TD class=input5><Input class=codeno verify="信息类型|NOTNULL" readonly="readonly" name=MsgType value='<%=mMsgType%>' ><input
			class=codename name="MsgTypeName" readonly="readonly"><font size=1 color='#ff0000'><b>*</b></TD>
		<TD class=title5><%=bundle.getString("handword_language")%></TD>
		<TD class=input5><Input class=codeno verify="<%=bundle.getString("handword_language")%>|NOTNULL" name=MsgLan ondblclick="return showCodeList('language',[this,MsgLanName],[0,1]);"
			onkeyup="return showCodeListKey('language',[this,MsgLanName],[0,1]);"><input
			class=codename name="MsgLanName" readonly="readonly"><font size=1 color='#ff0000'><b>*</b></TD>
		<TD class=input5></TD>
		<TD class=common></TD>
		<TD class=input5></TD>
		<TD class=common></TD>
	</TR>
	 
</Table>
<Table class=common align=center>
		<TR class="common">
         <td class="common">
             <textarea rows="4" cols="100" name="MsgDetail"  verify="信息|NOTNULL&len<1000" ></textarea><font size=1 color='#ff0000'><b>*</b></font>
         </td>
   	</TR>
</Table>
</div>
<BR></BR>
<BR></BR>
<div id="buttonForm" style="display: none"  >
<input type="button" class=cssButton name="submitData" value="保  存" onclick="submitForm()" />

<input type="button" class=cssButton name="submitData" value="删  除" onclick="deleteForm()" />
<input type="button" class=cssButton name="submitData" value="返  回" onclick="returnForm()" />
</div>


<input
	type="hidden" name="hiddenKeyID" value='<%=mKeyID%>'></input>

<input
	type="hidden" name="hiddenAction" value=''></input>
</form>

 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
