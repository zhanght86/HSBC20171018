<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//程序名称：GrpQuestInputInit.jsp
	//程序功能：团体问题件录入
	//创建日期：2002-06-19 11:10:36
	//创建人  ：WHN
	//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="com.sinosoft.lis.cbcheck.*"%>
<%
	String tProposalNo = "";
	String tFlag = "";
	String tGrpProposalNo = "";

	tGrpProposalNo = request.getParameter("GrpProposalNo");
	tProposalNo = request.getParameter("ProposalNo");
	tFlag = request.getParameter("Flag");

	loggerDebug("GrpQuestInputInit", "GrpPolNo:" + tGrpProposalNo);
	loggerDebug("GrpQuestInputInit", "ProposalNo:" + tProposalNo);
	loggerDebug("GrpQuestInputInit", "Flag:" + tFlag);
%>

<script language="JavaScript">
	// 输入框的初始化（单记录部分）
	function initInpBox() {
		try {
			document.all('BackObj').value = '';
			document.all('Content').value = '';
			document.all('Quest').value = '';
		} catch (ex) {
			alert("在UWManuDateInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
		}
	}

	// 下拉框的初始化
	function initSelBox() {
		try {
		} catch (ex) {
			alert("在UWSubInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
		}
	}

	function initForm(tGrpPolNo, tProposalNo, tFlag) {
		try {

			initInpBox();

			initHide(tGrpPolNo, tProposalNo, tFlag);

			QuestQuery(tProposalNo, tFlag);

			initCodeDate(tFlag);

		} catch (re) {
			alert("UWSubInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
		}
	}

	// 责任信息列表的初始化
	function initQuestGrid() {
		var iArray = new Array();

		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "30px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[1] = new Array();
			iArray[1][0] = "问题代码"; //列名
			iArray[1][1] = "120px"; //列宽
			iArray[1][2] = 100; //列最大值
			iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[2] = new Array();
			iArray[2][0] = "问题名称"; //列名
			iArray[2][1] = "120px"; //列宽
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[3] = new Array();
			iArray[3][0] = "问题内容"; //列名
			iArray[3][1] = "300px"; //列宽
			iArray[3][2] = 60; //列最大值
			iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许

			QuestGrid = new MulLineEnter("fm", "QuestGrid");
			//这些属性必须在loadMulLine前                            
			QuestGrid.mulLineCount = 1;
			QuestGrid.displayTitle = 1;
			QuestGrid.canChk = 1;
			QuestGrid.loadMulLine(iArray);

		}

		catch (ex) {
			alert(ex);
		}
	}

	function initHide(tGrpPolNo, tProposalNo, tFlag) {
		document.all('ProposalNoHide').value = tProposalNo;
		document.all('GrpProposalNoHide').value = tGrpPolNo;
		document.all('Flag').value = tFlag;
	}

	function initCodeDate(tFlag) {
		if (tFlag == "0") {
			document.all('BackObj').CodeData = "0|^2|业务员^3|保户^4|机构";
			//fm.all('BackObj').value = "1";
		}
		if (tFlag == "5") {
			document.all('BackObj').CodeData = "0|^1|操作员^2|业务员^3|保户^4|机构";
			//fm.all('BackObj').value = "1";
		}
		if (tFlag == "1") {
			document.all('BackObj').CodeData = "0|^1|操作员^2|业务员^3|保户^4|机构";
		}
	}
</script>


