<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�RecordQueryInit.jsp
//�����ܣ�����������ѯ
//�������ڣ�2005-06-23 11:10:36
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
  	//��ʼ����������multiline
  	initRecordGrid();
  	
  	//��ʼ��������֪��Ϣ
  	//initImpartGrid();
  	
  	//��ѯ�ͻ���Ϣ
  	//queryPersonInfo();
	  
	  //��ѯ��������
	  queryRecord();
	   	
  }
  catch(re) {
    alert("RecordQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


//���������б�ĳ�ʼ��
function initRecordGrid(){
    var iArray = new Array();
      
      try
      {
          iArray[0]=new Array();
          iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
          iArray[0][1]="40px";            		//�п�
          iArray[0][2]=10;            			//�����ֵ
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[1]=new Array();
          iArray[1][0]="ӡˢ��";         		//����
          iArray[1][1]="80px";            		//�п�
          iArray[1][2]=100;            			//�����ֵ
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[2]=new Array();
          iArray[2][0]="����Ա";         		//����
          iArray[2][1]="80px";            		//�п�
          iArray[2][2]=100;            			//�����ֵ
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[3]=new Array();
          iArray[3][0]="������ʼ����";         		//����
          iArray[3][1]="100px";            		//�п�
          iArray[3][2]=200;            			//�����ֵ
          iArray[3][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[4]=new Array();
          iArray[4][0]="������������";         		//����
          iArray[4][1]="100px";            		//�п�
          iArray[4][2]=200;            			//�����ֵ
          iArray[4][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[5]=new Array();
          iArray[5][0]="����ǰ״̬";         		//����
          iArray[5][1]="0px";            		//�п�
          iArray[5][2]=200;            			//�����ֵ
          iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[6]=new Array();
          iArray[6][0]="������״̬";         		//����
          iArray[6][1]="160px";            		//�п�
          iArray[6][2]=200;            			//�����ֵ
          iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[7]=new Array();
          iArray[7][0]="�ϱ�����";         		//����
          iArray[7][1]="160px";            		//�п�
          iArray[7][2]=100;            			//�����ֵ
          iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

               
          RecordGrid = new MulLineEnter( "document" , "RecordGrid" ); 

          //��Щ���Ա�����loadMulLineǰ
          RecordGrid.mulLineCount = 5;   
          RecordGrid.displayTitle = 1;
          RecordGrid.locked = 1;
          RecordGrid.canSel = 0;
          RecordGrid.hiddenPlus = 1;
          RecordGrid.hiddenSubtraction = 1;
          RecordGrid.loadMulLine(iArray);  
          
          //��Щ����������loadMulLine����
          //RecordGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}

</script>
                       

