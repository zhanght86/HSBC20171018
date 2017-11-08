<%
//程序名称：LLInqCourseMissInpute.jsp
//程序功能：获取调查任务处理队列
%>

<html>
<!--用户校验类-->
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
    <title>呈报信息</title>  
</head>
<body  onload="initForm();">
  <Form action=""  method=post name=fm target="fraSubmit">
    <Table>
          <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLInqApply);"></TD>
             <TD class= titleImg>获取调查任务队列</TD>
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
                  <td><INPUT class=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></td>
                  <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                  <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                  <td><INPUT class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></td>
              </tr> 
           </table>    
        </Div>   
      <%
      //保存数据用隐藏表单区
      %>   
      <Input type=hidden name="Operator" >
      <Input type=hidden name="ComCode" >
  </Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
