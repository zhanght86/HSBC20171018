//�������ƣ�PEdorUWManuHealth.js
//�����ܣ���ȫ�˹��˱��������¼��
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var turnPage = new turnPageClass();
var arrResult;
var turnPage1=new turnPageClass();
var turnPage2=new turnPageClass();
var sqlresourcename = "uwgrp.UWManuHealthQSql";
//�ύ�����水ť��Ӧ����
function submitForm()
{
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


// ��ѯ��ť
function easyQueryClickSingle()
{
	// ��дSQL���
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	tInsuredNo = fm.InsureNo.value;
  if(tContNo != "" && tInsuredNo != "")
  {/*
	strsql = "select LCPENotice.ContNo,LCPENotice.PrtSeq,LCPENotice.CustomerNo,LCPENotice.Name,LCPENotice.PEDate,LCPENotice.MakeDate,LOPRTManager.StateFlag from LCPENotice,LOPRTManager where 1=1"
				+ " and LCPENotice.PrtSeq = LOPRTManager.PrtSeq"
				 + " and ContNo = '"+ tContNo + "'"
				 + " and Customerno = '"+ tInsuredNo + "'";
		*/		 				 
		var sqlid1="UWManuHealthQSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tContNo);
		mySql1.addSubPara(tInsuredNo);
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(mySql1.getString(), 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = MainHealthGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strsql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  return true;
 
}


// ��ѯ��ť
function easyQueryClick(parm1,parm2)
{


	// ��дSQL���
	var strsql = "";
	var tContNo = "";
	var tEdorNo = "";
	tContNo = fm.ContNo.value;
	var tSelNo = MainHealthGrid.getSelNo()-1;
  	var tPrtSeq = MainHealthGrid.getRowColData(tSelNo,2);
  if(tContNo != "" && tPrtSeq != "")
  {/*
      var strsql1="select PEAddress,PEDoctor,PEDate,REPEDate,operator,makedate,modifydate,masculineflag,remark from LCPENotice where 1=1"	 
				  + " and ContNo = '"+ tContNo + "'"
				  + " and PrtSeq = '"+ tPrtSeq + "'";
				  //alert(strsql1);
	*/			  
		var sqlid2="UWManuHealthQSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(sqlresourcename);
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tContNo);
		mySql2.addSubPara(tPrtSeq);
				  
       //arrResult=easyExecSql(strsql1,1,0);
       var arrReturn = new Array();
       arrReturn = easyExecSql(mySql2.getString());
       //alert("arrReturn="+arrReturn);
        if(arrReturn!=null)
        {
        	//alert(222);
        	fm.all('PEAddress').value = arrReturn[0][0];
        	fm.all('PEDoctor').value = arrReturn[0][1];
        	
        	fm.all('PEDate').value = arrReturn[0][2];
       	 	fm.all('REPEDate').value = arrReturn[0][3];
       	 	
       	 	fm.all('Operator').value = arrReturn[0][4];
        	fm.all('MakeDate').value = arrReturn[0][5];
        	//alert(333);
        	fm.all('ReplyDate').value = arrReturn[0][6];
        	fm.all('MasculineFlag').value = arrReturn[0][7];
        	fm.all('Note').value = arrReturn[0][8];
        }
  }
  if(tContNo != "" && tPrtSeq != "")
  {/*
	 var tstrsql = "select peitemcode,peitemname,PEItemResult from LCPENoticeItem where 1=1"
				
				  + " and ContNo = '"+ tContNo + "'"
				  + " and PrtSeq = '"+ MainHealthGrid.getRowColData(MainHealthGrid.getSelNo()-1,2) + "'";
	*/
	var sqlid3="UWManuHealthQSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(sqlresourcename);
		mySql3.setSqlId(sqlid3);
		mySql3.addSubPara(tContNo);
		mySql3.addSubPara(MainHealthGrid.getRowColData(MainHealthGrid.getSelNo()-1,2));
	
	//alert(tstrsql);
	//fm.PEAddress.value = tstrsql;
	//turnPage.queryModal(tstrsql, HealthGrid);
	//alert()
 
	
turnPage.strQueryResult  = easyQueryVer3(mySql3.getString(), 1, 0, 1);  

  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	
    alert("δ��ѯ���������������ݣ�");
     return false;
  }
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  turnPage.pageLineNum = 30 ;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = HealthGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = tstrsql; 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  }
  
  //��ѯ��������Ϣ
  /*
  var Sql = "select DisDesb,DisResult,ICDCode from LCPENoticeResult where 1=1"
           + " and ContNo = '"+ tContNo + "'"
				 + " and PrtSeq = '"+ tPrtSeq + "'";
				  //��ѯSQL�����ؽ���ַ���
				  //alert(Sql);
	*/			  
				  var sqlid4="UWManuHealthQSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(sqlresourcename);
		mySql4.setSqlId(sqlid4);
		mySql4.addSubPara(tContNo);
		mySql4.addSubPara(tPrtSeq);
				  
  turnPage.strQueryResult = easyQueryVer3(mySql4.getString(), 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = DisDesbGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = Sql; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
  	//��ѯ���������Ϣ	
  	/*		 
	var strSQL = "select * from LCPENotice where 1=1"	
				+" and ContNo = '"+tContNo+"'"
				+" and PrtSeq = '"+tPrtSeq+"'"; 	
		
	*/
		var sqlid5="UWManuHealthQSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(sqlresourcename);
		mySql5.setSqlId(sqlid5);
		mySql5.addSubPara(tContNo);
		mySql5.addSubPara(tPrtSeq);
	
	var arrReturn = new Array();
        arrReturn = easyExecSql(mySql5.getString());
      
   if (arrReturn == null) {
			  alert("δ�������Ϣ");
			} else {
			   displayHealth(arrReturn[0]);
			}  
			
  return true;
 
}

function displayHealth(cArr)
{
	
  	try { fm.all('Note').value = cArr[21]; } catch(ex) { };
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

function initInsureNo()
{
	
	var i,j,m,n;
	var returnstr;
	
	var tContNo = fm.ContNo.value;
	
		//alert(tPrtSeq);
	//��ѯSQL�����ؽ���ַ���
	/*
 var strSql = " select ContNo,PrtSeq,CustomerNo,Name,Pedate,MakeDate,PrintFlag from lcpenotice where 1=1 "
          + " and ContNo = '"+tContNo+"'";
          */
 var sqlid6="UWManuHealthQSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(sqlresourcename);
		mySql6.setSqlId(sqlid6);
		mySql6.addSubPara(tContNo);

  turnPage.strQueryResult  = easyQueryVer3(mySql6.getString(), 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (!turnPage.strQueryResult)
  {
    alert("��ѯʧ�ܣ�");
    return "";
  }
  
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  
 //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = MainHealthGrid;

 //����SQL���
  turnPage.strQuerySql   = strSql;
  
 //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0; 
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  
   //����MULTILINE������ʾ��ѯ���
   displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
//    
//    var prtSeq = fm.PrtSeq.value;
//		var CustomerNo = fm.CustomerNo.value;
//		var arrReturn;
//		var strSQL = "select Operator,MakeDate,ModifyTime from LCPENotice where PrtSeq = '"+prtSeq+"' and CustomerNo='"+CustomerNo+"'";
//		alert(strSQL);
//		try{
//			arrReturn =easyExecSql(strSQL);
//		}
//		catch(ex)
//		{
//			alert( "��ѯ������ʧ�ܣ�");		
//		}
//		
//		fm.all.('Operator').value = arrReturn[0][0];
//		fm.all.('MakeDate').value = arrReturn[0][1];
//		return;	
//} 


}

  function initCustomerNo()
{
	
	var i,j,m,n;
	var returnstr;
	
	var tContNo = fm.ContNo.value;
	var tCustomerNo = fm.CustomerNo.value;
	
		//alert(tPrtSeq);
	//��ѯSQL�����ؽ���ַ���
	/*
 var strSql = " select ContNo,PrtSeq,CustomerNo,Name,Pedate,MakeDate,PrintFlag from lcpenotice where 1=1 "
          + " and ContNo = '"+tContNo+"'"
          + " and CustomerNo = '"+tCustomerNo+"'";
 */
 var sqlid7="UWManuHealthQSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName(sqlresourcename);
		mySql7.setSqlId(sqlid7);
		mySql7.addSubPara(tContNo);
 mySql7.addSubPara(tCustomerNo);
  turnPage.strQueryResult  = easyQueryVer3(mySql7.getString(), 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (!turnPage.strQueryResult)
  {
    alert("��ѯʧ�ܣ�");
    return "";
  }
  
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  
 //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = MainHealthGrid;

 //����SQL���
  turnPage.strQuerySql   = strSql;
  
 //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0; 
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  
   //����MULTILINE������ʾ��ѯ���
   displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
   
}


//��ʼ��ҽԺ
function initHospital(tContNo)
{
	var i,j,m,n;
	var returnstr;
	/*

	var strSql = "select code,codename from ldcode where 1=1 "
	       + "and codetype = 'hospitalcodeuw'"
	       + "and comcode = (select managecom from lccont where ContNo = '"+tContNo+"')";
	//��ѯSQL�����ؽ���ַ���
	*/
	var sqlid8="UWManuHealthQSql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(sqlresourcename);
		mySql8.setSqlId(sqlid8);
		mySql8.addSubPara(tContNo);

	
  turnPage.strQueryResult  = easyQueryVer3(mySql8.getString(), 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  //alert(turnPage.strQueryResult);
  if (turnPage.strQueryResult == "")
  {
    alert("ҽԺ��ʼ��ʧ�ܣ�");
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


/*********************************************************************
 *  ��켲����Ϣ����
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function saveDisDesb()
{
		if(DisDesbGrid.mulLineCount<1){
		alert("�������������Ϊ�գ�");
		return false;		
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
	
 
  fm.action= "./UWManuHealthQSave.jsp";
  fm.submit(); //�ύ
}

function init()
{
	
	var prtSeq = fm.PrtSeq.value;
	var contno=fm.ContNo.value;
	//alert("222"+contno);
	//alert("777"+prtSeq);
	//var strSQL = "select PEItemCode,PEItemName,PEItemResult from LCPENoticeItem where PrtSeq = '"+prtSeq+"'";
	
	var sqlid9="UWManuHealthQSql9";
		var mySql9=new SqlClass();
		mySql9.setResourceName(sqlresourcename);
		mySql9.setSqlId(sqlid9);
		mySql9.addSubPara(prtSeq);
	
	//alert(strSQL);
        //turnPage.queryModal(strSQL, HealthGrid);
        
        
        	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mySql9.getString(), 1, 0, 1);  
  //alert(turnPage.strQueryResult);
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	
    //alert("δ��ѯ���������������ݣ�");
     return false;
  }
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  turnPage.pageLineNum = 30 ;
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = HealthGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL 
  
  
  //arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex,MAXSCREENLINES);
  arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

      
       
}

