<%
//�������ƣ�PEdorTypeRFInit.jsp
//�����ܣ�
//�������ڣ�2003-01-08 
//������  ��Minim
//������  ��Nicholas
//�������ڣ�2005-07-09
%>                          

<script language="JavaScript">
	
function initInpBox()
{ 
  try
  {  
    Edorquery();  
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('PolNo').value = top.opener.document.all('PolNo').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    //document.all('ContType').value ="1";
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("��ʼ���������!");
  }      
}


function initForm()
{
 
  try
  {
    initInpBox();
    initCustomerGrid();
    ShowCustomerInfo();
    initLoanGrid();
  }
  catch(re)
  {
    alert("��ʼ���������!");
  }
}


function QueryCustomerInfo()
{
	var tContNo=top.opener.document.all('ContNo').value;
	var strSQL;

	if(tContNo != null && tContNo != "")
	{
// 	  strSQL = "SELECT AppntName,(select CodeName from LDCode where CodeType='idtype' and Code=a.AppntIDType and rownum=1),AppntIDNo,"
// 	               + " InsuredName,(select CodeName from LDCode where CodeType='idtype' and Code=a.InsuredIDType and rownum=1),InsuredIDNo"
// 	        + " FROM LCCont a WHERE ContNo='" + tContNo + "'";
	  var sqlid1="PEdorTypeRFInputSql3";
	  var mySql1=new SqlClass();
	  mySql1.setResourceName("bq.PEdorTypeRFInputSql"); //ָ��ʹ�õ�properties�ļ���
	  mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
	  mySql1.addSubPara(tContNo);//ָ������Ĳ���
	  strSQL=mySql1.getString();
	}
	else
	{
		alert('��ȡ������ʧ�ܣ�');
	}

	var arrSelected = new Array();	
	turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);	

  if(!turnPage.strQueryResult)
  {
		alert("��ѯ�ͻ�������Ϣʧ�ܣ�");
	}
	else
	{
	  arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
	  try {document.all('AppntName').value = arrSelected[0][0];} catch(ex) { }; //Ͷ��������
	  try {document.all('AppntIDType').value = arrSelected[0][1];} catch(ex) { }; //Ͷ����֤������
	  try {document.all('AppntIDNo').value = arrSelected[0][2];} catch(ex) { }; //Ͷ����֤������ 
	  try {document.all('InsuredName').value = arrSelected[0][3];} catch(ex) { }; //����������
	  try {document.all('InsuredIDType').value = arrSelected[0][4];} catch(ex) { }; //������֤������
	  try {document.all('InsuredIDNo').value = arrSelected[0][5];} catch(ex) { }; //������֤������ 
	  
  }
}


//��ѯ���ʼ��
function afterCount(tFlagStr,tContent,tMulArray)
{
		if (tFlagStr == "Success")
		{
        displayMultiline(tMulArray,LoanGrid,turnPage);
    }
    else
    {
		  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + tContent ;  
		  showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:300px");   
    }
     getLoanInfo();

}

function initCustomerGrid()
{                               
    var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="�ͻ�����";
      iArray[1][1]="120px";
      iArray[1][2]=100;
      iArray[1][3]=0;
      
       iArray[2]=new Array();
      iArray[2][0]="��ɫ";
      iArray[2][1]="80px";
      iArray[2][2]=100;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="����";
      iArray[3][1]="80px";
      iArray[3][2]=100;
      iArray[3][3]=0;
      
      iArray[4]=new Array();
      iArray[4][0]="֤������";
      iArray[4][1]="80px";
      iArray[4][2]=100;
      iArray[4][3]=0;
      
      iArray[5]=new Array();
      iArray[5][0]="֤������";
      iArray[5][1]="120px";
      iArray[5][2]=100;
      iArray[5][3]=0;        
      
      iArray[6]=new Array();
      iArray[6][0]="�Ա�";
      iArray[6][1]="60px";
      iArray[6][2]=100;
      iArray[6][3]=0;
      
      iArray[7]=new Array();
      iArray[7][0]="��������";
      iArray[7][1]="100px";
      iArray[7][2]=100;
      iArray[7][3]=8;
 
      CustomerGrid = new MulLineEnter( "fm" , "CustomerGrid" );   
      CustomerGrid.displayTitle = 1;
      CustomerGrid.canSel=0;       
      CustomerGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      CustomerGrid.hiddenSubtraction=1;
      CustomerGrid.loadMulLine(iArray);
      CustomerGrid.selBoxEventFuncName ="" ; 
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initLoanGrid()
{                               
    var iArray = new Array();
      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="������";
      iArray[1][1]="100px";
      iArray[1][2]=100;
      iArray[1][3]=0;
      
      iArray[2]=new Array();
      iArray[2][0]="�������";
      iArray[2][1]="80px";
      iArray[2][2]=100;
      iArray[2][3]=8;
      
      iArray[3]=new Array();
      iArray[3][0]="��ٻ�������";
      iArray[3][1]="80px";
      iArray[3][2]=100;
      iArray[3][3]=8;
      
      iArray[4]=new Array();
      iArray[4][0]="�����";
      iArray[4][1]="80px";
      iArray[4][2]=100;
      iArray[4][3]=7; 
      iArray[4][23]="0";       
  
      iArray[5]=new Array();
      iArray[5][0]="�����Ϣ";
      iArray[5][1]="80px";
      iArray[5][2]=100;
      iArray[5][3]=7;   
      iArray[5][23]="0";
      
          
      iArray[6]=new Array();
      iArray[6][0]="��Ϣ��ʽ";
      iArray[6][1]="80px";
      iArray[6][2]=100;
      iArray[6][3]=0;
             
      iArray[7]=new Array();
      iArray[7][0]="���λ���";
      iArray[7][1]="100px";
      iArray[7][2]=100;
      iArray[7][3]=7;   
      iArray[7][23]="1";
   
      iArray[8]=new Array();
      iArray[8][0]="���ձ�����";
      iArray[8][1]="0px";
      iArray[8][2]=100;
      iArray[8][3]=1;   
      
      iArray[9]=new Array();
      iArray[9][0]="����";
      iArray[9][1]="60px";
      iArray[9][2]=100;
      iArray[9][3]=2;
      iArray[9][4]="currency";   

      LoanGrid = new MulLineEnter( "fm" , "LoanGrid" );  
      LoanGrid.displayTitle = 1;
      LoanGrid.canSel=0;       
      LoanGrid.canChk=1;
      LoanGrid.hiddenPlus=1;   //�Ƿ�����"+"�ű�־��1Ϊ���أ�0Ϊ������(ȱʡֵ)
      LoanGrid.hiddenSubtraction=1;
      LoanGrid.loadMulLine(iArray);
      LoanGrid.selBoxEventFuncName ="" ;          
      //��Щ����������loadMulLine����
      fm.action="./PEdorTypeRFCount.jsp";
     fm.submit();
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>