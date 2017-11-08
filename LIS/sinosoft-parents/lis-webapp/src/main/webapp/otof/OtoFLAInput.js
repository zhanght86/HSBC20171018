//               该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var Action;
var tRowNo=0;

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
	fm.all('distill').disabled = false;
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






function SubmitFormAgent()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("请录入统计年和统计月！");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("统计年格式为“YYYY”，统计月格式为“MM”，请采用正确的格式");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构代码！");
			return ;
		}		
		if(comcode.length>6)
		{
			alert("管理机构不能低于三级！请录入三级或三级以上管理机构！");
			return ;
		}
  
        
    fm.Opt.value="Agent";
    fm.all('distill').disabled = true;
    fm.submit();
}

function SubmitFormReAgent()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("请录入统计年和统计月！");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("统计年格式为“YYYY”，统计月格式为“MM”，请采用正确的格式");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构代码！");
			return ;
		}		
    fm.Opt.value="ReAgent";
    fm.all('distill').disabled = true;
    fm.submit();
}


function SubmitFormReAgency()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("请录入统计年和统计月！");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("统计年格式为“YYYY”，统计月格式为“MM”，请采用正确的格式");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构代码！");
			return ;
		}		
    fm.Opt.value="ReAgency";
    fm.all('distill').disabled = true;
    fm.submit();
}

function SubmitFormAdminAgent()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("请录入统计年和统计月！");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("统计年格式为“YYYY”，统计月格式为“MM”，请采用正确的格式");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构代码！");
			return ;
		}		
    fm.Opt.value="AdminAgent";
    fm.all('distill').disabled = true;
    fm.submit();
}


function SubmitFormXiAgent()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("请录入统计年和统计月！");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("统计年格式为“YYYY”，统计月格式为“MM”，请采用正确的格式");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构代码！");
			return ;
		}		
    fm.Opt.value="XiAgent";
    fm.all('distill').disabled = true;
    fm.submit();
}



function SubmitFormLWage()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("请录入统计年和统计月！");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("统计年格式为“YYYY”，统计月格式为“MM”，请采用正确的格式");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构代码！");
			return ;
		}		
    fm.Opt.value="LWage";
    fm.all('distill').disabled = true;
    fm.submit();
}



function SubmitFormReB()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("请录入统计年和统计月！");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("统计年格式为“YYYY”，统计月格式为“MM”，请采用正确的格式");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构代码！");
			return ;
		}		
    fm.Opt.value="ReB";
    fm.all('distill').disabled = true;
    fm.submit();
}


function SubmitFormYCom()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("请录入统计年和统计月！");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("统计年格式为“YYYY”，统计月格式为“MM”，请采用正确的格式");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构代码！");
			return ;
		}		
    fm.Opt.value="YCom";
    fm.all('distill').disabled = true;
    fm.submit();
}



function SubmitFormXiAgent()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("请录入统计年和统计月！");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("统计年格式为“YYYY”，统计月格式为“MM”，请采用正确的格式");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构代码！");
			return ;
		}		
    fm.Opt.value="XiAgent";
    fm.all('distill').disabled = true;
    fm.submit();
}


function SubmitFormYAgent()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("请录入统计年和统计月！");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("统计年格式为“YYYY”，统计月格式为“MM”，请采用正确的格式");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构代码！");
			return ;
		}		
    fm.Opt.value="YAgent";
    fm.all('distill').disabled = true;
    fm.submit();
}

function SubmitFormAgeFee()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("请录入统计年和统计月！");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("统计年格式为“YYYY”，统计月格式为“MM”，请采用正确的格式");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构代码！");
			return ;
		}		
    fm.Opt.value="AgentFee";
    fm.all('distill').disabled = true;
    fm.submit();
}

function SubmitFormBanksFee()
{
		var statyear = fm.all('StatYear').value.trim();
		var statmon = fm.all('StatMon').value.trim();
		if(statyear == null || statyear == "" || statmon == null || statmon == "")
		{
			alert("请录入统计年和统计月！");
			return ;
		}
		
		if(statyear.length!=4 || statmon.length!=2)
		{
			alert("统计年格式为“YYYY”，统计月格式为“MM”，请采用正确的格式");
			return false;
		}
		
		var comcode = fm.all('ManageCom').value;
		if(comcode == null || comcode == "")
		{
			alert("请录入管理机构代码！");
			return ;
		}		
    fm.Opt.value="BanksFee";
    fm.all('distill').disabled = true;
    fm.submit();
}