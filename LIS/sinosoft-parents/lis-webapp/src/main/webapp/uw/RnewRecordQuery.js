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
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�

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
      //�޸��߼�
//      var aSQL = " select a1, a7,a2, a6,a3, a4 from (select a.activityid x, "
//				+" '"+ContNo+"' as a1, "
//				+" (select missionprop5 from lbmission where missionid=a.missionid and activityid = '0000007001' "
//               +"  union select missionprop5 from lwmission where  missionid=a.missionid and activityid = '0000007001') a7, "
//				+" (case when (select usercode from lduser "
//				+"	where  usercode = a.createoperator) is not null then (select usercode from lduser "
//				+"	where  usercode = a.createoperator) else  '' end) as a2, "
//               +" (select activityname from lwactivity where activityid = a.activityid) a6,   "
//			   +" (case when concat(concat(trim(to_char(indate, 'YYYY-MM-DD') , ' ') , intime)) is not null then  concat(concat(trim(to_char(indate, 'YYYY-MM-DD') , ' ') , intime)) else concat(concat(to_char(makedate, 'YYYY-MM-DD') , ' ') , maketime) end) as a3, "
//				+" concat(concat(to_char(outdate, 'YYYY-MM-DD') ,' ' ), outtime) as a4,a.missionid as a5,a.serialno a10 "
//			   +"  from lbmission a where missionid in (select missionid from lbmission "
//				+" where missionprop2 = '"+ContNo+"' and activityid='0000007001' union  "
//                +"  select missionid from lwmission where missionprop2 = '"+ContNo+"' and activityid='0000007001') "
//                +"  and a.activityid<>'0000007002') order by a5,a3, a10 ";     
      sql_id = "RnewRecordQuerySql0";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewRecordQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(ContNo);//ָ������Ĳ���
		my_sql.addSubPara(ContNo);//ָ������Ĳ���
		my_sql.addSubPara(ContNo);//ָ������Ĳ���
		str_sql = my_sql.getString();
   turnPage.queryModal(str_sql, RecordGrid);

}

function queryReason()
{
//	  var aSQL = " select 1 from lwmission w where w.activityid='0000001100' and w.missionprop18 ='6' and w.MissionProp2='"+ContNo+"' ";	
//	  var aSQL = " select 1 from lwmission w where w.activityid in (select activityid from lwactivity  where functionid ='10010028') and exists (select 1 from lccuwmaster where contno= w.missionprop1 and uwstate ='6') and w.MissionProp2='"+ContNo+"' ";	
	  sql_id = "RnewRecordQuerySql1";
		my_sql = new SqlClass();
		my_sql.setResourceName("uw.RnewRecordQuerySql"); //ָ��ʹ�õ�properties�ļ���
		my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
		my_sql.addSubPara(ContNo);//ָ������Ĳ���
		str_sql = my_sql.getString();
	var arrResult = easyExecSql(str_sql);
	  if(!arrResult)
	  {
	     alert("û�к˱��ȴ���Ϣ��");
	     return false;
	  }
	  window.open("./WaitReasonMain.jsp?ContNo="+ContNo+"&MissionID=''&SubMissionID=''&Type=2","window3");
	  
}

//function queryPersonInfo(){
//  var aSQL = "select a.customerno,a.name from ldperson a where a.customerno='"+customerNo+"'";	
//  var arrResult = easyExecSql(aSQL);
//  fm.all('CustomerNo').value = arrResult[0][0];
//  fm.all('CustomerName').value = arrResult[0][1];
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


