//�������ƣ�PDRiskAppDefi.js
//�����ܣ����ֳб�����
//�������ڣ�2009-3-12
//���ļ��а����ͻ�����Ҫ�����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;
function checkNullable()
{
  var GridObject = Mulline9Grid;
  var rowCount = GridObject.mulLineCount;
  
  for(var i = 0; i < rowCount; i++)
  {
    var PropName = GridObject.getRowColData(i,1);
    var PropValue = GridObject.getRowColData(i,4);
    
  	if(PropName.indexOf("[*]") > -1)
  	{
  		if(PropValue == null || PropValue == "")
  		{
  			PropName = PropName.substring(3,PropName.length);
  		
  			myAlert("\"" + PropName + ""+"\"����Ϊ��"+"");
  			
  			return false;
  		}
  	}
  }
  
  return true;
}

function submitForm()
{
if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
  	return;
  }
	if(!checkNullable())
	{
		return false;
	}
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
var Mulline9GridTurnPage = new turnPageClass(); 
function saveData()
{
 	fm.all("operator").value="save";
 	submitForm();
}
function update()
{
 fm.all("operator").value="update";
 submitForm();
}
function del()
{
 fm.all("operator").value="del";
 submitForm();
}
function returnParent()
{
  top.opener.focus();
	top.close();
}

function queryMulline9Grid()
{
   var strSQL = "";
   strSQL = "select DECODE(standbyflag1, 'N', '[*]' || fieldname,  fieldname),fieldcode,fieldtype,null,officialdesc,busidesc from Pd_Basefield where tablecode = upper('PD_LMRiskApp') and isdisplay = '1' order by displayorder";Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}