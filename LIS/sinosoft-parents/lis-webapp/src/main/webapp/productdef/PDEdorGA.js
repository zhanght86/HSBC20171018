//�������ƣ�PDEdorGA.js
//�����ܣ����������
//�������ڣ�2009-3-16
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
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
var Mulline10GridTurnPage = new turnPageClass(); 
var Mulline11GridTurnPage = new turnPageClass(); 
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
function button302()
{
  	var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"��ѡ���ѱ����ֶβ�����һ����¼�ٵ��"+"");
		return;
	}	
	var calCode = Mulline10Grid.getRowColData(selNo-1,4);
	if( calCode == null || calCode == "" )
	{
		myAlert(""+"�Ƿ����㷨���룡"+"");
		return;
	}
	showInfo = window.open("PDAlgoDefiMain.jsp?riskcode=" + fm.all("RiskCode").value+"&algocode="+calCode);
}	
		
function afterQueryAlgo(AlgoCode)
{
	var selNo = Mulline9Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"ѡ���еĽ��㶪ʧ"+"");
		return;
	}
	
	Mulline9Grid.setRowColDataCustomize(selNo-1,4,AlgoCode);
}
function retBack()
{
	try
	{
		top.opener.focus();
	}
	catch(ex)
	{
		myAlert(ex);
		window.open("PDEdorDefiEntryMain.jsp?riskcode=" + fm.all("RiskCode").value);
	}
	
	top.close();
}

function queryMulline9Grid()
{
   var strSQL = "";
   strSQL = "select DECODE(standbyflag1, 'N', '[*]' || fieldname,  fieldname),fieldcode,fieldtype,null,officialdesc,busidesc from Pd_Basefield where tablecode = upper('PD_LMEdorGA') and isdisplay = '1' order by displayorder";
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   strSQL = "SELECT RiskCode,RiskVersion,InsuAccNo,CalCode,IntvType FROM PD_LMEdorGA WHERE RiskCode = '"+fm.all("RiskCode").value+"'";
   Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}

// ��ѯ��Ʒ�ı����˻���Ϣ
function queryMulline11Grid()
{
   var strSQL = "";
   strSQL = "SELECT RiskCode,InsuAccNo FROM PD_LMRisktoAcc WHERE RiskCode = '"+fm.all("RiskCode").value+"'";
   Mulline11GridTurnPage.pageLineNum  = 3215;
   Mulline11GridTurnPage.queryModal(strSQL,Mulline11Grid);
}
