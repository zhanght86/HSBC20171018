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
    
    initLCInsureAccFeeGrid(); 
    initLCInsureAccClassFeeGrid();
  }
  catch(re)
  {
    alert("GpsLCInsureAccFeeQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


function initLCInsureAccFeeGrid()
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
      iArray[1][1]="120px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ʻ�����";         		//����
      iArray[2][1]="90px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[3]=new Array();
      iArray[3][0]="�ʻ�������";         		//����
      iArray[3][1]="90px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�ʻ�������";         		//����
      iArray[4][1]="90px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="����ѽ��";         		//����
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
      
      LCInsureAccFeeGrid = new MulLineEnter( "fm" , "LCInsureAccFeeGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LCInsureAccFeeGrid.mulLineCount = 5;   
      LCInsureAccFeeGrid.displayTitle = 1;
      LCInsureAccFeeGrid.locked = 1;
      LCInsureAccFeeGrid.canSel = 1;
      LCInsureAccFeeGrid.hiddenPlus = 1;
      LCInsureAccFeeGrid.hiddenSubtraction = 1;
      LCInsureAccFeeGrid.loadMulLine(iArray);  
 
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initLCInsureAccClassFeeGrid(){

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
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���Ѽƻ�����";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[3]=new Array();
      iArray[3][0]="��Ӧ��������";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="������������";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="�ʻ�������";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�ʻ�������";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[7]=new Array();
      iArray[7][0]="�ʻ���������";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="����ѽ��";         		//����
      iArray[8][1]="100px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
	  iArray[9][0]="����";         		
	  iArray[9][1]="50px";            		 
	  iArray[9][2]=60;            			
	  iArray[9][3]=2;              		
	  iArray[9][4]="Currency";              	  
	  iArray[9][9]="����|code:Currency";
      
      LCInsureAccClassFeeGrid = new MulLineEnter( "fm" , "LCInsureAccClassFeeGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      LCInsureAccClassFeeGrid.mulLineCount = 5;   
      LCInsureAccClassFeeGrid.displayTitle = 1;
      LCInsureAccClassFeeGrid.locked = 1;
      /*LCInsureAccClassFeeGrid.canSel = 1;*/
      LCInsureAccClassFeeGrid.hiddenPlus = 1;
      LCInsureAccClassFeeGrid.hiddenSubtraction = 1;
      LCInsureAccClassFeeGrid.loadMulLine(iArray);  
 
      }
      catch(ex)
      {
        alert(ex);
      }


}

</script>
