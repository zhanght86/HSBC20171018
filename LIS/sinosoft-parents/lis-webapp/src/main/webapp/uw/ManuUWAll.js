//�������ƣ�ManuUWAll.js
//�����ܣ������˹��˱�
//�������ڣ�2005-01-24 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
var mDebug="0";
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPagetest = new turnPageClass();
var k = 0;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

/*********************************************************************
 *  ִ������Լ�˹��˱���EasyQuery
 *  ����:��ѯ��ʾ�����Ǻ�ͬ����.��ʾ����:��ͬδ�����˹��˱�����״̬Ϊ���˱�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 
var withdrawFlag = false;
//���������ѯ,add by Minim
function withdrawQueryClick() {
  withdrawFlag = true;
  easyQueryClickAll();
}
function easyQueryClick()
{   
	//add by yaory 
	//2008-12-8 ln modify
	
	    var sqlid1="ManuUWAll1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(operator);//ָ������Ĳ���
	    var sql=mySql1.getString();	
	
//  var sql="select decode(SurpassGradeFlag,'',uwpopedom,SurpassGradeFlag) From LDUWUser where usercode='"+operator+"' and uwtype='1'";
	var Result = easyExecSql(sql);	
	// ��дSQL���
	var strSQL ="";
	var strSQLBase="";
	var strSQLOther="";
	var strSQLOrder="";
	k++;
	
		 var sqlid2="ManuUWAll2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(operator);//ָ������Ĳ���
	    tSql=mySql2.getString();	
	
//	tSql="select comcode From lduser where usercode='"+operator+"'";
	var com=easyExecSql(tSql);
	//alert(operator);
	if(operFlag == "1")
	{
		
	    strSQLBase = "select t.missionprop12,"
	    		+"(select insuredname from lccont where prtno = t.MissionProp1 and conttype='1'),"
				+ " (case t.missionprop11 when '0' then '����Լ�˱�' when '1' then '�ϱ�'  when '2' then '�������ϱ�' when '3' then '�ٱ��ϱ�' when '4' then '�����¼�' end) order1, "				
				+ "'�Ǽ�ҵ��Ա' order3,"
				+ " (case missionprop17 when 'NOSCAN' then '��ɨ���' else missionprop17 end) scandate," 
//				+ " ( case when missionprop18='1' then '' when missionprop18<>'1' then (missionprop8||' '||missionprop9) end) order4 ,"//�˱�״̬�ı�ʱ��
				+ " ( case when (select uwstate from lccuwmaster where contno= t.missionprop1)='1' then '' when (select uwstate from lccuwmaster where contno= t.missionprop1)<>'1' then (missionprop8||' '||missionprop9) end) order4 ,"//�˱�״̬�ı�ʱ��				
				+ "(select uwstate from lccuwmaster where contno= t.missionprop1),"
				+ "(case  when (length(t.missionprop10)>4) then substr(t.missionprop10,0,4) else t.missionprop10 end),"
				+ "(select riskcode from lcpol where polno=(select min(polno) from lcpol where prtno=t.MissionProp1 and insuredno=(select insuredno from lccont where prtno=t.MissionProp1  and conttype='1'))),"
		        + "t.missionprop1,"
		        + "t.lastoperator,"
		        + "case (select vipvalue from ldperson  where customerno = t.missionprop6) "
				+ " when '1' then '��' else 'no'"
				+ " end vipvalue,"
				+ "t.missionprop4,"
				+ "t.missionprop15,"
				+ "t.missionprop13,"
				+ "t.missionprop11,"
                + "( select to_date(max(enteraccdate)) from ljtempfee where otherno = t.missionprop2 )," 
				+ " t.missionid,t.submissionid,t.activityid, "
				+ " (select uwstate from lccuwmaster where contno= t.missionprop1),"
				+"'0',"
				+ "(select appntno from lccont where prtno = t.MissionProp1 and conttype='1')"
//				+  " ,round(calworktime(t.InDate,t.InTime,t.OutDate,t.OutTime) / 60 / 60,2), (case when t.TimeID is not null and t.StandEndDate is not null then (calworktime(t.InDate,t.InTime,t.StandEndDate,t.StandEndTime) / 60 / 60) else 0 end),(case when t.TimeID is not null and t.StandEndDate is not null then round(((calworktime(t.InDate,t.InTime,t.OutDate,t.OutTime)) / 60 / 60)/((calworktime(t.InDate,t.InTime,t.StandEndDate,t.StandEndTime)) / 60 / 60),2) else 0 end) "
        + " ,round(calworktime(t.InDate, t.InTime, t.StandEndDate, t.StandEndTime)/60/60,2), "
        + " (case when t.TimeID is not null and t.StandEndDate is not null then "
        + " (case when (to_char(sysdate,'yyyy-mm-dd') > t.StandEndDate or (to_char(sysdate,'yyyy-mm-dd')=t.StandEndDate and to_char(sysdate,'hh24:MM:dd')>t.StandEndTime))then 0 else round(calworktime(to_char(sysdate,'yyyy-mm-dd'),to_char(sysdate,'hh24:MM:SS'), t.StandEndDate, t.StandEndTime)/60/60,2) end) else 0 end), "
        + " (case when t.TimeID is not null and t.StandEndDate is not null then round((calworktime(to_char(sysdate,'yyyy-mm-dd'),to_char(sysdate,'hh24:MM:SS'), t.StandEndDate, t.StandEndTime)/60/60)/((calworktime(t.InDate,t.InTime,t.StandEndDate, t.StandEndTime))/60/60),2) else 0 end) ";

				
             strSQLOther =" from lwmission t where 1=1 "
					+ " and t.activityid in (select activityid from lwactivity  where functionid ='10010028')"
					+ " and not exists(select 1 from ES_DOC_MAIN where doccode = t.missionprop1 and subtype='UR201')" //2009-1-16�ų��ͻ����볷����Ͷ����
					+ " and (t.defaultoperator ='" + operator + "' or t.missionprop14='" + operator + "')"
					+ " and t.MissionProp10 like '"+com+"%%'" ;
					/*
					if(document.all('UWState2').value!=null&&document.all('UWState2').value!="")
					{
					    if(document.all('UWState2').value=="0")					    					    
						    alert("�˴��˱�״̬����Ϊ[0]");
					    
					    else 
					        strSQL = strSQL + " and t.missionprop18='"+document.all('UWState2').value+"' ";
						
					}*/
					if(document.all('UWState2').value!=null&&document.all('UWState2').value!="")
					{
					      strSQLOther = strSQLOther + " and exists (select 1 from lccuwmaster where contno= t.missionprop1 and uwstate ='"+document.all('UWState2').value+"') ";						
					}
					if(document.all('UWState2').value =="1"||document.all('UWState2').value =="2"||document.all('UWState2').value =="3")
					{		
						strSQLOrder = " order by order1 desc, vipvalue desc, order3 desc, order4 , scandate";						
					    
					}	
					else 
					{		
						strSQLOrder = " order by order4 desc";					
					    
					}
					//+ " order by Vipvalue desc,VIPProperty desc";
//					+ " order by decode(t.activitystatus,'5',1,'3',2,'1',3,'4',4,'2',5,6)";
        //prompt("",strSQL);
                    strSQL = strSQLBase + strSQLOther + strSQLOrder;
                    
                    //��ѯ�������������ܹ��ж��ٵ��� 
                  //  var sumsql = "select count(*) "+ strSQLOther;
				  // var sum = easyExecSql(sumsql);
				  // document.all('PolSum').value = sum; 
	}
		
	if(operFlag == "2")
	{
		
		var sqlid3="ManuUWAll3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(operator);//ָ������Ĳ���
		mySql3.addSubPara(operator);//ָ������Ĳ���
	    strSQL=mySql3.getString();	
		
//		 strSQL = "select t.missionprop1,t.missionprop2,t.missionprop7,(select codename from ldcode where codetype='uwstatus' and code=t.activitystatus),case t.activityid when '0000002004' then '����Լ' end,"
//                    + " t.missionid,t.submissionid,t.activityid ,"
//                    + " case t.missionprop11 when '0' then '����Լ�˱�' when '1' then '�ϱ�'  when '3' then '�ٱ��ϱ�' when '4' then '�����¼�' end, "
//                    + " t.missionprop12,t.missionprop4,t.missionprop5,t.missionprop15,t.missionprop13,"
//                    + " (select VIPProperty from latree where agentcode = t.missionprop4) VIPProperty ,"
//                    + " (select vipvalue from ldperson  where customerno = t.missionprop6) Vipvalue,t.missionprop11 ,"
//                    +" ( select to_date(max(enteraccdate)) from ljtempfee where  othernotype='4' and otherno = rpad(t.missionprop2,lislen('ljtempfee','otherno') ) ) "
//                    +" from lwmission t where 1=1 "
//					+ " and t.activityid in ('0000002004')"
//					+ " and (t.defaultoperator ='" + operator + "' or t.missionprop14='" + operator + "')"
//					+ " order by t.modifydate asc,t.modifytime asc";
				
				
	}
	if(operFlag == "3")
	{
	
			var sqlid4="ManuUWAll4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(operator);//ָ������Ĳ���
	    strSQL=mySql4.getString();	
	
//		 strSQL = "select t.missionprop2,t.missionprop1,t.missionprop6,(select codename from ldcode where codetype='uwstatus' and code=t.activitystatus),case t.activityid when '0000006004' then '�˱�����' end,t.missionid,t.submissionid,t.activityid,"
//		 +" case t.missionprop11 when '0' then '����Լ�˱�' when '1' then '�ϱ�'  when '2' then '��Ȩ���ϱ�' when '3' then '�ٱ��ϱ�' when '4' then '�����¼�' end, "
//		 +" t.missionprop12,t.missionprop4,t.missionprop3,"
//		            + "(select polapplydate from lcgrpcont where (lcgrpcont.grpcontno) = rpad(t.missionprop1,lislen('lcgrpcont','grpcontno'),' ')) poldate,"
//		            + "(select approvedate from lcgrpcont where (lcgrpcont.grpcontno) = rpad(t.missionprop1,lislen('lcgrpcont','grpcontno'),' ')) approvedate,"
//		            +" ( select to_date(max(enteraccdate)) from ljtempfee where  othernotype='4' and otherno = rpad(t.missionprop2,lislen('ljtempfee','otherno') ) ) "
//		            +" from lwmission t where 1=1 " 
//					+ " and t.activityid in ('0000006004')"
//					+ " and t.defaultoperator ='" + operator + "'"
//					+ " order by t.modifydate asc,t.modifytime asc";				
				
				
	}	
  turnPage2.queryModal(strSQL, PolGrid);  
  changeColor(bg1);
	//alert('1');
}


function NeweasyQueryClick()
{
	var strOperate="like";	
	//2008-12-8 ln modify
	
		var sqlid5="ManuUWAll5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(operator);//ָ������Ĳ���
	    var sql=mySql5.getString();	
	
//	var sql="select decode(SurpassGradeFlag,'',uwpopedom,SurpassGradeFlag) From LDUWUser where usercode='"+operator+"' and uwtype='1'";
	var Result = easyExecSql(sql);
	if(Result==null)
	{
			alert("�ú˱�ԭԱ"+operator+"û�ж���˱�����");
			return;
	}
	
		var sqlid6="ManuUWAll6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(operator);//ָ������Ĳ���
	    sql=mySql6.getString();	
	
//	sql="select comcode From lduser where usercode='"+operator+"'";
	var com=easyExecSql(sql);
	// ��дSQL���
	var strSQL ="";
	var tSql = "";
	k++;
//	if(operFlag == "1")
//	{
//		//tongmeng 2007-09-12 modify
//		//����vipҵ��Ա
//		 strSQL = "select t.missionprop12,"
//				+ "(select insuredname from lccont where prtno = t.MissionProp1 and conttype='1'),"
//				+ " (case t.missionprop11 when '0' then '����Լ�˱�' when '1' then '�ϱ�'  when '2' then '�������ϱ�' when '3' then '�ٱ��ϱ�' when '4' then '�����¼�' end) order1, "				
//				+ "'�Ǽ�ҵ��Ա' order3,"
//     //           + " (select vipvalue from ldperson  where customerno = t.missionprop6) Vipvalue "				
//				+ " decode(missionprop17,'NOSCAN','��ɨ���',missionprop17) scandate," 
//			  + " ( case when missionprop18='1' then '' when (missionprop18<>'1') then (missionprop8||' '||missionprop9) end) order4 ,"//�˱�״̬�ı�ʱ��
//
//				//+ "(select code from ldcode where codetype='uwstatus' and trim(code)=missionprop18),"
//				+ "t.missionprop18,"
//				+ "(case  when (length(t.missionprop10)>4) then substr(t.missionprop10,0,4) else t.missionprop10 end),"
//				+ "(select riskcode from lcpol where polno=(select min(polno) from lcpol where prtno=t.MissionProp1 and insuredno=(select insuredno from lccont where prtno=t.MissionProp1  and conttype='1'))) riskcodequery,"
//		        + "t.missionprop1,"
//		        + "t.missionprop20,"
//		        + "case (select vipvalue from ldperson  where customerno = t.missionprop6) "
//				+ " when '1' then '��' else ''"
//				+ " end vipvalue,"
//				+ "t.missionprop4,"
//				+ "t.missionprop15,"
//				+ "t.missionprop13,"
//				+ "t.missionprop11,"
//                + "( select to_date(max(enteraccdate)) from ljtempfee where otherno = t.missionprop2 )," 
//				+ " t.missionid,t.submissionid,t.activityid ,"
//				+ " t.MissionProp18, "
//				+ " (select min(lccont.firsttrialdate) from lccont where prtno = t.missionprop2) order5   "   
//		//        + "t.missionprop7,"
//		//		+ " to_char(t.modifydate,'yyyy-mm-dd')||' '||t.modifytime,"
////		        + " (select VIPProperty from latree where  (latree.agentcode) = rpad(t.missionprop4,lislen('latree','agentcode'),' ')) VIPProperty,"
//				//+ "(select codename from ldcode where codetype='uwstatus' and trim(code)=missionprop18),"
//		//        + "t.missionprop2,"
//				//+ " (select VIPProperty from latree where agentcode = t.missionprop4) VIPProperty ,"
//			  + ",''"
//			  //+  " ,round(calworktime(t.InDate,t.InTime,t.OutDate,t.OutTime) / 60 / 60,2), (case when t.TimeID is not null and t.StandEndDate is not null then (calworktime(t.InDate,t.InTime,t.StandEndDate,t.StandEndTime) / 60 / 60) else 0 end),(case when t.TimeID is not null and t.StandEndDate is not null then round(((calworktime(t.InDate,t.InTime,t.OutDate,t.OutTime)) / 60 / 60)/((calworktime(t.InDate,t.InTime,t.StandEndDate,t.StandEndTime)) / 60 / 60),2) else 0 end) "
//        + " ,round(calworktime(t.InDate, t.InTime, t.StandEndDate, t.StandEndTime)/60/60,2), "
//        + " (case when t.TimeID is not null and t.StandEndDate is not null then "
//        + " (case when (to_char(sysdate,'yyyy-mm-dd') > t.StandEndDate or (to_char(sysdate,'yyyy-mm-dd')=t.StandEndDate and to_char(sysdate,'hh24:MM:dd')>t.StandEndTime))then 0 else round(calworktime(to_char(sysdate,'yyyy-mm-dd'),to_char(sysdate,'hh24:MM:SS'), t.StandEndDate, t.StandEndTime)/60/60,2) end) else 0 end), "
//        + " (case when t.TimeID is not null and t.StandEndDate is not null then round((calworktime(to_char(sysdate,'yyyy-mm-dd'),to_char(sysdate,'hh24:MM:SS'), t.StandEndDate, t.StandEndTime)/60/60)/((calworktime(t.InDate,t.InTime,t.StandEndDate, t.StandEndTime))/60/60),2) else 0 end) "
//
//                + " from lwmission t where 1=1 "
//				+ " and t.activityid in ('0000001100')"
//				+ " and not exists(select 1 from ES_DOC_MAIN where doccode = t.missionprop1 and subtype='UR201')"  //2009-1-16�ų��ͻ����볷����Ͷ����
//				+ " and (t.defaultoperator ='" + operator + "' or t.missionprop14='" + operator + "')"
//				+ getWherePart('t.MissionProp2','ContNo1',strOperate)
//			    + getWherePart('t.MissionProp4','AgentCode1',strOperate)			   
//			    + getWherePart('t.MissionProp7','AppntName1',strOperate)
//			 //   + getWherePart('t.MissionProp13','ApproveDate1',strOperate)
//			    //+ getWherePart('t.activitystatus','UWState1',"=")
//			    + getWherePart('t.missionprop11','UWUpReport',"=")
//                + getWherePart('t.MissionProp10','ManageCom1',strOperate)
//          		+ " and t.MissionProp10 like '"+com+"%%'" ;
//			//���Ӱ��Ż�����ѯ���� hanbin 2010-06-12
//		  	if(document.all('BaiTuanCom1').value!=null&&document.all('BaiTuanCom1').value== "1")
//		  	{
//		  		strSQL = strSQL + " and exists(select 1 from ldcom where comcode = t.MissionProp10 and comareatype1 = '1') ";
//		  	}
//		  	if(document.all('BaiTuanCom1').value!=null&&document.all('BaiTuanCom1').value== "0")
//		  	{
//		  		strSQL = strSQL + " and exists(select 1 from ldcom where comcode = t.MissionProp10 and comareatype1 = '0' or comareatype1 is null) ";
//		  	}
//			 if(document.all("SaleChnl1").value!="")
//			 {
//			 	strSQL = strSQL 								
//			 	       + " and exists(select 'x' from lccont where lccont.contno = t.missionprop2 and lccont.salechnl='"+document.all("SaleChnl1").value+"')";
//			 }
//			 if(document.all("InsurName1").value!="")
//			 {                                                                                                                                 
//				strSQL = strSQL                                                                                                                                                    
//				 			 + " and exists(select 'x' from lccont where contno = t.MissionProp2 and insuredname='"+document.all("InsurName1").value+"')"; 	                                                                                                                                                                   
//			 }
//		     if(fm.EnteraccState1.value!="")
//			{
//			   if(fm.EnteraccState1.value=="1")
//			   {
//				strSQL = strSQL 								
//			 	       + " and exists(select 'x' from ljtempfee x where x.otherno = t.missionprop2 and x.enteraccdate is not null) ";
//			    }
//			    else if(fm.EnteraccState1.value=="0")
//			   {
//				strSQL = strSQL 								
//			 	       + " and not exists(select 'x' from ljtempfee x where x.otherno = t.missionprop2 and x.enteraccdate is not null) ";
//			    }
//			}   
//			if(fm.UWLevel1.value!="")
//			{
//				strSQL = strSQL 
//			    	+ " and exists(select 'x' from latree where agentcode = t.MissionProp4 and UWLevel = '"+fm.UWLevel1.value+"')";
//			}  	
//			 if(fm.OperatorState1.value!="")
//			{
//			   if(fm.OperatorState1.value=="1")
//			   {
//				strSQL = strSQL 								
//			 	       + " and t.MissionProp4 like '%%999999'";
//			    }
//			    else if(fm.OperatorState1.value=="0")
//			   {
//				strSQL = strSQL 								
//			 	       + " and t.MissionProp4 not like '%%999999'";
//			    }
//			}   
//			
//					if(document.all('UWState2').value!=null&&document.all('UWState2').value!="")
//					{
//					    /*if(document.all('UWState2').value=="0")					    					    
//						    alert("�˴��˱�״̬����Ϊ[0]");
//					    
//					    else */
//					        strSQL = strSQL + " and t.missionprop18='"+document.all('UWState2').value+"' ";
//						
//					}  
//		    if(document.all('RiskCode1').value !="")
//		    {
//		       strSQL = strSQL+" and exists(select 1 from lcpol where polno = (select min(polno) from lcpol where prtno=t.MissionProp1 and insuredno=(select insuredno from lccont where prtno=t.MissionProp1  and conttype='1')) and riskcode ='"+document.all('RiskCode1').value+"')";
//		    } 
//		    if(document.all('UWState2').value =="3")
//			{		
//				strSQL = strSQL+" order by order5 ,(select comareatype1 from ldcom where comcode = t.MissionProp10 ) asc, scandate ";						
//			    
//			}else if(document.all('UWState2').value =="1"||document.all('UWState2').value =="2")
//			{
//			    strSQL = strSQL+" order by order5 ,(select comareatype1 from ldcom where comcode = t.MissionProp10 ) asc, scandate  ";		
//			}	
//			else 
//			{		
//				strSQL = strSQL+ " order by order4 ";					
//			    
//			}
//			
//	 }
    if(operFlag == "2")
	{   
	
		var sqlid7="ManuUWAll7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(operator);//ָ������Ĳ���
		mySql7.addSubPara(operator);//ָ������Ĳ���
		mySql7.addSubPara(fm.ContNo1.value);//ָ������Ĳ���
        mySql7.addSubPara(fm.ApproveDate1.value);//ָ������Ĳ���
        mySql7.addSubPara(fm.AgentCode1.value);//ָ������Ĳ���
        
		mySql7.addSubPara(fm.UWState1.value);//ָ������Ĳ���
		mySql7.addSubPara(fm.UWUpReport.value);//ָ������Ĳ���
		mySql7.addSubPara(fm.ManageCom1.value);//ָ������Ĳ���
		
		mySql7.addSubPara(fm.SaleChnl1.value);//ָ������Ĳ���
		mySql7.addSubPara(fm.AppntName1.value);//ָ������Ĳ���
		
	    strSQL=mySql7.getString();	
	
//		 strSQL = "select t.missionprop1,t.missionprop2,t.missionprop7,(select codename from ldcode where codetype='uwstatus' and code=t.activitystatus),case t.activityid when '0000002004' then '����Լ' end,"
//                    + " t.missionid,t.submissionid,t.activityid,"
//                    + " case t.missionprop11 when '0' then '����Լ�˱�' when '1' then '�ϱ�'  when '2' then '��Ȩ���ϱ�' when '3' then '�ٱ��ϱ�' when '4' then '�����¼�' end, "
//                    + " t.missionprop12,t.missionprop4,t.missionprop5,t.missionprop15,t.missionprop13,"
//                    + " (select VIPProperty from latree where agentcode = t.missionprop4) VIPProperty ,"
//                    + " (select vipvalue from ldperson  where customerno = t.missionprop6) Vipvalue,t.missionprop11 ,"
//                    + " ( select to_date(max(enteraccdate)) from ljtempfee where  othernotype='4' and otherno = rpad(t.missionprop2,lislen('ljtempfee','otherno') ) ) "
//                    + " from lwmission t where 1=1 "
//					+ " and t.activityid in ('0000002004')"
//					+ " and (t.defaultoperator ='" + operator + "' or t.missionprop14='" + operator + "')"
//					+getWherePart('t.MissionProp2','ContNo1',strOperate)
//				  +getWherePart('t.MissionProp13','ApproveDate1',strOperate)
//				  +getWherePart('t.MissionProp5','AgentCode1',strOperate)
//				  +getWherePart('t.activitystatus','UWState1',strOperate)
//				  +getWherePart('t.missionprop11','UWUpReport',strOperate)
//				  +getWherePart('MissionProp4','ManageCom1',strOperate)
//				  +getWherePart('MissionProp3','SaleChnl1',strOperate)
//				  +getWherePart('MissionProp7','AppntName1',strOperate)
				  if(fm.InsurName1.value!=""&&fm.InsurName1.value!=null)
				   
				  	{ 
				  		var sql="select prtno from lccont where insuredname='"+fm.InsurName1.value+"'";
				  	   var arrResult = easyExecSql("select prtno from lccont where insuredname='"+fm.InsurName1.value+"'",1,0);
              var b;
              strSQL=strSQL+" and missionprop2 in (" 
              for( b=0;b<arrResult.length-1;b++)
                  {   
                  	  strSQL=strSQL+"'"+trim(arrResult[b][0])+"'," 
                  }
                 strSQL=strSQL+ "'"+arrResult[arrResult.length-1][0]+"') " 
				    }
				   if(fm.EnteraccDate1.value!="")
				   {
				     	strSQL = strSQL 								
			 	       + " and exists(select 'x' from ljtempfee x where x.othernotype='4' and x.otherno = rpad(t.missionprop2,lislen('ljtempfee','otherno') ) and x.enteraccdate='"+document.all("EnteraccDate1").value+"') "
				   }
				 strSQL=strSQL+ " order by t.modifydate asc,t.modifytime asc"; 
	
	if(operFlag == "3")
	{   	 
	
	    var sqlid8="ManuUWAll8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(operator);//ָ������Ĳ���
		mySql8.addSubPara(fm.ContNo1.value);//ָ������Ĳ���
        mySql8.addSubPara(fm.ApproveDate1.value);//ָ������Ĳ���
        mySql8.addSubPara(fm.AgentCode1.value);//ָ������Ĳ���
        
		mySql8.addSubPara(fm.UWState1.value);//ָ������Ĳ���
		mySql8.addSubPara(fm.ManageCom1.value);//ָ������Ĳ���
		mySql8.addSubPara(fm.AppntName1.value);//ָ������Ĳ���
		
	    strSQL=mySql8.getString();	
	
//		 strSQL = "select t.missionprop2,t.missionprop1,t.missionprop6,"
//		            + "(select codename from ldcode where codetype='uwstatus' and code=t.activitystatus),"
//		            + "case t.activityid when '0000006004' then '�˱�����' end,"
//                    + " t.missionid,t.submissionid,t.activityid,"
//                    +" case t.missionprop11 when '0' then '����Լ�˱�' when '1' then '�ϱ�'  when '2' then '��Ȩ���ϱ�' when '3' then '�ٱ��ϱ�' when '4' then '�����¼�' end, "
//                    +" t.missionprop12,t.missionprop4,t.missionprop3,"
//                    + "(select polapplydate from lcgrpcont where (lcgrpcont.grpcontno) = rpad(t.missionprop1,lislen('lcgrpcont','grpcontno'),' ')) poldate,"
//                    + "(select approvedate from lcgrpcont where (lcgrpcont.grpcontno) = rpad(t.missionprop1,lislen('lcgrpcont','grpcontno'),' ')) approvedate,"
//                    +"( select to_date(max(enteraccdate)) from ljtempfee where  othernotype='4' and otherno = rpad(t.missionprop2,lislen('ljtempfee','otherno') ) )"
//                    + " from lwmission t where 1=1 "
//					+ " and t.activityid in ('0000006004')"			
//					+ " and (t.defaultoperator ='" + operator + "')"
//					+ getWherePart('t.missionprop1','ContNo1',strOperate)
//				  + getWherePart('t.MissionProp3','AgentCode1',strOperate)
//				  + getWherePart('t.activitystatus','UWState1',strOperate)
//				  + getWherePart('t.MissionProp4','ManageCom1',strOperate)
//				  + getWherePart('t.MissionProp6','AppntName1',strOperate)
				  if(fm.InsurName1.value!="")
				  	{ 
				  		var sql="select prtno from lccont where insuredname='"+fm.InsurName1.value+"'";
				  	    var arrResult = easyExecSql("select prtno from lccont where insuredname='"+fm.InsurName1.value+"'",1,0);
                        var b;
                        strSQL=strSQL+" and missionprop1 in ("
              for( b=0;b<arrResult.length-1;b++)
                  {   
                  	  
                  	  strSQL=strSQL+"'"+trim(arrResult[b][0])+"',"
                  }
                 strSQL=strSQL+ "'"+arrResult[arrResult.length-1][0]+"') "
				    }
				  if(fm.EnteraccDate1.value!="")
				   {
				     	strSQL = strSQL 								
			 	       + " and exists(select 'x' from ljtempfee x where x.othernotype='4' and x.otherno = rpad(t.missionprop2,lislen('ljtempfee','otherno') ) and x.enteraccdate='"+document.all("EnteraccDate1").value+"') "
				   }
				    strSQL=strSQL+ " order by t.modifydate asc,t.modifytime asc";
	}
  }
//  prompt("",strSQL);
	 turnPage2.queryModal(strSQL, PolGrid);
	  changeColor(bg1);
		
}
function easyQueryClickAll()
{
	//alert(operFlag);
	// ��дSQL���
	var strSQL ="";
	var strSQLBase ="";
	var strSQLOther ="";
	var strSQLOrder="";
	
	var strOperate="like";
	//liuning 2008-06-10 modify
	var UWState1 = document.all('UWState1').value;
	//var UWCode = document.all('UWCode').value;
	if (UWState1 == "")
	{
			alert("��ѡ��˱�����״̬��");
			return;
	}
	/*if (UWCode != operator && UWCode !="")
	{			
			alert("��������ȷ�ĺ˱�Ա���룡");
			return;
	}*/
   
	//alert(operFlag+withdrawFlag);
	//tongmeng 2007-10-22 modify
	//���Ӻ˱�״̬��ѯ
	//��mulline�ķ�װ�ļ���ѯ�����ڴ˴���ѯ 2013-04-22 lzf
//	if(operFlag == "1")
//	{
//	    //SurpassGradeFlag��Ȩ�޼���UWProcessFlag�Ƿ����м�˱�Ȩ�� 2008-12-8 ln modify
//		var sql="select decode(SurpassGradeFlag,'',uwpopedom,SurpassGradeFlag) from lduwuser where usercode='"+operator+"' and uwtype='1'";
//		var Result = easyExecSql(sql);
//		if(Result==null)
//		{
//			alert("�ú˱�Ա"+operator+"û�ж���˱�����");
//			return;
//		}
//		sql="select comcode from lduser where usercode='"+operator+"'";
//		var com=easyExecSql(sql);
//		//tongmeng 2007-09-12 modify
//		//���ε�vip��־
//		strSQLBase = "select t.missionprop12,"
//		        + "decode(t.missionprop20,'',t.lastoperator,t.missionprop20),"
//		        + "case (select vipvalue from ldperson  where customerno = t.missionprop6) "
//				+ " when '1' then '��' else ''"
//				+ " end vipvalue,"
//				+ " '�Ǽ�ҵ��Ա' order3, "//����
////		        + " (select VIPProperty from latree where  (latree.agentcode) = rpad(t.missionprop4,lislen('latree','agentcode'),' ')) VIPProperty,"               
//				//+ " to_char(t.modifydate,'yyyy-mm-dd')||' '||t.modifytime,"
//				+ " decode(t.missionprop17,'NOSCAN','��ɨ���',missionprop17) scandate," 
//				+ " ( case when missionprop18='1' then '' when missionprop18<>'1' then (missionprop8||' '||missionprop9) end) order4 ,"//�˱�״̬�ı�ʱ��
//				//+ "(select codename from ldcode where codetype='uwstatus' and trim(code)=missionprop18),"
//				+ "t.missionprop18,"//�˱�״̬
//				+ "(case  when (length(t.missionprop10)>4) then substr(t.missionprop10,0,4) else t.missionprop10 end),"
//				+ "(select riskcode from lcpol where polno=(select min(polno) from lcpol where prtno=t.MissionProp1 and insuredno=(select insuredno from lccont where prtno=t.MissionProp1 and conttype='1'))),"
//		        + "t.missionprop1," //t.missionprop2,t.missionprop7,
//				+ "(select insuredname from lccont where prtno =t.MissionProp1 and conttype='1'),"
//				+ "(case t.missionprop11 when '0' then '����Լ�˱�' when '1' then '�ϱ�'  when '2' then '�������ϱ�' when '3' then '�ٱ��ϱ�' when '4' then '�����¼�' end) order1, "				
//				+ " t.missionprop4,t.missionprop15,t.missionprop13,t.missionprop11 ,"
//				//+ " (select VIPProperty from latree where agentcode = t.missionprop4) VIPProperty ,"
//                //+ " (select vipvalue from ldperson  where customerno = t.missionprop6) Vipvalue "
//                + "( select to_date(max(enteraccdate)) from ljtempfee where otherno = t.missionprop2 ),"       
//				+ " t.missionid,t.submissionid,t.activityid, "
//                + " (select lccont.firsttrialdate from lccont where prtno = t.missionprop2) order5   "   
//        //+  " ,round(calworktime(t.InDate,t.InTime,t.StandEndDate,t.StandEndTime) / 60 / 60,2), (case when t.TimeID is not null and t.StandEndDate is not null then (calworktime(t.InDate,t.InTime,to_char(sysdate,'yyyy-mm-dd'),to_char(sysdate,'hh24:MM:ss')) / 60 / 60) else 0 end),(case when t.TimeID is not null and t.StandEndDate is not null then round(((calworktime(t.InDate,t.InTime,t.OutDate,t.OutTime)) / 60 / 60)/((calworktime(t.InDate,t.InTime,t.StandEndDate,t.StandEndTime)) / 60 / 60),2) else 0 end) "                  
//        + " ,round(calworktime(t.InDate, t.InTime, t.StandEndDate, t.StandEndTime)/60/60,2), "
//        + " (case when t.TimeID is not null and t.StandEndDate is not null then "
//        + " (case when (to_char(sysdate,'yyyy-mm-dd') > t.StandEndDate or (to_char(sysdate,'yyyy-mm-dd')=t.StandEndDate and to_char(sysdate,'hh24:MM:dd')>t.StandEndTime))then 0 else round(calworktime(to_char(sysdate,'yyyy-mm-dd'),to_char(sysdate,'hh24:MM:SS'), t.StandEndDate, t.StandEndTime)/60/60,2) end) else 0 end), "
//        + " (case when t.TimeID is not null and t.StandEndDate is not null then round((calworktime(to_char(sysdate,'yyyy-mm-dd'),to_char(sysdate,'hh24:MM:SS'), t.StandEndDate, t.StandEndTime)/60/60)/((calworktime(t.InDate,t.InTime,t.StandEndDate, t.StandEndTime))/60/60),2) else 0 end) "
//
//       strSQLOther = " from lwmission t where 1=1 "
//				+ " and t.activityid in ('0000001100')"
//				+ " and not exists(select 1 from ES_DOC_MAIN where doccode = t.missionprop1 and subtype='UR201')"  //2009-1-16�ų��ͻ����볷����Ͷ����
//				+ " and t.defaultoperator is null "
//				//+ " and t.missionprop18='"+UWState1+"'"
//			  if(fm.VIPCustomer.value == "0")					
//		      {
//			      strSQLOther = 	strSQLOther	+ " and  exists(select 1 from ldperson where trim(customerno)=trim(t.missionprop6) and (vipvalue = '0' or vipvalue is null))";
//		      }
//		      if(fm.VIPCustomer.value == "1")
//		      {
//			      strSQLOther = 	strSQLOther	+ " and exists(select 1 from ldperson where trim(customerno)=trim(t.missionprop6) and vipvalue = '1')";
//		      }
//			  if(fm.StarAgent.value == "0")			
//			  {
//				  strSQLOther = strSQLOther+ " and exists(select 1 from latree where trim(agentcode)=trim(t.missionprop4) and (vipproperty = '0' or vipproperty is null ))"
//			  }
//			  if(fm.StarAgent.value == "1")			
//			  {
//				  strSQLOther = strSQLOther+ " and exists(select 1 from latree where trim(agentcode)=trim(t.missionprop4) and vipproperty = '1')"
//			  }
//			  //���Ӱ��Ż�����ѯ���� hanbin 2010-06-12
//       		  if(document.all('BaiTuanCom').value!=null&&document.all('BaiTuanCom').value== "1")
//       		  {
//       		  	  strSQLOther = strSQLOther + " and exists(select 1 from ldcom where comcode = t.MissionProp10 and comareatype1 = '1') ";
//       		  }
//       		  if(document.all('BaiTuanCom').value!=null&&document.all('BaiTuanCom').value== "0")
//       		  {
//       		  	  strSQLOther = strSQLOther + " and exists(select 1 from ldcom where comcode = t.MissionProp10 and comareatype1 = '0' or comareatype1 is null) ";
//       		  }
//       		
//			  if(withdrawFlag)
//				{
//					 strSQLOther = strSQLOther+ " and exists (select 1 from LCApplyRecallPol where trim(prtno)=t.missionprop2)";
//				}
//			  if(document.all('RiskCode').value !="")
//			  {
//			        strSQLOther = strSQLOther+" and exists(select 1 from lcpol where polno = (select min(polno) from lcpol where prtno=t.MissionProp1 and insuredno=(select insuredno from lccont where prtno=t.MissionProp1  and conttype='1')) and riskcode ='"+fm.RiskCode.value+"')";
//			  }
//			  if(document.all('ScanDate').value !="")
//		      {
//			        strSQLOther = strSQLOther+" and exists (select 1 from ES_DOC_MAIN where doccode = t.missionprop1 and subtype='UA001' and makedate <='"+document.all('ScanDate').value+"') ";
//			  }
//	//		  else
//	//		  {
////			       timesql = " select substr(sysdate,0,10) from dual ";
////			       var now = easyExecSql(timesql);
////			       strSQL = strSQL+" and MissionProp17 <= '"+now+"' and MissionProp17<>'NOSCAN'";
////			  }
//			   strSQLOther = strSQLOther  
//				   + getWherePart('t.MissionProp2','ContNo',strOperate)
//				   + getWherePart('t.MissionProp4','AgentCode',strOperate)			 
//				   + getWherePart('t.MissionProp7','AppntName2',strOperate)
//				   + getWherePart('t.MissionProp12','UWAuthority',strOperate)
//				   + getWherePart('t.MissionProp10','ManageCom',strOperate)
//				   //+ getWherePart('t.MissionProp11','UWUpReportType',"=")
//				   + getWherePart('t.MissionProp20','UserCode')
//				   //+ getWherePart('t.activitystatus','UWState2',"=")
//				   + " and t.MissionProp10 like '"+com+"%%'"
//				   + " and t.MissionProp12<='"+Result+"'"  
//				   + " and (t.MissionProp14 is null or t.MissionProp14='0000000000')"; 
//             
// 			 if(document.all('SaleChnl').value!="")
// 			 {
//			 	strSQLOther = strSQLOther 
//			 	       + " and exists(select 'x' from lccont where contno = t.MissionProp2 and lccont.salechnl='"+document.all('SaleChnl').value+"')";
//			 }
//			 if(document.all('InsurName2').value!="")
//			 {
//			   //alert(document.all('InsurName2').value);
//			 	strSQLOther = strSQLOther
//			 				 + " and exists(select 'x' from lccont where contno = t.MissionProp2 and insuredname='"+document.all('InsurName2').value+"')"; 
//			 }
//            if(fm.EnteraccState2.value!="")
//			{
//			   if(fm.EnteraccState2.value=="1")
//			   {
//				strSQLOther = strSQLOther 								
//			 	       + " and exists(select 'x' from ljtempfee x where x.otherno = t.missionprop2 and x.enteraccdate is not null) ";
//			    }
//			    else if(fm.EnteraccState2.value=="0")
//			   {
//				strSQLOther = strSQLOther 								
//			 	       + " and not exists(select 'x' from ljtempfee x where x.otherno = t.missionprop2 and x.enteraccdate is not null) ";
//			    }
//			}   
//			 if(fm.OperatorState2.value!="")
//			{
//			   if(fm.OperatorState2.value=="1")
//			   {
//				strSQLOther = strSQLOther 								
//			 	       + " and t.MissionProp4 like '%%999999'";
//			    }
//			    else if(fm.OperatorState2.value=="0")
//			   {
//				strSQLOther = strSQLOther 								
//			 	       + " and t.MissionProp4 not like '%%999999'";
//			    }
//			}  
//			
//			 if(fm.UWLevel.value!="")
//			{
//			strSQLOther = strSQLOther 	
//			    + " and exists(select 'x' from latree where agentcode = t.MissionProp4 and UWLevel = '"+fm.UWLevel.value+"')";
//			}  			
//		//    if(document.all('UWState2').value!=null&&document.all('UWState2').value!="")
//		//			{
//		//			    if(document.all('UWState2').value=="0")					    					    
//		//				    strSQL = strSQL + " and t.missionprop18 in ('1','2','3') ";
//		//			    
//		//			    else 
//		//			        strSQL = strSQL + " and t.missionprop18='"+document.all('UWState2').value+"' ";
//						
//		//			}
////			if(fm.ScanDate.value!="")
////			{
////				     	strSQL = strSQL 								
////			 	       + " and ((t.missionprop17<="+document.all('ScanDate').value+"' and t.missionprop17<>'NOSCAN' ) or t.missionprop17='NOSCAN')";
////		}  
////			if(fm.UWCode.value!="")
////			{
////				     	strSQL = strSQL 								
////			 	       + " and ((t.missionprop17<="+document.all('InputDate').value+"' and t.missionprop17<>'NOSCAN' ) or t.missionprop17='NOSCAN')";
////			} 
//            if(UWState1 =="0")
//			{
//				strSQLOther = strSQLOther + " and t.missionprop18 in ('1','2','3') ";
//				strSQLOrder = " order by order5 ,(select comareatype1 from ldcom where comcode = t.MissionProp10 ) asc, scandate ";						
//			}
//			else if(UWState1 =="3")
//			{
//			    strSQLOther = strSQLOther + " and t.missionprop18 ='"+ UWState1 +"' ";			
//				strSQLOrder = " order by order5 ,(select comareatype1 from ldcom where comcode = t.MissionProp10 ) asc, scandate ";						
//			    
//			}else if(UWState1 =="1"||UWState1 =="2")
//			{
//			    strSQLOther = strSQLOther + " and t.missionprop18 ='"+ UWState1 +"' ";			
//				strSQLOrder = " order by order5 ,(select comareatype1 from ldcom where comcode = t.MissionProp10 ) asc, scandate  ";						
//			    
//			}
//			else 
//			{
//			    strSQLOther = strSQLOther + " and t.missionprop18 ='"+ UWState1 +"' ";			
//				strSQLOrder = " order by order4 ";					
//			    
//			}
//			
//			strSQL = strSQLBase + strSQLOther + strSQLOrder;			
////			prompt('',strSQL);
//			
//			//��ѯ�����������ܹ��ж��ٵ���
//		   var sumsql = "select count(*) from lwmission t where 1=1 "
//				+ " and t.activityid in ('0000001100')"
//				+ " and not exists(select 1 from ES_DOC_MAIN where doccode = t.missionprop1 and subtype='UR201')"  //2009-1-16�ų��ͻ����볷����Ͷ����
//				+ " and t.defaultoperator is null "
//				+ " and t.missionprop18 in ('1','2','3') "
//				+ " and t.MissionProp10 like '"+com+"%%'"
//				+ " and t.MissionProp12<='"+Result+"'"  
//				+ " and (t.MissionProp14 is null or t.MissionProp14='0000000000')";
//		   var sum = easyExecSql(sumsql);
//		   if(sum!=null && sum!="")
//		       document.all('PolSum').value = sum;
//		   else
//		       document.all('PolSum').value = "0";
//	}
		
	if(operFlag == "2")
	{
		//var sql="select uwpopedom From LDUWUser where usercode='"+operator+"' and uwtype='2'";
		    var  sqlid9="ManuUWAll9";
	 	 	var  mySql9=new SqlClass();
	 	 	mySql9.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
	 	 	mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	 	 	mySql9.addSubPara(usercode);//ָ������Ĳ���
	 	    var sql=mySql9.getString();
		var Result = easyExecSql(sql);
		if(Result==null)
		{
			alert("�ú˱�ԭԱ"+operator+"û�ж���˱�����");
			return;
		}
		sql="select comcode From lduser where usercode='"+operator+"'";
		    var  sqlid10="ManuUWAll10";
	 	 	var  mySql10=new SqlClass();
	 	 	mySql10.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
	 	 	mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
	 	 	mySql10.addSubPara(usercode);//ָ������Ĳ���
	 	    sql=mySql10.getString();
		var com=easyExecSql(sql);
		document.all('ManageCom').value = com;
		//��ѯ�жϸú˱������Ƿ�û����˱�Ա����ߺ˱�����
		//ismaxuw����null�Ļ�����˵���ú˱�Ա�������ڻ��������Ȩ��
		sql="select * from dual where ( select max(uwpopedom) from lduwuser "
		   +" where uwtype='2' and usercode in"
		   +" (select usercode from lduser where comcode like '"+com+"%%'))>'"+Result+"'";
		var ismaxuw=easyExecSql(sql);
		
	
		strSQL = "select t.missionprop1,t.missionprop2,t.missionprop7,(select codename from ldcode where codetype='uwstatus' and code=t.activitystatus),case t.activityid when '0000002004' then '����Լ' end,"
          + " t.missionid,t.submissionid,t.activityid ,"
          + " case t.missionprop11 when '0' then '����Լ�˱�' when '1' then '�ϱ�'  when '2' then '��Ȩ���ϱ�' when '3' then '�ٱ��ϱ�' when '4' then '�����¼�' end, "
          + " t.missionprop12,t.missionprop4,t.missionprop5,t.missionprop15,t.missionprop13,"
          + " (select VIPProperty from latree where agentcode = t.missionprop4) VIPProperty ,"
          + " (select vipvalue from ldperson  where customerno = t.missionprop6) Vipvalue,t.missionprop11 ,"
          + " ( select to_date(max(enteraccdate)) from ljtempfee where  othernotype='4' and otherno = rpad(t.missionprop2,lislen('ljtempfee','otherno') ) ) "
          + " from lwmission t where 1=1 "
					+ " and t.activityid in ('0000002004')"
					+ " and t.defaultoperator is null "
			  if(fm.VIPCustomer.value = "")					
			  {
			      strSQL = 	strSQL	+ " and trim(missionprop6) in (select trim(customerno) from ldperson where vipvalue like '%"+fm.VIPCustomer.value+"')";
		      }
		      if(fm.StarAgent.value = "")			
		      {
			      strSQL =  strSQL  + " and trim(missionprop4) in (select trim(agentcode) from latree where vipproperty like '%"+fm.StarAgent.value+"' )"
		      }

			   strSQL = strSQL  
				   + getWherePart('t.MissionProp2','ContNo',strOperate)
				   + getWherePart('t.MissionProp5','AgentCode',strOperate)
				   + getWherePart('MissionProp3','SaleChnl',strOperate)
				   + getWherePart('MissionProp4','ManageCom',strOperate)
				   + getWherePart('t.MissionProp13','ApproveDate',strOperate)
				   + getWherePart('t.MissionProp12','UWAuthority',strOperate)
				   //+ getWherePart('t.missionprop11','UWUpReportType',strOperate)
			     + getWherePart('t.missionprop7','AppntName2',strOperate)
			     + " and t.MissionProp4 like '"+com+"%%'"
			   if(fm.InsurName2.value!="")
			     {
				 		 var sql="select prtno from lccont where insuredname='"+fm.InsurName2.value+"'";
				     var arrResult = easyExecSql("select prtno from lccont where insuredname='"+fm.InsurName2.value+"'",1,0);
              var b;
              strSQL=strSQL+" and missionprop2 in ("
              for( b=0;b<arrResult.length-1;b++)
                  {   
                  	  
                  	  strSQL=strSQL+"'"+trim(arrResult[b][0])+"',"
                  }
                 strSQL=strSQL+ "'"+arrResult[arrResult.length-1][0]+"')" 
				    }
				    if(fm.EnteraccDate2.value!="")
				   {
				     	strSQL = strSQL 								
			 	       + " and exists(select 'x' from ljtempfee x where othernotype='4' and x.otherno = rpad(t.missionprop2,lislen('ljtempfee','otherno') ) and x.enteraccdate='"+document.all("EnteraccDate2").value+"') "
				   }
				   //����ú˱�Ա�˱������Ǹû�������߼�����У��˱�����
				   if(ismaxuw==null)
				   {//
				   	strSQL=strSQL+" and (t.MissionProp11='0' or t.MissionProp12<='"+Result+"') " 
				   }     
				   else
				   {
				  	strSQL=strSQL+" and t.MissionProp12<='"+Result+"' "
				   }     
			     strSQL=strSQL + " and (t.MissionProp14 is null or t.MissionProp14='0000000000')"
				     + " order by t.modifydate asc,t.modifytime asc";

	}
	if(operFlag == "3")
	{
		
//		var sql="select uwpopedom From LDUWUser where usercode='"+operator+"' and uwtype='2'";
		    var  sqlid11="ManuUWAll11";
	 	 	var  mySql11=new SqlClass();
	 	 	mySql11.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
	 	 	mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
	 	 	mySql11.addSubPara(usercode);//ָ������Ĳ���
	 	    sql=mySql11.getString();
		var Result = easyExecSql(sql);
		if(Result==null)
		{
			alert("�ú˱�ԭԱ"+operator+"û�ж���˱�����");
			return;
		}
		//sql="select comcode From lduser where usercode='"+operator+"'";
		    var  sqlid12="ManuUWAll12";
	 	 	var  mySql12=new SqlClass();
	 	 	mySql12.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
	 	 	mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
	 	 	mySql12.addSubPara(usercode);//ָ������Ĳ���
	 	    sql=mySql12.getString();
		var com=easyExecSql(sql);
		document.all('ManageCom').value = com;
		//��ѯ�жϸú˱������Ƿ�û����˱�Ա����ߺ˱�����
		//ismaxuw����null�Ļ�����˵���ú˱�Ա�������ڻ��������Ȩ��
		sql="select * from dual where ( select max(uwpopedom) from lduwuser "
		   +" where uwtype='2' and usercode in"
		   +" (select usercode from lduser where comcode like '"+com+"%%'))>'"+Result+"'";
		var ismaxuw=easyExecSql(sql);
		
		 strSQL = "select t.missionprop2,t.missionprop1,t.missionprop6,(select codename from ldcode where codetype='uwstatus' and code=t.activitystatus),case t.activityid when '0000006004' then '�˱�����' end,t.missionid,t.submissionid,t.activityid,"
		 +" case t.missionprop11 when '0' then '����Լ�˱�' when '1' then '�ϱ�'  when '2' then '��Ȩ���ϱ�' when '3' then '�ٱ��ϱ�' when '4' then '�����¼�' end, "
		 +" t.missionprop12,t.missionprop4,t.missionprop3," 
		 +" (select polapplydate from lcgrpcont where (lcgrpcont.grpcontno) = rpad(t.missionprop1,lislen('lcgrpcont','grpcontno'),' ')) poldate ," 
		 +" (select approvedate from lcgrpcont where (lcgrpcont.grpcontno) = rpad(t.missionprop1,lislen('lcgrpcont','grpcontno'),' ')) approvedate,"
		 +" '',"
		 +" ( select to_date(max(enteraccdate)) from ljtempfee where  othernotype='4' and otherno = rpad(t.missionprop2,lislen('ljtempfee','otherno') ) ) "
		  +" from lwmission t where 1=1 "
					+ " and t.activityid in ('0000006004')"
					+ " and defaultoperator is null ";  		
		
		 strSQL = strSQL
		 				   + " and t.MissionProp4 like '"+com+"%%'";
		
		if(ismaxuw==null)
        {
            strSQL=strSQL+" and (t.MissionProp11='0' or t.MissionProp12<='"+Result+"') " 
        }     
        else
        {
            strSQL=strSQL+" and t.MissionProp12<='"+Result+"' "
        }     
					
		if (fm.ContNo.value!="")
		{
			strSQL = strSQL + " and t.MissionProp1='"+fm.ContNo.value+"'";
		}
		if (document.all('ManageCom').value!="")
		{
			strSQL = strSQL + " and MissionProp4 like '"+document.all('ManageCom').value+"%'";
		}
		if(fm.EnteraccDate2.value!="")
		{ 
			 strSQL = strSQL
			  								
		   + " and exists(select 'x' from ljtempfee x where othernotype='4' and x.otherno = rpad(t.missionprop2,lislen('ljtempfee','otherno') ) and x.enteraccdate='"+document.all("EnteraccDate2").value+"') "
		}
		strSQL = strSQL + " order by t.modifydate asc,t.modifytime asc";
	}
		if(withdrawFlag)
		{
			
		}
//		prompt("",strSQL);
		turnPage.queryModal(strSQL, AllPolGrid); 
		changeColor(bg);
		withdrawFlag = false; 
}

/*********************************************************************
 *  ִ������Լ�˹��˱���EasyQueryAddClick
 *  ����:����˱�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
/* modify by lzf 
 * function easyQueryAddClick()
{	
	var tSel = AllPolGrid.getSelNo();	   
	var ContNo = AllPolGrid.getRowColData(tSel - 1,10);        //��ͬͶ������
	var PrtNo = AllPolGrid.getRowColData(tSel - 1,10);         //ӡˢ��
	var MissionID = AllPolGrid.getRowColData(tSel - 1,18);     //�����������
	var SubMissionID = AllPolGrid.getRowColData(tSel - 1,19);  //�������������
	var activityid = AllPolGrid.getRowColData(tSel - 1,20);    //�������Id 
	var UWState = AllPolGrid.getRowColData(tSel - 1,7);
	var ReportFlag = AllPolGrid.getRowColData(tSel - 1,12);     //�ϱ���־
	//alert(ReportFlag);
	var NoType = operFlag;
	
	if(activityid == "0000001100")
	{
		window.open("./UWManuInputMain.jsp?ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo+"&ActivityID="+activityid+"&UWState="+UWState+"&NoType="+NoType+"&ReportFlag="+ReportFlag,"",sFeatures);
	}
	if(activityid == "0000002004")
	{
		window.open("./GroupUWMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&NoType="+NoType,"",sFeatures);
	}
	if(activityid == "0000006004")
	{
		window.open("../askapp/AskUWManuInputMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid,"",sFeatures); 
	}
}
*/
function CheckUserCode()
{
    if (document.all('UserCode').value!=null)
       if(document.all('UserCode').value != operator)
        {
           alert("�������뵱ǰ�˱�Ա���룡");
           document.all('UserCode').value = "";
           return;
        }
}
/* modify by lzf 2013-03-29
 * function IniteasyQueryAddClick()
{
	var tSel = PolGrid.getSelNo();
	var activityid = PolGrid.getRowColData(tSel - 1,20);    //�������Id    
	var ContNo = PolGrid.getRowColData(tSel - 1,10);        //��ͬͶ������
	var PrtNo = PolGrid.getRowColData(tSel - 1,10);         //ӡˢ��
	var MissionID = PolGrid.getRowColData(tSel - 1,18);     //�����������
	var SubMissionID = PolGrid.getRowColData(tSel - 1,19);  //�������������
	var UWState = PolGrid.getRowColData(tSel - 1,21);
	var ReportFlag = PolGrid.getRowColData(tSel - 1,16);     //�ϱ���־
	var CustomerNo = PolGrid.getRowColData(tSel - 1,23);     //�ϱ���־
	//alert(ReportFlag);
	var NoType = operFlag;
	
	if(activityid == "0000001100")
	{
		window.open("./UWManuInputMain.jsp?ContNo="+ContNo+"&CustomerNo="+CustomerNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo+"&ActivityID="+activityid+"&UWState="+UWState+"&NoType="+NoType+"&ReportFlag="+ReportFlag,"",sFeatures);
	}
	if(activityid == "0000002004")
	{
		window.open("./GroupUWMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&NoType="+NoType,"",sFeatures);
	}
	if(activityid == "0000006004")
	{
		window.open("../askapp/AskUWManuInputMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid,"",sFeatures); 
	}
}
*/

//�ŵ�����¼��

function GrpIniteasyQueryAddClick()
{

	var tSel = PolGrid.getSelNo();
	var activityid = PolGrid.getRowColData(tSel - 1,8);    //�������Id    
	
	var ContNo = PolGrid.getRowColData(tSel - 1,2);        //��ͬͶ������
	
	var PrtNo = PolGrid.getRowColData(tSel - 1,1);         //ӡˢ��
	var MissionID = PolGrid.getRowColData(tSel - 1,6);     //�����������
	var SubMissionID = PolGrid.getRowColData(tSel - 1,7);  //�������������
	
	//var ReportFlag = PolGrid.getRowColData(tSel - 1,16);     //�ϱ���־
	
	var NoType = operFlag;
	
	if(activityid == "0000001100")
	{
		window.open("./UWManuInputMain.jsp?ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo+"&ActivityID="+activityid+"&NoType="+NoType+"&ReportFlag="+ReportFlag);
	}
	if(activityid == "0000002004")
	{
		window.open("./GroupUWMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&NoType="+NoType);
	}
	if(activityid == "0000006004")
	{
		window.open("../askapp/AskUWManuInputMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid); 
	}
}
/*********************************************************************
 *  ����˱�
 *  ����:����˱�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
/*
 * modify by lzf
 *function ApplyUW()
{    
    var count1 =0;
    var count2 =0;    
    //alert(document.all('UWState1').value);
    //δ�˹��˱����˱�����Ŀ���ܳ���20
    if(document.all('UWState1').value!=null&&document.all('UWState1').value!=""&&document.all('UWState1').value=="1")
	{
		var sql="select count(*) from lwmission t where 1=1 "  
					+ " and t.activityid in ('0000001100')"
					+ " and t.defaultoperator ='" + operator + "'" 
					+ " and t.missionprop18='1'";
	    count1=easyExecSql(sql);
	//    alert(count1);
	    if (count1 > 20)
		{
		      alert("���˹�����������");
		 //     return;
		}				
	}    
        
	var selno = AllPolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��Ҫ�����Ͷ������");
	      return;
	}
	//alert(operFlag);
	if(operFlag == 1)
	{
		fm.MissionID.value = AllPolGrid.getRowColData(selno, 18);
		fm.SubMissionID.value = AllPolGrid.getRowColData(selno, 19);
		fm.ActivityID.value = AllPolGrid.getRowColData(selno, 20);	
	}
	else
	{
		fm.MissionID.value = AllPolGrid.getRowColData(selno, 18);
		fm.SubMissionID.value = AllPolGrid.getRowColData(selno, 19);
		fm.ActivityID.value = AllPolGrid.getRowColData(selno, 20);	
	}
	
	//2009-1-6 ln add --����˱�Ա�Ƿ���Ա��������Ȩ�ޣ����û����������Ա����
    var sqloperator="select count(*) from lccont where prtno='"+AllPolGrid.getRowColData(selno, 10)+"' and agentcode like '%%999999'";
    count1=easyExecSql(sqloperator);
	if (count1 > 0)
	{
	 	sqloperator="select uwoperatorflag from lduwuser where usercode='"+operator+"' and uwtype='1'";
    	var operatorFlag =easyExecSql(sqloperator);
    	if(operatorFlag ==null || operatorFlag == '' || operatorFlag == 'N')
    	{
    		alert("û��Ա�����˱�Ȩ�ޣ��������룡");
    		return;
    	}
    	
	}
	//09-06-01  ����У����������ѱ����뵽���˳��򷵻ش�����ʾ ˢ�½���
	var tOperatorSql = "select count(1) from lwmission where missionid='"+fm.MissionID.value+"'"
					+ " and submissionid='"+fm.SubMissionID.value+"' and activityid='0000001100'"
					+ " and defaultoperator is not null";
	var tOperator = 0;
	tOperator = easyExecSql(tOperatorSql);
	if(tOperator>0){
		alert("�����ѱ�������Ա���뵽���˹����أ�");
		easyQueryClickAll();//
		return false;
	}
	//add ln 2008-10-06  //2008-12-8 ln modify
	var sql="select decode(SurpassGradeFlag,'',uwpopedom,SurpassGradeFlag) from lduwuser where usercode='"+operator+"' and uwtype='1'";
	var Result = easyExecSql(sql);
	sql="select comcode from lduser where usercode='"+operator+"'";
	var com=easyExecSql(sql);
	sql="select insuredno from lcinsured where prtno='"+AllPolGrid.getRowColData(selno, 10)+"' ";
	var insuredNo=easyExecSql(sql);
	sql="select count(*) from lwmission w,lcinsured b"
	       + " where w.activityid='0000001100' and w.missionprop18 in ('1','2','3','4')"
	       + " and b.contno = w.MissionProp2 "
	       + " and w.MissionProp10 like '"+com+"%%'"
		   + " and w.MissionProp12>'"+Result+"'"  
		   + " and (w.MissionProp14 is null or w.MissionProp14='0000000000')"
		   + " and w.defaultoperator is null "
	       + " and b.insuredno in (select insuredno from lcinsured where prtno='"+AllPolGrid.getRowColData(selno, 10)+"' ) and w.MissionProp1 <> '"+AllPolGrid.getRowColData(selno, 10)+"'";		   
	
	var count=easyExecSql(sql);
	if (count > 0)
	{
		 alert("�г�Ȩ�޵�Ͷ�����޷�����,���ϱ���");		 
		     return;
	}	
	
	//tongmeng 2009-05-11 modify
	//�����ų�4״̬�ı���
	sql="select count(*) from lwmission w,lcinsured b"
	     //  + " where w.activityid='0000001100' and w.missionprop18 in ('1','2','3','4')"
	       + " where w.activityid='0000001100' and w.missionprop18 in ('1','2','3')"
	       + " and b.contno = w.MissionProp2 "
	       + " and w.MissionProp10 like '"+com+"%%'"
		   + " and w.MissionProp12<='"+Result+"'"  
		   + " and (w.MissionProp14 is null or w.MissionProp14='0000000000')"
		   + " and w.defaultoperator is null "
	       + " and b.insuredno in (select insuredno from lcinsured where prtno='"+AllPolGrid.getRowColData(selno, 10)+"' ) and w.MissionProp1 <> '"+AllPolGrid.getRowColData(selno, 10)+"'";		   
	
	count=easyExecSql(sql);
	var type="0";
	if (count > 0)
	{
	     type = "1";
		 if(!confirm("�˱����������������˱�Ͷ����("+count+"��)����һ�������Ƿ�������룿"))		 
		     return;
	}	
	//end add ln 2008-10-06
	
	//2008-11-27 add ln --δ�˹��˱����ϴ˴��������Ҫ������20��
	sql="select count(*) from lwmission w,lcinsured b"
	       + " where w.activityid='0000001100' and w.missionprop18 in ('1')"
	       + " and b.contno = w.MissionProp2 "
	       + " and w.MissionProp10 like '"+com+"%%'"
		   + " and w.MissionProp12<='"+Result+"'"  
		   + " and (w.MissionProp14 is null or w.MissionProp14='0000000000')"
		   + " and w.defaultoperator is null "
	       + " and b.insuredno in (select insuredno from lcinsured where prtno='"+AllPolGrid.getRowColData(selno, 10)+"' ) and w.MissionProp1 <> '"+AllPolGrid.getRowColData(selno, 10)+"'";		   
	
	count2=easyExecSql(sql);
	var count3=parseInt(count1)+parseInt(count2)-20;
	if (count3 > 0)
	{
		 if(!confirm("��������˴�Ͷ���������˹�������δ�˹��˱���Ͷ����������"+count3+"�ţ��Ƿ�������룿"))		 
		     return;
	}
	
	var i = 0;
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    fm.Com.value = com;
    fm.PrtNo.value = AllPolGrid.getRowColData(selno, 10);
    fm.Result.value = Result;
    fm.Type.value = type;   
	fm.action = "./ManuUWAllChk.jsp";
	document.getElementById("fm").submit(); //�ύ
}

/*********************************************************************
 *  �ύ�����,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
/*modify by lzf
 * function afterSubmit( FlagStr, content )
{
  showInfo.close();
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  if (FlagStr == "Fail" )
  {                 
    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
	easyQueryClick();
	easyQueryClickAll();//ˢ��ҳ��
	
  }
  else
  { 
	showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
	
	easyQueryAddClick();//ִ����һ������
	easyQueryClickAll();
	if(operFlag != "3")
	NeweasyQueryClick();
  }

}
*/

function showNotePad()
{

	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}
	
	var MissionID = PolGrid.getRowColData(selno, 7);
	var SubMissionID = PolGrid.getRowColData(selno, 8);
	var ActivityID = PolGrid.getRowColData(selno, 9);
	var PrtNo = PolGrid.getRowColData(selno, 1);
	var NoType = operFlag;
	if(PrtNo == null || PrtNo == "")
	{
		alert("ӡˢ��Ϊ�գ�");
		return;
	}			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");	
		
}

/*********************************************************************
 *  ѡ��˱����Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field )
{
	if(fm.UWState2.value=="0" ){
       alert("��������0״̬��");
       fm.UWState2.value= "";
       fm.UWStateName2.value = "";
   }
}     


/* modify by lzf 2013-03-29
 * function queryAgent()
{   
	//tongmeng 2007-10-17 modify
	//�޸�������������
	//��Ҫ���ݱ�������ȥȷ������������
	var tSaleChnl = document.all('SaleChnl').value;
	if(tSaleChnl==''||tSaleChnl==null)
	{
		alert('����ѡ����������!');
		return false;
	}
	if(operFlag == "2")
	  {
	  	var branchtype="2";
	  }
	 else 
	 	{
	 		
	 		var branchtype="1";
	 		if(tSaleChnl=='3')
	 		{
	 			branchtype ="3";
	 		}
	 	}
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value+"&branchtype="+branchtype+"&queryflag=2","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0;'+sFeatures);
}
*/
function queryAgent1()
{   
	//ln 2008-12-10 add
	var tSaleChnl1 = document.all('SaleChnl1').value;
	if(tSaleChnl1==''||tSaleChnl1==null)
	{
		alert('����ѡ����������!');
		return false;
	}
	if(operFlag == "2")
	  {
	  	var branchtype="2";
	  }
	 else 
	 	{
	 		
	 		var branchtype="1";
	 		if(tSaleChnl1=='3')
	 		{
	 			branchtype ="3";
	 		}
	 	}
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom1').value+"&branchtype="+branchtype+"&queryflag=1","AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/* modify by lzf 2013-03-29
 * function afterQuery2(arrResult)
{
  
  if(arrResult!=null)
  {
    	fm.AgentCode.value = arrResult[0][0];
  	 // fm.AgentCode1.value = arrResult[0][0];
  }
}
*/
function afterQuery3(arrResult)
{
  
  if(arrResult!=null)
  {
  	  fm.AgentCode1.value = arrResult[0][0];
  }
}

// add by lzf 2013-03-29
function queryAgent(gridName,row,col){
	if (gridName.indexOf("Public") != -1) {
		flag = "public";
	} else if (gridName.indexOf("Private") != -1) {
		flag = "private";
	}
	if (document.all('AgentCode').value == "") {
		var newWindow = window.open(
						"../sys/AgentCommonQueryMain.jsp?queryflag=" + flag
								+ "&ManageCom=" + document.all('ManageCom').value
								+ "&row="+row+"&col="+col  );
	}
}

	function afterQuery2(arrResult, queryFlag,row,col) {
		if (arrResult != null) {
			if (queryFlag == "public") {
				PublicWorkPoolQueryGrid.setRowColData(row, col, arrResult[0][0]);
			} else if (queryFlag == "private") {
				PrivateWorkPoolQueryGrid.setRowColData(row, col, arrResult[0][0]);
			}
		}
	}

	//���˳� ����˱�ҳ��
	function IniteasyQueryAddClick()
	{ 
		var tSel = 0;
		var i = 0;
		for(i=0;i<PrivateWorkPoolGrid.mulLineCount;i++){
			if(PrivateWorkPoolGrid.getSelNo(i)){
				tSel = PrivateWorkPoolGrid.getSelNo();
				break;
			}
			
		}
			//alert(tSel);
		var activityid = PrivateWorkPoolGrid.getRowColData(tSel - 1,33);    //�������Id    
		var ContNo = PrivateWorkPoolGrid.getRowColData(tSel - 1,11);        //��ͬͶ������
		var PrtNo = PrivateWorkPoolGrid.getRowColData(tSel - 1,9);         //ӡˢ��
		var MissionID = PrivateWorkPoolGrid.getRowColData(tSel - 1,30);     //�����������
		var SubMissionID = PrivateWorkPoolGrid.getRowColData(tSel - 1,31);  //�������������
		var UWState = PrivateWorkPoolGrid.getRowColData(tSel - 1,6);    //�˱�����״̬
		var ReportFlag = PrivateWorkPoolGrid.getRowColData(tSel - 1,3);     //�ϱ���־
		var CustomerNo = PrivateWorkPoolGrid.getRowColData(tSel - 1,15);     //Ͷ���˺�
		var UWAuthority = PrivateWorkPoolGrid.getRowColData(tSel - 1,1);     //�˱�����		
		var NoType = operFlag;
		
		if(operFlag == "1")
		{
			window.open("./UWManuInputMain.jsp?ContNo="+ContNo+"&CustomerNo="+CustomerNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo+"&ActivityID="+activityid+"&UWState="+UWState+"&NoType="+NoType+"&ReportFlag="+ReportFlag+"&UWAuthority="+UWAuthority,"",sFeatures);
		}
		if(operFlag == "2")
		{
			window.open("./GroupUWMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&NoType="+NoType,"",sFeatures);
		}
		if(operFlag == "3")
		{
			window.open("../askapp/AskUWManuInputMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid,"",sFeatures); 
		}
	}
 //���ص�½�˵Ļ���
 function returncomcode(){
	 var sql = "";
	 var com ;
	 if(operFlag == "1")
		{		   
          //sql="select comcode from lduser where usercode='"+operator+"'";
            var  sqlid13="ManuUWAll13";
	 	 	var  mySql13=new SqlClass();
	 	 	mySql13.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
	 	 	mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
	 	 	mySql13.addSubPara(operator);//ָ������Ĳ���
	 	    sql=mySql13.getString();
		  com=easyExecSql(sql);
		  return com; 
		}
 }	
	
 
 /*********************************************************************
  *  ִ������Լ�˹��˱���EasyQueryAddClick
  *  ����:����˱�����
  *  ����  ��  ��
  *  ����ֵ��  ��
  *********************************************************************
  */
 function easyQueryAddClick()
 {	
	 var tSel = 0;
		var i = 0;
		for(i=0;i<PublicWorkPoolGrid.mulLineCount;i++){
			if(PublicWorkPoolGrid.getSelNo(i)){
				tSel = PublicWorkPoolGrid.getSelNo();
				break;
			}			
		}
			//alert(tSel);
		var activityid = PublicWorkPoolGrid.getRowColData(tSel - 1,35);    //�������Id    
		var ContNo = PublicWorkPoolGrid.getRowColData(tSel - 1,12);        //��ͬͶ������
		var PrtNo = PublicWorkPoolGrid.getRowColData(tSel - 1,10);         //ӡˢ��
		var MissionID = PublicWorkPoolGrid.getRowColData(tSel - 1,32);     //�����������
		var SubMissionID = PublicWorkPoolGrid.getRowColData(tSel - 1,33);  //�������������
		var UWState = PublicWorkPoolGrid.getRowColData(tSel - 1,7);    //�˱�����״̬
		var ReportFlag = PublicWorkPoolGrid.getRowColData(tSel - 1,20);     //�ϱ���־
		var CustomerNo = PublicWorkPoolGrid.getRowColData(tSel - 1,16);     //Ͷ���˺�
		var UWAuthority = PublicWorkPoolGrid.getRowColData(tSel - 1,1);     //�˱�����		
 	    var NoType = operFlag;
 	  
 	if(operFlag == "1")
 	{
 		window.open("./UWManuInputMain.jsp?ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo+"&ActivityID="+activityid+"&UWState="+UWState+"&NoType="+NoType+"&ReportFlag="+ReportFlag+"&UWAuthority="+UWAuthority,"",sFeatures);
 	}
 	if(operFlag == "2")
 	{
 		window.open("./GroupUWMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&NoType="+NoType,"",sFeatures);
 	}
 	if(operFlag == "3")
 	{
 		window.open("../askapp/AskUWManuInputMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid,"",sFeatures); 
 	}
 }

 /*********************************************************************
  *  ����˱�
  *  ����:����˱�����
  *  ����  ��  ��
  *  ����ֵ��  ��
  *********************************************************************
  */
 function ApplyUW()
 {    
     var count1 =0;
     var count2 =0;    
     var uwstate = PublicWorkPoolQueryGrid.getRowColData(0,12);
    // alert("uwstate=="+uwstate);
     //δ�˹��˱����˱�����Ŀ���ܳ���20
     if(uwstate!=null&&uwstate!=""&&uwstate=="1")
    	{
// 		 var sql="select count(*) from lwmission t where 1=1 "  
// 					+ " and t.activityid in (select activityid from lwactivity where functionid ='10010028')"
// 					+ " and t.defaultoperator ='" + operator + "'" 
// 					+ " and exists (select distinct 1 from lccuwmaster where contno=t.missionprop1 and lccuwmaster.uwstate  = '1')";
 		    var  sqlid14="ManuUWAll14";
	 	 	var  mySql14=new SqlClass();
	 	 	mySql14.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
	 	 	mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
	 	 	mySql14.addSubPara(operator);//ָ������Ĳ���
	 	     var sql=mySql14.getString();
 	    count1=easyExecSql(sql);
 	//    alert(count1);
 	    if (count1 > 20)
 		{
 		      alert("���˹�����������");
 		 //     return;
 		}				
 	}    
         
 	var selno = PublicWorkPoolGrid.getSelNo()-1;
 	if (selno <0)
 	{
 	      alert("��ѡ��Ҫ�����Ͷ������");
 	      return;
 	}
 	//alert(operFlag);
 	if(operFlag == 1)
 	{
 		fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 32);
 		fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 33);
 		fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 35);	
// 		alert(fm.MissionID.value);
// 		alert(fm.SubMissionID.value);
// 		alert(fm.ActivityID.value);		
 	}
 	else
 	{
 		fm.MissionID.value = PublicWorkPoolGrid.getRowColData(selno, 32);
 		fm.SubMissionID.value = PublicWorkPoolGrid.getRowColData(selno, 33);
 		fm.ActivityID.value = PublicWorkPoolGrid.getRowColData(selno, 35);	
 	}
 	
 	//2009-1-6 ln add --����˱�Ա�Ƿ���Ա��������Ȩ�ޣ����û����������Ա����
//     var sqloperator="select count(*) from lccont where prtno='"+PublicWorkPoolGrid.getRowColData(selno, 10)+"' and agentcode like '%%999999'";
     var  sqlid15="ManuUWAll15";
 	 var  mySql15=new SqlClass();
 	 mySql15.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
 	 mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
 	 mySql15.addSubPara(PublicWorkPoolGrid.getRowColData(selno, 10));//ָ������Ĳ���
     var sqloperator=mySql15.getString();
     count1=easyExecSql(sqloperator);
 	if (count1 > 0)
 	{
// 	 	sqloperator="select uwoperatorflag from lduwuser where usercode='"+operator+"' and uwtype='1'";
 	 	 var  sqlid16="ManuUWAll16";
 	 	 var  mySql16=new SqlClass();
 	 	 mySql16.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
 	 	 mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
 	 	 mySql16.addSubPara(operator);//ָ������Ĳ���
 	     sqloperator=mySql16.getString();
     	var operatorFlag =easyExecSql(sqloperator);
     	if(operatorFlag ==null || operatorFlag == '' || operatorFlag == 'N')
     	{
     		alert("û��Ա�����˱�Ȩ�ޣ��������룡");
     		return;
     	}
     	
 	}
 	//09-06-01  ����У����������ѱ����뵽���˳��򷵻ش�����ʾ ˢ�½���
// 	var tOperatorSql = "select count(1) from lwmission where missionid='"+fm.MissionID.value+"'"
// 					+ " and submissionid='"+fm.SubMissionID.value+"' and activityid='"+fm.ActivityID.value+"'"
// 					+ " and defaultoperator is not null";
 	     var  sqlid17="ManuUWAll17";
	 	 var  mySql17=new SqlClass();
	 	 mySql17.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
	 	 mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
	 	 mySql17.addSubPara(fm.MissionID.value);//ָ������Ĳ���
	 	 mySql17.addSubPara(fm.SubMissionID.value);//ָ������Ĳ���
	 	 mySql17.addSubPara(fm.ActivityID.value);//ָ������Ĳ���
	     var tOperatorSql=mySql17.getString();
 	var tOperator = 0;
 	tOperator = easyExecSql(tOperatorSql);
 	//alert("tOperator=="+tOperator);
 	if(tOperator>0){
 		alert("�����ѱ�������Ա���뵽���˹����أ�");
 		jQuery("#publicSearch").click();//
 		return false;
 	}

 	//var sql="select (case SurpassGradeFlag when '' then uwpopedom else SurpassGradeFlag end) from lduwuser where usercode='"+operator+"' and uwtype='1'";
 	     var  sqlid18="ManuUWAll18";
	 	 var  mySql18=new SqlClass();
	 	 mySql18.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
	 	 mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
	 	 mySql18.addSubPara(operator);//ָ������Ĳ���
	     var sql=mySql18.getString();
 	var Result = easyExecSql(sql);
// 	sql="select comcode from lduser where usercode='"+operator+"'";
 	     var  sqlid19="ManuUWAll19";
	     var  mySql19=new SqlClass();
	     mySql19.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
	     mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
	     mySql19.addSubPara(operator);//ָ������Ĳ���
          sql=mySql19.getString();
 	var com=easyExecSql(sql);
 	//sql="select insuredno from lcinsured where prtno='"+PublicWorkPoolGrid.getRowColData(selno, 10)+"' ";
 	     var  sqlid20="ManuUWAll20";
         var  mySql20=new SqlClass();
         mySql20.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
         mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
         mySql20.addSubPara(PublicWorkPoolGrid.getRowColData(selno, 10));//ָ������Ĳ���
          sql=mySql20.getString();
 	var insuredNo=easyExecSql(sql);
// 	sql="select count(*) from lwmission w,lcinsured b"
// 	       + " where w.activityid='"+fm.ActivityID.value+"' and exists (select distinct 1 from lccuwmaster where contno=w.missionprop1 and lccuwmaster.uwstate  in ('1','2','3','4'))"
// 	       + " and b.contno = w.MissionProp2 "
// 	       + " and w.MissionProp10 like '"+com+"%%'"
// 		   + " and w.MissionProp12>'"+Result+"'"  
// 		   + " and (w.MissionProp14 is null or w.MissionProp14='0000000000')"
// 		   + " and w.defaultoperator is null "
// 	       + " and b.insuredno in (select insuredno from lcinsured where prtno='"+PublicWorkPoolGrid.getRowColData(selno, 10)+"' ) and w.MissionProp1 <> '"+PublicWorkPoolGrid.getRowColData(selno, 10)+"'";
 	
 	 var  sqlid21="ManuUWAll21";
     var  mySql21=new SqlClass();
     mySql21.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
     mySql21.setSqlId(sqlid21);//ָ��ʹ�õ�Sql��id
     mySql21.addSubPara(fm.ActivityID.value);//ָ������Ĳ���
     mySql21.addSubPara(com);//ָ������Ĳ���
     mySql21.addSubPara(Result);//ָ������Ĳ���
     mySql21.addSubPara(PublicWorkPoolGrid.getRowColData(selno, 10));//ָ������Ĳ���
     mySql21.addSubPara(PublicWorkPoolGrid.getRowColData(selno, 10));//ָ������Ĳ���
      sql=mySql21.getString();
 	
 	var count=easyExecSql(sql);
 	if (count > 0)
 	{
 		 alert("�г�Ȩ�޵�Ͷ�����޷�����,���ϱ���");		 
 		     return;
 	}	
 	
 	//tongmeng 2009-05-11 modify
 	//�����ų�4״̬�ı���
// 	sql="select count(*) from lwmission w,lcinsured b"
// 	       + " where w.activityid='"+fm.ActivityID.value+"' and exists (select distinct 1 from lccuwmaster where contno=w.missionprop1 and lccuwmaster.uwstate in ('1','2','3'))"
// 	       + " and b.contno = w.MissionProp2 "
// 	       + " and w.MissionProp10 like '"+com+"%%'"
// 		   + " and w.MissionProp12<='"+Result+"'"  
// 		   + " and (w.MissionProp14 is null or w.MissionProp14='0000000000')"
// 		   + " and w.defaultoperator is null "
// 	       + " and b.insuredno in (select insuredno from lcinsured where prtno='"+PublicWorkPoolGrid.getRowColData(selno, 10)+"' ) and w.MissionProp1 <> '"+PublicWorkPoolGrid.getRowColData(selno, 10)+"'";
 	 var  sqlid22="ManuUWAll22";
     var  mySql22=new SqlClass();
     mySql22.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
     mySql22.setSqlId(sqlid22);//ָ��ʹ�õ�Sql��id
     mySql22.addSubPara(fm.ActivityID.value);//ָ������Ĳ���
     mySql22.addSubPara(com);//ָ������Ĳ���
     mySql22.addSubPara(Result);//ָ������Ĳ���
     mySql22.addSubPara(PublicWorkPoolGrid.getRowColData(selno, 10));//ָ������Ĳ���
     mySql22.addSubPara(PublicWorkPoolGrid.getRowColData(selno, 10));//ָ������Ĳ���
      sql=mySql22.getString();
 	
 	count=easyExecSql(sql);
 	var type="0";
 	if (count > 0)
 	{
 	     type = "1";
 		 if(!confirm("�˱����������������˱�Ͷ����("+count+"��)����һ�������Ƿ�������룿"))		 
 		     return;
 	}	
 	//end add ln 2008-10-06
 	
 	//2008-11-27 add ln --δ�˹��˱����ϴ˴��������Ҫ������20��
// 	sql="select count(*) from lwmission w,lcinsured b"
// 	       + " where w.activityid='"+fm.ActivityID.value+"' and exists (select distinct 1 from lccuwmaster where contno=w.missionprop1 and lccuwmaster.uwstate in ('1'))"
// 	       + " and b.contno = w.MissionProp2 "
// 	       + " and w.MissionProp10 like '"+com+"%%'"
// 		   + " and w.MissionProp12<='"+Result+"'"  
// 		   + " and (w.MissionProp14 is null or w.MissionProp14='0000000000')"
// 		   + " and w.defaultoperator is null "
// 	       + " and b.insuredno in (select insuredno from lcinsured where prtno='"+PublicWorkPoolGrid.getRowColData(selno, 10)+"' ) and w.MissionProp1 <> '"+PublicWorkPoolGrid.getRowColData(selno, 10)+"'";
 	var  sqlid23="ManuUWAll23";
    var  mySql23=new SqlClass();
    mySql23.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
    mySql23.setSqlId(sqlid23);//ָ��ʹ�õ�Sql��id
    mySql23.addSubPara(fm.ActivityID.value);//ָ������Ĳ���
    mySql23.addSubPara(com);//ָ������Ĳ���
    mySql23.addSubPara(Result);//ָ������Ĳ���
    mySql23.addSubPara(PublicWorkPoolGrid.getRowColData(selno, 10));//ָ������Ĳ���
    mySql23.addSubPara(PublicWorkPoolGrid.getRowColData(selno, 10));//ָ������Ĳ���
     sql=mySql23.getString();
 	
 	count2=easyExecSql(sql);
 	var count3=parseInt(count1)+parseInt(count2)-20;
 	if (count3 > 0)
 	{
 		 if(!confirm("��������˴�Ͷ���������˹�������δ�˹��˱���Ͷ����������"+count3+"�ţ��Ƿ�������룿"))		 
 		     return;
 	}
 	
 	var i = 0;
 	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
 	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
 	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
     fm.Com.value = com;
     fm.PrtNo.value = PublicWorkPoolGrid.getRowColData(selno, 10);
     fm.Result.value = Result;
     fm.Type.value = type;   
 	fm.action = "./ManuUWAllChk.jsp";
 	document.getElementById("fm").submit(); //�ύ
 }

 /*********************************************************************
  *  �ύ�����,���������ݷ��غ�ִ�еĲ���
  *  ����  ��  ��
  *  ����ֵ��  ��
  *********************************************************************
  */
 function afterSubmit( FlagStr, content )
 {
   showInfo.close();
   var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
   if (FlagStr == "Fail" )
   {                 
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 	easyQueryClick();
 	jQuery("#publicSearch").click();//ˢ��ҳ��
 	
   }
   else
   { 
 	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      	
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 	easyQueryAddClick();//ִ����һ������
 	jQuery("#publicSearch").click();
 	if(operFlag != "3")
 		jQuery("#privateSearch").click();
   }
 }

 function returnPolSum(){
	 //var sql="select (case SurpassGradeFlag when '' then uwpopedom else SurpassGradeFlag end) from lduwuser where usercode='"+operator+"' and uwtype='1'";
	    var  sqlid24="ManuUWAll24";
	    var  mySql24=new SqlClass();
	    mySql24.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
	    mySql24.setSqlId(sqlid24);//ָ��ʹ�õ�Sql��id
	    mySql24.addSubPara(operator);//ָ������Ĳ���
	    var sql=mySql24.getString();
		var Result = easyExecSql(sql);
		if(Result==null)
		{
			alert("�ú˱�Ա"+operator+"û�ж���˱�����");
			return;
		}
		//sql="select comcode from lduser where usercode='"+operator+"'";
		  var  sqlid25="ManuUWAll25";
	      var  mySql25=new SqlClass();
	      mySql25.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
	      mySql25.setSqlId(sqlid25);//ָ��ʹ�õ�Sql��id
	      mySql25.addSubPara(operator);//ָ������Ĳ���
	      sql=mySql25.getString();
		var com=easyExecSql(sql);
	//��ѯ�����������ܹ��ж��ٵ���
//	    var sumsql = "select count(*) from lwmission t where 1=1 "
//			+ " and t.activityid in (select activityid from lwactivity where functionid ='10010028')"
//			+ " and not exists(select 1 from ES_DOC_MAIN where doccode = t.missionprop1 and subtype='UR201')"  //2009-1-16�ų��ͻ����볷����Ͷ����
//			+ " and t.defaultoperator is null "
//			+ " and exists(select distinct 1 from lccuwmaster where contno=t.missionprop1 and lccuwmaster.uwstate in ('1')) "
//			+ " and t.MissionProp10 like '"+com+"%%'"
//			+ " and t.MissionProp12<='"+Result+"'"  
//			+ " and (t.MissionProp14 is null or t.MissionProp14='0000000000')";
	      var  sqlid26="ManuUWAll26";
	      var  mySql26=new SqlClass();
	      mySql26.setResourceName("uw.ManuUWAllSql"); //ָ��ʹ�õ�properties�ļ���
	      mySql26.setSqlId(sqlid26);//ָ��ʹ�õ�Sql��id
	      mySql26.addSubPara(com);//ָ������Ĳ���
	      mySql26.addSubPara(Result);//ָ������Ĳ���
	      var sql_Str=mySql26.getString();
	      var sum = easyExecSql(sql_Str);
	   if(sum!=null && sum!="")
		   document.getElementById("PolSum").value = sum;
	   else
		   document.getElementById("PolSum").value  = 0;	
	   
 }
// end by lzf 2013-03-29