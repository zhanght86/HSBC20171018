//���¼�¼�� 
//������:  
//��������:  2008-8-15 
//����ԭ��/���ݣ�

var showInfo;
var turnPage = new turnPageClass();
var turnPageitem = new turnPageClass();

var tResourceName = 'productdef.LDMsgInputSql';

// ��ѯ���г�����BOM��¼
function queryClick() { 
	
	
		var sqlid1="LDMsgInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(jKeyID);//ָ������Ĳ���
		mySql1.addSubPara(jMsgType);//ָ������Ĳ���
		 var strSql=mySql1.getString();	
		// alert(strSql);
	turnPage.queryModal(strSql, QueryGrpGrid);
}


function deleteForm() {	
	var count = QueryGrpGrid.getSelNo();// ��ȡѡ�е���
	if(count<=0){
		myAlert(''+"��ѡ����Ҫɾ��������!"+'');
		return false;
	}
		
	//var i = 0;
	//var showStr = "�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	fm.all('hiddenAction').value='DELETE';
	lockPage(""+"�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��!"+"");
	fm.submit(); // �ύ

}

//�ύ
function submitForm() {	
	if(!verifyForm('fm')){
			return false;
	}
	//var i = 0;
	//var showStr = "�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	lockPage(""+"�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��!"+"");
	fm.all('hiddenAction').value='SAVE';
	fm.submit(); // �ύ
}
//ɾ��BOM����
function deleteClick() {
	var selMenuGrpNo = QueryGrpGrid.getSelNo();
	if (selMenuGrpNo == 0) {
		myAlert(""+"����û��ѡ����Ҫɾ����BOM"+"");
		return;
	}
	if (!confirm(""+"ɾ��BOM��ɾ��BOM�µ����д�������ȷʵҪɾ�����BOM��"+""))
		return;
	fm.all("Action").value = "DELETE";
	submitForm();	
	queryClick();
	itemQuery();
}

//----------- ��������-----------------
//������ѯ�����Ը���BOM����ѡ�еļ�¼��ѯ����
function itemQuery() {	
	
		var Lan;
	var count = QueryGrpGrid.getSelNo();// ��ȡѡ�е���
	if(count>0){
	  Lan = QueryGrpGrid.getRowColData(count-1,1);// ��ȡѡ���е�����
	  Msg = QueryGrpGrid.getRowColData(count-1,3);
	  MsgType = QueryGrpGrid.getRowColData(count-1,2);
	  document.getElementById("MsgLan").value=Lan;
	  document.getElementById("MsgDetail").value=Msg;
	   document.getElementById("MsgType").value=MsgType;
	  showOneCodeName('language','MsgLan','MsgLanName');
	   showOneCodeName('msgtype','MsgType','MsgTypeName');
	}
}

function afterSubmit(FlagStr,content)
{
  	unLockPage();
  if (FlagStr == "Fail")
    {
var urlStr="../common/jsp/MessagePage.jsp?picture=F&content="+content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

    }
    else
    {   
        content = ""+"����ɹ���"+"";
var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		}
		
		initForm();
}

//-------- add by jucy
//���ӷ��ذ�ť
function returnForm(){
	top.opener.focus();
	top.close();
}
//-------- end
