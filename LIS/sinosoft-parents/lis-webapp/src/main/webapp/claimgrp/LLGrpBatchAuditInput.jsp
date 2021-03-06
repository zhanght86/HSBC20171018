<%
//**************************************************************************************************
//Name：LLGrpBatchAuditInput.jsp
//Function：团险批量案件处理页面
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
  String tIsNew = request.getParameter("isNew");

  String tMissionID = request.getParameter("MissionID");  //工作流任务ID
  String tSubMissionID = request.getParameter("SubMissionID");  //工作流子任务ID  
  
//=======================END========================
%>


<script type="text/JavaScript">
</script>
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="LLGrpBatchAudit.js"></SCRIPT>
  <%@include file="LLGrpBatchAuditInit.jsp"%>
</head>
<body onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">
  <!--立案信息-->
<!--   <table>
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
<!--       <Input class= "readonly" type="hidden" name=AccNo>
  <TD  class= title> 赔案号 </TD>
  <TD  class= input> <Input class="readonly" readonly name=RptNo ></TD>
  <TD  class= title> 投保人数 </TD>
  <TD  class= input> <Input class= "readonly" readonly  name=Peoples ></TD>
  <TD  class= title> 申请人数 </TD>
  <TD  class= input> <Input class= "readonly" readonly name=PeopleNo ></TD>
  </TR>
  </table>
  </Div> -->
    <Div  id= "divLLReport1" style= "display: ''" class="maxbox">
  <table  class= common>
     
     <TR  class= common>
      <TD  class= title> 立案号 </TD>
      <TD  class= input> <Input class="common wid" readonly  name=RptNo id=RptNo ></TD>
      <TD class= title> 团体客户号 </TD>
      <TD class= input> <Input class="common wid"  name=GrpCustomerNo id=GrpCustomerNo onkeydown="QueryOnKeyDown(3);"><font size=1 color='#ff0000'><b>*</b></font></TD>
      <TD class= title> 团体保单号 </TD>
      <TD class= input> <Input class="common wid"   name=GrpContNo id=GrpContNo onkeydown="QueryOnKeyDown(3);"><font size=1 color='#ff0000'><b>*</b></font></TD>
    </TR>

    <TR  class= common>
    <TD class= title> 单位名称 </TD>
    <TD class= input> <Input class="common wid"  name=GrpName id=GrpName onkeydown="QueryOnKeyDown(3);"><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD  class= title> 立案日期 </TD>
    <TD  class= input> <input class="common wid" readonly name="RptDate" id="RptDate" ></TD>
    <TD class= title> 投保总人数 </TD>
    <TD class= input> <Input class="readonly wid" readonly name=Peoples id=Peoples></TD>
    </TR>
    
    <TR  class= common>
    <TD  class= title> 申请人姓名 </TD>
    <TD  class= input> <Input class="wid" class= common name=RptorName id=RptorName ><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD  class= title> 申请人电话 </TD>
    <TD  class= input> <Input class="wid" class= common name=RptorPhone id=RptorPhone ></TD>
    <TD class= title> 险种编码 </TD>
    <TD class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=Polno id=Polno ondblclick="return showCodeList('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');" onclick="return showCodeList('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');" onkeyup="return showCodeListKey('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');"></TD>
    </TR>

    <TR  class= common>
    <TD  class= title> 申请人通讯地址 </TD>
    <TD  class= input> <Input class="wid" class= common name=RptorAddress id=RptorAddress ></TD>
    <TD  class= title> 申请人与事故人关系 </TD>
    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Relation id=Relation ondblclick="return showCodeList('relation',[this,RelationName],[0,1]);" onclick="return showCodeList('relation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD  class= title> 出险地点 </TD>
    <TD  class= input> <Input class="wid" class= common name=AccidentSite id=AccidentSite ></TD>
    </TR>

    <TR  class= common>
    <TD class= title> 出险原因</TD>
    <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class=codename name=ReasonName id=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD  class= title> 受托人代码 </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneeCode id=AssigneeCode onblur="queryAgent();"></TD>
    <TD  class= title> 受托人姓名 </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneeName id=AssigneeName ></TD>
    </TR>

    <TR  class= common>
    <TD  class= title> 受托人性别 </TD>
    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno name=AssigneeSex id=AssigneeSex ondblclick="return showCodeList('sex',[this,AssigneeSexName],[0,1]);" onclick="return showCodeList('sex',[this,AssigneeSexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,AssigneeSexName],[0,1]);"><input class=codename name=AssigneeSexName id=AssigneeSexName readonly=true></TD>
    <TD  class= title> 受托人电话 </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneePhone id=AssigneePhone ></TD>
    <TD  class= title> 受托人地址 </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneeAddr id=AssigneeAddr  ></TD>
<!--     <TD  class= title> 申请类型</TD>-->
 <Input class= code type=hidden name=RgtClass value="2"><input class=codename type=hidden name=RgtClassName readonly=true>
    </TR>

    <TR class= common>
    <TD  class= title> 受托人类型 </TD>
    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno name=AssigneeType id=AssigneeType CodeData="0|^0|业务员^1|客户" ondblclick="return showCodeListEx('AssigneeType', [this,AssigneeTypeName],[0,1])"  onclick="return showCodeListEx('AssigneeType', [this,AssigneeTypeName],[0,1])" onkeyup="return showCodeListKeyEx('AssigneeType', [this,AssigneeTypeName],[0,1])"><Input class=codename name=AssigneeTypeName id=AssigneeTypeName readonly=true></TD>
    <TD  class= title> 受托人邮编 </TD>
    <TD  class= input> <Input class="wid" class= common name=AssigneeZip id=AssigneeZip ></TD>
    <TD class= title>  受理日期</TD>
    <TD class= input>  <input class="wid" class="readonly" readonly  name="AcceptedDate" id="AcceptedDate";"><font size=1 color='#ff0000'><b>*</b></font></TD>
    </TR>
    <TR class= common>
<!--     <TD  class= title> 事件号 </TD> -->
      <Input class="wid" class= "readonly" type="hidden" name=AccNo id=AccNo>
    </TR>

    <TR  class= common>
    <TD  class= title> 管辖机构 </TD>
    <TD  class= input> <Input class="wid" class= "readonly" readonly name=MngCom></TD>
    <TD  class= title> 立案受理人 </TD>
    <TD  class= input> <Input class="wid" class="readonly" readonly name=OOperator ></TD>
      <TD class= title> 团体预估金额</TD>
      <TD class= input> <Input class="wid" class="readonly"  readonly name=Grpstandpay></TD>
    </TR>

  </table>
  </Div>
  <!--出险人信息-->
  <table class= common>
  <tr class= common>
  <td text-align: left colSpan=1><span id="spanSubReportGrid" ></span></td>
  </tr>
  </table>
        <table align = center>
            <tr>
                <td><INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage3.firstPage();"></td>
                <td><INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage3.previousPage();"></td>
                <td><INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage3.nextPage();"></td>
                <td><INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage3.lastPage();"></td>
            </tr>
        </table>        
  <table>
  <tr>
  <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
  <td class=titleImg> 客户信息 </td>
  </tr>
  </table>
  <Div id= "divLLSubReport" style= "display: ''" class="maxbox1">
  
  <!--出险人信息表单-->
  <span id="spanSubReport" >
  <table class= common>
  <TR class= common>
    <!--出险人编码,见隐藏区-->
    <TD class= title> 客户姓名 </TD>
    <TD class= input> <Input class="readonly wid" readonly name=customerName id=customerName></TD>
    <TD class= title> 客户年龄 </TD>
    <TD class= input> <Input class="readonly wid" readonly name=customerAge id=customerAge></TD>
    <TD class= title> 客户性别 </TD>
    <TD class= input> <Input type=hidden name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class="wid" class=readonly readonly name=SexName id=SexName readonly=true></TD>
  </TR>
  <TR class= common>
    <TD class= title> 证件类型</TD>
    <TD class= input> <Input class="readonly wid" readonly name=IDTypeName id=IDTypeName></TD>
    <Input class="readonly" type = 'hidden' readonly name=IDType id=IDType>
    <TD class= title> 证件号码</TD>
    <TD class= input> <Input class="readonly wid" readonly name=IDNo id=IDNo></TD>
      <TD class= title> 出险日期</TD>
      <TD class= input>  <!--<input class="coolDatePicker" dateFormat="short" name="AccidentDate" onclick="checkapplydate()"><font size=1 color='#ff0000'><b>*</b></font>-->
      <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" onclick="checkapplydate()" verify="有效开始日期|DATE" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD>
  </TR>
  </table>

  <table class= common>
  <TR class= common>
    <TD class= title> 出险类型：<font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD align=left>
		<input style="vertical-align:middle" type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> <span style="vertical-align:middle">身故</span> </input>
        <input style="vertical-align:middle" type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"><span style="vertical-align:middle"> 高残</span> </input>
        <input style="vertical-align:middle" type="checkbox" value="04" name="claimType" onclick="callClaimType('04');"> <span style="vertical-align:middle">重大疾病</span> </input>
        <input style="vertical-align:middle" type="checkbox" value="01" name="claimType" onclick="callClaimType('01')"> <span style="vertical-align:middle">残疾、烧伤、骨折、重要器官切除</span> </input>
        <input style="vertical-align:middle" type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> <span style="vertical-align:middle">豁免</span> </input>
        <input style="vertical-align:middle" type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> <span style="vertical-align:middle">医疗</span> </input>
        <input style="vertical-align:middle" type="checkbox" value="05" name="claimType" onclick="callClaimType('05')"> <span style="vertical-align:middle">特种疾病</span> </input>
    </td>
  </TR>
  </table></div>
<table>
  <tr>
  <td><input class=cssButton name=QueryPerson value="客户查询" type=button onclick="showInsPerQuery()" ></td>
  <td><input class=cssButton name=addbutton     VALUE="保  存"  TYPE=button onclick="saveClick()" ></td>
  <td><input class=cssButton name=updatebutton  VALUE="修  改"  TYPE=button onclick="updateClick()" ></td>
  <td><input class=cssButton name=deletebutton  VALUE="删  除" TYPE=button onclick="deleteClick()" ></td>
  <td><input class=cssButton name=QueryCont2 VALUE=" 保单查询 "  TYPE=button onclick="showInsuredLCPol()"></td>
  <td><input class=cssButton name=QueryCont3 VALUE="既往赔案查询" TYPE=button onclick="showOldInsuredCase()"></td>
  <td><input class=cssButton name=QueryCont5 VALUE="既往赔案导出" TYPE=button onclick="getLastCaseInfo()"></td>
  <td><input class=cssButton name=DiskOutput VALUE="报案信息导出" TYPE=button onclick="PrintReportClass();"></td>
  <td><input class=cssButton name=DiskInput VALUE="出险人信息导入" TYPE=button onclick="getin()">
  <td><input class=cssButton type=button name=ColQueryImage VALUE="影像查询"  TYPE=button onclick="colQueryImage();"></td>
  </td>
  </tr>
  </table>
<!--  门诊/住院 --> 
	<table>
             <tr>
                <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);"></td>
                <td class= titleImg>门诊/住院录入信息</td>
             </tr>
    </table>
    <Div  id= "divPayPlan1" style= "display: ''">
  <table class= common>
  <tr class= common>
  <td text-align: left colSpan=1><span id="spanMedFeeInHosInpGrid" ></span></td>
  </tr>
  <!-- </table>
        <table >
            <tr>
                <td><INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage2.firstPage();"></td>
                <td><INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();"></td>
                <td><INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();"></td>
                <td><INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage2.lastPage();"></td>
            </tr>
        </table>    -->     
  </table>
  </div>

        <table>
             <tr>
                <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,peer);"></td>
                <td class= titleImg>比例给付录入信息</td>
             </tr>
        </table>
        <Div  id= "peer" style= "display: ''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanMedFeeCaseInfoGrid"></span></td>
                </tr>
            </Table></div>
  <!--<tr>
  <td><input class=cssButton name=addbutton2  VALUE="保  存"  TYPE=button onclick="StandPaySave()" ></td>
  <td><input class=cssButton name=updateFeebutton  VALUE="修 改"  TYPE=button onclick="StandPaySave()" ></td>
  <td><input class=cssButton name=deleteFeebutton  VALUE="删 除"  TYPE=button onclick="deleteFeeClick()" ></td>
  </tr>-->
  </table>
<!--其他信息-->
        <table>
             <tr>
                <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,air);"></td>
                <td class= titleImg>特种费用</td>
             </tr>
        </table><Div  id= "air" style= "display: ''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanMedFeeOtherGrid"></span></td>
                </tr>
            </Table></div>
<!--社保第三方给付-->
        <table>
             <tr>
                <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,wer);"></td>
                <td class= titleImg>社保第三方给付</td>
             </tr>
        </table><Div  id= "wer" style= "display: ''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanMedFeeThreeGrid"></span></td>
                </tr>
            </Table></div>

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
  
  <table>
  <tr>
  	<!--
  <td><input class=cssButton name="QueryReport" value="预估金额" type=button onclick="operStandPayInfo()"></td>
  -->
  <td><input class=cssButton VALUE="账户资金调整" TYPE=button name="LCInsureAcc"  disabled onclick="ctrllcinsureacc();"></td>
  <td><INPUT class=cssButton name="MedicalFeeInp" VALUE="医疗单证录入"  TYPE=button onclick="showLLMedicalFeeInp();"></td>    
  <td><INPUT class=cssButton name="dutySet" VALUE=" 匹配并理算 "  TYPE=button onclick="showMatchDutyPay();"></td>
  <td><input class=cssButton name="QuerydutySet" value="理算查询" type=button onclick="QuerydutySetInfo()"></td>
  <td><INPUT class=cssButton name="BeginInq" VALUE="发起调查"  TYPE=button onclick="showBeginInq()"></td>
  <td><INPUT class=cssButton name="ViewInvest" VALUE="查看调查"  TYPE=button onclick="showQueryInq()"></td>
  <td><INPUT class=cssButton name="SubmitReport" VALUE="发起呈报"  TYPE=button onclick="showBeginSubmit()"></td>
  <td><INPUT class=cssButton name="ViewReport" VALUE=" 查看呈报 " TYPE=button onclick="showQuerySubmit()"></td>
  <td><INPUT class=cssButton name="CreateNote" VALUE="受益人分配" TYPE=button onclick="showBnf()"></td>
  <!--<td><INPUT class=cssButton name="ScanInfo" VALUE="扫描件信息"  TYPE=button onclick="showScanInfo();" ></td>  
  <td><INPUT class=cssButton name="AddAffix" VALUE="案件情况上载"  TYPE=button onclick="AddAffixClick();" ></td>
  <td><INPUT class=cssButton name="LoadAffix" VALUE="案件情况浏览"  TYPE=button onclick="LoadAffixClick();" ></td>
  -->
  </tr>
  </table>
  

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

    <!--按照赔案理赔类型计算信息-->
    <table>
     <tr>
      <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,DutyKind);"></td>
      <td class= titleImg>理赔类型计算信息</td>
     </tr>
    </table>
    
    <Div  id= "DutyKind" style= "display:''">
     <Table  class= common>
      <tr><td text-align: left colSpan=1>
         <span id="spanDutyKindGrid"></span>
      </td></tr>
     </Table>
    </div>
    
    <!--按照保单理赔类型计算信息-->
        	<table>
        	     <tr>
        	        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,PolDutyKind);"></td>
        	        <td class= titleImg>保单计算信息</td>
        	     </tr>
        	</table>
        	<Div  id= "PolDutyKind" style= "display:''">
        	     <Table  class= common>
        	          <tr><td text-align: left colSpan=1>
        	               <span id="spanPolDutyKindGrid"></span>
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
        
   <!--保全匹配信息-->   
     <table>
     <tr>
      <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,PolDutyCode);"></td>
      <td class= titleImg>保全项目信息</td>
     </tr>
    </table>

    <Div  id= "LPEdorItem" style= "display:''">
    <Table  class= common>
      <tr>
      <td text-align: left colSpan=1><span id="spanLPEdorItemGrid"></span></td>
      </tr>
    </Table>
    </div> 

  <div id= "divBaseUnit5" style= "display: none">
  <table>
   <tr>
  <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit4);"></td>
  <td class= titleImg>保项赔付结论</td>
   </tr>
  </table>
  <Div  id= "divBaseUnit4" style= "display:''" class="maxbox">
  <table class= common>
  <TR class= common>
    <TD  class= title>赔付结论</TD>
    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=GiveType id=GiveType  ondblclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llpayconclusion',[this,GiveTypeName],[0,1]);" onfocus= "choiseGiveTypeType();"><input class=codename name=GiveTypeName id=GiveTypeName readonly=true>
    <TD  class= title></TD>
    <TD  class= input><Input class="wid" class=readonly readonly ></TD>
    <TD  class= title></TD>
    <TD  class= input></TD>
  </tr>
  </table>
  <Div  id= "divGiveTypeUnit1" style= "display: ''">
  <table class= common>
    <TR class= common>
    <TD  class= title>调整金额</TD>
<!--     <TD  class= input><Input class=common name=RealPay onblur="checkAdjMoney();"></TD> -->
    <TD  class= input><Input class="wid" class=common name=RealPay id=RealPay ></TD>
    <TD  class= title>调整原因</TD>
    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AdjReason id=AdjReason ondblclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1]);" onclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1]);" onkeyup="return showCodeListKey('lldutyadjreason',[this,AdjReasonName],[0,1]);"><input class=codename name=AdjReasonName id=AdjReasonName readonly=true>
    <TD  class= title>调整保单状态</TD>
      <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=polstate id=polstate ondblclick="return showCodeList('newpolstate',[this,polstateName],[0,1],null,null,null,null,120);" onclick="return showCodeList('newpolstate',[this,polstateName],[0,1],null,null,null,null,120);" onkeyup="return showCodeListKey('newpolstate',[this,polstateName],[0,1]);"><input class=codename name=polstateName id=polstateName readonly=true></td>
    </tr>
  </table>
  <Div  id= "divGiveTypeUnit3" style= "display: none">
      <table class= common>
      
      <TD  class= title></TD>
      <TD  class= input></TD>
      <TD  class= title></TD>
      <TD  class= input></TD>            
      </table>
  </Div>
  <table class= common>
    <TR class= common>
    <TD class= title> 调整备注 </TD>
    </tr>
    <TR class= common>
    <TD colspan="6" style="padding-left:16px"> <textarea name="AdjRemark" cols="197" rows="4" witdh=25% class="common"></textarea></TD>
    </tr>
   </table>
  </div>
  <Div  id= "divGiveTypeUnit2" style= "display: none">
  <table class= common>
    <TR class= common>
    <TD  class= title>拒付原因</TD>
    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=GiveReason id=GiveReason ondblclick="return showCodeList('llprotestreason',[this,GiveReasonName],[0,1]);" onclick="return showCodeList('llprotestreason',[this,GiveReasonName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,GiveReasonName],[0,1]);"><input class=codename name=GiveReasonName readonly=true></td>
    <TD  class= title>拒付依据</TD>
    <TD  class= input><Input class="wid" class=common name=GiveTypeDesc id=GiveTypeDesc></td>
    <TD  class= title></TD>
    <TD  class= input></TD>      
    </tr>
  </table>
  <table class= common>
    <TR class= common >
    <TD class= title> 特殊备注 </TD>
    </tr>
    <TR class= common>
    <TD colspan="6" style="padding-left:16px"> <textarea name="SpecialRemark" cols="197" rows="4" witdh=25% class="common"></textarea></TD>
    </tr>
  </table>
  </div>
  <table>
   <tr>
     <td><INPUT class=cssButton name="addUpdate" VALUE=" 添加修改 "  TYPE=button onclick="AddUpdate()" ></td>
     <td><INPUT class=cssButton name="saveUpdate" VALUE=" 保存修改"  TYPE=button onclick="SaveUpdate();" ></td>
   </tr>
  </table>
  </div>
  </div>
  

  <Div  id= "divSimpleClaim2" style= "display:''">
  <Div  id= "divSimpleClaim" style= "display:''">
  <Input class=codeno type = "hidden" name=SimpleConclusion value = "1">
  </div>
  <INPUT class=cssButton name="simpleClaim" VALUE="批量案件确认"  TYPE=button onclick="confirmClick()">
  <INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()">
  </div>

  <!--简易案件复核信息-->
  <Div  id= "divSimpleClaim3" style= "display:''">
  <table>
   <tr>
  <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSimpleClaim);"></td>
   <td class= titleImg>批量案件复核信息</td>
   </tr>
  </table>
<Div id= "divLLAudit" style= "display: 'none'" class="maxbox1">
    <table class= common>
   <TR class= common>
     <TD class= title>审核意见</TD>
   </tr>
   <TR class= common>
     <TD colspan="6" style="padding-left:16px"> <textarea name="AuditIdea" cols="197" rows="4" witdh=25% class="common"></textarea></TD>
   </tr>
  </table>
</div>
  <Div  id= "divSimpleClaim" style= "display:''">
  <table class= common>
  <TR class= common>
  <TD  class= title>批量案件审核结论</TD>
  <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=SimpleConclusion2 id=SimpleConclusion2 ondblClick="showCodeList('llexamconclusion2',[this,SimpleConclusion2Name],[0,1],null,'1 and code <> #0#',1,1,'150');" onClick="showCodeList('llexamconclusion2',[this,SimpleConclusion2Name],[0,1],null,'1 and code <> #0#',1,1,'150');" onkeyup="showCodeListKeyEx('llexamconclusion2',[this,SimpleConclusion2Name],[0,1],null,'1 and code <> #0#',1,1,'150');"><input class=codename name=SimpleConclusion2Name id=SimpleConclusion2Name readonly><font size=1 color='#ff0000'><b>*</b></font></TD>
  <TD  class= title>特殊备注</TD>
  <TD  class= input><Input class="wid" class=common name=SpecialRemark1 id=SpecialRemark1></TD>
  <TD class=title><!--拒付原因--></TD>
		<TD class=input><!--<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ProtestReason id=ProtestReason
			ondblclick="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);" onclick="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);"
			onkeyup="return showCodeListKey('llprotestreason',[this,ProtestReasonName],[0,1]);"><input
			class=codename name=ProtestReasonName id=ProtestReasonName readonly=true>--></TD>
  </tr>
  </table>
  </div>
<Div id="divLLAudit2" style="display: none">
<table class=common>
	<TR class=common>
		
		<TD class=title>拒付依据</TD>
		<TD class=input><Input class="wid" class=common name=ProtestReasonDesc id=ProtestReasonDesc></TD>
        <TD class=title></TD><TD class=input></TD>
        <TD class=title></TD><TD class=input></TD>
	</tr>
</table>
</div>
  

  
  
   
  <!--<INPUT class=cssButton name="ConclusionUp" VALUE="超权限上报"  TYPE=button onclick="ConclusionUpClick()">-->
    <INPUT class=cssButton name="simpleClaim2" VALUE="结论保存"  TYPE=button onclick="confirmClick2()">
  <INPUT class=cssButton name="ConclusionSave" VALUE="审核确认"  TYPE=button onclick="ConclusionSaveClick()">
  <!--<INPUT class=cssButton name="ConclusionBack" VALUE="回退确认"  TYPE=button onclick="AuditConfirmClick()">
  --><INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()">
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
  <input type=hidden id="RgtClass" name="RgtClass" value ="2"><!--团险个险代码-->
  
  <input type=hidden id="BudgetFlag" name="BudgetFlag"><!--是否预付-->
  <Input type=hidden id="customerNo" name="customerNo" ><!-- 出险人编码 -->
  <input type=hidden id="AuditPopedom" name="AuditPopedom"><!--审核权限-->
  <input type=hidden id="clmState2" name="clmState2"><!--案件状态-->
  <input type=hidden id="Flag" name="Flag"><!--返回页面判断-->
  
  <input type=hidden id="RgtNo" name="RgtNo">
  <input type=hidden id="tPolNo" name= "tPolNo">
  <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
  <input type=hidden id="MissionID"  name= "MissionID">
  <input type=hidden id="SubMissionID" name= "SubMissionID">
  <input type=hidden id="tSQL" name= "tSQL">
  <input type=hidden class=common  name="AccRiskCode" > 
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
