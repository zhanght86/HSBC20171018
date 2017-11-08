//               该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

var showInfo;
var mDebug="0";
var arrStrReturn = new Array();
var arrGrid;

//提交，保存按钮对应操作
function submitForm()
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
	try {
		fm.sql_where.value = eval("top.opener.fm.sql_where.value");
	} catch (ex) {
		fm.sql_where.value = "";
	}

  document.getElementById("fm").submit(); //提交
}


function QueryClick()
{
	// 初始化表格
	// 书写SQL语句
	var strSQL = "";
	var i, j, m, n;	
	initSysCertifyGrid();
	try {
		fm.sql_where.value = eval("top.opener.fm.sql_where.value");
	} catch (ex) {
		fm.sql_where.value = "";
	}
	//QUERY_FLAG
	var tQUERY_FLAG = eval("top.opener.fm.QUERY_FLAG.value");
	//alert('1');
	if(trim(fm.CertifyCode.value)=='')
	{
		alert('请先输入单证编码');
		return false;
	}
	//alert(tQUERY_FLAG);
	//fm.sql_where.value = " and StateFlag = '0' "
	if(trim(fm.CertifyCode.value)=='9994')
	{
	   if(fm.CertifyNo.value==null||trim(fm.CertifyNo.value)==""){
	     alert("请录入单证号码！");
	     return false;
	   }
		if(tQUERY_FLAG=='0')
		{
		strSQL = "select A.* from ("
		       + " select  '9994' x,grpcontno y,'A'||a.managecom z,'D'||a.agentcode m,a.operator n,null o,'SYS' p,"
		       + " null q,null r,null s,a.getpoldate t from lcgrpcont a "
           + " where a.appflag='1' and a.getpoldate is null and printcount>0 "
           + " ) A where 1=1 "
           + getWherePart('A.y','CertifyNo')
           + getWherePart('A.z','SendOutCom')
           + getWherePart('A.m','ReceiveCom')
           + " order by A.y"
           ;
      }
	
	else
		{
   //获取原保单信息
    strSQL = "select certifycode,certifyno,sendoutcom,receivecom,operator,validdate,handler,handledate,sendno,takebackno,makedate,takebackdate from LZSysCertify  WHERE 1=1 "				 			
			 + fm.sql_where.value +" "
			 + getWherePart('CertifyCode', 'CertifyCode') 
	     + getWherePart('CertifyNo', 'CertifyNo')
			 + getWherePart('SendOutCom','SendOutCom')
			 + getWherePart('ReceiveCom','ReceiveCom')
			 + getWherePart('Handler','Handler')
			 + getWherePart('HandleDate','HandleDate')
			 " order by CertifyNo ";	
			}
		}
	//prompt('',strSQL);		 				 
	turnPage.queryModal(strSQL, SysCertifyGrid); 
 if (!turnPage.strQueryResult) {
  	alert("查询无结果!");
  //easyQueryClickInit();
  return "";
}   
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
 
  return true;	
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
    parent.fraSubmit.getGridResult();
    
    arrGrid = null;
    if( arrStrReturn[0] == '0|0^' ) {
    	SysCertifyGrid.clearData();
    } else {
			arrGrid = decodeEasyQueryResult(arrStrReturn[0]);
    	useSimulationEasyQueryClick(arrStrReturn[0]);
    }
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
  	alert("在SysCertTakeBack.js-->resetForm函数中发生异常:初始化界面错误!");
  }
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

function returnParent()
{
  var arrReturn = new Array();
	var tSel = SysCertifyGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
	{
		//alert( "请先选择一条记录，再点击返回按钮。" );
		top.close();
	}
	else
	{
			try
			{	
				//alert(tSel);
				arrReturn = getQueryResult();
				top.opener.afterQuery( arrReturn );
			}
			catch(ex)
			{
				alert( "没有发现父窗口的afterQuery接口。" + ex );
			}
			top.close();
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = SysCertifyGrid.getSelNo();
	//alert("Selected row:" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	{
		return arrSelected;
	}
	arrSelected = new Array();
	//设置需要返回的数组
	//arrSelected[0] = arrDataSet[tRow-1];
	arrSelected[0] = SysCertifyGrid.getRowData(tRow-1);
	return arrSelected;
}


function useSimulationEasyQueryClick(strData) {
  //保存查询结果字符串
  turnPage.strQueryResult  = strData;
  
  //使用模拟数据源，必须写在拆分之前
  turnPage.useSimulation   = 1;  
    
  //拆分字符串，返回二维数组
  var tArr                 = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //与MULTILINE配合,使MULTILINE显示时的字段位置匹配数据库的字段位置
  var filterArray          = new Array(0, 1, 3, 4, 15);

  //清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //过滤二维数组，使之与MULTILINE匹配
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = SysCertifyGrid;
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
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