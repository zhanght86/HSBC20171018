/***************************************************************
 * <p>ProName��LCPropPrintInput.js</p>
 * <p>Title��Ͷ�����ӡ</p>
 * <p>Description��Ͷ�����ӡ</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-04-22
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var tSQLInfo = new SqlClass();

/**
 * ��ѯѯ����Ϣ
 */
function queryClick() {
	
	initQuotInfoGrid();
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropPrintSql");
	tSQLInfo.setSqlId("LCPropPrintSql2");
	tSQLInfo.addSubPara(fm.preCustomerName.value);
	tSQLInfo.addSubPara(fm.OfferListNo.value);
	tSQLInfo.addSubPara(fm.QuotNo.value);
	tSQLInfo.addSubPara(fm.QuotType.value);
	tSQLInfo.addSubPara(tManageCom);
	turnPage1.queryModal(tSQLInfo.getString(), QuotInfoGrid, 1);
	
}
function queryPropInfo() {
	
	initPropInfoGrid();
	var qGrpPropNo = fm.GrpPropNoQ.value;
	var qOfferListNo = fm.OfferListNoQ.value;
	var qCustomerName = fm.CustomerName.value;
	/**if(qGrpPropNo=="" && qOfferListNo=="" && qCustomerName=="" ){
		alert("Ͷ������/���۵���/��λ���ƣ�������¼��һ����ѯ����!");
		return false;
	}
	**/
	var tPrtNo = "";
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCPropPrintSql");
	tSQLInfo.setSqlId("LCPropPrintSql1");
	tSQLInfo.addSubPara(qGrpPropNo);
	tSQLInfo.addSubPara(qOfferListNo);
	tSQLInfo.addSubPara(qCustomerName);
	tSQLInfo.addSubPara(fm.State.value);
	tSQLInfo.addSubPara(tManageCom);
	tSQLInfo.addSubPara(tOperator);
	turnPage2.queryModal(tSQLInfo.getString(), PropInfoGrid, 1);
}
/**
 * ����Ͷ������Ϣ¼��
 */
function propInfo() {
	var tRow = PropInfoGrid.getSelNo();
	var tSelNo = PropInfoGrid.getSelNo()-1;
	if (tRow==0) {
		alert("��ѡ��һ����Ϣ!");
		return false;	
	}
	var tGrpPropNo = PropInfoGrid.getRowColData(tSelNo, 1);
	window.location="./LCPropEntryInput.jsp?GrpPropNo="+ tGrpPropNo;
}
/**
 * ����Ͷ������Ϣ¼��
 */
function createPrintInfo(){
	var tRow = QuotInfoGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ����Ϣ��");
		return false;	
	}
	var tOfferListNo = QuotInfoGrid.getRowColData(tRow-1, 1);

	fm.action = "./LCPropPrintSave.jsp?Operate=CREATE&OfferListNo=" + tOfferListNo ;
	submitForm(fm,"CREATE");
}

function submitForm(obj, tOperate) {
	
	submitFunc();
	mOperate = tOperate;
	obj.submit(); //�ύ
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
function afterSubmit(FlagStr, content, vGrpPropNo) {
	
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
		fm.GrpPropNoQ.value= vGrpPropNo;
		queryPropInfo();
	}	
}

/**
* ���۵���ϸ
**/
function showQuotation(){
	var tRow = QuotInfoGrid.getSelNo();
	if (tRow==0) {
		alert("����ѡ��һ��������Ϣ��");
		return false;	
	}
	
	var tOfferListNo = QuotInfoGrid.getRowColData(tRow-1,1);
	var tQuotNo = QuotInfoGrid.getRowColData(tRow-1,2);
	var tQuotBatNo = QuotInfoGrid.getRowColData(tRow-1,3);
	var tQuotType = QuotInfoGrid.getRowColData(tRow-1,4);
	
	var tSrc = "";
	tSrc += "?QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&OfferListNo="+ tOfferListNo+"&QuotType="+tQuotType+"&ReturnFlag=1";
	
	window.open("./LCQuotQueryMain.jsp"+tSrc,"���۵���ѯ",'width=950,height=520,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0'); 	
}


/**
 * ���۵�����
 */
function downloadAppnt(parm1){
	
	var tPrintState = document.all(parm1).all("PropInfoGrid4").value;
	
	if (tPrintState=="1"){
		var tFileName = document.all(parm1).all("PropInfoGrid7").value;
		var tFilePath = document.all(parm1).all("PropInfoGrid8").value;
		window.location = "../common/jsp/download.jsp?FilePath="+tFilePath+tFileName+"&FileName="+tFileName;
	}
}
