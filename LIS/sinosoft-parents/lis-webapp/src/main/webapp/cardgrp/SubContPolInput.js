//               ���ļ��а����ͻ�����Ҫ�����ĺ������¼�
var showInfo;
var arrResult;
var mDebug = "0";
var mOperate = "";
var mAction = "";
var mSwitch = parent.VD.gVSwitch;
 var turnPage = new turnPageClass();   
 var cflag = "5";
/*********************************************************************
 *  ���漯��Ͷ�������ύ
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function submitForm()
{
	var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  
	if( mAction == "")
		return;

	if((fm.all("CustomerNo").value == null || fm.all("CustomerNo").value == "") && ((fm.all("GrpName").value == null || fm.all("GrpName").value == "") || (fm.all("GrpAddress").value == null || fm.all("GrpAddress").value == "")))
	{
		alert("��Ҫ��Ϣ����ȫ��");
		return;
	}
	//showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ���; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.submit(); //�ύ
}

function afterQuery4(arrReturn)
{
	
	 //alert(arrReturn);
	 fm.CustomerNo.value=arrReturn[0][0];
	 fm.GrpName.value=arrReturn[0][1];
	 fm.GrpNature.value=arrReturn[0][3];
	 fm.BusinessType.value=arrReturn[0][4];
	 fm.Peoples.value=arrReturn[0][5];
	 fm.Fax.value=arrReturn[0][6];
	 //fm.Phone.value=arrReturn[0][7];
	 //fm.FoundDate.value=arrReturn[0][8];
	 //fm.E_Mail1.value=arrReturn[0][8];
}

function afterQuery(arrReturn)
{
	
	 //alert(arrReturn);
	 fm.CustomerNo.value=arrReturn[0][0];
	 fm.GrpName.value=arrReturn[0][1];
	 fm.GrpNature.value=arrReturn[0][3];
	 fm.BusinessType.value=arrReturn[0][4];
	 fm.Peoples.value=arrReturn[0][5];
	 //fm.Fax.value=arrReturn[0][6];
	 //fm.Phone.value=arrReturn[0][7];
	 //fm.FoundDate.value=arrReturn[0][8];
	 //fm.E_Mail1.value=arrReturn[0][9];
}
function appendOne()
{
	mAction = "INSERT";
	fm.all( 'fmAction' ).value = mAction;
	if( verifyInput2() == false ) return false;
	submitForm();
}

function updateOne()
{
	mAction = "UPDATE";
	fm.all( 'fmAction' ).value = mAction;
	if( verifyInput2() == false ) return false;
	submitForm();
}

function deleteOne()
{
	mAction = "DELETE";
	fm.all( 'fmAction' ).value = mAction;
	submitForm();
	fm.all("ExecuteCom").value = "";
	resetGrpCustomerForm()
}

/*********************************************************************
 *  �������Ͷ�������ύ��Ĳ���,���������ݷ��غ�ִ�еĲ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterSubmit( FlagStr, content, cCustomerNo)
{
	showInfo.close();
	if( FlagStr == "Fail" )
	{             
		var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ���; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
	}
	else
	{ 
		content = "����ɹ���";
		var urlStr = "../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
		//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
		var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ���; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();
		fm.all("addbutton").style.display = "none";
		//fm.all("modibutton").style.display = "none";
		fm.all("delbutton").style.display = "none";
		if (cCustomerNo != null && cCustomerNo != "")
		{
			fm.all("CustomerNo").value = cCustomerNo;
		}
	}
	mAction = ""; 
	queryGeneralInfo();
}


/*********************************************************************
 *  ѡ����������Ķ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field ) {
	try	
	{
		if( cCodeName == "comcode") 
		{
			queryGrpCustomerInfo(fm.all("ExecuteCom").value,"");
			queryGeneralInfo();
		}
	}
	catch( ex ) {
	}
}


/*********************************************************************
 *  ��ʾdiv
 *  ����  ��  ��һ������Ϊһ��div�����ã��ڶ�������Ϊ�Ƿ���ʾ�����Ϊ"true"����ʾ��������ʾ
 *  ����ֵ��  ��
 *********************************************************************
 */
function showDiv(cDiv,cShow)
{
	if( cShow == "true" )
		cDiv.style.display = "";
	else
		cDiv.style.display = "none";  
}


/*********************************************************************
 *  ��ʾ�ֵ�Ͷ����λ����
 *  ����  ��  
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryGrpCustomerInfo(cExecuteCom,cCustomerNo)
{
    
	var cGrpContNo = fm.all("GrpContNo").value;
 	arrResult = easyExecSql("select a.*,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.GetFlag,b.BankCode,b.BankAccNo,b.Remark from LCGrpAddress a,LDGrp b,LCGeneral c where  c.CustomerNo = b.CustomerNo and  c.CustomerNo = a.CustomerNo and  c.AddressNo = a.AddressNo and c.GrpContNo='" + cGrpContNo + "' and c.customerno = '"+cCustomerNo+"'", 1, 0);			
	if (arrResult == null)
	{
		
		resetGrpCustomerForm();
		if(LoadFlag=="4"||LoadFlag=="16")
		{
		 fm.all("addbutton").style.display = "none";
		}
		else{	
		fm.all("addbutton").style.display = "";
	        }
		//fm.all("modibutton").style.display = "none";
		fm.all("delbutton").style.display = "none";
	}
	else
	{
		displayGrpCustomerInfo();
		fm.all("addbutton").style.display = "none";
		//fm.all("modibutton").style.display = "";
		fm.all("delbutton").style.display = "";
	}
}

/*********************************************************************
 *  ��ʾ�ֵ�Ͷ����λ����
 *  ����  ��  
 *  ����ֵ��  ��
 *********************************************************************
 */
function queryGeneralInfo()
{
	var cGrpContNo = fm.all("GrpContNo").value;
		
	strSQL = "select a.ExecuteCom, a.ManageCom, b.GrpName, c.GrpAddress, a.Operator ,a.customerno from LCGeneral a, LDGrp b, LCGrpAddress c where a.CustomerNo = b.CustomerNo and a.CustomerNo = c.CustomerNo and a.AddressNo = c.AddressNo and a.GrpContNo = '" + cGrpContNo + "'";
		
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

    if (!turnPage.strQueryResult) {
		return;
    }

	//��ѯ�ɹ������ַ��������ض�ά����
	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	turnPage.pageDisplayGrid = GeneralGrid;
	//����SQL���
	turnPage.strQuerySql = strSQL;
	//���ò�ѯ��ʼλ��
	turnPage.pageIndex = 0;
	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	//����MULTILINE������ʾ��ѯ���
	displayMultiline(turnPage.arrDataCacheSet, turnPage.pageDisplayGrid);
}

function onGeneralSelected(parm1,parm2)
{
	var cExecuteCom = GeneralGrid.getRowColData(GeneralGrid.getSelNo() - 1, 1);
	var cCustomerNo = GeneralGrid.getRowColData(GeneralGrid.getSelNo() - 1, 6);
	fm.all("ExecuteCom").value = cExecuteCom;
	queryGrpCustomerInfo(cExecuteCom,cCustomerNo);
}

/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ���ַ���ݷ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function displayGrpCustomerInfo()
{ 
	try {fm.all('CustomerNo').value= arrResult[0][0]; } catch(ex) { };
	try {fm.all('AddressNo').value= arrResult[0][1]; } catch(ex) { };
	try {fm.all('GrpAddress').value= arrResult[0][2]; } catch(ex) { };
	try {fm.all('GrpZipCode').value= arrResult[0][3]; } catch(ex) { };
	try {fm.all('LinkMan1').value= arrResult[0][4]; } catch(ex) { };
	try {fm.all('Department1').value= arrResult[0][5]; } catch(ex) { };
	try {fm.all('HeadShip1').value= arrResult[0][6]; } catch(ex) { };
	try {fm.all('Phone1').value= arrResult[0][7]; } catch(ex) { };
	try {fm.all('E_Mail1').value= arrResult[0][8]; } catch(ex) { };
	try {fm.all('Fax1').value= arrResult[0][9]; } catch(ex) { };
	try {fm.all('LinkMan2').value= arrResult[0][10]; } catch(ex) { };
	try {fm.all('Department2').value= arrResult[0][11]; } catch(ex) { };
	try {fm.all('HeadShip2').value= arrResult[0][12]; } catch(ex) { };
	try {fm.all('Phone2').value= arrResult[0][13]; } catch(ex) { };
	try {fm.all('E_Mail2').value= arrResult[0][14]; } catch(ex) { };
	try {fm.all('Fax2').value= arrResult[0][15]; } catch(ex) { };
	try {fm.all('Operator').value= arrResult[0][16]; } catch(ex) { };
	try {fm.all('MakeDate').value= arrResult[0][17]; } catch(ex) { };
	try {fm.all('MakeTime').value= arrResult[0][18]; } catch(ex) { };
	try {fm.all('ModifyDate').value= arrResult[0][19]; } catch(ex) { };
	try {fm.all('ModifyTime').value= arrResult[0][20]; } catch(ex) { };
	//������ldgrp��
	try {fm.all('GrpName').value= arrResult[0][23];   } catch(ex) { };     
	try {fm.all('BusinessType').value= arrResult[0][24];} catch(ex) { };        
	try {fm.all('GrpNature').value= arrResult[0][25]; } catch(ex) { };       
	try {fm.all('Peoples').value= arrResult[0][26]; } catch(ex) { };       
	try {fm.all('RgtMoney').value= arrResult[0][27]; } catch(ex) { };       
	try {fm.all('Asset').value= arrResult[0][28]; } catch(ex) { };       
	try {fm.all('NetProfitRate').value= arrResult[0][29];} catch(ex) { };        
	try {fm.all('MainBussiness').value= arrResult[0][30];} catch(ex) { };        
	try {fm.all('Corporation').value= arrResult[0][31];  } catch(ex) { };      
	try {fm.all('ComAera').value= arrResult[0][302]; } catch(ex) { };  
	try {fm.all('Fax').value= arrResult[0][33]; } catch(ex) { };  
	try {fm.all('Phone').value= arrResult[0][34]; } catch(ex) { };  
	try {fm.all('FoundDate').value= arrResult[0][35]; } catch(ex) { };          
	try {fm.all('GetFlag').value= arrResult[0][36]; } catch(ex) { };          
	try {fm.all('BankCode').value= arrResult[0][37]; } catch(ex) { };          
	try {fm.all('BankAccNo').value= arrResult[0][38]; } catch(ex) { };          
	try {fm.all('Remark').value= arrResult[0][39]; } catch(ex) { };          
}

/*********************************************************************
 *  �Ѳ�ѯ���صĿͻ���ַ���ݷ���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function resetGrpCustomerForm()
{ 
	try {fm.all('CustomerNo').value= ""; } catch(ex) { };
	try {fm.all('AddressNo').value= ""; } catch(ex) { };
	try {fm.all('GrpAddress').value= ""; } catch(ex) { };
	try {fm.all('GrpZipCode').value= ""; } catch(ex) { };
	try {fm.all('LinkMan1').value= ""; } catch(ex) { };
	try {fm.all('Department1').value= ""; } catch(ex) { };
	try {fm.all('HeadShip1').value= ""; } catch(ex) { };
	try {fm.all('Phone1').value= ""; } catch(ex) { };
	try {fm.all('E_Mail1').value= ""; } catch(ex) { };
	try {fm.all('Fax1').value= ""; } catch(ex) { };
	try {fm.all('LinkMan2').value= ""; } catch(ex) { };
	try {fm.all('Department2').value= ""; } catch(ex) { };
	try {fm.all('HeadShip2').value= ""; } catch(ex) { };
	try {fm.all('Phone2').value= ""; } catch(ex) { };
	try {fm.all('E_Mail2').value= ""; } catch(ex) { };
	try {fm.all('Fax2').value= ""; } catch(ex) { };
	try {fm.all('Operator').value= ""; } catch(ex) { };
	try {fm.all('MakeDate').value= ""; } catch(ex) { };
	try {fm.all('MakeTime').value= ""; } catch(ex) { };
	try {fm.all('ModifyDate').value= ""; } catch(ex) { };
	try {fm.all('ModifyTime').value= ""; } catch(ex) { };
	//������ldgrp��
	try {fm.all('GrpName').value= "";   } catch(ex) { };     
	try {fm.all('BusinessType').value= "";} catch(ex) { };        
	try {fm.all('GrpNature').value= ""; } catch(ex) { };       
	try {fm.all('Peoples').value= ""; } catch(ex) { };       
	try {fm.all('RgtMoney').value= ""; } catch(ex) { };       
	try {fm.all('Asset').value= ""; } catch(ex) { };       
	try {fm.all('NetProfitRate').value= "";} catch(ex) { };        
	try {fm.all('MainBussiness').value= "";} catch(ex) { };        
	try {fm.all('Corporation').value= "";  } catch(ex) { };      
	try {fm.all('ComAera').value= ""; } catch(ex) { };  
	try {fm.all('Fax').value= ""; } catch(ex) { };  
	try {fm.all('Phone').value= ""; } catch(ex) { };  
	try {fm.all('FoundDate').value= ""; } catch(ex) { };          
	try {fm.all('GetFlag').value= ""; } catch(ex) { };          
	try {fm.all('BankCode').value= ""; } catch(ex) { };          
	try {fm.all('BankAccNo').value= ""; } catch(ex) { };          
	try {fm.all('Remark').value= ""; } catch(ex) { };          
}

/*********************************************************************
 *  Click�¼�����˫�����ͻ��š�¼���ʱ�����ú���
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showAppnt1()
{
	if( mOperate == 0 )
	{
		mOperate = 2;
		showInfo = window.open( "../sys/GroupMain.html" );
	}
}           
function showAppnt()
{
 if (fm.all("CustomerNo").value == "" ) { 
 	showAppnt1();
 }
 else
 {
	var cCustomerNo = fm.all("CustomerNo").value;
 	arrResult = easyExecSql("select a.*,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate,b.BankCode,b.BankAccNo,b.Remark from LCGrpAddress a,LDGrp b where a.CustomerNo = b.CustomerNo and a.CustomerNo = '" + cCustomerNo + "'", 1, 0);			
	if (arrResult == null) {
		alert("δ�鵽Ͷ����λ��Ϣ");
	} 
	else {
	  displayGrpCustomerInfo();
	}
 }
}

function checkuseronly(comname)
{
arrResult = easyExecSql("select * from LDGrp  where GrpName='" + comname + "'", 1, 0);			
	 if (arrResult == null) {
            alert("δ�鵽Ͷ����λ��Ϣ");
	  } 
	  else {      	
 	         arrResult = easyExecSql("select a.*,b.GrpName,b.BusinessType,b.GrpNature,b.Peoples,b.RgtMoney,b.Asset,b.NetProfitRate,b.MainBussiness,b.Corporation,b.ComAera,b.Fax,b.Phone,b.FoundDate from LCGrpAddress a,LDGrp b where a.CustomerNo = b.CustomerNo and b.CustomerNo='" + arrResult[0][0] + "'", 1, 0);			
	         if (arrResult == null) {
                    alert("δ�鵽Ͷ����λ��Ϣ");
	         } 
	         else {
	         displayAddress1(arrResult[0]);
	        }
	  }	
}                    

/*********************************************************************
 *  ������ذ�ť,�رյ�ǰҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function goback()
{
  top.close();	
  //location.href="ContPolInput.jsp?scantype="+scantype+"&MissionID="+MissionID+"&ManageCom="+ManageCom+"&SubMissionID="+SubMissionID+"&ActivityID="+ActivityID+"&NoType="+NoType+"&LoadFlag="+ LoadFlag + "&polNo=" + GrpContNo;
	//return;
}                        
                        