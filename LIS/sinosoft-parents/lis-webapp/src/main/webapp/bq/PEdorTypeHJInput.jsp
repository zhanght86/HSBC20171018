
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
    <!-- ˽�����ýű� -->
    <script src="PEdorTypeHJ.js"></script>
    <%@ include file="../common/jsp/UsrCheck.jsp" %>
    <%@ include file="PEdorTypeHJInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- ��ȫ����ͨ����Ϣ -->
		<div class=maxbox1>
        <table class="common">
            <tr class="common">
                <td class="title">��ȫ�����</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                <td class="title">��������</td>
                <td class="input"><input type="text" class="codeno" name="EdorType" id=EdorType readonly><input type="text" class="codename" id=EdorTypeName name="EdorTypeName" readonly></td>
                <td class="title">������</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" readonly></td>
            </tr>
            <tr class="common">
                <td class="title">��������</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorItemAppDate" id=EdorItemAppDate readonly></td>
                <td class="title">��Ч����</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorValiDate" id=EdorValiDate readonly></td>
				<td class="title"></td>
                <td class="input"></td>
             </tr>
        </table>
        </div>
        <!-- �ͻ���Ϣ�۵�չ�� -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divCustomerLayer)"></td>
                <td class="titleImg">�����ͻ���Ϣ</td>
            </tr>
        </table>
        <!-- �ͻ���Ϣչ�ֱ�� -->
        <div id="divCustomerLayer" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanCustomerGrid"></span></td>
                </tr>
            </table>
            <!-- �ͻ���Ϣ�����ҳ -->
            <div id="divTurnPageCustomerGrid" align="center" style="display:none">
                <input type="button" class="cssButton90" value="��  ҳ" onclick="turnPageCustomerGrid.firstPage()">
                <input type="button" class="cssButton91" value="��һҳ" onclick="turnPageCustomerGrid.previousPage()">
                <input type="button" class="cssButton92" value="��һҳ" onclick="turnPageCustomerGrid.nextPage()">
                <input type="button" class="cssButton93" value="β  ҳ" onclick="turnPageCustomerGrid.lastPage()">
            </div>
        </div>
        <!--�����б���Ϣ-->
        <!-- add by LuoHui 2007-12-13---begin--------------------------------->
    <!-- ����������Ϣ��� -->
    <table>
   <tr>
      <td class= common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol2);">
      </td>
      <td class= titleImg>
        ���ֻ�����Ϣ
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
        <!-- ��ȡ���ݵ������� -->
        <input type="hidden" name="EdorNo">
        <input type="hidden" name="PolNo">
        <input type="hidden" name="InsuredNo">
        <input type="hidden" name="AppObj">
        <!-- �ύ���ݲ�����ť -->        
        <div id="divEdorQuery" style="display:''">
            <input type="button" class="cssButton" value=" �� �� " onClick="saveEdorTypeHJ()">
            <input type="button" class="cssButton" value=" �� �� " onClick="returnParent()">
            <!--input type="button" class="cssButton" value="���±��鿴" onClick="showNotePad()"-->
        </div>
        <br>
        <%@ include file="PEdorFeeDetail.jsp" %>
		<br><br><br><br><br>
    </form>
</body>
</html>
