/***************************************************************
 * <p>ProName��EdorSMInput.js</p>
 * <p>Title�����ڽ���ά��</p>
 * <p>Description�����ڽ���ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-06-19
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();

function balanceSubmit(){
	
	if(fm.BalanceOnState.value==''||fm.BalanceOnState.value==null){
		alert("��¼����㷽ʽ!");
		return false;
	}
	if(fm.BalanceOnState.value=='0'){
		if(!verifyInput2()){
			return false;
		}
	}
	fm.action="./EdorSMSave.jsp?Operate=INSERT&EdorType="+tEdorType+"&EdorNo="+tEdorNo+"&GrpContNo="+tGrpContNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID;
	submitForm(fm,"INSERT");
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
}
function submitForm(obj, tOperate) {
	
	submitFunc();
	mOperate = tOperate;
	obj.submit(); //�ύ
}
function afterCodeSelect(o, p){
	if(o=='balanceonstate'){
		if(p.value=='0'){
			td1.style.display='';
			td2.style.display='';
			td3.style.display='';
			td4.style.display='';
		}else if(p.value=='1'){
			td1.style.display='none';
			td2.style.display='none';
			td3.style.display='none';
			td4.style.display='none';
			td5.style.display='';
			td6.style.display='';
			td7.style.display='';
			td8.style.display='';
			fm.BalancePeriod.value='';
			fm.BalancePeriodName.value='';
			fm.GracePeriod.value='';
		}
	}	
}

// ��ѯ���ڽ�������
function queryBalanceInfo(){
	
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorSMSql");
		tSQLInfo.setSqlId("EdorSMSql1");
		tSQLInfo.addSubPara(tEdorNo);
		tSQLInfo.addSubPara(tEdorType);
		tSQLInfo.addSubPara(tGrpContNo);
		
		var arrRuselt = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		
		if(arrRuselt == null ){	
			tSQLInfo = new SqlClass();
			tSQLInfo.setResourceName("g_pos.EdorSMSql");
			tSQLInfo.setSqlId("EdorSMSql2");
			tSQLInfo.addSubPara(tGrpContNo);
			tSQLInfo.addSubPara(tGrpContNo);
			
			var arrRuselt = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
			
			if(arrRuselt != null){
				fm.BalanceOnState.value=arrRuselt[0][4];
				fm.BalanceOnStateName.value=arrRuselt[0][5];
				fm.BalancePeriod.value=arrRuselt[0][6];
				fm.BalancePeriodName.value=arrRuselt[0][7];
				fm.GracePeriod.value=arrRuselt[0][8];
			}
		}else {
			
				fm.BalanceOnState.value=arrRuselt[0][0];
				fm.BalanceOnStateName.value=arrRuselt[0][1];
				fm.BalancePeriod.value=arrRuselt[0][2];
				fm.BalancePeriodName.value=arrRuselt[0][3];
				fm.GracePeriod.value=arrRuselt[0][4];	
		}
}
