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
  
//  var aSQL="select a.proposalcontno,a.contno,a.PolApplyDate,"
//          +" (case when a.appflag='0'"
//          +" then 'δ�б�' else '�ѳб�'"
//				  +" end)"
//				  +" from lccont a where a.contno in (select distinct contno from lccustomerimpart where customerno='"+customerNo+"')";	
// // alert(aSQL);
  
     var sqlid1="HealthImpartQuerySql1";
	 var mySql1=new SqlClass();
	 mySql1.setResourceName("uw.HealthImpartQuerySql");
	 mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
	 mySql1.addSubPara(customerNo);//ָ���������
	 var aSQL = mySql1.getString();
  
  turnPage.queryModal(aSQL, ContGrid);

}



function queryPersonInfo(){
//  var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";
  
     var sqlid2="HealthImpartQuerySql2";
	 var mySql2=new SqlClass();
	 mySql2.setResourceName("uw.HealthImpartQuerySql");
	 mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
	 mySql2.addSubPara(customerNo);//ָ���������
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
  var tProposalContNo = ContGrid.getRowColData(tSel - 1,1);        //��ͬͶ������
	
//  var aSQL="select impartver,impartcode,impartcontent,ImpartParamModle from lccustomerimpart where customerno='"+customerNo+"' and proposalcontno='"+tProposalContNo+"' and ImpartVer in('101','A01','B01','C01','D01','A05','A06','B05')";	
 
     var sqlid3="HealthImpartQuerySql3";
	 var mySql3=new SqlClass();
	 mySql3.setResourceName("uw.HealthImpartQuerySql");
	 mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
	 mySql3.addSubPara(customerNo);//ָ���������
	 mySql3.addSubPara(tProposalContNo);//ָ���������
	 var aSQL = mySql3.getString();
  
  turnPage.queryModal(aSQL, ImpartGrid);
	
}


