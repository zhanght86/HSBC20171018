<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�AmntAccumulateInit.jsp
//�����ܣ������ۼ�
//�������ڣ�2005-06-01 11:10:36
//������  ��HL
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
  	//��ʼ�������ۼ�multiline
  	initAmntAccuGrid();
  	
  	//��ʼ�������ۼ���ϸmultiline
  	initAmntAccuDetailGrid();
  	
  	//��ѯ�ͻ���Ϣ
  	queryPersonInfo();
	
	  //��ѯ�����ۼ���Ϣ
	  queryAmntAccu();
	  
	  //��ѯ�����ۼ���ϸ��Ϣ
	  queryAmntAccuDetail();
	  
  	
  }
  catch(re) {
    alert("AmntAccumulateInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initAmntAccuGrid(){
    var iArray = new Array();
      
      try
      {
          iArray[0]=new Array();
          iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
          iArray[0][1]="30px";            		//�п�
          iArray[0][2]=10;            			//�����ֵ
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[1]=new Array();
          iArray[1][0]="�������";         		//����
          iArray[1][1]="80px";            		//�п�
          iArray[1][2]=100;            			//�����ֵ
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[2]=new Array();
          iArray[2][0]="�б������ۼƣ�Ԫ��";         		//����
          iArray[2][1]="120px";            		//�п�
          iArray[2][2]=100;            			//�����ֵ
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[3]=new Array();
          iArray[3][0]="�б������ۼƣ�������";         		//����
          iArray[3][1]="120px";            		//�п�
          iArray[3][2]=200;            			//�����ֵ
          iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[4]=new Array();
          iArray[4][0]="�����ձ����ۼƣ�Ԫ��";         		//����
          iArray[4][1]="120px";            		//�п�
          iArray[4][2]=200;            			//�����ֵ
          iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[5]=new Array();
          iArray[5][0]="�����ձ����ۼƣ�������";         		//����
          iArray[5][1]="0px";            		//�п�
          iArray[5][2]=100;            			//�����ֵ
          iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          
          
          AmntAccuGrid = new MulLineEnter( "fm" , "AmntAccuGrid" ); 

          //��Щ���Ա�����loadMulLineǰ
          AmntAccuGrid.mulLineCount = 5;   
          AmntAccuGrid.displayTitle = 1;
          AmntAccuGrid.locked = 1;
          AmntAccuGrid.canSel = 0;
          AmntAccuGrid.hiddenPlus = 1;
          AmntAccuGrid.hiddenSubtraction = 1;
          AmntAccuGrid.loadMulLine(iArray);  
          
          //��Щ����������loadMulLine����
          //AmntAccuGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}

function initAmntAccuDetailGrid(){
	
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="���ִ���";         		//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="180px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�б������ۼƣ�Ԫ��";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="�б������ۼƣ�������";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�����ձ����ۼƣ�Ԫ��";         		//����
      iArray[5][1]="160px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�����ձ����ۼƣ�������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      
      AmntAccuDetailGrid = new MulLineEnter( "fm" , "AmntAccuDetailGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AmntAccuDetailGrid.mulLineCount = 5;   
      AmntAccuDetailGrid.displayTitle = 1;
      AmntAccuDetailGrid.locked = 1;
      AmntAccuDetailGrid.canSel = 0;
      AmntAccuDetailGrid.hiddenPlus = 1;
      AmntAccuDetailGrid.hiddenSubtraction = 1;
      AmntAccuDetailGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //AmntAccuGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}


// �����ĳ�ʼ��
function initInpBox()
{
}

// ������ĳ�ʼ��
function initSelBox(){  
}                                        


</script>
                       

