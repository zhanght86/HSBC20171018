//***********************************************
//�������ƣ�RecordQuery.js
//�����ܣ�����������ѯ
//�������ڣ�2005-06-23 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();

var sqlresourcename = "uwgrp.RecordQuerySql";
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
 *  ��ѯ����������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function queryRecord(){
  //alert(ContNo); 
  //var aSQL="select a.contno,a.operator,a.makedate, 1 ,(select c.codename from ldcode c where c.codetype='busitype' and trim(c.code)=trim(a.businesstype) )from ldconttime a where a.contno='"+ContNo+"' order by serialno";	
  //alert(aSQL);
  var sqlid1="RecordQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(ContNo);
		
  turnPage.queryModal(mySql1.getString(), RecordGrid);

}



//function queryPersonInfo(){
//  var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
//  var arrResult = easyExecSql(aSQL);
//  fm.all('CustomerNo').value = arrResult[0][0];
//  fm.all('CustomerName').value = arrResult[0][1];
//}

//function getContDetail(){
//	//alert();
//	
//}



//function contInfoClick(){
//  
//  var tSel = ContGrid.getSelNo();
//  var tProposalContNo = ContGrid.getRowColData(tSel - 1,1);        //��ͬͶ������
//	
//  var aSQL="select impartver,impartcode,impartcontent,ImpartParamModle from lccustomerimpart where customerno='"+customerNo+"' and proposalcontno='"+tProposalContNo+"'";	
// 
//  turnPage.queryModal(aSQL, ImpartGrid);
//	
//}


