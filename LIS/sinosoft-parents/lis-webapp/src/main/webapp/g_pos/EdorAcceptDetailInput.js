/***************************************************************
 * <p>ProName��EdorAcceptDetailInput.js</p>
 * <p>Title����ȫ����</p>
 * <p>Description����ȫ����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-06-12
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ������Ϣ��ѯ
 */
function queryEdorAppInfo() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorAcceptSql");
	tSQLInfo.setSqlId("EdorAcceptSql1");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm.PolicyNo.value = strQueryResult[0][0];
		fm.HidGrpContNo.value = strQueryResult[0][0];
		fm.AppntName.value = strQueryResult[0][1];
		
		if (strQueryResult[0][4]!=null && strQueryResult[0][4]!="") {
			
			fm.ConfButton.style.display = "none";
			divEdorAppDetailInfo.style.display = "";
			
			fm.AppDate.value = strQueryResult[0][2];
			fm.ReceiveDate.value = strQueryResult[0][3];
			fm.AppMode.value = strQueryResult[0][4];
			fm.AppModeName.value = strQueryResult[0][5];
		} else {
			
			fm.ConfButton.style.display = "";
			divEdorAppDetailInfo.style.display = "none";
			
			fm.AppDate.value = tCurrentDate;
			fm.ReceiveDate.value =tCurrentDate;
			fm.AppMode.value = "";
			fm.AppModeName.value = "";
		}
	}
}

/**
 * ������Ϣ��ѯ
 */
function queryPolicyInfo() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorAcceptSql");
	tSQLInfo.setSqlId("EdorAcceptSql2");
	tSQLInfo.addSubPara(tEdorAppNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm.ValDate.value = strQueryResult[0][0];
		fm.InitNumPeople.value = strQueryResult[0][1];
		fm.InitPrem.value = strQueryResult[0][2];
		fm.EndDate.value = strQueryResult[0][3];
		fm.NumPeople.value = strQueryResult[0][4];
		fm.Prem.value = strQueryResult[0][5];
		fm.ContState.value = strQueryResult[0][6];
		fm.EdorTimes.value = strQueryResult[0][7];
		fm.PayIntv.value = strQueryResult[0][8];
		fm.BanlanceFlag.value = strQueryResult[0][9];
		fm.AfterClmRule.value = strQueryResult[0][10];
		fm.InsuAccFlag.value = strQueryResult[0][11];
	}
}

/**
 * ��ȫ��Ŀ��ѯ
 */
function queryEdorTypeInfo() {
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorAcceptSql");
	tSQLInfo.setSqlId("EdorAcceptSql3");
	tSQLInfo.addSubPara(tEdorAppNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), EdorTypeGrid, 1, 1);
}

/**
 * ����������
 */
function confClick() {
	
	fm.Operate.value = "RELATE";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorAcceptDetailSave.jsp";
	submitForm(fm);
}

/**
 * ��ӱ�ȫ��Ŀ
 */
function addEdorType() {
	
	fm.Operate.value = "ADDEDORTYPE";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorAcceptDetailSave.jsp";
	submitForm(fm);
}

/**
 * ɾ����ȫ��Ŀ
 */
function delEdorType() {
	
	var tSelNo = EdorTypeGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫɾ���ı�ȫ��Ŀ��");
		return false;
	}
	
	fm.DelEdorType.value = EdorTypeGrid.getRowColData(tSelNo-1, 1);
	fm.Operate.value = "DELEDORTYPE";
	if (!beforeSubmit()) {
		return false;
	}
	
	fm.action = "./EdorAcceptDetailSave.jsp";
	submitForm(fm);
}

/**
 * չʾ��ȫ��Ŀ��Ϣ
 */
function showEdorType() {
	
	var tSelNo = EdorTypeGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ����Ҫɾ���ı�ȫ��Ŀ��");
		return false;
	}
	
	fm.EdorType.value = EdorTypeGrid.getRowColData(tSelNo-1, 1);
	fm.EdorTypeName.value = EdorTypeGrid.getRowColData(tSelNo-1, 2);
	fm.EdorValDate.value = EdorTypeGrid.getRowColData(tSelNo-1, 4);
	
	var tEdorLevel = EdorTypeGrid.getRowColData(tSelNo-1, 5);
	var tValDateFlag = EdorTypeGrid.getRowColData(tSelNo-1, 7);
	
	fm.GetObj.value = EdorTypeGrid.getRowColData(tSelNo-1, 8);
	fm.GetObjName.value = EdorTypeGrid.getRowColData(tSelNo-1, 9);
	
	if (tEdorLevel=="1" || (tEdorLevel=="0" && tValDateFlag=="0")) {
		
		divEdorValDateTitle.style.display = "none";
		divEdorValDateInput.style.display = "none";
		fm.EdorValDate.value = "";
		
		//�˻��Ͳ�Ʒ����չʾ֧����ʽ
		if (fm.EdorType.value=="ZT" && fm.InsuAccFlag.value=="1") {
			
			divGetObjTitle.style.display = "";
			divGetObjInput.style.display = "";
			
			divTD1.style.display = "none";
			divTD2.style.display = "none";
		} else {
			
			divGetObjTitle.style.display = "none";
			divGetObjInput.style.display = "none";
			fm.GetObj.value = "";
			fm.GetObjName.value = "";
			
			divTD1.style.display = "";
			divTD2.style.display = "";
		}
	} else {
		
		divEdorValDateTitle.style.display = "";
		divEdorValDateInput.style.display = "";
		
		divGetObjTitle.style.display = "none";
		divGetObjInput.style.display = "none";
		
		divTD1.style.display = "none";
		divTD2.style.display = "none";
	}
}

/**
 * ��ȫ��Ŀ��ϸ
 */
function detailClick() {
	
	var tSelNo = EdorTypeGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ����ȫ��Ŀ��");
		return false;
	}
	
	var tGrpContNo = fm.HidGrpContNo.value;
	var tEdorType = EdorTypeGrid.getRowColData(tSelNo-1, 1);
	var tEdorActivityID = EdorTypeGrid.getRowColData(tSelNo-1, 6);
	
	if (tActivityID!=tEdorActivityID) {
		alert("�ñ�ȫ��Ŀ���ڱ�ȫ�������¼�룡");
		return false;
	}
	
	window.open("./EdorTypeDetailMain.jsp?MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&EdorType="+tEdorType,null , 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * �������
 */
function acceptClick() {
	
	fm.Operate.value = "ACCEPT";
	fm.action = "./EdorAcceptDetailSave.jsp";
	submitForm(fm);
}

/**
 * �ύ����
 */
function submitForm(obj) {

	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
}

/**
 * �ύ�����,���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		initForm();
		
		if("ACCEPT"==fm.Operate.value){
			returnClick();
		}
	}
}

/**
 * �ύǰ��У�顢����
 */
function beforeSubmit() {
	
	//ϵͳ��У�鷽��
	if (!verifyInput2()) {
		return false;
	}
	
	if (fm.Operate.value=="RELATE") {
		
//		if (fm.PolicyNo.value=="") {
//			alert("�����Ų���Ϊ�գ�");
//			return false;
//		}
		
		if (fm.AppDate.value=="") {
			alert("�ͻ��������ڲ���Ϊ�գ�");
			return false;
		}
		
		if (fm.ReceiveDate.value=="") {
			alert("��˾�������ڲ���Ϊ�գ�");
			return false;
		}
		if (fm.AppDate.value>fm.ReceiveDate.value) {
			alert("��˾�������ڲ������ڿͻ��������ڣ�");
			return false;
		}
		if (fm.ReceiveDate.value>tCurrentDate) {
			alert("��˾�������ڲ������ڵ�ǰ���ڣ�");
			return false;
		}
		
		if (fm.AppMode.value=="") {
			alert("���뷽ʽ����Ϊ�գ�");
			return false;
		}
	} else if (fm.Operate.value=="ADDEDORTYPE") {
		
		if (fm.EdorType.value=="") {
			alert("����ѡ��ȫ��Ŀ��");
			return false;
		}
		
		if (divEdorValDateInput.style.display=="" && fm.EdorValDate.value=="") {
			alert("��ȫ��Ч���ڲ���Ϊ�գ�");
			return false;
		}
	}
	
	return true;
}

/**
 * ��������
 */
function afterCodeSelect(cCodeType, Field) {
	
	if (cCodeType=="edorcode") {
		
		if (fm.HidEdorLevel.value=="1" || (fm.HidEdorLevel.value=="0" && fm.HidValDateFlag.value=="0")) {
		
			divEdorValDateTitle.style.display = "none";
			divEdorValDateInput.style.display = "none";
			fm.EdorValDate.value = "";
			
			//�˻��Ͳ�Ʒ����Ĭ�϶Թ�֧��
			if (Field.value=="ZT" && fm.InsuAccFlag.value=="1") {
				
				divGetObjTitle.style.display = "";
				divGetObjInput.style.display = "";
				fm.GetObj.value = "00";
				fm.GetObjName.value = "�Թ�";
				
				divTD1.style.display = "none";
				divTD2.style.display = "none";
			} else {
				
				divGetObjTitle.style.display = "none";
				divGetObjInput.style.display = "none";
				fm.GetObj.value = "";
				fm.GetObjName.value = "";
				
				divTD1.style.display = "";
				divTD2.style.display = "";
			}
		} else {
			
			divEdorValDateTitle.style.display = "";
			divEdorValDateInput.style.display = "";
			fm.EdorValDate.value = "";
			
			divGetObjTitle.style.display = "none";
			divGetObjInput.style.display = "none";
			fm.GetObj.value = "";
			fm.GetObjName.value = "";
			
			divTD1.style.display = "none";
			divTD2.style.display = "none";
		}
	}
}