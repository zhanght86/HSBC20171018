//程序名称：GroupUWAuto.js
//程序功能：集体自动核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件

//var showInfo = "";
var mDebug="0";
var turnPage = new turnPageClass();
var k = 0;
var cflag = "1";  //问题件操作位置 1.核保
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.GroupUWModifySql";
//自动核保提交，
function submitForm()
{
	var tSel = GrpGrid.getSelNo();
	var cGrpPolNo = "";
	var cGrpPrtNo = "";
	if( tSel != null && tSel != 0 )
	{
		cGrpPolNo = GrpGrid.getRowColData( tSel - 1, 1 );
	  cGrpPrtNo = GrpGrid.getRowColData( tSel - 1, 2 );
    }
	
	if( cGrpPolNo == null || cGrpPolNo == ""|| cGrpPrtNo == null || cGrpPrtNo == "" )
		alert("请选择一张集体投保单后，再进行自动核保操作");
	else
	{
		window.open( "./GroupUWAutoDetailMain.jsp?ProposalGrpContNo=" + cGrpPolNo + "&PrtNo=" + cGrpPrtNo ,"",sFeatures);
	}
  
}

function SetSpecialFlag()
{
    var tSel = GrpGrid.getSelNo();
	var cGrpPolNo = "";
	var cGrpPrtNo = "";
	if( tSel != null && tSel != 0 )
	{
		cGrpPolNo = GrpGrid.getRowColData( tSel - 1, 1 );
	    cGrpPrtNo = GrpGrid.getRowColData( tSel - 1, 2 );
    }
	
	if( cGrpPolNo == null || cGrpPolNo == ""|| cGrpPrtNo == null || cGrpPrtNo == "" )
		alert("请选择一张集体投保单后，再进行设置特殊标志操作");
	else
	{
		window.open( "./GroupUWSetSpecialFlag.jsp?GrpProposalNo=" + cGrpPolNo + "&GrpPrtNo=" + cGrpPrtNo ,"",sFeatures);
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

//取消按钮对应操作
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作	
}           

//Click事件，当点击增加图片时触发该函数
function addClick()
{
  //下面增加相应的代码
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
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

//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
	parent.fraMain.rows = "0,0,0,0,*";
  }
  else
  {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
	
  parent.fraMain.rows = "0,0,0,0,*";
}

function returnParent()
{
    tPolNo = "00000120020110000050";
    top.location.href="./ProposalQueryDetail.jsp?PolNo="+tPolNo;
}

function easyQueryClick()
{
	// 初始化表格
	
	initGrpGrid();

	// 书写SQL语句
	
	var strSQL = "";
        var strOperate = "like";
        if(comcode=="%")
	{/*
		strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop7,lwmission.missionprop4,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid from lwmission where 1=1 "
				 + " and activityid = '0000002005' "
				 + " and processid = '0000000004'"
				 + getWherePart('missionprop1','ProposalGrpContNo',strOperate)
				 //+ getWherePart('missionprop5','AgentCode',strOperate)
				 + getWherePart('missionprop4','ManageCom',strOperate)
				 //+ getWherePart('missionprop6','AgentGroup',strOperate)
				 + getWherePart('missionprop9','GrpNo',strOperate)
				 + getWherePart('missionprop7','Name',strOperate)
				 //+ " and LWMission.MissionProp4 like '" + comcode + "%'";  //集中权限管理体现					 
				 + "order by lwmission.missionprop2"
				 ;
				*/ 
		var sqlid1="GroupUWModifySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(fm.ProposalGrpContNo.value); 
		mySql1.addSubPara(fm.ManageCom.value); 
		mySql1.addSubPara(fm.GrpNo.value); 
		mySql1.addSubPara(fm.Name.value); 
				 
			strSQL = mySql1.getString();	 
				 
				 
				 
				}else{
				/*
					strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop7,lwmission.missionprop4,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid from lwmission where 1=1 "
				 + " and activityid = '0000002005' "
				 + " and processid = '0000000004'"
				 + getWherePart('missionprop1','ProposalGrpContNo',strOperate)
				 //+ getWherePart('missionprop5','AgentCode',strOperate)
				 + getWherePart('missionprop4','ManageCom',strOperate)
				 //+ getWherePart('missionprop6','AgentGroup',strOperate)
				 + getWherePart('missionprop9','GrpNo',strOperate)
				 + getWherePart('missionprop7','Name',strOperate)
				 + " and LWMission.MissionProp4 like '" + comcode + "%%'"  //集中权限管理体现					 
				 + "order by lwmission.missionprop2"
				 ;
				 */
				var sqlid2="GroupUWModifySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(fm.ProposalGrpContNo.value); 
		mySql2.addSubPara(fm.ManageCom.value); 
		mySql2.addSubPara(fm.GrpNo.value); 
		mySql2.addSubPara(fm.Name.value); 	
		mySql2.addSubPara(comcode); 
		
			strSQL = mySql2.getString();
				}
			
      //alert(strSQL);
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有需要自动核保集体单！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = GrpGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
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
		initGrpGrid();
		
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				GrpGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}

function GrpUWModify(){
	//只能是四位区站操作
	var tLine=manageCom.length;
	if(tLine<4)
	{
		alert("只能在四位代码的机构进行系统操作！");
		return false;
	}
	
	  var i = 0;
	  var checkFlag = 0;
	  var cGrpProposalNo = "";
	  var cGrpPrtNo="";
	  var cflag = "2";
	  
	  for (i=0; i<GrpGrid.mulLineCount; i++) {
	    if (GrpGrid.getSelNo(i)) { 
	      checkFlag = GrpGrid.getSelNo();
	      break;
	    }
	  }
	 
	  if (checkFlag) { 
	  	cGrpProposalNo = GrpGrid.getRowColData(checkFlag - 1, 1);
	  	cGrpPrtNo = GrpGrid.getRowColData(checkFlag - 1, 2);
	  	fm.GrpContNo.value = cGrpProposalNo;
	  	fm.MissionID.value = GrpGrid.getRowColData(checkFlag - 1, 6);
	  	fm.SubMissionID.value = GrpGrid.getRowColData(checkFlag - 1, 7);
	  }
	  else {
	    alert("请先选择一条有效的保单信息！"); 
	    return false;
	  }
	  if( cGrpPrtNo == null || trim(cGrpPrtNo)=='' || cGrpProposalNo == null || trim(cGrpProposalNo)== '' )
	  {
	    alert("请先选择一条有效的保单信息！"); 
	    return false;
	  }
	 fm.ProposalGrpContNo.value = cGrpProposalNo ;
//	 fm.PrtNo.value = cGrpPrtNo ;
lockScreen('lkscreen');  
	 document.getElementById("fm").submit();
	 
}

/*********************************************************************
 *  集体投保单复核的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	//showInfo.close();
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
	if( FlagStr == "Fail" )
	{             
		
		//showInfo=showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
	}
	else
	{ 
		// 刷新查询结果
		//showInfo=showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		 //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
		easyQueryClick();
		//easyQueryClick_noAlert();
	}
}


function easyQueryClick_noAlert()
{
	// 初始化表格
	
	initGrpGrid();

	// 书写SQL语句
	var strSQL = "";
/*
		strSQL = "select lwmission.missionprop1,lwmission.missionprop2,lwmission.missionprop7,lwmission.missionprop4,lwmission.missionprop5,lwmission.missionid,lwmission.submissionid from lwmission where 1=1 "
				 + " and activityid = '0000002005' "
				 + " and processid = '0000000004'"
				 + getWherePart('missionprop1','ProposalGrpContNo')
				 + getWherePart('missionprop5','AgentCode')
				 + getWherePart('missionprop4','ManageCom')
				 + getWherePart('missionprop6','AgentGroup')
				 + getWherePart('missionprop9','GrpNo')
				 + "order by lwmission.missionprop2"
				 ;	 
				 */
		var sqlid3="GroupUWModifySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(fm.ProposalGrpContNo.value); 
		mySql3.addSubPara(fm.AgentCode.value); 
		mySql3.addSubPara(fm.ManageCom.value); 
		mySql3.addSubPara(fm.AgentGroup.value); 	
		mySql3.addSubPara(fm.GrpNo.value); 	
      //alert(strSQL);
	  turnPage.strQueryResult  = easyQueryVer3(mySql3.getString(), 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert("没有需要自动核保集体单！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = GrpGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
}
