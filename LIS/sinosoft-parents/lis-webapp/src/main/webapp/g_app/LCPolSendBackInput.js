/***************************************************************
 * <p>ProName��LCPolSendBackInput.js</p>
 * <p>Title����ִ����</p>
 * <p>Description����ִ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-07
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

var tSQLInfo = new SqlClass();
var mOperate = '';


//��ѯ����
function queryClick(){
	if(!beforeCheck()){
		return false;
	}
	var mManageCom=fm.ManageCom.value;
	var mGrpPropNo=fm.GrpPropNo.value;
	var mGrpContNo=fm.GrpContNo.value;
	var mGrpName=fm.GrpName.value;
	var mExpressStartDate=fm.ExpressStartDate.value;
	var mExpressEndDate=fm.ExpressEndDate.value;
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPolSendBackSql");
	if(_DBT==_DBO){
		tSQLInfo.setSqlId("LCPolSendBackSql1");
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(mManageCom);
		tSQLInfo.addSubPara(mGrpPropNo);
		tSQLInfo.addSubPara(mGrpContNo);
		tSQLInfo.addSubPara(mGrpName);
		tSQLInfo.addSubPara(mExpressStartDate);
		tSQLInfo.addSubPara(mExpressEndDate);
   }else if(_DBT==_DBM)
   {
	    tSQLInfo.setSqlId("LCPolSendBackSql2");
	    tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(mManageCom);
		tSQLInfo.addSubPara(mGrpPropNo);
		tSQLInfo.addSubPara(mGrpContNo);
		tSQLInfo.addSubPara(mGrpName);
		tSQLInfo.addSubPara(mExpressStartDate);
		tSQLInfo.addSubPara(mExpressEndDate);
   }
	turnPage1.queryModal(tSQLInfo.getString(), ContInfoGrid, 1, 1);
	if(!turnPage1.strQueryResult){
		alert("δ�鵽���������Ľ��!");
		initContInfoGrid();
	}
}

/**
 * ����¼�������Ϣ
 */
function saveClick(){
	if(!beforeSub()){
		return false;
	}
	fm.action = "./LCPolSendBackSave.jsp?Operate=INSERT";
	submitForm(fm,"INSERT");	
}


function submitFunc()
{
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
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
		queryClick();
	}	
}
function submitForm(obj, tOperate) {
	
	submitFunc();
	mOperate = tOperate;
	obj.submit(); //�ύ
}


function beforeSub(){
	var tSelNo = ContInfoGrid.getChkCount();
	if (tSelNo<1) {
		alert("��ѡ��һ��Ͷ����Ϣ");
		return false;
	}
	for (var i=0; i<ContInfoGrid.mulLineCount; i++) {
		if (ContInfoGrid.getChkNo(i)) {
			var tDate = ContInfoGrid.getRowColData(i, 10);//��ִ����
			if(!isDateFormat(tDate)) {
				alert("������"+ (i+1) +"�пͻ�ǩ������¼���ʽ����,�ο�[2000-08-08]��");
				return false;
			}
			if(ContInfoGrid.getRowColData(i, 9)>tDate){
				alert("�ͻ�ǩ������Ӧ���ڿͻ���������!");
				return false;
			}
			if(tDate>tCurrentDate){
				alert("�ͻ�ǩ�����ڲ������ڵ�ǰ����!");
				return false;
			}
		}
	}
	return true;
}
function beforeCheck(){
	var tExpressStartDate=fm.ExpressStartDate.value;
	var tExpressEndDate=fm.ExpressEndDate.value;
	if(((tExpressStartDate!=null&&tExpressStartDate!='')&&(tExpressEndDate==null||tExpressEndDate==''))||((tExpressStartDate==null||tExpressStartDate=='')&&(tExpressEndDate!=null&&tExpressEndDate!=''))){
		alert("���͵Ǽ����ں͵��͵Ǽ�ֹ�ڱ���ͬʱΪ�ջ���ͬʱ¼��!");
		return false;
	}
	if(tExpressStartDate>tExpressEndDate){
		alert("���͵Ǽ�����Ӧ���ڵ��͵Ǽ�ֹ��!");
		return false;
	}
	return true;
}
