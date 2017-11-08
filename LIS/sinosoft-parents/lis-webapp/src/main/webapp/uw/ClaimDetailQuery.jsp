<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：ClaimDetailQuery.jsp
//程序功能：既往理赔详细查询
//创建日期：2005-10-08
//创建人  ：wuhao2
//更新记录：  更新人    更新日期     更新原因/内容
%>

<html>
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>

<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
  //String CurrentDate = PubFun.getCurrentDate();
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");

   String tClmNo = request.getParameter("ClmNo");

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
    <SCRIPT src="ClaimDetailQuery.js"></SCRIPT>
    <%@include file="ClaimDetailQueryInit.jsp"%>
</head>
<body  onload="initForm()" >

<form method=post name=fm id="fm" target="fraSubmit">
    <!--报案信息-->
    <table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaimDetailQuery1);"></TD>
            <TD class= titleImg>报案信息</TD>
        </TR>
    </table>

    <Div id= "divClaimDetailQuery1" class="maxbox" style= "display:">
        <table class= common>
            <TR class= common>
                <TD class= title> 事件号 </TD>
                <TD class= input> <Input class= "readonly wid" readonly name=AccNo1 id="AccNo1"></TD>
                <TD class= title> 赔案号 </TD>
                <TD class= input> <Input class="readonly wid" readonly  name=RptNo1  id="RptNo1"></TD>
                <TD class= title> 报案人姓名 </TD>
                <TD class= input> <input class="readonly wid"  readonly name=RptorName1 id="RptorName1" ></TD>
            </TR>
            <TR class= common>
                <TD class= title> 报案人电话 </TD>
                <TD class= input> <input class="readonly wid"  readonly name=RptorPhone1  id="RptorPhone1"></TD>
                <TD class= title> 报案人通讯地址 </TD>
                <TD class= input> <input class="readonly wid" readonly name=RptorAddress1 id="RptorAddress1" ></TD>
                <TD class= title> 报案人与事故人关系 </TD>
                <TD class= input> <Input type=hidden id="Relation1" name=Relation1 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" onDblClick="return showCodeList('llrgrelation',[this,Relation1Name],[0,1]);" onKeyUp="return showCodeListKey('llrgrelation',[this,Relation1Name],[0,1]);"><input class="readonly wid" readonly name=Relation1Name id="Relation1Name" ></TD>
            </TR>
            <TR class= common>
                <TD class= title> 报案方式 </TD>
                <TD class= input> <Input type=hidden id="RptMode" name="RptMode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" value="01" onDblClick="return showCodeList('llrptmode',[this,RptModeName],[0,1]);" onKeyUp="return showCodeListKey('llrptmode',[this,RptModeName],[0,1]);"><input class="readonly wid" readonly name="RptModeName" id="RptModeName" value="电话报案" ></TD>
                <TD class= title> 出险地点 </TD>
                <TD class= input> <input class="readonly wid" readonly name=AccidentSite1 id="AccidentSite1" ></TD>
                <TD class= title> 报案受理日期 </TD>
            <TD class= input>  <input class="readonly wid" readonly name="RptDate1" id="RptDate1" ></TD>
            </TR>
            <TR class= common>
                <TD class= title> 管辖机构 </TD>
                <TD class= input> <Input class= "readonly wid" readonly name=MngCom1 id="MngCom1"></TD>
                <TD class= title> 报案受理人 </TD> 
                <TD class= input> <Input class="readonly wid" readonly name=Operator1 id="Operator1" ></TD>
        	    	<TD class= title> 申请类型</TD>
          	    <TD class= input> <Input type=hidden value="1" id="RgtClass1" style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=RgtClass1 onDblClick="return showCodeList('llrgtclass',[this,RgtClass1Name],[0,1]);" onKeyUp="return showCodeListKey('llrgtclass',[this,RgtClass1Name],[0,1]);"><input class="readonly wid" readonly name=RgtClass1Name id="RgtClass1Name"  value="个人"></TD>
            </TR>
        </table>
    </Div>
    <!--立案信息-->
    <table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaimDetailQuery2);"></TD>
            <TD class= titleImg>立案信息</TD>
        </TR>
    </table>
        <Div id= "divClaimDetailQuery2" class="maxbox" style= "display: ">
        <table  class= common>
       	    <TR  class= common>
			          <TD  class= title> 事件号 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=AccNo2 id="AccNo2"></TD>
         	      <TD  class= title> 赔案号 </TD>
                <TD  class= input> <Input class="readonly wid" readonly  name=RptNo2 id="RptNo2" ></TD>
          	    <TD  class= title> 申请人姓名 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=RptorName2 id="RptorName2" ></TD>
            </TR>
            <TR  class= common>
              	<TD  class= title> 申请人电话 </TD>
              	<TD  class= input> <Input class= "readonly wid" readonly  name=RptorPhone2 id="RptorPhone2" ></TD>
            	  <TD  class= title> 申请人通讯地址 </TD>
                <TD  class= input> <Input class= "readonly wid" readonly  name=RptorAddress2 id="RptorAddress2" ></TD>
    	          <TD  class= title> 申请人与事故人关系 </TD>
    	          <TD  class= input> <Input type=hidden id="Relation2" style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=Relation2 onDblClick="return showCodeList('llrgrelation',[this,Relation2Name],[0,1]);" onKeyUp="return showCodeListKey('llrgrelation',[this,Relation2Name],[0,1]);"><input class="readonly wid" readonly name=Relation2Name id="Relation2Name" readonly=true></TD>
            </TR>
            <TR  class= common>
       	        <TD  class= title> 出险地点 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=AccidentSite2 id="AccidentSite2" ></TD>
       		      <TD  class= title> 申请受理日期 </TD>
          	    <TD  class= input> <input class="readonly wid" readonly name="RptDate2" id="RptDate2" ></TD>
			          <TD  class= title> 管辖机构 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=MngCom2 id="MngCom2"></TD>
	          </TR>
            <TR  class= common>
        	      <TD  class= title> 立案受理人 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=Operator2 id="Operator2" ></TD>
        	      <TD  class= title> 受托人类型 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeType id="AssigneeType" ></TD>
        	      <TD  class= title> 受托人代码 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeCode id="AssigneeCode" ></TD>
            </TR>
            <TR  class= common>
        	      <TD  class= title> 受托人姓名 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeName id="AssigneeName" ></TD>
        	      <TD  class= title> 受托人性别 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeSex id="AssigneeSex" ></TD>
        	      <TD  class= title> 受托人电话 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneePhone id="AssigneePhone" ></TD>
            </TR>
            <TR  class= common>
        	      <TD  class= title> 受托人地址 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeAddr id="AssigneeAddr" ></TD>
        	      <TD  class= title> 受托人邮编 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeZip id="AssigneeZip" ></TD>
        	      <TD  class= title> 申请类型</TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=RgtClassName id="RgtClassName" ></TD>
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
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaimDetailQuery3);"></td>
            <td class=titleImg> 出险人信息 </td>
        </tr>
    </table>
    <Div id= "divClaimDetailQuery3" class="maxbox" style= "display: ">
    <!--出险人信息表单-->
        <span id="spanSubReport" >
            <table class= common>
                <TR class= common>
                    <!--出险人编码,见隐藏区-->
                    <TD class= title> 出险人姓名 </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=customerName id="customerName"></TD>
                    <TD class= title> 出险人年龄 </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=customerAge id="customerAge"></TD>
                    <TD class= title> 出险人性别 </TD>
                    <TD class= input> <Input class="readonly wid" type=hidden readonly id="customerSex" style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=customerSex onDblClick="return showCodeList('sex',[this,customerSexName],[0,1]);" onKeyUp="return showCodeListKey('sex',[this,customerSexName],[0,1]);"><input class="readonly wid" readonly name=customerSexName id="customerSexName" readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> VIP客户标示</TD>
                    <TD class= input> <Input class="readonly wid" readonly name=IsVip id="IsVip"></TD>
                    <TD class= title> 事故日期</TD>
                    <TD class= input>  <input class= "readonly wid" readonly name="AccidentDate1" id="AccidentDate1" ></font></TD>
                    <TD class= title> 出险原因</TD>
                    <TD class= input><Input class="readonly wid" type=hidden name=occurReason id="occurReason" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onDblClick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onKeyUp="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class="readonly wid" readonly name=ReasonName id="ReasonName" readonly=true></font></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗医院</TD>
                    <TD class= input> <Input type=hidden id="hospital" style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=hospital onDblClick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onKeyUp="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class="readonly wid" readonly name=TreatAreaName id="TreatAreaName" readonly=true></TD>
                    <TD class= title> 出险日期 </TD>
                    <TD class= input>  <input class= "readonly wid" readonly name="AccidentDate2" id="AccidentDate2" ></TD>
                    <TD class= title> 出险细节</TD>
                    <TD class= input> <Input type=hidden id="accidentDetail" style="background:url(../common/images/select--bg_03.png) no-repeat right center" name=accidentDetail onDblClick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onKeyUp="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class="readonly wid" readonly name=accidentDetailName  id="accidentDetailName" readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗情况</TD>
                    <TD class= input> <Input type=hidden name=cureDesc id="cureDesc" style="background:url(../common/images/select--bg_03.png) no-repeat right center" onDblClick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onKeyUp="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class="readonly wid" readonly name=cureDescName id="cureDescName" readonly=true></TD>
                    <TD class= title> 出险结果1</TD>
                    <TD class= input> <Input type=hidden name=AccResult1 id="AccResult1" onDblClick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onKeyUp="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1]);" onFocus="saveIcdValue();"><input class="readonly wid" readonly name=AccResult1Name id="AccResult1Name" readonly=true></TD>
                    <TD class= title> 出险结果2</TD>
                    <TD class= input> <Input type=hidden name=AccResult2 id="AccResult2" onDblClick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onKeyUp="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');"><input class="readonly wid" readonly name=AccResult2Name id="AccResult2Name" readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 单证齐全标示</TD>
                    <TD class= input> <Input type=hidden id="IsAllReady" name=IsAllReady disabled CodeData="0|3^0|否^1|是" onDblClick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class="readonly wid" readonly name=IsAllReadyName id="IsAllReadyName" readonly=true></TD>
                    <TD class= title> 重要信息修改标示</TD>
                    <TD class= input> <Input type=hidden name=IsModify id="IsModify" disabled CodeData="0|3^0|否^1|是" onDblClick="return showCodeListEx('IsModify', [this,IsModifyName],[0,1])"onkeyup="return showCodeListKeyEx('IsModify', [this,IsModifyName],[0,1])"><Input class="readonly wid" readonly name=IsModifyName id="IsModifyName" readonly=true></TD>
                </tr>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> 理赔类型：<font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD align=left>
                        <input disabled type="checkbox" value="02" id="claimType" name="claimType" > 身故 </input>
                        <input disabled type="checkbox" value="03" id="claimType" name="claimType" > 高残 </input>
                        <input disabled type="checkbox" value="04" id="claimType" name="claimType"> 重大疾病 </input>
                        <input disabled type="checkbox" value="01" id="claimType" name="claimType"> 伤残 </input>
                        <input disabled type="checkbox" value="09" id="claimType" name="claimType" > 豁免 </input>
                        <input disabled type="checkbox" value="00" id="claimType" name="claimType" > 医疗 </input>
                        <input disabled type="checkbox" value="05" id="claimType" name="claimType" > 特种疾病 </input>
                        <input disabled type="checkbox" value="06" id="claimType" name="claimType" > 失业失能 </input>

                    </td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> 事故描述 </TD>
                </tr>
                <TR class= common>
                    <TD class= title> <textarea name="AccDesc" id=AccDesc cols="100" rows="2" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
                <TR class= common>
                    <TD class= title> 备注 </TD>
                </tr>
                <TR class= common>
                    <TD class= title> <textarea name="Remark" id=Remark cols="100" rows="2" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
            </table>
        </span>
    </div>
    <!--立案结论信息-->
    <table>
         <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaimDetailQuery4);"></td>
            <td class= titleImg>立案结论信息</td>
         </tr>
    </table>
    <Div  id= "divClaimDetailQuery4" class="maxbox1" style= "display:">
    	<table class= common>
    	     <TR class= common>
    	          <TD  class= title> 立案结论</TD>
    	          <TD  class= input> <Input type=hidden name=RgtConclusion id="RgtConclusion" onDblClick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onKeyUp="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input class="readonly wid" readonly name=RgtConclusionName id="RgtConclusionName" readonly=true></TD>
                <TD  class= title> 案件标识</TD>
                <TD  class= input> <Input type=hidden name=rgtType id="rgtType" onDblClick="return showCodeListEx('llrgttype', [this,rgtTypeName],[0,1])"onkeyup="return showCodeListKeyEx('llrgttype', [this,rgtTypeName],[0,1])"><Input class="readonly wid" readonly name=rgtTypeName id="rgtTypeName" readonly=true></TD>
                <TD  class= title> 赔案状态</TD>
                <TD  class= input> <Input type=hidden name=ClmState id="ClmState" onDblClick="return showCodeListEx('llclaimstate', [this,ClmStateName],[0,1])"onkeyup="return showCodeListKeyEx('llclaimstate', [this,ClmStateName],[0,1])"><Input class="readonly wid" readonly name=ClmStateName id="ClmStateName" readonly=true></TD>
    	     </tr>
    	</table>
    </div>
    <!--审核管理信息-->
      <Span id="spanAudit" style="display:">
        <table>
        <TR>
               <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaimDetailQuery5);"></TD>
               <TD class= titleImg>审核管理</TD>
        </TR>
        </table>
        <Div id= "divClaimDetailQuery5" class="maxbox1" style= "display: ">
        <table class= common>
        <TR class= common>
                <TD  class= title5>审核结论</TD>
                <TD  class= input5><Input type=hidden class="wid" name=AuditConclusion id="AuditConclusion" onDblClick="return showCodeList('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onKeyUp="return showCodeListKey('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onfocus= "choiseConclusionType();"><input class="readonly wid" readonly name=AuditConclusionName id="AuditConclusionName" readonly=true></TD>
                <TD  class= title5>特殊备注</TD>
                <TD  class= input5><Input class="readonly wid" readonly name=SpecialRemark1 id="SpecialRemark1"></TD>
                <TD  class= title5></TD>
                <TD  class= input5></TD>
         </tr>
         </table>
         <table class= common>
         <TR class= common>
                <TD  class= title5>审核人</TD>
                <TD  class= input5><Input class="readonly wid" readonly name="AuditPer" id="AuditPer" ></TD>
                <TD  class= title5>审核日期</TD>
                <TD  class= input5><Input class="readonly wid" readonly name="AuditDate" id="AuditDate" ></TD>
                <TD  class= title5></TD>
                <TD  class= input5></TD>
          </tr>
          </table>
         <table class= common>
          <TR class= common>
                <TD class= title>审核意见</TD>
          </tr>
          <TR class= common>
                <TD class= title> <textarea name="AuditIdea" id="AuditIdea" cols="100" rows="2" witdh=25% class="common" readonly ></textarea></TD>
          </tr>
          </table>
          </Div>
        </span>
       <!--审批结论信息-->
        <Span id= "spanConfirm" style="display: ;">
            <table>
                <tr>
                    <td class=common> <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divClaimDetailQuery6);"></td>
                    <td class= titleImg> 审批内容</td>
                </tr>
            </table>
            <Div  id= "divClaimDetailQuery6" class="maxbox1" style="display: ;">
                <table class= common>
               	    <TR class= common>
        	              <TD  class= title>审批结论</TD>
                        <TD  class= input><Input type=hidden id="DecisionSP" name="DecisionSP"  ondblClick="showCodeList('llexamconclusion',[this,DecisionSPName],[0,1]);" onKeyUp="showCodeListKeyEx('llexamconclusion',[this,DecisionSPName],[0,1]);" ><input class="readonly wid" readonly name=DecisionSPName id="DecisionSPName" readonly></TD>
                        <TD  class= title>审批人</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ExamPer" id="ExamPer" ></TD>
                        <TD  class= title>审批日期</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ExamDate" id="ExamDate" ></TD>
        	        </TR>
                </table>               
                <table class= common>
                    <TR class= common>
                        <TD  class= title> 审批意见</TD>
                    </tr>
                    <TR class= common>
                        <TD  class= title> <textarea name="RemarkSP" id="RemarkSP" cols="100%" rows="2" witdh=25% class="common" readonly ></textarea></TD>
                    </tr>
                </table>
            </div>
         </span>
        <!--结案信息-->
        <Span id= "spanEndCase" style="display: ;">
            <table>
                <tr>
                    <td class=common> <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divClaimDetailQuery7);"></td>
                    <td class= titleImg> 结案信息</td>
                </tr>
            </table>
            <Div  id= "divClaimDetailQuery7" class="maxbox" style="display: ;">
                <table class= common>
               	    <TR class= common>
                        <TD  class= title>实际赔付金额</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ClmEndRealPay" id="ClmEndRealPay" ></TD>
                        <TD  class= title>结案人</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ClmEndPer" id="ClmEndPer" ></TD>
                        <TD  class= title>结案日期</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="ClmEndDate" id="ClmEndDate" ></TD>
        	          </TR>
               	    <TR class= common>
                        <TD  class= title>实付标志</TD>
                        <TD  class= input><Input class="readonly wid" readonly name="confGetMoney" id="confGetMoney" ></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
        	        </TR>
                </table>
            </div>
         </span>
    <table>
        <tr>
            <td><INPUT class=cssButton id="goBack" name="goBack" VALUE="返  回"  TYPE=button onClick="top.close()"></td>
        </tr>
    </table>

	
	<Input class="readonly wid" type=hidden id="ClmNo" name="ClmNo" >
<Input type=hidden id="customerNo" name="customerNo">
<Input type=hidden id="ICDCode" name="ICDCode"><!--icd10代码-->
</form>
<br/><br/><br/><br/>
</body>
</html>
