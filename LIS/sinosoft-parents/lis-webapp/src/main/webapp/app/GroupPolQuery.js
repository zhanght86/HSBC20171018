//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";


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
			arrReturn = getQueryResult();
			top.opener.afterQuery( arrReturn );
		}
		catch(ex)
		{
			alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
		top.close();
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
	//alert(arrSelected[0]);
	return arrSelected;
}

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initGrpPolGrid();
	
	// 书写SQL语句
	var strSQL = "";
	
			  var sqlid25="ContQuerySQL25";
		var mySql25=new SqlClass();
		mySql25.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql25.setSqlId(sqlid25);//指定使用的Sql的id
		mySql25.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql25.addSubPara(fm.GrpProposalNo.value);//指定传入的参数
		mySql25.addSubPara(fm.ManageCom.value);//指定传入的参数
		
		mySql25.addSubPara(fm.AgentCode.value);//指定传入的参数
		mySql25.addSubPara(fm.AgentGroup.value);//指定传入的参数
		mySql25.addSubPara(fm.SaleChnl.value);//指定传入的参数
		mySql25.addSubPara(comcode);//指定传入的参数
	    strSQL=mySql25.getString();	
	
//	strSQL = "select ProposalGrpContNo,PrtNo,SaleChnl,GrpName,CValiDate from LCGrpCont where 1=1 "
//	       + " and appflag='0' and approveflag='0' and uwflag='0'"//未复核，未自动核保，未人工核保，未签单
//				 + " and (InputOperator ='' or InputOperator is null) " //已经录入确认后不让再确认
//				 + getWherePart( 'PrtNo' )
//				 + getWherePart( 'ProposalGrpContNo','GrpProposalNo' )
//				 + getWherePart( 'ManageCom' )
//				 + getWherePart( 'AgentCode' )
//				 + getWherePart( 'AgentGroup' )
//				 + getWherePart( 'SaleChnl' )
//				 + " and ManageCom like '" + comcode + "%%'";  //集中权限管理体现	
//				 + " order by PrtNo " ;
        //alert(strSQL);
	execEasyQuery( strSQL );
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
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
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				GrpPolGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
		
		GrpPolGrid.delBlankLine();
	} // end of if
}
