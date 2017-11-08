<%
//**************************************************************************************************
//Name：LLGrpClaimRegisterMissInput.jsp
//Function：团体立案工作队列信息
//Author：pd
//Date: 2005-10-20
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
    <SCRIPT src="LLGrpClaimRegisterMiss.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLGrpClaimRegisterMissInit.jsp"%>
    <title>立案登记 </title>
</head>
<body  onload="initForm();" >
    <form action="./LLClaimRegisterMissSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- 共享池部分 -->
    <table class= common border=0 width=100%>
        <tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></td>
            <td class= titleImg>请输入查询条件：</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>
                <TD class= title5> 报案号 </TD>
                <TD class= input5> <Input class="wid" class= common name=RptNo id=RptNo ></TD>

                <TD class= title5> 团体客户号 </TD>
                <TD class= input5> <Input class="wid" class= common name=CustomerNo id=CustomerNo ></TD></TR>
                <TR class= common>
                <TD class= title5> 单位名称 </TD>
                <TD class= input5> <Input class="wid" class= common name=CustomerName id=CustomerName ></TD>
                <TD class= title5> 团体保单号 </TD>
                <TD class= input5> <Input class="wid" class= common name=GrpContNo id=GrpContNo ></TD>
            </TR>
            <TR class= common>
                
                <TD class= title5> 报案状态 </TD>
                <TD class= input5> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ClmState id=ClmState CodeData="0|3^10|已报案^20|已立案" ondblclick="return showCodeListEx('ClmState', [this,ClmStateName],[0,1])" onclick="return showCodeListEx('ClmState', [this,ClmStateName],[0,1])" onkeyup="return showCodeListKeyEx('ClmState', [this,ClmStateName],[0,1])" ><Input class=codename name=ClmStateName id=ClmStateName readonly=true></TD>
                <TD class= title5> 出险日期 </TD>
                <TD class= input5> <!--<input class="coolDatePicker" dateFormat="short" name="AccidentDate2" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate2'});" verify="有效开始日期|DATE" dateFormat="short" name=AccidentDate2 id="AccidentDate2"><span class="icon"><a onClick="laydate({elem: '#AccidentDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR>

        </table>
    </DIV>
    <!--<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="queryGrid();">
    <INPUT class=cssButton id="riskbutton" VALUE="申  请" TYPE=button onClick="ApplyClaim();">-->
    <a href="javascript:void(0);" class="button" onClick="queryGrid();">查    询</a>
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="ApplyClaim();">申    请</a>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimRegister);"></TD>
            <TD class= titleImg> 共享池 </TD>
        </TR>
    </Table>
    <Div  id= "divLLClaimRegister" style= "display: ''">
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLClaimRegisterGrid" ></span> </TD>
            </TR>
        </Table>
        </Table>
       
    </Div>
    <br>

    <DIV id=DivLCContInfo STYLE="display:''">
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLClaimRegisterGrid);"></td>
                <td class= titleImg> 工作队列 </td>
            </tr>
        </table>
    </Div>
<!-- 查询条件区 -->
     <Div  id= "divLLLLMainAskInput1" style= "display: ''" class="maxbox1">
       <table  class= common>
  <TR  class= common>
    <TD  class= title5>赔案号</TD>
    <TD  class= input5><input class="wid" class= common name="RptNo2" id="RptNo2" ></TD>
    <TD  class= title5>团体客户号</TD>
    <TD  class= input5><input class="wid" class= common name="CustomerNo2" id="CustomerNo2" ></TD></TR>
    <TR  class= common>
    <TD  class= title5>单位名称</TD>
    <TD  class= input5><input class="wid" class= common name="CustomerName2" id="CustomerName2" ></TD>
    <TD class= title5> 团体保单号 </TD>
    <TD class= input5> <Input class="wid" class= common name="GrpContNo2" id="GrpContNo2" ></TD>
  </TR>
  
  <TR  class= common>
    
    <TD  class= title5>申请日期 起始</TD>
    <TD  class= input5><!--<Input class="coolDatePicker"  dateFormat="short" name=RgtDateStart >-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#RgtDateStart'});" verify="有效开始日期|DATE" dateFormat="short" name=RgtDateStart id="RgtDateStart"><span class="icon"><a onClick="laydate({elem: '#RgtDateStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
    </TD>              
    <TD  class= title5>结束</TD>
    <TD  class= input5><!--<Input class="coolDatePicker"  dateFormat="short" name=RgtDateEnd >-->
    <Input class="coolDatePicker" onClick="laydate({elem: '#RgtDateEnd'});" verify="有效开始日期|DATE" dateFormat="short" name=RgtDateEnd id="RgtDateEnd"><span class="icon"><a onClick="laydate({elem: '#RgtDateEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
  </TR>
       </table>
     </DIV>
   <!--<input name="AskIn" style="display:''"  class=cssButton type=button value="查  询" onclick="querySelfGrid()">-->
   <a href="javascript:void(0);" name="AskIn" class="button" onClick="querySelfGrid();">查    询</a><br><br>

    <Div id= "divSelfLLClaimRegisterGrid" style= "display: ''">
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSelfLLClaimRegisterGrid" ></span></td>
            </tr>
        </table>
        
    </div>
    <br>
    <table  class= common >
        <tr  class=common>
            <!--td><INPUT class=cssButton name='Report' VALUE="新增立案" TYPE=button onclick="newReport();"></td-->
        </tr>
    </table>
    <%
    //保存数据用隐藏表单区
    %>
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    <Input type=hidden name=CurDate  id="CurDate">
    
  <input type=hidden id="MissionID"    name= "MissionID">
  <input type=hidden id="SubMissionID" name= "SubMissionID">
  <input type=hidden id="ActivityID" name= "ActivityID">
  
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
