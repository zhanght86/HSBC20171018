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
//���������뵽���˳ص�������ر���
var applyPrtNo ;
var applyContNo ;
var applyMissionID ;
var applySubMissionID ;
var applyUWState ;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var k = 0;

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
	var strOperate="like";	
	//add by yaory 
	//2008-12-8 ln modify
	
		var sqlid1="RnewSecUWAllSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("xb.RnewSecUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(operator);//ָ������Ĳ���
	    var sql=mySql1.getString();	
	
//  var sql="select decode(SurpassGradeFlag,'',uwpopedom,SurpassGradeFlag) From LDUWUser where usercode='"+operator+"' and uwtype='1'";
	var Result = easyExecSql(sql);	
	// ��дSQL���
	var strSQL ="";
	var strSQLBase="";
	var strSQLOther=" and 1=1 ";
	var strSQLOrder=" and 2=2 ";
	var addStr3 =" and 3=3 ";
	var addStr4 =" and 4=4 ";
	k++;
	
		var sqlid2="RnewSecUWAllSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("xb.RnewSecUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(operator);//ָ������Ĳ���
	    tSql=mySql2.getString();	
	
//	tSql="select comcode From lduser where usercode='"+operator+"'";
	var com=easyExecSql(tSql);
	var UWState2 = document.all('UWState2').value;
	//alert(operator);
/*
		var sqlid3="RnewSecUWAllSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("xb.RnewSecUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
//		mySql3.addSubPara(operator);//ָ������Ĳ���
	    strSQLBase=mySql3.getString();	
*/
	strSQLBase = "select t.missionprop2 contno,'' vip,'' star,min(missionprop10) insuredname,min(missionprop11) zhdate, min(maketime)  maketime, "
      +" (case min(missionprop12) when '1' then '' else  min(missionprop14) end) modifytime,(case min(missionprop12) when  '1' then '' else min(missionprop15) end) modifydate, "
      +" min(missionprop7) appntno,min(missionprop6)  managecom,min(lastoperator)  l_operator,min(missionid)  missionid, "
      +" min(submissionid)  submissionid,min(activityid)  activityid,min(ActivityStatus)  ActivityStatus, "
      +" min(missionprop1)  prtno,count(1) "
      ;       
	  
	  	var sqlid4="RnewSecUWAllSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("xb.RnewSecUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara(strSQLBase);//ָ������Ĳ���
		mySql4.addSubPara(operator);//ָ������Ĳ���
		mySql4.addSubPara(fm.ContNo2.value);//ָ������Ĳ���
		mySql4.addSubPara(fm.CustomerNo2.value);//ָ������Ĳ���
		mySql4.addSubPara(fm.AppntName2.value);//ָ������Ĳ���
		
		mySql4.addSubPara(fm.InsurName2.value);//ָ������Ĳ���
		mySql4.addSubPara(fm.ManageCom2.value);//ָ������Ĳ���
		mySql4.addSubPara(com);//ָ������Ĳ���
//	    strSQLBase=mySql4.getString();	
	  
//     strSQLOther = " from lwmission t where 1=1 and  activityid='0000007001' and defaultoperator='"+operator+"' "
//		+ getWherePart('t.MissionProp2','ContNo2',strOperate)
//		+ getWherePart('t.MissionProp7','CustomerNo2',strOperate)
//		+ getWherePart('t.MissionProp8','AppntName2',strOperate)
//		+ getWherePart('t.MissionProp10','InsurName2',strOperate)
//    + getWherePart('t.MissionProp6','ManageCom2',strOperate)
//    + " and t.missionprop6 like '"+com+"%%'" ;
      
    if(UWState2=='1')//δ��������
    {
    	strSQLOther= " and not exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus<>'1') ";
    	
    	strSQLOrder =  " order by vip,star,zhdate,maketime  ";
    }
    else if(UWState2=='3')//�����У�δ�ظ�
    {
    	strSQLOther= " and exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus='2' and x.missionprop12='3') "
      //+" and exists(select 1 from lcuwmaster a where a.contno=t.missionprop2 and a.polno=t.missionprop3  "
      //+"   and (a.healthflag in ('1','2') or a.reportflag in ('1','2') or a.printflag in ('1','2')) )"
      ;
      
      strSQLOrder =  " order by modifydate ,modifytime  ";
    }
    else if(UWState2=='2')//�ѻظ�
    {
    	strSQLOther= " and exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus='2' and x.missionprop12='2') "
      //+" and exists(select 1 from lcuwmaster a where a.contno=t.missionprop2 and a.polno=t.missionprop3  "
      //+"   and a.healthflag in ('0','3') and a.reportflag in ('0','3') and a.printflag in ('0','3')) "
      ;
      
      strSQLOrder =  " order by vip,star,modifydate ,modifytime ,zhdate,maketime  ";
    }else{
      strSQLOrder =  " order by vip,star,modifydate ,modifytime ,zhdate,maketime  ";
    }
    if(document.all('ZHDate2').value!="")
		{
			addStr3 = " and t.missionprop11<='"+document.all('ZHDate2').value+"'";				
		}
		if(document.all('LAAnswerDate2').value!="")
		{
			addStr4 = " and t.modifydate='"+document.all('LAAnswerDate2').value+"'";				
		}
             
					
//	  strSQL = strSQLBase + strSQLOther +" group by t.missionprop2 "+ strSQLOrder;		
            			  
     mySql4.addSubPara(strSQLOther);//ָ������Ĳ���
     mySql4.addSubPara(addStr3);//ָ������Ĳ���
     mySql4.addSubPara(addStr4);//ָ������Ĳ���
     mySql4.addSubPara(strSQLOrder);//ָ������Ĳ���
	 strSQL=mySql4.getString();	
     turnPage2.queryModal(strSQL, PolGrid);  

}


function NeweasyQueryClick()
{
	var strOperate="like";	
	//2008-12-8 ln modify
	
		 var sqlid5="RnewSecUWAllSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("xb.RnewSecUWAllSql"); //ָ��ʹ�õ�properties�ļ���
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
	
		var sqlid6="RnewSecUWAllSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("xb.RnewSecUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(operator);//ָ������Ĳ���
	    sql=mySql6.getString();	
	
//	sql="select comcode From lduser where usercode='"+operator+"'";
	var com=easyExecSql(sql);
	// ��дSQL���
	var strSQL ="";
	var tSql = "";
	k++;
	var UWState2 = document.all('UWState2').value;
	//xiongzh 2008-12-30 modify
	//����vipҵ��Ա
	var addStri1 = " and 1=1 ";
	var addStri2 = " ";
	var addStri3 = " and 3=3 ";
	var addStri4 = " and 4=4 ";
	
	strSQLBase = "select t.missionprop2 contno,'' vip,'' star,min(missionprop10) insuredname,min(missionprop11) zhdate, min(maketime)  maketime, "
      +" ( case min(missionprop12) when '1' then '' else min(missionprop14) end) modifytime,(case min(missionprop12) when '1' then '' else  min(missionprop15) end) modifydate, "
      +" min(missionprop7) appntno,min(missionprop6)  managecom,min(lastoperator)  l_operator,min(missionid)  missionid, "
      +" min(submissionid)  submissionid,min(activityid)  activityid,min(ActivityStatus)  ActivityStatus, "
      +" min(missionprop1)  prtno,count(1) "
      ;       
     strSQLOther = " from lwmission t where 1=1 and  activityid='0000007001' and defaultoperator='"+operator+"' "
		+ getWherePart('t.MissionProp2','ContNo2',strOperate)
		+ getWherePart('t.MissionProp7','CustomerNo2',strOperate)
		+ getWherePart('t.MissionProp8','AppntName2',strOperate)
		+ getWherePart('t.MissionProp10','InsurName2',strOperate)
    + getWherePart('t.MissionProp6','ManageCom2',strOperate)
    + " and t.missionprop6 like '"+com+"%%'" ;
      
    if(UWState2=='1')//δ��������
    {
    	addStri1 = " and not exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus<>'1') ";
    	
    	addStri2 =  " order by vip,star,zhdate,maketime  ";
    }
    else if(UWState2=='3')//�����У�δ�ظ�
    {
    	addStri1 = " and exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus='2' and x.missionprop12='3') "
      //+" and exists(select 1 from lcuwmaster a where a.contno=t.missionprop2 and a.polno=t.missionprop3  "
      //+"   and (a.healthflag in ('1','2') or a.reportflag in ('1','2') or a.printflag in ('1','2')) )"
      ;
      
      addStri2 =  " order by modifydate ,modifytime  ";
    }
    else if(UWState2=='2') //�ѻظ�
    {
    	addStri1 =  " and exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus='2' and x.missionprop12='2') "
      //+" and exists(select 1 from lcuwmaster a where a.contno=t.missionprop2 and a.polno=t.missionprop3  "
      //+"   and a.healthflag in ('0','3') and a.reportflag in ('0','3') and a.printflag in ('0','3')) "
      ;
      
      addStri2 =  " order by vip,star,modifydate ,modifytime ,zhdate,maketime  ";
    }else{
      addStri2 =  " order by vip,star,modifydate ,modifytime ,zhdate,maketime  ";
    }
    if(document.all('ZHDate2').value!="")
		{
			addStri3 = " and t.missionprop11<='"+document.all('ZHDate2').value+"'";				
		}
		if(document.all('LAAnswerDate2').value!="")
		{
			addStri4 = " and t.modifydate='"+document.all('LAAnswerDate2').value+"'";				
		}
             
					
//	  strSQL = strSQLBase + strSQLOther +" group by t.missionprop2 "+ strSQLOrder;		
            			  
        var sqlid7="RnewSecUWAllSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("xb.RnewSecUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(strSQLBase);//ָ������Ĳ���
		mySql7.addSubPara(strSQLOther);//ָ������Ĳ���
		mySql7.addSubPara(addStri1);//ָ������Ĳ���
		mySql7.addSubPara(addStri3);//ָ������Ĳ���
		mySql7.addSubPara(addStri4);//ָ������Ĳ���
		mySql7.addSubPara(addStri2);//ָ������Ĳ���
	    strSQL=mySql7.getString();	
	    
     turnPage2.queryModal(strSQL, PolGrid);  
}
function easyQueryClickAll()
{
	//alert(operFlag);
	// ��дSQL���
	var strSQL ="";
	var strSQLBase ="";
	var strSQLOther ="";
	var strSQLOrder="";
	var addStri1 = " and 1=1 ";
	var addStri2 = " ";
	var addStri3 = " and 3=3 ";
	var addStri4 = " and 4=4 ";
	
	var strOperate="like";
	//liuning 2008-06-10 modify
	var UWState1 = document.all('UWState1').value;
   
	//alert(operFlag+withdrawFlag);
	//tongmeng 2007-10-22 modify
	//���Ӻ˱�״̬��ѯ
	 //SurpassGradeFlag��Ȩ�޼���UWProcessFlag�Ƿ����м�˱�Ȩ�� 2008-12-8 ln modify
//		var sql="select ( case SurpassGradeFlag when '' then uwpopedom else SurpassGradeFlag end) from lduwuser where usercode='"+operator+"' and uwtype='1'";
		var sqlid9="RnewSecUWAllSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("xb.RnewSecUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
		mySql9.addSubPara(operator);//ָ������Ĳ���
	    var sql=mySql9.getString();
		var Result = easyExecSql(sql);
		if(Result==null)
		{
			alert("�ú˱�Ա"+operator+"û�ж���˱�����");
			return;
		}
//		sql="select comcode from lduser where usercode='"+operator+"'";
		var sqlid10="RnewSecUWAllSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("xb.RnewSecUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
		mySql10.addSubPara(operator);//ָ������Ĳ���
	    sql=mySql10.getString();
		var com=easyExecSql(sql);
		//xiongzh 2008-12-29 modify
    strSQLBase = "select t.missionprop2 contno,'' vip,'' star,min(missionprop10) insuredname,min(missionprop11) zhdate, min(maketime)  maketime, "
      +" (case min(missionprop12) when '1' then '' else  min(missionprop14) end) modifytime,(case min(missionprop12) when '1' then '' else  min(missionprop15) end) modifydate, "
      +" min(missionprop7) appntno,min(missionprop6)  managecom,min(lastoperator)  l_operator,min(missionid)  missionid, "
      +" min(submissionid)  submissionid,min(activityid)  activityid,min(ActivityStatus)  ActivityStatus, "
      +" min(missionprop1)  prtno,count(1) "
      ;       
                      
     strSQLOther = " from lwmission t where 1=1 and  activityid='0000007001' and defaultoperator is null"
     +" and not exists(select 1 from lwmission  where 1=1 and  activityid='0000007001' and missionprop2=t.missionprop2 and defaultoperator is not null)"
		+ getWherePart('t.MissionProp2','ContNo1',strOperate)
		+ getWherePart('t.MissionProp7','CustomerNo1',strOperate)
		+ getWherePart('t.MissionProp8','AppntName1',strOperate)
		+ getWherePart('t.MissionProp10','InsurName1',strOperate)
    + getWherePart('t.MissionProp6','ManageCom1',strOperate)
    + " and t.missionprop6 like '"+com+"%%'" ;
    if(UWState1=='1')//δ��������
    {
    	addStri1 = " and not exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus<>'1') ";
    	
    	addStri2 =  " order by vip,star,zhdate,maketime  ";
    }
    else if(UWState1=='3')//�����У�δ�ظ�
    {
    	addStri1 = " and exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus='2' and x.missionprop12='3') "
      //+" and exists(select 1 from lcuwmaster a where a.contno=t.missionprop2 and a.polno=t.missionprop3  "
      //+"   and (a.healthflag in ('1','2') or a.reportflag in ('1','2') or a.printflag in ('1','2')) )"
      ;
      
      addStri2 =  " order by modifydate ,modifytime  ";
    }
    else if(UWState1=='2') //�ѻظ�
    {
    	addStri1 = " and exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus='2' and x.missionprop12='2') "
      //+" and exists(select 1 from lcuwmaster a where a.contno=t.missionprop2 and a.polno=t.missionprop3  "
      //+"   and a.healthflag in ('0','3') and a.reportflag in ('0','3') and a.printflag in ('0','3')) "
      ;
      
      addStri2 =  " order by vip,star,modifydate ,modifytime ,zhdate,maketime  ";
    }else{
      addStri2 =  " order by vip,star,modifydate ,modifytime ,zhdate,maketime  ";
    }
    if(document.all('ZHDate1').value!="")
		{
			addStri3 = " and t.missionprop11<='"+document.all('ZHDate1').value+"'";				
		}
		if(document.all('LAAnswerDate1').value!="")
		{
			addStri4 = " and t.modifydate='"+document.all('LAAnswerDate1').value+"'";				
		}
             	
//	  strSQL = strSQLBase + strSQLOther +" group by t.missionprop2 " + strSQLOrder;			
		//prompt('',strSQL);
			
			//��ѯ�����������ܹ��ж��ٵ���
//		   var sumsql = "select count(distinct(t.missionprop2)) "+ strSQLOther + addStri1 + addStri3 + addStri4 ;
		   var sqlid11 = "RnewSecUWAllSql11";
			var mySql11 = new SqlClass();
			mySql11.setResourceName("xb.RnewSecUWAllSql"); // ָ��ʹ�õ�properties�ļ���
			mySql11.setSqlId(sqlid11);// ָ��ʹ�õ�Sql��id
			mySql11.addSubPara(strSQLOther);// ָ������Ĳ���
			mySql11.addSubPara(addStri1);// ָ������Ĳ���
			mySql11.addSubPara(addStri3);// ָ������Ĳ���
			mySql11.addSubPara(addStri4);// ָ������Ĳ���
			sumsql = mySql11.getString();
		   var sum = easyExecSql(sumsql);
		   if(sum!=null && sum!="")
		       document.all('PolSum').value = sum;
		   else
		       document.all('PolSum').value = "0";

		var sqlid8="RnewSecUWAllSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("xb.RnewSecUWAllSql"); //ָ��ʹ�õ�properties�ļ���
		mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
		mySql8.addSubPara(strSQLBase);//ָ������Ĳ���
		mySql8.addSubPara(strSQLOther);//ָ������Ĳ���
		mySql8.addSubPara(addStri1);//ָ������Ĳ���
		mySql8.addSubPara(addStri3);//ָ������Ĳ���
		mySql8.addSubPara(addStri4);//ָ������Ĳ���
		mySql8.addSubPara(addStri2);//ָ������Ĳ���
	    strSQL=mySql8.getString();	
		
		turnPage.queryModal(strSQL, AllPolGrid); 
		
		withdrawFlag = false; 
}

/*********************************************************************
 *  ִ������Լ�˹��˱���EasyQueryAddClick
 *  ����:����˱�����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
 /*
function easyQueryAddClick()
{	
	var tSel = AllPolGrid.getSelNo();	   
	var PrtNo = AllPolGrid.getRowColData(tSel - 1,16);        //ӡˢ��
	var ContNo = AllPolGrid.getRowColData(tSel - 1,1);        //��ͬ��
	var MissionID = AllPolGrid.getRowColData(tSel - 1,12);     //�����������
	var SubMissionID = AllPolGrid.getRowColData(tSel - 1,13);  //�������������
	var activityid = AllPolGrid.getRowColData(tSel - 1,14);    //�������Id 
	var UWState = AllPolGrid.getRowColData(tSel - 1,15);
	
  window.open("./RnewUWManuInputMain.jsp?PrtNo="+PrtNo+"&ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&ActivityStatus="+UWState,"",sFeatures);


}

*/
function easyQueryAddClick()
{	
	var selno = PublicWorkPoolGrid.getSelNo()-1;	
	if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }   
	var PrtNo = PublicWorkPoolGrid.getRowColData(selno,12);        //ӡˢ��  
	var ContNo = PublicWorkPoolGrid.getRowColData(selno,1);        //��ͬ��
	var MissionID = PublicWorkPoolGrid.getRowColData(selno,22);     //�����������
	var SubMissionID = PublicWorkPoolGrid.getRowColData(selno,23);  //�������������
	var activityid = PublicWorkPoolGrid.getRowColData(selno,25);    //�������Id  
	var UWState = PublicWorkPoolGrid.getRowColData(selno,17);
  window.open("./RnewUWManuInputMain.jsp?PrtNo="+PrtNo+"&ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&ActivityStatus="+UWState,"",sFeatures);


}
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
/*
function IniteasyQueryAddClick()
{
	var tSel = PolGrid.getSelNo();
	var activityid = PolGrid.getRowColData(tSel - 1,14);    //�������Id  
	var PrtNo = PolGrid.getRowColData(tSel - 1,16);        //ӡˢ��  
	var ContNo = PolGrid.getRowColData(tSel - 1,1);        //��ͬ��
	var MissionID = PolGrid.getRowColData(tSel - 1,12);     //�����������
	var SubMissionID = PolGrid.getRowColData(tSel - 1,13);  //�������������
	var UWState = PolGrid.getRowColData(tSel - 1,15);
	//alert(ReportFlag);
  window.open("./RnewUWManuInputMain.jsp?PrtNo="+PrtNo+"&ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&ActivityStatus="+UWState,"",sFeatures);
}*/
function IniteasyQueryAddClick()
{
	//var tSel = PrivateWorkPoolGrid.getSelNo();
	var selno = PrivateWorkPoolGrid.getSelNo()-1;
    if (selno <0)
    {
          alert("��ѡ��Ҫ���������");
          return;
    }
	//alert(PrivateWorkPoolGrid.getRowData(selno));
	var activityid = PrivateWorkPoolGrid.getRowColData(selno,24);    //�������Id  
	var PrtNo = PrivateWorkPoolGrid.getRowColData(selno,11);        //ӡˢ��  
	var ContNo = PrivateWorkPoolGrid.getRowColData(selno,1);        //��ͬ��
	var MissionID = PrivateWorkPoolGrid.getRowColData(selno,21);     //�����������
	var SubMissionID = PrivateWorkPoolGrid.getRowColData(selno,22);  //�������������
	var UWState = PrivateWorkPoolGrid.getRowColData(selno,17);
	//alert(ReportFlag);
  window.open("./RnewUWManuInputMain.jsp?PrtNo="+PrtNo+"&ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&ActivityStatus="+UWState,"",sFeatures);
}

/**
 * ���빲�������ҵ�����
 */
 function applyTheMission()
{
    var nSelNo;
    try
    {
        nSelNo = PublicWorkPoolGrid.getSelNo() - 1;
       // alert(PublicWorkPoolGrid.getRowData(nSelNo));
        
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0){
        alert("����ѡ����Ҫ��������� ");
        return;
    }
    else
    {
        var sMissionID, sSubMissionID, sActivityID;
        try
        {
            sMissionID = PublicWorkPoolGrid.getRowColData(nSelNo, 22);
            sSubMissionID = PublicWorkPoolGrid.getRowColData(nSelNo, 23);
            sActivityID = PublicWorkPoolGrid.getRowColData(nSelNo, 25);
        }
        catch (ex) {}
        if (sMissionID == null || trim(sMissionID) == "")
        {
            alert("���Ȳ�ѯ��ѡ��һ������ ");
            return;
        }
        var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
        //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        //����ͨ������ Mission ҳ��
        //����ҵ������Ҫ�ڵ�������ֱ�ӽ���˱�����������棬����Ҫ����ز�������
        applyPrtNo = PublicWorkPoolGrid.getRowColData(nSelNo, 12);
		applyContNo = PublicWorkPoolGrid.getRowColData(nSelNo, 1);
		applyMissionID = PublicWorkPoolGrid.getRowColData(nSelNo, 22);
		applySubMissionID = PublicWorkPoolGrid.getRowColData(nSelNo, 23);
		applyUWState = PublicWorkPoolGrid.getRowColData(nSelNo, 17);
                
        document.forms(0).action = "../bq/MissionApply.jsp?MissionID=" + sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityID=" + sActivityID;
        document.forms(0).submit();
    } //nSelNo > 0
}
 /*
function applyTheMission()
{
    var nSelNo;
    try
    {
        nSelNo = AllPolGrid.getSelNo() - 1;
    }
    catch (ex) {}
    if (nSelNo == null || nSelNo < 0)
    {
        alert("����ѡ����Ҫ��������� ");
        return;
    }
    else
    {
        var sMissionID, sSubMissionID, sActivityID;
        try
        {
            sMissionID = AllPolGrid.getRowColData(nSelNo, 12);
            sSubMissionID = AllPolGrid.getRowColData(nSelNo, 13);
            sActivityID = '0000007001';
        }
        catch (ex) {}
        if (sMissionID == null || trim(sMissionID) == "")
        {
            alert("���Ȳ�ѯ��ѡ��һ������ ");
            return;
        }
        var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
        showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
        //����ͨ������ Mission ҳ��
        //����ҵ������Ҫ�ڵ�������ֱ�ӽ���˱�����������棬����Ҫ����ز�������
        applyPrtNo = AllPolGrid.getRowColData(nSelNo, 16);
		applyContNo = AllPolGrid.getRowColData(nSelNo, 1);
		applyMissionID = AllPolGrid.getRowColData(nSelNo, 12);
		applySubMissionID = AllPolGrid.getRowColData(nSelNo, 13);
		applyUWState = AllPolGrid.getRowColData(nSelNo, 15);
                
        document.forms(0).action = "../bq/MissionApply.jsp?MissionID=" + sMissionID + "&SubMissionID=" + sSubMissionID + "&ActivityID=" + sActivityID;
        document.forms(0).submit();
    } //nSelNo > 0
}

*/
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
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		//easyQueryClick();
		jQuery("#publicSearch").click();
		jQuery("#privateSearch").click();
		//easyQueryClickAll();//ˢ��ҳ��
	
  }
  else
  { 
	  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		//easyQueryAddClick();//ִ����һ������
		//easyQueryClickAll();
		jQuery("#publicSearch").click();
		//if(operFlag != "3")
		NeweasyQueryClick();
	   window.open("../xb/RnewUWManuInputMain.jsp?PrtNo="+applyPrtNo+"&ContNo="+applyContNo+"&MissionID="+applyMissionID+"&SubMissionID="+applySubMissionID+"&ActivityID=0000007001&ActivityStatus="+applyUWState,"",sFeatures);
  }

}
function privateWorkPoolQuery(){
	jQuery("#privateSearch").click();
}
function showNotePad()
{

	var selno = PolGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("��ѡ��һ������");
	      return;
	}
	
	var MissionID = PolGrid.getRowColData(selno, 12);
	var SubMissionID = PolGrid.getRowColData(selno, 13);
	var ActivityID = PolGrid.getRowColData(selno, 14);
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


function queryAgent()
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

function afterQuery2(arrResult)
{
  
  if(arrResult!=null)
  {
    	fm.AgentCode.value = arrResult[0][0];
  	 // fm.AgentCode1.value = arrResult[0][0];
  }
}

function afterQuery3(arrResult)
{
  
  if(arrResult!=null)
  {
  	  fm.AgentCode1.value = arrResult[0][0];
  }
}