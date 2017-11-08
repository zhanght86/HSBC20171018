//               该文件中包含客户端需要处理的函数和事件

//Creator :范昕	
//Date :2008-08-18

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();

//初始化页面
function queryCostDataKeyDefInputGrid()
{
	try
	{
		initCostDataKeyDefInputGrid();
		var strSQL = ""; 
		/**
		strSQL = "select a.VersionNo,a.AcquisitionID,( select b.ColumnMark from FITableColumnDef b where b.ColumnID=a.KeyID and tableid='FIAboriginalData'),a.KeyName,a.KeyOrder,a.Remark from FICostDataKeyDef a where ";
		strSQL = strSQL + " a.VersionNo ='"+VersionNo+"' and a.AcquisitionID ='"+AcquisitionID+"' order by a.KeyOrder ASC ";
  		*/
  		var mySql1=new SqlClass();
			mySql1.setResourceName("fininterface.CostDataKeyDefInputSql"); //指定使用的properties文件名
			mySql1.setSqlId("CostDataKeyDefInputSql1");//指定使用的Sql的id
			mySql1.addSubPara(VersionNo);//指定传入的参数
			mySql1.addSubPara(AcquisitionID);//指定传入的参数
			strSQL= mySql1.getString();
  		
  	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  	if (!turnPage.strQueryResult)
		{
			return false;
		}

		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		turnPage.pageLineNum = 30 ;
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//设置初始化过的MULTILINE对象
		turnPage.pageDisplayGrid = CostDataKeyDefInputGrid;
		//保存SQL语句
		turnPage.strQuerySql = strSQL ;
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
		//调用MULTILINE对象显示查询结果
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  
  	catch(Ex)
	{
		alert(Ex.message);
	}
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



//Click事件，当点击“添加”图片时触发该函数
function submitForm()
{
	if(!beforeSubmit())
	{
		return false;
	}
  fm.OperateType.value = "INSERT||MAIN";
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.action = './CostDataKeyDefSave.jsp';
  document.getElementById("fm").submit(); //提交
}

//Click事件，当点击“修改”图片时触发该函数
function updateClick()
{
	if(!beforeSubmit2())
	{
		return false;
	}
	if(!beforeSubmit())
	{
		return false;
	}
  if (confirm("您确实想修改该记录吗?"))
   {
    fm.OperateType.value = "UPDATE||MAIN";
    var i = 0;
    var showStr="正在更新数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = './CostDataKeyDefSave.jsp';
    document.getElementById("fm").submit(); //提交
  }
  else
  {
    fm.OperateType.value = "";
    alert("您取消了修改操作！");
  }
}

//Click事件，当点击“删除”图片时触发该函数
function deleteClick()
{
	if(!beforeSubmit2())
	{
		return false;
	}
  if ((document.all("KeyID").value==null)||(document.all("KeyID").value==''))
  {
    alert("请确定要删除的主键编号！");
    return false;
  }
   if(document.all("KeyID").value != document.all('KeyID1').value)
	{
		alert("请确定要删除的数据源编号！");
		return false;
	}
  if (confirm("您确实要删除该记录吗？"))
  {
    fm.OperateType.value = "DELETE||MAIN";
    var i = 0;
    var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或连接其他的界面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './CostDataKeyDefSave.jsp';
    document.getElementById("fm").submit();//提交

  }
  else
  {
    fm.OperateType.value = "";
    //alert("您已经取消了修改操作！");
  }
}


function ReturnData()
{
  var arrReturn = new Array();
	var tSel = CostDataKeyDefInputGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )

		alert( "请先选择一条记录，再点击返回按钮。" );
	else
	{
		
			try
			{	
				arrReturn = getQueryResult();
				afterQuery( arrReturn );
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
	tRow = CostDataKeyDefInputGrid.getSelNo();

	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
	/**
	strSQL = "select a.VersionNo,a.AcquisitionID,( select b.ColumnMark from FITableColumnDef b where b.ColumnID=a.KeyID and tableid='FIAboriginalData'),a.KeyName,a.KeyOrder,a.Remark from FICostDataKeyDef a where 1=1 "
	       + "and a.VersionNo='"+CostDataKeyDefInputGrid.getRowColData(tRow-1,1)+"' and a.AcquisitionID='"+CostDataKeyDefInputGrid.getRowColData(tRow-1,2)+"' and a.KeyID= ( select b.ColumnID from FITableColumnDef b where b.ColumnMark='"+CostDataKeyDefInputGrid.getRowColData(tRow-1,3)+"' and tableid='FIAboriginalData')" ; 
	 */
	 	var mySql2=new SqlClass();
			mySql2.setResourceName("fininterface.CostDataKeyDefInputSql"); //指定使用的properties文件名
			mySql2.setSqlId("CostDataKeyDefInputSql2");//指定使用的Sql的id
			mySql2.addSubPara(CostDataKeyDefInputGrid.getRowColData(tRow-1,1));//指定传入的参数
			mySql2.addSubPara(CostDataKeyDefInputGrid.getRowColData(tRow-1,2));//指定传入的参数
			mySql2.addSubPara(CostDataKeyDefInputGrid.getRowColData(tRow-1,3));//指定传入的参数
			strSQL= mySql2.getString();    
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

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;

		document.all('KeyID').value = arrResult[0][2];
		document.all('KeyID1').value = arrResult[0][2];
		document.all('KeyName').value = arrResult[0][3];
		document.all('KeyOrder').value = arrResult[0][4];
		document.all('Remark').value = arrResult[0][5];
		//document.all('KeyID').disabled = true;	
		//document.all('KeyName').disabled = true;	
		showCodeName(); 
	}
	
}
function initCostDataKeyDefInputQuery()
{
	try
	{
  	var strSQL = ""; 
  	/**
		strSQL = "select a.VersionNo,a.AcquisitionID,( select b.ColumnMark from FITableColumnDef b where b.ColumnID=a.KeyID and tableid='FIAboriginalData'),a.KeyName,a.KeyOrder,a.Remark from FICostDataKeyDef a where ";
		strSQL = strSQL + " a.VersionNo ='"+VersionNo+"' and a.AcquisitionID ='"+AcquisitionID+"' order by a.KeyOrder ASC ";
	*/
		var mySql3=new SqlClass();
			mySql3.setResourceName("fininterface.CostDataKeyDefInputSql"); //指定使用的properties文件名
			mySql3.setSqlId("CostDataKeyDefInputSql3");//指定使用的Sql的id
			mySql3.addSubPara(VersionNo);//指定传入的参数
			mySql3.addSubPara(AcquisitionID);//指定传入的参数
			strSQL= mySql3.getString(); 
  	//alert(strSQL);
  	turnPage.queryModal(strSQL, CostDataKeyDefInputGrid);
  	
	}
	
	catch(Ex)
	{
		alert(Ex.message);
	}
}
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //释放“增加”按钮

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

		fm.OperateType.value="";
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

		if(fm.OperateType.value = "DELETE||MAIN")
		{
			document.all('KeyID').value = '';
			document.all('KeyID1').value = '';
  		document.all('KeyIDName').value = '';
			document.all('KeyName').value = '';
			document.all('KeyOrder').value = '';
			document.all('Remark').value = '';
		}
    fm.OperateType.value="";
    initCostDataKeyDefInputQuery();
  }
}

function beforeSubmit()
{			
  if((fm.KeyID.value=="")||(fm.KeyID.value=="null"))
  {
    alert("请您录入数据主键编号！！！");
    return false;
  }
  if((fm.KeyName.value=="")||(fm.KeyName.value==null))
  {
  	alert("主键名称不得为空！");
  	return false;
  }
    return true;
}


//删除和修改前必须选中一条记录
function beforeSubmit2()
{		
	var tSel = CostDataKeyDefInputGrid.getSelNo();	
	var strSQL="";
	if( tSel == 0 || tSel == null )
	{
		alert( "请您先选择一条记录，再进行修改和删除的操作!" );
		return;
	}
    return true;
}

function resetAgain()
{
			document.all('KeyID').value = '';
			document.all('KeyID1').value = '';
  		document.all('KeyIDName').value = '';
			document.all('KeyName').value = '';
			document.all('KeyOrder').value = '';
			document.all('Remark').value = '';
}