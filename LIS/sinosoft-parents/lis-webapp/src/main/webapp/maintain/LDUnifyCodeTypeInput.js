//-------------------------------------------------
//�������ƣ�LDUnifyCodeTypeInput.js
//�����ܣ�ϵͳͳһ����ά��
//�������ڣ�2012-01-01
//������  ��������
//�޸���  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
//-------------------------------------------------
var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var mySql = new SqlClass();
/**
 * �ύ�����水ť��Ӧ����
 */
function submitForm() {
	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.fmtransact.value=mOperate;
	fm.submit(); //�ύ
}

/**
 * �ύ�����,���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr, content) {
	if(typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}else {
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		if(mOperate=="UPDATE&TYPE"||mOperate=="INSERT&TYPE"||mOperate=="SUBMIT&TYPE"){
			fm.State.value="";
			fm.StateName.value="";
			queryClick();
			resetClick1();
		}else if(mOperate=="DELETE&TYPE") {
			resetClick();
			resetClick1();
		}else if(mOperate=="UPDATE"||mOperate=="SUBMIT") {
			queryClick1();
		}else if(mOperate=="INSERT"){
			queryClick1();
		}else if(mOperate=="DELETE") {
			initCodeGrid();
			//resetClick1();
			//showCodeTypeInfo();
			//��մ�����Ϣ
			fm.State1.value="";
			fm.State1Name.value="";
			fm.Code.value="";
			fm.CodeName.value="";
			fm.CodeAlias.value="";
			fm.Code.readOnly = false;	
		}
	}
}

/**
 * �ύǰ��У�顢����
 */
function beforeCodeTypeSubmit() {
	
	//ϵͳ��У�鷽��
	if(!verifyInput2()) {
		return false;
	}
	//��Ϊ�յ�У��
	
	var tSysCode = fm.SysCode.value.trim();
	var tCodeType = fm.CodeType.value.trim();
	var tCodeTypeName = fm.CodeTypeName.value.trim();
	var tMaintainFlag = fm.MaintainFlag.value.trim();
	
	if(tSysCode==null||tSysCode=="") {
		alert("ϵͳ���Ʋ���Ϊ�գ����������룡");
		fm.SysCode.focus();
		return false;
	}
	
	if(tCodeType==null||tCodeType=="") {
		alert("�������Ͳ���Ϊ�գ����������룡");
		fm.CodeType.value="";
		fm.CodeType.focus();
		return false;
	}
	
	if(tCodeTypeName==null||tCodeTypeName=="") {
		alert("�����������Ʋ���Ϊ�գ����������룡");
		fm.CodeTypeName.value = "";
		fm.CodeTypeName.focus();
		return false;
	}
	
	if(tMaintainFlag==null||tMaintainFlag=="") {
		alert("ά����ʶ����Ϊ�գ����������룡");
		fm.MaintainFlag.focus();
		return false;
	}
	//����ʱ�������Ͳ����ظ�
	if(mOperate=="INSERT&TYPE") {
		mySql = new SqlClass();
		mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
		mySql.setSqlId("LDUnifyCodeTypeSql1");
		mySql.addSubPara(trim(tSysCode));   
		mySql.addSubPara(trim(tCodeType));   
		var strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);			
		if (strQueryResult) {  	
			alert('�ñ��������Ѵ���,���������룡');
			fm.CodeType.focus();
			return false;
		}
	}
	
	return true;
}

/**
 * Click�¼������������ͼƬʱ�����ú���
 */
function addClick() {
	
	mOperate = "INSERT&TYPE";
	if(!beforeCodeTypeSubmit()) {
		return false;
	}
	submitForm();
}

/**
 * Click�¼�����������޸ġ�ͼƬʱ�����ú���
 */
function updateClick() {
	var tSelNo = CodeTypeInfoGrid.getSelNo()-1;
	if(tSelNo<0) {
		alert("��ѡ��һ������������Ϣ");
		return false;
	}
	var tState = CodeTypeInfoGrid.getRowColData(tSelNo,11);
	if(tState=="1") {
		alert("���������Ѿ���Ч������ִ�иò���");
		return false;
		}
	mOperate = "UPDATE&TYPE";
	if(!beforeCodeTypeSubmit()) {
		return false;
	}
	submitForm();
}
/**
 * Click�¼�����������ύ��ͼƬʱ�����ú���

 */
function submitClick() {
	var tSelNo = CodeTypeInfoGrid.getSelNo()-1;
	if(tSelNo<0) {
		alert("��ѡ��һ������������Ϣ");
		return false;
	}
	
	var tState = CodeTypeInfoGrid.getRowColData(tSelNo,11);
	if(tState=="1") {
		alert("���������Ѿ���Ч������ִ�иò���");
		return false;
	}
			mySql = new SqlClass();
		mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
		mySql.setSqlId("LDUnifyCodeTypeSql4");
		mySql.addSubPara(trim(fm.SysCode.value));   
		mySql.addSubPara(trim(fm.CodeType.value));   
		var strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);			
		if (!strQueryResult) {  	
			alert('�ô���������δά�����벻���ύ,��������ı�����Ϣ��ά����');
			return false;
		}
	
	mOperate = "SUBMIT&TYPE";
	submitForm();
}

/**
 * Click�¼������������ѯ��ͼƬʱ�����ú���
 */
function queryClick() {
	 
	mySql = new SqlClass();
	mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
	mySql.setSqlId("LDUnifyCodeTypeSql2");
	mySql.addSubPara(trim(fm.SysCode.value));
	mySql.addSubPara(trim(fm.CodeType.value));
	mySql.addSubPara(trim(fm.CodeTypeName.value));
	mySql.addSubPara(trim(fm.MaintainFlag.value));
	mySql.addSubPara(trim(fm.State.value));
	mySql.addSubPara(trim(fm.CodeTypeDescription.value));
	
	turnPage1.queryModal(mySql.getString(), CodeTypeInfoGrid);	
	fm.CodeType.readOnly = false;
	if(!turnPage1.strQueryResult){
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * Click�¼����������ɾ����ͼƬʱ�����ú���
 */
function deleteClick() {
	
	var tSelNo = CodeTypeInfoGrid.getSelNo()-1;
	if(tSelNo<0) {
		alert("��ѡ��һ������������Ϣ");
		return false;
	}
	
	var tState = CodeTypeInfoGrid.getRowColData(tSelNo,11);
	if(tState=="1") {
		alert("���������Ѿ���Ч������ִ�иò���");
		return false;
	}
	if (!confirm("ȷ��Ҫɾ������������Ϣ��")) {
		return false;
	}
	mOperate = "DELETE&TYPE";
	submitForm();
}

/**
 * Click�¼�������������á�ͼƬʱ�����ú���
 */
function resetClick() {
	initCodeTypeInfo();
	initCodeTypeGrid();
}

/**
 * չʾ����������Ϣ
 */
function showCodeTypeInfo() {
	var tSelNo = CodeTypeInfoGrid.getSelNo()-1;
	fm.SysCode.value= CodeTypeInfoGrid.getRowColData(tSelNo,9);
	
	fm.SysCodeName.value=CodeTypeInfoGrid.getRowColData(tSelNo,1);
	fm.CodeType.value=CodeTypeInfoGrid.getRowColData(tSelNo,2);
	fm.CodeTypeName.value=CodeTypeInfoGrid.getRowColData(tSelNo,3);
	fm.MaintainFlag.value=CodeTypeInfoGrid.getRowColData(tSelNo,10);
	fm.MaintainFlagName.value=CodeTypeInfoGrid.getRowColData(tSelNo,4);
	fm.State.value=CodeTypeInfoGrid.getRowColData(tSelNo,11);
	fm.StateName.value=CodeTypeInfoGrid.getRowColData(tSelNo,5);
	fm.CodeTypeDescription.value=CodeTypeInfoGrid.getRowColData(tSelNo,6);
	fm.SysCodeReadOnly.value = CodeTypeInfoGrid.getRowColData(tSelNo,9);
	fm.SysCodeReadOnlyName.value = CodeTypeInfoGrid.getRowColData(tSelNo,1);
	divSysCodeReadOnly.style.display = "";
	divSysCode.style.display="none";
	fm.CodeType.readOnly = true;
	//��Ӧ���������ѯ
	divSysCodeReadOnly1.style.display = "none";
	divCodeTypeReadOnly1.style.display = "none";
	divSysCode1.style.display = "";
	divCodeType1.style.display = "";
	fm.SysCode1.value= CodeTypeInfoGrid.getRowColData(tSelNo,9);
	fm.SysCode1Name.value= CodeTypeInfoGrid.getRowColData(tSelNo,1);
	fm.CodeType1.value= CodeTypeInfoGrid.getRowColData(tSelNo,2);
	fm.CodeType1Name.value= CodeTypeInfoGrid.getRowColData(tSelNo,3);
	fm.State1.value="";
	fm.State1Name.value="";
	fm.Code.value="";
	fm.CodeName.value="";
	fm.CodeAlias.value="";
	//��ѯ����
	initCodeQueryInfoByCodeType();
	fm.Code.readOnly = false;

}

/**
 * ��ʼ����ѯ��������
 */
function initCodeTypeQueryInfo() {
		mySql = new SqlClass();
		mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
		mySql.setSqlId("LDUnifyCodeTypeSql3");
		mySql.addSubPara("");
		turnPage1.queryModal(mySql.getString(), CodeTypeInfoGrid);	
	}

/**
 * -----------------------������Ϣά��js------------------------------------------------------------
 *--------------------------------------------------------------------------------------------------------
 */




/**
 * �ύǰ��У�顢����
 */
function beforeSubmit() {
	
	//ϵͳ��У�鷽��
	if(!verifyInput2()) {
		return false;
	}
	
	//��Ϊ�յ�У��
	var tSysCode = fm.SysCode1.value;
	var tCodeType = fm.CodeType1.value;
	var tCode = fm.Code.value;
	var tCodeName = fm.CodeName.value;
	
	if(tSysCode==null||tSysCode=="") {
		alert("ϵͳ���Ʋ���Ϊ�գ����������룡");
		fm.SysCode1.focus();
		return false;
	}
	
	if(tCodeType==null||tCodeType=="") {
		alert("�������Ͳ���Ϊ�գ����������룡");
		fm.CodeType1.focus();
		return false;
	}
	
	if(tCode==null||tCode=="") {
		alert("���벻��Ϊ�գ����������룡");
		fm.Code.focus();
		return false;
	}
	
	if(tCodeName==null||tCodeName=="") {
		alert("�������Ʋ���Ϊ�գ����������룡");
		fm.CodeName.focus();
		return false;
	}
	//�������벻���ظ�
	if(mOperate=="INSERT") {
		mySql = new SqlClass();
		mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
		mySql.setSqlId("LDUnifyCodeSql1");
		mySql.addSubPara(trim(tSysCode));   
		mySql.addSubPara(trim(tCodeType));   
		mySql.addSubPara(trim(tCode)); 
		var strQueryResult  = easyQueryVer3(mySql.getString(), 1, 1, 1);			
		if (strQueryResult) {  	
			alert('�ñ�����Ϣ�Ѵ���,���������룡');
			fm.Code.focus();
			return false;
		}
	}
	
	return true;
}

/**
 * Click�¼������������ͼƬʱ�����ú���
 */
function addClick1() {
	
	mOperate = "INSERT";
	if(!beforeSubmit()) {
		return false;
	}
	//�ж��Ƿ������ϵı�������
	mySql = new SqlClass();
	mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
	mySql.setSqlId("LDUnifyCodeSql4");
	mySql.addSubPara(trim(fm.SysCode1.value));   
	mySql.addSubPara(trim(fm.CodeType1.value));   
	
	var arr = easyExecSql(mySql.getString());
	if (arr == null) {
		alert('�����ڶ�Ӧ�ı�������,�뵽����������ά���ñ������ͣ�');
		return false;
	}
	var tState = arr[0][0];
	var tMaintainflag = arr[0][1];
	if(tState=="1"&&tMaintainflag=="0") {
			alert("ѡ��ı������ͣ�����Ч���Ҳ���ά������������������Ϣ");
			return false;
		}
	submitForm();
	cleartDate();//�������
}

/**
 * Click�¼�����������޸ġ�ͼƬʱ�����ú���
 */
function updateClick1() {
	var tSelNo = CodeInfoGrid.getSelNo()-1;
	if(tSelNo<0) {
		alert("��ѡ��һ��������Ϣ");
		return false;
	}
	var tState = CodeInfoGrid.getRowColData(tSelNo,11);
	if(tState=="1") {
		alert("�����Ѿ���Ч�����ܽ���ִ�иò���");
		return false;
		}
	mOperate = "UPDATE";
	if(!beforeSubmit()) {
		return false;
	}
	fm.Code.readOnly = false;
	fm.State1.value="";
	fm.State1Name.value="";
	submitForm();
	cleartDate();//�������
}
/**
 * Click�¼�����������ύ��ͼƬʱ�����ú���

 */
function submitClick1() {
	var tSelNo = CodeInfoGrid.getSelNo()-1;
	if(tSelNo<0) {
		alert("��ѡ��һ��������Ϣ");
		return false;
	}
	
	var tState = CodeInfoGrid.getRowColData(tSelNo,11);
	if(tState=="1") {
		alert("�����Ѿ���Ч������ִ�иò���");
		return false;
	}
		//�жϱ��������Ƿ�����Ч
	mySql = new SqlClass();
	mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
	mySql.setSqlId("LDUnifyCodeTypeSql1");
	mySql.addSubPara(trim(fm.SysCode1.value));   
	mySql.addSubPara(trim(fm.CodeType1.value));   
	
	var arr = easyExecSql(mySql.getString());
	var tState = arr[0][0];
	if(tState=="0") {
			alter("ѡ��ı�������δ��Ч�����ܵ����ύ���룬���ύ��������");
			return false;
		}
	mOperate = "SUBMIT";
	submitForm();
}

/**
 * Click�¼������������ѯ��ͼƬʱ�����ú���
 */
function queryClick1() {
	 
	mySql = new SqlClass();
	mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
	mySql.setSqlId("LDUnifyCodeSql3");
	mySql.addSubPara(trim(fm.SysCode1.value));
	mySql.addSubPara(trim(fm.CodeType1.value));
	mySql.addSubPara(trim(fm.State1.value));
	mySql.addSubPara(trim(fm.Code.value));
	mySql.addSubPara(trim(fm.CodeName.value));
	mySql.addSubPara(trim(fm.CodeAlias.value));
	turnPage2.queryModal(mySql.getString(), CodeInfoGrid);	
	fm.Code.readOnly = false;
	if(!turnPage2.strQueryResult)
	{
		alert("δ��ѯ�����������ı�����Ϣ��");
	}
}

/**
 * Click�¼����������ɾ����ͼƬʱ�����ú���
 */
function deleteClick1() {
	
	var tSelNo = CodeInfoGrid.getSelNo()-1;
	if(tSelNo<0) {
		alert("��ѡ��һ��������Ϣ");
		return false;
	}
	
	var tState = CodeInfoGrid.getRowColData(tSelNo,11);
	if(tState=="1") {
		alert("�����Ѿ���Ч������ִ�иò���");
		return false;
	}
	if (!confirm("ȷ��Ҫɾ��������Ϣ��")) {
		return false;
	}
	mOperate = "DELETE";
	submitForm();
	cleartDate();//�������
}

/**
 * Click�¼�������������á�ͼƬʱ�����ú���
 */
function resetClick1() {
	initCodeInfo();
	initCodeGrid();
	fm.Code.readOnly = false;
}
//�������
function cleartDate(){
	fm.SysCodeReadOnly1.value="";
	fm.CodeTypeReadOnly1.value="";
	fm.SysCode1.value="";
	fm.CodeType1.value="";
	fm.SysCodeReadOnlyName1.value="";
	fm.SysCodeReadOnlyName1.value="";
	fm.CodeType1.value="";
	fm.CodeType1Name.value="";
	fm.CodeTypeReadOnlyName1.value="";
	fm.State1.value="";
	fm.State1Name.value="";
	fm.Code.value="";
	fm.CodeName.value="";
	fm.CodeAlias.value="";
}
/**
 * չʾ����������Ϣ
 */
function showCodeInfo() {
	var tSelNo = CodeInfoGrid.getSelNo()-1;
	fm.SysCode1.value= CodeInfoGrid.getRowColData(tSelNo,9);
	fm.SysCode1Name.value= CodeInfoGrid.getRowColData(tSelNo,1);
	fm.CodeType1.value= CodeInfoGrid.getRowColData(tSelNo,10);
	fm.CodeType1Name.value= CodeInfoGrid.getRowColData(tSelNo,2);
	fm.State1.value= CodeInfoGrid.getRowColData(tSelNo,11);
	fm.State1Name.value= CodeInfoGrid.getRowColData(tSelNo,6);
	fm.Code.value= CodeInfoGrid.getRowColData(tSelNo,3);
	fm.CodeName.value= CodeInfoGrid.getRowColData(tSelNo,4);
	fm.CodeAlias.value= CodeInfoGrid.getRowColData(tSelNo,5);
	//������Ϣ
	fm.SysCodeReadOnly1.value =  CodeInfoGrid.getRowColData(tSelNo,9);
	fm.SysCodeReadOnlyName1.value = CodeInfoGrid.getRowColData(tSelNo,1);
	fm.CodeTypeReadOnly1.value = CodeInfoGrid.getRowColData(tSelNo,10);
	fm.CodeTypeReadOnlyName1.value = CodeInfoGrid.getRowColData(tSelNo,2);
	divSysCodeReadOnly1.style.display = "";
	divCodeTypeReadOnly1.style.display = "";
	divSysCode1.style.display = "none";
	divCodeType1.style.display = "none";
	fm.Code.readOnly = true;
}

/**
 * ��ʼ����ѯ��������
 */
function initCodeQueryInfo() {
		mySql = new SqlClass();
		mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
		mySql.setSqlId("LDUnifyCodeSql2");
		mySql.addSubPara("");
		turnPage2.queryModal(mySql.getString(), CodeInfoGrid);	
	}
	
/**
 * ���ݱ������Ͳ�ѯ����
 */	
function initCodeQueryInfoByCodeType() {
	mySql = new SqlClass();
	mySql.setResourceName("maintain.LDUnifyCodeTypeSql");
	mySql.setSqlId("LDUnifyCodeSql3");
	mySql.addSubPara(trim(fm.SysCode1.value));
	mySql.addSubPara(trim(fm.CodeType1.value));
	mySql.addSubPara("");
	mySql.addSubPara("");
	mySql.addSubPara("");
	mySql.addSubPara("");
	turnPage2.queryModal(mySql.getString(), CodeInfoGrid);	
}