//���¼�¼�� ��ʼ���������ҳ��
//������:  
//��������:  2008-8-15 
//����ԭ��/���ݣ�

var showInfo;

function itemAdd() {	
	if(beforeSubmit()){
		document.all("Transact").value = "INSERT";
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
	}else{	
	
	}
}

//���ȡ��ʱ���ص�ҳ��
function returnParent(){
	document.location.href="bomMain.jsp"; 
}


//����ԤУ��
function afterCodeSelect(coname,Field){
	try{
		if(coname=='IbrmsCommandType'){
			var itemsql;
			var SourceType = document.all('CommandType').value;			
			if(SourceType==''){
				//itemsql= "select code1,codename from ldcode1 where 1=2";
				document.all('PreCheck').CodeData='';
				}else{
				//itemsql = "select code1,codename from ldcode1 where code='"+SourceType+"'";
			
			//alert('SourceType'+SourceType);
			var sqlid1="itemAddInputSql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName('ibrms.itemAddInputSql'); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		
			mySql1.addSubPara(SourceType);
		  var strSql=mySql1.getString();	
		 // alert('1'+strSql);
				var itemTem = easyQueryVer3(strSql,1,1,1);		
				if(itemTem==false){
					document.all('PreCheck').CodeData=''
				}else{
					document.all('PreCheck').CodeData = itemTem;
				}
			}
		}
	}catch(ex){
		
	}
}

// �ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content) {
	//alert(FlagStr);
	showInfo.close();
	if (FlagStr == "Fail") {
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
		var bomname = document.all("bomName").value;
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+ content;	
		var flag = confirm("�����ɹ�,�Ƿ��ڵ�ǰBOM�¼������Ӵ���?");
		if(flag){
			document.location.href="itemAddInput.jsp?bbName="+bomname; 
		}else{
			returnParent();	
		}
				
	}
}

function eNameCheck(Filed){	
	var patrn=/^[a-zA-Z0-9]{0,20}$/;
	if (!patrn.test(Filed.value.trim())) {
		alert("��ʽ����,ֻ��ΪӢ�ĺ�����");
		document.all("ItemEName").value = "";	
	}
}

function cNameCheck(Filed){	
	var patrn=/^[\u4e00-\u9fa5/]{0,30}$/;
	if (!patrn.test(Filed.value.trim())) {
		alert("��ʽ����,ֻ��Ϊ���ĺ�"/"");
		document.all("CNName").value = "";	
	}
}


function beforeSubmit() {
	// ���ǰУ��
	if (document.all('ItemEName').value.trim() == '') {
		alert("����Ӣ��������Ϊ��!");
		return false;
	}
	if (document.all('CNName').value.trim() == '') {
		alert("��������������Ϊ��!");
		return false;
	}
	if (document.all('bomName').value.trim() == '') {
		alert("����BOM���ֲ���Ϊ��!");
		return false;
	}
	if (document.all('IsHierarchical').value.trim() == '') {
		alert("��������ݲ���Ϊ��!");
		return false;
	}
	if (document.all('IsBase').value.trim() == '') {
		alert("������������Ϊ��!");
		return false;
	}	
	if (document.all('CommandType').value.trim() == '') {
		alert("�����������Ͳ���Ϊ��!");
		return false;
	}
	if (document.all('Valid').value.trim() == '') {
		alert("������Ч�Բ���Ϊ��!");
		return false;
	}
	return true;
}