
//�������ƣ�
//�����ܣ����˱�ȫ����
//�������ڣ�2005-04-26 16:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����

var showInfo;
var mDebug="1";
var aEdorFlag='0';
var mEdorType;
var turnPage = new turnPageClass();
var turnPageCustomerContGrid = new turnPageClass();
var turnPageApprove = new turnPageClass();
var mflag = "";  //�������ͻ���ѯ��ʶ
var theFirstValue="";
var theSecondValue="";
var hasSaved = "";
var userEdorPopedom = "";  //��ǰ�û���ȫ����

var tPreLoanFlag="";
var CusBQPrintFlag="";
var mySql=new SqlClass();

/*********************************************************************
 *  ҳ�����ʾ����
 *  ����: ҳ�����ʾ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initDiv()
{
    var EdorAcceptNo = document.all('EdorAcceptNo').value;
    var MissionID = document.all('MissionID').value
    var SubMissionID = document.all('SubMissionID').value
		
    if(EdorAcceptNo == null || EdorAcceptNo == "")
    {
        alert("��ȫ�����Ϊ�գ�");
        return;
    }
    if(MissionID == null || MissionID == "")
    {
        alert("�����Ϊ�գ�");
        return;
    }
    if(SubMissionID == null || SubMissionID == "")
    {
        alert("�������Ϊ�գ�");
        return;
    }
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql1");
    mySql.addSubPara(fm.UserCode.value);   
    //��ѯ��ǰ�û���ȫ����

    var urr = easyExecSql(mySql.getString());
    if ( urr )
    {
        urr[0][0]==null||urr[0][0]=='null'?'0':userEdorPopedom = urr[0][0];
        
    }

    //��ѯ��������ȫ����ȷ�Ͻڵ�
  
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql2");
    mySql.addSubPara(EdorAcceptNo);  
    var brr = easyExecSql(mySql.getString());

    if ( brr )  //�Ѿ����뱣���
    {
        //alert("�Ѿ����뱣���");
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

        divApplySaveWrite.style.display="none";     //��ȫ������Ϣ����
        divApplySaveRead.style.display='';          //��ȫ������Ϣֻ��
        divApplySaveBT.style.display="none";        //�������뱣�水ť

        if(fm.OtherNoType.value == "1")  //���˿ͻ���
        {
            displayCustomerInfo(fm.OtherNo.value);  //��ѯ�ͻ���ϸ��Ϣ
            getEdorItemGrid();                  //��ѯ��ȫ��Ŀ�б���ϸ��Ϣ
            divCustomer.style.display='';       //�ͻ���Ϣ
            divCont.style.display="none";       //������Ϣ
            divContState.style.display="none";  //����״̬��Ϣ
            divEdorItemInfo.style.display='';   //��ȫ��Ŀ��Ϣ

            if(fm.LoadFlag.value == "edorApp" || fm.LoadFlag.value == "scanApp" || fm.LoadFlag.value=="CM")
            {
                //��ȫ����
                divappconfirm.style.display=''; //����ȷ�ϰ�ť
                divApproveModify.style.display="none";//�����޸�ȷ�ϰ�ť
                showDivGetPayForm();        //��ʾ�ո��ѷ�ʽ��Ϣ
                divEdorTest.style.display="none";     //������ϰ�ť
            }
            if(fm.LoadFlag.value == "approveModify")
            {
                //��ȫ�����޸�
                divappconfirm.style.display="none"; //����ȷ�ϰ�ť
                divApproveModify.style.display='';  //�����޸�ȷ�ϰ�ť
                showDivGetPayForm();        //��ʾ�ո��ѷ�ʽ��Ϣ
                divEdorTest.style.display="none";   //������ϰ�ť
            }
            if(fm.LoadFlag.value == "edorTest")
            {
                //��ȫ����
                divappconfirm.style.display="none";    //����ȷ�ϰ�ť
                divApproveModify.style.display="none"; //�����޸�ȷ�ϰ�ť
                showDivGetPayForm();        //�����ո��ѷ�ʽ��Ϣ
                divEdorTest.style.display='';          //������ϰ�ť
            }
        }
        if(fm.OtherNoType.value == "3")  //���˱�����
        {
            displayContInfo(fm.OtherNo.value);  //��ѯ������ϸ��Ϣ
            displayContStateInfo(fm.OtherNo.value);  //��ѯ����״̬��ϸ��Ϣ
            getEdorItemGrid();                  //��ѯ��ȫ��Ŀ�б���ϸ��Ϣ
            divCustomer.style.display="none";   //�ͻ���Ϣ
            divCont.style.display='';           //������Ϣ
            divContState.style.display='';      //����״̬��Ϣ
            divEdorItemInfo.style.display='';   //��ȫ��Ŀ��Ϣ

            if(fm.LoadFlag.value == "edorApp" || fm.LoadFlag.value == "scanApp" ||fm.LoadFlag.value=="CM")
            {
                //��ȫ����
                divappconfirm.style.display='';       //����ȷ�ϰ�ť
                divApproveModify.style.display="none";//�����޸�ȷ�ϰ�ť
                showDivGetPayForm();        //��ʾ�ո��ѷ�ʽ��Ϣ
                divEdorTest.style.display="none";     //������ϰ�ť
            }
            if(fm.LoadFlag.value == "approveModify")
            {
                //��ȫ�����޸�
                divappconfirm.style.display="none";//����ȷ�ϰ�ť
                divApproveModify.style.display=''; //�����޸�ȷ�ϰ�ť
                showDivGetPayForm();        //��ʾ�ո��ѷ�ʽ��Ϣ
                divEdorTest.style.display="none";  //������ϰ�ť
            }
            if(fm.LoadFlag.value == "edorTest")
            {
                //��ȫ����
                divappconfirm.style.display="none";    //����ȷ�ϰ�ť
                divApproveModify.style.display="none"; //�����޸�ȷ�ϰ�ť
                showDivGetPayForm();        //�����ո��ѷ�ʽ��Ϣ
                divEdorTest.style.display='';          //������ϰ�ť
            }
        }
        if(fm.PEdorState.value == '5'){
        	//�����޸�ԭ����ʾ	
        	initApproveTrackGrid();
        	
           mySql=new SqlClass();
           mySql.setResourceName("bq.PEdorApp");
           mySql.setSqlId("PEdorAppSql3");
           mySql.addSubPara(fm.EdorAcceptNo.value);  
				
       		turnPageApprove.queryModal(mySql.getString(), ApproveTrackGrid); 
        	divApproveTrack.style.display = '';
        }

    }
    else  //��δ���뱣��
    {
        //alert("��δ���뱣��");
        hasSaved = "N";
        divApplySaveWrite.style.display="";     //��ȫ������Ϣ����
        divApplySaveRead.style.display="none";  //��ȫ������Ϣֻ��
        divApplySaveBT.style.display='';        //��ʾ���뱣�水ť
        divCustomer.style.display="none";       //�ͻ���Ϣ
        divCont.style.display="none";           //������Ϣ
        divContState.style.display="none";      //����״̬��Ϣ
        divEdorItemInfo.style.display="none";   //��ȫ��Ŀ��Ϣ
        divappconfirm.style.display="none";     //����ȷ�ϰ�ť
        divApproveModify.style.display="none";  //�����޸�ȷ�ϰ�ť
        divEdorTest.style.display="none";       //������ϰ�ť

        showDivGetPayForm();        //��ʾ�ո��ѷ�ʽ��Ϣ

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

    //��ѯ�ͻ���ر����б�
    var EdorAppDate = document.all('EdorAppDate').value;
    mySql=new SqlClass();

   /* strSQL =  " select 1 from lpedoritem where edoracceptno = '" + fm.EdorAcceptNo.value + "'" ;*/
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql5");
    mySql.addSubPara(fm.EdorAcceptNo.value);     
    
    var brr = easyExecSql(mySql.getString());
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    
    if ( brr )  //�Ѿ���ӱ�ȫ��Ŀ
    {
   /* strSQL =  " select   ContNo, AppntName, InsuredName, agentcode, cvalidate, customgetpoldate, "
            + " case appflag when '1' then '�б�' when '2' then 'δ�б�' when '4' then '��ֹ' end , "
            + " case (select 1 from lpedoritem i where i.contno = c.contno and edoracceptno = '" + fm.EdorAcceptNo.value + "') when 1 then '������' else '��������' end  "
            ;
            */
        mySql.setSqlId("PEdorAppSql6");    
        mySql.addSubPara(fm.EdorAcceptNo.value);    
    }
    else  //��δ��ӱ�ȫ��Ŀ
    {
   /* strSQL =  " select   ContNo, AppntName, InsuredName, agentcode, cvalidate, customgetpoldate, "
            + " case appflag when '1' then '�б�' when '2' then 'δ�б�' when '4' then '��ֹ' end , "
            + " '' "  //��ʾΪ��
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
				+ " union " // ��Ͷ���˻򱻱��������صı���
				+ " select contno from lcappnt where appntno = '"
				+ CustomerNo
				+ "' ) "
				+ " and "
				+ " ( " // ����δ��ֹ����������δʧЧ��ʧЧ����������ͻ�������
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
				+ " ( appflag = '4' and exists " // 01-������ֹ��05-�Ե���ֹ��06-������ֹ ���Բ���ͻ�����
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
    //XinYQ modified on 2007-04-05 : ���ܳ���С�ŵ�, ʹ���Զ���ҳ����
    //prompt('',strSQL);
    try
    {
        turnPageCustomerContGrid.pageDivName = "divTurnPageCustomerContGrid";
        turnPageCustomerContGrid.queryModal(mySql.getString(), CustomerContGrid);
    }
    catch (ex)
    {
        alert("���棺��ѯ��ѯ�ͻ�����������Ϣ�����쳣�� ");
        return;
    }
}

//��տͻ���Ϣ
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
 *  ��ѯ������ϸ��Ϣ
 *  ����: ��ѯ������ϸ��Ϣ
 *  ����  ��  ContNo
 *  ����ֵ��  ��
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

    //��ѯ����״̬��ϸ��Ϣ
    displayContStateInfo(fm.OtherNo.value);

    //��ѯ���ս��Ѷ�Ӧ��
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

    //��ѯ�����б�
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
 *  ��ѯ����״̬��ϸ��Ϣ
 *  ����: ��ѯ����״̬��ϸ��Ϣ
 *  ����  ��  ContNo
 *  ����ֵ��  ��
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
    fm.Available.value = isInArray(brr,"Available") ? "ʧЧ" : "��Ч";
    fm.Lost.value      = isInArray(brr,"Lost") ? "��ʧ" : "δ��ʧ";
    fm.PayPrem.value   = isInArray(brr,"PayPrem") ? "�Ե�" : "δ�Ե�";
    fm.Loan.value      = isInArray(brr,"Loan") ? "����" : "δ����";
    fm.BankLoan.value  = isInArray(brr,"BankLoan") ? "��Ѻ���д���" : "δ��Ѻ���д���";
    fm.RPU.value       = isInArray(brr,"RPU") ? "�������" : "δ�������";
    fm.Terminate.value = isInArray(brr,"Terminate") ? "��ֹ" : "δ��ֹ";
    fm.Password.value  = isInArray(brr,"Password") ? "��ʧ" : "δ��ʧ";
    if (isInArray(brr,"Terminate"))
    {
        //��ʾ��ֹԭ��
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
                fm.Terminate.value = "��ֹ";
            }
        }
    }

    //�ͻ��㱣ȫ��ʾ����״̬
    fm.Available_C.value = fm.Available.value;
    fm.Lost_C.value      = fm.Lost.value     ;
    fm.PayPrem_C.value   = fm.PayPrem.value  ;
    fm.Loan_C.value      = fm.Loan.value     ;
    fm.BankLoan_C.value  = fm.BankLoan.value ;
    fm.RPU_C.value       = fm.RPU.value      ;
    fm.Terminate_C.value = fm.Terminate.value;
    fm.Password_C.value  = fm.Password.value;
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

/**
 * ת��������ֹ��ԭ�����Ϊ������ʾ
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
            alert("���棺��ѯ������ֹ��ԭ��������Ϣ�����쳣�� ");
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

//״̬�жϺ�����δ�����ã�����
function isOnState(ContNo,tPolNo,StateType,tDate)
{
    var strsql = "";
    if(tPolNo == "")
    {
//        strsql = "select state from lccontstate where statetype = '"+StateType+"' "
//                   + " and state = '1' and startdate <= '"+tDate+"' and (enddate is null or enddate >= '"+tDate+"') and contno = '"+ContNo+"'";
        var sqlid1="PEdorAppSql51";
        var mySql1=new SqlClass();
        mySql1.setResourceName("bq.PEdorApp"); //ָ��ʹ�õ�properties�ļ���
        mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
        mySql1.addSubPara(StateType);//ָ������Ĳ���
        mySql1.addSubPara(tDate);//ָ������Ĳ���
        mySql1.addSubPara(tDate);//ָ������Ĳ���
        mySql1.addSubPara(ContNo);//ָ������Ĳ���
        strsql=mySql1.getString();
    }
    else
    {
//        strsql = "select state from lccontstate where statetype = '"+StateType+"' "
//                   + " and state = '1' and startdate <= '"+tDate+"' and (enddate is null or enddate >= '"+tDate+"') and contno = '"+ContNo+"' and polno = '"+tPolNo+"'";
        var sqlid2="PEdorAppSql52";
        var mySql2=new SqlClass();
        mySql2.setResourceName("bq.PEdorApp"); //ָ��ʹ�õ�properties�ļ���
        mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
        mySql2.addSubPara(StateType);//ָ������Ĳ���
        mySql2.addSubPara(tDate);//ָ������Ĳ���
        mySql2.addSubPara(tDate);//ָ������Ĳ���
        mySql2.addSubPara(ContNo);//ָ������Ĳ���
        mySql2.addSubPara(tPolNo);//ָ������Ĳ���
        strsql=mySql2.getString();
    }
    
    
    
    var brr = easyExecSql(strsql);
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

//��տͻ���Ϣ
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

//��ձ���״̬��Ϣ
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

    if (tOtherNoType=='3')//���˱���������
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
    else if (tOtherNoType=='1')//���˿ͻ�������
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

    if (tOtherNoType=='3')//���˱���������
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
    else if (tOtherNoType=='1')//���˿ͻ�������
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
	        mySql1.setResourceName("bq.PEdorApp"); //ָ��ʹ�õ�properties�ļ���
	        mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	        mySql1.addSubPara(document.all("BfAgentCode").value);//ָ������Ĳ���
	        strsql=mySql1.getString();
		var arrResult = easyExecSql(QuerySQL, 1, 0);
		//prompt("QuerySQL",QuerySQL);
		//alert("arrResult"+arrResult);
		if (arrResult == null) {
			alert("��������Ϣ¼����󣬻����Ѿ���ְ");
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
	//ͬʱ�����������Ϣ
	document.all("BfName").value = "";
	document.all("BfIDType").value = "";
	document.all("BfIDTypeName").value = "";
	document.all("BfIDNo").value = "";
	document.all("BfPhone").value = "";
    document.all("ManageCom").value = "";
	document.all("ManageComName").value = "";
}
//<!-- XinYQ added on 2005-11-30 : ��ȫ�����������б�ͱ�����ϸ��ѯ : BGN -->
/*============================================================================*/

/**
 * ˫������������ʱ���ݺ�����������������б�
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
        alert("��ʾ������¼��ͻ�/�����Ų�ѡ��������ͣ�");
        return;
    }
    //if OtherNo and OtherNoType both has data, go next!
    try
    {
        document.getElementsByName("EdorApp")[0].value = "";
        document.getElementsByName("EdorApp")[0].CodeData = "";
        document.getElementsByName("EdorAppName")[0].value = "";
    } catch (ex) {}
    var sCodeData = "";    //���ظ� EdorApp �� CodeData
    var QuerySQL, arrResult;
    //����ѡ��Ĳ�ͬ�������Ͳ�����ͬ�� SQL ��ѯ
    switch (sOtherNoType)
    {
        case "1":    //���˿ͻ���
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
                alert("���棺�����˿ͻ��Ų�ѯ�ͻ�������Ϣ�����쳣�� ");
                return;
            }
            if (arrResult != null)
                sCodeData = "0|^�ͻ�|" + arrResult[0][0];
            else
                alert("��ʾ��û���ҵ�����Ϊ " + trim(sOtherNo) + " �Ŀͻ��� ");
            break;
        case "3":    //���˱�����
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
                alert("���棺�����˱����Ų�ѯ�ͻ���ͬ��Ϣ�����쳣�� ");
                return;
            }
            if (arrResult != null)
                sCodeData = "0|^Ͷ����|" + arrResult[0][0] + "^������|" + arrResult[0][1];
            else
                alert("��ʾ��û���ҵ�����Ϊ " + trim(sOtherNo) + " �ı����� ");
            break;
        default:
            alert("����δ֪�ĺ������ͣ� ");
            return;
    }
    sCodeData = sCodeData + "^����|"
    try { document.getElementsByName("EdorApp")[0].setAttribute('CodeData' , sCodeData); } catch (ex) {}
}

/*============================================================================*/

/**
 * ��ѯ������ϸ
 */
function viewPolDetail()
{
    var sOtherNo, sOtherNoType;
    try { sOtherNoType = document.getElementsByName("OtherNoType")[0].value; } catch (ex) {}
    if (sOtherNoType == null || trim(sOtherNoType) == "")
    {
        alert("���棺�޷���ȡ��������������͡���ѯ������ϸʧ�ܣ� ");
        return;
    }
    switch (trim(sOtherNoType))
    {
        case "1":
            //���˿ͻ���
            var nSelNo = CustomerContGrid.getSelNo() - 1;
            if (nSelNo < 0)
            {
                alert("����ѡ����Ҫ�鿴�Ŀͻ�����������Ϣ�� ");
                return;
            }
            else
            {
                try { sOtherNo = CustomerContGrid.getRowColData(nSelNo, 1); } catch (ex) {}
            }
            break;
        case "3":
            //���˱�����
            try { sOtherNo = document.getElementsByName("ContNoApp")[0].value; } catch (ex) {}
            break;
        default:
            alert("����δ֪�ı�������������ͣ� ");
            return;
    }
    if (sOtherNo == null || trim(sOtherNo) == "")
    {
        alert("���棺�޷���ȡ����������롣��ѯ������ϸʧ�ܣ� ");
        return;
    }
    //����ҳ����ʾ������ϸ
    var sOpenWinURL = "../sys/PolDetailQueryMain.jsp";
    var sParameters = "ContNo=" + sOtherNo + "&IsCancelPolFlag=0";
    OpenWindowNew(sOpenWinURL + "?" + sParameters, "��ȫ������ϸ��ѯ", "left");
}

/*============================================================================*/
//<!-- XinYQ added on 2005-11-30 : ��ȫ�����������б�ͱ�����ϸ��ѯ : END -->


/*********************************************************************
 *  ѡ��������Ŀ��Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect(cCodeName, Field)
{
    try {
        if( cCodeName == "EdorCode" )
        {
            var tOtherNoType = document.all('OtherNoType').value;
            var tAppObj;
            if (tOtherNoType=='3')//���˱���������
            {
                tAppObj = "I"
            }
            if (tOtherNoType=='1')//���˿ͻ�������
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
                alert("δ�鵽�ñ�ȫ��Ŀ��ʾ����");
                return;
            }
            if (tOtherNoType=='1')//���˿ͻ�������
            {
                tDisplayType = "1"
            }
            document.all("DisplayType").value = tDisplayType;

            if (tDisplayType=='1')  //ֻ��ʾ����
            {
                document.all("divPolInfo").style.display = "none";
                document.all("divPolGrid").style.display = "none";
                document.all("divInsuredInfo").style.display = "none";
                document.all("divInsuredGrid").style.display = "none";
            }
            else if (tDisplayType=='2')  //��Ҫ��ʾ�ͻ�
            {
                getInsuredGrid(document.all('OtherNo').value, Field.value);
                document.all("divPolInfo").style.display = "none";
                document.all("divPolGrid").style.display = "none";
                document.all("divInsuredInfo").style.display = "";
                document.all("divInsuredGrid").style.display = "";
            }
            else if (tDisplayType=='3')  //��Ҫ��ʾ����
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
            //���ݲ�ͬ�ı�ȫ��Ŀ���ñ�ȫ��Ч����
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

    //<!-- XinYQ added on 2005-11-30 : ��ȫ�����������б� : BGN -->
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
            if (trim(sEdorApp) == "����")
                document.getElementsByName("EdorAppName")[0].readOnly = false;
            else
                document.getElementsByName("EdorAppName")[0].readOnly = true;
        } catch (ex) {}
    }
    //<!-- XinYQ added on 2005-11-30 : ��ȫ�����������б� : END -->
    //add by jiaqiangli 2009-01-04 ��������Ϣ
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
    	    //add by jiaqiangli 2009-02-20 �˴���js��̬���DOM�¼��Ľ���취
    	    //����˵��ҪshowOneCodeNameEx ����������� ������showCodeListû��ִ�е����Ѿ�return��
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
    //add by jiaqiangli 2009-01-04 ��������Ϣ
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
      //���д���
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
              arrField = arrRecord[i].split("|");  //��ּ�¼���õ��ֶ�����
              fieldNum = arrField.length;
              arrEasyQuery[i] = new Array();

              var j=0;
              while(j<fieldNum)
              {
                  arrEasyQuery[i][j] = arrField[j];   //�γ�����Ϊ��¼����Ϊ�ֶεĶ�ά����
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
 *  ��ѯ���������б�
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function getInsuredGrid(tContNo, tEdorType)
{
//    var strSQL =
//    " select * from ( " +
//    "  select distinct '������' role, a.insuredno no, a.Name name, (concat(concat(a.Sex , '-') , (select CodeName from LDCode where 1 = 1 and CodeType = 'sex' and Code = a.Sex))) sex, " +
//    "     a.Birthday birthday, (concat(concat(a.IDType , '-') , (select CodeName from LDCode where 1 = 1 and CodeType = 'idtype' and Code = a.IDType))) idtype, a.IdNo idno, a.contno contno, a.grpcontno grpcontno, 1 type" +
//    "  from lcinsured a where a.ContNo = '" + tContNo + "' " +
//    "  union " +
//    "  select distinct 'Ͷ����' role, b.appntno no, b.appntname name, (concat(concat(b.AppntSex , '-' ), (select CodeName from LDCode where 1 = 1 and CodeType = 'sex' and Code = b.AppntSex))) sex, " +
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
        //���Ͷ����Ϊ�����ˣ�ֻ��ʾ������
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
    //strSQL += " order by type, no asc "; //��������ʾ����

    turnPage.queryModal(mySql.getString(), InsuredGrid);
 //   if (InsuredGrid.mulLineCount > 0)
 //   {
  //      InsuredGrid.checkBoxSel(1);
  //  }

}
/*********************************************************************
 *  ��ѯ�����б�
 *  ����  ��  ��
 *  ����ֵ��  ��
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
               //��ȥ��ֹ�ı���
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
 

    //ȥ���Ѿ�ʧЧ�ı���
    //�������Զ���������ж�����¼��ֻ����ʾһ����������Ч����Ϊ���磬��������Ϊ����
    turnPage.queryModal(mySql.getString(), PolGrid);
    if (PolGrid.mulLineCount > 0)
    {
        PolGrid.checkBoxSel(1);
    }

}

/*********************************************************************
 *  ��ӱ�ȫ��Ŀ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function addEdorItem()
{
    if (document.all('EdorAcceptNo').value==null || document.all('EdorAcceptNo').value=="")
    {
        alert("���ȱ��汣ȫ��������ӱ�ȫ��Ŀ!");
        return false;
    }
    if (document.all('EdorType').value==null || document.all('EdorType').value=="")
    {
        alert("��ѡ����Ҫ��ӵı�ȫ��Ŀ!");
        return false;
    }
    if (document.all('DisplayType').value == null || document.all('DisplayType').value == "" )
    {
        alert("��˫��ѡ����Ҫ��ӵı�ȫ��Ŀ!");
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
            alert("��ѡ����Ҫ��ӱ�ȫ��Ŀ�Ŀͻ��� ");
            return false;
        }
        if (InsuredGrid.getSelNo()>0 && EdorItemGrid.mulLineCount>0 )
        {
           alert("�ͻ��㱣ȫ������һ��ֻ�ܴ���һ���ͻ�,�뷵�� ");
           return false;
        }
        //���CM��ȫ��ͻ����¹���У�飬����й�����Ҫ����ҵ�����Ѻ�
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
            alert("�ÿͻ����������������ڹ����������ת����;���");
            if (window.confirm("�Ƿ��ӡ���α�ȫ��ҵ�������Ѻ�?"))
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
            alert("��ѡ����Ҫ��ӱ�ȫ��Ŀ�����֣� ");
            return false;
        }
    }
    else if (document.all('DisplayType').value == '1')
    {

    }
    else
    {
        alert("��ȫ��Ŀ��ʾ�����ѯ���� ");
        return;
    }
    if (document.all('EdorItemAppDate').value == null || document.all('EdorItemAppDate').value == "")
    {
        alert("����������������ڣ� ");
        return;
    }
    
    //add by jiaqiangli ���Ӹ��ձ�ȫ�ȫ�����������ڵ�������У��
   /* var tCheckSQL = "with lastbq as(select contno,edorappdate from (select contno,edorappdate from "
    				//edorstate in ('0','b') ��ȫ״̬�Ƿ�Ӧ�ð���ǿ����ֹ���˱���ֹ��������ֹ����Ŀ
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
    	alert("�ñ����ϴα�ȫ�����Ĺ�������������"+tCheckFlagDate+"���������α�ȫ�Ĺ�����������"+document.all('EdorItemAppDate').value+"!");
        return;
    }
    //add by jiaqiangli ���Ӹ��ձ�ȫ�ȫ�����������ڵ�������У��
    
    if (!chkEdorContPwd())    return;    //�������빦��
    
    if (!chkEdorType())
    {
    	return;
    }
    //ֻ����ҳ�������ʱ����CM��ǣ�����������Ȼ���ó������"edorApp"
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
    	alert("�ñ����ѱ����𣬲�����ӱ�ȫ��Ŀ��");
    	return;
    }
    
    document.all('fmtransact').value="INSERT||EDORITEM";
    fm.AddItem.disabled = true;
    var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=300;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = "../bq/PEdorAppItemSave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit();

}

/*********************************************************************
 *  ���뱣ȫ��Ŀ��ϸҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function gotoDetail()
{
    var tSelNo = EdorItemGrid.getSelNo()-1;
    if (tSelNo < 0)
    {
        alert("��ѡ����Ҫ�������Ŀ��");
        return false;
    }

    detailEdorType();

}

function ModEdorPersonInfo()
{
	 if((fm.Mobile_Mod.value == null || trim(fm.Mobile_Mod.value) == '')&&(fm.PostalAddress_Mod.value == null || trim(fm.PostalAddress_Mod.value) == '')){
   	 	alert("�����ʸ��˵��ֻ�����ϵ��ַ��¼��һ��");
   	 	return;
   }
   
   if(fm.Mobile_Mod.value != null && trim(fm.Mobile_Mod.value) != ''){
   		var valid= /^[0-9]\d{10}$/;
   		if(!valid.test(fm.Mobile_Mod.value)){
   				alert("�����ʸ����ֻ����벻�Ϸ�!");
   				return;
   		}
   }
   if(fm.PostalAddress_Mod.value != null && trim(fm.PostalAddress_Mod.value) != ''){
   		if(fm.ZipCode_Mod.value == null ||trim(fm.ZipCode_Mod.value) == '' ){
   				alert("��ַ��Ϣ���ʱ����ͬʱ¼��!");
   				return;
   		}
   }
   if(fm.ZipCode_Mod.value != null && trim(fm.ZipCode_Mod.value) != ''){
   		var valid= /^\d{6}$/;
     	if(!valid.test(fm.ZipCode_Mod.value)){
   			alert("�ʱ��ʽ���Ϸ�!");
   			return;
   		}
   }
   fm.fmtransact.value = "UPDATE||EDORPERSON";
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

   fm.action= "./EdorPersonInfoMod.jsp";
   document.getElementById("fm").submit();
}

/*********************************************************************
 *  ɾ��δ¼�뱣ȫ��Ŀ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function delEdorItem()
{
    if (fm.PEdorState.value == 2)
    {
        alert("��ȫ�����Ѿ�����ȷ��");
        return;
    }
   if (fm.PEdorState.value == 'c')
    {
        alert("��ȫ�����Ѿ���ֹ");
        return;
    }  
    var tSelNo = EdorItemGrid.getSelNo()-1;
    if (tSelNo < 0)
    {
        alert("��ѡ����Ҫɾ������Ŀ��");
        return false;
    }
    
    //����Ѿ����б�ȫ�����������������б�ȫ����
    var tEdorAcceptNo = document.all('EdorAcceptNo').value;
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql12");
    mySql.addSubPara(tEdorAcceptNo);     
   
   // var strSQL = "select Stateflag from loprtmanager where code in ('BQ37') and Stateflag !='2'  and standbyflag1='"+tEdorAcceptNo+"'";
    var sResult;
    sResult = easyExecSql(mySql.getString());
    if(sResult !=null){
        alert("����������δ�����ĺ�������������б�ȫ����");
        return;
    }

    document.all('Transact').value = "DELETE||EDORITEM";
    document.all('fmtransact').value = "DELETE||EDORITEM";
    document.all("DelFlag").value="EdorItem";

    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


    fm.action="./PEdorAppCancelSubmit.jsp";
    document.getElementById("fm").submit();

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
    fm.EdorType.value =     EdorItemGrid.getRowColData(tSelNo, 20);
    //fm.EdorTypeName.value = EdorItemGrid.getRowColData(tSelNo, 3);
    fm.ContNo.value =       EdorItemGrid.getRowColData(tSelNo, 6);
    fm.InsuredNo.value =    EdorItemGrid.getRowColData(tSelNo, 7);//�ͻ��㱣ȫ�£��˱��������lpedorapp.otherno,���ͻ��ţ����Բο�getEdorItemGrid()�о����ѯ����
    fm.PolNo.value =        EdorItemGrid.getRowColData(tSelNo, 8);
    fm.EdorItemAppDate.value= EdorItemGrid.getRowColData(tSelNo, 9);
    fm.EdorValiDate.value = EdorItemGrid.getRowColData(tSelNo, 10);
    fm.AppReasonName.value =EdorItemGrid.getRowColData(tSelNo, 11);
    fm.AppReason.value =    EdorItemGrid.getRowColData(tSelNo, 12);
    fm.MakeDate.value =     EdorItemGrid.getRowColData(tSelNo, 14);
    fm.EdorMakeDate.value = EdorItemGrid.getRowColData(tSelNo, 14);
    fm.MakeTime.value =     EdorItemGrid.getRowColData(tSelNo, 15);
    fm.EdorItemState.value =EdorItemGrid.getRowColData(tSelNo, 19);
	
	//modify by jiaqiangli 2009-01-22 ���������ж� ����ᷢ����������ձ�ȫ��Ŀ�ظ�����
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

    //XinYQ added on 2006-05-15 : �������ܴ����ظ���ӱ�ȫ��Ŀ������
    var oEdorType;
    try
    {
        oEdorType = document.getElementsByName("EdorType")[0];
        afterCodeSelect("EdorCode", oEdorType);
    }
    catch (ex) {}
}
/*********************************************************************
 *  ��ȫ����ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function edorAppConfirm()
{


    if (fm.PEdorState.value == 2)
    {
        alert("��ȫ�����Ѿ�����ȷ��");
        return;
    }
    if (fm.PEdorState.value == 'c')
    {
        alert("��ȫ�����Ѿ���ֹ");
        return;
    }
    if(!fm.IsFull.checked){
			alert("�������ϲ���ȫ�����ܽ�������ȷ��!");
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
    	alert("�ñ�ȫ������û�б�ȫ��Ŀ��������ӣ�");
    	return;
    }
    var tEdorType = arr[0][0];
    var tAppReason = arr[0][1];
    if (tAppReason !='2') {
    	if((fm.Mobile.value == null || trim(fm.Mobile.value) == '')&&(fm.PostalAddress.value == null || trim(fm.PostalAddress.value) == ''))
    	{

				//�ͻ���ϵ��ʽ������ͻ��������ϱ�������ѷ�ʽ�뽻���˺ű����������ʧ����ʧ���
				if(tEdorType != "AM" && tEdorType != "BB" && tEdorType != "PC" && tEdorType != "PL")
				{
					alert("�����ʸ�����ϵ��ʽ��ȫ����¼�������ʸ�����ϵ��ʽ��");
					divEdorPersonInfo.style.display = '';
					return;
				}
			}
 	  }
		
		//У���Ƿ���ӱ�ȫ��
   //����Ѿ����б�ȫ�����������������б�ȫ����
    var tEdorAcceptNo = document.all('EdorAcceptNo').value;
    //var strSQL = "select Stateflag from loprtmanager where code in ('BQ37') and Stateflag !='2'  and standbyflag1='"+tEdorAcceptNo+"'";
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql50");
    mySql.addSubPara(fm.EdorAcceptNo.value);   
   
    var sResult;
    sResult = easyExecSql(mySql.getString());
    if(sResult!=null){
        alert("����������δ�����ĺ�������������б�ȫȷ��");
        return;
    }
    
    
    document.all("fmtransact").value = "INSERT||EDORAPPCONFIRM";

//    if (window.confirm("��ȷ�ϱ�ȫ�ո��ѷ�ʽ��Ϣ����ȡ������������޸ģ�"))
//    {
        if(fm.GetPayForm.value == null || fm.GetPayForm.value == null)
        {
           alert("�Բ�������û��¼���ո��ѷ�ʽ!");
           return;
        }
        if(fm.GetPayForm.value=="4" || fm.GetPayForm.value=="7")
        {
            //���л��������֧��
            if(fm.BankCode.value == null || fm.BankCode.value == ""
             || fm.BankAccNo.value == null || fm.BankAccNo.value == ""
             || fm.AccName.value == null || fm.AccName.value == "")
          {
                alert("�����ո�����Ϣ������!");
                return;
          }
          if(fm.OtherNoType.value == "1")//�ͻ���
          {
            if(fm.PayGetName.value == null || fm.PayGetName.value == ""
             || fm.PersonID.value == null || fm.PersonID.value == "")
            {
                alert("ѡ�������ո��ѷ�ʽʱ����¼����ȡ�˼����֤����!");
                return;
            }
          }
        }
        if(fm.PayGetName.value != null && fm.PayGetName.value != "")
        {
            if(fm.PersonID.value == null || fm.PersonID.value == "")
            {
                alert("��¼����ȡ�����֤��!");
                return;
            }
        }
        if(fm.PersonID.value != null && fm.PersonID.value != "")
        {
            if(fm.PayGetName.value == null || fm.PayGetName.value == "")
            {
                alert("��¼����ȡ������!");
                return;
            }
        }
        //add by xiongzh  У���ȡothernotype����Ϊ��
        if(fm.OtherNoType.value == null || fm.OtherNoType.value == "")
        {
            alert("��ȡ��ȫ�����������ʧ�ܣ��޷�����������һ������!");
            return;
        }
        var showStr="���ڼ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");

        fm.action= "./PEdorAcptAppConfirmSubmit.jsp";
        document.getElementById("fm").submit();
//    }
}


/*********************************************************************
 *  ��ȫ�����޸�ȷ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function ApproveModifyConfirm()
{
    if (fm.PEdorState.value == 2)
    {
        alert("��ȫ�����Ѿ�����ȷ��");
        return;
    }
     if (fm.PEdorState.value == 'c')
    {
        alert("��ȫ�����Ѿ���ֹ");
        return;
    }
    document.all("fmtransact").value = "INSERT||EDORAPPCONFIRM";

    if (window.confirm("�Ƿ�ȷ�ϱ��α�ȫ����?"))
    {

        var showStr="���ڼ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
        showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");

        fm.action= "./PEdorAcptAppConfirmSubmit.jsp";
        document.getElementById("fm").submit();

    }
}

/*********************************************************************
 *  ��ȫ���뱣��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function applySave()
{
    var tOtherNo = document.all('OtherNo').value;
    var tOtherNoType = document.all('OtherNoType').value;
    if (tOtherNoType == null || tOtherNoType == "")
    {
        alert("������������ͣ�");
        return;
    }
    if(tOtherNo == null || tOtherNo == "")
    {
        if(tOtherNoType == "1")
        {
            alert("������ͻ����룡");
            return;
        }
        if(tOtherNoType == "3")
        {
            alert("�����뱣�����룡");
            return;
        }
    }
    if(tOtherNoType == "1")
    {
        displayCustomerInfo(tOtherNo);
        if(fm.CustomerNo.value == null || fm.CustomerNo.value == "")
        {
            alert("�ͻ��Ų�����!");
            return;
        }
        if (fm.EdorAppName.value == null || fm.EdorAppName.value == "")
        {
            if (fm.LoadFlag.value != "edorTest")
            {
                alert("��¼������������!");
                return;
            }
        }
    }
    if(tOtherNoType == "3")
    {
        displayContInfo(tOtherNo);
        displayContStateInfo(fm.OtherNo.value);  //��ѯ����״̬��ϸ��Ϣ
        if(fm.ContNoApp.value == null || fm.ContNoApp.value == "")
        {
            alert("�����Ų�����!");
            return;
        }
    }
    
    //add by jiaqiangli 2009-11-19 �˱��ҵı�ȫɨ������ Ŀǰֻ�пͻ����µĹ�˾�����������֪
   /* var tFlagSQL="select createoperator from lbmission a where missionprop1='"+fm.EdorAcceptNo.value+"' and activityid='0000000001' and exists(select 1 from lduwuser where usercode=a.createoperator) ";*/
    mySql=new SqlClass();
    mySql.setResourceName("bq.PEdorApp");
    mySql.setSqlId("PEdorAppSql12");
    mySql.addSubPara(fm.EdorAcceptNo.value);       
    
    var tFlag = easyExecSql(mySql.getString());
    if (tFlag != null && tFlag != '' && fm.AppType.value != '6') {
    	alert("�˱��ҵı�ȫɨ������"+"(����ԱΪ"+tFlag[0][0]+")"+"�����뷽ʽ����ѡ���ڲ�ת��!");
        return;
    }
    //add by jiaqiangli 2009-11-19 �˱��ҵı�ȫɨ������ Ŀǰֻ�пͻ����µĹ�˾�����������֪
      //���ڿͻ���Ҫ���ϱ���Ĳ��ܽ����κα�ȫ���룬���У��������أ�
		//  var strSQL =" select 'X' from lpconttempinfo where state='0' and contno='"+tOtherNo+"'";
		//	var brr = easyExecSql(strSQL);
		//	if ( brr )
		//	{
		//		alert("�˱������ڽ��пͻ�����Ҫ���ϱ�������Ƚ��пͻ���Ҫ���ϱ��");		
    //    return false;
		//	}
    //У�鱣���Ƿ��д��պ�������;
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
	      	    alert("�˱�����������Ӧ�գ�����������;����ʱ�����Խ��б�ȫ����!");
	            return;
	      }else 
	      	{
	      	if(!confirm("�˱�����������Ӧ�գ��Ƿ����"))
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
				+ " union " // ��Ͷ���˻򱻱��������صı���
				+ " select contno from lcappnt where appntno = '"
				+ tOtherNo
				+ "' ) "
				+ " and "
				+ " ( " // ����δ��ֹ����������δʧЧ��ʧЧ����������ͻ�������
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
				+ " ( appflag = '4' and exists " // 01-������ֹ��05-�Ե���ֹ��06-������ֹ ���Բ���ͻ�����
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
	      	    alert("�˿ͻ����±�����������Ӧ�գ�����������;����ʱ�����Խ��б�ȫ����!");
	      	    if (window.confirm("�Ƿ��ӡ���α�ȫ��ҵ�������Ѻ�?"))
	            {
	                  fm.action = "../f1print/CusBQHungupNotice.jsp";
	                  fm.target="f1print";
	                  document.getElementById("fm").submit();
	            }
	            return ;
	      }
	      else 
	      {
	      	if(!confirm("�˿ͻ����±�����������Ӧ�գ��Ƿ����"))
	      	{
	      		   return;
	      	}     		
	      }
	    }
    }
    //��̫������ذ�ť�����鲻ʹ��
    //if (!verifyInput2()) return;

    if(fm.EdorApp.value=='' || fm.EdorApp.value==null)
    {
    	      alert("�����������������Ϣ!");
            return;
    }
    
    if(fm.EdorApp.value=='����' && (fm.EdorAppName.value==null|| fm.EdorAppName.value==''))
    {
    	      alert("�����������������Ϣ!");
            return;
    }
    
     //add by jiaqianglee 2009-02-18 ��ʱ����У�飬������Ҫ����ֱ�ӷſ�����
    if (fm.CustomerPhone.value != null && fm.CustomerPhone.value != '') {
    if (verifyTel("�����ʸ�����ϵ�绰",fm.CustomerPhone.value) == false)  {
        alert("�����ʸ�����ϵ�绰¼�벻�Ϸ�!");
    	return false;
    }
    }    	 
    
     if (fm.BfPhone.value != null && fm.BfPhone.value != '') {
    if (verifyTel("��������ϵ�绰",fm.BfPhone.value) == false)  {
        alert("��������ϵ�绰¼�벻�Ϸ�!");
    	return false;
    }
    } 
    
    //add by jiaqiangli 2009-02-18 �����ʸ�����ϵ�绰
    //ֻУ�����������˵����֤�� ҵ��Ա������Ϣ����������У��
    if (fm.AppType.value=='3') {
    if (fm.BfIDType.value != null && fm.BfIDType.value == '0') {
    	if (fm.BfIDNo.value != null && fm.BfIDNo.value != '') {
    		if(!checkIdCard(fm.BfIDNo.value)) {
    			alert("���������֤������¼������");
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
    		    alert("����¼��ҵ��Ա���еĴ�����Ϣ����ȷ���Ƿ�¼���˴�������ϵ�绰��");
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
    		    alert("����¼�������˴���Ĵ�����Ϣ��");
    			return false;
    	}
    }
     if (fm.AppType.value=='6') {
    	if(  (fm.InternalSwitchChnl.value==null|| fm.InternalSwitchChnl.value=='')
    	   || (fm.InternalSwitchChnlName.value==null|| fm.InternalSwitchChnlName.value=='')
    	   ){
    		    alert("����¼���ڲ�ת��������Ϣ��");
    			return false;
    	}
    }
    //if (fm.CustomerPhone.value == null || fm.CustomerPhone.value == '') {
    //	if (window.confirm("�����ʸ�����ϵ�绰Ϊ���Ƿ����?") == false) {
    //			return;
    //        }
    //}
    
   
   		if((fm.CustomerPhone.value == null || trim(fm.CustomerPhone.value) == '')&&(fm.Mobile.value == null || trim(fm.Mobile.value) == '')&&(fm.PostalAddress.value == null || trim(fm.PostalAddress.value) == '')){
   			if(!confirm("�����ʸ�����ϵ��ʽ¼�벻��ȫ���Ƿ������")){
   				return;
   			}
   		}
   		
   		if(fm.Mobile.value != null && trim(fm.Mobile.value) != ''){
   			var valid= /^[0-9]\d{10}$/;
   			if(!valid.test(fm.Mobile.value)){
   					alert("�����ʸ����ֻ����벻�Ϸ�!");
   					return;
   			}
   		}
   		if(fm.PostalAddress.value != null && trim(fm.PostalAddress.value) != ''){
   			if(fm.ZipCode.value == null ||trim(fm.ZipCode.value) == '' ){
   					alert("��ַ��Ϣ���ʱ����ͬʱ¼��!");
   					return;
   			}
   		}
   		if(fm.ZipCode.value != null && trim(fm.ZipCode.value) != ''){
   			var valid= /^\d{6}$/;
   		  if(!valid.test(fm.ZipCode.value)){
   					alert("�ʱ��ʽ���Ϸ�!");
   					return;
   			}
   		}

  	
    
    fm.fmtransact.value = "INSERT||EDORAPP";
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

	
    document.getElementById("fm").submit(); //�ύ
    //add by jiaqiangli 2009-02-18 �����ʸ�����ϵ�绰
}

/*********************************************************************
 *  ��ִ̨����Ϸ�����Ϣ
 *  ����: ��ִ̨����Ϸ�����Ϣ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content )
{
    showInfo.close();

    fm.AddItem.disabled = false;

    if (FlagStr == "Succ" )
    {
        if (fm.fmtransact.value == "INSERT||EDORAPP") //��ȫ������
        {
            initForm();
        }
        if (fm.fmtransact.value == "INSERT||EDORITEM") //��ӱ�ȫ��Ŀ
        {
            displayCustomerInfo(document.all('OtherNo').value);
            EdorItemGrid.clearData();
            getEdorItemGrid();
            //XinYQ added on 2006-05-15 : �Զ�������ϸ����
            EdorItemGrid.selOneRow(1);
            gotoDetail();
        }
        if (fm.fmtransact.value == "DELETE||EDORITEM") //������ȫ��Ŀ
        {
            displayCustomerInfo(document.all('OtherNo').value);
            EdorItemGrid.clearData();
            getEdorItemGrid();
            clearLastEdorInfo();
        }
        //alert(fm.fmtransact.value );
        if (fm.fmtransact.value == "INSERT||EDORAPPCONFIRM") //����ȷ��
        {
            var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
            //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

			
            //alert(fm.EdorType.value);
            if(fm.EdorType.value!='RB')
            {
            if (window.confirm("�Ƿ��ӡ���α�ȫ����������?"))
            {
                  fm.action = "../f1print/AppEndorsementF1PJ1.jsp";
                  fm.target="f1print";
                  document.getElementById("fm").submit();
            }
            }
            initForm();

        }
        if (fm.fmtransact.value == "DELETE||EDORTESTFINISH") //�������
        {
            returnParent();
        }
        if (fm.fmtransact.value == "UPDATE||EDORPERSON") //�������
        {
            var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
            //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
			
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

			
            initForm();
        }
    }
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();

        if (fm.fmtransact.value == "INSERT||EDORAPPCONFIRM") //����ȷ��
        {
            initForm();
            // Ӧ��ӨҪ�� �ſ������ʾ modify by jiaqiangli 2009-04-23
            if (window.confirm("�Ƿ��ӡ���α�ȫ����������?"))
            {
                  fm.action = "../f1print/AppEndorsementF1PJ1.jsp";
                  fm.target="f1print";
                  document.getElementById("fm").submit();
            }
        }
        if (fm.fmtransact.value == "INSERT||EDORAPP") //��ȫ����
        {
            if(CusBQPrintFlag=="1")
            {
	            if (window.confirm("�Ƿ��ӡ���α�ȫ��ҵ�������Ѻ�?"))
	            {
	                  fm.action = "../f1print/CusBQHungupNotice.jsp";
	                  fm.target="f1print";
	                  document.getElementById("fm").submit();
	            }
            }
        }
    }

}
//�жϿͻ��㱣ȫ����ʧ���Ƿ���Ҫ��ӡ��ȫҵ�������Ѻ�(�ͻ��ŵ��´��ڱ���������������ʧ�ܵ�ʱ���ж�)
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
 *  ��ѯ��ȫ��Ŀ��д��MulLine
 *  ����  ��  ��
 *  ����ֵ��  ��
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

        if (tOtherNoType=='3') //���˱���������
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
        else if (tOtherNoType=='1') //���˿ͻ�������
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
        showDivGetPayForm();//��ʾ�ո��ѷ�ʽ��Ϣ
    }
}

/*********************************************************************
 *  �س�ʵ�ֲ�ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
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

    //�س���ascii����13
    if (keycode=="13")
    {
        var tOtherNoType = fm.OtherNoType.value;
        var tOtherNo = fm.OtherNo.value;

        if(tOtherNoType == null || tOtherNoType == "")
        {
            alert("������������ͣ�");
            return;
        }
        if(tOtherNo == null || tOtherNo == "")
        {
            if(tOtherNoType == "1")
            {
                alert("������ͻ����룡");
                return;
            }
            if(tOtherNoType == "3")
            {
                alert("�����뱣�����룡");
                return;
            }
        }
        if(tOtherNoType == "1")
        {
            displayCustomerInfo(tOtherNo);
            divCustomer.style.display='';       //�ͻ���Ϣ
            divCont.style.display="none";       //������Ϣ
            divContState.style.display="none";  //����״̬��Ϣ
            if(fm.CustomerNo.value == null || fm.CustomerNo.value == "")
            {
                alert("�ͻ��Ų�����!");
            }
        }
        if(tOtherNoType == "3")
        {
            displayContInfo(tOtherNo);
            divCustomer.style.display="none";   //�ͻ���Ϣ
            divCont.style.display='';           //������Ϣ
            divContState.style.display='';  //����״̬��Ϣ
            if(fm.ContNoApp.value == null || fm.ContNoApp.value == "")
            {
                alert("�����Ų����ڣ� ");
                clearContStateInfo();
            }
        }
    }
}

/*********************************************************************
 *  ����
 *  ����: ����
 *  ����  ��  ��
 *  ����ֵ��  ��
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
 *  �ͻ���ѯ
 *  ����: �ͻ���ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function customerQuery()
{
  mflag = "1";
    var showInfo = window.open( "../sys/LDPersonQueryNew.html","LDPersonQueryNew",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
    showInfo.focus();
}

/*********************************************************************
 *  ������ѯ
 *  ����: ������ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
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
 *  ����״̬��ѯ
 *  ����: ����״̬��ѯ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function ContStateQuery()
{

//   strSql = "select statetype,state from lccontstate where contno='"+document.all('ContNoApp').value+"' and (statetype='Lost' or statetype='Loan'or statetype='BankLoan')";
   var strSql = "";
   var sqlid1="PEdorAppSql54";
   var mySql1=new SqlClass();
   mySql1.setResourceName("bq.PEdorApp"); //ָ��ʹ�õ�properties�ļ���
   mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
   mySql1.addSubPara(document.all('ContNoApp').value);//ָ������Ĳ���
   strSql=mySql1.getString();
   var arrResult = easyExecSql(strSql, 1, 0);

   if (arrResult == null)
   {
       //alert("û����Ӧ�ı�����Ϣ");
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
 *  �������
 *  ����: ��������ύ��̨�����������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function EdorTestFinish()
{
    document.all("fmtransact").value = "DELETE||EDORTESTFINISH";

    var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();


    fm.action= "./PEdorTestFinishSubmit.jsp";
    document.getElementById("fm").submit();
}
/*********************************************************************
 *  �����ո��ѷ�ʽ��Ϣ����
 *  ����: �����ո��ѷ�ʽ��Ϣ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDivGetPayForm()
{
    if(fm.LoadFlag.value == "edorApp" || fm.LoadFlag.value == "scanApp" || fm.LoadFlag.value == "approveModify" ||  fm.LoadFlag.value=="CM")
    {
        var nCountNo = EdorItemGrid.mulLineCount;
        if(nCountNo > 0)
        {
          //ֻ������˱�ȫ��Ŀʱ���ո���¼�������Ż����
//          alert();
          if(EdorItemGrid.getRowColData(0,20)!=null&&(EdorItemGrid.getRowColData(0,20)=="CT"||EdorItemGrid.getRowColData(0,20)=="XT"||EdorItemGrid.getRowColData(0,20)=="LG"))
          {
        	  //ʲô������
          }
          //�����˱���Э���˱���������ȡ����ʾ
          else{
        	  divGetPayFormInfo.style.display="";      //�ո��ѷ�ʽ��Ϣ
          }
          
          initGetPayForm();                      //��ʼ���ո��ѷ�ʽ��Ϣ
        }
        else
        {
            divGetPayFormInfo.style.display="none";   //�ո��ѷ�ʽ��Ϣ
        }

    }
    else
    {
        divGetPayFormInfo.style.display="none";   //�����ո��ѷ�ʽ��Ϣ
    }
}
/*********************************************************************
 *  ��ʼ���ո��ѷ�ʽ��Ϣ
 *  ����: ��ʼ���ո��ѷ�ʽ��Ϣ����ÿ�γ�ʼ��������fm.GetPayForm�õ�����ʱ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function initGetPayForm()
{
   var tGetPayForm = fm.GetPayForm.value;
   if(tGetPayForm == null || tGetPayForm == "")
   {
      fm.GetPayForm.value = "1";        // Ĭ��Ϊ�ֽ�ʽ
      fm.GetPayFormName.value = "�ֽ�";
   }
   //9�������� add by jiaqiangli 2009-05-21
   if(tGetPayForm == "4"||tGetPayForm == "9")//���л���,����֧��
   {
      divBankInfo.style.display='';
      if(fm.BankCode.value == null || fm.BankCode.value == "")
      {
          //�����δ¼������е���Ϣ����ȡĬ��ֵ
          if(fm.OtherNoType.value == "1")
          {
              //�ͻ�����
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
                //������ڽ����ȡ�������ʺ�
               /* strsql = "select Getbankcode,(select bankname from ldbank where bankcode = a.Getbankcode and rownum=1),"
                       + " Getbankaccno,Getaccname from lcpol a "
                       + " where polno = (select polno from lpedoritem where edortype = 'AG' and edoraccetpno = '"+fm.EdorAcceptNo.value+"') ";
              */
               mySql.setSqlId("PEdorAppSql18");
               mySql.addSubPara(fm.EdorAcceptNo.value);           
            }
            else
            {
                //ȡͶ�����ʺ�
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
 * ���ϵͳ�Ƿ����ñ�������
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
        alert("���棺���ϵͳ�Ƿ����ñ������빦�ܳ����쳣�� ");
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
 * ��鱣ȫ��Ŀ�Ƿ���Ҫ��������
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
    mySql1.setResourceName("bq.PEdorApp"); //ָ��ʹ�õ�properties�ļ���
    mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    mySql1.addSubPara(window.document.getElementsByName("EdorType")[0].value);//ָ������Ĳ���
    QuerySQL=mySql1.getString();
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��鱣ȫ��Ŀ�Ƿ���Ҫ������������쳣�� ");
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
 * ����Ƿ���Ҫ���뱣�������Լ����������Ƿ���ȷ
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
            alert("�޷���ȡ��ȫ������Ϣ����ѯ����������Ϣʧ�ܣ� ");
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
            alert("���棺��ѯ��Ҫ��������ı�����Ϣ�����쳣�� ");
            return false;
        }
        if (arrResult != null)
        {
            //����ҳ����������
            var VerifyPwdURL = "PEdorAppPwdVerifyMain.jsp";
            var VerifyPwdParam = "EdorAcceptNo=" + sEdorAcceptNo + "&OtherNo=" + sOtherNo + "&OtherNoType=" + sOtherNoType + "&EdorItemAppDate=" + sEdorItemAppDate + "&AppType=" + sAppType;
            //var VerifyPwdFlag = showModalDialog(VerifyPwdURL + "?" + VerifyPwdParam, window, "status=0; help=0; close=0; dialogWidth=850px; dialogHeight=600px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=800;      //�������ڵĿ��; 
			var iHeight=600;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 

			var VerifyPwdFlag=window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
            if (!VerifyPwdFlag)
            {
                return false;
            }
        }
    }
    return true;
}

//XinYQ added on 2006-11-09 : �������б�ȫ��Ŀ֮�����������ʾ
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
* �˱�״̬��ѯ
*/
function  UWQuery()
{
    var pEdorAcceptNo=fm.EdorAcceptNo.value;
    window.open("./EdorErrorUWQueryMain.jsp?EdorAcceptNo="+pEdorAcceptNo,"window1");
}

/*********************************************************************
 *  ��ӱ�ȫ���У��
 *  ����: ��ȫ���У��
 *  ����  ��  ��
 *  ����ֵ��  ������
 *********************************************************************
 */
function chkEdorType()
{
		var tEdorType = document.all('EdorType').value;//��ȫ����
		//alert(tEdorType);
		if(tEdorType == "LR")
		{
			//alert("����LR");
			var tLost = document.all('Lost').value;
			if(tLost=="��ʧ")
			{
				return confirm("�ñ������ڹ�ʧ״̬�����иñ�ȫ��Ŀ���Զ�Ϊ���ң��Ƿ����������");
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
  			  alert("�ͻ���Ҫ���ϱ����ʱ������쳣��");
  			  return false;
  		  }
  	   if (isCM !=null )
  		 {
   			   if(!canApply(tEdorType))
   			   {
		          return false;
   			   }

  	   }
		
		
		//�ͻ�����Ҫ���ϱ��ֻ��ѡ��IC��Ŀ
		//alert(fm.LoadFlag.value);
		if(tPreLoanFlag=="CM" && tEdorType != "IC")
		{
			alert("�ͻ�����Ҫ���ϱ������ѡ�񱻱�����Ҫ���ϱ��(IC)");
			return false;
		}
		if(tEdorType == "CT")
		{
			//������Ե���ʾ����������Ͷ�����Ƿ�ֱϵ�����Ĺ�ϵ��
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
	  			alert("��ѯУ��Ͷ�����뱻���˹�ϵʱ�����쳣��");
	  			return false;
	  		}
	  		if (ret != null && ret[0][0] == "1")
	  		{
	  			return confirm("�ñ����µ�Ͷ�����뱻���˴��ڷ�ֱϵ������ϵ���Ƿ����������");	
	  		}
			//return false;
		}
		
		if(tEdorType == "XT" || tEdorType == "XS")
		{
			//������Ե���ʾ�����ִ�����ԥ�ڣ��Ƿ����Э���˱���
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
	  			alert("��ѯ�����ص������쳣");
	  			return false;
	  		}
	  		if (ret != null && ret[0][0] == "0")
	  		{
	  			if (tEdorType == "XT")
	  			return confirm("����������ԥ���ڣ��Ƿ����Э���˱�!");	
	  			
	  			//add by jiaqiangli 2009-05-14
	  			if (tEdorType == "XS")
	  			return confirm("����������ԥ���ڣ��Ƿ����Э�����!");
	  		}
			//return false;
		}
		
		//�ͻ����ϱ���ı����������ֻ������б����˲�������ְҵ���
		if(tEdorType=="IO")
		{
	        var tInsuredType; 
	        if (InsuredGrid.getSelNo()>0)
	        {
	          tInsuredType=InsuredGrid.getRowColData(InsuredGrid.getSelNo()-1, 1); 
	          //alert(tInsuredType);
	          if(tInsuredType=='Ͷ����')
	          {
	             alert("�˱�ȫ��Ŀ���ֻ�ܶԱ����˽��в�����������ѡ����򷵻�");
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
                 			//�ͻ�����Ҫ������Ҫ����CM����
                 			
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
	  			  alert("��ѯ�ͻ���Ҫ���ϱ����ʱ������쳣��");
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
	   			     alert("�ͻ���Ҫ���ϱ�������ͻ����������±���"+tTempContNo+"δ�����걣����ͻ����ϱ�����������������Щ����");
	  			     return false;
	  	        }
            }
            //alert(InsuredGrid.getSelNo());
            if (InsuredGrid.getSelNo()>0)
	         {
	               tInsuredNo=InsuredGrid.getRowColData(InsuredGrid.getSelNo()-1, 2); 
	    
	                 			//�ͻ�����Ҫ������Ҫ����CM����
	         mySql=new SqlClass();
           mySql.setResourceName("bq.PEdorApp");
           mySql.setSqlId("PEdorAppSql40");
           mySql.addSubPara(tInsuredNo);          			
	       /*          			
				   var rCMSQL = " select ContNo   "
				                  +"	 from lpedoritem    "
				                  +"	 where "            //��ѯ�˿ͻ������ڲ�����CM����Ŀ����״̬��Ϊ������׼
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
		  			  alert("��ѯ�ͻ���Ҫ���ϱ�����ı�����쳣��");
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
		   			 alert("�ͻ���Ҫ���ϱ�������ͻ����������±���"+tTempContNo+"���ڽ��б�����ͻ�����(CM)������뷵��");
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
	  			alert("�ͻ���Ҫ���ϱ����ʱ������쳣��");
	  			return false;
	  		 }
//	  	    if (result == null )
//	  		{
//	   			 alert("��������Ҫ���ϱ������������δ���пͻ������ϱ��������Ҫ���пͻ�����Ҫ���ϱ��( CM )����");
//	  			 return false;
//	  	    }
//	  	    if (result != null  && tPreLoanFlag!="CM" )
//	  		{
//	   			 alert("��������Ҫ���ϱ�������������ڽ��б�����ͻ����ϱ������ӱ�����ͻ����ϱ���˵���������");
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
    			alert("���α�ȫ�ɾܱ����ܷ��𣡲������Э���˱���XT�����˱���CT������ı�ȫ��!");
    			return false;
    		}
			}
		}
		if(tEdorType == "CS"){
			try{
				var parry = top.fraPic.arrPicName;
				if(parry == null){
					alert("��ɨ��״̬�²��ܽ��б��/��ǩ����");
					return false;
				}
			}catch(ex){
				alert("��ɨ��״̬�²��ܽ��б��/��ǩ����");
				return false;
			}
		}
		return true;
}
/**
Ϊʵ�ֿͻ�����Ҫ���ϱ�����
*/
function initCM()
{
	if(fm.LoadFlag.value=="CM")
	{
		tPreLoanFlag="CM";
		fm.LoadFlag.value="edorApp";
	  document.all('EdorType').value="IC";
	  document.all('EdorTypeName').value="��������Ҫ���ϱ��";
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
���ڲ����ͻ������ϱ������Ŀ�������һ����û�д�����ڶ��������Խ��еı�ȫ��Ŀ����Щ��Ŀ
�����ǲ�Ҫ����ģ��򵥵ı�ȫ��Ŀ����LDCode�������ʱ�����Բ����ı�ȫ��Ŀ����CT,XT,PT,IO,LN,AA,NS�ȵ�
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
  			  alert("��ѯʧ��");
  			  return false;
  		  }
  	   if (result != null )
  		 {
  			  alert("�ͻ���Ҫ���ϱ����δ��ɣ���ʱ���ܲ���"+tEdorType+"��");
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
        alert("��ȫ�����Ѿ�����ȷ��");
        return ;
      }
      if (fm.PEdorState.value == 'c')
    {
        alert("��ȫ�����Ѿ���ֹ");
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
        alert("��ѯ��ȫ��Ŀʧ��,������ӱ�ȫ��Ŀ�ٽ��б�ȫ��������!");
        return;
    }

	    var pEdorAcceptNo=fm.EdorAcceptNo.value;
	    //alert(fm.MissionID.value);
	    var varSrc="&EdorAcceptNo=" + pEdorAcceptNo + "&MissionID=" + fm.MissionID.value + "&SubMissionID=" +fm.SubMissionID.value;
	    //alert(varSrc);
	    var newWindow = OpenWindowNew("../bq/EdorNoticeFrame.jsp?Interface= ../bq/EdorNoticeInput.jsp" + varSrc,"��ȫ���","left");
}

/**
 * �����ʺŹ���
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
