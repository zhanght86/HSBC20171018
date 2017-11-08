<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
    String CurrentDate = PubFun.getCurrentDate();
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");

	  String tclaimNo = request.getParameter("claimNo");	//赔案号
	  String tcaseNo = request.getParameter("caseNo");	//
	  String tcustNo = request.getParameter("custNo");	//
	  String tAccDate1 = request.getParameter("accDate");	//事故日期
	  loggerDebug("LLColQueryMedicalFeeInpInput","事故日期:"+tAccDate1);
	  String tMedAccDate = request.getParameter("medAccDate");	//医疗出险日期
	  loggerDebug("LLColQueryMedicalFeeInpInput","医疗出险日期:"+tMedAccDate);
	  String tOtherAccDate = request.getParameter("otherAccDate");	//其他出险日期
	  loggerDebug("LLColQueryMedicalFeeInpInput","其他出险日期:"+tOtherAccDate);
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
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="LLColQueryMedicalFeeInp.js"></SCRIPT>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <%@include file="LLColQueryMedicalFeeInpInit.jsp"%>
</head>
<body onload="initForm();" >
<form action="" method=post name=fm id=fm target="fraSubmit">
	<table>
	     <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMedFee);"></td>
	          <td class= titleImg>费用类型</td>
	     </tr>
	</table>
	<Div  id= "divMedFee" style= "display:''" class="maxbox1">
        <table  class= common>
            <tr  class= common>
    			<TD  class= title> 费用类型</TD>
    			<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=MainFeeType id=MainFeeType ondblclick="return showCodeList('llfeetype',[this,MainFeeName],[0,1]);" onclick="return showCodeList('llfeetype',[this,MainFeeName],[0,1]);" onkeyup="return showCodeListKey('llfeetype',[this,MainFeeName],[0,1]);" onfocus= "choiseType();"><input class=codename name=MainFeeName id=MainFeeName readonly=true></TD></TD>
    			<TD  class= title> </TD>
    			<TD  class= input> </TD>
                <TD  class= title> </TD>
                <TD  class= input> </TD>
            </tr>
        </table>
    </div>
    <!--门诊单证录入信息-->
    <Div  id= "divMedFee1" style= "display:'none'">
        
    	<table>
    	     <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,MedFeeClinicInp);"></td>
    	          <td class= titleImg>门诊单证录入信息</td>
    	     </tr>
    	</table>
    	<Div  id= "MedFeeClinicInp" style= "display:''">
    		<Table  class= common>
    		    <tr>
    		        <td text-align: left colSpan=1><span id="spanMedFeeClinicInpGrid"></span></td>
    		    </tr>
    		</Table>	    
            <table>
                <tr>
                    <td><INPUT class=cssButton type=hidden name="saveButton1" VALUE="增  加"  TYPE=button onclick="AddClick1()" ></td>
					<td><INPUT class=cssButton type=hidden name="deleteButton1" VALUE="删  除"  TYPE=button onclick="DeleteClick1()" ></td>
    				<td><INPUT class=cssButton type=hidden name="hideButton1" VALUE="隐  藏"  TYPE=button onclick="divMedFee1.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <div class="maxbox">
            <table  class= common>
                <tr class= common>
    				<TD  class= title> 账单号</TD>
    				<TD  class= input> <Input class="wid" class=common name=ClinicMainFeeNo id=ClinicMainFeeNo><font size=1 color='#ff0000'><b>*</b></font></TD>
    				<TD  class= title> 医院名称</TD>
    				<!--TD  class= input> <Input class=codeno name=ClinicHosID ondblclick="return showCodeList('commendhospital',[this,ClinicHosName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,ClinicHosName],[0,1],null,null,null,null,'400');" ><input class=codename name=ClinicHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD-->
    				<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ClinicHosID id=ClinicHosID ondblclick="showHospital(this.name,'ClinicHosName');" onclick="showHospital(this.name,'ClinicHosName');" onkeyup="return showCodeListKey('commendhospital',[this,ClinicHosName],[0,1],null,null,null,null,'400');" ><input class=codename name=ClinicHosName id=ClinicHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 费用类型</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ClinicMedFeeType id=ClinicMedFeeType  verify="费用类型|code:LLMedFeeType" ondblclick="return showCodeList('llmedfeetype',[this,ClinicMedFeeTypeName],[0,1],null,null,null,null,'150');" onclick="return showCodeList('llmedfeetype',[this,ClinicMedFeeTypeName],[0,1],null,null,null,null,'150');" onkeyup="return showCodeListKey('llmedfeetype',[this,ClinicMedFeeTypeName],[0,1],null,null,null,null,'150');"><input class=codename name=ClinicMedFeeTypeName id=ClinicMedFeeTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> 开始日期</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="开始日期|date&NOTNULL" dateFormat="short" name="ClinicStartDate" onblur="CheckDate(fm.ClinicStartDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onblur="CheckDate(fm.ClinicStartDate);" onClick="laydate({elem: '#ClinicStartDate'});" verify="开始日期|date&NOTNULL" dateFormat="short" name=ClinicStartDate id="ClinicStartDate"><span class="icon"><a onClick="laydate({elem: '#ClinicStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD  class= title> 结束日期</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="结束日期|date&NOTNULL" dateFormat="short" name="ClinicEndDate" onblur="CheckDate(fm.ClinicEndDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#ClinicEndDate'});" verify="结束日期|date&NOTNULL" onblur="CheckDate(fm.ClinicEndDate);" dateFormat="short" name=ClinicEndDate id="ClinicEndDate"><span class="icon"><a onClick="laydate({elem: '#ClinicEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD  class= title> 天数</TD>
                    <TD  class= input> <Input class="readonly wid" readonly name=ClinicDayCount1 id=ClinicDayCount1></TD>                
			   </tr>
                <tr class= common>
					<TD  class= title> 费用金额</TD>
                    <TD  class= input> <Input class="wid" class=common name=ClinicMedFeeSum id=ClinicMedFeeSum verify="费用金额|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
    			    <TD  class= title> 自费/自付金额</TD>
                    <TD  class= input> <Input class="wid" class=common name=selfMedFeeSum id=selfMedFeeSum verify="自费/自付金额|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 距离事故日期天数</TD>
                    <TD  class= input> <Input class="readonly wid" readonly name=ClinicDayCount2 id=ClinicDayCount2></TD>
                    <!-- <TD  class= title> 距离出险日期天数</TD>
                    <TD  class= input> <Input class="readonly" readonly name=ClinicDayCount3></TD> -->                
                </tr>
				<tr class = common>
					<TD  class= title> 扣减原因</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=theReason1 id=theReason1 ondblClick="showCodeList('deductreason',[this,theReasonName1],[0,1]);" onClick="showCodeList('deductreason',[this,theReasonName1],[0,1]);" onkeyup="showCodeListKey('deductreason',[this,name=theReasonName1],[0,1]);"><input class=codename name=theReasonName1 id=theReasonName1 readonly=true></TD>
				</tr>
			</table>
			<table class = common>
				<tr class = common>
                    <TD  class= title> 备注 </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> 
                        <textarea name="Remark1" id="Remark1" cols="224" rows="4" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
            </table>
    	</div>
    </div>
    <!--住院单证录入信息-->
    <Div  id= "divMedFee2" style= "display:'none'">
        
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,MedFeeInHosInp);"></td>
                <td class= titleImg>住院单证录入信息</td>
             </tr>
        </table>
        <Div  id= "MedFeeInHosInp" style= "display:''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanMedFeeInHosInpGrid"></span></td>
                </tr>
            </Table>
            <table>
                <tr>
                    <td><INPUT class=cssButton type=hidden name="saveButton2" VALUE="增  加"  TYPE=button onclick="AddClick2()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton2" VALUE="删  除"  TYPE=button onclick="DeleteClick2()" ></td>
                    <td><INPUT class=cssButton type=hidden name="hideButton2" VALUE="隐  藏"  TYPE=button onclick="divMedFee2.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <div class="maxbox">
            <table  class= common>
                <tr class= common>
    				<TD  class= title> 账单号</TD>
    				<TD  class= input> <Input class="wid" class=common name=HosMainFeeNo id=HosMainFeeNo ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 医院名称</TD>
                    <!--TD  class= input> <Input class=codeno name=InHosHosID ondblclick="return showCodeList('commendhospital',[this,InHosHosName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,InHosHosName],[0,1],null,null,null,null,'400');"><input class=codename name=InHosHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD-->
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=InHosHosID id=InHosHosID ondblclick="showHospital(this.name,'InHosHosName');" onclick="showHospital(this.name,'InHosHosName');" onkeyup="return showCodeListKey('commendhospital',[this,InHosHosName],[0,1],null,null,null,null,'400');"><input class=codename name=InHosHosName id=InHosHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 费用类型</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=InHosMedFeeType id=InHosMedFeeType  verify="费用类型|code:llhospitalfeetype" ondblclick="return showCodeList('llhospitalfeetype',[this,InHosMedFeeTypeName],[0,1],null,null,null,null,'150');" onclick="return showCodeList('llhospitalfeetype',[this,InHosMedFeeTypeName],[0,1],null,null,null,null,'150');" onkeyup="return showCodeListKey('LLMedFeeType',[this,InHosMedFeeTypeName],[0,1],null,null,null,null,'150');"><input class=codename name=InHosMedFeeTypeName id=InHosMedFeeTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> 开始日期</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="开始日期|date&NOTNULL" dateFormat="short" name="InHosStartDate" onblur="CheckDate(fm.InHosStartDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onblur="CheckDate(fm.InHosStartDate);" onClick="laydate({elem: '#InHosStartDate'});" verify="开始日期|date&NOTNULL" dateFormat="short" name=InHosStartDate id="InHosStartDate"><span class="icon"><a onClick="laydate({elem: '#InHosStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD  class= title> 结束日期</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="结束日期|date&NOTNULL" dateFormat="short" name="InHosEndDate" onblur="CheckDate(fm.InHosEndDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#InHosEndDate'});" onblur="CheckDate(fm.InHosEndDate);" verify="结束日期|date&NOTNULL" dateFormat="short" name=InHosEndDate id="InHosEndDate"><span class="icon"><a onClick="laydate({elem: '#InHosEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
					<TD  class= title> 实际住院天数</TD>
                    <TD  class= input> <Input class="readonly wid" readonly name=InHosDayCount1 id=InHosDayCount1 verify="实际住院天数|NUM&LEN<5" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD>
               </tr>
                <tr class= common>
                    <TD  class= title> 费用金额</TD>
                    <TD  class= input> <Input class="wid" class=common  name=InHosMedFeeSum id=InHosMedFeeSum verify="费用金额|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
					<TD  class= title> 自费/自付金额</TD>
                    <TD  class= input> <Input class="wid" class=common name=selfInHosFeeSum id=selfInHosFeeSum verify="自费/自付金额|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
					<TD  class= title> 距离事故日期天数</TD>
                    <TD  class= input> <Input class="readonly wid" readonly name=InHosDayCount2 id=InHosDayCount2></TD>
                    <!--<TD  class= title> 距离出险日期天数</TD>
                    <TD  class= input> <Input class="readonly" readonly name=InHosDayCount3></TD>-->
                </tr>
				<tr class = common>
					<TD  class= title> 扣减原因</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=theReason2 id=theReason2 ondblClick="showCodeList('deductreason',[this,theReasonName2],[0,1]);" onClick="showCodeList('deductreason',[this,theReasonName2],[0,1]);" onkeyup="showCodeListKey('deductreason',[this,name=theReasonName2],[0,1]);"><input class=codename name=theReasonName2 id=theReasonName2 readonly=true></TD>
				</tr>
			</table>
			<table class = common>
				<tr class = common>
                    <TD  class= title> 备注 </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> 
                        <textarea name="Remark2" id="Remark2" cols="224" rows="4" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
            </table>
        </div>
    </div>

	<!--医保费用补偿录入信息-->
    <Div  id= "divMedFee7" style= "display:'none'">
        
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,MedFeeInHosInp);"></td>
                <td class= titleImg>费用补偿录入信息</td>
             </tr>
        </table>
        <Div  id= "MedFeeCompensateInp" style= "display:''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanMedFeeCompensateInpGrid"></span></td>
                </tr>
            </Table>
            <table>
                <tr>
                    <td><INPUT class=cssButton type=hidden name="saveButton7" VALUE="增  加"  TYPE=button onclick="AddClick7()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton7" VALUE="删  除"  TYPE=button onclick="DeleteClick7()" ></td>
                    <td><INPUT class=cssButton type=hidden name="hideButton7" VALUE="隐  藏"  TYPE=button onclick="divMedFee7.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <div class="maxbox">
            <table  class= common>
                <tr class= common>
    				<TD  class= title> 账单号</TD>
    				<TD  class= input> <Input class="wid" class=common name=CompensateMainFeeNo id=CompensateMainFeeNo ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 医院名称</TD>
                    <!--TD  class= input> <Input class=codeno name=InHosHosID ondblclick="return showCodeList('commendhospital',[this,InHosHosName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,InHosHosName],[0,1],null,null,null,null,'400');"><input class=codename name=InHosHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD-->
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=MedFeeCompensateHosID id=MedFeeCompensateHosID ondblclick="showHospital(this.name,'MedFeeCompensateHosName');" onclick="showHospital(this.name,'MedFeeCompensateHosName');" onkeyup="return showCodeListKey('commendhospital',[this,MedFeeCompensateHosName],[0,1],null,null,null,null,'400');"><input class=codename name=MedFeeCompensateHosName id=MedFeeCompensateHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 费用类型</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=MedFeeCompensateType id=MedFeeCompensateType  verify="费用类型|code:llcompensatefeetype" ondblclick="return showCodeList('llcompensatefeetype',[this,MedFeeCompensateTypeName],[0,1],null,null,null,null,'260');" onclick="return showCodeList('llcompensatefeetype',[this,MedFeeCompensateTypeName],[0,1],null,null,null,null,'260');" onkeyup="return showCodeListKey('LLMedFeeType',[this,MedFeeCompensateTypeName],[0,1],null,null,null,null,'260');"><input class=codename name=MedFeeCompensateTypeName id=MedFeeCompensateTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> 开始日期</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="开始日期|date&NOTNULL" dateFormat="short" name="MedFeeCompensateStartDate" onblur="CheckDate(fm.MedFeeCompensateStartDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#MedFeeCompensateStartDate'});" onblur="CheckDate(fm.MedFeeCompensateStartDate);" verify="开始日期|date&NOTNULL" dateFormat="short" name=MedFeeCompensateStartDate id="MedFeeCompensateStartDate"><span class="icon"><a onClick="laydate({elem: '#MedFeeCompensateStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD  class= title> 结束日期</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="结束日期|date&NOTNULL" dateFormat="short" name="MedFeeCompensateEndDate" onblur="CheckDate(fm.MedFeeCompensateEndDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onblur="CheckDate(fm.MedFeeCompensateEndDate);" onClick="laydate({elem: '#MedFeeCompensateEndDate'});" verify="结束日期|date&NOTNULL" dateFormat="short" name=MedFeeCompensateEndDate id="MedFeeCompensateEndDate"><span class="icon"><a onClick="laydate({elem: '#MedFeeCompensateEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
					<TD  class= title> 天数</TD>
                    <TD  class= input> <Input class="readonly wid" readonly name=MedFeeCompensateEndDateInHosDayCount1 id=MedFeeCompensateEndDateInHosDayCount1 verify="实际住院天数|NUM&LEN<5" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD>          
                </tr>
                <tr class= common>
 					<TD  class= title> 费用金额</TD>
                    <TD  class= input> <Input class="wid" class=common  name=MedFeeCompensateSum id=MedFeeCompensateSum verify="费用金额|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>  
					<TD  class= title> 自费/自付金额</TD>
                    <TD  class= input> <Input class="wid" class=common name=selfMedFeeCompensateFeeSum id=selfMedFeeCompensateFeeSum verify="自费/自付金额|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 距离事故日期天数</TD>
                    <TD  class= input> <Input class="readonly wid" readonly name=MedFeeCompensateEndDateInHosDayCount2 id=MedFeeCompensateEndDateInHosDayCount2></TD>
                    <!-- <TD  class= title> 距离出险日期天数</TD>
                    <TD  class= input> <Input class="readonly" readonly name=MedFeeCompensateEndDateInHosDayCount3></TD>  -->
                </tr>
				<tr class = common>
					<TD  class= title> 扣减原因</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=theReason3 id=theReason3 ondblClick="showCodeList('deductreason',[this,theReasonName3],[0,1]);" onClick="showCodeList('deductreason',[this,theReasonName3],[0,1]);" onkeyup="showCodeListKey('deductreason',[this,name=theReasonName3],[0,1]);"><input class=codename name=theReasonName3 id=theReasonName3 readonly=true></TD>
				</tr>
			</table>
			<table class = common>
				<tr class = common>
                    <TD  class= title> 备注 </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> 
                        <textarea name="Remark3" id="Remark3" cols="224" rows="4" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
            </table>
        </div>
    </div>

    <!--比例给付(原伤残)-->
    <Div  id= "divMedFee3" style= "display:'none'">
        
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,MedFeeCaseInfo);"></td>
                <td class= titleImg>比例给付录入信息</td>
             </tr>
        </table>
        <Div  id= "MedFeeCaseInfo" style= "display:''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanMedFeeCaseInfoGrid"></span></td>
                </tr>
            </Table>
            <table>
                <tr>
                    <td><INPUT class=cssButton type=hidden name="saveButton3" VALUE="增  加"  TYPE=button onclick="AddClick3()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton3" VALUE="删  除"  TYPE=button onclick="DeleteClick3()" ></td>
                    <td><INPUT class=cssButton type=hidden name="hideButton3" VALUE="隐  藏"  TYPE=button onclick="divMedFee3.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                    <td><INPUT class=cssButton type=hidden name="" VALUE="伤残代码查询"  TYPE=button onclick="showDefo('DefoCode','DefoCodeName');" ></td>
                </tr>
            </table>
            <div class="maxbox1">
            <table  class= common>
                <tr class= common>
    				<TD  class= title> 比例给付类型</TD>
    				<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DefoType id=DefoType ondblClick="showCodeList('lldefotype',[this,DefoTypeName],[0,1]);" onClick="showCodeList('lldefotype',[this,DefoTypeName],[0,1]);" onkeyup="showCodeListKey('lldefotype',[this,DefoTypeName],[0,1]);"><input class=codename name=DefoTypeName id=DefoTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 比例给付级别</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DefoGrade id=DefoGrade ondblclick="respondDefoGrade();" onclick="respondDefoGrade();" onkeyup="respondDefoGrade();"><input class=codename name=DefoGradeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 比例给付代码</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DefoCode id=DefoCode ondblclick="respondDefoCode();" onkeyup="respondDefoCode();" onclick="respondDefoCode();" onkeyup="respondDefoCode();" onfocus="queryDeformityRate();"><input class=codename name=DefoCodeName id=DefoCodeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> 给付比例</TD>
                    <TD  class= input> <Input class="readonly wid" readonly  name=DeformityRate id=DeformityRate ></TD>
                    <TD  class= title> 鉴定机构</TD>
                    <TD  class= input> <Input class="common wid" name=JudgeOrganName id=JudgeOrganName ></TD>
                    <TD  class= title> 鉴定日期</TD>
                    <TD  class= input> <!--<Input class="coolDatePicker" dateFormat="short" name=JudgeDate onblur="CheckDate(fm.JudgeDate);">-->
                    <Input class="coolDatePicker" onblur="CheckDate(fm.JudgeDate);" onClick="laydate({elem: '#JudgeDate'});" verify="有效开始日期|DATE" dateFormat="short" name=JudgeDate id="JudgeDate"><span class="icon"><a onClick="laydate({elem: '#JudgeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                </tr>
            </table>
        </div>
    </div>
    <!--手术与特种疾病信息-->
    <Div id= "divMedFee4" style= "display:'none'">
        
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMedFeeOper);"></td>
                <td class= titleImg>特定手术、特种疾病与特定给付信息</td>
             </tr>
        </table>
        <Div  id= "divMedFeeOper" style= "display:''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanMedFeeOperGrid"></span></td>
                </tr>
            </Table>
            <table>
                <tr>
                    <td><INPUT class=cssButton type=hidden name="saveButton4" VALUE="增  加"  TYPE=button onclick="AddClick4()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton4" VALUE="删  除"  TYPE=button onclick="DeleteClick4()" ></td>
                    <td><INPUT class=cssButton type=hidden name="hideButton4" VALUE="隐  藏"  TYPE=button onclick="divMedFee4.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <div class="maxbox1">
            <table  class= common>
                <tr class= common>
    				<TD  class= title> 费用类型</TD>
    				<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=OperationType id=OperationType CodeData="0|3^D|特定手术^E|特种疾病^F|特定给付" ondblclick="return showCodeListEx('OperationType', [this,OperationTypeName],[0,1])" onclick="return showCodeListEx('OperationType', [this,OperationTypeName],[0,1])"onkeyup="return showCodeListKeyEx('OperationType', [this,OperationTypeName],[0,1])" onfocus="setOperType();"><Input class=codename name=OperationTypeName id=OperationTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 费用类型代码</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=OperationCode id=OperationCode ondblclick="return showCodeList(fm.llOperType.value,[this,OperationName],[0,1],null,null,null,null,'250');" onclick="return showCodeList(fm.llOperType.value,[this,OperationName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey(fm.llOperType.value,[this,OperationName],[0,1],null,null,null,null,'250');"><input class=codename name=OperationName id=OperationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <!--TD  class= title> 手术档次</TD>
                    <TD  class= input> <Input class=common name=OpLevel></TD-->
                    <TD  class= title> 费用金额</TD>
                    <TD  class= input> <input class="wid" class=common name=OpFee id=OpFee verify="金额|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD>
                </tr>
                <tr class= common>
                    <!--TD  class= title> 服务机构代码</TD>
                    <TD  class= input> <Input class=common name=UnitNo></TD-->
					<TD  class= title> 自费/自付金额</TD>
                    <TD  class= input> <Input class="wid" class=common name=selfOpFeeSum id=selfOpFeeSum verify="自费/自付金额|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 医疗机构名称</TD>
                    <TD  class= input> <input class="wid" class=common name=UnitName id=UnitName ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 确诊日期</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="开始日期|date&NOTNULL" dateFormat="short" name="DiagnoseDate" onblur="CheckDate(fm.DiagnoseDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#DiagnoseDate'});" onblur="CheckDate(fm.DiagnoseDate);" verify="开始日期|date&NOTNULL" dateFormat="short" name=DiagnoseDate id="DiagnoseDate"><span class="icon"><a onClick="laydate({elem: '#DiagnoseDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD  class= title> </TD>
                    <TD  class= input> </TD>
                </tr>
            </table>
        </div>
    </div>
    <!--其他信息-->
    <Div  id= "divMedFee5" style= "display:'none'">
        
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMedFeeOther);"></td>
                <td class= titleImg>特种费用</td>
             </tr>
        </table>
        <Div  id= "divMedFeeOther" style= "display:''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanMedFeeOtherGrid"></span></td>
                </tr>
            </Table>
            <table>
                <tr>
                    <td><INPUT class=cssButton type=hidden name="saveButton5" VALUE="增  加"  TYPE=button onclick="AddClick5()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton5" VALUE="删  除"  TYPE=button onclick="DeleteClick5()" ></td>
                    <td><INPUT class=cssButton type=hidden name="hideButton5" VALUE="隐  藏"  TYPE=button onclick="divMedFee5.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <div class="maxbox">
            <table  class= common>
                <tr class= common>
    				<TD  class= title> 特种费用类型</TD>
    				<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=FactorType id=FactorType ondblclick="return showCodeList('llfactype',[this,FactorTypeName],[0,1],null,null,null,null,'250');" onclick="return showCodeList('llfactype',[this,FactorTypeName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey('llfactype',[this,FactorTypeName],[0,1],null,null,null,null,'250');" onfocus="setFactorType();"><input class=codename name=FactorTypeName id=FactorTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 特种费用代码</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=FactorCode id=FactorCode ondblclick="return showCodeList(fm.llFactorType.value,[this,FactorName],[0,1],null,null,null,null,'250');" onclick="return showCodeList(fm.llFactorType.value,[this,FactorName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey(fm.llFactorType.value,[this,FactorName],[0,1],null,null,null,null,'250');"><input class=codename name=FactorName id=FactorName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 特种费用金额</TD>
                    <TD  class= input> <Input class="wid" class=common  name=FactorValue id=FactorValue verify="费用金额|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
    				<TD  class= title> 自费/自付金额</TD>
                    <TD  class= input> <Input class="wid" class=common name=selfFactorFeeSum id=selfFactorFeeSum verify="自费/自付金额|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 起始日期</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="起始日期|date&NOTNULL" dateFormat="short" name="FactorStateDate" onblur="CheckDate(fm.FactorStateDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#FactorStateDate'});" onblur="CheckDate(fm.FactorStateDate);" verify="起始日期|date&NOTNULL" dateFormat="short" name=FactorStateDate id="FactorStateDate"><span class="icon"><a onClick="laydate({elem: '#FactorStateDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD  class= title> 结束日期</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="结束日期|date&NOTNULL" dateFormat="short" name="FactorEndDate" onblur="CheckDate(fm.FactorEndDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#FactorEndDate'});" onblur="CheckDate(fm.FactorEndDate);" verify="结束日期|date&NOTNULL" dateFormat="short" name=FactorEndDate id="FactorEndDate"><span class="icon"><a onClick="laydate({elem: '#FactorEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                </tr>
                <tr class= common>
    				<TD  class= title> 服务机构名称</TD>
    				<TD  class= input> <Input class="wid" class=common name=FactorUnitName id=FactorUnitName><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
					<TD  class= title> 扣减原因</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=theReason4 id=theReason4 ondblClick="showCodeList('deductreason',[this,theReasonName4],[0,1]);" onClick="showCodeList('deductreason',[this,theReasonName4],[0,1]);" onkeyup="showCodeListKey('deductreason',[this,name=theReasonName4],[0,1]);"><input class=codename name=theReasonName4 id=theReasonName4 readonly=true></TD>
				</tr>
			</table>
			<table class = common>
				<tr class = common>
                    <TD  class= title> 备注 </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> 
                        <textarea name="Remark4" id="Remark4" cols="224" rows="4" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
            </table>
        </div>
    </div>
    <!--社保第三方给付-->
    <Div  id= "divMedFee6" style= "display:'none'">
        
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMedFeeThree);"></td>
                <td class= titleImg>社保第三方给付</td>
             </tr>
        </table>
        <Div  id= "divMedFeeThree" style= "display:''">
            <Table  class= common>
                <tr>
                    <td text-align: left colSpan=1><span id="spanMedFeeThreeGrid"></span></td>
                </tr>
            </Table>
            <table>
                <tr>
                    <td><INPUT class=cssButton type=hidden name="saveButton6" VALUE="增  加"  TYPE=button onclick="AddClick6()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton6" VALUE="删  除"  TYPE=button onclick="DeleteClick6()" ></td>
                    <td><INPUT class=cssButton type=hidden name="hideButton6" VALUE="隐  藏"  TYPE=button onclick="divMedFee6.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <div class="maxbox1">
            <table  class= common>
                <tr class= common>
    				<TD  class= title> 费用类型</TD>
    				<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=FeeThreeType id=FeeThreeType ondblclick="return showCodeList('llsoctype',[this,FeeThreeTypeName],[0,1],null,null,null,null,'250');" onclick="return showCodeList('llsoctype',[this,FeeThreeTypeName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey('llsoctype',[this,FeeThreeTypeName],[0,1],null,null,null,null,'250');"><input class=codename name=FeeThreeTypeName id=FeeThreeTypeName readonly=true ></TD>
                    <TD  class= title> 费用代码</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=FeeThreeCode id=FeeThreeCode ondblclick="return showCodeList('llfactypemed',[this,FeeThreeName],[0,1],null,null,null,null,'250');"onclick="return showCodeList('llfactypemed',[this,FeeThreeName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey('llfactypemed',[this,FeeThreeName],[0,1],null,null,null,null,'250');"><input class=codename name=FeeThreeName id=FeeThreeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 费用金额</TD>
                    <TD  class= input> <Input class="wid" class=common name=FeeThreeValue id=FeeThreeValue verify="费用金额|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD>
                </tr>
                <tr class= common>
    				<TD  class= title> 服务机构名称</TD>
    				<TD  class= input> <Input class="wid" class=common name=FeeThreeUnitName id=FeeThreeUnitName><font size=1 color='#ff0000'><b>*</b></font></TD>
    				<TD  class= title> </TD>
    				<TD  class= input> </TD>
     				<TD  class= title> </TD>
    				<TD  class= input> </TD>
    				<!--TD  class= title> 起始日期</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="起始日期|date&NOTNULL" dateFormat="short" name="FeeThreeStateDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 结束日期</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="结束日期|date&NOTNULL" dateFormat="short" name="FeeThreeEndDate" ><font size=1 color='#ff0000'><b>*</b></font></TD-->
                </tr>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> 备注 </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> 
                        <textarea name="AdjRemark" id="AdjRemark" cols="224" rows="4" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
            </table>
        </div>
    </div>
    
    <!--<INPUT class=cssButton VALUE="查看全部" TYPE=button onclick="showAll();">
    <INPUT class=cssButton VALUE="隐藏全部" TYPE=button onclick="showNone();">
    <INPUT class=cssButton VALUE="返  回" TYPE=button onclick="top.close();">-->
    <br>
    <a href="javascript:void(0);" class="button" onClick="showAll();">查看全部</a>
    <a href="javascript:void(0);" class="button" onClick="showNone();">隐藏全部</a>
    <a href="javascript:void(0);" class="button" onClick="top.close();">返    回</a>
    
    <!--隐藏表单域,保存信息用-->
    <input type=hidden name=ClinicHosGrade>
    <input type=hidden name=InHosHosGrade>
    
    <input type=hidden name=claimNo value=''>
    <input type=hidden name=caseNo value=''>
    <input type=hidden name=custNo value=''>
    <input type=hidden name=custName value=''>
    <input type=hidden name=custSex value=''>
    
    <input type=hidden name=feeDetailNo value=''><!--门诊医院账单费用明细号-->  
    <input type=hidden name=SerialNo3 value=''><!--伤残序号-->
    <input type=hidden name=SerialNo4 value=''><!--手术序号-->
    <input type=hidden name=SerialNo5 value=''><!--其他序号-->
    <input type=hidden name=SerialNo6 value=''><!--社保第三方给付序号-->
    <Input type=hidden name=AppDeformityRate><!--申请给付比例-->
    <Input type=hidden name=RealRate><!--实际给付比例-->
    
    <input type=hidden name=accDate value=''>
    <input type=hidden name=medAccDate value=''> <!--医疗出险日期-->
	<input type=hidden name=otherAccDate value=''> <!--其他出险日期-->
    <input type=hidden name=llOperType value='lloperationtype'>
    <input type=hidden name=llFactorType value=''>
    <input type=hidden name=DealFlag value=''><!--开始日期是否早于出险日期,0是1不是-->
    
    <input type=hidden name=hideOperate value=''>
    <input type=hidden name=currentInput value=''><!--当前操作区域-->
    <input type=hidden id="fmtransact" name="fmtransact">
    
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
