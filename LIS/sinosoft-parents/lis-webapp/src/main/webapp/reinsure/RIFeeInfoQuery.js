var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIFeeInfoQuerySql";
function easyQueryClick()
{
	var feeno=fm.QureyFeeCode.value;
	var batchno=fm.QureyFeeBatchNo.value;
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
  var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIFeeInfoQuerySql1");//指定使用的Sql的id
	mySql.addSubPara(feeno);// 指定传入的参数，多个参数顺序添加
	var strSQL = mySql.getString();  
	var  result = easyExecSql(strSQL, 1, 0, 1);
	try{

		var clmStr="";
		var obStr="";
		for(i=0;i<result.length;i++){
			if(result[i][5]=='A02'||result[i][5]=='N02'){
				clmStr+=result[i][8]+"||'-'||"+result[i][7]+",";
				
			}else{
				if(result[i][4]=="InsuredSex"){
					clmStr+="(select codename from ldcode where code="+result[i][6]+" and codetype='sex'),";
				}else{
					clmStr+=result[i][6]+",";	
				}
			}
		}
		for(i=0;i<result.length;i++){
			if(result[i][5]=='A02'||result[i][5]=='N02'){
				obStr+=result[i][8];
			}else{
				obStr+=result[i][6];				
			}
			if(i<result.length-1){
					obStr+=",";
				}	
		}
		var mySql2 = new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId("RIFeeInfoQuerySql2");//指定使用的Sql的id
		mySql2.addSubPara(clmStr);// 指定传入的参数，多个参数顺序添加
		mySql2.addSubPara(feeno);// 指定传入的参数，多个参数顺序添加
		mySql2.addSubPara(batchno);// 指定传入的参数，多个参数顺序添加
		mySql2.addSubPara(obStr);// 指定传入的参数，多个参数顺序添加
		var strSQL = mySql2.getString();  
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
function afterCodeSelect( CodeName, Field ) {
	if(CodeName=="rifeerate"){
		fm.QureyFeeBatchName.value="";
		fm.QureyFeeBatchNo.value="";
	}
	FeeGrid.clearData();
}
