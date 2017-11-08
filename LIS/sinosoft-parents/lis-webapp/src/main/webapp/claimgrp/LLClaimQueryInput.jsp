<%
//**************************************************************************************************
//Name：LLClaimQueryInput.jsp
//Function：赔案查询
//Author：zl
//Date: 2005-6-20 14:00
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
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput)session.getValue("GI");

    String tClmNo = request.getParameter("claimNo");  //赔案号
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
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
   <SCRIPT src="LLClaimQuery.js"></SCRIPT>
   <%@include file="LLClaimQueryInit.jsp"%>
</head>
<body  onload="initForm();" >
<form action="./LLClaimQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!--立案信息-->
    <table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
            <TD class= titleImg>立案信息</TD>
        </TR>
    </table>
    <Div  id= "divLLReport1" style= "display: ''" class=" maxbox">
        <table  class= common>
             <TR  class= common>
          <TD  class= title> 事件号 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly name=AccNo id=AccNo></TD>
               <TD  class= title> 赔案号 </TD>
                <TD  class= input> <Input class="readonly wid" readonly  name=RptNo id=RptNo ></TD>
                <TD  class= title> 申请人姓名 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=RptorName id=RptorName ></TD>
            </TR>
            <TR  class= common>
                <TD  class= title> 申请人电话 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=RptorPhone id=RptorPhone ></TD>
              <TD  class= title> 申请人通讯地址 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=RptorAddress id=RptorAddress ></TD>
              <TD  class= title> 申请人与事故人关系 </TD>
              <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type=hidden name=Relation id=Relation ondblclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class="wid" class=readonly readonly name=RelationName id=RelationName readonly=true></TD>
            </TR>
            <TR  class= common>
                 <TD  class= title> 出险地点 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly name=AccidentSite id=AccidentSite ></TD>
               <TD  class= title> 申请受理日期 </TD>
                <TD  class= input> <input class="readonly wid" readonly name="RptDate" id="RptDate" ></TD>
          <TD  class= title> 管辖机构 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly name=MngCom id=MngCom></TD>
          </TR>
            <TR  class= common>
              <TD  class= title> 立案受理人 </TD>
                <TD  class= input> <Input class="readonly wid" readonly name=Operator id=Operator ></TD>
              <TD  class= title> 受托人类型 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeType id=AssigneeType ></TD>
              <TD  class= title> 受托人代码 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeCode id=AssigneeCode ></TD>
            </TR>
            <TR  class= common>
              <TD  class= title> 受托人姓名 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeName id=AssigneeName ></TD>
              <TD  class= title> 受托人性别 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeSex id=AssigneeSex ></TD>
              <TD  class= title> 受托人电话 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneePhone id=AssigneePhone ></TD>
            </TR>
            <TR  class= common>
              <TD  class= title> 受托人地址 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeAddr id=AssigneeAddr ></TD>
              <TD  class= title> 受托人邮编 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeZip id=AssigneeZip ></TD>
              <TD  class= title> 申请类型</TD>
                <TD  class= input> <Input class="wid" class= readonly readonly name=RgtClassName id=RgtClassName ></TD>
            </TR>
        </table>
    </Div>
 
    <!--出险人信息-->
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
                    <TD class= title> 年龄 </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=customerAge id=customerAge></TD>
                    <TD class= title> 性别 </TD>
                    <TD class= input> <Input type=hidden name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class="wid" class=readonly readonly name=SexName id=SexName readonly=true></TD>
                </TR>
                <TR class= common>
					<TD class=title>社保标志</TD>
					<TD class=input><Input class="readonly wid" readonly name=SocialInsuFlagName id=SocialInsuFlagName></TD>		
                    <TD class= title> 意外事故发生日期</TD>
                    <TD class= input>  <input class= "readonly wid" readonly name="AccidentDate" id="AccidentDate" ></font></TD>
                    <TD class= title> 出险原因</TD>
                    <TD class= input><Input type=hidden name=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class="wid" class=readonly readonly name=ReasonName id=ReasonName readonly=true></font></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗医院</TD>
                    <TD class= input> <Input type=hidden name=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class="wid" class=readonly readonly name=TreatAreaName id=TreatAreaName readonly=true></TD>
                    <TD class= title> 出险日期 </TD>
                    <TD class= input>  <input class="wid" class= "readonly" readonly name="AccidentDate2" id="AccidentDate2" ></TD>
                    <TD class= title> 出险细节</TD>
                    <TD class= input> <Input type=hidden name=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class="wid" class=readonly readonly name=accidentDetailName id=accidentDetailName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗情况</TD>
                    <TD class= input> <Input type=hidden name=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class="wid" class=readonly readonly name=cureDescName id=cureDescName readonly=true></TD>
                    <TD class= title> 出险结果1</TD>
                    <TD class= input> <Input type=hidden name=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1]);" onFocus="saveIcdValue();"><input class="wid" class=readonly readonly name=AccResult1Name id=AccResult1Name readonly=true></TD>
                    <TD class= title> 出险结果2</TD>
                    <TD class= input> <Input type=hidden name=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');"><input class="wid" class=readonly readonly name=AccResult2Name id=AccResult2Name readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 单证齐全标示</TD>
                    <TD class= input> <Input type=hidden name=IsAllReady disabled CodeData="0|3^0|否^1|是" ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class="wid" class=readonly readonly name=IsAllReadyName id=IsAllReadyName readonly=true></TD>
                    <TD class= title> 重要信息修改标示</TD>
                    <TD class= input> <Input type=hidden name=IsModify disabled CodeData="0|3^0|否^1|是" ondblclick="return showCodeListEx('IsModify', [this,IsModifyName],[0,1])"onkeyup="return showCodeListKeyEx('IsModify', [this,IsModifyName],[0,1])"><Input class="wid" class=readonly readonly name=IsModifyName id=IsModifyName readonly=true></TD>
                </tr>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> 理赔类型：<font size=1 color='#ff0000'><b>*</b></font></TD>
		<TD align=left>
			<input style="vertical-align:middle" disabled type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> <span style="vertical-align:middle">身故</span> </input> 
			<input style="vertical-align:middle" disabled type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> <span style="vertical-align:middle">高残</span> </input> 
			<input style="vertical-align:middle" disabled type="checkbox" value="04" name="claimType"> <span style="vertical-align:middle">重大疾病</span> </input>
			<input style="vertical-align:middle" disabled type="checkbox" value="01" name="claimType">	<span style="vertical-align:middle">残疾、烧伤、骨折、重要器官切除</span> </input> 
			<!--<input type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> 豁免 </input> -->
			<input style="vertical-align:middle" disabled type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> <span style="vertical-align:middle">医疗</span> </input> 
			<input style="vertical-align:middle" disabled type="checkbox" value="05" name="claimType"> <span style="vertical-align:middle">特种疾病</span> </input> 
			<!-- <input type="checkbox" value="06" name="claimType" > 失业失能 </input> -->
		</td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> 事故描述 </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> <textarea name="AccDesc" id="AccDesc" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
                <TR class= common>
                    <TD class= title> 备注 </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> <textarea name="Remark" id="Remark" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
            </table>
        </span>
        
    </div>
    
    <table>
            <tr>
                <td><input class=cssButton name=QueryCont1 VALUE=" 报案查询 "  TYPE=button onclick="showReport()"></td>
                <td><input class=cssButton name=QueryCont2 VALUE=" 保单查询 "  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><input class=cssButton type = "hidden" name=QueryCont3 VALUE="既往赔案查询" TYPE=button onclick="showOldInsuredCase()"></td>
                <td><input class=cssButton type = "hidden" name=BarCodePrint VALUE="打印条形码"  TYPE=button onclick="colBarCodePrint();"></td>
                <td><input class=cssButton type = "hidden" name=ColQueryImage VALUE="影像查询"  TYPE=button onclick="colQueryImage();"></td>                
            </tr>
        </table>
    <!--立案结论信息-->
    <table>
         <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit6);"></td>
            <td class= titleImg>立案结论信息</td>
         </tr>
    </table>
    <Div  id= "divBaseUnit6" style= "display:''" class="maxbox1">
      <table class= common>
          <TR class= common>
              <TD  class= title> 立案结论</TD>
              <TD  class= input> <Input type=hidden name=RgtConclusion ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input class="wid" class=readonly readonly name=RgtConclusionName id=RgtConclusionName readonly=true></TD>
                <TD  class= title> 案件标识</TD>
                <TD  class= input> <Input type=hidden name=rgtType ondblclick="return showCodeListEx('llrgttype', [this,rgtTypeName],[0,1])"onkeyup="return showCodeListKeyEx('llrgttype', [this,rgtTypeName],[0,1])"><Input class="wid" class=readonly readonly name=rgtTypeName id=rgtTypeName readonly=true></TD>
                <TD  class= title> 赔案状态</TD>
                <TD  class= input> <Input type=hidden name=ClmState ondblclick="return showCodeListEx('llclaimstate', [this,ClmStateName],[0,1])"onkeyup="return showCodeListKeyEx('llclaimstate', [this,ClmStateName],[0,1])"><Input class="wid" class=readonly readonly name=ClmStateName id=ClmStateName readonly=true></TD>
           </tr>
      </table>
    </div>
    <Div id= "divPart2" style= "display:'none'">
        
        <span id= "spanConclusion1" style= "display: ">
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
                    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit1);"></td>
                    <td class= titleImg>理赔类型计算信息</td>
                 </tr>
            </table>
            <Div  id= "divBaseUnit1" style= "display:''">
                 <Table  class= common>
                      <tr><td text-align: left colSpan=1>
                           <span id="spanDutyKindGrid"></span>
                      </td></tr>
                 </Table>
            </div>
            <!--按照保单理赔类型计算信息-->
            <table>
                 <tr>
                    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit2);"></td>
                    <td class= titleImg>保单计算信息</td>
                 </tr>
            </table>
            <Div  id= "divBaseUnit2" style= "display:''">
                 <Table  class= common>
                      <tr><td text-align: left colSpan=1>
                           <span id="spanPolDutyKindGrid"></span>
                      </td></tr>
                 </Table>
            </div>
            <!--保项匹配信息-->
            <table>
                 <tr>
                    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit3);"></td>
                    <td class= titleImg>保项计算信息</td>
                 </tr>
            </table>
            <Div  id= "divBaseUnit3" style= "display:''">
                <Table  class= common>
                    <tr>
                        <td text-align: left colSpan=1><span id="spanPolDutyCodeGrid"></span></td>
                    </tr>
                </Table>
            </div>
        </span>
        <div id= "divBaseUnit5" style= "display: none">
            <table>
                 <tr>
                    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit4);"></td>
                    <td class= titleImg>保项赔付结论</td>
                 </tr>
            </table>
            <Div  id= "divBaseUnit4" style= "display:''">
                <table class= common>
                    <TR class= common>
                        <TD  class= title>赔付结论</TD>
                        <TD  class= input><Input type=hidden name=GiveType ondblclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llpayconclusion',[this,GiveTypeName],[0,1]);" onfocus= "choiseGiveTypeType();"><input class=" wid" class=readonly readonly name=GiveTypeName id=GiveTypeName readonly=true></font>
                        <TD  class= title></TD>
                        <TD  class= input><Input class="wid" class=readonly readonly ></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                    </tr>
                </table>
                <Div  id= "divGiveTypeUnit1" style= "display: 'none'">
                    <table class= common>
                        <TR class= common>
                            <TD  class= title>调整金额</TD>
                            <TD  class= input><Input class=" wid" class=readonly readonly name=RealPay id=RealPay></TD>
                            <TD  class= title>调整原因</TD>
                            <TD  class= input><Input type=hidden name=AdjReason ondblclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1]);" onkeyup="return showCodeListKey('lldutyadjreason',[this,AdjReasonName],[0,1]);"><input class=" wid" class=readonly readonly name=AdjReasonName id=AdjReasonName readonly=true></font>
                            <TD  class= title></TD>
                            <TD  class= input></TD>
                        </tr>
                    </table>
                    <table class= common>
                        <TR class= common>
                            <TD class= title> 调整备注 </TD>
                        </tr>
                        <TR class= common>
                            <TD colspan="6" style="padding-left:16px"> <textarea name="AdjRemark" id="AdjRemark" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                        </tr>
                     </table>
                </div>
                <Div  id= "divGiveTypeUnit2" style= "display: 'none'">
                    <table class= common>
                        <TR class= common>
                            <TD  class= title>拒付原因</TD>
                            <TD  class= input><Input type=hidden name=GiveReason ondblclick="return showCodeList('llprotestreason',[this,GiveReasonName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,GiveReasonName],[0,1]);"><input class="wid" class=readonly readonly name=GiveReasonName id=GiveReasonName readonly=true></td>
                            <TD  class= title>拒付依据</TD>
                            <TD  class= input><Input class="wid" class=readonly readonly name=GiveReasonDesc id=GiveReasonDesc></td>
                            <TD  class= title></TD>
                            <TD  class= input></TD>
                        </tr>
                    </table>
                    <table class= common>
                        <TR class= common >
                            <TD class= title> 特殊备注 </TD>
                        </tr>
                        <TR class= common>
                            <TD colspan="6" style="padding-left:16px"> <textarea name="SpecialRemark" id="SpecialRemark" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <table>
            <tr>
                <td><INPUT class=cssButton name="AccidentDesc" VALUE=" 备注信息 "  TYPE=button onclick="showClaimDesc()"></td>
                <td><INPUT class=cssButton name="ViewReport" VALUE=" 查看呈报 "  TYPE=button onclick="showQuerySubmit()"></td>
                <td><INPUT class=cssButton name="ViewInvest" VALUE=" 查看调查 "  TYPE=button onclick="showQueryInq()"></td>
                <td><INPUT class=cssButton type = "hidden" name="" VALUE=" 二核结论 "  TYPE=button onclick="SecondUWInput()"></td>
                <td><INPUT class=cssButton name="" VALUE="核赔履历查询"  TYPE=button  onclick="LLQueryUWMDetailClick()" ></td>
       		    <td><INPUT class=cssButton name="MedicalFeeInp" VALUE=" 医疗单证 "  TYPE=button onclick="showLLMedicalFeeInp();"></td>
				<td><INPUT class=cssButton name="CreateNote" VALUE="受益人分配"  TYPE=button onclick="showBnf()"></td>      			
                <td><INPUT class=cssButton type = "hidden" name="medicalFeeCal" VALUE=" 费用查看 "  TYPE=button onclick="showLLMedicalFeeCal();"></td>
                <td><INPUT class=cssButton type = "hidden" name="CreateNote" VALUE=" 豁免查询 "  TYPE=button onclick="showExempt()"></td>
                <td><INPUT class=cssButton type = "hidden" name="" VALUE=" 保单结算 "  TYPE=button onclick="showPolDeal()"></td>
            </tr>
    </table>
    <table>
            <TR>
                <td><INPUT class=cssButton type = "hidden" name="" VALUE=" 合同处理 "  TYPE=button onclick="showContDeal()"></td>
                <!--td><INPUT class=cssButton name="" VALUE=" 帐户处理 "  TYPE=button onclick="showLCInsureAccont()"></td-->
            </tr>
        </table>
        <!--审核管理信息-->
        <Span id="spanAudit" style="display: 'none'">
            
            <table>
              <TR>
               <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLAudit);"></TD>
               <TD class= titleImg>审核管理</TD>
             </TR>
            </table>
            <Div id= "divLLAudit" style= "display: ''" class="maxbox1">
                 <table class= common>
                    <TR class= common>
                        <TD  class= title>审核结论</TD>
                        <TD  class= input><Input type=hidden name=AuditConclusion ondblclick="return showCodeList('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onkeyup="return showCodeListKey('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onfocus= "choiseConclusionType();"><input class="wid" class=readonly readonly name=AuditConclusionName id=AuditConclusionName readonly=true></TD>
                        <TD  class= title>特殊备注</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name=SpecialRemark1 id=SpecialRemark1></TD>
                        <TD  class= title>拒付原因</TD>
                            <TD  class= input><Input type=hidden name=ProtestReason ondblclick="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,ProtestReasonName],[0,1]);"><input class="wid" class=readonly readonly name=ProtestReasonName id=ProtestReasonName readonly=true></TD>
                        
                    </tr>
                </table>
                <Div id= "divLLAudit2" style= "display: 'none'">
                     <table class= common>
                        <TR class= common>
                            
                            <TD  class= title>拒付依据</TD>
                            <TD  class= input><Input class="wid" class=common disabled name=ProtestReasonDesc id=ProtestReasonDesc></TD>
                            <TD  class= title></TD>
                            <TD  class= input></TD><TD  class= title></TD>
                        <TD  class= input></TD>
                        </tr>
                    </table>
                </div>
                 <table class= common>
                    <TR class= common>
                        <TD  class= title>审核人</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="AuditPer" id="AuditPer" ></TD>
                        <TD  class= title>审核日期</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="AuditDate" id="AuditDate" ></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                    </tr>
                </table>
                <table class= common>
                    <TR class= common>
                        <TD class= title>审核意见</TD>
                    </tr>
                    <TR class= common>
                        <TD colspan="6" style="padding-left:16px"> <textarea name="AuditIdea" cols="226" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                    </tr>
                 </table>
            </Div>
        </span>
        <!--=========================================================================
            修改状态：结束
            修改原因：以上为审核信息，
            修 改 人：续涛
            修改日期：2005.05.13
            =========================================================================
        -->
          <!--审批结论信息-->
        <Span id= "spanConfirm" style="display: 'none';">
            
            <table>
                <tr>
                    <td class=common> <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divShowQueryDetail);"></td>
                    <td class= titleImg> 审批内容</td>
                </tr>
            </table>
            <Div  id= "divShowQueryDetail" style="display: '';" class="maxbox1">
                <table class= common>
                     <TR class= common>
                      <TD  class= title>审批结论</TD>
                        <TD  class= input><Input type=hidden name="DecisionSP"  ondblClick="showCodeList('llexamconclusion',[this,DecisionSPName],[0,1]);" onkeyup="showCodeListKeyEx('llexamconclusion',[this,DecisionSPName],[0,1]);" onfocus= "choiseQueryConclusionType();"><input class="wid" class=readonly readonly name=DecisionSPName id=DecisionSPName readonly></TD>
                        <TD  class= title>审批人</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="ExamPer" id="ExamPer" ></TD>
                        <TD  class= title>审批日期</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="ExamDate" id="ExamDate" ></TD>
                  </TR>
                </table>
                <Div id= "divLLQuery2" style= "display: 'none'">
                     <table class= common>
                        <TR class= common>
                            <TD  class= title>不通过原因</TD>
                            <TD  class= input><Input type=hidden name=ExamNoPassReason ondblclick="return showCodeList('llexamnopassreason',[this,ExamNoPassReasonName],[0,1]);" onkeyup="return showCodeListKey('llexamnopassreason',[this,ExamNoPassReasonName],[0,1]);"><input class="wid" class=readonly readonly name=ExamNoPassReasonName id=ExamNoPassReasonName readonly=true></TD>
                            <TD  class= title>不通过依据</TD>
                            <TD  class= input><Input class="wid" class=common name=ExamNoPassDesc id=ExamNoPassDesc></TD>
                            <TD  class= title></TD>
                            <TD  class= input></TD>
                        </tr>        
                    </table>
                </div>
                <table class= common>
                    <TR class= common>
                        <TD  class= title> 审批意见</TD>
                    </tr>
                    <TR class= common>
                        <TD colspan="6" style="padding-left:16px"> <textarea name="RemarkSP" cols="226%" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                    </tr>
                </table>
            </div>
         </span>
          <!--结案信息-->
        <Span id= "spanEndCase" style="display: 'none';">
            
            <table>
                <tr>
                    <td class=common> <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divShowEndCase);"></td>
                    <td class= titleImg> 结案信息</td>
                </tr>
            </table>
            <Div  id= "divShowEndCase" style="display: '';" class="maxbox1">
                <table class= common>
                     <TR class= common>
                        <TD  class= title>实际赔付金额</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="ClmEndRealPay" id="ClmEndRealPay" ></TD>
                        <TD  class= title>结案人</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="ClmEndPer" id="ClmEndPer" ></TD>
                        <TD  class= title>结案日期</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="ClmEndDate" id="ClmEndDate" ></TD>
                  </TR>
                     <TR class= common>
                        <TD  class= title>实付标志</TD>
                        <TD  class= input><Input class="wid" class=readonly readonly name="confGetMoney" id="confGetMoney" ></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                  </TR>
                </table>
            </div>
         </span>
    </div>
    
    <table>
        <tr>
            <td><INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()"></td>
        </tr>
    </table>
    <%
    //隐藏区,保存信息用
    %>
    <Input type=hidden name=customerNo >
    <input type=hidden id="fmtransact" name="fmtransact">

    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10代码-->
    <input type=hidden id="RgtClass" name="RgtClass"><!--团险个险代码-->
    <Input type=hidden id="Phase" name="Phase"><!--引用阶段-->
	<Input type=hidden id=SocialInsuFlag name=SocialInsuFlag>

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
