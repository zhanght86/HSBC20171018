//�������ƣ�PublRule.js
//�����ܣ��㷨�������
//�������ڣ�2009-3-17
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();
var showInfo;	

function returnParent()
{		
	top.opener.focus();		
	top.close();
}

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
var Mulline9GridTurnPage = new turnPageClass(); 
var Mulline10GridTurnPage = new turnPageClass(); 
var Mulline11GridTurnPage = new turnPageClass(); 
function save()
{
 if( fm.all('AlgoCode').value == '' || fm.all('AlgoCode').value == null )
 {
 	myAlert(""+"�㷨���벻��Ϊ�գ�"+"");
 	return;
 }
 if( fm.all('AlgoCode').value.toUpperCase().indexOf('U') < 0 )
 {
 	myAlert(""+"�㷨���������U��ͷ��"+"");
 	return;
 }
 if( fm.all('AlgoCode').value.length > 6 )
 {
 	myAlert(""+"�㷨�������С��6λ��"+"");
 	return;
 }
 if( fm.all('AlgoContent').value == '' || fm.all('AlgoContent').value == null )
 {
 	myAlert(""+"�㷨���ݲ���Ϊ�գ�"+"");
 	return;
 } 
 fm.all("operator").value="save";
 submitForm();
}
function update()
{
 var selNo = Mulline9Grid.getSelNo();
 if(selNo < 0)
 {  
  	myAlert(""+"��ѡ��Ҫ�޸ĵļ�¼��"+"");
  	return;
 }
 if( fm.all('AlgoCode').value == '' || fm.all('AlgoCode').value == null )
 {
 	myAlert(""+"�㷨���벻��Ϊ�գ�"+"");
 	return;
 }
 if( fm.all('AlgoCode').value.toUpperCase().indexOf('U') < 0 )
 {
 	myAlert(""+"�㷨���������U��ͷ��"+"");
 	return;
 }
 if( fm.all('AlgoCode').value.length > 6 )
 {
 	myAlert(""+"�㷨�������С��6λ��"+"");
 	return;
 }
 if( fm.all('AlgoContent').value == '' || fm.all('AlgoContent').value == null )
 {
 	myAlert(""+"�㷨���ݲ���Ϊ�գ�"+"");
 	return;
 } 
 fm.all("operator").value="update";
 submitForm();
}
function del()
{
 fm.all("operator").value="del";
 submitForm();
}
function queryAlgoTemp()
{
  showInfo = window.open("PDAlgoTempLibQueryMain.jsp");
}
function button326()
{
  showInfo = window.open("");
}
function query()
{
  var urlPara = "";
  if(fm.all("AlgoType").value != null)
  {
  	urlPara = "?algotype=" + fm.all("AlgoType").value;
  }
  
  showInfo = window.open("PDCalFactorQueryMain.jsp" + urlPara);
}
function button330()
{
  if(Mulline10Grid.getSelNo() == 0)
  {
  	myAlert(""+"��ѡ��ĳ�У��ٵ���ð�ť"+"");
  	return;
  }
  
  Mulline10Grid.delRadioTrueLine("Mulline10Grid");
}

function add()
{
  if(fm.all("SubAlgoCode").value == "" || fm.all("SubAlgoCode").value == null)
  {
  	myAlert(""+"���㷨���벻��Ϊ��"+"");
  	return;
  }
  if(fm.all("SubAlgoName").value == "" || fm.all("SubAlgoName").value == null)
  {
  	myAlert(""+"���㷨���Ʋ���Ϊ��"+"");
  	return;
  }
   if(fm.all("AlgoCode").value == "")
  {
  	myAlert(""+"�㷨���벻��Ϊ��"+"");
  	return;
  }
  
  fm.all("operator").value = "add";
  fm.action = "PDAlgoDefiSave.jsp";
  submitForm();
}

function subAlgoDefi()
{
if(fm.all("AlgoCode").value == "")
  {
  	myAlert(""+"�㷨���벻��Ϊ��"+"");
  	return;
  }
  
  var selNo = Mulline11Grid.getSelNo();
  
  if(selNo == 0)
  {
  	myAlert(""+"����ѡ��ĳ��"+"");
  	return;
  }
  showInfo = window.open("PDSubAlgoDefiMain.jsp?riskcode="+fm.all("RiskCode").value+"&algocode=" + fm.all("AlgoCode").value + "&subalgocode=" + Mulline11Grid.getRowColData(selNo-1,3) + "&subalgoname=" + Mulline11Grid.getRowColData(selNo-1,2) + "&subalgograde=" + Mulline11Grid.getRowColData(selNo-1,7));
}

function test()
{
  fm.all("operator").value = "test";
  fm.action = "PDAlgoDefiSave.jsp";
  submitForm();
}

function button332()
{
  showInfo = window.open("");
}

function queryMulline9Grid()
{
   var strSQL = "";
   strSQL = "select a.calcode,a.riskcode,a.type,a.Calsql,a.Remark,b.codename from Pd_Lmcalmode a ,ldcode b where a.type = b.code and b.codetype = 'algotemptype' and Riskcode = '000000'";
   Mulline9GridTurnPage.pageLineNum  = 10;
   Mulline9GridTurnPage.queryModal(strSQL,Mulline9Grid);
}
function queryMulline10Grid()
{
   var strSQL = "";
   strSQL = "";
   Mulline10GridTurnPage.pageLineNum  = 10;
   Mulline10GridTurnPage.queryModal(strSQL,Mulline10Grid);
}
function queryMulline11Grid()
{
   var strSQL = "";
   strSQL = "select a.Factortype,a.Factorname,a.factorcode,null,null,factortype,factorgrade from PD_Lmcalfactor a where calcode = '" + fm.all("AlgoCode").value +"'";   
   Mulline11GridTurnPage.pageLineNum  = 10;
   Mulline11GridTurnPage.queryModal(strSQL,Mulline11Grid);
}


function afterQueryAlgo(AlgoCode)
{
	var sql = "select a.code,a.name,a.content,a.type,b.codename from Pd_Algotemplib a left outer join ldcode b on a.type = b.code and b.codetype = 'algotemptype' where a.code = '" + AlgoCode + "'"; 
	var arr = easyExecSql(sql);
	
	if(arr != null)
	{
		//fm.all("AlgoCode").value = arr[0][0];
		fm.all("AlgoDesc").value = arr[0][1];
		fm.all("AlgoContent").value = arr[0][2];
		//fm.all("AlgoType").value = arr[0][3];	
		//fm.all("AlgoTypeName").value = arr[0][4];			
	}
}

function afterQueryFactor(dicSelectedFactors)
{
	var mulLineCurrentCount = Mulline10Grid.mulLineCount;

	a = (new VBArray(dicSelectedFactors.Items())).toArray();   //��ȡ��Ŀ��
   	s = "";
   	for (ii in a)                  //������ dictionary��
   	{
   		Mulline10Grid.addOne();
   		Mulline10Grid.setRowColData(parseInt(ii) + mulLineCurrentCount,1,a[ii][0]);
   		Mulline10Grid.setRowColData(parseInt(ii) + mulLineCurrentCount,3,a[ii][3]);
 		Mulline10Grid.setRowColData(parseInt(ii) + mulLineCurrentCount,5,a[ii][4]);
   		Mulline10Grid.setRowColData(parseInt(ii) + mulLineCurrentCount,6,a[ii][5]);
   	}
}


