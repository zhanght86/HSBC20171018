
//Creator :���	
//Date :2008-09-09

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();
window.onfocus=myonfocus;

//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null) //shwoInfo��ʲô��
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}


//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
  function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else 
 	{
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

function VersionStateQuery()
{
	showInfo=window.open("./FrameVersionRuleQuery.jsp");
}

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();
		//alert(arrQueryResult);

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('VersionNo').value = arrResult[0][0];
		document.all('VersionState').value = arrResult[0][5];
		document.all('VersionState2').value = arrResult[0][9];
		
		//���汾״̬��Ϊ01-��ɵ�ʱ����ɾ�İ�ťΪ��ɫ		
		if (arrResult[0][5] == "02"||arrResult[0][5] == "03"||arrResult[0][5] == ""||arrResult[0][5] == null)
		{
			document.all('DealErrdataButton').disabled = true;				
		}
		if (arrResult[0][5] == "01")
		{
			document.all('DealErrdataButton').disabled = false;				
		}
		
		document.all('RuleDealBatchNo').value = '';
		document.all('DataSourceBatchNo').value = '';
		document.all('CallPointID').value = '';
		document.all('RuleDealResult').value = '';
		document.all('DealOperator').value = '';
		document.all('RulePlanID').value = '';
		document.all('RuleDealDate').value = '';
		document.all('LogFilePath').value = '';
		document.all('LogFileName').value = '';
		
		//������Common\javascript\Common.js�����ݴ���ѡ��Ĵ�����Ҳ���ʾ����
		showCodeName(); 

	}
}
function queryResult()
{
	var VersionNo = document.all('VersionNo').value;
	if (VersionNo == null||VersionNo == '')
  {
  	alert("���Ƚ��а汾��Ϣ��ѯ��Ȼ���ٽ���У����־��Ϣ��ѯ");
  	return;
  }
  showInfo=window.open("./FrameFIRuleDealLogInput.jsp?VersionNo=" + VersionNo+"");
}

function DealErrdata()
{
	var RuleDealBatchNo = document.all('RuleDealBatchNo').value;
	var DataSourceBatchNo = document.all('DataSourceBatchNo').value;
	var RuleDealResult = document.all('RuleDealResult').value;
	var RulePlanID = document.all('RulePlanID').value;
	
	if(RuleDealBatchNo == null||RuleDealBatchNo == '')
	{
		alert("���Ƚ���У����־��Ϣ��ѯ��Ȼ���ٽ��д������ݴ���");
  	return;
	}
	if(DataSourceBatchNo == null||DataSourceBatchNo == '')
	{
		alert("���Ƚ���У����־��Ϣ��ѯ��Ȼ���ٽ��д������ݴ���");
  	return;
	}
	//if(RuleDealResult != 'Fail')
	//{
	//	alert("ֻ��У����Ϊʧ�ܵļ�¼���ܽ��д������ݴ���");
  	//return;
	//}
	showInfo=window.open("./FrameFIRuleDealErrLogInput.jsp?RuleDealBatchNo="+RuleDealBatchNo+"&DataSourceBatchNo="+DataSourceBatchNo+"&RulePlanID=" +RulePlanID);
}

function afterQuery1(arrQueryResult1)
{
	var arrResult1 = new Array();

	if(arrQueryResult1)
	{
		arrResult1 = arrQueryResult1;
		document.all('RuleDealBatchNo').value = arrResult1[0][0];
		document.all('DataSourceBatchNo').value = arrResult1[0][1];
		document.all('CallPointID').value = arrResult1[0][2];
		document.all('RuleDealResult').value = arrResult1[0][3];
		document.all('DealOperator').value = arrResult1[0][4];
		document.all('RulePlanID').value = arrResult1[0][5];
		document.all('RuleDealDate').value = arrResult1[0][6];
		document.all('LogFilePath').value = arrResult1[0][7];
		document.all('LogFileName').value = arrResult1[0][8]
	}
}



  