<%
//页面名称: LLClaimUserQueryInput.jsp
//页面功能：理赔用户查询页面
//建立人：yuejw
//建立日期：2005.07.14
//修改原因/内容：
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
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLClaimUserQuery.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="LLClaimUserQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<Form action="" method=post name=fm id=fm target="fraSubmit">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLClaimUserGrid);"></TD>
            <TD class= titleImg> 用户查询清单  </TD>
        </TR>
    </Table>
	<Div  id= "DivLLClaimUserGrid" style= "display: ''" class="maxbox1">
	    <Table  class= common align=center>
    			<TD  class= title>用户代码</TD>
    			<TD  class= Input> <Input class="wid" class=common name=UserCodeQ></TD>
                <TD  class= title>用户姓名</TD>      
                <TD  class= Input> <Input class="wid" class=common name=UserNameQ></TD> 
    			<TD  class= title>机构代码</TD>      
                <TD  class= Input> <Input class="wid" class=common name=ComCodeQ></TD>
	       </TR>   
	   </Table> </div>
       <a href="javascript:void(0);" class="button" onClick="queryClick();">查    询</a> <br><br>
       
		<!--<Table>
		    <TR>
				<TD><Input class=cssButton value="查  询" type=button onclick="queryClick();"></TD>
				<TD><Input class=cssButton value="返  回" type=button onclick="returnParent();"></TD>
			</TR>
		</Table> -->
		<Table  class= common>
		    <TR  class= common>
		        <TD text-align: left colSpan=1><span id="spanLLClaimUserGrid" ></span></TD>
		    </TR>      
		</Table> 
        <!--<center>
		<Input class= cssbutton90 value=" 首  页 " TYPE=button onclick="getFirstPage();"> 
        <Input class= cssbutton90 value=" 上一页 " TYPE=button onclick="getPreviousPage();">                   
        <Input class= cssbutton90 value=" 下一页 " TYPE=button onclick="getNextPage();"> 
       <Input class= cssbutton90 value=" 尾  页 " TYPE=button onclick="getLastPage();"> </center>-->
	</Div> 
    <a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
    <!--隐藏区,保存信息用-->
    <input type=hidden id="Operator" name=Operator>
    <input type=hidden id="ComCode" name=ComCode>    
    <input type=hidden id="MngCom" name=MngCom>    
    
</Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
