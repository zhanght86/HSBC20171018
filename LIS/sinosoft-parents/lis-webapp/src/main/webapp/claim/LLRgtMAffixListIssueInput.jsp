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
	      String tProc=request.getParameter("Proc"); //标志	          		  
	%> 	
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>    
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>     
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLRgtMAffixListIssue.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLRgtMAffixListIssueInit.jsp"%>
</head>
<body onload="initForm();">
<form action="./LLRgtMAffixListSave.jsp?" method=post name=fm id=fm target="fraSubmit">
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
                <td style="text-align: left" colSpan=1><span id="spanRgtAffixGrid" ></span></td>
            </tr>      
        </table>
    </Div>    
    <hr class=line>
    <table>
        <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,AppAffixList);"></td>
            <td class= titleImg>待回销的单证清单</td>
        </tr>
    </table>
    <Div id= "AppAffixList" style= "display: ''">
        <table class= common>
            <tr class= common>
                <td style="text-align: left" colSpan=1><span id="spanAffixGrid" ></span></td>
            </tr>      
        </table>
 	 <hr class=line>	
    <table class= common>
            <TR class= common>    
                <TD class= title5> 补充材料受理日期 </TD>       
                <TD class= input5> <input class="coolDatePicker" dateFormat="short" name="ReplyDate" onClick="laydate({elem: '#ReplyDate'});" id="ReplyDate"><span class="icon"><a onClick="laydate({elem: '#ReplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></TD>    
				<TD class= title5>  </TD>       
                <TD class= input5>  </TD>  
            </TR>
     </table> 
 	 <hr class=line>
        <table>
    	    <tr>    
                <td><input class=cssButton type=button id="save" name="save" value="回   销 " onclick="AffixSave()"></td>
                <td><input class=cssButton type=button id="close" name="close"  value="返  回" onclick="returnParent()"></td>
            </tr>
        </table>
    </div>
    <hr class=line>

                                                                
    <input type=hidden id="Operate" name="Operate">
    <input type=hidden id="ClmNo" name="ClmNo">
    <!--Proc为页面进入标志，Rpt:报案  Rgt:立案 RgtAdd:立案补充 -->
    <input type="hidden" id="Proc" name="Proc">    
</form>
</body>
</html>
