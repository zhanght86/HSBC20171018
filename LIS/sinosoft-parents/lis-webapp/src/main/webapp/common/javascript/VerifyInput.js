/**************************************************************************
 * ��������: VerifyInput.js
 * ������: ͨ��У�麯��Ver2
 *           ��Ver1���ӿ�ѧ������У�飬У�����ͼ�ɽ��С��루&����������|��������
 * ������  : �� ��
 * �����������: 2002-10-22
**************************************************************************/
var arrVerifyErrInfo = new Array();   //��¼һ���ֶε�У�������Ϣ

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

//ҵ�������ýӿڣ����ͨ��У�鷵��true�����򷵻�false
function verifyInput()
{
	var formsNum = 0;	//�����е�FORM��
	var elementsNum = 0;	//FORM�е�Ԫ����
	var passFlag = true;	//У��ͨ����־

	//��������FORM
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		//����FORM�е�����ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			//Ԫ��У������verify��ΪNULL
			if (window.document.forms[formsNum].elements[elementsNum].attributes.verify != null && window.document.forms[formsNum].elements[elementsNum].attributes.verify.nodeValue != "")
			{
				//����У��verifyElement
				if (!verifyElement(window.document.forms[formsNum].elements[elementsNum].attributes.verify.nodeValue, window.document.forms[formsNum].elements[elementsNum].value))
				{
					passFlag = false;
					break;
				}
			}
		}
		if (!passFlag) break;
	}

	//if(passFlag) alert("ͨ��У��");
	return passFlag;
}

//ҵ�������ýӿڣ���FORMΪ��λ�����ͨ��У�鷵��true�����򷵻�false
function verifyForm(formName)
{
	var elementsNum = 0;	//FORM�е�Ԫ����
	var passFlag = true;	//У��ͨ����־

	//����FORM�е�����ELEMENT
	for (elementsNum=0; elementsNum<window.document.all(formName).elements.length; elementsNum++)
	{
		//Ԫ��У������verify��ΪNULL
		if (window.document.all(formName).elements[elementsNum].attributes.verify != null && window.document.all(formName).elements[elementsNum].attributes.verify.nodeValue != "")
		{
			//����У��verifyElement
			if (!verifyElement(window.document.all(formName).elements[elementsNum].attributes.verify.nodeValue, window.document.all(formName).elements[elementsNum].value))
			{
				passFlag = false;
				break;
			}
		}
	}

	//if(passFlag) alert("ͨ��У��");
	return passFlag;
}

//У��Ԫ�أ�strInfoΪԪ��У����Ϣ��strValueΪԪ��ֵ
function verifyElement(strInfo, strValue, boxName)
{
	var strValue = trim(strValue);	//��տո�
	var passFlag = true;	//У��ͨ����־��true��ʾͨ��
	var vName;	//У���ֶ�����
	var vType;	//Ҫ���е�У������
	var intIndex;	//���������
	var typeStack = new Array();	//һ���ֶε�У������ջ
	var operStack = new Array();	//һ���ֶε�У�������Ŷ�ջ�������ڡ��롱�����򡱼���
	var strboxName = boxName;

	while (arrVerifyErrInfo != "")
	{
		//���ǰһ���ֶε�У�������Ϣ
		arrVerifyErrInfo.pop();
	}

	//������ֶ����ƣ�����ǰһ�汾�������á�|���ָ�
	vName = strInfo.substring(0, strInfo.indexOf("|"));
	strInfo = strInfo.substring(strInfo.indexOf("|") + 1);

	//��ֳ�У�����ͣ�������У�飬����У������ͨ��TRUE����FALSE���������ջ
	while (strInfo != "")
	{
		if (strInfo.indexOf("|") != -1 && strInfo.indexOf("&") != -1)
		{
			//������������
			intIndex = strInfo.indexOf("|")>strInfo.indexOf("&")?strInfo.indexOf("&"):strInfo.indexOf("|");
			vType = strInfo.substring(0, intIndex);
			typeStack.push(verifyType(vName, vType, strValue,strboxName));
			operStack.push(strInfo.substring(intIndex, intIndex + 1));
			strInfo = strInfo.substring(intIndex + 1);
		}
		else if (strInfo.indexOf("|") != -1 || strInfo.indexOf("&") != -1)
			{
				//ֻ��һ������
				intIndex = strInfo.indexOf("|")>strInfo.indexOf("&")?strInfo.indexOf("|"):strInfo.indexOf("&");
				vType = strInfo.substring(0, intIndex);
				typeStack.push(verifyType(vName, vType, strValue,strboxName));
				operStack.push(strInfo.substring(intIndex, intIndex + 1));
				strInfo = strInfo.substring(intIndex + 1);
			}
			else
			{
				//������
				vType = strInfo;
				strInfo = "";
				typeStack.push(verifyType(vName, vType, strValue,strboxName));
			}
	}

	passFlag = typeStack[0];
	//ֻ��һ��У������ʱ
	for (var k=0; k<operStack.length; k++)
	{
		//�ж��У�����ͣ���������
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
				alert("У�������������");
			}
		passFlag = typeStack[k + 1];
	}

	var strVerifyErrInfo = "�������󣬿��������´����е�һ���� \n";
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
	//����ֶβ�Ϊ��¼��,��û��¼������,��ô����code��У��,�������ݿ��ѯ����
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


	//���������޸�Լ����LEN��
	if (vType.toUpperCase().indexOf("LEN") == 0) passFlag = verifyLength(vName, strValue, vType);

	//���������޸�Լ����VALUE��
	if (vType.toUpperCase().indexOf("VALUE") == 0) passFlag = (verifyNumber(vName, strValue) && verifyValue(vName, strValue, vType));

	//���������޸�Լ����CODE:��
	if (vType.toUpperCase().indexOf("CODE:") == 0) 
	{
		if(strValue != "")
		//ֻ�в�Ϊ�ղ���У��
		{
			//verifyCodeNew
			//passFlag = verifyCode(vName, strValue, vType);
			passFlag = verifyCodeNew(vName, strValue, vType);
		}
	}

	//���������޸�Լ����CODE:��
	if (vType.toUpperCase() == "ZIPCODE") passFlag = (verifyLength(vName, strValue, "len=6") && verifyNumber(vName, strValue));



	return passFlag;
}

//����true��ʾͨ��У�飬����false��ʾ��ͨ��������Υ��
//����Ϊ��У��
function verifyMustNull(vName, strValue) {
	if (strValue != "")
	{
		arrVerifyErrInfo.push(vName + "����Ϊ�գ�\n");
		return false;
	}
	return true;
}

//����Ϊ��У��
function verifyNotNull(vName, strValue)
{
	if (strValue == "")
	{
		arrVerifyErrInfo.push(vName + "����Ϊ�գ�\n");
		return false;
	}
	return true;
}

//��������У��
function verifyNumber(vName, strValue)
{
//	if (strValue != "" && isNaN(parseFloat(strValue)) && !isNumeric(strValue))
	if (strValue != "" && !isNumeric(strValue))
	{
		arrVerifyErrInfo.push(vName + "������Ч�����֣�\n");
		return false;
	}
	return true;
}
//��������У��
function verifyFloat(vName, strValue)
{
	if (strValue != "" && isNaN(parseFloat(strValue)) && !isNumeric(strValue))
//	if (strValue != "" && !isNumeric(strValue))
	{
		arrVerifyErrInfo.push(vName + "������Ч�����֣�\n");
		return false;
	}
	return true;
}

//��������У��
function verifyDate(vName, strValue) {
	if (strValue != "" && !isDate(strValue)&&!isDateN(strValue))
	{
		arrVerifyErrInfo.push(vName + "������Ч�����ڸ�ʽ(YYYY-MM-DD)����(YYYYMMDD)��\n");
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
			arrVerifyErrInfo.push(vName +"email��ַ���Ȳ��ܳ���50λ��");
			return false;
		}
		var pos1 = s.indexOf("@");
		var pos2 = s.indexOf(".");
		var pos3 = s.lastIndexOf("@");
		var pos4 = s.lastIndexOf(".");

		if ((pos1 <= 0)||(pos1 == len)||(pos2 <= 0)||(pos2 == len))
		{
			arrVerifyErrInfo.push(vName +"��������Ч��e-mail��ַ��");
			return false;
		}
		else
		{
			if( (pos1 == pos2 - 1) || (pos1 == pos2 + 1)|| ( pos1 != pos3 )|| ( pos4 < pos3 ) )
			{
				arrVerifyErrInfo.push(vName +"��������Ч��e-mail��ַ��");
				return false;
    		}
    	}
		if ( !isCharsInBag( s, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-_@"))
		{
			arrVerifyErrInfo.push(vName +"email��ַ��ֻ�ܰ����ַ�ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.-_@\n"+"����������" );
			return false;
		}
		var badChar ="><,[]{}?/+=|\:;!#$%^&()`";
		if (isCharsInBag(s,badChar))
		{
			arrVerifyErrInfo.push(vName +"�벻Ҫ��email��ַ�������ַ� " + badChar + "\n" );
			return false;
		}
	}
	return true;
}
//��0��1֮�����ֵ(0,1]
function verifyDecimal(vName, strValue) {
        if (strValue != "" &&(parseFloat(strValue)<=0||parseFloat(strValue)>1) )
        {
		arrVerifyErrInfo.push(vName + "����0��1֮���С����\n");
		return false;
	}
	return true;
}
//��������У��
function verifyInteger(vName, strValue) {
	if (strValue != "" && !isInteger(strValue))
	{
		arrVerifyErrInfo.push(vName + "������Ч��������\n");
		return false;
	}
	return true;
}

//���볤��У��
function verifyLength(vName, strValue, vType) {
	var oper;
	var len;
	var strOperLen = vType.substring(3);	//��ȡ����������ֵ

	if (strValue == "") return true;

	if (isNaN(parseInt(strOperLen.substring(1))))
	{
		oper = strOperLen.substring(0, 2);	//��Ϊ">=", "<="ʱ
		len = strOperLen.substring(2);
	}
	else
	{
		oper = strOperLen.substring(0, 1);	//��Ϊ"=", "<", ">"ʱ
		len = strOperLen.substring(1);
	}

	switch (oper)
	{
		case "=" :
			if (strValue.length != parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "�����Ϲ涨�����볤�ȣ�������Ҫ����" + len + "��\n");
				return false;
			}
			break;
		case ">" :
			if (strValue.length <= parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "�����Ϲ涨�����볤�ȣ�������Ҫ����" + len + "��\n");
				return false;
			}
			break;
		case "<" :
			if (strValue.length >= parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "�����Ϲ涨�����볤�ȣ�������ҪС��" + len + "��\n");
				return false;
			}
			break;
		case ">=" :
			if (strValue.length < parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "�����Ϲ涨�����볤�ȣ�������Ҫ���ڵ���" + len + "��\n");
				return false;
			}
			break;
		case "<=" :
			if (strValue.length > parseInt(len))
			{
				arrVerifyErrInfo.push(vName + "�����Ϲ涨�����볤�ȣ�������ҪС�ڵ���" + len + "��\n");
				return false;
			}
			break;
	}
	return true;
}

//����ֵУ��
function verifyValue(vName, strValue, vType)
{
	var oper;
	var Val;
	var strOperVal = vType.substring(5);	//��ȡ����������ֵ

	if (strValue == "") return true;

	if (isNaN(parseFloat(strOperVal.substring(1))))
	{
		oper = strOperVal.substring(0, 2);	//��Ϊ">=", "<="ʱ
		Val = strOperVal.substring(2);
	}
	else
	{
		oper = strOperVal.substring(0, 1);	//��Ϊ"=", "<", ">"ʱ
		Val = strOperVal.substring(1);
	}

	switch (oper)
	{
		case "=" :
			if (parseFloat(strValue) != parseFloat(Val))
			{
				arrVerifyErrInfo.push(vName + "�����Ϲ涨��ȡֵ������ֵ��Ҫ����" + Val + "��\n");
				return false;
			}
			break;
		case ">" :
			if (parseFloat(strValue) <= parseFloat(Val))
			{
				arrVerifyErrInfo.push(vName + "�����Ϲ涨��ȡֵ������ֵ��Ҫ����" + Val + "��\n");
				return false;
			}
			break;
		case "<" :
			if (parseFloat(strValue) >= parseFloat(Val))
			{
				arrVerifyErrInfo.push(vName + "�����Ϲ涨��ȡֵ������ֵ��ҪС��" + Val + "��\n");
				return false;
			}
			break;
		case ">=" :
			if (parseFloat(strValue) < parseFloat(Val))
			{
				arrVerifyErrInfo.push(vName + "�����Ϲ涨��ȡֵ������ֵ��Ҫ���ڵ���" + Val + "��\n");
				return false;
			}
			break;
		case "<=" :
			if (parseFloat(strValue) > parseFloat(Val))
			{
	//alert(vName + "�����Ϲ涨��ȡֵ!����ֵ��ҪС�ڵ���"+Val);
	arrVerifyErrInfo.push(vName + "�����Ϲ涨��ȡֵ������ֵ��ҪС�ڵ���" + Val + "��\n");
	return false;
	}
	break;
	}
	return true;
	}

//��������У�飬����CodeSelect�������
function verifyCode(vName, strValue, vType, returnCode)
{
	var strCode = vType.substring(5)	//��ȡ���������ͱ�־
	var arrCode = mVs.getVar(strCode);	//���ڴ������
	var passFlag = false;	//У��ͨ����־��true��ʾͨ��
	var arrRecord;	//��ֵļ�¼����
	var arrField;	//��ֵ��ֶ�����
	var recordNum;	//��¼��
	var fieldNum;	//�ֶ���
	var arrResult = new Array();	//������飬ģ��CodeSelect���ݸ�ʽ
	var urlStr = "../common/jsp/CodeQueryWindow.jsp?codeType=" + strCode;
	//urlStr����ѯ����URL�Ͳ�ѯ����
	var sFeatures = "status:no;help:0;close:0;dialogWidth:150px;dialogHeight:0px;dialogLeft:-1;dialogTop:-1;resizable=1";
	//sFeatures����ѯ������ʽ
	var strCodeSelect = "";
	if (strValue == "") return true;

	//�ڴ��������ݣ�ֱ�ӽ���У��
	if (arrCode != false)
	{
		for (var i=0; i<arrCode.length; i++)
		{
			for (var j=0; j<arrCode[i].length; j++)
			{
				if (strValue == arrCode[i][j])
				{
					//�ڸ��˳б�¼���У��ְҵ��ְҵ�������õ�
					if (typeof(returnCode) != "undefined") return arrCode[i];
					passFlag = true;
					break;
				}
			}
		}
	}
	else
	{
		//�������ݿ����CODE��ѯ�����ز�ѯ�����arrCode
		arrCode = window.showModalDialog(urlStr, "", sFeatures);

		if ((arrCode == false) || (arrCode == ""))
		{
			//�ȴ������ݿ��ȡ������
			arrVerifyErrInfo.push("CODE��ѯ���ܴ����������Ա��ϵ��\n");
			return false;
		}
		else if (arrCode == "Code Query Faile")
			{
				//��ѯ���ݿ�ʧ�ܴ���
				arrVerifyErrInfo.push(vName + " ���ݿ�����ѯʧ�ܣ��������Ա��ϵ��\n");
				return false;
			}
			else
			{
				arrRecord = arrCode.split("^");	//��ּ�¼���γɷ��ص�����
				recordNum = arrRecord.length;
				for (i=1; i<recordNum; i++)
				{
					arrField  = arrRecord[i].split("|");	//����ֶ�,��ÿ����¼���Ϊһ������
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
				mVs.addArrVar(strCode,"",arrResult);	//������ڴ棬�ṩ��CodeSelect����
				mVs.addVar(strCode+"Select","",strCodeSelect);	//�����Ƿ������ݴӷ������˵õ�,�����øñ���

				for (i=0; i<arrResult.length; i++)
				{
					//����У��
					for (j=0; j<arrResult[i].length; j++)
					{
						if (strValue == arrResult[i][j])
						{
							//�ڸ��˳б�¼���У��ְҵ��ְҵ�������õ�
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
		arrVerifyErrInfo.push(vName + "���벻�������ݿ�涨��ȡֵ��Χ������Ļ�˫�������ѡ��\n");
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
		else if (eval(boxName + ".className") == "coolDatePicker" || eval(boxName + ".className") == "warndate")    //XinYQ added on 2006-03-26 : ��� coolDatePicker �䳤������
		{
		    strcolor = strboxName + ".className=\"warndate\";";
		}
		else if (eval(boxName + ".className") == "common3" || eval(boxName + ".className") == "warn3")    //XinYQ added on 2006-09-21 : ��� common3 ��̵�����
		{
		    strcolor = strboxName + ".className=\"warn3\";";
		}
		
		else if (eval(boxName + ".className") == "multiDatePicker" || eval(boxName + ".className") == "warndate")    //XinYQ added on 2006-03-26 : ��� coolDatePicker �䳤������
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

//ҵ�������ýӿڣ����ͨ��У�鷵��true�����򷵻�false
function verifyInput2()
{

	var formsNum = 0;	//�����е�FORM��
	var elementsNum = 0;	//FORM�е�Ԫ����
	var passFlag = true;	//У��ͨ����־
	//��������FORM
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		//����FORM�е�����ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)
		{
			//Ԫ��У������verify��ΪNULL
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
				
				//����У��verifyElement
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

//��֤�ظ������Ƿ�ͬ
function verifyCheckDifferent(vName, strValue, boxName)
{
	    var ConfirmValue = boxName.substring(0, boxName.indexOf(".")+1)+"Confirm"+boxName.substring(boxName.indexOf(".")+1,boxName.length)+".value";
	    if(strValue!=""&& eval(ConfirmValue)=="" )
		{
				arrVerifyErrInfo.push(vName + "�����ظ����봦��ֵ��\n");
				return false;
		}
	    if(strValue!=""&& eval(ConfirmValue)!="" && strValue!=eval(ConfirmValue))
		{
				arrVerifyErrInfo.push(vName + "������������\n");
				return false;
		}
		return true;
}

function verifyPhone(vName, strValue){

	if (strValue != "" && !isPhoneNo(strValue))
	{
		arrVerifyErrInfo.push(vName + "������Ч�ĵ绰���룡\n");
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
	�ж��Ƿ�Ϊ�绰��
	ֻ��Ϊ�̶��绰���ƶ��绰
*/
function verifyTel(vName,strValue)
{
	if (strValue != "" && !isTel(strValue))
	{
		arrVerifyErrInfo.push(vName + "������Ч�ĵ绰����!�������ֻ����߹̶��绰���̻�����010-99201234��\n");
		return false;
	}
	return true;
}   
function isTel(strValue)
{
   //modify by jiaqiangli 2009-03-09 Ŀǰ������Щ�Ŷ� 13XX 15XX 18XX ͬʱȥ��ǰ���0
   //var valid=/(^0\d{2,3}\-\d{7,8}$)|(^0?1[3,5][0,1,2,3,4,5,6,7,8,9]\d{8}$)/;
   var valid=/(^0\d{2,3}\-\d{7,8}$)|(^[0-9]\d{10}$)/;   
   return (verifyEmpty(strValue)||valid.test(strValue));
}  

/*�ж��û������Ƿ�Ϊ��*/   
function verifyEmpty(strValue)
{   
   return (strValue==null||strValue=="");   
} 

/*
* tongmeng 2008-05-07 add
  
  �޸�֮ǰ��У�����
  
  �޸�ԭ��:
  verifyCode�л����CodeQuery,��CodeQuery��û�жԲ�ѯ������������,����lacom�ȴ����ݱ�,
  ���ú������������ʱ�俨��.
  �޸ķ�ʽ:
  1 - ������ҪУ�����ֵ,��java������У��
  2 - Ϊ��֤����У�����ȷ��,���ܴӻ�����ȡ
*/
function verifyCodeNew(vName, strValue, vType, returnCode)
{
	if (strValue == "") return true;
	
	var strCode = vType.substring(5);	//��ȡ���������ͱ�־
	var arrCode = null;
	//Ѱ��������
	var win = searchMainWindow(this);
	if (win == false) { win = this; }
	//��������������ݣ���������ȡ����
	if (win.parent.VD.gVCode.getVar(strCode))
	{
		arrCode = win.parent.VD.gVCode.getVar(strCode);
	}
	var passFlag = false;	//У��ͨ����־��true��ʾͨ��

	if(arrCode == null){
		//�������ݿ����CODE��ѯ�����ز�ѯ�����arrCode
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
            //alert("���ݷ��س���" + ex.message);
        }
        
		if (arrCode == null|| (arrCode == false) || (arrCode == ""))
		{
			//�ȴ������ݿ��ȡ������
			arrVerifyErrInfo.push("CODE��ѯ���ܴ����������Ա��ϵ��\n");
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
				alert("ҳ��ȱ������EasyQueryVer3.js");
				return false;
			}
			var strCodeSelect = "";
			for (i=0; i<arrCode.length; i++)
			{
				strCodeSelect = strCodeSelect + "<option value=" + arrCode[i][0] + ">";
				strCodeSelect = strCodeSelect + arrCode[i][0] + "-" + arrCode[i][1];
				strCodeSelect = strCodeSelect + "</option>";
			}
			//����ֺõ����ݷŵ��ڴ���
			win.parent.VD.gVCode.addArrVar(strCode, "", arrCode);
			//�����Ƿ������ݴӷ������˵õ�,�����øñ���
			win.parent.VD.gVCode.addVar(strCode+"Select","",strCodeSelect);
		}
	}
	
	for (var i=0; i<arrCode.length; i++)
	{
		for (var j=0; j<arrCode[i].length; j++)
		{
			if (strValue == arrCode[i][j])
			{
				//�ڸ��˳б�¼���У��ְҵ��ְҵ�������õ�
				if (typeof(returnCode) != "undefined") return arrCode[i];
				passFlag = true;
				break;
			}
		}
	}
	
	if (!passFlag)
	{
		arrVerifyErrInfo.push(vName + "���벻�������ݿ�涨��ȡֵ��Χ������Ļ�˫�������ѡ��\n");
	}
	return passFlag;
}