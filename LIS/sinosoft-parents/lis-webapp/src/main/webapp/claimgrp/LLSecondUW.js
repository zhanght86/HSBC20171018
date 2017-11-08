//程序名称：LLSecondUW.js
//程序功能：合同单人工核保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var k = 0;
var mySql = new SqlClass();
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

// 初始化按钮------------该被保险人下的所有合同 
function initLCContGridQuery()
{
	var tInsuredNo = fm.InsuredNo.value;
	//根据 个单被保人表（lcinsured），个人保单表---合同表（LCCont）连接查询
	/*var strSQL = " select a.contno,a.appntno,a.appntname,a.managecom "
			+" from lccont a,lcinsured b where 1=1 and a.contno = b.contno "
 			+" and b.insuredno = '"+tInsuredNo+"' ";*/
	mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLSecondUWInputSql");
mySql.setSqlId("LLSecondUWSql1");
mySql.addSubPara(tInsuredNo); 
	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  //查询SQL，返回结果字符串
	if (!turnPage.strQueryResult)  //判断是否查询成功
	{
		alert("没有查到该被保险人下的相关合同！");
		return "";
	}
	//查询成功则拆分字符串，返回二维数组
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
	turnPage.pageDisplayGrid = LCContGrid;     
	turnPage.strQuerySql= strSQL; //保存SQL语句
	turnPage.pageIndex = 0;  //设置查询起始位置
	//在查询结果数组中取出符合页面显示大小设置的数组
	var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);//调用MULTILINE对象显示查询结果
//	isClaimRelFlag();	
	return true;
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;
	if( arrResult == null )
	{
		alert( "没有找到相关的数据!" );
	}
	else
	{
		arrGrid = arrResult;// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				CheckGrid.setRowColData( i, j+1, arrResult[i][j] );
			} 	
		} 	
		
	} 	
}

//[查询是每个合同否与当前赔案相关]------查询赔案保单名细表（LLClaimPolicy）检测
function isClaimRelFlag()
{
	var strSQL="";
	var row = LCContGrid.mulLineCount;
	for(var m=0;m<row;m++ )
	{
		/*var strSQL="select contno from llclaimpolicy where 1=1"
			+" and clmno='"+fm.CaseNo.value +"'"
			+" and contno='"+LCContGrid.getRowColData(m,1) +"'";*/
		mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLSecondUWInputSql");
mySql.setSqlId("LLSecondUWSql2");
mySql.addSubPara(fm.CaseNo.value ); 
mySql.addSubPara(LCContGrid.getRowColData(m,1)); 
		var arr=easyExecSql(mySql.getString());
		if(arr==null ||arr=="")
		{
			LCContGrid.setRowColData(m,5,"1");//置“1--不相关标志”
		}
		else
			 
		{ 
			LCContGrid.setRowColData(m,5,"0");//置“0--相关标志”
		} 

	}
}

function LLSeUWSaveClick()
{
    //检查是否填写数据()
    var row = LCContGrid.mulLineCount;
    var i=0;
    for(var m=0;m<row;m++ )
    {
    	if(LCContGrid.getChkNo(m))
    	{
    		i=i+1;
    		// 检测是否必须填写表格中的内容
    	}
    }
    if(i==0)
	{
		alert("你没有选择任何合同！");
		return;
	}
	if(trim(fm.Note.value)=="") 
	{
		alert("请填写出险人目前健康状况介绍！");
		return;
	}
//	if(confirm("已经提起过核保,确实要再次提起核保吗？")==true)
//	{
//	}
//    return;    
	fm.action="LLSecondUWSave.jsp";
	submitForm(); //提交
}

//提交，保存按钮对应操作
function submitForm()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.submit(); //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//  	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
}

