<%
//ҳ������: NBUserQueryInput.jsp
//ҳ�湦�ܣ��û���ѯҳ��
//�����ˣ�chenrong
//�������ڣ�2006.02.22
//�޸�ԭ�� ���ݣ�
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
    <%
    	  GlobalInput tGI = new GlobalInput();
    	  tGI = (GlobalInput)session.getValue("GI");	  
    %>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="NBUserQuery.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="NBUserQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<Form action="" method=post name=fm id="fm" target="fraSubmit">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivLLClaimUserGrid);"></TD>
            <TD class= titleImg> �û���ѯ�嵥  </TD>
        </TR>
    </Table>
	<Div  id= "DivLLClaimUserGrid" class=maxbox1 style= "display: ''">
	    <Table  class= common align=center>
    			<TD  class= title>�û�����</TD>
    			<TD  class= Input> <Input class="common wid" name=UserCode id="UserCode"></TD>
                <TD  class= title>�û�����</TD>      
                <TD  class= Input> <Input class="common wid" name=UserName id="UserName"></TD> 
    			<TD  class= title>�������</TD>      
                <TD  class= Input> <Input class="codeno" name=ManageCom id="ManageCom" verify="�������|code:station" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true></TD>
	       </TR>   
	   </Table>  
		<Table>
		    <TR>
				<TD><Input class=cssButton value="��  ѯ" type=button onclick="queryClick();"></TD>
				<TD><Input class=cssButton value="��  ��" type=button onclick="returnParent();"></TD>
			</TR>
		</Table> 
		<Table  class= common>
		    <TR  class= common>
		        <TD style="text-align: left" colSpan=1><span id="spanNBUserGrid" ></span></TD>
		    </TR>      
		</Table> 
		<div style="text-align:center">
		<Input class="cssButton90" value=" ��  ҳ " TYPE=button onclick="getFirstPage();"> 
        <Input class="cssButton91" value=" ��һҳ " TYPE=button onclick="getPreviousPage();">                   
        <Input class="cssButton92" value=" ��һҳ " TYPE=button onclick="getNextPage();"> 
        <Input class="cssButton93" value=" β  ҳ " TYPE=button onclick="getLastPage();"> 
		</div>
	</Div>  
    <!--������,������Ϣ��-->
    <input type=hidden id="Operator" name=Operator>
    <input type=hidden id="ComCode" name=ComCode>    
    <input type=hidden id="MngCom" name=MngCom>    
    
</Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
