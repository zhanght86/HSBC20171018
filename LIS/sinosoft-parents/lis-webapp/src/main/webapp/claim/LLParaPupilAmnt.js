var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库
// 清除页面上的数据
function ClearPagedata()
{
    fm.ComCode.value ="";
    fm.ComCodeName.value ="";
    fm.UpComCode.value = "";
    fm.BaseValue.value ="";
    fm.StartDate.value ="";
    fm.EndDate.value = "";  	 
    fm.ComCode.disabled= false; //[管理机构代码]
}

// 查询按钮
function InitQueryClick()
{
	
    ClearPagedata();//清除页面表单控件数据（未成年人保额标准信息）
   /* BaseValueResetClick();//[重置]按钮对应操作*/
//	var strSQL="select ComCode,ComCodeName,UpComCode,BaseValue,StartDate,EndDate from LLParaPupilAmnt where 1=1 "
//				 + getWherePart( 'ComCode','ComCodeQ')
//				 + " order by ComCode desc";
	sql_id = "LLParaPupilAmntSql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLParaPupilAmntSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(document.getElementsByName("ComCodeQ")[0].value);//指定传入的参数
	str_sql = my_sql.getString();
	
//	alert(strSQL);
    turnPage.pageLineNum=5;
	turnPage.queryModal(str_sql, LLParaPupilAmntGrid);        
}

/*未成年人保额标准信息列表（LLParaPupilAmntGrid）的触发函数*/
function LLParaPupilAmntGridClick()
{
     // ClearPagedata();
//    fm.editBaseValueButton.disabled =false;
//    fm.deleteBaseValueButton.disabled =false;  
//    fm.saveBaseValueButton.disabled =true;     
//    fm.ComCode.disabled=true; //此时[管理机构代码]不可修改---用于修改 或 删除 
    ClearPagedata();
    document.getElementById("editBaseValueButton").disabled =false;
    document.getElementById("deleteBaseValueButton").disabled =false;  
    document.getElementById("saveBaseValueButton").disabled =true;     
    fm.ComCode.disabled=true; //此时[管理机构代码]不可修改---用于修改 或 删除 
    var tNo = LLParaPupilAmntGrid.getSelNo()-1;	
    fm.ComCode.value =LLParaPupilAmntGrid.getRowColData(tNo,1);   
    fm.ComCodeName.value =LLParaPupilAmntGrid.getRowColData(tNo,2);
    fm.UpComCode.value =LLParaPupilAmntGrid.getRowColData(tNo,3);
    fm.BaseValue.value =LLParaPupilAmntGrid.getRowColData(tNo,4);
    fm.StartDate.value =LLParaPupilAmntGrid.getRowColData(tNo,5);
    fm.EndDate.value =LLParaPupilAmntGrid.getRowColData(tNo,6);
  
   // showOneCodeName('llhosgrade','HosAtti','HosAttiName');  
   // showOneCodeName('ConFlag','ConFlag','ConFlagName');
   // showOneCodeName('appflag','AppFlag','AppFlagName');    
   // showOneCodeName('hosstate','HosState','HosStateName');	 
}


/*[增加]按钮对应操作*/
function BaseValueAddClick()
{
	if(trim(fm.ComCode.value)==""||trim(fm.StartDate.value) ==""||fm.BaseValue.value =="")
	{
		alert("数据填写不完整，请重新填写！");
		return;
	}
//    var strSQL = "select ComCode from LLParaPupilAmnt where ComCode='"+trim(fm.ComCode.value)+"'and StartDate='"+trim(fm.StartDate.value)+"'" ;
//    
    sql_id = "LLParaPupilAmntSql1";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLParaPupilAmntSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(trim(fm.ComCode.value));//指定传入的参数
	my_sql.addSubPara(trim(fm.StartDate.value));
	str_sql = my_sql.getString();
    var arr = easyExecSql(str_sql);
//    alert(arr);
    if (!arr) 
    {
    }
	else
	{
		alert("该管理机构未成年人保额标准已经存在！");
		return;
	}
	fm.ConsultNo.value=fm.ComCode.value;//咨询通知号码
    fm.fmtransact.value="BaseValue||INSERT";
    submitBaseValueForm();
}
/*[修改]按钮对应操作*/
function BaseValueEditClick()
{
	if(trim(fm.ComCode.value)==""||trim(fm.StartDate.value) ==""||fm.BaseValue.value =="")
	{
		alert("数据填写不完整，请重新填写！");
		return;
	}
//	alert("trim(fm.HospitalCode.value).length="+trim(fm.HospitalCode.value).length);
//	alert("trim(fm.HospitalName.value).length="+trim(fm.HospitalName.value).length);
//	if(trim(fm.HospitalName.value).length>60)
//	{
//		alert("医院名称长度过长，请重新填写！");
//		return;
//	}
    if (confirm("您确实想修改该记录吗?"))
    {
    	fm.ComCode.disabled= false; //[管理机构代码]---否则无法取到数据
	    fm.fmtransact.value="BaseValue||UPDATE";
	    submitBaseValueForm();
    }
    else
    {
        fm.fmtransact.value="";
        return;        
    }

}
/*[删除]按钮对应操作*/
function BaseValueDeleteClick()
{
    if (confirm("您确实想删除该记录吗?"))
    {
    	fm.ComCode.disabled= false; //[管理机构代码]---否则无法取到数据
        fm.fmtransact.value="BaseValue||DELETE";
        submitBaseValueForm();
    }
    else
    {
        fm.fmtransact.value="";
        return;
    }
}

/*[重置]按钮对应操作*/
function BaseValueResetClick()
{
	ClearPagedata();
	document.getElementById("editBaseValueButton").disabled= true;
	document.getElementById("deleteBaseValueButton").disabled= true;
	document.getElementById("saveBaseValueButton").disabled= false;
//    fm.editBaseValueButton.disabled = true;//[修改]按钮
//    fm.deleteBaseValueButton.disabled = true;//[删除]按钮
//    fm.saveBaseValueButton.disabled = false; //[增加]按钮   
}


/*[公共函数]提交到后台*/
function submitBaseValueForm()
{
  	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./LLParaPupilAmntSave.jsp";
    document.getElementById("fm").submit(); //提交

}


/*[公共函数]交后操作,服务器数据返回后执行的操作*/
function afterHospitalSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "FAIL" )
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
    	InitQueryClick();//刷新列表及界面    
    }

}
