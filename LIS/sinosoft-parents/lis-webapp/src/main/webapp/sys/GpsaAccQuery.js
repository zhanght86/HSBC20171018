var turnPage = new turnPageClass(); 
var turnPage1 = new turnPageClass(); 
function LCInsureAccQuery()
{

  initLcInsureAccGrid();
  
  var sqlid826091902="DSHomeContSql826091902";
var mySql826091902=new SqlClass();
mySql826091902.setResourceName("sys.GpsaAccQuerySql");//ָ��ʹ�õ�properties�ļ���
mySql826091902.setSqlId(sqlid826091902);//ָ��ʹ�õ�Sql��id
mySql826091902.addSubPara(tPolNo);//ָ������Ĳ���
var strSQL=mySql826091902.getString();

//	var strSQL = "select a.InsuAccNo,"
//		+" (select d.InsuAccName from LMRiskToAcc d where d.InsuAccNo = a.InsuAccNo and d.riskcode=a.riskcode),"
//		+" a.AccFoundDate,a.BalaDate,a.InsuAccBala "
//		+" from LCInsureAcc a where PolNo='"+  tPolNo +"'";
  				 	
 
  
 turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    
  
    alert("��ѯʧ�ܣ�");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = LCInsureAccGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
	
}

function LCInsureAccClassQueryb()
{
 
	initLCInsureAccClassGrid();
	
	var sqlid826092003="DSHomeContSql826092003";
var mySql826092003=new SqlClass();
mySql826092003.setResourceName("sys.GpsaAccQuerySql");//ָ��ʹ�õ�properties�ļ���
mySql826092003.setSqlId(sqlid826092003);//ָ��ʹ�õ�Sql��id
mySql826092003.addSubPara(tPolNo);//ָ������Ĳ���
mySql826092003.addSubPara(cInsuAccNo);//ָ������Ĳ���
var strSQL=mySql826092003.getString();
  
//	var strSQL = "select a.InsuAccNo,a.PayPlanCode,"
//		+"(select d.PayPlanName from LMRiskAccPay d where d.InsuAccNo = a.InsuAccNo and d.PayPlanCode = a.PayPlanCode),"
//		+"a.OtherNo,a.OtherType,a.AccFoundDate,a.BalaDate,a.AccAscription,a.InsuAccBala "
//		+" from LCInsureAccClass a"
//		+" where PolNo='"+tPolNo +"'" + " and InsuAccNo='" + cInsuAccNo +"'";
  				 	
 

 turnPage1.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage1.strQueryResult) {
    
 
    alert("��ѯʧ�ܣ�");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage1.arrDataCacheSet = decodeEasyQueryResult(turnPage1.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage1.pageDisplayGrid = LCInsureAccClassGrid;    
          
  //����SQL���
  turnPage1.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage1.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet = turnPage1.getData(turnPage1.arrDataCacheSet, turnPage1.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage1.pageDisplayGrid);
  
	
}


function LCInsureAccClassQuery(){
	
	var tSel = LCInsureAccGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "�������ʻ���ѯ��Ϣ��ѡ��һ����¼." );
	else
	{ divLCPol1.style.display=""
	  cInsuAccNo = LCInsureAccGrid.getRowColData( tSel - 1, 1 );
		try
		{ 
		  LCInsureAccClassQueryb();
		  
		}
		catch(ex)
		{
			alert( ex );
		}
	}
	
}

function GoBack(){
	
	top.opener.window.focus();
	
	top.window.close();
	
	
	
}

function LCInsureAccTrace(){
	/*var cInsuAccNo="1"
	var cPayPlanCode="2"
	window.open("./GpsLCInsureAccTraceQuery.jsp?PolNo="+ tPolNo + "&" +  "InsuAccNo=" + cInsuAccNo + "&" + "PayPlanCode=" + cPayPlanCode);*/
	var arrReturn = new Array();
	var tSel = LCInsureAccClassGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "���������ʻ���ѯ��Ϣ��ѡ��һ����¼." );
	else
	{
		var cInsuAccNo = LCInsureAccClassGrid.getRowColData( tSel - 1, 1 );
		var cPayPlanCode =  LCInsureAccClassGrid.getRowColData( tSel - 1, 2 );
		if(cInsuAccNo!="")
		{   try
		    {
			     window.open("./GpsLCInsureAccTraceQuery.jsp?PolNo="+ tPolNo + "&" +  "InsuAccNo=" + cInsuAccNo + "&" + "PayPlanCode=" + cPayPlanCode,'','width='+screen.availWidth+',height='+screen.availHeight+',top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0');
		  
		    }
		    catch(ex)
		    {
			    alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
		    }

    }
   else{ alert( "���Ȳ�ѯ���ʻ���ѯ��Ϣ." ); return;}

	}  
}



function AccValueCal()
{
	
	var showStr = "���ڽ����ʻ���ֵ���㣬�����Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr = "../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 	  
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();
  
	document.all("PolNo").value=tPolNo;
    //alert(document.all("PolNo").value);
        	
	fm.action = "./GpsaAccQuerySave.jsp";
    fm.target="fraSubmit";
    document.getElementById("fm").submit();


}



function afterSubmit( FlagStr, content,dAcc,dInterest )
{
    showInfo.close();
	//alert(document.all("PolNo").value);
    if (FlagStr == "Fail" )
    {
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
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
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
        var iWidth=550;      //�������ڵĿ��; 
        var iHeight=350;     //�������ڵĸ߶�; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();
              
      	document.all("AccValue").value=dAcc;
		document.all("Interest").value=dInterest;	

    }
}