<%
//�������ƣ�LLInqCourseMissInpute.jsp
//�����ܣ���ȡ�������������
%>

<html>
<!--�û�У����-->
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
  <%
      GlobalInput tGlobalInput = new GlobalInput(); 
      tGlobalInput = (GlobalInput)session.getValue("GI");
    %>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT> 
    <SCRIPT src="LLInqCourseMiss.js"></SCRIPT>
    <%@include file="LLInqCourseMissInit.jsp"%>
    <title>�ʱ���Ϣ</title>  
</head>
<body  onload="initForm();">
  <Form action=""  method=post name=fm target="fraSubmit">
    <Table>
          <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLInqApply);"></TD>
             <TD class= titleImg>��ȡ�����������</TD>
           </TR>
      </Table> 
        <Div  id= "DivLLInqApply" style= "display: ''">
        <span id="operateButton1" >
            <Table  class= common>
              <TR class= common>
                <TD text-align: left colSpan=1>
                      <span id="spanLLInqApplyGrid"></span> 
                    </TD>
                </TR>
            </Table>  
          <table> 
              <tr>  
                  <td><INPUT class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                  <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                  <td><INPUT class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                  <td><INPUT class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
              </tr> 
           </table>    
        </Div>   
      <%
      //�������������ر���
      %>   
      <Input type=hidden name="Operator" >
      <Input type=hidden name="ComCode" >
  </Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
