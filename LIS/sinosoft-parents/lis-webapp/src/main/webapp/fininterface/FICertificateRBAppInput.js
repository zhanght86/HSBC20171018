
//�������ڣ� 
//������   jw
//���¼�¼��  ������    ��������     ����ԭ��/����
var showInfo;

var turnPage = new turnPageClass();
var turnPage1 = new turnPageClass();
var k = 0;

/*********************************************************************
 *  ִ������Լɨ��ġ���ʼ¼�롱
 *  ����:������ɨ��¼��ҳ��
 *  ����  ��  ��
 *  ����ֵ��  ��
 *********************************************************************
 */
function ApplyInput()
{

	if(fm.CertificateId.value==''){
		alert("ƾ֤���ͱ�Ų���Ϊ��");
		return ;
	}
	if(fm.BusinessNo.value==''){
		alert("ҵ����벻��Ϊ��");
		return ;
	}
	if(fm.DetailIndexID.value==''){
		alert("��ϸ�������벻��Ϊ��");
		return ;
	}
	if(fm.DetailIndexName.value==''){
		alert("��ϸ�������Ʋ���Ϊ��");
		return ;
	}
	if(fm.DetailReMark.value==''){
		alert("ϸ����������Ϊ��");
		return ;
	}
	if(fm.ReasonType.value==''){
		alert("���ԭ�����Ͳ���Ϊ��");
		return ;
	}
	if(fm.DetailReMark.value==''){
		alert("�����˲���Ϊ��");
		return ;
	}
	if(fm.AppDate.value==''){
		alert("�������ڲ���Ϊ��");
		return ;
	}
        var showStr="�����ύ���ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";                                             
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;                                             
        var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
		var iWidth=550;      //�������ڵĿ��; 
		var iHeight=250;     //�������ڵĸ߶�; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
	document.getElementById("fm").submit();
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{ 
  try
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
  }
    }catch(ex){}
  queryRBResultGrid();
}


function queryRBResultGrid()
{
 
    initRBResultGrid() ;
     
    var strSQL = ""; 
    /**
    strSQL = "select AppNo,CertificateID,DetailIndexID,DetailIndexName,BusinessNo,ReasonType,DetailReMark,AppDate,Applicant,AppState from FIDataFeeBackApp where 1=1"
				 + getWherePart('BusinessNo','BusinessNo')
				 + getWherePart('DetailIndexID','DetailIndexID')
				 + " and Applicant = '" + operator + "'"
				 ;
				 */
	var mySql1=new SqlClass();
		mySql1.setResourceName("fininterface.FICertificateRBAppInputSql"); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId("FICertificateRBAppInputSql1");//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(fm.BusinessNo.value);//ָ������Ĳ���
		mySql1.addSubPara(fm.DetailIndexID.value);//ָ������Ĳ���
		mySql1.addSubPara(operator);//ָ������Ĳ���
		strSQL= mySql1.getString();
				 
    turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
    if (!turnPage.strQueryResult)
    {
         alert("δ��ѯ���������������ݣ�");
	 return false;
    }
    
    //���ò�ѯ��ʼλ��
    turnPage.pageIndex = 0;
    //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
    turnPage.pageLineNum = 30 ;
    //��ѯ�ɹ������ַ��������ض�ά����
    turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
    //���ó�ʼ������MULTILINE����
    turnPage.pageDisplayGrid = RBResultGrid;
    //����SQL���
    turnPage.strQuerySql = strSQL ;
    arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex);
    //����MULTILINE������ʾ��ѯ���
    displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
 
}


function ReturnData()
{
        var arrReturn = new Array();
	var tSel = CostDataKeyDefInputGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )

	    alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		
	   document.all('KeyID').value = arrResult[0][2];
	   document.all('KeyID1').value = arrResult[0][2];
	   document.all('KeyName').value = arrResult[0][3];
           document.all('KeyOrder').value = arrResult[0][4];
	   document.all('Remark').value = arrResult[0][5];            
		
	}
}





 