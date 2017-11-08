/***************************************************************
 * <p>ProName��LLClaimCaseCheckAppInput.js</p>
 * <p>Title��������������</p>
 * <p>Description��������������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯ�����ظ��˰���
 */
function queryMain(tFlag) {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckAppSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckAppSql");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo.value);
	tSQLInfo.addSubPara(fm.GrpName.value);
	tSQLInfo.addSubPara(fm.AcceptCom.value);
	tSQLInfo.addSubPara(fm.AcceptConfirmStart.value);
	tSQLInfo.addSubPara(fm.AcceptConfirmEnd.value);
	tSQLInfo.addSubPara(fm.AcceptWorkdays.value);
	tSQLInfo.addSubPara(mManageCom);
				
	turnPage1.queryModal(tSQLInfo.getString(),MainCaseGrid, 2);
	if (!turnPage1.strQueryResult && tFlag==1) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	} else if (turnPage1.strQueryResult) {
		setMulLineColor(MainCaseGrid);
	}
}
//ΪMulLine������ɫ
function setMulLineColor(ObjGrid) {
	
	//ΪMulLine������ɫ
	for (var i=0;i<ObjGrid.mulLineCount;i++) {
		
		var tAcceptWorkdays = ObjGrid.getRowColData(i, 8);
		
		if (tAcceptWorkdays>=5 && tAcceptWorkdays<=10) {
			ObjGrid.setColor(i, "#43FA67");
		} else if (tAcceptWorkdays>10 && tAcceptWorkdays<=25) {
			ObjGrid.setColor(i, "#FCB97C");
		} else if (tAcceptWorkdays>25) {
			ObjGrid.setColor(i, "#FD8B73");
		}

	}	
}

function firstPage(tObject,tObjGrid) {
	
	tObject.firstPage();
	setMulLineColor(tObjGrid);
}

function previousPage(tObject,tObjGrid) {
	
	tObject.previousPage();
	setMulLineColor(tObjGrid);
}

function nextPage(tObject,tObjGrid) {
	
	tObject.nextPage();
	setMulLineColor(tObjGrid);
}

function lastPage(tObject,tObjGrid) {
	
	tObject.lastPage();
	setMulLineColor(tObjGrid);
}

/**
 * ��ѯ���˳ظ��˰���
 */
function querySelf(type) {

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimCaseCheckAppSql");
	tSQLInfo.setSqlId("LLClaimCaseCheckAppSql1");
	
	tSQLInfo.addSubPara(fm.GrpRgtNo1.value);
	tSQLInfo.addSubPara(fm.GrpName1.value);
	tSQLInfo.addSubPara(fm.AcceptCom1.value);
	tSQLInfo.addSubPara(fm.AcceptConfirmStart1.value);
	tSQLInfo.addSubPara(fm.AcceptConfirmEnd1.value);
	tSQLInfo.addSubPara(fm.AcceptWorkdays1.value);
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(mOperator);
				
	turnPage2.queryModal(tSQLInfo.getString(),SelfCaseGrid, 2);
	if (turnPage2.strQueryResult) {
		setMulLineColor(SelfCaseGrid);
	}
	if (!turnPage2.strQueryResult && type==1) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}	
}
/**
 * ���밸��
 */
function applayClick() {
	
	var tSelNo = MainCaseGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ�񹫹����е����ݣ�");
		return false;
	}
	
	var tGrpRgtNo = MainCaseGrid.getRowColData(tSelNo,1);
	fm.SelectGrpRgtNo.value = tGrpRgtNo;
	
	var tLastOperator = MainCaseGrid.getRowColData(tSelNo,10);	
	if (tLastOperator==mOperator) {
		alert("����������밸�������˲���Ϊͬһ��!");
		return false;
	}			
		
	fm.Operate.value="APPLY";
	submitForm();	
}
/**
 * ���밸��
 */
function enterCase(parm1) {
	
	var tSelNo;
	if (parm1!=null && parm1!="") {
		
		tSelNo = document.all(parm1).all("SelfCaseGridNo").value;
		tSelNo = tSelNo - turnPage2.pageIndex*turnPage2.pageLineNum;
		SelfCaseGrid.radioSel(tSelNo);
		tSelNo = tSelNo - 1;
	}	
	tSelNo = SelfCaseGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ����˳��е����ݣ�");
		return false;
	}
	var tGrpRgtNo = SelfCaseGrid.getRowColData(tSelNo,1);
	fm.SelectGrpRgtNo.value = tGrpRgtNo;
	
	window.location.href="LLClaimCaseCheckInput.jsp?GrpRgtNo="+tGrpRgtNo+"&Mode=0";
}
/**
 * �ͷŰ���
 */
function releaseCase() {
	
	var tSelNo = SelfCaseGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ����˳��е����ݣ�");
		return false;
	}
	var tGrpRgtNo = SelfCaseGrid.getRowColData(tSelNo,1);
	fm.SelectGrpRgtNo.value = tGrpRgtNo;		
		
	fm.Operate.value="RELEASE";
	submitForm();		
}

/**
 * �ύ����
 */
function submitForm() {
	
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
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
		initForm();
		queryMain();
	}	
}