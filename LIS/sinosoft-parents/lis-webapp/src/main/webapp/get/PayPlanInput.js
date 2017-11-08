//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量


var tResourceName="get.PayPlanInputSql";
var tResourceSQL1="PayPlanInputSql1";
var tResourceSQL2="PayPlanInputSql2";
var tResourceSQL3="PayPlanInputSql3";
var tResourceSQL4="PayPlanInputSql4"; 


//提交，保存按钮对应操作
function submitForm()
{              
  var i = 0;
  if((fm.ContNo.value == null || fm.ContNo.value == "") && (fm.GrpContNo.value == null || fm.GrpContNo.value == "")&&(fm.InsuredNo.value == null || fm.InsuredNo.value == ""))
  {
  	alert("至少录入个人合同号码，被保人客户号码或集体保单号码其中的一项！");
  	return;
  }
  if (window.confirm("是否确认本次催付?"))
  {
    if (verifyInput())
    {
	  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	  //showSubmitFrame(mDebug);
	  fm.action="./PayPlanSave.jsp"
	  document.getElementById("fm").submit(); //提交
    }
  }
}
function showCondPage()
{
	var tSerialNo = document.all('SerialNo').value;
	if (tSerialNo!=null&& tSerialNo!='')
	{
		divLCInsured2.style.display = '';
	}
	
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content,Count )
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
		document.all('SerialNo').value = FlagStr;
		document.all('getcount').value = Count;
		
		if (window.confirm("是否查询本次生成给付记录"))
			{
				divLCInsured2.style.display ='';
				easyQueryClick();
			}
	   //执行下一步操作
  }
}



//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true");
//    showDiv(inputButton,"false");
	  initForm();
  }
  catch(re)
  {
  	alert("在PayPlan.js-->resetForm函数中发生异常:初始化界面错误!");
  }
}

//取消按钮对应操作
function cancelForm()
{
//  window.location="../common/html/Blank.html";
  //  showDiv(operateButton,"true");
   // showDiv(inputButton,"false");
}

//提交前的校验、计算
function beforeSubmit()
{
  //添加操作
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

// 查询按钮
function easyQueryClick()
{
	var tSerialNo;
	
	tSerialNo = document.all('SerialNo').value;
	
	// 初始化表格
	initGetDrawGrid();
	
	// 书写SQL语句
	var strSQL = "";
	//strSQL = "select LJSGet.GetNoticeNo,LCPol.InsuredNo,LJSGet.OtherNo,LCPol.RiskCode,LJSGet.GetDate,LJSGet.SumGetMoney from LJSGet,LCPol where LJSGet.OtherNo = LCPol.PolNo and LJSGet.OtherNoType='2'"
	//			 +" "+ getWherePart( 'SerialNo' );
	
	strSQL = wrapSql(tResourceName,tResourceSQL1,[document.all('SerialNo').value]); 
//	alert(strSQL);				 
	//查询SQL，返回结果字符串
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
	  
	  //判断是否查询成功
	  if (!turnPage.strQueryResult) 
	  {
	    alert("查询失败！");
	  }
	  else
	  {
	  //查询成功则拆分字符串，返回二维数组
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  
	  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
	  turnPage.pageDisplayGrid = GetGrid;    
	          
	  //保存SQL语句
	  turnPage.strQuerySql     = strSQL; 
	  
	  //设置查询起始位置
	  turnPage.pageIndex       = 0;  
	  
	  //在查询结果数组中取出符合页面显示大小设置的数组
	  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	  
	  //调用MULTILINE对象显示查询结果
	  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		}
}



//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  //showDiv(operateButton,"false");
 // showDiv(inputButton,"true");
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