//�������ƣ�PDCheckRule.js
//�����ܣ���������
//�������ڣ�2009-3-17
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
	if( verifyInput() != true ) 
	{
	return;
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
    initDisplay();    
    query();
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
function save()
{
 fm.all("operator").value="save";
 submitForm();
}
function update()
{
 fm.all("operator").value="update";
 submitForm();
}
function query()
{
  try
	{
	   var strSQL = "";
	   strSQL = "select code,name,(select codename from ldcode where codetype = 'pdcheckruletype' and code = type),algo,description,type from Pd_Checkrule_Lib where 1=1 "
	   			+ getWherePart('code','CheckRuleCode') + getWherePart('name','CheckRuleName')
	   			+ getWherePart('type','Type') + getWherePart('algo','Algo')
	   			+ getWherePart('description','Description')
	   			+ " order by code"
	   			;
	   
	   Mulline9GridTurnPage.pageLineNum  = 3215;
       Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
   }
   catch(ex)
   {
   	   myAlert(""+"����ʱ����"+""+ex);
   }
}
function del()
{
	if(window.confirm(""+"ȷ��Ҫɾ������У�������?"+"")){
 		fm.all("operator").value="del";
 		submitForm();
	}
}

