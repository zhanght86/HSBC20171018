//���¼�¼�� 
//������:  
//��������:  2008-8-15 
//����ԭ��/���ݣ�

var showInfo;
var turnPage = new turnPageClass();
var turnPageitem = new turnPageClass();

var tResourceName = 'ibrms.LDMsgResultInputSql';

// ��ѯ���г�����BOM��¼
function queryClick() {
	
	
		var sqlid1="LDMsgResultInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(jKeyID);//ָ������Ĳ���
		 var strSql=mySql1.getString();	
	turnPage.queryModal(strSql, QueryGrpGrid);
}


//�ύ
function submitForm() {	
	//var i = 0;
	//var showStr = "�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	document.all('hiddenAction').value='SAVE';
	lockPage("�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��!");
	fm.submit(); // �ύ

}


function deleteForm() {	
	var count = QueryGrpGrid.getSelNo();// ��ȡѡ�е���
	if(count<=0){
		alert('��ѡ����Ҫɾ��������!');
		return false;
	}
		
	//var i = 0;
	//var showStr = "�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	//var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");	
	document.all('hiddenAction').value='DELETE';
	lockPage("�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��!");
	fm.submit(); // �ύ

}



function itemQuery() {	
	
	var Lan;
	var count = QueryGrpGrid.getSelNo();// ��ȡѡ�е���
	if(count>0){
	  Lan = QueryGrpGrid.getRowColData(count-1,1);// ��ȡѡ���е�����
	  Msg = QueryGrpGrid.getRowColData(count-1,2);
	  document.getElementById("MsgLan").value=Lan;
	  document.getElementById("MsgDetail").value=Msg;
	  showOneCodeName('language','MsgLan','MsgLanName');;
	}
}

function afterSubmit(FlagStr,content)
{
	unLockPage();
  if (FlagStr == "Fail")
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		}
    else
    {   
        content = "����ɹ���";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		}
		
		initForm();
}