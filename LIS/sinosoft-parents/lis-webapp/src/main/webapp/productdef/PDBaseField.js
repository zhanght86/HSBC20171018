//�������ƣ�PDBaseField.js
//�����ܣ�������Ϣ�ֶ�����
//�������ڣ�2009-3-17
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
  
  fm.submit();
}

function afterSubmit( FlagStr, content )
{
  showInfo.close();

  if (FlagStr == "Fail" )
  {             
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
  }
  else
  {
var urlStr="../common/jsp/MessagePage.jsp?picture=S&content="+content;
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    initForm();    
  } 
}
function update()
{
 fm.all("operator").value="update";
 if(!verifyInput2()){
		return false;
	}
 submitForm();
}
function query()
{
  showInfo = window.open("PDBaseFieldQueryMain.jsp");
}
function afterReturnParent(arrReturn)
{
	fm.all('TableCode').value = arrReturn[0];
	//fm.all('TableName').value = arrReturn[1];
	fm.all('FieldCode').value = arrReturn[2];
	//fm.all('FieldName').value = arrReturn[3];
	fm.all('FieldType').value = arrReturn[4];
	fm.all('IsDisplayCode').value = arrReturn[5];
	fm.all('OfficialDesc').value = arrReturn[6];
	fm.all('BusiDesc').value = arrReturn[7];
	fm.all('FieldTypeName').value = arrReturn[8];
	showCodeName();
	//showOneCodeName('pdtablename','TableCode','TableName');
	showOneCodeNameEx('pdfieldname','FieldCode','FieldName','tablecode',fm.all('TableCode').value);
}


