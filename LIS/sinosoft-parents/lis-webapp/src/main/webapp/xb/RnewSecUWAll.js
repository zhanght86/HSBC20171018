//程序名称：ManuUWAll.js
//程序功能：个人人工核保
//创建日期：2005-01-24 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
var mDebug="0";
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var turnPagetest = new turnPageClass();
//公共池申请到个人池的任务相关变量
var applyPrtNo ;
var applyContNo ;
var applyMissionID ;
var applySubMissionID ;
var applyUWState ;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var k = 0;

/*********************************************************************
 *  执行新契约人工核保的EasyQuery
 *  描述:查询显示对象是合同保单.显示条件:合同未进行人工核保，或状态为待核保
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 
var withdrawFlag = false;
//撤单申请查询,add by Minim
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
		mySql1.setResourceName("xb.RnewSecUWAllSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(operator);//指定传入的参数
	    var sql=mySql1.getString();	
	
//  var sql="select decode(SurpassGradeFlag,'',uwpopedom,SurpassGradeFlag) From LDUWUser where usercode='"+operator+"' and uwtype='1'";
	var Result = easyExecSql(sql);	
	// 书写SQL语句
	var strSQL ="";
	var strSQLBase="";
	var strSQLOther=" and 1=1 ";
	var strSQLOrder=" and 2=2 ";
	var addStr3 =" and 3=3 ";
	var addStr4 =" and 4=4 ";
	k++;
	
		var sqlid2="RnewSecUWAllSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("xb.RnewSecUWAllSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(operator);//指定传入的参数
	    tSql=mySql2.getString();	
	
//	tSql="select comcode From lduser where usercode='"+operator+"'";
	var com=easyExecSql(tSql);
	var UWState2 = document.all('UWState2').value;
	//alert(operator);
/*
		var sqlid3="RnewSecUWAllSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("xb.RnewSecUWAllSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
//		mySql3.addSubPara(operator);//指定传入的参数
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
		mySql4.setResourceName("xb.RnewSecUWAllSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(strSQLBase);//指定传入的参数
		mySql4.addSubPara(operator);//指定传入的参数
		mySql4.addSubPara(fm.ContNo2.value);//指定传入的参数
		mySql4.addSubPara(fm.CustomerNo2.value);//指定传入的参数
		mySql4.addSubPara(fm.AppntName2.value);//指定传入的参数
		
		mySql4.addSubPara(fm.InsurName2.value);//指定传入的参数
		mySql4.addSubPara(fm.ManageCom2.value);//指定传入的参数
		mySql4.addSubPara(com);//指定传入的参数
//	    strSQLBase=mySql4.getString();	
	  
//     strSQLOther = " from lwmission t where 1=1 and  activityid='0000007001' and defaultoperator='"+operator+"' "
//		+ getWherePart('t.MissionProp2','ContNo2',strOperate)
//		+ getWherePart('t.MissionProp7','CustomerNo2',strOperate)
//		+ getWherePart('t.MissionProp8','AppntName2',strOperate)
//		+ getWherePart('t.MissionProp10','InsurName2',strOperate)
//    + getWherePart('t.MissionProp6','ManageCom2',strOperate)
//    + " and t.missionprop6 like '"+com+"%%'" ;
      
    if(UWState2=='1')//未处理任务
    {
    	strSQLOther= " and not exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus<>'1') ";
    	
    	strSQLOrder =  " order by vip,star,zhdate,maketime  ";
    }
    else if(UWState2=='3')//处理中，未回复
    {
    	strSQLOther= " and exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus='2' and x.missionprop12='3') "
      //+" and exists(select 1 from lcuwmaster a where a.contno=t.missionprop2 and a.polno=t.missionprop3  "
      //+"   and (a.healthflag in ('1','2') or a.reportflag in ('1','2') or a.printflag in ('1','2')) )"
      ;
      
      strSQLOrder =  " order by modifydate ,modifytime  ";
    }
    else if(UWState2=='2')//已回复
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
            			  
     mySql4.addSubPara(strSQLOther);//指定传入的参数
     mySql4.addSubPara(addStr3);//指定传入的参数
     mySql4.addSubPara(addStr4);//指定传入的参数
     mySql4.addSubPara(strSQLOrder);//指定传入的参数
	 strSQL=mySql4.getString();	
     turnPage2.queryModal(strSQL, PolGrid);  

}


function NeweasyQueryClick()
{
	var strOperate="like";	
	//2008-12-8 ln modify
	
		 var sqlid5="RnewSecUWAllSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("xb.RnewSecUWAllSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(operator);//指定传入的参数
	    var sql=mySql5.getString();	
	
//	var sql="select decode(SurpassGradeFlag,'',uwpopedom,SurpassGradeFlag) From LDUWUser where usercode='"+operator+"' and uwtype='1'";
	var Result = easyExecSql(sql);
	if(Result==null)
	{
			alert("该核保原员"+operator+"没有定义核保级别");
			return;
	}
	
		var sqlid6="RnewSecUWAllSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("xb.RnewSecUWAllSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(operator);//指定传入的参数
	    sql=mySql6.getString();	
	
//	sql="select comcode From lduser where usercode='"+operator+"'";
	var com=easyExecSql(sql);
	// 书写SQL语句
	var strSQL ="";
	var tSql = "";
	k++;
	var UWState2 = document.all('UWState2').value;
	//xiongzh 2008-12-30 modify
	//屏蔽vip业务员
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
      
    if(UWState2=='1')//未处理任务
    {
    	addStri1 = " and not exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus<>'1') ";
    	
    	addStri2 =  " order by vip,star,zhdate,maketime  ";
    }
    else if(UWState2=='3')//处理中，未回复
    {
    	addStri1 = " and exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus='2' and x.missionprop12='3') "
      //+" and exists(select 1 from lcuwmaster a where a.contno=t.missionprop2 and a.polno=t.missionprop3  "
      //+"   and (a.healthflag in ('1','2') or a.reportflag in ('1','2') or a.printflag in ('1','2')) )"
      ;
      
      addStri2 =  " order by modifydate ,modifytime  ";
    }
    else if(UWState2=='2') //已回复
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
		mySql7.setResourceName("xb.RnewSecUWAllSql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(strSQLBase);//指定传入的参数
		mySql7.addSubPara(strSQLOther);//指定传入的参数
		mySql7.addSubPara(addStri1);//指定传入的参数
		mySql7.addSubPara(addStri3);//指定传入的参数
		mySql7.addSubPara(addStri4);//指定传入的参数
		mySql7.addSubPara(addStri2);//指定传入的参数
	    strSQL=mySql7.getString();	
	    
     turnPage2.queryModal(strSQL, PolGrid);  
}
function easyQueryClickAll()
{
	//alert(operFlag);
	// 书写SQL语句
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
	//增加核保状态查询
	 //SurpassGradeFlag超权限级别；UWProcessFlag是否有中间核保权限 2008-12-8 ln modify
//		var sql="select ( case SurpassGradeFlag when '' then uwpopedom else SurpassGradeFlag end) from lduwuser where usercode='"+operator+"' and uwtype='1'";
		var sqlid9="RnewSecUWAllSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName("xb.RnewSecUWAllSql"); //指定使用的properties文件名
		mySql9.setSqlId(sqlid9);//指定使用的Sql的id
		mySql9.addSubPara(operator);//指定传入的参数
	    var sql=mySql9.getString();
		var Result = easyExecSql(sql);
		if(Result==null)
		{
			alert("该核保员"+operator+"没有定义核保级别");
			return;
		}
//		sql="select comcode from lduser where usercode='"+operator+"'";
		var sqlid10="RnewSecUWAllSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName("xb.RnewSecUWAllSql"); //指定使用的properties文件名
		mySql10.setSqlId(sqlid10);//指定使用的Sql的id
		mySql10.addSubPara(operator);//指定传入的参数
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
    if(UWState1=='1')//未处理任务
    {
    	addStri1 = " and not exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus<>'1') ";
    	
    	addStri2 =  " order by vip,star,zhdate,maketime  ";
    }
    else if(UWState1=='3')//处理中，未回复
    {
    	addStri1 = " and exists(select 1 from lwmission x where x.missionprop2=t.missionprop2 and x.activitystatus='2' and x.missionprop12='3') "
      //+" and exists(select 1 from lcuwmaster a where a.contno=t.missionprop2 and a.polno=t.missionprop3  "
      //+"   and (a.healthflag in ('1','2') or a.reportflag in ('1','2') or a.printflag in ('1','2')) )"
      ;
      
      addStri2 =  " order by modifydate ,modifytime  ";
    }
    else if(UWState1=='2') //已回复
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
			
			//查询共享工作池中总共有多少单子
//		   var sumsql = "select count(distinct(t.missionprop2)) "+ strSQLOther + addStri1 + addStri3 + addStri4 ;
		   var sqlid11 = "RnewSecUWAllSql11";
			var mySql11 = new SqlClass();
			mySql11.setResourceName("xb.RnewSecUWAllSql"); // 指定使用的properties文件名
			mySql11.setSqlId(sqlid11);// 指定使用的Sql的id
			mySql11.addSubPara(strSQLOther);// 指定传入的参数
			mySql11.addSubPara(addStri1);// 指定传入的参数
			mySql11.addSubPara(addStri3);// 指定传入的参数
			mySql11.addSubPara(addStri4);// 指定传入的参数
			sumsql = mySql11.getString();
		   var sum = easyExecSql(sumsql);
		   if(sum!=null && sum!="")
		       document.all('PolSum').value = sum;
		   else
		       document.all('PolSum').value = "0";

		var sqlid8="RnewSecUWAllSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName("xb.RnewSecUWAllSql"); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(strSQLBase);//指定传入的参数
		mySql8.addSubPara(strSQLOther);//指定传入的参数
		mySql8.addSubPara(addStri1);//指定传入的参数
		mySql8.addSubPara(addStri3);//指定传入的参数
		mySql8.addSubPara(addStri4);//指定传入的参数
		mySql8.addSubPara(addStri2);//指定传入的参数
	    strSQL=mySql8.getString();	
		
		turnPage.queryModal(strSQL, AllPolGrid); 
		
		withdrawFlag = false; 
}

/*********************************************************************
 *  执行新契约人工核保的EasyQueryAddClick
 *  描述:进入核保界面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
 /*
function easyQueryAddClick()
{	
	var tSel = AllPolGrid.getSelNo();	   
	var PrtNo = AllPolGrid.getRowColData(tSel - 1,16);        //印刷号
	var ContNo = AllPolGrid.getRowColData(tSel - 1,1);        //合同号
	var MissionID = AllPolGrid.getRowColData(tSel - 1,12);     //工作流任务号
	var SubMissionID = AllPolGrid.getRowColData(tSel - 1,13);  //工作流子任务号
	var activityid = AllPolGrid.getRowColData(tSel - 1,14);    //工作流活动Id 
	var UWState = AllPolGrid.getRowColData(tSel - 1,15);
	
  window.open("./RnewUWManuInputMain.jsp?PrtNo="+PrtNo+"&ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&ActivityStatus="+UWState,"",sFeatures);


}

*/
function easyQueryAddClick()
{	
	var selno = PublicWorkPoolGrid.getSelNo()-1;	
	if (selno <0)
    {
          alert("请选择要处理的任务！");
          return;
    }   
	var PrtNo = PublicWorkPoolGrid.getRowColData(selno,12);        //印刷号  
	var ContNo = PublicWorkPoolGrid.getRowColData(selno,1);        //合同号
	var MissionID = PublicWorkPoolGrid.getRowColData(selno,22);     //工作流任务号
	var SubMissionID = PublicWorkPoolGrid.getRowColData(selno,23);  //工作流子任务号
	var activityid = PublicWorkPoolGrid.getRowColData(selno,25);    //工作流活动Id  
	var UWState = PublicWorkPoolGrid.getRowColData(selno,17);
  window.open("./RnewUWManuInputMain.jsp?PrtNo="+PrtNo+"&ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&ActivityStatus="+UWState,"",sFeatures);


}
function CheckUserCode()
{
    if (document.all('UserCode').value!=null)
       if(document.all('UserCode').value != operator)
        {
           alert("必须输入当前核保员代码！");
           document.all('UserCode').value = "";
           return;
        }
}
/*
function IniteasyQueryAddClick()
{
	var tSel = PolGrid.getSelNo();
	var activityid = PolGrid.getRowColData(tSel - 1,14);    //工作流活动Id  
	var PrtNo = PolGrid.getRowColData(tSel - 1,16);        //印刷号  
	var ContNo = PolGrid.getRowColData(tSel - 1,1);        //合同号
	var MissionID = PolGrid.getRowColData(tSel - 1,12);     //工作流任务号
	var SubMissionID = PolGrid.getRowColData(tSel - 1,13);  //工作流子任务号
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
          alert("请选择要处理的任务！");
          return;
    }
	//alert(PrivateWorkPoolGrid.getRowData(selno));
	var activityid = PrivateWorkPoolGrid.getRowColData(selno,24);    //工作流活动Id  
	var PrtNo = PrivateWorkPoolGrid.getRowColData(selno,11);        //印刷号  
	var ContNo = PrivateWorkPoolGrid.getRowColData(selno,1);        //合同号
	var MissionID = PrivateWorkPoolGrid.getRowColData(selno,21);     //工作流任务号
	var SubMissionID = PrivateWorkPoolGrid.getRowColData(selno,22);  //工作流子任务号
	var UWState = PrivateWorkPoolGrid.getRowColData(selno,17);
	//alert(ReportFlag);
  window.open("./RnewUWManuInputMain.jsp?PrtNo="+PrtNo+"&ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&ActivityStatus="+UWState,"",sFeatures);
}

/**
 * 申请共享任务到我的任务
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
        alert("请先选择您要申请的任务！ ");
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
            alert("请先查询并选择一条任务！ ");
            return;
        }
        var MsgContent = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
        //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        //调用通用申请 Mission 页面
        //由于业务部们需要在点击申请后直接进入核保具体操作界面，现需要将相关参数保存
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
        alert("请先选择您要申请的任务！ ");
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
            alert("请先查询并选择一条任务！ ");
            return;
        }
        var MsgContent = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
        showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
        //调用通用申请 Mission 页面
        //由于业务部们需要在点击申请后直接进入核保具体操作界面，现需要将相关参数保存
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
 *  提交后操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  if (FlagStr == "Fail" )
  {                 
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
		//easyQueryClick();
		jQuery("#publicSearch").click();
		jQuery("#privateSearch").click();
		//easyQueryClickAll();//刷新页面
	
  }
  else
  { 
	  //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		//easyQueryAddClick();//执行下一步操作
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
	      alert("请选择一条任务");
	      return;
	}
	
	var MissionID = PolGrid.getRowColData(selno, 12);
	var SubMissionID = PolGrid.getRowColData(selno, 13);
	var ActivityID = PolGrid.getRowColData(selno, 14);
	var PrtNo = PolGrid.getRowColData(selno, 1);
	var NoType = operFlag;
	if(PrtNo == null || PrtNo == "")
	{
		alert("印刷号为空！");
		return;
	}			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
		
}

/*********************************************************************
 *  选择核保结后的动作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field )
{
	if(fm.UWState2.value=="0" ){
       alert("不能输入0状态！");
       fm.UWState2.value= "";
       fm.UWStateName2.value = "";
   }
}     


function queryAgent()
{   
	//tongmeng 2007-10-17 modify
	//修改销售渠道错误。
	//需要根据保单渠道去确认销售渠道。
	var tSaleChnl = document.all('SaleChnl').value;
	if(tSaleChnl==''||tSaleChnl==null)
	{
		alert('请先选择销售渠道!');
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
		alert('请先选择销售渠道!');
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