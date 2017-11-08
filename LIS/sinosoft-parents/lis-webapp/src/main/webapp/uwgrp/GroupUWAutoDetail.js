//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.GroupUWAutoDetailSql";

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
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
	
	parent.fraMain.rows = "0,0,0,0,*";
}

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = GrpPolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			window.open("./AppGrpPolQuery.jsp","",sFeatures);
		}
		catch(ex)
		{
			alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = GrpPolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = arrGrid[tRow-1];
	return arrSelected;
}

// 查询按钮
var queryBug = 1;
function easyQueryClick()
{
	// 初始化表格
	initGrpPolGrid();
	if (contNo==null||contNo=='')
		contNo = "00000000000000000000";
	// 书写SQL语句
	var strSQL = "";
	/*
	var strSQL = "select ProposalGrpContNo,PrtNo,GrpName ,AppntNo ,prem from LCGrpCont where " + queryBug + "=" + queryBug
    				 + " and AppFlag='0' "   
    				 + " and PrtNo='" + grpprtno + "'" 
    				 + " and approveflag = '9'"
    				 + " and uwflag = '0'"
    				 + " order by makedate,maketime"; 
    				 */           
   	var sqlid1="GroupUWAutoDetailSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(queryBug);
		mySql1.addSubPara(queryBug);
		mySql1.addSubPara(grpprtno);
    //alert(strSQL);
	execEasyQuery( mySql1.getString() );
}


function displayEasyResult( arrResult )
{
	var i, j, m, n;
    
	if( arrResult == null || arrResult.length == 0 )
		alert( "该团单的自动核保工作已完成!" );
	else
	{
		// 初始化表格
		initGrpPolGrid();
		//HZM 到此修改
		GrpPolGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
		GrpPolGrid.loadMulLine(GrpPolGrid.arraySave);		
		//HZM 到此修改
		
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			GrpPolGrid.addOne();
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
			  GrpPolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
		
		//GrpPolGrid.delBlankLine();
	} // end of if
}


/*********************************************************************
 *  返回到自动核保查询主界面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 function GoBack()
 {
 	//top.opener.easyQueryClick();
 	top.close();
 }

/*********************************************************************
 *   自动核保所选投保单
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function GrpUWAutoMakeSure(){
	  var i = 0;
	  var checkFlag = 0;
	  var cGrpProposalNo = "";
	  var cGrpPrtNo="";
	  var cflag = "2";
	  
	  for (i=0; i<GrpPolGrid.mulLineCount; i++) {
	    if (GrpPolGrid.getSelNo(i)) { 
	      checkFlag = GrpPolGrid.getSelNo();
	      break;
	    }
	  }
	 
	  if (checkFlag) { 
	  	cGrpProposalNo = GrpPolGrid.getRowColData(checkFlag - 1, 1);
	  	cGrpPrtNo = GrpPolGrid.getRowColData(checkFlag - 1, 2);
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
	 fm.GrpProposalNo.value = cGrpProposalNo ;
	 fm.GrpPrtNo.value = cGrpPrtNo ;
	 fm.submit();
	 
}
/*********************************************************************
 *  集体投保单复核的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
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
		top.opener.easyQueryClick();		
	}
}
