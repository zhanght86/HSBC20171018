/***************************************************************
 * <p>ProName��LCInsuredInfoInput.js</p>
 * <p>Title����Ա�嵥ά��</p>
 * <p>Description����Ա�嵥ά��</p>
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
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();
var tQuerFlag;

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
		if(mOperate =="CREATE" ){//��ʼ���������κη�Ӧ
			return;
		}
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + tContent ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		if(mOperate=="CONFDATA"){
			turnBack();
		}
		if(mOperate =="CHOSEDEL" || mOperate =="CONDDEL" || mOperate =="ALLDEL" ){
			insuredQuery();
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
	fm.Operate.value = mOperate;
	fm.submit();
}

/**
 * ���ñ��շ����ķ���
 */
function showContPlanCode(cObj,cObjName,cObjCode){
	var sql="1 and (GrpContNo=#"+fm.GrpPropNo.value+"# or GrpContNo like #"+fm.GrpPropNo.value+"%#) ";
	return showCodeList('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,sql,'1',1,null);
}

function showContPlanCodeName(cObj,cObjName,cObjCode){
	var sql="1 and (GrpContNo=#"+fm.GrpPropNo.value+"# or GrpContNo like #"+fm.GrpPropNo.value+"%#) ";
	return showCodeListKey('contplan',[cObj,cObjName,cObjCode],[0,1,2],null,sql,'1',1,null);
}


/**
* ��ѯ��������Ϣ�б�
*/

function insuredQuery(){
	
	tQuerFlag ='Q';	
	var querySql = "";	 
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCInsuredInfoSql");
	tSQLInfo.setSqlId("LCInsuredInfo1");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	tSQLInfo.addSubPara(fm.Customerno.value);
	tSQLInfo.addSubPara(fm.CustomName.value);
	tSQLInfo.addSubPara(fm.IDNO.value);
	tSQLInfo.addSubPara(fm.CustomerID.value);
	tSQLInfo.addSubPara(fm.ContPlanCode.value);
	tSQLInfo.addSubPara(fm.SysPlanCode.value);
	
	turnPage1.queryModal(tSQLInfo.getString(), QueryScanGrid, 1);
	if (!turnPage1.strQueryResult) {
		alert("δ��ѯ�����������Ĳ�ѯ�����");
	}	
}

/**
* ѯ�۷�����ѯ
*/

function showPlanQuery(){
	
	window.open("./LCContPlanQueryMain.jsp?ContPlanType="+tContPlanType+"&PolicyNo="+tGrpPropNo ,"������ѯ",'height='+screen.availHeight+',width='+screen.availWidth+',channelmode=yes,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

/**
* ��ӱ�������Ϣ
*/

function goIntoInsuredList(){
	
	var tContNo ="";
	var tInsuredno =""
	var tGrpPropNo=fm.GrpPropNo.value;
	var tContPlanType = fm.ContPlanType.value;
	var tFlag = fm.Flag.value;
	
	window.open("./LCinsuredAddMain.jsp?GrpPropNo="+tGrpPropNo+"&InsuredNo="+tInsuredno+"&ContNo="+tContNo+"&ContPlanType="+tContPlanType+"&Flag="+tFlag,"��ӱ�������Ϣ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
* ���뱻�����嵥
*/
function showlmpInsuredList(){
	
	window.open("./LCInsuredUploadMain.jsp?ContPlanType="+tContPlanType+"&GrpPropNo="+tGrpPropNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&ActivityID="+tActivityID,"��Ա�嵥����",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
* �������˻�����Ϣ
*/

function showQueryInsredList(){
	
	var tGrpPropNo=fm.GrpPropNo.value;
	window.open("./LCInsuredCollectInput.jsp?GrpPropNo="+tGrpPropNo,"���뱻�����嵥",'width=950,height=820,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
* ѡ��ɾ��
*/
function choseDelet(){
	
	var hasCheck = false ;	
	for (var i = 0; i < QueryScanGrid.mulLineCount; i++) {
		if (QueryScanGrid.getChkNo(i)) {
			hasCheck = true;
			break;
		}
	}
	if (!hasCheck) {
		alert("������ѡ��һ�����ݽ��д��������");
		return false;
	}
	
	mOperate = "CHOSEDEL";
	submitForm();
	
}

/**
* ����ɾ��
*/
function condDelete(){
	
	if(fm.Customerno.value =="" && fm.CustomName.value=="" && fm.IDNO.value=="" && fm.CustomerID.value=="" && fm.ContPlanCode.value==""){
		alert("������¼��һ����ѯ������������ɾ��������");
		return false;
	}

	if(tQuerFlag !='Q'){
		alert("���ȵ������ѯ����ť��ѯ���ݣ�");
		return false;
	}
	var tCount =  QueryScanGrid.mulLineCount;
	if(tCount =='0'){		
		alert("û����Ҫ���С�����ɾ���������ݣ�");
		return false;	
	}
	
	mOperate = "CONDDEL";
	submitForm();
	
}

/**
* ȫ��ɾ��
*/
function allDelete(){
	
	if(!confirm("�Ƿ�ȷ��ִ��ȫ��ɾ��������")){
		return false;
	}
	
	mOperate = "ALLDEL";
	submitForm();
}

/**
* ������ϸҳ��
*/
function goToInfo(){
	
	var tSelNo = QueryScanGrid.getSelNo();
	if(tSelNo!='0'){	
		var tInsuredno = QueryScanGrid.getRowColData(tSelNo-1,1);
		var tContNo = QueryScanGrid.getRowColData(tSelNo-1,2);
		var tContPlanType = fm.ContPlanType.value
	}
	var tGrpPropNo=fm.GrpPropNo.value;
	var tFlag = fm.Flag.value;
	
	window.open("./LCinsuredAddMain.jsp?GrpPropNo="+tGrpPropNo+"&InsuredNo="+tInsuredno+"&ContNo="+tContNo+"&ContPlanType="+tContPlanType+"&Flag="+tFlag,"��ӱ�������Ϣ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
* ��Ա�嵥ȷ��
*/
function saveClick(){
	mOperate = "CONFDATA";
	submitForm();
}

//����
function turnBack(){
	location.href = "./LCInsuredListInput.jsp";
}

/**
** ���Ը����ƻ�
**/
function initContPlanQuery(){
	if("1"==tPolicyType){
		divContPlanQuery1.style.display="none";
		divContPlanQuery2.style.display="";
	}
}

function showAllPlan() {
	tAllDetailOpen = window.open("./LCContPlanAllMain.jsp?PolicyNo="+ tGrpPropNo +"&ContPlanType="+ tContPlanType, "1", "height="+screen.availHeight+",width="+screen.availWidth+",channelmode=yes,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}
