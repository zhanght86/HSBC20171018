<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�UWManuReport.jsp
//�����ܣ��˹��˱��˱�����¼��
//�������ڣ�
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWManuReport.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> �˱����������� </title>
  <%@include file="UWManuReportInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>');" >
  <form method=post name=fm target="fraSubmit" action= "./UWManuReportChk.jsp">
    <!-- �����˱���¼���֣��б� -->
    <table class= common>
    	<tr class= common>
    	    <td class= title>
    	        Ͷ������
    	    </td>
            <TD  class= input>
                <Input class= "readonly" name=ContNo >
            </TD>
    	    <td class= title>
    	        �˱���
    	    </td>
            <TD  class= input>
                <Input class= "readonly" name=Operator >
            </TD>
    	</tr>
    </table>
    <table class= common>
    	<TR  class= common>
          <TD  class= title>
            ��������
          </TD>
          <tr></tr>
          
      <TD  class= input> <textarea name="Content" cols="130" rows="30" witdh=100% class="common"></textarea></TD>
        </TR>
    </table>
      <INPUT type= "hidden" name= "ProposalNoHide" value= "">
    <Div  id= "divButton" style= "display: ''">
      <INPUT type= "button" class=common name= "sure" value="ȷ��" onclick="inputReport()">
      <INPUT type= "button" class=common name= "sure" value="��ѯ�¼��˱�Ա��������" onclick="window.open('./UWQuerySubReportMain.jsp?ContNo='+fm.ContNo.value);">
    </Div>
		
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
