/***************************************************************
 * <p>ProName��LJPremMatchConfirmInput.jsp</p>
 * <p>Title������ƥ��</p>
 * <p>Description������ƥ��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ������
 * @version  : 8.0
 * @date     : 2014-06-10
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage6 = new turnPageClass();
var turnPage7 = new turnPageClass();
var turnPage8 = new turnPageClass();
var tOperate;

/**
 * ��ѯ
 */
function queryMatchInfo() {
	
	initMatchInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql15");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), MatchInfoGrid, 1, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

function matchConfirmClick() {
	
	fm3.all("MatchConfirmButton").disabled = true;
	if (!beforeMatchConfirmClick()) {
		fm3.all("MatchConfirmButton").disabled = false;
		return false;
	}
	
	var tSelNo = MatchInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ���������ݣ�");
		return false;
	}
	
	var tMatchSerialNo = MatchInfoGrid.getRowColData(tSelNo, 3);
	
	tOperate = "MATCHCONFIRM";
	fmPub.Operate.value = tOperate;
	fm3.action = "./LJPremMatchConfirmSave.jsp?Operate="+ tOperate +"&MatchSerialNo="+ tMatchSerialNo;
	submitForm(fm3);
}

function beforeMatchConfirmClick() {

	var tSelNo = MatchInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ���������ݣ�");
		return false;
	}
	
	var tMatchSerialNo = MatchInfoGrid.getRowColData(tSelNo, 3);
	
	var tConfirmConclusion = fm3.ConfirmConclusion.value;
	var tConfirmDesc = fm3.ConfirmDesc.value;
	if (tConfirmConclusion==null || tConfirmConclusion=="") {
		alert("��˽��۲���Ϊ�գ�");
		return false;
	}
	
	if (tConfirmConclusion=="01") {
		
		if (tConfirmDesc==null || tConfirmDesc=="") {
			alert("�˻�ʱ������������Ϊ�գ�");
			return false;
		}
	}
	
	if (tConfirmDesc.length>300) {
		alert("��������Ӧ��300���ڣ�");
		return false;
	}
	
	return true;
}

/**
 * �ύ����
 */
function submitForm(obj) {

	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
		
		fm3.all("MatchConfirmButton").disabled = false;
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
		
		fm3.all("MatchConfirmButton").disabled = false;
		dealAfterSubmit();
	}
}

function dealAfterSubmit() {
	
	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="MATCHCONFIRM") {
	
		fm3.ConfirmConclusion.value = "";
		fm3.ConfirmConclusionName.value = "";
		fm3.ConfirmDesc.value = "";
		initMatchInfoGrid();
		initChoosedData1Grid();
		initBusinessData1Grid();
		initUploadFileGrid();
		divMatchingConfirmGrid.style.display = "none";
		divUploadInfo.style.display = "none";
	}
}

/**
 * ��ѯ��ѡƥ���¼�շ�����
 */
function queryMatchFeeInfo(o) {
	
	initChoosedData1Grid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql13");
	tSQLInfo.addSubPara(o);
	
	turnPage6.queryModal(tSQLInfo.getString(), ChoosedData1Grid, 0, 1);
}

/**
 * ��ѯ��ѡƥ���¼Ӧ������
 */
function queryMatchPayInfo(o) {
	
	initBusinessData1Grid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	if(_DBT==_DBO){
		tSQLInfo.setSqlId("LJMatchSql14");
	}else if(_DBT==_DBM){
		tSQLInfo.setSqlId("LJMatchSql25");
	}
	tSQLInfo.addSubPara(o);
	
	turnPage7.queryModal(tSQLInfo.getString(), BusinessData1Grid, 0, 1);
}

/**
 * ��ѯ��ƥ���ϴ��ĸ�����Ϣ
 */
function queryAttachmentInfo(o) {

	initUploadFileGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql7");
	tSQLInfo.addSubPara(o);
	
	turnPage8.queryModal(tSQLInfo.getString(), UploadFileGrid, 0, 1);
	
	if (turnPage8.strQueryResult) {
		divUploadInfo.style.display = "";
	} else {
		divUploadInfo.style.display = "none";
	}
}

function showMatchDetail() {

	var tSelNo = MatchInfoGrid.getSelNo()-1;
	var tMatchSerialNo = MatchInfoGrid.getRowColData(tSelNo, 3);
	
	queryMatchFeeInfo(tMatchSerialNo);
	queryMatchPayInfo(tMatchSerialNo);
	
	divMatchingConfirmGrid.style.display = "";
	queryAttachmentInfo(tMatchSerialNo);
}

/**
 * ���ظ���
 */
function downLoadClick(parm1) {
	
	var tFileName = document.all("UploadFileGrid3").value;
	var tFilePath = document.all("UploadFileGrid4").value;
	
	window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
}

/**
 * ��������
 */
function expData() {
	
	fm.SheetName.value = "�������Ϣ�б�";
	
	var tTitle = "�������^|������������^|�շѽ��^|ʹ����ɽ��^|Ӧ�ս��^|������ɽ��^|������^|��������" +
			"^|�����ύ����^|ƥ�������^|ƥ������^|ƥ���ύ����";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql21");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
