/***************************************************************
 * <p>ProName��LCGrpChargeFeeInput.js</p>
 * <p>Title���н�������ά��</p>
 * <p>Description���н�������ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-05
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tSQLInfo = new SqlClass();


function queryAgentInfo(){
	divZJInfo.style.display="";
	var tRow = FeeRateInfoGrid.getSelNo()-1;
	var tRiskCode=FeeRateInfoGrid.getRowColData(tRow,1);
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpChargeFeeSql");
	tSQLInfo.setSqlId("LCGrpChargeFeeSql2");
	tSQLInfo.addSubPara(tGrpPropNo);
	tSQLInfo.addSubPara(tRiskCode);
	turnPage2.queryModal(tSQLInfo.getString(), ZJGrid, 1, 1,30);
}
function queryPropInfo(){
	tSQLInfo = new SqlClass();
	tSQLInfo.setResourceName("g_app.LCGrpChargeFeeSql");
	tSQLInfo.setSqlId("LCGrpChargeFeeSql1");
	tSQLInfo.addSubPara(tGrpPropNo);
	turnPage1.queryModal(tSQLInfo.getString(), FeeRateInfoGrid, 1, 1,30);
}
function feeRateSubmit(){
	if(!beforeSub()){
		return false;
	}
	var tRow = FeeRateInfoGrid.getSelNo()-1;
	if(tRow==-1){
		alert("��ѡ��һ��!");
		return false;
	}
	if(!ZJGrid.checkValue("ZJGrid")){
		return false;
	}
	var tRickCode=FeeRateInfoGrid.getRowColData(tRow,1);
	fm.action = "./LCGrpChargeFeeSave.jsp?Operate=INSERT&RiskCode="+tRickCode;
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
	queryPropInfo();
}
function submitForm(obj, tOperate) {
	
	submitFunc();
	mOperate = tOperate;
	obj.submit(); //�ύ
}
function beforeSub(){
	var rowNum = ZJGrid.mulLineCount;
	var index=0;
	for(var i=0;i < rowNum;i++){
		if(ZJGrid.getRowColData(i,3)>1||ZJGrid.getRowColData(i,3)<0){
			alert("�н������ѱ���Ϊ����0��С��1������!");
			return false;
		}
		var k = new Number(ZJGrid.getRowColData(i,3));
		index=index+k;
	}
	if(index>1){
		alert("�н������ѱ���֮��ӦС�ڵ���1������!");
		return false;
	}
	return true;
}
