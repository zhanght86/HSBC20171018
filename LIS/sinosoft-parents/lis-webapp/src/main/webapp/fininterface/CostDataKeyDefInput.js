//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

//Creator :���	
//Date :2008-08-18

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();

//��ʼ��ҳ��
function queryCostDataKeyDefInputGrid()
{
	try
	{
		initCostDataKeyDefInputGrid();
		var strSQL = ""; 
		/**
		strSQL = "select a.VersionNo,a.AcquisitionID,( select b.ColumnMark from FITableColumnDef b where b.ColumnID=a.KeyID and tableid='FIAboriginalData'),a.KeyName,a.KeyOrder,a.Remark from FICostDataKeyDef a where ";
		strSQL = strSQL + " a.VersionNo ='"+VersionNo+"' and a.AcquisitionID ='"+AcquisitionID+"' order by a.KeyOrder ASC ";
  		*/
  		var mySql1=new SqlClass();
			mySql1.setResourceName("fininterface.CostDataKeyDefInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId("CostDataKeyDefInputSql1");//ָ��ʹ�õ�Sql��id
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
		turnPage.pageDisplayGrid = CostDataKeyDefInputGrid;
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
  fm.action = './CostDataKeyDefSave.jsp';
  document.getElementById("fm").submit(); //�ύ
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
	if(!beforeSubmit2())
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
    fm.action = './CostDataKeyDefSave.jsp';
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
	if(!beforeSubmit2())
	{
		return false;
	}
  if ((document.all("KeyID").value==null)||(document.all("KeyID").value==''))
  {
    alert("��ȷ��Ҫɾ����������ţ�");
    return false;
  }
   if(document.all("KeyID").value != document.all('KeyID1').value)
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

    fm.action = './CostDataKeyDefSave.jsp';
    document.getElementById("fm").submit();//�ύ

  }
  else
  {
    fm.OperateType.value = "";
    //alert("���Ѿ�ȡ�����޸Ĳ�����");
  }
}


function ReturnData()
{
  var arrReturn = new Array();
	var tSel = CostDataKeyDefInputGrid.getSelNo();	
		
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
	tRow = CostDataKeyDefInputGrid.getSelNo();

	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
	/**
	strSQL = "select a.VersionNo,a.AcquisitionID,( select b.ColumnMark from FITableColumnDef b where b.ColumnID=a.KeyID and tableid='FIAboriginalData'),a.KeyName,a.KeyOrder,a.Remark from FICostDataKeyDef a where 1=1 "
	       + "and a.VersionNo='"+CostDataKeyDefInputGrid.getRowColData(tRow-1,1)+"' and a.AcquisitionID='"+CostDataKeyDefInputGrid.getRowColData(tRow-1,2)+"' and a.KeyID= ( select b.ColumnID from FITableColumnDef b where b.ColumnMark='"+CostDataKeyDefInputGrid.getRowColData(tRow-1,3)+"' and tableid='FIAboriginalData')" ; 
	 */
	 	var mySql2=new SqlClass();
			mySql2.setResourceName("fininterface.CostDataKeyDefInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql2.setSqlId("CostDataKeyDefInputSql2");//ָ��ʹ�õ�Sql��id
			mySql2.addSubPara(CostDataKeyDefInputGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
			mySql2.addSubPara(CostDataKeyDefInputGrid.getRowColData(tRow-1,2));//ָ������Ĳ���
			mySql2.addSubPara(CostDataKeyDefInputGrid.getRowColData(tRow-1,3));//ָ������Ĳ���
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

		document.all('KeyID').value = arrResult[0][2];
		document.all('KeyID1').value = arrResult[0][2];
		document.all('KeyName').value = arrResult[0][3];
		document.all('KeyOrder').value = arrResult[0][4];
		document.all('Remark').value = arrResult[0][5];
		//document.all('KeyID').disabled = true;	
		//document.all('KeyName').disabled = true;	
		showCodeName(); 
	}
	
}
function initCostDataKeyDefInputQuery()
{
	try
	{
  	var strSQL = ""; 
  	/**
		strSQL = "select a.VersionNo,a.AcquisitionID,( select b.ColumnMark from FITableColumnDef b where b.ColumnID=a.KeyID and tableid='FIAboriginalData'),a.KeyName,a.KeyOrder,a.Remark from FICostDataKeyDef a where ";
		strSQL = strSQL + " a.VersionNo ='"+VersionNo+"' and a.AcquisitionID ='"+AcquisitionID+"' order by a.KeyOrder ASC ";
	*/
		var mySql3=new SqlClass();
			mySql3.setResourceName("fininterface.CostDataKeyDefInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql3.setSqlId("CostDataKeyDefInputSql3");//ָ��ʹ�õ�Sql��id
			mySql3.addSubPara(VersionNo);//ָ������Ĳ���
			mySql3.addSubPara(AcquisitionID);//ָ������Ĳ���
			strSQL= mySql3.getString(); 
  	//alert(strSQL);
  	turnPage.queryModal(strSQL, CostDataKeyDefInputGrid);
  	
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
	var iHeight=350;     //�������ڵĸ߶�; 
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
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

		if(fm.OperateType.value = "DELETE||MAIN")
		{
			document.all('KeyID').value = '';
			document.all('KeyID1').value = '';
  		document.all('KeyIDName').value = '';
			document.all('KeyName').value = '';
			document.all('KeyOrder').value = '';
			document.all('Remark').value = '';
		}
    fm.OperateType.value="";
    initCostDataKeyDefInputQuery();
  }
}

function beforeSubmit()
{			
  if((fm.KeyID.value=="")||(fm.KeyID.value=="null"))
  {
    alert("����¼������������ţ�����");
    return false;
  }
  if((fm.KeyName.value=="")||(fm.KeyName.value==null))
  {
  	alert("�������Ʋ���Ϊ�գ�");
  	return false;
  }
    return true;
}


//ɾ�����޸�ǰ����ѡ��һ����¼
function beforeSubmit2()
{		
	var tSel = CostDataKeyDefInputGrid.getSelNo();	
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
			document.all('KeyID').value = '';
			document.all('KeyID1').value = '';
  		document.all('KeyIDName').value = '';
			document.all('KeyName').value = '';
			document.all('KeyOrder').value = '';
			document.all('Remark').value = '';
}