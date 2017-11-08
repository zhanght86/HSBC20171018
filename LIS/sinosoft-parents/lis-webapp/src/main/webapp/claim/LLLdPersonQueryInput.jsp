<%
//**************************************************************************************************
//Name：LLReportInput.jsp
//Function：出险人查询
//Author：zl
//Date: 2005-6-9 15:31
//Desc: 
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
    GlobalInput tG = new GlobalInput();
    tG=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
    
    String CurrentDate = PubFun.getCurrentDate();
    String tFlag = request.getParameter("tFlag");	////投保人客户号
//=======================END========================
%>
<head>
<title>客户信息查询</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script src="./LLLdPersonQuery.js"></script> 
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLLdPersonQueryInit.jsp"%>
</head>
<body  onload="initForm();">
<!--登录画面表格-->
<br>
<form name=fm id=fm target=fraSubmit method=post>
	  <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,spanLDPerson);"></TD>
            <TD class= titleImg> 查询条件(请至少输入一项查询条件) </TD>
        </TR>
    </Table>
	<div class=maxbox1>
    <span id= "spanLDPerson" style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class= title> 客户号码 </TD>
                <TD class= input> <Input class="wid" class= common name=CustomerNo id=CustomerNo >  </TD>
                <TD class= title> 姓名 </TD>
                <TD class= input> <Input class="wid" class= common name=Name id=Name >  </TD>
                <TD class= title> 出生日期</TD>
                <TD class= input> <!--<Input class="multiDatePicker" dateFormat="short" name=Birthday onblur=" CheckDate(fm.Birthday); " >-->
                <Input class="coolDatePicker"  onblur=" CheckDate(fm.Birthday); " onClick="laydate({elem: '#Birthday'});" verify="有效开始日期|DATE" dateFormat="short" name=Birthday id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
            </TR>             
            <TR  class= common>
                <TD  class= title> 性别  </TD>
                <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Sex id=Sex class=codeno ondblclick="return showCodeList('Sex',[this,SexName],[0,1]);" onclick="return showCodeList('Sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('Sex',[this,SexName],[0,1]);" ><input class=codename name="SexName" id="SexName" readonly=true></TD>       
                <TD  class= title> 证件类型 </TD>
                <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=IDType id=IDType class=codeno ondblclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onclick="return showCodeList('IDType',[this,IDTypeName],[0,1]);" onkeyup="return showCodeListKey('IDType',[this,IDTypeName],[0,1]);"><input class=codename name="IDTypeName" id="IDTypeName" readonly=true></TD>
                <TD  class= title> 证件号码 </TD>
                <TD  class= input> <Input class="wid" class= common name=IDNo id=IDNo > </TD>
            </TR>   
            <TR  class= common>
                <TD  class= title> 保单号码 </TD>
                <TD  class= input> <Input class="wid" class= common name=ContNo id=ContNo > </TD>
                <!--  <TD  class= title> 团体保单号码 </TD>
                <TD  class= input> <Input class= common name=GrpContNo > </TD>-->
            </TR>   
        </Table>
    </span>  
	</div>
    <!--<table> 
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
       <!-- </tr> 
    </table>-->
    <INPUT class=cssButton VALUE="查  询" TYPE=button onclick="easyQueryClick();"> 
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson);"></TD>
            <TD class= titleImg> 客户信息 </TD>
        </TR>
    </Table>      
    <Div  id= "divLDPerson1" style= "display: ''">
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanPersonGrid" ></span> </TD>
            </TR>
        </Table>
        <table align= center> 
            <tr>  
                <td><INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr> 
        </table> 
    </Div>
    <INPUT class=cssButton VALUE="返  回" TYPE=button onclick="returnParent();">          
   <Input type=hidden id="Flag" name="Flag"><!--入口标记-->
   <span id="spanCode"  style="display: none; position:absolute; slategray"></span>          
</Form>
</body>
</html>
