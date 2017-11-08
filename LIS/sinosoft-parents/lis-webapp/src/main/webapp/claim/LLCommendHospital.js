var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();

var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库

// 清除页面上的数据
function ClearPagedata()
{
	fm.ConsultNo.value="";
    fm.HospitalCode.value ="";
    fm.HospitalName.value ="";
    fm.HosAtti.value = "";
    fm.ConFlag.value ="";
    fm.AppFlag.value ="";
    fm.HosState.value = "";  	
    fm.HosAttiName.value = "";
    fm.ConFlagName.value ="";
    fm.AppFlagName.value ="";
    fm.HosStateName.value = "";  
    fm.HospitalCode.disabled= false; //[医院代码]
}

// 查询按钮
function InitQueryClick()
{
    ClearPagedata();//清除页面表单控件数据（医院信息）
    HospitalResetClick();//[重置]按钮对应操作
    
	if(fm.MngComQ.value==null||fm.MngComQ.value=='')
	{
	      alert("请先选择管理机构!");
	      return false;
	}
	

    sql_id = "LLCommendHospitalSql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLCommendHospital"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(HospitalCodeQ);//指定传入的参数
	my_sql.addSubPara(HospitalNameQ);//指定传入的参数
	my_sql.addSubPara(HosAttiQ);//指定传入的参数
	my_sql.addSubPara(ConFlagQ);//指定传入的参数
	my_sql.addSubPara(HosStateQ);//指定传入的参数
	my_sql.addSubPara(fm.MngComQ.value);//指定传入的参数
	str_sql = my_sql.getString();
//	var strSQL="select ConsultNo,HospitalCode,HospitalName,HosAtti,ConFlag,AppFlag,HosState,MngCom from LLCommendHospital where 1=1 "
//				 + getWherePart( 'HospitalCode','HospitalCodeQ')
//				 + getWherePart( 'HospitalName','HospitalNameQ')
//				 + getWherePart( 'HosAtti','HosAttiQ')
//				 + getWherePart( 'ConFlag','ConFlagQ')
//				 + getWherePart( 'HosState','HosStateQ')
//				 + " and MngCom like '"+fm.MngComQ.value+"%'"
//				 + " order by MngCom,HospitalCode";
	   
	//alert(strSQL);
    turnPage.pageLineNum=5;
	turnPage.queryModal(str_sql, LLCommendHospitalGrid);        
}

/*医院信息列表（LLCommendHospitalGrid）的触发函数*/
function LLCommendHospitalGridClick()
{
    ClearPagedata();
    fm.editHospitalButton.disabled =false;
    fm.deleteHospitalButton.disabled =false;  
    fm.saveHospitalButton.disabled =true;     
    fm.HospitalCode.disabled=true; //此时[医院代码]不可修改---用于修改 或 删除 
    var tNo = LLCommendHospitalGrid.getSelNo()-1;	
    fm.ConsultNo.value =LLCommendHospitalGrid.getRowColData(tNo,1);   
    fm.HospitalCode.value =LLCommendHospitalGrid.getRowColData(tNo,2);
    fm.HospitalName.value =LLCommendHospitalGrid.getRowColData(tNo,3);
    fm.HosAtti.value =LLCommendHospitalGrid.getRowColData(tNo,4);
    fm.ConFlag.value =LLCommendHospitalGrid.getRowColData(tNo,5);
    fm.AppFlag.value =LLCommendHospitalGrid.getRowColData(tNo,6);
    fm.HosState.value =LLCommendHospitalGrid.getRowColData(tNo,7);  
    showOneCodeName('llhosgrade','HosAtti','HosAttiName');  
    showOneCodeName('ConFlag','ConFlag','ConFlagName');
    showOneCodeName('appflag','AppFlag','AppFlagName');    
    showOneCodeName('hosstate','HosState','HosStateName');	 
}


/*[增加]按钮对应操作*/
function HospitalAddClick()
{
	if(trim(fm.HospitalCode.value)==""||trim(fm.HospitalName.value) ==""||fm.HosState.value =="")
	{
		alert("数据填写不完整，请重新填写！");
		return;
	}
//	alert("trim(fm.HospitalCode.value).length="+trim(fm.HospitalCode.value).length);
//	alert("trim(fm.HospitalName.value).length="+trim(fm.HospitalName.value).length);
//	if(trim(fm.HospitalCode.value).length>10||trim(fm.HospitalName.value).length>60)
//	{
//		alert("医院代码超过10位或名称超过60位，请重新填写！");
//		return;
//	}
//    var strSQL = "select HospitalCode from LLCommendHospital where HospitalCode='"+trim(fm.HospitalCode.value)+"'";
    sql_id = "LLCommendHospitalSql1";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLCommendHospital"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(trim(fm.HospitalCode.value));//指定传入的参数
	str_sql = my_sql.getString();
    var arr = easyExecSql(str_sql);
//    alert(arr);
    if (arr== null) 
    {
//		alert("该医院代码可以使用！");
    }
	else
	{
		alert("该医院代码已经存在！");
		return;
	}
	fm.ConsultNo.value=fm.HospitalCode.value;//咨询通知号码
    fm.fmtransact.value="Hospital||INSERT";
    submitHospitalForm();
}
/*[修改]按钮对应操作*/
function HospitalEditClick()
{
	if(trim(fm.HospitalCode.value)==""||trim(fm.HospitalName.value) ==""||fm.HosState.value =="")
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
    	fm.HospitalCode.disabled= false; //[医院代码]---否则无法取到数据
	    fm.fmtransact.value="Hospital||UPDATE";
	    submitHospitalForm();
    }
    else
    {
        fm.fmtransact.value="";
        return;        
    }

}
/*[删除]按钮对应操作*/
function HospitalDeleteClick()
{
    if (confirm("您确实想删除该记录吗?"))
    {
    	fm.HospitalCode.disabled= false; //[医院代码]---否则无法取到数据
        fm.fmtransact.value="Hospital||DELETE";
        submitHospitalForm();
    }
    else
    {
        fm.fmtransact.value="";
        return;
    }
}

/*[重置]按钮对应操作*/
function HospitalResetClick()
{
	ClearPagedata();
    fm.editHospitalButton.disabled = true;//[修改]按钮
    fm.deleteHospitalButton.disabled = true;//[删除]按钮
    fm.saveHospitalButton.disabled = false; //[增加]按钮   
}


/*[公共函数]提交到后台*/
function submitHospitalForm()
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
    fm.action = "./LLCommendHospitalSave.jsp";
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
