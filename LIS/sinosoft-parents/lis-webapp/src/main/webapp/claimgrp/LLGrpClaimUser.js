var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();

// 清除页面上的数据
function ClearPagedata()
{
    fm.UserCode.value ="";
    fm.UserName.value ="";
    fm.ComCode.value = "";
    fm.ReportFlag.value ="";
    fm.RegisterFlag.value ="";
    fm.SubmitFlag.value = "";
    fm.SurveyFlag.value ="";
    fm.PrepayFlag.value = "";
    fm.SimpleFlag.value ="";
    fm.CheckFlag.value = "";
    fm.CheckLevel.value ="";
    fm.UWFlag.value ="";
    fm.UWLevel.value ="";
    //fm.StateFlag.value ="";

    fm.ComCodeName.value = "";    
    fm.ReportFlagName.value ="";
    fm.RegisterFlagName.value ="";
    fm.SubmitFlagName.value = "";
    fm.SurveyFlagName.value ="";
    fm.PrepayFlagName.value = "";
    fm.SimpleFlagName.value ="";
    fm.CheckFlagName.value = "";
    fm.CheckLevelName.value ="";
    fm.UWFlagName.value ="";
    fm.UWLevelName.value ="";
    //fm.StateFlagName.value ="";
    fm.RelPhone.value ="";
    
    fm.UpUserCode.value="";
    fm.UpUserName.value="";
    fm.JobCategory.value="";
    fm.JobCategoryName.value="";
    fm.CertifyScanName.value="";
    fm.TaskAssign.value="";
    fm.ExemptFlag.value="";
    fm.RgtFlag.value="";
    fm.CertifyScan.value="";
    fm.TaskAssignName.value="";
    fm.ExemptFlagName.value="";
    fm.RgtFlagName.value="";
}

function ClearQuerydataClick()
{
	fm.UserCodeQ.value ="";
    fm.UserNameQ.value ="";
    fm.ComCodeQ.value = "";
    fm.ComCodeQName.value = "";
}

// 查询按钮
function InitQueryClick()
{
  ClearPagedata();//初始化页面表单控件信息

    
    //------------------------------------------------------------------------------  
	var strSQL="select usercode,username,comcode,JobCategory,(select codename from ldcode where codetype='grpjobcategory' and code=JobCategory),"
				 + "upusercode,(select username from lduser where usercode=upusercode),reportflag,registerflag,submitflag,surveyflag,"
				 + "prepayflag,simpleflag,CertifyScan,TaskAssign,checkflag,checklevel,uwflag,uwlevel,ExemptFlag,RgtFlag,relphone"
				 + " from llgrpclaimuser where 1=1 "
				 + getWherePart( 'UserCode','UserCodeQ')
				 + getWherePart( 'UserName','UserNameQ')
				 + getWherePart( 'ComCode','ComCodeQ')
				 + getWherePart( 'RgtFlag','RgtFlagQ')				 
				 + getWherePart( 'JobCategory','JobCategoryQ')	
				 + " order by UserCode";
	
	//prompt("团险理赔用户查询:",strSQL);
//------------------------------------------------------------------------------End  
	turnPage.queryModal(strSQL, LLGrpClaimUserGrid);        
}

/*理赔权限表（LLGrpClaimUserGrid）的触发函数*/
function LLGrpClaimUserGridClick()
{
    ClearPagedata();
    fm.editUserButton.disabled =false;
    fm.deleteUserButton.disabled =false;  
    fm.saveUserButton.disabled =true;      
    //得到理赔权限表选中的行号
    var tNo = LLGrpClaimUserGrid.getSelNo()-1;
    
    //赋值信息
    fm.UserCode.value = LLGrpClaimUserGrid.getRowColData(tNo,1);
    fm.UserName.value = LLGrpClaimUserGrid.getRowColData(tNo,2);
    fm.ComCode.value = LLGrpClaimUserGrid.getRowColData(tNo,3);
    
    fm.JobCategory.value = LLGrpClaimUserGrid.getRowColData(tNo,4);
    fm.JobCategoryName.value = LLGrpClaimUserGrid.getRowColData(tNo,5);
    
    fm.UpUserCode.value = LLGrpClaimUserGrid.getRowColData(tNo,6);
    fm.UpUserName.value = LLGrpClaimUserGrid.getRowColData(tNo,7);
    
    fm.ReportFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,8);
    fm.RegisterFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,9);
    fm.SubmitFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,10);
    
    fm.SurveyFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,11);
    fm.PrepayFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,12);
    fm.SimpleFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,13);   
    
    fm.CertifyScan.value = LLGrpClaimUserGrid.getRowColData(tNo,14);
    fm.TaskAssign.value = LLGrpClaimUserGrid.getRowColData(tNo,15);     
    
    fm.CheckFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,16);
    fm.CheckLevel.value = LLGrpClaimUserGrid.getRowColData(tNo,17);
    fm.UWFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,18);
    fm.UWLevel.value = LLGrpClaimUserGrid.getRowColData(tNo,19);
    
    fm.ExemptFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,20);
    fm.RgtFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,21);
    //fm.StateFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,14);
    fm.RelPhone.value = LLGrpClaimUserGrid.getRowColData(tNo,22);

//-------------------------------------------------------------------------------Beg
//添加在岗状态
//修改人：zhouming
//修改日期：2006-04-30
    //------------------------------------------------------------------------------
    //fm.RgtFlag.value = LLGrpClaimUserGrid.getRowColData(tNo,17);
//------------------------------------------------------------------------------End    
    
    showOneCodeName('stati','ComCode','ComCodeName');  
    showOneCodeName('ReportFlag','ReportFlag','ReportFlagName');
    showOneCodeName('RegisterFlag','RegisterFlag','RegisterFlagName'); 
    showOneCodeName('SubmitFlag','SubmitFlag','SubmitFlagName');
    showOneCodeName('CertifyScan','CertifyScan','CertifyScanName');
    showOneCodeName('TaskAssign','TaskAssign','TaskAssignName');
    showOneCodeName('RgtFlag','RgtFlag','RgtFlagName');
    showOneCodeName('SurveyFlag','SurveyFlag','SurveyFlagName');        
    showOneCodeName('PrepayFlag','PrepayFlag','PrepayFlagName');
    showOneCodeName('SimpleFlag','SimpleFlag','SimpleFlagName');    
    showOneCodeName('CheckFlag','CheckFlag','CheckFlagName');
    //showOneCodeName('popedomlevel','CheckLevel','CheckLevelName');    
    showOneCodeName('UWFlag','UWFlag','UWFlagName');
    //showOneCodeName('popedomlevel','UWLevel','UWLevelName'); 
    //showOneCodeName('StateFlag','StateFlag','StateFlagName');       
//-------------------------------------------------------------------------------Beg
//添加在岗状态
//修改人：zhouming
//修改日期：2006-04-30
    //------------------------------------------------------------------------------
   // showOneCodeName('llclaimuserflag','RgtFlag','RgtFlagName');       
//------------------------------------------------------------------------------End    
    
}



/*[增加]按钮对应操作*/
function UserAddClick()
{
	if(fm.UserCode.value ==""||fm.UserName.value ==""||fm.ComCode.value == "")
	{
		alert("请先查询选择用户");
		return;
	}
	
	if(fm.UpUserCode.value ==""||fm.UpUserName.value =="")
	{
		alert("请录入上级用户编码和姓名");
		return;
	}
	
	if(fm.JobCategory.value ==null||fm.JobCategory.value =="")
	{
		alert("请选择权限级别");
		return;
	}
	
	if(fm.RgtFlag.value ==null||fm.RgtFlag.value =="")
	{
		alert("请选择在岗状态!");
		return;
	}
	
    fm.fmtransact.value="User||INSERT";
    submitUserForm();
}
/*[修改]按钮对应操作*/
function UserEditClick()
{
//	if(fm.CheckFlag.value=='1'&&(fm.CheckLevel.value==""||fm.CheckLevel.value==null))
//	{//Modify by zhaorx 2006-04-26
//		alert("请您选择审核级别！");
//		return;
//	}
//	if(fm.UWFlag.value=='1'&&(fm.UWLevel.value==""||fm.UWLevel.value==null))
//	{
//		alert("请您选择审批级别！");
//		return;
//	}	
    if (confirm("您确实想修改该记录吗?"))
    {
	    fm.fmtransact.value="User||UPDATE";
	    submitUserForm();
    }
    else
    {
        fm.fmtransact.value="";
        return;        
    }

}
/*[删除]按钮对应操作*/
function UserDeleteClick()
{
    if (confirm("您确实想删除该记录吗?"))
    {
        fm.fmtransact.value="User||DELETE";
        submitUserForm();
    }
    else
    {
        fm.fmtransact.value="";
        return;
    }
}

/*[重置]按钮对应操作*/
function UserResetClick()
{
	ClearPagedata();
    fm.editUserButton.disabled = true;
    fm.deleteUserButton.disabled = true;
    fm.saveUserButton.disabled = false;     
}

//用户信息查询
function　userQueryClick()
{
	var strUrl="LLUserQueryMain.jsp";
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//用户默认的权限---没有权限（用户增加用户时）
function DefaultPopedom() 
{
    fm.ReportFlag.value ="0";
    fm.RegisterFlag.value ="0";
    fm.SubmitFlag.value = "0";
    fm.SurveyFlag.value ="0";
    fm.PrepayFlag.value = "0";
    fm.SimpleFlag.value ="0";
    fm.CheckFlag.value = "0";
    fm.UWFlag.value ="0";
    //fm.StateFlag.value ="1";	
    
    showOneCodeName('ReportFlag','ReportFlag','ReportFlagName');
    showOneCodeName('RegisterFlag','RegisterFlag','RegisterFlagName');    
    showOneCodeName('SubmitFlag','SubmitFlag','SubmitFlagName');
    showOneCodeName('SurveyFlag','SurveyFlag','SurveyFlagName');        
    showOneCodeName('PrepayFlag','PrepayFlag','PrepayFlagName');
    showOneCodeName('SimpleFlag','SimpleFlag','SimpleFlagName');    
    showOneCodeName('CheckFlag','CheckFlag','CheckFlagName');
    showOneCodeName('UWFlag','UWFlag','UWFlagName');
    //showOneCodeName('StateFlag','StateFlag','StateFlagName');  
}
//用户查询返回信息（用户编码 、用户姓名、机构编码）
function afterQuery( arrQueryResult ) 
{
    fm.saveUserButton.disabled = false;      
    fm.editUserButton.disabled = true;
    fm.deleteUserButton.disabled = true;    
    var arrResult = new Array();
    if( arrQueryResult != null ) 
    {
    	ClearPagedata();
        arrResult = arrQueryResult;
		fm.UserCode.value = arrQueryResult[0][0];
		fm.UserName.value = arrQueryResult[0][1];
		fm.ComCode.value = arrQueryResult[0][2];    
		showOneCodeName('stati','ComCode','ComCodeName'); 
	    DefaultPopedom();   //用户默认的权限---没有权限（用户增加用户时） 
	}
}
/*[公共函数]提交到后台*/
function submitUserForm()
{
  	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.action = "./LLGrpClaimUserSave.jsp";
    document.getElementById("fm").submit(); //提交

}


/*[公共函数]交后操作,服务器数据返回后执行的操作*/
function afterUserSubmit( FlagStr, content )
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

        InitQueryClick();//刷新页面
    }

}

