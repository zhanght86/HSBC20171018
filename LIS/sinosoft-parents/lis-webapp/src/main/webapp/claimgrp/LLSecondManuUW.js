//程序名称：在LLSecondManuUW.js
//程序功能：个人人工核保
//创建日期：2002-09-24 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容

//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
//[清除页面上的数据]
function clearPageData()
{
	//[投保人信息]部分
 	fm.AppntName.value = "";
	fm.AppntSex.value = "";
	fm.AppntBirthday.value = "";
	fm.OccupationCode.value = "";
	fm.NativePlace.value ="";
	fm.VIPValue.value = "";
	fm.BlacklistFlag.value = "";
	
	//[信息----投保书健康告知栏询问号,体检健康告知栏询问号,对应未告知情况,出险人健康状况告知内容]
 	fm.HealthImpartNo1.value = "";
	fm.HealthImpartNo2.value = "";
	fm.NoImpartDesc.value = "";
	fm.Remark1.value = "";	
	
	//[合同详细信息]部分
    fm.PrtNo.value="";
    fm.ProposalContNo.value="";//	    
    fm.ManageCom.value="";
    fm.SaleChnl.value="";
    fm.AgentCode.value="";
    fm.AgentCom.value="";
    fm.Remark.value="";	
}

//[初始化查询保单合同列表]
function initLLCUWBatchGridQuery()
{	
	var strSQL="select t.contno,t.batno,t.appntno,t.appntname,t.insuredno,t.insuredname,t.managecom"
		+ " from llcuwbatch t where 1=1 "
		+ " and t.caseno='"+ document.all('tCaseNo').value +"' "
		+ " and t.batno='"+document.all('tBatNo').value +"'" 
		+ " and t.claimrelflag='"+document.all('tClaimRelFlag').value +"'"
		+ " order by t.contno";
    turnPage.queryModal(strSQL, LLCUWBatchGrid);		
}

//[LLCUWBatchGrid]列表单选钮单击响应函数     
function LLCUWBatchGridClick()
{
//	alert("待完善");
//	return;
	clearPageData();
	var tSelNo =LLCUWBatchGrid.getSelNo()-1;
	var tCaseNo=fm.tCaseNo.value;
	var tBatNo=fm.tBatNo.value;
	var tContNo = LLCUWBatchGrid.getRowColData(tSelNo,1);
	fm.tContNo.value= LLCUWBatchGrid.getRowColData(tSelNo,1);
	
	//[查询合同表详细信息]-------lccont表
	var strLCcont = "select prtno,proposalcontno,managecom,salechnl,agentcode,agentcom,remark from lccont where contno='"+tContNo+"'";
    var arrLCcont=easyExecSql(strLCcont);
    if(arrLCcont!=null)
    {
	    fm.PrtNo.value=arrLCcont[0][0];//
 		fm.ProposalContNo.value=arrLCcont[0][1];//	    
	    fm.ManageCom.value=arrLCcont[0][2];//
	    fm.SaleChnl.value=arrLCcont[0][3];//
	    fm.AgentCode.value=arrLCcont[0][4];//
	    fm.AgentCom.value=arrLCcont[0][5];//
	    fm.Remark.value=arrLCcont[0][6];//    	
    }
	//[查询---投保书健康告知栏询问号,体检健康告知栏询问号,对应未告知情况,出险人健康状况告知内容-]
	var strllcuwmatch="select healthimpartno1,healthimpartno2,noimpartdesc,remark1 from llcuwbatch where 1=1 "
				+" and caseno= '"+ tCaseNo +"'"
			    +" and batno= '"+ tBatNo+"'"
				+" and contno= '"+ tContNo +"'"; 
	var arrLLcuwatch=easyExecSql(strllcuwmatch);
//	alert(arrLLcuwatch);	
	 if(arrLLcuwatch!=null)
    {
	 	fm.HealthImpartNo1.value =arrLLcuwatch[0][0];
		fm.HealthImpartNo2.value =arrLLcuwatch[0][1];
		fm.NoImpartDesc.value =arrLLcuwatch[0][1];
		fm.Remark1.value =arrLLcuwatch[0][3];	
    }		
	
    //[查询 投保人信息]
    strLCAppnt="select a.appntno,a.appntname,a.appntsex,a.appntbirthday,a.occupationcode,a.nativeplace,b.vipvalue,b.blacklistflag"
    	+" from lcappnt a,ldperson b where 1=1"
    	+" and b.customerno = a.appntno"
    	+" and a.contno='"+tContNo+"'";
    var arrLCcont=easyExecSql(strLCAppnt);
    if(arrLCcont!=null)
    {
	 	document.all('AppntNo').value = arrLCcont[0][0];    	
	 	document.all('AppntName').value = arrLCcont[0][1];
		document.all('AppntSex').value = arrLCcont[0][2];
		document.all('AppntBirthday').value = calAge(arrLCcont[0][3]);
		document.all('OccupationCode').value = arrLCcont[0][4];
		document.all('NativePlace').value = arrLCcont[0][5];
		document.all('VIPValue').value = arrLCcont[0][6];
		document.all('BlacklistFlag').value = arrLCcont[0][7];        	
    }
   
}

//[投保单明细]按钮
function showPolDetail()
{
	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("请先选择保单!");
		return;
	}
	  var cContNo = fm.ProposalContNo.value;
	  var cPrtNo = fm.PrtNo.value;
	  window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+cPrtNo+"&ContNo="+cContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");  	  
}  

//[扫描件查询]按钮
function ScanQuery()
{
	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("请先选择保单!");
		return;
	}
	var prtNo = fm.PrtNo.value;	
	window.open("./LCcontQueryImageMain.jsp?prtNo="+prtNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1");	
}

//[投保人既往投保信息]按钮
function showApp(cindex)
{
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		showInfo.close();
		alert("请先选择保单!");
		return;
	}
	var cContNo=fm.tContNo.value;
	var cAppntNo = fm.AppntNo.value;
	if (cindex == 1)
	{
		window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1");
	}
	else
	{
		window.open("../uw/UWAppFamilyMain.jsp?ContNo="+cContNo);
	}
	showInfo.close();

}   

//体检资料
function showHealth()
{
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		showInfo.close();
		alert("请先选择保单!");
		return;
	}
	var tContNo = LLCUWBatchGrid.getRowColData(tSelNo,1);	
	window.open("../uw/GrpUWManuHealthMain.jsp?ContNo1="+tContNo,"window1");
	showInfo.close();
}     


//生存调查报告
function showRReport()
{
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		showInfo.close();
		alert("请先选择保单!");
		return;
	}
	var tContNo = LLCUWBatchGrid.getRowColData(tSelNo,1);	
	window.open("../uw/GrpUWManuRReportMain.jsp?ContNo1="+tContNo,"window2");  	
	showInfo.close();
                      
}      

//[记事本]按钮
function showNotePad()
 {  
	var tSelNo = LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("请先选择保单!");
		return;
	}
	ContNo = LLCUWBatchGrid.getRowColData(tSelNo,1);	
	window.open("../uw/UWNotePadMain.jsp?ContNo="+ContNo+"&OperatePos=3", "window1");
}

//[进入险种信息]按钮
function enterRiskInfo()
{
	var tSelNo =LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("请选择一条记录！");
		return;
	}
	var tContNo =LLCUWBatchGrid.getRowColData(tSelNo,1);	 
	var tInsuredNo=document.all('tInsuredNo').value;
	var tCaseNo=document.all('tCaseNo').value;
	var tBatNo=document.all('tBatNo').value;
    var strUrl="./LLSecondUWRiskMain.jsp?ScanFlag=0&ContNo="+tContNo+"&InsuredNo="+tInsuredNo+"&CaseNo="+tCaseNo+"&BatNo="+tBatNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"进入险种信息");
}
//[取消]按钮
function uwCancelClick()
{
	document.all('uwState').value = "";
	document.all('uwStatename').value = "";	
	document.all('UWIdea').value = "";
}

//[返回]按钮
function turnBack()
{
	window.location="./LLSecondUWAllInput.jsp";
}

//[确定]按钮 ----提交"核保结论"，保存按钮对应操作
function uwSaveClick()
{		
	var tSelNo =LLCUWBatchGrid.getSelNo()-1;
	if(tSelNo<0)
	{
		alert("请选择一条记录！");
		return;
	}	
//	var tUWState = fm.uwState.value;                  //核保结论
//	var tUWIdea = fm.UWIdea.value;                    
	if(fm.uwState.value == "")
	{
		alert("请先录入承保核保结论!");      
		return ;
	}
	fm.action = "./LLSecondManuUWSave.jsp";
	submitForm(); //提交
}

//【核保完成】---结束任务，将任务分别转向不同队列《》
//赔案相关标志(ClaimRelFlag),0----相关，任务返回理赔岗（只消亡本节点）
//赔案相关标志(ClaimRelFlag),1----无关，任务返回到保全岗《以单个保单的形式返回到保全队列》（消亡本节点，生成多个保全节点）
function llSecondUWFinish()
{
	var tClaimRelFlag=fm.tClaimRelFlag.value;
	if(tClaimRelFlag=="0")  
	{
		fm.fmtransact.value="Finish||ToClaim";
//		alert("与赔案有关，核保完成，转向理赔岗");
//		return;		
		fm.action = "./LLSecondManuUWFinishSave.jsp";
		submitForm(); //提交
	}
	if(tClaimRelFlag=="1")  
	{
		fm.fmtransact.value="Finish||ToWFEdor";
//		alert("与赔案无关，核保完成，转向保全岗");
//		return;		
		fm.action = "./LLSecondManuUWFinishSave.jsp";
		submitForm(); //提交		
	}

}


//【提交数据】-----通过Save页面向后台提交数据
function submitForm()
{
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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

//【核保结论】-----提交数据后操作,返回成功或失败信息（原因）
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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


//【核保完成】-----提交数据后操作,返回成功或失败信息（原因）
function UWFinishafterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        tSaveFlag ="0";
		turnBack();
    }

}