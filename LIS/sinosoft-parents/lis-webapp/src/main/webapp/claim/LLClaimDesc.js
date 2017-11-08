var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//添加按钮
function saveClick()
{
    //******************************************************
    //补充需要提交字段，通过此页面提交字段默认字段值
    //******************************************************
  　//提交
    fm.fmtransact.value = "INSERT";
    submitForm();
}

//提交数据
function submitForm()
{
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
//    showSubmitFrame(mDebug);
    fm.action = './LLClaimDescSave.jsp';
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";
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
        //初始化可编辑区域
        initForm();
        //添加到mulline
        addMulline();
    }
    tSaveFlag ="0";
}

//更新Mulline
function addMulline()
{
    //保存成功后返回
   /* var strSQL = "select clmno,customerno,descno,desctype,describe,operator,makedate,maketime from LLClaimDesc where 1=1 "
                 + getWherePart( 'ClmNo','ClmNo' )
                 + " order by clmno";*/
   mySql = new SqlClass();
	mySql.setResourceName("claim.LLClaimDescInputSql");
	mySql.setSqlId("LLClaimDescSql1");
	mySql.addSubPara(fm.ClmNo.value ); 
    var arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,LLSubReportDescGrid);
    }
}

//选中LLSubReportDescGrid响应事件
function LLSubReportDescGridClick()
{

	//置空相关表单
//    fm.DescType.value = "";
    fm.Describe.value = "";
//    fm.addClick.disabled = true;
    //取得数据
    var i = LLSubReportDescGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
//        fm.DescType.value = LLSubReportDescGrid.getRowColData(i,4);
        fm.Describe.value = LLSubReportDescGrid.getRowColData(i,5);
//        showOneCodeName('sex','WhoSex','SexName');//性别
    }

}
