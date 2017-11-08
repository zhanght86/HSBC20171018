
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    String tDisplay = "";
    String tContNo="";
    try
    {
       tDisplay = request.getParameter("display");
       //loggerDebug("PersonQueryInput",tDisplay+"--------------------");
    }
    catch( Exception e )
    {
        tDisplay = "";
    }
%>

<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI");//添加页面控件的初始化。
   //loggerDebug("PersonQueryInput","管理机构-----"+tG.ComCode);
%>

<script>
  var comCode = "<%=tG.ComCode%>";
</script>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>客户信息查询</title>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本 -->
     <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="PersonQuery.js"></script>
    <%@ include file="PersonQueryInit.jsp" %>
</head>
<body  onload="initForm()" >
  <form method=post name=fm id=fm target="fraSubmit">
    <!-- 客户基本信息部分 -->

  <!-- Add By QianLy --------------->
  <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divIdNo);">
            </td>
            <td class= titleImg align=left>
               客户基本信息查询：
              </td>
         </tr>
    </table>
        <Div  id= "divIdNo" style= "display: ''" align="left">
           <div class="maxbox1" >
        <table class= common border=0 width=100%>
            <tr>
            <td class= titleImg1 align="left">
               注意："证件号码"处可以填写登记过的证件号，例如身份证、驾照、军官证、护照等。
              </td>
         </tr>
        </table>
        <table  class= common>
        <TR  class= common>
        <TD  class= title5>客户姓名</TD>
          <TD  class= input5> <Input class= "common wid" name=Name elementtype=nacessary> </TD>
           <TD  class= title5>性别</TD>
         <TD  class= input5> <Input name=Sex class="codeno" MAXLENGTH=1 
         style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          onclick="return showCodeList('Sex',[this,SexName],[0,1]);" 
         onDblClick="return showCodeList('Sex',[this,SexName],[0,1]);" 
         onKeyUp="return showCodeListKey('Sex',[this,SexName],[0,1]);" ><input name=SexName  class=codename readonly=true>      	
        	</TD>
             </TR>
             <tr class= common>
          <TD  class= title5>出生日期</TD>
         <TD  class= input5>
         <Input class="coolDatePicker" 
          onClick="laydate({elem: '#BIRTHDAY'});"dateFormat="short" name=BIRTHDAY id=BIRTHDAY><span class="icon"><a onClick="laydate({elem: '#BIRTHDAY'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
         
         </TD>
          <TD  class= title5>证件号码</TD>
          <TD  class= input5> <Input class="common wid"name=IDNo > </TD>
          </TR>
                        <!--td class="title">&nbsp;</td-->
                        <!--td class="input">&nbsp;</td-->
        </TR>
       </table>
       <a href="javascript:void(0);" class="button"onClick="QueryClick();">查   询</a>
           <!-- <INPUT VALUE=" 查 询 " class = cssButton TYPE=button onClick="QueryClick();">-->

        </div>
 
       </div>
  <!-- Add By QianLy --------------->


   <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomer1);">
            </td>
            <td class= titleImg>
                 客户基本信息
            </td>
        </tr>
    </table>
     <Div id= "divCustomer1" style= "display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanCustomerGrid"></span></td>
            </tr>
        </table>
          <!--  <div id="divTurnPageCustomerGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton" value="首  页" onClick="turnPageCustomerGrid.firstPage()">
                <input type="button" class="cssButton" value="上一页" onClick="turnPageCustomerGrid.previousPage()">
                <input type="button" class="cssButton" value="下一页" onClick="turnPageCustomerGrid.nextPage()">
                <input type="button" class="cssButton" value="尾  页" onClick="turnPageCustomerGrid.lastPage()">
            </div>-->
    </div>
    
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAddressGrid);">
            </td>
            <td class= titleImg>
                 客户地址信息
            </td>
        </tr>
    </table>
    <Div id= "divAddressGrid" style= "display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanAddressGrid"></span></td>
            </tr>
        </table>
           <!-- <div id="divTurnAddressGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton" value="首  页" onClick="turnPageAddressGrid.firstPage()">
                <input type="button" class="cssButton" value="上一页" onClick="turnPageAddressGrid.previousPage()">
                <input type="button" class="cssButton" value="下一页" onClick="turnPageAddressGrid.nextPage()">
                <input type="button" class="cssButton" value="尾  页" onClick="turnPageAddressGrid.lastPage()">
            </div>-->
    </div>
    
    
     <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAccountGrid);">
            </td>
            <td class= titleImg>
                 客户银行账户信息
            </td>
        </tr>
    </table>
     <Div id= "divAccountGrid" style= "display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanAccountGrid"></span></td>
            </tr>
        </table>
            <div id="divTurnPageAccountGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton90" value="首  页" onClick="turnPageAccountGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onClick="turnPageAccountGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onClick="turnPageAccountGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onClick="turnPageAccountGrid.lastPage()">
            </div>
    </div>
    
    <p>
     <INPUT VALUE=" 既往投保 " Name=Pol class = cssButton  TYPE=button onClick="PolClick();">
     <INPUT VALUE=" 既往保全 " Name=Edor class = cssButton  TYPE=button onClick="EdorClick();">
     <INPUT VALUE=" 既往理赔 " Name=Claim class = cssButton  TYPE=button onClick="ClaimClick();">
     <INPUT VALUE=" 既往收费 " Name=Pay class = cssButton  TYPE=button onClick="PayClick();">
     <INPUT VALUE=" 既往领取 " Name=Get class = cssButton  TYPE=button onClick="GetClick();">
     </p>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
