<html>
<%
//程序名称：
//程序功能：个人保全
//创建日期：2002-07-19 16:49:22
//创建人  ：Tjj
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
  <%
     %>

<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeLR.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeLRInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeLRSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <div class=maxbox1>
  <TABLE class=common>
    <TR  class= common>
      <TD  class= title > 保全受理号</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title > 批改类型 </TD>
      <TD class = input >
        <Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>

      <TD class = title > 保单号 </TD>
      <TD class = input >
        <input class = "readonly wid" readonly name=ContNo id=ContNo>
      </TD>
    </TR>
       <TR  class= common>
               <TD class =title>柜面受理日期</TD>
        <TD class = input>
            <input class="multiDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        </TD>
        <TD class =title>生效日期</TD>
        <TD class = input>
            <input class="multiDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        <TD class= title width="">
        </TD>
        <TD  class= input width="">
        </TD>
      </TR>
  </TABLE>
  </div>
   <table>
   <tr>
      <td class=common>
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
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMainPol);">
      </td>
      <td class= titleImg>
        险种基本信息
      </td>
   </tr>
   </table>

    <Div  id= "divMainPol" style= "display: ''">
      	<table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanMainPolGrid" >
        			</span>
        	  	</td>
        	</tr>
        </table>
    </Div>
    
            <table class="common">
                <!--tr class="common">
                    <td class="title">险种号</td>
                    <td class="input"><input type="text" class="readonly" name="PolNo" readonly></td>
                    <td class="title">险种代码</td>
                    <td class="input"><input type="text" class="readonly" name="RiskCode" readonly></td>
                    <td class="title">险种名称</td>
                    <td class="input"><input type="text" class="readonly" name="RiskName" readonly></td>
                </tr>
                <tr class="common">
                    <td class="title">投保人</td>
                    <td class="input"><input type="text" class="readonly" name="AppntName" readonly></td>
                    <td class="title">被保人</td>
                    <td class="input"><input type="text" class="readonly" name="InsuredName" readonly></td>
                    <td class="title">投保年龄</td>
                    <td class="input"><input type="text" class="readonly" name="InsuredAppAge" readonly></td>
                </tr-->
                <tr class="common">
                    <td class="title">保单补发次数</td>
                    <td class="input"><input type="text" class="readonly wid" name="LostTimes" id=LostTimes readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
   <table>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAGInfo);">
      </td>
      <td class= titleImg>
        补发信息
      </td>
   </tr>
   </table>

    <Div  id= "divAGInfo" class=maxbox1 style= "display: ''">
      <table  class= common>
        <TR class = common >
          <TD class= title >
          补发原因<font color=red> *</font>
          </TD>
          <TD  class= input>
          <!--<input class="codeno" name=GoonGetMethod1 CodeData="0|^01|客户丢失^02|客户污损^03|业务员丢失 ^04|业务员污损" ondblclick="return showCodeListEx('GoonGetMethod1', [this,GoonGetMethod1Name],[0,1]);" onkeyup="return showCodeListKeyEx('GoonGetMethod1', [this,GoonGetMethod1Name],[0,1]);" ><input class="codename" name=GoonGetMethod1Name readonly=true>-->
           <Input class=codeno name=GoonGetMethod1 id=GoonGetMethod1 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="补发原因|code:comcode&notnull" ondblclick="return showCodeList('edorreason_lr',[this,GoonGetMethod1Name],[0,1]);" onkeyup="return showCodeListKey('edorreason_lr',[this,GoonGetMethod1Name],[0,1]);"><input class=codename name=GoonGetMethod1Name readonly=true elementtype=nacessary></td>
          </TD>
                <td class="title"><div id="divGetMoneyTitle" style="display:none">交费金额(工本费)</div></td>
                <td class="input"><div id="divGetMoneyInput" style="display:none"><input type="text" class="common wid" name="GetMoney" id=GetMoney onblur="return checkNumber(this)"></div></td>
        <TD class= title width="">
        </TD>
        <TD  class= input width="">
        </TD>
        </TR>
      </table>
    </Div>
    <br>
  <Div id= "divEdorquery" style="display: ''">
            <input type="button" class="cssButton" value=" 保 存 " onClick="edorTypeLRSave()">
            <input type="button" class="cssButton" value=" 返 回 " onClick="returnParent()">
            <input type="button" class="cssButton" value="记事本查看" onClick="showNotePad()">
  </Div>
        <input type="hidden" name="AppObj">
     <input type=hidden id="fmtransact" name="fmtransact">
      <input type=hidden id="EdotNo" name="EdorNo">
      <input type=hidden id="ContType" name="ContType">
      <input type=hidden id="GrpContNo" name="GrpContNo">
      <input type=hidden id="InsuredNo" name="InsuredNo">
   <br>
    <%@ include file="PEdorFeeDetail.jsp" %>
    <br><br><br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script language="javascript">
var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>
