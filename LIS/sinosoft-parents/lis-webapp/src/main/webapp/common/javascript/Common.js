/*д�붯̬�ļ�*/
String.prototype.endWith=function(str){     
  var reg=new RegExp(str+"$");     
  return reg.test(this);        
}

if(window.location.pathname.endWith("/common/EasyScanQuery/EasyScanQuery.jsp")){
	document.write("<script type='text/javascript' src='../javascript/dbtype.js'><\/script>");
}
else {
	document.write("<script type='text/javascript' src='../common/javascript/dbtype.js'><\/script>");
}
/**
 * <p>��������: Common.js</p>
 * <p>������: ���ú����������� </p>
 * <p>ע�͸�����: ����</p>
 * <p>�����������: 2002-10-2</p>
 * <p>ע�⣺���еı�������ΪVAR����JAVA�б�ʾΪSTRING<p>
 */
var _DBO = "ORACLE";
var _DBM = "MYSQL";
var _DBS = "SQLSERVER";
var _DBD = "DB2";
//���ñ���
/** ���ڷָ���,��ʼֵ=":" */
var DATEVALUEDELIMITER=":";
/** ��������ֵ�ķָ���,��ʼֵ=":" */
var NAMEVALUEDELIMITER=":";
/** ��ʼֵ=":" */
var SBCCASECOLON="��";
/** ��֮��ķָ���,��ʼֵ="|" */
var FIELDDELIMITER="|";
/** ��ʼֵ="��" */
var SBCCASEVERTICAL="��";
/** ��¼֮��ķָ���,��ʼֵ="^" */
var RECORDDELIMITER="^";
/** ÿһҳ�����ʾ������,��ʼֵ="10" */
var MAXSCREENLINES=10;
/** �ڴ��д洢������ҳ��,��ʼֵ="20" */
var MAXMEMORYPAGES=20;
/** �޸�(��ɫ),��ʼֵ="FFFF00" */
var BGCOLORU="FFFF00";
/** ���(��ɫ),��ʼֵ="#00F0F0" */
var BGCOLORI="#00F0F0";
/** ɾ��(��ɫ),��ʼֵ="#778899" */
var BGCOLORD="#778899";
/** ��ݲ˵�������� */
var MAXMENUSHORTNUM = 3;
/** ����¼��У�鹦�ܵĵ�һ��¼�� */
var theFirstValue="";
/** ����¼��У�鹦�ܵĵڶ���¼�� */
var theSecondValue="";
/** ����¼��У�鹦��У���һ�ζ��� */
//Object theFirstObject;
/** ����¼��У�鹦��У��ڶ��ζ��� */
//Object theSecondObject;
var tResourceNameCommon="common.CommonSql";

function BrowserInfo() {
	var ua = navigator.userAgent.toLowerCase();
	var Sys = {};
	var s;
	(s = ua.match(/msie ([\d.]+)/)) ? Sys.ie = s[1] : (s = ua
			.match(/Trident\/(\d+)/i)) ? Sys.ie11 = s[1] : (s = ua
			.match(/edge.([\d.]+)/)) ? Sys.edge = s[1] : (s = ua
			.match(/opera.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
			.match(/opr.([\d.]+)/)) ? Sys.opera = s[1] : (s = ua
			.match(/firefox\/([\d.]+)/)) ? Sys.firefox = s[1] : (s = ua
			.match(/chrome\/([\d.]+)/)) ? Sys.chrome = s[1] : (s = ua
			.match(/version\/([\d.]+).*safari/)) ? Sys.safari = s[1] : 0;
	var mBrowserInfo = {};
	if (Sys.ie) {
		mBrowserInfo.name = "IE";
		mBrowserInfo.version = parseInt(Sys.ie);
	} else if (Sys.ie11) {
		mBrowserInfo.name = "IE";
		mBrowserInfo.version = 11;
	} else if (Sys.firefox) {
		mBrowserInfo.name = "Firefox";
		mBrowserInfo.version = Sys.firefox;
	} else if (Sys.chrome) {
		mBrowserInfo.name = "Chrome";
		mBrowserInfo.version = Sys.chrome;
	} else if (Sys.opera) {
		mBrowserInfo.name = "Opera";
		mBrowserInfo.version = Sys.opera;
	} else if (Sys.safari) {
		mBrowserInfo.name = "Safari";
		mBrowserInfo.version = Sys.safari;
	} else if (Sys.edge) {
		mBrowserInfo.name = "Edge";
		mBrowserInfo.version = Sys.edge;
	} else {
		//Ĭ��Chrome
		mBrowserInfo.name = "Chrome";
		mBrowserInfo.version = "0";
	}
	return  mBrowserInfo;
}

function BrowserCompatible()
{
	//������input����id���Ա���ʹ�á�.������
	var children = document.getElementsByTagName("input");
	for (var i = 0; i < children.length; i++) {
		if(children[i].id == null || children[i].id == "" ){
//			if(children[i].type == "radio") {
//				children[i].id = children[i].name + Math.ceil(Math.random()*1000);
//			}
//			else {
				children[i].id = children[i].name
//			}
		}
		children[i].setAttribute("autocomplete","off");
	}
	
	//textarea ���id����
	var children = document.getElementsByTagName("textarea");
	for (var i = 0; i < children.length; i++) {
		if(children[i].id == null || children[i].id == "" ){
			children[i].id = children[i].name
		}
		children[i].setAttribute("autocomplete","off");
	}
	
	var formArray = document.getElementsByTagName("form");
	//������form����id���Ա���ʹ�á�.������
	for (var i = 0; i < formArray.length; i++) {
		var form = formArray[i];
		if(form.id == null || form.id == "" ){
			form.id = form.name;
		}
		form.setAttribute("autocomplete","off");
	}
	//��form���all����
	var bi = BrowserInfo();
	if(!(bi.name == "IE" && bi.version < 9)) {
		for (var i = 0; i < formArray.length; i++) {
			var form = formArray[i];
			form.all = function(eleName) {
				var eles = document.getElementsByName(eleName);
				if (eles == null || typeof(eles) == 'undefined') {
					eles = document.getElementById(eleName);
				}
				if (eles == null) {
					return "";
				} else if (eles.length == 1) {
					return eles[0];
				} else {
					//��form����
					var fmName = this.name || this.id;
					var elsArray = new Array();
					var k = 0;
					for(var j = 0; j < eles.length ; j++) {
						if(eles[j].form.name == fmName) {
							elsArray[k] = eles[j];
							k++;
						}
					}
					if(elsArray.length == 1)
						return elsArray[0];
					else 
						return  elsArray;
				}
			}
		}
	}
}

{ 
	try {
		if (window.attachEvent) {  
	         window.attachEvent("onload", BrowserCompatible);  
	     } else if (window.addEventListener) {  
	    	 window.addEventListener("load",BrowserCompatible, false);
	     }
	}catch(e){};
}

/** *************************** ���ݴ���End ********************************* */


/**
 * ��String���������trim����
 */

String.prototype.trim = function()
{
	//����������ʽȥ��ͷβ�Ŀո�
	return this.replace(/(^\s*)|(\s*$)/g,"");
}

/**
* ����ͼƬ
* <p><b>Example: </b><p>
* <p>function changeImage(image,gif)<p>
* @param image ���ͼƬ�Ķ�����ܻ�ҳ��
* @param gif ͼƬ��ȫ·��
*/

function changeImage(image,gif)
{
	image.src=gif;  //Modify by yt 2002-05-30
}

/**
 * ת�� null �� "null" Ϊ ""
 * @param    null or String
 * @return   String
 * Added by XinYQ on 2006-08-05
 */
function NullToEmpty(sNullString)
{
	if (sNullString == null || sNullString == "null" || sNullString == "undefined")
	{
		sNullString = "";
	}
	return sNullString;
}

/**
* �滻�ַ�������
* <p><b>Example: </b><p>
* <p>replace("Minim123Minim", "123", "Minim") returns "MinimMinimMinim"<p>
* @param strExpression �ַ������ʽ
* @param strFind ���滻�����ַ���
* @param strReplaceWith �滻��Ŀ���ַ���������strReplaceWith�ַ����滻��strFind
* @return �����滻����ַ������ʽ
*/

function replace(strExpression,strFind,strReplaceWith)
{
  var strReturn;
  var intIndex;
  strReturn = (strExpression==null?"":strExpression);

  while((intIndex=strReturn.indexOf(strFind))>-1)
  {
    strReturn = strReturn.substring(0,intIndex) + strReplaceWith
               + strReturn.substring(intIndex+strFind.length,strReturn.length);
  }
  return strReturn;
}

/**
 * ȥ���ַ���ͷβ�ո�
 * Example: trim(" XinYQ ") returns "XinYQ"<p>
 * @param strValue �ַ������ʽ
 * @return ͷβ�޿ո���ַ������ʽ
 */
function trim(sSrcString)
{
	//ʹ��������ʽ����ȫ���滻
	if (sSrcString != null && typeof(sSrcString) == "string")
	{
	    sSrcString = sSrcString.replace(/(^\s*)|(\s*$)/g, "");
	}
	return sSrcString;
}

/**
 * ���������Ƿ���������У��
 * Example: isInteger("Minim") returns false;isInteger("123") returns true
 * @param strValue ������ֵ���ʽ���ַ������ʽ
 * @return ����ֵ��true--������, false--����������
 * XinYQ rewrote on 2006-08-12
 */
function isInteger(sInteger)
{
    var RegChkExp = /^(\+?)(\-?)(\d+)$/;
    return RegChkExp.test(sInteger);
}

/**
 * ���������Ƿ������ֵ�У��
 * Example: isNumeric("Minim") returns false;isNumeric("123.1") returns true
 * @param strValue ������ֵ���ʽ���ַ������ʽ
 * @return ����ֵ��true--������, false--�������֣�
 * XinYQ rewrote on 2006-08-12
 */
function isNumeric(sNumer)
{
    var RegChkExp = /^(\+?)(\-?)(\d+)(\.\d+)?$/;
    return RegChkExp.test(sNumer);
}

/**
 * �뿪��ʱ������У��
 * Example: checkNumber(HTML.Form.Object.Name)
 * @param Field HTMLҳ��Ķ�������
 * @return true�����һ����errorMessage("������Ϸ�������")��
 */
function checkNumber(Field)
{
	var strValue=Field.value;
	if(trim(strValue)!="" && !isNumeric(strValue))
	{
		errorMessage("������Ϸ�������");
		Field.focus();
		Field.select();
		return false;
	}
	return true;
}

/**
* �����
* <p><b>Example: </b><p>
* <p>checkYear(HTML.Form.Object.Name)<p>
* @param Field HTMLҳ��Ķ�������
* @return ����һ����errorMessage("���ӦΪ4λ����")��
*/
function checkYear(Field)
{
	var strValue=Field.value;
	if(trim(strValue)!="" && !(isInteger(strValue) && strValue.length==4 ) )
	{
	  errorMessage("���ӦΪ4λ����");
		Field.focus();
		Field.select();
	}

}

/**
* �����
* <p><b>Example: </b><p>
* <p>checkMonth(HTML.Form.Object.Name)<p>
* @param Field HTMLҳ��Ķ�������
* @return ����һ����errorMessage("�·�ӦΪ1-12֮�������")��
*/
function checkMonth(Field)

{
	var strValue=Field.value;
	if(trim(strValue)!="" && !(isInteger(strValue) && eval(strValue)>0 && eval(strValue)<13 ) )
	{
	  errorMessage("�·�ӦΪ1-12֮�������");
		Field.focus();
		Field.select();
	}
}

/**
* �����
* <p><b>Example: </b><p>
* <p>checkDay(HTML.Form.Object.Name)<p>
* @param Field HTMLҳ��Ķ�������
* @return ����һ����errorMessage("����ӦΪ1-31֮�������")��
*/
function checkDay(Field)
{
	var strValue=Field.value;
	if(trim(strValue)!="" && !(isInteger(strValue) && eval(strValue)>0 && eval(strValue)<32 ) )
	{
	  errorMessage("����ӦΪ1-31֮�������");
		Field.focus();
		Field.select();
	}
}

/**
* ���Сʱ
* <p><b>Example: </b><p>
* <p>checkHour(HTML.Form.Object.Name)<p>
* @param Field HTMLҳ��Ķ�������
* @return ����һ����errorMessage("СʱӦΪ0-24֮�������")��
*/
function checkHour(Field)
{
	var strValue=Field.value;
	if(trim(strValue)!="" && !(isInteger(strValue) && eval(strValue)>=0 && eval(strValue)<=24 ) )
	{
		errorMessage("СʱӦΪ0-24֮�������");
		Field.focus();
		Field.select();
	}
}

/**
* ����
* <p><b>Example: </b><p>
* <p>hasValue(HTML.Form.Object.Name)<p>
* @param Field HTMLҳ��Ķ�������
* @return ����ֵ��true--��ֵ, false--�գ�
*/
function hasValue(Field)
{
	if(Field.value=="")



		return false;
	else
	  return true;
}


/**
* �������򰴼�ʱ������У��
* <p><b>Example: </b><p>
* <p>checkInteger(window.event)<p>
* @param Event �������¼�
* @return ����ֵ��true--����������, false--���˷���������
*/
function checkInteger(e)
{
  var charCode=e.keyCode;
  if(charCode>=48 && charCode<=57)
  {
    return true;
  }
  return false;
}

/**
* �������򰴼�ʱ��װ����ʽ��У��(ֻ�������ֺ�*��¼��)
* <p><b>Example: </b><p>
* <p>checkBind(window.event)<p>
* @param Event �������¼�
* @return ����ֵ��true--����װ����ʽ, false--������װ����ʽ��
*/
function checkBind(e)
{
  var charCode=e.keyCode;
  if(charCode>=48 && charCode<=57 || charCode==42)
  {
    return true;
  }
  return false;
}

/**
* �������򰴼�ʱ������У��
* <p><b>Example: </b><p>
* <p>checkNumeric(window.event)<p>
* @param Event �������¼�
* @return ����ֵ��true--�������ּ�, false--���˷����ּ���
*/
function checkNumeric(e)
{

  var charCode=e.keyCode;
	if(charCode>31 && (charCode<48 || charCode>57) && charCode!=46)
	{
	return false;
	}
	return true;
}
/**
*�ж϶���ֵ�Ƿ�Ϊ��
*/
function isEmpty(obj)
{
	if(obj.value == null || obj.value == "")
		return true;
	else
		return false;
}
/**
* �ж��ַ��Ƿ���s��
*/
function isCharsInBag (s, bag)
{
  var i;
  for (i = 0; i < s.length; i++)
  {
    var c = s.charAt(i);
    if (bag.indexOf(c) == -1) return false;
  }
  return true;
}

/**
* ���ڵĺϷ��ж�
* <p><b>Example: </b><p>
* <p>isLegalDate("2002", "10", "03") returns true<p>
* <p>isLegalDate("Minim", "10", "03") returns false<p>
* @param year ����ַ���
* @param month �·��ַ���
* @param day �����ַ���
* @return ����ֵ��true--�Ϸ�����, false--�Ƿ����ڣ�
*/
function isLegalDate(y,m,d)
{
  if(isNaN(parseInt(y,10)) || isNaN(parseInt(m,10)) || isNaN(parseInt(d,10)) )
    return false;
  var dt = new Date(parseInt(y,10),parseInt(m,10)-1,parseInt(d,10));
  if( dt.getFullYear()==parseInt(y,10) &&
      dt.getMonth()==parseInt(m,10)-1 &&
      dt.getDate()==parseInt(d,10)
    )
    return true;
  else
    return false;
}

/**
* ���������Ƿ������ڵ�У��
* <p><b>Example: </b><p>
* <p>isDate("2002-10-03") returns true<p>
* <p>isDate("2002/10/03") returns false<p>
* @param date �����ַ���,��ʽ����Ϊ��yyyy-mm-dd��
* @return ����ֵ��true--�Ϸ�����, false--�Ƿ����ڣ�
*/
function isDate(sDate)
{
  if(!sDate.match(/^\d{4}\-\d\d?\-\d\d?$/)){return   false;}
  var arr = sDate.replace(/\-0/g,"-").split("-");
  arr=new Array(parseInt(arr[0]),parseInt(arr[1])-1,parseInt(arr[2]));
  var tDate=new Date(arr[0],arr[1],arr[2]);
  return tDate.getFullYear()==arr[0] && tDate.getMonth()==arr[1] && tDate.getDate()==arr[2];
}

/**
 * �Ƚ����������ַ���
 * Example: compareDate("2002-10-03", "2002-10-03") returns 0;compareDate("2002-10-03", "2001-10-03") returns 1
 * @param date1 �����ַ���,��ʽ����Ϊ��yyyy-mm-dd��
 * @param date2 �����ַ���,��ʽ����Ϊ��yyyy-mm-dd��
 * @return date1=date2�򷵻�0 , date1>date2�򷵻�1 , date1<date2�򷵻�2
 */
function compareDate(date1,date2){
    var strValue=date1.split("-");
    var date1Temp=new Date(strValue[0],strValue[1]-1,strValue[2]);
    strValue=date2.split("-");
    var date2Temp=new Date(strValue[0],strValue[1]-1,strValue[2]); //lanjun 2007/2/1 js�е���Ϊ0~11
    if(date1Temp.getTime()==date2Temp.getTime()){
        return 0;
    }else if(date1Temp.getTime()>date2Temp.getTime()){
        return 1;
    }else{
        return 2;
    }
}


/**
* �Ը�ʽ�ַ������н���,����һ����������
* <p><b>Example: </b><p>
* <p>splitField("Minim:123|Hzm:456|") returns arrayReturn[Minim]=123;arrayReturn[Hzm]=456<p>
* @param record ��ʽ�ַ��� FieldName:FieldValue|
* @return �������� array[FieldName]=FieldValue
*/
function splitField(record)
{
  var arrayField=record.split(FIELDDELIMITER);
  var arrayReturn=new Array();
  var i;
  for(i=0;i<arrayField.length-1;i++)
  {
    var arrayNameValuePair=arrayField[i].split(NAMEVALUEDELIMITER);      //�ָ��һ����������ֵ
    arrayReturn[arrayNameValuePair[0]]=arrayNameValuePair[1];
  }
  return arrayReturn;
}

/**
* ��span����ʾ������
* <p><b>Example: </b><p>
* <p>showPage(HTML.ImageObject, HTML.SpanObject.ID)<p>
* @param img ��ʾͼƬ��HTML����
* @param spanID HTML��SPAN�����ID
* @return ���ҳ��SPAN�ɼ����������أ�����ʾ��ʾ�رյ�ͼƬ����֮
*/

function showPage(img,spanID)
{
  if(spanID.style.display=="")
  {
    //�ر�
    spanID.style.display="none";
    img.src="../common/images/butCollapse.gif";
  }
  else
  {
    //��
    spanID.style.display="";
    img.src="../common/images/butExpand.gif";
  }
}

/**
* ��span����ʾonly
* <p><b>Example: </b><p>
* <p>showPageOnly(HTML.ImageObject, HTML.SpanObject.ID)<p>
* @param img ��ʾͼƬ��HTML����
* @param spanID HTML��SPAN�����ID
*/
function showPageOnly(img,spanID)
{
  //��
  spanID.style.display="";
  img.src="/Images/piccSh/butExpand.gif";
}

/**
* ��һ������
* <p><b>Example: </b><p>
* <p>openWindow("www.163.com", null)<p>
* @param strURL �´��ڵ�����·����URL�������·��
* @param strName ָ��������������Ϊ��
* @return �����½����ڵľ��
*/
function openWindow(strURL,strName)
{
  var newWindow = window.open(strURL,strName,'width=640,height=480,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
  newWindow.focus();
  return newWindow;
}


/**
* �ָ���벢����select����
* <p><b>Example: </b><p>
* <p>setOption("name", "1=Minim&2=Hzm");�����������п�����ѡ��Minim��Hzm<p>
* @param selectName HTML��select������
* @param strValue ����select������ʾ���ݵ��ַ��������ĸ�ʽ����Ϊ: value1=text1&value2=text2���ԡ�&"�ŷָ�
*/
function setOption(selectName,strValue)
{
  var arrayField=strValue.split("&");
  var i=0;
  document.all(selectName).length = 0;
  while(i<arrayField.length)
  {
    var option=document.createElement("option");
    var arrayTemp=arrayField[i].split("=");
    var strFieldName=arrayTemp[0];
    var strFieldValue=unescape(arrayTemp[1]);
    option.value=strFieldName;
    option.text=strFieldValue;
    document.all(selectName).add(option);
    i++;
  }
}

/**
* ��ָ���ı���ǰ��0��ֱ������ָ��λ��
* <p><b>Example: </b><p>
* <p>addZero("Minim", 10) returns "00000Minim"<p>
* @param strValue ��Ҫ��0���ַ���
* @param intLen ��0���ַ����ĳ���
* @return ��0������Ҫ�󳤶ȵ��ַ���
*/

function addZero(strValue,intLen)
{
	var i,len;
	var strRet;
	strRet=strValue.toString();
	len=strRet.length;
	if (len<intLen)
	{
		while (strRet.length!=intLen)
		{
			strRet="0"+strRet;
		}
		return strRet;
	}
	else
	{
		return strRet;


	}
}

/**
* ��д������onkeypressʱ���ø÷���������ʹ���������Զ�ת���ɴ�д
* <p><b>Example: </b><p>
* <p>uppercaseKey()<p>
*/
function uppercaseKey()
{
  var keycode = window.event.keyCode;
  if( keycode>=97 && keycode<=122 )
  {
    window.event.keyCode = keycode-32;
  }
}

/**
* ʹHTML��FORM����ڵ�����Ԫ�غͶ�������Ч
* <p><b>Example: </b><p>
* <p>setFormAllDisabled()<p>
*/
function setFormAllDisabled()
{
  var i = 0;
  for(i=0;i<fm.elements.length;i++)
	{
			fm.elements[i].disabled=true;
	}
}

/**
* ʹHTML��FORM����ڵ�����Ԫ�غͶ�������Ч����setFormAllDisabled���
* <p><b>Example: </b><p>
* <p>setFormAllEnabled()<p>
*/
function setFormAllEnabled()

{
  var i = 0;
  for(i=0;i<fm.elements.length;i++)
	{
			fm.elements[i].disabled=false;

	}
}

/** ���ұ���ܺ����Ļ��������� */
var arrayCollect = new Array();    //����������

/**
* ���� (����,�ұ�����,��������,��������)
* <p><b>Example: </b><p>
* <p>��ȱ�������ҵ������Ա���<p>
* @param intNum ����
* @param CN �ұ�����
* @param Amt ��������
* @param Prm ��������
* @return ֱ��Ϊȫ�ֱ�������������arrayCollect��ֵ
*/
function collectFee(intNum,CN,Amt,Prm )
{
  var arrayCollectOne ;
  for(i=0;i<intNum;i++)
  {
    var strCN      = document.all(CN)[i].value;
    var strAmount  = document.all(Amt)[i].value;
    var strPremium = document.all(Prm)[i].value;
    var existFlag  = false;

    if(!isNumeric(strAmount))
      strAmount=0;
    else
      strAmount=eval(strAmount);
    if(!isNumeric(strPremium))
      strPremium=0;
    else
      strPremium=eval(strPremium);

    for(j=0;j<arrayCollect.length;j++)
    {
      if( arrayCollect[j]["CN"] == strCN )
      {
        existFlag = true;
        break;
      }
    }
    if(!existFlag)
    {
      arrayCollectOne = new Array(); //һ��������
      arrayCollectOne["CN"] = strCN;
      arrayCollectOne["Amount"] = strAmount;
      arrayCollectOne["Premium"] = strPremium;
      arrayCollect[j] = arrayCollectOne;
    }
    else
    {
      arrayCollect[j]["Amount"] = arrayCollect[j]["Amount"] + strAmount ;
      arrayCollect[j]["Premium"] = arrayCollect[j]["Premium"] + strPremium ;
    }
  }
}

/**
* ��ȡ���ڶ���
* @param strDate �����ַ���
* @param splitOp �ָ��
* @return �������ڶ���
*
*/
function getDate(strDate, splitOp) {
  if (splitOp == null) splitOp = "-";

  var arrDate = strDate.split(splitOp);
  //if (arrDate[1].length == 1) arrDate[1] = "0" + arrDate[1];
  //if (arrDate[2].length == 1) arrDate[2] = "0" + arrDate[2];

  //return new Date(arrDate[0], arrDate[1], arrDate[2]);
  return new Date(arrDate[0], arrDate[1]-1, arrDate[2]);   //YangYL�޸�BUG,�·�Ӧ����0~11,����1~12
}


/**
* �����������ڵĲ�,���ز������(M)������(D) (����������2.29��һ��)
* <p><b>Example: </b><p>
* <p>dateDiff("2002-10-1", "2002-10-3", "D") returns "2"<p>
* <p>dateDiff("2002-1-1", "2002-10-3", "M") returns "9"<p>
* @param dateStart ������
* @param dateEnd ��������
* @param MD ��ǣ���M��ΪҪ�󷵻ز����������D��ΪҪ�󷵻ز������
* @return �����������ڲ������(M)������(D)
*/
function dateDiff(dateStart,dateEnd,MD)
{
  if(dateStart==""||dateEnd=="")
  {
  	return false;
  }
  if (typeof(dateStart) == "string") {
    dateStart = getDate(dateStart);
  }

  if (typeof(dateEnd) == "string") {
    dateEnd = getDate(dateEnd);
  }

  var i;
  if(MD=="D") //��������
  {
/*
    var endD = dateEnd.getDate();
    var endM = dateEnd.getMonth();
    var endY = dateEnd.getFullYear();
    var startD = dateStart.getDate();
    var startM = dateStart.getMonth();
    var startY = dateStart.getFullYear();
    var startT=new Date(startY,startM-1,startD);
    var endT=new Date(endY,endM-1,endD);
    var startT=new Date(startY,startM,startD);
    var endT=new Date(endY,endM,endD);
    var diffDay=(endT.valueOf()-startT.valueOf())/86400000;
*/
		//Yangyl �����޸�getDate,��ͬ���޸ĸú���
		var diffDay=(dateEnd.valueOf()-dateStart.valueOf())/86400000;
    return diffDay;
  }
  else //���¼����
  {
    var endD = dateEnd.getDate();
    var endM = dateEnd.getMonth();
    var endY = dateEnd.getFullYear();
    var startD = dateStart.getDate();
    var startM = dateStart.getMonth();
    var startY = dateStart.getFullYear();
    
    if(endD>=startD)
    {  
      return (endY-startY)*12 + (endM-startM)+1;
    }
    else
    {
      return (endY-startY)*12 + (endM-startM);
    }
  }
}

/**
* ����HTMLԪ�ض���ı���ɫ
* <p><b>Example: </b><p>
* <p>setBackColor(HTML.Form.Object.Name, "red")<p>
* <p>setBackColor(HTML.Form.Object.Name, "#ff0000")<p>
* @param Field HTMLҳ��Ķ�������
* @param bcolor ��ɫ���ַ������16λ��
*/
function setBackColor(Field,bcolor)
{
  Field.style.backgroundColor = bcolor;
}

/**
* ����ͨ���ֵ�ı�ʱ,����ı���ɫ����Ϊ��ǰҳ�ı���ɫ
* <p><b>Example: </b><p>
* <p>commonBlur(HTML.Form.Object.Name)<p>
* @param Field HTMLҳ��Ķ�������
*/
function commonBlur(Field)
{
  var oldValue = eval("old"+Field.name);
  if( Field.value==oldValue )
    setBackColor(Field,"");
  else
    setBackColor(Field,BGCOLORU);
}

/**
* �������������ֵ�ı�ʱ���ñ���ɫ(��ʾ����)
* <p><b>Example: </b><p>
* <p>δ֪��ʹ�ú�������<p>
* @param Field HTMLҳ��Ķ�������
* @param PageName δ֪��ʹ�ú�������
*/
function mulline1Blur(Field,PageName)
{
  var i;
  var flen=document.all(Field.name).length;
  var index=0;
  var oldValue;
  for(i=0;i<flen;i++)
  {
    if( document.all(Field.name)[i]==Field )
    {
      index = i;
      break;
    }
  }
  if( index+1>eval("old"+PageName+"Num") ) return ;
  oldValue = eval("old"+Field.name+"["+index+"]");
  if( Field.value==oldValue )
    setBackColor(Field,"");
  else
    setBackColor(Field,BGCOLORU);

}

/**
* �������������ֵ�ı�ʱ���ñ���ɫ(��ʾһ��)
* <p><b>Example: </b><p>
* <p>δ֪��ʹ�ú�������<p>
* @param Field HTMLҳ��Ķ�������
* @param PageName δ֪��ʹ�ú�������
*/
function mulline2Blur(Field,PageName)
{
  var index=0;
  index = pagesAttributes[PageName]["curindex"];
  if( index+1>eval("old"+PageName+"Num") ) return ;
  oldValue = eval("old"+Field.name+"["+index+"]");
  if( Field.value==oldValue )
    setBackColor(Field,"");
  else
    setBackColor(Field,BGCOLORU);
}

/**
* ���������ֵ��ָ�������꣨ex,ey���У�ͨ��span��ʾ����
* @param oldValue �������ֵ
* @param ex X����
* @param ey Y����
*/
function showOldValue(oldValue,ex,ey)
{
  spanOldValue.innerHTML = oldValue;
  spanOldValue.style.left=ex;
  spanOldValue.style.top=ey;
  spanOldValue.style.display ='';
}

/**
* ͨ����span����Ϊ���ɼ���NONE��������ֵ
*/
function hideOldValue()
{
  spanOldValue.style.display ='none';
}

/**
* ��ͨ��mouseover�¼�������ʹ��δ֪��ʹ�ú�������
* @param Field HTMLҳ��Ķ�������


*/
function commonOldValue(Field)
{
  var i;
  var oldValue = eval("old"+Field.name);
  if( Field.value!=oldValue )
  {
    var ex=window.event.clientX+document.body.scrollLeft;
    var ey=window.event.clientY+document.body.scrollTop;
    if( Field.tagName == "SELECT" )
    {
			for(i=0;i<Field.options.length;i++)
			{
				if(Field.options[i].value==oldValue)
				{
				  oldValue = Field.options[i].text;
				  break;
				}
      }
    }
    showOldValue(oldValue,ex,ey);
  }
}

/**
* �����������mouseover�¼�(��ʾ����)������ʹ��δ֪��ʹ�ú�������
* @param Field HTMLҳ��Ķ�������
*/
function mulline1OldValue(Field,PageName)


{
  var i;
  var flen=document.all(Field.name).length;
  var index=0;
  var oldValue;
  for(i=0;i<flen;i++)
  {
    if( document.all(Field.name)[i]==Field )
    {
      index = i;
      break;
    }
  }
  if( index+1>eval("old"+PageName+"Num") ) return ;
  oldValue = eval("old"+Field.name+"["+index+"]");
  if( Field.value!=oldValue )
  {
    var ex=window.event.clientX+document.body.scrollLeft;
    var ey=window.event.clientY+document.body.scrollTop;
    if( Field.tagName == "SELECT" )
    {
			for(i=0;i<Field.options.length;i++)
			{
				if(Field.options[i].value==oldValue)



				{
				  oldValue = Field.options[i].text;
				  break;
				}
      }
    }
    showOldValue(oldValue,ex,ey);
  }

}

/**
* �����������mouseover�¼�(��ʾһ��)������ʹ��δ֪��ʹ�ú�������
* @param Field HTMLҳ��Ķ�������
*/
function mulline2OldValue(Field,PageName)
{
  var index=0;
  index = pagesAttributes[PageName]["curindex"];
  if( index+1>eval("old"+PageName+"Num") ) return ;
  oldValue = eval("old"+Field.name+"["+index+"]");
  if( Field.value!=oldValue )
  {
    var ex=window.event.clientX+document.body.scrollLeft;
    var ey=window.event.clientY+document.body.scrollTop;
    if( Field.tagName == "SELECT" )
    {
			for(i=0;i<Field.options.length;i++)
			{
				if(Field.options[i].value==oldValue)
				{
				  oldValue = Field.options[i].text;
				  break;
				}
      }
    }
    showOldValue(oldValue,ex,ey);
  }

}

/**
* ���ĵ�����ɾ����־������ʹ��δ֪��ʹ�ú�������
* @param flagName δ֪��ʹ�ú�������
* @return δ֪��ʹ�ú�������
*/
function setFlagText(flagName)
{
  var i;
  var flagText = "";
  for(i=0;i<eval(flagName+".length");i++)
  {
    flagText = flagText + "<input name='" + flagName+"s' "
                        + "value='" + eval(flagName+"["+i+"]") + "'>";
  }
  return flagText;
}

/**
* �ӱ�(����)������ʹ��δ֪��ʹ�ú�������
* @param vNewAmount δ֪��ʹ�ú�������
* @param vOldAmount δ֪��ʹ�ú�������
* @param vNewRate δ֪��ʹ�ú�������
* @param vOldRate δ֪��ʹ�ú�������
* @param vDiscount δ֪��ʹ�ú�������
* @param vShortRate δ֪��ʹ�ú�������
* @return δ֪��ʹ�ú�������
*/
function incAmount(vNewAmount,vOldAmount,vNewRate,vOldRate,vDiscount,vShortRate)
{
  var Dpremium =
    ( parseFloat(vNewAmount)-parseFloat(vOldAmount) ) * parseFloat(vNewRate) * parseFloat(vDiscount) * parseFloat(vShortRate)
    + parseFloat(vOldAmount) * ( parseFloat(vNewRate) - parseFloat(vOldRate) )  * parseFloat(vDiscount) * parseFloat(vShortRate);

  return Dpremium;
}

/**
* ����(����)������ʹ��δ֪��ʹ�ú�������
* @param vNewAmount δ֪��ʹ�ú�������
* @param vOldAmount δ֪��ʹ�ú�������
* @param vNewRate δ֪��ʹ�ú�������
* @param vOldRate δ֪��ʹ�ú�������
* @param vDiscount δ֪��ʹ�ú�������
* @param vOldShortRate δ֪��ʹ�ú�������
* @param vOverShortRate δ֪��ʹ�ú�������
* @return δ֪��ʹ�ú�������
*/
function decAmount(vNewAmount,vOldAmount,vNewRate,vOldRate,vDiscount,vOldShortRate,vOverShortRate)
{
  var Dpremium =
    ( parseFloat(vNewAmount)-parseFloat(vOldAmount) ) * parseFloat(vNewRate) * parseFloat(vDiscount)
    * ( parseFloat(vOldShortRate) - parseFloat(vOverShortRate) )
    + parseFloat(vOldAmount) * ( parseFloat(vNewRate) - parseFloat(vOldRate) )  * parseFloat(vDiscount)
    * ( parseFloat(vOldShortRate) - parseFloat(vOverShortRate) );

  return Dpremium;
}

/**
* �ӱ�(��������)������ʹ��δ֪��ʹ�ú�������
* @param vAmount δ֪��ʹ�ú�������
* @param vRate δ֪��ʹ�ú�������
* @param vDiscount δ֪��ʹ�ú�������
* @param vShortRate δ֪��ʹ�ú�������
* @return δ֪��ʹ�ú�������
*/
function incTime(vAmount,vRate,vDiscount,vShortRate)
{
  var Dpremium =
    parseFloat(vAmount) * parseFloat(vRate) * parseFloat(vDiscount) * parseFloat(vShortRate);
  return Dpremium;
}

/**
* ����(��������)������ʹ��δ֪��ʹ�ú�������
* @param vAmount δ֪��ʹ�ú�������
* @param vRate δ֪��ʹ�ú�������
* @param vDiscount δ֪��ʹ�ú�������
* @param vOldShortRate δ֪��ʹ�ú�������
* @param vNewShortRate δ֪��ʹ�ú�������
* @return δ֪��ʹ�ú�������
*/
function decTime(vAmount,vRate,vDiscount,vOldShortRate,vNewShortRate)
{
  var Dpremium =
    parseFloat(vAmount) * parseFloat(vRate) * parseFloat(vDiscount)
    * ( parseFloat(vNewShortRate) - parseFloat(vOldShortRate) );
  return Dpremium;
}

/**
* ����ֵ��������Ϊ����ֵ��isMulti��ʾ�����Ƿ�Ϊ���������
* <p><b>Example: </b><p>
* <p>setEmpty([name1,name2], [Minim, Hzm], 2)<p>
* @param FieldName HTMLҳ��Ķ�������
* @param FieldValue Ҫ���������ֵ
* @param isMulti ��־������ǵ��������Ƕ�������
*/
function setEmpty(FieldName,FieldValue,isMulti)
{
		var i = 0;
		if (!isMulti)
		{
			if (document.all(FieldName).value == "")
				document.all(FieldName).value = FieldValue;
		}
		else
		{
			for(i = 0; i< document.all(FieldName).length; i++)
			{
				theField = document.all(FieldName)[i];
				if (trim(theField.value) == "" || eval(theField.value) == 0)
					theField.value = FieldValue;
			}
		}
}

/**
* ���ĵļ��㱣��(����仯ʱ)������ʹ��δ֪��ʹ�ú�������
* @param Field δ֪��ʹ�ú�������
* @param ext δ֪��ʹ�ú�������
*/
function calAmountPremium(Field,ext)
{
	var fieldname=Field.name;
	var i = 0;
	var findex=0;
	//�õ�������
	for(i=0;i<document.all(fieldname).length;i++)

	{
		if( document.all(fieldname)[i] == Field )
		{
			findex=i;
			break;
		}
	}
	//�õ���ֵ
	var amountValue    = document.all("Amount"+ext)[findex].value;         //�±���
	var rateValue      = document.all("Rate"+ext)[findex].value;           //�·���
	var shortRateValue = document.all("ShortRate"+ext)[findex].value;      //δ�˶��ڷ���
	var discountValue  = document.all("Discount"+ext)[findex].value;       //�ۿ���
	var vShortrateFlag = document.all("ShortrateFlag"+ext)[findex].value;  //���ڷ��ʷ�ʽ
	//����ֵ�ĺϷ���
	if(vShortrateFlag=="1") vShortrateFlag = "M";
	else vShortrateFlag = "D";

	if( !isNumeric(amountValue) || !isNumeric(rateValue)
	    || !isNumeric(discountValue) || !isNumeric(shortRateValue) ) return ;
	if( eval(amountValue)<0 || eval(rateValue)<0
		  || eval(discountValue)<0 || eval(shortRateValue)<0) return ;

  //ԭֵ����
	var pv = 0;             //����
	var dpv = 0;            //���ѱ仯��
	var vOldAmount = 0;     //ԭ����
	var vOldRate = 0 ;      //ԭ����
	var vShortRate = 0;     //ԭ���ڷ���
	var vOverShortRate = 0; //�������ζ��ڷ���
	if( findex < eval("oldAmount"+ext+".length") ) //�޸ı��
	{
	  pv = eval("oldPremium"+ext+"["+findex+"]");        //ԭ����
	  vOldAmount = eval("oldAmount"+ext+"["+findex+"]"); //ԭ����
	  vOldRate   = eval("oldRate"+ext+"["+findex+"]");   //ԭ����
	}
	else
	{
	  pv = 0;         //ԭ����
	  vOldAmount = 0; //ԭ����
	  vOldRate = 0;   //ԭ����
	}

	//����
	var sdate,edate,pdate,pprevdate;
  var tmpd = fm.ValidDate.value.split("/");
  pdate = new Date(tmpd[0],parseInt(tmpd[1],10)-1,tmpd[2]);
  sdate = new Date(fm.StartDateYear.value,parseInt(fm.StartDateMonth.value,10)-1,fm.StartDateDay.value );
  edate = new Date(fm.EndDateYear.value,parseInt(fm.EndDateMonth.value,10)-1,fm.EndDateDay.value );
  pprevdate = new Date(pdate.getFullYear(),pdate.getMonth(),pdate.getDate()-1);

  //δ�����ζ��ڷ���
  if( vShortrateFlag=="M" )
    shortRateValue = monthToRate(dateDiff(pdate,edate,vShortrateFlag));
  else
    shortRateValue = dateDiff(pdate,edate,vShortrateFlag)/365;
  //ԭ���ڷ���
  if( vShortrateFlag=="M" )
    vShortRate = monthToRate(dateDiff(sdate,edate,vShortrateFlag));
  else
    vShortRate = dateDiff(sdate,edate,vShortrateFlag)/365;
  //�������ζ��ڷ���
  if( vShortrateFlag=="M" )
    vOverShortRate = monthToRate(dateDiff(sdate,pprevdate,vShortrateFlag));
  else
    vOverShortRate = dateDiff(sdate,pprevdate,vShortrateFlag)/365;
	if( vOldAmount==0 || vOldAmount<=parseFloat(amountValue) )
	{
	  dpv = incAmount( parseFloat(amountValue),
	             vOldAmount,
	             parseFloat(rateValue)/1000,
	             parseFloat(vOldRate)/1000,
	             discountValue/100,
	             shortRateValue);
	}
	else
	{
	  dpv = decAmount( parseFloat(amountValue),
	             vOldAmount,
	             parseFloat(rateValue)/1000,
	             parseFloat(vOldRate)/1000,
	             discountValue/100,
	             vShortRate,
	             vOverShortRate
	             );
	}

	pv = parseFloat(pv) + dpv;
	pv = pointTwo(mathRound(pv));
	document.all("Premium"+ext)[findex].value= pv;
	document.all("Premium"+ext)[findex].onchange();
}

/**
* ���ĵļ��㱣��(�������ޱ仯ʱ)������ʹ��δ֪��ʹ�ú�������
* @param Field δ֪��ʹ�ú�������
* @param ext δ֪��ʹ�ú�������
*/
function calTimePremium(fieldname,ext)
{
  var i;

	var vShortRate = 0;     //ԭ���ڷ���(��-ԭ�ձ�)
	var vOverShortRate = 0; //�������ζ��ڷ���(��-���ձ�)
  var shortRateValue = 0; //δ�˶��ڷ���(ԭ�ձ�-���ձ�)
	//����
	var sdate,edate,pdate,enextdate;
  pdate = new Date(fm.EndDateYear.value,parseInt(fm.EndDateMonth.value,10)-1,fm.EndDateDay.value );
  sdate = new Date(fm.StartDateYear.value,parseInt(fm.StartDateMonth.value,10)-1,fm.StartDateDay.value );
  edate = new Date(oldEndDateYear,parseInt(oldEndDateMonth,10)-1,oldEndDateDay);
  enextdate = new Date(edate.getFullYear(),edate.getMonth(),edate.getDate()+1);

  //�����������
  for(i=0;i<parseInt(document.all(fieldname).value,10);i++)
  {
  	//�õ���ֵ
  	var amountValue    = document.all("Amount"+ext)       [i].value;  //�±���
  	var rateValue      = document.all("Rate"+ext)         [i].value;  //�·���
  	var discountValue  = document.all("Discount"+ext)     [i].value;  //�ۿ���
    var vShortrateFlag = document.all("ShortrateFlag"+ext)[i].value;  //���ڷ��ʷ�ʽ
  	if(vShortrateFlag=="1") vShortrateFlag = "M";
  	else vShortrateFlag = "D";
    //δ�����ζ��ڷ���
    if( vShortrateFlag=="M" )
      shortRateValue = monthToRate(dateDiff(enextdate,pdate,vShortrateFlag));
    else
      shortRateValue = dateDiff(edate,pdate,vShortrateFlag)/365;
    //ԭ���ڷ���
    if( vShortrateFlag=="M" )
      vShortRate = monthToRate(dateDiff(sdate,edate,vShortrateFlag));
    else
      vShortRate = dateDiff(sdate,edate,vShortrateFlag)/365;
    //�������ζ��ڷ���
    if( vShortrateFlag=="M" )
      vOverShortRate = monthToRate(dateDiff(sdate,pdate,vShortrateFlag));
    else
      vOverShortRate = dateDiff(sdate,pdate,vShortrateFlag)/365;

  	//����ֵ�ĺϷ���
  	if( !isNumeric(amountValue) || !isNumeric(rateValue)
  	    || !isNumeric(discountValue) ) continue;
  	if( eval(amountValue)<0 || eval(rateValue)<0
  		  || eval(discountValue)<0 ) continue;

    //���㱣��
  	var pv = eval("oldPremium"+ext+"["+i+"]"); //����

  	if( isNaN(parseFloat(pv)) ) //�����
  	{
      document.all("Premium"+ext)[i].value = "";
  	  document.all("Premium"+ext)[i].onchange();
  	  return ;
  	}
  	var dpv = 0;            //���ѱ仯��
  	if( edate.getTime()<pdate.getTime() )
  	{
  	  dpv = incTime(parseFloat(amountValue),
  	                parseFloat(rateValue)/1000,
  	                discountValue/100,
  	                shortRateValue);
  	}
  	else
  	{
  	  dpv = decTime(parseFloat(amountValue),
  	                parseFloat(rateValue)/1000,
  	                discountValue/100,
  	                vShortRate,
  	                vOverShortRate);
  	}
  	pv = parseFloat(pv) + dpv;
  	pv = pointTwo(mathRound(pv));
  	document.all("Premium"+ext)[i].value= pv;
  	document.all("Premium"+ext)[i].onchange();
  }
}


/**
* ��С��������λ��������
* <p><b>Example: </b><p>
* <p>mathRound(123.456) returns 123.46<p>
* @param intValue ������ֵ
* @return ��С��������λ����������������ֵ
*/
function mathRound( x )
{
  var v = Math.round( x * 100 ) ;
  v = v/100;
  return v;
}

/**
* �����ְ�0.00��ʽ��
* <p><b>Example: </b><p>
* <p>pointTwo(123.456) returns 123.45<p>
* <p>pointTwo(123) returns 123.00<p>
* @param intValue ������ֵ
* @return ��0.00��ʽ�����������ֵ
*/
function pointTwo( s )
{
  var v = s.toString();
  var len = v.length;
  var index = v.indexOf(".");

  if( index==-1 )
  {
    v = v + ".00";
    return v;
  }
  else
  {
    if( len-index==3 )
    {
      return v;
    }
    else if( len-index==2 )
    {
      v = v +"0";
      return v;
    }
    else if( len-index==1 )
    {
      v = v + "00";
      return v;
    }
    else
    {
      return v.substring(0,index+3);
    }
  }
}

/**
* �����ְ�0.0000��ʽ��
* <p><b>Example: </b><p>
* <p>pointFour(123.456789) returns 123.4567<p>
* <p>pointFour(123) returns 123.0000<p>
* @param intValue ������ֵ
* @return ��0.0000��ʽ�����������ֵ
*/
function pointFour( s )
{
	var v = Math.round( parseFloat(s) * 10000 )/10000;
  v = v.toString();

  //var v = s.toString();

  var len = v.length;
  var index = v.indexOf(".");

  if( index==-1 )
  {
    v = v + ".0000";
    return v;
  }
  else
  {
    if( len-index==5)
    {
      return v;
    }
    else if( len-index==4 )
    {
      v = v +"0";
      return v;
    }
    else if( len-index==3 )
    {
      v = v + "00";
      return v;
    }
    else if( len-index==2 )
    {
      v = v + "000";
      return v;
    }
    else if( len-index==1 )
    {
      v = v + "0000";
      return v;
    }
    else
    {
      return v.substring(0,index+5);
    }
  }
}

/**
* ����һ����ʽ�������ַ���
* <p><b>Example: </b><p>
* <p>dateToString("2002-10-4") returns "2002/10/4"<p>
* @param date �����ͱ���
* @return ��YYYY/MM/DD����ʽ�������ַ���
*/
function dateToString(d)
{
  return  d.getFullYear() +"/"+
          (d.getMonth()<9?("0"+(d.getMonth()+1)):(d.getMonth()+1) ) +"/"+
          (d.getDate()<10?("0"+d.getDate()):d.getDate() );
}

/**
* ��������е���һ��alert����ʾ������Ϣ
* @param strErrMsg Ҫ��ʾ�Ĵ�����Ϣ�ַ���
*/
function errorMessage(strErrMsg)
{
	alert(strErrMsg);
}

/**
* ��ʾ��ӡ���ڣ���Ҫ��ͳһ��ӡ���ڵ���ʽ
* <p><b>Example: </b><p>
* <p>printWindow("../print.jsp", null)<p>
* @param strURL �´��ڵ�����·����URL�������·��
* @param strWindowName ָ��������������Ϊ��
* @return �����½����ڵľ��
*/
function printWindow(strURL,strWindowName)
{
  var pageWidth=screen.availWidth-10;
  var pageHeight=screen.availHeight-30;
  if (pageWidth<100 )
  {
    pageWidth = 100;
  }
  if (pageHeight<100 )
  {
    pageHeight = 100;
  }

  var newWindow = window.open(strURL,strWindowName,'width='+pageWidth+',height='+pageHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1.resizable=1,status=0');
  newWindow.focus();
  return newWindow;
}

/**
* ���������Ƿ������ڵ�У��(���ڸ�ʽxxxx/xx/xx)�������޸ģ���isDate�����ϲ�
* <p><b>Example: </b><p>
* <p>isDateI("2004/10/4") returns true<p>
* <p>isDateI("2004-10-4") returns false<p>
* @param date ��ʽ����Ϊ��YYYY/MM/DD���������ַ���
* @return ����ֵ��true--�Ϸ�����, false--�Ƿ����ڣ�
*/
function isDateI(date)
{
  var strValue;
  strValue=date.split("/");

  if(strValue.length!=3) return false;
  if(!isInteger(strValue[0]) || !isInteger(strValue[1]) || !isInteger(strValue[2]) ) return false;

  var intYear=eval(strValue[0]);
  var intMonth=eval(strValue[1]);
  var intDay=eval(strValue[2]);

  if( intYear<0 || intYear>9999 || intMonth<0 || intMonth>12 || intDay<0 || intDay>31 ) return false;
  return true;
}

/**
* ���������Ƿ������ڵ�У��(���ڸ�ʽxxxxxxxx)�������޸ģ���isDate�����ϲ�
* <p><b>Example: </b><p>
* <p>isDateI("2004104") returns true<p>
* <p>Other returns false<p>
*/
function isDateN(date)
{
  var strValue;
  strValue=new Array();
  strValue[0]=date.substring(0, 4);
  strValue[1]=date.substring(4, 6);
  strValue[2]=date.substring(6, 8);
  if(strValue.length!=3) return false;
  if(!isInteger(strValue[0]) || !isInteger(strValue[1]) || !isInteger(strValue[2]) ) return false;

  var intYear=eval(strValue[0]);
  var intMonth=eval(strValue[1]);
  var intDay=eval(strValue[2]);

  if( intYear<0 || intYear>9999 || intMonth<0 || intMonth>12 || intDay<0 || intDay>31 ) return false;
  return true;
}

/**
* �Ƚ����������ַ���(���ڸ�ʽxxxx/xx/xx)
* <p><b>Example: </b><p>
* <p>compareDateI("2002/10/03", "2002/10/03") returns 0<p>
* <p>compareDateI("2002/10/03", "2001/10/03") returns 1<p>
* @param date1 �����ַ���,��ʽ����Ϊ��yyyy/mm/dd��
* @param date2 �����ַ���,��ʽ����Ϊ��yyyy/mm/dd��
* @return date1=date2�򷵻�0 , date1>date2�򷵻�1 , date1<date2�򷵻�2
*/
function compareDateI(date1,date2){
    var strValue=date1.split("/");
    var date1Temp=new Date(strValue[0],strValue[1]-1,strValue[2]);
    strValue=date2.split("/");
    var date2Temp=new Date(strValue[0],strValue[1]-1,strValue[2]); //lanjun 2007/2/1 js�е���Ϊ0~11
    if(date1Temp.getTime()==date2Temp.getTime()){
        return 0;
    }else if(date1Temp.getTime()>date2Temp.getTime()){
        return 1;
    }else{
        return 2;
    }
}


/**
*�õ���ǰ��ϵͳʱ�䣺
*splitOp Ϊ�ָ����Example��
*splitOp='-' �����ڸ�ʽΪ ��-��-��
*splitOp='/' �����ڸ�ʽΪ ��/��/��
*splitOp���Ϊ�գ���Ĭ���ǣ�'-'
*/
function getCurrentDate(splitOp)
{
   if(splitOp==null) splitOp='-';
   if(trim(splitOp)=='') splitOp='-';
   var SystemDate=new Date();
   var year=SystemDate.getFullYear();
   var month=SystemDate.getMonth()+1;
   var day=SystemDate.getDate();
   var CurrentDate=year+splitOp+month+splitOp+day;
   return CurrentDate;
}


/**
* ���������Ƿ��������ѯ��ʽ�����ڵ�У��(���ڸ�ʽxxxx/xx/xx)
* <p><b>Example: </b><p>
* <p>isQueryDate(":", "2002/10/03:2002/10/04") returns true<p>
* <p>isQueryDate("", "2001/10/03") returns true<p>
* @param sign ��������͵�һ���ڵ��жϱ�־,���������ڲ������
* @param date �����������ݵ��ַ���
* @return ����ֵ��true--�Ϸ�����, false--�Ƿ����ڣ�
*/
function isQueryDate(sign,date)
{
  var strValue;

  //������ж�
  if (sign==":")
  {
  	strValue=date.split(":");
  	if (strValue.length!=2) return false;
  	if (!isDateI(strValue[0])) return false;
  	if (!isDateI(strValue[1])) return false;
  	if (compareDateI(strValue[0],strValue[1])==1) return false;

	}
	//��һ���ڵ��ж�
	else
	{
		return isDateI(date);
	}
  return true;
}

/**
* ���������Ƿ��������ѯ��ʽ��������У��integer
* <p><b>Example: </b><p>
* <p>isQueryInteger(":", "2002.12:2003.34") returns true<p>
* <p>isQueryInteger("", "2001.567") returns true<p>
* @param sign ��������͵�һ�������жϱ�־
* @param integer �����������ݵ��ַ����������������������
* @return ����ֵ��true--�Ϸ�������ʽ, false--�Ƿ�������ʽ��
*/
function isQueryInteger(sign,integer)
{
  var strValue;

  //������ж�
  if (sign==":")
  {
  	strValue=integer.split(":");
  	if (strValue.length!=2) return false;
  	if (!isInteger(strValue[0])) return false;
  	if (!isInteger(strValue[1])) return false;
  	if (strValue[0]>strValue[1]) return false;
	}
	//��һ���ڵ��ж�
	else
	{
		return isInteger(integer);

	}
  return true;
}

/**
* ���������Ƿ��������ѯ��ʽ�����ֵ�У��
* <p><b>Example: </b><p>
* <p>isQueryNum(":", "2002:2003") returns true<p>
* <p>isQueryNum("", "2001") returns true<p>
* @param sign ��������͵�һ���ֵ��жϱ�־
* @param num �����������ݵ��ַ��������������ֲ������
* @return ����ֵ��true--�Ϸ����ָ�ʽ, false--�Ƿ����ָ�ʽ��
*/
function isQueryNum(sign,num)
{
  var strValue;

  //������ж�
  if (sign==":")
  {
  	strValue=num.split(":");
  	if (strValue.length!=2) return false;
  	if (!isNumeric(strValue[0])) return false;
  	if (!isNumeric(strValue[1])) return false;
  	if (strValue[0]<strValue[1]) return false;
	}
	//��һ���ڵ��ж�
	else
	{
		return isNumeric(num);
	}
  return true;
}

/**
* ��ͼƬ����ʾ������
* @param imgID HTML�п���ʾͼƬ�Ķ����ID
* @param stl ������ʾ�����صı�־������Ϊ��ʾ����none��Ϊ����
*/
function showImg(imgID,stl)
{
  document.all(imgID).style.display = stl;

}

/**
* ����***���븳ֵ --����ά��ģ��ר��onblur=setNewCode(this)
* @param field HTMLҳ��Ķ�������
*/
function setNewCode(field)
{
  if( trim(document.all("new"+field.name).value)=="" )
  {
    document.all("new"+field.name).value = field.value;
  }
}

//�ظ���������������������
//���������Ƿ������ڵ�У��
function isCodeDate(date)
{
  var strValue;
  strValue=date.split("/");

  if(strValue.length!=3) return false;
  if(!isInteger(strValue[0]) || !isInteger(strValue[1]) || !isInteger(strValue[2]) ) return false;

  var intYear=eval(strValue[0]);
  var intMonth=eval(strValue[1]);
  var intDay=eval(strValue[2]);

  if( intYear<0 || intYear>9999 || intMonth<0 || intMonth>12 || intDay<0 || intDay>31 ) return false;
  return true;
}

/**
* ��ȡ�ַ����Ĳ����Ӵ����ú����õ�c_Str�еĵ�c_i����c_Split�ָ���ַ���
* <p><b>Example: </b><p>
* <p>getStr("Minim|Hzm|Yt|", "2", "|") returns "Hzm"<p>
* @param c_Str �зָ�������ַ���
* @param c_i ȡ�ڼ����ָ��Ӵ�
* @param c_Split �ָ���
* @return ���ص�c_i���ָ��Ӵ�
*/
function getStr(c_Str , c_i ,c_Split)
{
  var t_Str1, t_Str2 , t_strOld;
  var i, i_Start, j_End;
  t_Str1 = c_Str;
  t_Str2 = c_Split;
  i = 0;
	try
	{
    while (i < c_i)
    {
      i_Start = t_Str1.indexOf(t_Str2,0);
      if (i_Start >= 0)
      {
        i = i + 1;
        t_strOld = t_Str1;
        t_Str1 = t_Str1.substring(i_Start+t_Str2.length,t_Str1.length);
      }
      else
      {
        if (i != c_i - 1)
        {
          t_Str1="";
        }
        break;
      }
    }

    if (i_Start >= 0)
      t_Str1=t_strOld.substring(0,i_Start);
  }
  catch(ex)
  {
    t_Str1="";
  }
  return t_Str1;
}

/**
* ���ַ���������Ϊһ�����飬���ַ�����ͷ���з�����Ϣ
* <p><b>Example: δ���ԣ���ȷ�����ӵ���ȷ��</b><p>
* <p>decodeString("Minim|1^Hzm|2^Yt|3") returns "3��Minim,1,Hzm,2,Yt,3"<p>
* @param strValue ��Ҫ�������ַ���,ͨ���ǲ�ѯ���ݿⷵ�صĽ���ַ���
* @return ���ִ�гɹ����򷵻��Լ�¼Ϊ�У��ֶ�Ϊ�еĶ�Ψ���飬���ִ�в��ɹ����򷵻�false
*/
function decodeString(strValue)
{
	var i,i1,j,j1;
  var strValue;                         //��ŷ������˷��صĴ�������
  var arrField;
  var arrRecord;
  var arrCode = new Array();             //��ų�ʼ������ʱ��
  var t_Str;

  try
  {
    arrRecord = strValue.split(RECORDDELIMITER);  //����ַ������γɷ��ص�����

    t_Str     = getStr(arrRecord[0],1,FIELDDELIMITER);

    if (t_Str!="0")                                     //�����Ϊ0��ʾ��������ִ�з�������
    {
      return false;
    }

    i1=arrRecord.length;
    for (i=1;i<i1;i++)
    {
      arrField  = arrRecord[i].split(FIELDDELIMITER); //����ַ���,��ÿ����¼���Ϊһ������
      j1=arrField.length;
      arrCode[i-1] = new Array();
      for (j=0;j<j1;j++)
      {
        arrCode[i-1][j] = arrField[j];
      }
    }
  }
  catch(ex)
  {
    return false;
  }
  return arrCode;
}

/**
* ���ַ���������Ϊһ�����飬���ַ�����ͷ��û���з�����Ϣ
* <p><b>Example: δ���ԣ���ȷ�����ӵ���ȷ��</b><p>
* <p>decodeStringNoHead("Minim|1^Hzm|2^Yt|3") returns "Minim,1,Hzm,2,Yt,3"<p>
* @param strValue ��Ҫ�������ַ���,ͨ���ǲ�ѯ���ݿⷵ�صĽ���ַ���
* @return ���ִ�гɹ����򷵻��Լ�¼Ϊ�У��ֶ�Ϊ�еĶ�Ψ���飬���ִ�в��ɹ����򷵻�false
*/
function decodeStringNoHead(strValue)
{
	var i,i1,j,j1;
  var strValue;                         //��ŷ������˷��صĴ�������
  var arrField;
  var arrRecord;
  var arrCode = new Array();             //��ų�ʼ������ʱ��
  var t_Str;
  if(strValue==null || strValue=="")
    return false;

  try
  {
    arrRecord = strValue.split(RECORDDELIMITER);  //����ַ������γɷ��ص�����
    i1=arrRecord.length;
    for (i=0;i<i1;i++)
    {
      arrField  = arrRecord[i].split(FIELDDELIMITER); //����ַ���,��ÿ����¼���Ϊһ������
      j1=arrField.length;
      arrCode[i] = new Array();
      for (j=0;j<j1;j++)
      {
        arrCode[i][j] = arrField[j];
      }
    }
  }
  catch(ex)
  {
    return false;
  }
  return arrCode;
}

/**
* �жϺ������ͣ�����˱����ţ����屣����
* �ڡ���Ŀ�淶_Լ�����ġ��¾ɺ������.xls���й涨�������[12,13]λ��Ϊ�������ͱ�־;
* <p><b>Example: </b><p>
* <p>getCodeType("abcdefghijk11asdfasdf") returns "11"<p>
* @param strCode �������ַ���
* @return �����ַ���
*/
function getCodeType( strCode ) {
    if ( (strCode == null) || (strCode == "") ) {
      return "00";
    } else {
    //�ڡ���Ŀ�淶_Լ�����ġ��¾ɺ������.xls���й涨�������[12,13]λ��Ϊ�������ͱ�־
      return strCode.substring(11, 13);
    };
}

/**
* �ж���������а��������ͺ����ָ�����ͺ����Ƿ�һ��
* �ڡ���Ŀ�淶_Լ�����ġ��¾ɺ������.xls���й涨�������[12,13]λ��Ϊ�������ͱ�־;
* <p><b>Example: </b><p>
* <p>judgeCodeType("abcdefghijk11asdfasdf", "11") returns ture<p>
* @param strCode �������ַ���
* @param strType ���ͺ��룬���ա��¾ɺ������.xls��
* @return ����ֵ��true--һ��, false--��һ�£�
*/
function judgeCodeType( strCode, strType ) {
    if ( (strCode == null) || (strCode == "") || (strType == null) || (strType == "") ) {
      return false;
    } else {
      return (getCodeType(strCode).compareTo(strType) == 0);
    }
}

/**
* ��ս����ϵ�����������
* <p><b>Example: </b><p>
* <p>EmptyFormElements()<p>
*/
function emptyFormElements() {
  var formsNum = 0;          //�����е�FORM��
  var elementsNum = 0;       //FORM�е�Ԫ����

  //��������FORM
  for (formsNum=0; formsNum<window.document.forms.length; formsNum++) {
    //����FORM�е�����ELEMENT
    for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++) {
  	  if (window.document.forms[formsNum].elements[elementsNum].type == "text") {
  	    window.document.forms[formsNum].elements[elementsNum].value = "";
  	  }
  	}
  }
}

/**
* �������ϵ�������������Ϊ"undefined"���
* <p><b>Example: </b><p>
* <p>EmptyFormElements()<p>
*/
function emptyUndefined() {
  var formsNum = 0;          //�����е�FORM��
  var elementsNum = 0;       //FORM�е�Ԫ����

  //��������FORM
  for (formsNum=0; formsNum<window.document.forms.length; formsNum++) {
    //����FORM�е�����ELEMENT
    for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++) {
  	  if ((window.document.forms[formsNum].elements[elementsNum].value == "undefined"
  	      || window.document.forms[formsNum].elements[elementsNum].value == "null")
  	      &&
  	      (window.document.forms[formsNum].elements[elementsNum].type == "text"
  	      || window.document.forms[formsNum].elements[elementsNum].type == "textarea")) {
  	    window.document.forms[formsNum].elements[elementsNum].value = "";
  	  }
  	}
  }
}


/**
* ʹ��һά�����д�ŵ����������˶�ά����
* <p><b>Example: </b><p>
* <p>chooseArray({{1��2}��{3��4}}, {0}) returns {{1}��{3}}<p>
* @param dataArray ������ݵĶ�ά����
* @param filterArray �����������һά����
* @return ��һά�����е��������˹��Ķ�ά����
*/
function chooseArray(dataArray, filterArray) {
  var arrResult = new Array();
  var recordNum, filterNum;

  try {
    for (recordNum=0; recordNum<dataArray.length; recordNum++) {
      arrResult[recordNum] = new Array();

      for (filterNum=0; filterNum<filterArray.length; filterNum++) {
        arrResult[recordNum].push(dataArray[recordNum][filterArray[filterNum]]);
      }
    }
  } catch(ex) {
    alert("chooseArray������ִ���");
  }

  return arrResult;
}


/**
* ��js�ļ��е��ַ�ת���������ַ�
*/
function Conversion(strIn) {
	var strOut;
	strOut=replace(strIn,"@@Enter","\r\n");
	strIn=strOut;
	strOut=replace(strIn,"@@DouQuot","\"");
	strIn=strOut;
	strOut=replace(strIn,"@@SinQuot","\'");
	return strOut;
}

function initRequest() {
	var xmlhttp = null;
	try {

		if (window.XMLHttpRequest) {// for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	} catch (e) {
		console.log(e);
		alert("�Բ���!�����������֧�ָù���,��ʹ��Internet Explorer 6.0���������!");
	}
	return xmlhttp;
}

/**
 * ���ݴ���ѡ��Ĵ�����Ҳ���ʾ����
 */
function showCodeName()
{
	var formsNum = 0;	//�����е�FORM��
	var elementsNum = 0;	//FORM�е�Ԫ����
	var strEvent = "";	//����onDoubleClick�¼�����
	var urlStr = "";
	var sFeatures = "";
	var strCode = "";	//����ѡ��
	var strQueryResult = "";	//����ѡ��Ĳ�ѯ�����
	var arrCode = null;	//�������
	var strCodeValue = "";	//����ֵ
	var cacheFlag = false;	//�ڴ��������ݱ�־
	var strCodeSelect = "";

	//Ѱ��������
	var win = searchMainWindow(this);
	if(win == false)
	{
		win = this;
	}

	//��������FORM
	var tForCount = window.document.forms.length;
	for(formsNum=0; formsNum<tForCount; formsNum++)
	{
		//����FORM�е�����ELEMENT
		var tEleCount = window.document.forms[formsNum].elements.length;
		for(elementsNum=0; elementsNum<tEleCount; elementsNum++)
		{
			//Ѱ�Ҵ���ѡ��Ԫ��
			if(window.document.forms[formsNum].elements[elementsNum].className == "codeno")
			{



				//ȡ������ֵ
				strCodeValue = window.document.forms[formsNum].elements[elementsNum].value;


				//��ֵ�򲻴���
				if(strCodeValue == "") continue;

				//��������Դ����
				if(window.document.forms[formsNum].elements[elementsNum].CodeData != null)
				{
					strQueryResult = window.document.forms[formsNum].elements[elementsNum].CodeData;
				}
				else
				{
					//�Ӻ�̨ȡ����
					//ȡ��CODESELECT����
					strEvent = window.document.forms[formsNum].elements[elementsNum].ondblclick;
					strCode = new String(strEvent);
					strCode = strCode.substring(strCode.indexOf("showCodeList") + 14);
					strCode = strCode.substring(0, strCode.indexOf("'"));
					//����ڴ��������ݣ����ڴ���ȡ����
					if(win.parent.VD.gVCode.getVar(strCode))


					{
						arrCode = win.parent.VD.gVCode.getVar(strCode);
						cacheFlag = true;
					}
					else
					{
						if(strCode=="AgentCode"||strCode=="OccupationCode9")
						{
							//���ڴ��������ݺ�ְҵ������ݵ��������ϴ�������������ѯʱ������Ӱ�캺����ʾ�ٶ�
							//�ض����ǵĺ�����ѯ�����˵�������������������ѯ����������ٻ��棩
							urlStr = "../common/jsp/CodeQueryXML.jsp?codeType=" + strCode+"&codeField="+strCode+"&codeConditon="+strCodeValue;
						}
						else
						{
							urlStr = "../common/jsp/CodeQueryXML.jsp?codeType=" + strCode;
						}
						Request = initRequest();
						Request.open("GET", urlStr, false);
						Request.send(null);
						try
						{
							strQueryResult = Request.responseText.trim();
						}
						catch(e1)
						{
							alert("���뺺������"+e1.message);
						}
					}
				}
				//��ֳ�����
				try
				{
					if(!cacheFlag)
					{
						try
						{
							arrCode = decodeEasyQueryResult(strQueryResult,0,1);
						}
						catch(ex)
						{
							alert("ҳ��ȱ������EasyQueryVer3.js");
						}
						strCodeSelect = "";
						var arr2 = new Array();
						var tArrLength = arrCode.length;
						for(i=0; i<tArrLength; i++)
						{
							if(i%100==0)
							{
								arr2.push(strCodeSelect);
								strCodeSelect = "";
							}
							strCodeSelect = strCodeSelect + "<option value=" + arrCode[i][0] + ">" + arrCode[i][0] + "-" + arrCode[i][1] + "</option>";
						}
						arr2.push(strCodeSelect);
						strCodeSelect = "";
						tArrLength = arr2.length;
						for(i=0; i<tArrLength; i++)
						{
							strCodeSelect =  strCodeSelect + arr2[i];
						}
						if(strCode=="AgentCode" ||strCode=="OccupationType9")
						{
							//���ڴ��������ݺ�ְҵ������ݵ��������ϴ�������������ѯʱ������Ӱ�캺����ʾ�ٶ�
							//�ض����ǵĺ�����ѯ�����˵�������������������ѯ����������ٻ��棩
						}
						else
						{
							//����ֺõ����ݷŵ��ڴ���
							win.parent.VD.gVCode.addArrVar(strCode, "", arrCode);
							//�����Ƿ������ݴӷ������˵õ�,�����øñ���
							win.parent.VD.gVCode.addVar(strCode+"Select","",strCodeSelect);
						}
					}
					cacheFlag = false;
					tArrLength = arrCode.length;
					for(i=0; i<tArrLength; i++)
					{
						if(strCodeValue == arrCode[i][0])
						{
							window.document.forms[formsNum].elements[elementsNum].value = arrCode[i][0];
							window.document.forms[formsNum].elements[elementsNum+1].value = arrCode[i][1];
							break;
						}
					}
				}
				catch(ex)
				{}
			}
			//��ʾtitle
			if(window.document.forms[formsNum].elements[elementsNum].type == "text")
			{
				window.document.forms[formsNum].elements[elementsNum].title = window.document.forms[formsNum].elements[elementsNum].value;
			}
		}
	}
}


/**
* ���ݴ���ѡ��Ĵ�����Ҳ���ʾ���ƣ���ʾָ����һ�������ڴӺ�̨���ݿ��ѯ
* 2009-09-11 �����Ż������з�����ӷ���������ֱ�Ӵ������������ֵ����������ʱ��
* strCode - ����ѡ��Ĵ���
* showObjCode - �����ŵĽ����������
* showObjName - Ҫ��ʾ���ƵĽ����������
*/
function showOneCodeName(strCode, showObjCode, showObjName) {

	var formsNum = 0;	//�����е�FORM��
	var elementsNum = 0;	//FORM�е�Ԫ����
	var urlStr = "";
	var sFeatures = "";
	var strQueryResult = "";	//����ѡ��Ĳ�ѯ�����
	var arrCode = null;	//�������
	var strCodeValue = "";	//����ֵ
	var cacheFlag = false;	//�ڴ��������ݱ�־
	var showObjn;
	var showObjc;
	if (showObjName == null) showObjName = strCode;
	//��������FORM
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)

	{
		//����FORM�е�����ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)

		{
			//Ѱ�Ҵ���ѡ��Ԫ��
			//alert(window.document.forms[formsNum].elements[elementsNum].name);
			if (window.document.forms[formsNum].elements[elementsNum].name == showObjCode)
			{
				showObjc = window.document.forms[formsNum].elements[elementsNum];
			}
			if (window.document.forms[formsNum].elements[elementsNum].name == showObjName)
			{
				showObjn = window.document.forms[formsNum].elements[elementsNum];
				break;
			}
		}
	}
	//�����µķ���
	showOneCodeNameOfObj(strCode,showObjc,showObjn);
}
/**
* ��ʾ����ѡ���Ķ�Ӧ���������ƣ���ʾָ����һ��
* ��showOneCodeName�������Ǵ�����Ƕ��󣬲����ַ����������Ϳ���ֱ�Ӹ�ֵ������Ҫ����
* strCode - ����ѡ��Ĵ���
* showCodeObj - �����ŵĽ������
* showNameObj - Ҫ��ʾ���ƵĽ������
*/
function showOneCodeNameOfObj(strCode, showCodeObj, showNameObj) {
	showOneCodeNameOfObjEx(strCode,showCodeObj,showNameObj,null,null);
}
/**
* ��ʾ����ѡ���Ķ�Ӧ���������ƣ���ʾָ����һ��
* ��showOneCodeName�������Ǵ�����Ƕ��󣬲����ַ����������Ϳ���ֱ�Ӹ�ֵ������Ҫ����
* strCode - ����ѡ��Ĵ���
* showCodeObj - �����ŵĽ������
* showNameObj - Ҫ��ʾ���ƵĽ������
* strField    - �����ѯ�����ֶ�
* strCondition - �ֶζ�Ӧ����ֵ
*/
function showOneCodeNameOfObjEx(strCode, showCodeObj, showNameObj, strField, strCondition) {
	var urlStr = "";
	var sFeatures = "";
	var strQueryResult = "";	//����ѡ��Ĳ�ѯ�����
	var arrCode = null;	//�������
	var strCodeValue = "";	//����ֵ
	var cacheFlag = false;	//�ڴ��������ݱ�־
	if (showCodeObj == null || typeof(showCodeObj) == "string") return;
	if (showNameObj == null || typeof(showNameObj) == "string") return;
	//��������������ݲ�Ϊ�գ��Ų�ѯ���������κβ���
	if (showCodeObj.value != "")
	{
		//Ѱ��������
		var win = searchMainWindow(this);
		if (win == false) { win = this; }
		//��������������ݣ���������ȡ����
		if (win.parent.VD.gVCode.getVar(strCode))
		{
			arrCode = win.parent.VD.gVCode.getVar(strCode);
			cacheFlag = true;
		}
		else
		{
		    if (strField != null && strField != '' && strCondition != null && strCondition != '')
		    {
			    urlStr = "../common/jsp/CodeQueryWindow.jsp?codeType=" + strCode.replace("&","%26") + "&codeField=" + strField + "&codeConditon=" + strCondition;
		    }
		    else
		    {
			    urlStr = "../common/jsp/CodeQueryWindow.jsp?codeType=" + strCode.replace("&","%26");
		    }
			//�������ݿ����CODE��ѯ�����ز�ѯ�����strQueryResult
		    Request = initRequest();
	        Request.open("POST",urlStr, false);
	        Request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	        Request.send(null);
	        try
	        {
	            strQueryResult = Request.responseText;
	            if (strQueryResult != null && typeof(strQueryResult) == "string")
	            {
	                strQueryResult = strQueryResult.trim();
	            }
	        }catch (ex)
	        {
	            //alert("���ݷ��س���" + ex.message);
	        }
		}
		//��ֳ�����
		try {
			if (!cacheFlag)
			{
				try
				{
					arrCode = decodeEasyQueryResult(strQueryResult);
				}
				catch(ex)
				{
					alert("ҳ��ȱ������EasyQueryVer3.js");
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
			cacheFlag = false;
			for (i=0; i<arrCode.length; i++)
			{
				if (showCodeObj.value == arrCode[i][0])
				{
					showNameObj.value = arrCode[i][1];
					break;
				}
			}
		}
		catch(ex)
		{}
	}
}

/**
* ���ݴ���ѡ��Ĵ�����Ҳ���ʾ���ƣ���ʾָ����һ��
* 2009-09-11 �����Ż������з�����ӷ���������ֱ�Ӵ������������ֵ����������ʱ��
* strCode - ����ѡ��Ĵ���
* showObjCode - �����ŵĽ����������
* showObjName - Ҫ��ʾ���ƵĽ����������
* strField    - �����ѯ�����ֶ�
* strCondition - �ֶζ�Ӧ����ֵ
*/
function showOneCodeNameEx(strCode, showObjCode, showObjName, strField, strCondition) {
	var formsNum = 0;	//�����е�FORM��
	var elementsNum = 0;	//FORM�е�Ԫ����
	var showObjn;
	var showObjc;
	if (showObjName == null) showObjName = strCode;
	//��������FORM
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)
	{
		//����FORM�е�����ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)

		{
			//Ѱ�Ҵ���ѡ��Ԫ��
			//alert(window.document.forms[formsNum].elements[elementsNum].name);
			if (window.document.forms[formsNum].elements[elementsNum].name == showObjCode)
			{
				showObjc = window.document.forms[formsNum].elements[elementsNum];

			}
			if (window.document.forms[formsNum].elements[elementsNum].name == showObjName)
			{
				showObjn = window.document.forms[formsNum].elements[elementsNum];
				break;
			}
		}

	}
	//�����µķ���
	showOneCodeNameOfObjEx(strCode,showObjc,showObjn,strField,strCondition);

}

/**
* ��������Ա�У�����֤�ŵĺ���
* �����������֤�����룬�������ڣ��Ա�
* ����  ����ֵ
*/
function chkIdNo(iIdNo, iBirthday ,iSex)
{
  var tmpStr="";
  var idDate="";
  var tmpInt=0;
  var strReturn = "";

  iIdNo = trim(iIdNo);
  iBirthday = trim(iBirthday);
  iSex = trim(iSex);

  if ((iIdNo.length!=15) && (iIdNo.length!=18))
  {
    strReturn = "��������֤��λ������";
    return strReturn;
  }

  if (!(isDate(iBirthday)))
  {
  	strReturn = "��������ڸ�ʽ����";
    return strReturn;
  }

  //ת�����ڸ�ʽ��yy��mm��dd��by Minim
  var arrDate = iBirthday.split("-");
  if (arrDate[1].length == 1) arrDate[1] = "0" + arrDate[1];
  if (arrDate[2].length == 1) arrDate[2] = "0" + arrDate[2];
  iBirthday = arrDate[0] + "-" + arrDate[1] + "-" + arrDate[2];

  if (iSex!="0" && iSex!="1")
  {
  	strReturn = "������Ա���ȷ";
    return strReturn;
  }

  if (iIdNo.length==15)
  {
    tmpStr=iIdNo.substring(6,12);
    tmpStr= "19" + tmpStr;
    tmpStr= tmpStr.substring(0,4) + "-" + tmpStr.substring(4,6) + "-" + tmpStr.substring(6)

    if ( iBirthday == tmpStr )
    {
      if (iSex=="0")
      {
      	tmpInt = parseInt(iIdNo.substring(14));
      	tmpInt = tmpInt % 2
      	if (tmpInt==0)
      	{
      	  strReturn = "������Ա������֤�ŵ���Ϣ��һ��";
          return strReturn;
      	}
      }
      else
      {
      	tmpInt = parseInt(iIdNo.substring(14));
      	tmpInt = tmpInt % 2
      	if (tmpInt!=0)
      	{
      	  strReturn = "������Ա������֤�ŵ���Ϣ��һ��";
          return strReturn;
      	}
      }
    }
    else
    {
      strReturn = "��������������֤�ŵ���Ϣ��һ��";
      return strReturn;
    }

    return strReturn;
  }

  if (iIdNo.length==18)
  {
  	tmpStr=iIdNo.substring(6,14);
  	tmpStr= tmpStr.substring(0,4) + "-" + tmpStr.substring(4,6) + "-" + tmpStr.substring(6)

    if ( iBirthday == tmpStr )
    {
      if (iSex=="0")
      {
      	tmpInt = parseInt(iIdNo.substring(16,17));
      	tmpInt = tmpInt % 2
      	if (tmpInt==0)
      	{
      	  strReturn = "������Ա������֤�ŵ���Ϣ��һ��";
          return strReturn;
      	}
      }
      else
      {
      	tmpInt = parseInt(iIdNo.substring(16,17));
      	tmpInt = tmpInt % 2
      	if (tmpInt!=0)
      	{
      	  strReturn = "������Ա������֤�ŵ���Ϣ��һ��";
          return strReturn;
      	}
      }
    }
    else
    {
      strReturn = "��������������֤�ŵ���Ϣ��һ��";
      return strReturn;
    }

    return strReturn;
  }
}


/**
 * �ϸ�У�����֤����
 * ���� 2005-7-2 17:05
 * ������ݺ�������������룬
 * ��ʮ��λ���ֱ������һλ����У������ɡ�
 * ����˳�������������Ϊ����λ���ֵ�ַ�룬��λ���ֳ��������룬
 * ��λ����˳�����һλ����У���롣˳����������ָ����ԣ�ż���ָ�Ů�ԡ�
 * У�����Ǹ���ǰ��ʮ��λ�����룬����ISO7064:1983.MOD11-2У�����������ļ����롣
 */
function checkIdCard(idCard)
{
	var SystemDate=new Date();
	var year=SystemDate.getFullYear();
	var month=SystemDate.getMonth()+1;
	var day=SystemDate.getDate();
	var yyyy; //��
	var mm; //��
	var dd; //��
	var birthday; //����
	var sex; //�Ա�
	var id=idCard;
	var id_length=id.length;
	if (id_length==0)
	{
		alert("���������֤����!");
		return false;
	}
	if (id_length!=15 && id_length!=18)
	{
		alert("���֤�ų���ӦΪ15λ��18λ��");
		return false;
	}
	if (id_length==15)
	{
		for(var i =0 ;i<id_length;i++)
		{
			if(isNaN(idCard.charAt(i)))
			{
				alert("15λ���֤���в������ַ���");
				return false;
			}
		}
		yyyy="19"+id.substring(6,8);
		mm=id.substring(8,10);
		dd=id.substring(10,12);
		if (mm>12 || mm<=0)
		{
			alert("���֤���·ݷǷ���");
			return false;
		}
		if (dd>31 || dd<=0)
		{
			alert("���֤�����ڷǷ���");
			return false;
		}
		//4,6,9,11�·����ڲ��ܳ���30
		if((mm==4||mm==6||mm==9||mm==11)&&(dd>30))
		{
			alert("���֤�����ڷǷ���");
			return false;
		}
		//�ж�2�·�
		if(mm==2)
		{
			if(LeapYear(yyyy))
			{
				if(dd>29)
				{
					alert("���֤�����ڷǷ���");
					return false;
				}
			}
			else
			{
				if(dd>28)
				{
					alert("���֤�����ڷǷ���");
					return false;
				}
			}
		}
	}
	else
	{
		for(var i =0 ;i<id_length-1;i++)
		{
			if(isNaN(idCard.charAt(i)))
			{
				alert("���֤����ǰ17λ�в������ַ���");
				return false;
			}
		}
		if(isNaN(idCard.charAt(17))&& idCard.charAt(17) !="X" && idCard.charAt(17) !="x" )
		{
			alert("���֤У������������飡");
			return false;
		}
		if (idCard.indexOf("X") > 0 && idCard.indexOf("X")!=17 || idCard.indexOf("x")>0 && idCard.indexOf("x")!=17)
		{
			alert("���֤��\"X\"����λ�ò���ȷ��");
			return false;
		}
		yyyy=id.substring(6,10);
		if (yyyy>year || yyyy<1900)
		{
			alert("���֤����ȷǷ���");
			return false;
		}
		mm=id.substring(10,12);
		if (mm>12 || mm<=0)
		{
			alert("���֤���·ݷǷ���");
			return false;
		}
		if(yyyy==year&&mm>month)
		{
			alert("���֤���·ݷǷ���");
			return false;
		}
		dd=id.substring(12,14);
		if (dd>31 || dd<=0)
		{
			alert("���֤�����ڷǷ���");
			return false;
		}
		//4,6,9,11�·����ڲ��ܳ���30
		if((mm==4||mm==6||mm==9||mm==11)&&(dd>30))
		{
			alert("���֤�����ڷǷ���");
			return false;
		}
		//�ж�2�·�
		if(mm==2)
		{
			if(LeapYear(yyyy))
			{
				if(dd>29)
				{
					alert("���֤�����ڷǷ���");
					return false;

				}
			}
			else

			{
				if(dd>28)
				{
					alert("���֤�����ڷǷ���");
					return false;
				}
			}
		}
		if(yyyy==year&&mm==month&&dd>day)
		{
			alert("���֤�����ڷǷ���");
			return false;
		}
		if (id.charAt(17)=="x" || id.charAt(17)=="X")
		{
			if ("x"!=GetVerifyBit(id) && "X"!=GetVerifyBit(id))
			{
				alert("���֤У������������飡");
				return false;
			}
		}
		else
		{
			if (id.charAt(17)!=GetVerifyBit(id))
			{
				alert("���֤У������������飡");
				return false;
			}
		}
	}
	return true;
}

//�����˺�У�����
//1���������б���BankCode��ѯ������Ϣ��ldbank���������ͱ�־BankType�����л���Comcode��
//2�����BankTypeΪ��GSYH������У�������˺�BankAccno�Ƿ���ڡ�*���ַ�������ڡ�*���ַ�����ϵͳ���������ʾ��������������˺��д���*�ַ������������롱��
//3�����BankTypeΪ��NYYH�� ��Comcode��ǰ��λΪ��8613������У�������˺�BankAccno��ͷ�ַ��Ƿ�Ϊ��50-���ַ�����Ϊ��50-������ϵͳ���������ʾ��������������˺Ų�����50-����ͷ�����������롱��
function checkBankAccNo(tBankCode, tBankAccNo)
{
	if(tBankAccNo!=null && tBankAccNo!=""){
		if(tBankCode==null || tBankCode==""){
			alert("���������б��룡");
			return false;
		}
	}

	//var strSql = "select a.BankType, a.comcode from ldbank a where a.bankcode='"+tBankCode+"'";
	var strSql = wrapSql(tResourceNameCommon,"querysqldes1",[tBankCode]);
	var arrResult = easyExecSql(strSql);
	
	if(arrResult!=null && arrResult[0][0]=="GSYH"){
		for(var i =0; i<tBankAccNo.length; i++){
			if(tBankAccNo.charAt(i)=="*"){
				alert("������������˺��д���[*]�ַ������������룡");
				return false;	
			}
		}
	}else if(arrResult!=null && arrResult[0][0]=="NYYH" && arrResult[0][1].length>=4 && arrResult[0][1].substring(0,4)=="8613"){
		if(tBankAccNo.substring(0,3)=="50-"){
			alert("������������˺Ų�����[50-]��ͷ�����������룡");
			return false;
		}
	}

	return true;
}

/**
 * �������֤У����
 * ���� 2005-7-2 17:06
 * ԭ��:
 * ��(a[i]*W[i]) mod 11 ( i = 2, 3, ..., 18 )(1)
 * "*" ��ʾ�˺�
 * i--------��ʾ���֤����ÿһλ����ţ��������������Ϊ18�����Ҳ�Ϊ1��
 * a[i]-----��ʾ���֤����� i λ�ϵĺ���
 * W[i]-----��ʾ�� i λ�ϵ�Ȩֵ W[i] = 2^(i-1) mod 11
 * ���㹫ʽ (1) ����Ϊ R
 * �����±��ҳ� R ��Ӧ��У���뼴ΪҪ�����֤�����У����C��
 * R 0 1 2 3 4 5 6 7 8 9 10
 * C 1 0 X 9 8 7 6 5 4 3 2
 * X ���� 10�����������е� 10 ���� X
 * 15λת18λ��,����У��λ�����һλ
 */
function GetVerifyBit(id)
{
	var result;
	var nNum=eval(id.charAt(0)*7+id.charAt(1)*9+id.charAt(2)*10+id.charAt(3)*5+id.charAt(4)*8+id.charAt(5)*4+id.charAt(6)*2+id.charAt(7)*1+id.charAt(8)*6+id.charAt(9)*3+id.charAt(10)*7+id.charAt(11)*9+id.charAt(12)*10+id.charAt(13)*5+id.charAt(14)*8+id.charAt(15)*4+id.charAt(16)*2);
	nNum=nNum%11;
	switch (nNum)
	{
		case 0 :
			result="1";
			break;
		case 1 :
			result="0";
			break;
		case 2 :
			result="X";
			break;
		case 3 :
			result="9";
			break;
		case 4 :
			result="8";
			break;
		case 5 :
			result="7";
			break;
		case 6 :
			result="6";
			break;
		case 7 :
			result="5";
			break;
		case 8 :
			result="4";
			break;
		case 9 :
			result="3";
			break;
		case 10 :
			result="2";
			break;
	}
	return result;
}

/**
 * �ж��Ƿ�����
 * ����intYear������ݵ�ֵ
 * returntrue:������false:��������
 */
function LeapYear(intYear)
{
	if(intYear%100==0)
	{
		if(intYear%400==0)
		{
			return true;
		}
	}
	else
	{
		if((intYear%4)==0)
		{
			return true;
		}
	}
	return false;
}

/**
* ͨ�����֤�ŵĵõ��������ں���
* �����������֤������
* ����  ��������
*/
function getBirthdatByIdNo(iIdNo)
{
  var tmpStr="";
  var idDate="";
  var tmpInt=0;
  var strReturn = "";

  iIdNo = trim(iIdNo);

  if ((iIdNo.length!=15) && (iIdNo.length!=18))
  {
    strReturn = "��������֤��λ������";
    return strReturn;
  }

  if (iIdNo.length==15)
  {
    tmpStr=iIdNo.substring(6,12);
    tmpStr= "19" + tmpStr;
    tmpStr= tmpStr.substring(0,4) + "-" + tmpStr.substring(4,6) + "-" + tmpStr.substring(6)

    return tmpStr;
  }
  else// if (iIdNo.length==18)
  {
  	tmpStr=iIdNo.substring(6,14);
  	tmpStr= tmpStr.substring(0,4) + "-" + tmpStr.substring(4,6) + "-" + tmpStr.substring(6)

    return tmpStr;
  }
}


/**
 * ����Ͷ�������䣬�ж��Ƿ��쳣
 * ��������true���쳣����false
 */
function CheckAge(birthday)
{
	var i = calAge(birthday);
	if (i>150 ||i<0)
	{
		return false;
	}
	else
	{
		return true;
	}

}


/**
 * �ó������ڼ������䣬Ŀǰ��֧��yyyymmddģʽ
 * ��������������yy��mm��dd
 * ����  ����
 */
function calAge(birthday)
{
	var arrBirthday = birthday.split("-");
	if (arrBirthday.length == 1)
	{
		if(arrBirthday[0].length != 8)
		{
			return "";
		}
		var arrBirthdays = new Array();
		arrBirthdays[0] = arrBirthday[0].substring(0, 4);
		arrBirthdays[1] = arrBirthday[0].substring(4, 6);
		arrBirthdays[2] = arrBirthday[0].substring(6, 8);
		var today = new Date();
		var arrToday = new Array();
		arrToday[0] = today.getFullYear();
		arrToday[1] = today.getMonth() + 1;
		arrToday[2] = today.getDate();
		var age = arrToday[0] - arrBirthdays[0] - 1;
		//��ǰ�´��ڳ�����
		if(arrToday[1] > arrBirthdays[1])
		{
			age = age + 1;
			return age;
		}
		else if(arrToday[1] < arrBirthdays[1])
		{
			//��ǰ��С�ڳ�����
			return age;
		}
		else if(arrToday[2] >= arrBirthdays[2])
		{
			//��ǰ�µ��ڳ����µ�ʱ�򣬿�������
			age = age + 1;
			return age;
		}
		else
		{
			return age;
		}
	}
	else
	{
		if(arrBirthday[1].length == 1)
		{
			arrBirthday[1] = "0" + arrBirthday[1];
		}
		if(arrBirthday[2].length == 1)
		{
			arrBirthday[2] = "0" + arrBirthday[2];
		}
		var today = new Date();
		var arrToday = new Array();
		arrToday[0] = today.getFullYear();
		arrToday[1] = today.getMonth() + 1;
		arrToday[2] = today.getDate();
		var age = arrToday[0] - arrBirthday[0] - 1;
		//��ǰ�´��ڳ�����
		if(arrToday[1] > arrBirthday[1])
		{
			age = age + 1;
			return age;
		}
		else if(arrToday[1] < arrBirthday[1])
		{
			//��ǰ��С�ڳ�����
			return age;
		}
		else if(arrToday[2] >= arrBirthday[2])
		{
			//��ǰ�µ��ڳ����µ�ʱ�򣬿�������
			age = age + 1;
			return age;
		}
		else
		{
			return age;
		}
	}
}


/**
* 18λ���֤ת15λ
* ���������֤��
* ���أ�15λ��
*/
function transIdNo(iIdNo)
{
	var temStr="";
	var temStr1="";
	var temStr2="";

  iIdNo = trim(iIdNo);
	temStr = iIdNo;

  if ((iIdNo.length!=15) && (iIdNo.length!=18))
  {
    strReturn = "��������֤��λ������";
    return strReturn;
  }
	if (iIdNo.length==18)

	{
		temStr1 = iIdNo.substring(0,6);
		temStr2 = iIdNo.substring(8,17);
		temStr = temStr1.concat(temStr2);
	}

	return temStr;
}


/**
 * �ó������ںͱ����������ڼ�������
 * ��������������yy��mm��dd��yyyymmdd
 * ����  ����
 */
function calAgeNew(birthday,applyday)
{
	var arrBirthday = birthday.split("-");
	var arrApplyday = applyday.split("-");
	if (arrBirthday.length == 1&&arrApplyday.length == 1)
	{
		if(arrBirthday[0].length != 8||arrApplyday[0].length!= 8)
		{
			return "";
		}
		var arrBirthdays = new Array();
		arrBirthdays[0] = arrBirthday[0].substring(0, 4);
		arrBirthdays[1] = arrBirthday[0].substring(4, 6);
		arrBirthdays[2] = arrBirthday[0].substring(6, 8);
		var arrToday = new Array();
		arrToday[0] = arrApplyday[0].substring(0, 4);
		arrToday[1] = arrApplyday[0].substring(4, 6);
		arrToday[2] = arrApplyday[0].substring(6, 8);
		var age = arrToday[0] - arrBirthdays[0] - 1;
		//��ǰ�´��ڳ�����
		if(arrToday[1] > arrBirthdays[1])
		{
			age = age + 1;
			return age;
		}
		else if(arrToday[1] < arrBirthdays[1])
		{
			//��ǰ��С�ڳ�����
			return age;
		}
		else if(arrToday[2] >= arrBirthdays[2])
		{
			//��ǰ�µ��ڳ����µ�ʱ�򣬿�������
			age = age + 1;
			return age;
		}
		else
		{
			return age;
		}
	}
	else if(arrBirthday.length == 1&&arrApplyday.length != 1)
	{
		if(arrBirthday[0].length != 8)
		{
			return "";
		}
		var arrBirthdays = new Array();
		arrBirthdays[0] = arrBirthday[0].substring(0, 4);
		arrBirthdays[1] = arrBirthday[0].substring(4, 6);
		arrBirthdays[2] = arrBirthday[0].substring(6, 8);
		if(arrApplyday[1].length == 1)
		{
			arrApplyday[1] = "0" + arrApplyday[1];
		}
		if(arrApplyday[2].length == 1)
		{
			arrApplyday[2] = "0" + arrApplyday[2];
		}
		var arrToday = new Array();
		arrToday[0] = arrApplyday[0];
		arrToday[1] = arrApplyday[1];
		arrToday[2] = arrApplyday[2];
		var age = arrToday[0] - arrBirthdays[0] - 1;
		//��ǰ�´��ڳ�����
		if(arrToday[1] > arrBirthdays[1])
		{
			age = age + 1;
			return age;
		}
		else if(arrToday[1] < arrBirthdays[1])
		{
			//��ǰ��С�ڳ�����
			return age;
		}
		else if(arrToday[2] >= arrBirthdays[2])
		{
			//��ǰ�µ��ڳ����µ�ʱ�򣬿�������
			age = age + 1;
			return age;
		}
		else
		{
			return age;
		}
	}
	else if(arrBirthday.length != 1&&arrApplyday.length == 1)
	{
		if(arrApplyday[0].length != 8)
		{
			return "";
		}
		if(arrBirthday[1].length == 1)
		{
			arrBirthday[1] = "0" + arrBirthday[1];
		}
		if(arrBirthday[2].length == 1)
		{
			arrBirthday[2] = "0" + arrBirthday[2];
		}
		var arrToday = new Array();
		arrToday[0] = arrApplyday[0].substring(0, 4);
		arrToday[1] = arrApplyday[0].substring(4, 6);
		arrToday[2] = arrApplyday[0].substring(6, 8);
		var age = arrToday[0] - arrBirthday[0] - 1;
		//��ǰ�´��ڳ�����
		if(arrToday[1] > arrBirthday[1])
		{
			age = age + 1;
			return age;
		}
		else if(arrToday[1] < arrBirthday[1])
		{
			//��ǰ��С�ڳ�����
			return age;
		}
		else if(arrToday[2] >= arrBirthday[2])
		{
			//��ǰ�µ��ڳ����µ�ʱ�򣬿�������
			age = age + 1;
			return age;
		}
		else
		{
			return age;
		}
	}
	else if(arrBirthday.length != 1&&arrApplyday.length != 1)
	{
		if(arrBirthday[1].length == 1)
		{
			arrBirthday[1] = "0" + arrBirthday[1];
		}
		if(arrBirthday[2].length == 1)
		{
			arrBirthday[2] = "0" + arrBirthday[2];
		}
		if(arrApplyday[1].length == 1)
		{
			arrApplyday[1] = "0" + arrApplyday[1];
		}
		if(arrApplyday[2].length == 1)
		{
			arrApplyday[2] = "0" + arrApplyday[2];
		}
		var arrToday = new Array();
		arrToday[0] = arrApplyday[0];
		arrToday[1] = arrApplyday[1];
		arrToday[2] = arrApplyday[2];
		var age = arrToday[0] - arrBirthday[0] - 1;
		//��ǰ�´��ڳ�����
		if(arrToday[1] > arrBirthday[1])
		{
			age = age + 1;
			return age;
		}
		else if(arrToday[1] < arrBirthday[1])
		{
			//��ǰ��С�ڳ�����
			return age;
		}
		else if(arrToday[2] >= arrBirthday[2])
		{
			//��ǰ�µ��ڳ����µ�ʱ�򣬿�������
			age = age + 1;
			return age;
		}
		else
		{
			return age;
		}
	}
}


/**
* ��Ѱ�����ڣ�����CodeSelect��������
* ����
* ����
*/
function searchMainWindow(win) {
	if (typeof(win) != "object")
	{
		return false;
	}
	if (win.top.name == "Lis")
	{
		return win.top;
	}
	return searchMainWindow(win.top.opener);
}


//У���ݽ��Ѻŷ���
function verifyTempfeeNo(tempfeeNo) {

	//ȥϵͳ��LDSysVar�в�ѯSysvar�ֶ���ΪcheckNewType�ļ�¼���ж��Ƿ���Ҫȥ��ѯ��֤״̬��
	//var tSql = "select Sysvarvalue from LDSysVar where Sysvar='CheckNewType'";
	var tSql = wrapSql(tResourceNameCommon,"querysqldes2",["1"]);
	var tResult = easyExecSql(tSql, 1, 0, 1);
	//Ϊ��У�飬����ldsysvar����Ϊ3
	if(tResult=="1" || tResult=="3") {

		//����鵽�ü�¼��������Ҫ��ѯ��֤״̬��
		//ȥ��֤״̬�����ѯ�ú����Ƿ���Ч,�ݽ����վݺ�
		//var strSql = "select CertifyCode from LZCardTrack where Startno<='"+tempfeeNo+"' and Endno>='"+tempfeeNo+"' and Receivecom = 'D"+document.all('AgentCode').value+"' and StateFlag='0' and CertifyCode in (select CertifyCode from LMCertifyDes where CertifyClass2 = '0')";
		var strSql = wrapSql(tResourceNameCommon,"querysqldes3",[tempfeeNo,tempfeeNo,document.all('AgentCode').value]);
		var strResult=easyQueryVer3(strSql, 1, 0, 1);
		if(!strResult) {

			alert("�õ�֤����֤����Ϊ��"+tempfeeNo+" ��û�з��Ÿ��ô����ˣ�"+document.all('AgentCode').value+"��!");
			return false;
		}
	}
	return true;
}

//У�����վݣ�
//1����֤��Ҫ����
//2����֤���ڿ���״̬
//3����֤�Ѿ��������ô����˻��������ڻ�����
function verifyTempfeeNoNew(CertifyCode,TempfeeNo,AgentCode) {
	//1����֤��Ҫ����
	//var strSql = "select a.tackbackflag from lmcertifydes a where a.certifycode='"+CertifyCode+"'";
	var strSql = wrapSql(tResourceNameCommon,"querysqldes4",[CertifyCode]);
	var arrResult = easyExecSql(strSql);
	if(arrResult!=null && arrResult[0][0]!="Y"){
		alert("�õ�֤��"+CertifyCode+"�����ú�����");
		return false;	
	}
	
	/*var strSql2 = "select a.stateflag,a.receivecom from lzcard a where a.certifycode='" + CertifyCode 
				+ "' and a.startno='" + TempfeeNo + "'";*/
	var strSql2 = wrapSql(tResourceNameCommon,"querysqldes5",[CertifyCode,TempfeeNo]);
	var arrResult2 = easyExecSql(strSql2);
	if(arrResult2==null || arrResult2=="null" || arrResult2==""){
	  	alert("δ��ѯ����֤���롾"+CertifyCode+"����֤���롾"+TempfeeNo+"���ļ�¼������¼�����ݣ�");
		return false;	
	}else{
		//2����֤���ڿ���״̬
		if(arrResult2[0][0]!=null && arrResult2[0][0]!="3"){
			alert("��֤���롾"+CertifyCode+"����֤���롾"+TempfeeNo+"�����Ǵ����ѷ���δ����״̬�����ܺ�����");
			return false;	
		}
		//3����֤�Ѿ��������ô����˻��������ڻ�����	
		if(arrResult2[0][1]!=null && arrResult2[0][1].substring(0,1)=="D" && arrResult2[0][1].substring(1)!=AgentCode){
			alert("��֤���롾"+CertifyCode+"����֤���롾"+TempfeeNo+"��δ�����������ˡ�"+AgentCode+"�������ܺ�����");
			return false;	
		}
		if(arrResult2[0][1]!=null && (arrResult2[0][1].substring(0,1)=="A")){//������8λ����
			//var strSql3 = "select 1 from laagent a where a.agentcode='"+AgentCode+"' and a.managecom like '"+arrResult2[0][1].substring(1)+"%'";
			var strSql3 = wrapSql(tResourceNameCommon,"querysqldes6",[AgentCode,arrResult2[0][1].substring(1)]);
			var arrResult3 = easyExecSql(strSql3);		
			if(arrResult3==null || arrResult3=="null" || arrResult3==""){
				alert("��֤���롾"+CertifyCode+"����֤���롾"+TempfeeNo+"��δ�����������ˡ�"+AgentCode+"�����ڻ��������ܺ�����");
				return false;	
			}	
		}
		if(arrResult2[0][1]!=null && (arrResult2[0][1].substring(0,1)=="B")){//����������
			//var strSql3 = "select 1 from laagent a where a.agentcode='"+AgentCode+"' and a.managecom like '"+arrResult2[0][1].substring(1,arrResult2[0][1].length-2)+"%'";
			var strSql3 = wrapSql(tResourceNameCommon,"querysqldes6",[AgentCode,arrResult2[0][1].substring(1,arrResult2[0][1].length-2)]);
			var arrResult3 = easyExecSql(strSql3);		
			if(arrResult3==null || arrResult3=="null" || arrResult3==""){
				alert("��֤���롾"+CertifyCode+"����֤���롾"+TempfeeNo+"��δ�����������ˡ�"+AgentCode+"�����ڲ��ţ����ܺ�����");
				return false;	
			}	
		}			
	}

	return true;
}

//У��ӡˢ�ŷ���
function verifyPrtNo(prtNo) {

	//ȥϵͳ��LDSysVar�в�ѯSysvar�ֶ���ΪcheckNewType�ļ�¼���ж��Ƿ���Ҫȥ��ѯ��֤״̬��
	//var tSql = "select Sysvarvalue from LDSysVar where Sysvar='CheckNewType'";
	var tSql = wrapSql(tResourceNameCommon,"querysqldes2",[""]);
	var tResult = easyExecSql(tSql, 1, 0, 1);
	if(tResult=="2" || tResult=="3") {

		//����鵽�ü�¼��������Ҫ��ѯ��֤״̬��
		//ȥ��֤״̬�����ѯ�ú����Ƿ���Ч,Ͷ����ӡˢ����
		//var strSql = "select CertifyCode from LZCardTrack where Startno<='"+prtNo+"' and Endno>='"+prtNo+"' and Receivecom = 'D"+document.all('AgentCode').value+"' and StateFlag='0'";
		var strSql = wrapSql(tResourceNameCommon,"querysqldes7",[prtNo,prtNo,document.all('AgentCode').value]);
		var strResult=easyQueryVer3(strSql, 1, 0, 1);
		if(!strResult) {

			alert("�õ�֤����֤����Ϊ��"+prtNo+" ��û�з��Ÿ��ô����ˣ�"+document.all('AgentCode').value+"��!");
			return false;
		}
	}
	return true;
}

//У��¼��Ĺ�������Ƿ��ǲ���Ա��½����ͬ�����¼���Ϊ���򷵻�false
function checkManageComPre(InputManageCom,UserManageCom)

{
	if(InputManageCom == null || InputManageCom == '' || UserManageCom == null || UserManageCom == '')
	{
		return false;
	}
	//var sqlcheck = "select nvl((select 'Y' from ldsysvar where sysvar = 'onerow' and trim('"+InputManageCom+"') like trim('"+UserManageCom+"')||'%%' and exists (select '1' from ldcom where comcode = '"+InputManageCom+"')),'N') from ldsysvar where sysvar = 'onerow'";
	var sqlcheck = wrapSql(tResourceNameCommon,"querysqldes8",[InputManageCom,UserManageCom,InputManageCom]);
	var result = easyExecSql(sqlcheck);
  var re = result;
  if(re =="Y")
  return true;
  else
  return false;
}



/**
* ��ʾԪ�ص�Title��Ϣ�������Ϣ�϶��޷�ֱ���ڽ���Ԫ���л��������Ϣ�����⣩
*/
function showTitle() {
  var formsNum = 0;	//�����е�FORM��
  var elementsNum = 0;	//FORM�е�Ԫ����
  var strEvent = "";	//����onDoubleClick�¼�����
  var urlStr = "";
  var sFeatures = "";
  var strCode = "";	//����ѡ��
  var strQueryResult = "";	//����ѡ��Ĳ�ѯ�����
  var arrCode = null;	//�������
  var strCodeValue = "";	//����ֵ
  var cacheFlag = false;	//�ڴ��������ݱ�־

  var strCodeSelect = "";

  //Ѱ��������
  var win = searchMainWindow(this);
  if (win == false) { win = this; }

  //��������FORM
  for (formsNum=0; formsNum<window.document.forms.length; formsNum++) {
    //����FORM�е�����ELEMENT
    for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++) {
      //Ѱ�Ҵ���ѡ��Ԫ��
      if (window.document.forms[formsNum].elements[elementsNum].className == "code") {
       //ȡ������ֵ
        strCodeValue = window.document.forms[formsNum].elements[elementsNum].value;

        //��ֵ�򲻴���
        if (strCodeValue == "") continue;
      }

      //��ʾtitle
      if (window.document.forms[formsNum].elements[elementsNum].type == "text") {
         window.document.forms[formsNum].elements[elementsNum].title = window.document.forms[formsNum].elements[elementsNum].value;
      }

    }
  }
}

/**
*���ھ۽�����
*������show��Ҫ��ʾ�Ĵ���
*/
function myonfocus(show){

	if(show!=null)
	{
		try
		{
			show.focus();
		}
		catch(ex)
		{
			show=null;
		}
	}
}


//��ʼ���ؼ�����
function initElementtype(){
	var tFormCount = document.forms.length;
	for(var fm=0;fm<tFormCount;fm++)
	{
		var theElements=document.forms[fm].elements;
		for(var i=0;i<theElements.length;i++)
		{
			var ele = theElements[i];
			if(ele.attributes.elementtype && ele.attributes.elementtype.nodeValue.indexOf("nacessary")!=-1)
			{
				if(ele.nextSibling && ele.nextSibling.tagName && ele.nextSibling.tagName.toLowerCase() != 'font'){//��ֹ��д�죪
					ele.insertAdjacentHTML("afterEnd","<font id='"+ele.name+"n' color=red>&nbsp;*</font>");
				}
			}
			if(ele.attributes.elementtype && ele.attributes.elementtype.nodeValue.indexOf("misty")!=-1)
			{
				if(ele.nextSibling && ele.nextSibling.tagName && ele.nextSibling.tagName.toLowerCase() != 'font'){//��ֹ��д�죪
					ele.insertAdjacentHTML("afterEnd","<font color=red>&nbsp;?</font>");
				}
			}
			if(ele.readOnly)
			{
				ele.tabIndex=-1;
			}
		}
	}
}


//��ʾtextarea�е�����
function showTextareaDiv(parm1,parm2){
	var ex,ey;
	ex = window.event.clientX+document.body.scrollLeft;  //�õ��¼�������x
	ey = window.event.clientY+document.body.scrollTop;   //�õ��¼�������y
	var str=document.all( parm1 ).all(parm2).value;
	Gridobj=document.all(parm1 ).all(parm2);
	divDownList.innerHTML='<table id="tabList" style="width:100%" border="0" cellpadding="0" cellspacing="0">'
	+'<tr><td align="right"><textarea class=common2 rows=4 id=textareavalue name=textareavalue value="" style="overflow:auto;width:100%"></textarea></td></tr>'
	+'<tr><td align="right"><input type="button" value="ȷ��" onclick="insertvalue()"></td></tr></table>';
	textareavalue.value=str;
	divDownList.style.left=ex;
	divDownList.style.top =ey;
	divDownList.style.display="";
}

//������textarea����������ؼ�
function insertvalue(){
	Gridobj.value=textareavalue.value;
	divDownList.style.display="none";
}

//����ַ������Ƿ�������  ����--true û��--false
function chkzh(tchar){
	if (tchar =="")
	{
		return false;
	}
	var pattern = /^([\u4E00-\u9FA5]|[\uFE30-\uFFA0])*$/gi;
	if (pattern.test(tchar))
	{
		return true;
	}
	else
	{
		return false;
	}
}

//�������֤��ȡ���Ա� update 2004-12-09 wzw
function getSexByIDNo(iIdNo){

	var tSex="";
	var strReturn="";
	if ((iIdNo.length!=15) && (iIdNo.length!=18))
	{
		strReturn = "��������֤��λ������";
		return strReturn;
	}
	var tmpInt=0;
	if(iIdNo.length==15)
	{
		tmpInt = parseInt(iIdNo.substring(14));
	}
	if(iIdNo.length==18)
	{
		tmpInt = parseInt(iIdNo.substring(16,17));
	}
	tmpInt = tmpInt % 2;
	if (tmpInt==0)
	{
		tSex="1";
	}
	else
	{
		tSex="0";
	}
	return tSex;
}


/**
 * �������ÿ����´���
 */
function OpenWindowNew(strurl,windowname,opentype,width,height)
{
	if(opentype=="left")
	{
		window.open(strurl,windowname,'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
	if(width=='undefined' || width==null )
	{
		width=800
	}
	if(height=='undefined'||  height==null)
	{
		height=500
	}
	if(opentype=="middle")
	{
		var iWidth=width; //ģ̬���ڿ��
		var iHeight=height;//ģ̬���ڸ߶�
		var iTop=(window.screen.height-iHeight)/2;
		var iLeft=(window.screen.width-iWidth)/2;
		window.open(strurl,windowname,'width='+iWidth+',height='+iHeight+',top='+iTop+',left='+iLeft+',toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	}
}

/*
*����Ĵ����Ǹ���ҳ��title��ʾ��ʾ��ʽ�Ĵ���
*Ĭ�ϵ���ʾtitleʱ��̫�̲�ˬ
*update 2004-12-09 wzw
*/
ToolTipGlobal={
	id:0,
	getId:function(o){ this.all[this.all.length]=o;return this.id++},
	all:[]
};

function blurTwice(mySpan,myLength)
{
  if (myLength=="" || myLength==0)
    return
  else if (old_bm_app!=mySpan.value)
  {
    alert("����λ��������������벻һ�£�����������!")
    mySpan.focus()
  }
}

function focusTwice(mySpan,myLength)
{
  old_bm_app=""
  mySpan.value=""
}

function inputTwice(mySpan,myLength)
{
  var tLength;
  if (myLength=="" || myLength==0)
    return
  else
  {
    tLength=mySpan.value.length;

    if (tLength==myLength)
    {
      if (old_bm_app=="")
      {
        old_bm_app=mySpan.value
        mySpan.value="";
      }
    }
  }
}


/**
 * �������ڸ�ʽΪyyyymmdd
 * ������ڸ�ʽΪyyyy-mm-dd
 */
function modifydate(strDate)
{
	var stadate;
	if ( strDate!='')
	{
		if (!isDate(strDate))
		{
			var year=strDate.substring(0,4);
			var month=strDate.substring(4,6);
			var day=strDate.substring(6);
			stadate=year+'-'+month+'-'+day;
		}
		else
		{
			stadate=strDate;
		}
		return stadate;
	}
}

//ȡ�õ绰����
function getfullphone(phoneno)
{
	var arrphone = new Array();
	arrphone[0]="86";
	arrphone[1]="";
	arrphone[2]="";
	arrphone[3]="";
	if(phoneno!="")
	{
		arrphone[1]=phoneno.substring(0,phoneno.indexOf("-"));
		if(phoneno.indexOf("-")==phoneno.lastIndexOf("-"))
		{
			arrphone[2]=phoneno.substring(phoneno.indexOf("-")+1,phoneno.length);
			arrphone[3]="";
		}
		else
		{
			arrphone[2]=phoneno.substring(phoneno.indexOf("-")+1,phoneno.lastIndexOf("-"));
			arrphone[3]=phoneno.substring(phoneno.lastIndexOf("-")+1,phoneno.length);
		}
	}
	return arrphone;

}


/*
 * ����Ĵ����Ǹ���ҳ��title��ʾ��ʾ��ʽ�Ĵ���
 * Ĭ�ϵ���ʾtitleʱ��̫�̲�ˬ
 * update 2004-12-09 wzw
 */
ToolTipGlobal={id:0,getId:function(o){this.all[this.all.length]=o;return this.id++},all:[]};
function ToolTip(defaultOpacity,font,BGround,color,border,offsetOn,offsetOff)
{
	this.id = ToolTipGlobal.getId(this);
	this.defaultOpacity = defaultOpacity;
	this.opacity = defaultOpacity;
	this.font = font;
	this.BGround = BGround;	//title��ʾ�ı�����ɫ
	this.border = border;
	this.timerOn = null;
	this.timerOff = null;
	this.offsetOn = offsetOn;
	this.offsetOff = offsetOff;
	this.control = null;
	var o = this;
	try{
	window.addEventListener("onload",function(){ o.setup();});
}catch(e){};
}

ToolTip.prototype.fadeOn = function()
{
	window.clearTimeout(this.timerOff);
	this.timerOn = window.setTimeout("ToolTipGlobal.all["+this.id+"].fade(1)",this.offsetOn[1]);
}

ToolTip.prototype.fadeOff = function()
{
	window.clearTimeout(this.timerOn);
	this.timerOff = window.setTimeout("ToolTipGlobal.all["+this.id+"].fade(0)",this.offsetOff[1]);
}

ToolTip.prototype.setOpacity = function(x)
{
	this.opacity = x;
	this.control.style.filter = "progid:DXImageTransform.Microsoft.Alpha(opacity="+ x +") progid:DXImageTransform.Microsoft.Shadow(color='#FF444444', Direction=135, Strength=3)";
}

ToolTip.prototype.fade = function(x)
{
	var o = this.control;
	var ox = this.opacity;
	if(x)
	{
		if(ox + this.offsetOn[0] <100)
		{
			this.setOpacity(ox+this.offsetOn[0]);
			this.fadeOn();
		}
		else
		{
			this.setOpacity(100);
		}
	}
	else
	{
		if(ox - this.offsetOff[0]>this.defaultOpacity)
		{
			this.setOpacity(ox - this.offsetOn[0]);
			this.fadeOff();
		}
		else
		{
			this.setOpacity(this.defaultOpacity);
			o.style.visibility = "hidden"
		}
	}
}

ToolTip.prototype.setup = function()
{
	var o = document.createElement("div");
	var oThis = this;
	with(o.style)
	{
		position = "absolute";
		top = "0px";
		left = "0px";
		font = this.font;
		zIndex = 99999;
		background = this.BGround;
		color = this.color;
		border = this.border;
		padding = "2px 4px";
		visibility = "hidden";
	}
	document.body.appendChild(o);
	this.control = o;
	document.addEventListener("onmouseover", function()
	{
	    var e = window.event.srcElement;
        //XinYQ modified on 2006-10-18
        //�� srcElement Ϊ null ���� disabled ʱ����ʾ
        if (e != null && !e.disabled)
        {
	    	if(e.title != "")
	    	{
	    		e.tip = e.title;
	    		e.title = "";
	    	}
	    	if(typeof(e.tip) != "undefined" && e.tip != null)
	    	{
	    		o.innerHTML = e.tip;
	    		oThis.setOpacity(oThis.defaultOpacity);
	    		var x,y,docheight,docwidth,dh,dw;
	    		x = window.event.clientX + document.body.scrollLeft;	//�����¼����ں�����
	    		//document.body.scrollLeft����������������
	    		y = window.event.clientY + document.body.scrollTop;	//�����¼�����������
	    		docheight = document.body.clientHeight;	//�����¼�����������߶�
	    		docwidth  = document.body.clientWidth;	//�����¼�������������
	    		dh =(o.offsetHeight + y) - docheight;
	    		dw =(o.offsetWidth + x)  - docwidth;
//	    		o.offsetWidth = e.offsetWidth;
	    		if(dw > 0)
	    		{
//	    			o.style.left =(x - o.offsetWidth) + document.body.scrollLeft - 5 ;
	    			o.style.left = x;
	    		}
	    		else
	    		{
//	    			o.style.left = x + document.body.scrollLeft + 10;
	    			o.style.left = x;
	    		}
	    		if(dh > 0)
	    		{
	    			o.style.top = y  - 5 ;
	    		}
	    		else
	    		{
	    			o.style.top  = y  + 10 ;
	    		}
//	    		o.style.width = e.offsetWidth;
	    		o.style.visibility = "visible";
	    		oThis.fadeOn();
	    	}
	    }
	});
	//����ƿ�Ŀ���ʱ�򴥷��¼�
	document.addEventListener("onmouseout", function()
	{
	    var e = window.event.srcElement;
	    if (e != null && !e.disabled)
	    {
		    if (typeof(e.tip) != "undefined" && e.tip != null)
		    {
			    oThis.fadeOff();
		    }
	    }
    });
};


var tooltip =new ToolTip(20,"9pt Arial ����","#ffffdd","#000000","1px solid #000000",[8,20],[8,20]);
//ƴ�����ڸ�ʽΪ 2005-04-26��
function concatDate(year,month,day)
{
  if(month<10)  	month='0'+month;
  if(day<10)      day='0'+day;
  var fullDate = year+'-'+month+'-'+day;
  return fullDate;
}
function blurTwice(mySpan,myLength)
{
  if (myLength=="" || myLength==0)
    return
  else if (old_bm_app!=mySpan.value)
  {
    alert("����λ��������������벻һ�£�����������!")
    mySpan.focus()
  }
}

function focusTwice(mySpan,myLength)
{
  old_bm_app=""
  mySpan.value=""
}

function inputTwice(mySpan,myLength)
{
  var tLength;
  if (myLength=="" || myLength==0)
    return
  else
  {
    tLength=mySpan.value.length;

    if (tLength==myLength)
    {
      if (old_bm_app=="")
      {
        old_bm_app=mySpan.value
        mySpan.value="";
      }
    }
  }
}

//����¼��У�飬¼���ûس�ȷ��
function confirmSecondInput(aObject,aEvent){

        if(aEvent=="onkeyup"){

          var theKey = window.event.keyCode;

          if(theKey=="13"){

            if(theFirstValue!=""){

        theSecondValue = aObject.value;

              if(theSecondValue==""){

                alert("���ٴ�¼�룡");

                aObject.value="";

                aObject.focus();

                return;

              }

              if(theSecondValue==theFirstValue){

                aObject.value = theSecondValue;

                return;

              }

              else{

                alert("����¼����������������¼�룡");

                theFirstValue="";

                theSecondValue="";

                aObject.value="";

                aObject.focus();

                return;

              }

            }

      else{

        theFirstValue = aObject.value;

        if(theFirstValue==""){

                theSecondValue="";

          alert("��¼�����ݣ�");

          return;

        }

        aObject.value="";

        aObject.focus();

        return;

      }

    }

  }

  else if(aEvent=="onblur"){


            if(theFirstValue!=""){

              theSecondValue = aObject.value;

              theSecondObject = aObject;

              if((theSecondValue=="")&&(theFirstObject==theSecondObject)){

                alert("���ٴ�¼�룡");

                aObject.value="";

                aObject.focus();

                return;

              }

              if((theSecondValue==theFirstValue)&&(theFirstObject==theSecondObject)){

                aObject.value = theSecondValue;

                theSecondValue="";

                theFirstValue="";

                theFirstObject=null;

                theSecondObject=null;

                return;

              }

              else if ((theSecondValue !=theFirstValue)&&(theFirstObject==theSecondObject)){

									  alert("����¼����������������¼�룡");

                		theFirstValue="";
                		
                		theSecondValue="";
                		
                		aObject.value="";
                		
                		aObject.focus();
                		
                		return;

              }  else{
                		theSecondObject=null;
                		theSecondValue="";
                		
                		theFirstObject.value="";
                		
                		theFirstObject.focus();
                		
                		return;

              }

            }

      else{
					
          theFirstValue = aObject.value;

          theFirstObject = aObject;

          theSecondValue="";

          if(theFirstValue==""){

            //alert("aa");

            theFirstObject = null;
            theSecondObject = null;

            return;

          }

          aObject.value="";

          aObject.focus();

        return;

      }

     }
}


	/**
	 * �õ�tDate��tYear��һ��Ķ�Ӧ��
	 * 
	 * @param tYear
	 *            �������
	 * @param tDate
	 *            ����
	 * @return  tDate��tYear��һ��Ķ�Ӧ��
	   PST on 2008-09-18
	 */
	function calDate( tYear,  tDate) {
		var coDate = "";
		if (tDate != null && tDate.trim()!="") {
			var tMonth =tDate.substring(5,7) ;
			var tDay =tDate.substring(8,10) ; 
			// ���tDate��2��29�գ�������һ�겻������
			if (tMonth == '02' && tDay == '29' && !isLeap(tYear)) {
				tMonth = '03';
				tDay = '01';
			}
			coDate = tYear + "-" + tMonth + "-"+ tDay;
		}

		return coDate;
	}

	/**
	 * ����У��
	 * 
	 * @param mYear
	 *            ���
	 	   PST on 2008-09-18
	 * @return boolean ����:true ƽ�꣺false
	 */
function isLeap(tYear) {
		var returnFlag = (tYear % 4) == 0 ? ((tYear % 100) == 0 ? ((tYear % 400) == 0 ? true
				: false)
				: true)
				: false;
		return returnFlag;
	}
	
		/**
	 * ��ʽ������
	 * 
	 * @param mDate 2008-2-12
	 *            
	 * @return mDate 2008-02-12
	 */
function formartDate(tDate) {
		 var iArray = new Array();
		 iArray=tDate.split('-');
		 var tFomartDate=iArray[0];
		 for(var i=1;i<iArray.length;i++)
		 {
		 	 var tTemp=iArray[i];
		 	 
		 	 if(tTemp.length<2)
		 	 {
		 	 	tTemp='0'+tTemp;
		 	 	}
		 	 	tFomartDate+="-"+tTemp;
		 	}
		 return tFomartDate;
	}
	/**
	 * �õ�tDate��tYear��һ��Ķ�Ӧ��
	 * 
	 * @param tUnit  ���ڱ䶯��λ 'Y'-�� 'M'-�� 'D'-��
	 * @param tBaseDate  ���� �����ʽ'YYYY-MM-DD'
	   @param tIntv   �䶯��� �����ɸ� 
	            
	 * @return  tDate ����ָ��ʱ�������
	   PST on 2008-09-18
	 */	
function calSpecDate(tBaseDate,tUnit,tIntv)
{
var dtTmp = getDate(tBaseDate,'-'); 
var tTempDate; 
var tYear;
var tMonth;
var tDay;
var tCurrentDate;
tUnit=tUnit.toUpperCase();
switch (tUnit) {     
case'D':
tTempDate=new Date(Date.parse(dtTmp) + (86400000 * tIntv));
tYear=tTempDate.getFullYear();
tMonth=tTempDate.getMonth()+1;
tDay=tTempDate.getDate(); 
tCurrentDate=tYear+'-'+tMonth+'-'+tDay;  
tCurrentDate=formartDate(tCurrentDate);
return tCurrentDate;                 
case'M':
tTempDate=new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + tIntv, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());   
tYear=tTempDate.getFullYear();
tMonth=tTempDate.getMonth()+1;
tDay=tTempDate.getDate(); 
tCurrentDate=tYear+'-'+tMonth+'-'+tDay;  
tCurrentDate=formartDate(tCurrentDate); 
return tCurrentDate;
case'Y':
tTempDate=new Date((dtTmp.getFullYear() + tIntv), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());   
tYear=tTempDate.getFullYear();
tMonth=tTempDate.getMonth()+1;
tDay=tTempDate.getDate(); 
tCurrentDate=tYear+'-'+tMonth+'-'+tDay;  
tCurrentDate=formartDate(tCurrentDate); 
return tCurrentDate;
}
}       

/**
*���������������õ���ȷ�ĳ������
*˵����javascript�ĳ�����������������������������ʱ���Ƚ����ԡ�����������ؽ�Ϊ��ȷ�ĳ�������� 
*���ã�accDiv(arg1,arg2)
*����ֵ��arg1����arg2�ľ�ȷ��� 
* add by sunsx 2008-12-01 
*/
function accDiv(arg1,arg2)
{ 
	var t1=0,t2=0,r1,r2; 
	try{t1=arg1.toString().split(".")[1].length}catch(e){} 
	try{t2=arg2.toString().split(".")[1].length}catch(e){} 
	with(Math){ 
	r1=Number(arg1.toString().replace(".","")) 
	r2=Number(arg2.toString().replace(".","")) 
	return (r1/r2)*pow(10,t2-t1); 
	} 
} 

/*
*�˷������������õ���ȷ�ĳ˷���� 
*˵����javascript�ĳ˷������������������������˵�ʱ���Ƚ����ԡ�����������ؽ�Ϊ��ȷ�ĳ˷������ 
*���ã�accMul(arg1,arg2) 
*����ֵ��arg1����arg2�ľ�ȷ���
*add by sunsx 2008-12-01
*/ 
function accMul(arg1,arg2) 
{ 
var m=0,s1=arg1.toString(),s2=arg2.toString(); 
try{m+=s1.split(".")[1].length}catch(e){} 
try{m+=s2.split(".")[1].length}catch(e){} 
return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m) 
} 

/*�ӷ������������õ���ȷ�ļӷ���� 
*˵����javascript�ļӷ������������������������ӵ�ʱ���Ƚ����ԡ�����������ؽ�Ϊ��ȷ�ļӷ������ 
*���ã�accAdd(arg1,arg2) 
*����ֵ��arg1����arg2�ľ�ȷ��� 
*add by sunsx 2008-12-01
*/
function accAdd(arg1,arg2){ 
var r1,r2,m; 
try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0} 
try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0} 
m=Math.pow(10,Math.max(r1,r2)) 
return (arg1*m+arg2*m)/m 
}

/*�ӷ������������õ���ȷ�ļӷ���� 
*˵����javascript�ļ�����������������������������ʱ���Ƚ����ԡ�����������ؽ�Ϊ��ȷ�ļ�������� 
*���ã�Subtr(arg1,arg2) 
*����ֵ��arg1��ȥarg2�ľ�ȷ��� 
*add by sunsx 2008-12-01
*/  
function accSub(arg1,arg2){
     var r1,r2,m,n;
     try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
     try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
     m=Math.pow(10,Math.max(r1,r2));
     n=(r1>=r2)?r1:r2;
     return ((arg1*m-arg2*m)/m).toFixed(n);
}
/*�������㾫�Ⱥ����������õ���ȷ������ 
*���ã�
*�ӷ�Arithmetic(1.3,'+','3',2); 
*����Arithmetic(1.3,'-','3',3); 
*�˷�Arithmetic(1.3,'*','3',4); 
*����Arithmetic(1.3,'/','3',5); 
*����ֵ��arg1��arg2����������ľ�ȷ��� 
*add by sunsx 2008-12-01
*/
function Arithmetic(arg1,operator,arg2,accuracy){
	var tArithmeticRet;
	switch(operator)
	{ 
      case "+": 
      	tArithmeticRet = accAdd(arg1,arg2); 
      	break;   
      case "-":   
     		tArithmeticRet = accSub(arg1,arg2);
     		break;
      case "*":     
     		tArithmeticRet = accMul(arg1,arg2);
     		break;
      case "/":   
    		tArithmeticRet = accDiv(arg1,arg2);
    		break; 
   }
   return FormatByAccuracy(tArithmeticRet,accuracy);
	
} 
/* ���������ȴ���
* С��λ���� 
*����toPrecision�Ǵӵ�һ����Ϊ0��ֵ��ʼ�����ȣ�   
*�����ݲ�����0.00000X(<0.01)�����
*/
function FormatByAccuracy(val,accuracy){   
 
		if(val){ 
		    if(accuracy==0&&parseFloat(val)<1){ 
		        return parseFloat(val).toPrecision();   
		    }else{   
		        val = Number(val).toString();   
		        index = val.indexOf('.');   
		        //len����λ����   
		        len = index==-1?val.length:(val.substr(0,index)=='0'?index-1:index);   
		        accuracy = parseInt(len,10)+parseInt(accuracy,10);   
		        //toPrecision���֧��21λ����   
		        var accuracyaccuracy = accuracy>21?21:accuracy;                   
		        return parseFloat(val).toPrecision(accuracyaccuracy);   
		    }   
		}else{   		
		    return val;   		
		}   
}
/*
* Div������������
*add by sunsx 2008-12-31
*
*/
function unlockScreen(divid)
{ 
	if(document.getElementById(divid))
	{ 
		document.getElementById(divid).style.display='none'; 
		document.getElementById(divid).innerHTML=""; 
		document.getElementById(divid).parentNode.removeChild(document.getElementById(divid)); 
	} 
}

/*
* Div��������
*add by sunsx 2008-12-31
*
*/
function lockScreen(divId){ 
 
 		var oDiv = document.createElement("DIV"); 
 		oDiv.id=divId; 
 		oDiv.style.backgroundColor="#000000"; 
 		oDiv.style.position ="absolute"; 
 		oDiv.style.top = 0; 
 		oDiv.style.left = 0; 
 		oDiv.style.width=document.body.clientWidth+ "px"; 
 		var sh=document.body.scrollHeight;//�������ĸ߶�
 		var ch =document.body.clientHeight;
 		if(sh > ch)
 		{
 			oDiv.style.height=sh+ "px";
 		}else
 		{
 			oDiv.style.height=ch+ "px";
 		}

 		oDiv.style.filter="alpha(Opacity=20)"; 

 		document.body.appendChild(oDiv); 
 		oDiv.innerHTML="<div style='position:absolute;top:50%;left:50%;margin-top:-50px;margin-left:-50px;width:200px;height:100px;'><img src='../common/images/indicator_medium.gif'/><div>";
 	                            
}     
 
//tongmeng 2009-03-12 add
//ֻ����,�������ݷŵ�������
function showOneCodeNameRefresh(strCode, showObjCode, showObjName) {


	var formsNum = 0;	//�����е�FORM��
	var elementsNum = 0;	//FORM�е�Ԫ����
	var urlStr = "";
	var sFeatures = "";
	var strQueryResult = "";	//����ѡ��Ĳ�ѯ�����
	var arrCode = null;	//�������
	var strCodeValue = "";	//����ֵ
	var cacheFlag = false;	//�ڴ��������ݱ�־
	var showObjn;
	var showObjc;
	if (showObjName == null) showObjName = strCode;
	//��������FORM
	for (formsNum=0; formsNum<window.document.forms.length; formsNum++)

	{
		//����FORM�е�����ELEMENT
		for (elementsNum=0; elementsNum<window.document.forms[formsNum].elements.length; elementsNum++)

		{
			//Ѱ�Ҵ���ѡ��Ԫ��
			//alert(window.document.forms[formsNum].elements[elementsNum].name);
			if (window.document.forms[formsNum].elements[elementsNum].name == showObjCode)
			{
				showObjc = window.document.forms[formsNum].elements[elementsNum];
			}
			if (window.document.forms[formsNum].elements[elementsNum].name == showObjName)
			{
				showObjn = window.document.forms[formsNum].elements[elementsNum];
				break;
			}
		}
	}
	//��������������ݲ�Ϊ�գ��Ų�ѯ���������κβ���
	if (showObjc.value != "")
	{
		//Ѱ��������
		var win = searchMainWindow(this);
		if (win == false) { win = this; }



		//��������������ݣ���������ȡ����
		if (win.parent.VD.gVCode.getVar(strCode))
		{
			arrCode = win.parent.VD.gVCode.getVar(strCode);
			cacheFlag = true;
		}
		else
		{
			urlStr = "../common/jsp/CodeQueryWindow.jsp?codeType=" + strCode;
			sFeatures = "status:no;help:0;close:0;dialogWidth:150px;dialogHeight:0px;resizable=1";
			            //+ "dialogLeft:-1;dialogTop:-1;";
			//�������ݿ����CODE��ѯ�����ز�ѯ�����strQueryResult
			strQueryResult = window.showModalDialog(urlStr, "", sFeatures);
		}
		//��ֳ�����
		try {
			if (!cacheFlag)

			{
				try
				{
					arrCode = decodeEasyQueryResult(strQueryResult);
				}
				catch(ex)
				{
					alert("ҳ��ȱ������EasyQueryVer3.js");
				}
				strCodeSelect = "";
				for (i=0; i<arrCode.length; i++)
				{
					strCodeSelect = strCodeSelect + "<option value=" + arrCode[i][0] + ">";
					strCodeSelect = strCodeSelect + arrCode[i][0] + "-" + arrCode[i][1];
					strCodeSelect = strCodeSelect + "</option>";
				}
				//����ֺõ����ݷŵ��ڴ���
				//win.parent.VD.gVCode.addArrVar(strCode, "", arrCode);
				//�����Ƿ������ݴӷ������˵õ�,�����øñ���
				//win.parent.VD.gVCode.addVar(strCode+"Select","",strCodeSelect);
			}
			cacheFlag = false;
			for (i=0; i<arrCode.length; i++)
			{
				if (showObjc.value == arrCode[i][0])
				{
					showObjn.value = arrCode[i][1];
					break;
				}
			}
		}
		catch(ex)
		{}
	}
}

/*
Function�����������������
Para:
          type-Ҫ�ı�����ͣ�1-��
                             2-��
                             3-ʱ
                             4-��
                             5-��
                             6-����
                             7-��
          NumDay-ʱ����
          dtDate-ԭ����
Author:   wellhi
Date:     2006-04-06
Example:    
	document.write(addDate("5",5,"2005-5-21"))
*/
function addDate(type,NumDay,dtDate){
	//modify by fengzq 20080716 �޸�У���Ƿ����ڵķ�������������ڸ�ʽͨ��У��
	//if(!IsDate(dtDate)){
	if(!isDate(dtDate)){
		return false;
	}
	var oDate;
	oDate=replace(dtDate,"-","/");
	oDate=oDate + " 00:00:00";
	var date = new Date(oDate)
	type = parseInt(type) //���� 
	lIntval = parseInt(NumDay)//���
	switch(type){
		case 7 ://��
		  date.setFullYear(date.getFullYear() + lIntval)
		  break;
 		case 6 ://����
		  date.setMonth(date.getMonth() + (lIntval * 3) )
		  break;
		case 5 ://��
		  date.setMonth(date.getMonth() + lIntval)
		  break;
 		case 4 ://��
		  date.setDate(date.getDate() + lIntval)
		  break
 		case 3 ://ʱ
		  date.setHours(date.getHours() + lIntval)
		  break
		case 2 ://��
		  date.setMinutes(date.getMinutes() + lIntval)
		  break
 		case 1 ://��
		  date.setSeconds(date.getSeconds() + lIntval)
		  break;
 		default:    
  	} 
  //modify by fengzq 20080716 ͳһ��������ڷ��ظ�ʽΪ YYYY-MM-DD
  //return date.getYear() +'-' +  (date.getMonth()+1) + '-' +date.getDate();
  var newDate = date.getFullYear() +'-' +  (date.getMonth()+1) + '-' +date.getDate(); 
  if(newDate.length==8 )
	{newDate=newDate.substring(0,4) + '-0' + newDate.substring(5,6) + '-0' + newDate.substring(7,8);}
	else if(newDate.length==9 && newDate.substring(6,7)=='-')
	{newDate=newDate.substring(0,4) + '-0' + newDate.substring(5,9);}
  else if(newDate.length==9 && newDate.substring(7,8)=='-')
	{newDate=newDate.substring(0,7) + '-0' + newDate.substring(8,9);}
  return newDate;
} 

/**
  * 15λ���֤����ת��Ϊ18λ�����֤��
  * �����18λ�����֤��ֱ�ӷ��أ������κα仯��
  * @param idCard,15λ����Ч���֤����
  * @return idCard18 ����18λ����Ч���֤
  */
 function IdCard15to18(idCard){
  idCard = idCard.trim();
  var idCard18 = new String(idCard);
  //У����ֵ
  var checkBit = ['1','0','X','9','8','7','6','5','4','3','2'];
  var sum = 0;
  //alert("idCard18="+idCard18.length);
  
  //15λ�����֤
  if(idCard!=null && idCard.length==15){
  
   idCard18 = idCard18.substring(0,6)+"19"+idCard18.substring(6,15);

   for(var index=0;index<idCard18.length;index++){
    var c = idCard18.charAt(index);
    var ai = parseInt(c);
    //��Ȩ���ӵ��㷨
    var Wi = (Math.pow(2, idCard18.length-index))%11;
    sum = sum+ai*Wi;
   }
   var indexOfCheckBit = sum%11; //ȡģ
   idCard18 = idCard18+(checkBit[indexOfCheckBit]);
  }
  
  //alert("idCard18="+idCard18);
  return idCard18;
 }
 
 /**
  * ת��18λ���֤λ15λ���֤������������15λ�����֤�����κ�ת����ֱ�ӷ��ء�
  * @param idCard 18λ���֤����
  * @return idCard15
  */
 function IdCard18to15(idCard){
   idCard = idCard.trim();
  var idCard15 = new String(idCard);
  if(idCard!=null && idCard.length==18){
  
   idCard15 = idCard15.substring(0, 6)+idCard15.substring(8, 17);
   
  }
  //alert("idCard15="+idCard15);
  return idCard15;
  
 }
 
  /**
  * У����һ�֤������
  * @param iNativePlace �������
  * @param iIdType ֤������
  * @param iIdNo ֤������
  */
 function checkMultiIdNo(iNativePlace,iIdType,iIdNo)
 {
 	alert("NativePlace:"+ iNativePlace + ";IdType:" + iIdType + ";IdNo:" + iIdNo);
 	if(iNativePlace == "CHN"){	 	//�й�
 		//У�����֤
 		if(iIdType == "0"){
 			checkIdCard(iIdNo);
 		} else if (iIdType == "1") {
 			
 		} else {
 			
 		}
 	} else if(iNativePlace == "JAN"){ //�ձ�
 		
 	} else {
 		
 	}
 }
 
/**EJB SQL���� ���÷���, notes added by zhouwh@sinosoft.com.cn*/
/**
	mysql���������ݴ����������Sql�ַ���
	
	sqlId:ҳ����ĳ��sql��Ψһ��ʶ
	param:��������,sql��where��������Ĳ���
	function created by ganhaitian
**/
function wrapSql(resourceName,sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName(resourceName);
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	return mysql.getString();
}

 
/*******************************************************************
*  ������� alert �ĵ�������
*  content ��ʾ����ʾ��Ϣ
*  SuccOrFail ��Сд��ʾ����Ϣ��ǰ̨��������ȫ��ͬ��alert
*  SuccOrFail �Ǵ�д��ʾ����Ϣ�Ǻ�̨����
*  SuccOrFail Ϊ�ջ�"C"��"c"��һ����ʾ��Ϣ Common
*  SuccOrFail Ϊ"S"��"s"��ʾ��ʾ�ɹ�����Ϣ Succ
*  SuccOrFail Ϊ"F"��"f"��ʾ��ʾʧ�ܵ���Ϣ Fail
*  QianLy Added On 2007-08-10
********************************************************************
*/
function show(content,SuccOrFail){
    var tSuccOrFail = "c";
    if(SuccOrFail == null || SuccOrFail == ""){
      tSuccOrFail = "c";
    }else if(isIn(SuccOrFail,"s/f/c/S/F/C","/",true)){
      tSuccOrFail = SuccOrFail;
    }
    var urlStr="../common/jsp/MessagePage.jsp?picture="+tSuccOrFail+"&content=" + content ;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
}

//����ַ������������֡���ĸ�Ϳո񣬷���--true ������--false add by huangx
function chkNotZh(tchar){
	if (tchar ==""){
		return true;
	}
	var pattern = /^([\w]|[\s])*$/gi;
	if (!pattern.test(tchar)){
		return false;
	}	
	else{
		return true;
	}	
}
/**
* ���������Ƿ������ڵ�У��
* <p><b>Example: </b><p>
* <p>isDate("2002-10-03") returns true<p>
* <p>isDate("2002/10/03") returns false<p>
* @param date �����ַ���,��ʽ����Ϊ��yyyy-mm-dd��
* @return ����ֵ��true--�Ϸ�����, false--�Ƿ����ڣ�
*/
function isDateT(sDate)
{
  
  var result=sDate.match(/^(\d{4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
  if(result==null) return false;
  var tDate= new Date(result[1], result[3]-1, result[4], result[5], result[6], result[7]);
  return (tDate.getFullYear()==result[1]&&(tDate.getMonth()+1)==result[3]&&tDate.getDate()==result[4]&&tDate.getHours()==result[5]&&tDate.getMinutes()==result[6]&&tDate.getSeconds()==result[7]);

}


//��ȡ�ַ����ֽ���
function getStrByte(cStr)   
{   
   var tStr = cStr.replace(/[^\x00-\xff]/g,"**");
   return tStr.length;   
} 

function I18NMsg(msgCode)
{
		var url = '../i18n/jsboundle.jsp';
		var pars = "base=i18n&msgCode="+msgCode;
		msg = '';
		Request = initRequest();
    Request.open("POST",url, false);
    Request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
    Request.send(pars);
    try
    {
        msg = Request.responseText;
        if (msg != null && typeof(msg) == "string")
        {
            msg = msg.trim();
        }
    }
    catch (ex)
    {
        myAlert("���ݷ��س���" + ex.message);
    }
		return msg;
}
function myAlert(msg)
{
	  //alert(msg);
		(new divAlert(this,msg+"<p><input value=ok type=button id=surebutton onclick='$this.close()' onmousedown='$this.close()' />")).open();
}

/*************************************************
*
*	class divAlert
*	���ܣ�div��ʽ����ʾ���ұ����䰵�����ɲ���
*	���ߣ�pwwang
*	��վ��http://pwwang.com
*	������str: ��ʾ�������
*
**************************************************/
function divAlert(opene,str){
	 this.init(opene,str); 
}
 
$this = divAlert.prototype;
 
$this.str = "";
$this.MsgOpener = null;
$this.startX = 0;
$this.startY = 0;
$this.dragFlag = false;
$this.width = 360;		
$this.returnValue = "";
 

$this.bgdiv = document.createElement("div");
$this.bgdiv.style.position = "absolute";
$this.bgdiv.style.top = 0;
$this.bgdiv.style.left = 0;
$this.bgdiv.style.zIndex = 9999;
$this.bgdiv.style.filter = "alpha(opacity=80)";
$this.bgdiv.style.backgroundColor = "#999";


 

$this.outdiv = document.createElement("div");

$this.setWidth = function(w){
	$this.width = w;
	$this.outdiv.style.width = $this.width + "px";
	$this.outdiv.style.marginLeft = (0-$this.width/2) + "px"; 
}
$this.outdiv.style.position = "absolute";
$this.setWidth($this.width);

$this.outdiv.style.border = "1px solid #369";
$this.outdiv.style.zIndex = 10000;

 

$this.titdiv = document.createElement("div");
$this.titdiv.style.textAlign = "right";
$this.titdiv.style.backgroundColor = "#9cf";
$this.titdiv.style.padding = "8px"
$this.titdiv.style.cursor = "move";
//$this.titdiv.onmousedown = function(e){
//	e = e ? e : window.event;
//	$this.dragFlag = true;
//	$this.startX = (e.layerX ? e.layerX : e.offsetX) - $this.outdiv.offsetWidth/2;
//	$this.startY = e.layerY ? e.layerY : e.offsetY;
//}
//$this.titdiv.onmousemove = function(e){
//	e = e ? e : window.event;
//	if($this.dragFlag){
//		if(!e.pageX) e.pageX = e.clientX;
//		if(!e.pageY) e.pageY = e.clientY;
//		$this.outdiv.style.left = (e.pageX - $this.startX) + "px"; 
//		$this.outdiv.style.top = (e.pageY - $this.startY) + "px"; 
// 
//	}
//}
//$this.titdiv.onmouseup = function(){ $this.dragFlag = false;}
//$this.titdiv.onmouseout = function(){$this.dragFlag = false;};
 

$this.condiv = document.createElement("div");
$this.condiv.style.backgroundColor = "#fff";
$this.condiv.style.textAlign = "center";
$this.condiv.style.padding = "12px";
$this.condiv.style.height = "100px";
 

$this.clsbtn = document.createElement("a"); 
$this.clsbtn.innerHTML = "<img src=''>";
$this.clsbtn.style.cursor = "pointer"; 	
$this.clsbtn.style.fontSize = "12px";

$this.clsbtn.onmouseover = function(){ $this.dragFlag = false; }
$this.clsbtn.onmousedown = function(){ $this.close(); }
 
$this.init = function(opene,str){
	this.str = str;
	this.MsgOpener=opene; 	
	this.bgdiv.style.width = ((opene.document.body.scrollWidth >= opene.document.body.clientWidth) ? opene.document.body.scrollWidth : opene.document.body.clientWidth)+"px";
	this.bgdiv.style.height = ((opene.document.body.scrollHeight >= opene.document.body.clientHeight) ? opene.document.body.scrollHeight : opene.document.body.clientHeight)+"px";
	this.outdiv.style.left = (opene.document.body.scrollLeft+(opene.document.body.clientWidth-$this.outdiv.offsetWidth)/2)+"px";
	this.outdiv.style.top = (opene.document.body.scrollTop+(opene.document.body.clientHeight-$this.outdiv.offsetHeight-210)/2)+"px";;
}
 
$this.open = function(){
	document.body.appendChild(this.bgdiv);
	document.body.appendChild(this.outdiv);
	this.outdiv.appendChild(this.titdiv);
	this.outdiv.appendChild(this.condiv);
	this.titdiv.appendChild(this.clsbtn);
	this.condiv.innerHTML = this.str;
	document.all("surebutton").focus();
}
 
$this.close = function(){
	this.outdiv.removeChild(this.titdiv);
	this.outdiv.removeChild(this.condiv);
	this.titdiv.removeChild(this.clsbtn);
	document.body.removeChild(this.bgdiv);
	document.body.removeChild(this.outdiv);
}

function creatXMLHttpRequest(){
	
  return initRequest(); 		
}

function lockPart(objID,msg){
	var loadMsg = "Loading........";
	if(msg != null && msg != ""){
		loadMsg = msg;
	}
	var obj = $('#'+objID);
	var h = obj.height();
	var w = obj.width();

		$("<div class=\"datagrid-mask\" fit=\"true\"></div>").css(
			{display:"block",
			 width:w,
			 height:h
			}).appendTo(obj);
	$("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo(obj).css(
			{display:"block",
			 left:(w-$("div.datagrid-mask-msg",obj).outerWidth())/2,
			 top:(h-$("div.datagrid-mask-msg",obj).outerHeight())/2
			});  
}

function unlockPart(objID){
		$('#'+objID).find("div.datagrid-mask-msg").remove();
		$('#'+objID).find("div.datagrid-mask").remove();
}

function unLockPage()
{ 

		$(document.body).find("div.datagrid-mask-msg").remove();
		$(document.body).find("div.datagrid-mask").remove();

}


function lockPage(msg){ 
	
		var loadMsg = "Loading........";
		if(msg != null && msg != ""){
				loadMsg = msg;
		}
		var dheight = 0;
		var dWidth = 0;
	 	var sh=document.body.scrollHeight;//�������ĸ߶�
 		var ch =document.body.clientHeight;	
 		if(sh > ch){
 			dheight=sh;
 		}else{
 			dheight=ch;
 		}
 		var sw = document.body.scrollWidth;
 		var cw =document.body.clientWidth;
		if(sw >cw ){
			dWidth=sw; 
		}else{
			dWidth=cw;
		}
	var body = $(document.body);
	$("<div class=\"datagrid-mask\"></div>").css(
			{display:"block",
			 width:dWidth,
			 height:dheight
			}).appendTo(body);
	$("<div class=\"datagrid-mask-msg\"></div>").html(msg).appendTo(body).css(
			{display:"block",
			 left:(dWidth-$("div.datagrid-mask-msg",body).outerWidth())/2,
			 top:(dheight-$("div.datagrid-mask-msg",body).outerHeight())/2
			 });                    
} 