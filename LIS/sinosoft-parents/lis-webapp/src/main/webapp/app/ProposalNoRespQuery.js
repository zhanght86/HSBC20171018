//
//�������ƣ�ProposalNoRespQuery.js
//�����ܣ���ѯ����δ�ظ��嵥
//�������ڣ�2007-03-20 18:02
//������  ��Fuqx
//���¼�¼��  ������    ��������     ����ԭ��/����

//var arrDataSet;
var turnPage = new turnPageClass();
var sqlresourcename = "app.ProposalNoRespQuerySql";
//�򵥲�ѯ
function easyQuery()
{
	var ManageCom = document.all('ManageCom').value;
	var sDate = document.all('IssueStartDate').value;
	var eDate = document.all('IssueEndDate').value;
	if (ManageCom == "")
	{
			alert("��ѡ����������");
			return;
	}
//	if(ManageCom.length!=6 && ManageCom.length!=8){
//		alert("�������ӦΪ6λ��8λ��");
//		return;
//	}  
	//if(document.all('PrtNo').value=="")
	//{
	 if (sDate == "")
	  {
			alert("������������·���ʼʱ�䣡");
			return;
	  }
	  if (eDate == "")
	  {
			alert("������������·�����ʱ�䣡");
			return;
  	}
  //}
	initCodeGrid();
	// ��дSQL���
	var addsql = "";
	addsql = " and a.managecom like '"+ManageCom+"%%'  and c.senddate >= '"+sDate+"' and c.senddate <= '"+eDate+"' ";
	if(fm.RiskCode.value != "")
	{
		addsql = addsql + " and exists(select 1 from lcpol where contno = a.contno and riskcode = '"+fm.RiskCode.value+"')  "
	}
	if(fm.SaleChnl.value != "")
	{
		addsql = addsql + " and a.salechnl  = '"+fm.SaleChnl.value+"' "
	}
	if(fm.AgentCode.value != "")
	{
		addsql = addsql + " and a.AgentCode  = '"+fm.AgentCode.value+"' "
	}
	if(fm.InsuredName.value != "")
	{
		addsql = addsql + " and a.InsuredName  = '"+fm.InsuredName.value+"' "
	}
	if(fm.StartDate.value != "")
	{
		addsql = addsql + " and a.firsttrialdate  >= '"+fm.StartDate.value+"' "
	}
	if(fm.EndDate.value != "")
	{
		addsql = addsql + " and a.firsttrialdate  <= '"+fm.EndDate.value+"' "
	}
	if(fm.ScanStartDate.value != "")
	{
		addsql = addsql + " and b.makedate  >= '"+fm.ScanStartDate.value+"' "
	}
	if(fm.ScanEndDate.value != "")
	{
		addsql = addsql + " and b.makedate  <= '"+fm.ScanEndDate.value+"' "
	}
	
//	alert("addsql :" + addsql);
	var addsql22 = " and 2=2 ";
	/*var strSQL = " select X.a, "
			+"        X.b, "
			+"        X.c, "
			+"        X.d, "
			+"        X.e, "
			+"        X.f, "
			+"        X.g, "
			+"        X.o, "
			+"        X.m, "
			+"        X.h, "
			+"        X.i, "
			+"        X.x, "
			+"        X.j, "
			+"        X.l, "
			+"        Y.codename, "
			+"        X.y "
			+"   from (select a.managecom a, "
			+"                a.prtno b, "
			+"                a.proposalcontno c, "
			+"                a.appntname d, "
			+"                a.insuredname e, "
			+"                (case when (select sum(amnt) "
			+"                      from lcpol "
			+"                     where contno = a.contno "
			+"                       and mainpolno = polno  "
			+"                    ) is not null then (select sum(amnt) "
			+"                      from lcpol "
			+"                     where contno = a.contno "
			+"                       and mainpolno = polno  "
			+"                    )"
			+"                    else 0 end) f, "
			+"                (case when (select sum(prem) "
			+"                      from lcpol "
			+"                     where contno = a.contno  "
			+"                    ) is not null then (select sum(prem) "
			+"                      from lcpol "
			+"                     where contno = a.contno  "
			+"                    )"
			+"                    else 0 end) g, "
			+"                c.OperatePos o, "
			+"                d.agentcode m, "
			+"                (select name from laagent where agentcode = a.agentcode) h, "
			+"                a.salechnl i, "
			+"                a.firsttrialdate x, "
			+"                concat(concat(to_char(b.makedate,'yyyy-mm-dd'),' '),b.maketime) j, "
			+"                concat(concat(to_char(c.senddate,'yyyy-mm-dd') ,' '), c.sendtime) l, "
			+"                '01' k, "
			+"               (datediff(to_date(substr(now() ,1,10),'yyyy-mm-dd'),c.senddate)) y "
			+"           from lccont a, es_doc_main b, lcissuepol c, lcpol d "
			+"          where a.prtno = b.doccode "
			+"            and a.contno = d.contno "
			+"            and c.backobjtype != '1' "
			+"            and b.busstype = 'TB' "
			+"            and b.subtype = 'UA001' "
			+"            and a.appflag = '0' "
			+"            and a.uwflag not in ('1', '2', 'a') "
			+"            and c.needprint = 'Y' "
			+"            and a.conttype = '1' "
			+"            and a.contno = c.contno "
			+"            and c.standbyflag2 = 'Y' "
			+"            and c.backobjtype = '2' "
			+"            and c.state is not null "
			+"            and (c.replyresult is null and c.replydate is null) "
			+ addsql
			+ getWherePart('c.OperatePos','OperatePos')	
			+"         union "
			+"         select a.managecom a, "
			+"                a.prtno b, "
			+"                a.proposalcontno c, "
			+"                a.appntname d, "
			+"                a.insuredname e, "
			+"                (case when (select sum(amnt) "
			+"                      from lcpol "
			+"                     where contno = a.contno "
			+"                       and mainpolno = polno  "
			+"                    ) is not null then (select sum(amnt) "
			+"                      from lcpol "
			+"                     where contno = a.contno "
			+"                       and mainpolno = polno  "
			+"                    ) "
			+"                    else 0 end) f, "
			+"                (case when (select sum(prem) "
			+"                      from lcpol "
			+"                     where contno = a.contno  "
			+"                    ) is not null then (select sum(prem) "
			+"                      from lcpol "
			+"                     where contno = a.contno  "
			+"                    ) "
			+"                    else 0 end) g, "
			+"                c.OperatePos o, "
			+"                d.agentcode m, "
			+"                (select name from laagent where agentcode = a.agentcode) h, "
			+"                a.salechnl i, "
			+"                a.firsttrialdate x, "
			+"                concat(concat(to_char(b.makedate,'yyyy-mm-dd'),' '),b.maketime) j, "
			+"                concat(concat(to_char(c.senddate,'yyyy-mm-dd') ,' '),c.sendtime) l, "
			+"                '02' k, "
			+"                (datediff(to_date(substr(now() ,1,10),'yyyy-mm-dd'),c.senddate))  y "
			+"           from lccont a, es_doc_main b, lcissuepol c, lcpol d "
			+"          where a.prtno = b.doccode "
			+"            and a.contno = d.contno "
			+"            and c.backobjtype != '1' "
			+"            and b.busstype = 'TB' "
			+"            and b.subtype = 'UA001' "
			+"            and a.appflag = '0' "
			+"            and a.uwflag not in ('1', '2', 'a') "
			+"            and c.needprint = 'Y' "
			+"            and a.conttype = '1' "
			+"            and a.contno = c.contno "
			+"            and c.standbyflag2 = 'N' "
			+"            and c.backobjtype = '2' "
			+"            and c.state is not null "
			+"            and (c.replyresult is null and c.replydate is null) "
//			+"            AND a.ManageCom Like '8611%' "
//			+"            AND a.ManageCom Like '86%' "
//			+"            AND d.Makedate >= '2010-04-19' "
//			+"            AND d.Makedate <= '2010-05-19' "
//			+"            AND c.Senddate >= '2010-02-25' "
//			+"            AND c.Senddate <= '2010-05-19' "
			+ addsql
			+ getWherePart('c.OperatePos','OperatePos')	
			+"         union "
			+"         select a.managecom a, "
			+"                a.prtno b, "
			+"                a.proposalcontno c, "
			+"                a.appntname d, "
			+"                a.insuredname e, "
			+"                (case when (select sum(amnt) "
			+"                      from lcpol "
			+"                     where contno = a.contno "
			+"                       and mainpolno = polno  "
			+"                    ) is not null then (select sum(amnt) "
			+"                      from lcpol "
			+"                     where contno = a.contno "
			+"                       and mainpolno = polno  "
			+"                    )"
			+"                    else 0 end) f, "
			+"                (case when (select sum(prem) "
			+"                      from lcpol "
			+"                     where contno = a.contno  "
			+"                    ) is not null then  (select sum(prem) "
			+"                      from lcpol "
			+"                     where contno = a.contno  "
			+"                    ) "
			+"                    else 0 end) g, "
			+"                c.OperatePos o, "
			+"                d.agentcode m, "
			+"                (select name from laagent where agentcode = a.agentcode) h, "
			+"                a.salechnl i, "
			+"               a.firsttrialdate x, "
			+"                concat(concat(to_char(b.makedate,'yyyy-mm-dd'),' '),b.maketime) j, "
			+"                concat(concat(to_char(c.senddate,'yyyy-mm-dd'),' '),c.sendtime) l, "
			+"                '03' k, "
			+"                (datediff(to_date(substr(now() ,1,10),'yyyy-mm-dd'),c.senddate))  y "
			+"           from lccont a, es_doc_main b, lcissuepol c, lcpol d "
			+"          where a.prtno = b.doccode "
			+"            and a.contno = d.contno "
			+"            and b.busstype = 'TB' "
			+"            and c.backobjtype != '1' "
			+"            and b.subtype = 'UA001' "
			+"            and a.appflag = '0' "
			+"            and a.uwflag not in ('1', '2', 'a') "
			+"            and c.needprint = 'Y' "
			+"            and a.conttype = '1' "
			+"            and a.contno = c.contno "
			+"            and c.backobjtype = '3' "
			+"            and ((c.operatepos = '6' and c.state is not null) or "
			+"                c.operatepos <> '6') "
			+"            and (c.replyresult is null and c.replydate is null) "
//			+"            AND a.ManageCom Like '8611%' "
//			+"            AND a.ManageCom Like '86%' "
//			+"            AND d.Makedate >= '2010-04-19' "
//			+"            AND d.Makedate <= '2010-05-19' "
//			+"            AND c.Senddate >= '2010-02-25' "
//			+"            AND c.Senddate <= '2010-05-19' "
			+ addsql
			+ getWherePart('c.OperatePos','OperatePos')	
			+"         union "
			+"         select a.managecom a, "
			+"                a.prtno b, "
			+"                a.proposalcontno c, "
			+"                a.appntname d, "
			+"                a.insuredname e, "
			+"                (case when (select sum(amnt) "
			+"                      from lcpol "
			+"                     where contno = a.contno "
			+"                       and mainpolno = polno  "
			+"                    ) is not null then (select sum(amnt) "
			+"                      from lcpol "
			+"                     where contno = a.contno "
			+"                       and mainpolno = polno  "
			+"                    ) "
			+"                    else 0 end) f, "
			+"                (case when (select sum(prem) "
			+"                      from lcpol "
			+"                     where contno = a.contno  "
			+"                    ) is not null then (select sum(prem) "
			+"                      from lcpol "
			+"                     where contno = a.contno  "
			+"                    ) "
			+"                    else 0 end) g, "
			+"                c.OperatePos o, "
			+"                d.agentcode m, "
			+"                (select name from laagent where agentcode = a.agentcode) h, "
			+"                a.salechnl i, "
			+"                a.firsttrialdate x, "
			+"                concat(concat(to_char(b.makedate,'yyyy-mm-dd'),' '),b.maketime) j, "
			+"                concat(concat(to_char(c.senddate,'yyyy-mm-dd'),' '), c.sendtime) l, "
			+"                '04' k, "
			+"                (datediff(to_date(substr(now() ,1,10),'yyyy-mm-dd'),c.senddate))  y "
			+"           from lccont a, es_doc_main b, lcissuepol c, lcpol d "
			+"          where a.prtno = b.doccode "
			+"            and a.contno = d.contno "
			+"            and c.backobjtype != '1' "
			+"            and b.busstype = 'TB' "
			+"            and b.subtype = 'UA001' "
			+"            and a.appflag = '0' "
			+"            and a.uwflag not in ('1', '2', 'a') "
			+"            and c.needprint = 'Y' "
			+"            and a.conttype = '1' "
			+"            and a.contno = c.contno "
			+"            and c.backobjtype = '4' "
			+"            and ((c.operatepos = '6' and c.state is not null) or "
			+"                c.operatepos <> '6') "
			+"            and (c.replyresult is null and c.replydate is null) "
//			+"            AND a.ManageCom Like '8611%' "
//			+"            AND a.ManageCom Like '86%' "
//			+"            AND d.Makedate >= '2010-04-19' "
//			+"            AND d.Makedate <= '2010-05-19' "
//			+"            AND c.Senddate >= '2010-02-25' "
//			+"            AND c.Senddate <= '2010-05-19' "
			+ addsql
			+ getWherePart('c.OperatePos','OperatePos')	
			//�ų� 05 -����������޸ĸ� hanbin -2010-05-24
//			+"         union "
//			+"         select a.managecom a, "
//			+"                a.prtno b, "
//			+"                a.proposalcontno c, "
//			+"                a.appntname d, "
//			+"                a.insuredname e, "
//			+"                nvl((select sum(amnt) "
//			+"                      from lcpol "
//			+"                     where contno = a.contno "
//			+"                       and mainpolno = polno  "
//			+"                    ), "
//			+"                    0) f, "
//			+"                nvl((select sum(prem) "
//			+"                      from lcpol "
//			+"                     where contno = a.contno  "
//			+"                    ), "
//			+"                    0) g, "
//			+"                d.agentcode m, "
//			+"                (select name from laagent where agentcode = a.agentcode) h, "
//			+"                a.salechnl i, "
//			+"                a.firsttrialdate x,"
//			+"                to_char(b.makedate,'yyyy-mm-dd')||' '||b.maketime j, "
//			+"                to_char(c.senddate,'yyyy-mm-dd') ||' '|| c.sendtime l, "
//			+"                '05' k, "
//			+"                (to_date(substr(sysdate ,0,10),'yyyy-mm-dd') -c.senddate)  y "
//			+"           from lccont a, es_doc_main b, lcissuepol c, lcpol d "
//			+"          where a.prtno = b.doccode "
//			+"            and a.contno = d.contno "
//			+"            and b.busstype = 'TB' "
//			+"            and b.subtype = 'UA001' "
//			+"            and a.appflag = '0' "
//			+"            and a.uwflag not in ('1', '2', 'a') "
//			+"            and a.conttype = '1' "
//			+"            and a.contno = c.contno "
//			+"            and c.standbyflag2 = 'Y' "
//			+"            and c.backobjtype = '5' "
//			+"            and ((c.operatepos = '6' and c.state is not null) or "
//			+"                c.operatepos <> '6') "
//			+"            and (c.replyresult is null and c.replydate is null) "
////			+"            AND a.ManageCom Like '8611%' "
////			+"            AND a.ManageCom Like '86%' "
////			+"            AND d.Makedate >= '2010-04-19' "
////			+"            AND d.Makedate <= '2010-05-19' "
////			+"            AND c.Senddate >= '2010-02-25' "
////			+"            AND c.Senddate <= '2010-05-19' "
//			+ addsql
			+"         union "
			+"         select a.managecom a, "
			+"                a.prtno b, "
			+"                a.proposalcontno c, "
			+"                a.appntname d, "
			+"                a.insuredname e, "
			+"                (case when (select sum(amnt) "
			+"                      from lcpol "
			+"                     where contno = a.contno "
			+"                       and mainpolno = polno  "
			+"                    ) is not null then (select sum(amnt) "
			+"                      from lcpol "
			+"                     where contno = a.contno "
			+"                       and mainpolno = polno  "
			+"                    )  "
			+"                    else 0 end) f, "
			+"                (case when (select sum(prem) "
			+"                      from lcpol "
			+"                     where contno = a.contno  "
			+"                    ) is not null then (select sum(prem) "
			+"                      from lcpol "
			+"                     where contno = a.contno  "
			+"                    ) "
			+"                    else 0 end) g, "
			+"                '6' o, "
			+"                d.agentcode m, "
			+"                (select name from laagent where agentcode = a.agentcode) h, "
			+"                a.salechnl i, "
			+"                a.firsttrialdate x, "
			+"                concat(concat(to_char(b.makedate,'yyyy-mm-dd'),' '),b.maketime) j, "
			+"                concat(concat(to_char(c.senddate,'yyyy-mm-dd'),' '), c.sendtime) l, "
			+"                '06' k, "
			+"                (datediff(to_date(substr(now() ,1,10),'yyyy-mm-dd') ,c.senddate))  y "
			+"           from lccont a, es_doc_main b, lcrreport c, lcpol d "
			+"          where a.prtno = b.doccode "
			+"            and a.contno = d.contno "
			+"            and b.busstype = 'TB' "
			+"            and b.subtype = 'UA001' "
			+"            and a.appflag = '0' "
			+"            and a.uwflag not in ('1', '2', 'a') "
			+"            and a.conttype = '1' "
			+"            and a.contno = c.contno "
			+"            and c.replyflag is not null "
			+"            and c.replycontente is null "
//			+"            AND a.ManageCom Like '8611%' "
//			+"            AND a.ManageCom Like '86%' "
//			+"            AND d.Makedate >= '2010-04-19' "
//			+"            AND d.Makedate <= '2010-05-19' "
//			+"            AND c.Senddate >= '2010-02-25' "
//			+"            AND c.Senddate <= '2010-05-19' "
			+ addsql
			+ getWherePart('to_char(trim(cast(6 as char(20))))','OperatePos')	
			+"         union "
			+"         select a.managecom a, "
			+"                a.prtno b, "
			+"                a.proposalcontno c, "
			+"                a.appntname d, "
			+"                a.insuredname e, "
			+"                (case when (select sum(amnt) "
			+"                      from lcpol "
			+"                     where contno = a.contno "
			+"                       and mainpolno = polno  "
			+"                    ) is not null then (select sum(amnt) "
			+"                      from lcpol "
			+"                     where contno = a.contno "
			+"                       and mainpolno = polno  "
			+"                    )"
			+"                    else 0 end) f, "
			+"                (case when (select sum(prem) "
			+"                      from lcpol "
			+"                     where contno = a.contno  "
			+"                    ) is not null then (select sum(prem) "
			+"                      from lcpol "
			+"                     where contno = a.contno  "
			+"                    )"
			+"                    else 0 end) g, "
			+"                '6' o, "
			+"                d.agentcode m, "
			+"                (select name from laagent where agentcode = a.agentcode) h, "
			+"                a.salechnl i, "
			+"                a.firsttrialdate x, "
			+"                concat(concat(to_char(b.makedate,'yyyy-mm-dd'),' '),b.maketime) j, "
			+"                concat(concat(to_char(c.senddate,'yyyy-mm-dd') ,' '), c.sendtime) l, "
			+"                '07' k, "
			+"                (datediff(to_date(substr(now(),1,10),'yyyy-mm-dd') ,c.senddate))  y "
			+"           from lccont a, es_doc_main b, lcpenotice c, lcpol d "
			+"          where a.prtno = b.doccode "
			+"            and a.contno = d.contno "
			+"            and b.busstype = 'TB' "
			+"            and b.subtype = 'UA001' "
			+"            and a.appflag = '0' "
			+"            and a.uwflag not in ('1', '2', 'a') "
			+"            and a.conttype = '1' "
			+"            and a.contno = c.contno "
			+"            and c.printflag is not null "
			+"            and c.peresult is null "
//			+"            AND a.ManageCom Like '8611%' "
//			+"            AND a.ManageCom Like '86%' "
//			+"            AND d.Makedate >= '2010-04-19' "
//			+"            AND d.Makedate <= '2010-05-19' "
//			+"            AND c.Senddate >= '2010-02-25' "
//			+"            AND c.Senddate <= '2010-05-19'" 
			+ addsql
			+ getWherePart('to_char(trim(cast(6 as char(20))))','OperatePos')	
			+		      ") X, "
			+"        ldcode Y "
			+"  where Y.codetype = 'issuenoticetype' "
			+"    and X.k = Y.code ";
			if(fm.IssueNoticeType.value != "")
			{
				strSQL = strSQL + " and Y.code = '"+fm.IssueNoticeType.value+"' order by X.l ";
			}else{
				strSQL = strSQL +"  order by X.l ";
			}*/
			

	//prompt("abc",strSQL);
			
	var sqlid10="ProposalNoRespQuerySql10";
	var mySql10=new SqlClass();
	mySql10.setResourceName(sqlresourcename);
	mySql10.setSqlId(sqlid10);
	mySql10.addSubPara(addsql);		
	mySql10.addSubPara(window.document.getElementsByName(trim('OperatePos'))[0].value);		
	mySql10.addSubPara(addsql);		
	mySql10.addSubPara(window.document.getElementsByName(trim('OperatePos'))[0].value);		
	mySql10.addSubPara(addsql);		
	mySql10.addSubPara(window.document.getElementsByName(trim('OperatePos'))[0].value);		
	mySql10.addSubPara(addsql);		
	mySql10.addSubPara(window.document.getElementsByName(trim('OperatePos'))[0].value);		
	mySql10.addSubPara(addsql);		
	mySql10.addSubPara(window.document.getElementsByName(trim('OperatePos'))[0].value);		
	mySql10.addSubPara(addsql);		
	mySql10.addSubPara(window.document.getElementsByName(trim('OperatePos'))[0].value);		
	mySql10.addSubPara(fm.IssueNoticeType.value);		
	var strSQL = mySql10.getString();
	turnPage.queryModal(strSQL, CodeGrid);    
}

function easyPrint()
{
	var ManageCom = document.all('ManageCom').value;
	var sDate = document.all('IssueStartDate').value;
	var eDate = document.all('IssueEndDate').value;
	if (ManageCom == "")
	{
			alert("��ѡ����������");
			return;
	}
//	if(ManageCom.length!=6 && ManageCom.length!=8){
//		alert("�������ӦΪ6λ��8λ��");
//		return;
//	}  
	//if(document.all('PrtNo').value=="")
	//{
	 if (sDate == "")
	  {
			alert("������������·���ʼʱ�䣡");
			return;
	  }
	  if (eDate == "")
	  {
			alert("������������·�����ʱ�䣡");
			return;
  	}
	var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  var arrReturn = new Array();
		fm.target = "../f1print";
		//fm.action = "";
		document.getElementById("fm").submit();
		showInfo.close();
}

//��ѯ�����˵ķ�ʽ
function queryAgent(comcode)
{
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
	var cAgentCode = fm.AgentCode.value;  //�����˱���	
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
  
var sqlid8="ProposalNoRespQuerySql8";
var mySql8=new SqlClass();
mySql8.setResourceName(sqlresourcename);
mySql8.setSqlId(sqlid8);
mySql8.addSubPara(cAgentCode);	
  
    var arrResult = easyExecSql(mySql8.getString());
    //alert(arrResult);
    if (arrResult != null) 
    {
			alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else
    {
			
			alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
    }
	}	
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
//  	fm.QAgentGroup.value = arrResult[0][1];
  }
}
function queryAgent2()
{
	if(document.all('AgentCode').value != "" && document.all('AgentCode').value.length==10 )	 {
	var cAgentCode = fm.AgentCode.value; 
	//var strSql = "select AgentCode,Name from LAAgent where AgentCode='" + cAgentCode +"'";
   
var sqlid9="ProposalNoRespQuerySql9";
var mySql9=new SqlClass();
mySql9.setResourceName(sqlresourcename);
mySql9.setSqlId(sqlid9);
mySql9.addSubPara(cAgentCode);	
   
    var arrResult = easyExecSql(mySql9.getString());
       //alert(arrResult);
    if (arrResult != null) {
    
      alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else{
 
     alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
     }
	}
}