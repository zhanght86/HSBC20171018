.1/***************************************************************
 * <p>ProName��LCAgentToUserInput.js</p>
 * <p>Title���ͻ��������</p>
 * <p>Description���ͻ��������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-07-29
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * ��ѯ
 */
function queryClick() {
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.LCAgentToUserSql");
	tSQLInfo.setSqlId("LCAgentToUserSql1");
	tSQLInfo.addSubPara(fm.ManageCom.value);
	tSQLInfo.addSubPara(fm.AgentName.value);
	tSQLInfo.addSubPara(tManageCom);

	turnPage1.queryModal(tSQLInfo.getString(), AgentInfoGrid,1,1);
	if(!turnPage1.strQueryResult){
		alert("δ��ѯ��������������Ϣ!");
		return false;
	}
}
/**
 * ����
 */
function relaClick(){
	var tSelNo = UserInfoGrid.getSelNo()-1;
	if(tSelNo<0){
		alert("��ѡ��һ���û����й���!");
		return false;
	}
	var nSelNo = AgentInfoGrid.getSelNo()-1;
	var tUserCode = UserInfoGrid.getRowColData(tSelNo,1);
	var tAgentCode = AgentInfoGrid.getRowColData(nSelNo,2);

	mOperate="INSERT";
	fm.action = "./LCAgentToUserSave.jsp?Operate="+mOperate+"&AgentCode="+tAgentCode+"&UserCode="+tUserCode;
	submitFunc();
	fm.submit();
}

/**
 * ����
 */
function cancClick(){
	var tSelNo = AgentInfoGrid.getSelNo()-1;
	var tAgentCode = AgentInfoGrid.getRowColData(tSelNo,2);
	var tUserCode = AgentInfoGrid.getRowColData(tSelNo,3);
	if(tUserCode==null||tUserCode==''){
		alert("δ�����û�!");
		return false;
	}
	mOperate="DELETE";
	fm.action = "./LCAgentToUserSave.jsp?Operate="+mOperate+"&AgentCode="+tAgentCode+"&UserCode="+tUserCode;
	submitFunc();
	fm.submit();
}
/**
 * �ύ
 */
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

}

/**
 * �ύ�����,���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" +
		
		 content;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	} else {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	}
	queryClick();
	hidden.style.display='none';
}

function showHidden(){
	hidden.style.display='';
	var tSelNo = AgentInfoGrid.getSelNo()-1;
	var tAgentCode = AgentInfoGrid.getRowColData(tSelNo,2);
	var tUserCode = AgentInfoGrid.getRowColData(tSelNo,3);
	var tAgentName = AgentInfoGrid.getRowColData(tSelNo,4);
	var tManageCom = AgentInfoGrid.getRowColData(tSelNo,1);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_customer.LCAgentToUserSql");
	tSQLInfo.setSqlId("LCAgentToUserSql2");
	tSQLInfo.addSubPara(tAgentCode);
	
	var tPropEntry = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if(tUserCode!=null&&tUserCode!=''){
		hidden.style.display='none';
		fm.rela.style.display='none';
	}else if (tPropEntry==null) {
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_customer.LCAgentToUserSql");
		tSQLInfo.setSqlId("LCAgentToUserSql3");
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(tAgentName);
		turnPage2.queryModal(tSQLInfo.getString(), UserInfoGrid,1,1);
		fm.rela.style.display='';
	} 
}
