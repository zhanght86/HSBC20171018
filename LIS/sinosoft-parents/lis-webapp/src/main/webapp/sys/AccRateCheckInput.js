//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//�������ƣ�
//�����ܣ����������������
//�������ڣ�2007-11-09 17:55:57
//������  ������ͥ
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{

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

    showInfo.close();
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

    showInfo.close();
  }
  queryClick();
}



//addClickClick�¼�
//function addClick()
//{

//	var tClag=fm.RiskCode.value;
//	if(tClag==null || tClag=='')
//	{
//		alert("���ݲ�����������������");
//		return;
//		}
//
//   var tFlag=false;
//   var tLlag=false;   
//   var tRlag=false;
//   var tIlag=false;
//   var tBalaDate=fm.BalaDate.value;
//
//   tBalaDate=tBalaDate.substring(8,tBalaDate.length);
//   if('01'!=tBalaDate)
//   {
//   	       if (window.confirm("�����ղ���ȷ���Ƿ����?"))
//          {
//            tFlag=true;
//          }else{
//          	return ;
//          }
//   	}
//   
//   var tRate=fm.Rate.value;
//   if(tRate>0.05 || tRate<0.0 || tRate=='' || tRate==null)
//   {
//          if (window.confirm("�������벻��ȷ������Ƿ����?"))
//          {
//             tLlag=true;
//          }else{
//          	return ;
//          }
//   	}
//   	
//   	var tRiskCode=fm.RiskCode.value;
//   	var tSQL="select RiskCode from lmrisk where RiskCode='"+tRiskCode+"'";
//   	var tResult=easyExecSql(tSQL,1,0,1);
//   	if(tResult!='' || tResult!=null)
//   	{
//   		 tRlag=true;
//   		}
//   else{
//   		alert("���ֲ����ڣ�����������");   
//   		return;
//   			}
//   	
//   	var tInsuAccNo=fm.InsuAccNo.value;  	
//   	var tSQL_0="select InsuAccNo from lmrisktoacc where InsuAccNo='"+tInsuAccNo+"'";
//   	var tResult_0=easyExecSql(tSQL_0,1,0,1);
//   	if(tResult_0!='' || tResult_0!=null)
//   	{
//   		 tIlag=true;
//   		}
//   	else{
//   		 alert("�ʻ������ڣ�����������");  
//   		 return; 			
//   			}
//   		
//     if(tLlag==true&&tFlag==true&&tRlag==true&&tIlag==true)
//           {
//           var showStr="�����������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//           var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//           showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
//           fm.transact.value="insert"; 
//           fm.action="./AccRateSave.jsp";
//           document.getElementById("fm").submit(); //�ύ
//          }
//
//
//}           

//updateClick�¼�
function updateClick()
{	
		   var tFlag=fm.RiskCode.value;
	        if(tFlag==null || tFlag=='')
	      {
	         alert("��ѡ����Ҫ���˵ļ�¼��");
		       return;
		     }	        
           var showStr="�����޸����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
           var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//           showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;"); 
           var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
           var iWidth=550;      //�������ڵĿ��; 
           var iHeight=250;     //�������ڵĸ߶�; 
           var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
           var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
           showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

           showInfo.focus();

           fm.transact.value="update"; 
           fm.action="./AccRateCheckSave.jsp";
           document.getElementById("fm").submit(); //�ύ		
}           

//queryClick�¼�
function queryClick()
{
//    var tSql_0="select RISKCODE,(select riskshortname from lmrisk a where  a.riskcode=c.riskcode),INSUACCNO,"
//             +" BALADATE,RATE,RATEINTV,SRATEDATE,ARATEDATE,(select codename from ldcode b where b.code"
//             +" =ratestate and codetype='ratestate') from  LMInsuAccRate c  order by BALADATE desc";
    
    	var sqlid1="AccRateCheckInputSql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("sys.AccRateCheckInputSql");
	 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
	 	var tSql_0 = mySql1.getString();
    
//    var tSql_1="select RISKCODE,(select riskshortname from lmrisk a where  a.riskcode=c.riskcode),INSUACCNO,"
//             +" ' ',RATE,RATEINTV,RATESTARTDATE,RATEENDDATE,(select codename from ldcode b where b.code"
//             +" =ratestate and codetype='ratestate') from  LMAccGuratRate  c order by RATESTARTDATE desc";
    
	    var sqlid2="AccRateCheckInputSql2";
	 	var mySql2=new SqlClass();
	 	mySql2.setResourceName("sys.AccRateCheckInputSql");
	 	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
	 	var tSql_1 = mySql2.getString();
    
    if(fm.AccType.value==1)
    {
    	 turnPage.queryModal(tSql_0, LMInsuAccRateGrid);
    	}
    	else{
    	turnPage.queryModal(tSql_1, LMInsuAccRateGrid);
    		}             
         
}           

//deleteClick�¼�
//function deleteClick()
//{
//	var tFlag=fm.RiskCode.value;
//	if(tFlag==null || tFlag=='')
//	{
//		alert("��ѡ����Ҫɾ���ļ�¼��");
//		return;
//		}
//	else{
//		if(window.confirm("ɾ�����ɻָ����Ƿ����?")){
//		  var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
//      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//      showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
//		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
//		var iWidth=550;      //�������ڵĿ��; 
//		var iHeight=250;     //�������ڵĸ߶�; 
//		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
//		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
//		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
//		
//		showInfo.focus();

//      fm.transact.value="delete"; 
//      fm.action="./AccRateSave.jsp";
//      document.getElementById("fm").submit(); //�ύ
//     }
//     else{
//     	return ;
//     	}
//			}
//
//}           

function displayInfo()
{
	 var tFlag =fm.AccRate[1].value
	 var tLength=LMInsuAccRateGrid.mulLineCount;
	 var tCow=0;
	  if(fm.AccRate[0].checked==true) //���Խ���������Ϣ
	  {

	  tCow=LMInsuAccRateGrid.getSelNo()
	  tCow=tCow-1;  	
    fm.RiskCode.value =LMInsuAccRateGrid.getRowColData(tCow,1);
    fm.InsuAccNo.value=LMInsuAccRateGrid.getRowColData(tCow,3);
    fm.BalaDate.value =LMInsuAccRateGrid.getRowColData(tCow,4);
    fm.Rate.value = LMInsuAccRateGrid.getRowColData(tCow,5);
    fm.SRateDate.value = LMInsuAccRateGrid.getRowColData(tCow,7);
    fm.ARateDate.value =LMInsuAccRateGrid.getRowColData(tCow,8);
    }
   	if(fm.AccRate[1].checked==true) //���Ա�֤������Ϣ
   	{
	  tCow=LMInsuAccRateGrid.getSelNo()	
	  tCow=tCow-1; 
    fm.RiskCode.value =LMInsuAccRateGrid.getRowColData(tCow,1);
    fm.InsuAccNo.value=LMInsuAccRateGrid.getRowColData(tCow,3);
    fm.BalaDate.value =LMInsuAccRateGrid.getRowColData(tCow,4);
    fm.GruRate.value = LMInsuAccRateGrid.getRowColData(tCow,5);
    fm.RateStartDate.value = LMInsuAccRateGrid.getRowColData(tCow,7);
    fm.RateEndDate.value =LMInsuAccRateGrid.getRowColData(tCow,8);   		
   	  	}

	
	}
	

