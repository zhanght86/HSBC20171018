<%
//Name: LLQueryUserInit.jsp
//Function：用户查询页面
//Date：2005.07.11
//Author ：quyang
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<%
	//==========BGN，接收参数
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");	  
		 
	//==========END
	%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="LLQueryUser.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="LLQueryUserInit.jsp"%>
</head>
<body  onload="initForm();">
<form action="" method=post name=fm target="fraSubmit">
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLQueryUser);"></td>
            <td class= titleImg> 用户查询清单  </td>
        </tr>
    </table>
    <Div  id= "divLLQueryUser" style= "display: ''">
	<table>
	     <tr><td class=common>
	          <td class= titleImg>查询条件</td>
	     </tr>
	</table>
    <table  class= common align=center>
				<TD  class= title>用户编码</TD>
				<TD  class= input> <Input class=common name=UserCodeQ></TD>
                <TD  class= title>用户姓名</TD>      
                <TD  class= input> <Input class=common name=UserNameQ></TD> 
				<TD  class= title>机构编码</TD>      
                <TD  class= input> <Input class=common name=ComCodeQ></TD>
       </TR>   
   </Table>  
      <INPUT VALUE="查  询" class= cssButton TYPE=button onclick="queryClick();">
	  <input class=cssButton  type=button value=" 返 回 " onclick="returnParent()">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLQueryUserGrid" ></span></td>
            </tr>      
        </table> 
		<INPUT class= button VALUE=" 首  页 " TYPE=button onclick="getFirstPage();"> 
    <INPUT class= button VALUE=" 上一页 " TYPE=button onclick="getPreviousPage();">                   
    <INPUT class= button VALUE=" 下一页 " TYPE=button onclick="getNextPage();"> 
    <INPUT class= button VALUE=" 尾  页 " TYPE=button onclick="getLastPage();"> 
   <!--     <input class=cssButton  type=button value=" 保 存 " onclick="saveClick()"> 
        <input class=cssButton  type=button value=" 查 询 " onclick="queryClick()">        
        <input class=cssButton  type=button value=" 返 回 " onclick="returnParent()">-->
    </div>
    <!--隐藏区,保存信息用-->
    <Input type=hidden id="AppntNo" name="AppntNo"><!--投保人客户号码,由前一页面传入-->
    <Input type=hidden id="isReportExist" name="isReportExist"><!--判断是否新增事件-->
    <Input type=hidden id="fmtransact" name="fmtransact"><!--动作-->
    <Input type=hidden id="State" name="State"><!--选择的状态-->
    <Input type=hidden id="ContNo" name="ContNo"><!--投保人合同号码-->

</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
