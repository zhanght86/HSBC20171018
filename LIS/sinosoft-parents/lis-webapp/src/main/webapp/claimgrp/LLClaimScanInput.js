//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var mDebug="0";
var mOperate="";
var showInfo;

//<addcode>############################################################//
var old_AgentGroup="";
var new_AgentGroup="";
//</addcode>############################################################//

window.onfocus=myonfocus;
//ʹ�ôӸô��ڵ����Ĵ����ܹ��۽�
function myonfocus()
{
  if(showInfo!=null)
  {
    try
    {
      showInfo.focus();  
    }
    catch(ex)
    {
      showInfo=null;
    } 
  }
}

//�ύ�����水ť��Ӧ����
function submitForm()
{
	if (mOperate=="")
	{
		addClick();
	}
  if (!beforeSubmit())
   return false;
  var i = 0;
  var showStr="���ڱ������ݣ������Ժ��Ҳ�Ҫ�޸���Ļ�ϵ�ֵ����������ҳ��";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
  var iWidth=550;      //�������ڵĿ��; 
  var iHeight=250;     //�������ڵĸ߶�; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();
	
  // fm.hideOperate.value=mOperate;
  //if (fm.hideOperate.value=="")
 // {
  //  alert("�����������ݶ�ʧ��");
 // }
  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
{
  mOperate=""
  showInfo.close();

  if (FlagStr == "Fail" )
  {             
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
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
    //parent.fraInterface.initForm();
//    showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
    var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
    var iWidth=550;      //�������ڵĿ��; 
    var iHeight=350;     //�������ڵĸ߶�; 
    var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
    var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
    showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

    showInfo.focus();

    initForm();
    //showDiv(operateButton,"true"); 
    //showDiv(inputButton,"false"); 
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
    alert("��LLClaimScanInput.js-->resetForm�����з����쳣:��ʼ���������!");
  }
} 

//ȡ����ť��Ӧ����
function cancelForm()
{
//  window.location="../common/html/Blank.html";
    showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
}
 

//�ύǰ��У�顢����  
function beforeSubmit()
{
  //��Ӳ���
  if(document.all('BussNo').value==null || trim(document.all('BussNo').value)=="")
  {
    alert("�������ⰸ���룡");
    return false;
  }

	if(document.all('SubType').value==null || trim(document.all('SubType').value)=="")
  {
    alert("�����뵥֤�������ͣ�");
    return false;
  }

  return true;
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


//Click�¼������������ͼƬʱ�����ú���
function addClick()
{
  //����������Ӧ�Ĵ���
  mOperate="INSERT||MAIN";
  //showDiv(operateButton,"false"); 
  //showDiv(inputButton,"true"); 
}           

//Click�¼�����������޸ġ�ͼƬʱ�����ú���
function updateClick()
{
  //����������Ӧ�Ĵ���
  if ((document.all("AgentCode").value==null)||(trim(document.all("AgentCode").value)==''))
    alert('���Ȳ�ѯ��Ҫ�޸ĵļ�¼��');
  else if ((document.all("Idx").value==null)||(trim(document.all("Idx").value)==''))
    alert('���Ȳ�ѯ��Ҫ�޸ĵļ�¼��');
  else
  {
    if (confirm("��ȷʵ���޸ĸü�¼��?"))
    {
      mOperate="UPDATE||MAIN";
      submitForm();
    }
    else
    {
      mOperate="";
      alert("��ȡ�����޸Ĳ�����");
    }
  }
}           

//Click�¼������������ѯ��ͼƬʱ�����ú���
function queryClick()
{
  //����������Ӧ�Ĵ���
  //mOperate="QUERY||MAIN";
  showInfo=window.open("./LATrainQuery.html");
}           

//Click�¼����������ɾ����ͼƬʱ�����ú���
function deleteClick()
{
  //����������Ӧ��ɾ������
  if ((document.all("AgentCode").value==null)||(document.all("AgentCode").value==''))
    alert('���Ȳ�ѯ��Ҫɾ���ļ�¼��');
  else if ((document.all("Idx").value==null)||(document.all("Idx").value==''))
    alert('���Ȳ�ѯ��Ҫɾ���ļ�¼��');
  else
  {
    if (confirm("��ȷʵ��ɾ���ü�¼��?"))
    {
      mOperate="DELETE||MAIN";  
      submitForm();
    }
    else
    {
      mOperate="";
      alert("��ȡ����ɾ��������");
    }
  }
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


//��֤ҵ��Ա����ĺ�����
function checkValid()
{ 

  var strSQL = "";
  var tBranchType = document.all('BranchType').value;
  if (getWherePart('AgentCode')!='')
  {
     strSQL = "select * from LAAgent where 1='1' "
	     + getWherePart('AgentCode')
	     +" and ((AgentState in ('01','02')) or (AgentState is null))"
	     +" and branchtype='"+tBranchType+"'";
     var strQueryResult = easyQueryVer3(strSQL, 1, 1, 1);
  }
  else
  {
    document.all('AgentCode').value = '';
    document.all('AgentGroup').value = "";
    document.all('ManageCom').value  = "";
    document.all('Name').value  = "";
    return false;
  }
  //�ж��Ƿ��ѯ�ɹ�
  if (!strQueryResult) {
    alert("�޴�ҵ��Ա��");
    document.all('AgentCode').value  = "";
    document.all('AgentGroup').value = "";
    document.all('ManageCom').value  = "";
    document.all('Name').value  = "";
    return false;
  }
  //��ѯ�ɹ������ַ��������ض�ά����
  //var arrDataSet = decodeEasyQueryResult(strQueryResult);
  var tArr = new Array();
  tArr = decodeEasyQueryResult(strQueryResult);
  
  //<rem>######//
  //document.all('AgentGroup').value = tArr[0][1];
  //</rem>######//
  document.all('Name').value = tArr[0][5];
  document.all('ManageCom').value = tArr[0][2];
  //�洢ԭAgentGroupֵ���Ա�����ʱʹ��  
  //<addcode>############################################################//
  old_AgentGroup=tArr[0][74];
  
  document.all('HiddenAgentGroup').value=tArr[0][1];
  
  strSQL_AgentGroup = "select trim(BranchAttr) from labranchgroup where 1=1 "
                      +"and AgentGroup='"+old_AgentGroup+"'"
     var strQueryResult_AgentGroup = easyQueryVer3(strSQL_AgentGroup, 1, 1, 1);
  //var arrDataSet_AgentGroup = decodeEasyQueryResult(strQueryResult_AgentGroup);
  var tArr_AgentGroup = new Array();
  tArr_AgentGroup = decodeEasyQueryResult(strQueryResult_AgentGroup);
  //�Ա���ʾʱʹ��
  document.all('AgentGroup').value = tArr_AgentGroup[0][0];
  new_AgentGroup=tArr_AgentGroup[0][0];
    //alert(new_AgentGroup);
  //</addcode>############################################################//
}


//������ʾ���ص�ѡ��
function afterQuery(arrQueryResult)
{	
  var arrResult = new Array();
  if( arrQueryResult != null )
  {
    arrResult = arrQueryResult;
    document.all('AgentCode').value = arrResult[0][0];
    document.all('Name').value = arrResult[0][27];
    document.all('AgentGroup').value = arrResult[0][22]
    document.all('ManageCom').value = arrResult[0][2];
    document.all('Idx').value = arrResult[0][3];
    document.all('AClass').value = arrResult[0][4];
    document.all('TrainUnit').value = arrResult[0][5];
    document.all('TrainName').value = arrResult[0][6];
    document.all('Charger').value = arrResult[0][7];
    document.all('ResultLevel').value = arrResult[0][8];
    document.all('Result').value = arrResult[0][9];
    document.all('TrainPassFlag').value = arrResult[0][10];
    document.all('TrainStart').value = arrResult[0][11];
    document.all('TrainEnd').value = arrResult[0][12];
    document.all('Operator').value = arrResult[0][13];
    document.all('DoneDate').value = arrResult[0][14];
    document.all('DoneFlag').value = arrResult[0][15];
    document.all('Noti').value = arrResult[0][16];
    
    //<addcode>############################################################//
    document.all('HiddenAgentGroup').value=arrResult[0][1];
    //</addcode>############################################################//
    
    showOneCodeName('trainaclass','AClass','AClassName');
    showOneCodeName('ResultLevel','ResultLevel','ResultLevelName');
    showOneCodeName('yesno','TrainPassFlag','TrainPassFlagName');
                                                                                                                                                                                                                                      	
  }
    
}


