/***************************************************************
 * <p>ProName��EdorSAInput.js</p>
 * <p>Title�������ձ�������</p>
 * <p>Description�������ձ�������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-25
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

//��ʼ��������Ϣ
function initPolicyInfo(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorSASql");
	tSQLInfo.setSqlId("EdorSASql1");
	tSQLInfo.addSubPara(tGrpContNo);
	
	var tResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tResult==null) {
		return false;
	} else {
		fm.PremCalMode.value=tResult[0][0];
		fm.PremCalName.value=tResult[0][1];
		fm.EnginArea.value=tResult[0][2];
		fm.EnginCost.value=tResult[0][3];
		if(fm.PremCalMode.value=="2"){
			divEnginCost.style.display="";
		}
		if(fm.PremCalMode.value=="3"){
			divEnginArea.style.display="";
		}
	}
}

//�������
function saveClick(){
	
	if(!verifyInput()){
		return false;
	}
	
	mOperate="SAVE";
	fm.action = "./EdorSASave.jsp?Operate="+ mOperate+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
		initReason();
	}	
}

//��ʼ��������Ϣ��ѯ
function initSACount(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorSASql");
	tSQLInfo.setSqlId("EdorSASql3");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tGrpContNo);
	
	var tSAResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tSAResult==null) {
		return false;
	} else {
		fm.Mtime.value=tSAResult[0][0];
	}
}

//��ʼ��������Ϣ��ѯ
function initPrem(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorSASql");
	tSQLInfo.setSqlId("EdorSASql4");
	tSQLInfo.addSubPara(tGrpContNo);
	
	var tPremResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPremResult==null) {
		return false;
	} else {
		fm.FirstPrem.value=tPremResult[0][0];
		//fm.EndDate.value=tPremResult[0][1];
	}
}
//������ֹ����
function getStopDate(){
	
	var startDate = fm.EndDate.value;
	var sDeferDays = fm.DeferDays.value;
	if(NullToEmpty(startDate)==""){
		alert("��ȡ�����������ڴ���");
		return false;
	}
	if (sDeferDays!="" && !isInteger(sDeferDays)) {
		alert("������Ч��������");
		return false;
	}
	if(parseFloat(sDeferDays)<1){
		alert("��¼�����0������");
		return false;
	}
	fm.StopDate.value= addDate(4,sDeferDays,startDate);
	getDeferDays();
}

//������
function getDeferDays(){
	var startDate = fm.EndDate.value;
	if(NullToEmpty(startDate)==""){
		return false;
	}
	var stopDate = fm.StopDate.value;
	if(NullToEmpty(stopDate)==""){
		alert("��¼�뱣���������ڴ���!");
		return false;
	}
	if(compareDate(stopDate,startDate)!=1){
		alert("��¼����ڱ������ڵ���������!");
		return false;
	}
	fm.DeferDays.value = dateDiff(startDate,stopDate,"D");
	calInitPrem();
}

//��ʼ����ȫ��Ϣ
function initReason(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorSASql");
	tSQLInfo.setSqlId("EdorSASql5");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	
	var tSAResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tSAResult==null) {
		return false;
	} else {
		fm.GetMoney.value=tSAResult[0][0];
		fm.Reason.value=tSAResult[0][1];
		fm.ReasonName.value=tSAResult[0][2];
		fm.ReasonDesc.value=tSAResult[0][3];
	}
}

//���㱣��
function calInitPrem(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorSASql");
	tSQLInfo.setSqlId("EdorSASql6");
	tSQLInfo.addSubPara(fm.DeferDays.value);
	tSQLInfo.addSubPara(tGrpContNo);
	
	var tPremResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPremResult==null) {
		alert("���㱣�Ѵ���");
		return false;
	} else {
		fm.InitPrem.value=tPremResult[0][0];
	}
}


//��ȫ��������
function queryStopDate(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorSASql");
	tSQLInfo.setSqlId("EdorSASql7");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	
	var tPremResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPremResult==null) {
	} else {
		fm.StopDate.value=tPremResult[0][0];
		
		fm.DeferDays.value = dateDiff(fm.EndDate.value,fm.StopDate.value,"D");
		calInitPrem();
	}
}

//��ȫ��������
function queryEndDate(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorSASql");
	tSQLInfo.setSqlId("EdorSASql8");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorAppNo);
	
	var tEndResult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tEndResult==null) {
	} else {
		fm.EndDate.value=tEndResult[0][0];
	}
}
