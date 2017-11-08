<%
//**************************************************************************************************
//Name：LLReportInput.jsp
//Function：报案登记
//Author：zl
//Date: 2005-6-9 15:31
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
  String CurrentDate = PubFun.getCurrentDate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");

  String tClmNo = request.getParameter("claimNo");  //赔案号
  String tIsNew = request.getParameter("isNew");  //创建人
  
  String tMissionID = request.getParameter("MissionID");  //工作流任务ID
  String tSubMissionID = request.getParameter("SubMissionID");  //工作流子任务ID  
//=======================END========================
%>
<head>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
  <SCRIPT src="LLReport.js"></SCRIPT>
  <%@include file="LLReportInit.jsp"%>
</head>

<body  onload="initForm()" >

<form method=post name=fm target="fraSubmit">
  <!--报案信息-->
  <table>
   <TR>
    <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
    <TD class= titleImg>报案信息</TD>
   </TR>
  </table>

  <Div id= "divLLReport1" style= "display: ''">
   <table class= common>
    <TR class= common>
     <TD class= title> 事件号 </TD>
     <TD class= input> <Input class= "readonly" readonly name=AccNo></TD>
     <TD class= title> 赔案号 </TD>
     <TD class= input> <Input class="readonly" readonly  name=RptNo ></TD>
     <TD class= title> 报案人姓名 </TD>
     <TD class= input> <Input class= common name=RptorName ><font size=1 color='#ff0000'><b>*</b></font></TD>
    </TR>
    <TR class= common>
     <TD class= title> 报案人电话 </TD>
     <TD class= input> <Input class= common name=RptorPhone ></TD>
     <TD class= title> 报案人通讯地址 </TD>
     <TD class= input> <Input class= common name=RptorAddress ></TD>
     <TD class= title> 报案人与出险人关系 </TD>
     <TD class= input> <Input class=codeno name=Relation ondblclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class=codename name=RelationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
    </TR>
    <TR class= common>
     <TD class= title> 报案方式 </TD>
     <TD class= input> <Input class=codeno name="RptMode" value="01" ondblclick="return showCodeList('llrptmode',[this,RptModeName],[0,1]);" onkeyup="return showCodeListKey('llrptmode',[this,RptModeName],[0,1]);"><input class=codename name="RptModeName" value="电话报案" readonly=true></TD>
     <TD class= title> 出险地点 </TD>
     <TD class= input> <Input class= common name=AccidentSite ></TD>
     <TD class= title> 报案日期 </TD>
    <TD class= input>  <input class="readonly" readonly name="RptDate" ></TD>
    </TR>
    <TR class= common>
     <TD class= title> 管辖机构 </TD>
     <TD class= input> <Input class= "readonly" readonly name=MngCom></TD>
     <TD class= title> 报案受理人 </TD>
     <TD class= input> <Input class="readonly" readonly name=Operator ></TD>
    <TD class= title> 申请类型</TD>
     <TD class= input> <Input class= codeno value="1" name=RgtClass ondblclick="return showCodeList('llrgtclass',[this,RgtClassName],[0,1]);" onkeyup="return showCodeListKey('llrgtclass',[this,RgtClassName],[0,1]);"><input class=codename name=RgtClassName readonly=true value="个人"></TD>
     <!--TD class= title> 理赔状态 </TD>
     <TD class= input> <Input class= "readonly" readonly name=CaseState></TD-->
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
     <td><input class=cssButton type=hidden name=QueryReport value="事件查询" type=button onclick="queryLLAccident()"></td>
     <td>
    <span id="operateButton2" style= "display: none"><input class=cssButton name=addbutton  VALUE="保  存"  TYPE=button onclick="saveClick()" ></span>
    <span id="operateButton3" style= "display: none"><input class=cssButton name=updatebutton  VALUE="修  改"  TYPE=button onclick="updateClick()" ></span>
     </td>
     <!--td><input class=cssButton name=QueryCont1 VALUE="投保单查询"  TYPE=button onclick=""></td-->
     <td><input class=cssButton name=QueryCont2 VALUE="保单查询"  TYPE=button onclick="showInsuredLCPol()"></td>
     <td><input class=cssButton name=QueryCont3 VALUE="既往赔案查询" TYPE=button onclick="showOldInsuredCase()"></td>
     <td><input class=cssButton type=hidden name=MedCertForPrt VALUE="打印伤残鉴定通知"  TYPE=button onclick="PrintMedCert()"></td>
     <td><input class=cssButton type=hidden name=BarCodePrint VALUE="打印条形码"  TYPE=button onclick="colBarCodePrint();"></td>
     <td><input class=cssButton type=hidden name=ColQueryImage VALUE="影像查询"  TYPE=button onclick="colQueryImage();"></td>
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
    <TD class= title> VIP客户标示</TD>
    <TD class= input> <Input class="readonly" readonly name=IsVip></TD>
    <TD class= title> 事故日期</TD>
    <TD class= input>  <input class="coolDatePicker" dateFormat="short" name="AccidentDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD class= title> 出险原因</TD>
    <TD class= input><Input class=codeno name=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class=codename name=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
     </TR>
     <TR class= common>
    <TD class= title> 治疗医院</TD>
    <TD class= input> <Input class=codeno name=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName readonly=true></TD>
    <TD class= title> 出险日期 </TD>
    <TD class= input>  <input class="coolDatePicker" dateFormat="short" name="AccidentDate2" ><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD class= title> 出险细节</TD>
    <TD class= input> <Input class=codeno name=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');"><input class=codename name=accidentDetailName readonly=true></TD>
     </TR>
     <TR class= common>
    <TD class= title> 治疗情况</TD>
    <TD class= input> <Input class=codeno name=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName readonly=true></TD>
    <TD class= title> 出险结果1</TD>
    <TD class= input> <Input class=codeno name=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onFocus="saveIcdValue();"><input class=codename name=AccResult1Name readonly=true></TD>
    <TD class= title> 出险结果2</TD>
    <TD class= input> <Input class=codeno name=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'ICDCode','1','300');"><input class=codename name=AccResult2Name readonly=true></TD>
     </TR>
     <!--TR class= common>
    <TD class= title> 死亡标识</TD>
    <TD class= input> <Input class=codeno name=IsDead ondblclick="return showCodeList('llDieFlag',[this,IsDeadName],[0,1]);" onkeyup="return showCodeListKey('llDieFlag',[this,IsDeadName],[0,1]);"><input class=codename name=IsDeadName readonly=true></TD>
     </tr-->
    </table>

    <table class= common>
     <TR class= common>
    <TD class= title> 理赔类型 <font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD align=left>
      <input type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> 身故 </input>
<!--       <input type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> 高残 </input> -->
      <input type="checkbox" value="04" name="claimType"> 重大疾病 </input>
      <input type="checkbox" value="01" name="claimType"> 伤残 </input>
<!--       <input type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> 豁免 </input> -->
      <input type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> 医疗 </input>
<!--       <input type="checkbox" value="05" name="claimType" > 特种疾病 </input> -->
      <input type="checkbox" value="06" name="claimType" > 失业失能 </input>
    </td>
     </TR>
    </table>
    <table class= common>
     <TR  class= common>
    <TD  class= title> 事故描述 </TD>
     </tr>
     <TR class= common>
    <TD class= input> 
      <span id="spanText3" style= "display: ''">
       <textarea name="AccDesc" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>
      <!--隐藏表单作为显示数据用-->
      <span id="spanText4" style= "display: 'none'">
       <textarea disabled name="AccDescDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>
    </TD>
     </tr>
     <TR class= common>
    <TD class= title> 备注 </TD>
     </tr>
     <TR class= common>
    <TD class= input>
      <span id="spanText1" style= "display: ''">
       <textarea name="Remark" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>
      <!--隐藏表单作为显示数据用-->
      <span id="spanText2" style= "display: 'none'">
       <textarea disabled name="RemarkDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>
    </TD>
     </tr>
    </table>
   </span>
  </div>
  <hr>
  <span id="operateButton4" >
   <table>
    <tr>
     <td><INPUT class=cssButton name="DoHangUp" VALUE="保单挂起"  TYPE=button onclick="showLcContSuspend()" ></td>
     <td><INPUT class=cssButton name="CreateNote" VALUE="生成单证"  TYPE=button onclick="showAffix()"></td>
     <td><INPUT class=cssButton name="CreateNote" VALUE="打印单证"  TYPE=button onclick="showPrtAffix()"></td>     
     <td><INPUT class=cssButton name="BeginInq" VALUE="发起调查"  TYPE=button onclick="showBeginInq()"></td>
     <td><INPUT class=cssButton name="ViewInvest" VALUE="查看调查"  TYPE=button onclick="showQueryInq()"></td>
     <td><INPUT class=cssButton type=hidden name="Condole" VALUE="提起慰问"  TYPE=button onclick="showCondole()"></td>
     <td><INPUT class=cssButton type=hidden name="SubmitReport" VALUE="发起呈报"  TYPE=button onclick="showBeginSubmit()"></td>
     <td><INPUT class=cssButton type=hidden name="ViewReport" VALUE="查看呈报"  TYPE=button onclick="showQuerySubmit()"></td>
     <td><INPUT class=cssButton name="AccidentDesc" VALUE="备注信息"  TYPE=button onclick="showClaimDesc()"></td>
    </tr>
   </table>
  </span>
  <hr>
  <table>
   <tr>
    <td><INPUT class=cssButton name="RptConfirm" VALUE="报案确认"  TYPE=button onclick="reportConfirm()"></td>
    <td><INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()"></td>
   </tr>
  </table>
  <%
  //隐藏区,保存信息用
  %>
  <Input type=hidden id="customerNo" name="customerNo"><!--出险人代码-->
  <input type=hidden id="isReportExist" name="isReportExist"><!--是否为新增事件,不判断则为空-->
  <input type=hidden id="fmtransact" name="fmtransact"><!--操作代码‘insert,delete’等-->

  <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
  <input type=hidden id="isNew" name="isNew"><!--是否创建新建-->
  <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10代码-->
  
  <!--##########打印流水号，在打印检测时根据  赔案号、单证类型（代码）查询得到######-->
  <input type=hidden id="PrtSeq" name="PrtSeq">
  
  <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
  <input type=hidden id="MissionID"  name= "MissionID">
  <input type=hidden id="SubMissionID" name= "SubMissionID">

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
