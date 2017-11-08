<%
//Name: LLLClaimQueryInit.jsp
//Function：既往赔案查询
//Date：2005.06.21
//Author ：quyang
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<%
	//==========BGN，接收参数
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");	  
		  String tAppntNo = request.getParameter("tAppntNo");	////出险人编码
		  String tClmNo = request.getParameter("ClmNo");
	//==========END
	%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="LLLClaimQuery.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="LLLClaimQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<form action="" method=post name=fm target="fraSubmit">
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLLcContSuspend);"></td>
            <td class= titleImg> 既往赔案查询清单  </td>
        </tr>
    </table>
    <Div  id= "divLLLcContSuspend" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLLcContSuspendGrid" ></span></td>
            </tr>      
        </table>        
 <!--       <input class=cssButton  type=button value=" 保 存 " onclick="saveClick()">
        <input class=cssButton  type=button value=" 查 询 " onclick="queryClick()">        
        <input class=cssButton  type=button value=" 返 回 " onclick="top.close()"> -->
        <center>
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">                     
        <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"></center>
    </div>

    <Div id= "divLLReport2" style= "display:none">
		<table>
			<TR>
				<TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
				<TD class= titleImg>报案信息</TD>
			</TR>
		</table>
    </Div>

    <Div id= "divLLReport1" style= "display:none">
        <table class= common>
            <TR class= common>
                <TD class= title> 事件号 </TD>
                <TD class= input> <Input class= "readonly" readonly name=AccNo></TD>
                <TD class= title> 报案号 </TD>
                <TD class= input> <Input class="readonly" readonly  name=RptNo ></TD>
                <TD class= title> 报案人姓名 </TD>
                <TD class= input> <Input class= common name=RptorName ></TD>
            </TR>
            <TR class= common>
                <TD class= title> 报案人电话 </TD>
                <TD class= input> <Input class= common name=RptorPhone ></TD>
                <TD class= title> 报案人通讯地址 </TD>
                <TD class= input> <Input class= common name=RptorAddress ></TD>
                <TD class= title> 报案人与事故人关系 </TD>
                <TD class= input> <Input class=codeno name=Relation ondblclick="return showCodeList('Relation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('Relation',[this,RelationName],[0,1]);"><input class=codename name=RelationName readonly=true></TD>
            </TR>
            <TR class= common>
                <TD class= title> 报案方式 </TD>
                <TD class= input> <Input class=codeno name="RptMode" ondblclick="return showCodeList('llrptcasetype',[this,RptModeName],[0,1]);" onkeyup="return showCodeListKey('llrptcasetype',[this,RptModeName],[0,1]);"><input class=codename name="RptModeName" readonly=true></TD>
                <TD class= title> 出险地点 </TD>
                <TD class= input> <Input class= common name=AccidentSite ></TD>
                <TD class= title> 报案受理日期 </TD>
            <TD class= input>  <input class="readonly" readonly name="RptDate" ></TD>
            </TR>
            <TR class= common>
                <TD class= title> 管辖机构 </TD>
                <TD class= input> <Input class= "readonly" readonly name=MngCom></TD>
                <TD class= title> 报案受理人 </TD>
                <TD class= input> <Input class="readonly" readonly name=Operator ></TD>
                <TD class= title> 理赔状态 </TD>
                <TD class= input> <Input class= "readonly" readonly name=CaseState></TD>
            </TR>
        </table>

    <!--出险人信息-->
    <table class= common>
        <tr class= common>
            <td text-align: left colSpan=1><span id="spanSubReportGrid" ></span></td>
        </tr>
    </table>

       <table>
            <tr>
                <td><input class=cssButton name=QueryCont2 VALUE="保单查询"  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><INPUT class=cssButton name="ViewInvest" VALUE="查看调查"  TYPE=button onclick="showQueryInq()"></td>
				<td><INPUT class=cssButton name="ViewReport" VALUE="查看呈报"  TYPE=button onclick="showQuerySubmit()"></td>
            </tr>
        </table>
    </Div>



    <!--隐藏区,保存信息用-->
    <Input type=hidden id="AppntNo" name="AppntNo"><!--出险人编码,由前一页面传入-->
    <Input type=hidden id="isReportExist" name="isReportExist"><!--判断是否新增事件-->
    <Input type=hidden id="fmtransact" name="fmtransact"><!--动作-->
    <Input type=hidden id="State" name="State"><!--选择的状态-->
    <Input type=hidden id="ContNo" name="ContNo"><!--投保人合同号码-->
    <Input type=hidden id="ClmNo" name="ClmNo"><!--投保人合同号码-->

</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
