<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2005-01-26 13:18:16
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link rel="stylesheet" type="text/css" href="../common/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="../common/themes/icon.css">
  <link href="../common/css/mulLine.css" rel="stylesheet" type="text/css">
  <%@include file="ConfigCalendarFormatInit.jsp"%>
  <SCRIPT src="ConfigCalendarFormatInput.js"></SCRIPT>
</head>
<body  onload="initForm();" >
 
  <form action="./ConfigCalendarFormatSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table>
    	<tr>
    		<td class=common>
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDCode1);">
    		</td>
    		 <td class= titleImg>
        		 ������ʽ����
       		 </td>   		 
    	</tr>
    </table>
		 <div id="divLDCode1" style="display:''">
        <table class="common">
            <tr class="common">
                <td style="text-align:left" colSpan="1">
                    <span id="spanMulCalendarGrid">
                    </span>
                </td>
            </tr>
        </table>
    </div>	
	<INPUT class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return submitForm();">	<br>	
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="fmAction" name="fmAction">
  </Div>
  </form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
