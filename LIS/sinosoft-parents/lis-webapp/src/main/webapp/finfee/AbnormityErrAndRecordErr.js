//               该文件中包含客户端需要处理的函数和事件
// 查询按钮
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var turnPage1 = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var queryBug = 1;
var showInfo;
var mDebug="0";

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

//返回
function afterSubmit( FlagStr, content )
{
  showInfo.close();
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
    initForm();
  }
}






function initQuery() 
{
	// 初始化表格
	initPolGrid();
	

// 书写SQL语句			 
//	var strSql = "select bussno,errorcount,errorcontent,(select bponame from BPOServerInfo where BPOServerInfo.Lisoperator = BPOMissionDetailError.operator and bussNoType='"+bussNoType+"') bponame,MakeDate,MakeTime"
//	             +" from BPOMissionDetailError "
//	             +" where bussno='"+TempFeeNo+"'"
//	             +" and bussNoType='"+bussNoType+"'"
//	             +"  order by bussno,errorcount";
	
	var sqlid1="AbnormityErrAndRecordErrSql0";
	var mySql1=new SqlClass();
	mySql1.setResourceName("finfee.AbnormityErrAndRecordErrSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(bussNoType);//指定传入的参数
	mySql1.addSubPara(TempFeeNo);//指定传入的参数
	mySql1.addSubPara(bussNoType);//指定传入的参数
	var strSql=mySql1.getString();
  //prompt("",strSql);
  //alert(strSql);
	//turnPage.queryModal(strSql, PolGrid);
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {  
    //清空MULTILINE，使用方法见MULTILINE使用说明 
    PolGrid.clearData();  
    return false;
  }
  
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSql; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage1.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage1.style.display = "none"; } catch(ex) { }
  }
}


function initErrQuery() 
{
	// 初始化表格
	initErrGrid();
	

// 书写SQL语句			 
//	var strSql = "select errver,errcode,errorcontent from BPOErrLog"
//	             +" where bussno='"+TempFeeNo+"'"
//	             +" and bussNoType='"+bussNoType+"'";
	var sqlid2="AbnormityErrAndRecordErrSql1";
	var mySql2=new SqlClass();
	mySql2.setResourceName("finfee.AbnormityErrAndRecordErrSql"); //指定使用的properties文件名
	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
    mySql2.addSubPara(TempFeeNo);//指定传入的参数
	mySql2.addSubPara(bussNoType);//指定传入的参数
	var strSql=mySql2.getString();
	
  //prompt("",strSql);
  //alert(strSql);
	//turnPage.queryModal(strSql, PolGrid);
	//查询SQL，返回结果字符串
  turnPage1.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  // alert("dddd");

  //判断是否查询成功
  if (!turnPage1.strQueryResult) {  
    //清空MULTILINE，使用方法见MULTILINE使用说明 
    ErrGrid.clearData();  
    return false;
  }

  
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage1.arrDataCacheSet = clearArrayElements(turnPage1.arrDataCacheSet);
  
  
  //查询成功则拆分字符串，返回二维数组
  turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
  
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage1.pageDisplayGrid = ErrGrid;       
          
  //保存SQL语句
  turnPage1.strQuerySql     = strSql; 
  
  //设置查询起始位置
  turnPage1.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage1.getData(turnPage1.arrDataCacheSet, turnPage1.pageIndex, turnPage1.pageLineNum);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage1.pageDisplayGrid);
  
  //控制是否显示翻页按钮
  if (turnPage1.queryAllRecordCount > turnPage1.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
}


//提交
function submitForm()
{
   
  var aCount = ErrGrid.mulLineCount;
  	
  //alert("选中的行是"+aCount);
  if(aCount<1)
  {
	 alert("请至少填写一行差错记录");
	 return false;
  }
  
  
  if(TempFeeNo==null||TempFeeNo=="")
  {
  	alert("财务收费单证印刷号为空，无法保存差错记录");
	  return false;
  }
  
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
  //alert(prtNo);
  fm.action = "./AbnormityErrAndRecordErrSave.jsp?TempFeeNo="+TempFeeNo+ "&bussNoType="+bussNoType;
  fm.submit(); //
}







