<%
//**************************************************************************************************
//Name：LLClaimRegister.jsp
//Function：立案登记
//Author：zl
//Date: 2005-6-14 20:04
//Desc:
//**************************************************************************************************
%>
<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
    String CurrentDate = PubFun.getCurrentDate();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");

	String tClmNo = request.getParameter("claimNo");	//赔案号
	String tIsNew = request.getParameter("isNew");	//是否新增
	String tClmState = request.getParameter("clmState");	//立案状态

	String tMissionID = request.getParameter("MissionID");  //工作流任务ID
	String tSubMissionID = request.getParameter("SubMissionID");  //工作流子任务ID
	String tActivityID=request.getParameter("ActivityID");  //工作流子任务ID
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
   <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
   <SCRIPT src="LLClaimRegister.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
   <%@include file="LLClaimRegisterInit.jsp"%>
</head>
<body onload="initForm()" >
<form action="./LLClaimRegisterSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!--立案申请信息-->
    <table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
            <TD class= titleImg>立案申请信息</TD>
        </TR>
    </table>
    <Div  id= "divLLReport1" style= "display: ''" class="maxbox">
        <table  class= common>
       	    <TR  class= common>
			    			<TD  class= title> 事件号 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=AccNo id=AccNo></TD>
         	    	<TD  class= title> 立案号 </TD>
                <TD  class= input> <Input class="readonly wid" readonly  name=RptNo id=RptNo ></TD>
								<TD  class= title> 申请人姓名 </TD>
          	    <TD  class= input> <Input class="wid" class= common name=RptorName id=RptorName ><font size=1 color='#ff0000'><b>*</b></font></TD>
            </TR>
            <TR  class= common>
              	<TD  class= title> 申请人电话 </TD>
              	<TD  class= input> <Input class="wid" class= common name=RptorPhone id=RptorPhone ><font size=1 color='#ff0000'><b>*</b></font></TD>
								<TD  class= title> 申请人手机号码 </TD>
              	<TD  class= input> <Input class="wid" class= common name=RptorMoPhone id=RptorMoPhone verify="申请人手机号码|LEN=11" verifyorder="1" onblur="checkRptorMolength(this.value);confirmSecondInput(this,'onblur');"></TD>
                <TD  class= title> 申请人详细地址 </TD>
                <TD  class= input colspan="3"> <Input class="wid" class= "common3" name=RptorAddress id=RptorAddress ><font size=1 color='#ff0000'><b>*</b></font></TD>
						</TR>
 			 			<TR  class= common>		
 			 					<TD  class= title> 申请人邮编 </TD>
								<TD  class= input><Input class="wid" class=common name="AppntZipCode" id="AppntZipCode" elementtype=nacessary  verify="申请人邮编|NOTNULL&LEN=6" verifyorder="1" onblur="checkziplength(this.value);confirmSecondInput(this,'onblur');"><font size=1 color='#ff0000'><b>*</b></font></TD>
 			 					<TD  class= title> 申请人与出险人关系</TD>
    	       		<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Relation id=Relation onfocus= "getRptorInfo()" ondblclick="return showCodeList('relation',[this,RelationName],[0,1]);" onclick="return showCodeList('relation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('relation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
								<TD  class= title> 出险地点 </TD>
          	    <TD  class= input> <Input class="wid" class= common name=AccidentSite id=AccidentSite ></TD>            
						</TR>
						<TR  class= common>
       		    <TD  class= title> 立案日期 </TD>
          	  <TD  class= input> <input class="readonly wid" readonly name="RptDate" id="RptDate" ></TD>
			    		<TD  class= title> 管辖机构 </TD>
          	  <TD  class= input> <Input class= "readonly wid" readonly name=MngCom id=MngCom></TD>
	        		<TD  class= title> 立案受理人 </TD>
          	  <TD  class= input> <Input class="readonly wid" readonly name=Operator id=Operator ></TD>
 						</TR>
            <TR  class= common>
        	    <TD  class= title> 受托人类型 </TD>
          	  <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno name=AssigneeType id=AssigneeType CodeData="0|^0|业务员^1|客户^2|内勤" ondblclick="return showCodeListEx('AssigneeType', [this,AssigneeTypeName],[0,1])"  onclick="return showCodeListEx('AssigneeType', [this,AssigneeTypeName],[0,1])" onkeyup="return showCodeListKeyEx('AssigneeType', [this,AssigneeTypeName],[0,1])"><Input class=codename name=AssigneeTypeName id=AssigneeTypeName readonly=true></TD>
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
          	  <TD  class= input> <Input class="wid" class= common name=AssigneeAddr id=AssigneeAddr ></TD>           
 					 </TR>
           <TR  class= common>
        	    <TD  class= title> 受托人邮编 </TD>
          	  <TD  class= input> <Input class="wid" class= common name=AssigneeZip id=AssigneeZip ></TD>
           <!-- <TD  class= title> 申请类型</TD>
          	  <TD  class= input> <Input class= codeno value="1" name=RgtClass readonly ><input class=codename name=RgtClassName readonly=true value="个人"></TD>-->
              <TD class= title> 交接日期</TD>
              <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="AcceptedDate";"><font size=1 color='#ff0000'><b>*</b></font>-->
              <Input class="coolDatePicker" onClick="laydate({elem: '#AcceptedDate'});" verify="有效开始日期|DATE" dateFormat="short" name=AcceptedDate id="AcceptedDate"><span class="icon"><a onClick="laydate({elem: '#AcceptedDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
              </TD>            
							<TD class= title> 客户申请日期</TD>
              <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="ApplyDate";"><font size=1 color='#ff0000'><b>*</b></font>-->
              <Input class="coolDatePicker" onClick="laydate({elem: '#ApplyDate'});" verify="有效开始日期|DATE" dateFormat="short" name=ApplyDate id="ApplyDate"><span class="icon"><a onClick="laydate({elem: '#ApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
              </TD>           
			</TR>
        </table>
  	</Div>
	<br>
    <!--客户信息-->
    <table class= common>
        <tr class= common>
            <td text-align: left colSpan=1><span id="spanSubReportGrid" ></span></td>
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
                    <TD class= title> 性别 </TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type=hidden name=customerSex id=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input  class="wid"  class=readonly  readonly name=SexName id=SexName readonly=true></TD>
                </TR>
                <TR class= common>
                    <!-- <TD class= title> VIP客户标识</TD>
                    <TD class= input> <Input type=hidden name=IsVip><input class=readonly readonly name=VIPValueName readonly=true></TD> -->
                    <TD class= title> 事故日期</TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="AccidentDate" onblur="CheckDate(fm.AccidentDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" onblur="CheckDate(fm.AccidentDate);" verify="有效开始日期|DATE" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
					<TD class= title> 医疗出险日期 </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="MedicalAccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#MedicalAccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=MedicalAccidentDate id="MedicalAccidentDate"><span class="icon"><a onClick="laydate({elem: '#MedicalAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>              
                    <TD class= title> 其他出险日期 </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="OtherAccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#OtherAccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=OtherAccidentDate id="OtherAccidentDate"><span class="icon"><a onClick="laydate({elem: '#OtherAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
               </TR>
                <TR class= common>
                    <TD class= title> 治疗医院</TD>
                    <!--TD class= input> <Input class=codeno name=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName readonly=true></TD-->
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=hospital id=hospital ondblclick="showHospital(this.name,'TreatAreaName');" onclick="showHospital(this.name,'TreatAreaName');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName id=TreatAreaName readonly=true></TD>
                    <TD class= title> 出险原因</TD>
                    <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class=codename name=ReasonName id=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class= title> 意外细节</TD>
                    <!--TD class= input> <Input class=codeno name=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');"><input class=codename name=accidentDetailName readonly=true></TD-->
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=accidentDetail id=accidentDetail ondblclick="showAccDetail(this.name,'accidentDetailName');" onclick="showAccDetail(this.name,'accidentDetailName');" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');"><input class=codename name=accidentDetailName id=accidentDetailName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗情况</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=cureDesc id=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName id=cureDescName readonly=true></TD>
                    <TD class= title> 出险结果1</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AccResult1 id=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" ><input class=codename name=AccResult1Name id=AccResult1Name readonly=true><font size=1 color='#ff0000'><b>*</b></TD>
                    <TD class= title> 出险结果2</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AccResult2 id=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'ICDCode','1','300');"><input class=codename name=AccResult2Name id=AccResult2Name readonly=true><font size=1 color='#ff0000'><b>*</b></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 单证齐全标识</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=IsAllReady id=IsAllReady disabled CodeData="0|3^0|否^1|是" ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])" onclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class=codename name=IsAllReadyName id=IsAllReadyName readonly=true></TD>
                    <TD class= title> 案件标识</TD>
                    <TD class= input> <Input class="wid" class=readonly name=ClaimFlag id=ClaimFlag readonly ></TD>
                </tr>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> 理赔类型：<font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD align=left>
                        <input style="vertical-align:middle" type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"><span style="vertical-align:middle"> 身故</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"><span style="vertical-align:middle"> 高残</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="04" name="claimType" onclick="callClaimType('04');"> <span style="vertical-align:middle">重大疾病</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="01" name="claimType" onclick="callClaimType('01')"><span style="vertical-align:middle"> 残疾、烧伤、骨折、重要器官切除 </span></input>
                        <input style="vertical-align:middle" type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> <span style="vertical-align:middle">豁免</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"><span style="vertical-align:middle"> 医疗</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="05" name="claimType" onclick="callClaimType('05')"><span style="vertical-align:middle"> 特种疾病</span> </input>
                        <!-- <input type="checkbox" value="06" name="claimType" > 失业失能 </input> -->
					</td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> 事故描述 </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px">
                        <span id="spanText3" style= "display: ''">
                            <textarea name="AccDesc" cols="224" rows="4" witdh=25% class="common"></textarea>
                        </span>
                    </TD>
                </tr>
                <TR class= common>
                    <TD class= title> 备注 </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px">
                        <span id="spanText1" style= "display: ''">
                            <textarea name="Remark" id=Remark cols="224" rows="4" witdh=25% class="common"></textarea>
                        </span>
                    </TD>
                </tr>
            </table>
        </span>
    </div>
	<table>
            <tr>
                <td><input class=cssButton name=QueryPerson value="客户查询" type=button onclick="showInsPerQuery()" ></td>
 				<td><input class=cssButton name=QueryReportClm value="报案查询与关联" type=button onclick="queryLLReport()"></td>
                <td><input class=cssButton name=QueryReport value="事件查询" type=button onclick="queryLLAccident()"></td>
                <td>
                    <span id="operateButton21" style= "display: none"><input class=cssButton name=addbutton  VALUE="保  存"  TYPE=button onclick="saveClick()" ></span>
                    <span id="operateButton22" style= "display: none"><input class=cssButton name=updatebutton  VALUE="修  改"  TYPE=button onclick="updateClick()" ></span>
                </td>
                <!--td><input class=cssButton name=QueryCont1 VALUE="投保单查询"  TYPE=button onclick=""></td-->
                <td><input class=cssButton name=QueryCont2 VALUE="保单查询"  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><input class=cssButton name=QueryCont3 VALUE="既往赔案查询" TYPE=button onclick="showOldInsuredCase()"></td>
			          <!--<td><input type=hidden class=cssButton name=MedCertForPrt VALUE="打印伤残鉴定通知"  TYPE=button onclick="PrintMedCert()"></td>-->
                <td><input class=cssButton name=BarCodePrint VALUE="打印条形码"  TYPE=button onclick="colBarCodePrint();"></td>
                <td><input class=cssButton name=ColQueryImage VALUE="影像查询"  TYPE=button onclick="colQueryImage();"></td>                
            </tr>
        </table>
        <hr class="line">
    <span id="operateButton3" >
		<table>
			<tr>
				<td><INPUT class=cssButton name="DoHangUp" VALUE="保单挂起"  TYPE=button onclick="showLcContSuspend()" ></td>
				<td><INPUT class=cssButton name="CreateNote" VALUE="单证回销"  TYPE=button onclick="showRgtMAffixList()"></td>
                <td><INPUT class=cssButton name="CreateNote" VALUE="补充单证"  TYPE=button onclick="showRgtAddMAffixList()"></td>
				<td><INPUT class=cssButton name="ViewInvest" VALUE="查看调查"  TYPE=button onclick="showQueryInq()"></td>
				<td><INPUT class=cssButton name="ViewReport" VALUE="查看呈报"  TYPE=button onclick="showQuerySubmit()"></td>
			    <td><INPUT class=cssButton name="AccidentDesc" VALUE="备注信息"  TYPE=button onclick="showClaimDesc()"></td>
			    <td><INPUT class=cssButton name="MedicalFeeInp" VALUE="医疗单证录入"  TYPE=button onclick="showLLMedicalFeeInp();"></td>
			    <!--<td><INPUT class=cssButton disabled name="OutReciInfoInp" VALUE="发起外包录入"  TYPE=button onclick="showWaiBao();"></td>-->
			    <td><INPUT class=cssButton name="ReciInfoInp" VALUE="收件人信息"  TYPE=button onclick="showReciInfoInp();"></td>
			</tr>
		</table>
	</span>
    <!--立案结论信息*********************************Beg********************************************-->
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLRegisterConclusion);"></td>
            <td class=titleImg> 立案结论信息 </td>
		</tr>
    </table>
    <Div id= "divLLRegisterConclusion" style= "display: ''" class="maxbox1">
    	<table class= common>
    	    <TR class= common>
    	        <TD  class= title> 立案结论</TD>
    	        <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=RgtConclusion id=RgtConclusion ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onFocus="showDIV();"><input class=codename name=RgtConclusionName id=RgtConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
				<TD  class= title> 案件类型</TD>
            	<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=rgtType id=rgtType CodeData="0|^11|普通案件^01|简易案件" ondblclick="return showCodeListEx('rgtType', [this,rgtTypeTypeName],[0,1])"  onclick="return showCodeListEx('rgtType', [this,rgtTypeTypeName],[0,1])" onkeyup="return showCodeListKeyEx('rgtType', [this,rgtTypeTypeName],[0,1])"><Input class=codename name=rgtTypeTypeName id=rgtTypeTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>        
    	        <TD  class= title> </TD>
    	        <TD  class= input> </TD>
    	    </tr>
    	</table>
    	<span id= "spanConclusion1" style= "display: none">
        
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
                    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,PolDutyCode);"></td>
                    <td class= titleImg>保项计算信息</td>
                 </tr>
            </table>
            <Div  id= "PolDutyCode" style= "display:''">
                <Table  class= common>
                    <tr>
                        <td text-align: left colSpan=1><span id="spanPolDutyCodeGrid"></span></td>
                    </tr>
                </Table>
            </div>
            <br>
            <!--预估金额调整信息-->
            <table class= common>
                <TR class= common>
                    <!-- <TD  class= title> 预估金额</TD> -->
                    <TD  class= input> <Input class=common  type=hidden readonly name=standpay onblur="checkAdjPay();"></TD>
                    <!-- <TD  class= title> 调整为</TD> -->
                    <TD  class= input> <Input class=common type=hidden name=adjpay onblur="checkAdjPay();"></TD>  
			   </tr>
            </table>
        </span>
        <span id= "spanConclusion2" style= "display: '' "><!-- 显示 wyc  -->
            <table class= common>
                <TR class= common>
                	<TD  class= title> 不予立案原因</TD>
                	<TD  class= input> <Input class=codeno name=NoRgtReason style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llnorgtreason',[this,NoRgtReasonName],[0,1]);" onkeyup="return showCodeListKey('llnorgtreason',[this,NoRgtReasonName],[0,1]);"><input class=codename name=NoRgtReasonName readonly=true></TD>
                	<TD  class= title> </TD>
                	<TD  class= input> </TD>
                	<TD  class= title> </TD>
                	<TD  class= input> </TD>
                </tr>
            </table>
        </span>
    </DIV>
    <table>
        <tr>
            <td><INPUT class=cssButton name="conclusionSave" VALUE="结论保存"  TYPE=button onclick="firstSaveConclusionClick()"></td>
            <td><INPUT class=cssButton name="dutySet" VALUE="匹配并理算"  TYPE=hidden onclick="showMatchDutyPay();"></td>
            <td><INPUT class=cssButton name="medicalFeeCal" VALUE="单证计算信息"  TYPE=button onclick="showLLMedicalFeeCal();" ></td>
<!--             <td><INPUT class=cssButton name="printPassRgt" VALUE="打印理赔材料签收清单"  TYPE=button onclick="prtPassRgt()" ></td> -->
            <td><INPUT class=cssButton name="printNoRgt" VALUE="打印不予立案通知书"  TYPE=button onclick="prtNoRgt()" ></td>
        </tr>
    </table>
    <!--*****************************************end************************************************-->
    <hr class="line">
    <table>
        <tr>
            <td><INPUT class=cssButton name="RgtConfirm" VALUE="立案确认"  TYPE=button onclick="RegisterConfirm()"></td>
            <td><INPUT class=cssButton type=hidden name="BatchCreateNote" VALUE="单证批量打印控制"  TYPE=button onclick="showPrtControl()"></td>
            <td><INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()"></td>
        </tr>
    </table>
    <%
    //隐藏区,保存信息用
    %>
    <input type=hidden name=hideOperate value="">
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="isReportExist" name="isReportExist"><!--是否为新增事件,不判断则为空-->
    <input type=hidden id="isRegisterExist" name="isRegisterExist"><!--是否为新增立案-->

    <Input type=hidden id="customerNo" name="customerNo"><!--出险人代码-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10代码-->
    <Input type=hidden id="BaccDate" name="BaccDate"><!--后台的事故日期日期，每次查询时更新，校验时使用-->

    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    <input type=hidden id="isNew" name="isNew"><!--是否创建新建-->
    <input type=hidden id="clmState" name="clmState"><!--立案状态-->
    <Input type=hidden id="RgtClass" name=RgtClass value="1" >
    <input type=hidden id="RgtClassName" name=RgtClassName value="个人">

    <input type=hidden id="PrtSeq" name="PrtSeq"><!--打印流水号，在打印检测时根据  赔案号、单证类型（代码）查询得到-->
     
    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
	
</form>

<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
