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
<script>	
	var date = "<%=PubFun.getCurrentDate()%>";
</script>                            

<script language="JavaScript">

// �����ĳ�ʼ��
function initInpBox()
{ 

  try
  {                                   
     fm.Bdate.value = date; 	 
     fm.Edate.value = date;
     
  }
  catch(ex)
  {
    alert("FIDistillOtoFInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                            

function initForm()
{
  try
  {
    
    initInpBox();
 
    initRBResultGrid() ;
  }
  catch(re)
  {
    alert("FIDistillOtoFInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}
 

 
function initRBResultGrid()
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
      iArray[1][0]="�¼�����";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ݲɼ����κ�";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="����Ա";         		//����
      iArray[3][1]="50px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��������";         		//����
      iArray[4][1]="50px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="ִ��״̬";         		//����
      iArray[5][1]="40px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[6]=new Array();
      iArray[6][0]="������־";         		//����
      iArray[6][1]="40px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      RBResultGrid = new MulLineEnter( "document" , "RBResultGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
     RBResultGrid.mulLineCount = 5;   
      RBResultGrid.displayTitle = 1;
      RBResultGrid.locked = 1;
      RBResultGrid.canSel = 0;
      RBResultGrid.canChk = 0;
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
