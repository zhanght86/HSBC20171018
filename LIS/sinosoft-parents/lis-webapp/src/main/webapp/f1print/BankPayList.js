//提交，保存按钮对应操作
function submitForm() {
	if(!verifyInput())return false;
    fm.action="./BankPayListSave.jsp";
    fm.target = "f1print";
    document.getElementById("fm").submit(); //提交
}

function afterCodeSelect(){
initApp();
}

function initApp(){
	if(fm.ReportType.value=="1")
		fm.App.disabled=false;
	else
		fm.App.disabled=true;
}
