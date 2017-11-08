//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
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

    if(fm.IssueConclusion.value == "01")
    {

    	 //var tSql = " select MissionID,SubMissionID,ActivityID from lwmission where missionprop1 = '"+document.all('ClmNo').value+"' and activityid='0000005010'";
    	 mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterAuditInputSql");
		mySql.setSqlId("LLClaimRegisterAuditSql1");
		mySql.addSubPara( document.all('ClmNo').value);
         arr = easyExecSql( mySql.getString() );
    	// location.href="../claim/LLClaimScanRegisterInput.jsp?claimNo="+document.all('ClmNo').value+"&isNew=0&MissionID="+arr[0][0]+"&SubMissionID="+arr[0][1]+"&ActivityID="+arr[0][2];
    	window.open("./LLClaimScanRegisterMain.jsp?claimNo="+document.all('ClmNo').value+"&isNew=0&prtNo="+document.all('ClmNo').value+"&SubType=CA001&MissionID="+arr[0][0]+"&SubMissionID="+arr[0][1]+"&ActivityID="+arr[0][2]," ","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
    }
    else
    {
    	goToBack();
    }
}

//客户查询
function showInsPerQuery()
{
	var strUrl="LLLdPersonQueryMain.jsp?Flag=1";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open("LLLdPersonQueryMain.jsp");
}

//保单查询
//按照”客户号“在LCpol中进行查询，显示该客户的保单明细
function showInsuredLCPol()
{
  var strUrl="LLLcContQueryMainClmScan.jsp";
  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//影像查询---------------2005-08-14添加
function colQueryImage()
{
    var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('ClmNo').value+"&subtype=LP1001";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//立案单证补充
function showRgtAddMAffixListClmScan()
{
//    var row = SubReportGrid.getSelNo();
//    if(row < 1)
//    {
//        alert("请选中一行记录！");
//        return;
//    }
    var tClmNo=fm.ClmNo.value;
//    var tcustomerNo=fm.CustomerNo.value;
    var strUrl="LLRgtAddMAffixListClmScanMain.jsp?ClmNo="+tClmNo+"&Proc=RgtAdd";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');  
    //window.open(strUrl,"单证补充");
}

function IssueConclusionSave()
{

    if(fm.IssueConclusion.value == "")
    {
        alert("请选择初审结论！");
        return;
    }
	
	if(fm.IssueConclusion.value == "02")
	{
		//var strSQL="select 1 from LLAffix where rgtno='" + fm.ClmNo.value + "' and SupplyStage='01'";
		    	 mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterAuditInputSql");
		mySql.setSqlId("LLClaimRegisterAuditSql2");
		mySql.addSubPara(fm.ClmNo.value);
		var LLAffixExist = easyExecSql(mySql.getString());
		if(LLAffixExist == null || LLAffixExist == "")
		{
			alert("选择了材料不齐退回结论,但未补充单证，不能进行初审结论保存！");
			return false;
		}
	}
	else if (fm.IssueConclusion.value == "03")
	{
		if(fm.AccDesc.value == null || fm.AccDesc.value =="")
		{
			alert("选择了其他退回结论,但未录入退回说明，不能进行初审结论保存！");
			return false;
		}
	}

		fm.Operate.value="Insert";
		submitForm();

}

function submitForm()
{
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.submit();  
}

//返回按钮
function goToBack()
{
    location.href="LLClaimScanRegisterMissInput1.jsp";
}

function choiseIssueConclusion()
{
	  if (fm.IssueConclusion.value == '02')
		{
		    divAffix.style.display= "";
		    divAccDesc.style.display= "";
	    }
	  else if (fm.IssueConclusion.value == '03')
	    {
	    	divAffix.style.display= "none";
	    	divAccDesc.style.display= "";
	    }
	  else 
	  {
		    divAffix.style.display= "none";
		    divAccDesc.style.display= "none";
	  }
}
