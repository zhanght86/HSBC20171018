//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var PolNo;
var manageCom = '';
var AppntName ='';
var InsuredName='';
var sqlresourcename = "app.ReProposalPrtSql";
//�ύ�����水ť��Ӧ����,�ύ����
function ApplyRePrint()
{
	var i = 0;
	var flag = 0;
	flag = 0;
	
	for( i = 0; i < PolGrid.mulLineCount; i++ )
	{
		if( PolGrid.getChkNo(i) == true )
		{
			flag = 1;
			break;
		}
	}
	
	if( flag == 0 )
	{
		alert("����ѡ��һ����¼���ٽ�������");
		return false;
	}
	if(document.all('NeedPay').value == null||document.all('NeedPay').value =="")
	{
		alert("��¼��Ʒ���Ϣ!");
		return false;
	}
	if(document.all('Reason').value == null||document.all('Reason').value =="")
	{
		alert("��¼���ش�ԭ����Ϣ!");
		return false;
	}
	if(document.all('BatchNo').value == null||document.all('BatchNo').value =="")
	{
		alert("��¼�����������Ϣ!");
		return false;
	}
	var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	//showSubmitFrame(mDebug);
	if(document.all('NeedPay').value=='0') //����Ҫ���н�������Ҫ����������������
	{
	fmSave.fmtransact.value="CONFIRM";	
	}
	if(document.all('NeedPay').value=='1')
	{
	fmSave.fmtransact.value="PRINT";	
	}
	
	document.getElementById("fmSave").submit();

}

function getQueryResult()
{
	var arrSelected = null;
	tRow = PolGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	return arrSelected;

	arrSelected = new Array();
	//������Ҫ���ص�����
	arrSelected[0] = arrDataSet[tRow-1];

	return arrSelected;
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	showInfo.close();
	if (FlagStr == "Fail" )
	{
		//����ύʧ�ܣ���ʾ������Ϣ
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
		//����ύ�ɹ�������������������²�ѯ��Ҫ���������
		// ˢ��һ�������
		initForm();
		//easyQueryClick();
	}
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
  }
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
  		parent.fraMain.rows = "0,0,0,0,*";
 	}
}

function returnParent()
{
    tPolNo = "00000120020110000050";
    top.location.href="./ProposalQueryDetail.jsp?PolNo="+tPolNo;
}


// ��ѯ��ť
function easyQueryClick()
{
	if(comcode!='86')
	{
		alert("ֻ���ܹ�˾�����ش�Ȩ�����뷵��");
		return false;
		}
	if((document.all('ContNo').value == null || document.all('ContNo').value == "") 
    && (document.all('PrtNo').value == null || document.all('PrtNo').value == ""))
   {
   	
		alert("�����뱣���Ż���ӡˢ��");
		return false;
   }
	//��ʼ�����
	initPolGrid();
	//alert(1);
	var strManageComWhere = " ";
	var strContNoSQL="";
  var strRiskCodeWhere = " and EXISTS (select 1 from lcpol where contno = A.contno and polno =mainpolno  and riskcode in (SELECT riskcode FROM LMRiskApp where NotPrintPol in ('0','1')) )"; //֧�ֶԿ����Ĵ�ӡ
	if((document.all('ContNo').value == null || document.all('ContNo').value == "") 
    && (document.all('PrtNo').value == null || document.all('PrtNo').value == ""))
   {
   	
   	   strContNoSQL=" and a.signdate<=now() and a.signdate>=(subdate(now(),10))";
   	}
//��һ��SQL����ʱ�������в�ѯ
	var strSQL="";
	var tTempSQL="";
	if(_DBT==_DBO){
		  tTempSQL=" with familycont as ( select prtno,(select contno from lccont "
            +" where prtno = familyprt.prtno and rownum <= 1) contno from "   
+  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1'   "
		+ " AND ( PrintCount in ('1') )"
		+ getWherePart('a.contno', 'ContNo' )
	  + getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
		+ strContNoSQL
		+ getWherePart('ManageCom', 'ManageCom', 'like')
		+ " and ManageCom like '" + comcode + "%%' "
		+" union  "
+  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1' and FamilyType in ('1','2')   "
		+ " AND ( PrintCount in ('1'))"
		+ getWherePart('a.familycontno', 'ContNo' )
		+ getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
		+ strContNoSQL
		+ getWherePart('ManageCom', 'ManageCom', 'like')
		+ " and ManageCom like '" + comcode + "%%')) familyprt )"	;	
		  
		  var sqlid6="ReProposalPrtSql6";
		  var mySql6=new SqlClass();
		  mySql6.setResourceName(sqlresourcename);
		  mySql6.setSqlId(sqlid6);
		  mySql6.addSubPara(tTempSQL);
		  mySql6.addSubPara(strRiskCodeWhere);
		  strSQL = mySql6.getString() ;
		   
	}else if(_DBT==_DBM){
		  tTempSQL=" ( select prtno,(select contno from lccont "
            +" where prtno = familyprt.prtno limit 1) contno from "   
+  " (SELECT PrtNo FROM LCCont a where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1'   "
		+ " AND ( PrintCount in ('1') )"
		+ getWherePart('a.contno', 'ContNo' )
	  + getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
		+ strContNoSQL
		+ getWherePart('ManageCom', 'ManageCom', 'like')
		+ " and ManageCom like '" + comcode + "%%' "
		+" union  "
+  " (SELECT PrtNo FROM LCCont A where grpcontno = '00000000000000000000'"
		+ " AND AppFlag = '1' and FamilyType in ('1','2')   "
		+ " AND ( PrintCount in ('1'))"
		+ getWherePart('a.familycontno', 'ContNo' )
		+ getWherePart( 'PrtNo' )
		+ getWherePart( 'SaleChnl' )
		+ strContNoSQL
		+ getWherePart('ManageCom', 'ManageCom', 'like')
		+ " and ManageCom like '" + comcode + "%%')) familyprt ) b"	;	
		 
		  var sqlid7="ReProposalPrtSql7";
		  var mySql7=new SqlClass();
		  mySql7.setResourceName(sqlresourcename);
		  mySql7.setSqlId(sqlid7);
		  mySql7.addSubPara(tTempSQL);
		  mySql7.addSubPara(strRiskCodeWhere);
		  strSQL = mySql7.getString() ;
	}
  		

	// ��дSQL���
/*	var strSQL = "";
	strSQL =tTempSQL+ "select a.ContNo, a.Proposalcontno, a.prtno, a.AppntName, a.signdate,a.ManageCom,a.PrintCount,a.familytype,a.familycontno "
	       +"  from LCCont a , familycont b where a.contno=b.contno and a.PrtNo=b.PrtNo and  a.AppFlag = '1' AND a.PrintCount in ('1' ,'10')"
	       +strRiskCodeWhere
	       + " order by a.ContNo asc,a.signdate asc "; 
*/
//var sqlid6="ReProposalPrtSql6";
//var mySql6=new SqlClass();
//mySql6.setResourceName(sqlresourcename);
//mySql6.setSqlId(sqlid6);
//mySql6.addSubPara(tTempSQL);
//mySql6.addSubPara(strRiskCodeWhere);
//var strSQL = mySql6.getString() ; 

	turnPage.strQueryResult  = easyQueryVer3(strSQL);

	//�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		alert("û�в�ѯ��Ҫ�����ҵ�����ݣ�");
		return false;
	}
	
	//�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage.pageDisplayGrid = PolGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL;
	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}

//�ж��Ƿ�����������
function dataConfirm(rr)
{
	if(rr.checked == true)
	{
		if( confirm("�Ƿ����������ݣ����������ݵ����ȷ����������㡰ȡ������"))
		{
			rr.checked = true;
			fmSave.fmtransact.value = "CONFIRM";
		}
		else
		{
			rr.checked = false;
			fmSave.fmtransact.value = "PRINT";
		}
	}
	else
	{
		rr.checked = false;
		fmSave.fmtransact.value = "PRINT";
	}
}