var turnPage = new turnPageClass(); 
var turnPage2 = new turnPageClass(); 
function LCInsureAccFeeQuery()
{

  initLCInsureAccFeeGrid(); 
  
  var sqlid826092552="DSHomeContSql826092552";
var mySql826092552=new SqlClass();
mySql826092552.setResourceName("sys.GpsLCInsureAccFeeQuerySql");//指定使用的properties文件名
mySql826092552.setSqlId(sqlid826092552);//指定使用的Sql的id
mySql826092552.addSubPara(tPolNo);//指定传入的参数
var strSQL=mySql826092552.getString();


//	var strSQL = "select b.InsuAccNo,b.AccType,b.AccFoundDate,b.BalaDate,(select nvl(sum(fee),0) from LCInsureAccClassFee a where a.PolNo=b.PolNo and a.InsuAccNo = b.InsuAccNo) from LCInsureAccFee b where b.PolNo='"
//    				 +  tPolNo +"'";
  				 	
 
  turnPage2.pageLineNum=9;    //每页2条
  turnPage2.queryModal(strSQL,LCInsureAccFeeGrid);

  
	
}

function LCInsureAccClassFeeQueryb()
{
 
	initLCInsureAccClassFeeGrid();
	
	var sqlid826092851="DSHomeContSql826092851";
var mySql826092851=new SqlClass();
mySql826092851.setResourceName("sys.GpsLCInsureAccFeeQuerySql");//指定使用的properties文件名
mySql826092851.setSqlId(sqlid826092851);//指定使用的Sql的id
mySql826092851.addSubPara(tPolNo);//指定传入的参数
mySql826092851.addSubPara(cInsuAccNo);//指定传入的参数
var strSQL=mySql826092851.getString();
  
//	var strSQL = "select InsuAccNo,PayPlanCode,OtherNo,OtherType,AccFoundDate, BalaDate,AccAscription,Fee from LCInsureAccClassFee where PolNo='"
//    				 +  tPolNo +"'" + "  and InsuAccNo='" + cInsuAccNo +"' order by otherno";
  				 	
 

  
  turnPage.pageLineNum=9;    //每页2条
  turnPage.queryModal(strSQL,LCInsureAccClassFeeGrid);
  
	
}


function LCInsureAccClassFeeQuery(){
	
	var tSel = LCInsureAccFeeGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先在帐户查询信息中选择一条记录." );
	else
	{ divLCPol1.style.display=""
	  cInsuAccNo = LCInsureAccFeeGrid.getRowColData( tSel - 1, 1 );
		try
		{ 
		  LCInsureAccClassFeeQueryb();
		  
		}
		catch(ex)
		{
			alert( ex );
		}
	}
	
}

function GoBack(){
	
	parent.window.focus();
	window.close();
	
	
	
}

function LCInsureAccTrace(){
	/*var cInsuAccNo="1"
	var cPayPlanCode="2"
	window.open("./GpsLCInsureAccTraceQuery.jsp?PolNo="+ tPolNo + "&" +  "InsuAccNo=" + cInsuAccNo + "&" + "PayPlanCode=" + cPayPlanCode);*/
	var arrReturn = new Array();
	var tSel = LCInsureAccClassGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "请先在子帐户查询信息中选择一条记录." );
	else
	{
		var cInsuAccNo = LCInsureAccClassGrid.getRowColData( tSel - 1, 1 );
		var cPayPlanCode =  LCInsureAccClassGrid.getRowColData( tSel - 1, 2 );
		if(cInsuAccNo!="")
		{   try
		    {
			     window.open("./GpsLCInsureAccTraceQuery.jsp?PolNo="+ tPolNo + "&" +  "InsuAccNo=" + cInsuAccNo + "&" + "PayPlanCode=" + cPayPlanCode,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
		  
		    }
		    catch(ex)
		    {
			    alert( "没有发现父窗口的afterQuery接口。" + ex );
		    }

    }
   else{ alert( "请先查询子帐户查询信息." ); return;}

	}  
}
	