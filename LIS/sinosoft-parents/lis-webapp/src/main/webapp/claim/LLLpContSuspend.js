var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//初始化查询
function initQuery()
{

//########################################################此处为包含第二被保人的########################################	
//	//----------------------1--------2-------------3-----------4--------5-----------6----------7-----------8---------9
//	strSQL = "(select a.grpcontno,a.ContNo,a.ProposalContNo,a.PrtNo,a.ContType,a.FamilyType,a.FamilyID,a.PolType,nvl((select b.PosFlag from LCContHangUpState b where b.ContNo = a.ContNo),0),nvl((select b.RNFlag from LCContHangUpState b where b.ContNo = a.ContNo),0)"
//		   + " from LcCont a,LCInsured b where 1=1 and a.contno = b.contno "
//		   + " and b.insuredno = '"+ document.all('InsuredNo').value +"')" 
//		   + " UNION "
//		   + "(select a.grpcontno,a.ContNo,a.ProposalContNo,a.PrtNo,a.ContType,a.FamilyType,a.FamilyID,a.PolType,nvl((select b.PosFlag from LCContHangUpState b where b.ContNo = a.ContNo),0),nvl((select b.RNFlag from LCContHangUpState b where b.ContNo = a.ContNo),0)"
//		   + " from LcCont a,lcappnt b where 1=1 and a.contno = b.contno "
//		   + " and b.AppntNo = '"+ document.all('InsuredNo').value +"')";//投保人
//######################################################################################################################
	
    initLLLpContSuspendGrid();
    initLLLpContInGrid();

	var strSQL1=""; //与赔案无关
	var strSQL2=""; //与赔案相关
	//----------------------1--------2-------------3-----------4--------5-----------6----------7-----------8---------9
	/*strSQL1 = "select a.grpcontno,a.ContNo,a.ProposalContNo,a.PrtNo,a.ContType,a.FamilyType,a.FamilyID,a.PolType,(case (nvl((select count(*) from LCContHangUpState b where b.ContNo = a.ContNo and b.PosFlag = '1'),0)) when 0 then 0 else 1 end),(case (nvl((select count(*) from LCContHangUpState b where b.ContNo = a.ContNo and b.RNFlag = '1'),0)) when 0 then 0 else 1 end)"
		   + " from LcCont a where 1=1 "
		   + " and (a.insuredno = '"+ document.all('InsuredNo').value +"'"
		   + " or a.AppntNo = '"+ document.all('InsuredNo').value +"')"  //投保人
		   + " and a.contno not in (select distinct contno from lcconthangupstate b where b.hangupno = '" + document.all('ClmNo').value + "')";
	*///prompt("报案-查询保单挂起与赔案无关保单",strSQL1);	   
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLLpContSuspendInputSql");
	mySql.setSqlId("LLLpContSuspendSql1");
	mySql.addSubPara(document.all('InsuredNo').value ); 
	mySql.addSubPara(document.all('InsuredNo').value ); 
	mySql.addSubPara(document.all('ClmNo').value ); 
	var arrResult1 = easyExecSql(mySql.getString());
	
	//----------------------1--------2-------------3-----------4--------5-----------6----------7-----------8---------9
	/*strSQL2 = "select a.grpcontno,a.ContNo,a.ProposalContNo,a.PrtNo,a.ContType,a.FamilyType,a.FamilyID,a.PolType,(case (nvl((select count(*) from LCContHangUpState b where b.ContNo = a.ContNo and b.PosFlag = '1'),0)) when 0 then 0 else 1 end),(case (nvl((select count(*) from LCContHangUpState b where b.ContNo = a.ContNo and b.RNFlag = '1'),0)) when 0 then 0 else 1 end)"
		   + " from LcCont a where 1=1 "
		   + " and a.contno in (select distinct contno from lcconthangupstate b where b.hangupno =  '" + document.all('ClmNo').value + "')";
   */ //prompt("报案-查询保单挂起与赔案相关保单",strSQL2);	    
	mySql = new SqlClass();
	mySql.setResourceName("claim.LLLpContSuspendInputSql");
	mySql.setSqlId("LLLpContSuspendSql2"); 
	mySql.addSubPara(document.all('ClmNo').value ); 
	var arrResult2 = easyExecSql(mySql.getString());
    if (arrResult1)
    {
    	displayMultiline(arrResult1,LLLpContSuspendGrid);
    }
    
    if (arrResult2)
    {
    	displayMultiline(arrResult2,LLLpContInGrid);
    }
}

//LLLpContSuspendGrid的响应函数
function LLLpContSuspendGridClick()
{
	var i = LLLpContSuspendGrid.getSelNo();
    i = i - 1;
    fm.ContNo.value=LLLpContSuspendGrid.getRowColData(i,2);//合同号
}

//LLLcContInGrid的响应函数
function LLLcContInGridClick()
{
	var i = LLLcContInGrid.getSelNo();
    i = i - 1;
    fm.ContNo.value=LLLcContInGrid.getRowColData(i,2);//合同号
}

//“保存”按钮响应函数
function saveClick()
{
	submitForm();
}

//“查询”按钮
function queryClick()
{
    if (fm.ContNo.value == "" || fm.ContNo.value == null)
    {
    	alert("请选择一条记录！");
        return;
    } 

    strUrl="../sys/PolDetailQueryMain.jsp?ContNo=" + fm.ContNo.value + "&IsCancelPolFlag=0";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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
    fm.action = './LLLpContSuspendSave.jsp';
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
    }
    //tSaveFlag ="0";
    initQuery();//刷新
}

