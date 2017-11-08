<%
/***************************************************************
 * <p>ProName：LSQuotProjUWDetailInput.jsp</p>
 * <p>Title：项目询价核保明细</p>
 * <p>Description：项目询价核保明细</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-31
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tTranProdType = "";
</script>
<html>
<head >
	<title>项目询价核保明细</title>
	<script src="../common/javascript/Common.js"></script>
	<script src="../common/cvar/CCodeOperate.js"></script>
	<script src="../common/javascript/MulLine.js"></script>
	<script src="../common/javascript/EasyQuery.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
	<script src="../common/easyQueryVer3/EasyQueryCache.js"></script>
	<script src="../common/javascript/VerifyInput.js"></script>
	<script src="../common/laydate/laydate.js"></script>
	<link href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<script src="./LSQuotProjUWDetailInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script>
	<script src="./LSQuotPubPlan.js"></script>
	<%@include file="./LSQuotProjUWDetailInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm1 id=fm1 method=post action="" target=fraSubmit>
  <div class=maxbox1>
	<table id=table1 width=100% height=30>
		<tr>
			<td width=1%></td>
			<td class=common align=right> <input class=cssButton type=button id=RequestButton name=RequestButton value="业务需求" onclick="showRequest();"> <input class=cssButton type=button id=FeeButton name=FeeButton value="费用信息" onclick="showFeeInfo();"> <input class=cssButton type=button id=PastButton name=PastButton value="既往信息" onclick="showPast();"> <input class=cssButton type=button id=QuesButton name=QuesButton value="问卷调查" onclick="showQuesnaire();"> <input class=cssButton type=button id=AttachButton name=AttachButton value="附件管理" onclick="showAttachment();"> <input class=cssButton type=button id=SpecButton name=SpecButton value="特别约定" onclick="showGrpSpec();">
		</tr>
	</table>
  </div>
	
	<!--中支公司核保师最终处理-->
	<div id="divSubFinal" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSubFinalInfo);">
				</td>
				<td class=titleImg>中支公司核保最终处理</td>
			</tr>
		</table>
		<div id="divSubFinalInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>中支公司最终意见</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=SubFinalOpinion verify="中支公司最终意见|len<=1500" elementtype=nacessary></textarea></td>
				</tr>
				<tr class=common>
					<td class=title>中支公司最终结论</td>
					<td class=input><input class=codeno name=SubFinalConclu readonly id=SubFinalConclu style="background:url(../common/images/select--bg_03.png) no-repeat right center"
						ondblclick="showCodeList('qsubfinalconclu', [this, SubFinalConcluName],[0,1],null,null,null,1);" 
						onkeyup="showCodeListKey('qsubfinalconclu', [this, SubFinalConcluName],[0,1],null,null,null,1);"><input class=codename name=SubFinalConcluName id=SubFinalConcluName></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			
			<div id="divSubFinalButton" style="display: ''">
				<input class=cssButton type=button name=SubFinalSubmitButton id=SubFinalSubmitButton value="报价生成" onclick="UWSubmit();">
				<input class=cssButton type=button name=SubFinalReturnButton id=SubFinalReturnButton value="回目录" onclick="ReturnList();">
			</div>
		</div>
	</div>
	
	<!--分公司核保师最终处理-->
	<div id="divBranchFinal" style="display: none">
	  <div class=maxbox1>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBranchFinalInfo);">
				</td>
				<td class=titleImg>分公司核保最终处理</td>
			</tr>
		</table>
	  </div>
		<div id="divBranchFinalInfo"class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>分公司最终意见</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=BranchFinalOpinion verify="分公司最终意见|len<=1500" elementtype=nacessary></textarea></td>
				</tr>
				<tr class=common>
					<td class=title>分公司最终结论</td>
					<td class=input><input class=codeno name=BranchFinalConclu id=BranchFinalConclu readonly 
						ondblclick="showCodeList('qbranchfinalconclu', [this, BranchFinalConcluName],[0,1],null,null,null,1);" 
						onkeyup="showCodeListKey('qbranchfinalconclu', [this, BranchFinalConcluName],[0,1],null,null,null,1);"><input class=codename name=BranchFinalConcluName id=BranchFinalConcluName></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			
			<div id="divBranchFinalButton" style="display: ''">
				<input class=cssButton type=button name=BranchFinalSubmitButton id=BranchFinalSubmitButton value="报价生成" onclick="UWSubmit();">
				<input class=cssButton type=button name=BranchFinalReturnButton id=BranchFinalReturnButton value="回目录" onclick="ReturnList();">
			</div>
		</div>
	</div>
	
	<!--核保经理审核-->
	<div id="divUWManager" style="display: none">
	  <div class=maxbox1>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWManagerInfo);">
				</td>
				<td class=titleImg>核保经理审核信息</td>
			</tr>
		</table>
	  </div>
		<div id="divUWManagerInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>核保经理意见</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=UWManagerOpinion verify="核保经理意见|len<=1500" elementtype=nacessary></textarea></td>
				</tr>
				<tr class=common>
					<td class=title>核保经理结论</td>
					<td class=input><input class=codeno name=UWManagerConclu id=UWManagerConclu readonly 
						ondblclick="showCodeList('quwmanagerconclu', [this, UWManagerConcluName],[0,1],null,null,null,1);" 
						onkeyup="showCodeListKey('quwmanagerconclu', [this, UWManagerConcluName],[0,1],null,null,null,1);"><input class=codename name=UWManagerConcluName id=UWManagerConcluName></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			
			<div id="divUWManagerButton" style="display: ''">
				<input class=cssButton type=button name=UWManagerSubmitButton id=UWManagerSubmitButton value="核保提交" onclick="UWSubmit();">
				<input class=cssButton type=button name=UWManagerReturnButton id=UWManagerReturnButton value="回目录" onclick="ReturnList();">
			</div>
		</div>
	</div>
</form>
	
<form name=fm2 id=fm2 method=post action="" ENCTYPE="multipart/form-data" target=fraSubmit>
	<!--其他部门意见-->
	<div id="divOtherOpinion" style="display: none">
	  <div class=maxbox1>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divOtherOpinionInfo);">
				</td>
				<td class=titleImg>其他部门意见</td>
			</tr>
		</table>
	 </div>
		<div id="divOtherOpinionInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanOtherOpinionGrid" ></span>
					</td>
				</tr>
			</table>
			<div id="divTurnPage" style="display: ''">
				<center>		
					<input class=cssButton90 type=button value="首  页" onclick="turnPage2.firstPage();">
					<input class=cssButton91 type=button value="上一页" onclick="turnPage2.previousPage();">
					<input class=cssButton92 type=button value="下一页" onclick="turnPage2.nextPage();">
					<input class=cssButton93 type=button value="尾  页" onclick="turnPage2.lastPage();">
				</center>
			</div>
			<div class=maxbox>
			<table class=common>
				<tr class=common>
					<td class=title>意见类型</td>
					<td class=input colspan=5><input class=codeno name=OpinionType id=OpinionType readonly 
						ondblclick="return showCodeList('opiniontype',[this, OpinionTypeName],[0, 1],null,null,null,'1',180);" 
						onkeyup="return showCodeListKey('opiniontype',[this, OpinionTypeName],[0, 1],null,null,null,'1',180);"><input class=codename name=OpinionTypeName id=OpinionTypeName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>附件路径</td>
					<td class=input colspan=5><input class=common type=file name=UploadPath id=UploadPath style="width:400px"><font color="#FF0000">（仅支持txt，doc，docx，xls，xlsx，pdf，zip，rar，eml，gif 格式的文件上载）</font></td>
				</tr>
				<tr class=common>
					<td class=title>其他部门意见</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=OpinionDesc></textarea></td>
				</tr>
				<tr class=common>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			</div>
			
			<div id="divOtherOpinionButton" style="display: ''">
				<input class=cssButton type=button name=OtherOpinionAddButton id=OtherOpinionAddButton value="增加其他部门意见" onclick="OtherOpinionAdd();">
				<input class=cssButton type=button name=OtherOpinionModifyButton id=OtherOpinionModifyButton value="修改其他部门意见" onclick="OtherOpinionModify();">
				<input class=cssButton type=button name=OtherOpinionDeleteButton id=OtherOpinionDeleteButton value="删除其他部门意见" onclick="OtherOpinionDelete();">
			</div>
		</div>
	</div>
	
	<input type=hidden name=OtherOpinionSerialNo>
</form>
	
<form name=fm3 id=fm3 method=post action="" target=fraSubmit>
	<!--总公司核保-->
	<div id="divUW"class=maxbox1 style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWInfo);">
				</td>
				<td class=titleImg>总公司核保信息</td>
			</tr>
		</table>
		<div id="divUWInfo" class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>再保安排</td>
					<td class=input><input class=codeno name=ReinsArrange id=ReinsArrange readonly 
						ondblclick="showCodeList('reinsarrange', [this, ReinsArrangeName],[0,1],null,null,null,1);" 
						onkeyup="showCodeListKey('reinsarrange', [this, ReinsArrangeName],[0,1],null,null,null,1);"><input class=codename name=ReinsArrangeName id=ReinsArrangeName></td>
					<td class=title id=tdFacul1 name=tdFacul1 style="display: none">临分原因</td>
					<td class=input id=tdFacul2 name=tdFacul2 style="display: none"><input class=codeno name=FaculReason id=FaculReason readonly ondblclick="showCodeList('faculreason', [this, FaculReasonName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('faculreason', [this, FaculReasonName],[0,1],null,null,null,1);"><input class=codename name=FaculReasonName></td>
					<td class=title id=tdFacul3 name=tdFacul3 style="display: none"></td>
					<td class=input id=tdFacul4 name=tdFacul4 style="display: none"></td>
					<td class=title id=tdFacul5 name=tdFacul5 style="display: ''"></td>
					<td class=input id=tdFacul6 name=tdFacul6 style="display: ''"></td>
					<td class=title id=tdFacul7 name=tdFacul7 style="display: ''"></td>
					<td class=input id=tdFacul8 name=tdFacul8 style="display: ''"></td>
				</tr>
				<tr class=common id=tFaculOtherDesc name=tFaculOtherDesc style="display: none">
					<td class=title>临分其他描述</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=FaculOtherDesc verify="临分其他描述|len<=1500" ></textarea><font color=red> *</font></td>
				</tr>
				<tr class=common>
					<td class=title>总公司综合意见</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=UWOpinion verify="总公司综合意见|len<=1500" elementtype=nacessary></textarea></td>
				</tr>
			</table>
			
			<div id="divUWOpinionButton" style="display: ''">
				<input class=cssButton type=button name=UWOpinionSaveButton id=UWOpinionSaveButton value="综合意见保存" onclick="UWOpinionSave();">
				<input class=cssButton type=button name=QuotPrintButton value="打印询价单" onclick="printInquiry('pdf');">
				<!-- input class=cssButton type=button name=QuotPrintButton id=QuotPrintButton value="打印询价单" onclick="printInquiry('doc');"> -->
			</div>
			
		 <div class=maxbox1>	
			<table class=common>
				<tr class=common>
					<td class=title>总公司核保结论</td>
					<td class=input><input class=codeno name=UWConclu id=UWConclu readonly 
						ondblclick="showCodeList('quwconclu', [this, UWConcluName],[0,1],null,null,null,1);" 
						onkeyup="showCodeListKey('quwconclu', [this, UWConcluName],[0,1],null,null,null,1);"><input class=codename name=UWConcluName id=UWConcluName></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		</div>
			<div id="divUWSubmitButton" style="display: ''">
				<input class=cssButton type=button name=UWSubmitButton id=UWSubmitButton value="核保提交" onclick="UWSubmit();">
				<input class=cssButton type=button name=UWReturnButton id=UWReturnButton value="回目录" onclick="ReturnList();">
				<input class=cssButton type=button name=UWQuestionButton id=UWQuestionButton value="问题件查询" onclick="goToQuestion();">
			</div>
		</div>
		<br/>
	</div>
	
	<!--核保规则-->
	<div id="divUWRule" style="display: none">
	  <div class=maxbox1>
		<table>
			<tr>
				<td class=common id=tdUWRule1 name=tdUWRule1>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWRuleInfo);">
				</td>
				<td class=titleImg id=tdUWRule2 name=tdUWRule2>核保规则</td>
			</tr>
		</table>
	  </div>
		<div id="divUWRuleInfo" style="display: ''">
			<input class=cssButton type=button value="核保/保全规则" onclick="showUWRule();">
			<!--<input class=cssButton type=button value="理赔责任控制" onclick="showCMRule();">-->
			<input class=cssButton type=button value="方案组合配置" onclick="showPlanCombi();">
			<!--<input class=cssButton type=button value="责任拓展" onclick="showExpand();">-->
		</div>
	</div>
	
	<!--核保要点-->
	<div id="divUWMainPoint" style="display: none">
	   <div  class=maxbox1>
 		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWMainPointInfo);">
				</td>
				<td class=titleImg>核保要点</td>
			</tr>
		</table>
		</div>
		<div id="divUWMainPointInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanUWMainPointGrid"></span>
					</td>
				</tr>
			</table>
			<div id="divUWMainPointDetail" class=maxbox style="display: none">
				<table class=common>
					<tr class=common id="divSubUWAnaly" style="display: none">
						<td class=title>中支公司要点分析</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=SubUWAnaly verify="中支公司要点分析|len<=1500" elementtype=nacessary></textarea></td>
					</tr>
					<tr class=common id="divBranchUWAnaly" style="display: none">
						<td class=title>分公司要点分析</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=BranchUWAnaly verify="分公司要点分析|len<=1500" elementtype=nacessary></textarea></td>
					</tr>
					<tr class=common>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
						<td class=title></td>
						<td class=input></td>
					</tr>
				</table>
			
				<input class=cssButton type=button value="要点分析保存" name="UWAnalySaveButton" id=UWAnalySaveButton onclick="analySaveClick();"> <input class=cssButton type=button value="要点分析关闭" name="UWAnalyCloseButton" onclick="analyCloseClick();">
			</div>
		</div>
	</div>
	
	<!--分公司核保-->
	<div id="divBranchUW" style="display: none">
	  <div class=maxbox1>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBranchUWInfo);">
				</td>
				<td class=titleImg>分公司核保信息</td>
			</tr>
		</table>
	  </div>
		<div id="divBranchUWInfo" class= style="display: ''">
		  <div class=maxbox1>
			<table class=common>
				<tr class=common>
					<td class=title>分公司综合意见</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=BranchUWOpinion verify="分公司综合意见|len<=1500" elementtype=nacessary></textarea></td>
				</tr>
				<tr>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		  </div>
			
			<div id="divBranchUWOpinionButton" style="display: ''">
				<input class=cssButton type=button name=BranchUWOpinionSaveButton id=BranchUWOpinionSaveButton value="综合意见保存" onclick="UWOpinionSave();">
				<!-- input class=cssButton type=button name=QuotTransButton value="转  阅" onclick="" -->
				<input class=cssButton type=button name=QuotPrintButton id=QuotPrintButton value="打印询价单" onclick="printInquiry('pdf');">
				<!--input class=cssButton type=button name=QuotPrintButton value="打印询价单" onclick="printInquiry('doc');"-->
			</div>
			
         <div class=maxbox1>		
			<table class=common>
				<tr class=common>
					<td class=title>分公司核保结论</td>
					<td class=input><input class=codeno name=BranchUWConclu id=BranchUWConclu readonly 
						ondblclick="showCodeList('qbranchuwconclu', [this, BranchUWConcluName],[0,1],null,null,null,1);" 
						onkeyup="showCodeListKey('qbranchuwconclu', [this, BranchUWConcluName],[0,1],null,null,null,1);"><input class=codename name=BranchUWConcluName id=BranchUWConcluName readonly></td>
					<td class=title colspan=4><input type=checkbox name=BranchUrgentFlag_CK id=BranchUrgentFlag_CK onclick="FlagCheck();"><input type=hidden name=BranchUrgentFlag id=BranchUrgentFlag>是否加急</td>
				</tr>
				<tr>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		  </div>
			
			<div id="divBranchUWSubmitButton" style="display: ''">
				<input class=cssButton type=button name=BranchUWSubmitButton id=BranchUWSubmitButton value="核保提交" onclick="UWSubmit();">
				<input class=cssButton type=button name=BranchUWReturnButton id=BranchUWReturnButton value="回目录" onclick="ReturnList();">
				<input class=cssButton type=button name=BranchUWQuestionButton id=BranchUWQuestionButton value="问题件查询" onclick="goToQuestion();">
			</div>
		</div>
	</div>
	
	<!--中支公司核保-->
	<div id="divSubUW" style="display: none">
	  <div class=maxbox1>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSubUWInfo);">
				</td>
				<td class=titleImg>中支公司核保信息</td>
			</tr>
		</table>
	  </div>
		<div id="divSubUWInfo" style="display: ''">
		  <div class=maxbox1>
			<table class=common>
				<tr class=common>
					<td class=title>中支公司综合意见</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=SubUWOpinion verify="中支公司综合意见|len<=1500" elementtype=nacessary></textarea></td>
				</tr>
				<tr>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		  </div>
			
			<div id="divSubUWOpinionButton" style="display: ''">
				<input class=cssButton type=button name=BranchUWQuestionButton  id=BranchUWQuestionButton value="综合意见保存" onclick="UWOpinionSave();">
			</div>
			
		  <div class=maxbox1>
			<table class=common>
				<tr class=common>
					<td class=title>中支公司核保结论</td>
					<td class=input><input class=codeno name=SubUWConclu id=SubUWConclu readonly 
						ondblclick="showCodeList('qsubuwconclu', [this, SubUWConcluName],[0,1],null,null,null,1);" 
						onkeyup="showCodeListKey('qsubuwconclu', [this, SubUWConcluName],[0,1],null,null,null,1);"><input class=codename name=SubUWConcluName id=SubUWConcluName readonly elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
		  </div>
			
			<div id="divSubUWSubmitButton" style="display: ''">
				<input class=cssButton type=button name=SubUWSubmitButton id=SubUWSubmitButton value="核保提交" onclick="UWSubmit();">
				<input class=cssButton type=button name=SubUWReturnButton id=SubUWReturnButton value="回目录" onclick="ReturnList();">
				<input class=cssButton type=button name=SubUWQuestionButton  id=SubUWQuestionButton value="问题件查询" onclick="goToQuestion();">
			</div>
		</div>
	</div>
	
	<!--询价基本信息-->
	<div id="divProjQuotBasic" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divProjQuotBasicDetail);">
				</td>
				<td class=titleImg>询价基本信息</td>
			</tr>
		</table>
		<div id="divProjQuotBasicDetail"  class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>询价号</td>
					<td class=input><input class="wid readonly" name=ProjQuotNo id=ProjQuotNo readonly></td>
					<td class=title>批次号</td>
					<td class=input><input class="wid readonly" name=ProjQuotBatNo id=ProjQuotBatNo readonly></td>
					<td class=title>项目名称</td>
					<td class=input><input class="wid readonly" name=ProjName id=ProjName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>目标客户</td>
					<td class=input><input class="wid readonly" name=TargetCust id=TargetCust readonly></td>
					<td class=title>被保险人数量</td>
					<td class=input><input class="wid readonly" name=NumPeople id=NumPeople readonly></td>
					<td class=title>业务规模(元)</td>
					<td class=input><input class="wid readonly" name=ProjPrePrem id=ProjPrePrem readonly></td>
				</tr>
				<tr class=common>
					<td class=title>预估赔付率</td>
					<td class=input><input class="wid readonly" name=PreLossRatio id=PreLossRatio readonly></td>
					<td class=title>合作方</td>
					<td class=input><input class="wid readonly" name=Partner id=Partner readonly></td>
					<td class=title>有效止期</td>
					<td class=input><input class="wid readonly" name=ExpiryDate id=ExpiryDate readonly></td>
				</tr>
				<tr class=common>
					<td class=title>产品类型</td>
					<td class=input><input class="wid readonly" name=ProjProdTypeName id=ProjProdTypeName readonly></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>是否为统括单</td>
					<td class=input><input class="wid readonly" name=ProjBlanketFlagName id=ProjBlanketFlagName readonly></td>
					<td class=title id=tdElasticFlag1 name=tdElasticFlag1 style="display: none">是否为弹性计划</td>
					<td class=input id=tdElasticFlag2 name=tdElasticFlag2 style="display: none"><input class="wid readonly" name=ElasticFlag id=ElasticFlag type=hidden><input class="wid readonly" name=ElasticFlagName id=ElasticFlagName readonly></td>
					<td class=title id=tdElasticFlag3 name=tdElasticFlag3 style="display: ''"></td>
					<td class=input id=tdElasticFlag4 name=tdElasticFlag4 style="display: ''"></td>
					<td class=title id=tdElasticFlag5 name=tdElasticFlag5 style="display: ''"></td>
					<td class=input id=tdElasticFlag6 name=tdElasticFlag6 style="display: ''"></td>
				</tr>
				<tr class=common>
					<td class=title colspan=6>
						<div id="divPlanDiv" style="display: ''">
						</div>
					</td>
				</tr>
				<tr class=common>
					<td class=title>项目说明</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=ProjDesc></textarea></td>
				</tr>
				<tr class=common>
					<td class=title colspan=6>
						<div id="divPayIntvDiv" style="display: ''">
						</div>
					</td>
				</tr>
				<tr class=common>
					<td class=title colspan=6>
						<div id="divComInfo" style="display: none">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanComListGrid" ></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr class=common>
					<td class=title colspan=6>
						<div id="divSaleChannel" style="display: ''">
						</div>
					</td>
				</tr>
				<tr class=common>
					<td class=title colspan=6>
						<div id="divAgencyInfo" style="display: none">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanAgencyListGrid" ></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				<tr class=common>
					<td class=title colspan=6><input class=checkbox type=checkbox disabled name=RelaQuotFlag id=RelaQuotFlag>关联其他项目询价号既往信息</td>
				</tr>
				<tr class=common>
					<td class=title colspan=6>
						<div id="divRelaQuotInfo" style="display: none">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanRelaQuotListGrid"></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<input type=hidden name=UWMainPointRiskCode>
	<input type=hidden name=UWMainPointRuleCode>
</form>

<!--询价方案信息-->
<form name=fmPlan id=fmPlan method=post action="" target=fraSubmit>
  
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
			</td>
			<td class=titleImg>询价－方案信息维护<span style="color: red">（第一步）</span></td>
		</tr>
	</table>
  
	<div id="divInfo1" name="divInfo1" style="display: ''" >
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPlanInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="首  页" onclick="turnPage6.firstPage();">
			<input class=cssButton91 type=button value="上一页" onclick="turnPage6.previousPage();">
			<input class=cssButton92 type=button value="下一页" onclick="turnPage6.nextPage();">
			<input class=cssButton93 type=button value="尾  页" onclick="turnPage6.lastPage();">
		</center>
	</div>
	
	<div id="divInfo2" style="display: ''" >
		<table class=common>
			<tr class=common>
				<td class=title>方案描述</td>
				<td class=input><input class="wid common" name=PlanDesc id=PlanDesc elementtype=nacessary></td>
				<td class=title>方案编码</td>
				<td class=input><input class="wid readonly" name=PlanCode id=PlanCode readonly></td>
				<td class=title style="display: none">系统方案编码</td>
				<td class=input style="display: none"><input class="wid readonly" name=SysPlanCode id=SysPlanCode readonly></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>保险期限</td>
				<td class=input><input class="wid common" style="width:60px" name=InsuPeriod id=InsuPeriod verify="保险期限|INT&value>0"><input type=hidden name=InsuPeriodFlag id=InsuPeriodFlag type><input class=codeno name=InsuPeriodFlagName id=InsuPeriodFlagName ondblclick="return showCodeList('insuperiodflag', [this,InsuPeriodFlag], [1,0], null, null, null, '1', null);" onkeyup="return showCodeListKey('insuperiodflag', [this,InsuPeriodFlag], [1,0], null, null, null, '1', null);" elementtype=nacessary readonly></td>
				<td class=title id=tdPlan5 name=tdPlan5 style="display: none">方案类型</td>
				<td class=input id=tdPlan6 name=tdPlan6 style="display: none"><input class=codeno name=PlanType id=PlanType ondblclick="return showCodeList('quotplantype',[this, PlanTypeName],[0,1]);" onkeyup="return showCodeListKey('quotplantype',[this, PlanTypeName],[0,1]);" readonly><input class=codename name=PlanTypeName readonly elementtype=nacessary></td>
				<td class=title id=tdPlan8 name=tdPlan8 style="display: none">方案标识</td>
				<td class=input id=tdPlan9 name=tdPlan9 style="display: none"><input class=codeno name=PlanFlag id=PlanFlag ondblclick="return showCodeList('planflag', [this,PlanFlagName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('planflag', [this,PlanFlagName], [0,1], null, null, null, '1', null);"><input class=codename name=PlanFlagName readonly elementtype=nacessary></td>
				<td class=title id=tdPlan10 name=tdPlan10 style="display: none">保费计算方式</td>
				<td class=input id=tdPlan11 name=tdPlan11 style="display: none"><input class=codeno name=PremCalType id=PremCalType ondblclick="return showCodeList('engincaltype', [this,PremCalTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('engincaltype', [this,PremCalTypeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PremCalTypeName readonly elementtype=nacessary></td>
				<td class=title id=tdPlan12 name=tdPlan12 style="display: none">方案人数</td>
				<td class=input id=tdPlan13 name=tdPlan13 style="display: none"><input class=common name=PlanPeople id=PlanPeople elementtype=nacessary></td>
				<td class=title id=tdPlan14 name=tdPlan14 style="display: none">最低工程造价(元)</td>
				<td class=input id=tdPlan15 name=tdPlan15 style="display: none"><input class=common name=EnginCost id=EnginCost elementtype=nacessary></td>
				<td class=title id=tdPlan16 name=tdPlan16 style="display: none">最低工程面积(平方米)</td>
				<td class=input id=tdPlan17 name=tdPlan17 style="display: none"><input class=common name=EnginArea id=EnginArea elementtype=nacessary></td>
				<td class=title id=tdPlan1 name=tdPlan1 style="display: ''"></td>
				<td class=input id=tdPlan2 name=tdPlan2 style="display: ''"></td>
				<td class=title id=tdPlan3 name=tdPlan3 style="display: ''"></td>
				<td class=input id=tdPlan4 name=tdPlan4 style="display: ''"></td>
			<tr>
			<tr class=common id=trPlan1 name=trPlan1 style="display: none">
				<td class=title>职业类型</td>
				<td class=input colspan=5><input class=radio type="radio" id=OccupTypeRadio1 name=OccupTypeRadio id=OccupTypeRadio onclick="chooseOccupFlag('1');">单一职业类别<input class=radio type="radio" id=OccupTypeRadio2 name=OccupTypeRadio onclick="chooseOccupFlag('2');">多职业类别<input type=hidden name=OccupTypeFlag id=OccupTypeFlag></td>
			</tr>
			<tr class=common id=trOccupType1 name=trOccupType1 style="display: none">
				<td class=title>职业类别</td>
				<td class=input><input class=codeno name=OccupType id=OccupType ondblclick="return returnShowCodeList('occuptype1',[this, OccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype1',[this, OccupTypeName],[0,1]);" readonly><input class=codename name=OccupTypeName id=OccupTypeName readonly elementtype=nacessary></td>
				<td class=title>职业中分类</td>
				<td class=input><input class=codeno name=OccupMidType id=OccupMidType ondblclick="return returnShowCodeList('occupmidtype',[this, OccupMidTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occupmidtype',[this, OccupMidTypeName],[0,1]);" ><input class=codename name=OccupMidTypeName id=OccupMidTypeName readonly> <span id=spanOccupMid name=spanOccupMid style="display: none;color: red">*</span></td>
				<td class=title>工种</td>
				<td class=input nowrap><input class=codeno name=OccupCode id=OccupCode ondblclick="return returnShowCodeList('occupcode',[this, OccupCodeName],[0,1]);" onkeyup="return returnShowCodeListKey('occupcode',[this, OccupCodeName],[0,1]);" ><input class=codename name=OccupCodeName onkeydown="fuzzyQueryOccupCode(OccupCode,OccupCodeName)"> <span id=spanOccupCode name=spanOccupCode style="display: none;color: red">*</span><span style="color: red">(支持模糊查询)</span></td>
			</tr>
			<tr class=common id=trOccupType2 name=trOccupType2 style="display: none">
				<td class=title>最低职业类别</td>
				<td class=input><input class=codeno name=MinOccupType id=MinOccupType ondblclick="return returnShowCodeList('occuptype2',[this, MinOccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype2',[this, MinOccupTypeName],[0,1]);" readonly><input class=codename name=MinOccupTypeName id=MinOccupTypeName readonly elementtype=nacessary></td>
				<td class=title>最高职业类别</td>
				<td class=input><input class=codeno name=MaxOccupType id=MaxOccupType ondblclick="return returnShowCodeList('occuptype2',[this, MaxOccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype2',[this, MaxOccupTypeName],[0,1]);" readonly><input class=codename name=MaxOccupTypeName id=MaxOccupTypeName readonly elementtype=nacessary></td>
				<td class=title>职业比例</td>
				<td class=input><input class=common name=OccupRate id=OccupRate elementtype=nacessary></td>
			</tr>
			<tr class=common id=trPlan2 name=trPlan2 style="display: none">
				<td class=title>最低年龄(岁)</td>
				<td class=input><input class=common name=MinAge id=MinAge verify="最低年龄(岁)|INT&value>=0"></td>
				<td class=title>最高年龄(岁)</td>
				<td class=input><input class=common name=MaxAge id=MaxAge verify="最高年龄(岁)|INT&value>=0"></td>
				<td class=title>平均年龄(岁)</td>
				<td class=input><input class=common name=AvgAge id=AvgAge verify="平均年龄(岁)|INT&value>=0" elementtype=nacessary></td>
			</tr>
			<tr class=common id=trPlan3 name=trPlan3 style="display: none">
				<td class=title>人数</td>
				<td class=input><input class=common name=NumPeople id=NumPeople verify="人数|INT&value>=0"></td>
				<td class=title>参加社保占比</td>
				<td class=input><input class=common name=SocialInsuRate id=SocialInsuRate verify="参加社保占比|num&value>=0&value<=1" elementtype=nacessary></td>
				<td class=title>男女比例</td>
				<td class=input><input class=common style="width:50px" name=MaleRate id=MaleRate verify="男女比例|INT&value>=0" maxlength=9><b>：</b><input class=common style="width:50px" name=FemaleRate id=FemaleRate verify="男女比例|INT&value>=0" maxlength=9><font color=red> * (如 2:3)</font></td>
			</tr>
			<tr class=common id=trPlan4 name=trPlan4 style="display: none">
				<td class=title>退休占比</td>
				<td class=input><input class=common name=RetireRate id=RetireRate verify="退休占比|num&value>=0&value<=1"></td>
				<td class=title>保费分摊方式</td>
				<td class=input><input class=codeno name=PremMode id=PremMode ondblclick="return showCodeList('premmode', [this,PremModeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('premmode', [this,PremModeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PremModeName readonly></td>
				<td class=title>企业负担占比</td>
				<td class=input><input class=common name=EnterpriseRate id=EnterpriseRate verify="企业负担占比|num&value>=0&value<=1"></td>
			</tr>
			<tr class=common id=trPlan5 name=trPlan5 style="display: none">
				<td class=title>最低月薪(元)</td>
				<td class=input><input class=common name=MinSalary id=MinSalary verify="最低月薪(元)|num&value>=0"></td>
				<td class=title>最高月薪(元)</td>
				<td class=input><input class=common name=MaxSalary id=MaxSalary verify="最高月薪(元)|num&value>=0"></td>
				<td class=title>平均月薪(元)</td>
				<td class=input><input class=common name=AvgSalary id=AvgSalary verify="平均月薪(元)|num&value>=0"></td>
			</tr>
			<tr class=common id=trPlan6 name=trPlan6 style="display: none">
				<td class=title id=tdEngin1 name=tdEngin1 style="display: none">最高工程造价(元)</td>
				<td class=input id=tdEngin2 name=tdEngin2 style="display: none"><input class=common name=MaxEnginCost id=MaxEnginCost elementtype=nacessary></td>
				<td class=title id=tdEngin3 name=tdEngin3 style="display: none">最高工程面积(平方米)</td>
				<td class=input id=tdEngin4 name=tdEngin4 style="display: none"><input class=common name=MaxEnginArea id=MaxEnginArea elementtype=nacessary></td>
				<td class=title>工程类型</td>
				<td class=input><input class=codeno name=EnginType id=EnginType ondblclick="return showCodeList('engintype', [this,EnginTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('engintype', [this,EnginTypeName], [0,1], null, null, null, '1', null);"><input class=codename name=EnginTypeName readonly elementtype=nacessary></td>
				<td class=title>施工天数</td>
				<td class=input><input class=common name=EnginDays id=EnginDays maxlength=5  verify="施工天数|INT&value>0" elementtype=nacessary></td>
				<td class=title id=tdEngin5 name=tdEngin5 style="display: ''"></td>
				<td class=input id=tdEngin6 name=tdEngin6 style="display: ''"></td>
			</tr>
		</table>
		<div id=divEnginFactor>
		</div>
		
		<table class=common>
			<tr class=common>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common id=trPlan7 name=trPlan7 style="display: none">
				<td class=title>工程概述</td>
				<td class=input colspan=5><textarea cols=80 rows=3 name=EnginDesc elementtype=nacessary></textarea></td>
			</tr>
			<tr class=common id=trEnginCondition name=trEnginCondition style="display: none">
				<td class=title>工程状况说明</td>
				<td class=input colspan=5><textarea cols=80 rows=3 name=EnginCondition elementtype=nacessary></textarea><span style="color: red">(请说明各标段的地质、水文条件、常见自然灾害及损失情况)</span></td>
			</tr>
			<tr class=common>
				<td class=title>其他说明</td>
				<td class=input colspan=5><textarea class=common cols=80 rows=2 name=OtherDesc verify="其他说明|len<=1000"></textarea></td>
			</tr>
		</table>
		
		<input class=cssButton type=button name=AddPlanButton value="新  增" onclick="addPlan();">
		<input class=cssButton type=button name=ModifyPlanButton value="修  改" onclick="modifyPlan();">
		<input class=cssButton type=button name=DelPlanButton value="删  除" onclick="delPlan();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo3);">
			</td>
			<td class=titleImg>询价－方案明细信息维护<span style="color: red">（第二步）</span></td>
		</tr>
	</table>
	
	<div id="divInfo3" class=maxbox1>
		<input class=cssButton type=button name=PlanDetailButton value="方案信息维护" onclick="planDetailOpen();">
		<input class=cssButton type=button name=ShowAllDetailButton value="方案明细一览" onclick="showAllDetail();">
	</div>
</form>

<form name=fmPub id=fmPub method=post action="" target=fraSubmit>
	<div style="display: none">
		<input type=hidden name=Operate>
		<input type=hidden name=HiddenCodeType>
	</div>
</form>
<span id="spanCode" style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
