<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
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

	  String tclaimNo = request.getParameter("claimNo");	//�ⰸ��
	  String tcaseNo = request.getParameter("caseNo");	//
	  String tcustNo = request.getParameter("custNo");	//
	  String taccDate1 = request.getParameter("accDate1");	//
	  String taccDate2 = request.getParameter("accDate2");	//
	  String GrpFlag = request.getParameter("GrpFlag");	//
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
    <SCRIPT src="LLColQueryMedicalFeeInp.js"></SCRIPT>
    <%@include file="LLColQueryMedicalFeeInpInit.jsp"%>
</head>
<body onload="initForm();" >
<form action="" method=post name=fm target="fraSubmit">
    <br>
	<table>
	     <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMedFee);"></td>
	          <td class= titleImg>��������</td>
	     </tr>
	</table>
	<Div  id= "divMedFee" style= "display:''">
        <table  class= common>
            <tr  class= common>
    			<TD  class= title> ��������</TD>
    			<TD  class= input> <Input class=codeno name=MainFeeType ondblclick="return showCodeList('llfeetype',[this,MainFeeName],[0,1],null,'1 and code <> #C#',1,null,'150');" onkeyup="return showCodeListKey('llfeetype',[this,MainFeeName],[0,1],null,'1 and code <> #C#',1,null,'150');" onfocus= "choiseType();"><input class=codename name=MainFeeName readonly=true></TD></TD>
    			<TD  class= title> </TD>
    			<TD  class= input> </TD>
                <TD  class= title> </TD>
                <TD  class= input> </TD>
            </tr>
        </table>
    </div>
    <!--���ﵥ֤¼����Ϣ-->
    <Div  id= "divMedFee1" style= "display:'none'">
        <hr>
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
					<td><INPUT class=cssButton name="hideButton1" VALUE="��  ��"  TYPE=button onclick="divMedFee1.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                    <td><INPUT class=cssButton type=hidden name="saveButton1" VALUE="��  ��"  TYPE=button onclick="AddClick1()" ></td>
    				<td><INPUT class=cssButton type=hidden name="deleteButton1" VALUE="ɾ  ��"  TYPE=button onclick="DeleteClick1()" ></td>
    				
                </tr>
            </table>
            <table  class= common>
                <tr class= common>
    				<TD  class= title> �˵���</TD>
    				<TD  class= input> <Input class=common name=ClinicMainFeeNo><font size=1 color='#ff0000'><b>*</b></font></TD>
    				<TD  class= title> ҽԺ����</TD>
    				<TD  class= input> <Input class=codeno name=ClinicHosID ondblclick="return showCodeList('commendhospital',[this,ClinicHosName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,ClinicHosName],[0,1],null,null,null,null,'400');" ><input class=codename name=ClinicHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <Input class=codeno name=ClinicMedFeeType  verify="��������|code:LLMedFeeType" ondblclick="return showCodeList('llmedfeetype',[this,ClinicMedFeeTypeName],[0,1],null,null,null,null,'150');" onkeyup="return showCodeListKey('llmedfeetype',[this,ClinicMedFeeTypeName],[0,1],null,null,null,null,'150');"><input class=codename name=ClinicMedFeeTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> ���ý��</TD>
                    <TD  class= input> <Input class=common name=ClinicMedFeeSum verify="���ý��|num" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD>
                    <TD  class= title> ��ʼ����</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="��ʼ����|date&NOTNULL" dateFormat="short" name="ClinicStartDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="��������|date&NOTNULL" dateFormat="short" name="ClinicEndDate" onchange="dayCount(fm.ClinicStartDate.value,fm.ClinicEndDate.value,'C');" onfocus="dayCount(fm.ClinicStartDate.value,fm.ClinicEndDate.value,'C');" onblur="dayCount(fm.ClinicStartDate.value,fm.ClinicEndDate.value,'C');"><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> ����</TD>
                    <TD  class= input> <Input class="readonly" readonly name=ClinicDayCount1></TD>
                    <TD  class= title> ���������¹ʷ�������</TD>
                    <TD  class= input> <Input class="readonly" readonly name=ClinicDayCount2></TD>
                    <TD  class= title> ���������������</TD>
                    <TD  class= input> <Input class="readonly" readonly name=ClinicDayCount3></TD>                
                </tr>
            </table>
    	</div>
    </div>
    <!--סԺ��֤¼����Ϣ-->
    <Div  id= "divMedFee2" style= "display:'none'">
        <hr>
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
                    <td><INPUT class=cssButton name="hideButton2" VALUE="��  ��"  TYPE=button onclick="divMedFee2.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                    <td><INPUT class=cssButton type=hidden name="saveButton2" VALUE="��  ��"  TYPE=button onclick="AddClick2()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton2" VALUE="ɾ  ��"  TYPE=button onclick="DeleteClick2()" ></td>
                </tr>
            </table>
            <table  class= common>
                <tr class= common>
    				<TD  class= title> �˵���</TD>
    				<TD  class= input> <Input class=common name=HosMainFeeNo ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ҽԺ����</TD>
                    <TD  class= input> <Input class=codeno name=InHosHosID ondblclick="return showCodeList('commendhospital',[this,InHosHosName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,InHosHosName],[0,1],null,null,null,null,'400');"><input class=codename name=InHosHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <Input class=codeno name=InHosMedFeeType  verify="��������|code:LLMedFeeType" ondblclick="return showCodeList('LLMedFeeType',[this,InHosMedFeeTypeName],[0,1],null,null,null,null,'150');" onkeyup="return showCodeListKey('LLMedFeeType',[this,InHosMedFeeTypeName],[0,1],null,null,null,null,'150');"><input class=codename name=InHosMedFeeTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> ���ý��</TD>
                    <TD  class= input> <Input class=common  name=InHosMedFeeSum verify="���ý��|num" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD>
                    <TD  class= title> ��ʼ����</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="��ʼ����|date&NOTNULL" dateFormat="short" name="InHosStartDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="��������|date&NOTNULL" dateFormat="short" name="InHosEndDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> ʵ��סԺ����</TD>
                    <TD  class= input> <Input class="common" name=InHosDayCount1 ></TD>
                    <TD  class= title> ���������¹ʷ�������</TD>
                    <TD  class= input> <Input class="readonly" readonly name=InHosDayCount2></TD>
                    <TD  class= title> ���������������</TD>
                    <TD  class= input> <Input class="readonly" readonly name=InHosDayCount3></TD> 
                </tr>
            </table>
        </div>
    </div>
    <!--�����˲�-->
    <Div  id= "divMedFee3" style= "display:'none'">
        <hr>
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,MedFeeCaseInfo);"></td>
                <td class= titleImg>�˲�¼����Ϣ</td>
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
                    <td><INPUT class=cssButton name="hideButton3" VALUE="��  ��"  TYPE=button onclick="divMedFee3.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                    <td><INPUT class=cssButton type=hidden name="saveButton3" VALUE="��  ��"  TYPE=button onclick="AddClick3()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton3" VALUE="ɾ  ��"  TYPE=button onclick="DeleteClick3()" ></td>
                </tr>
            </table>
            <table  class= common>
                <tr class= common>
    				<TD  class= title> �м�����</TD>
    				<TD  class= input> <Input class=codeno name=DefoType ondblClick="showCodeList('lldefotype',[this,DefoTypeName],[0,1]);" onkeyup="showCodeListKey('lldefotype',[this,DefoTypeName],[0,1]);"><input class=codename name=DefoTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> �˲м���</TD>
                    <TD  class= input> <Input class=codeno name=DefoGrade ondblclick="return showCodeList('lldefograde',[this,DefoGradeName],[0,1],null,fm.DefoType.value,'DefoType','1','300');" onkeyup="return showCodeListKey('lldefograde',[this,DefoGradeName],[0,1],null,fm.DefoType.value,'DefoType','1','300');"><input class=codename name=DefoGradeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> �˲д���</TD>
                    <TD  class= input> <Input class=codeno name=DefoCode ondblclick="return showCodeList('lldefocode',[this,DefoCodeName],[0,1],null,fm.DefoType.value,fm.DefoGrade.value,'1','300');" onkeyup="return showCodeListKey('lldefocode',[this,DefoCodeName],[0,1],null,fm.DefoType.value,fm.DefoGrade.value,'1','300');" onfocus="queryDeformityRate();"><input class=codename name=DefoCodeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> �м���������</TD>
                    <TD  class= input> <Input class="readonly" readonly  name=DeformityRate ></TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <Input class="common" name=JudgeOrganName ></TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <Input class="coolDatePicker" dateFormat="short" name=JudgeDate ></TD>
                </tr>
            </table>
        </div>
    </div>
    <!--���������ּ�����Ϣ-->
    <Div id= "divMedFee4" style= "display:'none'">
        <hr>
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
                    <td><INPUT class=cssButton name="hideButton4" VALUE="��  ��"  TYPE=button onclick="divMedFee4.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                    <td><INPUT class=cssButton type=hidden name="saveButton4" VALUE="��  ��"  TYPE=button onclick="AddClick4()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton4" VALUE="ɾ  ��"  TYPE=button onclick="DeleteClick4()" ></td>
                </tr>
            </table>
            <table  class= common>
                <tr class= common>
    				<TD  class= title> ����</TD>
    				<TD  class= input> <Input class=codeno name=OperationType CodeData="0|3^D|�ض�����^E|���ּ���^F|�ض�����" ondblclick="return showCodeListEx('OperationType', [this,OperationTypeName],[0,1])"onkeyup="return showCodeListKeyEx('OperationType', [this,OperationTypeName],[0,1])" onfocus="setOperType();"><Input class=codename name=OperationTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ����</TD>
                    <TD  class= input> <Input class=codeno name=OperationCode ondblclick="return showCodeList(fm.llOperType.value,[this,OperationName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey(fm.llOperType.value,[this,OperationName],[0,1],null,null,null,null,'250');"><input class=codename name=OperationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <!--TD  class= title> ��������</TD>
                    <TD  class= input> <Input class=common name=OpLevel></TD-->
                    <TD  class= title> ���</TD>
                    <TD  class= input> <input class=common name=OpFee ></TD>
                </tr>
                <tr class= common>
                    <!--TD  class= title> �����������</TD>
                    <TD  class= input> <Input class=common name=UnitNo></TD-->
                    <TD  class= title> ҽ�ƻ�������</TD>
                    <TD  class= input> <input class=common name=UnitName ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ȷ������</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="��ʼ����|date&NOTNULL" dateFormat="short" name="DiagnoseDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> </TD>
                    <TD  class= input> </TD>
                </tr>
            </table>
        </div>
    </div>
    <!--������Ϣ-->
    <Div  id= "divMedFee5" style= "display:'none'">
        <hr>
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
                    <td><INPUT class=cssButton name="hideButton5" VALUE="��  ��"  TYPE=button onclick="divMedFee5.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                    <td><INPUT class=cssButton type=hidden name="saveButton5" VALUE="��  ��"  TYPE=button onclick="AddClick5()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton5" VALUE="ɾ  ��"  TYPE=button onclick="DeleteClick5()" ></td>
                </tr>
            </table>
            <table  class= common>
                <tr class= common>
    				<TD  class= title> ��������</TD>
    				<TD  class= input> <Input class=codeno name=FactorType ondblclick="return showCodeList('llfactype',[this,FactorTypeName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey('llfactype',[this,FactorTypeName],[0,1],null,null,null,null,'250');" onfocus="setFactorType();"><input class=codename name=FactorTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ���ַ��ô���</TD>
                    <TD  class= input> <Input class=codeno name=FactorCode ondblclick="return showCodeList(fm.llFactorType.value,[this,FactorName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey(fm.llFactorType.value,[this,FactorName],[0,1],null,null,null,null,'250');"><input class=codename name=FactorName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ���ַ��ý��</TD>
                    <TD  class= input> <Input class=common  name=FactorValue ></TD>
                </tr>
                <tr class= common>
    				<TD  class= title> �����������</TD>
    				<TD  class= input> <Input class=common name=FactorUnitName><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ��ʼ����</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="��ʼ����|date&NOTNULL" dateFormat="short" name="FactorStateDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ��������</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="��������|date&NOTNULL" dateFormat="short" name="FactorEndDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
            </table>
        </div>
    </div>
    <!--�籣����������-->
    <Div  id= "divMedFee6" style= "display:'none'">
        <hr>
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
                    <td><INPUT class=cssButton name="hideButton6" VALUE="��  ��"  TYPE=button onclick="divMedFee6.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                    <td><INPUT class=cssButton type=hidden name="saveButton6" VALUE="��  ��"  TYPE=button onclick="AddClick6()" ></td>
                    <td><INPUT class=cssButton type=hidden name="deleteButton6" VALUE="ɾ  ��"  TYPE=button onclick="DeleteClick6()" ></td>
                </tr>
            </table>
            <table  class= common>
                <tr class= common>
    				<TD  class= title> ��������</TD>
    				<TD  class= input> <Input class=codeno readonly name=FeeThreeType value="med"><input class=codename name=FeeThreeTypeName readonly=true value="ҽ������"></TD>
                    <TD  class= title> ���ô���</TD>
                    <TD  class= input> <Input class=codeno name=FeeThreeCode ondblclick="return showCodeList('llfactypemed',[this,FeeThreeName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey('llfactypemed',[this,FeeThreeName],[0,1],null,null,null,null,'250');"><input class=codename name=FeeThreeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> ���ý��</TD>
                    <TD  class= input> <Input class=common name=FeeThreeValue ></TD>
                </tr>
                <tr class= common>
    				<TD  class= title> �����������</TD>
    				<TD  class= input> <Input class=common name=FeeThreeUnitName><font size=1 color='#ff0000'><b>*</b></font></TD>
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
                    <TD class= input> 
                        <textarea name="AdjRemark" cols="100" rows="2" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
            </table>
        </div>
    </div>
    <hr>
    <INPUT class=cssButton VALUE="�鿴ȫ��" TYPE=button onclick="showAll();">
    <INPUT class=cssButton VALUE="����ȫ��" TYPE=button onclick="showNone();">
    <INPUT class=cssButton VALUE="��  ��" TYPE=button onclick="top.close();">
    
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
    
    <input type=hidden name=accDate1 value=''>
    <input type=hidden name=accDate2 value=''> 
    <input type=hidden name=llOperType value='lloperationtype'>
    <input type=hidden name=llFactorType value=''>
    <input type=hidden name=DealFlag value=''><!--��ʼ�����Ƿ����ڳ�������,0��1����-->
    
    <input type=hidden name=hideOperate value=''>
    <input type=hidden name=currentInput value=''><!--��ǰ��������-->
    <input type=hidden id="fmtransact" name="fmtransact">
    
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
