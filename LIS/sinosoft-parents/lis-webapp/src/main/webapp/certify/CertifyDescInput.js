var showInfo;
var mDebug="1";
var turnPage = new turnPageClass();
var turnPage2 = new turnPageClass();
var tResourceName="certify.CertifyDescInputSql";
//Click�¼������������ѯ��ʱ�����ú���
function queryClick()
{
  initForm();
  /*
  var strSql = "select a.certifycode,a.certifyname,a.certifyclass,"
  +" (case a.certifyclass2 when '0' then 'Ͷ����' when '1' then '�б���' when '2' then '��ȫ��' when '3' then '������' when '4' then '������' when '5' then '����' when '6' then '��Ʒ˵����' when '7' then '����' end),"
  +" a.haveprice,a.havenumber,(case a.state when '0' then '����' else 'ͣ��' end) "
  +" from lmcertifydes a where 1 = 1 "
  + getWherePart('a.CertifyCode', 'CertifyCode_1')
  + getWherePart('a.CertifyClass', 'CertifyClass_1')
  + getWherePart('a.CertifyClass2', 'CertifyClass2_1')
  + getWherePart('a.HaveNumber', 'HaveNumber_1');
  				
  if(fm.CertifyName_1.value!=null && fm.CertifyName_1.value!=""){
    strSql+=" and a.CertifyName like '%%" +fm.CertifyName_1.value+ "%'";
  }      
  strSql+=" order by a.certifycode";
  */
  var strSql = wrapSql(tResourceName,"lmcertifydes1",[trim(document.all('CertifyCode_1').value),document.all('CertifyClass_1').value
                           ,document.all('CertifyClass2_1').value,document.all('HaveNumber_1').value,document.all('CertifyName_1').value]);
  
  turnPage.pageDivName = "divCertifyDescGrid";
  turnPage.queryModal(strSql, CertifyDescGrid);
  if(CertifyDescGrid.mulLineCount<=0){
 	 alert("û�з��������ĵ�֤��¼��");
     return false;  
  }  	   				
}

function showDetail(parm1, parm2)
{
  var tCertifyCode = CertifyDescGrid.getRowColData(CertifyDescGrid.getSelNo()-1, 1);
  /*
  var sql="select a.certifycode,a.certifyname,a.certifyclass,a.certifyclass2,a.haveprice,a.havenumber,"
  	  +"a.certifylength,a.tackbackflag,a.certifyprice,a.unit,a.maxprintno,a.havelimit,a.maxunit1,a.maxunit2,"
  	  +"a.havevalidate,a.validate1,a.validate2,a.note from lmcertifydes a where a.certifycode='"
  	  + tCertifyCode+"'";
  //prompt("",sql);
  */
  var sql = wrapSql(tResourceName,"lmcertifydes2",[tCertifyCode]);
               
  var tResult = easyExecSql(sql);
  	document.all('CertifyCode').value = tResult[0][0];
    document.all('CertifyName').value = tResult[0][1];
    document.all('CertifyClass').value = tResult[0][2];
    document.all('CertifyClass2').value = tResult[0][3];
    //document.all('HavePrice').value = tResult[0][4];    
    document.all('HaveNumber').value = tResult[0][5];

    document.all('CertifyLength').value = tResult[0][6];
    document.all('TackBackFlag').value = tResult[0][7];
    document.all('CertifyPrice').value = tResult[0][8];
    document.all('Unit').value = tResult[0][9];
    document.all('MaxPrintNo').value = tResult[0][10];
    document.all('HaveLimit').value = tResult[0][11];
    document.all('MaxUnit1').value = tResult[0][12];
    document.all('MaxUnit2').value = tResult[0][13];
    
    document.all('HaveValidate').value = tResult[0][14];
    document.all('Validate1').value = tResult[0][15];
    document.all('Validate2').value = tResult[0][16];
    document.all('Note').value = tResult[0][17];
    showCodeName();
/**  var displayType = fm.CertifyClass.value;
  if(displayType=="D")
  {
    document.all('divShow').style.display="";
    document.all('divCardRisk').style.display="";
  }else{
    document.all('divShow').style.display="none";
    document.all('divCardRisk').style.display="none";
  }
  
    if(tResult[0][2]=="D"){      
      var riskSql="select a.riskcode,a.prem,a.premprop,a.premlot from lmcardrisk a "
      		+" where a.certifycode='" + tCertifyCode+"'";
  	  turnPage2.strQueryResult  = easyQueryVer3(riskSql, 1, 0, 1); 
      if (!turnPage2.strQueryResult) {
      	initCardRiskGrid();
 	    alert("û�з��������Ķ��������Ϣ��"); 	    
        return false;
      }else{
  	    turnPage2.pageDivName = "divCardRiskGrid";
  	    turnPage2.queryModal(riskSql, CardRiskGrid);
	  }
    }**/
}

function clearData()
{
    document.all('CertifyCode_1').value = '';
    document.all('CertifyName_1').value = '';
    document.all('CertifyClass_1').value = '';
    document.all('CertifyClass2_1').value = '';
    document.all('HaveNumber_1').value = '';
    initCertifyDescGrid();
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
  if(verifyInput() == false)
  {
	return;
  }
//  if(fm.CertifyClass.value=='D' && CardRiskGrid.mulLineCount==0){
//    alert("��Ҫ�м۵�֤��¼��������Ϣ��");
//    return false;
//  }
  if(fm.HaveLimit.value=='Y' && (fm.MaxUnit1.value=='' || fm.MaxUnit2.value=='')){
    alert("��¼����������������");
    return false;
  }  
  if(fm.HaveValidate.value=='Y' && (fm.Validate1.value=='' || fm.Validate2.value=='')){
    alert("��¼����Чʹ�����ޣ�");
    return false;
  }
  if((fm.CertifyClass.value=='D'||fm.CertifyClass.value=='B') && fm.TackBackFlag.value=='N'){
    alert("��Ҫ�м۵�֤/��Ҫ�հ׵�֤���Ƿ���ձ�־����ΪN��������¼�룡");
    return false;
  }        
  
  fm.OperateType.value = "INSERT";
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
   var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();  
	fm.action = './CertifyDescSave.jsp';
  document.getElementById("fm").submit(); //�ύ
}

function afterCodeSelect( cName, Filed)
{
  if(cName=='CertifyClass')
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
  }else{
    document.all('divShow').style.display="none";
    document.all('divCardRisk').style.display="none";
  }
  if(displayType=="D" || displayType=="B")
  {
    document.all('TackBackFlag').value="Y";
    document.all('TackBackFlagName').value="��Ҫ����";
  }else{
    document.all('TackBackFlag').value="";
    document.all('TackBackFlagName').value="";
  } 
   
}

function displayNumberInfo()
{
	var vHaveNumber = fm.HaveNumber.value;
	if( vHaveNumber == 'Y' ) {
		fm.CertifyLength.value = '';
		fm.CertifyLength.readOnly = false;
	} else {
		fm.CertifyLength.value = 18;
		fm.CertifyLength.readOnly = true;
	}
}

function displayHaveLimit()
{
	var vHaveLimit = fm.HaveLimit.value;
	if( vHaveLimit == 'Y' ) {
		fm.MaxUnit1.value = '5';
		fm.MaxUnit1.readOnly = false;
		fm.MaxUnit2.value = '500';
		fm.MaxUnit2.readOnly = false;
	} else {
		fm.MaxUnit1.value = '0';
		fm.MaxUnit1.readOnly = true;
		fm.MaxUnit2.value = '0';
		fm.MaxUnit2.readOnly = true;
	}
}

function displayHaveValidate()
{
	var vHaveValidate = fm.HaveValidate.value;
	if( vHaveValidate == 'Y' ) {
		fm.Validate1.value = '30';
		fm.Validate1.readOnly = false;
		fm.Validate2.value = '90';
		fm.Validate2.readOnly = false;
	} else {
		fm.Validate1.value = '0';
		fm.Validate1.readOnly = true;
		fm.Validate2.value = '0';
		fm.Validate2.readOnly = true;
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
    initForm();
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
		if(verifyInput() == false)
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
/**function vertifyInput()
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
  if((fm.CertifyClass.value=="")||(fm.CertifyClass.value=="null"))
  {
    alert("����¼�뵥֤���ͣ�����");
    return false;
  }
  if((fm.CertifyLength.value=="")||(fm.CertifyLength.value=="null"))
  {
    alert("����¼�뵥֤���볤�ȣ�����");
    return false;
  }
  if((fm.Note.value=="")||(fm.Note.value=="null"))
  {
    alert("����¼��ע����Ϣ������");
    return false;
  }  
  return;
}**/

