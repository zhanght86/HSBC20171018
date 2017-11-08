//程序名称：UWManuSpec.js
//程序功能：人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var str = "";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保

//提交，保存按钮对应操作
function submitForm()
{
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

    fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
    parent.close();
  }
  else
  { 
	var showStr="操作成功";  	
  	showInfo.close();
  	alert(showStr);
  	parent.close();
  	
    //执行下一步操作
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


function manuchkspecmain()
{
	fm.submit();
}

// 查询按钮
function initlist(tProposalNo)
{
	
	// 书写SQL语句
	k++;
	var strSQL = "";
	
	strSQL = "select dutycode,firstpaydate,payenddate from lcduty where "+k+" = "+k
		+ " and polno = '"+tProposalNo+"'";
	
	//alert(strSQL);
	//execEasyQuery( strSQL );
	  //查询SQL，返回结果字符串
  //turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 
  str  = easyQueryVer3(strSQL, 1, 0, 1); 
  //alert("ddd:"+str); 
  //判断是否查询成功
  //if (!turnPage.strQueryResult) {
    //alert("没有没通过核保个人单！");
    //return "";
  //}
  //else
  //{
  	//str = turnPage.strQuestResult;
  	//alert("sss:"+turnPage.strQuestResult);
  //alert("1:"+str);	
  //}

  
  return str;
}

//查询已经加费项目
function QueryGrid(tProposalNo)
{
	// 初始化表格
	//initPolGrid();
	
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;	
	strSQL = "select dutycode,payplantype,paystartdate,payenddate,prem from LCPrem where 1=1 "
			 + " and PolNo ='"+tProposalNo+"'"
			 + " and payplancode like '000000%%'";			
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = SpecGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;	
}



//查询已经录入加费原因
function QueryAddReason(tProposalNo)
{
	
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;	
	strSQL = "select addpremreason from LCUWMaster where 1=1 "
			 + " and proposalno = '"+tProposalNo+"'";
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1); 

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.Reason.value = turnPage.arrDataCacheSet[0][0];
  
  return true;	
}

