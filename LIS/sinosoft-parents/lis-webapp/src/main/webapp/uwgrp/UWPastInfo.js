//程序名称：UWGErrInit.jsp
//程序功能：集体核保未过原因查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="1";
var tPOLNO = "";
var turnPage = new turnPageClass();


//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
    //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  }
  else
  { 
    //执行下一步操作
  }
}



// 查询按钮
function easyQueryClick()
{
	// 初始化表格
	initUWInfoGrid();
	initUWSubInfoGrid();
	
	// 书写SQL语句
	var strSQL = "";
	var GrpContNo = document.all('GrpContNo').value;
	
	var tSql = " select (select name from ldcom where comcode = a.managecom),a.managecom, a.salechnl,"
	          +" a.grppolno,a.prtno,a.grpname,(select riskcode from lmrisk where riskcode = a.riskcode),"
	          +" a.prem, a.amnt,(select count(*) from lcpol where grpcontno = a.grpcontno),"
	          +" a.agentcode, (select name from laagent where agentcode = a.agentcode),"
	          +" nvl((select sum(realpay) from llclaimpolicy where grpcontno = a.grpcontno),0)"
	          +" from lcgrppol a "
	          +" where a.grpcontno = '"+GrpContNo+"'";
	turnPage.queryModal(tSql, UWInfoGrid,0,0,30);
	var tSubSql=" select distinct (a.clmno), a.caseno,(select riskname from lmrisk where riskcode = a.riskcode),"
	           +" a.realpay, b.accidentreason,a.insuredname, a.insuredno	from llclaimpolicy a, llregister b"
	           +" where a.clmno = b.rgtno and a.grpcontno='"+GrpContNo+"'";
	turnPage.queryModal(tSubSql, UWSubInfoGrid,0,0,30);
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;

	if( arrResult == null )
		alert( "没有找到相关的数据!" );
	else
	{
		arrGrid = arrResult;
		// 显示查询结果
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				UWErrGrid.setRowColData( i, j+1, arrResult[i][j] );
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}
