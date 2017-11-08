//程序名称：LLUWNoticeQuery.js
//程序功能：核保通知书查询
//创建日期：2005-12-05 
//创建人  ：万泽辉


var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var mySql = new SqlClass();
//加费信息查询
function LJSPayQuery()
{
	var tClmNo = fm.ClmNo.value ;
	var tContNo = fm.ContNo.value;
	
	var strSQL = "";
	/*strSQL = " select getnoticeno,appntno,sumduepaymoney,paydate,approvecode "
	       + " from ljspay where otherno = '"+tClmNo+"' and othernotype = '5' "
	       + " and serialno='"+tContNo+"' ";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWLJSPayQueryInputSql");
	mySql.setSqlId("LLUWLJSPayQuerySql1");
	mySql.addSubPara(tClmNo ); 
	mySql.addSubPara(tContNo ); 
//	prompt("个人加费信息查询查询",strSQL);
    turnPage.queryModal(mySql.getString(),LJSPayGrid);

}


//个人应收交费表记录查询
function GetLJSPayPerson()
{
	var tSel = LJSPayGrid.getSelNo()-1;
	var tNoticeNo  = LJSPayGrid.getRowColData(tSel,1);
	/*var tSQL = "";
	tSQL = " select polno,dutycode,payplancode,paytype,sumduepaymoney,lastpaytodate,curpaytodate"
	       + " from ljspayperson where getnoticeno = '"+tNoticeNo+"'";*/
	mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWLJSPayQueryInputSql");
	mySql.setSqlId("LLUWLJSPayQuerySql2");
	mySql.addSubPara(tNoticeNo ); 

	//prompt("个人应收交费表记录查询",tSQL);
    turnPage1.queryModal(mySql.getString(),LJSPayPersonGrid);
    
    //QueryLJSPayClaim();
}      

//查询利息函数
function QueryLJSPayClaim()
{
	var tNoticeNo  = LJSPayGrid.getRowColData(0,1);
	var tClmNo     = fm.ClmNo.value ;
	
    var tPaySQL   = "";
    fm.tPay.value = "";
    /*tPaySQL = " select Pay from ljspayclaim "
            + " where OtherNoType='5' and OtherNo='"+tClmNo+"' and getnoticeno='"+tNoticeNo+"' ";  */   
	mySql = new SqlClass();
	mySql.setResourceName("claimnb.LLUWLJSPayQueryInputSql");
	mySql.setSqlId("LLUWLJSPayQuerySql3");
	mySql.addSubPara(tClmNo ); 
	mySql.addSubPara(tNoticeNo ); 
	//prompt("查询利息函数查询",tPaySQL);
    var tPayResult = new Array;
    tPayResult     = easyExecSql(mySql.getString());  
    fm.tPay.value  = tPayResult[0][0];  
      
}

/*****************************************************************************
功能：打印补费通知书
创建：niuzj,2006-01-24
******************************************************************************/
function LLUWPCLMAddFeePrint()
{
	  //alert("打印补费通知书");
    var i = 0;
    i = LJSPayGrid.mulLineCount ; 
    if(i==0)
    {
        alert("没有可打印的数据!");
        return ;
    }	  
    
//    var tNoticeNo = LJSPayGrid.getRowColData(0,1); //通知书号
    var tSel = LJSPayGrid.getSelNo()-1;
    var tNoticeNo = LJSPayGrid.getRowColData(tSel,1); //通知书号
    var tPolNo = LJSPayPersonGrid.getRowColData(0,1); //保单号
    var tDoutyCode = LJSPayPersonGrid.getRowColData(0,2);//责任编码
    var tPayPlanCode = LJSPayPersonGrid.getRowColData(0,3);//交费计划编码
    var tClmNo = fm.ClmNo.value; //赔案号
    fm.NoticeNo.value=tNoticeNo;
    fm.DoutyCode.value=tDoutyCode;
    fm.PayPlayCode.value=tPayPlanCode;
    fm.PolNo.value=tPolNo;
    //alert(tNoticeNo+":"+tPolNo)
    if (tNoticeNo=="" || tClmNo=="")
    {
        alert("没有可打印的数据!");
        return;
    }
    fm.action="LLUWPCLMAddFeeSave.jsp";
	fm.target = "../f1print";
	fm.submit();
}
