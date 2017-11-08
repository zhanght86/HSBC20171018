<%
//**************************************************************************************************
//Name：LLGrpReportInput.jsp
//Function：团体报案登记
//Author：zhangzheng
//Date: 2008-10-25
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
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
  String CurrentDate = PubFun.getCurrentDate();
  GlobalInput tG = new GlobalInput();
  tG = (GlobalInput)session.getValue("GI");

  String tClmNo = request.getParameter("claimNo");  //赔案号
  String tIsNew = request.getParameter("isNew");  //创建人
  String tIsClaimState =request.getParameter("isClaimState"); //判断来自理赔状态查询的节点
  String trgtstate =request.getParameter("rgtstate"); //报案类型 
  loggerDebug("LLGrpReportInput","LLGrpReportInput.jsp传入Rgtstate:"+trgtstate);
  
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
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="LLGrpReport.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <%@include file="LLGrpReportInit.jsp"%>
</head>

<body  onload="initForm()" >

<form action="./LLGrpReportSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <!--报案信息-->
  <table>
  <TR>
    <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
    <TD class= titleImg>报案信息</TD>
  </TR>
  </table>

  <Div id= "divLLReport1" style= "display: ''" class="maxbox1">
   
    <TR class= common>
      <TD class= title> 报案类型 <font size=1 color='#ff0000'><b>*</b></font></TD>
      <TD align=left>
      <span id="spanText7" style= "display: ''">
      <input type="radio" value="11" name="rgtstate" onclick="callrgtstate('11');" > 普通案件报案 </input>
      <input type="radio" value="03" name="rgtstate" onclick="callrgtstate('03');" > 批量案件报案 </input>
      <input type="radio" value="02" name="rgtstate" onclick="callrgtstate('02');" > 帐户案件报案 </input>      
      </span> 
    </TR>
  
  </Div> 
  
  <table class= common>    
    <TR class= common>
	    <TD class= title> 团体保单号 </TD>
	    <TD class= input> <Input class="wid" class=common  name=GrpContNo id=GrpContNo onkeyup="queryCustomerIn()" ><font size=1 color='#ff0000'><b>*</b></font></TD>
	    <TD class= title> 团体客户号 </TD>
	    <TD class= input> <Input class="wid" class=common  name=GrpCustomerNo id=GrpCustomerNo ><font size=1 color='#ff0000'><b>*</b></font></TD>   
	    <TD class= title> 单位名称 </TD>
	    <TD class= input> <Input class="wid" class=common  name=GrpName id=GrpName ><font size=1 color='#ff0000'><b>*</b></font></TD>
    </TR>
  </table> 
        
    <table class= common>
    <TR class= common>
	<TD class= title> 报案人与出险人关系 </TD>
    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Relation id=Relation onfocus= "getRptorInfo()" ondblclick="return showCodeList('relation',[this,RelationName],[0,1],null,null,null,1);"onclick="return showCodeList('relation',[this,RelationName],[0,1],null,null,null,1);"  onkeyup="return showCodeListKey('relation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD class= title> 报案人姓名 </TD>
    <TD class= input> <Input class="wid" class= common name=RptorName id=RptorName ><font size=1 color='#ff0000'><b>*</b></font></TD>
    <TD class= title> 报案人电话 </TD>
    <TD class= input> <Input class="wid" class= common name=RptorPhone id=RptorPhone ></TD>  
    </TR>     
    </table>
    
   <Div id="divreport1" style="display:''"> 
    <table class= common>    
    <TR class= common>
    <TD class= title> 报案人通讯地址 </TD>
    <TD class= input> <Input class="wid" class= common name=RptorAddress id=RptorAddress ></TD>    
    <TD class= title> 报案方式 </TD>
    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="RptMode" id="RptMode" value="01" ondblclick="return showCodeList('llrptmode',[this,RptModeName],[0,1]);" onclick="return showCodeList('llrptmode',[this,RptModeName],[0,1]);" onkeyup="return showCodeListKey('llrptmode',[this,RptModeName],[0,1]);"><input class=codename name="RptModeName" id="RptModeName" value="电话报案" readonly=true></TD>
    <TD class= title> 出险地点 </TD>
    <TD class= input> <Input class="wid" class= common name=AccidentSite id=AccidentSite ></TD>  
    </TR>
    </table>
   </Div> 
    
    <table class= common>
     <TR class= common>

	  <TD class= title> 事故日期</TD>
      <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="AccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
      <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD> 
    
      <TD class= title> 出险原因</TD>
      <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class=codename name=ReasonName id=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
         
      <TD class= title> 险种编码 </TD>
      <TD class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=RiskCode id=RiskCode ondblclick="return showCodeList('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');" onclick="return showCodeList('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');" onkeyup="return showCodeListKey('llgetriskcode',[this,''],[0,1],null,fm.GrpContNo.value,fm.GrpContNo.value,'1','300');"></TD>
    
       </TR>
   </table>

    <table class= common>
     <TR class= common>

       <TD class= title> 事件号 </TD> 
       <TD class= input><Input class="wid" class= "readonly" readonly name=AccNo id=AccNo></TD>

       <TD class= title> 报案号 </TD>
       <TD class= input> <Input class="wid" class="readonly" readonly  name=RptNo id=RptNo ></TD>

       <TD class= title> 管辖机构 </TD>
       <TD class= input> <Input class="wid" class= "readonly" readonly name=MngCom id=MngCom></TD>
      </TR>
   </table>

   <table class= common>
     <TR class= common>
       <TD class= title> 报案受理人 </TD>
       <TD class= input> <Input class="wid" class="readonly" readonly name=Operator id=Operator ></TD>
     
       <TD class= title> 报案日期 </TD>
	    <TD class= input>  <input class="wid" class="readonly" readonly name="RptDate" id="RptDate" ></TD>
	    
	    <TD class= title> 申请类型</TD>
        <!--<TD class= input> <Input class= codeno value="1" name=RgtClass ondblclick="return showCodeList('llrgtclass',[this,RgtClassName],[0,1]);" onkeyup="return showCodeListKey('llrgtclass',[this,RgtClassName],[0,1]);"><input class=codename name=RgtClassName readonly=true value="个人"></TD>-->
        <TD class= input>  <input class="wid" class="readonly" readonly name="RgtClassName" id="RgtClassName" value='团体'></TD>
     </TR>
   </table>

   <Div id="divreport5" style="display:none">     
    <table class= common>
     <TR class= common>
      <TD class= title> 是否使用团体帐户金额</TD>
      <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AccFlag id=AccFlag CodeData="0|3^10|是^20|否" ondblclick="return showCodeListEx('AccFlag', [this,AccFlagName],[0,1])" onclick="return showCodeListEx('AccFlag', [this,AccFlagName],[0,1])"onkeyup="return showCodeListKeyEx('AccFlag', [this,AccFlagName],[0,1])" ><Input class=codename name=AccFlagName id=AccFlagName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
      <TD class= title></TD>
      <TD class= input></TD>
      <TD class= title></TD>
      <TD class= input></TD>
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
                <td><INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table> 
  <table>
  <tr>
    <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
    <td class=titleImg> 客户信息 </td>
  </tr>
  </table>
  <Div id= "divLLSubReport" style= "display: ''" class="maxbox">
 
  

  
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
      <TD class= input> <Input type=hidden readonly name=customerSex><input class="wid" class=readonly readonly name=SexName id=SexName readonly=true></TD>
    </TR>
    <TR class= common>
      <TD class= title> 出险日期</TD>
      <TD class= input> <!--<input class="multiDatePicker" dateFormat="short" name="AccidentDate2" ><font size=1 color='#ff0000'><b>*</b></font>-->
      <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate2'});" verify="有效开始日期|DATE" dateFormat="short" name=AccidentDate2 id="AccidentDate2"><span class="icon"><a onClick="laydate({elem: '#AccidentDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
      </TD> 
      <TD class= title> 治疗情况</TD>
      <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=cureDesc id=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName id=cureDescName readonly=true></TD>
	  <!-- <TD class= title> VIP客户标示</TD>
      <TD class= input> <Input class="readonly" readonly name=IsVip></TD> -->
      
<!--       <TD class= title> 出险原因</TD>
      <TD class= input><Input class=codeno name=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class=codename name=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD> -->
      <TD style= "display: none" class= title> 出险结果1</TD>
      <TD style= "display: none" class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AccResult1 id=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResultName],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onclick="return showCodeList('lldiseases1',[this,AccResultName],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onFocus="saveIcdValue();"><input class=codename name=AccResult1Name readonly=true></TD>
    </TR>
   </table> 
   
   <Div id= "divreport2" style= "display: ''">
   <table class= common>
    <TR class= common>
      <span id="spanText8" style= "display: none">
      <table>
      
      
      <!--TD class= title> 出险结果2</TD>
      <TD class= input> <Input class=codeno name=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'ICDCode','1','300');"><input class=codename name=AccResult2Name readonly=true></TD-->
      </table>
      </span>
    </TR>
    </table>
    
   
   
    <!--TR class= common>
      <TD class= title> 死亡标识</TD>
      <TD class= input> <Input class=codeno name=IsDead ondblclick="return showCodeList('llDieFlag',[this,IsDeadName],[0,1]);" onkeyup="return showCodeListKey('llDieFlag',[this,IsDeadName],[0,1]);"><input class=codename name=IsDeadName readonly=true></TD>
    </tr-->
    
    
    <table class= common>
    
    <TR class= common>
      <TD class= title> 出险细节编码</TD>
      <TD class= input> <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name=accidentDetail id=accidentDetail ondblclick="showAccDetail(this.name,'accidentDetailName');" onclick="showAccDetail(this.name,'accidentDetailName');" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');"></TD>  
        <TD class= title> 出险结果编码</TD>
      <TD class= input> <Input class=code name=AccResult2 ondblclick="return showCodeList('lldiseases1',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');"></TD>
        <TD class= title> 治疗医院代码</TD>
      <TD class= input> <Input class=code name=hospital ondblclick="showHospital(this.name,'TreatAreaName');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"></TD>    </TR></table>
        <table class= common>
       <TR class= common>
      <TD class= title> 出险细节 </TD>
    </tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px">
      <span id="spanText5" style= "display: ''">
        <textarea name="accidentDetailName" id="accidentDetailName" cols="196" rows="4" witdh=25% class="common" ></textarea>
      </span>
      <!--隐藏表单作为显示数据用-->
      <!--<span id="spanText6" style= "display: 'none'">
        <textarea disabled name="accidentDetailNameDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>-->
      </TD>
    </TR>

    <TR class= common>
     
      <TD class= title> 出险结果</TD>
      
      </tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px">
      <span id="spanText9" style= "display: ''">
        <textarea name="AccResult2Name" id="AccResult2Name" cols="196" rows="4" witdh=25% class="common" onkeydown="QueryOnKeyDown(4)"></textarea>
      </span>
      </TD>
    </TR>     
    
    <TR class= common>
          
      <TD class= title> 治疗医院</TD></tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px">
      
      <span id="spanText5" style= "display: ''">
        <textarea name="TreatAreaName" id="TreatAreaName" cols="196" rows="4" witdh=25% class="common" onkeydown="QueryOnKeyDown(2)"></textarea>
      </span>
      <!--隐藏表单作为显示数据用-->
      <!--<span id="spanText6" style= "display: 'none'">
        <textarea disabled name="TreatAreaNameDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>-->
      </TD>
    </TR>
  
    </table>
  </Div> 

    <table class= common>
    <TR class= common>
      <TD class= title> 理赔类型 <font size=1 color='#ff0000'><b>*</b></font></TD>
      <TD align=left>
      <input style="vertical-align:middle" type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> <span style="vertical-align:middle">身故</span> </input>
      <input style="vertical-align:middle" type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> <span style="vertical-align:middle">高残</span> </input> 
      <input style="vertical-align:middle" type="checkbox" value="04" name="claimType"> <span style="vertical-align:middle">重大疾病</span> </input>
      <input style="vertical-align:middle" type="checkbox" value="01" name="claimType"> <span style="vertical-align:middle">残疾、烧伤、骨折、重要器官切除</span> </input>
<!--       <input type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> 豁免 </input> -->
      <input style="vertical-align:middle" type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"><span style="vertical-align:middle"> 医疗</span> </input>
      <input style="vertical-align:middle" type="checkbox" value="05" name="claimType" ><span style="vertical-align:middle">特种疾病</span> </input>
<!--      <input type="checkbox" value="06" name="claimType" > 失业失能 </input> -->
      </td>
    </TR>
    </table>
    
    <Div id= "divreport3" style= "display: ''">
    <table class= common>
    <TR  class= common>
      <TD  class= title> 事故描述 </TD>
    </tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px"> 
      <span id="spanText3" style= "display: ''">
        <textarea name="AccDesc" id="AccDesc" cols="196" rows="4" witdh=25% class="common"></textarea>
      </span>
      <!--隐藏表单作为显示数据用-->
      <!--<span id="spanText4" style= "display: 'none'">
        <textarea disabled name="AccDescDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>-->
      </TD>
    </tr>

    <TR class= common>
      <TD class= title> 备注 </TD>
    </tr>
    <TR class= common>
      <TD colspan="6" style="padding-left:16px">
      <span id="spanText1" style= "display: ''">
        <textarea name="Remark" id="Remark" cols="196" rows="4" witdh=25% class="common"></textarea>
      </span>
      <!--隐藏表单作为显示数据用-->
      <!--<span id="spanText2" style= "display: 'none'">
        <textarea disabled name="RemarkDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
      </span>-->
      </TD>
    </tr>
    </table>
   </Div>    
    
  </span>
  </div>
   <table>
    <tr>
    <td><input class=cssButton  name=QueryPerson value="客户查询" type=button onclick="showInsPerQuery()" ></td>
    <td><input class=cssButton  name=QueryReport value="事件查询" type=button onclick="queryLLAccident()"></td>
    <td>
      <span id="operateButton2" style= "display: none"><input class=cssButton name=addbutton  VALUE="新  增"  TYPE=button onclick="saveClick()" ></span>
      <span id="operateButton1" style= "display: none"><input class=cssButton name=deletebutton  VALUE="删  除"  TYPE=button onclick="deleteClick()" --></span>
      <span id="operateButton3" style= "display: none"><input class=cssButton name=updatebutton  VALUE="修  改"  TYPE=button onclick="updateClick()" ></span>
    </td>
    <td><input class=cssButton name=QueryCont2 VALUE="保单查询"  TYPE=button onclick="showInsuredLCPol()"></td>
    <td><input class=cssButton name=QueryCont3 VALUE="既往赔案查询" TYPE=button onclick="showOldInsuredCase()"></td> 
    <td><input class=cssButton name=QueryCont5 VALUE="既往赔案导出" TYPE=button onclick="getLastCaseInfo()"></td>   
    <td><input class=cssButton type=button name=BarCodePrint VALUE="打印条形码"  TYPE=button onclick="colBarCodePrint();"></td>
    <td><input class=cssButton type=button name=ColQueryImage VALUE="影像查询"  TYPE=button onclick="colQueryImage();"></td>
    <td><input class=cssButton type=hidden name=QueryCont4 VALUE="卡单信息查询" TYPE=button onclick="showCard()"></td>
    <td><input class=cssButton type=hidden name=MedCertForPrt VALUE="打印伤残鉴定通知"  TYPE=button onclick="PrintMedCert()"></td>
    </tr>
  </table>
  <span id="operateButton4" >
  <table>
    <tr>
    <td><input class=cssButton name=QueryReport2 value="赔款预估金额" type=button onclick="operStandPayInfo()"></td>
    <td><INPUT class=cssButton type=hidden name="DoHangUp" VALUE="保单挂起"  TYPE=button onclick="showLcContSuspend()" ></td>
    <td><INPUT class=cssButton name="CreateNote" VALUE="生成索赔资料"  TYPE=button onclick="showAffix()"></td>
    <td><INPUT class=cssButton name="CreateNote2" VALUE="打印索赔资料"  TYPE=button onclick="showPrtAffix()"></td>
    <td><INPUT class=cssButton name="BeginInq" VALUE="发起调查"  TYPE=button onclick="showBeginInq()"></td>
    <td><INPUT class=cssButton name="ViewInvest" VALUE="查看调查"  TYPE=button onclick="showQueryInq()"></td>
    <td><INPUT class=cssButton name="AccidentDesc" VALUE="备注信息"  TYPE=button onclick="showClaimDesc()"></td>
    <td><INPUT class=cssButton type=hidden name="Condole" VALUE="提起慰问"  TYPE=button onclick="showCondole()"></td>   
    </tr>
  </table>
  <table>
    <tr>
    <td><INPUT class=cssButton name="SubmitReport" VALUE="发起呈报"  TYPE=button onclick="showBeginSubmit()"></td>
    <td><INPUT class=cssButton name="ViewReport" VALUE="查看呈报"  TYPE=button onclick="showQuerySubmit()"></td>
    <td><INPUT class=cssButton type=hidden name="strcondoleflag" VALUE="0"  TYPE=button ></td>
    <td><input class=cssButton VALUE="理赔责任控制信息" TYPE=button onclick="ctrlclaimduty();"></td>
    </tr>
  </table>

  </span>
  <hr class="line">
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
  <Input type=hidden id="IDNo" name="IDNo"><!--出险人代码-->
  <Input type=hidden id="IDType" name="IDType"><!--出险人代码-->
  <input type=hidden id="isReportExist" name="isReportExist"><!--是否为新增事件,不判断则为空-->
  <input type=hidden id="Peoples" name="Peoples"><!--投保总人数-->
  <input type=hidden id="RgtClass" name="RgtClass" value='2'><!--申请类型:1:个人，2:团体-->
  <input type=hidden id="fmtransact" name="fmtransact"><!--操作代码‘insert,delete’等-->
  <Input type=hidden id="ManageCom" name="ManageCom"><!--登陆区站前两个代码-->
  <Input type=hidden id="AllManageCom" name="AllManageCom"><!--登陆区站-->
  <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
  <input type=hidden id="isNew" name="isNew"><!--是否创建新建-->
  <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10代码-->
  <input type=hidden id="isClaimState" name= "isClaimState"><!--判断来自理赔状态查询的节点-->
  
  <!--##########打印流水号，在打印检测时根据  赔案号、单证类型（代码）查询得到######-->
  <input type=hidden id="PrtSeq" name="PrtSeq">
  <input type=hidden id="RgtClass" name="RgtClass" value = "2"><!--团险个险代码-->
  <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
  <input type=hidden id="MissionID"  name= "MissionID">
  <input type=hidden id="SubMissionID" name= "SubMissionID">
  <Input type=hidden id="BaccDate" name="BaccDate"><!--后台的事故日期日期，每次查询时更新，校验时使用-->
  
  <input type=hidden id="tSQL" name= "tSQL">

</form>
<Div id="divreport4" style="display:none">
<form action="./GrpCustomerDiskReportSave.jsp" method=post name=fmload target="fraSubmit" ENCTYPE="multipart/form-data">    
  <table>
  <TR>
    <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,air);"></TD>
    <TD class= titleImg>导入操作</TD>
  </TR>
  </table>
  <Div  id= "air" style= "display: ''" class="maxbox1">
  <table class=common>
      <TD class=common >文件地址:</TD>
      <TD class=common >
        <Input type="file" name=FileName size=20>
        <input name=ImportPath type= hidden>
        <input class=common name=BatchNo type=hidden>
        <INPUT class=cssButton VALUE="导  入" TYPE=button name="PrintIn" onclick="getin();">
        <a href="./template/GrpReportCustomer.xls">下载[报案出险人]导入模板</a>
      </TD>    
     <input type=hidden name=ImportFile>
  </table>
      <br>
      <TR class= common>                            
          <TD class=title><font  color='#ff0000'><b>*使用导入功能请先填写报案信息栏！导入操作只导入出险人信息！</b></font> </TD>
		  </TR> 

 <!--<Div id="divDiskErr" style="display: 'none'" align = center>
    <table class=common border=0 width=100%>
      <tr>
        <td class=titleImg align=center>出险人导入错误信息查询－请输入查询条件:</td>
      </tr>
    </table>
    <table class=common align=center>
      <TR class=common>
        <TD class=title>报案号</TD>
        <TD class=input>
          <Input class=common name=tRgtNo>
        </TD>
        <TD class=title>导入文件名</TD>
        <TD class=input>
          <Input class=common name=tBatchNo>
        </TD>  
      </TR> 
      <TR class=common> 
        <TD class=title>客户号</TD>
        <TD class=input>
          <Input class=common name=tCustomerNo>
        </TD>      
        <TD class=title>客户姓名</TD>
        <TD class=input>
          <Input class=common name=tCustomerName>
        </TD>    
      </TR>
      <TR class=common> 
        <TD class=input>
    		<INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick();">
        </TD>   
      </TR>
    </table> 
      <Table class=common>
        <TR class=common>
          <TD text-align: left colSpan=1>
            <span id="spanDiskErrQueryGrid"></span>
          </TD>
        </TR>
      </Table>
      <Table align = center>
      <INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage2.firstPage();">
      <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage2.previousPage();">
      <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage2.nextPage();">
      <INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage2.lastPage();">
      </Table>
    </Div>	 -->
    
</form>
</Div>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>

</body>
</html>
