
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
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  //showSubmitFrame(mDebug);

  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();

  if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
		tMissionID = PolGrid.getRowColData(tSel-1,8);
		tSubMissionID = PolGrid.getRowColData(tSel-1,9);
		tPrtNo = PolGrid.getRowColData(tSel-1,7);
		tContNo = PolGrid.getRowColData(tSel-1,2);
		//alert(ContNo);
		fmSave.PrtSeq.value = tPrtSeq;
		fmSave.MissionID.value = tMissionID;
		fmSave.SubMissionID.value = tSubMissionID;
		fmSave.ContNo.value = tContNo ;
		fmSave.PrtNo.value = tPrtNo ;
		fmSave.fmtransact.value = "PRINT";
		fmSave.target = "../f1print";
		fmSave.submit();
		showInfo.close();
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
	var strSQL = "";
	// 书写SQL语句
//	strSQL = "SELECT LWMission.MissionProp3, LWMission.MissionProp2,LWMission.MissionProp4, LWMission.MissionProp5,LWMission.MissionProp6,LWMission.MissionProp7 ,LWMission.MissionProp1,LWMission.MissionID ,LWMission.SubMissionID FROM LWMission WHERE LWMission.ActivityID = (select activityid from lwactivity where functionId = '10047007') "  //ActivityID = '0000001106' 单证类型为承保体检
//	        + "and exists (select 1 from Lwprocess a  where  a.Processid=LWMission.Processid and a.Busitype='1004') and exists (select 1 from loprtmanager where LWMission.MissionProp3 = prtseq and code = '43') " //保全工作流
//	        + getWherePart('LWMission.MissionProp1', 'PrtNo')
//	        + getWherePart('LWMission.MissionProp2', 'ContNo')
//			+ getWherePart('LWMission.MissionProp7', 'ManageCom', 'like')
//			+ getWherePart('LWMission.MissionProp4','AgentCode')
//			+ getWherePart('LWMission.MissionProp5','AgentGroup')
//			+ getWherePart('LWMission.MissionProp6','BranchGroup');
	
	var  PrtNo = window.document.getElementsByName(trim("PrtNo"))[0].value;
    var  ContNo = window.document.getElementsByName(trim("ContNo"))[0].value;
    var  ManageCom = window.document.getElementsByName(trim("ManageCom"))[0].value;
    var  AgentCode = window.document.getElementsByName(trim("AgentCode"))[0].value;
    var  AgentGroup = window.document.getElementsByName(trim("AgentGroup"))[0].value;
    var  BranchGroup = window.document.getElementsByName(trim("BranchGroup"))[0].value;
    var  sqlid1="RnewPENoticePrintInputSql0";
	var  mySql1=new SqlClass();
	mySql1.setResourceName("uw.RnewPENoticePrintInputSql"); //指定使用的properties文件名
	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	mySql1.addSubPara(PrtNo);//指定传入的参数
	mySql1.addSubPara(ContNo);//指定传入的参数
	mySql1.addSubPara(ManageCom);//指定传入的参数
	mySql1.addSubPara(AgentCode);//指定传入的参数
	mySql1.addSubPara(AgentGroup);//指定传入的参数
	mySql1.addSubPara(BranchGroup);//指定传入的参数
	 strSQL=mySql1.getString();

//	alert(strSQL);

	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) 
  {
//      strSQL = "SELECT a.MissionProp3, a.MissionProp2,a.MissionProp4, a.MissionProp5,a.MissionProp6,a.MissionProp7 ,a.MissionProp1,a.MissionID ,a.SubMissionID FROM LWMission a WHERE a.ActivityID = (select activityid from lwactivity where functionId = '10047007') "  //ActivityID = '0000007007' 单证类型为承保体检
//	        + "and exists (select 1 from Lwprocess   where  Processid=a.Processid and Busitype='1004') and exists (select 'X' from loprtmanager where code = '43' and prtseq = a.missionprop3) " //保全工作流
//	        + " and exists(select 1 from lwmission where missionid = a.missionid and activityid = (select activityid from lwactivity where functionId = '10047001') and missionprop7 like '" + fm.ManageCom.value + "')"
//	        + getWherePart('a.MissionProp1', 'PrtNo')
//	        + getWherePart('a.MissionProp2', 'ContNo')
//			+ getWherePart('a.MissionProp4','AgentCode')
//			+ getWherePart('a.MissionProp5','AgentGroup')
//			+ getWherePart('a.MissionProp6','BranchGroup');
      
      var  sqlid2="RnewPENoticePrintInputSql1";
  	  var  mySql2=new SqlClass();
  	  mySql2.setResourceName("uw.RnewPENoticePrintInputSql"); //指定使用的properties文件名
  	  mySql2.setSqlId(sqlid2);//指定使用的Sql的id
  	  mySql2.addSubPara(fm.ManageCom.value);//指定传入的参数
  	  mySql2.addSubPara(prtNo);//指定传入的参数
  	  mySql2.addSubPara(ContNo);//指定传入的参数
  	  mySql2.addSubPara(AgentCode);//指定传入的参数
  	  mySql2.addSubPara(AgentGroup);//指定传入的参数
  	  mySql2.addSubPara(BranchGroup);//指定传入的参数
  	  strSQL=mySql2.getString();
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);
	  if (!turnPage.strQueryResult)
	  {
          alert("没有要打印的续保体检通知书！");
          return false;
      }
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
 //tArr=chooseArray(arrDataSet,[0])
  //调用MULTILINE对象显示查询结果

  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  //displayMultiline(tArr, turnPage.pageDisplayGrid);
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