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
    alert("FinDistillRollBackInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("FinDistillRollBackInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
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
      iArray[1][0]="���κ�";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[2]=new Array();
      iArray[2][0]="�ɼ��¼�����";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=170;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  

      iArray[3]=new Array();
      iArray[3][0]="���ݲɼ�ʱ��";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=170;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  
       
      RBResultGrid = new MulLineEnter( "document" , "RBResultGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      RBResultGrid.mulLineCount = 5;   
      RBResultGrid.displayTitle = 1;
      RBResultGrid.locked = 1;
      RBResultGrid.canSel = 1;
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
