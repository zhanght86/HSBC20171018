<%
//**************************************************************************************************
//Name��LLClaimEndCaseMissInput.jsp
//Function���᰸����������Ϣ
//Author��zl
//Date: 2005-6-20 17:58
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
    <SCRIPT src="LLClaimEndCaseMiss.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimEndCaseMissInit.jsp"%>
    <title>�����Ǽ� </title>
</head>
<body  onload="initForm();" >
    <form action="./LLClaimEndCaseMissSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- ����ز��� -->
    <table class= common border=0 width=100%>
        <tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></td>
            <td class= titleImg>�������ѯ������</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>
                <TD class= title5> ������ </TD>
                <TD class= input5> <Input class="wid" class= common name=RptNo id=RptNo ></TD>
                <TD class= title5> �ⰸ״̬ </TD>
                <TD class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ClmState id=ClmState CodeData="0|3^40|����^50|�᰸" ondblclick="return showCodeListEx('ClmState', [this,ClmStateName],[0,1])" onclick="return showCodeListEx('ClmState', [this,ClmStateName],[0,1])" onkeyup="return showCodeListKeyEx('ClmState', [this,ClmStateName],[0,1])" ><Input class=codename name=ClmStateName id=ClmStateName readonly=true></TD></TR>
                <TR class= common>
                <TD class= title5> �ͻ����� </TD>
                <TD class= input5> <Input class="wid" class= common name=CustomerNo id=CustomerNo ></TD>
                <TD class= title5> �ͻ����� </TD>
                <TD class= input5> <Input class="wid" class= common name=CustomerName id=CustomerName ></TD>
            </TR>
            <TR class= common>
                
                <TD class= title5> �Ա� </TD>
                <TD class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=customerSex id=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=codename name=SexName id=SexName readonly=true></TD>
                <TD class= title5> �������� </TD>
                <TD class= input5> <!--<input class="coolDatePicker" dateFormat="short" name="RgtDate" onblur=" CheckDate(fm.RgtDate); " >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#RgtDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" onblur=" CheckDate(fm.RgtDate); " name=RgtDate id="RgtDate"><span class="icon"><a onClick="laydate({elem: '#RgtDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR>
            <TR class= common>
 				<TD class= title5> ǩ��ͬ������ </TD>
                <TD class= input5><!-- <input class="coolDatePicker" dateFormat="short" name="EndCaseDate" onblur=" CheckDate(fm.EndCaseDate); " >-->
                 <Input class="coolDatePicker" onClick="laydate({elem: '#EndCaseDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" onblur=" CheckDate(fm.EndCaseDate); " name=EndCaseDate id="EndCaseDate"><span class="icon"><a onClick="laydate({elem: '#EndCaseDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                <TD class= title5>  </TD>
                <TD class= input5> </TD>
              
               </TR>
        </table>
    </DIV>
    <!--<INPUT class=cssButton VALUE="��  ѯ" TYPE=button onclick="queryGrid();">-->
    <a href="javascript:void(0);" class="button" onClick="queryGrid();">��    ѯ</a>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimEndCase);"></TD>
            <TD class= titleImg> ����� </TD>
        </TR>
    </Table>
    <Div  id= "divLLClaimEndCase" style= "display: ''">
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLClaimEndCaseGrid" ></span> </TD>
            </TR>
        </Table>
        </Table>
        <table align="center">
            <tr>
                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();HighlightByRow()"></td>
                <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();HighlightByRow()"></td>
                <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();HighlightByRow()"></td>
                <td><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();HighlightByRow()"></td>
            </tr>
        </table>
    </Div>

    <!--<INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyClaim();">-->
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="ApplyClaim();">��    ��</a>
    <DIV id=DivLCContInfo STYLE="display:''">
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLClaimEndCaseGrid);"></td>
                <td class= titleImg> �������� </td>
            </tr>
        </table>
    </Div>
    <Div id= "divSelfLLClaimEndCaseGrid" style= "display: ''">
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSelfLLClaimEndCaseGrid" ></span></td>
            </tr>
        </table>
        <table align="center">
            <tr>
                <td><INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();HighlightSelfByRow()"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();HighlightSelfByRow()"></td>
                <td><INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();HighlightSelfByRow()"></td>
                <td><INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();HighlightSelfByRow()"></td>
            </tr>
        </table>        
    </div>

    <%
    //�������������ر���
    %>
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    <Input type=hidden name=CurDate  id="CurDate">
    
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
