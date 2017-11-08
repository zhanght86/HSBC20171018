//               该文件中包含客户端需要处理的函数和事件
var arrDataSet;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库


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
    //initPolGrid();
    //showSubmitFrame("1");
    fm.submit(); //提交
}



//提交，保存按钮对应操作
function printPol()
{
   var tRow = PolGrid.getSelNo();	
   if( tRow == 0 || tRow == null ){
	 	  alert( "请先选择一条记录，再点击补打按钮。" );
	    return;
	 }
	 else
	 {	 	
	 	  fm.PrtSeq.value = PolGrid.getRowColData(tRow-1,1);
	 	  fm.Code.value = PolGrid.getRowColData(tRow-1,2);
	 	  fm.ContNo.value = PolGrid.getRowColData(tRow-1,3);
	 	  
	 	  if(!checkdata(fm.ContNo.value,fm.PrtSeq.value,fm.Code.value))
	 	  {
	 	      return;
	 	  }
	 	  
      if(fm.Code.value=="BQ88")
	    {
	        fm.action="./ReEdorAskSave.jsp";
	    }
	    else{
	    	  fm.action="./bqRePrintSave.jsp";
	    }
	 	  fm.target = "../f1print";
	 	  fm.submit();
	 }
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
		return arrSelected;

	arrSelected = new Array();
	//设置需要返回的数组
	//arrSelected[0] = arrDataSet[tRow-1];
	arrSelected[0]=new Array();
	arrSelected[0][0]=PolGrid.getRowColData(tRow-1,1);
	arrSelected[0][1]=PolGrid.getRowColData(tRow-1,3);
	arrSelected[0][1]
	return arrSelected;
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
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
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");       	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  
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

//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           

// 查询按钮
function easyQueryClick()
{
	var tCode = fm.all('CodeS').value;
//Deleted By QianLy for QC7209 on 2006-9-29-------
//	if(tCode == ""){
//		alert("请选择补打核保通知书类型！");
//		return false;
//	}
//-----------------
	if(fm.ContNoS.value == "" && fm.PrtSeqS.value == ""){
	    alert("在线打印前，请输入要补打核保通知书的保单号或通知书号！\n或者到保全批量打印页面进行打印！");
	    return false;
	}
		 //书写SQL语句
	var strSQL = "";
//====================del=========liuxiaosong=================2006-11-06------------start====================
//	strSQL = "select a.prtseq,a.code,a.otherno,b.managecom,b.agentcode from loprtmanager a, lccont b where 1=1 and  a.otherno = b.contno and a.stateflag in ('1','0')"
//	         + " and trim(a.code) in (select trim(code) from ldcode where codetype = 'bquwnotice') "//Add By QianLy on 2006-10-14
//	         + getWherePart('a.code', 'CodeS')
//	         + getWherePart('a.otherno', 'ContNoS')
//	         + getWherePart('a.PrtSeq', 'PrtSeqS');
//====================del=========liuxiaosong=================2006-11-06------------end====================
//====================add=========liuxiaosong=================2006-11-06------------end====================
    //此SQL效率从0.031S提高到0.016S，减少50%消耗
//	strSQL = "select a.prtseq,a.code,a.otherno,b.managecom,b.agentcode from loprtmanager a, lccont b where 1=1 and  a.otherno = b.contno and a.stateflag in ('1','0')"
//			 + getWherePart('a.code', 'CodeS')
//	         + " and trim(a.code) in (select trim(code) from ldcode where codetype = 'bquw') "
//	         + getWherePart('a.otherno', 'ContNoS')
//	         + getWherePart('a.PrtSeq', 'PrtSeqS');
//====================add=========liuxiaosong=================2006-11-06------------end====================
	sql_id = "RnewRePrintSql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RnewRePrintSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(document.getElementsByName(trim('CodeS'))[0].value);//指定传入的参数
	my_sql.addSubPara(document.getElementsByName(trim('ContNoS'))[0].value);//指定传入的参数
	my_sql.addSubPara(document.getElementsByName(trim('PrtSeqS'))[0].value);//指定传入的参数
	str_sql = my_sql.getString();
  var aResult = easyExecSql(str_sql);  
  //alert(aResult);
	turnPage.strQueryResult  = easyQueryVer3(strSQL);  
  
  //alert(turnPage.strQueryResult);
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
   alert("没有要提交补打通知书的信息！");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //tArr = chooseArray(arrDataSet,[0,1,3,4]) 
  //调用MULTILINE对象显示查询结果
  
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //displayMultiline(tArr, turnPage.pageDisplayGrid);
}
//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
	parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,0,*";
 	}
}

function checkdata(aContNo,aPrtSeq,aCode)
{
	if(aContNo == "" || aContNo == null)
	{
		alert("保单号为空！");
		return false;
	}
	if(aPrtSeq == "" || aPrtSeq == null)
	{
		alert("保单号为空！");
		return false;
	}
	if(aCode == "" || aCode == null)
	{
		alert("保单号为空！");
		return false;
	}
	
	
	var wherepart = " and activityid = ";
	if(aCode == "BQ90")
	   wherepart += "'0000001106'";
	else
	   wherepart += "'0000001280'";
	
	
	sql_id = "RnewRePrintSql1";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RnewRePrintSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(aContNo);//指定传入的参数
	my_sql.addSubPara(aPrtSeq);//指定传入的参数
	my_sql.addSubPara(wherepart);//指定传入的参数
	str_sql = my_sql.getString();
	var tResult = easyExecSql(str_sql,1,0);
	if(tResult != null)
	{
		alert("此通知书未打印，请进行正常打印！");
		return false;
	}
	return true;
		
}