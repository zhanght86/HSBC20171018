//               该文件中包含客户端需要处理的函数和事件
var arrDataSet
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var manageCom;

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

	showInfo.focus();  initPolGrid();
  fm.submit(); //提交
}

//提交，保存按钮对应操作
function printPol()
{
  var i = 0;

  var arrReturn = new Array();
  var tSel = PolGrid.getSelNo();
  if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		PrtSeq = PolGrid.getRowColData(tSel-1,1);

		if( null == arrReturn ) {
			alert("无效的数据");
			return;
		}

		tPrtSeq = PolGrid.getRowColData(tSel-1,1);
		tMissionID = PolGrid.getRowColData(tSel-1,6);
		tSubMissionID = PolGrid.getRowColData(tSel-1,7);
		tPrtNo = PolGrid.getRowColData(tSel-1,2);
		tContNo = PolGrid.getRowColData(tSel-1,2);
		tOldPrtSeq = PolGrid.getRowColData(tSel-1,8);
		tActivityID = PolGrid.getRowColData(tSel-1,9);
		//alert(ContNo);
		fmSave.PrtSeq.value = tPrtSeq;
		fmSave.MissionID.value = tMissionID;
		fmSave.SubMissionID.value = tSubMissionID;
		fmSave.OldPrtSeq.value = tOldPrtSeq;
		fmSave.PrtNo.value = tPrtNo;
		fmSave.ContNo.value = tContNo ;
		fmSave.ActivityID.value =tActivityID
		fmSave.fmtransact.value = "PRINT";
		//fmSave.target = "../f1print";

		fmSave.submit();
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

//提交前的校验、计算
function beforeSubmit()
{
  //添加操作
}

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	// 书写SQL语句
	var strSQL = "";
	// 书写SQL语句
//	strSQL = "SELECT LWMission.MissionProp3, LWMission.MissionProp2,LWMission.MissionProp4,LWMission.MissionProp7 ,LWMission.MissionProp13,LWMission.MissionID ,LWMission.SubMissionID,LWMission.MissionProp14,LWMission.ActivityID FROM LWMission WHERE LWMission.ActivityID in( select Activityid from lwactivity where functionid='"+fmSave.FunctionID.value+"') "  //ActivityID = '0000001108' 单证类型为续保生调      
//	            + getWherePart('LWMission.MissionProp2', 'ContNo')
//		    	+ getWherePart('LWMission.MissionProp7', 'ManageCom', 'like')
//			    + getWherePart('LWMission.MissionProp4','AgentCode')
//			    + getWherePart('LWMission.MissionProp13','SaleChnl')
//		      ;
	
	   var  FunctionID1= fmSave.FunctionID.value;
	   var  ContNo1 = window.document.getElementsByName(trim("ContNo"))[0].value;
	   var  ManageCom1 = window.document.getElementsByName(trim("ManageCom"))[0].value;
	   var  AgentCode1 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	   var  SaleChnl1 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
	   var sqlid1="BQMeetSql1";
	   var mySql1=new SqlClass();
	   mySql1.setResourceName("uw.BQMeetSql");//指定使用的properties文件名
	   mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	   mySql1.addSubPara(FunctionID1);//指定传入参数
	   mySql1.addSubPara(ContNo1);//指定传入参数
	   mySql1.addSubPara(ManageCom1);//指定传入参数
	   mySql1.addSubPara(AgentCode1);//指定传入参数
	   mySql1.addSubPara(SaleChnl1);//指定传入参数
	   strSQL = mySql1.getString();

	turnPage.strQueryResult  = easyQueryVer3(strSQL);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有要打印的面见通知书！");
    return false;
  }
  
turnPage.queryModal(strSQL, PolGrid);

//  //查询成功则拆分字符串，返回二维数组
//  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult)
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
//  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
//  //tArr = chooseArray(arrDataSet,[0,1,3,4])
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