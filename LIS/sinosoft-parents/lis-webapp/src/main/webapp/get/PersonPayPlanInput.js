//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();          //ʹ�÷�ҳ���ܣ����뽨��Ϊȫ�ֱ���

var tResourceName="get.PersonPayPlanSql";
var tResourceSQL1="PersonPayPlanSql1";
var tResourceSQL2="PersonPayPlanSql2";

//�ύ�����水ť��Ӧ����
function submitForm()
{              
  var i = 0;
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
	  fm.action="./PersonPayPlanSave.jsp"
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
function afterSubmit( FlagStr, content, mSerialNo, mCount )
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
//	  alert(content);
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;
  	//showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=350;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.all('SerialNo').value = mSerialNo;			
	if (window.confirm("�Ƿ��ѯ�������ɸ�����¼"))
		{
			//divLCInsured2.style.display ='';
			easyQueryClick();
		}
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
  	alert("��PersonPayPlan.js-->resetForm�����з����쳣:��ʼ���������!");
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
	var tSerialNo = document.all('SerialNo').value;
	// ��ʼ�����
	initGetDrawGrid();
	initGetAccGrid();
	// ��дSQL���
	//var strSQL = " select GetNoticeNo, OtherNo, AppntNo, GetDate, SumGetMoney from LJSGet a"
	//			+" where a.serialno = '"+tSerialNo
	//			+"' order by a.getnoticeno, a.otherno, a.getdate";
  var strSQL = wrapSql(tResourceName,tResourceSQL1,[tSerialNo]); 
  
	//alert(strSQL);				 
	//��ѯSQL�����ؽ���ַ���
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
	  
	  //�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult) 
	  {
	    alert("��ѯʧ�ܣ�û��δ���ʻ���������������ܶ��Ѿ����ʻ����뵥������ѯ�Ѿ����ʻ�������ȡ����ť��ѯ��");
	    
	  } else
	  {
	  //��ѯ�ɹ������ַ��������ض�ά����
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//  fm.all('getcount').value = turnPage.arrDataCacheSet.length;
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

function queryinsuracc()
{
	if(document.all('ContNo').value==null||document.all('ContNo').value=="")
	{
		alert("�������벻��Ϊ�գ�");
		return false;
	}

	// var strSQL = "select contno,riskcode,(select riskname from lmrisk where riskcode=a.riskcode) ,"
	//		+" decode(moneytype,'YF','�����/���','EF','���ڽ�'),paydate,money from lcinsureacctrace a where  a.insuaccno in ('000005','000009')"
	//		+" and moneytype in ('YF','EF') "
			
			
			
    var strSQL = wrapSql(tResourceName,tResourceSQL2,[]); 
    
     if(document.all('ContNo').value!=null&&document.all('ContNo').value!="")
     {
    	 strSQL=strSQL+ " and contno='"+document.all('ContNo').value+"' ";
     }
	 if(document.all('PolNo').value!=null&&document.all('PolNo').value!="")
     {
    	 strSQL=strSQL+ " and PolNo='"+document.all('PolNo').value+"' ";
     }
	 strSQL=strSQL+" order by moneytype,paydate";
	//alert(strSQL);				 
	//��ѯSQL�����ؽ���ַ���
	  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
	  
	  //�ж��Ƿ��ѯ�ɹ�
	  if (!turnPage.strQueryResult) 
	  {
	    alert("��ѯʧ�ܣ��������ʻ��������");
	  } else
	  {
	  //��ѯ�ɹ������ַ��������ض�ά����
	  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
	//  fm.all('getcount').value = turnPage.arrDataCacheSet.length;
	  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
	  turnPage.pageDisplayGrid = GetGridInsurAcc;    
	          
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