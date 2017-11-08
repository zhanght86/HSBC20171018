<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
GlobalInput tGI =new GlobalInput();
tGI =(GlobalInput)session.getValue("GI");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="AddRelationInit.jsp"%>
<SCRIPT src="AddRelation.js"></SCRIPT>
<script language="javascript">
	//alert(14)
	//从服务器端取得数据:
	var	tGrpContNo = "<%=request.getParameter("GrpContNo")%>";
	var tPrtNo = "<%=request.getParameter("PrtNo")%>";
</script>
</head>
<body  onload="initForm();">
<form method=post name=fm id=fm target="fraSubmit">
	<!-- 合同信息部分 GroupPolSave.jsp-->
     <div id="DivMultiAgent" style="display:''">
            <table>
                <tr>
                    <td class=common>
                        <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divMultiAgent1);">
                    </td>
                    <td class= titleImg>合同下有连带被保人的主被保人信息</td>
                </tr>
            </table>
            <div id="divMultiAgent1" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td style="text-align:left" colSpan="1">
                            <span id="spanMainInsuredGrid"></span>
                        </td>
                    </tr>
                </table>
        <Div  id= "divPage" align=center style= "display: '' ">
          <INPUT CLASS=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();"> 
          <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
          <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
          <INPUT CLASS=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
        </Div> 
            </div>
     </div>
     <div id="DivMultiAgent" style="display:''">
            <table>
                <tr>
                    <td class=common>
                        <img src="../common/images/butExpand.gif" style="cursor:hand;" onclick= "showPage(this,divMultiAgent1);">
                    </td>
                    <td class= titleImg>该被保人下连带被保险人信息</td>
                </tr>
            </table>
            <div id="divMultiAgent1" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td style="text-align:left" colSpan="1">
                            <span id="spanInsuredGrid"></span>
                        </td>
                    </tr>
                </table>
            </div>
     </div>	
	<Div id="divnormalbtn" style="display: ''">
		<!--<input class=cssButton VALUE="确　定"  TYPE=button onclick="SaveRelation()">
		<input class=cssButton  type=button value="返　回" onclick="top.close()">-->
	</Div>
    <a href="javascript:void(0);" class="button" onClick="SaveRelation();">确    定</a>
    <a href="javascript:void(0);" class="button" onClick="top.close();">返    回</a>
	<input type=hidden name="GrpContNo" id="GrpContNo">
	<input type=hidden name="PrtNo" id="PrtNo">
	<input type=hidden name="MainCustomerNo" id="MainCustomerNo">
</form>
	<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
