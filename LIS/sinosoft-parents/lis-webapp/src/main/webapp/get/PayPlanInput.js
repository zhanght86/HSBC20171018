//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���


var tResourceName="get.PayPlanInputSql";
var tResourceSQL1="PayPlanInputSql1";
var tResourceSQL2="PayPlanInputSql2";
var tResourceSQL3="PayPlanInputSql3";
var tResourceSQL4="PayPlanInputSql4"; 


//�ύ�����水ť��Ӧ����
function submitForm()
{              
  var i = 0;
  if((fm.ContNo.value == null || fm.ContNo.value == "") && (fm.GrpContNo.value == null || fm.GrpContNo.value == "")&&(fm.InsuredNo.value == null || fm.InsuredNo.value == ""))
  {
  	alert("����¼����˺�ͬ���룬�����˿ͻ�������屣���������е�һ�");
  	return;
  }
  if (window.confirm("�Ƿ�ȷ�ϱ��δ߸�?"))
  {
    if (verifyInput())
    {
	  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
	  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
	  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	  //showSubmitFrame(mDebug);
	  fm.action="./PayPlanSave.jsp"
	  document.getElementById("fm").submit(); //�ύ
    }
  }
}
function showCondPage()
{
	var tSerialNo = document.all('SerialNo').value;
	if (tSerialNo!=null&& tSerialNo!='')
	{
		divLCInsured2.style.display = '';
	}
	
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content,Count )
{
  showInfo.close();
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
		document.all('SerialNo').value = FlagStr;
		document.all('getcount').value = Count;
		
		if (window.confirm("�Ƿ��ѯ�������ɸ�����¼"))
			{
				divLCInsured2.style.display ='';
				easyQueryClick();
			}
	   //ִ����һ������
  }
}



//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
//    showDiv(operateButton,"true");
//    showDiv(inputButton,"false");
	  initForm();
  }
  catch(re)
  {
  	alert("��PayPlan.js-->resetForm�����з����쳣:��ʼ���������!");
  }
}

//ȡ����ť��Ӧ����
function cancelForm()
{
//  window.location="../common/html/Blank.html";
  //  showDiv(operateButton,"true");
   // showDiv(inputButton,"false");
}

//�ύǰ��У�顢����
function beforeSubmit()
{
  //��Ӳ���
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

// ��ѯ��ť
function easyQueryClick()
{
	var tSerialNo;
	
	tSerialNo = document.all('SerialNo').value;
	
	// ��ʼ�����
	initGetDrawGrid();
	
	// ��дSQL���
	var strSQL = "";
	//strSQL = "select LJSGet.GetNoticeNo,LCPol.InsuredNo,LJSGet.OtherNo,LCPol.RiskCode,LJSGet.GetDate,LJSGet.SumGetMoney from LJSGet,LCPol where LJSGet.OtherNo = LCPol.PolNo and LJSGet.OtherNoType='2'"
	//			 +" "+ getWherePart( 'SerialNo' );
	
	strSQL = wrapSql(tResourceName,tResourceSQL1,[document.all('SerialNo').value]); 
//	alert(strSQL);				 
	//��ѯSQL�����ؽ���ַ���
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
	  
	  //�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult) 
	  {
	    alert("��ѯʧ�ܣ�");
	  }
	  else
	  {
	  //��ѯ�ɹ������ַ��������ض�ά����
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	  
	  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	  turnPage.pageDisplayGrid = GetGrid;    
	          
	  //����SQL���
	  turnPage.strQuerySql     = strSQL; 
	  
	  //���ò�ѯ��ʼλ��
	  turnPage.pageIndex       = 0;  
	  
	  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
	  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
	  
	  //����MULTILINE������ʾ��ѯ���
	  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
		}
}



//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  //showDiv(operateButton,"false");
 // showDiv(inputButton,"true");
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