//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量

// 初始化表格1
function queryGrid()
{
    initLLClaimQueryGrid();
    var strSQL = "";
    
    //查询立案以后信息
    strSQL = "(select a.rgtno,(case a.clmstate when '10' then '报案' when '20' then '立案' when '30' then '审核' when '40' then '审批' when '50' then '结案' when '60' then '完成' when '70' then '关闭' end),b.customerno,(select c.name from ldperson c where c.customerno = b.customerno),(case b.customersex when '0' then '男' when '1' then '女' when '2' then '不详' end),b.AccDate "
           + " from llregister a,llcase b "
           + " where a.rgtno = b.caseno "
           + " and a.MngCom like '" + fm.ManageCom.value + "%%'"
           + getWherePart( 'a.rgtno' ,'RptNo') //赔案号
           + getWherePart( 'a.clmstate' ,'ClmState') //赔案状态
           + getWherePart( 'a.AccidentDate' ,'AccidentDate') //意外事故发生日期
           + getWherePart( 'b.CustomerNo' ,'CustomerNo') //出险人编码
           + getWherePart( 'b.AccDate' ,'AccidentDate2') //出险日期
           + getWherePart( 'a.RgtDate' ,'RgtDate') //立案日期
           + getWherePart( 'a.EndCaseDate' ,'EndDate'); //结案日期
           if (fm.CustomerName.value != "")  //出险人姓名
           {
           strSQL = strSQL + " and b.CustomerNo in (select customerno from ldperson where name like '%%" + fm.CustomerName.value + "%%') ";
           }
           if (fm.ContNo.value != "")  //合同号
           {
           strSQL = strSQL + " and b.rgtno in (select ClmNo from LLClaimPolicy where ContNo ='" + fm.ContNo.value + "') ";
           }
           strSQL = strSQL + " )";
           
    //联合查询报案信息
    if ((fm.ClmState.value == "" || fm.ClmState.value == "10") && fm.RgtDate.value == "" && fm.EndDate.value == "" && fm.ContNo.value == "")
    {
    strSQL = strSQL + " union "
           + "(select a.rptno,'报案',b.customerno,c.name,(case c.sex when '0' then '男' when '1' then '女' when '2' then '不详' end),b.AccDate "
           + " from llreport a,llsubreport b,ldperson c "
           + " where a.rptno = b.subrptno and b.customerno = c.customerno and a.rgtflag = '10' "
           + " and a.MngCom like '" + fm.ManageCom.value + "%%'"
           + getWherePart( 'a.rptno' ,'RptNo') //赔案号
           + getWherePart( 'a.AccidentDate' ,'AccidentDate') //意外事故发生日期
           + getWherePart( 'b.CustomerNo' ,'CustomerNo') //出险人编码
           + getWherePart( 'b.AccDate' ,'AccidentDate2'); //出险日期
           if (fm.CustomerName.value != "")  //出险人姓名
           {
           strSQL = strSQL + " and b.CustomerNo in (select customerno from ldperson where name like '%%" + fm.CustomerName.value + "%%') ";
           }
           strSQL = strSQL + " )";
    }
    //增加排序
    strSQL = strSQL + " order by 1";

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