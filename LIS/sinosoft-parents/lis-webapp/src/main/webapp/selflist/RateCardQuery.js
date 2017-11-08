//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量


function EasyQueryClick()
{
    // 书写SQL语句
	var str="";
	//已发放到代理人手中的单证
//	var strSQL="select a.riskcode,(select riskname from lmrisk where a.riskcode=lmrisk.riskcode),a.insuyear,a.insuyearflag,a.prem,a.mult,a.productplan from ratecard a"
//	         +" where 1=1"
//             +getWherePart( 'a.riskcode','Riskcode' )
//             +getWherePart( 'a.ProductPlan','ProductPlan' )
//             +getWherePart( 'a.InsuYear','InsuYear' )
//             +getWherePart( 'a.InsuYearFlag','InsuYearFlag' )
//             +getWherePart( 'a.Prem','Prem' );

	var sqlid1="RateCardQuerySql1";
 	var mySql1=new SqlClass();
 	mySql1.setResourceName("selflist.RateCardQuerySql");
 	mySql1.setSqlId(sqlid1); //指定使用SQL的id
 	mySql1.addSubPara(window.document.getElementsByName(trim("Riskcode"))[0].value);//指定传入参数
 	mySql1.addSubPara(window.document.getElementsByName(trim("ProductPlan"))[0].value);
 	mySql1.addSubPara(window.document.getElementsByName(trim("InsuYear"))[0].value);
 	mySql1.addSubPara(window.document.getElementsByName(trim("InsuYearFlag"))[0].value);
 	mySql1.addSubPara(window.document.getElementsByName(trim("Prem"))[0].value);
 	var strSQL = mySql1.getString();
	
	
  	  //prompt("",strSQL);
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
	  
	  //判断是否查询成功
	  if (!turnPage.strQueryResult) 
	  {
	  	queryflag=0;
	    alert("没有所要查询的卡单费率定义信息！");
	    return false;
	  }
	    
	  queryflag=1;
	  //查询成功则拆分字符串，返回二维数组
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  
	  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	  turnPage.pageDisplayGrid = RateCardGrid;    
	          
	  //保存SQL语句
	  turnPage.strQuerySql     = strSQL; 
	  
	  //设置查询起始位置
	  turnPage.pageIndex       = 0;  
	  
	  //在查询结果数组中取出符合页面显示大小设置的数组
	  arrDataSet= turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	 //tArr=chooseArray(arrDataSet,[0]) 
	  //调用MULTILINE对象显示查询结果
	  
	  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

}

function displayQueryResult(strResult)
 {
  //与MULTILINE配合,使MULTILINE显示时的字段位置匹配数据库的字段位置
  strResult = Conversion(strResult);
  var filterArray = new Array(0,1,2,3,4,5);
  //保存查询结果字符串
  turnPage.strQueryResult  = strResult;

  //使用模拟数据源
  turnPage.useSimulation   = 1;

  //查询成功则拆分字符串，返回二维数组
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);

  //过滤二维数组，使之与MULTILINE匹配
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  turnPage.pageDisplayGrid = RateCardGrid;

  //设置查询起始位置
  turnPage.pageIndex       = 0;

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  //控制是否显示翻页按钮
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) 
  {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } 
  else 
  {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }

  //必须将所有数据设置为一个数据块
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;

}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{

  //showInfo.close();
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
	alert("query click");
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
			parent.fraMain.rows = "0,0,0,0,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}

	parent.fraMain.rows = "0,0,0,0,*";
}

function ReturnData()
{
   
   fm.OperateType.value = "RETURNDATA";
   var tRow=RateCardGrid.getSelNo();


   if (tRow==0)
   {
   		alert("请您选择一条记录");
  		return;
   }
  
   var tRiskCode = RateCardGrid.getRowColData(tRow-1,1);
   var tPrem   = RateCardGrid.getRowColData(tRow-1,5);
   top.location.href="./RateCardQueryDetail.jsp?RiskCode="+tRiskCode+"&Prem="+tPrem;
}
