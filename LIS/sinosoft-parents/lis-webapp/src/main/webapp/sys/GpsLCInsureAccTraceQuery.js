var turnPage = new turnPageClass();

function GoBack(){
	parent.window.focus();
	
  window.close();
}

function LCInsureAccTraceQuery(){
	

	initGrpPolGrid();
	
	var sqlid826092258="DSHomeContSql826092258";
var mySql826092258=new SqlClass();
mySql826092258.setResourceName("sys.GpsLCInsureAccTraceQuerySql");//ָ��ʹ�õ�properties�ļ���
mySql826092258.setSqlId(sqlid826092258);//ָ��ʹ�õ�Sql��id
mySql826092258.addSubPara(tPolNo);//ָ������Ĳ���
mySql826092258.addSubPara(tInsuAccNo);//ָ������Ĳ���
mySql826092258.addSubPara(tPayPlanCode);//ָ������Ĳ���
var strSQL=mySql826092258.getString();

//	var strSQL = "select InsuAccNo,PayPlanCode,PayDate,OtherNo,OtherType,Money,MoneyType,makedate,maketime from LCInsureAccTrace where PolNo='"
//    				 +  tPolNo +"' and InsuAccNo='" + tInsuAccNo + "' and PayPlanCode='" + tPayPlanCode + "' order by makedate,maketime";
  				 

 
  
 turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
 
  //�ж��Ƿ��ѯ�ɹ�
 if (!turnPage.strQueryResult) {
    
  
    alert("��ѯʧ�ܣ�");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = GrpPolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	
	
	
	
}