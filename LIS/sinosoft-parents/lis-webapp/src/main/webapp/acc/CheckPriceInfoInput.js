//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//window.onfocus=myonfocus;
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var sqlresourcename = "acc.CheckPriceInfoInputInputSql";
//�ύ�����水ť��Ӧ����

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

 function updateClick0()
 {
 	  if(document.all('State').value=='0')
 	  {
 	   alert("��¼״̬�Ѵ�����Ч״̬��");
 	   initForm();
 	  	return false;
 	 }else{

 	  	document.all('State').value=0;
 	  	document.all( 'StateName' ).value='0-��Ч';
 	    updateClick();

  }
 }
  function updateClick2()
  {
  	if(document.all('State').value=='1')
  	{
  	document.all('State').value=2;
  	updateClick();
  			document.all( 'StateName' ).value='2-������ȷ';
    }else{
    		alert("��¼״̬������¼��״̬�����ܽ��д˲�����");
 	  	return false;
    }
  }
   function updateClick3()
   {
   	  if(document.all('State').value=='1')
   	  {
     	document.all('State').value=3;
     	updateClick();
     	document.all( 'StateName' ).value='3-���˴���';
       }else{
    		alert("��¼״̬������¼��״̬�����ܽ��д˲�����");
 	    	return false;
        }
   }
    function updateClick4()
    {
    		if(document.all('State').value=='2')
    		{
       	document.all('State').value=4;
       	updateClick();
       			document.all( 'StateName' ).value='4-ȷ����ȷ';
         }else{
    		alert("��¼״̬�����ڸ�����ȷ״̬�����ܽ��д˲�����");
 	     	return false;
         }
    }
    function updateClick5()
    {
    	//if(document.all('State').value=='3')
    	//	{
       	document.all('State').value=5;
       	updateClick();
       		document.all( 'StateName' ).value='5-ȷ�ϴ���';
      //   }else{
    	//	alert("��¼״̬�����ڸ��˴���״̬�����ܽ��д˲�����");
 	     //	return false;
       //  }
    }


var oldRiskCode;
var oldInsuAccNo;
var oldStartDate;
var oldEndDate;
var oldInvestFlag;
var oldSRateDate;
var oldARateDate;
var oldUnitPriceBuy;
var oldUnitPriceSell;
var oldRedeemRate;
var oldRedeemMoney;
 function updateClick()
{
  //����������Ӧ�Ĵ���
  //���е������ֶ�"�����"��ΪUPDATE


  document.all('DoBatch').value = '';

   if(document.all('InsuAccNo').value == '')
    {
    alert("����ѡ��Ҫ��Ϊ��Ч�ļ�¼!");
    initForm();
    	return false;
    	}
    if(document.all('StartDate').value == '')
    {
    	alert("����ѡ��Ҫ��Ϊ��Ч�ļ�¼!");
    	initForm();
    	return false;
    	}
   if(oldInsuAccNo!=document.all('InsuAccNo').value)
  {
  	alert("�����޸�����2��");
  	return false;
  }
  //alert(document.all('StartDate').value);
   if(oldStartDate!=document.all('StartDate').value)
  {
  	alert("�����޸�����3��");
  	return false;
  }

//  if(oldEndDate!=document.all('EndDate').value)
//  {
//  	alert("�����޸�����4��");
//  	return false;
//  }
//  if(oldInvestFlag!=document.all('InvestFlag').value)
//  {
//  	alert("�����޸�����5��");
//  	return false;
//  }
//  if(oldSRateDate!=document.all('SRateDate').value)
//  {
//  	alert("�����޸�����6��");
//  	return false;
//  }
//  if(oldARateDate!=document.all('ARateDate').value)
//  {
//  	//alert("�����޸�����7��");
//  	//alert(oldARateDate+";;;"+document.all('ARateDate').value);
//  	//return false;
//  }
//
//  if(oldUnitPriceBuy!=document.all('UnitPriceBuy').value)
//  {
//  	alert("�����޸�����8��");
//  	return false;
//  }
//  if(oldUnitPriceSell!=document.all('UnitPriceSell').value)
//  {
//  	alert("�����޸�����9��");
//  	return false;
//  }
//  if(oldRedeemRate!=document.all('RedeemRate').value)
//  {
//  	alert("�����޸�����10��");
//  	return false;
//  }
//  if(oldRedeemMoney!=document.all('RedeemMoney').value)
//  {
//  	alert("�����޸�����11��");
//  	return false;
//  }

    document.all('Transact').value ="CONFIRM";
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
   //initForm();
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

    if(document.all('SRateDate').value == '')
    {
    	alert("�۸�Ӧ�������ڲ���Ϊ��!");
    	return false;
    	}

    if(!isDate(document.all('SRateDate').value))
    {
    	alert("�۸�Ӧ����������������!");
    	return false;
    	}
    if(!isDate(document.all('ARateDate').value))
    {
    	alert("�۸�ʵ�ʹ���������������!");
    	return false;
    	}


    if(document.all('UnitPriceBuy').value == '')
    {
    	alert("��λ����۸���Ϊ��!");
    	return false;
    	}
    if(document.all('UnitPriceSell').value == '')
    {
    	alert("��λ�����۸���Ϊ��!");
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


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
	//alert("afterSubmit-begin");
  showInfo.close();
  //alert("afterSubmit-begin");
  if (FlagStr == "Fail" )
  {  //alert("afterSubmit-01");
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
  { //alert("afterSubmit-02");
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
    //ִ����һ������
//    initForm();
  }
  //alert("afterSubmit-03");
  easyQueryClick();
}

function easyQueryClick()
{
	// ��ʼ�����
	initCollectivityGrid();

	// ��дSQL���
	var mySql=new SqlClass();
	    mySql.setResourceName(sqlresourcename);
	    mySql.setSqlId("CheckPriceInfoInputInputSql1");
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
		   mySql.setSqlId("CheckPriceInfoInputInputSql4");
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
    	//alert(arrResult);
   		document.all( 'RiskCode' ).value = arrResult[0][0];
		oldRiskCode=arrResult[0][0];
		//InputRiskCodeName();
		document.all( 'InsuAccNo' ).value = arrResult[0][1];
		oldInsuAccNo= arrResult[0][1];
		InputInsuAccNoName();
		document.all( 'StartDate' ).value = arrResult[0][2];
		oldStartDate = arrResult[0][2];
		document.all( 'EndDate' ).value = arrResult[0][3];
		document.all( 'InvestFlag' ).value = arrResult[0][4];
		//if(document.all( 'InvestFlag' ).value=='1')
		//{
		//	document.all( 'InvestFlagName' ).value='�������';
		//}
		//if(document.all( 'InvestFlag' ).value=='2')
		//{
		//	document.all( 'InvestFlagName' ).value='�ݶ�����';
		//}
		document.all( 'SRateDate' ).value = arrResult[0][5];
		document.all( 'ARateDate' ).value = arrResult[0][6];
		document.all( 'InsuTotalMoney' ).value = arrResult[0][7];
		document.all( 'Liabilities' ).value = arrResult[0][8];
		//document.all( 'RedeemRate' ).value = arrResult[0][9];
		//if(document.all( 'RedeemRate' ).value=='null')
		//{
		//	document.all( 'RedeemRate' ).value='';
		//}
		//alert("Flag");
		document.all( 'State' ).value = arrResult[0][10];
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
		//document.all( 'RedeemMoney' ).value = arrResult[0][11];
		//if(document.all( 'RedeemMoney' ).value =='null')
		//{
		//	document.all( 'RedeemMoney' ).value ='';
		//}  
		document.all( 'CompanyUnitCount' ).value = arrResult[0][12];
		document.all( 'ComChgUnitCount' ).value = arrResult[0][13];
		document.all('CustomersUnitCount').value = arrResult[0][14];
		document.all('SKFlag').value = arrResult[0][15];
//		if(document.all('SKFlag').value=='0')
//		{
//			document.all('SKFlagName').value='������';
//		}
//		else
//		{
//			document.all('SKFlagName').value='������';
//		}
		document.all('UnitPriceBuy').value = CollectivityGrid.getRowColData(tSel-1,3);
		document.all('UnitPriceSell').value = CollectivityGrid.getRowColData(tSel-1,3);
	}
}
 function InputRiskCodeName()
 {
 	var mySql=new SqlClass();
       mySql.setResourceName(sqlresourcename);
	   mySql.setSqlId("CheckPriceInfoInputInputSql9");
	   mySql.addSubPara(document.all('RiskCode').value);
	   var tDateResult = easyExecSql(mySql.getString());

     if(tDateResult!=null)
     { 
     	document.all( 'RiskCodeName' ).value=tDateResult[0][1];
     }
 }
 function InputInsuAccNoName()
 {
      var mySql=new SqlClass();
	       mySql.setResourceName(sqlresourcename);
		   mySql.setSqlId("CheckPriceInfoInputInputSql8");
		   mySql.addSubPara(document.all('RiskCode').value);
		   mySql.addSubPara(document.all('InsuAccNo').value);
 	   var tDateResult = easyExecSql(mySql.getString());

       if(tDateResult!=null)
       { 
       	document.all( 'InsuAccNoName' ).value=tDateResult[0][1];
       }
 }

 function BatchDeal()
 {
 	   if(!BatchDealCheck())
 	   {
 	   	   return ;
 	   }
     document.all('DoBatch').value = 'OK';
 	   //location="./BatchDealSave.jsp?&DealDate="+document.all('DealDate').value;
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
 	     //location="./CheckPriceInfoInput.jsp";
 	      document.getElementById("fm").submit();
}
function findriskcode()
{
	 if(document.all('QureyRiskCode').value == '')
 	   {
 	   	alert("����ѡ�����ֱ��룡");
 	   	return false;
 	  }
}
function findriskcode2()
{
	 if(document.all('RiskCode').value == '')
 	   {
 	   	alert("����ѡ�����ֱ��룡");
 	   	return false;
 	  }
}
function BatchDealCheck()
{
	   if(document.all('DealDate').value == '')
 	   {
 	   	  alert("�Ƽ����ڲ��ܿգ�");
 	   	  return false;
 	   }
 	   if(document.all('NextPriceDate').value == '')
 	   {
 	   	  alert("�¸��Ƽ��ղ��ܿգ�");
 	   	  return false;
 	   }
 	   var mySql=new SqlClass();
	       mySql.setResourceName(sqlresourcename);
		   mySql.setSqlId("CheckPriceInfoInputInputSql7");
		   mySql.addSubPara(document.all( 'DealDate' ).value);
 	   var arr = easyExecSql(mySql.getString());

    // mySql.setSqlId("CheckPriceInfoInputInputSql_5");
     if(arr[0][0]=='0')
     {
     	   if(!confirm("����������δ������Ͷ���ʻ����мƼۣ��Ƿ������"))
     	   {
             return false;
         }
     }
     if(document.all('DealDate').value!=document.all('OldDealDate').value)
     {
     	   if(!confirm("���ϴ�Լ���ļƼ��ղ�ͬ���Ƿ������"))
     	   {
             return false;
         }
     }
     if(document.all('DealDate').value!=document.all('OldDealDate').value || document.all('NextPriceDate').value!=document.all('OldNextPriceDate').value)
     {
     	   var tdate = document.all('NextPriceDate').value
     	   var tNextPriceDate = new Date(tdate.substr(0,4),tdate.substr(5,2)-1,tdate.substr(8,2));
     	   if(tNextPriceDate.getDay()==0 || tNextPriceDate.getDay()==6 )
     	   {
     	       if(!confirm("�¸��Ƽ���Ϊ�����գ��Ƿ������"))
     	       {
                 return false;
             }
     	   }
     	 var mySql2=new SqlClass();
	     mySql2.setResourceName(sqlresourcename);
         mySql2.setSqlId("CheckPriceInfoInputInputSql6");
         mySql2.addSubPara(document.all('NextPriceDate').value);
         mySql2.addSubPara(document.all('DealDate').value);
         var brr = easyExecSql(mySql2.getString());
         var interval =brr[0][0];
         if(interval<=0)
         {
         	   alert("������¸��Ƽ���Ӧ���ڱ��μƼ���֮��")
         	   return false;
         }
         if(interval>1)
         {
    	        if(!confirm("�´μƼ����뱾�μƼ����������һ�죬�Ƿ��¸��Ƽ�����Ϊ���գ�"))
    	        {
                 return false;
                }
         }
     }
     return true;
}
function initPiceDate()
{
 	 var mySql=new SqlClass();
 	 mySql.setResourceName(sqlresourcename);
	 mySql.setSqlId("CheckPriceInfoInputInputSql10");
     var arr=easyExecSql(mySql.getString());
     if(arr!=null)
     {
        fm.DealDate.value=arr[0][0];
        fm.OldDealDate.value=arr[0][0];
        var tdate = arr[0][1];
     	  var tNextPriceDate = new Date(tdate.substr(0,4),tdate.substr(5,2)-1,tdate.substr(8,2));
     	  var interval=0;
     	  var taday = new Date();
     	  if(tNextPriceDate.getDay()==0)
     	  {
     	  	  interval = 1;
     	  }
     	  if(tNextPriceDate.getDay()==6)
     	  {
     	  	  interval = 2;
     	  }
     	  mySql.setSqlId("CheckPriceInfoInputInputSql11");
     	  mySql.addSubPara(tdate);
     	  mySql.addSubPara(interval);
     	  var brr=easyExecSql(mySql.getString());
     	  if(brr!=null)
     	  {
     	  	  fm.NextPriceDate.value = brr[0][0];
              fm.OldNextPriceDate.value = brr[0][0];
     	  }

     }
}