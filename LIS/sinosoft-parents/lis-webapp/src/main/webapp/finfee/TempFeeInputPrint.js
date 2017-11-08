//               该文件中包含客户端需要处理的函数和事件

//程序名称：TempFeeInputPrint.js
//程序功能：暂收费票据打印
//创建日期：2005-12-21 14:55
//创 建 人：关巍
//更新记录:
//    更新人    更新日期     更新原因/内容


//使用翻页功能，必须建立为全局变量
var turnPage = new turnPageClass();


//使得从该窗口弹出的窗口能够聚焦
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

//根据不同的收据类型显示不同的查询条件
function afterCodeSelect(){ 
    //支票/汇票暂收通知书
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
		//客户预存收据
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
		//预交续期保费收据
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

// 查询按钮
function TempfeeQuery()
{
	//支票汇票暂收通知书
  if (fm.all('InvoiceType').value == '0')
  {
  	if(fm.all('ManageCom1').value == "" || fm.all('ManageCom1').value == null)
  	{
  		alert("收费机构为空！请输入");
  		fm.all('ManageCom1').focus();
  		return;
  		}
  	else if (fm.all('ManageCom1').value.length <6)
  		{
  			alert("收费机构必须为六位机构！请重新输入");
  		  fm.all('ManageCom1').focus();
  		  return;
  			}
  		if(fm.all('ChequeNo').value=="" || fm.all('ChequeNo').value==null)
  		{
  			alert("支票号码为空！请输入");
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
     	mySql1.setResourceName("finfee.TempFeeInputPrintSql"); //指定使用的properties文件名
     	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
     	mySql1.addSubPara(comCode);//指定传入的参数
     	mySql1.addSubPara(ChequeNo);//指定传入的参数
     	mySql1.addSubPara(MakeDate);//指定传入的参数
     	mySql1.addSubPara(PayMoney);//指定传入的参数
     	mySql1.addSubPara(ManageCom1);//指定传入的参数
     	mySql1.addSubPara(AccName);//指定传入的参数
     	var strSql=mySql1.getString();

      turnPage.queryModal(strSql, ChequeGrid);

	    if(ChequeGrid.mulLineCount =='0')
	    {
	    	alert("没有符合条件的数据");
	    }
	}

	//客户预存收据
	if (fm.all('InvoiceType').value == '1')
	{
  	if(fm.all('ManageCom2').value == "" || fm.all('ManageCom2').value == null)
  	{
  		alert("收费机构为空！请输入");
  		fm.all('ManageCom2').focus();
  		return;
  		}
  	else if (fm.all('ManageCom2').value.length <6)
  		{
  			alert("收费机构必须为六位机构！请重新输入");
  		  fm.all('ManageCom2').focus();
  		  return;
  			}
  		if(fm.all('CustomerNo').value=="" || fm.all('CustomerNo').value==null)
  		{
  			alert("客户编号为空！请输入");
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
     	mySql2.setResourceName("finfee.TempFeeInputPrintSql"); //指定使用的properties文件名
     	mySql2.setSqlId(sqlid2);//指定使用的Sql的id
     	mySql2.addSubPara(comCode);//指定传入的参数
     	mySql2.addSubPara(TempfeeNo);//指定传入的参数
     	mySql2.addSubPara(CustomerNo);//指定传入的参数
     	mySql2.addSubPara(PayMoney);//指定传入的参数
     	mySql2.addSubPara(ManageCom2);//指定传入的参数
     	mySql2.addSubPara(MakeDate);//指定传入的参数
     	mySql2.addSubPara(AccName);//指定传入的参数
     	var strSql=mySql2.getString();

       turnPage.queryModal(strSql, CustomertGrid);

	     if(CustomertGrid.mulLineCount =='0')
	     {
	     	alert("没有符合条件的数据");
	     }
	    }

	//预交续期保费收据
	if (fm.all('InvoiceType').value=='2')
	{
  	if(fm.all('ManageCom3').value == "" || fm.all('ManageCom3').value == null)
  	{
  		alert("收费机构为空！请输入");
  		fm.all('ManageCom3').focus();
  		return;
  		}
  	else if (fm.all('ManageCom3').value.length <6)
  		{
  			alert("收费机构必须为六位机构！请重新输入");
  		  fm.all('ManageCom3').focus();
  		  return;
  			}
  		if(fm.all('ContNo').value=="" || fm.all('ContNo').value==null)
  		{
  			alert("保单号为空！请输入");
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
//  	     	mySql1.setResourceName("finfee.TempFeeInputPrintSql"); //指定使用的properties文件名
//  	     	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
//  	     	mySql1.addSubPara(comCode);//指定传入的参数
//  	     	mySql1.addSubPara(ContNo);//指定传入的参数
//  	     	mySql1.addSubPara(PayMoney);//指定传入的参数
//  	     	mySql1.addSubPara(ManageCom3);//指定传入的参数
//  	     	mySql1.addSubPara(MakeDate);//指定传入的参数
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
//  	     	mySql1.setResourceName("finfee.TempFeeInputPrintSql"); //指定使用的properties文件名
//  	     	mySql1.setSqlId(sqlid1);//指定使用的Sql的id
//  	     	mySql1.addSubPara(comCode);//指定传入的参数
//  	     	mySql1.addSubPara(ContNo);//指定传入的参数
//  	     	mySql1.addSubPara(PayMoney);//指定传入的参数
//  	     	mySql1.addSubPara(ManageCom3);//指定传入的参数
//  	     	mySql1.addSubPara(MakeDate);//指定传入的参数
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
	     	alert("没有符合条件的数据");
	     }
	    }
}

function easyQueryAddClick()
{
  var arrReturn = new Array();

  //支票汇票暂收通知书
  if (fm.all("InvoiceType").value=='0')
  {
	 var tSel = ChequeGrid.getSelNo();
	 if( tSel == 0 || tSel == null )
	 {
	 	alert( "请先选择一条记录，再点击打印按钮。" );
	 }
	 	var tRow = ChequeGrid.getSelNo() - 1;
       fm.all('ChequeNoHidden').value = ChequeGrid.getRowColData(tRow,2);
       //alert( "支票号"+ChequeGrid.getRowColData(tRow,2));
       fm.all('OtherNoHidden').value = ChequeGrid.getRowColData(tRow,3);
       //alert( "其它号码"+ChequeGrid.getRowColData(tRow3));
       fm.all('PayMoneyHidden').value = ChequeGrid.getRowColData(tRow,6);
       //alert( "金额"+ChequeGrid.getRowColData(tRow,5));
       fm.all('AccNameHidden').value = ChequeGrid.getRowColData(tRow,4);
       //alert( "出票单位"+ChequeGrid.getRowColData(tRow,3));
       fm.all('AgentNameHidden').value = ChequeGrid.getRowColData(tRow,8);
       //alert( "业务员姓名"+ChequeGrid.getRowColData(tRow,7));
       fm.all('AgentCodeHidden').value = ChequeGrid.getRowColData(tRow,7);
       //alert( "业务员代码"+ChequeGrid.getRowColData(tRow,6));
       fm.all('MakeDateHidden').value = ChequeGrid.getRowColData(tRow,5);
       //alert( "缴费日期"+ChequeGrid.getRowColData(tRow,4));
       fm.all('ManageCom1Hidden').value = ChequeGrid.getRowColData(tRow,9);
  }

  //客户预存收据
  if (fm.all("InvoiceType").value=='1')
   	{
   	 var tSel = CustomertGrid.getSelNo();
	   if( tSel == 0 || tSel == null )
	   {
	   	alert( "请先选择一条记录，再点击打印按钮。" );
	   }
	   	 	var tRow = CustomertGrid.getSelNo() - 1;
       fm.all('CustomerNoHidden').value = CustomertGrid.getRowColData(tRow,1);
       //alert( "客户号"+CustomertGrid.getRowColData(tRow,1));
       fm.all('CustomerNameHidden').value = CustomertGrid.getRowColData(tRow,2);
       //alert( "客户姓名"+CustomertGrid.getRowColData(tRow,2));
       fm.all('PayModeHidden').value = CustomertGrid.getRowColData(tRow,4);
       //alert( "交费方式"+CustomertGrid.getRowColData(tRow,4));
       fm.all('PayMoneyHidden').value = CustomertGrid.getRowColData(tRow,3);
       //alert( "缴费金额"+CustomertGrid.getRowColData(tRow,3));
       fm.all('TempfeeNoHidden').value = CustomertGrid.getRowColData(tRow,5);
       //alert( "暂缴费号"+CustomertGrid.getRowColData(tRow,5));
       fm.all('ActuGetNoHidden').value = CustomertGrid.getRowColData(tRow,6);
       //alert( "暂缴费号"+CustomertGrid.getRowColData(tRow,5));
       fm.all('PayDateHidden').value = CustomertGrid.getRowColData(tRow,7);
       //alert( "缴费日期"+CustomertGrid.getRowColData(tRow,6));
       fm.all('CollectorHidden').value = CustomertGrid.getRowColData(tRow,8);
   		 //alert( "收银员"+CustomertGrid.getRowColData(tRow,7));
	}

	//预交续期保费收据
   if (fm.all("InvoiceType").value=='2')
   	{
   		var tSel = RNPremGrid.getSelNo();
	   if( tSel == 0 || tSel == null )
	   {
	   	alert( "请先选择一条记录，再点击打印按钮。" );
	   }
	   	 	var tRow = RNPremGrid.getSelNo() - 1;
       fm.all('AppntNameHidden').value = RNPremGrid.getRowColData(tRow,1);
       //alert( "投保人姓名"+RNPremGrid.getRowColData(tRow,1));
       fm.all('PaytoDateHidden').value = RNPremGrid.getRowColData(tRow,2);
       //alert( "缴费日期"+RNPremGrid.getRowColData(tRow,2));
       fm.all('ContNoHidden').value = RNPremGrid.getRowColData(tRow,3);
       //alert( "投保单号"+RNPremGrid.getRowColData(tRow,3));
       fm.all('TempfeeNo2Hidden').value = RNPremGrid.getRowColData(tRow,4);
       //alert( "预存号"+RNPremGrid.getRowColData(tRow,4));
       fm.all('PayMoneyHidden').value = RNPremGrid.getRowColData(tRow,5);
       //alert( "缴费金额"+RNPremGrid.getRowColData(tRow,4));
       fm.all('OperatorHidden').value = RNPremGrid.getRowColData(tRow,6);
       //alert( "操作员"+RNPremGrid.getRowColData(tRow,5));
       fm.all('PayerHidden').value = RNPremGrid.getRowColData(tRow,7);
       //alert( "缴费人"+RNPremGrid.getRowColData(tRow,6));
       fm.all('IDNoHidden').value = RNPremGrid.getRowColData(tRow,8);
   		 //alert( "身份证号"+RNPremGrid.getRowColData(tRow,7));
   		}
}

//判断该行是否确实被选中
function showOne(parm1, parm2) 
{
	if(fm.all(parm1).all('InpChequeGridSel').value == '1' )
	{
	  var index = (fm.all(parm1).all('ChequeGridNo').value - 1) % (turnPage.blockPageNum * turnPage.pageLineNum);
	  fm.PrtSeq.value = turnPage.arrDataCacheSet[index][0];
  }
}

//收据打印
function printInvoice()
{

	//支票/汇票暂收通知书
	if (fm.all("InvoiceType").value=='0')
	{
		var tSel = ChequeGrid.getSelNo();

	  if( tSel == 0 || tSel == null )
	  {
	  	return;
	  	alert( "请先选择一条进行打印。" );
	  }
		fm.action="../f1print/RNChequeTempNoticeSave.jsp";
  	fm.target="f1print";
    fm.submit();
	}

	//客户预存收据
	if (fm.all("InvoiceType").value=='1')
	{
		var tSel = CustomertGrid.getSelNo();

	  if( tSel == 0 || tSel == null )
	  {
	  	alert( "请先选择一条进行打印。" );
	  	return;
	  }
		fm.action="../f1print/RNCustomerPreSaveInvoiceSave.jsp";
  	fm.target="f1print";
    fm.submit();
	}

	//预交续期保费收据
	if (fm.all("InvoiceType").value=='2')
	{
		var tSel = RNPremGrid.getSelNo();

	  if( tSel == 0 || tSel == null )
	  {
	  	alert( "请先选择一条进行打印。" );
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
  }
}