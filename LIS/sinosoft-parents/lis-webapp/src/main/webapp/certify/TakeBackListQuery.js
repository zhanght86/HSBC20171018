//程序名称：TakeBackListQuery.js
//程序功能：查询保单回执清单和打印
//创建日期：2005-3-31
//创建人  ：weikai
//更新记录：  更新人    更新日期     更新原因/内容

var turnPage = new turnPageClass();


function easyQuery()
{
    var Mcom=document.all("ManageCom").value;
    if(Mcom =="")
    {
        alert ("请选择管理机构");
        return;
    }
    var tMaxDateb =fm.MaxDateb.value;
    var tMaxDatee =fm.MaxDatee.value;
    if(tMaxDateb==null||tMaxDateb==""||tMaxDatee==null||tMaxDatee==""){
        alert("请录入起止时间！");
        return false;
    }
    
    
//    var replySql = " nullif(greatest((case when (select max(lpt.replydate)  from lcrreport lpt Where lpt.contno = b.contno) is not null then to_char((select max(lpt.replydate)  from lcrreport lpt Where lpt.contno = b.contno),'yyyy-mm-dd') else '1000-01-01' end), (case when (select max(lne.repedate)  from lcpenotice lne where lne.contno = b.contno) is not null then to_char((select max(lne.repedate)  from lcpenotice lne where lne.contno = b.contno),'yyyy-mm-dd') else '1000-01-01' end),(case when (Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = b.prtno and lbm.activityid = '0000001112') is not null then to_char((Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = b.prtno and lbm.activityid = '0000001112'),'yyyy-mm-dd') else '1000-01-01' end)),to_date('1000-01-01','yyyy-mm-dd')) " ;
//    			
//  	var strsql="select b.contno,b.managecom,b.agentcom,b.agentcode,(select name from laagent where agentcode =b.agentcode),b.appntname,b.firsttrialdate,("+replySql+"),b.signdate,b.customgetpoldate,b.getpoldate,( select takebackoperator from lzsyscertify where certifyno=b.contno),b.delayreasondesc "
//               +" from lccont b"
//               +" where "
//               +" 1=1 "
//               +" and exists (select 1 from lzsyscertify c where certifyno =b.contno and certifycode in ('9994','9995')"
//               +getWherePart('c.TakeBackMakeDate','MaxDateb','>=','0')
//               +getWherePart('c.TakeBackMakeDate','MaxDatee','<=','0')
//               +" )"
//               +getWherePart('b.ManageCom','ManageCom','like','0')
//               +getWherePart('b.SaleChnl','SaleChnl','=','0')
//               +getWherePart('b.operator','Operator','=','0')
//               +" and b.conttype ='1'"
//               +" and b.selltype<>'08'"
//               + " and not exists (select 1 from lcpol where contno = b.contno  and riskcode in ('141812','311603','111603')) "
//               +" and b.appflag ='1'"
//               +" and b.managecom like '"+comCode+"%%'"
//               +" order by getpoldate"
//               ;
//          
        //alert(Dateb+Datee);
    
   
       //alert( strsql)
    var  MaxDateb2 = window.document.getElementsByName(trim("MaxDateb"))[0].value;
    var  MaxDatee2 = window.document.getElementsByName(trim("MaxDatee"))[0].value;
    var ManageCom2 = window.document.getElementsByName(trim("ManageCom"))[0].value;
    var  SaleChnl2 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
    var  operator2 = window.document.getElementsByName(trim("Operator"))[0].value;
  	var sqlid2="TakeBackListQuerySql1";
  	var mySql2=new SqlClass();
  	mySql2.setResourceName("certify.TakeBackListQuerySql"); //指定使用的properties文件名
  	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
  	mySql2.addSubPara(MaxDateb2);//指定传入的参数
  	mySql2.addSubPara(MaxDatee2);//指定传入的参数
  	mySql2.addSubPara(ManageCom2);//指定传入的参数
  	mySql2.addSubPara(SaleChnl2);//指定传入的参数
  	mySql2.addSubPara(operator2);//指定传入的参数
  	mySql2.addSubPara(ManageCom2);//指定传入的参数
  	strSql=mySql2.getString();
   turnPage.queryModal(strSql, CodeGrid);  
   

}


function easyPrint()
{
	easyQueryPrint(2,'CodeGrid','turnPage');
}