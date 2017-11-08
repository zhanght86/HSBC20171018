//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量

//提交前的校验、计算  
function beforeSubmit()
{
    //输入条件校验
    if (fm.RptNo.value == ""
       && fm.ClmState.value == ""
       && fm.AccidentDate.value == ""
       && fm.CustomerNo.value == ""
       && fm.CustomerName.value == ""
       && fm.AccidentDate2.value == ""
       && fm.RgtDate.value == ""
       && fm.EndDate.value == ""
       && fm.ContNo.value == ""
       && fm.CalManageCom.value == ""
       && fm.RgtDateStart.value == ""
       && fm.RgtDateEnd.value == ""
       && fm.GrpClmNo.value == ""
       && fm.GrpContNo.value == ""       
       )
    {
        alert("请至少输入一个条件!");
        return false;
    }
    
    //选择赔案状态必须输入受理日期区间
    //考虑查询效率添加 2006-1-10 15:11 周磊
    if (fm.ClmState.value != "" && fm.RgtDateStart.value == "" && fm.RgtDateEnd.value == "")
    {
        alert("选择赔案状态必须输入受理日期区间!");
        return false;
    }
    
    //性能优化,姓名必须输入二个汉字
    //2006-1-23 14:45 周磊
    var tName = fm.CustomerName.value;
    if (tName != "" && tName.length < 2)
    {
        alert("按出险人姓名查询至少需要输入二个汉字!");
        return false;
    }
    
    return true;
}         

// 初始化表格1
function queryGrid()
{
    if (!beforeSubmit())
    {
        return;
    }
    initLLClaimQueryGrid();
    var strSQL = "";
    
    //查询立案以后信息
//    strSQL = "(select a.rgtno,(case a.clmstate when '10' then '报案' when '20' then '立案' when '30' then '审核' when '40' then '审批' when '50' then '结案' when '60' then '完成' when '70' then '关闭' end),b.customerno,(select c.name from ldperson c where c.customerno = b.customerno),(case b.customersex when '0' then '男' when '1' then '女' when '2' then '不详' end),b.AccDate "
//           + " from llregister a,llcase b "
//           + " where a.rgtno = b.caseno "
//           + getWherePart( 'a.rgtno' ,'RptNo') //赔案号
//           + getWherePart( 'a.clmstate' ,'ClmState') //赔案状态
//           + getWherePart( 'a.AccidentDate' ,'AccidentDate') //意外事故发生日期
//           + getWherePart( 'b.CustomerNo' ,'CustomerNo') //出险人编码
//           + getWherePart( 'b.AccDate' ,'AccidentDate2') //出险日期
//           + getWherePart( 'a.RgtDate' ,'RgtDate') //立案日期
//           + getWherePart( 'a.EndCaseDate' ,'EndDate'); //结案日期
    
   
    
//           if (fm.CustomerName.value != "")  //出险人姓名
//           {
//           strSQL = strSQL + " and b.CustomerNo in (select customerno from ldperson where name like '" + fm.CustomerName.value + "%%') ";
//           }
//           
//           if (fm.ContNo.value != "")  //合同号
//           {
//           strSQL = strSQL + " and b.rgtno in (select ClmNo from LLClaimPolicy where ContNo ='" + fm.ContNo.value + "') ";
//           }
//           
//           if (fm.CalManageCom.value != "")  //立案机构
//           {
//           strSQL = strSQL + " and a.MngCom like '" + fm.CalManageCom.value + "%%'";
//           }
//           
//           if (fm.RgtDateStart.value != "")  //受理日期（区间起期）
//           {
//           strSQL = strSQL + " and a.RgtDate >= '" + fm.RgtDateStart.value + "'";
//           }
//           
//           if (fm.RgtDateEnd.value != "")  //受理日期（区间止期）
//           {
//           strSQL = strSQL + " and a.RgtDate <= '" + fm.RgtDateEnd.value + "'";
//           }
//           
//           if (fm.GrpContNo.value != "")  //团体保单号
//           {
//           strSQL = strSQL + " and a.rgtobjno in (select gp.rptno from llgrpreport gp where gp.rgtobjno = '" + fm.GrpContNo.value + "') " ;
//           }           
//           strSQL = strSQL + " )";
           
        var  RptNo1 = window.document.getElementsByName(trim("RptNo"))[0].value;
       	var  ClmState1 = window.document.getElementsByName(trim("ClmState"))[0].value;
       	var  AccidentDate1 = window.document.getElementsByName(trim("AccidentDate"))[0].value;
       	var  CustomerNo1 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
       	var  AccidentDate21 = window.document.getElementsByName(trim("AccidentDate2"))[0].value;
       	var  RgtDate1 = window.document.getElementsByName(trim("RgtDate"))[0].value;
       	var  EndDate1 = window.document.getElementsByName(trim("EndDate"))[0].value;
       	 var sqlid1="LLClaimPrtQueryMissSql1";
       	 var mySql1=new SqlClass();
       	 mySql1.setResourceName("claim.LLClaimPrtQueryMissSql");
       	 mySql1.setSqlId(sqlid1);//指定使用SQL的id
       	 mySql1.addSubPara(RptNo1);//指定传入参数
       	 mySql1.addSubPara(ClmState1);//指定传入参数
       	 mySql1.addSubPara(AccidentDate1);//指定传入参数
       	 mySql1.addSubPara(CustomerNo1);//指定传入参数
       	 mySql1.addSubPara(AccidentDate21);//指定传入参数
       	 mySql1.addSubPara(RgtDate1);//指定传入参数
       	 mySql1.addSubPara(EndDate1);//指定传入参数
       	mySql1.addSubPara(fm.CustomerName.value);//指定传入参数
       	mySql1.addSubPara(fm.ContNo.value);//指定传入参数
       	mySql1.addSubPara(fm.CalManageCom.value);//指定传入参数
       	mySql1.addSubPara(fm.RgtDateStart.value);//指定传入参数
       	mySql1.addSubPara(fm.RgtDateEnd.value);//指定传入参数
       	mySql1.addSubPara(fm.GrpContNo.value);//指定传入参数
       	 
       	 strSQL = mySql1.getString();
           
    //联合查询报案信息
    if ((fm.ClmState.value == "" || fm.ClmState.value == "10") && fm.RgtDate.value == "" && fm.EndDate.value == "" && fm.ContNo.value == "" && fm.CalManageCom.value == "")
    {
//    	   strSQL = strSQL + " union "
//           + "(select a.rptno,'报案',b.customerno,c.name,(case c.sex when '0' then '男' when '1' then '女' when '2' then '不详' end),b.AccDate "
//           + " from llreport a,llsubreport b,ldperson c "
//           + " where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' "
//           + getWherePart( 'a.rptno' ,'RptNo') //赔案号
//           + getWherePart( 'a.AccidentDate' ,'AccidentDate') //意外事故发生日期
//           + getWherePart( 'b.CustomerNo' ,'CustomerNo') //出险人编码
//           + getWherePart( 'b.AccDate' ,'AccidentDate2'); //出险日期
    	   
    	   
    	   
//           if (fm.CustomerName.value != "")  //出险人姓名
//           {
//           strSQL = strSQL + " and b.CustomerNo in (select customerno from ldperson where name like '" + fm.CustomerName.value + "%%') ";
//           }
//           
//           if (fm.RgtDateStart.value != "")  //受理日期（区间起期）
//           {
//           strSQL = strSQL + " and a.RptDate >= '" + fm.RgtDateStart.value + "'";
//           }
//           
//           if (fm.RgtDateEnd.value != "")  //受理日期（区间止期）
//           {
//           strSQL = strSQL + " and a.RptDate <= '" + fm.RgtDateEnd.value + "'";
//           }
//           
//           if (fm.GrpContNo.value != "")  //团体保单号
//           {
//           strSQL = strSQL + " and a.rgtobjno in (select gp.rptno from llgrpreport gp where gp.rgtobjno = '" + fm.GrpContNo.value + "') ";
//           }           
//           strSQL = strSQL + " )";
           
    	 var  RptNo2 = window.document.getElementsByName(trim("RptNo"))[0].value;
        	var  ClmState2 = window.document.getElementsByName(trim("ClmState"))[0].value;
        	var  AccidentDate2 = window.document.getElementsByName(trim("AccidentDate"))[0].value;
        	var  CustomerNo2 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
        	var  AccidentDate22 = window.document.getElementsByName(trim("AccidentDate2"))[0].value;
        	var  RgtDate2 = window.document.getElementsByName(trim("RgtDate"))[0].value;
        	var  EndDate2 = window.document.getElementsByName(trim("EndDate"))[0].value;
//         var  RptNo2 = window.document.getElementsByName(trim("RptNo"))[0].value;
//   		 var  AccidentDate2 = window.document.getElementsByName(trim("AccidentDate"))[0].value;
//   		 var  CustomerNo2 = window.document.getElementsByName(trim("CustomerNo"))[0].value;
//   		 var  AccidentDate22 = window.document.getElementsByName(trim("AccidentDate2"))[0].value;
   		 var sqlid2="LLClaimPrtQueryMissSql2";
   		 var mySql2=new SqlClass();
   		 mySql2.setResourceName("claim.LLClaimPrtQueryMissSql");
   		 mySql2.setSqlId(sqlid2);//指定使用SQL的id
   		 mySql2.addSubPara(RptNo2);//指定传入参数
      	 mySql2.addSubPara(ClmState2);//指定传入参数
      	 mySql2.addSubPara(AccidentDate2);//指定传入参数
      	 mySql2.addSubPara(CustomerNo2);//指定传入参数
      	 mySql2.addSubPara(AccidentDate22);//指定传入参数
      	 mySql2.addSubPara(RgtDate2);//指定传入参数
      	 mySql2.addSubPara(EndDate2);//指定传入参数
      	 mySql2.addSubPara(fm.CustomerName.value);//指定传入参数
      	 mySql2.addSubPara(fm.ContNo.value);//指定传入参数
      	 mySql2.addSubPara(fm.CalManageCom.value);//指定传入参数
      	 mySql2.addSubPara(fm.RgtDateStart.value);//指定传入参数
      	 mySql2.addSubPara(fm.RgtDateEnd.value);//指定传入参数
      	 mySql2.addSubPara(fm.GrpContNo.value);//指定传入参数
   		 mySql2.addSubPara(RptNo2);//指定传入参数
   		 mySql2.addSubPara(AccidentDate2);//指定传入参数
   		 mySql2.addSubPara(CustomerNo2);//指定传入参数
   		 mySql2.addSubPara(AccidentDate22);//指定传入参数
   		 mySql2.addSubPara(fm.CustomerName.value);//指定传入参数
   		 mySql2.addSubPara(fm.RgtDateStart.value);//指定传入参数
   		 mySql2.addSubPara(fm.RgtDateEnd.value);//指定传入参数
   		 mySql2.addSubPara(fm.GrpContNo.value);//指定传入参数
   		 strSQL = mySql2.getString()
   		
           
    }
    //增加排序
 //   strSQL = strSQL + " order by 1";

    
    
    
    turnPage.queryModal(strSQL,LLClaimQueryGrid);
}

//LLClaimQueryGrid点击事件
function LLClaimQueryGridClick()
{
//    var i = LLClaimQueryGrid.getSelNo();
//    if (i != '0')
//    {
//        i = i - 1;
//        var tClmNo = LLClaimQueryGrid.getRowColData(i,1);
//        var tPhase = LLClaimQueryGrid.getRowColData(i,2);
//        if (tPhase != '报案')
//        {
////            location.href="LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
//            var strUrl="LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
//        }
//        else
//        {
////            location.href="LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=0";
//            var strUrl="LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=0";
//        }
//        window.open(strUrl,"赔案查询",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    }
}

//赔案打印
function showPrint()
{
    var i = LLClaimQueryGrid.getSelNo()-1;
	if(i<0)
	{
		alert("请选择一条赔案记录");
		return;
	}
    var tClmNo = LLClaimQueryGrid.getRowColData(i,1);
    var tCustNo = LLClaimQueryGrid.getRowColData(i,3);
    var strUrl="LLClaimEndCaseAffixPrtMain.jsp?claimNo="+tClmNo+"&custNo="+tCustNo;
//        window.open(strUrl,"赔案打印");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    
}

//赔案详细查询
function showDetail()
{
    var i = LLClaimQueryGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tClmNo = LLClaimQueryGrid.getRowColData(i,1);
        var tPhase = LLClaimQueryGrid.getRowColData(i,2);
        if (tPhase != '报案')
        {
//            location.href="LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
            var strUrl="LLClaimQueryInput.jsp?claimNo="+tClmNo+"&phase=0";
        }
        else
        {
//            location.href="LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=0";
            var strUrl="LLClaimQueryReportInput.jsp?claimNo="+tClmNo+"&phase=0";
        }
        window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    }
}


    //用户自定义打印
function showUserDefinedPrt()
{
    var i = LLClaimQueryGrid.getSelNo()-1;
	if(i<0)
	{
		alert("请选择一条赔案记录");
		return;
	}
    var tClmNo = LLClaimQueryGrid.getRowColData(i,1);
    var tCustNo = LLClaimQueryGrid.getRowColData(i,3);
    var strUrl="LLUserDefinedBillPrtMain.jsp?ClmNo="+tClmNo+"&CustNo="+tCustNo;
//    window.open(strUrl,"用户自定义打印");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');  
}


//团体医疗给付清单打印响应函数
function showMedBillGrp()
{
	  if(fm.GrpClmNo.value ==null||fm.GrpClmNo.value == "")
	  {
		    var i = LLClaimQueryGrid.getSelNo()-1;
		  	if(i<0)
		  	{
			  	  alert("请选择一条赔案记录!");
			  	  return;
			  }
		    var tClmNo = LLClaimQueryGrid.getRowColData(i,1);
//		    var tSQLF = " select rgtobjno from llregister where rgtobj='2' "
//		              + " and rgtno='"+tClmNo+"'";
		    
		    var sqlid3="LLClaimPrtQueryMissSql3";
   		    var mySql3=new SqlClass();
   		    mySql3.setResourceName("claim.LLClaimPrtQueryMissSql");
   		    mySql3.setSqlId(sqlid3);//指定使用SQL的id
   		    mySql3.addSubPara(tClmNo);//指定传入参数
   		    var tSQLF = mySql3.getString()
		    
		    var tResult = easyExecSql(tSQLF);
		    if(tResult == null||tResult == "")
		    {
		      	alert("查询团体赔案号出现错误！");
		    	  return;
		    }
		    fm.GrpClmNo.value = tResult;		    
     }
    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		fm.transact.value = "MedBillGrp";
		fm.target = "../f1print";
		fm.action="./LLPrtGrpSave.jsp";
		document.getElementById("fm").submit();
		showInfo.close(); 
}

//团体批单给付批注打印响应函数
function showPostilGrp()
{
	  if(fm.GrpClmNo.value ==null||fm.GrpClmNo.value == "")
	  {
		    var i = LLClaimQueryGrid.getSelNo()-1;
		  	if(i<0)
		  	{
			  	  alert("请选择一条赔案记录!");
			  	  return;
			  }
		    var tClmNo = LLClaimQueryGrid.getRowColData(i,1);
//		    var tSQLF = " select rgtobjno from llregister where rgtno='"+tClmNo+"' and rgtobj='2'";
		    
		    var sqlid4="LLClaimPrtQueryMissSql4";
   		    var mySql4=new SqlClass();
   		    mySql4.setResourceName("claim.LLClaimPrtQueryMissSql");
   		    mySql4.setSqlId(sqlid4);//指定使用SQL的id
   		    mySql4.addSubPara(tClmNo);//指定传入参数
   		    var tSQLF = mySql4.getString()
		    
		    var tResult = easyExecSql(tSQLF);
		    if(tResult == null||tResult == "")
		    {
		      	alert("查询团体赔案号出现错误！");
		    	  return;
		    }
		    fm.GrpClmNo.value = tResult;		    
    }
    var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		
		fm.transact.value = "PostilGrp";
		fm.target = "../f1print";
		fm.action="./LLPrtGrpSave.jsp";
		document.getElementById("fm").submit();
		showInfo.close();  	    
}