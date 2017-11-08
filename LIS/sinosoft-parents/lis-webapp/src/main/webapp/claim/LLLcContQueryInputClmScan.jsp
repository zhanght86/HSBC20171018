<%
//Name: LLLcContQueryInit.jsp
//Function：保单查询页面
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

	//==========END
	%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLLcContQueryClmScan.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="LLLcContQueryClmScanInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
<form action="" method=post name=fm id=fm target="fraSubmit">
    <table class= common border=0 width=100%>
        <tr>
			<td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img>
			</td>
            <td class= titleImg align= center>保单查询 — 请输入查询条件：</td>
        </tr>
    </table>
    <Div  id= "divSearch" class=maxbox1 style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class= title5> 客户号 </TD>
                <TD class= input5> <Input class="common wid" name=Customerno id=Customerno elementtype=nacessary></TD>
				<TD class= title5>  </TD>
                <TD class= input5>  </TD>
            </TR>
        </table>
    </DIV>
    <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="queryGrid();">
    <p>
    <Div  id= "divLLLcContSuspend" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td style="text-align: left" colSpan=1><span id="spanLLLcContSuspendGrid" ></span></td>
            </tr>      
        </table>        
        <Div  id= "divLcContState" style= "display: 'none'">
            <table>
                <tr>
                    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divState);"></td>
                    <td class= titleImg> 保单状态信息 </td>
                </tr>
            </table>
            <div id= "divState" style= "display: ''">
                <table  class= common>
                    <tr  class= common>
                        <td style="text-align: left" colSpan=1><span id="spanLcContStateGrid" ></span></td>
                    </tr>      
                </table>    
            </div>
        </div>
    </div>
    <hr class=line>
    <table>
        <tr>
            <td><input class=cssButton  type=button value="保单详细" onclick="queryClick()"></td>
            <td><input class=cssButton  type=button value="返  回" onclick="top.close()"></td>
        </tr>      
    </table>   
    
    <!--隐藏区,保存信息用-->
    <Input type=hidden id="AppntNo" name="AppntNo"><!--投保人客户号码,由前一页面传入-->
    <Input type=hidden id="isReportExist" name="isReportExist"><!--判断是否新增事件-->
    <Input type=hidden id="fmtransact" name="fmtransact"><!--动作-->
    <Input type=hidden id="State" name="State"><!--选择的状态-->
    <Input type=hidden id="ContNo" name="ContNo"><!--投保人合同号码-->

</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
