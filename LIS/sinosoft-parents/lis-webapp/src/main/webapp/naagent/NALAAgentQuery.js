//               ���ļ��а����ͻ�����Ҫ����ĺ������¼�

var showInfo;
var mDebug="0";
var arrDataSet;
var turnPage = new turnPageClass();
var tResourceName="naagent.NALAAgentQueryInputSql";

//�ύ�����水ť��Ӧ����
function submitForm()
{
  var i = 0;
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

  //showSubmitFrame(mDebug);
  fm.submit(); //�ύ
}


//�ύ�����,���������ݷ��غ�ִ�еĲ���
function afterSubmit( FlagStr, content )
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
    var urlStr="../common/jsp/MessagePage.jsp?picture=S&content=" + content ;  
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:350px");   
	var name='��ʾ';   //��ҳ���ƣ���Ϊ��; 
	var iWidth=550;      //�������ڵĿ��; 
	var iHeight=250;     //�������ڵĸ߶�; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //��ô��ڵĴ�ֱλ�� 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //��ô��ڵ�ˮƽλ�� 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

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
  	alert("��LAAgentQuery.js-->resetForm�����з����쳣:��ʼ���������!");
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
/*
function returnParent()
{
  var tRow=AgentGrid.getSelNo();
   if(tRow>0)
   {
     //�õ���ѡ�еļ�¼�ַ���
     tRow--;
     //alert(tRow);
     var str=document.all("LAAgentInformation"+tRow).value;
     var arrRecord = str.split("|");  //����ַ������γɷ��ص�����
     for(var i=0; i<63;i++)
     {
       if(arrRecord[i]=='null')
       {
         arrRecord[i]='';
       }
     }
     top.opener.document.all('AgentCode').value = arrRecord[0];
     top.opener.document.all('AgentGroup').value = arrRecord[1];
     top.opener.document.all('ManageCom').value = arrRecord[2];
     top.opener.document.all('Password').value = arrRecord[3];
     top.opener.document.all('EntryNo').value = arrRecord[4];
     top.opener.document.all('Name').value = arrRecord[5];
     top.opener.document.all('Sex').value = arrRecord[6];
     top.opener.document.all('Birthday').value = arrRecord[7];
     top.opener.document.all('NativePlace').value = arrRecord[8];
     top.opener.document.all('Nationality').value = arrRecord[9];
     top.opener.document.all('Marriage').value = arrRecord[10];
     top.opener.document.all('CreditGrade').value = arrRecord[11];
     top.opener.document.all('HomeAddressCode').value = arrRecord[12];
     top.opener.document.all('HomeAddress').value = arrRecord[13];
     top.opener.document.all('PostalAddress').value = arrRecord[14];
     top.opener.document.all('ZipCode').value = arrRecord[15];
     top.opener.document.all('Phone').value = arrRecord[16];
     top.opener.document.all('BP').value = arrRecord[17];
     top.opener.document.all('Mobile').value = arrRecord[18];
     top.opener.document.all('EMail').value = arrRecord[19];
     top.opener.document.all('MarriageDate').value = arrRecord[20];
     top.opener.document.all('IDNo').value = arrRecord[21];
     top.opener.document.all('Source').value = arrRecord[22];
     top.opener.document.all('BloodType').value = arrRecord[23];
     top.opener.document.all('PolityVisage').value = arrRecord[24];
     top.opener.document.all('Degree').value = arrRecord[25];
     top.opener.document.all('GraduateSchool').value = arrRecord[26];
     top.opener.document.all('Speciality').value = arrRecord[27];
     top.opener.document.all('PostTitle').value = arrRecord[28];
     top.opener.document.all('ForeignLevel').value = arrRecord[29];
     top.opener.document.all('WorkAge').value = arrRecord[30];
     top.opener.document.all('OldCom').value = arrRecord[31];
     top.opener.document.all('OldOccupation').value = arrRecord[32];
     top.opener.document.all('HeadShip').value = arrRecord[33];
     top.opener.document.all('RecommendAgent').value = arrRecord[34];
     top.opener.document.all('Business').value = arrRecord[35];
     top.opener.document.all('SaleQuaf').value = arrRecord[36];
     top.opener.document.all('QuafNo').value = arrRecord[37];
     top.opener.document.all('QuafStartDate').value = arrRecord[38];
     top.opener.document.all('QuafEndDate').value = arrRecord[39];
     top.opener.document.all('DevNo1').value = arrRecord[40];
     top.opener.document.all('DevNo2').value = arrRecord[41];
     top.opener.document.all('RetainContNo').value = arrRecord[42];
     top.opener.document.all('AgentKind').value = arrRecord[43];
     top.opener.document.all('DevGrade').value = arrRecord[44];
     top.opener.document.all('InsideFlag').value = arrRecord[45];
     top.opener.document.all('FullTimeFlag').value = arrRecord[46];
     top.opener.document.all('NoWorkFlag').value = arrRecord[47];
     top.opener.document.all('TrainDate').value = arrRecord[48];
     top.opener.document.all('EmployDate').value = arrRecord[49];
     top.opener.document.all('InDueFormDate').value = arrRecord[50];
     top.opener.document.all('OutWorkDate').value = arrRecord[51];
     top.opener.document.all('Approver').value = arrRecord[52];
     top.opener.document.all('ApproveDate').value = arrRecord[53];
     top.opener.document.all('AssuMoney').value = arrRecord[54];
     top.opener.document.all('AgentState').value = arrRecord[55];
     top.opener.document.all('QualiPassFlag').value = arrRecord[56];
     top.opener.document.all('SmokeFlag').value = arrRecord[57];
     top.opener.document.all('RgtAddress').value = arrRecord[58];
     top.opener.document.all('BankCode').value = arrRecord[59];
     top.opener.document.all('BankAccNo').value = arrRecord[60];
     top.opener.document.all('Remark').value = arrRecord[61];
     top.opener.document.all('Operator').value = arrRecord[62];
     //alert("Operateor:"+arrRecord[62]);
     
     top.close();
     top.opener.afterQuery();
     //top.location.href="./LAWarrantorQuery.jsp?AgentCode="+tAgentCode;
    }
   else
    alert("��ѡ���¼!");
}*/

function returnParent()
{
        var arrReturn = new Array();
	var tSel = AgentGrid.getSelNo();	
		
	if( tSel == 0 || tSel == null )
		//top.close();
		alert( "����ѡ��һ����¼���ٵ�����ذ�ť��" );
	else
	{
		
			try
			{	
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
	tRow = AgentGrid.getSelNo();
	//alert("111" + tRow);
	if( tRow == 0 || tRow == null || arrDataSet == null )
	  return arrSelected;
	
	arrSelected = new Array();
	
	var strSQL = "";
	//         77          78               79             80          81                       82        
	/*strSQL = "select a.*,c.BranchManager,b.IntroAgency,b.AgentSeries,b.AgentGrade1,(select branchattr from labranchgroup where a.BranchCode = AgentGroup and (state<>'1' or state is null)) bt,"
    //         83                 84           85            86                87           88
	       + "b.AscriptSeries,c.BranchLevel,c.upBranch,(select name from laagent where agentcode=c.BranchManager),c.upBranchAttr,a.BranchCode from LAAgent a,LATree b,LABranchGroup c where 1=1 "
	       + "and a.AgentCode = b.AgentCode and a.AgentGroup = c.AgentGroup and a.AgentCode='"+AgentGrid.getRowColData(tRow-1,1)+"' and (c.state<>'1' or c.state is null)";*/
	strSQL = wrapSql(tResourceName,"querysqldes1",[AgentGrid.getRowColData(tRow-1,1)]);
	//alert(strSQL);     
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
        //�ж��Ƿ��ѯ�ɹ�
       if (!turnPage.strQueryResult) {
         alert("��ѯʧ�ܣ�");
         return false;
       }
       //��ѯ�ɹ������ַ��������ض�ά����
       arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	
	return arrSelected;
}

// ��ѯ��ť
function easyQueryClick()
{
	// ��ʼ�����
	initAgentGrid();
	var tReturn = getManageComLimitlike("a.managecom");
	// ��дSQL���
	var strSQL = "";
	/*strSQL = "select a.agentcode,b.BranchAttr,a.managecom,a.name,a.idno,a.agentstate from LAAgent a,LABranchGroup b where 1=1 "
	         + "and (a.AgentState is null or a.AgentState < '03') and a.BranchCode = b.agentGroup  and (b.state<>'1' or b.state is null)"
	         + tReturn
	         + getWherePart('a.AgentCode','AgentCode','like')
	         + getWherePart('b.BranchAttr','AgentGroup')
	         + getWherePart('a.ManageCom','ManageCom','like')
	         + getWherePart('a.Name','Name')
	         //+ getWherePart('a.Password','Password')
	         //+ getWherePart('a.EntryNo','EntryNo')
	         + getWherePart('a.Sex','Sex')
	         + getWherePart('a.Birthday','Birthday')
	         + getWherePart('a.IDNo','IDNo')
	         + getWherePart('a.NativePlace','NativePlace')
	         + getWherePart('a.Nationality','Nationality')
	         //+ getWherePart('a.Source','Source')
	         //+ getWherePart('a.BloodType','BloodType')
	         //+ getWherePart('a.Marriage','Marriage')
	         //+ getWherePart('a.MarriageDate','MarriageDate')
	         //+ getWherePart('HomeAddressCode')
	         //+ getWherePart('HomeAddress')
	         //+ getWherePart('a.PostalAddress','PostalAddress')
	         + getWherePart('a.ZipCode','ZipCode')
	         + getWherePart('a.Phone','Phone')
	         + getWherePart('a.BP','BP')
	         + getWherePart('a.Mobile','Mobile')
	         + getWherePart('a.EMail','EMail')
	         + getWherePart('a.HeadShip','HeadShip')
	         //+ getWherePart('a.RecommendAgent','RecommendAgent')
	         //+ getWherePart('a.Business','Business')
	         //+ getWherePart('a.CreditGrade','CreditGrade')
	         //+ getWherePart('a.SaleQuaf','SaleQuaf')
	         + getWherePart('a.QuafNo','QuafNo')
	         + getWherePart('a.DevNo1','DevNo1')
	         + getWherePart('a.EmployDate','EmployDate')
	         + getWherePart('a.BranchType','BranchType');
	         //+ getWherePart('PolityVisage')
	         //+ getWherePart('Degree')
	         //+ getWherePart('GraduateSchool')
	         //+ getWherePart('Speciality')
	         //+ getWherePart('PostTitle')
	         //+ getWherePart('ForeignLevel')
	         //+ getWherePart('WorkAge','','','1')
	         //+ getWherePart('PolityVisage')
	         //+ getWherePart('Operator');	*/
	strSQL = wrapSql(tResourceName,"querysqldes2",[commonManageCom,document.all('AgentCode').value,document.all('AgentGroup').value
	                                               ,document.all('ManageCom').value,document.all('Name').value,document.all('Sex').value
	                                               ,document.all('Birthday').value,document.all('IDNo').value,document.all('NativePlace').value
	                                               ,document.all('Nationality').value,document.all('ZipCode').value,document.all('Phone').value
	                                               ,document.all('BP').value,document.all('Mobile').value,document.all('EMail').value
	                                               ,document.all('HeadShip').value,document.all('QuafNo').value,document.all('DevNo1').value
	                                               ,document.all('EmployDate').value,document.all('BranchType').value]);
     	//alert(strSQL); 
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 1, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
    alert("��ѯʧ�ܣ�");
    return false;
}

//��ѯ�ɹ������ַ��������ض�ά����
  arrDataSet = decodeEasyQueryResult(turnPage.strQueryResult);
  //tArr = decodeEasyQueryResult(turnPage.strQueryResult);
  turnPage.arrDataCacheSet = arrDataSet;
  //���ó�ʼ������MULTILINE����VarGridΪ�ڳ�ʼ��ҳ�ж����ȫ�ֱ���
  turnPage.pageDisplayGrid = AgentGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex       = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  //arrDataSet           = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  var tArr = new Array();
  tArr = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //����MULTILINE������ʾ��ѯ���
  
  //displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  displayMultiline(tArr, turnPage.pageDisplayGrid);
}