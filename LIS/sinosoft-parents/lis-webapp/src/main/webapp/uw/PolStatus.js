//程序名称：PolStatus.js
//程序功能：保单状态查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var k = 0;
var arrResult1;

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

  initPolGrid();
  document.getElementById("fm").submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    alert("对不起！没有找到相应数据"); 
  }
  else
  { 
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



// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initPolGrid();
	var strSQL = "";
	var strOperate="like";
	if(document.all('ProposalContNo').value==null||trim(document.all('ProposalContNo').value)=='')
	{
		alert('请录入印刷号!');
		return false;
	}
	
	    var sqlid1="PolStatusSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.PolStatusSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(document.all('ProposalContNo').value);//指定传入的参数
	    strSQL=mySql1.getString();	
	
//	strSQL = "select appflag from lccont where prtno = '"+document.all('ProposalContNo').value+"'";
	arrResult1 = easyExecSql(strSQL,1,0);
	if(arrResult1!=null&&arrResult1[0][0]=="1")
	{
	//	alert("该投保单已经签单!");
	//	return;
		}
		
		var sqlid2="PolStatusSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.PolStatusSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		//mySql2.addSubPara('1');//指定传入的参数
	//	mySql2.addSubPara(k);//指定传入的参数
		mySql2.addSubPara(document.all('ProposalContNo').value);//指定传入的参数
		mySql2.addSubPara(manageCom);//指定传入的参数
	    strSQL=mySql2.getString();	
		//alert(document.all('ProposalContNo').value+":"+manageCom);
//	strSQL = "select ContNo,ProposalContNo,PrtNo,ManageCom,AgentCode,AppntName from LCCont where "+k+" = "+k
//				 + " and ContType = '1'"		 
//				 + getWherePart( 'prtno','ProposalContNo',strOperate )
//				 + " and ManageCom like '" + manageCom + "%%'";
  var tArray = new Array;
  tArray = easyExecSql(strSQL);
  if(tArray=="" || tArray==null)
  {
  	
		var sqlid3="PolStatusSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.PolStatusSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(document.all('ProposalContNo').value);//指定传入的参数
		mySql3.addSubPara(manageCom);//指定传入的参数
		mySql3.addSubPara(document.all('ProposalContNo').value);//指定传入的参数
		mySql3.addSubPara(manageCom);//指定传入的参数
		mySql3.addSubPara(document.all('ProposalContNo').value);//指定传入的参数
		mySql3.addSubPara(document.all('ProposalContNo').value);//指定传入的参数
	    strSQL=mySql3.getString();	
	
//  	strSQL = "select MissionProp1,'','',MissionProp3 from LWMission "+
//  	         " where MissionProp1 = '"+document.all("ProposalContNo").value+"'"+
//  	         " and MissionProp3 like '" + manageCom + "%%'"
//  	         + " and processid='0000000003' "
//  	         + " and activityid not in ('0000001090','0000001091')"
//  	         +" union "
//  	         + "select MissionProp1,'','',MissionProp3 from LWMission "+
//  	         " where MissionProp1 = '"+document.all("ProposalContNo").value+"'"+
//  	         " and MissionProp2 like '" + manageCom + "%%' "
//  	         + " and activityid not in ('0000001090','0000001091')"
//  	         + " and processid='0000000003' "
//  	         +" union "
//  	         + "select MissionProp1,'','',MissionProp3 from LWMission "+
//  	         " where MissionProp1 = '"+document.all("ProposalContNo").value+"' "
//  	         + " and activityid='0000001403' "
//  	         +" union "
//  	         + "select MissionProp1,'','',MissionProp13 from LWMission "+
//  	         " where MissionProp1 = '"+document.all("ProposalContNo").value+"' "
//  	         + " and activityid in ('0000001090','0000001091')"
//  	         ;
  	}

	  //查询SQL，返回结果字符串
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);
    alert("无此投保单信息！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
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

function getStatus()
{
	initPolStatuGrid();
  var i = 0;
  var showStr="正在传送数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  //showSubmitFrame(mDebug);
  //showSubmitFrame(1);
  //document.all('spanPolGrid').all('PolGridSel').checked='true';
  document.getElementById("fm").submit(); //提交
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
		parent.fraMain.rows = "0,0,*,0,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}