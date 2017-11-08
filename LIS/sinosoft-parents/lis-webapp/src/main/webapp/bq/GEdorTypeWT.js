//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量

function edorTypeWTReturn()
{
	top.close();
	top.opener.getEdorItemGrid();
}
function edorTypeWTQuery()
{
	alert("Wait...");
}
function edorTypeWTSave()
{
  //校验是否已过犹豫期 by wenhuan
  //var intval = dateDiff(fm.CValiDate.value, fm.EdorValiDate.value, "D");
  //alert("保单已保天数"+intval);
  //if (intval>10)
  //{
  //	alert("该保单已保"+intval+"天，已过犹豫期!");
  //	return false;        //没有合适的单子，为了测试暂时注掉
  //}
  //开始保存
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.all('fmtransact').value = "INSERT||MAIN";

 	//showSubmitFrame(mDebug);
  fm.submit();
  
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content,Result )
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
  	var tTransact = document.all('fmtransact').value;
		if (tTransact=="QUERY||MAIN")
		{
			var iArray;
			//清空数据容器，两个不同查询共用一个turnPage对象时必须使用，最好加上，容错
  	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
			//保存查询结果字符串
 		 	turnPage.strQueryResult  = Result;
//  alert(Result);
  		//使用模拟数据源，必须写在拆分之前
  		turnPage.useSimulation   = 1;  
    
  		//查询成功则拆分字符串，返回二维数组
  		var tArr   = decodeEasyQueryResult(turnPage.strQueryResult,0);
			
			turnPage.arrDataCacheSet =chooseArray(tArr,[1,20,21,31,42])
			//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
		 	turnPage.pageDisplayGrid = LPPolGrid;    
		  
		  //设置查询起始位置
		 	turnPage.pageIndex       = 0;
		 	//在查询结果数组中取出符合页面显示大小设置的数组
	  	var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
			//调用MULTILINE对象显示查询结果
	   	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		}
		else
		{
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //alert(FlagStr);
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //执行下一步操作
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
//---------------------------
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

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

function getWTFeeDetailGrid()
{
	var sqlid817111718="DSHomeContSql817111718";
var mySql817111718=new SqlClass();
mySql817111718.setResourceName("bq.GEdorTypeWTInputSql");//指定使用的properties文件名
mySql817111718.setSqlId(sqlid817111718);//指定使用的Sql的id
mySql817111718.addSubPara(document.all('GrpContNo').value);//指定传入的参数
var strSQL=mySql817111718.getString();
	
//	var strSQL = "select p.grppolno,p.riskcode,(select RiskName from LMRisk m where m.RiskCode =p.RiskCode),p.cvalidate,nvl(p.prem,0) from lcgrppol p where p.grpcontno='" + document.all('GrpContNo').value + "'";
	drrResult = easyExecSql(strSQL);
	if (drrResult)
	{
		displayMultiline(drrResult,WTFeeDetailGrid);
	}
}

