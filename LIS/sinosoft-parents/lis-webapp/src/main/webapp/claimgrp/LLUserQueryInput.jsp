<%
//Name: LLQueryUserInput.jsp
//Function：用户查询页面
//Date：2005.07.11
//Author ：quyang
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
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
    <SCRIPT src="LLUserQuery.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="LLUserQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<Form action="" method=post name=fm target="fraSubmit">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLQueryUser);"></TD>
            <TD class= titleImg> 用户查询清单  </TD>
        </TR>
    </Table>
	<Div  id= "DivLLQueryUser" style= "display: ''" class="maxbox1">
	    <Table  class= common align=center>
					<TD  class= title>用户编码</TD>
					<TD  class= Input> <Input class="wid" class=common name=UserCodeQ id=UserCodeQ></TD>
	                <TD  class= title>用户姓名</TD>      
	                <TD  class= Input> <Input class="wid" class=common name=UserNameQ id=UserNameQ></TD> 
					<TD  class= title>机构编码</TD>      
	                <TD  class= Input> <Input class="wid" class=common name=ComCodeQ id=ComCodeQ></TD>
	       </TR>   
	   </Table>  </div>
		<Input class=cssButton value="查  询" type=button onclick="queryClick();">
				
		
		<Table  class= common>
		    <TR  class= common>
		        <TD text-align: left colSpan=1><span id="spanLLQueryUserGrid" ></span></TD>
		    </TR>      
		</Table> <Input class=cssButton value="返  回" type=button onclick="returnParent();">
		<!--<Input class= button value=" 首  页 " TYPE=button onclick="getFirstPage();"> 
        <Input class= button value=" 上一页 " TYPE=button onclick="getPreviousPage();">                   
        <Input class= button value=" 下一页 " TYPE=button onclick="getNextPage();"> 
       <Input class= button value=" 尾  页 " TYPE=button onclick="getLastPage();"> -->
	 
</Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>