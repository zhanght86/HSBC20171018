var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//初始化查询
function initQuery()
{
	var strSQL = "";
	var tClmno = fm.ClmNo.value;
    if (tClmno=="")
    {
        tClmno = "000000";
    }
	/*strSQL = "select k1,k2,k3,k4,k5,k6,k7,k8 from"
		   + "(select a.rgtno k1 ,(case a.clmstate when '10' then '报案' when '20' then '立案' when '30' then '审核' when '40' then '审批' when '50' then '结案' when '60' then '完成' when '70' then '关闭' end) k2,"
		   + " b.customerno k3,(select c.name from ldperson c where c.customerno = b.customerno) k4,(case b.customersex when '0' then '男' when '1' then '女' when '2' then '不详' end) k5,b.medaccdate k6,b.AccDate k7,c.caserelano k8"
           + " from LLRegister a,llcase b,LLCaseRela c"
           + " where a.rgtno = b.caseno and b.caseno=c.caseno"
           + " and a.RgtNo in (select t.CaseNo from LLCase t where t.CustomerNo = '"+ document.all('AppntNo').value +"' and t.CaseNo != '" + tClmno + "')"
           + " union " 
           + " select a.rptno k1,'报案'k2,b.customerno k3,c.name k4,(case c.sex when '0' then '男' when '1' then '女' when '2' then '不详' end) k5,b.medaccdate k6,b.AccDate k7,d.caserelano  k8"
           + " from (select * from llreport where rptno not in (select rgtno from llregister)) a,llsubreport b,ldperson c,LLCaseRela d "
           + " where a.rptno = b.subrptno and b.subrptno=d.caseno and b.customerno = c.customerno and a.rgtflag = '10' "
           + " and a.rptno in (select t.subrptno from llsubreport t where t.CustomerNo = '"+ document.all('AppntNo').value +"' and t.subrptno != '" + tClmno + "')"
           + " )";*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLLClaimQueryInputSql");
		mySql.setSqlId("LLLClaimQuerySql1");
		mySql.addSubPara(document.all('AppntNo').value );  
		mySql.addSubPara(tClmno );       
		mySql.addSubPara(document.all('AppntNo').value );       
		mySql.addSubPara(tClmno);                 
//	  turnPage.pageLineNum=5;    //每页5条
//    turnPage.queryModal(strSQL, LLLcContSuspendGrid);

	//prompt("既往赔案查询",strSQL);
	arrResult = easyExecSql(mySql.getString());
    if (arrResult)
    {
    	displayMultiline(arrResult,LLLcContSuspendGrid);
    }
}

//初始化查询案件合并信息
function initQuery2()
{
	var strSQL = "";
	var tClmno = fm.ClmNo.value;
    if (tClmno=="")
    {
        tClmno = "000000";
    }
	/*strSQL = "(select a.rgtno,(case a.clmstate when '10' then '报案' when '20' then '立案' when '30' then '审核' when '40' then '审批' when '50' then '结案' when '60' then '完成' when '70' then '关闭' end),"
		   + " b.customerno,b.customername,(case b.customersex when '0' then '男' when '1' then '女' when '2' then '不详' end),b.medaccdate,b.AccDate,c.caserelano"
           + " from LLRegister a,llcase b,LLCaseRela c"
           + " where a.rgtno = b.caseno and b.caseno=c.caseno"
           + " and b.CustomerNo = '"+ document.all('AppntNo').value +"' and b.CaseNo != '" + tClmno + "'"
           + " and clmstate in('50','60')"
           + ")"
           + " union " 
           + "(select a.rgtno,(case a.clmstate when '10' then '报案' when '20' then '立案' when '30' then '审核' when '40' then '审批' when '50' then '结案' when '60' then '完成' when '70' then '关闭' end),"
           + " b.customerno,b.customername,(case b.customersex when '0' then '男' when '1' then '女' when '2' then '不详' end),b.medaccdate,b.AccDate,c.caserelano "
           + " from LLRegister a,llcase b,LLCaseRela c"
           + " where a.rgtno = b.caseno and b.caseno=c.caseno "
           + " and b.CustomerNo = '"+ document.all('AppntNo').value +"' and b.CaseNo != '" + tClmno + "'"
           + " and exists(select 1 from ljagetclaim where otherno=a.rgtno and feeoperationtype='B')"
           + ")";*/
       		mySql = new SqlClass();
		mySql.setResourceName("claim.LLLClaimQueryInputSql");
		mySql.setSqlId("LLLClaimQuerySql2");
		mySql.addSubPara(document.all('AppntNo').value );  
		mySql.addSubPara(tClmno );       
		mySql.addSubPara(document.all('AppntNo').value );       
		mySql.addSubPara(tClmno);       
//	  turnPage.pageLineNum=5;    //每页5条
//    turnPage.queryModal(strSQL, LLLcContSuspendGrid);

	//prompt("既往赔案查询-案件合并信息",strSQL);
	arrResult = easyExecSql(mySql.getString());
    if (arrResult)
    {
    	displayMultiline(arrResult,LLClaimMergeGrid);
    }
}


//LLLcContSuspendGrid的响应函数
function LLLcContSuspendGridClick()
{
	var i = LLLcContSuspendGrid.getSelNo();
    i = i - 1;
    fm.ContNo.value=LLLcContSuspendGrid.getRowColData(i,2);//合同号
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

//返回按钮
function goToBack()
{
	//关闭当前页面
    top.close();
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
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr,content,tAccNo)
{
    showInfo.close();

    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        document.all('AccNo').value=tAccNo;
        
        //alert("tAccNo");
        
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        
        if(fm.fmtransact.value=='CancelMerge')
        {
	        operateButton1.style.display="";
	        operateButton2.style.display="none"; 
        }
        else
        {
	        operateButton1.style.display="none";
	        operateButton2.style.display=""; 
        }
        
        initQuery();//刷新
        initQuery2();//刷新
        

        top.opener.showMatchDutyPay2(tAccNo);//自动匹配并理算

    }


}



//刷新
function Refresh()
{
	top.opener.showMatchDutyPay2();//自动匹配并理算
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
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"呈报查询");
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

    if (i < 1)
    {
    	alert("请选择一条记录！");
        return;
    }

    var strUrl = "";
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = LLLcContSuspendGrid.getRowColData(i,1);
        var tPhase = LLLcContSuspendGrid.getRowColData(i,2);
        var tAppntNo = LLLcContSuspendGrid.getRowColData(i,3);

        
        //2008-11-21 由于客户需求取消在立案阶段的理算功能,所以对于立案部分的显示也和报案显示的元素一样
        if (tPhase == '报案'||tPhase == '立案')
        {
//          location.href = "LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=" + fm.AppntNo.value;
            strUrl = "LLClaimQueryReportInputMain.jsp?claimNo="+tClmNo+"&AppntNo="+tAppntNo+"&phase=0";

        }
        else
        {
//          location.href = "LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=" + fm.AppntNo.value;
            strUrl = "LLClaimQueryInputMain.jsp?claimNo="+tClmNo+"&AppntNo="+tAppntNo+"&phase=0";

        }
        
        window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
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


/**
 * 根据传入的标志控制界面显示内容
 *
 */
function viewParm()
{

	//只有在审核管理有需要进行案件合并才需要显示案件合并的那部分信息
	if(document.all('ViewFlag').value==null||document.all('ViewFlag').value=="")
	{
		divLLCaseMerge.style.display= "none";
	}
	else
	{
		//显示案件合并信息
		divLLCaseMerge.style.display= "";
		
		//先判断险种是否可以执行案件合并
	    /*var strSql = "  select nvl(count(1),0) from lmrisksort a where"
	    	       + "  exists(select 1 from llclaimpolicy b where b.riskcode=a.riskcode "
                   + "  and b.clmno = '" + document.all('ClmNo').value + "' )"
                   + "  and a.risksorttype = '6'";*/
	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLLClaimQueryInputSql");
		mySql.setSqlId("LLLClaimQuerySql3");
		mySql.addSubPara(document.all('ClmNo').value );  

	    //prompt("判断险种是否可以执行案件合并",strSql);
	    
		var tFlag = easyExecSql(mySql.getString());
		
		if (tFlag)
		{
			//等于0描述该险种不准许执行案件合并,则不必显示案件合并的那部分界面
		    if (tFlag[0][0] ==0)
		    {
		    	divLLCaseMerge.style.display= "none";
		    }
		    else
		    {
		    	//判断是否已经执行过案件合并
			    /*strSql = " select nvl(count(1),0),a.caserelano from llcaserela a,llcaserela b  where a.caserelano=b.caserelano"
		                    + " and a.caseno = '" + document.all('ClmNo').value + "' group by a.caserelano"; */
			mySql = new SqlClass();
			mySql.setResourceName("claim.LLLClaimQueryInputSql");
			mySql.setSqlId("LLLClaimQuerySql4");
			mySql.addSubPara(document.all('ClmNo').value );  
			    //prompt("判断是否已经执行过案件合并的sql",strSql);
			    
				tFlag = easyExecSql(mySql.getString());
				
				//alert("tFlag[0][0]:"+tFlag[0][0]);
				//alert("tFlag[0][1]:"+tFlag[0][1]);
				
				if (tFlag)
				{
					//小于等于1表示没有执行过案件合并,则初始化显示案件合并按钮
				    if (tFlag[0][0] <=1)
				    {
				        operateButton1.style.display="";
				        operateButton2.style.display="none"; 
				    	fm.AccNo.value=tFlag[0][1];	
				    }
				    else
				    {
				        operateButton1.style.display="none";
				        operateButton2.style.display="";  	
				    }
				}
		    }
		}

	}
	
	MergeClick();
}


/**
 * 当用户点击案件合并信息的multiline的每一行的单选按钮时响应该函数
 *
 */
function MergeClick()
{
     var i = LLClaimMergeGrid.getSelNo();
	 
	/* var strSql2 = " select a.caserelano from llcaserela a "
            + " where  a.caseno = '" + document.all('ClmNo').value + "'";*/
			mySql = new SqlClass();
			mySql.setResourceName("claim.LLLClaimQueryInputSql");
			mySql.setSqlId("LLLClaimQuerySql4");
			mySql.addSubPara(document.all('ClmNo').value ); 
	 var result=easyExecSql(mySql.getString());
	 
	 //alert("result:"+result);

 	 fm.AccNo.value=result[0][0];	

}

/**
 * 2009-01-06
 * zhangzheng
 * 案件合并函数
 * @return
 */
function LLClaimMerge()
{
    var i = LLClaimMergeGrid.getSelNo();

    if (i < 1)
    {
    	alert("请选择一条记录！");
        return;
    }

	fm.fmtransact.value="Merge";
    fm.action = './LLClaimCaseMergeSave.jsp';
	submitForm();
}

/**
 * 2009-01-06
 * zhangzheng
 * 取消案件合并函数
 * @return
 */
function LLClaimCancelMerge()
{
    var i = LLClaimMergeGrid.getSelNo();

    if (i < 1)
    {
    	alert("请选择一条记录！");
        return;
    }

	fm.fmtransact.value="CancelMerge";   
    fm.action = './LLClaimCaseMergeSave.jsp';
	submitForm();
}