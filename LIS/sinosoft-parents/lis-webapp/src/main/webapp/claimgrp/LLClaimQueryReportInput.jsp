<%
//**************************************************************************************************
//Name：LLClaimQueryReportInput.jsp
//Function：报案查询
//Author：zl
//Date: 2005-8-23 18:09
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

	String tClmNo = request.getParameter("claimNo");	//赔案号
	String tPhase = request.getParameter("phase");
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
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <SCRIPT src="LLClaimQueryReport.js"></SCRIPT>
    <%@include file="LLClaimQueryReportInit.jsp"%>
</head>

<body  onload="initForm()" >

<form method=post name=fm target="fraSubmit">
    <!--报案信息-->
    <table>
        <TR>
            <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimQueryReport1);"></TD>
            <TD class= titleImg>报案信息</TD>
        </TR>
    </table>

    <Div id= "divLLClaimQueryReport1" style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class= title> 事件号 </TD>
                <TD class= input> <Input class= "readonly" readonly name=AccNo></TD>
                <TD class= title> 赔案号 </TD>
                <TD class= input> <Input class="readonly" readonly  name=RptNo ></TD>
                <TD class= title> 报案人姓名 </TD>
                <TD class= input> <input class="readonly" readonly name=RptorName ></TD>
            </TR>
            <TR class= common>
                <TD class= title> 报案人电话 </TD>
                <TD class= input> <input class="readonly" readonly name=RptorPhone ></TD>
                <TD class= title> 报案人通讯地址 </TD>
                <TD class= input> <input class="readonly" readonly name=RptorAddress ></TD>
                <TD class= title> 报案人与事故人关系 </TD>
                <TD class= input> <Input type=hidden name=Relation ondblclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class=readonly readonly name=RelationName ></TD>
            </TR>
            <TR class= common>
                <TD class= title> 报案方式 </TD>
                <TD class= input> <Input type=hidden name="RptMode" value="01" ondblclick="return showCodeList('llrptmode',[this,RptModeName],[0,1]);" onkeyup="return showCodeListKey('llrptmode',[this,RptModeName],[0,1]);"><input class=readonly readonly name="RptModeName" value="电话报案" ></TD>
                <TD class= title> 出险地点 </TD>
                <TD class= input> <input class="readonly" readonly name=AccidentSite ></TD>
                <TD class= title> 报案受理日期 </TD>
            <TD class= input>  <input class="readonly" readonly name="RptDate" ></TD>
            </TR>
            <TR class= common>
                <TD class= title> 管辖机构 </TD>
                <TD class= input> <Input class= "readonly" readonly name=MngCom></TD>
                <TD class= title> 报案受理人 </TD>
                <TD class= input> <Input class="readonly" readonly name=Operator ></TD>
        	    <TD class= title> 申请类型</TD>
          	    <TD class= input> <Input type=hidden value="1" name=RgtClass ondblclick="return showCodeList('llrgtclass',[this,RgtClassName],[0,1]);" onkeyup="return showCodeListKey('llrgtclass',[this,RgtClassName],[0,1]);"><input class=readonly readonly name=RgtClassName  value="个人"></TD>
                <!--TD class= title> 理赔状态 </TD>
                <TD class= input> <Input class= "readonly" readonly name=CaseState></TD-->
            </TR>
        </table>
    </Div>
    <hr>

    <!--出险人信息-->
    <table class= common>
        <tr class= common>
            <td text-align: left colSpan=1><span id="spanSubReportGrid" ></span></td>
        </tr>
    </table>
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
            <td class=titleImg> 出险人信息 </td>
        </tr>
    </table>
    <Div id= "divLLSubReport" style= "display: ''">
        <table>
            <tr>
                <td><input class=cssButton name=QueryCont2 VALUE="保单查询"  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><input class=cssButton name=QueryCont3 VALUE="既往赔案查询" TYPE=button onclick="showOldInsuredCase()"></td>
                <td><input class=cssButton name=ColQueryImage VALUE="影像查询"  TYPE=button onclick="colQueryImage();"></td>
            </tr>
        </table>
        <!--出险人信息表单-->
        <span id="spanSubReport" >
            <table class= common>
                <TR class= common>
                    <!--出险人编码,见隐藏区-->
                    <TD class= title> 出险人姓名 </TD>
                    <TD class= input> <Input class="readonly" readonly name=customerName></TD>
                    <TD class= title> 出险人年龄 </TD>
                    <TD class= input> <Input class="readonly" readonly name=customerAge></TD>
                    <TD class= title> 出险人性别 </TD>
                    <TD class= input> <Input type=hidden name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=readonly readonly name=SexName ></TD>
                </TR>
                <TR class= common>
                    <TD class= title> VIP客户标示</TD>
                    <TD class= input> <Input class="readonly" readonly name=IsVip></TD>
                    <TD class= title> 事故日期</TD>
                    <TD class= input>  <Input type=hidden name="AccidentDate" ></TD>
                    <TD class= title> 出险原因</TD>
                    <TD class= input><Input type=hidden name=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);" onfocus="checkOccurReason();"><input class=readonly readonly name=ReasonName ></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗医院</TD>
                    <TD class= input> <Input type=hidden name=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1]);" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1]);"><input class=readonly readonly name=TreatAreaName ></TD>
                    <TD class= title> 出险日期 </TD>
                    <TD class= input>  <Input type=hidden name="AccidentDate2" ></TD>
                    <TD class= title> 出险细节</TD>
                    <TD class= input> <Input type=hidden name=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1],null,null,null,null,'400');"><input class=readonly readonly name=accidentDetailName ></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗情况</TD>
                    <TD class= input> <Input type=hidden name=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=readonly readonly name=cureDescName ></TD>
                    <TD class= title> 出险结果1</TD>
                    <TD class= input> <Input type=hidden name=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1],null,fm.occurReason.value,fm.occurReason.value,'1','400');" onFocus="saveIcdValue();"><input class=readonly readonly name=AccResult1Name ></TD>
                    <TD class= title> 出险结果2</TD>
                    <TD class= input> <Input type=hidden name=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'upicdcode','1','300');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.AccResult1.value,'ICDCode','1','300');"><input class=readonly readonly name=AccResult2Name ></TD>
                </TR>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> 理赔类型 </TD>
                    <TD align=left>
                        <input disabled type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> 身故 </input>
                        <input disabled type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> 高残 </input>
                        <input disabled type="checkbox" value="04" name="claimType"> 重大疾病 </input>
                        <input disabled type="checkbox" value="01" name="claimType"> 伤残 </input>
                        <input disabled type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> 豁免 </input>
                        <input disabled type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> 医疗 </input>
                        <input disabled type="checkbox" value="05" name="claimType" > 特种疾病 </input>
                        <input disabled type="checkbox" value="06" name="claimType" > 失业失能 </input>
                    </td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> 事故描述 </TD>
                </tr>
                <TR class= common>
                    <TD class= input> 
                        <textarea disabled name="AccDescDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
                <TR class= common>
                    <TD class= title> 备注 </TD>
                </tr>
                <TR class= common>
                    <TD class= input>
                        <textarea disabled name="RemarkDisabled" cols="100" rows="2" witdh=25% class="common"></textarea>
                    </TD>
                </tr>
            </table>
        </span>
    </div>
    <hr>
    <span id="operateButton4" >
        <table>
            <tr>
                <td><INPUT class=cssButton name="ViewInvest" VALUE="查看调查"  TYPE=button onclick="showQueryInq()"></td>
                <td><INPUT class=cssButton name="ViewReport" VALUE="查看呈报"  TYPE=button onclick="showQuerySubmit()"></td>
                <td><INPUT class=cssButton name="AccidentDesc" VALUE="备注信息"  TYPE=button onclick="showClaimDesc()"></td>
            </tr>
        </table>
    </span>
    <hr>
    <table>
        <tr>
            <td><INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()"></td>
        </tr>
    </table>
    <%
    //隐藏区,保存信息用
    %>
    <Input type=hidden id="customerNo" name="customerNo"><!--出险人代码-->

    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10代码-->
    <Input type=hidden id="Phase" name="Phase"><!--引用阶段-->

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
