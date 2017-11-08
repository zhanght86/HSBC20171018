/***************************************************************
 * <p>ProName:EdorRDInput.js</p>
 * <p>Title:  ������ȡ</p>
 * <p>Description:������ȡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : zhoufz
 * @version  : 8.0
 * @date     : 2014-08-22
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
	tSQLInfo.setResourceName("g_pos.EdorRDSql");
	tSQLInfo.setSqlId("EdorRDSql1");
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
	tSQLInfo.setResourceName("g_pos.EdorRDSql");
	tSQLInfo.setSqlId("EdorRDSql2");
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
	fm.action = "./EdorRDSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
	fm.action = "./EdorRDSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
	tSQLInfo.setResourceName("g_pos.EdorRDSql");
	if(_DBT==_DBM){
		tSQLInfo.setSqlId("EdorRDSql5");
		tSQLInfo.addSubPara(tGrpContNo);
		tSQLInfo.addSubPara(tInsuredName);
		tSQLInfo.addSubPara(tInsuredIDNo);
		tSQLInfo.addSubPara(fm.EdorAppNo.value);
		tSQLInfo.addSubPara(tBatchNo);
		tSQLInfo.addSubPara(tInsuredID);
		tSQLInfo.addSubPara(tCurrenDate);
   }else if(_DBT==_DBO)
   {
	   tSQLInfo.setSqlId("EdorRDSql3");
		tSQLInfo.addSubPara(tGrpContNo);
		tSQLInfo.addSubPara(tInsuredName);
		tSQLInfo.addSubPara(tInsuredIDNo);
		tSQLInfo.addSubPara(fm.EdorAppNo.value);
		tSQLInfo.addSubPara(tBatchNo);
		tSQLInfo.addSubPara(tInsuredID);
		tSQLInfo.addSubPara(tCurrenDate);
   }
		
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
	tSQLInfo.setResourceName("g_pos.EdorRDSql");
	tSQLInfo.setSqlId("EdorRDSql4");
	tSQLInfo.addSubPara(tPolNo);
	tSQLInfo.addSubPara(tDutyCode);
	tSQLInfo.addSubPara(tPayPlanCode);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(fm.BatchNo.value);
	tSQLInfo.addSubPara(tInsuredID);
	tSQLInfo.addSubPara(tCurrenDate);
	
	turnPage4.queryModal(tSQLInfo.getString(), InvestGrid, 1, 1);
	
	if (InvestGrid.mulLineCount>0) {
		divInvest.style.display = "";
	} else {
		divInvest.style.display = "none";
	}
}

/**
 * ��ȡ��Ϣ����
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
	var tInsuAccBala = PayPlanGrid.getRowColData(tSelNo-1, 6);
	var tGetAmount = PayPlanGrid.getRowColData(tSelNo-1, 7);
	
	if (tGetAmount =="") {
		alert("�ɷ�����ȡ����Ϊ�գ�");
		return false;
	}
	
	if (!isNumeric(tGetAmount)) {
		alert("�ɷ�����ȡ�����Ҫ¼����ڵ���0�����֣�");
		return false;
	}
	
	if (tGetAmount<0) {
		alert("�ɷ�����ȡ�����Ҫ¼����ڵ���0�����֣�");
		return false;
	}
	
	if (parseFloat(tGetAmount)>parseFloat(tInsuAccBala)) {
		alert("��ȡ���ܴ����˻���Ϣ�ͣ�");
		return false;
	}
	
	if (tGetAmount>0 && InvestGrid.mulLineCount>1) {
		
		var tInvestMoneyFlag = false;//Ͷ�ʽ��¼���־
		var tSumInvestGetAmount = 0;
		for (var i=0;i < InvestGrid.mulLineCount;i++) {
			
			var tInvestGetAmount = InvestGrid.getRowColData(i,7);
			
			if (tInvestGetAmount=="") {
				alert("�ڡ�"+(i+1)+"���е��˻���ȡ����Ϊ�գ�");
				return false;
			}
			
			if (!isNumeric(tInvestGetAmount)) {
				alert("�ڡ�"+(i+1)+"���е��˻���ȡ�����Ҫ¼����ڵ���0�����֣�");
				return false;
			}
				
			if (tInvestGetAmount<0) {
				alert("�ڡ�"+(i+1)+"���е��˻���ȡ�����Ҫ¼����ڵ���0�����֣�");
				return false;
			}
				
			tSumInvestGetAmount = tSumInvestGetAmount + parseFloat(tInvestGetAmount);
		}
		
		if (tSumInvestGetAmount!=tGetAmount) {
			alert("�˻���ȡ����ܺ���Ҫ��ɷ�����ȡ�����ȣ�");
			return false;
		}
	}
	
	mOperate = "PayPlanSave";
	fm.action = "./EdorRDSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
