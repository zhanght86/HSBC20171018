var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库
//初始化查询
function initializeQuery()
{
    var strSQL = "";	
	if(document.all('ClmNo').value=="" ||document.all('ConNo').value=="")
	{
		document.all('saveAdd').disabled="true";
		alert("数据传送错误!");
	    return;
	}
//    strSQL = "select clmno,conno,batno,initdept,inqdept "
//    	   +" ,(case locflag when '0' then '本地' when '1' then '异地' end)"
//    	   +" ,(case colflag when '0' then '总结论' when '2' then '机构结论' end)"
//    	   +" ,(case finiflag when '0' then '未完成' when '1' then '已完成' end)"
//    	   +" from llinqconclusion where 1=1"
//           +" and clmno='"+document.all('ClmNo').value+"'"
//           +" and conno='"+document.all('ConNo').value+"'"; 
    sql_id = "LLInqConclusionSql0";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLInqConclusionSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(document.all('ClmNo').value);//指定传入的参数
	my_sql.addSubPara(document.all('ConNo').value);//指定传入的参数
	str_sql = my_sql.getString();
    var arr = easyExecSql(str_sql);
    if (arr == null)
    {
	      alert("调查申请不存在!");
	      return;
    }
    else
    {
    	fm.ClmNo.value=arr[0][0]; //赔案号
        fm.ConNo.value=arr[0][1];  //结论序号
        fm.BatNo.value=arr[0][2]; //批次号
        fm.InitDept.value=arr[0][3];  //发起机构
        fm.InqDept.value=arr[0][4];   //调查机构 
        fm.LocFlag.value=arr[0][5];   //本地标志
        fm.ColFlag.value=arr[0][6];   //汇总标志 
        fm.FiniFlag.value=arr[0][7];   //   完成标志          
       showOneCodeName('stati','InitDept','InitDeptName');          
       showOneCodeName('stati','InqDept','InqDeptName');    
    }	
     //查询调查申请信息,组成mulline
//    var strSQL = "select clmno,inqno,batno,customerno,customername,vipflag"
//    			+ ",(case initphase when '01' then '报案阶段' when '02' then '审核阶段' when '03' then '呈报阶段' end)"
//    			+ " ,inqrcode,inqitem,inqconclusion ,inqstartdate,inqenddate from llinqapply where 1=1"
//                + " and clmno = '" +fm.ClmNo.value +"'"
//                + " and batno = '" + fm.BatNo.value +"'"
//                + " and inqdept = '" + fm.InqDept.value +"'"
//                + " order by clmno";

    sql_id = "LLInqConclusionSql1";
	my_sql = new SqlClass();
	my_sql.setResourceName("claim.LLInqConclusionSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(fm.ClmNo.value);//指定传入的参数
	my_sql.addSubPara(fm.BatNo.value);//指定传入的参数
	my_sql.addSubPara(fm.InqDept.value);//指定传入的参数
	str_sql = my_sql.getString();
    turnPage.queryModal(str_sql, LLInqApplyGrid);  		
}

//选中LLInqApplyGrid响应事件
function LLInqApplyGridClick()
{
    var i = LLInqApplyGrid.getSelNo()-1;
    fm.InqReason.value = LLInqApplyGrid.getRowColData(i,8);
	fm.InqItem.value = LLInqApplyGrid.getRowColData(i,9);
	fm.InqConclusion1.value = LLInqApplyGrid.getRowColData(i,10);
	showOneCodeName('llinqreason','InqReason','InqReasonName');//
}


//返回按钮
function goBack()
{
    var strUrl= "LLInqConclusionMissInput.jsp?";    
    location.href=strUrl;
}

//保存按钮
function saveClick()
{
	//首先进行非空字段检验   
    if (fm.InqConclusion.value == "" || fm.InqConclusion.value == null)
    {
        alert("请填写机构调查结论！");
        return;
    }

  　//提交
    fm.fmtransact.value ="";
    submitForm();
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
    fm.action = './LLInqConclusionSave.jsp';
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";
}

//提交后操作,服务器数据返回后执行的操作
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
        //更改可操作按钮
        fm.saveAdd.disabled = true;
        location.href="LLInqConclusionMissInput.jsp?";
    }
    tSaveFlag ="0";
}



//查看调查申请-------查看该机构该批次下的所有调查申请-------2005-08-15添加
function queryInqApply()
{   
//	alert();
	document.all('divQueryInqApply').style.display="";
}


//隐藏调查申请-------隐藏该机构该批次下的所有调查申请-------2005-08-15添加
function hideInqApply()
{   
	document.all('divQueryInqApply').style.display="none";
}

function queryInqInfo()
{
	var strUrl="LLInqConclusionQueryMain.jsp?ClaimNo="+fm.ClmNo.value+"&BatNo="+fm.BatNo.value+"&InqDept="+fm.InqDept.value+"&Type=2";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"查看调查");
}
