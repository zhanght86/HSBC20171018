//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass(); 
var arrDataSet;

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
function getQueryDetail()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录。"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,2);				
		parent.VD.gVSwitch.deleteVar("PolNo");				
		parent.VD.gVSwitch.addVar("PolNo","",cPolNo);
		
		if (cPolNo == "")
		    return;
		    
		var GrpPolNo = PolGrid.getRowColData(tSel-1,1);
        
        //alert("dfdf");
        if( tIsCancelPolFlag == "0"){
	    	if (GrpPolNo =="00000000000000000000") {
	    	 	window.open("./AllProQueryMain.jsp?LoadFlag=3","window1");	
		    } else {
			window.open("./AllProQueryMain.jsp?LoadFlag=4");	
		    }
		} else {
		if( tIsCancelPolFlag == "1"){//销户保单查询
			if (GrpPolNo =="00000000000000000000")   {
	    	    window.open("./AllProQueryMain_B.jsp?LoadFlag=6","window1");	
			} else {
				window.open("./AllProQueryMain_B.jsp?LoadFlag=7");	
			}
	    } else {
	    	myAlert(""+"保单类型传输错误!"+"");
	    	return ;
	    }
	 }
 }
}

//销户保单的查询函数
function getQueryDetail_B()
{
	
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录。"+"" );
	else
	{
	  var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
		parent.VD.gVSwitch.deleteVar("PolNo");				
		parent.VD.gVSwitch.addVar("PolNo","",cPolNo);
		if (cPolNo == "")
			return;
		var GrpPolNo = PolGrid.getRowColData(tSel-1,6);
	    if (GrpPolNo =="00000000000000000000") 
	    {
	    	    window.open("./AllProQueryMain_B.jsp?LoadFlag=6","window1");	
			} 
			else 
			{
				window.open("./AllProQueryMain_B.jsp?LoadFlag=7");	
			}
	}
}



// 保单明细查询
function PolClick()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录，再点击返回按钮。"+"" );
	else
	{
	    var cPolNo = PolGrid.getRowColData(tSel - 1,1);				
		if (cPolNo == "")
		    return;		
		
	    window.open("RePolDetailQueryMain.jsp?PolNo=" + cPolNo );	
	}
}

// 查询按钮
function easyQueryClick()
{
    
	// 初始化表格
	initPolGrid();
	
	// 书写SQL语句
	var pram=getWherePart( 'PolNo' )+ getWherePart( 'GrpPolNo' )+ getWherePart( 'RiskCode' )+ getWherePart( 'CessStart' )+ getWherePart( 'ReinsurItem' )+ getWherePart( 'ProtItem' )+ getWherePart( 'InsuredName' )+ getWherePart( 'InsuredNo' )+ getWherePart( 'InsuredBirthday' )+ getWherePart( 'InsuredYear' );
	var strSQL = "";
	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.AllReinsurPolQuerySql"); //指定使用的properties文件名
		mySql100.setSqlId("AllReinsurPolQuerySql100");//指定使用的Sql的id
		mySql100.addSubPara(pram);//指定传入的参数
	    strSQL=mySql100.getString();
	/**
	strSQL = "select PolNo,ReinsurItem,GrpPolNo,RiskCode,SignDate,CValiDate,InsuredName,StandPrem,RiskAmnt,CessionRate,CessionAmount,CessPrem,CessComm,ExPrem,ExCessPrem,ExCessComm,InsuredAppAge,InsuredSex from LRPol where 1=1  "
				 + getWherePart( 'PolNo' )
				 + getWherePart( 'GrpPolNo' )
				 + getWherePart( 'RiskCode' )
				 + getWherePart( 'CessStart' )
				 + getWherePart( 'ReinsurItem' )
				 + getWherePart( 'ProtItem' )
				 + getWherePart( 'InsuredName' )
				 + getWherePart( 'InsuredNo' )
				 + getWherePart( 'InsuredBirthday' )
				 + getWherePart( 'InsuredYear' )
//				 + " and ManageCom like '" + comCode + "%%'"
				 + " order by InsuredNo,signdate";
		 
//	alert(strSQL);
*/
	//查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    myAlert(""+"查询失败！"+"");
    return false;
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  //showCodeName();
}




// 数据返回父窗口
function returnParent()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	//alert(tSel);
	if( tSel == 0 || tSel == null )
		myAlert( ""+"请先选择一条记录，再点击返回按钮。"+"" );
	else
	{
		try
		{
			arrReturn = getQueryResult();
			//alert(arrReturn);
			//alert("返回"+arrReturn);
			top.opener.afterQuery( arrReturn );
			//alert("333");
			top.close();
		}
		catch(ex)
		{
			myAlert( ""+"请先选择一条非空记录，再点击返回按钮。"+"");
			//alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	var tRow = PolGrid.getSelNo();
	//alert(arrGrid);
	//alert("safsf");
	if( tRow == 0 || tRow == null || arrDataSet == null )
		      return arrSelected;
	
	arrSelected = new Array();
	arrSelected[0] = new Array();
	arrSelected[0] = PolGrid.getRowData(tRow-1);
	//alert(arrSelected[0][1]);
	//tRow = 10 * turnPage.pageIndex + tRow; //10代表multiline的行数
	//设置需要返回的数组
	//arrSelected[0] = turnPage.arrDataCacheSet[tRow-1];
	//设置需要返回的数组
	//arrSelected[0] = arrDataSet[tRow-1];
	//alert(arrDataSet[tRow-1]);
	return arrSelected;
}
