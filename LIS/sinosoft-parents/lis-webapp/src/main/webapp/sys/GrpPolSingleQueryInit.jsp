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

var GrpPolGrid;
var SingleInfoGrid;
                                   
function initForm()
{
  try
  {
    initGrpPolGrid();
    initSingleInfoGrid();
    document.all("divSingleInfo").style.display= "none";
   
    fm.RiskCode.CodeData=getRisk(); 
    
  }
  catch(re)
  {
    alert("GrpPolsingleQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

// ������Ϣ�б�ĳ�ʼ��
function initGrpPolGrid(){                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���˱�����";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���˿ͻ���";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=80;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      iArray[3]=new Array();
      iArray[3][0]="����";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����";         		//����
      iArray[4][1]="60px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="��Ч����";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=80;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

			iArray[6]=new Array();
      iArray[6][0]="��ֹ����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=80;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
       
      iArray[7]=new Array();
      iArray[7][0]="��������";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=80;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[8]=new Array();
      iArray[8][0]="�Ա�";         		//����
      iArray[8][1]="50px";            		//�п�
      iArray[8][2]=80;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[9]=new Array();
      iArray[9][0]="����";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="����";         		//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[11]=new Array();
      iArray[11][0]="����״̬";         		//����
      iArray[11][1]="60px";            		//�п�
      iArray[11][2]=200;            			//�����ֵ
      iArray[11][3]=0;
      
      iArray[13]=new Array();
	  iArray[13][0]="����";         		
	  iArray[13][1]="50px";            		 
	  iArray[13][2]=60;            			
	  iArray[13][3]=2;              		
	  iArray[13][4]="Currency";              	  
	  iArray[13][9]="����|code:Currency";
      
      //��ʾ��contno �ڲ���ѯ��polno
      //add by jiaqiangli 2009-10-30
      iArray[12]=new Array();
      iArray[12][0]="polno";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=200;            			//�����ֵ
      iArray[12][3]=0;  
        
       
        
      GrpPolGrid = new MulLineEnter( "document" , "GrpPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpPolGrid.mulLineCount = 5;   
      GrpPolGrid.displayTitle = 1;
      GrpPolGrid.locked = 1;
      GrpPolGrid.canSel = 1;
      GrpPolGrid.hiddenPlus = 1;
      GrpPolGrid.hiddenSubtraction = 1;
      GrpPolGrid.loadMulLine(iArray);
      GrpPolGrid.selBoxEventFuncName = "querySingleInfo"; 
      //��Щ����������loadMulLine����
      //GrpPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
// �ͻ���Ҫ��Ϣ�б�ĳ�ʼ��
function initSingleInfoGrid(){                               
    var iArray2 = new Array(); 
      try
      {
      iArray2[0]=new Array();
      iArray2[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray2[0][1]="30px";            		//�п�
      iArray2[0][2]=10;            			//�����ֵ
      iArray2[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray2[1]=new Array();
      iArray2[1][0]="���˿ͻ���";    
      iArray2[1][1]="100px";            		
      iArray2[1][2]=100;            			
      iArray2[1][3]=0;              			

      iArray2[2]=new Array();
      iArray2[2][0]="����";         		
      iArray2[2][1]="80px";            		
      iArray2[2][2]=80;            			
      iArray2[2][3]=0;              			

      
      iArray2[3]=new Array();
      iArray2[3][0]="�Ա�";         		
      iArray2[3][1]="40px";            		
      iArray2[3][2]=200;            			
      iArray2[3][3]=0;              			

      iArray2[4]=new Array();
      iArray2[4][0]="��������";         		
      iArray2[4][1]="80px";            		
      iArray2[4][2]=100;            			
      iArray2[4][3]=0;              			
      
      iArray2[5]=new Array();
      iArray2[5][0]="֤������";         		
      iArray2[5][1]="50px";            		
      iArray2[5][2]=80;            			
      iArray2[5][3]=0;              			
      
      iArray2[6]=new Array();
      iArray2[6][0]="֤������";         		
      iArray2[6][1]="120px";            	
      iArray2[6][2]=80;            		
      iArray2[6][3]=0;              			

      iArray2[7]=new Array();
      iArray2[7][0]="ְҵ����";         		
      iArray2[7][1]="80px";            		
      iArray2[7][2]=200;            			
      iArray2[7][3]=0;              			

      iArray2[8]=new Array();
      iArray2[8][0]="ְҵ���";         		
      iArray2[8][1]="40px";            		
      iArray2[8][2]=200;            			
      iArray2[8][3]=0;              			
      
      
      iArray2[9]=new Array();
      iArray2[9][0]="����";         		
      iArray2[9][1]="80px";            
      iArray2[9][2]=200;            			
      iArray2[9][3]=0; 
 
      iArray2[10]=new Array();
      iArray2[10][0]="����״��";       
      iArray2[10][1]="60px";            		
      iArray2[10][2]=200;            			
      iArray2[10][3]=0; 
        
      SingleInfoGrid = new MulLineEnter( "document" , "SingleInfoGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SingleInfoGrid.mulLineCount = 5;   
      SingleInfoGrid.displayTitle = 1;
      SingleInfoGrid.locked = 1;
      SingleInfoGrid.canSel = 0;
      SingleInfoGrid.hiddenPlus = 1;
      SingleInfoGrid.hiddenSubtraction = 1;
      SingleInfoGrid.loadMulLine(iArray2);  
      //��Щ����������loadMulLine����
      //GrpPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
