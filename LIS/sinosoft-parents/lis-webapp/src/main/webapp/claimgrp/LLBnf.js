var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
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
}

//���ǿ�
function checkInput()
{
    if (fm.insuredno.value == "" || fm.insuredno.value == null)
    {
        alert("�����˺��벻��Ϊ��!");
        return false;
    }
    if (fm.cName.value == "" || fm.cName.value == null)
    {
        alert("��������������Ϊ��!");
        return false;
    }
    if (fm.payeename.value == "" || fm.payeename.value == null)
    {
        alert("�������������Ϊ��!");
        return false;
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

//��ѯ�ⰸ������ϸ
function queryLLBalanceGrid()
{
    var strSQL = '';
    var arr = '';
    if (fm.BnfKind.value == 'A')
    {
       /* strSQL = "select ClmNo,polno,sum(pay),GrpContNo,GrpPolNo,ContNo,'0',(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'A') when 0 then 0 else 1 end),(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'A') when 0 then 'δ����' else '�ѷ���' end) from LLBalance a where 1=1 "
                   + getWherePart( 'ClmNo','ClmNo' )
                   + " group by ClmNo,polno,GrpContNo,GrpPolNo,ContNo "
                   + " order by ClmNo";*/
	    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql1");
		mySql.addSubPara(fm.ClmNo.value );                
//    alert(strSQL);
        arr = easyExecSql( mySql.getString() );
    }
    else if (fm.BnfKind.value == 'B')
    {
      /*  strSQL = "select ClmNo,polno,abs(sum(pay)),GrpContNo,GrpPolNo,ContNo,'0',(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'B') when 0 then 0 else 1 end),(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'B') when 0 then 'δ����' else '�ѷ���' end) from LLBalance a where 1=1 "
               + getWherePart( 'ClmNo','ClmNo' )
               + " and FeeOperationType = 'B'"
               + " group by ClmNo,polno,GrpContNo,GrpPolNo,ContNo " 
               + " order by ClmNo";*/
       mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql2");
		mySql.addSubPara(fm.ClmNo.value );  
    //    alert(strSQL);
        arr = easyExecSql( mySql.getString() );
    }
    //prompt("��ѯ�ⰸ������ϸ",strSQL);
    if (arr)
    {
        displayMultiline(arr,LLBalanceGrid);
    }
//    turnPage.queryModal(strSQL, LLBalanceGrid);
}

//��ѯ�����˷�����ϸ
function queryLLBnfGrid()
{
    var i = LLBalanceGrid.getSelNo() - 1;
    initLLBnfGrid();
    //---------------------1-----2--------3-------4--------5------6------7-------8--------9-------10---------11-----------12---13--------14----15---------16----------17----------18----------19---------20--------21------22-----23
   /* var strSQL1 = "select clmno,polNo,insuredno,bnfno,customerNo,name,payeeNo,payeename,bnftype,bnfgrade,relationtoinsured,sex,birthday,idtype,idno,relationtopayee,payeesex,payeebirthday,payeeidtype,payeeidno,getmoney,bnflot,CasePayMode,BankCode,BankAccNo,AccName from LLBnf where "
                + " clmno = '" + LLBalanceGrid.getRowColData(i,1) + "'"
                + " and PolNo = '" + LLBalanceGrid.getRowColData(i,2) + "'"
                + " and BnfKind = '" + fm.BnfKind.value + "'"
                + " order by clmno";*/
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql3");
		mySql.addSubPara(LLBalanceGrid.getRowColData(i,1)); 
		mySql.addSubPara(LLBalanceGrid.getRowColData(i,2));  
		mySql.addSubPara(fm.BnfKind.value );              
    var arr1 = easyExecSql( mySql.getString() );
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
	//---------------------------------------------------------------------------------------------beg
	//����ⰸ������ϸ��ϸ��Ϣ
    //---------------------------------------------------------------------------------------------
    var i = LLBalanceGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo2.value = LLBalanceGrid.getRowColData(i,1);
        fm.polNo.value = LLBalanceGrid.getRowColData(i,2);
        fm.sumPay.value = LLBalanceGrid.getRowColData(i,3);
        fm.BatNo.value = LLBalanceGrid.getRowColData(i,7)
        
//        //�ж����������
//        if (fm.BnfKind.value == 'A' && fm.sumPay.value < 0)
//        {
//            alert("�˱������ܷ���������!");
//            disabledButton();
//            fm.goBack.disabled = false;
//            return;
//        }
        if (LLBalanceGrid.getRowColData(i,4) == "" || LLBalanceGrid.getRowColData(i,4) == null)
        {
            fm.GrpContNo.value = '00000000000000000000';
        }
        else
        {
            fm.GrpContNo.value = LLBalanceGrid.getRowColData(i,4);
        }
        if (LLBalanceGrid.getRowColData(i,5) == "" || LLBalanceGrid.getRowColData(i,5) == null)
        {
            fm.GrpPolNo.value = '00000000000000000000';
        }
        else
        {
            fm.GrpPolNo.value = LLBalanceGrid.getRowColData(i,5);
        }
        if (LLBalanceGrid.getRowColData(i,6) == "" || LLBalanceGrid.getRowColData(i,6) == null)
        {
            fm.ContNo.value = '00000000000000000000';
        }
        else
        {
            fm.ContNo.value = LLBalanceGrid.getRowColData(i,6);
        }
    }
    //---------------------------------------------------------------------------------------------end

	//---------------------------------------------------------------------------------------------beg
	//��ѯ�����������˻���Ϣ,���mulline
    //---------------------------------------------------------------------------------------------
    document.all('divLLBalance').style.display="";
    initLLBnfGrid();
    //---------------------1-----2--------3-------4--------5------6------7-------8--------9-------10---------11-----------12---13--------14----15---------16----------17----------18----------19---------20--------21------22-------23
  /*  var strSQL1 = "select clmno,polNo,insuredno,bnfno,customerNo,name,payeeNo,payeename,bnftype,bnfgrade,relationtoinsured,sex,birthday,idtype,idno,relationtopayee,payeesex,payeebirthday,payeeidtype,payeeidno,getmoney,bnflot,CasePayMode,BankCode,BankAccNo,AccName from LLBnf where "
                + " clmno = '" + LLBalanceGrid.getRowColData(i,1) + "'"
                + " and PolNo = '" + LLBalanceGrid.getRowColData(i,2) + "'"
                + " and BnfKind = '" + fm.BnfKind.value + "'"
                + " order by clmno";*/
    mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql4");
		mySql.addSubPara(LLBalanceGrid.getRowColData(i,1)); 
		mySql.addSubPara(LLBalanceGrid.getRowColData(i,2));  
		mySql.addSubPara(fm.BnfKind.value );              
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
    
    if (LLBalanceGrid.getRowColData(i,8) == '0')
    {
        //---------------------------------------------------------------------------------------------BEG
        //�������స��������Ĭ��Ϊ�����˱���,�����స��Ĭ��Ϊ������   2005-8-20 14:49 ����
        //---------------------------------------------------------------------------------------------
        
        //�����ж��ǲ�����������
        var tDeadFlag = 0;
        /*var tSql = "select substr(d.reasoncode,2,2) from llappclaimreason d where "
                 + " d.caseno = '" + fm.ClmNo.value + "'";*/
        mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLBnfInputSql");
		mySql.setSqlId("LLBnfSql5");
		mySql.addSubPara(fm.ClmNo.value );   
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
           // var strSQL3 = "select customerno from llcase where caseno = '" + LLBalanceGrid.getRowColData(i,1) + "'";
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLBnfInputSql");
			mySql.setSqlId("LLBnfSql6");
			mySql.addSubPara(LLBalanceGrid.getRowColData(i,1) );  
	            var tCustNo = easyExecSql( mySql.getString() );
            
            /*var strSQL4 = "select a.name,a.sex,a.birthday,a.idtype,a.idno,(select b.bankcode from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y'),(select b.bankaccno from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y'),(select b.accname from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y') from ldperson a where 1=1"
                        + " and a.customerno = '" + tCustNo[0] + "'";*/
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLBnfInputSql");
			mySql.setSqlId("LLBnfSql7");
			mySql.addSubPara( tCustNo[0] );  
            tPerson = easyExecSql( mySql.getString() );
            
            fm.insuredno.value = tCustNo[0];
            fm.customerNo.value = tCustNo[0];
            fm.payeeNo.value = tCustNo[0];
            fm.relationtoinsured.value = '00';
            fm.relationtopayee.value = '00';
        }
        else   //�����స��
        {
            //-----------------------------------------1-------------------2--------3-------4--------5------6-------7-------8-----9-------10---------11-----------12---13--------14----15--16-17-18-19-20------------------------------21-----------------22----23
            /*var strSQL2 = "select '"+LLBalanceGrid.getRowColData(i,1)+"',polNo,insuredno,bnfno,customerNo,name,customerNo,name,bnftype,bnfgrade,relationtoinsured,sex,birthday,idtype,idno,'','','','','',bnflot*" + parseFloat(fm.sumPay.value) + ",bnflot*100,'','','','' from LCBnf where "
                        + " PolNo = '" + LLBalanceGrid.getRowColData(i,2) + "'"
                        + " order by BnfNo";*/
            mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLBnfInputSql");
			mySql.setSqlId("LLBnfSql8");
			mySql.addSubPara(LLBalanceGrid.getRowColData(i,1));  
			mySql.addSubPara( parseFloat(fm.sumPay.value) );  
			mySql.addSubPara( LLBalanceGrid.getRowColData(i,2) );              
    //        alert(strSQL2);
            var arr2 = easyExecSql( mySql.getString() ); //�ȵ��б����в���
            if (arr2)
            {
                displayMultiline(arr2,LLBnfGrid);
            }
            else  //�б�û������,Ĭ��Ϊ������
            {
                /*var strSQL4 = "select a.RgtantName,a.RgtantSex,'',a.IDType,a.IDNo,'0','0','0',Relation from LLRegister a where 1=1"
                            + " and a.RgtNo = '" + fm.ClmNo.value + "'";*/
                 mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLBnfInputSql");
			mySql.setSqlId("LLBnfSql9");
			mySql.addSubPara( fm.ClmNo.value);  
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
            fm.getmoney.value = LLBalanceGrid.getRowColData(i,3);
            fm.bnflot.value = '100';
            fm.BankCode.value = tPerson[0][5];
            fm.BankAccNo.value = tPerson[0][6];
            fm.AccName.value = tPerson[0][7];
            
            showOneCodeName('sex','sex','SexName');
            showOneCodeName('llidtype','idtype','idtypeName');
            showOneCodeName('relation','relationtoinsured','relationtoinsuredName');
            showOneCodeName('relation','relationtopayee','relationtopayeeName');
            showOneCodeName('sex','payeesex','payeesexName');
            showOneCodeName('llidtype','payeeidtype','payeeidtypeName');
            showOneCodeName('llpaymode','CasePayMode','CasePayModeName');
            showOneCodeName('bank','BankCode','BankCodeName');
        }
        //---------------------------------------------------------------------------------------------END
    }
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
        
        showOneCodeName('sex','sex','SexName');
        showOneCodeName('llidtype','idtype','idtypeName');
        showOneCodeName('relation','relationtoinsured','relationtoinsuredName');
        showOneCodeName('relation','relationtopayee','relationtopayeeName');
        showOneCodeName('sex','payeesex','payeesexName');
        showOneCodeName('llidtype','payeeidtype','payeeidtypeName');
        showOneCodeName('llpaymode','CasePayMode','CasePayModeName');
        showOneCodeName('bank','BankCode','BankCodeName');
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
    for (var j=0; j < i; j++)
    {
        tAll = tAll + parseFloat(LLBnfGrid.getRowColData(j,22));
    }
    if (fm.bnflot.value == "" || fm.bnflot.value == null)
    {
        fm.bnflot.value = 0;
    }
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
            fm.getmoney.value = mathRound(parseFloat(fm.sumPay.value) - tAllMoney);
        }
        else
        {
            fm.getmoney.value = mathRound(tBnfLot*0.01*parseFloat(fm.sumPay.value));
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
        LLBnfGrid.addOne();
        LLBnfGrid.setRowColData(i,1,fm.ClmNo.value);//
        LLBnfGrid.setRowColData(i,2,fm.polNo.value);//
        LLBnfGrid.setRowColData(i,3,fm.insuredno.value);//
        LLBnfGrid.setRowColData(i,4,fm.bnfno.value);//
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
    for (var j=0; j < i; j++)
    {
        if (j != k)
        {
            tAll = tAll + parseFloat(LLBnfGrid.getRowColData(j,22));
        }
    }
    if (fm.bnflot.value == "" || fm.bnflot.value == null)
    {
        fm.bnflot.value = 0;
    }
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
                if (l != k)
                {
                    tAllMoney = tAllMoney + parseFloat(LLBnfGrid.getRowColData(l,21));
                }
//                alert(tAllMoney);
            }
            fm.getmoney.value = mathRound(parseFloat(fm.sumPay.value) - tAllMoney);
        }
        else
        {
            fm.getmoney.value = mathRound(tBnfLot*0.01*parseFloat(fm.sumPay.value));
        }
        //--------------------------------------------------------------------------------END
        LLBnfGrid.setRowColData(k,1,fm.ClmNo.value);//
        LLBnfGrid.setRowColData(k,2,fm.polNo.value);//
        LLBnfGrid.setRowColData(k,3,fm.insuredno.value);//
        LLBnfGrid.setRowColData(k,4,fm.bnfno.value);//
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
   /* var strSQL = " select CasePayFlag from LLBnf where "
               + " ClmNo = '" + LLBnfGrid.getRowColData(k,1) + "'"
               + " and CaseNo = '" + LLBnfGrid.getRowColData(k,1) + "'"
               + " and BatNo = '" + fm.BatNo.value + "'"
               + " and BnfKind = '" + fm.BnfKind.value + "'"
               + " and PolNo = '" + LLBnfGrid.getRowColData(k,2) + "'"
               + " and InsuredNo = '" + LLBnfGrid.getRowColData(k,3) + "'"
               + " and BnfNo = '" + LLBnfGrid.getRowColData(k,4) + "'"
               + " order by BnfNo";*/
   			mySql = new SqlClass();
			mySql.setResourceName("claimgrp.LLBnfInputSql");
			mySql.setSqlId("LLBnfSql10");
			mySql.addSubPara(LLBnfGrid.getRowColData(k,1) );    
			mySql.addSubPara(LLBnfGrid.getRowColData(k,1));  
			mySql.addSubPara( fm.BatNo.value);  
			mySql.addSubPara( fm.BnfKind.value);  
			mySql.addSubPara( LLBnfGrid.getRowColData(k,2));  
			mySql.addSubPara(LLBnfGrid.getRowColData(k,3) );  
			mySql.addSubPara( LLBnfGrid.getRowColData(k,4));            
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

//�Ա����޸ı��浽��̨
function saveClick()
{
    //�������100%У��,�ǿ�У��
    var i = LLBnfGrid.mulLineCount;
    var tAll = 0;
    for (var j=0; j < i; j++)
    {
        tAll = tAll + parseFloat(LLBnfGrid.getRowColData(j,22));
    }
    if(tAll != 100)
    {
        alert("δ��ȫ����������!");
        return;
    }
    fm.action = './LLBnfSave.jsp';
    submitForm();
}

//�����ύ����
function submitForm()
{
    //�ύ����
    var i = 0;
    var showStr = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
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
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();


        emptyInput();
        queryLLBalanceGrid();
//        queryLLBnfGrid();
    }
    tSaveFlag ="0";
}
