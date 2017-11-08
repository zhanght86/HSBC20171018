//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";

 var turnPage = new turnPageClass();   
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
	var tSel = ContGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		try
		{       
			var strreturn = getQueryResult();
			top.opener.afterQuery1( strreturn );
			
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
	var tRow = ContGrid.getSelNo();
	if( tRow == 0 || tRow == null || ContGrid == null )
		return arrSelected;
	arrSelected = new Array();
	//设置需要返回的数组
	arrSelected[0] = ContGrid.getRowData(tRow-1);
	var streturn=arrSelected[0][1];
	return streturn;
}

// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initContGrid();
	
	// 书写SQL语句
	var strSQL = "";
	
	  var sqlid1="ContQuerySQL1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.ContQuerySQL"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		
		mySql1.addSubPara(fm.GrpProposalNo.value);//指定传入的参数
		mySql1.addSubPara(fm.PrtNo.value);//指定传入的参数
		mySql1.addSubPara(fm.ManageCom.value);//指定传入的参数
		
		mySql1.addSubPara(fm.AgentCode.value);//指定传入的参数
		mySql1.addSubPara(fm.AgentGroup.value);//指定传入的参数
	    strSQL=mySql1.getString();	
	
//	strSQL = "select ContNo,PrtNo,AgentCode,AppntName from LCCont where 1=1 "
//				 + "and AppFlag='0' "
//				 + " and (InputOperator ='' or InputOperator is null) " //已经录入确认后不让再确认				 
//				 + getWherePart( 'ContNo','GrpProposalNo')
//				 + getWherePart( 'PrtNo' )
//				 + getWherePart( 'ManageCom' )
//				 + getWherePart( 'AgentCode' )
//				 + getWherePart( 'AgentGroup' )
//				 + "order by PrtNo";

	turnPage.queryModal(strSQL, ContGrid);
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		// 初始化表格
		initContGrid();
		//HZM 到此修改
		ContGrid.recordNo = (currBlockIndex - 1) * MAXMEMORYPAGES * MAXSCREENLINES + (currPageIndex - 1) * MAXSCREENLINES;
		ContGrid.loadMulLine(ContGrid.arraySave);		
		//HZM 到此修改
		
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				ContGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
		
		ContGrid.delBlankLine();
	} // end of if
}
