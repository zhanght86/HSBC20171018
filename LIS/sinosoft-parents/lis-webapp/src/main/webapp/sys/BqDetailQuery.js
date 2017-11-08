//程序名称：
//程序功能：个人保全查询
//创建日期：2005-06-22 15:15:22
//更新记录：  更新人    更新日期     更新原因/内容

var showInfo;
var mDebug="1";
var aEdorFlag='0';
var mEdorType;
var turnPage = new turnPageClass();
var mflag = "";  //保单、客户查询标识
var mySql = new SqlClass();


/*********************************************************************
 *  页面层显示控制
 *  描述: 页面层显示控制
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initDiv()
{
//alert(0);
    var EdorAcceptNo = document.all('EdorAcceptNo').value;
    if(EdorAcceptNo == null || EdorAcceptNo == "")
    {
        alert("保全受理号为空！");
        return;
    }
    var strSQL;

    //查询工作流保全申请确认节点
   /* strSQL =  " select OtherNo, OtherNoType, EdorAppName, AppType, EdorAppDate , EdorState,"
            + " (select codename from ldcode a where trim(a.codetype) = 'edorstate' and trim(a.code) = trim(edorstate)),"
            + " bankcode, bankaccno, accname, "
            + " (select codename from ldcode b where trim(b.codetype) = 'edornotype' and trim(b.code) = trim(OtherNoType)),"
            + " (select codename from ldcode where trim(codetype) = 'edorapptype' and trim(code) = trim(AppType)),"
            + " (select codename from ldcode where trim(codetype) = 'bank' and trim(code) = trim(bankcode)), "
            + " paygetname, personid, "
            + " BehalfName,BehalfIDNo,BehalfPhone,BehalfCode,(select codename from ldcode where codetype='idtype' and code=BehalfIDType) "
            + ",EdorAppPhone,BehalfCodeCom,SwitchChnlName "
            + " from LPEdorApp "
            + " where EdorAcceptNo = '" + EdorAcceptNo + "' ";*/
	mySql = new SqlClass();
	mySql.setResourceName("sys.BqDetailQuerySql");
	mySql.setSqlId("BqDetailQuerySql1");
	mySql.addSubPara(EdorAcceptNo); 
    var brr = easyExecSql(mySql.getString());

    if ( brr )  //已经申请保存过
    {
        //alert("已经申请保存过");
        //hasSaved = "Y";
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.OtherNo.value     = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.OtherNoType.value = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.EdorAppName_Read.value = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.AppType.value     = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.EdorAppDate_Read.value = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.PEdorState.value  = brr[0][5];
        brr[0][6]==null||brr[0][6]=='null'?'0':fm.PEdorStateName_Read.value  = brr[0][6];

        brr[0][7]==null||brr[0][7]=='null'?'0':fm.BankCode.value    = brr[0][7];
        brr[0][8]==null||brr[0][8]=='null'?'0':fm.BankAccNo.value   = brr[0][8];
        brr[0][9]==null||brr[0][9]=='null'?'0':fm.AccName.value     = brr[0][9];
        brr[0][10]==null||brr[0][10]=='null'?'0':fm.OtherNoType_Read.value    = brr[0][10];
        brr[0][11]==null||brr[0][11]=='null'?'0':fm.AppType_Read.value   = brr[0][11];
        brr[0][12]==null||brr[0][12]=='null'?'0':fm.BankName.value     = brr[0][12];
        brr[0][13]==null||brr[0][13]=='null'?'0':fm.PayGetName_Read.value     = brr[0][13];
        brr[0][14]==null||brr[0][14]=='null'?'0':fm.PersonID_Read.value     = brr[0][14];
        
        brr[0][15]==null||brr[0][15]==''?'':fm.BfName_Read.value     = brr[0][15];
        brr[0][16]==null||brr[0][16]==''?'':fm.BfIDNo_Read.value     = brr[0][16];
        brr[0][17]==null||brr[0][17]==''?'':fm.BfPhone_Read.value     = brr[0][17];
        brr[0][18]==null||brr[0][18]==''?'':fm.BfAgentCode_Read.value     = brr[0][18];
        brr[0][19]==null||brr[0][19]==''?'':fm.BfIDType_Read.value     = brr[0][19];
        
        brr[0][20]==null||brr[0][20]==''?'':fm.CustomerPhone_Read.value     = brr[0][20];
        brr[0][21]==null||brr[0][21]==''?'':fm.ManageCom_Read.value     = brr[0][21];
        brr[0][22]==null||brr[0][22]==''?'':fm.InternalSwitchChnlName_Read.value     = brr[0][22];

        fm.EdorAcceptNo_Read.value= fm.EdorAcceptNo.value;
        fm.OtherNo_Read.value     = fm.OtherNo.value;
        //fm.OtherNoType_Read.value = fm.OtherNoName.value;
        //fm.EdorAppName_Read.value = fm.EdorAppName.value;
        //fm.AppType_Read.value     = fm.AppTypeName.value;
        //fm.EdorAppDate_Read.value = fm.EdorAppDate.value;
        //fm.PEdorStateName_Read.value = fm.PEdorStateName.value;
        //fm.PayGetName_Read.value = fm.PayGetName.value;
        //fm.PersonID_Read.value = fm.PersonID.value;
        
        
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

        if(fm.OtherNoType.value == "1")  //个人客户号
        {
            displayCustomerInfo(fm.OtherNo.value);  //查询客户详细信息
            getEdorItemGrid();                  //查询保全项目列表详细信息
            divCustomer.style.display='';       //客户信息
            divCont.style.display='none';       //保单信息
            divEdorItemInfo.style.display='';   //保全项目信息

        }
        if(fm.OtherNoType.value == "3")  //个人保单号
        {
            displayContInfo(fm.OtherNo.value);  //查询保单详细信息
            getEdorItemGrid();                      //查询保全项目列表详细信息
            divCustomer.style.display='none';   //客户信息
            divCont.style.display='';           //保单信息
            divEdorItemInfo.style.display='';   //保全项目信息

        }

    }
    else
    {
        alert("没有该保全受理信息");

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

    /*strSQL =  " select CustomerNo, Name, Sex, Birthday, "
            + " IDType, IDNo, Marriage, Health, "
            + " RgtAddress, VIPValue, Salary, GrpName "
            + " from LDPerson "
            + " where customerno = '" + CustomerNo + "'";*/
	mySql = new SqlClass();
	mySql.setResourceName("sys.BqDetailQuerySql");
	mySql.setSqlId("BqDetailQuerySql2");
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
    }
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

    /*strSQL =  " select ContNo, AppntName, InsuredName, Prem, Amnt, "
            + " AgentCode, GetPolDate, CValiDate, PaytoDate, Mult "
            + " from lccont "
            + " where ContNo = '" + ContNo + "'";*/
	mySql = new SqlClass();
	mySql.setResourceName("sys.BqDetailQuerySql");
	mySql.setSqlId("BqDetailQuerySql3");
	mySql.addSubPara(ContNo); 
    var brr = easyExecSql(mySql.getString());

    if ( brr )
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.ContNoApp.value   = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.AppntName.value   = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.InsuredName.value = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.Prem.value        = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.Amnt.value        = brr[0][4];

        brr[0][5]==null||brr[0][5]=='null'?'0':fm.AgentCode.value   = brr[0][5];
        //brr[0][6]==null||brr[0][6]=='null'?'0':fm.GetPolDate.value    = brr[0][6];
        brr[0][7]==null||brr[0][7]=='null'?'0':fm.CValiDate.value   = brr[0][7];
        brr[0][8]==null||brr[0][8]=='null'?'0':fm.PaytoDate.value   = brr[0][8];
        brr[0][9]==null||brr[0][9]=='null'?'0':fm.Mult.value   = brr[0][9];
    }
    displayContStateInfo(ContNo);  //查询保单状态详细信息

    //查询主险交费对应日
   // strSQL =  " select PaytoDate from lcpol where polno = mainpolno and contno = '" + ContNo + "' ";
   mySql = new SqlClass();
	mySql.setResourceName("sys.BqDetailQuerySql");
	mySql.setSqlId("BqDetailQuerySql4");
	mySql.addSubPara(ContNo); 
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
    initRiskGrid();
   /* var strSQL = " select RiskCode," +
                 " (select RiskShortName from LMRisk where LMRisk.RiskCode = c.RiskCode)," +
                 " InsuredName, Amnt, mult ," +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= (select sysdate from dual) and payenddate >= (select sysdate from dual) and p.polno = c.polno and length(dutycode)=6 and p.payplantype in ('0')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= (select sysdate from dual) and payenddate >= (select sysdate from dual) and p.polno = c.polno and p.payplantype in ('01', '03')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= (select sysdate from dual) and payenddate >= (select sysdate from dual) and p.polno = c.polno and p.payplantype in ('02', '04')), 0), " +
                  " polno, paytodate " +
                 " from LCPol c where appflag = '1' and ContNo='" + ContNo + "'"
                 ;*/
	mySql = new SqlClass();
	mySql.setResourceName("sys.BqDetailQuerySql");
	mySql.setSqlId("BqDetailQuerySql5");
	mySql.addSubPara(ContNo); 
    brrResult = easyExecSql(mySql.getString());
    if (brrResult)
    {
        displayMultiline(brrResult,RiskGrid);
    }
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
    fm.ContNo.value =       EdorItemGrid.getRowColData(tSelNo, 6);
    fm.InsuredNo.value =    EdorItemGrid.getRowColData(tSelNo, 7);
    fm.PolNo.value =        EdorItemGrid.getRowColData(tSelNo, 8);
    fm.EdorItemAppDate.value=EdorItemGrid.getRowColData(tSelNo, 10);
    fm.EdorAppDate.value =  EdorItemGrid.getRowColData(tSelNo, 10);
    fm.EdorValiDate.value = EdorItemGrid.getRowColData(tSelNo, 11);
    fm.MakeDate.value =     EdorItemGrid.getRowColData(tSelNo, 14);
    fm.MakeTime.value =     EdorItemGrid.getRowColData(tSelNo, 15);
    fm.EdorItemState.value =EdorItemGrid.getRowColData(tSelNo, 20);
    fm.EdorType.value =     EdorItemGrid.getRowColData(tSelNo, 21);

    document.all('ContType').value ='1';
    fm.CustomerNoBak.value = fm.InsuredNo.value;
    fm.ContNoBak.value = fm.ContNo.value;
}

/*********************************************************************
 *  查询保全项目，写入MulLine
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getPEdorItemGrid()
{
    initEdorItemGrid();
    var tEdorAcceptNo = document.all('EdorAcceptNo').value;

    if(tEdorAcceptNo!=null)
    {
       /* var strSQL =  " select EdorAcceptNo, EdorNo,"
                    + " EdorType, DisplayType, "
                    + " GrpContNo, ContNo, InsuredNo, PolNo,"
                    + " EdorAppDate, EdorValiDate, "
                    + " (select CodeName from LDCode where codetype = 'edorappreason' and trim(code) = trim(appreason)), appreason, "
                    + " GetMoney, MakeDate, MakeTime, ModifyDate, Operator, "
                    + " LDCode.CodeName , EdorState "
                    + " from LPEdorItem, LDCode "
                    + " where EdorAcceptNo='" + tEdorAcceptNo + "'"
                    + " and LDCode.codetype = 'edorstate' and trim(LDCode.code) = trim(LPEdorItem.EdorState) "
                    + " order by makedate, maketime ";
                    //+ " and ContNo = '" + LCContGrid.getRowColData(tSelNo,1) + "'";
*/
        //turnPage.queryModal(strSQL, EdorItemGrid);
        mySql = new SqlClass();
	mySql.setResourceName("sys.BqDetailQuerySql");
	mySql.setSqlId("BqDetailQuerySql6");
	mySql.addSubPara(tEdorAcceptNo); 
        arrResult = easyExecSql(mySql.getString());
        if (arrResult)
        {
            displayMultiline(arrResult,EdorItemGrid);
        }

    }
}


/*********************************************************************
 *  查询保全项目，写入MulLine
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function getEdorItemGrid()
{
    initEdorItemGrid();
    var tEdorAcceptNo = document.all('EdorAcceptNo').value;
    var tOtherNoType = document.all('OtherNoType').value;
    //alert("tOtherNoType="+tOtherNoType);
    if(tEdorAcceptNo!=null)
    {

        if (tOtherNoType=='3') //个人保单号申请
        {
          /*  var strSQL =  " select EdorAcceptNo, EdorNo,"
                        + " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype) and appobj in ('I','B') and rownum = 1), "
                        + " DisplayType, "
                        + " GrpContNo, ContNo, InsuredNo, PolNo,"
                        + " (select m.riskname from lcpol b ,lmrisk m  where b.polno =LPEdorItem.polno and b.riskcode=m.riskcode),"
                        + " EdorAppDate, EdorValiDate, "
                        + " (select CodeName from LDCode a where a.codetype = 'edorappreason' and trim(a.code) = trim(appreason)), appreason, "
                        + " GetMoney, MakeDate, MakeTime, ModifyDate, Operator, "
                        + " (select CodeName from LDCode b where b.codetype = 'edorstate' and trim(b.code) = trim(edorstate)),"
                        + " EdorState,edortype"
                        + " from LPEdorItem "
                        + " where EdorAcceptNo='" + tEdorAcceptNo + "'"
                        + " order by makedate asc, maketime asc";
                        //+ " and ContNo = '" + LCContGrid.getRowColData(tSelNo,1) + "'";
*/
            //turnPage.queryModal(strSQL, EdorItemGrid);
           mySql = new SqlClass();
			mySql.setResourceName("sys.BqDetailQuerySql");
			mySql.setSqlId("BqDetailQuerySql7");
			mySql.addSubPara(tEdorAcceptNo);  
            arrResult = easyExecSql(mySql.getString());
            if (arrResult)
            {
                displayMultiline(arrResult,EdorItemGrid);
            }
        }
        else if (tOtherNoType=='1') //个人客户号申请
        {
        /*    var strSQL =  " select distinct EdorAcceptNo, '', "
                        + " (select distinct edorcode||'-'||edorname from lmedoritem m where  trim(m.edorcode) = trim(edortype) and appobj in ('I','B') and rownum = 1), "
                        + " DisplayType, '', (select otherno from lpedorapp where lpedorapp.edoracceptno=lpedoritem.edoracceptno), InsuredNo, PolNo,"
                        + " (select m.riskname from lcpol b ,lmrisk m  where b.polno =LPEdorItem.polno and b.riskcode=m.riskcode),"
                        + " EdorAppDate, EdorValiDate, "
                        + " (select CodeName from LDCode where codetype = 'edorappreason' and trim(code) = trim(appreason)), "
                        + " appreason, '', MakeDate, MakeTime, '', Operator, "
                        + " (select CodeName from LDCode where codetype = 'edorstate' and trim(code) = trim(edorstate)),"
                        + " EdorState,edortype "
                        + " from LPEdorItem "
                        + " where EdorAcceptNo='" + tEdorAcceptNo + "'"
                        + " order by makedate asc, maketime asc";

*/
            //turnPage.queryModal(strSQL, EdorItemGrid);
			 mySql = new SqlClass();
			mySql.setResourceName("sys.BqDetailQuerySql");
			mySql.setSqlId("BqDetailQuerySql8");
			mySql.addSubPara(tEdorAcceptNo);  
            arrResult = easyExecSql(mySql.getString());
            if (arrResult)
            {
                displayMultiline(arrResult,EdorItemGrid);
            }
            }
     }
   }
/*********************************************************************
 *  返回
 *  描述: 页面层显示控制
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function returnParent()
{
    //top.opener.initSelfGrid();
    //top.opener.easyQueryClickSelf();
    top.close();
}

    //保全查询
function QueryEdorClick()
{
    var arrReturn = new Array();
    var cEdorAcceptNo = document.all('EdorAcceptNo').value;
    var tSel = EdorItemGrid.getSelNo();
    if( tSel == 0 || tSel == null )
    {
        alert( "请先选择一条保全项目记录。" );
        return;
    }
    else
    {
        var tEdortype = EdorItemGrid.getRowColData(tSel - 1,21);
        if(tEdortype==0||tEdortype==null)
        {
            alert("本次保全申请没有保全项目，请另选择保全申请");
            return false;
        }
        
     //var str = "select 1 from lpedorapp where EdorAcceptNo='"+cEdorAcceptNo+"' and edorstate='0'";
     mySql = new SqlClass();
			mySql.setResourceName("sys.BqDetailQuerySql");
			mySql.setSqlId("BqDetailQuerySql9");
			mySql.addSubPara(cEdorAcceptNo); 
     var arrResult = easyExecSql(mySql.getString());
     //alert(arrResult);
     if(arrResult != null){
          alert("此次保全申请已经确认，不能进行明细查询，请进行批单查询！");
          return;
     }
        
        var cEdorType = EdorItemGrid.getRowColData(tSel - 1,1);
        //var cContNo = EdorItemGrid.getRowColData(tSel - 1,2);
        var cPolNo = EdorItemGrid.getRowColData(tSel - 1,3);
          //if (cEdorType == ""||cContNo == "")
            //return;
        //var tEdortype = EdorItemGrid.getRowColData(tSel - 1,21);
       // window.open("../bq/PEdorType"+tEdortype+"Input.jsp?splflag=1");
          //    	alert(tEdortype);
       if(tEdortype=='NS')
       {
       if (fm.EdorItemState.value == "0")
       {
           window.open("../bqs/PEdorType" +tEdortype+ ".jsp?ContNo="+fm.ContNoApp.value, "PEdorType" +tEdortype, 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
       }
       else
       {
           window.open("../bq/PEdorType" +tEdortype+ ".jsp?ContNo="+fm.ContNoApp.value, "PEdorType" +tEdortype, 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
       }      	      	
       	}else
       	{
       
       if (fm.EdorItemState.value == "0")
       {
           window.open("../bqs/PEdorType" +tEdortype+ ".jsp", "PEdorType" +tEdortype, 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
       }
       else
       {
           window.open("../bq/PEdorType" +tEdortype+ ".jsp", "PEdorType" +tEdortype, 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
       }
      }
    }
}

    //保全查询，新的，测试用
function QueryEdorClickNew()
{
    var arrReturn = new Array();
    var cEdorAcceptNo = document.all('EdorAcceptNo').value;
    var tSel = EdorItemGrid.getSelNo();
    if( tSel == 0 || tSel == null )
    {
        alert( "请先选择一条保全项目记录。" );
        return;
    }
    else
    {
        var tEdortype = EdorItemGrid.getRowColData(tSel - 1,21);
        if(tEdortype==0||tEdortype==null)
        {
            alert("本次保全申请没有保全项目，请另选择保全申请");
            return false;
        }
        var cEdorType = EdorItemGrid.getRowColData(tSel - 1,1);
        //var cContNo = EdorItemGrid.getRowColData(tSel - 1,2);
        var cPolNo = EdorItemGrid.getRowColData(tSel - 1,3);
          //if (cEdorType == ""||cContNo == "")
            //return;
        //var tEdortype = EdorItemGrid.getRowColData(tSel - 1,21);
       // window.open("../bq/PEdorType"+tEdortype+"Input.jsp?splflag=1");
       if (fm.EdorItemState.value == '0' || fm.EdorItemState.value == '6')
       {
            //QueryEdorRecipt();
            window.open("../bqs/PEdorType" +tEdortype+ ".jsp", "PEdorType" +tEdortype, 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
       }
       else
        {
            window.open("../bq/PEdorType" +tEdortype+ ".jsp", "PEdorType" +tEdortype, 'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
        }
    }
}

function QueryPEdor()
{
    var newWindow = OpenWindowNew("../bq/QueryPEdor.jsp?Interface= ../sys/BqDetailQuery.jsp","保全批改查询","left");
}
function QueryEdorRecipt()
{

    var newWindow = OpenWindowNew("../f1print/AppEndorsementF1PJ1.jsp");
     fm.action = "../f1print/AppEndorsementF1PJ1.jsp";
   fm.target="f1print";
   document.getElementById("fm").submit();
}
function QueryPhoto()
{
     var EdorAcceptNo    = document.all('EdorAcceptNo').value;
     var tUrl="../bq/ImageQueryMain.jsp?BussNo="+EdorAcceptNo+"&BussType=BQ";
     OpenWindowNew(tUrl,"保全扫描影像","left");
//     var MissionID       = document.all('MissionID').value;
//   var SubMissionID    = document.all('SubMissionID').value;
//     var str = "select docid from es_doc_relation where bussno = '" + EdorAcceptNo + "' and busstype = 'BQ' and relaflag = '0'";
//     var arrResult = easyExecSql(str);
//     if(arrResult == null){
//          alert("此次保全申请没有相关扫描影像资料！");
//          return;
//     }
//     var PrtNo = arrResult[0][0];
//     var tResult = easyExecSql("select a.codealias from ldcode a,es_doc_relation b where a.codetype = 'bqscan' and trim(a.code) = trim(b.subtype) and b.busstype = 'BQ' and b.bussno = '"+EdorAcceptNo+"'", 1, 0);
//     if(tResult == null){
//          alert("查询保全扫描子业务类型编码失败！");
//          return;
//     }
//     var varSrc = "&ScanFlag = 1&prtNo=" + PrtNo + "&EdorAcceptNo=" + EdorAcceptNo + "&MissionID=" + MissionID + "&SubMissionID=" + SubMissionID;// + "&SubType=" + tResult[0][0];
//     var newWindow = OpenWindowNew("../bq/EdorScan.jsp?" + varSrc,"保全扫描影像","left");
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
    var tDate = fm.EdorAppDate_Read.value;
    var PolNo = getMainPol(ContNo);
   /* var strsql = "select trim(statetype), statereason from lccontstate where state = '1' "
                   + " and startdate <= '"+tDate+"' and (enddate is null or enddate >= '"
                   + tDate +"') and contno = '"+ContNo+"' and polno in('"+PolNo+"','000000') ";*/
     mySql = new SqlClass();
			mySql.setResourceName("sys.BqDetailQuerySql");
			mySql.setSqlId("BqDetailQuerySql10");
			mySql.addSubPara(tDate); 
			mySql.addSubPara(tDate); 
			mySql.addSubPara(ContNo); 
			mySql.addSubPara(PolNo); 
    var brr = easyExecSql(mySql.getString());
    fm.Available.value = isInArray(brr,"Available")?"失效":"有效";
    fm.Lost.value      = isInArray(brr,"Lost")?"挂失":"未挂失";
    fm.PayPrem.value   = isInArray(brr,"PayPrem")?"自垫":"未自垫";
    fm.Loan.value      = isInArray(brr,"Loan")?"贷款":"未贷款";
    fm.BankLoan.value  = isInArray(brr,"BankLoan")?"质押银行贷款":"未质押银行贷款";
    fm.RPU.value       = isInArray(brr,"RPU")?"减额缴清":"未减额缴清";
    fm.Terminate.value = isInArray(brr,"Terminate")?"终止":"未终止";
    if (isInArray(brr,"Terminate"))
    {
        //显示终止原因
        var TerminateReason = getStateReasonCN(getStateReason(brr,"Terminate"));
        fm.Terminate.value = TerminateReason;
    }
    else
    {
      //  var strsql = " select appflag from lccont where contno = '"+ContNo+"'";
        mySql = new SqlClass();
			mySql.setResourceName("sys.BqDetailQuerySql");
			mySql.setSqlId("BqDetailQuerySql11");
			mySql.addSubPara(ContNo); 
		
        
        var crr = easyExecSql(mySql.getString());
        if ( crr )
        {
            var appflag;
            crr[0][0]==null||crr[0][0]=='null'?'0':appflag   = crr[0][0];
            if (appflag == "4")
            {
                fm.Terminate.value = "终止";
            }
        }
    }
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

function getStateReasonCN(StateReason)
{
    if (StateReason == "01") return "满期终止";
    if (StateReason == "02") return "退保终止";
    if (StateReason == "03") return "解约终止";
    if (StateReason == "04") return "理赔终止";
    if (StateReason == "05") return "自垫终止";
    if (StateReason == "06") return "贷款终止";
    if (StateReason == "07") return "失效终止";
    if (StateReason == "08") return "其他终止";
}

//状态判断函数；未被调用，暂留
function isOnState(ContNo,tPolNo,StateType,tDate)
{
    var strsql = "";
    if(tPolNo == "")
    {
       /* strsql = "select state from lccontstate where statetype = '"+StateType+"' "
                   + " and state = '1' and startdate <= '"+tDate+"' and (enddate is null or enddate >= '"+tDate+"') and contno = '"+ContNo+"'";
   		*/	mySql = new SqlClass();
			mySql.setResourceName("sys.BqDetailQuerySql");
			mySql.setSqlId("BqDetailQuerySql12");
			mySql.addSubPara(StateType);
			mySql.addSubPara(tDate); 
			mySql.addSubPara(tDate); 
			mySql.addSubPara(ContNo);  
    }
    else
    {
      /*  strsql = "select state from lccontstate where statetype = '"+StateType+"' "
                   + " and state = '1' and startdate <= '"+tDate+"' and (enddate is null or enddate >= '"+tDate+"') and contno = '"+ContNo+"' and polno = '"+tPolNo+"'";
    	*/	mySql = new SqlClass();
			mySql.setResourceName("sys.BqDetailQuerySql");
			mySql.setSqlId("BqDetailQuerySql13");
			mySql.addSubPara(StateType);
			mySql.addSubPara(tDate); 
			mySql.addSubPara(tDate); 
			mySql.addSubPara(ContNo);  
			mySql.addSubPara(tPolNo); 
    }
    var brr = easyExecSql(mySql.getString());
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
  //  var strsql = "select trim(polno) from lcpol where contno = '"+ContNo+"' and polno = mainpolno";
    mySql = new SqlClass();
			mySql.setResourceName("sys.BqDetailQuerySql");
			mySql.setSqlId("BqDetailQuerySql14"); 
			mySql.addSubPara(ContNo);   
    var brr = easyExecSql(mySql.getString());
    if(brr)
    {
        PolNo = brr[0][0];
    }
    return PolNo;
}
//保全操作轨迹
function MissionQuery()
{
    var EdorAcceptNo = fm.EdorAcceptNo.value;
   // var strSql = "select missionid from lbmission where missionprop1 = '"+EdorAcceptNo+"' union select missionid from lwmission where missionprop1 = '"+EdorAcceptNo+"' ";
    mySql = new SqlClass();
			mySql.setResourceName("sys.BqDetailQuerySql");
			mySql.setSqlId("BqDetailQuerySql15"); 
			mySql.addSubPara(EdorAcceptNo);   
			mySql.addSubPara(EdorAcceptNo);  
    var brr =  easyExecSql(mySql.getString());
    if(!brr)
    {
       alert("该保全受理轨迹信息不存在！");
       return;
    }
    var pMissionID = brr[0][0];
    window.open("../bq/EdorMissionFrame.jsp?MissionID="+pMissionID,"window3","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}
/*********************************************************************
 *  核保查询
 *  描述: 核保状态查询
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function  UWQuery()
{
    var pEdorAcceptNo=fm.EdorAcceptNo.value;
    window.open("../bq/EdorAppUWQueryMain.jsp?EdorAcceptNo="+pEdorAcceptNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");


}