var showInfo;
var mDebug="1";
var mEdorType;
var turnPage = new turnPageClass();
var turnPageCustomerContGrid = new turnPageClass();
var sqlresourcename = "bq.PEdorTypeBBInputSql";

//��ѯ��ɫ	 AppntIsInsuredFlag Ͷ���˻��Ǳ�����,0,Ͷ���ˣ�1�������ˣ�2 ���߼�֮
function initInpRole()
{
		var tContNo=document.all('ContNo').value;
	  //�ж����Ƿ���Ͷ����
//	  var sql = " select 1 from lcappnt where contno = '" + tContNo + "' and appntno ='"+document.all('CustomerNo').value+"'";
    
    var sql = "";
	var sqlid1="PEdorTypeBBInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(tContNo);//ָ������Ĳ���
	mySql1.addSubPara(document.all('CustomerNo').value);
	sql=mySql1.getString();
    
    var mrr = easyExecSql(sql);
    if ( mrr )
    {
        fm.AppntIsInsuredFlag.value="0";
    }
    //alert(mrr);
//    sql = " select socialinsuflag from lcinsured where contno = '" + tContNo + "' and insuredno ='"+document.all('CustomerNo').value+"'";
    
    //var sql = "";
	var sqlid2="PEdorTypeBBInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(tContNo);//ָ������Ĳ���
	mySql2.addSubPara(document.all('CustomerNo').value);
	sql=mySql2.getString();
    
    mrr = easyExecSql(sql);
    if ( mrr )
    {
        fm.AppntIsInsuredFlag.value="1";
        document.all("divSociaFlag").style.display = "";
        if(mrr[0][0]=='1')
        {
        	fm.PreSFlag.value='1';
        	}else
        		{
           	fm.PreSFlag.value='0';         	     			          	     			
           	     			}
    }
//    var tsql = " select socialinsuflag from lcinsured where contno = '" + tContNo + "' and insuredno = (select appntno from lccont where contno = '" + tContNo + "')";
    
    var tsql = "";
    var sqlid3="PEdorTypeBBInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(tContNo);//ָ������Ĳ���
	mySql3.addSubPara(tContNo);
	tsql=mySql3.getString();
    
     mrr = easyExecSql(tsql);
    if ( mrr )
    {
        fm.AppntIsInsuredFlag.value="2";
        document.all("divSociaFlag").style.display = "";
        if(mrr[0][0]=='1')
        {
        	fm.PreSFlag.value='1';
        	}else
        		{
           	fm.PreSFlag.value='0';        	     			          	     			
           	     			}
    }


}
function initInpCustomerInfo()
{	  
 	  var tContNo=document.all('ContNo').value;
    var CustomerInfo;
    var tSQL;
    //����Ǳ�����
    if(fm.AppntIsInsuredFlag.value=='1')
    {
//    	tSQL="select Name,NativePlace,RgtAddress,Marriage,SocialInsuFlag from lcinsured where insuredno='"+document.all('CustomerNo').value+"' and contno='"+fm.ContNo.value+"'";
      
    var sqlid4="PEdorTypeBBInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	mySql4.addSubPara(fm.ContNo.value);
	tSQL=mySql4.getString();
      
      CustomerInfo= easyExecSql(tSQL);
      if(CustomerInfo!=null)
      {
        DisPlyaCustomerInf(CustomerInfo);		
      }	
    }else
    	{
//    		tSQL="select appntName,NativePlace,RgtAddress,Marriage from lcappnt where appntno='"+document.all('CustomerNo').value+"'and contno='"+fm.ContNo.value+"'";
    		
    var sqlid5="PEdorTypeBBInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	mySql5.addSubPara(fm.ContNo.value);
	tSQL=mySql5.getString();
    		
    		CustomerInfo = easyExecSql(tSQL);
      if(CustomerInfo!=null)
      {
        DisPlyaCustomerInf(CustomerInfo);		
      }		
        //alert(CustomerInfo);		
    }
    
}
function initInpPCustomerInfo()
{

    var CustomerInfo;
    var tSQL;
	    //����Ǳ�������鱻����
    if(fm.AppntIsInsuredFlag.value=='1')
    {
//    	tSQL="select Name,NativePlace,RgtAddress,Marriage,SocialInsuFlag from lpinsured where insuredno='"+document.all('CustomerNo').value+"' and edorno='"+fm.EdorNo.value+"' and contno='"+fm.ContNo.value+"'";
      
    var sqlid6="PEdorTypeBBInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	mySql6.addSubPara(fm.EdorNo.value);
	mySql6.addSubPara(fm.ContNo.value);
	tSQL=mySql6.getString();
      
      CustomerInfo = easyExecSql(tSQL);
      if(CustomerInfo!=null)
      {
        DisPlyaPCustomerInf(CustomerInfo);			
    }else
    	{
//    		tSQL="select Name,NativePlace,RgtAddress,Marriage,SocialInsuFlag from lcinsured where insuredno='"+document.all('CustomerNo').value+"' and contno='"+fm.ContNo.value+"'";
    		
    var sqlid7="PEdorTypeBBInputSql7";
	var mySql7=new SqlClass();
	mySql7.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql7.setSqlId(sqlid7);//ָ��ʹ�õ�Sql��id
	mySql7.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	mySql7.addSubPara(fm.ContNo.value);
	tSQL=mySql7.getString();
    		
    		CustomerInfo = easyExecSql(tSQL);
    		if(CustomerInfo!=null)
    		{
        DisPlyaPCustomerInf(CustomerInfo);				
    		}					
    		}
    }else
    	{    		
//    		tSQL="select appntName,NativePlace,RgtAddress,Marriage from lpappnt where appntno='"+document.all('CustomerNo').value+"' and edorno='"+fm.EdorNo.value+"' and contno='"+fm.ContNo.value+"'";
    		
    var sqlid8="PEdorTypeBBInputSql8";
	var mySql8=new SqlClass();
	mySql8.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql8.setSqlId(sqlid8);//ָ��ʹ�õ�Sql��id
	mySql8.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	mySql8.addSubPara(fm.EdorNo.value);
	mySql8.addSubPara(fm.ContNo.value);
	tSQL=mySql8.getString();
    		
    		CustomerInfo = easyExecSql(tSQL);
    		if(CustomerInfo!=null)
    		{
        DisPlyaPCustomerInf(CustomerInfo);				
    		}else
    			{
//    		tSQL="select appntName,NativePlace,RgtAddress,Marriage from lcappnt where appntno='"+document.all('CustomerNo').value+"' and contno='"+fm.ContNo.value+"'";
    		
    var sqlid9="PEdorTypeBBInputSql9";
	var mySql9=new SqlClass();
	mySql9.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql9.setSqlId(sqlid9);//ָ��ʹ�õ�Sql��id
	mySql9.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	mySql9.addSubPara(fm.ContNo.value);
	tSQL=mySql9.getString();
    		
    		CustomerInfo = easyExecSql(tSQL);
    		if(CustomerInfo!=null)
    		{
        DisPlyaPCustomerInf(CustomerInfo);				
    		}		
    	}
    	}
 	}
 	
function DisPlyaCustomerInf(CustomerInfo)
{
      CustomerInfo[0][0]==null||CustomerInfo[0][0]=='null'?'0':fm.Name.value = CustomerInfo[0][0];	
      CustomerInfo[0][1]==null||CustomerInfo[0][1]=='null'?'0':fm.NativePlace.value = CustomerInfo[0][1];	
      CustomerInfo[0][2]==null||CustomerInfo[0][2]=='null'?'0':fm.AppntRgtAddress.value = CustomerInfo[0][2];	
      CustomerInfo[0][3]==null||CustomerInfo[0][3]=='null'?'0':fm.AppntMarriage.value = CustomerInfo[0][3];	 
      CustomerInfo[0][4]==null||CustomerInfo[0][4]=='null'?'0':fm.PreSFlag.value = CustomerInfo[0][4];	 
      CustomerInfo[0][5]==null||CustomerInfo[0][5]=='null'?'0':fm.OldLanguage.value = CustomerInfo[0][5]; 	             	
      showOneCodeName('nativeplace','NativePlace','NativePlaceName');         
      showOneCodeName('marriage','AppntMarriage','AppntMarriageName');      
      showOneCodeName('language','OldLanguage','OldLanguageName');  		
	} 	
	
	function DisPlyaPCustomerInf(CustomerInfo)
{
      CustomerInfo[0][0]==null||CustomerInfo[0][0]=='null'?'0':fm.Name_New.value = CustomerInfo[0][0];	
      CustomerInfo[0][1]==null||CustomerInfo[0][1]=='null'?'0':fm.NativePlace_New.value = CustomerInfo[0][1];	
      CustomerInfo[0][2]==null||CustomerInfo[0][2]=='null'?'0':fm.AppntRgtAddress_New.value = CustomerInfo[0][2];	
      CustomerInfo[0][3]==null||CustomerInfo[0][3]=='null'?'0':fm.AppntMarriage_New.value = CustomerInfo[0][3];
      CustomerInfo[0][4]==null||CustomerInfo[0][4]=='null'?'0':fm.NewSFlag.value = CustomerInfo[0][4];	  
      CustomerInfo[0][5]==null||CustomerInfo[0][5]=='null'?'0':fm.NewLanguage.value = CustomerInfo[0][5];	 	    
      //alert(fm.NewSFlag.value);
      if(fm.NewSFlag.value=='1')
      {
      	 fm.SociaFlag.checked=true;
      	}     		
      showOneCodeName('nativeplace','NativePlace_New','NativePlaceName_New');         
      showOneCodeName('marriage','AppntMarriage_New','AppntMarriageName_New');  	
      showOneCodeName('language','NewLanguage','NewLanguageName');  	
	} 	
 	
/**
 * �ύ�����, ���������ݷ��غ�ִ�еĲ���
 */
function afterSubmit(DealFlag, MsgContent)
{
    try { showInfo.close(); } catch (ex) {}
    DealFlag = DealFlag.toLowerCase();
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=";
    switch (DealFlag)
    {
        case "fail":
            MsgPageURL = MsgPageURL + "F&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=250px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        case "succ":
        case "success":
            MsgPageURL = MsgPageURL + "S&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=350px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=350;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
        default:
            MsgPageURL = MsgPageURL + "C&content=" + MsgContent;
            //showInfo = showModalDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
			var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=300;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
            break;
    }
}

/**
 * ����������
 */
function returnParent()
{
    try
    {
        top.close();
        top.opener.focus();
        top.opener.getEdorItemGrid();
    }
    catch (ex) {}
}


function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = '0000000003';
    var OtherNo = top.opener.document.all("OtherNo").value;;

    var OtherNoType = "1";
    if(MissionID == null || MissionID == "")
    {
        alert("MissionIDΪ�գ�");
        return;
    }
    var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
    var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface= ../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
}

function edorTypeBBSave()
{
	    //����У��
    if (!verifyInput2())
    {
        return false;
    }
   if(fm.SociaFlag.checked)
   {
   	fm.NewSFlag.value='1'
   	}else
   		{
   			fm.NewSFlag.value='0'
   			}
    
   if ((fm.NativePlace.value == fm.NativePlace_New.value) 
   && (fm.AppntRgtAddress.value == fm.AppntRgtAddress_New.value)
   && (fm.AppntMarriage.value == fm.AppntMarriage_New.value)
   && (fm.OldLanguage.value == fm.NewLanguage.value)
   && (fm.NewSFlag.value == fm.PreSFlag.value))
    {
        alert('����������δ���������');
        return false;
    }
    
          var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
          var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
          //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
		  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=250;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
          fm.action = "PEdorTypeBBSubmit.jsp";
          fm.submit();
}          