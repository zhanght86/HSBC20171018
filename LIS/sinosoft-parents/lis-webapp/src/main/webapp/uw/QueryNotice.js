//***********************************************
//�������ƣ�QueryNotice.js
//�����ܣ��ѷ���֪ͨ���ѯ 
//�������ڣ�2006-11-17 17:05
//������  ��haopan
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���

var turnPage = new turnPageClass();

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
 *  ��ѯ֪ͨ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 function queryNotice()
 {
 	//alert(GrpContNo);
 	var strSQl="";
// 	strSQl="select prtseq, case code "
// 							+" when 'G03' then '���֪ͨ��'  "
// 							+" when 'G04' then '����֪ͨ��'   "	
// 							+" when '50'  then '����˱�֪ͨ��'	"
// 							+" when '53'  then '�������ڳб�֪ͨ��'	"
// 							+" when '57'  then '�������ڽ���'	"
// 							+" when '54'  then '�ŵ�����Լ�����֪ͨ��'	"
// 							+" when '56'  then '�ŵ�����Լ�ͻ��ϲ�֪ͨ��'	"
// 							+" when '75'  then '����˱�Ҫ��֪ͨ��'	"
// 							+" when '76'  then '�ŵ��˱�����֪ͨ��'	"
// 							+" when '78'  then '�ŵ��罻�˷�֪ͨ��'	"
// 							+" when 'BQ94' then '��ȫ����˱�����֪ͨ��'"
// 							+" when 'BQ95' then '��ȫ����˱�Ҫ��֪ͨ��'"
//							+" 	End,case stateflag when '0' then '�ѷ��Ŵ���ӡ' when '1' then '�Ѵ�ӡ������' when '2' then '�ѻ���' end "
//			+" from loprtmanager  where otherno='"+GrpContNo+"' or  standbyflag1='"+GrpContNo+"' or standbyflag2='"+GrpContNo+"' or standbyflag3='"+GrpContNo+"'";
 	    var  sqlid1="QueryNoticeSql0";
	 	var  mySql1=new SqlClass();
	 	mySql1.setResourceName("uw.QueryNoticeSql"); //ָ��ʹ�õ�properties�ļ���
	 	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	 	mySql1.addSubPara(GrpContNo);//ָ������Ĳ���
	 	mySql1.addSubPara(GrpContNo);//ָ������Ĳ���
	 	mySql1.addSubPara(GrpContNo);//ָ������Ĳ���
	 	mySql1.addSubPara(GrpContNo);//ָ������Ĳ���
	 	strSQl=mySql1.getString();
	 turnPage.queryModal(strSQl, NoticeGrid);
	 
	   

			
}
