//            ���ļ��а����ͻ�����Ҫ����ĺ������¼�
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
  //var i = 0;
  //var showStr="����׼����ӡ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
  //showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  //fm.hideOperate.value=mOperate;
  //if (fm.hideOperate.value=="")
  //{
  //  alert("�����������ݶ�ʧ��");
  //}
  //showSubmitFrame(mDebug);
  //alert (verifyInput());
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

    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
    //ִ����һ������
  }
}

// ��ѯ��ť
function easyQueryClick()
{	
	if(document.all('PayDate').value == "" 
		&& document.all('PayNo').value == "" 
		&& document.all('IncomeNo').value == "" 
		&& document.all('AgentCode').value == ""
		&& document.all('GetNoticeNo').value == ""){
		alert("�������ڡ������վݺš�ʵ�ձ��롢�����˱��롢��ͬ��������������дһ���ٽ��в�ѯ");
		return;
	}
  //submitForm();
  if(!verifyInput()) 
  {
  	return false;
  }
  if(!(document.all('IncomeNo').value == "" || document.all('IncomeNo').value == null))
  {
  	if((document.all('IncomeType').value == "" || document.all('IncomeType').value == null))
  	{
  		alert("��ѡ���ͬ��������");
  		return false;
  		}
  	}
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	
	//zy 2009-08-12 10:12 �����ܽ�����ʾ��ֻ��ʾ���Ϊ�������ѵ�;���ڱ�ȫ�ŵ��������Ѷ�����ljapayperson��¼�����Զ��ڱ�ȫ�Ĳ������Ÿ�������
//	strSQL = "select GetNoticeNo,PayNo,IncomeNo,IncomeType,(case incometype when '1' then (select sum(SumActuPayMoney) from ljapaygrp where payno=a.payno and paytype='ZC') else (select sum(SumActuPayMoney) from ljapayperson where payno=a.payno and paytype='ZC' ) end) ,PayDate,ConfDate,Operator,ManageCom,AgentCode from LJAPay a where 1=1  and IncomeType in ('1','2') "			 
//				 + getWherePart( 'PayNo' )
//				 + getWherePart( 'IncomeNo' )
//				 + getWherePart( 'IncomeType' )
//				 + getWherePart( 'PayDate' )				 
//				 + getWherePart( 'AgentCode' )
//				 + getWherePart( 'GetNoticeNo' )
	var IncomeType1 ="";
	var IncomeType2 ="";
	if(document.all('IncomeType').value=='1')
//				 + " and PayNo not in (select otherno from LOPRTManager2 where code='33' and StateFlag='1')"
				 IncomeType1="1";
	else if(document.all('IncomeType').value=='2')
//				 + " and PayNo not in (select otherno from LOPRTManager2 where code='32'  and StateFlag='1')"
				 IncomeType2="1";
	var MngCom1="";
	var MngCom2="";
	if (fm.MngCom.value == null || fm.MngCom.value == "" )
	{
//		strSQL = strSQL + "and ManageCom like '" + managecomvalue + "%25'";	
		MngCom1=managecomvalue;
	}	
	else
	{
//		strSQL = strSQL + getWherePart( 'ManageCom','MngCom','like' );
		MngCom2=window.document.getElementsByName(trim("MngCom"))[0].value;
	}		
//	 strSQL = strSQL + "order by IncomeNo,PayDate,ConfDate";
	//prompt('',strSQL);
	
	  	var  PayNo0 = window.document.getElementsByName(trim("PayNo"))[0].value;
	  	var  IncomeNo0 = window.document.getElementsByName(trim("IncomeNo"))[0].value;
	  	var  IncomeType0 = window.document.getElementsByName(trim("IncomeType"))[0].value;
	  	var  PayDate0 = window.document.getElementsByName(trim("PayDate"))[0].value;
	  	var  AgentCode0 = window.document.getElementsByName(trim("AgentCode"))[0].value;
	  	var  GetNoticeNo0 = window.document.getElementsByName(trim("GetNoticeNo"))[0].value;
		var sqlid0="FeeInvoiceInputSql0";
		var mySql0=new SqlClass();
		mySql0.setResourceName("f1print.FeeInvoiceInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql0.setSqlId(sqlid0);//ָ��ʹ�õ�Sql��id
		mySql0.addSubPara(PayNo0);//ָ������Ĳ���
		mySql0.addSubPara(IncomeNo0);//ָ������Ĳ���
		mySql0.addSubPara(IncomeType0);//ָ������Ĳ���
		mySql0.addSubPara(PayDate0);//ָ������Ĳ���
		mySql0.addSubPara(AgentCode0);//ָ������Ĳ���
		mySql0.addSubPara(GetNoticeNo0);//ָ������Ĳ���
		mySql0.addSubPara(IncomeType1);//ָ������Ĳ���
		mySql0.addSubPara(IncomeType2);//ָ������Ĳ���
		mySql0.addSubPara(MngCom1);//ָ������Ĳ���
		mySql0.addSubPara(MngCom2);//ָ������Ĳ���
		strSQL=mySql0.getString();
	 
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
	var iHeight=350;     //�������ڵĸ߶�; 
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
		{
			alert( "����ѡ��һ����¼���ٵ����ӡ��ť��" );
			return;
		}
	else
	{
		arrReturn = getQueryResult();
	  var cPayNo = PolGrid.getRowColData(tSel - 1,2);		
		if (cPayNo == ""){
				alert("��Ч�Ľɷ���Ϣ");
		    return;
		  }
		}  
	
	fm.PayNo.value = cPayNo;
//	alert(cPayNo);
//	var tSql = "select 1 from dual ";
	var tSqlid = "1";
		fm.IncomeType.value = arrReturn[0][3];
	if(fm.IncomeType.value==2){
//		tSql = "select count(1) from ljapayperson where payno='" + cPayNo +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
		tSqlid = "2";
	}
	if(fm.IncomeType.value==1){
//		tSql = "select count(1) from ljapaygrp where payno='" + cPayNo +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";	
		tSqlid = "3";
	}
	
	var tSql ="";
	if(fm.IncomeType.value==2 || fm.IncomeType.value==1) {
		
		var sqlid1="FeeInvoiceInputSql"+tSqlid;
		var mySql1=new SqlClass();
		mySql1.setResourceName("f1print.FeeInvoiceInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(cPayNo);//ָ������Ĳ���
		tSql=mySql1.getString();
	} else {
		var sqlid1="FeeInvoiceInputSql"+tSqlid;
		var mySql1=new SqlClass();
		mySql1.setResourceName("f1print.FeeInvoiceInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		tSql=mySql1.getString();
	}
	
	var tResult = easyExecSql(tSql);
  if (tResult != null) 
  {
			if(tResult[0][0]>4){
				alert("����"+tResult[0][0]+"���շ���Ŀ����Ʊ��С������ֻ�ܴ�ӡ��ǰ��4��");	
			}
		
  }
  else
  {
		alert("û�нɷ���Ϣ����ȷ��!");
		return;
  }
// zy 2009-08-11 9:08 �����������0����ϵͳ������ʾ
 var yMoney = 0;
// var ySql = "select '0' from dual ";
 var ySqlid ="4";
 if(fm.IncomeType.value==2)
 {
// 		ySql ="select (case when sum(sumactupaymoney) is not null then sum(sumactupaymoney) else 0 end) from ljapayperson   where payno='" + cPayNo +"' and paytype='YET' ";
 		ySqlid ="5";
 }
 if(fm.IncomeType.value==1){
//		ySql = "select (case when sum(sumactupaymoney) is not null then sum(sumactupaymoney) else 0 end) from ljapaygrp where payno='" + cPayNo +"'  and paytype='YET' ";
		ySqlid ="6";
}
 
	var ySql ="";
	if(fm.IncomeType.value==2 || fm.IncomeType.value==1) {
		
		var sqlid5="FeeInvoiceInputSql"+ySqlid;
		var mySql5=new SqlClass();
		mySql5.setResourceName("f1print.FeeInvoiceInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara(cPayNo);//ָ������Ĳ���
		ySql=mySql5.getString();
	} else {
		var sqlid4="FeeInvoiceInputSql"+ySqlid;
		var mySql4=new SqlClass();
		mySql4.setResourceName("f1print.FeeInvoiceInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		ySql=mySql4.getString();
	}
 
   //prompt("",ySql);
  yMoney= easyExecSql(ySql);
  if(yMoney!=null){
  	if(yMoney>0)
  	{
  		alert("���ű��������罻����Ʊ�������֣��뿪�վݻ������˷Ѵ���");
  	}
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
		else
			{
				alert("�ݲ�֧�ָ����ͱ����ķ�Ʊ��ӡ�����ʵ");
				return ;
			}
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
	    var cPayNo = PolGrid.getRowColData(tSel - 1,2);				
		
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


//��ѯ�����˵ķ�ʽ
function queryAgent(comcode)
{
  if(document.all('AgentCode').value == "")	
  {  
		var newWindow = window.open("../sys/AgentCommonQueryMain.jsp?ManageCom="+comcode,"AgentCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');	  
  }
  
	if(document.all('AgentCode').value != "")	 
	{
		var cAgentCode = fm.AgentCode.value;  //��������	
//		var strSql = "select AgentCode,Name,AgentGroup from LAAgent where AgentCode='" + cAgentCode +"'";// and ManageCom = '"+document.all('ManageCom').value+"'";
    
		var sqlid7="FeeInvoiceInputSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("f1print.FeeInvoiceInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
		mySql7.addSubPara(cAgentCode);//ָ������Ĳ���
		var strSql=mySql7.getString();
		
		var arrResult = easyExecSql(strSql);
       //alert(arrResult);
    if (arrResult != null) 
    {
			fm.AgentCode.value = arrResult[0][0];
			alert("��ѯ���:  �����˱���:["+arrResult[0][0]+"] ����������Ϊ:["+arrResult[0][1]+"]");
    }
    else
    {
			fm.AgentCode.value="";
			alert("����Ϊ:["+document.all('AgentCode').value+"]�Ĵ����˲����ڣ���ȷ��!");
    }
	}	
}

//��ѯ����ʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
function afterQuery2(arrResult)
{  
  if(arrResult!=null)
  {
  	fm.AgentCode.value = arrResult[0][0];
//  	fm.QAgentGroup.value = arrResult[0][1];
  }
}