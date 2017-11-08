<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
String GrpContno = request.getParameter("GrpContNo");
loggerDebug("SpecInp","合同====="+GrpContno);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/javascript/EasyQuery.js"></script>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="SpecInp.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>团体特约录入</title>
  <%@include file="GrpSpecInit.jsp"%>
<SCRIPT>var GrpContno="<%=request.getParameter("GrpContNo")%>";</SCRIPT>
</head>
<body onload="initForm();">
  <form method=post name=fm target="fraSubmit" action= "./SpecSave.jsp">


   <div id="divLCImpart1" style="display:''">
          <table class="common">
          	<tr>
          		<td>  特约内容</td>
         	</tr>
              <tr class="common">
                  <td style="text-align:left" colSpan="1">
                      <span id="spanSpecInfoGrid">
                      </span>
                  </td>
              </tr>
          </table>
    </div>
     <Div  id= "divPage" align=center style= "display: '' ">
      <INPUT CLASS=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();">
      </Div> 
  <table  class=common width="121%" height="37%">
    <TR  class= common>
      <TD width="100%" height="13%"  class= title> 特约内容 </TD>
    </TR>
    <TR  class= common>
      <TD height="87%"  class= title><textarea name="Content" cols="135" rows="10" class="common"></textarea></TD>
    </TR>
  </table>
 
   <Input type="hidden"  class="readonly" readonly name=PrtNo value="<%=request.getParameter("GrpContNo")%>">
   <Input type="hidden"  class="readonly" name="Action">
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<input class= cssButton type= "button" name= "sure" value="保  存" onClick="submitForm()">
<input class= cssButton type= "button" name= "sure" value="删  除" onClick="deleteInfo()">
<input class= cssButton type= "button" name= "backbutton" value="返  回" onClick="javascript:top.close();">

</body>
</html>
