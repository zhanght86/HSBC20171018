//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  document.getElementById("fm").submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("在AgentQuery.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           

//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
}           

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
  //下面增加相应的代码
  alert("update click");
}           

//Click事件，当点击“查询”图片时触发该函数
function queryClick()
{
  //下面增加相应的代码
  //window.showModalDialog("./ProposalQuery.jsp",window,"status:0;help:0;edge:sunken;dialogHide:0;dialogWidth=15cm;dialogHeight=12cm");
  //查询命令单独弹出一个模态对话框，并提交，和其它命令是不同的
  //因此，表单中的活动名称也可以不用赋值的
}           

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
  //下面增加相应的代码
  alert("delete click");
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
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = AgentGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{			
			arrReturn = getQueryResult();                        
			top.opener.afterQuery2( arrReturn );
			top.close();			 
		}
		catch(ex)
		{
			alert( "要返回的页面函数出错！");
		}		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = AgentGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();	
//	var strSQL = "select a.agentcom, a.managecom, a.name, a.address, a.zipcode, a.phone from lacom a where 1=1 "
//	           + "and a.agentcom='"+AgentGrid.getRowColData(tRow-1,1)+"'"; 
	
	    var sqlid1="AgentComQuerySql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("sys.AgentComQuerySql");
	 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
	 	mySql1.addSubPara(AgentGrid.getRowColData(tRow-1,1));//指定传入参数
	 	var strSQL = mySql1.getString();
	
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

// 查询按钮
function easyQueryClick()
{ 
	initAgentGrid();
	
	// 书写SQL语句
	var strSQL = "";
//	var strOperate = "like";
//	strSQL = " select a.agentcom, a.managecom, a.name, a.address, a.zipcode, a.phone from lacom a where 1=1 "                  
//	         + getWherePart('a.agentcom','AgentCom',strOperate)
//	         + getWherePart('a.ManageCom','ManageCom',strOperate)
//	         + getWherePart('a.Name','Name',strOperate)	        
//	         + getWherePart('a.Address','Address',strOperate)
//	         + getWherePart('a.ZipCode','ZipCode',strOperate)
//	         + getWherePart('a.Phone','Phone',strOperate);  	
//	
	    var sqlid2="AgentComQuerySql2";
	 	var mySql2=new SqlClass();
	 	mySql2.setResourceName("sys.AgentComQuerySql");
	 	mySql2.setSqlId(sqlid2); //指定使用SQL的id
	 	mySql2.addSubPara(window.document.getElementsByName(trim("AgentCom"))[0].value);//指定传入参数
	 	mySql2.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql2.addSubPara(window.document.getElementsByName(trim("Name"))[0].value);
	 	mySql2.addSubPara(window.document.getElementsByName(trim("Address"))[0].value);
	 	mySql2.addSubPara(window.document.getElementsByName(trim("ZipCode"))[0].value);
	 	mySql2.addSubPara(window.document.getElementsByName(trim("Phone"))[0].value);
	 	strSQL = mySql2.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("未查询到满足条件的数据！");
    return false;
    }
//查询成功则拆分字符串，返回二维数组
  arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.arrDataCacheSet = arrDataSet;
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = AgentGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var tArr = new Array();
  tArr = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(tArr, turnPage.pageDisplayGrid);
}


// 投保单信息查询
function ProposalClick()
{
	var arrReturn = new Array();
	var tSel = AgentGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
	    var cCustomerNo = AgentGrid.getRowColData(tSel - 1,1);				
		
		if (cCustomerNo == "")
		    return;		    
		    var cName = AgentGrid.getRowColData(tSel - 1,4);
			window.open("../sys/ProposalQuery.jsp?CustomerNo=" + cCustomerNo + "&Name=" + cName + "&Flag=Agent");	
	}
}

// 保单信息查询
function PolClick()
{
	var arrReturn = new Array();
	var tSel = AgentGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
	    var cCustomerNo = AgentGrid.getRowColData(tSel - 1,1);			
		if (cCustomerNo == "")
		    return;
		    var cName = AgentGrid.getRowColData(tSel - 1,4);
		    window.open("../sys/PolQueryMain.jsp?CustomerNo=" + cCustomerNo + "&Name=" + cName + "&Flag=Agent");	
	}
}

//销户保单信息查询
function DesPolClick()
{
	var arrReturn = new Array();
	var tSel = AgentGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
	    var cCustomerNo = AgentGrid.getRowColData(tSel - 1,1);				
		if (cCustomerNo == "")
		    return;		    
		    var cName = AgentGrid.getRowColData(tSel - 1,4);
		    window.open("../sys/DesPolQueryMain.jsp?CustomerNo=" + cCustomerNo + "&Name=" + cName + "&Flag=Agent");	
	}
}
