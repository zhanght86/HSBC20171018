var showInfo;
var turnPage = new turnPageClass();


function queryData()
{
	if(trim(document.all('BonusCalYear').value)=='')
	{
		alert('请输入红利分配会计年度');
		document.all('BonusCalYear').focus();
		return false;
	}
   var sqlid830155420="DSHomeContSql830155420";
var mySql830155420=new SqlClass();
mySql830155420.setResourceName("get.BonusRiskPreCheckInputSql");//指定使用的properties文件名
mySql830155420.setSqlId(sqlid830155420);//指定使用的Sql的id
mySql830155420.addSubPara(fm.BonusCalRisk.value);//指定传入的参数
mySql830155420.addSubPara(fm.BonusCalYear.value);//指定传入的参数
var strSQL=mySql830155420.getString();
   
//   var strSQL = " select riskcode,(select riskname from lmriskapp where riskcode=a.riskcode),decode(state,'0','未复核','1','已复核')  "
//  	                  + " from LoBonusRiskRem a where 1=1 "
//                          +getWherePart('a.riskcode','BonusCalRisk')
//                          +getWherePart('a.fiscalyear','BonusCalYear')
//                          + " order by riskcode ";                
     turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
		if (!turnPage.strQueryResult) 
		{
			alert("查询无数据！");
			return false;
		}	
		//查询成功则拆分字符串，返回二维数组
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult,0,0,turnPage);
		//设置初始化过的MULTILINE对象，VarGrid为在初始化页中定义的全局变量
		turnPage.pageDisplayGrid = NoBonusRiskGrid;
		//保存SQL语句
		turnPage.strQuerySql = strSQL;
		//设置查询起始位置
		turnPage.pageIndex = 0;
		//在查询结果数组中取出符合页面显示大小设置的数组
		var tArr = new Array();
    tArr=turnPage.getData(turnPage.arrDataCacheSet,turnPage.pageIndex,MAXSCREENLINES,turnPage);
		//调用MULTILINE对象显示查询结果
		displayMultiline(tArr, turnPage.pageDisplayGrid,turnPage);
        	
}


function dealData()
{
  var tFlag=false;
  for(var k=0;k<NoBonusRiskGrid.mulLineCount;k++)
  {
  	
  	if(NoBonusRiskGrid.getChkNo(k))
  	{
  		tFlag=true;
  		}
  }
  if(tFlag)
  {
  document.all('transact').value='UPDATE';
	var showStr="正在准备处理数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit(); //提交
  	}else
  {
  	alert("请至少选中一行");
  			
}

}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{

	showInfo.close();	
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
    parent.fraInterface.document.all('compute').disabled = false;
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
  	parent.fraInterface.document.all('compute').disabled = false;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  }
   initNoBonusRiskGrid();  
   queryData();
}




