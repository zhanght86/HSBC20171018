<%
//程序名称：LLGrpLdPersonQueryInput.jsp
//程序功能：团体客户信息查询
//创建日期： 2008-10-27
//创建人  ：zhangzheng
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<title>团体客户信息查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <script src="./LLGrpLdPersonQuery.js"></script> 
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="LLGrpLdPersonQueryInit.jsp"%>
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
                <TD class= title> 团体客户号码 </TD>
                <TD class= input> <Input class= common name=CustomerNo >  </TD>
                <TD  class= title> 团体名称 </TD>
                <TD  class= input> <Input class= common name=GrpContName > </TD> 
                <TD  class= title> 团体保单号 </TD>
                <TD  class= input> <Input class= common name=GrpContNo > </TD>
            </TR>
            
            <TR class= common>
               <TD class= title> 被保人号 </TD>
                <TD class= input> <Input class= common name=InsuredNo >  </TD>
                <TD  class= title> 被保人姓名 </TD>
                <TD  class= input> <Input class= common name=Name > </TD> 
               </TR>
        </Table>
    </span>  
    <table> 
        <tr>    
            <td> <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();">  </td>
            <td> <INPUT class=cssButton VALUE="返  回" TYPE=button onclick="returnParent();">  </td>
        </tr> 
    </table> 
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson1);"></TD>
            <TD class= titleImg> 团体客户信息 </TD>
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
  <Input type=hidden id="ManageCom" name="ManageCom"><!--登陆区站-->
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>          
</Form>
</body>
</html>
