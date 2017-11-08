var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//初始化查询
function initQuery()
{
	var tClmNo = fm.ClmNo.value;
	var strSQL = "";
	/*strSQL = "select a.RgtNo, "
           + " (select c.codename from ldcode c where c.codetype = 'llrgttype' and trim(c.code)=trim(a.RgtState)), "
           + " (select c.codename from ldcode c where c.codetype = 'llrgtclass' and trim(c.code)=trim(a.RgtClass)), "
           + " (select c.codename from ldcode c where c.codetype = 'llrgtclass' and trim(c.code)=trim(a.RgtObj)), "
           + " a.RgtObjNo, "
           + " a.RgtDate, "
           + " (select c.codename from ldcode c where c.codetype = 'llclaimstate' and trim(c.code)=trim(a.ClmState)) "
           + " from LLRegister a "
           + " where a.RgtNo in (select t.CaseNo from LLCase t where t.CustomerNo = '"+ document.all('AppntNo').value +"')"
           //+ " and a.ClmState in ('60','70')" ; */
  /*strSQL="select a.clmno,a.grpcontno,a.insuredno,a.insuredname,(select codename from ldcode where codetype='getdutykind' and code=a.getdutykind),"
        +" case a.clmstate when '10' then '待立案' when '20' then '待审核' when '30' then '待签批'"
        +" when '35' then '预付' when '40' then '审批' when '50' then '结案' when '60' then '完成'"
        +" when '70' then '关闭' end ,"/*+"b.accdate,"*/ /*+"b.endcasedate,a.riskcode,a.standpay,"
        +"case when c.clmstate in ('10','20') then 0 else "
		+"nvl((select realpay from llclaim where clmno = a.clmno),0) end"
        +" from llclaimpolicy a,llcase b,llregister c where a.clmno=b.caseno and a.clmno = c.rgtno"
        +" and a.insuredno=b.customerno and a.insuredno = '"+ document.all('AppntNo').value +"'"
        +"and a.clmno !='"+tClmNo+"'";*/
        //+" and b.rgtstate='60' " ;                     
//               prompt("",strSQL);
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLLClaimQueryInputSql");
	mySql.setSqlId("LLLClaimQuerySql1");
	mySql.addSubPara(document.all('AppntNo').value );
	mySql.addSubPara(tClmNo);
	turnPage.pageLineNum=5;    //每页5条
    turnPage.queryModal(mySql.getString(), LLLcContSuspendGrid);
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
    fm.ContNo.value=LLLcContSuspendGrid.getRowColData(i,2);//合同号
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
//	//首先检验是否选择记录  
	var row = LLLcContSuspendGrid.getSelNo();
	var tContNo="";
    if (row < 1)
    {
    	alert("请选择一条记录！");
        return;
    } 
        
    tContNo=fm.ContNo.value;//合同号    
    location.href="../sys/PolDetailQueryMain.jsp?ContNo="+tContNo+"&IsCancelPolFlag=0";
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

//初始化查询
function initQuery2(tClmNo)
{
   // var rptNo = fm.ClmNo.value;
   var rptNo = tClmNo;
    if(rptNo == "")
    {
        alert("传入赔案为空！");
        return;
    }
    //检索事件号(一条)
   /* var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLLClaimQueryInputSql");
	mySql.setSqlId("LLLClaimQuerySql2");
	mySql.addSubPara(rptNo);            
//    alert("strSQL1= "+strSQL1);
    var AccNo = easyExecSql(mySql.getString());
//    alert("AccNo= "+AccNo);
    //检索报案号及其他报案信息(一条)
   /* var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,RptMode,AccidentSite,AccidentReason,RptDate,MngCom,Operator from LLReport where "
                + "RptNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLLClaimQueryInputSql");
	mySql.setSqlId("LLLClaimQuerySql3");
	mySql.addSubPara(rptNo);                   
//    alert("strSQL2= "+strSQL2);
    var RptContent = easyExecSql(mySql.getString());
//    alert("RptContent= "+RptContent);
    //更新页面内容
    fm.AccNo.value = AccNo[0][0];
//    fm.AccidentDate.value = AccNo[0][1];
    
    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];
    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];
    fm.RptMode.value = RptContent[0][5];
    fm.AccidentSite.value = RptContent[0][6];
//    fm.occurReason.value = RptContent[0][7];
    fm.RptDate.value = RptContent[0][8];
    fm.MngCom.value = RptContent[0][9];
    fm.Operator.value = RptContent[0][10];
    showOneCodeName('Relation','Relation','RelationName');//报案人与事故人关系
    showOneCodeName('RptMode','RptMode','RptModeName');//报案方式
//    showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
//    showOneCodeName('hospitalcode','hospital','TreatAreaName');//治疗医院
//    showOneCodeName('lloccurreason','IsDead','IsDeadName');//死亡标志
    
    //****************************************************
    //更新页面权限
    //****************************************************    
//    fm.AccidentDate.readonly = true;

//    fm.QueryPerson.disabled=true;
//    fm.QueryReport.disabled=true;

//    fm.occurReason.disabled=true;
//    fm.accidentDetail.disabled=true;
//    fm.claimType.disabled=true;
//    fm.Remark.disabled=true;

//    fm.AddReport.disabled=false;
//    fm.addbutton.disabled=false;
//    fm.updatebutton.disabled=false;
//    fm.DoHangUp.disabled=false;
/*    fm.CreateNote.disabled=false;
    fm.BeginInq.disabled=false;
    fm.ViewInvest.disabled=false;
   	fm.Condole.disabled=false;
    fm.SubmitReport.disabled=false;
    fm.ViewReport.disabled=false;
    fm.AccidentDesc.disabled=false;
   	fm.QueryCont1.disabled=false;
    fm.QueryCont2.disabled=false;
    fm.QueryCont3.disabled=false;*/

    //检索分案关联出险人信息(多条)
    /*var strSQL3 = "select CustomerNo,Name,Sex,Birthday,VIPValue from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLLClaimQueryInputSql");
	mySql.setSqlId("LLLClaimQuerySql4");
	mySql.addSubPara(rptNo);   
//    alert("strSQL3= "+strSQL3);
    easyQueryVer3Modal(mySql.getString(), SubReportGrid);
/*    if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
    {
        SubReportGridClick(0);
    }*/
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
    try
    {
        //initForm();
        
  	    fm.AccNo.value = "";
 	    fm.RptNo.value = "";
	    fm.RptorName.value = "";
	    fm.RptorPhone.value = "";
	    fm.RptorAddress.value = "";
	    fm.Relation.value = "";
	    fm.RelationName.value = "";
	    fm.RptMode.value = "";
	    fm.RptModeName.value = "";
	    fm.AccidentSite.value = "";
	    fm.RptDate.value = "";
 	    fm.MngCom.value = "";
 	    fm.Operator.value = "";
	    fm.CaseState.value = "";

/*	    fm.customerNo.value = "";
	    fm.customerAge.value = "";
 	    fm.customerSex.value = "";
 	    fm.SexName.value = "";
	    fm.IsVip.value = "";
	    fm.AccidentDate.value = "";
 	    fm.occurReason.value = "";
 	    fm.ReasonName.value = "";
	    fm.hospital.value = "";
	    fm.TreatAreaName.value = "";
	    fm.AccidentDate2.value = "";
 	    fm.accidentDetail.value = "";
 	    fm.accidentDetailName.value = "";
//	 	    fm.IsDead.value = "";
//	 	    fm.IsDeadName.value = "";
 	    fm.claimType.value = "";
 	    fm.cureDesc.value = "";
 	    fm.cureDescName.value = "";
 	    fm.Remark.value = "";
 	    fm.AccDesc.value = "";//事故描述
 	    fm.AccResult1.value = "";
 	    fm.AccResult1Name.value = "";
 	    fm.AccResult2.value = "";
 	    fm.AccResult2Name.value = "";

        //理赔类型置空
        for (var j = 0;j < fm.claimType.length; j++)
        {
        	  fm.claimType[j].checked = false;
        }*/
    }
    catch(re)
    {
        alert("在LLReport.js-->resetForm函数中发生异常:初始化界面错误!");
    }
}


//保单查询
//按照”客户号“在LCpol中进行查询，显示该客户的保单明细
function showInsuredLCPol()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        
        return;
    }
	var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//查看调查
function showQueryInq()
{
    if(fm.RptNo.value == "" || fm.RptNo.value == null)
    {
        alert("先选择赔案！");
        return;
    }
	  //***************************************
	  //判断该赔案是否存在调查
	  //***************************************
//    var strSQL = "select count(1) from LLInqConclusion where "
//                + "ClmNo = " + fm.RptNo.value;
//    var tCount = easyExecSql(strSQL);
////    alert(tCount);
//    if (tCount == "0")
//    {
//    	  alert("该赔案还没有调查信息！");
//    	  return;
//    }
	  var strUrl="LLInqQueryMain.jsp?claimNo="+fm.RptNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"查看调查");
}

//查看呈报
function showQuerySubmit()
{
	  //***************************************
	  //判断该赔案是否存在呈报
	  //***************************************
//    var strSQL = "select count(1) from LLSubmitApply where "
//                + "ClmNo = " + fm.RptNo.value;
//    var tCount = easyExecSql(strSQL);
////    alert(tCount);
//    if (tCount == "0")
//    {
//    	  alert("该赔案还没有呈报信息！");
//    	  return;
//    }
	  var strUrl="LLSubmitQueryMain.jsp?claimNo="+fm.RptNo.value;
//    window.open(strUrl,"呈报查询",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    //window.open(strUrl,"呈报查询");
    window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

/**
* 对span的显示、隐藏
* <p><b>Example: </b><p>
* <p>showPage(HTML.ImageObject, HTML.SpanObject.ID)<p>
* @param img 显示图片的HTML对象
* @param spanID HTML中SPAN对象的ID
* @return 如果页面SPAN可见，则将其隐藏，并显示表示关闭的图片；反之
*/
function showPage2(img,spanID)
{
  if(spanID.style.display=="")
  {
    //关闭
    //spanID.style.display="none";
    //img.src="../common/images/butCollapse.gif";

	//打开
    spanID.style.display="";
    img.src="../common/images/butExpand.gif";
  }
  else
  {
    //打开
    spanID.style.display="";
    img.src="../common/images/butExpand.gif";
  }
}

//SelfLLReportGrid点击事件
function LLLClaimQueryGridClick()
{
    var i = LLLcContSuspendGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        tClmNo = LLLcContSuspendGrid.getRowColData(i,1);
        location.href = "LLClaimQueryInput.jsp?claimNo=" + tClmNo + "&phase=" + fm.AppntNo.value;//存储出险人号码待返回用
    }

//-------------------------原页面显示信息----------------------------------------------------------
//    showPage2(this,divLLReport1);
//    showPage2(this,divLLReport2);
//
//	resetForm();
//
//    try
//    {
//    	//initParam();
//		var appntNo = tClmNo;
//        initSubReportGrid();
//        initQuery2(appntNo);
//    }
//    catch(re)
//    {
//        alert("LLLClaimQueryInit.jsp-->LLLClaimQueryGridClick函数中发生异常:初始化界面错误!");
//    }
//-------------------------原页面显示信息----------------------------------------------------------

}