<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
    GlobalInput tGI = (GlobalInput)session.getValue("GI");
    String Branch = tGI.ComCode;
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="./PersonPolPrintCount.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./PersonPolPrintCountInit.jsp"%>
  <title>������ӡ������Ϣ��ѯ</title>
</head>
<script>
var codeSql = "1  and code like #"+<%=Branch%>+"%#";
var tComCode=<%=Branch%>;
</script>
<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <table>
    	<tr>
        <td class=common>
			    <IMG src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPolContion);">
    		</td>
    		<td class= titleImg>������ӡ������Ϣ��ѯ</td>
    	</tr>
    </table>
    <div class="maxbox1">
    <Div id= "divLCPolContion" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD class=title5>������� </TD>
          <TD class= input5><Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" type="text" class="codeno" name=ManageCom onclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql);"  ondblclick="return showCodeList('station',[this,ManageComName],[0,1],null,codeSql);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1],null,codeSql);"><input class="codename" name="ManageComName" > </TD>
        </TR>
        <TR class= common>
          <TD  class= title5>ǩ����ʼ����</TD>
          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="ǩ����ʼ����|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
          <TD  class= title5>ǩ����������</TD>
          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="ǩ����������|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        </TR>
      </table>
    </Div>
    </div>
    <div>
      <a href="javascript:void(0)" class=button onclick="easyQueryClick();">��  ѯ</a>
    <a href="javascript:void(0)" class=button onclick="easyPrint();">��  ӡ</a>
    </div>
    <br>
		<!-- <input class="cssButton" TYPE=button value="��       ѯ" onclick="easyQueryClick()">
		<input class="cssButton" TYPE=button value="��       ӡ" onclick="easyPrint()"> -->
	  
    <Div style= "display: ''" align=center>
	    <table  class= common>
	    	<tr  class= common>
	      	<td text-align:  colSpan=1>
		  			<span id="spanCodeGrid" ></span> 
	  	  	</td>
	  		</tr>
	    </table>
	    <INPUT VALUE="��ҳ" class="cssButton90" TYPE=button onclick="getFirstPage();"> 
	    <INPUT VALUE="��һҳ" class="cssButton91" TYPE=button onclick="getPreviousPage();"> 					
	    <INPUT VALUE="��һҳ" class="cssButton92" TYPE=button onclick="getNextPage();"> 
	    <INPUT VALUE="βҳ" class="cssButton93" TYPE=button onclick="getLastPage();"> 	
  	</div>
  	 <input type=hidden id="ComCode" name="ComCode">
  	<br> 	
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>

