<%
/***************************************************************
 * <p>ProName：LLClaimPreinvestInput.jsp</p>
 * <p>Title：发起调查</p>
 * <p>Description：发起调查</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-04-17
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");	
	String tManageCom = request.getParameter("ManageCom");
	String tOperator = tGI.Operator;
	String mCurrentDate = PubFun.getCurrentDate();
  String tGrpRgtNo = request.getParameter("GrpRgtNo");  //团体赔案号
  String tRgtNo = request.getParameter("RgtNo"); 
  String tCustomerNo = request.getParameter("custNo"); //出险人编码
  String tSurveyType = request.getParameter("surveyType"); //调查类型
  String tInitPhase = request.getParameter("initPhase"); //调查流程状态
  String tAppntNo =request.getParameter("AppntNo"); //投保人编码
  String tMode =request.getParameter("Mode"); //投保人编码
	
%>
<script>
	var tManageCom = "<%=tManageCom%>"; //记录登陆机构
	var tOperator = "<%=tOperator%>";  //记录操作人
	var tCurrentDate = "<%=mCurrentDate%>"; 	//系统时间
	var tGrpRgtNo ="<%=tGrpRgtNo%>";
	var tRgtNo ="<%=tRgtNo%>";
	var tCustomerNo ="<%=tCustomerNo%>";
	var tSurveyType ="<%=tSurveyType%>";
	var tInitPhase ="<%=tInitPhase%>";
	var tAppntNo ="<%=tAppntNo%>";
	var tMode = "<%=tMode%>";
</script>
<html>
<head>
	<title>发起调查</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/Calendar/Calendar.js"></script>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
		<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="../common/javascript/jquery-1.7.2.js"></script>
	<script src="./LLClaimPreinvestInput.js"></script>
	<%@include file="./LLClaimPreinvestInit.jsp"%>
</head>

<body onload="initForm(); initElementtype();">
<form name=fm method=post action="./LLClaimPreinvestSave.jsp" target=fraSubmit>
	<table>
		<tr>
			<td class=commontop>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divInvestList);">
			</td>
			<td class=titleImgtop>已发起调查信息列表</td>
		</tr>
	</table>
	<div id="divInvestList" style="display: ''">
		<input class=cssButton name=queryInqInfo value="调查明细查询" type=button onclick="showInvest();">
		<input class=cssButton name=inqTuenBack value="调查收回" type=button onclick="returnInvest();">
		<font color="#FF0000">***针对未分配的调查，可以进行【调查收回】</font>					
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanInvestListGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton90 type=button value="首  页" onclick="turnPage1.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage1.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage1.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage1.lastPage();">
		</center>
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this,divOnInvest);">
			</td>
			<td class=titleImg>待发起调查信息列表</td>
		</tr>
	</table>
	<div id="divOnInvest" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanOnInvestGrid"></span>
				</td>
			</tr>
		</table>
		<center>
			<input class=cssButton type=button value="首  页" onclick="turnPage2.firstPage();">
			<input class=cssButton type=button value="上一页" onclick="turnPage2.previousPage();">
			<input class=cssButton type=button value="下一页" onclick="turnPage2.nextPage();">
			<input class=cssButton type=button value="尾  页" onclick="turnPage2.lastPage();">
		</center>
	</div>
  <!--调查信息-->
		<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInvesstInfo);">
			</td>
			<td class=titleImg>发起调查信息</td>
		</tr>
		</table>
  
  <div id="divInvesstInfo" class=maxbox style="display: ''">
	  <input class=cssButton name=saveButton value="新增调查" type=button onclick="saveSurvey();">
		<input class=cssButton name=recoverButton value="修改调查" type=button onclick="modifySurvey();">
		<input class=cssButton name=deleteButton value="删除调查" type=button onclick="deleteSurvey();">
		<input class=cssButton name=doCloseButton value="关闭调查" type=button onclick="closeSurvey();">
		<input class=cssButton name=doButton value="发起调查" type=button onclick="startSurvey();">
		<input class=cssButton name=goBackButton value="重  置" type=button onclick="returnBack();">
		<input class=cssButton name=turnback value="关  闭" type=button onclick="goBack();">
		<input class=cssButton name=attachAdd value="新增附件" type=button onclick="addAttach();">
	  <table class=common>
	  <tr class=common>
			<td class=title>出险人姓名</td>
			<td class=input><Input class="wid readonly" readonly name=CustomerName id=CustomerName></td>
			<td class=title>调查发起日期</td>
		  <td class=input><input class="coolDatePicker" dateFormat="short"  verify="调查申请日期|notnull&DATE verifyorder="1""   name=ApplyDate elementtype=nacessary onClick="laydate({elem: '#ApplyDate'});" id="ApplyDate"><span class="icon"><a onClick="laydate({elem: '#ApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td> 
			 
<!-- 			<td class=Input ><Input class=coolDatePicker dateFormat="short" name=ApplyDate  verify="调查申请日期|NOTNULL" elementtype=nacessary  maxlength="10" onClick="laydate({elem: '#ApplyDate'});" id="ApplyDate"><span class="icon"><a onClick="laydate({elem: '#ApplyDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>		
 -->			<td class=title>调查类型</td>
			<td class=input><Input class=codeno  name=SurveyType  readonly ><input class=codename name="SurveyTypeName" readonly></TD>      
		</tr>    
	  <tr class=common>
	    <td class=title> 调查原因 </td>
	    <td class=input><Input class=codeno  name=InqReason verify="调查原因|NOTNULL" style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return showCodeList('llinqreason',[this,InqReasonName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('llinqreason',[this,InqReasonName],[0,1],null,null,null,1);"><input class=codename name="InqReasonName" readonly=true elementtype=nacessary></TD>       
	    <td class=title> 调查关闭原因 </td>
	    <td class=input><Input class=codeno  name=InqCloseReason ondblclick="return showCodeList('inqcloseresason',[this,InqCloseReasonName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('inqcloseresason',[this,InqCloseReasonName],[0,1],null,null,null,1);"><input class=codename name="InqCloseReasonName" readonly=true ></TD>              
	    <td class=title></td>
	    <td class=input></TD>
	  </tr>
	  <tr class=common>
	    <td class=title> 调查目的 <font color=red>*</font></td>
	    <td class=input colspan="5"><textarea name=InqPurpose id=InqPurpose cols=60 rows=5 verify="调查目的|NOTNULL&LEN<=1000" maxlength="1000" class=common></textarea></TD>       
	   </tr>
	  <tr class=common>
	    <td class=title> 调查计划 <font color=red>*</font> </td>
	    <td class=input colspan="5"><textarea name=InqPlan cols=60 rows=5 verify="调查计划|NOTNULL" maxlength="1000" class=common></textarea></TD>       
	  </tr>
	   <tr class=common>
		    <td class=title> 备注信息 </td>
		    <td class=input colspan="5"><textarea name=Remark cols=60 rows=3 maxlength="1000" class=common></textarea></TD>       
		  </tr> 
	  </table>	  	
  </div> 
	<Input type=hidden  name=Operate> 	 	 <!--操作类型-->
	<Input type=hidden  name=CustomerNo>
	<Input type=hidden  name=InitPhase>
	<Input type=hidden  name=ManageCom>
	<Input type=hidden  name=LocFlag><!--本地标识-->
	<Input type=hidden  name=InqNo><!--调查序号-->
  <Input type=hidden  name=BatNO><!--批次号-->
  <Input type=hidden	name=RgtNo>
  <Input type=hidden	name=GrpRgtNo>
  <Input type=hidden	name=InqFlowState>
  <Input type=hidden	name=InitiationType>
  <input type=hidden  name=AppntNo>
</form>
  <!-- 调查附件form-->
	<form action="LLSurveyAttachmentSave.jsp" style="margin-bottom:0px;margin-top: 0px" method=post name=fm2 id=fm2 target="fraSubmit" ENCtype="multipart/form-data">
	
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSurveyAttachmentGrid);">
				</td>
				<td class=titleImg>调查附件信息列表</td>
			</tr>
		</table>
		<div id="divSurveyAttachmentGrid" style="display: ''" align=left>
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanSurveyAttachmentGrid" ></span> 
					</td>
				</tr>
			</table>
		</div>
		<div  id="divSurveyAttachmentPage" style="display: none">
			<center>
				<Input class=cssButton value="首  页" type=button onclick="turnPage4.firstPage();">
				<Input class=cssButton value="上一页" type=button onclick="turnPage4.previousPage();">
				<Input class=cssButton value="下一页" type=button onclick="turnPage4.nextPage();">
				<Input class=cssButton value="尾  页" type=button onclick="turnPage4.lastPage();">
			</center>	
		</div>
  		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divFJ);">
				</td>
				<td class=titleImg>调查附件信息</td>
			</tr>
		</table>
		<div id=divFJ>
			<Input value="新  增" class="cssButton" type=button name=saveAttache onclick="saveAttachmentClick();">
			<Input value="修  改" class="cssButton" type=button name=updateAttache onclick="updateLLinqupload();">
			<Input value="删  除" class="cssButton" type=button name=deleteAttache onclick="deleteLLinqupload();">
			<td style="font:10pt"><font color=red>&nbsp&nbsp&nbsp&nbsp* 请先"新增"附件，然后进行"调查附件上载"</font></td>
		</div>
		<div>
			<table class=common>
				<tr class=common>
					<td class=title> 调查附件名称 </td>
					<td class=Input><Input class=common name=AffixName maxlength="10" elementtype=nacessary></td>
					<td class=title> 原件标识 </td>
					<td class=Input><Input class=codeno name=OriginalLogo ondblclick="return showCodeList('originallogo',[this,OriginalLogoName],[0,1],null,null,null,1);" onkeyup="return showCodeListKey('originallogo',[this,OriginalLogoName],[0,1],null,null,null,1);"><Input class=codename name=OriginalLogoName elementtype=nacessary readonly=true></td>        
					<td class=title> 上传张数 </td>
					<td class=Input><Input class=common name=AffixNumber maxlength="10" elementtype=nacessary value=1></td>
				</tr>
			</table>
		</div>
		<div>
			<td class=title> 调查附件上载 &nbsp&nbsp&nbsp</td><td class=Input><Input type=hidden id="ImportPath" name=ImportPath> </td>
			<Input type="file" name=FileName style= "height:20px ;width:260px " onkeydown="this.blur()" >
			<Input value="上传文件" class="cssButton" type=button id=uploadfile name=uploadfile onclick="uploadFileClick('0');">
			<Input value="替换文件" class="cssButton" type=button id=repalecefile  name=repalecefile onclick="replaceFileClick();">
			<Input value="查看文件" class="cssButton" type=button name=viewfile onclick="viewAttachmentClick();">
			<Input value="删除文件" class="cssButton" type=button id=deletefile name=deletefile onclick="deleteAttachmentClick();">
		</div>
		<Input type=hidden name=AffixPatch>
		<Input type=hidden name=AttachNo><!--附件序号-->
		<Input type=hidden name=InitDept2><!--调查申请的业务机构-->
		<Input type=hidden name=insuredimport value="1">
		<Input type=hidden name=ImportFile>
		<Input type=hidden name=OldFileName>
		<Input type=hidden name=fmtransact><!--操作代码‘insert,delete’等-->   
		<Input type=hidden name=RgtNo>
		<Input type=hidden name=InqNo><!--业务号-->
	</form>
	<br /><br /><br /><br />
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html>
