//***********************************************
//�������ƣ�EdorRiskTrace.js
//�����ܣ����ֺ˱��켣��ѯ 
//�������ڣ�2005-07-13 11:10:36
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

function queryRiskTrace()
{
  
  var strSql = "   
//  select a.polno,a.operator,a.makedate,a.passflag,"
//                 +"(select b.codename from ldcode b where b.codetype='edoruwstate' and trim(b.code)=trim(a.PassFlag) ) "
//                 +"from LPUWSub a where a.polno='"+PolNo+"' "
//                 +"and a.EdorNo = '"+EdorNo+"' and a.autouwflag = '2' order by makedate ";	  
  var strSql = "";
  var sqlid1="EdorRiskTraceSql1";
  var mySql1=new SqlClass();
  mySql1.setResourceName("bq.EdorRiskTraceSql"); //ָ��ʹ�õ�properties�ļ���
  mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
  mySql1.addSubPara(PolNo);//ָ������Ĳ���
  mySql1.addSubPara(EdorNo);//ָ������Ĳ���
  strSql=mySql1.getString();
  var brr=easyExecSql(strSql);
  if(brr)
  {
    turnPage.queryModal(strSql, RiskTraceGrid);
  }
  else
  {
  	alert("��������δ�¹��˱����ۣ�");
  	return ;
  }
 
}  
 