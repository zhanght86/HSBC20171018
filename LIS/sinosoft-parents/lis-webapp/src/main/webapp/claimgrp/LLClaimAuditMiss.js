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
        var i = LLClaimAuditGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = LLClaimAuditGrid.getRowColData(i,1);
            var tClmState = LLClaimAuditGrid.getRowColData(i,2);
            var tMissionID = LLClaimAuditGrid.getRowColData(i,7);
            var tSubMissionID = LLClaimAuditGrid.getRowColData(i,8);
            var tBudgetFlag = LLClaimAuditGrid.getRowColData(i,14);
            var tAuditPopedom = LLClaimAuditGrid.getRowColData(i,13);
            location.href="LLClaimAuditInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom;
        }
    }
    tSaveFlag ="0";
}

function returnparent()
{
    var backstr=document.all("ContNo").value;
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

// 初始化表格1
function queryGrid()
{
    //---------------------------------------------------------------------------BEG2005-8-8 15:05
    //查询操作人审核权限
    //---------------------------------------------------------------------------
   /* var tSql = "select substr(a.checklevel,2,2) from LLClaimUser a where 1=1 "
         + " and a.checkflag = '1' "
         + " and a.usercode = '" + fm.Operator.value + "'"*/
    mySql = new SqlClass();
mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
mySql.setSqlId("LLClaimAuditMissSql1");
mySql.addSubPara(fm.Operator.value );      
    var tCheckLevel = easyExecSql(mySql.getString());
    //---------------------------------------------------------------------------End
  
    //---------------------------------------------------------------------------BEG2005-8-14 16:31
    //查询操作人机构层次,按comcode
    //---------------------------------------------------------------------------
    var tRank = "";
    var tComCode = fm.ComCode.value;
    if (tComCode.length == 2)
    {
      tRank = "1";//总公司
    }
    else
    {
      if (tComCode.length == 4)
      {
        tRank = "2";//分公司
      }
    }
    //---------------------------------------------------------------------------End
    
    initLLClaimAuditGrid();
    /*var strSQL = "";
    strSQL = "select missionprop1,(case missionprop2 when '20' then '立案' when '30' then '审核' when '40' then '审批' end),"
         + "missionprop3,missionprop4,(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),"
         + "missionprop6,missionid,submissionid,activityid,"
         + "(case MissionProp11 when '0' then '无预付' when '1' then '有预付' end),"
         + "(case MissionProp7 when '1' then '个人' when '2' then '团体' end),"
         + "(case MissionProp12 when 'A' then '立案' when 'B' then '审批金额为正' when 'C' then '审批金额为负' when 'D' then '简易案件' end),"
         + "MissionProp10,MissionProp11,MakeDate,lastoperator,missionprop20 from lwmission where 1=1 "      
         + " and activityid = '0000009035'"   
         + " and processid = '0000000009'"
         + " and DefaultOperator is null "
         + getWherePart( 'missionprop1' ,'RptNo')
         + getWherePart( 'missionprop2' ,'ClmState')
         + getWherePart( 'missionprop3' ,'CustomerNo')
         + getWherePart( 'missionprop4' ,'CustomerName','like')
         + getWherePart( 'missionprop5' ,'customerSex')
         + getWherePart( 'missionprop6' ,'AccidentDate2')
         + " and MissionProp19 = '0'"   //没有原审核人
         + " and MissionProp15 = '11'"*/
//         + " and (missionprop20 like '" + fm.ManageCom.value + "%%'"  //本级
//         + " or (missionprop20 like '" + fm.ManageCom.value + "%%'"  //本级已下
//         + " and missionprop18 = '" + tRank + "'))" 
	 mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql2");
	mySql.addSubPara(fm.RptNo.value );  
	mySql.addSubPara(fm.ClmState.value );  
	mySql.addSubPara(fm.CustomerNo.value );  
	mySql.addSubPara(fm.CustomerName.value );  
	mySql.addSubPara(fm.customerSex.value );  
	mySql.addSubPara(fm.AccidentDate2.value );  
         if(fm.ManageCom.value.length >= 2){
        // strSQL += " and missionprop20 like '" + fm.ManageCom.value + "%%'" 
         mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql3");
	mySql.addSubPara(fm.RptNo.value );  
	mySql.addSubPara(fm.ClmState.value );  
	mySql.addSubPara(fm.CustomerNo.value );  
	mySql.addSubPara(fm.CustomerName.value );  
	mySql.addSubPara(fm.customerSex.value );  
	mySql.addSubPara(fm.AccidentDate2.value );  
	mySql.addSubPara(fm.ManageCom.value );  
         }
         //strSQL += " order by missionprop10 desc,missionid ";
    //prompt('审核结案共享池查询',strSQL);
    turnPage.queryModal(mySql.getString(),LLClaimAuditGrid,1,1);
}

// 初始化表格2
function querySelfGrid()
{
    initSelfLLClaimAuditGrid();
    var strSQL = "";
    var tManageCom = fm.ManageCom.value;
    strSQL = "select missionprop1,(case missionprop2 when '20' then '立案' when '30' then '审核' when '40' then '审批' end),"
         +" missionprop3,missionprop4,(case missionprop5 when '0' then '男' when '1' then '女' when '2' then '未知' end),"
         +" missionprop6,missionid,submissionid,activityid,"
         +" (case MissionProp11 when '0' then '无预付' when '1' then '有预付' end),"
         +" (case MissionProp7 when '1' then '个人' when '2' then '团体' end),"
         +" (case MissionProp12 when 'A' then '立案' when 'B' then '审批金额为正' when 'C' then '审批金额为负' when 'D' then '简易案件' end),"
         +" MissionProp10,MissionProp11,MakeDate,lastoperator,missionprop20,''," 
         +" (select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 "
         + " and activityid = '0000009035'"
         + " and processid = '0000000009'"
         + " and (DefaultOperator = '" + fm.Operator.value + "'"
         + " or MissionProp19 = '" + fm.Operator.value + "')"
         + " and MissionProp15 = '11'"
         // zy 2009-9-8 17:54 屏蔽管理机构的校验，保持与个险一致
         //if(fm.ManageCom.value.length >= 2){
         //strSQL += " and missionprop20 like '"+tManageCom+"%%' "
         //}
		/*mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql4");
	mySql.addSubPara(fm.Operator.value );  
	mySql.addSubPara(fm.Operator.value );  */
	
         strSQL += " order by AcceptedDate,missionprop10 desc,missionid ";
    turnPage2.queryModal(strSQL,SelfLLClaimAuditGrid,1,1);
    HighlightByRow();
}

//SelfLLClaimEndCaseGrid若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
function HighlightByRow(){
    for(var i=0; i<SelfLLClaimAuditGrid.mulLineCount; i++){
    	var accepteddate = SelfLLClaimAuditGrid.getRowColData(i,19); //受理日期
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
    	   {
    		   SelfLLClaimAuditGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}


//LLClaimAuditGrid点击事件
function LLClaimAuditGridClick()
{
}

//SelfLLClaimAuditGrid点击事件
function SelfLLClaimAuditGridClick()
{
             HighlightByRow();
		    /************************20030410jinsh只有机构是两位的可以操作*********************/
			  //alert(document.all('ComCode').value.length);
//			  if((document.all('ComCode').value.length)>2)
//			  {
//			  	alert("结案操作只能由两位机构来操作!");
//			  }
//			  else
//			  {
							 /************************20030410jinsh只有机构是两位的可以操作*********************/
							 
							 var i = SelfLLClaimAuditGrid.getSelNo();
							 if (i != '0')
							 {
										    i = i - 1;
										    var tClmNo = SelfLLClaimAuditGrid.getRowColData(i,1);
										    var tClmState = SelfLLClaimAuditGrid.getRowColData(i,2);
										    var tMissionID = SelfLLClaimAuditGrid.getRowColData(i,7);
										    var tSubMissionID = SelfLLClaimAuditGrid.getRowColData(i,8);
										    var tBudgetFlag = SelfLLClaimAuditGrid.getRowColData(i,14);
										    var tAuditPopedom = SelfLLClaimAuditGrid.getRowColData(i,13);
										    location.href="LLClaimAuditInput.jsp?claimNo="+tClmNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID+"&BudgetFlag="+tBudgetFlag+"&AuditPopedom="+tAuditPopedom;
							 }
//				 }
}

//[申请]按钮
function ApplyClaim()
{
//		if((document.all('ComCode').value.length)>2)
//		
//		{
//					alert("结案操作只能由两位机构来操作!");
//		}
//		else
//		{	    
			  var selno = LLClaimAuditGrid.getSelNo()-1;
				    if (selno <0)
				    {
				        alert("请选择要申请处理的案件！");
				        return;
				    }
				    var tRgtNO = LLClaimAuditGrid.getRowColData(selno, 1);
				   // var tSql = "select Operator from llregister where rgtno = '"+tRgtNO+"'";
				    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql5");
	mySql.addSubPara(tRgtNO);  
				    var tOperator = easyExecSql(mySql.getString());
				    if(fm.Operator.value == tOperator)
				    {
				        alert("立案审核和复核结案不能为同一个操作员!");
				        return;
				    }
				//var txsql="select distinct givetype from llclaimdetail where clmno='"+tRgtNO+"' ";
        mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql6");
	mySql.addSubPara(tRgtNO);  
        var tGiveType=easyExecSql(mySql.getString());
				
			if (tGiveType == '0')     
      {	
       // var csql="select customerno from llcase where caseno='"+tRgtNO+"' ";
        mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql7");
	mySql.addSubPara(tRgtNO);  
        var tcustomerno=new Array();
        tcustomerno=easyExecSql(mySql.getString());
        var tClaimType1="";
        var tClaimType2="";
        for (var i=0;i<tcustomerno.length;i++)
        { 				

				 //01.1查询该赔案个人寿险的最大理赔值
          /*  var strSql00 = " select sum(realpay) from llclaimpolicy "
                      + " where clmno = '"+tRgtNO+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='10') and insuredno='"+tcustomerno[i]+"'";                  
			*/ mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql8");
	mySql.addSubPara(tRgtNO); 
	mySql.addSubPara(tcustomerno[i]); 
            var tSubReport = new Array;
            tSubReport = easyExecSql(mySql.getString());    
            var tRealpay1 = tSubReport[0][0];            
            if(tRealpay1==null || tRealpay1 == "")
              {
                 tRealpay1=0;       
              }
             else
              {              	 
              	 tClaimType1='10';              	 
              }
         //01.2查询该赔案个人健康险的最大理赔值    
          /*  var strSql01 = " select sum(realpay) from llclaimpolicy "
                          + " where clmno = '"+tRgtNO+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='30') and insuredno='"+tcustomerno[i]+"'";      */            
            mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql9");
	mySql.addSubPara(tRgtNO); 
	mySql.addSubPara(tcustomerno[i]); 
            var tSubReport1 = new Array;
            tSubReport1 = easyExecSql(mySql.getString());
            var tRealpay2 = tSubReport1[0][0];
            if(tRealpay2==null || tRealpay2 == "")
              {
                 tRealpay2=0;           
              }
            else
            	{            	   
            	   tClaimType2='30';            	   
            	}              
                  
				//0501.查询该机构最高理赔权限
				    var tManageCom = fm.ManageCom.value.substring(0,2);
				   // var strSql0501 = " select checklevel, username,usercode from llclaimuser where comcode like '"+tManageCom+"%%' and StateFlag = '1' and checklevel = (select max(checklevel) from llclaimuser a , Lduser b where a.comcode like '"+tManageCom+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0') order by comcode" ;
				    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql10");
	mySql.addSubPara(tManageCom); 
	mySql.addSubPara(tManageCom); 
				    var tSubReport2 = new Array;
				        tSubReport2 = easyExecSql(mySql.getString());
				    if(tSubReport2 != null)
				    {
				        var tChecklevel = tSubReport2[0][0];
				        var tUserName   = tSubReport2[0][1];
				        var tUserCode   = tSubReport2[0][2];
				    }else{
				        alert("未查询到该分机构的最高级别理赔人员!");
				        return false;
				    }
				
				//0502.1查询该机构最高权限的寿险赔付金额
				   // var strSql0502 = "select basemax from LLClaimPopedom where claimtype = '"+tClaimType1+"' and Popedomlevel = '"+tChecklevel+"'";
				     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql11");
	mySql.addSubPara(tClaimType1); 
	mySql.addSubPara(tChecklevel); 
				    var tBasemax1  = easyExecSql(mySql.getString());
				        tBasemax1  = tBasemax1*1;
				        tRealpay1  = tRealpay1*1;
				        
				//0502.2查询该机构最高权限的健康险赔付金额
				    //var strSql0503 = "select basemax from LLClaimPopedom where claimtype = '"+tClaimType2+"' and Popedomlevel = '"+tChecklevel+"'";
				    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql12");
	mySql.addSubPara(tClaimType2); 
	mySql.addSubPara(tChecklevel); 
				    var tBasemax2  = easyExecSql(mySql.getString());
				        tBasemax2 = tBasemax2*1;
				        tRealpay2  = tRealpay2*1;				        
				//案件赔付金额超过当地最高理赔级别判断
				   if(((tRealpay1 > tBasemax1)||(tRealpay2 > tBasemax2))&&(tUserCode != fm.Operator.value))
				    {
				        alert("案件赔付金额超过本分公司最高理赔级别,请 "+tUserName+" 登录把案件上报~!");
				        return false;
				    }
			  }	//for tcustomerno
			}else if (tGiveType == '2'||tGiveType == '3')
       {   
            //var csql2="select customerno from llcase where caseno='"+tRgtNO+"' ";
            mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql13");
	mySql.addSubPara(tRgtNO); 
            var xcustomerno=new Array();
            xcustomerno=easyExecSql(mySql.getString());
       
				    var tManageCom = fm.ManageCom.value.substring(0,2);
				    
				   // var strSql0501 = " select checklevel, username,usercode from llclaimuser where comcode like '"+tManageCom+"%%' and StateFlag = '1' and checklevel = (select max(checklevel) from llclaimuser a , Lduser b where a.comcode like '"+tManageCom+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0') order by comcode" ;
				    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql14");
	mySql.addSubPara(tManageCom);
	mySql.addSubPara(tManageCom);  
				    var tSubReport2 = new Array;
				        tSubReport2 = easyExecSql(mySql.getString());
				    if(tSubReport2 != null)
				    {
				        var tChecklevel = tSubReport2[0][0];
				        var tUserName   = tSubReport2[0][1];
				        var tUserCode   = tSubReport2[0][2];
				    }else{
				        alert("未查询到该分机构的最高级别理赔人员!");
				        return false;
				    }
           // var strSql0502 = "select distinct appendmax from LLClaimPopedom where Popedomlevel = '"+tChecklevel+"'";                 
            mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql15");
	mySql.addSubPara(tChecklevel);
            var tBasemax3  = easyExecSql(mySql.getString());                 
            tBasemax3 = tBasemax3*1;
				           
           for (var k=0;k<xcustomerno.length;k++)
           {         		
            // var maxsql="select sum(realpay) from llclaimpolicy where clmno = '"+tRgtNO+"' and insuredno='"+xcustomerno[k]+"' ";
             mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql16");
	mySql.addSubPara(tRgtNO);
	mySql.addSubPara(xcustomerno[k]);
             var tRealpay=easyExecSql(mySql.getString());  
				     if((tRealpay > tBasemax3)&&(tUserCode != fm.Operator.value))
				     {
				        alert("案件通融、协议赔付金额超过本分公司最高理赔级别,请 "+tUserName+" 登录把案件上报~!");
				        return false;
				     }                    
       
           }
        }else if (tGiveType == '1')         
         {
				    var tManageCom = fm.ManageCom.value.substring(0,2);
				  //  var strSql0501 = " select checklevel, username,usercode from llclaimuser where comcode like '"+tManageCom+"%%' and StateFlag = '1' and checklevel = (select max(checklevel) from llclaimuser a , Lduser b where a.comcode like '"+tManageCom+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0') order by comcode" ;
				    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLClaimAuditMissInputSql");
	mySql.setSqlId("LLClaimAuditMissSql17");
	mySql.addSubPara(tManageCom);
	mySql.addSubPara(tManageCom);
				    var tSubReport2 = new Array;
				        tSubReport2 = easyExecSql(mySql.getString());
				    if(tSubReport2 != null)
				    {
				        var tChecklevel = tSubReport2[0][0];
				        var tUserName   = tSubReport2[0][1];
				        var tUserCode   = tSubReport2[0][2];
				    }else{
				        alert("未查询到该分机构的最高级别理赔人员!");
				        return false;
				    }         	
            if ((tChecklevel=="A"||tChecklevel=="B1"||tChecklevel=="B2"||tChecklevel=="B3")&&(tUserCode != fm.Operator.value))
            {
              alert("案件拒付权限超过本分公司最高理赔级别,请 "+tUserName+" 登录把案件上报~!");	
              return false;
            }             
         
         }                 
				
				    fm.MissionID.value = LLClaimAuditGrid.getRowColData(selno, 7);
				    fm.SubMissionID.value = LLClaimAuditGrid.getRowColData(selno, 8);
				    fm.ActivityID.value = LLClaimAuditGrid.getRowColData(selno, 9);
				    
				    fm.action = "./LLReportMissSave.jsp";
				    submitForm(); //提交
//		} 
	 
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