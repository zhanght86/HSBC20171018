//�������ƣ�PDClaimDefi.js
//�����ܣ����θ����������
//�������ڣ�2009-3-16
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function submitForm()
{
if(fm.all("IsReadOnly").value == "1")
  {
  	myAlert(""+"����Ȩִ�д˲���"+"");
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
    initForm();    
  } 
}
var Mulline9GridTurnPage = new turnPageClass(); 
function button224()
{
  	var selNo = Mulline9Grid.getSelNo();
	if(selNo == 0)
	{
		myAlert(""+"��ѡ���㷨���ٵ��"+"");
		return;
	}
	else
	{
		var name = Mulline9Grid.getRowColData(selNo-1,1); 
		if(name.indexOf(""+"�㷨"+"") == -1)
		{
			myAlert(""+"��ѡ���㷨��"+","+"���������趨���㷨"+"");
			return;
		}
	}
	
	showInfo = window.open("PDAlgoDefiMain.jsp?riskcode=" + fm.all("RiskCode").value+ "&algocode=" + Mulline9Grid.getRowColData(selNo-1,4));
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
function button225()
{
  showInfo = window.open("PDDutyGetAliveMain.jsp?riskcode=" + fm.all("RiskCode").value+"&getdutycode="+fm.all('GetDutyCode').value);
}
function button226()
{
  showInfo = window.open("PDDutyGetClmMain.jsp?riskcode=" + fm.all("RiskCode").value+"&getdutycode="+fm.all('GetDutyCode').value);
}
function button227()
{
  showInfo = window.open("PDClaimCtrlMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button228()
{
  showInfo = window.open("PDClaimCtrlFeeMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button230()
{
  showInfo = window.open("PDClaimCtrlPeriodMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button231()
{
  showInfo = window.open("PDRiskAccGetMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button232()
{
  showInfo = window.open("PDDutyGetClmCalMain.jsp?riskcode=" + fm.all("RiskCode").value);
}
function button234()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
   var strSQL = "";
   strSQL = "select DECODE(standbyflag1, 'N', '[*]' || fieldname,  fieldname),fieldcode,fieldtype,null,officialdesc,busidesc from Pd_Basefield where tablecode = upper('PD_LMDutyGet') and isdisplay = '1' order by displayorder";
   Mulline9GridTurnPage.pageLineNum  = 3215;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
