<%
/***************************************************************
 * <p>ProName��LSQuotUWDetailInput.jsp</p>
 * <p>Title��һ��ѯ�ۺ˱���ϸ</p>
 * <p>Description��һ��ѯ�ۺ˱���ϸ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-31
 ****************************************************************/
%>
<%@page pageEncoding="GBK" contentType="text/html; charset=GBK"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tActivityID = request.getParameter("ActivityID");
	String tQuotNo = request.getParameter("QuotNo");
	String tQuotBatNo = request.getParameter("QuotBatNo");
	String tQuotType = request.getParameter("QuotType");
	String tCurrentDate= PubFun.getCurrentDate();
%>
<script>
	var tMissionID = "<%=tMissionID%>";
	var tSubMissionID = "<%=tSubMissionID%>";
	var tActivityID = "<%=tActivityID%>";
	var tQuotNo = "<%=tQuotNo%>";
	var tQuotBatNo = "<%=tQuotBatNo%>";
	var tQuotType = "<%=tQuotType%>";
	var tTranProdType = "";
	var tCurrentDate = "<%=tCurrentDate%>";
</script>
<html>
<head >
	<title>һ��ѯ�ۺ˱���ϸ</title>
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
	<script src="./LSQuotUWDetailInput.js"></script>
	<script src="./LSQuotCommonInput.js"></script>
	<script src="./LSQuotPubPlan.js"></script>
	<%@include file="./LSQuotUWDetailInit.jsp"%>
</head>
<body onload="initForm(); initElementtype();">
<form name=fm1 id=fm1 method=post action="" target=fraSubmit>
	<table id=table1 width=100% height=30>
		<tr>
			<td width=1%></td>
			<td class=common align=right> <input class=cssButton type=button id=RequestButton name=RequestButton value="ҵ������" onclick="showRequest();"> <input class=cssButton type=button id=FeeButton name=FeeButton value="������Ϣ" onclick="showFeeInfo();"> <input class=cssButton type=button id=PastButton name=PastButton value="������Ϣ" onclick="showPast();"> <input class=cssButton type=button id=QuesButton name=QuesButton value="�ʾ����" onclick="showQuesnaire();"> <input class=cssButton type=button id=AttachButton name=AttachButton value="��������" onclick="showAttachment();"> <input class=cssButton type=button id=SpecButton name=SpecButton value="�ر�Լ��" onclick="showGrpSpec();"> <input class=cssButton type=button value="��������" id=ShowCoinButton name=ShowCoinButton onclick="showCoinsurance();"></td>
		</tr>
	</table>
	
	<!--��֧��˾�˱�ʦ���մ���-->
	<div id="divSubFinal" style="ddisplay: none">
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
					<td class=input colspan=5><textarea cols=80 rows=3 name=SubFinalOpinion id=SubFinalOpinion verify="��֧��˾�������|len<=1500" elementtype=nacessary></textarea></td>
				</tr>
				<tr class=common>
					<td class=title>��֧��˾���ս���</td>
					<td class=input><input class=codeno name=SubFinalConclu id=SubFinalConclu readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
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
	<div id="divBranchFinal" style="ddisplay: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBranchFinalInfo);">
				</td>
				<td class=titleImg>�ֹ�˾�˱����մ���</td>
			</tr>
		</table>
		<div id="divBranchFinalInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>�ֹ�˾�������</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=BranchFinalOpinion id=BranchFinalOpinion verify="�ֹ�˾�������|len<=1500" elementtype=nacessary></textarea></td>
				</tr>
				<tr class=common>
					<td class=title>�ֹ�˾���ս���</td>
					<td class=input><input class=codeno name=BranchFinalConclu id=BranchFinalConclu readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
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
	<div id="divUWManager" style="ddisplay: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWManagerInfo);">
				</td>
				<td class=titleImg>�˱����������Ϣ</td>
			</tr>
		</table>
		<div id="divUWManagerInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>�˱��������</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=UWManagerOpinion id=UWManagerOpinion verify="�˱��������|len<=1500" elementtype=nacessary></textarea></td>
				</tr>
				<tr class=common>
					<td class=title>�˱��������</td>
					<td class=input><input class=codeno name=UWManagerConclu id=UWManagerConclu readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
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
	<div id="divOtherOpinion" style="ddisplay: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divOtherOpinionInfo);">
				</td>
				<td class=titleImg>�����������</td>
			</tr>
		</table>
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
			<table class=common>
				<tr class=common>
					<td class=title>�������</td>
					<td class=input colspan=5><input class=codeno name=OpinionType id=OpinionType readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
						ondblclick="return showCodeList('opiniontype',[this, OpinionTypeName],[0, 1],null,null,null,'1',180);" 
						onkeyup="return showCodeListKey('opiniontype',[this, OpinionTypeName],[0, 1],null,null,null,'1',180);"><input class=codename name=OpinionTypeName id=OpinionTypeName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>����·��</td>
					<td class=input colspan=5><input class=common type=file name=UploadPath id=UploadPath style="width:400px"><font color="#FF0000">����֧��txt��doc��docx��xls��xlsx��pdf��zip��rar��eml��gif ��ʽ���ļ����أ�</font></td>
				</tr>
				<tr class=common>
					<td class=title>�����������</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=OpinionDesc id=OpinionDesc></textarea></td>
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
			
			<div id="divOtherOpinionButton" style="display: ''">
				<input class=cssButton type=button name=OtherOpinionAddButton id=OtherOpinionAddButton value="���������������" onclick="OtherOpinionAdd();">
				<input class=cssButton type=button name=OtherOpinionModifyButton id=OtherOpinionModifyButton value="�޸������������" onclick="OtherOpinionModify();">
				<input class=cssButton type=button name=OtherOpinionDeleteButton id=OtherOpinionDeleteButton value="ɾ�������������" onclick="OtherOpinionDelete();">
			</div>
		</div>
	</div>
	
	<input type=hidden name=OtherOpinionSerialNo id=OtherOpinionSerialNo>
</form>
	
<form name=fm3 id=fm3 method=post action="" target=fraSubmit>
	<!--�ܹ�˾�˱�-->
	<div id="divUW" style="ddisplay: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWInfo);">
				</td>
				<td class=titleImg>�ܹ�˾�˱���Ϣ</td>
			</tr>
		</table>
		<div id="divUWInfo"  class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>�ٱ�����</td>
					<td class=input><input class=codeno name=ReinsArrange id=ReinsArrange readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center"
						ondblclick="showCodeList('reinsarrange', [this, ReinsArrangeName],[0,1],null,null,null,1);" 
						onkeyup="showCodeListKey('reinsarrange', [this, ReinsArrangeName],[0,1],null,null,null,1);"><input class=codename name=ReinsArrangeName id=ReinsArrangeName></td>
					<td class=title id=tdFacul1 name=tdFacul1 style="ddisplay: none">�ٷ�ԭ��</td>
					<td class=input id=tdFacul2 name=tdFacul2 style="ddisplay: none"><input class=codeno name=FaculReason id=FaculReason style="background:url(../common/images/select--bg_03.png) no-repeat right center"  readonly ondblclick="showCodeList('faculreason', [this, FaculReasonName],[0,1],null,null,null,1);" onkeyup="showCodeListKey('faculreason', [this, FaculReasonName],[0,1],null,null,null,1);"><input class=codename name=FaculReasonName id=FaculReasonName></td>
					<td class=title id=tdFacul3 name=tdFacul3 style="ddisplay: none"></td>
					<td class=input id=tdFacul4 name=tdFacul4 style="ddisplay: none"></td>
					<td class=title id=tdFacul5 name=tdFacul5 style="display: ''"></td>
					<td class=input id=tdFacul6 name=tdFacul6 style="display: ''"></td>
					<td class=title id=tdFacul7 name=tdFacul7 style="display: ''"></td>
					<td class=input id=tdFacul8 name=tdFacul8 style="display: ''"></td>
				</tr>
				<tr class=common id=tFaculOtherDesc name=tFaculOtherDesc style="ddisplay: none">
					<td class=title>�ٷ���������</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=FaculOtherDesc id=FaculOtherDesc verify="�ٷ���������|len<=1500" ></textarea><font color=red> *</font></td>
				</tr>
				<tr class=common>
					<td class=title>�ܹ�˾�ۺ����</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=UWOpinion id=UWOpinion verify="�ܹ�˾�ۺ����|len<=1500" elementtype=nacessary></textarea></td>
				</tr>
			</table>
			
			<div id="divUWOpinionButton" style="display: ''">
				<input class=cssButton type=button name=UWOpinionSaveButton id=UWOpinionSaveButton value="�ۺ��������" onclick="UWOpinionSave();">
			</div>
			
			<table class=common>
				<tr class=common>
					<td class=title>�ܹ�˾�˱�����</td>
					<td class=input><input class=codeno name=UWConclu id=UWConclu readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
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
			
			<div id="divUWSubmitButton" style="display: ''">
				<input class=cssButton type=button name=UWSubmitButton id=UWSubmitButton value="�˱��ύ" onclick="UWSubmit();">
				<input class=cssButton type=button name=UWReturnButton id=UWReturnButton value="��Ŀ¼" onclick="ReturnList();">
				<input class=cssButton type=button id=UWQuestionButton name=UWQuestionButton style="ddisplay: none" value="�������ѯ" onclick="goToQuestion();">
			</div>
		</div>
		<br/>
	</div>
	
	<!--�˱�����-->
	<div id="divUWRule" style="ddisplay: none">
		<table>
			<tr>
				<td class=common id=tdUWRule1 name=tdUWRule1>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWRuleInfo);">
				</td>
				<td class=titleImg id=tdUWRule2 name=tdUWRule2>�˱�����</td>
			</tr>
		</table>
		<div id="divUWRuleInfo" class=maxbox1 style="display: ''">
			<input class=cssButton type=button value="�˱�/��ȫ����" onclick="showUWRule();">
			<!--<input class=cssButton type=button value="�������ο���" onclick="showCMRule();">-->
			<input class=cssButton type=button value="�����������" onclick="showPlanCombi();">
			<!--<input class=cssButton type=button value="������չ" onclick="showExpand();">-->
		</div>
	</div>
	
	<!--�˱�Ҫ��-->
	<div id="divUWMainPoint" style="ddisplay: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divUWMainPointInfo);">
				</td>
				<td class=titleImg>�˱�Ҫ��</td>
			</tr>
		</table>
		<div id="divUWMainPointInfo" style="display: ''">
			<table class=common>
				<tr class=common>
					<td text-align: left colSpan=1>
						<span id="spanUWMainPointGrid"></span>
					</td>
				</tr>
			</table>
			<div id="divUWMainPointDetail" style="ddisplay: none">
				<table class=common>
					<tr class=common id="divSubUWAnaly" style="ddisplay: none">
						<td class=title>��֧��˾Ҫ�����</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=SubUWAnaly id=SubUWAnaly verify="��֧��˾Ҫ�����|len<=1500" elementtype=nacessary></textarea></td>
					</tr>
					<tr class=common id="divBranchUWAnaly" style="ddisplay: none">
						<td class=title>�ֹ�˾Ҫ�����</td>
						<td class=input colspan=5><textarea cols=80 rows=3 name=BranchUWAnaly id=BranchUWAnaly verify="�ֹ�˾Ҫ�����|len<=1500" elementtype=nacessary></textarea></td>
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
			
				<input class=cssButton type=button value="Ҫ���������" name="UWAnalySaveButton" id=UWAnalySaveButton onclick="analySaveClick();"> <input class=cssButton type=button value="Ҫ������ر�" name="UWAnalyCloseButton" id=UWAnalyCloseButton onclick="analyCloseClick();">
			</div>
		</div>
	</div>
	
	<!--�ֹ�˾�˱�-->
	<div id="divBranchUW" style="ddisplay: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divBranchUWInfo);">
				</td>
				<td class=titleImg>�ֹ�˾�˱���Ϣ</td>
			</tr>
		</table>
		<div id="divBranchUWInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>�ֹ�˾�ۺ����</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=BranchUWOpinion id=BranchUWOpinion verify="�ֹ�˾�ۺ����|len<=1500" elementtype=nacessary></textarea></td>
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
			
			<div id="divBranchUWOpinionButton" style="display: ''">
				<input class=cssButton type=button name=BranchUWOpinionSaveButton id=BranchUWOpinionSaveButton value="�ۺ��������" onclick="UWOpinionSave();">
			</div>
			
			<table class=common>
				<tr class=common>
					<td class=title>�ֹ�˾�˱�����</td>
					<td class=input><input class=codeno name=BranchUWConclu id=BranchUWConclu readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
						ondblclick="showCodeList('qbranchuwconclu', [this, BranchUWConcluName],[0,1],null,null,null,1);" 
						onkeyup="showCodeListKey('qbranchuwconclu', [this, BranchUWConcluName],[0,1],null,null,null,1);"><input class=codename name=BranchUWConcluName id=BranchUWConcluName></td>
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
			
			<div id="divBranchUWSubmitButton" style="display: ''">
				<input class=cssButton type=button name=BranchUWSubmitButton id=BranchUWSubmitButton value="�˱��ύ" onclick="UWSubmit();">
				<input class=cssButton type=button name=BranchUWReturnButton id=BranchUWReturnButton value="��Ŀ¼" onclick="ReturnList();">
				<input class=cssButton type=button id=BranchUWQuestionButton name=BranchUWQuestionButton style="ddisplay: none" value="�������ѯ" onclick="goToQuestion();">
			</div>
		</div>
	</div>
	
	<!--��֧��˾�˱�-->
	<div id="divSubUW" style="ddisplay: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divSubUWInfo);">
				</td>
				<td class=titleImg>��֧��˾�˱���Ϣ</td>
			</tr>
		</table>
		<div id="divSubUWInfo" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>��֧��˾�ۺ����</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=SubUWOpinion id=SubUWOpinion verify="��֧��˾�ۺ����|len<=1500" elementtype=nacessary></textarea></td>
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
			
			<div id="divSubUWOpinionButton" style="display: ''">
				<input class=cssButton type=button name=SubUWOpinionSaveButton id=SubUWOpinionSaveButton value="�ۺ��������" onclick="UWOpinionSave();">
			</div>
			
			<table class=common>
				<tr class=common>
					<td class=title>��֧��˾�˱�����</td>
					<td class=input><input class=codeno name=SubUWConclu id=SubUWConclu readonly style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
						ondblclick="showCodeList('qsubuwconclu', [this, SubUWConcluName],[0,1],null,null,null,1);" 
						onkeyup="showCodeListKey('qsubuwconclu', [this, SubUWConcluName],[0,1],null,null,null,1);"><input class=codename name=SubUWConcluName id=SubUWConcluName></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			
			<div id="divSubUWSubmitButton" style="display: ''">
				<input class=cssButton type=button name=SubUWSubmitButton id=SubUWSubmitButton value="�˱��ύ" onclick="UWSubmit();">
				<input class=cssButton type=button name=SubUWReturnButton id=SubUWReturnButton value="��Ŀ¼" onclick="ReturnList();">
				<input class=cssButton type=button id=SubUWQuestionButton name=SubUWQuestionButton style="ddisplay: none" value="�������ѯ" onclick="goToQuestion();">
			</div>
		</div>
	</div>
	
	<!--ѯ�ۻ�����Ϣ-->
	<div id="divQuotBasic" style="display: ''">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divQuotBasicDetail);">
				</td>
				<td class=titleImg>ѯ�ۻ�����Ϣ</td>
			</tr>
		</table>
		<div id="divQuotBasicDetail" class=maxbox1 style="display: ''">
			<table class=common>
				<tr class=common>
					<td class=title>ѯ�ۺ�</td>
					<td class=input><input class="wid readonly" name=QuotNo id=QuotNo readonly></td>
					<td class=title>���κ�</td>
					<td class=input><input class="wid readonly" name=QuotBatNo id=QuotBatNo readonly></td>
					<td class=title>׼�ͻ�����</td>
					<td class=input><input class="wid readonly" name=PreCustomerName id=PreCustomerName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>֤������</td>
					<td class=input><input class="wid readonly" name=IDTypeName id=IDTypeName readonly></td>
					<td class=title>֤������</td>
					<td class=input><input class="wid readonly" name=IDNo id=IDNo readonly></td>
					<td class=title>��λ����</td>
					<td class=input><input class="wid readonly" name=GrpNatureName id=GrpNatureName readonly></td>
				</tr>
				<tr class=common>
					<td class=title>��ҵ����</td>
					<td class=input><input class="wid readonly" name=BusiCategoryName id=BusiCategoryName readonly></td>
					<td class=title>��ַ</td>
					<td class=input colspan=3><input class="wid readonly" name=Address id=Address readonly></td>
				</tr>
				<tr class=common>
					<td class=title>��Ʒ����</td>
					<td class=input><input class="wid readonly" name=ProdTypeName id=ProdTypeName readonly></td>
					<td class=title></td>
					<td class=input></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input><input class="wid readonly" name=SaleChannelName id=SaleChannelName readonly></td>
					<td class=title>���ѷ�̯��ʽ</td>
					<td class=input><input class="wid readonly" name=PremModeName id=PremModeName readonly></td>
					<td class=title>Ԥ�Ʊ��ѹ�ģ(Ԫ)</td>
					<td class=input><input class="wid readonly" name=PrePrem id=PrePrem readonly></td>
				</tr>
				<tr class=common>
					<td class=title>�Ƿ�Ϊ����ҵ��</td>
					<td class=input><input class="wid readonly" name=RenewFlagName id=RenewFlagName readonly></td>
					<td class=title>�Ƿ�Ϊͳ����</td>
					<td class=input><input class="wid readonly" name=BlanketFlagName id=BlanketFlagName readonly></td>
					<td class=title id=tdElasticFlag1 name=tdElasticFlag1 style="ddisplay: none">�Ƿ�Ϊ���Լƻ�</td>
					<td class=input id=tdElasticFlag2 name=tdElasticFlag2 style="ddisplay: none"><input class="wid readonly" name=ElasticFlag id=ElasticFlag type=hidden><input class="wid readonly" name=ElasticFlagName id=ElasticFlagName readonly></td>
					<td class=title id=tdElasticFlag3 name=tdElasticFlag3></td>
					<td class=input id=tdElasticFlag4 name=tdElasticFlag4></td>
				</tr>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input><input class="wid readonly" name=InsuPeriod id=InsuPeriod readonly></td>
					<td class=title>��Լ��Ч������</td>
					<td class=input><input class="wid readonly" name=ValDateTypeName id=ValDateTypeName readonly></td>
					<td class=title id=tTD1 style="ddisplay: none"></td>
					<td class=input id=tTD2 style="ddisplay: none"></td>
					<td class=title id=ValDateTitle style="display: ''">��Ч����</td>
					<td class=input id=ValDateInput style="display: ''"><input class="wid readonly" name=AppointValDate id=AppointValDate readonly></td>
				</tr>
				<tr class=common>
					<td class=title>�ɷѷ�ʽ</td>
					<td class=input><input class="wid readonly" name=PayIntvName id=PayIntvName></td>
					<td class=title>�Ƿ񹲱�</td>
					<td class=input><input class="wid readonly" type=hidden name=Coinsurance id=Coinsurance><input class="wid readonly" name=CoinsuranceName id=CoinsuranceName readonly></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title colspan=6>
						<div id="divPlanDiv" style="display: ''">
						</div>
					</td>
				</tr>
				<tr class=common>
					<td class=title>��˾���</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=CustomerIntro id=CustomerIntro></textarea></td>
				</tr>
				<tr class=common>
					<td class=title colspan=6>
						<div id="divAgencyInfo" style="ddisplay: none">
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
					<td class=title colspan=6><input class=checkbox type=checkbox disabled name=RelaCustomerFlag id=RelaCustomerFlag>�Ƿ��������׼�ͻ�</td>
				</tr>
				<tr class=common>
					<td class=title colspan=6>
						<div id="divRelaCustInfo" style="ddisplay: none">
							<table class=common>
								<tr class=common>
									<td text-align: left colSpan=1>
										<span id="spanRelaCustListGrid"></span>
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
	<input type=hidden name=UWMainPointRiskCode id=UWMainPointRiskCode>
	<input type=hidden name=UWMainPointRuleCode id=UWMainPointRuleCode>
</form>
	
<!--ѯ�۹�����Ϣ-->
<form name=fmEngin id=fmEngin method=post action="" target=fraSubmit>
	<div id=divEngin name=divEngin style="ddisplay: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divEnginInfo);">
				</td>
				<td class=titleImg>������Ϣ</td>
			</tr>
		</table>
		<div id="divEnginInfo" class=maxbox1>
			<table class=common>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input colspan=5><input class="wid common" name=EnginName id=EnginName style="width: 500px" elementtype=nacessary></td>
				</tr>
				<tr class=common>
					<td class=title>��������</td>
					<td class=input><input class=codeno name=EnginType style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('engintype', [this,EnginTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('engintype', [this,EnginTypeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=EnginTypeName id=EnginTypeName readonly elementtype=nacessary></td>
					<td class=title>�������(ƽ����)</td>
					<td class=input><input class="wid common" name=EnginArea id=EnginArea></td>
					<td class=title>�������(Ԫ)</td>
					<td class=input><input class="wid common" name=EnginCost id=EnginCost></td>
				</tr>
			</table>
			<div id=divEnginFactor name=divEnginFactor>
			</div>
			<table class=common>
				<tr class=common>
					<td class=title>���̵ص�</td>
					<td class=input><input class="wid common" name=EnginPlace id=EnginPlace elementtype=nacessary></td>
					<td class=title>��������</td>
					<td class=input><input class="coolDatePicker" dateFormat="short" name=EnginStartDate id=EnginStartDate verify="��������|date"></td>
					<td class=title>����ֹ��</td>
					<td class=input><input class="coolDatePicker" dateFormat="short" name=EnginEndDate id=EnginEndDate verify="����ֹ��|date"></td>
				</tr>
				<tr class=common>
					<td class=title>���̸���</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=EnginDesc id=EnginDesc elementtype=nacessary></textarea></td>
				</tr>
				<tr class=common id=trEnginCondition name=trEnginCondition style="ddisplay: none">
					<td class=title>����״��˵��</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=EnginCondition id=EnginCondition elementtype=nacessary></textarea><span style="color: red">(��˵������εĵ��ʡ�ˮ��������������Ȼ�ֺ�����ʧ���)</span></td>
				</tr>
				<tr class=common>
					<td class=title>��ע</td>
					<td class=input colspan=5><textarea cols=80 rows=3 name=Remark id=Remark></textarea></td>
				</tr>
				<tr class=common>
					<td class=title>�а�������</td>
					<td class=input><input class="wid common" name=InsurerName id=InsurerName></td>
					<td class=title>�а�������</td>
					<td class=input><input class=codeno name=InsurerType id=InsurerType style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('insurertype', [this,InsurerTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListkey('insurertype', [this,InsurerTypeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=InsurerTypeName id=InsurerTypeName readonly></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
				<tr class=common>
					<td class=title>ʩ��������</td>
					<td class=input><input class="wid common" name=ContractorName id=ContractorName elementtype=nacessary></td>
					<td class=title>ʩ��������</td>
					<td class=input><input class=codeno name=ContractorType id=ContractorType style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('contractortype', [this,ContractorTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('contractortype', [this,ContractorTypeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=ContractorTypeName id=ContractorTypeName readonly elementtype=nacessary></td>
					<td class=title></td>
					<td class=input></td>
				</tr>
			</table>
			<div>
				<input class=cssButton type=button name=EnginSaveButton id=EnginSaveButton value="��  ��" onclick="saveEngin();">
			</div>
			<br/>
		</div>
	</div>
</form>

<!--ѯ�۷�����Ϣ-->
<form name=fmPlan id=v method=post action="" target=fraSubmit>
	<table>
		<tr>
			<td class=common>
				<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo1);">
			</td>
			<td class=titleImg>ѯ�ۣ�������Ϣά��<span style="color: red">����һ����</span></td>
		</tr>
	</table>
	<div id="divInfo1" name="divInfo1" style="display: ''">
		<table class=common>
			<tr class=common>
				<td text-align: left colSpan=1>
					<span id="spanPlanInfoGrid" ></span>
				</td>
			</tr>
		</table>
		<center>		
			<input class=cssButton90 type=button value="��  ҳ" onclick="turnPage5.firstPage();">
			<input class=cssButton91 type=button value="��һҳ" onclick="turnPage5.previousPage();">
			<input class=cssButton92 type=button value="��һҳ" onclick="turnPage5.nextPage();">
			<input class=cssButton93 type=button value="β  ҳ" onclick="turnPage5.lastPage();">
		</center>
	</div>
	
	<div id="divInfo2" style="display: ''">
		<table class=common>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class="wid readonly" name=PlanCode id=PlanCode readonly></td>
				<td class=title style="ddisplay: none">ϵͳ��������</td>
				<td class=input style="ddisplay: none"><input class="wid readonly" name=SysPlanCode id=SysPlanCode readonly></td>
				<td class=title></td>
				<td class=input></td>
				<td class=title></td>
				<td class=input></td>
			</tr>
			<tr class=common>
				<td class=title>��������</td>
				<td class=input><input class="wid readonly" name=PlanDesc id=PlanDesc verify="��������|len<15" elementtype=nacessary></td>
				<td class=title id=tdPlan5 name=tdPlan5 style="ddisplay: none">��������</td>
				<td class=input id=tdPlan6 name=tdPlan6 style="ddisplay: none"><input class=codeno name=PlanType id=PlanType style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return returnShowCodeList('plantype',[this, PlanTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('plantype',[this, PlanTypeName],[0,1]);" readonly><input class=codename name=PlanTypeName id=PlanTypeName readonly elementtype=nacessary></td>
				<td class=title id=tdPlan8 name=tdPlan8 style="ddisplay: none">������ʶ</td>
				<td class=input id=tdPlan9 name=tdPlan9 style="ddisplay: none"><input class=codeno name=PlanFlag id=PlanFlag style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return showCodeList('planflag', [this,PlanFlagName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('planflag', [this,PlanFlagName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PlanFlagName id=PlanFlagName readonly readonly elementtype=nacessary></td>
				<td class=title id=tdPlan10 name=tdPlan10 style="ddisplay: none">���Ѽ��㷽ʽ</td>
				<td class=input id=tdPlan11 name=tdPlan11 style="ddisplay: none"><input class=codeno name=PremCalType id=PremCalType style="background:url(../common/images/select--bg_03.png) no-repeat right center"   ondblclick="return showCodeList('engincaltype', [this,PremCalTypeName], [0,1], null, null, null, '1', null);" onkeyup="return showCodeListKey('engincaltype', [this,PremCalTypeName], [0,1], null, null, null, '1', null);" readonly><input class=codename name=PremCalTypeName id=PremCalTypeName readonly elementtype=nacessary></td>
				<td class=title id=tdPlan12 name=tdPlan12 style="ddisplay: none">��������</td>
				<td class=input id=tdPlan13 name=tdPlan13 style="ddisplay: none"><input class="wid readonly" name=PlanPeople id=PlanPeople verify="��������|INT&value>0" elementtype=nacessary></td>
				<td class=title id=tdPlan1 name=tdPlan1 style="display: ''"></td>
				<td class=input id=tdPlan2 name=tdPlan2 style="display: ''"></td>
				<td class=title id=tdPlan3 name=tdPlan3 style="display: ''"></td>
				<td class=input id=tdPlan4 name=tdPlan4 style="display: ''"></td>
			<tr>
			<tr class=common id=trPlan1 name=trPlan1 style="ddisplay: none">
				<td class=title>ְҵ����<span style="color: red">*</span></td>
				<td class=input colspan=5><input class=radio type="radio" id=OccupTypeRadio1 name=OccupTypeRadio1 onclick="chooseOccupFlag('1');">��һְҵ���<input class=radio type="radio" id=OccupTypeRadio2 name=OccupTypeRadio onclick="chooseOccupFlag('2');">��ְҵ���<input type=hidden name=OccupTypeFlag id=OccupTypeFlag></td>
			</tr>
			<tr class=common id=trOccupType1 name=trOccupType1 style="ddisplay: none">
				<td class=title>ְҵ���</td>
				<td class=input><input class=codeno name=OccupType id=OccupType style="background:url(../common/images/select--bg_03.png) no-repeat right center"  ondblclick="return returnShowCodeList('occuptype1',[this, OccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype1',[this, OccupTypeName],[0,1]);" readonly><input class=codename name=OccupTypeName id=OccupTypeName readonly elementtype=nacessary></td>
				<td class=title>ְҵ�з���</td>
				<td class=input><input class=codeno name=OccupMidType id=OccupMidType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('occupmidtype',[this, OccupMidTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occupmidtype',[this,OccupMidTypeName],[0,1]);" readonly><input class=codename name=OccupMidTypeName id=OccupMidTypeName readonly><span id=spanOccupMid name=spanOccupMid style="ddisplay: none;color: red">*</span></td>
				<td class=title>����</td>
 				<td class=input nowrap><input class=codeno name=OccupCode id=OccupCode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('occupcode',[this, OccupCodeName],[0,1]);" onkeyup="return returnShowCodeListKey('occupcode',[this,OccupCodeName],[0,1]);" readonly><input class=codename name=OccupCodeName id=OccupCodeName onkeydown="fuzzyQueryOccupCode(OccupCode,OccupCodeName)"><span id=spanOccupCode name=spanOccupCode id=spanOccupCode style="ddisplay: none;color: red">*</span><span style="color: red">(֧��ģ����ѯ)</span></td>
			</tr>
			<tr class=common id=trOccupType2 name=trOccupType2 style="ddisplay: none">
				<td class=title>���ְҵ���</td>
				<td class=input><input class=codeno name=MinOccupType id=MinOccupType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('occuptype2',[this, MinOccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype2',[this, MinOccupTypeName],[0,1]);" readonly><input class=codename name=MinOccupTypeName id=MinOccupTypeName readonly elementtype=nacessary></td>
				<td class=title>���ְҵ���</td>
				<td class=input><input class=codeno name=MaxOccupType id=MaxOccupType style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('occuptype2',[this, MaxOccupTypeName],[0,1]);" onkeyup="return returnShowCodeListKey('occuptype2',[this, MaxOccupTypeName],[0,1]);" readonly><input class=codename name=MaxOccupTypeName id=MaxOccupTypeName readonly elementtype=nacessary></td>
				<td class=title>ְҵ����</td>
				<td class=input><input class="wid readonly" name=OccupRate id=OccupRate elementtype=nacessary></td>
			</tr>
			<tr class=common id=trPlan2 name=trPlan2 style="ddisplay: none">
				<td class=title>�������(��)</td>
				<td class=input><input class="wid readonly" name=MinAge id=MinAge verify="�������(��)|INT&value>=0"></td>
				<td class=title>�������(��)</td>
				<td class=input><input class="wid readonly" name=MaxAge id=MaxAge  verify="�������(��)|INT&value>=0"></td>
				<td class=title>ƽ������(��)</td>
				<td class=input><input class="wid readonly" name=AvgAge id=AvgAge verify="ƽ������(��)|INT&value>=0" elementtype=nacessary></td>
			</tr>
			<tr class=common id=trPlan3 name=trPlan3 style="ddisplay: none">
				<td class=title>����</td>
				<td class=input><input class="wid readonly" name=NumPeople id=NumPeople verify="����|INT&value>0" elementtype=nacessary></td>
				<td class=title>�μ��籣ռ��</td>
				<td class=input><input class="wid readonly" name=SocialInsuRate id=SocialInsuRate verify="�μ��籣ռ��|num&value>=0&value<=1"  elementtype=nacessary></td>
				<td class=title>��Ů����</td>
				<td class=input><input class="wid readonly" style="width:50px" name=MaleRate id=MaleRate verify="��Ů����|INT&value>=0" maxlength=9><b>��</b><input class="wid common" style="width:50px" name=FemaleRate id=FemaleRate verify="��Ů����|INT&value>=0" maxlength=9  elementtype=nacessary><font color=red> (�� 2:3)</font></td>
			</tr>
			<tr class=common id=trPlan4 name=trPlan4 style="ddisplay: none">
				<td class=title>����ռ��</td>
				<td class=input><input class="wid readonly" name=RetireRate  id=RetireRate verify="����ռ��|num&value>=0&value<=1"  elementtype=nacessary></td>
				<td class=title>���ѷ�̯��ʽ</td>
				<td class=input><input class=codeno name=PremMode id=PremMode style="background:url(../common/images/select--bg_03.png) no-repeat right center" ondblclick="return returnShowCodeList('premmode', [this,PremModeName], [0,1]);" onkeyup="return returnShowCodeListKey('premmode', [this,PremModeName], [0,1]);" readonly><input class=codename name=PremModeName id=PremModeName readonly  elementtype=nacessary></td>
				<td class=title>��ҵ����ռ��</td>
				<td class=input><input class="wid readonly" name=EnterpriseRate id=EnterpriseRate verify="��ҵ����ռ��|num&value>=0&value<=1"  elementtype=nacessary></td>
			</tr>
			<tr class=common id=trPlan5 name=trPlan5 style="ddisplay: none">
				<td class=title>�����н(Ԫ)</td>
				<td class=input><input class="wid readonly" name=MinSalary id=MinSalary verify="�����н(Ԫ)|num&value>=0"></td>
				<td class=title>�����н(Ԫ)</td>
				<td class=input><input class="wid readonly" name=MaxSalary id=MaxSalary verify="�����н(Ԫ)|num&value>=0"></td>
				<td class=title>ƽ����н(Ԫ)</td>
				<td class=input><input class="wid readonly" name=AvgSalary id=AvgSalary verify="ƽ����н(Ԫ)|num&value>=0"></td>
			</tr>
			<tr class=common>
				<td class=title>����˵��</td>
				<td class=input colspan=11><textarea class=readonly cols=70 rows=2 name=OtherDesc id=OtherDesc verify="����˵��|len<=1000"></textarea></td>
			</tr>
		</table>
		<input class=cssButton type=button name=AddPlanButton id=AddPlanButton value="��  ��" onclick="addPlan();">
		<input class=cssButton type=button name=ModifyPlanButton id=ModifyPlanButton value="��  ��" onclick="modifyPlan();">
		<input class=cssButton type=button name=DelPlanButton id=DelPlanButton value="ɾ  ��" onclick="delPlan();">
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
		<input class=cssButton type=button name=PlanDetailButton id=PlanDetailButton value="������Ϣά��" onclick="planDetailOpen();">
		<input class=cssButton type=button name=ShowAllDetailButton id=ShowAllDetailButton value="������ϸһ��" onclick="showAllDetail();">
	</div>
	<div id=divInfo5 name=divInfo5 style="ddisplay: none">
		<table>
			<tr>
				<td class=common>
					<img src="../common/images/butExpand.gif" style="cursor:hand;" onclick="showPage(this, divInfo4);">
				</td>
				<td class=titleImg>ѯ�ۣ�������Ϣά��<span style="color: red">����������</span></td>
			</tr>
		</table>
		<div id="divInfo4" class= maxbox1 style="display: ''">
			<input class=cssButton type=button id=productButton name=productButton value="��Ʒ������Ϣά��" onclick="showProdParam();">
		</div>
	</div>
</form>
	
<form name=fmPub  id=fmPub method=post action="" target=fraSubmit>
	<div style="ddisplay: none">
		<input type=hidden name=Operate id=Operate>
		<input type=hidden name=HiddenCodeType id=HiddenCodeType>
	</div>
</form>
<span id="spanCode" style="ddisplay: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
