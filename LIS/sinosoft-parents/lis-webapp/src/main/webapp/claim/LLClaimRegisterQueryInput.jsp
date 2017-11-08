<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/AccessCheck.jsp"%>
<html>
<head>
<title>立案信息查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script src="./LLClaimRegisterQuery.js"></script> 
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLClaimRegisterQueryInit.jsp"%>
<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
%>
</head>
<body  onload="initForm();">
<!--登录画面表格-->
<br>
<form name=fm id=fm target=fraSubmit method=post>
	  <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,spanLLClaimRegister);"></TD>
            <TD class= titleImg> 查询条件 </TD>
        </TR>
    </Table>
    <span id= "spanLLClaimRegister" class=maxbox style= "display: ''">
        <table class= common>
            <TR class= common>    
                <TD class= title> 赔案号 </TD>
                <TD class= input> <Input class="common wid" name=RptNo id=RptNo ></TD>
                <TD class= title> 申请人姓名 </TD>
                <TD class= input> <Input class= "common wid" name=RptorName id=RptorName ></TD>
                <TD class= title> 申请人电话 </TD>       
                <TD class= input> <Input class= "common wid" name=RptorPhone ></TD>
            </TR>
            <TR class= common>                  
                <TD class= title> 申请人通讯地址 </TD>
                <TD class= input> <Input class= "common wid" name=RptorAddress id=RptorAddress ></TD>          
                <TD class= title> 申请人与事故人关系 </TD>
                <TD class= input> <Input class=codeno name=Relation id=Relation style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('Relation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('Relation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true></TD>
                <TD class= title> 立案结论 </TD>
                <TD class= input> <Input class=codeno name="RgtConclusion" id=RgtConclusion style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('RptMode',[this,RptModeName],[0,1]);" onkeyup="return showCodeListKey('RptMode',[this,RptModeName],[0,1]);"><input class=codename name="RptModeName" id=RptModeName readonly=true></TD>            
            </TR>
<!--
            <TR class= common>            
                <TD class= title> 出险地点 </TD>     
                <TD class= input> <Input class= common name=AccidentSite ></TD>        
                <TD class= title> 报案受理日期 </TD>
                <TD class= input>  <input class="readonly" readonly name="RptDate" ></TD>
                <TD class= title> 管辖机构 </TD>
                <TD class= input> <Input class= "readonly" readonly name=MngCom></TD>         
            </TR>
            <TR class= common>                  
                <TD class= title> 报案受理人 </TD>
                <TD class= input> <Input class="readonly" readonly name=Operator ></TD>        
                <TD class= title> 理赔状态 </TD>
                <TD class= input> <Input class= "readonly" readonly name=CaseState></TD>
            </TR>
-->            
        </table> 
    </span>  
    <table> 
        <tr>    
            <td> <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();">  </td>
            <td> <INPUT class=cssButton VALUE="返  回" TYPE=button onclick="returnParent();">  </td>
        </tr> 
    </table> 
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimRegister);"></TD>
            <TD class= titleImg> 立案信息 </TD>
        </TR>
    </Table>      
    <Div  id= "divLLClaimRegister" style= "display: ''" align=center>
        <Table  class= common>
            <TR  class= common>
                <TD style="text-align: left" colSpan=1><span id="spanLLClaimRegisterGrid" ></span> </TD>
            </TR>
        </Table>
        </Table>
        <table> 
            <tr>  
                <td><INPUT class=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table>  
    </Div>          

    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>          
</Form>
</body>
</html>
