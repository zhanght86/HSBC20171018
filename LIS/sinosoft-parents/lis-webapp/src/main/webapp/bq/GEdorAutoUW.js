//程序名称：GedorAutoUW.js
//程序功能：团单保全自动核保
//创建日期：
//创建人  ：
//更新记录：  更新人 ZhangRong   更新日期   2004.12  更新原因/内容 Upgrade

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

//提交，保存按钮对应操作
function submitForm()
{
	var i = 0;
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	initPGrpEdorMainGrid();
	fm.submit(); //提交
}

//自动核保保全申请
function edorAutoUW()
{
	var tGrpContNo ;
	var tEdorNo;
	tGrpContNo = document.all('GrpContNo').value;
	tEdorNo = document.all('EdorNo').value;
	
	if (window.confirm("是否核保本次申请?"))
	{
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
		fm.submit();
	}
}

//人工核保保全
function edorManuUW()
{
	alert("waiting....");
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  initForm();
  easyQueryClick();
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
  	alert("在Proposal.js-->resetForm函数中发生异常:初始化界面错误!");
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

// 查询按钮
function easyQueryClick()
{
	var EdorState;
	
	tEdorState='2';
	
	// 初始化表格
	initPGrpEdorMainGrid();
	
	// 书写SQL语句
	var strSQL = "";
	var tReturn = parseManageComLimit();
	var tGrpContNo = document.all('GrpContNo').value;
	var tEdorNo = document.all('EdorNo').value;	
//	strSQL = "select EdorNo,GrpContNo,EdorAppDate,EdorValiDate,GetMoney from LPGrpEdorMain where EdorState='" + tEdorState + "' and "+ tReturn
//				 + " and UWState in ('0')"
//				 + getWherePart( 'GrpContNo' )
//				 + getWherePart('EdorNo');				 
	var sqlid1="GEdorAutoUwSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.GEdorAutoUwSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tEdorState);//指定传入的参数
	mySql1.addSubPara(tReturn);//指定传入的参数
	mySql1.addSubPara(tGrpContNo);//指定传入的参数
	mySql1.addSubPara(tEdorNo);//指定传入的参数
	strSQL=mySql1.getString();	
	//查询SQL，返回结果字符串
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
	  
	//判断是否查询成功
	if (!turnPage.strQueryResult) 
	{
		alert("未查询到待自动核保的批单！");
	}
	else
	{
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);

		//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
		turnPage.pageDisplayGrid = PGrpEdorMainGrid;    
			  
		//保存SQL语句
		turnPage.strQuerySql     = strSQL; 

		//设置查询起始位置
		turnPage.pageIndex       = 0;  

		//在查询结果数组中取出符合页面显示大小设置的数组
		var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		arrGrid = arrDataSet;
		//调用MULTILINE对象显示查询结果
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}
}

//查询明细信息
function detailEdor()
{
	var arrReturn = new Array();
	var tSel = PGrpEdorMainGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击查看按钮。" );
	else
	{
		arrReturn = getQueryResult();
		document.all('EdorType').value =arrReturn[0][2];
		document.all('EdorNo').value = arrReturn[0][0];
		document.all('GrpContNo').value=arrReturn[0][1];
		detailEdorType();
	}
}
	
function getQueryResult()
{
	var arrSelected = null;
	tRow = PGrpEdorMainGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}
