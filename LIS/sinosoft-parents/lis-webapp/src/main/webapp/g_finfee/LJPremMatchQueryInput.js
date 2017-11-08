/***************************************************************
 * <p>ProName��LJPremMatchQueryInput.js</p>
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
var tOperate;

/**
 * ��ѯ
 */
function queryMatchInfo() {
	
	initMatchInfoGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql8");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara("");
	
	turnPage1.queryModal(tSQLInfo.getString(), MatchInfoGrid, 0, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ����ƥ��
 */
function goToMatch() {

	var tSelNo = MatchInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ���в����ļ�¼��");
		return false;
	}
	
	var tMatchSerialNo = MatchInfoGrid.getRowColData(tSelNo, 3);
	location.href = "./LJPremMatchInput.jsp?MatchSerialNo="+ tMatchSerialNo;
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
	 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content,filepath,tfileName) {
	
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
	 var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    var showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	var filepath1 =filepath+tfileName;
	 window.location = "../common/jsp/download.jsp?FilePath="+filepath1+"&FileName="+tfileName;
	 showInfo.focus();
	
	}
	
	dealAfterSubmit(FlagStr);
}

function printMatch() {
	
	var tSelNo = MatchInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��Ҫ�����ļ�¼��");
		return false;
	}
	
	var tMatchSerialNo = MatchInfoGrid.getRowColData(tSelNo, 3);
	var tOperate = "PRINT";
	fmPub.Operate.value = tOperate;
	fm.action = "./LJPremMatchPrintSave.jsp?Operate="+ tOperate +"&MatchSerialNo="+ tMatchSerialNo;
	submitForm(fm);
}

function dealAfterSubmit(cSuccFlag) {
	
	tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="PRINT") {
		
		if (cSuccFlag!="Fail") {
			
			var tSelNo = MatchInfoGrid.getSelNo()-1;
			var tMatchSerialNo = MatchInfoGrid.getRowColData(tSelNo, 3);
			fm.ManageCom.value = "";
			fm.QueryStartDate.value = "";
			fm.QueryEndDate.value = "";
			initMatchInfoGrid();
	
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_finfee.LJMatchSql");
			tSQLInfo.setSqlId("LJMatchSql8");
			tSQLInfo.addSubPara(tManageCom);
			tSQLInfo.addSubPara(fm.ManageCom.value);
			tSQLInfo.addSubPara(fm.QueryStartDate.value);
			tSQLInfo.addSubPara(fm.QueryEndDate.value);
			tSQLInfo.addSubPara(tMatchSerialNo);
			
			turnPage1.queryModal(tSQLInfo.getString(), MatchInfoGrid, 0, 1);
		}
	}
}

function downLoadClick(parm1) {
	
	var tFlag = document.all(parm1).all("MatchInfoGrid13").value;
	if (tFlag!='1') {
		return;
	}
	
	var tMatchSerialNo = document.all(parm1).all("MatchInfoGrid3").value;
	var tFilePath = document.all(parm1).all("MatchInfoGrid15").value;
	
	var tFileName = tMatchSerialNo+".pdf";
	window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+"&FileName="+tFileName;
	
}

/**
 * ��������
 */
function expData() {
	
	fm.SheetName.value = "����������Ϣ�б�";
	
	var tTitle = "�������^|������������^|�շѽ��^|ʹ����ɽ��^|Ӧ�ս��^|������ɽ��^|������";

	fm.SheetTitle.value = tTitle;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_finfee.LJMatchSql");
	tSQLInfo.setSqlId("LJMatchSql20");
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara("");
	
	fm.SheetSql.value = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryLargeDataExport.jsp";
	fm.submit();
}
