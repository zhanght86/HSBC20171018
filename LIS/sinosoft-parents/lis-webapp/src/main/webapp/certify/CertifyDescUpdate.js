var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var tResourceName="certify.CertifyDescUpdateInputSql";
//Click�¼������������ѯ��ʱ�����ú���
function queryClick()
{
  initForm();			//�������ʼ��form�Ļ�������һ�β�ѯ������ˣ��ٴβ�ѯ��ʱ�������div��ʾ�Ͳ�������
  showDiv(divShow,"");
  showDiv(divCardRisk,"");
  /*
  var strSql = "select a.certifycode,a.certifyname,a.certifyclass,"
  +"(case a.certifyclass2 when '0' then 'Ͷ����' when '1' then '�б���' when '2' then '��ȫ��' when '3' then '������' when '4' then '������' when '5' then '����' when '6' then '��Ʒ˵����' when '7' then '����' end),"
  +"a.haveprice,a.havenumber,(case a.state when '0' then '����' else 'ͣ��' end),a.makedate from lmcertifydes a where 1 = 1 ";
  
  if(fm.CertifyName_1.value!=null && fm.CertifyName_1.value!=""){
    strSql+=" and a.CertifyName like '%%" +fm.CertifyName_1.value+ "%'";
  }  
  
  var sqlWhere = "" + getWherePart('a.CertifyCode', 'CertifyCode_1')
  				//+ getWherePart('a.CertifyName', 'CertifyName_1', 'like')
  				+ getWherePart('a.CertifyClass', 'CertifyClass_1')
  				+ getWherePart('a.CertifyClass2', 'CertifyClass2_1')
  				+ getWherePart('a.HaveNumber', 'HaveNumber_1')
  				+" order by a.certifycode";
  */
  var strSql = wrapSql(tResourceName,"lmcertifydes1",[trim(document.all('CertifyCode_1').value),document.all('CertifyClass_1').value
                                        ,document.all('CertifyClass2_1').value,document.all('HaveNumber_1').value,document.all('CertifyName_1').value]);
               
  turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 0, 1); 
  if (!turnPage.strQueryResult) {
 	 alert("û�з��������ĵ�֤��¼��");
     return false;
    }else{
  	  turnPage.pageDivName = "divCertifyDescGrid";
  	  turnPage.queryModal(strSql, CertifyDescGrid);
	}
}

function showDetail(parm1, parm2)
{
  //������Ϣ�����޸�
  var tCertifyCode = CertifyDescGrid.getRowColData(CertifyDescGrid.getSelNo()-1, 1);
  /*
  var sql="select a.certifycode,a.certifyname,a.state,a.havelimit,a.maxunit1,a.maxunit2,"
  	  +"a.havevalidate,a.validate1,a.validate2,a.note from lmcertifydes a where a.certifycode='"
  	  + tCertifyCode+"'";
  	  */
  var sql = wrapSql(tResourceName,"lmcertifydes2",[tCertifyCode]);
  var tResult = easyExecSql(sql);
  	document.all('CertifyCode').value = tResult[0][0];
    document.all('CertifyName').value = tResult[0][1];
    document.all('State').value = tResult[0][2];
    
    document.all('HaveLimit').value = tResult[0][3];
    document.all('MaxUnit1').value = tResult[0][4];    
    document.all('MaxUnit2').value = tResult[0][5];
    
    document.all('HaveValidate').value = tResult[0][6];
    document.all('Validate1').value = tResult[0][7];
    document.all('Validate2').value = tResult[0][8];    
    document.all('Note').value = tResult[0][9];
    showCodeName();
    
    //������Ϣ�����޸ģ���֤�����޸Ĺ켣
    /*
    var traceSql="select a.certifycode,a.certifyname,(case a.state when '0' then '����' else 'ͣ��' end),a.havelimit,a.maxunit1,a.maxunit2,"
  	  +"a.havevalidate,a.validate1,a.validate2,a.note,a.makedate from lmcertifydestrace a where 1 = 1 and a.certifycode='"
  	  + tCertifyCode+"'";*/
    var traceSql = wrapSql(tResourceName,"lmcertifydes3",[tCertifyCode]);
  	  turnPage2.strQueryResult  = easyQueryVer3(traceSql, 1, 0, 1); 
  	  turnPage2.pageDivName = "divCertifyDescTraceGrid";
  	  turnPage2.queryModal(traceSql, CertifyDescTraceGrid);
}

function clearData()
{
    document.all('CertifyCode_1').value = '';
    document.all('CertifyName_1').value = '';
    document.all('CertifyClass_1').value = '';
    document.all('CertifyClass2_1').value = '';
    document.all('HaveNumber_1').value = '';
    //document.all('HavePrice_1').value = '';
    initCertifyDescGrid();
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
   {
	  if(vertifyInput() == false)
	  {
		return;
	  }
    fm.OperateType.value = "UPDATE";
    var showStr="���ڸ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();   // showSubmitFrame(mDebug);    
    fm.action = './CertifyDescUpdateSave.jsp';
    document.getElementById("fm").submit(); //�ύ
    
  }else
  {
    fm.OperateType.value = "";
    alert("��ȡ�����޸Ĳ�����");
  }
}

function afterCodeSelect( cName, Filed)
{
  if(cName=='CertifyClassListNew')
  {
    displayMulLine();
  }else if( cName == 'HaveNumber' ) {
	displayNumberInfo();		
  }else if( cName == 'HaveLimit' ) {
	displayHaveLimit();		
  }else if( cName == 'HaveValidate' ) {
	displayHaveValidate();		
  }
}

function displayMulLine()
{
  var displayType = fm.CertifyClass.value;
  if(displayType=="D")
  {
    document.all('divShow').style.display="";
    document.all('divCardRisk').style.display="";
  }
  if(displayType=="P")
  {
    document.all('divShow').style.display="none";
    document.all('divCardRisk').style.display="none";
  }
}

function displayNumberInfo()
{
	var vHaveNumber = fm.HaveNumber.value;
	if( vHaveNumber == 'Y' ) {
		fm.CertifyLength.value = '';
		fm.CertifyLength.disabled = false;
	} else {
		fm.CertifyLength.value = 0;
		fm.CertifyLength.disabled = true;
	}
}

function displayHaveLimit()
{
	var vHaveLimit = fm.HaveLimit.value;
	if( vHaveLimit == 'Y' ) {
		fm.MaxUnit1.value = '5';
		fm.MaxUnit1.disabled = false;
		fm.MaxUnit2.value = '500';
		fm.MaxUnit2.disabled = false;
	} else {
		fm.MaxUnit1.value = '0';
		fm.MaxUnit1.disabled = true;
		fm.MaxUnit2.value = '0';
		fm.MaxUnit2.disabled = true;
	}
}

function displayHaveValidate()
{
	var vHaveValidate = fm.HaveValidate.value;
	if( vHaveValidate == 'Y' ) {
		fm.Validate1.value = '30';
		fm.Validate1.disabled = false;
		fm.Validate2.value = '90';
		fm.Validate2.disabled = false;
	} else {
		fm.Validate1.value = '0';
		fm.Validate1.disabled = true;
		fm.Validate2.value = '0';
		fm.Validate2.disabled = true;
	}
}

//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
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
}

//���ð�ť��Ӧ����,Form�ĳ�ʼ�������ڹ�����+Init.jsp�ļ���ʵ�֣�����������ΪinitForm()
function resetForm()
{
  try
  {
    initCardRiskGrid();
  }
  catch(re)
  {
    alert("��ʼ��ҳ��������ó���");
  }
}

//ȡ����ť��Ӧ����
function cancelForm()
{
    showDiv(operateButton,"true");
    showDiv(inputButton,"false");
}

//��ʾfrmSubmit��ܣ���������
function showSubmitFrame(cDebug)
{
  if(cDebug=="1")
  {
      parent.fraMain.rows = "0,0,0,0,*";
  }
   else
   {
      parent.fraMain.rows = "0,0,0,0,*";
   }
}

//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  showDiv(operateButton,"false");
  showDiv(inputButton,"true");
}

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  if (confirm("��ȷʵ���޸ĸü�¼��?"))
   {
		if(vertifyInput() == false)
		{
			return;
		}
    fm.OperateType.value = "UPDATE";
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
	showSubmitFrame(mDebug);
    
    fm.action = './CertifyDescSave.jsp';
    document.getElementById("fm").submit(); //�ύ
  }
  else
  {
    fm.OperateType.value = "";
    alert("��ȡ�����޸Ĳ�����");
  }
}

function deleteClick()
{
  alert("���޷�ִ��ɾ��������������");
  return ;
//  if (confirm("��ȷʵҪɾ���ü�¼��"))
//  {
//    if(fm.CertifyCode.value!=fm.CertifyCode_1.value)
//    {
//      alert("��֤����������޸ģ����޷�ִ��ɾ������������");
//      return;
//    }
//    fm.OperateType.value = "DELETE";
//    var i = 0;
//    var showStr="����ɾ�����ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ�����������Ľ���";
//    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//    showInfo=window.open(urlStr,"newwindow","height=250;width=550;top=0;left=0;toolbar=no;menubar=no;scrollbars=no;resizable=no;alwaysRaised:yes;");
//    showSubmitFrame(mDebug);
//    fm.action = './CertifyDescSave.jsp';
//
//    document.getElementById("fm").submit();//�ύ
//    resetForm();
//  }
//  else
//  {
//    fm.OperateType.value = "";
//    alert("���Ѿ�ȡ�����޸Ĳ�����");
//  }
}

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

//У������
function vertifyInput()
{
  if((fm.CertifyCode.value=="")||(fm.CertifyCode.value=="null"))
  {
    alert("����¼�뵥֤���룡����");
    return false;
  }
  if((fm.CertifyName.value=="")||(fm.CertifyName.value=="null"))
  {
    alert("����¼�뵥֤���ƣ�����");
    return false;
  }
  if((fm.HaveLimit.value=="")||(fm.HaveLimit.value=="null"))
  {
    alert("�Ƿ��������ã�����");
    return false;
  }
  if((fm.HaveValidate.value=="")||(fm.HaveValidate.value=="null"))
  {
    alert("�Ƿ���ʹ���ڣ�����");
    return false;
  } 
  return;
}
