var showInfo;
var mDebug = "1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mBnfBatNo=0;
var tDeadFlag = 0;
var mySql = new SqlClass();

//��ѯ���ݿ������������
function queryBnfBatNo()
{
    var strSQL = "";
    mBnfBatNo=0;
    
    if (fm.BnfKind.value == 'A')
    {
    	/*strSQL="select nvl(max(bnfno/1),0) from llbnfgather a where 1=1 "
 		   + " and bnfkind!='B'"
 		   //+ " and insuredno='"+document.all('insuredno').value+"'"
           + getWherePart( 'ClmNo','ClmNo' )
               + " order by ClmNo";*/
    	var  ClmNo1 = window.document.getElementsByName(trim("ClmNo"))[0].value;
    	var sqlid1="LLBnfSql1";
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql1");
		mySql.addSubPara(document.all('insuredno').value); 
		mySql.addSubPara(ClmNo1);
        //prompt("�������̲�ѯ���������",strSQL);
        
        arr = easyExecSql( mySql.getString() );
    }
    else if (fm.BnfKind.value == 'B')
    {
        /*strSQL = "select nvl(max(bnfno/1),0) from llbnfgather a where 1=1 "
     	   + " and bnfkind='B'"
     	   //+ " and insuredno='"+document.all('insuredno').value+"'"
           + getWherePart( 'ClmNo','ClmNo' );*/
    	var  ClmNo2 = window.document.getElementsByName(trim("ClmNo"))[0].value;
    	var sqlid1="LLBnfSql2";
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql2");
		mySql.addSubPara(document.all('insuredno').value); 
		mySql.addSubPara(ClmNo2);
        //prompt("Ԥ��������˽׶������˷����ʼ�����",strSQL);
        
        arr = easyExecSql( mySql.getString() );
        
    }
    
    if (arr)
    {
        mBnfBatNo=arr[0][0];
    }
//    alert("insuredno="+fm.insuredno.value);
    //alert("mBnfBatNo*****:"+mBnfBatNo);

}

//��ѯ�ⰸ������ϸ
function queryLLBalanceGrid()
{
    var strSQL = '';
    var arr = '';
    if (fm.BnfKind.value == 'A')
    {
//        strSQL = "select ClmNo,polno,sum(pay),GrpContNo,GrpPolNo,ContNo,'0',(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'A') when 0 then 0 else 1 end),(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'A') when 0 then 'δ����' else '�ѷ���' end) from LLBalance a where 1=1 "
//                   + getWherePart( 'ClmNo','ClmNo' )
//                   + " and exists(select 1 from lcpol b where a.polno=b.polno and b.insuredno='"+document.all('insuredno').value+"')"
//                   + " group by ClmNo,polno,GrpContNo,GrpPolNo,ContNo "
//                   + " order by ClmNo";
        
       /* strSQL = "select ClmNo,polno,feeoperationtype,(select distinct baltypedesc from llbalancerela where baltype=feeoperationtype),"
        	   +" sum(pay)+(select nvl(sum(pay),0) from llbalance where clmno='"+fm.ClmNo.value+"' and feeoperationtype='B' and grpcontno=a.grpcontno and grppolno=a.grppolno and contno=a.contno and polno=a.polno and a.feeoperationtype='A') pay,"
        	   +" GrpContNo,GrpPolNo,ContNo,'0',"
        	   +" (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'A' and a.feeoperationtype=b.feeoperationtype) when 0 then 0 else 1 end),(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'A' and a.feeoperationtype=b.feeoperationtype) when 0 then 'δ����' else '�ѷ���' end)"
        	   +" ,customerno "
        	   +" from LLBalance a where 1=1 "
               + getWherePart( 'ClmNo','ClmNo' )
               + " and exists(select 1 from lcpol b where a.polno=b.polno and b.insuredno='"+document.all('insuredno').value+"'  union  select 1 from lcinsuredrelated b where a.polno=b.polno and b.customerno='"+document.all('insuredno').value+"'  union  select 1  from lcpol b,lmrisksort c where a.contno = b.contno  and b.appntno ='"+document.all('insuredno').value+"' and b.riskcode=c.riskcode and c.risksorttype='18')"
               + " and feeoperationtype!='B'"
               + " group by ClmNo,polno,feeoperationtype,GrpContNo,GrpPolNo,ContNo,customerno "
               + " order by pay desc";*/
          mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql3");
		mySql.addSubPara(fm.ClmNo.value );   
		mySql.addSubPara(fm.ClmNo.value ); 
		mySql.addSubPara(document.all('insuredno').value ); 
		mySql.addSubPara(document.all('insuredno').value); 
		mySql.addSubPara(document.all('insuredno').value);     
        //prompt("����������˽׶������˷����ʼ����ѯ",strSQL);
        
        arr = easyExecSql( mySql.getString() );
    }
    else if (fm.BnfKind.value == 'B')
    {
//        strSQL = "select ClmNo,polno,abs(sum(pay)),GrpContNo,GrpPolNo,ContNo,'0',(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'B') when 0 then 0 else 1 end),(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'B') when 0 then 'δ����' else '�ѷ���' end) from LLBalance a where 1=1 "
//               + getWherePart( 'ClmNo','ClmNo' )
//               + " and exists(select 1 from lcpol b where a.polno=b.polno and b.insuredno='"+document.all('insuredno').value+"')"
//               + " and FeeOperationType = 'B'"
//               + " group by ClmNo,polno,GrpContNo,GrpPolNo,ContNo " 
//               + " order by ClmNo";
        
        /*strSQL = "select ClmNo,polno,feeoperationtype,(select distinct baltypedesc from llbalancerela where baltype=feeoperationtype),"
        	+ " abs(sum(pay)) pay,GrpContNo,GrpPolNo,ContNo,'0',"
        	+ " (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'B' and a.feeoperationtype=b.feeoperationtype) when 0 then 0 else 1 end),(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'B' and a.feeoperationtype=b.feeoperationtype) when 0 then 'δ����' else '�ѷ���' end)"
        	+ " ,customerno "
        	+ " from LLBalance a where 1=1 "
            + getWherePart( 'ClmNo','ClmNo' )
            + " and exists(select 1 from lcpol b where a.polno=b.polno and b.insuredno='"+document.all('insuredno').value+"'  union  select 1 from lcinsuredrelated b where a.polno=b.polno and b.customerno='"+document.all('insuredno').value+"' union  select 1  from lcpol b,lmrisksort c where a.contno = b.contno  and b.appntno ='"+document.all('insuredno').value+"' and b.riskcode=c.riskcode and c.risksorttype='18')"
            + " and FeeOperationType = 'B'"
            + " group by ClmNo,polno,feeoperationtype,GrpContNo,GrpPolNo,ContNo ,customerno"
            + " order by pay desc";*/
        mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql4");
		mySql.addSubPara(fm.ClmNo.value );   
		mySql.addSubPara(document.all('insuredno').value ); 
		mySql.addSubPara(document.all('insuredno').value); 
		mySql.addSubPara(document.all('insuredno').value); 
//        prompt("Ԥ��������˽׶������˷����ʼ����ѯ",strSQL);
        
        arr = easyExecSql( mySql.getString() );
        
    }
    if (arr)
    {
        displayMultiline(arr,LLBalanceGrid);
    }
//    turnPage.queryModal(strSQL, LLBalanceGrid);

    /**-----------------------------------------------------------------------------------BEG
     * ��ʼ���ⰸ��ȡ���(1һ��ͳһ���� 2 �����ʽ��ȡ 3 ����֧��)
     * add ���� 2006-3-3 9:23
     *-----------------------------------------------------------------------------------*/
   /* var tQusql = "select GetMode from llregister a where 1=1 "
               + getWherePart( 'rgtno','ClmNo' );*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLBnfInputSql");
	mySql.setSqlId("LLBnfSql5");
	mySql.addSubPara(fm.ClmNo.value );   
	
    var tFlag = easyExecSql( mySql.getString() );
    if (tFlag == "2")
    {
        fm.ClmFlag.value = "2"; //�������ȡ
    }
    //------------------------------------------------------------------------------------END
}

//��ѯ�����˷�����ϸ
function queryLLBnfGrid()
{
    var i = LLBalanceGrid.getSelNo() - 1;
    initLLBnfGrid();
    //---------------------1-----2--------3-------4--------5------6------7-------8--------9-------10---------11-----------12---13--------14----15---------16----------17----------18----------19---------20--------21------22-----23
    /*var strSQL1 = "select clmno,polNo,insuredno,bnfno,customerNo,name,payeeNo,payeename,bnftype,bnfgrade,relationtoinsured,sex,birthday,idtype,idno,relationtopayee,payeesex,payeebirthday,payeeidtype,payeeidno,getmoney,bnflot,CasePayMode,BankCode,BankAccNo,AccName from LLBnf where "
                + " clmno = '" + LLBalanceGrid.getRowColData(i,1) + "'"
                + " and PolNo = '" + LLBalanceGrid.getRowColData(i,2) + "'"
                + " and BnfKind = '" + fm.BnfKind.value + "'"
                + " and insuredno = '"+ fm.insuredno.value+"'"
                + " order by clmno";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLBnfInputSql");
	mySql.setSqlId("LLBnfSql6");
	mySql.addSubPara(LLBalanceGrid.getRowColData(i,1));   
	mySql.addSubPara( LLBalanceGrid.getRowColData(i,2));  
	mySql.addSubPara(fm.BnfKind.value );  
	mySql.addSubPara(fm.insuredno.value );  
//    prompt("��ѯ�����˷�����ϸsql",strSQL1);
    var arr1 = easyExecSql(  mySql.getString()  );
    if (arr1)
    {
        displayMultiline(arr1,LLBnfGrid);
//        turnPage.queryModal(strSQL, LLBnfGrid);
    }
    //���ÿɲ�����ť
    fm.addButton.disabled = false;
//    fm.updateButton.disabled = true;
    fm.deleteButton.disabled = false;
    fm.saveButton.disabled = false;
}

//ѡ��LLBalanceGrid��Ӧ�¼�
function LLBalanceGridClick()
{
    //��ձ�
	//alert( fm.insuredno.value);
    emptyInput();
    
	//---------------------------------------------------------------------------------------------beg
	//����ⰸ������ϸ��ϸ��Ϣ
    //---------------------------------------------------------------------------------------------
    var i = LLBalanceGrid.getSelNo();
   // alert(i);
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo2.value = LLBalanceGrid.getRowColData(i,1);
        fm.polNo.value = LLBalanceGrid.getRowColData(i,2);
        fm.Currency.value = LLBalanceGrid.getRowColData(i,5);
        fm.sumPay.value = LLBalanceGrid.getRowColData(i,6);
        fm.BatNo.value = LLBalanceGrid.getRowColData(i,10);
        fm.FeeOperationType.value = LLBalanceGrid.getRowColData(i,3)
        fm.ClmNo.value = LLBalanceGrid.getRowColData(i,1);
//        //�ж����������
//        if (fm.BnfKind.value == 'A' && fm.sumPay.value < 0)
//        {
//            alert("�˱������ܷ���������!");
//            disabledButton();
//            fm.goBack.disabled = false;
//            return;
//        }
        
        if (LLBalanceGrid.getRowColData(i,3) == "" || LLBalanceGrid.getRowColData(i,3) == null)
        {
            fm.FeeOperationType.value = 'A';
        }
        else
        {
            fm.FeeOperationType.value = LLBalanceGrid.getRowColData(i,3);
        }
        
        
        if (LLBalanceGrid.getRowColData(i,7) == "" || LLBalanceGrid.getRowColData(i,7) == null)
        {
            fm.GrpContNo.value = '00000000000000000000';
        }
        else
        {
            fm.GrpContNo.value = LLBalanceGrid.getRowColData(i,7);
        }
        
        if (LLBalanceGrid.getRowColData(i,8) == "" || LLBalanceGrid.getRowColData(i,8) == null)
        {
            fm.GrpPolNo.value = '00000000000000000000';
        }
        else
        {
            fm.GrpPolNo.value = LLBalanceGrid.getRowColData(i,8);
        }
        
        if (LLBalanceGrid.getRowColData(i,9) == "" || LLBalanceGrid.getRowColData(i,9) == null)
        {
            fm.ContNo.value = '00000000000000000000';
        }
        else
        {
            fm.ContNo.value = LLBalanceGrid.getRowColData(i,9);
        }
        
        if (LLBalanceGrid.getRowColData(i,2) == "" || LLBalanceGrid.getRowColData(i,2) == null)
        {
            fm.PolNo.value = '00000000000000000000';
        }
        else
        {
            fm.PolNo.value = LLBalanceGrid.getRowColData(i,2);
        }
        
        fm.insuredno.value= LLBalanceGrid.getRowColData(i,13);
        
    }
    
    var tInsuredNo = fm.insuredno.value;
    //alert( fm.insuredno.value);
    //---------------------------------------------------------------------------------------------end

	//---------------------------------------------------------------------------------------------beg
	//��ѯ�����������˻���Ϣ,���mulline
    //---------------------------------------------------------------------------------------------
    document.all('divLLBalance').style.display="";
    initLLBnfGrid();
    //---------------------1-----2--------3-------4--------5------6------7-------8--------9-------10---------11-----------12---13--------14----15---------16----------17----------18----------19---------20--------21------22-------23
    /*var strSQL1 = "select clmno,polNo,insuredno,bnfno,customerNo,name,payeeNo,payeename,bnftype,bnfgrade,relationtoinsured,sex,birthday,idtype,idno,relationtopayee,payeesex,payeebirthday,payeeidtype,payeeidno,getmoney,bnflot,CasePayMode,BankCode,BankAccNo,AccName,CasePayMode,FeeOperationType,OBankCode from LLBnf where "
                + " clmno = '" + LLBalanceGrid.getRowColData(i,1) + "'"
                + " and PolNo = '" + LLBalanceGrid.getRowColData(i,2) + "'"
                + " and BnfKind = '" + fm.BnfKind.value + "'"
                + " and FeeOperationType='"+fm.FeeOperationType.value+"'"
                + " and insuredno='"+fm.insuredno.value+"'"
                + " order by clmno";*/
    mySql = new SqlClass();
	mySql.setResourceName("claim.LLBnfInputSql");
	mySql.setSqlId("LLBnfSql7");
	mySql.addSubPara(LLBalanceGrid.getRowColData(i,1));   
	mySql.addSubPara( LLBalanceGrid.getRowColData(i,2));  
	mySql.addSubPara(fm.BnfKind.value );  
	mySql.addSubPara(fm.FeeOperationType.value );  
	mySql.addSubPara(fm.insuredno.value );  
//    prompt("��ѯ�����������˻���Ϣ",strSQL1);
    var arr1 = easyExecSql( mySql.getString() );
    if (arr1)
    {
        displayMultiline(arr1,LLBnfGrid);
//        turnPage.queryModal(strSQL, LLBnfGrid);
    }
    //---------------------------------------------------------------------------------------------end

    //���ÿɲ�����ť
    fm.addButton.disabled = false;
//    fm.updateButton.disabled = true;
    fm.deleteButton.disabled = false;
    fm.saveButton.disabled = false;
    
    //��������δ����״̬,����ط�ʽ��ѯ������
    if (LLBalanceGrid.getRowColData(i,11) == '0')
    {
        //---------------------------------------------------------------------------------------------BEG
        //�������స��������Ĭ��Ϊ�����˱���,�����స��Ĭ��Ϊ������   2005-8-20 14:49 ����
        //---------------------------------------------------------------------------------------------
        
        //�����ж��ǲ�����������
       /* var tSql = "select substr(d.reasoncode,2,2) from llappclaimreason d where "
                 + " d.caseno = '" + fm.ClmNo.value + "' and customerno='"+fm.insurednobak.value+"'";
        */mySql = new SqlClass();
	mySql.setResourceName("claim.LLBnfInputSql");
	mySql.setSqlId("LLBnfSql8");
	mySql.addSubPara( fm.ClmNo.value);   
	mySql.addSubPara( fm.insurednobak.value);  

        var tRcode = easyExecSql( mySql.getString() );
        if (tRcode)
        {
            for (var j = 0; j < tRcode.length; j++)
            {
                if (tRcode[j] == '02')
                {
                    tDeadFlag = 1;
                    break;
                }
            }
        }
        
        var tPerson = new Array;
        if (tDeadFlag == 0)  //�������స��
        {
            //var strSQL3 = "select customerno from llcase where caseno = '" + LLBalanceGrid.getRowColData(i,1) + "' and customerno='"+fm.insuredno.value+"'";
            mySql = new SqlClass();
	mySql.setResourceName("claim.LLBnfInputSql");
	mySql.setSqlId("LLBnfSql9");
	mySql.addSubPara( LLBalanceGrid.getRowColData(i,1) );   
	mySql.addSubPara( fm.insuredno.value);  
            var tCustNo = easyExecSql(  mySql.getString() );
            
           /* var strSQL4 = "select a.name,a.sex,a.birthday,a.idtype,a.idno,(select b.bankcode from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y'),(select b.bankaccno from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y'),(select b.accname from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y') from ldperson a where 1=1"
                        + " and a.customerno = '" + tInsuredNo + "'";*/
             mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql10");
		mySql.addSubPara(tInsuredNo);   
	        
            tPerson = easyExecSql(  mySql.getString() );
//            prompt("",strSQL3);
            fm.insuredno.value = tCustNo[0];
            fm.customerNo.value = tCustNo[0];
            fm.payeeNo.value = tCustNo[0];
            fm.relationtoinsured.value = '00';
            fm.relationtopayee.value = '00';
//            alert("insuredno="+fm.insuredno.value);
        }
        else   //�����స��
        {
            //-----------------------------------------1-------------------2--------3-------4--------5------6-------7-------8-----9-------10---------11-----------12---13--------14----15------------16------17-----18------19----20------------------------------21-----------------22-----23
            /*var strSQL2 = "select '"+LLBalanceGrid.getRowColData(i,1)+"',polNo,insuredno,bnfno,customerNo,name,customerNo,name,bnftype,bnfgrade,relationtoinsured,sex,birthday,idtype,idno,relationtoinsured,sex,birthday,idtype,idno,bnflot*" + parseFloat(fm.sumPay.value) + ",bnflot*100,'',(select b.bankcode from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y'),(select b.bankaccno from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y'),(select b.accname from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y') from LCBnf a where "
                        + " PolNo = '" + LLBalanceGrid.getRowColData(i,2) + "'"
                        + " order by BnfNo";*/
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql11");
		mySql.addSubPara(LLBalanceGrid.getRowColData(i,1)); 
		mySql.addSubPara(LLBalanceGrid.getRowColData(i,5)); 
		mySql.addSubPara(parseFloat(fm.sumPay.value));  
		mySql.addSubPara(LLBalanceGrid.getRowColData(i,2));  
//            prompt("����స����ѯ�б�ʱȷ����������",strSQL2);
            var arr2 = easyExecSql( mySql.getString() ); //�ȵ��б����в���
            if (arr2)
            {
                displayMultiline(arr2,LLBnfGrid);
            }
            else  //�б�û������,Ĭ��Ϊ������
            {
               /* var strSQL4 = "select a.RgtantName,a.RgtantSex,'',a.IDType,a.IDNo,'','','',Relation from LLRegister a where 1=1"
                            + " and a.RgtNo = '" + fm.ClmNo.value + "'";*/
                mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql12");
		mySql.addSubPara(fm.ClmNo.value );  
		
//                prompt("�б�û������,Ĭ��Ϊ������",strSQL4);
                tPerson = easyExecSql( mySql.getString() );
                fm.relationtoinsured.value = tPerson[0][5];
                fm.relationtopayee.value = tPerson[0][5];
            }
        }

        if (tPerson != "" && tPerson != null)
        {
            fm.polNo.value = LLBalanceGrid.getRowColData(i,2);//
            fm.cName.value = tPerson[0][0];
            fm.payeename.value = tPerson[0][0];
            fm.sex.value = tPerson[0][1];
            fm.birthday.value = tPerson[0][2];
            fm.idtype.value = tPerson[0][3];
            fm.idno.value = tPerson[0][4];
            fm.payeesex.value = tPerson[0][1];
            fm.payeebirthday.value = tPerson[0][2];
            fm.payeeidtype.value = tPerson[0][3];
            fm.payeeidno.value = tPerson[0][4];
            fm.bnfCurrency.value = LLBalanceGrid.getRowColData(i,5);
            fm.getmoney.value = LLBalanceGrid.getRowColData(i,6);
            fm.bnflot.value = '100';
            fm.BankCode.value = tPerson[0][5];
            fm.BankAccNo.value = tPerson[0][6];
            fm.AccName.value = tPerson[0][7];
        }
        showOneCodeName('sex','sex','SexName');
        showOneCodeName('llidtype','idtype','idtypeName');
        showOneCodeName('relation','relationtoinsured','relationtoinsuredName');
        showOneCodeName('relation','relationtopayee','relationtopayeeName');
        showOneCodeName('sex','payeesex','payeesexName');
        showOneCodeName('llidtype','payeeidtype','payeeidtypeName');
        showOneCodeName('llpaymode','CasePayMode','CasePayModeName');
        showOneCodeName('bank','BankCode','BankCodeName');
        //---------------------------------------------------------------------------------------------END
    }
    
    //��������ת�������б���Ϊ"default"���ݽ�������
    //���� 2006-1-12 11:18 Ӧ���쵼Ҫ��
    if (fm.BankCode.value == "default")
    {
        fm.BankCode.value = "";
    }
    if (fm.BankAccNo.value == "default")
    {
        fm.BankAccNo.value = "";
    }
    
    /**-----------------------------------------------------------------------------------BEG
     * ת������ӵ�����ж��Ƿ��������ʾ��ť�Ͳ�����
     * add ���� 2006-3-3 9:23
     *-----------------------------------------------------------------------------------*/
//    alert(fm.ClmFlag.value);
    //���������ѷ���״̬��Ϊת�����ȡ
    if (LLBalanceGrid.getRowColData(i,11) != '0' && fm.ClmFlag.value == "2")
    {
        document.all("divButtonBnf").style.display = "none";
        document.all("divButtonToPension").style.display = "none";
    }
//    alert("insuredno="+fm.insuredno.value);
    //------------------------------------------------------------------------------------END
}

//����������,���adjustBnfButton��Ӧ�¼�
function toAdjustBnf()
{
//    if (!confirm("����:���������˽�ɾ��ת�����ȡ�ķ��䡣�Ƿ������"))
//    {
//        return;
//    }
    document.all("divButtonBnf").style.display = "";
    document.all("divButtonToPension").style.display = "none";
}

//ѡ��LLBnfGrid��Ӧ�¼�
function LLBnfGridClick()
{
    //��ձ�
    emptyInput();
	//-----------------------------------------------------
	//��������������˻���ϸ��Ϣ
    //-----------------------------------------------------
    var i = LLBnfGrid.getSelNo();
   // alert("LLBnfGridClick begin:"+fm.insuredno.value);
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo.value = LLBnfGrid.getRowColData(i,1);//
      //  fm.polNo.value = LLBnfGrid.getRowColData(i,2);// wyc
//        fm.insuredno.value = LLBnfGrid.getRowColData(i,3);//
        fm.bnfno.value = LLBnfGrid.getRowColData(i,4);//
        fm.customerNo.value = LLBnfGrid.getRowColData(i,5);
        fm.cName.value = LLBnfGrid.getRowColData(i,6);//
        fm.payeeNo.value = LLBnfGrid.getRowColData(i,7);//
        fm.payeename.value = LLBnfGrid.getRowColData(i,8);//
        fm.bnftype.value = LLBnfGrid.getRowColData(i,9);//
        fm.bnfgrade.value = LLBnfGrid.getRowColData(i,10);//
        fm.relationtoinsured.value = LLBnfGrid.getRowColData(i,11);//
        fm.sex.value = LLBnfGrid.getRowColData(i,12);//
        fm.birthday.value = LLBnfGrid.getRowColData(i,13);//
        fm.idtype.value = LLBnfGrid.getRowColData(i,14);//
        fm.idno.value = LLBnfGrid.getRowColData(i,15);//
        fm.relationtopayee.value = LLBnfGrid.getRowColData(i,16);//
        fm.payeesex.value = LLBnfGrid.getRowColData(i,17);//
        fm.payeebirthday.value = LLBnfGrid.getRowColData(i,18);//
        fm.payeeidtype.value = LLBnfGrid.getRowColData(i,19);//
        fm.payeeidno.value = LLBnfGrid.getRowColData(i,20);//
        fm.bnfCurrency.value = LLBnfGrid.getRowColData(i,21);  //wyc
        fm.getmoney.value = LLBnfGrid.getRowColData(i,22);//
        fm.bnflot.value = LLBnfGrid.getRowColData(i,23);//
        fm.CasePayMode.value = LLBnfGrid.getRowColData(i,24);//
        fm.BankCode.value = LLBnfGrid.getRowColData(i,25);//
        fm.BankAccNo.value = LLBnfGrid.getRowColData(i,26);//
        fm.AccName.value = LLBnfGrid.getRowColData(i,27);//
        fm.oriBnfNo.value = LLBnfGrid.getRowColData(i,30);//
        
        showOneCodeName('sex','sex','SexName');
        showOneCodeName('llidtype','idtype','idtypeName');
        showOneCodeName('relation','relationtoinsured','relationtoinsuredName');
        showOneCodeName('relation','relationtopayee','relationtopayeeName');
        showOneCodeName('sex','payeesex','payeesexName');
        showOneCodeName('llidtype','payeeidtype','payeeidtypeName');
        showOneCodeName('llpaymode','CasePayMode','CasePayModeName');
        showOneCodeName('bank','BankCode','BankCodeName');
        
        /**-----------------------------------------------------------------------------------BEG
         * ���Ϊת������ѯ�����������Ƿ��Ѿ�ָ��ת�������
         * add ���� 2006-3-3 9:23
         *-----------------------------------------------------------------------------------*/
        //���������ѷ���״̬��Ϊת�����ȡ
        if (fm.ClmFlag.value == "2")
        {
//            var tQusql = "select b.getdutycode from llget b where 1=1 "
//                       + getWherePart( 'clmno','ClmNo' )
//                       + getWherePart( 'bnfno','bnfno' )
//                       + getWherePart( 'polno','polNo' );
            
            var  ClmNo1 = window.document.getElementsByName(trim("ClmNo"))[0].value;
            var  bnfno1 = window.document.getElementsByName(trim("bnfno"))[0].value;
            var  polNo1 = window.document.getElementsByName(trim("polNo"))[0].value;
          	var sqlid1="LLBnfInputSql22";
          	var mySql1=new SqlClass();
          	mySql1.setResourceName("claim.LLBnfInputSql"); //ָ��ʹ�õ�properties�ļ���
          	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
            var tFlag = easyExecSql( mySql1 );
            if (tFlag)
            {
                fm.PensionType.value = tFlag;
                showOneCodeName('llclmtopension','PensionType','PensionTypeName');
            }
          
            else
            {
                //��ʼ��ʱ��ת���ť��Ϊ�����ã�ֱ��ȷ����û����ת�����ʱ�ŷſ�
                fm.clmToPension.disabled = false;
            }
        }
        //------------------------------------------------------------------------------------END

    }
    //alert("LLBnfGridClick begin:"+fm.insuredno.value);
    //���ÿɲ�����ť
    fm.updateButton.disabled = false;
    
}

//����������
function addClick()
{
    //1������ѡ�б���
    if (LLBalanceGrid.getSelNo() < 1)
    {
        alert("��ѡ�񱣵���");
        return;
    }
    //alert("1111111111:"+fm.insuredno.value);
    //2���ǿռ���
    if(!checkInput())
    {
        return;
    }
    
    //3������mulLinemulLine�кϼ�������ϼ�������� 
    var tAllMoney = 0;	//mulLine�кϼ�������
    var tAllLot = 0;	//mulLine�кϼ��������
    for (var j=0; j < LLBnfGrid.mulLineCount; j++)
    {
        tAllMoney = tAllMoney + parseFloat(LLBnfGrid.getRowColData(j,22));
        tAllLot = tAllLot + parseFloat(LLBnfGrid.getRowColData(j,23));
    }    
    
    //4��У���������֮��<=100������ʣ��ٷֱ� 
    if (fm.bnflot.value == "" || fm.bnflot.value == null)
    {
        fm.bnflot.value = 0;
    }    
    var tBnfLot = parseFloat(fm.bnflot.value);  
    if (tAllLot + tBnfLot > 100)	//У���������֮��<=100
    {
        alert("�������֮�ʹ���100�������!");
        return;
    }
	fm.RemainLot.value = Math.round((100 - tAllLot - tBnfLot)*100)/100;
    
    //5������������
    if(tAllLot + tBnfLot == 100)	//���Ϊ��������һ�ˣ�������ʣ���������
	{
		fm.getmoney.value = eval(parseFloat(fm.sumPay.value)-tAllMoney).toFixed(2)
	}
	else if(tAllLot + tBnfLot < 100)
	{
		fm.getmoney.value = eval((parseFloat(fm.sumPay.value)*tBnfLot*0.01).toFixed(2));
	}
    
	//6��У������ӵ������������Ҫ��ӵ���ͬһ���ˣ����ش�����ʾ
 	var rowNum = LLBnfGrid.mulLineCount;
	for(var i=0;i<rowNum;i++)
	{
		if((LLBnfGrid.getRowColData(i,3)==fm.insuredno.value)//���ݱ����������
			&&(LLBnfGrid.getRowColData(i,6)==fm.cName.value)
			&&(LLBnfGrid.getRowColData(i,12)==fm.sex.value)
			&&(LLBnfGrid.getRowColData(i,13)==fm.birthday.value)
			&&(LLBnfGrid.getRowColData(i,14)==fm.idtype.value)
			&&(LLBnfGrid.getRowColData(i,15)==fm.idno.value)
			&&(LLBnfGrid.getRowColData(i,11)==fm.relationtoinsured.value))
		{
			alert("���������Ѿ���ӣ������ظ����!");
			return false;
		}
	}
   
    //7��У���ѱ���������������Ҫ��ӵ���ͬһ���ˣ�֧����ʽ����ϢҪһ�£����������������˵����
	/*var strSQL = " select GetMoney,BnfNo,CasePayMode,BankCode,BankAccNo,AccName,RelationToInsured from LLBnf "
		+ " where clmno ='"+document.all('ClmNo').value+"'"
		+ " and Insuredno='"+document.all('insuredno').value+"'"
		+ " and BnfKind='"+fm.BnfKind.value+"'"
		+ " and Name='"+document.all('cName').value+"'"
		+ " and sex='"+document.all('sex').value+"'"
		+ " and Birthday='"+document.all('birthday').value+"'"
		+ " and idtype='"+document.all('idtype').value+"'"
		+ " and idno='"+document.all('idno').value+"'";	*///prompt("У���Ƿ���ͬһ����������Ϣ",strSQL);	    
	    mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql13");
		mySql.addSubPara(document.all('ClmNo').value );  
		mySql.addSubPara(document.all('insuredno').value );  
		mySql.addSubPara(fm.BnfKind.value );  
		mySql.addSubPara(document.all('cName').value );  
		mySql.addSubPara(document.all('sex').value );  
		mySql.addSubPara(document.all('birthday').value );  
		mySql.addSubPara(document.all('idtype').value );  
		mySql.addSubPara(document.all('idno').value );  
	var strQueryResult = easyExecSql(mySql.getString());
  
	if(strQueryResult==null || strQueryResult=="")//�µ�������
	{
		//var SQL_maxBnfNo = " select nvl(max(BnfNo),0) from LLBnf where clmno ='"+document.all('ClmNo').value+"'";
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql14");
		mySql.addSubPara(document.all('ClmNo').value );  

		var maxBnfNo = parseInt(easyExecSql(mySql.getString()));//�ѱ����������������
		for(var i=0;i<rowNum;i++)
		{
			if(parseInt(LLBnfGrid.getRowColData(i,4)) > maxBnfNo){//�������ӵ���Ÿ�����ȡ֮
				maxBnfNo = LLBnfGrid.getRowColData(i,4);
			}
		}
		fm.bnfno.value = parseInt(maxBnfNo) + 1;
		//У���ܽ���Ϊ��
		if(parseFloat(fm.getmoney.value) < 0){
			alert("�������������ܽ���Ϊ��!");
	    	return;
		}
	}
	else	//���ݿ��б������������	
	{
		fm.bnfno.value = strQueryResult[0][1];//ȡ�ѱ�������
	    if(fm.relationtoinsured.value != strQueryResult[0][6])//У�鱻�����˹�ϵ���뱣��һ��
    	{
    		alert("������:"+fm.cName.value+"���뱻�����˹�ϵ�뱣��һ��!");
    		return false;
    	}		
		if(fm.CasePayMode.value != strQueryResult[0][2])//У�������ʽ����һ��
		{
			alert("������:"+fm.cName.value+"��֧����ʽ�뱣��һ��!");
			return false;
		}
		if(fm.CasePayMode.value == '4')//��ѡ������ת�������б���,����,�����˺�Ҳ����һ��
		{
      		if(fm.BankCode.value == "" || fm.BankCode.value == null )//���б���
      	    {	
      	    	alert("֧����ʽΪ����ת��ʱ���б��벻��Ϊ��!");
      	    	return false;
      	    }
        	if(fm.BankCode.value != strQueryResult[0][3])
        	{
        		alert("ͬһ������֧����ʽΪ����ת��ʱ���б����豣��һ��!");
        		return false;
        	}
      	    if(fm.BankAccNo.value == "" || fm.BankAccNo.value == null )//�����ʺ�
      	    {	
      	    	alert("֧����ʽΪ����ת��ʱ�����ʺŲ���Ϊ��!");
      	    	return false;
      	    }
        	if(fm.BankAccNo.value != strQueryResult[0][4])
        	{
        		alert("ͬһ������֧����ʽΪ����ת��ʱ�����˺��뱣��һ��!");
        		return false;
        	}
      	    if(fm.AccName.value == "" || fm.AccName.value == null )//�����ʻ���
      	    {	
      	    	alert("֧����ʽΪ����ת��ʱ�����ʻ�������Ϊ��!");
      	    	return false;
      	    }
          	if(fm.AccName.value != strQueryResult[0][5])
        	{
        		alert("ͬһ������֧����ʽΪ����ת��ʱ�����ʻ����뱣��һ��!");
        		return false;
        	}
		}
		//У���ܽ���Ϊ��
		var sumMoney = parseFloat(fm.getmoney.value);
		for(var j=0; j<strQueryResult.length; j++){
			sumMoney += parseFloat(strQueryResult[j][0]);
		}
		if(sumMoney<0){
			alert("�������������ܽ���Ϊ��!");
	    	return;
		}
	}

	LLBnfGrid.addOne();
	LLBnfGrid.setRowColData(rowNum,1,fm.ClmNo.value);//
	LLBnfGrid.setRowColData(rowNum,2,fm.polNo.value);//
	//alert("222222222222:"+fm.insuredno.value);
	LLBnfGrid.setRowColData(rowNum,3,fm.insuredno.value);//
	LLBnfGrid.setRowColData(rowNum,4,fm.bnfno.value);//���������
	LLBnfGrid.setRowColData(rowNum,5,fm.customerNo.value);
	LLBnfGrid.setRowColData(rowNum,6,fm.cName.value);//
	LLBnfGrid.setRowColData(rowNum,7,fm.payeeNo.value);//
	LLBnfGrid.setRowColData(rowNum,8,fm.payeename.value);//
	LLBnfGrid.setRowColData(rowNum,9,fm.bnftype.value);//
	LLBnfGrid.setRowColData(rowNum,10,fm.bnfgrade.value);//
	LLBnfGrid.setRowColData(rowNum,11,fm.relationtoinsured.value);//
	LLBnfGrid.setRowColData(rowNum,12,fm.sex.value);//
	LLBnfGrid.setRowColData(rowNum,13,fm.birthday.value);//
	LLBnfGrid.setRowColData(rowNum,14,fm.idtype.value);//
	LLBnfGrid.setRowColData(rowNum,15,fm.idno.value);//
	LLBnfGrid.setRowColData(rowNum,16,fm.relationtopayee.value);//
	LLBnfGrid.setRowColData(rowNum,17,fm.payeesex.value);//
	LLBnfGrid.setRowColData(rowNum,18,fm.payeebirthday.value);//
	LLBnfGrid.setRowColData(rowNum,19,fm.payeeidtype.value);//
	LLBnfGrid.setRowColData(rowNum,20,fm.payeeidno.value);//
	LLBnfGrid.setRowColData(rowNum,21,fm.bnfCurrency.value);//
	LLBnfGrid.setRowColData(rowNum,22,fm.getmoney.value);//�����˷�����
	LLBnfGrid.setRowColData(rowNum,23,fm.bnflot.value);//�����˷������	
	LLBnfGrid.setRowColData(rowNum,24,fm.CasePayMode.value);//
	LLBnfGrid.setRowColData(rowNum,25,fm.BankCode.value);//
	LLBnfGrid.setRowColData(rowNum,26,fm.BankAccNo.value);//
	LLBnfGrid.setRowColData(rowNum,27,fm.AccName.value);//
	LLBnfGrid.setRowColData(rowNum,28,fm.FeeOperationType.value);//
	LLBnfGrid.setRowColData(rowNum,29,fm.CasePayModeName.value);//
	


}

//����������
function addClick_1()
{
    //����ѡ�б���
    if (LLBalanceGrid.getSelNo() < 1)
    {
        alert("��ѡ�񱣵�");
        return;
    }
    //�ǿռ���
    if(!checkInput())
    {
        return;
    }
    
    //����ٷֱȼ�������
    var i = LLBnfGrid.mulLineCount;
    var tAll = 0;
    var temp = 0;
    
    for (var j=0; j < i; j++)
    {
        tAll = tAll + parseFloat(LLBnfGrid.getRowColData(j,23));
        //alert("LLBnfGrid.getRowColData("+j+",30):"+LLBnfGrid.getRowColData(j,30));
        temp = temp + parseFloat(LLBnfGrid.getRowColData(j,31));
        //alert("temp:"+temp);
    }
    
    if (fm.bnflot.value == "" || fm.bnflot.value == null)
    {
        fm.bnflot.value = 0;
    }
    
    var tBnfLot = parseFloat(fm.bnflot.value);
    tAll = tAll + tBnfLot;
    
    if (tAll > 100 
    		//|| tAll < 0
    		)
    {
        alert("�����������!");
        return;
    }
    else
    {

    	
        //����ʣ��ٷֱ�
        fm.RemainLot.value = 100 - tAll;
        //fm.RemainLot.value=eval(parseFloat(fm.RemainLot.value)).toFixed(2)
        
        fm.RemainLot.value=Math.round(fm.RemainLot.value*100)/100;   
        //alert("fm.RemainLot.value:"+fm.RemainLot.value);
        
        //--------------------------------------------------------------------------------BEG
        //����������,���Ϊ��������һ�ˣ�������ʣ���������
        //--------------------------------------------------------------------------------
        if(fm.RemainLot.value == '0')
        {
            var tAllMoney = 0;
            var tAllLot=0;
            for (var l=0; l < i; l++)
            {
                tAllMoney = tAllMoney + parseFloat(LLBnfGrid.getRowColData(l,22));
                tAllLot = tAllLot+parseFloat(LLBnfGrid.getRowColData(l,23));
            }
            
            tAllLot=Math.round(tAllLot*100)/100; 
        	//alert("tAllMoney:"+tAllMoney);
        	//alert("tAllLot:"+tAllLot);
        	//alert("fm.sumPay.value:"+fm.sumPay.value);
        	//alert("temp:"+temp);

            //fm.getmoney.value = eval(parseFloat(fm.sumPay.value)-parseFloat(tAllLot)*0.01*parseFloat(fm.sumPay.value).toFixed(4));
            
            //fm.getmoney.value = parseFloat(fm.sumPay.value)-parseFloat(tAllLot)*0.01*parseFloat(fm.sumPay.value).toFixed(4);
            
            fm.getmoney.value = eval(parseFloat(fm.sumPay.value)-temp).toFixed(2)
            
        	//alert("fm.getmoney.value1:"+fm.getmoney.value);
     
        }
        else
        {           
            fm.getmoney.value = eval((tBnfLot*0.01*parseFloat(fm.sumPay.value)).toFixed(4));
        }
        //--------------------------------------------------------------------------------END
        //ȡ�����
        if (i == 0)
        {
            fm.bnfno.value = 1;
        }
        else
        {
            fm.bnfno.value = parseInt(LLBnfGrid.getRowColData(i-1,4)) + 1;
        }
        
        /**
         * 2009-02-25 zhangzheng 
         * ��һ�����������ݽ������������Ϊ����������ҲҪ���з��䡣
		   �����������˷�����Ϣ���б���ʱ����ÿ�������˵ı��η�����Ŀ�����кϲ�(ÿ�������˵�������Ŀ�Ľ��ϲ��ں�ִ̨��)�����κ�һ�������˵���������Ϊ����ʱ�������˷��䲻�ܽ��б��棬������ϵͳ��ʾ"�����˵����������Ϊ���������º˶������˷�����Ϣ��"
		   ������������Ϣ�ж�����������Ϣ�����ݿ��е���Ϣ�ж�,ֻ�и�����Ϣ:�������뱻�����˹�ϵ,����,�Ա�,��������,֤������,֤��������ȫһ�º����ݿⱣ�����Ϣһ�²���Ϊ��һ����,���н��ϲ�,���ϲ���Ľ����Ǹ�ֵ;
         */      
        
	    /*var strSQL = " select GetMoney,BnfNo,CasePayMode,BankCode,BankAccNo,AccName,RelationToInsured from LLBnfGather"
 	         		 + " where clmno ='"+document.all('ClmNo').value+"'"
 	         		 //+ " and grpcontno='"+document.all('GrpContNo').value+"'"
 	         		 //+ " and grppolno='"+document.all('GrpPolNo').value+"'"
 	         		 //+ " and contno='"+document.all('ContNo').value+"'"
 	         		 //+ " and polno='"+document.all('polNo').value+"'"
 	         		 + " and Insuredno='"+document.all('insuredno').value+"'"
 	         		 + " and BnfKind='"+fm.BnfKind.value+"'"
 	         		 //+ " and FeeOperationType='"+document.all('FeeOperationType').value+"'"
 	         		 //+ " and RelationToInsured='"+document.all('relationtoinsured').value+"'"
 	         		 + " and Name='"+document.all('cName').value+"'"
 	         		 + " and sex='"+document.all('sex').value+"'"
 	         		 + " and Birthday='"+document.all('birthday').value+"'"
 	         		 + " and idtype='"+document.all('idtype').value+"'"
 	         		 + " and idno='"+document.all('idno').value+"'"*/
   	   mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql15");
		mySql.addSubPara(document.all('ClmNo').value );  
		mySql.addSubPara(document.all('insuredno').value );  
		mySql.addSubPara(fm.BnfKind.value );  
		mySql.addSubPara(document.all('cName').value );  
		mySql.addSubPara(document.all('sex').value );  
		mySql.addSubPara(document.all('birthday').value );  
		mySql.addSubPara(document.all('idtype').value );  
		mySql.addSubPara(document.all('idno').value );  
	    //prompt("У���Ƿ���ͬһ����������Ϣ",strSQL);
	    
	    var strQueryResult = easyExecSql(mySql.getString());
	    
	    var tFlag=false;//�ϲ���־
	    var mergeMoney=0.0;//�ϲ����
        var rowNum = LLBnfGrid.mulLineCount;
	    
	    if(strQueryResult==null || strQueryResult=="")
	    {
	    	
	        //���ݿ����û��,���ж��ѱ����multiline���Ƿ��ѱ����˼�¼
	    	//-----------------------------------------------------
	    	//��������������˻���ϸ��Ϣ
	        //-----------------------------------------------------

	        if (rowNum != '0')
	        {
	            for(var i=0;i<rowNum;i++)
	            {
  	
	            	//���ݱ����������
	            	if((LLBnfGrid.getRowColData(i,3)==fm.insuredno.value)
	            			&&(LLBnfGrid.getRowColData(i,6)==fm.cName.value)
	            			  &&(LLBnfGrid.getRowColData(i,12)==fm.sex.value)
	            			    &&(LLBnfGrid.getRowColData(i,13)==fm.birthday.value)
	            			      &&(LLBnfGrid.getRowColData(i,14)==fm.idtype.value)
	            			       &&(LLBnfGrid.getRowColData(i,15)==fm.idno.value)
	            			        &&(LLBnfGrid.getRowColData(i,11)==fm.relationtoinsured.value))
	            	{
	            		//���뵽�����ʾ��ͬһ��������,У��֧����ʽ
	            		 if (fm.CasePayMode.value == "" || fm.CasePayMode.value == null)
	            		 {
	            		        alert("֧����ʽ����Ϊ��!");
	            		        return false;
	            		 }
	            		 
	              		 //alert("fm.CasePayMode.value:"+fm.CasePayMode.value);
	              		 //alert("LLBnfGrid.getRowColData(i,23):"+LLBnfGrid.getRowColData(i,23));
	            		     
	            		 if(fm.CasePayMode.value != LLBnfGrid.getRowColData(i,24))
	            		 {
	            		    	alert("ͬһ������֧����ʽ����һ��!");
	            		    	return false;
	            		 }
	            		 
	              		 if(fm.CasePayMode.value == '4')
	            		 {
	              			if(fm.BankCode.value == "" || fm.BankCode.value == null )
	              	    	{	
	              	    		alert("֧����ʽΪ����ת��ʱ���б��벻��Ϊ��!");
	              	    		return false;
	              	    	}
	              			
	              			//alert("fm.BankCode.value:"+fm.BankCode.value);
	              			//alert("LLBnfGrid.getRowColData(i,24):"+LLBnfGrid.getRowColData(i,24));

	              			
	                		if(fm.BankCode.value != LLBnfGrid.getRowColData(i,25))
		            		{
		            		    	alert("ͬһ������֧����ʽΪ����ת��ʱ���б����豣��һ��!");
		            		    	return false;
		            		}

	              			
	              	    	if(fm.BankAccNo.value == "" || fm.BankAccNo.value == null )
	              	    	{	
	              	    		alert("֧����ʽΪ����ת��ʱ�����ʺŲ���Ϊ��!");
	              	    		return false;
	              	    	}
	              	    	

	             			//alert("fm.BankAccNo.value:"+fm.BankAccNo.value);
	              			//alert("LLBnfGrid.getRowColData(i,25):"+LLBnfGrid.getRowColData(i,25));
	              	    	
	                	    if(fm.BankAccNo.value != LLBnfGrid.getRowColData(i,26))
		            		{
		            		    	alert("ͬһ������֧����ʽΪ����ת��ʱ�����˺��豣��һ��!");
		            		    	return false;
		            		}

	              			
	              	    	if(fm.AccName.value == "" || fm.AccName.value == null )
	              	    	{	
	              	    		alert("֧����ʽΪ����ת��ʱ�����ʻ�������Ϊ��!");
	              	    		return false;
	              	    	}
	              	    	
	             			//alert("fm.AccName.value:"+fm.AccName.value);
	              			//alert("LLBnfGrid.getRowColData(i,26):"+LLBnfGrid.getRowColData(i,26));
	              	    	
	                  	    if(fm.AccName.value != LLBnfGrid.getRowColData(i,27))
		            		{
		            		    	alert("ͬһ������֧����ʽΪ����ת��ʱ���л����豣��һ��!");
		            		    	return false;
		            		}
	                  	    
	                  	    if(fm.payeeidtype.value != "0"||fm.idtype.value !="0"){
	                  	    	alert("��ȡ��ʽΪ����ת�ˣ�����������ȡ�˵�֤�����ͱ���Ϊ���֤!");
	            		    	return false;
	                  	    }
	            		 }
		
	              		 //�ϲ�������
	              		 //���·���������˽��ͱ���
	     		    	 document.all('oriBnfPay').value=parseFloat(document.all('getmoney').value);    
	              		 mergeMoney=parseFloat(LLBnfGrid.getRowColData(i,22))+parseFloat(document.all('getmoney').value);
	        	    	 
	        	    	 if(mergeMoney<0)
	        	    	 {
	        	    	      alert("�������������ܽ���Ϊ��!");
	        	    	      return;
	        	    	 }
	        	    	 

	        	    	 document.all('getmoney').value=(parseFloat(document.all('getmoney').value) + parseFloat(LLBnfGrid.getRowColData(i,22))).toFixed(2);
	        	    	 document.all('bnflot').value=eval((document.all('getmoney').value / fm.sumPay.value).toFixed(2)*100);


	        		     LLBnfGrid.setRowColData(i,22,fm.getmoney.value);//�����˷�����
	        		     LLBnfGrid.setRowColData(i,23,fm.bnflot.value);//�����˷������
	        		     
	        		     
	        		     tFlag=true;
	            	}
	            }

	            //alert("tFlag:"+tFlag);
	            
	            //������ӵ���һ����������
	            if(tFlag==false)
	            {
	 
	            	
	    	    	LLBnfGrid.addOne();
	    	        LLBnfGrid.setRowColData(i,1,fm.ClmNo.value);//
	    	        LLBnfGrid.setRowColData(i,2,fm.polNo.value);//
	    	        LLBnfGrid.setRowColData(i,3,fm.insuredno.value);//
  	        
	    	        mBnfBatNo++;     
	    	        fm.bnfno.value=mBnfBatNo;               	
	               	document.all('getmoney').value=(parseFloat(document.all('getmoney').value)).toFixed(2);    	 
	               	LLBnfGrid.setRowColData(i,4,fm.bnfno.value);//
	               	
	               	//alert("LLBnfGrid.getRowColData("+i+",4):"+LLBnfGrid.getRowColData(i,4));
	               	
	

	    	        LLBnfGrid.setRowColData(i,5,fm.customerNo.value);
	    	        LLBnfGrid.setRowColData(i,6,fm.cName.value);//
	    	        LLBnfGrid.setRowColData(i,7,fm.payeeNo.value);//
	    	        LLBnfGrid.setRowColData(i,8,fm.payeename.value);//
	    	        LLBnfGrid.setRowColData(i,9,fm.bnftype.value);//
	    	        LLBnfGrid.setRowColData(i,10,fm.bnfgrade.value);//
	    	        LLBnfGrid.setRowColData(i,11,fm.relationtoinsured.value);//
	    	        LLBnfGrid.setRowColData(i,12,fm.sex.value);//
	    	        LLBnfGrid.setRowColData(i,13,fm.birthday.value);//
	    	        LLBnfGrid.setRowColData(i,14,fm.idtype.value);//
	    	        LLBnfGrid.setRowColData(i,15,fm.idno.value);//
	    	        LLBnfGrid.setRowColData(i,16,fm.relationtopayee.value);//
	    	        LLBnfGrid.setRowColData(i,17,fm.payeesex.value);//
	    	        LLBnfGrid.setRowColData(i,18,fm.payeebirthday.value);//
	    	        LLBnfGrid.setRowColData(i,19,fm.payeeidtype.value);//
	    	        LLBnfGrid.setRowColData(i,20,fm.payeeidno.value);//
	    	        LLBnfGrid.setRowColData(i,21,fm.bnfCurrency.value);//
	    	        LLBnfGrid.setRowColData(i,22,fm.getmoney.value);//
	    	        LLBnfGrid.setRowColData(i,23,fm.bnflot.value);//
	    	        LLBnfGrid.setRowColData(i,24,fm.CasePayMode.value);//
	    	        LLBnfGrid.setRowColData(i,25,fm.BankCode.value);//
	    	        LLBnfGrid.setRowColData(i,26,fm.BankAccNo.value);//
	    	        LLBnfGrid.setRowColData(i,27,fm.AccName.value);//
	    	        LLBnfGrid.setRowColData(i,28,fm.FeeOperationType.value);//
	    	        LLBnfGrid.setRowColData(i,29,fm.CasePayModeName.value);//
	    	        
	                fm.oriBnfNo.value = LLBnfGrid.getRowColData(i,4);//�������������,Ϊ���޸���������ϢʱУ����
	    	        LLBnfGrid.setRowColData(i,30,fm.oriBnfNo.value);//
		            
		 	        LLBnfGrid.setRowColData(i,31,LLBnfGrid.getRowColData(i,22));
		            //alert("12--LLBnfGrid.getRowColData("+i+",30):"+LLBnfGrid.getRowColData(i,30));
	            }
	        }
	        else
	        {
		    	//document.all('oriBnfPay').value=parseFloat(document.all('getmoney').value);    
		    	
	        	mergeMoney=parseFloat(parseFloat(document.all('getmoney').value));
   	    	 
   	    	 	if(mergeMoney<0) {
   	    	      alert("�������������ܽ���Ϊ��!");
   	    	      return;
   	    	 	}
	        	//��һ����¼,ֱ�����������˼�¼
    	    	LLBnfGrid.addOne();
    	        LLBnfGrid.setRowColData(i,1,fm.ClmNo.value);//
    	        LLBnfGrid.setRowColData(i,2,fm.polNo.value);//
    	        LLBnfGrid.setRowColData(i,3,fm.insuredno.value);//
    	        
    	        //alert("mBnfBatNo1:"+mBnfBatNo);
    	        mBnfBatNo++;     
    	        //alert("mBnfBatNo2:"+mBnfBatNo);
    	        
    	        fm.bnfno.value=mBnfBatNo;               	
               	document.all('getmoney').value=(parseFloat(document.all('getmoney').value)).toFixed(2);    	 
               	LLBnfGrid.setRowColData(i,4,fm.bnfno.value);//
               	
               	//alert("LLBnfGrid.getRowColData("+i+",4):"+LLBnfGrid.getRowColData(i,4));
               	
    	        LLBnfGrid.setRowColData(i,5,fm.customerNo.value);
    	        LLBnfGrid.setRowColData(i,6,fm.cName.value);//
    	        LLBnfGrid.setRowColData(i,7,fm.payeeNo.value);//
    	        LLBnfGrid.setRowColData(i,8,fm.payeename.value);//
    	        LLBnfGrid.setRowColData(i,9,fm.bnftype.value);//
    	        LLBnfGrid.setRowColData(i,10,fm.bnfgrade.value);//
    	        LLBnfGrid.setRowColData(i,11,fm.relationtoinsured.value);//
    	        LLBnfGrid.setRowColData(i,12,fm.sex.value);//
    	        LLBnfGrid.setRowColData(i,13,fm.birthday.value);//
    	        LLBnfGrid.setRowColData(i,14,fm.idtype.value);//
    	        LLBnfGrid.setRowColData(i,15,fm.idno.value);//
    	        LLBnfGrid.setRowColData(i,16,fm.relationtopayee.value);//
    	        LLBnfGrid.setRowColData(i,17,fm.payeesex.value);//
    	        LLBnfGrid.setRowColData(i,18,fm.payeebirthday.value);//
    	        LLBnfGrid.setRowColData(i,19,fm.payeeidtype.value);//
    	        LLBnfGrid.setRowColData(i,20,fm.payeeidno.value);//
    	        LLBnfGrid.setRowColData(i,21,fm.bnfCurrency.value);//
    	        LLBnfGrid.setRowColData(i,22,fm.getmoney.value);//
    	        LLBnfGrid.setRowColData(i,23,fm.bnflot.value);//
    	        LLBnfGrid.setRowColData(i,24,fm.CasePayMode.value);//
    	        LLBnfGrid.setRowColData(i,25,fm.BankCode.value);//
    	        LLBnfGrid.setRowColData(i,26,fm.BankAccNo.value);//
    	        LLBnfGrid.setRowColData(i,27,fm.AccName.value);//
    	        LLBnfGrid.setRowColData(i,28,fm.FeeOperationType.value);//
    	        LLBnfGrid.setRowColData(i,29,fm.CasePayModeName.value);//
    	        
                fm.oriBnfNo.value = LLBnfGrid.getRowColData(i,4);//�������������,Ϊ���޸���������ϢʱУ����
    	        LLBnfGrid.setRowColData(i,30,fm.oriBnfNo.value);//
	 
	 	        LLBnfGrid.setRowColData(i,31,LLBnfGrid.getRowColData(i,22));
	            //alert("22--LLBnfGrid.getRowColData("+i+",30):"+LLBnfGrid.getRowColData(i,30));
	        }

	    }
	    else
	    {
	    	
	    	 //����У�������ʽ����һ��,��ѡ������ת�������б���,����,�����˺�Ҳ����һ��
	    	 if(fm.CasePayMode.value != strQueryResult[0][2])
    		 {
    		    	alert("������:"+fm.cName.value+"��֧����ʽ�뱣��һ��!");
    		    	return false;
    		 }
    		 
      		 if(fm.CasePayMode.value == '4')
    		 {
      			if(fm.BankCode.value == "" || fm.BankCode.value == null )
      	    	{	
      	    		alert("֧����ʽΪ����ת��ʱ���б��벻��Ϊ��!");
      	    		return false;
      	    	}
      			
      			//alert("fm.BankCode.value:"+fm.BankCode.value);
      			//alert("LLBnfGrid.getRowColData(i,24):"+LLBnfGrid.getRowColData(i,24));

      			
        		if(fm.BankCode.value != strQueryResult[0][3])
        		{
        		    	alert("ͬһ������֧����ʽΪ����ת��ʱ���б����豣��һ��!");
        		    	return false;
        		}

      			
      	    	if(fm.BankAccNo.value == "" || fm.BankAccNo.value == null )
      	    	{	
      	    		alert("֧����ʽΪ����ת��ʱ�����ʺŲ���Ϊ��!");
      	    		return false;
      	    	}
      	    	

     			//alert("fm.BankAccNo.value:"+fm.BankAccNo.value);
      			//alert("LLBnfGrid.getRowColData(i,25):"+LLBnfGrid.getRowColData(i,25));
      	    	
        	    if(fm.BankAccNo.value != strQueryResult[0][4])
        		{
        		    	alert("������:"+fm.cName.value+"��֧����ʽΪ�����˺��뱣��һ��!");
        		    	return false;
        		}

      			
      	    	if(fm.AccName.value == "" || fm.AccName.value == null )
      	    	{	
      	    		alert("֧����ʽΪ����ת��ʱ�����ʻ�������Ϊ��!");
      	    		return false;
      	    	}
      	    	
     			//alert("fm.AccName.value:"+fm.AccName.value);
      			//alert("LLBnfGrid.getRowColData(i,26):"+LLBnfGrid.getRowColData(i,26));
      	    	
          	    if(fm.AccName.value != strQueryResult[0][5])
        		{
        		    	alert("������:"+fm.cName.value+"��֧����ʽΪ���л����뱣��һ��!");
        		    	return false;
        		}
    		 }
      		 
	    	 //У�鱻�����˹�ϵ���뱣��һ��
	    	 if(fm.relationtoinsured.value != strQueryResult[0][6])
    		 {
    		    	alert("������:"+fm.cName.value+"���뱻�����˹�ϵ�뱣��һ��!");
    		    	return false;
    		 }
	    	 
	    	 document.all('oriBnfPay').value=parseFloat(document.all('getmoney').value);    	 
	    	 
	    	 //alert("document.all('oriBnfPay').value:"+document.all('oriBnfPay').value);
	    	 
	    	 //alert("parseFloat(strQueryResult[0][0]):"+parseFloat(strQueryResult[0][0]));
	    	 //alert("parseFloat(document.all('getmoney').value:"+parseFloat(document.all('getmoney').value));

	         //���·���������˽��ͱ���
	    	 mergeMoney=parseFloat(strQueryResult[0][0])+parseFloat(document.all('getmoney').value);
	    	 
	    	 //alert("mergeMoney:"+mergeMoney);
	    	 
	    	 if(mergeMoney<0)
	    	 {
	    	      alert("�������������ܽ���Ϊ��!");
	    	      return;
	    	 }
	    	 
	    	 document.all('getmoney').value=mergeMoney.toFixed(2);
	    	 
	    	 /**
	    	  * 2009-05-10 zhangzheng
	    	  * ��ʹ���ݿ���ڼ�¼,�ں����ݿ��д��ڼ�¼�ϲ�֮��,��ֹ�û���ε��������Ӽ�¼,Ҳ��Ҫ�ж��Ƿ���Ҫ���Ѿ���ӵ�multiline���кϲ�
	    	  */
	    	 var rowNum2 = LLBnfGrid.mulLineCount;
	    	 
		     if (rowNum != '0')
		     {
		         for(var i=0;i<rowNum;i++)
		         {
	  	
		            	//���ݱ����������
		            	if((LLBnfGrid.getRowColData(i,3)==fm.insuredno.value)
		            			&&(LLBnfGrid.getRowColData(i,6)==fm.cName.value)
		            			  &&(LLBnfGrid.getRowColData(i,12)==fm.sex.value)
		            			    &&(LLBnfGrid.getRowColData(i,13)==fm.birthday.value)
		            			      &&(LLBnfGrid.getRowColData(i,14)==fm.idtype.value)
		            			       &&(LLBnfGrid.getRowColData(i,15)==fm.idno.value)
		            			        &&(LLBnfGrid.getRowColData(i,11)==fm.relationtoinsured.value))
		            	{
		            		//���뵽�����ʾ��ͬһ��������,У��֧����ʽ
		            		 if (fm.CasePayMode.value == "" || fm.CasePayMode.value == null)
		            		 {
		            		        alert("֧����ʽ����Ϊ��!");
		            		        return false;
		            		 }
		            		 
		              		 //alert("fm.CasePayMode.value:"+fm.CasePayMode.value);
		              		 //alert("LLBnfGrid.getRowColData(i,23):"+LLBnfGrid.getRowColData(i,23));
		            		     
		            		 if(fm.CasePayMode.value != LLBnfGrid.getRowColData(i,24))
		            		 {
		            		    	alert("ͬһ������֧����ʽ����һ��!");
		            		    	return false;
		            		 }
		            		 
		              		 if(fm.CasePayMode.value == '4')
		            		 {
		              			if(fm.BankCode.value == "" || fm.BankCode.value == null )
		              	    	{	
		              	    		alert("֧����ʽΪ����ת��ʱ���б��벻��Ϊ��!");
		              	    		return false;
		              	    	}
		              			
		              			//alert("fm.BankCode.value:"+fm.BankCode.value);
		              			//alert("LLBnfGrid.getRowColData(i,24):"+LLBnfGrid.getRowColData(i,24));

		              			
		                		if(fm.BankCode.value != LLBnfGrid.getRowColData(i,25))
			            		{
			            		    	alert("ͬһ������֧����ʽΪ����ת��ʱ���б����豣��һ��!");
			            		    	return false;
			            		}

		              			
		              	    	if(fm.BankAccNo.value == "" || fm.BankAccNo.value == null )
		              	    	{	
		              	    		alert("֧����ʽΪ����ת��ʱ�����ʺŲ���Ϊ��!");
		              	    		return false;
		              	    	}
		              	    	

		             			//alert("fm.BankAccNo.value:"+fm.BankAccNo.value);
		              			//alert("LLBnfGrid.getRowColData(i,25):"+LLBnfGrid.getRowColData(i,25));
		              	    	
		                	    if(fm.BankAccNo.value != LLBnfGrid.getRowColData(i,26))
			            		{
			            		    	alert("ͬһ������֧����ʽΪ����ת��ʱ�����˺��豣��һ��!");
			            		    	return false;
			            		}

		              			
		              	    	if(fm.AccName.value == "" || fm.AccName.value == null )
		              	    	{	
		              	    		alert("֧����ʽΪ����ת��ʱ�����ʻ�������Ϊ��!");
		              	    		return false;
		              	    	}
		              	    	
		             			//alert("fm.AccName.value:"+fm.AccName.value);
		              			//alert("LLBnfGrid.getRowColData(i,26):"+LLBnfGrid.getRowColData(i,26));
		              	    	
		                  	    if(fm.AccName.value != LLBnfGrid.getRowColData(i,27))
			            		{
			            		    	alert("ͬһ������֧����ʽΪ����ת��ʱ���л����豣��һ��!");
			            		    	return false;
			            		}
		                  	    
		                  	    if(fm.payeeidtype.value != "0"||fm.idtype.value !="0"){
		                  	    	alert("��ȡ��ʽΪ����ת�ˣ�����������ȡ�˵�֤�����ͱ���Ϊ���֤!");
		            		    	return false;
		                  	    }
		            		 }        		     
		        		     
		        		     tFlag=true;
		        		     break;
		            	}
		            }

		            //alert("tFlag:"+tFlag);
		            
		            //������ӵ���һ����������
		            if(tFlag==true)
		            {
	        	    	alert("�������Ѿ�������б���,�����ظ����,�����޸ķ�����Ϣ,�����޸İ�ť����!");
        		    	return false;
		            }
		     }
		     
		     if(tFlag==false)
		     {
		    	//��һ����¼,ֱ�����������˼�¼
	 	    	 LLBnfGrid.addOne();
	 	         LLBnfGrid.setRowColData(rowNum,1,fm.ClmNo.value);//
	 	         LLBnfGrid.setRowColData(rowNum,2,fm.polNo.value);//
	 	         LLBnfGrid.setRowColData(rowNum,3,fm.insuredno.value);//
	 	         LLBnfGrid.setRowColData(rowNum,5,fm.customerNo.value);
	 	         LLBnfGrid.setRowColData(rowNum,6,fm.cName.value);//
	 	         LLBnfGrid.setRowColData(rowNum,7,fm.payeeNo.value);//
	 	         LLBnfGrid.setRowColData(rowNum,8,fm.payeename.value);//
	 	         LLBnfGrid.setRowColData(rowNum,9,fm.bnftype.value);//
	 	         LLBnfGrid.setRowColData(rowNum,10,fm.bnfgrade.value);//
	 	         LLBnfGrid.setRowColData(rowNum,11,fm.relationtoinsured.value);//
	 	         LLBnfGrid.setRowColData(rowNum,12,fm.sex.value);//
	 	         LLBnfGrid.setRowColData(rowNum,13,fm.birthday.value);//
	 	         LLBnfGrid.setRowColData(rowNum,14,fm.idtype.value);//
	 	         LLBnfGrid.setRowColData(rowNum,15,fm.idno.value);//
	 	         LLBnfGrid.setRowColData(rowNum,16,fm.relationtopayee.value);//
	 	         LLBnfGrid.setRowColData(rowNum,17,fm.payeesex.value);//
	 	         LLBnfGrid.setRowColData(rowNum,18,fm.payeebirthday.value);//
	 	         LLBnfGrid.setRowColData(rowNum,19,fm.payeeidtype.value);//
	 	         LLBnfGrid.setRowColData(rowNum,20,fm.payeeidno.value);//
	 	         LLBnfGrid.setRowColData(rowNum,24,fm.CasePayMode.value);//
	 	         LLBnfGrid.setRowColData(rowNum,25,fm.BankCode.value);//
	 	         LLBnfGrid.setRowColData(rowNum,26,fm.BankAccNo.value);//
	 	         LLBnfGrid.setRowColData(rowNum,27,fm.AccName.value);//
	 	         LLBnfGrid.setRowColData(rowNum,28,fm.FeeOperationType.value);//
	 	         LLBnfGrid.setRowColData(rowNum,29,fm.CasePayModeName.value);//
	 	         
			     LLBnfGrid.setRowColData(rowNum,4,strQueryResult[0][1]);//���������
			     LLBnfGrid.setRowColData(rowNum,21,fm.bnfCurrency.value);
			     LLBnfGrid.setRowColData(rowNum,22,fm.getmoney.value);//�����˷�����
			     LLBnfGrid.setRowColData(rowNum,23,fm.bnflot.value);//�����˷������
			     
	             fm.oriBnfNo.value = LLBnfGrid.getRowColData(rowNum,4);//�������������,Ϊ���޸���������ϢʱУ����
	 	         LLBnfGrid.setRowColData(rowNum,30,fm.oriBnfNo.value);//

		 	     LLBnfGrid.setRowColData(rowNum,31,LLBnfGrid.getRowColData(rowNum,22));
		         //alert("32--LLBnfGrid.getRowColData("+rowNum+",30):"+LLBnfGrid.getRowColData(rowNum,30));
		     }
	    }        
    }
}

//�޸�������
function updateClick()
{
    //1������ѡ�б���
    if (LLBalanceGrid.getSelNo() < 1)
    {
        alert("��ѡ�񱣵���");
        return;
    }
    
    //2���ǿռ���
    if(!checkInput())
    {
        return;
    }
    
    //3������mulLinemulLine�г�ѡ���еĺϼ�������ϼ�������� 
    var tAllMoney = 0;	//mulLine�кϼ�������
    var tAllLot = 0;	//mulLine�кϼ��������
    for (var j=0; j < LLBnfGrid.mulLineCount; j++)
    {
    	if (j != LLBnfGrid.getSelNo()-1){
        	tAllMoney = tAllMoney + parseFloat(LLBnfGrid.getRowColData(j,22));
        	tAllLot = tAllLot + parseFloat(LLBnfGrid.getRowColData(j,23));
        }
    }    
    
    //4��У���������֮��<=100������ʣ��ٷֱ� 
    if (fm.bnflot.value == "" || fm.bnflot.value == null)
    {
        fm.bnflot.value = 0;
    }    
    var tBnfLot = parseFloat(fm.bnflot.value);  
    if (tAllLot + tBnfLot > 100)	//У���������֮��<=100
    {
        alert("�������֮�ʹ���100�������!");
        return;
    }
	fm.RemainLot.value = Math.round((100 - tAllLot - tBnfLot)*100)/100;
    
    //5������������
    if(tAllLot + tBnfLot == 100)	//���Ϊ��������һ�ˣ�������ʣ���������
	{
		fm.getmoney.value = eval(parseFloat(fm.sumPay.value)-tAllMoney).toFixed(2)
	}
	else if(tAllLot + tBnfLot < 100)
	{
		fm.getmoney.value = eval((parseFloat(fm.sumPay.value)*tBnfLot*0.01).toFixed(2));
	}
    
	//6��У������ӵ������������Ҫ��ӵ���ͬһ���ˣ����ش�����ʾ
 	var rowNum = LLBnfGrid.mulLineCount;
	for(var i=0;i<rowNum;i++)
	{
		if (i != LLBnfGrid.getSelNo()-1){
			if((LLBnfGrid.getRowColData(i,3)==fm.insuredno.value)//���ݱ����������
				&&(LLBnfGrid.getRowColData(i,6)==fm.cName.value)
				&&(LLBnfGrid.getRowColData(i,12)==fm.sex.value)
				&&(LLBnfGrid.getRowColData(i,13)==fm.birthday.value)
				&&(LLBnfGrid.getRowColData(i,14)==fm.idtype.value)
				&&(LLBnfGrid.getRowColData(i,15)==fm.idno.value)
				&&(LLBnfGrid.getRowColData(i,11)==fm.relationtoinsured.value))
			{
				alert("���������Ѿ���ӣ������ظ����!");
				return false;
			}
		}
	}
   
    //7��У���ѱ���������������Ҫ��ӵ���ͬһ���ˣ�֧����ʽ����ϢҪһ�£����������������˵����
	/*var strSQL = " select GetMoney,BnfNo,CasePayMode,BankCode,BankAccNo,AccName,RelationToInsured from LLBnf "
		+ " where clmno ='"+document.all('ClmNo').value+"'"
		+ " and Insuredno='"+document.all('insuredno').value+"'"
		+ " and BnfKind='"+fm.BnfKind.value+"'"
		+ " and Name='"+document.all('cName').value+"'"
		+ " and sex='"+document.all('sex').value+"'"
		+ " and Birthday='"+document.all('birthday').value+"'"
		+ " and idtype='"+document.all('idtype').value+"'"
		+ " and idno='"+document.all('idno').value+"'";*/	//prompt("У���Ƿ���ͬһ����������Ϣ",strSQL);	    
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql16");
		mySql.addSubPara(document.all('ClmNo').value );  
		mySql.addSubPara(document.all('insuredno').value );  
		mySql.addSubPara(fm.BnfKind.value );  
		mySql.addSubPara(document.all('cName').value );  
		mySql.addSubPara(document.all('sex').value );  
		mySql.addSubPara(document.all('birthday').value );  
		mySql.addSubPara(document.all('idtype').value );  
		mySql.addSubPara(document.all('idno').value );  
	var strQueryResult = easyExecSql(mySql.getString());
  
	if(strQueryResult==null || strQueryResult=="")//�µ�������
	{
		fm.bnfno.value = LLBnfGrid.getRowColData(LLBnfGrid.getSelNo()-1,4);
	}
	else	//���ݿ��б������������	
	{
		fm.bnfno.value = strQueryResult[0][1];//ȡ�ѱ�������
	    if(fm.relationtoinsured.value != strQueryResult[0][6])//У�鱻�����˹�ϵ���뱣��һ��
    	{
    		alert("������:"+fm.cName.value+"���뱻�����˹�ϵ�뱣��һ��!");
    		return false;
    	}		
		if(fm.CasePayMode.value != strQueryResult[0][2])//У�������ʽ����һ��
		{
			alert("������:"+fm.cName.value+"��֧����ʽ�뱣��һ��!");
			return false;
		}
		if(fm.CasePayMode.value == '4')//��ѡ������ת�������б���,����,�����˺�Ҳ����һ��
		{
      		if(fm.BankCode.value == "" || fm.BankCode.value == null )//���б���
      	    {	
      	    	alert("֧����ʽΪ����ת��ʱ���б��벻��Ϊ��!");
      	    	return false;
      	    }
        	if(fm.BankCode.value != strQueryResult[0][3])
        	{
        		alert("ͬһ������֧����ʽΪ����ת��ʱ���б����豣��һ��!");
        		return false;
        	}
      	    if(fm.BankAccNo.value == "" || fm.BankAccNo.value == null )//�����ʺ�
      	    {	
      	    	alert("֧����ʽΪ����ת��ʱ�����ʺŲ���Ϊ��!");
      	    	return false;
      	    }
        	if(fm.BankAccNo.value != strQueryResult[0][4])
        	{
        		alert("ͬһ������֧����ʽΪ����ת��ʱ�����˺��뱣��һ��!");
        		return false;
        	}
      	    if(fm.AccName.value == "" || fm.AccName.value == null )//�����ʻ���
      	    {	
      	    	alert("֧����ʽΪ����ת��ʱ�����ʻ�������Ϊ��!");
      	    	return false;
      	    }
          	if(fm.AccName.value != strQueryResult[0][5])
        	{
        		alert("ͬһ������֧����ʽΪ����ת��ʱ�����ʻ����뱣��һ��!");
        		return false;
        	}
		}      	
	}

	var k = LLBnfGrid.getSelNo() - 1;//�õ�������
	LLBnfGrid.setRowColData(k,1,fm.ClmNo.value);//
	LLBnfGrid.setRowColData(k,2,fm.polNo.value);//
	LLBnfGrid.setRowColData(k,3,fm.insuredno.value);//
	LLBnfGrid.setRowColData(k,4,fm.bnfno.value);//���������
	LLBnfGrid.setRowColData(k,5,fm.customerNo.value);
	LLBnfGrid.setRowColData(k,6,fm.cName.value);//
	LLBnfGrid.setRowColData(k,7,fm.payeeNo.value);//
	LLBnfGrid.setRowColData(k,8,fm.payeename.value);//
	LLBnfGrid.setRowColData(k,9,fm.bnftype.value);//
	LLBnfGrid.setRowColData(k,10,fm.bnfgrade.value);//
	LLBnfGrid.setRowColData(k,11,fm.relationtoinsured.value);//
	LLBnfGrid.setRowColData(k,12,fm.sex.value);//
	LLBnfGrid.setRowColData(k,13,fm.birthday.value);//
	LLBnfGrid.setRowColData(k,14,fm.idtype.value);//
	LLBnfGrid.setRowColData(k,15,fm.idno.value);//
	LLBnfGrid.setRowColData(k,16,fm.relationtopayee.value);//
	LLBnfGrid.setRowColData(k,17,fm.payeesex.value);//
	LLBnfGrid.setRowColData(k,18,fm.payeebirthday.value);//
	LLBnfGrid.setRowColData(k,19,fm.payeeidtype.value);//
	LLBnfGrid.setRowColData(k,20,fm.payeeidno.value);//
	LLBnfGrid.setRowColData(k,21,fm.bnfCurrency.value);
	LLBnfGrid.setRowColData(k,22,fm.getmoney.value);//�����˷�����
	LLBnfGrid.setRowColData(k,23,fm.bnflot.value);//�����˷������	
	LLBnfGrid.setRowColData(k,24,fm.CasePayMode.value);//
	LLBnfGrid.setRowColData(k,25,fm.BankCode.value);//
	LLBnfGrid.setRowColData(k,26,fm.BankAccNo.value);//
	LLBnfGrid.setRowColData(k,27,fm.AccName.value);//
	LLBnfGrid.setRowColData(k,28,fm.FeeOperationType.value);//
	LLBnfGrid.setRowColData(k,29,fm.CasePayModeName.value);//
}

//�޸�������
function updateClick_1()
{
    var k = LLBnfGrid.getSelNo() - 1;//�õ�������
    if (k < 0)
    {
        alert("��ѡ��һ��!");
        return;
    }
    //�ǿռ���
    if(!checkInput())
    {
        return;
    }
    //����ٷֱȼ�������
    var i = LLBnfGrid.mulLineCount;
    var tAll = 0;
    var temp = 0;

    for (var j=0; j < i; j++)
    {
        if (j != k)
        {
            tAll = tAll + parseFloat(LLBnfGrid.getRowColData(j,23));
            //alert("LLBnfGrid.getRowColData("+j+",30):"+LLBnfGrid.getRowColData(j,30));
            temp = temp + parseFloat(LLBnfGrid.getRowColData(j,31));
        }
    }
    
    if (fm.bnflot.value == "" || fm.bnflot.value == null)
    {
        fm.bnflot.value = 0;
    }
    
    var tAllLot=tAll;//���ݳ����м�¼�����б����¼�ı���
    tAllLot=Math.round(tAllLot*100)/100; 
    
    var tBnfLot = parseFloat(fm.bnflot.value);
    tAll = tAll + tBnfLot;
    if (tAll > 100 || tAll < 0)
    {
        alert("�����������!");
        return;
    }
    else
    {
        //����ʣ��ٷֱ�
        fm.RemainLot.value = 100 - tAll;
        fm.RemainLot.value=Math.round(fm.RemainLot.value*100)/100;   
        //--------------------------------------------------------------------------------BEG
        //����������,���Ϊ��������һ�ˣ�������ʣ���������
        //--------------------------------------------------------------------------------

        if(fm.RemainLot.value == '0')
        {
            var tAllMoney = 0;

            for (var l=0; l < i; l++)
            {
                tAllMoney = tAllMoney + parseFloat(LLBnfGrid.getRowColData(l,22));
            }
            
        	//alert("tAllMoney:"+tAllMoney);
        	//alert("tAllLot:"+tAllLot);
        	//alert("fm.sumPay.value:"+fm.sumPay.value);

        	//���ֻ��һ������Ϊȫ�����,����Ϊ�ܽ��-���⸶�ı���*�ܽ��
        	if(i==1)
        	{
        		fm.getmoney.value = parseFloat(fm.sumPay.value);
        		
        	}
        	else
        	{
                //fm.getmoney.value = eval(parseFloat(fm.sumPay.value)-parseFloat(tAllLot)*0.01*parseFloat(fm.sumPay.value).toFixed(4));

                //fm.getmoney.value = parseFloat(fm.sumPay.value)-parseFloat(tAllLot)*0.01*parseFloat(fm.sumPay.value).toFixed(4);
                
            	//alert("fm.getmoney.value1:"+fm.getmoney.value);
            	
                fm.getmoney.value = eval(parseFloat(fm.sumPay.value)-temp).toFixed(2)
                
            	//alert("fm.getmoney.value2:"+fm.getmoney.value);
        	}


        }
        else
        {
            fm.getmoney.value = eval((tBnfLot*0.01*parseFloat(fm.sumPay.value)).toFixed(4));
        }
        
        
        
        /**
         * 2009-02-25 zhangzheng 
         * ��һ�����������ݽ������������Ϊ����������ҲҪ���з��䡣
		   �����������˷�����Ϣ���б���ʱ����ÿ�������˵ı��η�����Ŀ�����кϲ�(ÿ�������˵�������Ŀ�Ľ��ϲ��ں�ִ̨��)�����κ�һ�������˵���������Ϊ����ʱ�������˷��䲻�ܽ��б��棬������ϵͳ��ʾ"�����˵����������Ϊ���������º˶������˷�����Ϣ��"
		   ������������Ϣ�ж�����������Ϣ�����ݿ��е���Ϣ�ж�,ֻ�и�����Ϣ:�������뱻�����˹�ϵ,����,�Ա�,��������,֤������,֤��������ȫһ�º����ݿⱣ�����Ϣһ�²���Ϊ��һ����,���н��ϲ�,���ϲ���Ľ����Ǹ�ֵ;
         */      
        
	    /*var strSQL = " select GetMoney,BnfNo,CasePayMode,BankCode,BankAccNo,AccName,RelationToInsured,OBankCode from LLBnfGather"
 	         		 + " where clmno ='"+document.all('ClmNo').value+"'"
 	         		 //+ " and grpcontno='"+document.all('GrpContNo').value+"'"
 	         		 //+ " and grppolno='"+document.all('GrpPolNo').value+"'"
 	         		 //+ " and contno='"+document.all('ContNo').value+"'"
 	         		 //+ " and polno='"+document.all('polNo').value+"'"
 	         		 + " and Insuredno='"+document.all('insuredno').value+"'"
 	         		 + " and BnfKind='"+fm.BnfKind.value+"'"
 	         		 //+ " and FeeOperationType='"+document.all('FeeOperationType').value+"'"
 	         		 //+ " and RelationToInsured='"+document.all('relationtoinsured').value+"'"
 	         		 + " and Name='"+document.all('cName').value+"'"
 	         		 + " and sex='"+document.all('sex').value+"'"
 	         		 + " and Birthday='"+document.all('birthday').value+"'"
 	         		 + " and idtype='"+document.all('idtype').value+"'"
 	         		 + " and idno='"+document.all('idno').value+"'"*/
   mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql17");
		mySql.addSubPara(document.all('ClmNo').value );  
		mySql.addSubPara(document.all('insuredno').value );  
		mySql.addSubPara(fm.BnfKind.value );  
		mySql.addSubPara(document.all('cName').value );  
		mySql.addSubPara(document.all('sex').value );  
		mySql.addSubPara(document.all('birthday').value );  
		mySql.addSubPara(document.all('idtype').value );  
		mySql.addSubPara(document.all('idno').value );  
	    //prompt("У���Ƿ���ͬһ����������Ϣ",strSQL);
	    
	    var strQueryResult = easyExecSql(mySql.getString());
	    
	    var tFlag=false;//�ϲ���־
	    var mergeMoney=0.0;//�ϲ����
	    
	    if(strQueryResult==null || strQueryResult=="")
	    {
	    	


	         //���·���������˽��	    	 
	    	 //document.all('getmoney').value=(parseFloat(document.all('getmoney').value) ).toFixed(2);    	 
	    	 //alert("document.all('getmoney').value:"+document.all('getmoney').value);
	    	 
	    	 if(document.all('getmoney').value<0)
	    	 {
	    	      alert("�������������ܽ���Ϊ��!");
	    	      return false;
	    	 }
	      	    document.all('getmoney').value=(parseFloat(document.all('getmoney').value)).toFixed(2);
		    	document.all('oriBnfPay').value=parseFloat(document.all('getmoney').value);    
	            //���ݿ����û��,ֱ���޸ļ�¼
    	        LLBnfGrid.setRowColData(k,1,fm.ClmNo.value);//
    	        LLBnfGrid.setRowColData(k,2,fm.polNo.value);//
    	        LLBnfGrid.setRowColData(k,3,fm.insuredno.value);//

    	        //alert("fm.oriBnfNo.value1:"+fm.oriBnfNo.value);
    	        //alert("mBnfBatNo:"+mBnfBatNo);
    	        
    	        //��������ֶ�Ϊ��,��������ǵ�һ�������˼�¼
    	        if(fm.oriBnfNo.value==null||fm.oriBnfNo.value=="")
    	        {
    	        	mBnfBatNo++
	    	        fm.bnfno.value=mBnfBatNo;
	    	        
    	        	LLBnfGrid.setRowColData(k,4,fm.bnfno.value);
    	        	
    	        	//alert("LLBnfGrid.getRowColData("+k+",4):"+LLBnfGrid.getRowColData(k,4));
    	        	
    	        	fm.oriBnfNo.value = LLBnfGrid.getRowColData(k,4);//�������������,Ϊ���޸���������ϢʱУ����
    		        LLBnfGrid.setRowColData(k,30,LLBnfGrid.getRowColData(k,4));//
    	        }
    	        
      	        //alert("fm.oriBnfNo.value2:"+fm.oriBnfNo.value);
  
               	
    	        LLBnfGrid.setRowColData(k,5,fm.customerNo.value);
    	        LLBnfGrid.setRowColData(k,6,fm.cName.value);//
    	        LLBnfGrid.setRowColData(k,7,fm.payeeNo.value);//
    	        LLBnfGrid.setRowColData(k,8,fm.payeename.value);//
    	        LLBnfGrid.setRowColData(k,9,fm.bnftype.value);//
    	        LLBnfGrid.setRowColData(k,10,fm.bnfgrade.value);//
    	        LLBnfGrid.setRowColData(k,11,fm.relationtoinsured.value);//
    	        LLBnfGrid.setRowColData(k,12,fm.sex.value);//
    	        LLBnfGrid.setRowColData(k,13,fm.birthday.value);//
    	        LLBnfGrid.setRowColData(k,14,fm.idtype.value);//
    	        LLBnfGrid.setRowColData(k,15,fm.idno.value);//
    	        LLBnfGrid.setRowColData(k,16,fm.relationtopayee.value);//
    	        LLBnfGrid.setRowColData(k,17,fm.payeesex.value);//
    	        LLBnfGrid.setRowColData(k,18,fm.payeebirthday.value);//
    	        LLBnfGrid.setRowColData(k,19,fm.payeeidtype.value);//
    	        LLBnfGrid.setRowColData(k,20,fm.payeeidno.value);//
    	        LLBnfGrid.setRowColData(k,21,fm.bnfCurrency.value);//
    	        LLBnfGrid.setRowColData(k,22,fm.getmoney.value);//
    	        LLBnfGrid.setRowColData(k,23,fm.bnflot.value);//
    	        LLBnfGrid.setRowColData(k,24,fm.CasePayMode.value);//
    	        LLBnfGrid.setRowColData(k,25,fm.BankCode.value);//
    	        LLBnfGrid.setRowColData(k,26,fm.BankAccNo.value);//
    	        LLBnfGrid.setRowColData(k,27,fm.AccName.value);//
    	        LLBnfGrid.setRowColData(k,28,fm.FeeOperationType.value);//
    	        LLBnfGrid.setRowColData(k,29,fm.CasePayModeName.value);//
    	 	    LLBnfGrid.setRowColData(k,31,LLBnfGrid.getRowColData(k,21));
	            //alert("42-LLBnfGrid.getRowColData("+k+",30):"+LLBnfGrid.getRowColData(k,30));


	    }
	    else
	    {
	    	
	    	 //����У�������ʽ����һ��,��ѡ������ת�������б���,����,�����˺�Ҳ����һ��
	    	 if(fm.CasePayMode.value != strQueryResult[0][2])
    		 {
    		    	alert("������"+fm.cName.value+"��֧����ʽ�뱣��һ��!");
    		    	return false;
    		 }
      		 if(fm.CasePayMode.value == '4')
    		 {
      			if(fm.BankCode.value == "" || fm.BankCode.value == null )
      	    	{	
      	    		alert("֧����ʽΪ����ת��ʱ���б��벻��Ϊ��!");
      	    		return false;
      	    	}
      			
      			//alert("fm.BankCode.value:"+fm.BankCode.value);
      			//alert("LLBnfGrid.getRowColData(i,24):"+LLBnfGrid.getRowColData(i,24));

      			
        		if(fm.BankCode.value != strQueryResult[0][3])
        		{
        		    	alert("ͬһ������֧����ʽΪ����ת��ʱ���б����豣��һ��!");
        		    	return false;
        		}

      			
      	    	if(fm.BankAccNo.value == "" || fm.BankAccNo.value == null )
      	    	{	
      	    		alert("֧����ʽΪ����ת��ʱ�����ʺŲ���Ϊ��!");
      	    		return false;
      	    	}
      	    	

     			//alert("fm.BankAccNo.value:"+fm.BankAccNo.value);
      			//alert("LLBnfGrid.getRowColData(i,25):"+LLBnfGrid.getRowColData(i,25));
      	    	
        	    if(fm.BankAccNo.value != strQueryResult[0][4])
        		{
        		    	alert("������"+fm.cName.value+"��֧����ʽΪ�����˺��뱣��һ��!");
        		    	return false;
        		}

      			
      	    	if(fm.AccName.value == "" || fm.AccName.value == null )
      	    	{	
      	    		alert("֧����ʽΪ����ת��ʱ�����ʻ�������Ϊ��!");
      	    		return false;
      	    	}
      	    	
     			//alert("fm.AccName.value:"+fm.AccName.value);
      			//alert("LLBnfGrid.getRowColData(i,26):"+LLBnfGrid.getRowColData(i,26));
      	    	
          	    if(fm.AccName.value != strQueryResult[0][5])
        		{
        		    	alert("������"+fm.cName.value+"��֧����ʽΪ���л����뱣��һ��!");
        		    	return false;
        		}
          	    
          	    if(fm.payeeidtype.value != "0"||fm.idtype.value !="0"){
                  alert("��ȡ��ʽΪ����ת�ˣ�����������ȡ�˵�֤�����ͱ���Ϊ���֤!");
                  return false;
          	    }
    		 }
	    	 //У�鱻�����˹�ϵ���뱣��һ��
	    	 if(fm.relationtoinsured.value != strQueryResult[0][6])
    		 {
    		    	alert("������"+fm.cName.value+"���뱻�����˹�ϵ�뱣��һ��!");
    		    	return false;
    		 }

	         //���·���������˽��	    	 
		     //document.all('oriBnfPay').value=parseFloat(document.all('getmoney').value);    
	    	 document.all('getmoney').value=(parseFloat(document.all('getmoney').value) + parseFloat(strQueryResult[0][0])).toFixed(2);    	 
	    	 //alert("document.all('getmoney').value:"+document.all('getmoney').value);
	    	 
	    	 if(document.all('getmoney').value<0)
	    	 {
	    	      alert("�������������ܽ���Ϊ��!");
	    	      return false;
	    	 }
	    	 
		     LLBnfGrid.setRowColData(k,4,strQueryResult[0][1]);//���������
		     
 	         LLBnfGrid.setRowColData(k,1,fm.ClmNo.value);//
 	         LLBnfGrid.setRowColData(k,2,fm.polNo.value);//
 	         LLBnfGrid.setRowColData(k,3,fm.insuredno.value);//
 	         LLBnfGrid.setRowColData(k,5,fm.customerNo.value);
 	         LLBnfGrid.setRowColData(k,6,fm.cName.value);//
 	         LLBnfGrid.setRowColData(k,7,fm.payeeNo.value);//
 	         LLBnfGrid.setRowColData(k,8,fm.payeename.value);//
 	         LLBnfGrid.setRowColData(k,9,fm.bnftype.value);//
 	         LLBnfGrid.setRowColData(k,10,fm.bnfgrade.value);//
 	         LLBnfGrid.setRowColData(k,11,fm.relationtoinsured.value);//
 	         LLBnfGrid.setRowColData(k,12,fm.sex.value);//
 	         LLBnfGrid.setRowColData(k,13,fm.birthday.value);//
 	         LLBnfGrid.setRowColData(k,14,fm.idtype.value);//
 	         LLBnfGrid.setRowColData(k,15,fm.idno.value);//
 	         LLBnfGrid.setRowColData(k,16,fm.relationtopayee.value);//
 	         LLBnfGrid.setRowColData(k,17,fm.payeesex.value);//
 	         LLBnfGrid.setRowColData(k,18,fm.payeebirthday.value);//
 	         LLBnfGrid.setRowColData(k,19,fm.payeeidtype.value);//
 	         LLBnfGrid.setRowColData(k,20,fm.payeeidno.value);//
 	         LLBnfGrid.setRowColData(k,21,fm.bnfCurrency.value);//
 	         LLBnfGrid.setRowColData(k,22,fm.getmoney.value);//
 	         LLBnfGrid.setRowColData(k,23,fm.bnflot.value);//
 	         LLBnfGrid.setRowColData(k,24,fm.CasePayMode.value);//
 	         LLBnfGrid.setRowColData(k,25,fm.BankCode.value);//
 	         LLBnfGrid.setRowColData(k,26,fm.BankAccNo.value);//
 	         LLBnfGrid.setRowColData(k,27,fm.AccName.value);//
 	         LLBnfGrid.setRowColData(k,28,fm.FeeOperationType.value);//
 	         LLBnfGrid.setRowColData(k,29,fm.CasePayModeName.value);//
 	         	         
 	         //��������ֶ�Ϊ��,��������ǵ�һ�������˼�¼
 	         fm.oriBnfNo.value = strQueryResult[0][6];//�������������
	 	     
 	 	    LLBnfGrid.setRowColData(k,31,LLBnfGrid.getRowColData(k,22));
            //alert("42-LLBnfGrid.getRowColData("+k+",30):"+LLBnfGrid.getRowColData(k,30));

	    } 
    }
}

//ɾ��������
function deleteClick()
{
    var k = LLBnfGrid.getSelNo() - 1;//�õ�������
    if (k < 0)
    {
        alert("��ѡ��һ��!");
        return;
    }
    
    //-------------------------------------------------------------------------------------------------------------------------BEG
    //����������ɾ����ʱ��,ֻ��ɾ��֧����־Ϊ0������,Ҳ����δ֧��,��Ϊ�����־Ϊ1�����ñ������Ѿ����е�֧������(��Ҫ���Ԥ��)
    //-------------------------------------------------------------------------------------------------------------------------
    /*var strSQL = " select CasePayFlag from LLBnf where "
               + " ClmNo = '" + LLBnfGrid.getRowColData(k,1) + "'"
               + " and CaseNo = '" + LLBnfGrid.getRowColData(k,1) + "'"
               + " and BatNo = '" + fm.BatNo.value + "'"
               + " and BnfKind = '" + fm.BnfKind.value + "'"
               + " and PolNo = '" + LLBnfGrid.getRowColData(k,2) + "'"
               + " and InsuredNo = '" + LLBnfGrid.getRowColData(k,3) + "'"
               + " and BnfNo = '" + LLBnfGrid.getRowColData(k,4) + "'"
               + " order by BnfNo";*/
     mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql18");
		mySql.addSubPara(LLBnfGrid.getRowColData(k,1));  
		mySql.addSubPara(LLBnfGrid.getRowColData(k,1)  ); 
		mySql.addSubPara(fm.BatNo.value );  
		mySql.addSubPara(fm.BnfKind.value );  
		mySql.addSubPara( LLBnfGrid.getRowColData(k,2) );  
		mySql.addSubPara( LLBnfGrid.getRowColData(k,3));  
		mySql.addSubPara( LLBnfGrid.getRowColData(k,4) ); 
//    alert(strSQL);
    var arr = easyExecSql( mySql.getString() );
    if (arr == '1')
    {
        alert("�ñ������Ѿ����е�֧������,����ɾ����");
        return;
    }
    else
    {
        LLBnfGrid.delRadioTrueLine();
        emptyInput();
    }
 
    //-------------------------------------------------------------------------------------------------------------------------END
}

//У�����ڱ������Ŀ�Ƿ��Ѿ��������,������ѱ�������������ȫ��ɾ�����б������Ŀ����
function checkRepeatSave()
{
	 
	 /*var strSql = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.FeeOperationType = '"+fm.FeeOperationType.value+"') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
         + getWherePart( 'ClmNo','ClmNo' )
         + " and grpcontno='"+fm.GrpContNo.value+"'"
         + " and grppolno='"+fm.GrpPolNo.value+"'"
         + " and contno='"+fm.ContNo.value+"'"
         + " and polno='"+fm.PolNo.value+"'"
         + " and Feeoperationtype='"+fm.FeeOperationType.value+"'";*/
	  mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql19");
		mySql.addSubPara(fm.FeeOperationType.value);  
		mySql.addSubPara(fm.ClmNo.value);  
		mySql.addSubPara(fm.GrpContNo.value);  
		mySql.addSubPara(fm.GrpPolNo.value); 
		mySql.addSubPara(fm.ContNo.value );  
		mySql.addSubPara(fm.PolNo.value );  
		mySql.addSubPara( fm.FeeOperationType.value);  

	 //prompt("У��ͬһ��Ŀ�Ƿ񷴸�����",strSql);
	 
	 var tBNF = easyExecSql(mySql.getString());

	 if (tBNF)
	 {
		 for (var i = 0; i < tBNF.length; i++)
		 {
			 if (tBNF[i] == '1')
			 {
				 alert("����Ŀ���������Ѿ����,�����޸�,���ȫ��������ť�����ѱ����������Ŀ�����·���!");
				 return false;
			 }
		 }
	 }

     return true;
}

//�Ա����޸ı��浽��̨
function saveClick()
{
	//���ƹ������ظ������ѱ������Ŀ,������ȫ��ɾ����������¼��
    if (!checkRepeatSave())
    {
        return;
    }

//	alert(document.all('insuredno').value);
    //����ʱ���ǿ���У��
    var i = LLBnfGrid.mulLineCount;
    var tAll = 0;
    for (var j=0; j < i; j++)
    {
        if (LLBnfGrid.getRowColData(j,6) == null || LLBnfGrid.getRowColData(j,6) == "")
        {
            j++;
            alert("������"+j+"����������Ϊ��!");
            return;
        }
        
        if (LLBnfGrid.getRowColData(j,8) == null || LLBnfGrid.getRowColData(j,8) == "")
        {
            j++;
            alert("������"+j+"���������������Ϊ��!");
            return;
        }

        if (LLBnfGrid.getRowColData(j,12) == null || LLBnfGrid.getRowColData(j,12) == "")
        {
            j++;
            alert("������"+j+"���Ա���Ϊ��!");
            return;
        }
        if (LLBnfGrid.getRowColData(j,13) == null || LLBnfGrid.getRowColData(j,13) == "")
        {
            j++;
            alert("������"+j+"�ĳ������ڲ���Ϊ��!");
            return;
        }
        if (LLBnfGrid.getRowColData(j,14) != "9" ){
	        if (LLBnfGrid.getRowColData(j,15) == null || LLBnfGrid.getRowColData(j,15) == "")
	        {
	            j++;
	            alert("������"+j+"��֤�����벻��Ϊ��!");
	            return;
	        }
        }
        if (LLBnfGrid.getRowColData(j,17) == null || LLBnfGrid.getRowColData(j,17) == "")
        {
            j++;
            alert("������"+j+"��������Ա���Ϊ��!");
            return;
        }
        if (LLBnfGrid.getRowColData(j,18) == null || LLBnfGrid.getRowColData(j,18) == "")
        {
            j++;
            alert("������"+j+"����������ղ���Ϊ��!");
            return;
        }
        if (LLBnfGrid.getRowColData(j,19) != "9" ){
	        if (LLBnfGrid.getRowColData(j,20) == null || LLBnfGrid.getRowColData(j,20) == "")
	        {
	            j++;
	            alert("������"+j+"�������֤�����벻��Ϊ��!");
	            return;
	        }
        }
        if (
        		//LLBnfGrid.getRowColData(j,22) < 0 ||
        		LLBnfGrid.getRowColData(j,23) > 100)
        {
            j++;
            alert("������"+j+"�������������!");
            return;
        }
        if (LLBnfGrid.getRowColData(j,24) == null || LLBnfGrid.getRowColData(j,24) == "")
        {
            j++;
            alert("������"+j+"��֧����ʽ����Ϊ��!");
            return;
        }
        tAll = tAll + parseFloat(LLBnfGrid.getRowColData(j,23));
    }
    
    //�������100%У��
    if(tAll != 100)
    {
        alert("δ��ȫ����������!");
        return;
    }
    
    fm.action = './LLBnfSave.jsp';
   // alert(document.all('insuredno').value);
    submitForm();
}

//�ύת�����
function saveClmToPension()
{
    if (fm.PensionType.value == "" || fm.PensionType.value == null)
    {
        alert("��ѡ��ת������ͣ�");
        return;
    }
    fm.action = './LLBnfToPensionSave.jsp';
    submitForm();
}

//ȫ����������
function repealClick()
{
    //�ύ����
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = './LLBnfRepealSave.jsp';
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";
}

//�����ύ����
function submitForm()
{
    //�ύ����
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //�ύ
    tSaveFlag ="0";
	
}

//�ύ�����,����
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    //queryBnfBatNo();
    
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        emptyInput();
        queryLLBalanceGrid();

        /**-----------------------------------------------------------------------------------BEG
         * ת������ӵ�����ж��Ƿ��������ʾ��ť�Ͳ�����
         * add ���� 2006-3-3 9:23
         *-----------------------------------------------------------------------------------*/
        if (fm.ClmFlag.value == "2")
        {
            document.all("divButtonBnf").style.display = "none";
            document.all("divButtonToPension").style.display = "none";
            fm.clmToPension.disabled = true;
        }
    }
    tSaveFlag ="0";
}

//���������,����
function afterSubmit2( FlagStr, content )
{
    showInfo.close();

    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=350;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        emptyInput();
        initForm();
//        alert("insuredno="+fm.insuredno.value);
    }

}

//Ĭ����ȡ��Ϊ������
function setName()
{
    //����
    if(fm.payeeNo.value == "" || fm.payeeNo.value == null)
    {
        fm.payeeNo.value = fm.customerNo.value;
    }
    //����
    if(fm.payeename.value == "" || fm.payeename.value == null)
    {
        fm.payeename.value = fm.cName.value;
    }
}

//���������
function emptyInput()
{
//    fm.ClmNo.value = "";
//    fm.polNo.value = "";
//    fm.insuredno.value = "";
    fm.bnfno.value = "";
//    fm.customerNo.value = "";
    fm.cName.value = "";
//    fm.payeeNo.value = "";
    fm.payeename.value = "";
//    fm.bnftype.value = "";
//    fm.bnfgrade.value = "";
    fm.relationtoinsured.value = "";
    fm.sex.value = "";
    fm.birthday.value = "";
    fm.idtype.value = "";
    fm.idno.value = "";
    fm.relationtopayee.value = "";
    fm.payeesex.value = "";
    fm.payeebirthday.value = "";
    fm.payeeidtype.value = "";
    fm.payeeidno.value = "";
    fm.getmoney.value = "";
    fm.bnflot.value = "";
    fm.CasePayMode.value = "";
    fm.CasePayModeName.value = "";

    fm.relationtoinsuredName.value = "";
    fm.SexName.value = "";
    fm.idtypeName.value = "";
    fm.relationtopayeeName.value = "";
    fm.payeesexName.value = "";
    fm.payeeidtypeName.value = "";
    fm.BankCode.value = "";
    fm.BankCodeName.value = "";
    fm.BankAccNo.value = "";
    fm.AccName.value = "";
    
//    fm.PensionType.value = "";
//    fm.PensionTypeName.value = "";
}

//���ǿ�
function checkInput()
{
   
    if (fm.insuredno.value == "" || fm.insuredno.value == null)
    {
        alert("�����˺��벻��Ϊ��!");
        return false;
    }
    
    if (fm.relationtoinsured.value == "" || fm.relationtoinsured.value == null)
    {
        alert("�������뱻���˹�ϵ����Ϊ��!");
        return false;
    }
    
    if (fm.cName.value == "" || fm.cName.value == null)
    {
        alert("��������������Ϊ��!");
        return false;
    }
    
    //niuzj,2005-11-02,���ӷǿ�У��
    if (fm.sex.value == "" || fm.sex.value == null)
    {
        alert("�������Ա���Ϊ��!");
        return false;
    }
    if (fm.birthday.value == "" || fm.birthday.value == null)
    {
        alert("�����˳������ڲ���Ϊ��!");
        return false;
    }
    if (fm.idtype.value == "" || fm.idtype.value == null)
    {
        alert("������֤�����Ͳ���Ϊ��!");
        return false;
    }
//    if(fm.idtype.value != "9"){
	    if (fm.idno.value == "" || fm.idno.value == null)
	    {
	        alert("������֤�����벻��Ϊ��!");
	        return false;
	    }
//    }
    
    if (fm.relationtopayee.value == "" || fm.relationtopayee.value == null)
    {
        alert("������������˹�ϵ����Ϊ��!");
        return false;
    }
    
    if (fm.payeename.value == "" || fm.payeename.value == null)
    {
        alert("�������������Ϊ��!");
        return false;
    }
    //niuzj,2005-11-02,���ӷǿ�У��
    if (fm.payeesex.value == "" || fm.payeesex.value == null)
    {
        alert("������Ա���Ϊ��!");
        return false;
    }
    
    if (fm.payeebirthday.value == "" || fm.payeebirthday.value == null)
  
    {
        alert("����˳������ڲ���Ϊ��!");
        return false;
    }
    if (fm.payeeidtype.value == "" || fm.payeeidtype.value == null)
    {
        alert("�����֤�����Ͳ���Ϊ��!");
        return false;
    }
//    if (fm.payeeidtype.value != "9" ){
	    if (fm.payeeidno.value == "" || fm.payeeidno.value == null)
	    {
	        alert("�����֤�����벻��Ϊ��!");
	        return false;
	    }
//    }
    
    if((fm.relationtoinsured.value=="00")&&(fm.relationtopayee.value=="00"))
    {
        if (fm.cName.value !=fm.payeename.value)
        {
            alert("�������˺�����˾�Ϊ�������˱���ʱ,�������������������������һ��!");
            return false;
        }
        
        if (fm.sex.value !=fm.payeesex.value)
        {
            alert("�������˺�����˾�Ϊ�������˱���ʱ,�������Ա��������Ա����һ��!");
            return false;
        }
        
        if (fm.birthday.value !=fm.payeebirthday.value)
        {
            alert("�������˺�����˾�Ϊ�������˱���ʱ,�����˳������ں�����˳������ڱ���һ��!");
            return false;
        }
        
        if (fm.idtype.value !=fm.payeeidtype.value)
        {
            alert("�������˺�����˾�Ϊ�������˱���ʱ,������֤�����ͺ������֤�����ͱ���һ��!");
            return false;
        }
        
        if (fm.idno.value !=fm.payeeidno.value)
        {
            alert("�������˺�����˾�Ϊ�������˱���ʱ,������֤������������֤���������һ��!");
            return false;
        }
    }
    
	if(tDeadFlag ==1)
	{
	    //��ʰ����������˺�����˾������Ǳ������˱���
	    if(fm.relationtoinsured.value=="00")
	    {
			  alert("��ʰ����������˲����Ǳ������˱���!");
		      return false;
	    }
	    
	    if(fm.relationtopayee.value=="00")
	    {
			  alert("��ʰ���������˲����Ǳ������˱���!");
		      return false;
	    }
	}

    if (fm.CasePayMode.value == "" || fm.CasePayMode.value == null)
    {
        alert("֧����ʽ����Ϊ��!");
        return false;
    }
     
    if((fm.CasePayMode.value != null || fm.CasePayMode.value != "") && fm.CasePayMode.value == '4')
    {
    	if(fm.BankCode.value == "" || fm.BankCode.value == null || fm.BankCode.value == '0')
    	{
    		alert("���б��벻��Ϊ��!");
    		return false;
    	}
    	if(fm.BankAccNo.value == "" || fm.BankAccNo.value == null || fm.BankAccNo.value == '0')
    	{	
    		alert("�����ʺŲ���Ϊ��!");
    		return false;
    	}
    	if(!checkBankAccNo(fm.BankCode.value, fm.BankAccNo.value)){
    		return false;
    	}
    	if(fm.AccName.value == "" || fm.AccName.value == null || fm.AccName.value == '0')
    	{	
    		alert("�����ʻ�������Ϊ��!");
    		return false;
    	}
    	if(fm.payeeidtype.value != "0"||fm.idtype.value !="0"){
            alert("��ȡ��ʽΪ����ת�ˣ�����������ȡ�˵�֤�����ͱ���Ϊ���֤!");
            return false;
    	}
    }
    
    if((fm.CasePayMode.value != null || fm.CasePayMode.value != "") && fm.CasePayMode.value == '1')
    {
    	if((fm.BankCode.value != "" && fm.BankCode.value != null) || fm.BankCode.value == '0')
    	{	
    		alert("֧����ʽΪ�ֽ�ʱ���б�����Ϊ��!");
    		return false;
    	}
    	if((fm.BankAccNo.value != "" && fm.BankAccNo.value != null) || fm.BankAccNo.value == '0')
    	{	
    		alert("֧����ʽΪ�ֽ�ʱ�����ʺ���Ϊ��!");
    		return false;
    	}
    	if((fm.AccName.value != "" && fm.AccName.value != null) || fm.AccName.value == '0')
    	{	
    		alert("֧����ʽΪ�ֽ�ʱ�����ʻ�����Ϊ��!");
    		return false;
    	}
    }
    
    return true;
}

//��ʾ����span��
function showDiv(spanID,divID)
{
    if (spanID != null)
    {
        document.all(divID).style.display="";
    }
    else
    {
        document.all(divID).style.display="none";
    }
}

//���ý����ϵ����а�ťΪdisabled
function disabledButton()
{
    var elementsNum = 0;//FORM�е�Ԫ����
    //����FORM�е�����ELEMENT
    for (elementsNum=0; elementsNum<fm.elements.length; elementsNum++)
    {
  	    if (fm.elements[elementsNum].type == "button")
  	    {
  	        fm.elements[elementsNum].disabled = true;
  	    }
  	}
}

/*******************************************************************
 * ��ʼ
 * ��֤�������������֤
 * ���ߣ� �����
 * ���ڣ� 2005-11-11
 ******************************************************************/

//��֤֤������
function checkidtype()
{
	if(fm.idno.value==""&&fm.idno.value!="")
	{
		alert("����ѡ��֤�����ͣ�");
		fm.idno.value="";
  }
}

//��֤֤������
function checkidtype1()
{
	if(fm.payeeidno.value==""&&fm.payeeidno.value!="")
	{
		alert("����ѡ��֤�����ͣ�");
		fm.payeeidno.value="";
  }
}

//�������֤��ȡ�ó������ں��Ա�
function getBirthdaySexByIDNo(iIdNo)
{
	if(document.all('idtype').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="��������֤��λ������"||tSex=="��������֤��λ������")
		{
			alert("��������֤��λ������");
			theFirstValue="";
			theSecondValue="";
			return;
		}
		else
		{
			document.all('birthday').value=tBirthday;
			document.all('sex').value=tSex;
		}
	}
}


function getBirthdaySexByIDNo1(iIdNo)
{
	if(document.all('payeeidtype').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="��������֤��λ������"||tSex=="��������֤��λ������")
		{
			alert("��������֤��λ������");
			theFirstValue="";
			theSecondValue="";
			return;
		}
		else
		{
			document.all('payeebirthday').value=tBirthday;
			document.all('payeesex').value=tSex;
		}
	}
}




//2009-01-10 zhangzheng ˫������������Ӧ����
function afterCodeSelect( cCodeName, Field ) {

    //alert(cCodeName);  
    //alert(Field.name);  

	/**=========================================================================
	 ��ѡ�������˺ͱ������˹�ϵ��������뱻�����˹�ϵʱ����Ǳ����Զ������������˵������Ϣ
	=========================================================================
	**/
	//�Ƿ�ͬʱ����Ԥ����Ϣ
    if(cCodeName=="relation"){
    	
    	//�������뱻���˹�ϵ 
    	if(Field.name=="relationtoinsured"){
    		
    		if(fm.relationtoinsured.value=="00")
    		{
    			  //alert(document.all('insuredno').value);
    			  
    			  if( document.all('insuredno').value != null  && document.all('insuredno').value != "")
    			  {
    			     /* var strSQL = " select name,sex,(select codename from ldcode where codetype='sex' and code=sex),birthday,"
    			    	         + " idtype,(select codename from ldcode where codetype='idtype' and code=idtype ),idno "
    			    	         + " from ldperson where customerno ='"+document.all('insuredno').value+"'";*/
    			      mySql = new SqlClass();
						mySql.setResourceName("claim.LLBnfInputSql");
						mySql.setSqlId("LLBnfSql20");
						mySql.addSubPara(document.all('insuredno').value);  

//    			      prompt("���������˺ͳ����˹�ϵ��ѯ����������Ϣ",strSQL);
    			      
    			      var strQueryResult = easyExecSql(mySql.getString());
    			      if(strQueryResult==null || strQueryResult=="")
    			      {
    			      		return;
    			      }
    			      else
    			      {
    			    	  fm.cName.value = strQueryResult[0][0];
    			    	  fm.sex.value = strQueryResult[0][1];
    			    	  fm.SexName.value = strQueryResult[0][2];
    			    	  
    			    	  fm.birthday.value = strQueryResult[0][3];
    			    	  fm.idtype.value = strQueryResult[0][4];
    			    	  fm.idtypeName.value = strQueryResult[0][5];
    			    	  
    			    	  fm.idno.value = strQueryResult[0][6];

    			      }
    			  }
    			  else{
    		          return;
    		      }
    		}
    		else
    		{
    	    	  fm.cName.value = "";
    	    	  fm.sex.value = "";
    	    	  fm.SexName.value = "";
    	    	  
    	    	  fm.birthday.value = "";
    	    	  fm.idtype.value = "";
    	    	  fm.idtypeName.value = "";
    	    	  
    	    	  fm.idno.value = "";
    		}
        }
    	
    	//������������˹�ϵ
    	if(Field.name=="relationtopayee"){
    		
    		if(fm.relationtopayee.value=="00")
    		{
    			  //alert(document.all('insuredno').value);
    			  
    			document.all("relationtopayee").value=document.all("relationtoinsured").value;
    			document.all("payeename").value=document.all("cName").value;
    			document.all("payeesex").value=document.all("sex").value;
    			document.all("payeebirthday").value=document.all("birthday").value;
    			document.all("payeeidtype").value=document.all("idtype").value;
    			document.all("payeeidno").value=document.all("idno").value;
    			/* if( document.all('insuredno').value != null  && document.all('insuredno').value != "")
    			  {
    			      var strSQL = " select name,sex,(select codename from ldcode where codetype='sex' and code=sex),birthday,"
    			    	         + " idtype,(select codename from ldcode where codetype='idtype' and code=idtype ),idno "
    			    	         + " from ldperson where customerno ='"+document.all('insuredno').value+"'";
    			       mySql = new SqlClass();
						mySql.setResourceName("claim.LLBnfInputSql");
						mySql.setSqlId("LLBnfSql21");
						mySql.addSubPara(document.all('insuredno').value);  
    			      //prompt("���������˺ͳ����˹�ϵ��ѯ����������Ϣ",strSQL);
    			      
    			      var strQueryResult = easyExecSql(mySql.getString());
    			      if(strQueryResult==null || strQueryResult=="")
    			      {
    			      		return;
    			      }
    			      else
    			      {
    			    	  fm.payeename.value = strQueryResult[0][0];
    			    	  fm.payeesex.value = strQueryResult[0][1];
    			    	  fm.payeesexName.value = strQueryResult[0][2];
    			    	  
    			    	  fm.payeebirthday.value = strQueryResult[0][3];
    			    	  fm.payeeidtype.value = strQueryResult[0][4];
    			    	  fm.payeeidtypeName.value = strQueryResult[0][5];
    			    	  
    			    	  fm.payeeidno.value = strQueryResult[0][6];

    			      }
    			  }
    			  else{
    		          return;
    		      }*/
    		}
    		else
    		{
		    	  fm.payeename.value = "";
		    	  fm.payeesex.value = "";
		    	  fm.payeesexName.value = "";
		    	  
		    	  fm.payeebirthday.value = "";
		    	  fm.payeeidtype.value = "";
		    	  fm.payeeidtypeName.value = "";
		    	  
		    	  fm.payeeidno.value = "";
    		}
        }

    	
        return true;
    }
}


/*********************************************************************
 *  �������֤��ȡ�ó������ں��Ա�
 *  ����  ��  ���֤��
 *  ����ֵ��  ��
 *********************************************************************
 */

function getBirthdaySexByIDNo2(iIdNo)
{
	//alert("aafsd");
	if(document.all('idtype').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="��������֤��λ������"||tSex=="��������֤��λ������")
		{
			alert("��������֤��λ������");
			theFirstValue="";
			theSecondValue="";
			//document.all('IDNo').focus();
			return;

		}
		else
		{
			document.all('birthday').value=tBirthday;
			document.all('sex').value=tSex;
		}

	}
	
	if(document.all('payeeidtype').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="��������֤��λ������"||tSex=="��������֤��λ������")
		{
			alert("��������֤��λ������");
			theFirstValue="";
			theSecondValue="";
			//document.all('IDNo').focus();
			return;

		}
		else
		{
			document.all('payeebirthday').value=tBirthday;
			document.all('payeesex').value=tSex;
		}

	}
}
function CRUD(){
	if(cFlag==1)
		document.all('divCRUD').style.display="none";
}
