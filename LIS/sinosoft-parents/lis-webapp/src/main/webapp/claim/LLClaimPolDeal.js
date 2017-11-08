//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();

//查询保单结算结果
function initQuery()
{
    //alert(pContNo);
   /* var strSql = " select a.FeeOperationType,"
       +" (select e.BalTypeDesc from LLBalanceRela e where e.BalType=a.FeeOperationType and e.SubBalType=a.SubFeeOperationType and e.FinaType=a.FeeFinaType),"
       +" a.SubFeeOperationType,"
       +" (select f.SubBalTypeDesc from LLBalanceRela f where f.SubBalType=a.SubFeeOperationType),"
       +" a.contno,a.PolNo,RiskCode,GetDate,a.Pay "
       +" from LLBalance a where 1=1 "
       +" and a.ClmNo  in ('" + fm.ClmNo.value + "')"
       +" and substr(a.FeeOperationType,1,1) in ('C') "*/
       
      mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimPolDealInputSql");
	mySql.setSqlId("LLClaimPolDealSql1");
	mySql.addSubPara(fm.ClmNo.value );  
    //prompt("保单结算初始化查询",strSql);
    //显示所有数据
    var arr = easyExecSql(mySql.getString());
    if (arr)
    {
        displayMultiline(arr,PolCalResultGrid);
    }
    else
    {
        initPolCalResultGrid();         //计算结果
    }
}

//公共函数--提交到后台操作
function submitForm()
{
    var showStr = "正在向后台提交数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr ="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
//    fm.hideOperate.value = mOperate;
    fm.action = "./LLClaimPolDealSave.jsp";
    fm.submit(); //提交
}

//公共函数--Save页面--服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "FAIL" )
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
        mOperate = '';
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
        initQuery();
    }

}
