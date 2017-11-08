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
  document.getElementById("fm").submit(); //提交
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
		tPrtNo = PolGrid.getRowColData(tSel-1,2);
		tContNo = PolGrid.getRowColData(tSel-1,2);
		tNoticeType = PolGrid.getRowColData(tSel-1,4);
		tMissionID = PolGrid.getRowColData(tSel-1,8);
		tSubMissionID = PolGrid.getRowColData(tSel-1,9);
	
		//alert(ContNo);
		fmSave.PrtSeq.value = tPrtSeq;
		fmSave.OldPrtSeq.value = tOldPrtSeq;
		fmSave.PrtNo.value = tPrtNo;
		fmSave.ContNo.value = tContNo ;
		fmSave.NoticeType.value = tNoticeType;
		fmSave.fmtransact.value = "PRINT";
		fmSave.MissionID.value = tMissionID;
		fmSave.SubMissionID.value = tSubMissionID;
		fmSave.target = "../f1print";
	  if(tNoticeType==89)
	    {
	   	  //alert(11111);
	   	  fmSave.action="./MeetF1PSave.jsp";
	   	}
	   	
	  	  else
	  	{
	  	
	  	fmSave.action="./RnewUWF1PSave.jsp";
	  	} 	
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
	var iHeight=250;     //弹出窗口的高度; 
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
	var strSql = "";
	var sqlid1="RnewUWNoticeInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("uw.RnewUWNoticeInputSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	// 书写SQL语句
//	strSQL = "SELECT LWMission.MissionProp3, LWMission.MissionProp2,LWMission.MissionProp4,"
//	        //+ " LWMission.MissionProp5 "
//	        + "a.code "
//	        + ",'续保核保通知书' "
//	        + ",LWMission.MissionProp7 "
//	        + " ,c.salechnl,LWMission.MissionID ,LWMission.SubMissionID,LWMission.MissionProp14 "
//	        + " FROM LWMission,loprtmanager a,lccont c "
//	        + " WHERE LWMission.ActivityID in (select activityid from lwactivity where functionId = '10047004') "
//	        + "and exists (select 1 from Lwprocess a  where  a.Processid=LWMission.Processid and a.Busitype='1004')" //承保工作流
//	        + " and c.contno=a.otherno "
//	        + " and a.prtseq=LWMission.MissionProp3"
	//tongmeng 2007-12-11 add
	if(document.all('NoticeType').value!=null&&trim(document.all('NoticeType').value)!='')   
	{
		strSql = " and exists (select '1' from loprtmanager where prtseq=LWMission.MissionProp3 and code='"+trim(document.all('NoticeType').value)+"')";
	}     
//	        strSQL = strSQL + getWherePart('LWMission.MissionProp1', 'PrtNo')
//	        + getWherePart('LWMission.MissionProp2', 'ContNo')
//			    + getWherePart('LWMission.MissionProp7', 'ManageCom', 'like')
//			    + getWherePart('LWMission.MissionProp4','AgentCode')
//			   // + getWherePart('LWMission.MissionProp5','NoticeType')
//			    + getWherePart('c.salechnl','SaleChnl');
	//tongmeng 2007-10-30 add
	//增加核保下结论后系统直接发放的通知书的打印
//	var tSQL_b = " union "
//	           + "select a.prtseq,a.otherno,a.agentcode,a.code,'续保核保通知书',a.managecom,(select salechnl from lccont where contno=a.otherno) "
//	           + ",'TBJB','1',a.prtseq from loprtmanager a " 
//                   + " where 1=1 and a.standbyflag7='TBJB' and a.stateflag='0' "
//	           + getWherePart('a.otherno', 'ContNo')
//     	           + getWherePart('a.managecom', 'ManageCom', 'like')
//	           + getWherePart('a.agentcode','AgentCode')
//	           + getWherePart('a.code','NoticeType')
	var tSql_b = "";
	if(document.all('SaleChnl').value!=null && trim(document.all('SaleChnl').value)!='')
	{
	   tSql_b = " and exists (select '1' from lccont where salechnl='"+trim(document.all('SaleChnl').value)+"')"
	}	
//	strSQL = strSQL + tSQL_b;
	
	mySql1.addSubPara(strSql);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("PrtNo")[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("ContNo")[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("ManageCom")[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("AgentCode")[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("SaleChnl")[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("ContNo")[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("ManageCom")[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("AgentCode")[0].value);//指定传入的参数
	mySql1.addSubPara(window.document.getElementsByName("NoticeType")[0].value);//指定传入的参数
	mySql1.addSubPara(tSql_b);//指定传入的参数
	strSQL=mySql1.getString();
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("无待打印的核保通知书！");
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