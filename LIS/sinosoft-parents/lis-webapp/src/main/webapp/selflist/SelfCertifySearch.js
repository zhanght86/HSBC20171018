
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���
var showInfo;
var lastCertifyCode;

// һЩ״̬�ĺ���
var vStateFlag = new ActiveXObject("Scripting.Dictionary");

vStateFlag.Add("0", "δ��");
vStateFlag.Add("1", "��������");
vStateFlag.Add("2", "����");
vStateFlag.Add("3", "��ʧ");
vStateFlag.Add("4", "����");

var vOperateFlag = new ActiveXObject("Scripting.Dictionary");

vOperateFlag.Add("0", "����");
vOperateFlag.Add("1", "����");
vOperateFlag.Add("2", "���Ż���");
vOperateFlag.Add("3", "���ջ���");


//�ύ�����水ť��Ӧ����
function submitForm()
{
    //alert("***");
	if( vertify()==true) 
	{
	  var showStr="���ڲ�ѯ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  	  document.getElementById("fm").submit(); //�ύ
	}
}

//����У��
function vertify()
{		
   //alert(document.all('CertifyCode').value);
   if(document.all('CertifyCode').value==null||document.all('CertifyCode').value=="")
   {
   	   alert("����ѡ��֤����!");
   	   return false;
   }
   
   lastCertifyCode=document.all('CertifyCode').value;
   
   return true;
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
  	alert("��CertifySearch.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

// �ڲ�ѯ����ʱ����������¼���
function onShowResult(result)
{
	if( result[0] == '' ) {
		alert('û�в�ѯ������');
	} else {
		useSimulationEasyQueryClick(result[0]);
	}
}

function useSimulationEasyQueryClick(strData) {
  //�����ѯ����ַ���
  turnPage.strQueryResult  = strData;
  
  //ʹ��ģ������Դ������д�ڲ��֮ǰ
  turnPage.useSimulation   = 1;  
    
  //����ַ��������ض�ά����
  var tArr    = turnPage.decodeEasyQueryResult(turnPage.strQueryResult);
  
  //��MULTILINE���,ʹMULTILINE��ʾʱ���ֶ�λ��ƥ�����ݿ���ֶ�λ��
  var filterArray  = new Array(0,1,2,3,4,5,6,7,8,9);

  //�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
  
  //���˶�ά���飬ʹ֮��MULTILINEƥ��
  turnPage.arrDataCacheSet = chooseArray(tArr, filterArray);
  
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = CardInfo;
  
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
	try 
	{
		//alert("lastCertifyCode"+lastCertifyCode);
		if(cCodeName == "CertifyCode") 
		{  
	    	//alert(Field.value);
	    	//���"�����˹�ϵѡ��Ϊ���׻�ĸ��,����ʾ�����˹�ϵ"
	    	if(Field.value!=lastCertifyCode)
	    	{
				//alert("***");
				fm.StartNo.value="";
				fm.EndNo.value="";
				fm.SendOutCom.value="";
				fm.ReceiveCom.value="";
	    	}

		}
	} 
	catch(ex) 
	{
		alert("��afterCodeSelect�з����쳣");
	}
}

// ��ѯ��֤״̬�����Ϣ
function searchState()
{
	fm.State.value = "0";
	submitForm();
}



function putList()
{
	/**var vRow = CardInfo.getSelNo();

	if( vRow == null || vRow == 0 ) {
		alert("����ѡ��һ����ѯ���");
		return;
	}

	vRow = vRow - 1;
	
	var vColIndex = 0;
	var vMaxRow = CardListInfo.mulLineCount;
	
	CardListInfo.addOne();
	
	for(vColIndex = 1; vColIndex <=10; vColIndex++) {
		CardListInfo.setRowColData(vMaxRow, vColIndex, CardInfo.getRowColData(vRow, vColIndex));
	}*/
	 var nChkCount = CardInfo.getChkCount();
  if (nChkCount == null || nChkCount <= 0)
  {
    alert("����ѡ��һ����ѯ����� ");
    return false;
  }
  for(var i=0;i<CardInfo.mulLineCount;i++)
  {
    if(CardInfo.getChkNo(i)){
    		var vColIndex = 0;
	     var vMaxRow = CardListInfo.mulLineCount;
	
	     CardListInfo.addOne();
	
	     for(vColIndex = 1; vColIndex <=10; vColIndex++) 
	     {
		     CardListInfo.setRowColData(vMaxRow, vColIndex, CardInfo.getRowColData(i, vColIndex));
	      }  	
    }
  }
}




function printList()
{
	//ͨ��У���ִ�д�ӡ����
	var rowNum=CardListInfo. mulLineCount
	//alert(rowNum);
	if(rowNum<=0)
	{
		alert("���Ƚ���Ҫ��ӡ�ļ�¼�����ӡ����!");
		return false;
	}
	
	if(document.all('feetypeName').value==null||document.all('feetypeName').value=="")
   	{
   	   alert("����ѡ��ɷ�����!");
   	}
   	else
   	{
   		fm_print.hiddenFeeType.value=document.all('feetypeName').value;
   		//alert(fm_print.all('hiddenFeeType').value);
   		document.getElementById("fm_print").submit();
   	}

}

function boxEventHandler(parm1, parm2)
{
	var vRow = CardInfo.getSelNo();
	
	if( vRow == null || vRow == 0 ) {
		return;
	}
	
	vRow = vRow - 1;

	var strSQL = "";
	
	
	if( fm.State.value == "0" )
	 {
//		strSQL = "SELECT * FROM LZCard";
		
		var sqlid1="SelfCertifySearchSql1";
	 	var mySql1=new SqlClass();
	 	mySql1.setResourceName("selflist.SelfCertifySearchSql");
	 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
	 	mySql1.addSubPara(CardInfo.getRowColData(vRow, 1));//ָ���������
	 	mySql1.addSubPara(CardInfo.getRowColData(vRow, 3));
	 	mySql1.addSubPara(CardInfo.getRowColData(vRow, 4));
	 	mySql1.addSubPara(CardInfo.getRowColData(vRow, 6));
	 	mySql1.addSubPara(CardInfo.getRowColData(vRow, 7));
	 	mySql1.addSubPara(CardInfo.getRowColData(vRow, 10));
	 	strSQL= mySql1.getString();
		
	} 
	else 
	{
//		strSQL = "SELECT * FROM LZCardTrack";
		
		var sqlid2="SelfCertifySearchSql2";
	 	var mySql2=new SqlClass();
	 	mySql2.setResourceName("selflist.SelfCertifySearchSql");
	 	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
	 	mySql2.addSubPara(CardInfo.getRowColData(vRow, 1));//ָ���������
	 	mySql2.addSubPara(CardInfo.getRowColData(vRow, 3));
	 	mySql2.addSubPara(CardInfo.getRowColData(vRow, 4));
	 	mySql2.addSubPara(CardInfo.getRowColData(vRow, 6));
	 	mySql2.addSubPara(CardInfo.getRowColData(vRow, 7));
	 	mySql2.addSubPara(CardInfo.getRowColData(vRow, 10));
	 	strSQL = mySql2.getString();
		
	}
	


//	strSQL = strSQL + " WHERE CertifyCode = '" + CardInfo.getRowColData(vRow, 1) +
//	          "' AND SendOutCom = '" + CardInfo.getRowColData(vRow, 3) +
//	          "' AND ReceiveCom = '" + CardInfo.getRowColData(vRow, 4) +
//	          "' AND StartNo >= '" + CardInfo.getRowColData(vRow, 6) +
//	          "' AND EndNo <= '" + CardInfo.getRowColData(vRow, 7) +
//	          "' AND MakeDate = '" + CardInfo.getRowColData(vRow, 10) + "'";
	
	
	
	
	//prompt("",strSQL);
	// Use my docode function
	var myResult = myDecodeEasyQueryResult(easyQueryVer3(strSQL));
	
	fm.CertifyCode.value 		= myResult[0][0];
	fm.SendOutCom.value 		= myResult[0][6];
	fm.ReceiveCom.value 		= myResult[0][7];
	fm.Operator.value 			= myResult[0][22];

	//fm.Handler.value 				= myResult[0][11];
	//fm.HandleDateB.value 		= myResult[0][12];
	//fm.HandleDateE.value 		= "";
	fm.MakeDateB.value 			= myResult[0][23];
	fm.MakeDateE.value 			= "";
	//fm.TakeBackNo.value 		= myResult[0][14];
	//fm.SumCount.value 			= myResult[0][8];
	fm.StartNo.value 				= myResult[0][4];
	fm.EndNo.value 					= myResult[0][5];

	
	//fm.CertifyState.value 	= vStateFlag.Item(myResult[0][16]);
	//fm.OperateFlag.value 		= vOperateFlag.Item(myResult[0][17]);
}

// EasyQuery ������������ turnPage�����໥Ӱ�죬���Ծ��Լ�����д��һ��
function myDecodeEasyQueryResult(strResult) {
	var arrEasyQuery = new Array();
	var arrRecord = new Array();
	var arrField = new Array();
	var recordNum, fieldNum, i, j;

	if (typeof(strResult) == "undefined" || strResult == "" || strResult == false)	{
		return null;
	}

	//���ó���������ǿ�ݴ���
	if (typeof(RECORDDELIMITER) == "undefined") RECORDDELIMITER = "^";
	if (typeof(FIELDDELIMITER) == "undefined") FIELDDELIMITER = "|";

	try {
	  arrRecord = strResult.split(RECORDDELIMITER);      //��ֲ�ѯ������õ���¼����
	  
	  recordNum = arrRecord.length;
	  for(i=1; i<recordNum; i++) {
	  	arrField = arrRecord[i].split(FIELDDELIMITER); //��ּ�¼���õ��ֶ�����
	  	
	  	fieldNum = arrField.length;
	  	arrEasyQuery[i - 1] = new Array();
	  	for(j=0; j<fieldNum; j++) {
		  	arrEasyQuery[i - 1][j] = arrField[j];          //�γ�����Ϊ��¼����Ϊ�ֶεĶ�ά����
		  }
	  }		
	} 
	catch(ex) {
	  alert("�������ʧ�ܣ�" + "\n����ԭ���ǣ�" + ex);
	  return null;  
	}
  
	return arrEasyQuery;
}
