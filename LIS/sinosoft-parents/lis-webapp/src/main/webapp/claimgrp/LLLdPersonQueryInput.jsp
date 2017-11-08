<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<title>客户信息查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <script src="./LLLdPersonQuery.js"></script> 
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLLdPersonQueryInit.jsp"%>
<%
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
%>
</head>
<body  onload="initForm();">
<!--登录画面表格-->
<br>
<form name=fm target=fraSubmit method=post>
	  <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,spanLDPerson);"></TD>
            <TD class= titleImg> 查询条件 </TD>
        </TR>
    </Table>
    <span id= "spanLDPerson" style= "display: ''">
        <table class= common align=center>
            <TR class= common>
                <TD class= title> 客户号码 </TD>
                <TD class= input> <Input class= common name=CustomerNo >  </TD>
                <TD class= title> 姓名 </TD>
                <TD class= input> <Input class= common name=Name >  </TD>
                <TD class= title> 出生日期</TD>
                <TD class= input> <Input class="coolDatePicker" dateFormat="short" name=Birthday >             </TD>
            </TR>             
            <TR  class= common>
                <TD  class= title> 性别  </TD>
                <TD  class= input> <Input name=Sex class=codeno ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);" ><input class=codename name="SexName" readonly=true></TD>       
                <TD  class= title> 证件类型 </TD>
                <TD  class= input><Input name=IDType class=codeno ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);"><input class=codename name="IDTypeName" readonly=true></TD>
                <TD  class= title> 证件号码 </TD>
                <TD  class= input> <Input class= common name=IDNo > </TD>
            </TR>   
            <TR  class= common>
                <TD  class= title> 保单号码 </TD>
                <TD  class= input> <Input class= common name=ContNo > </TD>
                <TD  class= title> 团体保单号码 </TD>
                <TD  class= input> <Input class= common name=GrpContNo > </TD>
            </TR>   
        </Table>
    </span>  
    <table> 
        <tr>    
            <td> <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();">  </td>
            <td> <INPUT class=cssButton VALUE="返  回" TYPE=button onclick="returnParent();">  </td>
            <!--td> <INPUT class=cssButton VALUE="返回(不关闭窗口)" TYPE=button onclick="returnParent2();">  </td-->
            <!--
            <td>  <INPUT class=cssButton VALUE="客户信息" TYPE=button onclick="returnParent();">  </td>
            <td> <INPUT class=cssButton VALUE="投保单信息" TYPE=button onclick="ProposalClick();"> </td>
            <td>  <INPUT class=cssButton VALUE="保单信息" TYPE=button onclick="PolClick();"> </td>
            <td>  <INPUT class=cssButton VALUE="销户保单信息" TYPE=button onclick="DesPolClick();"> </td>
            -->
        </tr> 
    </table> 
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson);"></TD>
            <TD class= titleImg> 客户信息 </TD>
        </TR>
    </Table>      
    <Div  id= "divLDPerson1" style= "display: ''" align= center>
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanPersonGrid" ></span> </TD>
            </TR>
        </Table>
        <table> 
            <tr>  
                <td><INPUT class=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table> 
    </Div>          

    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>          
</Form>
</body>
</html>
