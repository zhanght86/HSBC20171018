//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

//Creator :���	
//Date :2008-08-18

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();

//��ʼ��ҳ��
function queryFIRulePlanDefDetailInputGrid()
{
	try
	{

		initFIRulePlanDefDetailInputGrid();
		var strSQL = ""; 
		/**
		strSQL = "select VersionNo,RulePlanID,RuleID,Sequence,RuleState from FIRulePlanDefDetail where ";
		strSQL = strSQL + " VersionNo ='"+VersionNo+"' and RulePlanID ='"+RulePlanID+"' order by Sequence ASC ";
  		*/
  		var mySql1=new SqlClass();
			mySql1.setResourceName("fininterface.FIRulePlanDefDetailInputSql"); //ָ��ʹ�õ�properties�ļ���
			mySql1.setSqlId("FIRulePlanDefDetailInputSql1");//ָ��ʹ�õ�Sql��id
			mySql1.addSubPara(VersionNo);//ָ������Ĳ���
			mySql1.addSubPara(RulePlanID);//ָ������Ĳ���
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
		turnPage.pageDisplayGrid = FIRulePlanDefDetailInputGrid;
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
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.action = './FIRulePlanDefDetailSave.jsp';
  fm.submit(); //�ύ
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
	if(fm.RuleID.value != fm.RuleID1.value)
  {
  	alert("��ȷ����¼���У������ţ�����");
    return false;
  }
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
   {
    fm.OperateType.value = "UPDATE||MAIN";
    var i = 0;
    var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './FIRulePlanDefDetailSave.jsp';
    fm.submit(); //�ύ
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
	if((fm.RuleID.value=="")||(fm.RuleID.value=="null"))
  {
    alert("У������Ų���Ϊ�գ�����");
    return false;
  }	
  if(fm.RuleID.value != fm.RuleID1.value)
  {
  	alert("��ȷ����¼���У������ţ�����");
    return false;
  }
  if (confirm("��ȷʵҪɾ���ü�¼��"))
  {
    fm.OperateType.value = "DELETE||MAIN";
    var i = 0;
    var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ�����������Ľ���";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    fm.action = './FIRulePlanDefDetailSave.jsp';
    fm.submit();//�ύ
  }
  else
  {
    fm.OperateType.value = "";
  }
}


function ReturnData()
{
  var arrReturn = new Array();
	var tSel = FIRulePlanDefDetailInputGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		
			try
			{	
				arrReturn = getQueryResult();
				afterQuery( arrReturn );

				//alert(3);
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
	tRow = FIRulePlanDefDetailInputGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	var strSQL = "";
	/**
	strSQL = "select VersionNo,RulePlanID,RuleID,Sequence,RuleState from FIRulePlanDefDetail where 1=1 "
	       + "and VersionNo='"+FIRulePlanDefDetailInputGrid.getRowColData(tRow-1,1)+"' and RulePlanID='"+FIRulePlanDefDetailInputGrid.getRowColData(tRow-1,2)+"' and RuleID= '"+FIRulePlanDefDetailInputGrid.getRowColData(tRow-1,3)+"'" ; 
	*/
	var mySql2=new SqlClass();
		mySql2.setResourceName("fininterface.FIRulePlanDefDetailInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId("FIRulePlanDefDetailInputSql2");//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara(FIRulePlanDefDetailInputGrid.getRowColData(tRow-1,1));//ָ������Ĳ���
		mySql2.addSubPara(FIRulePlanDefDetailInputGrid.getRowColData(tRow-1,2));//ָ������Ĳ���
		mySql2.addSubPara(FIRulePlanDefDetailInputGrid.getRowColData(tRow-1,3));//ָ������Ĳ���
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
		fm.all('RuleID').value = arrResult[0][2];
		fm.all('RuleID1').value = arrResult[0][2];
		fm.all('Sequence').value = arrResult[0][3];
		fm.all('RuleState').value = arrResult[0][4];
		//fm.all('RuleID').disabled = true;	
		//fm.all('RuleIDName').disabled = true;	
		showCodeName(); 
		
	}
	
}
function initFIRulePlanDefDetailInputQuery()
{
	try
	{
  	var strSQL = "";
  	/**
		strSQL = "select VersionNo,RulePlanID,RuleID,Sequence,RuleState from FIRulePlanDefDetail where ";
		strSQL = strSQL + " VersionNo ='"+VersionNo+"' and RulePlanID ='"+RulePlanID+"' order by Sequence ASC ";
  	*/
  	var mySql3=new SqlClass();
		mySql3.setResourceName("fininterface.FIRulePlanDefDetailInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId("FIRulePlanDefDetailInputSql3");//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara(VersionNo);//ָ������Ĳ���
		mySql3.addSubPara(RulePlanID);//ָ������Ĳ���
		strSQL= mySql3.getString();
  	turnPage.queryModal(strSQL, FIRulePlanDefDetailInputGrid);
		
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

		if(fm.OperateType.value == "DELETE||MAIN")
		{
			fm.all('RuleID').value = '';  
			fm.all('RuleID1').value = '';       
    	fm.all('RuleIDName').value = '';
    	fm.all('Sequence').value = '';    
    	fm.all('RuleState').value = '';   
    	fm.all('RuleStateName').value = '';  
		}
    fm.OperateType.value="";
    initFIRulePlanDefDetailInputQuery();
  }
}

function beforeSubmit()
{			
  if((fm.RuleID.value=="")||(fm.RuleID.value=="null"))
  {
    alert("У������Ų���Ϊ�գ�����");
    return false;
  }	
  if((fm.RuleState.value=="")||(fm.RuleState.value=="null"))
  {
    alert("У�����״̬����Ϊ�գ�����");
    return false;
  }	
    return true;
}


//ɾ�����޸�ǰ����ѡ��һ����¼
function beforeSubmit2()
{		
	var tSel = FIRulePlanDefDetailInputGrid.getSelNo();	
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
			fm.all('RuleID').value = '';  
			fm.all('RuleID1').value = '';       
    	fm.all('RuleIDName').value = '';
    	fm.all('Sequence').value = '';    
    	fm.all('RuleState').value = '';   
    	fm.all('RuleStateName').value = '';
    	
    	
}