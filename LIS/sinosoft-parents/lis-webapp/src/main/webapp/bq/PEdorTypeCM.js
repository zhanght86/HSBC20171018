//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var mFlag;
var sqlresourcename = "bq.PEdorTypeCMInputSql";
//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

function verifyPage()
{
    //��������Ա�У�����֤��
    if (document.all("IDTypeBak").value == "0")
    {
    var strChkIdNo = chkIdNo(document.all("IDNoBak").value, document.all("BirthdayBak").value, document.all("SexBak").value);

    if (strChkIdNo!="" && strChkIdNo!=null)
    {
        if (!confirm(strChkIdNo + "���Ƿ�������棿 "))
        {
            return false;
        }
    }
   }

    return true;
}


function edorTypeCMSave()
{
    if (!verifyPage()) return;
    if (!verifyInput2()) return;

    if (fm.LastName.value == fm.LastNameBak.value && fm.FirstName.value == fm.FirstNameBak.value && fm.Sex.value == fm.SexBak.value &&
        fm.Birthday.value == fm.BirthdayBak.value && fm.IDType.value == fm.IDTypeBak.value &&
        fm.IDNo.value == fm.IDNoBak.value)
    {
        if (!confirm("�ͻ�����δ����������Ƿ�������棿 "))
        {
            return false;
        }
    }
    fm.NameBak.value = fm.LastNameBak.value + fm.FirstNameBak.value;

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
    fm.submit();
}



//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit(FlagStr, content)
{
    showInfo.close();
    var urlStr;
    if (FlagStr == "Success")
    {
        urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else if (FlagStr == "Fail")
    {
        urlStr="../common/jsp/MessagePage.jsp?picture=F&content=" + content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
    else
    {
        urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content;
        //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=300;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
    }
         queryRelationCont(); 
}




function returnParent()
{

 top.close();
 top.opener.focus();
 top.opener.getEdorItemGrid();
}


function showNotePad()
{
    var MissionID = top.opener.document.all("MissionID").value;
    var SubMissionID = top.opener.document.all("SubMissionID").value;;
    var ActivityID = "0000000003";
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

function Edorquery()
{
    var ButtonFlag = top.opener.document.all('ButtonFlag').value;
    if(ButtonFlag!=null && ButtonFlag=="1")
    {
       divEdorquery.style.display = "none";
    }
    else
    {
        divEdorquery.style.display = "";
    }
}

function initInpCustomerInfo()
{	  
    var CustomerInfo;
//    var tSQL="select name,sex,birthday,idtype,idNo from LDPerson where CustomerNo ='" + document.all('CustomerNo').value + "'";
    var tSQL = "";
	var sqlid1="PEdorTypeCMInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	tSQL=mySql1.getString();
      CustomerInfo= easyExecSql(tSQL);
      if(CustomerInfo!=null)
      {
        DisPlyaCustomerInf(CustomerInfo);		
      }	

    
}
function initInpPCustomerInfo()
{

    var CustomerInfo;
//    var tSQL="select name,sex,birthday,idtype,idNo from LPPerson where CustomerNo ='" + document.all('CustomerNo').value + "' and EdorNo  = '" + fm.EdorNo.value + "' and EdorType = 'CM'";
    		
    var tSQL = "";
	var sqlid2="PEdorTypeCMInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	mySql2.addSubPara(fm.EdorNo.value);
	tSQL=mySql2.getString();
    		
    		CustomerInfo = easyExecSql(tSQL);
    		if(CustomerInfo!=null)
    		{
        DisPlyaPCustomerInf(CustomerInfo);				
    		}else
    			{
//    		tSQL="select name,sex,birthday,idtype,idNo from LDPerson where CustomerNo ='" + document.all('CustomerNo').value + "'";
    		
	var sqlid3="PEdorTypeCMInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	tSQL=mySql3.getString();
    		
    		CustomerInfo = easyExecSql(tSQL);
    		if(CustomerInfo!=null)
    		{
        DisPlyaPCustomerInf(CustomerInfo);				
    		}		
    	}
 	}
 	
function DisPlyaCustomerInf(CustomerInfo)
{
      CustomerInfo[0][0]==null||CustomerInfo[0][0]=='null'?'0':fm.Name.value = CustomerInfo[0][0];	
      CustomerInfo[0][1]==null||CustomerInfo[0][1]=='null'?'0':fm.Sex.value = CustomerInfo[0][1];	
      CustomerInfo[0][2]==null||CustomerInfo[0][2]=='null'?'0':fm.Birthday.value = CustomerInfo[0][2];	
      CustomerInfo[0][3]==null||CustomerInfo[0][3]=='null'?'0':fm.IDType.value = CustomerInfo[0][3];	
      CustomerInfo[0][4]==null||CustomerInfo[0][4]=='null'?'0':fm.IDNo.value = CustomerInfo[0][4];
      CustomerInfo[0][5]==null||CustomerInfo[0][5]=='null'?'0':fm.LastName.value = CustomerInfo[0][5];
      CustomerInfo[0][6]==null||CustomerInfo[0][6]=='null'?'0':fm.FirstName.value = CustomerInfo[0][6];	                                        
      	
      showOneCodeName('sex','Sex','SexName');         
      showOneCodeName('idtype','IDType','IDTypeName');  		
	} 	
	
	function DisPlyaPCustomerInf(CustomerInfo)
{
      CustomerInfo[0][0]==null||CustomerInfo[0][0]=='null'?'0':fm.NameBak.value = CustomerInfo[0][0];	
      CustomerInfo[0][1]==null||CustomerInfo[0][1]=='null'?'0':fm.SexBak.value = CustomerInfo[0][1];	
      CustomerInfo[0][2]==null||CustomerInfo[0][2]=='null'?'0':fm.BirthdayBak.value = CustomerInfo[0][2];	
      CustomerInfo[0][3]==null||CustomerInfo[0][3]=='null'?'0':fm.IDTypeBak.value = CustomerInfo[0][3];	                    
      CustomerInfo[0][4]==null||CustomerInfo[0][4]=='null'?'0':fm.IDNoBak.value = CustomerInfo[0][4];	  
      CustomerInfo[0][5]==null||CustomerInfo[0][5]=='null'?'0':fm.LastNameBak.value = CustomerInfo[0][5];
      CustomerInfo[0][6]==null||CustomerInfo[0][6]=='null'?'0':fm.FirstNameBak.value = CustomerInfo[0][6];	                                          
      showOneCodeName('sex','SexBak','SexBakName');         
      showOneCodeName('idtype','IDTypeBak','IDTypeBakName');  
	}
	

function queryRelationCont()
{
    var iArray;
/*    var strSQL = "select distinct a.ContNo, a.insuredno, a.CValidate, " +
                "a.Prem, a.Amnt " +       
                "from lccont a where a.grpcontno='00000000000000000000' and (insuredno='" +document.all('CustomerNo').value+"' or appntno='" +document.all('CustomerNo').value+"') and a.appflag='1' and Customgetpoldate is not null";
*/   
    var strSQL = "";
    var sqlid4="PEdorTypeCMInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	mySql4.addSubPara(document.all('CustomerNo').value);
	strSQL=mySql4.getString();
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    var brrResult = easyExecSql(strSQL);
//    var sql1="select distinct a.ContNo from lccont a where a.grpcontno='00000000000000000000' and (insuredno='"+document.all('CustomerNo').value+"' or appntno='"+document.all('CustomerNo').value+"') and a.appflag='1' and Customgetpoldate is not null";
    var sql1 = "";
    var sqlid5="PEdorTypeCMInputSql5";
	var mySql5=new SqlClass();
	mySql5.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
	mySql5.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	mySql5.addSubPara(document.all('CustomerNo').value);
    var brr1=easyExecSql(mySql5.getString());
    var contNo=brr1;
    var num = contNo.length;
    var ss="";
    for(var i=0;i<num;i++){
    	if(i>0){
    		ss = ss + ",";
    	}
    	ss=ss+"'"+contNo[i][0]+"'";
    }
//    var sql="select currency from lcpol where contno in ("+ss+") and polno=mainpolno";
    var sql = "";
    var sqlid6="PEdorTypeCMInputSql6";
	var mySql6=new SqlClass();
	mySql6.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
	mySql6.addSubPara(ss);//ָ������Ĳ���
	sql=mySql6.getString();
    var brr=easyExecSql(sql);
    if (brrResult)
    {
    	displayMultiline(brrResult,ContNewGrid);
	    ContNewGrid.setRowColData(0, 6, brr[0][0],ContNewGrid);
	   
    }else
    {
    	alert("��ѯ��ؿͻ���ر���ʧ�ܻ��߱��ͻ���û�б������볷��������������ѡ��ͻ���");
    	top.close();
    	return false;
    }

}	
	 
	 
function edorTypeReturn()
{
	initForm();
	}	 	