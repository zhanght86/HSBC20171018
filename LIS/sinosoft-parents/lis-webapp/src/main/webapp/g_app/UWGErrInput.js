/***************************************************************
 * <p>ProName��UWGErrInput.js</p>
 * <p>Title���˱�����</p>
 * <p>Description���˱�����</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : weigh
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPage4 = new turnPageClass();
var turnPage5 = new turnPageClass();
var turnPage6 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * �ύ���ݺ󷵻ز���

 */
function afterSubmit(tFlagStr, tContent) {
	
	if (typeof(showInfo) == "object" && typeof(showInfo) != "unknown") {
	
		showInfo.close();
	}
	
	if (tFlagStr == "Fail") {
	
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	} else {
		
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + tContent ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		if(mOperate=="INSERT||GRP"){
			queryUWErr();
		}else if(mOperate=="ADJUST"){
			queryAdjust();
		}else if(mOperate="INSERT||Person"){
			queryCGUWMaster(0);
		}else if(mOperate="INSERT||AllPerson"){
			queryCGUWMaster(0);
		}
	}	
}

/**
 * �ύ
 */
function submitForm() {
	
	var i = 0;
	var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr;
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

function showContPlanCode(cObj,cObjName,cObjCode){
	return showCodeList('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,fm.GrpPropNo.value,'grpcontno',1,null);
}

function showContPlanCodeName(cObj,cObjName,cObjCode){
	return showCodeListKey('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,fm.GrpPropNo.value,'grpcontno',1,null);
}

/**
 * ��ʼ����ѯ�ٱ�����
 */
function queryUWErr(){
	
	initUWGErrGrid();	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.UWGErrSql");
	tSQLInfo.setSqlId("UWGErrSql1");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), UWGCErrGrid, 1, 1);
}

/**
 * ��ȡ�����Զ��˱���Ϣ
 */
function showUWGErrGrid(){
	
	var tGrpPropNo = fm.GrpPropNo.value;
	var tSelNo = UWGCErrGrid.getSelNo();
	var tRulecode = UWGCErrGrid.getRowColData(tSelNo-1,1);	
	var tUWNo = UWGCErrGrid.getRowColData(tSelNo-1,3);
	var tRulevel = UWGCErrGrid.getRowColData(tSelNo-1,4);
	var tUWlevel = UWGCErrGrid.getRowColData(tSelNo-1,5);
	var tRiskcode= UWGCErrGrid.getRowColData(tSelNo-1,7);

	var tUWConclusion =  UWGCErrGrid.getRowColData(tSelNo-1,11);
	var tUWConclusionName = UWGCErrGrid.getRowColData(tSelNo-1,12);
	var tUWOption =  UWGCErrGrid.getRowColData(tSelNo-1,13);
	
	if(tUWlevel =='G'){
		divUWGErr1.style.display = 'none';
		divUWGErr2.style.display = '';
		
		fm.UWConclusion.value=tUWConclusion;
		fm.UWConclusionName.value=tUWConclusionName;
		fm.UWOption.value=tUWOption;
		
	}else if(tUWlevel =='I'){		
		divUWGErr1.style.display = '';
		divUWGErr2.style.display = 'none';
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.UWGErrSql");
		tSQLInfo.setSqlId("UWGErrSql2");
		tSQLInfo.addSubPara(fm.GrpPropNo.value);
		tSQLInfo.addSubPara(fm.GrpPropNo.value);
		tSQLInfo.addSubPara(tRulevel);
		tSQLInfo.addSubPara(tUWNo);
		tSQLInfo.addSubPara(tRulecode);
		
		turnPage2.queryModal(tSQLInfo.getString(), UWContErrGrid, 1, 1);	
	}
}


/**
 * ���������б���ʾ��ѯ
 */
function queryCGUWMaster(o){
	
	initUWGErrGrid();
	initCCErrGrid();
	initCpolErrGrid();
	clearPage();
	
	var tFlagState = fm.FlagState.value;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.UWGErrSql");
	
	if(tFlagState =="1"){	
		tSQLInfo.setSqlId("UWGErrSql3");
		tSQLInfo.addSubPara(fm.FlagState.value);	
	}else if(tFlagState =="0" ){
		tSQLInfo.setSqlId("UWGErrSql3");
		tSQLInfo.addSubPara(fm.FlagState.value);
	}else if(tFlagState == ""){
		tSQLInfo.setSqlId("UWGErrSql7");
		tSQLInfo.addSubPara(fm.FlagState.value);
	}
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.insuredName.value);
	tSQLInfo.addSubPara(fm.idNo.value);
	tSQLInfo.addSubPara(fm.ContPlanCode.value);
	tSQLInfo.addSubPara(fm.sysPlanCode.value);
	turnPage3.queryModal(tSQLInfo.getString(), UWGErrGrid, 1, 1);
	
}

/**
 * չʾ�����˷��������ĺ˱������������Ϣ
 */
function showInsInfo(){
	
	var tSelNo = UWGErrGrid.getSelNo();
	var tInsuredno = UWGErrGrid.getRowColData(tSelNo-1,1);
	var tContno=  UWGErrGrid.getRowColData(tSelNo-1,2);
	var tUWno = UWGErrGrid.getRowColData(tSelNo-1,3);

	var tUWConclu = UWGErrGrid.getRowColData(tSelNo-1,11);
	var tUWConcluName = UWGErrGrid.getRowColData(tSelNo-1,12);
	var tUWIdea =  UWGErrGrid.getRowColData(tSelNo-1,13);
	
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.UWGErrSql");
	tSQLInfo.setSqlId("UWGErrSql4");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(tInsuredno);
	tSQLInfo.addSubPara(tContno);
	tSQLInfo.addSubPara(tUWno);
	
	turnPage4.queryModal(tSQLInfo.getString(), CCErrGrid, 1, 1);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.UWGErrSql");
	tSQLInfo.setSqlId("UWGErrSql5");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(tContno);
	tSQLInfo.addSubPara(tInsuredno);
	
	turnPage5.queryModal(tSQLInfo.getString(), CpolErrGrid, 1, 1,100000000);
	var tReinsType ="";
	
	if(CpolErrGrid.mulLineCount>0){
		tReinsType = CpolErrGrid.getRowColData(0,13);
	}
	fm.ReinsType.value = tReinsType;
		
	if(""!=tReinsType){
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_app.UWGErrSql");
		tSQLInfo.setSqlId("UWGErrSql8");
		tSQLInfo.addSubPara("reinsarrange");
		tSQLInfo.addSubPara(tReinsType);
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null) {	
		} else {		
			fm.ReinsTypeName.value = tArr[0][0];
		}
	}else{
		fm.ReinsType.value = "";
		fm.ReinsTypeName.value = "";
	}

	fm.UWConclu.value=tUWConclu;
	fm.UWConcluName.value = tUWConcluName;
	fm.UWIdea.value=tUWIdea;
	
}

/**
 * �������������Ϣ
 */
function savePremAdjust(){

	CpolErrGrid.delBlankLine();
	var mulRow = CpolErrGrid.mulLineCount;
	
	if(mulRow ==0){
		alert("��������û����Ϣ����Ҫ������棡");
		return false;
	}
	if(!confirm("�Ƿ�ȷ�ϵ����������˱��ѡ����")){
		return false;
	}
	mOperate="ADJUST";
	fm.action = "./LCDutyAdjustPremSave.jsp?Operate="+ mOperate;
	submitForm();	
}

/**
 * ������˺˱�����
 */

function saveUWConclu(){
	
	var tSelNo = UWGErrGrid.getSelNo();
	if(tSelNo ==0){
		alert("��ѡ��һ����������Ϣ���к˱����۱��棡");
		return false;
	}
	
	var tInsuredno = UWGErrGrid.getRowColData(tSelNo-1,1);
	var tContNo = UWGErrGrid.getRowColData(tSelNo-1,2);
	var tUWNo = UWGErrGrid.getRowColData(tSelNo-1,3);
	
	if(fm.UWConclu.value==""){
		alert("��¼����˺˱����ۣ�");
		return false;	
	}
	if(fm.UWConclu.value!="0" && fm.UWIdea.value==""){
		alert("��¼����˺˱������");
		return false;	
	}
	mOperate="INSERT||Person";
	fm.action = "./UWGErrSave.jsp?Operate="+ mOperate+"&ContNo="+tContNo+"&InsuredNo="+tInsuredno+"&UWNo="+tUWNo;
	submitForm();
}

/**
 * ����������˺˱�����
 */
 
 function saveUWContAllclu(){
	
	var hasCheck = false ;	
	for (var i = 0; i < UWGErrGrid.mulLineCount; i++) {
		if (UWGErrGrid.getChkNo(i)) {
			hasCheck = true;
			break;
		}
	}
	if (!hasCheck) {
		alert("������ѡ��һ�����ݽ��д��������");
		return false;
	}
	
	if(fm.UWConclu.value==""){
		alert("��¼����˺˱������");
		return false;
	}
	if(fm.UWConclu.value!="0" && fm.UWIdea.value==""){
		alert("��¼����˺˱������");
		return false;	
	}
	mOperate="INSERT||AllPerson";
	fm.action = "./UWGErrSave.jsp?Operate="+ mOperate;
	submitForm();
	
 }

/**
 * �ŵ��㱣��˱�����
 */
 
function saveUWGrpclu(){
	
	var tSelNo = UWGCErrGrid.getSelNo();	
	if(tSelNo ==0){
		alert("��ѡ��һ����Ϣ���к˱����۱��棡");
		return false;
	}
	var tGrpPropNo = fm.GrpPropNo.value;	
	var tRulecode = UWGCErrGrid.getRowColData(tSelNo-1,1);	
	var tUWNo = UWGCErrGrid.getRowColData(tSelNo-1,3);
	var tRulevel = UWGCErrGrid.getRowColData(tSelNo-1,4);
	var tRiskcode= UWGCErrGrid.getRowColData(tSelNo-1,7);
	
	if(fm.UWConclusion.value==""){
		alert("��¼��˱����ۣ�");
		return false;	
	}
			
	mOperate="INSERT||GRP";
	fm.action = "./UWGErrSave.jsp?Operate="+ mOperate+"&GrpProNo="+tGrpPropNo+"&Rulecode="+tRulecode+"&UWNo="+tUWNo+"&Rulevel="+tRulevel+"&Riskcode="+tRiskcode;
	submitForm();
}

/**
 * ����ͨ��
 */
function passUWMaster(){
	
	if(fm.UWConclu.value==""){
		alert("��¼����˺˱����ۣ�");
		return false;
	}
	if(fm.UWConclu.value!="0" && fm.UWIdea.value==""){
		alert("��¼����˺˱������");
		return false;	
	}

	if(!confirm("�Ƿ�ȷ��Ҫ�����˱���")){
		return false;
	}
	
	var tGrpPropNo =fm.GrpPropNo.value;	
	mOperate="INSERT||ALL";
	fm.action = "./UWGErrSave.jsp?Operate="+ mOperate+"&GrpProNo="+tGrpPropNo;
	submitForm();
}

/**
* ��ղ�ѯ����
*/
function initQuery(){
	fm.insuredName.value="";
	fm.idNo.value="";
	fm.ContPlanCode.value="";
	fm.ContPlanCodeName.value="";
	fm.FlagState.value="";
	fm.FlagStateName.value="";
	fm.sysPlanCode.value="";
}

/**
* ���¼�������
*/

function clearPage(){
	fm.ReinsType.value ="";
	fm.ReinsTypeName.value ="";
	fm.UWConclu.value ="";
	fm.UWConcluName.value ="";
	fm.UWIdea.value ="";
}

function queryAdjust(){
	
	var tSelNo = UWGErrGrid.getSelNo();
	if(tSelNo ==0){
		return false;
	}
	var tContno=  UWGErrGrid.getRowColData(tSelNo-1,2);
	var tUWno = UWGErrGrid.getRowColData(tSelNo-1,3);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.UWGErrSql");
	tSQLInfo.setSqlId("UWGErrSql5");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(tContno);
	tSQLInfo.addSubPara(tInsuredno);
	
	turnPage5.queryModal(tSQLInfo.getString(), CpolErrGrid, 1, 1);
	
}

