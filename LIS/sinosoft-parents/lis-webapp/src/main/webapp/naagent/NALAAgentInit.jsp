<%
//�������ƣ�LAAgentInput.jsp
//�����ܣ�
//�������ڣ�2002-08-16 15:39:06
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {      
    document.all('AgentCode').value = '';
    //document.all('Password').value = '';
    //document.all('EntryNo').value = '';
    document.all('Name').value = '';
    document.all('Sex').value = '';
    document.all('Birthday').value = '';
    document.all('NativePlace').value = '';
    document.all('Nationality').value = '';
    //document.all('Marriage').value = '';
    //document.all('CreditGrade').value = '';
    //document.all('HomeAddressCode').value = '';
    document.all('HomeAddress').value = '';
    //document.all('PostalAddress').value = '';
    document.all('ZipCode').value = '';
    document.all('Phone').value = '';
    document.all('BP').value = '';
    document.all('Mobile').value = '';
    document.all('EMail').value = '';
    //document.all('MarriageDate').value = '';
    document.all('IdType').value = '';
    document.all('IDNo').value = '';
    //document.all('Source').value = '';
    //document.all('BloodType').value = '';
    document.all('PolityVisage').value = '';
    document.all('Degree').value = '';
    document.all('GraduateSchool').value = '';
    document.all('Speciality').value = '';
    document.all('PostTitle').value = '';
    //document.all('ForeignLevel').value = '';
    //document.all('WorkAge').value = '';
    document.all('OldCom').value = '';
    document.all('OldOccupation').value = '';
    document.all('HeadShip').value = '';
    //document.all('RecommendAgent').value = '';
    //document.all('Business').value = '';
    //document.all('SaleQuaf').value = '';
    document.all('QuafNo').value = '';
    //document.all('QuafStartDate').value = '';
    document.all('QuafEndDate').value = '';
    document.all('DevNo1').value = '';
    //document.all('DevNo2').value = '';
    //document.all('RetainContNo').value = '';
    //document.all('AgentKind').value = '';
    //document.all('DevGrade').value = '';
    //document.all('InsideFlag').value = '';
    //document.all('FullTimeFlag').value = '';
    //document.all('NoWorkFlag').value = '';
    document.all('TrainPeriods').value = '';
    document.all('EmployDate').value = '';
    //document.all('InDueFormDate').value = '';
    //document.all('OutWorkDate').value = '';
    //document.all('Approver').value = '';
    //document.all('ApproveDate').value = '';
    document.all('AssuMoney').value = '';
    //document.all('AgentState').value = '01';  //��Ա״̬
    //document.all('QualiPassFlag').value = '';
    //document.all('SmokeFlag').value = '';
    document.all('RgtAddress').value = '';
    document.all('BankCode').value = '';
    document.all('BankAccNo').value = '';
    document.all('Remark').value = '';
    document.all('Operator').value = '';
    //������Ϣ
    document.all('UpAgent').value = '';
    document.all('GroupManagerName').value = '';
    document.all('DepManagerName').value = '';
    document.all('IntroAgency').value = '';
    document.all('BranchCode').value = '';
    document.all('ManageCom').value = '';
    document.all('AgentSeries').value = '';
    document.all('AgentGrade').value = '';
    document.all('AgentGrade1').value = '';
    document.all('RearAgent').value = '';
    document.all('RearDepartAgent').value = '';
    document.all('RearSuperintAgent').value = '';
    document.all('RearAreaSuperintAgent').value = '';
    document.all('hideBranchCode').value = '';
    //document.all('AgentGroup').value = '';
    //document.all('hideManageCom').value = '';
    document.all('ManagerCode').value = '';
    document.all('upBranchAttr').value = '';
    document.all('hideIsManager').value = 'false';
    document.all('BranchType').value = getBranchType();  
    //by jiaqiangli 2006-07-24
    //�����ڳ�ʼ��Ϣ�޸���NALAAgentEditInput1.jsp�����˴�jsp,����Ҳ����2���ؼ�
    document.all('DirManagerName').value = '';   
    document.all('MajordomoName').value = '';
  }
  catch(ex)
  {
    alert("��LAAgentInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=��&1=Ů&2=����");      
//    setOption("sex","0=��&1=Ů&2=����");        
//    setOption("reduce_flag","0=����״̬&1=�����");
//    setOption("pad_flag","0=����&1=�潻");   
  }
  catch(ex)
  {
    alert("��LAAgentInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initWarrantorGrid();    
  }
  catch(re)
  {
    alert("LAAgentInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}  
// ��������Ϣ�ĳ�ʼ��
function initWarrantorGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px"; 	           		//�п�
      iArray[0][2]=1;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="����";          		        //����
      iArray[1][1]="60px";      	      		//�п�
      iArray[1][2]=20;            			//�����ֵ
      iArray[1][3]=1;             //�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ����ѡ��3��ʾ���������ص�
      iArray[1][9]="����|NOTNULL";

      iArray[2]=new Array();
      iArray[2][0]="�Ա�";         		        //����
      iArray[2][1]="30px";            			//�п�
      iArray[2][2]=10;            			//�����ֵ
      iArray[2][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[2][4]="Sex";
      iArray[2][9]="�Ա�|NOTNULL&code:Sex";

      iArray[3]=new Array();
      iArray[3][0]="���֤����";      	   		//����
      iArray[3][1]="130px";            			//�п�
      iArray[3][2]=20;            			//�����ֵ
      iArray[3][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[3][9]="���֤����|NOTNULL";

      iArray[4]=new Array();
      iArray[4][0]="��λ";      	   		//����
      iArray[4][1]="110px";            			//�п�
      iArray[4][2]=30;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[4][9]="��λ|NOTNULL";
    
      iArray[5]=new Array();
      iArray[5][0]="��ͥ��ַ";      	   		//����
      iArray[5][1]="120px";            			//�п�
      iArray[5][2]=40;            			//�����ֵ
      iArray[5][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[5][9]="��ͥ��ַ|NOTNULL";

      iArray[6]=new Array();
      iArray[6][0]="�ֻ�";      	   		//����
      iArray[6][1]="80px";            			//�п�
      iArray[6][2]=20;            			//�����ֵ
      iArray[6][3]=1;  
      iArray[6][9]="�ֻ�|NOTNULL&NUM";
      
      iArray[7]=new Array();
      iArray[7][0]="��������";      	   		//����
      iArray[7][1]="60px";            			//�п�
      iArray[7][2]=20;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[7][9]="��������|NOTNULL&NUM&len=6";
  
      iArray[8]=new Array();
      iArray[8][0]="�绰";      	   		//����
      iArray[8][1]="80px";            			//�п�
      iArray[8][2]=20;            			//�����ֵ
      iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[8][9]="�绰|NOTNULL&NUM";
  
      iArray[9]=new Array();
      iArray[9][0]="��ϵ";      	   		//����
      iArray[9][1]="50px";            			//�п�
      iArray[9][2]=20;            			//�����ֵ
      iArray[9][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
      iArray[9][4]='relaseries';
      iArray[9][9]="��ϵ|NOTNULL&code:relaseries";
      

      WarrantorGrid = new MulLineEnter( "fm" , "WarrantorGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      WarrantorGrid.mulLineCount = 1;   
      WarrantorGrid.displayTitle = 1;
      //WarrantorGrid.locked=1;      
      WarrantorGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
