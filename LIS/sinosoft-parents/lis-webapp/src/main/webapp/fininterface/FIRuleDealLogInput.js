
//Creator :范昕	
//Date :2008-09-09

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();
window.onfocus=myonfocus;

//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
	if(showInfo!=null) //shwoInfo是什么？
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


//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
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

//显示frmSubmit框架，用来调试
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
		
		//当版本状态不为01-完成的时候，增删改按钮为灰色		
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
		
		//来自于Common\javascript\Common.js，根据代码选择的代码查找并显示名称
		showCodeName(); 

	}
}
function queryResult()
{
	var VersionNo = document.all('VersionNo').value;
	if (VersionNo == null||VersionNo == '')
  {
  	alert("请先进行版本信息查询，然后再进行校验日志信息查询");
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
		alert("请先进行校验日志信息查询，然后再进行错误数据处理");
  	return;
	}
	if(DataSourceBatchNo == null||DataSourceBatchNo == '')
	{
		alert("请先进行校验日志信息查询，然后再进行错误数据处理");
  	return;
	}
	//if(RuleDealResult != 'Fail')
	//{
	//	alert("只有校验结果为失败的记录才能进行错误数据处理");
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



  