<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorQueryInit.jsp
//�����ܣ���ȫ��ѯ
//�������ڣ�2005-6-10 14:46
//������  ��guomy 
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
  	//��ʼ����ȫ��Ϣ
  	initEdorGrid();
  	
  	//��ʼ����ȫ��Ŀ��Ϣ
  	initEdorItemGrid();

    
  	//��ѯ�ͻ���Ϣ
  	queryPersonInfo();

	     
	  //��ѯ������ȫ��Ϣ
	  queryEdor();
	  
  	
  }
  catch(re) { 
    alert("EdorQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initEdorItemGrid(){
    var iArray = new Array();
      
      try
      {
          iArray[0]=new Array();
          iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
          iArray[0][1]="30px";            		//�п�
          iArray[0][2]=10;            			//�����ֵ
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[1]=new Array();
          iArray[1][0]="������";         		//����
          iArray[1][1]="100px";            		//�п�
          iArray[1][2]=100;            			//�����ֵ
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[2]=new Array();
          iArray[2][0]="������";         		//����
          iArray[2][1]="100px";            		//�п� 
          iArray[2][2]=100;            			//�����ֵ
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[3]=new Array();
          iArray[3][0]="���뱣ȫ��Ŀ";         		//����
          iArray[3][1]="120px";            		//�п�
          iArray[3][2]=100;            			//�����ֵ
          iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[4]=new Array();
          iArray[4][0]="�������";         		//����
          iArray[4][1]="180px";            		//�п�
          iArray[4][2]=200;            			//�����ֵ
          iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[5]=new Array();
          iArray[5][0]="�������ʱ��";         		//����
          iArray[5][1]="60px";            		//�п�
          iArray[5][2]=200;            			//�����ֵ
          iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[6]=new Array();
          iArray[6][0]="�����Ч����";         		//����
          iArray[6][1]="60px";            		//�п�
          iArray[6][2]=100;            			//�����ֵ
          iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[7]=new Array();
          iArray[7][0]="��ȫ��Ŀ�˱�����";         		//����
          iArray[7][1]="0px";            		//�п�
          iArray[7][2]=100;            			//�����ֵ
          iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          
          EdorItemGrid = new MulLineEnter( "fm" , "EdorItemGrid" ); 

          //��Щ���Ա�����loadMulLineǰ
          EdorItemGrid.mulLineCount = 5;   
          EdorItemGrid.displayTitle = 1;
          EdorItemGrid.locked = 1;
          EdorItemGrid.canSel = 1; 
          EdorItemGrid.hiddenPlus = 1;
          EdorItemGrid.hiddenSubtraction = 1; 
          EdorItemGrid.selBoxEventFuncName = "";
          EdorItemGrid.loadMulLine(iArray);  
          
          //��Щ����������loadMulLine����
          //EdorItemGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}


function initEdorGrid(){
    var iArray = new Array();
      
      try
      {
          iArray[0]=new Array();
          iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
          iArray[0][1]="30px";            		//�п�
          iArray[0][2]=10;            			//�����ֵ
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[1]=new Array();
          iArray[1][0]="������";         		//����
          iArray[1][1]="100px";            		//�п� 
          iArray[1][2]=90;            			//�����ֵ
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[2]=new Array();
          iArray[2][0]="Ͷ��������";         		//���� 
          iArray[2][1]="120px";            		//�п�
          iArray[2][2]=100;            			//�����ֵ
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[3]=new Array();
          iArray[3][0]="�����������";         		//����
          iArray[3][1]="70px";            		//�п�
          iArray[3][2]=200;            			//�����ֵ
          iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[4]=new Array();
          iArray[4][0]="�����Ч����";         		//����
          iArray[4][1]="70px";            		//�п�
          iArray[4][2]=200;            			//�����ֵ
          iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
          
          iArray[5]=new Array();
          iArray[5][0]="�˱�����";         		//����
          iArray[5][1]="250px";            		//�п�
          iArray[5][2]=250;            			//�����ֵ
          iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          
          
          EdorGrid = new MulLineEnter( "fm" , "EdorGrid" ); 

          //��Щ���Ա�����loadMulLineǰ
          EdorGrid.mulLineCount = 5;   
          EdorGrid.displayTitle = 1;
          EdorGrid.locked = 1;
          EdorGrid.canSel = 1;
          EdorGrid.hiddenPlus = 1;
          EdorGrid.hiddenSubtraction = 1;
          EdorGrid.selBoxEventFuncName = "getEdorItemInfo";
          EdorGrid.loadMulLine(iArray);   
          
          //��Щ����������loadMulLine����
          //EdorItemGrid.setRowColData(1,1,"asdf");
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
                       

