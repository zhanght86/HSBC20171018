 <%
//Creator :Fanxin
//Date :2008-09-16
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
    alert("��AssociatedDirectItemDefQueryInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��AssociatedDirectItemDefQueryInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}


function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initAssociatedDirectItemDefGrid();
  }
  catch(re)
  {
    alert("��AssociatedDirectItemDefQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


function initAssociatedDirectItemDefGrid()
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
      iArray[2][0]="ר����ֶα�ʶ";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=60;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[3]=new Array();
      iArray[3][0]="����������Դ����";         			//����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[4]=new Array();
      iArray[4][0]="����������Դ�ֶ�";         			//����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
            

      

      AssociatedDirectItemDefGrid = new MulLineEnter( "document" , "AssociatedDirectItemDefGrid" ); 
      AssociatedDirectItemDefGrid.mulLineCount = 5;   
      AssociatedDirectItemDefGrid.displayTitle = 1;
      AssociatedDirectItemDefGrid.canSel=1;
      AssociatedDirectItemDefGrid.locked = 1;	
	    AssociatedDirectItemDefGrid.hiddenPlus = 1;
	    AssociatedDirectItemDefGrid.hiddenSubtraction = 1;
      AssociatedDirectItemDefGrid.loadMulLine(iArray);  
      AssociatedDirectItemDefGrid.detailInfo="������ʾ��ϸ��Ϣ";
      //AssociatedItemDefGrid.detailClick=reportDetailClick;
     
      }
      
      catch(ex)
      {
        alert("��ʼ��AssociatedDirectItemDefGridʱ����"+ ex);
      }

}


</script>
