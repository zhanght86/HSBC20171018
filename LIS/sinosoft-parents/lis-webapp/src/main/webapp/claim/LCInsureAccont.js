
var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//查询初始化页面上部的保险账户分类表
function LCInsureAccClassGridQuery()
{
    //首先根据赔案号从“赔付明细表”（llclaimdetail）中查询出该赔案下所有的 保单号（PolNo）
    //然后查询“保险账户分类表”（LCInsureAccClass）表，按“保单险种号码”（PolNo）进行查找帐户信息
    //###################################################LCInsureAccClass表的字段
    //GrpContNo,GrpPolNo,ContNo,ManageCom
    // PolNo ,InsuAccNo ,PayPlanCode ,OtherNo ,OtherType ,AccAscription 
	//,RiskCode ,InsuredNo ,AppntNo ,AccType ,AccComputeFlag ,AccFoundDate 
	//,AccFoundTime ,BalaDate ,BalaTime ,SumPay ,SumPaym ,LastAccBala ,LastUnitCount 
	//,LastUnitPrice ,InsuAccBala ,UnitCount ,InsuAccGetMoney ,FrozenMoney ,State 
	//#######################################################
	/*var strSql = " select PolNo ,InsuAccNo ,PayPlanCode ,OtherNo ,OtherType ,AccAscription,InsuredNo,AccType ,AccComputeFlag ,AccFoundDate from LCInsureAccClass where 1=1"
            + " and polno in (select polno from llclaimdetail where clmno='" +document.all('ClmNo').value + "')";*/
	mySql = new SqlClass();
	mySql.setResourceName("claim.LCInsureAccontInputSql");
	mySql.setSqlId("LCInsureAccontSql1");
	mySql.addSubPara(document.all('ClmNo').value ); 
  //    alert(strSql);        
    arr = easyExecSql( mySql.getString() );
    if (arr==null ||arr=="")
    {
		alert("在该赔案下未找到任何帐户信息！");    
		return;    	
    }
    else
	{
        displayMultiline(arr,LCInsureAccClassGrid);		
	}
}
//LCInsureAccClassGrid响应的函数名
function LCInsureAccClassGridClick()  //
{
    var i = LCInsureAccClassGrid.getSelNo() - 1;	
	var tPolNo=LCInsureAccClassGrid.getRowColData(i,1)
	var tInsuAccNo=LCInsureAccClassGrid.getRowColData(i,2)
	/*var strSQL="select polno,insuaccno,serialno,moneytype,money,unitcount,paydate,state from lcinsureacctrace  where 1=1 "
         + "and polno='"+ tPolNo +"'"
         +" and insuaccno='"+ tInsuAccNo +"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LCInsureAccontInputSql");
	mySql.setSqlId("LCInsureAccontSql2");
	mySql.addSubPara(tPolNo); 
	mySql.addSubPara(tInsuAccNo); 
    //alert(strSQL);        
    arr = easyExecSql( mySql.getString() );
    if (arr==null ||arr=="")
    {
		alert("未找到任何该帐户记价履历信息！");    
		return;    	
    }
    else
	{
        displayMultiline(arr,LCInsureAccTraceGrid);		
	}         
}
//查询初始化页面中部的保险帐户表记价履历表
function LCInsureAccTraceGridQuery()
{

}
//显示或者隐藏span域
function showDiv(spanID,divID)
{
    if (spanID != null)
    {
        document.all(divID).style.display="";
    }
    else
    {
        document.all(divID).style.display="none";
    }
}

