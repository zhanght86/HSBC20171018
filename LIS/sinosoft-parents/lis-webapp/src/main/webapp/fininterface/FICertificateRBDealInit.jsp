 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ� 
//�����ܣ� 
//�������ڣ� 
//������  jw
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
     document.all('BusinessNo').value =  BusinessNo;
     document.all('AppNo').value =  AppNo;     
     document.all('CertificateId').value =  CertificateId;	 
  }
  catch(ex)
  {
    alert("FICertificateRBDealInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                            

function initForm()
{
  try
  {
    
    initInpBox();
    initResultGrid();
    queryRBResultGrid();


  }
  catch(re)
  {
    alert("FICertificateRBDealInit.jsp-->InitForm�����з����쳣:��ʼ���������!" );
    alert(re);
  }
}
 
function initResultGrid()
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
      iArray[1][0]="ҵ�����";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ƾ֤���ͱ��";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=200;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="ƾ֤���";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
 
      iArray[5]=new Array();
      iArray[5][0]="�������κ�";         		//����
      iArray[5][1]="120px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�������";         		//����
      iArray[6][1]="120px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="ƾ֤����";         		//����
      iArray[7][1]="120px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      
      RBDealResultGrid = new MulLineEnter( "document" , "RBDealResultGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      RBDealResultGrid.mulLineCount = 1;   
      RBDealResultGrid.displayTitle = 1;
      RBDealResultGrid.locked = 1;
      RBDealResultGrid.canSel = 1;
      RBDealResultGrid.canChk = 0;
      RBDealResultGrid.hiddenSubtraction = 1;
      RBDealResultGrid.hiddenPlus = 1;
      RBDealResultGrid.loadMulLine(iArray);  
      
    }
    catch(ex)
    {
       alert(ex);
    }
}


</script>
