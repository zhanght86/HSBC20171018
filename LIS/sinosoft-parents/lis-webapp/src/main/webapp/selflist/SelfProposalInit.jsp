<%
//�������ƣ�SelfProposalInputInit.jsp
//�����ܣ���������-�ͻ���Ϣ¼��
//�������ڣ�2008-03-05 
//������  ��zz
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox() 
{ 
  try 
  {  
                                   
	// ������Ϣ����
	document.all('CertifyNo').value=tCertifyNo;
	document.all("CValiDate").readOnly = true;


	// Ͷ������Ϣ����  
  	document.all( 'AppntName' ).value                = "";
  	document.all( 'AppntSex' ).value                 = "";
  	document.all( 'AppntBirthday' ).value            = "";
  	document.all( 'AppntIDType' ).value              = "";
  	document.all( 'AppntIDNo' ).value                = "";
  	document.all( 'AppntPostalAddress' ).value       = "";
  	document.all( 'AppntZipCode' ).value             = "";
  	document.all( 'AppntPhone' ).value              = "";
  	document.all( 'AppntEMail' ).value               = "";
  	document.all( 'AppntOccupationType' ).value      = "";
  	document.all( 'AppntOccupationCode' ).value      = "";
  	document.all( 'RelationToLCInsured' ).value      = "";
  	
  	//alert("��ʼ��Ͷ�������");
  	
  	// ��������Ϣ����  
  	document.all( 'LCInsuredName' ).value                = "";
  	document.all( 'LCInsuredSex' ).value                 = "";
  	document.all( 'LCInsuredBirthday' ).value            = "";
  	document.all( 'LCInsuredIDType' ).value              = "";
  	document.all( 'LCInsuredIDNo' ).value                = "";
  	document.all( 'LCInsuredPostalAddress' ).value       = "";
  	document.all( 'LCInsuredZipCode' ).value             = "";
  	document.all( 'LCInsuredPhone' ).value              = "";
  	document.all( 'LCInsuredEMail' ).value               = "";
  	document.all( 'LCInsuredOccupationType' ).value      = "";
  	document.all( 'LCInsuredOccupationCode' ).value      = "";

    //alert("��ʼ�����������");
    
  } 
  catch(ex)
  {
     alert("��SelfProposalInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  } 
  
}

                                       

//��ʼ������
function initForm() 
{
	initInpBox();
	initAppntCodeGrid();
	initLCInsuredCodeGrid();
}


//Ͷ����ְҵ����
function initAppntCodeGrid()
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          			//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="ְҵ���";    	//����
    iArray[1][1]="30px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="ְҵ����";         			//����
    iArray[2][1]="150px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="��ҵ����";         			//����
    iArray[3][1]="150px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="ְҵ����";         			//����
    iArray[4][1]="100px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    AppntCodeGrid = new MulLineEnter( "fm" , "AppntCodeGrid" );
    //LCInsuredCodeGrid.mulLineCount = 10;
    AppntCodeGrid.displayTitle = 1;
    AppntCodeGrid.canSel=1;
    AppntCodeGrid.locked = 1;
    AppntCodeGrid.selBoxEventFuncName = "afterAppntLOccupationSelect";
    AppntCodeGrid.loadMulLine(iArray);
    AppntCodeGrid.detailInfo="������ʾ��ϸ��Ϣ";
  }
  catch(ex)
  {
    alert(ex);
  }
}


//��������ְҵ����
function initLCInsuredCodeGrid()
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
    iArray[0][1]="30px";         			//�п�
    iArray[0][2]=10;          			//�����ֵ
    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[1]=new Array();
    iArray[1][0]="ְҵ���";    	//����
    iArray[1][1]="30px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[2]=new Array();
    iArray[2][0]="ְҵ����";         			//����
    iArray[2][1]="150px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
    iArray[3]=new Array();
    iArray[3][0]="��ҵ����";         			//����
    iArray[3][1]="150px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    iArray[4]=new Array();
    iArray[4][0]="ְҵ����";         			//����
    iArray[4][1]="100px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

    LCInsuredCodeGrid = new MulLineEnter( "fm" , "LCInsuredCodeGrid" );
    //LCInsuredCodeGrid.mulLineCount = 10;
    LCInsuredCodeGrid.displayTitle = 1;
    LCInsuredCodeGrid.canSel=1;
    LCInsuredCodeGrid.locked = 1;
    LCInsuredCodeGrid.selBoxEventFuncName = "afterLOccupationSelect";
    LCInsuredCodeGrid.loadMulLine(iArray);
    LCInsuredCodeGrid.detailInfo="������ʾ��ϸ��Ϣ";
  }
  catch(ex)
  {
    alert(ex);
  }
}


</script>
