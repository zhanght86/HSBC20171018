//               该文件中包含客户端需要处理的函数和事件

//Creator :范昕	
//Date :2008-08-18

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();

//初始化页面
function queryFIRulePlanDefDetailInputGrid()
{
	try
	{

		initFIRulePlanDefDetailInputGrid();
		var strSQL = ""; 
		/**
		strSQL = "select VersionNo,RulePlanID,RuleID,Sequence,RuleState from FIRulePlanDefDetail where ";
		strSQL = strSQL + " VersionNo ='"+VersionNo+"' and RulePlanID ='"+RulePlanID+"' order by Sequence ASC ";
  		*/
  		var mySql1=new SqlClass();
			mySql1.setResourceName("fininterface.FIRulePlanDefDetailInputSql"); //指定使用的properties文件名
			mySql1.setSqlId("FIRulePlanDefDetailInputSql1");//指定使用的Sql的id
			mySql1.addSubPara(VersionNo);//指定传入的参数
			mySql1.addSubPara(RulePlanID);//指定传入的参数
			strSQL= mySql1.getString();
  	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  	if (!turnPage.strQueryResult)
		{
			return false;
		}
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		turnPage.pageLineNum = 30 ;
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象
		turnPage.pageDisplayGrid = FIRulePlanDefDetailInputGrid;
		//保存SQL语句
		turnPage.strQuerySql = strSQL ;
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
		//调用MULTILINE对象显示查询结果
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  }
  
  	catch(Ex)
	{
		alert(Ex.message);
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



//Click事件，当点击“添加”图片时触发该函数
function submitForm()
{
	 if(!beforeSubmit())
	{
		return false;
	}
	
  fm.OperateType.value = "INSERT||MAIN";
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.action = './FIRulePlanDefDetailSave.jsp';
  fm.submit(); //提交
}

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
	if(!beforeSubmit2())
	{
		return false;
	}
	if(!beforeSubmit())
	{
		return false;
	}
	if(fm.RuleID.value != fm.RuleID1.value)
  {
  	alert("请确定您录入的校验规则编号！！！");
    return false;
  }
  if (confirm("您确实想修改该记录吗?"))
   {
    fm.OperateType.value = "UPDATE||MAIN";
    var i = 0;
    var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './FIRulePlanDefDetailSave.jsp';
    fm.submit(); //提交
  }
  else
  {
    fm.OperateType.value = "";
    alert("您取消了修改操作！");
  }
}

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
	if(!beforeSubmit2())
	{
		return false;
	}
	if((fm.RuleID.value=="")||(fm.RuleID.value=="null"))
  {
    alert("校验规则编号不得为空！！！");
    return false;
  }	
  if(fm.RuleID.value != fm.RuleID1.value)
  {
  	alert("请确定您录入的校验规则编号！！！");
    return false;
  }
  if (confirm("您确实要删除该记录吗？"))
  {
    fm.OperateType.value = "DELETE||MAIN";
    var i = 0;
    var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或连接其他的界面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './FIRulePlanDefDetailSave.jsp';
    fm.submit();//提交
  }
  else
  {
    fm.OperateType.value = "";
  }
}


function ReturnData()
{
  var arrReturn = new Array();
	var tSel = FIRulePlanDefDetailInputGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		
			try
			{	
				arrReturn = getQueryResult();
				afterQuery( arrReturn );

				//alert(3);
			}
			catch(ex)
			{
				alert( "没有发现父窗口的afterQuery接口。" + ex );
			}
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = FIRulePlanDefDetailInputGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	var strSQL = "";
	/**
	strSQL = "select VersionNo,RulePlanID,RuleID,Sequence,RuleState from FIRulePlanDefDetail where 1=1 "
	       + "and VersionNo='"+FIRulePlanDefDetailInputGrid.getRowColData(tRow-1,1)+"' and RulePlanID='"+FIRulePlanDefDetailInputGrid.getRowColData(tRow-1,2)+"' and RuleID= '"+FIRulePlanDefDetailInputGrid.getRowColData(tRow-1,3)+"'" ; 
	*/
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FIRulePlanDefDetailInputSql"); //指定使用的properties文件名
		mySql2.setSqlId("FIRulePlanDefDetailInputSql2");//指定使用的Sql的id
		mySql2.addSubPara(FIRulePlanDefDetailInputGrid.getRowColData(tRow-1,1));//指定传入的参数
		mySql2.addSubPara(FIRulePlanDefDetailInputGrid.getRowColData(tRow-1,2));//指定传入的参数
		mySql2.addSubPara(FIRulePlanDefDetailInputGrid.getRowColData(tRow-1,3));//指定传入的参数
		strSQL= mySql2.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败！");
    return false;
    }
//查询成功则拆分字符串，返回二维数组
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	return arrSelected;
}

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		fm.all('RuleID').value = arrResult[0][2];
		fm.all('RuleID1').value = arrResult[0][2];
		fm.all('Sequence').value = arrResult[0][3];
		fm.all('RuleState').value = arrResult[0][4];
		//fm.all('RuleID').disabled = true;	
		//fm.all('RuleIDName').disabled = true;	
		showCodeName(); 
		
	}
	
}
function initFIRulePlanDefDetailInputQuery()
{
	try
	{
  	var strSQL = "";
  	/**
		strSQL = "select VersionNo,RulePlanID,RuleID,Sequence,RuleState from FIRulePlanDefDetail where ";
		strSQL = strSQL + " VersionNo ='"+VersionNo+"' and RulePlanID ='"+RulePlanID+"' order by Sequence ASC ";
  	*/
  	var mySql3=new SqlClass();
		mySql3.setResourceName("fininterface.FIRulePlanDefDetailInputSql"); //指定使用的properties文件名
		mySql3.setSqlId("FIRulePlanDefDetailInputSql3");//指定使用的Sql的id
		mySql3.addSubPara(VersionNo);//指定传入的参数
		mySql3.addSubPara(RulePlanID);//指定传入的参数
		strSQL= mySql3.getString();
  	turnPage.queryModal(strSQL, FIRulePlanDefDetailInputGrid);
		
	}
	
	catch(Ex)
	{
		alert(Ex.message);
	}
}
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //释放“增加”按钮

  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		fm.OperateType.value="";
  }
  else
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

		if(fm.OperateType.value == "DELETE||MAIN")
		{
			fm.all('RuleID').value = '';  
			fm.all('RuleID1').value = '';       
    	fm.all('RuleIDName').value = '';
    	fm.all('Sequence').value = '';    
    	fm.all('RuleState').value = '';   
    	fm.all('RuleStateName').value = '';  
		}
    fm.OperateType.value="";
    initFIRulePlanDefDetailInputQuery();
  }
}

function beforeSubmit()
{			
  if((fm.RuleID.value=="")||(fm.RuleID.value=="null"))
  {
    alert("校验规则编号不得为空！！！");
    return false;
  }	
  if((fm.RuleState.value=="")||(fm.RuleState.value=="null"))
  {
    alert("校验规则状态不得为空！！！");
    return false;
  }	
    return true;
}


//删除和修改前必须选中一条记录
function beforeSubmit2()
{		
	var tSel = FIRulePlanDefDetailInputGrid.getSelNo();	
	var strSQL="";
	if( tSel == 0 || tSel == null )
	{
		alert( "请您先选择一条记录，再进行修改和删除的操作!" );
		return;
	}
    return true;
}

function resetAgain()
{
			fm.all('RuleID').value = '';  
			fm.all('RuleID1').value = '';       
    	fm.all('RuleIDName').value = '';
    	fm.all('Sequence').value = '';    
    	fm.all('RuleState').value = '';   
    	fm.all('RuleStateName').value = '';
    	
    	
}