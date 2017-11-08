var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//初始化查询
function initQuery()
{

  /*var strSQLA="select "
        +" a.grpcontno,a.ContNo,a.ManageCom,substr(to_char(a.CValiDate),0,10),"
        +" substr(to_char(a.PaytoDate),0,10),"
        +" a.SignCom,substr(to_char(a.SignDate),0,10),"
        +" (case a.PolType when '1' then '个单' when '2' then '团单' end),"
    +" (case a.AppFlag when '0' then '投保' when '1' then '承保' when '4' then '终止' end),c.prtno "
    +" from LcCont a,LCInsured b ,LCGrpCont c"
        +" where a.contno=b.contno and b.InsuredNo = '"+ document.all('AppntNo').value +"'  and a.grpcontno = c.grpcontno"
        ;
    var strSQLB = "select "
        +" a.grpcontno,a.ContNo,a.ManageCom,substr(to_char(a.CValiDate),0,10),"
        +" substr(to_char(a.PaytoDate),0,10),"
        +" a.SignCom,substr(to_char(a.SignDate),0,10),"
        +" (case a.PolType when '1' then '个单' when '2' then '团单' end),"
        +" (case a.AppFlag when '0' then '投保' when '1' then '承保' when '4' then '终止' end),c.prtno "
        +" from LcCont a,LCAppnt b ,LCGrpCont c"
        +" where a.contno=b.contno and b.appntno = '"+ document.all('AppntNo').value +"'  and a.grpcontno = c.grpcontno";     
        
    var strSQLC = strSQLA + " union " + strSQLB*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLLcContQueryInputSql");
	mySql.setSqlId("LLLcContQuerySql1");
	mySql.addSubPara(document.all('AppntNo').value ); 
    mySql.addSubPara(document.all('AppntNo').value );     
  //turnPage.pageLineNum=10;    //每页10条
    //turnPage.queryModal(strSQL, LLLcContSuspendGrid);
  arrResult = easyExecSql(mySql.getString());
    if (arrResult)
    {
      displayMultiline(arrResult,LLLcContSuspendGrid);
    }
}

//LLLcContSuspendGrid的响应函数
function LLLcContSuspendGridClick()
{
  var i = LLLcContSuspendGrid.getSelNo();
    i = i - 1;
    fm.ContNo.value = LLLcContSuspendGrid.getRowColData(i,2);//合同号
    
    //查询状态
   /* var tSql = "select contno,insuredno,polno,(case statetype when 'Terminate' then '终止' when 'Available' then '有效' end ),(case state when '0' then '否' when '1' then '是' end),statereason,startdate,enddate,remark,operator,makedate from lccontstate "
             + " where contno = '" + fm.ContNo.value + "'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLLcContQueryInputSql");
	mySql.setSqlId("LLLcContQuerySql2");
	mySql.addSubPara(fm.ContNo.value ); 
    
//    var arrState = easyExecSql(tSql); 
    turnPage.queryModal(mySql.getString(),LcContStateGrid);
    
    //显示隐藏
    divLcContState.style.display = '';
}

//“保存”按钮响应函数
function saveClick()
{
    fm.isReportExist.value="false";
  fm.fmtransact.value="update";
  submitForm();
}
//“查询”按钮
function queryClick()
{
//  //首先检验是否选择记录  
  var row = LLLcContSuspendGrid.getSelNo();
  var tContNo="";
    if (row < 1)
    {
      alert("请选择一条记录！");
        return;
    }else{
    //tPrtNo=   LLLcContSuspendGrid.getRowColData(row-1,10);
    var cGrpContNo = LLLcContSuspendGrid.getRowColData( row - 1, 1 );
    }
    //location.href="../sys/GrpPolDetailQueryMain.jsp?PrtNo="+tPrtNo;		
    var tPrtNoSql = "select prtno from lccont where grpcontno='"+cGrpContNo+"'";
    var tPrtNo = "";
    var tSSRS = easyExecSql(tPrtNoSql);
    if(tSSRS){
    	tPrtNo = tSSRS[0][0];
    }else{
    	alert("查询投保单信息失败！");
    	return false;
    }
		try
		{
			//window.open("../sys/GrpPolDetailQueryMain.jsp?GrpContNo=" + cGrpContNo + "&PrtNo="+tPrtNo);
			window.open("../sys/GrpPolDetailQueryMain.jsp?GrpContNo=" + cGrpContNo + "&PrtNo="+tPrtNo,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
		}
		catch(ex)
		{
			alert( "没有发现父窗口的afterQuery接口。" + ex );
		}
	
}
//返回
//function goToBack()
//{
//    location.href="LLReportInput.jsp";
//}

//提交数据
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

    fm.action = './LLLcContSuspendSave.jsp';
    fm.submit(); //提交
    tSaveFlag ="0"; 
}
//提交后操作,服务器数据返回后执行的操作
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
    //tSaveFlag ="0";
    initQuery();//刷新
}

