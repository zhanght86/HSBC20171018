
function submitForm()
{  
	fm.oldPwd.value = fm.oldPwd.value.trim();
    if(fm.oldPwd.value.length == 0){
		alert("����д������.");
		return false;
	}
	
	var newPwd = fm.newPwd.value.trim();
	var confirmPwd = fm.confirmPwd.value.trim();
	if (newPwd == "") {
	    alert("������������.");
	    return false;
	}
	fm.newPwd.value = newPwd;
	if (confirmPwd == "") {
	    alert("������������.");
	    return false;
	}
	fm.confirmPwd.value = confirmPwd;
  // zy 2009-04-10 17:28 ��������У��
	if (newPwd.length < 8) {
	    alert("���볤����СΪ8λ����������������.");
	    return false;
	}
	if (newPwd == fm.oldPwd.value) {
	    alert("�����벻�����������ͬ������������.");
	    return false;
	}
	
	if (newPwd != confirmPwd){
		alert("��������ȷ���������벻һ��.");
		fm.confirmPwd.value = "";
		fm.newPwd.value = "";
		return false;
	}	
	var patrn=/^(([0-9]+([a-zA-Z]+[0-9]*)+)|([a-zA-Z]+([0-9]+[a-zA-Z]*)+))$/;  //��������λ��������ĸ�����
	//var patrn=/^(?=.*[a-zA-Z])(?=.*?\d)(?=.*?[#@*&$%^!()~`]).*$/;
	if (!patrn.exec(newPwd)) {
		alert("����ֻ��Ϊ��ĸ�����ֵ����");
		return false ;
	}
	
	//����
	fm.oldPwd.value = encrypt(fm.oldPwd.value.trim());
	fm.newPwd.value = encrypt(fm.newPwd.value.trim());
	fm.confirmPwd.value = encrypt(fm.confirmPwd.value.trim());
	//prompt('1',fm.newPwd.value+":"+newPwd);
	fm.submit();
	return true;
}


//function afterSubmit(FlagStr) 
//{
//	if (FlagStr == "false") {
//		alert("�������ʧ�ܣ����ܵ�ԭ����ԭ������������");
//	} else {
//		alert("������³ɹ���");		
//	}
//}
function afterSubmit( FlagStr, content )
{    
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.oldPwd.value = "";
	fm.confirmPwd.value = "";
	fm.newPwd.value = "";
}
function resetForm()
{
	fm.oldPwd.value = "";
	fm.newPwd.value = "";
	fm.confirmPwd.value = "";
}