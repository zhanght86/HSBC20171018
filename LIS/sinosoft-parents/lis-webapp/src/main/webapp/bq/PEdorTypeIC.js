//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var mFlag;
var sqlresourcename = "bq.PEdorTypeICInputSql";

//���ļ��а����ͻ�����Ҫ����ĺ������¼�
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���



function edorTypeReturn()
{
	initForm();
}

function edorTypeICSave()
{

	document.all('fmtransact').value = "INSERT||MAIN";


    var MsgContent = "���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var MsgPageURL = "../common/jsp/MessagePage.jsp?picture=C&content=" + MsgContent;
    //showInfo = showModelessDialog(MsgPageURL, window, "status=0; help=0; close=0; dialogWidth=550px; dialogHeight=300px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
			var iWidth=550;      //�������ڵĿ��; 
			var iHeight=300;     //�������ڵĸ߶�; 
			var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
			var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
			showInfo = window.open (MsgPageURL,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

			showInfo.focus();
	  fm.action = "PEdorTypeICSubmit.jsp";
	  fm.submit();
 
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content,Result )
{
	showInfo.close();
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	if (FlagStr == "Success" )
	{
		var tTransact=document.all('fmtransact').value;

		if (tTransact=="INSERT||MAIN")
		{
            queryRelationPol();
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
			edorTypeReturn();

	  	}
	  	//XinYQ added on 2005-12-22
	  	try
	  	{
	  	    queryBackFee();              //�ύ֮���ٴβ�ѯ���˷���ϸ
	  	    top.opener.getEdorItemGrid();
	  	}
	  	catch (ex) {}
	  	  var tFlag=""//�����Ƿ��������ǣ����Ҳ�����Ͷ������
  //      var tSQL="select standbyflag3 from lpedoritem where EdorAcceptNo ='" + document.all('EdorAcceptNo').value + "'";
    		
    var tSQL = "";
	var sqlid1="PEdorTypeICInputSql1";
	var mySql1=new SqlClass();
	mySql1.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	mySql1.addSubPara(document.all('EdorAcceptNo').value);//ָ������Ĳ���
	tSQL=mySql1.getString();
    		
    		tFlag = easyExecSql(tSQL);
    		if(tFlag=="1")
    		{
          alert("����������Ͷ�����䲻�ڳб���Χ�ڣ�������Ͷ���������꣬��ע�⴦��");	
          return ;
    		}	
	}
}

//�ύǰ��У�顢����
function beforeSubmit()
{
  //��Ӳ���
}

//---------------------------
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
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else
		parent.fraMain.rows = "0,0,0,72,*";
}


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
	var ActivityID = "0000000003";
	var OtherNo = top.opener.document.all("OtherNo").value;;

	var OtherNoType = "1";
	if(MissionID == null || MissionID == "")
	{
		alert("MissionIDΪ�գ�");
		return;
	}
	var varSrc= "&MissionID="+ MissionID + "&SubMissionID="+ SubMissionID + "&ActivityID="+ ActivityID + "&PrtNo="+ OtherNo + "&NoType="+ OtherNoType;
	var newWindow = OpenWindowNew("../uw/WorkFlowNotePadFrame.jsp?Interface=../uw/WorkFlowNotePadInput.jsp" + varSrc,"���������±��鿴","left");
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


function initInpPCustomerInfo()
{	  
    var CustomerInfo;
//    var tSQL="select name,sex,birthday,idtype,idNo from LPPerson where CustomerNo ='" + document.all('CustomerNo').value + "' and EdorNo='"+getLastEdorNo()+"'";
      
    var tSQL = "";
	var sqlid2="PEdorTypeICInputSql2";
	var mySql2=new SqlClass();
	mySql2.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
	mySql2.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	mySql2.addSubPara(getLastEdorNo());
	tSQL=mySql2.getString();
      
      CustomerInfo= easyExecSql(tSQL);
      if(CustomerInfo!=null)
      {
        DisPlyaPCustomerInf(CustomerInfo);		
      }	
    
}
function initInpCustomerInfo()
{
    
    var CustomerInfo=null;
//    var tSQL="select name,sex,birthday,idtype,idNo from LDPerson where CustomerNo ='" + document.all('CustomerNo').value + "'";
    		
    var tSQL = "";
	var sqlid3="PEdorTypeICInputSql3";
	var mySql3=new SqlClass();
	mySql3.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
	mySql3.addSubPara(document.all('CustomerNo').value);//ָ������Ĳ���
	tSQL=mySql3.getString();
    		
    		CustomerInfo = easyExecSql(tSQL);
    		if(CustomerInfo!=null)
    		{
        DisPlyaCustomerInf(CustomerInfo);				
    		}	
}	


function DisPlyaPCustomerInf(CustomerInfo)
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
	
	function DisPlyaCustomerInf(CustomerInfo)
{
      CustomerInfo[0][0]==null||CustomerInfo[0][0]=='null'?'0':fm.NameBak.value = CustomerInfo[0][0];	
      CustomerInfo[0][1]==null||CustomerInfo[0][1]=='null'?'0':fm.SexBak.value = CustomerInfo[0][1];	
      CustomerInfo[0][2]==null||CustomerInfo[0][2]=='null'?'0':fm.BirthdayBak.value = CustomerInfo[0][2];	
      CustomerInfo[0][3]==null||CustomerInfo[0][3]=='null'?'0':fm.IDTypeBak.value = CustomerInfo[0][3];	                    
      CustomerInfo[0][4]==null||CustomerInfo[0][4]=='null'?'0':fm.IDNoBak.value = CustomerInfo[0][4];	                                         
      CustomerInfo[0][5]==null||CustomerInfo[0][5]=='null'?'0':fm.LastNameBak.value = CustomerInfo[0][5];
      CustomerInfo[0][6]==null||CustomerInfo[0][6]=='null'?'0':fm.FirstNameBak.value = CustomerInfo[0][6];	
      showOneCodeName('sex','SexBak','SexNameBak');         
      showOneCodeName('idtype','IDTypeBak','IDTypeNameBak');  
}

function getLastEdorNo()
{
	    var tEdorNo;
//		  var strSQL =  " select edorno from lpconttempinfo where edortype='CM' and state='0' and contno='"+document.all('ContNo').value+"'";
			
	var strSQL = "";
	var sqlid4="PEdorTypeICInputSql4";
	var mySql4=new SqlClass();
	mySql4.setResourceName(sqlresourcename); //ָ��ʹ�õ�properties�ļ���
	mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
	mySql4.addSubPara(document.all('ContNo').value);//ָ������Ĳ���
	strSQL=mySql4.getString();
			
			var brr = easyExecSql(strSQL);
			if ( brr )
			{
				//alert("�Ѿ����뱣���");
				brr[0][0]==null||brr[0][0]=='null'?'0':tEdorNo= brr[0][0];
				fm.CMEdorNo.value=tEdorNo;
        return tEdorNo;
			}
			else
			{
				alert("��ȫ������ʧ��");
				return "";
			}
	}