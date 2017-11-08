//             该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mOperate="";
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


//进入页面自动查询

function OperationLogQuery() 
{		
	  var sStartDay = document.all('StartDay').value;
		var sEndDay = document.all('EndDay').value;
		var strSQL = ""; 
		
		
  	//alert(strSQL);  
  	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FIRuleDealLogQueryForDownLoadSql"); //指定使用的properties文件名
		mySql1.setSqlId("FIRuleDealLogQueryForDownLoadSql1");//指定使用的Sql的id
		mySql1.addSubPara(document.all('StartDay').value);//指定传入的参数
		mySql1.addSubPara(document.all('EndDay').value);//指定传入的参数
		strSQL= mySql1.getString();
  		
  	turnPage.queryModal(strSQL, FIRuleDealLogQueryForDownLoadGrid);
  	
  	strSQL = "select CheckSerialNo,VersionNo,RuleDealBatchNo,DataSourceBatchNo,CallPointID,RuleDealResult,RulePlanID from FIRuleDealLog where 1=1 ";
  	if(document.all('StartDay').value != '')
  	{
  		strSQL = strSQL + " and RuleDealDate >= '"+document.all('StartDay').value+"' ";
  	}
  	if(document.all('EndDay').value != '')
  	{
  		strSQL = strSQL + " and RuleDealDate <= '"+document.all('EndDay').value+"' ";
  	}
} 

function DownloadAddress()
{
	if((document.all('CheckSerialNo1').value == '')||(document.all('CheckSerialNo1').value == null))
	{
		alert("请先在校验日志信息查询结果中选择一条记录再做该操作！");
    return ;
	}
	Querydiv.style.display='';
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


//增加、删除、修改提交前的校验、计算
function beforeSubmit()
{			
  if (!verifyInput2())
  {
  	return false;
  }
    
    return true;
}




//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}


//选中mulline中一行，自动给输入框赋值
 function ReturnData()
{
 	var arrResult = new Array();

	var tSel = FIRuleDealLogQueryForDownLoadGrid.getSelNo();	
	var strSQL="";
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录!" );
		return;
	}			
	else
	{
	//设置需要返回的数组
	// 书写SQL语句		
  	var strSQL = "";
  	/**
		strSQL = "select a.CheckSerialNo,a.RulePlanID,a.LogFileName,a.LogFilePath from FIRuleDealLog a where a.CheckSerialNo='"+
	          FIRuleDealLogQueryForDownLoadGrid.getRowColData(tSel-1,1)+"'";
	          */
		var mySql2=new SqlClass();
			mySql2.setResourceName("fininterface.FIRuleDealLogQueryForDownLoadSql"); //指定使用的properties文件名
			mySql2.setSqlId("FIRuleDealLogQueryForDownLoadSql2");//指定使用的Sql的id
			mySql2.addSubPara(FIRuleDealLogQueryForDownLoadGrid.getRowColData(tSel-1,1));//指定传入的参数
			strSQL= mySql2.getString();
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
		
		
  //判断是否查询成功
  if (!turnPage.strQueryResult) 
  {
    alert("查询失败！");
    return false;
  }
  
	//查询成功则拆分字符串，返回二维数组
  	arrResult = decodeEasyQueryResult(turnPage.strQueryResult);  	
  	document.all('CheckSerialNo').value = arrResult[0][0];
  	document.all('RulePlanID').value = arrResult[0][1];
  	document.all('CheckSerialNo1').value = arrResult[0][0];
  	fileUrl.href = arrResult[0][3]+arrResult[0][2]; 
  	alert(fileUrl.href);
    fileUrl.innerText =  arrResult[0][0];  	  
	}	 
}

