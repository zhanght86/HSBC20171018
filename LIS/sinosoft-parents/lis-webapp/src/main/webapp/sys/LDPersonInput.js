//�������ƣ�LDPersonInput.js
//�����ܣ��ͻ����������ͻ���
//�������ڣ�2005-06-20
//������  ��wangyan
//���¼�¼��������    ��������     ����ԭ��/����

//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var mAction = "";
var turnPage = new turnPageClass(); 
var theFirstValue="";
var theSecondValue="";
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "sys.LDPersonInputSql";

//�����ӡ���ť��Ӧ����
function submitForm()
{
  //�ύǰ�ļ���
  if(beforeSubmit())
  {
	  fm.all('Transact').value ="INSERT";	
	  var i = 0;
	  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//	  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	  var iWidth=550;      //�������ڵĿ��; 
	  var iHeight=250;     //�������ڵĸ߶�; 
	  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	  showInfo.focus();

	  //showSubmitFrame(mDebug);
	  mAction = "INSERT";
	  fm.submit(); //�ύ 
  }
}


//���޸ġ���ť��Ӧ����
function updateClick()
{
  //����������Ӧ�Ĵ���
  //�뿼���޸Ŀͻ���������
	// alert("update");
  //���е������ֶ�"�����"��Ϊupdate
  if(fm.all('CustomerNo').value=='')
  {
  	alert("���Ȳ�ѯ�������޸Ĳ�����");
  	return false;
  }
  fm.all('Transact').value ="UPDATE";
  var i = 0;
  var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.submit(); //�ύ 
}


//����ѯ����ť��Ӧ����
function queryClick()
{
	var loadFlag = "0";

	try
	{
		if( top.opener.mShowCustomerDetail == "PROPOSAL" ) loadFlag = "1";
	}
	catch(ex){}
	
	if( loadFlag == "1" )
		parent.fraInterface.window.location = "./LDPersonQuery.jsp";
	else
	    window.open("./LDPersonQuery.html","",sFeatures);
}           


//��ɾ�����޸İ�ť��Ӧ����
function deleteClick()
{
  if(fm.all('CustomerNo').value == '')
  {
		alert("�ͻ����벻��Ϊ��!");
		return false;
  }
  else
  {
  	alert("ȷ��ɾ���˿ͻ�������Ϣ��");
   // var tSql="select count(contno) from lcinsured where insuredno='"+fm.all('CustomerNo').value+"'";
    
    var sqlid1="LDPersonInputSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(fm.all('CustomerNo').value);
    
    var arr=easyExecSql(mySql1.getString());
    if(arr[0]>0)
    {
      alert("�ÿͻ����к�ͬ����,����ɾ��!");
      return false;	
    }
  var i = 0;
  var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  fm.all('Transact').value ="DELETE";
  fm.submit();
  parent.fraInterface.initForm();
  }
}           


//�ύǰ��У�顢����  
function beforeSubmit()
{
  if(!verifyInput2()) 
    return false;
  if(fm.all('GetBankAccNo').value!='')
    {
      alert("��ѯ�󣬲��ܱ���");
      return false;
    }
 
  return true;
} 


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
//  alert(FlagStr);
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=350;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    //��ʼ��
    //initForm();
  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //parent.fraInterface.initForm();
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=350;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //ִ����һ������
   
    
    
    if( mAction == "INSERT" ) mAction = "INSERT||OK";
  }
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


/*********************************************************************
 *  ��ѯ������ϸ��Ϣʱִ�еĺ���,��ѯ����һ��2ά���飬�����±��[0][0]��ʼ
 *  ����  ��  ��ѯ���صĶ�ά����
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterQuery( arrQueryResult )
{
	if( arrQueryResult != null )
	{
		initInpBox();
		fm.all( 'CustomerNo' ).value = arrQueryResult[0][0];

    displayperson( arrQueryResult );
      
		//strSql = "select * from LCAddress where CustomerNo = '" + fm.all( 'CustomerNo' ).value + "'";
		
		var sqlid2="LDPersonInputSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(fm.all('CustomerNo').value);
		
		var strQueryResult  = easyQueryVer3(mySql2.getString(), 1, 1, 1);  
		arrSelected = decodeEasyQueryResult(strQueryResult);
      if( arrSelected == null )
      {
				//alert( "�ͻ�ȱ�ٵ�ַ��Ϣ!" );	
				return;
      }
      else
      {	
				displayaddress(arrSelected);
      }
    
		//strSql = "select * from LCAccount where CustomerNo = '" + fm.all( 'CustomerNo' ).value + "'";
		
		var sqlid3="LDPersonInputSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(fm.all('CustomerNo').value);
		
		var strQueryResult  = easyQueryVer3(mySql3.getString(), 1, 1, 1);  
		arrSelected = decodeEasyQueryResult(strQueryResult);
      if( arrSelected == null )
      {
				//alert( "�ͻ�ȱ�������ʺ���Ϣ!" );	
				return;
      }
      else
      {	
				displayaccount(arrSelected);
      }
	}
	else
	{
		initInpBox();
		alert( "û���ҵ��ͻ���Ϣ!" );
	}
}


//��ʾ�ͻ�������Ϣ
function displayperson( arrQueryResult )
{				 
	initInfoBox();
	fm.all( 'CustomerNo' ).value = arrQueryResult[0][0];
  fm.all('Name').value = arrQueryResult[0][1];;
  fm.all('AppntSex').value = arrQueryResult[0][2];
  fm.all('Birthday').value = arrQueryResult[0][3];
  fm.all('AppntIDType').value = arrQueryResult[0][4];
  fm.all('IDNo').value = arrQueryResult[0][5];
  fm.all('Password').value = arrQueryResult[0][6];
  fm.all('AppntNativePlace').value = arrQueryResult[0][7];
  fm.all('AppntNationality').value = arrQueryResult[0][8];
  fm.all('RgtAddress').value = arrQueryResult[0][9];
  fm.all('AppntMarriage').value = arrQueryResult[0][10];
  fm.all('MarriageDate').value = arrQueryResult[0][11];
  fm.all('HealthState').value = arrQueryResult[0][12];
  fm.all('Stature').value = arrQueryResult[0][13];
  fm.all('Avoirdupois').value = arrQueryResult[0][14];
  fm.all('DegreeState').value = arrQueryResult[0][15];
  fm.all('CreditGrade').value = arrQueryResult[0][16];
  fm.all('OthIDType').value = arrQueryResult[0][17];
  fm.all('OthIDNo').value = arrQueryResult[0][18];
  fm.all('ICNo').value = arrQueryResult[0][19];
  fm.all('GrpNo').value = arrQueryResult[0][20];
  fm.all('JoinCompanyDate').value = arrQueryResult[0][21];
  fm.all('StartWorkDate').value = arrQueryResult[0][22];
  fm.all('Position').value = arrQueryResult[0][23];
  fm.all('Salary').value = arrQueryResult[0][24];
  fm.all('OccupationTypeState').value = arrQueryResult[0][25];
  fm.all('OccupationCodeState').value = arrQueryResult[0][26];
  fm.all('WorkType').value = arrQueryResult[0][27];
  fm.all('PluralityType').value = arrQueryResult[0][28];
  fm.all('DeathDate').value = arrQueryResult[0][29];
  fm.all('SmokeFlag').value = arrQueryResult[0][30];
  fm.all('listFlag').value = arrQueryResult[0][31];
  fm.all('ProtertyState').value = arrQueryResult[0][32];
  fm.all('Remark').value = arrQueryResult[0][33];
  fm.all('State').value = arrQueryResult[0][34];
  fm.all('VIPValueState').value = arrQueryResult[0][35]; 
}


//��ʾ�ͻ���ַ��Ϣ
function displayaddress(arrQueryResult)
{
  initAddressBox();
  fm.all('AddressNo').value = arrQueryResult[0][1];
  fm.all('PostalAddress').value = arrQueryResult[0][2];
  fm.all('ZipCode').value = arrQueryResult[0][3];
  fm.all('Phone').value = arrQueryResult[0][4];
  fm.all('Fax').value = arrQueryResult[0][5];
  fm.all('HomeAddress').value = arrQueryResult[0][6];
  fm.all('HomeZipCode').value = arrQueryResult[0][7];
  fm.all('HomePhone').value = arrQueryResult[0][8];
  fm.all('HomeFax').value = arrQueryResult[0][9];
  fm.all('CompanyAddress').value = arrQueryResult[0][10];
  fm.all('CompanyZipCode').value = arrQueryResult[0][11];
  fm.all('CompanyPhone').value = arrQueryResult[0][12];
  fm.all('CompanyFax').value = arrQueryResult[0][13];
  fm.all('Mobile').value = arrQueryResult[0][14];
  fm.all('MobileChs').value = arrQueryResult[0][15];
  fm.all('EMail').value = arrQueryResult[0][16];
  fm.all('BP').value = arrQueryResult[0][17];
  fm.all('Mobile2').value = arrQueryResult[0][18];
  fm.all('MobileChs2').value = arrQueryResult[0][19];
  fm.all('EMail2').value = arrQueryResult[0][20];
  fm.all('BP2').value = arrQueryResult[0][21];
}   


//��ʾ�ͻ������ʺ���Ϣ
function displayaccount(arrQueryResult)
{
  initAccountBox();
  fm.all('AccounteFlag').value = arrQueryResult[0][1];
  fm.all('BankCodeState').value = arrQueryResult[0][2];
  fm.all('GetBankAccNo').value = arrQueryResult[0][3];
  fm.all('AccName').value = arrQueryResult[0][4];
}   


//��ȡ�ͻ���ַ���
function getaddresscodedata()
{
  var i = 0;
  var j = 0;
  var m = 0;
  var n = 0;
  var strsql = "";
  var tCodeData = "0|";
  //strsql = "select AddressNo,PostalAddress from LCAddress where CustomerNo ='"+fm.CustomerNo.value+"'";
  
  var sqlid4="LDPersonInputSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(fm.CustomerNo.value);
  
  turnPage.strQueryResult  = easyQueryVer3(sqlid4.getString(), 1, 0, 1);  
  if (turnPage.strQueryResult != "")
  {
  	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  	m = turnPage.arrDataCacheSet.length;
  	for (i = 0; i < m; i++)
  	{
  		j = i + 1;
  		tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
  	}
  }
  fm.all("AddressNo").CodeData=tCodeData;
}  


//���ݵ�ַ��Ż�õ�ַ��ϸ��Ϣ
function getdetailaddress()
{
 // var strSQL="select b.* from LCAddress b where b.AddressNo='"+fm.AddressNo.value+"' and b.CustomerNo='"+fm.CustomerNo.value+"'";
  
  var sqlid5="LDPersonInputSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(fm.AddressNo.value);
		mySql5.addSubPara(fm.CustomerNo.value);
  
  arrResult=easyExecSql(mySql5.getString());
	try{fm.all('CustomerNo').value= arrResult[0][0];}catch(ex){}; 
	try{fm.all('AddressNo').value= arrResult[0][1];}catch(ex){}; 
	try{fm.all('PostalAddress').value= arrResult[0][2];}catch(ex){}; 
	try{fm.all('ZipCode').value= arrResult[0][3];}catch(ex){}; 
	try{fm.all('Phone').value= arrResult[0][4];}catch(ex){}; 
	try{fm.all('Fax').value= arrResult[0][5];}catch(ex){}; 
	try{fm.all('HomeAddress').value= arrResult[0][6];}catch(ex){}; 
	try{fm.all('HomeZipCode').value= arrResult[0][7];}catch(ex){}; 
	try{fm.all('HomePhone').value= arrResult[0][8];}catch(ex){}; 
	try{fm.all('HomeFax').value= arrResult[0][9];}catch(ex){}; 
	try{fm.all('CompanyAddress').value= arrResult[0][10];}catch(ex){}; 
	try{fm.all('CompanyZipCode').value= arrResult[0][11];  }catch(ex){}; 
	try{fm.all('CompanyPhone').value= arrResult[0][12];}catch(ex){}; 
	try{fm.all('CompanyFax').value= arrResult[0][13];}catch(ex){}; 
	try{fm.all('Mobile').value= arrResult[0][14];}catch(ex){}; 
	try{fm.all('MobileChs').value= arrResult[0][15];}catch(ex){}; 
	try{fm.all('EMail').value= arrResult[0][16];}catch(ex){}; 
	try{fm.all('BP').value= arrResult[0][17];}catch(ex){}; 
	try{fm.all('Mobile2').value= arrResult[0][18];}catch(ex){}; 
	try{fm.all('MobileChs2').value= arrResult[0][19];}catch(ex){}; 
	try{fm.all('EMail2').value= arrResult[0][20];}catch(ex){}; 
	try{fm.all('BP2').value= arrResult[0][21];}catch(ex){}; 
	try{fm.all('Operator').value= arrResult[0][22];}catch(ex){}; 
	try{fm.all('MakeDate').value= arrResult[0][23];}catch(ex){}; 
	try{fm.all('MakeTime').value= arrResult[0][24];}catch(ex){}; 
	try{fm.all('ModifyDate').value= arrResult[0][25];}catch(ex){}; 
	try{fm.all('ModifyTime').value= arrResult[0][26];}catch(ex){}; 
}





//У���ı��������������ֵ�Ƿ����
function confirmSecondInput(aObject,aEvent){	
	if(aEvent=="onkeyup")
	{
    var theKey = window.event.keyCode;
  	
  	if(theKey=="13")
  	{
    	if(theFirstValue!="")
    	{
				theSecondValue = aObject.value;
      	if(theSecondValue=="")
      	{
        	alert("���ٴ�¼�룡");
        	aObject.value="";
        	aObject.focus();
        	return;
      	}
      	if(theSecondValue==theFirstValue)
      	{
        	aObject.value = theSecondValue;
        	return;   
      	}  
      	else{
        	alert("����¼����������������¼�룡");
        	theFirstValue="";
        	theSecondValue="";
        	aObject.value="";
        	aObject.focus();
        	return;
      	}
    	}
    	else{
        theFirstValue = aObject.value;
        if(theFirstValue=="")
        {
        	theSecondValue="";
          alert("��¼�����ݣ�");
          return;  
        }
        aObject.value="";
        aObject.focus();
      	return;            
    	}
  	}
	}
	
	else if(aEvent=="onblur")
	{
   	if(theFirstValue!="")
   	{
     	theSecondValue = aObject.value;
     	if(theSecondValue=="")
     	{
       	alert("���ٴ�¼�룡");
        aObject.value="";
        aObject.focus();
        return;
      }
      if(theSecondValue==theFirstValue)
      {
        aObject.value = theSecondValue;
        theSecondValue="";
        theFirstValue="";
        return;              
      }           
      else{
        alert("����¼����������������¼�룡");
        theFirstValue="";
        theSecondValue="";
        aObject.value="";
        aObject.focus();
        return;
      }
    }
		else{
      theFirstValue = aObject.value;
      if(theFirstValue=="")
      {
				return;  
      }
      aObject.value="";
      aObject.focus();
      return; 
  	}
	}
}
function resetForm()
{
	initForm();
	}