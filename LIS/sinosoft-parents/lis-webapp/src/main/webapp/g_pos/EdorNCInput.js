/***************************************************************
 * <p>ProName��EdorNCInput.js</p>
 * <p>Title��������Ϣ¼��</p>
 * <p>Description��������Ϣ¼��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : JingDian
 * @version  : 8.0
 * @date     : 2014-07-16
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();//������Ϣ
var tSQLInfo = new SqlClass();
var mOperate = "";
var tTranPremMode;//���ѷ�̯��ʽ
var tContPlanType;

/**
* ���ؽ���Ҫ��
*/
function showPageInfo(){
	
	queryPlanInfo();
	pubInitContStep2(fm2, tContPlanType,"");
	
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
 * ��ʼ��������Ϣ��ѯ
 */
function queryPlanInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNCSql");
	tSQLInfo.setSqlId("EdorNCSql1");
	tSQLInfo.addSubPara(tPolicyNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	initPlanInfoGrid();
	turnPage1.queryModal(tSQLInfo.getString(), PlanInfoGrid, 1, 1);//����λ��ʾʹ�ô�������
}
/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	initButton();
	if (FlagStr=="Fail") {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		dealAfterSubmit();
	}	
}

function dealAfterSubmit() {
	var tOperate = fmPub.Operate.value;
	fmPub.Operate.value = "";
	if (tOperate=="ADDPLAN") {
		queryPlanInfo();
		pubInitContStep2(fm2, tContPlanType, tTranPremMode);
	} else if (tOperate=="MODIFYPLAN") {
		queryPlanInfo();
		pubInitContStep2(fm2, tContPlanType, tTranPremMode);
	} else if (tOperate=="DELPLAN") {
		queryPlanInfo();
		pubInitContStep2(fm2, tContPlanType, tTranPremMode);
	} else if (tOperate=="SAVEPLAN") {
		turnBack();
	} else if(tOperate=="PERIODSAVE"){
		
	}else {
		showPageInfo();
	}
}
/**
 * ѡ���¼����
 */
function showPlanInfo() {
	
	pubShowPlanInfo(fm2, tContPlanType);
}
/**
 * ��������
 */
function afterCodeSelect(cCodeType, Field) {
	
	var tCodeType = fmPub.HiddenCodeType.value;
	fmPub.HiddenCodeType.value = "";
	if (tCodeType=="premmode") {
				
			var tPremMode = fm2.PremMode.value;
			if (tPremMode=="1") {//��ҵ����
				
				fm2.EnterpriseRate.readOnly = true;
				fm2.EnterpriseRate.value = "1";
			} else if (tPremMode=="2") {//���˸���
			
				fm2.EnterpriseRate.readOnly = true;
				fm2.EnterpriseRate.value = "0";
			} else {
				
				fm2.EnterpriseRate.readOnly = false;
			}
		}
	if (cCodeType=="queryexp") {//ѡ���Զ�������
		
		if (tCodeType=="quotplantype") {
			
			pubPlanAfterCodeSelect(fm2, tCodeType, Field.value);
		} else if (tCodeType=="premmode") {
				
			var tPremMode = fm2.PremMode.value;
			if (tPremMode=="1") {//��ҵ����
				
				fm2.EnterpriseRate.readOnly = true;
				fm2.EnterpriseRate.value = "1";
			} else if (tPremMode=="2") {//���˸���
			
				fm2.EnterpriseRate.readOnly = true;
				fm2.EnterpriseRate.value = "0";
			} else {
				
				fm2.EnterpriseRate.readOnly = false;
			}
		}
	} else if (cCodeType=="engincaltype") {//ѡ�񱣷Ѽ��㷽ʽ
		
		pubPlanAfterCodeSelect(fm2, cCodeType, Field.value);
	} else	if (cCodeType=="occuptype") {
		
		if (tCodeType=="occuptype1") {
			
			fm2.OccupMidType.value = "";
			fm2.OccupMidTypeName.value = "";
			fm2.OccupCode.value = "";
			fm2.OccupCodeName.value = "";
		}
	}
}

/**
 * ���ӷ���
 */
function addPlan() {
 
	dealRedundant(fm2, tContPlanType);
	if (!verifyForm('fm2')) {
		return false;
	}
	
	if (!pubBeforeSubmit(fm2, tContPlanType)) {//�ύǰ�Ĺ���У��
		return false;
	}
	
	fmPub.Operate.value = "ADDPLAN";
	mOperate = "ADDPLAN";
	fm2.action = "./EdorNCSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&EdorNo="+tEdorNo+"&PolicyNo="+tPolicyNo+ "&ContPlanType="+ tContPlanType + "&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitForm(fm2);
}


/**
 * �޸ķ���
 */
function modifyPlan() {
	
	var tSelNo = PlanInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
	
		alert("��ѡ��Ҫ�޸ĵķ�����¼��");
		return false;
	}
	
	dealRedundant(fm2, tContPlanType);
	if (!verifyForm('fm2')) {
		return false;
	}
	
	if (!pubBeforeModifyPlan(fm2, tContPlanType)) {
		return false;
	}
	
	if (!pubBeforeSubmit(fm2, tContPlanType)) {//�ύǰ�Ĺ���У��
		return false;
	}
	
	fmPub.Operate.value = "MODIFYPLAN";
	mOperate = "MODIFYPLAN";
	fm2.action = "./EdorNCSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&EdorNo="+tEdorNo+"&PolicyNo="+tPolicyNo+ "&ContPlanType="+ tContPlanType + "&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitForm(fm2);
}

/**
 * ɾ������
 */
function delPlan() {
	
	var tSelNo = PlanInfoGrid.getSelNo()-1;
	if (tSelNo<0) {
	
		alert("��ѡ��Ҫɾ���ķ�����¼��");
		return false;
	}
	
	fmPub.Operate.value = "DELPLAN";
	mOperate = "DELPLAN";
	fm2.action = "./EdorNCSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&EdorNo="+tEdorNo+"&PolicyNo="+tPolicyNo+ "&ContPlanType="+ tContPlanType + "&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitForm(fm2);
}

/**
 * ģ����������
 */
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {

	fmPub.all("HiddenCodeType").value = value1;
	if (value1=='quotplantype') {
	
		var tSql = "1 and codetype=#quotplantype# and codeexp=#"+tContPlanType+"#  and code not in (#01#,#03#)";
		
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'1','1',null);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'1','1',null);
		}
	} else if (value1=='occuptype1') {//��ְҵ��������
		
		if (returnType=='0') {
			return showCodeList('occuptype',value2,value3,null,null,null,'1',null);
		} else {
			return showCodeListKey('occuptype',value2,value3,null,null,null,'1',null);
		}
	} else if (value1=="occupmidtype") {
		
		if (returnType=='0') {
			
			if (isEmpty(fm2.OccupType)) {
				alert("����ѡ��ְҵ���");
				return false;
			}
			var tSql = "1 and b.occupationtype =#"+fm2.OccupType.value+"#";
			return showCodeList('occupmidtype',value2,value3,null,tSql,1,'1',null);
		} else {
			
			if (isEmpty(fm2.OccupType)) {
				alert("����ѡ��ְҵ���");
				return false;
			}
			var tSql = "1 and b.occupationtype =#"+fm2.OccupType.value+"#";
			return showCodeListKey('occupmidtype',value2,value3,null,tSql,1,'1',null);
		}
	}	else if (value1=="occupcode") {
		
		if (returnType=='0') {
			
			if (isEmpty(fm2.OccupMidType)) {
				alert("����ѡ��ְҵ�з��࣡");
				return false;
			}
			var tSql = "1 and a.occupmidcode =#"+fm2.OccupMidType.value+"#";
			return showCodeList('occupcode',value2,value3,null,tSql,1,'1',null);
		} else {
			
			if (isEmpty(fm2.OccupMidType)) {
				alert("����ѡ��ְҵ�з��࣡");
				return false;
			}
			var tSql = "1 and a.occupmidcode =#"+fm2.OccupMidType.value+"#";
			return showCodeListKey('occupcode',value2,value3,null,tSql,1,'1',null);
		}
	} else if (value1=='occuptype2') {//��ְҵ��������
		
		if (returnType=='0') {
			return showCodeList('occuptype',value2,value3,null,null,null,'1',null);
		} else {
			return showCodeListKey('occuptype',value2,value3,null,null,null,'1',null);
		}
	} else if (value1=="premmode") {
		
		if (returnType=='0') {
			return showCodeList('premmode',value2,value3,null,null,null,'1',null);
		} else {
			return showCodeListKey('premmode',value2,value3,null,null,null,'1',null);
		}
	}
}

function chooseOccupFlag(cQuotFlag) {

	pubChooseOccupFlag(fm2, cQuotFlag);
}


/**
 * ѡ�񵥶�ְҵ���
 */
function pubChooseOccupFlag(cObj, cQuotFlag) {

	if (cQuotFlag=='1') {
		
		cObj.OccupTypeFlag.value = '1';
		document.all("OccupTypeRadio1").checked = true;
		document.all("trOccupType1").style.display = "";
		document.all("trOccupType2").style.display = "none";
	} else if (cQuotFlag=='2') {
		
		cObj.OccupTypeFlag.value = '2';
		document.all("OccupTypeRadio2").checked = true;
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "";
	} else {
		
		cObj.OccupTypeFlag.value = '';
		document.all("trOccupType1").style.display = "none";
		document.all("trOccupType2").style.display = "none";
	}
}

/**
 * ������ϸά��
 */
function planDetailOpen() {
	
	tPlanDetailOpen = window.open("./EdorNCDetailMain.jsp?PolicyNo="+ tPolicyNo +"&EdorNo="+ tEdorNo +"&EdorType="+ tEdorType+"&ContPlanType="+ tContPlanType +"&MissionID="+ tMissionID +"&SubMissionID="+ tSubMissionID +"&ActivityID="+ tActivityID+"&QueryFlag="+ tQueryFlag ,"1", "width="+screen.availWidth+",height="+screen.availHeight+",channelmode=yes,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

function showAllDetail() {
	
	tAllDetailOpen = window.open("./EdorNCAllDetailMain.jsp?PolicyNo="+ tPolicyNo +"&EdorNo="+ tEdorNo +"&EdorType="+ tEdorType +"&ContPlanType="+ tContPlanType, "1", "width="+screen.availWidth+",height="+screen.availHeight+",channelmode=yes,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

/**
 * ��ʼ��������ʾ
 */
function queryPlanFlag() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorNCSql");
	tSQLInfo.setSqlId("EdorNCSql19");
	tSQLInfo.addSubPara(tPolicyNo);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr==null) {
		return "";
	} else {
		return tArr[0][0];
	}
}
