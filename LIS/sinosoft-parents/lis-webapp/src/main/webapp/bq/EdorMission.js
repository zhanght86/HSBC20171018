//***********************************************
//�������ƣ�EdorMission.js
//�����ܣ���ȫ�����켣��ѯ
//�������ڣ�2005-11-24 19:10:36
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//���¼�¼��  ������    ��������     ����ԭ��/����
//***********************************************

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�


//ȫ�ֱ���
var showInfo;
var turnPage = new turnPageClass();


/*********************************************************************
 *  ����
 *  ����: ҳ�����ʾ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function returnParent()
{
    top.close();
    top.opener.focus();
}


/*********************************************************************
 *  ��ѯ��ȫ�������ڵ�켣 
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryEdorMission() 
{

//	 var tSql = "select distinct a.missionprop1 from lwmission a, lpedorapp b where a.missionprop1 = b.edoracceptno and b.othernotype in ('1','3') and a.missionid = '"+MissionID+"'"
// 						+ " union "
// 						+ " select distinct a.missionprop1 from lbmission a, lpedorapp b where a.missionprop1 = b.edoracceptno and b.othernotype in ('1','3') and a.missionid = '"+MissionID+"'";
	
	var sqlid824101829="DSHomeContSql824101829";
	var mySql824101829=new SqlClass();
		mySql824101829.setResourceName("bq.EdorMissionInputSql");//ָ��ʹ�õ�properties�ļ���
		mySql824101829.setSqlId(sqlid824101829);//ָ��ʹ�õ�Sql��id
		mySql824101829.addSubPara(MissionID);//ָ������Ĳ���
		mySql824101829.addSubPara(MissionID);//ָ������Ĳ���
	var tSql=mySql824101829.getString();
	 
	var arr = easyExecSql(tSql);
	
	var strSql = " ";
	if (arr)
	{
    	//���ձ�ȫ
		var tEdorAcceptNo = arr[0][0];  
//    	tNoticePart =	" union "
//    							+ " select '�˹��˱����������', a.operator, to_char(a.makedate,'yyyy-mm-dd') || ' ' || a.maketime mt, to_char(a.makedate,'yyyy-mm-dd') || ' ' || a.maketime it,decode(a.state, '1', to_char(a.modifydate,'yyyy-mm-dd') || ' ' || a.modifytime, '') ot,'',a.makedate,'' "
//  								+ " from LPIssuePol a, lpedoritem b where a.edorno = b.edorno and a.edortype = b.edortype and b.EdorAcceptNo = '"+tEdorAcceptNo+"' "
//   								+ " union "
//   								+ " select '��ȫ���������', a.StandbyFlag2, to_char(a.makedate,'yyyy-mm-dd') || ' ' || a.maketime mt, to_char(a.makedate,'yyyy-mm-dd')|| ' ' || a.maketime it,decode(a.StateFlag, 'R', a.StandbyFlag7 || ' ' || a.donetime, '') ot,'',a.makedate,'' "
//     							+ " from LOPRTManager a where a.code = 'BQ38' and a.standbyflag1 = '"+tEdorAcceptNo+"' "
//    							+ " union "
//      					  + " select decode(a.Code,'BQ33','��ȫ�ܾ�֪ͨ��','BQ37','��ȫ���֪ͨ��',''), a.StandbyFlag3,to_char(a.makedate,'yyyy-mm-dd')|| ' ' || a.maketime mt, to_char(a.makedate,'yyyy-mm-dd')|| ' ' || a.maketime it,decode(a.StateFlag, '0', '', to_char(a.donedate,'yyyy-mm-dd') || ' ' || a.donetime) ot,'',a.makedate,'' "
//    							+ " from LOPRTManager a where a.code in ('BQ33','BQ37') and a.standbyflag1 = '"+tEdorAcceptNo+"'"
		var mySql001=new SqlClass();
			mySql001.setResourceName("bq.EdorMissionInputSql");//ָ��ʹ�õ�properties�ļ���
			mySql001.setSqlId("DSHomeContSql001");//ָ��ʹ�õ�Sql��id
			mySql001.addSubPara(MissionID);//ָ������Ĳ���
			mySql001.addSubPara(MissionID);//ָ������Ĳ���			
			mySql001.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
			mySql001.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
			mySql001.addSubPara(tEdorAcceptNo);//ָ������Ĳ���
		strSql=mySql001.getString() ;
	}
	else
	{

		var sqlid824102620="DSHomeContSql824102620";
		var mySql824102620=new SqlClass();
			mySql824102620.setResourceName("bq.EdorMissionInputSql");//ָ��ʹ�õ�properties�ļ���
			mySql824102620.setSqlId(sqlid824102620);//ָ��ʹ�õ�Sql��id
			mySql824102620.addSubPara(MissionID);//ָ������Ĳ���
			mySql824102620.addSubPara(MissionID);//ָ������Ĳ���
			
		strSql=mySql824102620.getString();   	
	
	}
//   var strSql = " select (select codename from ldcode where code = activityid and codetype = 'bqactivityname'), "
//   			  + "  nvl(defaultoperator,(case when exists(select 'X' from ldcode where codetype = 'bqactivityname' and othersign = 'manuuw' and code = activityid) then '' else '' end ))," 			  
//   			  + " substr(MakeDate,0,10)||' '||MakeTime mt,"                                     
//   			  + " substr(InDate,0,10)||' '||InTime it,"                                         
//   			  + " nvl2(OutDate,substr(OutDate, 0, 10) || ' ' || OutTime,'') ot,"                
//   			  + " activityid,makedate,maketime "                                              
//          + " from lwmission where 1=1  " //and (defaultoperator is not null or outdate is not null)
//          + "  and activitystatus <> '4' "                                               
//          + "  and exists(select 'X' from ldcode where codetype = 'bqactivityname' and code = activityid) "
//          + "  and not exists (select 'X' from ldcode where codetype = 'bqactivityname' and othersign = 'manuuw' and code = activityid) "
//          + "  and missionid = '" + MissionID + "' "                                        
//          + "union "                                              
//          + " select (select codename from ldcode where code = activityid and codetype = 'bqactivityname'), " 
//          + "  nvl(defaultoperator,(case when exists(select 'X' from ldcode where codetype = 'bqactivityname' and othersign = 'manuuw' and code = activityid) then '' else '' end )),"
//   			  + " substr(MakeDate,0,10)||' '||MakeTime mt,"                                     
//   			  + " substr(InDate,0,10)||' '||InTime it,"                                         
//   			  + " nvl2(OutDate,substr(OutDate, 0, 10) || ' ' || OutTime,'') ot," //Null�ַ�����Oracel ��������
//   			  + " activityid,makedate,maketime"                                              
//          + " from lbmission where 1=1 " //and (defaultoperator is not null or outdate is not null) 
//          + "  and activitystatus <> '4' "                                               
//          + "  and exists(select 'X' from ldcode where codetype = 'bqactivityname' and code = activityid) "
//          + "  and missionid = '" + MissionID + "'"                                         
//          + tNoticePart                                              
//          //+ " order by makedate,maketime, activityid";                                    
//          // modify by jiaqiangli 2009-04-02                                              
//          + " order by mt,it,ot nulls last ";      
    var crr = easyExecSql(strSql);
    if (!crr)
    {
        //divEdorMainInfo.style.display='none';
        alert("��ȫ������û��������");
        divPolInfo.style.display = 'none';
        return;
    }
    else
    {
        initEdorMissionGrid();
        displayMultiline(crr, EdorMissionGrid);
        //turnPage.queryModal(strSql, EdorMissionGrid);
    }   
}
