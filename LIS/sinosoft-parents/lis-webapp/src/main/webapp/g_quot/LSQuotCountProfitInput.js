/***************************************************************
 * <p>ProName��LSQuotCountProfitInit.js</p>
 * <p>Title���������</p>
 * <p>Description���������</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : �ų�
 * @version  : 8.0
 * @date     : 2014-05-29
 ****************************************************************/
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mOperate = "";//����״̬
var tSQLInfo = new SqlClass();

/**
 * �ύ����
 */
function submitForm(obj) {

	var showStr = "���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ�棡";
	var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content="+ showStr;
	//showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	obj.submit();
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
//		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content="+ content ;
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		getQueryResult(content);             
	}
}

/**
 * ģ����������
 */
function returnShowCodeList(value1, value2, value3) {
	
	returnShowCode(value1, value2, value3, '0');
}

function returnShowCodeListKey(value1, value2, value3) {

	returnShowCode(value1, value2, value3, '1');
}

function returnShowCode(value1, value2, value3, returnType) {
	
	//����
	if (value1=='quotrisk') {
		
		var tSql = "1 and b.startdate<=#"+tCurrentDate+"# and (case b.enddate when ## then #9999-12-31# else b.enddate end) >#"+tCurrentDate+"# and a.insuaccflag=#Y# and b.riskprop!=#I# ";
		
		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1','300');
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1','300');
		}
	}
	//����
	if (value1=='quotduty') {
		
		if (isEmpty(fm2.RiskCode)) {
			alert("����ѡ�����֣�");
			return false;
		}
		
		var tSql = "1 and a.riskcode=#"+ fm2.RiskCode.value +"#";
		
		if (returnType=='0') {
			return showCodeList(value1,value2,value3,null,tSql,1,'1',null);
		} else {
			return showCodeListKey(value1,value2,value3,null,tSql,1,'1',null);
		}
	} 
	//����ѿ۽ɷ�ʽ
	if(value1=='deducttype') {
		
		var tSql = "deducttype|0";
		
		if (returnType=='0') {
			return showCodeList('queryexp',value2,value3,null,tSql,'codetype|codeexp','1',180);
		} else {
			return showCodeList('queryexp',value2,value3,null,tSql,'codetype|codeexp','1',180);
		}
	}
	//�¶ȹ��������
	if(value1=='deducttype1') {
		
		var tSql = "1 and code in (#100#,#101#)";
		
		if (returnType=='0') {
			return showCodeList('deducttype',value2,value3,null,tSql,1,'1',180);
		} else {
			return showCodeListKey('deducttype',value2,value3,null,tSql,1,'1',180);
		}
	}
}

/**
* ����ѡ���չʾ����Ҫ��
*/
function afterCodeSelect(o, p) {
	
	if(o=='quotrisk') {
		
		fm2.DutyCode.value = "";
		fm2.DutyName.value = "";
	}
}

/**
 * �������
 */
function profitClick() {
	
	initProfitInfoGrid();
	
	if (!verifyInput2()) {
		return false;
	}
	
	if (!checkDecimalFormat(fm2.InitPrem.value, '12', '2')) {
		alert("[Ԥ�Ƴ�ʼ����]����λ��Ӧ����12λ,С��λ��Ӧ����2λ��");
		return false;
	}
	
	var tMangFeeType = fm2.MangFeeType.value;
	var tInitMangFee = fm2.InitMangFee.value;
	if (tMangFeeType == "000" || tMangFeeType == "002" ) {
		
		if (!checkDecimalFormat(tInitMangFee, '12', '2')) {
			alert("[��ʼ�����(Ԫ)/����]����λ��Ӧ����12λ,С��λ��Ӧ����2λ��");
			return false;
		}
	}
	
	if (tMangFeeType == "001" || tMangFeeType == "003" ) {
		
		if (Number(tInitMangFee)<0 || Number(tInitMangFee)>1) {
			alert("[��ʼ�����(Ԫ)/����]Ӧ���ڵ���0��С�ڵ���1��");
			return false;
		}
	}
	
	var tMonthFeeType = fm2.MonthFeeType.value;
	var tMonthFee = fm2.MonthFee.value;
	if (tMonthFeeType == "100") {
		
		if (!checkDecimalFormat(tMonthFee, '12', '2')) {
			alert("[�¶ȹ����(Ԫ)/����]����λ��Ӧ����12λ,С��λ��Ӧ����2λ��");
			return false;
		}
	}
	
	if (tMonthFeeType == "101") {
		
		if (Number(tMonthFee)<0 || Number(tMonthFee)>1) {
			alert("[�¶ȹ����(Ԫ)/����]Ӧ���ڵ���0��С�ڵ���1��");
			return false;
		}
	}
	if (tMangFeeType == "000") {
		
		if (Number(tInitMangFee)>Number(fm2.InitPrem.value)) {
			alert("[��ʼ�����(Ԫ)/����]ӦС��[Ԥ�Ƴ�ʼ����]��");
			return false;
		}
	}
	
	mOperate = "TRYCAL";
	
	fm2.action = "./LSQuotCountProfitSave.jsp?Operate="+ mOperate +"&QuotNo="+ tQuotNo +"&QuotBatNo="+ tQuotBatNo +"&QuotType="+ tQuotType;
	submitForm(fm2);
	
}

/**
 * չʾMultiline
 */
function getQueryResult(strQueryResult) {
	
	var iArray;
	turnPage1 = new turnPageClass();
	//�����ѯ����ַ���	
	turnPage1.strQueryResult  = strQueryResult;
	//�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
	turnPage1.arrDataCacheSet = clearArrayElements(turnPage1.arrDataCacheSet);
	turnPage1.allArrDataCacheSet = clearArrayElements(turnPage1.allArrDataCacheSet);
	turnPage1.allCacheSize = 0;

	//ʹ��ģ������Դ������д�ڲ��֮ǰ
	turnPage1.useSimulation   = 1; 

	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult, 0, 0, turnPage1);
	var tKey = 1;

	//cTurnPage.allCacheSize ++;
	turnPage1.allArrDataCacheSet[turnPage1.allCacheSize%turnPage1.allArrCacheSize] = {id:tKey,value:turnPage1.arrDataCacheSet};			
	turnPage1.pageDisplayGrid = ProfitInfoGrid;
	turnPage1.pageIndex = 0;
	var arrDataSet = turnPage1.getData(turnPage1.arrDataCacheSet, turnPage1.pageIndex, turnPage1.pageLineNum);
	displayMultiline(arrDataSet, turnPage1.pageDisplayGrid, turnPage1);

		if (turnPage1.showTurnPageDiv==1) {
		try {
			ProfitInfoGrid.setPageMark(turnPage1);
		} catch(ex){}
	}
}

