//�������ƣ�UWManuHealth.js
//�����ܣ�����Լ�˹��˱��������¼��
//�������ڣ�2004-11-19 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var turnPage = new turnPageClass();
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";
var sqlresourcename = "uwgrp.UWManuHealthSql";
//�ύ�����水ť��Ӧ����
function submitForm()
{ 
	if (fm.all('InsureNo').value == "")   
	    {
	   	 alert("����������˺��룡");
	   	 return;
    	}
  else
    		 
     { 	
	var tCustomerNo = fm.all('InsureNo').value;
	var tContno = fm.all('ContNo').value; 
	
  //arrResult = easyExecSql("select * from LCPENotice where contno='" + tContno + "' and CustomerNo='" + tCustomerNo + "'", 1, 0);
  
  var sqlid1="UWManuHealthSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tContno);
		mySql1.addSubPara(tCustomerNo);
		arrResult = easyExecSql(mySql1.getString(), 1, 0);
  
		
  if (arrResult != null)
     {
       alert("���֪ͨ�Ѿ�¼��,��δ��ӡ������¼�����������!");
       return;	
   	 }
   	 
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]

  fm.action= "./UWManuHealthChk.jsp";
  fm.submit(); //�ύ
      }
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");      
    showInfo.close();
    alert(content);
    //parent.close();
  }
  else
  { 
	var showStr="�����ɹ�";
  	//showInfo.close();
  	alert(showStr);
  	parent.close();
  	
    //ִ����һ������
  }
}


//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
			parent.fraMain.rows = "0,0,50,82,*";
  }
 	else {
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


function manuchkhealthmain()
{
	fm.submit();
}

/*********************************************************************
 *  ִ�д�����Լ���֪ͨ���EasyQuery
 *  ����:��ѯ��ʾ���������֪ͨ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClickSingle()
{
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tMissionID = fm.MissionID.value;
	tInsuredNo = fm.InsureNo.value;
	QueryReason();
	/*
  strsql = "select LWMission.SubMissionID from LWMission where 1=1"
				 //+ " and LWMission.MissionProp2 = '"+ tContNo + "'"
				 + " and LWMission.ActivityID = '0000001101'"
				 + " and LWMission.ProcessID in ('0000000003', '0000000000')"
				 + " and LWMission.MissionID = '" +tMissionID +"'";
	 */
	  var sqlid2="UWManuHealthSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tMissionID);	
			 
	turnPage.strQueryResult = easyQueryVer3(mySql2.getString(), 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("������¼�������֪ͨ�飡");
    return "";
  }
   
   turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
   fm.SubMissionID.value = turnPage.arrDataCacheSet[0][0];
   turnPage = new turnPageClass();			 
	/*
	 strsql = "select LCPENotice.ContNo,LCPENotice.name,LCPENotice.pedate,LCPENotice.peaddress,LCPENotice.PEBeforeCond,LCPENotice.remark,LCPENotice.printflag from LCPENotice where 1=1"
				 + " and LCPENotice.ContNo = '"+ tContNo + "'"
				 + " and LCPENotice.customerno = '"+ tInsuredNo + "'";
	*/			 
	 var sqlid3="UWManuHealthSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(tContNo);	
		mySql3.addSubPara(tInsuredNo);	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(mySql3.getString(), 1, 1, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    //alert("��ѯʧ�ܣ�");
    easyQueryClickInit();
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  fm.ContNo.value = turnPage.arrDataCacheSet[0][0];
  //fm.InsureNo.value = turnPage.arrDataCacheSet[0][1];
  //fm.EDate.value = turnPage.arrDataCacheSet[0][2];
  fm.Hospital.value = turnPage.arrDataCacheSet[0][3];
  fm.IfEmpty.value = turnPage.arrDataCacheSet[0][4];
  fm.Note.value = turnPage.arrDataCacheSet[0][5];
  fm.PrintFlag.value = turnPage.arrDataCacheSet[0][6];
  easyQueryClick();
 
}

function QueryReason()
{
		// ��дSQL���

  var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tMissionID = fm.MissionID.value;
	tInsuredNo = fm.InsureNo.value;
  if(tContNo != "" && tInsuredNo != "")
  {
	
	/*
	strSQL = "select a.contno,a.uwno,a.uwerror,a.modifydate from LCUWError a where 1=1 "
			 + " and a.PolNo in (select distinct polno from lcpol where contno ='" +tContNo+ "')"
			 + " and SugPassFlag = 'H'"
			 + " and (a.uwno = (select max(b.uwno) from LCUWError b where b.ContNo = '" +tContNo + "' and b.PolNo = a.PolNo))"
			 + " union "
			 + "select c.contno,c.uwno,c.uwerror,c.modifydate from LCCUWError c where 1=1"
			 + " and c.ContNo ='" + tContNo + "'"
			 + " and SugPassFlag = 'H'"
			 + " and (c.uwno = (select max(d.uwno) from LCCUWError d where d.ContNo = '" + tContNo + "'))"
		*/
		 var sqlid4="UWManuHealthSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(tContNo);	
		mySql4.addSubPara(tContNo);	
		mySql4.addSubPara(tContNo);	
		mySql4.addSubPara(tContNo);	
		
				 				 
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(mySql4.getString(), 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = UWErrGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strsql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 }
}
// ��ѯ��ť
function easyQueryClick()
{
	// ��дSQL���
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tInsuredNo = fm.InsureNo.value; 
  
  if(tContNo != "" && tInsuredNo != "")
  {/*
	strsql = "select peitemcode,peitemname from LCPENoticeItem where 1=1"
				 + " and ContNo = '"+ tContNo + "'"
				 + " and PrtSeq in (select distinct prtseq from lcpenotice where contno = '"+ tContNo + "'"
				 + " and customerno = '"+ tInsuredNo + "')";
	*/			 				 
	 var sqlid5="UWManuHealthSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(tContNo);	
		mySql5.addSubPara(tContNo);	
		mySql5.addSubPara(tInsuredNo);	

  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(mySql5.getString(), 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  //turnPage.pageDisplayGrid = HealthGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strsql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  
  
 
  return true;
}


// ��������ϲ�ѯ��ť
function easyQueryClickInit()
{
	fm.action= "./UWManuHealthQuery.jsp";
	fm.submit();
}

function displayEasyResult( arrResult )
{
	var i, j, m, n;
	if( arrResult == null )
		alert( "û���ҵ���ص�����!" );
	else
	{		
		arrGrid = arrResult;
		// ��ʾ��ѯ���
		n = arrResult.length;
		for( i = 0; i < n; i++ )
		{
			m = arrResult[i].length;
			for( j = 0; j < m; j++ )
			{
				HealthGrid.setRowColData( i, j+1, arrResult[i][j] );
				
			} // end of for
		} // end of for
		//alert("result:"+arrResult);
	} // end of if
}


function initInsureNo(tPrtNo)
{
	var i,j,m,n;
	var returnstr;
	/*
	var strSql = "select InsuredNo,name from lcinsured where 1=1 "
	       + " and Prtno = '" +tPrtNo + "'"
	       + " union select CustomerNo , Name from LCInsuredRelated where polno in (select polno from lcpol where Prtno = '" +tPrtNo+"')";
	//��ѯSQL�����ؽ���ַ���
	*/
	 var sqlid6="UWManuHealthSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(tPrtNo);	
		mySql6.addSubPara(tPrtNo);	
	
	
  turnPage.strQueryResult  = easyQueryVer3(mySql6.getString(), 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    alert("��ѯʧ�ܣ�");
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
  fm.InsureNo.CodeData = returnstr;
  return returnstr;
}

//��ʼ��ҽԺ
function initHospital(tContNo)
{
	var i,j,m,n;
	var returnstr;
	/*
	var strSql = "select hospitcode,hospitname from ldhospital where 1=1 "
	       //+ "and codetype = 'hospitalcodeuw'"
	       + "and areacode = trim((select managecom from lccont where ContNo = '"+tContNo+"'))";
	*/
	 var sqlid7="UWManuHealthSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(tContNo);	

	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mySql7.getString(), 1, 1, 1);  
  //alert(strSql);
  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    //alert("ҽԺ��ʼ��ʧ�ܣ�");
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  //turnPage.pageDisplayGrid = VarGrid;    
          
  //����SQL���
  //turnPage.strQuerySql     = strSql; 
  
  //���ò�ѯ��ʼλ��
  //turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  //var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
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
  fm.Hospital.CodeData = returnstr;
  return returnstr;
}

function showNewUWSub()
{
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  //[start]  add by liuzhijie ˵��:��[start]...[end]֮��Ĵ�������滻�����һ����ע�ʹ��롢�Թ������������
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);
	showInfo.focus();
	//[end]
  
  cContNo=fm.ContNo.value;

  	//window.open("./PUWErrMain.jsp?ProposalNo1="+cProposalNo,"window1");
  	window.open("./UWPEErrMain.jsp?ContNo="+cContNo,"window1",sFeatures);
  	           
  	showInfo.close();
  	
}                      
