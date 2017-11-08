//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();

//提交完成后所作操作
function afterSubmit5( FlagStr, content )
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


// 查询
function querySelfGrid()
{

    /*var strSQL = "";
	strSQL = "select a.RptNo,a.RptorName,a.RptDate,b.customerno,b.customername,"
	       + "(Select codename From ldcode Where codetype='sex' And code=c.sex),a.AccidentDate, "
	       + " (Case (Select 1 From llregister where rgtobjno=a.rptno) When 1 Then '是' Else '否' end)"
	       + "from LLReport a,llsubreport b,ldperson c Where a.rptno=b.SubRptNo "
	       + "And b.customerno=c.customerno And a.rgtclass='1'";*/
	
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimReportQueryInputSql");
	mySql.setSqlId("LLClaimReportQuerySql1");
	mySql.addSubPara("1");

	if (fm.CustomerNo.value != null && fm.CustomerNo.value != "")
	{
		//strSQL=strSQL+"and b.customerno='"+fm.CustomerNo.value+"'";
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimReportQueryInputSql");
		mySql.setSqlId("LLClaimReportQuerySql2");
		mySql.addSubPara(fm.CustomerNo.value ); 
	}
	
    //prompt("立案初始界面查询",strSQL);
    turnPage2.queryModal(mySql.getString(),SelfLLReportGrid);
}

//报案详细信息查询
function QueryRepDetail()
{
	 var i = SelfLLReportGrid.getSelNo();
	 if (i != '0')
	    {
	        i = i - 1;
	        var tClmNo = SelfLLReportGrid.getRowColData(i,1);
	    //  location.href="LLReportInput.jsp?claimNo="+tClmNo+"&isNew=3";
	        var strUrl="LLReportInputMain.jsp?claimNo="+tClmNo+"&isNew=3";
	        window.open(strUrl,"报案详细信息查询",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	    }
}


//提交数据
function RepRegrelaSubmit()
{
	 if(!confirm("您确认将本条报案信息与本次立案进行关联?"))
     {
         return false;
     }
	 var i = SelfLLReportGrid.getSelNo();
	 if (i != '0')
	 {
		  i = i - 1;
		  var tRptNo = SelfLLReportGrid.getRowColData(i,1);
		  fm.RptNo.value=tRptNo;
	 }
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //提交
}