<%
//Creator :ZhongYan
//Date :2008-08-12
%>
<!--�û�У����-->

<script language="JavaScript">


// �����ĳ�ʼ��
function initInpBox()
{
  try
  {
    document.all('VersionNo').value=VersionNo; 
    
  }
  catch(ex)
  {
    alert("��AssociatedItemDefQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }
}


// ������ĳ�ʼ��
function initSelBox()
{
  try
  {
  	
  }
  catch(ex)
  {
    alert("��AssociatedItemDefQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}


function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initAssociatedItemDefGrid();
  }
  catch(re)
  {
    alert("��AssociatedItemDefQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


function initAssociatedItemDefGrid()
{
	var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�汾���";    	//����
      iArray[1][1]="100px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ר����";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[3]=new Array();
      iArray[3][0]="ר������";         			//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[4]=new Array();
      iArray[4][0]="ר����ֶα�ʶ";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[5]=new Array();
      iArray[5][0]="����������Դ����";         			//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[6]=new Array();
      iArray[6][0]="����������Դ�ֶ�";         			//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
            
      iArray[7]=new Array();
      iArray[7][0]="ת����־";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
      
      iArray[8]=new Array();
      iArray[8][0]="ת����־";         		//����
      iArray[8][1]="50px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������      

      

      AssociatedItemDefGrid = new MulLineEnter( "document" , "AssociatedItemDefGrid" ); 
      AssociatedItemDefGrid.mulLineCount = 5;   
      AssociatedItemDefGrid.displayTitle = 1;
      AssociatedItemDefGrid.canSel=1;
      AssociatedItemDefGrid.locked = 1;	
	    AssociatedItemDefGrid.hiddenPlus = 1;
	    AssociatedItemDefGrid.hiddenSubtraction = 1;
      AssociatedItemDefGrid.loadMulLine(iArray);  
      AssociatedItemDefGrid.detailInfo="������ʾ��ϸ��Ϣ";
      //AssociatedItemDefGrid.detailClick=reportDetailClick;
     
      }
      
      catch(ex)
      {
        alert("��ʼ��AssociatedItemDefGridʱ����"+ ex);
      }

}


</script>
