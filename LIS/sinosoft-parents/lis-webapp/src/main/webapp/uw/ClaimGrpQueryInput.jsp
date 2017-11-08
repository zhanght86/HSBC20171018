<%
//**************************************************************************************************
//文件名称：ClaimGrpQueryInput.jsp
//程序功能：承保处理-团体保单-人工核保-既往理赔查询响应界面，团体信息的显示，当点击此界面单选按钮
//          时，连接到（ui/uw/ClaimQueryMain.jsp）个险层面的赔案查询。
//创建日期：2006-11-08
//创建人  ：zhaorx
//更新记录：
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
<%
//============================================================BGN，接收参数
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");	  
	  String tGrpAppntNo = request.getParameter("CustomerNo");	//团体投保人客户号
//============================================================END
%>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="ClaimGrpQuery.js"></SCRIPT>  
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="ClaimGrpQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<form action="" method=post name=fm id=fm target="fraSubmit">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLGrpQueryGrid);"></TD>
            <TD class= titleImg> 投保人保单涉及的赔案信息</TD>
        </TR>
    </Table>
    <Div  id= "divLLGrpQueryGrid" style= "display: ''">
         <Table  class= common>
             <TR  class= common>
                 <TD text-align: left colSpan=1><span id="spanLLClaimGrpQueryGrid" ></span> </TD>
             </TR>
         </Table>
         <center>
           <table>
             <tr>
                 <td><INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></td>
                 <td><INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                 <td><INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                 <td><INPUT class=cssButton93 VALUE=" 尾页 " TYPE=button onclick="turnPage.lastPage();"></td>
             </tr>
          </table>
        </center>
    </Div>     	                 
    
    <!--隐藏区,保存信息用-->
    <Input type=hidden id="GrpAppntNo" name="GrpAppntNo"><!--投保人客户号码,由前一页面传入-->
    <Input type=hidden id="isReportExist" name="isReportExist"><!--判断是否新增事件-->
    <Input type=hidden id="fmtransact" name="fmtransact"><!--动作-->
    <Input type=hidden id="State" name="State"><!--选择的状态-->
    <Input type=hidden id="ContNo" name="ContNo"><!--投保人合同号码-->

</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
