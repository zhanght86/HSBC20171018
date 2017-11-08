<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=UTF-8" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="i18nManager.js"></SCRIPT> 
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <%@include file="i18nManagerInit.jsp"%> 
</head> 
<body  onload="initForm()" >
<form  method=post name=fm target="fraSubmit" action="i18nManagerSave.jsp"> 
  <br>
     <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaimEndGrid);"></td>
                <td class= titleImg>资源文件管理</td>
            </tr>
     </table>
   	<Table class= common>  	
     <TR class= common>
      <TD class= title> 日文显示 </TD>
      <TD class= input> <Input  name="japanese"></TD>
      <TD class= title> 中文显示 </TD>
      <TD class= input> <Input  name="chinese"></TD>
     </tr> 
     
  </table>
  <hr>
      <INPUT class=cssButton  VALUE="<%=bundle.getString("M0000045936")%>" TYPE=button name=querybt onclick="msginfoquery();">
        <table  class= common >
            <tr  class=common>
                <td text-align: colSpan=1 ><span id="spanMsgGrid" ></span></td>
            </tr>
        </table>
        <INPUT VALUE="<%=bundle.getString("M0000046063")%>" class=cssButton TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT VALUE="<%=bundle.getString("M0000045908")%>" class=cssButton TYPE=button onclick="turnPage.previousPage();">                     
        <INPUT VALUE="<%=bundle.getString("M0000045909")%>" class=cssButton TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT VALUE="<%=bundle.getString("M0000046064")%>" class=cssButton TYPE=button onclick="turnPage.lastPage();">     
   	<br>
   	<hr>
   	<Table class= common>  	
     <TR class= common>
     	 <TD class= title> 信息号 </TD>
      <TD class= input> <Input  name="msg_id"></TD>
      <TD class= title> 日文显示 </TD>
      <TD class= input> <Input  name="msg_ja"></TD>
      <TD class= title> 中文显示 </TD>
      <TD class= input> <Input  name="msg_cn"></TD>
     </tr>    
  </table>
    <INPUT class=cssButton  VALUE="<%=bundle.getString("M0000047094")%>" TYPE=button name=savebt onclick="SaveClick();">    
  	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </Form>
</body>
</html>
