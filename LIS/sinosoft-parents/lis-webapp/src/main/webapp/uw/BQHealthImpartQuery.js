//***********************************************
//�������ƣ�HealthImpartQuery.js
//�����ܣ�������֪��ѯ
//�������ڣ�2005-06-18 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();


/*********************************************************************
 *  ������һҳ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function returnParent(){
  top.close();	
	
}


/*********************************************************************
 *  ��ѯ������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function queryCont(){
  
//  var aSQL="select edorno,proposalcontno,contno,PolApplyDate,state from ("
//  		  +"select '' edorno,a.proposalcontno,a.contno,a.PolApplyDate,"
//          +" (case trim(appflag) when '0' else 'δ�б�' when '1' then '��Ч' when '4' then '��ֹ' end) state"
//				  +" from lccont a where a.contno in (select distinct contno from lccustomerimpart where customerno='"+customerNo+"' and ImpartVer in('101','A01','B01','C01','D01','A05','A06'))"
//				  +" and not exists (select contno from lpcont where contno=a.contno ) "
//		  +" union all "
//		  +"select a.edorno,a.proposalcontno,a.contno,a.PolApplyDate,"
//          +" (case trim(appflag) when '1' then '��Ч' when '4' then '��ֹ' end) state"
//				  +" from lpcont a where a.contno in (select distinct contno from("
//				  + " select contno from lccustomerimpart where customerno='"+customerNo+"' and ImpartVer in('101','A01','B01','C01','D01','A05','A06')"
//				  + " union all select contno from lpcustomerimpart where customerno='"+customerNo+"' and ImpartVer in('101','A01','B01','C01','D01','A05','A06')))"
//		  +")";	
//  //alert(aSQL);
  
    var sqlid1="BQHealthImpartQuerySql1";                              
	var mySql1=new SqlClass();                                            
	mySql1.setResourceName('uw.BQHealthImpartQuerySql'); //ָ��ʹ�õ�properties�ļ���         
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id                                
	mySql1.addSubPara(customerNo);//�������                             
	mySql1.addSubPara(customerNo);//�������                                      
	mySql1.addSubPara(customerNo);//�������                                                   
	var aSQL = mySql1.getString();   
  
  turnPage.queryModal(aSQL, ContGrid);

}

function queryPersonInfo(){
//  var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
  
    var sqlid2="BQHealthImpartQuerySql2";                              
	var mySql2=new SqlClass();                                            
	mySql2.setResourceName('uw.BQHealthImpartQuerySql'); //ָ��ʹ�õ�properties�ļ���         
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id                                
	mySql2.addSubPara(customerNo);//�������                                                                                
	var aSQL = mySql2.getString();   

  
  var arrResult = easyExecSql(aSQL);
  document.all('CustomerNo').value = arrResult[0][0];
  document.all('CustomerName').value = arrResult[0][1];
}

function getContDetail(){
	//alert();
	
}



function contInfoClick(){
  
  var tSel = ContGrid.getSelNo();
  var tEdorNo = ContGrid.getRowColData(tSel - 1,1); 
  var tProposalContNo = ContGrid.getRowColData(tSel - 1,1);        //��ͬͶ������
  var tContNo = ContGrid.getRowColData(tSel - 1,3); 
	
//  var aSQL="select impartver,impartcode,impartcontent,ImpartParamModle from lccustomerimpart where customerno='"+customerNo+"' and contno='"+tContNo+"' and ImpartVer in('101','A01','B01','C01','D01','A05','A06')"
//           + " union all "
//           + "select impartver,impartcode,impartcontent,ImpartParamModle from lpcustomerimpart where customerno='"+customerNo+"' and edorno='"+tEdorNo+"' and contno='"+tContNo+"' and ImpartVer in('101','A01','B01','C01','D01','A05','A06')";	
 
    var sqlid3="BQHealthImpartQuerySql3";                              
	var mySql3=new SqlClass();                                            
	mySql3.setResourceName('uw.BQHealthImpartQuerySql'); //ָ��ʹ�õ�properties�ļ���         
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id                                
	mySql3.addSubPara(customerNo);//�������     
	mySql3.addSubPara(tContNo);//�������     
	mySql3.addSubPara(customerNo);//�������     
	mySql3.addSubPara(tEdorNo);//�������     
	mySql3.addSubPara(tContNo);//�������     
	var aSQL = mySql3.getString();   

  
  turnPage.queryModal(aSQL, ImpartGrid);
	
}


