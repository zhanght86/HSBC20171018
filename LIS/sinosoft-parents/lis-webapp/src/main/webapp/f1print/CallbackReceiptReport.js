//�������ƣ�CallbackReceiptReport.js
//�����ܣ�Ϊ���ջ�ִ��ѯ��ӡ
//�������ڣ�2010-05-11
//������  ��hanbin
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
    var tsigndate =fm.SignDateStart.value;
    var tsigndateend =fm.SignDateEnd.value;
    if(tsigndateend==null||tsigndateend==""||tsigndate==null||tsigndate==""){
      alert("��¼�뱣��ǩ������ֹʱ�䣡");
      return false;
    }
    

    
//    var strsql=" select a.contno,a.managecom,a.agentcom,a.agentcode,(select c.name from laagent c where a.agentcode=c.agentcode),case("+cleanSql+",'1','������','����'),a.firsttrialdate ,(select max(makedate) from es_doc_main where doccode = a.prtno and subtype = 'UA001')   "
//               +" '������ظ�ʱ��',(select min(enteraccdate) from ljtempfee where ohterno = a.contno ),a.signdate from lccont a "
//               +" where  a.grpcontno like '%%0000000000' and a.appflag='1'  "
//               //�ų� ��ʽ���� hanbin - 2010-05-04
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
//               //�ų� ��ʽ���� hanbin - 2010-05-04
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
    
    //������ִ�嵥�ͱ������ hanbin - 2010-05-06
//    var cleanSql = " select 1 from lcissuepol where backobjtype = '2' and  needprint = 'Y' and contno = a.contno union  select 1 from lcpenotice where contno = a.contno union select 1 from LCRReport where contno = a.contno union select 1 from lccspec where contno = a.contno  union select 1 from lcprem where payplancode like '000000%' and contno = a.contno union select 1 from lbmission where missionprop1 = a.prtno and activityid = '0000001100' and missionprop18 = '6' ";
//    var replySql = " nullif(greatest(nvl((select max(lpt.replydate)  from lcrreport lpt Where lpt.contno = a.contno), '2000-01-01'), nvl((Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid = '0000001111'), '2000-01-01'), nvl((Select max(makedate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid = '0000001100'and missionprop18 = '6'), '2000-01-01'),nvl((Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid = '0000001112'), '2000-01-01')),date'2000-01-01') " ;
    //�޸�Ϊ��functionid��ѯ��lccuwmaster���uwstate��ѯ״̬ 2013-04-22 lzf
//    var cleanSql = " select 1 from lcissuepol where backobjtype = '2' and  needprint = 'Y' and contno = a.contno union  select 1 from lcpenotice where contno = a.contno union select 1 from LCRReport where contno = a.contno union select 1 from lccspec where contno = a.contno  union select 1 from lcprem where payplancode like '000000%' and contno = a.contno union select 1 from lbmission where missionprop1 = a.prtno and activityid in (select activityid from lwactivity  where functionid='10010028') and exists (select 1 from lccuwmaster where contno= a.prtno and uwstate='6') ";
//    var replySql = " nullif(greatest((case when (select max(lpt.replydate)  from lcrreport lpt Where lpt.contno = a.contno) is not null then to_char((select max(lpt.replydate)  from lcrreport lpt Where lpt.contno = a.contno),'yyyy-mm-dd') else '2000-01-01' end), (case when (Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid in (select activityid from lwactivity  where functionid='10010029')) is not null then to_char((Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid in (select activityid from lwactivity  where functionid='10010029')),'yyyy-mm-dd') else '2000-01-01' end), (case when (Select max(makedate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid in (select activityid from lwactivity where functionid='10010028') and exists (select 1 from lccuwmaster where contno= a.prtno and uwstate='6')) is not null then to_char((Select max(makedate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid in (select activityid from lwactivity where functionid='10010028') and exists (select 1 from lccuwmaster where contno= a.prtno and uwstate='6')),'yyyy-mm-dd') else '2000-01-01' end),(case when (Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid in (select activityid from lwactivity  where functionid='10010030')) is not null then to_char((Select max(outdate)  from lbmission lbm Where lbm.missionprop1 = a.prtno and lbm.activityid in (select activityid from lwactivity  where functionid='10010030')),'yyyy-mm-dd') else '2000-01-01' end)),to_date('2000-01-01','yyyy-mm-dd')) " ;
   
    var strsqlID="";
    
//    var strsql= " select a.contno,a.managecom,a.agentcom,a.agentcode,(select c.name from laagent c where a.agentcode=c.agentcode),(case ("+cleanSql+") when  1  then '������' else '����' end),a.firsttrialdate ,(select max(makedate) from es_doc_main where doccode = a.prtno and subtype = 'UA001'), "
//    		+" "+replySql+",(select min(enteraccdate) from ljtempfee where otherno = a.contno ),a.signdate from lccont a "
//		    +" where  a.appflag='1' and a.conttype ='1'  and a.getpoldate is null "
//		    //�ų� ��ʽ���� hanbin - 2010-05-04
//		    + " and not exists (select 1 from lcpol where contno = a.contno  and riskcode in ('141812','311603','111603')) "
//		    + " and a.selltype != '08' "
//		  //+getWherePart('a.ManageCom','ManageCom','like','0')
//		    +getWherePart('a.SaleChnl','SaleChnl','=','0')
//		    +getWherePart('a.agentcode','AgentCode','=','0')
//		    +getWherePart('a.agentcom','AgentCom','=','0')
//		    +getWherePart('a.signdate','signdate','>=','0')
//		    +getWherePart('a.signdate','signdateend','<=','0')+" and a.ManageCom like '"+document.all("ManageCom").value+"%25' and a.ManageCom like '"+comCode+"%%'";
    		if(fm.CleanPolFlag.value != "" && fm.CleanPolFlag.value == "0")
    		{//������
//    			strsql = strsql + " and exists ("+cleanSql+")  ";
    			strsqlID = "0";
    			
    			if(fm.StatType.value != null && fm.StatType.value == "1")
    			{//����δ������
    				
//    				strsql = strsql + " and to_date(substr(now(),1,10),'yyyy-mm-dd') -  "+replySql+" >= 19  order by a.firsttrialdate" ;
    				strsqlID +="1"; //CallbackReceiptReportSql01
    			
    			}else if(fm.StatType.value != null && fm.StatType.value == "0")
    			{//Ԥ����
//    				strsql = strsql + " and to_date(substr(now(),1,10),'yyyy-mm-dd') -  "+replySql+" >= 15   and to_date(substr(now(),1,10),'yyyy-mm-dd') -  "+replySql+" < 19 order by a.firsttrialdate" ;
    				strsqlID +="0"; //CallbackReceiptReportSql00
    			
    			}else{
//    				strsql = strsql + " order by a.firsttrialdate ";
    				strsqlID +="2"; //CallbackReceiptReportSql02
    			
    			}
    			
    		}else if(fm.CleanPolFlag.value != "" && fm.CleanPolFlag.value == "1")
    		{//����
//    			strsql = strsql + " and not exists ("+cleanSql+")  ";
    			strsqlID = "1";
    			
    			if(fm.StatType.value != null && fm.StatType.value == "1")
    			{//����δ������
//    				strsql = strsql + " and datediff(to_date(substr(now(),1,10),'yyyy-mm-dd') , a.firsttrialdate) >= 19   order  by a.firsttrialdate";
    				strsqlID +="1"; //CallbackReceiptReportSql11
    			
    			}else if(fm.StatType.value != null && fm.StatType.value == "0")
    			{//Ԥ����
//    				strsql = strsql + " and datediff(to_date(substr(now(),1,10),'yyyy-mm-dd') ,  a.firsttrialdate)  >= 15  and  datediff(to_date(substr(now(),1,10),'yyyy-mm-dd') ,  a.firsttrialdate)  < 19 order by a.firsttrialdate" ;
    				strsqlID +="0"; //CallbackReceiptReportSql10
    			
    			}else{
//    				strsql = strsql + " order by a.firsttrialdate ";
    				strsqlID +="2"; //CallbackReceiptReportSql12
    			
    			}
    		}else
    		{
    			strsqlID = "2";
    			
    			if(fm.StatType.value != null && fm.StatType.value == "1")
    			{
//    				strsql = strsql + " and ((1=(case ("+cleanSql+") when  1  then 1 else 0 end) and to_date(substr(now(),1,10),'yyyy-mm-dd') -  "+replySql+" >= 19 ) or (0 = (case ("+cleanSql+") when  1  then 1 else 0 end) and  to_date(substr(now(),1, 10), 'yyyy-mm-dd') - a.firsttrialdate >= 19)) order by a.firsttrialdate ";
    				strsqlID +="1"; //CallbackReceiptReportSql21
    			
    			}else if(fm.StatType.value != null && fm.StatType.value == "0")
    			{
    				
//    				strsql = strsql + " and ((1=(case ("+cleanSql+") when  1  then 1 else 0 end) to_date(substr(now(),1,10),'yyyy-mm-dd') -  "+replySql+" >= 15   and to_date(substr(now(),1,10),'yyyy-mm-dd') -  "+replySql+" < 19  ) or (0 = (case ("+cleanSql+") when  1  then 1 else 0 end) and  and datediff(to_date(substr(now(),1,10),'yyyy-mm-dd') ,  a.firsttrialdate)  >= 15  and  datediff(to_date(substr(now(),1,10),'yyyy-mm-dd') ,  a.firsttrialdate)  < 19 )) order by a.firsttrialdate ";
//    				strsql = strsql + " and ((1=(case ("+cleanSql+") when  1  then 1 else 0 end) and to_date(substr(now(),1,10),'yyyy-mm-dd') -  "+replySql+" >= 15   and to_date(substr(now(),1,10),'yyyy-mm-dd') -  "+replySql+" < 19  ) or (0 = (case ("+cleanSql+") when  1  then 1 else 0 end)  and datediff(to_date(substr(now(),1,10),'yyyy-mm-dd') ,  a.firsttrialdate)  >= 15  and  datediff(to_date(substr(now(),1,10),'yyyy-mm-dd') ,  a.firsttrialdate)  < 19 )) order by a.firsttrialdate ";//���Ͼ��޸�
    				
    				strsqlID +="0"; //CallbackReceiptReportSql20
    			
    			}else
    			{
//    				strsql = strsql   +" order by a.firsttrialdate " ;
    				strsqlID +="2"; //CallbackReceiptReportSql22
    			}
    		}
	 
		  	var  SaleChnl1 = window.document.getElementsByName(trim("SaleChnl"))[0].value;
		  	var  AgentCode1 = window.document.getElementsByName(trim("AgentCode"))[0].value;
		  	var  AgentCom1 = window.document.getElementsByName(trim("AgentCom"))[0].value;
		  	var  signdate1 = window.document.getElementsByName(trim("signdate"))[0].value;
		  	var  signdateend1 = window.document.getElementsByName(trim("signdateend"))[0].value;
			var sqlid1="CallbackReceiptReportSql"+strsqlID;
			var mySql1=new SqlClass();
			mySql1.setResourceName("f1print.CallbackReceiptReportSql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(SaleChnl1);//ָ������Ĳ���
			mySql1.addSubPara(AgentCode1);//ָ������Ĳ���
			mySql1.addSubPara(AgentCom1);//ָ������Ĳ���
			mySql1.addSubPara(signdate1);//ָ������Ĳ���
			mySql1.addSubPara(signdateend1);//ָ������Ĳ���
			mySql1.addSubPara(document.all("ManageCom").value);//ָ������Ĳ���
			mySql1.addSubPara(comCode);//ָ������Ĳ���
			var strsql=mySql1.getString();
    		
    		
  //     alert( strsql)
   turnPage.queryModal(strsql, CodeGrid);  
   

}


function easyPrint()
{
	var Mcom=document.all("ManageCom").value;
    if(Mcom =="")
    {
        alert ("��ѡ��������");
        return;
    }
    var tsigndate =fm.SignDateStart.value;
    var tsigndateend =fm.SignDateEnd.value;
    if(tsigndateend==null||tsigndateend==""||tsigndate==null||tsigndate==""){
      alert("��¼�뱣��ǩ������ֹʱ�䣡");
      return false;
    }
    
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.target="f1print";
    showInfo.close();
    document.getElementById("fm").submit();
}