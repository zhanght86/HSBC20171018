<%
//ҳ������: LLClaimUserQueryInput.jsp
//ҳ�湦�ܣ������û���ѯҳ��
//�����ˣ�yuejw
//�������ڣ�2005.07.14
//�޸�ԭ��/���ݣ�
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
            <TD class= titleImg> �û���ѯ�嵥  </TD>
        </TR>
    </Table>
	<Div  id= "DivLLClaimUserGrid" style= "display: ''" class="maxbox1">
	    <Table  class= common align=center>
    			<TD  class= title>�û�����</TD>
    			<TD  class= Input> <Input class="wid" class=common name=UserCodeQ></TD>
                <TD  class= title>�û�����</TD>      
                <TD  class= Input> <Input class="wid" class=common name=UserNameQ></TD> 
    			<TD  class= title>��������</TD>      
                <TD  class= Input> <Input class="wid" class=common name=ComCodeQ></TD>
	       </TR>   
	   </Table> </div>
       <a href="javascript:void(0);" class="button" onClick="queryClick();">��    ѯ</a> <br><br>
       
		<!--<Table>
		    <TR>
				<TD><Input class=cssButton value="��  ѯ" type=button onclick="queryClick();"></TD>
				<TD><Input class=cssButton value="��  ��" type=button onclick="returnParent();"></TD>
			</TR>
		</Table> -->
		<Table  class= common>
		    <TR  class= common>
		        <TD text-align: left colSpan=1><span id="spanLLClaimUserGrid" ></span></TD>
		    </TR>      
		</Table> 
        <!--<center>
		<Input class= cssbutton90 value=" ��  ҳ " TYPE=button onclick="getFirstPage();"> 
        <Input class= cssbutton90 value=" ��һҳ " TYPE=button onclick="getPreviousPage();">                   
        <Input class= cssbutton90 value=" ��һҳ " TYPE=button onclick="getNextPage();"> 
       <Input class= cssbutton90 value=" β  ҳ " TYPE=button onclick="getLastPage();"> </center>-->
	</Div> 
    <a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
    <!--������,������Ϣ��-->
    <input type=hidden id="Operator" name=Operator>
    <input type=hidden id="ComCode" name=ComCode>    
    <input type=hidden id="MngCom" name=MngCom>    
    
</Form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
