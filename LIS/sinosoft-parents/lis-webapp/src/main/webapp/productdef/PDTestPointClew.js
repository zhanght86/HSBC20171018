//�������ƣ�PDTestPointClew.js
//�����ܣ�����Ҫ����ʾ
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

function save()
{
  if(!verifyInput2()){
  	return false;
  }
  if(fm.all("Id").value!=""){
		myAlert(""+"�Բ�ѯ�󷵻صĽ��ֻ�ܽ����޸ģ�"+"")
		return false;
	}
  fm.all("operator").value="save";
  submitForm();
}
function update()
{
  if(!verifyInput2()){
 		return false;
 	}
 	if(fm.all("Id").value==null||fm.all("Id").value==""){
		myAlert(""+"ֻ�ܶԲ�ѯ�󷵻صĽ�������޸ģ�"+"")
		return false;
	}
 	fm.all("operator").value="update";
 	submitForm();
}
function query()
{
  showInfo = window.open("PDTestPointClewQueryMain.jsp");
}
function del()
{
 	if(!verifyInput2()){
 		return false;
 	}
 	if(fm.all("Id").value==null||fm.all("Id").value==""){
		myAlert(""+"ֻ�ܶԲ�ѯ�󷵻صĽ������ɾ����"+"")
		return false;
	}
	if(window.confirm(""+"ȷ��Ҫɾ����������Ҫ����ʾ��?"+"")){
 		fm.all("operator").value="del";
 		submitForm();
	}
}
function afterQuery(TableCode,FieldCode,Id)
{
	try
	{
	   var strSQL = "";
	   strSQL = "select tablecode,fieldcode,clewcontent from Pd_Testpointclew_Lib where tablecode = '"
	   + TableCode +"' and fieldcode = '" + FieldCode +"' and Id = '" + Id +"'";

	   var arr = easyExecSql(strSQL);
	   fm.all("TableCode").value = arr[0][0];
	   fm.all("FieldCode").value = arr[0][1];
	   fm.all("Id").value = Id;
	   fm.all("ClewContent").value = arr[0][2];
	   showCodeName();
	   showOneCodeNameEx('pdfieldname','FieldCode','FieldName','tablecode',fm.all('TableCode').value);
   }
   catch(ex)
   {
   	   myAlert(""+"����ʱ����"+""+ex);
   }
}

