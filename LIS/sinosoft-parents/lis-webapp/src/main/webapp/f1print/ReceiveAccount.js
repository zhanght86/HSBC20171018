	//�������ƣ�ReceiveAccount.js
	//�����ܣ� ͳ�ƹ����б�ͳ�ơ�����3�յ�����
	//�������ڣ�2010-05-26
	//������  ��hanbin
	//���¼�¼��  ������    ��������     ����ԭ��/

var turnPage = new turnPageClass();


function easyQuery()
{
    var Mcom=document.all("ManageCom").value;
    if(Mcom =="")
    {
        alert ("��ѡ��������");
        return;
    }
    var StartFirstTrialDate =fm.StartFirstTrialDate.value;
    var EndFirstTrialDate =fm.EndFirstTrialDate.value;  
    if(StartFirstTrialDate==null||StartFirstTrialDate==""||EndFirstTrialDate==null||EndFirstTrialDate==""){
      alert("��¼������ֹ���ڣ�");
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
    		alert("��ѡ��"+ Mcom.length+ "λ�����ϵ�ͳ�����ȣ�");
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
		mySql0.setResourceName("f1print.ReceiveAccountSql"); //ָ��ʹ�õ�properties�ļ���
		mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
		mySql0.addSubPara(gradeSql);//ָ������Ĳ���
		mySql0.addSubPara(StartFirstTrialDate0);//ָ������Ĳ���
		mySql0.addSubPara(EndFirstTrialDate0);//ָ������Ĳ���
		mySql0.addSubPara(Mcom);//ָ������Ĳ���
		mySql0.addSubPara(SaleChnl0);//ָ������Ĳ���
		mySql0.addSubPara(state);//ָ������Ĳ���
		mySql0.addSubPara(gradeSql);//ָ������Ĳ���
		mySql0.addSubPara(gradeSql);//ָ������Ĳ���
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
		mySql1.setResourceName("f1print.ReceiveAccountSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(gradeSql);//ָ������Ĳ���
		mySql1.addSubPara(StartFirstTrialDate1);//ָ������Ĳ���
		mySql1.addSubPara(EndFirstTrialDate1);//ָ������Ĳ���
		mySql1.addSubPara(Mcom);//ָ������Ĳ���
		mySql1.addSubPara(SaleChnl1);//ָ������Ĳ���
		mySql1.addSubPara(state);//ָ������Ĳ���
		mySql1.addSubPara(gradeSql);//ָ������Ĳ���
		mySql1.addSubPara(gradeSql);//ָ������Ĳ���
		strsql=mySql1.getString();
    }
    
	 
       //prompt( 1,strsql);
   turnPage.queryModal(strsql, CodeGrid);  
   

}


function easyPrint()
{
	easyQueryPrint(2,'CodeGrid','turnPage');
}