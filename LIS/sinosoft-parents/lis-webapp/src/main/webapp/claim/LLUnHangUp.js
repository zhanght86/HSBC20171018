//               该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//提交，保存按钮对应操作
function submitForm()
{
    var i = 0;

    var selno = PolGrid.getSelNo() - 1;
    if (selno < 0)
    {
        alert("请选择一条记录！");
        return;
    }

//    fm.PrtNo.value = PolGrid.getRowColData(selno, 2);
//    if (fm.PrtNo.value == null || fm.PrtNo.value == "")
//    {
//        alert("印刷号为空！");
//        return;
//    }
    if (fm.Content.value == null || fm.Content.value == "")
    {
        alert("请输入修改原因！");
        return;
    }
    var tContent = fm.Content.value;
    var tCustomerNo = fm.CustomerNo1.value;
    //alert(tContent);
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var strUrl="LLUnHangUpApplyMain.jsp?InsuredNo="+tCustomerNo+"&ClmNo="+fm.RptNo.value+"&Content="+tContent;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    document.getElementById("fm").submit();
    //提交
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr, content)
{
    showInfo.close();
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    if (FlagStr == 'Succ')
    {
        easyQueryClick();
    }
}


//显示frmSubmit框架，用来调试
function showSubmitFrame(cDebug)
{
    if (cDebug == "1")
    {
        parent.fraMain.rows = "0,0,50,82,*";
    }
    else
    {
        parent.fraMain.rows = "0,0,0,82,*";
    }
}


//显示div，第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
function showDiv(cDiv, cShow)
{
    if (cShow == "true")
    {
        cDiv.style.display = "";
    }
    else
    {
        cDiv.style.display = "none";
    }
}

function queryPol()
{
    //调用查询投保单的文件
   // window.open("./QuestionAdditionalRiskQuery.html");
   window.open("./QuestionAdditionalRiskQuery.html","","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

function easyQueryClick()
{
    // 初始化表格
    initPolGrid();

    if (fm.RptNo.value==""&&fm.CustomerNo.value==""&&fm.Name.value=="") {
    	alert("请至少输入报案号、出险人客户号、出险人姓名其中一项查询条件！");
    	return;
    }
//    Array
//    tArray = new Array();
//    tArray[0] = "RptNo";
//    tArray[1] = "CustomerNo";
//    tArray[2] = "Name";
//    tArray[3] = "AppntName";
//    tArray[4] = "InsuredName";
//    tArray[5] = "InsuredSex";
//    tArray[6] = "UWStateQuery";
//    tArray[7] = "AgentCode";
//    tArray[8] = "AgentGroup";
//    var j = 0;
//    for (var i = 0; i < tArray.length; i++) {
//        if (eval("fm." + tArray[i] + ".value") == "") {
//            j++;
//        }
//    }

    // 书写SQL语句
//    var tContType = fm.ContType.value;
//    fm.PolType.value = tContType;

       /* var strSql = "select a.rptno,"
        			+ " a.rptorname,"
        			+ " a.makedate,"
        			+ " b.customerno,"
        			+ " b.customername,"
        			+ " b.sex,"
        			+ " b.accdate"
        			+ " from llreport a, LLSubReport b"
        			+ " where rgtflag = '10'"
        			+ " and a.rptno=b.subrptno"
        			+ getWherePart('a.rptno', 'RptNo')
        			+ getWherePart('b.customerno', 'CustomerNo')
        			+ getWherePart('b.customername', 'Name')
        			+ getWherePart('b.sex', 'Sex')
        			+ getWherePart('a.makedate', 'RptDate')
        			+ getWherePart('b.accdate', 'AccdentDate')
        			+ " order by a.MakeDate ";*/
			
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLUnHangUpSql");
	mySql.setSqlId("LLUnHangUpSql1");
	mySql.addSubPara(fm.RptNo.value ); 
	mySql.addSubPara(fm.CustomerNo.value ); 
	mySql.addSubPara(fm.Name.value ); 
	mySql.addSubPara(fm.Sex.value ); 
	mySql.addSubPara(fm.RptDate.value ); 
	mySql.addSubPara(fm.AccdentDate.value ); 
    turnPage.queryModal(mySql.getString(), PolGrid);//prompt("",strSql);
}

/**
 * 点击multLine时触发的函数
 * */
function displayHidden(){
	var i = PolGrid.getSelNo();
    i = i - 1;
    fm.CustomerNo1.value=PolGrid.getRowColData(i,4);//出险人客户号
    var rptno = PolGrid.getRowColData(i,1);
    //var strSql = "select assigneeaddr from llreport where rptno='"+rptno+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLUnHangUpSql");
	mySql.setSqlId("LLUnHangUpSql2");
	mySql.addSubPara(rptno);  
    var arrResult = easyExecSql(mySql.getString());
    if(arrResult){
    	fm.Content.value=arrResult;
    }
}
