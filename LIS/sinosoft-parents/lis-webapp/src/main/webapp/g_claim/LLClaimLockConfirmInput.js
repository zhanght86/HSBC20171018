/***************************************************************
 * <p>ProName��LLClaimLockConfirmInput.js</p>
 * <p>Title����������ȷ��</p>
 * <p>Description����������ȷ��</p>
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
	*��ѯ����������б�
	*/
function queryClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimLockConfirmSql");
	tSQLInfo.setSqlId("LLClaimLockConfirmSql");
	tSQLInfo.addSubPara(mManageCom);
	tSQLInfo.addSubPara(fm.GrpContNo.value);
	tSQLInfo.addSubPara(fm.AppntName.value);
	tSQLInfo.addSubPara(fm.SignCom.value);
	tSQLInfo.addSubPara(fm.CustomerName.value);	
	tSQLInfo.addSubPara(fm.IdNo.value);	
	tSQLInfo.addSubPara(fm.BirthDay.value);	
	tSQLInfo.addSubPara(fm.CustomerNo.value);
	tSQLInfo.addSubPara(fm.AppntName.value);	
	turnPage1.queryModal(tSQLInfo.getString(), AppLockGrid,"2");		
}

/**
	*����ȷ��
	*/
function confirmUnlock(){
	
	var i = AppLockGrid.getSelNo();
   if(i == 0 ) {
   		alert("����ѡ��һ����¼��");
   }else {	
   	
   	if (fm.AuditConlusion.value==null || fm.AuditConlusion.value=="") {
   		alert("��˽��۲���Ϊ��!");
   		fm.AuditConlusion.focus();
   		fm.AuditConlusion.style.background="#ffb900";   
   		return false;		
   	}
   	if (fm.AuditReason.value==null || fm.AuditReason.value=="") {
   		alert("����������Ϊ��!");
   		fm.AuditReason.focus();
   		fm.AuditReason.style.background="#ffb900";
   		return false;  		
   	}   	
   	
   	fm.LockNo.value = AppLockGrid.getRowColData(i-1,1);
   	fm.Operate.value="CONFIRM";
   	submitForm();
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
		//showDialogWindow(urlStr, 1);
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showDialogWindow(urlStr, 1);
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	
		showInfo.focus();
		initForm();
	}	
}

/**
 * ������ѡ���Ժ����
 */
function afterCodeSelect(cCodeName, Field) {

	if(cCodeName == '') {
		
	}
	
}
