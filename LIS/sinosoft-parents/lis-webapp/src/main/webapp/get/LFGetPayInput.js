//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var submitFlag="0";
var queryFlag="0";
var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var turnPage2 = new turnPageClass();

var tResourceName="get.LFGetPayInputSql";
var tResourceSQL1="LFGetPayInputSql1"; 
var tResourceSQL2="LFGetPayInputSql2"; 
var tResourceSQL3="LFGetPayInputSql3"; 
var tResourceSQL4="LFGetPayInputSql4"; 


//�ύ�����水ť��Ӧ����
function submitForm()
{                              
  if (!window.confirm("�Ƿ�ȷ�ϱ�������?"))
    return;         
    
  if(document.all('CommitFlag').value!="1")
  {                                  
    alert("�ϴ�����δ�������,�����ĵȴ�!");
    return;
  }
  document.all('CommitFlag').value = "0";
  var i = 0;
  var tSelNum = SubPayGrid.mulLineCount;
	var tFlag = false;
	//alert('aaa'+tSelNum);
	for (i=0;i<=tSelNum-1;i++)
	{
		if (SubPayGrid.getChkNo(i))
		{
			tFlag = true;
		}
	}	
	if (tFlag)
	{
		submitFlag="1";
	  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	  //showSubmitFrame(mDebug);
	  fm.action="./LFGetPaySave.jsp"
	  fm.submit(); //�ύ
	}
	else
	{
		alert("��ѡ�������Ϣ!");
		return;
	}
}

function easyQueryClick()
{
  // ��ʼ�����
   
  if(fm.GrpContNo.value == null ||trim(fm.GrpContNo.value) =="")
  {
  	alert("��¼�����屣����");
  	return;
  }
  divLCGet.style.display ='';
	initLJSGetGrid(); 
  // ��дSQL���
 // var strSQL = "";	
 // strSQL = "select ljsget.getnoticeno,ljsget.otherno,LCCont.GrpContNo,LCCont.insuredname,ljsget.sumgetmoney,ljsget.getdate,ljsget.makedate,ljsget.operator "
 //        + "from ljsget,LCCont "
 //        + "where ljsget.otherno=LCCont.ContNo and ljsget.othernotype='2' and ljsget.managecom like '"+manageCom+"%%' and LCCont.GrpContNo!='00000000000000000000'"
 //        + getWherePart( 'LCCont.GrpContNo','GrpContNo' )
 //        + " order by ljsget.getnoticeno";
         
	//strSQL = "select LCCont.GrpContNo,ljsget.otherno,LCCont.insuredname,sum(ljsget.sumgetmoney),ljsget.operator "
  //       + "from ljsget,LCCont "
  //       + "where ljsget.otherno=LCCont.ContNo and ljsget.othernotype='2' and ljsget.getdate <= sysdate and ljsget.managecom like '"+manageCom+"%%' and LCCont.GrpContNo!='00000000000000000000'"
  //       + getWherePart( 'LCCont.GrpContNo','GrpContNo' )
  //       + " group by LCCont.GrpContNo,ljsget.otherno,LCCont.insuredname,ljsget.operator order by ljsget.otherno";
  
  
  strSQL = wrapSql(tResourceName,tResourceSQL1,[fm.manageCom.value,fm.GrpContNo.value]);       
  
  
  //��ѯSQL�����ؽ���ַ���
  turnPage2.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage2.strQueryResult) {
    alert("��ѯʧ��,û�з�������������!");
    divLCGet.style.display ='none';
    divDiskApp.style.display ='none';
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage2.arrDataCacheSet = decodeEasyQueryResult(turnPage2.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage2.pageDisplayGrid = LJSGetGrid;    
          
  //����SQL���
  turnPage2.strQuerySql = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage2.pageIndex = 0;  

  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage2.getData(turnPage2.arrDataCacheSet, turnPage2.pageIndex, MAXSCREENLINES);
  arrGrid = arrDataSet;
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage2.pageDisplayGrid);
 divGetDrawInfo.style.display='none';
  fm.PayMode.value = "";
	fm.PayModeName.value = "";
	fm.Drawer.value = "";
	fm.DrawerIDNo.value = "";
	fm.BankCode.value = "";
	fm.BankName.value = "";
	fm.BankAccNo.value = "";
	fm.AccName.value = "";
	divGetInfo.style.display="";
	var rowNum= LJSGetGrid.mulLineCount;
	if(rowNum<1){
		alert("���޿���ȡ��Ϣ");
		fm.GrpContNoBak.value="";
		divLCGet.style.display ='none';
	}else{
		fm.GrpContNoBak.value=fm.GrpContNo.value;
		divLCGet.style.display ='';
	}
}

function GetNotice()
{
  
   
   var tResult = document.all('PrtSeq').value;
   if(tResult == null || tResult == ""){
       alert("��ѯ��ȡ�嵥��Ϣʧ�ܣ�");
       return;
   }
   //alert(document.all('PrtSeq').value);
   fm.action = "../bq/EdorNoticePrintSave.jsp";
   fm.target = "f1print";
   fm.submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content,PrtSeq )
{
  showInfo.close();
  document.all('CommitFlag').value = "1";
  if (FlagStr == "Fail")
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
	   	//initForm();

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
		
    //ִ����һ������
    if(fm.fmtransact.value=="DISKINPUT")
    {
    	fm.GrpContNo.value=fm.GrpContNoBak.value;
    	easyQueryClick();
    }
    if(fm.fmtransact.value=="GETMONEY")
    {
    	divLCGet.style.display='none';
    	divGetDrawInfo.style.display='none';
    	divGetInfo.style.display='none';
    	divGetNotice.style.display = '';
    	//alert(PrtSeq);
    	document.all('PrtSeq').value = PrtSeq;
    }
  }
}



//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true"); 
//    showDiv(inputButton,"false"); 
	  initForm();
  }
  catch(re)
  {
  	alert("��LJSGetDraw.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
}           


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
  		parent.fraMain.rows = "0,0,0,82,*";
 	}
}


//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  //showDiv(operateButton,"false"); 
  //showDiv(inputButton,"true"); 
}           

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  alert("update click");
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  var tNoticeNo = document.all('bmtz').value;
  var tPolNo = document.all('bmcert').value;
  if ((tNoticeNo==null||tNoticeNo=='')&&(tPolNo==null||tPolNo==''))
  {
  	alert("��¼�뱣���Ż�֪ͨ��Ų�ѯ!");
  	return;
  }	
	submitFlag="0";
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
 // showSubmitFrame(mDebug);
  fm.action="./LFGetPayQueryOut.jsp"
  fm.submit(); //�ύ
 }           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  alert("delete click");
}           

//��ʾdiv����һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
function showDiv(cDiv,cShow)
{
  if (cShow=="true")
  {
    cDiv.style.display="";
  }
  else
  {
    cDiv.style.display="none";  
  }
}

//����ϼƽ��
function CalSumMoney(parm1,parm2)
{
	var tSelNum = SubPayGrid.mulLineCount;
	var i;
	var aGetMoney=0;
	//alert('aaa'+tSelNum);
	for (i=0;i<=tSelNum-1;i++)
	{
		if (SubPayGrid.getChkNo(i))
		{
			var tMoney =0;
			if (SubPayGrid.getRowColData(i,7)!=null&&SubPayGrid.getRowColData(i,7)!='')
				tMoney = parseFloat(SubPayGrid.getRowColData(i,7));
			aGetMoney = aGetMoney + tMoney;
				
		}
	}
	aGetMoney=Math.round(aGetMoney*100)/100;
	document.all('SumGetMoney').value = aGetMoney;
		
}

function reportDetailClick()
{
	divGetDrawInfo.style.display='';
	var tSelNo = LJSGetGrid.getSelNo()-1;
	var tGrpContNo = LJSGetGrid.getRowColData(tSelNo,1); 
	var tContNo = LJSGetGrid.getRowColData(tSelNo,2); 
	var strSQL = "";	
  //strSQL = "select a.getnoticeno,b.contno,b.insuredname,(select dutyname from lmduty where dutycode = a.dutycode),"
  //			 + " decode(a.getdutykind,'1','һ������ȡ���Ͻ�','2','���춨�����Ͻ�','3','���춨�����Ͻ�','4','����ʮ��̶��������Ͻ�','5','����ʮ��̶��������Ͻ�','6','�����������������Ͻ�','7','�����������������Ͻ�','8','���켸�����������Ͻ�','9','���켸�����������Ͻ�','����'),"
  //			 + " a.LastGettoDate,a.CurGetToDate,a.GetMoney "
  //       + "from ljsgetdraw a ,LCCont b "
  //       + "where a.ContNo=b.ContNo and a.managecom like '"+manageCom+"%' and b.GrpContNo='"+tGrpContNo+"'"
	//			 + " and a.ContNo = '"+tContNo+"' and a.GetDate <=sysdate "
  //       + " order by a.getnoticeno";
  
  strSQL = wrapSql(tResourceName,tResourceSQL2,[fm.ManageCom.value,tGrpContNo,tContNo]); 
	
        
  turnPage1.queryModal(strSQL, SubPayGrid);
  fm.ContNo.value=tContNo;
  divGetDrawInfo.style.display='';
  
  //strSQL = "select distinct paymode,decode(paymode,'1','�ֽ�','4','����ת��','����'),drawer,drawerid,BankCode,(SELECT BankName FROM LDBank WHERE BankCode = LjsGet.BankCode),BankAccNo,AccName "
  //			 + " from ljsget where getdate <= sysdate and othernotype = '2' and otherno = '"+tContNo+"'";
  
  strSQL = wrapSql(tResourceName,tResourceSQL3,[tContNo]);   
  
  var tArr = easyExecSql(strSQL, 1, 0, 1);
	
	if (tArr != null)
	{
		 divGetInfo.style.display="";
		 try
  	 {
		 		fm.PayMode.value = tArr[0][0];
		 		fm.PayModeName.value = tArr[0][1];
		 		fm.Drawer.value = tArr[0][2];
		 		fm.DrawerIDNo.value = tArr[0][3];
		 		fm.BankCode.value = tArr[0][4];
		 		fm.BankName.value = tArr[0][5];
		 		fm.BankAccNo.value = tArr[0][6];
		 		fm.AccName.value = tArr[0][7];
		 }  catch(ex){}
  }
} 
  
function updateSubAcc()
{
		var tSelNo = LJSGetGrid.getSelNo()-1;
		//alert(tSelNo);
    if (tSelNo < 0)
    {
        alert("��ѡ����Ҫ�޸ĵĸ�����¼��");
        return;
    }
    
		fm.ContNo.value = LJSGetGrid.getRowColData(tSelNo, 2);
		//fm.InsuredNo.value = SubAccGrid.getRowColData(tSelNo, 2);
		var tPayMode = fm.PayMode.value;
		var tDrawer = fm.Drawer.value;
		var tDrawerIDNo = fm.DrawerIDNo.value;
		if(tPayMode == null || trim(tPayMode) == "" || (tPayMode != "1" && tPayMode != "4"))
		{
			alert("��ѡ���¼����ȷ����ȡ��ʽ!");
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
		fm.fmtransact.value = "UPDATEPAYMODE";
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
    fm.action = "LFGetPaySave.jsp";
    fm.submit();
}

function confirmGet()
{
	//alert(fm.GrpContNoBak.value);
	//��У���Ƿ�ȫ����ȡ��Ϣ��¼������
	
	if(trim(fm.GrpContNoBak.value)=="")
	{
		alert("��¼�����屣����");
		return;
	}
	if(trim(fm.LFAppName.value)=="")
	{
		alert("����д����������");
		return;
	}
	fm.fmtransact.value = "GETMONEY";
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
    fm.action = "LFGetPaySave.jsp";
    fm.submit();
}  
 
function diskInput()
{
	
	if ( fm.GrpContNoBak.value == "")
	{
		alert("���Ȳ�ѯ������Ϣ");
		return ;
	}

	  var tGrpContNo = fm.GrpContNoBak.value;


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
    //document.all("Transact").value = "IMPORT||EDOR"

    fm.fmtransact.value = "DISKINPUT";
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
    fm2.action = "./LFGetPayLoad.jsp?LGrpContNo="+tGrpContNo+"";
    fm2.submit();
} 
  