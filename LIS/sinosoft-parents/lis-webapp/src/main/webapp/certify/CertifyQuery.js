
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var showInfo;

//�ύ�����水ť��Ӧ����
function submitForm()
{
	if( verifyInput() == true ) {
	  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	  var iWidth=550;      //�������ڵĿ��; 
	  var iHeight=250;     //�������ڵĸ߶�; 
	  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	  showInfo.focus();
	
	  fm.submit(); //�ύ
	}
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  showInfo.close();

  CardInfo.clearData();  // ���ԭ��������

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
  	alert("��CertifyQuery.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

function returnParent()
{
    tRow=PolGrid.getSelNo();
    tCol=1;
    tPolNo = PolGrid.getRowColData(tRow-1,tCol);
    var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
    //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    top.location.href="./CertifyQueryDetail.jsp?PolNo="+tPolNo;
}

// �ڲ�ѯ����ʱ����������¼���
function onShowResult(result)
{
	var strSQL;
	
	// useSimulationEasyQueryClick(result[0]);
	strSQL = result[0];
	fm.SumCount.value = result[1];
	
	// ����EasyQuery�Ĺ���
	turnPage.strQueryResult = easyQueryVer3(strSQL);
  
    //�ж��Ƿ��ѯ�ɹ�
	if (!turnPage.strQueryResult) {
		alert("û�в�ѯ������");
  	return false;
	}

	//��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult)
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = CardInfo;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //tArr = chooseArray(arrDataSet,[0,1,3,4]) 
  //����MULTILINE������ʾ��ѯ���
  
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
}


function useSimulationEasyQueryClick(strData) {
  //�����ѯ����ַ���
  turnPage.strQueryResult  = strData;
  
  //ʹ��ģ������Դ������д�ڲ��֮ǰ
  turnPage.useSimulation   = 1;  
    
  //����ַ��������ض�ά����
  var tArr                 = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //��MULTILINE���,ʹMULTILINE��ʾʱ���ֶ�λ��ƥ�����ݿ���ֶ�λ��
  var filterArray          = new Array(0, 6, 7, 4, 5, 8, 22);

  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //���˶�ά���飬ʹ֮��MULTILINEƥ��
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = CardInfo
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, turnPage.pageLineNum);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);	
  
  //�����Ƿ���ʾ��ҳ��ť
  if (turnPage.queryAllRecordCount > turnPage.pageLineNum) {
    try { window.divPage.style.display = ""; } catch(ex) { }
  } else {
    try { window.divPage.style.display = "none"; } catch(ex) { }
  }
  
  //���뽫������������Ϊһ�����ݿ�
  turnPage.blockPageNum = turnPage.queryAllRecordCount / turnPage.pageLineNum;
}

// �¼���Ӧ���������û��ı�CodeSelect��ֵʱ����
function afterCodeSelect(cCodeName, Field)
{
	try {
		if( cCodeName == 'State' ) {
			var trObj = tbInfo.rows(1);
			var tdObj;
			
			if( Field.value == '1' ) {  // ���
				trObj.cells(0).innerHTML = "";
				trObj.cells(1).innerHTML = "<input class='common' name='SendOutCom' type='hidden'>";
        trObj.cells(2).innerHTML = "ͳ�ƻ���";
        trObj.cells(3).innerHTML = "<input class='common' name='ReceiveCom'>";
        
			} else if( Field.value == '2' ) {  // �ѷ���
				trObj.cells(0).innerHTML = "��";
				trObj.cells(1).innerHTML = "<input class='common' name='SendOutCom'>";
        trObj.cells(2).innerHTML = "���ŵ�";
        trObj.cells(3).innerHTML = "<input class='common' name='ReceiveCom'>";

			} else if( Field.value == '3' ) {  // δ����
				trObj.cells(0).innerHTML = "��";
				trObj.cells(1).innerHTML = "<input class='common' name='SendOutCom'>";
        trObj.cells(2).innerHTML = "���ŵ�";
        trObj.cells(3).innerHTML = "<input class='common' name='ReceiveCom'>";

			} else if( Field.value == '4' ) {  // �������
				trObj.cells(0).innerHTML = "";
				trObj.cells(1).innerHTML = "<input class='common' name='SendOutCom' type='hidden'>";
        trObj.cells(2).innerHTML = "ͳ�ƻ���";
        trObj.cells(3).innerHTML = "<input class='common' name='ReceiveCom'>";

			}else if( Field.value == '5' ) {  // ��������
				trObj.cells(0).innerHTML = "��";
				trObj.cells(1).innerHTML = "<input class='common' name='SendOutCom'>";
        trObj.cells(2).innerHTML = "���յ�";
        trObj.cells(3).innerHTML = "<input class='common' name='ReceiveCom'>";

			}else if( Field.value == '6' ) {  // ��ʧ����
				trObj.cells(0).innerHTML = "��";
				trObj.cells(1).innerHTML = "<input class='common' name='SendOutCom'>";
        trObj.cells(2).innerHTML = "���յ�";
        trObj.cells(3).innerHTML = "<input class='common' name='ReceiveCom'>";

			}else if( Field.value == '7' ) {  // ����
				trObj.cells(0).innerHTML = "��";
				trObj.cells(1).innerHTML = "<input class='common' name='SendOutCom'>";
        trObj.cells(2).innerHTML = "������";
        trObj.cells(3).innerHTML = "<input class='common' name='ReceiveCom'>";

			}
		}
	} catch(ex) {
		alert("��afterCodeSelect�з����쳣");
	}
}

function easyPrint()
{
	easyQueryPrint(2,'CardInfo','turnPage');
}