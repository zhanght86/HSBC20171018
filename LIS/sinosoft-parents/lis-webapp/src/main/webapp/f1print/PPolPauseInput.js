/*
  //创建人：刘岩松
*/
var showInfo;
var mDebug="0";
var FlagDel;//在delete函数中判断删除的是否成功
var turnPage = new turnPageClass();

function displayQueryResult(strResult) {
  //与MULTILINE配合,使MULTILINE显示时的字段位置匹配数据库的字段位置

  strResult = Conversion(strResult);
  var filterArray          = new Array(0,12,1);

  //保存查询结果字符串
  turnPage.strQueryResult  = strResult;

  //使用模拟数据源
  turnPage.useSimulation   = 1;

  //查询成功则拆分字符串，返回二维数组
  var tArr = decodeEasyQueryResult(turnPage.strQueryResult);

  //过滤二维数组，使之与MULTILINE匹配
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  //alert(turnPage.arrDataCacheSet);


  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = BillGrid;

  //设置查询起始位置
  turnPage.pageIndex       = 0;

  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);

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


function afterSubmit( FlagStr, content )
{
  FlagDel = FlagStr;

    showInfo.close();
    if (FlagStr == "Fail" )
    {
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
			//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
    //	var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
		//	showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    	showDiv(inputButton,"false");

    	//执行下一步操作
    }
}

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
    if(cDebug=="1")
    {
			parent.fraMain.rows = "0,0,0,0,*";
    }
    else
    {
  		parent.fraMain.rows = "0,0,0,0,*";
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

//根据起始日期进行查询出要该日期范围内的成功件数
function showSerialNo()
{
	
 	 var strSQL = "";
  if((fm.Station.value=="")||(fm.Station.value=="null"))
	{		
//  strSQL = " select OtherNo,StandbyFlag2 ,makedate "
//  			 + " from LOPRTManager where 1=1 "
//  			 + " and ManageCom like '"+ComCode+"%%'"
//  			 + " and code = '42' and StateFlag = '0'"
//  			 + getWherePart( 'LOPRTManager.MakeDate','StartDate','>=' )
//  			 + getWherePart( 'LOPRTManager.MakeDate','EndDate','<=')
  			 
  			var  StartDate0 = window.document.getElementsByName(trim("StartDate"))[0].value;
		  	var  EndDate0 = window.document.getElementsByName(trim("EndDate"))[0].value;
			var sqlid0="PPolPauseInputSql0";
			var mySql0=new SqlClass();
			mySql0.setResourceName("f1print.PPolPauseInputSql"); //指定使用的properties文件名
			mySql0.setSqlId(sqlid0);//指定使用的Sql的id
			mySql0.addSubPara(ComCode);//指定传入的参数
			mySql0.addSubPara(StartDate0);//指定传入的参数
			mySql0.addSubPara(EndDate0);//指定传入的参数
			strSQL=mySql0.getString();
  			  
	}
	else
	{
//  strSQL = " select OtherNo,StandbyFlag2 ,makedate "
//  			 + " from LOPRTManager where 1=1 "
//  			 + " and ManageCom like '"+fm.Station.value+"%%'"
//  			 + " and code = '42' and StateFlag = '0'"
//  			 + getWherePart( 'LOPRTManager.MakeDate','StartDate','>=' )
//  			 + getWherePart( 'LOPRTManager.MakeDate','EndDate','<=')
  			 
  			var  StartDate1 = window.document.getElementsByName(trim("StartDate"))[0].value;
		  	var  EndDate1 = window.document.getElementsByName(trim("EndDate"))[0].value;
			var sqlid1="PPolPauseInputSql1";
			var mySql1=new SqlClass();
			mySql1.setResourceName("f1print.PPolPauseInputSql"); //指定使用的properties文件名
			mySql1.setSqlId(sqlid1);//指定使用的Sql的id
			mySql1.addSubPara(fm.Station.value);//指定传入的参数
			mySql1.addSubPara(StartDate1);//指定传入的参数
			mySql1.addSubPara(EndDate1);//指定传入的参数
			strSQL=mySql1.getString();
  			  
	}
	
	if(!verifyInput()) 
  {
  	return false;
  }
  initBillGrid();
 
  			
 	
 	
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
 		if (!turnPage.strQueryResult) 
 		{
    	alert("在该管理机构下没有满足条件的保单中止信息记录");
    	return "";
  	}
  
  	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  	turnPage.pageDisplayGrid = BillGrid;    
  	turnPage.strQuerySql     = strSQL; 
  	turnPage.pageIndex       = 0;  
  	var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  	return true;

  
}

//根据选中的批次号、银行编码、管理机构进行查询并且执行打印功能；
function PrintBill()
{
	var MainPolNo = "";
	var tRow=BillGrid.getSelNo();
  	if (tRow==0)
   	{
   		alert("请您先进行选择");
  		return;
  	}
    else
    {
    	strGetNoticeNo = BillGrid.getRowColData(tRow-1,2);//得到交费通知书号码
//      alert("交费通知书号码是"+strGetNoticeNo);
    	var i = 0;
    	fm.action = "./PPolPausePrint.jsp?MainPolNo="+strGetNoticeNo;
    	fm.target="f1print";
//    	showInfo.close();
    	document.getElementById("fm").submit();
    }
}

//根据选中的批次号、银行编码、管理机构进行查询并且执行打印功能；
function QueryBill()
{
   fm.action = "./PPolPauseQuery.jsp";
   fm.target="f1print";
//   showInfo.close();
   document.getElementById("fm").submit();
}