
//创建日期： 
//创建人   jw
//更新记录：  更新人    更新日期     更新原因/内容

var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;




function SubmitForm()
{
  if(fm.Bdate.value==''){
     alert("请输入起始日期");
     return;
  }
  if(fm.Edate.value==''){
     alert("请输入终止日期");
     return;
  }
  var showStr="正在提取数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fm").submit();
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{ 
	 
 try{

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
  }
 }
 catch(ex){}

}

function easyQueryClick()
{
 
  // 初始化表格
  initRBResultGrid();
  
  var oper="like" ;
  // 书写SQL语句
  var strSQL = "";
  /**
  strSQL = "select EventNo,(select b.parametervalue from FIOperationParameter b where b.eventno=a.eventno and b.parametertype = 'BatchNo') as BatchNo,Operator,MakeDate,PerformState,othernoMark from FIOperationLog a where 1=1 and a.EventType = '01'";	 		
  if(fm.Bdate2.value!=''){
  	strSQL = strSQL + " and a.MakeDate>='"+fm.Bdate2.value+"' ";
  }
  if(fm.Edate2.value!=''){
  	strSQL = strSQL + " and a.MakeDate<='"+fm.Edate2.value+"' ";
  } 
  */
  var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FIDistillOtoFSql"); //指定使用的properties文件名
		mySql1.setSqlId("FIDistillOtoFSql1");//指定使用的Sql的id
		mySql1.addSubPara(fm.Bdate2.value);//指定传入的参数
		mySql1.addSubPara(fm.Edate2.value);//指定传入的参数
		strSQL= mySql1.getString();
  
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有查询到数据！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = RBResultGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
}

