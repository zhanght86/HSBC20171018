var PassFlag = '0';
var ComLength= 8;
var ScreenWidth=640;
var ScreenHeight=480;
var showInfo;
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var turnPage11 = new turnPageClass();  
var turnPage22 = new turnPageClass();   


function queryMoneyDetail()
{
  //alert("ok");
  var tEdorNo = fm.all('EdorNo').value;
  var tEdorType = fm.all('EdorType').value;
  var strSQL = "";
  var bnfSQL = "";
  if(tEdorNo != null || tEdorNo != ''){
      //strSQL = "Select a.GetNoticeNo, a.FeeFinaType, a.ContNo,b.insuredname, a.GetDate, a.RiskCode, a.GetMoney From LJSGetEndorse a,lccont b where a.otherno='"+tEdorNo+"' and a.contno = b.contno and a.FeeOperationType='"+tEdorType+"' order by a.ContNo";
var sqlid824140150="DSHomeContSql824140150";
var mySql824140150=new SqlClass();
mySql824140150.setResourceName("bq.SubAccPayInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824140150.setSqlId(sqlid824140150);//ָ��ʹ�õ�Sql��id
mySql824140150.addSubPara(tEdorNo);//ָ������Ĳ���
mySql824140150.addSubPara(tEdorType);//ָ������Ĳ���
strSQL=mySql824140150.getString();
    
//    strSQL = "SELECT a.ContNo,a.InsuredNo,(SELECT Name FROM LCInsured WHERE InsuredNo = a.InsuredNo AND ContNo = a.ContNo),Sum(a.GetMoney) FROM LJSGetEndorse a WHERE a.OtherNo = '"+tEdorNo+"' AND NOT EXISTS (SELECT * FROM LPBnf WHERE InsuredNo = a.InsuredNo AND ContNo = a.ContNo AND EdorNo = a.OtherNo AND EdorType = '"+tEdorType+"') GROUP BY a.ContNo,a.InsuredNo";
    
var sqlid824140350="DSHomeContSql824140350";
var mySql824140350=new SqlClass();
mySql824140350.setResourceName("bq.SubAccPayInputSql");//ָ��ʹ�õ�properties�ļ���
mySql824140350.setSqlId(sqlid824140350);//ָ��ʹ�õ�Sql��id
mySql824140350.addSubPara(tEdorNo);//ָ������Ĳ���
mySql824140350.addSubPara(tEdorType);//ָ������Ĳ���
bnfSQL=mySql824140350.getString();
    
//    bnfSQL = "SELECT b.ContNo,b.InsuredNo,(SELECT Name FROM LCInsured WHERE InsuredNo = b.InsuredNo AND ContNo = b.ContNo),(SELECT sum(GetMoney) FROM LJSGetEndorse WHERE OtherNo = b.EdorNo AND ContNo = b.ContNo AND InsuredNo = b.InsuredNo),b.Remark,(SELECT CodeName FROM LDCODE WHERE CodeType = 'edorgetpayform' AND Code = b.Remark),b.Name,b.IDNo,b.BankCode,(SELECT BankName FROM LDBank WHERE BankCode = b.BankCode),b.BankAccNo,b.AccName FROM LPBnf b WHERE b.EdorNo = '"+tEdorNo+"' AND b.EdorType = '"+tEdorType+"'";
  }
  turnPage11.queryModal(strSQL, MoneyDetailGrid);
  turnPage22.queryModal(bnfSQL, SubAccGrid);
}
function afterSubmit(FlagStr, content, Result) 
{

  showInfo.close();    
  if (FlagStr == "Fail") 
  {             
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
    //pEdorFlag = true;
    
      var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
      //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");  
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    
    //parent.fraInterface.fm.all('GetMoney').value = Result;
  } 
     
  initForm(); 
	resetSubAccInfo();
  unlockScreen('lkscreen');  
    
}

function diskInput()
{
	
	if ( fm.GrpContNo.value == "")
	{
		alert("���Ȳ�ѯ������Ϣ");
		return ;
	}
	  if (fm.PEdorState.value == 2)
    {
        alert("��ȫ�����Ѿ�����ȷ��");
        return;
    }
	  var tGrpContNo = fm.GrpContNo.value;
    var tEdorNo = fm.EdorNo.value;
    var tEdorAcceptNo = fm.EdorAcceptNo.value;
    var tEdorType = fm.EdorType.value;

		var tFileName=fm2.all('FileName').value;
		//alert(tFileName);
		if ( tFileName.indexOf("\\")>0 ) tFileName=tFileName.substring(tFileName.lastIndexOf("\\")+1);
		if ( tFileName.indexOf("/")>0 ) tFileName=tFileName.substring(tFileName.lastIndexOf("/")+1);
		if ( tFileName.indexOf("_")>0) tFileName=tFileName.substring( 0,tFileName.indexOf("_"));
		if ( tFileName.indexOf(".")>0) tFileName=tFileName.substring( 0,tFileName.indexOf("."));
		if(tFileName != tGrpContNo)
		{
			alert("�ļ��������屣���Ų�һ��,�����ļ���!");
			return ;
		}
   //alert("�ɹ�����");	
    //fm.all("Transact").value = "IMPORT||EDOR"

    
    var showStr = "���ڱ��棬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 

var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
var iWidth=550;      //�������ڵĿ��; 
var iHeight=250;     //�������ڵĸ߶�; 
var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

showInfo.focus();

	
    fm2.action = "./SubAccLoadDetail.jsp?GrpContNo="+tGrpContNo+"&EdorNo="+tEdorNo+"&EdorAcceptNo="+tEdorAcceptNo+"&EdorType="+tEdorType+"";
    lockScreen('lkscreen');
    fm2.submit();
}

function addSubAcc()
{
	    if (fm.PEdorState.value == 2)
    {
        alert("��ȫ�����Ѿ�����ȷ��");
        return;
    }
		var tSelNo = MoneyDetailGrid.getSelNo()-1;
		//alert(tSelNo);
    if (tSelNo < 0)
    {
        alert("��ѡ����Ҫ��Ӹ�����Ϣ����ϸ��¼��");
        return;
    }
    
		fm.ContNo.value = MoneyDetailGrid.getRowColData(tSelNo, 1);
		fm.InsuredNo.value = MoneyDetailGrid.getRowColData(tSelNo, 2);
		var tPayMode = fm.PayMode.value;
		var tDrawer = fm.Drawer.value;
		var tDrawerIDNo = fm.DrawerIDNo.value;
		if(tPayMode == null || trim(tPayMode) == "")
		{
			alert("��ѡ���˷ѷ�ʽ!");
			return
		}
		if(tDrawer == null || trim(tDrawer) == "")
		{
			alert("��¼����ȡ��!");
			return
		}
		if(tDrawerIDNo == null || trim(tDrawerIDNo) == "")
		{
			alert("��¼����ȡ�����֤��!");
			return
		}
		if(!checkIdCard(tDrawerIDNo))
		{
			return;
		}
		if(tPayMode == "4" || tPayMode=="7")
		{
			//����ת��
			var tBankCode = fm.BankCode.value;
			var tBankAccNo = fm.BankAccNo.value;
			var tAccName = fm.AccName.value;
			if(tBankCode == null || trim(tBankCode) == "")
			{
				alert("��ѡ�񿪻�����!");
				return
			}
			if(tBankAccNo == null || trim(tBankAccNo) == "")
			{
				alert("��¼�������˻�!");
				return
			}
			if(tAccName == null || trim(tAccName) == "")
			{
				alert("��¼���˻���!");
				return
			}
		}else {
				fm.BankCode.value == "";
				fm.BankAccNo.value == "";
				fm.AccName.value == "";
		}
		fm.fmtransact.value = "INSERT";
		var showStr = "���ڱ��棬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./SubAccPaySubmit.jsp";
    lockScreen('lkscreen');
    fm.submit();
}

function updateSubAcc()
{
	   if (fm.PEdorState.value == 2)
    {
        alert("��ȫ�����Ѿ�����ȷ��");
        return;
    }
		var tSelNo = SubAccGrid.getSelNo()-1;
		//alert(tSelNo);
    if (tSelNo < 0)
    {
        alert("��ѡ����Ҫ�޸ĵĸ�����¼��");
        return;
    }
    
		fm.ContNo.value = SubAccGrid.getRowColData(tSelNo, 1);
		fm.InsuredNo.value = SubAccGrid.getRowColData(tSelNo, 2);
		var tPayMode = fm.PayMode.value;
		var tDrawer = fm.Drawer.value;
		var tDrawerIDNo = fm.DrawerIDNo.value;
		if(tPayMode == null || trim(tPayMode) == "")
		{
			alert("��ѡ���˷ѷ�ʽ!");
			return
		}
		if(tDrawer == null || trim(tDrawer) == "")
		{
			alert("��¼����ȡ��!");
			return
		}
		if(tDrawerIDNo == null || trim(tDrawerIDNo) == "")
		{
			alert("��¼����ȡ�����֤��!");
			return
		}
		if(!checkIdCard(tDrawerIDNo))
		{
			return;
		}
		if(tPayMode == "4" || tPayMode=="7")
		{
			//����ת��
			var tBankCode = fm.BankCode.value;
			var tBankAccNo = fm.BankAccNo.value;
			var tAccName = fm.AccName.value;
			if(tBankCode == null || trim(tBankCode) == "")
			{
				alert("��ѡ�񿪻�����!");
				return
			}
			if(tBankAccNo == null || trim(tBankAccNo) == "")
			{
				alert("��¼�������˻�!");
				return
			}
			if(tAccName == null || trim(tAccName) == "")
			{
				alert("��¼���˻���!");
				return
			}
		}else {
				fm.BankCode.value == "";
				fm.BankAccNo.value == "";
				fm.AccName.value == "";
		}
		fm.fmtransact.value = "UPDATE";
		var showStr = "���ڱ��棬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./SubAccPaySubmit.jsp";
    lockScreen('lkscreen');
    fm.submit();
}

function deleteSubAcc()
{
	    if (fm.PEdorState.value == 2)
    {
        alert("��ȫ�����Ѿ�����ȷ��");
        return;
    }
		var tSelNo = SubAccGrid.getSelNo()-1;
		//alert(tSelNo);
    if (tSelNo < 0)
    {
        alert("��ѡ����Ҫɾ���ĸ�����¼��");
        return;
    }
    
		fm.ContNo.value = SubAccGrid.getRowColData(tSelNo, 1);
		fm.InsuredNo.value = SubAccGrid.getRowColData(tSelNo, 2);
		
		fm.fmtransact.value = "DELETE";
		var showStr = "���ڱ��棬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px"); 
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    fm.action = "./SubAccPaySubmit.jsp";
    lockScreen('lkscreen');
    fm.submit();
}
function resetSubAccInfo()
{
	fm.ContNo.value = "";
	fm.InsuredNo.value = "";
	fm.PayMode.value = "";
	fm.PayModeName.value = "";
	fm.Drawer.value = "";
	fm.DrawerIDNo.value = "";
	fm.BankCode.value = "";
	fm.BankName.value = "";
	fm.BankAccNo.value = "";
	fm.AccName.value = "";
}
function ShowSubAccInfo()
{
		var tSelNo = SubAccGrid.getSelNo()-1;
		fm.ContNo.value = SubAccGrid.getRowColData(tSelNo, 1);
		fm.InsuredNo.value = SubAccGrid.getRowColData(tSelNo, 2);
		fm.PayMode.value = SubAccGrid.getRowColData(tSelNo, 5);
		fm.PayModeName.value = SubAccGrid.getRowColData(tSelNo,6);
		fm.Drawer.value = SubAccGrid.getRowColData(tSelNo, 7);
		fm.DrawerIDNo.value = SubAccGrid.getRowColData(tSelNo, 8);
		fm.BankCode.value = SubAccGrid.getRowColData(tSelNo, 9);
		fm.BankName.value = SubAccGrid.getRowColData(tSelNo, 10);
		fm.BankAccNo.value = SubAccGrid.getRowColData(tSelNo, 11);
		fm.AccName.value = SubAccGrid.getRowColData(tSelNo, 12);
}