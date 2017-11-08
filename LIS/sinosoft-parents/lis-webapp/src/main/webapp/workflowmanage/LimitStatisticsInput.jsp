<%/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp" %>
<html>
<%GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");%>
<head>
    <script>
        var manageCom = "<%=tG.ManageCom%>"; //记录登陆机构
    </script>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="LimitStatistics.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LimitStatisticsInit.jsp" %>
    <title>时效监测</title>
</head>
<body onLoad="initForm(); initElementtype();">
<form method=post name=fm id=fm target="fraSubmit" action="">
    <table>
        <tr>
            <td class=common>
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divDef);">
            </td>
            <td class=titleImg>时效信息</td>
        </tr>
    </table>
    <Div id="divDef" style="display: ''">
      <div class="maxbox1" >
        <table class=common>
            <tr class=common>
                <TD class=title5>业务类型</TD>
                <TD class=input5>
                    <Input class=codeno name=BusiType  id=BusiType 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="return showCodeList('busitype',[this,BusiTypeName],[0,1]);"
                     ondblclick="return showCodeList('busitype',[this,BusiTypeName],[0,1]);"
                      onKeyUp="return showCodeList('busitype',[this,BusiTypeName],[0,1]);"><input class=codename name=BusiTypeName id=BusiTypeName readonly=true >
                </TD>

                <td class=title5>监测节点</td>
                <td class=input5>
                    <input class=codeno name=ActivityID  id=ActivityID 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                     onclick="checkBusiType();return showCodeList('queryactivityid2',[this,ActivityName],[0,1],null,null,fm.BusiType.value);"
                     onDblClick="checkBusiType();return showCodeList('queryactivityid2',[this,ActivityName],[0,1],null,null,fm.BusiType.value);"
                     onKeyUp="checkBusiType();return showCodeListKey('queryactivityid2',[this,ActivityName],[0,1],null,null,fm.BusiType.value);"><input class=codename name=ActivityName id=ActivityName readonly=true elementtype=nacessary>
                </td>
            </tr>
            <tr class=common>

                <td class=title5>业务号</td>
                <td class=input5>
                    <input class="common wid " name=BusiNo id=BusiNo>
                </td>
                <td ></td>
                <td >
                </td>

            </tr>
        </table>
        </div>
    </Div>
    <!--<INPUT VALUE="  查  询  " class=cssButton name=updateClickButton TYPE=button onClick="easyQueryClick();">-->
     <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a>
 <br><br>
    <Div id="divWorkDayGrid" style="display:''">
        <table class=common>
            <tr class=common>
                <td text-align: left colSpan=1>
             <span id="spanMonitorGrid">
             </span>
                </td>
            </tr>
        </table>
        <div align="center">
        <INPUT VALUE="首页"   class="cssButton90" TYPE=button onClick="turnPage.firstPage();">
        <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="turnPage.previousPage();">
        <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="turnPage.nextPage();">
        <INPUT VALUE="尾页"   class="cssButton93" TYPE=button onClick="turnPage.lastPage();"> 
        </div>       
    </Div>

    <input name=Operate type=hidden value=''> 
</form>
<div class="common">
<span style="color:red">时间利用率是反映完成任务作业效率的参数，
时间利用率 = 实际作业时间/规定作业用时
</span>
</div>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
