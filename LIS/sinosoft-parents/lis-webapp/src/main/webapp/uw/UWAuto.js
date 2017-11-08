//程序名称：UWAuto.js
//程序功能：个人自动核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var k = 0;

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  initPolGrid();
  document.getElementById("fm").submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content)
{
    
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        content = "自核不通过。" + content;
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
        content = "自核通过。" + content;
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
	
  // 刷新查询结果
    jQuery("#publicSearch").click();
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
	initAllPolGrid();
	
	// 书写SQL语句
	k++;
	var strSQL = "";
	//strSQL = "select ProposalNo,PrtNo,RiskCode,RiskVersion,AppntName,InsuredName from LCPol where "+k+" = "+k
	//			 + " and VERIFYOPERATEPOPEDOM(Riskcode,Managecom,'"+ComCode+"','Pd')=1 "
	//			 + " and AppFlag='0'"          //承保
	//			 + " and UWFlag in ('0')"  //未核保
	//			 + " and LCPol.ApproveFlag = '9' "
	//			 + " and grppolno = '00000000000000000000'"
	//			 + " and contno = '00000000000000000000'"
	//			 + getWherePart( 'ProposalNo' )
	//			 + getWherePart( 'ManageCom', 'ManageCom')
	//			 + getWherePart( 'AgentCode' )
	//			 + getWherePart( 'AgentGroup' )
	//			 + getWherePart( 'RiskCode' )
	//			 + getWherePart( 'PrtNo' )
	//			 + " and ManageCom like '" + ComCode + "%%'";
				 //+ getWherePart( 'RiskVersion' );
	  //查询SQL，返回结果字符串
	
//	strSQL = "select lwmission.MissionProp1,lwmission.MissionProp2,lwmission.MissionProp3,lwmission.MissionProp4,lwmission.MissionProp5,LWMission.MissionID ,LWMission.SubMissionID,LWMission.ActivityID from lwmission where "+k+"="+k
//	        + " and LWMission.ProcessID = '0000000003' " 
//            + " and LWMission.ActivityID = '0000001003' " 
//            + " and defaultoperator is null "
//            + getWherePart('lwmission.MissionProp1','ContNo')
// 			+ getWherePart('lwmission.MissionProp2','PrtNo')
// 			+ getWherePart('lwmission.MissionProp3','AppntName')
// 			+ getWherePart('lwmission.MissionProp4','InsuredName')
// 			+ getWherePart('lwmission.MissionProp5','AgentName')
// 			+ getWherePart('lwmission.MissionProp6','ManageCom')
 	
		 var sqlid1="UWAutoSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.UWAutoSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
//		mySql1.addSubPara(k);//指定传入的参数
//		mySql1.addSubPara(k);//指定传入的参数
		
		mySql1.addSubPara(fm.ContNo.value);//指定传入的参数
		mySql1.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql1.addSubPara(fm.AppntName.value);//指定传入的参数
		
		mySql1.addSubPara(fm.InsuredName.value);//指定传入的参数
		mySql1.addSubPara(fm.AgentName.value);//指定传入的参数
		mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数
	    strSQL=mySql1.getString();	
						 
	turnPage.queryModal(strSQL, AllPolGrid);
	return true;
}

// 查询按钮
function easyQueryClickSelf()
{
	// 初始化表格
	initPolGrid();
	// 书写SQL语句
	k++;
	var strSQL = "";
  
  		 var sqlid2="UWAutoSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.UWAutoSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
//		mySql2.addSubPara(k);//指定传入的参数
//		mySql2.addSubPara(k);//指定传入的参数
		
		mySql2.addSubPara(fm.ContNo.value);//指定传入的参数
		mySql2.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql2.addSubPara(fm.AppntName.value);//指定传入的参数
		
		mySql2.addSubPara(fm.AgentCode.value);//指定传入的参数
		mySql2.addSubPara(fm.ManageCom.value);//指定传入的参数
		mySql2.addSubPara(ComCode);//指定传入的参数
	    strSQL=mySql2.getString();	
  
//	strSQL = "select lwmission.MissionProp1,lwmission.MissionProp2,lwmission.MissionProp3,lwmission.MissionProp4,lwmission.MissionProp5,LWMission.MissionID ,LWMission.SubMissionID,LWMission.ActivityID,LCCont.FirstTrialDate,(select nvl(to_char(min(makedate)),'无扫描件') from es_doc_main where subtype= 'UA001' and DocCode = LCCont.PrtNo),lwmission.makedate,nvl(to_char(to_date(lwmission.makedate) - LCCont.FirstTrialDate),' ') "
//			+ " from lwmission ,LCCont where "+k+"="+k
//	        + " and LWMission.ProcessID = '0000000003' " 
//            + " and LWMission.ActivityID = '0000001003' " 
//            + " and LWMission.MissionProp2 = LCCont.prtno "
//            + getWherePart('lwmission.MissionProp1','ContNo')
// 			+ getWherePart('lwmission.MissionProp2','PrtNo')
// 			+ getWherePart('lwmission.MissionProp3','AppntName')
// 			+ getWherePart('lwmission.MissionProp4','AgentCode')
// 			+ getWherePart('lwmission.MissionProp5','ManageCom','like' )
// 			+ " and MissionProp5 like '" + ComCode + "%%'";
 					 
	turnPage2.queryModal(strSQL, PolGrid);
	return true;
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		// 初始化表格
		initPolGrid();
		
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				PolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

/*********************************************************************
 *  执行自动核保任务
 *  参数  ：  无
 *  返回值：  无
 *  修改人：续涛
 *  时  间：2005-10-12 
 *********************************************************************
 */
function autochk()
{
  var i = 0;
  var showStr="正在传送数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();
  //showSubmitFrame(mDebug);
  //lockScreen('lkscreen');  
  fm.action = "../uw/UWAutoChk.jsp";
  document.getElementById("fm").submit(); //提交
}


/*********************************************************************
 *  执行自动核保任务后返回
 *  参数  ：  无
 *  返回值：  无
 *  修改人：续涛
 *  时  间：2005-10-12 
 *********************************************************************
 */
function afterAutoChk( FlagStr, content )
{
	unlockScreen('lkscreen');  
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        content = "自核不通过。" + content;
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
        content = "自核通过。" + content;    
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
//    easyQueryClick();
   // easyQueryClickSelf();


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


/*********************************************************************
 *  申请自动核保任务
 *  参数  ：  无
 *  返回值：  无
 *  修改人：续涛
 *  时  间：2005-10-12 
 *********************************************************************
 */
function ApplyUW()
{

	var selno = AllPolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请的投保单！");
	      return;
	}

	fm.MissionID.value = AllPolGrid.getRowColData(selno, 6);
	fm.SubMissionID.value = AllPolGrid.getRowColData(selno, 7);
	fm.ActivityID.value = AllPolGrid.getRowColData(selno, 8);
	
	var i = 0;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	fm.action = "../uw/UWAutoApply.jsp";
	document.getElementById("fm").submit(); //提交
}

/*********************************************************************
 *  申请自动核保任务后返回
 *  参数  ：  无
 *  返回值：  无
 *  修改人：续涛
 *  时  间：2005-10-12
 *********************************************************************
 */
function afterApplyUW( FlagStr, content )
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
    }
//    easyQueryClick();
   // easyQueryClickSelf();
}

function queryAgent(gridName,row,col){
	if (gridName.indexOf("Public") != -1) {
		flag = "public";
	} else if (gridName.indexOf("Private") != -1) {
		flag = "private";
	}
	if (document.all('AgentCode').value == "") {
		var newWindow = window.open(
						"../sys/AgentCommonQueryMain.jsp?queryflag=" + flag
								+ "&ManageCom=" + document.all('ManageCom').value
								+ "&row="+row+"&col="+col  );
	}
}

	function afterQuery2(arrResult, queryFlag,row,col) {
		if (arrResult != null) {
			if (queryFlag == "public") {
				PublicWorkPoolQueryGrid.setRowColData(Number(row), Number(col), arrResult[0][0]);
			} else if (queryFlag == "private") {
				PrivateWorkPoolQueryGrid.setRowColData(Number(row), Number(col), arrResult[0][0]);
			}
		}
	}

