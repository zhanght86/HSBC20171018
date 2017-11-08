/***************************************************************
 * <p>ProName��LLClaimSpotCheckInput.js</p>
 * <p>Title��������</p>
 * <p>Description��������</p>
 * <p>Copyright��Copyright (c) 2014</p>
 * <p>Company��Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
var showInfo;
var tSQLInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();

/**
 * ��ѯ�������
 */
function queryClaimRuleInfo() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpotCheckSql");
	tSQLInfo.setSqlId("LLClaimSpotCheckSql");
	
	tSQLInfo.addSubPara(fm.QueryUserCode.value);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryRiskName.value);
	tSQLInfo.addSubPara(fm.QueryRealPay.value);
	tSQLInfo.addSubPara(fm.QueryGiveType.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(mManageCom);
				
	turnPage1.queryModal(tSQLInfo.getString(),LLClaimRuleListGrid, 2);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
		return false;
	}
}
/**
 * չʾ������ϸ
 */
function showClaimRuleDetail() {
	
	var i = LLClaimRuleListGrid.getSelNo()-1;
	var tRuleNo = LLClaimRuleListGrid.getRowColData(i,1);
	if (tRuleNo==null || tRuleNo=="") {
		alert("���Ȳ�ѯ��");
		return;
	}
	
	fm.RuleNo.value = tRuleNo;
	fm.CheckType.value = LLClaimRuleListGrid.getRowColData(i,2);
	fm.CheckTypeName.value = LLClaimRuleListGrid.getRowColData(i,3);
	fm.CheckUserCode.value = LLClaimRuleListGrid.getRowColData(i,6);
	fm.CheckUserName.value = LLClaimRuleListGrid.getRowColData(i,7);
	fm.CheckManageCom.value = LLClaimRuleListGrid.getRowColData(i,4);
	fm.CheckManageComName.value = LLClaimRuleListGrid.getRowColData(i,5);	
	fm.CheckGrpContNo.value = LLClaimRuleListGrid.getRowColData(i,8);
	fm.CheckAppntNo.value = LLClaimRuleListGrid.getRowColData(i,9);
	fm.CheckAppntName.value = LLClaimRuleListGrid.getRowColData(i,10);
	fm.CheckRiskCode.value = LLClaimRuleListGrid.getRowColData(i,11);
	fm.CheckRiskName.value = LLClaimRuleListGrid.getRowColData(i,12);
	fm.CheckRate.value = LLClaimRuleListGrid.getRowColData(i,13);
	fm.CheckMoney.value = LLClaimRuleListGrid.getRowColData(i,14);
	fm.CheckGiveType.value = LLClaimRuleListGrid.getRowColData(i,15);
	fm.CheckGiveTypeName.value = LLClaimRuleListGrid.getRowColData(i,16);
	fm.CheckStartDate.value = LLClaimRuleListGrid.getRowColData(i,17);
	fm.CheckEndDate.value = LLClaimRuleListGrid.getRowColData(i,18);
	
	fm.QueryBnfFlag.value = LLClaimRuleListGrid.getRowColData(i,19);;	
	fm.QueryBnfFlagName.value = LLClaimRuleListGrid.getRowColData(i,20);	
	
}

/**
 * ��������
 */
function addRule() {
	
	//ϵͳ��У�鷽��
	if (!verifyInput2()) {
		return false;
	}
	
	//У��������Ƿ����
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpotCheckSql");
	tSQLInfo.setSqlId("LLClaimSpotCheckSql2");
	tSQLInfo.addSubPara(fm.CheckManageCom.value);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length>0) {
		alert("�÷ֹ�˾�£���������ά����ֻ���޸ģ�");
		return false;
	}
	
	fm.Operate.value="INSERT";
	fm.action="./LLClaimSpotCheckSave.jsp";
	submitForm();		
}
/**
 * �޸Ĺ���
 */
function modifyRule() {
	
	var tSelNo = LLClaimRuleListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ��������");
		return false;
	}
	var tRuleNo = LLClaimRuleListGrid.getRowColData(tSelNo,1);
	fm.RuleNo.value = tRuleNo;	
		
	fm.Operate.value="UPDATE";
	fm.action="./LLClaimSpotCheckSave.jsp";
	submitForm();	
//	fm.action="./LLClaimSpotCheckSave.jsp";
}
/**
 * ɾ������
 */
function delteRule() {

	var tSelNo = LLClaimRuleListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ��������");
		return false;
	}
	var tRuleNo = LLClaimRuleListGrid.getRowColData(tSelNo,1);
	fm.RuleNo.value = tRuleNo;	
		
	fm.Operate.value="DELETE";
	fm.action="./LLClaimSpotCheckSave.jsp";
	submitForm();
}
/**
 * ���ù���
 */
function initRule() {
	initDetailInfo();
}

//չʾ��ʾ��Ϣ
function showWarnInfo() {
		
	alert('��¼��[Ͷ��������]��س�����(֧��ģ����ѯ)��');
	fm.CheckAppntName.focus();
	fm.CheckAppntName.style.background = "#ffb900";
}

//��ѯͶ������Ϣ��֧������ģ����ѯ
function QueryOnKeyDown(tObject) {
	
	var keycode = event.keyCode;
	//�س���ascii����13	
	if(keycode!="13" && keycode!="9") {
		return;
	}
	var tObjectName = tObject.name;
	var tObjectValue = tObject.value;
	if (tObjectName=="CheckAppntName") {
		
		var tAppntName = tObjectValue;	
			
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimSpotCheckSql");
		tSQLInfo.setSqlId("LLClaimSpotCheckSql1");
		tSQLInfo.addSubPara(tAppntName);
		tSQLInfo.addSubPara(mManageCom);
		tSQLInfo.addSubPara(tAppntName);
		tSQLInfo.addSubPara(mManageCom);				
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("δ��ѯ�����������Ĳ�ѯ�����");
			return false;
		} else {
			
			if (tArr.length==1) {
				fm.CheckAppntNo.value = tArr[0][1];
				fm.CheckAppntName.value = tArr[0][2];
			} else {
				showAppntList();			
			}
		}		
	}
}
/**
 * �򿪲�ѯ��ϵͳ����Ͷ����ѡ��ҳ��
 */
function showAppntList() {
	
	var strUrl="./LLClaimQueryAppntMain.jsp?AppntName="+fm.CheckAppntName.value+"&ContType=01";
	var tWidth=800;
	var tHeight=600;
	var tTop=(window.screen.availHeight-30-tHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var tLeft=(window.screen.availWidth-10-tWidth)/2;        //��ô��ڵ�ˮƽλ��;	
	window.open(strUrl,"�û���Ϣ��ѯ",'width='+tWidth+',height='+tHeight+',top='+tTop+',left='+tLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');		
}
/**
 * ��ò�ѯ��ϵͳͶ����
 */
function afterQueryAppnt(tQueryResult) {
	
	fm.all('CheckAppntNo').value = tQueryResult[1];
	fm.all('CheckAppntName').value = tQueryResult[2];
	self.focus();
}
/**
 * �ύ����
 */
function submitForm() {
	
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
	fm.submit();
}

/**
 * �ύ���ݺ󷵻ز���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object" && typeof(showInfo)!="unknown") {
		showInfo.close();
	}
	
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
		initForm();
	}	
}
//���ݵ���
function exportData() {
	
	if (!confirm("ȷ��Ҫ�������ݣ�")) {
		return false;
	}
	
	//�������
	var tTitle = "���������^|������ͱ���^|�������^|������^|����������^|����û�^|������^|����û���^|"
							+"Ͷ���˱���^|Ͷ��������^|���ֱ���^|��������^|������^|�⸶���^|�������ͱ���^|������ʽ^|�������^|���ֹ��^|�Ƿ������˱���^|�Ƿ���������һ��";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimSpotCheckSql");
	tSQLInfo.setSqlId("LLClaimSpotCheckSql");
	
	tSQLInfo.addSubPara(fm.QueryUserCode.value);
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryGrpContNo.value);
	tSQLInfo.addSubPara(fm.QueryGrpName.value);
	tSQLInfo.addSubPara(fm.QueryRiskName.value);
	tSQLInfo.addSubPara(fm.QueryRealPay.value);
	tSQLInfo.addSubPara(fm.QueryGiveType.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
}
