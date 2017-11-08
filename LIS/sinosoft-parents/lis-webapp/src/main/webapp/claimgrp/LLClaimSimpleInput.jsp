<%
//**************************************************************************************************
//Name：LLClaimSimpleInput.jsp
//Function：简易案件工作队列信息
//Author：zl
//Date: 2005-6-21 10:20
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

	  String tClmNo = request.getParameter("claimNo");	//赔案号

	  String tMissionID = request.getParameter("MissionID");  //工作流任务ID
	  String tSubMissionID = request.getParameter("SubMissionID");  //工作流子任务ID
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
    <SCRIPT src="LLClaimSimple.js"></SCRIPT>
    <%@include file="LLClaimSimpleInit.jsp"%>
</head>
<body onload="initForm();" >
<form method=post name=fm target="fraSubmit">
    <!--立案信息-->
    <table>
        <TR>
            <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
            <TD class= titleImg>立案信息</TD>
        </TR>
    </table>
    <Div  id= "divLLReport1" style= "display: ''">
        <table  class= common>
       	    <TR  class= common>
			    <TD  class= title> 事件号 </TD>
          	    <TD  class= input> <Input class= "readonly" readonly name=AccNo></TD>
         	    <TD  class= title> 赔案号 </TD>
                <TD  class= input> <Input class="readonly" readonly  name=RptNo ></TD>
          	    <TD  class= title> 申请人姓名 </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=RptorName ></TD>
            </TR>
            <TR  class= common>
              	<TD  class= title> 申请人电话 </TD>
              	<TD  class= input> <Input class= "readonly" readonly  name=RptorPhone ></TD>
            	<TD  class= title> 申请人通讯地址 </TD>
                <TD  class= input> <Input class= "readonly" readonly  name=RptorAddress ></TD>
    	        <TD  class= title> 申请人与事故人关系 </TD>
    	        <TD  class= input> <Input class=codeno disabled name=Relation ondblclick="return showCodeList('relation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('relation',[this,RelationName],[0,1]);"><input class=codename name=RelationName readonly=true></TD>
            </TR>
            <TR  class= common>
       	        <TD  class= title> 出险地点 </TD>
          	    <TD  class= input> <Input class= "readonly" readonly name=AccidentSite ></TD>
       		    <TD  class= title> 申请受理日期 </TD>
          	    <TD  class= input> <input class="readonly" readonly name="RptDate" ></TD>
			    <TD  class= title> 管辖机构 </TD>
          	    <TD  class= input> <Input class= "readonly" readonly name=MngCom></TD>
	        </TR>
            <TR  class= common>
        	    <TD  class= title> 立案受理人 </TD>
          	    <TD  class= input> <Input class="readonly" readonly name=Operator ></TD>
        	    <TD  class= title> 受托人类型 </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=AssigneeType ></TD>
        	    <TD  class= title> 受托人代码 </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=AssigneeCode ></TD>
            </TR>
            <TR  class= common>
        	    <TD  class= title> 受托人姓名 </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=AssigneeName ></TD>
        	    <TD  class= title> 受托人性别 </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=AssigneeSex ></TD>
        	    <TD  class= title> 受托人电话 </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=AssigneePhone ></TD>
            </TR>
            <TR  class= common>
        	    <TD  class= title> 受托人地址 </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=AssigneeAddr ></TD>
        	    <TD  class= title> 受托人邮编 </TD>
          	    <TD  class= input> <Input class= "readonly" readonly  name=AssigneeZip ></TD>
        	    <TD  class= title> 申请类型</TD>
          	    <TD  class= input> <Input class= readonly readonly name=RgtClassName ></TD>
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
            <!--
                <td><input class=cssButton name=QueryPerson value="出险人查询" type=button onclick="showInsPerQuery()" ></td>
                <td><input class=cssButton name=QueryReport value=" 事件查询 " type=button onclick="queryLLAccident()"></td>
                <td>
                    <span id="operateButton21" style= "display: none"><input class=cssButton name=addbutton  VALUE="保  存"  TYPE=button onclick="saveClick()" ></span>
                    <span id="operateButton22" style= "display: none"><input class=cssButton name=updatebutton  VALUE="修  改"  TYPE=button onclick="updateClick()" ></span>
                </td>
             -->
                <td><input class=cssButton name=QueryCont2 VALUE=" 保单查询 "  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><input class=cssButton name=QueryCont3 VALUE="既往赔案查询" TYPE=button onclick="showOldInsuredCase()"></td>
				<!--td><input class=cssButton name=QueryCont5 VALUE="重要信息修改" TYPE=button onclick="editImpInfo()"></td-->
                <td><input class=cssButton name=BarCodePrint VALUE="打印条形码"  TYPE=button onclick="colBarCodePrint();"></td>
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
                    <TD class= input> <Input type=hidden name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=readonly readonly name=SexName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> VIP客户标示</TD>
                    <TD class= input> <Input class="readonly" readonly name=IsVip></TD>
                    <TD class= title> 意外事故发生日期</TD>
                    <TD class= input>  <input class= "readonly" readonly name="AccidentDate" ></font></TD>
                    <TD class= title> 出险原因</TD>
                    <TD class= input><Input class=codeno disabled name=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class=codename name=ReasonName readonly=true></font></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗医院</TD>
                    <TD class= input> <Input class=codeno disabled name=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName readonly=true></TD>
                    <TD class= title> 出险日期 </TD>
                    <TD class= input>  <input class= "readonly" readonly name="AccidentDate2" ></TD>
                    <TD class= title> 出险细节</TD>
                    <TD class= input> <Input class=codeno disabled name=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class=codename name=accidentDetailName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗情况</TD>
                    <TD class= input> <Input class=codeno disabled name=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName readonly=true></TD>
                    <TD class= title> 出险结果1</TD>
                    <TD class= input> <Input class=codeno disabled name=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1]);" onFocus="saveIcdValue();"><input class=codename name=AccResult1Name readonly=true></TD>
                    <TD class= title> 出险结果2</TD>
                    <TD class= input> <Input class=codeno disabled name=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');"><input class=codename name=AccResult2Name readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 单证齐全标示</TD>
                    <TD class= input> <Input class=codeno name=IsAllReady disabled CodeData="0|3^0|否^1|是" ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class=codename name=IsAllReadyName readonly=true></TD>
                    <TD class= title> 重要信息修改标示</TD>
                    <TD class= input> <Input class=codeno name=IsModify disabled CodeData="0|3^0|否^1|是" ondblclick="return showCodeListEx('IsModify', [this,IsModifyName],[0,1])"onkeyup="return showCodeListKeyEx('IsModify', [this,IsModifyName],[0,1])"><Input class=codename name=IsModifyName readonly=true></TD>
                </tr>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> 理赔类型：<font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD align=left>
                        <input disabled type="checkbox" value="02" name="claimType" onclick="callClaimType('02')"> 死亡 </input>
                        <input disabled type="checkbox" value="03" name="claimType" onclick="callClaimType('03')"> 高残 </input>
                        <input disabled type="checkbox" value="04" name="claimType"> 大病 </input>
                        <input disabled type="checkbox" value="01" name="claimType"> 伤残 </input>
                        <input disabled type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> 豁免 </input>
                        <input disabled type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> 医疗 </input>
                        <input disabled type="checkbox" value="05" name="claimType" > 特种疾病 </input>
                        <input disabled type="checkbox" value="06" name="claimType" > 失能 </input>

                    </td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> 事故描述 </TD>
                </tr>
                <TR class= common>
                    <TD class= input> <textarea name="AccDesc" cols="100" rows="2" witdh=25% class="common" disabled readonly ></textarea></TD>
                </tr>
                <TR class= common>
                    <TD class= title> 备注 </TD>
                </tr>
                <TR class= common>
                    <TD class= input> <textarea name="Remark" cols="100" rows="2" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
            </table>
        </span>
    </div>
    <hr>
    <!--立案结论信息-->
    <table>
         <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit6);"></td>
            <td class= titleImg>立案结论信息</td>
         </tr>
    </table>
    <Div  id= "divBaseUnit6" style= "display:''">
    	<table class= common>
    	    <TR class= common>
    	        <TD  class= title>立案结论</TD>
    	        <TD  class= input>
    	        <Input class=codeno disabled name=RgtConclusion ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input class=codename name=RgtConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font>
    	        </TD>
                <TD  class= title> 案件标识</TD>
                <TD  class= input> <Input class=codeno disabled name=rgtType ondblclick="return showCodeListEx('llrgttype', [this,rgtTypeTypeName],[0,1])"onkeyup="return showCodeListKeyEx('llrgttype', [this,rgtTypeTypeName],[0,1])"><Input class=codename name=rgtTypeTypeName readonly=true></TD>
    	        <td>&nbsp;&nbsp;</td>
    	     </tr>
    	</table>
    </div>
    <hr>
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
                    <TD  class= input><Input class=codeno name=GiveType disabled ondblclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llpayconclusion',[this,GiveTypeName],[0,1]);" onfocus= "choiseGiveTypeType();"><input class=codename name=GiveTypeName readonly=true></font>
                    <TD  class= title></TD>
                    <TD  class= input><Input class=readonly readonly ></TD>
                    <TD  class= title></TD>
                    <TD  class= input></TD>
                </tr>
            </table>
            <Div  id= "divGiveTypeUnit1" style= "display: 'none'">
                <table class= common>
                    <TR class= common>
                        <TD  class= title>调整金额</TD>
                        <TD  class= input><Input class=common name=RealPay onblur="checkAdjMoney();"></TD>
                        <TD  class= title>调整原因</TD>
                        <TD  class= input><Input class=codeno name=AdjReason ondblclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1]);" onkeyup="return showCodeListKey('lldutyadjreason',[this,AdjReasonName],[0,1]);"><input class=codename name=AdjReasonName readonly=true></font>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                    </tr>
                </table>
                <table class= common>
                    <TR class= common>
                        <TD class= title> 调整备注 </TD>
                    </tr>
                    <TR class= common>
                        <TD class= input> <textarea name="AdjRemark" cols="100" rows="2" witdh=25% class="common"></textarea></TD>
                    </tr>
                 </table>
            </div>
            <Div  id= "divGiveTypeUnit2" style= "display: 'none'">
                <table class= common>
                    <TR class= common>
                        <TD  class= title>拒付原因</TD>
                        <TD  class= input><Input class=codeno name=GiveTypeDesc ondblclick="return showCodeList('llprotestreason',[this,GiveTypeDescName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,GiveTypeDescName],[0,1]);"><input class=codename name=GiveTypeDescName readonly=true></td>
                        <TD  class= title>拒付依据</TD>
                        <TD  class= input><Input class=common name=GiveReason></td>
                        <TD  class= title></TD>
                        <TD  class= input></TD>                                                
                    </tr>
                </table>
                <table class= common>
                    <TR class= common >
                        <TD class= title> 特殊备注 </TD>
                    </tr>
                    <TR class= common>
                        <TD class= input> <textarea name="SpecialRemark" cols="100" rows="2" witdh=25% class="common"></textarea></TD>
                    </tr>
                </table>
            </div>
            <table>
                 <tr>
                     <td><INPUT class=cssButton name="addUpdate" VALUE=" 添加修改 "  TYPE=button onclick="AddUpdate()" ></td>
                     <td><INPUT class=cssButton name="saveUpdate" VALUE=" 保存修改 "  TYPE=button onclick="SaveUpdate();" ></td>
                 </tr>
            </table>
        </div>
    </div>
    <hr>
    <table>
        <tr>
            <td><INPUT class=cssButton name="dutySet" VALUE=" 匹配并理算 "  TYPE=button onclick="showMatchDutyPay();"></td>
            <td><INPUT class=cssButton name="MedicalFeeInp" VALUE="医疗单证调整"  TYPE=button onclick="showLLMedicalFeeInp();"></td>
            <td><INPUT class=cssButton name="" VALUE="单证费用调整"  TYPE=button onclick="showLLMedicalFeeAdj();"></td>
            <td><INPUT class=cssButton name="medicalFeeCal" VALUE="费用计算查看"  TYPE=button onclick="showLLMedicalFeeCal();"></td>
            <td><INPUT class=cssButton name="AccidentDesc" VALUE=" 备注信息 "  TYPE=button onclick="showClaimDesc()"></td>
            <td><INPUT class=cssButton name="" VALUE="受益人分配"  TYPE=button onclick="showBnf()"></td>
        </tr>
    </table>
    <hr>
    <!--简易案件信息-->
    <table>
         <tr>
         	 <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSimpleClaim);"></td>
             <td class= titleImg>简易案件信息</td>
         </tr>
    </table>
    <Div  id= "divSimpleClaim" style= "display:''">
        <table class= common>
            <TR class= common>
                <TD  class= title>简易案件结论</TD>
                <TD  class= input><Input class=codeno name=SimpleConclusion ondblClick="showCodeList('llexamconclusion',[this,SimpleConclusionName],[0,1]);" onkeyup="showCodeListKeyEx('llexamconclusion',[this,SimpleConclusionName],[0,1]);"><input class=codename name=SimpleConclusionName readonly><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
            </tr>
        </table>
    </div>
    <hr>
    <INPUT class=cssButton name="simpleClaim" VALUE="简易案件确认"  TYPE=button onclick="confirmClick()">
    <INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()">

    <%
    //隐藏区,保存信息用
    %>
    <Input type=hidden name=customerNo >
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden name=hideOperate value=''>
    <input type=hidden id="isReportExist" name="isReportExist"><!--是否为新增事件,不判断则为空-->

    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    <input type=hidden id="isNew" name="isNew"><!--是否创建新建-->
    <input type=hidden id="clmState" name="clmState"><!--立案状态-->
    <Input type=hidden id="ICDCode" name="ICDCode"><!--icd10代码-->
    <input type=hidden id="RgtClass" name="RgtClass"><!--团险个险代码-->
    <input type=hidden id="BudgetFlag" name="BudgetFlag"><!--是否预付-->
    <input type=hidden id="AuditPopedom" name="AuditPopedom"><!--审核权限-->

    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
