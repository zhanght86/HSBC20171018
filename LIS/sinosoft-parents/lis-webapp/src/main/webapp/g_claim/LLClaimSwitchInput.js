/***************************************************************
 * <p>ProName��LLClaimBenefitInput.jsp</p>
 * <p>Title����ͣ����������</p>
 * <p>Description����ͣ����������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �߶���
 * @version  : 8.0
 * @date     : 2014-02-26
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯ���������и��˰���
 */
function queryClaimList() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSwitchSql");
	tSQLInfo.setSqlId("LLClaimSwitchSql");
	tSQLInfo.addSubPara(mRgtNo);
	tSQLInfo.addSubPara(mCustomerNo);
	
	turnPage1.queryModal(tSQLInfo.getString(),ClaimCaseGrid,2);	
}
/**
 * ��ѯѡ�еĸ��˰��������켣
 */
function showSelectTrace() {
	
	var i = ClaimCaseGrid.getSelNo()-1;		
	var tRgtNo = ClaimCaseGrid.getRowColData(i,1);
	fm.RgtNo.value = tRgtNo;
	var tCustomerNo = ClaimCaseGrid.getRowColData(i,2);
	fm.CustomerNo.value = tCustomerNo;
	var tRgtConClusion = ClaimCaseGrid.getRowColData(i,8);
	var tPauseReason = document.getElementById("Pause");
	var tDivInfo = document.getElementById("divRemarkInfo");
	var tRemark = document.getElementById("Remark");
	if (tRgtConClusion=="6") {//������ͣ
		
		tDivInfo.style.display = "";
		tPauseReason.style.display = "none";
		tRemark.style.display = "";
		fm.RgtPause.style.display = "none";
		fm.RgtOpen.style.display = "";		
	} else {
		
		tDivInfo.style.display = "";
		tPauseReason.style.display = "";
		tRemark.style.display = "";
		fm.RgtPause.style.display = "";
		fm.RgtOpen.style.display = "none";			
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSwitchSql");
	tSQLInfo.setSqlId("LLClaimSwitchSql1");
	tSQLInfo.addSubPara(tRgtNo);
	tSQLInfo.addSubPara(tCustomerNo);	
	
	turnPage2.queryModal(tSQLInfo.getString(),ClaimCaseTraceGrid,2);	
		
}

//��ͣ����
function pauseRgt(){
	
	if(!notNull(fm.PauseReason,"������ͣԭ��")){return false;};
	if(!notNull(fm.ReasonDesc,"ԭ������")){return false;};
	fm.Operate.value="PAUSE";
	submitForm();	
}

//��������
function openRgt(){
	
	if(!notNull(fm.ReasonDesc,"ԭ������")){return false;};	
	fm.Operate.value="OPEN";
	submitForm();	
}
//�رղ���
function closeClick() {
	top.close();
	top.opener.afterSwitchCase();
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
	}	
}
/**
 * У��ǿ�Ҫ�أ����۽�
 */
function notNull(tObject,tName) {

	if (tObject!=null && tObject.value=="") {
		alert(tName+"����Ϊ�գ���¼�룡");
		if (tObject.className=="codeno") {
			tObject.className="warnno";
		}else {
			tObject.className="warn";
		}
		return false;
	} else if (tObject==null) {
		return false;
	}
	return true;
}