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
    alert("FIDistillCertificateInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                            

function initForm()
{
  try
  {   
    initInpBox();
    initResultGrid();
  }
  catch(re)
  {
    alert("FIDistillCertificateInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
      iArray[1][0]="���κ�";         		//����
      iArray[1][1]="70px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";      	   		//����
      iArray[2][1]="100px";            			//�п�
      iArray[2][2]=10;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[3]=new Array();
      iArray[3][0]="��������";      	   		//����
      iArray[3][1]="130px";            			//�п�
      iArray[3][2]=10;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
    
      iArray[4]=new Array();
      iArray[4][0]="����Ա";      	   		//����
      iArray[4][1]="60px";            			//�п�
      iArray[4][2]=10;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      ResultGrid = new MulLineEnter( "document" , "ResultGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
     ResultGrid.mulLineCount = 5;   
      ResultGrid.displayTitle = 1;
      ResultGrid.locked = 1;
      ResultGrid.canSel = 1;
      ResultGrid.canChk = 0;
      ResultGrid.hiddenSubtraction = 1;
      ResultGrid.hiddenPlus = 1;
      ResultGrid.loadMulLine(iArray);  
      

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
