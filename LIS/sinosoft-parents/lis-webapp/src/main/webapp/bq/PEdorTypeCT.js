var showInfo;

var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var sqlresourcename = "bq.PEdorTypeCTInputSql";

function getCustomerActBnfGrid()
{
    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
//   	var sql="select name,sex,birthday,idtype,idno,BnfLot,Remark,bankcode,BankAccNo,AccName from lpbnf where edorno in (select edorno from lpedoritem where edoracceptno='"+document.all('EdorAcceptNo').value+"')";
    	
    var sql = "";
	var sqlid1="PEdorTypeCTInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(document.all('EdorAcceptNo').value);//ָ������Ĳ���
	sql=mySql1.getString();
    	
    	arrResult = easyExecSql(sql);
    	if(arrResult)
    	{
    		displayMultiline(arrResult,CustomerActBnfGrid);
    		showPageOnly(this,divBackFeeTotal);
    	}
//    	else
//    	{
//	        var strSQL = "select a.appntname,appntsex,"
//	        	+"  a.appntbirthday,idtype"
//	        	+"  ,a.idno,"
//	        	//+"(select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'bnftype' and trim(m.code) = trim(bnftype)), "
//	        	//+"  relationtoinsured,"
//	        	//+"  (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'bnforder' and trim(m.code) = trim(a.bnfgrade)),"
//	        	+"'','' ,a.bankcode,a.BankAccNo,a.AccName from lcappnt "
//	        	+"a where contno='"+tContNo+"' ";
//	        
//	        arrResult = easyExecSql(strSQL);
//	        if (arrResult)
//	        {
//	            displayMultiline(arrResult,CustomerActBnfGrid);
//	        }
//    	}
    }
}

function getPolGrid(tContNo)
{
    var EdorAppDate = fm.EdorItemAppDate.value;
    var tContno=document.all('ContNo').value;
    //alert(tContno);
/*    var strSQL = " select RiskCode," +
                 " (select RiskName from LMRisk where LMRisk.RiskCode = c.RiskCode)," +
                 " InsuredName, Amnt, " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "'  and p.polno = c.polno and p.payplantype in ('0')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "'  and p.polno = c.polno and p.payplantype in ('01', '03')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "'  and p.polno = c.polno and p.payplantype in ('02', '04')), 0), " +
                 " polno,CValiDate,PaytoDate,mainpolno " +
                 " from LCPol c where ContNo='" + tContNo + "' and appflag = '1'" +
                 " union" +
                 " select RiskCode," +
                 " (select RiskName from LMRisk where LMRisk.RiskCode = c.RiskCode)," +
                 " InsuredName, Amnt, " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "'  and p.polno = c.polno and p.payplantype in ('0')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "'  and p.polno = c.polno and p.payplantype in ('01', '03')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "' and payenddate >= '" + EdorAppDate + "' and p.polno = c.polno and p.payplantype in ('02', '04')), 0), " +
                 " polno,cvalidate,paytodate,mainpolno" 
                 + " from LCPol c where 1=1"
                 + " and (select count(*) from lccontstate s where trim(statetype) in('Terminate') and trim(state) = '1' and trim(StateReason) in ('07') and ((startdate <= '" + EdorAppDate + "' and '" + EdorAppDate + "' <= enddate )or(startdate <= '" + EdorAppDate + "' and enddate is null ))and s.polno = c.polno) > 0"
                 + " and appflag = '4' and contno = '" + tContNo + "'";
    //alert(strSQL);
    //prompt("",strSQL);
*/    
    var strSQL = "";
	var sqlid2="PEdorTypeCTInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(EdorAppDate);//ָ������Ĳ���
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(tContNo);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(tContNo);
	strSQL=mySql2.getString();
    
    brrResult = easyExecSql(strSQL);
    if (brrResult)
    {
        displayMultiline(brrResult,PolGrid);
    }
}

function getCustomerGrid()
{

    var tContNo=document.all("ContNo").value;
    if(tContNo!=null&&tContNo!="")
    {
/*        var strSQL = " Select a.appntno, 'Ͷ����', a.appntname, "
                    +" (select trim(n.code)||'-'||trim(n.CodeName) from LDCode n where trim(n.codetype) = 'sex' and trim(n.code) = trim(appntsex)),"
                    +" a.appntbirthday, "
                    +" (select trim(m.code)||'-'||trim(m.CodeName) from LDCode m where trim(m.codetype) = 'idtype' and trim(m.code) = trim(idtype)), "
                    +" a.idno,(select '�뱻���˵Ĺ�ϵ--'||codename from ldcode where codetype = 'relation' and code = a.relationtoinsured) "
                    +" From lcappnt a Where contno='" + tContNo+"'"
                    +" Union"
                    +" Select i.insuredno, '������', i.name, "
                    +" (select trim(u.code)||'-'||trim(u.CodeName) from LDCode u where trim(u.codetype) = 'sex' and trim(u.code) = trim(sex)),"
                    +" i.Birthday,"
                    +" (select trim(y.code)||'-'||trim(y.CodeName) from LDCode y where trim(y.codetype) = 'idtype' and trim(y.code) = trim(idtype)), "
                    +" i.IDNo,(select '��Ͷ���˵Ĺ�ϵ--'||codename from ldcode where codetype = 'relation' and code = i.relationtoappnt) "
                    +" From lcinsured i Where contno='" + tContNo+"'";
*/        
    var strSQL = "";
	var sqlid3="PEdorTypeCTInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tContNo);//ָ������Ĳ���
	mySql3.addSubPara(tContNo);
	strSQL=mySql3.getString();
        
        arrResult = easyExecSql(strSQL);
        if (arrResult)
        {
            displayMultiline(arrResult,CustomerGrid);
        }
    }
}

function chkPol()
{
    var tContNo   = document.all('ContNo').value;
    var tEdorNo   = document.all('EdorNo').value;
    var tEdorType = document.all('EdorType').value;
/*    var strSQL = " select polno from lppol " +
                 " where edorno='" + tEdorNo +
                 "' and edortype='" + tEdorType +
                 "' and contno='"+tContno+"'";
*/    
    var strSQL = "";
	var sqlid4="PEdorTypeCTInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(tEdorNo);//ָ������Ĳ���
	mySql4.addSubPara(tEdorType);
	mySql4.addSubPara(tContNo);
	strSQL=mySql4.getString();
    
    var arrResult2=easyExecSql(strSQL);
    var m=0;
    var n=0;

    if(arrResult2!=null)
    {
        var q = arrResult2.length;
        for(m = 0; m < PolGrid.mulLineCount; m++)
        {
            for(n = 0; n < q; n++)
           {
                if(PolGrid.getRowColData(m, 9) == arrResult2[n][0])
                {
                    PolGrid.checkBoxSel(m+1);
                }
            }
        }
        //alert("aa");
        showPageOnly(this,divAppnt);
    }
}


function getContZTInfo()
{
    var MainPolNo;
    var MainPayIntv;
    var MainPayToDate;
    var MainPayEndDate;
    var MainRiskCode;
/*    strSQL = " select polno, paytodate, payintv, payenddate, riskcode from lcPol " +
             " where contno = '" + fm.ContNo.value +
             "' and polno = mainpolno";
*/    
	var sqlid5="PEdorTypeCTInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strSQL=mySql5.getString();
    
    var hrr = easyExecSql(strSQL);
    if ( hrr )
    {
        hrr[0][0]==null||hrr[0][0]=='null'?'0':MainPolNo = hrr[0][0];
        hrr[0][1]==null||hrr[0][1]=='null'?'0':MainPayToDate = hrr[0][1];
        hrr[0][2]==null||hrr[0][2]=='null'?'0':MainPayIntv = hrr[0][2];
        hrr[0][3]==null||hrr[0][3]=='null'?'0':MainPayEndDate = hrr[0][3];
        hrr[0][4]==null||hrr[0][4]=='null'?'0':MainRiskCode = hrr[0][4];
    }
    else
    {
        alert("��������ʧ��");
        return;
    }

/*    strSQL = " select customgetpoldate, ReceiveDate, cvalidate from lccont " +
             " where contno = '" + fm.ContNo.value + "' ";
*/    
    var sqlid6="PEdorTypeCTInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strSQL=mySql6.getString();
    
    var drr = easyExecSql(strSQL);

    if ( drr )
    {
        drr[0][0]==null||drr[0][0]=='null'?'0':fm.CustomGetPolDate.value = drr[0][0];
        //drr[0][1]==null||drr[0][1]=='null'?'0':fm.ReceiveDate.value = drr[0][1];
        drr[0][2]==null||drr[0][2]=='null'?'0':fm.CvaliDate.value = drr[0][2];

/*        strSQL = " select count(*) from lccontstate  " +
                 " where statetype in('Available') and state = '1' " +
                 " and  ( (startdate <= '" + fm.EdorItemAppDate.value + "' and enddate >= '" + fm.EdorItemAppDate.value + "' ) " +
                 " or (startdate <= '" + fm.EdorItemAppDate.value + "' and enddate is null )) " +
                 " and polno = '" + MainPolNo + "' ";
*/        
    var sqlid7="PEdorTypeCTInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(fm.EdorItemAppDate.value);//ָ������Ĳ���
	mySql7.addSubPara(fm.EdorItemAppDate.value);
	mySql7.addSubPara(fm.EdorItemAppDate.value);
	mySql7.addSubPara(MainPolNo);
	strSQL=mySql7.getString();
        
        var frr = easyExecSql(strSQL);
        var CalDate;
        if ( frr )
        {
            frr[0][0]==null||frr[0][0]=='null'?'0':disAvailable = frr[0][0];
            //alert(disAvailable);
            //�ж������Ƿ�ʧЧ
            if (disAvailable == 0)
            {
                //û��ʧЧ

                //�жϽ�������

                var intv = dateDiffCT(fm.EdorItemAppDate.value, MainPayToDate, "D");
                //alert(intv);
                if (intv > 0)
                {
                    CalDate = fm.EdorItemAppDate.value;
                }
                else
                {
                    if (MainPayIntv == 0 || MainPayToDate == MainPayEndDate)
                    {
                        CalDate = fm.EdorItemAppDate.value;
                    }
                    else
                    {
                        CalDate = MainPayToDate;
                    }
                }

            }
            else
            {
                CalDate = MainPayToDate;
            }
        }

/*        strSQL = " select riskcode from lmrisksort " +
                 " where riskcode = '" + MainRiskCode +
                 "' and risksorttype = '30' ";
*/       
    var sqlid8="PEdorTypeCTInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	mySql8.addSubPara(MainRiskCode);//ָ������Ĳ���
	strSQL=mySql8.getString();
        
        var srr = easyExecSql(strSQL);
        if ( srr )  //����ǲ����ڽ��ѣ����������ʾʵ���ѹ����
        {
            CalDate = fm.EdorItemAppDate.value;
        }
//				strSQL = "select floor(months_between('"+CalDate+"','"+fm.CvaliDate.value+"')) from dual"
        
    var sqlid9="PEdorTypeCTInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	mySql9.addSubPara(CalDate);//ָ������Ĳ���
	mySql9.addSubPara(fm.CvaliDate.value);
	strSQL=mySql9.getString();
        
        srr = easyExecSql(strSQL);
        if(srr){
        	var intval = srr[0][0];
					//alert(intval);
        	var year = (intval- intval%12)/12;
        	var month = intval%12;
        	fm.Inteval.value = year + " �� �� " + month + " ��";
				}
        if (MainPayIntv == 0)
        {
            //alert("����");
            //�ж����޸�����
/*            strSQL = " select paytodate from lcPol " +
                     " where contno = '" + fm.ContNo.value +
                     "' and polno <> mainpolno and rownum = 1 and appflag = '1' ";
*/            
    var sqlid10="PEdorTypeCTInputSql10";
	var mySql10=new SqlClass();
	mySql10.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql10.setSqlId(sqlid10);//ָ��ʹ�õ�Sql��id
	mySql10.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strSQL=mySql10.getString();
            
            var mrr = easyExecSql(strSQL);
            if ( mrr )
            {
                mrr[0][0]==null||mrr[0][0]=='null'?'0':fm.PayToDate.value = mrr[0][0];
            }
            else
            {
                fm.PayToDate.value = "";
            }
        }
        else
        {
            //alert("�ڽ�");
            //�ж�����
            //alert(MainPayEndDate);
            //alert(MainPayToDate);
            if (MainPayEndDate == MainPayToDate)
            {
                //�ж����޸�����
/*                strSQL = " select paytodate from lcPol " +
                         " where contno = '" + fm.ContNo.value +
                         "' and polno <> mainpolno and rownum = 1  and appflag = '1' ";
*/               
    var sqlid11="PEdorTypeCTInputSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
	mySql11.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strSQL=mySql11.getString();
                
                var mrr = easyExecSql(strSQL);
                if ( mrr )
                {
                    mrr[0][0]==null||mrr[0][0]=='null'?'0':fm.PayToDate.value = mrr[0][0];
                }
                else
                {
                    fm.PayToDate.value = "";
                }
            }
            else
            {
                fm.PayToDate.value = MainPayToDate;
            }
        }
    }
}

function edorActBnfSave()
{
	//var row = BackFeeDetailGrid.mulLineCount;
	//alert("row:"+fm.BackFeeGetMoney.value);
	CustomerActBnfGrid.delBlankLine();
//	if(!CustomerActBnfGrid.checkValue2())
//	{
//		  return false;
//	}
	var row = CustomerActBnfGrid.mulLineCount;
	if(row<=0)
	{
		alert("δ��дʵ����ȡ�ˣ�");
		return false;
	}
	var money = 0.0;
	for(var i=0;i<row;i++)
	{
			var tIDType=CustomerActBnfGrid.getRowColData(i,4);
		var tIDNo=CustomerActBnfGrid.getRowColData(i,5);
		if(tIDType=="0")
		{
		   tBirthday=getBirthdatByIdNo(tIDNo);
		   tSex=getSexByIDNo(tIDNo);
		   if(tBirthday=="��������֤��λ������"||tSex=="��������֤��λ������")
		   {
		   	  str="��"+(i+1)+"�����֤��λ���������!!!" ;
		   	  return false;
		   }
		   else
		   {  
			   CustomerActBnfGrid.setRowColData(i,3,tBirthday) ;
			   CustomerActBnfGrid.setRowColData(i,2,tSex) ;
		   }
	    }
		if(CustomerActBnfGrid.getRowColData(i,7)!="1"&&CustomerActBnfGrid.getRowColData(i,7)!="4"&&CustomerActBnfGrid.getRowColData(i,7)!="9")
		{
			alert("ʵ����ȡ���б��е�"+(i+1)+"����ȡ��ʽ����ȷ��������ѡ��");
			return false;
		}
		
		if(CustomerActBnfGrid.getRowColData(i,7)=="4" || CustomerActBnfGrid.getRowColData(i,7)=="9")
		{
			if(CustomerActBnfGrid.getRowColData(i,8)==null||CustomerActBnfGrid.getRowColData(i,8)==""||
					CustomerActBnfGrid.getRowColData(i,9)==null||CustomerActBnfGrid.getRowColData(i,9)==""||
					CustomerActBnfGrid.getRowColData(i,10)==null||CustomerActBnfGrid.getRowColData(i,10)=="")
			{
				alert("ʵ����ȡ���б��е�"+(i+1)+"����ȡ��ʽΪ����ת�˻��������������Ա�����д���б��롢�����˺ź������ʻ���");
				return false;
			}
			//if(CustomerActBnfGrid.getRowColData(i,9))
						//����ת�ʻ����ϼ��������ʺŹ���У��
			if(!checkBank(CustomerActBnfGrid.getRowColData(i,8),CustomerActBnfGrid.getRowColData(i,9)))
			{
			  //CustomerActBnfGrid.setRowColData(i,8,"");
			  CustomerActBnfGrid.setRowColData(i,9,"");
			  return false;
			}
		}
		money+=parseFloat(CustomerActBnfGrid.getRowColData(i,6))*100;
	}
//	if(money!=parseFloat(fm.BackFeeGetMoney.value))
//	{
//		alert("ʵ����ȡ���ϼƲ����ڱ��θ����ܽ�")
//		return false;
//	}
	 if(money!=100)
	  {
		  alert("ʵ����ȡ����ȡ�����ϼƲ���100%��������¼����ȡ������");
		  return false;
	  }

	fm.fmtransact.value="EDORITEM|UPDATE";
	//alert(fm.fmtransact.value);
	  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();	  
		document.getElementById("fm").submit();
}


//add by jiaqiangli 2009-06-23
function GetBankCode(spanID) {
	if (document.all(spanID).all('CustomerActBnfGrid7').value == null || document.all(spanID).all('CustomerActBnfGrid7').value == "")
		return;
	//����ת�� ldbank �����нӿ�ǩ������Э���
	if (document.all(spanID).all('CustomerActBnfGrid7').value == "4") {
		showCodeList('bank',[document.all(spanID).all('CustomerActBnfGrid8')],null,null,null,null,"1",null,"1");
	}
	//�������� ldcode
	else if (document.all(spanID).all('CustomerActBnfGrid7').value == "9") {
		//finabank
		showCodeList('finabank',[document.all(spanID).all('CustomerActBnfGrid8')],null,null,null,null,"1",null,"1");
	}		
}
//add by jiaqiangli 2009-06-23

function edorTypeCTSave()
{

    var row = PolGrid.mulLineCount;
    //�����ж���ѡ�������˱�ʱ�����ѡ�����գ���Ҫ�󽫸�����һ��ѡ��
    var tFlag=false;
    var i = 0;
      for(var m = 0; m < row; m++ )
      {
        if(PolGrid.getChkNo(m))
        {
        	var tmpPolNo = PolGrid.getRowColData(m,9);
         if(isMainPolno(tmpPolNo))
         {
        	tFlag=true;
         }
         //add by sunsx У��ֺ������Ƿ���δ������� 2010-01-27
//         var tChkBonusSql = "select distinct 1 from lcpol a where polno = '"+tmpPolNo+"' and Exists (select 1 from lmriskapp b where a.riskcode = b.riskcode and bonusflag = '1') and ((sysdate >= paytodate and floor(months_between(paytodate, cvalidate) / 12) > (select count(1) from lobonuspol where polno = a.polno and bonusflag = '1')) or (sysdate < paytodate and floor(months_between(sysdate, cvalidate) / 12) > (select count(1) from lobonuspol where polno = a.polno and bonusflag = '1')))";
    
    var tChkBonusSql = "";     
    var sqlid12="PEdorTypeCTInputSql12";
	var mySql12=new SqlClass();
	mySql12.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
	mySql12.addSubPara(tmpPolNo);//ָ������Ĳ���
	tChkBonusSql=mySql12.getString();
         
         var tArr = easyExecSql(tChkBonusSql, 1, 0, 1);
         if (tArr != null)
				 {
		  		 var tBonusRet = tArr[0][0]; 
		  		 if(tBonusRet == "1")
		  		 {
		  		 		alert("ѡ�е��˱����֣�"+PolGrid.getRowColData(m,2)+"����δ�������,�����˱���");
		  		 		return;
		  		 }
				 }
         i = i+1;
        }
      }
      if(i == 0)
      {
        alert("��ѡ����Ҫ�˱������֣�");
        return false;
      }
      //
      var tNoChek=false;
      for(var k = 0; k < row;k++ )
      {
        if(!PolGrid.getChkNo(k))
        {
         if(!isMainPolno(PolGrid.getRowColData(k,9)))
         {
          tNoChek=true;
         }
       }
      }
      if(tNoChek&&tFlag)
      {
        alert("�Ѿ�ѡ�����գ���ѡ�񸽼���һ���˱�");
        return false;
      }
     //�ж��������˻��ǲ���������
     if(tFlag)
     {
     	fm.WTContFLag.value="1";
     }else
     	{
     	fm.WTContFLag.value="0";
     		}

    if (fm.SurrReason.value == null || fm.SurrReason.value == "")
    {
        alert("��¼���˱�ԭ��!");
        return;
    }
    if(fm.SurrReason.value=='06')
    {
    	divRemarkInfo.style.display="block";
    	fm.SurrReasonName.value=fm.Remark.value;
    	}
    //add by sunsx 2008-08-26 �в�����¼��У��	
    if(fm.TrueLostTimes.value > 0 )
    	{
    		if(fm.LostTimes.value == null || fm.LostTimes.value == "")
    		{
    			alert("�ñ����в�����¼����¼�벹����¼��");
    			fm.LostTimes.focus();
    			return;
    		}
    		
    		if(fm.LostTimes.value != fm.TrueLostTimes.value)
    		{
    			alert("����Ĳ�����������ȷ���ʵ��");
    			return;
    		}
    		//return;
    	}

    //<!-- XinYQ modified on 2006-03-01 : ���������˱�ʱ��ʾ������ӡ���� : BGN -->
    //if (!checkIsReissue()) return; //sunsx modified on 2008-08-26
    //<!-- XinYQ modified on 2006-03-01 : ���������˱�ʱ��ʾ������ӡ���� : END -->

  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.getElementById("fm").submit();
}
function showInfo()
{
	    //alert(12);
	    if(fm.SurrReason.value=='06')
    {
    	divRemarkInfo.style.display="block";
    	fm.SurrReasonName.value=fm.Remark.value;
    	}
	}

function queryLRInfo()
{
//		var tSQL = "select c.losttimes from lccont c where c.contno ='"+fm.ContNo.value+"'";
		
	var tSQL = "";     
    var sqlid13="PEdorTypeCTInputSql13";
	var mySql13=new SqlClass();
	mySql13.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
	mySql13.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	tSQL=mySql13.getString();
		
		var ret = easyExecSql(tSQL);
		if(ret)
		{
			var tLostTimes = ret[0][0];
			if(tLostTimes > 0)
			{
				fm.TrueLostTimes.value = tLostTimes;
				divLRInfo.style.display="";
			}
			else
			{
				fm.TrueLostTimes.value = 0;
			}
		}
}
//<!-- XinYQ added on 2006-03-01 : ���������˱�ʱ��ʾ������ӡ���� : BGN -->
/*============================================================================*/

/**
 * ����Ƿ��ǲ�����ӡ����
 */
function checkIsReissue()
{
    var QuerySQL, arrResult;
    //��ѯ�Ƿ�������������
/*    QuerySQL = "select 'X' "
             +   "from LPEdorItem "
             +  "where 1 = 1 "
             +  getWherePart("ContNo", "ContNo")
             +    "and EdorType = 'LR' "
             +    "and EdorState = '0'";
    //alert(QuerySQL);
*/      
    var sqlid14="PEdorTypeCTInputSql14";
	var mySql14=new SqlClass();
	mySql14.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
	mySql14.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	QuerySQL=mySql14.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL, 1, 0);
    }
    catch (ex)
    {
        alert("���棺��ѯ�Ƿ������������������쳣�� ");
        return true;
    }
    if (arrResult != null)
    {
        var sConfirmMsg = "�ñ�������������";
        //��ѯ����������ӡ����
/*        QuerySQL = "select distinct max(MakeDate) "
                 +   "from LDContInvoiceMap "
                 +  "where 1 = 1 "
                 +  getWherePart("ContNo", "ContNo")
                 +    "and OperType = '4'";
        //alert(QuerySQL);
*/        
    var sqlid15="PEdorTypeCTInputSql15";
	var mySql15=new SqlClass();
	mySql15.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
	mySql15.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	QuerySQL=mySql15.getString();
        
        try
        {
            arrResult = easyExecSql(QuerySQL, 1, 0);
        }
        catch (ex)
        {
            alert("���棺��ѯ���������³����Ĵ�ӡ���ڳ����쳣�� ");
            return true;
        }
        if (arrResult != null && trim(arrResult[0][0]) != "")
        {
            sConfirmMsg += "���һ�δ�ӡ����Ϊ " + trim(arrResult[0][0]) + "��";
        }
        sConfirmMsg += "�Ƿ�����˱��� ";
        //ȷ����ʾ�˱�
        if (!confirm(sConfirmMsg))
        {
            return false;
        }
    }
    return true;
}

/*============================================================================*/
//<!-- XinYQ added on 2006-03-01 : ���������˱�ʱ��ʾ������ӡ���� : END -->


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{

    showInfo.close();
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    if (FlagStr == "Succ")
    {
        try
        {
            chkPol();
            getZTMoney();
            queryBackFee();
            top.opener.getEdorItemGrid();
            getCustomerActBnfGrid();
        } catch (ex) {}
    }
}


function returnParent()
{
    top.close();
    top.opener.focus();
}

function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = "0000000003";
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}


/**
* �����������ڵĲ�,���ز������(M)������(D) (����������2.29��һ��)
* <p><b>Example: </b><p>
* <p>dateDiff("2002-10-1", "2002-10-3", "D") returns "2"<p>
* <p>dateDiff("2002-1-1", "2002-10-3", "M") returns "9"<p>
* @param dateStart ������
* @param dateEnd ��������
* @param MD ��ǣ���M��ΪҪ�󷵻ز����������D��ΪҪ�󷵻ز������
* @return �����������ڲ������(M)������(D)
*/
function dateDiffCT(dateStart,dateEnd,MD)
{
  if(dateStart==""||dateEnd=="")
  {
    return false;
  }
  if (typeof(dateStart) == "string") {
    dateStart = getDate(dateStart);
  }

  if (typeof(dateEnd) == "string") {
    dateEnd = getDate(dateEnd);
  }

  var i;
  if(MD=="D") //��������
  {
    var endD = dateEnd.getDate();
    var endM = dateEnd.getMonth();
    var endY = dateEnd.getFullYear();
    var startD = dateStart.getDate();
    var startM = dateStart.getMonth();
    var startY = dateStart.getFullYear();
    var startT=new Date(startY,startM-1,startD);
    var endT=new Date(endY,endM-1,endD);
    var diffDay=(endT.valueOf()-startT.valueOf())/86400000;
    return diffDay;
  }
  else //���¼����
  {
    var endD = dateEnd.getDate();
    var endM = dateEnd.getMonth();
    var endY = dateEnd.getFullYear();
    var startD = dateStart.getDate();
    var startM = dateStart.getMonth();
    var startY = dateStart.getFullYear();

    if(endD<startD)
    {
      return (endY-startY)*12 + (endM-startM) -1;  //ֻ��������
    }
    else
    {
      return (endY-startY)*12 + (endM-startM);
    }
  }
}

function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
    }
}

/**
* ��ȡ���ڶ���
* @param strDate �����ַ���
* @param splitOp �ָ��
* @return �������ڶ���
*/
function getDate(strDate, splitOp) {
  if (splitOp == null) splitOp = "-";

  var arrDate = strDate.split(splitOp);
  if (arrDate[2] == "31")
  {
    arrDate[2] = "30";
  }
  if (arrDate[1].length == 1) arrDate[1] = "0" + arrDate[1];
  if (arrDate[2].length == 1) arrDate[2] = "0" + arrDate[2];

  return new Date(arrDate[0], arrDate[1], arrDate[2]);

}

function getZTMoney()
{
/*    strSQL = " select getmoney,EdorReasonCode,EdorReason, standbyflag2, " +
    		 " (select trim(n.codename) from LDCode n where trim(n.codetype) = 'cttype' and trim(n.code) = trim(standbyflag1))" +
    		 " from lpedoritem " +
    		 " where edoracceptno = '" + fm.EdorAcceptNo.value +
    		 "' and edorno = '" + fm.EdorNo.value +
    		 "' and edortype = 'CT' ";
*/
	var sqlid16="PEdorTypeCTInputSql16";
	var mySql16=new SqlClass();
	mySql16.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
	mySql16.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
	mySql16.addSubPara(fm.EdorNo.value);
	strSQL=mySql16.getString();

    var brr = easyExecSql(strSQL);

    if ( brr )
    {
        //brr[0][0]==null||brr[0][0]=='null'?'0':fm.GetMoney.value     	= brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.SurrReason.value     	= brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.SurrReasonName.value  = brr[0][2];
        //brr[0][3]==null||brr[0][3]=='null'?'0':fm.RelationToAppnt.value  = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.CTType.value  = brr[0][4];
    }

}

function checkMainPol(spanId)
{
		var rowNum=PolGrid.mulLineCount;//������
		var rowLine = spanId.substr(11,1); //��ǰ��ѡ�е�������
		
		if(document.all(spanId).all('InpPolGridChk').value=='1')
		{
			var tPolNo = PolGrid.getRowColData(rowLine,9);
			var tMainPolNo = PolGrid.getRowColData(rowLine,12);
			if(tPolNo == tMainPolNo)
			{
				var i;
				var hasAdd = false;
				for(i = 0;i<rowNum;i++)
				{
					var rMainPolNo = PolGrid.getRowColData(i,12);
					var rPolNo = PolGrid.getRowColData(i,9);
					if(rMainPolNo == tMainPolNo && rMainPolNo != rPolNo)
					{
						//alert(tRiskCode);
						hasAdd = true;
						PolGrid.checkBoxSel(i+1);
					}
				}
				if(hasAdd)
				{
						alert("��ʾ����ѡ�е������ձ������ñ����µ����и����ս�һ�������");
				}
			}
		}
		
		if(document.all(spanId).all('InpPolGridChk').value=='0')
		{
			var tPolNo = PolGrid.getRowColData(rowLine,9);
			var tMainPolNo = PolGrid.getRowColData(rowLine,12);
			if(tPolNo == tMainPolNo)
			{
				var i;
				for(i = 0;i<rowNum;i++)
				{
					var rMainPolNo = PolGrid.getRowColData(i,12);
					if(rMainPolNo == tMainPolNo)
					{
						//alert(tRiskCode);
						PolGrid.checkBoxNotSel(i+1);
					}
				}
				//alert("��ʾ����ѡ�е������ձ������ñ����µ����и����ս�һ�������");
			}
		}
}

/**�жϴ������Ƿ������գ�*/
function isMainPolno(tPolNo)
{
//	var tSQL="select 1 from lcpol  where polno='"+tPolNo+"' and mainpolno=polno and contno='"+document.all('ContNo').value+"'";
	
	var tSQL="";
	var sqlid17="PEdorTypeCTInputSql17";
	var mySql17=new SqlClass();
	mySql17.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
	mySql17.addSubPara(tPolNo);//ָ������Ĳ���
	mySql17.addSubPara(document.all('ContNo').value);
	tSQL=mySql17.getString();
	
	var tFlag=easyExecSql(tSQL);
	//alert(tPolNo);
	if(tFlag==null)
	{
		return false;
	}else
		{
			return true;
			}
	
}



function confirmSecondInput1(aObject,aEvent)
{
 {
  if(theFirstValue!="")
  {
   clipboardData.setData('text','');
   theSecondValue = aObject.value;
   if(theSecondValue=="")
   {
    alert("���ٴ�¼�룡");
    aObject.value="";
    aObject.focus();
    return;
   }

   if(theSecondValue==theFirstValue)
   {
    aObject.value = theSecondValue;
    theSecondValue="";
    theFirstValue="";
    return;
   }
   else
   {
    alert("����¼����������������¼�룡");
    theFirstValue="";
    theSecondValue="";
    aObject.value="";
    aObject.focus();
    return;
   }
  }
  else
  {
   theFirstValue = aObject.value;
   theSecondValue="";
   if(theFirstValue=="")
   {
    return;
   }
   aObject.value="";
   aObject.focus();
   clipboardData.setData('text','');
   return;
  }
 }
}

/**
 * �����ʺŹ���
 */
function checkBank(tBankCode,tBankAccNo)
{
	if(tBankCode.length>0 && tBankAccNo.length>0)
	{
		if(!checkBankAccNo(tBankCode,tBankAccNo))
		{	
			return false;
		}
	}
	return true;
}

