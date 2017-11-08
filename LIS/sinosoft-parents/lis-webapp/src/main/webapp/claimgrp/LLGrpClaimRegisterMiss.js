//该文件中包含客户端需要处理的函数和事件
var showInfo;
var mDebug="0";
var mSwitch = parent.VD.gVSwitch;
var turnPage = new turnPageClass();  //使用翻页功能，必须建立为全局变量
var turnPage2 = new turnPageClass();
var mySql = new SqlClass();
//提交完成后所作操作
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=350;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

        //直接取得数据跳转到立案界面
        var i = LLClaimRegisterGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = LLClaimRegisterGrid.getRowColData(i,1);
            var tClmState = LLClaimRegisterGrid.getRowColData(i,2);
            tClmState = tClmState.substring(0,2);
            var tMissionID = LLClaimRegisterGrid.getRowColData(i,7);
            var tSubMissionID = LLClaimRegisterGrid.getRowColData(i,8);
            location.href="LLGrpClaimRegister.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
        }
    }
    tSaveFlag ="0";
}

function returnparent()
{
    var backstr=document.all("ContNo").value;
    //alert(backstr+"backstr");
    mSwitch.deleteVar("ContNo");
  mSwitch.addVar("ContNo", "", backstr);
  mSwitch.updateVar("ContNo", "", backstr);
    location.href="ContInsuredInput.jsp?LoadFlag=5&ContType="+ContType;
}

function showNotePad()
{
    alert("开发中")
    return;
  var selno = SelfPolGrid.getSelNo()-1;
  if (selno <0)
  {
        alert("请选择一条任务");
        return;
  }

  var MissionID = SelfPolGrid.getRowColData(selno, 6);
  var SubMissionID = SelfPolGrid.getRowColData(selno, 7);
  var ActivityID = SelfPolGrid.getRowColData(selno, 8);
  var PrtNo = SelfPolGrid.getRowColData(selno, 2);
  if(PrtNo == null || PrtNo == "")
  {
    alert("印刷号为空！");
    return;
  }
  var NoType = "1";

  var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ PrtNo + "&NoType="+ NoType;
  var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"工作流记事本查看","left");

}

//显示报案页面
function newReport()
{
    location.href="LLGrpClaimRegister.jsp?isNew=0";
}

// 初始化表格1
function queryGrid()
{
    initLLClaimRegisterGrid();
    var strSQL = "";
    var tCustomerName = fm.CustomerName.value;//客户姓名
    var tManageCom    = fm.ManageCom.value;   //管理机构
    //报案号,报案状态,出险人编码,出险人姓名,团体保单号,出险日期
   /* strSQL = "select missionprop1,(case missionprop2 when '10' "
           + " then '10-已报案' when '20' then '20-已立案' when '25' "
           + " then '25-延迟立案' end),missionprop3,missionprop7,"
           + " missionprop4,missionprop6,missionid,submissionid,"
           + " activityid,"
           + " makedate, "
           + " (select UserName from lduser "
           + " where UserCode = lwmission.lastoperator),missionprop20, "
           + " (select case count(*) when 0 then '无' else '有' end from es_doc_main where doccode=lwmission.missionprop1),"
           + " (select case count(*) when 0 then '无' else '有' end from LLInqConclusion where clmno=lwmission.missionprop1 and finiflag='0')"
           + " from lwmission where 1=1 "
           + " and activityid = '0000009015'"
           + " and processid = '0000000009'"
           + " and DefaultOperator is null "
           + " and missionprop15 = '11' "
           //+ " and missionprop2 != '25' "
           + getWherePart( 'missionprop1' ,'RptNo')
           + getWherePart( 'missionprop2' ,'ClmState')
           + getWherePart( 'missionprop3' ,'CustomerNo')
           + getWherePart( 'missionprop6' ,'AccidentDate2')
           + getWherePart( 'missionprop7' ,'GrpContNo')*/
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpClaimRegisterMissInputSql");
	mySql.setSqlId("LLGrpClaimRegisterMissSql1");
	mySql.addSubPara(fm.RptNo.value ); 
	mySql.addSubPara(fm.ClmState.value ); 
	mySql.addSubPara(fm.CustomerNo.value ); 
	mySql.addSubPara(fm.AccidentDate2.value ); 
	mySql.addSubPara(fm.GrpContNo.value ); 
     //如果输入出险人姓名则在全国范围内查找
     if (tCustomerName == "" || tCustomerName == null)
     {
        // strSQL = strSQL + " and missionprop20 like '" + tManageCom + "%%'"
         mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpClaimRegisterMissInputSql");
		mySql.setSqlId("LLGrpClaimRegisterMissSql2");
		mySql.addSubPara(fm.RptNo.value ); 
		mySql.addSubPara(fm.ClmState.value ); 
		mySql.addSubPara(fm.CustomerNo.value ); 
		mySql.addSubPara(fm.AccidentDate2.value ); 
		mySql.addSubPara(fm.GrpContNo.value ); 
		mySql.addSubPara(tManageCom); 
     }
     else
     {
        // strSQL = strSQL + getWherePart( 'missionprop4' ,'CustomerName','like')
           mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpClaimRegisterMissInputSql");
		mySql.setSqlId("LLGrpClaimRegisterMissSql3");
		mySql.addSubPara(fm.RptNo.value ); 
		mySql.addSubPara(fm.ClmState.value ); 
		mySql.addSubPara(fm.CustomerNo.value ); 
		mySql.addSubPara(fm.AccidentDate2.value ); 
		mySql.addSubPara(fm.GrpContNo.value ); 
		mySql.addSubPara(tManageCom); 
		mySql.addSubPara(fm.CustomerName.value ); 
     }
     //strSQL = strSQL + " order by makedate,missionprop2,missionid";
     turnPage.queryModal(mySql.getString(),LLClaimRegisterGrid,1,1);
}

// 初始化表格2
function querySelfGrid()
{
//登陆机构操作控制,登陆机构代码小于4位的不允许进行操作
/*if( fm.ManageCom.value.length < 4){
    fm.riskbutton.disabled=true;
    fm.Report.disabled=true;
}else{
    fm.riskbutton.disabled=false;
    fm.Report.disabled=false;
}*/

    initSelfLLClaimRegisterGrid();
    var strSQL = "";
    var tRgtDateStart = document.all('RgtDateStart').value;//登记日期
    var tRgtDateEnd   = document.all('RgtDateEnd').value;  //登记日期
    var tOperator     = fm.Operator.value;           //用户
    var tManageCom    = fm.ManageCom.value;          //管理机构
   /* strSQL = "select missionprop1,(case missionprop2 when '10' "
           +" then '10-已报案' when '20' then '20-已立案' when '25' "
           +" then '25-延迟立案' end),missionprop3,missionprop4,"
           +" missionprop7,missionprop6,missionid,submissionid,"
           +" activityid,(select UserName from lduser "
           +" where UserCode = lwmission.lastoperator),missionprop20, "
           +" (select case count(*) when 0 then '无' else '有' end from es_doc_main where doccode=lwmission.missionprop1), "
           +" (select case count(*) when 0 then '无' else '有' end from LLInqConclusion where clmno=lwmission.missionprop1 and finiflag='0'),"
           +" (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 "
           +" and activityid = '0000009015' "
           +" and processid = '0000000009'and missionprop10 is null "
           +" and missionprop15 = '11' "
           //+ " and missionprop2 != '25' "
           + getWherePart( 'missionprop1' ,'RptNo2')
           + getWherePart( 'missionprop7' ,'GrpContNo2')
           + getWherePart( 'missionprop3' ,'CustomerNo2')
           + getWherePart( 'missionprop4' ,'CustomerName2','like')*/
           //prompt("",strSQL);
		mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpClaimRegisterMissInputSql");
		mySql.setSqlId("LLGrpClaimRegisterMissSql4");
		mySql.addSubPara(fm.RptNo2.value ); 
		mySql.addSubPara(fm.GrpContNo2.value ); 
		mySql.addSubPara(fm.CustomerNo2.value ); 
		mySql.addSubPara(fm.CustomerName2.value ); 
		mySql.addSubPara(tOperator); 
		
   //申请日期区间查询
   var blDateStart = !(tRgtDateStart == null || tRgtDateStart == "");
   var blDateEnd = !(tRgtDateEnd== null ||tRgtDateEnd == ""); 
   
   if(blDateStart && blDateEnd)
   {
       /* strSQL = strSQL + " AND missionprop6 BETWEEN '"+
               tRgtDateStart + "' AND '" +tRgtDateEnd+ "'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpClaimRegisterMissInputSql");
		mySql.setSqlId("LLGrpClaimRegisterMissSql5");
		mySql.addSubPara(fm.RptNo2.value ); 
		mySql.addSubPara(fm.GrpContNo2.value ); 
		mySql.addSubPara(fm.CustomerNo2.value ); 
		mySql.addSubPara(fm.CustomerName2.value ); 
		mySql.addSubPara(tRgtDateStart ); 
		mySql.addSubPara(tRgtDateEnd); 
		mySql.addSubPara(tOperator); 
   } 
   else if (blDateStart) 
   {
       // strSQL = strSQL + " AND makedate >= '" + tRgtDateStart + "'";
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpClaimRegisterMissInputSql");
		mySql.setSqlId("LLGrpClaimRegisterMissSql6");
		mySql.addSubPara(fm.RptNo2.value ); 
		mySql.addSubPara(fm.GrpContNo2.value ); 
		mySql.addSubPara(fm.CustomerNo2.value ); 
		mySql.addSubPara(fm.CustomerName2.value ); 
		mySql.addSubPara(tRgtDateStart ); 
		mySql.addSubPara(tOperator); 
		
   } 
   else if (blDateEnd) 
   {
        //strSQL = strSQL + " AND makedate =< '" + tRgtDateEnd + "'";
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpClaimRegisterMissInputSql");
		mySql.setSqlId("LLGrpClaimRegisterMissSql7");
		mySql.addSubPara(fm.RptNo2.value ); 
		mySql.addSubPara(fm.GrpContNo2.value ); 
		mySql.addSubPara(fm.CustomerNo2.value ); 
		mySql.addSubPara(fm.CustomerName2.value ); 
		mySql.addSubPara(tRgtDateEnd); 
		mySql.addSubPara(tOperator); 
   }

   // strSQL = strSQL + " and missionprop20 like '"+tManageCom+"%%' "
    //       +" and  DefaultOperator= '"+tOperator;
    //zy 2009-9-8 17:46 保持与个险一致，不进行管理机构的校验
   // strSQL = strSQL +" and  DefaultOperator= '"+tOperator;
   // strSQL = strSQL + "' order by AcceptedDate,missionprop2,missionid";
    
    turnPage2.queryModal(mySql.getString(),SelfLLClaimRegisterGrid,1,1);
    
    HighlightSelfByRow();
}

//SelfLLClaimEndCaseGrid若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
function HighlightSelfByRow(){
    for(var i=0; i<SelfLLClaimRegisterGrid.mulLineCount; i++){
    	var accepteddate = SelfLLClaimRegisterGrid.getRowColData(i,14); //受理日期
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
    	   {
    		   SelfLLClaimRegisterGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}


//LLClaimRegisterGrid点击事件
function LLClaimRegisterGridClick()
{
}

//SelfLLClaimRegisterGrid点击事件
function SelfLLClaimRegisterGridClick()
{
                      HighlightSelfByRow();
					  //alert(document.all('ComCode').value.length);
					  var i = SelfLLClaimRegisterGrid.getSelNo()-1;
					  if(document.all('ComCode').value.length == 2)//总公司不能进行立案操作
					  {
						  alert("总公司不能进行立案操作!");
						  return false;
					  }
					  //----------------------jinsh20070522------------------//
					//  var oldornewsql2="select flowflag from llclaimmanage where managecom='"+document.all('ComCode').value+"'";
					//	var oldornewflag2=easyExecSql(oldornewsql2);
					var oldornewflag2 = '1';
						//alert(oldornewflag2);
						if(oldornewflag2=="1"||oldornewflag2=='1')//新
						{
							  //else//两位机构可以申请
							  //{
							  			var tClmNo = SelfLLClaimRegisterGrid.getRowColData(i,1);
		        					var tClmState = SelfLLClaimRegisterGrid.getRowColData(i,2);
		        					tClmState = tClmState.substring(0,2);
		        					var tMissionID = SelfLLClaimRegisterGrid.getRowColData(i,7);
		        					var tSubMissionID = SelfLLClaimRegisterGrid.getRowColData(i,8);
		        					location.href="LLGrpClaimRegister.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
							  //}
						}
						if(oldornewflag2=="0"||oldornewflag2=='0')//旧
						{
								//if(document.all('ComCode').value.length>2)//四位能申请
								//{
											var tClmNo = SelfLLClaimRegisterGrid.getRowColData(i,1);
		        					var tClmState = SelfLLClaimRegisterGrid.getRowColData(i,2);
		        					tClmState = tClmState.substring(0,2);
		        					var tMissionID = SelfLLClaimRegisterGrid.getRowColData(i,7);
		        					var tSubMissionID = SelfLLClaimRegisterGrid.getRowColData(i,8);
		        					location.href="LLGrpClaimRegister.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
								//}
							  //else//两位机构不能申请
							  //{
							  			//alert("此操作只能由四位机构来操作!");
							  			
							  //}
						}
	  //----------------------------jinsh20070522------------------------//
	  
	  
	  
	  
	  /*var printflagsql2="select count(*) from es_doc_main where doccode='"+SelfLLClaimRegisterGrid.getRowColData(i,1)+"'";
		var printflag2=easyExecSql(printflagsql2);
		if(printflag2!='0')
		{
				  if((document.all('ComCode').value.length)>2)
				  {
				  	alert("扫描案件立案操作只能由两位机构来操作!");
				  }
				  else
					{
							var i = SelfLLClaimRegisterGrid.getSelNo();
    					if (i != '0')
    					{
		        			i = i - 1;
		       			  var tClmNo = SelfLLClaimRegisterGrid.getRowColData(i,1);
		        			var tClmState = SelfLLClaimRegisterGrid.getRowColData(i,2);
		        			tClmState = tClmState.substring(0,2);
		        			var tMissionID = SelfLLClaimRegisterGrid.getRowColData(i,7);
		        			var tSubMissionID = SelfLLClaimRegisterGrid.getRowColData(i,8);
		        			location.href="LLGrpClaimRegister.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
    					}
					}
		}
	  else
	  {
	  			
    			var i = SelfLLClaimRegisterGrid.getSelNo();
    			if (i != '0')
    			{
        			i = i - 1;
       			  var tClmNo = SelfLLClaimRegisterGrid.getRowColData(i,1);
        			var tClmState = SelfLLClaimRegisterGrid.getRowColData(i,2);
        			tClmState = tClmState.substring(0,2);
        			var tMissionID = SelfLLClaimRegisterGrid.getRowColData(i,7);
        			var tSubMissionID = SelfLLClaimRegisterGrid.getRowColData(i,8);
        			location.href="LLGrpClaimRegister.jsp?claimNo="+tClmNo+"&isNew=1&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
    			}
    }*/
}

//[申请]按钮
function ApplyClaim()
{
/************************20070824-liuyu-按描述表控制流程*********************/
						var selno = LLClaimRegisterGrid.getSelNo()-1;
						if (selno <0)
						{
									alert("请选择要申请处理的报案！");
									return;
						}
						//var printflagsql="select count(*) from es_doc_main where doccode='"+LLClaimRegisterGrid.getRowColData(selno,1)+"'";
						//var printflag=easyExecSql(printflagsql);
						//alert(LLClaimRegisterGrid.getRowColData(selno,1));
						//alert(printflag);
						//if(printflag!='0')
						//{		  
								  if((document.all('ComCode').value.length)==2)
								  {
								  	alert("总公司不能进行立案操作!");
								  	return false;
								  }
								  //else
								  //{
								  				//fm.MissionID.value = LLClaimRegisterGrid.getRowColData(selno, 7);
												  //fm.SubMissionID.value = LLClaimRegisterGrid.getRowColData(selno, 8);
												  //fm.ActivityID.value = LLClaimRegisterGrid.getRowColData(selno, 9);  
												  //fm.action = "./LLReportMissSave.jsp";
												  //submitForm(); //提交
								  //}
						//}
					  //else
					  //{
						/************************20030409jinsh只有机构是两位的可以操作*********************/
												  //fm.MissionID.value = LLClaimRegisterGrid.getRowColData(selno, 7);
												  //fm.SubMissionID.value = LLClaimRegisterGrid.getRowColData(selno, 8);
												  //fm.ActivityID.value = LLClaimRegisterGrid.getRowColData(selno, 9);  
												  //fm.action = "./LLReportMissSave.jsp";
												  //submitForm(); //提交
						//}
						//var oldornewsql="select flowflag from llclaimmanage where managecom='"+document.all('ComCode').value+"'";
					//	var oldornewflag=easyExecSql(oldornewsql);
					//	alert(oldornewflag);
					oldornewflag = '1';
						if(oldornewflag=="1"||oldornewflag=='1')//新
						{
//								if(document.all('ComCode').value.length>2)//四位机构不能申请
//								{
//											//alert("此操作只能由两位机构来操作!");
//											//return;
//								}
//							  else//两位机构可以申请
//							  {
							  			fm.MissionID.value = LLClaimRegisterGrid.getRowColData(selno, 7);
											fm.SubMissionID.value = LLClaimRegisterGrid.getRowColData(selno, 8);
											fm.ActivityID.value = LLClaimRegisterGrid.getRowColData(selno, 9);  
											fm.action = "./LLReportMissSave.jsp";
											submitForm(); //提交
//							  }
						}
						if(oldornewflag=="0"||oldornewflag=='0')//旧
						{
								if(document.all('ComCode').value.length>2)//四位能申请
								{
											fm.MissionID.value = LLClaimRegisterGrid.getRowColData(selno, 7);
											fm.SubMissionID.value = LLClaimRegisterGrid.getRowColData(selno, 8);
											fm.ActivityID.value = LLClaimRegisterGrid.getRowColData(selno, 9);  
											fm.action = "./LLReportMissSave.jsp";
											submitForm(); //提交
								}
							  else//两位机构不能申请
							  {
							  			alert("此操作只能由四位机构来操作!");
							  			return;							  			
							  }
						}
}

//提交数据
function submitForm()
{
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";
}