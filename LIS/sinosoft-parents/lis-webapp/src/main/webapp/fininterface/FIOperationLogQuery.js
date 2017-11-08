//            该文件中包含客户端需要处理的函数和事件

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
	var strSQL = ""; 
        //alert(document.all('EventType').value);		
        /**
  	strSQL = "select EventNo,EventType,LogFileName,LogFilePath,PerformState,MakeDate,MakeTime from FIOperationLog where 1=1 ";
		if(document.all('StartDay').value != '')
		{
			strSQL = strSQL + " and MakeDate >= '"+document.all('StartDay').value+"' ";
		}
		if(document.all('EndDay').value != '')
		{
			strSQL = strSQL + " and MakeDate <= '"+document.all('EndDay').value+"' ";
		}
		if(document.all('EventClass').value != '')
		{
			strSQL = strSQL + " and EventType = '"+document.all('EventClass').value+"' ";
		}
		if(document.all('EventFlag').value != '')
		{
			strSQL = strSQL + " and PerformState = '"+document.all('EventFlag').value+"' ";
		}		
		*/
		var mySql1=new SqlClass();
			mySql1.setResourceName("fininterface.FIOperationLogQuerySql"); //指定使用的properties文件名
			mySql1.setSqlId("FIOperationLogQuerySql1");//指定使用的Sql的id
			mySql1.addSubPara(1);//指定传入的参数
			mySql1.addSubPara(document.all('StartDay').value);//指定传入的参数
			mySql1.addSubPara(document.all('EndDay').value);//指定传入的参数
			mySql1.addSubPara(document.all('EventClass').value);//指定传入的参数
			mySql1.addSubPara(document.all('EventFlag').value);//指定传入的参数
			strSQL= mySql1.getString();
		
		
		
  	//alert(strSQL);  	
  	turnPage.queryModal(strSQL, FIOperationLogGrid);
} 

function DownloadAddress()
{
	if((document.all('EventNo1').value == '')||(document.all('EventNo1').value == null))
	{
		alert("请先在系统操作日志查询结果中选择一条记录再做该操作！");
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
  else 
  {
    parent.fraMain.rows = "0,0,0,82,*";
  }
}


//选中mulline中一行，自动给输入框赋值
 function ReturnData()
{
 	var arrResult = new Array();

	var tSel = FIOperationLogGrid.getSelNo();	
	var strSQL="";
	if( tSel == 0 || tSel == null )
	{
		alert( "请先选择一条记录!" );
		return;
	}			
	else
	{
	  
		//查询成功则拆分字符串，返回二维数组
	  	arrResult = decodeEasyQueryResult(turnPage.strQueryResult);  	
	  	document.all('EventNo').value = FIOperationLogGrid.getRowColData(tSel-1,1);
	  	document.all('EventType').value = FIOperationLogGrid.getRowColData(tSel-1,2);
	  	document.all('EventNo1').value = FIOperationLogGrid.getRowColData(tSel-1,1);
	  	//fileUrl.href = filepath + FIOperationLogGrid.getRowColData(tSel-1,3); 
	  	//alert(fileUrl.href);
	  	fileUrl.href = "http://localhost:8080/ui/fininterface/log/" + FIOperationLogGrid.getRowColData(tSel-1,3);
	    fileUrl.innerText =  FIOperationLogGrid.getRowColData(tSel-1,1);
	        FIOperationParameterQuery();
	}	 
}

function FIOperationParameterQuery()
{
	var strSQL = ""; 	
	/**	
  	strSQL = "select EventNo,EventType,ParameterType,ParameterMark,ParameterValue from FIOperationParameter"
	         +" where 1=1 "
	         + getWherePart('EventNo','EventNo1')	
	 */     
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FIOperationLogQuerySql"); //指定使用的properties文件名
		mySql2.setSqlId("FIOperationLogQuerySql2");//指定使用的Sql的id
		mySql2.addSubPara(document.all('EventNo1').value);//指定传入的参数
		strSQL= mySql2.getString();        
  	turnPage.queryModal(strSQL, FIOperationParameterGrid);	
}
