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
var sqlresourcename = "uwgrp.HealthImpartQuerySql";

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
  /*
  var aSQL="select a.proposalcontno,a.contno,a.PolApplyDate,"
          +" (case when a.appflag='0'"
          +" then 'δ�б�' else '�ѳб�'"
				  +" end)"
				  +" from lccont a where a.contno in (select distinct contno from lccustomerimpart where customerno='"+customerNo+"')";	
 // alert(aSQL);
  */
  var sqlid1="HealthImpartQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(customerNo);
  
  
  turnPage.queryModal(mySql1.getString(), ContGrid);

}



function queryPersonInfo(){
 // var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
  
    var sqlid2="HealthImpartQuerySql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(customerNo);
  
  var arrResult = easyExecSql(mySql2.getString());
  fm.all('CustomerNo').value = arrResult[0][0];
  fm.all('CustomerName').value = arrResult[0][1];
}

function getContDetail(){
	//alert();
	
}



function contInfoClick(){
  
  var tSel = ContGrid.getSelNo();
  var tProposalContNo = ContGrid.getRowColData(tSel - 1,1);        //��ͬͶ������
	
  //var aSQL="select impartver,impartcode,impartcontent,ImpartParamModle from lccustomerimpart where customerno='"+customerNo+"' and proposalcontno='"+tProposalContNo+"' and ImpartVer='02'";	
 
 var sqlid3="HealthImpartQuerySql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(customerNo);
		mySql3.addSubPara(tProposalContNo);
 
  turnPage.queryModal(mySql3.getString(), ImpartGrid);
	
}


