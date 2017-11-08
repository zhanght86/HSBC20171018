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
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//=======================BGN========================
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");

	  String tClmNo = request.getParameter("claimNo");	//赔案号

	  String tMissionID = request.getParameter("MissionID");  //工作流任务ID
	  String tSubMissionID = request.getParameter("SubMissionID");  //工作流子任务ID
	  String tActivityID = request.getParameter("ActivityID");
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
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <SCRIPT src="LLClaimSimple.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLClaimSimpleInit.jsp"%>
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
          	    <TD  class= input> <Input class= "readonly wid" readonly name=AccNo id=AccNo></TD>
         	    <TD  class= title> 赔案号 </TD>
                <TD  class= input> <Input class="readonly wid" readonly  name=RptNo id=RptNo ></TD>
          	    <TD  class= title> 申请人姓名 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=RptorName id=RptorName ></TD>
            </TR>
            <TR  class= common>
              	<TD  class= title> 申请人电话 </TD>
              	<TD  class= input> <Input class= "readonly wid" readonly  name=RptorPhone id=RptorPhone ></TD>
              	<TD  class= title> 申请人手机号码 </TD>
              	<TD  class= input> <Input class= "readonly wid" readonly name=RptorMoPhone id=RptorMoPhone ></TD>
                <TD  class= title> 申请人详细地址 </TD>
                <TD  class= input> <Input class= "common3 wid" readonly  name=RptorAddress id=RptorAddress ></TD>
            </TR>
           
             <TR  class= common>
            		<TD  class= title> 申请人邮编 </TD>
								<TD  class= input><Input class= "readonly wid" readonly name="AppntZipCode" id="AppntZipCode" ></TD>
    	        	<TD  class= title> 申请人与出险人关系 </TD>
    	        	<TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=Relation id=Relation ondblclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);"><input class=codename name=RelationName id=RelationName readonly=true></TD>
                 <TD  class= title> 出险地点 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=AccidentSite id=AccidentSite ></TD>
            </TR>
            <TR  class= common>
       		    <TD  class= title> 立案日期 </TD>
          	    <TD  class= input> <input class="readonly wid" readonly name="RptDate" id="RptDate" ></TD>
			    <TD  class= title> 管辖机构 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly name=MngCom id=MngCom></TD>
          	      <TD  class= title> 立案受理人 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=Operator id=Operator ></TD>
	        </TR>
            <TR  class= common>
        	    <TD  class= title> 受托人类型 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeType id=AssigneeType ></TD>
        	    <TD  class= title> 受托人代码 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeCode id=AssigneeCode ></TD>
          	     <TD  class= title> 受托人姓名 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeName id=AssigneeName ></TD>
            </TR>
            <TR  class= common>
        	    <TD  class= title> 受托人性别 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeSex id=AssigneeSex ></TD>
        	    <TD  class= title> 受托人电话 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneePhone id=AssigneePhone ></TD>
          	      <TD  class= title> 受托人地址 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeAddr id=AssigneeAddr ></TD>
            </TR>
            <TR  class= common>
        	    <TD  class= title> 受托人邮编 </TD>
          	    <TD  class= input> <Input class= "readonly wid" readonly  name=AssigneeZip id=AssigneeZip ></TD>
          	     <TD class= title> 交接日期</TD>
                <TD class= input>  <input class="readonly wid" readonly  name="AcceptedDate" id="AcceptedDate";"><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD class= title> 客户申请日期</TD>
                <TD class= input>  <input class="readonly wid" readonly  name="ApplyDate" id="ApplyDate";"><font size=1 color='#ff0000'><b>*</b></font></TD>           
            </TR>
            <TR  class= common>
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
                    <TD class= title> 客户年龄 </TD>
                    <TD class= input> <Input class="readonly wid" readonly name=customerAge id=customerAge></TD>
                    <TD class= title> 性别 </TD>
                    <TD class= input> <Input type=hidden name=customerSex ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class="wid" class=readonly readonly name=SexName id=SexName readonly=true></TD>
                </TR>
                <TR class= common>
					<!-- <TD class= title> VIP客户标识</TD>
                    <TD class= input> <Input type=hidden name=IsVip><input class=readonly readonly name=VIPValueName readonly=true></TD> -->
                    <TD class= title> 事故日期</TD>
                    <TD class= input>  <input class= "readonly wid" readonly name="AccidentDate" id="AccidentDate" ></font></TD>
					<TD class= title> 医疗出险日期 </TD>
                    <TD class= input>  <!--<input class="multiDatePicker" dateFormat="short" name="MedicalAccidentDate" readonly><font size=1 color='#ff0000'><b>*</b></font>-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#MedicalAccidentDate'});" verify="有效开始日期|DATE" dateFormat="short" name=MedicalAccidentDate id="MedicalAccidentDate"><span class="icon"><a onClick="laydate({elem: '#MedicalAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </TD>              
                    <!--<TD class= title> 其他出险日期 </TD>
                    <TD class= input>  <input class="multiDatePicker" dateFormat="short" name="OtherAccidentDate" readonly><font size=1 color='#ff0000'><b>*</b></font></TD>  -->
					<TD class= title> 治疗情况</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=cureDesc id=cureDesc ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName id=cureDescName readonly=true></TD>    
               </TR>
                <TR class= common>
                    <TD class= title> 治疗医院</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=hospital id=hospital ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName id=TreatAreaName readonly=true></TD>
					<TD class= title> 出险原因</TD>
                    <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=occurReason id=occurReason ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);"onclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class=codename name=ReasonName id=ReasonName readonly=true></font></TD>            
                    <TD class= title> 意外细节</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=accidentDetail id=accidentDetail ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class=codename name=accidentDetailName id=accidentDetailName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 出险结果1</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=AccResult1 id=AccResult1 ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1]);" onFocus="saveIcdValue();"><input class=codename name=AccResult1Name id=AccResult1Name readonly=true></TD>
                    <TD class= title> 出险结果2</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=AccResult2 id=AccResult2 ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');"><input class=codename name=AccResult2Name id=AccResult2Name readonly=true></TD>
					<TD class= title> 单证齐全标识</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=IsAllReady id=IsAllReady disabled CodeData="0|3^0|否^1|是" ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])" onclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class=codename name=IsAllReadyName id=IsAllReadyName readonly=true></TD>            
                </TR>
                <TR class= common>
                    <TD class= title> 重要信息修改标识</TD>
                    <TD class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=IsModify id=IsModify disabled CodeData="0|3^0|否^1|是" ondblclick="return showCodeListEx('IsModify', [this,IsModifyName],[0,1])"  onclick="return showCodeListEx('IsModify', [this,IsModifyName],[0,1])"onkeyup="return showCodeListKeyEx('IsModify', [this,IsModifyName],[0,1])"><Input class=codename name=IsModifyName id=IsModifyName readonly=true></TD>
                    <TD class= title> 案件标识</TD>
                    <TD class= input> <Input class="wid" class=readonly name=ClaimFlag id=ClaimFlag readonly ></TD>
                </tr>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> 理赔类型：<font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD align=left>
                        <input style="vertical-align:middle" type="checkbox" value="02" name="claimType" ><span style="vertical-align:middle"> 身故</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="03" name="claimType" > <span style="vertical-align:middle">高残</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="04" name="claimType" ><span style="vertical-align:middle">重大疾病</span></input>
                        <input style="vertical-align:middle" type="checkbox" value="01" name="claimType" ><span style="vertical-align:middle"> 残疾、烧伤、骨折、重要器官切除</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="09" name="claimType" > <span style="vertical-align:middle">豁免</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="00" name="claimType" > <span style="vertical-align:middle">医疗</span> </input>
                        <input style="vertical-align:middle" type="checkbox" value="05" name="claimType" ><span style="vertical-align:middle"> 特种疾病</span> </input>
                        <!-- <input type="checkbox" value="06" name="claimType" > 失业失能 </input> -->
                    </td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> 事故描述 </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> <textarea name="AccDesc" id="AccDesc" cols="197" rows="4" witdh=25% class="common" disabled readonly ></textarea></TD>
                </tr>
                <TR class= common>
                    <TD class= title> 备注 </TD>
                </tr>
                <TR class= common>
                    <TD colspan="6" style="padding-left:16px"> <textarea name="Remark" id="Remark" cols="197" rows="4" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
            </table>
        </span>
    </div>
    <table style="display:none">
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
        <a href="javascript:void(0);" name=QueryCont2 class="button" onClick="showInsuredLCPol();">保单查询</a>
        <a href="javascript:void(0);" name=QueryCont3 class="button" onClick="showOldInsuredCase();">既往赔案查询</a>
        <a href="javascript:void(0);" name=BarCodePrint class="button" onClick="colBarCodePrint();">打印条形码</a>
        <a href="javascript:void(0);" name=ColQueryImage class="button" onClick="colQueryImage();">影像查询</a>
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
    	        <TD  class= title>立案结论</TD>
    	        <TD  class= input>
    	        <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=RgtConclusion id=RgtConclusion ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input class=codename name=RgtConclusionName id=RgtConclusionName readonly=true><font size=1 color='#ff0000'><b>*</b></font>
    	        </TD>
                <TD  class= title> 案件标识</TD>
                <TD  class= input> <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno disabled name=rgtType id=rgtType ondblclick="return showCodeListEx('llrgttype', [this,rgtTypeTypeName],[0,1])" onclick="return showCodeListEx('llrgttype', [this,rgtTypeTypeName],[0,1])"onkeyup="return showCodeListKeyEx('llrgttype', [this,rgtTypeTypeName],[0,1])"><Input class=codename name=rgtTypeTypeName id=rgtTypeTypeName readonly=true></TD>
    	        <TD  class= title></TD>
                <TD  class= input></TD>
    	     </tr>
    	</table>
    </div>
    
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
        <Div  id= "divBaseUnit4" style= "display:''" class="maxbox1">
            <table class= common>
                <TR class= common>
                    <TD  class= title>赔付结论</TD>
                    <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=GiveType id=GiveType ondblclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onclick="return showCodeList('llpayconclusion',[this,GiveTypeName],[0,1]);" onkeyup="return showCodeListKey('llpayconclusion',[this,GiveTypeName],[0,1]);" onfocus= "choiseGiveTypeType();"><input class=codename name=GiveTypeName id=GiveTypeName readonly=true></font>
                    <TD  class= title></TD>
                    <TD  class= input><Input class="wid" class=readonly readonly ></TD>
                    <td class=title>币种</td>
    					<td class=input><Input class=codeno name=Currency id=Currency readonly=true><Input class=codename name=CurrencyName id=CurrencyName readonly=true></td>
                    
                </tr>
            </table>
            <Div  id= "divGiveTypeUnit1" style= "display: 'none'">
                <table class= common>
                    <TR class= common>
                    	
                        <TD  class= title>调整金额</TD>
                        <TD  class= input><Input class="wid" class=common name=RealPay id=RealPay ></TD>
                        <TD  class= title>调整原因</TD>
                        <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AdjReason id=AdjReason ondblclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1]);" onclick="return showCodeList('lldutyadjreason',[this,AdjReasonName],[0,1]);" onkeyup="return showCodeListKey('lldutyadjreason',[this,AdjReasonName],[0,1]);"><input class=codename name=AdjReasonName id=AdjReasonName readonly=true></font></TD>
                        <TD  class= title></TD>
                        <TD  class= input></TD>
                    </tr>
                </table>
                <table class= common>
                    <TR class= common>
                        <TD class= title> 调整备注 </TD>
                    </tr>
                    <TR class= common>
                        <TD colspan="6" style="padding-left:16px"> <textarea name="AdjRemark" id="AdjRemark" cols="226" rows="4" witdh=25% class="common"></textarea></TD>
                    </tr>
                 </table>
            </div>
            <Div  id= "divGiveTypeUnit2" style= "display: 'none'">
                <table class= common>
                    <TR class= common>
                        <TD  class= title>拒付原因</TD>
                        <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=GiveTypeDesc id=GiveTypeDesc ondblclick="return showCodeList('llprotestreason',[this,GiveTypeDescName],[0,1]);" onclick="return showCodeList('llprotestreason',[this,GiveTypeDescName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,GiveTypeDescName],[0,1]);"><input class=codename name=GiveTypeDescName id=GiveTypeDescName readonly=true></td>
                        <TD  class= title>拒付依据</TD>
                        <TD  class= input><Input class="wid" class=common name=GiveReason id=GiveReason></td>
                        <TD  class= title></TD>
                        <TD  class= input></TD>                                                
                    </tr>
                </table>
                <table class= common>
                    <TR class= common >
                        <TD class= title> 特殊备注 </TD>
                    </tr>
                    <TR class= common>
                        <TD colspan="6" style="padding-left:16px"> <textarea name="SpecialRemark" id="SpecialRemark" cols="226" rows="4" witdh=25% class="common"></textarea></TD>
                    </tr>
                </table>
            </div>
            <table style="display:none">
                 <tr>
                     <td><INPUT class=cssButton name="addUpdate" VALUE=" 添加修改 "  TYPE=button onclick="AddUpdate()" ></td>
                     <td><INPUT class=cssButton name="saveUpdate" VALUE=" 保存修改 "  TYPE=button onclick="SaveUpdate();" ></td>
                 </tr>
            </table>
        </div>
    </div>
    <a href="javascript:void(0);" name="addUpdate" class="button" onClick="AddUpdate();">添加修改</a>
    <a href="javascript:void(0);" name="saveUpdate" class="button" onClick="SaveUpdate();">保存修改</a><br><br>
    <table style="display:none">
        <tr>
			<td><INPUT class=cssButton name="MedicalFeeInp" VALUE=" 医疗单证调整"  TYPE=button onclick="showMedicalAdjClick();"></td>
            <td><INPUT class=cssButton name="dutySet" VALUE=" 匹配并理算 "  TYPE=button onclick="showMatchDutyPay();"></td>
            <!--td><INPUT class=cssButton name="CertifyFeeInp" VALUE="单证费用调整"  TYPE=button onclick="showLLMedicalFeeAdj();"></td-->
            <!--td><INPUT class=cssButton name="" VALUE="费用调整查看"  TYPE=button  onclick="showMedicalAdjClick()" ></td-->
            <td><INPUT class=cssButton name="AccidentDesc" VALUE=" 备注信息 "  TYPE=button onclick="showClaimDesc()"></td>
            <td><INPUT class=cssButton name="" VALUE="受益人分配"  TYPE=button onclick="showBnf()"></td>
			<td><INPUT class=cssButton type=hidden name="medicalFeeCal" VALUE="费用计算查看"  TYPE=button onclick="showLLMedicalFeeCal();"></td>    
        </tr>
    </table><br>
    <a href="javascript:void(0);" name="MedicalFeeInp" class="button" onClick="showMedicalAdjClick();">医疗单证调整</a>
    <a href="javascript:void(0);" name="dutySet" class="button" onClick="showMatchDutyPay();">匹配并理算</a>
    <a href="javascript:void(0);" name="AccidentDesc" class="button" onClick="showClaimDesc();">备注信息</a>
    <a href="javascript:void(0);" name="" class="button" onClick="showBnf();">受益人分配</a>
    <a href="javascript:void(0);" type=hidden name="medicalFeeCal" class="button" onClick="showLLMedicalFeeCal();">费用计算查看</a>
    
    <!--简易案件信息-->
    <table>
         <tr>
         	 <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSimpleClaim);"></td>
             <td class= titleImg>简易案件信息</td>
         </tr>
    </table>
    <Div  id= "divSimpleClaim" style= "display:''" class="maxbox1">
        <table class= common>
            <TR class= common>
                <TD  class= title>简易案件结论</TD>
                <TD  class= input><Input class=codeno readonly name=SimpleConclusion ondblClick="showCodeList('llsimcaseconclusion',[this,SimpleConclusionName],[0,1]);" onkeyup="showCodeListKeyEx('llexamconclusion',[this,SimpleConclusionName],[0,1]);"><input class=codename name=SimpleConclusionName readonly><font size=1 color='#ff0000'><b>*</b></font></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
            </tr>
        </table>
    </div>
    
    <!--<INPUT class=cssButton name="simpleClaim" VALUE="简易案件确认"  TYPE=button onclick="confirmClick()">
    <INPUT class=cssButton name="goBack" VALUE="返  回"  TYPE=button onclick="goToBack()">--><br>
    <a href="javascript:void(0);" name="simpleClaim" class="button" onClick="confirmClick();">简易案件确认</a>
    <a href="javascript:void(0);" name="goBack" class="button" onClick="goToBack();">返    回</a>

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
    <input type=hidden id="ActivityID" name="ActivityID">
    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<input type=hidden id="MissionID" 	 name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
