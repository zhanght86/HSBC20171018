//var win = searchMainWindow(this);
//if (win == false) { win = this; }
//cpVar = win.parent.VD.cpVar;
//dfVar = win.parent.VD.dfVar;	

dfVar = getDfVar();
cpVar = getCpVar(dfVar.getVar("nativeplace"));

//alert(dfVar.getVar("nativeplace"));
//alert(dfVar.getVar("DDateFormat"));
//alert(cpVar.getVar("DCurrency"));
//alert(cpVar.getVar("DPrecision"));

function dealNull(FieldItem)
{	
	if(FieldItem == null || FieldItem == "null" || FieldItem == undefined || FieldItem == "undefined" || FieldItem == "") FieldItem = null;
	return FieldItem;
}

function getMoneyPrec(moneyType,moneyPrec)
{	
	//���û�и������Ҿ���
	if(moneyPrec == null){
		//���û�и�����������
		if(moneyType == null){
			//���û�и������Ⱥͻ������ͣ���ȡĬ�Ϲ��ҵĻ��Ҿ���
			//alert(cpVar.getVar("DPrecision"));
			moneyPrec = cpVar.getVar("DPrecision");
		} else {
			//���û�и������ȣ��������˻������ͣ���ȡ��Ӧ�������͵ľ���
			moneyPrec = cpVar.getVar(moneyType.toUpperCase());
		}
	}
	
	return moneyPrec;
}

function changeCurrency(eleName,eleId)
{	
	if(eleId !="" && eleId !=null && eleId != undefined) eleName = eleId;
	
	var moneyType = dealNull(document.getElementById(eleName).moneytype);
	var moneyPrec = dealNull(document.getElementById(eleName).moneyprec);
	
	if(document.getElementById(eleName).amtcol != null && document.getElementById(eleName).amtcol != "" ){
//		document.getElementById(eleName).moneytype = document.getElementById(document.getElementById(eleName).amtcol).value;
		moneyType = dealNull(document.getElementById(document.getElementById(eleName).amtcol).value);
	}
	
	//��ñ��ֵľ���
	moneyPrec = getMoneyPrec(moneyType,moneyPrec);
	
	//ȡ��ԭʼֵ
	var rawCurrency = document.getElementById(eleName).value;
	
	//ѡ�������ʾ������
	/*document.getElementById("Lable" + eleName).value = ( moneyType == null ? 
		I18NMsg("ldcode_amlcurrencytype_" + cpVar.getVar("DCurrency"))  : I18NMsg("ldcode_amlcurrencytype_" + moneyType));*/
	if(rawCurrency != null && rawCurrency != ""){
		//������Ȼ���С���㣬��ȡС������λ������ֵΪ�������û��С���㣬��ȡС����ǰ�ĳ��ȣ�ֵΪ��
		var dLength = ( moneyPrec.indexOf(".") != -1 ? -moneyPrec.substring(moneyPrec.indexOf(".") + 1).length : moneyPrec.length );
		if(dLength > 0 ){
			document.getElementById("Show" + eleName).value = parseInt(rawCurrency / Math.pow(10,dLength - 1));
		} else if(dLength < 0 ){
			if(("" +��document.getElementById(eleName).value).indexOf(".") != -1){
				var re = new RegExp("(\\.[0-9]{" + (-dLength) + "})[0-9]*");
				document.getElementById("Show" + eleName).value = document.getElementById(eleName).value.replace(re,"$1");
			} else {
				var dStr = "";
				for(var i = 0;i< -dLength ; i++){
					dStr = dStr + "0";
				}
				document.getElementById("Show" + eleName).value = document.getElementById(eleName).value + "." + dStr;
			}
		} else {
			alert("���ȴ���");
		}
	} else {
		try{
			document.getElementById("Show" + eleName).value = "";
		} catch(ex){
		}
			
	}
}

function changeRawCurrency(eleName,eleId)
{	
	if(eleId !="" && eleId !=null && eleId != undefined) eleName = eleId;
	
	var moneyType = dealNull(document.getElementById(eleName).moneytype);
	var moneyPrec = dealNull(document.getElementById(eleName).moneyprec);
	if(document.getElementById(eleName).amtcol != null ){
//		document.getElementById(eleName).moneytype = document.getElementById(document.getElementById(eleName).amtcol).value;
		moneyType = dealNull(document.getElementById(document.getElementById(eleName).amtcol).value);
	}
	//��ñ��ֵľ���
	moneyPrec = getMoneyPrec(moneyType,moneyPrec);
	//�����ʾ�ı�ֵ
	var showCurrency = document.getElementById("Show" + eleName).value;
	
	//������Ȼ���С���㣬��ȡС������λ������ֵΪ�������û��С���㣬��ȡС����ǰ�ĳ��ȣ�ֵΪ��
	var dLength = ( moneyPrec.indexOf(".") != -1 ? -moneyPrec.substring(moneyPrec.indexOf(".") + 1).length : moneyPrec.length );
	if( dLength > 0 ){
		showCurrency = showCurrency * Math.pow(10,dLength - 1) + "";
//		if(showCurrency.indexOf(".") != -1 ) showCurrency = showCurrency + ".00";
	} else if(dLength < 0 ){
		dLength = 0 - dLength;
		//��������С��λ
		var re = new RegExp("(\\.[0-9]{" + dLength + "})[0-9]*");
		showCurrency = showCurrency.replace(re,"$1") + "";
		//�ж���ʾ��ֵ�Ƿ���С��λ������ȡλ����û���򷵻�-1
		var sLength = ( showCurrency.indexOf(".") != -1 ? showCurrency.substring(showCurrency.indexOf(".") + 1).length : -1 );
		if(sLength == -1){
			//û��С��λ�������ȶ�Ӧ��С��λ
			var dStr = ".";
			for(var i = 0;i < dLength ; i++){
				dStr = dStr + "0";
			}
			showCurrency = showCurrency + dStr;
		} else {
			if( dLength > sLength ){
				var dStr = "";
				for(var i = 0;i< dLength -sLength ; i++){
					dStr = dStr + "0";
				}
				showCurrency = showCurrency + dStr;
			}
		}
	} else {
		alert("���ȴ���");
	}
	document.getElementById(eleName).value = showCurrency;
}

function changeMultiDate(eleName,eleId)
{		
	if(eleId !="" && eleId !=null && eleId != undefined) eleName = eleId;
	dateFormat = dealNull(document.getElementById(eleName).dateformat);
//	if(dateFormat == null) dateFormat = eval("Date" + document.getElementById("Nation").value);
	//���ڸ�ʽΪ�գ���ȡĬ�ϵ�������ڸ�ʽ
	if(dateFormat == null) dateFormat = dfVar.getVar("DDateFormat");

	var showDate = dateFormat ;
	var rawDate = document.getElementById(eleName).value;
	if(rawDate == "" || rawDate == null){
		return ;
	}
	
	var rawDateSplit = rawDate.split("-");
	if( rawDate != "ErrorDate" ){
		showDate = showDate.replace(/YYYY/g,rawDateSplit[0]);
		showDate = showDate.replace(/MM/g,rawDateSplit[1]);
		showDate = showDate.replace(/DD/g,rawDateSplit[2]);
		document.getElementById("Show" + eleName).value = showDate;
	}

}

function changeRawDate(eleName,eleId)
{	
	if(eleId !="" && eleId !=null && eleId != undefined) eleName = eleId;
	dateFormat = dealNull(document.getElementById(eleName).dateformat);	
	
//	if(dateFormat == null) dateFormat = eval("Date" + document.getElementById("Nation").value);
	if(dateFormat == null) dateFormat = dfVar.getVar("DDateFormat");
	var showDate = document.getElementById("Show"+eleName).value;
	if(dateFormat.length != showDate.length && showDate.length!=0){
		rawDate = "ErrorDate";
		alert("���ڸ�ʽ���󣡱�׼���ڸ�ʽ��" +��dateFormat);
	} else if (showDate.length == 0){
		rawDate = "";
	}else {
		var rawDate = "YYYY-MM-DD";
		rawDate = rawDate.replace(/YYYY/g,showDate.substr(dateFormat.indexOf("Y"),4));
		rawDate = rawDate.replace(/MM/g,showDate.substr(dateFormat.indexOf("M"),2));
		rawDate = rawDate.replace(/DD/g,showDate.substr(dateFormat.indexOf("D"),2));
	}
	document.getElementById(eleName).value = rawDate;
}


function showIdentityList(eleName)
{	
	var CodeCondition = "Code1";
	if(document.getElementById(eleName).condition != undefined || document.getElementById(eleName).condition != null){
		CodeCondition = document.getElementById(eleName).condition + " and " + CodeCondition ;
	}
	showCodeList('mulidtype',[document.getElementById(eleName),document.getElementById(eleName+"Code")],[1,0],null,dfVar.getVar("nativeplace"),CodeCondition);
}

function isDisplayIdentity(eleName)
{	
	if(dfVar.getVar("nativeplace") == "JAN"){
		document.getElementById(eleName).style.display="none";
		document.getElementById(eleName + "No").style.display="none";
	}
}

function checkIdentity(eleName)
{	
	checkMultiIdNo(dfVar.getVar("nativeplace"),document.getElementById(eleName+"Code").value,document.getElementById(eleName+"No").value);
}

function getDfVar()
{	
	var windowObj;
	//�ڿ���ں��´򿪵Ŀ��
	if(top.name == "Lis") windowObj = window.top;
	else windowObj = window.top.opener.top;
	//����Ƕ��ش򿪣�����Ҫ�������ң�Ŀǰֻ��������3��
	//�������Բ����ã���Ӽ��� Edit by ningyc 20110922
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	
	
	//�����δ�õ�������
	if(windowObj.name != "Lis"){
		alert("��ȡ���ʻ����ô��󣬴�����룺dfvar1���������Ա��ϵ��");
		return null;
	}
		
	var dfVar = windowObj.VD.dfVar;
	if(dfVar == undefined || dfVar == null){
		alert("��ȡ���ʻ����ô��󣬴�����룺dfvar2���������Ա��ϵ��");
		return null;
	} else {
		if(dfVar.marrayLength != -1) return dfVar;
	}
	//var strSQL = "select SysVarValue from LDSysVar where SysVar='nativeplace' ";
	var mySql=new SqlClass();
	 mySql.setResourceName("javascript.MultiComSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId("querysqldes1");//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara("");//ָ������Ĳ���
	var strSQL = mySql.getString();
	var arrResult = easyExecSql(strSQL);
	if(arrResult == null || arrResult == ""){
		alert("û�в��������Ϣ����");
		parent.window.location = "../indexlis.jsp";
//		return false;
	} else {
		dfVar.addVar("nativeplace","",arrResult[0][0]);	
	}
	
	//strSQL = "select Code from ldcode1 where codetype='dateformat' and Code1='" + dfVar.getVar("nativeplace") + "'";
	mySql=new SqlClass();
	 mySql.setResourceName("javascript.MultiComSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId("querysqldes2");//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(dfVar.getVar("nativeplace"));//ָ������Ĳ���
	strSQL = mySql.getString();
	arrResult = easyExecSql(strSQL);
	if(arrResult == null || arrResult == ""){
		alert("���ڸ�ʽ��Ϣ����");
		parent.window.location ="../indexlis.jsp";
//		return false;
	} else {
//		alert(arrResult[0][0]);
		dfVar.addVar("DDateFormat","",arrResult[0][0]);
	}
	
	return dfVar;	
}

function getCpVar(nativeplace)
{	
	var windowObj;
	//�ڿ���ں��´򿪵Ŀ��
	if(top.name == "Lis") windowObj = window.top;
	else windowObj = window.top.opener.top;
	//����Ƕ��ش򿪣�����Ҫ�������ң�Ŀǰֻ��������3��
	//�������Բ����ã���Ӽ��� Edit by ningyc 20110922
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	if(windowObj.name != "Lis")
		windowObj = windowObj.opener.top;
	
	//�����δ�õ�������
	if(windowObj.name != "Lis"){
		alert("��ȡ���ʻ����ô��󣬴�����룺cpvar1���������Ա��ϵ��");
		return null;
	}
	var cpVar = windowObj.VD.cpVar;
	if(cpVar == undefined || cpVar == null){
		alert("��ȡ���ʻ����ô��󣬴�����룺cpvar2���������Ա��ϵ��");
		return null;
	} else {
		if(cpVar.marrayLength != -1) return cpVar;
	}

	//var strSQL = "select Code,Code1,CodeName from ldcode1 where CodeType='currencyprecision' ";
	var mySql=new SqlClass();
	 mySql.setResourceName("javascript.MultiComSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId("querysqldes3");//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara("");//ָ������Ĳ���
	var strSQL = mySql.getString();
	var arrResult = easyExecSql(strSQL);
	if(arrResult == null || arrResult == ""){
		alert("���Ҿ�����Ϣ����");
		parent.window.location ="../indexlis.jsp";
	}
	
	for(var i=0;i<arrResult.length;i++){
		
		cpVar.addVar(arrResult[i][2],"",arrResult[i][0]);
		if(arrResult[i][1] == nativeplace){
//			alert(arrResult[i][0]);
			//Ĭ�ϻ���
			cpVar.addVar("DCurrency","",arrResult[i][2]);
			//Ĭ�ϻ��ҵľ���
			cpVar.addVar("DPrecision","",arrResult[i][0]);
		}
	}
	return cpVar;
}
