/***************************************************************
 * <p>ProName��LLClaimQuerySocialInfoInput.js</p>
 * <p>Title���籣��ѯ</p>
 * <p>Description���籣��ѯ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

//"����"��ť
function getToBack(){
		
	top.close();
}
/**
 * �ύ����
 */
function submitForm() {
	
	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	showInfo = showDialogWindow(urlStr, 0);
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
		showDialogWindow(urlStr, 1);
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		showDialogWindow(urlStr, 1);
	}	
	queryAccInfo();
}
function queryAccInfo(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimQueryAccInfoSql");
	tSQLInfo.setSqlId("LLClaimQuerySocialInfoSql1");
	tSQLInfo.addSubPara(tGrpRgtNo);
	tSQLInfo.addSubPara(tRgtNo);
	
//	turnPage1.pageLineNum=1000;
	//turnPage1.queryModal(tSQLInfo.getString(), QuesRecordGrid,"2",1,1);	
	turnPage1.queryModal(tSQLInfo.getString(),QuesRecordGrid,2);	
}

//����ѡ�е������˻���Ϣ
function returnInfo() {
	
	var tSelNo = QuesRecordGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("��ѡ��һ���˻���Ϣ");
		return false;
	}
	try {
		
		var returnArr = new Array();
		returnArr = QuesRecordGrid.getRowData(tSelNo);
		if (returnArr!=null) {
		
			if (returnArr[0]==null || returnArr[0]=="") {
				alert("���Ȳ�ѯ��");
				return false;
			} else {				
				top.opener.afterQueryAccInfo(returnArr);
			}		
		} else {
			return false;
		}
	} catch(ex) {
		alert("�����쳣��"+ ex);
	}
	top.close();
}