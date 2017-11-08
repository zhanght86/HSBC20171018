
//程序名称：BankTraceQuery.js
//程序功能：银行转账轨迹查询
//创建日期：2010-04-12
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容

//var arrDataSet;
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//简单查询

function easyPrint()
{
	easyQueryPrint(2,'CodeGrid','turnPage');
}

function easyQuery()
{	
	if (fm.Tempfeeno.value == "" & fm.Actugetno.value == "" & fm.Otherno.value == "")
	{
			alert("收据号/实付号/业务号/三者不能都为空!");
			return false;
	}
	if (fm.Tempfeeno.value != "" & fm.Actugetno.value != "" )
	{
			alert("收据号/实付号/只能录入其中之一!");
			return false;
	}
	initCodeGrid();
	
	var tempfeeno="";
	var actugetno="";
	var otherno=fm.Otherno.value;
	/*var str1="";
	var str2="";
	var strSQL=""*/
	
	if (fm.Tempfeeno.value != ""  )
	{
		tempfeeno=fm.Tempfeeno.value;
		//str1= "  and dealtype='S' and paycode='"+tempfeeno+"'" ;
		mySql = new SqlClass();
		mySql.setResourceName("finfee.BankTraceQuerySql");
		mySql.setSqlId("BankTraceQuerySql1");
		mySql.addSubPara(tempfeeno);          
		mySql.addSubPara(otherno);  
	}
	if (fm.Actugetno.value != ""  )
	{
		actugetno=fm.Actugetno.value;
		//str1= " and dealtype='F'  and paycode='"+actugetno+"'" ;
		mySql = new SqlClass();
		mySql.setResourceName("finfee.BankTraceQuerySql");
		mySql.setSqlId("BankTraceQuerySql2");
		mySql.addSubPara(actugetno);          
		mySql.addSubPara(otherno);   
	}
	
	if (otherno != "" )
	{     
          //     str2= " and  polno='"+otherno+"'" ;
	}
	
               
     /*   strSQL = "select bankcode,serialno,senddate,bankdealdate,(select codename from ldcode1 where codetype like '%bankerror%' and code=a.bankcode and code1=a.banksuccflag),accno,accname,paymoney,paycode,polno,notype" 
                   + " from lyreturnfrombankb a"
                   + " where 1=1 "
                   + str1
                   + str2                                                     
                   + " order by 3";*/
	//alert(code);
	// 书写SQL语句
	
	 
        //alert(strSQL);    
//      prompt('',strSQL);
	turnPage.queryModal(mySql.getString(), CodeGrid);    

}


