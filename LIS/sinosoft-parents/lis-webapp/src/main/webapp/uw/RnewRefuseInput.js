//               该文件中包含客户端需要处理的函数和事件
var arrDataSet
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var ContNo;
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库
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

	showInfo.focus();  //showSubmitFrame(mDebug);
  initPolGrid();
  document.getElementById("fm").submit();//提交
}

//提交，保存按钮对应操作
function printPol()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showSubmitFrame(mDebug);

  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();
  //var testPol = PolGrid.getRowColData();
  //alert(tSel);
  if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();		
		//arrReturn = getQueryResult();
		//ContNo=arrReturn[0][0];
		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
	  tOldPrtSeq = PolGrid.getRowColData(tSel-1,10); 
		tContNo = PolGrid.getRowColData(tSel-1,2);
		tNoticeType = PolGrid.getRowColData(tSel-1,4);
	
		//alert(ContNo);
		fmSave.PrtSeq.value = tPrtSeq;
		fmSave.OldPrtSeq.value = tOldPrtSeq;
		fmSave.ContNo.value = tContNo ;
		fmSave.NoticeType.value = tNoticeType;
		fmSave.fmtransact.value = "PRINT";
		
		fmSave.target = "../f1print";
    
    fmSave.action="./RnewRefuseSave.jsp";
	
		fmSave.submit();
		showInfo.close();


	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	//alert("111" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	//{alert("111");
	return arrSelected;
	//}
	//alert("222");
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrDataSet[tRow-1];

	return arrSelected;
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

    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
    //执行下一步操作
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

function returnParent()
{
    tContNo = "00000120020110000050";
    top.location.href="./ProposalQueryDetail.jsp?ContNo="+tContNo;
}


// 查询按钮
function easyQueryClick()
{

	initPolGrid();
	// 书写SQL语句
	//tongmeng 2007-11-12 modify
	//统一打印核保通知书
	var strSQL = "";
	//tongmeng 2007-10-30 add
	//增加核保下结论后系统直接发放的通知书的打印
//	strSQL = "select a.prtseq,a.otherno,a.agentcode,a.code,'续保拒保通知书',a.managecom,(select salechnl from lccont where contno=a.otherno) "
//	           + ",'XBJB','1',a.prtseq from loprtmanager a " 
//             + " where 1=1 and a.code='RE00' and a.standbyflag7='XBJB' and a.stateflag='0' "
//	           + getWherePart('a.otherno', 'ContNo')
//     	       + getWherePart('a.managecom', 'ManageCom', 'like')
//	           + getWherePart('a.agentcode','AgentCode');
	sql_id = "RnewRefuseInputSql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("uw.RnewRefuseInputSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
	my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
	my_sql.addSubPara(document.getElementsByName(trim('AgentCode'))[0].value);//指定传入的参数
	str_sql = my_sql.getString();

	if(document.all('SaleChnl').value!=null && trim(document.all('SaleChnl').value)!='')
	{
//	   strSQL = strSQL + " and exists (select '1' from lccont where 1=1 and salechnl='"+trim(document.all('SaleChnl').value)+"')";
	   sql_id = "RnewRefuseInputSql1";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewRefuseInputSql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(document.getElementsByName(trim('ContNo'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ManageCom'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('AgentCode'))[0].value);//指定传入的参数
		my_sql.addSubPara(trim(document.all('SaleChnl').value));//指定传入的参数
		str_sql = my_sql.getString();
	}	
	turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("无待打印的续保拒保通知书！");
    return false;
    }
    turnPage.queryModal(strSQL, PolGrid);
////查询成功则拆分字符串，返回二维数组
//  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//
//  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
//  turnPage.pageDisplayGrid = PolGrid;
//
//  //保存SQL语句
//  turnPage.strQuerySql     = strSQL;
//
//  //设置查询起始位置
//  turnPage.pageIndex       = 0;
//
//  //在查询结果数组中取出符合页面显示大小设置的数组
// arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
// //tArr=chooseArray(arrDataSet,[0])
//  //调用MULTILINE对象显示查询结果
//
//  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
//  //displayMultiline(tArr, turnPage.pageDisplayGrid);
}

function queryBranch()
{
  showInfo = window.open("../certify/AgentTrussQuery.html");
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery(arrResult)
{

  if(arrResult!=null)
  {
  	fm.BranchGroup.value = arrResult[0][3];
  }
}