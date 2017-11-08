var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库
//理赔用户查询返回信息（用户编码 、用户姓名、机构编码）
function afterQuery( arrQueryResult ) 
{ 
    var arrResult = new Array();
    if( arrQueryResult != null ) 
    {
        arrResult = arrQueryResult;
		fm.DesignateOperator.value = arrResult[0];
		fm.ComCode.value = arrResult[1];
	}
}

//[查询任务]按钮
function LWMissionQueryClick()
{
	//fm.DefaultOperator.value = "";
	 document.all('DefaultOperator').value="";
	 document.all('DesignateOperator').value="";
	//fm.DesignateOperator.value = "";	
//	var strSQL = "select missionprop1,"
//	           + " (case ActivityID when '0000005005' then '报案' when '0000005015' then '立案' when '0000005025' then '预付' when '0000005035' then '审核' when '0000005055' then '审批' when '0000005065' then '简易案件' when '0000005075' then '结案' when '0000005105' then '呈报' when '0000005125' then '调查分配' when '0000005145' then '调查过程' when '0000005165' then '调查机构结论' when '0000005175' then '调查赔案结论' "
//	           +" when '0000009005' then '团险报案' when '0000009015' then '团险立案' when '0000009025' then '团险预付' when '0000009035' then '团险审核' when '0000009055' then '团险审批' when '0000009065' then '团险简易案件' when '0000009075' then '团险结案' when '0000009105' then '团险上报' when '0000009125' then '团险调查分配' when '0000009145' then '团险调查过程' when '0000009165' then '团险调查机构结论' when '0000009175' then '团险调查赔案结论' "
//	           +" when '0000005505' then '理赔二核' end),"
//	           + " missionprop3,missionprop4,"
//	           + " missionprop20,"
//	           + " missionprop6,"
//	           + " defaultoperator,"
//               + " (select username from llclaimuser where usercode = defaultoperator),"
//               + " missionid,submissionid,activityid,'' "
//               + " from lwmission where 1=1 "
//               + " and processid in ('0000000005','0000000009','6100300004','6100301011') "  
//               + " and (case when (select comcode from llclaimuser where usercode = lwmission.defaultoperator) is not null then (select comcode from llclaimuser where usercode = lwmission.defaultoperator) else missionprop20 end) like '" + document.all('ComCode').value + "%%'"
//    		   + getWherePart( 'missionprop7','ClmNOQ')
//    		   + getWherePart( 'missionprop1','ClmNOQ1')
//    		   + getWherePart( 'defaultoperator','OperatorQ');
	 //document.all('ActivityIDQ').value="";
	if(document.all('ActivityIDQ').value==null||document.all('ActivityIDQ').value=="")
	{
		if(document.all('ClmNOQ').value == null || document.all('ClmNOQ').value== "")
		{
//		  strSQL=strSQL+" union all  "
//					+ "select missionprop1,'事前扫描',  missionprop3,missionprop4, missionprop20,"
//	        + " missionprop6,defaultoperator, (select username from llclaimuser where usercode = defaultoperator),"
//	        + " missionid,submissionid,activityid,missionprop9  from lwmission where  activityid='0000005010'  "
//	        + " and (case when (select comcode from llclaimuser where usercode = lwmission.defaultoperator) is not null then (select comcode from llclaimuser where usercode = lwmission.defaultoperator) else missionprop20 end) like '" + document.all('ComCode').value + "%%'"
//	  		  + getWherePart( 'missionprop1','ClmNOQ1')
//	  		  + getWherePart( 'defaultoperator','OperatorQ');
		  
		    sql_id = "LWMissionReassignSql11";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql"); //指定使用的properties文件名
			my_sql.setSqlId(sql_id);//指定使用的Sql的id
			my_sql.addSubPara(document.all('ComCode').value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.all('ComCode').value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//指定传入的参数
			str_sql = my_sql.getString();
	   }
		}
	
	else
	{
		if(fm.ActivityIDQ.value=="001"){
//			strSQL=strSQL+" and activityid in ('0000009005','0000005005') ";
			sql_id = "LWMissionReassignSql12";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql"); //指定使用的properties文件名
			my_sql.setSqlId(sql_id);//指定使用的Sql的id
			my_sql.addSubPara(document.all('ComCode').value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//指定传入的参数
			str_sql = my_sql.getString();
		}
		if(fm.ActivityIDQ.value=="002"){
//			strSQL=strSQL+" and activityid in ('0000009015','0000005015') ";
			sql_id = "LWMissionReassignSql13";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql"); //指定使用的properties文件名
			my_sql.setSqlId(sql_id);//指定使用的Sql的id
			my_sql.addSubPara(document.all('ComCode').value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//指定传入的参数
			str_sql = my_sql.getString();
		}
		if(fm.ActivityIDQ.value=="003"){
//			strSQL=strSQL+" and activityid in ('0000009025','0000005025') ";
			sql_id = "LWMissionReassignSql14";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql"); //指定使用的properties文件名
			my_sql.setSqlId(sql_id);//指定使用的Sql的id
			my_sql.addSubPara(document.all('ComCode').value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//指定传入的参数
			str_sql = my_sql.getString();
		}
		if(fm.ActivityIDQ.value=="004"){
			/*strSQL=strSQL+" and activityid in ('0000009035','0000005035') ";*/
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql");
			my_sql.setSqlId("LWMissionReassignSql6");
			my_sql.addSubPara(document.all('ComCode').value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//指定传入的参数
			str_sql = my_sql.getString();
		}
		if(fm.ActivityIDQ.value=="005"){
//			strSQL=strSQL+" and activityid in ('0000009055','0000005055') ";
			sql_id = "LWMissionReassignSql16";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql"); //指定使用的properties文件名
			my_sql.setSqlId(sql_id);//指定使用的Sql的id
			my_sql.addSubPara(document.all('ComCode').value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//指定传入的参数
			str_sql = my_sql.getString();
		}
		if(fm.ActivityIDQ.value=="006"){
//			strSQL=strSQL+" and activityid in ('0000009065','0000005065') ";
			sql_id = "LWMissionReassignSql17";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql"); //指定使用的properties文件名
			my_sql.setSqlId(sql_id);//指定使用的Sql的id
			my_sql.addSubPara(document.all('ComCode').value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//指定传入的参数
			str_sql = my_sql.getString();
		}
		if(fm.ActivityIDQ.value=="007"){
//			strSQL=strSQL+" and activityid in ('0000009075','0000005075') ";
			sql_id = "LWMissionReassignSql18";
			my_sql = new SqlClass();
			my_sql.setResourceName("claim.LWMissionReassignInputSql"); //指定使用的properties文件名
			my_sql.setSqlId(sql_id);//指定使用的Sql的id
			my_sql.addSubPara(document.all('ComCode').value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//指定传入的参数
			my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//指定传入的参数
			str_sql = my_sql.getString();
		}
	}
	
	//只针对事前扫描
	if(fm.ActivityIDQ.value=="008")
	{
//		strSQL= "select missionprop1,'事前扫描',  missionprop3,missionprop4, missionprop20,"
//	        + " missionprop6,defaultoperator, (select username from llclaimuser where usercode = defaultoperator),"
//          + " missionid,submissionid,activityid,missionprop9  from lwmission where  activityid='0000005010'  "
//          + " and (case when (select comcode from llclaimuser where usercode = lwmission.defaultoperator) is not null then (select comcode from llclaimuser where usercode = lwmission.defaultoperator) else missionprop20 end) like '" + document.all('ComCode').value + "%%'"
//    		  + getWherePart( 'missionprop1','ClmNOQ1')
//    		  + getWherePart( 'defaultoperator','OperatorQ');
		sql_id = "LWMissionReassignSql19";
		my_sql = new SqlClass();
		my_sql.setResourceName("claim.LWMissionReassignInputSql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(document.all('ComCode').value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('ClmNOQ1'))[0].value);//指定传入的参数
		my_sql.addSubPara(document.getElementsByName(trim('OperatorQ'))[0].value);//指定传入的参数
		str_sql = my_sql.getString();
				  
   }
	var arrResult = easyExecSql(str_sql);//prompt("",strSQL);
	if(arrResult==null ||arrResult=="")
	{
		LWMissionGrid.clearData();
		alert("没有查询到任何任务记录！请重新输入条件。");
		document.all('ActivityIDQ').value="";
		document.all('ActivityIDQName').value="";
		document.all('ClmNOQ').value="";
		document.all('OperatorQ').value="";
		
//		fm.ActivityIDQ.value="";
//		fm.ActivityIDQName.value="";		
//		fm.ClmNOQ.value="";
//		fm.OperatorQ.value="";
		return;
	}
    else
   	{
		turnPage.queryModal(str_sql, LWMissionGrid);  
   	}
}

//LWMissionGrid的单选钮响应函数
function LWMissionGridClick()
{
	var tRow = LWMissionGrid.getSelNo();
	fm.MissionID.value=LWMissionGrid.getRowColData(tRow-1,9);
	fm.SubMissionID.value=LWMissionGrid.getRowColData(tRow-1,10);
	fm.ActivityID.value=LWMissionGrid.getRowColData(tRow-1,11);
	fm.DefaultOperator.value=LWMissionGrid.getRowColData(tRow-1,8);
	fm.OComCode.value=LWMissionGrid.getRowColData(tRow-1,5);//原机构代码
}

//[查询理赔用户]按钮
function LLClaimUserQueryClick()
{
	//var tRow = LWMissionGrid.getSelNo();
	//if( tRow < 1||tRow == null )
	//{
	//	alert( "请先选择一条记录!" );
	//	return;
	//}
	var strUrl="LLClaimUserQueryMain.jsp";
	window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}
//[指定确认]按钮
function DesignateConfirmClick()
{
	//var tRow = LWMissionGrid.getSelNo();
	//if( tRow < 1||tRow == null )
	//{
	//	alert( "请先选择一条记录!" );
	//	return;
	//}
//	if(fm.DesignateOperator.value=="" ||fm.DesignateOperator.value==null)
//	{
//		alert( "请选择操作员！" );
//		return;		
//	}
	//alert("fm.ComCode.value: "+fm.ComCode.value);
	//alert("fm.OComCode.value: "+fm.OComCode.value);
	//if(!(fm.ComCode.value==fm.OComCode.value)){
	//	alert("所选择的操作员与原操作员不属同一机构!")
	//	return;
	//}
	fm.fmtransact.value="User||UPDATE";
	fm.action="LWMissionReassignSave.jsp";
	submitForm();
}

//确认后刷新页面(默认操作人)
function Renewpage()
{
	//var tRow = LWMissionGrid.getSelNo()-1;
	//LWMissionGrid.setRowColData(tRow,8,fm.DesignateOperator.value);
	//fm.DefaultOperator.value=fm.DesignateOperator.value;
}

//公共提交方法
function submitForm()
{
    //提交数据
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
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";    
}

//预付保存提交后操作,返回
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
    Renewpage();
}
