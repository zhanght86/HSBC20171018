<%
//**************************************************************************************************
//Name：LLGrpSimpleInput.jsp
//Function：简易案件处理页面
//Author：pd
//Date: 2005-10-21
//Desc: 
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");
  String CurrentDate = PubFun.getCurrentDate();
  String RptNo = request.getParameter("RptNo");
  String Flag = request.getParameter("Flag"); //1简易案件 2简易复核
//=======================END========================
%>
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="LLGrpSimpleAll.js"></SCRIPT>
  <%@include file="LLGrpSimpleAllInit.jsp"%>
</head>
<body onload="initForm();" >
<form method=post name=fm target="fraSubmit">
  <!--立案信息-->
  <table>
  <TR>
  <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
  <TD class= titleImg>立案信息</TD>
  </TR>
  </table>
  <Div  id= "divLLReport1" style= "display: ''">
  <table  class= common>
    <TR class= common>
    <TD class= title> 团体客户号 </TD>
    <TD class= input> <Input class="common"  name=GrpCustomerNo onkeydown="ClientQuery2();"></TD>
    <TD class= title> 单位名称 </TD>
    <TD class= input> <Input class="common"  name=GrpName onkeydown="ClientQuery2();"></TD>
    <TD class= title> 团体保单号 </TD>
    <TD class= input> <Input class="common"   name=GrpContNo onkeydown="ClientQuery2();"></TD>
    </TR>
  <TR class= common>
    <TD class= title> 出险原因</TD>
    <TD class= input><Input class=codeno  name=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class=codename name=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
    <input class="readonly" type=hidden dateFormat="short" name="AccidentDate2" >
<TD class= title> </TD>
<TD class= input></TD>
    <TD class= title> 险种编码 </TD>
    <TD class= input> <Input class=code name=Polno ondblclick="return showCodeList('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');" onkeyup="return showCodeListKey('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');"></TD>
  </TR>
  <TR  class= common>
<!--     <TD  class= title> 事件号 </TD> -->
      <Input class= "readonly" type="hidden" name=AccNo>
  <TD  class= title> 赔案号 </TD>
  <TD  class= input> <Input class="readonly" readonly name=RptNo ></TD>
  <TD  class= title> 投保人数 </TD>
  <TD  class= input> <Input class= "readonly" readonly  name=Peoples ></TD>
  <TD  class= title> 申请人数 </TD>
  <TD  class= input> <Input class= "readonly" readonly name=PeopleNo ></TD>
  </TR>
  </table>
  </Div>
  <hr>
  <!--出险人信息-->
  <table class= common>
  <tr class= common>
  <td text-align: left colSpan=1><span id="spanSubReportGrid" ></span></td>
  </tr>
  </table>
        <table align = center>
            <tr>
                <td><INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage3.firstPage();"></td>
                <td><INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage3.previousPage();"></td>
                <td><INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage3.nextPage();"></td>
                <td><INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage3.lastPage();"></td>
            </tr>
        </table>        
  <table>
  <tr>
  <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
  <td class=titleImg> 出险人信息 </td>
  </tr>
  </table>
  <Div id= "divLLSubReport" style= "display: ''">
  <table>
  <tr>
  <td><input class=cssButton name=QueryPerson value="出险人查询" type=button onclick="showInsPerQuery()" ></td>
  <td><input class=cssButton name=addbutton  VALUE="新  增"  TYPE=button onclick="saveClick()" ></td>
  <td><input class=cssButton name=updatebutton  VALUE="修  改"  TYPE=button onclick="updateClick()" ></td>
  <td><input class=cssButton name=deletebutton  VALUE="删  除" TYPE=button onclick="deleteClick()" ></td>
  <td><input class=cssButton name=QueryCont2 VALUE=" 保单查询 "  TYPE=button onclick="showInsuredLCPol()"></td>
  <td><input class=cssButton name=QueryCont3 VALUE="既往赔案查询" TYPE=button onclick="showOldInsuredCase()"></td>
  </tr>
  </table>
  <!--出险人信息表单-->
  <span id="spanSubReport" >
  <table class= common>
  <TR class= common>
    <!--出险人编码,见隐藏区-->
    <TD class= title> 出险人姓名 </TD>
    <TD class= input> <Input class="readonly" readonly name=customerName></TD>
    <TD class= title> 出险人年龄 </TD>
    <TD class= input> <Input class="readonly" readonly name=customerAge></TD>
    <TD class= title> 出险人性别 </TD>
    <TD class= input> <Input type=hidden name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=readonly readonly name=SexName readonly=true></TD>
  </TR>
  <TR class= common>
    <TD class= title> 证件类型</TD>
    <TD class= input> <Input class="readonly" readonly name=IDTypeName></TD>
    <Input class="readonly" type = 'hidden' readonly name=IDType>
    <TD class= title> 证件号码</TD>
    <TD class= input> <Input class="readonly" readonly name=IDNo></TD>
      <TD class= title> 出险日期</TD>
      <TD class= input>  <input class="coolDatePicker" dateFormat="short" name="AccidentDate" onclick="checkapplydate()"><font size=1 color='#ff0000'><b>*</b></font></TD>
  </TR>
  </table>

  <table class= common>
  <TR class= common>
    <TD class= title> 出险类型：<font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD align=left>
    <input  type="checkbox" value="02" name="claimType" > 死亡 </input>
    <input  type="checkbox" value="04" name="claimType" > 大病 </input>
    <input  type="checkbox" value="01" name="claimType" > 伤残 </input>
    <input  type="checkbox" value="00" name="claimType" > 医疗 </input>
    <input  type="checkbox" value="06" name="claimType" > 失能 </input>
    </td>
  </TR>
  </table>

<!-- 门诊/住院 -->
  <div id = divLLSubReport2 align = center>
  <table class= common>
  <tr class= common>
  <td text-align: left colSpan=1><span id="spanMedFeeInHosInpGrid" ></span></td>
  </tr>
  </table>
        <table >
            <tr>
                <td><INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage2.firstPage();"></td>
                <td><INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();"></td>
                <td><INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();"></td>
                <td><INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage2.lastPage();"></td>
            </tr>
        </table>        
  </div>
  <table>
  <tr>
  <td><input class=cssButton name=addbutton2  VALUE="保  存"  TYPE=button onclick="StandPaySave()" ></td>
  <td><input class=cssButton name=updateFeebutton  VALUE="修 改"  TYPE=button onclick="StandPaySave()" ></td>
  <td><input class=cssButton name=deleteFeebutton  VALUE="删 除"  TYPE=button onclick="deleteFeeClick()" ></td>
  </tr>
  </table>

  <!--立案结论信息-->
<!--    <table>
   <tr>
  <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit6);"></td>
  <td class= titleImg>立案结论信息</td>
   </tr>
  </table>
  <Div  id= "divBaseUnit6" style= "display:''">
  <table class= common>
  <TR class= common>
  <TD  class= title>立案结论</TD>
  <TD  class= input>
  <Input class=codeno  name=RgtConclusion ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input class=codename name=RgtConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font>
  </TD>
   <TD  class= title> 案件标识</TD>
  <TD  class= input> <Input class=codeno  name=rgtType ondblclick="return showCodeListEx('llrgttype', [this,rgtTypeTypeName],[0,1])"onkeyup="return showCodeListKeyEx('llrgttype', [this,rgtTypeTypeName],[0,1])"><Input class=codename name=rgtTypeTypeName readonly=true></TD>
  <td>&nbsp;&nbsp;</td>
   </tr> 
  </table>
  </div>  -->
  <hr>
  <table>
  <tr>
  <td><input class=cssButton name="QueryReport" value="预估金额" type=button onclick="operStandPayInfo()"></td>
  <td><INPUT class=cssButton name="dutySet" VALUE=" 匹配并理算 "  TYPE=button onclick="showMatchDutyPay();"></td>
  <td><input class=cssButton name="QuerydutySet" value="理算查询" type=button onclick="QuerydutySetInfo()"></td>
  </tr>
  </table>
  <hr>

    <!--整个赔案计算信息-->
    <table>
     <tr>
      <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaimGrid);"></td>
      <td class= titleImg>赔案计算信息</td>
     </tr>
    </table>

    <Div  id= "divClaimGrid" style= "display:''">
     <Table  class= common>
      <tr><td text-align: left colSpan=1>
       <span id="spanClaimGrid"></span>
      </td></tr>
     </Table>
    </div>

        <!--保项匹配信息-->
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit3);"></td>
                <td class= titleImg>保项计算信息</td>
             </tr>
        </table>
        <Div  id= "divBaseUnit3" style= "display:''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanPolDutyCodeGrid"></span></td>
                </tr>
            </Table>
        </div>

  <div id= "divBaseUnit5" style= "display: 'none'">
  <table>
   <tr>
  <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit4);"></td>
  <td class= titleImg>保项赔付结论</td>
   </tr>
  </table>
  <Div  id= "divBaseUnit4" style= "display:''">
  <table class= common>
  <TR class= common>
    <TD  class= title>赔付结论</TD>
    <TD  class= input><Input class=codeno name=GiveType  ondblclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llpayconclusion',[this,GiveTypeName],[0,1]);" onfocus= "choiseGiveTypeType();"><input class=codename name=GiveTypeName readonly=true></font>
    <TD  class= title></TD>
    <TD  class= input><Input class=readonly readonly ></TD>
    <TD  class= title></TD>
    <TD  class= input></TD>
  </tr>
  </table>
  <Div  id= "divGiveTypeUnit1" style= "display: ''">
  <table class= common>
    <TR class= common>
    <TD  class= title>调整金额</TD>
<!--     <TD  class= input><Input class=common name=RealPay onblur="checkAdjMoney();"></TD> -->
    <TD  class= input><Input class=common name=RealPay ></TD>
    <TD  class= title>调整原因</TD>
    <TD  class= input><Input class=codeno name=AdjReason ondblclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1]);" onkeyup="return showCodeListKey('lldutyadjreason',[this,AdjReasonName],[0,1]);"><input class=codename name=AdjReasonName readonly=true></font>
    <TD  class= title></TD>
    <TD  class= input></TD>
    </tr>
  </table>
  <table class= common>
    <TR class= common>
    <TD class= title> 调整备注 </TD>
    </tr>
    <TR class= common>
    <TD class= input> <textarea name="AdjRemark" cols="100" rows="2" witdh=25% class="common"></textarea></TD>
    </tr>
   </table>
  </div>
  <Div  id= "divGiveTypeUnit2" style= "display: 'none'">
  <table class= common>
    <TR class= common>
    <TD  class= title>拒付原因</TD>
    <TD  class= input><Input class=codeno name=GiveTypeDesc ondblclick="return showCodeList('llprotestreason',[this,GiveTypeDescName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,GiveTypeDescName],[0,1]);"><input class=codename name=GiveTypeDescName readonly=true></td>
    <TD  class= title>拒付依据</TD>
    <TD  class= input><Input class=common name=GiveReason></td>
    <TD  class= title></TD>
    <TD  class= input></TD>      
    </tr>
  </table>
  <table class= common>
    <TR class= common >
    <TD class= title> 特殊备注 </TD>
    </tr>
    <TR class= common>
    <TD class= input> <textarea name="SpecialRemark" cols="100" rows="2" witdh=25% class="common"></textarea></TD>
    </tr>
  </table>
  </div>
  <table>
   <tr>
     <td><INPUT class=cssButton name="addUpdate" VALUE=" 添加修改 "  TYPE=button onclick="AddUpdate()" ></td>
     <td><INPUT class=cssButton name="saveUpdate" VALUE=" 保存修改金额 "  TYPE=button onclick="SaveUpdate();" ></td>
   </tr>
  </table>
  </div>
  </div>
  <hr>

  <Div  id= "divSimpleClaim2" style= "display:''">
  <Div  id= "divSimpleClaim" style= "display:''">
  <Input class=codeno type = "hidden" name=SimpleConclusion value = "1">
  </div>
  <INPUT class=cssButton name="simpleClaim" VALUE="简易案件确认"  TYPE=button onclick="confirmClick()">
  <INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()">
  </div>

  <!--简易案件复核信息-->
  <Div  id= "divSimpleClaim3" style= "display:''">
  <table>
   <tr>
  <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSimpleClaim);"></td>
   <td class= titleImg>简易案件复核信息</td>
   </tr>
  </table>
  <Div  id= "divSimpleClaim" style= "display:''">
  <table class= common>
  <TR class= common>
  <TD  class= title>简易案件复核结论</TD>
  <TD  class= input><Input class=codeno name=SimpleConclusion2 ondblClick="showCodeList('llexamconclusion',[this,SimpleConclusion2Name],[0,1]);" onkeyup="showCodeListKeyEx('llexamconclusion',[this,SimpleConclusion2Name],[0,1]);"><input class=codename name=SimpleConclusion2Name readonly><font size=1 color='#ff0000'><b>*</b></font></TD>
  <TD  class= title></TD>
  <TD  class= input></TD>
  <TD  class= title></TD>
  <TD  class= input></TD>
  </tr>
  </table>
  </div>
  <hr>
  <INPUT class=cssButton name="simpleClaim2" VALUE="复核确认"  TYPE=button onclick="confirmClick()">
  <INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()">
  </div>

  <%
  //隐藏区,保存信息用
  %>
  <input type=hidden id="fmtransact" name="fmtransact">
  <input type=hidden name=hideOperate value=''>
  <input type=hidden id="isReportExist" name="isReportExist"><!--是否为新增事件,不判断则为空-->
  <Input type=hidden id="ManageCom" name="ManageCom"><!--登陆区站前两个代码-->
  <Input type=hidden id="AllManageCom" name="AllManageCom"><!--登陆区站-->
  <Input type=hidden id="Operator" name="Operator"><!--登陆用户-->
   <Input type=hidden id="tOperator" name="tOperator"><!--立案用户-->
  <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
  <input type=hidden id="isNew" name="isNew"><!--是否创建新建-->
  <input type=hidden id="clmState" name="clmState"><!--立案状态-->
  <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10代码-->
  <input type=hidden id="RgtClass" name="RgtClass" value = "2"><!--团险个险代码-->
  <input type=hidden id="BudgetFlag" name="BudgetFlag"><!--是否预付-->
  <Input type=hidden id="customerNo" name="customerNo" ><!-- 出险人编码 -->
  <input type=hidden id="AuditPopedom" name="AuditPopedom"><!--审核权限-->
  <input type=hidden id="clmState2" name="clmState2"><!--案件状态-->
  <input type=hidden id="Flag" name="Flag"><!--返回页面判断-->
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
