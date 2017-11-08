<html>
<%
//Name:LLRgtMAffixListInput.jsp
//Function：单证回销主界面
//Date：2005-07-1 17:44:28
//Author ：yuejw
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
	<% 
		  GlobalInput tG = new GlobalInput();
		  tG = (GlobalInput)session.getValue("GI");		  
	      String tClmNo=request.getParameter("ClmNo"); //赔案号
	      String tCustomerNo=request.getParameter("CustomerNo"); //客户号   
	      String tProc=request.getParameter("Proc"); //标志	          		  
	%> 	
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>    
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="LLRgtMAffixList.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLRgtMAffixListInit.jsp"%>
</head>
<body onload="initForm();">
<form action="./LLRgtMAffixListSave.jsp?" method=post name=fm target="fraSubmit">
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DivRgtAffixGrid);"></td>
            <td class= titleImg>已经回销单证清单</td>
        </tr>
    </table>
    <Div id= "DivRgtAffixGrid" style= "display: ''">
        <table class= common>
            <tr class= common>
                <td text-align: left colSpan=1><span id="spanRgtAffixGrid" ></span></td>
            </tr>      
        </table>
    </Div>    
    <hr>
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,AppAffixList);"></td>
            <td class= titleImg>待回销的单证清单</td>
        </tr>
    </table>
    <Div id= "AppAffixList" style= "display: ''">
        <table class= common>
            <tr class= common>
                <td text-align: left colSpan=1><span id="spanAffixGrid" ></span></td>
            </tr>      
        </table>
        <table>
    	    <tr>    
                <td><input class=cssButton type=button id="save" name="save" value="保  存" onclick="AffixSave()"></td>
                <td><input class=cssButton type=button id="close" name="close"  value="返  回" onclick="returnParent()"></td>
            </tr>
        </table>
    </div>
    <hr>

                                                                
    <input type=hidden id="Operate" name="Operate">
    <input type=hidden id="ClmNo" name="ClmNo">
    <input type=hidden id="CustomerNo" name="CustomerNo">
    <!--Proc为页面进入标志，Rpt:报案  Rgt:立案 RgtAdd:立案补充 -->
    <input type="hidden" id="Proc" name="Proc">    
</form>
</body>
</html>
