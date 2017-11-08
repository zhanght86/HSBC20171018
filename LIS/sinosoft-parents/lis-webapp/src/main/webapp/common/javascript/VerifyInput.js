/**************************************************************************
 * 程序名称: VerifyInput.js
 * 程序功能: 通用校验函数Ver2
 *           比Ver1增加科学记数法校验，校验类型间可进行“与（&）”、“或（|）”运算
 * 创建人  : 胡 博
 * 最近更新日期: 2002-10-22
**************************************************************************/
var arrVerifyErrInfo = new Array();   //记录一个字段的校验错误信息

function verifyClass()
{
	this.verifyInput = verifyInput;
	this.verifyForm = verifyForm;
	this.verifyElement = verifyElement;
	this.verifyType = verifyType;
	this.verifyMustNull = verifyMustNull;
	this.verifyNotNull = verifyNotNull;
	this.verifyNumber = verifyNumber;
	this.verifyDate = verifyDate;
	this.verifyEmail = verifyEmail;
	this.verifyDecimal=verifyDecimal;
	this.verifyInteger = verifyInteger;
	this.verifyLength = verifyLength;
	this.verifyValue = verifyValue;
	//this.verifyCode = verifyCode;
  this.verifyCode = verifyCodeNew;
	this.verifyCheckDifferent = verifyCheckDifferent;
}

//业务程序调用接口，如果通过校验返回true，否则返回false
function verifyInput()
{
	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var passFlag = true;	//校验通过标志

	//遍历所有FORM
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		//遍历FORM中的所有ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			//元素校验属性verify不为NULL
			if (window.document.forms[formsNum].elements[elementsNum].attributes.verify != null && window.document.forms[formsNum].elements[elementsNum].attributes.verify.nodeValue != "")
			{
				//进行校验verifyElement
				if (!verifyElement(window.document.forms[formsNum].elements[elementsNum].attributes.verify.nodeValue, window.document.forms[formsNum].elements[elementsNum].value))
				{
					passFlag = false;
					break;
				}
			}
		}
		if (!passFlag) break;
	}

	//if(passFlag) alert("通过校验");
	return passFlag;
}

//业务程序调用接口，以FORM为单位，如果通过校验返回true，否则返回false
function verifyForm(formName)
{
	var elementsNum = 0;	//FORM中的元素数
	var passFlag = true;	//校验通过标志

	//遍历FORM中的所有ELEMENT
	for (elementsNum=0; elementsNum<window.document.all(formName).elements.length; elementsNum++)
	{
		//元素校验属性verify不为NULL
		if (window.document.all(formName).elements[elementsNum].attributes.verify != null && window.document.all(formName).elements[elementsNum].attributes.verify.nodeValue != "")
		{
			//进行校验verifyElement
			if (!verifyElement(window.document.all(formName).elements[elementsNum].attributes.verify.nodeValue, window.document.all(formName).elements[elementsNum].value))
			{
				passFlag = false;
				break;
			}
		}
	}

	//if(passFlag) alert("通过校验");
	return passFlag;
}

//校验元素，strInfo为元素校验信息，strValue为元素值
function verifyElement(strInfo, strValue, boxName)
{
	var strValue = trim(strValue);	//清空空格
	var passFlag = true;	//校验通过标志，true表示通过
	var vName;	//校验字段名称
	var vType;	//要进行的校验类型
	var intIndex;	//运算符索引
	var typeStack = new Array();	//一个字段的校验结果堆栈
	var operStack = new Array();	//一个字段的校验计算符号堆栈，仅限于“与”，“或”计算
	var strboxName = boxName;

	while (arrVerifyErrInfo != "")
	{
		//清空前一个字段的校验错误信息
		arrVerifyErrInfo.pop();
	}

	//分离出字段名称，兼容前一版本，故仍用“|”分隔
	vName = strInfo.substring(0, strInfo.indexOf("|"));
	strInfo = strInfo.substring(strInfo.indexOf("|") + 1);

	//拆分出校验类型，并进行校验，返回校验结果（通过TRUE，否FALSE），并入堆栈
	while (strInfo != "")
	{
		if (strInfo.indexOf("|") != -1 && strInfo.indexOf("&") != -1)
		{
			//存在两种运算
			intIndex = strInfo.indexOf("|")>strInfo.indexOf("&")?strInfo.indexOf("&"):strInfo.indexOf("|");
			vType = strInfo.substring(0, intIndex);
			typeStack.push(verifyType(vName, vType, strValue,strboxName));
			operStack.push(strInfo.substring(intIndex, intIndex + 1));
			strInfo = strInfo.substring(intIndex + 1);
		}
		else if (strInfo.indexOf("|") != -1 || strInfo.indexOf("&") != -1)
			{
				//只有一种运算
				intIndex = strInfo.indexOf("|")>strInfo.indexOf("&")?strInfo.indexOf("|"):strInfo.indexOf("&");
				vType = strInfo.substring(0, intIndex);
				typeStack.push(verifyType(vName, vType, strValue,strboxName));
				operStack.push(strInfo.substring(intIndex, intIndex + 1));
				strInfo = strInfo.substring(intIndex + 1);
			}
			else
			{
				//无运算
				vType = strInfo;
				strInfo = "";
				typeStack.push(verifyType(vName, vType, strValue,strboxName));
			}
	}

	passFlag = typeStack[0];
	//只有一个校验类型时
	for (var k=0; k<operStack.length; k++)
	{
		//有多个校验类型，进行运算
		if (operStack[k] == "|")
		{
			typeStack[k + 1] = typeStack[k] | typeStack[k + 1];
		}
		else if (operStack[k] == "&")
			{
				typeStack[k + 1] = typeStack[k] & typeStack[k + 1];
			}
			else
			{
				alert("校验参数设置有误");
			}
		passFlag = typeStack[k + 1];
	}

	var strVerifyErrInfo = "输入有误，可能是如下错误中的一个： \n";
	if (!passFlag)
	{
		while (arrVerifyErrInfo != "")
		{
			strVerifyErrInfo = strVerifyErrInfo + arrVerifyErrInfo.pop();
		}
		alert(strVerifyErrInfo);
	}
	return passFlag;
}

function verifyType(vName, vType, strValue, boxName)
{
	var passFlag = true;
	//tongmeng 2008-05-07 modify
	//如果字段不为必录项,又没有录入数据,那么不做code的校验,减少数据库查询次数
	var notnullFlag = false;

	if (vType.toUpperCase() == "NULL") passFlag = verifyMustNull(vName, strValue);

	if (vType.toUpperCase() == "NOTNULL") 
	{
		passFlag = verifyNotNull(vName, strValue);
		//notnullFlag = true;
	}

	if (vType.toUpperCase() == "NUM") passFlag = verifyNumber(vName, strValue);

	if (vType.toUpperCase() == "DATE") passFlag = verifyDate(vName, strValue);

	if (vType.toUpperCase() == "EMAIL") passFlag = verifyEmail(vName, strValue);

  if (vType.toUpperCase() == "DECIMAL") passFlag = verifyDecimal(vName, strValue);

	if (vType.toUpperCase() == "INT") passFlag = verifyInteger(vName, strValue);

	if (vType.toUpperCase() == "CHECKDIFFERENT") passFlag = verifyCheckDifferent(vName, strValue, boxName);

	if (vType.toUpperCase() == "PHONE") passFlag = verifyPhone(vName, strValue);

	if (vType.toUpperCase() == "FLOAT") passFlag = verifyFloat(vName, strValue);
	
	if (vType.toUpperCase() == "TEL") passFlag = verifyTel(vName, strValue);


	//不能随意修改约定“LEN”
	if (vType.toUpperCase().indexOf("LEN") == 0) passFlag = verifyLength(vName, strValue, vType);

	//不能随意修改约定“VALUE”
	if (vType.toUpperCase().indexOf("VALUE") == 0) passFlag = (verifyNumber(vName, strValue) && verifyValue(vName, strValue, vType));

	//不能随意修改约定“CODE:”
	if (vType.toUpperCase().indexOf("CODE:") == 0) 
	{
		if(strValue != "")
		//只有不为空才做校验
		{
			//verifyCodeNew
			//passFlag = verifyCode(vName, strValue, vType);
			passFlag = verifyCodeNew(vName, strValue, vType);
		}
	}

	//不能随意修改约定“CODE:”
	if (vType.toUpperCase() == "ZIPCODE") passFlag = (verifyLength(vName, strValue, "len=6") && verifyNumber(vName, strValue));



	return passFlag;
}

//返回true表示通过校验，返回false表示不通过，数据违法
//必须为空校验
function verifyMustNull(vName, strValue) {
	if (strValue != "")
	{
		arrVerifyErrInfo.push(vName + "必须为空！\n");
		return false;
	}
	return true;
}

//不能为空校验
function verifyNotNull(vName, strValue)
{
	if (strValue == "")
	{
		arrVerifyErrInfo.push(vName + "不能为空！\n");
		return false;
	}
	return true;
}

//数字类型校验
function verifyNumber(vName, strValue)
{
//	if (strValue != "" && isNaN(parseFloat(strValue)) && !isNumeric(strValue))
	if (strValue != "" && !isNumeric(strValue))
	{
		arrVerifyErrInfo.push(vName + "不是有效的数字！\n");
		return false;
	}
	return true;
}
//数字类型校验
function verifyFloat(vName, strValue)
{
	if (strValue != "" && isNaN(parseFloat(strValue)) && !isNumeric(strValue))
//	if (strValue != "" && !isNumeric(strValue))
	{
		arrVerifyErrInfo.push(vName + "不是有效的数字！\n");
		return false;
	}
	return true;
}

//日期类型校验
function verifyDate(vName, strValue) {
	if (strValue != "" && !isDate(strValue)&&!isDateN(strValue))
	{
		arrVerifyErrInfo.push(vName + "不是有效的日期格式(YYYY-MM-DD)或者(YYYYMMDD)！\n");
		return false;
	}
	return true;
}

function verifyEmail (vName, strValue)
{
	if (strValue != "")
	{
		var s=strValue;
		var i = 1;
		var len = s.length;
		if (len > 50)
		{
			arrVerifyErrInfo.push(vName +"email地址长度不能超过50位！");
			return false;
		}
		var pos1 = s.indexOf("@");
		var pos2 = s.indexOf(".");
		var pos3 = s.lastIndexOf("@");
		var pos4 = s.lastIndexOf(".");

		if ((pos1 <= 0)||(pos1 == len)||(pos2 <= 0)||(pos2 == len))
		{
			arrVerifyErrInfo.push(vName +"请输入有效的e-mail地址！");
			return false;
		}
		else
		{
			if( (pos1 == pos2 - 1) || (pos1 == pos2 + 1)|| ( pos1 != pos3 )|| ( pos4 < pos3 ) )
			{
				arrVerifyErrInfo.push(vName +"请输入有效的e-mail地址！");
				return false;
    		}
    	}
		if ( !isCharsInBag( s, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-_@"))
		{
			arrVerifyErrInfo.push(vName +"email地址中只能包含字符ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-_@\n"+"请重新输入" );
			return false;
		}
		var badChar ="><,[]{}?/+=|\:;!#$%^&()`";
		if (isCharsInBag(s,badChar))
		{
			arrVerifyErrInfo.push(vName +"请不要在email地址中输入字符 " + badChar + "\n" );
			return false;
		}
	}
	return true;
}
//在0到1之间的数值(0,1]
function verifyDecimal(vName, strValue) {
        if (strValue != "" &&(parseFloat(strValue)<=0||parseFloat(strValue)>1) )
        {
		arrVerifyErrInfo.push(vName + "不是0到1之间的小数！\n");
		return false;
	}
	return true;
}
//整数类型校验
function verifyInteger(vName, strValue) {
	if (strValue != "" && !isInteger(strValue))
	{
		arrVerifyErrInfo.push(vName + "不是有效的整数！\n");
		return false;
	}
	return true;
}

//输入长度校验
function verifyLength(vName, strValue, vType) {
	var oper;
	var len;
	var strOperLen = vType.substring(3);	//截取出操作符和值

	if (strValue == "") return true;

	if (isNaN(parseInt(strOperLen.substring(1))))
	{
		oper = strOperLen.substring(0, 2);	//当为">=", "<="时
		len = strOperLen.substring(2);
	}
	else
	{
		oper = strOperLen.substring(0, 1);	//当为"=", "<", ">"时
		len = strOperLen.substring(1);
	}

	switch (oper)
	{
		case "=" :
			if (strValue.length != parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "不符合规定的输入长度！长度需要等于" + len + "。\n");
				return false;
			}
			break;
		case ">" :
			if (strValue.length <= parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "不符合规定的输入长度！长度需要大于" + len + "。\n");
				return false;
			}
			break;
		case "<" :
			if (strValue.length >= parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "不符合规定的输入长度！长度需要小于" + len + "。\n");
				return false;
			}
			break;
		case ">=" :
			if (strValue.length < parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "不符合规定的输入长度！长度需要大于等于" + len + "。\n");
				return false;
			}
			break;
		case "<=" :
			if (strValue.length > parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "不符合规定的输入长度！长度需要小于等于" + len + "。\n");
				return false;
			}
			break;
	}
	return true;
}

//输入值校验
function verifyValue(vName, strValue, vType)
{
	var oper;
	var Val;
	var strOperVal = vType.substring(5);	//截取出操作符和值

	if (strValue == "") return true;

	if (isNaN(parseFloat(strOperVal.substring(1))))
	{
		oper = strOperVal.substring(0, 2);	//当为">=", "<="时
		Val = strOperVal.substring(2);
	}
	else
	{
		oper = strOperVal.substring(0, 1);	//当为"=", "<", ">"时
		Val = strOperVal.substring(1);
	}

	switch (oper)
	{
		case "=" :
			if (parseFloat(strValue) != parseFloat(Val))
			{
				arrVerifyErrInfo.push(vName + "不符合规定的取值！输入值需要等于" + Val + "。\n");
				return false;
			}
			break;
		case ">" :
			if (parseFloat(strValue) <= parseFloat(Val))
			{
				arrVerifyErrInfo.push(vName + "不符合规定的取值！输入值需要大于" + Val + "。\n");
				return false;
			}
			break;
		case "<" :
			if (parseFloat(strValue) >= parseFloat(Val))
			{
				arrVerifyErrInfo.push(vName + "不符合规定的取值！输入值需要小于" + Val + "。\n");
				return false;
			}
			break;
		case ">=" :
			if (parseFloat(strValue) < parseFloat(Val))
			{
				arrVerifyErrInfo.push(vName + "不符合规定的取值！输入值需要大于等于" + Val + "。\n");
				return false;
			}
			break;
		case "<=" :
			if (parseFloat(strValue) > parseFloat(Val))
			{
	//alert(vName + "不符合规定的取值!输入值需要小于等于"+Val);
	arrVerifyErrInfo.push(vName + "不符合规定的取值！输入值需要小于等于" + Val + "。\n");
	return false;
	}
	break;
	}
	return true;
	}

//代码类型校验，可与CodeSelect功能配合
function verifyCode(vName, strValue, vType, returnCode)
{
	var strCode = vType.substring(5)	//截取出代码类型标志
	var arrCode = mVs.getVar(strCode);	//从内存读数据
	var passFlag = false;	//校验通过标志，true表示通过
	var arrRecord;	//拆分的记录数组
	var arrField;	//拆分的字段数组
	var recordNum;	//记录数
	var fieldNum;	//字段数
	var arrResult = new Array();	//结果数组，模仿CodeSelect数据格式
	var urlStr = "../common/jsp/CodeQueryWindow.jsp?codeType=" + strCode;
	//urlStr：查询窗口URL和查询参数
	var sFeatures = "status:no;help:0;close:0;dialogWidth:150px;dialogHeight:0px;dialogLeft:-1;dialogTop:-1;resizable=1";
	//sFeatures：查询窗口样式
	var strCodeSelect = "";
	if (strValue == "") return true;

	//内存中有数据，直接进行校验
	if (arrCode != false)
	{
		for (var i=0; i<arrCode.length; i++)
		{
			for (var j=0; j<arrCode[i].length; j++)
			{
				if (strValue == arrCode[i][j])
				{
					//在个人承保录入的校验职业和职业代码中用到
					if (typeof(returnCode) != "undefined") return arrCode[i];
					passFlag = true;
					break;
				}
			}
		}
	}
	else
	{
		//连接数据库进行CODE查询，返回查询结果给arrCode
		arrCode = window.showModalDialog(urlStr, "", sFeatures);

		if ((arrCode == false) || (arrCode == ""))
		{
			//等待从数据库端取回数据
			arrVerifyErrInfo.push("CODE查询功能错误，请与管理员联系！\n");
			return false;
		}
		else if (arrCode == "Code Query Faile")
			{
				//查询数据库失败处理
				arrVerifyErrInfo.push(vName + " 数据库代码查询失败，请与管理员联系！\n");
				return false;
			}
			else
			{
				arrRecord = arrCode.split("^");	//拆分记录，形成返回的数组
				recordNum = arrRecord.length;
				for (i=1; i<recordNum; i++)
				{
					arrField  = arrRecord[i].split("|");	//拆分字段,将每个纪录拆分为一个数组
					fieldNum = arrField.length;
					arrResult[i-1] = new Array();
					for (j=0;j<fieldNum;j++)
					{
						arrResult[i-1][j] = arrField[j];
					}
					strCodeSelect = strCodeSelect + "<option value=" + arrResult[i-1][0] + ">";
					strCodeSelect = strCodeSelect + arrResult[i-1][0] + "-" + arrResult[i-1][1];
					strCodeSelect = strCodeSelect + "</option>";
				}
				mVs.addArrVar(strCode,"",arrResult);	//保存进内存，提供给CodeSelect共享
				mVs.addVar(strCode+"Select","",strCodeSelect);	//无论是否有数据从服务器端得到,都设置该变量

				for (i=0; i<arrResult.length; i++)
				{
					//进行校验
					for (j=0; j<arrResult[i].length; j++)
					{
						if (strValue == arrResult[i][j])
						{
							//在个人承保录入的校验职业和职业代码中用到
							if (typeof(returnCode) != "undefined") return arrResult[i];
							passFlag = true;
							break;
						}
					}
				}
			}
	}
	if (!passFlag)
	{
		arrVerifyErrInfo.push(vName + "输入不符合数据库规定的取值范围！请查阅或双击输入框选择！\n");
	}
	return passFlag;
}

function verifyElementWrap(strInfo, strValue,boxName)
{
	var strboxName,strfocus,strcolor,cleardata;
	strboxName=boxName;
	chkoldclass="if(!"+strboxName+".oldclass) \n"+strboxName+".oldclass="+strboxName+".className;";
	eval(chkoldclass);
	if (!verifyElement(strInfo, strValue))
	{
		strfocus=strboxName+".onblur="+strboxName+".focus;";
		strcolor=strboxName+".className=\"warn\";";
		cleardata=strboxName+".value='';";
		eval(strcolor);
		eval(cleardata);
		eval(strfocus);
		return false;
	}
	strfocus=strboxName+".onblur=null";
	strcolor=strboxName+".className="+strboxName+".oldclass;";
	eval(strcolor);
	eval(strfocus);
	return true;
}

//add with wzw 2004-10-12
function verifyElementWrap2(strInfo, strValue,boxName,tName)
{
	var strboxName,strfocus,strcolor,cleardata;
	strboxName=boxName;
	chkoldclass="if("+strboxName+".getAttribute('oldclass') == null || "+strboxName+".getAttribute('oldclass') == '') \n"+strboxName+".setAttribute('oldclass',"+strboxName+".className);";
	eval(chkoldclass);
	if (!verifyElement(strInfo, strValue,strboxName))
	{
		strfocus = "try { " + strboxName + ".focus();" + " } catch (ex) {}";    //XinYQ modified on 2005-12-05 : old : strfocus=strboxName+".focus();";
		if (eval(boxName + ".className") == "codeno" || eval(boxName + ".className") == "warnno")    //XinYQ modified on 2005-12-05 : old : if (eval(boxName+".className") == "codeno")
		{
		 	strcolor = strboxName + ".className=\"warnno\";";
		}
		else if (eval(boxName + ".className") == "coolDatePicker" || eval(boxName + ".className") == "warndate")    //XinYQ added on 2006-03-26 : 解决 coolDatePicker 变长的问题
		{
		    strcolor = strboxName + ".className=\"warndate\";";
		}
		else if (eval(boxName + ".className") == "common3" || eval(boxName + ".className") == "warn3")    //XinYQ added on 2006-09-21 : 解决 common3 变短的问题
		{
		    strcolor = strboxName + ".className=\"warn3\";";
		}
		
		else if (eval(boxName + ".className") == "multiDatePicker" || eval(boxName + ".className") == "warndate")    //XinYQ added on 2006-03-26 : 解决 coolDatePicker 变长的问题
		{
			//alert('strboxName:'+strboxName+':'+eval(boxName + ".className")+":"+document.getElementById("Show"+strboxName).value);
		    strcolor = strboxName + ".className=\"multiDatePicker\";";
		    
		    document.getElementById("Show"+tName).className = "warndate";
		    document.getElementById("Show"+tName).value = "";
		    document.getElementById(tName).value = "";
		}
		
	 else
	 {
			strcolor = strboxName + ".className=\"warn\";";
		}

		
		//alert(eval(boxName + ".className"));
			cleardata=strboxName+".value='';";
	
		eval(strcolor);
		eval(cleardata);
		eval(strfocus);
		return false;
	}
	strcolor=strboxName+".className="+strboxName+".getAttribute('oldclass');";
	eval(strcolor);
	return true;
}

//业务程序调用接口，如果通过校验返回true，否则返回false
function verifyInput2()
{

	var formsNum = 0;	//窗口中的FORM数
	var elementsNum = 0;	//FORM中的元素数
	var passFlag = true;	//校验通过标志
	//遍历所有FORM
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		//遍历FORM中的所有ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			//元素校验属性verify不为NULL
			if (window.document.forms[formsNum].elements[elementsNum].attributes.verify != null && window.document.forms[formsNum].elements[elementsNum].attributes.verify.nodeValue != "")
			{
				var verifyid = window.document.forms[formsNum].elements[elementsNum].id;
				if(verifyid == null || verifyid == ""){
					verifyid = window.document.forms[formsNum].elements[elementsNum].name;
					verifyid = "document.getElementsByName('"+verifyid+"')[0]";
				}
				else{
					verifyid = "document.getElementById('"+verifyid+"')";
				}
				
				//进行校验verifyElement
				if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].attributes.verify.nodeValue, window.document.forms[formsNum].elements[elementsNum].value,verifyid,window.document.forms[formsNum].elements[elementsNum].name))
				{
					passFlag = false;
					break;
				}
			}
		}
		if (!passFlag) break;
	}
	return passFlag;
}

//验证重复输入是否不同
function verifyCheckDifferent(vName, strValue, boxName)
{
	    var ConfirmValue = boxName.substring(0, boxName.indexOf(".")+1)+"Confirm"+boxName.substring(boxName.indexOf(".")+1,boxName.length)+".value";
	    if(strValue!=""&& eval(ConfirmValue)=="" )
		{
				arrVerifyErrInfo.push(vName + "请在重复输入处填值！\n");
				return false;
		}
	    if(strValue!=""&& eval(ConfirmValue)!="" && strValue!=eval(ConfirmValue))
		{
				arrVerifyErrInfo.push(vName + "两次输入有误！\n");
				return false;
		}
		return true;
}

function verifyPhone(vName, strValue){

	if (strValue != "" && !isPhoneNo(strValue))
	{
		arrVerifyErrInfo.push(vName + "不是有效的电话号码！\n");
		return false;
	}
	return true;


}


function isPhoneNo(strValue)
{


  var NUM="0123456789+-";
  var i;
  if(strValue==null ||strValue=="") return false;
  for(i=0;i<strValue.length;i++)
  {
    if(NUM.indexOf(strValue.charAt(i))<0) return false

  }
//  if(strValue.indexOf("-")!=strValue.lastIndexOf("-")) return false;
  if(strValue.indexOf("+")!=strValue.lastIndexOf("+")) return false;
  return true;
}

/*
 *add by sunsx 2008-09-10
	判断是否为电话，
	只能为固定电话或移动电话
*/
function verifyTel(vName,strValue)
{
	if (strValue != "" && !isTel(strValue))
	{
		arrVerifyErrInfo.push(vName + "不是有效的电话号码!请输入手机或者固定电话（固话例：010-99201234）\n");
		return false;
	}
	return true;
}   
function isTel(strValue)
{
   //modify by jiaqiangli 2009-03-09 目前都有这些号段 13XX 15XX 18XX 同时去掉前面的0
   //var valid=/(^0\d{2,3}\-\d{7,8}$)|(^0?1[3,5][0,1,2,3,4,5,6,7,8,9]\d{8}$)/;
   var valid=/(^0\d{2,3}\-\d{7,8}$)|(^[0-9]\d{10}$)/;   
   return (verifyEmpty(strValue)||valid.test(strValue));
}  

/*判断用户输入是否为空*/   
function verifyEmpty(strValue)
{   
   return (strValue==null||strValue=="");   
} 

/*
* tongmeng 2008-05-07 add
  
  修改之前的校验策略
  
  修改原因:
  verifyCode中会调用CodeQuery,在CodeQuery中没有对查询数据量做控制,对于lacom等大数据表,
  调用后会造成浏览器长时间卡死.
  修改方式:
  1 - 传入需要校验的数值,在java程序中校验
  2 - 为保证数据校验的正确性,不能从缓存中取
*/
function verifyCodeNew(vName, strValue, vType, returnCode)
{
	if (strValue == "") return true;
	
	var strCode = vType.substring(5);	//截取出代码类型标志
	var arrCode = null;
	//寻找主窗口
	var win = searchMainWindow(this);
	if (win == false) { win = this; }
	//如果内容中有数据，从内容中取数据
	if (win.parent.VD.gVCode.getVar(strCode))
	{
		arrCode = win.parent.VD.gVCode.getVar(strCode);
	}
	var passFlag = false;	//校验通过标志，true表示通过

	if(arrCode == null){
		//连接数据库进行CODE查询，返回查询结果给arrCode
		var urlStr = "../common/jsp/CodeQueryWindow.jsp?codeType=" + strCode + "&verifyValue="+strValue+"";
	    var Request = initRequest();
        Request.open("POST",urlStr, false);
        Request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        Request.send(null);
        try
        {
        	arrCode = Request.responseText;
            if (arrCode != null && typeof(arrCode) == "string")
            {
            	arrCode = arrCode.trim();
            }
        }catch (ex)
        {
            //alert("数据返回出错：" + ex.message);
        }
        
		if (arrCode == null|| (arrCode == false) || (arrCode == ""))
		{
			//等待从数据库端取回数据
			arrVerifyErrInfo.push("CODE查询功能错误，请与管理员联系！\n");
			return false;
		}
		else
		{
			try {
				arrCode = decodeEasyQueryResult(arrCode);
			}
			catch(ex)
			{
				console.log(ex);
				alert("页面缺少引用EasyQueryVer3.js");
				return false;
			}
			var strCodeSelect = "";
			for (i=0; i<arrCode.length; i++)
			{
				strCodeSelect = strCodeSelect + "<option value=" + arrCode[i][0] + ">";
				strCodeSelect = strCodeSelect + arrCode[i][0] + "-" + arrCode[i][1];
				strCodeSelect = strCodeSelect + "</option>";
			}
			//将拆分好的数据放到内存中
			win.parent.VD.gVCode.addArrVar(strCode, "", arrCode);
			//无论是否有数据从服务器端得到,都设置该变量
			win.parent.VD.gVCode.addVar(strCode+"Select","",strCodeSelect);
		}
	}
	
	for (var i=0; i<arrCode.length; i++)
	{
		for (var j=0; j<arrCode[i].length; j++)
		{
			if (strValue == arrCode[i][j])
			{
				//在个人承保录入的校验职业和职业代码中用到
				if (typeof(returnCode) != "undefined") return arrCode[i];
				passFlag = true;
				break;
			}
		}
	}
	
	if (!passFlag)
	{
		arrVerifyErrInfo.push(vName + "输入不符合数据库规定的取值范围！请查阅或双击输入框选择！\n");
	}
	return passFlag;
}