/**************************************************************************
 * ��������: VerifyWorkFlow.js
 * ������: ͨ��У�麯�����ڽ��й����Բ���ǰУ�鹤�����ڵ��Ƿ��ڣ���ֹ����ͬʱ��һ������������������������
 *           
 * ������  : 
 * �����������: 
**************************************************************************/

function verifyWorkFlow(Missionid,Submissionid,Activityid)
{

	var arrResult;
	var chkSQL = "";//"select * from lwmission where missionid='"+Missionid+"' and submissionid='"+Submissionid+"' and activityid='"+Activityid+"'";
	
	var sqlid="VerifyWorkFlow";
	var mySql=new SqlClass();
	mySql.setResourceName("common.CommonInputSql"); //ָ��ʹ�õ�properties�ļ���
    mySql.setSqlId(sqlid);//ָ��ʹ�õ�Sql��id
    mySql.addSubPara(Missionid);//ָ������Ĳ���
    mySql.addSubPara(Submissionid);
	mySql.addSubPara(Activityid);
	chkSQL=mySql.getString();
	
	arrResult = easyExecSql(chkSQL);
	if(arrResult==null||arrResult=="")
	{
		alert("�ô������Ѿ���������������иò���");
		return false;
	}
	return true;

}
