/***************************************************************
 * <p>ProName��LCDeliveryInput.js</p>
 * <p>Title�����͵Ǽ�</p>
 * <p>Description�����͵Ǽ�</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-07
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

var tSQLInfo = new SqlClass();
var mOperate = '';


//��ѯ����
function queryClick(){
	if(""!=fm.PrintStartDate.value && ""!=fm.PrintEndDate.value){
		
		if(fm.PrintStartDate.value>fm.PrintEndDate.value){
			alert("��ӡ����Ӧ���ڴ�ӡֹ��!");
			return false;
		}
	}
	var mManageCom=fm.ManageCom.value;
	var mGrpPropNo=fm.GrpPropNo.value;
	var mGrpContNo=fm.GrpContNo.value;
	var mGrpName=fm.GrpName.value;
	var mContState=fm.ContState.value;
	if(mContState==null||mContState==''){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCPostalInfoSql");
		if(_DBT==_DBO){
			tSQLInfo.setSqlId("LCPostalInfoSql1");
	   }else if(_DBT==_DBM)
	   {
		   tSQLInfo.setSqlId("LCPostalInfoSql11");
	   }
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(mManageCom);
		tSQLInfo.addSubPara(mGrpPropNo);
		tSQLInfo.addSubPara(mGrpContNo);
		tSQLInfo.addSubPara(mGrpName);
		tSQLInfo.addSubPara(fm.PrintStartDate.value);
		tSQLInfo.addSubPara(fm.PrintEndDate.value);
		turnPage1.queryModal(tSQLInfo.getString(), ContInfoGrid, 1, 1);
		if(!turnPage1.strQueryResult){
			alert("δ�鵽���������Ľ��!");
			initContInfoGrid();
		}
	}else if(mContState=='0'){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCPostalInfoSql");
		if(_DBT==_DBO){
			tSQLInfo.setSqlId("LCPostalInfoSql2");
	   }else if(_DBT==_DBM)
	   {
			tSQLInfo.setSqlId("LCPostalInfoSql21");
	   }
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(mManageCom);
		tSQLInfo.addSubPara(mGrpPropNo);
		tSQLInfo.addSubPara(mGrpContNo);
		tSQLInfo.addSubPara(mGrpName);
		tSQLInfo.addSubPara(fm.PrintStartDate.value);
		tSQLInfo.addSubPara(fm.PrintEndDate.value);
		turnPage1.queryModal(tSQLInfo.getString(), ContInfoGrid, 1, 1);
		if(!turnPage1.strQueryResult){
			alert("δ�鵽���������Ľ��!");
			initContInfoGrid();
		}		
	}else if(mContState=='1'){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.LCPostalInfoSql");
		
		if(_DBT==_DBO){
			tSQLInfo.setSqlId("LCPostalInfoSql3");
	   }else if(_DBT==_DBM)
	   {
		   tSQLInfo.setSqlId("LCPostalInfoSql31");
	   }
		tSQLInfo.addSubPara(tManageCom);
		tSQLInfo.addSubPara(mManageCom);
		tSQLInfo.addSubPara(mGrpPropNo);
		tSQLInfo.addSubPara(mGrpContNo);
		tSQLInfo.addSubPara(mGrpName);
		tSQLInfo.addSubPara(fm.PrintStartDate.value);
		tSQLInfo.addSubPara(fm.PrintEndDate.value);
		turnPage1.queryModal(tSQLInfo.getString(), ContInfoGrid, 1, 1);
		if(!turnPage1.strQueryResult){
			alert("δ�鵽���������Ľ��!");
			initContInfoGrid();
		}
	}
}

/**
 * ����¼�������Ϣ
 */
function saveClick() {
	
	if(!beforeSub()){
		return false;
	}
	if(!verifyInput2()){
		return false;
	}
	if(fm.RegisterPassFlag.value=='1'){
		if(fm.Reason.value==null||fm.Reason.value==''){
			alert("���ϸ�ԭ����Ϊ��!");
			return false;
		}
	}
	if(fm.RegisterPassFlag.value=="0"){
		if(fm.TransferType.value==null || fm.TransferType.value==""){
			alert("��ѡ�񽻽ӷ�ʽ!");
			return false;
		}
		if(fm.TransferDate.value==null || fm.TransferDate.value==""){
			alert("��¼�뽻������!");
			return false;
		}
	}
	
	if(fm.RegisterPassFlag.value=="0" && fm.TransferType.value=="2"){
		if(fm.ExpressCorpName.value==null || fm.ExpressCorpName.value=="" || fm.ExpressNo.value==null || fm.ExpressNo.value==""){
			alert("��ݽ��ӣ���ݹ�˾�Ϳ�ݵ��Ų���Ϊ��!");
			return false;
		}
	}
	fm.action = "./LCDeliverySave.jsp?Operate=INSERT";
	submitForm(fm,"INSERT");	
}


function submitFunc()
{
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
		queryClick();
		divQuery1Info.style.display='none';
		divQuery2Info.style.display='none';
		fm.RegisterPassFlag.value='';
		fm.RegisterPassFlagName.value='';
	}	
}
function submitForm(obj, tOperate) {
	
	submitFunc();
	mOperate = tOperate;
	obj.submit(); //�ύ
}

function afterCodeSelect(o, p){	
	if(o=='registerclu'){
		if(p.value=='0'){
			divQuery1Info.style.display='';
			divQuery2Info.style.display='none';
		}else{
			divQuery1Info.style.display='none';
			divQuery2Info.style.display='';
		}
	}		
}
function beforeSub(){
	var tSelNo = ContInfoGrid.getChkCount();
	if (tSelNo<1) {
		alert("��ѡ��һ��Ͷ����Ϣ");
		return false;
	}
	if(fm.RegisterPassFlag.value=='0'){
		if(fm.TransferDate.value>fm.ExpressDate.value){
			alert("��������Ӧ���ڵ�ǰ����!");
			return false;
		}
	}
	return true;
}
