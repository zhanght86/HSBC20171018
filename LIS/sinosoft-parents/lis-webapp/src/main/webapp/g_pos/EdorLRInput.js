/***************************************************************
 * <p>ProName��EdorLRInput.js</p>
 * <p>Title����������</p>
 * <p>Description����������</p>
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
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();



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
	if(mOperate=="PERCLICK" ||mOperate=="GRPCLICK" || mOperate=="DELETE"){
		queryOldClick();
		queryUpClick();		
	}	
}

/**
 * ���ƹ�ѡ��������
 */
 function showGrpLR(){
 		
 	if(fm.GrpLR.checked==true){
 		fm.GrpLRFlag.value ="1";		
 	}else {
 			fm.GrpLRFlag.value ="0"; 		
 		}
 	}
/**
 * ���ƹ�ѡ��ȡ������������
 */
function showMoney(){
	
	if(fm.NeedGetMoney.checked==true){		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorLRSql");
		tSQLInfo.setSqlId("EdorLRSql1");
		tSQLInfo.addSubPara("Reissue");
		
		var arrResult = easyExecSql(tSQLInfo.getString());	
		if(arrResult !=null){
			fm.Money.value=arrResult[0][0];
		}
		fm.FeeLRFlag.value ="1";	
	}else{		
		fm.Money.value='0';
		fm.FeeLRFlag.value ="0";
	}
}

/**
 * ���ƹ�ѡ����ƾ֤����
 */
function showHiddenInfo(){
	
	if(fm.PerLR.checked==true){
		hiddenInfo.style.display='';
		fm.PreLRFlag.value = '1';
		queryUpClick();			
	}else{
		hiddenInfo.style.display='none';
		fm.PreLRFlag.value = '0';
	}
}

/**
 * ��ѯ������Ϣ

 */
function showContPlanCode(cObj,cObjName,cObjCode){
	var tSQL = "";
	tSQL = "1 and a.grpcontno=#" + tGrpContNo + "# ";
	return showCodeList('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,tSQL,'1',1,null);
}

function showContPlanCodeName(cObj,cObjName,cObjCode){
	var tSQL = "";
		tSQL = "1 and a.grpcontno=#" + tGrpContNo + "# ";
	return showCodeListKey('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,tSQL,'1',1,null);
}

/**
 * ��ѯ�貹����������Ϣ
 */
function queryOldClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorLRSql");
	tSQLInfo.setSqlId("EdorLRSql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(fm.OldInsuredName.value);
	tSQLInfo.addSubPara(fm.OldInsuredIDNo.value);
	tSQLInfo.addSubPara(fm.ContPlanCode.value);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	tSQLInfo.addSubPara(fm.CustomernNo.value);

	turnPage1.queryModal(tSQLInfo.getString(), OldInsuredInfoGrid, 1, 1);	
	
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}
}

/**
 * ��ѯ���β�����Ϣ
 */
function queryUpClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorLRSql");
	tSQLInfo.setSqlId("EdorLRSql3");
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	tSQLInfo.addSubPara(tGrpContNo);
	
	initUpdateInsuredInfoGrid();
	turnPage2.queryModal(tSQLInfo.getString(), UpdateInsuredInfoGrid, 1, 1);	
}


/**
 * ����ƾ֤����
 */
function contClick(){
	
	var rowNum = OldInsuredInfoGrid.mulLineCount ;
	var tRow = 0;
	for(var index=0;index<rowNum;index++){
		if(OldInsuredInfoGrid.getChkNo(index)){
			tRow=1;
		}
	}
	if(tRow==0){
		alert("������ѡ��һ����¼!");
		return false;
	}
	
	mOperate="PERCLICK";	
	fm.action = "./EdorLRSave.jsp?Operate="+ mOperate+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
	
}

/**
 * ����ƾ֤����
 */
function grpContClick(){
	
	mOperate="GRPCLICK";	
	fm.action = "./EdorLRSave.jsp?Operate="+ mOperate+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
	
}

//��������
function deleteOperate(){
	var rowNum = UpdateInsuredInfoGrid.mulLineCount ;
	var tRow = 0;
	for(var index=0;index<rowNum;index++){
		if(UpdateInsuredInfoGrid.getChkNo(index)){
			tRow=1;
		}
	}
	if(tRow==0){
		alert("������ѡ��һ����¼!");
		return false;
	}
	mOperate="DELETE";
	fm.action = "./EdorLRSave.jsp?Operate="+ mOperate+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
}

//��Ч��������
function saveClick(){
	
	if(!verifyInput()){
		return false;
	}
	
	if(fm.GrpLR.checked==false && fm.PerLR.checked== false){
		alert("���ٹ�ѡһ�����������������ߡ�����ƾ֤������" );
		return false;
	}

	mOperate="SAVE";
	fm.action = "./EdorLRSave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&EdorNo="+tEdorNo;
	submitFunc();
}

/***
* ��鹴ѡ����
*/
function showCheck(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorLRSql");
	tSQLInfo.setSqlId("EdorLRSql4");
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	tSQLInfo.addSubPara(tGrpContNo);
	
	var arrRelsult = easyExecSql(tSQLInfo.getString());	
	if(arrRelsult != null){
		if(arrRelsult[0][0]=='0'){
			fm.GrpLR.checked=true;
			fm.GrpLRFlag.value ="1";	
			hiddenInfo.style.display='none';
		}else if(arrRelsult[0][0]=='1'){
			hiddenInfo.style.display='';
			fm.PerLR.checked=true;
			fm.PreLRFlag.value ="1";	
			queryUpClick();
		}else if(arrRelsult[0][0]=='2'){
			hiddenInfo.style.display='';
			fm.GrpLR.checked=true;
			fm.PerLR.checked=true;
			fm.GrpLRFlag.value ="1";	
			fm.PreLRFlag.value ="1";
			queryUpClick();
		}
		
		fm.Reason.value = arrRelsult[0][1];
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorLRSql");
	tSQLInfo.setSqlId("EdorLRSql5");
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	tSQLInfo.addSubPara(tGrpContNo);
	
	var arrRelsult = easyExecSql(tSQLInfo.getString());	
	if(arrRelsult != null){
		fm.NeedGetMoney.checked=true;
		fm.Money.value = arrRelsult[0][0];
		fm.FeeLRFlag.value ="1";	
	}

	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorLRSql");
	tSQLInfo.setSqlId("EdorLRSql6");
	tSQLInfo.addSubPara(tGrpContNo);
	var arrRelsult = easyExecSql(tSQLInfo.getString());	
	if(arrRelsult != null){
		fm.Times.value = arrRelsult[0][0];
	}
}
