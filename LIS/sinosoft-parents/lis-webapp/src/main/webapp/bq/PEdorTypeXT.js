var showInfo;
var mSwitch;
//�Ƿ񱣴�ı�־
var isSave;
var turnPage = new turnPageClass();
var sqlresourcename = "bq.PEdorTypeXTInputSql";

function getCustomerActBnfGrid()
{
    var tContNo=document.all("ContNo").value;
 
    if(tContNo!=null&&tContNo!="")
    {
//    	var sql="select name,sex,birthday,idtype,idno,BnfLot,Remark,bankcode,BankAccNo,AccName from lpbnf where edorno in (select edorno from lpedoritem where edoracceptno='"+document.all('EdorAcceptNo').value+"')";
    	
    var sql = "";
	var sqlid1="PEdorTypeXTInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(document.all('EdorAcceptNo').value);//ָ������Ĳ���
	sql=mySql1.getString();
    	
    	var tarrResult = easyExecSql(sql);
    	if(tarrResult)
    	{
    		displayMultiline(tarrResult,CustomerActBnfGrid);
    		//showPageOnly(this,divBackFeeTotal);
    	}
    } 
}

function edorActBnfSave()
{
	//var row = BackFeeDetailGrid.mulLineCount;
	//alert("row:"+fm.BackFeeGetMoney.value);
	CustomerActBnfGrid.delBlankLine();
	if(!CustomerActBnfGrid.checkValue2())
	{
		  return false;
	}
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
		   	  alert(str);
		   	  return false;
		   }
		   else
		   {  
			   CustomerActBnfGrid.setRowColData(i,3,tBirthday) ;
			   CustomerActBnfGrid.setRowColData(i,2,tSex) ;
		   }
	    }
		if(CustomerActBnfGrid.getRowColData(i,7)!="1"&&CustomerActBnfGrid.getRowColData(i,7)!="4")
		{
			alert("ʵ����ȡ���б��е�"+(i+1)+"����ȡ��ʽ����ȷ��������ѡ��");
			return false;
		}
		
		if(CustomerActBnfGrid.getRowColData(i,7)=="4")
		{
			if(CustomerActBnfGrid.getRowColData(i,8)==null||CustomerActBnfGrid.getRowColData(i,8)==""||
					CustomerActBnfGrid.getRowColData(i,9)==null||CustomerActBnfGrid.getRowColData(i,9)==""||
					CustomerActBnfGrid.getRowColData(i,10)==null||CustomerActBnfGrid.getRowColData(i,10)=="")
			{
				alert("ʵ����ȡ���б��е�"+(i+1)+"����ȡ��ʽΪת�ˣ����Ա�����д���б��롢�����˺ź������ʻ���");
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
	  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	  fm.submit();
}

function getPolGrid(tContNo)
{
    var EdorAppDate = fm.EdorItemAppDate.value;
    var tContno=document.all('ContNo').value;
/*    var strSQL = " select RiskCode," +
                 " (select RiskName from LMRisk where LMRisk.RiskCode = c.RiskCode)," +
                 " InsuredName, Amnt, " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "' and payenddate >= '" + EdorAppDate + "' and p.polno = c.polno and p.payplantype in ('0')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "' and payenddate >= '" + EdorAppDate + "' and p.polno = c.polno and p.payplantype in ('01', '03')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "' and p.polno = c.polno and p.payplantype in ('02', '04')), 0), " +
                 " polno,cvalidate,paytodate,mainpolno" +
                 " from LCPol c where ContNo='" + tContNo + "' and appflag = '1'" +
                 " union" +
                 " select RiskCode," +
                 " (select RiskName from LMRisk where LMRisk.RiskCode = d.RiskCode)," +
                 " InsuredName, Amnt, " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "' and p.polno = d.polno and p.payplantype in ('0')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "' and p.polno = d.polno and p.payplantype in ('01', '03')), 0), " +
                 " nvl((select sum(p.prem) from lcprem p where paystartdate <= '" + EdorAppDate + "' and p.polno = d.polno and p.payplantype in ('02', '04')), 0), " +
                 " polno,cvalidate,paytodate,mainpolno" 
                 + " from LCPol d where 1=1"
                 + " and (select count(*) from lccontstate s where trim(statetype) in('Terminate') and trim(state) = '1' and trim(StateReason) in ('07') and ((startdate <= '" + EdorAppDate + "' and '" + EdorAppDate + "' <= enddate )or(startdate <= '" + EdorAppDate + "' and enddate is null ))and s.polno = d.polno) > 0"
                 + " and d.appflag = '4' and d.contno = '" + tContNo + "'";
            //and c.RiskCode not in (" +
            //     "'111303','121703','131601','141807','141808','141809','141813')";
*/    
    var strSQL = "";
	var sqlid2="PEdorTypeXTInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(EdorAppDate);//ָ������Ĳ���
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);//ָ������Ĳ���
	mySql2.addSubPara(tContNo);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);
	mySql2.addSubPara(EdorAppDate);//ָ������Ĳ���
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
                    +" a.idno "
                    +" From lcappnt a Where contno='" + tContNo+"'"
                    +" Union"
                    +" Select i.insuredno, '������', i.name, "
                    +" (select trim(u.code)||'-'||trim(u.CodeName) from LDCode u where trim(u.codetype) = 'sex' and trim(u.code) = trim(sex)),"
                    +" i.Birthday,"
                    +" (select trim(y.code)||'-'||trim(y.CodeName) from LDCode y where trim(y.codetype) = 'idtype' and trim(y.code) = trim(idtype)), "
                    +" i.IDNo "
                    +" From lcinsured i Where contno='" + tContNo+"'";
*/        
    var strSQL = "";
	var sqlid3="PEdorTypeXTInputSql3";
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
	var sqlid4="PEdorTypeXTInputSql4";
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
                if(PolGrid.getRowColData(m, 8) == arrResult2[n][0])
                {
                    PolGrid.checkBoxSel(m+1);
                }
            }
        }
        showPageOnly(this,divAppnt);
    }
}


function queryEdorReason()
{
/*    strSQL = " select getmoney,EdorReasonCode,EdorReason, standbyflag2, " +
             " (select trim(n.codename) from LDCode n where trim(n.codetype) = 'cttype' and trim(n.code) = trim(standbyflag1))," +
             " (select codename from ldcode  where codetype='xsurrordereason' and code=a.EdorReasonCode)" +
             " from lpedoritem a " +
             " where edoracceptno = '" + fm.EdorAcceptNo.value +
             "' and edorno = '" + fm.EdorNo.value +
             "' and edortype = '" + document.all('EdorType').value + "' ";
*/
	var sqlid5="PEdorTypeXTInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(fm.EdorAcceptNo.value);//ָ������Ĳ���
	mySql5.addSubPara(fm.EdorNo.value);
	mySql5.addSubPara(document.all('EdorType').value);
	strSQL=mySql5.getString();

    var brr = easyExecSql(strSQL);

    if ( brr )
    {
        //brr[0][0]==null||brr[0][0]=='null'?'0':fm.GetMoney.value        = brr[0][0];
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.SurrReason.value      = brr[0][1];
        brr[0][2]==null||brr[0][2]=='null'?'0':fm.ReasonContent.value   = brr[0][2];
        //brr[0][3]==null||brr[0][3]=='null'?'0':fm.RelationToAppnt.value  = brr[0][3];
        brr[0][4]==null||brr[0][4]=='null'?'0':fm.CTType.value  = brr[0][4];
        brr[0][5]==null||brr[0][5]=='null'?'0':fm.SurrReasonName.value  = brr[0][5];
    }

}

function getContZTInfo()
{
    var MainPolNo;
    var MainPayIntv;
    var MainPayToDate;
    var MainPayEndDate;
/*    strSQL = " select polno, paytodate, payintv, payenddate from lcPol " +
             " where contno = '" + fm.ContNo.value +
             "' and polno = mainpolno";
*/   
    var sqlid6="PEdorTypeXTInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strSQL=mySql6.getString();
    
    var hrr = easyExecSql(strSQL);
    if ( hrr )
    {
        hrr[0][0]==null||hrr[0][0]=='null'?'0':MainPolNo = hrr[0][0];
        hrr[0][1]==null||hrr[0][1]=='null'?'0':MainPayToDate = hrr[0][1];
        hrr[0][2]==null||hrr[0][2]=='null'?'0':MainPayIntv = hrr[0][2];
        hrr[0][3]==null||hrr[0][3]=='null'?'0':MainPayEndDate = hrr[0][3];
    }
    else
    {
        alert("��������ʧ��");
        return;
    }

/*    strSQL = " select customgetpoldate, ReceiveDate, cvalidate from lccont " +
             " where contno = '" + fm.ContNo.value + "' ";
*/    
    var sqlid7="PEdorTypeXTInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strSQL=mySql7.getString();
    
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
    var sqlid8="PEdorTypeXTInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	mySql8.addSubPara(fm.EdorItemAppDate.value);//ָ������Ĳ���
	mySql8.addSubPara(fm.EdorItemAppDate.value);
	mySql8.addSubPara(fm.EdorItemAppDate.value);
	mySql8.addSubPara(MainPolNo);
	strSQL=mySql8.getString();
        
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

        var intval = dateDiffCT(fm.CvaliDate.value, CalDate, "M");
        var year = (intval- intval%12)/12;
        var month = intval%12;
        fm.Inteval.value = year + " �� �� " + month + " ��";

        if (MainPayIntv == 0)
        {
            //alert("����");
            //�ж����޸�����
/*            strSQL = " select paytodate from lcPol " +
                     " where contno = '" + fm.ContNo.value +
                     "' and polno <> mainpolno and rownum = 1 and appflag = '1' ";
*/            
    var sqlid9="PEdorTypeXTInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	mySql9.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	strSQL=mySql9.getString();
            
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
    var sqlid10="PEdorTypeXTInputSql10";
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
                fm.PayToDate.value = MainPayToDate;
            }
        }
    }
}

function edorTypeCTCal()
{

    var row = PolGrid.mulLineCount;
    //�����ж���ѡ�������˱�ʱ�����ѡ�����գ���Ҫ�󽫸�����һ��ѡ��
    var tFlag=false;
    var i = 0;
      for(var m = 0; m < row; m++ )
      {
        if(PolGrid.getChkNo(m))
        {
         if(isMainPolno(PolGrid.getRowColData(m,8)))
         {
        	tFlag=true;
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
         if(!isMainPolno(PolGrid.getRowColData(k,8)))
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
    else {
    	//add by jiaqiangli 2009-05-23
    	if (fm.SurrReason.value == "05" && (fm.ReasonContent.value == null || fm.ReasonContent.value == "")) {
    		alert("���ڱ�ע��¼�������ϸ�������˱�ԭ��!");
        	return;
    	}
    	//add by jiaqiangli 2009-05-23
    }

    var tContNo = document.all("ContNo").value;
//    var strSql = "select * from LPEdorItem where ContNo = '" + tContNo + "' and EdorType = 'LR' and EdorState = '0'";
    
    var strSql = "";
    var sqlid11="PEdorTypeXTInputSql11";
	var mySql11=new SqlClass();
	mySql11.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql11.setSqlId(sqlid11);//ָ��ʹ�õ�Sql��id
	mySql11.addSubPara(tContNo);//ָ������Ĳ���
	strSql=mySql11.getString();
    
    var arrResult = easyExecSql(strSql);     //ִ��SQL�����в�ѯ�����ض�ά����
    if (arrResult != null)
    {
        if (!confirm("�ñ���Ϊ�����������Ƿ�����˱���"))
        {
            return;
        }

    }
  fm.fmtransact.value = "EdorCal";

  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();
  fm.submit();
}

/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=350;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=300;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
    //���ļ������⴦��
    if (DealFlag == "succ" || DealFlag == "success")
    {
        try         
        {  
        	  //alert(fm.fmtransact.value);
        	  //if(fm.fmtransact.value == 'EdorSave')
        	  //{
        		// isSave = 'Y';
        		//}
            queryShouldMoney();
            queryAdjustMoney();
            getCTFeePolGrid();
            getCTFeeDetailGrid();
            top.opener.getEdorItemGrid();
//            alert("a");
            getCustomerActBnfGrid();
            showPageOnly(this,divAppnt);
//            alert("b");
        }
        catch (ex) {}
    }
}

/**
 * ����������
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
    }
    catch (ex) {}
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

/*============================================================================*/

/**
 * XinYQ rewrote on 2007-06-05
 */
function getCTFeePolGrid()
{
    var QuerySQL;
/*     QuerySQL= "select distinct polno, "
                 +   "(select riskcode from lcpol where polno = a.polno) , "
                 +   "(select RiskName "
                 +   "from LMRisk where RiskCode = "
                 +   "(select riskcode from lcpol where polno = a.polno)), "                
                 +   "'','' "             
                 +           "from ljsgetendorse a "
                 +          "where 1 = 1 "
                 +             getWherePart("a.EndorsementNo", "EdorNo")
                  +     getWherePart("ContNo", "ContNo")
                 +          "order by a.polno";
    //alert(QuerySQL);
*/    
    var sqlid12="PEdorTypeXTInputSql12";
	var mySql12=new SqlClass();
	mySql12.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql12.setSqlId(sqlid12);//ָ��ʹ�õ�Sql��id
	mySql12.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql12.addSubPara(fm.ContNo.value);
	QuerySQL=mySql12.getString();
    
    var arrResult = easyExecSql(QuerySQL);
    if (arrResult)
    {
        displayMultiline(arrResult, CTFeePolGrid);
        var rowNum=CTFeePolGrid.mulLineCount; 
        //alert(rowNum);
        if(rowNum > 0)
        {
        	for(var t=0;t<rowNum;t++)
        	{
        		//alert(arrResult[t][0]);
            CTFeePolGrid.setRowColData(t,4,queryPolShouldMoney(arrResult[t][0]));
            CTFeePolGrid.setRowColData(t,5,queryPolAdjustMoney(arrResult[t][0]));
        		}
         }

   
    }
}

function isHasCal()
{
	    var QuerySQL;
/*     QuerySQL= "select  polno from lppol where  contno='"+fm.ContNo.value+"' and EdorNo='"+document.all('EdorNo').value+"'"; 
    //alert(QuerySQL);
*/    
    var sqlid13="PEdorTypeXTInputSql13";
	var mySql13=new SqlClass();
	mySql13.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql13.setSqlId(sqlid13);//ָ��ʹ�õ�Sql��id
	mySql13.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	mySql13.addSubPara(document.all('EdorNo').value);
	QuerySQL=mySql13.getString();
    
    var arrResult = easyExecSql(QuerySQL);	
    if(arrResult)
    {
    		   fm.hasCal.value='Y';
    	}else
    	{
    			fm.hasCal.value='N';
    	}

	}

/*============================================================================*/

/**
 * XinYQ rewrote on 2007-06-05
 */
function getCTFeeDetailGrid()
{
	  var QuerySQL;
	  //alert(isSave);
/*    QuerySQL = "select distinct polno , "
                 +  "(select riskcode from lcpol where polno = a.polno) , "
                 +  "(select RiskName "
                 +  " from LMRisk "
                 +  " where RiskCode = "
                 +  " (select riskcode from lcpol where polno = a.polno)) , "
                 +  " (select codename "
                 +  " from ldcode "
                 +  " where 1 = 1 "
                 +  " and codetype = 'finfeetype' "
                 +  " and code = a.feefinatype), "
                 +  " (select codename "
                 +  " from ldcode "
                 +  " where 1 = 1 "
                 +  " and codetype = 'BQSubFeeType' "
                 +  " and code = a.subfeeoperationtype) , "
                 +  " nvl((a.SerialNo),(a.GetMoney)) , "
                 +  " (a.GetMoney) , "
                 +  " a.feefinatype , "
                 +  " a.subfeeoperationtype, "
                 +  " a.getnoticeno,decode(a.getflag,'0','�շ�','1','����','����'),  "
                  + " a.dutycode , "
                 +  " a.payplancode "
                 +  " from ljsgetendorse a "
                 +  " where 1 = 1 "
                 +  getWherePart("a.EndorsementNo", "EdorNo")
                 +  "order by a.polno, a.feefinatype, a.subfeeoperationtype,a.getnoticeno ";
    //alert(QuerySQL);
*/    
    var sqlid14="PEdorTypeXTInputSql14";
	var mySql14=new SqlClass();
	mySql14.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql14.setSqlId(sqlid14);//ָ��ʹ�õ�Sql��id
	mySql14.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	QuerySQL=mySql14.getString();
    
    var arrResult = easyExecSql(QuerySQL);
    if (arrResult)
    {
        displayMultiline(arrResult, CTFeeDetailGrid);
    }
}

/*
*������Ȳ�����¼�븺ֵ���˱�����峥��������Ϣ�������޸�
*/
function CheckAjustMoney() {
	var tGridCount = CTFeeDetailGrid.mulLineCount;
	for (var i=0;i<tGridCount;i++) {
		if (CTFeeDetailGrid.getRowColData(i,11) == '0') {
			if(CTFeeDetailGrid.getRowColData(i,7) != CTFeeDetailGrid.getRowColData(i,6)) {
				alert("Э���˱����峥��������Ϣ�������޸�");
				return false;
			}
		}
			if(CTFeeDetailGrid.getRowColData(i,7) < 0) {
				alert("��"+i+"�����ݽ��¼�벻�Ϸ�");
				return false;
			}
	}
	return true;
}
/*============================================================================*/

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
       divCalButton.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
        divCalButton.style.display = "";
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

/**
* �������˫����ֵ
* @param parm1
*/
function formatOldAmnt(parm1)
{
    document.all(parm1).all('CTFeeDetailGrid7').value = document.all(parm1).all('CTFeeDetailGrid6').value;
}

/**
* �������˫����ֵ
*/
function saveEdorTypeXT()
{
	
	    isHasCal();
	  	var isSaveFlag = fm.hasCal.value;
			//alert(isSaveFlag);
			if(isSaveFlag == null || isSaveFlag == "" || isSaveFlag != "Y")
			{
				alert("���ȼ��㣬Ȼ������˱�����ٱ��棡");
				return;
			}
		  if(fm.TrueLostTimes.value > 0 )
    	{
    		if(fm.LostTimes.value == null || fm.LostTimes.value == "")
    		{
    			alert("�ñ����в�����¼����¼�벹��������");
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
    var rowNum=CTFeeDetailGrid.mulLineCount;
    if(rowNum > 0)
    {
    	var i;
    	for(i = 0 ; i <rowNum ; i++)
    	{
    		var inputNum = CTFeeDetailGrid.getRowColData(i,7)
    		//alert(inputNum);
    		if(!isNumeric(inputNum))
    		{
    			alert("���������¼��Ϸ����֣�")
    			return;
    		}
    	}
    	//return;
    }
    
    //add check
    if (CheckAjustMoney() == false) {
    	return false;
    }	
    	
    fm.fmtransact.value = "EdorSave";    

    fm.fmtransact.value = "EdorSave";
    var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.submit();
}

/*============================================================================*/

/**
 * ��ѯ�Ѿ��������Ӧ�˽���Ŀ���𼴱�������
 */
function queryShouldMoney()
{
	
	  //����ǰ��ѯGetMoney,����󣬲�ѯSerialNo
    var QuerySQL, arrResult;
    var tSumGetMoney=0,tSumPayMoney=0;
/*    	  QuerySQL = "select nvl(SerialNo,GetMoney),getflag "
             +   "from LJSGetEndorse "
             +  "where 1 = 1 "
             +     getWherePart("EndorsementNo", "EdorNo")
             +     getWherePart("FeeOperationType", "EdorType")
             +     getWherePart("ContNo", "ContNo")
             +    "and OtherNo <> '000000'";
    //alert(QuerySQL);
*/    
    var sqlid15="PEdorTypeXTInputSql15";
	var mySql15=new SqlClass();
	mySql15.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql15.setSqlId(sqlid15);//ָ��ʹ�õ�Sql��id
	mySql15.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql15.addSubPara(fm.EdorType.value);
	mySql15.addSubPara(fm.ContNo.value);
	QuerySQL=mySql15.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL);
    }
    catch (ex)
    {
        alert("���棺��ѯ�Ѿ��������Ӧ�˽����Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null)
    {

    	for(var k=0;k<arrResult.length;k++)
    	{
    		if(arrResult[k][1]=='1')
    		{
    			tSumGetMoney+=parseFloat(arrResult[k][0]);
    		}else
    		{
    				
         tSumPayMoney-=parseFloat(arrResult[k][0]);  	
    		}
     }
        try
        {  
        	fm.GetMoney.value=NullToEmpty(mathRound(tSumPayMoney+tSumGetMoney));
            document.getElementsByName("GetMoney")[0].value = NullToEmpty(mathRound(tSumPayMoney+tSumGetMoney));
            //alert(document.getElementsByName("GetMoney")[0].value);    
        }
        catch (ex) {}
    }

     

}

/*============================================================================*/

/**
 * ��ѯ�Ѿ���������˷ѽ���Ŀ���𼴱�������
 */
function queryAdjustMoney()
{
    var QuerySQL, arrResult;
    var tSumGetMoney=0,tSumPayMoney=0;
/*    QuerySQL = "select nvl(GetMoney,a.SerialNo),getflag "
             +   "from LJSGetEndorse a "
             +  "where 1 = 1 "
             +     getWherePart("a.EndorsementNo", "EdorNo")
             +     getWherePart("a.FeeOperationType", "EdorType")
             +     getWherePart("a.ContNo", "ContNo");
                 //alert(QuerySQL);
*/    
    var sqlid16="PEdorTypeXTInputSql16";
	var mySql16=new SqlClass();
	mySql16.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql16.setSqlId(sqlid16);//ָ��ʹ�õ�Sql��id
	mySql16.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql16.addSubPara(fm.EdorType.value);
	mySql16.addSubPara(fm.ContNo.value);
	QuerySQL=mySql16.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL);
    }
    catch (ex)
    {
        alert("���棺��ѯ�Ѿ���������˷ѽ����Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null)
    {
    	for(var k=0;k<arrResult.length;k++)
    	{
    		if(arrResult[k][1]=='1')
    		{
    			tSumGetMoney+=parseFloat(arrResult[k][0]);
    		}else
    		{
    				
         tSumPayMoney-=parseFloat(arrResult[k][0]);  	  			
    		}
      }
        try
        {
        document.getElementsByName("AdjustMoney")[0].value = NullToEmpty(mathRound(tSumPayMoney+tSumGetMoney));
        }
        catch (ex) {}
    }
}

/*============================================================================*/
function queryLRInfo()
{
//		var tSQL = "select c.losttimes from lccont c where c.contno ='"+fm.ContNo.value+"'";
		
	var tSQL = "";
	var sqlid17="PEdorTypeXTInputSql17";
	var mySql17=new SqlClass();
	mySql17.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql17.setSqlId(sqlid17);//ָ��ʹ�õ�Sql��id
	mySql17.addSubPara(fm.ContNo.value);//ָ������Ĳ���
	tSQL=mySql17.getString();
		
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

function checkMainPol(spanId)
{
		var rowNum=PolGrid.mulLineCount;//������
		var rowLine = spanId.substr(11,1); //��ǰ��ѡ�е�������
		
		if(document.all(spanId).all('InpPolGridChk').value=='1')
		{
			var tPolNo = PolGrid.getRowColData(rowLine,8);
			var tMainPolNo = PolGrid.getRowColData(rowLine,11);
			if(tPolNo == tMainPolNo)
			{
				var i;
				var hasAdd = false;
				for(i = 0;i<rowNum;i++)
				{
					var rMainPolNo = PolGrid.getRowColData(i,11);
					var rPolNo = PolGrid.getRowColData(i,8);
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
			else  //add by xiongzh ���Ӷ�'121704','121705','121801'�����������ֲ��������˱�У��
			{
			   var tRiskCode = PolGrid.getRowColData(rowLine,1);
			   if(tRiskCode=='121704'||tRiskCode=='121705'||tRiskCode=='121801')
			   {
			        //�ж������Ƿ�ѡ���˱�
			        var j;
				    var mainAdd = false;
					for(j = 0;j<rowNum;j++)
					{
						var rMainPolNo = PolGrid.getRowColData(j,11);
						var rPolNo = PolGrid.getRowColData(j,8);
						if(rMainPolNo == tMainPolNo && rMainPolNo == rPolNo
						 && PolGrid.getChkNo(j))
						{
							mainAdd = true;
						}
					}
					if(!mainAdd)
					{  
					        var trowLine = parseInt(rowLine)+1;
							alert("��ʾ����ѡ�е���������ֻ��������һ���˱������������˱���");
							PolGrid.checkBoxNotSel(trowLine);
					}
			   }
			}
		}
		
		if(document.all(spanId).all('InpPolGridChk').value=='0')
		{
			var tPolNo = PolGrid.getRowColData(rowLine,8);
			var tMainPolNo = PolGrid.getRowColData(rowLine,11);
			if(tPolNo == tMainPolNo)
			{
				var i;
				for(i = 0;i<rowNum;i++)
				{
					var rMainPolNo = PolGrid.getRowColData(i,11);
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
	
	var tSQL = "";
	var sqlid18="PEdorTypeXTInputSql18";
	var mySql18=new SqlClass();
	mySql18.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql18.setSqlId(sqlid18);//ָ��ʹ�õ�Sql��id
	mySql18.addSubPara(tPolNo);//ָ������Ĳ���
	mySql18.addSubPara(document.all('ContNo').value);
	tSQL=mySql18.getString();
	
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


/**
 * ��ѯ�Ѿ��������Ӧ�˽����ּ���
 */
function queryPolShouldMoney(tPolNo)
{
	
	  //����ǰ��ѯGetMoney,����󣬲�ѯSerialNo
    var QuerySQL, arrResult;
    var tSumGetMoney=0,tSumPayMoney=0;
/*    	  QuerySQL = "select nvl(SerialNo,GetMoney),getflag "
             +   "from LJSGetEndorse "
             +  " where 1 = 1 "
             +  " and  EndorsementNo='"+fm.EdorNo.value+"'"
             +  " and FeeOperationType='XT' "
             +  " and  polno='"+tPolNo+"'"
             +    "and OtherNo <> '000000'";
    //alert(QuerySQL);
*/    
	var sqlid19="PEdorTypeXTInputSql19";
	var mySql19=new SqlClass();
	mySql19.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql19.setSqlId(sqlid19);//ָ��ʹ�õ�Sql��id
	mySql19.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql19.addSubPara(tPolNo);
	QuerySQL=mySql19.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL);
    }
    catch (ex)
    {
        alert("���棺��ѯ�Ѿ��������Ӧ�˽����Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null)
    {

    	for(var k=0;k<arrResult.length;k++)
    	{
    		if(arrResult[k][1]=='1')
    		{
    			tSumGetMoney+=parseFloat(arrResult[k][0]);
    		}else
    		{
    				
         tSumPayMoney-=parseFloat(arrResult[k][0]);  	
    		}
     }
        try
        {  
        	 //alert(tSumPayMoney+tSumGetMoney);
           return String (mathRound(tSumPayMoney+tSumGetMoney));    
        }
        catch (ex) {}
    }else
    	{
    		    		return "0.00"
    		}

     

}

/*============================================================================*/

/**
 * ��ѯ�Ѿ���������˷ѽ��,���ּ���
 */
function queryPolAdjustMoney(tPolNo)
{
    var QuerySQL, arrResult;
    var tSumGetMoney=0,tSumPayMoney=0;
/*    QuerySQL = "select nvl(GetMoney,a.SerialNo),getflag "
             +   "from LJSGetEndorse a "
             +  "where 1 = 1 "
             +  " and EndorsementNo='"+fm.EdorNo.value+"'"
             +  " and FeeOperationType='XT' "
             +  " and polno='"+tPolNo+"'";
                 //alert(QuerySQL);
*/    
    var sqlid20="PEdorTypeXTInputSql20";
	var mySql20=new SqlClass();
	mySql20.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql20.setSqlId(sqlid20);//ָ��ʹ�õ�Sql��id
	mySql20.addSubPara(fm.EdorNo.value);//ָ������Ĳ���
	mySql20.addSubPara(tPolNo);
	QuerySQL=mySql20.getString();
    
    try
    {
        arrResult = easyExecSql(QuerySQL);
    }
    catch (ex)
    {
        alert("���棺��ѯ�Ѿ���������˷ѽ����Ϣ�����쳣�� ");
        return;
    }
    if (arrResult != null)
    {
    	for(var k=0;k<arrResult.length;k++)
    	{
    		if(arrResult[k][1]=='1')
    		{
    			tSumGetMoney+=parseFloat(arrResult[k][0]);
    		}else
    		{
    				
         tSumPayMoney-=parseFloat(arrResult[k][0]);  	  			
    		}
      }
        try
        {
        	//alert(tSumPayMoney+tSumGetMoney);
        return String(mathRound(tSumPayMoney+tSumGetMoney));
        }
        catch (ex) {}
    }else
    	{
    		return "0.00"
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
