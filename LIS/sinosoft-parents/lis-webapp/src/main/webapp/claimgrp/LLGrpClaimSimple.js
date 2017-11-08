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

        //报案成功则刷新页面
        //querySelfGrid();
        var i = SelfLLClaimSimpleGrid.getSelNo();
        if (i != '0')
        {
            i = i - 1;
            var tClmNo = SelfLLClaimSimpleGrid.getRowColData(i,1);
            var tClmState = SelfLLClaimSimpleGrid.getRowColData(i,2);
            tClmState = tClmState.substring(0,2);
            var tMissionID = SelfLLClaimSimpleGrid.getRowColData(i,6);
            var tSubMissionID = SelfLLClaimSimpleGrid.getRowColData(i,7);
            
            //var tsql="select missionprop2 from lwmission where missionprop1='"+tClmNo+"' ";
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
			mySql.setSqlId("LLGrpClaimSimpleSql1");
			mySql.addSubPara(tClmNo); 
            var tclmstate=easyExecSql(mySql.getString());
            var isNew;
            if (tclmstate==10)   
            {
        	    isNew=0;
            }else
        	  {
        	    isNew=1;	
        	  }             
            
            location.href="LLGrpSimpleInput.jsp?RptNo="+tClmNo+"&isNew="+isNew+"&clmState="+tClmState+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
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


// 初始化表格2
function querySelfGrid()
{
	 
    initSelfLLClaimSimpleGrid();
    var strSQL = "";
    var tManageCom = fm.ManageCom.value;
    var tCustomerName = fm.CustomerName.value;
    var tOperator = fm.Operator.value;
 /*未加工作流前的SQL   
    strSQL = "select RgtNo,decode(ClmState,'60','结案','处理中'),"
           +" AppntNo,GrpName,Peoples2,RgtDate,Operator,MngCom "
           +" from LLRegister a where appntno is not null  and "
           +" rgtstate = '01' and ClmState != '40' and ClmState != '60'"
           + getWherePart( 'RgtNo' ,'RptNo')
           + getWherePart( 'AppntNo' ,'CustomerNo')
           + getWherePart( 'GrpContNo' ,'GrpContNo')
           if (tCustomerName!= '' && tCustomerName!= null)
           {
               strSQL = strSQL + " and GrpName like '%%" + tCustomerName + "%%'"
           }
           if(tManageCom != '%')
           {
               strSQL = strSQL + " and MngCom like '"+tManageCom+"%%'";
           }

    strSQL = strSQL + " order by RgtNo,RgtDate";
 */  
   /*strSQL = "select missionprop1,(case missionprop2 when '10' then '10-已报案' when '20' then '20-已立案' end ),"
           + "missionprop3,missionprop4,missionprop6,missionid,submissionid,activityid,"           
           + "(select UserName from lduser where UserCode = lwmission.lastoperator),missionprop20, "
           + "(select case count(*) when 0 then '无' else '有' end from es_doc_main where doccode=lwmission.missionprop1), "
           + "(select case count(*) when 0 then '无' else '有' end from LLInqConclusion where clmno=lwmission.missionprop1 and finiflag='0'), "
           + "makedate"
           + " from lwmission where 1=1 "
           + " and activityid = '0000009015'"
           + " and processid = '0000000009'"
           + " and DefaultOperator is null "
           + " and missionprop15 = '03' "*/
           + getWherePart( 'missionprop1' ,'RptNo')
           + getWherePart( 'missionprop3' ,'CustomerNo')
           + getWherePart( 'missionprop7' ,'GrpContNo')
           + getWherePart( 'missionprop6' ,'AccidentDate2')
           mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
			mySql.setSqlId("LLGrpClaimSimpleSql2");
			mySql.addSubPara(fm.RptNo.value); 
			mySql.addSubPara(fm.CustomerNo.value); 
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(fm.AccidentDate2.value); 
           if (tCustomerName!= '' && tCustomerName!= null)
           {
             //  strSQL = strSQL + " and missionprop4 like '%%" + tCustomerName + "%%'"
               mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
			mySql.setSqlId("LLGrpClaimSimpleSql3");
			mySql.addSubPara(fm.RptNo.value); 
			mySql.addSubPara(fm.CustomerNo.value); 
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(fm.AccidentDate2.value); 
			mySql.addSubPara(tCustomerName); 
           }
           if(tManageCom != '%')
           {
               //strSQL = strSQL + " and missionprop20 like '"+tManageCom+"%%'";
               mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
			mySql.setSqlId("LLGrpClaimSimpleSql4");
			mySql.addSubPara(fm.RptNo.value); 
			mySql.addSubPara(fm.CustomerNo.value); 
			mySql.addSubPara(fm.GrpContNo.value); 
			mySql.addSubPara(fm.AccidentDate2.value); 
			mySql.addSubPara(tCustomerName); 
			mySql.addSubPara(tManageCom); 
           }

   // strSQL = strSQL + " order by makedate,missionprop1,missionid";
    //prompt("批量案件查询",strSQL);
    turnPage.queryModal(mySql.getString(),SelfLLClaimSimpleGrid,1,1);
}

function queryGrid()
{
	 
    initLLClaimSimpleGrid();
    var strSQL = "";
    var tManageCom = fm.ManageCom.value;
    var tCustomerName = fm.CustomerName2.value;
    var tOperator = fm.Operator.value;

  /* strSQL = "select missionprop1,(case missionprop2 when '10' then '10-已报案' when '20' then '20-已立案' end ),"
           + "missionprop3,missionprop4,missionprop6,missionid,submissionid,activityid,"
           + "(select UserName from lduser where UserCode = lwmission.lastoperator),missionprop20,"
           + "(select case count(*) when 0 then '无' else '有' end from es_doc_main where doccode=lwmission.missionprop1),"
           + "(select case count(*) when 0 then '无' else '有' end from LLInqConclusion where clmno=lwmission.missionprop1 and finiflag='0'),"
           + "(select accepteddate from llregister where rgtno=lwmission.missionprop1) AcceptedDate from lwmission where 1=1 "
           + " and activityid = '0000009015'"
           + " and processid = '0000000009'"
           + " and DefaultOperator= '"+tOperator+"' "
           + " and missionprop15 = '03' "
           + getWherePart( 'missionprop1' ,'RptNo2')
           + getWherePart( 'missionprop3' ,'CustomerNo2')
           + getWherePart( 'missionprop7' ,'GrpContNo2')
           + getWherePart( 'missionprop6' ,'AccidentDate2')*/
           mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
			mySql.setSqlId("LLGrpClaimSimpleSql5");
			mySql.addSubPara(tOperator); 
			mySql.addSubPara(fm.RptNo2.value); 
			mySql.addSubPara(fm.CustomerNo2.value); 
			mySql.addSubPara(fm.GrpContNo2.value); 
			mySql.addSubPara(fm.AccidentDate2.value); 
			
           if (tCustomerName!= '' && tCustomerName!= null)
           {
               //strSQL = strSQL + " and missionprop4 like '%%" + tCustomerName + "%%'"
               mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
			mySql.setSqlId("LLGrpClaimSimpleSql6");
			mySql.addSubPara(tOperator); 
			mySql.addSubPara(fm.RptNo2.value); 
			mySql.addSubPara(fm.CustomerNo2.value); 
			mySql.addSubPara(fm.GrpContNo2.value); 
			mySql.addSubPara(fm.AccidentDate2.value); 
			mySql.addSubPara(tCustomerName); 
           }
           //zy 2009-9-8 17:50 跟个险保持一致，不进行管理机构的校验
          // if(tManageCom != '%')
          // {
          //     strSQL = strSQL + " and missionprop20 like '"+tManageCom+"%%'";
          // }

   // strSQL = strSQL + " order by AcceptedDate,missionprop1,missionid";
    
    turnPage2.queryModal(mySql.getString(),LLClaimSimpleGrid,1,1);
    
    HighlightByRow();
}

//LLClaimSimpleGrid若该案件自受理日期至操作当日已达到20日或超过20日的，则该案件的显示条目变为红色
function HighlightByRow(){
    for(var i=0; i<LLClaimSimpleGrid.mulLineCount; i++){
    	var accepteddate = LLClaimSimpleGrid.getRowColData(i,13); //受理日期
    	if(accepteddate != null && accepteddate != "")
    	{
    	   if(dateDiff(accepteddate,fm.CurDate.value,"D")>=20)		//受理日期超过20天
    	   {
    		   LLClaimSimpleGrid.setRowClass(i,'warn'); 
    	   }
    	}
    }  
}


function ApplyClaim2()
{
/************************20070824-liuyu-按描述表控制流程*********************/
	  	
			var selno = SelfLLClaimSimpleGrid.getSelNo()-1;
			if (selno <0)
			{
				 alert("请选择要申请处理的报案！");
				 return;
			}						

			if(document.all('ComCode').value.length==2)//两位机构不能进行立案操作
			{
				alert("总公司不能进行立案操作!");
				return false;
			}
						//var oldornewsql="select flowflag from llclaimmanage where managecom='"+document.all('ComCode').value+"'";
						//2009-02-04 zhangzheng 目前没有llclaimmanage，暂时赋值让流程能走
						//var oldornewflag=easyExecSql(oldornewsql);				
						//alert(oldornewflag);
						mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
						mySql.setSqlId("LLGrpClaimSimpleSql7");
						mySql.addSubPara(document.all('ComCode').value); 
						////var oldornewflag=easyExecSql(mySql.getString());
						//7没有被执行？上面这条语句被注掉了
						var oldornewflag='1';
						if(oldornewflag=="1"||oldornewflag=='1')//新
						{
//								else
								//{							  
						       fm.MissionID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 6);
						       fm.SubMissionID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 7);
						       fm.ActivityID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 8);
						       
						       fm.action = "./LLSimpleReportMissSave.jsp";
						       submitForm(); //提交
						    // }
						}
						if(oldornewflag=="0"||oldornewflag=='0')//旧
						{
								//if(document.all('ComCode').value.length>2)//四位能申请
								//{						
						       fm.MissionID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 6);
						       fm.SubMissionID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 7);
						       fm.ActivityID.value = SelfLLClaimSimpleGrid.getRowColData(selno, 8);
						       
						       fm.action = "./LLSimpleReportMissSave.jsp";
						       submitForm(); //提交			
						     //}
						     //else//两位机构不能申请
							  //{
							  			//alert("此操作只能由四位机构来操作!");
							  			//return;							  			
							   //}									
			      }
}

//LLClaimSimpleGrid点击事件
function SelfLLClaimSimpleGridClick()
{
}

//SelfLLClaimSimpleGrid点击事件
function LLClaimSimpleGridClick()
{                       
	                    HighlightByRow();
						//var oldornewsql="select flowflag from llclaimmanage where managecom='"+document.all('ComCode').value+"'";
						//2009-02-04 zhangzheng 目前没有llclaimmanage，暂时赋值让流程能走
						//var oldornewflag=easyExecSql(oldornewsql);
						//alert(oldornewflag);
						mySql = new SqlClass();
						mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
						mySql.setSqlId("LLGrpClaimSimpleSql8");
						mySql.addSubPara(document.all('ComCode').value); 
						////var oldornewflag=easyExecSql(mySql.getString()); //这条语句也被注掉了，不执行？
						if(document.all('ComCode').value.length==2)//总公司不能进行立案操作
						{
							alert("总公司不能进行立案操作!");
							return false;
						}
						var oldornewflag='1';
						if(oldornewflag=="1"||oldornewflag=='1')//新
						{
								//else
								//{			  		
						           var i = LLClaimSimpleGrid.getSelNo();
						           if (i != '0')
						           {
						               i = i - 1;
						               var tRptNo = LLClaimSimpleGrid.getRowColData(i,1); 
						               var tMissionID = LLClaimSimpleGrid.getRowColData(i,6);
						               var tSubMissionID = LLClaimSimpleGrid.getRowColData(i,7);    
						                       
						               //var tsql="select missionprop2 from lwmission where missionprop1='"+tRptNo+"' ";
						               mySql = new SqlClass();
										mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
										mySql.setSqlId("LLGrpClaimSimpleSql9");
										mySql.addSubPara(tRptNo); 
						               var tclmstate=easyExecSql(mySql.getString());
						               var isNew;
						               if (tclmstate==10)   
						               {
						               		isNew=0;
						               }
						               else
						               {
						               	  isNew=1;	
						               }
						               location.href="LLGrpSimpleInput.jsp?isNew="+isNew+"&RptNo="+tRptNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
						           }
								//}
			     }
			     if(oldornewflag=="0"||oldornewflag=='0')//旧
						{
				        var i = LLClaimSimpleGrid.getSelNo();
				        if (i != '0')
				        {
				            i = i - 1;
				            var tRptNo = LLClaimSimpleGrid.getRowColData(i,1); 
				            var tMissionID = LLClaimSimpleGrid.getRowColData(i,6);
				            var tSubMissionID = LLClaimSimpleGrid.getRowColData(i,7);    
				                    
				            //var tsql="select missionprop2 from lwmission where missionprop1='"+tRptNo+"' ";
				             mySql = new SqlClass();
										mySql.setResourceName("claimgrp.LLGrpClaimSimpleInputSql");
										mySql.setSqlId("LLGrpClaimSimpleSql10");
										mySql.addSubPara(tRptNo); 
				            var tclmstate=easyExecSql(mySql.getString());
				            var isNew;
				            if (tclmstate==10)   
				            {
				            		isNew=0;
				            }
				            else
				            {
				            	  isNew=1;	
				            }
				            location.href="LLGrpSimpleInput.jsp?isNew="+isNew+"&RptNo="+tRptNo+"&MissionID="+tMissionID+"&SubMissionID="+tSubMissionID;
				        }
						}
			     
			     
}

//[申请]按钮
function ApplyClaim()
{
     location.href="LLGrpSimpleInput.jsp?";
}

//报案信息导出
function PrintReportClass()
{
     location.href="LLGrpReportSimpleInput.jsp?";
     //window.open("./LLGrpReportSimpleInput.jsp?");
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

function getin()
{

   var tFlag  = "TOSIM";
   var tRptNo = fm.RptNo.value;
//   var strUrl = "../claim/GrpCustomerDiskInput.jsp?grpcontno="+tRptNo+"&Flag="+tFlag;
   var strUrl = "../claim/GrpCustomerDiskMain.jsp?Flag="+tFlag;
   //showInfo=window.open(strUrl,"被保人清单导入","");
   showInfo=window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}