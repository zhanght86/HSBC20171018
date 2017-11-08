//程序名称：UWMissionAssign.js
//程序功能：核保任务分配
//创建日期：2005-5-14 16:25
//创建人  ：HWM
//更新记录：  更新人    更新日期     更新原因/内容
//            刘晓松    2006-10-20    加入核保任务分配类型

var showInfo;
var mDebug="0";
var flag;
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();
//turnPage.pageLineNum = 10;
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();
var tActivityStatus="";
var k = 0;

/*********************************************************************
 *  执行新契约人工核保的EasyQuery
 //===add====liuxiaosong===2006-10-20====start
    修改后，按选择新契约、保全、理赔 的 人工核保类型，
    执行相应的SQL
 //===add====liuxiaosong===2006-10-20====end
 *  描述:查询显示对象是合同保单.显示条件:合同未进行人工核保，或状态为待核保
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */

function easyQueryClickAll()
{
	// 书写SQL语句
//	alert('1');
	var strSQL ="";
  if(fm.UWKind.value=="")
  {
  	alert("请先选择核保类型");
  	fm.UWKind.focus();
  	return false;
  }
  
   if(fm.UWKind.value!="1"&&fm.UWKind.value!="2"&&fm.UWKind.value!="3")
   {
       alert("错误核保类型,请双击选择正确的核保类型");
       fm.UWKind.value="";
       fm.UWKind.focus();
       return false		
   }
   if(fm.ContType.value!="")
   {
   if(fm.ContType.value!="1"&&fm.ContType.value!="2")
   {
       alert("错误保单类型,请双击选择正确的保单类型");
       fm.ContType.value="";
       fm.ContType.focus();
       return false		
   }
  }
//  alert(fm.UWName.value);
 
 // if(fm.UWName.value=="xqy")
 if(fm.UWKind.value=='1')
   {
   	initAllPolGrid();
   	
   	var wherePartStr = getWherePart('t.MissionProp1','PrtNo')
			//+ getWherePart('t.MissionProp1','PrtNo')
			+ getWherePart('t.MissionProp7','AppntName')
		//	+ getWherePart('t.MissionProp10','ManageCom')			
		//	+ getWherePart('t.MissionProp4','AgentCode')
			+ getWherePart('t.DefaultOperator','UserCode')
			+ getWherePart('t.ActivityID','ActivityID');
   	var sqlid915162539="DSHomeContSql915162539";
var mySql915162539=new SqlClass();
mySql915162539.setResourceName("uw.UWMissionAssignSql");//指定使用的properties文件名
mySql915162539.setSqlId(sqlid915162539);//指定使用的Sql的id
mySql915162539.addSubPara(wherePartStr);//指定传入的参数
mySql915162539.addSubPara(manageCom+"%%");//指定传入的参数
mySql915162539.addSubPara(manageCom+"%%");//指定传入的参数
var subPart1 = " and 1=1 ";
var subPart2 = " and 1=1 ";
var subPart3 = " and 1=1 ";

   	
//     strSQL = "select t.missionprop1,t.missionprop2,t.missionprop7, "
//            + " (select codename from ldcode where codetype='uwstatus' and trim(code)=missionprop18),"
//            + " case t.ActivityID when '0000001100' "
//            + " then '个险' when '0000002004' then '团险' end,t.missionid,t.submissionid,t.ActivityID,"
//            +" t.defaultoperator,t.missionprop12,nvl(t.missionprop10,t.missionprop4) from lwmission t where 1=1 "
//			+ " and t.ActivityID in ('0000001100')"
//			//+ " and t.defaultoperator is not null "
//			//+ getWherePart('t.MissionProp2','ContNo')
//			+ getWherePart('t.MissionProp1','PrtNo')
//			//+ getWherePart('t.MissionProp1','PrtNo')
//			+ getWherePart('t.MissionProp7','AppntName')
//		//	+ getWherePart('t.MissionProp10','ManageCom')			
//		//	+ getWherePart('t.MissionProp4','AgentCode')
//			+ getWherePart('t.DefaultOperator','UserCode')
//			+ getWherePart('t.ActivityID','ActivityID')
//			//+ getWherePart('t.ActivityStatus','ActivityStatus')
//			+ " and (t.MissionProp10 like '"+manageCom+"%' or t.MissionProp4 like '"+manageCom+"%')" ;
		 if(fm.ManageCom.value!=null&&fm.ManageCom.value!="")
			{
				subPart1 = " and (t.MissionProp10 like '"+fm.ManageCom.value+"%' or t.MissionProp4 like '"+fm.ManageCom.value+"%')" ;
			}
		
			if(fm.AgentCode.value!=null&&fm.AgentCode.value!="")
			{
			  subPart2 = " and (t.MissionProp4 like '"+fm.AgentCode.value+"%' or t.MissionProp5 like '"+fm.AgentCode.value+"%')" ;
		  }
		  if(document.all('ActivityStatus').value!=null&&document.all('ActivityStatus').value!="")
					{
//				        subPart3 =  " and t.missionprop18='"+document.all('ActivityStatus').value+"' ";
						subPart3 =  " and exists (select 1 from lccuwmaster where contno= t.missionprop1 and uwstate ='"+document.all('ActivityStatus').value+"') ";
					}
			var subPart4 = " order by t.defaultoperator asc, t.modifydate asc,t.modifytime asc";
			mySql915162539.addSubPara(subPart1);//指定传入的参数
			mySql915162539.addSubPara(subPart2);//指定传入的参数
			mySql915162539.addSubPara(subPart3);//指定传入的参数
			mySql915162539.addSubPara(subPart4);//指定传入的参数
			strSQL=mySql915162539.getString();
      //prompt('',strSQL);
      turnPage.queryModal(strSQL, AllPolGrid);  
   }
   if(fm.UWName.value=="bq")
   {
   	initBqPolGrid();
   	
   	var wherePartStr = getWherePart('a.ActivityID','ActivityID')
          // +  getWherePart('a.MissionProp11','AppntName')
           +  getWherePart('a.MissionProp4','EdorAppName')
           +  getWherePart('a.MissionProp2','OtherNo')
           +  getWherePart('a.MissionProp1','EdorAcceptNo')
           +  getWherePart('a.ActivityStatus','ActivityStatus')
           +  getWherePart('a.MissionProp7','ManageCom','like')		
           +  getWherePart('a.DefaultOperator','UserCode');
   	var sqlid916100729="DSHomeContSql916100729";
var mySql916100729=new SqlClass();
mySql916100729.setResourceName("uw.UWMissionAssignSql");//指定使用的properties文件名
mySql916100729.setSqlId(sqlid916100729);//指定使用的Sql的id
mySql916100729.addSubPara(wherePartStr);//指定传入的参数
mySql916100729.addSubPara(manageCom+"%%");//指定传入的参数
strSQL=mySql916100729.getString();
   	
//   	strSQL = "select a.MissionID, a.SubMissionID, a.MissionProp1, a.MissionProp2,"
//   	       +" (select CodeName from LDCode where  1 = 1 and "
//   	       +" CodeType like '%edornotype%'  and trim(Code) = a.MissionProp3),a.MissionProp4,"
//   	       +" case  a.ActivityStatus when '1' then '未人工核保'  when '3' then '核保已回复' when '2' then '核保未回复' when '4' then '撤保' when '5' then '核保逾期' end,"
//           +" case a.ActivityID when '0000000005' then '个险' when '0000008005' then '团险' end,"
//           +" a.defaultoperator,a.MissionProp7,a.ActivityID from LWMission a where 1 = 1 and a.ProcessID = '0000000000'  "
//           + " and a.ActivityID in ('0000000005','0000008005')"
//           +  getWherePart('a.ActivityID','ActivityID')
//          // +  getWherePart('a.MissionProp11','AppntName')
//           +  getWherePart('a.MissionProp4','EdorAppName')
//           +  getWherePart('a.MissionProp2','OtherNo')
//           +  getWherePart('a.MissionProp1','EdorAcceptNo')
//           +  getWherePart('a.ActivityStatus','ActivityStatus')
//           +  getWherePart('a.MissionProp7','ManageCom','like')		
//           +  getWherePart('a.DefaultOperator','UserCode')
//           + " and a.MissionProp7 like '"+manageCom+"%'"	
//           +" order by a.defaultoperator asc,a.MissionProp1 asc, a.MakeDate asc " ;
          turnPage1.queryModal(strSQL, BqPolGrid);  
   }
   if(fm.UWName.value=="lp")
   {
   	initLpPolGrid();
   	
   	var wherePartStr = getWherePart('missionprop20' ,'ManageCom','like')  //<当前机构>
             + getWherePart('ActivityID','ActivityID')
             + getWherePart('MissionProp1','QCaseNo')
             + getWherePart('MissionProp4','QInsuredName')
             + getWherePart('MissionProp2','QBatNo')
             + getWherePart('ActivityStatus','ActivityStatus')
             + getWherePart('DefaultOperator','UserCode')	;
   	var sqlid916101029="DSHomeContSql916101029";
var mySql916101029=new SqlClass();
mySql916101029.setResourceName("uw.UWMissionAssignSql");//指定使用的properties文件名
mySql916101029.setSqlId(sqlid916101029);//指定使用的Sql的id
mySql916101029.addSubPara(wherePartStr);//指定传入的参数
mySql916101029.addSubPara(manageCom+"%%");//指定传入的参数
strSQL=mySql916101029.getString();
   	
//   	 strSQL = " select missionprop1,missionprop2, "
//	           +" case activitystatus when '1' then '未人工核保' when '3' then '核保已回复' "
//	           +" when '2' then '核保未回复'  when '4' then '再保未回复' when '5' then '再保已回复' end,"
//	           +" missionprop4,"
//		         +" case missionprop5 when '0' then '相关' when '1' then '不相关' end,DefaultOperator,"
//		         +" missionprop20,missionid,submissionid,ActivityID, missionprop5 from lwmission where 1=1 "
//             +" and ActivityID = '0000005505' "
//             +" and processid = '0000000005'"
//            // +" and DefaultOperator ='"+fm.Operator.value+"'"
//             + getWherePart('missionprop20' ,'ManageCom','like')  //<当前机构>
//             + getWherePart('ActivityID','ActivityID')
//             + getWherePart('MissionProp1','QCaseNo')
//             + getWherePart('MissionProp4','QInsuredName')
//             + getWherePart('MissionProp2','QBatNo')
//             + getWherePart('ActivityStatus','ActivityStatus')
//             + getWherePart('DefaultOperator','UserCode')	
//             + " and MissionProp20 like '"+manageCom+"%'"
//             + " order by defaultoperator asc,makedate asc,maketime asc";	
             turnPage2.queryModal(strSQL, LpPolGrid);    	
   }

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
	var tSel = PolGrid.getSelNo();
	var ActivityID = PolGrid.getRowColData(tSel - 1,8);
	var ContNo = PolGrid.getRowColData(tSel - 1,2);
	var PrtNo = PolGrid.getRowColData(tSel - 1,1);
	var MissionID = PolGrid.getRowColData(tSel - 1,6);
	var SubMissionID = PolGrid.getRowColData(tSel - 1,7);

	if(ActivityID == "0000001100")
	{
		window.location="./UWManuInputMain.jsp?ContNo="+ContNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&PrtNo="+PrtNo;
	}
	if(ActivityID == "0000002004")
	{
		window.location="./GroupUWMain.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID;
	}
	if(ActivityID == "0000006004")
	{
		window.location="../askapp/AskGroupUW.jsp?GrpContNo="+ContNo+"&PrtNo="+PrtNo+"&MissionID="+MissionID+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID; 
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
  if(beforUWApply()==false) return false;
	if(fm.DefaultOperator.value.length == 0)
	{
		  alert("没有输入任务核保师编号,即将把该任务退回到任务池!");
			fm.DefaultOperator.focus();
			return;
			
//		if(!confirm("没有输入任务核保师编号,确认要把该任务退回到任务池吗?")){
//			fm.DefaultOperator.focus();
//			return;
//		  }
//	  if(tActivityStatus!="未人工核保")
//	  {
//	  	alert("该保单为非未人工核保状态，不能退回到共享工作池");
//	  	return false;
//	  }
	}
	var i = 0;
	var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();    lockScreen('lkscreen');  
	fm.action = "./UWMissionAssignSave.jsp";
	document.getElementById("fm").submit(); //提交
}

/*********************************************************************
 *  提交后操作,服务器数据返回后执行的操作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
	unlockScreen('lkscreen');  
	showInfo.close();
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
  if (FlagStr == "Fail" )
  {                 
    alert(content);
  }
  else
  { 
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");     
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=350;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    //执行下一步操作
  }
  easyQueryClickAll();

}


function queryAgent()
{
	  var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

function afterQuery2(arrResult)
{
  
  if(arrResult!=null)
  {
    	fm.AgentCode.value = arrResult[0][0];
  	  //fm.AgentCode1.value = arrResult[0][0];
  }
}

function setManageCom(){
  document.all('MngCom').value =	AllPolGrid.getRowColData(AllPolGrid.getSelNo()-1, 10);
  //alert(fm.MngCom.value);
}
function afterCodeSelect(cCodeName, Field)
{
	if(cCodeName=="uwtype"||cCodeName=="contype")
	{
		fm.ActivityID.value="";
	}
  if (cCodeName == "uwtype") 
  {
       getActivityID("uwtype");
  }
   if(cCodeName == "contype")
   {
     getActivityID("contype");
   }
}
function getActivityID(flag)
{
	if(fm.ContType.value!="1"&&fm.ContType.value!="2")
  {
   	fm.ActivityID.value="";
  }
  if(flag=="uwtype")
  {
     if(fm.UWKind.value=="1")
     {
  	   	divBqPolGrid.style.display="none";
  	   	BQInfo.style.display="none";
  	   	divLpPolGrid.style.display="none";
  	   	LPInfo.style.display="none";
  	   	XQYInfo.style.display="";
  		  divAllPolGrid.style.display="";
  		  fm.UWName.value="xqy";
  		  if(fm.ContType.value=="1")
  		  {
  		  	fm.ActivityID.value="0000001100";
  		  }
  		  if(fm.ContType.value=="2")
  		  {
  		  	fm.ActivityID.value="0000002004";
  		  }
  	  }
     else 
     	if(fm.UWKind.value=="2")
       {  
       	 divAllPolGrid.style.display="none";
       	 XQYInfo.style.display="none";
       	 divLpPolGrid.style.display="none";
       	 LPInfo.style.display="none";
       	 BQInfo.style.display="";
       	 divBqPolGrid.style.display="";
       	 fm.UWName.value="bq";
       	 if(fm.ContType.value=="1")
  		   {
  		  	 fm.ActivityID.value="0000000005";
  		   }
  		   if(fm.ContType.value=="2")
  		   {
  		  	 fm.ActivityID.value="0000008005";
  		   }
       }
      else 
       if(fm.UWKind.value=="3")
        {
       	 	divBqPolGrid.style.display="none";
       	 	BQInfo.style.display="none";
  		    divAllPolGrid.style.display="none";
  		    XQYInfo.style.display="none";
  		    LPInfo.style.display="";
  		    divLpPolGrid.style.display="";
       	  fm.UWName.value="lp";
       	  fm.ActivityID.value="0000005505";
         }
     }
   if(flag=="contype")
   { 
     if(fm.ContType.value=="1")
     {
      	if(fm.UWKind.value=="1")
     	  {
     		  fm.ActivityID.value="0000001100";
      	}
      	if(fm.UWKind.value=="2")
       	{
     		  fm.ActivityID.value="0000000005";
     	  }
        if(fm.UWKind.value=="3")
     	  {
     			fm.ActivityID.value="0000005505";
     	  }
     }
     if(fm.ContType.value=="2")
      {
     	  if(fm.UWKind.value=="1")
     	  {
          fm.ActivityID.value="0000002004";
     	  }
     	  if(fm.UWKind.value=="2")
       	{
     	   fm.ActivityID.value="0000008005";
     	  }
        if(fm.UWKind.value=="3")
     	  {
     			fm.ActivityID.value="0000005505";
     	  }
      }
   }
}

function showmngcom()
{
	alert(fm.MngCom.value);
	}

//modified by liuning 08.01.17

function beforUWApply()
{
  if(fm.UWKind.value=="")
  {
	 alert("请先选择核保类型");
	 return false;
  } 
  var i=0 ;
    
  if(fm.UWKind.value=="1"&&fm.UWName.value=="xqy")
  {
  	/************************************************************************************/
  	/*******************************新契约核保任务分配***********************************/
  	/************************************************************************************/
  	
   for(i=0 ;i<AllPolGrid.mulLineCount ;i++)
  	if(AllPolGrid.getChkNo(i))
  	{
  		return;
    }
      //alert(1);
  		alert( "请选择要调配的投保单！" );
      return false;
	}
    
  if(fm.UWKind.value=="2"&&fm.UWName.value=="bq")
  { 
  	/************************************************************************************/
  	/*******************************保全核保任务分配***********************************/
  	/************************************************************************************/	

	 for(i=0 ;i<BqPolGrid.mulLineCount ;i++)
  	if(BqPolGrid.getChkNo(i))
  	{
  		return;
    }	 
     // alert(2);
  		alert( "请选择要调配的投保单！" );
      return false;
	 }

	 if(fm.UWKind.value=="3"&&fm.UWName.value=="lp")
   {
   	 /************************************************************************************/
  	/*******************************理赔二核任务分配***********************************/
  	/************************************************************************************/	
      
    for(i=0 ;i<LpPolGrid.mulLineCount ;i++)
	   if(LpPolGrid.getChkNo(i))
  	 {
  		return;
     }	  
      //alert(3);
  		alert( "请选择要调配的投保单！" );
      return false;
	 }

}		
  
//  var selno;
//  if(fm.UWKind.value=="1"&&fm.UWName.value=="xqy")
//  {
//  	/************************************************************************************/
//  	/*******************************新契约核保任务分配***********************************/
//  	/************************************************************************************/
//  	AllPolGrid.delBlankLine();
//  	if(AllPolGrid.mulLineCount==0)
//  	{
//  		alert("请先做查询");
//  		return false;
//  	}
//	  selno = AllPolGrid.getSelNo()-1;
//	  if (selno <0)
//	  {
//	      alert("请选择要调配的投保单！");
//	      return false;
//	  }
//	  fm.MissionID.value = AllPolGrid.getRowColData(selno, 6);
//	  fm.SubMissionID.value = AllPolGrid.getRowColData(selno, 7);
//	  fm.ActivityID.value = AllPolGrid.getRowColData(selno, 8);
//	  document.all('MngCom').value =	AllPolGrid.getRowColData(selno, 10);
//	  tActivityStatus=AllPolGrid.getRowColData(selno, 4)
//	}
//	if(fm.UWKind.value=="2"&&fm.UWName.value=="bq")
//  { 
//  	/************************************************************************************/
//  	/*******************************保全核保任务分配***********************************/
//  	/************************************************************************************/	
//  	BqPolGrid.delBlankLine();
//  	if(BqPolGrid.mulLineCount==0)
//  	{
//  		alert("请先做查询");
//  		return false;
//  	}
//	  selno = BqPolGrid.getSelNo()-1;
//	  if (selno <0)
//	  {
//	      alert("请选择要调配的投保单！");
//	      return false;
//  	}
//	  fm.MissionID.value = BqPolGrid.getRowColData(selno, 1);
//	  fm.SubMissionID.value = BqPolGrid.getRowColData(selno, 2);
//	  fm.ActivityID.value = BqPolGrid.getRowColData(selno, 11);
//	  document.all('MngCom').value =	BqPolGrid.getRowColData(selno, 10);
//	   tActivityStatus=BqPolGrid.getRowColData(selno, 7)
//	 }
//	 if(fm.UWKind.value=="3"&&fm.UWName.value=="lp")
//   {
//   	 /************************************************************************************/
//  	/*******************************理赔二核任务分配***********************************/
//  	/************************************************************************************/	
//  	 LpPolGrid.delBlankLine();
//  	 if(LpPolGrid.mulLineCount==0)
//  	 {
//  		 alert("请先做查询");
//  		 return false;
//  	 }
//	   selno = LpPolGrid.getSelNo()-1;
//	   if (selno <0)
//	   { 
//	      alert("请选择要调配的投保单！");
//	      return;
//	   }
//	   fm.MissionID.value = LpPolGrid.getRowColData(selno, 8);
//	   fm.SubMissionID.value = LpPolGrid.getRowColData(selno, 9);
//     fm.ActivityID.value = LpPolGrid.getRowColData(selno, 10);
//     document.all('MngCom').value =	LpPolGrid.getRowColData(selno, 11);
//     tActivityStatus=LpPolGrid.getRowColData(selno,3);
//	 }
	
