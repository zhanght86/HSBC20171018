var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;



//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{ 
	 
try
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
  }
  easyQueryClick();
}catch(ex){}

}

function rollBack()
{
	var flag = 0;
	var j = 0;
	//判定是否有选择打印数据
	for( j = 0; j < RBResultGrid.mulLineCount; j++ )
	{
		if( RBResultGrid.getSelNo(j) >0)
		{
			flag = 1;
			break;
		}
	}
	//如果没有打印数据，提示用户选择
	if( flag == 0 )
	{
		alert("请先选择一个批次进行回退");
		return false;
	}
        var showStr="正在提取数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        document.getElementById("fm").submit();
}
 
function easyQueryClick()
{
  if(fm.Bdate.value=='')
  {
		
     alert("起始日期不能为空!");
     return;
  }
  if(fm.Edate.value=='')
  {
     alert("终止日期不能为空!");
     return;
  }
	// 初始化表格
  initRBResultGrid();
  var oper="like" ;
  
  // 书写SQL语句
  var strSQL = "";
  /**
  strSQL = "select a.parametervalue,a.eventno,b.makedate from FIOperationParameter a ,FIOperationLog b where a.eventno = b.eventno and a.parametertype = 'BatchNo'  and a.eventtype in ('01') " ;
  
  if(fm.Bdate.value!='')
  {
  	strSQL = strSQL + " and b.makedate >='"+fm.Bdate.value+"' ";
  }
  if(fm.Edate.value!=''){
  	strSQL = strSQL + " and b.makedate <='"+fm.Edate.value+"' ";
  }
  strSQL = strSQL + " and (exists (select 1 from FIDataTransGather  where FIDataTransGather.Batchno = a.parametervalue and FIDataTransGather.VoucherNo is null) or not exists( select 1 from FIDataTransGather  where FIDataTransGather.Batchno = a.parametervalue)) and not exists (select * from FIOperationParameter c ,FIOperationLog d where c.eventno = d.eventno and d.performstate = '0' and d.eventtype = '03' and c.parametertype = 'BatchNo' and c.parametervalue = a.parametervalue)";
 */
 	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FinDistillRollBackSql"); //指定使用的properties文件名
		mySql1.setSqlId("FinDistillRollBackSql1");//指定使用的Sql的id
		mySql1.addSubPara(fm.Bdate.value);//指定传入的参数
		mySql1.addSubPara(fm.Edate.value);//指定传入的参数
		strSQL= mySql1.getString();
 	 
				   		 
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  
  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    alert("没有查询到数据！");
    return "";
  }
  
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = RBResultGrid;    
          
  //保存SQL语句
  turnPage.strQuerySql     = strSQL; 
  
  //设置查询起始位置
  turnPage.pageIndex       = 0;  
  
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet  = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;
}

