//程序名称：SendOutListQuery.js
//程序功能：查询保单回执清单和打印
//创建日期：2005-7-7
//创建人  ：weikai
//更新记录：  更新人    更新日期     更新原因/内容

var turnPage = new turnPageClass();
var mySql = new SqlClass();

function easyQuery()
{
    var Mcom=document.all("ManageCom").value;
    if(Mcom == "")
    {
        alert ("请选择管理机构");
        return;
    }
    var tsigndate =fm.signdate.value;
    var tsigndateend =fm.signdateend.value;
    if(tsigndateend==null||tsigndateend==""||tsigndate==null||tsigndate==""){
      alert("请录入保单签发的起止时间！");
      return false;
    }
    

    
//    var strsql=" select a.contno,a.managecom,a.agentcom,a.agentcode,(select c.name from laagent c where a.agentcode=c.agentcode),case("+cleanSql+",'1','非清洁件','清洁件'),a.firsttrialdate ,(select max(makedate) from es_doc_main where doccode = a.prtno and subtype = 'UA001')   "
//               +" '问题件回复时间',(select min(enteraccdate) from ljtempfee where ohterno = a.contno ),a.signdate from lccont a "
//               +" where  a.grpcontno like '%%0000000000' and a.appflag='1'  "
//               //排除 折式保单 hanbin - 2010-05-04
//               + " and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
//             //+getWherePart('a.ManageCom','ManageCom','like','0')
//               +getWherePart('a.SaleChnl','SaleChnl','=','0')
//               +getWherePart('a.agentcom','AgentCom','=','0')
//               +getWherePart('a.agentcode','AgentCode','=','0')
//               +getWherePart('a.signdate','signdateend','<=','0')
//               +getWherePart('a.signdate','signdate','>=','0')+" and a.ManageCom like '"+document.all("ManageCom").value+"%25' and a.ManageCom like '"+comCode+"%%' and exists (select 1 from lzsyscertify b where( "
//               +"  b.takebackmakedate>'"+document.all("takebackdate").value+"'  or ("
//               +" b.takebackmakedate='"+document.all("takebackdate").value+"' and "
//               +"  b.takebackmaketime is not null and b.takebackmaketime>'"+document.all("takebacktime").value+"'"
//               +") or ( b.takebackmakedate='"+document.all("takebackdate").value+"' and b.takebackmaketime is null ))and b.certifycode ='9995' and a.contno=b.certifyno"
//               
//               +" ) "
//               +" union "
//               +" select a.contno,(select c.name from laagent c where a.agentcode=c.agentcode),a.agentcode i,a.signdate j,a.managecom h,a.agentcom,a.appntname   "
//               +" from lccont a "
//               +" where  a.appflag='1' and a.conttype ='1'  and a.getpoldate is null "
//               //排除 折式保单 hanbin - 2010-05-04
//               + " and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
//             //+getWherePart('a.ManageCom','ManageCom','like','0')
//               +getWherePart('a.SaleChnl','SaleChnl','=','0')
//               +getWherePart('a.agentcode','AgentCode','=','0')
//               +getWherePart('a.agentcom','AgentCom','=','0')
//               +getWherePart('a.signdate','signdate','>=','0')
//               +getWherePart('a.signdate','signdateend','<=','0')+" and a.ManageCom like '"+document.all("ManageCom").value+"%25' and a.ManageCom like '"+comCode+"%%'"
//               +" order by j,h, i"
//               ;
//        //alert(Dateb+Datee);
    
    //保单回执清单和报表分离 hanbin - 2010-05-06
   /* var cleanSql = " select 1 from lcissuepol where backobjtype = '2' and  needprint = 'Y' and contno = a.contno union  select 1 from lcpenotice where contno = a.contno union select 1 from LCRReport where contno = a.contno union select 1 from lccspec where contno = a.contno union select 1  from lcprem where payplancode like '000000%' and contno = a.contno union select 1 from lbmission where missionprop1 = a.prtno and activityid = '0000001100' and missionprop18 = '6' ";
    var replySql = " nullif(greatest(nvl((select max(lpt.replydate)  from lcrreport lpt Where lpt.contno = a.contno), '2000-01-01'), nvl((Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid = '0000001111'), '2000-01-01'), nvl((Select max(makedate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid = '0000001100'and missionprop18 = '6'), '2000-01-01'),nvl((Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid = '0000001112'), '2000-01-01')),date'2000-01-01') " ;
    var strsql= " select a.contno,a.managecom,a.agentcom,a.agentcode,(select c.name from laagent c where a.agentcode=c.agentcode),a.appntname,(case ("+cleanSql+") when  1  then '非清洁件' else '清洁件' end),a.firsttrialdate ,(select max(makedate) from es_doc_main where doccode = a.prtno and subtype = 'UA001'), "
    		+" "+replySql+",(select min(enteraccdate) from ljtempfee where otherno = a.contno ),a.signdate from lccont a "
		    +" where  a.appflag='1' and a.conttype ='1'  and a.getpoldate is null "
		    //排除 折式保单 hanbin - 2010-05-04
		    + " and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
		  //排除 邮保通  hanbin 2010-06-07
		    + " and a.selltype != '08' "
		  //+getWherePart('a.ManageCom','ManageCom','like','0')
		    +getWherePart('a.SaleChnl','SaleChnl','=','0')
		    +getWherePart('a.agentcode','AgentCode','=','0')
		    +getWherePart('a.agentcom','AgentCom','=','0')
		    +getWherePart('a.signdate','signdate','>=','0')
		    +getWherePart('a.signdate','signdateend','<=','0')+" and a.ManageCom like '"+document.all("ManageCom").value+"%25' and a.ManageCom like '"+comCode+"%%'";
	*/	    
		    mySql = new SqlClass();
			mySql.setResourceName("certify.SendOutListQuerySql");
			mySql.setSqlId("SendOutListQuerySql1");
			mySql.addSubPara(fm.SaleChnl.value ); 
			mySql.addSubPara(fm.AgentCode.value ); 
			mySql.addSubPara(fm.AgentCom.value ); 
			mySql.addSubPara(fm.signdate.value ); 
			mySql.addSubPara(fm.signdateend.value ); 
			mySql.addSubPara(document.all("ManageCom").value ); 
			mySql.addSubPara(comCode); 
			  
    		if(fm.CleanPolFlag.value != "" && fm.CleanPolFlag.value == "0")
    		{//非清洁件
    			//strsql = strsql + " and (exists ("+cleanSql+"))  ";
    			mySql = new SqlClass();
				mySql.setResourceName("certify.SendOutListQuerySql");
				mySql.setSqlId("SendOutListQuerySql2");
				mySql.addSubPara(fm.SaleChnl.value ); 
				mySql.addSubPara(fm.AgentCode.value ); 
				mySql.addSubPara(fm.AgentCom.value ); 
				mySql.addSubPara(fm.signdate.value ); 
				mySql.addSubPara(fm.signdateend.value ); 
				mySql.addSubPara(document.all("ManageCom").value ); 
				mySql.addSubPara(comCode); 

    			if(fm.StatType.value != null && fm.StatType.value == "1")
    			{//超期未核销件
    				//strsql = strsql + " and to_date(substr(sysdate,0,10),'yyyy-mm-dd') -  "+replySql+" >= 19  order by a.firsttrialdate" ;
    				mySql = new SqlClass();
					mySql.setResourceName("certify.SendOutListQuerySql");
					mySql.setSqlId("SendOutListQuerySql3");
					mySql.addSubPara(fm.SaleChnl.value ); 
					mySql.addSubPara(fm.AgentCode.value ); 
					mySql.addSubPara(fm.AgentCom.value ); 
					mySql.addSubPara(fm.signdate.value ); 
					mySql.addSubPara(fm.signdateend.value ); 
					mySql.addSubPara(document.all("ManageCom").value ); 
					mySql.addSubPara(comCode); 
    			}else if(fm.StatType.value != null && fm.StatType.value == "0")
    			{//预警件
    				//strsql = strsql + " and to_date(substr(sysdate,0,10),'yyyy-mm-dd') -  "+replySql+" >= 15   and to_date(substr(sysdate,0,10),'yyyy-mm-dd') -  "+replySql+" < 19 order by a.firsttrialdate" ;
    				mySql = new SqlClass();
					mySql.setResourceName("certify.SendOutListQuerySql");
					mySql.setSqlId("SendOutListQuerySql4");
					mySql.addSubPara(fm.SaleChnl.value ); 
					mySql.addSubPara(fm.AgentCode.value ); 
					mySql.addSubPara(fm.AgentCom.value ); 
					mySql.addSubPara(fm.signdate.value ); 
					mySql.addSubPara(fm.signdateend.value ); 
					mySql.addSubPara(document.all("ManageCom").value ); 
					mySql.addSubPara(comCode); 
    			}else{
    			//	strsql = strsql + " order by a.firsttrialdate ";
    				mySql = new SqlClass();
					mySql.setResourceName("certify.SendOutListQuerySql");
					mySql.setSqlId("SendOutListQuerySql5");
					mySql.addSubPara(fm.SaleChnl.value ); 
					mySql.addSubPara(fm.AgentCode.value ); 
					mySql.addSubPara(fm.AgentCom.value ); 
					mySql.addSubPara(fm.signdate.value ); 
					mySql.addSubPara(fm.signdateend.value ); 
					mySql.addSubPara(document.all("ManageCom").value ); 
					mySql.addSubPara(comCode); 
    			}
    			
    		}else if(fm.CleanPolFlag.value != "" && fm.CleanPolFlag.value == "1")
    		{//清洁件
    			//strsql = strsql + " and (not exists ("+cleanSql+"))  ";
    			mySql = new SqlClass();
				mySql.setResourceName("certify.SendOutListQuerySql");
				mySql.setSqlId("SendOutListQuerySql6");
				mySql.addSubPara(fm.SaleChnl.value ); 
				mySql.addSubPara(fm.AgentCode.value ); 
				mySql.addSubPara(fm.AgentCom.value ); 
				mySql.addSubPara(fm.signdate.value ); 
				mySql.addSubPara(fm.signdateend.value ); 
				mySql.addSubPara(document.all("ManageCom").value ); 
				mySql.addSubPara(comCode); 
				
    			if(fm.StatType.value != null && fm.StatType.value == "1")
    			{//超期未核销件
    				//strsql = strsql + " and to_date(substr(sysdate,0,10),'yyyy-mm-dd') - a.firsttrialdate >= 19   order  by a.firsttrialdate";
    				mySql = new SqlClass();
				mySql.setResourceName("certify.SendOutListQuerySql");
				mySql.setSqlId("SendOutListQuerySql7");
				mySql.addSubPara(fm.SaleChnl.value ); 
				mySql.addSubPara(fm.AgentCode.value ); 
				mySql.addSubPara(fm.AgentCom.value ); 
				mySql.addSubPara(fm.signdate.value ); 
				mySql.addSubPara(fm.signdateend.value ); 
				mySql.addSubPara(document.all("ManageCom").value ); 
				mySql.addSubPara(comCode); 
    			}else if(fm.StatType.value != null && fm.StatType.value == "0")
    			{//预警件
    			//	strsql = strsql + " and to_date(substr(sysdate,0,10),'yyyy-mm-dd') -  a.firsttrialdate  >= 15  and  to_date(substr(sysdate,0,10),'yyyy-mm-dd') -  a.firsttrialdate  < 19 order by a.firsttrialdate" ;
    				mySql = new SqlClass();
				mySql.setResourceName("certify.SendOutListQuerySql");
				mySql.setSqlId("SendOutListQuerySql8");
				mySql.addSubPara(fm.SaleChnl.value ); 
				mySql.addSubPara(fm.AgentCode.value ); 
				mySql.addSubPara(fm.AgentCom.value ); 
				mySql.addSubPara(fm.signdate.value ); 
				mySql.addSubPara(fm.signdateend.value ); 
				mySql.addSubPara(document.all("ManageCom").value ); 
				mySql.addSubPara(comCode); 
    			}else{
    			//	strsql = strsql + " order by a.firsttrialdate ";
    				mySql = new SqlClass();
				mySql.setResourceName("certify.SendOutListQuerySql");
				mySql.setSqlId("SendOutListQuerySql9");
				mySql.addSubPara(fm.SaleChnl.value ); 
				mySql.addSubPara(fm.AgentCode.value ); 
				mySql.addSubPara(fm.AgentCom.value ); 
				mySql.addSubPara(fm.signdate.value ); 
				mySql.addSubPara(fm.signdateend.value ); 
				mySql.addSubPara(document.all("ManageCom").value ); 
				mySql.addSubPara(comCode); 
    			}
    		}else
    		{
    			if(fm.StatType.value != null && fm.StatType.value == "1")
    			{
    			//	strsql = strsql + " and ((1=(case ("+cleanSql+") when  1  then 1 else 0 end) and to_date(substr(sysdate,0,10),'yyyy-mm-dd') -  "+replySql+" >= 19 ) or (0 = (case ("+cleanSql+") when  1  then 1 else 0 end) and  to_date(substr(sysdate, 0, 10), 'yyyy-mm-dd') - a.firsttrialdate >= 19)) order by a.firsttrialdate ";
    				mySql = new SqlClass();
				mySql.setResourceName("certify.SendOutListQuerySql");
				mySql.setSqlId("SendOutListQuerySql10");
				mySql.addSubPara(fm.SaleChnl.value ); 
				mySql.addSubPara(fm.AgentCode.value ); 
				mySql.addSubPara(fm.AgentCom.value ); 
				mySql.addSubPara(fm.signdate.value ); 
				mySql.addSubPara(fm.signdateend.value ); 
				mySql.addSubPara(document.all("ManageCom").value ); 
				mySql.addSubPara(comCode); 
    			}else if(fm.StatType.value != null && fm.StatType.value == "0")
    			{
    			//	strsql = strsql + " and ((1=(case ("+cleanSql+") when  1  then 1 else 0 end) and to_date(substr(sysdate,0,10),'yyyy-mm-dd') -  "+replySql+" >= 15   and to_date(substr(sysdate,0,10),'yyyy-mm-dd') -  "+replySql+" < 19  ) or (0 = (case ("+cleanSql+") when  1  then 1 else 0 end) and  to_date(substr(sysdate,0,10),'yyyy-mm-dd') -  a.firsttrialdate  >= 15  and  to_date(substr(sysdate,0,10),'yyyy-mm-dd') -  a.firsttrialdate  < 19 )) order by a.firsttrialdate ";
    				mySql = new SqlClass();
				mySql.setResourceName("certify.SendOutListQuerySql");
				mySql.setSqlId("SendOutListQuerySql11");
				mySql.addSubPara(fm.SaleChnl.value ); 
				mySql.addSubPara(fm.AgentCode.value ); 
				mySql.addSubPara(fm.AgentCom.value ); 
				mySql.addSubPara(fm.signdate.value ); 
				mySql.addSubPara(fm.signdateend.value ); 
				mySql.addSubPara(document.all("ManageCom").value ); 
				mySql.addSubPara(comCode); 
    			}else
    			{
    				//strsql = strsql   +" order by a.firsttrialdate " ;
    				mySql = new SqlClass();
				mySql.setResourceName("certify.SendOutListQuerySql");
				mySql.setSqlId("SendOutListQuerySql12");
				mySql.addSubPara(fm.SaleChnl.value ); 
				mySql.addSubPara(fm.AgentCode.value ); 
				mySql.addSubPara(fm.AgentCom.value ); 
				mySql.addSubPara(fm.signdate.value ); 
				mySql.addSubPara(fm.signdateend.value ); 
				mySql.addSubPara(document.all("ManageCom").value ); 
				mySql.addSubPara(comCode); 
    			}
    			
    			
    			
    		}
	 
  //     alert( strsql)
   turnPage.queryModal(mySql.getString(), CodeGrid);  
   

}


function easyPrint()
{
	easyQueryPrint(2,'CodeGrid','turnPage');
}