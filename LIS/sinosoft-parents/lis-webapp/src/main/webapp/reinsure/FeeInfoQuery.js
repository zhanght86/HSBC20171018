var turnPage = new turnPageClass();
function easyQueryClick()
{
	var feeno=fm.QureyFeeCode.value;
	var batchno=fm.QureyBatchNo.value;
	if(feeno==""||batchno==""){
		myAlert(""+"��ѡ����ʱ����ͷ��ʱ�����"+"");
		return false;
	}
	// ��ʼ�����
	initFeeGrid();
	divFeeGrid.style.display="";
	divFeeInfo.style.display="";
	var i=0;
    var iArray = new Array();
  	var mySql100=new SqlClass();
		mySql100.setResourceName("reinsure.FeeInfoQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql100.setSqlId("FeeInfoQuerySql100");//ָ��ʹ�õ�Sql��id
		mySql100.addSubPara(feeno);//ָ������Ĳ���
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
					strSQL+="case "+result[i][6]+" when '1' then '"+"Ů"+"' when '0' then '"+"��"+"' end,";
				}else{
					strSQL+=result[i][6]+",";	
				}
			}
		}
		var mySql101=new SqlClass();
		mySql101.setResourceName("reinsure.FeeInfoQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql101.setSqlId("FeeInfoQuerySql101");//ָ��ʹ�õ�Sql��id
		mySql101.addSubPara(feeno);//ָ������Ĳ���
		mySql101.addSubPara(batchno);//ָ������Ĳ���
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
		myAlert(""+"��ѯ���ʱ�ʧ��!"+"");
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
