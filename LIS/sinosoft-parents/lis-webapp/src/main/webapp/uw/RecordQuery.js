//***********************************************
//�������ƣ�RecordQuery.js
//�����ܣ�����������ѯ
//�������ڣ�2005-06-23 11:10:36
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
 *  ��ѯ����������Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */

function queryRecord(){
  //alert(ContNo); 
 // var aSQL="select a.contno,a.operator,a.makedate, 1 ,(select c.codename from ldcode c where c.codetype='busitype' and trim(c.code)=trim(a.businesstype) )from ldconttime a where a.contno='"+ContNo+"' order by serialno";	
  //alert(aSQL);
//  var aSQL = " select '"+ContNo+"',"
//           + " (select username from lduser where usercode = defaultoperator),"
//           + " to_char(indate, 'YYYY-MM-DD') || ' ' || intime || ' �� ' ||"
//           + " to_char(outdate, 'YYYY-MM-DD') || ' ' || outtime,'1',"
//           + " (select activityname from lwactivity where activityid = a.activityid)"
//           + " from lbmission a"
//           + " where missionprop1 = '"+ContNo+"'"
//           + " and defaultoperator is not null"
//           + " order by makedate, maketime"
//           ;
      /*     
  var aSQL = "select a1,a2,a3,a4,a5,a6,a9"
           + " from (select '"+ContNo+"' as a1,"
           + " nvl((select usercode"
           + " from lduser"
           + " where '1135241645000' = '1135241645000'"
           + " and usercode = defaultoperator),"
           + " '') as a2,"
           + " to_char(indate, 'YYYY-MM-DD') || ' ' || intime as a3,"
           + " to_char(outdate, 'YYYY-MM-DD') || ' ' || outtime as a4,"
           + " '1' as a5,"
           + " (select activityname from lwactivity where activityid = a.activityid) as a6,"
           + " makedate as a7,"
           + " maketime as a8,"
           + " (case when activityid='0000001110' then decode(missionprop11,'1','�ϱ��б�','4','�����¼�','����Լ�˱�') end ) a9"
           + " from lbmission a"
           + " where 1 = 1 "
           + " and missionid = (select missionid"
           + " from lbmission"
           + " where missionprop1 = '"+ContNo+"'"
           + " and rownum = 1)"
           + " and activityid not in ('0000001270', '0000001104', '0000001101')"
           + " and outdate is not null"
           + " union"
           + " select '"+ContNo+"' as a1,"
           + " '' as a2,"
           + " to_char(indate, 'YYYY-MM-DD') || ' ' || intime as a3,"
           + " to_char(outdate, 'YYYY-MM-DD') || ' ' || outtime as a4,"
           + " '1' as a5,"
           + " (select activityname from lwactivity where activityid = b.activityid) as a6,"
           + " makedate as a7,"
           + " maketime as a8,"
           + " (case when activityid='0000001110' then decode(missionprop11,'1','�ϱ��б�','4','�����¼�','����Լ�˱�') end ) a9"           
           + " from lbmission b"
           + " where 1 = 1 "
           + " and missionid = (select missionid"
           + " from lbmission"
           + " where missionprop1 = '"+ContNo+"'"
           + " and rownum = 1)"
           + " and activityid in ('0000001270', '0000001104', '0000001101')"
           + " and outdate is not null)"
           + " order by a7, a8";           
      */
      //tongmeng 2009-06-03 modify
      //�޸��߼�
      var sqlid917155346="DSHomeContSql917155346";
var mySql917155346=new SqlClass();
mySql917155346.setResourceName("uw.RecordQuerySql");//ָ��ʹ�õ�properties�ļ���
mySql917155346.setSqlId(sqlid917155346);//ָ��ʹ�õ�Sql��id
mySql917155346.addSubPara(ContNo);//ָ������Ĳ���
mySql917155346.addSubPara(ContNo);//ָ������Ĳ���
mySql917155346.addSubPara(ContNo);//ָ������Ĳ���
mySql917155346.addSubPara(ContNo);//ָ������Ĳ���
var aSQL=mySql917155346.getString();
      
      
//      var aSQL = " select a1, a2, a3, a4, a5, a6, a9 "
//  	           + " from (select a.activityid x,'"+ContNo+"' as a1, "
//               + " nvl((select usercode from lduser where usercode = defaultoperator), '') as a2,"
//               + " nvl(trim(to_char(indate, 'YYYY-MM-DD') || ' ' || intime),to_char(makedate, 'YYYY-MM-DD') || ' ' || maketime) as a3, "
//               + " to_char(outdate, 'YYYY-MM-DD') || ' ' || outtime as a4, "
//               + " '1' as a5, "
//               + " (case when activityid='0000001100'then '�˹��˱�'||a.missionprop18||'״̬' else (select activityname "
//               + " from lwactivity where activityid = a.activityid) end ) as a6, "
//               + " makedate as a7, maketime as a8, "
//               + " (case when activityid = '0000001110' then decode(missionprop11,'1','�ϱ��б�','4','�����¼�','����Լ�˱�') "
//               + " end) a9,a.serialno a10 "
//               + " from lbmission a "
//               + " where 1 = 1 "
//               + " and missionid = (select missionid "
//               + " from lbmission where missionprop1 = '"+ContNo+"' "
//               + " and rownum = 1) "
//               + " and activityid not in ('0000001270', '0000001104', '0000001101') "
//               + " and activityid<>'0000001105' "
//               + " and (outdate is not null or (outdate is null and defaultoperator is not null) or (missionprop18='4')) "
//               + " union "
//               + " select b.activityid x, '"+ContNo+"' as a1, '' as a2, "
//               + " nvl(trim(to_char(indate, 'YYYY-MM-DD') || ' ' || intime),to_char(makedate, 'YYYY-MM-DD') || ' ' || maketime) as a3, "
//               + " to_char(outdate, 'YYYY-MM-DD') || ' ' || outtime as a4, "
//               + " '1' as a5, "
//               + " (case when activityid='0000001100'then '�˹��˱�'||b.missionprop18||'״̬' else (select activityname "
//               + " from lwactivity where activityid = b.activityid) end )  as a6, "
//               + " makedate as a7, maketime as a8, "
//               + " (case when activityid = '0000001110' then decode(missionprop11, "
//               + " '1','�ϱ��б�','4','�����¼�','����Լ�˱�') "
//               + " end) a9,b.serialno a10 "
//               + " from lbmission b "
//               + " where 1 = 1 "
//               + " and missionid = (select missionid "
//               + " from lbmission where missionprop1 = '"+ContNo+"' and rownum = 1) "
//               + " and activityid in ('0000001270', '0000001104', '0000001101') "
//               + " and activityid<>'0000001105' "
//               + " and outdate is not null) "
//               + " order by a3,a10 ";     
   turnPage.queryModal(aSQL, RecordGrid);

}

function queryReason()
{
	  var sqlid917155728="DSHomeContSql917155728";
var mySql917155728=new SqlClass();
mySql917155728.setResourceName("uw.RecordQuerySql");//ָ��ʹ�õ�properties�ļ���
mySql917155728.setSqlId(sqlid917155728);//ָ��ʹ�õ�Sql��id
mySql917155728.addSubPara(ContNo);//ָ������Ĳ���
var aSQL=mySql917155728.getString();

//	  var aSQL = " select 1 from lwmission w where w.activityid='0000001100' and w.missionprop18 ='6' and w.MissionProp2='"+ContNo+"' ";	
	  var arrResult = easyExecSql(aSQL);
	  if(!arrResult)
	  {
	     alert("û�к˱��ȴ���Ϣ��");
	     return false;
	  }
	  window.open("./WaitReasonMain.jsp?ContNo="+ContNo+"&MissionID=''&SubMissionID=''&Type=2","window3","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
	  
}

//function queryPersonInfo(){
//  var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
//  var arrResult = easyExecSql(aSQL);
//  document.all('CustomerNo').value = arrResult[0][0];
//  document.all('CustomerName').value = arrResult[0][1];
//}

//function getContDetail(){
//	//alert();
//	
//}



//function contInfoClick(){
//  
//  var tSel = ContGrid.getSelNo();
//  var tProposalContNo = ContGrid.getRowColData(tSel - 1,1);        //��ͬͶ������
//	
//  var aSQL="select impartver,impartcode,impartcontent,ImpartParamModle from lccustomerimpart where customerno='"+customerNo+"' and proposalcontno='"+tProposalContNo+"'";	
// 
//  turnPage.queryModal(aSQL, ImpartGrid);
//	
//}


