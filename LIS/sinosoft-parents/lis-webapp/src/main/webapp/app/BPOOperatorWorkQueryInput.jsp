<%
//�������ƣ�BPOOperatorWorkQueryInput.jsp
//�����ܣ�¼�����쳣��������ͳ��
//�������ڣ�2007-09-19 16:53
//������  ��JL
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./BPOOperatorWorkQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>¼������쳣��������ͳ��</title>
</head>
<body>
  <form action="./BPOOperatorWorkQuerySave.jsp" method=post name=fm id=fm target="f1print">
    <table>
    	<tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);"></td>
    		<td class= titleImg>¼������쳣��������ͳ��</td>
    	</tr>
    </table>
    <Div  id= "divLCPol1" style= "display: ''" class="maxbox1">
      <table  class= common>
�������� <TR  class= common>
       		<TD  class= title5>��ʼ����</TD>
       		<TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="��ʼ����|NOTNULL" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
       		<TD  class= title5>��������</TD>
       		<TD  class= input5>
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��������|NOTNULL" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
            </TD>
        </TR>
      </table>
      
      </div>
      
      <!--<INPUT VALUE="��ӡ����" class=cssButton TYPE=button name=btnSubmit onclick="submitForm();"> -->
      <a href="javascript:void(0);" name=btnSubmit class="button" onClick="submitForm();">��ӡ����</a>
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
