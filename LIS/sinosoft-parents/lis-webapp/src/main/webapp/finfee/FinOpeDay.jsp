<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>    
<%
//程序名称：finOpeDay.jsp
//程序功能：
//创建日期：2003-05-13 15:39:06
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="FinOpeDay.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="FinOpeDayInit.jsp"%>
</head>
<body  onload="initForm();" >    
  <form  method=post name=fm id="fm" target="fraSubmit">
    <table>
    	<tr> 
    		<td class=common>
    		 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
    		</td>
    		 <td class= titleImg>
        		操作员付费日结
       		 </td>   		 
    	</tr>
    </table>
    <div class="maxbox1">
     <Div  id= "divFCDay" style= "display: ''">
      <table  class= common>
        <TR  class= common>
          <TD  class= "title5">
            起始时间
          </TD>
          <TD  class= "input5">
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDay'});" verify="起始时间|NOTNULL" dateFormat="short" name=StartDay id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= "title5">
            结束时间
          </TD>
          <TD  class= "input5">
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDay'});" verify="结束时间|NOTNULL" dateFormat="short" name=EndDay id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>  
        </TR>
        </table>
     </Div>
    </div>
      <Div  id= "divOperator" style= "display: ''">    
			  <a href="javascript:void(0);" class= button onClick="printPay()">打  印</a>
      </Div>
       <input type=hidden  name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
