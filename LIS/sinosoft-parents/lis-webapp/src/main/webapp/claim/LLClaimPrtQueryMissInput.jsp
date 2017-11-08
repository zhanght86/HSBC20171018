<%
//**************************************************************************************************
//Name��LLClaimPrtQueryMissInput.jsp
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
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLClaimPrtQueryMiss.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
     <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLClaimPrtQueryMissInit.jsp"%>
    <title> �ⰸ��ӡ��ѯ </title>
</head>
<body  onload="initForm();" >
    <form method=post name=fm id="fm" target="fraSubmit">
    <!-- ����ز��� -->
    <table class= common border=0 width=100%>
        <tr>
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divSearch);">
            </TD>

            <td class= titleImg align= center>�������ѯ����(������Ҫһ������������ѡ���ⰸ״̬��������������������)��</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
       <div class="maxbox1" >
        <table class= common>
            <TR class= common>
                <TD class= title5> �ⰸ�� </TD>
                <TD class= input5> <Input class="common wid" name=RptNo ></TD>
                <TD class= title5> �ⰸ״̬ </TD>
                <TD class= input5> <Input class=codeno name=ClmState  id=ClmState 
                style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('llclaimstate',[this,ClmStateName],[0,1]);"
                onDblClick="return showCodeList('llclaimstate',[this,ClmStateName],[0,1]);"
                 onKeyUp="return showCodeListKey('llclaimstate',[this,ClmStateName],[0,1]);"><input class=codename name=ClmStateName readonly=true></TD>
                 </TR>
                 <TR class= common>
                <TD class= title5> �¹����� </TD>
                <TD class= input5>
                <input class=" multiDatePicker laydate-icon" dateFormat="short" name="AccidentDate"  id="AccidentDate"  onClick="laydate({elem:'#AccidentDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
           
                <TD class= title5> �����˱��� </TD>
                <TD class= input5> <Input class="common wid" name=CustomerNo ></TD>
                 </TR>
            <TR class= common>
                <TD class= title5> ���������� </TD>
                <TD class= input5> <Input class= "common wid" name=CustomerName ></TD>
                <!--TD class= title> �������Ա� </TD>
                <TD class= input> <Input class=codeno name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly=true></TD-->
                <TD class= title5> �������� </TD>
                <TD class= input5> 
                 <input class=" multiDatePicker laydate-icon" dateFormat="short"name="AccidentDate2"  id="AccidentDate2"  onClick="laydate({elem:'#AccidentDate2'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#AccidentDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR>
            <TR class= common>
                <TD class= title5> �������� </TD>
                <TD class= input5> 
                <input class=" multiDatePicker laydate-icon" dateFormat="short"name="RgtDate"  id="RgtDate" onClick="laydate({elem:'#RgtDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#RgtDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                <TD class= title5> �᰸���� </TD>
                <TD class= input5> 
                 <input class=" multiDatePicker laydate-icon" dateFormat="short"name="EndDate" id="EndDate" onClick="laydate({elem:'#EndDate'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                 </TR>
            <TR class= common>
                <TD class= title5> ��ͬ�� </TD>
                <TD class= input5> <input class="common wid" name="ContNo" ></TD>
            
				<TD class= title5> ��������</TD>
				<TD class= input5> <Input class=codeno name="CalManageCom" id="CalManageCom"
                style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('station',[this,ManageComName],[0,1]);" 
                 onDblClick="return showCodeList('station',[this,ManageComName],[0,1]);" 
                 onKeyUp="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name="ManageComName" readonly=true></TD> 	
                </TR>
            <TR class= common>
                <TD class= title5> �������ڣ��������ڣ� </TD>
                <TD class= input5> 
                <input class=" multiDatePicker laydate-icon" dateFormat="short"name="RgtDateStart" id="RgtDateStart" onClick="laydate({elem:'#RgtDateStart'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#RgtDateStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                <TD class= title5> �������ڣ�����ֹ�ڣ� </TD>
                <TD class= input5>
                <input class=" multiDatePicker laydate-icon" dateFormat="short"  name="RgtDateEnd"   id="RgtDateEnd" onClick="laydate({elem:'#RgtDateEnd'});" verify="��������|Date"> <span class="icon"><a onClick="laydate({elem: '#RgtDateEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR>
            <TR class= common>
				        <TD class= title5> �����ⰸ��</TD>
				        <TD class= input5> <Input class="common wid" name="GrpClmNo"></TD> 	
                <TD class= title5> ���屣���� </TD>
                <TD class= input5> <input class="common wid" name="GrpContNo"></TD>
            </TR>              
        </table>
    </DIV></DIV>
    <!--<INPUT class=cssButton VALUE="��  ѯ" TYPE=button onClick="queryGrid();">-->
    <a href="javascript:void(0);" class="button"onClick="queryGrid();">��   ѯ</a>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimQuery);"></TD>
            <TD class= titleImg> �ⰸ�б� </TD>
        </TR>
    </Table>
    <Div  id= "divLLClaimQuery" style= "display: ''" align = center>
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLClaimQueryGrid" ></span> </TD>
            </TR>
        </Table>
        </Table>
        <table>
            <tr>
                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();"></td>
            </tr>
        </table>
    </Div>
 <a href="javascript:void(0);" class="button"onClick="showDetail();">�ⰸ��ѯ</a>
  <a href="javascript:void(0);" class="button"onClick="showPrint();">�ⰸ��ӡ</a>
    <table>
        <tr>
           <!-- <td><INPUT class=cssButton VALUE="�ⰸ��ѯ" TYPE=button onClick="showDetail();"></td>
            <td><INPUT class=cssButton VALUE="�ⰸ��ӡ" TYPE=button onClick="showPrint();"></td>-->
            <td><INPUT class=cssButton type=hidden VALUE="�Զ��������嵥��ӡ" TYPE=button onclick="showUserDefinedPrt();"></td>        
            <td><INPUT class=cssButton type=hidden VALUE="����ҽ�Ƹ����嵥��ӡ" TYPE=button onclick="showMedBillGrp();"></td>        
            <td><INPUT class=cssButton type=hidden VALUE="��������������ע��ӡ" TYPE=button onclick="showPostilGrp();"></td>        
        </tr>
    </table>
    
    <%
    //�������������ر���
    %>
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
	<input type=hidden id="transact" name="transact">
	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
