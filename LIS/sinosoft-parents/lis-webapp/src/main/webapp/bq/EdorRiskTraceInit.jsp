<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorRiskTraceInit.jsp
//�����ܣ����ֺ˱��켣��ѯ
//�������ڣ�2005-07-13 11:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
     //���ҳ��ؼ��ĳ�ʼ����
%>     
<script language="JavaScript">

function initForm()
{
  try  
  {
    
  	//��ʼ���������ֺ˱��켣multiline
  	initRiskTraceGrid();  
    
	//��ѯ�˱��켣
	queryRiskTrace();   
	  
  } 
  catch(re) {
    alert("EdorRiskTraceInit.jsp-->InitForm�����з����쳣:��ʼ���������!"); 
  }
}


//���ֺ˱��켣�б�ĳ�ʼ��
function initRiskTraceGrid(){
    var iArray = new Array();
       
      try 
      {   
          iArray[0]=new Array();
          iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
          iArray[0][1]="40px";            		//�п�
          iArray[0][2]=10;            			//�����ֵ
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[1]=new Array();
          iArray[1][0]="���ֱ�����";         		//���� 
          iArray[1][1]="0px";            		//�п�
          iArray[1][2]=100;            			//�����ֵ
          iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  
          
          iArray[2]=new Array();
          iArray[2][0]="����Ա";         		//����
          iArray[2][1]="220px";            		//�п�
          iArray[2][2]=100;            			//�����ֵ
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
          
          iArray[3]=new Array();
          iArray[3][0]="��������";         		//����
          iArray[3][1]="220px";            		//�п�
          iArray[3][2]=200;            			//�����ֵ
          iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
          
          iArray[4]=new Array();
          iArray[4][0]="�˱����۴���";         		//����
          iArray[4][1]="220px";            		//�п�
          iArray[4][2]=80;            			//�����ֵ
          iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
          
     
          iArray[5]=new Array();
          iArray[5][0]="�˱�����";         		//����
          iArray[5][1]="220px";            		//�п�
          iArray[5][2]=200;            			//�����ֵ
          iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
          
        
       
          RiskTraceGrid = new MulLineEnter( "fm" , "RiskTraceGrid" ); 

          //��Щ���Ա�����loadMulLineǰ
          
          RiskTraceGrid.mulLineCount = 10;     
          RiskTraceGrid.displayTitle = 1;
          RiskTraceGrid.locked = 1;
          RiskTraceGrid.canSel = 0;
          RiskTraceGrid.hiddenPlus = 1;
          RiskTraceGrid.hiddenSubtraction = 1;
          RiskTraceGrid.loadMulLine(iArray);  
          
          //��Щ����������loadMulLine����
          //RiskTraceGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}
 
</script>
                       

