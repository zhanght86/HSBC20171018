<%
//**************************************************************************************************
//Name：LLClaimPrepayInput.jsp
//Function：“预付管理”主界面
//Author：yuejw
//Date: 2005-7-5 16:00
//Desc:
//**************************************************************************************************
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<head>
<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
	String tClmNo=request.getParameter("ClmNo"); //赔案号
	loggerDebug("LLClaimPrepayInput","tClmNo:"+tClmNo);
	
	String tCustomerNo=request.getParameter("CustomerNo"); //客户号
	String tCustomerName=request.getParameter("CustomerName"); //客户姓名     
	String tCustomerSex=request.getParameter("CustomerSex"); //性别      
	
	String tActivityID=request.getParameter("ActivityID"); //活动ID
	String tMissionID =request.getParameter("MissionID");  //工作流任务ID
	String tSubMissionID =request.getParameter("SubMissionID");  //工作流子任务ID	
	String mAuditer =request.getParameter("Auditer");  //案件审核人
%>
    <meta http-equiv="Content-Type" content="text/html; charset=GBK">
    <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="LLClaimPrepay.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <%@include file="LLClaimPrepayInit.jsp"%>
    <title> 预付管理</title>
</head>
<body  onload="initForm();" >
<form  method=post name=fm id=fm target="fraSubmit">
        <!--立案申请信息-->
    <table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLReport1);"></TD>
            <TD class= titleImg>立案申请信息</TD>
        </TR>
    </table>
    <Div  id= "divLLReport1" class=maxbox style= "display: ''">
        <table  class= common>
       	    <TR  class= common>
			          <TD  class= title> 事件号 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=AccNo id=AccNo></TD>
         	      <TD  class= title> 立案号 </TD>
                <TD  class= input> <Input class="readonly wid" readonly  name=RptNo id=RptNo ></TD>
          	    <TD  class= title> 申请人姓名 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=RptorName id=RptorName ></TD>
            </TR>
            <TR  class= common>
              	<TD  class= title> 申请人电话 </TD>
              	<TD  class= input> <Input class="readonly wid" readonly  name=RptorPhone id=RptorPhone ></TD>
            	  <TD  class= title> 申请人地址 </TD>
                <TD  class= input> <Input class="readonly wid" readonly  name=RptorAddress id=RptorAddress ></TD>
    	          <TD  class= title> 申请人与出险人关系 </TD>
    	          <TD  class= input> <Input class=codeno disabled name=Relation id=Relation style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llrgrelation',[this,RelationName],[0,1]);" onkeyup="return showCodeListKey('llrgrelation',[this,RelationName],[0,1]);">
				  <input class=codename name=RelationName id=RelationName readonly=true></TD>
            </TR>
            <TR  class= common>
       	        <TD  class= title> 出险地点 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=AccidentSite id=AccidentSite ></TD>
       		      <TD  class= title> 立案日期 </TD>
          	    <TD  class= input> <input class="readonly wid" readonly name="RptDate" id=RptDate ></TD>
			          <TD  class= title> 管辖机构 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=MngCom id=MngCom></TD>
	          </TR>
            <TR  class= common>
        	      <TD  class= title> 立案受理人 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=Operator id=Operator ></TD>
        	      <TD  class= title> 受托人类型 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=AssigneeType id=AssigneeType ></TD>
        	      <TD  class= title> 受托人代码 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=AssigneeCode id=AssigneeCode ></TD>
            </TR>
            <TR  class= common>
        	      <TD  class= title> 受托人姓名 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=AssigneeName id=AssigneeName ></TD>
        	      <TD  class= title> 受托人性别 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=AssigneeSex id=AssigneeSex ></TD>
        	      <TD  class= title> 受托人电话 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=AssigneePhone id=AssigneePhone ></TD>
            </TR>
            <TR  class= common>
        	      <TD  class= title> 受托人地址 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=AssigneeAddr id=AssigneeAddr ></TD>
        	      <TD  class= title> 受托人邮编 </TD>
          	    <TD  class= input> <Input class="readonly wid" readonly  name=AssigneeZip id=AssigneeZip ></TD>
        	<!--  <TD  class= title> 申请类型</TD>
          	    <TD  class= input> <Input class= readonly readonly name=RgtClassName ></TD> -->      
                  <TD class= title> 受理日期</TD>
                <TD class= input>  <input class="readonly wid" readonly  name="AcceptedDate" id=AcceptedDate><font size=1 color='#ff0000'><b>*</b></font></TD>
            </TR>
            <TR  class= common>
            	  <TD  class= title>理赔金领取方式</TD>
          	    <TD  class= input> <Input class="readonly wid" readonly name=GetMode id=GetMode ></TD>
          	    
          	    <TD  class= title>
          	       <span id="spanRgtObjNo1" style= "display: none">       
          	           团体赔案号
          	       </span>
          	    </TD>
          	    
          	    <TD  class= input> 
          	       <span id="spanRgtObjNo2" style= "display: none"> 	
          	           <Input class="readonly wid" readonly name=RgtObjNo id=RgtObjNo >
          	       </span>
          	    </TD>   
            </TR>
        </table>
  	</Div>
	<hr class=line>
    <!--客户信息-->
    <table class= common>
        <tr class= common>
            <td style="text-align: left" colSpan=1><span id="spanSubReportGrid" ></span></td>
        </tr>
    </table>
    <table>
        <tr>
            <td class=common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLSubReport);"></td>
            <td class=titleImg> 客户信息 </td>
        </tr>
    </table>
    <Div id= "divLLSubReport" class=maxbox style= "display: ''">
        <table>
            <tr>
            <!--
                <td><input class=cssButton name=QueryPerson value="客户查询" type=button onclick="showInsPerQuery()" ></td>
                <td><input class=cssButton name=QueryReport value=" 事件查询 " type=button onclick="queryLLAccident()"></td>
                <td>
                    <span id="operateButton21" style= "display: none"><input class=cssButton name=addbutton  VALUE="保  存"  TYPE=button onclick="saveClick()" ></span>
                    <span id="operateButton22" style= "display: none"><input class=cssButton name=updatebutton  VALUE="修  改"  TYPE=button onclick="updateClick()" ></span>
                </td>
             -->
                <td><input class=cssButton name=QueryCont2 VALUE=" 保单查询 "  TYPE=button onclick="showInsuredLCPol()"></td>
                <td><input class=cssButton name=QueryCont3 VALUE="既往赔案查询" TYPE=button onclick="showOldInsuredCase()"></td>
				<!-- <td><input class=cssButton name=QueryCont5 VALUE="重要信息修改" TYPE=button onclick="editImpInfo()"></td> -->
                <td><input class=cssButton name=BarCodePrint VALUE="打印条形码"  TYPE=button onclick="colBarCodePrint();"></td>
                <td><input class=cssButton name=ColQueryImage VALUE="影像查询"  TYPE=button onclick="colQueryImage();"></td>                
            </tr>
        </table>
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
                    <TD class= input> <Input type=hidden name=customerSex id=customerSex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);">
					<input class="readonly wid" readonly name=SexName id=SexName readonly=true></TD>
                </TR>
                <TR class= common>
					<!-- <TD class= title> VIP客户标识</TD>
                    <TD class= input> <Input type=hidden name=IsVip><input class=readonly readonly name=VIPValueName readonly=true></TD> -->
                    <TD class= title> 事故日期</TD>
                    <TD class= input>  <input class="multiDatePicker common" dateFormat="short" name="AccidentDate" onblur="CheckDate(fm.AccidentDate);" onClick="laydate({elem: '#AccidentDate'});" id="AccidentDate"><span class="icon"><a onClick="laydate({elem: '#AccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></TD>
					<TD class= title> 医疗出险日期 </TD>
                    <TD class= input>  <input class="multiDatePicker common" dateFormat="short" name="MedicalAccidentDate" onClick="laydate({elem: '#MedicalAccidentDate'});" id="MedicalAccidentDate"><span class="icon"><a onClick="laydate({elem: '#MedicalAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></TD>              
                    <TD class= title> 其他出险日期 </TD>
                    <TD class= input>  <input class="multiDatePicker common" dateFormat="short" name="OtherAccidentDate" onClick="laydate({elem: '#OtherAccidentDate'});" id="OtherAccidentDate"><span class="icon"><a onClick="laydate({elem: '#OtherAccidentDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></TD>
               
                </TR>
                <TR class= common>
                      <TD class= title> 治疗医院</TD>
                      <TD class= input> <Input class=codeno disabled name=hospital id=hospital style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('commendhospital',[this,TreatAreaName],[0,1],null,null,null,null,'400');"><input class=codename name=TreatAreaName id=TreatAreaName readonly=true></TD>
                      <TD class= title> 出险原因</TD>
                      <TD class= input><Input class=codeno disabled name=occurReason id=occurReason style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('lloccurreason',[this,ReasonName],[0,1]);" onkeyup="return showCodeListKey('lloccurreason',[this,ReasonName],[0,1]);"><input class=codename name=ReasonName id=ReasonName readonly=true></font></TD>
                      <TD class= title> 意外细节</TD>
                      <TD class= input> <Input class=codeno disabled name=accidentDetail id=accidentDetail style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llaccidentdetail',[this,accidentDetailName],[0,1]);" onkeyup="return showCodeListKey('llaccidentdetail',[this,accidentDetailName],[0,1]);"><input class=codename name=accidentDetailName id=accidentDetailName readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 治疗情况</TD>
                    <TD class= input> <Input class=codeno disabled name=cureDesc id=cureDesc style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llCureDesc',[this,cureDescName],[0,1]);" onkeyup="return showCodeListKey('llCureDesc',[this,cureDescName],[0,1]);"><input class=codename name=cureDescName id=cureDescName readonly=true></TD>
                    <TD class= title> 出险结果1</TD>
                    <TD class= input> <Input class=codeno disabled name=AccResult1 id=AccResult1 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('lldiseases1',[this,AccResult1Name],[0,1]);" onkeyup="return showCodeListKey('lldiseases1',[this,AccResult1Name],[0,1]);" onFocus="saveIcdValue();"><input class=codename name=AccResult1Name id=AccResult1Name readonly=true></TD>
                    <TD class= title> 出险结果2</TD>
                    <TD class= input> <Input class=codeno disabled name=AccResult2 id=AccResult2 style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'upicdcode','1');" onkeyup="return showCodeListKey('lldiseases2',[this,AccResult2Name],[0,1],null,fm.ICDCode.value,'ICDCode','1');"><input class=codename name=AccResult2Name id=AccResult2Name readonly=true></TD>
                </TR>
                <TR class= common>
                    <TD class= title> 单证齐全标识</TD>
                    <TD class= input> <Input class=codeno name=IsAllReady id=IsAllReady disabled CodeData="0|^0|否^1|是" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeListEx('IsAllReady', [this,IsAllReadyName],[0,1])"onkeyup="return showCodeListKeyEx('IsAllReady', [this,IsAllReadyName],[0,1])"><Input class=codename name=IsAllReadyName id=IsAllReadyName readonly=true></TD>
                    <TD class= title> 重要信息修改标识</TD>
                    <TD class= input> <Input class=codeno name=IsModify id=IsModify disabled CodeData="0|3^0|否^1|是" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeListEx('IsModify', [this,IsModifyName],[0,1])"onkeyup="return showCodeListKeyEx('IsModify', [this,IsModifyName],[0,1])"><Input class=codename name=IsModifyName id=IsModifyName readonly=true></TD>
                    <TD class= title> 案件标识</TD>
                    <TD class= input> <Input class="readonly wid" name=ClaimFlag id=ClaimFlag readonly ></TD>
                </tr>
            </table>

            <table class= common>
                <TR class= common>
                    <TD class= title> 理赔类型：<font size=1 color='#ff0000'><b>*</b></font></TD>
                    <TD align=left>
                        <input type="checkbox" value="02" name="claimType" > 身故 </input>
                        <input type="checkbox" value="03" name="claimType" > 高残 </input>
                        <input type="checkbox" value="04" name="claimType" > 重大疾病 </input>
                        <input type="checkbox" value="01" name="claimType" > 残疾、烧伤、骨折、重要器官切除 </input>
                        <input type="checkbox" value="09" name="claimType" > 豁免 </input>
                        <input type="checkbox" value="00" name="claimType" > 医疗 </input>
                        <input type="checkbox" value="05" name="claimType" > 特种疾病 </input>
                        <!-- <input type="checkbox" value="06" name="claimType" > 失业失能 </input> -->
                    </td>
                </TR>
            </table>
            <table class= common>
                <TR  class= common>
                    <TD  class= title> 事故描述 </TD>
                </tr>
                <TR class= common>
                    <TD class= input> <textarea name="AccDesc" id=AccDesc cols="100" rows="2" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
                <TR class= common>
                    <TD class= title> 备注 </TD>
                </tr>
                <TR class= common>
                    <TD class= input> <textarea name="Remark" id=Remark cols="100" rows="2" witdh=25% class="common" readonly ></textarea></TD>
                </tr>
            </table>
        </span>
    </div>
    <hr class=line>
    <!--立案结论信息-->
    <table>
         <tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBaseUnit6);"></td>
            <td class= titleImg>立案结论信息</td>
         </tr>
    </table>
    <Div  id= "divBaseUnit6" class=maxbox1 style= "display:''">
    	<table class= common>
    	    <TR class= common>
    	        <TD  class= title> 立案结论</TD>
    	        <TD  class= input> <Input class=codeno disabled name=RgtConclusion id=RgtConclusion style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llrgtconclusion',[this,RgtConclusionName],[0,1]);" onkeyup="return showCodeListKey('llrgtconclusion',[this,RgtConclusionName],[0,1]);"><input class=codename name=RgtConclusionName id=RgtConclusionName readonly=true></TD>
                <!-- Modify by zhaorx 2006-04-17
                <TD  class= title> 案件标识</TD>
                <TD  class= input> <Input class=codeno disabled name=rgtType CodeData="0|3^11|普通案件^12|诉讼案件^14|疑难案件" ondblclick="return showCodeListEx('rgtType', [this,rgtTypeName],[0,1])"onkeyup="return showCodeListKeyEx('rgtType', [this,rgtTypeName],[0,1])"><Input class=codename name=rgtTypeName readonly=true></TD>-->
                <TD  class= title></TD>
                <TD  class= input></TD>                
                <TD  class= title></TD>
                <TD  class= input></TD>
    	     </tr>
    	</table>
    </div>
    <hr class=line>
    <table>
        <tr>
            <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLClaimDetailGrid);"></td>
            <td class= titleImg> 给付保项信息列表 </td>
        </tr>
    </table>
    <Div id= "divLLClaimDetailGrid" style= "display: ''" align = center>
        <table  class= common >
            <tr  class=common>
                <td style="text-align: left" colSpan=1 ><span id="spanLLClaimDetailGrid" ></span></td>
            </tr>
        </table>
        <table>
            <tr>
                <td><INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table>   
   
    </div>    

	<hr class=line>
	    <table>
        <tr>
            <td class= common> <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLPrepayDetailInfo);"></td>
            <td class= titleImg> 保项的预付金额的处理 </td>
        </tr>
    </table>
   <Div id= "divLLPrepayDetailInfo" style= "display: ''" align = center>

        <Table  class= common>
            <TR  class= common>
                <TD style="text-align: left" colSpan=1><span id="spanLLPrepayDetailGrid" ></span> </TD>
            </TR>
        </Table>
        <!--<Table>
            <tr>
                <td><INPUT class=cssButton90 VALUE="首  页" TYPE=button onclick=" turnPage2.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick=" turnPage2.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick=" turnPage2.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="尾  页" TYPE=button onclick=" turnPage2.lastPage();"></td>
            </tr>
        </Table>--> 
    </Div>    
	<!-- 
    <table >
        <TR class= common>
            <TD> <Input TYPE=button class=cssButton name="LLPrepayAdd" disabled VALUE="增  加" onclick="LLPrepayDetailAdd();"></TD>
            <TD> <Input TYPE=button class=cssButton name="LLPrepayCancel" disabled VALUE="取  消" onclick="LLPrepayDetailCancel();"></TD>
        </TR>
    </Table> -->  
   <Div id= "divLLPrepayDetail" style= "display: none" align = center>
		<table class= common>
		    <TR class= common>
		    	<td class=title>币种</td>
    			<td class=input><Input class="ommon wid" name=Currency readonly=true></td>
	            <TD class= title> 输入预付金额 </TD>
	            <TD class= input><Input class="ommon wid" name="PrepaySum" ></TD>		        
	            <TD class= title>支付方式</TD>
	            <td class= input><Input class=codeno readonly=true name="CasePayMode" id=CasePayMode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llpaymode',[this,CasePayModeName],[0,1]);" onkeyup="return showCodeListKey('llpaymode',[this,CasePayModeName],[0,1]);"><input class=codename name="CasePayModeName" id=CasePayModeName readonly=true></TD>
		    </TR>
		     <TR class= common>
            	<TD> <Input TYPE=button class=cssButton VALUE="保  存" onclick="LLPrepayDetailSave();"></TD>
            </TR>
		</table>
   </Div>      
	<hr class=line>
<!--审核管理信息-->
    <table>
      <TR>
       <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLAudit);"></TD>
       <TD class= titleImg>审核管理</TD>
     </TR>
    </table>
    <Div id= "divLLAudit" class=maxbox style= "display: ''">
        <table class= common>
            <TR class= common>
                <TD class= title>审核意见</TD>
            </tr>
            <TR class= common>
                <TD class= input> <textarea name="AuditIdea" id=AuditIdea cols="100" rows="6" witdh=25% class="common" readonly ></textarea></TD>
            </tr>
         </table>
         <table class= common>
            <TR class= common>
                <TD  class= title>审核结论</TD>
                <TD  class= input><Input class=codeno disabled name=AuditConclusion id=AuditConclusion style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llclaimpreconclusion',[this,AuditConclusionName],[0,1]);" onkeyup="return showCodeListKey('llclaimpreconclusion',[this,AuditConclusionName],[0,1]);" ><input class=codename name=AuditConclusionName id=AuditConclusionName readonly=true></TD>
                <!-- <TD  class= title>特殊备注</TD>
                <TD  class= input><Input class=common disabled name=SpecialRemark1></TD> -->
                <TD  class= title></TD>
                <TD  class= input></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
            </tr>
        </table>
        <!-- 
        <Div id= "divLLAudit2" style= "display: none">
             <table class= common>
                <TR class= common>
                    <TD  class= title>拒付原因</TD>
                    <TD  class= input><Input class=codeno disabled name=ProtestReason ondblclick="return showCodeList('llprotestreason',[this,ProtestReasonName],[0,1]);" onkeyup="return showCodeListKey('llprotestreason',[this,ProtestReasonName],[0,1]);"><input class=codename name=ProtestReasonName readonly=true></TD>
                    <TD  class= title>拒付依据</TD>
                    <TD  class= input><Input class=common disabled name=ProtestReasonDesc></TD>
                    <TD  class= title></TD>
                    <TD  class= input></TD>
                </tr>
            </table>
        </div> -->
    </Div>
    <hr class=line>
    <!--=========================================================================
        修改状态：结束
        修改原因：以上为审核信息，
        修 改 人：续涛
        修改日期：2005.05.13
        =========================================================================
    -->
      <!--审批结论信息-->
    <table>
        <tr>
            <td class=common> <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" onClick= "showPage(this,divShowConfirmDetail);"></td>
            <td class= titleImg> 审批管理</td>
        </tr>
    </table>
    <Div  id= "divShowConfirmDetail" class=maxbox1 style="display: '';">
        <table class= common>
            <TR class= common>
                <TD  class= title> 审批意见(包括符号最多700汉字)</TD>
            </tr>
            <TR class= common>
                <TD  class= input> <textarea name="RemarkSP" id=RemarkSP cols="100%" rows="6" witdh=25% class="common"></textarea></TD>
            </tr>
        </table>
        <table class= common>
       	    <TR class= common>
	            <TD  class= title>审批结论</TD>
                <TD  class= input><Input class=codeno readonly name="DecisionSP" id=DecisionSP  style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblClick="showCodeList('llpreExamconclusion',[this,DecisionSPName],[0,1]);" onkeyup="showCodeListKeyEx('llpreExamconclusion',[this,DecisionSPName],[0,1]);" ><input class=codename name=DecisionSPName id=DecisionSPName readonly><font size=1 color='#ff0000'><b>*</b></font></TD>
                <!-- <TD  class= title> 案件标识</TD>
                <TD  class= input><Input class=codeno name=ModifyRgtState CodeData="0|^11|普通案件^12|诉讼案件^14|疑难案件" ondblclick="return showCodeListEx('rgtType', [this,ModifyRgtStateName],[0,1])"onkeyup="return showCodeListKeyEx('rgtType', [this,ModifyRgtStateName],[0,1])"><Input class=codename name=ModifyRgtStateName readonly=true></TD>  -->
                <TD  class= title></TD>
                <TD  class= input></TD>
                <TD  class= title></TD>
                <TD  class= input></TD>
			</TR>
        </table>
		<!-- 
        <Div id= "divLLConfirm2" style= "display: none">
             <table class= common>
                <TR class= common>
                    <TD  class= title>不通过原因</TD>
                    <TD  class= input><Input class=codeno name=ExamNoPassReason ondblclick="return showCodeList('llexamnopassreason',[this,ExamNoPassReasonName],[0,1]);" onkeyup="return showCodeListKey('llexamnopassreason',[this,ExamNoPassReasonName],[0,1]);"><input class=codename name=ExamNoPassReasonName readonly=true></TD>
                    <TD  class= title>不通过依据</TD>
                    <TD  class= input><Input class=common name=ExamNoPassDesc></TD>
                    <TD  class= title></TD>
                    <TD  class= input></TD>
                </tr>        
            </table>
        </div> -->
    </div>
    <hr class=line>
	<Table>
        <TR>
            <td><INPUT class=cssButton name="" VALUE="审核审批结论查询"  TYPE=button  onclick="LLQueryUWMDetailClick()" ></td>   
            <TD> <Input TYPE=button class=cssButton name="PrepayCofirm" VALUE="审批确认" onclick="LLClaimPrepayCofirm();"></TD>
            <!-- <TD> <Input TYPE=button class=cssButton VALUE="返   回" onclick="Return();"></TD>  -->        
        </TR>
    </Table>
	
    <!--//保存数据用隐藏表单区-->
    <Input type=hidden name="Operator" >    <!--//当前操作人-->
    <Input type=hidden name="ComCode" >     <!--//当前机构-->
    <input type=hidden id="fmtransact" name="fmtransact"><!--操作代码‘insert,delete’等-->
    <Input type=hidden name="PrepayNo" >     <!--//预付批次号,每次进入预付页面初始化时生成，在处理过程中一直不变-->
    <!--//保存响应“ 给付保项信息列表”点击单选钮的函数时传入的数据-->
	<input type=hidden id="ClmNo"   name= "ClmNo">
	<input type=hidden id="CaseNo" name= "CaseNo">    
	<input type=hidden id="PolNo"   name= "PolNo">
	<input type=hidden id="GetDutyKind" name= "GetDutyKind">
	<input type=hidden id="GetDutyCode" name= "GetDutyCode">
	<input type=hidden id="CaseRelaNo" name= "CaseRelaNo">
	<input type=hidden id="GrpContNo"   name= "GrpContNo">
	<input type=hidden id="GrpPolNo" name= "GrpPolNo">    
	<input type=hidden id="ContNo"   name= "ContNo">
	<input type=hidden id="KindCode" name= "KindCode">
	<input type=hidden id="RiskCode" name= "RiskCode">
	<input type=hidden id="RiskVer" name= "RiskVer">    
	<input type=hidden id="DutyCode" name= "DutyCode">    
    <Input type=hidden id="RgtClass" name=RgtClass value="1" >
    <input type=hidden id="RgtClassName" name=RgtClassName value="个人">
	
    <!--//保存上页传入的数据,用于“预付确认”时查询节点数据,为生成新节点准备数据-->  
    
	<input type=hidden id="tRptorState"   name= "tRptorState">
	<input type=hidden id="CustomerNo"   name= "CustomerNo">
	<input type=hidden id="CustomerName"   name= "CustomerName">
	<input type=hidden id="CustomerSex"   name= "CustomerSex">
	<input type=hidden id="tAccDate"   name= "tAccDate">
	<input type=hidden id="tRgtClass"   name= "tRgtClass">
	<input type=hidden id="tRgtObj"   name= "tRgtObj">
	<input type=hidden id="tRgtObjNo"   name= "tRgtObjNo">
	<input type=hidden id="AuditPopedom"   name= "AuditPopedom">
	<input type=hidden id="tPrepayFlag"   name= "tPrepayFlag">
	<input type=hidden id="tAuditer"   name= "tAuditer">	
	<input type=hidden id="tComeWhere"   name= "tComeWhere">
	<input type=hidden id="tMngCom"   name= "tMngCom">
	<Input type=hidden id="tComFlag" name= "tComFlag">     <!--//权限跨级标志-->
	<input type=hidden id="MissionID"   name= "MissionID">
	<input type=hidden id="SubMissionID" name= "SubMissionID">
	<input type=hidden id="ActivityID" name= "ActivityID">
</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
	<br /><br /><br /><br />
</body>
</html>	
