<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ContTraceInit.jsp 
//�����ܣ���ͬ�˱��켣��ѯ  
//�������ڣ�2005-06-27 11:10:36
//������  ��ccvip
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

  	//��ʼ���ٺ�ͬ�˱��켣multiline

  	initContTraceGrid();   

	  //��ѯ�˱��켣
	  queryContTrace();      
  } 
  catch(re) {
    alert("ContTraceInit.jsp-->InitForm�����з����쳣:��ʼ���������!"); 
  } 
}


//���ֺ˱��켣�б�ĳ�ʼ��
function initContTraceGrid(){
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
          iArray[2][1]="80px";            		//�п�
          iArray[2][2]=100;            			//�����ֵ
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
          
          iArray[3]=new Array();
          iArray[3][0]="��������";         		//����
          iArray[3][1]="100px";            		//�п�
          iArray[3][2]=200;            			//�����ֵ
          iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
            
          iArray[4]=new Array();
          iArray[4][0]="�˱����۴���";         		//����
          iArray[4][1]="80px";            		//�п�
          iArray[4][2]=200;            			//�����ֵ
          iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
          
          
          iArray[5]=new Array();
          iArray[5][0]="�˱�����";         		//����
          iArray[5][1]="100px";            		//�п�
          iArray[5][2]=200;            			//�����ֵ
          iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
          
          iArray[6]=new Array();
          iArray[6][0]="�˱�����\�˱����";         		//����
          iArray[6][1]="320px";            		//�п�
          iArray[6][2]=1000;            			//�����ֵ
          iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
        
       
          ContTraceGrid = new MulLineEnter( "document" , "ContTraceGrid" ); 

          //��Щ���Ա�����loadMulLineǰ
          
          ContTraceGrid.mulLineCount = 5;     
          ContTraceGrid.displayTitle = 1;
          ContTraceGrid.locked = 1;
          ContTraceGrid.canSel = 0;
          ContTraceGrid.hiddenPlus = 1;
          ContTraceGrid.hiddenSubtraction = 1;
          ContTraceGrid.loadMulLine(iArray);  
          
          //��Щ����������loadMulLine����
          //ContTraceGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
} 
 
</script>
                       

