//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

//Creator :���	
//Date :2008-08-18

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();

//��ʼ��ҳ��
function queryCostDataBaseDefInputGrid()
{
	try
	{

		initCostDataBaseDefInputGrid();
		var strSQL = ""; 
		/**
		strSQL = "select VersionNo,AcquisitionID,DataBaseID,DataBaseName,DataBaseOrder,Remark from FICostDataBaseDef where ";
		strSQL = strSQL + " VersionNo ='"+VersionNo+"' and AcquisitionID ='"+AcquisitionID+"' order by DataBaseOrder ASC ";
  		*/
  		var mySql1=new SqlClass();
			mySql1.setResourceName("fininterface.CostDataBaseDefInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId("CostDataBaseDefInputSql1");//ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(VersionNo);//ָ������Ĳ���
			mySql1.addSubPara(AcquisitionID);//ָ������Ĳ���
			strSQL= mySql1.getString();
  		
  	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  	if (!turnPage.strQueryResult)
		{
			return false;
		}
		//���ò�ѯ��ʼλ��
		turnPage.pageIndex = 0;
		//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
		turnPage.pageLineNum = 30 ;
		//��ѯ�ɹ������ַ��������ض�ά����
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		//���ó�ʼ������MULTILINE����
		turnPage.pageDisplayGrid = CostDataBaseDefInputGrid;
		//����SQL���
		turnPage.strQuerySql = strSQL ;
		arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
		//����MULTILINE������ʾ��ѯ���
		displayMultiline(arrDataSet, turnPage.pageDisplayGrid);

  }
  
  	catch(Ex)
	{
		alert(Ex.message);
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



//Click�¼������������ӡ�ͼƬʱ�����ú���
function submitForm()
{
	 if(!beforeSubmit())
	{
		return false;
	}
  fm.OperateType.value = "INSERT||MAIN";
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.action = './CostDataBaseDefSave.jsp';
  document.getElementById("fm").submit(); //�ύ
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
	if (!beforeSubmit2())
  {
  	return false;
  }	
  if(!beforeSubmit())
	{
		return false;
	}
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
   {
    fm.OperateType.value = "UPDATE||MAIN";
    var i = 0;
    var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './CostDataBaseDefSave.jsp';
    document.getElementById("fm").submit(); //�ύ
  }
  else
  {
    fm.OperateType.value = "";
    alert("��ȡ�����޸Ĳ�����");
  }
}

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
	if (!beforeSubmit2())
  {
  	return false;
  }	
  if ((document.all("DataBaseID").value==null)||(document.all("DataBaseID").value ==''))
  {
    alert("��ȷ��Ҫɾ��������Դ��ţ�");
    return false;
  }
    if(document.all("DataBaseID").value != document.all('DataBaseID1').value)
	{
		alert("��ȷ��Ҫɾ��������Դ��ţ�");
		return false;
	}
  if (confirm("��ȷʵҪɾ���ü�¼��"))
  {
    fm.OperateType.value = "DELETE||MAIN";
    var i = 0;
    var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ�����������Ľ���";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './CostDataBaseDefSave.jsp';
    document.getElementById("fm").submit();//�ύ
  }
  else
  {
    fm.OperateType.value = "";
  }
}


function ReturnData()
{
  var arrReturn = new Array();
	var tSel = CostDataBaseDefInputGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		
			try
			{	
				arrReturn = getQueryResult();
				afterQuery( arrReturn );
			}
			catch(ex)
			{
				alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
			}
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = CostDataBaseDefInputGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
	/**
	strSQL = "select * from FICostDataBaseDef where 1=1 "
	       + "and VersionNo='"+CostDataBaseDefInputGrid.getRowColData(tRow-1,1)+"' and AcquisitionID='"+CostDataBaseDefInputGrid.getRowColData(tRow-1,2)+"' and DataBaseID= '"+CostDataBaseDefInputGrid.getRowColData(tRow-1,3)+"'" ; 
	 */
	 	var mySql2=new SqlClass();
			mySql2.setResourceName("fininterface.CostDataBaseDefInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql2.setSqlId("CostDataBaseDefInputSql2");//ָ��ʹ�õ�Sql��id
			mySql2.addSubPara(CostDataBaseDefInputGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
			mySql2.addSubPara(CostDataBaseDefInputGrid.getRowColData(tRow-1,2));//ָ������Ĳ���
			mySql2.addSubPara(CostDataBaseDefInputGrid.getRowColData(tRow-1,3));//ָ������Ĳ���
			strSQL= mySql2.getString();    
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ�ܣ�");
    return false;
    }
//��ѯ�ɹ������ַ��������ض�ά����
  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
	return arrSelected;
}

function afterQuery( arrQueryResult )
{
	var arrResult = new Array();

	if( arrQueryResult != null )
	{
		arrResult = arrQueryResult;
		document.all('DataBaseID').value = arrResult[0][2];
		document.all('DataBaseID1').value = arrResult[0][2];
		document.all('DataBaseName').value = arrResult[0][3];
		document.all('DataBaseOrder').value = arrResult[0][4];
		document.all('Remark').value = arrResult[0][5];
		//document.all('DataBaseID').disabled = true;	
		//document.all('DataBaseName').disabled = true;	
		showCodeName(); 
	}
}
function initCostDataBaseDefInputQuery()
{
	try
	{
  	var strSQL = "";
  	/**
  	strSQL = "select VersionNo,AcquisitionID,DataBaseID,DataBaseName,DataBaseOrder,Remark from FICostDataBaseDef where ";
		strSQL = strSQL + " VersionNo ='"+VersionNo+"' and AcquisitionID ='"+AcquisitionID+"' order by DataBaseOrder ASC ";
  	*/
  	//alert(strSQL);
  		var mySql3=new SqlClass();
			mySql3.setResourceName("fininterface.CostDataBaseDefInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql3.setSqlId("CostDataBaseDefInputSql3");//ָ��ʹ�õ�Sql��id
			mySql3.addSubPara(VersionNo);//ָ������Ĳ���
			mySql3.addSubPara(AcquisitionID);//ָ������Ĳ���
			strSQL= mySql3.getString(); 
  	turnPage.queryModal(strSQL, CostDataBaseDefInputGrid);
  	
	}
	
	catch(Ex)
	{
		alert(Ex.message);
	}
}
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  //�ͷš����ӡ���ť

  if (FlagStr == "Fail" )
  {
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	fm.OperateType.value="";
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
		if(fm.OperateType.value == "DELETE||MAIN")
		{
			document.all('DataBaseID').value = '';
			document.all('DataBaseID1').value = '';
			document.all('DataBaseName').value = '';
			document.all('DataBaseOrder').value = '';
			document.all('Remark').value = '';
		}

    fm.OperateType.value="";
    initCostDataBaseDefInputQuery();
  }
}

function beforeSubmit()
{			
  if((fm.DataBaseID.value=="")||(fm.DataBaseID.value=="null"))
  {
    alert("����¼������Դ��ţ�����");
    return false;
  }	
  if((fm.DataBaseName.value=="")||(fm.DataBaseName.value=="null"))
  {
    alert("����Դ���Ʋ���Ϊ�գ�����");
    return false;
  }
    return true;
}


//ɾ�����޸�ǰ����ѡ��һ����¼
function beforeSubmit2()
{		
	var tSel = CostDataBaseDefInputGrid.getSelNo();	
	var strSQL="";
	if( tSel == 0 || tSel == null )
	{
		alert( "������ѡ��һ����¼���ٽ����޸ĺ�ɾ���Ĳ���!" );
		return;
	}
    return true;
}

function resetAgain()
{
			document.all('DataBaseID').value = '';
			document.all('DataBaseID1').value = '';
			document.all('DataBaseName').value = '';
			document.all('DataBaseOrder').value = '';
			document.all('Remark').value = '';
}