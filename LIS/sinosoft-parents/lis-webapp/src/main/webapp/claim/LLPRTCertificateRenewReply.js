//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
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
    }
    tSaveFlag ="0";
}

function initqueryGrid()
{
	var strSQLinit = "";
	/*
	strSQLinit = "Select a.RgtNo,a.IssueBackDate,a.IssueBacker,(Case a.IssueStage When '1' Then '初审' When '2' Then '立案' end)"
    	   + " From LLRegisterIssue a where 1=1"
           + " and a.IssueBacker = '" + fm.Operator.value + "' and a.issueconclusion='02' and a.IssueReplyDate is null"
           + " and exists (select 1 from LLAffix where rgtno=a.rgtno and SupplyStage='01' and ReAffixDate is null and AffixState='02' and (subflag is null or subflag='1'))"
           + " and exists (select 1 from loprtmanager where otherno=a.RgtNo and code='PCT003' And stateflag= '1') order by a.RgtNo"
   */
   mySql = new SqlClass();
	mySql.setResourceName("claim.LLPRTCertificateRenewReplyInputSql");
	mySql.setSqlId("LLPRTCertificateRenewReplySql1");
	mySql.addSubPara(fm.Operator.value );
    turnPage.queryModal(mySql.getString(),LLRegisterIssueGrid);
}


// 初始化表格1
function queryGrid()
{
	
	if (fm.Rgtno.value == ""
	       && fm.IssueBackCom.value == ""
	       && fm.IssueBackStartDate.value == ""
	       && fm.IssueBackEndDate.value == "" )
	{
	        alert("请至少输入一个条件!");
	        return false;
	}
	initLLRegisterIssueGrid();
    var strSQL = "";
    /*
    strSQL = "Select a.RgtNo,a.IssueBackDate,a.IssueBacker,(Case a.IssueStage When '1' Then '初审' When '2' Then '立案' end)"
    	   + " From LLRegisterIssue a where 1=1"
           + " and a.IssueBackCom like '" + document.all('ManageCom').value +"%' and a.issueconclusion='02' and a.IssueReplyDate is null"
           + " and exists (select 1 from LLAffix where rgtno=a.rgtno and SupplyStage='01' and ReAffixDate is null and AffixState='02' and (subflag is null or subflag='1'))"
           + " and exists (select 1 from loprtmanager where otherno=a.RgtNo and code='PCT003' And stateflag= '1')"
           + getWherePart( 'a.RgtNo' ,'Rgtno');
    */
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLPRTCertificateRenewReplyInputSql");
	mySql.setSqlId("LLPRTCertificateRenewReplySql2");
	mySql.addSubPara(document.all('ManageCom').value );
	mySql.addSubPara(fm.Rgtno.value );
	
	
    if (fm.IssueBackCom.value != "" && fm.IssueBackCom.value != null)    
    {
    	//strSQL =  strSQL + " and a.IssueBackCom like '" + fm.IssueBackCom.value + "%'";
	   	mySql = new SqlClass();
		mySql.setResourceName("claim.LLPRTCertificateRenewReplyInputSql");
		mySql.setSqlId("LLPRTCertificateRenewReplySql3");
		mySql.addSubPara(document.all('ManageCom').value );
		mySql.addSubPara(fm.Rgtno.value );
		mySql.addSubPara(fm.IssueBackCom.value );
    }
           
    if (fm.IssueBackStartDate.value != "" && fm.IssueBackStartDate.value != null)    
    {
    	//strSQL =  strSQL + "and a.IssueBackDate>='" + fm.IssueBackStartDate.value + "'";
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLPRTCertificateRenewReplyInputSql");
		mySql.setSqlId("LLPRTCertificateRenewReplySql4");
		mySql.addSubPara(document.all('ManageCom').value );
		mySql.addSubPara(fm.Rgtno.value );
		//mySql.addSubPara(fm.IssueBackCom.value );
		mySql.addSubPara(fm.IssueBackStartDate.value );
    }
    
    if (fm.IssueBackEndDate.value != "" && fm.IssueBackEndDate.value != null)    
    {
    	//strSQL =  strSQL + "and a.IssueBackDate<='" + fm.IssueBackEndDate.value + "'";
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLPRTCertificateRenewReplyInputSql");
		mySql.setSqlId("LLPRTCertificateRenewReplySql5");
		mySql.addSubPara(document.all('ManageCom').value );
		mySql.addSubPara(fm.Rgtno.value );
		mySql.addSubPara(fm.IssueBackCom.value );
		mySql.addSubPara(fm.IssueBackStartDate.value );
		mySql.addSubPara(fm.IssueBackEndDate.value );
    }
    	
    	
    //strSQL = strSQL + " order by a.RgtNo";
          
    turnPage.queryModal(mySql.getString(),LLRegisterIssueGrid);
}




//LLReportGrid点击事件 ：单证回销
function LLRegisterIssueGridClick()
{
	//取得赔案号
	var tClmNo = "";
    var i = LLRegisterIssueGrid.getSelNo();
    if(i < 1)
    {
    	alert("请选中一行记录！");
    	return;
    }	
    else
    {
        i = i - 1;
        tClmNo = LLRegisterIssueGrid.getRowColData(i,1);
    }

        var strUrl="LLRgtMAffixListIssueMain.jsp?ClmNo="+tClmNo+"&Proc=Rgt";
        window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');  
        //window.open(strUrl,"单证回销");

}
