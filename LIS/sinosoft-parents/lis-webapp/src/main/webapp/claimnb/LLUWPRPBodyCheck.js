//               该文件中包含客户端需要处理的函数和事件
var arrDataSet;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();



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
  //initPolGrid();
  //showSubmitFrame("1");
  document.getElementById("fm").submit(); //提交
}



////提交，保存按钮对应操作
//function printPol()
//{
//   var tRow = PolGrid.getSelNo();	
//   //alert(tRow);
//  if( tRow == 0 || tRow == null )
//		alert( "请先选择一条记录，再点击补打按钮。" );
//	else
//	{
//		fm.PrtSeq.value = PolGrid.getRowColData(tRow-1,1);
//		fm.Code.value = PolGrid.getRowColData(tRow-1,2);		
//		fm.ClmNo.value = PolGrid.getRowColData(tRow-1,3);
//		fm.BatNo.value = PolGrid.getRowColData(tRow-1,4);
//		fm.ContNo.value = PolGrid.getRowColData(tRow-1,5);
//		fm.MissionID.value = PolGrid.getRowColData(tRow-1,8);
//		fm.SubMissionID.value = PolGrid.getRowColData(tRow-1,9);
//		fm.ActivityID.value = PolGrid.getRowColData(tRow-1,10);
//		fm.fmtransact.value = "CONFIRM";
//		submitForm();
//	}
//}

//提交，保存按钮对应操作
function printPol()
{
   var tRow = PublicWorkPoolGrid.getSelNo();	
   //alert(tRow);
  if( tRow == 0 || tRow == null )
		alert( "请先选择一条记录，再点击补打按钮。" );
	else
	{
		fm.PrtSeq.value = PublicWorkPoolGrid.getRowColData(tRow-1,1);
		fm.Code.value = PublicWorkPoolGrid.getRowColData(tRow-1,2);		
		fm.ClmNo.value = PublicWorkPoolGrid.getRowColData(tRow-1,9);
		fm.BatNo.value = PublicWorkPoolGrid.getRowColData(tRow-1,10);
		fm.ContNo.value = PublicWorkPoolGrid.getRowColData(tRow-1,3);
		fm.MissionID.value = PublicWorkPoolGrid.getRowColData(tRow-1,12);
		fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(tRow-1,13);
		fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(tRow-1,15);
		//alert(fm.Code.value); return false;
		fm.fmtransact.value = "CONFIRM";
		submitForm();
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
	var iHeight=350;     //弹出窗口的高度; 
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
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
  
 // easyQueryClick();
  jQuery("#publicSearch").click();
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
/*function easyQueryClick()
{
	 //初始化表格
	initPolGrid();
	
		 //书写SQL语句
	var strSQL = "";
	//tongmeng 2007-11-29 modify
	//增加核保通知书(非打印类)补打
	strSQL = " select LWMission.MissionProp14,LWMission.MissionProp4,LWMission.MissionProp9,LWMission.MissionProp10,LWMission.MissionProp2,LWMission.MissionProp5,LWMission.MissionProp6 "
	    + " ,LWMission.MissionID,LWMission.SubMissionID,LWMission.ActivityID "
	    + " from LWMission where 1=1"
		+ " and LWMission.ProcessID in ('0000000005')"
		+ " and LWMission.ActivityID in ('0000005532')"
		+ " and LWMission.MissionProp5 like '"+strManageCom+"%%'"
		//+ " and Substr(LWMission.MissionProp5,1,"+len+") = '"+tmanageCom+"'"
		+ getWherePart('LWMission.MissionProp7','StartDay','>=')
		+ getWherePart('LWMission.MissionProp7','EndDay','<=')
		+ getWherePart('LWMission.MissionProp2','ContNoS','like') 
	    + getWherePart('LWMission.MissionProp14','PrtSeqS') 
	    + getWherePart('LWMission.MissionProp4','CodeS')	  ; 
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
*/
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
	
	
	var wherepart = "";
	if(aCode == "LP90")
	   wherepart += "'0000005551'";
	else
	   wherepart += "'0000001280'";
	
//	var tResult = easyExecSql("select 1 from lwmission where missionprop2 = '" + aContNo + "' and missionprop3 = '" + aPrtSeq + "'" + wherepart,1,0);
	
	var sqlid0="LLUWPRPBodyCheckSQL";
	var mySql0=new SqlClass();
	mySql0.setResourceName("claimnb.LLUWPRPBodyCheckSQL0"); //指定使用的properties文件名
	mySql0.setSqlId(sqlid0);//指定使用的Sql的id
	mySql0.addSubPara(aContNo);//指定传入的参数
	mySql0.addSubPara(aPrtSeq);//指定传入的参数
	mySql0.addPara(wherepart);//指定传入的参数
	var strSQL0=mySql0.getString();
	var tResult = easyExecSql(strSQL0,1,0);
	
	if(tResult != null)
	{
		alert("此通知书未打印，请进行正常打印！");
		return false;
	}
	return true;
		
}