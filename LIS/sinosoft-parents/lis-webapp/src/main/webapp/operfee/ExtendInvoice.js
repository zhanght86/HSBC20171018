//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage1 = new turnPageClass();
var mDebug="0";
var mOperate="";
var showInfo;
var ttmanagecom = "";
window.onfocus=myonfocus;

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

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
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
  	//parent.fraInterface.initForm();
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    //showDiv(operateButton,"true");
    //showDiv(inputButton,"false");
  }
}

//////////////////////////////////////////////�����Ҫ�洢Ҫ��Ӧ�Ķ�����
function easyQueryAddClick()
{
	//var tSelNo = PolGrid.getSelNo()-1;
	//fm.PolNo.value = PolGrid.getRowColData(tSelNo,1);	
        var arrReturn = new Array();
	var tSel = ContGrid.getSelNo();	 
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	}	
	else
	{
			var tRow = ContGrid.getSelNo() - 1;	        
             fm.PayCode1.value = ContGrid.getRowColData(tRow,1);
             fm.ContNo1.value = ContGrid.getRowColData(tRow,2);
             fm.AccNo1.value = ContGrid.getRowColData(tRow,3);
             fm.AccName1.value = ContGrid.getRowColData(tRow,4);
             fm.senddate.value = ContGrid.getRowColData(tRow,5);
             fm.paymoney.value = ContGrid.getRowColData(tRow,6); 
             
        } 	
}

//������Ʊ��ӡ

function PPrint()
{
	var tSel = ContGrid.getSelNo();	 

	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ�����д�ӡ��" );
	}	
	else
	{
		fm.AppntNoHidden.value = ContGrid.getRowColData(tSel-1,1); 
        fm.AppntNameHidden.value =  ContGrid.getRowColData(tSel-1,2);
	}
	fm.action="XQWoodcutterNoticeSave.jsp";
	fm.target="f1print";
	fm.fmtransact.value="";
	fm.submit();
}

function CheckDataCom()
{
	var tEnterDate = 0 ;

	if (fm.PrintType.value==02&&(fm.TempFeeNo.value==""||fm.TempFeeNo.value==null||fm.TempFeeNo.value=="null"))
	{
		alert("Ԥ�վݻ���Ʊ����¼��Ԥ��ţ�������Ԥ�վ��ϵ�Ԥ��š�");
		fm.TempFeeNo.focus();
		return false;
		}

	 if (fm.StartDate.value !=null&&fm.StartDate.value!=""&& fm.EndDate.value !=null&&fm.EndDate.value!="")
   {
     if(fm.StartDate.value > fm.EndDate.value)
     {
     		alert("��ʼ���ڴ�����ֹ���ڣ���ȷ�Ϻ��������룡");
     		return false;
     	}
     tEnterDate = 1;
   }

	if(tEnterDate == 0)
	{
	 if (fm.ContNo.value !=null&&fm.ContNo.value!="")
   {
     	return true;
   }
  else
  	{
  		alert("���ںͱ����ţ�����Ҫ¼����һ");	
  		return false;
  	}
	}

	return true;
}

function EasyQueryClick()
{
	
	if( verifyInput2() == false ) return false;
	if(CheckDataCom() == false)
	{
				return false;
	}


	if(document.all('PrintType').value == '02')
	{
				EasyQueryb();
	}
 else
 	{
/* 				var checkSql = " select * from ljtempfeeclass "+
		 									 "  where paymode = '7' "+
		 									 "    and bankcode = (select code "+
		 									 "			                from ldcode1 "+
		 									 "                     where codetype = 'BankBigList' "+
		 									 "                       and comcode = '"+comCode.substring(1,4)+"'"+
		 									 "											 and code = ljtempfeeclass.bankcode) "
											 + getWherePart( 'OtherNo','ContNo' )
											 + getWherePart( 'TempFeeNo','GetNoticeNo' )
											 + getWherePart( 'TempFeeNo','TempFeeNo' )*/
				var checkSql= "";
 				var mySql1=new SqlClass();
 			    mySql1.setResourceName("operfee.ExtendInvoiceSql");
 			    mySql1.setSqlId("ExtendInvoiceSql1");
 			    mySql1.addSubPara(comCode.substring(1,4));
 			    mySql1.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);
 			    mySql1.addSubPara(window.document.getElementsByName(trim('GetNoticeNo'))[0].value);
 			    mySql1.addSubPara(window.document.getElementsByName(trim('TempFeeNo'))[0].value);
 			   checkSql = mySql1.getString();
				var checkData = easyExecSql(checkSql,1,0);
				if(checkData == null)
				{
							EasyQueryC();
				}
				else
				{
							EasyQueryA();
				}
 	}
}

//�����̴�ӡ��Ʊ
function EasyQueryA()
{
	/*var strSql = " select a.otherno,a.StandbyFlag1,a.StandbyFlag3,a.StandbyFlag4 ,"+
							 "   			a.PrtSeq ,'','', a.managecom "+
							 "   from loprtmanager a, ljtempfee b "+
							 " 	where 1=1 and b.tempfeeno = a.StandbyFlag1 "+
							 "    and b.enteraccdate is not null  and a.StateFlag = '0' "+
							 "		and b.PolicyCom like '"+comCode+"%%'"+
							 "		and  b.managecom like '"+comCode.substring(1,4)+'%%'+"'"
							 + getWherePart( 'a.OtherNo','ContNo' )
							 + getWherePart( 'b.TempFeeNo','TempFeeNo')
							 + getWherePart( 'a.StandbyFlag1','GetNoticeNo' )
							 + getWherePart( 'a.StandbyFlag3','StartDate','>=')
							 + getWherePart( 'a.StandbyFlag3','EndDate','<=' )

   if(fm.ManageCom.value!=null&&fm.ManageCom.value!="")
   {
   	 strSql = strSql + " and b.PolicyCom like '"+fm.ManageCom.value+"%%'";
   }

   if(fm.SecPayMode.value!=null&&fm.SecPayMode.value!="")
   {
   	 strSql = strSql + " and a.otherno in (select contno from lccont where paymode ='"+fm.SecPayMode.value+"' )";
   }

  if(fm.ContType.value !=null && fm.ContType.value != "")
  {
  	 if(fm.ContType.value == "01")
  	 {
  	 	strSql = strSql + " and a.code = '32'";
  	 }
  	 if(fm.ContType.value == "02")
  	 {
  	 	strSql = strSql + " and a.code = '33'";
  	 }
  	 if(fm.ContType.value == "03")
  	 {
  	 	strSql = strSql + " and a.code = '36'";
  	 }
  	 if(fm.ContType.value == "04")
  	 {
  	 	strSql = strSql + " and a.code = '36'";
  	 }
  }
	else
	{
		strSql = strSql + " and a.code in ('32','33','36')";
	}*/
	

	var addstr0 = "";
	if (fm.SecPayMode.value != null && fm.SecPayMode.value != "") {
		addstr0 = addstr0
				+ " and a.otherno in (select contno from lccont where paymode ='"
				+ fm.SecPayMode.value + "' )";
	}
	var addstr = "";
	if (fm.ContType.value != null && fm.ContType.value != "") {
		if (fm.ContType.value == "01") {
			addstr = addstr + " and a.code = '32'";
		}
		if (fm.ContType.value == "02") {
			addstr = addstr + " and a.code = '33'";
		}
		if (fm.ContType.value == "03") {
			addstr = addstr + " and a.code = '36'";
		}
		if (fm.ContType.value == "04") {
			addstr = addstr + " and a.code = '36'";
		}
	} else {
		addstr = addstr + " and a.code in ('32','33','36')";
	}
   var strSql = "";
   var mySql2=new SqlClass();
   mySql2.setResourceName("operfee.ExtendInvoiceSql");
   mySql2.setSqlId("ExtendInvoiceSql2");
   mySql2.addSubPara(comCode);
   mySql2.addSubPara(comCode.substring(1,4));
   mySql2.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);
   mySql2.addSubPara(window.document.getElementsByName(trim('TempFeeNo'))[0].value);
   mySql2.addSubPara(window.document.getElementsByName(trim('GetNoticeNo'))[0].value);
   mySql2.addSubPara(window.document.getElementsByName(trim('StartDate'))[0].value);
   mySql2.addSubPara(window.document.getElementsByName(trim('EndDate'))[0].value);
   mySql2.addSubPara(fm.ManageCom.value);
   mySql2.addSubPara(addstr0);
   mySql2.addSubPara(addstr);
   strSql = mySql2.getString();
   turnPage.queryModal(strSql, ContGrid);

	if(ContGrid.mulLineCount ==0)
	{
		alert("û�з�������������");
		initContGrid();
	}
}


//Ԥ�վݻ���Ʊ
function EasyQueryb()
{
	/*var strSql = " select a.otherno,a.StandbyFlag1,a.StandbyFlag3,a.StandbyFlag4 ,a.PrtSeq ,'','',"+
							 " 				a.managecom from loprtmanager a  , ljapay b ,ljtempfee c "+
							 "	where 1=1 and b.otherno = c.otherno and b.getnoticeno = a.StandbyFlag1 "+
							 "    and b.enteraccdate is not null  and a.StateFlag = '0' and c.tempfeetype = '3'"+
							 " 		and c.managecom like '"+comCode+"%%'"
							 + getWherePart( 'a.OtherNo','ContNo' )
							 + getWherePart( 'c.TempFeeNo','TempFeeNo' )
							 + getWherePart( 'a.StandbyFlag1','GetNoticeNo' )
							 + getWherePart( 'a.StandbyFlag3','StartDate','>=' )
							 + getWherePart( 'a.StandbyFlag3','EndDate','<=' )
							 + getWherePart( 'c.ManageCom','ManageCom','like' )

   if(fm.SecPayMode.value!=null&&fm.SecPayMode.value!="")
   {
   	 strSql = strSql + " and a.otherno in (select contno from lccont where paylocation ='"+fm.SecPayMode.value+"' )";
   }

  if(fm.ContType.value !=null && fm.ContType.value != "")
  {
  	 if(fm.ContType.value == "01")
  	 {
  	 	strSql = strSql + " and a.code = '32'";
  	 }
  	 if(fm.ContType.value == "02")
  	 {
  	 	strSql = strSql + " and a.code = '33'";
  	 }
  	 if(fm.ContType.value == "03")
  	 {
  	 	strSql = strSql + " and a.code = '36'";
  	 }
  	 if(fm.ContType.value == "04")
  	 {
  	 	strSql = strSql + " and a.code = '36'";
  	 }
  }
	else
	{
		strSql = strSql + " and a.code in ('32','33','36')";
	}*/
	var addstr0 = "";
	if (fm.SecPayMode.value != null && fm.SecPayMode.value != "") {
		addstr0 = addstr0
				+ " and a.otherno in (select contno from lccont where paylocation ='"
				+ fm.SecPayMode.value + "' )";
	}
	var addstr = "";
	if (fm.ContType.value != null && fm.ContType.value != "") {
		if (fm.ContType.value == "01") {
			addstr = addstr + " and a.code = '32'";
		}
		if (fm.ContType.value == "02") {
			addstr = addstr + " and a.code = '33'";
		}
		if (fm.ContType.value == "03") {
			addstr = addstr + " and a.code = '36'";
		}
		if (fm.ContType.value == "04") {
			addstr = addstr + " and a.code = '36'";
		}
	} else {
		addstr = addstr + " and a.code in ('32','33','36')";
	}
   var strSql = "";
   var mySql3=new SqlClass();
   mySql3.setResourceName("operfee.ExtendInvoiceSql");
   mySql3.setSqlId("ExtendInvoiceSql2");
   mySql3.addSubPara(comCode);
   mySql3.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);
   mySql3.addSubPara(window.document.getElementsByName(trim('TempFeeNo'))[0].value);
   mySql3.addSubPara(window.document.getElementsByName(trim('GetNoticeNo'))[0].value);
   mySql3.addSubPara(window.document.getElementsByName(trim('StartDate'))[0].value);
   mySql3.addSubPara(window.document.getElementsByName(trim('EndDate'))[0].value);
   mySql3.addSubPara(fm.ManageCom.value);
   mySql3.addSubPara(addstr0);
   mySql3.addSubPara(addstr);
   strSql = mySql3.getString();
   turnPage.queryModal(strSql, ContGrid);

	if(ContGrid.mulLineCount ==0)
	{
		alert("û�з�������������");
		initContGrid();
	}
}


//������Ʊ��ӡ
function EasyQueryC()
{
	/*var strSql = " select a.otherno,a.StandbyFlag1,a.StandbyFlag3,a.StandbyFlag4 ,"+
							 "   			a.PrtSeq ,'','', a.managecom "+
							 "   from loprtmanager a, ljtempfee b "+
							 " 	where 1=1 and b.tempfeeno = a.StandbyFlag1 "+
							 "    and b.enteraccdate is not null  and a.StateFlag = '0' "+
							 "    and exists (select 'X' from ljtempfeeclass where tempfeeno = a.standbyflag1 and managecom like '"+comCode+"%%') "+
							 "		and b.managecom like '"+comCode+"%%'"
							 + getWherePart( 'a.OtherNo','ContNo' )
							 + getWherePart( 'b.TempFeeNo','TempFeeNo')
							 + getWherePart( 'a.StandbyFlag1','GetNoticeNo' )
							 + getWherePart( 'a.StandbyFlag3','StartDate','>=')
							 + getWherePart( 'a.StandbyFlag3','EndDate','<=' )
							 + getWherePart( 'a.ManageCom','ManageCom','like' )

   if(fm.SecPayMode.value!=null&&fm.SecPayMode.value!="")
   {
   	 strSql = strSql + " and a.otherno in (select contno from lccont where paymode ='"+fm.SecPayMode.value+"' )";
   }

  if(fm.ContType.value !=null && fm.ContType.value != "")
  {
  	 if(fm.ContType.value == "01")
  	 {
  	 	strSql = strSql + " and a.code = '32'";
  	 }
  	 if(fm.ContType.value == "02")
  	 {
  	 	strSql = strSql + " and a.code = '33'";
  	 }
  	 if(fm.ContType.value == "03")
  	 {
  	 	strSql = strSql + " and a.code = '36'";
  	 }  	 
  	 if(fm.ContType.value == "04")
  	 {
  	 	strSql = strSql + " and a.code = '36'";
  	 }
  }
else
	{
		strSql = strSql + " and a.code in ('32','33','36')";
	} */  
	var addstr0 = "";
	if (fm.SecPayMode.value != null && fm.SecPayMode.value != "") {
		addstr0 = addstr0
				+ " and a.otherno in (select contno from lccont where paymode ='"
				+ fm.SecPayMode.value + "' )";
	}
	var addstr = "";
	if (fm.ContType.value != null && fm.ContType.value != "") {
		if (fm.ContType.value == "01") {
			addstr = addstr + " and a.code = '32'";
		}
		if (fm.ContType.value == "02") {
			addstr = addstr + " and a.code = '33'";
		}
		if (fm.ContType.value == "03") {
			addstr = addstr + " and a.code = '36'";
		}
		if (fm.ContType.value == "04") {
			addstr = addstr + " and a.code = '36'";
		}
	} else {
		addstr = addstr + " and a.code in ('32','33','36')";
	}
   var strSql = "";
   var mySql4=new SqlClass();
   mySql4.setResourceName("operfee.ExtendInvoiceSql");
   mySql4.setSqlId("ExtendInvoiceSql2");
   mySql4.addSubPara(comCode);
   mySql4.addSubPara(comCode);
   mySql4.addSubPara(window.document.getElementsByName(trim('ContNo'))[0].value);
   mySql4.addSubPara(window.document.getElementsByName(trim('TempFeeNo'))[0].value);
   mySql4.addSubPara(window.document.getElementsByName(trim('GetNoticeNo'))[0].value);
   mySql4.addSubPara(window.document.getElementsByName(trim('StartDate'))[0].value);
   mySql4.addSubPara(window.document.getElementsByName(trim('EndDate'))[0].value);
   mySql4.addSubPara(fm.ManageCom.value);
   mySql4.addSubPara(addstr0);
   mySql4.addSubPara(addstr);
   strSql = mySql4.getString();
   turnPage.queryModal(strSql, ContGrid);

	if(ContGrid.mulLineCount ==0)
	{
		alert("û�з�������������");
		initContGrid();
	}
}

	function certifyInput()
	{
		var tSel = ContGrid.getSelNo();	
//		var tSql = " select * from lzcard where startno <='"+fm.ChequNo.value+"'  and  endno >='"+fm.ChequNo.value+"' and certifycode = '"+fm.CertifyCode.value+"' and stateflag <> '1' ";
		var tSql = "";
		var mySql5 = new SqlClass();
		mySql5.setResourceName("operfee.ExtendInvoiceSql");
		mySql5.setSqlId("ExtendInvoiceSql5");
		mySql5.addSubPara(fm.ChequNo.value);
		mySql5.addSubPara(fm.ChequNo.value);
		mySql5.addSubPara(fm.CertifyCode.value);
		strSql = mySql5.getString();
		var arrResult3 = easyExecSql(tSql, 1, 0);
		  if(arrResult3 == null)
		  {
		   alert("�˷�Ʊ�Ų�����,����û�д˵�֤������");	
		   return false;
		  }
		  
	 return true;
	}

function showOne(parm1, parm2) {	
  //�жϸ����Ƿ�ȷʵ��ѡ��
	if(document.all(parm1).all('InpPolGridSel').value == '1' ) {
	  var index = (document.all(parm1).all('PolGridNo').value - 1) % (turnPage.blockPageNum * turnPage.pageLineNum);
	  fm.PrtSeq.value = turnPage.arrDataCacheSet[index][0];
  }
}

function NoPrint() {	
  var i = 0;
  var tSel = ContGrid.getSelNo();	
      	 
       if( fm.ChequNo.value =='' ||  fm.ChequNo.value == 'null' )
        {
      	alert( "��û�����뷢Ʊ���룬��ȷ�Ϻ������Ʊ����" );
      	return false;
      }
       
	  if (!certifyInput()) return false;     
	  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   

  if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		ContNo = ContGrid.getRowColData(tSel-1,1);
		fm.OtherNo.value = ContNo;
		fm.GetNoticeNoHidden.value = ContGrid.getRowColData(tSel-1,2);
		fm.PreSeq.value = ContGrid.getRowColData(tSel-1,5);
		ttmanagecom = ContGrid.getRowColData(tSel-1,6);
	
		fm.type.value =  ContGrid.getRowColData(tSel-1,7);
		fm.fmtransact.value = "Print";
		fm.target = "f1print";	
		fm.submit();
		showInfo.close();
		initContGrid();
	}
}
