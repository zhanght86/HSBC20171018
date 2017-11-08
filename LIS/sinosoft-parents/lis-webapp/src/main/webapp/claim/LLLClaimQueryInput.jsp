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
		  String tAppntNo = request.getParameter("AppntNo");	//出险人编码
		  String tClmNo = request.getParameter("ClmNo");	//传入的客户号
		  String tFlag = request.getParameter("flag");	//传入的客户号
	//==========END
	%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLLClaimQuery.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
     <%@include file="LLLClaimQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit">
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLLcContSuspend);"></td>
            <td class= titleImg> 既往赔案查询</td>
        </tr>
    </table>
    <Div  id= "divLLLcContSuspend" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLLcContSuspendGrid" ></span></td>
            </tr>      
        </table>        
		<!--INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage.previousPage();">                     
        <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage.lastPage();"-->
		<table style="display:none">
			<TR>
   	          <td><INPUT class=cssButton name="Casedetail" VALUE="赔案详情查询"  TYPE=button onclick="LLLClaimQueryGridClick()"></td>
              <td><INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()"></td>
			</TR>
		</table>
    </div>
    <br>
<a href="javascript:void(0);" name="Casedetail" class="button" onClick="LLLClaimQueryGridClick();">赔案详情查询</a>
<a href="javascript:void(0);" name="goBack" class="button" onClick="goToBack();">返    回</a>
    <Div id= "divLLCaseMerge" style= "display:'none'">
 		<table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLLcContSuspend);"></td>
            <td class= titleImg> 案件合并信息</td>
        </tr>
       </table>

        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1><span id="spanLLClaimMergeGrid" ></span></td>
            </tr>      
        </table>      
		<table>
			<TR>
			  <td>
	   	          <span id="operateButton1" style= "display: ''"><INPUT class=cssButton  name="CaseMerge" VALUE="案件合并"  TYPE=button onclick="LLClaimMerge()"></span>
				  <span id="operateButton2" style= "display: none"><INPUT class=cssButton name="CancleCaseMerge" VALUE="取消案件合并"  TYPE=button onclick="LLClaimCancelMerge()"></span>
			  <td>
              <td><INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()"></td>
			  <!--<td><INPUT class=cssButton name="refresh" VALUE="刷 新"  TYPE=button onclick="Refresh()"></td>   -->
			</TR>
		</table>  
    </Div>


    <!--隐藏区,保存信息用-->
    <Input type=hidden id="AppntNo" name="AppntNo"><!--出险人编码,由前一页面传入-->
    <Input type=hidden id="isReportExist" name="isReportExist"><!--判断是否新增事件-->
    <Input type=hidden id="fmtransact" name="fmtransact"><!--动作-->
    <Input type=hidden id="State" name="State"><!--选择的状态-->
    <Input type=hidden id="ContNo" name="ContNo"><!--投保人合同号码-->
    <Input type=hidden id="ClmNo" name="ClmNo"> <!--传入的本次赔案号-->
	<Input type=hidden id="ViewFlag" name="ViewFlag"> <!--标识是否是审核阶段-->
	<Input type=hidden id="AccNo" name="AccNo"> <!--传入的本次案件的事件号-->

</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
