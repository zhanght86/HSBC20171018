//               该文件中包含客户端需要处理的函数和事件
var turnPage = new turnPageClass();         //使用翻页功能，必须建立为全局变量
var turnPage1 = new turnPageClass();
var showInfo;
var mDebug="0";
var Action;
var tRowNo=0;

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
}

function initQuery() 
{
 /**
  var strSql = "select b.parametervalue,a.makedate,(select min(m.parametervalue) || '至' || max(m.parametervalue) from FIOperationParameter m ,FIOperationLog n where m.eventno = n.eventno and m.parametertype in ('StartDate','EndDate') and n.PerformState = '0' and exists ( select 1 from FIOperationParameter g where g.eventno = m.eventno and g.parametertype = 'BatchNo' and g.parametervalue = b.parametervalue)) as datelenth,a.operator from FIOperationLog  a , FIOperationParameter b where a.eventno = b.eventno and b.EventType = '02' and b.parametertype = 'BatchNo' and a.PerformState = '0' and not exists (select 1 from FIOperationParameter c , FIOperationLog v where c.eventno = v.eventno and v.performstate = '0' and c.eventtype = '11' and c.parametertype = 'BatchNo' and c.parametervalue = b.parametervalue)";
  strSql = strSql + " union select a.AppNo,a.AppDate,'',a.Applicant from FIDataFeeBackApp a where 1=1 and AppState = '04' and not exists (select 1 from FIOperationParameter c, FIOperationLog v where c.eventno = v.eventno and v.performstate = '0' and c.eventtype = '11' and c.parametertype = 'BatchNo' and c.parametervalue = a.AppNo)"
  */
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.MoveDataSql"); //指定使用的properties文件名
		mySql1.setSqlId("MoveDataSql1");//指定使用的Sql的id
		mySql1.addSubPara(1);//指定传入的参数
	var	strSql= mySql1.getString();
  
  turnPage1.queryModal(strSql, MoveDataGrid);
} 
 
function SubmitForm()
{
  if(MoveDataGrid.getSelNo()) 
  { 
     document.all("sBatchNo").value = MoveDataGrid.getRowColData(MoveDataGrid.getSelNo()-1, 1);
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
     document.getElementById("fm").submit(); //提交
  }
  else 
  {
    alert("请先选择一条批次号信息！"); 
  }
}

