//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var arrDataSet 
var showInfo;
var mDebug="0";
var tArr;
var turnPage = new turnPageClass();

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

//  initPolGrid();
  //showSubmitFrame(mDebug);
  document.getElementById("fm").submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();window.focus();
  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
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
    //var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   

    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //ִ����һ������
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
  	alert("��OLDCodeQuery.js-->resetForm�����з����쳣:��ʼ���������!");
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

function returnParent()
{
        var arrReturn = new Array();
	var tSel = CodeGrid.getSelNo();
	
	
		
	if( tSel == 0 || tSel == null )
		top.close();
		//alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		
			try
			{	
				//alert(tSel);
				arrReturn = getQueryResult();
				top.opener.afterQuery( arrReturn );
			}
			catch(ex)
			{
				alert( "û�з��ָ����ڵ�afterQuery�ӿڡ�" + ex );
			}
			top.close();
		
	}
}

function getQueryResult()
{
	var arrSelected = null;
	tRow = CodeGrid.getSelNo();
	//alert("111" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	//{alert("111");
	return arrSelected;
	//}
	//alert("222");	
	arrSelected = new Array();
	//������Ҫ���ص�����
	arrSelected[0] = arrDataSet[tRow-1];
	
	return arrSelected;
}



// ��ѯ��ť
function easyQueryClick()
{
	//alert("aaa");
	// ��ʼ�����
	initCodeGrid();
	
	// ��дSQL���
	var strSQL = "";
	
	//strSQL = "select AgentCode,Name,Birthday,AgentGroup,ManageCom,Business from LAAgent where 1=1"

	//			 +" "+ getWherePart( 'AgentCode' );
	//alert(strSQL);
	/**
	strSQL = "select * from FICodeTrans where 1=1"+" "+ getWherePart( 'CodeType' )+ getWherePart( 'Code' );			 
	*/
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.CodeQuerySql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("CodeQuerySql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.CodeType.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.Code.value);//ָ������Ĳ���
		strSQL= mySql1.getString();
	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ�ܣ�");
    return false;
    }
//��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = CodeGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
 arrDataSet= turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
 tArr=chooseArray(arrDataSet,[0,1,2,3,4]) 
  //����MULTILINE������ʾ��ѯ���
  
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  displayMultiline(tArr, turnPage.pageDisplayGrid);
}
