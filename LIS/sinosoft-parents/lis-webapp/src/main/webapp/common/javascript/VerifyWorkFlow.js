/**************************************************************************
 * 程序名称: VerifyWorkFlow.js
 * 程序功能: 通用校验函数，在进行功能性操作前校验工作流节点是否还在，防止出现同时对一条任务操作所可能引起的问题
 *           
 * 创建人  : 
 * 最近更新日期: 
**************************************************************************/

function verifyWorkFlow(Missionid,Submissionid,Activityid)
{

	var arrResult;
	var chkSQL = "";//"select * from lwmission where missionid='"+Missionid+"' and submissionid='"+Submissionid+"' and activityid='"+Activityid+"'";
	
	var sqlid="VerifyWorkFlow";
	var mySql=new SqlClass();
	mySql.setResourceName("common.CommonInputSql"); //指定使用的properties文件名
    mySql.setSqlId(sqlid);//指定使用的Sql的id
    mySql.addSubPara(Missionid);//指定传入的参数
    mySql.addSubPara(Submissionid);
	mySql.addSubPara(Activityid);
	chkSQL=mySql.getString();
	
	arrResult = easyExecSql(chkSQL);
	if(arrResult==null||arrResult=="")
	{
		alert("该次任务已经结束，不允许进行该操作");
		return false;
	}
	return true;

}
