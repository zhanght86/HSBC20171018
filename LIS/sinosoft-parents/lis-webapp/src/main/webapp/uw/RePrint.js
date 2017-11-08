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
  lockScreen('lkscreen');  
  //showSubmitFrame("1");
  fmSave.submit(); //提交
}



//提交，保存按钮对应操作
function printPol()
{
  var tRow = PolGrid.getSelNo();	
  if( tRow == 0 || tRow == null )
		alert( "请先选择一条记录，再点击补打按钮。" );
	else
	{
		fmSave.PrtSeq.value = PolGrid.getRowColData(tRow-1,1);
		fmSave.Code.value = PolGrid.getRowColData(tRow-1,2);
		fmSave.ContNo.value = PolGrid.getRowColData(tRow-1,3);
		fmSave.MissionID.value = PolGrid.getRowColData(tRow-1,7);
		fmSave.SubMissionID.value = PolGrid.getRowColData(tRow-1,8);
		fmSave.PrtNo.value = PolGrid.getRowColData(tRow-1,6);
		fmSave.ActivityID.value = PolGrid.getRowColData(tRow-1,9);
		fmSave.fmtransact.value = "CONFIRM";
		submitForm();
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
	unlockScreen('lkscreen');  
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
	 //初始化表格
	initPolGrid();
	
		 //书写SQL语句
	var strSQL = "";
	var len = tmanageCom.length;
	//tongmeng 2007-11-29 modify
	//增加核保通知书(非打印类)补打
//	strSQL = " select LWMission.MissionProp14,LWMission.MissionProp4,LWMission.MissionProp2,LWMission.MissionProp5,LWMission.MissionProp6,LWMission.MissionProp1,LWMission.MissionID,LWMission.SubMissionID,activityid from LWMission where 1=1"
//		//+ " and LWMission.ProcessID in ('0000000003','0000000004')"
//		//+ " and LWMission.ActivityID in ('0000001114','0000001024','0000001115','0000001116','0000001018','0000001290','0000002210','0000002114','0000002314','0000001210','0000001240','0000002240','0000001301')"
//		+ " and lwmission.activityid in (select lwactivity.activityid from lwactivity where functionid in ('10010008','10010051','10010059','10010032','10010033','10010043','10010046','10010034','20010019','20010014')) "
//		+ " and LWMission.MissionProp5 like '"+tmanageCom+"%%'"
//		//+ " and Substr(LWMission.MissionProp5,1,"+len+") = '"+tmanageCom+"'"
//		+ getWherePart('LWMission.MissionProp7','StartDay','>=')
//		+ getWherePart('LWMission.MissionProp7','EndDay','<=')
//		+ getWherePart('LWMission.MissionProp2','OtherNo','like') 
//	    + getWherePart('LWMission.MissionProp6','AgentCode') 
//	    + getWherePart('LWMission.MissionProp3','PrtSeq') 
//	    + getWherePart('LWMission.MissionProp4','Code')
//        + " union "
//        + "  select a.prtseq,a.code,a.otherno,a.managecom,a.agentcode,a.otherno,'TBJB','1','' "
//	      + " from loprtmanager a "
//	      + " where 1 = 1 "
//		    + " and a.standbyflag7 = 'TBJB' "
//		    + " and a.stateflag in ( '1','3') "
//		    //+ " and (patchflag is null or patchflag<>'1')"
//		    + " and a.managecom like '"+tmanageCom+"%%' "
//		    + getWherePart('a.DoneDate','StartDay','>=')
//			+ getWherePart('a.DoneDate','EndDay','<=')
//			+ getWherePart('a.otherno','OtherNo','like') 
//	    + getWherePart('a.agentcode','AgentCode') 
//	    + getWherePart('a.prtseq','PrtSeq') 
//	    + getWherePart('a.code','Code')
//	    + " union "
//	    + "  select a.prtseq,a.code,a.otherno,a.managecom,a.agentcode,a.otherno,'TBJB','1','' "
//	      + " from loprtmanager a "
//	      + " where 1 = 1 "
//		    + " and a.code in ('08') "
//		    + " and a.stateflag in ( '1','3') "
//		    //+ " and (patchflag is null or patchflag<>'1')"
//		    + " and a.managecom like '"+tmanageCom+"%%' "
//		    + getWherePart('a.DoneDate','StartDay','>=')
//			+ getWherePart('a.DoneDate','EndDay','<=')
//			+ getWherePart('a.otherno','OtherNo','like') 
//	    + getWherePart('a.agentcode','AgentCode') 
//	    + getWherePart('a.prtseq','PrtSeq') 
//	    + getWherePart('a.code','Code')
//	    
//	    + " union "
//	    + "  select a.prtseq,a.code,a.otherno,a.managecom,a.agentcode,a.otherno,'TBJB','1','' "
//	    + " from loprtmanager a "
//	    + " where 1 = 1 "
//		+ " and a.code in ('21') "
//		+ " and a.stateflag in ( '1','3') "
//		+ " and not exists(select 1 from ljtempfee where otherno = a.otherno and enteraccdate is not null) "
//		+ " and not exists(select 1 from lccont where prtno = a.otherno and (appflag = '1' or uwflag = 'a')) "
//		+ " and a.managecom like '"+tmanageCom+"%%' "
//		+ getWherePart('a.DoneDate','StartDay','>=')
//		+ getWherePart('a.DoneDate','EndDay','<=')
//		+ getWherePart('a.otherno','OtherNo','like') 
//	    + getWherePart('a.agentcode','AgentCode') 
//	    + getWherePart('a.prtseq','PrtSeq') 
//	    + getWherePart('a.code','Code')
//
//	  ;
	
	 var  StartDay = window.document.getElementsByName(trim("StartDate"))[0].value;
     var  EndDay = window.document.getElementsByName(trim("EndDate"))[0].value;
     var  OtherNo = window.document.getElementsByName(trim("OtherNo"))[0].value;
     var  AgentCode = window.document.getElementsByName(trim("AgentCode"))[0].value;
     var  PrtSeq = window.document.getElementsByName(trim("PrtSeq"))[0].value;
     var  Code = window.document.getElementsByName(trim("Code"))[0].value;
 	   var  sqlid1="RePrintSql0";
 	   var  mySql1=new SqlClass();
 	   mySql1.setResourceName("uw.RePrintSql"); //指定使用的properties文件名
 	   mySql1.setSqlId(sqlid1);//指定使用的Sql的id
 	   mySql1.addSubPara(tmanageCom);//指定传入的参数
 	   mySql1.addSubPara(StartDay);//指定传入的参数
 	   mySql1.addSubPara(EndDay);//指定传入的参数
 	   mySql1.addSubPara(OtherNo);//指定传入的参数
 	   mySql1.addSubPara(AgentCode);//指定传入的参数
 	   mySql1.addSubPara(PrtSeq);//指定传入的参数
	   mySql1.addSubPara(Code);//指定传入的参数
	   mySql1.addSubPara(tmanageCom);//指定传入的参数
 	   mySql1.addSubPara(StartDay);//指定传入的参数
 	   mySql1.addSubPara(EndDay);//指定传入的参数
 	   mySql1.addSubPara(OtherNo);//指定传入的参数
 	   mySql1.addSubPara(AgentCode);//指定传入的参数
 	   mySql1.addSubPara(PrtSeq);//指定传入的参数
	   mySql1.addSubPara(Code);//指定传入的参数
	   mySql1.addSubPara(tmanageCom);//指定传入的参数
 	   mySql1.addSubPara(StartDay);//指定传入的参数
 	   mySql1.addSubPara(EndDay);//指定传入的参数
 	   mySql1.addSubPara(OtherNo);//指定传入的参数
 	   mySql1.addSubPara(AgentCode);//指定传入的参数
 	   mySql1.addSubPara(PrtSeq);//指定传入的参数
	   mySql1.addSubPara(Code);//指定传入的参数
	   mySql1.addSubPara(tmanageCom);//指定传入的参数
 	   mySql1.addSubPara(StartDay);//指定传入的参数
 	   mySql1.addSubPara(EndDay);//指定传入的参数
 	   mySql1.addSubPara(OtherNo);//指定传入的参数
 	   mySql1.addSubPara(AgentCode);//指定传入的参数
 	   mySql1.addSubPara(PrtSeq);//指定传入的参数
	   mySql1.addSubPara(Code);//指定传入的参数
 	   strSQL=mySql1.getString();
	  //tongmeng 2009-06-03 modify
	  //修改查询逻辑
	  turnPage.queryModal(strSQL,PolGrid);
	  if (!turnPage.strQueryResult) {
   alert("没有要提交补打通知书的信息！");
    return false;
  }
	 /* 
	turnPage.strQueryResult  = easyQueryVer3(strSQL);  
  
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
  */
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