<%
//**************************************************************************************************/
//Name：LLClaimAuditInput.jsp
//Function：审核管理
//Author：zl
//Date: 2005-6-20 14:00
//Desc:
//修改：niuzj,2006-01-13,新增“理赔金领取方式”字段
//**************************************************************************************************/
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
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");

	  String tClmNo = request.getParameter("claimNo");	//赔案号

	  String tMissionID = request.getParameter("MissionID");  //工作流任务ID
	  String tSubMissionID = request.getParameter("SubMissionID");  //工作流子任务ID
	  String tActivityid = request.getParameter("Activityid");  //节点ID
	  String tBudgetFlag = request.getParameter("BudgetFlag");
	  String tAuditPopedom = request.getParameter("AuditPopedom");
	  String tGrpRptNo = request.getParameter("GrpRptNo");  //团体赔案号
	  String tRgtClass = request.getParameter("RgtClass");  //申请类型
	  String tRgtObj = request.getParameter("RgtObj");      //号码类型
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
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <SCRIPT src="LLClaimAudit.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLClaimAuditInit.jsp"%>

</head>
<body onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit">
    <!--立案信息-->
    
    <table>
        <TR>
            <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
            <TD class= titleImg>立案信息</TD>
        </TR>
    </table>
    <Div  id= "divLLReport1" style= "display: ''" class="maxbox">
        <table  class= common>
       	    <TR  class= common>
			          <TD  class= title> 事件号 </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly name=AccNo id=AccNo></TD>
         	      <TD  class= title> 立案号 </TD>
                <TD  class= input> <Input class="wid" class="readonly" readonly  name=RptNo id=RptNo ></TD>
          	    <TD  class= title> 申请人姓名 </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=RptorName id=RptorName ></TD>
            </TR>
            <TR  class= common>
              	<TD  class= title> 申请人电话 </TD>
              	<TD  class= input> <Input class="wid" class= "readonly" readonly  name=RptorPhone id=RptorPhone ></TD>
              	<TD  class= title> 申请人手机号码 </TD>
              	<TD  class= input> <Input class="wid" class= "readonly" readonly name=RptorMoPhone id=RptorMoPhone ></TD>
                <TD  class= title> 申请人详细地址 </TD>
                <TD  class= input> <Input class="wid" class= "common3" readonly  name=RptorAddress id=RptorAddress ></TD>
            </TR>
           
            <TR  class= common>
            		<TD  class= title> 申请人邮编 </TD>
								<TD  class= input><Input class="wid" class= "readonly" readonly name="AppntZipCode" id="AppntZipCode" ></TD>
            		<TD  class= title> 申请人与出险人关系 </TD>
    	          <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=Relation ondblclick="showCodeList('llrgrelation',[this,RelationName],[0,1]);" onMouseDown="showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class=codename name=RelationName readonly=true></TD>
       	        <TD  class= title> 出险地点 </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly name=AccidentSite id=AccidentSite ></TD>
	          </TR>
            <TR  class= common>
              	<TD  class= title> 立案日期 </TD>
          	    <TD  class= input> <!--<input class="readonly" readonly name="RptDate" >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#RptDate'});" verify="有效开始日期|DATE" dateFormat="short" name=RptDate id=RptDate id="RptDate"><span class="icon"><a onClick="laydate({elem: '#RptDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
			          <TD  class= title> 管辖机构 </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly name=MngCom id=MngCom></TD>
        	      <TD  class= title> 立案受理人 </TD>
          	    <TD  class= input> <Input class="wid" class="readonly" readonly name=Operator id=Operator ></TD>
            </TR>
            <TR  class= common>
             		<TD  class= title> 受托人类型 </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=AssigneeType id=AssigneeType ></TD>
        	      <TD  class= title> 受托人代码 </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=AssigneeCode id=AssigneeCode ></TD>
        	      <TD  class= title> 受托人姓名 </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=AssigneeName id=AssigneeName ></TD>
            </TR>
            <TR  class= common>
            	  <TD  class= title> 受托人性别 </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=AssigneeSex id=AssigneeSex ></TD>
        	      <TD  class= title> 受托人电话 </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=AssigneePhone id=AssigneePhone ></TD>
        	      <TD  class= title> 受托人地址 </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=AssigneeAddr id=AssigneeAddr ></TD>
        	   </TR>  
        	   <TR  class= common>
        	      <TD  class= title> 受托人邮编 </TD>
          	    <TD  class= input> <Input class="wid" class= "readonly" readonly  name=AssigneeZip id=AssigneeZip ></TD>
           <!-- <TD  class= title> 申请类型</TD>
          	    <TD  class= input> <Input class= "readonly" readonly name=RgtClassName ></TD>  -->
                <TD class= title> 交接日期 </TD>
                <TD class= input>  <!--<input class="readonly" readonly  name="AcceptedDate";">-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#AcceptedDate'});" verify="有效开始日期|DATE" dateFormat="short" name=AcceptedDate id="AcceptedDate"><span class="icon"><a onClick="laydate({elem: '#AcceptedDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                <font size=1 color='#ff0000'><b>*</b></font></TD>            
           			<TD class= title> 客户申请日期</TD>
                <TD class= input>  <!--<input class="readonly" readonly  name="ApplyDate";">-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#ApplyDate'});" verify="有效开始日期|DATE" dateFormat="short" name=ApplyDate id="ApplyDate"><span class="icon"><a onClick="laydate({elem: '#ApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                <font size=1 color='#ff0000'><b>*</b></font></TD>           
            </TR>
            <TR  class= common>
            	  <TD  class= title>理赔金领取方式</TD>
          	    <TD  class= input> <Input class="wid" class= readonly readonly name=GetMode ></TD>
				<TD  class= title>补充材料受理日期</TD>
          	    <TD  class= input> <!--<Input class= readonly readonly name=AddAffixAccDate >-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#AddAffixAccDate'});" verify="有效开始日期|DATE" dateFormat="short" name=AddAffixAccDate id="AddAffixAccDate"><span class="icon"><a onClick="laydate({elem: '#AddAffixAccDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
          	    <TD  class= title>
          	       <span id="spanRgtObjNo1" style= "display: none">       
          	           团体赔案号
          	       </span>
          	    </TD>
          	    
          	    <TD  class= input> 
          	       <span id="spanRgtObjNo2" style= "display: none"> 	
          	           <Input class="wid" class= readonly readonly name=RgtObjNo id=RgtObjNo >
          	       </span>
          	    </TD>   
            </TR>
        </table>
        </Div>
	<br />
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
                    <TD class= input> <Input class="wid" class="readonly" readonly name=customerName id=customerName></TD>
                    <TD class= title> 年龄 </TD>
                    <TD class= input> <Input class="wid" class="readonly" readonly name=customerAge id=customerAge></TD>
                    <TD class= title> 性别 </TD>
                    <TD class= input> <Input class="wid" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" type=hidden name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onMouseDown="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class="wid" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=readonly readonly name=SexName readonly=true></TD>
                </TR>
                <TR class= common>
                    <!-- <TD class= title> VIP客户标识</TD>
                    <TD class= input> <Input type=hidden name=IsVip><input class=readonly readonly name=VIPValueName readonly=true></TD> -->
                    <TD class= title> 事故日期</TD>
                    <TD class= input> <!--<input class="readonly" readonly name="AccidentDate" >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#AccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=AccidentDate id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    <font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class= title> 医疗出险日期 </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="MedicalAccidentDate" >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#MedicalAccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=MedicalAccidentDate id="MedicalAccidentDate"><span class="icon"><a onClick="laydate({elem: '#MedicalAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    <font size=1 color='#ff0000'><b>*</b></font></TD>              
                    <TD class= title> 其他出险日期 </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="OtherAccidentDate" >-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#OtherAccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=OtherAccidentDate id="OtherAccidentDate"><span class="icon"><a onClick="laydate({elem: '#OtherAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    <font size=1 color='#ff0000'><b>*</b></font></TD>
					</TR>
                <TR class= common>
                    <TD class= title> 治疗医院</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=hospital id=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onMouseDown="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName readonly=true></TD>
					<TD class= title> 出险原因</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onMouseDown="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class=codename name=ReasonName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class= title> 意外细节</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=accidentDetail
                    id=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onMouseDown="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class=codename name=accidentDetailName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗情况</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=cureDesc id=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onMouseDown="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName readonly=true></TD>
                    <TD class= title> 出险结果1</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=AccResult1 id=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1]);" onMouseDown="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onFocus="saveIcdValue();"><input class=codename name=AccResult1Name readonly=true></TD>
                    <TD class= title> 出险结果2</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=AccResult2 id=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onMouseDown="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');"><input class=codename name=AccResult2Name readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 单证齐全标识</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=IsAllReady id=IsAllReady disabled CodeData="0|^0|否^1|是" ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])" onMouseDown="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])" onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class=codename name=IsAllReadyName readonly=true></TD>
                    <TD class= title> 重要信息修改标识</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=IsModify id=IsModify disabled CodeData="0|^0|否^1|是" ondblclick="return showCodeListEx('IsAllReady', [this,IsModifyName],[0,1])" onMouseDown="return showCodeListEx('IsAllReady', [this,IsModifyName],[0,1])" onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsModifyName],[0,1])"><Input class=codename name=IsModifyName readonly=true></TD>
                    <TD class= title> 案件标识</TD>
                    <TD class= input> <Input class="wid" class=readonly name=ClaimFlag readonly ></TD>
                </tr>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> 理赔类型：<font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD class="tet" align=left>
                        <input class="ver" type="checkbox" value="02" name="claimType" onclick="callClaimType('02');"> <span>身故</span> </input>
                        <input class="ver" type="checkbox" value="03" name="claimType" onclick="callClaimType('03');"> <span>高残</span> </input>
                        <input class="ver" type="checkbox" value="04" name="claimType" onclick="callClaimType('04');"> <span>重大疾病</span> </input>
                        <input class="ver" type="checkbox" value="01" name="claimType" onclick="callClaimType('01')"> <span>残疾、烧伤、骨折、重要器官切除</span> </input>
                        <input class="ver" type="checkbox" value="09" name="claimType" onclick="callClaimType('09')"> <span>豁免</span> </input>
                        <input class="ver" type="checkbox" value="00" name="claimType" onclick="callClaimType('00')"> <span>医疗</span> </input>
                        <input class="ver" type="checkbox" value="05" name="claimType" onclick="callClaimType('05')"> <span>特种疾病</span> </input>
                        <!-- <input type="checkbox" value="06" name="claimType" > 失业失能 </input> -->
                    </td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> 事故描述 </TD>
                    </tr>
                <TR class= common>
                    <TD> <textarea style="margin-left:16px" name="AccDesc" cols="199" rows="4" witdh=25% class="common" ></textarea></TD>
                   
                </TR>
                <TR class= common>
                    <TD class= title> 备注 </TD>
                </tr>
                <TR class= common>
                    <TD class= input> <textarea style="margin-left:16px" name="Remark" cols="199" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
            </table>
        </span>
    </div>
     <!--<table>
            <tr>-->
            <!--
                <td><input class=cssButton name=QueryPerson value="出险人查询" type=button onclick="showInsPerQuery()" ></td>
                <td><input class=cssButton name=QueryReport value=" 事件查询 " type=button onclick="queryLLAccident()"></td>
                <td>
                    <span id="operateButton21" style= "display: none"><input class=cssButton name=addbutton  VALUE="保  存"  TYPE=button onclick="saveClick()" ></span>
                    <span id="operateButton22" style= "display: none"><input class=cssButton name=updatebutton  VALUE="修  改"  TYPE=button onclick="updateClick()" ></span>
                </td>
             -->
                <input class=cssButton name=QueryCont2 VALUE=" 保单查询 "  TYPE=button onclick="showInsuredLCPol()">
                <input class=cssButton name=QueryCont3 VALUE="既往赔案查询" TYPE=button onclick="showOldInsuredCase()">
				<input class=cssButton name=QueryCont5 VALUE="重要信息修改" TYPE=button onclick="editImpInfo()"></td>
                <input class=cssButton name=BarCodePrint VALUE="打印条形码"  TYPE=button onclick="colBarCodePrint();">
                <input class=cssButton name=ColQueryImage VALUE="影像查询"  TYPE=button onclick="colQueryImage();">                

    <br />
	<!--=========================================================================
        修改状态：开始
        修改原因：以下为立案信息，
        修 改 人：续涛
        修改日期：2005.05.13
        =========================================================================
    -->

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
    	        <TD  class= title5>立案结论</TD>
    	        <TD  class= input5>
    	        <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=RgtConclusion id=RgtConclusion ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onMouseDown="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input class=codename name=RgtConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font>
    	        </TD>

 				<TD  class= title5>是否预付</TD>
    	        <TD  class= input5>
    	        <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno  name=whetherPrePay id=whetherPrePay ondblclick="return showCodeList('whetherpreconclusion',[this,whetherPrePayName],[0,1]);" onMouseDown="return showCodeList('whetherpreconclusion',[this,whetherPrePayName],[0,1]);" onkeyup="return showCodeListKey('whetherpreconclusion',[this,whetherPrePayName],[0,1]);"><input class=codename name=whetherPrePayName readonly=true>
    	        </TD>
               
                <!--Modify by zhaorx
                <TD  class= title> 案件标识</TD>
                <TD  class= input> <Input class=codeno disabled name=rgtType ondblclick="return showCodeListEx('llrgttype', [this,rgtTypeTypeName],[0,1])"onkeyup="return showCodeListKeyEx('llrgttype', [this,rgtTypeTypeName],[0,1])"><Input class=codename name=rgtTypeTypeName readonly=true></TD>
                -->
    	        <td>&nbsp;&nbsp;</td>
    	     </tr>
    	</table>
    </div>
    
        	<INPUT class=cssButton name="dutySet1" VALUE="投连结算"  TYPE=button onclick="clearAcc();"></td>
            <INPUT class=cssButton name="dutySet" VALUE=" 匹配并理算 "  TYPE=button onclick="showMatchDutyPay();"></td>
            <INPUT class=cssButton name="MedicalFeeInp" VALUE="医疗单证调整"  TYPE=button onclick="showLLMedicalFeeInp();"></td>
            <INPUT class=cssButton type=hidden name="CertifyFeeInp" VALUE="单证费用调整"  TYPE=button onclick="showLLMedicalFeeAdj();"></td>
			<INPUT class=cssButton name="medicalFeeCal" VALUE="费用计算查看"  TYPE=button onclick="showLLMedicalFeeCal();"></td>

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
                    <TD  class= input><Input class=codeno name=GiveType ondblclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llpayconclusion',[this,GiveTypeName],[0,1]);" onfocus= "choiseGiveTypeType();"><input class=codename name=GiveTypeName readonly=true></font>
                    <td class=title>币种</td>
    					<td class=input><Input class=common name=Currency readonly=true></td>
                    <TD  class= title></TD>
                    <TD  class= input></TD>
                </tr>
            </table>
            <Div  id= "divGiveTypeUnit1" style= "display: none">
                <table class= common>
                    <TR class= common>
                        <TD  class= title>调整金额</TD>
                        <TD  class= input><Input class=common name=RealPay ></TD>
                        <TD  class= title>调整原因</TD>
                        <TD  class= input><Input class=codeno name=AdjReason ondblclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1],null,'1 and code <> #14#',1,null,'150');" onkeyup="return showCodeListKey('lldutyadjreason',[this,AdjReasonName],[0,1],null,'1 and code <> #14#',1,null,'150');"><input class=codename name=AdjReasonName readonly=true></font>
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
            <Div  id= "divGiveTypeUnit2" style= "display: none">
                <table class= common>
                    <TR class= common>
                        <TD  class= title>拒付原因</TD>
                        <TD  class= input><Input class=codeno name=GiveReason ondblclick="return showCodeList('llprotestreason',[this,GiveReasonName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,GiveReasonName],[0,1]);"><input class=codename name=GiveReasonName readonly=true></td>
                        <TD  class= title></TD>
                        <TD  class= input></TD>                                                
                    </tr>
                </table>
                <table class= common>
                    <TR class= common >
                        <TD class= title> 拒付依据 </TD>
                    </tr>
                    <TR class= common>
                        <TD class= input> <textarea name="GiveReasonDesc" cols="100" rows="2" witdh=25% class="common"></textarea></TD>
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
    <!--=========================================================================
        修改状态：结束
        修改原因：以上为立案信息，
        修 改 人：续涛
        修改日期：2005.05.13
        =========================================================================
    -->
    <!--=========================================================================
        修改状态：开始
        修改原因：以下为审核信息，
        修 改 人：续涛
        修改日期：2005.05.13
        =========================================================================
    -->
    <!--<table>-->
        <tr>
	        <td><INPUT class=cssButton name="DoHangUp" VALUE=" 保单挂起 "  TYPE=button onclick="showLcContSuspend()" ></td>
            <!--<td><INPUT class=cssButton name="AccidentDesc" VALUE="   备注信息   "  TYPE=button onclick="showClaimDesc()"></td>-->
            <!--<td><INPUT class=cssButton name="ViewReport" VALUE=" 查看呈报 "  TYPE=button onclick="showQuerySubmit()"></td>-->
            <td><INPUT class=cssButton name="BeginInq" VALUE=" 发起调查 "  TYPE=button onclick="showBeginInq()"></td>
            <!--<td><INPUT class=cssButton name="ViewInvest" VALUE=" 查看调查 "  TYPE=button onclick="showQueryInq()"></td>-->
	    </tr><br>
        <tr>
            <td><INPUT id="demo" name="SecondUW" style = "display:''" class=cssButton name="" VALUE=" 发起二核 "  TYPE=button onclick="showSecondUWInput()"></td>
            <td><INPUT id="demo" name="SecondUWResult" style = "display:''" class=cssButton name="" VALUE=" 二核结论查询 "  TYPE=button onclick="SecondUWInput();"></td>
            <td><INPUT class=cssButton name="ExemptDeal" VALUE=" 豁免处理 "  TYPE=button onclick="showExempt();"></td>            
            <td><INPUT class=cssButton name="PolnoCheckIn " VALUE=" 保单结算 "  TYPE=button onclick="showPolDeal();"></td>
            <td><INPUT class=cssButton name="ContDeal" VALUE=" 合同处理 "  TYPE=button onclick="showContDeal();"></td>
            <td><INPUT class=cssButton name="BeneficiaryAssign" VALUE="受益人分配"  TYPE=button onclick="showBnf()"></td>
            <td><INPUT class=cssButton name="AccountDeal" VALUE=" 帐户处理 "  TYPE=button onclick="showLCInsureAccont()"></td>
        </tr><br><br><hr class="line"><br>
        <!-- wyc add -->
        <!-- 
         -->
        <a href="javascript:void(0);" name="benefit" class="button" onClick="benefit();">历史账单</a>
        <a href="javascript:void(0);" name="claimCheck" class="button" onClick="claimCheck();">本次账单核对</a>
        
        
    <!--</table>-->
    <!--审核管理信息-->
    
    <table>
      <TR>
       <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLAudit);"></TD>
       <TD class= titleImg>审核管理</TD>
     </TR>
    </table>
    <Div id= "divLLAudit" style= "display: ''" class="maxbox">
        <table class= common>
            <TR class= common>
                <TD class= title>审核意见(包括符号最多700汉字)</TD>
            </tr>
            <TR class= common>
                <TD class= input> <textarea style="margin-left:16px" name="AuditIdea" cols="199" rows="4" witdh=25% class="common"></textarea></TD>
            </tr>
         </table>
         <table class= common>
            <TR class= common>
                <TD  class= title>审核结论</TD>
                <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name=AuditConclusion id=AuditConclusion ondblclick="return showCodeList('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onMouseDown="return showCodeList('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onkeyup="return showCodeListKey('llclaimconclusion',[this,AuditConclusionName],[0,1]);" onfocus= "choiseConclusionType();"><input class=codename name=AuditConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD  class= title>特殊备注</TD>
                <TD  class= input><Input class="wid" class=common name=SpecialRemark1></TD>
                <TD  class= title> 案件标识</TD><!--Modify by zhaorx 2006-04-17,2006-08-15-->
                <TD  class= input><Input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ModifyRgtState id=ModifyRgtState CodeData="0|^11|普通案件^12|诉讼案件^14|疑难案件" ondblclick="return showCodeListEx('rgtType', [this,ModifyRgtStateName],[0,1])" onMouseDown= "return showCodeListEx('rgtType', [this,ModifyRgtStateName],[0,1])" onkeyup="return showCodeListKeyEx('rgtType', [this,ModifyRgtStateName],[0,1])"><Input class=codename name=ModifyRgtStateName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>                
            </tr>        
        </table>
        <Div id= "divLLAudit2" style= "display: none">
             <table class= common>
                <TR class= common>
                    <TD  class= title>拒付原因</TD>
                    <TD  class= input><Input  style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=ProtestReason id=ProtestReason ondblclick="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);" onMouseDown="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,ProtestReasonName],[0,1]);"><input class=codename name=ProtestReasonName readonly=true></TD>
                    <TD  class= title>拒付依据</TD>
                    <TD  class= input><Input class="wid" class=common name=ProtestReasonDesc></TD>
                     <TD  class= title></TD>    
                     <TD  class= input></TD>
                </tr>        
            </table>
        </div></Div>
        <table>
            <tr>
                <td><INPUT class=cssButton name="" VALUE="结论保存" TYPE=button onclick="ConclusionSaveClick()" ></td>   
            	<td><INPUT class=cssButton name="" VALUE="审核审批结论查询"  TYPE=button  onclick="LLQueryUWMDetailClick()" ></td>   
                <td><INPUT class=cssButton type=hidden name="printAuditRpt" VALUE="打印审核报告"  TYPE=button onclick="LLPRTClaimAuditiReport()" ></td>                                 
                <td><INPUT class=cssButton type=hidden name="printPatchFee" VALUE="打印补缴保费通知" TYPE=button onclick="LLPRTPatchFeePrint()" ></td>                                              
                <td><INPUT class=cssButton name="printDelayRgt" VALUE="打印补充单证通知书"  TYPE=button onclick="prtDelayRgt()" ></td>
                <td><INPUT class=cssButton name="ReciInfoInp" VALUE="收件人信息"  TYPE=button onclick="showReciInfoInp();"></td>
           </tr>
        </table>
       
    <!--=========================================================================
        修改状态：结束
        修改原因：以上为审核信息，
        修 改 人：续涛
        修改日期：2005.05.13
        =========================================================================
    -->
    <br><br><hr class="line"><br>
    <table>
        <tr>
            <td><INPUT class=cssButton name="ConclusionSave" VALUE="审核确认"  TYPE=button onclick="AuditConfirmClick()"></td>
            <td><INPUT class=cssButton type=hidden name="BatchCreateNote" VALUE="单证批量打印控制"  TYPE=button onclick="showPrtControl()"></td>
            <td><INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()"></td>
        </tr>
    </table>
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
    <!--input type=hidden id="RgtClass" name="RgtClass"--><!--团险个险代码-->
    <input type=hidden id="BudgetFlag" name="BudgetFlag"><!--是否预付-->
    <input type=hidden id="AuditPopedom" name="AuditPopedom"><!--审核权限-->

    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
	<input type=hidden id="RgtObj" name= "RgtObj">

    <Input type=hidden id="RgtClass" name=RgtClass value="1" >
    <input type=hidden id="RgtClassName" name=RgtClassName value="个人">
	
	<!--##########打印流水号，在打印检测时根据  赔案号、单证类型（代码）查询得到######-->
    <input type=hidden id="PrtSeq" name="PrtSeq">

</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
