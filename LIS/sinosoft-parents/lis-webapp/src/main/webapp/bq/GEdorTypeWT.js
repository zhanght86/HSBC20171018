//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

function edorTypeWTReturn()
{
	top.close();
	top.opener.getEdorItemGrid();
}
function edorTypeWTQuery()
{
	alert("Wait...");
}
function edorTypeWTSave()
{
  //У���Ƿ��ѹ���ԥ�� by wenhuan
  //var intval = dateDiff(fm.CValiDate.value, fm.EdorValiDate.value, "D");
  //alert("�����ѱ�����"+intval);
  //if (intval>10)
  //{
  //	alert("�ñ����ѱ�"+intval+"�죬�ѹ���ԥ��!");
  //	return false;        //û�к��ʵĵ��ӣ�Ϊ�˲�����ʱע��
  //}
  //��ʼ����
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  document.all('fmtransact').value = "INSERT||MAIN";

 	//showSubmitFrame(mDebug);
  fm.submit();
  
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content,Result )
{
  showInfo.close();
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
  	var tTransact = document.all('fmtransact').value;
		if (tTransact=="QUERY||MAIN")
		{
			var iArray;
			//�������������������ͬ��ѯ����һ��turnPage����ʱ����ʹ�ã���ü��ϣ��ݴ�
  	turnPage.arrDataCacheSet = clearArrayElements(turnPage.arrDataCacheSet);
			//�����ѯ����ַ���
 		 	turnPage.strQueryResult  = Result;
//  alert(Result);
  		//ʹ��ģ������Դ������д�ڲ��֮ǰ
  		turnPage.useSimulation   = 1;  
    
  		//��ѯ�ɹ������ַ��������ض�ά����
  		var tArr   = decodeEasyQueryResult(turnPage.strQueryResult,0);
			
			turnPage.arrDataCacheSet =chooseArray(tArr,[1,20,21,31,42])
			//���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
		 	turnPage.pageDisplayGrid = LPPolGrid;    
		  
		  //���ò�ѯ��ʼλ��
		 	turnPage.pageIndex       = 0;
		 	//�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	  	var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
			//����MULTILINE������ʾ��ѯ���
	   	displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
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
    //alert(FlagStr);
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
    //ִ����һ������
  	}
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
  	alert("��Proposal.js-->resetForm�����з����쳣:��ʼ���������!");
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
	alert("query click");
	  //��ѯ���������һ��ģ̬�Ի��򣬲��ύ�������������ǲ�ͬ��
  //��ˣ����еĻ����Ҳ���Բ��ø�ֵ��
}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ�Ĵ���
  alert("delete click");
}           
//---------------------------
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

/*********************************************************************
 *  ��ʾfrmSubmit��ܣ���������
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function showSubmitFrame(cDebug)
{
	if( cDebug == "1" )
		parent.fraMain.rows = "0,0,50,82,*";
	else 
		parent.fraMain.rows = "0,0,0,72,*";
}

function getWTFeeDetailGrid()
{
	var sqlid817111718="DSHomeContSql817111718";
var mySql817111718=new SqlClass();
mySql817111718.setResourceName("bq.GEdorTypeWTInputSql");//ָ��ʹ�õ�properties�ļ���
mySql817111718.setSqlId(sqlid817111718);//ָ��ʹ�õ�Sql��id
mySql817111718.addSubPara(document.all('GrpContNo').value);//ָ������Ĳ���
var strSQL=mySql817111718.getString();
	
//	var strSQL = "select p.grppolno,p.riskcode,(select RiskName from LMRisk m where m.RiskCode =p.RiskCode),p.cvalidate,nvl(p.prem,0) from lcgrppol p where p.grpcontno='" + document.all('GrpContNo').value + "'";
	drrResult = easyExecSql(strSQL);
	if (drrResult)
	{
		displayMultiline(drrResult,WTFeeDetailGrid);
	}
}

