//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�TempFeeInputPrint.js
//�����ܣ����շ�Ʊ�ݴ�ӡ
//�������ڣ�2005-12-21 14:55
//�� �� �ˣ���Ρ
//���¼�¼:
//    ������    ��������     ����ԭ��/����


//ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage = new turnPageClass();


//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
	if(showInfo!=null)
	{
	  try
	  {
	    showInfo.focus();
	  }
	  catch(ex)
	  {
	    showInfo=null;
	  }
	}
}

//���ݲ�ͬ���վ�������ʾ��ͬ�Ĳ�ѯ����
function afterCodeSelect(){ 
    //֧Ʊ/��Ʊ����֪ͨ��
		if(fm.all("InvoiceType").value=='0')
		{
			//alert("0");
			fm.all("InvoiceType0").style.display = '';
			fm.all("InvoiceType1").style.display = 'none';
			fm.all("InvoiceType2").style.display = 'none';
			fm.all("divChequeGrid").style.display = '';
			fm.all("divCustomertGrid").style.display = 'none';
			fm.all("divRNPremGrid").style.display = 'none';
		}
		//�ͻ�Ԥ���վ�
    else if (fm.all("InvoiceType").value =='1')
		{
			//alert("1");
			fm.all("InvoiceType0").style.display = 'none';
			fm.all("InvoiceType1").style.display = '';
			fm.all("InvoiceType2").style.display = 'none';
			fm.all("divChequeGrid").style.display = 'none';
			fm.all("divCustomertGrid").style.display = '';
			fm.all("divRNPremGrid").style.display = 'none';
		}
		//Ԥ�����ڱ����վ�
    else if(fm.all("InvoiceType").value == '2')
    {
    	//alert("2");
			fm.all("InvoiceType0").style.display = 'none';
			fm.all("InvoiceType1").style.display = 'none';
			fm.all("InvoiceType2").style.display = '';
			fm.all("divChequeGrid").style.display = 'none';
			fm.all("divCustomertGrid").style.display = 'none';
			fm.all("divRNPremGrid").style.display = '';
    }
}

// ��ѯ��ť
function TempfeeQuery()
{
	//֧Ʊ��Ʊ����֪ͨ��
  if (fm.all('InvoiceType').value == '0')
  {
  	if(fm.all('ManageCom1').value == "" || fm.all('ManageCom1').value == null)
  	{
  		alert("�շѻ���Ϊ�գ�������");
  		fm.all('ManageCom1').focus();
  		return;
  		}
  	else if (fm.all('ManageCom1').value.length <6)
  		{
  			alert("�շѻ�������Ϊ��λ����������������");
  		  fm.all('ManageCom1').focus();
  		  return;
  			}
  		if(fm.all('ChequeNo').value=="" || fm.all('ChequeNo').value==null)
  		{
  			alert("֧Ʊ����Ϊ�գ�������");
  			fm.all('ChequeNo').focus();
  			return;
  			}
//	    var strSql = " select b.appntname,b.chequeno,b.otherno,b.accname,b.makedate,b.paymoney,a.agentcode,"+
//	                 "(select name from laagent where agentcode = trim(a.agentcode)), "+
//	                 "(select name from ldcom where trim(comcode) = substr(a.managecom,1,6) )"+
//	                 "   from ljtempfee a, ljtempfeeclass b  "+
//	                 "  where 1=1  and a.tempfeeno = b.tempfeeno and b.paymode = '3'"+
//	                 "    and a.managecom like '"+comCode+"%%' "+
//	          			  getWherePart( 'b.chequeno','ChequeNo' )+
//	          			  getWherePart( 'b.makedate','MakeDate')+
//	          			  getWherePart( 'a.paymoney','PayMoney')+
//	          			  getWherePart( 'a.managecom','ManageCom1','like')+
//	          			  getWherePart( 'b.AccName','AccName');
	    
	    var  ChequeNo = window.document.getElementsByName(trim("ChequeNo"))[0].value;
	    var  MakeDate = window.document.getElementsByName(trim("MakeDate"))[0].value;
	    var  PayMoney = window.document.getElementsByName(trim("PayMoney"))[0].value;
	    var  ManageCom1 = window.document.getElementsByName(trim("ManageCom1"))[0].value;
	    var  AccName = window.document.getElementsByName(trim("AccName"))[0].value;	    
     	var  sqlid1="TempFeeInputPrintSql0";
     	var  mySql1=new SqlClass();
     	mySql1.setResourceName("finfee.TempFeeInputPrintSql"); //ָ��ʹ�õ�properties�ļ���
     	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
     	mySql1.addSubPara(comCode);//ָ������Ĳ���
     	mySql1.addSubPara(ChequeNo);//ָ������Ĳ���
     	mySql1.addSubPara(MakeDate);//ָ������Ĳ���
     	mySql1.addSubPara(PayMoney);//ָ������Ĳ���
     	mySql1.addSubPara(ManageCom1);//ָ������Ĳ���
     	mySql1.addSubPara(AccName);//ָ������Ĳ���
     	var strSql=mySql1.getString();

      turnPage.queryModal(strSql, ChequeGrid);

	    if(ChequeGrid.mulLineCount =='0')
	    {
	    	alert("û�з�������������");
	    }
	}

	//�ͻ�Ԥ���վ�
	if (fm.all('InvoiceType').value == '1')
	{
  	if(fm.all('ManageCom2').value == "" || fm.all('ManageCom2').value == null)
  	{
  		alert("�շѻ���Ϊ�գ�������");
  		fm.all('ManageCom2').focus();
  		return;
  		}
  	else if (fm.all('ManageCom2').value.length <6)
  		{
  			alert("�շѻ�������Ϊ��λ����������������");
  		  fm.all('ManageCom2').focus();
  		  return;
  			}
  		if(fm.all('CustomerNo').value=="" || fm.all('CustomerNo').value==null)
  		{
  			alert("�ͻ����Ϊ�գ�������");
  			fm.all('CustomerNo').focus();
  			return;
  			}
//	    var strSql = " select a.otherno,a.appntname, a.paymoney,"+
//	                 "(select codename from ldcode  where codetype = 'paymodequery' and code = b.paymode),"+
//	                 " a.tempfeeno,c.actugetno, a.paydate,a.agentcode  "+
//	                 "  from ljtempfee a, ljtempfeeclass b,ljagettempfee c "+
//	                 " where a.tempfeeno = b.tempfeeno and c.tempfeeno = a.tempfeeno "+
//	                 "   and a.tempfeetype = '5' and a.managecom like '"+comCode+"%%'"+
//	                 getWherePart( 'a.tempfeeno','TempfeeNo' )+
//	                 getWherePart( 'a.otherno','CustomerNo' )+
//	                 getWherePart( 'a.paymoney','PayMoney')+
//          			   getWherePart( 'a.managecom','ManageCom2','like')+
//             			 getWherePart( 'b.makedate','MakeDate')+
//             			 getWherePart( 'a.appntname','AccName');
	    
	    var  TempfeeNo = window.document.getElementsByName(trim("TempfeeNo"))[0].value;
	    var  CustomerNo = window.document.getElementsByName(trim("CustomerNo"))[0].value;
	    var  PayMoney = window.document.getElementsByName(trim("PayMoney"))[0].value;
	    var  ManageCom2 = window.document.getElementsByName(trim("ManageCom2"))[0].value;
	    var  MakeDate = window.document.getElementsByName(trim("MakeDate"))[0].value;
	    var  AccName = window.document.getElementsByName(trim("AccName"))[0].value;
     	var  sqlid2="TempFeeInputPrintSql1";
     	var  mySql2=new SqlClass();
     	mySql2.setResourceName("finfee.TempFeeInputPrintSql"); //ָ��ʹ�õ�properties�ļ���
     	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
     	mySql2.addSubPara(comCode);//ָ������Ĳ���
     	mySql2.addSubPara(TempfeeNo);//ָ������Ĳ���
     	mySql2.addSubPara(CustomerNo);//ָ������Ĳ���
     	mySql2.addSubPara(PayMoney);//ָ������Ĳ���
     	mySql2.addSubPara(ManageCom2);//ָ������Ĳ���
     	mySql2.addSubPara(MakeDate);//ָ������Ĳ���
     	mySql2.addSubPara(AccName);//ָ������Ĳ���
     	var strSql=mySql2.getString();

       turnPage.queryModal(strSql, CustomertGrid);

	     if(CustomertGrid.mulLineCount =='0')
	     {
	     	alert("û�з�������������");
	     }
	    }

	//Ԥ�����ڱ����վ�
	if (fm.all('InvoiceType').value=='2')
	{
  	if(fm.all('ManageCom3').value == "" || fm.all('ManageCom3').value == null)
  	{
  		alert("�շѻ���Ϊ�գ�������");
  		fm.all('ManageCom3').focus();
  		return;
  		}
  	else if (fm.all('ManageCom3').value.length <6)
  		{
  			alert("�շѻ�������Ϊ��λ����������������");
  		  fm.all('ManageCom3').focus();
  		  return;
  			}
  		if(fm.all('ContNo').value=="" || fm.all('ContNo').value==null)
  		{
  			alert("������Ϊ�գ�������");
  			fm.all('ContNo').focus();
  			return;
  			}
  		var strSql = "";
  		if(_DBT==_DBO){
  			strSql = " select d.appntname,a.paydate,a.otherno,a.tempfeeno, a.paymoney, "+
			 "(select distinct operator from ljtempfeeclass where tempfeeno = a.tempfeeno and rownum = '1')operator,"+
			 "(select distinct accname from ljtempfeeclass where tempfeeno = a.tempfeeno and rownum = '1')accname,"+
			 "				c.idno "+
             " from ljtempfee a,ldperson c,lccont d"+
             " where a.otherno = d.contno   and d.appntno = c.customerno "+
             "   and a.tempfeetype = '3' "+
             "	 and a.managecom like '"+comCode+"%%' "+
             getWherePart( 'a.otherno','ContNo' )+
             getWherePart( 'a.paymoney','PayMoney')+
	         getWherePart( 'a.managecom','ManageCom3','like')+
	         getWherePart( 'a.makedate','MakeDate');
  			
//  			var  ContNo = window.document.getElementsByName(trim("ContNo"))[0].value;
//  		    var  PayMoney = window.document.getElementsByName(trim("PayMoney"))[0].value;
//  		    var  ManageCom3 = window.document.getElementsByName(trim("ManageCom3"))[0].value;    
//  		    var  MakeDate = window.document.getElementsByName(trim("MakeDate"))[0].value;
//  	     	var  sqlid1="TempFeeInputPrintSql2";
//  	     	var  mySql1=new SqlClass();
//  	     	mySql1.setResourceName("finfee.TempFeeInputPrintSql"); //ָ��ʹ�õ�properties�ļ���
//  	     	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
//  	     	mySql1.addSubPara(comCode);//ָ������Ĳ���
//  	     	mySql1.addSubPara(ContNo);//ָ������Ĳ���
//  	     	mySql1.addSubPara(PayMoney);//ָ������Ĳ���
//  	     	mySql1.addSubPara(ManageCom3);//ָ������Ĳ���
//  	     	mySql1.addSubPara(MakeDate);//ָ������Ĳ���
//  	     	 strSql=mySql1.getString();
  		}else if(_DBT==_DBM){
  			strSql = " select d.appntname,a.paydate,a.otherno,a.tempfeeno, a.paymoney, "+
			 "(select distinct operator from ljtempfeeclass where tempfeeno = a.tempfeeno limit 0,1)operator,"+
			 "(select distinct accname from ljtempfeeclass where tempfeeno = a.tempfeeno limit 0,1)accname,"+
			 "				c.idno "+
             " from ljtempfee a,ldperson c,lccont d"+
             " where a.otherno = d.contno   and d.appntno = c.customerno "+
             "   and a.tempfeetype = '3' "+
             "	 and a.managecom like '"+comCode+"%%' "+
             getWherePart( 'a.otherno','ContNo' )+
             getWherePart( 'a.paymoney','PayMoney')+
	         getWherePart( 'a.managecom','ManageCom3','like')+
	         getWherePart( 'a.makedate','MakeDate');
  			

//  			var  ContNo = window.document.getElementsByName(trim("ContNo"))[0].value;
//  		    var  PayMoney = window.document.getElementsByName(trim("PayMoney"))[0].value;
//  		    var  ManageCom3 = window.document.getElementsByName(trim("ManageCom3"))[0].value;    
//  		    var  MakeDate = window.document.getElementsByName(trim("MakeDate"))[0].value;
//  	     	var  sqlid1="TempFeeInputPrintSql3";
//  	     	var  mySql1=new SqlClass();
//  	     	mySql1.setResourceName("finfee.TempFeeInputPrintSql"); //ָ��ʹ�õ�properties�ļ���
//  	     	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
//  	     	mySql1.addSubPara(comCode);//ָ������Ĳ���
//  	     	mySql1.addSubPara(ContNo);//ָ������Ĳ���
//  	     	mySql1.addSubPara(PayMoney);//ָ������Ĳ���
//  	     	mySql1.addSubPara(ManageCom3);//ָ������Ĳ���
//  	     	mySql1.addSubPara(MakeDate);//ָ������Ĳ���
//  	     	 strSql=mySql1.getString();
  		}
	    

			if(fm.AccName.value!=""&&fm.AccName.value!=null)
			{
				strSql = strSql + "and exists (select * from ljtempfeeclass where tempfeeno = a.tempfeeno and accname = '"+fm.AccName.value+"')";
				}

			 strSql = strSql + "order by a.paydate";

       turnPage.queryModal(strSql, RNPremGrid);

	     if(RNPremGrid.mulLineCount =='0')
	     {
	     	alert("û�з�������������");
	     }
	    }
}

function easyQueryAddClick()
{
  var arrReturn = new Array();

  //֧Ʊ��Ʊ����֪ͨ��
  if (fm.all("InvoiceType").value=='0')
  {
	 var tSel = ChequeGrid.getSelNo();
	 if( tSel == 0 || tSel == null )
	 {
	 	alert( "����ѡ��һ����¼���ٵ����ӡ��ť��" );
	 }
	 	var tRow = ChequeGrid.getSelNo() - 1;
       fm.all('ChequeNoHidden').value = ChequeGrid.getRowColData(tRow,2);
       //alert( "֧Ʊ��"+ChequeGrid.getRowColData(tRow,2));
       fm.all('OtherNoHidden').value = ChequeGrid.getRowColData(tRow,3);
       //alert( "��������"+ChequeGrid.getRowColData(tRow3));
       fm.all('PayMoneyHidden').value = ChequeGrid.getRowColData(tRow,6);
       //alert( "���"+ChequeGrid.getRowColData(tRow,5));
       fm.all('AccNameHidden').value = ChequeGrid.getRowColData(tRow,4);
       //alert( "��Ʊ��λ"+ChequeGrid.getRowColData(tRow,3));
       fm.all('AgentNameHidden').value = ChequeGrid.getRowColData(tRow,8);
       //alert( "ҵ��Ա����"+ChequeGrid.getRowColData(tRow,7));
       fm.all('AgentCodeHidden').value = ChequeGrid.getRowColData(tRow,7);
       //alert( "ҵ��Ա����"+ChequeGrid.getRowColData(tRow,6));
       fm.all('MakeDateHidden').value = ChequeGrid.getRowColData(tRow,5);
       //alert( "�ɷ�����"+ChequeGrid.getRowColData(tRow,4));
       fm.all('ManageCom1Hidden').value = ChequeGrid.getRowColData(tRow,9);
  }

  //�ͻ�Ԥ���վ�
  if (fm.all("InvoiceType").value=='1')
   	{
   	 var tSel = CustomertGrid.getSelNo();
	   if( tSel == 0 || tSel == null )
	   {
	   	alert( "����ѡ��һ����¼���ٵ����ӡ��ť��" );
	   }
	   	 	var tRow = CustomertGrid.getSelNo() - 1;
       fm.all('CustomerNoHidden').value = CustomertGrid.getRowColData(tRow,1);
       //alert( "�ͻ���"+CustomertGrid.getRowColData(tRow,1));
       fm.all('CustomerNameHidden').value = CustomertGrid.getRowColData(tRow,2);
       //alert( "�ͻ�����"+CustomertGrid.getRowColData(tRow,2));
       fm.all('PayModeHidden').value = CustomertGrid.getRowColData(tRow,4);
       //alert( "���ѷ�ʽ"+CustomertGrid.getRowColData(tRow,4));
       fm.all('PayMoneyHidden').value = CustomertGrid.getRowColData(tRow,3);
       //alert( "�ɷѽ��"+CustomertGrid.getRowColData(tRow,3));
       fm.all('TempfeeNoHidden').value = CustomertGrid.getRowColData(tRow,5);
       //alert( "�ݽɷѺ�"+CustomertGrid.getRowColData(tRow,5));
       fm.all('ActuGetNoHidden').value = CustomertGrid.getRowColData(tRow,6);
       //alert( "�ݽɷѺ�"+CustomertGrid.getRowColData(tRow,5));
       fm.all('PayDateHidden').value = CustomertGrid.getRowColData(tRow,7);
       //alert( "�ɷ�����"+CustomertGrid.getRowColData(tRow,6));
       fm.all('CollectorHidden').value = CustomertGrid.getRowColData(tRow,8);
   		 //alert( "����Ա"+CustomertGrid.getRowColData(tRow,7));
	}

	//Ԥ�����ڱ����վ�
   if (fm.all("InvoiceType").value=='2')
   	{
   		var tSel = RNPremGrid.getSelNo();
	   if( tSel == 0 || tSel == null )
	   {
	   	alert( "����ѡ��һ����¼���ٵ����ӡ��ť��" );
	   }
	   	 	var tRow = RNPremGrid.getSelNo() - 1;
       fm.all('AppntNameHidden').value = RNPremGrid.getRowColData(tRow,1);
       //alert( "Ͷ��������"+RNPremGrid.getRowColData(tRow,1));
       fm.all('PaytoDateHidden').value = RNPremGrid.getRowColData(tRow,2);
       //alert( "�ɷ�����"+RNPremGrid.getRowColData(tRow,2));
       fm.all('ContNoHidden').value = RNPremGrid.getRowColData(tRow,3);
       //alert( "Ͷ������"+RNPremGrid.getRowColData(tRow,3));
       fm.all('TempfeeNo2Hidden').value = RNPremGrid.getRowColData(tRow,4);
       //alert( "Ԥ���"+RNPremGrid.getRowColData(tRow,4));
       fm.all('PayMoneyHidden').value = RNPremGrid.getRowColData(tRow,5);
       //alert( "�ɷѽ��"+RNPremGrid.getRowColData(tRow,4));
       fm.all('OperatorHidden').value = RNPremGrid.getRowColData(tRow,6);
       //alert( "����Ա"+RNPremGrid.getRowColData(tRow,5));
       fm.all('PayerHidden').value = RNPremGrid.getRowColData(tRow,7);
       //alert( "�ɷ���"+RNPremGrid.getRowColData(tRow,6));
       fm.all('IDNoHidden').value = RNPremGrid.getRowColData(tRow,8);
   		 //alert( "���֤��"+RNPremGrid.getRowColData(tRow,7));
   		}
}

//�жϸ����Ƿ�ȷʵ��ѡ��
function showOne(parm1, parm2) 
{
	if(fm.all(parm1).all('InpChequeGridSel').value == '1' )
	{
	  var index = (fm.all(parm1).all('ChequeGridNo').value - 1) % (turnPage.blockPageNum * turnPage.pageLineNum);
	  fm.PrtSeq.value = turnPage.arrDataCacheSet[index][0];
  }
}

//�վݴ�ӡ
function printInvoice()
{

	//֧Ʊ/��Ʊ����֪ͨ��
	if (fm.all("InvoiceType").value=='0')
	{
		var tSel = ChequeGrid.getSelNo();

	  if( tSel == 0 || tSel == null )
	  {
	  	return;
	  	alert( "����ѡ��һ�����д�ӡ��" );
	  }
		fm.action="../f1print/RNChequeTempNoticeSave.jsp";
  	fm.target="f1print";
    fm.submit();
	}

	//�ͻ�Ԥ���վ�
	if (fm.all("InvoiceType").value=='1')
	{
		var tSel = CustomertGrid.getSelNo();

	  if( tSel == 0 || tSel == null )
	  {
	  	alert( "����ѡ��һ�����д�ӡ��" );
	  	return;
	  }
		fm.action="../f1print/RNCustomerPreSaveInvoiceSave.jsp";
  	fm.target="f1print";
    fm.submit();
	}

	//Ԥ�����ڱ����վ�
	if (fm.all("InvoiceType").value=='2')
	{
		var tSel = RNPremGrid.getSelNo();

	  if( tSel == 0 || tSel == null )
	  {
	  	alert( "����ѡ��һ�����д�ӡ��" );
	  	return;
	  }
		fm.action="../f1print/RNPrePremInvoiceSave.jsp";
  	fm.target="f1print";
    fm.submit();
	}
}

function afterSubmit( FlagStr, content )
{
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
  }
}