/***************************************************************
 * <p>ProName��LSQuotQueryInput.js</p>
 * <p>Title��ѯ�۲�ѯ</p>
 * <p>Description��ѯ�۲�ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-09-17
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();
var mOperate = "";//����״̬

/**
 * �ύ����
 */
function submitForm(obj) {
	
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		
		if (mOperate == "QUERY_PRINT") {
			
			var tFileName = content.substring(content.lastIndexOf('/')+1,content.length); 
			var tFilePath = content;
			
			window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
		} else {
			
			var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
			//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
		}
	}
}

/**
 * ��ѯѯ����Ϣ
 */
function queryClick() {
	
	document.all("enterQuest").style.display = "none";
	document.all("UWDesc").style.display = "none";
	document.all("printButton").style.display = "none";
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQuerySql");
	tSQLInfo.setSqlId("LSQuotQuerySql1");
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.QuotNo.value);
	tSQLInfo.addSubPara(fm.QuotType.value);
	tSQLInfo.addSubPara(fm.QuotState.value);
	tSQLInfo.addSubPara(fm.Manager.value);
	tSQLInfo.addSubPara(fm.ApplyCom.value);
	tSQLInfo.addSubPara(fm.ApplyStartDate.value);
	tSQLInfo.addSubPara(fm.ApplyEndDate.value);
	tSQLInfo.addSubPara(fm.UWStartDate.value);
	tSQLInfo.addSubPara(fm.UWEndDate.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tManageCom);
	
	initQuotGrid();
	initOfferGrid();
	//turnPage1.pageLineNum = 5;
	turnPage1.queryModal(tSQLInfo.getString(), QuotGrid, 2, 1);
	
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ������Ϣ
 */
function clickOfferInfo() {
	
	var tRow = QuotGrid.getSelNo();
	var tQuotNoTemp = QuotGrid.getRowColData(tRow-1,1);
	var tQuotBatNoTemp = QuotGrid.getRowColData(tRow-1,2);
	var tActivityID = QuotGrid.getRowColData(tRow-1,19);
	
	//�����������ť
	showQuestionButton(tQuotNoTemp,tQuotBatNoTemp,tActivityID);
	
	//�����ܹ�˾�������
	showUWDescButton(tQuotNoTemp,tQuotBatNoTemp,tActivityID);
	
	//����ѯ�۵���ӡ��ťչʾ
	var tQuotState = QuotGrid.getRowColData(tRow-1,8);
	if (tQuotState =="02" || tQuotState =="04" || tQuotState =="05") {
		document.all("printButton").style.display = "";
	} else {
		document.all("printButton").style.display = "none";
	}
	
	//�����ٱ��ٷַ������ð�ť
	//showFaculButton(tQuotNoTemp,tQuotBatNoTemp);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQuerySql");
	tSQLInfo.setSqlId("LSQuotQuerySql2");
	tSQLInfo.addSubPara(tQuotNoTemp);
	tSQLInfo.addSubPara(tQuotBatNoTemp);
	
	initOfferGrid();
	turnPage2.queryModal(tSQLInfo.getString(), OfferGrid, 2, 1);
}

/**
 * ��ϸ�鿴
 */
function showQuotInfo() {
	
	var tRow = QuotGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ��ѯ����Ϣ��");
		return false;	
	}
	tQuotNo = QuotGrid.getRowColData(tRow-1,1);
	tQuotBatNo = QuotGrid.getRowColData(tRow-1,2);
	tQuotType = QuotGrid.getRowColData(tRow-1,3);
	
	showPalnDetailView();
}

/**
 * ������Ϣ�鿴
 */
function showOfferInfo() {
	
	var tRow = OfferGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ��������Ϣ��");
		return false;	
	}

	var tOfferListNo = OfferGrid.getRowColData(tRow-1,1);
	var tQuotNo = OfferGrid.getRowColData(tRow-1,2);
	var tQuotBatNo = OfferGrid.getRowColData(tRow-1,3);
	var tQuotType = OfferGrid.getRowColData(tRow-1,4);

	var tSrc = "";
	if (tQuotType == "00") {
		tSrc = "./LSQuotETSeeInput.jsp";
	} else if (tQuotType == "01") {
		tSrc = "./LSQuotProjSeeInput.jsp";
	}

	tSrc += "?QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&OfferListNo="+ tOfferListNo+"&QuotType="+tQuotType+"&QuotQuery=Y&ReturnFlag=1";
	//location.href = tSrc;
	window.open(tSrc,"������Ϣ�鿴",'width=2000,height=1000,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


/**
 * �����������ť��չʾ
 */
function showQuestionButton(cQuotNo,cQuotBatNo,cActivityID) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSql");
	tSQLInfo.setSqlId("LSQuotSql46");
	tSQLInfo.addSubPara(cQuotNo);
	tSQLInfo.addSubPara(cQuotBatNo);
		
	
	var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr[0][0]=="0") {
		document.all("enterQuest").style.display = "none";
	} else {
		document.all("enterQuest").style.display = "";
	}
}

/**
 * ���������
 */
function goQuestion() {
	
	var tRow = QuotGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ��ѯ����Ϣ��");
		return false;	
	}
	var tQuotNoTemp = QuotGrid.getRowColData(tRow-1,1);
	var tQuotBatNoTemp = QuotGrid.getRowColData(tRow-1,2);
	var tActivityID = QuotGrid.getRowColData(tRow-1,19);
	
	//��� ShowFlag=1 ��ǣ�����ѯ�۲�ѯʱ�������ģ�鰴ť����ʾ����
	window.open("../g_busicommon/LDQuestionMain.jsp?OtherNoType=QUOT&OtherNo="+tQuotNoTemp+"&SubOtherNo="+tQuotBatNoTemp+"&ActivityID="+tActivityID+"&ShowStyle=1&ShowFlag=1","���������",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * �����ܹ�˾�������չʾ
 */
function showUWDescButton(cQuotNo,cQuotBatNo,cActivityID) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQuerySql");
	tSQLInfo.setSqlId("LSQuotQuerySql4");
	tSQLInfo.addSubPara(cQuotNo);
	tSQLInfo.addSubPara(cQuotBatNo);
	
	var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr[0][0]=="0") {
		document.all("UWDesc").style.display = "none";
	} else {
		document.all("UWDesc").style.display = "";
	}
}

/**
 * �ܹ�˾�������
 */
function goUWDesc() {
	
	var tRow = QuotGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ��ѯ����Ϣ��");
		return false;	
	}
	var tQuotNoTemp = QuotGrid.getRowColData(tRow-1,1);
	var tQuotBatNoTemp = QuotGrid.getRowColData(tRow-1,2);
	
	window.open("./LSQuotShowUWDescMain.jsp?QuotNo="+ tQuotNoTemp + "&QuotBatNo="+ tQuotBatNoTemp,"�ܹ�˾�������",'height=300,width=900,top=0,left=370,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ��ӡѯ�۵�
 */
function printInquiry(o) {
	
	var tRow = QuotGrid.getSelNo();
	var tQuotNoT = QuotGrid.getRowColData(tRow-1,1);
	var tQuotBatNoT = QuotGrid.getRowColData(tRow-1,2);
	var tQuotTypeT = QuotGrid.getRowColData(tRow-1,3);
	var tTranProdTypeT = QuotGrid.getRowColData(tRow-1,6);
	var tMissionIDT = "";
	var tSubMissionIDT = "";
	var tActivityIDT = "";
	
	var tPrintType = "";
	if (o == "pdf") {
		tPrintType = "pdf";
	} else if (o == "doc") {
		tPrintType = "doc";
	}
	mOperate = "QUERY_PRINT";
	fm.action = "./LSQuotPrintInquirySave.jsp?QuotType="+tQuotTypeT+"&QuotNo="+tQuotNoT+"&QuotBatNo="+tQuotBatNoT+"&MissionID="+tMissionIDT+"&SubMissionID="+tSubMissionIDT+"&ActivityID="+tActivityIDT+"&Operate="+ mOperate +"&ProdType="+ tTranProdTypeT+"&PrintType="+tPrintType;
	submitForm(fm);
} 

/**
 * �����ٱ��ٷַ������ð�ť
 */
function showFaculButton(cQuotNo,cQuotBatNo) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotQuerySql");
	tSQLInfo.setSqlId("LSQuotQuerySql6");
	tSQLInfo.addSubPara(cQuotNo);
	tSQLInfo.addSubPara(cQuotBatNo);
	
	var tArr =  easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr != null && tArr[0][0]=="2") {
		document.all("faculButton").style.display = "";
	} else {
		document.all("faculButton").style.display = "none";
	}
}

/**
 * �ٱ��ٷַ�������
 */
function faculPlanInput() {
	
	var tRow = QuotGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ��ѯ����Ϣ��");
		return false;	
	}
	var tQuotNoTemp = QuotGrid.getRowColData(tRow-1,1);
	var tQuotBatNoTemp = QuotGrid.getRowColData(tRow-1,2);
	
	window.open("../reinsure/RIFaculPreceptMain.jsp?QuotNo="+ tQuotNoTemp + "&QuotBatNo="+ tQuotBatNoTemp+ "&Flag=0","�ٱ��ٷַ�������",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
