/***************************************************************
 * <p>ProName��EdorBEInput.js</p>
 * <p>Title���������˸�Ч</p>
 * <p>Description���������˸�Ч</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-23
 ****************************************************************/
 
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

//ԭ����������Ϣ��ѯ
function queryOldClick(){
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorBESql");
	tSQLInfo.setSqlId("EdorBESql1");
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
	tSQLInfo.setResourceName("g_pos.EdorBESql");
	tSQLInfo.setSqlId("EdorBESql2");
	tSQLInfo.addSubPara(tGrpContNo);
	tSQLInfo.addSubPara(tEdorType);
	tSQLInfo.addSubPara(tEdorAppNo);
	tSQLInfo.addSubPara(fm.InsuredName.value);
	tSQLInfo.addSubPara(fm.InsuredIDNo.value);
	tSQLInfo.addSubPara(NullToEmpty(tEdorNo));
	tSQLInfo.addSubPara(fm.BatchNo.value);

	turnPage2.queryModal(tSQLInfo.getString(), UpdateInsuredInfoGrid, 1, 1);	
	
	if(o=='1'){
		if(!turnPage2.strQueryResult){
			alert("δ��ѯ�����������Ĳ�ѯ���!");
			return false;
		}		
	}	
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
	fm.action = "./EdorBESave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitFunc();
}

//��Ч��������
function reSetInsured(){
	
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

	mOperate="UPDATE";
	fm.action = "./EdorBESave.jsp?Operate="+ mOperate+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID+"&EdorNo="+tEdorNo;
	submitFunc();
}

function submitFunc(){
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ���; 
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
		var iWidth=550;      //�������ڵĿ���; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ���; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}	
	queryOldClick();
	queryUpClick();
}