<%
/***************************************************************
 * <p>ProName��LSQuotProjUWDetailInput.jsp</p>
 * <p>Title����Ŀѯ�ۺ˱���ϸ</p>
 * <p>Description����Ŀѯ�ۺ˱���ϸ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
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
	<title>��Ŀѯ�ۺ˱���ϸ</title>
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
			<td class=common align=right> <input class=cssButton type=button id=RequestButton name=RequestButton value="ҵ������" onclick="showRequest();"> <input class=cssButton type=button id=FeeButton name=FeeButton value="������Ϣ" onclick="showFeeInfo();"> <input class=cssButton type=button id=PastButton name=PastButton value="������Ϣ" onclick="showPast();"> <input class=cssButton type=button id=QuesButton name=QuesButton value="�ʾ����" onclick="showQuesnaire();"> <input class=cssButton type=button id=AttachButton name=AttachButton value="��������" onclick="showAttachment();"> <input class=cssButton type=button id=SpecButton name=SpecButton value="�ر�Լ��" onclick="showGrpSpec();">
		</tr>
	</table>
  </div>
	
	<!--��֧��˾�˱�ʦ���մ���-->
	<div id="divSubFinal" style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSubFinalInfo);">
				</td>
				<td class=titleImg>��֧��˾�˱����մ���</td>
			</tr>
		</table>
		<div id="divSubFinalInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>��֧��˾�������</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=SubFinalOpinion verify="��֧��˾�������|len<=1500" elementtype=nacessary></textarea></td>
				</tr>
				<tr class=common>
					<td class=title>��֧��˾���ս���</td>
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
				<input class=cssButton type=button name=SubFinalSubmitButton id=SubFinalSubmitButton value="��������" onclick="UWSubmit();">
				<input class=cssButton type=button name=SubFinalReturnButton id=SubFinalReturnButton value="��Ŀ¼" onclick="ReturnList();">
			</div>
		</div>
	</div>
	
	<!--�ֹ�˾�˱�ʦ���մ���-->
	<div id="divBranchFinal" style="display: none">
	  <div class=maxbox1>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBranchFinalInfo);">
				</td>
				<td class=titleImg>�ֹ�˾�˱����մ���</td>
			</tr>
		</table>
	  </div>
		<div id="divBranchFinalInfo"class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>�ֹ�˾�������</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=BranchFinalOpinion verify="�ֹ�˾�������|len<=1500" elementtype=nacessary></textarea></td>
				</tr>
				<tr class=common>
					<td class=title>�ֹ�˾���ս���</td>
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
				<input class=cssButton type=button name=BranchFinalSubmitButton id=BranchFinalSubmitButton value="��������" onclick="UWSubmit();">
				<input class=cssButton type=button name=BranchFinalReturnButton id=BranchFinalReturnButton value="��Ŀ¼" onclick="ReturnList();">
			</div>
		</div>
	</div>
	
	<!--�˱��������-->
	<div id="divUWManager" style="display: none">
	  <div class=maxbox1>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWManagerInfo);">
				</td>
				<td class=titleImg>�˱����������Ϣ</td>
			</tr>
		</table>
	  </div>
		<div id="divUWManagerInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>�˱��������</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=UWManagerOpinion verify="�˱��������|len<=1500" elementtype=nacessary></textarea></td>
				</tr>
				<tr class=common>
					<td class=title>�˱��������</td>
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
				<input class=cssButton type=button name=UWManagerSubmitButton id=UWManagerSubmitButton value="�˱��ύ" onclick="UWSubmit();">
				<input class=cssButton type=button name=UWManagerReturnButton id=UWManagerReturnButton value="��Ŀ¼" onclick="ReturnList();">
			</div>
		</div>
	</div>
</form>
	
<form name=fm2 id=fm2 method=post action="" ENCTYPE="multipart/form-data" target=fraSubmit>
	<!--�����������-->
	<div id="divOtherOpinion" style="display: none">
	  <div class=maxbox1>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divOtherOpinionInfo);">
				</td>
				<td class=titleImg>�����������</td>
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
					<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage2.firstPage();">
					<input class=cssButton91 type=button value="��һҳ" onclick="turnPage2.previousPage();">
					<input class=cssButton92 type=button value="��һҳ" onclick="turnPage2.nextPage();">
					<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage2.lastPage();">
				</center>
			</div>
			<div class=maxbox>
			<table class=common>
				<tr class=common>
					<td class=title>�������</td>
					<td class=input colspan=5><input class=codeno name=OpinionType id=OpinionType readonly 
						ondblclick="return showCodeList('opiniontype',[this, OpinionTypeName],[0, 1],null,null,null,'1',180);" 
						onkeyup="return showCodeListKey('opiniontype',[this, OpinionTypeName],[0, 1],null,null,null,'1',180);"><input class=codename name=OpinionTypeName id=OpinionTypeName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>����·��</td>
					<td class=input colspan=5><input class=common type=file name=UploadPath id=UploadPath style="width:400px"><font color="#FF0000">����֧��txt��doc��docx��xls��xlsx��pdf��zip��rar��eml��gif ��ʽ���ļ����أ�</font></td>
				</tr>
				<tr class=common>
					<td class=title>�����������</td>
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
				<input class=cssButton type=button name=OtherOpinionAddButton id=OtherOpinionAddButton value="���������������" onclick="OtherOpinionAdd();">
				<input class=cssButton type=button name=OtherOpinionModifyButton id=OtherOpinionModifyButton value="�޸������������" onclick="OtherOpinionModify();">
				<input class=cssButton type=button name=OtherOpinionDeleteButton id=OtherOpinionDeleteButton value="ɾ�������������" onclick="OtherOpinionDelete();">
			</div>
		</div>
	</div>
	
	<input type=hidden name=OtherOpinionSerialNo>
</form>
	
<form name=fm3 id=fm3 method=post action="" target=fraSubmit>
	<!--�ܹ�˾�˱�-->
	<div id="divUW"class=maxbox1 style="display: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWInfo);">
				</td>
				<td class=titleImg>�ܹ�˾�˱���Ϣ</td>
			</tr>
		</table>
		<div id="divUWInfo" class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>�ٱ�����</td>
					<td class=input><input class=codeno name=ReinsArrange id=ReinsArrange readonly 
						ondblclick="showCodeList('reinsarrange', [this, ReinsArrangeName],[0,1],null,null,null,1);" 
						onkeyup="showCodeListKey('reinsarrange', [this, ReinsArrangeName],[0,1],null,null,null,1);"><input class=codename name=ReinsArrangeName id=ReinsArrangeName></td>
					<td class=title id=tdFacul1 name=tdFacul1 style="display: none">�ٷ�ԭ��</td>
					<td class=input id=tdFacul2 name=tdFacul2 style="display: none"><input class=codeno name=FaculReason id=FaculReason readonly ondblclick="showCodeList('faculreason', [this, FaculReasonName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('faculreason', [this, FaculReasonName],[0,1],null,null,null,1);"><input class=codename name=FaculReasonName></td>
					<td class=title id=tdFacul3 name=tdFacul3 style="display: none"></td>
					<td class=input id=tdFacul4 name=tdFacul4 style="display: none"></td>
					<td class=title id=tdFacul5 name=tdFacul5 style="display: ''"></td>
					<td class=input id=tdFacul6 name=tdFacul6 style="display: ''"></td>
					<td class=title id=tdFacul7 name=tdFacul7 style="display: ''"></td>
					<td class=input id=tdFacul8 name=tdFacul8 style="display: ''"></td>
				</tr>
				<tr class=common id=tFaculOtherDesc name=tFaculOtherDesc style="display: none">
					<td class=title>�ٷ���������</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=FaculOtherDesc verify="�ٷ���������|len<=1500" ></textarea><font color=red> *</font></td>
				</tr>
				<tr class=common>
					<td class=title>�ܹ�˾�ۺ����</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=UWOpinion verify="�ܹ�˾�ۺ����|len<=1500" elementtype=nacessary></textarea></td>
				</tr>
			</table>
			
			<div id="divUWOpinionButton" style="display: ''">
				<input class=cssButton type=button name=UWOpinionSaveButton id=UWOpinionSaveButton value="�ۺ��������" onclick="UWOpinionSave();">
				<input class=cssButton type=button name=QuotPrintButton value="��ӡѯ�۵�" onclick="printInquiry('pdf');">
				<!-- input class=cssButton type=button name=QuotPrintButton id=QuotPrintButton value="��ӡѯ�۵�" onclick="printInquiry('doc');"> -->
			</div>
			
		 <div class=maxbox1>	
			<table class=common>
				<tr class=common>
					<td class=title>�ܹ�˾�˱�����</td>
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
				<input class=cssButton type=button name=UWSubmitButton id=UWSubmitButton value="�˱��ύ" onclick="UWSubmit();">
				<input class=cssButton type=button name=UWReturnButton id=UWReturnButton value="��Ŀ¼" onclick="ReturnList();">
				<input class=cssButton type=button name=UWQuestionButton id=UWQuestionButton value="�������ѯ" onclick="goToQuestion();">
			</div>
		</div>
		<br/>
	</div>
	
	<!--�˱�����-->
	<div id="divUWRule" style="display: none">
	  <div class=maxbox1>
		<table>
			<tr>
				<td class=common id=tdUWRule1 name=tdUWRule1>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWRuleInfo);">
				</td>
				<td class=titleImg id=tdUWRule2 name=tdUWRule2>�˱�����</td>
			</tr>
		</table>
	  </div>
		<div id="divUWRuleInfo" style="display: ''">
			<input class=cssButton type=button value="�˱�/��ȫ����" onclick="showUWRule();">
			<!--<input class=cssButton type=button value="�������ο���" onclick="showCMRule();">-->
			<input class=cssButton type=button value="�����������" onclick="showPlanCombi();">
			<!--<input class=cssButton type=button value="������չ" onclick="showExpand();">-->
		</div>
	</div>
	
	<!--�˱�Ҫ��-->
	<div id="divUWMainPoint" style="display: none">
	   <div  class=maxbox1>
 		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWMainPointInfo);">
				</td>
				<td class=titleImg>�˱�Ҫ��</td>
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
						<td class=title>��֧��˾Ҫ�����</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=SubUWAnaly verify="��֧��˾Ҫ�����|len<=1500" elementtype=nacessary></textarea></td>
					</tr>
					<tr class=common id="divBranchUWAnaly" style="display: none">
						<td class=title>�ֹ�˾Ҫ�����</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=BranchUWAnaly verify="�ֹ�˾Ҫ�����|len<=1500" elementtype=nacessary></textarea></td>
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
			
				<input class=cssButton type=button value="Ҫ���������" name="UWAnalySaveButton" id=UWAnalySaveButton onclick="analySaveClick();"> <input class=cssButton type=button value="Ҫ������ر�" name="UWAnalyCloseButton" onclick="analyCloseClick();">
			</div>
		</div>
	</div>
	
	<!--�ֹ�˾�˱�-->
	<div id="divBranchUW" style="display: none">
	  <div class=maxbox1>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBranchUWInfo);">
				</td>
				<td class=titleImg>�ֹ�˾�˱���Ϣ</td>
			</tr>
		</table>
	  </div>
		<div id="divBranchUWInfo" class= style="display: ''">
		  <div class=maxbox1>
			<table class=common>
				<tr class=common>
					<td class=title>�ֹ�˾�ۺ����</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=BranchUWOpinion verify="�ֹ�˾�ۺ����|len<=1500" elementtype=nacessary></textarea></td>
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
				<input class=cssButton type=button name=BranchUWOpinionSaveButton id=BranchUWOpinionSaveButton value="�ۺ��������" onclick="UWOpinionSave();">
				<!-- input class=cssButton type=button name=QuotTransButton value="ת  ��" onclick="" -->
				<input class=cssButton type=button name=QuotPrintButton id=QuotPrintButton value="��ӡѯ�۵�" onclick="printInquiry('pdf');">
				<!--input class=cssButton type=button name=QuotPrintButton value="��ӡѯ�۵�" onclick="printInquiry('doc');"-->
			</div>
			
         <div class=maxbox1>		
			<table class=common>
				<tr class=common>
					<td class=title>�ֹ�˾�˱�����</td>
					<td class=input><input class=codeno name=BranchUWConclu id=BranchUWConclu readonly 
						ondblclick="showCodeList('qbranchuwconclu', [this, BranchUWConcluName],[0,1],null,null,null,1);" 
						onkeyup="showCodeListKey('qbranchuwconclu', [this, BranchUWConcluName],[0,1],null,null,null,1);"><input class=codename name=BranchUWConcluName id=BranchUWConcluName readonly></td>
					<td class=title colspan=4><input type=checkbox name=BranchUrgentFlag_CK id=BranchUrgentFlag_CK onclick="FlagCheck();"><input type=hidden name=BranchUrgentFlag id=BranchUrgentFlag>�Ƿ�Ӽ�</td>
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
				<input class=cssButton type=button name=BranchUWSubmitButton id=BranchUWSubmitButton value="�˱��ύ" onclick="UWSubmit();">
				<input class=cssButton type=button name=BranchUWReturnButton id=BranchUWReturnButton value="��Ŀ¼" onclick="ReturnList();">
				<input class=cssButton type=button name=BranchUWQuestionButton id=BranchUWQuestionButton value="�������ѯ" onclick="goToQuestion();">
			</div>
		</div>
	</div>
	
	<!--��֧��˾�˱�-->
	<div id="divSubUW" style="display: none">
	  <div class=maxbox1>
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSubUWInfo);">
				</td>
				<td class=titleImg>��֧��˾�˱���Ϣ</td>
			</tr>
		</table>
	  </div>
		<div id="divSubUWInfo" style="display: ''">
		  <div class=maxbox1>
			<table class=common>
				<tr class=common>
					<td class=title>��֧��˾�ۺ����</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=SubUWOpinion verify="��֧��˾�ۺ����|len<=1500" elementtype=nacessary></textarea></td>
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
				<input class=cssButton type=button name=BranchUWQuestionButton  id=BranchUWQuestionButton value="�ۺ��������" onclick="UWOpinionSave();">
			</div>
			
		  <div class=maxbox1>
			<table class=common>
				<tr class=common>
					<td class=title>��֧��˾�˱�����</td>
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
				<input class=cssButton type=button name=SubUWSubmitButton id=SubUWSubmitButton value="�˱��ύ" onclick="UWSubmit();">
				<input class=cssButton type=button name=SubUWReturnButton id=SubUWReturnButton value="��Ŀ¼" onclick="ReturnList();">
				<input class=cssButton type=button name=SubUWQuestionButton  id=SubUWQuestionButton value="�������ѯ" onclick="goToQuestion();">
			</div>
		</div>
	</div>
	
	<!--ѯ�ۻ�����Ϣ-->
	<div id="divProjQuotBasic" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divProjQuotBasicDetail);">
				</td>
				<td class=titleImg>ѯ�ۻ�����Ϣ</td>
			</tr>
		</table>
		<div id="divProjQuotBasicDetail"  class=maxbox style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>ѯ�ۺ�</td>
					<td class=input><input class="wid readonly" name=ProjQuotNo id=ProjQuotNo readonly></td>
					<td class=title>���κ�</td>
					<td class=input><input class="wid readonly" name=ProjQuotBatNo id=ProjQuotBatNo readonly></td>
					<td class=title>��Ŀ����</td>
					<td class=input><input class="wid readonly" name=ProjName id=ProjName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>Ŀ��ͻ�</td>
					<td class=input><input class="wid readonly" name=TargetCust id=TargetCust readonly></td>
					<td class=title>������������</td>
					<td class=input><input class="wid readonly" name=NumPeople id=NumPeople readonly></td>
					<td class=title>ҵ���ģ(Ԫ)</td>
					<td class=input><input class="wid readonly" name=ProjPrePrem id=ProjPrePrem readonly></td>
				</tr>
				<tr class=common>
					<td class=title>Ԥ���⸶��</td>
					<td class=input><input class="wid readonly" name=PreLossRatio id=PreLossRatio readonly></td>
					<td class=title>������</td>
					<td class=input><input class="wid readonly" name=Partner id=Partner readonly></td>
					<td class=title>��Чֹ��</td>
					<td class=input><input class="wid readonly" name=ExpiryDate id=ExpiryDate readonly></td>
				</tr>
				<tr class=common>
					<td class=title>��Ʒ����</td>
					<td class=input><input class="wid readonly" name=ProjProdTypeName id=ProjProdTypeName readonly></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>�Ƿ�Ϊͳ����</td>
					<td class=input><input class="wid readonly" name=ProjBlanketFlagName id=ProjBlanketFlagName readonly></td>
					<td class=title id=tdElasticFlag1 name=tdElasticFlag1 style="display: none">�Ƿ�Ϊ���Լƻ�</td>
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
					<td class=title>��Ŀ˵��</td>
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
					<td class=title colspan=6><input class=checkbox type=checkbox disabled name=RelaQuotFlag id=RelaQuotFlag>����������Ŀѯ�ۺż�����Ϣ</td>
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

<!--ѯ�۷�����Ϣ-->
<form name=fmPlan id=fmPlan method=post action="" target=fraSubmit>
  
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
			</td>
			<td class=titleImg>ѯ�ۣ�������Ϣά��<span style="color: red">����һ����</span></td>
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
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage6.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage6.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage6.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage6.lastPage();">
		</center>
	</div>
	
	<div id="divInfo2" style="display: ''" >
		<table class=common>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class="wid common" name=PlanDesc id=PlanDesc elementtype=nacessary></td>
				<td class=title>��������</td>
				<td class=input><input class="wid readonly" name=PlanCode id=PlanCode readonly></td>
				<td class=title style="display: none">ϵͳ��������</td>
				<td class=input style="display: none"><input class="wid readonly" name=SysPlanCode id=SysPlanCode readonly></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class="wid common" style="width:60px" name=InsuPeriod id=InsuPeriod verify="��������|INT&value>0"><input type=hidden name=InsuPeriodFlag id=InsuPeriodFlag type><input class=codeno name=InsuPeriodFlagName id=InsuPeriodFlagName ondblclick="return showCodeList('insuperiodflag', [this,InsuPeriodFlag], [1,0], null, null, null, '1', null);" onkeyup="return showCodeListKey('insuperiodflag', [this,InsuPeriodFlag], [1,0], null, null, null, '1', null);" elementtype=nacessary readonly></td>
				<td class=title id=tdPlan5 name=tdPlan5 style="display: none">��������</td>
				<td class=input id=tdPlan6 name=tdPlan6 style="display: none"><input class=codeno name=PlanType id=PlanType ondblclick="return showCodeList('quotplantype',[this, PlanTypeName],[0,1]);" onkeyup="return showCodeListKey('quotplantype',[this, PlanTypeName],[0,1]);" readonly><input class=codename name=PlanTypeName readonly elementtype=nacessary></td>
				<td class=title id=tdPlan8 name=tdPlan8 style="display: none">������ʶ</td>
				<td class=input id=tdPlan9 name=tdPlan9 style="display: none"><input class=codeno name=PlanFlag id=PlanFlag ondblclick="return showCodeList('planflag', [this,PlanFlagName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('planflag', [this,PlanFlagName], [0,1], null, null, null, '1', null);"><input class=codename name=PlanFlagName readonly elementtype=nacessary></td>
				<td class=title id=tdPlan10 name=tdPlan10 style="display: none">���Ѽ��㷽ʽ</td>
				<td class=input id=tdPlan11 name=tdPlan11 style="display: none"><input class=codeno name=PremCalType id=PremCalType ondblclick="return showCodeList('engincaltype', [this,PremCalTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('engincaltype', [this,PremCalTypeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PremCalTypeName readonly elementtype=nacessary></td>
				<td class=title id=tdPlan12 name=tdPlan12 style="display: none">��������</td>
				<td class=input id=tdPlan13 name=tdPlan13 style="display: none"><input class=common name=PlanPeople id=PlanPeople elementtype=nacessary></td>
				<td class=title id=tdPlan14 name=tdPlan14 style="display: none">��͹������(Ԫ)</td>
				<td class=input id=tdPlan15 name=tdPlan15 style="display: none"><input class=common name=EnginCost id=EnginCost elementtype=nacessary></td>
				<td class=title id=tdPlan16 name=tdPlan16 style="display: none">��͹������(ƽ����)</td>
				<td class=input id=tdPlan17 name=tdPlan17 style="display: none"><input class=common name=EnginArea id=EnginArea elementtype=nacessary></td>
				<td class=title id=tdPlan1 name=tdPlan1 style="display: ''"></td>
				<td class=input id=tdPlan2 name=tdPlan2 style="display: ''"></td>
				<td class=title id=tdPlan3 name=tdPlan3 style="display: ''"></td>
				<td class=input id=tdPlan4 name=tdPlan4 style="display: ''"></td>
			<tr>
			<tr class=common id=trPlan1 name=trPlan1 style="display: none">
				<td class=title>ְҵ����</td>
				<td class=input colspan=5><input class=radio type="radio" id=OccupTypeRadio1 name=OccupTypeRadio id=OccupTypeRadio onclick="chooseOccupFlag('1');">��һְҵ���<input class=radio type="radio" id=OccupTypeRadio2 name=OccupTypeRadio onclick="chooseOccupFlag('2');">��ְҵ���<input type=hidden name=OccupTypeFlag id=OccupTypeFlag></td>
			</tr>
			<tr class=common id=trOccupType1 name=trOccupType1 style="display: none">
				<td class=title>ְҵ���</td>
				<td class=input><input class=codeno name=OccupType id=OccupType ondblclick="return returnShowCodeList('occuptype1',[this, OccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype1',[this, OccupTypeName],[0,1]);" readonly><input class=codename name=OccupTypeName id=OccupTypeName readonly elementtype=nacessary></td>
				<td class=title>ְҵ�з���</td>
				<td class=input><input class=codeno name=OccupMidType id=OccupMidType ondblclick="return returnShowCodeList('occupmidtype',[this, OccupMidTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occupmidtype',[this, OccupMidTypeName],[0,1]);" ><input class=codename name=OccupMidTypeName id=OccupMidTypeName readonly> <span id=spanOccupMid name=spanOccupMid style="display: none;color: red">*</span></td>
				<td class=title>����</td>
				<td class=input nowrap><input class=codeno name=OccupCode id=OccupCode ondblclick="return returnShowCodeList('occupcode',[this, OccupCodeName],[0,1]);" onkeyup="return returnShowCodeListKey('occupcode',[this, OccupCodeName],[0,1]);" ><input class=codename name=OccupCodeName onkeydown="fuzzyQueryOccupCode(OccupCode,OccupCodeName)"> <span id=spanOccupCode name=spanOccupCode style="display: none;color: red">*</span><span style="color: red">(֧��ģ����ѯ)</span></td>
			</tr>
			<tr class=common id=trOccupType2 name=trOccupType2 style="display: none">
				<td class=title>���ְҵ���</td>
				<td class=input><input class=codeno name=MinOccupType id=MinOccupType ondblclick="return returnShowCodeList('occuptype2',[this, MinOccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype2',[this, MinOccupTypeName],[0,1]);" readonly><input class=codename name=MinOccupTypeName id=MinOccupTypeName readonly elementtype=nacessary></td>
				<td class=title>���ְҵ���</td>
				<td class=input><input class=codeno name=MaxOccupType id=MaxOccupType ondblclick="return returnShowCodeList('occuptype2',[this, MaxOccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype2',[this, MaxOccupTypeName],[0,1]);" readonly><input class=codename name=MaxOccupTypeName id=MaxOccupTypeName readonly elementtype=nacessary></td>
				<td class=title>ְҵ����</td>
				<td class=input><input class=common name=OccupRate id=OccupRate elementtype=nacessary></td>
			</tr>
			<tr class=common id=trPlan2 name=trPlan2 style="display: none">
				<td class=title>�������(��)</td>
				<td class=input><input class=common name=MinAge id=MinAge verify="�������(��)|INT&value>=0"></td>
				<td class=title>�������(��)</td>
				<td class=input><input class=common name=MaxAge id=MaxAge verify="�������(��)|INT&value>=0"></td>
				<td class=title>ƽ������(��)</td>
				<td class=input><input class=common name=AvgAge id=AvgAge verify="ƽ������(��)|INT&value>=0" elementtype=nacessary></td>
			</tr>
			<tr class=common id=trPlan3 name=trPlan3 style="display: none">
				<td class=title>����</td>
				<td class=input><input class=common name=NumPeople id=NumPeople verify="����|INT&value>=0"></td>
				<td class=title>�μ��籣ռ��</td>
				<td class=input><input class=common name=SocialInsuRate id=SocialInsuRate verify="�μ��籣ռ��|num&value>=0&value<=1" elementtype=nacessary></td>
				<td class=title>��Ů����</td>
				<td class=input><input class=common style="width:50px" name=MaleRate id=MaleRate verify="��Ů����|INT&value>=0" maxlength=9><b>��</b><input class=common style="width:50px" name=FemaleRate id=FemaleRate verify="��Ů����|INT&value>=0" maxlength=9><font color=red> * (�� 2:3)</font></td>
			</tr>
			<tr class=common id=trPlan4 name=trPlan4 style="display: none">
				<td class=title>����ռ��</td>
				<td class=input><input class=common name=RetireRate id=RetireRate verify="����ռ��|num&value>=0&value<=1"></td>
				<td class=title>���ѷ�̯��ʽ</td>
				<td class=input><input class=codeno name=PremMode id=PremMode ondblclick="return showCodeList('premmode', [this,PremModeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('premmode', [this,PremModeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PremModeName readonly></td>
				<td class=title>��ҵ����ռ��</td>
				<td class=input><input class=common name=EnterpriseRate id=EnterpriseRate verify="��ҵ����ռ��|num&value>=0&value<=1"></td>
			</tr>
			<tr class=common id=trPlan5 name=trPlan5 style="display: none">
				<td class=title>�����н(Ԫ)</td>
				<td class=input><input class=common name=MinSalary id=MinSalary verify="�����н(Ԫ)|num&value>=0"></td>
				<td class=title>�����н(Ԫ)</td>
				<td class=input><input class=common name=MaxSalary id=MaxSalary verify="�����н(Ԫ)|num&value>=0"></td>
				<td class=title>ƽ����н(Ԫ)</td>
				<td class=input><input class=common name=AvgSalary id=AvgSalary verify="ƽ����н(Ԫ)|num&value>=0"></td>
			</tr>
			<tr class=common id=trPlan6 name=trPlan6 style="display: none">
				<td class=title id=tdEngin1 name=tdEngin1 style="display: none">��߹������(Ԫ)</td>
				<td class=input id=tdEngin2 name=tdEngin2 style="display: none"><input class=common name=MaxEnginCost id=MaxEnginCost elementtype=nacessary></td>
				<td class=title id=tdEngin3 name=tdEngin3 style="display: none">��߹������(ƽ����)</td>
				<td class=input id=tdEngin4 name=tdEngin4 style="display: none"><input class=common name=MaxEnginArea id=MaxEnginArea elementtype=nacessary></td>
				<td class=title>��������</td>
				<td class=input><input class=codeno name=EnginType id=EnginType ondblclick="return showCodeList('engintype', [this,EnginTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('engintype', [this,EnginTypeName], [0,1], null, null, null, '1', null);"><input class=codename name=EnginTypeName readonly elementtype=nacessary></td>
				<td class=title>ʩ������</td>
				<td class=input><input class=common name=EnginDays id=EnginDays maxlength=5  verify="ʩ������|INT&value>0" elementtype=nacessary></td>
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
				<td class=title>���̸���</td>
				<td class=input colspan=5><textarea cols=80 rows=3 name=EnginDesc elementtype=nacessary></textarea></td>
			</tr>
			<tr class=common id=trEnginCondition name=trEnginCondition style="display: none">
				<td class=title>����״��˵��</td>
				<td class=input colspan=5><textarea cols=80 rows=3 name=EnginCondition elementtype=nacessary></textarea><span style="color: red">(��˵������εĵ��ʡ�ˮ��������������Ȼ�ֺ�����ʧ���)</span></td>
			</tr>
			<tr class=common>
				<td class=title>����˵��</td>
				<td class=input colspan=5><textarea class=common cols=80 rows=2 name=OtherDesc verify="����˵��|len<=1000"></textarea></td>
			</tr>
		</table>
		
		<input class=cssButton type=button name=AddPlanButton value="��  ��" onclick="addPlan();">
		<input class=cssButton type=button name=ModifyPlanButton value="��  ��" onclick="modifyPlan();">
		<input class=cssButton type=button name=DelPlanButton value="ɾ  ��" onclick="delPlan();">
	</div>
	
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo3);">
			</td>
			<td class=titleImg>ѯ�ۣ�������ϸ��Ϣά��<span style="color: red">���ڶ�����</span></td>
		</tr>
	</table>
	
	<div id="divInfo3" class=maxbox1>
		<input class=cssButton type=button name=PlanDetailButton value="������Ϣά��" onclick="planDetailOpen();">
		<input class=cssButton type=button name=ShowAllDetailButton value="������ϸһ��" onclick="showAllDetail();">
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
