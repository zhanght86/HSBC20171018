/***************************************************************
 * <p>ProName��LCGrpUWInput.js</p>
 * <p>Title���˹��˱�</p>
 * <p>Description���˹��˱�</p>
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
		if("UWCONFIRM"==fm.Operate.value ){
			goBack();
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
 * ��ʼ��ҳ����Ϣ

 */
function initElements()
{
	var arrResult = new Array();
	var querySql = "";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGroupUWSql");
	tSQLInfo.setSqlId("LCGroupUWSql1");
	tSQLInfo.addSubPara(fm.GrpPropNo.value);

	arrResult =  easyExecSql(tSQLInfo.getString());
	if(arrResult==null || arrResult.length==0){
			return ;
	}

	fm.ManageCom.value=arrResult[0][0];
	fm.GrpName.value=arrResult[0][1];
	fm.SaleChnl.value=arrResult[0][2];
	fm.ApplyDate.value=arrResult[0][3];
	fm.ValDate.value=arrResult[0][4];
	fm.AppntNum.value=arrResult[0][5];
	fm.ShouldPrem.value=arrResult[0][6];
	fm.ValDateType.value=arrResult[0][7];

	if("0"==arrResult[0][7]){
		divVal.style.display="none";
		divValS.style.display="";
	}else{
		divVal.style.display="";
		divValS.style.display="none";
	}
}


/**
 * ��ѯ��Ա�嵥
 */

 function insuredQuery(){

 	var tGrpPropNo = fm.GrpPropNo.value;
 	window.open("./UWInsuredQueryMain.jsp?GrpPropNo="+tGrpPropNo,"��ѯ��Ա�嵥",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
 }

 /**
 * ��ѯ��Ա�ֲ�
 */
 function insuredStatistic(){
 	var tGrpPropNo = fm.GrpPropNo.value;
 	window.open("./UWPersonQueryMain.jsp?GrpPropNo="+tGrpPropNo,"��ѯ�ֲ��嵥",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
 }

 /**
 * �˱�����
 */
 function goToGErr(){

 	var tGrpPropNo = fm.GrpPropNo.value;
 	window.open("./UWGErrMain.jsp?GrpPropNo="+tGrpPropNo,"�˱�����",'width=1600,height=620,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
 }

 /**
 * �ٱ�����
 */
 function gotoReins(){

 	var tGrpPropNo = fm.GrpPropNo.value;
 	window.open("../reinsure/RIFaculDealMain.jsp?GrpPropNo="+tGrpPropNo+"&Flag=NB","�ٱ�����",'width=1600,height=620,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
 }
  /**
 * ����
 */
 function goBack(){

 	window.location="./LCGrpUWListInput.jsp"

 }
 /**
  * �˱�ȷ��
  */
function uwConfirm(){
	if(!verifyInput2()){
		return false;
	}
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGroupUWSql");
	tSQLInfo.setSqlId("LCGroupUWSql2");
	tSQLInfo.addSubPara(tActivityID);
	tSQLInfo.addSubPara(fm.GrpPropNo.value);
	arrResult =  easyExecSql(tSQLInfo.getString());
	if(arrResult!=null && arrResult.length >0){
		if(confirm("�����С�"+arrResult[0][3]+"����������˻��Ƿ�����ٱ����ۣ�")){
			fm.ReinsFlag.value= "1";
		}else{
			fm.ReinsFlag.value= "0";
			return false;
		}
	}

	mOperate = "UWCONFIRM";
	fm.Operate.value= "UWCONFIRM";

	fm.action = "./LCGrpUWSave.jsp";
	submitForm();
}
/**
	* �������ο���
*/
function claimDutyControl(){
	var strUrl="../g_claim/LLCLaimControlMain.jsp?BussType=NB&Flag=1&BussNo="+ fm.GrpPropNo.value;

	window.open(strUrl,"�������ο���",'width=1600,height=620,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
* ������ѯ
*/

function showPlanQuery(){

	window.open("./LCContPlanQueryMain.jsp?ContPlanType="+tContPlanType+"&PolicyNo="+tGrpPropNo ,"������ѯ",'width=1366,height=768,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
/**
* ������ѯ
*/

function policyQuery(){
	window.open("./LCBussQueryMain.jsp?Page=./LCGrpContPolInput.jsp&ContPlanType="+tContPlanType+"&GrpPropNo="+tGrpPropNo ,"Ͷ������ѯ",'width=1366,height=768,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
