<%
//**************************************************************************************************
//Name：LLCaseBackMissInput.jsp
//Function：案件回退类信息
//Author：wanzh
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
    <title>案件回退类</title>
</head>
<body  onload="initForm();" >
<form action="./LLCaseBackMissSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- 共享池部分 -->
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSearch);"></TD>
            <TD class= titleImg> 请输入查询条件： </TD>
        </TR>
    </Table>
    <Div id= "divSearch" style= "display: ''" class="maxbox1">
        <table class= common>
            <TR class= common>
                <TD class= title5> 赔案号 </TD>
                <TD class= input5> <Input class="wid" class= common name=RptNo id=RptNo ></TD>
                <TD class= title5> 申请人 </TD>
                <TD class= input5> <Input class="wid" class= common name=RptorName id=RptorName ></TD></TR>
                <TR class= common>
    	        <TD class= title5> 立案日期 </TD>
                <TD class= input5> <!--<Input class= multiDatePicker dateFormat="short" name=RgtDate >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#RgtDate'});" verify="有效开始日期|DATE" dateFormat="short" name=RgtDate id="RgtDate"><span class="icon"><a onClick="laydate({elem: '#RgtDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                <TD class= title5> 受托人姓名 </TD>
          	    <TD class= input5> <Input class="wid" class= common name=AssigneeName id=AssigneeName ></TD>
		    </TR>
            <TR class= common>
        	    
        	    <TD class= title5> 事故日期 </TD>
                <TD class= input5><!-- <Input class= multiDatePicker dateFormat="short" name=accidentdate >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#accidentdate'});" verify="有效开始日期|DATE" dateFormat="short" name=accidentdate id="accidentdate"><span class="icon"><a onClick="laydate({elem: '#accidentdate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
          	    <TD class= title5> 结案日期 </TD>
          	    <TD class= input5> <!--<Input class= multiDatePicker dateFormat="short" name=endcasedate >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#endcasedate'});" verify="有效开始日期|DATE" dateFormat="short" name=endcasedate id="endcasedate"><span class="icon"><a onClick="laydate({elem: '#endcasedate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR> 
        </table>
    </DIV>
    <!--<INPUT class=cssButton VALUE="查  询" TYPE=button onclick="queryGrid();">-->
    <a href="javascript:void(0);" class="button" onClick="queryGrid();">查    询</a>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLCaseBack);"></TD>
            <TD class= titleImg> 可选赔案 </TD>
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
                <td><INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>
    </Div>
    <!--<INPUT class=cssButton id="riskbutton" VALUE="回  退" TYPE=button onClick="CaseBackClaim();">-->
    <a href="javascript:void(0);" id="riskbutton" class="button" onClick="CaseBackClaim();">回    退</a>
    <DIV id=DivLCContInfo STYLE="display:''">
        <table>
            <tr>
                <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSelfLLCaseBackGrid);"></td>
                <td class= titleImg> 工作队列 </td>
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
                <td><INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage2.firstPage();"></td>
                <td><INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage2.previousPage();"></td>
                <td><INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage2.nextPage();"></td>
                <td><INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage2.lastPage();"></td>
            </tr>
        </table>        
    </div>
    <br>
    <%
    //保存数据用隐藏表单区
    %>
    <Input type=hidden name="Operator" >
    <Input type=hidden name="ComCode" >
    <Input type=hidden name="ManageCom" >
    
	<input type=hidden id="ClmNo" name="ClmNo">
	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
