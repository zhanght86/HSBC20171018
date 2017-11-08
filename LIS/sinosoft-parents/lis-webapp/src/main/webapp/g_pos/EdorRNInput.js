/***************************************************************
 * <p>ProName��EdorRNInput.js</p>
 * <p>Title�������ڽɷ�</p>
 * <p>Description�������ڽɷ�</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-26
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

//ԭ����������Ϣ��ѯ
function queryOldClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRNSql");
	tSQLInfo.setSqlId("EdorRNSql1");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.OldInsuredName.value);
	tSQLInfo.addSubPara(fm.OldInsuredIDNo.value);
	tSQLInfo.addSubPara(fm.OldInsuredNo.value);
	tSQLInfo.addSubPara(tEdorAppNo);
	
	turnPage1.queryModal(tSQLInfo.getString(), OldInsuredInfoGrid, 1, 1);
		
	if(!turnPage1.strQueryResult){
		alert("δ��ѯ�����������Ĳ�ѯ���!");
		return false;
	}
}

//�޸Ĺ��ı���������Ϣ��ѯ
function queryUpClick(o){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRNSql");
	tSQLInfo.setSqlId("EdorRNSql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorType);
	tSQLInfo.addSubPara(NullToEmpty(tEdorNo));
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.InsuredIDNo.value);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	
	turnPage2.queryModal(tSQLInfo.getString(), UpdateInsuredInfoGrid, 1, 1);
	
	if(o=='1'){
		if(!turnPage2.strQueryResult){
			alert("δ��ѯ�����������Ĳ�ѯ���!");
			return false;
		}
	}
}

//ѡ�����
function selectClick(){
	
	var rowNum = OldInsuredInfoGrid.mulLineCount ;
	var tRow = 0;
	
	for (var index=0;index<rowNum;index++) {
		
		if (OldInsuredInfoGrid.getChkNo(index)) {
			tRow=1;
		}
	}
	
	if (tRow==0) {
		alert("������ѡ��һ����¼!");
		return false;
	}
	
	if (!verifyInput2()) {
		return false;
	}
	
	mOperate="UPDATE";
	fm.action = "./EdorRNSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
}

//��������
function deleteOperate(){
	
	var tSelNo = UpdateInsuredInfoGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ����¼��");
		return false;
	}
	
	mOperate="DELETE";
	fm.action = "./EdorRNSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
}

/**
 * ��ѯ�����˽ɷ�����Ϣ
 **/
function queryPayPlan(){
	
	var tSelNo= UpdateInsuredInfoGrid.getSelNo();
	
	var tSerialNo = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,1);
	var tBatchNo = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,2);
	var tInsuredID = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,3);
	var tInsuredName = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,4);
	var tInsuredIDNo = UpdateInsuredInfoGrid.getRowColData(tSelNo-1,5);
	
	initPayPlanGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRNSql");
	if(_DBT==_DBO){
		tSQLInfo.setSqlId("EdorRNSql3");
	   }else if(_DBT==_DBM)
	   {
		   tSQLInfo.setSqlId("EdorRNSql5");
	   }
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tInsuredName);
	tSQLInfo.addSubPara(tInsuredIDNo);
	tSQLInfo.addSubPara(fm.EdorAppNo.value);
	tSQLInfo.addSubPara(tBatchNo);
	tSQLInfo.addSubPara(tInsuredID);
	
	turnPage3.queryModal(tSQLInfo.getString(), PayPlanGrid, 1, 1);
	
	divPayPlan.style.display = "";
	divInvest.style.display = "none";
}

/**
 * ��ѯͶ���˻���Ϣ
 **/
function queryInvest(){
	
	var tInsuredListSelNo= UpdateInsuredInfoGrid.getSelNo();
	var tInsuredID = UpdateInsuredInfoGrid.getRowColData(tInsuredListSelNo-1,3);
	
	var tSelNo = PayPlanGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ���ɷ�����Ϣ��");
		return false;
	}
	
	var tPolNo = PayPlanGrid.getRowColData(tSelNo-1, 1);
	var tDutyCode = PayPlanGrid.getRowColData(tSelNo-1, 2);
	var tPayPlanCode = PayPlanGrid.getRowColData(tSelNo-1, 4);
	
	initInvestGrid();
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRNSql");
	tSQLInfo.setSqlId("EdorRNSql4");
	tSQLInfo.addSubPara(tPolNo);
	tSQLInfo.addSubPara(tDutyCode);
	tSQLInfo.addSubPara(tPayPlanCode);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	tSQLInfo.addSubPara(tInsuredID);
	
	turnPage4.queryModal(tSQLInfo.getString(), InvestGrid, 1, 1);
	
	if (InvestGrid.mulLineCount>0) {
		divInvest.style.display = "";
	} else {
		divInvest.style.display = "none";
	}
}

/**
 * �ɷ���Ϣ����
 **/
function payPlanSave(){
	
	var tInsuredListSelNo= UpdateInsuredInfoGrid.getSelNo();
	if (tInsuredListSelNo==0) {
		alert("��ѡ��һ������������Ϣ��");
		return false;
	}
	
	fm.InsuredID.value = UpdateInsuredInfoGrid.getRowColData(tInsuredListSelNo-1,3);
	
	var tSelNo = PayPlanGrid.getSelNo();
	if (tSelNo==0) {
		alert("��ѡ��һ���ɷ�����Ϣ��");
		return false;
	}
	
	var tDutyCode = PayPlanGrid.getRowColData(tSelNo-1, 2);
	var tPayPlanCode = PayPlanGrid.getRowColData(tSelNo-1, 4);
	var tPayPlanPrem = PayPlanGrid.getRowColData(tSelNo-1, 6);
	
	if (tPayPlanPrem =="") {
		alert("�ɷѽ���Ϊ�գ�");
		return false;
	}
	
	if (!isNumeric(tPayPlanPrem)) {
		alert("�ɷѽ����Ҫ¼����ڵ���0�����֣�");
		return false;
	}
	
	if (tPayPlanPrem<0) {
		alert("�ɷѽ����Ҫ¼����ڵ���0�����֣�");
		return false;
	}
	
	if (tPayPlanPrem>0 && InvestGrid.mulLineCount>1) {
		
		var tInvestMoneyFlag = false;//Ͷ�ʽ��¼���־
		var tInvestRateFlag = false;//Ͷ�ʷ������¼���־
		var tSumInvestMoney = 0;
		var tSumInvestRate = 0;
		for (var i=0;i < InvestGrid.mulLineCount;i++) {
			
			var tInvestMoney = InvestGrid.getRowColData(i,6);
			var tInvestRate = InvestGrid.getRowColData(i,7);
			
			if (tInvestMoney=="" && tInvestRate=="") {
				alert("�ڡ�"+(i+1)+"����Ͷ�ʽ����Ͷ�ʷ����������ͬʱΪ�գ�");
				return false;
			}
			
			if (tInvestMoney!="" && tInvestRate!="") {
				alert("�ڡ�"+(i+1)+"����Ͷ�ʽ����Ͷ�ʷ����������ͬʱ¼�룡");
				return false;
			}
			
			if (tInvestMoney!="") {
				
				tInvestMoneyFlag = true;
				
				if (!isNumeric(tInvestMoney)) {
					alert("�ڡ�"+(i+1)+"���е�Ͷ�ʽ����Ҫ¼����ڵ���0�����֣�");
					return false;
				}
				
				if (tInvestMoney<0) {
					alert("�ڡ�"+(i+1)+"���е�Ͷ�ʽ����Ҫ¼����ڵ���0�����֣�");
					return false;
				}
				
				tSumInvestMoney = tSumInvestMoney + parseFloat(tInvestMoney);
			}
			
			if (tInvestRate!="") {
				
				tInvestRateFlag = true;
				
				if (!isNumeric(tInvestRate)) {
					alert("�ڡ�"+(i+1)+"���е�Ͷ�ʷ��������Ҫ¼��0-1֮���С����");
					return false;
				}
				
				if (tInvestRate>1 || tInvestRate<0) {
					alert("�ڡ�"+(i+1)+"���е�Ͷ�ʷ��������Ҫ¼��0-1֮���С����");
					return false;
				}
				
				tSumInvestRate = tSumInvestRate + parseFloat(tInvestRate);
			}
		}
		
		if (tInvestMoneyFlag && tInvestRateFlag) {
			alert("Ͷ�ʽ����Ͷ�ʷ����������ͬʱ¼�룡");
			return false;
		}
		
		if (tInvestMoneyFlag && tSumInvestMoney!=tPayPlanPrem) {
			alert("Ͷ�ʽ���ܺ���Ҫ��ɷѽ����ȣ�");
			return false;
		}
		
		if (tInvestRateFlag && tSumInvestRate!=1) {
			alert("Ͷ�ʷ������֮�Ͳ�����1��");
			return false;
		}
	}
	
	mOperate = "PayPlanSave";
	fm.action = "./EdorRNSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
}

function submitFunc(){
	
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
	}
	
	divPayPlan.style.display = "none";
	divInvest.style.display = "none";
	queryOldClick();
	queryUpClick(2);
}
