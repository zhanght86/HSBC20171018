<%
//�������ƣ�NewSelfProposalInputInit.jsp
//�����ܣ�����������-�ͻ���Ϣ¼��
//�������ڣ�2010-01-25 
//������  ��yanglh
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
//  	document.all( 'RelationToLCInsured' ).value      = "";
  	
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
     alert("��NewSelfProposalInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  } 
  
}

                                       

//��ʼ������
function initForm() 
{
	initInpBox();
	initAppntCodeGrid();
	initLCInsuredCodeGrid();
	initLCInsuredGrid();
	initPeoples();
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


//�������˱�
function initLCInsuredGrid()
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
    iArray[1][0]="����";    	//����
    iArray[1][1]="80px";            		//�п�
    iArray[1][2]=100;            			//�����ֵ
    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		iArray[1][9]="����������|notnull&len<=20";     

    iArray[2]=new Array();
    iArray[2][0]="�Ա�";         			//����
    iArray[2][1]="150px";            		//�п�
    iArray[2][2]=100;            			//�����ֵ
    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[2][9]="�������Ա�|notnull&code:Sex";     
    
    iArray[3]=new Array();
    iArray[3][0]="��������";         			//����
    iArray[3][1]="150px";            		//�п�
    iArray[3][2]=100;            			//�����ֵ
    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 		iArray[3][9]="�����˳�������|notnull&date";
    
    iArray[4]=new Array();
    iArray[4][0]="֤������";         			//����
    iArray[4][1]="100px";            		//�п�
    iArray[4][2]=100;            			//�����ֵ
    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		iArray[4][9]="������֤������|code:IDType";
		
		iArray[5]=new Array();
    iArray[5][0]="֤������";         			//����
    iArray[5][1]="100px";            		//�п�
    iArray[5][2]=100;            			//�����ֵ
    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		iArray[5][9]="������֤������|len<=20";

		iArray[6]=new Array();
    iArray[6][0]="ְҵ���";         			//����
    iArray[6][1]="100px";            		//�п�
    iArray[6][2]=100;            			//�����ֵ
    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

		iArray[7]=new Array();
    iArray[7][0]="ְҵ����";         			//����
    iArray[7][1]="100px";            		//�п�
    iArray[7][2]=100;            			//�����ֵ
    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


		iArray[8]=new Array();
    iArray[8][0]="��ϵ��ַ";         			//����
    iArray[8][1]="100px";            		//�п�
    iArray[8][2]=100;            			//�����ֵ
    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		iArray[8][9]="��������ϵ��ַ|len<=80";
		
		iArray[9]=new Array();
    iArray[9][0]="��������";         			//����
    iArray[9][1]="100px";            		//�п�
    iArray[9][2]=100;            			//�����ֵ
    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		iArray[9][9]="��������������|zipcode";
		
		iArray[10]=new Array();
    iArray[10][0]="��ϵ�绰";         			//����
    iArray[10][1]="100px";            		//�п�
    iArray[10][2]=100;            			//�����ֵ
    iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    iArray[10][9]="��������ϵ�绰|len<=15";

    
    iArray[11]=new Array();
    iArray[11][0]="��������";         			//����
    iArray[11][1]="100px";            		//�п�
    iArray[11][2]=100;            			//�����ֵ
    iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������	
		iArray[11][9]="�����˵�������|len<=20";
		
		iArray[12]=new Array();
    iArray[12][0]="��Ͷ���˹�ϵ";         			//����
    iArray[12][1]="100px";            		//�п�
    iArray[12][2]=100;            			//�����ֵ
    iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������	
		iArray[12][9]="��Ͷ���˹�ϵ|CODE:Relation";
    LCInsuredGrid = new MulLineEnter( "fm" , "LCInsuredGrid" );
    //LCInsuredCodeGrid.mulLineCount = 10;
    LCInsuredGrid.displayTitle = 1;
    LCInsuredGrid.canSel=1;
    LCInsuredGrid.locked = 1;
    LCInsuredGrid.selBoxEventFuncName = "afterInsuredSelect";
    LCInsuredGrid.loadMulLine(iArray);
    LCInsuredGrid.hiddenPlus = 1;
    LCInsuredGrid.hiddenSubtraction = 1;
//    LCInsuredGrid.detailInfo="������ʾ��ϸ��Ϣ";
  }
  catch(ex)
  {
    alert(ex);
  }
}

//��ѯ����������
function initPeoples(){

	  var tCertifyCode = fm.CertifyNo.value.substring(2,8);
	  var tSql="select d.peoples3,plankind1 from ldplan d where d.contplancode in (select riskcode from lmcardrisk c where  portfolioflag=1 and c.certifycode='"+tCertifyCode+"') and d.portfolioflag=1";

    arrResult = easyExecSql(tSql, 1, 0);
    
    if (arrResult == null) 
    {
      alert("��Ʒ����û�������Ϣ�����ʵ");
  //    displayAppnt(new Array());
    } 
    else 
    {
      fm.Peoples.value=arrResult[0][0];
      fm.AmntRate.value=arrResult[0][1];
    }
}

</script>
