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
var sqlresourcename = "uwgrp.EdorContTraceSql";
 
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
  //var aSQL="select 1,a.operator,a.makedate,a.passflag,(select b.codename from ldcode b where b.codetype='contuwstate' and trim(b.code)=trim(a.PassFlag) )from LPCUWSub a where a.contno='"+ContNo+"'Union select 1,c.operator,c.makedate,c.passflag,(select d.codename from ldcode d where d.codetype='contuwstate' and trim(d.code)=trim(c.PassFlag) )from LPCUWMaster c where c.contno='"+ContNo+"'";	   
  
  var sqlid1="EdorContTraceSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(ContNo);
		mySql1.addSubPara(ContNo);
		
  
  turnPage.queryModal(mySql1.getString(), ContTraceGrid);
 
}   
