var showInfo;
var mDebug = "1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mBnfBatNo=0;
var tDeadFlag = 0;
var str_sql = "",sql_id = "", my_sql = "";   //�󶨱����������ݿ�


//��ѯ���ݿ������������
function queryBnfBatNo()
{
    var strSQL = "";
    mBnfBatNo=0;
    
    if (fm.BnfKind.value == 'A')
    {
    	var strSQL1 = "";
    	var sqlid1="LLColQueryBnfSql1";
    	var mySql1=new SqlClass();
    	mySql1.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
    	mySql1.addSubPara(document.getElementsByName("ClmNo")[0].value);//ָ������Ĳ���
    	strSQL1=mySql1.getString();
//    	strSQL="select (case when max(bnfno/1) is not null then max(bnfno/1)  else 0 end) from llbnfgather a where 1=1 "
// 		   + " and bnfkind!='B'"
//           + getWherePart( 'ClmNo','ClmNo' )
//               + " order by ClmNo";
                
        //prompt("�������̲�ѯ���������",strSQL);
        
        arr = easyExecSql( strSQL1 );
    }
    else if (fm.BnfKind.value == 'B')
    {
    	var strSQL2 = "";
    	var sqlid2="LLColQueryBnfSql2";
    	var mySql2=new SqlClass();
    	mySql2.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
    	mySql2.addSubPara(document.getElementsByName("ClmNo")[0].value);//ָ������Ĳ���
    	strSQL2=mySql2.getString();
//        strSQL = "select (case when max(bnfno/1) is not null then max(bnfno/1)  else 0 end) from llbnfgather a where 1=1 "
//     	   + " and bnfkind='B'"
//           + getWherePart( 'ClmNo','ClmNo' );
        
        //prompt("Ԥ��������˽׶������˷����ʼ�����",strSQL);
        
        arr = easyExecSql( strSQL2 );
        
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
    	var strSQL3 = "";
    	var sqlid3="LLColQueryBnfSql3";
    	var mySql3=new SqlClass();
    	mySql3.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
    	mySql3.addSubPara(fm.ClmNo.value);//ָ������Ĳ���
    	mySql3.addSubPara(fm.ClmNo.value);
    	mySql3.addSubPara(document.all('insuredno').value);
    	mySql3.addSubPara(document.all('insuredno').value);
    	mySql3.addSubPara(document.all('insuredno').value);
    	strSQL3=mySql3.getString();        
//        strSQL = "select ClmNo,polno,feeoperationtype,(select distinct baltypedesc from llbalancerela where baltype=feeoperationtype),"
//        	   +" sum(pay)+(select (case when sum(pay) is not null then sum(pay)  else 0 end) from llbalance where clmno='"+fm.ClmNo.value+"' and feeoperationtype='B' and grpcontno=a.grpcontno and grppolno=a.grppolno and contno=a.contno and polno=a.polno),GrpContNo,GrpPolNo,ContNo,'0',(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'A' and a.feeoperationtype=b.feeoperationtype) when 0 then 0 else 1 end),(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'A' and a.feeoperationtype=b.feeoperationtype) when 0 then 'δ����' else '�ѷ���' end) from LLBalance a where 1=1 "
//               + getWherePart( 'ClmNo','ClmNo' )
//               + " and exists(select 1 from lcpol b where a.polno=b.polno and b.insuredno='"+document.all('insuredno').value+"'  union  select 1 from lcinsuredrelated b where a.polno=b.polno and b.customerno='"+document.all('insuredno').value+"'  union  select 1  from lcpol b,lmrisksort c where a.contno = b.contno  and b.appntno ='"+document.all('insuredno').value+"' and b.riskcode=c.riskcode and c.risksorttype='18')"
//               + " and feeoperationtype!='B'"
//               + " group by ClmNo,polno,feeoperationtype,GrpContNo,GrpPolNo,ContNo "
//               + " order by ClmNo";
                
        //prompt("����������˽׶������˷����ʼ����ѯ",strSQL);
        
        arr = easyExecSql( strSQL3 );
    }
    else if (fm.BnfKind.value == 'B')
    {
    	var strSQL4 = "";
    	var sqlid4="LLColQueryBnfSql4";
    	var mySql4=new SqlClass();
    	mySql4.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
    	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
    	mySql4.addSubPara(document.getElementsByName("ClmNo")[0].value);//ָ������Ĳ���
    	mySql4.addSubPara(document.all('insuredno').value);
    	mySql4.addSubPara(document.all('insuredno').value);
    	mySql4.addSubPara(document.all('insuredno').value);
    	strSQL4=mySql4.getString();         
//        strSQL = "select ClmNo,polno,feeoperationtype,(select distinct baltypedesc from llbalancerela where baltype=feeoperationtype),abs(sum(pay)),GrpContNo,GrpPolNo,ContNo,'0',(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'B' and a.feeoperationtype=b.feeoperationtype) when 0 then 0 else 1 end),(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'B' and a.feeoperationtype=b.feeoperationtype) when 0 then 'δ����' else '�ѷ���' end) from LLBalance a where 1=1 "
//            + getWherePart( 'ClmNo','ClmNo' )
//            + " and exists(select 1 from lcpol b where a.polno=b.polno and b.insuredno='"+document.all('insuredno').value+"'  union  select 1 from lcinsuredrelated b where a.polno=b.polno and b.customerno='"+document.all('insuredno').value+"' union  select 1  from lcpol b,lmrisksort c where a.contno = b.contno  and b.appntno ='"+document.all('insuredno').value+"' and b.riskcode=c.riskcode and c.risksorttype='18')"
//            + " and FeeOperationType = 'B'"
//            + " group by ClmNo,polno,feeoperationtype,GrpContNo,GrpPolNo,ContNo "
//            + " order by ClmNo";
        
//        prompt("Ԥ��������˽׶������˷����ʼ����ѯ",strSQL);
        
        arr = easyExecSql( strSQL4 );
        
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
//    var tQusql = "select GetMode from llregister a where 1=1 "
//               + getWherePart( 'rgtno','ClmNo' );
    var strSQL5 = "";
	var sqlid5="LLColQueryBnfSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(document.getElementsByName("ClmNo")[0].value);//ָ������Ĳ���
	strSQL=mySql5.getString();
    var tFlag = easyExecSql( strSQL );
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
//    var strSQL1 = "select clmno,polNo,insuredno,bnfno,customerNo,name,payeeNo,payeename,bnftype,bnfgrade,relationtoinsured,sex,birthday,idtype,idno,relationtopayee,payeesex,payeebirthday,payeeidtype,payeeidno,getmoney,bnflot,CasePayMode,BankCode,BankAccNo,AccName from LLBnf where "
//                + " clmno = '" + LLBalanceGrid.getRowColData(i,1) + "'"
//                + " and PolNo = '" + LLBalanceGrid.getRowColData(i,2) + "'"
//                + " and BnfKind = '" + fm.BnfKind.value + "'"
//                + " and insuredno = '"+ fm.insuredno.value+"'"
//                + " order by clmno";
//    prompt("��ѯ�����˷�����ϸsql",strSQL1);
    var strSQL6 = "";
  	var sqlid6="LLColQueryBnfSql6";
  	var mySql6=new SqlClass();
  	mySql6.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
  	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
  	mySql6.addSubPara(LLBalanceGrid.getRowColData(i,1));//ָ������Ĳ���
  	mySql6.addSubPara(LLBalanceGrid.getRowColData(i,2));  
  	mySql6.addSubPara(fm.BnfKind.value);
  	mySql6.addSubPara(fm.insuredno.value);
  	strSQL6=mySql6.getString();
    var arr1 = easyExecSql( strSQL6 );
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
    emptyInput();
    var tInsuredNo = fm.insuredno.value;
	//---------------------------------------------------------------------------------------------beg
	//����ⰸ������ϸ��ϸ��Ϣ
    //---------------------------------------------------------------------------------------------
    var i = LLBalanceGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo2.value = LLBalanceGrid.getRowColData(i,1);
        fm.polNo.value = LLBalanceGrid.getRowColData(i,2);
        fm.sumPay.value = LLBalanceGrid.getRowColData(i,5);
        fm.BatNo.value = LLBalanceGrid.getRowColData(i,9);
        fm.FeeOperationType.value = LLBalanceGrid.getRowColData(i,3)
        
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
        
        
        if (LLBalanceGrid.getRowColData(i,6) == "" || LLBalanceGrid.getRowColData(i,6) == null)
        {
            fm.GrpContNo.value = '00000000000000000000';
        }
        else
        {
            fm.GrpContNo.value = LLBalanceGrid.getRowColData(i,6);
        }
        
        if (LLBalanceGrid.getRowColData(i,7) == "" || LLBalanceGrid.getRowColData(i,7) == null)
        {
            fm.GrpPolNo.value = '00000000000000000000';
        }
        else
        {
            fm.GrpPolNo.value = LLBalanceGrid.getRowColData(i,7);
        }
        
        if (LLBalanceGrid.getRowColData(i,8) == "" || LLBalanceGrid.getRowColData(i,8) == null)
        {
            fm.ContNo.value = '00000000000000000000';
        }
        else
        {
            fm.ContNo.value = LLBalanceGrid.getRowColData(i,8);
        }
        
        if (LLBalanceGrid.getRowColData(i,2) == "" || LLBalanceGrid.getRowColData(i,2) == null)
        {
            fm.PolNo.value = '00000000000000000000';
        }
        else
        {
            fm.PolNo.value = LLBalanceGrid.getRowColData(i,2);
        }
        
        
    }
    //---------------------------------------------------------------------------------------------end

	//---------------------------------------------------------------------------------------------beg
	//��ѯ�����������˻���Ϣ,���mulline
    //---------------------------------------------------------------------------------------------
    document.all('divLLBalance').style.display="";
    initLLBnfGrid();
    //---------------------1-----2--------3-------4--------5------6------7-------8--------9-------10---------11-----------12---13--------14----15---------16----------17----------18----------19---------20--------21------22-------23
//    var strSQL1 = "select clmno,polNo,insuredno,bnfno,customerNo,name,payeeNo,payeename,bnftype,bnfgrade,relationtoinsured,sex,birthday,idtype,idno,relationtopayee,payeesex,payeebirthday,payeeidtype,payeeidno,getmoney,bnflot,CasePayMode,BankCode,BankAccNo,AccName,CasePayMode,FeeOperationType,OBankCode from LLBnf where "
//                + " clmno = '" + LLBalanceGrid.getRowColData(i,1) + "'"
//                + " and PolNo = '" + LLBalanceGrid.getRowColData(i,2) + "'"
//                + " and BnfKind = '" + fm.BnfKind.value + "'"
//                + " and FeeOperationType='"+fm.FeeOperationType.value+"'"
//                + " and insuredno='"+fm.insuredno.value+"'"
//                + " order by clmno";
    var strSQL7 = "";
  	var sqlid7="LLColQueryBnfSql7";
  	var mySql7=new SqlClass();
  	mySql7.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
  	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
  	mySql7.addSubPara(LLBalanceGrid.getRowColData(i,1));//ָ������Ĳ���
  	mySql7.addSubPara(LLBalanceGrid.getRowColData(i,2));
  	mySql7.addSubPara(fm.BnfKind.value);
  	mySql7.addSubPara(fm.FeeOperationType.value);
  	mySql7.addSubPara(fm.insuredno.value);
	strSQL7=mySql7.getString();
//    prompt("��ѯ�����������˻���Ϣ",strSQL1);
    var arr1 = easyExecSql( strSQL7 );
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
    if (LLBalanceGrid.getRowColData(i,10) == '0')
    {
        //---------------------------------------------------------------------------------------------BEG
        //�������స��������Ĭ��Ϊ�����˱���,�����స��Ĭ��Ϊ������   2005-8-20 14:49 ����
        //---------------------------------------------------------------------------------------------
        
        //�����ж��ǲ�����������
//        var tSql = "select substr(d.reasoncode,2,2) from llappclaimreason d where "
//                 + " d.caseno = '" + fm.ClmNo.value + "' and customerno='"+fm.insuredno.value+"'";
        var strSQL = "";
      	var sqlid8="LLColQueryBnfSql8";
      	var mySql8=new SqlClass();
      	mySql8.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
      	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
      	mySql8.addSubPara(fm.ClmNo.value);//ָ������Ĳ���
      	mySql8.addSubPara(fm.insuredno.value);
    	strSQL8=mySql8.getString();
        var tRcode = easyExecSql( strSQL8 );
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
    	    var strSQL9 = "";
         	var sqlid9="LLColQueryBnfSql9";
         	var mySql9=new SqlClass();
         	mySql9.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
         	mySql9.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
         	mySql9.addSubPara(LLBalanceGrid.getRowColData(i,1));//ָ������Ĳ���
         	mySql9.addSubPara(fm.insuredno.value);
         	strSQL9=mySql9.getString();
//            var strSQL3 = "select customerno from llcase where caseno = '" + LLBalanceGrid.getRowColData(i,1) + "' and customerno='"+fm.insuredno.value+"'";
            var tCustNo = easyExecSql( strSQL9 );
            
//            var strSQL4 = "select a.name,a.sex,a.birthday,a.idtype,a.idno,(select b.bankcode from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y'),(select b.bankaccno from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y'),(select b.accname from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y') from ldperson a where 1=1"
//                        + " and a.customerno = '" + tInsuredNo + "'";
            var strSQL10 = "";
         	var sqlid10="LLColQueryBnfSql10";
         	var mySql10=new SqlClass();
         	mySql10.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
         	mySql10.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
         	mySql10.addSubPara(tInsuredNo);//ָ������Ĳ���
         	strSQL10=mySql10.getString();
            tPerson = easyExecSql( strSQL10 );
           
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
//            var strSQL2 = "select '"+LLBalanceGrid.getRowColData(i,1)+"',polNo,insuredno,bnfno,customerNo,name,customerNo,name,bnftype,bnfgrade,relationtoinsured,sex,birthday,idtype,idno,relationtoinsured,sex,birthday,idtype,idno,bnflot*" + parseFloat(fm.sumPay.value) + ",bnflot*100,'',(select b.bankcode from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y'),(select b.bankaccno from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y'),(select b.accname from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y') from LCBnf a where "
//                        + " PolNo = '" + LLBalanceGrid.getRowColData(i,2) + "'"
//                        + " order by BnfNo";
            sql_id = "LLColQueryBnfSql11";
            my_sql = new SqlClass();
            my_sql.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
            my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
            my_sql.addSubPara(LLBalanceGrid.getRowColData(i,1));
            my_sql.addSubPara(parseFloat(fm.sumPay.value));//ָ������Ĳ���
            my_sql.addSubPara(LLBalanceGrid.getRowColData(i,2));//ָ������Ĳ���
            str_sql = my_sql.getString();
//            prompt("����స����ѯ�б�ʱȷ����������",strSQL2);
            var arr2 = easyExecSql( str_sql ); //�ȵ��б����в���
            if (arr2)
            {
                displayMultiline(arr2,LLBnfGrid);
            }
            else  //�б�û������,Ĭ��Ϊ������
            {
//                var strSQL4 = "select a.RgtantName,a.RgtantSex,'',a.IDType,a.IDNo,'','','',Relation from LLRegister a where 1=1"
//                            + " and a.RgtNo = '" +  + "'";
//                prompt("�б�û������,Ĭ��Ϊ������",strSQL4);
                sql_id = "LLColQueryBnfSql12";
                my_sql = new SqlClass();
                my_sql.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
                my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
                my_sql.addSubPara(fm.ClmNo.value);//ָ������Ĳ���
                str_sql = my_sql.getString();
                tPerson = easyExecSql( str_sql );
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
            fm.getmoney.value = LLBalanceGrid.getRowColData(i,5);
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
    //���������ѷ���״̬��Ϊת�����ȡ
    if (LLBalanceGrid.getRowColData(i,10) != '0' && fm.ClmFlag.value == "2")
    {
        document.all("divButtonBnf").style.display = "none";
        document.all("divButtonToPension").style.display = "";
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
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo.value = LLBnfGrid.getRowColData(i,1);//
        fm.polNo.value = LLBnfGrid.getRowColData(i,2);//
        fm.insuredno.value = LLBnfGrid.getRowColData(i,3);//
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
        fm.getmoney.value = LLBnfGrid.getRowColData(i,21);//
        fm.bnflot.value = LLBnfGrid.getRowColData(i,22);//
        fm.CasePayMode.value = LLBnfGrid.getRowColData(i,23);//
        fm.BankCode.value = LLBnfGrid.getRowColData(i,24);//
        fm.BankAccNo.value = LLBnfGrid.getRowColData(i,25);//
        fm.AccName.value = LLBnfGrid.getRowColData(i,26);//
        fm.oriBnfNo.value = LLBnfGrid.getRowColData(i,29);//
        
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
            sql_id = "LLColQueryBnfSql13";
            my_sql = new SqlClass();
            my_sql.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
            my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
            my_sql.addSubPara(document.getElementsByName("ClmNo")[0].value);//ָ������Ĳ���
            my_sql.addSubPara(document.getElementsByName("bnfno")[0].value);//ָ������Ĳ���
            my_sql.addSubPara(document.getElementsByName("polNo")[0].value);//ָ������Ĳ���
            str_sql = my_sql.getString();
            var tFlag = easyExecSql( str_sql );
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
    //���ÿɲ�����ť
    fm.updateButton.disabled = false;
    
}

//����������
function addClick()
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
        tAll = tAll + parseFloat(LLBnfGrid.getRowColData(j,22));
        //alert("LLBnfGrid.getRowColData("+j+",30):"+LLBnfGrid.getRowColData(j,30));
        temp = temp + parseFloat(LLBnfGrid.getRowColData(j,30));
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
        
        //--------------------------------------------------------------------------------BEG
        //����������,���Ϊ��������һ�ˣ�������ʣ���������
        //--------------------------------------------------------------------------------
        if(fm.RemainLot.value == '0')
        {
            var tAllMoney = 0;
            var tAllLot=0;
            for (var l=0; l < i; l++)
            {
                tAllMoney = tAllMoney + parseFloat(LLBnfGrid.getRowColData(l,21));
                tAllLot = tAllLot+parseFloat(LLBnfGrid.getRowColData(l,22));
            }
            
        	//alert("tAllMoney:"+tAllMoney);
        	//alert("tAllLot:"+tAllLot);
        	//alert("fm.sumPay.value:"+fm.sumPay.value);
        	///alert("temp:"+temp);

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
        
//	    var strSQL = " select GetMoney,BnfNo,CasePayMode,BankCode,BankAccNo,AccName,RelationToInsured from LLBnfGather"
// 	         		 + " where clmno ='"+document.all('ClmNo').value+"'"
// 	         		 + " and Insuredno='"+document.all('insuredno').value+"'"
// 	         		 + " and BnfKind='"+fm.BnfKind.value+"'"
// 	         		 + " and Name='"+document.all('cName').value+"'"
// 	         		 + " and sex='"+document.all('sex').value+"'"
// 	         		 + " and Birthday='"+document.all('birthday').value+"'"
// 	         		 + " and idtype='"+document.all('idtype').value+"'"
// 	         		 + " and idno='"+document.all('idno').value+"'"
   
	    //prompt("У���Ƿ���ͬһ����������Ϣ",strSQL);
	    sql_id = "LLColQueryBnfSql14";
        my_sql = new SqlClass();
        my_sql.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
        my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
        my_sql.addSubPara(document.all('ClmNo').value);//ָ������Ĳ���
        my_sql.addSubPara(document.all('insuredno').value);//ָ������Ĳ���
        my_sql.addSubPara(fm.BnfKind.value);//ָ������Ĳ���
        my_sql.addSubPara(document.all('cName').value);
        my_sql.addSubPara(document.all('sex').value);
        my_sql.addSubPara(document.all('birthday').value);
        my_sql.addSubPara(document.all('idtype').value);
        my_sql.addSubPara(document.all('idno').value);
        str_sql = my_sql.getString();
	    var strQueryResult = easyExecSql(str_sql);
	    
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
	            		     
	            		 if(fm.CasePayMode.value != LLBnfGrid.getRowColData(i,23))
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

	              			
	                		if(fm.BankCode.value != LLBnfGrid.getRowColData(i,24))
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
	              	    	
	                	    if(fm.BankAccNo.value != LLBnfGrid.getRowColData(i,25))
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
	              	    	
	                  	    if(fm.AccName.value != LLBnfGrid.getRowColData(i,26))
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
	              		 mergeMoney=parseFloat(LLBnfGrid.getRowColData(i,21))+parseFloat(document.all('getmoney').value);
	        	    	 
	        	    	 if(mergeMoney<0)
	        	    	 {
	        	    	      alert("�������������ܽ���Ϊ��!");
	        	    	      return;
	        	    	 }
	        	    	 

	        	    	 document.all('getmoney').value=(parseFloat(document.all('getmoney').value) + parseFloat(LLBnfGrid.getRowColData(i,21))).toFixed(2);
	        	    	 document.all('bnflot').value=eval((document.all('getmoney').value / fm.sumPay.value).toFixed(2)*100);


	        		     LLBnfGrid.setRowColData(i,21,fm.getmoney.value);//�����˷�����
	        		     LLBnfGrid.setRowColData(i,22,fm.bnflot.value);//�����˷������
	        		     
	        		     
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
	    	        LLBnfGrid.setRowColData(i,21,fm.getmoney.value);//
	    	        LLBnfGrid.setRowColData(i,22,fm.bnflot.value);//
	    	        LLBnfGrid.setRowColData(i,23,fm.CasePayMode.value);//
	    	        LLBnfGrid.setRowColData(i,24,fm.BankCode.value);//
	    	        LLBnfGrid.setRowColData(i,25,fm.BankAccNo.value);//
	    	        LLBnfGrid.setRowColData(i,26,fm.AccName.value);//
	    	        LLBnfGrid.setRowColData(i,27,fm.FeeOperationType.value);//
	    	        LLBnfGrid.setRowColData(i,28,fm.CasePayModeName.value);//
	    	        
	                fm.oriBnfNo.value = LLBnfGrid.getRowColData(i,4);//�������������,Ϊ���޸���������ϢʱУ����
	    	        LLBnfGrid.setRowColData(i,29,fm.oriBnfNo.value);//
		 	        LLBnfGrid.setRowColData(rowNum,30,document.all('oriBnfPay').value);
		            //alert("LLBnfGrid.getRowColData("+rowNum+",30):"+LLBnfGrid.getRowColData(rowNum,30));
	            }
	        }
	        else
	        {
		    	document.all('oriBnfPay').value=parseFloat(document.all('getmoney').value);    
		    	
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
    	        LLBnfGrid.setRowColData(i,21,fm.getmoney.value);//
    	        LLBnfGrid.setRowColData(i,22,fm.bnflot.value);//
    	        LLBnfGrid.setRowColData(i,23,fm.CasePayMode.value);//
    	        LLBnfGrid.setRowColData(i,24,fm.BankCode.value);//
    	        LLBnfGrid.setRowColData(i,25,fm.BankAccNo.value);//
    	        LLBnfGrid.setRowColData(i,26,fm.AccName.value);//
    	        LLBnfGrid.setRowColData(i,27,fm.FeeOperationType.value);//
    	        LLBnfGrid.setRowColData(i,28,fm.CasePayModeName.value);//
    	        
                fm.oriBnfNo.value = LLBnfGrid.getRowColData(i,4);//�������������,Ϊ���޸���������ϢʱУ����
    	        LLBnfGrid.setRowColData(i,29,fm.oriBnfNo.value);//
	 	        LLBnfGrid.setRowColData(rowNum,30,document.all('oriBnfPay').value);
	            //alert("LLBnfGrid.getRowColData("+rowNum+",30):"+LLBnfGrid.getRowColData(rowNum,30));
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
		            		     
		            		 if(fm.CasePayMode.value != LLBnfGrid.getRowColData(i,23))
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

		              			
		                		if(fm.BankCode.value != LLBnfGrid.getRowColData(i,24))
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
		              	    	
		                	    if(fm.BankAccNo.value != LLBnfGrid.getRowColData(i,25))
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
		              	    	
		                  	    if(fm.AccName.value != LLBnfGrid.getRowColData(i,26))
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
	 	         LLBnfGrid.setRowColData(rowNum,23,fm.CasePayMode.value);//
	 	         LLBnfGrid.setRowColData(rowNum,24,fm.BankCode.value);//
	 	         LLBnfGrid.setRowColData(rowNum,25,fm.BankAccNo.value);//
	 	         LLBnfGrid.setRowColData(rowNum,26,fm.AccName.value);//
	 	         LLBnfGrid.setRowColData(rowNum,27,fm.FeeOperationType.value);//
	 	         LLBnfGrid.setRowColData(rowNum,28,fm.CasePayModeName.value);//
	 	         
			     LLBnfGrid.setRowColData(rowNum,4,strQueryResult[0][1]);//���������
			     LLBnfGrid.setRowColData(rowNum,21,fm.getmoney.value);//�����˷�����
			     LLBnfGrid.setRowColData(rowNum,22,fm.bnflot.value);//�����˷������
			     
	             fm.oriBnfNo.value = LLBnfGrid.getRowColData(rowNum,4);//�������������,Ϊ���޸���������ϢʱУ����
	 	         LLBnfGrid.setRowColData(rowNum,29,fm.oriBnfNo.value);//

	 	         LLBnfGrid.setRowColData(rowNum,30,document.all('oriBnfPay').value);
	             //alert("LLBnfGrid.getRowColData("+rowNum+",30):"+LLBnfGrid.getRowColData(rowNum,30));
		     }
	    }        
    }
}

//�޸�������
function updateClick()
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
            tAll = tAll + parseFloat(LLBnfGrid.getRowColData(j,22));
            //alert("LLBnfGrid.getRowColData("+j+",30):"+LLBnfGrid.getRowColData(j,30));
            temp = temp + parseFloat(LLBnfGrid.getRowColData(j,30));
        }
    }
    
    if (fm.bnflot.value == "" || fm.bnflot.value == null)
    {
        fm.bnflot.value = 0;
    }
    
    var tAllLot=tAll;//���ݳ����м�¼�����б����¼�ı���
    
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
        //--------------------------------------------------------------------------------BEG
        //����������,���Ϊ��������һ�ˣ�������ʣ���������
        //--------------------------------------------------------------------------------

        if(fm.RemainLot.value == '0')
        {
            var tAllMoney = 0;

            for (var l=0; l < i; l++)
            {
                tAllMoney = tAllMoney + parseFloat(LLBnfGrid.getRowColData(l,21));
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
        
//	    var strSQL = " select GetMoney,BnfNo,CasePayMode,BankCode,BankAccNo,AccName,RelationToInsured,OBankCode from LLBnfGather"
// 	         		 + " where clmno ='"+document.all('ClmNo').value+"'"
// 	         		 + " and Insuredno='"+document.all('insuredno').value+"'"
// 	         		 + " and BnfKind='"+fm.BnfKind.value+"'"
// 	         		 + " and Name='"+document.all('cName').value+"'"
// 	         		 + " and sex='"+document.all('sex').value+"'"
// 	         		 + " and Birthday='"+document.all('birthday').value+"'"
// 	         		 + " and idtype='"+document.all('idtype').value+"'"
// 	         		 + " and idno='"+document.all('idno').value+"'"
   
	    //prompt("У���Ƿ���ͬһ����������Ϣ",strSQL);
	    sql_id = "LLColQueryBnfSql15";
        my_sql = new SqlClass();
        my_sql.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
        my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
        my_sql.addSubPara(document.all('ClmNo').value);//ָ������Ĳ���
        my_sql.addSubPara(document.all('insuredno').value);//ָ������Ĳ���
        my_sql.addSubPara(fm.BnfKind.value);//ָ������Ĳ���
        my_sql.addSubPara(document.all('cName').value);
        my_sql.addSubPara(document.all('sex').value);
        my_sql.addSubPara(document.all('birthday').value);
        my_sql.addSubPara(document.all('idtype').value);
        my_sql.addSubPara(document.all('idno').value);
        str_sql = my_sql.getString();
	    var strQueryResult = easyExecSql(str_sql);
	    
	    var tFlag=false;//�ϲ���־
	    var mergeMoney=0.0;//�ϲ����
	    
	    if(strQueryResult==null || strQueryResult=="")
	    {
	    	


	         //���·���������˽��	    	 
	    	 document.all('getmoney').value=(parseFloat(document.all('getmoney').value) ).toFixed(2);    	 
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
    		        LLBnfGrid.setRowColData(k,29,LLBnfGrid.getRowColData(k,4));//
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
    	        LLBnfGrid.setRowColData(k,21,fm.getmoney.value);//
    	        LLBnfGrid.setRowColData(k,22,fm.bnflot.value);//
    	        LLBnfGrid.setRowColData(k,23,fm.CasePayMode.value);//
    	        LLBnfGrid.setRowColData(k,24,fm.BankCode.value);//
    	        LLBnfGrid.setRowColData(k,25,fm.BankAccNo.value);//
    	        LLBnfGrid.setRowColData(k,26,fm.AccName.value);//
    	        LLBnfGrid.setRowColData(k,27,fm.FeeOperationType.value);//
    	        LLBnfGrid.setRowColData(k,28,fm.CasePayModeName.value);//
	 	        LLBnfGrid.setRowColData(k,30,document.all('oriBnfPay').value);
	            //alert("LLBnfGrid.getRowColData("+rowNum+",30):"+LLBnfGrid.getRowColData(rowNum,30));


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
		     document.all('oriBnfPay').value=parseFloat(document.all('getmoney').value);    
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
 	         LLBnfGrid.setRowColData(k,21,fm.getmoney.value);//
 	         LLBnfGrid.setRowColData(k,22,fm.bnflot.value);//
 	         LLBnfGrid.setRowColData(k,23,fm.CasePayMode.value);//
 	         LLBnfGrid.setRowColData(k,24,fm.BankCode.value);//
 	         LLBnfGrid.setRowColData(k,25,fm.BankAccNo.value);//
 	         LLBnfGrid.setRowColData(k,26,fm.AccName.value);//
 	         LLBnfGrid.setRowColData(k,27,fm.FeeOperationType.value);//
 	         LLBnfGrid.setRowColData(k,28,fm.CasePayModeName.value);//
 	         	         
 	         //��������ֶ�Ϊ��,��������ǵ�һ�������˼�¼
 	         fm.oriBnfNo.value = strQueryResult[0][6];//�������������

	 	     LLBnfGrid.setRowColData(k,30,document.all('oriBnfPay').value);
	         //alert("LLBnfGrid.getRowColData("+k+",30):"+LLBnfGrid.getRowColData(k,30));

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
//    var strSQL = " select CasePayFlag from LLBnf where "
//               + " ClmNo = '" + LLBnfGrid.getRowColData(k,1) + "'"
//               + " and CaseNo = '" + LLBnfGrid.getRowColData(k,1) + "'"
//               + " and BatNo = '" + fm.BatNo.value + "'"
//               + " and BnfKind = '" + fm.BnfKind.value + "'"
//               + " and PolNo = '" + LLBnfGrid.getRowColData(k,2) + "'"
//               + " and InsuredNo = '" + LLBnfGrid.getRowColData(k,3) + "'"
//               + " and BnfNo = '" + LLBnfGrid.getRowColData(k,4) + "'"
//               + " order by BnfNo";
//    alert(strSQL);
    sql_id = "LLColQueryBnfSql16";
    my_sql = new SqlClass();
    my_sql.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
    my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
    my_sql.addSubPara(LLBnfGrid.getRowColData(k,1));//ָ������Ĳ���
    my_sql.addSubPara(LLBnfGrid.getRowColData(k,1));//ָ������Ĳ���
    my_sql.addSubPara(fm.BatNo.value);
    my_sql.addSubPara(fm.BnfKind.value);
    my_sql.addSubPara(LLBnfGrid.getRowColData(k,2));
    my_sql.addSubPara(LLBnfGrid.getRowColData(k,3));
    my_sql.addSubPara(LLBnfGrid.getRowColData(k,4));
    str_sql = my_sql.getString();
    var arr = easyExecSql( str_sql );
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
	 
//	 var strSql = "select (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and b.FeeOperationType = '"+fm.FeeOperationType.value+"') when 0 then 0 else 1 end) from LLBalance a where 1=1 "
//         + getWherePart( 'ClmNo','ClmNo' )
//         + " and grpcontno='"+fm.GrpContNo.value+"'"
//         + " and grppolno='"+fm.GrpPolNo.value+"'"
//         + " and contno='"+fm.ContNo.value+"'"
//         + " and polno='"+fm.PolNo.value+"'"
//         + " and Feeoperationtype='"+fm.FeeOperationType.value+"'";
	 
	 //prompt("У��ͬһ��Ŀ�Ƿ񷴸�����",strSql);
	 sql_id = "LLColQueryBnfSql17";
	    my_sql = new SqlClass();
	    my_sql.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
	    my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
	    my_sql.addSubPara(fm.FeeOperationType.value);
	    my_sql.addSubPara(document.getElementsByName("ClmNo")[0].value);//ָ������Ĳ���
	    my_sql.addSubPara(fm.GrpContNo.value);//ָ������Ĳ���
	    my_sql.addSubPara(fm.GrpPolNo.value);
	    my_sql.addSubPara(fm.ContNo.value);
	    my_sql.addSubPara(fm.PolNo.value);
	    my_sql.addSubPara(fm.FeeOperationType.value);
	    str_sql = my_sql.getString();
	 var tBNF = easyExecSql(str_sql);

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
        		LLBnfGrid.getRowColData(j,22) > 100)
        {
            j++;
            alert("������"+j+"�������������!");
            return;
        }
        if (LLBnfGrid.getRowColData(j,23) == null || LLBnfGrid.getRowColData(j,23) == "")
        {
            j++;
            alert("������"+j+"��֧����ʽ����Ϊ��!");
            return;
        }
        tAll = tAll + parseFloat(LLBnfGrid.getRowColData(j,22));
    }
    
    //�������100%У��
    if(tAll != 100)
    {
        alert("δ��ȫ����������!");
        return;
    }
    
    fm.action = './LLBnfSave.jsp';
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
    fm.submit(); //�ύ
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
    fm.submit(); //�ύ
    tSaveFlag ="0";
}

//�ύ�����,����
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    queryBnfBatNo();
    
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
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
		var iHeight=250;     //�������ڵĸ߶�; 
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
            document.all("divButtonToPension").style.display = "";
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
       // showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");\var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
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
    
    fm.PensionType.value = "";
    fm.PensionTypeName.value = "";
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
//    			      var strSQL = " select name,sex,(select codename from ldcode where codetype='sex' and code=sex),birthday,"
//    			    	         + " idtype,(select codename from ldcode where codetype='idtype' and code=idtype ),idno "
//    			    	         + " from ldperson where customerno ='"+document.all('insuredno').value+"'";
//    			      
//    			      prompt("���������˺ͳ����˹�ϵ��ѯ����������Ϣ",strSQL);
    			      sql_id = "LLColQueryBnfSql18";
    				    my_sql = new SqlClass();
    				    my_sql.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
    				    my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
    				    my_sql.addSubPara(document.all('insuredno').value);//ָ������Ĳ���
    				    str_sql = my_sql.getString();
    			      var strQueryResult = easyExecSql(str_sql);
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
    			  
    			  if( document.all('insuredno').value != null  && document.all('insuredno').value != "")
    			  {
//    			      var strSQL = " select name,sex,(select codename from ldcode where codetype='sex' and code=sex),birthday,"
//    			    	         + " idtype,(select codename from ldcode where codetype='idtype' and code=idtype ),idno "
//    			    	         + " from ldperson where customerno ='"+document.all('insuredno').value+"'";
    			      
    			      //prompt("���������˺ͳ����˹�ϵ��ѯ����������Ϣ",strSQL);
    			    sql_id = "LLColQueryBnfSql19";
  				    my_sql = new SqlClass();
  				    my_sql.setResourceName("claim.LLColQueryBnfSql"); //ָ��ʹ�õ�properties�ļ���
  				    my_sql.setSqlId(sql_id);//ָ��ʹ�õ�Sql��id
  				    my_sql.addSubPara(document.all('insuredno').value+);//ָ������Ĳ���
  				    str_sql = my_sql.getString();
    			      var strQueryResult = easyExecSql(str_sql);
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
    		      }
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