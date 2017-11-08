<%
//**************************************************************************************************
//Name：LLReportInput.jsp
//Function：报案登记
//Author：zl
//Date: 2005-6-9 15:31
//Desc: 
//修改：niuzj,2006-01-12,新增报案人邮编
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

	String tClmNo = request.getParameter("claimNo");	//赔案号
	String tIsNew = request.getParameter("isNew");	//创建人
	
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
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT> <!-- modify wyc -->
    <SCRIPT src="LLReport.js"></SCRIPT>
    
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLReportInit.jsp"%>
</head>

<body  onload="initForm()" >

<form method=post name=fm id=fm target="fraSubmit">
    <!--报案信息-->
    <table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
            <TD class= titleImg>报案信息</TD>
        </TR>
    </table>

    <Div id= "divLLReport1" style= "display: ''" class="maxbox">
        <table class= common>
            <TR class= common>
                <TD class= title> 事件号 </TD>
                <TD class= input> <Input class= "readonly wid" readonly name=AccNo id=AccNo></TD>
                <TD class= title> 报案号 </TD>
                <TD class= input> <Input class="readonly wid" readonly  name=RptNo id=RptNo ></TD>
                <TD class= title> 报案人姓名 </TD>
                <TD class= input> <Input class="wid" class= common name=RptorName id=RptorName ><font size=1 color='#ff0000'><b>*</b></font></TD>
            </TR>
            <TR class= common>
                <TD class= title> 报案人电话 </TD>
                <TD class= input> <Input class="wid" class= common name=RptorPhone id=RptorPhone></TD>
                <TD class= title> 报案人地址 </TD>
                <TD class= input> <Input class="wid" class= common name=RptorAddress id=RptorAddress ></TD>
                <TD class= title> 报案人邮编 </TD>
                <TD class= input> <Input class="wid" class= common name=PostCode id=PostCode onblur="checkPostCode(fm.PostCode);"></TD>   
            </TR>
            <TR class= common>
            	  <TD class= title> 报案人与出险人关系 </TD>
                <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=Relation id=Relation onfocus= "getRptorInfo()" ondblclick="return showCodeList('relation',[this,RelationName],[0,1]);" onclick="return showCodeList('relation',[this,RelationName],[0,1]);"  onkeyup="return showCodeListKey('relation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD class= title> 报案方式 </TD>
                <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class= codeno name="RptMode" id="RptMode" value="01" ondblclick="return showCodeList('llrptmode',[this,RptModeName],[0,1]);" onclick="return showCodeList('llrptmode',[this,RptModeName],[0,1]);" onkeyup="return showCodeListKey('llrptmode',[this,RptModeName],[0,1]);"><input class=codename name="RptModeName" id="RptModeName" value="电话报案" readonly=true></TD>
                <TD class= title> 出险地点 </TD>
                <TD class= input> <Input class="wid" class= common name=AccidentSite id=AccidentSite ></TD>
            </TR>
            <TR class= common>
            	  <TD class= title> 报案日期 </TD>
                <TD class= input>  <input class="readonly wid" readonly name="RptDate" id="RptDate" ></TD>
                <TD class= title> 管辖机构 </TD>
                <TD class= input> <Input class= "readonly wid" readonly name=MngCom id=MngCom></TD>
                <TD class= title> 报案受理人 </TD>
                <TD class= input> <Input class="readonly wid" readonly name=Operator id=Operator ></TD>
        	  </TR>
            <TR class= common>
            	  <TD class= title> 申请类型</TD>
          	    <TD class= input> <Input class= codeno value="1" name=RgtClass  id=RgtClassreadonly ><input class=codename name=RgtClassName id=RgtClassName readonly=true value="个人"></TD>
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
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type=hidden name=customerSex id=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input  class="wid" class=readonly readonly name=SexName id=SexName readonly=true></TD>
                </TR>
                <TR class= common>
	                <TD class= title> 事故日期</TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="AccidentDate" onblur="CheckDate(fm.AccidentDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" verify="有效开始日期|DATE" onblur="CheckDate(fm.AccidentDate);" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
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
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=hospital id=hospital ondblclick="showHospital(this.name,'TreatAreaName');" onclick="showHospital(this.name,'TreatAreaName');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName id=TreatAreaName readonly=true></TD>
                    <TD class= title> 出险原因</TD>
                    <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class=codename name=ReasonName id=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                	<TD class= title> 意外细节</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=accidentDetail id=accidentDetail ondblclick="showAccDetail(this.name,'accidentDetailName');" onclick="showAccDetail(this.name,'accidentDetailName');" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');"><input class=codename name=accidentDetailName id=accidentDetailName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗情况</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=cureDesc id=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName id=cureDescName readonly=true></TD>
                    <TD class= title> 出险结果1</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AccResult1 id=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onFocus="saveIcdValue();"><input class=codename name=AccResult1Name id=AccResult1Name readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class= title> 出险结果2</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AccResult2 id=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'ICDCode','1','300');"><input class=codename name=AccResult2Name id=AccResult2Name readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </TR>
               </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> 理赔类型 <font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD align=left>
                        <input style="vertical-align:middle" type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> <span style="vertical-align:middle">身故</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> <span style="vertical-align:middle">高残</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="04" name="claimType" onclick="callClaimType('04');"><span style="vertical-align:middle"> 重大疾病</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="01" name="claimType" onclick="callClaimType('01')"> <span style="vertical-align:middle">残疾、烧伤、骨折、重要器官切除 </span></input>
                        <input style="vertical-align:middle" type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> <span style="vertical-align:middle">豁免</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> <span style="vertical-align:middle">医疗</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="05" name="claimType" onclick="callClaimType('05')"> <span style="vertical-align:middle">特种疾病</span> </input>
                        <!-- <input type="checkbox" value="06" name="claimType" > 失业失能 </input>  -->
                    </td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> 事故描述 </TD>
                </tr>
                <TR class= common>
                    <TD style="padding-left:16px" colspan="6"> 
                        <span id="spanText3" style= "display: ''">
                            <textarea name="AccDesc" id="AccDesc" cols="224" rows="4" witdh=25% class="common"></textarea>
                        </span>
                    </TD>
                </tr>
                <TR class= common>
                    <TD class= title> 备注 </TD>
                </tr>
                <TR class= common>
                    <TD style="padding-left:16px" colspan="6">
                        <span id="spanText1" style= "display: ''">
                            <textarea name="Remark" id="Remark" cols="224" rows="4" witdh=25% class="common"></textarea>
                        </span>
                        <!--隐藏表单作为显示数据用-->
                        <br><br>
                        <span id="spanText2" style= "display: none">
                            <textarea  name="RemarkDisabled" id="RemarkDisabled" cols="224" rows="4" witdh=25% class="common"></textarea>
                        </span>
                    </TD>
                </tr>
            </table>
        </span>
    </div>
     <table>
            <tr>
                <td><input class=cssButton name=QueryPerson value="客户查询" type=button onclick="showInsPerQuery()" ></td>
                <td><input class=cssButton name=QueryReport value="事件查询" type=button onclick="queryLLAccident()"></td>
                <td>
                    <span id="operateButton2" style= "display: none"><input class=cssButton name=addbutton  VALUE="保  存"  TYPE=button onclick="saveClick()" ></span>
                    <span id="operateButton3" style= "display: none"><input class=cssButton name=updatebutton  VALUE="修  改"  TYPE=button onclick="updateClick()" ></span>
                </td>
                <td><input class=cssButton name=QueryCont2 VALUE="保单查询"  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><input class=cssButton name=QueryCont3 VALUE="既往赔案查询" TYPE=button onclick="showOldInsuredCase()"></td>
                <td><input class=cssButton type=hidden  name=MedCertForPrt VALUE="打印伤残鉴定通知"  TYPE=button onclick="PrintMedCert()"></td>
                <td><input class=cssButton name=BarCodePrint VALUE="打印条形码"  TYPE=button onclick="colBarCodePrint();"></td>
                <td><input class=cssButton name=ColQueryImage VALUE="影像查询"  TYPE=button onclick="colQueryImage();"></td>
            </tr>
        </table>
        <hr class="line">
    <span id="operateButton4" >
        <table>
            <tr>
                <td><INPUT class=cssButton name="DoHangUp" VALUE="保单挂起"  TYPE=button onclick="showLcContSuspend()" ></td>
                <td><INPUT class=cssButton name="CreateNote" VALUE="生成单证"  TYPE=button onclick="showAffix()"></td>
                <td><INPUT class=cssButton name="PrintCertify" VALUE="打印单证"  TYPE=button onclick="showPrtAffix()"></td>                
                <td><INPUT class=cssButton name="BeginInq" VALUE="发起调查"  TYPE=button onclick="showBeginInq()"></td>
                <td><INPUT class=cssButton name="ViewInvest" VALUE="查看调查"  TYPE=button onclick="showQueryInq()"></td>
                <!-- <td><INPUT class=cssButton name="Condole" VALUE="提起慰问"  TYPE=button onclick="showCondole()"></td> -->
                <td><INPUT class=cssButton name="SubmitReport" VALUE="发起呈报"  TYPE=button onclick="showBeginSubmit()"></td>
                <td><INPUT class=cssButton name="ViewReport" VALUE="查看呈报"  TYPE=button onclick="showQuerySubmit()"></td>
                <td><INPUT class=cssButton name="AccidentDesc" VALUE="备注信息"  TYPE=button onclick="showClaimDesc()"></td>
            </tr>
        </table>
    </span>
    <hr class="line">
    <table>
        <tr>
            <td><INPUT class=cssButton name="RptConfirm" VALUE="报案确认"  TYPE=button onclick="reportConfirm()"></td>
            <td><INPUT class=cssButton type=hidden  name="BatchCreateNote" VALUE="单证批量打印控制"  TYPE=button onclick="showPrtControl()"></td>
            <td><INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()"></td>
        </tr>
    </table><!--<br>
    <a href="javascript:void(0);" name="RptConfirm" class="button" onClick="reportConfirm();">报案确认</a>
    <a href="javascript:void(0);" name="BatchCreateNote" class="button" onClick="showPrtControl();">单证批量打印控制</a>
    <a href="javascript:void(0);" name="goBack" class="button" onClick="goToBack();">返    回</a>-->
    <%
    //隐藏区,保存信息用
    %>
    <Input type=hidden id="customerNo" name="customerNo"><!--出险人代码-->
    <input type=hidden id="isReportExist" name="isReportExist"><!--是否为新增事件,不判断则为空-->
    <input type=hidden id="fmtransact" name="fmtransact"><!--操作代码‘insert,delete’等-->

    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    <input type=hidden id="isNew" name="isNew"><!--是否创建新建-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10代码-->
	<Input type=hidden id="BaccDate" name="BaccDate"><!--后台的事故日期日期，每次查询时更新，校验时使用-->
    
    <!--##########打印流水号，在打印检测时根据  赔案号、单证类型（代码）查询得到######-->
    <input type=hidden id="PrtSeq" name="PrtSeq">
    
    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
