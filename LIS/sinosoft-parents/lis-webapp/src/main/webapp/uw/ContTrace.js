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
  var sqlid826140829="DSHomeContSql826140829";
var mySql826140829=new SqlClass();
mySql826140829.setResourceName("uw.ContTraceSql");//ָ��ʹ�õ�properties�ļ���
mySql826140829.setSqlId(sqlid826140829);//ָ��ʹ�õ�Sql��id
mySql826140829.addSubPara(ContNo);//ָ������Ĳ���
var aSQL=mySql826140829.getString();
  
//  var aSQL="select 1,a.operator,a.makedate,a.passflag,case passflag when '5' then '�Ժ˲�ͨ��' when 'z' then '�˱�����' else (select b.codename from ldcode b where b.codetype='contuwstate' and b.code=a.PassFlag) end,uwidea from LCCUWSub a where a.contno='"+ContNo+"'  and a.passflag is not null order by uwno/1"

//  prompt("",aSQL);
  turnPage.queryModal(aSQL, ContTraceGrid);

}
