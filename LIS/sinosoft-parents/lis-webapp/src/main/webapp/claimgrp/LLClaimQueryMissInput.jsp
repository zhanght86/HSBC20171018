<%
//**************************************************************************************************
//Name��LLClaimQueryMissInput.jsp
//Function���ⰸ��ѯ���
//Author��zl
//Date: 2005-8-22 10:17
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
    String tGrpContNo = request.getParameter("GrpContNo");
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLClaimQueryMiss.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimQueryMissInit.jsp"%>
    <title> �ⰸ��ѯ </title>
</head>
<body  onload="initForm();" >
    <form method=post name=fm id=fm target="fraSubmit">
    <!-- ����ز��� -->
    <table class= common border=0 width=100%>
        <tr><td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></td>
            <td class= titleImg>�������ѯ������</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>
                <TD class= title> �ⰸ�� </TD>
                <TD class= input> <Input class="wid" class= common name=RptNo id=RptNo ></TD>
                <TD class= title> �ⰸ״̬ </TD>
                <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ClmState id=ClmState ondblclick="return showCodeList('llclaimstate',[this,ClmStateName],[0,1]);" onclick="return showCodeList('llclaimstate',[this,ClmStateName],[0,1]);" onkeyup="return showCodeListKey('llclaimstate',[this,ClmStateName],[0,1]);"><input class=codename name=ClmStateName id=ClmStateName readonly=true></TD>
                <TD class= title> �����¹����� </TD>
                <TD class= input> <!--<input class="coolDatePicker" dateFormat="short" name="AccidentDate" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR>
            <TR class= common>
                <TD class= title> �����˱��� </TD>
                <TD class= input> <Input class="wid" class= common name=CustomerNo id=CustomerNo ></TD>
                <TD class= title> ���������� </TD>
                <TD class= input> <Input class="wid" class= common name=CustomerName id=CustomerName ></TD>
                <!--TD class= title> �������Ա� </TD>
                <TD class= input> <Input class=codeno name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly=true></TD-->
                <TD class= title> �������� </TD>
                <TD class= input> <!--<input class="coolDatePicker" dateFormat="short" name="AccidentDate2" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate2'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=AccidentDate2 id="AccidentDate2"><span class="icon"><a onClick="laydate({elem: '#AccidentDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR>
            <TR class= common>
                <TD class= title> �������� </TD>
                <TD class= input> <!--<input class="coolDatePicker" dateFormat="short" name="RgtDate" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#RgtDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=RgtDate id="RgtDate"><span class="icon"><a onClick="laydate({elem: '#RgtDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                <TD class= title> �᰸���� </TD>
                <TD class= input> <!--<input class="coolDatePicker" dateFormat="short" name="EndDate" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                <TD class= title> ��ͬ�� </TD>
                <TD class= input> <input class="wid" class=common name="ContNo" id="ContNo" ></TD>
            </TR>
        </table>
    </DIV>
    <!--<INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryGrid();">--><a href="javascript:void(0);" class="button" onClick="queryGrid();">��    ѯ</a>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimQuery);"></TD>
            <TD class= titleImg> �ⰸ�б� </TD>
        </TR>
    </Table>
    <Div  id= "divLLClaimQuery" style= "display: ''">
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLClaimQueryGrid" ></span> </TD>
            </TR>
        </Table>
        </Table>
        <!--<table align = center>
            <tr>
                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>-->
    </Div>
    
    <%
    //�������������ر���
    %>
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
