
<%
//程序名称：
//程序功能：个人保全
//创建日期：2002-07-19 16:49:22
//创建人  ：Supl
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>保单复效</title>
    <!-- 检查访问地址 -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeRE.jsp";
        }
    </script>
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
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="PEdorTypeRE.js"></script>
    <%@ include file="PEdorTypeREInit.jsp" %>
</head>
<body onload="initForm();" >
  <form action="./PEdorTypeRESubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <div class="maxbox1">
        <table class="common">
            <tr class="common">
                <td class="title">保全受理号</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id="EdorAcceptNo" readonly></td>
                <td class="title">批改类型</td>
                <td class="input"><input type="text" class="codeno" name="EdorType" id="EdorType" readonly><input type="text" class="codename" name="EdorTypeName" id="EdorTypeName" readonly></td>
                <td class="title">保单号</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" id="ContNo" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">柜面受理日期</td>
                <td class="input"><!--<input type="text" class="multiDatePicker" name="EdorItemAppDate" readonly>-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#EdorItemAppDate'});" verify="有效开始日期|DATE" dateFormat="short" name=EdorItemAppDate id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </td>
                <!--  
                <td class="title">生效日期</td>
                <td class="input"><input type="text" class="multiDatePicker" name="EdorValiDate" readonly></td>
                -->
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
		</div>
   <!--险种的详细信息-->
   <table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol1);">
      </td>
      <td class= titleImg>
        客户基本信息
      </td>
   </tr>
   </table>

    <Div  id= "divPol1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanCustomerGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </Div>

  <table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol2);">
      </td>
      <td class= titleImg>
        险种基本信息
      </td>
   </tr>
   </table>
        <Div  id= "divPol2" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanLCInsuredGrid"></span></td>
            </tr>
        </table>
      </Div>
        <!--加保后信息-->
<table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
      </td>
      <td class= titleImg>
        被保人健康告知信息
      </td>
   </tr>
   </table>

  <Div  id= "divLCImpart1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanImpartGrid"></span></td>
            </tr>
        </table>
    </Div>
<div id = "divAppnt" style = "display: none">
<table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divImpart);">
      </td>
      <td class= titleImg>
        投保人健康告知信息
      </td>
   </tr>
   </table>
</div>
<div id = "divInsured" style = "display: none">
<table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divImpart);">
      </td>
      <td class= titleImg>
        联生险第二被保人健康告知信息
      </td>
   </tr>
   </table>
</div>

  <Div  id= "divImpart" style= "display: none">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanAppntImpartGrid"></span></td>
            </tr>
        </table>
    </Div>
</div>

   <!--
   <table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,OperationDiv);">
      </td>
      <td class= titleImg>
        保单复效信息
      </td>
   </tr>
   </table>
    <Div id = "OperationDiv" style= "display: ">
        <table class = common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanPolInsuredGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </Div>
    -->
    
    <table>
      <tr>
        <td class= titleImg>
          复效信息（是否计算复效保费利息<Input name="isCalInterest" type=checkbox checked verify="是否计算利息|code:YesNo">）
        </td>
      </tr>
    </table>

    <br>
    <Div  id= "divEdorquery" style= "display: ''">
             <Input class= cssButton type=Button value=" 保 存 " onclick="saveEdorTypeRE()">
             <Input class= cssButton type=Button value=" 返 回 " onclick="returnParent()">
             <Input class= cssButton type=Button value="记事本查看" onclick="showNotePad()">
   </Div>

     <input type="hidden" name="CalMode">
     <input type="hidden" name="fmtransact">
     <input type="hidden" value= 1 name="ContType">
     <input type="hidden" value= 1 name="EdorNo">
     <input type="hidden" value= 1 name="InsuredNo">
     <input type="hidden" value= 1 name="CustomerNo"> <!-- 含投保人责任和联身险被包人共用-->
     <input type="hidden" value= 1 name="ReFlag">  <!-- 联身险标志--->

    <br>
    <%@ include file="PEdorFeeDetail.jsp" %>
    <br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
