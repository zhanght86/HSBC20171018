//���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  document.getElementById("fm").submit(); //�ύ
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=250;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
  else
  { 
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=350;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

  }
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
	  initForm();
  }
  catch(re)
  {
  	alert("��AgentQuery.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 
//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���	
}           

//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  showDiv(operateButton,"false"); 
  showDiv(inputButton,"true"); 
}           

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  alert("update click");
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  //window.showModalDialog("./ProposalQuery.jsp",window,"status:0;help:0;edge:sunken;dialogHide:0;dialogWidth=15cm;dialogHeight=12cm");
  //��ѯ���������һ��ģ̬�Ի��򣬲��ύ�������������ǲ�ͬ��
  //��ˣ����еĻ����Ҳ���Բ��ø�ֵ��
}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  alert("delete click");
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

// ���ݷ��ظ�����
function returnParent()
{
	var arrReturn = new Array();
	var tSel = AgentGrid.getSelNo();
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		try
		{			
			arrReturn = getQueryResult();                        
			top.opener.afterQuery2( arrReturn );
			top.close();			 
		}
		catch(ex)
		{
			alert( "Ҫ���ص�ҳ�溯������");
		}		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = AgentGrid.getSelNo();
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();	
//	var strSQL = "select a.agentcom, a.managecom, a.name, a.address, a.zipcode, a.phone from lacom a where 1=1 "
//	           + "and a.agentcom='"+AgentGrid.getRowColData(tRow-1,1)+"'"; 
	
	    var sqlid1="AgentComQuerySql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("sys.AgentComQuerySql");
	 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
	 	mySql1.addSubPara(AgentGrid.getRowColData(tRow-1,1));//ָ���������
	 	var strSQL = mySql1.getString();
	
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

// ��ѯ��ť
function easyQueryClick()
{ 
	initAgentGrid();
	
	// ��дSQL���
	var strSQL = "";
//	var strOperate = "like";
//	strSQL = " select a.agentcom, a.managecom, a.name, a.address, a.zipcode, a.phone from lacom a where 1=1 "                  
//	         + getWherePart('a.agentcom','AgentCom',strOperate)
//	         + getWherePart('a.ManageCom','ManageCom',strOperate)
//	         + getWherePart('a.Name','Name',strOperate)	        
//	         + getWherePart('a.Address','Address',strOperate)
//	         + getWherePart('a.ZipCode','ZipCode',strOperate)
//	         + getWherePart('a.Phone','Phone',strOperate);  	
//	
	    var sqlid2="AgentComQuerySql2";
	 	var mySql2=new SqlClass();
	 	mySql2.setResourceName("sys.AgentComQuerySql");
	 	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
	 	mySql2.addSubPara(window.document.getElementsByName(trim("AgentCom"))[0].value);//ָ���������
	 	mySql2.addSubPara(window.document.getElementsByName(trim("ManageCom"))[0].value);
	 	mySql2.addSubPara(window.document.getElementsByName(trim("Name"))[0].value);
	 	mySql2.addSubPara(window.document.getElementsByName(trim("Address"))[0].value);
	 	mySql2.addSubPara(window.document.getElementsByName(trim("ZipCode"))[0].value);
	 	mySql2.addSubPara(window.document.getElementsByName(trim("Phone"))[0].value);
	 	strSQL = mySql2.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("δ��ѯ���������������ݣ�");
    return false;
    }
//��ѯ�ɹ������ַ��������ض�ά����
  arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.arrDataCacheSet = arrDataSet;
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = AgentGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var tArr = new Array();
  tArr = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(tArr, turnPage.pageDisplayGrid);
}


// Ͷ������Ϣ��ѯ
function ProposalClick()
{
	var arrReturn = new Array();
	var tSel = AgentGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
	    var cCustomerNo = AgentGrid.getRowColData(tSel - 1,1);				
		
		if (cCustomerNo == "")
		    return;		    
		    var cName = AgentGrid.getRowColData(tSel - 1,4);
			window.open("../sys/ProposalQuery.jsp?CustomerNo=" + cCustomerNo + "&Name=" + cName + "&Flag=Agent");	
	}
}

// ������Ϣ��ѯ
function PolClick()
{
	var arrReturn = new Array();
	var tSel = AgentGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
	    var cCustomerNo = AgentGrid.getRowColData(tSel - 1,1);			
		if (cCustomerNo == "")
		    return;
		    var cName = AgentGrid.getRowColData(tSel - 1,4);
		    window.open("../sys/PolQueryMain.jsp?CustomerNo=" + cCustomerNo + "&Name=" + cName + "&Flag=Agent");	
	}
}

//����������Ϣ��ѯ
function DesPolClick()
{
	var arrReturn = new Array();
	var tSel = AgentGrid.getSelNo();
	
	if( tSel == 0 || tSel == null )
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
	    var cCustomerNo = AgentGrid.getRowColData(tSel - 1,1);				
		if (cCustomerNo == "")
		    return;		    
		    var cName = AgentGrid.getRowColData(tSel - 1,4);
		    window.open("../sys/DesPolQueryMain.jsp?CustomerNo=" + cCustomerNo + "&Name=" + cName + "&Flag=Agent");	
	}
}
