//程序名称：
//程序功能：
//创建日期：2007-01-04
//创建人  ：路明
//更新记录：  更新人    更新日期     更新原因/内容

var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var showInfo;
var k = 0;

/*********************************************************************
 *  查询交费记录
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClick(PrtNo)
{
	
	var sqlid825173716="DSHomeContSql825173716";
var mySql825173716=new SqlClass();
mySql825173716.setResourceName("uw.ShowTempFeeSql");//指定使用的properties文件名
mySql825173716.setSqlId(sqlid825173716);//指定使用的Sql的id
mySql825173716.addSubPara(PrtNo);//指定传入的参数
var strSQL=mySql825173716.getString();
	
//	var strSQL = "select tempfeeno,TempFeeType,RiskCode,PayMoney,EnterAccDate,ManageCom from LJTempFee where '"+k+"'='"+k+"'"
//	 							//+getWherePart('ljtempfee.otherno','PrtNo')
//	 							+" and ljtempfee.otherno='"+PrtNo+"'"
//	 							;
	 							
	turnPage.queryModal(strSQL, TempToGrid); 					
	
	var sqlid825173815="DSHomeContSql825173815";
var mySql825173815=new SqlClass();
mySql825173815.setResourceName("uw.ShowTempFeeSql");//指定使用的properties文件名
mySql825173815.setSqlId(sqlid825173815);//指定使用的Sql的id
mySql825173815.addSubPara(PrtNo);//指定传入的参数
var strSQL1=mySql825173815.getString();
	
//	var strSQL1 = "select tempfeeno,PayMode,PayMoney,EnterAccDate,ChequeNo,PayDate,BankCode,BankAccNo,AccName,InBankAccNo from LJTempFeeClass where '"+k+"'='"+k+"'"		
//	 							+" and tempfeeno in (select tempfeeno from ljtempfee where otherno =  '"+PrtNo+"')"
//	 							;
	 							
	turnPage1.queryModal(strSQL1, TempClassToGrid); 		 							
}

/*********************************************************************
 *  查询交费记录
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function QryTempFee()
{
   
 var strSQL = "";
   // var contno = "";
   
   var sqlid825173912="DSHomeContSql825173912";
var mySql825173912=new SqlClass();
mySql825173912.setResourceName("uw.ShowTempFeeSql");//指定使用的properties文件名
mySql825173912.setSqlId(sqlid825173912);//指定使用的Sql的id
mySql825173912.addSubPara(PrtNo);//指定传入的参数
strSQL=mySql825173912.getString();

//   strSQL = " select tempfeeno,AgentCode from LJTempFee where '"+k+"'='"+k+"'"
//    		 + " and ljtempfee.otherno='" + PrtNo + "'"   
//   			 +" group by tempfeeno,agentcode";
    
   var brr = easyExecSql(strSQL);		
   
   var sqlid825174005="DSHomeContSql825174005";
var mySql825174005=new SqlClass();
mySql825174005.setResourceName("uw.ShowTempFeeSql");//指定使用的properties文件名
mySql825174005.setSqlId(sqlid825174005);//指定使用的Sql的id
mySql825174005.addSubPara(PrtNo);//指定传入的参数
var strSQL1=mySql825174005.getString();			
   
//   var strSQL1= " select sum(PayMoney) from LJTempFee Where '"+k+"'='"+k+"'"
//    				 + " and ljtempfee.otherno='" + PrtNo + "'"
//             + " group by otherno";
   var brr1=easyExecSql(strSQL1);
   if(brr&&brr1)
   {
    	//document.all('TempFeeNo').value = brr[0][0];
    	document.all('AgentCode').value = brr[0][1];
      document.all('PayMoney').value = brr1[0][0];
    
    	easyQueryClick(PrtNo);
    }
    else
    {
   	alert("该保单未录入交费信息！");
   }
    
}

function QryCustomer()
{
	
	var strSQL = "";
	var tContType = fm.ContType.value;
    var strSQL = "";
    if (tContType == "2")
    {
        var sqlid825174048="DSHomeContSql825174048";
var mySql825174048=new SqlClass();
mySql825174048.setResourceName("uw.ShowTempFeeSql");//指定使用的properties文件名
mySql825174048.setSqlId(sqlid825174048);//指定使用的Sql的id
mySql825174048.addSubPara(CustomerNo);//指定传入的参数
strSQL=mySql825174048.getString();
        
//        strSQL = "select grpname from ldgrp where CustomerNo = '"+CustomerNo+"'";
    }
    else
    {
        var sqlid825174134="DSHomeContSql825174134";
var mySql825174134=new SqlClass();
mySql825174134.setResourceName("uw.ShowTempFeeSql");//指定使用的properties文件名
mySql825174134.setSqlId(sqlid825174134);//指定使用的Sql的id
mySql825174134.addSubPara(CustomerNo);//指定传入的参数
strSQL=mySql825174134.getString();
        
//        strSQL = "select Name from ldperson where CustomerNo = '"+CustomerNo+"'";
    }
	var crr = easyExecSql(strSQL); 
	
	if(crr != null)
	{
        document.all('CustomerName').value = crr[0][0]; 
	}
	
}   