<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ڣ�2008-08-11
//������  ��ZhongYan
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
             
<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 
  try
  {          	
  
  	document.all('VersionNo').value=VersionNo;
  	//alert(document.all('VersionNo').value);
  	document.all('FinItemID').value=FinItemID;
  	document.all('AssociatedID1').value='';  	  	
  	//alert(document.all('FinItemID').value);  	
  	
  	//���汾״̬��Ϊ02-ά����ʱ����ɾ�İ�ťΪ��ɫ		
  	//alert(VersionState);
		if (VersionState == "01" || VersionState == "03" || VersionState == '' || VersionState == null )
		{
			document.all('addbutton').disabled = true;				
			document.all('deletebutton').disabled = true;	
		}                    
  	//document.all('VersionNo').readOnly=false;       
  	//document.all('AssociatedID').readOnly=false;       
  }
  catch(ex)
  {
    alert("��InfoFinItemAssociatedInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������0!");
  }      
}



function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��InfoFinItemAssociatedInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������1!");
  }
}                                   


function initForm()
{
  try
  {
    initInpBox(); 
    initSelBox();
		initItemAssociatedGrid();    
  	initAssociatedQuery();    
  }
  catch(re)
  {
    alert("��InfoFinItemAssociatedInputInit.jsp-->InitForm�����з����쳣:��ʼ���������2!");
  }
}


 function initItemAssociatedGrid()
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
      iArray[2][0]="��Ŀ���";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[3]=new Array();
      iArray[3][0]="ר����";         			//����
      iArray[3][1]="100px";            		//�п�
      iArray[3][2]=60;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[4]=new Array();
      iArray[4][0]="ר������";         			//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="����";         		//����
      iArray[5][1]="200px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
      

      ItemAssociatedGrid = new MulLineEnter( "document" , "ItemAssociatedGrid" ); 
      ItemAssociatedGrid.mulLineCount = 5;   
      ItemAssociatedGrid.displayTitle = 1;
      ItemAssociatedGrid.canSel=1;
      //ѡ��һ���Զ��������ֵ
      ItemAssociatedGrid.selBoxEventFuncName = "ShowAssociated";			      
      ItemAssociatedGrid.locked = 1;	
			ItemAssociatedGrid.hiddenPlus = 1;
			ItemAssociatedGrid.hiddenSubtraction = 1;

      ItemAssociatedGrid.loadMulLine(iArray);  
      ItemAssociatedGrid.detailInfo="������ʾ��ϸ��Ϣ";
      //ItemAssociatedGrid.detailClick=reportDetailClick;     
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

</script>
