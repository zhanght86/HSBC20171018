//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//window.onfocus=myonfocus;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var myCheckInsuAccNo = "";
var myCheckDate = "";
var sqlresourcename = "acc.PriceInfoInputInputSql";

//�ύ�����水ť��Ӧ����
function submitFrom()
{
	QueryCompanyUnitCount();
  if(beforeSubmit())
  {
	var mySql=new SqlClass();
        mySql.setResourceName(sqlresourcename);
        mySql.setSqlId("PriceInfoInputInputSql0");
        mySql.addSubPara(document.all('InsuAccNo').value);
        mySql.addSubPara(document.all('StartDate').value);
	var  oldarrResult =new Array;
	oldInsuAccNo= document.all('InsuAccNo').value;
	oldStartDate= document.all('StartDate').value;

	oldarrResult=easyExecSql(mySql.getString());
            
	  if (oldarrResult>0)
	  {
	     alert ("�������Ѵ��ڣ���ѡ������������");	
	     return false;
	  } 
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
   document.all('Transact').value ="INSERT";
  document.getElementById("fm").submit(); //�ύ 
  }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit1( FlagStr, content )
{
//  alert(FlagStr);
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
  }
  resetForm();
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
  	alert("toulian.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
//  window.location="../common/html/Blank.html";
}

var oldInsuAccNo;
var oldStartDate;
 function updateClick()
{
  //����������Ӧ�Ĵ���
  //���е������ֶ�"�����"��ΪUPDATE 
  if(beforeSubmit())
  { 	
  	if(document.all('State').value!='1')
	{
	  	alert("�ü�¼������¼��״̬�������޸ģ�");
	  	return false;
	 }
    if(parseFloat(fm.CompanyUnitCount.value)+parseFloat(fm.ComChgUnitCount.value)<0)
    {
  		alert("�䶯��˾Ͷ�ʵ�λ������Ϊ����");
  		return false;
    }
    document.all('Transact').value ="UPDATE";
    var i = 0;
    var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit(); //�ύ 
  }
}       
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���
  //������Ϣ���в���Ϊ�յ��ֶμ���,����2����
  //ҳ����ʾ�ؼ���Ҫ������ֶ�(CustomerNo,Name,Sex,Birthday,Operator)	
  //���ص��ֶ�(MakeDate,MakeTime,ModifyDate,ModifyTime),��ClientConjoinQueryUI������
    if(document.all('InsuAccNo').value == '')
    {
    	alert("�����ʻ����벻��Ϊ��!");
    	return false;
    	}
    if(document.all('StartDate').value == '')
    {
    	alert("�Ƽ����ڲ���Ϊ��!");
    	return false;
    	}
   if(!isDate(document.all('StartDate').value))
    {
    	alert("�Ƽ�������������!");
    	return false;
    	}  
    	
    if(document.all('SKFlag').value == '')
    {
    	alert("�������ű�־����Ϊ��!");
    	return false;
    	}

//    if(!isDate(document.all('SRateDate').value))
//    {
//    	alert("�۸�Ӧ����������������!");
//    	return false;
//    	}  
//    if(!isDate(document.all('ARateDate').value))
//    {
//    	alert("�۸�ʵ�ʹ���������������!");
//    	return false;
//    	}  
    	
    	
    if(document.all('InsuTotalMoney').value == '')
    {
    	alert("��δ��д�˻����ʲ�!");
    	return false;
    }else{
    	if(!isNumeric(document.all('InsuTotalMoney').value))
    	{
    		alert("�Ʋ����ʲ��������ʽ����!");
    		return false;
    	}
    }
    if(document.all('Liabilities').value == '')
    {
    	alert("��δ��д��ծ!");
    	return false;
    }else{
    	if(!isNumeric(document.all('Liabilities').value))
    	{
    		alert("��ծ�������ʽ����!");
    		return false;
    	}
    }	
    if(document.all('CompanyUnitCount').value == '')
    {
    	alert("��˾Ͷ�ʵ�λ������Ϊ��!");
    	return false;
    }	
    if(document.all('CustomersUnitCount').value =='')
    {
    	alert("�ͻ�Ͷ�ʵ�λ������Ϊ��!");
    	return false;
    }	
    if(document.all('ComChgUnitCount').value == '')
    {
    	alert("��δ��д���α䶯��λ��!");
    	return false;
    }else{
    	if(!isNumeric(document.all('ComChgUnitCount').value))
    	{
    		alert("���α䶯��λ���������ʽ����!");
    		return false;
    	}
    }	
    	
   	if(document.all( 'StateName' ).value!='1-¼��')
   	{
   		alert("��¼��״̬�����������á�����ˢ��ҳ��!");
    	return false;
   		}
    	if(document.all('State').value == '')
    {
    	alert("��¼״̬����Ϊ��!");
    	return false;
    	}	
    return true;
}           

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1") {
	parent.fraMain.rows = "0,0,50,82,*";
  } else {
  	parent.fraMain.rows = "0,0,0,82,*";
  }
}

function queryValidCertify()
{
  alert("queryValidCertify");
  // submitForm();  
}    

function queryInvalidCertify( )
{
  alert("queryInvalidCertify");
}       

function queryClientClick()
{
  //����������Ӧ�Ĵ���
//  window.showModalDialog("./ClientConjoinQueryQuery.html",window,"status:0;help:0;edge:sunken;dialogHide:0");
  //��ѯ���������һ��ģ̬�Ի��򣬲��ύ�������������ǲ�ͬ��
  //��ˣ����еĻ����Ҳ���Բ��ø�ֵ��
  alert("./PriceInfoQuery.html");
  window.open("./PriceInfoQuery.html");
  
}           
function queryClick()
{
	var loadFlag = "0";

	try
	{
		if( top.opener.mShowCustomerDetail == "GROUPPOL" ) loadFlag = "1";
	}
	catch(ex){}
	
	if( loadFlag == "1" )
		parent.fraInterface.window.location = "./PriceInfoQuery.jsp";
	else
    	window.open("./PriceInfoQuery.html");
  
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
function deleteClick()
{
  //����������Ӧ�Ĵ���
//  alert("delete");
  //��δ����ȫ��Ϊ�յ����
 if(document.all('InsuAccNo').value == '')
  {
   alert("�����ʻ����벻��Ϊ��!");
   return false;
  }else if(document.all('StartDate').value == '')
  {
   alert("�Ƽ����ڲ���Ϊ��!");
   return false;
  } else
  {
   if(document.all('State').value!='1')
  {
  	alert("�ü�¼������¼��״̬������ɾ����");
  	return false;
  }
    document.all('Transact').value ="DELETE";
    var i = 0;
    var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.getElementById("fm").submit(); //�ύ 
    //initForm();
  }
  
}          
function afterQuery(arrQueryResult)
{
 
	var arrResult = new Array();
	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all( 'InsuAccNo' ).value = arrResult[0][0];
		oldInsuAccNo= arrResult[0][0];
		document.all( 'StartDate' ).value = arrResult[0][1];
		oldStartDate = arrResult[0][1];
		document.all( 'EndDate' ).value = arrResult[0][2];
		document.all( 'InvestFlag' ).value = arrResult[0][3];
		document.all( 'SRateDate' ).value = arrResult[0][4];
		document.all( 'ARateDate' ).value = arrResult[0][5];
		document.all( 'InsuTotalMoney' ).value = arrResult[0][6];
		document.all( 'Liabilities' ).value = arrResult[0][7];
		document.all( 'RedeemRate' ).value = arrResult[0][8];
		document.all( 'State' ).value = arrResult[0][9];
			alert("hehe--State="+document.all( 'State' ).value);
		if(document.all( 'State' ).value=='0')
		{
				alert("hehe");
				document.all( 'StateName' ).value='0-��Ч'
		}
		if(document.all( 'State' ).value=='1')
		{
			document.all( 'StateName' ).value='1-¼��'
		}
		if(document.all( 'State' ).value=='2')
		{
			document.all( 'StateName' ).value='2-������ȷ'
		}
		if(document.all( 'State' ).value=='3')
		{
			document.all( 'StateName' ).value='3-���˴���'
		}
		if(document.all( 'State' ).value=='4')
		{
			document.all( 'StateName' ).value='4-ȷ����ȷ'
		}
		if(document.all( 'State' ).value=='5')
		{
			document.all( 'StateName' ).value='5-ȷ�ϴ���'
		}
		document.all( 'RedeemMoney' ).value = arrResult[0][10];
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
  }

  	fm.QueryState.value = "1";
  	easyQueryClick();
	document.all( 'InsuAccNo' ).value = '';
	document.all('InsuAccNoName').value = '';
	document.all( 'StartDate' ).value = '';
	document.all( 'InsuTotalMoney' ).value = '';
	document.all( 'Liabilities' ).value = '';
	document.all( 'State' ).value = '1';
	document.all( 'StateName' ).value='1-¼��';
	document.all('CompanyUnitCount').value = '';
	document.all('ComChgUnitCount').value = '0';
	document.all('CustomersUnitCount').value = '';
	document.all('SKFlag').value = '';
   	document.all('SKFlagName').value = '';
	myCheckDate = '';
	myCheckInsuAccNo = '';
    document.all('Transact').value = '';
}

function easyQueryClick()
{
	// ��ʼ�����
	initCollectivityGrid();

	// ��дSQL���
	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("PriceInfoInputInputSql1");
	    mySql.addSubPara(fm.QureyInsuAccNo.value);
	    mySql.addSubPara(fm.QueryStartDate.value);
	    mySql.addSubPara(fm.QueryState.value);

	turnPage.queryModal(mySql.getString(), CollectivityGrid);
	if(CollectivityGrid.mulLineCount <= 0){
		alert("û�з������������ݣ�");
		return false;
	}
}

 function ShowPlan()
{
 	var arrResult = new Array();
	var tSel = CollectivityGrid.getSelNo();	
	if( tSel == 0 || tSel == null )
	{
		alert( "����ѡ��һ����¼!" );
		return;
	}			
	else
	{
	//������Ҫ���ص�����
		var strSQL = "";
		var qqq1 = CollectivityGrid.getRowColData(tSel-1,1);
		var qqq2 = CollectivityGrid.getRowColData(tSel-1,2);
		var mySql=new SqlClass();
	       mySql.setResourceName(sqlresourcename);
		   mySql.setSqlId("PriceInfoInputInputSql4");
		   mySql.addSubPara(qqq1);
		   mySql.addSubPara(qqq2);
		strSQL = mySql.getString();
		turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
		if (!turnPage.strQueryResult) {    //�ж��Ƿ��ѯ�ɹ�
		    alert("��ѯʧ�ܣ�");
		    return false;
	    }
    
	//��ѯ�ɹ������ַ��������ض�ά����
     arrResult = decodeEasyQueryResult(turnPage.strQueryResult); 
		document.all( 'InsuAccNo' ).value = arrResult[0][0];
		oldInsuAccNo= arrResult[0][0];
		InputInsuAccNoName();
		document.all( 'StartDate' ).value = arrResult[0][1];
		oldStartDate = arrResult[0][1];
		//document.all( 'EndDate' ).value = arrResult[0][2];
		//document.all( 'InvestFlag' ).value = arrResult[0][3];
		//if(document.all( 'InvestFlag' ).value=='1')
		//{
		//	document.all( 'InvestFlagName' ).value='�������';
		//}
		//if(document.all( 'InvestFlag' ).value=='2')
		//{
		//	document.all( 'InvestFlagName' ).value='�ݶ�����';
		//}
		//document.all( 'SRateDate' ).value = arrResult[0][4];
		//document.all( 'ARateDate' ).value = arrResult[0][5];
		document.all( 'InsuTotalMoney' ).value = arrResult[0][6];
		document.all( 'Liabilities' ).value = arrResult[0][7];
		//document.all( 'RedeemRate' ).value = arrResult[0][8];
		//if(document.all( 'RedeemRate' ).value=='null')
		//{
		//	document.all( 'RedeemRate' ).value='';
		//}
		//alert("Flag");
		document.all( 'State' ).value = arrResult[0][9];
			//alert("hehe--State="+document.all( 'State' ).value);
		if(document.all( 'State' ).value=='0')
		{
			//	alert("hehe");
			document.all( 'StateName' ).value='0-��Ч'
		}
		if(document.all( 'State' ).value=='1')
		{
			document.all( 'StateName' ).value='1-¼��'
		}
		if(document.all( 'State' ).value=='2')
		{
			document.all( 'StateName' ).value='2-������ȷ'
		}
		if(document.all( 'State' ).value=='3')
		{
			document.all( 'StateName' ).value='3-���˴���'
		}
		if(document.all( 'State' ).value=='4')
		{
			document.all( 'StateName' ).value='4-ȷ����ȷ'
		}
		if(document.all( 'State' ).value=='5')
		{
			document.all( 'StateName' ).value='5-ȷ�ϴ���'
		}
		//document.all( 'RedeemMoney' ).value = arrResult[0][10];
		//if(document.all( 'RedeemMoney' ).value =='null')
		//{
		//	document.all( 'RedeemMoney' ).value ='';
		//}  
		document.all( 'CompanyUnitCount' ).value = arrResult[0][11];
		document.all( 'ComChgUnitCount' ).value = arrResult[0][12];
		document.all('CustomersUnitCount').value = arrResult[0][13];
		document.all('SKFlag').value = arrResult[0][14];
		if(document.all('SKFlag').value=='0')
		{
			document.all('SKFlagName').value='������';
		}
		else
		{
			document.all('SKFlagName').value='������';
		}
	}
	 
}

 function InputInsuAccNoName()
 {
      var mySql=new SqlClass();
	       mySql.setResourceName(sqlresourcename);
		   mySql.setSqlId("PriceInfoInputInputSql8");
		   mySql.addSubPara(document.all('InsuAccNo').value);
 	   var tDateResult = easyExecSql(mySql.getString());

       if(tDateResult!=null)
       { 
       	document.all( 'InsuAccNoName' ).value=tDateResult[0][1];
       }
 }

function QueryCompanyUnitCount()
{
	var insuaccno = document.all('InsuAccNo').value;
	var mydate = document.all('StartDate').value;
	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("PriceInfoInputInputSql5");
	    mySql.addSubPara(insuaccno);
	    mySql.addSubPara(mydate);
	    mySql.addSubPara(insuaccno);
    var strSQL = mySql.getString();
    var result = easyExecSql(strSQL); 
    if(result!=null)
    {
    	document.all('CompanyUnitCount').value = result[0][0];
    }
  
	var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
	    mySql2.setSqlId("PriceInfoInputInputSql6");
	    mySql2.addSubPara(insuaccno);
    var strSQL2 = mySql2.getString();
    var result2 = easyExecSql(strSQL2); 
    if(result2[0][0]=='null')
    {
    	document.all('CustomersUnitCount').value = '';
	}
    else
    {
    	document.all('CustomersUnitCount').value = result2[0][0];
    }
}

function CheckPriceDate()
{
	if(document.all('InsuAccNo').value=="")
	{
		alert("��������Ͷ���ʻ����룡");
		document.all('StartDate').value = "";
		myCheckDate = "";
		document.all('CompanyUnitCount').value = "";
		document.all('CustomersUnitCount').value = "";
		return false;
	}
	else
	{
		var maxPriceDate = document.all('StartDate').value;
		var mySql=new SqlClass();
			mySql.setResourceName(sqlresourcename);
		    mySql.setSqlId("PriceInfoInputInputSql7");
		    mySql.addSubPara(document.all('InsuAccNo').value);
		var strSQL = mySql.getString();
		var result = easyExecSql(strSQL); 
		if(result[0][0]>=maxPriceDate)
		{
			//alert(maxPriceDate);
			alert(result[0][0]+"�Ѿ���"+document.all('InsuAccNo').value+"("+document.all('InsuAccNoName').value+")���Ѿ���Ч�ĵ�λ�۸��¼�����������������֮�������������ʲ��������ڣ���������"+result[0][0]+"��");
			document.all('StartDate').value = "";
			myCheckDate = "";
			document.all('CompanyUnitCount').value = "";
			document.all('CustomersUnitCount').value = "";
			return false;
		}
   }
  return true;
}

function myCheckFiled()
{
//	if(CollectivityGrid.getSelNo()==0)
//	{
	if(document.all('StateName').value == '1-¼��')
	{
		if(myCheckInsuAccNo!=document.all('InsuAccNo').value)
		{
			if(document.all('StartDate').value!="")
			{
			QueryCompanyUnitCount();
			myCheckInsuAccNo=document.all('InsuAccNo').value;
		  }
		}
		if(myCheckDate!=document.all('StartDate').value)
		{
			if(CheckPriceDate())
			{
			QueryCompanyUnitCount();
			myCheckDate = document.all('StartDate').value;
		  }
		}
	}
//}
}