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
	if(tContType!="1"){
		
	var sqlid826115051="DSHomeContSql826115051";
var mySql826115051=new SqlClass();
mySql826115051.setResourceName("uw.RiskTraceSql");//ָ��ʹ�õ�properties�ļ���
mySql826115051.setSqlId(sqlid826115051);//ָ��ʹ�õ�Sql��id
mySql826115051.addSubPara(PolNo);//ָ������Ĳ���
var aSQL=mySql826115051.getString();	
	
//  var aSQL=" select a.polno,a.operator,a.makedate,a.passflag,case a.passflag when '5' then '�Ժ˲�ͨ��' when 'z' then '�˱�����' else (select b.codename from ldcode b where b.codetype='uwstate' and b.code=a.PassFlag) end,a.changepolreason from LCUWSub a where a.polno='"+PolNo+"'"
//           
//           + " and a.autouwflag = '2' order by uwno/1" ;    
	}else{
		var sqlid826140634="DSHomeContSql826140634";
var mySql826140634=new SqlClass();
mySql826140634.setResourceName("uw.RiskTraceSql");//ָ��ʹ�õ�properties�ļ���
mySql826140634.setSqlId(sqlid826140634);//ָ��ʹ�õ�Sql��id
mySql826140634.addSubPara(tProposalNo);//ָ������Ĳ���
var aSQL=mySql826140634.getString();
		
//		var aSQL=" select a.polno,a.operator,a.makedate,a.passflag,case a.passflag when '5' then '�Ժ˲�ͨ��' when 'z' then '�˱�����' else (select b.codename from ldcode b where b.codetype='uwstate' and b.code=a.PassFlag) end,a.changepolreason from LCUWSub a where a.proposalno='"+tProposalNo+"'"
//        
////        + " and a.autouwflag = '2'"
//        + " order by uwno/1" ;    
	}
// prompt('',aSQL);
  turnPage.queryModal(aSQL, RiskTraceGrid);
 
}  
 