<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	//�������ƣ�UWAutoInit.jsp
	//�����ܣ������Զ��˱�
	//�������ڣ�2002-06-19 11:10:36
	//������  ��WHN
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
	// ������ѯ����
    document.all('ManageCom').value = '';
    document.all('ContNo').value = '';
    document.all('AgentCode').value = '';
    document.all('RiskCode').value = '';
    document.all('SecPayMode').value = '';
    document.all('ContType').value = '';
  }
  catch(ex)
  {
    alert("��ProposalQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��ProposalQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                        

function initForm()
{
  try
  {
   initInpBox();
   //initContGrid();
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initContGrid()
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
      iArray[1][0]="��������";         		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	
	  iArray[2]=new Array();
	  iArray[2][0]="Ͷ����";         		//����
	  iArray[2][1]="160px";            		//�п�
	  iArray[2][2]=100;            			//�����ֵ
	  iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      ContGrid = new MulLineEnter( "fm" , "ContGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      ContGrid.mulLineCount = 0;   
      ContGrid.displayTitle = 1;
      ContGrid.locked = 1;
      ContGrid.canSel = 0;
      ContGrid.canChk = 0;
      ContGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
