<%
//�������ƣ�ProposalScanQueryInit.jsp
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��Minim
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //����ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox(){   
}

// ������ĳ�ʼ��
function initSelBox(){  
}                                        

function initForm()
{
  try {
    initInpBox();
    initSelBox();    
	  initPolGrid();
	  //initQuery();
  }
  catch(re) {
    alert("ProposalApproveModifyInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}

var PolGrid;
// ������Ϣ�б��ĳ�ʼ��
function initPolGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ӡˢ��";         		//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="ҵ������";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="��֤ϸ��";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������            

      iArray[4]=new Array();
      iArray[4][0]="Ͷ����ɨ��ҳ��";         		//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="���������Ա";         		//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="��ʼ����";         		//����
      iArray[6][1]="100px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[7]=new Array();
      iArray[7][0]="��ʼʱ��";         		//����
      iArray[7][1]="100px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
      
      iArray[8]=new Array();
      iArray[8][0]="��������";         		//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=2; 
      iArray[8][4]="station";              	        //�Ƿ����ô���:null||""Ϊ������
      iArray[8][5]="6";              	                //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
      iArray[8][9]="��������|code:station&NOTNULL";
      iArray[8][18]=250;
      iArray[8][19]= 0 ;
      
      iArray[9]=new Array();
      iArray[9][0]="ɨ������";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������

      iArray[10]=new Array();
      iArray[10][0]="ɨ��ʱ��";         		//����
      iArray[10][1]="100px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ������0��ʾ������
     
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>