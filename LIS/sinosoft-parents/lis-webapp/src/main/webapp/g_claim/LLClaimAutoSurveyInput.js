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

/**
 * ��ѯ�������
 */
function queryAutoRule() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimAutoSurveySql");
	tSQLInfo.setSqlId("LLClaimAutoSurveySql");
	
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryRiskcode.value);
	tSQLInfo.addSubPara(fm.QueryRiskName.value);
	tSQLInfo.addSubPara(fm.QueryUWMoney.value);
	tSQLInfo.addSubPara(fm.QueryPayMoney.value);
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
	fm.CheckManageCom.value = LLClaimRuleListGrid.getRowColData(i,2);
	fm.CheckManageComName.value = LLClaimRuleListGrid.getRowColData(i,3);	
	fm.CheckRiskCode.value = LLClaimRuleListGrid.getRowColData(i,4);
	fm.CheckRiskName.value = LLClaimRuleListGrid.getRowColData(i,5);
	fm.CheckAppPay.value = LLClaimRuleListGrid.getRowColData(i,6);
	fm.CheckClmPay.value = LLClaimRuleListGrid.getRowColData(i,7);
	fm.CheckStartDate.value = LLClaimRuleListGrid.getRowColData(i,8);
	fm.CheckEndDate.value = LLClaimRuleListGrid.getRowColData(i,9);	
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
	tSQLInfo.setResourceName("g_claim.LLClaimAutoSurveySql");
	tSQLInfo.setSqlId("LLClaimAutoSurveySql2");
	tSQLInfo.addSubPara(fm.CheckManageCom.value);
	
	var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tArr!=null && tArr.length>0) {
		alert("�÷ֹ�˾�£��Զ����������ά����ֻ���޸ģ�");
		return false;
	}
	
	fm.Operate.value="INSERT";
	fm.action = "./LLClaimAutoSurveySave.jsp";
	submitForm();		
}
/**
 * �޸Ĺ���
 */
function modifyRule() {
	
	var tSelNo = LLClaimRuleListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ���Զ��������");
		return false;
	}
	var tRuleNo = LLClaimRuleListGrid.getRowColData(tSelNo,1);
	fm.RuleNo.value = tRuleNo;	
		
	fm.Operate.value="UPDATE";
	fm.action = "./LLClaimAutoSurveySave.jsp";
	submitForm();	
}
/**
 * ɾ������
 */
function delteRule() {

	var tSelNo = LLClaimRuleListGrid.getSelNo()-1;
	if (tSelNo<0) {
		alert("����ѡ��һ���Զ��������");
		return false;
	}
	var tRuleNo = LLClaimRuleListGrid.getRowColData(tSelNo,1);
	fm.RuleNo.value = tRuleNo;	
		
	fm.Operate.value="DELETE";
	fm.action = "./LLClaimAutoSurveySave.jsp";
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
	var tTitle = "���������^|������^|����������^|"
							+"���ֱ���^|��������^|��˽��^|�⸶���^|��Ч����^|��Чֹ��";
	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_claim.LLClaimAutoSurveySql");
	tSQLInfo.setSqlId("LLClaimAutoSurveySql");
	
	tSQLInfo.addSubPara(fm.QueryManageCom.value);
	tSQLInfo.addSubPara(fm.QueryRiskcode.value);
	tSQLInfo.addSubPara(fm.QueryRiskName.value);
	tSQLInfo.addSubPara(fm.QueryUWMoney.value);
	tSQLInfo.addSubPara(fm.QueryPayMoney.value);
	tSQLInfo.addSubPara(fm.QueryStartDate.value);
	tSQLInfo.addSubPara(fm.QueryEndDate.value);
	tSQLInfo.addSubPara(mManageCom);
	
	var tQuerySQL = tSQLInfo.getString();
	fm.action = "../common/jsp/QueryDataExport.jsp?Title="+ tTitle +"&QuerySQL="+ tQuerySQL;
	
	fm.submit();
	
	
}
