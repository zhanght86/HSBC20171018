//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initCollectivityGrid();
	
	// 书写SQL语句
	var strSQL = "";
	var strOperate = "like";
	
		var sqlid30="ContQuerySQL30";
		var mySql30=new SqlClass();
		mySql30.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql30.setSqlId(sqlid30);//指定使用的Sql的id
		mySql30.addSubPara(fm.GrpNo.value);//指定传入的参数
		mySql30.addSubPara(fm.GrpName.value);//指定传入的参数
		mySql30.addSubPara(fm.GrpNature.value);//指定传入的参数
	    strSQL=mySql30.getString();	
	
//	strSQL = "select customerno,GrpName,GrpNature from LDGrp  "
//	         +" where 1=1 "
//				 + getWherePart( 'customerno','GrpNo',strOperate )
//				 + getWherePart( 'GrpName','GrpName',strOperate )
//				 + getWherePart( 'GrpNature','GrpNature',strOperate );
//alert(strSQL);
	//execEasyQuery( strSQL );
	turnPage.queryModal(strSQL, CollectivityGrid); 
	//turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		// 初始化表格
		initCollectivityGrid();
		CollectivityGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
		CollectivityGrid.loadMulLine(CollectivityGrid.arraySave);
		
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				CollectivityGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
		
		//CollectivityGrid.delBlankLine();
	} // end of if
}

// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = CollectivityGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			
			top.opener.afterQuery4( arrReturn );
			top.close();
		}
		catch(ex)
		{
			//alert( "请先选择一条非空记录，再点击返回按钮。");
			alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = CollectivityGrid.getSelNo();

	if( tRow == 0 || tRow == null)
	
		{
			//alert(1); 
			return arrSelected;
    }
  else
  	
	{
		 
	arrSelected = new Array();
	//设置需要返回的数组
  var strSQL = "";
  
  		var sqlid31="ContQuerySQL31";
		var mySql31=new SqlClass();
		mySql31.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql31.setSqlId(sqlid31);//指定使用的Sql的id
		mySql31.addSubPara(CollectivityGrid.getRowColData(tRow-1,1));//指定传入的参数
	    strSQL=mySql31.getString();	
  
//	strSQL = "select CustomerNo,grpname,Asset,GrpNature,BusinessType,Peoples,Fax,vipvalue,blacklistflag from ldgrp where CustomerNo='"+
//	          CollectivityGrid.getRowColData(tRow-1,1)+"'";
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("查询失败！");
    return false;
    }
//查询成功则拆分字符串，返回二维数组
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);

	return arrSelected; 
}
}
