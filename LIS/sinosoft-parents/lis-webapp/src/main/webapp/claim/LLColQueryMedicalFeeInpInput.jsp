<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
    String CurrentDate = PubFun.getCurrentDate();
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");

	  String tclaimNo = request.getParameter("claimNo");	//�ⰸ��
	  String tcaseNo = request.getParameter("caseNo");	//
	  String tcustNo = request.getParameter("custNo");	//
	  String tAccDate1 = request.getParameter("accDate");	//�¹�����
	  loggerDebug("LLColQueryMedicalFeeInpInput","�¹�����:"+tAccDate1);
	  String tMedAccDate = request.getParameter("medAccDate");	//ҽ�Ƴ�������
	  loggerDebug("LLColQueryMedicalFeeInpInput","ҽ�Ƴ�������:"+tMedAccDate);
	  String tOtherAccDate = request.getParameter("otherAccDate");	//������������
	  loggerDebug("LLColQueryMedicalFeeInpInput","������������:"+tOtherAccDate);
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
	          <td class= titleImg>��������</td>
	     </tr>
	</table>
	<Div  id= "divMedFee" style= "display:''" class="maxbox1">
        <table  class= common>
            <tr  class= common>
    			<TD  class= title> ��������</TD>
    			<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=MainFeeType id=MainFeeType ondblclick="return showCodeList('llfeetype',[this,MainFeeName],[0,1]);" onclick="return showCodeList('llfeetype',[this,MainFeeName],[0,1]);" onkeyup="return showCodeListKey('llfeetype',[this,MainFeeName],[0,1]);" onfocus= "choiseType();"><input class=codename name=MainFeeName id=MainFeeName readonly=true></TD></TD>
    			<TD  class= title> </TD>
    			<TD  class= input> </TD>
                <TD  class= title> </TD>
                <TD  class= input> </TD>
            </tr>
        </table>
    </div>
    <!--���ﵥ֤¼����Ϣ-->
    <Div  id= "divMedFee1" style= "display:'none'">
        
    	<table>
    	     <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,MedFeeClinicInp);"></td>
    	          <td class= titleImg>���ﵥ֤¼����Ϣ</td>
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
                    <td><INPUT class=cssButton type=hidden name="saveButton1" VALUE="��  ��"  TYPE=button onclick="AddClick1()" ></td>
					<td><INPUT class=cssButton type=hidden name="deleteButton1" VALUE="ɾ  ��"  TYPE=button onclick="DeleteClick1()" ></td>
    				<td><INPUT class=cssButton type=hidden name="hideButton1" VALUE="��  ��"  TYPE=button onclick="divMedFee1.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <div class="maxbox">
            <table  class= common>
                <tr class= common>
    				<TD  class= title> �˵���</TD>
    				<TD  class= input> <Input class="wid" class=common name=ClinicMainFeeNo id=ClinicMainFeeNo><font size=1 color='#ff0000'><b>*</b></font></TD>
    				<TD  class= title> ҽԺ����</TD>
    				<!--TD  class= input> <Input class=codeno name=ClinicHosID ondblclick="return showCodeList('commendhospital',[this,ClinicHosName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,ClinicHosName],[0,1],null,null,null,null,'400');" ><input class=codename name=ClinicHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD-->
    				<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ClinicHosID id=ClinicHosID ondblclick="showHospital(this.name,'ClinicHosName');" onclick="showHospital(this.name,'ClinicHosName');" onkeyup="return showCodeListKey('commendhospital',[this,ClinicHosName],[0,1],null,null,null,null,'400');" ><input class=codename name=ClinicHosName id=ClinicHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ClinicMedFeeType id=ClinicMedFeeType  verify="��������|code:LLMedFeeType" ondblclick="return showCodeList('llmedfeetype',[this,ClinicMedFeeTypeName],[0,1],null,null,null,null,'150');" onclick="return showCodeList('llmedfeetype',[this,ClinicMedFeeTypeName],[0,1],null,null,null,null,'150');" onkeyup="return showCodeListKey('llmedfeetype',[this,ClinicMedFeeTypeName],[0,1],null,null,null,null,'150');"><input class=codename name=ClinicMedFeeTypeName id=ClinicMedFeeTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> ��ʼ����</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="��ʼ����|date&NOTNULL" dateFormat="short" name="ClinicStartDate" onblur="CheckDate(fm.ClinicStartDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onblur="CheckDate(fm.ClinicStartDate);" onClick="laydate({elem: '#ClinicStartDate'});" verify="��ʼ����|date&NOTNULL" dateFormat="short" name=ClinicStartDate id="ClinicStartDate"><span class="icon"><a onClick="laydate({elem: '#ClinicStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="��������|date&NOTNULL" dateFormat="short" name="ClinicEndDate" onblur="CheckDate(fm.ClinicEndDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#ClinicEndDate'});" verify="��������|date&NOTNULL" onblur="CheckDate(fm.ClinicEndDate);" dateFormat="short" name=ClinicEndDate id="ClinicEndDate"><span class="icon"><a onClick="laydate({elem: '#ClinicEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD  class= title> ����</TD>
                    <TD  class= input> <Input class="readonly wid" readonly name=ClinicDayCount1 id=ClinicDayCount1></TD>                
			   </tr>
                <tr class= common>
					<TD  class= title> ���ý��</TD>
                    <TD  class= input> <Input class="wid" class=common name=ClinicMedFeeSum id=ClinicMedFeeSum verify="���ý��|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
    			    <TD  class= title> �Է�/�Ը����</TD>
                    <TD  class= input> <Input class="wid" class=common name=selfMedFeeSum id=selfMedFeeSum verify="�Է�/�Ը����|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> �����¹���������</TD>
                    <TD  class= input> <Input class="readonly wid" readonly name=ClinicDayCount2 id=ClinicDayCount2></TD>
                    <!-- <TD  class= title> ���������������</TD>
                    <TD  class= input> <Input class="readonly" readonly name=ClinicDayCount3></TD> -->                
                </tr>
				<tr class = common>
					<TD  class= title> �ۼ�ԭ��</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=theReason1 id=theReason1 ondblClick="showCodeList('deductreason',[this,theReasonName1],[0,1]);" onClick="showCodeList('deductreason',[this,theReasonName1],[0,1]);" onkeyup="showCodeListKey('deductreason',[this,name=theReasonName1],[0,1]);"><input class=codename name=theReasonName1 id=theReasonName1 readonly=true></TD>
				</tr>
			</table>
			<table class = common>
				<tr class = common>
                    <TD  class= title> ��ע </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> 
                        <textarea name="Remark1" id="Remark1" cols="224" rows="4" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
            </table>
    	</div>
    </div>
    <!--סԺ��֤¼����Ϣ-->
    <Div  id= "divMedFee2" style= "display:'none'">
        
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,MedFeeInHosInp);"></td>
                <td class= titleImg>סԺ��֤¼����Ϣ</td>
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
                    <td><INPUT class=cssButton type=hidden name="saveButton2" VALUE="��  ��"  TYPE=button onclick="AddClick2()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton2" VALUE="ɾ  ��"  TYPE=button onclick="DeleteClick2()" ></td>
                    <td><INPUT class=cssButton type=hidden name="hideButton2" VALUE="��  ��"  TYPE=button onclick="divMedFee2.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <div class="maxbox">
            <table  class= common>
                <tr class= common>
    				<TD  class= title> �˵���</TD>
    				<TD  class= input> <Input class="wid" class=common name=HosMainFeeNo id=HosMainFeeNo ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ҽԺ����</TD>
                    <!--TD  class= input> <Input class=codeno name=InHosHosID ondblclick="return showCodeList('commendhospital',[this,InHosHosName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,InHosHosName],[0,1],null,null,null,null,'400');"><input class=codename name=InHosHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD-->
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=InHosHosID id=InHosHosID ondblclick="showHospital(this.name,'InHosHosName');" onclick="showHospital(this.name,'InHosHosName');" onkeyup="return showCodeListKey('commendhospital',[this,InHosHosName],[0,1],null,null,null,null,'400');"><input class=codename name=InHosHosName id=InHosHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=InHosMedFeeType id=InHosMedFeeType  verify="��������|code:llhospitalfeetype" ondblclick="return showCodeList('llhospitalfeetype',[this,InHosMedFeeTypeName],[0,1],null,null,null,null,'150');" onclick="return showCodeList('llhospitalfeetype',[this,InHosMedFeeTypeName],[0,1],null,null,null,null,'150');" onkeyup="return showCodeListKey('LLMedFeeType',[this,InHosMedFeeTypeName],[0,1],null,null,null,null,'150');"><input class=codename name=InHosMedFeeTypeName id=InHosMedFeeTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> ��ʼ����</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="��ʼ����|date&NOTNULL" dateFormat="short" name="InHosStartDate" onblur="CheckDate(fm.InHosStartDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onblur="CheckDate(fm.InHosStartDate);" onClick="laydate({elem: '#InHosStartDate'});" verify="��ʼ����|date&NOTNULL" dateFormat="short" name=InHosStartDate id="InHosStartDate"><span class="icon"><a onClick="laydate({elem: '#InHosStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="��������|date&NOTNULL" dateFormat="short" name="InHosEndDate" onblur="CheckDate(fm.InHosEndDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#InHosEndDate'});" onblur="CheckDate(fm.InHosEndDate);" verify="��������|date&NOTNULL" dateFormat="short" name=InHosEndDate id="InHosEndDate"><span class="icon"><a onClick="laydate({elem: '#InHosEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
					<TD  class= title> ʵ��סԺ����</TD>
                    <TD  class= input> <Input class="readonly wid" readonly name=InHosDayCount1 id=InHosDayCount1 verify="ʵ��סԺ����|NUM&LEN<5" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD>
               </tr>
                <tr class= common>
                    <TD  class= title> ���ý��</TD>
                    <TD  class= input> <Input class="wid" class=common  name=InHosMedFeeSum id=InHosMedFeeSum verify="���ý��|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
					<TD  class= title> �Է�/�Ը����</TD>
                    <TD  class= input> <Input class="wid" class=common name=selfInHosFeeSum id=selfInHosFeeSum verify="�Է�/�Ը����|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
					<TD  class= title> �����¹���������</TD>
                    <TD  class= input> <Input class="readonly wid" readonly name=InHosDayCount2 id=InHosDayCount2></TD>
                    <!--<TD  class= title> ���������������</TD>
                    <TD  class= input> <Input class="readonly" readonly name=InHosDayCount3></TD>-->
                </tr>
				<tr class = common>
					<TD  class= title> �ۼ�ԭ��</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=theReason2 id=theReason2 ondblClick="showCodeList('deductreason',[this,theReasonName2],[0,1]);" onClick="showCodeList('deductreason',[this,theReasonName2],[0,1]);" onkeyup="showCodeListKey('deductreason',[this,name=theReasonName2],[0,1]);"><input class=codename name=theReasonName2 id=theReasonName2 readonly=true></TD>
				</tr>
			</table>
			<table class = common>
				<tr class = common>
                    <TD  class= title> ��ע </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> 
                        <textarea name="Remark2" id="Remark2" cols="224" rows="4" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
            </table>
        </div>
    </div>

	<!--ҽ�����ò���¼����Ϣ-->
    <Div  id= "divMedFee7" style= "display:'none'">
        
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,MedFeeInHosInp);"></td>
                <td class= titleImg>���ò���¼����Ϣ</td>
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
                    <td><INPUT class=cssButton type=hidden name="saveButton7" VALUE="��  ��"  TYPE=button onclick="AddClick7()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton7" VALUE="ɾ  ��"  TYPE=button onclick="DeleteClick7()" ></td>
                    <td><INPUT class=cssButton type=hidden name="hideButton7" VALUE="��  ��"  TYPE=button onclick="divMedFee7.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <div class="maxbox">
            <table  class= common>
                <tr class= common>
    				<TD  class= title> �˵���</TD>
    				<TD  class= input> <Input class="wid" class=common name=CompensateMainFeeNo id=CompensateMainFeeNo ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ҽԺ����</TD>
                    <!--TD  class= input> <Input class=codeno name=InHosHosID ondblclick="return showCodeList('commendhospital',[this,InHosHosName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,InHosHosName],[0,1],null,null,null,null,'400');"><input class=codename name=InHosHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD-->
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=MedFeeCompensateHosID id=MedFeeCompensateHosID ondblclick="showHospital(this.name,'MedFeeCompensateHosName');" onclick="showHospital(this.name,'MedFeeCompensateHosName');" onkeyup="return showCodeListKey('commendhospital',[this,MedFeeCompensateHosName],[0,1],null,null,null,null,'400');"><input class=codename name=MedFeeCompensateHosName id=MedFeeCompensateHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=MedFeeCompensateType id=MedFeeCompensateType  verify="��������|code:llcompensatefeetype" ondblclick="return showCodeList('llcompensatefeetype',[this,MedFeeCompensateTypeName],[0,1],null,null,null,null,'260');" onclick="return showCodeList('llcompensatefeetype',[this,MedFeeCompensateTypeName],[0,1],null,null,null,null,'260');" onkeyup="return showCodeListKey('LLMedFeeType',[this,MedFeeCompensateTypeName],[0,1],null,null,null,null,'260');"><input class=codename name=MedFeeCompensateTypeName id=MedFeeCompensateTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> ��ʼ����</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="��ʼ����|date&NOTNULL" dateFormat="short" name="MedFeeCompensateStartDate" onblur="CheckDate(fm.MedFeeCompensateStartDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#MedFeeCompensateStartDate'});" onblur="CheckDate(fm.MedFeeCompensateStartDate);" verify="��ʼ����|date&NOTNULL" dateFormat="short" name=MedFeeCompensateStartDate id="MedFeeCompensateStartDate"><span class="icon"><a onClick="laydate({elem: '#MedFeeCompensateStartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="��������|date&NOTNULL" dateFormat="short" name="MedFeeCompensateEndDate" onblur="CheckDate(fm.MedFeeCompensateEndDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onblur="CheckDate(fm.MedFeeCompensateEndDate);" onClick="laydate({elem: '#MedFeeCompensateEndDate'});" verify="��������|date&NOTNULL" dateFormat="short" name=MedFeeCompensateEndDate id="MedFeeCompensateEndDate"><span class="icon"><a onClick="laydate({elem: '#MedFeeCompensateEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
					<TD  class= title> ����</TD>
                    <TD  class= input> <Input class="readonly wid" readonly name=MedFeeCompensateEndDateInHosDayCount1 id=MedFeeCompensateEndDateInHosDayCount1 verify="ʵ��סԺ����|NUM&LEN<5" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD>          
                </tr>
                <tr class= common>
 					<TD  class= title> ���ý��</TD>
                    <TD  class= input> <Input class="wid" class=common  name=MedFeeCompensateSum id=MedFeeCompensateSum verify="���ý��|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>  
					<TD  class= title> �Է�/�Ը����</TD>
                    <TD  class= input> <Input class="wid" class=common name=selfMedFeeCompensateFeeSum id=selfMedFeeCompensateFeeSum verify="�Է�/�Ը����|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> �����¹���������</TD>
                    <TD  class= input> <Input class="readonly wid" readonly name=MedFeeCompensateEndDateInHosDayCount2 id=MedFeeCompensateEndDateInHosDayCount2></TD>
                    <!-- <TD  class= title> ���������������</TD>
                    <TD  class= input> <Input class="readonly" readonly name=MedFeeCompensateEndDateInHosDayCount3></TD>  -->
                </tr>
				<tr class = common>
					<TD  class= title> �ۼ�ԭ��</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=theReason3 id=theReason3 ondblClick="showCodeList('deductreason',[this,theReasonName3],[0,1]);" onClick="showCodeList('deductreason',[this,theReasonName3],[0,1]);" onkeyup="showCodeListKey('deductreason',[this,name=theReasonName3],[0,1]);"><input class=codename name=theReasonName3 id=theReasonName3 readonly=true></TD>
				</tr>
			</table>
			<table class = common>
				<tr class = common>
                    <TD  class= title> ��ע </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> 
                        <textarea name="Remark3" id="Remark3" cols="224" rows="4" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
            </table>
        </div>
    </div>

    <!--��������(ԭ�˲�)-->
    <Div  id= "divMedFee3" style= "display:'none'">
        
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,MedFeeCaseInfo);"></td>
                <td class= titleImg>��������¼����Ϣ</td>
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
                    <td><INPUT class=cssButton type=hidden name="saveButton3" VALUE="��  ��"  TYPE=button onclick="AddClick3()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton3" VALUE="ɾ  ��"  TYPE=button onclick="DeleteClick3()" ></td>
                    <td><INPUT class=cssButton type=hidden name="hideButton3" VALUE="��  ��"  TYPE=button onclick="divMedFee3.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                    <td><INPUT class=cssButton type=hidden name="" VALUE="�˲д����ѯ"  TYPE=button onclick="showDefo('DefoCode','DefoCodeName');" ></td>
                </tr>
            </table>
            <div class="maxbox1">
            <table  class= common>
                <tr class= common>
    				<TD  class= title> ������������</TD>
    				<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DefoType id=DefoType ondblClick="showCodeList('lldefotype',[this,DefoTypeName],[0,1]);" onClick="showCodeList('lldefotype',[this,DefoTypeName],[0,1]);" onkeyup="showCodeListKey('lldefotype',[this,DefoTypeName],[0,1]);"><input class=codename name=DefoTypeName id=DefoTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ������������</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DefoGrade id=DefoGrade ondblclick="respondDefoGrade();" onclick="respondDefoGrade();" onkeyup="respondDefoGrade();"><input class=codename name=DefoGradeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ������������</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=DefoCode id=DefoCode ondblclick="respondDefoCode();" onkeyup="respondDefoCode();" onclick="respondDefoCode();" onkeyup="respondDefoCode();" onfocus="queryDeformityRate();"><input class=codename name=DefoCodeName id=DefoCodeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <Input class="readonly wid" readonly  name=DeformityRate id=DeformityRate ></TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <Input class="common wid" name=JudgeOrganName id=JudgeOrganName ></TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <!--<Input class="coolDatePicker" dateFormat="short" name=JudgeDate onblur="CheckDate(fm.JudgeDate);">-->
                    <Input class="coolDatePicker" onblur="CheckDate(fm.JudgeDate);" onClick="laydate({elem: '#JudgeDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=JudgeDate id="JudgeDate"><span class="icon"><a onClick="laydate({elem: '#JudgeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                </tr>
            </table>
        </div>
    </div>
    <!--���������ּ�����Ϣ-->
    <Div id= "divMedFee4" style= "display:'none'">
        
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMedFeeOper);"></td>
                <td class= titleImg>�ض����������ּ������ض�������Ϣ</td>
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
                    <td><INPUT class=cssButton type=hidden name="saveButton4" VALUE="��  ��"  TYPE=button onclick="AddClick4()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton4" VALUE="ɾ  ��"  TYPE=button onclick="DeleteClick4()" ></td>
                    <td><INPUT class=cssButton type=hidden name="hideButton4" VALUE="��  ��"  TYPE=button onclick="divMedFee4.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <div class="maxbox1">
            <table  class= common>
                <tr class= common>
    				<TD  class= title> ��������</TD>
    				<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=OperationType id=OperationType CodeData="0|3^D|�ض�����^E|���ּ���^F|�ض�����" ondblclick="return showCodeListEx('OperationType', [this,OperationTypeName],[0,1])" onclick="return showCodeListEx('OperationType', [this,OperationTypeName],[0,1])"onkeyup="return showCodeListKeyEx('OperationType', [this,OperationTypeName],[0,1])" onfocus="setOperType();"><Input class=codename name=OperationTypeName id=OperationTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> �������ʹ���</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=OperationCode id=OperationCode ondblclick="return showCodeList(fm.llOperType.value,[this,OperationName],[0,1],null,null,null,null,'250');" onclick="return showCodeList(fm.llOperType.value,[this,OperationName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey(fm.llOperType.value,[this,OperationName],[0,1],null,null,null,null,'250');"><input class=codename name=OperationName id=OperationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <!--TD  class= title> ��������</TD>
                    <TD  class= input> <Input class=common name=OpLevel></TD-->
                    <TD  class= title> ���ý��</TD>
                    <TD  class= input> <input class="wid" class=common name=OpFee id=OpFee verify="���|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD>
                </tr>
                <tr class= common>
                    <!--TD  class= title> �����������</TD>
                    <TD  class= input> <Input class=common name=UnitNo></TD-->
					<TD  class= title> �Է�/�Ը����</TD>
                    <TD  class= input> <Input class="wid" class=common name=selfOpFeeSum id=selfOpFeeSum verify="�Է�/�Ը����|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ҽ�ƻ�������</TD>
                    <TD  class= input> <input class="wid" class=common name=UnitName id=UnitName ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ȷ������</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="��ʼ����|date&NOTNULL" dateFormat="short" name="DiagnoseDate" onblur="CheckDate(fm.DiagnoseDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#DiagnoseDate'});" onblur="CheckDate(fm.DiagnoseDate);" verify="��ʼ����|date&NOTNULL" dateFormat="short" name=DiagnoseDate id="DiagnoseDate"><span class="icon"><a onClick="laydate({elem: '#DiagnoseDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD  class= title> </TD>
                    <TD  class= input> </TD>
                </tr>
            </table>
        </div>
    </div>
    <!--������Ϣ-->
    <Div  id= "divMedFee5" style= "display:'none'">
        
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMedFeeOther);"></td>
                <td class= titleImg>���ַ���</td>
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
                    <td><INPUT class=cssButton type=hidden name="saveButton5" VALUE="��  ��"  TYPE=button onclick="AddClick5()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton5" VALUE="ɾ  ��"  TYPE=button onclick="DeleteClick5()" ></td>
                    <td><INPUT class=cssButton type=hidden name="hideButton5" VALUE="��  ��"  TYPE=button onclick="divMedFee5.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <div class="maxbox">
            <table  class= common>
                <tr class= common>
    				<TD  class= title> ���ַ�������</TD>
    				<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=FactorType id=FactorType ondblclick="return showCodeList('llfactype',[this,FactorTypeName],[0,1],null,null,null,null,'250');" onclick="return showCodeList('llfactype',[this,FactorTypeName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey('llfactype',[this,FactorTypeName],[0,1],null,null,null,null,'250');" onfocus="setFactorType();"><input class=codename name=FactorTypeName id=FactorTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ���ַ��ô���</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=FactorCode id=FactorCode ondblclick="return showCodeList(fm.llFactorType.value,[this,FactorName],[0,1],null,null,null,null,'250');" onclick="return showCodeList(fm.llFactorType.value,[this,FactorName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey(fm.llFactorType.value,[this,FactorName],[0,1],null,null,null,null,'250');"><input class=codename name=FactorName id=FactorName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ���ַ��ý��</TD>
                    <TD  class= input> <Input class="wid" class=common  name=FactorValue id=FactorValue verify="���ý��|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
    				<TD  class= title> �Է�/�Ը����</TD>
                    <TD  class= input> <Input class="wid" class=common name=selfFactorFeeSum id=selfFactorFeeSum verify="�Է�/�Ը����|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ��ʼ����</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="��ʼ����|date&NOTNULL" dateFormat="short" name="FactorStateDate" onblur="CheckDate(fm.FactorStateDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#FactorStateDate'});" onblur="CheckDate(fm.FactorStateDate);" verify="��ʼ����|date&NOTNULL" dateFormat="short" name=FactorStateDate id="FactorStateDate"><span class="icon"><a onClick="laydate({elem: '#FactorStateDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <!--<input class="coolDatePicker" verify="��������|date&NOTNULL" dateFormat="short" name="FactorEndDate" onblur="CheckDate(fm.FactorEndDate);"><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#FactorEndDate'});" onblur="CheckDate(fm.FactorEndDate);" verify="��������|date&NOTNULL" dateFormat="short" name=FactorEndDate id="FactorEndDate"><span class="icon"><a onClick="laydate({elem: '#FactorEndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>
                </tr>
                <tr class= common>
    				<TD  class= title> �����������</TD>
    				<TD  class= input> <Input class="wid" class=common name=FactorUnitName id=FactorUnitName><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
					<TD  class= title> �ۼ�ԭ��</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=theReason4 id=theReason4 ondblClick="showCodeList('deductreason',[this,theReasonName4],[0,1]);" onClick="showCodeList('deductreason',[this,theReasonName4],[0,1]);" onkeyup="showCodeListKey('deductreason',[this,name=theReasonName4],[0,1]);"><input class=codename name=theReasonName4 id=theReasonName4 readonly=true></TD>
				</tr>
			</table>
			<table class = common>
				<tr class = common>
                    <TD  class= title> ��ע </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> 
                        <textarea name="Remark4" id="Remark4" cols="224" rows="4" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
            </table>
        </div>
    </div>
    <!--�籣����������-->
    <Div  id= "divMedFee6" style= "display:'none'">
        
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMedFeeThree);"></td>
                <td class= titleImg>�籣����������</td>
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
                    <td><INPUT class=cssButton type=hidden name="saveButton6" VALUE="��  ��"  TYPE=button onclick="AddClick6()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton6" VALUE="ɾ  ��"  TYPE=button onclick="DeleteClick6()" ></td>
                    <td><INPUT class=cssButton type=hidden name="hideButton6" VALUE="��  ��"  TYPE=button onclick="divMedFee6.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <div class="maxbox1">
            <table  class= common>
                <tr class= common>
    				<TD  class= title> ��������</TD>
    				<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=FeeThreeType id=FeeThreeType ondblclick="return showCodeList('llsoctype',[this,FeeThreeTypeName],[0,1],null,null,null,null,'250');" onclick="return showCodeList('llsoctype',[this,FeeThreeTypeName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey('llsoctype',[this,FeeThreeTypeName],[0,1],null,null,null,null,'250');"><input class=codename name=FeeThreeTypeName id=FeeThreeTypeName readonly=true ></TD>
                    <TD  class= title> ���ô���</TD>
                    <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=FeeThreeCode id=FeeThreeCode ondblclick="return showCodeList('llfactypemed',[this,FeeThreeName],[0,1],null,null,null,null,'250');"onclick="return showCodeList('llfactypemed',[this,FeeThreeName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey('llfactypemed',[this,FeeThreeName],[0,1],null,null,null,null,'250');"><input class=codename name=FeeThreeName id=FeeThreeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ���ý��</TD>
                    <TD  class= input> <Input class="wid" class=common name=FeeThreeValue id=FeeThreeValue verify="���ý��|NUM&LEN<10" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD>
                </tr>
                <tr class= common>
    				<TD  class= title> �����������</TD>
    				<TD  class= input> <Input class="wid" class=common name=FeeThreeUnitName id=FeeThreeUnitName><font size=1 color='#ff0000'><b>*</b></font></TD>
    				<TD  class= title> </TD>
    				<TD  class= input> </TD>
     				<TD  class= title> </TD>
    				<TD  class= input> </TD>
    				<!--TD  class= title> ��ʼ����</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="��ʼ����|date&NOTNULL" dateFormat="short" name="FeeThreeStateDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="��������|date&NOTNULL" dateFormat="short" name="FeeThreeEndDate" ><font size=1 color='#ff0000'><b>*</b></font></TD-->
                </tr>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> ��ע </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> 
                        <textarea name="AdjRemark" id="AdjRemark" cols="224" rows="4" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
            </table>
        </div>
    </div>
    
    <!--<INPUT class=cssButton VALUE="�鿴ȫ��" TYPE=button onclick="showAll();">
    <INPUT class=cssButton VALUE="����ȫ��" TYPE=button onclick="showNone();">
    <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="top.close();">-->
    <br>
    <a href="javascript:void(0);" class="button" onClick="showAll();">�鿴ȫ��</a>
    <a href="javascript:void(0);" class="button" onClick="showNone();">����ȫ��</a>
    <a href="javascript:void(0);" class="button" onClick="top.close();">��    ��</a>
    
    <!--���ر���,������Ϣ��-->
    <input type=hidden name=ClinicHosGrade>
    <input type=hidden name=InHosHosGrade>
    
    <input type=hidden name=claimNo value=''>
    <input type=hidden name=caseNo value=''>
    <input type=hidden name=custNo value=''>
    <input type=hidden name=custName value=''>
    <input type=hidden name=custSex value=''>
    
    <input type=hidden name=feeDetailNo value=''><!--����ҽԺ�˵�������ϸ��-->  
    <input type=hidden name=SerialNo3 value=''><!--�˲����-->
    <input type=hidden name=SerialNo4 value=''><!--�������-->
    <input type=hidden name=SerialNo5 value=''><!--�������-->
    <input type=hidden name=SerialNo6 value=''><!--�籣�������������-->
    <Input type=hidden name=AppDeformityRate><!--�����������-->
    <Input type=hidden name=RealRate><!--ʵ�ʸ�������-->
    
    <input type=hidden name=accDate value=''>
    <input type=hidden name=medAccDate value=''> <!--ҽ�Ƴ�������-->
	<input type=hidden name=otherAccDate value=''> <!--������������-->
    <input type=hidden name=llOperType value='lloperationtype'>
    <input type=hidden name=llFactorType value=''>
    <input type=hidden name=DealFlag value=''><!--��ʼ�����Ƿ����ڳ�������,0��1����-->
    
    <input type=hidden name=hideOperate value=''>
    <input type=hidden name=currentInput value=''><!--��ǰ��������-->
    <input type=hidden id="fmtransact" name="fmtransact">
    
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
