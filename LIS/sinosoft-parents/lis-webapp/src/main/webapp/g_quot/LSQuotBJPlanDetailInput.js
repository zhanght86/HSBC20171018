/***************************************************************
 * <p>ProName��LSQuotBJPlanDetailInput.js</p>
 * <p>Title�����۵���ӡ--���۷�����ϸ</p>
 * <p>Description�����۵���ӡ--���۷�����ϸ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-20
 ****************************************************************/

var showInfo;
var turnPage = new turnPageClass();//ϵͳʹ��
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();
var tPrint;

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

/**
 * �ύ�����,���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(FlagStr, content) {
	
	if (typeof(showInfo)=="object") {
		showInfo.close();
	}
	if (FlagStr == "Fail" ) {
		var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content;
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
}

/**
 * չʾ������ϸ
 */
function showQuotPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cShow,cPrint,cQuotQuery) {
	
	cObj.PageInfo.value = "��"+OnPage+"/"+PageNum+"ҳ";
	document.all("divBJPlanDetail").innerHTML = pubQuotPlanDetail(cObj, cArr, tStartNum, cQuotType, cTranProdType, cShow,cPrint,cQuotQuery);
}

/**
 * չʾָ��ҳ
 */
function goToPage(OnPage) {
	
	tStartNum = (OnPage-1)*tPlanShowRows+1;
	showQuotPlanDetail(fm, strQueryResult,tStartNum, tQuotType, tTranProdType, tShow,tPrint,tQuotQuery);
}

/**
 * �������
 */
function changDetail(cOfferListNo,cQuotNo,cQuotBatNo,cSysPlanCode,cPlanCode) {
	
	window.open("./LSQuotChangePremMain.jsp?QuotNo="+ cQuotNo + "&QuotBatNo="+ cQuotBatNo +"&SysPlanCode="+ cSysPlanCode +"&PlanCode="+ cPlanCode+"&OfferListNo="+ cOfferListNo+"&PrintState="+ tPrint+"&QuotQuery="+tQuotQuery,"�������",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/**
 * ��ȡ�ñ��۵���ӡ״̬
 */
function getPrintState() {
	
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_quot.LSQuotSelectPlanSql");
	tSQLInfo.setSqlId("LSQuotSelectPlanSql8");
	tSQLInfo.addSubPara(tOfferListNo);
	
	var tStateArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
	if (tStateArr!=null) {
		return tStateArr[0][0];
	} else {
		return null;
	}
}
