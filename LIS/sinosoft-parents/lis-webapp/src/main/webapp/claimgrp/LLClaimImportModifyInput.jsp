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
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
      String CurrentDate = PubFun.getCurrentDate();
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");
      String tManageCom=tG.ManageCom;
      
	  String tRptNo = request.getParameter("tRptNo");	//赔案号
      loggerDebug("LLClaimImportModifyInput",tRptNo);
	  
      String tCustomerNo = request.getParameter("tAppntNo");	//客户号
      loggerDebug("LLClaimImportModifyInput",tCustomerNo);
      
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
    <SCRIPT src="LLClaimImportModify.js"></SCRIPT>
    <%@include file="LLClaimImportModifyInit.jsp"%>
</head>
<body onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
            <td class=titleImg> 出险人信息 --- 备份区</td>
        </tr>
    </table>
    <Div id= "divLLSubReport" style= "display: ''" class="maxbox">

        <!--出险人信息表单-->
        <span id="spanSubReport" >
            <table class= common>
                <TR class= common>
                    <!--出险人编码,见隐藏区-->
                    <TD class= title> 出险人姓名 </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=customerName id=customerName></TD>
                    <TD class= title> 出险人年龄 </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=customerAge id=customerAge><Input type="hidden" readonly name=customerBir></TD>
                    <TD class= title> 出险人性别 </TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled readonly name=customerSex id=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=codename name=SexName id=SexName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 社保标示</TD>
                    <TD class= input> <Input class="readonly wid" readonly name=SocialInsuFlag id=SocialInsuFlag></TD>
                    <TD class= title> 意外事故发生日期</TD>
                    <TD class= input>  <!--<input class="readonly" dateFormat="short" name="AccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD class= title> 出险原因</TD>
                    <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=occurReason id=TreatAreaName ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class=codename name=ReasonName id=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗医院</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=hospital id=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,fm.ManageCom.value,fm.ManageCom.value,null,'400');" onclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,fm.ManageCom.value,fm.ManageCom.value,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,fm.ManageCom.value,fm.ManageCom.value,null,'400');"><input class=codename name=TreatAreaName id=TreatAreaName readonly=true></TD>
                    <TD class= title> 出险日期 </TD>
                    <TD class= input>  <!--<input class="readonly" dateFormat="short" name="AccidentDate2" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate2'});" verify="有效开始日期|DATE" dateFormat="short" name=AccidentDate2 id="AccidentDate2"><span class="icon"><a onClick="laydate({elem: '#AccidentDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD class= title> 出险细节</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=accidentDetail id=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class=codename name=accidentDetailName id=accidentDetailName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗情况</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=cureDesc id=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName id=cureDescName readonly=true></TD>
                   
                    <TD class= title> 出险结果</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=AccResult2 id=AccResult2 ondblclick="return showCodeList('lldiseases3',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');"onclick="return showCodeList('lldiseases3',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');" onkeyup="return showCodeListKey('lldiseases3',[this,AccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');" onFocus="saveIcdValue();"><input class=codename name=AccResult2Name id=AccResult2Name readonly=true></TD>
                   
                    <!--<span id="spanText1" style= "display: 'none'">
                    <table> <TR class= common>-->
                    <TD class= title> 出险结果1</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=AccResult1 id=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);"onclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1]);" ><input class=codename name=AccResult1Name id=AccResult1Name readonly=true></TD>
                    <TD class= title></TD>
                    <TD class= input></TD><TD class= title></TD>
                    <TD class= input></TD></TR>
                    <!--TD class= title> 出险结果2</TD>
                    <TD class= input> <Input class=codeno disabled name=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');" onFocus="saveIcdValue();"><input class=codename name=AccResult2Name readonly=true></TD-->
                    </table>
                    </span>
                    
                    
                </TR> 
				<input type="hidden" name=AccDesc >
				<input type="hidden" name=Remark >
                <!--TR class= common>
                    <TD class= title> 死亡标识</TD>
                    <TD class= input> <Input class=codeno name=IsDead ondblclick="return showCodeList('llDieFlag',[this,IsDeadName],[0,1]);" onkeyup="return showCodeListKey('llDieFlag',[this,IsDeadName],[0,1]);"><input class=codename name=IsDeadName readonly=true></TD>
                </tr-->
            </table>

            <table class= common>
			    <TR class= common>
			      <TD class= title> 理赔类型 <font size=1 color='#ff0000'><b>*</b></font></TD>
			      <TD align=left>
			      <input style="vertical-align:middle" type="checkbox" value="02" name="claimType" onclick="callClaimType('02');">  <span style="vertical-align:middle">身故</span> </input>
			      <input style="vertical-align:middle" type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> <span style="vertical-align:middle"> 高残</span> </input> 
			      <input style="vertical-align:middle" type="checkbox" value="04" name="claimType">  <span style="vertical-align:middle">重大疾病</span> </input>
			      <input style="vertical-align:middle" type="checkbox" value="01" name="claimType">  <span style="vertical-align:middle">残疾、烧伤、骨折、重要器官切除</span> </input>
			<!--       <input type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> 豁免 </input> -->
			      <input style="vertical-align:middle" type="checkbox" value="00" name="claimType" onclick="callClaimType('00')">  <span style="vertical-align:middle">医疗</span> </input>
			      <input style="vertical-align:middle" type="checkbox" value="05" name="claimType" >  <span style="vertical-align:middle">特种疾病</span> </input>
			      <!-- <input type="checkbox" value="06" name="claimType" > 失业失能 </input> -->
			      </td>
			    </TR>
            </table>
            
        </span>
    </div>
    
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport1);"></td>
            <td class=titleImg> 出险人信息 --- 修改区</td>
        </tr>
    </table>
    <Div id= "divLLSubReport1" style= "display: ''" class="maxbox">

        <!--出险人信息表单-->
        <span id="spanSubReport" >
            <table class= common>
                <TR class= common>
                    <!--出险人编码,见隐藏区-->
                    <TD class= title> 出险人姓名 </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=newcustomerName id=newcustomerName></TD>
                    <TD class= title> 出险人年龄 </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=newcustomerAge id=newcustomerAge><Input type="hidden" readonly name=newcustomerBir id=newcustomerBir></TD>
                    <TD class= title> 出险人性别 </TD>
                    <TD class= input> <Input type=hidden name=newcustomerSex ondblclick="return showCodeList('sex',[this,newSexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,newSexName],[0,1]);"><input class="wid" class=readonly readonly name=newSexName id=newSexName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 社保标示</TD>
                    <TD class= input> <Input class="readonly wid" readonly name=newSocialInsuFlag id=newSocialInsuFlag></TD>
                    <TD class= title> 事故日期</TD>
                    <TD class= input>  <!--<input class="readonly" readonly dateFormat="short" name="newAccidentDate" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#newAccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=newAccidentDate id="newAccidentDate"><span class="icon"><a onClick="laydate({elem: '#newAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD class= title> 出险原因</TD>
                    <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=newoccurReason id=newoccurReason ondblclick="return showCodeList('lloccurreason',[this,newReasonName],[0,1]);" onclick="return showCodeList('lloccurreason',[this,newReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,newReasonName],[0,1]);" onFocus="checkInsReason()"><input class=codename name=newReasonName id=newReasonName readonly=true onFocus="checkInsReason()" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗医院</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=newhospital id=newhospital ondblclick="showHospital(this.name,'newTreatAreaName');" onclick="showHospital(this.name,'newTreatAreaName');" onkeyup="showHospital(this.name,'newTreatAreaName');"><input class=codename name=newTreatAreaNam id=newTreatAreaNamee readonly=true></TD>
                    <TD class= title> 出险日期 </TD>
                    <TD class= input>  <!--<input class="coolDatePicker" dateFormat="short" name="newAccidentDate2" ><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#newAccidentDate2'});" verify="有效开始日期|DATE" dateFormat="short" name=newAccidentDate2 id="newAccidentDate2"><span class="icon"><a onClick="laydate({elem: '#newAccidentDate2'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD class= title> 出险细节</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=newaccidentDetail id=newaccidentDetail ondblclick="showAccDetail(this.name,'newaccidentDetailName');" onclick="showAccDetail(this.name,'newaccidentDetailName');" onkeyup="return showCodeListKey('llaccidentdetail',[this,newaccidentDetailName],[0,1],null,null,null,'1','500');"><input class=codename name=newaccidentDetailName id=newaccidentDetailName ></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗情况</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=newcureDesc id=newcureDesc ondblclick="return showCodeList('llCureDesc',[this,newcureDescName],[0,1]);" onclick="return showCodeList('llCureDesc',[this,newcureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,newcureDescName],[0,1]);"><input class=codename name=newcureDescName id=newcureDescName readonly=true></TD>
                    
                    <TD class= title> 出险结果</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=newAccResult2 ondblclick="return showCodeList('lldiseases1',[this,newAccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');" onclick="return showCodeList('lldiseases1',[this,newAccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');" onkeyup="return showCodeList('lldiseases1',[this,newAccResult2Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','300');" onFocus="saveIcdValue();"><input class=codename name=newAccResult2Name id=newAccResult2Name readonly=true></TD>
                    
                    <!--<span id="spanText2" style= "display: 'none'">
                    <table>-->
                    <TD class= title> 出险结果1</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=newAccResult1 id=newAccResult1 ondblclick="return showCodeList('lldiseases1',[this,newAccResult1Name],[0,1],null,fm.newoccurReason.value,fm.newoccurReason.value,'1','400');" onclick="return showCodeList('lldiseases1',[this,newAccResult1Name],[0,1],null,fm.newoccurReason.value,fm.newoccurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,newAccResult1Name],[0,1],null,fm.newoccurReason.value,fm.newoccurReason.value,'1','400');" onFocus="saveIcdValue();"><input class=codename name=newAccResult1Name id=newAccResult1Name readonly=true></TD>
                    <TD class= title></TD>
                    <TD class= input></TD><TD class= title></TD>
                    <TD class= input></TD>
                    <!--TD class= title> 出险结果2</TD>
                    <TD class= input> <Input class=codeno name=newAccResult2 ondblclick="return showCodeList('lldiseases2',[this,newAccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1','',300);" onkeyup="return showCodeListKey('lldiseases2',[this,newAccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1',300);" onFocus="saveIcdValue();"><input class=codename name=newAccResult2Name readonly=true></TD-->
                    </table>
                    </span>
                                        
                </TR>
				<input type="hidden" name=newAccDesc >
				<input type="hidden" name=newRemark >
                <!--TR class= common>
                    <TD class= title> 死亡标识</TD>
                    <TD class= input> <Input class=codeno name=IsDead ondblclick="return showCodeList('llDieFlag',[this,IsDeadName],[0,1]);" onkeyup="return showCodeListKey('llDieFlag',[this,IsDeadName],[0,1]);"><input class=codename name=IsDeadName readonly=true></TD>
                </tr-->
            </table>

            <table class= common>
		    	<TR class= common>
			      <TD class= title> 理赔类型 <font size=1 color='#ff0000'><b>*</b></font></TD>
			      <TD align=left>
			      <input style="vertical-align:middle" type="checkbox" value="02" name="newclaimType" onclick="callClaimType('02');"> <span style="vertical-align:middle">身故</span> </input>
			      <input style="vertical-align:middle" type="checkbox" value="03" name="newclaimType" onclick="callClaimType('03');"> <span style="vertical-align:middle">高残</span> </input> 
			      <input style="vertical-align:middle" type="checkbox" value="04" name="newclaimType"> <span style="vertical-align:middle">重大疾病</span> </input>
			      <input style="vertical-align:middle" type="checkbox" value="01" name="newclaimType"> <span style="vertical-align:middle">残疾、烧伤、骨折、重要器官切除</span> </input>
			<!--       <input type="checkbox" value="09" name="newclaimType" onclick="callClaimType('09')"> 豁免 </input> -->
			      <input style="vertical-align:middle" type="checkbox" value="00" name="newclaimType" onclick="callClaimType('00')"> <span style="vertical-align:middle">医疗</span> </input>
			      <input style="vertical-align:middle" type="checkbox" value="05" name="newclaimType" > <span style="vertical-align:middle">特种疾病</span> </input>
			      <!-- <input type="checkbox" value="06" name="newclaimType" > 失业失能 </input> -->
			      </td>
			    </TR>
            </table>

			<table class= common>
				<TR class= common>
					<TD class= title> 修改备注</TD>
				</TR>
				<TR class= common>
					<TD colspan="6" style="padding-left:16px">
					<textarea name="EditReason" verify="修改备注|len<1000" verifyorder="1" cols="196" rows="4" witdh=25% class="common"></textarea><font size=1 color='#ff0000'><b>*</b></font>
					</TD>
				</TR>
			</table>
            
        </span>
    </div>

    
    <!--<INPUT class=cssButton name="ConclusionSave" VALUE=" 修改确认 "  TYPE=button onclick="saveClick()">
    <INPUT class=cssButton name="goBack" VALUE=" 返  回 "  TYPE=button onclick="top.close();">
    <INPUT class=cssButton name="queryReason" VALUE="查询修改原因"  TYPE=button onclick="queryHisEditReason();">-->
    <a href="javascript:void(0);" name="ConclusionSave" class="button" onClick="saveClick();">修改确认</a>
    <a href="javascript:void(0);" name="goBack" class="button" onClick="top.close();">返    回</a>
    <a href="javascript:void(0);" name="queryReason" class="button" onClick="queryHisEditReason();">查询修改原因</a>
        
    
    
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
        <INPUT class= cssbutton93 VALUE=" 尾  页 " TYPE=button onclick="getLastPage();">    </center>  
    </Div>
    
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
    <Input type=hidden id="recalculationFlag" name="recalculationFlag" ><!--是否重算标志-->
	<Input type=hidden id="cancelMergeFlag" name="cancelMergeFlag" ><!--是否清楚案件合并信息标志-->
	
	<Input type=hidden id="ManageCom" name="ManageCom" value=<%= tManageCom %> ><!--登陆区站前两个代码-->

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
