//���¼�¼�� ��ʼ��BOM���ҳ��
//������:  
//��������:  2008-8-15 
//����ԭ��/���ݣ�

var showInfo;
var turnPage = new turnPageClass();
var tResourceName = 'ibrms.downloadBOMSql';
function submitForm() {
	// �ύǰ�ļ���
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
		//showInfo.close();	
}

function queryClick() {
	//BOM�߼���ѯ
//	var sqlStr = "select name,cnname,fbom,localitem,fatheritem,bomlevel,business,discription,source,valid from LRBOM where 1=1";
	
	var sqlid1="downloadBOMSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(document.all("eName").value);//ָ������Ĳ���
	mySql1.addSubPara(document.all("cName").value);//ָ������Ĳ���
	mySql1.addSubPara(document.all("Business").value);//ָ������Ĳ���
	mySql1.addSubPara(document.all("Valid").value);
	var strSql=mySql1.getString();	
	
	/*if (document.all('eName').value.trim().length > 0) {
		sqlStr += " and name like  '%" + document.all("eName").value + "%'";
	}
	if (document.all('cName').value.trim().length > 0) {
		sqlStr += " and cnname like  '%" + document.all("cName").value + "%'";
	}
	if (document.all('Business').value.trim().length > 0) {
		sqlStr += " and business like  '%" + document.all("Business").value + "%'";
	}
	if (document.all('Valid').value.trim().length > 0) {
		sqlStr += " and valid like  '%" + document.all("Valid").value + "%'";
	}
	sqlStr += " order by name";*/
	//prompt("",sqlStr);
	turnPage.queryModal(strSql, QueryGrpGrid);
	
}

function closeShowInfo(){
	try{
		showInfo.close();
	}catch(ex){
		
	}
}
// �ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {
	//alert(FlagStr);	
	if (FlagStr == "Fail") {	
		closeShowInfo();
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="
				+ content;
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");		
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		closeShowInfo();
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+ content;	
		//showModalDialog(urlStr, window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");		
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		}
}

function creatPartClick(){
	var i = 0;
	var flag = 0;
	flag = 0;
	//�ж��Ƿ���ѡ���ӡ����
	for( i = 0; i < QueryGrpGrid.mulLineCount; i++ )
	{
		if( QueryGrpGrid.getChkNo(i) == true )
		{
			flag = 1;
			break;
		}
	}
	//���û�д�ӡ���ݣ���ʾ�û�ѡ��
	if( flag == 0 )
	{
		alert("����û��ѡ����Ҫ����JAVA�ļ���BOM��Ϣ");
		return false;
	}
	document.all('creatFlag').value = "CREATEPAET";	
	submitForm();	
	queryClick();
}

function creatAllClick(){
	document.all("creatFlag").value = "CREATEALL";
	submitForm();	
}

function downClick(){
	document.all("creatFlag").value = "DOWNCLASS";
	submitForm();
}