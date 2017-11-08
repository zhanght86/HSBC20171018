
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
    }
    catch( Exception e )
    {
        tDisplay = "";
    }
%>

<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
   //loggerDebug("AllProposalQuery","管理机构-----"+tG.ComCode);
%>

<script>
  var comCode = "<%=tG.ComCode%>";
</script>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>保单查询</title>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本 -->
    <script language="JavaScript" src="../common/laydate/laydate.js"></script>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="AllProposalQuery.js"></script>
    <%@ include file="AllProposalQueryInit.jsp" %>
</head>
<body  onload="initForm()" >
  <form method=post name=fm id=fm target="fraSubmit">
    <!-- 保单信息部分 -->
  <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divIdNo);">
            </td>
            <td class= titleImg>
               一号快速查询：
              </td>
         </tr>
    </table>
        <Div  id= "divIdNo" style= "display: ''"  class="maxbox1">
       <!-- <table class= common border=0 width=100%>
            <tr>
            <td class= titleImg align="left">
               注意："证件号码"处可以填写登记过的证件号，例如身份证、驾照、军官证、护照等。
              </td>
         </tr>
        </table>-->
        <font color = red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注意："证件号码"处可以填写登记过的证件号，例如身份证、驾照、军官证、护照等。</font>
        <table  class= common>
        <TR  class= common>
          <TD  class= title>证件号码</TD>
          <TD  class= input> <Input class="wid" class= common name=IdCardNo id=IdCardNo > </TD>
          <TD  class= title>客户号码</TD>
          <TD  class= input> <Input class="wid" class= common name=CustomerNo id=CustomerNo > </TD>
          <TD  class= title>印刷号</TD>
          <TD  class= input> <Input class="wid" class= common name=PrtNo id=PrtNo > </TD>
        </TR>
       </table> </div>
           <!-- <INPUT VALUE=" 查 询 " class = cssButton TYPE=button onclick="quickQueryClick();">

          <INPUT VALUE=" 返 回 " name=ReturnBack class = cssButton TYPE=button STYLE="display:none" onclick="returnParent()">-->
          <a href="javascript:void(0);" class="button" onClick="quickQueryClick();">查    询</a>
          <a href="javascript:void(0);" name=ReturnBack class="button" STYLE="display:none" onClick="returnParent();">返    回</a>
      
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFq);">
            </td>
            <td class= titleImg>
                多项条件查询：
              </td>
        </tr>
    </table>

    <Div  id= "divFq" style= "display: ''" class="maxbox1">
 <!-- <table>
        <tr>
            <td class= titleImg align= center>注意：请至少输入以下号码之一：保单号码、投保单号码、划款协议书号。如无法提供上述号码请输入至少三条数据作为查询条件！</td>
        </tr>
    </table>-->
    <font color = red>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注意：请至少输入以下号码之一：保单号码、投保单号码、划款协议书号。如无法提供上述号码请输入至少三条数据作为查询条件！</font>
    <table  class= common>
        <TR  class= common>
          <TD  class= title>保险合同号 </TD>
          <TD  class= input> <Input class="wid" class= common name=ContNo id=ContNo></TD>
          <TD  class= title> 集体保单号码</TD>
          <TD  class= input> <Input class="wid" class= common name=GrpContNo id=GrpContNo></TD>
          <TD  class= title> 印刷号码 </TD>
          <TD  class= input> <Input class="wid" class= common name=ProposalContNo id=ProposalContNo></TD>
        </TR>
        <TR  class= common>

          <TD  class= title> 代理人编码</TD>
            <TD  class= input> <input class="wid" NAME="AgentCode" VALUE="" MAXLENGTH="10" CLASS="code" elementtype=nacessary verifyorder="1" Aonkeyup="return queryAgent();" onblur="return queryAgent();" ondblclick="return queryAgent();" onclick="return queryAgent();" > </TD>
					<Input class=common type=hidden name=AgentGroup verify="代理人组别|notnull&len<=12" > </TD>
          <TD  class= title> 管理机构 </TD>
          <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=ManageCom id=ManageCom ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class="codename" name="ManageComName" id="ManageComName" readonly></TD>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
        </TR>

        <TR  class= common>
          <TD  class= title> 投保人姓名 </TD>
          <TD  class= input> <Input class="wid" class= common name=AppntName id=AppntName > </TD>
          <TD  class= title> 保单生效日 </TD>
          <TD  class= input><Input class="wid" class= "multiDatePicker" dateFormat="short" name=CValiDate id=CValiDate></TD>
          <TD  class= title> 续期交费方式 </TD>
          <TD  class="input"> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name="PayLocation" id="PayLocation"  ondblClick="showCodeList('PayLocation',[this,PayLocationName],[0,1]);" onClick="showCodeList('PayLocation',[this,PayLocationName],[0,1]);" onkeyup="showCodeListKey('PayLocation',[this,PayLocationName],[0,1]);" ><input class="codename" name="PayLocationName" id="PayLocationName" readonly></TD>
        </TR>

        <TR  class= common>
          <TD  class= title> 被保人客户号 </TD>
          <TD  class= input> <Input class="wid" class= common name=InsuredNo id=InsuredNo > </TD>
          <TD  class= title> 被保人姓名 </TD>
          <TD  class= input> <Input class="wid" class= common name=InsuredName id=InsuredName> </TD>
          <TD  class= title> 客户证件号码 </TD>
          <TD  class= input> <input class="wid" class="common" name="AppntIDNo" id="AppntIDNo" ></TD>
     </TR>
    <TR  class= common>
          <TD class= title>暂收收据号/划款协议书号</TD>
          <TD class= input> <Input class="wid" class= common name=tempfeeno id=tempfeeno >
                    <td class="title">险种代码</td>
                    <td class="input"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="RiskCode" id="RiskCode" verify="险种代码|Code:IYRiskCode" ondblclick="showCodeList('IYRiskCode',[this,RiskCodeName],[0,1])" onclick="showCodeList('IYRiskCode',[this,RiskCodeName],[0,1])" onkeyup="showCodeListKey('IYRiskCode',[this,RiskCodeName],[0,1])"><input type="text" class="codename" name="RiskCodeName" id="RiskCodeName" readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
        </TR>
    </table></div>
          <!--<INPUT VALUE=" 查 询 " class = cssButton TYPE=button onclick="easyQueryClick();">

          <INPUT VALUE=" 返 回 " name=Return class=cssButton TYPE=button STYLE="display:none" onclick="returnParent()">-->
    		<a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>
            <a href="javascript:void(0);" name=Return class="button" STYLE="display:none" onClick="returnParent();">返    回</a>

   <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomer1);">
            </td>
            <td class= titleImg>
                 客户信息
            </td>
        </tr>
    </table>
     <Div id= "divCustomer1" style= "display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanCustomerGrid"></span></td>
            </tr>
        </table>
            <div id="divTurnPageCustomerGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPageCustomerGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPageCustomerGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPageCustomerGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPageCustomerGrid.lastPage()">
            </div>
    </div>
    
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
            </td>
            <td class= titleImg>
                 保单信息
            </td>
        </tr>
    </table>
    <Div id= "divLCPol1" style= "display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanPolGrid"></span></td>
            </tr>
        </table>
            <div id="divTurnPagePolGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPagePolGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPagePolGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPagePolGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPagePolGrid.lastPage()">
            </div>
    </div>
    <p>
     <!--<INPUT VALUE=" 保单明细 " class = cssButton TYPE=button onclick="PolClick();">-->
     <a href="javascript:void(0);" class="button" onClick="PolClick();">保单明细</a>
     </p>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
