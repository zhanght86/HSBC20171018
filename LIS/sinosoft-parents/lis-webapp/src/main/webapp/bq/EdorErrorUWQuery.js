//***********************************************
//�������ƣ�EdorErrorUWQuery.js
//�����ܣ����κ˱���ѯ
//�������ڣ�2008-07-21 19:10:36
//������  ��pst
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();

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
 *  ��ѯ�����˱���Ϣ 
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function queryEdorMain() 
{
  
//   var strSql="select distinct UWRuleCode,UWError,EdorAcceptNo,edorno,edortype,ContNo"
//            + " from LPEdorUWError where EdorAcceptNo='"+EdorAcceptNo+ "'";
   
    var sqlid1="EdorErrorUWQuerySql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.EdorErrorUWQuerySql");
	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
	mySql1.addSubPara(EdorAcceptNo);//ָ���������
	var strSql = mySql1.getString();

    var crr = easyExecSql(strSql, 1, 0,"","",1);
    if (!crr)
    {
        alert("��ȫ������û�г��κ˱���Ϣ��");
        return;
    }
    else
    {
        initEdorMainGrid();
        turnPage.queryModal(strSql, EdorMainGrid);
    }   
}


 function showApproveInfo()
 {

 	//��дSQL���
// 	var infoSql="";
// 	infoSql="select edoracceptno,operator,approveoperator,approvedate,"
// 	       +" (select codename from ldcode where codetype='edorapprovereason' and code = modifyreason),"
// 	       +" approvecontent"
// 	       +" from LPApproveTrack a"
// 	       +" where EdorAcceptNo = '"+EdorAcceptNo+"' order by approvedate,approvetime";
 	
 	var sqlid2="EdorErrorUWQuerySql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName("bq.EdorErrorUWQuerySql");
	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
	mySql2.addSubPara(EdorAcceptNo);//ָ���������
	var infoSql = mySql2.getString();
 	
 	turnPage1.queryModal(infoSql, InfoGrid);
 	
 	return true;
}