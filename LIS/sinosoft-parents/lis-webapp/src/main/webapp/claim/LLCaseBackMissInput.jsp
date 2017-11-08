<%
//**************************************************************************************************
//Name��LLCaseBackMissInput.jsp
//Function��������������Ϣ
//Author��wanzh
//Date: 2005-20-20 09:59
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
    <SCRIPT src="LLCaseBackMiss.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLCaseBackMissInit.jsp"%>
    <title>����������</title>
</head>
<body  onload="initForm();" >
<form action="./LLCaseBackMissSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- ����ز��� -->
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></TD>
            <TD class= titleImg> �������ѯ������ </TD>
        </TR>
    </Table>
    <Div id= "divSearch" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>
                <TD class= title5> �ⰸ�� </TD>
                <TD class= input5> <Input class="wid" class= common name=RptNo id=RptNo ></TD>
                <TD class= title5> ������ </TD>
                <TD class= input5> <Input class="wid" class= common name=RptorName id=RptorName ></TD></TR>
                <TR class= common>
    	        <TD class= title5> �������� </TD>
                <TD class= input5> <!--<Input class= multiDatePicker dateFormat="short" name=RgtDate >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#RgtDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=RgtDate id="RgtDate"><span class="icon"><a onClick="laydate({elem: '#RgtDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                <TD class= title5> ���������� </TD>
          	    <TD class= input5> <Input class="wid" class= common name=AssigneeName id=AssigneeName ></TD>
		    </TR>
            <TR class= common>
        	    
        	    <TD class= title5> �¹����� </TD>
                <TD class= input5><!-- <Input class= multiDatePicker dateFormat="short" name=accidentdate >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#accidentdate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=accidentdate id="accidentdate"><span class="icon"><a onClick="laydate({elem: '#accidentdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
          	    <TD class= title5> �᰸���� </TD>
          	    <TD class= input5> <!--<Input class= multiDatePicker dateFormat="short" name=endcasedate >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#endcasedate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=endcasedate id="endcasedate"><span class="icon"><a onClick="laydate({elem: '#endcasedate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR> 
        </table>
    </DIV>
    <!--<INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryGrid();">-->
    <a href="javascript:void(0);" class="button" onClick="queryGrid();">��    ѯ</a>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLCaseBack);"></TD>
            <TD class= titleImg> ��ѡ�ⰸ </TD>
        </TR>
    </Table>
    <Div  id= "divLLCaseBack" style= "display: ''">
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLCaseBackGrid" ></span> </TD>
            </TR>
        </Table>
        </Table>
        <table align="center">
            <tr>
                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>
    </Div>
    <!--<INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="CaseBackClaim();">-->
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="CaseBackClaim();">��    ��</a>
    <DIV id=DivLCContInfo STYLE="display:''">
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLCaseBackGrid);"></td>
                <td class= titleImg> �������� </td>
            </tr>
        </table>
    </Div>
    <Div id= "divSelfLLCaseBackGrid" style= "display: ''">
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSelfLLCaseBackGrid" ></span></td>
            </tr>
        </table>
        <table align = center>
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
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    
	<input type=hidden id="ClmNo" name="ClmNo">
	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
