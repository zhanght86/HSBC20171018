<%
//�������ƣ�GrpPolQueryInit.jsp
//�����ܣ�
//�������ڣ�2003-03-14 
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">
function initForm()
{
  try
  {
    
    initLcInsureAccGrid(); 
    initLCInsureAccClassGrid();
  }
  catch(re)
  {
    alert("GpsaAccQueryinit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


function initLcInsureAccGrid()
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
      iArray[1][0]="�����ʻ�����";         		//����
      iArray[1][1]="150px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�����ʻ�����";         		//����
      iArray[2][1]="100px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[3]=new Array();
      iArray[3][0]="�ʻ�������";         		//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�ʻ�������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="�ʻ����";         		//����
      iArray[5][1]="100px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
	  iArray[6][0]="����";         		
	  iArray[6][1]="50px";            		 
	  iArray[6][2]=60;            			
	  iArray[6][3]=2;              		
	  iArray[6][4]="Currency";              	  
	  iArray[6][9]="����|code:Currency";
      
      LCInsureAccGrid = new MulLineEnter( "fm" , "LCInsureAccGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LCInsureAccGrid.mulLineCount = 1;   
      LCInsureAccGrid.displayTitle = 1;
      LCInsureAccGrid.locked = 1;
      LCInsureAccGrid.canSel = 1;
      LCInsureAccGrid.hiddenPlus = 1;
      LCInsureAccGrid.hiddenSubtraction = 1;
      LCInsureAccGrid.loadMulLine(iArray);  
 
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initLCInsureAccClassGrid(){

var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����ʻ�����";         		//����
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���Ѽƻ�����";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[3]=new Array();
      iArray[3][0]="���Ѽƻ�����";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="��Ӧ��������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="������������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�ʻ�������";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�ʻ�������";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="�ʻ���������";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="�ʻ����";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[10]=new Array();
	  iArray[10][0]="����";         		
	  iArray[10][1]="50px";            		 
	  iArray[10][2]=60;            			
	  iArray[10][3]=2;              		
	  iArray[10][4]="Currency";              	  
	  iArray[10][9]="����|code:Currency";
      
      LCInsureAccClassGrid = new MulLineEnter( "fm" , "LCInsureAccClassGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LCInsureAccClassGrid.mulLineCount = 1;   
      LCInsureAccClassGrid.displayTitle = 1;
      LCInsureAccClassGrid.locked = 1;
      LCInsureAccClassGrid.canSel = 1;
      LCInsureAccClassGrid.hiddenPlus = 1;
      LCInsureAccClassGrid.hiddenSubtraction = 1;
      LCInsureAccClassGrid.loadMulLine(iArray);  
 
      }
      catch(ex)
      {
        alert(ex);
      }


}

</script>
