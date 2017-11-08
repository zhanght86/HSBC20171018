<%
//**************************************************************************************************
//Name：LLClaimPrtQueryMissInput.jsp
//Function：赔案查询入口
//Author：zl
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
    <title> 赔案打印查询 </title>
</head>
<body  onload="initForm();" >
    <form method=post name=fm id="fm" target="fraSubmit">
    <!-- 共享池部分 -->
    <table class= common border=0 width=100%>
        <tr>
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divSearch);">
            </TD>

            <td class= titleImg align= center>请输入查询条件(至少需要一个条件，其中选择赔案状态必须输入受理日期区间)：</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
       <div class="maxbox1" >
        <table class= common>
            <TR class= common>
                <TD class= title5> 赔案号 </TD>
                <TD class= input5> <Input class="common wid" name=RptNo ></TD>
                <TD class= title5> 赔案状态 </TD>
                <TD class= input5> <Input class=codeno name=ClmState  id=ClmState 
                style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('llclaimstate',[this,ClmStateName],[0,1]);"
                onDblClick="return showCodeList('llclaimstate',[this,ClmStateName],[0,1]);"
                 onKeyUp="return showCodeListKey('llclaimstate',[this,ClmStateName],[0,1]);"><input class=codename name=ClmStateName readonly=true></TD>
                 </TR>
                 <TR class= common>
                <TD class= title5> 事故日期 </TD>
                <TD class= input5>
                <input class=" multiDatePicker laydate-icon" dateFormat="short" name="AccidentDate"  id="AccidentDate"  onClick="laydate({elem:'#AccidentDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
           
                <TD class= title5> 出险人编码 </TD>
                <TD class= input5> <Input class="common wid" name=CustomerNo ></TD>
                 </TR>
            <TR class= common>
                <TD class= title5> 出险人姓名 </TD>
                <TD class= input5> <Input class= "common wid" name=CustomerName ></TD>
                <!--TD class= title> 出险人性别 </TD>
                <TD class= input> <Input class=codeno name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly=true></TD-->
                <TD class= title5> 出险日期 </TD>
                <TD class= input5> 
                 <input class=" multiDatePicker laydate-icon" dateFormat="short"name="AccidentDate2"  id="AccidentDate2"  onClick="laydate({elem:'#AccidentDate2'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#AccidentDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR>
            <TR class= common>
                <TD class= title5> 立案日期 </TD>
                <TD class= input5> 
                <input class=" multiDatePicker laydate-icon" dateFormat="short"name="RgtDate"  id="RgtDate" onClick="laydate({elem:'#RgtDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#RgtDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                <TD class= title5> 结案日期 </TD>
                <TD class= input5> 
                 <input class=" multiDatePicker laydate-icon" dateFormat="short"name="EndDate" id="EndDate" onClick="laydate({elem:'#EndDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                 </TR>
            <TR class= common>
                <TD class= title5> 合同号 </TD>
                <TD class= input5> <input class="common wid" name="ContNo" ></TD>
            
				<TD class= title5> 立案机构</TD>
				<TD class= input5> <Input class=codeno name="CalManageCom" id="CalManageCom"
                style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('station',[this,ManageComName],[0,1]);" 
                 onDblClick="return showCodeList('station',[this,ManageComName],[0,1]);" 
                 onKeyUp="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name="ManageComName" readonly=true></TD> 	
                </TR>
            <TR class= common>
                <TD class= title5> 受理日期（区间起期） </TD>
                <TD class= input5> 
                <input class=" multiDatePicker laydate-icon" dateFormat="short"name="RgtDateStart" id="RgtDateStart" onClick="laydate({elem:'#RgtDateStart'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#RgtDateStart'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                <TD class= title5> 受理日期（区间止期） </TD>
                <TD class= input5>
                <input class=" multiDatePicker laydate-icon" dateFormat="short"  name="RgtDateEnd"   id="RgtDateEnd" onClick="laydate({elem:'#RgtDateEnd'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#RgtDateEnd'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR>
            <TR class= common>
				        <TD class= title5> 团体赔案号</TD>
				        <TD class= input5> <Input class="common wid" name="GrpClmNo"></TD> 	
                <TD class= title5> 团体保单号 </TD>
                <TD class= input5> <input class="common wid" name="GrpContNo"></TD>
            </TR>              
        </table>
    </DIV></DIV>
    <!--<INPUT class=cssButton VALUE="查  询" TYPE=button onClick="queryGrid();">-->
    <a href="javascript:void(0);" class="button"onClick="queryGrid();">查   询</a>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimQuery);"></TD>
            <TD class= titleImg> 赔案列表 </TD>
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
                <td><INPUT class=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();"></td>
            </tr>
        </table>
    </Div>
 <a href="javascript:void(0);" class="button"onClick="showDetail();">赔案查询</a>
  <a href="javascript:void(0);" class="button"onClick="showPrint();">赔案打印</a>
    <table>
        <tr>
           <!-- <td><INPUT class=cssButton VALUE="赔案查询" TYPE=button onClick="showDetail();"></td>
            <td><INPUT class=cssButton VALUE="赔案打印" TYPE=button onClick="showPrint();"></td>-->
            <td><INPUT class=cssButton type=hidden VALUE="自定义索赔清单打印" TYPE=button onclick="showUserDefinedPrt();"></td>        
            <td><INPUT class=cssButton type=hidden VALUE="团体医疗给付清单打印" TYPE=button onclick="showMedBillGrp();"></td>        
            <td><INPUT class=cssButton type=hidden VALUE="团体批单给付批注打印" TYPE=button onclick="showPostilGrp();"></td>        
        </tr>
    </table>
    
    <%
    //保存数据用隐藏表单区
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
