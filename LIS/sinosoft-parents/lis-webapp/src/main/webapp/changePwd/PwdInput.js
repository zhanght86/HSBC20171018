
function submitForm()
{  
	fm.oldPwd.value = fm.oldPwd.value.trim();
    if(fm.oldPwd.value.length == 0){
		alert("请填写旧密码.");
		return false;
	}
	
	var newPwd = fm.newPwd.value.trim();
	var confirmPwd = fm.confirmPwd.value.trim();
	if (newPwd == "") {
	    alert("请输入新密码.");
	    return false;
	}
	fm.newPwd.value = newPwd;
	if (confirmPwd == "") {
	    alert("请输入新密码.");
	    return false;
	}
	fm.confirmPwd.value = confirmPwd;
  // zy 2009-04-10 17:28 增加密码校验
	if (newPwd.length < 8) {
	    alert("密码长度最小为8位，请重新输入密码.");
	    return false;
	}
	if (newPwd == fm.oldPwd.value) {
	    alert("新密码不能与旧密码相同，请重新输入.");
	    return false;
	}
	
	if (newPwd != confirmPwd){
		alert("新密码与确认密码输入不一致.");
		fm.confirmPwd.value = "";
		fm.newPwd.value = "";
		return false;
	}	
	var patrn=/^(([0-9]+([a-zA-Z]+[0-9]*)+)|([a-zA-Z]+([0-9]+[a-zA-Z]*)+))$/;  //满足任意位数字与字母的组合
	//var patrn=/^(?=.*[a-zA-Z])(?=.*?\d)(?=.*?[#@*&$%^!()~`]).*$/;
	if (!patrn.exec(newPwd)) {
		alert("密码只能为字母和数字的组合");
		return false ;
	}
	
	//加密
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
//		alert("密码更改失败，可能的原因是原密码输入有误。");
//	} else {
//		alert("密码更新成功！");		
//	}
//}
function afterSubmit( FlagStr, content )
{    
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
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