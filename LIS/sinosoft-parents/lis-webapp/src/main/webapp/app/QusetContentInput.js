 //�������ƣ�SelAssignDuty.js
//�����ܣ�
//�������ڣ�2008-09-26
//������  :  liuqh
//���¼�¼��  ������    ��������     ����ԭ��/����

//var showInfo;
var arrResult;
var mDebug = "0";
var mOperate = "";
var mAction = "";
var mSwitch = parent.VD.gVSwitch;
var k = 0;
var turnPage = new turnPageClass();  
var turnPage1 = new turnPageClass();  
var strOperate ="like"; 
/*********************************************************************
 *  �ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function afterSubmit(FlagStr,content){
	showInfo.close();  
	fm.QuestCode.disabled = false;
	fm.BackObj.disabled = false;
	initForm();
	//fm.QuestCode.value="";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
}

//tongmeng 2007-11-13 modify
//����MS���߼��޸�
function afterCodeSelect( cCodeName, Field ){
	//alert(cCodeName);
	if(cCodeName=="backobj"){
		
	}
}

//��ѯ
function QueryData(){
	window.open("./QuestContentQueryMain.jsp");
//	var BackObj = fm.BackObj.value;
//	var QuestCode = fm.QuestCode.value;
//	fm.tCode.value = trim(BackObj+QuestCode);
//	var tQuerySql="select a.code,a.cont,substr(a.code,0,1),a.recordquest,a.operator,a.modifydate "
//				  +"from ldcodemod a where 1=1 and codetype = 'Question' "
//	              +getWherePart('code','tCode',strOperate)+" "
//	              +getWherePart('RecordQuest','RecordQuest',strOperate);
//	var ssArr=easyExecSql(tQuerySql);//prompt("",tQuerySql);
//	if(ssArr!=null){
		//fm.QuestCode.value=ssArr[0][0];
		//fm.Content.value=ssArr[0][1];
//	}else{
//		alert("δ�鵽���������������Ӧ�������");
//		return false;
//	}
//	turnPage.queryModal(tQuerySql, QuestGrid);
}

//����
function SaveQuest(){
	if(fm.BackObj.value==null||fm.BackObj.value==""){
		alert("��ѡ���Ͷ���");
		return false;
	}
	
	var BackObj = fm.BackObj.value;
	var QuestCode = fm.QuestCode.value;
//	var tQuerySql = "select * from ldcodemod where 1=1 and codetype = 'Question' and code='"+BackObj+QuestCode+"'";
	var tQuerySql="";
	var sqlid1="QuestContentInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("app.QuestContentInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(BackObj+QuestCode);//ָ������Ĳ���
	tQuerySql = mySql1.getString();
	var ssArr=easyExecSql(tQuerySql);
	if(ssArr!=null){
		alert("����������Ѵ��ڣ�������������");
		return false;
	}
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "INSERT";
	document.all('fmAction').value = mAction;//alert(73);
	alert(document.all('fmAction').value);
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	fm.submit(); //�ύ
}

//ɾ��
function deleteQuest(){
	alert("��ֹɾ����");
	return false;
	if(fm.QuestCode.value==null||fm.QuestCode.value==""){
		alert("���Ȳ�ѯ����Ҫɾ�����������");
		return false;
	}
	var BackObj = fm.BackObj.value;
	var QuestCode = fm.QuestCode.value;
//	var tQuerySql = "select code,cont from ldcodemod where 1=1 and codetype = 'Question' and code='"+BackObj+QuestCode+"'";
	var tQuerySql="";
	var sqlid2="QuestContentInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("app.QuestContentInputSql"); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(BackObj+QuestCode);//ָ������Ĳ���
	tQuerySql = mySql2.getString();
	var ssArr=easyExecSql(tQuerySql);
	if(ssArr==null){
		alert("�������������δ��ѯ���ñ����µ��������");
		return false;
	}
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "DELETE";
	document.all('mAction').value = mAction;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	fm.submit(); //�ύ
}

//�޸�
function updateQuest(){
	fm.QuestCode.disabled = false;
	fm.BackObj.disabled = false;
	if(fm.QuestCode.value==null||fm.QuestCode.value==""){
		alert("���Ȳ�ѯ����Ҫ�޸ĵ��������");
		return false;
	}
	if(fm.BackObj.value==null||fm.BackObj.value==""){
		alert("��ѡ���Ͷ���");
		return false;
	}
	
	var BackObj = fm.BackObj.value;
	var QuestCode = fm.QuestCode.value;
	var tQuerySql = "select * from ldcodemod where 1=1 and codetype = 'Question' and code='"+QuestCode+"'";
	var ssArr=easyExecSql(tQuerySql);
	if(ssArr==null){
		alert("�������������δ��ѯ���ñ����µ��������");
		fm.QuestCode.disabled = true;
		return false;
	}
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	mAction = "UPDATE";
	document.all('fmAction').value = mAction;//alert(111);
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
	fm.submit(); //�ύ
}

function displayQuest(){
	//alert("ok");
	var tBackObj="";//���Ͷ���
	var tQuestCode="";//���������
	var checkFlag = QuestGrid.getSelNo() - 1;
	
	var tCode=QuestGrid.getRowColData(checkFlag, 1);
	//alert("1tCode:"+tCode.substring(1,3));
	//alert("2tCode:"+tCode.substring(2,4));
	fm.RecordQuest.value=QuestGrid.getRowColData(checkFlag, 4);
	fm.BackObj.value=QuestGrid.getRowColData(checkFlag, 3);
	fm.QuestCode.value=tCode.substring(1,4)
	fm.Content.value=QuestGrid.getRowColData(checkFlag, 2);
}

//���ܷ����������ݲ���ʾ
function afterQuery(RecordQuest,BackObj,QuestCode,Content){
	
}