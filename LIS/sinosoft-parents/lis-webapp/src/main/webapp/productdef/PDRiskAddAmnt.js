//�������ƣ�PDRiskAddAmnt.js
//�����ܣ����ֱ��������
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
 var selNo = Mulline11Grid.getSelNo();
 if( selNo == 0 )
 {
 	myAlert(""+"��ѡ��һ����¼��"+"");
 	return;
 }	
 fm.all("operator").value="update";
 submitForm();
}
function button298()
{
	var selNo = Mulline11Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"��ѡ���ѱ����ֶβ�����һ����¼�ٵ��"+"");
		return;
	}	
	var calCode = Mulline11Grid.getRowColData(selNo-1,6);
	if( calCode == null || calCode == "" )
	{
		myAlert(""+"���ǺϷ����㷨���룡"+"");
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
function button299()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
   var strSQL = "";
   strSQL = "select DECODE(standbyflag1, 'N', '[*]' || fieldname,  fieldname),fieldcode,fieldtype,null,officialdesc,busidesc from Pd_Basefield where tablecode = upper('PD_LMRiskAddAmnt') and isdisplay = '1' order by displayorder";
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   strSQL = " SELECT a.Riskcode,b.Dutycode,b.Dutyname,c.Payplancode FROM PD_LMRiskDuty a,PD_LMDuty b,PD_LMDutyPay c "
   		  + " WHERE a.Riskcode = '"+fm.all("RiskCode").value+"' and a.Dutycode = b.Dutycode and b.Dutycode = c.Dutycode";
   Mulline10GridTurnPage.pageLineNum  = 3215;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function queryMulline11Grid()
{
   var strSQL = "";
   strSQL = "SELECT RiskCode,'',DutyCode,'','',AddCalCode,'' FROM PD_LMRiskAddAmnt WHERE RiskCode = '"+fm.all("RiskCode").value+"'";
   Mulline11GridTurnPage.pageLineNum  = 3215;
   Mulline11GridTurnPage.queryModal(strSQL,Mulline11Grid);
}
