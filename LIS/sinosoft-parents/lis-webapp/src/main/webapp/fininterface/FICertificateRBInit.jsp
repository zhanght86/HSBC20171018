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
	 
  }
  catch(ex)
  {
    alert("FICertificateRBAppInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                            

function initForm()
{
  try
  {    
    initInpBox();
    initResultGrid();
    queryResultGrid();
  }
  catch(re)
  {
    alert("FICertificateRBAppInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
      iArray[1][0]="����������";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ƾ֤���ͱ��";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="ҵ�������";         		//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=0;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[4]=new Array();
      iArray[4][0]="ҵ�����";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=0;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="���ԭ������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=170;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="ϸ������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=170;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��������";         		//����
      iArray[7][1]="60px";            		//�п�
      iArray[7][2]=170;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="������";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=170;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="����״̬";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=170;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      RBResultGrid = new MulLineEnter( "document" , "RBResultGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      RBResultGrid.mulLineCount = 0;   
      RBResultGrid.displayTitle = 1;
      RBResultGrid.locked = 1;
      RBResultGrid.canSel = 1;
      RBResultGrid.canChk = 0;
      RBResultGrid.selBoxEventFuncName = "ReturnData";      
      RBResultGrid.hiddenSubtraction = 1;      
      RBResultGrid.hiddenPlus = 1;
      RBResultGrid.loadMulLine(iArray);  
            
     
      }
      catch(ex)
      {
        alert(ex);
      }
}
 
</script>
