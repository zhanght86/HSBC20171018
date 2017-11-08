var turnPage = new turnPageClass();
var sqlresourcename = "reinsure.RIFeeInfoQuerySql";
function easyQueryClick()
{
	var feeno=fm.QureyFeeCode.value;
	var batchno=fm.QureyFeeBatchNo.value;
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
  var mySql = new SqlClass();
	mySql.setResourceName(sqlresourcename);
	mySql.setSqlId("RIFeeInfoQuerySql1");//ָ��ʹ�õ�Sql��id
	mySql.addSubPara(feeno);// ָ������Ĳ������������˳�����
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
		mySql2.setSqlId("RIFeeInfoQuerySql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(clmStr);// ָ������Ĳ������������˳�����
		mySql2.addSubPara(feeno);// ָ������Ĳ������������˳�����
		mySql2.addSubPara(batchno);// ָ������Ĳ������������˳�����
		mySql2.addSubPara(obStr);// ָ������Ĳ������������˳�����
		var strSQL = mySql2.getString();  
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
function afterCodeSelect( CodeName, Field ) {
	if(CodeName=="rifeerate"){
		fm.QureyFeeBatchName.value="";
		fm.QureyFeeBatchNo.value="";
	}
	FeeGrid.clearData();
}
