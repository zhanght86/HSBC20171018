//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�EdorFormPrint.js
//�����ܣ������ӡ
//�������ڣ�2005-08-13 15:39:06
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����


var showInfo;
var turnPage = new turnPageClass();



//�ύ�����水ť��Ӧ����
function printBill()
{  
  var i = 0;
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
  var StartDate = fm.StartDate.value;
  var EndDate = fm.EndDate.value;
  var BillType = fm.BillType.value;
  var ManageCom = fm.ManageCom.value;
  var SaleChnl = fm.SaleChnl.value;  
  var RiskFlag = fm.RiskFlag.value;  
  var PayIntv = fm.PayIntv.value; 
  var Statistic = fm.Statistic.value; 
  var RiskCode = fm.RiskCode.value; 
  var Period = fm.Period.value; 
  if(SaleChnl == "")
  {
  	   showInfo.close();
  	   alert("��ѡ��������");
  	   fm.SaleChnl.focus();
  	   return;
  }  
  if(BillType == "")
  {
  	   showInfo.close();
  	   alert("��ѡ���嵥���ͣ�");
  	   fm.BillType.focus();
  	   return;
  }
  if(StartDate == "" || EndDate == "")
  {
  	    showInfo.close();
    	alert("��������ʼ���ںͽ�������!");
    	return;
  }

  if(!checkDate(StartDate)||!checkDate(EndDate))
  {
  	showInfo.close();
  	alert("����Ч�����ڸ�ʽ��");
    return;
  }
  if(RiskFlag == "Yes"){
  	  if(RiskCode == ""){
  	  	  showInfo.close();
  	      alert("��ѡ�����ִ��룡");
  	      fm.RiskCode.focus();
  	      return;
  	  }
  	  if(PayIntv == ""){
  	  	  showInfo.close();
  	      alert("��ѡ��ɷѷ�ʽ��");
  	      fm.PayIntv.focus();
  	      return;
  	  }
  }
  var startValue=StartDate.split("-");
  var dateStartDate = new Date(startValue[0],startValue[1]-1,startValue[2]);
  var endValue=EndDate.split("-");
  var dateEndDate = new Date(endValue[0],endValue[1]-1,endValue[2]);  
  if(dateStartDate.getTime() > dateEndDate.getTime())  
  {
  	    showInfo.close();
    	alert("ͳ�����ڲ�������ͳ��ֹ�ڣ�");
    	return;
  }
  var tFlag = fm.Flag.value;
  if(tFlag=="1")
  {
        var DATEDIFF = 31;
        if(startValue[1] == 11 && endValue[1] == 12)
        {
        	DATEDIFF = 36;
        }
        var day = dateDiff(dateStartDate,dateEndDate,"D");
        if(day>DATEDIFF)
        {   
        	showInfo.close();		 
	 		alert("���ڵ��±���ͳ�����ں�ֹ��ֻ�����һ���£�");
	 		return ;        	
        }
  }
  if(fm.CTFlag.value == "1")
  {
  	if(fm.DateType.value == "")
  	{
  	   showInfo.close();
  	   alert("��ѡ��ͳ����ֹ�ڵ����ͣ�");
       fm.DateType.focus();
  	   return ;
  	}
    if(fm.DateType.value != "1"&&fm.DateType.value != "2")
    {
     	showInfo.close();
    	alert("ͳ����ֹ�ڵ���������");
    	fm.DateType.focus();
    	return ;
    }
  }
  fm.fmtransact.value = "PRINT";
  fm.target = "f1print";		
  fm.submit();
  showInfo.close();				

}



//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  //showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
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
}


function showChnlType()
{

	var SaleChnl = fm.SaleChnl.value;
	var ChnlSel = fm.ChnlSel.value;
	if(SaleChnl!=ChnlSel)
	{
		fm.ChnlSel.value = SaleChnl;    //��¼ÿ����ѡ���������������������ձ�������
	    document.all('BillType').value = '';
	    document.all('BillTypeName').value = '';		
	}

	switch(SaleChnl)
	{
		case "1":  fm.ChnlType.value = "bqformperson";
					break;
		case "2":  fm.ChnlType.value = "bqformgrp";
			        break;
		case "3":  fm.ChnlType.value = "bqformbank";
			        break;			                    
		default:    fm.ChnlType.value = ""; 
		break;
	}
	initRiskCode(SaleChnl);
}
function initBillType(cObjCode,cObjName)
{
	var ChnlType = fm.ChnlType.value;
    if(ChnlType == null||ChnlType == "")
    {
     	alert("����ѡ����������ѡ�񱨱�");
     	fm.SaleChnl.focus();
   	    return;
    }
    return showCodeList(ChnlType,[cObjCode,cObjName],[0,1],null,null,null,null,'230');
}
function onKeyUpBillType(cObjCode,cObjName)
{
	var ChnlType = fm.ChnlType.value;
    if(ChnlType == null||ChnlType == "")
    {
    	alert("����ѡ����������ѡ�񱨱�");
    	fm.SaleChnl.focus();
    	return;
    }
    return showCodeListKey(ChnlType,[cObjCode,cObjName],[0,1],null,null,null,null,'230');
}
//��Ϊ��̨ȡĬ����ֹ�ڣ��˺�����������
function initDate()
{


        var today = new Date();
	    var thisDay = 25;
	    var preDay = 26;
    	var tYear = today.getYear();
    	var preYear = tYear;
    	var thisMonth = today.getMonth()+1;
	    var preMonth = thisMonth-1;
	    if(thisMonth == 1)
	    {
	       preMonth = thisMonth;
	       preDay = 1;
	    }
	    if(thisMonth == 12)
	    {
	       thisDay = 31;
	    }
        document.all('StartDate').value = preYear+"-"+preMonth+"-"+preDay;
        document.all('EndDate').value = tYear+"-"+thisMonth+"-"+thisDay;  	 	

}

function afterCodeSelect( cCodeName, Field )
{
	  try	{
		    if( cCodeName == "bqformbank" || cCodeName == "bqformperson")	
		    {
		    	
		    	  var BillType = document.all("BillType").value; 
		    	  if(BillType == "103" || BillType == "303")
		    	  {
		    		   divform.style.display = "";
		    		   divPayTitle.style.display = "";
		    		   divPayIntv.style.display = "";
		    		   document.all('RiskFlag').value = "Yes";	
		    		   document.all('Statistic').value = "0";
		    		   document.all('Period').value = "0";	    	  	 
		    	  }
		    	  else if(BillType == "304" || BillType == "104")
		    	  {
		    		   divform.style.display = "";
		    		   divPayTitle.style.display = "";
		    		   divPayIntv.style.display = "";
		    		   document.all('RiskFlag').value = "Yes";	
		    		   document.all('Statistic').value = "0";
		    		   document.all('Period').value = "1";	    	  	 
		    	  }
		    	  else if(BillType == "306" || BillType == "106")
		    	  {
		    		   divform.style.display = "";
		    		   divPayTitle.style.display = "none";
		    		   divPayIntv.style.display = "none";
		    		   document.all('RiskFlag').value = "NO";	
		    		   document.all('Statistic').value = "1";
		    		   document.all('Period').value = "1";	    	  	 
		    	  }	    	  
		    	  else 
		          {
		    		 divPayTitle.style.display = "none";
		    		 divPayIntv.style.display = "none";
		    	     divform.style.display = "none";
		    	     document.all('RiskFlag').value = "NO";
		         }
		         if(fm.BillType.value == "101" || fm.BillType.value == "301")
		         {
		        	fm.Flag.value = "1";//���±����ͳ��
		         }
		        else{
		        	fm.Flag.value = "0";//�ۼƱ����ͳ��
		        }
                 checkCT();//�˱�������ѡ����������
		    }	  
	  }
	  catch( ex ) {
	  }
}
function checkCT()
{
	var tBillType = fm.BillType.value;
	var tCodeType = fm.ChnlType.value;
	if(tBillType!=null && tBillType!="")
	{
//		var strSql = "select trim(othersign) from ldcode "
//		              + " where codetype = '"+tCodeType+"' "
//		              + " and code = '"+tBillType+"'";
//		
		var sqlid1="EdorFormPrintSql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("bq.EdorFormPrintSql");
	 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
	 	mySql1.addSubPara(tCodeType);//ָ���������
	 	mySql1.addSubPara(tBillType);
	 	var strSql = mySql1.getString();
		
		var brr = easyExecSql(strSql);
		if(brr)
		{
			//ldcode����othersign:
			//Ϊ2����Ҫѡ����������
			if(brr[0][0]=="2")
		    {
				divDateType.style.display = ''; 
				fm.CTFlag.value = "1";
	            fm.DateType.value = "2";
	            fm.DateTypeName.value = "��Ч����";
				return;		    	
		    }
		}
	}
	divDateType.style.display = 'none';
	fm.CTFlag.value = "0";
}

//��ʼ��ҽԺ
function initRiskCode(SaleChnl)
{
	var i,j,m,n;
	var returnstr;
	var strSql = "";
	if(SaleChnl == "1")
	{
//		strSql = "select riskcode,riskname from lmriskapp where riskperiod = 'L' and subriskflag = 'M' and riskprop in ('I','C','D','A') union select '000000','���и�������' from dual";
		
		var sqlid2="EdorFormPrintSql2";
	 	var mySql2=new SqlClass();
	 	mySql2.setResourceName("bq.EdorFormPrintSql");
	 	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
	 	strSql = mySql2.getString();
	
	}
    else if(SaleChnl == "3")
    {
//		strSql = "select riskcode,riskname from lmriskapp where riskperiod = 'L' and subriskflag = 'M' and riskprop in ('Y','B','C','D') union select '000000','���д�������' from dual";
    
		var sqlid3="EdorFormPrintSql3";
	 	var mySql3=new SqlClass();
	 	mySql3.setResourceName("bq.EdorFormPrintSql");
	 	mySql3.setSqlId(sqlid3); //ָ��ʹ��SQL��id
	 	strSql = mySql3.getString();
	
		
    }
    else 
    {
//		strSql = "select riskcode,riskname from lmriskapp where riskperiod = 'L' and subriskflag = 'M' union select '000000','��������' from dual";
    
		var sqlid4="EdorFormPrintSql4";
	 	var mySql4=new SqlClass();
	 	mySql4.setResourceName("bq.EdorFormPrintSql");
	 	mySql4.setSqlId(sqlid4); //ָ��ʹ��SQL��id
	 	strSql = mySql4.getString();
		
    }
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  //alert(strSql);
  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);  

  var returnstr = "";
  var n = turnPage.arrDataCacheSet.length;
  //alert("N:"+n);
  if (n > 0)
  {
  	for( i = 0;i < n; i++)
  	{
  		m = turnPage.arrDataCacheSet[i].length;
  		//alert("M:"+m);
  		if (m > 0)
  		{
  			for( j = 0; j< m; j++)
  			{
  				if (i == 0 && j == 0)
  				{
  					returnstr = "0|^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i == 0 && j > 0)
  				{
  					returnstr = returnstr + "|" + turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j == 0)
  				{
  					returnstr = returnstr+"^"+turnPage.arrDataCacheSet[i][j];
  				}
  				if (i > 0 && j > 0)
  				{
  					returnstr = returnstr+"|"+turnPage.arrDataCacheSet[i][j];
  				}
  				
  			}
  		}
  		else
  		{
  			alert("��ѯʧ��!!");
  			return "";
  		}
  	}
}
else
{
	alert("��ѯʧ��!");
	return "";
}
  //alert("returnstr:"+returnstr);		
  fm.RiskCode.CodeData = returnstr;
  return returnstr;
}
function isLeap(tYear)
{
	return (tYear%4)==0 ? ((tYear%100)==0?((tYear%400)==0?true:false):true):false;
}
function getMonthLength(tYear,tMonth)
{
    if(tMonth > 12 || tMonth < 1)
        return 0;
    var MONTH_LENGTH = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
    var LEAP_MONTH_LENGTH = new Array(31,29,31,30,31,30,31,31,30,31,30,31);
   
    return isLeap(tYear) ? LEAP_MONTH_LENGTH[tMonth-1] : MONTH_LENGTH[tMonth-1];
}
/**
 * ����У�麯��
 �� ������Common.js�ж��������ڸ�ʽ��У�飬��������������˻�ֱ�Ӱ�"2006-05-31"��Ϊ"2006-04-31"����ѯ,���϶��������ܳ����·ݳ��ȵ�У�顣
 * �����������ַ���
 * added by liurx 2006-05-25
 */
function checkDate(tDate)
{
	var dateValue;
	var tYear;
	var tMonth;
	var tDay;
	if(isDate(tDate))
	{
		dateValue = tDate.split("-");
		tYear = eval(dateValue[0]);
		tMonth = eval(dateValue[1]);
		tDay = eval(dateValue[2]);
		if(tDay > getMonthLength(tYear,tMonth))
		{
			return false;
		}
		return true;
	}
	else if(isDateN(tDate))
	{
		dateValue = new Array();
        dateValue[0]=tDate.substring(0, 4);
        dateValue[1]=tDate.substring(4, 6);
        dateValue[2]=tDate.substring(6, 8);
		
		tYear = eval(dateValue[0]);
		tMonth = eval(dateValue[1]);
		tDay = eval(dateValue[2]);
		if(tDay > getMonthLength(tYear,tMonth))
		{
			return false;
		}
		return true;
	}
	else if(isDateI(tDate))
	{
		dateValue = tDate.split("/");
		tYear = eval(dateValue[0]);
		tMonth = eval(dateValue[1]);
		tDay = eval(dateValue[2]);
		if(tDay > getMonthLength(tYear,tMonth))
		{
			return false;
		}
		return true;
	}
	else
	{
		return false;
	}
}