<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ProposalQueryInit.jsp
//�����ܣ��б���ѯ
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
  	//��ʼ�������ⰸmultiline
  	
  	initClaimGrid();
  	
  	//��ʼ���ⰸ������Ϣ
  	initPolGrid();
  	
  	
  	//��ѯ�ͻ���Ϣ
  	queryPersonInfo();
	 
	  //��ѯ������Ϣ
	  queryClaim(); 
	  //alert(555);
  	
  }
  catch(re) {
    alert("ClaimQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

function initPolGrid(){
    var iArray = new Array();
      
      try
      {
          iArray[0]=new Array();
          iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
          iArray[0][1]="30px";            		//�п�
          iArray[0][2]=10;            			//�����ֵ
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[1]=new Array();
          iArray[1][0]="�ⰸ��";         	        //����
          iArray[1][1]="100px";            		//�п�
          iArray[1][2]=10;            			//�����ֵ
          iArray[1][3]=0;  
                    
          iArray[2]=new Array();
          iArray[2][0]="������";         		//����
          iArray[2][1]="110px";            		//�п�
          iArray[2][2]=100;            			//�����ֵ
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[3]=new Array();
          iArray[3][0]="���֣����";         		//����
          iArray[3][1]="120px";            		//�п�
          iArray[3][2]=100;            			//�����ֵ
          iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

          iArray[4]=new Array();
          iArray[4][0]="�������";         		//����
          iArray[4][1]="160px";            		//�п�
          iArray[4][2]=100;            			//�����ֵ
          iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
           
          iArray[5]=new Array();
          iArray[5][0]="�⸶���";         		//����
          iArray[5][1]="120px";            		//�п�
          iArray[5][2]=200;            			//�����ֵ
          iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[6]=new Array();
          iArray[6][0]="������";         		//����
          iArray[6][1]="100px";            		//�п�
          iArray[6][2]=200;            			//�����ֵ
          iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          //iArray[7]=new Array();
          //iArray[7][0]="������";         		//����
          //iArray[7][1]="60px";            		//�п�
          //iArray[7][2]=100;            			//�����ֵ
          //iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          
          
          
          PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 

          //��Щ���Ա�����loadMulLineǰ
          PolGrid.mulLineCount = 5;   
          PolGrid.displayTitle = 1;
          PolGrid.locked = 1;
          PolGrid.canSel = 1;
          PolGrid.hiddenPlus = 1;
          PolGrid.hiddenSubtraction = 1;
          //PolGrid.selBoxEventFuncName = "alert";
          PolGrid.loadMulLine(iArray);  
          
          //��Щ����������loadMulLine����
          //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
	
}


function initClaimGrid(){
    var iArray = new Array();
      
      try
      {
          iArray[0]=new Array();
          iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
          iArray[0][1]="30px";            		//�п�
          iArray[0][2]=10;            			//�����ֵ
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[1]=new Array();
          iArray[1][0]="�ⰸ��";         		//����
          iArray[1][1]="200px";            		//�п�
          iArray[1][2]=100;            			//�����ֵ
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
            
          iArray[2]=new Array();
          iArray[2][0]="����������";         		//����
          iArray[2][1]="200px";            		//�п�
          iArray[2][2]=100;            			//�����ֵ
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[3]=new Array();
          iArray[3][0]="��������";         		//����
          iArray[3][1]="210px";            		//�п�
          iArray[3][2]=200;            			//�����ֵ
          iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
          
          iArray[4]=new Array();
          iArray[4][0]="�ⰸ״̬";         		//����
          iArray[4][1]="120px";            		//�п�
          iArray[4][2]=200;            			//�����ֵ
          iArray[4][3]=0; 
           
                
          ClaimGrid = new MulLineEnter( "fm" , "ClaimGrid" ); 

          //��Щ���Ա�����loadMulLineǰ
          ClaimGrid.mulLineCount = 5;   
          ClaimGrid.displayTitle = 1;
          ClaimGrid.locked = 1;
          ClaimGrid.canSel = 1;
          ClaimGrid.hiddenPlus = 1;
          ClaimGrid.hiddenSubtraction = 1;
          ClaimGrid.selBoxEventFuncName = "getPolInfo";
          ClaimGrid.loadMulLine(iArray);  
          
          //��Щ����������loadMulLine����
          //PolGrid.setRowColData(1,1,"asdf");
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
                       

