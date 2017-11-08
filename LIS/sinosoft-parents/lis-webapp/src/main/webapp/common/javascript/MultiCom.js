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
	//如果没有给定货币精度
	if(moneyPrec == null){
		//如果没有给定货币类型
		if(moneyType == null){
			//如果没有给定精度和货币类型，则取默认国家的货币精度
			//alert(cpVar.getVar("DPrecision"));
			moneyPrec = cpVar.getVar("DPrecision");
		} else {
			//如果没有给定精度，但给定了货币类型，则取对应货币类型的精度
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
	
	//获得币种的精度
	moneyPrec = getMoneyPrec(moneyType,moneyPrec);
	
	//取出原始值
	var rawCurrency = document.getElementById(eleName).value;
	
	//选择币种显示的语言
	/*document.getElementById("Lable" + eleName).value = ( moneyType == null ? 
		I18NMsg("ldcode_amlcurrencytype_" + cpVar.getVar("DCurrency"))  : I18NMsg("ldcode_amlcurrencytype_" + moneyType));*/
	if(rawCurrency != null && rawCurrency != ""){
		//如果精度还有小数点，则取小数点后的位数，且值为负；如果没有小数点，则取小数点前的长度，值为正
		var dLength = ( moneyPrec.indexOf(".") != -1 ? -moneyPrec.substring(moneyPrec.indexOf(".") + 1).length : moneyPrec.length );
		if(dLength > 0 ){
			document.getElementById("Show" + eleName).value = parseInt(rawCurrency / Math.pow(10,dLength - 1));
		} else if(dLength < 0 ){
			if(("" +　document.getElementById(eleName).value).indexOf(".") != -1){
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
			alert("精度错误！");
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
	//获得币种的精度
	moneyPrec = getMoneyPrec(moneyType,moneyPrec);
	//获得显示的币值
	var showCurrency = document.getElementById("Show" + eleName).value;
	
	//如果精度还有小数点，则取小数点后的位数，且值为负；如果没有小数点，则取小数点前的长度，值为正
	var dLength = ( moneyPrec.indexOf(".") != -1 ? -moneyPrec.substring(moneyPrec.indexOf(".") + 1).length : moneyPrec.length );
	if( dLength > 0 ){
		showCurrency = showCurrency * Math.pow(10,dLength - 1) + "";
//		if(showCurrency.indexOf(".") != -1 ) showCurrency = showCurrency + ".00";
	} else if(dLength < 0 ){
		dLength = 0 - dLength;
		//舍掉多出的小数位
		var re = new RegExp("(\\.[0-9]{" + dLength + "})[0-9]*");
		showCurrency = showCurrency.replace(re,"$1") + "";
		//判断显示的值是否有小数位，有则取位数，没有则返回-1
		var sLength = ( showCurrency.indexOf(".") != -1 ? showCurrency.substring(showCurrency.indexOf(".") + 1).length : -1 );
		if(sLength == -1){
			//没有小数位，则补添精度对应的小数位
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
		alert("精度错误！");
	}
	document.getElementById(eleName).value = showCurrency;
}

function changeMultiDate(eleName,eleId)
{		
	if(eleId !="" && eleId !=null && eleId != undefined) eleName = eleId;
	dateFormat = dealNull(document.getElementById(eleName).dateformat);
//	if(dateFormat == null) dateFormat = eval("Date" + document.getElementById("Nation").value);
	//日期格式为空，则取默认登入的日期格式
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
		alert("日期格式错误！标准日期格式：" +　dateFormat);
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
	//在框架内和新打开的框架
	if(top.name == "Lis") windowObj = window.top;
	else windowObj = window.top.opener.top;
	//如果是多重打开，还需要再向上找，目前只考虑最多打开3层
	//三层明显不够用，多加几层 Edit by ningyc 20110922
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
	
	
	//如果还未得到，报错
	if(windowObj.name != "Lis"){
		alert("获取国际化配置错误，错误代码：dfvar1，请与管理员联系！");
		return null;
	}
		
	var dfVar = windowObj.VD.dfVar;
	if(dfVar == undefined || dfVar == null){
		alert("获取国际化配置错误，错误代码：dfvar2，请与管理员联系！");
		return null;
	} else {
		if(dfVar.marrayLength != -1) return dfVar;
	}
	//var strSQL = "select SysVarValue from LDSysVar where SysVar='nativeplace' ";
	var mySql=new SqlClass();
	 mySql.setResourceName("javascript.MultiComSql"); //指定使用的properties文件名
	 mySql.setSqlId("querysqldes1");//指定使用的Sql的id
	 mySql.addSubPara("");//指定传入的参数
	var strSQL = mySql.getString();
	var arrResult = easyExecSql(strSQL);
	if(arrResult == null || arrResult == ""){
		alert("没有部署国家信息错误！");
		parent.window.location = "../indexlis.jsp";
//		return false;
	} else {
		dfVar.addVar("nativeplace","",arrResult[0][0]);	
	}
	
	//strSQL = "select Code from ldcode1 where codetype='dateformat' and Code1='" + dfVar.getVar("nativeplace") + "'";
	mySql=new SqlClass();
	 mySql.setResourceName("javascript.MultiComSql"); //指定使用的properties文件名
	 mySql.setSqlId("querysqldes2");//指定使用的Sql的id
	 mySql.addSubPara(dfVar.getVar("nativeplace"));//指定传入的参数
	strSQL = mySql.getString();
	arrResult = easyExecSql(strSQL);
	if(arrResult == null || arrResult == ""){
		alert("日期格式信息错误！");
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
	//在框架内和新打开的框架
	if(top.name == "Lis") windowObj = window.top;
	else windowObj = window.top.opener.top;
	//如果是多重打开，还需要再向上找，目前只考虑最多打开3层
	//三层明显不够用，多加几层 Edit by ningyc 20110922
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
	
	//如果还未得到，报错
	if(windowObj.name != "Lis"){
		alert("获取国际化配置错误，错误代码：cpvar1，请与管理员联系！");
		return null;
	}
	var cpVar = windowObj.VD.cpVar;
	if(cpVar == undefined || cpVar == null){
		alert("获取国际化配置错误，错误代码：cpvar2，请与管理员联系！");
		return null;
	} else {
		if(cpVar.marrayLength != -1) return cpVar;
	}

	//var strSQL = "select Code,Code1,CodeName from ldcode1 where CodeType='currencyprecision' ";
	var mySql=new SqlClass();
	 mySql.setResourceName("javascript.MultiComSql"); //指定使用的properties文件名
	 mySql.setSqlId("querysqldes3");//指定使用的Sql的id
	 mySql.addSubPara("");//指定传入的参数
	var strSQL = mySql.getString();
	var arrResult = easyExecSql(strSQL);
	if(arrResult == null || arrResult == ""){
		alert("货币精度信息错误！");
		parent.window.location ="../indexlis.jsp";
	}
	
	for(var i=0;i<arrResult.length;i++){
		
		cpVar.addVar(arrResult[i][2],"",arrResult[i][0]);
		if(arrResult[i][1] == nativeplace){
//			alert(arrResult[i][0]);
			//默认货币
			cpVar.addVar("DCurrency","",arrResult[i][2]);
			//默认货币的精度
			cpVar.addVar("DPrecision","",arrResult[i][0]);
		}
	}
	return cpVar;
}
