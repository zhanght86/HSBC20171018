/***************************************************************
 * <p>ProName��LLClaimMajorMainInput.js</p>
 * <p>Title���ش󰸼��ϱ������ؽ���</p>
 * <p>Description���ش󰸼��ϱ������ؽ���</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2012-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯ�����б�
 */
function queryClick() {
	
	//У��¼���������
	var tComGrade;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimMajorAppSql");
	tSQLInfo.setSqlId("LLClaimMajorAppSql2");
	tSQLInfo.addSubPara(mManageCom);
	tArr = null;
	tArr = easyExecSql(tSQLInfo.getString());
	//�ж��Ƿ��ѯ�ɹ�
	if (tArr==null || tArr.length==0) {
		alert("δ��ѯ����¼�����Ļ�������!");
		return false;
	} else {
		tComGrade = tArr[0][0];
	}
			
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimMajorAppSql");	
	if (tComGrade=="01") {
		
		tSQLInfo.setSqlId("LLClaimMajorAppSql1");		
	} else if (tComGrade=="02") {
		
		tSQLInfo.setSqlId("LLClaimMajorAppSql");
	}
	tSQLInfo.addSubPara(fm.RptNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.RptCom.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);
	tSQLInfo.addSubPara(fm.RptDateStart.value);
	tSQLInfo.addSubPara(fm.RptDateEnd.value);
	tSQLInfo.addSubPara(mManageCom);	
	
	turnPage1.queryModal(tSQLInfo.getString(),LLClaimReportGrid,2);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return;
	}	
}

/**
 * ��ѯ������ϸ
 */
function showRptDetail() {
	
	var tSelNo = LLClaimReportGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ�񹫹�����һ��������Ϣ");
		return false;
	}
	var tRptNo = LLClaimReportGrid.getRowColData(tSelNo,1);
	if (tRptNo==null || tRptNo=="") {
		alert("���Ȳ�ѯ�������¼���Ϣ��");
		return;
	}	
	window.location.href="LLClaimReportInput.jsp?Type=3&RptNo="+ tRptNo;	
}

/**
 * ���밸��
 */
function enterReport(parm1) {
	
	var tSelNo;
	if (parm1!=null && parm1!="") {
		tSelNo = document.all(parm1).all("LLClaimReportGridNo").value;
		tSelNo = tSelNo - turnPage1.pageIndex*turnPage1.pageLineNum;
		LLClaimReportGrid.radioSel(tSelNo);
		tSelNo = tSelNo - 1;
	} else {
		tSelNo = LLClaimReportGrid.getSelNo() - 1;
	}	
	
	if (tSelNo<0) {
		alert("��ѡ�񹫹�����һ��������Ϣ");
		return false;
	}
	var tRptNo = LLClaimReportGrid.getRowColData(tSelNo,1);
	if (tRptNo==null || tRptNo=="") {
		alert("���Ȳ�ѯ�������¼���Ϣ��");
		return;
	}	
	window.location.href="LLClaimReportInput.jsp?Type=4&RptNo="+ tRptNo;	
}

/**
 * �ύ����
 */
function submitForm() {
	
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = showDialogWindow(urlStr, 0);
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=1;      //�������ڵĿ��; 
	var iHeight=1;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit();
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
		//showDialogWindow(urlStr, 1);
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=1;      //�������ڵĿ��; 
		var iHeight=1;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		fm.submit();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showDialogWindow(urlStr, 1);
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=1;      //�������ڵĿ��; 
		var iHeight=1;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		fm.submit();
	}	
}

/**
 * ������ѡ���Ժ����
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == '') {
		
	}
	
}
