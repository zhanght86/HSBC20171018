//�������ƣ�TakeBackListQuery.js
//�����ܣ���ѯ������ִ�嵥�ʹ�ӡ
//�������ڣ�2005-3-31
//������  ��weikai
//���¼�¼��  ������    ��������     ����ԭ��/����

var turnPage = new turnPageClass();


function easyQuery()
{
    var Mcom=document.all("ManageCom").value;
    if(Mcom =="")
    {
        alert ("��ѡ��������");
        return;
    }
    var tMaxDateb =fm.MaxDateb.value;
    var tMaxDatee =fm.MaxDatee.value;
    if(tMaxDateb==null||tMaxDateb==""||tMaxDatee==null||tMaxDatee==""){
        alert("��¼����ֹʱ�䣡");
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
  	mySql2.setResourceName("certify.TakeBackListQuerySql"); //ָ��ʹ�õ�properties�ļ���
  	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
  	mySql2.addSubPara(MaxDateb2);//ָ������Ĳ���
  	mySql2.addSubPara(MaxDatee2);//ָ������Ĳ���
  	mySql2.addSubPara(ManageCom2);//ָ������Ĳ���
  	mySql2.addSubPara(SaleChnl2);//ָ������Ĳ���
  	mySql2.addSubPara(operator2);//ָ������Ĳ���
  	mySql2.addSubPara(ManageCom2);//ָ������Ĳ���
  	strSql=mySql2.getString();
   turnPage.queryModal(strSql, CodeGrid);  
   

}


function easyPrint()
{
	easyQueryPrint(2,'CodeGrid','turnPage');
}