
//程序名称：
//程序功能：个人保全申请
//创建日期：2005-04-26 16:49:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
var mDebug="1";
var aEdorFlag='0';
var mEdorType;
var turnPage = new turnPageClass();
var turnPageCustomerContGrid = new turnPageClass();
var turnPageApprove = new turnPageClass();
var mflag = "";  //保单、客户查询标识
var theFirstValue="";
var theSecondValue="";
var hasSaved = "";
var userEdorPopedom = "";  //当前用户保全级别

var tPreLoanFlag="";
var CusBQPrintFlag="";
var mySql=new SqlClass();

/*********************************************************************
 *  页面层显示控制
 *  描述: 页面层显示控制
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initDiv()
{
    var EdorAcceptNo = document.all('EdorAcceptNo').value;
    var MissionID = document.all('MissionID').value
    var SubMissionID = document.all('SubMissionID').value
		
    if(EdorAcceptNo == null || EdorAcceptNo == "")
    {
        alert("保全受理号为空！");
        return;
    }
    if(MissionID == null || MissionID == "")
    {
        alert("任务号为空！");
        return;
    }
    if(SubMissionID == null || SubMissionID == "")
    {
        alert("子任务号为空！");
        return;
    }
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql1");
    mySql.addSubPara(fm.UserCode.value);   
    //查询当前用户保全级别

    var urr = easyExecSql(mySql.getString());
    if ( urr )
    {
        urr[0][0]==null||urr[0][0]=='null'?'0':userEdorPopedom = urr[0][0];
        
    }

    //查询工作流保全申请确认节点
  
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql2");
    mySql.addSubPara(EdorAcceptNo);  
    var brr = easyExecSql(mySql.getString());

    if ( brr )  //已经申请保存过
    {
        //alert("已经申请保存过");
        hasSaved = "Y";
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.OtherNo.value     = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.OtherNoType.value = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.EdorAppName.value = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.AppType.value     = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.EdorAppDate.value = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.PEdorState.value  = brr[0][5];
        brr[0][6]==null||brr[0][6]=='null'?'0':fm.PEdorStateName.value  = brr[0][6];
        brr[0][7]==null||brr[0][7]=='null'?'0':fm.BankCode.value    = brr[0][7];
        brr[0][8]==null||brr[0][8]=='null'?'0':fm.BankAccNo.value   = brr[0][8];
        brr[0][9]==null||brr[0][9]=='null'?'0':fm.AccName.value     = brr[0][9];
        brr[0][10]==null||brr[0][10]=='null'?'0':fm.OtherNoName.value    = brr[0][10];
        brr[0][11]==null||brr[0][11]=='null'?'0':fm.AppTypeName.value   = brr[0][11];
        brr[0][12]==null||brr[0][12]=='null'?'0':fm.BankName.value     = brr[0][12];
        brr[0][13]==null||brr[0][13]=='null'?'0':fm.PayGetName.value     = brr[0][13];
        brr[0][14]==null||brr[0][14]=='null'?'0':fm.PersonID.value     = brr[0][14];
        brr[0][15]==null||brr[0][15]=='null'?'0':fm.GetPayForm.value     = brr[0][15]; //added by liurx 05-12-09
        brr[0][16]==null||brr[0][16]=='null'?'0':fm.GetPayFormName.value     = brr[0][16]; //added by liurx 05-12-09
        
        brr[0][17]==null||brr[0][17]==''?'':fm.BfName.value     = brr[0][17];
        brr[0][18]==null||brr[0][18]==''?'':fm.BfIDType.value     = brr[0][18];
        brr[0][19]==null||brr[0][19]==''?'':fm.BfIDNo.value     = brr[0][19];
        brr[0][20]==null||brr[0][20]==''?'':fm.BfPhone.value     = brr[0][20];
        brr[0][21]==null||brr[0][21]==''?'':fm.BfAgentCode.value     = brr[0][21];
        brr[0][22]==null||brr[0][22]==''?'':fm.BfIDTypeName.value     = brr[0][22];
        
        brr[0][23]==null||brr[0][23]==''?'':fm.CustomerPhone.value     = brr[0][23];
        brr[0][24]==null||brr[0][24]==''?'':fm.ManageCom.value     = brr[0][24];
        brr[0][25]==null||brr[0][25]==''?'':fm.InternalSwitchChnl.value     = brr[0][25];
        brr[0][26]==null||brr[0][26]==''?'':fm.InternalSwitchChnlName.value     = brr[0][26];
        brr[0][27]==null||brr[0][27]==''?'':fm.Mobile.value     = brr[0][27];
        brr[0][28]==null||brr[0][28]==''?'':fm.PostalAddress.value     = brr[0][28];
        brr[0][29]==null||brr[0][29]==''?'':fm.ZipCode.value     = brr[0][29];

        fm.EdorAcceptNo_Read.value= fm.EdorAcceptNo.value;
        fm.OtherNo_Read.value     = fm.OtherNo.value;
        fm.OtherNoType_Read.value = fm.OtherNoName.value;
        fm.EdorAppName_Read.value = fm.EdorAppName.value;
        fm.AppType_Read.value     = fm.AppTypeName.value;
        fm.EdorAppDate_Read.value = fm.EdorAppDate.value;
        fm.PEdorStateName_Read.value = fm.PEdorStateName.value;
        
        fm.BfName_Read.value = fm.BfName.value;     
		fm.BfIDType_Read.value = fm.BfIDTypeName.value;   
		fm.BfIDNo_Read.value = fm.BfIDNo.value;     
		fm.BfPhone_Read.value = fm.BfPhone.value;  
		fm.BfAgentCode_Read.value = fm.BfAgentCode.value;
		
		fm.CustomerPhone_Read.value = fm.CustomerPhone.value;   
		fm.ManageCom_Read.value = fm.ManageCom.value;     
		fm.InternalSwitchChnlName_Read.value = fm.InternalSwitchChnlName.value;  
		
		    fm.Mobile_Read.value     = fm.Mobile.value;
        fm.PostalAddress_Read.value = fm.PostalAddress.value;
        fm.ZipCode_Read.value = fm.ZipCode.value;
        
        fm.Mobile_Mod.value     = fm.Mobile.value;
        fm.PostalAddress_Mod.value = fm.PostalAddress.value;
        fm.ZipCode_Mod.value = fm.ZipCode.value;
		document.all("divBehalfAgentCodeInfo").style.display = "none";
    	document.all("divBehalfInfo").style.display = "none";
    	document.all("divInternalSwitchInfo").style.display = "none";
    	divEdorPersonInfo.style.display = "none";
    		
		if (fm.AppType.value == '2') {
    		document.all("divBehalfAgentCodeInfoRead").style.display = "";
    		document.all("divBehalfInfoRead").style.display = "";
    		document.all("divInternalSwitchInfoRead").style.display = "none";
    	}
    	else if (fm.AppType.value == '3') {
    	    document.all("divBehalfAgentCodeInfoRead").style.display = "none";
    		document.all("divBehalfInfoRead").style.display = "";
    		document.all("divInternalSwitchInfoRead").style.display = "none";
    	}
    	else if (fm.AppType.value == '6') {
    	    document.all("divBehalfAgentCodeInfoRead").style.display = "none";
    		document.all("divBehalfInfoRead").style.display = "none";
    		document.all("divInternalSwitchInfoRead").style.display = "";
    	}
    	else {
    		document.all("divBehalfAgentCodeInfoRead").style.display = "none";
    		document.all("divBehalfInfoRead").style.display = "none";
    		document.all("divInternalSwitchInfoRead").style.display = "none";
    	}
        
	
        //fm.PayGetName_Read.value = fm.PayGetName.value;
        //fm.PersonID_Read.value = fm.PersonID.value;
        //fm.BankCode_Read.value    = fm.BankName.value;
        //fm.BankAccNo_Read.value   = fm.BankAccNo.value;
        //fm.AccName_Read.value     = fm.AccName.value;

        divApplySaveWrite.style.display="none";     //保全受理信息可填
        divApplySaveRead.style.display='';          //保全受理信息只读
        divApplySaveBT.style.display="none";        //隐掉申请保存按钮

        if(fm.OtherNoType.value == "1")  //个人客户号
        {
            displayCustomerInfo(fm.OtherNo.value);  //查询客户详细信息
            getEdorItemGrid();                  //查询保全项目列表详细信息
            divCustomer.style.display='';       //客户信息
            divCont.style.display="none";       //保单信息
            divContState.style.display="none";  //保单状态信息
            divEdorItemInfo.style.display='';   //保全项目信息

            if(fm.LoadFlag.value == "edorApp" || fm.LoadFlag.value == "scanApp" || fm.LoadFlag.value=="CM")
            {
                //保全申请
                divappconfirm.style.display=''; //申请确认按钮
                divApproveModify.style.display="none";//复核修改确认按钮
                showDivGetPayForm();        //显示收付费方式信息
                divEdorTest.style.display="none";     //试算完毕按钮
            }
            if(fm.LoadFlag.value == "approveModify")
            {
                //保全审批修改
                divappconfirm.style.display="none"; //申请确认按钮
                divApproveModify.style.display='';  //复核修改确认按钮
                showDivGetPayForm();        //显示收付费方式信息
                divEdorTest.style.display="none";   //试算完毕按钮
            }
            if(fm.LoadFlag.value == "edorTest")
            {
                //保全试算
                divappconfirm.style.display="none";    //申请确认按钮
                divApproveModify.style.display="none"; //复核修改确认按钮
                showDivGetPayForm();        //隐藏收付费方式信息
                divEdorTest.style.display='';          //试算完毕按钮
            }
        }
        if(fm.OtherNoType.value == "3")  //个人保单号
        {
            displayContInfo(fm.OtherNo.value);  //查询保单详细信息
            displayContStateInfo(fm.OtherNo.value);  //查询保单状态详细信息
            getEdorItemGrid();                  //查询保全项目列表详细信息
            divCustomer.style.display="none";   //客户信息
            divCont.style.display='';           //保单信息
            divContState.style.display='';      //保单状态信息
            divEdorItemInfo.style.display='';   //保全项目信息

            if(fm.LoadFlag.value == "edorApp" || fm.LoadFlag.value == "scanApp" ||fm.LoadFlag.value=="CM")
            {
                //保全申请
                divappconfirm.style.display='';       //申请确认按钮
                divApproveModify.style.display="none";//复核修改确认按钮
                showDivGetPayForm();        //显示收付费方式信息
                divEdorTest.style.display="none";     //试算完毕按钮
            }
            if(fm.LoadFlag.value == "approveModify")
            {
                //保全复核修改
                divappconfirm.style.display="none";//申请确认按钮
                divApproveModify.style.display=''; //复核修改确认按钮
                showDivGetPayForm();        //显示收付费方式信息
                divEdorTest.style.display="none";  //试算完毕按钮
            }
            if(fm.LoadFlag.value == "edorTest")
            {
                //保全试算
                divappconfirm.style.display="none";    //申请确认按钮
                divApproveModify.style.display="none"; //复核修改确认按钮
                showDivGetPayForm();        //隐藏收付费方式信息
                divEdorTest.style.display='';          //试算完毕按钮
            }
        }
        if(fm.PEdorState.value == '5'){
        	//审批修改原因显示	
        	initApproveTrackGrid();
        	
           mySql=new SqlClass();
           mySql.setResourceName("bq.PEdorApp");
           mySql.setSqlId("PEdorAppSql3");
           mySql.addSubPara(fm.EdorAcceptNo.value);  
				
       		turnPageApprove.queryModal(mySql.getString(), ApproveTrackGrid); 
        	divApproveTrack.style.display = '';
        }

    }
    else  //尚未申请保存
    {
        //alert("尚未申请保存");
        hasSaved = "N";
        divApplySaveWrite.style.display="";     //保全受理信息可填
        divApplySaveRead.style.display="none";  //保全受理信息只读
        divApplySaveBT.style.display='';        //显示申请保存按钮
        divCustomer.style.display="none";       //客户信息
        divCont.style.display="none";           //保单信息
        divContState.style.display="none";      //保单状态信息
        divEdorItemInfo.style.display="none";   //保全项目信息
        divappconfirm.style.display="none";     //申请确认按钮
        divApproveModify.style.display="none";  //复核修改确认按钮
        divEdorTest.style.display="none";       //试算完毕按钮

        showDivGetPayForm();        //显示收付费方式信息

    }
}

/*********************************************************************
 *  查询客户详细信息
 *  描述: 查询客户详细信息
 *  参数  ：  CustomerNo
 *  返回值：  无
 *********************************************************************
 */
function displayCustomerInfo(CustomerNo)
{
    var strSQL;
    mySql=new SqlClass();
    /*
    strSQL =  " select CustomerNo, Name, Sex, Birthday, "
            + " IDType, IDNo, Marriage, Health, "
            + " RgtAddress, VIPValue, Salary, GrpName "
            + " from LDPerson "
            + " where customerno = '" + CustomerNo + "'";
*/
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql4");
    mySql.addSubPara(CustomerNo);  


    var brr = easyExecSql(mySql.getString());

    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.CustomerNo.value  = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.Name.value        = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.Sex.value         = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.Birthday.value    = brr[0][3];

        brr[0][4]==null||brr[0][4]=='null'?'0':fm.IDType.value      = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.IDNo.value        = brr[0][5];
        //brr[0][6]==null||brr[0][6]=='null'?'0':fm.Marriage.value    = brr[0][6];
        //brr[0][7]==null||brr[0][7]=='null'?'0':fm.Health.value      = brr[0][7];

        //brr[0][8]==null||brr[0][8]=='null'?'0':fm.RgtAddress.value  = brr[0][8];
        //brr[0][9]==null||brr[0][9]=='null'?'0':fm.VIPValue.value    = brr[0][9];
        //brr[0][10]==null||brr[0][10]=='null'?'0':fm.Salary.value    = brr[0][10];
        brr[0][11]==null||brr[0][11]=='null'?'0':fm.GrpName.value   = brr[0][11];
        showOneCodeName('sex','Sex','SexName');
        showOneCodeName('idtype','IDType','IDTypeName');
    }
    else
    {
        clearCustomerInfo();
    }
    clearContStateInfo();

    //查询客户相关保单列表
    var EdorAppDate = document.all('EdorAppDate').value;
    mySql=new SqlClass();

   /* strSQL =  " select 1 from lpedoritem where edoracceptno = '" + fm.EdorAcceptNo.value + "'" ;*/
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql5");
    mySql.addSubPara(fm.EdorAcceptNo.value);     
    
    var brr = easyExecSql(mySql.getString());
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    
    if ( brr )  //已经添加保全项目
    {
   /* strSQL =  " select   ContNo, AppntName, InsuredName, agentcode, cvalidate, customgetpoldate, "
            + " case appflag when '1' then '承保' when '2' then '未承保' when '4' then '终止' end , "
            + " case (select 1 from lpedoritem i where i.contno = c.contno and edoracceptno = '" + fm.EdorAcceptNo.value + "') when 1 then '参与变更' else '不参与变更' end  "
            ;
            */
        mySql.setSqlId("PEdorAppSql6");    
        mySql.addSubPara(fm.EdorAcceptNo.value);    
    }
    else  //尚未添加保全项目
    {
   /* strSQL =  " select   ContNo, AppntName, InsuredName, agentcode, cvalidate, customgetpoldate, "
            + " case appflag when '1' then '承保' when '2' then '未承保' when '4' then '终止' end , "
            + " '' "  //显示为空
            ;
    */        
         mySql.setSqlId("PEdorAppSql7");   
    }
    
    mySql.addSubPara(CustomerNo);    
    mySql.addSubPara(CustomerNo);
    mySql.addSubPara(EdorAppDate);
    mySql.addSubPara(EdorAppDate);
    mySql.addSubPara(EdorAppDate);
    mySql.addSubPara(EdorAppDate);
    mySql.addSubPara(EdorAppDate);
    mySql.addSubPara(EdorAppDate);

    /*
    strSQL=strSQL+" from lccont c "
				+ " where 1 = 1 "
				+ "and (c.GrpContNo is null or c.GrpContNo = '00000000000000000000') "
				+ "and c.contno in ( select contno from lcinsured where insuredno = '"
				+ CustomerNo
				+ "' "
				+ " union " // 以投保人或被保人身份相关的保单
				+ " select contno from lcappnt where appntno = '"
				+ CustomerNo
				+ "' ) "
				+ " and "
				+ " ( " // 保单未终止，并且主险未失效（失效保单不参与客户层变更）
				+ " ( appflag = '1' and not exists "
				+ " ( select 'X' from lccontstate s "
				+ " where trim(statetype) in ('Available') and trim(state) = '1' "
				+ " and ((startdate <= '"
				+ EdorAppDate
				+ "' and '"
				+ EdorAppDate
				+ "' <= enddate) or (startdate <= '"
				+ EdorAppDate
				+ "' and enddate is null))"
				+ " and contno = c.contno and trim(polno) = (select trim(p1.polno) from lcpol p1 where p1.contno = s.contno and p1.polno = p1.mainpolno and rownum = 1) "
				+ " ) "
				+ " ) "
				+ " or "
				+ " ( appflag = '4' and exists " // 01-满期终止、05-自垫终止、06-贷款终止 可以参与客户层变更
				+ " ( select 'X' from lccontstate s "
				+ " where trim(statetype) in ('Terminate') and trim(state) = '1' and trim(statereason) is not null and trim(statereason) in ('01', '05', '06', '09') "
				+ " and ((startdate <= '"
				+ EdorAppDate
				+ "' and '"
				+ EdorAppDate
				+ "' <= enddate) or (startdate <= '"
				+ EdorAppDate
				+ "' and enddate is null)) "
				+ " and contno = c.contno and trim(polno) = (select trim(p1.polno) from lcpol p1 where p1.contno = s.contno and p1.polno = p1.mainpolno and rownum = 1) "
				+ " ) " + " ) " + " ) ";
*/
    //XinYQ modified on 2007-04-05 : 可能出现小团单, 使用自动分页功能
    //prompt('',strSQL);
    try
    {
        turnPageCustomerContGrid.pageDivName = "divTurnPageCustomerContGrid";
        turnPageCustomerContGrid.queryModal(mySql.getString(), CustomerContGrid);
    }
    catch (ex)
    {
        alert("警告：查询查询客户关联保单信息出现异常！ ");
        return;
    }
}

//清空客户信息
function clearCustomerInfo()
{
        fm.CustomerNo.value   = "";
        fm.Name.value         = "";
        fm.Sex.value          = "";
        fm.Birthday.value     = "";
        fm.IDType.value       = "";
        fm.IDNo.value         = "";
        //fm.Marriage.value     = "";
        //fm.Health.value       = "";
        //fm.RgtAddress.value   = "";
        //fm.VIPValue.value     = "";
        //fm.Salary.value         = "";
        fm.GrpName.value      = "";
}

/*********************************************************************
 *  查询保单详细信息
 *  描述: 查询保单详细信息
 *  参数  ：  ContNo
 *  返回值：  无
 *********************************************************************
 */
function displayContInfo(ContNo)
{
    var strSQL;
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql8");
    mySql.addSubPara(ContNo); 
    /*
    strSQL =  " select ContNo, AppntName, InsuredName, Prem, Amnt, Mult, "
            + " AgentCode, GetPolDate, CValiDate, '' "
            + " from lccont "
            + " where ContNo = '" + ContNo + "'"
            + " and appflag in ( '1', '4') ";
  */
    var brr = easyExecSql(mySql.getString());
    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.ContNoApp.value   = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.AppntName.value   = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.InsuredName.value = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.Prem.value        = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.Amnt.value        = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.Mult.value        = brr[0][5];
        brr[0][6]==null||brr[0][6]=='null'?'0':fm.AgentCode.value   = brr[0][6];
        //brr[0][7]==null||brr[0][7]=='null'?'0':fm.GetPolDate.value    = brr[0][7];
        brr[0][8]==null||brr[0][8]=='null'?'0':fm.CValiDate.value   = brr[0][8];
        //brr[0][9]==null||brr[0][9]=='null'?'0':fm.PaytoDate.value   = brr[0][9];
    }
    else
    {
        clearContInfo();
        clearContStateInfo();
    }

    //查询保单状态详细信息
    displayContStateInfo(fm.OtherNo.value);

    //查询主险交费对应日
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql24");
    mySql.addSubPara(ContNo);     
    
    //strSQL =  " select PaytoDate from lcpol where polno = mainpolno and contno = '" + ContNo + "' ";
    var crr = easyExecSql(mySql.getString());
    if ( crr )
    {
        crr[0][0]==null||crr[0][0]=='null'?'0':fm.MainPolPaytoDate.value   = crr[0][0];
    }
    else
    {
        fm.MainPolPaytoDate.value = "";
    }

    //查询险种列表
    var EdorAppDate = document.all('EdorAppDate').value;
  /*  var strSQL = " select RiskCode," +
                 " (select RiskShortName from LMRisk where LMRisk.RiskCode = c.RiskCode)," +
                 " InsuredName, Amnt, mult ," +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "' and payenddate >= '" + EdorAppDate + "' and p.polno = c.polno and length(dutycode)=6 and p.payplantype in ('0')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "' and payenddate >= '" + EdorAppDate + "' and p.polno = c.polno and p.payplantype in ('01', '03')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "' and payenddate >= '" + EdorAppDate + "' and p.polno = c.polno and p.payplantype in ('02', '04')), 0), " +
                 " polno, paytodate " +
                 " from LCPol c where appflag = '1' and ContNo='" + ContNo + "'"
                 ;*/
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql28");
    
    mySql.addSubPara(EdorAppDate); 
    mySql.addSubPara(EdorAppDate);  
    mySql.addSubPara(EdorAppDate);     
    mySql.addSubPara(EdorAppDate);  
    mySql.addSubPara(EdorAppDate);  
    mySql.addSubPara(EdorAppDate);  
    mySql.addSubPara(ContNo);  
    
    brrResult = easyExecSql(mySql.getString());
    if (brrResult)
    {
        displayMultiline(brrResult,RiskGrid);
    }
}

function showContStateInfo()
{

    var tSelNo = CustomerContGrid.getSelNo()-1;

    var ContNo = CustomerContGrid.getRowColData(tSelNo, 1);
    if (ContNo == null || ContNo.trim() == "")
    {
        clearContStateInfo();
    }
    else
    {
        displayContStateInfo(ContNo);
    }
}

/*********************************************************************
 *  查询保单状态详细信息
 *  描述: 查询保单状态详细信息
 *  参数  ：  ContNo
 *  返回值：  无
 *********************************************************************
 */
function displayContStateInfo(ContNo)
{
    var tState = "";
    var tDate = fm.EdorAppDate.value;
    var PolNo = getMainPol(ContNo);
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql27");
    mySql.addSubPara(tDate);     
    mySql.addSubPara(tDate);
    mySql.addSubPara(ContNo);
    mySql.addSubPara(PolNo);
    /*
    var strsql = "select trim(statetype), statereason from lccontstate where state = '1' "
                   + " and startdate <= '"+tDate+"' and (enddate is null or enddate >= '"
                   + tDate +"') and contno = '"+ContNo+"' and polno in('"+PolNo+"','000000') ";
     */              
    var brr = easyExecSql(mySql.getString());
    fm.Available.value = isInArray(brr,"Available") ? "失效" : "有效";
    fm.Lost.value      = isInArray(brr,"Lost") ? "挂失" : "未挂失";
    fm.PayPrem.value   = isInArray(brr,"PayPrem") ? "自垫" : "未自垫";
    fm.Loan.value      = isInArray(brr,"Loan") ? "贷款" : "未贷款";
    fm.BankLoan.value  = isInArray(brr,"BankLoan") ? "质押银行贷款" : "未质押银行贷款";
    fm.RPU.value       = isInArray(brr,"RPU") ? "减额缴清" : "未减额缴清";
    fm.Terminate.value = isInArray(brr,"Terminate") ? "终止" : "未终止";
    fm.Password.value  = isInArray(brr,"Password") ? "挂失" : "未挂失";
    if (isInArray(brr,"Terminate"))
    {
        //显示终止原因
        var TerminateReason = getStateReasonCN(getStateReason(brr,"Terminate"));
        fm.Terminate.value = TerminateReason;
    }
    else
    {
    	  mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorApp");
        mySql.setSqlId("PEdorAppSql11");
        mySql.addSubPara(ContNo); 
    	
        /*var strsql = " select appflag from lccont where contno = '"+ContNo+"'";*/
        var crr = easyExecSql(mySql.getString());
        if ( crr )
        {
            var appflag;
            crr[0][0]==null||crr[0][0]=='null' ? '0' : appflag = crr[0][0];
            if (appflag == "4")
            {
                fm.Terminate.value = "终止";
            }
        }
    }

    //客户层保全显示保单状态
    fm.Available_C.value = fm.Available.value;
    fm.Lost_C.value      = fm.Lost.value     ;
    fm.PayPrem_C.value   = fm.PayPrem.value  ;
    fm.Loan_C.value      = fm.Loan.value     ;
    fm.BankLoan_C.value  = fm.BankLoan.value ;
    fm.RPU_C.value       = fm.RPU.value      ;
    fm.Terminate_C.value = fm.Terminate.value;
    fm.Password_C.value  = fm.Password.value;
}

//判断字符串StateType是否存在于二维数组brr第一列中，存在则返回true
function isInArray(brr,StateType)
{
    if(typeof(brr) != "object" || !brr || StateType == null || trim(StateType) == "")
    {
        return false;
    }
    var i=0;
    for(i=0;i<brr.length;i++)
    {
        if(trim(StateType) == trim(brr[i][0]))
        {
            return true;
        }
    }
    return false;
}

function getStateReason(brr,StateType)
{
    if(typeof(brr) != "object" || !brr || StateType == null || trim(StateType) == "")
    {
        return false;
    }
    var i=0;
    for(i=0;i<brr.length;i++)
    {
        if(trim(StateType) == trim(brr[i][0]))
        {
            return brr[i][1];
        }
    }
    return "";
}

/**
 * 转换保单终止的原因代码为汉字显示
 * XinYQ modified on 2006-11-02
 */
function getStateReasonCN(sStateReason)
{
    var sStateReasonCN;
    if (sStateReason != null && sStateReason.trim() != "")
    {
        var QuerySQL, arrResult;
        mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorApp");
        mySql.setSqlId("PEdorAppSql10");
        mySql.addSubPara(sStateReason); 
        /*
        QuerySQL = "select CodeName "
                 +   "from LDCode "
                 +  "where 1 = 1 "
                 +    "and CodeType = 'contterminatereason' "
                 +    "and Code = '" + sStateReason + "'";
        //alert(QuerySQL);
        */
        try
        {
            arrResult = easyExecSql(mySql.getString(), 1, 0);
        }
        catch (ex)
        {
            alert("警告：查询保单终止的原因描述信息出现异常！ ");
            return null;
        }
        if (arrResult != null)
        {
            sStateReasonCN = arrResult[0][0];
        }
    }
    sStateReasonCN = NullToEmpty(sStateReasonCN);
    return sStateReasonCN;
}

//状态判断函数；未被调用，暂留
function isOnState(ContNo,tPolNo,StateType,tDate)
{
    var strsql = "";
    if(tPolNo == "")
    {
//        strsql = "select state from lccontstate where statetype = '"+StateType+"' "
//                   + " and state = '1' and startdate <= '"+tDate+"' and (enddate is null or enddate >= '"+tDate+"') and contno = '"+ContNo+"'";
        var sqlid1="PEdorAppSql51";
        var mySql1=new SqlClass();
        mySql1.setResourceName("bq.PEdorApp"); //指定使用的properties文件名
        mySql1.setSqlId(sqlid1);//指定使用的Sql的id
        mySql1.addSubPara(StateType);//指定传入的参数
        mySql1.addSubPara(tDate);//指定传入的参数
        mySql1.addSubPara(tDate);//指定传入的参数
        mySql1.addSubPara(ContNo);//指定传入的参数
        strsql=mySql1.getString();
    }
    else
    {
//        strsql = "select state from lccontstate where statetype = '"+StateType+"' "
//                   + " and state = '1' and startdate <= '"+tDate+"' and (enddate is null or enddate >= '"+tDate+"') and contno = '"+ContNo+"' and polno = '"+tPolNo+"'";
        var sqlid2="PEdorAppSql52";
        var mySql2=new SqlClass();
        mySql2.setResourceName("bq.PEdorApp"); //指定使用的properties文件名
        mySql2.setSqlId(sqlid2);//指定使用的Sql的id
        mySql2.addSubPara(StateType);//指定传入的参数
        mySql2.addSubPara(tDate);//指定传入的参数
        mySql2.addSubPara(tDate);//指定传入的参数
        mySql2.addSubPara(ContNo);//指定传入的参数
        mySql2.addSubPara(tPolNo);//指定传入的参数
        strsql=mySql2.getString();
    }
    
    
    
    var brr = easyExecSql(strsql);
    if(brr)
    {
        return "1";
    }
    return "0";
}

//得到保单的主险(对于多主险的情况？)
function getMainPol(ContNo)
{
    var PolNo = "";
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql23");
    mySql.addSubPara(ContNo);     
    
    //var strsql = "select trim(polno) from lcpol where contno = '"+ContNo+"' and polno = mainpolno";
    var brr = easyExecSql(mySql.getString());
    if(brr)
    {
        PolNo = brr[0][0];
    }
    return PolNo;
}

//清空客户信息
function clearContInfo()
{
    fm.ContNoApp.value   = "";
    fm.AppntName.value   = "";
    fm.InsuredName.value = "";
    fm.Prem.value        = "";
    fm.Amnt.value        = "";
    fm.AgentCode.value   = "";
    fm.CValiDate.value   = "";
}

//清空保单状态信息
function clearContStateInfo()
{
    var sOtherNoType;
    try
    {
        sOtherNoType = document.getElementsByName("OtherNoType")[0].value;
    }
    catch (ex) {}
    if (sOtherNoType != null && sOtherNoType.trim() == "1")
    {
        fm.Available.value   = "";
        fm.Terminate.value   = "";
        fm.Lost.value = "";
        fm.PayPrem.value     = "";
        fm.Loan.value        = "";
        fm.BankLoan.value    = "";
        fm.RPU.value         = "";
        fm.Password.value    = "";
        fm.Available_C.value = "";
        fm.Terminate_C.value = "";
        fm.Lost_C.value      = "";
        fm.PayPrem_C.value   = "";
        fm.Loan_C.value      = "";
        fm.BankLoan_C.value  = "";
        fm.RPU_C.value       = "";
        fm.Password_C.value  = "";
    }
}

function initEdorType(cObjCode,cObjName)
{
    var tOtherNo = document.all('OtherNo').value;
    var tOtherNoType = document.all('OtherNoType').value;
    var tLoadFlag = fm.LoadFlag.value;

    if (tOtherNoType=='3')//个人保单号申请
    {
        mEdorType = " 1 and AppObj=#I#" ;
        mEdorType=mEdorType+" and a.edorcode=b.edorcode";
        mEdorType=mEdorType+" and riskcode in (select riskcode from lcpol where ContNo=#"+tOtherNo+"# ";
        mEdorType=mEdorType+"  union select #000000# from dual ) ";
        if(tLoadFlag != "edorTest")
        {
            mEdorType=mEdorType+" and (EdorPopedom is not null and trim(EdorPopedom) <= #"+userEdorPopedom+"# )"; // and  EdorPopedom<=#@#
        }
    }
    else if (tOtherNoType=='1')//个人客户号申请
    {
        mEdorType = " 1 and AppObj=#B#" ;
        mEdorType=mEdorType+" and a.edorcode=b.edorcode";
        mEdorType=mEdorType+" and riskcode in (select riskcode from lcpol where AppntNo=#"+tOtherNo+"# ";
        mEdorType=mEdorType+"  union select riskcode from lcpol where insuredno=#"+tOtherNo+"# ";
        mEdorType=mEdorType+"  union select #000000# from dual ) ";
        if(tLoadFlag != "edorTest")
        {
            mEdorType=mEdorType+" and (EdorPopedom is not null and trim(EdorPopedom) <= #"+userEdorPopedom+"# )"; // and  EdorPopedom<=#@#
        }
    }
    //prompt("mEdorType",mEdorType);
    return showCodeList('EdorCode', [cObjCode,cObjName], [0,1], null, mEdorType, "1", 0, 207);
}

function actionKeyUp(cObjCode,cObjName)
{
    var tOtherNo = document.all('OtherNo').value;
    var tOtherNoType = document.all('OtherNoType').value;
    var tLoadFlag = fm.LoadFlag.value;

    if (tOtherNoType=='3')//个人保单号申请
    {
        mEdorType = " 1 and AppObj=#I#" ;
        mEdorType=mEdorType+" and a.edorcode=b.edorcode";
        mEdorType=mEdorType+" and riskcode in (select riskcode from lcpol where ContNo=#"+tOtherNo+"# ";
        mEdorType=mEdorType+"  union select #000000# from dual ) ";
        if(tLoadFlag != "edorTest")
        {
            mEdorType=mEdorType+" and (EdorPopedom is not null and trim(EdorPopedom) <= #"+userEdorPopedom+"# )"; // and  EdorPopedom<=#@#
        }
    }
    else if (tOtherNoType=='1')//个人客户号申请
    {
        mEdorType = " 1 and AppObj=#B#" ;
        mEdorType=mEdorType+" and a.edorcode=b.edorcode";
        mEdorType=mEdorType+" and riskcode in (select riskcode from lcpol where AppntNo=#"+tOtherNo+"# ";
        mEdorType=mEdorType+"  union select riskcode from lcpol where insuredno=#"+tOtherNo+"# ";
        mEdorType=mEdorType+"  union select #000000# from dual ) ";
        if(tLoadFlag != "edorTest")
        {
            mEdorType=mEdorType+" and (EdorPopedom is not null and trim(EdorPopedom) <= #"+userEdorPopedom+"# )"; // and  EdorPopedom<=#@#
        }
    }
    return showCodeListKey('EdorCode', [cObjCode,cObjName], [0,1], null, mEdorType, "1", 0, 207);
}

function QueryAgent() {
	if (document.all("BfAgentCode").value !=null && document.all("BfAgentCode").value != "") {
		//var QuerySQL = "select Name,idtype,(select codename from ldcode where codetype = lower('IDType') and code=a.idtype),idno,nvl(mobile,phone),a.managecom,(select name from ldcom where comcode=a.managecom) from laagent a where agentstate<='02' and agentcode = '" + document.all("BfAgentCode").value + "'";
//		var QuerySQL = "select Name,idtype,(select codename from ldcode where codetype = lower('IDType') and code=a.idtype),idno,a.Phone,a.managecom,(select name from ldcom where comcode=a.managecom) from laagent a where agentstate<='02' and agentcode = '" + document.all("BfAgentCode").value + "'";
		 var sqlid1="PEdorAppSql53";
	        var mySql1=new SqlClass();
	        mySql1.setResourceName("bq.PEdorApp"); //指定使用的properties文件名
	        mySql1.setSqlId(sqlid1);//指定使用的Sql的id
	        mySql1.addSubPara(document.all("BfAgentCode").value);//指定传入的参数
	        strsql=mySql1.getString();
		var arrResult = easyExecSql(QuerySQL, 1, 0);
		//prompt("QuerySQL",QuerySQL);
		//alert("arrResult"+arrResult);
		if (arrResult == null) {
			alert("代理人信息录入错误，或是已经离职");
			document.all("BfAgentCode").value = "";
			document.all("BfName").value = "";
		    document.all("BfIDType").value = "";
		    document.all("BfIDTypeName").value = "";
		    document.all("BfIDNo").value = "";
		    document.all("BfPhone").value = "";
		    document.all("ManageCom").value = "";
		    document.all("ManageComName").value = "";
			return;
		}
		document.all("BfName").value = arrResult[0][0];
		document.all("BfIDType").value = arrResult[0][1];
		document.all("BfIDTypeName").value = arrResult[0][2];
		document.all("BfIDNo").value = arrResult[0][3];
		document.all("BfPhone").value = arrResult[0][4];
		document.all("ManageCom").value = arrResult[0][5];
		document.all("ManageComName").value = arrResult[0][6];
		return;
	}
	//同时清空其他的信息
	document.all("BfName").value = "";
	document.all("BfIDType").value = "";
	document.all("BfIDTypeName").value = "";
	document.all("BfIDNo").value = "";
	document.all("BfPhone").value = "";
    document.all("ManageCom").value = "";
	document.all("ManageComName").value = "";
}
//<!-- XinYQ added on 2005-11-30 : 保全申请人下拉列表和保单明细查询 : BGN -->
/*============================================================================*/

/**
 * 双击申请人姓名时根据号码和类型生成下拉列表
 */
function getEdorAppList()
{
    var sOtherNo, sOtherNoType;
    try
    {
        sOtherNo = document.getElementsByName("OtherNo")[0].value;
        sOtherNoType = document.getElementsByName("OtherNoType")[0].value;
    } catch (ex) {}
    if (sOtherNo == null || trim(sOtherNo) == "" || sOtherNoType == null || trim(sOtherNoType) == "")
    {
        alert("提示：请先录入客户/保单号并选择号码类型！");
        return;
    }
    //if OtherNo and OtherNoType both has data, go next!
    try
    {
        document.getElementsByName("EdorApp")[0].value = "";
        document.getElementsByName("EdorApp")[0].CodeData = "";
        document.getElementsByName("EdorAppName")[0].value = "";
    } catch (ex) {}
    var sCodeData = "";    //返回给 EdorApp 的 CodeData
    var QuerySQL, arrResult;
    //根据选择的不同号码类型产生不同的 SQL 查询
    switch (sOtherNoType)
    {
        case "1":    //个人客户号
            mySql=new SqlClass();
            mySql.setResourceName("bq.PEdorApp");
            mySql.setSqlId("PEdorAppSql29");
            mySql.addSubPara(trim(sOtherNo));   
                     
           /// QuerySQL = "select Name from LDPerson where CustomerNo = '" + trim(sOtherNo) + "'";
            try
            {
                arrResult = easyExecSql(mySql.getString(), 1, 0);
            }
            catch (ex)
            {
                alert("警告：按个人客户号查询客户个人信息出现异常！ ");
                return;
            }
            if (arrResult != null)
                sCodeData = "0|^客户|" + arrResult[0][0];
            else
                alert("提示：没有找到号码为 " + trim(sOtherNo) + " 的客户！ ");
            break;
        case "3":    //个人保单号
            mySql=new SqlClass();
            mySql.setResourceName("bq.PEdorApp");
            mySql.setSqlId("PEdorAppSql30");
            mySql.addSubPara(trim(sOtherNo));         
            //QuerySQL = "select AppntName, InsuredName from LCCont where ContNo = '" + trim(sOtherNo) + "'";
            try
            {
                arrResult = easyExecSql(mySql.getString(), 1, 0);
            }
            catch (ex)
            {
                alert("警告：按个人保单号查询客户合同信息出现异常！ ");
                return;
            }
            if (arrResult != null)
                sCodeData = "0|^投保人|" + arrResult[0][0] + "^被保人|" + arrResult[0][1];
            else
                alert("提示：没有找到号码为 " + trim(sOtherNo) + " 的保单！ ");
            break;
        default:
            alert("错误：未知的号码类型！ ");
            return;
    }
    sCodeData = sCodeData + "^其他|"
    try { document.getElementsByName("EdorApp")[0].setAttribute('CodeData' , sCodeData); } catch (ex) {}
}

/*============================================================================*/

/**
 * 查询保单明细
 */
function viewPolDetail()
{
    var sOtherNo, sOtherNoType;
    try { sOtherNoType = document.getElementsByName("OtherNoType")[0].value; } catch (ex) {}
    if (sOtherNoType == null || trim(sOtherNoType) == "")
    {
        alert("警告：无法获取保单申请号码类型。查询保单明细失败！ ");
        return;
    }
    switch (trim(sOtherNoType))
    {
        case "1":
            //个人客户号
            var nSelNo = CustomerContGrid.getSelNo() - 1;
            if (nSelNo < 0)
            {
                alert("请先选择您要查看的客户关联保单信息！ ");
                return;
            }
            else
            {
                try { sOtherNo = CustomerContGrid.getRowColData(nSelNo, 1); } catch (ex) {}
            }
            break;
        case "3":
            //个人保单号
            try { sOtherNo = document.getElementsByName("ContNoApp")[0].value; } catch (ex) {}
            break;
        default:
            alert("错误：未知的保单申请号码类型！ ");
            return;
    }
    if (sOtherNo == null || trim(sOtherNo) == "")
    {
        alert("警告：无法获取保单申请号码。查询保单明细失败！ ");
        return;
    }
    //弹出页面显示保单明细
    var sOpenWinURL = "../sys/PolDetailQueryMain.jsp";
    var sParameters = "ContNo=" + sOtherNo + "&IsCancelPolFlag=0";
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "保全保单明细查询", "left");
}

/*============================================================================*/
//<!-- XinYQ added on 2005-11-30 : 保全申请人下拉列表和保单明细查询 : END -->


/*********************************************************************
 *  选择批改项目后的动作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect(cCodeName, Field)
{
    try {
        if( cCodeName == "EdorCode" )
        {
            var tOtherNoType = document.all('OtherNoType').value;
            var tAppObj;
            if (tOtherNoType=='3')//个人保单号申请
            {
                tAppObj = "I"
            }
            if (tOtherNoType=='1')//个人客户号申请
            {
                tAppObj = "B"
            }
            //XinYQ  modified on 2006-05-15
          /*  var strsql = "select DisplayFlag "
                       +   "from LMEdorItem "
                       +  "where 1 = 1 "
                       +    "and EdorCode = '" + Field.value + "' "
                       +    "and AppObj = '" + tAppObj + "'";
          */
           mySql=new SqlClass();
           mySql.setResourceName("bq.PEdorApp");
           mySql.setSqlId("PEdorAppSql20");
           mySql.addSubPara(Field.value);                       
           mySql.addSubPara(tAppObj); 
                      
            var tDisplayType = easyExecSql(mySql.getString());

            if (tDisplayType == null || tDisplayType == '')
            {
                alert("未查到该保全项目显示级别！");
                return;
            }
            if (tOtherNoType=='1')//个人客户号申请
            {
                tDisplayType = "1"
            }
            document.all("DisplayType").value = tDisplayType;

            if (tDisplayType=='1')  //只显示保单
            {
                document.all("divPolInfo").style.display = "none";
                document.all("divPolGrid").style.display = "none";
                document.all("divInsuredInfo").style.display = "none";
                document.all("divInsuredGrid").style.display = "none";
            }
            else if (tDisplayType=='2')  //需要显示客户
            {
                getInsuredGrid(document.all('OtherNo').value, Field.value);
                document.all("divPolInfo").style.display = "none";
                document.all("divPolGrid").style.display = "none";
                document.all("divInsuredInfo").style.display = "";
                document.all("divInsuredGrid").style.display = "";
            }
            else if (tDisplayType=='3')  //需要显示险种
            {
                getPolGrid(document.all('OtherNo').value);
                document.all("divPolInfo").style.display = "";
                document.all("divPolGrid").style.display = "";
                document.all("divInsuredInfo").style.display = "none";
                document.all("divInsuredGrid").style.display = "none";
            }
        }
/***************************************************
        if (cCodeName == "EdorCode")
        {
            //根据不同的保全项目设置保全生效日期
            var edorType = Field.value;
            if (edorType == "AC" || edorType == "BB" ||
                edorType == "BC" || edorType == "IA" ||
                edorType == "CC")
            {
                document.all('EdorValiDate').value = document.all('dayAfterCurrent').value;
            }
            else if (edorType == "WT" || edorType == "ZT" || edorType == "GT")
            {
                document.all('EdorValiDate').value = document.all('currentDay').value;
            }
            else
            {
                document.all('EdorValiDate').value = document.all('currentDay').value;
            }

        }
***************************************************/
    }
    catch( ex )
    {
        alert(ex);
    }

    //<!-- XinYQ added on 2005-11-30 : 保全申请人下拉列表 : BGN -->
    if (cCodeName == "nothis")
    {
        var sEdorApp;
        try
        {
            sEdorApp = document.getElementsByName("EdorApp")[0].value ;
        }
        catch (ex) {}
        if (sEdorApp == null || trim(sEdorApp) == "") return;
        try
        {
            if (trim(sEdorApp) == "其他")
                document.getElementsByName("EdorAppName")[0].readOnly = false;
            else
                document.getElementsByName("EdorAppName")[0].readOnly = true;
        } catch (ex) {}
    }
    //<!-- XinYQ added on 2005-11-30 : 保全申请人下拉列表 : END -->
    //add by jiaqiangli 2009-01-04 代办人信息
    if (cCodeName == "edorapptype") {
    	if (Field.value == '2') {
    		document.all("divBehalfAgentCodeInfo").style.display = "";
    		document.all("divBehalfInfo").style.display = "";
    		document.all("divInternalSwitchInfo").style.display = "none";
    		
    		document.getElementsByName("BfName")[0].readOnly = true;
    	    //document.getElementsByName("BfIDType")[0].className = "input";
    	    //document.getElementsByName("BfIDType")[0].readOnly = true;
    	    document.getElementsByName("ManageCom")[0].ondblclick = null;
    	    document.getElementsByName("ManageCom")[0].onkeyup = null;
    	    document.getElementsByName("ManageCom")[0].readOnly = true;
    	    
    	    document.getElementsByName("BfIDType")[0].ondblclick = null;
    	    document.getElementsByName("BfIDType")[0].onkeyup = null;
    	    document.getElementsByName("BfIDType")[0].readOnly = true;
    	    
    	    document.getElementsByName("BfIDNo")[0].readOnly = true;
    	    
    		clearHiddenDiv();
    	}
    	else if (Field.value == '3') {
    	    document.all("divBehalfAgentCodeInfo").style.display = "none";
    		document.all("divBehalfInfo").style.display = "";
    		document.all("divInternalSwitchInfo").style.display = "none";
    		
    		document.getElementsByName("BfName")[0].readOnly = false;
    	    //document.getElementsByName("BfIDType")[0].className = "codeno";
    	    //document.getElementsByName("BfIDType")[0].readOnly = false;
    	    //document.getElementsByName("BfIDType")[0].ondblclick = null;
    	    //document.getElementsByName("BfIDType")[0].onkeyup = null;
    	    //document.getElementsByName("BfIDType")[0].readOnly = false;
    	    //alert("ccccc");
    	    //document.getElementsByName("BfIDType")[0].ondblclick = Function("testAlert('456')");
    	    //document.getElementsByName("BfIDType")[0].onkeyup = Function("testAlert('789')");
    	    //document.getElementsByName("BfIDType")[0].ondblclick = "testAlert";
    	    //document.getElementsByName("BfIDType")[0].ondblclick = Function("return showCodeList('IDType',[this,BfIDTypeName],[0,1],null,null,null,1);");
    	    //document.getElementsByName("BfIDType")[0].ondblclick = Function("return testAlert()");
    	    //document.getElementsByName("BfIDType")[0].onkeyup = Function("return testAlertB()");
    	    //add by jiaqiangli 2009-02-20 此处的js动态添加DOM事件的解决办法
    	    //至于说需要showOneCodeNameEx 这个不够优雅 估计是showCodeList没有执行到就已经return了
    	    document.getElementsByName("BfIDType")[0].ondblclick = Function("return showCodeList('IDType',document.getElementsByName('BfIDType'),null,null,null,null,1);");
    	    document.getElementsByName("BfIDType")[0].onkeyup = Function("return showCodeListKey('IDType',document.getElementsByName('BfIDType'),null,null,null,null,1);");
    	    //document.getElementsByName("BfIDType")[0].onkeyup = Function("return showCodeListKey('station',[this,ManageComName],[0,1]);");
    	    //alert("dddd");
    	    
    	    document.getElementsByName("BfIDNo")[0].readOnly = false;
    	    
    		clearHiddenDiv();
    	}
    	else if (Field.value == '6') {
    	    document.all("divBehalfAgentCodeInfo").style.display = "none";
    		document.all("divBehalfInfo").style.display = "none";
    		document.all("divInternalSwitchInfo").style.display = "";
    		clearHiddenDiv();
    	}
    	else {
    		document.all("divBehalfAgentCodeInfo").style.display = "none";
    		document.all("divBehalfInfo").style.display = "none";
    		document.all("divInternalSwitchInfo").style.display = "none";
    		clearHiddenDiv();
    	}
    }
    //add by jiaqiangli 2009-01-04 代办人信息
    if (cCodeName == "InternalSwitchChnl") {
    	    //deal other - OO
    		var sInternalSwitch;
            try
            {
               sInternalSwitch = document.getElementsByName("InternalSwitchChnl")[0].value ;
            }
            catch (ex) {}
            if (sInternalSwitch == null || trim(sInternalSwitch) == "") return;
            try
            {
                if (trim(sInternalSwitch) == "OO") {
                   document.getElementsByName("InternalSwitchChnlName")[0].value = "";
                   document.getElementsByName("InternalSwitchChnlName")[0].readOnly = false;
                }
                else {
                   document.getElementsByName("InternalSwitchChnlName")[0].readOnly = true;
                }
             } catch (ex) {}
    }
    if (cCodeName == "IDType") {
    	showOneCodeNameEx('IDType', 'BfIDType', 'BfIDTypeName');
    }
    if(cCodeName == "edorgetpayform"){
      //银行代码
      //alert(fm.GetPayForm.value);
      document.all("BankCode").CodeData="";
      //alert(document.all('BankCode').CodeData);
        if(fm.GetPayForm.value=="9"){
          getcodedata2("finabank");
        }else{
          getcodedata2("bank");
        }
    }
}
function getcodedata2(codetype)
{
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    
    

  if(codetype =="bank"){
    //sql ="select distinct bankcode,bankname from ldbank a where 1=1 and comcode like (select substr(managecom,0,4) from lpedorapp a where a.edoracceptno='"+document.all('EdorAcceptNo').value+"')||'%'  order by bankcode ";
    mySql.setSqlId("PEdorAppSql22");
  }
  if(codetype =="finabank"){
   // sql="select Code, CodeName, CodeAlias, ComCode, OtherSign from ldcode where 1=1"
   //         +" and codetype = 'bank' and comcode like (select substr(managecom,0,4) from lpedorapp a where a.edoracceptno='"+document.all('EdorAcceptNo').value+"')||'%'  order by Code";
    mySql.setSqlId("PEdorAppSql23");
  }
  mySql.addSubPara(document.all('EdorAcceptNo').value); 
  
	var tCodeData = "0|";
	turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);
	

    if (turnPage.strQueryResult != "")
    {

    	
    	arrRecord = turnPage.strQueryResult.split("^");

    	recordNum = arrRecord.length;
    	var i=0;
    	  while(i<recordNum)
          {
              arrField = arrRecord[i].split("|");  //拆分记录，得到字段数组
              fieldNum = arrField.length;
              arrEasyQuery[i] = new Array();

              var j=0;
              while(j<fieldNum)
              {
                  arrEasyQuery[i][j] = arrField[j];   //形成以行为记录，列为字段的二维数组
                  j++;
              }
              i++;
          }

    	turnPage.arrDataCacheSet = arrEasyQuery;//decodeEasyQueryResult(turnPage.strQueryResult);
    	m = turnPage.arrDataCacheSet.length;

    	for (l = 1; l < m; l++)
    	{
    		k = l + 1;
    		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[l][0] + "|" + turnPage.arrDataCacheSet[l][1];
    	}
    }

    document.all("BankCode").CodeData=tCodeData;
	
}

function testAlert() {
	//alert("cc");
	//showCodeList('IDType',[this,BfIDTypeName],[0,1],null,null,null,1);getElementById
	//showCodeList('IDType',[document.getElementsByName("BfIDType"),document.getElementsByName("BfIDTypeName")],[0,1],null,null,null,1);
	//alert("44");
	showCodeList('IDType',document.getElementsByName("BfIDType"),null,null,null,null,1);
	//showCodeList('IDType',['BfIDType','BfIDTypeName'],[0,1],null,null,null,1);
	//showOneCodeNameEx('IDType', 'BfIDType', 'BfIDTypeName'); 
	//alert("dd");
}

function testAlertB() {
	//alert("cc");
	//showCodeList('IDType',[this,BfIDTypeName],[0,1],null,null,null,1);
	showCodeListKey('IDType',document.getElementsByName("BfIDType"),null,null,null,null,1);
	//showOneCodeNameEx('IDType', 'BfIDType', 'BfIDTypeName',null,null);
	//alert("dd");
}

function clearHiddenDiv() {
	fm.BfAgentCode.value = "";
	fm.ManageCom.value = "";
	fm.ManageComName.value = "";
	fm.BfName.value = "";
	fm.BfIDType.value = "";
	fm.BfIDTypeName.value = "";
	fm.BfIDNo.value = "";
	fm.BfPhone.value = "";
	fm.InternalSwitchChnl.value = "";
	fm.InternalSwitchChnlName.value = "";
}

/*********************************************************************
 *  查询被保险人列表
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getInsuredGrid(tContNo, tEdorType)
{
//    var strSQL =
//    " select * from ( " +
//    "  select distinct '被保人' role, a.insuredno no, a.Name name, (concat(concat(a.Sex , '-') , (select CodeName from LDCode where 1 = 1 and CodeType = 'sex' and Code = a.Sex))) sex, " +
//    "     a.Birthday birthday, (concat(concat(a.IDType , '-') , (select CodeName from LDCode where 1 = 1 and CodeType = 'idtype' and Code = a.IDType))) idtype, a.IdNo idno, a.contno contno, a.grpcontno grpcontno, 1 type" +
//    "  from lcinsured a where a.ContNo = '" + tContNo + "' " +
//    "  union " +
//    "  select distinct '投保人' role, b.appntno no, b.appntname name, (concat(concat(b.AppntSex , '-' ), (select CodeName from LDCode where 1 = 1 and CodeType = 'sex' and Code = b.AppntSex))) sex, " +
//    "     b.appntbirthday birthday, (concat(concat(b.IDType , '-' ), (select CodeName from LDCode where 1 = 1 and CodeType = 'idtype' and Code = b.IDType))) idtype, b.IdNo idno, b.contno contno, b.grpcontno grpcontno, 2 type" +
//    "  from lcappnt b where b.ContNo = '" + tContNo + "' " +
//    " ) t ";

    var AppntIsInsuredFlag;
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql43");
    mySql.addSubPara(tContNo);      
    mySql.addSubPara(tContNo);
    
   // var sql = " select 1 from lcinsured where contno = '" + tContNo + "' and insuredno = (select appntno from lccont where contno = '" + tContNo + "')";
    var mrr = easyExecSql(mySql.getString());
    if ( mrr )
    {
        AppntIsInsuredFlag = true;
    }
    else
    {
        AppntIsInsuredFlag = false;
    }
    
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    
    
    if (AppntIsInsuredFlag)
    {
        //如果投保人为被保人，只显示被保人
        mySql.setSqlId("PEdorAppSql44");
        
       // strSQL += " where type=1 ";
    }
    else
    {
    	  mySql.setSqlId("PEdorAppSql45");
        //strSQL += " where type in ( '1','2') ";  
    }
    mySql.addSubPara(tContNo);
    mySql.addSubPara(tContNo);
    //strSQL += " order by type, no asc "; //被保人显示最先

    turnPage.queryModal(mySql.getString(), InsuredGrid);
 //   if (InsuredGrid.mulLineCount > 0)
 //   {
  //      InsuredGrid.checkBoxSel(1);
  //  }

}
/*********************************************************************
 *  查询险种列表
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getPolGrid(tContNo)
{
    var AppDate = document.all('EdorItemAppDate').value;
    if (AppDate == null || AppDate =="")
    {
        AppDate = document.all('EdorAppDate').value;
    }
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql26");
    mySql.addSubPara(document.all('EdorType').value);  
    mySql.addSubPara(AppDate); 
    mySql.addSubPara(AppDate); 
    mySql.addSubPara(AppDate); 
    mySql.addSubPara(tContNo);
    /*
    var strSQL = "select * from ("
               + " select RiskCode, PolNo, InsuredNo, InsuredName,"
               + " amnt, prem, "
               + " (case (select distinct 1 from lcpol p1 where p1.contno = p.contno and p1.proposalno = p.proposalno and p1.polno <> p.polno )  when 1 then (select min(cvalidate) from lcpol p2 where p2.contno = p.contno and p2.proposalno = p.proposalno and p2.polno <> p.polno ) else p.cvalidate end),"
               + " PayToDate, ContNo, GrpContNo "
               + " from LCPol p where 1=1 "
               + " and riskcode in (select riskcode from lmriskedoritem where edorcode = '" + document.all('EdorType').value + "')"
               + " and (select count(*) from lccontstate s where trim(statetype) in('Terminate') and trim(state) = '1' and ((startdate <= '" + AppDate + "' and '" + AppDate + "' <= enddate )or(startdate <= '" + AppDate + "' and enddate is null ))and s.polno = p.polno) = 0"
               + " and appflag = '1' and ContNo='" + tContNo + "'"
               +" ) order by PolNo asc, RiskCode asc";
     */          
               //除去终止的保单
               //+ " union "
               //+ " select RiskCode, PolNo, InsuredNo, InsuredName,"
               //+ " amnt, prem, "
               //+ " (case (select distinct 1 from lcpol p1 where p1.contno = p.contno and p1.proposalno = p.proposalno and p1.polno <> p.polno )  when 1 then (select min(cvalidate) from lcpol p2 where p2.contno = p.contno and p2.proposalno = p.proposalno and p2.polno <> p.polno ) else p.cvalidate end),"
               //+ " PayToDate, ContNo, GrpContNo "
               //+ " from LCPol p where 1=1 "
               //+ " and riskcode in (select riskcode from lmriskedoritem where edorcode = '" + document.all('EdorType').value + "')"
               //+ " and (select count(*) from lccontstate s where trim(statetype) in('Terminate') and trim(state) = '1' and trim(StateReason) in ('01','04', '09') and ((startdate <= '" + AppDate + "' and '" + AppDate + "' <= enddate )or(startdate <= '" + AppDate + "' and enddate is null ))and s.polno = p.polno) > 0"
               //+ " and polno = mainpolno and appflag = '4' and contno = '" + tContNo + "'"
               //+ "
 

    //去处已经失效的保单
    //附加险自动续保后会有多条记录，只能显示一条，并且生效日期为最早，交至日期为最晚
    turnPage.queryModal(mySql.getString(), PolGrid);
    if (PolGrid.mulLineCount > 0)
    {
        PolGrid.checkBoxSel(1);
    }

}

/*********************************************************************
 *  添加保全项目
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function addEdorItem()
{
    if (document.all('EdorAcceptNo').value==null || document.all('EdorAcceptNo').value=="")
    {
        alert("请先保存保全受理，再添加保全项目!");
        return false;
    }
    if (document.all('EdorType').value==null || document.all('EdorType').value=="")
    {
        alert("请选择需要添加的保全项目!");
        return false;
    }
    if (document.all('DisplayType').value == null || document.all('DisplayType').value == "" )
    {
        alert("请双击选择需要添加的保全项目!");
        return false;
    }
    if (document.all('DisplayType').value == '2')
    {
        var chkflag = false;

        if (InsuredGrid.getSelNo()>0)
        {
             chkflag = true;
        }
        if (!chkflag)
        {
            alert("请选择需要添加保全项目的客户！ ");
            return false;
        }
        if (InsuredGrid.getSelNo()>0 && EdorItemGrid.mulLineCount>0 )
        {
           alert("客户层保全操作，一次只能处理一个客户,请返回 ");
           return false;
        }
        //添加CM保全项客户号下挂起校验，如果有挂起，需要触发业务提醒函
        if(document.all('EdorType').value=='CM')
        {
          var customerno = InsuredGrid.getRowColData(InsuredGrid.getSelNo()-1,2);
          document.all('CMCustomerNo').value=customerno;
          document.all('CMCustomerName').value= InsuredGrid.getRowColData(InsuredGrid.getSelNo()-1,3);
         /*
          var checksql=" select count(*) from lccont a where (a.appntno='"+customerno+"' or a.insuredno='"+customerno+"') "
          +" and a.appflag='1' and a.contno<>'"+document.all('OtherNo').value+"' "
          +" and (exists(select 1 from lcconthangupstate b where a.contno=b.contno) "
          +" or exists (select 1 from ljspay c where c.othernotype='2' and c.otherno=a.contno and c.bankonthewayflag='1'))";
         */
          mySql=new SqlClass();
          mySql.setResourceName("bq.PEdorApp");
          mySql.setSqlId("PEdorAppSql31");
          mySql.addSubPara(customerno);           
          mySql.addSubPara(customerno);
          mySql.addSubPara(document.all('OtherNo').value);
          
          var checkcount = easyExecSql(mySql.getString());
          if(checkcount>0)
          {
            alert("该客户号下其他保单存在挂起或者续期转帐在途情况");
            if (window.confirm("是否打印本次保全的业务处理提醒函?"))
	            {
	                  fm.action = "../f1print/CusBQHungupNotice.jsp";
	                  fm.target="f1print";
	                  document.getElementById("fm").submit();
	            }
          }
        }
    }
    else if (document.all('DisplayType').value == '3')
    {
        var chkflag = false;

        for (i = 0; i < PolGrid.mulLineCount; i++)
        {
            if (PolGrid.getChkNo(i))
            {
                chkflag = true;
                break;
            }
        }
        if (!chkflag)
        {
            alert("请选择需要添加保全项目的险种！ ");
            return false;
        }
    }
    else if (document.all('DisplayType').value == '1')
    {

    }
    else
    {
        alert("保全项目显示级别查询错误！ ");
        return;
    }
    if (document.all('EdorItemAppDate').value == null || document.all('EdorItemAppDate').value == "")
    {
        alert("请输入柜面受理日期！ ");
        return;
    }
    
    //add by jiaqiangli 增加个险保全项保全柜面受理日期的连续性校验
   /* var tCheckSQL = "with lastbq as(select contno,edorappdate from (select contno,edorappdate from "
    				//edorstate in ('0','b') 保全状态是否还应该包含强制终止，核保终止等其他终止的项目
                  + "lpedoritem where contno = '"+document.all("OtherNo").value+"' and edorstate in ('0','b') order by approvedate desc, approvetime desc) where rownum <= 1) "
                  + "select edorappdate from lastbq where edorappdate>'"+document.all('EdorItemAppDate').value+"' ";
    */
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql32");
    mySql.addSubPara(document.all("OtherNo").value);           
    mySql.addSubPara(document.all('EdorItemAppDate').value);

    
    var tCheckFlagDate = easyExecSql(mySql.getString(),1,0,1);
    if (tCheckFlagDate != null && tCheckFlagDate != "") {
    	alert("该保单上次保全操作的柜面受理日期是"+tCheckFlagDate+"，超过本次保全的柜面受理日期"+document.all('EdorItemAppDate').value+"!");
        return;
    }
    //add by jiaqiangli 增加个险保全项保全柜面受理日期的连续性校验
    
    if (!chkEdorContPwd())    return;    //保单密码功能
    
    if (!chkEdorType())
    {
    	return;
    }
    //只有在页面载入的时候用CM标记，其他操作仍然复用常规操作"edorApp"
	  if(fm.LoadFlag.value=="CM")
	  {
			fm.LoadFlag.value="edorApp";
		}
	  
	mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql56");
    mySql.addSubPara(document.all("OtherNo").value);           
    var checkhangup = easyExecSql(mySql.getString());
    if(checkhangup != 0) {
    	alert("该保单已被挂起，不能添加保全项目！");
    	return;
    }
    
    document.all('fmtransact').value="INSERT||EDORITEM";
    fm.AddItem.disabled = true;
    var MsgContent = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=300;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = "../bq/PEdorAppItemSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit();

}

/*********************************************************************
 *  进入保全项目明细页面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function gotoDetail()
{
    var tSelNo = EdorItemGrid.getSelNo()-1;
    if (tSelNo < 0)
    {
        alert("请选择需要变更的项目！");
        return false;
    }

    detailEdorType();

}

function ModEdorPersonInfo()
{
	 if((fm.Mobile_Mod.value == null || trim(fm.Mobile_Mod.value) == '')&&(fm.PostalAddress_Mod.value == null || trim(fm.PostalAddress_Mod.value) == '')){
   	 	alert("申请资格人的手机与联系地址必录其一！");
   	 	return;
   }
   
   if(fm.Mobile_Mod.value != null && trim(fm.Mobile_Mod.value) != ''){
   		var valid= /^[0-9]\d{10}$/;
   		if(!valid.test(fm.Mobile_Mod.value)){
   				alert("申请资格人手机号码不合法!");
   				return;
   		}
   }
   if(fm.PostalAddress_Mod.value != null && trim(fm.PostalAddress_Mod.value) != ''){
   		if(fm.ZipCode_Mod.value == null ||trim(fm.ZipCode_Mod.value) == '' ){
   				alert("地址信息与邮编必须同时录入!");
   				return;
   		}
   }
   if(fm.ZipCode_Mod.value != null && trim(fm.ZipCode_Mod.value) != ''){
   		var valid= /^\d{6}$/;
     	if(!valid.test(fm.ZipCode_Mod.value)){
   			alert("邮编格式不合法!");
   			return;
   		}
   }
   fm.fmtransact.value = "UPDATE||EDORPERSON";
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

   fm.action= "./EdorPersonInfoMod.jsp";
   document.getElementById("fm").submit();
}

/*********************************************************************
 *  删除未录入保全项目
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function delEdorItem()
{
    if (fm.PEdorState.value == 2)
    {
        alert("保全受理已经申请确认");
        return;
    }
   if (fm.PEdorState.value == 'c')
    {
        alert("保全受理已经终止");
        return;
    }  
    var tSelNo = EdorItemGrid.getSelNo()-1;
    if (tSelNo < 0)
    {
        alert("请选择需要删除的项目！");
        return false;
    }
    
    //如果已经进行保全问题件处理，则不允许进行保全撤销
    var tEdorAcceptNo = document.all('EdorAcceptNo').value;
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql12");
    mySql.addSubPara(tEdorAcceptNo);     
   
   // var strSQL = "select Stateflag from loprtmanager where code in ('BQ37') and Stateflag !='2'  and standbyflag1='"+tEdorAcceptNo+"'";
    var sResult;
    sResult = easyExecSql(mySql.getString());
    if(sResult !=null){
        alert("保单存在尚未回销的函件，不允许进行保全撤销");
        return;
    }

    document.all('Transact').value = "DELETE||EDORITEM";
    document.all('fmtransact').value = "DELETE||EDORITEM";
    document.all("DelFlag").value="EdorItem";

    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


    fm.action="./PEdorAppCancelSubmit.jsp";
    document.getElementById("fm").submit();

}

/*********************************************************************
 *  MulLine的RadioBox点击事件，显示项目明细按钮
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getEdorItemDetail()
{

    var tSelNo = EdorItemGrid.getSelNo()-1;

    fm.EdorNo.value =       EdorItemGrid.getRowColData(tSelNo, 2);
    fm.EdorType.value =     EdorItemGrid.getRowColData(tSelNo, 20);
    //fm.EdorTypeName.value = EdorItemGrid.getRowColData(tSelNo, 3);
    fm.ContNo.value =       EdorItemGrid.getRowColData(tSelNo, 6);
    fm.InsuredNo.value =    EdorItemGrid.getRowColData(tSelNo, 7);//客户层保全下，此变量存的是lpedorapp.otherno,即客户号，可以参考getEdorItemGrid()中具体查询内容
    fm.PolNo.value =        EdorItemGrid.getRowColData(tSelNo, 8);
    fm.EdorItemAppDate.value= EdorItemGrid.getRowColData(tSelNo, 9);
    fm.EdorValiDate.value = EdorItemGrid.getRowColData(tSelNo, 10);
    fm.AppReasonName.value =EdorItemGrid.getRowColData(tSelNo, 11);
    fm.AppReason.value =    EdorItemGrid.getRowColData(tSelNo, 12);
    fm.MakeDate.value =     EdorItemGrid.getRowColData(tSelNo, 14);
    fm.EdorMakeDate.value = EdorItemGrid.getRowColData(tSelNo, 14);
    fm.MakeTime.value =     EdorItemGrid.getRowColData(tSelNo, 15);
    fm.EdorItemState.value =EdorItemGrid.getRowColData(tSelNo, 19);
	
	//modify by jiaqiangli 2009-01-22 增加条件判断 否则会发生团险与个险保全项目重复编码
	showOneCodeNameEx('EdorCode', 'EdorType', 'EdorTypeName','APPOBJ','I'); 
    //showOneCodeName('EdorCode', 'EdorType', 'EdorTypeName');    //XinYQ modified on 2005-11-17 : old :fm.EdorTypeName.value = "";

    //alert(fm.EdorNo.value);
    //alert(fm.EdorType.value);
    //alert(fm.ContNo.value);
    //alert(fm.InsuredNo.value);
    //alert(fm.PolNo.value);
    //alert(fm.EdorValiDate.value);
    //alert(fm.MakeDate.value);
    //alert(fm.MakeTime.value);
    //alert(fm.EdorItemState.value);
    fm.CustomerNoBak.value = fm.InsuredNo.value;
    fm.ContNoBak.value = fm.ContNo.value;

    //XinYQ added on 2006-05-15 : 修正可能存在重复添加保全项目的问题
    var oEdorType;
    try
    {
        oEdorType = document.getElementsByName("EdorType")[0];
        afterCodeSelect("EdorCode", oEdorType);
    }
    catch (ex) {}
}
/*********************************************************************
 *  保全申请确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function edorAppConfirm()
{


    if (fm.PEdorState.value == 2)
    {
        alert("保全受理已经申请确认");
        return;
    }
    if (fm.PEdorState.value == 'c')
    {
        alert("保全受理已经终止");
        return;
    }
    if(!fm.IsFull.checked){
			alert("受理资料不齐全，不能进行申请确认!");
			return;
		}
		
		//var strSQL =  " select edortype,AppReason from lpedoritem where edoracceptno = '"+fm.EdorAcceptNo.value+"'";
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql49");
    mySql.addSubPara(fm.EdorAcceptNo.value);     
    
    var arr = easyExecSql(mySql.getString());
    if (!arr )
    {
    	alert("该保全受理下没有保全项目，请先添加！");
    	return;
    }
    var tEdorType = arr[0][0];
    var tAppReason = arr[0][1];
    if (tAppReason !='2') {
    	if((fm.Mobile.value == null || trim(fm.Mobile.value) == '')&&(fm.PostalAddress.value == null || trim(fm.PostalAddress.value) == ''))
    	{

				//客户联系方式变更、客户基本资料变更、交费方式与交费账号变更、保单挂失、挂失解除
				if(tEdorType != "AM" && tEdorType != "BB" && tEdorType != "PC" && tEdorType != "PL")
				{
					alert("申请资格人联系方式不全，须录入申请资格人联系方式！");
					divEdorPersonInfo.style.display = '';
					return;
				}
			}
 	  }
		
		//校验是否添加保全项
   //如果已经进行保全问题件处理，则不允许进行保全撤销
    var tEdorAcceptNo = document.all('EdorAcceptNo').value;
    //var strSQL = "select Stateflag from loprtmanager where code in ('BQ37') and Stateflag !='2'  and standbyflag1='"+tEdorAcceptNo+"'";
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql50");
    mySql.addSubPara(fm.EdorAcceptNo.value);   
   
    var sResult;
    sResult = easyExecSql(mySql.getString());
    if(sResult!=null){
        alert("保单存在尚未回销的函件，不允许进行保全确认");
        return;
    }
    
    
    document.all("fmtransact").value = "INSERT||EDORAPPCONFIRM";

//    if (window.confirm("请确认保全收付费方式信息，点取消进行输入或修改！"))
//    {
        if(fm.GetPayForm.value == null || fm.GetPayForm.value == null)
        {
           alert("对不起，您还没有录入收付费方式!");
           return;
        }
        if(fm.GetPayForm.value=="4" || fm.GetPayForm.value=="7")
        {
            //银行划款或网上支付
            if(fm.BankCode.value == null || fm.BankCode.value == ""
             || fm.BankAccNo.value == null || fm.BankAccNo.value == ""
             || fm.AccName.value == null || fm.AccName.value == "")
          {
                alert("银行收付费信息不完整!");
                return;
          }
          if(fm.OtherNoType.value == "1")//客户层
          {
            if(fm.PayGetName.value == null || fm.PayGetName.value == ""
             || fm.PersonID.value == null || fm.PersonID.value == "")
            {
                alert("选择银行收付费方式时必须录入领取人及身份证号码!");
                return;
            }
          }
        }
        if(fm.PayGetName.value != null && fm.PayGetName.value != "")
        {
            if(fm.PersonID.value == null || fm.PersonID.value == "")
            {
                alert("请录入领取人身份证号!");
                return;
            }
        }
        if(fm.PersonID.value != null && fm.PersonID.value != "")
        {
            if(fm.PayGetName.value == null || fm.PayGetName.value == "")
            {
                alert("请录入领取人姓名!");
                return;
            }
        }
        //add by xiongzh  校验获取othernotype不能为空
        if(fm.OtherNoType.value == null || fm.OtherNoType.value == "")
        {
            alert("获取保全申请号码类型失败，无法正常进行下一步操作!");
            return;
        }
        var showStr="正在计算数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");

        fm.action= "./PEdorAcptAppConfirmSubmit.jsp";
        document.getElementById("fm").submit();
//    }
}


/*********************************************************************
 *  保全复核修改确认
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function ApproveModifyConfirm()
{
    if (fm.PEdorState.value == 2)
    {
        alert("保全受理已经申请确认");
        return;
    }
     if (fm.PEdorState.value == 'c')
    {
        alert("保全受理已经终止");
        return;
    }
    document.all("fmtransact").value = "INSERT||EDORAPPCONFIRM";

    if (window.confirm("是否确认本次保全申请?"))
    {

        var showStr="正在计算数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");

        fm.action= "./PEdorAcptAppConfirmSubmit.jsp";
        document.getElementById("fm").submit();

    }
}

/*********************************************************************
 *  保全申请保存
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function applySave()
{
    var tOtherNo = document.all('OtherNo').value;
    var tOtherNoType = document.all('OtherNoType').value;
    if (tOtherNoType == null || tOtherNoType == "")
    {
        alert("请输入号码类型！");
        return;
    }
    if(tOtherNo == null || tOtherNo == "")
    {
        if(tOtherNoType == "1")
        {
            alert("请输入客户号码！");
            return;
        }
        if(tOtherNoType == "3")
        {
            alert("请输入保单号码！");
            return;
        }
    }
    if(tOtherNoType == "1")
    {
        displayCustomerInfo(tOtherNo);
        if(fm.CustomerNo.value == null || fm.CustomerNo.value == "")
        {
            alert("客户号不存在!");
            return;
        }
        if (fm.EdorAppName.value == null || fm.EdorAppName.value == "")
        {
            if (fm.LoadFlag.value != "edorTest")
            {
                alert("请录入申请人姓名!");
                return;
            }
        }
    }
    if(tOtherNoType == "3")
    {
        displayContInfo(tOtherNo);
        displayContStateInfo(fm.OtherNo.value);  //查询保单状态详细信息
        if(fm.ContNoApp.value == null || fm.ContNoApp.value == "")
        {
            alert("保单号不存在!");
            return;
        }
    }
    
    //add by jiaqiangli 2009-11-19 核保室的保全扫描申请 目前只有客户层下的公司层面的增补告知
   /* var tFlagSQL="select createoperator from lbmission a where missionprop1='"+fm.EdorAcceptNo.value+"' and activityid='0000000001' and exists(select 1 from lduwuser where usercode=a.createoperator) ";*/
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql12");
    mySql.addSubPara(fm.EdorAcceptNo.value);       
    
    var tFlag = easyExecSql(mySql.getString());
    if (tFlag != null && tFlag != '' && fm.AppType.value != '6') {
    	alert("核保室的保全扫描申请"+"(操作员为"+tFlag[0][0]+")"+"的申请方式必须选择内部转办!");
        return;
    }
    //add by jiaqiangli 2009-11-19 核保室的保全扫描申请 目前只有客户层下的公司层面的增补告知
      //处于客户重要资料变更的不能进行任何保全申请，这个校验加在哪呢？
		//  var strSQL =" select 'X' from lpconttempinfo where state='0' and contno='"+tOtherNo+"'";
		//	var brr = easyExecSql(strSQL);
		//	if ( brr )
		//	{
		//		alert("此保单正在进行客户层重要资料变更，请先进行客户重要资料变更");		
    //    return false;
		//	}
    //校验保单是否有催收和银行在途
   if(tOtherNoType == "3")
    {
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql13");
    mySql.addSubPara(tOtherNo);      	
    	
	    /*var selSQL = " select BankOnTheWayFlag from ljspay where othernotype in ('2') "
	                  + " and otherno = '" + tOtherNo + "'";  */                
	    var brr = easyExecSql(mySql.getString());
	    var tFLag;
	    if(brr)
	    {
	      brr[0][0]==null||brr[0][0]=='null'?'0':tFLag  = brr[0][0];
	      if(tFLag=='1')
	      {
	      	    alert("此保单存在续期应收，而且银行在途，暂时不可以进行保全申请!");
	            return;
	      }else 
	      	{
	      	if(!confirm("此保单存在续期应收，是否继续"))
	      	{
	      		   return;
	      	}     		
	      	}
	    }
    }
    if(tOtherNoType == "1")
    {
       var EdorAppDate = document.all('EdorAppDate').value;
       
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql14");
    mySql.addSubPara(tOtherNo);         
    mySql.addSubPara(tOtherNo);
    mySql.addSubPara(EdorAppDate);
    mySql.addSubPara(EdorAppDate);
    mySql.addSubPara(EdorAppDate);
    mySql.addSubPara(EdorAppDate);
    mySql.addSubPara(EdorAppDate);
    mySql.addSubPara(EdorAppDate);
    /*
       var selSQL = " select c.contno,d.bankonthewayflag from lccont c,ljspay d "
                +" where c.contno=d.otherno and d.othernotype='2' "
				+ "and (c.GrpContNo is null or c.GrpContNo = '00000000000000000000') "
				+ "and c.contno in ( select contno from lcinsured where insuredno = '"
				+ tOtherNo
				+ "' "
				+ " union " // 以投保人或被保人身份相关的保单
				+ " select contno from lcappnt where appntno = '"
				+ tOtherNo
				+ "' ) "
				+ " and "
				+ " ( " // 保单未终止，并且主险未失效（失效保单不参与客户层变更）
				+ " ( appflag = '1' and not exists "
				+ " ( select 'X' from lccontstate s "
				+ " where trim(statetype) in ('Available') and trim(state) = '1' "
				+ " and ((startdate <= '"
				+ EdorAppDate
				+ "' and '"
				+ EdorAppDate
				+ "' <= enddate) or (startdate <= '"
				+ EdorAppDate
				+ "' and enddate is null))"
				+ " and contno = c.contno and trim(polno) = (select trim(p1.polno) from lcpol p1 where p1.contno = s.contno and p1.polno = p1.mainpolno and rownum = 1) "
				+ " ) "
				+ " ) "
				+ " or "
				+ " ( appflag = '4' and exists " // 01-满期终止、05-自垫终止、06-贷款终止 可以参与客户层变更
				+ " ( select 'X' from lccontstate s "
				+ " where trim(statetype) in ('Terminate') and trim(state) = '1' and trim(statereason) is not null and trim(statereason) in ('01', '05', '06', '09') "
				+ " and ((startdate <= '"
				+ EdorAppDate
				+ "' and '"
				+ EdorAppDate
				+ "' <= enddate) or (startdate <= '"
				+ EdorAppDate
				+ "' and enddate is null)) "
				+ " and contno = c.contno and trim(polno) = (select trim(p1.polno) from lcpol p1 where p1.contno = s.contno and p1.polno = p1.mainpolno and rownum = 1) "
				+ " ) " + " ) " + " ) ";        
		*/		
				                   
	   var brr = easyExecSql(mySql.getString());
	   var tFLag;
	   if(brr)
	    {
        mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorApp");
        mySql.setSqlId("PEdorAppSql15");
	    	
	    	
	      selSQL=selSQL+"  and d.bankonthewayflag='1'";
	      var crr = easyExecSql(mySql.getString());
	      if(crr)
	      {
	      	    alert("此客户名下保单存在续期应收，而且银行在途，暂时不可以进行保全申请!");
	      	    if (window.confirm("是否打印本次保全的业务处理提醒函?"))
	            {
	                  fm.action = "../f1print/CusBQHungupNotice.jsp";
	                  fm.target="f1print";
	                  document.getElementById("fm").submit();
	            }
	            return ;
	      }
	      else 
	      {
	      	if(!confirm("此客户名下保单存在续期应收，是否继续"))
	      	{
	      		   return;
	      	}     		
	      }
	    }
    }
    //有太多的隐藏按钮，建议不使用
    //if (!verifyInput2()) return;

    if(fm.EdorApp.value=='' || fm.EdorApp.value==null)
    {
    	      alert("请输入申请人相关信息!");
            return;
    }
    
    if(fm.EdorApp.value=='其他' && (fm.EdorAppName.value==null|| fm.EdorAppName.value==''))
    {
    	      alert("请输入申请人相关信息!");
            return;
    }
    
     //add by jiaqianglee 2009-02-18 暂时不加校验，如有需要，请直接放开即可
    if (fm.CustomerPhone.value != null && fm.CustomerPhone.value != '') {
    if (verifyTel("申请资格人联系电话",fm.CustomerPhone.value) == false)  {
        alert("申请资格人联系电话录入不合法!");
    	return false;
    }
    }    	 
    
     if (fm.BfPhone.value != null && fm.BfPhone.value != '') {
    if (verifyTel("代办人联系电话",fm.BfPhone.value) == false)  {
        alert("代办人联系电话录入不合法!");
    	return false;
    }
    } 
    
    //add by jiaqiangli 2009-02-18 申请资格人联系电话
    //只校验其他代办人的身份证号 业务员代办信息带出，不做校验
    if (fm.AppType.value=='3') {
    if (fm.BfIDType.value != null && fm.BfIDType.value == '0') {
    	if (fm.BfIDNo.value != null && fm.BfIDNo.value != '') {
    		if(!checkIdCard(fm.BfIDNo.value)) {
    			alert("代办人身份证件号码录入有误！");
    			return false;
    		}
    	}
    }
    }
    
    if (fm.AppType.value=='2') {
    	if( (fm.BfAgentCode.value==null|| fm.BfAgentCode.value=='') 
    	   || (fm.ManageCom.value==null|| fm.ManageCom.value=='')
    	   || (fm.ManageComName.value==null|| fm.ManageComName.value=='')
    	   || (fm.BfName.value==null|| fm.BfName.value=='')
    	   || (fm.BfIDType.value==null|| fm.BfIDType.value=='')
    	   || (fm.BfIDTypeName.value==null|| fm.BfIDTypeName.value=='')
    	   || (fm.BfIDNo.value==null|| fm.BfIDNo.value=='')
    	   || (fm.BfPhone.value==null|| fm.BfPhone.value=='')
    	   ){
    		    alert("请检查录入业务员所有的代办信息！请确认是否录入了代办人联系电话！");
    			return false;
    	}
    }
     if (fm.AppType.value=='3') {
    	if(  (fm.BfName.value==null|| fm.BfName.value=='')
    	   || (fm.BfIDType.value==null|| fm.BfIDType.value=='')
    	   || (fm.BfIDTypeName.value==null|| fm.BfIDTypeName.value=='')
    	   || (fm.BfIDNo.value==null|| fm.BfIDNo.value=='')
    	   || (fm.BfPhone.value==null|| fm.BfPhone.value=='')
    	   ){
    		    alert("请检查录入其他人代办的代办信息！");
    			return false;
    	}
    }
     if (fm.AppType.value=='6') {
    	if(  (fm.InternalSwitchChnl.value==null|| fm.InternalSwitchChnl.value=='')
    	   || (fm.InternalSwitchChnlName.value==null|| fm.InternalSwitchChnlName.value=='')
    	   ){
    		    alert("请检查录入内部转办渠道信息！");
    			return false;
    	}
    }
    //if (fm.CustomerPhone.value == null || fm.CustomerPhone.value == '') {
    //	if (window.confirm("申请资格人联系电话为空是否继续?") == false) {
    //			return;
    //        }
    //}
    
   
   		if((fm.CustomerPhone.value == null || trim(fm.CustomerPhone.value) == '')&&(fm.Mobile.value == null || trim(fm.Mobile.value) == '')&&(fm.PostalAddress.value == null || trim(fm.PostalAddress.value) == '')){
   			if(!confirm("申请资格人联系方式录入不齐全，是否继续？")){
   				return;
   			}
   		}
   		
   		if(fm.Mobile.value != null && trim(fm.Mobile.value) != ''){
   			var valid= /^[0-9]\d{10}$/;
   			if(!valid.test(fm.Mobile.value)){
   					alert("申请资格人手机号码不合法!");
   					return;
   			}
   		}
   		if(fm.PostalAddress.value != null && trim(fm.PostalAddress.value) != ''){
   			if(fm.ZipCode.value == null ||trim(fm.ZipCode.value) == '' ){
   					alert("地址信息与邮编必须同时录入!");
   					return;
   			}
   		}
   		if(fm.ZipCode.value != null && trim(fm.ZipCode.value) != ''){
   			var valid= /^\d{6}$/;
   		  if(!valid.test(fm.ZipCode.value)){
   					alert("邮编格式不合法!");
   					return;
   			}
   		}

  	
    
    fm.fmtransact.value = "INSERT||EDORAPP";
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	
    document.getElementById("fm").submit(); //提交
    //add by jiaqiangli 2009-02-18 申请资格人联系电话
}

/*********************************************************************
 *  后台执行完毕反馈信息
 *  描述: 后台执行完毕反馈信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
    showInfo.close();

    fm.AddItem.disabled = false;

    if (FlagStr == "Succ" )
    {
        if (fm.fmtransact.value == "INSERT||EDORAPP") //保全受理保存
        {
            initForm();
        }
        if (fm.fmtransact.value == "INSERT||EDORITEM") //添加保全项目
        {
            displayCustomerInfo(document.all('OtherNo').value);
            EdorItemGrid.clearData();
            getEdorItemGrid();
            //XinYQ added on 2006-05-15 : 自动弹出明细界面
            EdorItemGrid.selOneRow(1);
            gotoDetail();
        }
        if (fm.fmtransact.value == "DELETE||EDORITEM") //撤销保全项目
        {
            displayCustomerInfo(document.all('OtherNo').value);
            EdorItemGrid.clearData();
            getEdorItemGrid();
            clearLastEdorInfo();
        }
        //alert(fm.fmtransact.value );
        if (fm.fmtransact.value == "INSERT||EDORAPPCONFIRM") //申请确认
        {
            var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
            //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

			
            //alert(fm.EdorType.value);
            if(fm.EdorType.value!='RB')
            {
            if (window.confirm("是否打印本次保全的申请批单?"))
            {
                  fm.action = "../f1print/AppEndorsementF1PJ1.jsp";
                  fm.target="f1print";
                  document.getElementById("fm").submit();
            }
            }
            initForm();

        }
        if (fm.fmtransact.value == "DELETE||EDORTESTFINISH") //试算完毕
        {
            returnParent();
        }
        if (fm.fmtransact.value == "UPDATE||EDORPERSON") //试算完毕
        {
            var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
            //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			
			var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

			
            initForm();
        }
    }
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='提示';   //网页名称，可为空; 
			var iWidth=550;      //弹出窗口的宽度; 
			var iHeight=250;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

        if (fm.fmtransact.value == "INSERT||EDORAPPCONFIRM") //申请确认
        {
            initForm();
            // 应边莹要求 放开这个提示 modify by jiaqiangli 2009-04-23
            if (window.confirm("是否打印本次保全的申请批单?"))
            {
                  fm.action = "../f1print/AppEndorsementF1PJ1.jsp";
                  fm.target="f1print";
                  document.getElementById("fm").submit();
            }
        }
        if (fm.fmtransact.value == "INSERT||EDORAPP") //保全申请
        {
            if(CusBQPrintFlag=="1")
            {
	            if (window.confirm("是否打印本次保全的业务处理提醒函?"))
	            {
	                  fm.action = "../f1print/CusBQHungupNotice.jsp";
	                  fm.target="f1print";
	                  document.getElementById("fm").submit();
	            }
            }
        }
    }

}
//判断客户层保全申请失败是否需要打印保全业务处理提醒函(客户号底下存在保单被挂起导致申请失败的时候判断)
function CusPrintcheck(tModuleName)
{
  if(document.all('OtherNoType').value=='1')
  {
    if(tModuleName=='ContHangUpBL')
    {
       CusBQPrintFlag="1";
    }
  }
  return true;
}

/*********************************************************************
 *  查询保全项目，写入MulLine
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getEdorItemGrid()
{
    //try
    //{
    //    EdorItemGrid.clearData();
    //}
    //catch (ex) {}

    var tEdorAcceptNo = document.all('EdorAcceptNo').value;
    var tOtherNoType = document.all('OtherNoType').value;

    if(tEdorAcceptNo!=null)
    {

        if (tOtherNoType=='3') //个人保单号申请
        {
          mySql=new SqlClass();
          mySql.setResourceName("bq.PEdorApp");
          mySql.setSqlId("PEdorAppSql16");
          mySql.addSubPara(tEdorAcceptNo);  
   	/*
            var strSQL =  " select EdorAcceptNo, EdorNo, "
                        + " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype) and appobj in ('I', 'B') ), "
                        + "DisplayType, "
                        + " GrpContNo, ContNo, InsuredNo, PolNo, EdorAppDate, EdorValiDate, "
                        + " (select CodeName from LDCode a where a.codetype = 'edorappreason' and trim(a.code) = trim(appreason)), appreason, "
                        + " GetMoney, MakeDate, MakeTime, ModifyDate, Operator, "
                        + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate)),"
                        + "EdorState,EdorType "
                        + " from LPEdorItem "
                        + " where EdorAcceptNo='" + tEdorAcceptNo + "'"
                        + " order by makedate asc, maketime asc";
                        //+ " and ContNo = '" + LCContGrid.getRowColData(tSelNo,1) + "'";
    */
            //turnPage.queryModal(strSQL, EdorItemGrid);
            arrResult = easyExecSql(mySql.getString());
            if (arrResult)
            {
                displayMultiline(arrResult, EdorItemGrid);
            }
			else if(arrResult == null)
			{
				EdorItemGrid.clearData();
			}
        }
        else if (tOtherNoType=='1') //个人客户号申请
        {
        	var EdorAppDate = document.all('EdorAppDate').value;
        	var CustomerNo =document.all('OtherNo').value;
          mySql=new SqlClass();
          mySql.setResourceName("bq.PEdorApp");
          mySql.setSqlId("PEdorAppSql17");
          mySql.addSubPara(tEdorAcceptNo);  

        	/*
            var strSQL =  " select distinct EdorAcceptNo, '', "
                        + " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype) and appobj in ('B')), "
                        + " DisplayType, '', '000000', (select otherno from lpedorapp x where x.edoracceptno=LPEdorItem.Edoracceptno and x.othernotype='1'), PolNo, EdorAppDate, EdorValiDate, "
                        + " (select CodeName from LDCode where codetype = 'edorappreason' and trim(code) = trim(appreason)), "
                        + " appreason, '', MakeDate, MakeTime, '', Operator, "
                        + " (select CodeName from LDCode where codetype = 'edorstate' and trim(code) = trim(edorstate)),"
                        + " EdorState, EdorType "
                        + " from LPEdorItem "
                        + " where EdorAcceptNo='" + tEdorAcceptNo + "'"
                        + " order by makedate asc, maketime asc";
                        //+ " and ContNo = '" + LCContGrid.getRowColData(tSelNo,1) + "'";
            */
            //turnPage.queryModal(strSQL, EdorItemGrid);
            arrResult = easyExecSql(mySql.getString());
            if (arrResult)
            {
                displayMultiline(arrResult, EdorItemGrid);
            }
        }
        showDivGetPayForm();//显示收付费方式信息
    }
}

/*********************************************************************
 *  回车实现查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function QueryOnKeyDown()
{
	
    if (hasSaved == "Y")
    {
        return;
    }

        var keyCode;
        if(mflag == "0" || mflag == "1")
        {
            keycode ="13";
            mflag = "";
        }
        else
        {
            keycode = event.keyCode;
        }

    //回车的ascii码是13
    if (keycode=="13")
    {
        var tOtherNoType = fm.OtherNoType.value;
        var tOtherNo = fm.OtherNo.value;

        if(tOtherNoType == null || tOtherNoType == "")
        {
            alert("请输入号码类型！");
            return;
        }
        if(tOtherNo == null || tOtherNo == "")
        {
            if(tOtherNoType == "1")
            {
                alert("请输入客户号码！");
                return;
            }
            if(tOtherNoType == "3")
            {
                alert("请输入保单号码！");
                return;
            }
        }
        if(tOtherNoType == "1")
        {
            displayCustomerInfo(tOtherNo);
            divCustomer.style.display='';       //客户信息
            divCont.style.display="none";       //保单信息
            divContState.style.display="none";  //保单状态信息
            if(fm.CustomerNo.value == null || fm.CustomerNo.value == "")
            {
                alert("客户号不存在!");
            }
        }
        if(tOtherNoType == "3")
        {
            displayContInfo(tOtherNo);
            divCustomer.style.display="none";   //客户信息
            divCont.style.display='';           //保单信息
            divContState.style.display='';  //保单状态信息
            if(fm.ContNoApp.value == null || fm.ContNoApp.value == "")
            {
                alert("保单号不存在！ ");
                clearContStateInfo();
            }
        }
    }
}

/*********************************************************************
 *  返回
 *  描述: 返回
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function returnParent()
{
    //top.opener.initSelfGrid();
    try
    {
        top.close();
        top.opener.easyQueryClickSelf();
        top.opener.focus();
    }
    catch (ex) {}
}

/*********************************************************************
 *  客户查询
 *  描述: 客户查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function customerQuery()
{
  mflag = "1";
    var showInfo = window.open( "../sys/LDPersonQueryNew.html","LDPersonQueryNew",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    showInfo.focus();
}

/*********************************************************************
 *  保单查询
 *  描述: 保单查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function contQuery()
{
    mflag = "0";
    var showInfo = window.open("../sys/AllProposalQueryMain.jsp","AllProposalQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    showInfo.focus();
}

function afterQuery(arrQueryResult)
{
    if (arrQueryResult != null)
    {
        if (mflag == "1")
        {
            try { document.all('OtherNo').value = arrQueryResult[0][0]; } catch(ex) {};
            try { document.all('OtherNoType').value = "1"; } catch(ex) {};
        }
        else
        {
            try { document.all('OtherNo').value = arrQueryResult[0][0]; } catch(ex) {};
            try { document.all('OtherNoType').value = "3"; } catch(ex) {};
        }
        QueryOnKeyDown();
    }
}

/*********************************************************************
 *  保单状态查询
 *  描述: 保单状态查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function ContStateQuery()
{

//   strSql = "select statetype,state from lccontstate where contno='"+document.all('ContNoApp').value+"' and (statetype='Lost' or statetype='Loan'or statetype='BankLoan')";
   var strSql = "";
   var sqlid1="PEdorAppSql54";
   var mySql1=new SqlClass();
   mySql1.setResourceName("bq.PEdorApp"); //指定使用的properties文件名
   mySql1.setSqlId(sqlid1);//指定使用的Sql的id
   mySql1.addSubPara(document.all('ContNoApp').value);//指定传入的参数
   strSql=mySql1.getString();
   var arrResult = easyExecSql(strSql, 1, 0);

   if (arrResult == null)
   {
       //alert("没有相应的保单信息");
   }
   else
   {
    //document.all('Available').value = arrResult[0][2];
    //document.all('Terminate').value = arrResult[0][1];
      document.all('Lost').value = arrResult[0][1];
    //document.all('PayPrem').value = arrResult[0][3];
      document.all('Loan').value = arrResult[1][1];
      document.all('BankLoan').value = arrResult[2][1];
    //document.all('RPU').value = arrResult[0][6];
   }
}

/*********************************************************************
 *  试算完毕
 *  描述: 试算完毕提交后台清除试算数据
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function EdorTestFinish()
{
    document.all("fmtransact").value = "DELETE||EDORTESTFINISH";

    var showStr="正在删除数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


    fm.action= "./PEdorTestFinishSubmit.jsp";
    document.getElementById("fm").submit();
}
/*********************************************************************
 *  开关收付费方式信息区域
 *  描述: 开关收付费方式信息区域
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showDivGetPayForm()
{
    if(fm.LoadFlag.value == "edorApp" || fm.LoadFlag.value == "scanApp" || fm.LoadFlag.value == "approveModify" ||  fm.LoadFlag.value=="CM")
    {
        var nCountNo = EdorItemGrid.mulLineCount;
        if(nCountNo > 0)
        {
          //只有添加了保全项目时，收付费录入的区域才会出现
//          alert();
          if(EdorItemGrid.getRowColData(0,20)!=null&&(EdorItemGrid.getRowColData(0,20)=="CT"||EdorItemGrid.getRowColData(0,20)=="XT"||EdorItemGrid.getRowColData(0,20)=="LG"))
          {
        	  //什么都不做
          }
          //除了退保、协议退保、生存领取都显示
          else{
        	  divGetPayFormInfo.style.display="";      //收付费方式信息
          }
          
          initGetPayForm();                      //初始化收付费方式信息
        }
        else
        {
            divGetPayFormInfo.style.display="none";   //收付费方式信息
        }

    }
    else
    {
        divGetPayFormInfo.style.display="none";   //隐藏收付费方式信息
    }
}
/*********************************************************************
 *  初始化收付费方式信息
 *  描述: 初始化收付费方式信息，当每次初始化此区域、fm.GetPayForm得到焦点时调用
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initGetPayForm()
{
   var tGetPayForm = fm.GetPayForm.value;
   if(tGetPayForm == null || tGetPayForm == "")
   {
      fm.GetPayForm.value = "1";        // 默认为现金方式
      fm.GetPayFormName.value = "现金";
   }
   //9网银代付 add by jiaqiangli 2009-05-21
   if(tGetPayForm == "4"||tGetPayForm == "9")//银行划款,网上支付
   {
      divBankInfo.style.display='';
      if(fm.BankCode.value == null || fm.BankCode.value == "")
      {
          //如果尚未录入过银行等信息，则取默认值
          if(fm.OtherNoType.value == "1")
          {
              //客户层变更
              fm.BankCode.value = "";
              fm.BankName.value = "";
              fm.BankAccNo.value = "";
              fm.AccName.value = "";
          }
          else
          {
            var strsql = "";
            mySql=new SqlClass();
            mySql.setResourceName("bq.PEdorApp");
             
            
            
            if(fm.EdorType.value == "AG")
            {
                //年金、满期金给付取被保人帐号
               /* strsql = "select Getbankcode,(select bankname from ldbank where bankcode = a.Getbankcode and rownum=1),"
                       + " Getbankaccno,Getaccname from lcpol a "
                       + " where polno = (select polno from lpedoritem where edortype = 'AG' and edoraccetpno = '"+fm.EdorAcceptNo.value+"') ";
              */
               mySql.setSqlId("PEdorAppSql18");
               mySql.addSubPara(fm.EdorAcceptNo.value);           
            }
            else
            {
                //取投保人帐号
                /*
                strsql = "select bankcode,(select bankname from ldbank where bankcode = a.bankcode and rownum=1),bankaccno,accname from lccont a where contno = '"+fm.OtherNo.value+"'";
                */
                mySql.setSqlId("PEdorAppSql9");
                mySql.addSubPara(fm.OtherNo.value);            
            
            }
            var brr = easyExecSql(mySql.getString());
            if(brr)
            {
               brr[0][0]==null||brr[0][0]=='null'?'0':fm.BankCode.value  = brr[0][0];
               brr[0][1]==null||brr[0][1]=='null'?'0':fm.BankName.value = brr[0][1];
               brr[0][2]==null||brr[0][2]=='null'?'0':fm.BankAccNo.value = brr[0][2];
               brr[0][3]==null||brr[0][3]=='null'?'0':fm.AccName.value = brr[0][3];
            }
          }
      }
   }
   else
   {
      divBankInfo.style.display="none";
      fm.BankCode.value = "";
      fm.BankName.value = "";
      fm.BankAccNo.value = "";
      fm.AccName.value = "";
   }
}

/*============================================================================*/

/**
 * 检查系统是否启用保单密码
 * XinYQ added on 2006-11-07
 */
function isEnableEdorContPwd()
{
    var QuerySQL, arrResult;
    
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql46");
    
    //QuerySQL = "select SysVarValue from LDSysVar where SysVar = 'EnableEdorContPwd'";

    try
    {
        arrResult = easyExecSql(mySql.getString(), 1, 0);
    }
    catch (ex)
    {
        alert("警告：检查系统是否启用保单密码功能出现异常！ ");
        return false;
    }
    if (arrResult != null && trim(arrResult[0][0]) == "1")
    {
        return true;
    }
    return false;
}

/*============================================================================*/

/**
 * 检查保全项目是否需要保单密码
 * XinYQ added on 2006-11-07
 */
function isEdorItemNeedPwd()
{
    var  arrResult;
//    QuerySQL = "select PwdFlag "
//             +   "from LMEdorItem "
//             +  "where 1 = 1 "
//             +     getWherePart("EdorCode", "EdorType")
//             +    "and AppObj in ('I', 'B')";
    //alert(QuerySQL);
    var QuerySQL = "";
    var sqlid1="PEdorAppSql55";
    var mySql1=new SqlClass();
    mySql1.setResourceName("bq.PEdorApp"); //指定使用的properties文件名
    mySql1.setSqlId(sqlid1);//指定使用的Sql的id
    mySql1.addSubPara(window.document.getElementsByName("EdorType")[0].value);//指定传入的参数
    QuerySQL=mySql1.getString();
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("警告：检查保全项目是否需要保单密码出现异常！ ");
        return false;
    }
    if (arrResult != null && trim(arrResult[0][0]) == "1")
    {
        return true;
    }
    return false;
}

/*============================================================================*/

/**
 * 检查是否需要输入保单密码以及保单密码是否正确
 * XinYQ added on 2006-11-07
 */
function chkEdorContPwd()
{
    if (isEnableEdorContPwd() && isEdorItemNeedPwd())
    {
        var sEdorAcceptNo, sOtherNo, sOtherNoType, sEdorItemAppDate, sAppType;
        try
        {
            sEdorAcceptNo = document.getElementsByName("EdorAcceptNo")[0].value;
            sOtherNo = document.getElementsByName("OtherNo")[0].value;
            sOtherNoType = document.getElementsByName("OtherNoType")[0].value;
            sEdorItemAppDate = document.getElementsByName("EdorItemAppDate")[0].value;
            sAppType = document.getElementsByName("AppType")[0].value;
        }
        catch (ex) {}
        if (sOtherNo == null || sOtherNo.trim() == "" || sOtherNoType == null || sOtherNoType.trim() == "" || sEdorItemAppDate == null || sEdorItemAppDate.trim() == "")
        {
            alert("无法获取保全申请信息。查询保单密码信息失败！ ");
            return false;
        }
        
       mySql=new SqlClass();
       mySql.setResourceName("bq.PEdorApp");
          
        /*    
        var QuerySQL = "select 'X' "
                     +   "from LCCont a "
                     +  "where 1 = 1 "
                     +    "and a.Password is not null ";
        */             
        if (sOtherNoType == "1")
        {
        	  mySql.setSqlId("PEdorAppSql33");
        	  mySql.addSubPara(sEdorItemAppDate); 
        	  mySql.addSubPara(sEdorItemAppDate);
        	  mySql.addSubPara(sEdorItemAppDate);
        	  mySql.addSubPara(sEdorItemAppDate);
        	  mySql.addSubPara(sEdorItemAppDate);
        	  mySql.addSubPara(sEdorItemAppDate);
        	  mySql.addSubPara(sOtherNo);
        	  mySql.addSubPara(sOtherNo);
        	  /*
            QuerySQL +=    "and ((a.AppFlag = '1' and not exists "
                     +         "(select 'X' "
                     +             "from LCContState "
                     +            "where 1 = 1 "
                     +              "and ContNo = a.ContNo "
                     +              "and (PolNo = '000000' or "
                     +                   "PolNo = (select PolNo "
                     +                              "from LCPol "
                     +                             "where 1 = 1 "
                     +                               "and ContNo = a.ContNo "
                     +                               "and PolNo = MainPolNo)) "
                     +              "and StateType = 'Available' "
                     +              "and State = '1' "
                     +              "and (('" + sEdorItemAppDate + "' >= StartDate and '" + sEdorItemAppDate + "' <= EndDate) or "
                     +                  "('" + sEdorItemAppDate + "' >= StartDate and EndDate is null)))) or "
                     +        "(a.AppFlag = '4' and exists "
                     +         "(select 'X' "
                     +             "from LCContState "
                     +            "where 1 = 1 "
                     +              "and ContNo = a.ContNo "
                     +              "and (PolNo = '000000' or "
                     +                   "PolNo = (select PolNo "
                     +                              "from LCPol "
                     +                             "where 1 = 1 "
                     +                               "and ContNo = a.ContNo "
                     +                               "and PolNo = MainPolNo)) "
                     +              "and StateType = 'Terminate' "
                     +              "and State = '1' "
                     +              "and (('" + sEdorItemAppDate + "' >= StartDate and '" + sEdorItemAppDate + "' <= EndDate) or "
                     +                  "('" + sEdorItemAppDate + "' >= StartDate and EndDate is null)) "
                     +              "and StateReason in ('01', '05', '06', '09')))) "
                     +  "and a.ContNo in "
                     +      "(select ContNo from LCAppnt where AppntNo = '" + sOtherNo + "' "
                     +      "union "
                     +      "select ContNo from LCInsured where InsuredNo = '" + sOtherNo + "') ";
                     */
        }
        else if (sOtherNoType == "3")
        {
        	  mySql.setSqlId("PEdorAppSql34");
        	  mySql.addSubPara(sOtherNo);         	
            //QuerySQL += "and a.ContNo = '" + sOtherNo + "' ";
        }
        if (sAppType != null && sAppType != "6" && sAppType != "7")
        {
          	mySql.addSubPara(sEdorItemAppDate);     
           // QuerySQL += "and a.CustomGetPolDate <= '" + sEdorItemAppDate + "'";
        }

        try
        {
            arrResult = easyExecSql(mySql.getString(), 1, 0);
        }
        catch (ex)
        {
            alert("警告：查询需要输入密码的保单信息出现异常！ ");
            return false;
        }
        if (arrResult != null)
        {
            //弹出页面输入密码
            var VerifyPwdURL = "PEdorAppPwdVerifyMain.jsp";
            var VerifyPwdParam = "EdorAcceptNo=" + sEdorAcceptNo + "&OtherNo=" + sOtherNo + "&OtherNoType=" + sOtherNoType + "&EdorItemAppDate=" + sEdorItemAppDate + "&AppType=" + sAppType;
            //var VerifyPwdFlag = showModalDialog(VerifyPwdURL + "?" + VerifyPwdParam, window, "status=0; help=0; close=0; dialogWidth=850px; dialogHeight=600px");
			var name='提示';   //网页名称，可为空; 
			var iWidth=800;      //弹出窗口的宽度; 
			var iHeight=600;     //弹出窗口的高度; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 

			var VerifyPwdFlag=window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
            if (!VerifyPwdFlag)
            {
                return false;
            }
        }
    }
    return true;
}

//XinYQ added on 2006-11-09 : 撤销所有保全项目之后清除数据显示
function clearLastEdorInfo()
{
    if (EdorItemGrid.mulLineCount <= 0)
    {
        try
        {
            document.all("divPolInfo").style.display = "none";
            document.all("divPolGrid").style.display = "none";
            document.all("divInsuredInfo").style.display = "none";
            document.all("divInsuredGrid").style.display = "none";
            document.getElementsByName("EdorType")[0].value = "";
            document.getElementsByName("EdorTypeName")[0].value = "";
            document.getElementsByName("EdorValiDate")[0].value = "";
        }
        catch (ex) {}
    }
}

/**
* 核保状态查询
*/
function  UWQuery()
{
    var pEdorAcceptNo=fm.EdorAcceptNo.value;
    window.open("./EdorErrorUWQueryMain.jsp?EdorAcceptNo="+pEdorAcceptNo,"window1");
}

/*********************************************************************
 *  添加保全项的校验
 *  描述: 保全项的校验
 *  参数  ：  无
 *  返回值：  布尔型
 *********************************************************************
 */
function chkEdorType()
{
		var tEdorType = document.all('EdorType').value;//保全类型
		//alert(tEdorType);
		if(tEdorType == "LR")
		{
			//alert("进入LR");
			var tLost = document.all('Lost').value;
			if(tLost=="挂失")
			{
				return confirm("该保单处于挂失状态，进行该保全项目将自动为其解挂，是否继续操作？");
			}
		}
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql36");
    mySql.addSubPara(fm.OtherNo.value); 
    /*		
		  var rSQL = " select contno   "
			                  +"	 from lpconttempinfo    "
			                  +"	 where "
			                  +"	edortype='CM' and state = '0'  "
			                  +"		and contno = '"+fm.OtherNo.value+"'";
			                  */
  	   var isCM;
  		 try
  		  {
  			    isCM = easyExecSql(mySql.getString(), 1, 0);
  		  }
  		 catch(ex)
  		  {
  			  alert("客户重要资料变更临时表出现异常！");
  			  return false;
  		  }
  	   if (isCM !=null )
  		 {
   			   if(!canApply(tEdorType))
   			   {
		          return false;
   			   }

  	   }
		
		
		//客户层重要资料变更只能选择IC项目
		//alert(fm.LoadFlag.value);
		if(tPreLoanFlag=="CM" && tEdorType != "IC")
		{
			alert("客户层重要资料变更，请选择被保人重要资料变更(IC)");
			return false;
		}
		if(tEdorType == "CT")
		{
			//非阻断性的提示，被保人与投保人是非直系亲属的关系。
			/*var relationSQL = " select distinct 1 "
    					 + " from lcinsured i "
 							 + " where i.contno = '" + fm.ContNoApp.value + "'"
   						 + " and i.relationtoappnt in ('25', '26', '27', '28', '29', '30')";
   						 */
	  		var ret;
       mySql=new SqlClass();
       mySql.setResourceName("bq.PEdorApp");
       mySql.setSqlId("PEdorAppSql37");
       mySql.addSubPara(fm.ContNoApp.value);
    
	  		try
	  		{
	  			ret = easyExecSql(mySql.getString(), 1, 0);
	  		}
	  		catch(ex)
	  		{
	  			alert("查询校检投保人与被保人关系时出现异常！");
	  			return false;
	  		}
	  		if (ret != null && ret[0][0] == "1")
	  		{
	  			return confirm("该保单下的投保人与被保人存在非直系亲属关系，是否继续操作？");	
	  		}
			//return false;
		}
		
		if(tEdorType == "XT" || tEdorType == "XS")
		{
			//非阻断性的提示，险种处于犹豫期，是否进行协议退保。
			 /*var relationSQL = " select case when (select sysdate-customgetpoldate "
    					 + " from lccont  "
 							 + " where contno = '" + fm.OtherNo.value + "')<=10 then 0 else 1 end  from dual";
 							 */
	  		var ret;
	  		mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorApp");
        mySql.setSqlId("PEdorAppSql38");
        mySql.addSubPara(fm.OtherNo.value);
	  		try
	  		{
	  			ret = easyExecSql(mySql.getString(), 1, 0);
	  		}
	  		catch(ex)
	  		{
	  			alert("查询保单回单日期异常");
	  			return false;
	  		}
	  		if (ret != null && ret[0][0] == "0")
	  		{
	  			if (tEdorType == "XT")
	  			return confirm("保单处于犹豫期内，是否进行协议退保!");	
	  			
	  			//add by jiaqiangli 2009-05-14
	  			if (tEdorType == "XS")
	  			return confirm("保单处于犹豫期内，是否进行协议减保!");
	  		}
			//return false;
		}
		
		//客户资料变更的保单层操作，只允许进行被保人操作，如职业变更
		if(tEdorType=="IO")
		{
	        var tInsuredType; 
	        if (InsuredGrid.getSelNo()>0)
	        {
	          tInsuredType=InsuredGrid.getRowColData(InsuredGrid.getSelNo()-1, 1); 
	          //alert(tInsuredType);
	          if(tInsuredType=='投保人')
	          {
	             alert("此保全项目变更只能对被保人进行操作，请重新选择或则返回");
	  			     return false;
	          	}
	        }
		}
		if(tEdorType == "CM")
		{     
        	  var tInsuredNo; 
            if (InsuredGrid.getSelNo()>0)
            {
                 tInsuredNo=InsuredGrid.getRowColData(InsuredGrid.getSelNo()-1, 2); 
                 //alert(tInsuredNo);
                 			//客户层重要资料先要进行CM操作
                 			
              mySql=new SqlClass();
             mySql.setResourceName("bq.PEdorApp");
             mySql.setSqlId("PEdorAppSql39");
             mySql.addSubPara(tInsuredNo);   
             mySql.addSubPara(tInsuredNo);       
             /*      			
			       var rCMSQL = " select ContNo   "
			                  +"	 from lpconttempinfo    "
			                  +"	 where "
			                  +"		 state = '0'  "
			                  +"		and (insuredno = '"+tInsuredNo+"' or AppntNo='"+tInsuredNo+"')";
			                  */
	  	    	var result;
	  		    try
	  		    {
	  			    result = easyExecSql(mySql.getString(), 1, 0);
	  		    }
	  		    catch(ex)
	  		    {
	  			  alert("查询客户重要资料变更临时表出现异常！");
	  			  return false;
	  		    }
	  	   	    if (result != null )
	  		    {
	  		   	     var tTempContNo="";
		  		   	 for(var t=0;t<result.length;t++)
		  		   	 {
		  		   	 	   tTempContNo+=result[t][0]+",";
		  		   	 }
	  		   	 	 tTempContNo=tTempContNo.substr(0,tTempContNo.lastIndexOf(","));
	   			     alert("客户重要资料变更，本客户下尚有如下保单"+tTempContNo+"未进行完保单层客户资料变更，请继续处理完这些保单");
	  			     return false;
	  	        }
            }
            //alert(InsuredGrid.getSelNo());
            if (InsuredGrid.getSelNo()>0)
	         {
	               tInsuredNo=InsuredGrid.getRowColData(InsuredGrid.getSelNo()-1, 2); 
	    
	                 			//客户层重要资料先要进行CM操作
	         mySql=new SqlClass();
           mySql.setResourceName("bq.PEdorApp");
           mySql.setSqlId("PEdorAppSql40");
           mySql.addSubPara(tInsuredNo);          			
	       /*          			
				   var rCMSQL = " select ContNo   "
				                  +"	 from lpedoritem    "
				                  +"	 where "            //查询此客户下正在操作的CM的项目，用状态作为评定标准
				                  +"		 EdorState not in  ('0','4','8','9','d','c','b') "
				                  +"		 and insuredno = '"+tInsuredNo+"' and edortype='CM'";
				                  */
		  	       var result;
		  		   try
		  		   {
		  			    result = easyExecSql(mySql.getString(), 1, 0);
		  		   }
		  		   catch(ex)
		  		   {
		  			  alert("查询客户重要资料变更批改表出现异常！");
		  			  return false;
		  		   }
		  	   	   if (result != null )
		  		   {
		  		   	 var tTempContNo="";
		  		   	 for(var t=0;t<result.length;t++)
		  		   	 {
		  		   	 	   tTempContNo+=result[t][0]+",";
		  		   	 }
		  		   	 tTempContNo=tTempContNo.substr(0,tTempContNo.lastIndexOf(","));
		   			 alert("客户重要资料变更，本客户下已有如下保单"+tTempContNo+"正在进行保单层客户资料(CM)变更，请返回");
		  			 return false;
		  	       }
	         }
			
			
		}
		if(tEdorType == "IC")
		{
      mySql=new SqlClass();
      mySql.setResourceName("bq.PEdorApp");
      mySql.setSqlId("PEdorAppSql41");
      mySql.addSubPara(fm.OtherNo.value); 
      /*			
			var rCMSQL = " select edorno   "
			                  +"	 from lpconttempinfo    "
			                  +"	 where "
			                  +"		edortype='CM' and state = '0'  "
			                  +"		and contno = '"+fm.OtherNo.value+"'";
			                  */
	  	    var result;
	  		try
	  		 {
	  			result = easyExecSql( mySql.getString(), 1, 0);
	  		 }
	  		catch(ex)
	  		 {
	  			alert("客户重要资料变更临时表出现异常！");
	  			return false;
	  		 }
//	  	    if (result == null )
//	  		{
//	   			 alert("保单层重要资料变更，本保单尚未进行客户层资料变更，请先要进行客户层重要资料变更( CM )操作");
//	  			 return false;
//	  	    }
//	  	    if (result != null  && tPreLoanFlag!="CM" )
//	  		{
//	   			 alert("保单层重要资料变更，本保单正在进行保单层客户资料变更，请从保单层客户资料变更菜单进行申请");
//	  			 return false;
//	  	    }
		}
		
		if(tEdorType != "XT" && tEdorType != "CT")
		{
       mySql=new SqlClass();
      mySql.setResourceName("bq.PEdorApp");
      mySql.setSqlId("PEdorAppSql42");
      mySql.addSubPara(fm.MissionID.value); 
      
			/*var sSQL = "select 1 from dual where exists( select 1 from lwmission where MissionID = '"+fm.MissionID.value+"' and missionprop24 = 'XC')";*/
			var sRet = easyExecSql(mySql.getString());
			if(sRet != null){
    		var xcState = sRet[0][0];	
    		if("1" == xcState)
    		{
    			alert("本次保全由拒保功能发起！不能添加协议退保（XT）和退保（CT）以外的保全项!");
    			return false;
    		}
			}
		}
		if(tEdorType == "CS"){
			try{
				var parry = top.fraPic.arrPicName;
				if(parry == null){
					alert("无扫描状态下不能进行变更/补签名！");
					return false;
				}
			}catch(ex){
				alert("无扫描状态下不能进行变更/补签名！");
				return false;
			}
		}
		return true;
}
/**
为实现客户层重要资料变更添加
*/
function initCM()
{
	if(fm.LoadFlag.value=="CM")
	{
		tPreLoanFlag="CM";
		fm.LoadFlag.value="edorApp";
	  document.all('EdorType').value="IC";
	  document.all('EdorTypeName').value="被保人重要资料变更";
	  document.all('DisplayType').value ='1';	 
	  document.all('EdorType').readOnly=true;
    document.all('EdorTypeName').readOnly=true;
    
    document.all("divPolInfo").style.display = "none";
    document.all("divPolGrid").style.display = "none";
    document.all("divInsuredInfo").style.display = "";
    document.all("divInsuredGrid").style.display = "none";	  
  }
}
/**
对于操作客户层资料变更的项目处理完第一步后，没有处理完第二步而可以进行的保全项目，这些项目
必须是不要重算的，简单的保全项目，在LDCode中描叙此时不可以操作的保全项目，如CT,XT,PT,IO,LN,AA,NS等等
*/
function canApply(tEdorType)
{
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql23");
    mySql.addSubPara(tEdorType); 	
	  /*
			var rCMQL = " select   code "
			                  +"	 from ldcode    "
			                  +"	 where "
			                  +"		 codetype = 'cm'  "
			                  +"		and codename = '"+tEdorType+"'";
		*/	                  
  	   var result;
  		 try
  		  {
  			    result = easyExecSql(mySql.getString(), 1, 0);
  		  }
  		 catch(ex)
  		  {
  			  alert("查询失败");
  			  return false;
  		  }
  	   if (result != null )
  		 {
  			  alert("客户重要资料变更尚未完成，暂时不能操作"+tEdorType+"！");
  			  return false;
  	   }else
  	   	{
  	   		return true;
  	   		}
  	   		
  	 return true; 		
	
}

function EdorNoticeSend()
{
		  if (fm.PEdorState.value == 2)
      {
        alert("保全受理已经申请确认");
        return ;
      }
      if (fm.PEdorState.value == 'c')
    {
        alert("保全受理已经终止");
        return;
    }
	  var EdorAcceptNo    = document.all('EdorAcceptNo').value;
    //var strSQL = "select 'x' from lpedoritem where  EdorAcceptNo= '"+EdorAcceptNo+"'";
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql48");
    mySql.addSubPara(EdorAcceptNo); 	    
    var sResult;
    sResult = easyExecSql(mySql.getString(), 1, 0,"","",1);
    if(sResult ==null){
        alert("查询保全项目失败,请先添加保全项目再进行保全函件处理!");
        return;
    }

	    var pEdorAcceptNo=fm.EdorAcceptNo.value;
	    //alert(fm.MissionID.value);
	    var varSrc="&EdorAcceptNo=" + pEdorAcceptNo + "&MissionID=" + fm.MissionID.value + "&SubMissionID=" +fm.SubMissionID.value;
	    //alert(varSrc);
	    var newWindow = OpenWindowNew("../bq/EdorNoticeFrame.jsp?Interface= ../bq/EdorNoticeInput.jsp" + varSrc,"保全审核","left");
}

/**
 * 银行帐号过虑
 */
function checkBank(tBankCode,tBankAccNo)
{
	if(tBankCode.value.length>0 && tBankAccNo.value.length>0)
	{
		if(!checkBankAccNo(tBankCode.value,tBankAccNo.value))
		{
			tBankAccNo.value = "";
			return false;
		}
	}
}

function ShowApproveInfo(){
	  var tApproveContent = ApproveTrackGrid.getRowColData(ApproveTrackGrid.getSelNo()-1,7); 
    fm.ApproveContent.value=tApproveContent;
    divAprIdea.style.display = '';

}
