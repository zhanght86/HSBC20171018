var showInfo;
var turnPage = new turnPageClass(); 

function easyQuery()
{
	initCodeGrid();
	document.all('AccountingDate').value='';
	var strSQL="";
/*	var sqlPart=" ";
	if(document.all('ManageCom').value=='')
	{
		sqlPart=" and ManageCom like '"+document.all('ComCode').value+"%' ";
	}

   strSQL="select BatchNo,MatchID,AccountCode,AccountSubCode,VoucherID,RiskCode,EnteredDR,EnteredCR,ManageCom,TransDate,Polno,Bussno,AccountingDate,VoucherType from ofinterface where 1=1 and (ReversedStatus='0') and VoucherFlag<>'NA' and VoucherID<>'-1' and VoucherDate is not null"
   		+ getWherePart('VoucherType','VoucherType')
   		+ getWherePart('ManageCom','ManageCom','like')
   		+ getWherePart('TransDate','TransDate')
   		+ getWherePart('VoucherID','VoucherID')
   		+ getWherePart('PolNo','PolNo')
   		+ getWherePart('BussNo','BussNo')
   		+ sqlPart
   		+" order by BatchNo,MatchID,polno,bussno,RecordID ";*/
   var mySql1 = new SqlClass();
	mySql1.setResourceName("otof.OtoFReverseSql");
	mySql1.setSqlId("OtoFReverseSql1");
	mySql1.addSubPara(window.document.getElementsByName(trim('VoucherType'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('ManageCom'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('TransDate'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('VoucherID'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('PolNo'))[0].value);
	mySql1.addSubPara(window.document.getElementsByName(trim('BussNo'))[0].value);
	mySql1.addSubPara(document.all('ComCode').value);
	strSQL = mySql1.getString();
����turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("û����ز���ƾ֤��Ϣ��");
    return "";
  }
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = CodeGrid;    
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  return true;		
}

function OtoFReverse()
{
	if(fm.AccountingDate.value=="")
	  {
	  	    alert("����¼��������ڣ�");
		    return false;	
	  }
	  if(fm.Reason.value == "" || fm.Reason.value == "null")
	  {
		    alert("����¼��ƾ֤����ԭ��");
		    return false;
	  }
	  if (confirm("��ȷʵ������ò���ƾ֤��?"))
	  {
			  var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
			  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
			  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=250;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
			  document.all('fmAction').value = "Reverse";
			  document.getElementById("fm").submit(); 
	  }
}

function OtoFReverseAll()
{
	 if(fm.VoucherType.value==""&&fm.ManageCom.value==""&&fm.TransDate.value==""&&fm.VoucherID.value==""&&fm.PolNo.value==""&&fm.BussNo.value=="")
	  {
	  	alert("��ѡ�����������");
	  	return false;
	  }
	if(fm.AccountingDate.value=="")
	  {
	  	    alert("����¼��������ڣ�");
		    return false;	
	  }
	  if(fm.Reason.value == "" || fm.Reason.value == "null")
	  {
		    alert("����¼��ƾ֤����ԭ��");
		    return false;
	  }
	  if (confirm("��ȷʵ�밴����ѡ������������ƾ֤��?"))
	  {
			  var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
			  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
			  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
				var iWidth=550;      //�������ڵĿ��; 
				var iHeight=250;     //�������ڵĸ߶�; 
				var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
				var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
				showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

				showInfo.focus();
			  document.all('fmAction').value = "ReverseAll";
			  document.getElementById("fm").submit(); 
	  }
}

function afterSubmit( FlagStr, content )
{
	  showInfo.close();
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
