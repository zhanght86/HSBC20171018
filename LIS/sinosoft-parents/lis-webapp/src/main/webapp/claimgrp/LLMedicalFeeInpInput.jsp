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

    String tclaimNo = request.getParameter("claimNo");  //赔案号
    String tcaseNo = request.getParameter("caseNo");  //
    String tcustNo = request.getParameter("custNo");  //
    String taccDate1 = request.getParameter("accDate1");  //
    String taccDate2 = request.getParameter("accDate2");  //
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
    <SCRIPT src="LLMedicalFeeInp.js"></SCRIPT>
    <%@include file="LLMedicalFeeInpInit.jsp"%>
</head>
<body onload="initForm();" >
<form action="" method=post name=fm target="fraSubmit">
    <br>
  <table>
       <tr><td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMedFee);"></td>
            <td class= titleImg>费用类型</td>
       </tr>
  </table>
  <Div  id= "divMedFee" style= "display:''">
        <table  class= common>
            <tr  class= common>
          <TD  class= title> 费用类型</TD>
          <TD  class= input> <Input class=codeno name=MainFeeType ondblclick="return showCodeList('llfeetype',[this,MainFeeName],[0,1]);" onkeyup="return showCodeListKey('llfeetype',[this,MainFeeName],[0,1]);" onfocus= "choiseType();"><input class=codename name=MainFeeName readonly=true></TD></TD>
          <TD  class= title> </TD>
          <TD  class= input> </TD>
                <TD  class= title> </TD>
                <TD  class= input> </TD>
            </tr>
        </table>
    </div>
    <!--门诊单证录入信息-->
    <Div  id= "divMedFee1" style= "display:'none'">
        <hr>
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
                    <td><INPUT class=cssButton name="saveButton1" VALUE="增  加"  TYPE=button onclick="AddClick1()" ></td>
            <td><INPUT class=cssButton name="deleteButton1" VALUE="删  除"  TYPE=button onclick="DeleteClick1()" ></td>
            <td><INPUT class=cssButton name="hideButton1" VALUE="隐  藏"  TYPE=button onclick="divMedFee1.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <table  class= common>
                <tr class= common>
            <TD  class= title> 账单号</TD>
            <TD  class= input> <Input class=common name=ClinicMainFeeNo><font size=1 color='#ff0000'><b>*</b></font></TD>
            <TD  class= title> 医院名称</TD>
            <TD  class= input> <Input class=codeno name=ClinicHosID ondblclick="return showCodeList('commendhospital',[this,ClinicHosName],[0,1],null,fm.ManageCom.value,fm.ManageCom.value,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,ClinicHosName],[0,1],null,fm.ManageCom.value,fm.ManageCom.value,null,'400');" ><input class=codename name=ClinicHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 费用类型</TD>
                    <TD  class= input> <Input class=codeno name=ClinicMedFeeType  verify="费用类型|code:llfeeitemtype" ondblclick="return showCodeList('llfeeitemtype',[this,ClinicMedFeeTypeName],[0,1],null,null,null,null,'150');" onkeyup="return showCodeListKey('llfeeitemtype',[this,ClinicMedFeeTypeName],[0,1],null,null,null,null,'150');"><input class=codename name=ClinicMedFeeTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <!--增加的by yaory -->
                <tr class= common>
                  
                    <TD  class= title> 原始费用</TD>
                    <TD  class= input> <Input class=common name=OriginFee><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 扣除费用</TD>
                    <TD  class= input>  <Input class=common name=MinusFee onblur="calculate();"></TD>
                    <TD  class= title> 扣除原因</TD>
                    <TD  class= input> <Input class=codeno name=MinusReason ondblclick="return showCodeList('deductreason',[this,MinusReasonName],[0,1],null,null,null,null,'150');" onkeyup="return showCodeListKey('deductreason',[this,MinusReasonName],[0,1],null,null,null,null,'150');" ><input class=codename name=MinusReasonName ></TD>
                </tr>
                <tr class= common>
                  
                    <TD  class= title> 合理费用</TD>
                    <TD  class= input> <Input class=readonly readonly name=ClinicMedFeeSum></TD>
                    <TD  class= title> 社保赔付费用</TD>
                    <TD  class= input> <Input class=common name=SocietyFee onblur="calmix();"></TD>
                     <TD  class= title> 开始日期</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="开始日期|date&NOTNULL" dateFormat="short" name="ClinicStartDate" onclick="checkapplydate()"><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <!--end-->
                <tr class= common>
                    <!--TD  class= title> 费用金额</TD>
                    <TD  class= input> <Input class=common name=ClinicMedFeeSum verify="费用金额|num" onchange=verifyElementWrap(this.verify,this.value,this.form.name+"."+this.name)></TD-->
                   
<!--                     <TD  class= title> 结束日期</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="结束日期|date&NOTNULL" dateFormat="short" name="ClinicEndDate" onchange="dayCount(fm.ClinicStartDate.value,fm.ClinicEndDate.value,'C');" onfocus="dayCount(fm.ClinicStartDate.value,fm.ClinicEndDate.value,'C');" onblur="dayCount(fm.ClinicStartDate.value,fm.ClinicEndDate.value,'C');"><font size=1 color='#ff0000'><b>*</b></font></TD> -->
                </tr>
                <tr class= common>
                    <input class="readonly" type = "hidden" name="ClinicEndDate">
                    <Input class="readonly" type = "hidden" readonly name=ClinicDayCount1>
<!--                     <TD  class= title> 天数</TD>
                    <TD  class= input> <Input class="readonly" readonly name=ClinicDayCount1></TD> -->
                    <TD  class= title> 距离意外事故发生天数</TD>
                    <TD  class= input> <Input class="readonly" readonly name=ClinicDayCount2></TD>
                    <TD  class= title> 距离出险日期天数</TD>
                    <TD  class= input> <Input class="readonly" readonly name=ClinicDayCount3></TD>                
                </tr>
            </table>
      </div>
    </div>
    <!--住院单证录入信息-->
    <Div  id= "divMedFee2" style= "display:'none'">
        <hr>
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
                    <td><INPUT class=cssButton name="saveButton2" VALUE="增  加"  TYPE=button onclick="AddClick2()" ></td>
                    <td><INPUT class=cssButton name="deleteButton2" VALUE="删  除"  TYPE=button onclick="DeleteClick2()" ></td>
                    <td><INPUT class=cssButton name="hideButton2" VALUE="隐  藏"  TYPE=button onclick="divMedFee2.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <table  class= common>
                <tr class= common>
            <TD  class= title> 账单号</TD>
            <TD  class= input> <Input class=common name=HosMainFeeNo ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 医院名称</TD>
                    <TD  class= input> <Input class=codeno name=InHosHosID ondblclick="return showCodeList('commendhospital',[this,InHosHosName],[0,1],null,fm.ManageCom.value,fm.ManageCom.value,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,InHosHosName],[0,1],null,fm.ManageCom.value,fm.ManageCom.value,null,'400');"><input class=codename name=InHosHosName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 费用类型</TD>
                    <TD  class= input> <Input class=codeno name=InHosMedFeeType  verify="费用类型|code:llfeeitemtype" ondblclick="return showCodeList('llfeeitemtyp2',[this,InHosMedFeeTypeName],[0,1],null,null,null,null,'150');" onkeyup="return showCodeListKey('llfeeitemtyp2',[this,InHosMedFeeTypeName],[0,1],null,null,null,null,'150');"><input class=codename name=InHosMedFeeTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <!--yaoyr-->
                <tr class= common>
                  
                    <TD  class= title> 原始费用</TD>
                    <TD  class= input> <Input class=common name=OriginFee1><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 扣除费用</TD>
                    <TD  class= input>  <Input class=common name=MinusFee1 onblur="calculate1();"></TD>
                    <TD  class= title> 扣除原因</TD>
                    <TD  class= input> <Input class=codeno name=MinusReason1 ondblclick="return showCodeList('deductreason',[this,MinusReasonName1],[0,1],null,null,null,null,'150');" onkeyup="return showCodeListKey('deductreason',[this,MinusReasonName1],[0,1],null,null,null,null,'150');" ><input class=codename name=MinusReasonName1 ></TD>
                </tr>
                <tr class= common>
                  
                    <TD  class= title> 合理费用</TD>
                    <TD  class= input> <Input class=readonly readonly name=InHosMedFeeSum1></TD>
                    <TD  class= title> 社保赔付费用</TD>
                    <TD  class= input> <Input class=common name=SocietyFee1 onblur="calmix1();"></TD>
                    <TD  class= title> 住院起付线</TD>
                    <TD  class= input> <Input class=common name=HospLineAmnt ></TD>
                     </tr>
                <!--end-->
                <tr class= common> 
                	<TD  class= title> 开始日期</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="开始日期|date&NOTNULL" dateFormat="short" name="InHosStartDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                           
                    <TD  class= title> 结束日期</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="结束日期|date&NOTNULL" dateFormat="short" name="InHosEndDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> 实际住院天数</TD>
                    <TD  class= input> <Input class="common" name=InHosDayCount1 ></TD>
                    <TD  class= title> 距离意外事故发生天数</TD>
                    <TD  class= input> <Input class="readonly" readonly name=InHosDayCount2></TD>
                    <TD  class= title> 距离出险日期天数</TD>
                    <TD  class= input> <Input class="readonly" readonly name=InHosDayCount3></TD> 
                </tr>
            </table>
        </div>
    </div>
    <!--疾病伤残-->
    <Div  id= "divMedFee3" style= "display:'none'">
        <hr>
        <table>
             <tr>
                <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,MedFeeCaseInfo);"></td>
                <td class= titleImg>伤残录入信息</td>
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
                    <td><INPUT class=cssButton name="saveButton3" VALUE="增  加"  TYPE=button onclick="AddClick3()" ></td>
                    <td><INPUT class=cssButton name="deleteButton3" VALUE="删  除"  TYPE=button onclick="DeleteClick3()" ></td>
                    <td><INPUT class=cssButton name="hideButton3" VALUE="隐  藏"  TYPE=button onclick="divMedFee3.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <table  class= common>
                <tr class= common>
            <TD  class= title> 残疾类型</TD>
            <TD  class= input> <Input class=codeno name=DefoType ondblClick="showCodeList('lldefotype',[this,DefoTypeName],[0,1]);" onkeyup="showCodeListKey('lldefotype',[this,DefoTypeName],[0,1]);"><input class=codename name=DefoTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 伤残级别</TD>
                    <TD  class= input> <Input class=codeno name=DefoGrade ondblclick="return showCodeList('lldefograde',[this,DefoGradeName],[0,1],null,fm.DefoType.value,'DefoType','1','300');" onkeyup="return showCodeListKey('lldefograde',[this,DefoGradeName],[0,1],null,fm.DefoType.value,'DefoType','1','300');"><input class=codename name=DefoGradeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 伤残代码</TD>
                    <TD  class= input> <Input class=codeno name=DefoCode ondblclick="return showCodeList('lldefocode',[this,DefoCodeName],[0,1],null,fm.DefoType.value,fm.DefoGrade.value,'1','250');" onkeyup="return showCodeListKey('lldefocode',[this,DefoCodeName],[0,1],null,fm.DefoType.value,fm.DefoGrade.value,'1','250');" onfocus="queryDeformityRate();"><input class=codename name=DefoCodeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <TD  class= title> 残疾给付比例</TD>
                    <TD  class= input> <Input class="readonly" readonly  name=DeformityRate ></TD>
                    <TD  class= title> 鉴定机构</TD>
                    <TD  class= input> <Input class="common" name=JudgeOrganName ></TD>
                    <TD  class= title> 鉴定日期</TD>
                    <TD  class= input> <Input class="coolDatePicker" dateFormat="short" name=JudgeDate ></TD>
                </tr>
            </table>
        </div>
    </div>
    <!--手术与特种疾病信息-->
    <Div id= "divMedFee4" style= "display:'none'">
        <hr>
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
                    <td><INPUT class=cssButton name="saveButton4" VALUE="增  加"  TYPE=button onclick="AddClick4()" ></td>
                    <td><INPUT class=cssButton name="deleteButton4" VALUE="删  除"  TYPE=button onclick="DeleteClick4()" ></td>
                    <td><INPUT class=cssButton name="hideButton4" VALUE="隐  藏"  TYPE=button onclick="divMedFee4.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <table  class= common>
                <tr class= common>
            <TD  class= title> 类型</TD>
            <TD  class= input> <Input class=codeno name=OperationType CodeData="0|3^D|特定手术^E|特种疾病^F|特定给付" ondblclick="return showCodeListEx('OperationType', [this,OperationTypeName],[0,1])"onkeyup="return showCodeListKeyEx('OperationType', [this,OperationTypeName],[0,1])" onfocus="setOperType();"><Input class=codename name=OperationTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 代码</TD>
                    <TD  class= input> <Input class=codeno name=OperationCode ondblclick="return showCodeList(fm.llOperType.value,[this,OperationName],[0,1],null,fm.llOperType.value,'codetype',null,'250');" onkeyup="return showCodeListKey(fm.llOperType.value,[this,OperationName],[0,1],null,fm.llOperType.value,codetype,null,'250');"><input class=codename name=OperationName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <!--TD  class= title> 手术档次</TD>
                    <TD  class= input> <Input class=common name=OpLevel></TD-->
                    <TD  class= title> 金额</TD>
                    <TD  class= input> <input class=common name=OpFee ><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
                    <!--TD  class= title> 服务机构代码</TD>
                    <TD  class= input> <Input class=common name=UnitNo></TD-->
                    <TD  class= title> 医疗机构名称</TD>
                    <TD  class= input> <input class=common name=UnitName ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 确诊日期</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="开始日期|date&NOTNULL" dateFormat="short" name="DiagnoseDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> </TD>
                    <TD  class= input> </TD>
                </tr>
            </table>
        </div>
    </div>
    <!--其他信息-->
    <Div  id= "divMedFee5" style= "display:'none'">
        <hr>
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
                    <td><INPUT class=cssButton name="saveButton5" VALUE="增  加"  TYPE=button onclick="AddClick5()" ></td>
                    <td><INPUT class=cssButton name="deleteButton5" VALUE="删  除"  TYPE=button onclick="DeleteClick5()" ></td>
                    <td><INPUT class=cssButton name="hideButton5" VALUE="隐  藏"  TYPE=button onclick="divMedFee5.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <table  class= common>
                <tr class= common>
            <TD  class= title> 费用类型</TD>
            <TD  class= input> <Input class=codeno name=FactorType ondblclick="return showCodeList('llfactype',[this,FactorTypeName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey('llfactype',[this,FactorTypeName],[0,1],null,null,null,null,'250');" onfocus="setFactorType();"><input class=codename name=FactorTypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 特种费用代码</TD>
                    <TD  class= input> <Input class=codeno name=FactorCode ondblclick="return showCodeList(fm.llFactorType.value,[this,FactorName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey(fm.llFactorType.value,[this,FactorName],[0,1],null,null,null,null,'250');"><input class=codename name=FactorName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 特种费用金额</TD>
                    <TD  class= input> <Input class=common  name=FactorValue ></TD>
                </tr>
                <tr class= common>
            <TD  class= title> 服务机构名称</TD>
            <TD  class= input> <Input class=common name=FactorUnitName><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 起始日期</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="起始日期|date&NOTNULL" dateFormat="short" name="FactorStateDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 结束日期</TD>
                    <TD  class= input> <input class="coolDatePicker" verify="结束日期|date&NOTNULL" dateFormat="short" name="FactorEndDate" ><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
            </table>
        </div>
    </div>
    <!--社保第三方给付-->
    <Div  id= "divMedFee6" style= "display:'none'">
        <hr>
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
                    <td><INPUT class=cssButton name="saveButton6" VALUE="增  加"  TYPE=button onclick="AddClick6()" ></td>
                    <td><INPUT class=cssButton name="deleteButton6" VALUE="删  除"  TYPE=button onclick="DeleteClick6()" ></td>
                    <td><INPUT class=cssButton name="hideButton6" VALUE="隐  藏"  TYPE=button onclick="divMedFee6.style.display='none';fm.MainFeeType.value='';fm.MainFeeName.value='';" ></td>
                </tr>
            </table>
            <table  class= common>
                <tr class= common>
            <TD  class= title> 费用类型</TD>
            <TD  class= input> <Input class=codeno readonly name=FeeThreeType value="med"><input class=codename name=FeeThreeTypeName readonly=true value="医疗类型"></TD>
                    <TD  class= title> 费用代码</TD>
                    <TD  class= input> <Input class=codeno name=FeeThreeCode ondblclick="return showCodeList('llfactypemed',[this,FeeThreeName],[0,1],null,null,null,null,'250');" onkeyup="return showCodeListKey('llfactypemed',[this,FeeThreeName],[0,1],null,null,null,null,'250');"><input class=codename name=FeeThreeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD  class= title> 费用金额</TD>
                    <TD  class= input> <Input class=common name=FeeThreeValue ><font size=1 color='#ff0000'><b>*</b></font></TD>
                </tr>
                <tr class= common>
            <TD  class= title> 服务机构名称</TD>
            <TD  class= input> <Input class=common name=FeeThreeUnitName><font size=1 color='#ff0000'><b>*</b></font></TD>
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
                    <TD class= input> 
                        <textarea name="AdjRemark" cols="100" rows="2" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
            </table>
        </div>
    </div>
    <hr>
    <INPUT class=cssButton VALUE="查看全部" TYPE=button onclick="showAll();">
    <INPUT class=cssButton VALUE="隐藏全部" TYPE=button onclick="showNone();">
    <INPUT class=cssButton VALUE="返  回" TYPE=button onclick="top.close();">
    
    <!--隐藏表单域,保存信息用-->
    <input type=hidden name=ClinicHosGrade>
    <input type=hidden name=InHosHosGrade>
    <!--yaory-->
    <input type=hidden name=MixFee>
    <input type=hidden name=MixFee1>
    <!--end-->
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
    
    <input type=hidden name=accDate1 value=''>
    <input type=hidden name=accDate2 value=''> 
    <input type=hidden name=llOperType value='lloperationtype'>
    <input type=hidden name=llFactorType value=''>
    <input type=hidden name=DealFlag value=''><!--开始日期是否早于出险日期,0是1不是-->
    
    <input type=hidden name=hideOperate value=''>
    <input type=hidden name=currentInput value=''><!--当前操作区域-->
    <input type=hidden id="fmtransact" name="fmtransact">
    <Input type=hidden id="ManageCom" name="ManageCom"><!--登陆区站-->
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
