//�ύ�����水ť��Ӧ����
function submitForm() {
	if(!verifyInput())return false;
    fm.action="./BankPayListSave.jsp";
    fm.target = "f1print";
    document.getElementById("fm").submit(); //�ύ
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
