//�������ƣ�PDCalFactor.js
//�����ܣ��㷨Ҫ�ؿ�
//�������ڣ�2009-3-17
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
	if( verifyInput() != true ) {
		return;
	}
	
  var showStr=""+"���ڴ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��"+"";
var urlStr="../common/jsp/MessagePage.jsp?picture=C&content="+showStr;
  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   	
  
  fm.submit();
}

function afterQueryFactor(dicSelectedFactors)
{
	try
	{       
	  	var factorCount = dicSelectedFactors.Count;
		a = (new VBArray(dicSelectedFactors.Items())).toArray(); 
		// ���ѡ���˶��У�ֻ��ʾ���һ�е�Ҫ����Ϣ
		var index = factorCount - 1;
		
		fm.all("FactorType").value = a[index][6];
	   	fm.all("Module").value = a[index][7];
		fm.all("FactorCode").value = a[index][3];
		fm.all("FactorValType").value = a[index][4];
		fm.all("FactorDesc").value = a[index][5];
		fm.all("Kind").value = a[index][8];
		
		fm.all("FactorCode").readOnly = true;
		fm.all("btnSave").disabled = true;
		
		fm.ModuleName.value = "";
		fm.PropertyName.value = "";
		fm.FactorTypeName.value = "";
		
		showCodeName();
   }
   catch(ex)
   {
   	   myAlert(""+"����ʱ����"+""+ex);
   }
}

function afterSubmit( FlagStr, content )
{
  if(showInfo != null)
  {
  	showInfo.close();
  }
  
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
  window.open("PDCalFactorQueryMain.jsp?algotype=null&selectTable=PD_CalFactor_Lib");
}
function del()
{
	if(window.confirm(""+"ȷ��Ҫɾ�������㷨Ҫ����?"+"")){
 		fm.all("operator").value="del";
 		submitForm();
 	}
}

