/***************************************************************
 * <p>ProName��PreCustomerUnlockInput.js</p>
 * <p>Title��׼�ͻ���������</p>
 * <p>Description��׼�ͻ���������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-03-17
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ѯ���ͻ�����
 */
function queryAgent(cObj, cObjName) {
	
	var keyCode = event.keyCode;
	if(keyCode=="13"||keyCode=="9") {
		
		window.event.keyCode = 0;
		if (cObjName.value==null || cObjName.value=="") {
			alert("���ͻ��������Ʋ���Ϊ�գ�");
			return false;
		}
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_customer.PreCustomerSql");
		tSQLInfo.setSqlId("PreCustomerSql12");
		tSQLInfo.addSubPara(cObjName.value);
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr == null) {
			alert("�����ڸÿͻ�����");
			cObj.value = "";
			cObjName.value = "";
			return false;
		} else {
			if (tArr.length == 1) {
				cObj.value = tArr[0][0];
				cObjName.value = tArr[0][1];
			} else {
				showCodeList('agentcode',[cObj, cObjName],[0, 1],null,"1 and agentname like #%%"+cObjName.value+"%%#","1",'1',null);
			}
		}
	}
}

/**
 * ����
 */
function unlockClick() {
	
	if (!confirm("��������ɾ����׼�ͻ���Ϣ���Ƿ�ȷ�ϣ�")) {
		return false;
	}
	mOperate = "UNLOCK";
	submitForm();
}

/**
 * �ͻ�������Ϣ�޸�
 */
function modifyClick() {
	
	mOperate = "UPDATE";
	if (!beforeSubmit()) {
		return false;
	}
	submitForm();
}

/*
*	����
*/
function returnBack() {
	
	if (isTraceFlag==0) {
		
		window.location="./PreCustomerQueryInput.jsp?Flag=2";
	} else if (isTraceFlag==1) {
		
		window.open("./PreCustomerTraceQueryMain.jsp?PreCustomerNo="+tPreCustomerNo,"�޸Ĺ켣��ѯ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}

/**
 * �ύ
 */
function submitForm() {
	
	var i = 0;
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.Operate.value = mOperate;
	fm.submit();
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
		//window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
		//window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	if (mOperate == "UNLOCK") {
		window.location = "./PreCustomerQueryInput.jsp?Flag=2";
	}
}

/**
 * ��ѯ׼�ͻ���ϸ��Ϣ
 */
function queryDetail() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	
	if (isTraceFlag==0) {
		
		tSQLInfo.setSqlId("PreCustomerSql2");
		tSQLInfo.addSubPara(tPreCustomerNo);
	} else if (isTraceFlag==1) {
		
		tSQLInfo.setSqlId("PreCustomerSql7");
		tSQLInfo.addSubPara(tTraceID);
	}
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		fm.PreCustomerNo.value = strQueryResult[0][0];
		fm.PreCustomerName.value = strQueryResult[0][1];
		fm.IDTypeName.value = strQueryResult[0][3];
		fm.IDNo.value = strQueryResult[0][4];
		fm.GrpNatureName.value = strQueryResult[0][6];
		fm.BusiCategoryName.value = strQueryResult[0][8];
		fm.SumNumPeople.value = strQueryResult[0][9];
		fm.PreSumPeople.value = strQueryResult[0][10];
		fm.PreSumPrem.value = strQueryResult[0][11];
		fm.UpCustomerName.value = strQueryResult[0][13];
		fm.SaleChannelName.value = strQueryResult[0][15];
		
		fm.DetailAddress.value = strQueryResult[0][17]+strQueryResult[0][19]+strQueryResult[0][21]+strQueryResult[0][22];
		fm.CustomerIntro.value = strQueryResult[0][23];
	}
	
	//��ѯ��Ҫ��ϵ����Ϣ
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	
	if (isTraceFlag==0) {
		
		tSQLInfo.setSqlId("PreCustomerSql3");
		tSQLInfo.addSubPara(tPreCustomerNo);
	} else if (isTraceFlag==1) {
		
		tSQLInfo.setSqlId("PreCustomerSql8");
		tSQLInfo.addSubPara(tTraceID);
	}
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm.LinkMan.value = strQueryResult[0][0];
		fm.Mobile.value = strQueryResult[0][1];
		fm.Phone.value = strQueryResult[0][2];
		fm.Depart.value = strQueryResult[0][3];
		fm.Post.value = strQueryResult[0][4];
		fm.Email.value = strQueryResult[0][5];
	}
	
	//��ѯ���ͻ�������Ϣ
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	tSQLInfo.setSqlId("PreCustomerSql4");
	tSQLInfo.addSubPara(tPreCustomerNo);
	
	var strQueryResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (strQueryResult != null) {
		
		fm.SalerCode.value = strQueryResult[0][0];
		fm.SalerName.value = strQueryResult[0][1];
	}
	
	//��ѯ�����ͻ�������Ϣ
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.PreCustomerSql");
	
	if (isTraceFlag==0) {
		
		tSQLInfo.setSqlId("PreCustomerSql5");
		tSQLInfo.addSubPara(tPreCustomerNo);
	} else if (isTraceFlag==1) {
		
		tSQLInfo.setSqlId("PreCustomerSql9");
		tSQLInfo.addSubPara(tTraceID);
	}
	
	turnPage1.queryModal(tSQLInfo.getString(), SalerGrid, 0, 1);
	
	//�켣��ϸ��Ϣ��ѯʱ�����û�и����ͻ�������Ϣ����������
	if (SalerGrid.mulLineCount==0) {
		if (isTraceFlag==1) {
			divSaler.style.display = "none";
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
	
	if (mOperate=="UPDATE") {
		
		for(var i=0;i < SalerGrid.mulLineCount;i++){
				
			var tSalerCode = SalerGrid.getRowColData(i,1);
			if(tSalerCode == null || tSalerCode.trim() == "") {
				
				alert("��["+(i+1)+"]�пͻ�������벻��Ϊ�գ�");
				return false;
			}
			
			var tMainSalerCode = fm.SalerCode.value;
			if (tMainSalerCode==tSalerCode) {
				alert("��["+(i+1)+"]�пͻ�������벻�������ͻ����������ͬ��");
				return false;
			}
				
			//��Ա�ظ���У��
			for(var j=0;j < i;j++){
				
				var mSalerCode = SalerGrid.getRowColData(j,1);
				if (mSalerCode==tSalerCode) {
					alert("��["+(i+1)+"]�пͻ�����������["+(j+1)+"]���ظ���");
					return false;
				}
			}
		}
	}
	return true;
}
//��ѯ�ͻ�����
function queryManager() {
	
	var tSelNo = SalerGrid.lastFocusRowNo;//�кŴ�0��ʼ
	var strUrl = "PreCustomerManagerQueryMain.jsp?SelNo="+tSelNo;
	window.open(strUrl,"�ͻ������ѯ",'height=600,width=900,left=0,top=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}
//��ѯ���ͻ�����
function queryManagerInfo() {
	
	var strUrl = "MainManagerQueryMain.jsp";
	window.open(strUrl,"���ͻ������ѯ",'height=600,width=900,left=0,top=0,toolbar=no,menubar=no,scrollbars=no, resizable=yes,location=no, status=no');
}
