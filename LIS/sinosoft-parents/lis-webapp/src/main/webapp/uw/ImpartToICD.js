//�������ƣ�UWManuHealth.js
//�����ܣ�����Լ�˹��˱��������¼��
//�������ڣ�2004-11-19 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����

//               ���ļ��а����ͻ�����Ҫ�����ĺ������¼�

var showInfo;
var mDebug="0";
var flag;
var turnPage = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ���; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  fm.action= "./ImpartToICDSave.jsp";
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

/*********************************************************************
 *  ִ�д�����Լ���֪ͨ���EasyQuery
 *  ����:��ѯ��ʾ���������֪ͨ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function easyQueryClickSingle()
{
	// ��дSQL���
	var strsql = "";
	var tContNo = "";

	tContNo = fm.ContNo.value;
  tInsuredNo = fm.InsureNo.value;
  var mySql=new SqlClass();
  //turnPage = new turnPageClass();			
  if(tContNo != "" && tInsuredNo != "")
  { 
//		strSQL = "select ImpartVer,ImpartCode,ImpartDetailContent,DiseaseContent,StartDate,EndDate,Prover,CurrCondition,IsProved from LCCustomerImpartDetail where CustomerNo='"+tInsuredNo+"' and ContNo='"+tContNo+"' and CustomerNoType='I'";
		 
			 mySql.setResourceName("uw.ImpartToICDSql"); //ָ��ʹ�õ�properties�ļ���
			 mySql.setSqlId("ImpartToICDSql4");//ָ��ʹ�õ�Sql��id
			 mySql.addSubPara(tInsuredNo);
			 mySql.addSubPara(tContNo);
			 strSQL = mySql.getString();
  	//��ѯSQL�����ؽ���ַ���
  	turnPage.strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);  
  	
  	//�ж��Ƿ��ѯ�ɹ�
  	if (!turnPage.strQueryResult) {
  	  return "";
  	}
  	
  	//��ѯ�ɹ������ַ��������ض�ά����
  	turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  	//���ó�ʼ������MULTILINE����
  	turnPage.pageDisplayGrid = ImpartDetailGrid;    
  	//����SQL���
  	turnPage.strQuerySql = strSQL; 
  	//���ò�ѯ��ʼλ��
  	turnPage.pageIndex = 0;  
  	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  	
  	arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  	
  	//����MULTILINE������ʾ��ѯ���
  	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
	}
  return true;
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
	  var mySql=new SqlClass();
  if(tContNo != "" && tInsuredNo != "")
  {
//	strsql = "select peitemcode,peitemname,freepe from LCPENoticeItem where 1=1"
//				 + " and ContNo = '"+ tContNo + "'"
//				 + " and PrtSeq in (select distinct prtseq from lcpenotice where contno = '"+ tContNo + "'"
//				 + " and customerno = '"+ tInsuredNo + "')";
	 mySql.setResourceName("uw.ImpartToICDSql"); //ָ��ʹ�õ�properties�ļ���
	 mySql.setSqlId("ImpartToICDSql5");//ָ��ʹ�õ�Sql��id
	 mySql.addSubPara(tContNo);
	 mySql.addSubPara(tInsuredNo);
	 strsql = mySql.getString();		 				 
	
  //��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult = easyQueryVer3(strsql, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    return "";
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����HealthGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = HealthGrid;    
          
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
	
//	var strSql = "select InsuredNo,name from lcinsured where 1=1 "
//	       + " and Prtno = '" +tPrtNo + "'"
	       var mySql=new SqlClass();
			 mySql.setResourceName("uw.ImpartToICDSql"); //ָ��ʹ�õ�properties�ļ���
			 mySql.setSqlId("ImpartToICDSql6");//ָ��ʹ�õ�Sql��id
			 mySql.addSubPara(tPrtNo);
			 var strSql = mySql.getString();			;
	//��ѯSQL�����ؽ���ַ���
  
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);  
  
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
