//***********************************************
//�������ƣ�RiskTrace.js
//�����ܣ����ֺ˱��켣��ѯ 
//�������ڣ�2005-06-27 11:10:36
//������  ��ccvip
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sqlresourcename = "uwgrp.EdorRiskTraceSql";
 
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

function queryRiskTrace(){
 // alert(PolNo); 
 // var aSQL="select a.polno,a.operator,a.makedate,a.passflag,(select b.codename from ldcode b where b.codetype='uwstate' and trim(b.code)=trim(a.PassFlag) )from LPUWSub a where a.polno='"+PolNo+"'Union select c.polno,c.operator,c.makedate,c.passflag,(select d.codename from ldcode d where d.codetype='uwstate' and trim(d.code)=trim(c.PassFlag) )from LPUWMaster c where c.polno='"+PolNo+"'";	     


		var sqlid1="EdorRiskTraceSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(PolNo);
		mySql1.addSubPara(PolNo);
   
  turnPage.queryModal(mySql1.getString(), RiskTraceGrid);
 
}  
 