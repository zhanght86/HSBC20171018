//***********************************************
//�������ƣ�ContTrace.js
//�����ܣ���ͬ�˱��켣��ѯ
//�������ڣ�2005-06-27 11:10:36
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
 *  ��ѯ���ֺ˱��켣
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function queryContTrace(){
  //alert("ContNo="+ContNo);
  
  var sqlid826141102="DSHomeContSql826141102";
var mySql826141102=new SqlClass();
mySql826141102.setResourceName("uw.GrpContTraceSql");//ָ��ʹ�õ�properties�ļ���
mySql826141102.setSqlId(sqlid826141102);//ָ��ʹ�õ�Sql��id
mySql826141102.addSubPara(ContNo);//ָ������Ĳ���
var aSQL=mySql826141102.getString();
  
//  var aSQL="select 1,a.operator,a.makedate,a.passflag,case passflag when '5' then '�Ժ˲�ͨ��' when 'z' then '�˱�����' else (select b.codename from ldcode b where b.codetype='uwstate' and b.code=a.PassFlag) end,uwidea from LCCUWSub a where a.grpcontno='"+ContNo+"'  and a.passflag is not null "

  //alert(aSQL);
  turnPage.queryModal(aSQL, ContTraceGrid);

}
