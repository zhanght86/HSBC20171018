<%
//Name：LLClaimPRTPostilPerInput.jsp
//Function：理赔批单打印
//Author：
//Date: 2010-4-30 15:08
//Desc:
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
    <SCRIPT src="LLClaimPRTPostilPerInput.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLClaimPRTPostilPerInit.jsp"%>
    <title> 理赔给付通知书打印查询 </title>
</head>
<body  onload="initForm();" >
    <form method=post name=fm  id="fm"  action="./LLClaimPRTPostilPerSave.jsp" target="fraSubmit">
    <!-- 共享池部分 -->
    <table class= common border=0 width=100%>
        <tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divSearch);">
            </TD>

            <td class= titleImg align= center>请输入查询条件(至少需要一个条件)：</td>
        </tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
     <div class="maxbox1" >
        <table class= common>
            <TR class= common>
                <TD class= title5> 赔案号 </TD>
                <TD class= input5> <Input class= "common wid" name=RptNo ></TD>
                <TD class= title5> 出险人编码 </TD>
                <TD class= input5> <Input class="common wid" name=CustomerNo ></TD>
              </TR>

            <TR class= common>
                <TD class= title5> 立案日期 </TD>
                <TD class= input5> 
                <input class=" coolDatePicker laydate-icon" dateFormat="short" name="RgtDate" id="RgtDate" onClick="laydate({elem:'#RgtDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#RgtDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
                <TD class= title5> 立案机构</TD>
								<TD class= input5> <Input class=codeno name="CalManageCom"  id="CalManageCom" 
                                style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                                onclick="return showCodeList('station',[this,ManageComName],[0,1]);" 
                                onDblClick="return showCodeList('station',[this,ManageComName],[0,1]);" 
                                onKeyUp="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name="ManageComName" readonly=true></TD> 	
            </TR>            
        </table>
    </DIV>
    </Div>
    <!--<INPUT class=cssButton VALUE="查  询" TYPE=button onClick="queryGrid();">-->
    <a href="javascript:void(0);" class="button" onClick="queryGrid();">查   询</a>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimQuery);"></TD>
            <TD class= titleImg> 待打印列表 </TD>
        </TR>
    </Table>
    <Div  id= "divLLClaimQuery" style= "display: ''" align = center>
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLClaimQueryGrid" ></span> </TD>
            </TR>
        </Table>
        </Table>
       <!-- <table>
            <tr>
                <td><INPUT class=cssButton VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();"></td>
            </tr>
        </table>-->
    </Div>
<a href="javascript:void(0);" class="button"onClick="showPrint();">打    印</a>
   <!-- <table>
        <tr>
            <td><INPUT class=cssButton VALUE="打    印" TYPE=button onClick="showPrint();">
            
            </td>
        </tr>
    </table>-->
  <input type=hidden id="fmtransact" name="fmtransact">   
	<input type=hidden id="prtType" name="prtType" value="PCT010" > 
	<input type=hidden id="ClmNo" name="ClmNo"> 
	<input type=hidden id="PrtSeq" name="PrtSeq"> 
	<input type=hidden id="dCustomerNo" name="dCustomerNo"  >  
	<input type=hidden id="mComCode" name="mComCode" value="<%=tGlobalInput.ComCode%>" > 
	<input type=hidden id="mOperator" name="mOperator" value="<%=tGlobalInput.Operator%>" 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
