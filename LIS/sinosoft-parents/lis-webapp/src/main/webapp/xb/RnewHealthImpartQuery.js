//***********************************************
//�������ƣ�EdorHealthImpartQuery.js
//�����ܣ�������֪��ѯ
//�������ڣ�2005-06-23 11:10:36
//������  ��liurongxiao
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
  
//  var aSql=" select a.proposalcontno,a.contno,a.PolApplyDate,"
//          +" (case when a.appflag='0'"
//          +" then 'δ�б�' else '�ѳб�'"
//				  +" end)"
//				  +" from lccont a where a.contno in"
//				  +" (select distinct contno from lccustomerimpart where customerno='"+customerNo
//				  +"' and impartver = '02' union select distinct contno from lpcustomerimpart where customerno='"+customerNo
//				  +"' and impartver = '02')";	
  var aSql="";
  var sqlid1="RnewHealthImpartQuerySql3";
  var mySql1=new SqlClass();
	mySql1.setResourceName("xb.RnewHealthImpartQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(customerNo);//ָ������Ĳ���
	mySql1.addSubPara(customerNo);//ָ������Ĳ���
	aSql = mySql1.getString();
   
 
  var brr = easyExecSql(aSql);
 
  
  if(brr)
  {
    turnPage.queryModal(aSql, ContGrid);
  }
  else
  {
  	alert("�ÿͻ�û�н�����֪��Ϣ��");
  	divContInfo.style.display = "none";
  	divImpartInfo.style.display = "none";
  	return;
  	
  }
}


//��ѯ�ͻ���Ϣ
function queryPersonInfo()
{
//    var aSQL = "select CustomerNo, Name from LDPerson where CustomerNo = '" + customerNo + "'";
    var aSQL="";
    var sqlid1="RnewHealthImpartQuerySql2";
    var mySql1=new SqlClass();
	mySql1.setResourceName("xb.RnewHealthImpartQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(customerNo);//ָ������Ĳ���
	aSQL = mySql1.getString();
    var arrResult = easyExecSql(aSQL);
    if (arrResult != null)
    {
        document.all('CustomerNo').value = arrResult[0][0];
        document.all('CustomerName').value = arrResult[0][1];
    }
}

//��ʾ�����½�����֪��Ϣ
function contInfoClick(){
  
  var tSel = ContGrid.getSelNo();
  var tProposalContNo = ContGrid.getRowColData(tSel - 1,1);        //��ͬͶ������
	
//  var aSQL="select impartver,impartcode,impartcontent,ImpartParamModle"
//                +" from lccustomerimpart where customerno='"+customerNo+"' and proposalcontno='"+tProposalContNo+"' and impartver = '02'"
//                +" union select impartver,impartcode,impartcontent,impartParamModle"
//                +" from lpcustomerimpart where customerno='"+customerNo+"' and proposalcontno='"+tProposalContNo+"' and impartver = '02'";	
  var aSQL="";
  var sqlid1="RnewHealthImpartQuerySql1";
  var mySql1=new SqlClass();
	mySql1.setResourceName("xb.RnewHealthImpartQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(customerNo);//ָ������Ĳ���
	mySql1.addSubPara(tProposalContNo);//ָ������Ĳ���
	mySql1.addSubPara(customerNo);//ָ������Ĳ���
	mySql1.addSubPara(tProposalContNo);//ָ������Ĳ���
	aSQL = mySql1.getString();
  turnPage.queryModal(aSQL, ImpartGrid);
	
}


