//���¼�¼�� 
//������:  
//��������:  2008-9-17 
//����ԭ��/���ݣ�

var showInfo;
var turnPage = new turnPageClass();
var tResourceName = 'ibrms.CommandMainSql';
// ��ѯ���г������������¼
function queryClick() {
	//BOM�߼���ѯ
		var sqlid1="CommandMainSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(document.all("CommandName").value);//ָ������Ĳ���
		mySql1.addSubPara(document.all("CommandType").value);//ָ������Ĳ���
		mySql1.addSubPara(document.all("CommType").value);//ָ������Ĳ���
		mySql1.addSubPara(document.all("Valid").value);
	  var strSql=mySql1.getString();	
	  
	/*
	
	var sqlStr = "select name,display,Replace(implenmation,'''','\"') implenmation,valid,commandtype,resulttype,paratype,paranum,description,commtype from LRCOMMAND where 1=1";
	if (document.all("CommandName").value.trim()!=null&&document.all("CommandName").value.trim().length > 0) {
		sqlStr += " and name like  '%" + document.all("CommandName").value + "%'";
	}	
	if (document.all("CommandType").value.length > 0) {
		sqlStr += " and commandtype='" + document.all("CommandType").value + "'";
	}	
	if (document.all("CommType").value.length > 0) {
		sqlStr += " and CommType='" + document.all("CommType").value + "'";
	}	
	
	if (document.all("Valid").value.length > 0) {
		sqlStr += " and valid='" + document.all("Valid").value + "'";
	}
	sqlStr += " order by name";*/
	//prompt("",sqlStr);
	turnPage.queryModal(strSql, QueryGrpGrid);
	
}

function hiddenButton(){
	var BranchTyp = document.all('BranchTyp').value;
	if(BranchTyp=='1'){
		document.all('ic').style.display='none';
		document.all('uc').style.display='none';
		document.all('dc').style.display='none';		
	}
}

//��������
function insertClick() {
	document.all("CommandName").value = "";
	document.all("CommandType").value = "";	
	document.all("Valid").value = "";	
	document.location.href = "CommandAddInput.jsp";
}

//����
function updateClick(){
	var count = QueryGrpGrid.getSelNo();// ��ȡѡ�е���
	if(count>0){
	var CommandName = QueryGrpGrid.getRowColData(count-1,1);// ��ȡѡ���е�����
	}else{
		if (count == 0) {
			alert("����û��ѡ����Ҫ�޸ĵ������");
			return;
		}
	}		
	document.location.href = "CommandUpdate.jsp?CommandName="+CommandName;
}

//�ύ
function submitForm() {	
	var showStr = "�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit(); // �ύ
}


//ɾ��
function deleteClick() {
	var selMenuGrpNo = QueryGrpGrid.getSelNo();
	if (selMenuGrpNo == 0) {
		alert("����û��ѡ����Ҫɾ���������");
		return;
	}
	if (!confirm("��ȷʵҪɾ������������"))
		return;
	document.all("Transact").value = "DELETE";
	submitForm();	
	queryClick();	
}

function InfoClose(){
	try{
		showInfo.close();
	}catch(ex){
		
	}
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {
	alert(FlagStr);
	InfoClose();
	if (FlagStr == "Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {		
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
			+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	}
}