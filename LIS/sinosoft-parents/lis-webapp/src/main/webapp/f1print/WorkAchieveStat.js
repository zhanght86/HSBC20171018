//���ļ��а����ͻ�����Ҫ����ĺ������¼�

//�������ƣ�WorkAchieveStat.js
//�����ܣ�������Чͳ���嵥
//�������ڣ�2005-11-29 17:20:22
//������  ��liurx
//���¼�¼��  ������    ��������      ����ԭ��/���� 


var showInfo;
var turnPage = new turnPageClass();
var tTurnPage = new turnPageClass();


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
  var ManageCom = fm.ManageCom.value;
  if(ManageCom == "")
  {
  	    showInfo.close();
    	alert("��ѡ��������!");
    	return;
  }
  if(StartDate == "" || EndDate == "")
  {
  	    showInfo.close();
    	alert("��������ʼ���ںͽ�������!");
    	return;
  }
  if (!isDate(StartDate)&&!isDateN(StartDate)||!isDate(EndDate)&&!isDateN(EndDate))
  {
  	    showInfo.close();
		alert("����Ч�����ڸ�ʽ��ӦΪ(YYYY-MM-DD)����(YYYYMMDD)");
		return ;
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
  else
  {
  	    //
		fm.fmtransact.value = "PRINT";
		fm.target = "f1print";		
		fm.submit();
		showInfo.close();				
  }
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
//�˺����������ã���Ϊ��init�ļ��е��ú�̨��ʼ��
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
//�����û���Ϣ��ѯҳ��
function queryUsr()
{
	if(document.all('ManageCom').value=="")
	{
		 alert("����ѡ����������");
		 return;
	}
	var newWindow = window.open("../sys/UsrCommonQueryMain.jsp?ManageCom="+document.all('ManageCom').value,"UsrCommonQueryMain",'width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');

}
//�����û�����
function afterQuery(arrResult)
{
  if(arrResult!=null)
  {
    fm.UsrCode.value = arrResult[0][0];
  }
}
function initEdorType()
{
    	var i,j,m,n;
	    var returnstr;
//	    var strSQL = "select edorcode,edorname from lmedoritem where appobj in('I','B') order by edorcode";
	    
		var sqlid1="WorkAchieveStatSql0";
		var mySql1=new SqlClass();
		mySql1.setResourceName("f1print.WorkAchieveStatSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		var strSQL=mySql1.getString();
	    
	    
	    tTurnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1); 
        if (tTurnPage.strQueryResult == "")
        {
          return "";
        }
        tTurnPage.arrDataCacheSet = decodeEasyQueryResult(tTurnPage.strQueryResult);  
        var returnstr = "";
        var n = tTurnPage.arrDataCacheSet.length;
        if (n > 0)
        {
        	for( i = 0;i < n; i++)
        	{
  	        	m = tTurnPage.arrDataCacheSet[i].length;
        		if (m > 0)
  		        {
  		        	for( j = 0; j< m; j++)
  			        {
  		 		        if (i == 0 && j == 0)
  			        	{
  				        	returnstr = "0|^"+tTurnPage.arrDataCacheSet[i][j];
  			        	}
  			        	if (i == 0 && j > 0)
  			        	{
  					        returnstr = returnstr + "|" + tTurnPage.arrDataCacheSet[i][j];
  				        }
  			         	if (i > 0 && j == 0)
  				        {
  					        returnstr = returnstr+"^"+tTurnPage.arrDataCacheSet[i][j];
  				        }
  			        	if (i > 0 && j > 0)
  				        {
  					        returnstr = returnstr+"|"+tTurnPage.arrDataCacheSet[i][j];
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
         fm.EdorType.CodeData = returnstr;
         return returnstr;
	
}