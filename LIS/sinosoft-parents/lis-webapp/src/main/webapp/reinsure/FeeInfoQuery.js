var turnPage = new turnPageClass();
function easyQueryClick()
{
	var feeno=fm.QureyFeeCode.value;
	var batchno=fm.QureyBatchNo.value;
	if(feeno==""||batchno==""){
		myAlert(""+"请选择费率表代码和费率表批次"+"");
		return false;
	}
	// 初始化表格
	initFeeGrid();
	divFeeGrid.style.display="";
	divFeeInfo.style.display="";
	var i=0;
    var iArray = new Array();
  	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.FeeInfoQuerySql"); //指定使用的properties文件名
		mySql100.setSqlId("FeeInfoQuerySql100");//指定使用的Sql的id
		mySql100.addSubPara(feeno);//指定传入的参数
	    strSQL=mySql100.getString();
  	
    //var strSQL = "select * from RIFeeRateTableClumnDef where feetableno='"+feeno+"' order by feeclumnno";
	var  result = new Array();

	try{
		result=easyExecSql(strSQL, 1, 0, 1);
	  strSQL="select ";
		for(i=0;i<result.length;i++){
			if(result[i][5]=='A02'||result[i][5]=='N02'){
				strSQL+=result[i][8]+"||'-'||"+result[i][7]+",";
				
			}else{
				if(result[i][4]=="InsuredSex"){
					strSQL+="case "+result[i][6]+" when '1' then '"+"女"+"' when '0' then '"+"男"+"' end,";
				}else{
					strSQL+=result[i][6]+",";	
				}
			}
		}
		var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.FeeInfoQuerySql"); //指定使用的properties文件名
		mySql101.setSqlId("FeeInfoQuerySql101");//指定使用的Sql的id
		mySql101.addSubPara(feeno);//指定传入的参数
		mySql101.addSubPara(batchno);//指定传入的参数
	    strSQL1=mySql101.getString();
		//strSQL+="feerate from RIFeeRateTable where feetableno='"+feeno+"' and batchno='"+batchno+"' order by ";
		strSQL+=strSQL1;
		
		for(i=0;i<result.length;i++){
			if(result[i][5]=='A02'||result[i][5]=='N02'){
				strSQL+=result[i][8];
				
			}else{
				strSQL+=result[i][6];				
			}
			if(i<result.length-1){
					strSQL+=",";
				}	
		}
		turnPage.queryModal(strSQL, FeeGrid);
	}catch(ex){
		myAlert(""+"查询费率表失败!"+"");
		return;
	}
}

function resetInput(){
	fm.reset();	
	FeeGrid.clearData();
}
function afterCodeSelect( cCodeName, Field ) {
	FeeGrid.clearData();
}
