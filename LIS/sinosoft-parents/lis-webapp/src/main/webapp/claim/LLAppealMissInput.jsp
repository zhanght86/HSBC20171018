<%
//**************************************************************************************************
//Name��LLAppealMissInput.jsp
//Function��������������Ϣ
//Author��zl
//Date: 2005-7-25 16:29
//Desc: 
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLAppealMiss.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLAppealMissInit.jsp"%>
    <title>����������</title>
</head>
<body  onload="initForm();" >
    <form action="./LLAppealMissSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- ����ز��� -->
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></TD>
            <TD class= titleImg> �������ѯ������ </TD>
        </TR>
    </Table>
    <Div id= "divSearch" class=maxbox1 style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class= title> �ⰸ�� </TD>
                <TD class= input> <Input class="common wid" name=RptNo id=RptNo ></TD>
                <TD class= title> ������ </TD>
                <TD class= input> <Input class= "common wid" name=RptorName id=RptorName ></TD>
    	        <TD class= title> ������������˹�ϵ </TD>
    	        <TD class= input> <Input class=codeno name=Relation id=Relation style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true></TD>
            </TR>
            <TR class= common>
        	    <TD class= title> ���������� </TD>
          	    <TD class= input> <Input class= codeno name=AssigneeType id=AssigneeType CodeData="0|^0|ҵ��Ա^1|�ͻ�" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeListEx('AssigneeType', [this,AssigneeTypeName],[0,1])" onkeyup="return showCodeListKeyEx('AssigneeType', [this,AssigneeTypeName],[0,1])"><Input class=codename name=AssigneeTypeName id=AssigneeTypeName readonly=true></TD>
        	    <TD class= title> �����˴��� </TD>
          	    <TD class= input> <Input class= "common wid" name=AssigneeCode id=AssigneeCode ></TD>
        	    <TD class= title> ���������� </TD>
          	    <TD class= input> <Input class= "common wid" name=AssigneeName id=AssigneeName ></TD>
            </TR>
        </table>
    </DIV>
    <INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryGrid();">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLAppeal);"></TD>
            <TD class= titleImg> ��ѡ�ⰸ </TD>
        </TR>
    </Table>
    <Div  id= "divLLAppeal" style= "display: ''" align = center>
        <Table  class= common>
            <TR  class= common>
                <TD style="text-align: left" colSpan=1><span id="spanLLAppealGrid" ></span> </TD>
            </TR>
        </Table>
        </Table>
        <table>
            <tr>
                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>
    </Div>
    <br>
    <hr class=line>
    <INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyClaim();">
    <DIV id=DivLCContInfo STYLE="display:''">
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLAppealGrid);"></td>
                <td class= titleImg> �������� </td>
            </tr>
        </table>
    </Div>
    <Div id= "divSelfLLAppealGrid" style= "display: ''" align = center>
        <table  class= common >
            <tr  class=common>
                <td style="text-align: left" colSpan=1 ><span id="spanSelfLLAppealGrid" ></span></td>
            </tr>
        </table>
        <table>
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();"></td>
            </tr>
        </table>        
    </div>
    <br>
    <%
    //�������������ر���
    %>
    <Input type=hidden name="Operator" id=Operator >
    <Input type=hidden name="ComCode" id=ComCode >
    <Input type=hidden name="ManageCom" id=ManageCom >
    
	<input type=hidden id="ClmNo" name="ClmNo">
	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
