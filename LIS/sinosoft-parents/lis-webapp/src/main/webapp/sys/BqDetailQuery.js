//�������ƣ�
//�����ܣ����˱�ȫ��ѯ
//�������ڣ�2005-06-22 15:15:22
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
var mDebug="1";
var aEdorFlag='0';
var mEdorType;
var turnPage = new turnPageClass();
var mflag = "";  //�������ͻ���ѯ��ʶ
var mySql = new SqlClass();


/*********************************************************************
 *  ҳ�����ʾ����
 *  ����: ҳ�����ʾ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initDiv()
{
//alert(0);
    var EdorAcceptNo = document.all('EdorAcceptNo').value;
    if(EdorAcceptNo == null || EdorAcceptNo == "")
    {
        alert("��ȫ�����Ϊ�գ�");
        return;
    }
    var strSQL;

    //��ѯ��������ȫ����ȷ�Ͻڵ�
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

    if ( brr )  //�Ѿ����뱣���
    {
        //alert("�Ѿ����뱣���");
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

        if(fm.OtherNoType.value == "1")  //���˿ͻ���
        {
            displayCustomerInfo(fm.OtherNo.value);  //��ѯ�ͻ���ϸ��Ϣ
            getEdorItemGrid();                  //��ѯ��ȫ��Ŀ�б���ϸ��Ϣ
            divCustomer.style.display='';       //�ͻ���Ϣ
            divCont.style.display='none';       //������Ϣ
            divEdorItemInfo.style.display='';   //��ȫ��Ŀ��Ϣ

        }
        if(fm.OtherNoType.value == "3")  //���˱�����
        {
            displayContInfo(fm.OtherNo.value);  //��ѯ������ϸ��Ϣ
            getEdorItemGrid();                      //��ѯ��ȫ��Ŀ�б���ϸ��Ϣ
            divCustomer.style.display='none';   //�ͻ���Ϣ
            divCont.style.display='';           //������Ϣ
            divEdorItemInfo.style.display='';   //��ȫ��Ŀ��Ϣ

        }

    }
    else
    {
        alert("û�иñ�ȫ������Ϣ");

    }
}

/*********************************************************************
 *  ��ѯ�ͻ���ϸ��Ϣ
 *  ����: ��ѯ�ͻ���ϸ��Ϣ
 *  ����  ��  CustomerNo
 *  ����ֵ��  ��
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
 *  ��ѯ������ϸ��Ϣ
 *  ����: ��ѯ������ϸ��Ϣ
 *  ����  ��  ContNo
 *  ����ֵ��  ��
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
    displayContStateInfo(ContNo);  //��ѯ����״̬��ϸ��Ϣ

    //��ѯ���ս��Ѷ�Ӧ��
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

    //��ѯ�����б�
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
 *  MulLine��RadioBox����¼�����ʾ��Ŀ��ϸ��ť
 *  ����  ��  ��
 *  ����ֵ��  ��
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
 *  ��ѯ��ȫ��Ŀ��д��MulLine
 *  ����  ��  ��
 *  ����ֵ��  ��
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
 *  ��ѯ��ȫ��Ŀ��д��MulLine
 *  ����  ��  ��
 *  ����ֵ��  ��
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

        if (tOtherNoType=='3') //���˱���������
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
        else if (tOtherNoType=='1') //���˿ͻ�������
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
 *  ����
 *  ����: ҳ�����ʾ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function returnParent()
{
    //top.opener.initSelfGrid();
    //top.opener.easyQueryClickSelf();
    top.close();
}

    //��ȫ��ѯ
function QueryEdorClick()
{
    var arrReturn = new Array();
    var cEdorAcceptNo = document.all('EdorAcceptNo').value;
    var tSel = EdorItemGrid.getSelNo();
    if( tSel == 0 || tSel == null )
    {
        alert( "����ѡ��һ����ȫ��Ŀ��¼��" );
        return;
    }
    else
    {
        var tEdortype = EdorItemGrid.getRowColData(tSel - 1,21);
        if(tEdortype==0||tEdortype==null)
        {
            alert("���α�ȫ����û�б�ȫ��Ŀ������ѡ��ȫ����");
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
          alert("�˴α�ȫ�����Ѿ�ȷ�ϣ����ܽ�����ϸ��ѯ�������������ѯ��");
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

    //��ȫ��ѯ���µģ�������
function QueryEdorClickNew()
{
    var arrReturn = new Array();
    var cEdorAcceptNo = document.all('EdorAcceptNo').value;
    var tSel = EdorItemGrid.getSelNo();
    if( tSel == 0 || tSel == null )
    {
        alert( "����ѡ��һ����ȫ��Ŀ��¼��" );
        return;
    }
    else
    {
        var tEdortype = EdorItemGrid.getRowColData(tSel - 1,21);
        if(tEdortype==0||tEdortype==null)
        {
            alert("���α�ȫ����û�б�ȫ��Ŀ������ѡ��ȫ����");
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
    var newWindow = OpenWindowNew("../bq/QueryPEdor.jsp?Interface= ../sys/BqDetailQuery.jsp","��ȫ���Ĳ�ѯ","left");
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
     OpenWindowNew(tUrl,"��ȫɨ��Ӱ��","left");
//     var MissionID       = document.all('MissionID').value;
//   var SubMissionID    = document.all('SubMissionID').value;
//     var str = "select docid from es_doc_relation where bussno = '" + EdorAcceptNo + "' and busstype = 'BQ' and relaflag = '0'";
//     var arrResult = easyExecSql(str);
//     if(arrResult == null){
//          alert("�˴α�ȫ����û�����ɨ��Ӱ�����ϣ�");
//          return;
//     }
//     var PrtNo = arrResult[0][0];
//     var tResult = easyExecSql("select a.codealias from ldcode a,es_doc_relation b where a.codetype = 'bqscan' and trim(a.code) = trim(b.subtype) and b.busstype = 'BQ' and b.bussno = '"+EdorAcceptNo+"'", 1, 0);
//     if(tResult == null){
//          alert("��ѯ��ȫɨ����ҵ�����ͱ���ʧ�ܣ�");
//          return;
//     }
//     var varSrc = "&ScanFlag = 1&prtNo=" + PrtNo + "&EdorAcceptNo=" + EdorAcceptNo + "&MissionID=" + MissionID + "&SubMissionID=" + SubMissionID;// + "&SubType=" + tResult[0][0];
//     var newWindow = OpenWindowNew("../bq/EdorScan.jsp?" + varSrc,"��ȫɨ��Ӱ��","left");
}
/*********************************************************************
 *  ��ѯ����״̬��ϸ��Ϣ
 *  ����: ��ѯ����״̬��ϸ��Ϣ
 *  ����  ��  ContNo
 *  ����ֵ��  ��
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
    fm.Available.value = isInArray(brr,"Available")?"ʧЧ":"��Ч";
    fm.Lost.value      = isInArray(brr,"Lost")?"��ʧ":"δ��ʧ";
    fm.PayPrem.value   = isInArray(brr,"PayPrem")?"�Ե�":"δ�Ե�";
    fm.Loan.value      = isInArray(brr,"Loan")?"����":"δ����";
    fm.BankLoan.value  = isInArray(brr,"BankLoan")?"��Ѻ���д���":"δ��Ѻ���д���";
    fm.RPU.value       = isInArray(brr,"RPU")?"�������":"δ�������";
    fm.Terminate.value = isInArray(brr,"Terminate")?"��ֹ":"δ��ֹ";
    if (isInArray(brr,"Terminate"))
    {
        //��ʾ��ֹԭ��
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
                fm.Terminate.value = "��ֹ";
            }
        }
    }
}

//�ж��ַ���StateType�Ƿ�����ڶ�ά����brr��һ���У������򷵻�true
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
    if (StateReason == "01") return "������ֹ";
    if (StateReason == "02") return "�˱���ֹ";
    if (StateReason == "03") return "��Լ��ֹ";
    if (StateReason == "04") return "������ֹ";
    if (StateReason == "05") return "�Ե���ֹ";
    if (StateReason == "06") return "������ֹ";
    if (StateReason == "07") return "ʧЧ��ֹ";
    if (StateReason == "08") return "������ֹ";
}

//״̬�жϺ�����δ�����ã�����
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
//�õ�����������(���ڶ����յ������)
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
//��ȫ�����켣
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
       alert("�ñ�ȫ����켣��Ϣ�����ڣ�");
       return;
    }
    var pMissionID = brr[0][0];
    window.open("../bq/EdorMissionFrame.jsp?MissionID="+pMissionID,"window3","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");
}
/*********************************************************************
 *  �˱���ѯ
 *  ����: �˱�״̬��ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function  UWQuery()
{
    var pEdorAcceptNo=fm.EdorAcceptNo.value;
    window.open("../bq/EdorAppUWQueryMain.jsp?EdorAcceptNo="+pEdorAcceptNo,"window1","toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes");


}