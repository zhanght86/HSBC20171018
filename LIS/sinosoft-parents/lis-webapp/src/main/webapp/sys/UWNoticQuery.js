//程序名称：SendAllnotice.js
//程序功能：问题件录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var flag;
var k = 0;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=350;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    showInfo.close();
       
  }
  else
  { 
	var showStr="操作成功";
  	
  	showInfo.close();
  	alert(showStr);
   //	initLoprtManager();
  	top.close();
    //执行下一步操作
  }
}







function QuestQuery()
{	
	
	// 书写SQL语句

	k++;
   tCode = document.all('NoticeType').value;
	
		/*var	strSQL = "select cont from ldcodemod where "+k+"="+k				 	
				 + " and codetype = 'uwnoticetype' and Code = '"+tCode+"'";*/

	mySql = new SqlClass();
	mySql.setResourceName("sys.UWNoticQuerySql");
	mySql.setSqlId("UWNoticQuerySql1");
	mySql.addSubPara(tCode); 

	
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
   // alert("没有录入过问题键！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	m = turnPage.arrDataCacheSet[0].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			//alert("turnPage:"+turnPage.arrDataCacheSet[0][0]);
			returnstr = turnPage.arrDataCacheSet[0][0];
			
			document.all('Content').value = returnstr
  		}
  		else
  		{
  			alert("没有录入过问题键！");
  			return "";
  		}
  	
  }
  else
  {
  	alert("没有录入过问题键！");
	return "";
  }

  if (returnstr == "")
  {
  	alert("没有录入过问题键！");
  }
  
   
 
  return returnstr;
}

function afterCodeSelect( cCodeName, Field )
{

	var tCode = document.all('NoticeType').value;
	
	if(cCodeName=="uwnoticetype")
	{
	if(tCode=="84"||tCode=="86"||tCode=="87"||tCode=="88")
	{
		divnoticecontent.style.display = '';
	
		QuestQuery(tCode);
	}
else if(tCode=="81")
	{
		divnoticecontent.style.display = '';
		fm.Content.value = "";
	}
else
	{
		
		divnoticecontent.style.display = 'none';
	}
}
}



function initLoprtManager()
{
	var tContNo = document.all('ContNo').value;
	/*var strSQL = "select prtseq,otherno,othernotype,agentcode,code,stateflag from loprtmanager where otherno = '"+tContNo+"'"
	        + " and Code in ('00','06','81','82','83','84','85','86','87','88','89')";*/
	 mySql = new SqlClass();
	mySql.setResourceName("sys.UWNoticQuerySql");
	mySql.setSqlId("UWNoticQuerySql2");
	mySql.addSubPara(tContNo);       
	//alert(strSQL);        
	        //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1); 
    
 initPolGrid();

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("数据库中没有满足条件的数据！");
 
    return false;
  }
 
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;   
         
  //保存SQL语句
  turnPage.strQuerySql   = strSQL;
 
  //设置查询起始位置
  turnPage.pageIndex = 0; 
 
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
 
  //调用MULTILINE对象显示查询结果
      displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

	        
}


//查询核保通知书内容
function  easyQueryClick()
{
	

	var tSelNo = PolGrid.getSelNo()-1;
	var tPrtSeq = PolGrid.getRowColData(tSelNo,1);

	//var sql="select reqoperator,makedate,donedate ,remark from loprtmanager where prtseq='"+tPrtSeq+"'";
	//alert(sql);　
	 mySql = new SqlClass();
	mySql.setResourceName("sys.UWNoticQuerySql");
	mySql.setSqlId("UWNoticQuerySql3");
	mySql.addSubPara(tPrtSeq); 
	var arrResult = easyExecSql(mySql.getString());
	//alert("arrResult"+arrResult);
		if(arrResult!=null)
		{
		  document.all('Operator').value = arrResult[0][0];
		  document.all('MakeDate').value = arrResult[0][1];
		  document.all('ReplyDate').value = arrResult[0][2];
		  document.all('Content').value = arrResult[0][3];
		}
		  return true;
}
