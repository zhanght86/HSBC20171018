//该文件中包含客户端需要处理的函数和事件
var showInfo;
var GEdorFlag = true;
var selGridFlag = 0;            //标识当前选中记录的GRID

//该文件中包含客户端需要处理的函数和事件
var sqlresourcename = "bq.PEdorTypeTIDetailInputSql"; 
var turnPage1 = new turnPageClass();
var turnPage = new turnPageClass();           //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage3 = new turnPageClass();          //使用翻页功能，必须建立为全局变量
//重定义获取焦点处理事件
//window.onfocus = initFocus;

//查询按钮
function queryClick()
{
	var tPolNo=document.all('PolNo').value;
	var strSql="";
	var tStartDate="";
    var sqlid1="PEdorTypeTIDetailInputSql1";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tPolNo);
	mySql1.addSubPara(fm.EdorItemAppDate.value);//指定传入的参数
	strSql=mySql1.getString();
	var arrResult=easyExecSql(strSql);
	
	if(arrResult==null||arrResult=="")
	{

	    var sqlid2="PEdorTypeTIDetailInputSql2";
		
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(tPolNo);
		mySql2.addSubPara(fm.EdorItemAppDate.value);//指定传入的参数
		var strSql2=mySql2.getString();
          
		arrResult=easyExecSql(strSql2);
	}
	//alert(strSql);
	tStartDate=arrResult[0][0];
	

   var sqlid3="PEdorTypeTIDetailInputSql3";
	
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid3);//指定使用的Sql的id
	mySql3.addSubPara(tStartDate);
	mySql3.addSubPara(fm.PolNo.value);//指定传入的参数
	var strSql3=mySql3.getString();
	turnPage1.pageDivName = "divPage1";
	turnPage1.queryModal(strSql3, LCGrpInsuAccGrid);
	

    var sqlid4="PEdorTypeTIDetailInputSql4";
	
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql4.setSqlId(sqlid4);//指定使用的Sql的id
	mySql4.addSubPara(fm.EdorNo.value);//指定传入的参数
	var strSql4=mySql4.getString();
          
	var arrResult=easyExecSql(strSql4);
	
	if(arrResult==null||arrResult=="")
	{
	  divLCInsuAcc.style.display="";
	}
	else
	{
		turnPage3.pageDivName = "divPage3";
		turnPage3.queryModal(strSql4, LPInsuAccGrid); 
		//divLCInsuAcc.style.display="none";
	}
	queryClick2();
}



//查询按钮
function queryClick2()
{
	var tPolNo=document.all('PolNo').value;
	var strSql="";
	var tStartDate="";
    var sqlid5="PEdorTypeTIDetailInputSql5";
	
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql5.setSqlId(sqlid5);//指定使用的Sql的id
	mySql5.addSubPara(tPolNo);
    mySql5.addSubPara(fm.EdorItemAppDate.value);
	var strSql=mySql5.getString();
          
	var arrResult=easyExecSql(strSql);
	
	if(arrResult==null||arrResult=="")
	{

	    var sqlid6="PEdorTypeTIDetailInputSql6";
		
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(tPolNo);
		mySql6.addSubPara(fm.EdorItemAppDate.value);//指定传入的参数
		var strSql6=mySql6.getString();
          
		arrResult=easyExecSql(strSql6);
	}
	
	tStartDate=arrResult[0][0];
	

   var sqlid7="PEdorTypeTIDetailInputSql7";
	
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql7.setSqlId(sqlid7);//指定使用的Sql的id
	mySql7.addSubPara(fm.EdorNo.value);//指定传入的参数
	var strSql=mySql7.getString();
          
	turnPage3.pageDivName = "divPage3";
	turnPage3.queryModal(strSql, TempOutInsuAccGrid); 
	var arrResult=easyExecSql(strSql);
	if(arrResult==null||arrResult=="")
	{
		
	}
    else
	{
		showInsuAccIn("");
	}
}

function reportDetail2Click()
{
   var selno = TempOutInsuAccGrid.getSelNo()-1;
   if (selno <0)
   {
          alert("请选择要处理的转出记录！");
          return;
   }
   var tCurrency = TempOutInsuAccGrid.getRowColData(selno,8);
   showInsuAccIn(tCurrency);
}

//个人账户转换
function DealAccIn()
{  
	
		//alert("111");
//   if (LCInsuAccGrid.getSelNo()==0)
//  {
    //alert("请选择需要转入的帐户!");
    //return;
  //}
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");     
  var name='提示';   //网页名称，可为空; 
var iWidth=550;      //弹出窗口的宽度; 
var iHeight=250;     //弹出窗口的高度; 
var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  document.all('Transact').value = "INSERT||DEALIN";
  fm.action = "./PEdorTypeTIDetailSubmit.jsp";
  fm.submit();
}

//撤销个人保全
function cancelAccIn()
{
 if (divLCInsuAcc.style.display=="")
  {
    alert("没有需要撤销的历史转入信息!");
    return;
  }

  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");     
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();
  document.all('Transact').value = "DELETE||CANCELIN";
  fm.action = "./PEdorTypeTIDetailSubmit.jsp";
  fm.submit();
}


//

function cancelAccOut()
{
 if (TempOutInsuAccGrid.getSelNo()==0)
  {
    alert("请选择需要撤销的转出记录!");
    return;
  }
  fm.InsuAccNo.value=TempOutInsuAccGrid.getRowColData(TempOutInsuAccGrid.getSelNo()-1,3);
	//fm.PayPlanCode.value=TempOutInsuAccGrid.getRowColData(TempOutInsuAccGrid.getSelNo()-1,4);
	fm.PolNoDO.value=TempOutInsuAccGrid.getRowColData(TempOutInsuAccGrid.getSelNo()-1,2);

	//alert(fm.InsuAccNo.value+"@@@"+fm.PayPlanCode.value);
	//return;
	
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");     
  var name='提示';   //网页名称，可为空; 
var iWidth=550;      //弹出窗口的宽度; 
var iHeight=250;     //弹出窗口的高度; 
var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();
  document.all('Transact').value = "DELETE||CANCELOUT";
  fm.action = "./PEdorTypeTIDetailSubmit.jsp";
  fm.submit();
}

//显示
function showInsuAccIn(xCurrency)
{

	initLCInsuAccGrid();
	
	var tPolNo=document.all('PolNo').value;
	var strSql="";
	var tStartDate="";
var sqlid1="PEdorTypeTIDetailInputSql8";
	
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(tPolNo);
	mySql1.addSubPara(fm.EdorItemAppDate.value);//指定传入的参数
	var strSql=mySql1.getString();
          
	var arrResult=easyExecSql(strSql);
	
	if(arrResult==null||arrResult=="")
	{
		var sqlid1="PEdorTypeTIDetailInputSql9";
	
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql2.setSqlId(sqlid1);//指定使用的Sql的id
	mySql2.addSubPara(tPolNo);
	mySql2.addSubPara(fm.EdorItemAppDate.value);//指定传入的参数
	var strSql=mySql2.getString();
          
		arrResult=easyExecSql(strSql);
	}
	//alert(arrResult);
	tStartDate=arrResult[0][0];
	
	
	//alert("001");
var sqlid1="PEdorTypeTIDetailInputSql10";
	
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //指定使用的properties文件名
	mySql3.setSqlId(sqlid1);//指定使用的Sql的id
	mySql3.addSubPara(tStartDate);
	mySql3.addSubPara(fm.PolNo.value);//指定传入的参数
	mySql3.addSubPara(xCurrency);
	var strSql=mySql3.getString();

	turnPage2.pageDivName = "divPage2";
	turnPage2.queryModal(strSql, LCInsuAccGrid); 
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content, Result)
{
	showInfo.close();
	if (FlagStr == "Fail")
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
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		//initLCGrpInsuAccGrid();
		//initLCInsuAccGrid();
    //initLPInsuAccGrid();
		//queryClick();
		
	}
initForm();
//alert("aaa");
}

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug) {
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

function returnParent() {
	top.close();
	//top.opener.initLPInsuAccGrid();
	top.opener.QueryBussiness();
}
function QueryEdorInfo()
{
	var tEdortype=document.all('EdorType').value
	if(tEdortype!=null || tEdortype !='')
	{
	    var sqlid1="PEdorTypeTIDetailInputSql11";
		
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(tEdortype);
		var strSql=mySql1.getString();
    }
    else
	{
		alert('未查询到保全批改项目信息！');
	}
	
	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1);
	arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
    //try {document.all('EdorType').value = arrSelected[0][0];} catch(ex) { }; 
    try {document.all('EdorTypeName').value = arrSelected[0][1];} catch(ex) { }; 
}












//个人账户转换
function DealAccOut()
{
	//alert("111");
   if (LCGrpInsuAccGrid.getSelNo()==0)
  {
    alert("请选择需要转出的帐户!");
    return;
  }
  //转出单位数
  try {
  	var OutUnit=LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,9);
  	var OutBala=LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,10);
  	
	if((OutUnit==null||OutUnit=='')&&(OutBala==null||OutBala==''))
	{
		alert("请输入转出单位!");
		return;	
	}else if(OutUnit-0<=0)
	{
		alert("请输入大于0的单位数!");
	  	return;	
	}
	//alert(LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,8)+";;"+OutUnit);
	var aaa = OutUnit-LCGrpInsuAccGrid.getRowColData(LCGrpInsuAccGrid.getSelNo()-1,6);
	if(aaa>0)
	{
		alert("转出的单位数不能多于可转出单位!");
	return;	
	}
  } catch(ex) { 
  	alert("请输入正确单位数!");
	return;	
  	}
    	fm.OutUnit.value = OutUnit;
  	//alert(OutUnit);
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");     
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();
  document.all('Transact').value = "INSERT||DEALOUT";
  fm.action = "./PEdorTypeTIDetailSubmit.jsp";
  fm.submit();
}
