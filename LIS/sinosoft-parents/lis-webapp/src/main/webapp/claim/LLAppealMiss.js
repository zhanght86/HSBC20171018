//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();
//提交完成后所作操作
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
        //直接取得数据跳转到立案界面
        location.href="LLAppealInput.jsp?claimNo="+fm.ClmNo.value;
    }
    tSaveFlag ="0";
}

// 初始化表格1
function queryGrid()
{
    initLLAppealGrid();
    var strSQL = "";
    //
	/*strSQL = " select rgtno,rgtantname,"
	             + " (select ldcode.codename from ldcode where trim(ldcode.code)=trim(relation) and trim(ldcode.codetype)='llrgrelation') as Relation,"
	             + " assigneename,"
	             + " (case trim(assigneesex) when '0' then '男' when '1' then '女'  else '不详' end) as assigneesex,"
	             + " accidentdate,endcasedate from llregister where 1=1 "
                 + getWherePart( 'RgtNo' ,'RptNo')
                 + getWherePart( 'RgtantName' ,'RptorName','like')
                 + getWherePart( 'Relation' ,'Relation')
                 + getWherePart( 'AssigneeType' ,'AssigneeType')
                 + getWherePart( 'AssigneeCode' ,'AssigneeCode')
                 + getWherePart( 'AssigneeName' ,'AssigneeName','like')
                 + " and MngCom like '" + fm.ManageCom.value + "%%'"
                 + " and ClmState = '60'"   //赔案为己完成
                 + " and EndCaseFlag = '1'"   //已结案
                 + " and RgtState <> '13' "
                 + " order by RgtNo ";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLAppealMissInputSql");
	mySql.setSqlId("LLAppealMissSql1");
	mySql.addSubPara(fm.RptNo.value ); 
	mySql.addSubPara(fm.RptorName.value ); 
	mySql.addSubPara(fm.Relation.value ); 
	mySql.addSubPara(fm.AssigneeType.value ); 
	mySql.addSubPara(fm.AssigneeCode.value ); 
	mySql.addSubPara(fm.AssigneeName.value ); 
	mySql.addSubPara(fm.ManageCom.value ); 
//    alert(strSQL);
    turnPage.queryModal(mySql.getString(),LLAppealGrid);
}

// 初始化表格2
function querySelfGrid()
{
    initSelfLLAppealGrid();
   /* var strSQL = "";
	strSQL = " select appealno,appealname,"
	             + " (case trim(appealsex) when '0' then '男' when '1' then '女'  else '不详' end) as appealsex,"
	             + " relation,idtype,idno,waitdate from LLAppeal where 1=1 "
                 + " and Operator = '" + fm.Operator.value + "'"
                 + " and AppealState != '1'"
                 + " order by appealno ";*/
     mySql = new SqlClass();
	mySql.setResourceName("claim.LLAppealMissInputSql");
	mySql.setSqlId("LLAppealMissSql2");
	mySql.addSubPara(fm.Operator.value ); 
	
//    alert(strSQL);
    turnPage2.queryModal(mySql.getString(),SelfLLAppealGrid);
}

//LLAppealGrid点击事件
function LLAppealGridClick()
{
}

//SelfLLAppealGrid点击事件
function SelfLLAppealGridClick()
{
    var i = SelfLLAppealGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = SelfLLAppealGrid.getRowColData(i,1);
        location.href="LLAppealInput.jsp?claimNo="+tClmNo;
    }
}

//[申诉]按钮
function ApplyClaim()
{
	var selno = LLAppealGrid.getSelNo() - 1;
	if (selno <0)
	{
	      alert("请选择要申请处理的赔案！");
	      return;
	}
	fm.ClmNo.value = LLAppealGrid.getRowColData(selno, 1);
	
	//检验是否为全部拒付案件,如是不允许做申诉处理  2005-10-21 16:15 周磊
   /* var tSql = " select givetype from LLClaim where 1=1 "
             + " and ClmNo = '" + fm.ClmNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLAppealMissInputSql");
	mySql.setSqlId("LLAppealMissSql3");
	mySql.addSubPara(fm.ClmNo.value ); 
    var tGiveType = easyExecSql(mySql.getString());
    if (tGiveType == "1")
    {
        alert("整案拒付的案件无法进行申诉处理!");
        return;
    }
    
	fm.action = "./LLAppealMissSave.jsp";
	submitForm(); //提交
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
    fm.submit(); //提交
    tSaveFlag ="0";
}