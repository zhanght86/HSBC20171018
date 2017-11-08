<%
//**************************************************************************************************
//Name：LLGrpClaimSimpleInput.jsp
//Function：简易案件立案信息
//Author：pd
//Date: 2005-10-21
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
    <SCRIPT src="LLGrpClaimSimple.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLGrpClaimSimpleInit.jsp"%>
</head>
<body  onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">
    <table class= common border=0 width=100%>
        <tr>
        <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></td>
            <td class= titleImg>请输入查询条件：</td>
        </tr>
    </table>    

    <Div  id= "divSearch" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>
                <TD class= title5> 赔案号 </TD>
                <TD class= input5> <Input class="wid" class= common name=RptNo id=RptNo ></TD>
                <TD class= title5> 团体客户号 </TD>
                <TD class= input5><Input class="wid" class=common name=CustomerNo id=CustomerNo ></TD></TR>
                <TR class= common>
                <TD class= title5> 单位名称 </TD>
                <TD class= input5> <Input class="wid" class= common name=CustomerName id=CustomerName ></TD>
                <TD class= title5> 团体保单号 </TD>
                <TD class= input5> <Input class="wid" class= common name=GrpContNo id=GrpContNo ></TD>
            </TR>
            <TR class= common>
                
                <TD class= title5> 出险日期 </TD>
                <TD class= input5> <!--<input class="coolDatePicker" dateFormat="short" name="AccidentDate" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                <TD class= title5> </TD>
                <TD class= input5></TD>
            </TR>
        </table>
    </DIV>
   <!-- <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="querySelfGrid();">
    <INPUT class=cssButton VALUE="申  请" TYPE=button onclick="ApplyClaim2();">-->
    <a href="javascript:void(0);" class="button" onClick="querySelfGrid();">查    询</a>
    <a href="javascript:void(0);" class="button" onClick="ApplyClaim2();">申    请</a>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimRegister);"></TD>
            <TD class= titleImg> 共享池 </TD>
        </TR>
    </Table>
    <Div id= "divSelfLLClaimSimpleGrid" style= "display: ''">
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanSelfLLClaimSimpleGrid" ></span></td>
            </tr>
        </table>
        <table align = center>
            <tr>
                <td><INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>        
    </div>
    <br>
    <DIV id=DivLCContInfo STYLE="display:''">
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLClaimSimpleGrid);"></td>
                <td class= titleImg> 工作队列 </td>
            </tr>
        </table>
    </Div>
    <Div  id= "divSearch1" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>
                <TD class= title5> 赔案号 </TD>
                <TD class= input5> <Input class="wid" class= common name=RptNo2 id=RptNo2 ></TD>
                <TD class= title5> 团体客户号 </TD>
                <TD class= input5><Input class="wid" class=common name=CustomerNo2 id=CustomerNo2 ></TD></TR>
                 <TR class= common>
                <TD class= title5> 单位名称 </TD>
                <TD class= input5> <Input class="wid" class= common name=CustomerName2 id=CustomerName2 ></TD>
                 <TD class= title5> 团体保单号 </TD>
                <TD class= input5> <Input class="wid" class= common name=GrpContNo2 id=GrpContNo2 ></TD>
            </TR>
            <TR class= common>
               
                <TD class= title5> 出险日期 </TD>
                <TD class= input5> <!--<input class="coolDatePicker" dateFormat="short" name="AccidentDate2" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate2'});" verify="有效开始日期|DATE" dateFormat="short" name=AccidentDate2 id="AccidentDate2"><span class="icon"><a onClick="laydate({elem: '#AccidentDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                <TD class= title5> </TD>
                <TD class= input5></TD>
            </TR>
        </table>
    </DIV>
    <!--<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="queryGrid();">--> 
    <a href="javascript:void(0);" class="button" onClick="queryGrid();">查    询</a><br><br>   
    <br>

    <Div id= "divLLClaimSimpleGrid" style= "display: ''">
        <table  class= common >
            <tr  class=common>
                <td text-align: left colSpan=1 ><span id="spanLLClaimSimpleGrid" ></span></td>
            </tr>
        </table>
        <table align = center>
            <tr>
                <td><INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();HighlightByRow()"></td>
                <td><INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();HighlightByRow()"></td>
                <td><INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();HighlightByRow()"></td>
                <td><INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();HighlightByRow()"></td>
            </tr>
        </table>        
    </div>
    <br>
    <!--INPUT class=cssButton id="riskbutton" VALUE="简易案件申请" TYPE=button onClick="ApplyClaim();"-->
    <!--<input class=cssButton name=Reportbutton  VALUE="报案信息导出"  TYPE=button onclick="PrintReportClass();" >
    <input class=cssButton name=Inputbutton  VALUE="出险人信息导入"  TYPE=button onclick="getin()" >-->
    <!--a href="./simpleclaim.xls">下载[简易案件理赔]导入模板</a-->
    <%
    //保存数据用隐藏表单区
    %>
   
   <!-- <TR class= common>                            
          <TD class=title><font  color='#ff0000'><b> 使用导入功能请先导出报案信息!</b></font> </TD>
		    </TR>     -->  
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
