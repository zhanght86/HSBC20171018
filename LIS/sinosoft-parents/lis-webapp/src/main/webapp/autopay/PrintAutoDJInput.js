//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
var arrDataSet;
var mDebug="0";
var mOperate="";
var showInfo;
var turnPage = new turnPageClass(); 
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

//�ύ�����水ť��Ӧ����
function submitForm()
{
  if(verifyInput()) 
  {
  	document.getElementById("fm").submit(); //�ύ
  }
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
  }
}

// ��ѯ��ť
function easyQueryClick()
{	
  //submitForm();
  if(!verifyInput())  
  {
  	return false;
  }
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
//	
//	
//	strSQL = "select PayNo,IncomeNo,IncomeType,SumActuPayMoney,PayDate,ConfDate,Operator,ManageCom,AgentCode from LJAPay where 1=1 "			 
//				 + getWherePart( 'PayNo' )
//				 + getWherePart( 'IncomeNo' )
//				 + getWherePart( 'IncomeType' )
//				 + getWherePart( 'PayDate' )				 
//				 + getWherePart( 'AgentCode' )
				 
		  
				 
	if(document.all('IncomeType').value=='1')
				 + " and PayNo not in (select otherno from LOPRTManager2 where code='33' and StateFlag='1')"
	else if(document.all('IncomeType').value=='2')
				 + " and PayNo not in (select otherno from LOPRTManager2 where code='32'  and StateFlag='1')"
	
	if (fm.MngCom.value == null || fm.MngCom.value == "" )
	{
//		strSQL = strSQL + "and ManageCom like '" + managecomvalue + "%25'";	
		 
		 var  PayNo = window.document.getElementsByName(trim("PayNo"))[0].value;	
	     var  IncomeNo = window.document.getElementsByName(trim("IncomeNo"))[0].value;
	     var  IncomeType = window.document.getElementsByName(trim("IncomeType"))[0].value;
	     var  PayDate = window.document.getElementsByName(trim("PayDate"))[0].value;
	     var  AgentCode = window.document.getElementsByName(trim("AgentCode"))[0].value;
	     var sqlid1="PrintAutoDJInputSql1";
		  var mySql1=new SqlClass();
		  mySql1.setResourceName("autopay.PrintAutoDJInputSql");
		  mySql1.setSqlId(sqlid1);//ָ��ʹ��SQL��id
		  mySql1.addSubPara(PayNo);//ָ���������
		  mySql1.addSubPara(IncomeNo);//ָ���������
		  mySql1.addSubPara(IncomeType);//ָ���������
		  mySql1.addSubPara(PayDate);//ָ���������
		  mySql1.addSubPara(AgentCode);//ָ���������
		  mySql1.addSubPara(managecomvalue);
		  strSQL = mySql1.getString();
		
		
	}	
	else
	{
//		strSQL = strSQL + getWherePart( 'ManageCom','MngCom','like' );
		
		 var  PayNo = window.document.getElementsByName(trim("PayNo"))[0].value;	
	     var  IncomeNo = window.document.getElementsByName(trim("IncomeNo"))[0].value;
	     var  IncomeType = window.document.getElementsByName(trim("IncomeType"))[0].value;
	     var  PayDate = window.document.getElementsByName(trim("PayDate"))[0].value;
	     var  AgentCode = window.document.getElementsByName(trim("AgentCode"))[0].value;
	     var sqlid3="PrintAutoDJInputSql3";
		  var mySql3=new SqlClass();
		  mySql3.setResourceName("autopay.PrintAutoDJInputSql");
		  mySql3.setSqlId(sqlid3);//ָ��ʹ��SQL��id
		  mySql3.addSubPara(PayNo);//ָ���������
		  mySql3.addSubPara(IncomeNo);//ָ���������
		  mySql3.addSubPara(IncomeType);//ָ���������
		  mySql3.addSubPara(PayDate);//ָ���������
		  mySql3.addSubPara(AgentCode);//ָ���������
		  mySql3.addSubPara(window.document.getElementsByName(trim("MngCom"))[0].value);
		  strSQL = mySql3.getString();
		
		
	}		
//	 strSQL = strSQL + "order by IncomeNo,PayDate,ConfDate";
	 
	 
	 
	 
	 
	 
//	alert(strSQL);
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL); 
    
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
    //alert("��ѯʧ�ܣ�");
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=û�в�ѯ����������������" ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0; 
  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //����MULTILINE������ʾ��ѯ���
  
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);   
 
}

//������Ʊ��ӡ
function PPrint()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ��������ϸ��ť��" );
	else
	{
		arrReturn = getQueryResult();
	  var cPayNo = PolGrid.getRowColData(tSel - 1,1);		
		if (cPayNo == "")
		    return;
		    
		}  
	fm.PayNo.value = cPayNo;
	
	fm.IncomeType.value = arrReturn[0][2];
	if(fm.IncomeType.value==2)
	{
	  fm.action="PFeeInvoiceF1PSave.jsp";
	  fm.target="f1print";
	  fm.fmtransact.value="CONFIRM";
	  submitForm();
	}
    else if (fm.IncomeType.value==1)
   {
	 fm.action="GFeeInvoiceF1PSave.jsp";
     fm.target="f1print";
     fm.fmtransact.value="CONFIRM";
     submitForm();
	}
}

//���巢Ʊ��ӡ
function GPrint()
{
	var arrReturn = new Array();
	var tSel = PolGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ��������ϸ��ť��" );
	else
	{
	    var cPayNo = PolGrid.getRowColData(tSel - 1,1);				
		
		if (cPayNo == "")
		    return;
		    
		}  
	
	fm.PayNo.value = cPayNo;
  fm.action="GFeeInvoiceF1PSave.jsp";
  fm.target="f1print";
  fm.fmtransact.value="PRINT";
  submitForm();
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


function QueryAutoDJ()
{
	
  if(!verifyInput()) 
  {
  	return false;
  }
  initPolGrid();
//  var strSQL = "";
//  strSQL = " select LOLoan.PolNo,(select t.tempfeeno from ljtempfeeclass t where t.chequeno=LOLoan.Actugetno ),LOLoan.LoanDate,LOLoan.LeaveMoney "
//  			 + " from LOLoan,LOPRTManager where LOLoan.contno = LOPRTManager.OtherNo "
//  			 + " and LOPRTManager.remark = LOLoan.EdorNo"
//  			 + " and LOPRTManager.ManageCom like '"+fm.Station.value+"%%'"
//  			 + " and LOPRTManager.code = '41' and LOPRTManager.StateFlag = '0'"
//  			 + " and LOLoan.LoanType = '1' "
//  			 + getWherePart( 'LOLoan.PayOffFlag','TRFlag','=' )
//  			 + getWherePart( 'LOPRTManager.MakeDate','StartDate','>=' )
//  			 + getWherePart( 'LOPRTManager.MakeDate','EndDate','<=');
  
  var  TRFlag = window.document.getElementsByName(trim("TRFlag"))[0].value;
  var  StartDate = window.document.getElementsByName(trim("StartDate"))[0].value;
  var  EndDate = window.document.getElementsByName(trim("EndDate"))[0].value;
  
  
  
  var sqlid2="PrintAutoDJInputSql2";
  var mySql2=new SqlClass();
  mySql2.setResourceName("autopay.PrintAutoDJInputSql");
  mySql2.setSqlId(sqlid2);//ָ��ʹ��SQL��id
  mySql2.addSubPara(fm.Station.value);//ָ���������
  mySql2.addSubPara(TRFlag);//ָ���������
  mySql2.addSubPara(StartDate);//ָ���������
  mySql2.addSubPara(EndDate);//ָ���������
  
  var strSQL = mySql2.getString();

  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
 		if (!turnPage.strQueryResult) 
 		{
    	alert("�ڸù��������û�������������Զ��潻��Ϣ��¼");
    	return "";
  	}
  
  	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  	turnPage.pageDisplayGrid = PolGrid;    
  	turnPage.strQuerySql     = strSQL; 
  	turnPage.pageIndex       = 0;  
  	var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  	return true;

 }
 
function PrintAutoDJ()
{
	tRow=PolGrid.getSelNo();
  if (tRow==0)
  {
  	alert("�����Ƚ���ѡ��");
    return;
  }
  
  tEdorNo = PolGrid.getRowColData(tRow-1,2);
  fm.Date.value= PolGrid.getRowColData(tRow-1,3);
//  alert("���������"+fm.Date.value);
   
  fm.action="./AutoDJPrint.jsp?EdorNo="+tEdorNo;
  fm.target="f1print";
//  showInfo.close();
  document.getElementById("fm").submit();
}
//��ӡ�����嵥�ĺ���
function PrintQD()
{
	fm.action="./AutoDJAllPrint.jsp";
  fm.target="f1print";
  document.getElementById("fm").submit();
}	