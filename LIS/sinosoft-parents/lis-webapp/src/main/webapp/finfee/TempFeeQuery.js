//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

//提交，保存按钮对应操作
function submitForm()
{
	//zy 增加暂收收据和其他号码为空的提示
		if( (fm.TempFeeStatus.value == null || fm.TempFeeStatus.value == "")
	&& (fm.RiskCode.value == null || fm.RiskCode.value == "")
	&& (fm.Operator.value == null || fm.Operator.value == "")
	&& (fm.AgentCode.value == null || fm.AgentCode.value == "")
	&& (fm.TempFeeNo.value == null || fm.TempFeeNo.value == "")
	&& (fm.PrtNo.value == null || fm.PrtNo.value == "")){
		alert("暂收据号/投保单印刷号/暂交费状态/险种/操作员/业务员 请至少填写一项再进行查询");
		return false;
		}
  if(beforeSubmit())
  {
   var i = 0;
   var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
   var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
   var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   TempQueryGrid.clearData("TempQueryGrid");
   //fm.target="_blank";
   document.all('fmAction').value="QUERY";
   fm.QUERYName.disabled=true;
   document.getElementById("fm").submit(); //提交
   }
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

    //var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //执行下一步操作
  }
   fm.QUERYName.disabled=false;
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
    //if(!verifyInput()) return false;
    return true;
}           

function returnParent()
{
    top.close();

}

function easyprint()
{
	easyQueryPrint(1,'TempQueryGrid','turnPage');
}

//显示数据的函数，和easyQuery及MulLine 一起使用
function showRecord(strRecord)
{

  //保存查询结果字符串
  turnPage.strQueryResult  = strRecord;

//alert(strRecord);
  
  //使用模拟数据源，必须写在拆分之前
  turnPage.useSimulation   = 1;  
    
  //查询成功则拆分字符串，返回二维数组
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //与MULTILINE配合,使MULTILINE显示时的字段位置匹配数据库的字段位置
  var filterArray = new Array(2,19,18,39,6,0,7,8,10,25,9,22,23,4,3,38);

  
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //过滤二维数组，使之与MULTILINE匹配
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = TempQueryGrid;             
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
  //必须将所有数据设置为一个数据块
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
	
}

//提交，保存按钮对应操作
function submitFormClass()
{
  if(beforeSubmit())
  {
   var i = 0;
   var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
   var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
   var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  TempQueryByChqNoGrid.clearData("TempQueryByChqNoGrid");
  //fm.target="_blank";
   document.all('fmAction').value="CHEQUE||QUERY";
   document.getElementById("fm").submit(); //提交
   }
}

//显示数据的函数，和easyQuery及MulLine 一起使用
function showClassRecord(strRecord)
{

  //保存查询结果字符串
  turnPage.strQueryResult  = strRecord;

//alert(strRecord);
  
  //使用模拟数据源，必须写在拆分之前
  turnPage.useSimulation   = 1;  
    
  //查询成功则拆分字符串，返回二维数组
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //与MULTILINE配合,使MULTILINE显示时的字段位置匹配数据库的字段位置
  var filterArray = new Array(3,0,11,4,1,2,18,5,8,13,14,15);

  
  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //过滤二维数组，使之与MULTILINE匹配
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = TempQueryByChqNoGrid;             
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
  //必须将所有数据设置为一个数据块
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
	
}


function afterCodeSelect(cCodeName, Field){
  if(cCodeName=="TempFeeStatus"&&window.document.all('TempFeeStatus1')!=null){
    if(Field.value=="0"||Field.value=="6"){
      divChequeNo.style.display="";
    }	
    else {
      divChequeNo.style.display="none";	
    }
  }
  if(cCodeName=="PayMode"&&window.document.all('ChequeNo')!=null){
    if(Field.value=="2"||Field.value=="3"){
      divChequeNo.style.display="";
    }	
    else {
      divChequeNo.style.display="none";	
    }	
  }	
}

function queryByChqNo(){
  window.location = "./TempFeeQueryByChqNo.jsp";	
}

function easyQueryClick(){
  initTempQueryByChqNoGrid();
  var tSql="";
  //tSql = "select * from ljtempfeeclass 	
}

