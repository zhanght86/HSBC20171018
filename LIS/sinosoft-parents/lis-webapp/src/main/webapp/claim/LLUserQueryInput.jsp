<%
//Name: LLQueryUserInput.jsp
//Function���û���ѯҳ��
//Date��2005.07.11
//Author ��quyang
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<%
	//==========BGN�����ղ���
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");	  
		 
	//==========END
	%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLUserQuery.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="LLUserQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<Form action="" method=post name=fm id=fm target="fraSubmit">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLQueryUser);"></TD>
            <TD class= titleImg> �û���ѯ�嵥  </TD>
        </TR>
    </Table>
	<Div  id= "DivLLQueryUser" style= "display: ''" class="maxbox1">
	    <Table  class= common>
        <Tr  class= common>
					<TD  class= title5>�û�����</TD>
					<TD  class= Input5> <Input class="wid" class=common name=UserCodeQ id=UserCodeQ></TD>
	                <TD  class= title5>�û�����</TD>      
	                <TD  class= Input5> <Input class="wid" class=common name=UserNameQ id=UserNameQ></TD> </Tr>
                  <Tr  class= common> 
					<TD  class= title5>��������</TD>      
	                <TD  class= Input5> <Input class="wid" class=common name=ComCodeQ id=ComCodeQ></TD>
	       </TR>   
	   </Table></div>
       <!--<Input class=cssButton value="��  ѯ" type=button onclick="queryClick();">--> 
       <a href="javascript:void(0);" class="button" onClick="queryClick();">��    ѯ</a> <br><br>
		
		<Table  class= common>
		    <TR  class= common>
		        <TD text-align: left colSpan=1><span id="spanLLQueryUserGrid" ></span></TD>
		    </TR>      
		</Table> 
		
	</Div> 
    <!--<Input class=cssButton value="��  ��" type=button onclick="returnParent();"> -->
    <a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
</Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
