/***************************************************************
 * <p>ProName��EdorUWGErrInput.js</p>
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
		
		if(mOperate=="INSERT||GRP" ){
			queryUWErr();
		}else if(mOperate=="INSERT||AllPerson" || mOperate=="INSERT||Person"){
			queryCGUWMaster();
			clearPage();
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
	tSQLInfo.setResourceName("g_pos.EdorUWGErrSql");
	tSQLInfo.setSqlId("EdorUWGErrSql1");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(tEdorNo);
	
	turnPage3.queryModal(tSQLInfo.getString(), UWGCErrGrid, 1, 1);	
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
	var tEdorType = UWGCErrGrid.getRowColData(tSelNo-1,7);
	var tRiskcode= UWGCErrGrid.getRowColData(tSelNo-1,9);

	var tUWConclusion =  UWGCErrGrid.getRowColData(tSelNo-1,13);
	var tUWConclusionName = UWGCErrGrid.getRowColData(tSelNo-1,14);
	var tUWOption =  UWGCErrGrid.getRowColData(tSelNo-1,15);
	
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
		tSQLInfo.setResourceName("g_pos.EdorUWGErrSql");
		tSQLInfo.setSqlId("EdorUWGErrSql2");
		tSQLInfo.addSubPara(fm.GrpPropNo.value);
		tSQLInfo.addSubPara(tRulevel);
		tSQLInfo.addSubPara(tUWNo);
		tSQLInfo.addSubPara(tRulecode);
		tSQLInfo.addSubPara(tEdorNo);
		tSQLInfo.addSubPara(tEdorType);
		
		turnPage2.queryModal(tSQLInfo.getString(), UWContErrGrid, 1, 1);	
	}
}

/**
 * ���������б���ʾ��ѯ
 */
function queryCGUWMaster(){
	
	initUWGErrGrid();
	initCCErrGrid();
	initCpolErrGrid();
	initCpolTErrGrid
	clearPage();
	
	divPerPol01.style.display = 'none';
	divPerPol02.style.display = 'none';
	
	var tFlagState = fm.FlagState.value;
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorUWGErrSql");
	
	if(tFlagState =="1"){	
		tSQLInfo.setSqlId("EdorUWGErrSql3");
		tSQLInfo.addSubPara(fm.FlagState.value);	
	}else if(tFlagState =="0" ){
		tSQLInfo.setSqlId("EdorUWGErrSql3");
		tSQLInfo.addSubPara(fm.FlagState.value);
	}else if(tFlagState == ""){
		tSQLInfo.setSqlId("EdorUWGErrSql7");
		tSQLInfo.addSubPara("");
	}
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.insuredName.value);
	tSQLInfo.addSubPara(fm.idNo.value);
	tSQLInfo.addSubPara(fm.ContPlanCode.value);
	tSQLInfo.addSubPara(tEdorNo);
	
	turnPage3.queryModal(tSQLInfo.getString(), UWGErrGrid, 1, 1);
	
	if (!turnPage3.strQueryResult) {
		alert("δ��ѯ�����������ĸ��˷ֱ���Ϣ��");
		return false;
	}
	
}

/**
 * չʾ�����˷��������ĺ˱������������Ϣ
 */
function showInsInfo(){
	
	var tSelNo = UWGErrGrid.getSelNo();
	var tInsuredno = UWGErrGrid.getRowColData(tSelNo-1,1);
	var tContno=  UWGErrGrid.getRowColData(tSelNo-1,2);
	var tUWno = UWGErrGrid.getRowColData(tSelNo-1,3);
	var tEdorType = UWGErrGrid.getRowColData(tSelNo-1,4);
	
	var tUWConclu = UWGErrGrid.getRowColData(tSelNo-1,13);
	var tUWConcluName = UWGErrGrid.getRowColData(tSelNo-1,14);
	var tUWIdea =  UWGErrGrid.getRowColData(tSelNo-1,15);
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorUWGErrSql");
	tSQLInfo.setSqlId("EdorUWGErrSql4");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(tInsuredno);
	tSQLInfo.addSubPara(tContno);
	tSQLInfo.addSubPara(tUWno);
	tSQLInfo.addSubPara(tEdorNo);
	tSQLInfo.addSubPara(tEdorType);
	
	turnPage4.queryModal(tSQLInfo.getString(), CCErrGrid, 1, 1);
	
	if(tEdorType=='NI'){
		divPerPol01.style.display='';
		divPerPol02.style.display='none';
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorUWGErrSql");
		tSQLInfo.setSqlId("EdorUWGErrSql5");
		tSQLInfo.addSubPara(fm.GrpPropNo.value);
		tSQLInfo.addSubPara(tContno);
		tSQLInfo.addSubPara(tInsuredno);
		tSQLInfo.addSubPara(tEdorNo);
		tSQLInfo.addSubPara(tEdorType);
		
		turnPage5.queryModal(tSQLInfo.getString(), CpolErrGrid, 1, 1);		
	}else {
		divPerPol02.style.display='';
		divPerPol01.style.display='none';

		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_pos.EdorUWGErrSql");
		tSQLInfo.setSqlId("EdorUWGErrSql9");
		tSQLInfo.addSubPara(tEdorNo);
		tSQLInfo.addSubPara(tEdorType);
		tSQLInfo.addSubPara(tContno);
		
		turnPage6.queryModal(tSQLInfo.getString(), CpolTErrGrid, 1, 1);
	}

	showReins.style.display = 'none';
	
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
	
	var tSelNo = UWGErrGrid.getSelNo();
	var tEdorType = UWGErrGrid.getRowColData(tSelNo-1,4);
	
	if(mulRow ==0){
		alert("��������û����Ϣ����Ҫ������棡");
		return false;
	}
	if(!confirm("�Ƿ�ȷ�ϵ����������˱��ѡ����")){
		return false;
	}
	mOperate="ADJUST";
	fm.action = "./EdorDutyAdjustPremSave.jsp?Operate="+ mOperate+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType+"&EdorAppNo="+tEdorAppNo;
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
	var tEdorType = UWGErrGrid.getRowColData(tSelNo-1,4);
	
	if(fm.UWConclu.value==""){
		alert("��¼����˺˱����ۣ�");
		return false;	
	}
	if(fm.UWConclu.value!="0" && fm.UWIdea.value==""){
		alert("��¼����˺˱������");
		return false;	
	}
	
	mOperate="INSERT||Person";
	fm.action = "./EdorUWGErrSave.jsp?Operate="+ mOperate+"&ContNo="+tContNo+"&InsuredNo="+tInsuredno+"&UWNo="+tUWNo+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType;
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
	/**
	if(fm.ReinsFlag.value=="1"){
		showReins.style.display = '';
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorUWSql");
	tSQLInfo.setSqlId("EdorUWSql4");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.EdorNo.value);
	
	var arrResult= easyExecSql(tSQLInfo.getString());
	if(arrResult !=null && arrResult[0][0]=="1"){
		alert("���Ƚ����ٱ�����");
		return false;
	}
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_pos.EdorUWGErrSql");
	tSQLInfo.setSqlId("EdorUWGErrSql10");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(tEdorNo);
		
	var arrRrsult = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	
	if(arrRrsult != null){
		if(fm.ReinsType.value ==""){
			alert("��¼������ٱ����ۣ�");
			return false;	
		}	
	}
	**/
	if(fm.UWConclu.value==""){
		alert("��¼����˺˱������");
		return false;
	}
	if(fm.UWConclu.value!="0" && fm.UWIdea.value==""){
		alert("��¼����˺˱������");
		return false;	
	}
	mOperate="INSERT||AllPerson";
	fm.action = "./EdorUWGErrSave.jsp?Operate="+ mOperate;
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
	var tEdorType = UWGCErrGrid.getRowColData(tSelNo-1,7);
	var tRiskcode= UWGCErrGrid.getRowColData(tSelNo-1,9);
	
	if(fm.UWConclusion.value==""){
		alert("��¼��˱����ۣ�");
		return false;	
	}
			
	mOperate="INSERT||GRP";
	fm.action = "./EdorUWGErrSave.jsp?Operate="+ mOperate+"&GrpProNo="+tGrpPropNo+"&Rulecode="+tRulecode+"&UWNo="+tUWNo+"&Rulevel="+tRulevel+"&Riskcode="+tRiskcode+"&EdorNo="+tEdorNo+"&EdorType="+tEdorType;
	submitForm();
}

/**
 * ����ͨ��
 */
function passUWMaster(){
	
	if(fm.UWConclu.value ==""){
		alert("��¼����˺˱����ۣ�");
		return false		
	}

	if(!confirm("�Ƿ�ȷ��Ҫ�����˱���")){
		return false;
	}

	var tGrpPropNo =fm.GrpPropNo.value;	
	
	mOperate="INSERT||ALL";
	fm.action = "./EdorUWGErrSave.jsp?Operate="+ mOperate+"&GrpProNo="+tGrpPropNo+"&EdorNo="+tEdorNo;
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
