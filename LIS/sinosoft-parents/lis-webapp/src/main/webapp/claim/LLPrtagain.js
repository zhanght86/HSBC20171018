var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//初始化查询
function initLoprtQuery()
{
	//查询[打印管理表----LOPRTManager]
    /*var strSql="select t.prtseq,t.otherno,t.code,t.reqoperator,t.reqcom,t.managecom,t.exeoperator,t.prttype,t.stateflag from loprtmanager t where 1=1 "
			+" and t.prtseq='"+fm.PrtSeq.value+"' ";*/
			
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLPrtagainInputSql");
	mySql.setSqlId("LLPrtagainSql1");
	mySql.addSubPara(fm.PrtSeq.value ); 
	var arrLoprt = easyExecSql(mySql.getString());
	fm.PrtSeq.value=arrLoprt[0][0];
	fm.OtherNo.value=arrLoprt[0][1];
	fm.Code.value=arrLoprt[0][2];
	fm.ReqOperator.value=arrLoprt[0][3];
	fm.ReqCom.value=arrLoprt[0][4];
	showOneCodeName('stati','ReqCom','ReqComName')
	fm.ManageCom.value=arrLoprt[0][5];
	fm.ExeOperator.value=arrLoprt[0][6];
	fm.PrtType.value=arrLoprt[0][7];
	fm.StateFlag.value=arrLoprt[0][8];
	
	fm.PrtCode.value=fm.PrtSeq.value;
	fm.ClmNo.value=fm.OtherNo.value;
}

//[补打原因保存按钮]
function showPrtagainReasion()
{
	if(trim(fm.Remark.value)==null || trim(fm.Remark.value)=="")
	{
		alert("请录入补打原因！");
		return;
	}
	fm.action = './LLPrtagainSave.jsp';  
	submitForm();
}

//[补打单证按钮]-----根据不同的单证代码调用不同Save页面
function showPrtAffix()
{
	var tcode=fm.Code.value;
	//alert("prtType1= "+fm.prtType1.value);return false;
	if(tcode=="PCT001"){fm.action = './LLPRTAppraisalSave.jsp';}
	else if(tcode=="PCT002"){fm.action = './LLPRTCertificatePutOutSave.jsp';}
	else if(tcode=="PCT003"){fm.action = './LLPRTCertificateRenewSave.jsp';}
	else if(tcode=="PCT008"){fm.action = './LLPRTPatchFeeSave.jsp';}
	else if(tcode=="PCT007"){fm.action = './LLPRTProtestNoRegisterSave.jsp';}
	else if(tcode=="PCT013"){fm.action = './LLClaimEndCasePrintContSave.jsp';}
	else if(tcode=="PCT005" || tcode=="PCT006"){fm.action = './LLClaimEndCaseProtestPrint.jsp';}
	else {fm.action = './LLClaimEndCaseParaPrint.jsp';}
	fm.target = "../f1print";
	fm.submit();
}

//[返回]
function goToBack()
{
	top.close();
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
    fm.target="fraSubmit";
    fm.submit(); //提交
    tSaveFlag ="0";
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit(FlagStr,content )
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
        fm.Prtagain.disabled=false;
    }
    tSaveFlag ="0";
}