var showInfo;
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mOperate = "";


/*提交后台前的页面数据校验*/
function beforePopedomSubmit()
{
		var AccDate1 = fm.StartDate.value;//启用日期
		var AccDate2 = fm.EndDate.value;//结束日期
		var AccYear1 = AccDate1.substring(0,4);
    var AccMonth1 = AccDate1.substring(5,7);
    var AccDay1 = AccDate1.substring(8,10);
    var AccYear2 = AccDate2.substring(0,4);
    var AccMonth2 = AccDate2.substring(5,7);
    var AccDay2 = AccDate2.substring(8,10);
    
    if (AccDate1 == null || AccDate1 == '')
    {
        alert("启用日期不能为空！");
        return false;
    }
    
    
    if (AccDate2 != null && AccDate2 != '')
    {
	   	if (AccYear1 > AccYear2)
	    {
	        alert("启用日期不能晚于结束日期");
	        return false;
	    }
	   	else if (AccYear1 == AccYear2)
	    {
	        if (AccMonth1 > AccMonth2)
	        {
	
	            alert("启用日期不能晚于结束日期");
	            return false;
	        }
	        else if (AccMonth1 == AccMonth2 && AccDay1 > AccDay2)
	        {
	            alert("启用日期不能晚于结束日期");
	            return false;
	        }
	    }
	}
    
    var BaseMin = parseFloat(fm.BaseMin.value);//最小金额
    var BaseMax = parseFloat(fm.BaseMax.value);//最大金额
    
  	if (BaseMin == null || BaseMin == '')
  	{
  			alert("最小金额不能为空");
    		return false;
  	}
  	
  	if (BaseMax == null || BaseMax == '')
  	{
  			alert("最大金额不能为空");
    		return false;
  	}
  	
  	
    if (BaseMin > BaseMax)
    {
    		alert("最小金额不能大于最大金额");
    		return false;
  	} 
    
  	var JobCategory=fm.JobCategory.value;
  	var CaseCategory=fm.CaseCategory.value;
  	
  	if (JobCategory == null || JobCategory == '')
  	{
  			alert("权限级别不能为空");
    		return false;
  	}
  	
  	if (CaseCategory == null || CaseCategory == '')
  	{
  			alert("案件类型不能为空");
    		return false;
  	}
    return true;

}

/*[公共函数]提交到后台*/
function submitPopedomForm()
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

    fm.hideOperate.value=mOperate;
    fm.action = "./LLGrpClaimPopedomSave.jsp";
    document.getElementById("fm").submit(); //提交

}


/*[公共函数]交后操作,服务器数据返回后执行的操作*/
function afterPopedomSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "FAIL" )
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

        InitQueryClick();//刷新列表及界面    
    }

}


/*[增加]按钮对应操作*/
function PopedomAddClick()
{
	if (beforePopedomSubmit())
	{	
		mOperate="Popedom||INSERT";
		submitPopedomForm();
	}
}
/*[修改]按钮对应操作*/
function PopedomEditClick()
{
	if (beforePopedomSubmit)
	{
		fm.JobCategory.disabled=false;
    fm.JobCategoryName.disabled=false;
    fm.ClaimType.disabled=false;
    fm.StartDate.disabled=false;
    fm.MngCom.disabled=false;
    mOperate="Popedom||UPDATE";
    submitPopedomForm();
  }
}

/*[重置]按钮对应操作*/
function PopedomResetClick()
{
    //清空录入信息
    fm.JobCategory.value = '';
    fm.JobCategoryName.value = '';
    fm.CaseCategory.value = '';
    fm.CaseCategoryName.value = '';
    fm.BaseMin.value = '';
    fm.BaseMax.value = '';
    fm.MngCom.value = '';
    fm.StartDate.value = '';
    fm.EndDate.value = '';
    fm.Operator.value = '';
    fm.MakeDate.value = '';
    fm.MakeTime.value = '';
    fm.ModifyDate.value = '';
	fm.ModifyTime.value = '';
	fm.MngComName.value='';
	
	fm.JobCategory.disabled=false;
	fm.MngCom.disabled=false;
	fm.StartDate.disabled=false;
	fm.JobCategoryName.disabled=false;
	
    fm.editPopedomButton.disabled = true;
    fm.savePopedomButton.disabled = false; //[增加]按钮   
    fm.deletePopedomButton.disabled=true;
}


/*理赔权限信息MulLine]的触发函数*/
function getClaimPopedomGrid(spanID,parm)
{

    //得到MulLine的理赔权限信息
    var tNo = ClaimPopedomGrid.getSelNo();
    fm.JobCategory.disabled=true;
    fm.JobCategoryName.disabled=true;
    //fm.ClaimType.disabled=true;
    fm.StartDate.disabled=true;
    fm.MngCom.disabled=true;

    //赋值信息
    fm.JobCategory.value = ClaimPopedomGrid.getRowColData(tNo - 1,1);
    fm.CaseCategory.value = ClaimPopedomGrid.getRowColData(tNo - 1,2);
    fm.CaseCategoryName.value = ClaimPopedomGrid.getRowColData(tNo - 1,3);
    fm.JobCategoryName.value = ClaimPopedomGrid.getRowColData(tNo - 1,4);
    fm.ClaimType.value = ClaimPopedomGrid.getRowColData(tNo - 1,5);
    fm.ClaimCheckUwFlag.value = ClaimPopedomGrid.getRowColData(tNo - 1,6);
    fm.BaseMin.value = ClaimPopedomGrid.getRowColData(tNo - 1,7);
    fm.BaseMax.value = ClaimPopedomGrid.getRowColData(tNo - 1,8);
    fm.StartDate.value = ClaimPopedomGrid.getRowColData(tNo - 1,9);
    fm.EndDate.value = ClaimPopedomGrid.getRowColData(tNo - 1,10);
    fm.MngCom.value = ClaimPopedomGrid.getRowColData(tNo - 1,11);
    fm.Operator.value = ClaimPopedomGrid.getRowColData(tNo - 1,12);
    fm.MakeDate.value = ClaimPopedomGrid.getRowColData(tNo - 1,13);
    fm.MakeTime.value = ClaimPopedomGrid.getRowColData(tNo - 1,14);
    fm.ModifyDate.value = ClaimPopedomGrid.getRowColData(tNo - 1,15);
    fm.ModifyTime.value = ClaimPopedomGrid.getRowColData(tNo - 1,16);
    
    fm.OriJobCategory.value = ClaimPopedomGrid.getRowColData(tNo - 1,1);
    fm.OriCaseCategory.value = ClaimPopedomGrid.getRowColData(tNo - 1,2);
   	
    fm.editPopedomButton.disabled = false;
    fm.deletePopedomButton.disabled = false;
	fm.savePopedomButton.disabled =true;     
}

// 查询按钮
function InitQueryClick()
{
	PopedomResetClick();//[重置]按钮对应操作
    
	var strSQL="select JobCategory,CaseCategory,(select codename from ldcode where codetype='grpcasecategory' and code=casecategory),(select codename from ldcode where codetype='grpjobcategory' and code=JobCategory),"
		         +" ClaimType,ClaimCheckUwFlag,BaseMin,BaseMax,StartDate,EndDate,MngCom,Operator,MakeDate,MakeTime,ModifyDate,ModifyTime from LLGrpClaimPopedom where 1=1 "
				 + getWherePart( 'JobCategory','JobCategoryQ')
				 + getWherePart( 'CaseCategory','CaseCategoryQ')
				 + " order by JobCategory ";
	//prompt("团险权限查询",strSQL);
	turnPage.pageLineNum=5;
	turnPage.queryModal(strSQL, ClaimPopedomGrid);        
}

/*[删除]按钮对应操作*/
function PopedomDeleteClick()
{
    if (confirm("您确实想删除该记录吗?"))
    {
        mOperate="Popedom||DELETE"; 
        submitPopedomForm();
    }
    else
    {
        fm.fmtransact.value="";
        return;
    }
}

