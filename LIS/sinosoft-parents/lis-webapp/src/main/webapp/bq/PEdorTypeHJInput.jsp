
<html>
<%@page contentType="text/html;charset=GBK" %>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
 <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- 私有引用脚本 -->
    <script src="PEdorTypeHJ.js"></script>
    <%@ include file="../common/jsp/UsrCheck.jsp" %>
    <%@ include file="PEdorTypeHJInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- 保全受理通用信息 -->
		<div class=maxbox1>
        <table class="common">
            <tr class="common">
                <td class="title">保全受理号</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                <td class="title">批改类型</td>
                <td class="input"><input type="text" class="codeno" name="EdorType" id=EdorType readonly><input type="text" class="codename" id=EdorTypeName name="EdorTypeName" readonly></td>
                <td class="title">保单号</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">申请日期</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorItemAppDate" id=EdorItemAppDate readonly></td>
                <td class="title">生效日期</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorValiDate" id=EdorValiDate readonly></td>
				<td class="title"></td>
                <td class="input"></td>
             </tr>
        </table>
        </div>
        <!-- 客户信息折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divCustomerLayer)"></td>
                <td class="titleImg">保单客户信息</td>
            </tr>
        </table>
        <!-- 客户信息展现表格 -->
        <div id="divCustomerLayer" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanCustomerGrid"></span></td>
                </tr>
            </table>
            <!-- 客户信息结果翻页 -->
            <div id="divTurnPageCustomerGrid" align="center" style="display:none">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPageCustomerGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPageCustomerGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPageCustomerGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPageCustomerGrid.lastPage()">
            </div>
        </div>
        <!--险种列表信息-->
        <!-- add by LuoHui 2007-12-13---begin--------------------------------->
    <!-- 保单险种信息表格 -->
    <table>
   <tr>
      <td class= common>
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
          		<td text-align: left colSpan=1>
          			<span id = "spanPolGrid">
          			</span>
        	  	</td>
        	</tr>
        </table>
    </div>
        <br>
        <!-- 获取数据的隐藏域 -->
        <input type="hidden" name="EdorNo">
        <input type="hidden" name="PolNo">
        <input type="hidden" name="InsuredNo">
        <input type="hidden" name="AppObj">
        <!-- 提交数据操作按钮 -->        
        <div id="divEdorQuery" style="display:''">
            <input type="button" class="cssButton" value=" 保 存 " onClick="saveEdorTypeHJ()">
            <input type="button" class="cssButton" value=" 返 回 " onClick="returnParent()">
            <!--input type="button" class="cssButton" value="记事本查看" onClick="showNotePad()"-->
        </div>
        <br>
        <%@ include file="PEdorFeeDetail.jsp" %>
		<br><br><br><br><br>
    </form>
</body>
</html>
