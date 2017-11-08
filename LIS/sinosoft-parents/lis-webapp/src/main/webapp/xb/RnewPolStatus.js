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

function getcodedata2()
{
	//var sql="select riskcode,riskname from lmriskapp where riskcode in (select riskcode from lcpol where contno ='"+fm.ProposalContNo.value+"' and appflag ='9')";
	var sql=wrapSQL('LMRiskApp1',[fm.ProposalContNo.value]);
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(sql, 1, 0, 1);
	//prompt("查询险种代码:",sql);
    if (turnPage.strQueryResult != "")
    {
    	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;
    	for (i = 0; i < m; i++)
    	{
    		j = i + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
    	}
    }
    //alert(tCodeData);
    document.all("RiskCode").CodeData=tCodeData;
	
}

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
  document.getElementById("fm").submit();; //提交
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
		alert('请录入合同号!');
		return false;
	}
	//strSQL = "select appflag from lccont where contno = '"+document.all('ProposalContNo').value+"'";
	strSQL=wrapSQL('LCCont1',[document.all('ProposalContNo').value]);
	arrResult1 = easyExecSql(strSQL,1,0);
	if(arrResult1!=null&&arrResult1[0][0]=="0")
	{
		alert("该投保单未签单!");
		return;
		}
	//strSQL = "select ContNo,ProposalContNo,PrtNo,ManageCom,AgentCode,AppntName from LCCont where "+k+" = "+k
	//			 + " and ContType = '1'"		 
	//			 + " and AppFlag = '1'"	 
	//			 + getWherePart( 'contno','ProposalContNo',strOperate )
	//			 + " and ManageCom like '" + manageCom + "%%'";
	//strSQL = "select ContNo,prtno,PrtNo,ManageCom,AgentCode,AppntName,riskcode from LCPol where "+k+" = "+k
	//			 + " and ContType = '1'"		 
	//			 + " and AppFlag = '9'"	 
	//			 + getWherePart( 'contno','ProposalContNo',strOperate )
	//			 + getWherePart( 'riskcode','RiskCode',strOperate )
	//			 + " and ManageCom like '" + manageCom + "%%'";
		strSQL=wrapSQL('LCPol1',[fm.ProposalContNo.value,fm.RiskCode.value,manageCom]);
  var tArray = new Array;
  tArray = easyExecSql(strSQL);
  if(tArray=="" || tArray==null)
  {
  	//strSQL = "select MissionProp1,MissionProp2,'',MissionProp6 from LWMission "+
  	//         " where MissionProp2 = '"+document.all("ProposalContNo").value+"'"+
  	//         " and MissionProp3 like '" + manageCom + "%%'"
  	         //+" and ActivityID in ('0000001099','0000001098')"
  	        //+" union "
  	        //+ "select MissionProp1,MissionProp2,'',MissionProp6 from LWMission "+
  	        //" where MissionProp2 = '"+document.all("ProposalContNo").value+"'"+
  	        //" and MissionProp2 like '" + manageCom + "%%' "
  	        //+" union "
  	        //+ "select MissionProp1,MissionProp2,'',MissionProp6 from LWMission "+
  	        //" where MissionProp2 = '"+document.all("ProposalContNo").value+"' "
  	        //+ " and activityid='0000001403' ";
  	        
  	   strSQL=wrapSQL('LWMission1',[document.all("ProposalContNo").value,manageCom,
  	   															document.all("ProposalContNo").value,
  	   															manageCom,document.all("ProposalContNo").value]);
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
  document.getElementById("fm").submit();; //提交
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



/**
	mysql工厂，根据传入参数生成Sql字符串
	
	sqlId:页面中某条sql的唯一标识
	param:数组类型,sql中where条件里面的参数
**/
function wrapSQL(sqlId,param){
	
	var mysql=new SqlClass();
	
	mysql.setResourceName("xb.RnewPolStatusInputSql");
	mysql.setSqlId(sqlId);
	
	for(i=0;i<param.length;i++){
		 mysql.addSubPara(param[i]);
	}
	
	return mysql.getString();
	
}
