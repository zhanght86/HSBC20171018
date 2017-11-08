<%
//**************************************************************************************************
//Name：LLClaimImportModifyInput.jsp
//Function：重点信息修改
//Author：quyang
//Date: 2005-6-22
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

	  String tRptNo = request.getParameter("tRptNo");	//赔案号
      loggerDebug("LLClaimImportModifyInput",tRptNo);
	  String tMissionID = request.getParameter("MissionID");  //工作流任务ID
	  String tSubMissionID = request.getParameter("SubMissionID");  //工作流子任务ID
	  
	  ////加入一个参数,用于控制"重点信息修改"页面上的"修改确认"按钮
	  //为0时该按钮能使用,为1时该按钮不能使用
	  String tIsShow = request.getParameter("tIsShow");	
    loggerDebug("LLClaimImportModifyInput",tIsShow);
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
    <SCRIPT src="LLClaimImportModify.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLClaimImportModifyInit.jsp"%>
</head>
<body onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
            <td class=titleImg> 客户信息 --- 备份区</td>
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
                    <TD class= input> <Input class="readonly wid" readonly name=customerAge id=customerAge><Input type="hidden" readonly name=customerBir></TD>
                    <TD class= title> 性别 </TD>
                    <TD class= input> <Input type="hidden" style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled readonly name=customerSex id=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class="wid" class=codename name=SexName id=SexName readonly=true></TD>
                </TR>
 				<TR class= common>
 					<TD class= title> 交接日期 </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" readonly dateFormat="short" name="AcceptedDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#AcceptedDate'});" verify="有效开始日期|DATE" dateFormat="short" name=AcceptedDate id="AcceptedDate"><span class="icon"><a onClick="laydate({elem: '#AcceptedDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
					<TD class= title> 客户申请日期 </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" readonly dateFormat="short" name="ApplyDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#ApplyDate'});" verify="有效开始日期|DATE" dateFormat="short" name=ApplyDate id="ApplyDate"><span class="icon"><a onClick="laydate({elem: '#ApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
					<TD class= title> 补充材料受理日期  </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" readonly dateFormat="short" name="AddFileDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#AddFileDate'});" verify="有效开始日期|DATE" dateFormat="short" name=AddFileDate id="AddFileDate"><span class="icon"><a onClick="laydate({elem: '#AddFileDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>				
				</TR>
                <TR class= common>
                    <!-- <TD class= title> VIP客户标示</TD>
                    <TD class= input> <Input class="readonly" readonly name=IsVip></TD> -->
                    <TD class= title> 事故日期</TD>
                    <TD class= input> <input class="readonly wid" readonly name="AccidentDate" id="AccidentDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class= title> 医疗出险日期 </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" readonly dateFormat="short" name="MedicalAccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#MedicalAccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=MedicalAccidentDate id="MedicalAccidentDate"><span class="icon"><a onClick="laydate({elem: '#MedicalAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>              
                    <TD class= title> 其他出险日期 </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" readonly dateFormat="short" name="OtherAccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#OtherAccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=OtherAccidentDate id="OtherAccidentDate"><span class="icon"><a onClick="laydate({elem: '#OtherAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
					</TR>
                <TR class= common>
                    <TD class= title> 治疗医院</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=hospital id=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName id=TreatAreaName readonly=true></TD>
                    <TD class= title> 出险原因</TD>
                    <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class=codename name=ReasonName id=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                	<TD class= title> 出险细节</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=accidentDetail id=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class=codename name=accidentDetailName id=accidentDetailName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗情况</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=cureDesc  id=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);"onclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName id=cureDescName readonly=true></TD>
                    <TD class= title> 出险结果1</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=AccResult1 id=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1]);" ><input class=codename name=AccResult1Name id=AccResult1Name readonly=true></TD>
                    <TD class= title> 出险结果2</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=AccResult2 id=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');" onFocus="saveIcdValue();"><input class=codename name=AccResult2Name id=AccResult2Name readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 非正常修改标识</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=AccDateModSign id=AccDateModSign CodeData ="0|^0|出险日期正常修改 ^1|出险日期非正常修改" ondblclick="return showCodeListEx('llAccDateModSign', [this,AccDateModSignName],[0,1]);" onclick="return showCodeListEx('llAccDateModSign', [this,AccDateModSignName],[0,1]);" onkeyup="return showCodeListKeyEx('llAccDateModSign', [this,AccDateModSignName],[0,1]);"><input class=codename name=AccDateModSignName id=AccDateModSignName readonly=true></TD>
                    <TD class= title> 赔付金领取方式</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=PayGetMode id=PayGetMode ondblclick="return showCodeList('llcasegetmode',[this,PayGetModeName],[0,1]);" onclick="return showCodeList('llcasegetmode',[this,PayGetModeName],[0,1]);" onkeyup="return showCodeListKeyEx('llcasegetmode', [this,PayGetModeName],[0,1]);"><input class=codename name=PayGetModeName id=PayGetModeName readonly=true></TD>
                </TR> 
  			    <TR class= common>
					<TD  class= title> 申请人手机号码 </TD>
              		<TD  class= input> <Input class="wid" class=readonly readonly disabled name=RptorMoPhone id=RptorMoPhone verify="申请人手机号码|LEN=11" verifyorder="1" onblur="checkRptorMolength(this.value);confirmSecondInput(this,'onblur');"></TD>
					<TD  class= title> 申请人邮编 </TD>
				    <TD  class= input><Input class="wid" class=readonly readonly disabled name="AppntZipCode" id="AppntZipCode" elementtype=nacessary  verify="申请人邮编|NOTNULL&LEN=6" verifyorder="1" onblur="checkziplength(this.value);confirmSecondInput(this,'onblur');"><font size=1 color='#ff0000'><b>*</b></font></TD> 				
				</TR>        
				<TR  class= common>
				    <TD  class= title> 申请人详细地址 </TD>
               		<TD  class= input> <Input class="wid" class= "common3" readonly name=RptorAddress id=RptorAddress ><font size=1 color='#ff0000'><b>*</b></font></TD>            
 			 	</TR>          
				<input type="hidden" name=AccDesc >
				<input type="hidden" name=Remark >
                </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> 理赔类型：<font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD align=left >
               <TD align=left>
                 		<input style="vertical-align:middle" type="checkbox" value="02" name="claimType" onclick="return false;"><span style="vertical-align:middle"> 身故</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="03" name="claimType" onclick="return false;"><span style="vertical-align:middle"> 高残</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="04" name="claimType" onclick="return false;"><span style="vertical-align:middle"> 重大疾病</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="01" name="claimType" onclick="return false;"> <span style="vertical-align:middle">残疾、烧伤、骨折、重要器官切除</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="09" name="claimType" onclick="return false;"> <span style="vertical-align:middle">豁免</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="00" name="claimType" onclick="return false;"> <span style="vertical-align:middle">医疗</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="05" name="claimType" onclick="return false;"> <span style="vertical-align:middle">特种疾病</span> </input>
                    </td>
                </TR>
            </table>
            
        </span>
    </div>
    
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport1);"></td>
            <td class=titleImg> 客户信息 --- 修改区</td>
        </tr>
    </table>
    <Div id= "divLLSubReport1" style= "display: ''" class="maxbox">

        <!--出险人信息表单-->
        <span id="spanSubReport" >
            <table class= common>
                <TR class= common>
                    <!--出险人编码,见隐藏区-->
                    <TD class= title> 客户姓名 </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=newcustomerName id=newcustomerName></TD>
                    <TD class= title> 客户年龄 </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=newcustomerAge id=newcustomerAge><Input type="hidden" readonly name=newcustomerBir></TD>
                    <TD class= title> 性别 </TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type=hidden name=newcustomerSex id=newcustomerSex ondblclick="return showCodeList('sex',[this,newSexName],[0,1]);" onclick="return showCodeList('sex',[this,newSexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,newSexName],[0,1]);"><input class="wid" class=readonly readonly name=newSexName id=newSexName readonly=true></TD>
                </TR>
				<TR class= common>
 					<TD class= title> 交接日期 </TD>
                    <TD class= input>  <!--<input class="multiDatePicker"  dateFormat="short" name="newAcceptedDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    
                    <Input class="coolDatePicker" onClick="laydate({elem: '#newAcceptedDate'});" verify="有效开始日期|DATE" dateFormat="short" name=newAcceptedDate id="newAcceptedDate"><span class="icon"><a onClick="laydate({elem: '#newAcceptedDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
					<TD class= title> 客户申请日期 </TD>
                    <TD class= input> <!-- <input class="multiDatePicker"  dateFormat="short" name="newApplyDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#newApplyDate'});" verify="有效开始日期|DATE" dateFormat="short" name=newApplyDate id="newApplyDate"><span class="icon"><a onClick="laydate({elem: '#newApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
					<TD class= title> 补充材料受理日期  </TD>
                    <TD class= input>  <!--<input class="multiDatePicker"  dateFormat="short" name="newAddFileDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#newAddFileDate'});" verify="有效开始日期|DATE" dateFormat="short" name=newAddFileDate id="newAddFileDate"><span class="icon"><a onClick="laydate({elem: '#newAddFileDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>				
				</TR>
                <TR class= common>
                    <!-- <TD class= title> VIP客户标示</TD>
                    <TD class= input> <Input class="readonly" readonly name=newIsVip></TD> -->
 					<TD class= title> 事故日期</TD>
                    <TD class= input> <!--<input class="multiDatePicker"  dateFormat="short" name="newAccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#newAccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=newAccidentDate id="newAccidentDate"><span class="icon"><a onClick="laydate({elem: '#newAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD class= title> 医疗出险日期 </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="newMedicalAccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#newMedicalAccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=newMedicalAccidentDate id="newMedicalAccidentDate"><span class="icon"><a onClick="laydate({elem: '#newMedicalAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>              
                    <TD class= title> 其他出险日期 </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="newOtherAccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#newOtherAccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=newOtherAccidentDate id="newOtherAccidentDate"><span class="icon"><a onClick="laydate({elem: '#newOtherAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
					</TR>
                <TR class= common>
                    <TD class= title> 治疗医院</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=newhospital id=newhospital ondblclick="showHospital(this.name,'newTreatAreaName');" onclick="showHospital(this.name,'newTreatAreaName');" onkeyup="return showCodeListKey('commendhospital',[this,newTreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=newTreatAreaName id=newTreatAreaName readonly=true></TD>
                    <TD class= title> 出险原因</TD>
                    <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=newoccurReason id=newoccurReason ondblclick="return showCodeList('lloccurreason',[this,newReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,newReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,newReasonName],[0,1]);" onFocus="checkInsReason()"><input class=codename name=newReasonName id=newReasonName readonly=true onFocus="checkInsReason()" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class= title> 出险细节</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=newaccidentDetail id=newaccidentDetail ondblclick="showAccDetail(this.name,'newaccidentDetailName');" onclick="showAccDetail(this.name,'newaccidentDetailName');" onkeyup="return showCodeListKey('llaccidentdetail',[this,newaccidentDetailName],[0,1]);"><input class=codename name=newaccidentDetailName id=newaccidentDetailName ></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗情况</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=newcureDesc id=newcureDesc ondblclick="return showCodeList('llCureDesc',[this,newcureDescName],[0,1]);" onclick="return showCodeList('llCureDesc',[this,newcureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,newcureDescName],[0,1]);"><input class=codename name=newcureDescName id=newcureDescName readonly=true></TD>
                    <TD class= title> 出险结果1</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=newAccResult1 id=newAccResult1 ondblclick="return showCodeList('lldiseases1',[this,newAccResult1Name],[0,1],null,fm.newoccurReason.value,fm.newoccurReason.value,'1','400');" onclick="return showCodeList('lldiseases1',[this,newAccResult1Name],[0,1],null,fm.newoccurReason.value,fm.newoccurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,newAccResult1Name],[0,1],null,fm.newoccurReason.value,fm.newoccurReason.value,'1','400');" onFocus="saveIcdValue();"><input class=codename name=newAccResult1Name id=newAccResult1Name readonly=true></TD>
                    <TD class= title> 出险结果2</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=newAccResult2 id=newAccResult2 ondblclick="return showCodeList('lldiseases2',[this,newAccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1','',300);" onclick="return showCodeList('lldiseases2',[this,newAccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1','',300);" onkeyup="return showCodeListKey('lldiseases2',[this,newAccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1',300);" onFocus="saveIcdValue();"><input class=codename name=newAccResult2Name  id=newAccResult2Name readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 非正常修改标识</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=newAccDateModSign id=newAccDateModSign CodeData="0|^0|出险日期正常修改 ^1|出险日期非正常修改" ondblclick="return showCodeListEx('newAccDateModSign', [this,newAccDateModSignName],[0,1],'','','','','',100)" onclick="return showCodeListEx('newAccDateModSign', [this,newAccDateModSignName],[0,1],'','','','','',100)" onkeyup="return showCodeListKeyEx('newAccDateModSign', [this,newAccDateModSignName],[0,1],'','','','','',100);"><input class=codename name=newAccDateModSignName id=newAccDateModSignName readonly=true ></TD>
                    <TD class= title> 赔付金领取方式</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=newPayGetMode id=newPayGetMode ondblclick="return showCodeList('llcasegetmode',[this,newPayGetModeName],[0,1]);" onclick="return showCodeList('llcasegetmode',[this,newPayGetModeName],[0,1]);" onkeyup="return showCodeListKeyEx('llcasegetmode', [this,newPayGetModeName],[0,1]);"><input class=codename name=newPayGetModeName id=newPayGetModeName readonly=true></TD>
                </TR>   
  				<TR class= common>
					<TD  class= title> 申请人手机号码 </TD>
              		<TD  class= input> <Input class="wid" class= common name=newRptorMoPhone id=newRptorMoPhone verify="申请人手机号码|LEN=11" verifyorder="1" onblur="checkRptorMolength(this.value);confirmSecondInput(this,'onblur');"></TD>
					<TD  class= title> 申请人邮编 </TD>
				    <TD  class= input><Input class="wid" class=common name="newAppntZipCode" id="newAppntZipCode" elementtype=nacessary  verify="申请人邮编|NOTNULL&LEN=6" verifyorder="1" onblur="checkziplength(this.value);confirmSecondInput(this,'onblur');"><font size=1 color='#ff0000'><b>*</b></font></TD> 				
				</TR>        
				<TR  class= common>
				    <TD  class= title> 申请人详细地址 </TD>
               		<TD  class= input> <Input class="wid" class= "common3" name=newRptorAddress id=newRptorAddress ><font size=1 color='#ff0000'><b>*</b></font></TD>            
 			 	</TR>               
				<input type="hidden" name=newAccDesc >
				<input type="hidden" name=newRemark >
               </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> 理赔类型：<font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD align=left>
                 		<input style="vertical-align:middle" type="checkbox" value="02" name="newclaimType" onclick="callClaimType('02');"> <span style="vertical-align:middle">身故</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="03" name="newclaimType" onclick="callClaimType('03');"><span style="vertical-align:middle"> 高残</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="04" name="newclaimType" onclick="callClaimType('04');"> <span style="vertical-align:middle">重大疾病</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="01" name="newclaimType" onclick="callClaimType('01')"> <span style="vertical-align:middle">残疾、烧伤、骨折、重要器官切除</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="09" name="newclaimType" onclick="callClaimType('09')"> <span style="vertical-align:middle">豁免</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="00" name="newclaimType" onclick="callClaimType('00')"> <span style="vertical-align:middle">医疗</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="05" name="newclaimType" onclick="callClaimType('05')"> <span style="vertical-align:middle">特种疾病</span> </input>
                    </td>
                </TR>
            </table>

			<table class= common>
				<TR class= common>
					<TD class= title> 修改原因</TD>
				</TR>
				<TR class= common>
					<TD colspan="6" style="padding-left:16px">
					<textarea name="EditReason" id="EditReason" verify="修改备注|len<1000" verifyorder="1" cols="224" rows="4" witdh=25% class="common"></textarea><font size=1 color='#ff0000'><b>*</b></font>
					</TD>
				</TR>
			</table>
            
        </span>
    </div>


    <td style="display:none"><!--用来隐藏这个按钮-->
    	<span id="spanIsShow" style= "display: none"><INPUT class=cssButton name="ConclusionSave" VALUE=" 修改确认 "  TYPE=button onclick="saveClick()"></span>
    </td>
    <td style="display:none"><INPUT class=cssButton name="goBack" VALUE=" 返  回 "  TYPE=button onclick="top.close();"></td>
    <td style="display:none"><INPUT class=cssButton name="queryReason" VALUE="查询修改原因"  TYPE=button onclick="queryHisEditReason();"></td>
  <!--<a href="javascript:void(0);" name="ConclusionSave" class="button" onClick="saveClick();">修改确认</a>
  
  <a href="javascript:void(0);" name="queryReason" class="button" onClick="queryHisEditReason();">查询修改原因</a>-->
    
    
    <table>
         <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,HospitalQuery);"></td>
              <td class= titleImg>修改原因</td>
         </tr>
    </table>
        
   <Div  id= "HospitalQuery" style= "display:''">     
                                   
        <Table  class= common>
            <tr><td text-align: left colSpan=1>
                <span id="spanHisEditReasonGrid"></span> 
            </td></tr>
        </Table> 
        <center>   
        <INPUT class= cssbutton90 VALUE=" 首  页 " TYPE=button onclick="getFirstPage();"> 
        <INPUT class= cssbutton91 VALUE=" 上一页 " TYPE=button onclick="getPreviousPage();">                   
        <INPUT class= cssbutton92 VALUE=" 下一页 " TYPE=button onclick="getNextPage();"> 
        <INPUT class= cssbutton93 VALUE=" 尾  页 " TYPE=button onclick="getLastPage();"> </center>     
    </Div>
   <!-- <a href="javascript:void(0);" name="goBack" class="button" onClick="top.close();">返    回</a>-->
    <%
    //隐藏区,保存信息用
    %>
    <Input type=hidden name=customerNo >
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="isReportExist" name="isReportExist"><!--是否为新增事件,不判断则为空-->

	<Input type=hidden id="AccNo" name="AccNo"><!--事件号-->
    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    <input type=hidden id="isNew" name="isNew"><!--是否创建新建-->
    <input type=hidden id="clmState" name="clmState"><!--立案状态-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10代码-->
	<Input type=hidden id="BaccDate" name="BaccDate"><!--后台的事故日期日期，每次查询时更新，校验时使用-->

    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<Input type=hidden id="IsShow" name="IsShow">
    <Input type=hidden id="recalculationFlag" name="recalculationFlag" ><!--是否重算标志-->
	<Input type=hidden id="cancelMergeFlag" name="cancelMergeFlag" ><!--是否清楚案件合并信息标志-->

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
