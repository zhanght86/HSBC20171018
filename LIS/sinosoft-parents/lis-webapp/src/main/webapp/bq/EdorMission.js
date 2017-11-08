//***********************************************
//程序名称：EdorMission.js
//程序功能：保全操作轨迹查询
//创建日期：2005-11-24 19:10:36
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
//更新记录：  更新人    更新日期     更新原因/内容
//***********************************************

//               该文件中包含客户端需要处理的函数和事件


//全局变量
var showInfo;
var turnPage = new turnPageClass();


/*********************************************************************
 *  返回
 *  描述: 页面层显示控制
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function returnParent()
{
    top.close();
    top.opener.focus();
}


/*********************************************************************
 *  查询保全工作流节点轨迹 
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function queryEdorMission() 
{

//	 var tSql = "select distinct a.missionprop1 from lwmission a, lpedorapp b where a.missionprop1 = b.edoracceptno and b.othernotype in ('1','3') and a.missionid = '"+MissionID+"'"
// 						+ " union "
// 						+ " select distinct a.missionprop1 from lbmission a, lpedorapp b where a.missionprop1 = b.edoracceptno and b.othernotype in ('1','3') and a.missionid = '"+MissionID+"'";
	
	var sqlid824101829="DSHomeContSql824101829";
	var mySql824101829=new SqlClass();
		mySql824101829.setResourceName("bq.EdorMissionInputSql");//指定使用的properties文件名
		mySql824101829.setSqlId(sqlid824101829);//指定使用的Sql的id
		mySql824101829.addSubPara(MissionID);//指定传入的参数
		mySql824101829.addSubPara(MissionID);//指定传入的参数
	var tSql=mySql824101829.getString();
	 
	var arr = easyExecSql(tSql);
	
	var strSql = " ";
	if (arr)
	{
    	//个险保全
		var tEdorAcceptNo = arr[0][0];  
//    	tNoticePart =	" union "
//    							+ " select '人工核保机构问题件', a.operator, to_char(a.makedate,'yyyy-mm-dd') || ' ' || a.maketime mt, to_char(a.makedate,'yyyy-mm-dd') || ' ' || a.maketime it,decode(a.state, '1', to_char(a.modifydate,'yyyy-mm-dd') || ' ' || a.modifytime, '') ot,'',a.makedate,'' "
//  								+ " from LPIssuePol a, lpedoritem b where a.edorno = b.edorno and a.edortype = b.edortype and b.EdorAcceptNo = '"+tEdorAcceptNo+"' "
//   								+ " union "
//   								+ " select '保全审批问题件', a.StandbyFlag2, to_char(a.makedate,'yyyy-mm-dd') || ' ' || a.maketime mt, to_char(a.makedate,'yyyy-mm-dd')|| ' ' || a.maketime it,decode(a.StateFlag, 'R', a.StandbyFlag7 || ' ' || a.donetime, '') ot,'',a.makedate,'' "
//     							+ " from LOPRTManager a where a.code = 'BQ38' and a.standbyflag1 = '"+tEdorAcceptNo+"' "
//    							+ " union "
//      					  + " select decode(a.Code,'BQ33','保全拒绝通知书','BQ37','保全审核通知书',''), a.StandbyFlag3,to_char(a.makedate,'yyyy-mm-dd')|| ' ' || a.maketime mt, to_char(a.makedate,'yyyy-mm-dd')|| ' ' || a.maketime it,decode(a.StateFlag, '0', '', to_char(a.donedate,'yyyy-mm-dd') || ' ' || a.donetime) ot,'',a.makedate,'' "
//    							+ " from LOPRTManager a where a.code in ('BQ33','BQ37') and a.standbyflag1 = '"+tEdorAcceptNo+"'"
		var mySql001=new SqlClass();
			mySql001.setResourceName("bq.EdorMissionInputSql");//指定使用的properties文件名
			mySql001.setSqlId("DSHomeContSql001");//指定使用的Sql的id
			mySql001.addSubPara(MissionID);//指定传入的参数
			mySql001.addSubPara(MissionID);//指定传入的参数			
			mySql001.addSubPara(tEdorAcceptNo);//指定传入的参数
			mySql001.addSubPara(tEdorAcceptNo);//指定传入的参数
			mySql001.addSubPara(tEdorAcceptNo);//指定传入的参数
		strSql=mySql001.getString() ;
	}
	else
	{

		var sqlid824102620="DSHomeContSql824102620";
		var mySql824102620=new SqlClass();
			mySql824102620.setResourceName("bq.EdorMissionInputSql");//指定使用的properties文件名
			mySql824102620.setSqlId(sqlid824102620);//指定使用的Sql的id
			mySql824102620.addSubPara(MissionID);//指定传入的参数
			mySql824102620.addSubPara(MissionID);//指定传入的参数
			
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
//   			  + " nvl2(OutDate,substr(OutDate, 0, 10) || ' ' || OutTime,'') ot," //Null字符串在Oracel 中是最大的
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
        alert("保全申请下没有批单！");
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
