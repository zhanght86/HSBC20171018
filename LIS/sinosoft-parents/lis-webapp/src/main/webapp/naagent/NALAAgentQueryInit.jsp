<%
//�������ƣ�NALAAgentQueryInit.js
//�����ܣ�
//�������ڣ�2003-01-13 15:31:09
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {    
    /*
    document.all('AgentCode').value = '';
    document.all('AgentGroup').value = '';
    document.all('ManageCom').value = '';
    document.all('Password').value = '';
    document.all('EntryNo').value = '';
    document.all('Name').value = '';
    document.all('Sex').value = '';
    document.all('Birthday').value = '';
    document.all('NativePlace').value = '';
    document.all('Nationality').value = '';
    document.all('Marriage').value = '';
    document.all('CreditGrade').value = '';
    document.all('HomeAddressCode').value = '';
    document.all('HomeAddress').value = '';
    document.all('PostalAddress').value = '';
    document.all('ZipCode').value = '';
    document.all('Phone').value = '';
    document.all('BP').value = '';
    document.all('Mobile').value = '';
    document.all('EMail').value = '';
    document.all('MarriageDate').value = '';
    document.all('IDNo').value = '';
    document.all('Source').value = '';
    document.all('BloodType').value = '';
    document.all('PolityVisage').value = '';
    document.all('Degree').value = '';
    document.all('GraduateSchool').value = '';
    document.all('Speciality').value = '';
    document.all('PostTitle').value = '';
    document.all('ForeignLevel').value = '';
    document.all('WorkAge').value = '';
    document.all('OldCom').value = '';
    document.all('OldOccupation').value = '';
    document.all('HeadShip').value = '';
    document.all('RecommendAgent').value = '';
    document.all('Business').value = '';
    document.all('SaleQuaf').value = '';
    document.all('QuafNo').value = '';
    document.all('QuafStartDate').value = '';
    document.all('QuafEndDate').value = '';
    document.all('DevNo1').value = '';
    document.all('DevNo2').value = '';
    document.all('RetainContNo').value = '';
    document.all('AgentKind').value = '';
    document.all('DevGrade').value = '';
    document.all('InsideFlag').value = '';
    document.all('FullTimeFlag').value = '';
    document.all('NoWorkFlag').value = '';
    document.all('TrainDate').value = '';
    document.all('EmployDate').value = '';
    document.all('InDueFormDate').value = '';
    document.all('OutWorkDate').value = '';
    document.all('Approver').value = '';
    document.all('ApproveDate').value = '';
    document.all('AssuMoney').value = '';
    document.all('AgentState').value = '';
    document.all('QualiPassFlag').value = '';
    document.all('SmokeFlag').value = '';
    document.all('RgtAddress').value = '';
    document.all('BankCode').value = '';
    document.all('BankAccNo').value = '';
    document.all('Remark').value = '';
    document.all('Operator').value = '';*/
    document.all('BranchType').value = top.opener.document.all('BranchType').value;
  }
  catch(ex)
  {
    alert("��NALAAgentQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

// ������ĳ�ʼ��
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
    alert("��NALAAgentQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initAgentGrid();
  }
  catch(re)
  {
    alert("NALAAgentQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
/************************************************************
 *               
 *���룺          û��
 *�����          û��
 *���ܣ�          ��ʼ��AgentGrid
 ************************************************************
 */
function initAgentGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="���";         //����
        iArray[0][1]="30px";         //����
        iArray[0][2]=100;         //����
        iArray[0][3]=0;         //����

        iArray[1]=new Array();
        iArray[1][0]="�����˱���";         //����
        iArray[1][1]="80px";         //���
        iArray[1][2]=100;         //��󳤶�
        iArray[1][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[2]=new Array();
        iArray[2][0]="���ۻ���";         //����
        iArray[2][1]="100px";         //���
        iArray[2][2]=100;         //��󳤶�
        iArray[2][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[3]=new Array();
        iArray[3][0]="�������";         //����
        iArray[3][1]="80px";         //���
        iArray[3][2]=100;         //��󳤶�
        iArray[3][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[4]=new Array();
        iArray[4][0]="����";         //����
        iArray[4][1]="100px";         //���
        iArray[4][2]=100;         //��󳤶�
        iArray[4][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

        iArray[5]=new Array();
        iArray[5][0]="֤������";         //����
        iArray[5][1]="120px";         //���
        iArray[5][2]=100;         //��󳤶�
        iArray[5][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����
        
        iArray[6]=new Array();
        iArray[6][0]="������״̬";         //����
        iArray[6][1]="50px";         //���
        iArray[6][2]=100;         //��󳤶�
        iArray[6][3]=0;         //�Ƿ�����¼�룬0--���ܣ�1--����

  
        AgentGrid = new MulLineEnter( "fm" , "AgentGrid" ); 

        //��Щ���Ա�����loadMulLineǰ
        AgentGrid.mulLineCount = 0;   
        AgentGrid.displayTitle = 1;
        AgentGrid.canSel=1;
        AgentGrid.canChk=0;
        AgentGrid.locked=1;
        AgentGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("��ʼ��AgentGridʱ����"+ ex);
      }
    }


</script>
