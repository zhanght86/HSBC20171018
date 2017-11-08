/***************************************************************
 * <p>ProName��LLClaimHandAppInput.js</p>
 * <p>Title��������ת������ҳ��</p>
 * <p>Description��������ת������ҳ��</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : ��Ф��
 * @version  : 8.0
 * @date     : 2014-01-01
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();
var tPageIndex = 0;
var tSelNo = 0;

/**
 * ��ѯ
 */
function queryClick() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimPageNoSql");
	tSQLInfo.setSqlId("LLClaimPageNoSql");
	tSQLInfo.addSubPara(fm.QueryPageNo.value);
	tSQLInfo.addSubPara(fm.AppOperator.value);
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(fm.AppStartDate.value);
	tSQLInfo.addSubPara(fm.AppEndDate.value);	
	
	turnPage1.queryModal(tSQLInfo.getString(),HandNoListGrid,1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}	
}
/**
 * ����ѡ�еĽ�����ת��
 */
function returnSelect() {
	
	var tSelNo = HandNoListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ�������û���Ϣ");
		return false;
	}
	try {
		
		var returnArr = new Array();
		returnArr = HandNoListGrid.getRowData(tSelNo);
		if (returnArr!=null) {
		
			if (returnArr[0]==null || returnArr[0]=="") {
				alert("���Ȳ�ѯ��");
				return false;
			} else {
				var tAvailabeSum = returnArr[1]-returnArr[2];
				if (tAvailabeSum<=0) {
					alert("���������Ѵﵽ���ޣ���ѡ������������ת��");
					return false;
				}
				top.opener.afterAppPageNo(returnArr);
			}		
		} else {
			return false;
		}
	} catch(ex) {
		alert("�����쳣��"+ ex);
	}
	top.close();	
}
/**
 * ���ӽ�����ת��Ϣ
 */
function addPageNoClick() {
	
	if (!verifyInput2()) {
		return false;
	}
	var tNum = fm.SumNum.value;
	if (tNum!=null && tNum<=0) {
		alert("���������������0");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimPageNoSql");
	tSQLInfo.setSqlId("LLClaimPageNoSql2");	
	tSQLInfo.addSubPara(fm.AppOperator.value);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	var RowNum = 0;
	if (tArr==null || tArr.length==0) {
		RowNum = 0;
	} else {
		RowNum = tArr[0][0];
	}
	var mRowNum = Number(RowNum)+1;
	tPageIndex = Math.ceil(mRowNum/10)-1;
	tSelNo = mRowNum%10;
	if(mRowNum%10 == 0){
		tSelNo = 10;
	}
	
	fm.PageNo.value = "";
	fm.Operate.value = "INSERT";
	fm.action = "./LLClaimHandAppSave.jsp?Operate = INSERT";
	submitForm();
}
/**
 * �޸�
 */
function modifyClick() {
	
	if (!verifyInput2()) {
		return false;
	}
	var tNum = fm.SumNum.value;
	if (tNum!=null && tNum<=0) {
		alert("���������������0");
		return false;
	}	
	fm.Operate.value = "UPDATE";
	submitForm();	
}
/**
 * ɾ��
 */
function deleteClick() {
	fm.Operate.value = "DELETE";
	submitForm();	
}
/**
 * ����
 */
function goBack() {
	top.close();
}
/**
 * ѡ��һ��������ˮ��
 */
function selectPageNo() {
	
	var tSelNo = HandNoListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ�������û���Ϣ");
		return;
	}
	var tPageNo = HandNoListGrid.getRowColData(tSelNo,1);
	if (tPageNo==null || tPageNo=="") {
		alert("���Ȳ�ѯ��");
		return;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimPageNoSql");
	tSQLInfo.setSqlId("LLClaimPageNoSql1");
	tSQLInfo.addSubPara(tPageNo);	
	var tArr = easyExecSql(tSQLInfo.getString());
	if (tArr!=null && tArr.length>0) {
		fm.PageNo.value = tArr[0][0];
		fm.SumNum.value = tArr[0][1];
		fm.Remark.value = tArr[0][2];
	}
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
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
/**
 * Ĭ��ѡ�иղ����ļ�¼
 */
function setSelRow(ObjGrid,tTurnPage){			
	 	
	if (tPageIndex!=null && tPageIndex!="") {
		for (var i=0; i<tPageIndex; i++) {
			tTurnPage.nextPage();
		}
	}
	if(tSelNo==null || tSelNo ==""){
		tSelNo = 1;
	}	
	ObjGrid.radioSel(tSelNo);
	
}
