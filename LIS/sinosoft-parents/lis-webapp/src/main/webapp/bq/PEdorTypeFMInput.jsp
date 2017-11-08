<html>
<%
//程序名称：PEdorTypeFMInput.jsp
//程序功能：个人保全-缴费方式及期限变更
//创建日期：2005-05-14 11:05:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeFM.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeFMInit.jsp"%>

  <title> 交费年期变更 </title>
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeFMSubmit.jsp" method=post name=fm id=fm target="fraSubmit">

<!-- 保全受理信息 -->
    <div class=maxbox1>
    <TABLE class=common>
        <tr class=common>
            <td class=title> 保全受理号 </td>
            <td class= input><Input type="input" class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo></td>
            <td class=title> 批改类型 </td>
            <td class= input><Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true></td>
            <td class=title> 保单号 </td>
            <td class= input><Input type="input" class="readonly wid" readonly name=ContNo> </td>
        </tr>
        <tr class=common>
            <td class=title> 柜面受理日期 </td>
            <td class= input><Input type="input" class="coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
            <td class=title>  </td>
            <td class= input> </td>
			<td class=title>  </td>
            <td class= input> </td>
        </tr>
    </TABLE>
	</div>
    <Div  id= "divMultiPol" style= "display: none">
        <table>
                <tr>
                    <td class=common>
                        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid);">
                    </td>
                    <td class= titleImg> 变更险种信息<font color = red >(选中单选按钮查询更详细的险种信息)</font> </td>
                </tr>
        </table>

        <Div  id= "divPolGrid" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td text-align: left colSpan=1>
                        <span id="spanPolGrid" >
                        </span>
                    </td>
                </tr>
            </table>
        </Div>
    </Div>

    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolInfo);">
            </td>
            <td class= titleImg> 险种信息<font color = red >(保存后选中单选按钮查询变更后的险种信息)</font> </td>
        </tr>
    </table>

    <Div id= "divPolInfo" class=maxbox1 style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td class=title> 险种号 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=PolNo_old id=PolNo_old></td>
                <td class=title> 险种代码 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=RiskCode_old id=RiskCode_old></td>
                <td class=title> 险种名称 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=RiskName_old id=RiskName_old></td>
            </tr>
            <tr  class= common>
                <td class=title> 投保人 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=AppntName_old id=AppntName_old></td>
                <td class=title> 被保人 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=InsuredName_old id=InsuredName_old></td>
                <td class=title> 投保年龄 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=InsuredAppAge_old id=InsuredAppAge_old></td>
            </tr>
            <tr  class= common>
                <td class=title> 保费标准 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=Prem_old id=Prem_old></td>
                <td class=title> 基本保额 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=Amnt_old id=Amnt_old></td>
                <td class=title> 份数 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=Mult_old id=Mult_old></td>
            </tr>
            <tr  class= common>
                <td class=title> 缴费期限 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=PayYears_old id=PayYears_old ></td>
                <td class=title> 缴费方式 </td>
                <td class= input><Input type="input" class="readonly wid" readonly name=PayIntv_old id=PayIntv_old ></td>
                <td class=title>  </td>
                <td class= input> </td>
            </tr>
        </table>
    </DIV>


    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorTypeChangeInfo);">
            </td>
            <td class= titleImg> 交费年期变更信息 </td>
        </tr>
    </table>

    <Div id= "divEdorTypeChangeInfo" class=maxbox1 style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <TD  class= title> 缴费期限 </TD>
                <TD  class= input><Input class= "codeno" name=PayYears_new id=PayYears_new style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
                    ondblClick="initPayEndYear(this,PayYears_newName);"
                    onkeyup="initPayEndYear(this,PayYears_newName);" ><input class=codename name=PayYears_newName id=PayYears_newName readonly=true></TD>
					<td class=title>  </td>
					<td class= input> </td>
					<td class=title>  </td>
					<td class= input> </td>
            </tr>
        </table>
    </DIV>
  <br>
        <div id="divEdorQuery" style="display:''">
            <input type="button" class="cssButton" value=" 保 存 " onClick="saveEdorTypeFM()">
            <input type="button" class="cssButton" value=" 返 回 " onClick="returnParent()">
            <input type="button" class="cssButton" value="记事本查看" onClick="showNotePad()">
        </div>

        <input type=hidden name="fmtransact">
        <input type=hidden name="InsuredNo">
        <input type=hidden name="PolNo">
        <input type=hidden name="EdorNo">
        <input type=hidden name="RiskCode">
        <input type=hidden name="MainRiskCode">
        <input type=hidden name="MainPayEndYear">
        <br>
        <%@ include file="PEdorFeeDetail.jsp" %>
		<Br /><Br /><Br /><Br />
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>
