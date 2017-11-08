//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var approvefalg;
var arrResult;
var arrResult1;
var mDebug = "0";
var mOperate = 0;
var mAction = "";
var mSwitch = parent.VD.gVSwitch;
var mShowCustomerDetail = "GROUPPOL";
var turnPage = new turnPageClass();
this.window.onfocus = myonfocus;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

//记录需要录入两遍时的两次值
//var theFirstValue="";
//var theSecondValue="";

//该文件中包含客户端需要处理的函数和事件
var showInfo1;

//存放添加动作执行的次数
var addAction = 0;
//暂交费总金额
var sumTempFee = 0.0;
//暂交费信息中交费金额累计
var tempFee = 0.0;
//暂交费分类信息中交费金额累计
var tempClassFee = 0.0;
//单击确定后，该变量置为真，单击添加一笔时，检验该值是否为真，为真继续，然后再将该变量置假
var confirmFlag = false;
//
var arrCardRisk;

//工作流flag
var mWFlag = 0;
//使得从该窗口弹出的窗口能够聚焦


/*********************************************************************
 *  保存集体投保单的提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function submitForm()
{
    //modify by zhangxing
    if (verifyInputNB("1") == false) return false;
    if (verifyInputNB("2") == false) return false;
    if (fm.PolAppntDate.value.length != 10 || fm.PolAppntDate.value.substring(4, 5) != '-' || fm.PolAppntDate.value.substring(7, 8) != '-' || fm.PolAppntDate.value.substring(0, 1) == '0')
    {
        alert("请输入正确的日期格式！");
        fm.PolAppntDate.focus();
        return;
    }
    //卡号和投保单号不允许相同
	if(trim(fm.ContCardNo.value)==trim(fm.ProposalContNo.value))
	{
		alert("卡号和投保单号不允许相同。\n 而输入的卡号和投保单号相同，不允许保存");
		return false;
	}
	
    if (fm.AppntIDType.value != "" && fm.AppntIDNo.value == "" || fm.AppntIDType.value == "" && fm.AppntIDNo.value != "") {
        alert("证件类型和证件号码必须同时填写或不填");
        return false;
    }

		if(trim(document.all('Mult').value)== "")
		{
			alert('份数不能为空!');
			return false;
		}
		if(!isNumeric(trim(document.all('Mult').value)))
		{
				alert('份数必须为数字!');
				document.all('Mult').value = "";
				document.all('Mult').focus();
				return false;
		}
		if(parseFloat(trim(document.all('Mult').value)) == 0)
		{
			alert('份数不能为零!');
			document.all('Mult').value = "";
			document.all('Mult').focus();
			return false;
		}

    for (i = 0; i < BnfGrid.mulLineCount; i++)
    {

        //如果受益人的受益份额和受益顺序没有录入则默认为1
        if (BnfGrid.getRowColData(i, 7) == null || BnfGrid.getRowColData(i, 7) == '')
        {
            BnfGrid.setRowColData(i, 7, "1");
        }
        if (BnfGrid.getRowColData(i, 6) == null || BnfGrid.getRowColData(i, 6) == '')
        {
            BnfGrid.setRowColData(i, 6, "1");
        }
    }

		//将其他险种信息列表的信息赋值到其他单独控件里
   setValueFromPolOtherGrid();

    getdetailwork();
    getdetailwork2();

    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.fmAction.value = "INSERT";
    fm.submit();
    //提交
}
function submitForm1()
{
    //修改按纽提交事件
    if (trim(fm.ProposalContNo.value) == "")
    {
        alert("请您填写投保单号！");
        return;
    }
	
		var sqlid1="CardContInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(fm.ProposalContNo.value);//指定传入的参数
	    strSql=mySql1.getString();	
	
//    strSql = "select * from lccont where contno = '" + fm.ProposalContNo.value + "' and CardFlag='3'";
    var HaveTB = easyExecSql(strSql);
    if (HaveTB == null)
    {
        alert("这个单子您还没有保存！");
        return;
    }
    fm.fmAction.value = "EDIT";
    fm.submit();
}
function submitForm2()
{
    //修改按纽提交事件
    if (trim(fm.ProposalContNo.value) == "" || trim(fm.ContCardNo.value) == "")
    {
        alert("请同时填写卡号和投保单号！");
        return;
    }
    else
	{
		fm.ProposalContNo.value=trim(fm.ProposalContNo.value);
		fm.ContCardNo.value=trim(fm.ContCardNo.value);
	}
    //卡号和投保单号不允许相同
	if(trim(fm.ContCardNo.value)==trim(fm.ProposalContNo.value))
	{
		alert("卡号和投保单号不允许相同。\n 而输入的卡号和投保单号相同，不允许保存");
		return false;
	}
	
		var sqlid2="CardContInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(fm.ProposalContNo.value);//指定传入的参数
	    strSql=mySql2.getString();	
	
//    strSql = "select * from lccont where contno = '" + fm.ProposalContNo.value + "' and CardFlag='3'";
    var HaveTB = easyExecSql(strSql);
    if (HaveTB == null)
    {
        alert("这个单子您还没有保存！");
        return;
    }
    //对保费进行校验:如果生成的保费和录入的保费不相等,则签单不能通过
    if (checkpayfee(fm.ProposalContNo.value) == false)
    {
        return false;
    }

    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./CardSign.jsp";
    fm.submit();
}
/*********************************************************************
 *  保存个人投保单的提交后的操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit(FlagStr, content, contract)
{
    //alert("here 1!");
    showInfo.close();

    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        strSql = "select uwflag From lccont where contno='" + contract + "'";
        
    	var sqlid1="CardContInputSql79";
    	var mySql1=new SqlClass();
    	mySql1.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
    	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
    	mySql1.addSubPara(contract);//指定传入的参数
    	strSql=mySql1.getString();
        
        
        var uwflag = easyExecSql(strSql);
        //alert(uwflag);
        if (uwflag == "9")
        {
            content = "自核通过 !  " + content;
        }
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

        showDiv(operateButton, "true");
        //showDiv(inputButton, "false");
        //alert("djfdjfldj")
        //window.top.parent.fraInterface.initForm();
        //alert(12313)
        //top.close();
        //alert("xingxing")


        if (approvefalg == "2")
        {
            //top.close();
        }

        //	  try { goToPic(2) } catch(e) {}

    }
    mAction = "";
}


function afterSubmit4(FlagStr, content)
{

    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        content = "操作成功！";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
//        var tt = "select amnt,prem from lcpol where contno='" + fm.ProposalContNo.value + "'";
        
        var ProposalContNo1 = fm.ProposalContNo.value;
    	var sqlid1="CardContInputSql80";
    	var mySql1=new SqlClass();
    	mySql1.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
    	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
    	mySql1.addSubPara(ProposalContNo1);//指定传入的参数
    	var tt=mySql1.getString();
    	
        var Result = easyExecSql(tt, 1, 0);
        //alert(Result);
        if (Result != null)
        {
            fm.Amnt.value = Result[0][0];
            fm.Prem.value = Result[0][1];
        }

    }

}
function afterSubmit5(FlagStr, content)
{
    //alert("here 1!");
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        content = "操作成功！";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

        showDiv(operateButton, "true");

        //top.close();


        if (approvefalg == "2")
        {
            //top.close();
        }


    }
    mAction = "";

}

/*********************************************************************
 *  "重置"按钮对应操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function resetForm()
{
    try
    {
        initForm();
        document.all('PrtNo').value = prtNo;
    }
    catch(re)
    {
        alert("在GroupPolInput.js-->resetForm函数中发生异常:初始化界面错误!");
    }
}

/*********************************************************************
 *  "取消"按钮对应操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function cancelForm()
{
    showDiv(operateButton, "true");
    showDiv(inputButton, "false");
}

/*********************************************************************
 *  显示frmSubmit框架，用来调试
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
    if (cDebug == "1")
        parent.fraMain.rows = "0,0,50,82,*";
    else
        parent.fraMain.rows = "0,0,0,72,*";
}

/*********************************************************************
 *  Click事件，当点击增加图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addClick()
{
    //下面增加相应的代码
    showDiv(operateButton, "false");
    showDiv(inputButton, "true");
    /*暂时屏蔽，保全部分再作处理	document.all('RiskCode').value = "";

 //保全调用会传2过来，否则默认为0，将付值于保单表中的appflag字段
   if (BQFlag=="2") {
     var strSql = "select grppolno, grpno from lcgrppol where prtno='" + prtNo + "' and riskcode in (select riskcode from lmriskapp where subriskflag='M')";
     var arrResult = easyExecSql(strSql);
     //alert(arrResult);
     mOperate = 1;
     afterQuery(arrResult);
     //strSql = "select GrpNo,GrpName,GrpAddress,Satrap from LDGrp where GrpNo='" + arrResult[0][1] + "'";
     //arrResult = easyExecSql(strSql);
     //mOperate = 2;
     //afterQuery(arrResult);

     document.all('RiskCode').value = BQRiskCode;
     document.all('RiskCode').className = "readonly";
     document.all('RiskCode').readOnly = true;
     document.all('RiskCode').ondblclick = "";
   }
     document.all('ContNo').value = "";
     document.all('GrpProposalNo').value = "";*/
    if (verifyInput2() == false) return false;
}

/*********************************************************************
 *  Click事件，当点击“查询”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryClick()
{
    if (this.ScanFlag == "1") {
        alert("有扫描件录入不允许查询!");
        return false;
    }
    if (mOperate == 0)
    {
        mOperate = 1;
        cContNo = document.all('ContNo').value;
        showInfo = window.open("./ContQueryMain.jsp?ContNo=" + cContNo,"",sFeatures);
    }
}

/*********************************************************************
 *  Click事件，当点击“修改”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function updateClick()
{
    //alert(fm.Remark.value);
    if (fm.AppntIDType.value != "" && fm.AppntIDNo.value == "" || fm.AppntIDType.value == "" && fm.AppntIDNo.value != "") {
        alert("证件类型和证件号码必须同时填写或不填");
        return false;
    }
    if ((fm.PayMode.value == "4" || fm.PayMode.value == "7") && fm.AppntBankCode.value == "" && fm.AppntAccName.value == "" && fm.AppntBankAccNo.value == "")
    {

        alert("请填入首期转帐开户行、首期帐户姓名、首期帐户号！");
        return false;
    }
    if (fm.SecPayMode.value == "3" && fm.SecAppntBankCode.value == "" && fm.SecAppntAccName.value == "" && fm.SecAppntBankAccNo.value == "")
    {

        alert("请填入续期转帐开户行、续期帐户姓名、续期帐户号！");
        return false;
    }
    if (verifyInputNB("1") == false) return false;

    var tGrpProposalNo = "";
    tGrpProposalNo = document.all('ProposalContNo').value;
    if (tGrpProposalNo == null || tGrpProposalNo == "")
        alert("请先做投保单查询操作，再进行修改!");
    else
    {
        ImpartGrid.delBlankLine();
        var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

        if (mAction == "")
        {
            //showSubmitFrame(mDebug);
            //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            mAction = "UPDATE";
            document.all('fmAction').value = mAction;
            fm.action = "ContSave.jsp";
            fm.submit();
            //提交
        }
    }
}

/*********************************************************************
 *  Click事件，当点击“删除”图片时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function deleteClick()
{
    var tGrpProposalNo = "";
    var tProposalContNo = "";
    if (LoadFlag == 1)
    {
        tGrpProposalNo = document.all('GrpContNo').value;
        if (tGrpProposalNo == null || tGrpProposalNo == "")
            alert("请先做投保单查询操作，再进行删除!");
        else
        {
            var showStr = "正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
            var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

            if (mAction == "")
            {
                //showSubmitFrame(mDebug);
                //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
				var name='提示';   //网页名称，可为空; 
				var iWidth=550;      //弹出窗口的宽度; 
				var iHeight=250;     //弹出窗口的高度; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
                mAction = "DELETE";
                document.all('fmAction').value = mAction;
                fm.action = "ContSave.jsp";
                fm.submit();
                //提交
            }
        }

    }
    //top.close();
}

/*********************************************************************
 *  delete事件，当点击“删除”按钮时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function deleteClick2()
{
    if (trim(fm.ProposalContNo.value) == "")
    {
        alert("请先录入投保单号，再进行删除操作!")
        return;
    }
	
		var sqlid3="CardContInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(fm.ProposalContNo.value);//指定传入的参数
	    strSql=mySql3.getString();	
	
//    strSql = "select * from lccont where contno = '" + fm.ProposalContNo.value + "' and CardFlag='3'";
    var HaveTB = easyExecSql(strSql);
    if (HaveTB == null)
    {
        alert("该保单不存在，无法进行删除!");
        return;
    }
    else
    {
        var showStr = "正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;

        if (mAction == "")
        {
            //showSubmitFrame(mDebug);
            //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            mAction = "DELETE";
            document.all('fmAction').value = mAction;
            fm.action = "CardContSave.jsp";
            fm.submit();
            //提交
        }
    }


    //top.close();
}


/*********************************************************************
 *  显示div
 *  参数  ：  第一个参数为一个div的引用，第二个参数为是否显示，如果为"true"则显示，否则不显示
 *  返回值：  无
 *********************************************************************
 */
function showDiv(cDiv, cShow)
{
    if (cShow == "true")
        cDiv.style.display = "";
    else
        cDiv.style.display = "none";
}

/*********************************************************************
 *  当点击“进入个人信息”按钮时触发该函数
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function intoInsured()
{
    //下面增加相应的代码

    var tAppntNo = fm.AppntNo.value;
    var tAppntName = fm.AppntName.value;
    fm.ContNo.value = fm.ProposalContNo.value;
    if (fm.ContNo.value == "")
    {
        alert("您必须先录入合同信息才能进入被保险人信息部分。");
        return false;
    }

    //把集体信息放入内存
    mSwitch = parent.VD.gVSwitch;
    //桢容错
    putCont();

    try {
        goToPic(2)
    } catch(e) {
    }

    try {
        parent.fraInterface.window.location = "./ContInsuredInput.jsp?LoadFlag=" + LoadFlag + "&type=" + type + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&AppntNo=" + tAppntNo + "&AppntName=" + tAppntName + "&checktype=1" + "&scantype=" + scantype;
    }
    catch (e) {
        parent.fraInterface.window.location = "./ContInsuredInput.jsp?LoadFlag=1&type=" + type + "&MissionID=" + tMissionID + "&SubMissionID=" + tSubMissionID + "&AppntNo=" + tAppntNo + "&AppntName=" + tAppntName;
    }
}


/*********************************************************************
 *  把合同所有信息录入结束确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function inputConfirm(wFlag)
{
    if (wFlag == 1) //录入完毕确认
    {
        //alert(ScanFlag);
		
		var sqlid4="CardContInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(fm.ContNo.value);//指定传入的参数
	     var tStr=mySql4.getString();	
		
//        var tStr = "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '" + fm.ContNo.value + "'";
        turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
        if (turnPage.strQueryResult) {
            alert("该合同已经做过保存！");
            return;
        }
        if (document.all('ProposalContNo').value == "")
        {
            alert("合同信息未保存,不容许您进行 [录入完毕] 确认！");
            return;
        }
        if (ScanFlag == "1") {
            fm.WorkFlowFlag.value = "0000001099";
        }
        else {
            fm.WorkFlowFlag.value = "0000001098";
        }
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
        //录入完毕
    }
    else if (wFlag == 2)//复核完毕确认
    {
        if (document.all('ProposalContNo').value == "")
        {
            alert("未查询出合同信息,不容许您进行 [复核完毕] 确认！");
            return;
        }
        fm.WorkFlowFlag.value = "0000001001";
        //复核完毕
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
        approvefalg = "2";
    }
    else if (wFlag == 3)
    {
        if (document.all('ProposalContNo').value == "")
        {
            alert("未查询出合同信息,不容许您进行 [复核修改完毕] 确认！");
            return;
        }
        fm.WorkFlowFlag.value = "0000001002";
        //复核修改完毕
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
    }
    else if (wFlag == 4)
    {
        if (document.all('ProposalContNo').value == "")
        {
            alert("未查询出合同信息,不容许您进行 [修改完毕] 确认！");
            return;
        }
        fm.WorkFlowFlag.value = "0000001021";
        //问题修改
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
    }
    else
        return;

    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./InputConfirm.jsp";
    fm.submit();
    //提交
}

/*********************************************************************
 *  把合同信息放入内存
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function putCont()
{
    delContVar();
    addIntoCont();
}

/*********************************************************************
 *  把合同信息放入加到变量中
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addIntoCont()
{
    //try { mSwitch.addVar( "intoPolFlag", "", "GROUPPOL" ); } catch(ex) { };
    // body信息
    try {
        mSwitch.addVar("BODY", "", window.document.body.innerHTML);
    } catch(ex) {
    }
    ;
    // 集体信息
    //由"./AutoCreatLDGrpInit.jsp"自动生成
    try {
        mSwitch.addVar('ContNo', '', fm.ContNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ProposalContNo', '', fm.ProposalContNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PrtNo', '', fm.PrtNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('GrpContNo', '', fm.GrpContNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ContType', '', fm.ContType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('FamilyType', '', fm.FamilyType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PolType', '', fm.PolType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('CardFlag', '', fm.CardFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ManageCom', '', fm.ManageCom.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AgentCom', '', fm.AgentCom.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AgentCode', '', fm.AgentCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AgentGroup', '', fm.AgentGroup.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AgentCode1', '', fm.AgentCode1.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AgentType', '', fm.AgentType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('SaleChnl', '', fm.SaleChnl.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Handler', '', fm.Handler.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Password', '', fm.Password.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntNo', '', fm.AppntNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntName', '', fm.AppntName.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntSex', '', fm.AppntSex.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntBirthday', '', fm.AppntBirthday.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntIDType', '', fm.AppntIDType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntIDNo', '', fm.AppntIDNo.value);
    } catch(ex) {
    }
    ;

    try {
        mSwitch.addVar('InsuredNo', '', fm.InsuredNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InsuredName', '', fm.InsuredName.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InsuredSex', '', fm.InsuredSex.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InsuredBirthday', '', fm.InsuredBirthday.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InsuredIDType', '', fm.InsuredIDType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InsuredIDNo', '', fm.InsuredIDNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PayIntv', '', fm.PayIntv.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PayMode', '', fm.PayMode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PayLocation', '', fm.PayLocation.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('DisputedFlag', '', fm.DisputedFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('OutPayFlag', '', fm.OutPayFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('GetPolMode', '', fm.GetPolMode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('SignCom', '', fm.SignCom.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('SignDate', '', fm.SignDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('SignTime', '', fm.SignTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ConsignNo', '', fm.ConsignNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('BankCode', '', fm.BankCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('BankAccNo', '', fm.BankAccNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AccName', '', fm.AccName.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PrintCount', '', fm.PrintCount.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('LostTimes', '', fm.LostTimes.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Lang', '', fm.Lang.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Currency', '', fm.Currency.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Remark', '', fm.Remark.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Peoples', '', fm.Peoples.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Mult', '', fm.Mult.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Prem', '', fm.Prem.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Amnt', '', fm.Amnt.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('SumPrem', '', fm.SumPrem.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Dif', '', fm.Dif.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PaytoDate', '', fm.PaytoDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('FirstPayDate', '', fm.FirstPayDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('CValiDate', '', fm.CValiDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InputOperator', '', fm.InputOperator.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InputDate', '', fm.InputDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('InputTime', '', fm.InputTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ApproveFlag', '', fm.ApproveFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ApproveCode', '', fm.ApproveCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ApproveDate', '', fm.ApproveDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ApproveTime', '', fm.ApproveTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('UWFlag', '', fm.UWFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('UWOperator', '', fm.UWOperator.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('UWDate', '', fm.UWDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('UWTime', '', fm.UWTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppFlag', '', fm.AppFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PolApplyDate', '', fm.PolApplyDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('GetPolDate', '', fm.GetPolDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('GetPolTime', '', fm.GetPolTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('CustomGetPolDate', '', fm.CustomGetPolDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('State', '', fm.State.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Operator', '', fm.Operator.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('MakeDate', '', fm.MakeDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('MakeTime', '', fm.MakeTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ModifyDate', '', fm.ModifyDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ModifyTime', '', fm.ModifyTime.value);
    } catch(ex) {
    }
    ;

    //新的数据处理
    try {
        mSwitch.addVar('AppntNo', '', fm.AppntNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntName', '', fm.AppntName.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntSex', '', fm.AppntSex.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntBirthday', '', fm.AppntBirthday.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntIDType', '', fm.AppntIDType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntIDNo', '', fm.AppntIDNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntPassword', '', fm.AppntPassword.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntNativePlace', '', fm.AppntNativePlace.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntNationality', '', fm.AppntNationality.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AddressNo', '', fm.AppntAddressNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntRgtAddress', '', fm.AppntRgtAddress.value);
    } catch(ex) {
    }
    ;
    //try { mSwitch.addVar('AppntMarriage','',fm.AppntMarriage.value); } catch(ex) { };
    //try { mSwitch.addVar('AppntMarriageDate','',fm.AppntMarriageDate.value); } catch(ex) { };
    try {
        mSwitch.addVar('AppntHealth', '', fm.AppntHealth.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntStature', '', fm.AppntStature.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntAvoirdupois', '', fm.AppntAvoirdupois.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntDegree', '', fm.AppntDegree.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntCreditGrade', '', fm.AppntCreditGrade.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntOthIDType', '', fm.AppntOthIDType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntOthIDNo', '', fm.AppntOthIDNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntICNo', '', fm.AppntICNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntGrpNo', '', fm.AppntGrpNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntJoinCompanyDate', '', fm.AppntJoinCompanyDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntStartWorkDate', '', fm.AppntStartWorkDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntPosition', '', fm.AppntPosition.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntSalary', '', fm.AppntSalary.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntOccupationType', '', fm.AppntOccupationType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntOccupationCode', '', fm.AppntOccupationCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntWorkType', '', fm.AppntWorkType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntPluralityType', '', fm.AppntPluralityType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntDeathDate', '', fm.AppntDeathDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntSmokeFlag', '', fm.AppntSmokeFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntBlacklistFlag', '', fm.AppntBlacklistFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntProterty', '', fm.AppntProterty.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntRemark', '', fm.AppntRemark.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntState', '', fm.AppntState.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntOperator', '', fm.AppntOperator.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntMakeDate', '', fm.AppntMakeDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntMakeTime', '', fm.AppntMakeTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntModifyDate', '', fm.AppntModifyDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntModifyTime', '', fm.AppntModifyTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntHomeAddress', '', fm.AppntHomeAddress.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntHomeZipCode', '', fm.AppntHomeZipCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntHomePhone', '', fm.AppntHomePhone.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntHomeFax', '', fm.AppntHomeFax.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntGrpName', '', fm.AppntGrpName.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntGrpPhone', '', fm.AppntGrpPhone.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('CompanyAddress', '', fm.CompanyAddress.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntGrpZipCode', '', fm.AppntGrpZipCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntGrpFax', '', fm.AppntGrpFax.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntPostalAddress', '', fm.AppntPostalAddress.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntZipCode', '', fm.AppntZipCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntPhone', '', fm.AppntPhone.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntMobile', '', fm.AppntMobile.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntFax', '', fm.AppntFax.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntEMail', '', fm.AppntEMail.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntBankAccNo', '', document.all('AppntBankAccNo').value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntBankCode', '', document.all('AppntBankCode').value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AppntAccName', '', document.all('AppntAccName').value);
    } catch(ex) {
    }
    ;


}

/*********************************************************************
 *  把集体信息从变量中删除
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function delContVar()
{
    try {
        mSwitch.deleteVar("intoPolFlag");
    } catch(ex) {
    }
    ;
    // body信息
    try {
        mSwitch.deleteVar("BODY");
    } catch(ex) {
    }
    ;
    // 集体信息
    //由"./AutoCreatLDGrpInit.jsp"自动生成
    try {
        mSwitch.deleteVar('ContNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ProposalContNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PrtNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('GrpContNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ContType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('FamilyType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PolType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('CardFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ManageCom');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AgentCom');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AgentCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AgentGroup');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AgentCode1');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AgentType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('SaleChnl');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Handler');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Password');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntName');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntSex');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntBirthday');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntIDType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntIDNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InsuredNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InsuredNam');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InsuredSex');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InsuredBirthday');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InsuredIDType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InsuredIDNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PayIntv');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PayMode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PayLocation');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('DisputedFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('OutPayFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('GetPolMode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('SignCom');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('SignDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('SignTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ConsignNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('BankCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('BankAccNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AccName');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PrintCount');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('LostTimes');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Lang');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Currency');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Remark');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Peoples');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Mult');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Prem');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Amnt');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('SumPrem');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Dif');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PaytoDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('FirstPayDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('CValiDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InputOperator');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InputDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InputTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ApproveFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ApproveCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ApproveDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ApproveTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('UWFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('UWOperator');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('UWDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('UWTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PolApplyDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('GetPolDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('GetPolTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('CustomGetPolDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('State');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Operator');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('MakeDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('MakeTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ModifyDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ModifyTime');
    } catch(ex) {
    }
    ;

    //新的删除数据处理
    try {
        mSwitch.deleteVar('AppntNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntName');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntSex');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntBirthday');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntIDType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntIDNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntPassword');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntNativePlace');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntNationality');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AddressNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntRgtAddress');
    } catch(ex) {
    }
    ;
    ////try { mSwitch.deleteVar  ('AppntMarriage'); } catch(ex) { };
    //try { mSwitch.deleteVar  ('AppntMarriageDate'); } catch(ex) { };
    try {
        mSwitch.deleteVar('AppntHealth');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntStature');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntAvoirdupois');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntDegree');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntCreditGrade');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntOthIDType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntOthIDNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntICNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntGrpNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntJoinCompanyDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntStartWorkDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntPosition')
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntSalary');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntOccupationType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntOccupationCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntWorkType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntPluralityType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntDeathDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntSmokeFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntBlacklistFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntProterty');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntRemark');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntState');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntOperator');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntMakeDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntMakeTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntModifyDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntModifyTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntHomeAddress');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntHomeZipCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntHomePhone');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntHomeFax');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntPostalAddress');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntZipCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntPhone');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntFax');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntMobile');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntEMail');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntGrpName');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntGrpPhone');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('CompanyAddress');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntGrpZipCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntGrpFax');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntBankAccNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntBankCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AppntAccName');
    } catch(ex) {
    }
    ;
}


/*********************************************************************
 *  查询返回明细信息时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
 *  参数  ：  查询返回的二维数组
 *  返回值：  无
 *********************************************************************
 */
function afterQuery(arrQueryResult) {

    if (arrQueryResult != null) {
        arrResult = arrQueryResult;

        if (mOperate == 1) {        // 查询投保单
            document.all('ContNo').value = arrQueryResult[0][0];
			
		var sqlid5="CardContInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(arrQueryResult[0][0] );//指定传入的参数
	     var tStr5=mySql5.getString();	
			
			arrResult = easyExecSql(tStr5, 1, 0);
            //arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'", 1, 0);

            if (arrResult == null) {
                alert("未查到投保单信息");
            } else {
                displayLCContPol(arrResult[0]);
            }
        }

        if (mOperate == 2) {        // 投保人信息
            //alert("arrQueryResult[0][0]=="+arrQueryResult[0][0]);
			
		var sqlid6="CardContInputSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(arrQueryResult[0][0]);//指定传入的参数
	     var tStr6=mySql6.getString();	
			
			arrResult = easyExecSql(tStr6, 1, 0);
            //arrResult = easyExecSql("select a.*  from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
            if (arrResult == null) {
                alert("未查到投保人信息");
            } else {
                displayAppnt(arrResult[0]);
            }
        }
    }

    mOperate = 0;
    // 恢复初态

    showCodeName();

}
/*********************************************************************
 *  投保单信息初始化函数。以loadFlag标志作为分支
 *  参数  ：  投保单印刷号
 *  返回值：  无
 *********************************************************************
 */
function detailInit(PrtNo) {
    try {

        if (PrtNo == null) return;

        //查询首期账号
        //alert("PrtNo=="+PrtNo);
		
		var sqlid7="CardContInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(PrtNo);//指定传入的参数
	     var accSQL=mySql7.getString();	
		
//        var accSQL = "select a.BankCode,a.AccName,a.BankAccNo from LJTempFeeClass a,LJTempFee b "
//                + "where trim(a.TempFeeNo)=trim(b.TempFeeNo) and a.PayMode='4' and b.TempFeeType='1' "
//                + "and b.OtherNo='" + PrtNo + "'";

        arrResult = easyExecSql(accSQL, 1, 0);

        if (arrResult == null) {
            //return;
            //默认为续期为银行转账
            //    	alert("aaa");
            //      document.all('PayMode').value="3";
        }
        else {
            displayFirstAccount();
            //如果首期交费方式为银行转账，续期同样为银行转账
            document.all('PayMode').value = "";
        }

		var sqlid8="CardContInputSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(PrtNo);//指定传入的参数
	     var accSQL8=mySql8.getString();	
		 
		 arrResult = easyExecSql(accSQL8, 1, 0);
        //arrResult = easyExecSql("select * from LCCont where PrtNo='" + PrtNo + "'", 1, 0);

        if (arrResult == null) {
            alert(未得到投保单信息);
            return;
        }
        else {
            displayLCCont();
            //显示投保单详细内容
        }

		var sqlid9="CardContInputSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		mySql9.addSubPara(PrtNo);//指定传入的参数
	     var accSQL9=mySql9.getString();	
		 
		  arrResult = easyExecSql(accSQL9, 1, 0);
       // arrResult = easyExecSql("select a.* from LDPerson a where 1=1  and a.CustomerNo = '" + arrResult[0][19] + "'", 1, 0);

        if (arrResult == null) {
            alert("未查到投保人客户层信息");
        }
        else {
            //显示投保人信息
            displayAppnt(arrResult[0]);
        }

		var sqlid10="CardContInputSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql10.setSqlId(sqlid10);//指定使用的Sql的id
		mySql10.addSubPara(PrtNo);//指定传入的参数
	     var accSQL10=mySql10.getString();	

        arrResult = easyExecSql(accSQL10, 1, 0);
       // arrResult = easyExecSql("select * from LCAppnt where PrtNo='" + PrtNo + "'", 1, 0);

        if (arrResult == null) {
            alert("未得到投保人保单层信息");
            return;
        } else {
            displayContAppnt();
            //显示投保人的详细内容
        }
        getAge();
        var tContNo = arrResult[0][1];
        var tCustomerNo = arrResult[0][3];
        // 得到投保人客户号
        var tAddressNo = arrResult[0][9];
        // 得到投保人地址号

        //alert("--tContNo=="+tContNo+"--tCustomerNo--"+tCustomerNo+"--tAddressNo--"+tAddressNo);


        /**************
            arrResult=easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='"+tCustomerNo+"'",1,0);
            if(arrResult==null){
              alert("未得到用户信息");
              return;
            }else{
              displayAppnt();       //显示投保人详细内容
              emptyUndefined();
            }
        ******************/
        /******************
            arrResult=easyExecSql("select * from LCAddress where AddressNo='"+tAddressNo+"' and CustomerNo='"+tCustomerNo+"'",1,0);
            if(arrResult==null){
              alert(未得到投保人地址信息);
              return;
            }else{
              displayAddress();       //显示投保人地址详细内容
            }

        *********************/

        fm.AppntAddressNo.value = tAddressNo;

        //查询并显示投保人地址详细内容
        getdetailaddress();

        //alert("zzz");
        //查询投保人告知信息
		
		var sqlid11="CardContInputSql11";
		var mySql11=new SqlClass();
		mySql11.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql11.setSqlId(sqlid11);//指定使用的Sql的id
		mySql11.addSubPara(tCustomerNo);//指定传入的参数
		mySql11.addSubPara(tContNo);//指定传入的参数
	     var strSQL0=mySql11.getString();	
		 
		 var sqlid12="CardContInputSql12";
		var mySql12=new SqlClass();
		mySql12.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql12.setSqlId(sqlid12);//指定使用的Sql的id
		mySql12.addSubPara(tCustomerNo);//指定传入的参数
		mySql12.addSubPara(tContNo);//指定传入的参数
	     var strSQL1=mySql12.getString();	
		
//        var strSQL0 = "select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='" + tCustomerNo + "' and ContNo='" + tContNo + "' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
//        var strSQL1 = "select ImpartParam from LCCustomerImpartparams where CustomerNoType='0' and CustomerNo='" + tCustomerNo + "' and ContNo='" + tContNo + "' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";
        arrResult = easyExecSql(strSQL0, 1, 0);
        arrResult1 = easyExecSql(strSQL1, 1, 0);


        try {
            document.all('Income0').value = arrResult[0][0];
        } catch(ex) {
        }
        ;
        try {
            document.all('IncomeWay0').value = arrResult1[0][0];
        } catch(ex) {
        }
        ;

		 var sqlid13="CardContInputSql13";
		var mySql13=new SqlClass();
		mySql13.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql13.setSqlId(sqlid13);//指定使用的Sql的id
		mySql13.addSubPara(tCustomerNo);//指定传入的参数
		mySql13.addSubPara(tContNo);//指定传入的参数
	     var strSQL1=mySql13.getString();	

       // var strSQL1 = "select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='0' and CustomerNo='" + tCustomerNo + "' and ContNo='" + tContNo + "' and impartver in ('01','02') and impartcode<>'001'";

        turnPage.strQueryResult = easyQueryVer3(strSQL1, 1, 0, 1);

        //判断是否查询成功,成功则显示告知信息
        if (turnPage.strQueryResult) {
            //查询成功则拆分字符串，返回二维数组
            turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
            //设置初始化过的MULTILINE对象
            turnPage.pageDisplayGrid = ImpartGrid;
            //保存SQL语句
            turnPage.strQuerySql = strSQL1;
            //设置查询起始位置
            turnPage.pageIndex = 0;
            //在查询结果数组中取出符合页面显示大小设置的数组
            arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
            //调用MULTILINE对象显示查询结果
            displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
        }
        else {
            initImpartGrid();
        }

        //如果有分支
        if (loadFlag == "5" || loadFlag == "25") {
            //showCodeName();
        }
        //alert("tContNo=="+tContNo);

        /***********************************个险取消多业务员故取消
            //查询已有多业务员信息
            var muliAgentSQL="select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr "+
                             "from lccont a,labranchgroup b,lacommisiondetail c,laagent d "+
                             "where 1=1 "+
                             "and trim(a.contno)='" + tContNo + "' " +
                             "and trim(b.agentgroup)=trim(c.agentgroup) " +
                             "and trim(c.agentcode)!=trim(a.agentcode) " +
                             "and trim(d.agentcode)=trim(c.agentcode) " +
                             "and trim(d.agentcode)!=trim(a.agentcode) " +
                             "and trim(c.grpcontno)=trim(a.contno) "
                             ;
            //alert("--muliAgentSQL="+muliAgentSQL);
            turnPage.strQueryResult = easyQueryVer3(muliAgentSQL, 1, 0, 1);

            //alert("--turnPage.strQueryResult=="+turnPage.strQueryResult);
            //判断是否查询成功,成功则显示告知信息
            if (turnPage.strQueryResult) {
              //查询成功则拆分字符串，返回二维数组
              turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
              //设置初始化过的MULTILINE对象
              turnPage.pageDisplayGrid = MultiAgentGrid;
              //保存SQL语句
              turnPage.strQuerySql = muliAgentSQL;
              //设置查询起始位置
              turnPage.pageIndex = 0;
              //在查询结果数组中取出符合页面显示大小设置的数组
              arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
              //调用MULTILINE对象显示查询结果
              displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
              //显示多业务员
              displayMultiAgent();
            }
            else{
              //初始化多业务员列表
              //alert("saafas");
              initMultiAgentGrid();
            }
        ********************************************/
        /***********************************/
        //查询主业务员信息
		
		var sqlid14="CardContInputSql14";
		var mySql14=new SqlClass();
		mySql14.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql14.setSqlId(sqlid14);//指定使用的Sql的id
		mySql14.addSubPara(tCustomerNo);//指定传入的参数
		mySql14.addSubPara(tContNo);//指定传入的参数
	     var strSQL14=mySql14.getString();	
		
		 arrResult = easyExecSql(strSQL14, 1, 0);
//        arrResult = easyExecSql("select c.agentcode,d.name,d.managecom,b.name,c.busirate,a.agentgroup,b.branchattr " +
//                                "from lccont a,labranchgroup b,lacommisiondetail c,laagent d " +
//                                "where 1=1 " +
//                                "and trim(a.contno)='" + tContNo + "' " +
//                                "and trim(b.agentgroup)=trim(c.agentgroup) " +
//                                "and trim(c.agentcode)=trim(a.agentcode) " +
//                                "and trim(d.agentcode)=trim(a.agentcode) " +
//                                "and trim(c.grpcontno)=trim(a.contno)", 1, 0);

        if (arrResult == null) {
            //alert("未得到主业务员信息");
            //return;
        } else {
            displayMainAgent();
            //显示主业务员的详细内容
        }
        /**************************************/
        //alert();
        //////////////////////////////////////////

        displayAgent();
        //显示主业务员的详细内容
        //查询业务员信息
        queryAgent();

        //////////////////////////////////////////////
        //查询业务员告知

		var sqlid15="CardContInputSql15";
		var mySql15=new SqlClass();
		mySql15.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql15.setSqlId(sqlid15);//指定使用的Sql的id
		mySql15.addSubPara(tCustomerNo);//指定传入的参数
		mySql15.addSubPara(tContNo);//指定传入的参数
	     var aSQL=mySql15.getString();	

       // var aSQL = "select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNoType='2' and CustomerNo='" + tCustomerNo + "' and ContNo='" + tContNo + "'";
        turnPage.queryModal(aSQL, AgentImpartGrid);


        //////////////////////////////////////////////////


    }
    catch(ex) {
    }

}


function displayAgent()
{
    if (fm.AgentCode.value != "") {
        var cAgentCode = fm.AgentCode.value;
        //保单号码
		
		var sqlid16="CardContInputSql16";
		var mySql16=new SqlClass();
		mySql16.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql16.setSqlId(sqlid16);//指定使用的Sql的id
		mySql16.addSubPara(cAgentCode);//指定传入的参数
	     var strSQL=mySql16.getString();	
		
//        var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//                + "and a.AgentCode = b.AgentCode and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='" + cAgentCode + "'";
        //alert(strSQL);
        var arrResult = easyExecSql(strSQL);
        //alert(arrResult);
        if (arrResult != null) {
            fm.AgentCode.value = arrResult[0][0];
            fm.BranchAttr.value = arrResult[0][10];
            fm.AgentGroup.value = arrResult[0][1];
            fm.AgentName.value = arrResult[0][3];
            fm.AgentManageCom.value = arrResult[0][2];
            showContCodeName();
            //alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
        }
        else
        {
            fm.AgentGroup.value = "";
            alert("编码为:[" + document.all('AgentCode').value + "]的业务员不存在，请确认!");
        }
    }
    else
    {
        return;
    }
}


/*********************************************************************
*  把查询返回的客户数据显示到投保人部分
*  参数  ：  无
*  返回值：  无
*********************************************************************
*/
function displayAccount()
{
    try {
        document.all('AppntBankAccNo').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankCode').value = arrResult[0][23];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAccName').value = arrResult[0][25];
    } catch(ex) {
    }
    ;

}
/*********************************************************************
 *  把查询返回的客户数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayCustomer()
{
    try {
        document.all('AppntNationality').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
}
/*********************************************************************
 *  把查询返回的客户地址数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAddress()
{
    try {
        document.all('AppntAddressNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPostalAddress').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntZipCode').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPhone').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMobile').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntEMail').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    //try{document.all('GrpName').value= arrResult[0][6];}catch(ex){};
    try {
        document.all('AppntGrpPhone').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('CompanyAddress').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpZipCode').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    /*
            try{document.all('AppntPostalAddress').value= arrResult[0][2]; }catch(ex){};
        try{document.all('AppntZipCode').value= arrResult[0][3]; }catch(ex){};
        try{document.all('AppntPhone').value= arrResult[0][4]; }catch(ex){};
        try{document.all('AppntMobile').value= arrResult[0][14]; }catch(ex){};
        try{document.all('AppntEMail').value= arrResult[0][16]; }catch(ex){};
        //try{document.all('AppntGrpName').value= arrResult[0][2]; }catch(ex){};
        try{document.all('AppntGrpPhone').value= arrResult[0][12]; }catch(ex){};
        try{document.all('CompanyAddress').value= arrResult[0][10]; }catch(ex){};
        try{document.all('AppntGrpZipCode').value= arrResult[0][11]; }catch(ex){};
    */
}

/*********************************************************************
 *  把查询返回的合同中被保人数据显示到页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayContAppnt()
{
    try {
        document.all('AppntGrpContNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntContNo').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPrtNo').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNo').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrade').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntName').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSex').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBirthday').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntType').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAddressNo').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDType').value = arrResult[0][10];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDNo').value = arrResult[0][11];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNativePlace').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNationality').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntRgtAddress').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    //  try{document.all('AppntMarriage').value= arrResult[0][15];}catch(ex){};
    //  try{document.all('AppntMarriageDate').value= arrResult[0][16];}catch(ex){};
    try {
        document.all('AppntHealth').value = arrResult[0][17];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntStature').value = arrResult[0][18];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAvoirdupois').value = arrResult[0][19];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntDegree').value = arrResult[0][20];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntCreditGrade').value = arrResult[0][21];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankCode').value = arrResult[0][22];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankAccNo').value = arrResult[0][23];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAccName').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntJoinCompanyDate').value = arrResult[0][25];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntStartWorkDate').value = arrResult[0][26];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPosition').value = arrResult[0][27];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSalary').value = arrResult[0][28];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOccupationType').value = arrResult[0][29];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOccupationCode').value = arrResult[0][30];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntWorkType').value = arrResult[0][31];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPluralityType').value = arrResult[0][32];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSmokeFlag').value = arrResult[0][33];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOperator').value = arrResult[0][34];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntManageCom').value = arrResult[0][35];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMakeDate').value = arrResult[0][36];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMakeTime').value = arrResult[0][37];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntModifyDate').value = arrResult[0][38];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntModifyTime').value = arrResult[0][39];
    } catch(ex) {
    }
    ;
    //getAge();
}
/*********************************************************************
 *  把查询返回的投保人数据显示到投保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function displayAppnt()
{
    //alert("here in 928 col");
    try {
        document.all('AppntNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntName').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSex').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBirthday').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDType').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDNo').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPassword').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNativePlace').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNationality').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntRgtAddress').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    //try{document.all('AppntMarriage').value= arrResult[0][10];}catch(ex){};
    //try{document.all('AppntMarriageDate').value= arrResult[0][11];}catch(ex){};
    try {
        document.all('AppntHealth').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntStature').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAvoirdupois').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntDegree').value = arrResult[0][15];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntCreditGrade').value = arrResult[0][16];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOthIDType').value = arrResult[0][17];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOthIDNo').value = arrResult[0][18];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntICNo').value = arrResult[0][19];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpNo').value = arrResult[0][20];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntJoinCompanyDate').value = arrResult[0][21];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntStartWorkDate').value = arrResult[0][22];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPosition').value = arrResult[0][23];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSalary').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOccupationType').value = arrResult[0][25];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOccupationCode').value = arrResult[0][26];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntWorkType').value = arrResult[0][27];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPluralityType').value = arrResult[0][28];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntDeathDate').value = arrResult[0][29];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSmokeFlag').value = arrResult[0][30];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBlacklistFlag').value = arrResult[0][31];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntProterty').value = arrResult[0][32];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntRemark').value = arrResult[0][33];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntState').value = arrResult[0][34];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntVIPValue').value = arrResult[0][35];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOperator').value = arrResult[0][36];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMakeDate').value = arrResult[0][37];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMakeTime').value = arrResult[0][38];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntModifyDate').value = arrResult[0][39];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntModifyTime').value = arrResult[0][40];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpName').value = arrResult[0][41];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntLicenseType').value = arrResult[0][43];
    } catch(ex) {
    }
    ;


    //顺便将投保人地址信息等进行初始化
    try {
        document.all('AppntPostalAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPostalAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntZipCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntFax').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMobile').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntEMail').value = "";
    } catch(ex) {
    }
    ;
    //try{document.all('AppntGrpName').value= "";}catch(ex){};
    try {
        document.all('AppntHomeAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomeZipCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomePhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomeFax').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpPhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('CompanyAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpZipCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpFax').value = "";
    } catch(ex) {
    }
    ;
}
/**
 *投保单详细内容显示
 */
function displayLCCont() {
    //alert("aaa");
    try {
        document.all('GrpContNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('ContNo').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('ProposalContNo').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('PrtNo').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('ContType').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('FamilyType').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('FamilyID').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('PolType ').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('CardFlag').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('ManageCom').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    try {
        document.all('ExecuteCom ').value = arrResult[0][10];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentCom').value = arrResult[0][11];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentCode').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentGroup').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentCode1 ').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    try {
        document.all('AgentType').value = arrResult[0][15];
    } catch(ex) {
    }
    ;
    try {
        document.all('SaleChnl').value = arrResult[0][16];
    } catch(ex) {
    }
    ;
    try {
        document.all('Handler').value = arrResult[0][17];
    } catch(ex) {
    }
    ;
    try {
        document.all('Password').value = arrResult[0][18];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNo').value = arrResult[0][19];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntName').value = arrResult[0][20];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSex').value = arrResult[0][21];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBirthday ').value = arrResult[0][22];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDType').value = arrResult[0][23];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDNo').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredNo').value = arrResult[0][25];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredName').value = arrResult[0][26];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredSex').value = arrResult[0][27];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredBirthday').value = arrResult[0][28];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredIDType ').value = arrResult[0][29];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredIDNo').value = arrResult[0][30];
    } catch(ex) {
    }
    ;
    try {
        document.all('PayIntv').value = arrResult[0][31];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecPayMode').value = arrResult[0][32];
    } catch(ex) {
    }
    ;
    try {
        document.all('PayLocation').value = arrResult[0][33];
    } catch(ex) {
    }
    ;
    try {
        document.all('DisputedFlag').value = arrResult[0][34];
    } catch(ex) {
    }
    ;
    try {
        document.all('OutPayFlag').value = arrResult[0][35];
    } catch(ex) {
    }
    ;
    try {
        document.all('GetPolMode').value = arrResult[0][36];
    } catch(ex) {
    }
    ;
    try {
        document.all('SignCom').value = arrResult[0][37];
    } catch(ex) {
    }
    ;
    try {
        document.all('SignDate').value = arrResult[0][38];
    } catch(ex) {
    }
    ;
    try {
        document.all('SignTime').value = arrResult[0][39];
    } catch(ex) {
    }
    ;
    try {
        document.all('ConsignNo').value = arrResult[0][40];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntBankCode').value = arrResult[0][41];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntBankAccNo').value = arrResult[0][42];
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntAccName').value = arrResult[0][43];
    } catch(ex) {
    }
    ;
    try {
        document.all('PrintCount').value = arrResult[0][44];
    } catch(ex) {
    }
    ;
    try {
        document.all('LostTimes').value = arrResult[0][45];
    } catch(ex) {
    }
    ;
    try {
        document.all('Lang').value = arrResult[0][46];
    } catch(ex) {
    }
    ;
    try {
        document.all('Currency').value = arrResult[0][47];
    } catch(ex) {
    }
    ;
    try {
        document.all('Remark').value = arrResult[0][48];
    } catch(ex) {
    }
    ;
    try {
        document.all('Peoples ').value = arrResult[0][49];
    } catch(ex) {
    }
    ;
    try {
        document.all('Mult').value = arrResult[0][50];
    } catch(ex) {
    }
    ;
    try {
        document.all('Prem').value = arrResult[0][51];
    } catch(ex) {
    }
    ;
    try {
        document.all('Amnt').value = arrResult[0][52];
    } catch(ex) {
    }
    ;
    try {
        document.all('SumPrem').value = arrResult[0][53];
    } catch(ex) {
    }
    ;
    try {
        document.all('Dif').value = arrResult[0][54];
    } catch(ex) {
    }
    ;
    try {
        document.all('PaytoDate').value = arrResult[0][55];
    } catch(ex) {
    }
    ;
    try {
        document.all('FirstPayDate').value = arrResult[0][56];
    } catch(ex) {
    }
    ;
    try {
        document.all('CValiDate').value = arrResult[0][57];
    } catch(ex) {
    }
    ;
    try {
        document.all('InputOperator ').value = arrResult[0][58];
    } catch(ex) {
    }
    ;
    try {
        document.all('InputDate').value = arrResult[0][59];
    } catch(ex) {
    }
    ;
    try {
        document.all('InputTime').value = arrResult[0][60];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveFlag').value = arrResult[0][61];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveCode').value = arrResult[0][62];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveDate').value = arrResult[0][63];
    } catch(ex) {
    }
    ;
    try {
        document.all('ApproveTime').value = arrResult[0][64];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWFlag').value = arrResult[0][65];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWOperator').value = arrResult[0][66];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWDate').value = arrResult[0][67];
    } catch(ex) {
    }
    ;
    try {
        document.all('UWTime').value = arrResult[0][68];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppFlag').value = arrResult[0][69];
    } catch(ex) {
    }
    ;
    try {
        document.all('PolAppntDate').value = arrResult[0][70];
    } catch(ex) {
    }
    ;
    try {
        document.all('GetPolDate').value = arrResult[0][71];
    } catch(ex) {
    }
    ;
    try {
        document.all('GetPolTime').value = arrResult[0][72];
    } catch(ex) {
    }
    ;
    try {
        document.all('CustomGetPolDate').value = arrResult[0][73];
    } catch(ex) {
    }
    ;
    try {
        document.all('State').value = arrResult[0][74];
    } catch(ex) {
    }
    ;
    try {
        document.all('Operator').value = arrResult[0][75];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeDate').value = arrResult[0][76];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeTime').value = arrResult[0][77];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyDate').value = arrResult[0][78];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyTime').value = arrResult[0][79];
    } catch(ex) {
    }
    ;
    try {
        document.all('SellType').value = arrResult[0][87];
    } catch(ex) {
    }
    ;

    try {
        document.all('AppntBankCode').value = arrResult[0][90];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankAccNo').value = arrResult[0][91];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAccName').value = arrResult[0][92];
    } catch(ex) {
    }
    ;
    try {
        document.all('PayMode').value = arrResult[0][93];
    } catch(ex) {
    }
    ;

}
//使得从该窗口弹出的窗口能够聚焦
function myonfocus()
{
    if (showInfo != null)
    {
        try
        {
            showInfo.focus();
        }
        ;
    catch
        (ex)
    {
        showInfo = null;
    }
    }
}


//投保人客户号查询按扭事件
function queryAppntNo() {

    if (document.all("AppntNo").value == "" && loadFlag == "1")
    {
        showAppnt1();
    }
    else if (loadFlag != "1" && loadFlag != "2")
    {
        alert("只能在投保单录入时进行操作！");
    }
    else
    {
		
		var sqlid17="CardContInputSql17";
		var mySql17=new SqlClass();
		mySql17.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql17.setSqlId(sqlid17);//指定使用的Sql的id
		mySql17.addSubPara(fm.AppntNo.value);//指定传入的参数
	     var strSQL17=mySql17.getString();	
		
		arrResult = easyExecSql(strSQL17, 1, 0);
        //arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("AppntNo").value + "'", 1, 0);
        if (arrResult == null) {
            alert("未查到投保人信息");
            displayAppnt(new Array());
            emptyUndefined();
        }
        else
        {
            displayAppnt(arrResult[0]);
        }
    }
}

function showAppnt1()

{
    //alert("here in 1115 row");
    if (mOperate == 0)
    {
        mOperate = 2;
        showInfo = window.open("../sys/LDPersonQueryNewMain.jsp?queryFlag=queryAppnt","",sFeatures);
    }
}

function queryAgent()
{

    if (document.all('ManageCom').value == "") {
        alert("请先录入管理机构信息！");
        return;
    }
    if (document.all('AgentCode').value == "") {
        //var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+",AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
        var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom=" + document.all('ManageCom').value, "AgentCommonQueryMain", 'width=' + screen.availWidth + ',height=' + screen.availHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
    }
    //alert(0)
    if (document.all('AgentCode').value != "") {

        var cAgentCode = fm.AgentCode.value;
        //保单号码

        //如果业务员代码长度为8则自动查询出相应的代码名字等信息
        //alert("cAgentCode=="+cAgentCode);
        if (cAgentCode.length != 8) {
            return;
        }
        //var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"' and ManageCom = '"+document.all('ManageCom').value+"'";
      
//	  	var sqlid18="CardContInputSql18";
//		var mySql18=new SqlClass();
//		mySql18.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
//		mySql18.setSqlId(sqlid18);//指定使用的Sql的id
//		mySql18.addSubPara(cAgentCode);//指定传入的参数
//	     var strSQL=mySql18.getString();	
//	  
//	    var strSQL = "select a.AgentCode,a.AgentGroup,a.ManageCom,a.Name,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade,c.BranchAttr,b.AscriptSeries,c.name from LAAgent a,LATree b,LABranchGroup c where 1=1 "
//                + "and a.AgentCode = b.AgentCode and a.agentstate!='03' and a.agentstate!='04' and a.AgentGroup = c.AgentGroup and a.AgentCode='" + cAgentCode + "'";

	  	var sqlid81="CardContInputSql81";
		var mySql81=new SqlClass();
		mySql81.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql81.setSqlId(sqlid81);//指定使用的Sql的id
		mySql81.addSubPara(cAgentCode);//指定传入的参数
	    var strSQL=mySql81.getString();
	    
	    
        var arrResult = easyExecSql(strSQL);

        if (arrResult != null) {
            fm.AgentCode.value = arrResult[0][0];
            fm.BranchAttr.value = arrResult[0][10];
            fm.AgentGroup.value = arrResult[0][1];
            fm.AgentName.value = arrResult[0][3];
            fm.AgentManageCom.value = arrResult[0][2];

            if (fm.AgentManageCom.value != fm.ManageCom.value)
            {

                fm.ManageCom.value = fm.AgentManageCom.value;
                fm.ManageComName.value = fm.AgentManageComName.value;
                //showCodeName('comcode','ManageCom','AgentManageComName');  //代码汉化

            }
            showContCodeName();
            //alert("查询结果:  代理人编码:["+arrResult[0][0]+"] 代理人名称为:["+arrResult[0][1]+"]");
        }
        else
        {
            fm.AgentGroup.value = "";
            alert("编码为:[" + document.all('AgentCode').value + "]的业务员不存在，请确认!");
            return;
        }
    }
}

//问题件录入
function QuestInput2()
{
    cContNo = fm.ContNo.value;
    //保单号码
    if (cContNo == "")
    {
        alert("尚无合同投保单号，请先保存!");
    }
    else
    {
        window.open("../uw/QuestInputMain.jsp?ContNo=" + cContNo + "&Flag=" + LoadFlag, "window1",sFeatures);
    }
}

//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery1(arrQueryResult)
{
    //document.all("Donextbutton1").style.display="";
    //document.all("Donextbutton2").style.display="none";
    //document.all("Donextbutton3").style.display="";
    //document.all("butBack").style.display="none";
    //详细信息初始化
    detailInit(arrQueryResult);

}
//查询返回时执行的函数,查询返回一个2维数组，数组下标从[0][0]开始
function afterQuery2(arrResult)
{

    if (arrResult != null)
    {
        //  	fm.AgentCode.value = arrResult[0][0];
        //  	fm.BranchAttr.value = arrResult[0][93];
        //  	fm.AgentGroup.value = arrResult[0][1];
        //  	fm.AgentName.value = arrResult[0][5];
        //  	fm.AgentManageCom.value = arrResult[0][2];
        fm.AgentCode.value = arrResult[0][0];
        fm.BranchAttr.value = arrResult[0][10];
        fm.AgentGroup.value = arrResult[0][1];
        fm.AgentName.value = arrResult[0][3];
        fm.AgentManageCom.value = arrResult[0][2];

        if (fm.AgentManageCom.value != fm.ManageCom.value)
        {

            fm.ManageCom.value = fm.AgentManageCom.value;
            fm.ManageComName.value = fm.AgentManageComName.value;
        }
        showContCodeName();
    }
}
//返回showContCodeName();                                                          查询结果，赋给多业务员multline
function afterQuery3(arrResult)
{
    if (arrResult != null)
    {
        document.all(tField).all('MultiAgentGrid1').value = arrResult[0][0];
        document.all(tField).all('MultiAgentGrid2').value = arrResult[0][3];
        document.all(tField).all('MultiAgentGrid3').value = arrResult[0][2];
        document.all(tField).all('MultiAgentGrid4').value = arrResult[0][10];
        document.all(tField).all('MultiAgentGrid6').value = arrResult[0][1];
        document.all(tField).all('MultiAgentGrid7').value = arrResult[0][6];
    }

}


function queryAgent2()
{

    if (document.all('ManageCom').value == "") {
        alert("请先录入管理机构信息！");
        return;
    }
    if (document.all('AgentCode').value != "" && document.all('AgentCode').value.length == 10) {
        var cAgentCode = fm.AgentCode.value;
        //保单号码
		
		var sqlid19="CardContInputSql19";
		var mySql19=new SqlClass();
		mySql19.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql19.setSqlId(sqlid19);//指定使用的Sql的id
		mySql19.addSubPara(cAgentCode);//指定传入的参数
		mySql19.addSubPara(document.all('ManageCom').value);//指定传入的参数
	     var strSql=mySql19.getString();	
		
       // var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode + "' and ManageCom = '" + document.all('ManageCom').value + "'";
        var arrResult = easyExecSql(strSql);
        //alert(arrResult);
        if (arrResult != null) {
            fm.AgentGroup.value = arrResult[0][2];
            alert("查询结果:  业务员编码:[" + arrResult[0][0] + "] 业务员名称为:[" + arrResult[0][1] + "]");
        }
        else {
            fm.AgentGroup.value = "";
            alert("编码为:[" + document.all('AgentCode').value + "]的业务员不存在，请确认!");
        }
    }
}
function getdetail()
{
    //alert("fm.AppntNo.value=="+fm.AppntNo.value);
	
			var sqlid20="CardContInputSql20";
		var mySql20=new SqlClass();
		mySql20.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql20.setSqlId(sqlid20);//指定使用的Sql的id
		mySql20.addSubPara(document.all('AppntBankAccNo').value);//指定传入的参数
		mySql20.addSubPara(document.all('AppntNo').value);//指定传入的参数
	     var strSql=mySql20.getString();	
	
 //   var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.AppntBankAccNo.value + "' and customerno='" + fm.AppntNo.value + "'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null) {
        fm.AppntBankCode.value = arrResult[0][0];
        fm.AppntAccName.value = arrResult[0][1];
    }
    else {
        //fm.AppntBankCode.value = '';
        //fm.AppntAccName.value = '';
        return;
    }
}
function getdetailwork()
{

    showOneCodeName('OccupationCode', 'AppntOccupationCode', 'AppntOccupationCodeName');
	
		var sqlid21="CardContInputSql21";
		var mySql21=new SqlClass();
		mySql21.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql21.setSqlId(sqlid21);//指定使用的Sql的id
		mySql21.addSubPara(document.all('AppntOccupationCode').value);//指定传入的参数
	     var strSql=mySql21.getString();	
	
  //  var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.AppntOccupationCode.value + "'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null)
    {
        fm.AppntOccupationType.value = arrResult[0][0];
        showOneCodeName('OccupationType', 'AppntOccupationType', 'AppntOccupationTypeName');
    }
    else
    {
        fm.AppntOccupationType.value = "";
        fm.AppntOccupationTypeName.value = "";
    }
}

/********************
**
** 查询投保人地址信息
**
*********************/
function getdetailaddress() {
	
		var sqlid22="CardContInputSql22";
		var mySql22=new SqlClass();
		mySql22.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql22.setSqlId(sqlid22);//指定使用的Sql的id
		mySql22.addSubPara(document.all('AppntAddressNo').value);//指定传入的参数
		mySql22.addSubPara(document.all('AppntNo').value);//指定传入的参数
	     var strSQL=mySql22.getString();	
	
   // var strSQL = "select b.* from LCAddress b where b.AddressNo='" + fm.AppntAddressNo.value + "' and b.CustomerNo='" + fm.AppntNo.value + "'";
    arrResult = easyExecSql(strSQL);
    //alert("arrResult[0][1]=="+arrResult[0][1]);
    try {
        document.all('AppntNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAddressNo').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPostalAddress').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntZipCode').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPhone').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntFax').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomeAddress').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomeZipCode').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomePhone').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomeFax').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    try {
        document.all('CompanyAddress').value = arrResult[0][10];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpZipCode').value = arrResult[0][11];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpPhone').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpFax').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMobile').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMobileChs').value = arrResult[0][15];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntEMail').value = arrResult[0][16];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBP').value = arrResult[0][17];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMobile2').value = arrResult[0][18];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMobileChs2').value = arrResult[0][19];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntEMail2').value = arrResult[0][20];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBP2').value = arrResult[0][21];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntProvince').value = arrResult[0][28];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntCity').value = arrResult[0][29];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntDistrict').value = arrResult[0][30];
    } catch(ex) {
    }
    ;
}
function getaddresscodedata()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    if (fm.AppntAddressNo.value == "")
    {
        fm.AppntPostalAddress.value = "";
        fm.AddressNoName.value = "";
        fm.AppntProvinceName.value = "";
        fm.AppntProvince.value = "";
        fm.AppntCityName.value = "";
        fm.AppntCity.value = "";
        fm.AppntDistrictName.value = "";
        fm.AppntDistrict.value = "";
        fm.AppntZipCode.value = "";
        fm.AppntFax.value = "";
        fm.AppntEMail.value = "";
        fm.AppntDistrict.value = "";
        //	fm.AppntGrpName.value="";
        fm.AppntHomePhone.value = "";
        fm.AppntMobile.value = "";
        fm.AppntGrpPhone.value = "";
    }
	
		var sqlid23="CardContInputSql23";
		var mySql23=new SqlClass();
		mySql23.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql23.setSqlId(sqlid23);//指定使用的Sql的id
		mySql23.addSubPara(document.all('AppntNo').value);//指定传入的参数
	     strsql=mySql23.getString();	
	
   // strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='" + fm.AppntNo.value + "'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }
    //alert ("tcodedata : " + tCodeData);
    //return tCodeData;
    document.all("AppntAddressNo").CodeData = tCodeData;
}
function afterCodeSelect(cCodeName, Field)
{
    //alert("afdasdf");
    if (cCodeName == "GetAddressNo") {
		
		var sqlid24="CardContInputSql24";
		var mySql24=new SqlClass();
		mySql24.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql24.setSqlId(sqlid24);//指定使用的Sql的id
		mySql24.addSubPara(document.all('AppntAddressNo').value);//指定传入的参数
		mySql24.addSubPara(document.all('AppntNo').value);//指定传入的参数
	    var strSQL=mySql24.getString();	
		
        //var strSQL = "select b.* from LCAddress b where b.AddressNo='" + fm.AppntAddressNo.value + "' and b.CustomerNo='" + fm.AppntNo.value + "'";
        arrResult = easyExecSql(strSQL);
        try {
            document.all('AppntNo').value = arrResult[0][0];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntAddressNo').value = arrResult[0][1];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntPostalAddress').value = arrResult[0][2];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntZipCode').value = arrResult[0][3];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntPhone').value = arrResult[0][4];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntFax').value = arrResult[0][5];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntHomeAddress').value = arrResult[0][6];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntHomeZipCode').value = arrResult[0][7];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntHomePhone').value = arrResult[0][8];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntHomeFax').value = arrResult[0][9];
        } catch(ex) {
        }
        ;
        //try{document.all('CompanyAddress').value= arrResult[0][10];}catch(ex){};
        try {
            document.all('AppntGrpZipCode').value = arrResult[0][11];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntGrpPhone').value = arrResult[0][12];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntGrpFax').value = arrResult[0][13];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntMobile').value = arrResult[0][14];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntMobileChs').value = arrResult[0][15];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntEMail').value = arrResult[0][16];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntBP').value = arrResult[0][17];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntMobile2').value = arrResult[0][18];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntMobileChs2').value = arrResult[0][19];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntEMail2').value = arrResult[0][20];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntBP2').value = arrResult[0][21];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntProvince').value = arrResult[0][28];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntCity').value = arrResult[0][29];
        } catch(ex) {
        }
        ;
        try {
            document.all('AppntDistrict').value = arrResult[0][30];
        } catch(ex) {
        }
        ;
        showOneCodeName('province', 'AppntProvince', 'AppntProvinceName');
        showOneCodeName('city', 'AppntCity', 'AppntCityName');
        showOneCodeName('district', 'AppntDistrict', 'AppntDistrictName');
        return;
    }
    if (cCodeName == "paymode") {
        if (document.all('PayMode').value == "4") {
            //divLCAccount1.style.display="";
        }
        else
        {
            //divLCAccount1.style.display="none";
            //alert("accountImg===");
        }
    }

    //自动填写受益人信息
    if (cCodeName == "customertype") {
        if (Field.value == "A") {
            if (ContType != "2")
            {
                var index = BnfGrid.mulLineCount;
                BnfGrid.setRowColData(index - 1, 2, document.all("AppntName").value);
                BnfGrid.setRowColData(index - 1, 4, document.all("AppntIDType").value);

                BnfGrid.setRowColData(index - 1, 5, document.all("AppntIDNo").value);
                BnfGrid.setRowColData(index - 1, 3, document.all("RelationToAppnt").value);

                BnfGrid.setRowColData(index - 1, 8, document.all("AppntHomeAddress").value);
                //hl
                BnfGrid.setRowColData(index - 1, 9, document.all("AppntNo").value);
                //alert("toubaoren:"+document.all("AppntNo").value)

            }
            else
            {
                alert("投保人为团体，不能作为受益人！")
                var index = BnfGrid.mulLineCount;
                BnfGrid.setRowColData(index - 1, 2, "");
                BnfGrid.setRowColData(index - 1, 3, "");
                BnfGrid.setRowColData(index - 1, 4, "");
                BnfGrid.setRowColData(index - 1, 5, "");
                BnfGrid.setRowColData(index - 1, 8, "");
            }
        }
        else if (Field.value == "I") {
            var index = BnfGrid.mulLineCount;
            BnfGrid.setRowColData(index - 1, 2, document.all("Name").value);
            BnfGrid.setRowColData(index - 1, 4, document.all("IDType").value);
            BnfGrid.setRowColData(index - 1, 5, document.all("IDNo").value);
            BnfGrid.setRowColData(index - 1, 3, "00");
            //BnfGrid.setRowColData(index-1, 8, document.all("AppntHomeAddress").value);
            //hl
            BnfGrid.setRowColData(index - 1, 8, document.all("HomeAddress").value);

            BnfGrid.setRowColData(index - 1, 9, document.all("InsuredNo").value);
            //alert("4544564"+document.all("InsuredNo").value);
        }
        return;
    }
    
    if (cCodeName == "CardRiskCode" || cCodeName=="cardriskcode") 
    {
    	document.all('Mult').value="";
    	document.all('Amnt').value="";
    	document.all('Prem').value="";
    	queryPolOtherGrid();
    }
    if(cCodeName=="OccupationCode")
    {
			if(Field.name=="AppntOccupationCode")
			{
				
		        var sqlid25="CardContInputSql25";
				var mySql25=new SqlClass();
				mySql25.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql25.setSqlId(sqlid25);//指定使用的Sql的id
				mySql25.addSubPara(document.all('AppntOccupationCode').value);//指定传入的参数
			    var strSQL=mySql25.getString();	
				
//				var strSQL="select Occupationtype,Occupationname from LDOccupation  where OccupationCode='" + fm.AppntOccupationCode.value+"' ";
				var arrResult = easyExecSql(strSQL);
				if (arrResult != null)
				{
					fm.AppntOccupationType.value = arrResult[0][0];
					fm.AppntOccupationTypeName.value =arrResult[0][1];
					showOneCodeName('occupationtype','AppntOccupationType','AppntOccupationTypeName');
				}
			}
			else 	if(Field.name=="OccupationCode") 
			{
				
				 var sqlid26="CardContInputSql26";
				var mySql26=new SqlClass();
				mySql26.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql26.setSqlId(sqlid26);//指定使用的Sql的id
				mySql26.addSubPara(document.all('OccupationCode').value);//指定传入的参数
			    var strSQL=mySql26.getString();	
				
				//var strSQL="select Occupationtype,Occupationname from LDOccupation  where OccupationCode='" + fm.OccupationCode.value+"' ";
				var arrResult = easyExecSql(strSQL);
				if (arrResult != null)
				{
					fm.OccupationType.value = arrResult[0][0];
					fm.OccupationTypeName.value =arrResult[0][1];
					showOneCodeName('occupationtype','OccupationType','OccupationTypeName');
				}	
			}
			else {}
    	  	
    }
    //alert("aaa");
    afterCodeSelect2(cCodeName, Field);
}

/*********************************************************************
 *  初始化工作流MissionID
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function initMissionID()
{
    if (tMissionID == "null" || tSubMissionID == "null")
    {
        //alert("tMissionID="+tMissionID);
        //alert("tSubMissionID="+tSubMissionID);
        tMissionID = mSwitch.getVar('MissionID');
        tSubMissionID = mSwitch.getVar('SubMissionID');
    }
    else
    {
        mSwitch.deleteVar("MissionID");
        mSwitch.deleteVar("SubMissionID");
        mSwitch.addVar("MissionID", "", tMissionID);
        mSwitch.addVar("SubMissionID", "", tSubMissionID);
        mSwitch.updateVar("MissionID", "", tMissionID);
        mSwitch.updateVar("SubMissionID", "", tSubMissionID);
    }
}
function FillPostalAddress()
{
    if (fm.CheckPostalAddress.value == "1")
    {
        document.all('AppntPostalAddress').value = document.all('CompanyAddress').value;
        document.all('AppntZipCode').value = document.all('AppntGrpZipCode').value;
        document.all('AppntPhone').value = document.all('AppntGrpPhone').value;
        document.all('AppntFax').value = document.all('AppntGrpFax').value;

    }
    else if (fm.CheckPostalAddress.value == "2")
    {
        document.all('AppntPostalAddress').value = document.all('AppntHomeAddress').value;
        document.all('AppntZipCode').value = document.all('AppntHomeZipCode').value;
        document.all('AppntPhone').value = document.all('AppntHomePhone').value;
        document.all('AppntFax').value = document.all('AppntHomeFax').value;
    }
    else if (fm.CheckPostalAddress.value == "3")
    {
        document.all('AppntPostalAddress').value = "";
        document.all('AppntZipCode').value = "";
        document.all('AppntPhone').value = "";
        document.all('AppntFax').value = "";
    }
}
function AppntChk()
{
    var Sex = fm.AppntSex.value;
    //var i=Sex.indexOf("-");
    //Sex=Sex.substring(0,i);
	
				 var sqlid27="CardContInputSql27";
				var mySql27=new SqlClass();
				mySql27.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql27.setSqlId(sqlid27);//指定使用的Sql的id
				mySql27.addSubPara(document.all('AppntName').value);//指定传入的参数
				mySql27.addSubPara(Sex);//指定传入的参数
				mySql27.addSubPara(document.all('AppntBirthday').value);//指定传入的参数
				mySql27.addSubPara(document.all('AppntNo').value);//指定传入的参数
				
				mySql27.addSubPara(document.all('AppntIDType').value);//指定传入的参数
				mySql27.addSubPara(document.all('AppntIDNo').value);//指定传入的参数
				mySql27.addSubPara(document.all('AppntNo').value);//指定传入的参数
			    var sqlstr=mySql27.getString();	
	
//    var sqlstr = "select * from ldperson where Name='" + fm.AppntName.value
//            + "' and Sex='" + Sex + "' and Birthday='" + fm.AppntBirthday.value
//            + "' and CustomerNo<>'" + fm.AppntNo.value + "'"
//            + " union select * from ldperson where IDType = '" + fm.AppntIDType.value
//            + "' and IDType is not null and IDNo = '" + fm.AppntIDNo.value
//            + "' and IDNo is not null and CustomerNo<>'" + fm.AppntNo.value + "'" ;
			
    arrResult = easyExecSql(sqlstr, 1, 0);
    if (arrResult == null)
    {
        alert("该没有与该投保人相似的客户,无需校验");
        return false;
    } else {

        window.open("../uw/AppntChkMain.jsp?ProposalNo1=" + fm.ContNo.value + "&Flag=A&LoadFlag=" + LoadFlag, "window1",sFeatures);
    }
}

//在初始化body时自动效验投保人信息，
//判断系统中是否存在姓名、性别、出生日期相同或证件类型证件号码相同的被保人信息。
function AppntChkNew() {
    //alert("aa");
    var Sex = fm.AppntSex.value;
	
				var sqlid28="CardContInputSql28";
				var mySql28=new SqlClass();
				mySql28.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql28.setSqlId(sqlid28);//指定使用的Sql的id
				mySql28.addSubPara(document.all('AppntName').value);//指定传入的参数
				mySql28.addSubPara(Sex);//指定传入的参数
				mySql28.addSubPara(document.all('AppntBirthday').value);//指定传入的参数
				mySql28.addSubPara(document.all('AppntNo').value);//指定传入的参数
				
				mySql28.addSubPara(document.all('AppntIDType').value);//指定传入的参数
				mySql28.addSubPara(document.all('AppntIDNo').value);//指定传入的参数
				mySql28.addSubPara(document.all('AppntNo').value);//指定传入的参数
			    var sqlstr=mySql28.getString();	
	
//    var sqlstr = "select * from ldperson where Name='" + fm.AppntName.value
//            + "' and Sex='" + Sex + "' and Birthday='" + fm.AppntBirthday.value
//            + "' and CustomerNo<>'" + fm.AppntNo.value + "'"
//            + " union select * from ldperson where IDType = '" + fm.AppntIDType.value
//            + "' and IDType is not null and IDNo = '" + fm.AppntIDNo.value
//            + "' and IDNo is not null and CustomerNo<>'" + fm.AppntNo.value + "'" ;
    //alert("sqlstr="+sqlstr);
    arrResult = easyExecSql(sqlstr, 1, 0);
    if (arrResult == null)
    {
        //disabled"投保人效验"按钮   //判断是否存在重复被保人？
        fm.AppntChkButton.disabled = true;
        fm.AppntChkButton2.disabled = true;

    }
    else {
        fm.AppntChkButton.disabled = "";
        fm.AppntChkButton2.disabled = "";
    }
}

function checkidtype()
{
    //alert("haha!");
    if (fm.AppntIDType.value == "" && fm.AppntIDNo.value != "")
    {
        alert("请先选择证件类型！");
        fm.AppntIDNo.value = "";
    }
}
/*********************************************************************
 *  根据身份证号取得出生日期和性别
 *  参数  ：  身份证号
 *  返回值：  无
 *********************************************************************
 */

function getBirthdaySexByIDNo(iIdNo)
{
    if (document.all('AppntIDType').value == "0")
    {
        var tBirthday = getBirthdatByIdNo(iIdNo);
        var tSex = getSexByIDNo(iIdNo);
        if (tBirthday == "输入的身份证号位数错误" || tSex == "输入的身份证号位数错误")
        {
            alert("输入的身份证号位数错误");
            theFirstValue = "";
            theSecondValue = "";
            //document.all('AppntIDNo').focus();
            return;
        }
        else
        {
            document.all('AppntBirthday').value = tBirthday;
            document.all('AppntSex').value = tSex;
        }
    }
}
function getCompanyCode()
{
    var strsql = "";
    var tCodeData = "";
	
				var sqlid29="CardContInputSql29";
				var mySql29=new SqlClass();
				mySql29.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql29.setSqlId(sqlid29);//指定使用的Sql的id
//				mySql29.addSubPara(document.all('AppntName').value);//指定传入的参数

			    strsql=mySql28.getString();	
	
   // strsql = "select CustomerNo,GrpName from LDgrp ";
    document.all("AppntGrpNo").CodeData = tCodeData + easyQueryVer3(strsql, 1, 0, 1);
}
function haveMultiAgent() {
    //alert("aa＝＝"+document.all("multiagentflag").checked);
    if (document.all("multiagentflag").checked) {
        DivMultiAgent.style.display = "";
    }
    else {
        DivMultiAgent.style.display = "none";
    }

}

//Muline 表单发佣分配表 parm1
function queryAgentGrid(Field)
{
    //alert("Field=="+Field);
    tField = Field;
    if (document.all('ManageCom').value == "") {
        alert("请先录入管理机构信息！");
        return;
    }
    if (document.all(Field).all('MultiAgentGrid1').value == "") {
        var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom=&queryflag=1", "AgentCommonQueryMain", 'width=' + screen.availWidth + ',height=' + screen.availHeight + ',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }

    if (document.all(Field).all('MultiAgentGrid1').value != "") {
        var cAgentCode = document.all(Field).all('MultiAgentGrid1').value;
        //保单号码
//        var strSql = "select AgentCode from LAAgent where AgentCode='" + cAgentCode + "'";
        
		var sqlid82="CardContInputSql82";
		var mySql82=new SqlClass();
		mySql82.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql82.setSqlId(sqlid82);//指定使用的Sql的id
		mySql82.addSubPara(cAgentCode);//指定传入的参数
		var strSql=mySql82.getString();	
        
        var arrResult = easyExecSql(strSql);
        //alert(arrResult);
        if (arrResult == null) {
            alert("编码为:[" + document.all(Field).all('MultiAgentGrid1').value + "]的代理人不存在，请确认!");
        }
    }
}


//初始化主业务员信息
function displayMainAgent() {
    document.all("BranchAttr").value = arrResult[0][3];
    document.all("AgentName").value = arrResult[0][1];
}

function displayMultiAgent() {
    document.all('multiagentflag').checked = "true";
    haveMultiAgent();
}

//进行两次输入的校验
function confirmSecondInput1(aObject, aEvent) {
    if (aEvent == "onkeyup") {
        var theKey = window.event.keyCode;
        if (theKey == "13") {
            if (theFirstValue != "") {
                theSecondValue = aObject.value;
                if (theSecondValue == "") {
                    alert("请再次录入！");
                    aObject.value = "";
                    aObject.focus();
                    return;
                }
                if (theSecondValue == theFirstValue) {
                    aObject.value = theSecondValue;
                    return;
                }
                else {
                    alert("两次录入结果不符，请重新录入！");
                    theFirstValue = "";
                    theSecondValue = "";
                    aObject.value = "";
                    aObject.focus();
                    return;
                }
            }
            else {
                theFirstValue = aObject.value;
                if (theFirstValue == "") {
                    theSecondValue = "";
                    alert("请录入内容！");
                    return;
                }
                aObject.value = "";
                aObject.focus();
                return;
            }
        }
    }
    else if (aEvent == "onblur") {
        //alert("theFirstValue="+theFirstValue);
        if (theFirstValue != "") {
            theSecondValue = aObject.value;
            if (theSecondValue == "") {
                alert("请再次录入！");
                aObject.value = "";
                aObject.focus();
                return;
            }
            if (theSecondValue == theFirstValue) {
                aObject.value = theSecondValue;
                theSecondValue = "";
                theFirstValue = "";
                return;
            }
            else {
                alert("两次录入结果不符，请重新录入！");
                theFirstValue = "";
                theSecondValue = "";
                aObject.value = "";
                aObject.focus();
                return;
            }
        }
        else {
            theFirstValue = aObject.value;
            theSecondValue = "";
            if (theFirstValue == "") {
                //alert("aa");
                return;
            }
            aObject.value = "";
            aObject.focus();
            return;
        }
    }
}
//校验投保日期
function checkapplydate()
{
	if(trim(fm.PolAppntDate.value)==""){return ;}
    else if (fm.PolAppntDate.value.length == 8)
    {
        if (fm.PolAppntDate.value.indexOf('-') == -1)
        {
            var Year = fm.PolAppntDate.value.substring(0, 4);
            var Month = fm.PolAppntDate.value.substring(4, 6);
            var Day = fm.PolAppntDate.value.substring(6, 8);
            fm.PolAppntDate.value = Year + "-" + Month + "-" + Day;
            if (Year == "0000" || Month == "00" || Day == "00")
            {
                alert("您输入的投保日期有误!");
                fm.PolAppntDate.value = "";
                fm.PolAppntDate.focus();
                return;
            }
        }
    }
    else if(fm.PolAppntDate.value.length == 10)
    {
        var Year = fm.PolAppntDate.value.substring(0, 4);
        var Month = fm.PolAppntDate.value.substring(5, 7);
        var Day = fm.PolAppntDate.value.substring(8, 10);
        if (Year == "0000" || Month == "00" || Day == "00")
        {
            alert("您输入的投保日期有误!");
            fm.PolAppntDate.value = "";
            fm.PolAppntDate.focus();
            return;
        }
    }
	else
	{
		alert("您输入的投保日期有误!");
        fm.PolAppntDate.value = "";
        fm.PolAppntDate.focus();
        return;
	}
    //增加系统对录入的投保日期校验
    if (checkPolDate(fm.ProposalContNo.value, fm.PolAppntDate.value) == false)
    {
        fm.PolAppntDate.value = "";
        fm.PolAppntDate.focus();
        return;
    }
}

/******************************************************************************
* 添加校验，增加系统对录入的投保日期校验，校验规则为：录入的投保日期控制在录单的系统当期日期60天内
* 所需参数：ContNo---合同号（印刷号）；  PolAppntDate---投保日期
*******************************************************************************/
function checkPolDate(ContNo, PolAppntDate)
{
    var tContNo = ContNo;//合同号
    var tPolAppntDate = PolAppntDate;//投保日期
     //投保日期只能为当前日期以前
    if (calAge(tPolAppntDate) < 0)
    {
        alert("投保日期只能为当前日期以前!");
        return false;
    }
    var DayIntv=60;//录单日期与投保日期的标准间隔天数，默认为60天
    //查询录单日期与投保日期的标准间隔天数
	
					var sqlid30="CardContInputSql30";
				var mySql30=new SqlClass();
				mySql30.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql30.setSqlId(sqlid30);//指定使用的Sql的id
				mySql30.addSubPara(document.all('AppntName').value);//指定传入的参数

			   var strsql30=mySql30.getString();	
			   
	 var DayIntvArr = easyExecSql(strsql30);
   // var DayIntvArr = easyExecSql("select sysvarvalue from ldsysvar where sysvar='input_poldate_intv'");
    if (DayIntvArr != null) { DayIntv = DayIntvArr[0][0]; }
    //根据合同号获取录单日期<查询合同表，存在则取之，否则取系统当前日期>
    var tMakeDate = "";//录单的系统当期日期
    if(tContNo!=null && tContNo!="")
    {
		
				var sqlid31="CardContInputSql31";
				var mySql31=new SqlClass();
				mySql31.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql31.setSqlId(sqlid31);//指定使用的Sql的id
				mySql31.addSubPara(tContNo);//指定传入的参数
			   var makedatesql=mySql31.getString();	
		
		//var makedatesql = "select contno,prtno,makedate from lccont where prtno='" + tContNo + "'";
		var makedatearr = easyExecSql(makedatesql);
		if (makedatearr != null){ tMakeDate = makedatearr[0][2];}
    }
    if(tMakeDate=="")  //录单的系统当期日期为空，则 默认系统日期
    {    

//    	var sysdatearr = easyExecSql("select to_date(sysdate) from dual");
    	
		var sqlid83="CardContInputSql83";
		var mySql83=new SqlClass();
		mySql83.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql83.setSqlId(sqlid83);//指定使用的Sql的id
		var sqlstr=mySql83.getString();
		var sysdatearr = easyExecSql(sqlstr);
    	
    	tMakeDate = sysdatearr[0][0];//录单的系统当期日期，默认系统日期。
    }
    var Days = dateDiff(tPolAppntDate, tMakeDate, "D");//录单日期与投保日期的间隔
    if (Days > DayIntv || Days < 0)
    {
        var strInfo = "投保日期应在录单的系统当期日期 "+DayIntv+" 天内。";
        strInfo = strInfo +"\n而录单日期["+tMakeDate+"] — 投保日期["+tPolAppntDate+"]=="+Days+" 天。";
		strInfo = strInfo +"\n请重新填写投保日期。";
        alert(strInfo);
        return false;
    }
    return true;
}

//校验投保人出生日期
function checkappntbirthday() {
    if (fm.AppntBirthday.value.length == 8) {
        if (fm.AppntBirthday.value.indexOf('-') == -1) {
            var Year = fm.AppntBirthday.value.substring(0, 4);
            var Month = fm.AppntBirthday.value.substring(4, 6);
            var Day = fm.AppntBirthday.value.substring(6, 8);
            fm.AppntBirthday.value = Year + "-" + Month + "-" + Day;
            if (Year == "0000" || Month == "00" || Day == "00") {
                alert("您输入的投保人出生日期有误!");
                fm.AppntBirthday.value = "";
                return;
            }
        }
    }

    else {
        var Year = fm.AppntBirthday.value.substring(0, 4);
        var Month = fm.AppntBirthday.value.substring(5, 7);
        var Day = fm.AppntBirthday.value.substring(8, 10);
        if (Year == "0000" || Month == "00" || Day == "00") {
            alert("您输入的投保人出生日期有误!");
            fm.AppntBirthday.value = "";
            return;
        }
    }
}


//校验被保人出生日期
function checkinsuredbirthday() {
    if (fm.Birthday.value.length == 8) {
        if (fm.Birthday.value.indexOf('-') == -1) {
            var Year = fm.Birthday.value.substring(0, 4);
            var Month = fm.Birthday.value.substring(4, 6);
            var Day = fm.Birthday.value.substring(6, 8);
            fm.Birthday.value = Year + "-" + Month + "-" + Day;
            if (Year == "0000" || Month == "00" || Day == "00") {
                alert("您输入的被保人出生日期有误!");
                fm.Birthday.value = "";
                return;
            }
        }
    }

    else {
        var Year = fm.Birthday.value.substring(0, 4);
        var Month = fm.Birthday.value.substring(5, 7);
        var Day = fm.Birthday.value.substring(8, 10);
        if (Year == "0000" || Month == "00" || Day == "00") {
            alert("您输入的被保人出生日期有误!");
            fm.Birthday.value = "";
            return;
        }
    }
}
//投保人年龄<投保人被保人年龄应该为投保日期与生日之差;2005-11-18修改>
function getAge()
{
    if (fm.AppntBirthday.value == "")
    {
        return;
    }
    if (fm.AppntBirthday.value.indexOf('-') == -1)
    {
        var Year = fm.AppntBirthday.value.substring(0, 4);
        var Month = fm.AppntBirthday.value.substring(4, 6);
        var Day = fm.AppntBirthday.value.substring(6, 8);
        fm.AppntBirthday.value = Year + "-" + Month + "-" + Day;
    }
    if (calAge(fm.AppntBirthday.value) < 0)
    {
        alert("出生日期只能为当前日期以前!");
        return;
    }
    //	fm.AppntAge.value=calAge(fm.AppntBirthday.value);
    fm.AppntAge.value = calPolAppntAge(fm.AppntBirthday.value, fm.PolAppntDate.value);
    return;
}
////被保人年龄龄<被保人年龄应该为投保日期与生日之差;2005-11-18修改>
function getAge2()
{
    if (fm.Birthday.value == "")
    {
        return;
    }
    //alert("fm.Birthday.value.indexOf('-')=="+fm.Birthday.value.indexOf('-'));
    if (fm.Birthday.value.indexOf('-') == -1)
    {
        var Year = fm.Birthday.value.substring(0, 4);
        var Month = fm.Birthday.value.substring(4, 6);
        var Day = fm.Birthday.value.substring(6, 8);
        fm.Birthday.value = Year + "-" + Month + "-" + Day;
    }
    else
    {
        var Year1 = fm.Birthday.value.substring(0, 4);
        var Month1 = fm.Birthday.value.substring(5, 7);
        var Day1 = fm.Birthday.value.substring(8, 10);
        fm.Birthday.value = Year1 + "-" + Month1 + "-" + Day1;
    }
    if (calAge(fm.Birthday.value) < 0)
    {
        alert("出生日期只能为当前日期以前");
        fm.InsuredAppAge.value = "";
        return;
    }
    //	fm.InsuredAppAge.value=calAge(fm.Birthday.value);
    fm.InsuredAppAge.value = calPolAppntAge(fm.Birthday.value, fm.PolAppntDate.value);
    return;
}


//录入校验方法
//传入参数：verifyOrder校验的顺序
//业务程序调用接口，如果通过校验返回true，否则返回false
function verifyInputNB(verifyOrder)
{
    var formsNum = 0;
    //窗口中的FORM数
    var elementsNum = 0;
    //FORM中的元素数
    var passFlag = true;
    //校验通过标志
    //遍历所有FORM
    for (formsNum = 0; formsNum < window.document.forms.length; formsNum++)
    {
        //遍历FORM中的所有ELEMENT
        for (elementsNum = 0; elementsNum < window.document.forms[formsNum].elements.length; elementsNum++)
        {
            //元素校验属性verify不为NULL
            if (window.document.forms[formsNum].elements[elementsNum].verify != null && window.document.forms[formsNum].elements[elementsNum].verify != "" && window.document.forms[formsNum].elements[elementsNum].verifyorder == verifyOrder)
            {
                //进行校验verifyElement
                if (!verifyElementWrap2(window.document.forms[formsNum].elements[elementsNum].verify, window.document.forms[formsNum].elements[elementsNum].value, window.document.forms[formsNum].name + "." + window.document.forms[formsNum].elements[elementsNum].name))
                {
                    passFlag = false;
                    break;
                }
            }
        }
        if (!passFlag) break;
    }
    return passFlag;
}

//***********************************************************


function focuswrap()
{
    myonfocus(showInfo1);
}


//提交后操作,服务器数据返回后执行的操作
function afterSubmit2(FlagStr, content)
{
    //alert("here 2!");
    try {
        showInfo.close();
    } catch(e) {
    }
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    if (FlagStr == "Succ" && mWFlag == 0)
    {
        if (confirm("是否继续录入其他客户？"))
        {
            if (fm.InsuredSequencename.value == "被保险人资料")
            {
                //emptyFormElements();
                param = "122";
                fm.pagename.value = "122";
                fm.SequenceNo.value = "2";
                fm.InsuredSequencename.value = "第二被保险人资料";
                if (scantype == "scan")
                {
                    setFocus();
                }
                noneedhome();
                return false;
            }
            if (fm.InsuredSequencename.value == "第二被保险人资料")
            {
                //emptyFormElements();
                param = "123";
                fm.pagename.value = "123";
                fm.SequenceNo.value = "3";
                fm.InsuredSequencename.value = "第三被保险人资料";
                if (scantype == "scan")
                {
                    setFocus();
                }
                noneedhome();
                return false;
            }
            if (fm.InsuredSequencename.value == "第三被保险人资料")
            {
                //emptyFormElements();
                param = "124";
                fm.pagename.value = "124";
                fm.SequenceNo.value = "";
                fm.InsuredSequencename.value = "第四被保险人资料";
                if (scantype == "scan")
                {
                    setFocus();
                }
                return false;
            }
        }
    }

    if (InsuredGrid.mulLineCount > 0) {
        document.all('spanInsuredGrid0').all('InsuredGridSel').checked = 'true'
    }

}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit3(FlagStr, content)
{
    showInfo.close();
    if (FlagStr == "Fail")
    {
        var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        content = "操作成功！";
        var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        top.close();
       	top.opener.easyQueryClick();
        //window.location = "./CardContInput.jsp";
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

//问题件录入
function QuestInput()
{

    cContNo = fm.ContNo.value;
    //保单号码
    if (LoadFlag == "2" || LoadFlag == "4" || LoadFlag == "13")
    {
        if (mSwitch.getVar("ProposalGrpContNo") == "")
        {
            alert("尚无集体合同投保单号，请先保存!");
        }
        else
        {
            window.open("./GrpQuestInputMain.jsp?GrpContNo=" + mSwitch.getVar("ProposalGrpContNo") + "&Flag=" + LoadFlag,"",sFeatures);
        }
    }
    else
    {
        if (cContNo == "")
        {
            alert("尚无合同投保单号，请先保存!");
        }
        else
        {
            window.open("../uw/QuestInputMain.jsp?ContNo=" + cContNo + "&Flag=" + LoadFlag, "window1",sFeatures);
        }
    }
}
//问题件查询
function QuestQuery()
{
    cContNo = document.all("ContNo").value;
    //保单号码
    if (LoadFlag == "2" || LoadFlag == "4" || LoadFlag == "13")
    {
        if (mSwitch.getVar("ProposalGrpContNo") == "" || mSwitch.getVar("ProposalGrpContNo") == null)
        {
            alert("请先选择一个团体主险投保单!");
            return;
        }
        else
        {
            window.open("./GrpQuestQueryMain.jsp?GrpContNo=" + mSwitch.getVar("ProposalGrpContNo") + "&Flag=" + LoadFlag,"",sFeatures);
        }
    }
    else
    {
        if (cContNo == "")
        {
            alert("尚无合同投保单号，请先保存!");
        }
        else
        {
            window.open("../uw/QuestQueryMain.jsp?ContNo=" + cContNo + "&Flag=" + LoadFlag, "window1",sFeatures);
        }
    }
}


/*********************************************************************
 *  进入险种信息录入
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function intoRiskInfo()
{

    //把合同信息和被保人信息放入内存
    mSwitch = parent.VD.gVSwitch;
    //桢容错
    putCont();
    //注意该函数在ContInput.js中

    if (fm.InsuredNo.value == "" || fm.ContNo.value == "" || InsuredGrid.mulLineCount == "0")
    {
        alert("请先添加，选择被保人");
        return false;
    }
    //mSwitch =parent.VD.gVSwitch;
    delInsuredVar();
    addInsuredVar();

    try {
        mSwitch.addVar('SelPonNo', '', fm.SelPolNo.value);
    } catch(ex) {
    } //选择险种单进入险种界面带出已保存的信息

    if ((LoadFlag == '5' || LoadFlag == '4' || LoadFlag == '6' || LoadFlag == '16') && (mSwitch.getVar("PolNo") == null || mSwitch.getVar("PolNo") == ""))
    {
        alert("请先选择被保险人险种信息！");
        return;
    }
    try {
        mSwitch.addVar('SelPolNo', '', fm.SelPolNo.value);
    } catch(ex) {
    }
    try {
        mSwitch.deleteVar('ContNo');
    } catch(ex) {
    }
    try {
        mSwitch.addVar('ContNo', '', fm.ContNo.value);
    } catch(ex) {
    }
    try {
        mSwitch.updateVar('ContNo', '', fm.ContNo.value);
    } catch(ex) {
    }
    try {
        mSwitch.deleteVar('mainRiskPolNo');
    } catch(ex) {
    }
    try {
        mSwitch.addVar('CValiDate', '', document.all('PolAppntDate').value);
    } catch(ex) {
    }
    //add by yaory
    // alert(ActivityID);
    //  alert(NoType);
    parent.fraInterface.window.location = "./ProposalInput.jsp?LoadFlag=" + LoadFlag + "&ContType=" + ContType + "&scantype=" + scantype + "&MissionID=" + MissionID + "&SubMissionID=" + SubMissionID + "&BQFlag=" + BQFlag + "&EdorType=" + EdorType + "&ActivityID=" + ActivityID + "&NoType=" + NoType + "&hh=1&checktype=" + checktype + "&ProposalContNo=" + fm.ProposalContNo.value + "&ScanFlag=" + ScanFlag;
}

/*********************************************************************
 *  删除缓存中被保险人信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function delInsuredVar()
{
    try {
        mSwitch.deleteVar('ContNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('InsuredNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PrtNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('GrpContNo');
    } catch(ex) {
    }
    ;
    //   try{mSwitch.deleteVar('AppntNo');}catch(ex){};
    //   try{mSwitch.deleteVar('ManageCom');}catch(ex){};
    try {
        mSwitch.deleteVar('ExecuteCom');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('FamilyType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('RelationToMainInsure');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('RelationToAppnt');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AddressNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('SequenceNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Name');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Sex');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Birthday');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('IDType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('IDNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('RgtAddress');
    } catch(ex) {
    }
    ;
    //    try{mSwitch.deleteVar('Marriage');}catch(ex){};
    //    try{mSwitch.deleteVar('MarriageDate');}catch(ex){};
    try {
        mSwitch.deleteVar('Health');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Stature');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Avoirdupois');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Degree');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('CreditGrade');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('BankCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('BankAccNo');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('AccName');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('JoinCompanyDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('StartWorkDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Position');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Salary');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('OccupationType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('OccupationCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('WorkType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('PluralityType');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('SmokeFlag');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ContPlanCode');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('Operator');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('MakeDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('MakeTime');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ModifyDate');
    } catch(ex) {
    }
    ;
    try {
        mSwitch.deleteVar('ModifyTime');
    } catch(ex) {
    }
    ;
}

/*********************************************************************
 *  将被保险人信息加入到缓存中
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addInsuredVar()
{
    try {
        mSwitch.addVar('ContNo', '', fm.ContNo.value);
    } catch(ex) {
    }
    ;
    //alert("ContNo:"+fm.ContNo.value);
    try {
        mSwitch.addVar('InsuredNo', '', fm.InsuredNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PrtNo', '', fm.PrtNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('GrpContNo', '', fm.GrpContNo.value);
    } catch(ex) {
    }
    ;
    //   try{mSwitch.addVar('AppntNo','',fm.AppntNo.value);}catch(ex){};
    //   try{mSwitch.addVar('ManageCom','',fm.ManageCom.value);}catch(ex){};
    try {
        mSwitch.addVar('ExecuteCom', '', fm.ExecuteCom.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('FamilyType', '', fm.FamilyType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('RelationToMainInsure', '', fm.RelationToMainInsure.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('RelationToAppnt', '', fm.RelationToAppnt.value);
    } catch(ex) {
    }
    ;

    try {
        mSwitch.addVar('AddressNo', '', fm.AppntAddressNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('SequenceNo', '', fm.SequenceNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Name', '', fm.Name.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Sex', '', fm.Sex.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Birthday', '', fm.Birthday.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('IDType', '', fm.IDType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('IDNo', '', fm.IDNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('RgtAddress', '', fm.RgtAddress.value);
    } catch(ex) {
    }
    ;
    //    try{mSwitch.addVar('Marriage','',fm.Marriage.value);}catch(ex){};
    //    try{mSwitch.addVar('MarriageDate','',fm.MarriageDate.value);}catch(ex){};
    try {
        mSwitch.addVar('Health', '', fm.Health.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Stature', '', fm.Stature.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Avoirdupois', '', fm.Avoirdupois.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Degree', '', fm.Degree.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('CreditGrade', '', fm.CreditGrade.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('BankCode', '', fm.BankCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('BankAccNo', '', fm.BankAccNo.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('AccName', '', fm.AccName.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('JoinCompanyDate', '', fm.JoinCompanyDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('StartWorkDate', '', fm.StartWorkDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Position', '', fm.Position.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Salary', '', fm.Salary.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('OccupationType', '', fm.OccupationType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('OccupationCode', '', fm.OccupationCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('WorkType', '', fm.WorkType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PluralityType', '', fm.PluralityType.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('SmokeFlag', '', fm.SmokeFlag.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ContPlanCode', '', fm.ContPlanCode.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('Operator', '', fm.Operator.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('MakeDate', '', fm.MakeDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('MakeTime', '', fm.MakeTime.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ModifyDate', '', fm.ModifyDate.value);
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('ModifyTime', '', fm.ModifyTime.value);
    } catch(ex) {
    }
    ;
    //    alert("=fm.PolAppntDate.value="+fm.PolAppntDate.value);
    //    try{mSwitch.addVar('CValidate','',fm.PolAppntDate.value);}catch(ex){};

}

/*********************************************************************
 *  添加被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addRecord()
{
    if (fm.AppntName.value == fm.Name.value && fm.AppntIDType.value == fm.IDType.value && fm.IDNo.value == fm.AppntIDNo.value && fm.Sex.value == fm.AppntSex.value && fm.Birthday.value == fm.AppntBirthday.value)
    {
        alert("被保险人与投保人相同！");
        fm.SamePersonFlag.checked = true;
        isSamePerson();

    }
    //判断是否已经添加过投保人，没有的话不允许添加被保人
    if (!checkAppnt()) {
        alert("请先添加合同信息及投保人信息！");
        fm.AppntName.focus();
        return;
    }

    //2005.03.18 chenhq 对此进行修改
    if (LoadFlag == 1) {

        if (fm.SamePersonFlag.checked == true && fm.RelationToAppnt.value != "00")
        {
            alert("与投保人关系只能选择本人！");
            fm.RelationToAppnt.value = "00";
            fm.RelationToAppntName.value = "本人";
            return;
        }
        var tPrtNo = document.all("PrtNo").value;

				var sqlid32="CardContInputSql32";
				var mySql32=new SqlClass();
				mySql32.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql32.setSqlId(sqlid32);//指定使用的Sql的id
				mySql32.addSubPara(tPrtNo);//指定传入的参数
			   var sqlstr=mySql32.getString();	


//        var sqlstr = "select SequenceNo from LCInsured where PrtNo='" + tPrtNo + "'";

        arrResult = easyExecSql(sqlstr, 1, 0);

        if (arrResult != null) {
            for (var sequencenocout = 0; sequencenocout < arrResult.length; sequencenocout++)
            {
                if (fm.SequenceNo.value == arrResult[sequencenocout][0]) {
                    alert("已经存在该客户内部号");
                    fm.SequenceNo.focus();
                    return false;
                }
            }
        }
    }

    //2005.03.18 chenhq 对此进行修改
    if (LoadFlag == 3) {

        var tPrtNo = document.all("PrtNo").value;

				var sqlid33="CardContInputSql33";
				var mySql33=new SqlClass();
				mySql33.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql33.setSqlId(sqlid33);//指定使用的Sql的id
				mySql33.addSubPara(tPrtNo);//指定传入的参数
			   var sqlstr=mySql33.getString();	


//        var sqlstr = "select SequenceNo from LCInsured where PrtNo='" + tPrtNo + "'";

        arrResult = easyExecSql(sqlstr, 1, 0);

        if (arrResult != null) {
            for (var sequencenocout = 0; sequencenocout < arrResult.length; sequencenocout++)
            {
                if (fm.SequenceNo.value == arrResult[sequencenocout][0]) {
                    alert("已经存在该客户内部号");
                    fm.SequenceNo.focus();
                    return false;
                }
            }
        }
    }


    //2005.03.18 chenhq 对此进行修改
    if (LoadFlag == 5) {

        var tPrtNo = document.all("PrtNo").value;


				var sqlid33="CardContInputSql33";
				var mySql33=new SqlClass();
				mySql33.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql33.setSqlId(sqlid33);//指定使用的Sql的id
				mySql33.addSubPara(tPrtNo);//指定传入的参数
			   var sqlstr=mySql33.getString();	

//        var sqlstr = "select SequenceNo from LCInsured where PrtNo='" + tPrtNo + "'";

        arrResult = easyExecSql(sqlstr, 1, 0);

        if (arrResult != null) {
            for (var sequencenocout = 0; sequencenocout < arrResult.length; sequencenocout++)
            {
                if (fm.SequenceNo.value == arrResult[sequencenocout][0]) {
                    alert("已经存在该客户内部号");
                    fm.SequenceNo.focus();
                    return false;
                }
            }
        }
    }


    //2005.03.18 chenhq 对此进行修改
    if (LoadFlag == 6) {

        var tPrtNo = document.all("PrtNo").value;

				var sqlid34="CardContInputSql34";
				var mySql34=new SqlClass();
				mySql34.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql34.setSqlId(sqlid34);//指定使用的Sql的id
				mySql34.addSubPara(tPrtNo);//指定传入的参数
			   var sqlstr=mySql34.getString();	


//        var sqlstr = "select SequenceNo from LCInsured where PrtNo='" + tPrtNo + "'";

        arrResult = easyExecSql(sqlstr, 1, 0);

        if (arrResult != null) {
            for (var sequencenocout = 0; sequencenocout < arrResult.length; sequencenocout++)
            {
                if (fm.SequenceNo.value == arrResult[sequencenocout][0]) {
                    alert("已经存在该客户内部号");
                    fm.SequenceNo.focus();
                    return false;
                }
            }
        }
    }


    /***************************************************
      if(LoadFlag==1)
      {
    //    if(fm.Marriage.value=="")
        {
          alert("请填写婚姻状况！");
            return false;
        }
        if(fm.RelationToMainInsured.value=="")
        {
          alert("请填写与主被保险人关系！");
            return false;
        }
        if(fm.RelationToAppnt.value=="")
        {
          alert("请填写与投保人关系！！");
            return false;
        }
      }

    *******************************************************/

    if (document.all('PolTypeFlag').value == 0)
    {
        if (verifyInputNB('2') == false) return false;
    }

    if (document.all('IDType').value == "0")
    {
        var strChkIdNo = chkIdNo(trim(document.all('IDNo').value), trim(document.all('Birthday').value), trim(document.all('Sex').value));
        if (strChkIdNo != "")
        {
            alert(strChkIdNo);
            return false;
        }
    }

    if (!checkself()) return false;

    if (!checkrelation()) return false;

    if (ImpartGrid.checkValue2(ImpartGrid.name, ImpartGrid) == false) return false;

    //if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)return false;
    ImpartGrid.delBlankLine();
    //  ImpartDetailGrid.delBlankLine();
    //alert("fm.AddressNo.value=="+fm.InsuredAddressNo.value);
    if (fm.InsuredNo.value == '' && fm.InsuredAddressNo.value != '')
    {
        alert("被保险人客户号为空，不能有地址号码");
        return false;
    }

    //hanlin 20050504
    fm.action = "ContInsuredSave.jsp";
    //end hanlin
    document.all('ContType').value = ContType;
    document.all('BQFlag').value = BQFlag;
    document.all('fmAction').value = "INSERT||CONTINSURED";
    fm.submit();
}

/*********************************************************************
 *  修改被选中的被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function modifyRecord()
{
    /*
                var tPrtNo=document.all("PrtNo").value;
          arrResult=easyExecSql("select SequenceNo from LCInsured where PrtNo='"+tPrtNo+"'",1,0);
          for(var sequencenocout=0; sequencenocout<arrResult[0].length;sequencenocout++ )
          {
          if(fm.SequenceNo.value==arrResult[0][sequencenocout]){
              alert("已经存在该客户内部号");
              fm.SequenceNo.focus();
              return false;
              }
            }
    */
    //alert("here!");
    if (fm.SamePersonFlag.checked == true && fm.RelationToAppnt.value != "00")
    {
        alert("与投保人关系只能选择本人！");
        fm.RelationToAppnt.value = "00";
        fm.RelationToAppntName.value = "本人";
        return;
    }

    if (document.all('PolTypeFlag').value == 0)
    {
        if (verifyInputNB('2') == false) return false;
    }
    if (!checkself())
        return false;
    if (fm.Name.value == '')
    {
        alert("请选中需要修改的客户！")
        return false;
    }
    //alert("SelNo:"+InsuredGrid.getSelNo());
    if (InsuredGrid.mulLineCount == 0)
    {
        alert("该被保险人还没有保存，无法进行修改！");
        return false;
    }
    if (fm.InsuredNo.value == '' && fm.InsuredAddressNo.value != '')
    {
        alert("被保险人客户号为空，不能有地址号码");
        return false;
    }
    if (ImpartGrid.checkValue2(ImpartGrid.name, ImpartGrid) == false)
    {
        return false;
    }
    // if(ImpartDetailGrid.checkValue2(ImpartDetailGrid.name,ImpartDetailGrid)== false)
    // {
    //    return false;
    //  }
    ImpartGrid.delBlankLine();
    //ImpartDetailGrid.delBlankLine();

    document.all('ContType').value = ContType;
    document.all('fmAction').value = "UPDATE||CONTINSURED";
    fm.action = "ContInsuredSave.jsp";
    fm.submit();
}
/*********************************************************************
 *  删除被选中的被保险人
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function deleteRecord()
{
    if (fm.InsuredNo.value == '')
    {
        alert("请选中需要删除的客户！")
        return false;
    }
    if (InsuredGrid.mulLineCount == 0)
    {
        alert("该被保险人还没有保存，无法进行修改！");
        return false;
    }
    if (fm.InsuredNo.value == '' && fm.InsuredAddressNo.value != '')
    {
        alert("被保险人客户号为空，不能有地址号码");
        return false;
    }
	
				var sqlid35="CardContInputSql35";
				var mySql35=new SqlClass();
				mySql35.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql35.setSqlId(sqlid35);//指定使用的Sql的id
				mySql35.addSubPara(fm.ContNo.value);//指定传入的参数
			   var sqlstr=mySql35.getString();	
	
//    var sqlstr = "select polno from  lcpol where contNo='" + fm.ContNo.value + "'";
    arrResult = easyExecSql(sqlstr, 1, 0);

    if (arrResult != null)
    {
        alert("请先删除被保人产品信息");
        return false;
    }
    document.all('ContType').value = ContType;
    document.all('fmAction').value = "DELETE||CONTINSURED";
    fm.action = "ContInsuredSave.jsp";
    fm.submit();
}
/*********************************************************************
 *  返回上一页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function returnparent()
{
    //alert("LoadFlag=="+LoadFlag);
    var backstr = document.all("ContNo").value;

    mSwitch.addVar("PolNo", "", backstr);
    mSwitch.updateVar("PolNo", "", backstr);
    try
    {
        mSwitch.deleteVar('ContNo');
    }
    catch(ex) {
    }
    ;
    if (LoadFlag == "1" || LoadFlag == "3")
    {
        //alert(document.all("PrtNo").value);
        location.href = "../app/ContInput.jsp?LoadFlag=" + LoadFlag + "&prtNo=" + document.all("PrtNo").value;
    }
    if (LoadFlag == "5" || LoadFlag == "25")
    {
        //alert(document.all("PrtNo").value);
        location.href = "../app/ContInput.jsp?LoadFlag=" + LoadFlag + "&prtNo=" + document.all("PrtNo").value;
    }

    if (LoadFlag == "2")
    {
        location.href = "../app/ContGrpInsuredInput.jsp?LoadFlag=" + LoadFlag + "&polNo=" + document.all("GrpContNo").value + "&scantype=" + scantype;
    }
    else if (LoadFlag == "6")
    {
        location.href = "ContInput.jsp?LoadFlag=" + LoadFlag + "&ContNo=" + backstr + "&prtNo=" + document.all("PrtNo").value;
        return;
    }
    else if (LoadFlag == "7")
    {
        location.href = "../bq/GEdorTypeNI.jsp?BQFlag=" + BQFlag;
        return;
    }
    else if (LoadFlag == "4" || LoadFlag == "16" || LoadFlag == "13" || LoadFlag == "14" || LoadFlag == "23")
    {
        if (Auditing == "1")
        {
            top.close();
        }
        else
        {
            mSwitch.deleteVar("PolNo");
            parent.fraInterface.window.location = "../app/ContGrpInsuredInput.jsp?LoadFlag=" + LoadFlag + "&scantype=" + scantype;
        }
    }
    else if (LoadFlag == "99")
    {
        location.href = "ContPolInput.jsp?LoadFlag=" + LoadFlag + "&scantype=" + scantype;
        return;
    }
    /*    else
        {
            location.href="ContInput.jsp?LoadFlag="+ LoadFlag;
        }  针对	各种情况的不同暂不支持else方式
    */
}
/*********************************************************************
 *  进入险种计划界面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function grpRiskPlanInfo()
{
    var newWindow = window.open("../app/GroupRiskPlan.jsp","",sFeatures);
}
/*********************************************************************
 *  代码选择后触发时间
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect2(cCodeName, Field)
{
    try
    {
        //如果是无名单
        if (cCodeName == "PolTypeFlag")
        {
            if (Field.value != '0')
            {
                document.all('InsuredPeoples').readOnly = false;
                document.all('InsuredAppAge').readOnly = false;
            }
            else
            {
                document.all('InsuredPeoples').readOnly = true;
                document.all('InsuredAppAge').readOnly = true;
            }
        }
        if (cCodeName == "ImpartCode")
        {

        }
        if (cCodeName == "SequenceNo")
        {
            if (Field.value == "1" && fm.SamePersonFlag.checked == false)
            {
                //emptyInsured();
                param = "121";
                fm.pagename.value = "121";
                fm.InsuredSequencename.value = "被保险人资料";
                fm.RelationToMainInsured.value = "00";
            }
            if (Field.value == "2" && fm.SamePersonFlag.checked == false)
            {
                if (InsuredGrid.mulLineCount == 0)
                {
                    alert("请先添加第一被保人");
                    fm.SequenceNo.value = "1";
                    fm.SequenceNoName.value = "被保险人";
                    return false;
                }
                //emptyInsured();
                noneedhome();
                param = "122";
                fm.pagename.value = "122";
                fm.InsuredSequencename.value = "第二被保险人资料";
            }
            if (Field.value == "3" && fm.SamePersonFlag.checked == false)
            {
                if (InsuredGrid.mulLineCount == 0)
                {
                    alert("请先添加第一被保人");
                    Field.value = "1";
                    fm.SequenceNo.value = "1";
                    fm.SequenceNoName.value = "被保险人";

                    return false;
                }
                if (InsuredGrid.mulLineCount == 1)
                {
                    alert("请先添加第二被保人");
                    Field.value = "1";
                    fm.SequenceNo.value = "2";
                    fm.SequenceNoName.value = "第二被保险人";
                    return false;
                }
                //emptyInsured();
                noneedhome();
                param = "123";
                fm.pagename.value = "123";
                fm.InsuredSequencename.value = "第三被保险人资料";
            }
            if (scantype == "scan")
            {
                setFocus();
            }
        }
        if (cCodeName == "CheckPostalAddress")
        {
            if (fm.CheckPostalAddress.value == "1")
            {
                document.all('PostalAddress').value = document.all('GrpAddress').value;
                document.all('ZipCode').value = document.all('GrpZipCode').value;
                document.all('InsuredPhone').value = document.all('GrpPhone').value;

            }
            else if (fm.CheckPostalAddress.value == "2")
            {
                document.all('PostalAddress').value = document.all('HomeAddress').value;
                document.all('ZipCode').value = document.all('HomeZipCode').value;
                document.all('InsuredPhone').value = document.all('HomePhone').value;
            }
            else if (fm.CheckPostalAddress.value == "3")
            {
                document.all('PostalAddress').value = "";
                document.all('ZipCode').value = "";
                document.all('InsuredPhone').value = "";
            }
        }

    }
    catch(ex) {
    }

}
/*********************************************************************
 *  显示家庭单下被保险人的信息
 *  返回值：  无
 *********************************************************************
 */
function getInsuredInfo()
{
    //alert("hl in getInsuredInfo ContNo="+document.all("ContNo").value);
    //alert("hl in getInsuredInfo GrpName="+document.all("GrpName").value);
    var ContNo = document.all("ContNo").value;
    if (ContNo != null && ContNo != "")
    {
				var sqlid36="CardContInputSql36";
				var mySql36=new SqlClass();
				mySql36.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql36.setSqlId(sqlid36);//指定使用的Sql的id
				mySql36.addSubPara(ContNo);//指定传入的参数
			   var strSQL=mySql36.getString();	
		
		
//        var strSQL = "select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='" + ContNo + "'";

        //alert("strSQL=="+strSQL);

        turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
        //alert("turnPage==="+turnPage.strQueryResult);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = InsuredGrid;
        //保存SQL语句
        turnPage.strQuerySql = strSQL;
        //设置查询起始位置
        turnPage.pageIndex = 0;
        //在查询结果数组中取出符合页面显示大小设置的数组
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

        //默认将焦点指向第一被保人
        //alert("fm.InsuredGrid=="+document.all('spanInsuredGrid0').all('InsuredGridSel').checked);
        document.all('spanInsuredGrid0').all('InsuredGridSel').checked = true;
        getInsuredDetail("spanInsuredGrid0", "");
        showInsuredCodeName();

    }
}

/*********************************************************************
 *  获得个单合同的被保人信息
 *  返回值：  无
 *********************************************************************
 */
function getProposalInsuredInfo()
{
    //alert("ContNo=="+document.all("ContNo").value);
    var tContNo = document.all("ContNo").value;
	
					var sqlid37="CardContInputSql37";
				var mySql37=new SqlClass();
				mySql37.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql37.setSqlId(sqlid37);//指定使用的Sql的id
				mySql37.addSubPara(ContNo);//指定传入的参数
			   var strSQL37=mySql37.getString();	
	
	arrResult = easyExecSql(strSQL37, 1, 0);
//    arrResult = easyExecSql("select * from LCInsured where ContNo='" + tContNo + "'", 1, 0);
    if (arrResult == null || InsuredGrid.mulLineCount > 1)
    {
        return;
    }
    else
    {
        if (InsuredGrid.mulLineCount = 1) {
            DisplayInsured();
            //该合同下的被投保人信息
            var tCustomerNo = arrResult[0][2];
            // 得到被保人客户号
            var tAddressNo = arrResult[0][10];
            // 得到被保人地址号
			
				var sqlid38="CardContInputSql38";
				var mySql38=new SqlClass();
				mySql38.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql38.setSqlId(sqlid38);//指定使用的Sql的id
				mySql38.addSubPara(tCustomerNo);//指定传入的参数
			   var strSQL38=mySql38.getString();	
			   
			arrResult = easyExecSql(strSQL38, 1, 0);
            //arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='" + tCustomerNo + "'", 1, 0);
        }
        if (arrResult == null)
        {
            //alert("未得到用户信息");
            //return;
        }
        else
        {
            //displayAppnt();       //显示被保人详细内容
            emptyUndefined();
            fm.InsuredAddressNo.value = tAddressNo;
            getdetailaddress2();
            //显示被保人地址详细内容
        }
    }
    getInsuredPolInfo();
    getImpartInfo();
    getImpartDetailInfo();
}

/*********************************************************************
 *  MulLine的RadioBox点击事件，获得被保人详细信息，填入被保人信息表
 *  返回值：  无
 *********************************************************************
 */
function getInsuredDetail(parm1, parm2)
{
    //alert("---parm1=="+parm1+"---parm2=="+parm2);

    var InsuredNo = document.all(parm1).all('InsuredGrid1').value;

    var ContNo = fm.ContNo.value;

    //被保人详细信息
	
				var sqlid39="CardContInputSql39";
				var mySql39=new SqlClass();
				mySql39.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql39.setSqlId(sqlid39);//指定使用的Sql的id
				mySql39.addSubPara(InsuredNo);//指定传入的参数
			   var strSQL=mySql39.getString();	
	
//    var strSQL = "select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo='" + InsuredNo + "'";

    arrResult = easyExecSql(strSQL);

    if (arrResult != null)
    {
        //displayAppnt();
        displayInsuredInfo();
    }

				var sqlid40="CardContInputSql40";
				var mySql40=new SqlClass();
				mySql40.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql40.setSqlId(sqlid40);//指定使用的Sql的id
				mySql40.addSubPara(ContNo);//指定传入的参数
				mySql40.addSubPara(InsuredNo);//指定传入的参数
			    strSQL=mySql40.getString();	


//    strSQL = "select * from LCInsured where ContNo = '" + ContNo + "' and InsuredNo='" + InsuredNo + "'";

    arrResult = easyExecSql(strSQL);

    if (arrResult != null)
    {
        DisplayInsured();
    }

    var tAddressNo = arrResult[0][10];
    // 得到被保人地址号
    //alert("arrResult[0][10]=="+arrResult[0][10]);
    fm.InsuredAddressNo.value = tAddressNo;

    //显示被保人地址信息
    getdetailaddress2();

    getInsuredPolInfo();

    getImpartInfo();

    //getImpartDetailInfo();
    getAge2();
    //在录单过程中不需要进行客户合并，故注释掉　hl 20050505
    //如果是复核状态，则进行重复客户校验
    if (LoadFlag == "5") {
        InsuredChkNew();
        return;
    }
}
/*********************************************************************
 *  把查询返回的客户数据显示到被保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayCustomer2()
{
    try {
        document.all('Nationality').value = arrResult[0][8];
    } catch(ex) {
    }
    ;

}
/*********************************************************************
 *  把查询返回的客户地址数据显示到被保人部分
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayAddress2()
{
    try {
        document.all('PostalAddress').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('ZipCode').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredPhone').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('Mobile').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    try {
        document.all('EMail').value = arrResult[0][16];
    } catch(ex) {
    }
    ;
    //try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){};
    try {
        document.all('GrpPhone').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('CompanyAddress').value = arrResult[0][10];
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpZipCode').value = arrResult[0][11];
    } catch(ex) {
    }
    ;
}
/*********************************************************************
 *  显示被保人详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function DisplayInsured()
{
    try {
        document.all('GrpContNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('ContNo').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredNo').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('PrtNo').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNo').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('ManageCom').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('ExecuteCom').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('FamilyID').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('RelationToMainInsured').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('RelationToAppnt').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredAddressNo').value = arrResult[0][10];
    } catch(ex) {
    }
    ;
    try {
        document.all('SequenceNo').value = arrResult[0][11];
    } catch(ex) {
    }
    ;
    try {
        document.all('Name').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('Sex').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('Birthday').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    try {
        document.all('IDType').value = arrResult[0][15];
    } catch(ex) {
    }
    ;
    try {
        document.all('IDNo').value = arrResult[0][16];
    } catch(ex) {
    }
    ;
    try {
        document.all('NativePlace').value = arrResult[0][17];
    } catch(ex) {
    }
    ;
    try {
        document.all('Nationality').value = arrResult[0][18];
    } catch(ex) {
    }
    ;
    try {
        document.all('RgtAddress').value = arrResult[0][19];
    } catch(ex) {
    }
    ;
    //    try{document.all('Marriage').value=arrResult[0][20];}catch(ex){};
    //    try{document.all('MarriageDate').value=arrResult[0][21];}catch(ex){};
    try {
        document.all('Health').value = arrResult[0][22];
    } catch(ex) {
    }
    ;
    try {
        document.all('Stature').value = arrResult[0][23];
    } catch(ex) {
    }
    ;
    try {
        document.all('Avoirdupois').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    try {
        document.all('Degree').value = arrResult[0][25];
    } catch(ex) {
    }
    ;
    try {
        document.all('CreditGrade').value = arrResult[0][26];
    } catch(ex) {
    }
    ;
    try {
        document.all('BankCode').value = arrResult[0][27];
    } catch(ex) {
    }
    ;
    try {
        document.all('BankAccNo').value = arrResult[0][28];
    } catch(ex) {
    }
    ;
    try {
        document.all('AccName').value = arrResult[0][29];
    } catch(ex) {
    }
    ;
    try {
        document.all('JoinCompanyDate').value = arrResult[0][30];
    } catch(ex) {
    }
    ;
    try {
        document.all('StartWorkDate').value = arrResult[0][31];
    } catch(ex) {
    }
    ;
    try {
        document.all('Position').value = arrResult[0][32];
    } catch(ex) {
    }
    ;
    try {
        document.all('Salary').value = arrResult[0][33];
    } catch(ex) {
    }
    ;
    try {
        document.all('OccupationType').value = arrResult[0][34];
    } catch(ex) {
    }
    ;
    try {
        document.all('OccupationCode').value = arrResult[0][35];
    } catch(ex) {
    }
    ;
    try {
        document.all('WorkType').value = arrResult[0][36];
    } catch(ex) {
    }
    ;
    try {
        document.all('PluralityType').value = arrResult[0][37];
    } catch(ex) {
    }
    ;
    try {
        document.all('SmokeFlag').value = arrResult[0][38];
    } catch(ex) {
    }
    ;
    try {
        document.all('ContPlanCode').value = arrResult[0][39];
    } catch(ex) {
    }
    ;
    try {
        document.all('Operator').value = arrResult[0][40];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeDate').value = arrResult[0][42];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeTime').value = arrResult[0][43];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyDate').value = arrResult[0][44];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyTime').value = arrResult[0][45];
    } catch(ex) {
    }
    ;
    try {
        document.all('LicenseType').value = arrResult[0][53];
    } catch(ex) {
    }
    ;
}
function displayissameperson()
{
    //alert("here!");
    //alert("document.all( 'AppntName').value="+document.all( "AppntName").value);
　
    try {
        document.all('InsuredNo').value = document.all("AppntNo").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Name').value = document.all("AppntName").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Sex').value = document.all("AppntSex").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Birthday').value = document.all("AppntBirthday").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('IDType').value = document.all("AppntIDType").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('IDNo').value = document.all("AppntIDNo").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Password').value = document.all("AppntPassword").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('NativePlace').value = document.all("AppntNativePlace").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Nationality').value = document.all("AppntNationality").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('InsuredAddressNo').value = document.all("AppntAddressNo").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('RgtAddress').value = document.all("AppntRgtAddress").value;
    } catch(ex) {
    }
    ;
    //　try{document.all('Marriage').value= document.all( "AppntMarriage" ).value;          }catch(ex){};
    //　try{document.all('MarriageDate').value= document.all( "AppntMarriageDate" ).value;  }catch(ex){};
　
    try {
        document.all('Health').value = document.all("AppntHealth").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Stature').value = document.all("AppntStature").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Avoirdupois').value = document.all("AppntAvoirdupois").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Degree').value = document.all("AppntDegree").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('CreditGrade').value = document.all("AppntDegreeCreditGrade").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('OthIDType').value = document.all("AppntOthIDType").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('OthIDNo').value = document.all("AppntOthIDNo").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('ICNo').value = document.all("AppntICNo").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('GrpNo').value = document.all("AppntGrpNo").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('JoinCompanyDate').value = document.all("JoinCompanyDate").value;
        if (document.all('JoinCompanyDate').value == "false") {
            document.all('JoinCompanyDate').value = "";
        }
    } catch(ex) {
    }
    ;
　
    try {
        document.all('StartWorkDate').value = document.all("AppntStartWorkDate").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Position').value = document.all("AppntPosition").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Position').value = document.all("Position").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Salary').value = document.all("AppntSalary").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('OccupationType').value = document.all("AppntOccupationType").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('OccupationCode').value = document.all("AppntOccupationCode").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('WorkType').value = document.all("AppntWorkType").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('PluralityType').value = document.all("AppntPluralityType").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('DeathDate').value = document.all("AppntDeathDate").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('SmokeFlag').value = document.all("AppntSmokeFlag").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('BlacklistFlag').value = document.all("AppntBlacklistFlag").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Proterty').value = document.all("AppntProterty").value;
    } catch(ex) {
    }
    ;
　//try{document.all('Remark').value= document.all("AppntRemark").value;}catch(ex){};
　
    try {
        document.all('State').value = document.all("AppntState").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Operator').value = document.all("AppntOperator").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('MakeDate').value = document.all("AppntMakeDate").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('MakeTime').value = document.all("AppntMakeTime").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('ModifyDate').value = document.all("AppntModifyDate").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('ModifyTime').value = document.all("AppntModifyTime").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('PostalAddress').value = document.all("AppntPostalAddress").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('ZipCode').value = document.all("AppntZipCode").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('InsuredPhone').value = document.all("AppntPhone").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Fax').value = document.all("AppntFax").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('Mobile').value = document.all("AppntMobile").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('EMail').value = document.all("AppntEMail").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('GrpName').value = document.all("AppntGrpName").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('GrpPhone').value = document.all("AppntGrpPhone").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('GrpAddress').value = document.all("CompanyAddress").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('GrpZipCode').value = document.all("AppntGrpZipCode").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('GrpFax').value = document.all("AppntGrpFax").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('HomeAddress').value = document.all("AppntHomeAddress").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('HomePhone').value = document.all("AppntHomePhone").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('HomeZipCode').value = document.all("AppntHomeZipCode").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('HomeFax').value = document.all("AppntHomeFax").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('RelationToAppnt').value = "00";
    } catch(ex) {
    }
    ;
　
    try {
        document.all('InsuredProvince').value = document.all("AppntProvince").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('InsuredCity').value = document.all("AppntCity").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('InsuredDistrict').value = document.all("AppntDistrict").value;
    } catch(ex) {
    }
    ;
　
    try {
        document.all('LicenseType').value = document.all("AppntLicenseType").value;
    } catch(ex) {
    }
    ;
    try {
        document.all('IncomeWay').value = document.all("IncomeWay0").value;
    } catch(ex) {
    }
    ;
    try {
        document.all('Income').value = document.all("Income0").value;
    } catch(ex) {
    }
    ;
    showOneCodeName('incomeway', 'IncomeWay', 'IncomeWayName');

    /***************************
      if(document.all('Position').value=="false"){
          document.all('Position').value="";
        }
      if(document.all('Salary').value=="false"){
          document.all( 'Salary' ).value="";
      }
    ***************************/
}
/*********************************************************************
 *  查询被保人告知信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getImpartInfo()
{
    initImpartGrid2();
    var InsuredNo = document.all("InsuredNo").value;
    var ContNo = document.all("ContNo").value;
    //告知信息初始化
    if (InsuredNo != null && InsuredNo != "")
    {

				var sqlid41="CardContInputSql41";
				var mySql41=new SqlClass();
				mySql41.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql41.setSqlId(sqlid41);//指定使用的Sql的id
				mySql41.addSubPara(InsuredNo);//指定传入的参数
				mySql41.addSubPara(ContNo);//指定传入的参数
			     var strSQL0 =mySql41.getString();	

				var sqlid42="CardContInputSql42";
				var mySql42=new SqlClass();
				mySql42.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql42.setSqlId(sqlid42);//指定使用的Sql的id
				mySql42.addSubPara(InsuredNo);//指定传入的参数
				mySql42.addSubPara(ContNo);//指定传入的参数
			     var strSQL1 =mySql42.getString();	

//        var strSQL0 = "select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='" + InsuredNo + "' and ContNo='" + ContNo + "' and impartver='01' and impartcode='001' and ImpartParamNo = '1'";
//
//        var strSQL1 = "select ImpartParam from LCCustomerImpartparams where CustomerNoType='1' and CustomerNo='" + InsuredNo + "' and ContNo='" + ContNo + "' and impartver='01' and impartcode='001' and ImpartParamNo = '2'";

        arrResult = easyExecSql(strSQL0, 1, 0);
        arrResult1 = easyExecSql(strSQL1, 1, 0);


        try {
            document.all('Income').value = arrResult[0][0];
        } catch(ex) {
        }
        ;
        try {
            document.all('IncomeWay').value = arrResult1[0][0];
        } catch(ex) {
        }
        ;

				var sqlid43="CardContInputSql43";
				var mySql43=new SqlClass();
				mySql43.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql43.setSqlId(sqlid43);//指定使用的Sql的id
				mySql43.addSubPara(InsuredNo);//指定传入的参数
				mySql43.addSubPara(ContNo);//指定传入的参数
			     var strSQL =mySql43.getString();	

//        var strSQL = "select ImpartVer,ImpartCode,ImpartContent,ImpartParamModle from LCCustomerImpart where CustomerNo='" + InsuredNo + "' and ProposalContNo='" + ContNo + "' and CustomerNoType='1' and ((impartver='01' and impartcode<>'001') or (impartver='02'))";
        turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = ImpartInsuredGrid;
        //保存SQL语句
        turnPage.strQuerySql = strSQL;
        //设置查询起始位置
        turnPage.pageIndex = 0;
        //在查询结果数组中取出符合页面显示大小设置的数组
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}
/*********************************************************************
 *  查询告知信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getImpartDetailInfo()
{
    initImpartDetailGrid2();
    var InsuredNo = document.all("InsuredNo").value;
    var ContNo = document.all("ContNo").value;
    //告知信息初始化
    if (InsuredNo != null && InsuredNo != "")
    {
		
				var sqlid44="CardContInputSql44";
				var mySql44=new SqlClass();
				mySql44.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql44.setSqlId(sqlid44);//指定使用的Sql的id
				mySql44.addSubPara(InsuredNo);//指定传入的参数
				mySql44.addSubPara(ContNo);//指定传入的参数
			     var strSQL =mySql44.getString();	
		
//        var strSQL = "select ImpartVer,ImpartCode,ImpartDetailContent from LCCustomerImpartDetail where CustomerNo='" + InsuredNo + "' and ContNo='" + ContNo + "' and CustomerNoType='I'";
        turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = ImpartDetailGrid;
        //保存SQL语句
        turnPage.strQuerySql = strSQL;
        //设置查询起始位置
        turnPage.pageIndex = 0;
        //在查询结果数组中取出符合页面显示大小设置的数组
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}
/*********************************************************************
 *  获得被保人险种信息，写入MulLine
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getInsuredPolInfo()
{
    initPolGrid();
    var InsuredNo = document.all("InsuredNo").value;
    var ContNo = document.all("ContNo").value;
    //险种信息初始化
    if (InsuredNo != null && InsuredNo != "")
    {
        //alert("InsuredNo=="+InsuredNo+"--ContNo=="+ContNo);
		
				var sqlid45="CardContInputSql45";
				var mySql45=new SqlClass();
				mySql45.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql45.setSqlId(sqlid45);//指定使用的Sql的id
				mySql45.addSubPara(InsuredNo);//指定传入的参数
				mySql45.addSubPara(ContNo);//指定传入的参数
			     var strSQL =mySql45.getString();	
		
//        var strSQL = "select PolNo,RiskCode,Prem,Amnt from LCPol where InsuredNo='" + InsuredNo + "' and ContNo='" + ContNo + "'";
        //alert(strSQL);
        turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
        //判断是否查询成功
        if (!turnPage.strQueryResult)
        {
            return false;
        }
        //查询成功则拆分字符串，返回二维数组
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        //设置初始化过的MULTILINE对象
        turnPage.pageDisplayGrid = PolGrid;
        //保存SQL语句
        turnPage.strQuerySql = strSQL;
        //设置查询起始位置
        turnPage.pageIndex = 0;
        //在查询结果数组中取出符合页面显示大小设置的数组
        arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
        //调用MULTILINE对象显示查询结果
        displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
    }
}
/*********************************************************************
 *  MulLine的RadioBox点击事件，获得被保人险种详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getPolDetail(parm1, parm2)
{
    var PolNo = document.all(parm1).all('PolGrid1').value
    try {
        mSwitch.deleteVar('PolNo')
    } catch(ex) {
    }
    ;
    try {
        mSwitch.addVar('PolNo', '', PolNo);
    } catch(ex) {
    }
    ;
    fm.SelPolNo.value = PolNo;
}
/*********************************************************************
 *  根据家庭单类型，隐藏界面控件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function choosetype() {
    if (fm.FamilyType.value == "1")
        divTempFeeInput.style.display = "";
    if (fm.FamilyType.value == "0")
        divTempFeeInput.style.display = "none";
}
/*********************************************************************
 *  校验被保险人与主被保险人关系
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function checkself()
{
    if (fm.FamilyType.value == "0" && fm.RelationToMainInsured.value == "")
    {
        fm.RelationToMainInsured.value = "00";
        return true;
    }
    else if (fm.FamilyType.value == "0" && fm.RelationToMainInsured.value != "00")
    {
        alert("个人单中'与主被保险人关系'只能是'本人'");
        fm.RelationToMainInsured.value = "00";
        return false;
    }
    else if (fm.FamilyType.value == "1" && fm.RelationToMainInsured.value == "" && InsuredGrid.mulLineCount == 0)
    {
        fm.RelationToMainInsured.value = "00";
        return true;
    }
    else if (fm.FamilyType.value == "1" && fm.RelationToMainInsured.value != "00" && InsuredGrid.mulLineCount == 0)
    {
        alert("家庭单中第一位被保险人的'与主被保险人关系'只能是'本人'");
        fm.RelationToMainInsured.value = "00";
        return false;
    }
    else {
        return true;
    }
}
/*********************************************************************
 *  校验保险人
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function checkrelation()
{
    if (LoadFlag == 2 || LoadFlag == 7)
    {
        if (document.all('ContNo').value != "")
        {
            alert("团单的个单不能有多被保险人");
            return false;
        }
        else
            return true;
    }
    else
    {
        if (document.all('ContNo').value != "" && fm.FamilyType.value == "0")
        {
			
				var sqlid46="CardContInputSql46";
				var mySql46=new SqlClass();
				mySql46.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql46.setSqlId(sqlid46);//指定使用的Sql的id
				mySql46.addSubPara(fm.ContNo.value);//指定传入的参数
			    var strSQL =mySql46.getString();	
			
//            var strSQL = "select * from LCInsured where contno='" + document.all('ContNo').value + "'";
            turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
            if (turnPage.strQueryResult)
            {
                alert("个单不能有多被保险人");
                return false;
            }
            else
                return true;
        }
        else if (document.all('ContNo').value != "" && fm.FamilyType.value == "1" && InsuredGrid.mulLineCount > 0 && fm.RelationToMainInsured.value == "00")
        {
            alert("家庭单只能有一个主被保险人");
            return false;
        }
        else if (document.all('ContNo').value != "" && fm.FamilyType.value == "1" && fm.RelationToAppnt.value == "00")
        {
//            var strSql = "select * from LCInsured where contno='" + document.all('ContNo').value + "' and RelationToAppnt='00' ";
            
			var sqlid84="CardContInputSql84";
			var mySql84=new SqlClass();
			mySql84.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
			mySql84.setSqlId(sqlid84);//指定使用的Sql的id
			mySql84.addSubPara(document.all('ContNo').value);//指定传入的参数
		    var strSql =mySql84.getString();
            
            turnPage.strQueryResult = easyQueryVer3(strSql, 1, 0, 1);
            if (turnPage.strQueryResult)
            {
                alert("投保人已经是该合同号下的被保险人");
                return false;
            }
            else
                return true;
        }
        else
            return true;
    }
    //select count(*) from ldinsured

}
/*********************************************************************
 *  投保人与被保人相同选择框事件
 *  参数  ：  无
 *  返回值：  true or false
 *********************************************************************
 */
function isSamePerson()
{
    //对应未选同一人，又打钩的情况
    if (fm.SamePersonFlag.checked == true && fm.RelationToAppnt.value != "00")
    {
        DivLCInsured.style.display = "none";
        divLCInsuredPerson.style.display = "none";
        divSalary.style.display = "none";
        fm.SamePersonFlag.checked = true;
        fm.RelationToAppnt.value = "00"
        displayissameperson();
    }
    //对应是同一人，又打钩的情况
    else if (fm.SamePersonFlag.checked == true)
    {
        document.all('DivLCInsured').style.display = "none";
        divLCInsuredPerson.style.display = "none";
        divSalary.style.display = "none";

        displayissameperson();
    }
    //对应不选同一人的情况
    else if (fm.SamePersonFlag.checked == false)
    {
        document.all('DivLCInsured').style.display = "";
        divLCInsuredPerson.style.display = "";
        divSalary.style.display = "";
        try {
            document.all('Name').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Sex').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Birthday').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('IDType').value = "0";
        } catch(ex) {
        }
        ;
        try {
            document.all('IDNo').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Password').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('NativePlace').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Nationality').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('RgtAddress').value = "";
        } catch(ex) {
        }
        ;
        //      try{document.all('Marriage').value= "";}catch(ex){};
        //      try{document.all('MarriageDate').value= "";}catch(ex){};
        try {
            document.all('Health').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Stature').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Avoirdupois').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Degree').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('CreditGrade').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('OthIDType').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('OthIDNo').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('ICNo').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('GrpNo').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('JoinCompanyDate').value = "";
        } catch(ex) {
        }
        try {
            document.all('StartWorkDate').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Position').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Salary').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('OccupationType').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('OccupationCode').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('WorkType').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('PluralityType').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('DeathDate').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('SmokeFlag').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('BlacklistFlag').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Proterty').value = "";
        } catch(ex) {
        }
        ;
        //try{document.all('Remark').value= "";}catch(ex){};
        try {
            document.all('State').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Operator').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('MakeDate').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('MakeTime').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('ModifyDate').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('ModifyTime').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('PostalAddress').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('PostalAddress').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('ZipCode').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('InsuredPhone').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Mobile').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('EMail').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('GrpName').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('GrpPhone').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('GrpAddress').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('GrpZipCode').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('RelationToAppnt').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Fax').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('HomePhone').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('IncomeWay').value = "";
        } catch(ex) {
        }
        ;
        try {
            document.all('Income').value = "";
        } catch(ex) {
        }
        ;

    }
}
/*********************************************************************
 *  投保人客户号查询按扭事件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryInsuredNo()
{
    if (document.all("InsuredNo").value == "")
    {
        //showAppnt1();
        showInsured1();
    }
    else
    {
        //arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
        
				var sqlid47="CardContInputSql47";
				var mySql47=new SqlClass();
				mySql47.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql47.setSqlId(sqlid47);//指定使用的Sql的id
				mySql47.addSubPara(fm.ContNo.value);//指定传入的参数
			    var strSQL47 =mySql47.getString();	
		
		arrResult = easyExecSql(strSQL47, 1, 0);
		//arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + document.all("InsuredNo").value + "'", 1, 0);
        if (arrResult == null)
        {
            alert("未查到被保人信息");
            //displayAppnt(new Array());
            displayInsuredInfo(new Array());
            emptyUndefined();
        }
        else
        {
            //displayAppnt(arrResult[0]);
            displayInsuredInfo(arrResult[0]);
        }
    }
}
/*********************************************************************
 *  投保人查询按扭事件
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
//function showAppnt1()
function showInsured1()
{
    if (mOperate == 0)
    {
        //mOperate = 2;
        mOperate = 3;
        //被保人信息查询　mOperate = 3; hl20050503
        showInfo = window.open("../sys/LDPersonQueryNewMain.jsp?queryFlag=queryInsured","",sFeatures)
    }
}
/*********************************************************************
 *  显示投保人信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
//hanlin 20050503
//function displayAppnt()
function displayInsuredInfo()
{
    //alert("asdfasfsf");
    try {
        document.all('InsuredNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('Name').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('Sex').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('Birthday').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('IDType').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('IDNo').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    try {
        document.all('Password').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('NativePlace').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('Nationality').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('RgtAddress').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    //    try{document.all('Marriage').value= arrResult[0][10];}catch(ex){};
    //    try{document.all('MarriageDate').value= arrResult[0][11];}catch(ex){};
    try {
        document.all('Health').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('Stature').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('Avoirdupois').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    try {
        document.all('Degree').value = arrResult[0][15];
    } catch(ex) {
    }
    ;
    try {
        document.all('CreditGrade').value = arrResult[0][16];
    } catch(ex) {
    }
    ;
    try {
        document.all('OthIDType').value = arrResult[0][17];
    } catch(ex) {
    }
    ;
    try {
        document.all('OthIDNo').value = arrResult[0][18];
    } catch(ex) {
    }
    ;
    try {
        document.all('ICNo').value = arrResult[0][19];
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpNo').value = arrResult[0][20];
    } catch(ex) {
    }
    ;
    try {
        document.all('JoinCompanyDate').value = arrResult[0][21];
    } catch(ex) {
    }
    ;
    try {
        document.all('StartWorkDate').value = arrResult[0][22];
    } catch(ex) {
    }
    ;
    try {
        document.all('Position').value = arrResult[0][23];
    } catch(ex) {
    }
    ;
    try {
        document.all('Salary').value = arrResult[0][24];
    } catch(ex) {
    }
    ;
    try {
        document.all('OccupationType').value = arrResult[0][25];
    } catch(ex) {
    }
    ;
    try {
        document.all('OccupationCode').value = arrResult[0][26];
    } catch(ex) {
    }
    ;
    try {
        document.all('WorkType').value = arrResult[0][27];
    } catch(ex) {
    }
    ;
    try {
        document.all('PluralityType').value = arrResult[0][28];
    } catch(ex) {
    }
    ;
    try {
        document.all('DeathDate').value = arrResult[0][29];
    } catch(ex) {
    }
    ;
    try {
        document.all('SmokeFlag').value = arrResult[0][30];
    } catch(ex) {
    }
    ;
    try {
        document.all('BlacklistFlag').value = arrResult[0][31];
    } catch(ex) {
    }
    ;
    try {
        document.all('Proterty').value = arrResult[0][32];
    } catch(ex) {
    }
    ;
    //try{document.all('Remark').value= arrResult[0][33];}catch(ex){};
    try {
        document.all('State').value = arrResult[0][34];
    } catch(ex) {
    }
    ;
    try {
        document.all('VIPValue1').value = arrResult[0][35];
    } catch(ex) {
    }
    ;
    try {
        document.all('Operator').value = arrResult[0][36];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeDate').value = arrResult[0][37];
    } catch(ex) {
    }
    ;
    try {
        document.all('MakeTime').value = arrResult[0][38];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyDate').value = arrResult[0][39];
    } catch(ex) {
    }
    ;
    try {
        document.all('ModifyTime').value = arrResult[0][40];
    } catch(ex) {
    }
    ;
    try {
        document.all('LicenseType').value = arrResult[0][43];
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpName').value = arrResult[0][41];
    } catch(ex) {
    }
    ;


    //地址显示部分的变动
    try {
        document.all('InsuredAddressNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('PostalAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('ZipCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredPhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Mobile').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('EMail').value = "";
    } catch(ex) {
    }
    ;
    //try{document.all('GrpName').value= arrResult[0][46];}catch(ex){};
    try {
        document.all('GrpPhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpZipCode').value = "";
    } catch(ex) {
    }
    ;
}
/*********************************************************************
 *  查询返回后触发
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterQuery21(arrQueryResult)
{
    //alert("1111here:" + arrQueryResult + "\n" + mOperate);

    if (arrQueryResult != null)
    {
        arrResult = arrQueryResult;

        if (mOperate == 1)
        {        // 查询投保单
            document.all('ContNo').value = arrQueryResult[0][0];

				var sqlid48="CardContInputSql48";
				var mySql48=new SqlClass();
				mySql48.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql48.setSqlId(sqlid48);//指定使用的Sql的id
				mySql48.addSubPara(arrQueryResult[0][0] );//指定传入的参数
			    var strSQL48 =mySql48.getString();	

              arrResult = easyExecSql(strSQL48, 1, 0);
            //arrResult = easyExecSql("select ProposalContNo,PrtNo,ManageCom,SaleChnl,AgentCode,AgentGroup,AgentCode1,AgentCom,AgentType,Remark from LCCont where LCCont = '" + arrQueryResult[0][0] + "'", 1, 0);

            if (arrResult == null)
            {
                alert("未查到投保单信息");
            }
            else
            {
                displayLCContPol(arrResult[0]);
            }
        }

        if (mOperate == 3)
        {        // 被保人信息
            //arrResult = easyExecSql("select a.*,b.AddressNo,b.PostalAddress,b.ZipCode,b.HomePhone,b.Mobile,b.EMail,a.GrpNo,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode from LDPerson a,LCAddress b where 1=1 and a.CustomerNo=b.CustomerNo and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
           
		   		var sqlid49="CardContInputSql49";
				var mySql49=new SqlClass();
				mySql49.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql49.setSqlId(sqlid49);//指定使用的Sql的id
				mySql49.addSubPara(arrQueryResult[0][0] );//指定传入的参数
			    var strSQL49 =mySql49.getString();	
		   
		    arrResult = easyExecSql(strSQL49, 1, 0);
		   // arrResult = easyExecSql("select a.*,(select GrpName from LDGrp where CustomerNo=(select GrpNo from LDPerson where CustomerNo=a.CustomerNo) ) from LDPerson a where 1=1  and a.CustomerNo = '" + arrQueryResult[0][0] + "'", 1, 0);
            if (arrResult == null)
            {
                alert("未查到投保人信息");
            }
            else
            {
                //hl 20050503
                // displayAppnt(arrResult[0]);
                //alert("arrResult[0][35]=="+arrResult[0][35]);
                displayInsuredInfo(arrResult[0]);
                // showCodeName();
            }
        }
    }

    mOperate = 0;
    // 恢复初态
    showCodeName();
}
/*********************************************************************
 *  查询职业类别
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getdetailwork2()
{
    showOneCodeName('OccupationCode', 'OccupationCode', 'OccupationCodeName');
	
			   	var sqlid50="CardContInputSql50";
				var mySql50=new SqlClass();
				mySql50.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql50.setSqlId(sqlid50);//指定使用的Sql的id
				mySql50.addSubPara( fm.OccupationCode.value );//指定传入的参数
			    var strSql =mySql50.getString();	
	
//    var strSql = "select OccupationType from LDOccupation where OccupationCode='" + fm.OccupationCode.value + "'";
    var arrResult = easyExecSql(strSql);
    if (arrResult != null)
    {
        fm.OccupationType.value = arrResult[0][0];
        showOneCodeName('OccupationType', 'OccupationType', 'OccupationTypeName');
    }
    else
    {
        fm.OccupationType.value = "";
        fm.OccupationTypeName.value = "";
    }
}
/*获得个人单信息，写入页面控件
function getProposalInsuredInfo(){
  var ContNo = fm.ContNo.value;
  //被保人详细信息
  var strSQL ="select * from ldperson where CustomerNo in (select InsuredNo from LCInsured where ContNo='"+ContNo+"')";
  arrResult=easyExecSql(strSQL);
  if(arrResult!=null){
  	DisplayCustomer();
  }

  strSQL ="select * from LCInsured where ContNo = '"+ContNo+"'";
  arrResult=easyExecSql(strSQL);

  if(arrResult!=null){
  	   DisplayInsured();
  }else{


    return;
  }

  var tAddressNo = arrResult[0][10]; 		// 得到被保人地址号
  var InsuredNo=arrResult[0][2];
  var strSQL="select * from LCAddress where AddressNo='"+tAddressNo+"' and CustomerNo='"+InsuredNo+"'";
  arrResult=easyExecSql(strSQL);
    if(arrResult!=null){
  	DisplayAddress();
    }

    getInsuredPolInfo();

}*/


/*********************************************************************
 *  把合同所有信息录入结束确认(团险)
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function inputConfirm2(wFlag)
{
    alert("LoadFlag==" + LoadFlag);


    if (wFlag == 1) //录入完毕确认
    {
		
				var sqlid51="CardContInputSql51";
				var mySql51=new SqlClass();
				mySql51.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql51.setSqlId(sqlid51);//指定使用的Sql的id
				mySql51.addSubPara( fm.ContNo.value );//指定传入的参数
			    var tStr =mySql51.getString();	
		
//        var tStr = "	select * from lwmission where 1=1 and lwmission.activityid in ('0000001001','0000001002','0000001220','0000001230') and lwmission.missionprop1 = '" + fm.ContNo.value + "'";
        turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
        if (turnPage.strQueryResult)
        {
            alert("该合同已经做过保存！");
            return;
        }
        fm.AppntNo.value = AppntNo;
        fm.AppntName.value = AppntName;
        fm.WorkFlowFlag.value = "7999999999";
    }
    else if (wFlag == 2)//复核完毕确认
    {
        if (document.all('ProposalContNo').value == "")
        {
            alert("未查询出合同信息,不容许您进行 [复核完毕] 确认！");
            return;
        }
        fm.WorkFlowFlag.value = "0000001001";
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
    }
    else if (wFlag == 3)
    {
        if (document.all('ProposalContNo').value == "")
        {
            alert("未查询出合同信息,不容许您进行 [复核修改完毕] 确认！");
            return;
        }
        fm.WorkFlowFlag.value = "0000001002";
        fm.MissionID.value = tMissionID;
        fm.SubMissionID.value = tSubMissionID;
    }
    else
        return;

    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./InputConfirm.jsp";
    fm.submit();
    //提交
}
/*********************************************************************
 *  查询被保险人详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getdetailaddress2()
{
    //alert("abc");
	
				var sqlid52="CardContInputSql52";
				var mySql52=new SqlClass();
				mySql52.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql52.setSqlId(sqlid52);//指定使用的Sql的id
				mySql52.addSubPara( fm.InsuredAddressNo.value );//指定传入的参数
				mySql52.addSubPara( fm.InsuredNo.value );//指定传入的参数
			    var strSQL =mySql52.getString();	
	
//    var strSQL = "select b.AddressNo,b.PostalAddress,b.ZipCode,b.Phone,b.Mobile,b.EMail,b.CompanyPhone,b.CompanyAddress,b.CompanyZipCode,b.fax,b.HomePhone,b.grpName,b.province,b.city,b.County from LCAddress b where b.AddressNo='" + fm.InsuredAddressNo.value + "' and b.CustomerNo='" + fm.InsuredNo.value + "'";
    arrResult = easyExecSql(strSQL);
    try {
        document.all('InsuredAddressNo').value = arrResult[0][0];
    } catch(ex) {
    }
    ;
    try {
        document.all('PostalAddress').value = arrResult[0][1];
    } catch(ex) {
    }
    ;
    try {
        document.all('ZIPCODE').value = arrResult[0][2];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredPhone').value = arrResult[0][3];
    } catch(ex) {
    }
    ;
    try {
        document.all('Mobile').value = arrResult[0][4];
    } catch(ex) {
    }
    ;
    try {
        document.all('EMail').value = arrResult[0][5];
    } catch(ex) {
    }
    ;
    //try{document.all('GrpName').value= arrResult[0][6];}catch(ex){};
    try {
        document.all('GrpPhone').value = arrResult[0][6];
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpAddress').value = arrResult[0][7];
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpZipCode').value = arrResult[0][8];
    } catch(ex) {
    }
    ;
    try {
        document.all('Fax').value = arrResult[0][9];
    } catch(ex) {
    }
    ;
    try {
        document.all('HomePhone').value = arrResult[0][10];
    } catch(ex) {
    }
    ;
    //alert("arrResult[0][11]=="+arrResult[0][11]);
    // try{document.all('GrpName').value= arrResult[0][11];}catch(ex){};
    //alert("fm.GrpName="+fm.GrpName.value);
    try {
        document.all('InsuredProvince').value = arrResult[0][12];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredCity').value = arrResult[0][13];
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredDistrict').value = arrResult[0][14];
    } catch(ex) {
    }
    ;
    showOneCodeName('Province', 'InsuredProvince', 'InsuredProvinceName');
    showOneCodeName('City', 'InsuredCity', 'InsuredCityName');
    showOneCodeName('District', 'InsuredDistrict', 'InsuredDistrictName');
}
/*********************************************************************
 *  查询保险计划
 *  参数  ：  集体合同投保单号
 *  返回值：  无
 *********************************************************************
 */
function getContPlanCode(tProposalGrpContNo)
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
	
					var sqlid53="CardContInputSql53";
				var mySql53=new SqlClass();
				mySql53.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql53.setSqlId(sqlid53);//指定使用的Sql的id
				mySql53.addSubPara( tProposalGrpContNo );//指定传入的参数
			   strsql =mySql53.getString();	
	
//    strsql = "select ContPlanCode,ContPlanName from LCContPlan where ContPlanCode!='00' and ProposalGrpContNo='" + tProposalGrpContNo + "'";
    
    
    
    
    //alert("strsql :" + strsql);
    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
        divContPlan.style.display = "";
    }
    else
    {
        //alert("保险计划没查到");
        divContPlan.style.display = "none";
    }
    //alert ("tcodedata : " + tCodeData);
    return tCodeData;
}

/*********************************************************************
 *  查询处理机构
 *  参数  ：  集体合同投保单号
 *  返回值：  无
 *********************************************************************
 */
function getExecuteCom(tProposalGrpContNo)
{
    //alert("1");
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
	
				var sqlid54="CardContInputSql54";
				var mySql54=new SqlClass();
				mySql54.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql54.setSqlId(sqlid54);//指定使用的Sql的id
				mySql54.addSubPara( tProposalGrpContNo );//指定传入的参数
			   strsql =mySql54.getString();	
	
//    strsql = "select ExecuteCom,Name from LCGeneral a,LDCom b where a.GrpContNo='" + tProposalGrpContNo + "' and a.ExecuteCom=b.ComCode";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
            //			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
        divExecuteCom.style.display = "";
    }
    else
    {
        divExecuteCom.style.display = "none";
    }
    //alert ("tcodedata : " + tCodeData);

    return tCodeData;
}
/*********************************************************************
 *  清空投保人数据
 *  参数  ：  无
 *  返回值：  无
 **********************************************************************/
function emptyAppnt()
{
    try {
        document.all('AppntVIPValue').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntVIPFlagname').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntIDNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSex').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntSexName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBirthday').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAge').value = "";
    } catch(ex) {
    }
    ;
    //        try{document.all('AppntMarriage').value= ""; }catch(ex){};
    //        try{document.all('AppntMarriageName').value= ""; }catch(ex){};
    try {
        document.all('AppntNativePlace').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntNativePlaceName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOccupationCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntOccupationCodeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntLicenseType').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntLicenseTypeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAddressNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AddressNoName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntProvince').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntProvinceName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntCity').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntCityName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntDistrict').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntDistrictName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntPostalAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntZipCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntMobile').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpPhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntFax').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntHomePhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntGrpName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntEMail').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('PayMode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('FirstPayModeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('FirstBankCodeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntAccName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankAccNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SecPayMode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('PayModeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntBankCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntBankCodeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntAccName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SecAppntBankAccNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Income0').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('IncomeWay0').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('IncomeWayName0').value = "";
    } catch(ex) {
    }
    ;
    ImpartGrid.clearData();
    ImpartGrid.addOne();

}
function emptyInsured()
{

    try {
        document.all('InsuredNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('VIPValue1').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AppntVIPFlagname1').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SequenceNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('ExecuteCom').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('FamilyID').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('RelationToMainInsured').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('RelationToAppnt').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('RelationToAppntName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredAddressNo').value = "";
    } catch(ex) {
    }
    ;
    //try{document.all('SequenceNo').value= ""; }catch(ex){};
    try {
        document.all('Name').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Sex').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SexName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('RelationToMainInsuredName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SequenceNoName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Birthday').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredAppAge').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('IDType').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('IDNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('IncomeWayName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredProvince').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredProvinceName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredCity').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredCityName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredDistrict').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredDistrictName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Income').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('LicenseType').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('LicenseTypeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('IDTypeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredAddressNoName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('IncomeWay').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('NativePlace').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('NativePlaceName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Nationality').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('RgtAddress').value = "";
    } catch(ex) {
    }
    ;
    //try{document.all('Marriage').value= ""; }catch(ex){};
    //try{document.all('MarriageName').value= ""; }catch(ex){};
    //try{document.all('MarriageDate').value= ""; }catch(ex){};
    try {
        document.all('Health').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Stature').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Avoirdupois').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Degree').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('CreditGrade').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('BankCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('BankAccNo').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('AccName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('JoinCompanyDate').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('StartWorkDate').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Position').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Salary').value = "";
    } catch(ex) {
    }
    ;
    //try{document.all('OccupationType').value= ""; }catch(ex){};
    try {
        document.all('OccupationCodeName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('OccupationCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('WorkType').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('PluralityType').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('SmokeFlag').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('ContPlanCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpName').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('HomeAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('HomeZipCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('HomePhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('HomeFax').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpFax').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Fax').value = "";
    } catch(ex) {
    }
    ;
    emptyAddress();
    ImpartGrid.clearData();
    ImpartGrid.addOne();
    //ImpartDetailGrid.clearData();
    //ImpartDetailGrid.addOne();
}

/*********************************************************************
 *  清空客户地址数据
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function emptyAddress()
{
    try {
        document.all('PostalAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('ZipCode').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('InsuredPhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('Mobile').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('EMail').value = "";
    } catch(ex) {
    }
    ;
    //try{document.all('GrpName').value= arrResult[0][2]; }catch(ex){};
    try {
        document.all('GrpPhone').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpAddress').value = "";
    } catch(ex) {
    }
    ;
    try {
        document.all('GrpZipCode').value = "";
    } catch(ex) {
    }
    ;
}
/*********************************************************************
 *  根据身份证号取得出生日期和性别
 *  参数  ：  身份证号
 *  返回值：  无
 *********************************************************************
 */

function getBirthdaySexByIDNo2(iIdNo)
{
    //alert("aafsd");
    if (document.all('IDType').value == "0")
    {
        var tBirthday = getBirthdatByIdNo(iIdNo);
        var tSex = getSexByIDNo(iIdNo);
        if (tBirthday == "输入的身份证号位数错误" || tSex == "输入的身份证号位数错误")
        {
            alert("输入的身份证号位数错误");
            theFirstValue = "";
            theSecondValue = "";
            //document.all('IDNo').focus();
            return;

        }
        else
        {
            document.all('Birthday').value = tBirthday;
            document.all('Sex').value = tSex;
        }

    }
}
/*********************************************************************
 *  合同信息录入完毕确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function GrpInputConfirm(wFlag)
{
    mWFlag = 1;
    if (wFlag == 1) //录入完毕确认
    {
//        var tStr = "	select * from lwmission where 1=1 "
//                + " and lwmission.processid = '0000000004'"
//                + " and lwmission.activityid = '0000002001'"
//                + " and lwmission.missionprop1 = '" + fm.ProposalGrpContNo.value + "'";
        
		var sqlid85="CardContInputSql85";
		var mySql85=new SqlClass();
		mySql85.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
		mySql85.setSqlId(sqlid85);//指定使用的Sql的id
		mySql85.addSubPara(fm.ProposalGrpContNo.value);//指定传入的参数
		var tStr =mySql85.getString();	
        
        turnPage.strQueryResult = easyQueryVer3(tStr, 1, 0, 1);
        if (turnPage.strQueryResult)
        {
            alert("该团单合同已经做过保存！");
            return;
        }
        if (document.all('ProposalGrpContNo').value == "")
        {
            alert("团单合同信息未保存,不容许您进行 [录入完毕] 确认！");
            return;
        }
        fm.WorkFlowFlag.value = "6999999999";
        //录入完毕
    }
    else if (wFlag == 2)//复核完毕确认
    {
        if (document.all('ProposalGrpContNo').value == "")
        {
            alert("未查询出团单合同信息,不容许您进行 [复核完毕] 确认！");
            return;
        }
        fm.WorkFlowFlag.value = "0000002002";
        //复核完毕
        fm.MissionID.value = MissionID;
        fm.SubMissionID.value = SubMissionID;
    }
    else if (wFlag == 3)
    {
        if (document.all('ProposalGrpContNo').value == "")
        {
            alert("未查询出合同信息,不容许您进行 [复核修改完毕] 确认！");
            return;
        }
        fm.WorkFlowFlag.value = "0000001002";
        //复核修改完毕
        fm.MissionID.value = MissionID;
        fm.SubMissionID.value = SubMissionID;
    }
    else if (wFlag == 4)
    {
        if (document.all('ProposalGrpContNo').value == "")
        {
            alert("未查询出合同信息,不容许您进行 [修改完毕] 确认！");
            return;
        }
        fm.WorkFlowFlag.value = "0000001021";
        //问题修改
        fm.MissionID.value = MissionID;
        fm.SubMissionID.value = SubMissionID;
    }
    else
        return;

    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr, window, "status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./GrpInputConfirm.jsp";
    fm.submit();
    //提交
}
function getaddresscodedata2()
{
    var i = 0;
    var j = 0;
    var m = 0;
    var n = 0;
    var strsql = "";
    var tCodeData = "0|";
    if (fm.InsuredAddressNo.value == "")
    {
        fm.PostalAddress.value = "";
        fm.InsuredAddressNoName.value = "";
        fm.InsuredProvinceName.value = "";
        fm.InsuredProvince.value = "";
        fm.InsuredCityName.value = "";
        fm.InsuredCity.value = "";
        fm.InsuredDistrictName.value = "";
        fm.InsuredDistrict.value = "";
        fm.PostalAddress.value = "";
        fm.ZIPCODE.value = "";
        fm.Mobile.value = "";
        fm.GrpPhone.value = "";
        fm.Fax.value = "";
        fm.HomePhone.value = "";
        fm.EMail.value = "";
    }
	
					var sqlid55="CardContInputSql55";
				var mySql55=new SqlClass();
				mySql55.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql55.setSqlId(sqlid55);//指定使用的Sql的id
				mySql55.addSubPara( fm.InsuredNo.value );//指定传入的参数
			   strsql =mySql55.getString();	
	
//    strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='" + fm.InsuredNo.value + "'";
    //alert("strsql :" + strsql);
    turnPage.strQueryResult = easyQueryVer3(strsql, 1, 0, 1);
    if (turnPage.strQueryResult != "")
    {
        turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
        m = turnPage.arrDataCacheSet.length;
        for (i = 0; i < m; i++)
        {
            j = i + 1;
            tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
        }
    }
    //alert ("tcodedata : " + tCodeData);
    //return tCodeData;
    document.all("InsuredAddressNo").CodeData = tCodeData;
}

function getImpartCode(parm1, parm2) {
    //alert("hehe:"+document.all(parm1).all('ImpartGrid1').value);
    var impartVer = document.all(parm1).all('ImpartGrid1').value;
    window.open("../app/ImpartCodeSel.jsp?ImpartVer=" + impartVer,"",sFeatures);
}
function checkidtype2()
{
    //alert("sdasf");
    if (fm.IDType.value == "")
    {
        alert("请先选择证件类型！");
        //	fm.IDNo.value="";
    }
}
function getallinfo()
{
    if (fm.Name.value != "" && fm.IDType.value != "" && fm.IDNo.value != "")
    {
		
				var sqlid56="CardContInputSql56";
				var mySql56=new SqlClass();
				mySql56.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql56.setSqlId(sqlid56);//指定使用的Sql的id
				mySql56.addSubPara( fm.Name.value );//指定传入的参数
				mySql56.addSubPara( fm.IDType.value );//指定传入的参数
				mySql56.addSubPara( fm.IDNo.value );//指定传入的参数
			    strSQL =mySql56.getString();	
		
//        strSQL = "select a.CustomerNo, a.Name, a.Sex, a.Birthday, a.IDType, a.IDNo from LDPerson a where 1=1 "
//                + "  and Name='" + fm.Name.value
//                + "' and IDType='" + fm.IDType.value
//                + "' and IDNo='" + fm.IDNo.value
//                + "' order by a.CustomerNo";
        turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 0, 1);
        if (turnPage.strQueryResult != "")
        {
            mOperate = 2;
            window.open("../sys/LDPersonQueryAll.html?Name=" + fm.Name.value + "&IDType=" + fm.IDType.value + "&IDNo=" + fm.IDNo.value, "newwindow", "height=10,width=1090,top=180,left=180, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no");
        }
        else
            return;
    }
}
function DelRiskInfo()
{
    if (fm.InsuredNo.value == "")
    {
        alert("请先选择被保人");
        return false;
    }
    var tSel = PolGrid.getSelNo();
    if (tSel == 0 || tSel == null)
    {
        alert("该客户没有险种或者您忘记选择了？");
        return false;
    }
    var tRow = PolGrid.getSelNo() - 1;
    var tpolno = PolGrid.getRowColData(tRow, 1)
    document.all('fmAction').value = "DELETE||INSUREDRISK";
    fm.action = "./DelIsuredRisk.jsp?polno=" + tpolno;
    fm.submit();
    //提交

}
function InsuredChk()
{
    var tSel = InsuredGrid.getSelNo();
    if (tSel == 0 || tSel == null)
    {
        alert("请先选择被保险人！");
        return false;
    }
    var tRow = InsuredGrid.getSelNo() - 1;
    var tInsuredNo = InsuredGrid.getRowColData(tRow, 1);
    var tInsuredName = InsuredGrid.getRowColData(tRow, 2);
    var tInsuredSex = InsuredGrid.getRowColData(tRow, 3);
    var tBirthday = InsuredGrid.getRowColData(tRow, 4);
	
					var sqlid57="CardContInputSql57";
				var mySql57=new SqlClass();
				mySql57.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql57.setSqlId(sqlid57);//指定使用的Sql的id
				mySql57.addSubPara(tInsuredName);//指定传入的参数
				mySql57.addSubPara( tInsuredSex );//指定传入的参数
				mySql57.addSubPara( tInsuredNo );//指定传入的参数
				
				mySql57.addSubPara(tInsuredNo);//指定传入的参数
				mySql57.addSubPara( fm.IDType.value );//指定传入的参数
				mySql57.addSubPara( fm.IDNo.value );//指定传入的参数
				mySql57.addSubPara( tInsuredNo );//指定传入的参数
			     var sqlstr  =mySql57.getString();	
	
//    var sqlstr = "select * from ldperson where Name='" + tInsuredName
//            + "' and Sex='" + tInsuredSex + "' and Birthday='" + tBirthday
//            + "' and CustomerNo<>'" + tInsuredNo + "'"
//            + " union select * from ldperson where IDType = '" + fm.IDType.value
//            + "' and IDType is not null and IDNo = '" + fm.IDNo.value
//            + "' and IDNo is not null and CustomerNo<>'" + tInsuredNo + "'" ;
    arrResult = easyExecSql(sqlstr, 1, 0);

    if (arrResult == null)
    {
        alert("该没有与该被保人保人相似的客户,无需校验");
        return false;
    }

    window.open("../uw/AppntChkMain.jsp?ProposalNo1=" + fm.ContNo.value + "&Flag=I&LoadFlag=" + LoadFlag + "&InsuredNo=" + tInsuredNo, "window1",sFeatures);
}
function getdetailaccount()
{
    if (fm.AccountNo.value == "1")
    {
        document.all('BankAccNo').value = mSwitch.getVar("AppntBankAccNo");
        document.all('BankCode').value = mSwitch.getVar("AppntBankCode");
        document.all('AccName').value = mSwitch.getVar("AppntAccName");
    }
    if (fm.AccountNo.value == "2")
    {
        document.all('BankAccNo').value = "";
        document.all('BankCode').value = "";
        document.all('AccName').value = "";
    }

}
function AutoMoveForNext()
{
    if (fm.AutoMovePerson.value == "定制第二被保险人")
    {
        //emptyFormElements();
        param = "122";
        fm.pagename.value = "122";
        fm.AutoMovePerson.value = "定制第三被保险人";
        return false;
    }
    if (fm.AutoMovePerson.value == "定制第三被保险人")
    {
        //emptyFormElements();
        param = "123";
        fm.pagename.value = "123";
        fm.AutoMovePerson.value = "定制第一被保险人";
        return false;
    }
    if (fm.AutoMovePerson.value == "定制第一被保险人")
    {
        //emptyFormElements();
        param = "121";
        fm.pagename.value = "121";
        fm.AutoMovePerson.value = "定制第二被保险人";
        return false;
    }
}
function noneedhome()
{

    var insuredno = "";
    if (InsuredGrid.mulLineCount >= 1)
    {
        for (var personcount = 0; personcount <= InsuredGrid.mulLineCount; personcount++)
        {
            if (InsuredGrid.getRowColData(personcount, 5) == "00")
            {
                insuredno = InsuredGrid.getRowColData(personcount, 1);

                break;
            }
        }
		
				var sqlid58="CardContInputSql58";
				var mySql58=new SqlClass();
				mySql58.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql58.setSqlId(sqlid58);//指定使用的Sql的id
				mySql58.addSubPara(insuredno);//指定传入的参数
				mySql58.addSubPara(  fm.ContNo.value );//指定传入的参数
				mySql58.addSubPara( insuredno );//指定传入的参数
				
			     var strhomea  =mySql58.getString();	
		
//        var strhomea = "select HomeAddress,HomeZipCode,HomePhone from lcaddress where customerno='" + insuredno + "' and addressno=(select addressno from lcinsured where contno='" + fm.ContNo.value + "' and insuredno='" + insuredno + "')";
        arrResult = easyExecSql(strhomea, 1, 0);
        try {
            document.all('HomeAddress').value = arrResult[0][0];
        } catch(ex) {
        }
        ;

        try {
            document.all('HomeZipCode').value = arrResult[0][1];
        } catch(ex) {
        }
        ;

        try {
            document.all('HomePhone').value = arrResult[0][2];
        } catch(ex) {
        }
        ;

        fm.InsuredAddressNo.value = "";
        fm.InsuredNo.value = "";
    }
}
function getdetail2()
{
	
					var sqlid59="CardContInputSql59";
				var mySql59=new SqlClass();
				mySql59.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql59.setSqlId(sqlid59);//指定使用的Sql的id
				mySql59.addSubPara(  fm.BankAccNo.value );//指定传入的参数
				
			     var strSql  =mySql59.getString();
	
//    var strSql = "select BankCode,AccName from LCAccount where BankAccNo='" + fm.BankAccNo.value + "'";
    arrResult = easyExecSql(strSql);
    if (arrResult != null) {
        fm.BankCode.value = arrResult[0][0];
        fm.AccName.value = arrResult[0][1];
    }
}


// 在初始化body时自动效验投保人信息
function InsuredChkNew() {
    //alert("aaa");
    var tRow = InsuredGrid.getSelNo() - 1;
    var tInsuredNo = InsuredGrid.getRowColData(tRow, 1);
    var tInsuredName = InsuredGrid.getRowColData(tRow, 2);
    var tInsuredSex = InsuredGrid.getRowColData(tRow, 3);
    var tBirthday = InsuredGrid.getRowColData(tRow, 4);
	
				var sqlid60="CardContInputSql60";
				var mySql60=new SqlClass();
				mySql60.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql60.setSqlId(sqlid60);//指定使用的Sql的id
				mySql60.addSubPara(  tInsuredName );//指定传入的参数
				mySql60.addSubPara(  tInsuredSex );//指定传入的参数
				mySql60.addSubPara(  tBirthday );//指定传入的参数
				mySql60.addSubPara(  tInsuredNo );//指定传入的参数
				mySql60.addSubPara(   fm.IDType.value );//指定传入的参数
				mySql60.addSubPara(   fm.IDNo.value );//指定传入的参数
				mySql60.addSubPara(  tInsuredNo );//指定传入的参数
				
			     var sqlstr  =mySql60.getString();
	
//    var sqlstr = "select * from ldperson where Name='" + tInsuredName
//            + "' and Sex='" + tInsuredSex + "' and Birthday='" + tBirthday
//            + "' and CustomerNo<>'" + tInsuredNo + "'"
//            + " union select * from ldperson where IDType = '" + fm.IDType.value
//            + "' and IDType is not null and IDNo = '" + fm.IDNo.value
//            + "' and IDNo is not null and CustomerNo<>'" + tInsuredNo + "'" ;
    arrResult = easyExecSql(sqlstr, 1, 0);
    if (arrResult == null)
    {
        //disabled"投保人效验"按钮
        fm.InsuredChkButton.disabled = true;
        //				  return false;
    }
    else
    {
        fm.InsuredChkButton.disabled = "";
        //如果有相同姓名、性别、生日不同客户号的用户显示"投保人校验"按钮
    }
}


//*******************************************************************
//将首期账号赋值到续期账号上
function theSameToFirstAccount() {
    //alert("aasf");
    if (fm.theSameAccount.checked == true) {
        document.all('SecAppntBankCode').value = document.all('AppntBankCode').value;
        document.all('SecAppntAccName').value = document.all('AppntAccName').value;
        //    alert(document.all('AppntBankAccNo').value);
        //    alert(document.all('SecAppntBankAccNo').value);
        document.all('SecAppntBankAccNo').value = document.all('AppntBankAccNo').value;
        return;
    }
    if (fm.theSameAccount.checked == false) {
        //  	document.all('AppntBankCode').value='';
        //  	document.all('AppntAccName').value='';
        //  	document.all('AppntBankAccNo').value='';
        return;
    }
}

//显示首期账号信息
function displayFirstAccount() {
    document.all('AppntBankCode').value = arrResult[0][0];
    document.all('AppntAccName').value = arrResult[0][1];
    document.all('AppntBankAccNo').value = arrResult[0][2];

    //查询出首期账号后，并同时赋值给续期账号
    document.all('SecAppntBankCode').value = document.all('AppntBankCode').value;
    document.all('SecAppntAccName').value = document.all('AppntAccName').value;
    document.all('SecAppntBankAccNo').value = document.all('AppntBankAccNo').value;

}

//强制进入人工核保
function forceUW() {
    //查询是否已经进行选择强制进入人工核保
    var ContNo = document.all("ContNo").value;
	
					var sqlid61="CardContInputSql61";
				var mySql61=new SqlClass();
				mySql61.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql61.setSqlId(sqlid61);//指定使用的Sql的id
				mySql61.addSubPara(  ContNo );//指定传入的参数

				
			     var sqlstr  =mySql61.getString();
	
//    var sqlstr = "select forceuwflag from lccont where contno='" + ContNo + "'" ;
    arrResult = easyExecSql(sqlstr, 1, 0);
    if (arrResult == null) {
        alert("不存在该投保单！");
    }
    else {
        window.open("../uw/ForceUWMain.jsp?ContNo=" + ContNo, "window1",sFeatures);
    }

}

//查询是否已经添加过投保人
function checkAppnt() {
    var ContNo = document.all("ContNo").value;
	
						var sqlid62="CardContInputSql62";
				var mySql62=new SqlClass();
				mySql62.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql62.setSqlId(sqlid62);//指定使用的Sql的id
				mySql62.addSubPara(  ContNo );//指定传入的参数
				
			     var sqlstr  =mySql62.getString();
	
//    var sqlstr = "select forceuwflag from lccont where contno='" + ContNo + "'" ;
    arrResult = easyExecSql(sqlstr, 1, 0);
    if (arrResult == null) {
        return false;
    }
    else {
        return true;
    }

}

function exitAuditing() {
    if (confirm("确认该投保单的复核界面？")) {
        top.close();
    }
    else {
        return;
    }
}

function exitAuditing2() {
    if (confirm("确认离开该投保单问题件修改界面？")) {
        top.close();
    }
    else {
        return;
    }
}


function showNotePad()
{

    //	var selno = SelfGrpGrid.getSelNo()-1;
    //	if (selno <0)
    //	{
    //	      alert("请选择一条任务");
    //	      return;
    //	}

    //var MissionID = SelfGrpGrid.getRowColData(selno, 4);
    //var MissionID = MissionID;
    //alert("MissionID="+MissionID);

    //var SubMissionID = SelfGrpGrid.getRowColData(selno, 5);
    // var SubMissionID = document.all.SubMissionID.value;
    //alert("SubMissionID="+SubMissionID);

    //	var ActivityID = SelfGrpGrid.getRowColData(selno, 6);
    var ActivityID = document.all.ActivityID.value;
    //alert("ActivityID="+ActivityID);

    //	var PrtNo = SelfGrpGrid.getRowColData(selno, 2);
    var PrtNo = document.all.PrtNo2.value;
    // alert("PrtNo="+ document.all.PrtNo2.value);

    var NoType = document.all.NoType.value;
    //alert("NoType="+NoType);
    if (PrtNo == null || PrtNo == "")
    {
        alert("投保单号为空！");
        return;
    }
    var varSrc = "&MissionID=" + MissionID + "&SubMissionID=" + SubMissionID + "&ActivityID=" + ActivityID + "&PrtNo=" + PrtNo + "&NoType=" + NoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc, "工作流记事本查看", "left");

}

/*********************************************************************
 *  代码框汉化
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showCodeName()
{

    showAppntCodeName();
    showContCodeName();
    showInsuredCodeName();
}
/*********************************************************************
 *  合同代码框汉化
 *  参数  ：  无
 *  返回值：  无
 **********************************************************************/
function showContCodeName()
{

    showOneCodeName('comcode', 'ManageCom', 'ManageComName');
    //代码汉化

    showOneCodeName('comcode', 'AgentManageCom', 'AgentManageComName');

    showOneCodeName('sellType', 'SellType', 'sellTypeName');

}
/*********************************************************************
 *  投保人代码框汉化
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showAppntCodeName()
{
    showOneCodeName('vipvalue', 'AppntVIPValue', 'AppntVIPFlagname');

    showOneCodeName('IDType', 'AppntIDType', 'AppntIDTypeName');

    showOneCodeName('Sex', 'AppntSex', 'AppntSexName');

    //showOneCodeName('Marriage','AppntMarriage','AppntMarriageName');

    showOneCodeName('NativePlace', 'AppntNativePlace', 'AppntNativePlaceName');

    showOneCodeName('OccupationCode', 'AppntOccupationCode', 'AppntOccupationCodeName');

    showOneCodeName('LicenseType', 'AppntLicenseType', 'AppntLicenseTypeName');

    showOneCodeName('GetAddressNo', 'AppntAddressNo', 'AddressNoName');

    showOneCodeName('province', 'AppntProvince', 'AppntProvinceName');

    showOneCodeName('city', 'AppntCity', 'AppntCityName');

    showOneCodeName('district', 'AppntDistrict', 'AppntDistrictName');

    showOneCodeName('paymode', 'PayMode', 'FirstPayModeName');

    showOneCodeName('continuepaymode', 'SecPayMode', 'PayModeName');

    showOneCodeName('bank', 'AppntBankCode', 'FirstBankCodeName');

    showOneCodeName('bank', 'SecAppntBankCode', 'AppntBankCodeName');
    showOneCodeName('incomeway', 'IncomeWay0', 'IncomeWayName0');
    //
    //showOneCodeName('GetAddressNo','AppntAddressNo','AddressNoName');
}

/*********************************************************************
 *  被保人代码框汉化
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInsuredCodeName()
{

    showOneCodeName('SequenceNo', 'SequenceNo', 'SequenceNoName');

    showOneCodeName('vipvalue', 'VIPValue1', 'AppntVIPFlagname1');

    showOneCodeName('Relation', 'RelationToMainInsured', 'RelationToMainInsuredName');

    showOneCodeName('Relation', 'RelationToAppnt', 'RelationToAppntName');

    showOneCodeName('IDType', 'IDType', 'IDTypeName');

    showOneCodeName('Sex', 'Sex', 'SexName');

    //showOneCodeName('Marriage','Marriage','MarriageName');

    showOneCodeName('OccupationCode', 'OccupationCode', 'OccupationCodeName');

    showOneCodeName('NativePlace', 'NativePlace', 'NativePlaceName');

    showOneCodeName('LicenseType', 'LicenseType', 'LicenseTypeName');

    showOneCodeName('GetAddressNo', 'InsuredAddressNo', 'InsuredAddressNoName');

    showOneCodeName('Province', 'InsuredProvince', 'InsuredProvinceName');

    showOneCodeName('City', 'InsuredCity', 'InsuredCityName');

    showOneCodeName('District', 'InsuredDistrict', 'InsuredDistrictName');
    showOneCodeName('incomeway', 'IncomeWay', 'IncomeWayName');

}

//问题件影像查询
function QuestPicQuery() {
    alert("建设中……");
    return;
}
//查询业务员告知信息
function getImpartInitInfo()
{
    var tContNo = fm.ContNo.value;

//    var strSQL = "select impartver,impartcode,ImpartContent from LDImpart where impartver='05' ";
    
	var sqlid86="CardContInputSql86";
	var mySql86=new SqlClass();
	mySql86.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
	mySql86.setSqlId(sqlid86);//指定使用的Sql的id
    var strSQL  =mySql86.getString();

    turnPage.queryModal(strSQL, AgentImpartGrid);
	
					var sqlid63="CardContInputSql63";
				var mySql63=new SqlClass();
				mySql63.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql63.setSqlId(sqlid63);//指定使用的Sql的id
				mySql63.addSubPara(  tContNo );//指定传入的参数
				
			     var aSQL  =mySql63.getString();
	
//    var aSQL = "SELECT distinct a.impartver,a.impartcode,a.ImpartContent,b.impartparammodle "
//            + "FROM ldimpart a left join lccustomerimpart b on a.impartver=b.impartver and a.ImpartCode=b.ImpartCode and b.contno='" + tContNo + "' "
//            + "WHERE a.impartver='05'";

    turnPage.queryModal(aSQL, AgentImpartGrid);
    return true;
}

function querycont()
{
    //alert(BankFlag);
    if (BankFlag != null && BankFlag == "4") {
        fm.addbutton.disabled = true;
        fm.Donextbutton9.disabled = true;
        fm.Donextbutton10.disabled = true;
    }
    var tContNo = fm.ProposalContNo.value;
	
	         	var sqlid64="CardContInputSql64";
				var mySql64=new SqlClass();
				mySql64.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql64.setSqlId(sqlid64);//指定使用的Sql的id
				mySql64.addSubPara(  tContNo );//指定传入的参数
				
			     var strSQL  =mySql64.getString();
	
//    var strSQL = "select decode(trim(appflag),'0',a.forceuwreason,a.contno),a.polapplydate,a.selltype,a.managecom,a.agentcode,(select b.name from laagent b where trim(b.agentcode)=trim(a.agentcode)),(select c.name from labranchgroup c where trim(c.agentgroup)=trim(a.agentgroup)) from lccont a where a.proposalcontno='" + tContNo + "'";

    var Arrayresult = easyExecSql(strSQL);
    if (Arrayresult != null)
    {
        document.all('ContCardNo').value = Arrayresult[0][0];
        document.all('PolAppntDate').value = Arrayresult[0][1];
        document.all('SellType').value = Arrayresult[0][2];
        document.all('ManageCom').value = Arrayresult[0][3];
        document.all('AgentCode').value = Arrayresult[0][4];
        document.all('AgentName').value = Arrayresult[0][5];
        document.all('AgentManageCom').value = Arrayresult[0][3];
    }


	         	var sqlid65="CardContInputSql65";
				var mySql65=new SqlClass();
				mySql65.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql65.setSqlId(sqlid65);//指定使用的Sql的id
				mySql65.addSubPara(  document.all('AgentCode').value );//指定传入的参数
				
			    strSQL  =mySql65.getString();

//    strSQL = "select a.name from labranchgroup a where a.agentgroup= (select  b.agentgroup from laagent b where b.agentcode='" + document.all('AgentCode').value + "')";
    var brray = easyExecSql(strSQL);
    if(brray!=null)
    document.all('BranchAttr').value = brray[0][0];

    showOneCodeName('sellType', 'SellType', 'sellTypeName');
    showOneCodeName('comcode', 'ManageCom', 'ManageComName');
    showOneCodeName('comcode', 'AgentManageCom', 'AgentManageComName');

}


function queryappnt()
{
    var tPrtNo = fm.ProposalContNo.value;
	
		         	var sqlid66="CardContInputSql66";
				var mySql66=new SqlClass();
				mySql66.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql66.setSqlId(sqlid66);//指定使用的Sql的id
				mySql66.addSubPara( tPrtNo );//指定传入的参数
				
			     var strSQL  =mySql66.getString();
	
//    var strSQL = "select a.insuredno,a.name,a.idtype,a.idno,a.sex,a.birthday,a.marriage,a.occupationcode,(select b.grpname from lcaddress b where b.customerno=a.insuredno and b.addressno=a.addressno ),a.addressno,(select c.province from lcaddress c where c.customerno=a.insuredno and c.addressno=a.addressno ),(select d.city from lcaddress d where d.customerno=a.insuredno and d.addressno=a.addressno ),(select e.county from lcaddress e where e.customerno=a.insuredno and e.addressno=a.addressno ),(select f.postaladdress from lcaddress f where f.customerno=a.insuredno and f.addressno=a.addressno ),(select g.zipcode from lcaddress g where g.customerno=a.insuredno and g.addressno=a.addressno ),a.relationtoappnt from lcinsured a where a.prtno='" + tPrtNo + "'";

    var crrayresult = easyExecSql(strSQL);
    if (crrayresult != null)
    {
        document.all('InsuredNo').value = crrayresult[0][0];
        document.all('Name').value = crrayresult[0][1];
        document.all('IDType').value = crrayresult[0][2];
        document.all('IDNo').value = crrayresult[0][3];
        document.all('Sex').value = crrayresult[0][4];
        document.all('Birthday').value = crrayresult[0][5];
        //document.all('Marriage').value = crrayresult[0][6];
        document.all('OccupationCode').value = crrayresult[0][7];
        document.all('GrpName').value = crrayresult[0][8];
        document.all('InsuredAddressNo').value = crrayresult[0][9];
        document.all('InsuredProvince').value = crrayresult[0][10];
        document.all('InsuredCity').value = crrayresult[0][11];
        document.all('InsuredDistrict').value = crrayresult[0][12];
        document.all('PostalAddress').value = crrayresult[0][13];
        document.all('InsuredAddressNoName').value = crrayresult[0][13];
        document.all('ZIPCODE').value = crrayresult[0][14];
        document.all('RelationToAppnt').value = crrayresult[0][15];

        getAge2();
        getdetailwork2();
        showOneCodeName('idtype', 'IDType', 'IDTypeName');
        showOneCodeName('sex', 'Sex', 'SexName');
        //showOneCodeName('marriage','Marriage','MarriageName');

		         	var sqlid67="CardContInputSql67";
				var mySql67=new SqlClass();
				mySql67.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql67.setSqlId(sqlid67);//指定使用的Sql的id
				mySql67.addSubPara( document.all('InsuredProvince').value );//指定传入的参数
				mySql67.addSubPara( document.all('InsuredCity').value );//指定传入的参数
				mySql67.addSubPara( document.all('InsuredDistrict').value );//指定传入的参数
			     strSQL =mySql67.getString();


//        strSQL = "select (select a.placename from LDAddress a where a.placetype='01' and a.placecode='" + document.all('InsuredProvince').value + "')," +
//                 "(select b.placename from LDAddress b where b.placetype='02' and b.placecode='" + document.all('InsuredCity').value + "')," +
//                 "(select c.placename from LDAddress c where c.placetype='03' and c.placecode='" + document.all('InsuredDistrict').value + "') from dual ";

        var drrayresult = easyExecSql(strSQL);
        if (drrayresult != null)
        {
            document.all('InsuredProvinceName').value = drrayresult[0][0];
            document.all('InsuredCityName').value = drrayresult[0][1];
            document.all('InsuredDistrictName').value = drrayresult[0][2];
        }

		         	var sqlid68="CardContInputSql68";
				var mySql68=new SqlClass();
				mySql68.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql68.setSqlId(sqlid68);//指定使用的Sql的id
				mySql68.addSubPara( document.all('OccupationCode').value );//指定传入的参数
			     strSQL =mySql68.getString();

//        strSQL = "select trim(OccupationName) from LDOccupation where occupationcode='" + document.all('OccupationCode').value + "' and worktype = 'GR' ";

        var drrayresult = easyExecSql(strSQL);
        if (drrayresult != null)
        {
            document.all('OccupationCodeName').value = drrayresult[0][0];
        }


    }

}

function queryinsured()
{
    var tPrtNo = fm.ProposalContNo.value;
	
			      var sqlid69="CardContInputSql69";
				var mySql69=new SqlClass();
				mySql69.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql69.setSqlId(sqlid69);//指定使用的Sql的id
				mySql69.addSubPara( tPrtNo);//指定传入的参数
			      var strSQL =mySql69.getString();
	
//    var strSQL = "select a.appntno,a.appntname,a.idtype,a.idno,a.appntsex,a.appntbirthday,a.marriage,a.occupationcode,(select b.grpname from lcaddress b where b.customerno=a.appntno and b.addressno=a.addressno ),a.addressno,(select c.province from lcaddress c where c.customerno=a.appntno and c.addressno=a.addressno ),(select d.city from lcaddress d where d.customerno=a.appntno and d.addressno=a.addressno ),(select e.county from lcaddress e where e.customerno=a.appntno and e.addressno=a.addressno ),(select f.postaladdress from lcaddress f where f.customerno=a.appntno and f.addressno=a.addressno ),(select g.zipcode from lcaddress g where g.customerno=a.appntno and g.addressno=a.addressno ) from lcappnt a where prtno='" + tPrtNo + "'";

    var crrayresult = easyExecSql(strSQL);
    if (crrayresult != null)
    {
        document.all('AppntNo').value = crrayresult[0][0];
        document.all('AppntName').value = crrayresult[0][1];
        document.all('AppntIDType').value = crrayresult[0][2];
        document.all('AppntIDNo').value = crrayresult[0][3];
        document.all('AppntSex').value = crrayresult[0][4];
        document.all('AppntBirthday').value = crrayresult[0][5];
        //document.all('AppntMarriage').value = crrayresult[0][6];
        document.all('AppntOccupationCode').value = crrayresult[0][7];
        document.all('AppntGrpName').value = crrayresult[0][8];
        document.all('AppntAddressNo').value = crrayresult[0][9];
        document.all('AppntProvince').value = crrayresult[0][10];
        document.all('AppntCity').value = crrayresult[0][11];
        document.all('AppntDistrict').value = crrayresult[0][12];
        document.all('AppntPostalAddress').value = crrayresult[0][13];
        document.all('AddressNoName').value = crrayresult[0][13];
        document.all('AppntZipCode').value = crrayresult[0][14];

        getAge();
        getdetailwork();
        showOneCodeName('idtype', 'AppntIDType', 'AppntIDTypeName');
        showOneCodeName('sex', 'AppntSex', 'AppntSexName');
        //showOneCodeName('marriage','AppntMarriage','AppntMarriageName');


			      var sqlid70="CardContInputSql70";
				var mySql70=new SqlClass();
				mySql70.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql70.setSqlId(sqlid70);//指定使用的Sql的id
				mySql70.addSubPara( document.all('AppntProvince').value);//指定传入的参数
				mySql70.addSubPara(  document.all('AppntCity').value);//指定传入的参数
				mySql70.addSubPara( document.all('AppntDistrict').value);//指定传入的参数
			     strSQL =mySql70.getString();

//        strSQL = "select (select a.placename from LDAddress a where a.placetype='01' and a.placecode='" + document.all('AppntProvince').value + "')," +
//                 "(select b.placename from LDAddress b where b.placetype='02' and b.placecode='" + document.all('AppntCity').value + "')," +
//                 "(select c.placename from LDAddress c where c.placetype='03' and c.placecode='" + document.all('AppntDistrict').value + "') from dual ";

        var drrayresult = easyExecSql(strSQL);
        if (drrayresult != null)
        {
            document.all('AppntProvinceName').value = drrayresult[0][0];
            document.all('AppntCityName').value = drrayresult[0][1];
            document.all('AppntDistrictName').value = drrayresult[0][2];
        }

			      var sqlid71="CardContInputSql71";
				var mySql71=new SqlClass();
				mySql71.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql71.setSqlId(sqlid71);//指定使用的Sql的id
				mySql71.addSubPara( document.all('AppntOccupationCode').value);//指定传入的参数
			     strSQL =mySql71.getString();

//        strSQL = "select trim(OccupationName) from LDOccupation where occupationcode='" + document.all('AppntOccupationCode').value + "' and worktype = 'GR' ";

        var drrayresult = easyExecSql(strSQL);
        if (drrayresult != null)
        {
            document.all('AppntOccupationCodeName').value = drrayresult[0][0];
        }

    }
}

function querybnf()
{
    //因为 受益人录入信息栏，“速填”字段提前，故调整了受益人查询语句的 字段顺序
    var tContNo = document.all('ContCardNo').value;
	
				      var sqlid72="CardContInputSql72";
				var mySql72=new SqlClass();
				mySql72.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql72.setSqlId(sqlid72);//指定使用的Sql的id
				mySql72.addSubPara(tContNo);//指定传入的参数
			       var tSql  =mySql72.getString();
	
//    var tSql = "select '',name,relationtoinsured,idtype,idno,bnfno,bnfgrade,'',1 from lcbnf where contno = '" + tContNo + "'";
    var turnPage = new turnPageClass();
    turnPage.queryModal(tSql, BnfGrid);

}

function queryrisk()
{
    var tContNo = document.all('ContCardNo').value;
	
				var sqlid73="CardContInputSql73";
				var mySql73=new SqlClass();
				mySql73.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql73.setSqlId(sqlid73);//指定使用的Sql的id
				mySql73.addSubPara(tContNo);//指定传入的参数
			     var tSql  =mySql73.getString();
	
//    var tSql = "select riskcode , mult,amnt,prem from lcpol where contno = '" + tContNo + "'";

    var xrrayresult = easyExecSql(tSql);
    if (xrrayresult != null)
    {
        document.all('RiskCode').value = xrrayresult[0][0];
        document.all('Mult').value = xrrayresult[0][1];
        document.all('Amnt').value = xrrayresult[0][2];
        document.all('Prem').value = xrrayresult[0][3];
    }

				var sqlid74="CardContInputSql74";
				var mySql74=new SqlClass();
				mySql74.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql74.setSqlId(sqlid74);//指定使用的Sql的id
				mySql74.addSubPara( document.all('RiskCode').value);//指定传入的参数
			     tSql  =mySql74.getString();

//    tSql = "select riskname from lmriskapp where poltype='C' and riskcode = '" + document.all('RiskCode').value + "'";
    var urrayresult = easyExecSql(tSql);
    document.all('RiskCodeName').value = urrayresult[0][0];

}


//根据险种初始化其他险种信息列表
function queryPolOtherGrid()
{
	var tRiskCode=trim(fm.RiskCode.value);
	
					var sqlid75="CardContInputSql75";
				var mySql75=new SqlClass();
				mySql75.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql75.setSqlId(sqlid75);//指定使用的Sql的id
				mySql75.addSubPara(tRiskCode);//指定传入的参数
			    var queryParamSQL  =mySql75.getString();
	
//	var queryParamSQL="select riskcode,dutycode,sourfieldname,sourfieldcname,destfieldcname"
//	 +" from lcpolotherfielddesc where riskcode='"+tRiskCode+"'";
	turnPage.queryModal(queryParamSQL,PolOtherGrid);
	if(PolOtherGrid.mulLineCount>=1)
	 {DivPolOtherGrid.style.display = "";}
	else
	 {DivPolOtherGrid.style.display = "none";}
}

//将其他险种信息列表的信息赋值到其他单独控件里
function setValueFromPolOtherGrid()
{
			  
	 var pName="";	  
   var pValue="";
	 try
	 {
		for (i = 0; i < PolOtherGrid.mulLineCount; i++)
		{
		  pName=PolOtherGrid.getRowColData(i, 4);
		  pValue=PolOtherGrid.getRowColData(i, 6);
			var evalStr="document.all('"+pName+"').value='"+pValue+"'";
			try{eval(evalStr); }catch(ex){};	  
		}
	 }
	 catch(ex)
	 {}
}


/**
* 计算投保人被保人年龄《投保日期与生日之差=投保人被保人年龄》,2005-11-18日添加
* 参数，出生日期yy－mm－dd；投保日期yy－mm－dd
* 返回  年龄
*/
function calPolAppntAge(BirthDate, PolAppntDate)
{
    var age = "";
    if (BirthDate == "" || PolAppntDate == "")
    {
        age = "";
        return age;
    }
    var arrBirthDate = BirthDate.split("-");
    if (arrBirthDate[1].length == 1) arrBirthDate[1] = "0" + arrBirthDate[1];
    if (arrBirthDate[2].length == 1) arrBirthDate[2] = "0" + arrBirthDate[2];
    //	alert("生日"+arrBirthDate);
    var arrPolAppntDate = PolAppntDate.split("-");
    if (arrPolAppntDate[1].length == 1) arrPolAppntDate[1] = "0" + arrBirthDate[1];
    if (arrPolAppntDate[2].length == 1) arrPolAppntDate[2] = "0" + arrBirthDate[2];
    //	alert("投保日期"+arrPolAppntDate);
    if (arrPolAppntDate[0] <= 99)
    {
        arrBirthDate[0] = arrBirthDate[0] - 1900;
    }
    age = arrPolAppntDate[0] - arrBirthDate[0] - 1;
    //当前月大于出生月
    //alert(arrPolAppntDate[1] + " | " + arrBirthDate[1] + " | " + (arrPolAppntDate[1] > arrBirthDate[1]));
    if (arrPolAppntDate[1] > arrBirthDate[1])
    {
        age = age + 1;
        return age;
    }
    //当前月小于出生月
    else if (arrPolAppntDate[1] < arrBirthDate[1])
    {
        return age;
    }
    //当前月等于出生月的时候，看出生日
    else if (arrPolAppntDate[2] >= arrBirthDate[2])
    {
        age = age + 1;
        return age;
    }
    else
    {
        return age;
    }
}

//根据地址代码查询地址汉字信息,查询地址代码表<ldaddress>
//所需参数,地址级别<province--省;city--市;district--区/县;>,地址代码<代码表单名>,地址汉字信息<汉字表单名>
//返回,直接为--地址汉字信息<汉字表单名>--赋值
function getAddressName(strCode, strObjCode, strObjName)
{
    var PlaceType = "";
    var PlaceCode = "";
    //判断地址级别,<province--省--01;city--市--02;district--区/县--03;>
    switch (strCode)
            {
        case "province":
            PlaceType = "01";
            break;
        case "city":
            PlaceType = "02";
            break;
        case "district":
            PlaceType = "03";
            break;
        default:
            PlaceType = "";
    }
    //遍历FORM中的所有ELEMENT
    for (formsNum = 0; formsNum < window.document.forms.length; formsNum++)
    {
        for (elementsNum = 0; elementsNum < window.document.forms[formsNum].elements.length; elementsNum++)
        {
            //寻找代码选择元素
            if (window.document.forms[formsNum].elements[elementsNum].name == strObjCode)
            {
                strObjCode = window.document.forms[formsNum].elements[elementsNum];
            }
            if (window.document.forms[formsNum].elements[elementsNum].name == strObjName)
            {
                strObjName = window.document.forms[formsNum].elements[elementsNum];
                break;
            }
        }
    }
    //如果代码栏的数据长度为[6]才查询，否则不做任何操作
    PlaceCode = strObjCode.value;
    //	strObjName.value="";
    if (strObjCode.value != "" && PlaceCode.length == 6)
    {
		
				var sqlid76="CardContInputSql76";
				var mySql76=new SqlClass();
				mySql76.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql76.setSqlId(sqlid76);//指定使用的Sql的id
				mySql76.addSubPara(PlaceCode);//指定传入的参数PlaceType
				mySql76.addSubPara(PlaceType);//指定传入的参数
			    var strSQL  =mySql76.getString();
		
//        var strSQL = "select placecode,placename from ldaddress where placecode='" + PlaceCode + "' and placetype='" + PlaceType + "'";
        var arrAddress = easyExecSql(strSQL);
        if (arrAddress != null)
        {
            strObjName.value = arrAddress[0][1];
        }

        else
        {
            strObjName.value = "";
        }
    }
}


/*************************************************************************************
在卡单签单保存时，系统生成的保费与收银录入的保费进行校验，
如果系统生成的保费和收银录入的保费不一致,签单不能通过
所需参数：合同号<投保单号>;     返回:true or false
*************************************************************************************/
function checkpayfee(ContNo)
{
    var tContNo = ContNo;
    var tTempFee = "0";
    //收银录入的保费
    var tPremFee = "0";
    //系统生成的保费
    //查询收银录入的保费
	
					var sqlid77="CardContInputSql77";
				var mySql77=new SqlClass();
				mySql77.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql77.setSqlId(sqlid77);//指定使用的Sql的id
				mySql77.addSubPara(tContNo);//指定传入的参数PlaceType
			    var tempfeeSQL  =mySql77.getString();
	
//    var tempfeeSQL = "select sum(nvl(paymoney,0)) from ljtempfee where tempfeetype='1' "
//    		+ " and (enteraccdate is not null and enteraccdate <>'3000-01-01') and confdate is null "
//            + " and otherno=(select contno from lccont where prtno= '" + tContNo + "')";
    var TempFeeArr = easyExecSql(tempfeeSQL);
    if (TempFeeArr != null)
    {
        tTempFee = TempFeeArr[0][0];
        if (tTempFee == null || tTempFee == "" || tTempFee == "null")
        {
            alert("查询该投保单收银录入的保费失败");
            return false;
        }
    }
    else
    {
        alert("查询该投保单收银录入的保费失败");
        return false;
    }
    //查询系统生成的保费
	
						var sqlid78="CardContInputSql78";
				var mySql78=new SqlClass();
				mySql78.setResourceName("app.CardContInputSql"); //指定使用的properties文件名
				mySql78.setSqlId(sqlid78);//指定使用的Sql的id
				mySql78.addSubPara(tContNo);//指定传入的参数PlaceType
			    var premfeeSQL  =mySql78.getString();
	
//    var premfeeSQL = "select sum(case when prem is not null then prem else 0 end) from lcpol where 1=1 "
//            + " and contno=(select contno from lccont where prtno= '" + tContNo + "')";
    var PremFeeArr = easyExecSql(premfeeSQL);
    tPremFee = PremFeeArr[0][0];
    if (PremFeeArr != null)
    {
        tPremFee = PremFeeArr[0][0];
        if (tPremFee == null || tPremFee == "" || tPremFee == "null")
        {
            alert("查询该投保单下险种信息及生成保费数据失败！！！");
            return false;
        }
    }
    else
    {
        alert("查询该投保单下险种信息及生成保费数据失败！！！");
        return false;
    }
    //比较“查询收银录入的保费” 和 “查询系统生成的保费”是否相等，如不相等则弹出信息提示，并返回false
    if (parseFloat(tTempFee) - parseFloat(tPremFee) != 0)
    {
        alert("系统生成的保费【" + parseFloat(tPremFee) + "】和收银录入的保费【" + parseFloat(tTempFee) + "】不一致,签单不能通过!");
        return false;
    }
    return true;
}

