<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�AcceptIssueDoc.jsp
//�����ܣ�
//�������ڣ�2006-04-07
//������  ��wentao
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String Branch = tG1.ComCode;
		String mOperator = tG1.Operator;
%>
<script>
	var moperator = "<%=mOperator%>"; //��¼�������
</script>
<head >
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./AcceptOtherDoc.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./AcceptOtherDocInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./AcceptOtherDocSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <table>
    	<tr>
				<td class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCode1);"></td>
				<td class= titleImg>ɨ�赥֤��Ϣ</td></tr></table>
    <div class="maxbox1">	 
    <Div  id= "divCode1" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>ӡˢ��</TD>
          <TD  class= input5><Input class= "common wid" name=BussNo id="BussNo"></TD>
          <TD  class= title5>�������</TD>
          <TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center" class='code' name=ManageCom id="ManageCom" value="<%=Branch%>"
          		onclick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);"  ondblclick="return showCodeList('station',[this],null,null,codeSql,'1',null,250);" 
            	onkeyup="return showCodeListKey('station',[this],null,null,codeSql,'1',null,250);"></TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>��ʼ����</TD>
          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼ����|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
          <TD  class= title5>����ʱ��</TD>
          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="����ʱ��|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        </TR>
      </table>
    </Div>
    </div>
    <div>
    </div>  
    <br>
    <INPUT VALUE="��    ѯ" TYPE=button class=cssButton onclick="easyQueryClick()">
  	<Div  id= "divCodeGrid" style= "display: ''" >
    	<table  class= common>
     		<tr  class= common>
  	  		<td text-align: left colSpan=1><span id="spanCodeGrid" ></span></td>
				</tr>
  		</table>
      <div align=center>
    	<INPUT VALUE="��  ҳ" TYPE=button class=cssButton90 onclick="getFirstPage();"> 
    	<INPUT VALUE="��һҳ" TYPE=button class=cssButton91 onclick="getPreviousPage();">
    	<INPUT VALUE="��һҳ" TYPE=button class=cssButton92 onclick="getNextPage();"> 
    	<INPUT VALUE="β  ҳ" TYPE=button class=cssButton93 onclick="getLastPage();">
      </div>
  	</div>
    <div>
    </div>
    <br>
    <INPUT VALUE="ǩ��ͬ��" TYPE=button class=cssButton name="Accept" onclick="submitForm()"> 
	<input type=hidden id="fmtransact" name="fmtransact">
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=Branch%>+"%#";
</script>
