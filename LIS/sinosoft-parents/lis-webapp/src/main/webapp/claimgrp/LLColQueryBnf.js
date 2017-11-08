var showInfo;
var mDebug="1";
var tSaveFlag = "0";
var turnPage = new turnPageClass();

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
}

//检查非空
function checkInput()
{
    if (fm.insuredno.value == "" || fm.insuredno.value == null)
    {
        alert("被保人号码不能为空!");
        return false;
    }
    if (fm.cName.value == "" || fm.cName.value == null)
    {
        alert("受益人姓名不能为空!");
        return false;
    }
    if (fm.payeename.value == "" || fm.payeename.value == null)
    {
        alert("领款人姓名不能为空!");
        return false;
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

//查询赔案保单名细
function queryLLBalanceGrid()
{
    var strSQL = '';
    var arr = '';
    if (fm.BnfKind.value == 'A')
    {
        strSQL = "select ClmNo,polno,sum(pay),GrpContNo,GrpPolNo,ContNo,'0',(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'A') when 0 then 0 else 1 end),(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'A') when 0 then '未分配' else '已分配' end) from LLBalance a where 1=1 "
                   + getWherePart( 'ClmNo','ClmNo' )
                   + " group by ClmNo,polno,GrpContNo,GrpPolNo,ContNo "
                   + " order by ClmNo";
//    alert(strSQL);
        arr = easyExecSql( strSQL );
    }
    else if (fm.BnfKind.value == 'B')
    {
        strSQL = "select ClmNo,polno,abs(sum(pay)),GrpContNo,GrpPolNo,ContNo,'0',(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'B') when 0 then 0 else 1 end),(case (select count(1) from llbnf b where b.ClmNo=a.ClmNo and b.polno=a.polno and BnfKind = 'B') when 0 then '未分配' else '已分配' end) from LLBalance a where 1=1 "
               + getWherePart( 'ClmNo','ClmNo' )
               + " and FeeOperationType = 'B'"
               + " group by ClmNo,polno,GrpContNo,GrpPolNo,ContNo " 
               + " order by ClmNo";
    //    alert(strSQL);
        arr = easyExecSql( strSQL );
    }
    if (arr)
    {
        displayMultiline(arr,LLBalanceGrid);
    }
//    turnPage.queryModal(strSQL, LLBalanceGrid);
}

//查询受益人分配名细
function queryLLBnfGrid()
{
    var i = LLBalanceGrid.getSelNo() - 1;
    initLLBnfGrid();
    //---------------------1-----2--------3-------4--------5------6------7-------8--------9-------10---------11-----------12---13--------14----15---------16----------17----------18----------19---------20--------21------22-----23
    var strSQL1 = "select clmno,polNo,insuredno,bnfno,customerNo,name,payeeNo,payeename,bnftype,bnfgrade,relationtoinsured,sex,birthday,idtype,idno,relationtopayee,payeesex,payeebirthday,payeeidtype,payeeidno,getmoney,bnflot,CasePayMode,BankCode,BankAccNo,AccName from LLBnf where "
                + " clmno = '" + LLBalanceGrid.getRowColData(i,1) + "'"
                + " and PolNo = '" + LLBalanceGrid.getRowColData(i,2) + "'"
                + " and BnfKind = '" + fm.BnfKind.value + "'"
                + " order by clmno";
    var arr1 = easyExecSql( strSQL1 );
    if (arr1)
    {
        displayMultiline(arr1,LLBnfGrid);
//        turnPage.queryModal(strSQL, LLBnfGrid);
    }
//    //设置可操作按钮
//    fm.addButton.disabled = false;
////    fm.updateButton.disabled = true;
//    fm.deleteButton.disabled = false;
//    fm.saveButton.disabled = false;
}

//选中LLBalanceGrid响应事件
function LLBalanceGridClick()
{
    //清空表单
    emptyInput();
	//---------------------------------------------------------------------------------------------beg
	//填充赔案保单名细详细信息
    //---------------------------------------------------------------------------------------------
    var i = LLBalanceGrid.getSelNo();
    if (i != '0')
    {
        i = i - 1;
        fm.ClmNo2.value = LLBalanceGrid.getRowColData(i,1);
        fm.polNo.value = LLBalanceGrid.getRowColData(i,2);
        fm.sumPay.value = LLBalanceGrid.getRowColData(i,3);
        fm.BatNo.value = LLBalanceGrid.getRowColData(i,7)
        
//        //判断受益分配金额
//        if (fm.BnfKind.value == 'A' && fm.sumPay.value < 0)
//        {
//            alert("此保单不能分配受益人!");
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
	//查询理赔受益人账户信息,组成mulline
    //---------------------------------------------------------------------------------------------
    document.all('divLLBalance').style.display="";
    initLLBnfGrid();
    //---------------------1-----2--------3-------4--------5------6------7-------8--------9-------10---------11-----------12---13--------14----15---------16----------17----------18----------19---------20--------21------22-------23
    var strSQL1 = "select clmno,polNo,insuredno,bnfno,customerNo,name,payeeNo,payeename,bnftype,bnfgrade,relationtoinsured,sex,birthday,idtype,idno,relationtopayee,payeesex,payeebirthday,payeeidtype,payeeidno,getmoney,bnflot,CasePayMode,BankCode,BankAccNo,AccName from LLBnf where "
                + " clmno = '" + LLBalanceGrid.getRowColData(i,1) + "'"
                + " and PolNo = '" + LLBalanceGrid.getRowColData(i,2) + "'"
                + " and BnfKind = '" + fm.BnfKind.value + "'"
                + " order by clmno";
    var arr1 = easyExecSql( strSQL1 );
    if (arr1)
    {
        displayMultiline(arr1,LLBnfGrid);
//        turnPage.queryModal(strSQL, LLBnfGrid);
    }
    //---------------------------------------------------------------------------------------------end

//    //设置可操作按钮
//    fm.addButton.disabled = false;
////    fm.updateButton.disabled = true;
//    fm.deleteButton.disabled = false;
//    fm.saveButton.disabled = false;
    
//    if (LLBalanceGrid.getRowColData(i,8) == '0')
//    {
//        //---------------------------------------------------------------------------------------------BEG
//        //非死亡类案件受益人默认为出险人本人,死亡类案件默认为申请人   2005-8-20 14:49 周磊
//        //---------------------------------------------------------------------------------------------
//        
//        //首先判断是不是死亡案件
//        var tDeadFlag = 0;
//        var tSql = "select substr(d.reasoncode,2,2) from llappclaimreason d where "
//                 + " d.caseno = '" + fm.ClmNo.value + "'";
//        var tRcode = easyExecSql( tSql );
//        if (tRcode)
//        {
//            for (var j = 0; j < tRcode.length; j++)
//            {
//                if (tRcode[j] == '02')
//                {
//                    tDeadFlag = 1;
//                    break;
//                }
//            }
//        }
//        
//        var tPerson = new Array;
//        if (tDeadFlag == 0)  //非死亡类案件
//        {
//            var strSQL3 = "select customerno from llcase where caseno = '" + LLBalanceGrid.getRowColData(i,1) + "'";
//            var tCustNo = easyExecSql( strSQL3 );
//            
//            var strSQL4 = "select a.name,a.sex,a.birthday,a.idtype,a.idno,(select b.bankcode from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y'),(select b.bankaccno from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y'),(select b.accname from LCAccount b where b.customerno = a.customerno and b.acckind = 'Y') from ldperson a where 1=1"
//                        + " and a.customerno = '" + tCustNo[0] + "'";
//            tPerson = easyExecSql( strSQL4 );
//            
//            fm.insuredno.value = tCustNo[0];
//            fm.customerNo.value = tCustNo[0];
//            fm.payeeNo.value = tCustNo[0];
//            fm.relationtoinsured.value = '00';
//            fm.relationtopayee.value = '00';
//        }
//        else   //死亡类案件
//        {
//            //-----------------------------------------1-------------------2--------3-------4--------5------6-------7-------8-----9-------10---------11-----------12---13--------14----15--16-17-18-19-20------------------------------21-----------------22----23
//            var strSQL2 = "select '"+LLBalanceGrid.getRowColData(i,1)+"',polNo,insuredno,bnfno,customerNo,name,customerNo,name,bnftype,bnfgrade,relationtoinsured,sex,birthday,idtype,idno,'','','','','',bnflot*" + parseFloat(fm.sumPay.value) + ",bnflot*100,'','','','' from LCBnf where "
//                        + " PolNo = '" + LLBalanceGrid.getRowColData(i,2) + "'"
//                        + " order by BnfNo";
//    //        alert(strSQL2);
//            var arr2 = easyExecSql( strSQL2 ); //先到承保表中查找
//            if (arr2)
//            {
//                displayMultiline(arr2,LLBnfGrid);
//            }
//            else  //承保没有数据,默认为申请人
//            {
//                var strSQL4 = "select a.RgtantName,a.RgtantSex,'',a.IDType,a.IDNo,'0','0','0',Relation from LLRegister a where 1=1"
//                            + " and a.RgtNo = '" + fm.ClmNo.value + "'";
//                tPerson = easyExecSql( strSQL4 );
//                fm.relationtoinsured.value = tPerson[0][5];
//                fm.relationtopayee.value = tPerson[0][5];
//            }
//        }
//
//        if (tPerson != "" && tPerson != null)
//        {
//            fm.polNo.value = LLBalanceGrid.getRowColData(i,2);//
//            fm.cName.value = tPerson[0][0];
//            fm.payeename.value = tPerson[0][0];
//            fm.sex.value = tPerson[0][1];
//            fm.birthday.value = tPerson[0][2];
//            fm.idtype.value = tPerson[0][3];
//            fm.idno.value = tPerson[0][4];
//            fm.payeesex.value = tPerson[0][1];
//            fm.payeebirthday.value = tPerson[0][2];
//            fm.payeeidtype.value = tPerson[0][3];
//            fm.payeeidno.value = tPerson[0][4];
//            fm.getmoney.value = LLBalanceGrid.getRowColData(i,3);
//            fm.bnflot.value = '100';
//            fm.BankCode.value = tPerson[0][5];
//            fm.BankAccNo.value = tPerson[0][6];
//            fm.AccName.value = tPerson[0][7];
//            
//            showOneCodeName('sex','sex','SexName');
//            showOneCodeName('llidtype','idtype','idtypeName');
//            showOneCodeName('llrelationtoinsured','relationtoinsured','relationtoinsuredName');
//            showOneCodeName('llrelationtoinsured','relationtopayee','relationtopayeeName');
//            showOneCodeName('sex','payeesex','payeesexName');
//            showOneCodeName('llidtype','payeeidtype','payeeidtypeName');
//            showOneCodeName('llpaymode','CasePayMode','CasePayModeName');
//            showOneCodeName('bank','BankCode','BankCodeName');
//        }
//        //---------------------------------------------------------------------------------------------END
//    }
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
        showOneCodeName('llrelationtoinsured','relationtoinsured','relationtoinsuredName');
        showOneCodeName('llrelationtoinsured','relationtopayee','relationtopayeeName');
        showOneCodeName('sex','payeesex','payeesexName');
        showOneCodeName('llidtype','payeeidtype','payeeidtypeName');
        showOneCodeName('llpaymode','CasePayMode','CasePayModeName');
        showOneCodeName('bank','BankCode','BankCodeName');
    }
//    //设置可操作按钮
//    fm.updateButton.disabled = false;
}

//增加受益人
function addClick()
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
        alert("分配比例错误!");
        return;
    }
    else
    {
        //计算剩余百分比
        fm.RemainLot.value = 100 - tAll;
        //--------------------------------------------------------------------------------BEG
        //计算受益金额,如果为分配的最后一人，把所有剩余金额都赋给他
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
        //取得序号
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

//修改受益人
function updateClick()
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
        alert("分配比例错误!");
        return;
    }
    else
    {
        //计算剩余百分比
        fm.RemainLot.value = 100 - tAll;
        //--------------------------------------------------------------------------------BEG
        //计算受益金额,如果为分配的最后一人，把所有剩余金额都赋给他
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
    var strSQL = " select CasePayFlag from LLBnf where "
               + " ClmNo = '" + LLBnfGrid.getRowColData(k,1) + "'"
               + " and CaseNo = '" + LLBnfGrid.getRowColData(k,1) + "'"
               + " and BatNo = '" + fm.BatNo.value + "'"
               + " and BnfKind = '" + fm.BnfKind.value + "'"
               + " and PolNo = '" + LLBnfGrid.getRowColData(k,2) + "'"
               + " and InsuredNo = '" + LLBnfGrid.getRowColData(k,3) + "'"
               + " and BnfNo = '" + LLBnfGrid.getRowColData(k,4) + "'"
               + " order by BnfNo";
//    alert(strSQL);
    var arr = easyExecSql( strSQL );
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

//对保项修改保存到后台
function saveClick()
{
    //分配比例100%校验,非空校验
    var i = LLBnfGrid.mulLineCount;
    var tAll = 0;
    for (var j=0; j < i; j++)
    {
        tAll = tAll + parseFloat(LLBnfGrid.getRowColData(j,22));
    }
    if(tAll != 100)
    {
        alert("未完全分配受益金额!");
        return;
    }
    fm.action = './LLBnfSave.jsp';
    submitForm();
}

//公共提交方法
function submitForm()
{
    //提交数据
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

//提交后操作,返回
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

        emptyInput();
        queryLLBalanceGrid();
//        queryLLBnfGrid();
    }
    tSaveFlag ="0";
}
