//���¼�¼�� 
//������:  
//��������:  2008-8-15 
//����ԭ��/���ݣ�

var showInfo;
var turnPage = new turnPageClass();
var turnPageitem = new turnPageClass();

var tResourceName = 'ibrms.bomMainSql';

// ��ѯ���г�����BOM��¼
function queryClick() {
	//BOM�߼���ѯ
	/*
	var sqlStr = "select name,cnname,fbom,localitem,fatheritem,bomlevel,business,discription,source,valid from LRBOM where 1=1";
	if (document.all("eName").value.trim().length > 0) {
		sqlStr += " and name like  '%" + document.all("eName").value + "%'";
	}	
	if (document.all("cName").value.trim().length > 0) {
		sqlStr += " and cnname like  '%" + document.all("cName").value + "%'";
	}
	if (document.all("Business").value.length > 0) {
		sqlStr += " and business like  '%" + document.all("Business").value + "%'";
	}
	if (document.all("Valid").value.length > 0) {
		sqlStr += " and valid like  '%" + document.all("Valid").value + "%'";
	}
	sqlStr += " order by name";
	//prompt("",sqlStr);
	*/
	
		var sqlid1="bomMainSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(document.all("eName").value);//ָ������Ĳ���
		mySql1.addSubPara(document.all("cName").value);//ָ������Ĳ���
		mySql1.addSubPara(document.all("Business").value);//ָ������Ĳ���
		mySql1.addSubPara(document.all("Valid").value);
	  var strSql=mySql1.getString();	
	turnPage.queryModal(strSql, QueryGrpGrid);
}

//���BOM������ת��bomAddInput.jsp,����bomAddInput.jsp����
function insertClick() {
	document.all("eName").value = "";
	document.all("cName").value = "";	
	document.location.href = "bomAddInput.jsp";
}

function updateClick(){
	var count = QueryGrpGrid.getSelNo();// ��ȡѡ�е���
	if(count>0){
	var bomName = QueryGrpGrid.getRowColData(count-1,1);// ��ȡѡ���е�����
	}else{
		if (count == 0) {
			alert("����û��ѡ����Ҫ�޸ĵ�BOM");
			return;
		}
	}		
	document.location.href = "bomUpdate.jsp?bbName="+bomName;
}

//�ύ
function submitForm() {	
	var i = 0;
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

function hiddenButton(){
	var BranchTyp = document.all('BranchTyp').value;
	if(BranchTyp=='1'){
		document.all('ic').style.display='none';
		document.all('uc').style.display='none';
		document.all('dc').style.display='none';
		document.all('ii').style.display='none';
		document.all('iu').style.display='none';
		document.all('id').style.display='none';
		}
}

//ɾ��BOM����
function deleteClick() {
	var selMenuGrpNo = QueryGrpGrid.getSelNo();
	if (selMenuGrpNo == 0) {
		alert("����û��ѡ����Ҫɾ����BOM");
		return;
	}
	if (!confirm("ɾ��BOM��ɾ��BOM�µ����д�������ȷʵҪɾ�����BOM��"))
		return;
	document.all("Action").value = "DELETE";
	submitForm();	
	queryClick();
	itemQuery();
}

//----------- ��������-----------------
//������ѯ�����Ը���BOM����ѡ�еļ�¼��ѯ����
function itemQuery() {	
	
	var bomName;
	var count = QueryGrpGrid.getSelNo();// ��ȡѡ�е���
	if(count>0){
	  bomName = QueryGrpGrid.getRowColData(count-1,1);// ��ȡѡ���е�����
	}
	
	/*else{
		 bomName="";
	}
	var sqlStr = "select name,bomname,cnname,connector,ishierarchical,isbase,sourcetype,source,commandtype,precheck,valid,description from lrbomitem where 1=1";
	
	if(bomName.trim()!=""){
		sqlStr+=" and bomname like '%" + bomName + "%'";
	}
	sqlStr+=" order by cnname,name";
	*/
		var sqlid1="bomMainSql2";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(bomName);//ָ������Ĳ���
	  var strSql=mySql1.getString();	
	
	turnPageitem.queryModal(strSql, ItemGrid);
//	for ( var i = 0; i < ItemGrid.mulLineCount; i++) {
//		if (ItemGrid.getRowColData(i, 10) == "null") {		
//			ItemGrid.setRowColData(i, 10, "");
//		}
//	}
}

//��������
function itemInsert(){
	var count = QueryGrpGrid.getSelNo();// ��ȡѡ�е���
	if(count>0){
	var bomName = QueryGrpGrid.getRowColData(count-1,1);// ��ȡѡ���е�����
	}else{
		if (count == 0) {
			alert("����û��ѡ����Ҫ���Ӵ���������BOM");
			return;
		}
	}
	document.location.href = "itemAddInput.jsp?bbName="+bomName;
}

function itemUpdate(){
	var count = ItemGrid.getSelNo();// ��ȡѡ�е���	
	if(count>0){
	var itemName = ItemGrid.getRowColData(count-1,1);// ��ȡѡ���е�����	
	var bomName = ItemGrid.getRowColData(count-1,2);// ��ȡѡ���е�����	
	document.location.href = "itemUpdate.jsp?itemName="+itemName+"&bomName="+bomName;
	}else{
		if (count == 0) {
			alert("����û��ѡ����Ҫ�޸ĵĴ���");
			return;
		}
	}			
}

function itemdelete(){
	var selMenuGrpNo = ItemGrid.getSelNo();
	if (selMenuGrpNo == 0) {
		alert("����û��ѡ����Ҫɾ���Ĵ���");
		return;
	}
	if (!confirm("��ȷʵҪɾ�����������"))
		return;
	
	document.all("itemAction").value = "DELETE";
	submitForm();
	showInfo.close(); 
	itemQuery();
}

function afterSubmit(FlagStr)
{
    showInfo.close();  
    if (document.all('Action').value == "DELETE") {
        if (FlagStr == "success")
            alert("ɾ��BOM�ɹ���");
        else
            alert("ɾ��BOMʧ��!");
    }		
    if (document.all('itemAction').value == "DELETE") {
        if (FlagStr == "success")
            alert("ɾ�������ɹ���");
        else
            alert("ɾ������ʧ��!");
    }	
}