//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�
//�������ƣ�
//�����ܣ����������������
//�������ڣ�2007-11-09 17:55:57
//������  ������ͥ
var showInfo;
var mDebug="0";
var turnPage = new turnPageClass(); 
var AccFlag;

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
function addClick()
{

	var tClag=fm.RiskCode.value;
	if(tClag==null || tClag=='')
	{
		alert("���ݲ�����������������");
		return;
		}
   var tFlag=false;
   var tLlag=false;   
   var tRlag=false;
   var tIlag=false;
   var tBalaDate=fm.BalaDate.value;

   if(fm.AccType.value=='0')    //ֻ�н����ʻ��Ľ����ղ���У��
   {
   	            tFlag=true;
   	}else
   		{
   tBalaDate=tBalaDate.substring(8,tBalaDate.length);
   if('01'!=tBalaDate)
   {
   	       if (window.confirm("�����ղ���ȷ���Ƿ����?"))
          {
            tFlag=true;
          }else{
          	return ;
          }
   	}else{
   		     tFlag=true;
   		}
   	}
    
    if(fm.AccType.value=='0')    //�������ʺͱ�֤������ǰ̨��������һ��
   {
      var tGruRate=fm.GruRate.value; 
    if(tGruRate>0.05 || tGruRate<0.0 || tGruRate=='' || tGruRate==null)
    {
          if (window.confirm("�������벻��ȷ������Ƿ����?"))
          {
             tLlag=true;
          }else{
          	return ;
          }
   	}else{
   		     tLlag=true;
   		}
   	}
   	else
   	{     
   var tRate=fm.Rate.value;
   if(tRate>0.05 || tRate<0.0 || tRate=='' || tRate==null)
   {
          if (window.confirm("�������벻��ȷ������Ƿ����?"))
          {
             tLlag=true;
          }else{
          	return ;
          }
   	}else{
   		     tLlag=true;
   		}
   	}
   	
   	var tRiskCode=fm.RiskCode.value;
//   	var tSQL="select RiskCode from lmrisk where RiskCode='"+tRiskCode+"'";
   	
   	    var sqlid1="AccRateInputSql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("sys.AccRateInputSql");
	 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
	 	mySql1.addSubPara(tRiskCode);//ָ���������
	 	var tSQL = mySql1.getString();
   	
   	var tResult=easyExecSql(tSQL,1,0,1);
   	if(tResult!='' || tResult!=null)
   	{
   		 tRlag=true;
   		}
   else{
   		alert("���ֲ����ڣ�����������");   
   		return;
   			}	  	
   	var tInsuAccNo=fm.InsuAccNo.value;  	
//   	var tSQL_0="select InsuAccNo from lmrisktoacc where InsuAccNo='"+tInsuAccNo+"' and RiskCode='"+tRiskCode+"'";
   	
   	var sqlid2="AccRateInputSql2";
 	var mySql2=new SqlClass();
 	mySql2.setResourceName("sys.AccRateInputSql");
 	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
 	mySql2.addSubPara(tInsuAccNo);//ָ���������
 	mySql2.addSubPara(tRiskCode);
 	var tSQL_0 = mySql2.getString();
   	
   	var tResult_0=easyExecSql(tSQL_0,1,0,1);
   	if(tInsuAccNo==tResult_0)
   	{
   		 tIlag=true;
   		}
   	else{
   		 alert("�ʻ������ڣ�����������");  
   		 return; 			
   			}
   
     if(tLlag==true&&tFlag==true&&tRlag==true&&tIlag==true)
           {
           var showStr="�����������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
           var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//           showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
           var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
           var iWidth=550;      //�������ڵĿ��; 
           var iHeight=250;     //�������ڵĸ߶�; 
           var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
           var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
           showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

           showInfo.focus();

           fm.transact.value="insert"; 
           fm.action="./AccRateSave.jsp";
           document.getElementById("fm").submit(); //�ύ
          }


}           

//updateClick�¼�
function updateClick()
{
	
	var tClag=fm.RiskCode.value;
	if(tClag==null || tClag=='')
	{
		alert("��ѡ����Ҫ�޸ĵļ�¼��");
		return;
		}
		
	 var tFlag=false;
   var tLlag=false;   
   var tRlag=false;
   var tIlag=false;
   var tBalaDate=fm.BalaDate.value;

   tBalaDate=tBalaDate.substring(8,tBalaDate.length);
   if('01'!=tBalaDate)
   {
   	       if (window.confirm("�����ղ���ȷ���Ƿ����?"))
          {
            tFlag=true;
          }else{
          	return ;
          }
   	}else{
            tFlag=true;   		
   		}
   
   var tRate=fm.Rate.value;
   if(tRate>0.05 || tRate<0.0)
   {
          if (window.confirm("���ʹ����Ƿ����?"))
          {
             tLlag=true;
          }else{
          	return ;
          }
   	}else{
             tLlag=true;   		
   		}
   	
   	var tRiskCode=fm.RiskCode.value;
//   	var tSQL="select RiskCode from lmrisk where RiskCode='"+tRiskCode+"'";
   	
   	var sqlid3="AccRateInputSql3";
 	var mySql3=new SqlClass();
 	mySql3.setResourceName("sys.AccRateInputSql");
 	mySql3.setSqlId(sqlid3); //ָ��ʹ��SQL��id
 	mySql3.addSubPara(tRiskCode);//ָ���������
 	var tSQL = mySql3.getString();
   	
   	var tResult=easyExecSql(tSQL,1,0,1);
   	if(tResult!='' || tResult!=null)
   	{
   		 tRlag=true;
   		}
   else{
   		alert("���ֲ����ڣ�����������");   
   		return;
   			}
   	
   	var tInsuAccNo=fm.InsuAccNo.value;  	
//   	var tSQL_0="select InsuAccNo from lmrisktoacc where InsuAccNo='"+tInsuAccNo+"' and RiskCode='"+tRiskCode+"'";
   	
   	var sqlid4="AccRateInputSql4";
 	var mySql4=new SqlClass();
 	mySql4.setResourceName("sys.AccRateInputSql");
 	mySql4.setSqlId(sqlid4); //ָ��ʹ��SQL��id
 	mySql4.addSubPara(tInsuAccNo);//ָ���������
 	mySql4.addSubPara(tRiskCode);
 	var tSQL_0 = mySql4.getString();
   	
   	var tResult_0=easyExecSql(tSQL_0,1,0,1);
   	if(tInsuAccNo==tResult_0)
   	{
   		 tIlag=true;
   		}
   	else{
   		 alert("�ʻ������ڣ�����������");
   		 return;   			
   			}
   		
     if(tLlag==true&&tFlag==true&&tRlag==true&&tIlag==true)
           {
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
           fm.action="./AccRateSave.jsp";
           document.getElementById("fm").submit(); //�ύ
          }

	
	
}           

//queryClick�¼�
function queryClick()
{
//    var tSql_0="select RISKCODE,(select riskshortname from lmrisk a where  a.riskcode=c.riskcode),INSUACCNO,"
//             +" BALADATE,RATE,RATEINTV,SRATEDATE,ARATEDATE,(select codename from ldcode b where b.code"
//             +" =ratestate and codetype='ratestate') from  LMInsuAccRate c  order by BALADATE desc";
    
    var sqlid5="AccRateInputSql5";
 	var mySql5=new SqlClass();
 	mySql5.setResourceName("sys.AccRateInputSql");
 	mySql5.setSqlId(sqlid5); //ָ��ʹ��SQL��id
 	var tSql_0 = mySql5.getString();
    
//    var tSql_1="select RISKCODE,(select riskshortname from lmrisk a where  a.riskcode=c.riskcode),INSUACCNO,"
//             +" ' ',RATE,RATEINTV,RATESTARTDATE,RATEENDDATE,(select codename from ldcode b where b.code"
//             +" =ratestate and codetype='ratestate') from  LMAccGuratRate  c order by RATESTARTDATE desc";  
    
    var sqlid6="AccRateInputSql6";
 	var mySql6=new SqlClass();
 	mySql6.setResourceName("sys.AccRateInputSql");
 	mySql6.setSqlId(sqlid6); //ָ��ʹ��SQL��id
 	var tSql_1 = mySql6.getString();
    
    if(fm.AccType.value==1)
    {
    	 turnPage.queryModal(tSql_0, LMInsuAccRateGrid);
    	}
    	else{
    	turnPage.queryModal(tSql_1, LMInsuAccRateGrid);
    		}             
         
}           

//deleteClick�¼�
function deleteClick()
{
	var tFlag=fm.RiskCode.value;
	if(tFlag==null || tFlag=='')
	{
		alert("��ѡ����Ҫɾ���ļ�¼��");
		return;
		}
	else{
		if(window.confirm("ɾ�����ɻָ����Ƿ����?")){
		  var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
      var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//      showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
      var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
      var iWidth=550;      //�������ڵĿ��; 
      var iHeight=250;     //�������ڵĸ߶�; 
      var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
      var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
      showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

      showInfo.focus();

      fm.transact.value="delete"; 
      fm.action="./AccRateSave.jsp";
      document.getElementById("fm").submit(); //�ύ
     }
     else{
     	return ;
     	}
			}

}           

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
	

