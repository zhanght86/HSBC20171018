//���¼�¼�� ��ʼ��BOM���ҳ��
//������:  
//��������:  2008-8-15 
//����ԭ��/���ݣ�

var showInfo;

function returnParent(){
	document.location.href="bomMain.jsp"; 
}

function submitForm() {
	// �ύǰ�ļ���
	if(beforeSubmit()){
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
		
	}else{	
	
	}
}

function afterCodeSelect(coname,Field){
	try{		
		if(coname=='ibrmsfBOM'){
			var bomtem = document.all('fBOM').value;
			//var fitemsql = "select name,cnname from lrbomitem where bomname='"+bomtem+"'";
			//prompt("",fitemsql);
			//var tem = easyQueryVer3(fitemsql,1,1,1);
		var sqlid1="bomAddInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName('ibrms.bomAddInputSql'); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		
		mySql1.addSubPara(fBOM);
	  var strSql=mySql1.getString();	
		var tem = easyQueryVer3(strSql,1,1,1);
			document.all('fatherItem').CodeData = tem;
		}
	}catch(ex){
		
	}
}
// �Ը�BOM�ֶν��г�ʼ��
function fbomonload(){
	document.all('fatherItem').value = "";	
	var fBOM = document.all("fBOM").value;

	
	
	var fbomsql;
	if(fBOM==''){
		//fbomsql = "select name,cnname from (select * from lrbomitem where 1=2)";
		document.all('fatherItem').CodeData='';
	}else{
		//fbomsql = "select name,cnname from (select * from lrbomitem where bomname='"+fBOM+"')";
	
		//prompt("",fbomsql);
			var sqlid1="bomAddInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName('ibrms.bomAddInputSql'); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		
		mySql1.addSubPara(fBOM);
	  var strSql=mySql1.getString();	
		var tem = easyQueryVer3(strSql,1,1,1);
		if(tem==false){
			document.all('fatherItem').CodeData=''
		}else{
			document.all('fatherItem').CodeData = tem;
		}
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
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="
				+ content;	
		if(confirm("���BOM����ɹ�,�Ƿ�������?")){
			document.location.href="bomAddInput.jsp";		
		}else{
			returnParent();			
		}	
	}
	
}

// ���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm() {
	try {
		initForm();
	} catch (re) {
		alert("��bomAddInput.js-->resetForm�����з����쳣:��ʼ���������!");
	}
}

// ȡ����ť��Ӧ����
function cancelForm() {
	// window.location="../common/html/Blank.html";
}

// �ύǰ��У�顢����
function beforeSubmit() {
	// ���ǰУ��	
	document.all("Transact").value = "INSERT";
	if (document.all('eName').value.trim() == '') {
		alert("BOMӢ��������Ϊ��!");
		return false;
	}
	if (document.all('cName').value.trim() == '') {
		alert("BOM����������Ϊ��!");
		return false;
	}		
	if(document.all("bomLevel").value.trim()==''){
		alert("��β���Ϊ�գ�");
	    return false;
	}
	if (document.all('Business').value.trim()== ''){
		alert("ҵ��ģ�鲻��Ϊ�գ�");
		return false;
	}
	return true;
}

function eNameCheck(Filed){	
	var patrn=/^[a-zA-Z0-9]{0,20}$/;
	if (!patrn.test(Filed.value.trim())) {
		alert("��ʽ����,ֻ��ΪӢ�ĺ�����");
	document.all("eName").value = "";	
	}
}

function cNameCheck(Filed){	
	var patrn=/^[\u4e00-\u9fa5]{0,30}$/;
	if (!patrn.test(Filed.value.trim())) {
		alert("��ʽ����,ֻ��Ϊ����");
	document.all("cName").value = "";	
	}
}

function bomLevelCheck(Field){
	var patrn=/^[0-9]\d*$/;
	if (!patrn.test(Field.value.trim())) {
		alert("��ʽ����,BOM���ֻ��Ϊ����");
	    document.all("bomLevel").value = "";
	    return "0";
	}
	
}