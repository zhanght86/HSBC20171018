/***************************************************************
 * <p>ProName��EdorREInput.js</p>
 * <p>Title��������Ч</p>
 * <p>Description��������Ч</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-24
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();
var tContPlanType;

//��ʼ��
function queryClick(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRESql");
	tSQLInfo.setSqlId("EdorRESql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorType);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorNo);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry==null) {
		return false;
	} else {
		fm.GetMoney.value=tPropEntry[0][0]-tPropEntry[0][1];
		fm.GetInterest.value=tPropEntry[0][1];
	}
}

function queryInfoClick(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRESql");
	tSQLInfo.setSqlId("EdorRESql3");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorNo);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry==null) {
		return false;
	} else {
		fm.Days.value=tPropEntry[0][0];
	}
}
 
//������Ч
function saveClick(){
	
	if(fm.REtype.value=='31'){
		if(!verifyInput2()){
			return false;
		}
		
		if (parseFloat(fm.GetInterest.value)<0) {
			alert("��Ϣ����¼����ڵ���0�����֣�");
			return false;
		}
	}else{
		if(fm.EdorValDate.value ==""){
			alert("������Ч����û��¼�룡");
			return false;
		}
	}
	
	mOperate="INSERT";
	fm.action = "./EdorRESave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&EdorNo="+tEdorNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
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
		initForm();
	}
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
	
	if (value1=="queryexp") {
				
		var tSql = "surrenderRE"+tContPlanType;
		
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',180);
		} else {
			return showCodeListKey('queryexp',value2,value3,null,tSql,'concat(codetype,codeexp)','1',180);
		}
	} 
}

//��Ч����
function queryEdorValDate(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorRESql");
	tSQLInfo.setSqlId("EdorRESql4");
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorType);
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tPropEntry==null) {
		return false;
	} else {
		fm.EdorValDate.value=tPropEntry[0][0];
	}
}
