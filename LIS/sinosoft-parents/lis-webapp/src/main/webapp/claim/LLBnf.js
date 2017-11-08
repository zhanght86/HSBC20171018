var showInfo;
var mDebug = "1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mBnfBatNo=0;
var tDeadFlag = 0;
var mySql = new SqlClass();

//查询数据库中受益人序号
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
        //prompt("正常流程查询受益人序号",strSQL);
        
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
        //prompt("预付流程审核阶段受益人分配初始化金额",strSQL);
        
        arr = easyExecSql( mySql.getString() );
        
    }
    
    if (arr)
    {
        mBnfBatNo=arr[0][0];
    }
//    alert("insuredno="+fm.insuredno.value);
    //alert("mBnfBatNo*****:"+mBnfBatNo);

}

//查询赔案保单名细
function queryLLBalanceGrid()
{
    var strSQL = '';
    var arr = '';
    if (fm.BnfKind.value == 'A')
    {
//        strSQL = "select ClmNo,polno,sum(pay),GrpContNo,GrpPolNo,ContNo,'0',(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'A') when 0 then 0 else 1 end),(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'A') when 0 then '未分配' else '已分配' end) from LLBalance a where 1=1 "
//                   + getWherePart( 'ClmNo','ClmNo' )
//                   + " and exists(select 1 from lcpol b where a.polno=b.polno and b.insuredno='"+document.all('insuredno').value+"')"
//                   + " group by ClmNo,polno,GrpContNo,GrpPolNo,ContNo "
//                   + " order by ClmNo";
        
       /* strSQL = "select ClmNo,polno,feeoperationtype,(select distinct baltypedesc from llbalancerela where baltype=feeoperationtype),"
        	   +" sum(pay)+(select nvl(sum(pay),0) from llbalance where clmno='"+fm.ClmNo.value+"' and feeoperationtype='B' and grpcontno=a.grpcontno and grppolno=a.grppolno and contno=a.contno and polno=a.polno and a.feeoperationtype='A') pay,"
        	   +" GrpContNo,GrpPolNo,ContNo,'0',"
        	   +" (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'A' and a.feeoperationtype=b.feeoperationtype) when 0 then 0 else 1 end),(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'A' and a.feeoperationtype=b.feeoperationtype) when 0 then '未分配' else '已分配' end)"
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
        //prompt("正常流程审核阶段受益人分配初始化查询",strSQL);
        
        arr = easyExecSql( mySql.getString() );
    }
    else if (fm.BnfKind.value == 'B')
    {
//        strSQL = "select ClmNo,polno,abs(sum(pay)),GrpContNo,GrpPolNo,ContNo,'0',(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'B') when 0 then 0 else 1 end),(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'B') when 0 then '未分配' else '已分配' end) from LLBalance a where 1=1 "
//               + getWherePart( 'ClmNo','ClmNo' )
//               + " and exists(select 1 from lcpol b where a.polno=b.polno and b.insuredno='"+document.all('insuredno').value+"')"
//               + " and FeeOperationType = 'B'"
//               + " group by ClmNo,polno,GrpContNo,GrpPolNo,ContNo " 
//               + " order by ClmNo";
        
        /*strSQL = "select ClmNo,polno,feeoperationtype,(select distinct baltypedesc from llbalancerela where baltype=feeoperationtype),"
        	+ " abs(sum(pay)) pay,GrpContNo,GrpPolNo,ContNo,'0',"
        	+ " (case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'B' and a.feeoperationtype=b.feeoperationtype) when 0 then 0 else 1 end),(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'B' and a.feeoperationtype=b.feeoperationtype) when 0 then '未分配' else '已分配' end)"
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
//        prompt("预付流程审核阶段受益人分配初始化查询",strSQL);
        
        arr = easyExecSql( mySql.getString() );
        
    }
    if (arr)
    {
        displayMultiline(arr,LLBalanceGrid);
    }
//    turnPage.queryModal(strSQL, LLBalanceGrid);

    /**-----------------------------------------------------------------------------------BEG
     * 初始化赔案领取标记(1一次统一给付 2 按年金方式领取 3 分期支付)
     * add 周磊 2006-3-3 9:23
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
        fm.ClmFlag.value = "2"; //按年金领取
    }
    //------------------------------------------------------------------------------------END
}

//查询受益人分配名细
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
//    prompt("查询受益人分配明细sql",strSQL1);
    var arr1 = easyExecSql(  mySql.getString()  );
    if (arr1)
    {
        displayMultiline(arr1,LLBnfGrid);
//        turnPage.queryModal(strSQL, LLBnfGrid);
    }
    //设置可操作按钮
    fm.addButton.disabled = false;
//    fm.updateButton.disabled = true;
    fm.deleteButton.disabled = false;
    fm.saveButton.disabled = false;
}

//选中LLBalanceGrid响应事件
function LLBalanceGridClick()
{
    //清空表单
	//alert( fm.insuredno.value);
    emptyInput();
    
	//---------------------------------------------------------------------------------------------beg
	//填充赔案保单名细详细信息
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
//        //判断受益分配金额
//        if (fm.BnfKind.value == 'A' && fm.sumPay.value < 0)
//        {
//            alert("此保单不能分配受益人!");
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
	//查询理赔受益人账户信息,组成mulline
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
//    prompt("查询理赔受益人账户信息",strSQL1);
    var arr1 = easyExecSql( mySql.getString() );
    if (arr1)
    {
        displayMultiline(arr1,LLBnfGrid);
//        turnPage.queryModal(strSQL, LLBnfGrid);
    }
    //---------------------------------------------------------------------------------------------end

    //设置可操作按钮
    fm.addButton.disabled = false;
//    fm.updateButton.disabled = true;
    fm.deleteButton.disabled = false;
    fm.saveButton.disabled = false;
    
    //保单处于未分配状态,按相关方式查询受益人
    if (LLBalanceGrid.getRowColData(i,11) == '0')
    {
        //---------------------------------------------------------------------------------------------BEG
        //非死亡类案件受益人默认为出险人本人,死亡类案件默认为申请人   2005-8-20 14:49 周磊
        //---------------------------------------------------------------------------------------------
        
        //首先判断是不是死亡案件
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
        if (tDeadFlag == 0)  //非死亡类案件
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
        else   //死亡类案件
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
//            prompt("身故类案件查询承保时确定的受益人",strSQL2);
            var arr2 = easyExecSql( mySql.getString() ); //先到承保表中查找
            if (arr2)
            {
                displayMultiline(arr2,LLBnfGrid);
            }
            else  //承保没有数据,默认为申请人
            {
               /* var strSQL4 = "select a.RgtantName,a.RgtantSex,'',a.IDType,a.IDNo,'','','',Relation from LLRegister a where 1=1"
                            + " and a.RgtNo = '" + fm.ClmNo.value + "'";*/
                mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql12");
		mySql.addSubPara(fm.ClmNo.value );  
		
//                prompt("承保没有数据,默认为申请人",strSQL4);
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
    
    //对于数据转换的银行编码为"default"数据进行清理
    //周磊 2006-1-12 11:18 应张领导要求
    if (fm.BankCode.value == "default")
    {
        fm.BankCode.value = "";
    }
    if (fm.BankAccNo.value == "default")
    {
        fm.BankAccNo.value = "";
    }
    
    /**-----------------------------------------------------------------------------------BEG
     * 转年金增加点击后判断是否分配来显示按钮和操作域
     * add 周磊 2006-3-3 9:23
     *-----------------------------------------------------------------------------------*/
//    alert(fm.ClmFlag.value);
    //保单处于已分配状态且为转年金领取
    if (LLBalanceGrid.getRowColData(i,11) != '0' && fm.ClmFlag.value == "2")
    {
        document.all("divButtonBnf").style.display = "none";
        document.all("divButtonToPension").style.display = "none";
    }
//    alert("insuredno="+fm.insuredno.value);
    //------------------------------------------------------------------------------------END
}

//调整受益人,点击adjustBnfButton响应事件
function toAdjustBnf()
{
//    if (!confirm("警告:调整受益人将删除转年金领取的分配。是否继续？"))
//    {
//        return;
//    }
    document.all("divButtonBnf").style.display = "";
    document.all("divButtonToPension").style.display = "none";
}

//选中LLBnfGrid响应事件
function LLBnfGridClick()
{
    //清空表单
    emptyInput();
	//-----------------------------------------------------
	//填充理赔受益人账户详细信息
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
         * 如果为转年金则查询对于受益人是否已经指定转年金类型
         * add 周磊 2006-3-3 9:23
         *-----------------------------------------------------------------------------------*/
        //保单处于已分配状态且为转年金领取
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
          	mySql1.setResourceName("claim.LLBnfInputSql"); //指定使用的properties文件名
          	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
            var tFlag = easyExecSql( mySql1 );
            if (tFlag)
            {
                fm.PensionType.value = tFlag;
                showOneCodeName('llclmtopension','PensionType','PensionTypeName');
            }
          
            else
            {
                //初始化时把转年金按钮置为不可用，直到确认它没做过转年金处理时才放开
                fm.clmToPension.disabled = false;
            }
        }
        //------------------------------------------------------------------------------------END

    }
    //alert("LLBnfGridClick begin:"+fm.insuredno.value);
    //设置可操作按钮
    fm.updateButton.disabled = false;
    
}

//增加受益人
function addClick()
{
    //1、必须选中保单
    if (LLBalanceGrid.getSelNo() < 1)
    {
        alert("请选择保单！");
        return;
    }
    //alert("1111111111:"+fm.insuredno.value);
    //2、非空检验
    if(!checkInput())
    {
        return;
    }
    
    //3、计算mulLinemulLine中合计受益金额、合计受益比例 
    var tAllMoney = 0;	//mulLine中合计受益金额
    var tAllLot = 0;	//mulLine中合计受益比例
    for (var j=0; j < LLBnfGrid.mulLineCount; j++)
    {
        tAllMoney = tAllMoney + parseFloat(LLBnfGrid.getRowColData(j,22));
        tAllLot = tAllLot + parseFloat(LLBnfGrid.getRowColData(j,23));
    }    
    
    //4、校验受益比例之和<=100，计算剩余百分比 
    if (fm.bnflot.value == "" || fm.bnflot.value == null)
    {
        fm.bnflot.value = 0;
    }    
    var tBnfLot = parseFloat(fm.bnflot.value);  
    if (tAllLot + tBnfLot > 100)	//校验受益比例之和<=100
    {
        alert("分配比例之和大于100，请调整!");
        return;
    }
	fm.RemainLot.value = Math.round((100 - tAllLot - tBnfLot)*100)/100;
    
    //5、计算受益金额
    if(tAllLot + tBnfLot == 100)	//如果为分配的最后一人，把所有剩余金额都赋给他
	{
		fm.getmoney.value = eval(parseFloat(fm.sumPay.value)-tAllMoney).toFixed(2)
	}
	else if(tAllLot + tBnfLot < 100)
	{
		fm.getmoney.value = eval((parseFloat(fm.sumPay.value)*tBnfLot*0.01).toFixed(2));
	}
    
	//6、校验已添加的受益人如果和要添加的是同一个人，返回错误提示
 	var rowNum = LLBnfGrid.mulLineCount;
	for(var i=0;i<rowNum;i++)
	{
		if((LLBnfGrid.getRowColData(i,3)==fm.insuredno.value)//根据被保险人序号
			&&(LLBnfGrid.getRowColData(i,6)==fm.cName.value)
			&&(LLBnfGrid.getRowColData(i,12)==fm.sex.value)
			&&(LLBnfGrid.getRowColData(i,13)==fm.birthday.value)
			&&(LLBnfGrid.getRowColData(i,14)==fm.idtype.value)
			&&(LLBnfGrid.getRowColData(i,15)==fm.idno.value)
			&&(LLBnfGrid.getRowColData(i,11)==fm.relationtoinsured.value))
		{
			alert("该受益人已经添加，不能重复添加!");
			return false;
		}
	}
   
    //7、校验已保存的受益人如果和要添加的是同一个人，支付方式等信息要一致，并计算待添加受益人的序号
	/*var strSQL = " select GetMoney,BnfNo,CasePayMode,BankCode,BankAccNo,AccName,RelationToInsured from LLBnf "
		+ " where clmno ='"+document.all('ClmNo').value+"'"
		+ " and Insuredno='"+document.all('insuredno').value+"'"
		+ " and BnfKind='"+fm.BnfKind.value+"'"
		+ " and Name='"+document.all('cName').value+"'"
		+ " and sex='"+document.all('sex').value+"'"
		+ " and Birthday='"+document.all('birthday').value+"'"
		+ " and idtype='"+document.all('idtype').value+"'"
		+ " and idno='"+document.all('idno').value+"'";	*///prompt("校验是否是同一个受益人信息",strSQL);	    
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
  
	if(strQueryResult==null || strQueryResult=="")//新的受益人
	{
		//var SQL_maxBnfNo = " select nvl(max(BnfNo),0) from LLBnf where clmno ='"+document.all('ClmNo').value+"'";
		mySql = new SqlClass();
		mySql.setResourceName("claim.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql14");
		mySql.addSubPara(document.all('ClmNo').value );  

		var maxBnfNo = parseInt(easyExecSql(mySql.getString()));//已保存的最大受益人序号
		for(var i=0;i<rowNum;i++)
		{
			if(parseInt(LLBnfGrid.getRowColData(i,4)) > maxBnfNo){//如果已添加的序号更大，则取之
				maxBnfNo = LLBnfGrid.getRowColData(i,4);
			}
		}
		fm.bnfno.value = parseInt(maxBnfNo) + 1;
		//校验总金额不能为负
		if(parseFloat(fm.getmoney.value) < 0){
			alert("分配后的受益人总金额不能为负!");
	    	return;
		}
	}
	else	//数据库中保存过此受益人	
	{
		fm.bnfno.value = strQueryResult[0][1];//取已保存的序号
	    if(fm.relationtoinsured.value != strQueryResult[0][6])//校验被保险人关系必须保持一致
    	{
    		alert("受益人:"+fm.cName.value+"的与被保险人关系请保持一致!");
    		return false;
    	}		
		if(fm.CasePayMode.value != strQueryResult[0][2])//校验给付方式必须一致
		{
			alert("受益人:"+fm.cName.value+"的支付方式请保持一致!");
			return false;
		}
		if(fm.CasePayMode.value == '4')//如选择银行转账则银行编码,户名,银行账号也必须一致
		{
      		if(fm.BankCode.value == "" || fm.BankCode.value == null )//银行编码
      	    {	
      	    	alert("支付方式为银行转账时银行编码不能为空!");
      	    	return false;
      	    }
        	if(fm.BankCode.value != strQueryResult[0][3])
        	{
        		alert("同一受益人支付方式为银行转账时银行编码需保持一致!");
        		return false;
        	}
      	    if(fm.BankAccNo.value == "" || fm.BankAccNo.value == null )//银行帐号
      	    {	
      	    	alert("支付方式为银行转账时银行帐号不能为空!");
      	    	return false;
      	    }
        	if(fm.BankAccNo.value != strQueryResult[0][4])
        	{
        		alert("同一受益人支付方式为银行转账时银行账号请保持一致!");
        		return false;
        	}
      	    if(fm.AccName.value == "" || fm.AccName.value == null )//银行帐户名
      	    {	
      	    	alert("支付方式为银行转账时银行帐户名不能为空!");
      	    	return false;
      	    }
          	if(fm.AccName.value != strQueryResult[0][5])
        	{
        		alert("同一受益人支付方式为银行转账时银行帐户名请保持一致!");
        		return false;
        	}
		}
		//校验总金额不能为负
		var sumMoney = parseFloat(fm.getmoney.value);
		for(var j=0; j<strQueryResult.length; j++){
			sumMoney += parseFloat(strQueryResult[j][0]);
		}
		if(sumMoney<0){
			alert("分配后的受益人总金额不能为负!");
	    	return;
		}
	}

	LLBnfGrid.addOne();
	LLBnfGrid.setRowColData(rowNum,1,fm.ClmNo.value);//
	LLBnfGrid.setRowColData(rowNum,2,fm.polNo.value);//
	//alert("222222222222:"+fm.insuredno.value);
	LLBnfGrid.setRowColData(rowNum,3,fm.insuredno.value);//
	LLBnfGrid.setRowColData(rowNum,4,fm.bnfno.value);//受益人序号
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
	LLBnfGrid.setRowColData(rowNum,22,fm.getmoney.value);//受益人分配金额
	LLBnfGrid.setRowColData(rowNum,23,fm.bnflot.value);//受益人分配比例	
	LLBnfGrid.setRowColData(rowNum,24,fm.CasePayMode.value);//
	LLBnfGrid.setRowColData(rowNum,25,fm.BankCode.value);//
	LLBnfGrid.setRowColData(rowNum,26,fm.BankAccNo.value);//
	LLBnfGrid.setRowColData(rowNum,27,fm.AccName.value);//
	LLBnfGrid.setRowColData(rowNum,28,fm.FeeOperationType.value);//
	LLBnfGrid.setRowColData(rowNum,29,fm.CasePayModeName.value);//
	


}

//增加受益人
function addClick_1()
{
    //必须选中保单
    if (LLBalanceGrid.getSelNo() < 1)
    {
        alert("请选择保单");
        return;
    }
    //非空检验
    if(!checkInput())
    {
        return;
    }
    
    //计算百分比及受益金额
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
        alert("分配比例错误!");
        return;
    }
    else
    {

    	
        //计算剩余百分比
        fm.RemainLot.value = 100 - tAll;
        //fm.RemainLot.value=eval(parseFloat(fm.RemainLot.value)).toFixed(2)
        
        fm.RemainLot.value=Math.round(fm.RemainLot.value*100)/100;   
        //alert("fm.RemainLot.value:"+fm.RemainLot.value);
        
        //--------------------------------------------------------------------------------BEG
        //计算受益金额,如果为分配的最后一人，把所有剩余金额都赋给他
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
        //取得序号
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
         * （一）给付金额根据结算情况，可能为负数。负数也要进行分配。
		   （二）受益人分配信息进行保存时，对每个受益人的本次分配项目金额进行合并(每个受益人的所有项目的金额合并在后台执行)。当任何一个受益人的总受益金额为负数时，受益人分配不能进行保存，并进行系统提示"受益人的总受益金额不能为负，请重新核对受益人分配信息！"
		   （三）个人信息判断用受益人信息和数据库中的信息判断,只有个人信息:受益人与被保险人关系,姓名,性别,出生日期,证件类型,证件号码完全一致和数据库保存的信息一致才认为是一个人,进行金额合并,但合并后的金额不能是负值;
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
	    //prompt("校验是否是同一个受益人信息",strSQL);
	    
	    var strQueryResult = easyExecSql(mySql.getString());
	    
	    var tFlag=false;//合并标志
	    var mergeMoney=0.0;//合并金额
        var rowNum = LLBnfGrid.mulLineCount;
	    
	    if(strQueryResult==null || strQueryResult=="")
	    {
	    	
	        //数据库如果没有,再判断已保存的multiline中是否已保存了记录
	    	//-----------------------------------------------------
	    	//填充理赔受益人账户详细信息
	        //-----------------------------------------------------

	        if (rowNum != '0')
	        {
	            for(var i=0;i<rowNum;i++)
	            {
  	
	            	//根据被保险人序号
	            	if((LLBnfGrid.getRowColData(i,3)==fm.insuredno.value)
	            			&&(LLBnfGrid.getRowColData(i,6)==fm.cName.value)
	            			  &&(LLBnfGrid.getRowColData(i,12)==fm.sex.value)
	            			    &&(LLBnfGrid.getRowColData(i,13)==fm.birthday.value)
	            			      &&(LLBnfGrid.getRowColData(i,14)==fm.idtype.value)
	            			       &&(LLBnfGrid.getRowColData(i,15)==fm.idno.value)
	            			        &&(LLBnfGrid.getRowColData(i,11)==fm.relationtoinsured.value))
	            	{
	            		//进入到这里表示是同一被保险人,校验支付方式
	            		 if (fm.CasePayMode.value == "" || fm.CasePayMode.value == null)
	            		 {
	            		        alert("支付方式不能为空!");
	            		        return false;
	            		 }
	            		 
	              		 //alert("fm.CasePayMode.value:"+fm.CasePayMode.value);
	              		 //alert("LLBnfGrid.getRowColData(i,23):"+LLBnfGrid.getRowColData(i,23));
	            		     
	            		 if(fm.CasePayMode.value != LLBnfGrid.getRowColData(i,24))
	            		 {
	            		    	alert("同一受益人支付方式必须一致!");
	            		    	return false;
	            		 }
	            		 
	              		 if(fm.CasePayMode.value == '4')
	            		 {
	              			if(fm.BankCode.value == "" || fm.BankCode.value == null )
	              	    	{	
	              	    		alert("支付方式为银行转账时银行编码不能为空!");
	              	    		return false;
	              	    	}
	              			
	              			//alert("fm.BankCode.value:"+fm.BankCode.value);
	              			//alert("LLBnfGrid.getRowColData(i,24):"+LLBnfGrid.getRowColData(i,24));

	              			
	                		if(fm.BankCode.value != LLBnfGrid.getRowColData(i,25))
		            		{
		            		    	alert("同一受益人支付方式为银行转账时银行编码需保持一致!");
		            		    	return false;
		            		}

	              			
	              	    	if(fm.BankAccNo.value == "" || fm.BankAccNo.value == null )
	              	    	{	
	              	    		alert("支付方式为银行转账时银行帐号不能为空!");
	              	    		return false;
	              	    	}
	              	    	

	             			//alert("fm.BankAccNo.value:"+fm.BankAccNo.value);
	              			//alert("LLBnfGrid.getRowColData(i,25):"+LLBnfGrid.getRowColData(i,25));
	              	    	
	                	    if(fm.BankAccNo.value != LLBnfGrid.getRowColData(i,26))
		            		{
		            		    	alert("同一受益人支付方式为银行转账时银行账号需保持一致!");
		            		    	return false;
		            		}

	              			
	              	    	if(fm.AccName.value == "" || fm.AccName.value == null )
	              	    	{	
	              	    		alert("支付方式为银行转账时银行帐户名不能为空!");
	              	    		return false;
	              	    	}
	              	    	
	             			//alert("fm.AccName.value:"+fm.AccName.value);
	              			//alert("LLBnfGrid.getRowColData(i,26):"+LLBnfGrid.getRowColData(i,26));
	              	    	
	                  	    if(fm.AccName.value != LLBnfGrid.getRowColData(i,27))
		            		{
		            		    	alert("同一受益人支付方式为银行转账时银行户名需保持一致!");
		            		    	return false;
		            		}
	                  	    
	                  	    if(fm.payeeidtype.value != "0"||fm.idtype.value !="0"){
	                  	    	alert("领取方式为银行转账，受益人与领取人的证件类型必须为身份证!");
	            		    	return false;
	                  	    }
	            		 }
		
	              		 //合并受益人
	              		 //更新分配的受益人金额和比例
	     		    	 document.all('oriBnfPay').value=parseFloat(document.all('getmoney').value);    
	              		 mergeMoney=parseFloat(LLBnfGrid.getRowColData(i,22))+parseFloat(document.all('getmoney').value);
	        	    	 
	        	    	 if(mergeMoney<0)
	        	    	 {
	        	    	      alert("分配后的受益人总金额不能为负!");
	        	    	      return;
	        	    	 }
	        	    	 

	        	    	 document.all('getmoney').value=(parseFloat(document.all('getmoney').value) + parseFloat(LLBnfGrid.getRowColData(i,22))).toFixed(2);
	        	    	 document.all('bnflot').value=eval((document.all('getmoney').value / fm.sumPay.value).toFixed(2)*100);


	        		     LLBnfGrid.setRowColData(i,22,fm.getmoney.value);//受益人分配金额
	        		     LLBnfGrid.setRowColData(i,23,fm.bnflot.value);//受益人分配比例
	        		     
	        		     
	        		     tFlag=true;
	            	}
	            }

	            //alert("tFlag:"+tFlag);
	            
	            //本次添加的是一个新受益人
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
	    	        
	                fm.oriBnfNo.value = LLBnfGrid.getRowColData(i,4);//备份受益人序号,为了修改受益人信息时校验用
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
   	    	      alert("分配后的受益人总金额不能为负!");
   	    	      return;
   	    	 	}
	        	//第一条记录,直接新增受益人记录
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
    	        
                fm.oriBnfNo.value = LLBnfGrid.getRowColData(i,4);//备份受益人序号,为了修改受益人信息时校验用
    	        LLBnfGrid.setRowColData(i,30,fm.oriBnfNo.value);//
	 
	 	        LLBnfGrid.setRowColData(i,31,LLBnfGrid.getRowColData(i,22));
	            //alert("22--LLBnfGrid.getRowColData("+i+",30):"+LLBnfGrid.getRowColData(i,30));
	        }

	    }
	    else
	    {
	    	
	    	 //首先校验给付方式必须一致,如选择银行转账则银行编码,户名,银行账号也必须一致
	    	 if(fm.CasePayMode.value != strQueryResult[0][2])
    		 {
    		    	alert("受益人:"+fm.cName.value+"的支付方式请保持一致!");
    		    	return false;
    		 }
    		 
      		 if(fm.CasePayMode.value == '4')
    		 {
      			if(fm.BankCode.value == "" || fm.BankCode.value == null )
      	    	{	
      	    		alert("支付方式为银行转账时银行编码不能为空!");
      	    		return false;
      	    	}
      			
      			//alert("fm.BankCode.value:"+fm.BankCode.value);
      			//alert("LLBnfGrid.getRowColData(i,24):"+LLBnfGrid.getRowColData(i,24));

      			
        		if(fm.BankCode.value != strQueryResult[0][3])
        		{
        		    	alert("同一受益人支付方式为银行转账时银行编码需保持一致!");
        		    	return false;
        		}

      			
      	    	if(fm.BankAccNo.value == "" || fm.BankAccNo.value == null )
      	    	{	
      	    		alert("支付方式为银行转账时银行帐号不能为空!");
      	    		return false;
      	    	}
      	    	

     			//alert("fm.BankAccNo.value:"+fm.BankAccNo.value);
      			//alert("LLBnfGrid.getRowColData(i,25):"+LLBnfGrid.getRowColData(i,25));
      	    	
        	    if(fm.BankAccNo.value != strQueryResult[0][4])
        		{
        		    	alert("受益人:"+fm.cName.value+"的支付方式为银行账号请保持一致!");
        		    	return false;
        		}

      			
      	    	if(fm.AccName.value == "" || fm.AccName.value == null )
      	    	{	
      	    		alert("支付方式为银行转账时银行帐户名不能为空!");
      	    		return false;
      	    	}
      	    	
     			//alert("fm.AccName.value:"+fm.AccName.value);
      			//alert("LLBnfGrid.getRowColData(i,26):"+LLBnfGrid.getRowColData(i,26));
      	    	
          	    if(fm.AccName.value != strQueryResult[0][5])
        		{
        		    	alert("受益人:"+fm.cName.value+"的支付方式为银行户名请保持一致!");
        		    	return false;
        		}
    		 }
      		 
	    	 //校验被保险人关系必须保持一致
	    	 if(fm.relationtoinsured.value != strQueryResult[0][6])
    		 {
    		    	alert("受益人:"+fm.cName.value+"的与被保险人关系请保持一致!");
    		    	return false;
    		 }
	    	 
	    	 document.all('oriBnfPay').value=parseFloat(document.all('getmoney').value);    	 
	    	 
	    	 //alert("document.all('oriBnfPay').value:"+document.all('oriBnfPay').value);
	    	 
	    	 //alert("parseFloat(strQueryResult[0][0]):"+parseFloat(strQueryResult[0][0]));
	    	 //alert("parseFloat(document.all('getmoney').value:"+parseFloat(document.all('getmoney').value));

	         //更新分配的受益人金额和比例
	    	 mergeMoney=parseFloat(strQueryResult[0][0])+parseFloat(document.all('getmoney').value);
	    	 
	    	 //alert("mergeMoney:"+mergeMoney);
	    	 
	    	 if(mergeMoney<0)
	    	 {
	    	      alert("分配后的受益人总金额不能为负!");
	    	      return;
	    	 }
	    	 
	    	 document.all('getmoney').value=mergeMoney.toFixed(2);
	    	 
	    	 /**
	    	  * 2009-05-10 zhangzheng
	    	  * 即使数据库存在记录,在和数据库中存在记录合并之后,防止用户多次点击反复添加记录,也需要判断是否需要和已经添加的multiline进行合并
	    	  */
	    	 var rowNum2 = LLBnfGrid.mulLineCount;
	    	 
		     if (rowNum != '0')
		     {
		         for(var i=0;i<rowNum;i++)
		         {
	  	
		            	//根据被保险人序号
		            	if((LLBnfGrid.getRowColData(i,3)==fm.insuredno.value)
		            			&&(LLBnfGrid.getRowColData(i,6)==fm.cName.value)
		            			  &&(LLBnfGrid.getRowColData(i,12)==fm.sex.value)
		            			    &&(LLBnfGrid.getRowColData(i,13)==fm.birthday.value)
		            			      &&(LLBnfGrid.getRowColData(i,14)==fm.idtype.value)
		            			       &&(LLBnfGrid.getRowColData(i,15)==fm.idno.value)
		            			        &&(LLBnfGrid.getRowColData(i,11)==fm.relationtoinsured.value))
		            	{
		            		//进入到这里表示是同一被保险人,校验支付方式
		            		 if (fm.CasePayMode.value == "" || fm.CasePayMode.value == null)
		            		 {
		            		        alert("支付方式不能为空!");
		            		        return false;
		            		 }
		            		 
		              		 //alert("fm.CasePayMode.value:"+fm.CasePayMode.value);
		              		 //alert("LLBnfGrid.getRowColData(i,23):"+LLBnfGrid.getRowColData(i,23));
		            		     
		            		 if(fm.CasePayMode.value != LLBnfGrid.getRowColData(i,24))
		            		 {
		            		    	alert("同一受益人支付方式必须一致!");
		            		    	return false;
		            		 }
		            		 
		              		 if(fm.CasePayMode.value == '4')
		            		 {
		              			if(fm.BankCode.value == "" || fm.BankCode.value == null )
		              	    	{	
		              	    		alert("支付方式为银行转账时银行编码不能为空!");
		              	    		return false;
		              	    	}
		              			
		              			//alert("fm.BankCode.value:"+fm.BankCode.value);
		              			//alert("LLBnfGrid.getRowColData(i,24):"+LLBnfGrid.getRowColData(i,24));

		              			
		                		if(fm.BankCode.value != LLBnfGrid.getRowColData(i,25))
			            		{
			            		    	alert("同一受益人支付方式为银行转账时银行编码需保持一致!");
			            		    	return false;
			            		}

		              			
		              	    	if(fm.BankAccNo.value == "" || fm.BankAccNo.value == null )
		              	    	{	
		              	    		alert("支付方式为银行转账时银行帐号不能为空!");
		              	    		return false;
		              	    	}
		              	    	

		             			//alert("fm.BankAccNo.value:"+fm.BankAccNo.value);
		              			//alert("LLBnfGrid.getRowColData(i,25):"+LLBnfGrid.getRowColData(i,25));
		              	    	
		                	    if(fm.BankAccNo.value != LLBnfGrid.getRowColData(i,26))
			            		{
			            		    	alert("同一受益人支付方式为银行转账时银行账号需保持一致!");
			            		    	return false;
			            		}

		              			
		              	    	if(fm.AccName.value == "" || fm.AccName.value == null )
		              	    	{	
		              	    		alert("支付方式为银行转账时银行帐户名不能为空!");
		              	    		return false;
		              	    	}
		              	    	
		             			//alert("fm.AccName.value:"+fm.AccName.value);
		              			//alert("LLBnfGrid.getRowColData(i,26):"+LLBnfGrid.getRowColData(i,26));
		              	    	
		                  	    if(fm.AccName.value != LLBnfGrid.getRowColData(i,27))
			            		{
			            		    	alert("同一受益人支付方式为银行转账时银行户名需保持一致!");
			            		    	return false;
			            		}
		                  	    
		                  	    if(fm.payeeidtype.value != "0"||fm.idtype.value !="0"){
		                  	    	alert("领取方式为银行转账，受益人与领取人的证件类型必须为身份证!");
		            		    	return false;
		                  	    }
		            		 }        		     
		        		     
		        		     tFlag=true;
		        		     break;
		            	}
		            }

		            //alert("tFlag:"+tFlag);
		            
		            //本次添加的是一个新受益人
		            if(tFlag==true)
		            {
	        	    	alert("受益人已经添加在列表中,不能重复添加,如需修改分配信息,请点击修改按钮分配!");
        		    	return false;
		            }
		     }
		     
		     if(tFlag==false)
		     {
		    	//第一条记录,直接新增受益人记录
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
	 	         
			     LLBnfGrid.setRowColData(rowNum,4,strQueryResult[0][1]);//受益人序号
			     LLBnfGrid.setRowColData(rowNum,21,fm.bnfCurrency.value);
			     LLBnfGrid.setRowColData(rowNum,22,fm.getmoney.value);//受益人分配金额
			     LLBnfGrid.setRowColData(rowNum,23,fm.bnflot.value);//受益人分配比例
			     
	             fm.oriBnfNo.value = LLBnfGrid.getRowColData(rowNum,4);//备份受益人序号,为了修改受益人信息时校验用
	 	         LLBnfGrid.setRowColData(rowNum,30,fm.oriBnfNo.value);//

		 	     LLBnfGrid.setRowColData(rowNum,31,LLBnfGrid.getRowColData(rowNum,22));
		         //alert("32--LLBnfGrid.getRowColData("+rowNum+",30):"+LLBnfGrid.getRowColData(rowNum,30));
		     }
	    }        
    }
}

//修改受益人
function updateClick()
{
    //1、必须选中保单
    if (LLBalanceGrid.getSelNo() < 1)
    {
        alert("请选择保单！");
        return;
    }
    
    //2、非空检验
    if(!checkInput())
    {
        return;
    }
    
    //3、计算mulLinemulLine中除选中行的合计受益金额、合计受益比例 
    var tAllMoney = 0;	//mulLine中合计受益金额
    var tAllLot = 0;	//mulLine中合计受益比例
    for (var j=0; j < LLBnfGrid.mulLineCount; j++)
    {
    	if (j != LLBnfGrid.getSelNo()-1){
        	tAllMoney = tAllMoney + parseFloat(LLBnfGrid.getRowColData(j,22));
        	tAllLot = tAllLot + parseFloat(LLBnfGrid.getRowColData(j,23));
        }
    }    
    
    //4、校验受益比例之和<=100，计算剩余百分比 
    if (fm.bnflot.value == "" || fm.bnflot.value == null)
    {
        fm.bnflot.value = 0;
    }    
    var tBnfLot = parseFloat(fm.bnflot.value);  
    if (tAllLot + tBnfLot > 100)	//校验受益比例之和<=100
    {
        alert("分配比例之和大于100，请调整!");
        return;
    }
	fm.RemainLot.value = Math.round((100 - tAllLot - tBnfLot)*100)/100;
    
    //5、计算受益金额
    if(tAllLot + tBnfLot == 100)	//如果为分配的最后一人，把所有剩余金额都赋给他
	{
		fm.getmoney.value = eval(parseFloat(fm.sumPay.value)-tAllMoney).toFixed(2)
	}
	else if(tAllLot + tBnfLot < 100)
	{
		fm.getmoney.value = eval((parseFloat(fm.sumPay.value)*tBnfLot*0.01).toFixed(2));
	}
    
	//6、校验已添加的受益人如果和要添加的是同一个人，返回错误提示
 	var rowNum = LLBnfGrid.mulLineCount;
	for(var i=0;i<rowNum;i++)
	{
		if (i != LLBnfGrid.getSelNo()-1){
			if((LLBnfGrid.getRowColData(i,3)==fm.insuredno.value)//根据被保险人序号
				&&(LLBnfGrid.getRowColData(i,6)==fm.cName.value)
				&&(LLBnfGrid.getRowColData(i,12)==fm.sex.value)
				&&(LLBnfGrid.getRowColData(i,13)==fm.birthday.value)
				&&(LLBnfGrid.getRowColData(i,14)==fm.idtype.value)
				&&(LLBnfGrid.getRowColData(i,15)==fm.idno.value)
				&&(LLBnfGrid.getRowColData(i,11)==fm.relationtoinsured.value))
			{
				alert("该受益人已经添加，不能重复添加!");
				return false;
			}
		}
	}
   
    //7、校验已保存的受益人如果和要添加的是同一个人，支付方式等信息要一致，并计算待添加受益人的序号
	/*var strSQL = " select GetMoney,BnfNo,CasePayMode,BankCode,BankAccNo,AccName,RelationToInsured from LLBnf "
		+ " where clmno ='"+document.all('ClmNo').value+"'"
		+ " and Insuredno='"+document.all('insuredno').value+"'"
		+ " and BnfKind='"+fm.BnfKind.value+"'"
		+ " and Name='"+document.all('cName').value+"'"
		+ " and sex='"+document.all('sex').value+"'"
		+ " and Birthday='"+document.all('birthday').value+"'"
		+ " and idtype='"+document.all('idtype').value+"'"
		+ " and idno='"+document.all('idno').value+"'";*/	//prompt("校验是否是同一个受益人信息",strSQL);	    
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
  
	if(strQueryResult==null || strQueryResult=="")//新的受益人
	{
		fm.bnfno.value = LLBnfGrid.getRowColData(LLBnfGrid.getSelNo()-1,4);
	}
	else	//数据库中保存过此受益人	
	{
		fm.bnfno.value = strQueryResult[0][1];//取已保存的序号
	    if(fm.relationtoinsured.value != strQueryResult[0][6])//校验被保险人关系必须保持一致
    	{
    		alert("受益人:"+fm.cName.value+"的与被保险人关系请保持一致!");
    		return false;
    	}		
		if(fm.CasePayMode.value != strQueryResult[0][2])//校验给付方式必须一致
		{
			alert("受益人:"+fm.cName.value+"的支付方式请保持一致!");
			return false;
		}
		if(fm.CasePayMode.value == '4')//如选择银行转账则银行编码,户名,银行账号也必须一致
		{
      		if(fm.BankCode.value == "" || fm.BankCode.value == null )//银行编码
      	    {	
      	    	alert("支付方式为银行转账时银行编码不能为空!");
      	    	return false;
      	    }
        	if(fm.BankCode.value != strQueryResult[0][3])
        	{
        		alert("同一受益人支付方式为银行转账时银行编码需保持一致!");
        		return false;
        	}
      	    if(fm.BankAccNo.value == "" || fm.BankAccNo.value == null )//银行帐号
      	    {	
      	    	alert("支付方式为银行转账时银行帐号不能为空!");
      	    	return false;
      	    }
        	if(fm.BankAccNo.value != strQueryResult[0][4])
        	{
        		alert("同一受益人支付方式为银行转账时银行账号请保持一致!");
        		return false;
        	}
      	    if(fm.AccName.value == "" || fm.AccName.value == null )//银行帐户名
      	    {	
      	    	alert("支付方式为银行转账时银行帐户名不能为空!");
      	    	return false;
      	    }
          	if(fm.AccName.value != strQueryResult[0][5])
        	{
        		alert("同一受益人支付方式为银行转账时银行帐户名请保持一致!");
        		return false;
        	}
		}      	
	}

	var k = LLBnfGrid.getSelNo() - 1;//得到焦点行
	LLBnfGrid.setRowColData(k,1,fm.ClmNo.value);//
	LLBnfGrid.setRowColData(k,2,fm.polNo.value);//
	LLBnfGrid.setRowColData(k,3,fm.insuredno.value);//
	LLBnfGrid.setRowColData(k,4,fm.bnfno.value);//受益人序号
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
	LLBnfGrid.setRowColData(k,22,fm.getmoney.value);//受益人分配金额
	LLBnfGrid.setRowColData(k,23,fm.bnflot.value);//受益人分配比例	
	LLBnfGrid.setRowColData(k,24,fm.CasePayMode.value);//
	LLBnfGrid.setRowColData(k,25,fm.BankCode.value);//
	LLBnfGrid.setRowColData(k,26,fm.BankAccNo.value);//
	LLBnfGrid.setRowColData(k,27,fm.AccName.value);//
	LLBnfGrid.setRowColData(k,28,fm.FeeOperationType.value);//
	LLBnfGrid.setRowColData(k,29,fm.CasePayModeName.value);//
}

//修改受益人
function updateClick_1()
{
    var k = LLBnfGrid.getSelNo() - 1;//得到焦点行
    if (k < 0)
    {
        alert("请选中一行!");
        return;
    }
    //非空检验
    if(!checkInput())
    {
        return;
    }
    //计算百分比及受益金额
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
    
    var tAllLot=tAll;//备份除本行记录的所有保存记录的比例
    tAllLot=Math.round(tAllLot*100)/100; 
    
    var tBnfLot = parseFloat(fm.bnflot.value);
    tAll = tAll + tBnfLot;
    if (tAll > 100 || tAll < 0)
    {
        alert("分配比例错误!");
        return;
    }
    else
    {
        //计算剩余百分比
        fm.RemainLot.value = 100 - tAll;
        fm.RemainLot.value=Math.round(fm.RemainLot.value*100)/100;   
        //--------------------------------------------------------------------------------BEG
        //计算受益金额,如果为分配的最后一人，把所有剩余金额都赋给他
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

        	//如果只有一列则金额为全部金额,否则为总金额-已赔付的比例*总金额
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
         * （一）给付金额根据结算情况，可能为负数。负数也要进行分配。
		   （二）受益人分配信息进行保存时，对每个受益人的本次分配项目金额进行合并(每个受益人的所有项目的金额合并在后台执行)。当任何一个受益人的总受益金额为负数时，受益人分配不能进行保存，并进行系统提示"受益人的总受益金额不能为负，请重新核对受益人分配信息！"
		   （三）个人信息判断用受益人信息和数据库中的信息判断,只有个人信息:受益人与被保险人关系,姓名,性别,出生日期,证件类型,证件号码完全一致和数据库保存的信息一致才认为是一个人,进行金额合并,但合并后的金额不能是负值;
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
	    //prompt("校验是否是同一个受益人信息",strSQL);
	    
	    var strQueryResult = easyExecSql(mySql.getString());
	    
	    var tFlag=false;//合并标志
	    var mergeMoney=0.0;//合并金额
	    
	    if(strQueryResult==null || strQueryResult=="")
	    {
	    	


	         //更新分配的受益人金额	    	 
	    	 //document.all('getmoney').value=(parseFloat(document.all('getmoney').value) ).toFixed(2);    	 
	    	 //alert("document.all('getmoney').value:"+document.all('getmoney').value);
	    	 
	    	 if(document.all('getmoney').value<0)
	    	 {
	    	      alert("分配后的受益人总金额不能为负!");
	    	      return false;
	    	 }
	      	    document.all('getmoney').value=(parseFloat(document.all('getmoney').value)).toFixed(2);
		    	document.all('oriBnfPay').value=parseFloat(document.all('getmoney').value);    
	            //数据库如果没有,直接修改记录
    	        LLBnfGrid.setRowColData(k,1,fm.ClmNo.value);//
    	        LLBnfGrid.setRowColData(k,2,fm.polNo.value);//
    	        LLBnfGrid.setRowColData(k,3,fm.insuredno.value);//

    	        //alert("fm.oriBnfNo.value1:"+fm.oriBnfNo.value);
    	        //alert("mBnfBatNo:"+mBnfBatNo);
    	        
    	        //如果备份字段为空,则表明这是第一条受益人记录
    	        if(fm.oriBnfNo.value==null||fm.oriBnfNo.value=="")
    	        {
    	        	mBnfBatNo++
	    	        fm.bnfno.value=mBnfBatNo;
	    	        
    	        	LLBnfGrid.setRowColData(k,4,fm.bnfno.value);
    	        	
    	        	//alert("LLBnfGrid.getRowColData("+k+",4):"+LLBnfGrid.getRowColData(k,4));
    	        	
    	        	fm.oriBnfNo.value = LLBnfGrid.getRowColData(k,4);//备份受益人序号,为了修改受益人信息时校验用
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
	    	
	    	 //首先校验给付方式必须一致,如选择银行转账则银行编码,户名,银行账号也必须一致
	    	 if(fm.CasePayMode.value != strQueryResult[0][2])
    		 {
    		    	alert("受益人"+fm.cName.value+"的支付方式请保持一致!");
    		    	return false;
    		 }
      		 if(fm.CasePayMode.value == '4')
    		 {
      			if(fm.BankCode.value == "" || fm.BankCode.value == null )
      	    	{	
      	    		alert("支付方式为银行转账时银行编码不能为空!");
      	    		return false;
      	    	}
      			
      			//alert("fm.BankCode.value:"+fm.BankCode.value);
      			//alert("LLBnfGrid.getRowColData(i,24):"+LLBnfGrid.getRowColData(i,24));

      			
        		if(fm.BankCode.value != strQueryResult[0][3])
        		{
        		    	alert("同一受益人支付方式为银行转账时银行编码需保持一致!");
        		    	return false;
        		}

      			
      	    	if(fm.BankAccNo.value == "" || fm.BankAccNo.value == null )
      	    	{	
      	    		alert("支付方式为银行转账时银行帐号不能为空!");
      	    		return false;
      	    	}
      	    	

     			//alert("fm.BankAccNo.value:"+fm.BankAccNo.value);
      			//alert("LLBnfGrid.getRowColData(i,25):"+LLBnfGrid.getRowColData(i,25));
      	    	
        	    if(fm.BankAccNo.value != strQueryResult[0][4])
        		{
        		    	alert("受益人"+fm.cName.value+"的支付方式为银行账号请保持一致!");
        		    	return false;
        		}

      			
      	    	if(fm.AccName.value == "" || fm.AccName.value == null )
      	    	{	
      	    		alert("支付方式为银行转账时银行帐户名不能为空!");
      	    		return false;
      	    	}
      	    	
     			//alert("fm.AccName.value:"+fm.AccName.value);
      			//alert("LLBnfGrid.getRowColData(i,26):"+LLBnfGrid.getRowColData(i,26));
      	    	
          	    if(fm.AccName.value != strQueryResult[0][5])
        		{
        		    	alert("受益人"+fm.cName.value+"的支付方式为银行户名请保持一致!");
        		    	return false;
        		}
          	    
          	    if(fm.payeeidtype.value != "0"||fm.idtype.value !="0"){
                  alert("领取方式为银行转账，受益人与领取人的证件类型必须为身份证!");
                  return false;
          	    }
    		 }
	    	 //校验被保险人关系必须保持一致
	    	 if(fm.relationtoinsured.value != strQueryResult[0][6])
    		 {
    		    	alert("受益人"+fm.cName.value+"的与被保险人关系请保持一致!");
    		    	return false;
    		 }

	         //更新分配的受益人金额	    	 
		     //document.all('oriBnfPay').value=parseFloat(document.all('getmoney').value);    
	    	 document.all('getmoney').value=(parseFloat(document.all('getmoney').value) + parseFloat(strQueryResult[0][0])).toFixed(2);    	 
	    	 //alert("document.all('getmoney').value:"+document.all('getmoney').value);
	    	 
	    	 if(document.all('getmoney').value<0)
	    	 {
	    	      alert("分配后的受益人总金额不能为负!");
	    	      return false;
	    	 }
	    	 
		     LLBnfGrid.setRowColData(k,4,strQueryResult[0][1]);//受益人序号
		     
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
 	         	         
 	         //如果备份字段为空,则表明这是第一条受益人记录
 	         fm.oriBnfNo.value = strQueryResult[0][6];//备份受益人序号
	 	     
 	 	    LLBnfGrid.setRowColData(k,31,LLBnfGrid.getRowColData(k,22));
            //alert("42-LLBnfGrid.getRowColData("+k+",30):"+LLBnfGrid.getRowColData(k,30));

	    } 
    }
}

//删除受益人
function deleteClick()
{
    var k = LLBnfGrid.getSelNo() - 1;//得到焦点行
    if (k < 0)
    {
        alert("请选中一行!");
        return;
    }
    
    //-------------------------------------------------------------------------------------------------------------------------BEG
    //进行受益人删除的时候,只能删除支付标志为0的数据,也就是未支付,因为如果标志为1则代表该笔数据已经进行的支付处理(主要针对预付)
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
        alert("该笔数据已经进行的支付处理,不能删除！");
        return;
    }
    else
    {
        LLBnfGrid.delRadioTrueLine();
        emptyInput();
    }
 
    //-------------------------------------------------------------------------------------------------------------------------END
}

//校验现在保存的项目是否已经保存完成,如果是已保存完成则必须先全部删除所有保存的项目才行
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

	 //prompt("校验同一项目是否反复分配",strSql);
	 
	 var tBNF = easyExecSql(mySql.getString());

	 if (tBNF)
	 {
		 for (var i = 0; i < tBNF.length; i++)
		 {
			 if (tBNF[i] == '1')
			 {
				 alert("本项目的受益人已经完成,如需修改,请点全部撤销按钮撤销已保存的所有项目后重新分配!");
				 return false;
			 }
		 }
	 }

     return true;
}

//对保项修改保存到后台
function saveClick()
{
	//限制过不能重复保存已保存的项目,必须先全部删除后再重新录入
    if (!checkRepeatSave())
    {
        return;
    }

//	alert(document.all('insuredno').value);
    //保存时做非空项校验
    var i = LLBnfGrid.mulLineCount;
    var tAll = 0;
    for (var j=0; j < i; j++)
    {
        if (LLBnfGrid.getRowColData(j,6) == null || LLBnfGrid.getRowColData(j,6) == "")
        {
            j++;
            alert("受益人"+j+"的姓名不能为空!");
            return;
        }
        
        if (LLBnfGrid.getRowColData(j,8) == null || LLBnfGrid.getRowColData(j,8) == "")
        {
            j++;
            alert("受益人"+j+"的领款人姓名不能为空!");
            return;
        }

        if (LLBnfGrid.getRowColData(j,12) == null || LLBnfGrid.getRowColData(j,12) == "")
        {
            j++;
            alert("受益人"+j+"的性别不能为空!");
            return;
        }
        if (LLBnfGrid.getRowColData(j,13) == null || LLBnfGrid.getRowColData(j,13) == "")
        {
            j++;
            alert("受益人"+j+"的出生日期不能为空!");
            return;
        }
        if (LLBnfGrid.getRowColData(j,14) != "9" ){
	        if (LLBnfGrid.getRowColData(j,15) == null || LLBnfGrid.getRowColData(j,15) == "")
	        {
	            j++;
	            alert("受益人"+j+"的证件号码不能为空!");
	            return;
	        }
        }
        if (LLBnfGrid.getRowColData(j,17) == null || LLBnfGrid.getRowColData(j,17) == "")
        {
            j++;
            alert("受益人"+j+"的领款人性别不能为空!");
            return;
        }
        if (LLBnfGrid.getRowColData(j,18) == null || LLBnfGrid.getRowColData(j,18) == "")
        {
            j++;
            alert("受益人"+j+"的领款人生日不能为空!");
            return;
        }
        if (LLBnfGrid.getRowColData(j,19) != "9" ){
	        if (LLBnfGrid.getRowColData(j,20) == null || LLBnfGrid.getRowColData(j,20) == "")
	        {
	            j++;
	            alert("受益人"+j+"的领款人证件号码不能为空!");
	            return;
	        }
        }
        if (
        		//LLBnfGrid.getRowColData(j,22) < 0 ||
        		LLBnfGrid.getRowColData(j,23) > 100)
        {
            j++;
            alert("受益人"+j+"的受益比例错误!");
            return;
        }
        if (LLBnfGrid.getRowColData(j,24) == null || LLBnfGrid.getRowColData(j,24) == "")
        {
            j++;
            alert("受益人"+j+"的支付方式不能为空!");
            return;
        }
        tAll = tAll + parseFloat(LLBnfGrid.getRowColData(j,23));
    }
    
    //分配比例100%校验
    if(tAll != 100)
    {
        alert("未完全分配受益金额!");
        return;
    }
    
    fm.action = './LLBnfSave.jsp';
   // alert(document.all('insuredno').value);
    submitForm();
}

//提交转年金处理
function saveClmToPension()
{
    if (fm.PensionType.value == "" || fm.PensionType.value == null)
    {
        alert("请选择转年金类型！");
        return;
    }
    fm.action = './LLBnfToPensionSave.jsp';
    submitForm();
}

//全部撤销方法
function repealClick()
{
    //提交数据
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = './LLBnfRepealSave.jsp';
    document.getElementById("fm").submit(); //提交
    tSaveFlag ="0";
}

//公共提交方法
function submitForm()
{
    //提交数据
    var i = 0;
    var showStr = "正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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

//提交后操作,返回
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    //queryBnfBatNo();
    
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        emptyInput();
        queryLLBalanceGrid();

        /**-----------------------------------------------------------------------------------BEG
         * 转年金增加点击后判断是否分配来显示按钮和操作域
         * add 周磊 2006-3-3 9:23
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

//撤销后操作,返回
function afterSubmit2( FlagStr, content )
{
    showInfo.close();

    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=350;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
        emptyInput();
        initForm();
//        alert("insuredno="+fm.insuredno.value);
    }

}

//默认领取人为收益人
function setName()
{
    //号码
    if(fm.payeeNo.value == "" || fm.payeeNo.value == null)
    {
        fm.payeeNo.value = fm.customerNo.value;
    }
    //姓名
    if(fm.payeename.value == "" || fm.payeename.value == null)
    {
        fm.payeename.value = fm.cName.value;
    }
}

//清空输入域
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

//检查非空
function checkInput()
{
   
    if (fm.insuredno.value == "" || fm.insuredno.value == null)
    {
        alert("被保人号码不能为空!");
        return false;
    }
    
    if (fm.relationtoinsured.value == "" || fm.relationtoinsured.value == null)
    {
        alert("受益人与被保人关系不能为空!");
        return false;
    }
    
    if (fm.cName.value == "" || fm.cName.value == null)
    {
        alert("受益人姓名不能为空!");
        return false;
    }
    
    //niuzj,2005-11-02,增加非空校验
    if (fm.sex.value == "" || fm.sex.value == null)
    {
        alert("受益人性别不能为空!");
        return false;
    }
    if (fm.birthday.value == "" || fm.birthday.value == null)
    {
        alert("受益人出生日期不能为空!");
        return false;
    }
    if (fm.idtype.value == "" || fm.idtype.value == null)
    {
        alert("受益人证件类型不能为空!");
        return false;
    }
//    if(fm.idtype.value != "9"){
	    if (fm.idno.value == "" || fm.idno.value == null)
	    {
	        alert("受益人证件号码不能为空!");
	        return false;
	    }
//    }
    
    if (fm.relationtopayee.value == "" || fm.relationtopayee.value == null)
    {
        alert("领款人与受益人关系不能为空!");
        return false;
    }
    
    if (fm.payeename.value == "" || fm.payeename.value == null)
    {
        alert("领款人姓名不能为空!");
        return false;
    }
    //niuzj,2005-11-02,增加非空校验
    if (fm.payeesex.value == "" || fm.payeesex.value == null)
    {
        alert("领款人性别不能为空!");
        return false;
    }
    
    if (fm.payeebirthday.value == "" || fm.payeebirthday.value == null)
  
    {
        alert("领款人出生日期不能为空!");
        return false;
    }
    if (fm.payeeidtype.value == "" || fm.payeeidtype.value == null)
    {
        alert("领款人证件类型不能为空!");
        return false;
    }
//    if (fm.payeeidtype.value != "9" ){
	    if (fm.payeeidno.value == "" || fm.payeeidno.value == null)
	    {
	        alert("领款人证件号码不能为空!");
	        return false;
	    }
//    }
    
    if((fm.relationtoinsured.value=="00")&&(fm.relationtopayee.value=="00"))
    {
        if (fm.cName.value !=fm.payeename.value)
        {
            alert("当受益人和领款人均为被保险人本人时,受益人姓名和领款人姓名必须一致!");
            return false;
        }
        
        if (fm.sex.value !=fm.payeesex.value)
        {
            alert("当受益人和领款人均为被保险人本人时,受益人性别和领款人性别必须一致!");
            return false;
        }
        
        if (fm.birthday.value !=fm.payeebirthday.value)
        {
            alert("当受益人和领款人均为被保险人本人时,受益人出生日期和领款人出生日期必须一致!");
            return false;
        }
        
        if (fm.idtype.value !=fm.payeeidtype.value)
        {
            alert("当受益人和领款人均为被保险人本人时,受益人证件类型和领款人证件类型必须一致!");
            return false;
        }
        
        if (fm.idno.value !=fm.payeeidno.value)
        {
            alert("当受益人和领款人均为被保险人本人时,受益人证件号码和领款人证件号码必须一致!");
            return false;
        }
    }
    
	if(tDeadFlag ==1)
	{
	    //身故案件的受益人和领款人均不能是被保险人本人
	    if(fm.relationtoinsured.value=="00")
	    {
			  alert("身故案件的受益人不能是被保险人本人!");
		      return false;
	    }
	    
	    if(fm.relationtopayee.value=="00")
	    {
			  alert("身故案件的领款人不能是被保险人本人!");
		      return false;
	    }
	}

    if (fm.CasePayMode.value == "" || fm.CasePayMode.value == null)
    {
        alert("支付方式不能为空!");
        return false;
    }
     
    if((fm.CasePayMode.value != null || fm.CasePayMode.value != "") && fm.CasePayMode.value == '4')
    {
    	if(fm.BankCode.value == "" || fm.BankCode.value == null || fm.BankCode.value == '0')
    	{
    		alert("银行编码不能为空!");
    		return false;
    	}
    	if(fm.BankAccNo.value == "" || fm.BankAccNo.value == null || fm.BankAccNo.value == '0')
    	{	
    		alert("银行帐号不能为空!");
    		return false;
    	}
    	if(!checkBankAccNo(fm.BankCode.value, fm.BankAccNo.value)){
    		return false;
    	}
    	if(fm.AccName.value == "" || fm.AccName.value == null || fm.AccName.value == '0')
    	{	
    		alert("银行帐户名不能为空!");
    		return false;
    	}
    	if(fm.payeeidtype.value != "0"||fm.idtype.value !="0"){
            alert("领取方式为银行转账，受益人与领取人的证件类型必须为身份证!");
            return false;
    	}
    }
    
    if((fm.CasePayMode.value != null || fm.CasePayMode.value != "") && fm.CasePayMode.value == '1')
    {
    	if((fm.BankCode.value != "" && fm.BankCode.value != null) || fm.BankCode.value == '0')
    	{	
    		alert("支付方式为现金时银行编码需为空!");
    		return false;
    	}
    	if((fm.BankAccNo.value != "" && fm.BankAccNo.value != null) || fm.BankAccNo.value == '0')
    	{	
    		alert("支付方式为现金时银行帐号需为空!");
    		return false;
    	}
    	if((fm.AccName.value != "" && fm.AccName.value != null) || fm.AccName.value == '0')
    	{	
    		alert("支付方式为现金时银行帐户名需为空!");
    		return false;
    	}
    }
    
    return true;
}

//显示隐藏span域
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

//设置界面上的所有按钮为disabled
function disabledButton()
{
    var elementsNum = 0;//FORM中的元素数
    //遍历FORM中的所有ELEMENT
    for (elementsNum=0; elementsNum<fm.elements.length; elementsNum++)
    {
  	    if (fm.elements[elementsNum].type == "button")
  	    {
  	        fm.elements[elementsNum].disabled = true;
  	    }
  	}
}

/*******************************************************************
 * 开始
 * 对证件的输入二次验证
 * 作者： 万泽辉
 * 日期： 2005-11-11
 ******************************************************************/

//验证证件类型
function checkidtype()
{
	if(fm.idno.value==""&&fm.idno.value!="")
	{
		alert("请先选择证件类型！");
		fm.idno.value="";
  }
}

//验证证件类型
function checkidtype1()
{
	if(fm.payeeidno.value==""&&fm.payeeidno.value!="")
	{
		alert("请先选择证件类型！");
		fm.payeeidno.value="";
  }
}

//根据身份证号取得出生日期和性别
function getBirthdaySexByIDNo(iIdNo)
{
	if(document.all('idtype').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		{
			alert("输入的身份证号位数错误");
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
		if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		{
			alert("输入的身份证号位数错误");
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




//2009-01-10 zhangzheng 双击下拉框后的响应函数
function afterCodeSelect( cCodeName, Field ) {

    //alert(cCodeName);  
    //alert(Field.name);  

	/**=========================================================================
	 当选择受益人和被保险人关系或领款人与被保险人关系时如果是本人自动带出被保险人的相关信息
	=========================================================================
	**/
	//是否同时回退预付信息
    if(cCodeName=="relation"){
    	
    	//受益人与被保人关系 
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

//    			      prompt("根据受益人和出险人关系查询被保险人信息",strSQL);
    			      
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
    	
    	//领款人与受益人关系
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
    			      //prompt("根据受益人和出险人关系查询被保险人信息",strSQL);
    			      
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
 *  根据身份证号取得出生日期和性别
 *  参数  ：  身份证号
 *  返回值：  无
 *********************************************************************
 */

function getBirthdaySexByIDNo2(iIdNo)
{
	//alert("aafsd");
	if(document.all('idtype').value=="0")
	{
		var tBirthday=getBirthdatByIdNo(iIdNo);
		var tSex=getSexByIDNo(iIdNo);
		if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		{
			alert("输入的身份证号位数错误");
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
		if(tBirthday=="输入的身份证号位数错误"||tSex=="输入的身份证号位数错误")
		{
			alert("输入的身份证号位数错误");
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
