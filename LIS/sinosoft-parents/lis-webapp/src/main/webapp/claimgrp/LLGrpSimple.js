var showInfo;
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var turnPage3 = new turnPageClass();
var mySql = new SqlClass();
//由客户查询（LLLdPersonQuery.js）返回单条记录时调用
function afterQueryLL(arr)
{
  
    fm.customerNo.value   = arr[0];//投保人客户号
    fm.customerName.value = arr[1];//投保人姓名
    fm.customerSex.value  = arr[2];//性别
    fm.customerAge.value  = arr[3];//年纪
    fm.IDNo.value         = arr[6];//ID号
    fm.IDTypeName.value   = arr[5];//证件类型名称
    fm.IDType.value       = arr[7];//证件类型
    showOneCodeName('sex','customerSex','SexName');//性别
    fm.QueryCont2.disabled = false; //返回数据后，将QueryCont2设置为可用
    fm.QueryCont3.disabled = false; //返回数据后，将QueryCont3设置为可用
    //初始化录入域
    //fm.AccidentDate2.value = "";
    for(var j=0;j<fm.claimType.length;j++)
    {
        fm.claimType[j].checked = false;
    }

}

//理赔责任控制信息
function ctrlclaimduty()
{
    var strUrl="../appgrp/CtrlClaimDuty.jsp?ProposalGrpContNo="+fm.GrpContNo.value+"&GrpContNo="+fm.GrpContNo.value+"&LoadFlag=2&LPFlag=1";
    //  window.open(strUrl,"案件核赔履历查询");
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

    //showInfo = window.open();
    //parent.fraInterface.window.location = "../appgrp/CtrlClaimDuty.jsp?scantype="+scantype+"&MissionID="+MissionID+"&ManageCom="+ManageCom+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&GrpContNo="+fm.GrpContNo.value+"&ProposalGrpContNo="+tGrpContNo+"&LoadFlag="+LoadFlag;
}

function afterQuery(mRgtNo){
if(mRgtNo!=''){
document.all('RptNo').value = mRgtNo;
queryRegister();
}
}

//打开发起调查
function showBeginInq()
{
    //var JustStateSql="select COUNT(*) from lwmission where activityid in ('0000005125','0000005145','0000005165','0000005175') and missionprop1='"+fm.RptNo.value+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
	mySql.setSqlId("LLGrpSimpleSql1");
	mySql.addSubPara(fm.RptNo.value ); 
    var JustStateCount=easyExecSql(LLGrpSimple);
    if(parseInt(JustStateCount)>0)
    {      				
    		alert("该案件已经发起调查，请不要重复调查!");
    		return;
    }       
    var strUrl="LLInqApplyMain.jsp?claimNo="+fm.RptNo.value+"&custNo="+fm.customerNo.value+"&custName="+fm.customerName.value+"&initPhase=01";
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//查看调查
function showQueryInq()
{
  //---------------------------------------
  //判断该赔案是否存在调查
  //---------------------------------------
    /*var strSQL = "select count(1) from LLInqConclusion where "
                + "ClmNo = '" + fm.RptNo.value+"'";*/
     mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
	mySql.setSqlId("LLGrpSimpleSql2");
	mySql.addSubPara(fm.RptNo.value ); 
    var tCount = easyExecSql(LLGrpSimple);
    if (tCount == "0")
    {
        alert("该赔案还没有调查信息！");
        return;
    }
    var strUrl="LLInqQueryMain.jsp?claimNo="+fm.RptNo.value;
      window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//立案查询
function queryRegister()
{
    
    var rptNo = document.all('RptNo').value;
    var tFlag = fm.Flag.value;
    //alert("rptNo="+rptNo);
    getGrpstandpay();
    if(rptNo == "")
    {
      
     fm.updatebutton.disabled = true;
     fm.QueryCont2.disabled = true;
     fm.QueryCont3.disabled = true;
     //fm.QueryReport.disabled = true;
     
     fm.dutySet.disabled = true;
     fm.QuerydutySet.disabled = true;
     fm.addUpdate.disabled = true;
     fm.simpleClaim.disabled = true;
     divSimpleClaim2.style.display= "";
     divSimpleClaim3.style.display= "none";
    }else{
      
     fm.updatebutton.disabled = false;
     fm.deletebutton.disabled = false;
     fm.QueryCont2.disabled = false;
     fm.QueryCont3.disabled = false;
     //fm.QueryReport.disabled = false;
     if(tFlag =="FROMSIMALL")
        fm.dutySet.disabled = true;
     else
         fm.dutySet.disabled = false;
     fm.QuerydutySet.disabled = false;
     fm.addUpdate.disabled = false;
     fm.simpleClaim.disabled = false;
    }
      
    //检索事件号、意外事故发生日期(一条)
   /* var strSQL1 = "select LLCaseRela.CaseRelaNo,LLAccident.AccDate from LLCaseRela,LLAccident where LLCaseRela.CaseRelaNo = LLAccident.AccNo and "
                + "LLCaseRela.CaseNo = '"+rptNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
	mySql.setSqlId("LLGrpSimpleSql3");
	mySql.addSubPara(rptNo); 
    var AccNo = easyExecSql(mySql.getString());
    //检索立案号及其他立案信息(一条)
    /*var strSQL2 = "select AppntNo,GrpName,GrpContNo,RgtNo,Peoples2,AppPeoples,RgtantName,AccidentReason,RgtConclusion,RgtClass,clmState,RiskCode,Operator from llregister where "
                + "rgtno = '"+rptNo+"'";*/
    /*var strSQL2 = "select AppntNo,GrpName,GrpContNo,RptNo,Peoples2,'','',AccidentReason,rgtflag,RgtClass,AvaliReason,RiskCode,Operator from llreport where "
                + "rptno = '"+rptNo+"'";   //prompt("",strSQL2); */
   /* var strSQL2 = "select RptNo,RptorName,RptorPhone,RptorAddress,Relation,(select codename from ldcode where codetype='relation' and code=Relation),"
    			+" RptMode,AccidentSite,AccidentReason,(select codename from ldcode where codetype='lloccurreason' and code=AccidentReason),"
    			+" RptDate,MngCom,Operator,RgtClass,GRPCONTNO,APPNTNO,PEOPLES2,GRPNAME,RiskCode from LLReport where "
                + "RptNo = '"+rptNo+"'";  */
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
	mySql.setSqlId("LLGrpSimpleSql4");
	mySql.addSubPara(rptNo);        
    var RptContent = easyExecSql(mySql.getString());//prompt("",strSQL2);
    //更新页面内容
    
    if(AccNo!=null)
    {
        fm.AccNo.value = AccNo[0][0];
        fm.AccidentDate.value = AccNo[0][1];
    }
    
   /*if(RptContent!=null)
    {
        fm.GrpCustomerNo.value = RptContent[0][0];
        fm.GrpName.value = RptContent[0][1];
        fm.GrpContNo.value = RptContent[0][2];
        fm.RptNo.value = RptContent[0][3];
        fm.Peoples.value = RptContent[0][4];
        fm.customerName.value = RptContent[0][6];
        fm.occurReason.value = RptContent[0][7];
        fm.clmState.value = RptContent[0][8];
        fm.RgtClass.value = RptContent[0][9];
        //fm.clmState.value = RptContent[0][10];
        fm.Polno.value = RptContent[0][11];
        
       var ttoperator = new Array;
       var ttsql="select operator from llregister where rgtno='"+fm.RptNo.value+"' ";    	         
       ttoperator = easyExecSql(ttsql);        	      	  
        	        	      	                
        if(ttoperator==null||ttoperator=="")
        {
           fm.tOperator.value = RptContent[0][12];  
        }else
        	{
             fm.tOperator.value = ttoperator; //此处取立案操作员，用于对权限的校验        		
        	}         
        

        showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    }*/
    if(RptContent!=null){
    fm.RptNo.value = RptContent[0][0];
    fm.RptorName.value = RptContent[0][1];
    fm.RptorPhone.value = RptContent[0][2];
    fm.RptorAddress.value = RptContent[0][3];
    fm.Relation.value = RptContent[0][4];//申请人与事故人关系 
    fm.RelationName.value = RptContent[0][5];
//    fm.RptMode.value = RptContent[0][6];
    fm.AccidentSite.value = RptContent[0][7];
    fm.occurReason.value = RptContent[0][8];//出险原因
    fm.ReasonName.value = RptContent[0][9];
    fm.RptDate.value = RptContent[0][10];
    fm.MngCom.value = RptContent[0][11];
    fm.OOperator.value = RptContent[0][12];//alert("RptContent[0][10]: "+RptContent[0][10]);

    fm.GrpContNo.value = RptContent[0][14];
    fm.GrpCustomerNo.value = RptContent[0][15];
    fm.Peoples.value = RptContent[0][16];
    fm.GrpName.value = RptContent[0][17];
    fm.Polno.value = RptContent[0][18];
}
   /* var strSQL2 = "select rgtno,rgtantname,rgtantphone,rgtantaddress,relation,accidentsite,rgtdate,operator,mngcom,assigneetype,assigneecode,assigneename,assigneesex,assigneephone,assigneeaddr,assigneezip,accidentreason,RgtConclusion,NoRgtReason,RgtClass,(case RgtClass when '1' then '个人' when '2' then '团体' when '3' then '家庭' end),GRPCONTNO,APPNTNO,PEOPLES2,GRPNAME,RiskCode,clmstate,DeferRgtReason,AcceptedDate from llregister where "
        + "rgtno = '"+rptNo+"'";*/
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
	mySql.setSqlId("LLGrpSimpleSql5");
	mySql.addSubPara(rptNo);    
    var RptContent = new Array();
    RptContent = easyExecSql(mySql.getString());
    if(RptContent!=null){
	    fm.AssigneeType.value = RptContent[0][9];
	    fm.AssigneeCode.value = RptContent[0][10];
	    fm.AssigneeName.value = RptContent[0][11];
	    fm.AssigneeSex.value = RptContent[0][12];
	    fm.AssigneePhone.value = RptContent[0][13];
	    fm.AssigneeAddr.value = RptContent[0][14];
	    fm.AssigneeZip.value = RptContent[0][15];
	    fm.AcceptedDate.value = RptContent[0][28];
    }
    showOneCodeName('sex','AssigneeSex','AssigneeSexName');//受托人性别
    showOneCodeName('AssigneeType','AssigneeType','AssigneeTypeName');//受托人类型
  //alert(document.all('isNew').value);   
  if (document.all('isNew').value =='0')   //已报案，没立案
   {               
     fm.addbutton.disabled = false;
     fm.updatebutton.disabled = true;
     fm.deletebutton.disabled = true;
     //fm.addbutton2.disabled = true;
     //fm.updateFeebutton.disabled = true;
     //fm.deleteFeebutton.disabled = true;
     fm.DiskInput.disabled = true;
     //fm.QueryReport.disabled = true;
     fm.dutySet.disabled = true;
     fm.QuerydutySet.disabled = true;   
     fm.simpleClaim.disabled = true; 
     divSimpleClaim2.style.display= "";
     divSimpleClaim3.style.display= "none";      
   }else if (document.all('isNew').value =='1')      //已立案，没复核
   {          
     fm.addbutton.disabled = true;
     fm.updatebutton.disabled = false;
     fm.deletebutton.disabled = false;
     //fm.addbutton2.disabled = false;
     //fm.updateFeebutton.disabled = false;
     //fm.deleteFeebutton.disabled = false;
     //fm.QueryReport.disabled = false;
     fm.dutySet.disabled = false;
     fm.QuerydutySet.disabled = false;   
     fm.simpleClaim.disabled = false; 
     divSimpleClaim2.style.display= "";
     divSimpleClaim3.style.display= "none";         
   } else if (document.all('isNew').value =='2')      //复核
   {
   	//var strSQL3="select auditconclusion from LLClaimUWMain where clmno='"+rptNo+"'";
   	mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
	mySql.setSqlId("LLGrpSimpleSql6");
	mySql.addSubPara(rptNo);    
   	var SimpleConclusion2 = easyExecSql(mySql.getString());//prompt("",strSQL3);
   	if(SimpleConclusion2!=null&&SimpleConclusion2!="null"){
   		fm.SimpleConclusion2.value=SimpleConclusion2;
   		showOneCodeName('llexamconclusion2','SimpleConclusion2','SimpleConclusion2Name');//审核结论
   	}
   	//alert("SimpleConclusion2:  "+SimpleConclusion2);
     fm.QueryPerson.disabled = true;
     fm.addbutton.disabled = true;
     fm.updatebutton.disabled = true;
     fm.deletebutton.disabled = true;
     fm.DiskInput.disabled = true;
     fm.DiskOutput.disabled = true;
     fm.QueryCont2.disabled = true;
     fm.QueryCont3.disabled = true;
     //fm.addbutton2.disabled = true;
     //fm.updateFeebutton.disabled = true;
     //fm.deleteFeebutton.disabled = true;
     //fm.QueryReport.disabled = true;
     fm.dutySet.disabled = true;
     fm.QuerydutySet.disabled = false;   
     divSimpleClaim2.style.display= "none";
     divSimpleClaim3.style.display= "";
     divLLAudit.style.display = "";
//     fm.ConclusionUp.disabled = true;          
     //fm.ConclusionSave.disabled = true;
//     fm.ConclusionBack.disabled = true;
     
     if( fm.SimpleConclusion2.value == '0'||fm.SimpleConclusion2.value == '1'||fm.SimpleConclusion2.value == '4'){
      	fm.ConclusionSave.disabled = false;
     }else{
     	//fm.ConclusionSave.disabled = true;
     }
     
     if( fm.SimpleConclusion2.value == '5'){//案件回退      
//      fm.ConclusionBack.disabled = false;
     }else{
//      fm.ConclusionBack.disabled = true;
     }
     
   }else if (document.all('isNew').value =="" || document.all('isNew').value ==null)
   	{   	 
   	 fm.QueryPerson.disabled = false;
     fm.addbutton.disabled = false;
     fm.updatebutton.disabled = false;
     fm.deletebutton.disabled = false;
     fm.QueryCont2.disabled = false;
     fm.QueryCont3.disabled = false;
     //fm.addbutton2.disabled = false;
     //fm.updateFeebutton.disabled = false;
     //fm.deleteFeebutton.disabled = false;
     //fm.QueryReport.disabled = false;
     fm.dutySet.disabled = true;
     fm.QuerydutySet.disabled = false;   
     divSimpleClaim2.style.display= "";
     divSimpleClaim3.style.display= "none";  
//     fm.ConclusionUp.disabled = false;   
     fm.ConclusionSave.disabled = false;
//     fm.ConclusionBack.disabled = false; 
   	}           
     
   
    
  /*  if(RptContent!=null)
    {
        fm.GrpCustomerNo.value = RptContent[0][0];
        fm.GrpName.value = RptContent[0][1];
        fm.GrpContNo.value = RptContent[0][2];
        fm.RptNo.value = RptContent[0][3];
        fm.Peoples.value = RptContent[0][4];
        fm.customerName.value = RptContent[0][6];
        fm.occurReason.value = RptContent[0][7];
        fm.clmState.value = RptContent[0][8];
        fm.RgtClass.value = RptContent[0][9];
        fm.clmState.value = RptContent[0][10];
        fm.Polno.value = RptContent[0][11];
        fm.tOperator.value = RptContent[0][12];

        showOneCodeName('lloccurreason','occurReason','ReasonName');//出险原因
    
        fm.clmState2.value = RptContent[0][10]
        //结案按键控制
        if( fm.clmState2.value == '60' )
        {
             fm.QueryPerson.disabled = true;
             fm.addbutton.disabled = true;
             fm.Inputbutton.disabled = true;
             fm.addbutton.disabled = true;
             fm.addbutton2.disabled = true;
             fm.updatebutton.disabled = true;
             fm.deletebutton.disabled = true;
             fm.QueryCont2.disabled = true;
             fm.QueryCont3.disabled = true;
             fm.QueryReport.disabled = true;
             fm.dutySet.disabled = true;
             fm.addUpdate.disabled = true;
             fm.simpleClaim.disabled = true;
             fm.SimpleConclusion.value = '0';
             showOneCodeName('llexamconclusion','SimpleConclusion','SimpleConclusionName');//出险原因
             divSimpleClaim2.style.display= "none";
             divSimpleClaim3.style.display= "";
         }else if( fm.clmState2.value == '40')//复核按键控制
         {
             fm.QueryPerson.disabled = true;
             fm.addbutton.disabled = true;
             fm.addbutton.disabled = true;
             fm.addbutton2.disabled = true;
             fm.updatebutton.disabled = true;
             fm.deletebutton.disabled = true;
             fm.QueryCont2.disabled = true;
             fm.QueryCont3.disabled = true;
             fm.QueryReport.disabled = true;
             fm.dutySet.disabled = true;
             fm.addUpdate.disabled = true;
            if(fm.Flag.value == '2')
            {
                 divSimpleClaim2.style.display= "none";
                 divSimpleClaim3.style.display= "";
            }
            else
            {
                 divSimpleClaim2.style.display= "";
                 divSimpleClaim3.style.display= "none";
            }
        }
        else
        {
             divSimpleClaim2.style.display= "";
             divSimpleClaim3.style.display= "none";
        }

    } */
  
    //var strSQL4 = "select count(*) CustomerNo from llsubreport where subrptno = '"+rptNo+"'";
    mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
	mySql.setSqlId("LLGrpSimpleSql7");
	mySql.addSubPara(rptNo);   
    var CustomerNoCount = easyExecSql(mySql.getString());
    if(CustomerNoCount!=null)
    {
        //fm.PeopleNo.value = CustomerNoCount[0][0];
    }
    
   /* var strSQL5 = "select reasoncode from Llappclaimreason where "
                + "RgtNo = '"+rptNo+"'";
    var ReasonCode = easyExecSql(strSQL5);
    if (ReasonCode!=null||ReasonCode!="")
    {
      fm.occurReason.value=	ReasonCode[0][0].substring(0,1);
      showCodeName('occurReason','occurReason','ReasonName');      
    } */
    
    //****************************************************
    //更新页面权限
    //****************************************************
    fm.AccidentDate.readonly = true;
    fm.claimType.disabled=true;
    
    //检索分案关联出险人信息(多条)
   /* var strSQL1 = "select count(*) from LDPerson where "
                + "CustomerNo in("
                + "select CustomerNo from LLSubReport where SubRptNo = '"+ rptNo +"')";*/
 //     alert("rptNo"+rptNo);          
   mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
	mySql.setSqlId("LLGrpSimpleSql8");
	mySql.addSubPara(rptNo);   
    var count = easyExecSql(mySql.getString());
   
    if(count > 0)
    {
       /* var strSQL3 = "select a.CustomerNo,a.Name,a.Sex,a.Birthday,"
                    + " nvl(SocialInsuFlag,0) as SocialInsuFlag,"
                	+ " (case when trim(nvl(SocialInsuFlag, 0)) = '1' then '有' else '无' end) as 是否有社保标志, "
                    +"(select codename from ldcode where "
                    + " codetype = 'idtype' and code = a.IDType),a.IDNo,"
                    + " a.IDType, b.accdate,(select case count(*) when 0 then '无' else '有' end from es_doc_main where doccode='"+ rptNo +"' and printcode=a.customerno) " 
                    + " from LDPerson a,llsubreport b where "
                    + " a.CustomerNo=b.CustomerNo "                 
                    + " and b.subrptno = '"+ rptNo +"' order by to_number(b.condoleflag)"; */
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql9");
		mySql.addSubPara(rptNo);      
		mySql.addSubPara(rptNo);     
       /*var strSQL3 = "select a.CustomerNo,a.Name,a.Sex,a.Birthday,"
                    + " a.VIPValue,(select codename from ldcode where "
                    + " codetype = 'idtype' and code = a.IDType),a.IDNo,"
                    + " a.IDType, b.accdate from LDPerson a,LLCase b where "
                    + " a.CustomerNo=b.CustomerNo "                 
                    + " and b.CaseNo = '"+ rptNo +"' order by lpad(b.seconduwflag,4,'0')";                       
       */           // prompt("",strSQL3) ;
        turnPage3.queryModal(mySql.getString(),SubReportGrid);
        //prompt("查询出险人",strSQL3);
        if (SubReportGrid.getRowColData(0,1) != null && SubReportGrid.getRowColData(0,1) != "")
        {
            SubReportGridClick(0);
        }
    }
   
  //查询审核结论
    queryAudit();
}

//选中SubReportGrid响应事件,tPhase=0为初始化时调用
function SubReportGridClick(tPhase)
{
  //********************************************Beg
  //置空相关表单
  //********************************************
  fm.customerName.value = "";
  fm.customerAge.value = "";
  fm.customerSex.value = "";
  fm.SexName.value = "";
  //fm.AccidentDate2.value = "";
  fm.claimType.value = "";
  fm.IDType.value = "";
  fm.IDTypeName.value = "";
  fm.IDNo.value = "";
  fm.QueryCont3.disabled = false;
  fm.QueryCont2.disabled = false;
  fm.dutySet.disabled = false;
  //理赔类型置空
    for (var j = 0;j < fm.claimType.length; j++)
    {
        fm.claimType[j].checked = false;
    }
    //********************************************End
	
    //取得数据
    var i = "";
    if (tPhase == "0")
    {
        i = 1;
    }
    else
    {
        i = SubReportGrid.getSelNo();
    }
    if (i != '0')
    {
        i = i - 1;
        fm.customerNo.value = SubReportGrid.getRowColData(i,1);
        fm.customerName.value = SubReportGrid.getRowColData(i,2);
        fm.customerSex.value = SubReportGrid.getRowColData(i,3);
        fm.customerAge.value = calAge(SubReportGrid.getRowColData(i,4));
        fm.IDTypeName.value = SubReportGrid.getRowColData(i,7);
        fm.IDNo.value = SubReportGrid.getRowColData(i,8);
        fm.IDType.value = SubReportGrid.getRowColData(i,9);
        showOneCodeName('sex','customerSex','SexName');//性别
        fm.AccidentDate.value = SubReportGrid.getRowColData(i,10);//出险日期
    }

    //查询获得理赔类型
    var tClaimType = new Array;
    /*var strSQL1 = "select ReasonCode from llreportreason where "
                + " rpNo = '"+fm.RptNo.value+"'"
                + " and CustomerNo = '"+fm.customerNo.value+"'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql10");
		mySql.addSubPara(fm.RptNo.value);      
		mySql.addSubPara(fm.customerNo.value);             
    tClaimType = easyExecSql(mySql.getString());
    if (tClaimType == null)
    {
        alert("理赔类型为空，请检查此记录的有效性！");
        return;
    }
    else
    {
        for(var j=0;j<fm.claimType.length;j++)
        {
            for (var l=0;l<tClaimType.length;l++)
            {
                var tClaim = tClaimType[l].toString();
                tClaim = tClaim.substring(tClaim.length-2,tClaim.length);//取理赔类型后两位
                if(fm.claimType[j].value == tClaim)
                {
                    fm.claimType[j].checked = true;
                }
            }
        }
    }
//根据选中的出险人显示相关的理算结果
afterMatchDutyPayQuery();
}

//选中PolDutyCodeGrid响应事件
function PolDutyCodeGridClick() {
	//alert(document.all('Flag').value);
    if(document.all('Flag').value != '2'){
	    //清空表单
	    fm.GiveType.value = "";//赔付结论
	    fm.RealPay.value = "";
	    fm.AdjReason.value = "";//调整原因
	    fm.AdjReasonName.value = "";//
	    fm.AdjRemark.value = "";//调整备注
	    fm.GiveTypeDesc.value = "";//拒付原因代码
	    fm.GiveReason.value = "";//拒付依据
	    fm.SpecialRemark.value = "";//特殊备注
	    var tRiskCode = '';
	    //设置按钮
	    if(fm.clmState2.value == '60'){
	    fm.addUpdate.disabled = true;//添加修改
	    }else{
	    fm.addUpdate.disabled = false;//添加修改
	    }
	    //得到mulline信息
	    var i = PolDutyCodeGrid.getSelNo();
	    if (i != '0')
	    {
	        i = i - 1;
	        fm.tPolNo.value = PolDutyCodeGrid.getRowColData(i,1);//保单号
	        fm.GiveType.value = PolDutyCodeGrid.getRowColData(i,13);//赔付结论
	        fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,21);
	        fm.AdjReason.value = PolDutyCodeGrid.getRowColData(i,22);//调整原因
	        fm.AdjReasonName.value = PolDutyCodeGrid.getRowColData(i,23);//
	        fm.AdjRemark.value = PolDutyCodeGrid.getRowColData(i,24);//调整备注
	        fm.GiveTypeDesc.value = PolDutyCodeGrid.getRowColData(i,15);//拒付原因代码
	        fm.GiveReason.value = PolDutyCodeGrid.getRowColData(i,17);//拒付依据
	        fm.SpecialRemark.value = PolDutyCodeGrid.getRowColData(i,18);//特殊备注
	        showOneCodeName('llpayconclusion','GiveType','GiveTypeName');
	        showOneCodeName('llprotestreason','GiveTypeDesc','GiveTypeDescName');
	        tRiskCode = PolDutyCodeGrid.getRowColData(i,3);//险种编码
	    }
    	//帐	户型险种判断，是的不允许修改理算金额
	    //var sql1 = " select insuaccflag From lmrisk where riskcode = '"+tRiskCode+"'";
	    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql11");
		mySql.addSubPara(tRiskCode);      
	  
	    var tInsuaccFlag = easyExecSql(mySql.getString());
	    //长险险种判断，是的不允许修改理算金额
	   // var sql2 = " select riskperiod from lmriskapp where riskcode = '"+tRiskCode+"'";
	   mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql12");
		mySql.addSubPara(tRiskCode);  
	    var tRiskPeriod = easyExecSql(mySql.getString());
	    //显示隐藏层divBaseUnit5.style.display= "";
//	    if(tInsuaccFlag != 'Y' && tRiskPeriod != 'L' && document.all('isNew').value =='1'){
		    divBaseUnit5.style.display= "";
		    choiseGiveTypeType();
//	    }else{
//	    	divBaseUnit5.style.display= "none";
//	    }
    }
}

//对保项增加修改
function AddUpdate()
{
    if(PolDutyCodeGrid.getSelNo() <= 0)
    {
        alert("请先选择要处理的保项信息,再执行此操作！");
        return;
    }
        
    //检查保项金额调整
    if(checkAdjMoney()==null)
    {
    	return true;
    }
    
    var i = PolDutyCodeGrid.getSelNo() - 1;//得到焦点行

    PolDutyCodeGrid.setRowColData(i,13,fm.GiveType.value);//赔付结论
    PolDutyCodeGrid.setRowColData(i,15,fm.GiveTypeDesc.value);//拒付原因代码
    PolDutyCodeGrid.setRowColData(i,17,fm.GiveReason.value);//拒付依据
    PolDutyCodeGrid.setRowColData(i,18,fm.SpecialRemark.value);//特殊备注
    PolDutyCodeGrid.setRowColData(i,21,fm.RealPay.value);
    PolDutyCodeGrid.setRowColData(i,22,fm.AdjReason.value);//调整原因
    PolDutyCodeGrid.setRowColData(i,23,fm.AdjReasonName.value);//
    PolDutyCodeGrid.setRowColData(i,24,fm.AdjRemark.value);//调整备注
    if(fm.GiveType.value == 0){
    	PolDutyCodeGrid.setRowColData(i,14,"给付");//赔付结论名称
    }else if(fm.GiveType.value == 1){
    	PolDutyCodeGrid.setRowColData(i,14,"拒付");//赔付结论名称
    }else if(fm.GiveType.value == 2){
    	PolDutyCodeGrid.setRowColData(i,14,"通融赔付");//赔付结论名称
    }else if(fm.GiveType.value == 3){
    	PolDutyCodeGrid.setRowColData(i,14,"协议赔付");//赔付结论名称
    }
    
    fm.saveUpdate.disabled = false;//保存修改
}

//对保项修改保存到后台
function SaveUpdate() {
	/*
   if (fm.GiveType.value==2||fm.GiveType.value==3)
    {      
      var tsql="select distinct b.appendmax from llclaimuser a ,llclaimpopedom b where a.checklevel=b.popedomlevel and a.usercode='"+document.all('Operator').value+"' ";
      var tappndmax=new Array;
      tappndmax=easyExecSql(tsql);
      
      if (tappndmax<document.all('RealPay').value) 
      {
      	 alert("您的通融、协议赔付权限不够！");
      	 return false;
      }     	
    }else if (fm.GiveType.value==1)
    {
      var tsql="select checklevel from llclaimuser where usercode='"+document.all('Operator').value+"' ";	
      var tchecklevel=new Array;
      tchecklevel=easyExecSql(tsql);
      if (tchecklevel=="A"||tchecklevel=="B1"||tchecklevel=="B2"||tchecklevel=="B3")
      {
        alert("您没有拒付权限！");	
        return false;
      }    
    }    
    //添加拒付校验*/
    //在复核时校验
    
    fm.action = './LLClaimAuditGiveTypeSave.jsp';
    fm.fmtransact.value = "UPDATE";
    submitForm();
    fm.saveUpdate.disabled = true;//保存修改
}

//选择赔付结论
function choiseGiveTypeType()
{
    if (fm.GiveType.value == '0')
  {
        divGiveTypeUnit1.style.display= "";
        divGiveTypeUnit2.style.display= "none";
        divGiveTypeUnit3.style.display= "none";
    }
    else if (fm.GiveType.value == '1')
    {
        divGiveTypeUnit1.style.display= "none";
        divGiveTypeUnit2.style.display= "";
        divGiveTypeUnit3.style.display= "none";
    }
    else if (fm.GiveType.value == '2'||fm.GiveType.value == '3')
    {
        divGiveTypeUnit1.style.display= "";
        divGiveTypeUnit2.style.display= "none";
        divGiveTypeUnit3.style.display= "none";        
    }
}

//进行保险责任匹配
function showMatchDutyPay()
{    
/*var strSQL = "select count(*) from lcinsureaccclass where accascription = '0'"
                + " and grpcontno = '" + fm.GrpContNo.value+"'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql13");
		mySql.addSubPara(fm.GrpContNo.value);  
    var tCount = easyExecSql(mySql.getString());//alert(tCount); return false;
    if(tCount > 0){
       if(confirm("您确定已经做了归属?"))
      {

    mOperate="MATCH||INSERT";
    var showStr="正在进行保险责任匹配操作，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.hideOperate.value=mOperate;
    fm.action = "./LLClaimRegisterMatchCalSave.jsp";
    fm.target="fraSubmit";
    fm.submit(); //提交

      }else{
        alert("请到保全做归属!");
        return false;
      }
    }else{

    mOperate="MATCH||INSERT";
    var showStr="正在进行保险责任匹配操作，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.hideOperate.value=mOperate;
    fm.action = "./LLClaimRegisterMatchCalSave.jsp";
    fm.target="fraSubmit";
    fm.submit(); //提交

    }
}

//匹配后的查询

function afterMatchDutyPayQuery()
{
    var tSql;
    var arr;

    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;          //客户号

    //查询整个赔案的金额
  /*  tSql = " select a.StandPay ,a.BeforePay,a.BalancePay,a.RealPay,a.DeclinePay,"
       +" '','','' "
       +" from LLClaim a  where 1=1 "
       +" and a.caseno = '"+tClaimNo+"'"*/
       ;//prompt("",tSql);
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql14");
		mySql.addSubPara(tClaimNo);  
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,ClaimGrid);
    }
    else
    {
        initClaimGrid();
    }
    
        //查询LLClaimPolicy,查询保单理赔类型层面的信息
   /* tSql = " select a.ContNo,a.PolNo,a.GetDutyKind,"
       +" a.CValiDate,b.PaytoDate,"
       +" a.RiskCode,(select RiskName from LMRisk d where d.RiskCode = a.RiskCode), "
       +" a.RealPay "
       +" from LLClaimPolicy a ,LCPol b where 1=1 "
       +" and a.PolNo = b.PolNo"       
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.insuredno= '"+tCustNo+"'";*/
	//prompt("保单计算信息",tSql);
	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql15");
		mySql.addSubPara(tClaimNo);  
		mySql.addSubPara(tCustNo);  
    arr = easyExecSql(  mySql.getString()  );
    if (arr)
    {
        displayMultiline(arr,PolDutyKindGrid);
    }
    else
    {
        initPolDutyKindGrid();
    }
    
    //查询LLClaimDutyKind，按照赔案理赔类型进行查找
    /*tSql = " select a.GetDutyKind ,"
       +" (select c.codename from ldcode c where c.codetype = 'llclaimtype' and c.code=a.GetDutyKind),"
       +" a.TabFeeMoney,a.SelfAmnt,a.StandPay,a.SocPay,a.RealPay,RealPay "
       +" from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"*/
//    prompt("查询LLClaimDutyKind，按照赔案理赔类型进行查找",tSql);
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql16");
		mySql.addSubPara(tClaimNo);  
    arr = easyExecSql( mySql.getString() );
    if (arr)
    {
        displayMultiline(arr,DutyKindGrid);
    }
    else
    {
        initDutyKindGrid();
    }
    
//alert(651);
    initPolDutyCodeGrid();
    //查询LLClaimDetail,查询保单理赔类型保项层面的信息
   /* tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
        +" (select c.GetDutyName from LMDutyGetClm c where c.GetDutyKind = a.GetDutyKind and c.GetDutyCode = a.GetDutyCode),"
        +" b.GetStartDate,b.GetEndDate,"
        +" nvl(a.GracePeriod,0),"
        +" a.Amnt,a.YearBonus,a.EndBonus,"
        +" a.StandPay,a.GiveType, "
        +" (select e.codename from ldcode e where e.codetype = 'llpayconclusion2' and e.code=a.GiveType),"
        +" a.GiveReason,"
        +" (select f.codename from ldcode f where f.codetype = 'llprotestreason' and f.code=a.GiveReason),"
        +" a.GiveReasonDesc,a.SpecialRemark,"
        +" a.PrepaySum ,"//预付金额
        +" '',"
        +" a.RealPay,a.AdjReason,"
        +" (select g.codename from ldcode g where g.codetype = 'lldutyadjreason' and g.code=a.AdjReason),"  
        +" a.AdjRemark, "
        +" a.PrepayFlag,case a.PrepayFlag when '0' then '无预付' when '1' then '有预付' end,"
        +" case a.PolSort when 'A' then '承保前' when 'B' then '过期' when 'C' then '当期' end ,"
        +" a.dutycode,a.CustomerNo,a.GrpContNo,a.ContNo, "
        +" (select name from ldperson where customerno=a.CustomerNo), "
        +"  (select codename from ldcode where codetype='polstate' and code in(select polstate from lcpol t where t.polno=a.PolNo))"
        +" from LLClaimDetail a,LCGet b  where 1=1 "
        +" and a.PolNo = b.PolNo"       
        +" and a.DutyCode = b.DutyCode"       
        +" and a.GetDutyCode = b.GetDutyCode" 
        +" and a.ClmNo = '"+tClaimNo+"'"   
        //+" and a.GiveType not in ('1')"
        +" and a.CustomerNo = '"+tCustNo+"'"
        ;*/
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql17");
		mySql.addSubPara(tClaimNo);  
		mySql.addSubPara(tCustNo);  
//    tSql = " select a.PolNo,a.GetDutyKind,a.RiskCode,a.GetDutyCode, "
//       +" (select c.GetDutyName from LMDutyGetRela c where c.GetDutyCode = a.GetDutyCode),"
//       +" b.GetStartDate,b.GetEndDate,"
//       +" nvl(a.GracePeriod,0)," //交至日期 + 宽限期--+ a.GracePeriod
//       +" a.Amnt,a.YearBonus,a.EndBonus,"
//       +" a.StandPay,a.GiveType, "
//       +" (select e.codename from ldcode e where e.codetype = 'llpayconclusion2' and e.code=a.GiveType),"
//       +" a.GiveReason,"
//       +" (select f.codename from ldcode f where f.codetype = 'llprotestreason' and f.code=a.GiveReason),"
//       +" a.GiveReasonDesc,a.SpecialRemark,"
//       +" a.PrepaySum ,"//预付金额
//       +" '',"
//       +" a.RealPay,a.AdjReason,"
//       +" (select g.codename from ldcode g where g.codetype = 'lldutyadjreason' and g.code=a.AdjReason),"
//       +" a.AdjRemark, "
//       +" a.PrepayFlag,case a.PrepayFlag when '0' then '无预付' when '1' then '有预付' end,"
//       +" case a.PolSort when 'A' then '承保前' when 'B' then '过期' when 'C' then '当期' end ,"
//       +" a.DutyCode,a.CustomerNo,a.GrpContNo,a.ContNo,"
//       //+" (select name from ldperson where customerno=a.CustomerNo), "
//       +"  (select codename from ldcode where codetype='polstate' and code in(select polstate from lcpol t where t.polno=a.PolNo))"
//       +" from LLClaimDetail a,LCGet b  where 1=1 "
//       +" and a.PolNo = b.PolNo"
//       +" and a.DutyCode = b.DutyCode"
//       +" and a.GetDutyCode = b.GetDutyCode"
//       +" and a.ClmNo = '"+tClaimNo+"'"
//       //+" and a.GiveType not in ('1')"
//       +" and a.CustomerNo = '"+tCustNo+"'"
    arr = easyExecSql( mySql.getString() );//prompt("",tSql);
    if (arr)
    {
        displayMultiline(arr,PolDutyCodeGrid);
    }
	//prompt("",tSql);
    //查询LLFeeMain和LLCaseReceipt，显示门诊/住院信息
    
      /*tSql = "select a.customerno, b.FeeItemType,(select hospitalname from LLCommendHospital where hospitalcode=a.hospitalcode), a.HospitalCode, "
         +" a.HospitalGrade,a.MainFeeNo, (select icdname from lddisease where icdcode=b.diseasecode),b.diseasecode,"
         +" b.startdate,b.EndDate,decode(a.FeeType,'A','',b.DayCount),b.FeeItemName,"
         +" b.FeeItemCode,b.Fee,"
         +" b.SelfAmnt,(select codename from ldcode where codetype = 'deductreason' and Code = b.AdjReason),b.AdjReason,b.AdjSum,b.SecurityAmnt,b.HospLineAmnt,b.AdjRemark,b.FeeDetailNo "
         +" from llfeemain a, LLCaseReceipt b,LLCase c "
         +" where a.clmno = '"+tClaimNo+"' and a.clmno = b.clmno and a.clmno=c.caseno and b.customerno=c.customerno"
         +" and a.customerno = b.customerno and a.mainfeeno = b.mainfeeno "
         +" and a.customerno = '"+tCustNo+"'"
         +" order by lpad(c.seconduwflag,4,'0')";*/
         //+" and a.hospitalcode = c.hospitalcode  and trim(b.diseasecode) = trim(d.icdcode) ";   
//         alert(697);
//         prompt("出错！",tSql);
	 mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql18");
		mySql.addSubPara(tClaimNo);  
		mySql.addSubPara(tCustNo);  
    turnPage2.queryModal(mySql.getString(),MedFeeInHosInpGrid);
//alert(699);
    //伤残信息查询
    initMedFeeCaseInfoGrid();       //比例给付(原伤残)
    /*var strSql = " select defotype,defograde,DefoName,defocode,DefoCodeName,deformityrate,appdeformityrate,realrate,clmno,caseno,serialno,customerno,JudgeOrganName,JudgeDate,adjremark"
        + " from LLCaseInfo where "
        + " ClmNo='" + tClaimNo + "'"
        + " and CustomerNo='"+tCustNo+"'"
        + " order by serialno";*/
    //显示所有数据
     mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql19");
		mySql.addSubPara(tClaimNo);  
		mySql.addSubPara(tCustNo);  
    var arr = easyExecSql(mySql.getString());
    if (arr) {
    	displayMultiline(arr,MedFeeCaseInfoGrid);
    }
    //特种费用
    initMedFeeOtherGrid();          //特种费用 
    /*var strSql = " select factortype,factorcode,factorname,factorvalue,SelfAmnt,clmno,caseno,serialno,customerno,UnitName,StartDate,EndDate,"
		   + " (select codename from ldcode where codetype='deductreason' and code=adjreason),adjreason,adjremark"
        + " from LLOtherFactor where "
        + " ClmNo='" + tClaimNo + "'"
        + " and CustomerNo='"+tCustNo+"'"
        + " and FeeItemType = 'T'"
        + " order by serialno";*/
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql20");
		mySql.addSubPara(tClaimNo);  
		mySql.addSubPara(tCustNo);  
    //prompt("其他信息查询",strSql);
    //显示所有数据
    var arr = easyExecSql(mySql.getString());
    if (arr) {
    	displayMultiline(arr,MedFeeOtherGrid);
    }
    //社保第三方给付
    initMedFeeThreeGrid();          //社保第三方给付
   /* var strSql = " select factortype,factorcode,factorname,factorvalue,clmno,caseno,serialno,customerno,UnitName,AdjRemark"
        + " from LLOtherFactor where "
        + " ClmNo='" + tClaimNo + "'"
        + " and CustomerNo='"+tCustNo+"'"
        + " and FeeItemType = 'D'"
        + " order by serialno";*/
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql21");
		mySql.addSubPara(tClaimNo);  
		mySql.addSubPara(tCustNo);  
    //显示所有数据
    var arr = easyExecSql(mySql.getString());
    if (arr) {
    	displayMultiline(arr,MedFeeThreeGrid);
    }
//查询LLClaimPolicy,查询保全项目信息      
    /* tSql = " select a.PolNo,(select RiskCode from LCPol where PolNo=a.PolNo),(select c.codename from ldcode c where c.codetype = 'edortypecode' and c.code=a.EdorType),"
     +" a.EdorValiDate,a.GetMoney "
     +" from LPEdorItem a where 1=1 and a.PolNo in(select PolNo from LLClaimPolicy where ClmNo = '"+tClaimNo+"') ";
   */ mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql22");
		mySql.addSubPara(tClaimNo);  
    arr = easyExecSql( mySql.getString() );
  
    if (arr)
    {
        displayMultiline(arr,LPEdorItemGrid);
    }
    else
    {
        initLPEdorItemGrid();
    }

}


//保单查询
//按照”客户号“在LCpol中进行查询，显示该客户的保单明细
function showInsuredLCPol()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
  var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLcContQueryMain.jsp?AppntNo="+tCustomerNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//既往赔案查询
function showOldInsuredCase()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    var tRgtNo = fm.RptNo.value;
  var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
    var strUrl="LLLClaimQueryMain.jsp?AppntNo="+tCustomerNo+"&ClmNo="+tRgtNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//计算医疗单证信息
function showLLMedicalFeeCal()
{
    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;     //客户号

    var strUrl="LLClaimRegMedFeeCalMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}


//数据提交
function submitForm()
{
    //提交数据
    //showInfo.close();           
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

    fm.submit(); //提交
    tSaveFlag ="0";
}

//匹配后的动作
function afterMatchDutyPay(FlagStr, content)
{
    showInfo.close();
    if (FlagStr == "FAIL" )
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

        mOperate = '';
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

    }
    afterMatchDutyPayQuery();

}

//返回按钮
function goToBack()
{
    var tFlag = fm.Flag.value;
    if(tFlag == "FROMSIMALL")
    {
       location.href="LLGrpClaimSimpleAllInput.jsp";
    }
    else if(tFlag == '2' || document.all('isNew').value =='2')
    {
        location.href="LLGrpClaimConfirmInput.jsp";
    }
    else
    {
        location.href="LLGrpClaimSimpleInput.jsp";
    }
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
    
    showInfo.close();
    if (FlagStr == "Fail")
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

        goToBack();
    }
    queryRegister();
    tSaveFlag ="0";
}

//提交后操作,服务器数据返回后执行的操作
function afterSubmit2( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail")
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

        queryRegister();        
    }
    if(fm.SimpleConclusion2.value != '4' && fm.SimpleConclusion2.value != '5')
    {
//      goToBack();
    }
    if(fm.SimpleConclusion2.value == '4'){
    	fm.ConclusionSave.disabled=false;
    }
    fm.simpleClaim2.disabled = false;
    tSaveFlag ="0";
}

//提交后操作,不返回
function afterSubmit3( FlagStr, content )
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

    }
      queryRegister();
      afterMatchDutyPayQuery();
      tSaveFlag ="0";
      
    //  var tsql="select missionprop2 from lwmission where missionprop1='"+fm.RptNo.value+"' ";
      mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql23");
		mySql.addSubPara(fm.RptNo.value);  
      var tclmstate=easyExecSql(mySql.getString());
      if (tclmstate==10)         
      {
         fm.addbutton.disabled = false;
         fm.updatebutton.disabled = false;
         fm.deletebutton.disabled = false;
         //fm.addbutton2.disabled = true;
         //fm.updateFeebutton.disabled = true;
         //fm.deleteFeebutton.disabled = true;
         //fm.QueryReport.disabled = true;
         fm.dutySet.disabled = true;
         fm.QuerydutySet.disabled = true;   
         fm.simpleClaim.disabled = true; 
         fm.DiskInput.disabled = true;
       }else 
       {          
         fm.addbutton.disabled = true;
         fm.updatebutton.disabled = false;
         fm.deletebutton.disabled = false;
         //fm.addbutton2.disabled = false;
         //fm.updateFeebutton.disabled = false;
         //fm.deleteFeebutton.disabled = false;
         //fm.QueryReport.disabled = false;
         fm.dutySet.disabled = false;
         fm.QuerydutySet.disabled = false;   
         fm.simpleClaim.disabled = false;
         fm.DiskInput.disabled = false;  
       }    	
      
      
}


//出险人查询
function ClientQuery()
{
    window.open("LLLdPersonQueryInput.jsp","",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open("LLLdPersonQueryInput.jsp");

}

//得到0级icd10码
function saveIcdValue()
{
    fm.ICDCode.value = fm.AccResult1.value;
}


//团体简易案件
function confirmClick()
{
  //检查非空 
  if (fm.Flag.value != '2' && (fm.SimpleConclusion.value == "" || fm.SimpleConclusion.value == null))
  {
      alert("请填写批量案件结论！");
      return;
  }
  //检查非空
  if (fm.Flag.value == '2'&&(fm.SimpleConclusion2.value == "" || fm.SimpleConclusion2.value == null))
  {
      alert("请填写批量案件复核结论！");
      return;
  }
  //匹配并理算判断
  var i = PolDutyCodeGrid.mulLineCount;
  if(i == '0')
  {
      alert("请先匹配并理算！");
      return;
  }
       //查询是否进行过匹配计算 2009-08-04 9:08
 /* var Detailsql = "select count(1) from LLClaimDetail where"
           + " ClmNo = '" + fm.RptNo.value + "'";*/
  mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql24");
		mySql.addSubPara(fm.RptNo.value);  
  var tDetailDutyKind = easyExecSql(mySql.getString());
  if (tDetailDutyKind == 0)
  {
      alert("请先进行匹配并理算!");
      return;
  }
  
    //检查是否有扫描件 liuyu-20070827
    /*var ssql="select count(*) from es_doc_main where doccode='"+fm.RptNo.value+"' ";
    var scancount=easyExecSql(ssql);
    if (scancount==0)
    {
        alert("该赔案下没有扫描件信息，不能立案确认！");
        return;    	
    }  */
  
    //校验赔付金额与理算金额是否相等  liuyu-2008-2-29
    //var accSql="select nvl(sum(pay),0) from llbalance where clmno='"+fm.RptNo.value+"' ";
     mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql25");
		mySql.addSubPara(fm.RptNo.value);  
		 var accMoney=easyExecSql(mySql.getString());
    //var polSql="select nvl(sum(realpay),0) from llclaimpolicy where clmno='"+fm.RptNo.value+"' ";
	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql26");
		mySql.addSubPara(fm.RptNo.value); 
    var polMoney=easyExecSql(mySql.getString());

    if (parseFloat(accMoney)!=parseFloat(polMoney))
    {
        alert("该赔案赔付金额与理算金额不等，不能立案确认！请重新理算后再试！");
        return;      	
    }  
  
  //  var txsql="select distinct givetype from llclaimdetail where clmno='"+fm.RptNo.value+"' ";
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql27");
		mySql.addSubPara(fm.RptNo.value); 
    var tGiveType=easyExecSql(mySql.getString());
    
    if (tGiveType.length>1)
    {     
       alert('赔付结论不统一！请修改后再立案确认！');	
       return;
    }   

   //理赔人员权限判断
  if (fm.isNew.value == '2')
  {
    if(fm.SimpleConclusion2.value == '0' ){
    var tClaimType1 ;
    var tClaimType2 ;
    var tRealpay ;
//01.查询该赔案的最大理赔值

     //var csql="select customerno from llcase where caseno='"+fm.RptNo.value+"' ";
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql28");
		mySql.addSubPara(fm.RptNo.value); 
     var tcustomerno=new Array();
     tcustomerno=easyExecSql(mySql.getString());
     /*for (var i=0;i<tcustomerno.length;i++)
     {
          /*var strSql01 = " select realpay, insuredno from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' and realpay = (select max(realpay) from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' )"   */
         /*var strSql00 = " select sum(realpay) from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='10') and insuredno='"+tcustomerno[i]+"'";                  
                       
         var tSubReport = new Array;
         tSubReport = easyExecSql(strSql00);
         var  tRealpay1 = tSubReport[0][0];
         //alert ("寿险赔付:"+tRealpay1);
        // var tInsuredno = tSubReport[0][1];
        
             if(tRealpay1 == '' || tRealpay1 == null)
             {
               //  alert("未查询到该赔案的赔付金额！");
               //  return;
                 tRealpay1=0;  
             }
           else
           	{
           	  tClaimType1='10';	
           	}
           	
         var strSql01 = " select sum(realpay) from llclaimpolicy "
                       + " where clmno = '"+fm.RptNo.value+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='30') and insuredno='"+tcustomerno[i]+"'";                  
        
         var tSubReport1 = new Array;
         tSubReport1 = easyExecSql(strSql01);
         var   tRealpay2 = tSubReport1[0][0];
        //alert ("健康险赔付:"+tRealpay2);
        // var tInsuredno2 = tSubReport[0][1];
           if(tRealpay2==null || tRealpay2 == "")
             {
        //         alert("未查询到该赔案的赔付金额！");
        //         return;
                tRealpay2=0;           
             }
           else
           	{
           	   	tClaimType2='30';
           	}      	
           	
        /*//*02.查询该最大理赔值的客户的出险类型
             var strSql02 = "select reasoncode from LLAppClaimReason where caseno = '"+fm.RptNo.value+"' and customerno = '"+tInsuredno+"'";
             var tSubReport2 = new Array;
             tSubReport2 = easyExecSql(strSql02);
               if(tSubReport2 == null ){
                    alert("未查询到该赔案的出险类型！");
                    return;
                   }
             for (var i= 0;i < tSubReport2.length ; i++ )
             {
                 var tReasonCode = tSubReport2[i][0].substring(1,3);
                 if(tReasonCode == 01 || tReasonCode == 02 || tReasonCode == 04){
                      tClaimType = 10;//寿险
                      break;
                     }else{
                      tClaimType = 30;//健康险
                     }
             }*/
             
        //03.1查询理赔人员的相关类型的理赔权限
        //0301.1查询复核人员的理赔权限
               /*var strSql0301 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
                var tBasemax0301 = easyExecSql(strSql0301);
                if (tBasemax0301 == null || tBasemax0301 == "")
                {
        //            alert("未查询到您的理赔权限！");
        //            return;
                      tBasemax0301 = 0; //为查询到的默认为0
                }
        //0302.1查询立案人员的理赔权限
                var strSql0302 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
                var tBasemax0302 = easyExecSql(strSql0302);
                if (tBasemax0302 == null || tBasemax0302 == "")
                {
        //            alert("未查询到您的理赔权限！");
        //            return;
                      tBasemax0302 = 0; //为查询到的默认为0
                }
        //0303.1立案人员和审核人员权限判断
                tBasemax0301 = tBasemax0301*1;
                tBasemax0302 = tBasemax0302*1;
                if(tBasemax0301 > tBasemax0302){
                  tBasemax1 = tBasemax0301;
                }else{
                  tBasemax1 = tBasemax0302;
                }
        
        //03.2查询理赔人员的相关类型的理赔权限 
        //0301.2查询复核人员的理赔权限        
                var strSql03011 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
                var tBasemax03011 = easyExecSql(strSql03011);
                if (tBasemax03011 == null || tBasemax03011 == "")
                {
        //            alert("未查询到您的理赔权限！");
        //            return;
                      tBasemax03011 = 0; //为查询到的默认为0
                }
        //0302.2查询立案人员的理赔权限
                var strSql03022 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
                var tBasemax03022 = easyExecSql(strSql03022);
                if (tBasemax03022== null || tBasemax03022 == "")
                {
        //            alert("未查询到您的理赔权限！");
        //            return;
                      tBasemax03022 = 0; //为查询到的默认为0
                }
        //0303.2立案人员和审核人员权限判断
                tBasemax03011 = tBasemax03011*1;
                tBasemax03022 = tBasemax03022*1;
                if(tBasemax03011 > tBasemax03022){
                  tBasemax2 = tBasemax03011;
                }else{
                  tBasemax2 = tBasemax03022;
                }
        
        //04.权限判断
                tBasemax1 = tBasemax1*1;
                tBasemax2 = tBasemax2*1;
        
              		tRealpay1 = tRealpay1*1;
              		tRealpay2 = tRealpay2*1;
              		
              	//alert ("寿险最高赔付:"+tBasemax1);
                          
                //alert ("健康险最高赔付:"+tBasemax2);
              		
                if(tRealpay1 > tBasemax1 ||tRealpay2 > tBasemax2)
                {
                    alert("您的权限不足！请上报总公司处理！");
                    return;
                }     	
             }*/
             
             
      }else if (fm.SimpleConclusion2.value == '1')
      {
    	  var mngcom = new Array;
    	 // var tsql="select mngcom from llregister where rgtno='"+fm.RptNo.value+"' ";    	  
    	  mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql29");
		mySql.addSubPara(fm.RptNo.value); 
    	  mngcom = easyExecSql(mySql.getString());   
    	  
    	  if (fm.AllManageCom.value!=mngcom)
    	  {
    	     alert ("请登录立案机构 "+mngcom+" 进行回退操作！");	
    	     return;
    	  }
      }
   }   
      
 

  fm.action="./LLGrpClaimSimpleSave.jsp"
  fm.fmtransact.value = "";
  submitForm();
}

//团体简易案件
function confirmClick2()
{

if(!(confirm("确认保存理赔结论!")==true)){
   return false;
}
  //检查非空
  if (fm.isNew.value == '2'&&(fm.SimpleConclusion2.value == "" || fm.SimpleConclusion2.value == null))
  {
      alert("请填写简易案件复核结论！");
      return;
  }
  //匹配并理算判断
  var i = PolDutyCodeGrid.mulLineCount;
  if(i == '0')
  {
      alert("请先匹配并理算！");
      return;
  }

   //理赔人员权限判断
  if (fm.isNew.value == '2')
  {
    	//var tclmSql="select clmstate from llclaim where clmno='"+fm.RptNo.value+"' ";
    	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql30");
		mySql.addSubPara(fm.RptNo.value); 
    	var ttclmstate=easyExecSql(mySql.getString());
    	if (ttclmstate=="60"||ttclmstate=="70"||ttclmstate=="80")
    	{
    		alert("该赔案已经结案！");
    	  return;
    	}    
    
    fm.simpleClaim2.disabled = true;
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

        
    if(fm.SimpleConclusion2.value == '0')
    {
    var tClaimType1 ;
    var tClaimType2 ;
    var tRealpay ;
    var tGiveType ;
    var tappndmax ;
    var tappndmax1 ;
    var tappndmax2 ;    

    //var txsql="select distinct givetype from llclaimdetail where clmno='"+fm.RptNo.value+"' ";
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql31");
		mySql.addSubPara(fm.RptNo.value); 
    tGiveType=easyExecSql(mySql.getString());
    
    if (tGiveType.length>1)
    {
       showInfo.close();
       fm.simpleClaim2.disabled = false;
       alert('赔付结论不统一！请回退到立案审核来修改！');	
       return;
    }
    
//  if(tGiveType == '0' && tGiveType.length==1 )     
//  {
//     //01.查询该赔案的最大理赔值     
//     var csql="select customerno from llcase where caseno='"+fm.RptNo.value+"' ";
//     var tcustomerno=new Array();
//     tcustomerno=easyExecSql(csql);
//     for (var i=0;i<tcustomerno.length;i++)
//     {         
//         var strSql00 = " select sum(realpay) from llclaimpolicy "
//                      + " where clmno = '"+fm.RptNo.value+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='10') and insuredno='"+tcustomerno[i]+"'";                  
//         
//         var tSubReport = new Array;
//         tSubReport = easyExecSql(strSql00);    
//         var   tRealpay1 = tSubReport[0][0];
//         //alert ("寿险赔付:"+tRealpay1);
//         //var tInsuredno1 = tSubReport[0][1];
//         
//          if(tRealpay1==null || tRealpay1 == "")
//             {
//               //  alert("未查询到该赔案的赔付金额！");
//              //   return false;
//              tRealpay1=0;       
//             }
//          else
//           	{
//           		//alert("tClaimType1");
//           	   tClaimType1='10';
//           	}
//         
//         var strSql01 = " select sum(realpay) from llclaimpolicy "
//                       + " where clmno = '"+fm.RptNo.value+"' and riskcode in (select riskcode  from LMRiskApp where RiskType7='30') and insuredno='"+tcustomerno[i]+"'";                  
//         
//         var tSubReport1 = new Array;
//         tSubReport1 = easyExecSql(strSql01);
//         var   tRealpay2 = tSubReport1[0][0];
//             //alert ("健康险赔付:"+tRealpay2);
//            // var tInsuredno2 = tSubReport[0][1];
//               if(tRealpay2==null || tRealpay2 == "")
//                 {
//            //         alert("未查询到该赔案的赔付金额！");
//            //         return;
//                    tRealpay2=0;           
//                 }
//               else
//               	{
//               	   	tClaimType2='30';
//               	}
//               
//         //02.查询该最大理赔值的客户的出险类型
//              /*var strSql02 = "select distinct reasoncode from LLAppClaimReason where caseno = '"+fm.ClmNo.value+"' and customerno = '"+fm.customerNo.value+"'";
//              var tSubReport2 = new Array;
//              tSubReport2 = easyExecSql(strSql02);
//                if(tSubReport2 == null ){
//                     alert("未查询到该赔案的出险类型！");
//                     return;
//                    }
//              for (var i= 0;i < tSubReport2.length ; i++ )
//              {
//                  var tReasonCode = tSubReport2[i][0].substring(1,3);
//                  if(tReasonCode == 01 || tReasonCode == 02 || tReasonCode == 04){
//                       tClaimType = 10;//寿险
//                       break;
//                      }else{
//                       tClaimType = 30;//健康险
//                      }
//              }*/
//         //03.1查询理赔人员的相关类型的理赔权限
//         //0301.1查询复核人员的理赔权限
//                 var strSql0301 = "select b.basemax from llgrpclaimuser a,LLgrpClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
//                 var tBasemax0301 = easyExecSql(strSql0301);
//                 if (tBasemax0301 == null || tBasemax0301 == "")
//                 {
//         //            alert("未查询到您的理赔权限！");
//         //            return;
//                       tBasemax0301 = 0; //为查询到的默认为0
//                 }
//         //0302.1查询立案人员的理赔权限
//                 var strSql0302 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType1+"' and a.StateFlag = '1'"
//                 var tBasemax0302 = easyExecSql(strSql0302);
//                 if (tBasemax0302 == null || tBasemax0302 == "")
//                 {
//         //            alert("未查询到您的理赔权限！");
//         //            return;
//                       tBasemax0302 = 0; //为查询到的默认为0
//                 }
//         //0303.1立案人员和审核人员权限判断
//                 tBasemax0301 = tBasemax0301*1;
//                 tBasemax0302 = tBasemax0302*1;
//                 if(tBasemax0301 > tBasemax0302){
//                   tBasemax1 = tBasemax0301;
//                 }else{
//                   tBasemax1 = tBasemax0302;
//                 }
//         
//         //03.2查询理赔人员的相关类型的理赔权限 
//         //0301.2查询复核人员的理赔权限        
//                 var strSql03011 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('tOperator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
//                 var tBasemax03011 = easyExecSql(strSql03011);
//                 if (tBasemax03011 == null || tBasemax03011 == "")
//                 {
//         //            alert("未查询到您的理赔权限！");
//         //            return;
//                       tBasemax03011 = 0; //为查询到的默认为0
//                 }
//         //0302.2查询立案人员的理赔权限
//                 var strSql03022 = "select b.basemax from llclaimuser a,LLClaimPopedom b where a.checklevel = b.popedomlevel and UserCode = '"+ document.all('Operator').value +"' and b.claimtype = '"+tClaimType2+"' and a.StateFlag = '1'"
//                 var tBasemax03022 = easyExecSql(strSql03022);
//                 if (tBasemax03022== null || tBasemax03022 == "")
//                 {
//         //            alert("未查询到您的理赔权限！");
//         //            return;
//                       tBasemax03022 = 0; //为查询到的默认为0
//                 }
//         //0303.2立案人员和审核人员权限判断
//                 tBasemax03011 = tBasemax03011*1;
//                 tBasemax03022 = tBasemax03022*1;
//                 if(tBasemax03011 > tBasemax03022){
//                   tBasemax2 = tBasemax03011;
//                 }else{
//                   tBasemax2 = tBasemax03022;
//                 }
//                 
//         //04.权限判断
//                 tBasemax1 = tBasemax1*1;
//                 tBasemax2 = tBasemax2*1;
//         
//               		tRealpay1 = tRealpay1*1;
//               		tRealpay2 = tRealpay2*1;      	                    
//                        
//                 //alert ("寿险最高赔付:"+tBasemax1);
//                           
//                 //alert ("健康险最高赔付:"+tBasemax2);
//              if(tRealpay1 > tBasemax1 || tRealpay2 > tBasemax2)
//               {
//         //05.超权限上报和审批管理判断
//         //0501.查询该机构最高理赔权限,同时跟用户表相关联userstate 0 有效 1失效
//                 var strSql0501 = "select max(checklevel) from llclaimuser a , Lduser b  where a.comcode like '"+fm.ManageCom.value+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0'";
//                 var tChecklevel  = easyExecSql(strSql0501);
//         //0502.查询该机构最高权限的赔付金额
//                 var strSql0502 = "select basemax from LLClaimPopedom where claimtype = '"+tClaimType1+"' and Popedomlevel = '"+tChecklevel+"'";
//                 var strSql05022 = "select basemax from LLClaimPopedom where claimtype = '"+tClaimType2+"' and Popedomlevel = '"+tChecklevel+"'";
//                 var tBasemax3  = easyExecSql(strSql0502);
//                 var tBasemax4  = easyExecSql(strSql05022);
//                 tBasemax3 = tBasemax3*1;
//                 tBasemax4 = tBasemax4*1;
//                 
//                 if(tRealpay1 > tBasemax3 || tRealpay2 > tBasemax4)
//                   {
//         //控件权限设定
//                    fm.ConclusionSave.disabled = false;
////                    fm.ConclusionUp.disabled = true;
////                    fm.ConclusionBack.disabled = true;
//                    showInfo.close();
//                    fm.simpleClaim2.disabled=false;                     
//                     alert("您的权限不足！请将该案件上报到审批管理！");
//                     return;
//                   }                  
//                   else{
//         //控件权限设定 
//                    //fm.ConclusionSave.disabled = true;
////                    fm.ConclusionUp.disabled = false;
////                    fm.ConclusionBack.disabled = true;
//                    showInfo.close();
//                    fm.simpleClaim2.disabled=false;                         
//                     alert("您的权限不足！请将该案件超权限上报！");
//                     return;
//                   }
//                   
//               }
//     }  //for customerno
//   }else if ((tGiveType == '2'||tGiveType == '3') && tGiveType.length== 1 )
//    {            
//      var tsql1="select distinct b.appendmax from llclaimuser a ,llclaimpopedom b where a.checklevel=b.popedomlevel and a.usercode='"+document.all('tOperator').value+"' "; //立案人员
//      tappndmax1=easyExecSql(tsql1);
//      var tsql2="select distinct b.appendmax from llclaimuser a ,llclaimpopedom b where a.checklevel=b.popedomlevel and a.usercode='"+document.all('Operator').value+"' ";  //复核人员
//      tappndmax2=easyExecSql(tsql2);     
//      if (parseFloat(tappndmax2)>parseFloat(tappndmax1))
//        {      	   
//      	    tappndmax=tappndmax2;      	    
//      	}else
//      		{
//      			tappndmax=tappndmax1;  
//      		}          	
//       var csql2="select customerno from llcase where caseno='"+fm.RptNo.value+"' ";
//       var xcustomerno=new Array();
//       xcustomerno=easyExecSql(csql2);
//       for (var k=0;k<xcustomerno.length;k++)
//       {         		
//         var maxsql="select sum(realpay) from llclaimpolicy where clmno = '"+fm.RptNo.value+"' and insuredno='"+xcustomerno[k]+"' ";
//         tRealpay=easyExecSql(maxsql);      
//         if (parseFloat(tappndmax)<parseFloat(tRealpay)) 
//         { 
//            var strSql0501 = "select max(checklevel) from llclaimuser a , Lduser b  where a.comcode like '"+fm.ManageCom.value+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0'";
//            var tChecklevel  = easyExecSql(strSql0501);
//            
//            var strSql0502 = "select distinct appendmax from LLClaimPopedom where Popedomlevel = '"+tChecklevel+"'";                 
//            var tBasemax3  = easyExecSql(strSql0502);                 
//            tBasemax3 = tBasemax3*1;                 
//            
//            if(tRealpay > tBasemax3)
//              {
//               fm.ConclusionSave.disabled = false;
////               fm.ConclusionUp.disabled = true;
////               fm.ConclusionBack.disabled = true;
//               showInfo.close();
//               fm.simpleClaim2.disabled=false;                     
//                alert("您的通融、协议赔付权限不够！请将该案件上报到审批管理！");
//                return;
//              }                  
//              else{                        
//               //fm.ConclusionSave.disabled = true;
////               fm.ConclusionUp.disabled = false;
////               fm.ConclusionBack.disabled = true;
//               showInfo.close();
//               fm.simpleClaim2.disabled=false;                       
//                alert("您的通融、协议赔付权限不足！请将该案件超权限上报！");
//                return;
//              }         	        	 
//          } 
//       }    	
//    }else if(tGiveType == '1' && tGiveType.length==1 )         
//    {
//      var tsql="select checklevel from llclaimuser where usercode='"+document.all('Operator').value+"' ";	//复核人员
//      var tchecklevel=new Array;
//      tchecklevel=easyExecSql(tsql);
//      if (tchecklevel=="A"||tchecklevel=="B1"||tchecklevel=="B2"||tchecklevel=="B3")
//      {
//          var strSql0501 = "select max(checklevel) from llclaimuser a , Lduser b  where a.comcode like '"+fm.ManageCom.value+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0'";
//          var tChecklevel  = easyExecSql(strSql0501);              
//          
//          if (tChecklevel=="A"||tChecklevel=="B1"||tChecklevel=="B2"||tChecklevel=="B3")
//            {
//             fm.ConclusionSave.disabled = false;
////             fm.ConclusionUp.disabled = true;
////             fm.ConclusionBack.disabled = true;
//             showInfo.close();
//             fm.simpleClaim2.disabled=false;                     
//              alert("您的拒付权限不够！请将该案件上报到审批管理！");
//              return;
//            }                  
//            else{         
//             //fm.ConclusionSave.disabled = true;
////             fm.ConclusionUp.disabled = false;
////             fm.ConclusionBack.disabled = true;
//             showInfo.close();
//             fm.simpleClaim2.disabled=false;                         
//              alert("您的拒付权限不足！请将该案件超权限上报！");
//              return;
//            }          
//
//       }                
//     }     
   }   //fm.SimpleConclusion2.value == '0'
   
 }   //fm.isNew.value == '2'
 
  fm.action="./LLGrpSimpleSave.jsp"
  fm.fmtransact.value = "INSERT";
  showInfo.close();
  submitForm();
}

function AuditConfirmClick()
{
   //-----------------------------liuyu-20070627----------------------------------------//
   var mngcom = new Array;
   //var tsql="select mngcom,operator from llregister where rgtno='"+fm.RptNo.value+"' ";    	  
   mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql32");
		mySql.addSubPara(fm.RptNo.value); 
   mngcom = easyExecSql(mySql.getString());
   var tmanagecom=mngcom[0][0];   	      	  
   var toperator=mngcom[0][1];    	        	      	  
   
    if(tmanagecom==null||tmanagecom=="")
    {
    		alert("查找立案机构失败!");
    		return;
    }
    if(toperator==null||toperator=="")
    {
    		alert("查找立案操作员失败!");
    		return;
    }     
    //-----------------------------liuyu-20070627----------------------------------------//
    fm.action="./LLGrpSimpleAuditSave.jsp?mngNo="+tmanagecom+"&operator="+toperator;
    submitForm();  	
}

function ConclusionSaveClick()
{
		var tManageCom = fm.ManageCom.value.substring(0,2);
		//var strSql0501 = " select checklevel,username,usercode from llclaimuser where comcode like '"+tManageCom+"%%' and StateFlag = '1' and checklevel = (select max(checklevel) from llclaimuser a , Lduser b where a.comcode like '"+tManageCom+"%%' and a.StateFlag = '1' and a.usercode = b.usercode and b.userstate = '0') order by comcode" ;
		 mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql33");
		mySql.addSubPara(tManageCom); 
		mySql.addSubPara(tManageCom); 
		var tSubReport2 = new Array;
		    tSubReport2 = easyExecSql(mySql.getString());
		/*if(tSubReport2 != null)
		{
		    var tChecklevel = tSubReport2[0][0];
		    var tUserName   = tSubReport2[0][1];
		    var tUserCode   = tSubReport2[0][2];
		}else{
		    alert("未查询到该分机构的最高级别理赔人员!");
		    return false;
		} 
		if(tUserCode != fm.Operator.value)
		{
        fm.ConclusionSave.disabled = true;
        fm.ConclusionUp.disabled = false;
        fm.ConclusionBack.disabled = true;                    
        fm.simpleClaim2.disabled=false;  		    
		    alert("请本分公司最高理赔级别, "+tUserName+" 登录把案件上报！请先进行超权限上报！");
		    return false;
		}*/
	//09-04-21增加对受益人是否完全分配的校验
		  //如果只有豁免不用判断受益人
	       /* var tSQL = "select substr(getdutykind,2,2) from llclaimdutykind where 1=1 "
	                 + getWherePart( 'ClmNo','ClmNo' );*/
	        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql34");
		mySql.addSubPara(fm.ClmNo.value); 
	        var tDutyKind = easyExecSql(mySql.getString());
	        if (tDutyKind != '09')
	        {
	            //如果下给付结论,校验受益人分配
	            if (fm.SimpleConclusion2.value == "1"|| fm.SimpleConclusion2.value == "4")
	            {
	               /* var strSql4 = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.bnfkind = 'A') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
	                            + getWherePart( 'ClmNo','RptNo' );//prompt("",strSql4);*/
	                mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql35");
		mySql.addSubPara(fm.RptNo.value); 
	                var tBNF = easyExecSql(mySql.getString());                
	                if (tBNF)
	                {
	                    for (var i = 0; i < tBNF.length; i++)
	                    {
	                        if (tBNF[i] == '0')
	                        {
	                            alert("受益人分配没有完成!");
	                            return;
	                        }
	                    }
	                }
	            }
	        }
    fm.action="./LLGrpSimpleAuditSave.jsp"
    submitForm();  	
}

//超权限上报提交方法
function ConclusionUpClick()
{
    fm.SimpleConclusion2.value = 6 ;//超权限上报
    confirmClick2();//直接保存审核结论
}


//检查保项金额调整,只能调小
function checkAdjMoney()
{
    var i = PolDutyCodeGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        var tContNo   = parseFloat(PolDutyCodeGrid.getRowColData(i,31));//个单合同号
        var tRiskCode = parseFloat(PolDutyCodeGrid.getRowColData(i,3));//险种编码
        var tDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,28));//责任编码
        var tGetDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,4));//责任编码
        var tRealM    = parseFloat(PolDutyCodeGrid.getRowColData(i,21));//调整金额
        var tAmnt     = parseFloat(PolDutyCodeGrid.getRowColData(i,9));//保额
        var tGrpContNo= parseFloat(PolDutyCodeGrid.getRowColData(i,30));//团体合同号
        var tGetDutyKind = parseFloat(PolDutyCodeGrid.getRowColData(i,2));
        var tGetDutyCode = parseFloat(PolDutyCodeGrid.getRowColData(i,4));
        var tAdjM     = parseFloat(fm.RealPay.value);
        var tContPlanCode = "0";
        var tDeadTopvalFlag = "";

       // var strSQL = "select 1 from lmrisksort where riskcode='"+tRiskCode+"' and risksorttype='26' and risksortvalue='"+tGetDutyCode+"'";
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql36");
		mySql.addSubPara(tRiskCode); 
		mySql.addSubPara(tGetDutyCode); 
        //prompt("校验调整保项是否是津贴型:",strSQL);
        var arr= easyExecSql(mySql.getString());
        
        //放开对津贴型险种的限制
        if(arr == null){
        	
            if (tAmnt < tAdjM)
            {
                alert("调整金额不能大于保额!");
                fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,12);
                return false;
            } else if(tAdjM < 0){
                alert("调整金额不能小于零!");
                fm.RealPay.value = PolDutyCodeGrid.getRowColData(i,12);
                return false;
            }
        }

    }
    
    if (fm.GiveType.value=="")
    {
    	alert("请输入赔付结论!");
    	return false;
    }
    else if (fm.GiveType.value=="0" || fm.GiveType.value=="2" || fm.GiveType.value=="3")
    {
       if (fm.RealPay.value=="")
       {
       	alert("请输入调整金额!");
       	return false;
       }    
       if (fm.AdjReason.value=="")
       {
       	alert("请输入调整原因!");
       	return false;
       }      	
    }
    else if (fm.GiveType.value=="1")
     {
        if (fm.GiveTypeDesc.value=="")
        {
        	alert("请输入拒付原因!");
        	return false;
        }         	
     }
    else {
     	alert("请输入正确的赔付结论代码!");
        return false;     		
     }      
    
    if(fm.AdjReason.value == "00"||fm.AdjReason.value == "14"){
    	if(fm.AdjRemark.value == null || fm.AdjRemark.value =="null" || fm.AdjRemark.value == ""){
    		alert("调整原因为通融给付或协议给付时，必须录入调整备注!");
    		return false;
    	}
    }
    
    return true;
}


//出险人查询
function showInsPerQuery()
{
    var strUrl = "LLGrpLdPersonQueryMain.jsp?GrpContNo="+fm.GrpContNo.value+"&GrpCustomerNo="+fm.GrpCustomerNo.value+"&GrpName="+fm.GrpName.value+"&ManageCom="+fm.AllManageCom.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

//[保存]按钮对应操作
function saveClick()
{
    //首先进行非空字段检验
    if (beforeSubmit())
    {
        if (fm.RgtClass.value == '' || fm.RgtClass.value == null)
        {
            fm.RgtClass.value = "2";//团险
        }
        
        
        /*update by wood，已在报案界面做了限制，这里不用再限制了。
        增加对出险人的立案情况进行判断，如果有未结案的案件存在，不允许新增
         *2006-03-06 P。D
         
        var StrSQL = "select count(*) from llcase a, llregister b where a.caseno = b.rgtno and a.customerno = '"+fm.customerNo.value+"' and  b.clmstate not in ('60','80','50','70')";
        var arr= easyExecSql(StrSQL);
        if(arr > 0){
           alert("该出险人有未结案件，请结案后在做新增！");
           return false;
        }*/
        /*================================================================
         * 修改原因：增加对做完生存给付（趸领）的被保险人的判断不允许理赔
         * 修 改 人：P.D
         * 修改日期：2006-8-16
         *=================================================================
         */
        /*var SqlPol = " select count(*) From lcpol where polstate = '6'"
                   + " and  insuredno = '"+fm.customerNo.value+"'"
                   + " and  grpcontno = '"+fm.GrpContNo.value+"'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql37");
		mySql.addSubPara(fm.customerNo.value); 
		mySql.addSubPara(fm.GrpContNo.value); 
        if(fm.Polno.value != ''){
           // SqlPol += " and riskcode = '"+fm.Polno.value+"'";
             mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql38");
		mySql.addSubPara(fm.customerNo.value); 
		mySql.addSubPara(fm.GrpContNo.value); 
		mySql.addSubPara(fm.Polno.value); 
             }
        var tPolState = easyExecSql(mySql.getString());
        if(tPolState > 0){
           alert("该出险人已经做了生存给付，不允许做新增！");
           return false;
         }
        /*================================================================
         * 修改原因：增加对长险保全的判断，保全未确认和退保的不允许理赔
         * 修 改 人：P.D
         * 修改日期：2006-8-14
         =================================================================
         */
       // var SqlBq = "select  count(*) from LPEdorItem where insuredno = '"+fm.customerNo.value+"' and edorstate != '0' and edortype = 'CT'";
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql39");
		mySql.addSubPara(fm.customerNo.value); 
        var tEdorState = easyExecSql(mySql.getString());
        /*var sqlC = "select count(*) from lmriskapp where "
                 + "riskcode in (select riskcode From lcpol where "
                 + "grpcontno = '"+fm.GrpContNo.value+"' and insuredno = '"+fm.customerNo.value+"') "
                 + "and RiskPeriod = 'L'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql40");
		mySql.addSubPara(fm.GrpContNo.value); 
		mySql.addSubPara(fm.customerNo.value); 
        var tcount = easyExecSql(mySql.getString());
        if(tEdorState > 0 && tcount > 0 ){
           alert("该出险人有未确认的保全或已经退保，请确认后在做新增！");
           return false;
        }

/*
        //jixf add 20051213
        var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
        strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";
         if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
         {
         strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
         }
        if (fm.GrpContNo.value!=null)
        {
        strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
        }
        
         var arr= easyExecSql(strSQL);
         if ( arr == null )
         {
         alert("该保单未生效或这次出险日期不在该被保险人的保险期间内！");         
         return false;
         }
*/
         /*if(TempCustomer() == false && showDate() == false){
         alert("该保单未生效或这次出险日期不在该被保险人的保险期间内！");         
         return false;
          }*/
         
        /* var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'";
            strSQLBQ=strSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'";
            strSQLBQ=strSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')　and EdorState='0'";*/
             mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql41");
		mySql.addSubPara(fm.AccidentDate.value); 
		mySql.addSubPara(fm.customerNo.value); 
             if (fm.GrpCustomerNo.value!=null)
             {
               //strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
               mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql42");
		mySql.addSubPara(fm.AccidentDate.value); 
		mySql.addSubPara(fm.customerNo.value); 
		mySql.addSubPara(fm.GrpCustomerNo.value); 
             }
            if (fm.GrpContNo.value!=null)
            {
             // strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
              mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql43");
		mySql.addSubPara(fm.AccidentDate.value); 
		mySql.addSubPara(fm.customerNo.value); 
		mySql.addSubPara(fm.GrpCustomerNo.value); 
		mySql.addSubPara(fm.GrpContNo.value); 
            }
            
             var arrbq= easyExecSql(mySql.getString());
             if ( arrbq != null )
             {
               alert("严重警告：该被保险人在出险日期之后做过可能更改保额的保全！");                      
             }

    /*var strSQL = "select count(*) from lcinsureaccclass where accascription = '0'"
                + " and grpcontno = '" + fm.GrpContNo.value+"'";
    var tCount = easyExecSql(strSQL);//归属  */


        //判断区分新增、保存立案、还是保存出险人
        //if(fm.RptNo.value == ''){
         //fm.isReportExist.value = false;
        //}
        //if (fm.isReportExist.value == "false")
        //{
            //fm.fmtransact.value = "insert||first";
        //}
        //else
        //{
            fm.fmtransact.value = "insert||customer";
        //}
        fm.action = './LLGrpRegisterSave.jsp';
        submitForm();
              
   }
}

//出险人信息修改
function updateClick()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    
    if (confirm("您确实想修改该记录吗？"))
    {
        if (beforeSubmit())
        {
            alert("修改信息后请重新匹配理算！");
            //jixf add 20051213
          /*  var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
            strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";
           */ mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql44");
		mySql.addSubPara(fm.AccidentDate.value); 
		mySql.addSubPara(fm.AccidentDate.value); 
		mySql.addSubPara(fm.customerNo.value);  
             if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
             {
             //strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
             mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql45");
		mySql.addSubPara(fm.AccidentDate.value); 
		mySql.addSubPara(fm.AccidentDate.value); 
		mySql.addSubPara(fm.customerNo.value);
		mySql.addSubPara(fm.GrpCustomerNo.value);    
             }
            if (fm.GrpContNo.value!=null)
            {
           // strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
            mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql46");
		mySql.addSubPara(fm.AccidentDate.value); 
		mySql.addSubPara(fm.AccidentDate.value); 
		mySql.addSubPara(fm.customerNo.value);
		mySql.addSubPara(fm.GrpCustomerNo.value);  
		mySql.addSubPara(fm.GrpContNo.value); 
            }
            
             var arr= easyExecSql(mySql.getString());//prompt("",strSQL);
             if ( arr == null )
             {
             alert("该保单未生效或这次出险日期不在该被保险人的保险期间内！");         
             return false;
             }
             
             /*var strSQLBQ = " select a.polno from LPEdorItem a, LCGrpCont b where  a.EdorValidate>='"+fm.AccidentDate.value+"'";
            strSQLBQ=strSQLBQ+" and a.GrpContNo=b.GrpContNo and a.insuredno='"+fm.customerNo.value+"'";
            strSQLBQ=strSQLBQ+" and a.EdorType in ('AA','PT','IC','LC')　and EdorState='0'";*/
            mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql47");
		mySql.addSubPara(fm.AccidentDate.value);  
		mySql.addSubPara(fm.customerNo.value);
             if (fm.GrpCustomerNo.value!=null)
             {
               //strSQLBQ=strSQLBQ+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
               mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql48");
		mySql.addSubPara(fm.AccidentDate.value);  
		mySql.addSubPara(fm.customerNo.value);
		mySql.addSubPara(fm.GrpCustomerNo.value);
             }
            if (fm.GrpContNo.value!=null)
            {
              //strSQLBQ=strSQLBQ+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
              mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql49");
		mySql.addSubPara(fm.AccidentDate.value);  
		mySql.addSubPara(fm.customerNo.value);
		mySql.addSubPara(fm.GrpCustomerNo.value);
		mySql.addSubPara(fm.GrpContNo.value);
            }
            
             var arrbq= easyExecSql(mySql.getString());
             if ( arrbq != null )
             {
               alert("严重警告：该被保险人在出险日期之后做过可能更改保额的保全！");                      
             }
                       
            fm.fmtransact.value = "update";
            fm.action = './LLGrpRegisterSave.jsp';
            submitForm();
        }
    }
}

//团体信息查询
function ClientQuery2()
{
   var keycode = event.keyCode;
   if(keycode=="13" || keycode=="9")
   {
   if(document.all('GrpCustomerNo').value!=null && document.all('GrpCustomerNo').value!='' ||
      (document.all('GrpContNo').value!=null && document.all('GrpContNo').value!='') ||
      (document.all('GrpName').value!=null && document.all('GrpName').value!='') )
   {
      var arrResult = new Array();
     /* var strSQL="select  a.customerno, a.name, g.grpcontno,g.Peoples2, g.bankcode, g.bankaccno, accname, a.AddressNo  " +
      " from lcgrpcont g, LCGrpAppnt a " +
      " where a.grpcontno = g.grpcontno and g.appflag in ('1','4') "
      + getWherePart("g.AppntNo", "GrpCustomerNo" )
      + getWherePart( "g.GrpContNo","GrpContNo" );*/
       mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql50");
		mySql.addSubPara(fm.GrpCustomerNo.value);  
		mySql.addSubPara(fm.GrpContNo.value);
		mySql.addSubPara(document.all('AllManageCom').value);
      if(document.all('GrpName').value!=''){
         //strSQL += " and a.Name like '%%"+document.all('GrpName').value+"%%'";
         mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql51");
		mySql.addSubPara(fm.GrpCustomerNo.value);  
		mySql.addSubPara(fm.GrpContNo.value);
		mySql.addSubPara(document.all('AllManageCom').value);
		mySql.addSubPara(document.all('GrpName').value);
      }
      //增加对机构的判断 登陆机构只能处理本机构的单子 总机构可以处理分机构的单子 2006-02-18 P.D
      //strSQL += " and g.managecom like '"+document.all('AllManageCom').value+"%%'";
      arrResult=easyExecSql(mySql.getString());
       if(arrResult != null)
       {
          if(arrResult.length==1)
          {
             try{document.all('GrpCustomerNo').value=arrResult[0][0]}catch(ex){alert(ex.message+"GrpCustomerNo")}
             try{document.all('GrpName').value=arrResult[0][1]}catch(ex){alert(ex.message+"GrpName")}
             try{document.all('GrpContNo').value=arrResult[0][2]}catch(ex){alert(ex.message+"GrpContNo")}
             try{document.all('Peoples').value=arrResult[0][3]}catch(ex){alert(ex.message+"Peoples")}
             if(document.all('Peoples').value == null ||
                document.all('Peoples').value == "0")
             {
                alert("投保人数为空！");
                return;
             }
          }
      else
      {
            var varSrc = "&CustomerNo=" + fm.GrpCustomerNo.value;
               varSrc += "&GrpContNo=" + fm.GrpContNo.value;
               varSrc += "&GrpName=" + fm.GrpName.value;
               showInfo = window.open("./FrameMainLCGrpQuery.jsp?Interface= LCGrpQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
      }
       }else{
      //取到处理机构代码 2006-02-18 P.D
      //var SQLEx = "select ExecuteCom From lcgeneral where grpcontno='"+document.all('GrpContNo').value+"'";
      mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql52");
		mySql.addSubPara(document.all('GrpContNo').value);
      var tExecuteCom = new Array();
      tExecuteCom = easyExecSql(mySql.getString());
      for(var i = 0;i<tExecuteCom.length;i++){
      //判断登陆机构是否是保单指定机构
      if(tExecuteCom[i][0] == document.all('AllManageCom').value){
      var arrResult = new Array();
     /* var strSQL="select  a.customerno, a.name, g.grpcontno,g.Peoples2, g.bankcode, g.bankaccno, accname, a.AddressNo  " +
      " from lcgrpcont g, LCGrpAppnt a ,lcgeneral b " +
      " where a.grpcontno = g.grpcontno and g.grpcontno = b.grpcontno and g.appflag = '1' "
      + getWherePart("g.AppntNo", "GrpCustomerNo" )
      + getWherePart( "g.GrpContNo","GrpContNo" );*/
       mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql53");
		mySql.addSubPara(fm.GrpCustomerNo.value);
		mySql.addSubPara(fm.GrpContNo.value);
		mySql.addSubPara(tExecuteCom[i][0]);
      if(document.all('GrpName').value!=''){
        // strSQL += " and a.Name like '%%"+document.all('GrpName').value+"%%'";
         mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql54");
		mySql.addSubPara(fm.GrpCustomerNo.value);
		mySql.addSubPara(fm.GrpContNo.value);
		mySql.addSubPara(tExecuteCom[i][0]);
		mySql.addSubPara(document.all('GrpName').value);
      }
         //strSQL += " and b.ExecuteCom = '"+tExecuteCom[i][0]+"'";
      arrResult=easyExecSql(mySql.getString());

   if(arrResult != null){
          if(arrResult.length==1)
          {
             try{document.all('GrpCustomerNo').value=arrResult[0][0]}catch(ex){alert(ex.message+"GrpCustomerNo")}
             try{document.all('GrpName').value=arrResult[0][1]}catch(ex){alert(ex.message+"GrpName")}
             try{document.all('GrpContNo').value=arrResult[0][2]}catch(ex){alert(ex.message+"GrpContNo")}
             try{document.all('Peoples').value=arrResult[0][3]}catch(ex){alert(ex.message+"Peoples")}
             if(document.all('Peoples').value == null ||
                document.all('Peoples').value == "0")
             {
                alert("投保人数为空！");
                return;
             }
          }
      else
      {
            var varSrc = "&CustomerNo=" + fm.GrpCustomerNo.value;
               varSrc += "&GrpContNo=" + fm.GrpContNo.value;
               varSrc += "&GrpName=" + fm.GrpName.value;
               showInfo = window.open("./FrameMainLCGrpQuery.jsp?Interface= LCGrpQuery.jsp"+varSrc,"",'width=800,height=550,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

      }
       } else {
         alert("没有符合条件的数据！");
         document.all('GrpCustomerNo').value = "";
         document.all('GrpName').value = "";
         document.all('GrpContNo').value = "";
         document.all('Peoples').value = "";
         return;
       }
         }
      }
     }
   }
   else
   {
      alert("请输入查询条件");
   }
   }
}
function afterLCGrpQuery(arrReturn)
{
     document.all('GrpCustomerNo').value=arrReturn[0][0];
     document.all('GrpName').value=arrReturn[0][1];
//     document.all('GrpContNo').value=arrReturn[0][2];
     document.all('Peoples').value=arrReturn[0][3];
}

//参数为出生年月,如1980-5-9
function getAge(birth)
{
    var oneYear = birth.substring(0,4);
    var age = mNowYear - oneYear;
    if (age <= 0)
    {
        age = 0
    }
    return age;
}


//提交前的校验、计算
function beforeSubmit()
{
	/////////////////////////////////////////////////////////////////////////
	// * 增加出险人的出现原因必须一致的校验  09-04-17                         //
	/////////////////////////////////////////////////////////////////////////
	var tClmNo = fm.RptNo.value;
	var tCustomerNo = fm.customerNo.value;
	//var tReasonSql = "select AccidentType from LLSubReport where subrptno='"+tClmNo+"' and customerno<>'"+tCustomerNo+"'";
	 mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql55");
		mySql.addSubPara(tClmNo);
		mySql.addSubPara(tCustomerNo);
	var occurReasonResult = easyExecSql(mySql.getString());//prompt("",tReasonSql);
	if(occurReasonResult){
		if(fm.occurReason.value!=occurReasonResult[0][0]){
			alert("出险人的出险原因必须保持一致！");
			return false;
		}
	}
    //获取表单域信息
    var RptNo = fm.RptNo.value;//赔案号
    var CustomerNo = fm.customerNo.value;//出险人编号
    var AccDate = fm.AccidentDate.value;//事故日期
    var AccReason = fm.occurReason.value;//出险原因
    var AccDate2 = fm.AccidentDate.value;//出险日期
//    var AccDesc = fm.accidentDetail.value;//出险细节
    var ClaimType = new Array;
    //理赔类型
    for(var j=0;j<fm.claimType.length;j++)
    {
        if(fm.claimType[j].checked == true)
        {
            ClaimType[j] = fm.claimType[j].value;
        }
    }
    //取得年月日信息
    var AccYear = AccDate.substring(0,4);
    var AccMonth = AccDate.substring(5,7);
    var AccDay = AccDate.substring(8,10);
    var AccYear2 = AccDate2.substring(0,4);
    var AccMonth2 = AccDate2.substring(5,7);
    var AccDay2 = AccDate2.substring(8,10);
    //----------------------------------------------------------BEG2005-7-12 16:45
    //增加申请人姓名和关系的非空校验
    //----------------------------------------------------------
    if (fm.GrpCustomerNo.value == "" || fm.GrpCustomerNo.value == null)
    {
        alert("请输入团体客户号！");
        return false;
    }
    if (fm.GrpName.value == "" || fm.GrpName.value == null)
    {
        alert("请输入单位名称！");
        return false;
    }
    if (fm.GrpContNo.value == "" || fm.GrpContNo.value == null)
    {
        alert("请输入团体保单号！");
        return false;
    }
/*
    var tAssigneeZip = fm.AssigneeZip.value;
    if (tAssigneeZip.length > 6)
    {
        alert("邮编错误！");
        return false;
    }
*/
    //判断受理日期
    if (fm.AcceptedDate.value == "" || fm.AcceptedDate.value == null)
    {
        alert("请输入受理日期！");
        return false;
    }
    

    //2-1 检查出险日期
    if (AccDate2 == null || AccDate2 == '')
    {
        alert("出险日期不能为空！");
        return false;
    }
    else
    {
        if (AccYear2 > mNowYear)
        {
            alert("出险日期不能晚于当前日期");
            return false;
        }
        else if (AccYear2 == mNowYear)
        {
            if (AccMonth2 > mNowMonth)
            {

                alert("事故日期不能晚于当前日期");
                return false;
            }
            else if (AccMonth2 == mNowMonth && AccDay2 > mNowDay)
            {
                alert("出险日期不能晚于当前日期");
                return false;
            }
        }
    }
    //2-2 检查出险日期
    if (AccYear > AccYear2 )
    {
        alert("事故日期不能晚于出险日期");
        return false;
    }
    else if (AccYear == AccYear2)
    {
        if (AccMonth > AccMonth2)
        {

            alert("事故日期不能晚于出险日期");
            return false;
        }
        else if (AccMonth == AccMonth2 && AccDay > AccDay2)
        {
            alert("事故日期不能晚于出险日期");
            return false;
        }
    }
    //3 检查出险原因
    if (AccReason == null || AccReason == '')
    {
        alert("出险原因不能为空！");
        return false;
    }

    //5 检查理赔类型
    if (ClaimType == null || ClaimType == '')
    {
        alert("出险类型不能为空！");
        return false;
    }

    return true;
}

//预估金额信息录入
function operStandPayInfo()
{
     var varSrc ="";
     var CaseNo = fm.RptNo.value;
     pathStr="./StandPayInfoMain.jsp?CaseNo="+CaseNo;
     showInfo = OpenWindowNew(pathStr,"","middle");
}

//理算结果统计查询
function QuerydutySetInfo()
{
     var varSrc ="";
     var CaseNo = fm.RptNo.value;
     pathStr="./QuerydutySetInfoMain.jsp?CaseNo="+CaseNo;
     showInfo = OpenWindowNew(pathStr,"","middle");
}

//帐单信息录入
function StandPaySave()
{
    var row = MedFeeInHosInpGrid.mulLineCount-1;
    var i = 0;
    var arr = new Array();
    var tCustomerNo = '';
    var tClinicStartDate = '';
    var tClinicEndDate = '';
    var tClinicDayCount1 = '';
    var tFeeType = '';
    var numFlag = false;
  if(row < 0){
    alert("请您输入数据再保存");
    return false;
    }
  for(var m=0;m<=row;m++ )
  {
   var i = MedFeeInHosInpGrid.getChkNo(m);//得到焦点行
   //aler(i);
   //if(i==true)
   //{
    numFlag = true;
   fm.hideOperate.value = "INSERT";
   tCustomerNo = MedFeeInHosInpGrid.getRowColData(m ,1);//出险人号码
   tClinicStartDate = MedFeeInHosInpGrid.getRowColData(m ,9);//帐单开始日期
   tClinicEndDate = MedFeeInHosInpGrid.getRowColData(m ,10);//帐单结束日期
   tClinicDayCount1 = MedFeeInHosInpGrid.getRowColData(m ,11);//天数
   tFeeType = MedFeeInHosInpGrid.getRowColData(m ,2);//帐单种类
   tMainFeeNo = MedFeeInHosInpGrid.getRowColData(m ,6);//帐单号码
   tClinicMedFeeTypeName = MedFeeInHosInpGrid.getRowColData(m ,12);//费用名称
   tClinicMedFeeSum = MedFeeInHosInpGrid.getRowColData(m ,14);//原始费用

//出险人号码检验
  if(tCustomerNo=='')
  {
    alert("请您输入出险人号码");
    return false;
  }
//帐单种类
  if(tFeeType=='')
  {
    alert("请您输入帐单种类");
    return false;
  }
//帐单号码检验
  if(tMainFeeNo=='')
  {
    alert("请您输入帐单号码");
    return false;
  }
//帐单开始日期检验
  if(tClinicStartDate=='')
  {
    alert("请您输入开始日期");
    return false;
  }

if(tClinicStartDate != ''){
 if(!isDate(trim(tClinicStartDate)))
  {
    alert('第'+m+'行的帐单开始日期格式应为XXXX-XX-XX');
    return false;
  }
}

if(tClinicEndDate != ''){
 if(!isDate(trim(tClinicEndDate)))
  {
    alert('第'+m+'行的帐单结束日期格式应为XXXX-XX-XX');
    return false;
  }
}
//费用类型检验
//  if(tFeeType == 'A'&& tClinicEndDate != '' )
//  {
//    alert("门诊类型不应有结束日期");
//    return false;
//  }
  if(tFeeType == 'B'&& tClinicEndDate == '' )
  {
    alert("住院类型结束日期不能为空");
    return false;
  }
   //日期检验
    if (dateDiff(tClinicStartDate,mCurrentDate,'D') < 0 || dateDiff(tClinicEndDate,mCurrentDate,'D') < 0)
    {
        alert("日期错误");
        return;
    }
    /*var date4 = dateDiff(fm.AccidentDate.value,tClinicStartDate,'D');
    if(date4 < 0)
    {
       alert("单证开始日期早于出险日期");
       return false;
    }*/
//费用名称检验
  if(tClinicMedFeeTypeName=='')
  {
    alert("请您输入费用名称");
    return false;
  }
//原始费用检验
  if(tClinicMedFeeSum=='')
  {
    alert("请您输入原始费用");
    return false;
  }
// }else
//   {
//      alert ("没有选择要保存的数据!")
//      return false;  
//   }
   
   
  }

    fm.fmtransact.value="INSERT||MAIN";
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
    var name='提示';   //网页名称，可为空; 
    var iWidth=550;      //弹出窗口的宽度; 
    var iHeight=250;     //弹出窗口的高度; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    fm.action ="./LLGrpMedicalFeeInpSave.jsp";
    fm.submit();
}
//删除帐单
function deleteFeeClick()
{
    var numFlag=false;
    var row = MedFeeInHosInpGrid.mulLineCount-1;
    for(var m=0;m<=row;m++)
    {
      var i = MedFeeInHosInpGrid.getChkNo(m);//得到焦点行
      if(i==true)
      {
         numFlag = true;
         var feeNo = MedFeeInHosInpGrid.getRowColData(m,20);
         if(feeNo=="")
          {
             alert("该明细未保存，可以直接删除！");
          }
      }
    }
     if(numFlag==false)
    {
        alert("请选择要删除的帐单");
        return false;
    }
    else
    {
          fm.hideOperate.value = "DELETE";
          fm.action ='./LLGrpMedicalFeeInpSave.jsp';
          submitForm();  
    }     
    
}
function getMedFeeInHosInpGrid()
{
    var i = MedFeeInHosInpGrid.getSelNo() - 1;//得到焦点行

    MedFeeInHosInpGrid.setRowColData(i,1,fm.customerNo.value);//出险人编码

}
//预估金额返回方法
function getGrpstandpay(){
	//var tSql = "select sum(standpay) from LLStandPayInfo where caseno='"+fm.RptNo.value+"'";//prompt("",tSql);
	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql56");
		mySql.addSubPara(fm.RptNo.value);
	var standpay = easyExecSql(mySql.getString());
	if(standpay!=null||standpay!=""||standpay!="null"){
		fm.Grpstandpay.value = standpay; 
	}
}

function getin()
{
var tFlag  = "TOSIM"; //表示来自[批量案件理赔]
   var tRgtNo = fm.RptNo.value;
//   var strUrl = "../claim/GrpCustomerDiskInput.jsp?grpcontno="+tRptNo+"&Flag="+tFlag;
   var strUrl = "./GrpCustomerDiskMain.jsp?Flag="+tFlag+"&RgtNo="+tRgtNo; 
   //showInfo=window.open(strUrl,"被保人清单导入","");
   showInfo=window.open(strUrl,"","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}

//影像查询
function colQueryImage()
{
  var strUrl="LLColQueryImageMain.jsp?claimNo="+document.all('ClmNo').value+"&subtype=LP1001";
  window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
}

/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
  * 修改原因：添加出险人信息删除操作
  * 修 改 人：万泽辉
  * 修改日期：2006-02-17
  －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
  */
function deleteClick()
{
    if (confirm("您确实想删除该记录吗？"))
    {
        alert("删除信息后请重新匹配理算！");
        fm.fmtransact.value = "DELETE";
        fm.action = './LLGrpRegisterSave.jsp';
        submitForm();
    }
}
/*－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
  * 修改原因：添加出险人信息删除后的返回提交
  * 修 改 人：万泽辉
  * 修改日期：2006-02-17
  －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
  */
function afterSubmit4( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail")
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

        initForm();
    }
    queryRegister();
    tSaveFlag ="0";
}

//校验日期格式
function checkapplydate(){
  if(fm.AccidentDate.value.length==8){
  if(fm.AccidentDate.value.indexOf('-')==-1){ 
    var Year =     fm.AccidentDate.value.substring(0,4);
    var Month =    fm.AccidentDate.value.substring(4,6);
    var Day =      fm.AccidentDate.value.substring(6,8);
    fm.AccidentDate.value = Year+"-"+Month+"-"+Day;
    if(Year=="0000"||Month=="00"||Day=="00"){
        alert("您输入的日期有误!");
        fm.AccidentDate.value = ""; 
        return;
      }
  }
} else {var Year =     fm.AccidentDate.value.substring(0,4);
      var Month =    fm.AccidentDate.value.substring(5,7);
      var Day =      fm.AccidentDate.value.substring(8,10);
      if(Year=="0000"||Month=="00"||Day=="00"){
        alert("您输入的日期有误!");
        fm.AccidentDate.value = "";
        return;
         }
  }
}
//无名转有名保险期间判断
function TempCustomer(){
      //  var StrSQL = "select grpcontno From lctempcustomer where startdate <= '"+fm.AccidentDate.value+"' and paytodate >= '"+fm.AccidentDate.value+"' and grpcontno = '"+fm.GrpContNo.value+"'";
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql57");
		mySql.addSubPara(fm.AccidentDate.value);
		mySql.addSubPara(fm.AccidentDate.value);
		mySql.addSubPara(fm.GrpContNo.value);
        var arr= easyExecSql(mySql.getString());
        if(arr != null ){
        return true;
        }else{
        return false;
        }
}
//判断保险期间
function showDate(){
        //jixf add 20051213
        /*var strSQL = " select a.polno from LCPol a, LCGrpCont b where  a.CValidate<='"+fm.AccidentDate.value+"' and a.enddate>='"+fm.AccidentDate.value+"'";
        strSQL=strSQL+" and a.GrpContNo=b.GrpContNo and insuredno='"+fm.customerNo.value+"' and a.appflag in ('1','4')";
       */ mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql58");
		mySql.addSubPara(fm.AccidentDate.value);
		mySql.addSubPara(fm.AccidentDate.value);
		mySql.addSubPara(fm.GrpContNo.value);
         if (fm.GrpCustomerNo.value!=null&&fm.GrpCustomerNo.value!='000000')
         {
         //strSQL=strSQL+" and b.AppntNo='"+fm.GrpCustomerNo.value+"'";
         mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql59");
		mySql.addSubPara(fm.AccidentDate.value);
		mySql.addSubPara(fm.AccidentDate.value);
		mySql.addSubPara(fm.GrpContNo.value);
		mySql.addSubPara(fm.GrpCustomerNo.value);
         }
        if (fm.GrpContNo.value!=null)
        {
       // strSQL=strSQL+" and b.GrpContNo='"+fm.GrpContNo.value+"'";
         mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql60");
		mySql.addSubPara(fm.AccidentDate.value);
		mySql.addSubPara(fm.AccidentDate.value);
		mySql.addSubPara(fm.GrpContNo.value);
		mySql.addSubPara(fm.GrpCustomerNo.value);
		mySql.addSubPara(fm.GrpContNo.value);
        }
        
         var arr= easyExecSql(mySql.getString());
        if(arr != null ){
        return true;
        }else{
        return false;
        }
}

function showScanInfo()
{
    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    var tClaimNo = fm.RptNo.value;         //赔案号  
    var tCustNo = fm.customerNo.value;     //客户号
    var tCustomerName = fm.customerName.value;     //客户姓名
    
  if (tCustNo == "")
  {
      alert("请选择出险人！");
      return;
  }
    var strUrl="SubScanClaimQuery.jsp?ClaimNo="+tClaimNo+"&CustomerNo="+tCustNo+"&CustomerName="+tCustomerName;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
	
}

function AddAffixClick()
{
   var tClmNo=document.all("RptNo").value;
   var urlStr;
   urlStr="./LLInqCourseAffixFrame.jsp?ClmNo="+tClmNo+""; 
   window.open(urlStr,"",'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');   			
}

function LoadAffixClick()
{
   var tClmNo=document.all("RptNo").value;
   var urlStr;
   urlStr="./LLInqCourseShowAffixFrame.jsp?ClmNo="+tClmNo+""; 
   window.open(urlStr,"",'height=100, width=400, top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');   			
}

//报案信息导出
function  PrintReportClass()
{	
	
  document.all('RgtNo').value = document.all('RptNo').value;
	
	fm.action = "./ClaimPrtPDF.jsp?operator=PrintReportClass";
  fm.target=".";
	fm.submit();
	
}

//导出该赔案下所有出险人既往赔付信息
function getLastCaseInfo(){
var CaseNo = fm.RptNo.value;
var GrpContNo = fm.GrpContNo.value
if(CaseNo == "" || CaseNo == null){
	alert("请先点保存！");
	return false;
}else{
	var row = SubReportGrid.getSelNo();
    if(row < 1) {
        alert("请选中一行记录！");
        return;
    }
    var tCustomerNo = SubReportGrid.getRowColData(row-1,1);
//alert("bb");
//  var tSQL = "select a.clmno,a.grpcontno,a.insuredno,a.insuredname,a.getdutykind,"
// + " b.accdate,b.endcasedate,a.riskcode,a.standpay," 
// + "a.realpay from llclaimpolicy a,llcase b "
// +"where  a.clmno=b.caseno and a.insuredno=b.customerno  and b.rgtstate='60' and "
// +" a.insuredno in (select distinct customerno from llsubreport where subrptno='"
//+ CaseNo + "' ) and a.grpcontno = '" + GrpContNo + "'";
	/*var tSQL = "select a.clmno,a.grpcontno,a.insuredno,a.insuredname,a.getdutykind,"
		 + " b.accdate,b.endcasedate,a.riskcode,a.standpay," 
		 + "a.realpay from llclaimpolicy a,llcase b "
		 +"where  a.clmno=b.caseno and a.insuredno=b.customerno "
		 //+" and b.rgtstate='60'"
		 +" and "
		 +" a.insuredno = '"+ tCustomerNo +"'"
	     +" and a.clmno <>'"+CaseNo+"'";
		 //+" and a.grpcontno = '" + GrpContNo + "'";*/
		 mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql61");
		mySql.addSubPara(tCustomerNo);
		mySql.addSubPara(CaseNo);
		  var arr = easyExecSql(mySql.getString());
		  if(!arr){
			  alert("未查询到既往赔案信息，无法导出");
			  return false;
		  }
fm.tSQL.value = tSQL;
  var Title="既往赔案信息查询";
	var SheetName="既往赔案信息查询";       
	var ColName = "赔案号@团体保单号@客户号@客户姓名@理赔类型@出险日期@结案日期@险种代码@应赔付金额@实际赔付金额";
	fm.action = "./PubCreateExcelSave.jsp?tSQl="+tSQL+"&Title=既往赔案信息查询"+"&SheetName="+Title+"&ColName="+ColName;
	fm.target="../claimgrp";		 	
	fm.submit();
}   
}

//---------------------------------------------------------------------------------------*
//功能：理赔类型逻辑判断
//处理：1 死亡、高残、医疗三者只可选一
//      2 选择死亡或高残时，默认选中豁免
//---------------------------------------------------------------------------------------*
function callClaimType(ttype)
{
    switch (ttype)
    {
        case "02" : //死亡
            if (fm.claimType[0].checked == true)
            {
                fm.claimType[4].checked = true;
            }
//            if ((fm.claimType[1].checked == true || fm.claimType[5].checked == true) && fm.claimType[0].checked == true)
//            {
//                alert("死亡、高残、医疗三者只可选一!");
//                fm.claimType[0].checked = false;
//                return;
//            }
//            fm.claimType[4].checked = true;
        case "03" :
            if (fm.claimType[1].checked == true)
            {
                fm.claimType[4].checked = true;
            }
//            if ((fm.claimType[0].checked == true || fm.claimType[5].checked == true)&& fm.claimType[1].checked == true)
//            {
//                alert("死亡、高残、医疗三者只可选一!");
//                fm.claimType[1].checked = false;
//                return;
//            }
//            fm.claimType[4].checked = true;
        case "09" :
//            if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[4].checked == false)
//            {
//                alert("选取死亡、高残必须选择豁免!");
//                fm.claimType[4].checked = true;
//                return;
//            }
        case "00" :
//            if ((fm.claimType[0].checked == true || fm.claimType[1].checked == true) && fm.claimType[5].checked == true)
//            {
//                alert("死亡、高残、医疗三者只可选一!");
//                fm.claimType[5].checked = false;
//                return;
//            }
        default :
        	
        	getChangeDate();
        
            return;
    }
}

/**
 * 2008-11-18
 * zhangzheng
 * 根据选择的理赔类型决定医疗出险日期和其他出险日期是否可以录入
 * 
**/
function getChangeDate()
{
	var flag=false;//控制是否准许录入其他出险日期标志,默认不准许录入
	
	//理赔类型包含医疗时,准许录入医疗出险日期
	if(fm.claimType[5].checked == true)
	{
	    //document.all('MedicalAccidentDate').disabled=false;
	}
	else
	{
	    //document.all('MedicalAccidentDate').value="";
	    //document.all('MedicalAccidentDate').disabled=true;
	}
	
	//当只存在医疗类型时,其他出险日期不准许录入
	var ClaimType = new Array;
    for(var j=0;j<fm.claimType.length;j++)
    {
    	  if((fm.claimType[j].checked == true)&&(j!=5))
    	  {
    		  flag=true;
    	  }
    }
    
    if(flag==true)
    {
    	//document.all('OtherAccidentDate').disabled=false;
    }
    else
    {
	    //document.all('OtherAccidentDate').value="";
    	//document.all('OtherAccidentDate').disabled=true;
    }
}


//录入医疗单证信息
function showLLMedicalFeeInp()
{

    //var tSel = SubReportGrid.getSelNo();
    //var tClaimNo = SubReportGrid.getRowColData(tSel - 1,8);         //赔案号
    //var tCaseNo = SubReportGrid.getRowColData(tSel - 1,2);          //分案号
    //var tCustNo = SubReportGrid.getRowColData(tSel - 1,1);          //客户号
    //if( tSel == 0 || tSel == null ){
//        alert( "请先选择一条客户记录，再执行此操作!" );
//        return false;
//    }
//else{
//        window.open("LLMedicalFeeInpInput.jsp?clamNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&custName="+tCustName+"&custSex="+tCustSex,"医疗单证信息");
//    }

    var row = SubReportGrid.getSelNo();
    if(row < 1)
    {
        alert("请选中一行记录！");
        return;
    }
    var tClaimNo = fm.RptNo.value;         //赔案号
    var tCaseNo = fm.RptNo.value;          //分案号
    var tCustNo = fm.customerNo.value;          //客户号
  if (tCustNo == "")
  {
      alert("请选择出险人！");
      return;
  }
    var strUrl="../claim/LLMedicalFeeInpMain.jsp?claimNo="+tClaimNo+"&caseNo="+tCaseNo+"&custNo="+tCustNo+"&accDate="+fm.AccidentDate.value+"&medAccDate="+fm.AccidentDate.value+"&otherAccDate="+fm.AccidentDate.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}

//出险原因判断
function checkOccurReason()
{
    if (fm.occurReason.value == '1')
    {
        //fm.accidentDetail.disabled = false;
    }
    else if (fm.occurReason.value == '2')
    {
        //fm.accidentDetail.disabled = true;
    }
}


//审核结论查询
function queryAudit()
{
    /*var strSql = " select AuditConclusion,AuditIdea,SpecialRemark,AuditNoPassReason,AuditNoPassDesc from LLClaimUWMain where "
               + " ClmNo = '" + fm.RptNo.value + "'";*/
     mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql62");
		mySql.addSubPara(fm.RptNo.value );
//    alert("strSql= "+strSql);
    //prompt("",strSql);
    var tAudit = easyExecSql(mySql.getString());
//    alert(tAudit);
    if (tAudit != null)
    {
        fm.SimpleConclusion2.value = tAudit[0][0];
        fm.AuditIdea.value = tAudit[0][1];
        fm.SpecialRemark1.value = tAudit[0][2];
        showOneCodeName('llexamconclusion2','SimpleConclusion2','SimpleConclusion2Name');//审核结论
        //fm.ProtestReason.value = tAudit[0][3];
        //fm.ProtestReasonDesc.value = tAudit[0][4];
        //showOneCodeName('llprotestreason','ProtestReason','ProtestReasonName');
        //showOneCodeName('llclaimconclusion','AuditConclusion','AuditConclusionName');
        //显示隐藏层
        //choiseConclusionType();
        //fm.printAuditRpt.disabled = false;
    }
  //  alert("query audit!");
}


//受益人分配
function showBnf()
{
    var tSel = SubReportGrid.getSelNo();

    if( tSel == 0 || tSel == null ){
        alert( "请先选择一条记录，再执行此操作!!!" );
    }
    else{
    	 /**=========================================================================BEG
        修改状态：
        修改原因：在受益人分配前进行医疗类理赔金额和保项层面汇总金额的校验
        修 改 人：续涛
        修改日期：2005.08.11
       =========================================================================**/
    var tSql ;
    var tClaimNo = fm.RptNo.value;
    
    //查询医疗类理赔类型的金额
   /* tSql = " select nvl(sum(a.RealPay),0) from LLClaimDutyKind a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GetDutyKind in ('100','200') "
       ;*/
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql63");
		mySql.addSubPara(tClaimNo);
    var arr = easyExecSql( mySql.getString() );    
    var tSumDutyKind = arr[0][0];


    //查询保项层面医疗类理赔类型的金额
   /* tSql = " select nvl(sum(a.RealPay),0) from LLClaimDetail a  where 1=1 "
       +" and a.ClmNo = '"+tClaimNo+"'"
       +" and a.GetDutyKind in ('100','200') "
       +" and a.GiveType not in ('1') "       
       ;*/
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql64");
		mySql.addSubPara(tClaimNo);
    var brr = easyExecSql( mySql.getString() );    
    
    var tSumDutyCode = brr[0][0];
            
    if (tSumDutyKind != tSumDutyCode)
    {
        alert("提示:医疗类赔付总金额为:["+tSumDutyKind+"],而保项赔付总金额为:["+tSumDutyCode+"],请先进行保项层面金额调整,以便进行保额冲减!");
        return;
    }
    /**=========================================================================END**/
    
    //调查、呈报、问题件和二核校验,保单结算、合同处理
    if (!checkPre())
    {
        return;
    } 
    
    var rptNo = fm.RptNo.value;//赔案号
    var strUrl="../claim/LLBnfMain.jsp?RptNo="+rptNo+"&BnfKind=A&InsuredNo="+fm.customerNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
//    window.open(strUrl,"出险人分配");
    }
   
}

//受益人分配前确认问题件\调查\呈报\二核是否完毕,保单结算\合同处理
function checkPre()
{
//    alert("开始");
    //问题件
   /* var strSql0 = "select AffixConclusion from LLAffix where "
               + "RgtNo = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql65");
		mySql.addSubPara(tClaimNo);
    var tAffixConclusion = easyExecSql(mySql.getString());
    
    if (tAffixConclusion)
    {
        for (var i = 0; i < tAffixConclusion.length; i++)
        {
//            alert(tAffixConclusion[i]);
            if (tAffixConclusion[i] == null || tAffixConclusion[i] == "")
            {
//                alert("问题件回销没有完成!");
//                return false;
            }
        }
    }
    //调查
  /*  var strSql1 = "select FiniFlag from LLInqConclusion where "
               + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql66");
		mySql.addSubPara(fm.RptNo.value );
    var tFiniFlag = easyExecSql(mySql.getString());
    
    if (tFiniFlag)
    {
        for (var i = 0; i < tFiniFlag.length; i++)
        {
            if (tFiniFlag[i] != '1')
            {
                alert("发起的调查没有完成!");
                return false;
            }
        }
    }
    //呈报

   /* var strSql2 = "select SubState from LLSubmitApply where "
               + "clmno = '" + fm.RptNo.value + "'";*/
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql67");
		mySql.addSubPara(fm.RptNo.value );
    var tSubState = easyExecSql(mySql.getString());
    
    if (tSubState)
    {
        for (var i = 0; i < tSubState.length; i++)
        {
            if (tSubState[i] != '1')
            {
                alert("发起的呈报没有完成!");
                return false;
            }
        }
    }

    //二核=============================================================目前尚待完善
/*
    //保单结算\合同处理是否完成
    var strSql3 = "select conbalflag,condealflag from llclaim where "
                + "clmno = '" + fm.RptNo.value + "'";
    var tFlag = easyExecSql(strSql3);
//    alert(tFlag);
    if (tFlag == null)
    {
        alert("未进行保单结算和合同处理操作!");
        return;
    }
    if (tFlag[0][0] != '1')
    {
        alert("未进行保单结算操作!");
        return;
    }
    if (tFlag[0][1] != '1')
    {
        alert("未进行合同处理操作!");
        return;
    }
*/
    return true;
}



//2009-04-22 zhangzheng 双击下拉框后的响应函数
function afterCodeSelect( cCodeName, Field ) {

    //alert(cCodeName);  
    
	//审核结论
    if(cCodeName=="llexamconclusion2"){
    	
    	//当给付原因不为拒付时,清空拒付原因和拒付依据
    	if(fm.SimpleConclusion2.value !='1'){
    		
       		fm.ProtestReason.value='';
    		fm.ProtestReasonName.value='';
    		fm.ProtestReasonDesc.value='';	
    		fm.SpecialRemark1.value='';
    	}
    	else
    	{
    		//清空特殊备注
    		fm.SpecialRemark1.value='';
    	}
        return true;
    }
    if(cCodeName=="llfeetype"){
    	fm.Feetype.value=Field.value;
    	var tFeeSql = "";
    	//alert(Field.value);
    }
    if(cCodeName=="llmedfeetype"){
    	
    	//当给付原因不为拒付时,清空拒付原因和拒付依据
    	//var tSql = "select code,codename from ldcode where codetype='"+Feetype+"'";
    	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql68");
		mySql.addSubPara(Feetype);
    	var tArrResult = easyExecSql(mySql.getString());
    	if(tArrResult){
    		
    	}
        return true;
    }
}
function showFeeType(spanID,no){
//	var tSql="select code,codename from ldcode where codetype='""'"
}

//zy 2009-07-28 17:43 获取康福产品保单的险种信息
function getLLEdorTypeCA()
{

	// var tAccRiskCodeSql="select distinct riskcode from lcgrppol where grpcontno='"+fm.GrpContNo.value+"' and riskcode='211901' and grpname like 'MS人寿保险股份有限公司%'";
	mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpSimpleInputSql");
		mySql.setSqlId("LLGrpSimpleSql69");
		mySql.addSubPara(fm.GrpContNo.value);
	 //prompt("",tAccRiskCodeSql);
   var tAccRiskCode=easyExecSql(mySql.getString());
   if(tAccRiskCode=="211901")
   {
   	fm.AccRiskCode.value=tAccRiskCode;
   }
   else
   {
   	fm.AccRiskCode.value="";   
   }

}

//zy 2009-07-28 14:58 账户资金调整
function ctrllcinsureacc()
{
	
    var strUrl="./LLGrpClaimEdorTypeCAMain.jsp?GrpContNo="+fm.GrpContNo.value+"&RptNo="+fm.RptNo.value;
    window.open(strUrl,"",'width=800,height=600,top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
