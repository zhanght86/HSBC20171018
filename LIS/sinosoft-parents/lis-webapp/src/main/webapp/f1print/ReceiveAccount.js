	//程序名称：ReceiveAccount.js
	//程序功能： 统计管理—承保统计—保费3日到账率
	//创建日期：2010-05-26
	//创建人  ：hanbin
	//更新记录：  更新人    更新日期     更新原因/

var turnPage = new turnPageClass();


function easyQuery()
{
    var Mcom=document.all("ManageCom").value;
    if(Mcom =="")
    {
        alert ("请选择管理机构");
        return;
    }
    var StartFirstTrialDate =fm.StartFirstTrialDate.value;
    var EndFirstTrialDate =fm.EndFirstTrialDate.value;  
    if(StartFirstTrialDate==null||StartFirstTrialDate==""||EndFirstTrialDate==null||EndFirstTrialDate==""){
      alert("请录初审起止日期！");
      return false;
    }
    var gradeSql =" B.x ";
    var tComGrade = fm.ComGrade.value  ;
    if(tComGrade != "" && tComGrade.length > 0)
    {
    	if(tComGrade < Mcom.length)
    	{
    		fm.ComGrade.value = "";
    		fm.ComGradeName.value = "";
    		alert("请选择"+ Mcom.length+ "位及以上的统计粒度！");
    		return;
    	}else{
    		gradeSql = " substr(B.x,0,"+tComGrade+") " ;
    	}
    }
    
//    var addSql_state = " and 1=1 ";
    var state = fm.State.value;
//    if(state!= "")
//    {
//    	addSql_state = addSql_state + " and a.prtno like '86"+state+"%%' ";
//    }
    
    var strsql = "";
    if(_DBT==_DBO){
//    	strsql = " select "+gradeSql+", (case when sum(B.y) is not null then sum(B.y) else 0 end), sum(B.z), concat(round( (case when sum(B.y) is not null then sum(B.y) else 0 end) /sum(B.z), 3) * 100 , '%')  "
//		+" from ( select managecom x, count(*) z,  sum((select 1 from ljtempfee "
//		+" where tempfeetype = '1'  and rownum = 1 and otherno = a.contno and datediff(enteraccdate , a.firsttrialdate) <= 3 )) y "
//		+" from lccont a where 1=1  " 
//		+ getWherePart('a.firsttrialdate','StartFirstTrialDate','>=')	
//		+ getWherePart('a.firsttrialdate','EndFirstTrialDate','<=')	
//		+" and a.selltype != '08' "
//		//+" and substr(a.PrtNo,0,4) != '8616' " 
//		+" and a.managecom like '"+Mcom+"%%' "
//		+" and not exists (select 1  from lcpol where contno = a.contno and riskcode in ('141812', '311603', '111603')) "
//		+ getWherePart('a.SaleChnl','SaleChnl')
//		+addSql_state
//	    +"  group by a.managecom ) B "
//		+ " where 1=1 group by "+gradeSql+" order by "+gradeSql+" ";
    	
	  	var  StartFirstTrialDate0 = window.document.getElementsByName(trim("StartFirstTrialDate"))[0].value;
	  	var  EndFirstTrialDate0 = window.document.getElementsByName(trim("EndFirstTrialDate"))[0].value;
	  	var  SaleChnl0 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
		var sqlid0="ReceiveAccountSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("f1print.ReceiveAccountSql"); //指定使用的properties文件名
		mySql0.setSqlId(sqlid0);//指定使用的Sql的id
		mySql0.addSubPara(gradeSql);//指定传入的参数
		mySql0.addSubPara(StartFirstTrialDate0);//指定传入的参数
		mySql0.addSubPara(EndFirstTrialDate0);//指定传入的参数
		mySql0.addSubPara(Mcom);//指定传入的参数
		mySql0.addSubPara(SaleChnl0);//指定传入的参数
		mySql0.addSubPara(state);//指定传入的参数
		mySql0.addSubPara(gradeSql);//指定传入的参数
		mySql0.addSubPara(gradeSql);//指定传入的参数
		strsql=mySql0.getString();
    	
    	
    }else if(_DBT==_DBM){
//    	strsql = " select "+gradeSql+", (case when sum(B.y) is not null then sum(B.y) else 0 end), sum(B.z), concat(round( (case when sum(B.y) is not null then sum(B.y) else 0 end) /sum(B.z), 3) * 100 , '%')  "
//		+" from ( select managecom x, count(*) z,  sum((select 1 from ljtempfee "
//		+" where tempfeetype = '1'  and otherno = a.contno and datediff(enteraccdate , a.firsttrialdate) <= 3 limit 0,1 )) y "
//		+" from lccont a where 1=1  " 
//		+ getWherePart('a.firsttrialdate','StartFirstTrialDate','>=')	
//		+ getWherePart('a.firsttrialdate','EndFirstTrialDate','<=')	
//		+" and a.selltype != '08' "
//		//+" and substr(a.PrtNo,0,4) != '8616' " 
//		+" and a.managecom like '"+Mcom+"%%' "
//		+" and not exists (select 1  from lcpol where contno = a.contno and riskcode in ('141812', '311603', '111603')) "
//		+ getWherePart('a.SaleChnl','SaleChnl')
//		+addSql_state
//	    +"  group by a.managecom ) B "
//		+ " where 1=1 group by "+gradeSql+" order by "+gradeSql+" ";
    	
	  	var  StartFirstTrialDate1 = window.document.getElementsByName(trim("StartFirstTrialDate"))[0].value;
	  	var  EndFirstTrialDate1 = window.document.getElementsByName(trim("EndFirstTrialDate"))[0].value;
	  	var  SaleChnl1 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
		var sqlid1="ReceiveAccountSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("f1print.ReceiveAccountSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(gradeSql);//指定传入的参数
		mySql1.addSubPara(StartFirstTrialDate1);//指定传入的参数
		mySql1.addSubPara(EndFirstTrialDate1);//指定传入的参数
		mySql1.addSubPara(Mcom);//指定传入的参数
		mySql1.addSubPara(SaleChnl1);//指定传入的参数
		mySql1.addSubPara(state);//指定传入的参数
		mySql1.addSubPara(gradeSql);//指定传入的参数
		mySql1.addSubPara(gradeSql);//指定传入的参数
		strsql=mySql1.getString();
    }
    
	 
       //prompt( 1,strsql);
   turnPage.queryModal(strsql, CodeGrid);  
   

}


function easyPrint()
{
	easyQueryPrint(2,'CodeGrid','turnPage');
}