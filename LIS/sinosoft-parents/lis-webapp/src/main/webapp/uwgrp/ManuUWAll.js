

var showInfo;
var mDebug="0";
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var k = 0;
var sqlresourcename = "uwgrp.ManuUWAllChkSql";
/*********************************************************************
 *  执行新契约人工核保的EasyQuery
 *  描述:查询显示对象是合同保单.显示条件:合同未进行人工核保，或状态为待核保
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryClick()
{
	//add by yaory
//var sql="select uwpopedom From LDUWUser where usercode='"+operator+"' and uwtype='2'";

        var sqlid1="ManuUWAllChkSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(operator);
	var Result = easyExecSql(mySql1.getString());//核保权限	
	// 书写SQL语句
	var strSQL ="";
	var tSql = "";
	k++;
	//alert(operFlag);

	if(operFlag == "2")
	{
		 if(manageCom == "%"){
		 /*
		 	strSQL = "select decode(A.x, 0, '已点击待核',1, '已回复问题件待核','有未回复问题件'),A.y,A.z,A.m,A.n,A.o,A.p,A.q,A.r,A.s,A.t,A.u,A.v,A.w,A.a,A.b,A.c,A.d,A.e from (select case when (select count(*) from lcgrpissuepol where grpcontno=t.missionprop2 )=0 then 0 when (select count(*) from lcgrpissuepol where grpcontno=t.missionprop2 and replyresult is null )=0 then 1 else 2 end x, "
		 	        +"t.missionprop4 y,t.missionprop2 z,t.missionprop1 m,t.missionprop7 n,t.indate o,case t.activitystatus when '1' then '未人工核保'  when '3' then '核保已回复' when '2' then '核保未回复' when '4' then '再保未回复' when '5' then '再保已回复' end p,case t.activityid when '0000001100' then '新契约' end q,"
                    + " t.missionid r,t.submissionid s,t.activityid t,"
                    + " case t.missionprop11 when '1' then '疑难案例上报'  when '2' then '超权限上报' when '3' then '再保上报' when '4' then '返回下级' end u, "
                    + " t.missionprop12 v,t.missionprop4 w,t.missionprop8 a,t.missionprop13 b,'' c,'' d,t.missionprop11 e"
                    + " from lwmission t where 1=1 "
					+ " and t.activityid in ('0000002004')"
					+ " and (t.defaultoperator ='" + operator + "' or t.missionprop14='" + operator + "')"
//					+ " and t.missionprop14 ='" +operator+ "'"
					+ " and t.MissionProp12<='"+Result+"'"
					+ getWherePart('t.MissionProp2','ContNoSelf','=')			   
			   + getWherePart('t.MissionProp7','AppntNameSelf','like')
			   + getWherePart('t.MissionProp4','ManageCom1','like')
			   + getWherePart('t.MissionProp13','ApproveDateSelf','=')			   
//					+ " and t.MissionProp4 like '"+manageCom+"%%'"
					+ " ) A order by A.x desc";
				*/	
					
		var sqlid2="ManuUWAllChkSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(operator);
		mySql2.addSubPara(operator);
		mySql2.addSubPara(Result);
		mySql2.addSubPara(fm.ContNoSelf.value);
		mySql2.addSubPara(fm.AppntNameSelf.value);
		mySql2.addSubPara(fm.ManageCom1.value);
		mySql2.addSubPara(fm.ApproveDateSelf.value);
					
			strSQL = mySql2.getString();		
		}else{
		/*
		 strSQL ="select decode(A.x, 0, '已点击待核',1, '已回复问题件待核','有未回复问题件'),A.y,A.z,A.m,A.n,A.o,A.p,A.q,A.r,A.s,A.t,A.u,A.v,A.w,A.a,A.b,A.c,A.d,A.e from (select case when (select count(*) from lcgrpissuepol where grpcontno=t.missionprop2 )=0 then 0 when (select count(*) from lcgrpissuepol where grpcontno=t.missionprop2 and replyresult is null )=0 then 1 else 2 end x, "
		        +"t.missionprop4 y,t.missionprop2 z,t.missionprop1 m,t.missionprop7 n,t.indate o,case t.activitystatus when '1' then '未人工核保'  when '3' then '核保已回复' when '2' then '核保未回复' when '4' then '再保未回复' when '5' then '再保已回复' end p,case t.activityid when '0000001100' then '新契约' end q,"
                    + " t.missionid r,t.submissionid s,t.activityid t,"
                    + " case t.missionprop11 when '1' then '疑难案例上报'  when '2' then '超权限上报' when '3' then '再保上报' when '4' then '返回下级' end u, "
                    + " t.missionprop12 v,t.missionprop5 w,t.missionprop8 a,t.missionprop13 b,'' c,'' d,t.missionprop11 e"
                    + " from lwmission t where 1=1 "
					+ " and t.activityid in ('0000002004')"
					+ " and (t.defaultoperator ='" + operator + "' or t.missionprop14='" + operator + "')"

					+ getWherePart('t.MissionProp2','ContNoSelf','=')
			   + getWherePart('t.MissionProp7','AppntNameSelf','like')
			   + getWherePart('t.MissionProp13','ApproveDateSelf','=')
			   + getWherePart('t.MissionProp4','ManageCom1','like')
					+ " and t.MissionProp4 like '"+manageCom+"%%'"
					+ " ) A order by A.x desc";
				*/
				var sqlid3="ManuUWAllChkSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(operator);
		mySql3.addSubPara(operator);
		mySql3.addSubPara(fm.ContNoSelf.value);
		mySql3.addSubPara(fm.AppntNameSelf.value);
		mySql3.addSubPara(fm.ApproveDateSelf.value);
		mySql3.addSubPara(fm.ManageCom1.value);	
		mySql3.addSubPara(manageCom);
			strSQL = mySql3.getString();	
				
				
				}
			
	}
	if(operFlag == "3")
	{
	/*
	
		 strSQL = "select t.missionprop2,t.missionprop1,t.missionprop7,case t.activitystatus when '1' then '未人工核保'  when '3' then '核保已回复' when '2' then '核保未回复' when '4' then '再保未回复' when '5' then '再保已回复' end,case t.activityid when '0000006004' then '询价' end,t.missionid,t.submissionid,t.activityid from lwmission t where 1=1 "
					+ " and t.activityid in ('0000006004')"
					+ " and t.defaultoperator ='" + operator + "'"
//					+ " and t.MissionProp12<='"+Result+"'"
					+ " order by t.modifydate asc,t.modifytime asc";				
		*/		
				var sqlid4="ManuUWAllChkSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(operator);
		
			strSQL = mySql4.getString();		
	}
	turnPage.queryModal(strSQL, SelfGrpGrid);

}

function easyQueryClickAll()
{
	//add by yaory

	//alert(operFlag);
	// 书写SQL语句
	var strSQL ="";
	var strOperate="like";
	var addStr = " and 1=1 ";
	//alert(operFlag);
		
	if(operFlag == "2")
	{
		  
			//var sql="select uwpopedom From LDUWUser where usercode='"+operator+"' and uwtype='2'";
			
		var sqlid5="ManuUWAllChkSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(operator);
		
    	var Result = easyExecSql(mySql5.getString());
	  // sql="select comcode From lduser where usercode='"+operator+"'";
	   
	   var sqlid6="ManuUWAllChkSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(operator);
	   
	   var com=easyExecSql(mySql6.getString());
	   //document.all('ManageCom').value = com;
	/*
	strSQL = "select t.missionprop1,t.missionprop2,t.missionprop7,"
                  	+ " case (select riskflag from lmriskapp where riskcode = (select riskcode from lcgrppol where grpcontno=t.missionprop1 and rownum=1)) when ('A') then '短险' when 'B' then '短险' when 'C' then '短险' when 'D' then '短险' when 'L' then '长险' end ,"
	                  + " case t.activitystatus when '1' then '未人工核保'  when '3' then '核保已回复' when '2' then '核保未回复' when '4' then '再保未回复' when '5' then '再保已回复' end,case t.activityid when '0000002004' then '新契约' end,"
                    + " t.missionid,t.submissionid,t.activityid ,"
                    + " case t.missionprop11 when '0' then '新契约核保' when '1' then '疑难案例上报'  when '2' then '超权限上报' when '3' then '再保上报' when '4' then '返回下级' when '6' then '赠送保单'  end, "
                    + " t.missionprop5,t.missionprop15,t.missionprop13,t.missionprop11 ,"
										//tongmeng 2008-07-23 modify
                    //注释掉vip标志
                    + " '' VIPProperty ,"
                    + " (select vipvalue from ldperson  where customerno = t.missionprop6) Vipvalue "
//                    + " case (select riskflag from lmriskapp where riskcode = (select riskcode from lcgrppol where grpcontno=t.missionprop1 and rownum=1)) when ('A') then '短险' when 'B' then '短险' when 'C' then '短险' when 'D' then '短险' when 'L' then '长险' end "
                    + " from lwmission t where 1=1 "
					+ " and t.activityid in ('0000002004')"
					//+ " and defaultoperator is null ";
					//if(fm.VIPCustomer.value = "")					
					//{
			    //  strSQL = 	strSQL	+ " and trim(missionprop6) in (select trim(customerno) from ldperson where vipvalue like '%"+fm.VIPCustomer.value+"')";
		      //}
			    //if(fm.StarAgent.value = "")			
			    //{
					//
				  //  strSQL = strSQL+ " and trim(missionprop4) in (select trim(agentcode) from latree where vipproperty like '%"+fm.StarAgent.value+"' )"
			    //}
			    //
			*/    
/*			    
		var sqlid7="ManuUWAllChkSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara("1");
		
		strSQL =mySql7.getString();  
*/			    
			    
			    
			    
			    
			    
			    
			    
			    
 // alert(comcode+":"+manageCom);
	//tongmeng 2009-04-23 modify
	//如果登陆机构是总公司的话,那么只查询上报到总公司的数据.
  //如果登陆机构是分公司,那么查询当前机构下,正常和上报的数据
  if(trim(comcode).length==2)
  {
  	addStr = " and (MissionProp17 is not null and MissionProp17='0' ) "; 
  }
  else
  {
  	addStr = " and (MissionProp17 is null or MissionProp17='1' ) "; 
  }      
			     /*
		strSQL = strSQL + getWherePart('t.MissionProp2','ContNo',strOperate)
			   //+ getWherePart('t.MissionProp4','AgentCode',strOperate)
			   + getWherePart('t.MissionProp4','ManageCom','like')
			   + getWherePart('t.MissionProp7','AppntName',strOperate)
			   + getWherePart('t.MissionProp13','ApproveDate',strOperate)
			   + " and t.MissionProp4 like '"+manageCom+"%%'"
			   //+ getWherePart('t.MissionProp12','UWAuthority','<=')
			   //+ " and t.MissionProp12<='"+Result+"'" 			  
			   + " and (t.MissionProp14 is null or t.MissionProp14='0000000000')"
				 + " order by t.modifydate asc,t.modifytime asc";
			//	fm.AppntName.value = strSQL;
			//alert(strSQL);
			*/
			var sqlid8="ManuUWAllChkSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(addStr);
		mySql8.addSubPara(fm.ContNo.value);
		mySql8.addSubPara(fm.ManageCom.value);
		mySql8.addSubPara(fm.AppntName.value);
		mySql8.addSubPara(fm.ApproveDate.value);
		mySql8.addSubPara(manageCom);
		
		strSQL = mySql8.getString();
	}
	if(operFlag == "3")
	{
	/*
		 strSQL = "select t.missionprop2,t.missionprop1,t.missionprop7,case t.activitystatus when '1' then '未人工核保'  when '3' then '核保已回复' when '2' then '核保未回复' when '4' then '再保未回复' when '5' then '再保已回复' end,case t.activityid when '0000006004' then '询价' end,t.missionid,t.submissionid,t.activityid from lwmission t where 1=1 "
					+ " and t.activityid in ('0000006004')"
					+ " and defaultoperator is null "
					+ getWherePart('t.MissionProp2','ContNo',strOperate)
			   		+ getWherePart('t.MissionProp4','AgentCode',strOperate)
			   		//+ getWherePart('t.MissionProp10','ManageCom',strOperate)
			   		+ getWherePart('t.MissionProp7','AppntName',strOperate)
			   		+ getWherePart('t.MissionProp13','ApproveDate',strOperate)
			   		//+ " and t.MissionProp12<='"+Result+"'" 
			   		+ " and t.MissionProp10 like '"+com+"%'"
					+ " order by t.modifydate asc,t.modifytime asc";	
			*/					
		var sqlid9="ManuUWAllChkSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename);
		mySql9.setSqlId(sqlid9);
		mySql9.addSubPara(fm.ContNo.value);
		mySql9.addSubPara(fm.AgentCode.value);
		mySql9.addSubPara(fm.AppntName.value);
		mySql9.addSubPara(fm.ApproveDate.value);
		mySql9.addSubPara(com);	
		strSQL = mySql9.getString();
	}

  turnPage2.queryModal(strSQL, GrpGrid);  
 // easyQueryClick();
}

/*********************************************************************
 *  执行新契约人工核保的EasyQueryAddClick
 *  描述:进入核保界面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function easyQueryAddClick()
{
	
	var tSel = GrpGrid.getSelNo();
	var activityid = GrpGrid.getRowColData(tSel - 1,9);    //工作流活动Id    
	var ContNo = GrpGrid.getRowColData(tSel - 1,2);        //合同投保单号
	var PrtNo = GrpGrid.getRowColData(tSel - 1,1);         //印刷号
	var MissionID = GrpGrid.getRowColData(tSel - 1,7);     //工作流任务号
	var SubMissionID = GrpGrid.getRowColData(tSel - 1,8);  //工作流子任务号
	
	var ReportFlag = GrpGrid.getRowColData(tSel - 1,16);    //上报标志
	
	if(activityid == "0000001100")
	{
		window.location="./UWManuInputMain.jsp?ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo+"&ReportFlag="+ReportFlag;
	}
	if(activityid == "0000002004")
	{
		window.location="./GroupUWMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&NoType=2";
	}
	if(activityid == "0000006004")
	{
		window.location="../askapp/AskGroupUW.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid; 
	}
}



function IniteasyQueryAddClick()
{

	var tSel = SelfGrpGrid.getSelNo();
	var activityid = SelfGrpGrid.getRowColData(tSel - 1,11);    //工作流活动Id    
	
	var ContNo = SelfGrpGrid.getRowColData(tSel - 1,3);        //合同投保单号
	var PrtNo = SelfGrpGrid.getRowColData(tSel - 1,4);         //印刷号
	var MissionID = SelfGrpGrid.getRowColData(tSel - 1,9);     //工作流任务号
	var SubMissionID = SelfGrpGrid.getRowColData(tSel - 1,11);  //工作流子任务号
	
	var ReportFlag = SelfGrpGrid.getRowColData(tSel - 1,19);     //上报标志
	
	var NoType = operFlag;
	
	if(activityid == "0000001100")
	{
		window.location="./UWManuInputMain.jsp?ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo+"&ActivityID="+activityid+"&NoType="+NoType+"&ReportFlag="+ReportFlag;
	}
	if(activityid == "0000002004")
	{
		window.location="./GroupUWMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&NoType="+NoType;
	}
	if(activityid == "0000006004")
	{
		window.location="../askapp/AskGroupUW.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid; 
	}
}


//团单申请录入

function GrpIniteasyQueryAddClick()
{

	var tSel = SelfGrpGrid.getSelNo();
	var activityid = SelfGrpGrid.getRowColData(tSel - 1,11);    //工作流活动Id    
	
	var ContNo = SelfGrpGrid.getRowColData(tSel - 1,4);        //合同投保单号
	
	var PrtNo = SelfGrpGrid.getRowColData(tSel - 1,3);         //印刷号
	var MissionID = SelfGrpGrid.getRowColData(tSel - 1,9);     //工作流任务号
	var SubMissionID = SelfGrpGrid.getRowColData(tSel - 1,10);  //工作流子任务号
	
	//var ReportFlag = SelfGrpGrid.getRowColData(tSel - 1,16);     //上报标志
	
	var NoType = operFlag;
	
	if(activityid == "0000001100")
	{
		window.location="./UWManuInputMain.jsp?ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo+"&ActivityID="+activityid+"&NoType="+NoType+"&ReportFlag="+ReportFlag;
	}
	if(activityid == "0000002004")
	{
		window.location="./GroupUWMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid+"&NoType="+NoType;
	}
	if(activityid == "0000006004")
	{
		window.location="../askapp/AskGroupUW.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+activityid; 
	}
}
/*********************************************************************
 *  申请核保
 *  描述:进入核保界面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function ApplyUW()
{
	var selno = GrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择要申请的投保单！");
	      return;
	}

////增加再保险未处理不能人工核保(再保险程序未上线,先不执行此检查)
//	var PrtNo = GrpGrid.getRowColData(selno ,1);  
//	var sql = "select count(*) from lwmission where missionprop1='"+PrtNo+"' and missionprop11='3'";
//	    sql = sql + " and exists (select * from LWReLifeTrace where RelifeState in ('2','3','5')  and otherno='"+PrtNo+"')";
//	var num = easyExecSql(sql);
//	if (num>=1)
//	{
//  	alert("投保单 "+PrtNo+" 正处于再保险待处理状态,不能进行人工核保!");
//  	return;
//	}

  //增加权限控制
  //var sql="select uwpopedom From LDUWUser where usercode='"+operator+"' and uwtype='2'";
  
  var sqlid10="ManuUWAllChkSql10";
		var mySql10=new SqlClass();
		mySql10.setResourceName(sqlresourcename);
		mySql10.setSqlId(sqlid10);
		mySql10.addSubPara(operator);	
		
  var Result = easyExecSql(mySql10.getString()); 
  if(Result==null)
  {
  	alert("您没有核保权限！");
  	return;
  }  
 
 	var ReportType = GrpGrid.getRowColData(selno,14);     //上报标志
  if(	ReportType=='6'){
  	//赠送保单 
  	if(manageCom!="%"){
     alert("您没有核保赠送保单权限，只有总公司可以处理。");
  	 return;
  	}		
  }  

	 //如果有权限，判断全县是否可以操作这张单子
	 //tongmeng  2009-03-31 modify 
	 //允许低权限人员申请,但是不允许低权限人员下最终核保结论.
	 /*
  var tAuth=Result[0][0];
  sql="select missionprop12 From lwmission where missionprop1='"+GrpGrid.getRowColData(selno, 2)+"'"
     + " and activityid='0000002004' ";
  Result = easyExecSql(sql); 
  var tMiss = "";
  if(Result!=null)
  {
  	 tMiss=Result[0][0];
	}
	//alert("tAuth:"+tAuth+":tMiss:"+tMiss+":");
	
  if(tAuth<tMiss)
  {
  	alert("您的权限不能核保这张投保单！");
	  return;
  }*/
 // return;
   //需要增加校验，如果是权限是C6，只能总公司登录，赵宁定的需求，个人认为不合理，在未打到MANTIS上之前暂不做
	fm.MissionID.value = GrpGrid.getRowColData(selno, 7);
	fm.SubMissionID.value = GrpGrid.getRowColData(selno, 8);
	fm.ActivityID.value = GrpGrid.getRowColData(selno, 9);
	var i = 0;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	//[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
	lockScreen('lkscreen');  
	fm.action = "./ManuUWAllChk.jsp";
	document.getElementById("fm").submit(); //提交
	
	//easyQueryAddClick();
}

/*********************************************************************
 *  提交后操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	unlockScreen('lkscreen');  
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  

  if (FlagStr == "Fail" )
  {  
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
        //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
    	var name='提示';   //网页名称，可为空; 
    	var iWidth=550;      //弹出窗口的宽度; 
    	var iHeight=250;     //弹出窗口的高度; 
    	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
    	showInfo.focus();
    	//[end]
   // alert(content);
  easyQueryClick();
  easyQueryClickAll();
  }
  else
  { 
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	  //[start]  add by liuzhijie 说明:用[start]...[end]之间的代码块来替换上面第一条所注释代码、以供浏览器兼容性
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
		showInfo.focus();
		//[end]
    easyQueryAddClick();
    //执行下一步操作
  }

}

function showNotePad()
{

	var selno = SelfGrpGrid.getSelNo()-1;
	if (selno <0)
	{
	      alert("请选择一条任务");
	      return;
	}
	
	var MissionID = SelfGrpGrid.getRowColData(selno, 9);
	var SubMissionID = SelfGrpGrid.getRowColData(selno, 10);
	var ActivityID = SelfGrpGrid.getRowColData(selno, 11);
	var PrtNo = SelfGrpGrid.getRowColData(selno, 4);
	var NoType = operFlag;
	if(PrtNo == null || PrtNo == "")
	{
		alert("印刷号为空！");
		return;
	}			
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
	var newWindow = OpenWindowNew("../uwgrp/WorkFlowNotePadFrame.jsp?Interface=../uwgrp/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");	
		
}

/*********************************************************************
 *  选择核保结后的动作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field ) {
	
}
