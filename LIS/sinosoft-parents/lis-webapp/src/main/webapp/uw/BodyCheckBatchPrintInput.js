//               该文件中包含客户端需要处理的函数和事件
var arrDataSet
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var ContNo;

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
  fm.submit(); //提交
}

//提交，保存按钮对应操作
function printPol()
{
	/**
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
  //showSubmitFrame(mDebug);

  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();

  if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
		tMissionID = PolGrid.getRowColData(tSel-1,6);
		tSubMissionID = PolGrid.getRowColData(tSel-1,7);
		tPrtNo = PolGrid.getRowColData(tSel-1,2);
		tContNo = PolGrid.getRowColData(tSel-1,2);
		//alert(ContNo);
		fmSave.PrtSeq.value = tPrtSeq;
		fmSave.MissionID.value = tMissionID;
		fmSave.SubMissionID.value = tSubMissionID;
		fmSave.ContNo.value = tContNo ;
		fmSave.PrtNo.value = tPrtNo ;


		document.getElementById("fmSave").submit();
		showInfo.close();
	}
	
	*/
	
	var count=0;
	for(var j=0;j<PolGrid.mulLineCount;j++){
		if(PolGrid.getChkNo(j)){
			count++;
		}	
	}
	if(count==0){
		alert("请先选择一条记录，再点击打印按钮。");	
		return;
	}
	fmSave.fmtransact.value = "PRINT";
	fmSave.target = "../f1print";
	fmSave.action="./BodyCheckBatchPrintSave.jsp?ChkCount="+count;
	document.getElementById("fmSave").submit();
}

//提交，保存按钮对应操作
function printPol2()
{
	var count=0;
	for(var j=0;j<PolGrid.mulLineCount;j++){
		if(PolGrid.getChkNo(j)){
			count++;
		}	
	}
	if(count==0){
		alert("请先选择一条记录，再点击打印按钮。");	
		return;
	}		
	fmSave.fmtransact.value = "PRINT";
	fmSave.target = "../f1print";
	fmSave.action="./BodyCheckPrintSave2.jsp?ChkCount="+count;
	document.getElementById("fmSave").submit();
}

//提交，保存按钮对应操作
function printPol3()
{
	var count=0;
	for(var j=0;j<PolGrid.mulLineCount;j++){
		if(PolGrid.getChkNo(j)){
			count++;
		}	
	}
	count = 1;
		
	fmSave.fmtransact.value = "PRINT";
	fmSave.target = "../f1print";
	fmSave.action="./BodyCheckPrintSave3.jsp?PrintType="+count;
	document.getElementById("fmSave").submit();
}

//提交，保存按钮对应操作
function printPol4()
{
	var count=0;
	for(var j=0;j<PolGrid.mulLineCount;j++){
		if(PolGrid.getChkNo(j)){
			count++;
		}	
	}
	count = 2;
		
	fmSave.fmtransact.value = "PRINT";
	fmSave.target = "../f1print";
	fmSave.action="./BodyCheckPrintSave3.jsp?PrintType="+count;
	document.getElementById("fmSave").submit();
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	return arrSelected;
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
	var strSQL = "";
//	var ssql = "SELECT processid FROM LWCORRESPONDING where busitype='1001'";
	
	 var sqlid1="BodyCheckBatchPrintInputSql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.BodyCheckBatchPrintInputSql");
	 mySql1.setSqlId(sqlid1);//指定使用SQL的id
	 var ssql = mySql1.getString();
	
	var tProcessID = easyExecSql(ssql);
	// 书写SQL语句
//	strSQL = "SELECT a.MissionProp3, a.MissionProp2,a.MissionProp4, a.MissionProp7 ,a.MissionProp13,a.MissionID ,a.SubMissionID FROM LWMission a WHERE a.ActivityID  in (select activityid from lwactivity  where functionid ='10010025') "  //ActivityID = '0000001106' 单证类型为承保体检
//	        + "and a.ProcessID ='"+tProcessID+"' " //承保工作流	     
//	        + getWherePart('a.MissionProp2', 'ContNo')
//		    + getWherePart('a.MissionProp7', 'ManageCom', 'like')
//			+ getWherePart('a.MissionProp4','AgentCode');
//			    //+ getWherePart('LWMission.MissionProp13','SaleChnl')
//             if(fm.SaleChnl.value!=null&&fm.SaleChnl.value!=""){			   
//			    strSQL=strSQL+" and exists (select 1 from lcpol where prtno =a.missionprop1 and salechnl='"+fm.SaleChnl.value+"')";
//			    }
		
           
             var  ContNo2 = window.document.getElementsByName(trim("ContNo"))[0].value;
         	 var  ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
         	 var  AgentCode2 = window.document.getElementsByName(trim("AgentCode"))[0].value;
         	 var  SaleChnl2 = fm.SaleChnl.value;
         	 var sqlid2="BodyCheckBatchPrintInputSql2";
         	 var mySql2=new SqlClass();
         	 mySql2.setResourceName("uw.BodyCheckBatchPrintInputSql");
         	 mySql2.setSqlId(sqlid2);//指定使用SQL的id
         	 mySql2.addSubPara(tProcessID);//指定传入参数
         	 mySql2.addSubPara(ContNo2);//指定传入参数
         	 mySql2.addSubPara(ManageCom2);//指定传入参数
         	 mySql2.addSubPara(AgentCode2);//指定传入参数
         	 mySql2.addSubPara(SaleChnl2);//指定传入参数
         	 strSQL = mySql2.getString();

	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
   alert("没有要打印的承保体检通知书！");
    return false;
    }
   turnPage.queryModal(strSQL, PolGrid);
   
////查询成功则拆分字符串，返回二维数组
//  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
//alert("b");
//  //设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
//  turnPage.pageDisplayGrid = PolGrid;
//alert("c");
//  //保存SQL语句
//  turnPage.strQuerySql     = strSQL;
//alert("d");
//  //设置查询起始位置
//  turnPage.pageIndex       = 0;
//alert("e");
//  //在查询结果数组中取出符合页面显示大小设置的数组
// arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
// //tArr=chooseArray(arrDataSet,[0])
//  //调用MULTILINE对象显示查询结果
//
//  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
//  alert("ok");
  //displayMultiline(tArr, turnPage.pageDisplayGrid);
  fmSave.printButton.disabled = false;
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