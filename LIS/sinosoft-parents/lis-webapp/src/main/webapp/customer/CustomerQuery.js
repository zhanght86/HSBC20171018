//程序名称：FinFeeSure.js
//程序功能：到帐确认
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var k = 0;
var turnPage = new turnPageClass();
var tResourceName = "customer.CustomerQuerySql";
var tResourceSQL1="CustomerQuerySql1";

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
	
	initCustomerGrid();
	document.getElementById("fm").submit(); //提交
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	if (FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		alert(content); 
	}
	else
	{ 	
		var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		alert(content);
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



// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initCustomerGrid();
	k++;


	// 书写SQL语句
	var strSQL = "";
	var strSQL2 = "";
//	strSQL = "select a.insuaccno,a.customerno,b.name,a.contno,a.operationno,a.operationtype,(case a.operationtype when '1' then '新单' when '2' then '续期' when '3' then '保全' when '4' then '赔案' else '领取' end),(case a.opertype when '1' then '收费' when '2' then '应付转入'"
//+" when '3' then '账户转入'  when '4' then '业务使用'  when '5' then '退费'  when '6' then '账户转出'  when '7' then '重新指定' else '' end"
//+"),a.otherno,a.money,getcurrname(a.currency),a.sequence,case a.state when '00' then '未使用' when '01' then '已使用'when '02' then '已领取'when '03' then '挂起' end "
//+",case a.dcflag when 'D' then '借' else '贷' end,((case a.paymode when '1' then '柜面现金' when '2' then '现金支票' when '3' then '转账支票' when '4' then '银行柜面' when '5' then '应付转入' when '6' then 'POS收款' when '7' then '银行批量代扣' when '8' then '领款凭证' when 'A' then '银保通' end)),a.makedate,a.maketime  from ficustomeracctrace a,ldperson b where b.customerno=a.customerno ";
  if (fm.InsuAccNo.value!="")
  {
  	 //strSQL = strSQL + " and a.insuaccno='"+ fm.InsuAccNo.value+"'";
  	  strSQL2 = strSQL2 + " and a.insuaccno='"+ fm.InsuAccNo.value+"'";
  }
  if (fm.CustomerNo.value!="")
  {
  	 //strSQL = strSQL + " and a.CustomerNo='"+ fm.CustomerNo.value+"'";
  	 strSQL2 = strSQL2 + " and a.CustomerNo='"+ fm.CustomerNo.value+"'";
  }
  if (fm.CustomerName.value!="")
  {
  	 //strSQL = strSQL + " and b.name='"+ fm.CustomerName.value+"'";
  	 strSQL2 = strSQL2 + " and b.name='"+ fm.CustomerName.value+"'";
  }
    if (fm.OperationNo.value!="")
  {
  	 //strSQL = strSQL + " and a.OperationNo='"+ fm.OperationNo.value+"'";
  	  strSQL2 = strSQL2 + " and a.OperationNo='"+ fm.OperationNo.value+"'";
  }
    if (fm.OtherNo.value!="")
  {
  	 //strSQL = strSQL + " and a.OtherNo='"+ fm.OtherNo.value+"'";
  	 strSQL2 = strSQL2 + " and a.OtherNo='"+ fm.OtherNo.value+"'";
  }		 
  //strSQL = strSQL + " order by a.makedate desc,a.maketime desc";
  strSQL = wrapSql(tResourceName,tResourceSQL1,[strSQL2]); 
  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有数据！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);	 
				 
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = CustomerGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
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
  
  return true;
}

//付费确认
function autochk()
{

	var haveCheck = false;
	for (i=0; i<CustomerGrid.mulLineCount; i++) 
	{
		if (CustomerGrid.getChkNo(i)) 
		{
			haveCheck = true;
			break;
		}
	}

	if (haveCheck) {
		var showStr="正在传送数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		
		//showSubmitFrame(mDebug);
		fm.action="./CustomerQueryChk.jsp";
		document.getElementById("fm").submit(); //提交
	}
	else 
	{
		alert("请先选择一条暂交费信息，在选择框中打钩！");
	}
}


