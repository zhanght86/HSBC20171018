//***********************************************
//�������ƣ�EdorContTrace.js  
//�����ܣ���ȫ��ͬ�˱��켣��ѯ  
//�������ڣ�2005-07-13 10:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���
var showInfo;
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
 *  ��ѯ���ֺ˱��켣
 *  ����  ��  ��
 *  ����ֵ��  ��
 ********************************************************************* 
 */

function queryContTrace()
{
  
//  var strSql = "select 1,a.operator,a.makedate,a.passflag,"
//                 +"(select b.codename from ldcode b where b.codetype='edorcontuwstate' and trim(b.code)=trim(a.PassFlag) ) "
//                 +"from LPCUWSub a where a.contno='"+ContNo+"' "
//                 +"and a.EdorNo = '"+EdorNo+"' and a.autouwflag = '2' order by makedate ";
//  
    var sqlid1="EdorContTraceSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName("bq.EdorContTraceSql");
	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
	mySql1.addSubPara(ContNo);//ָ���������
	mySql1.addSubPara(EdorNo);
	var strSql = mySql1.getString();

  var brr=easyExecSql(strSql);
  if(brr)
  {
    turnPage.queryModal(strSql, ContTraceGrid);
  }
  else
  {
  	alert("�ñ�����δ�¹��˱����ۣ�");
  	return ;
  }
}   
