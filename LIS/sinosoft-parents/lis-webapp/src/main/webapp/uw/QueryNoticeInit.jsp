<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�QueryNoticeInit.jsp
//�����ܣ��ѷ���֪ͨ���ѯ
//�������ڣ�2006-11-17 17:05
//������  ��haopan
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
 
  	//��ʼ��֪ͨ���ѯmultiline
  	initNoticeGrid();  
  	

	  //��ѯ�ѷ���֪ͨ��
	  queryNotice();
   
	   
  } 
  catch(re) {
    alert("QueryNoticeInit.jsp-->InitForm�����з����쳣:��ʼ���������!"); 
  }
}

//�ѷ���֪ͨ���б�ĳ�ʼ��
function initNoticeGrid(){
    var iArray = new Array();
       
      try 
      {   
          iArray[0]=new Array();
          iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
          iArray[0][1]="40px";            		//�п�
          iArray[0][2]=10;            			//�����ֵ
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[1]=new Array();
          iArray[1][0]="��ӡ��ˮ��";         		//���� 
          iArray[1][1]="120px";            		//�п�
          iArray[1][2]=100;            			//�����ֵ
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������  

          
          iArray[2]=new Array();
          iArray[2][0]="֪ͨ������";         		//����
          iArray[2][1]="180px";            		//�п�
          iArray[2][2]=200;            			//�����ֵ
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������    
          
     
          iArray[3]=new Array();
          iArray[3][0]="��ӡ״̬";         		//����
          iArray[3][1]="180px";            		//�п�
          iArray[3][2]=200;            			//�����ֵ
          iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
          
          
        
       
          NoticeGrid = new MulLineEnter( "fm" , "NoticeGrid" ); 

          //��Щ���Ա�����loadMulLineǰ
          
          NoticeGrid.mulLineCount = 10;     
          NoticeGrid.displayTitle = 1;
          NoticeGrid.locked = 1;
          NoticeGrid.canSel = 0;
          NoticeGrid.hiddenPlus = 1;
          NoticeGrid.hiddenSubtraction = 1;
          NoticeGrid.loadMulLine(iArray);  
          
					//��Щ����������loadMulLine����
          //NoticeGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}
 
</script>
