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
  	document.all('CertificateID').value=CertificateID;  	
  	//alert(document.all('CertificateID').value);
  	//״̬State����ֶ���FICostTypeDef��ƾ֤�������Ͷ��壩�������֮����Ϊ�գ���FICostDataAcquisitionDef��ƾ֤�������ݲɼ����壩���֮����Ϊ01-�������
  	document.all('State').value = '02'; 	
  	
  	//���汾״̬��Ϊ02-ά����ʱ����ɾ�İ�ťΪ��ɫ		
  	//alert(VersionState);
		if (VersionState == "01" || VersionState == "03" || VersionState == '' || VersionState == null )
		{
			document.all('addbutton').disabled = true;				
			document.all('updatebutton').disabled = true;		
			document.all('deletebutton').disabled = true;	
		}                    
  	//document.all('VersionNo').readOnly=false;       
  }
  catch(ex)
  {
    alert("��CostTypeDefInputInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}



function initSelBox()
{  
  try                 
  {

  }
  catch(ex)
  {
    alert("��CostTypeDefInputInit.jsp-->InitSelBox�����з����쳣:��ʼ���������!");
  }
}                                   


function initForm()
{
  try
  {    
    initInpBox();    
    initSelBox();
		initCostTypeDefGrid();  
  	initCostTypeDefQuery();    
  }
  
  catch(re)
  {
    alert("��CostTypeDefInputInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
  }
}


 function initCostTypeDefGrid()
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
      iArray[1][1]="110px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[2]=new Array();
      iArray[2][0]="ƾ֤���ͱ��";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[3]=new Array();
      iArray[3][0]="�������ͱ���";         			//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
 
      iArray[4]=new Array();
      iArray[4][0]="������������";         			//����
      iArray[4][1]="150px";            		//�п�
      iArray[4][2]=60;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[5]=new Array();
      iArray[5][0]="�����־";         			//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=60;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[6]=new Array();
      iArray[6][0]="�����־";         			//����
      iArray[6][1]="50px";            		//�п�
      iArray[6][2]=60;            			//�����ֵ
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������      

      iArray[7]=new Array();
      iArray[7][0]="��Ŀ���ͱ���";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
  
      iArray[8]=new Array();
      iArray[8][0]="��Ŀ��������";         		//����
      iArray[8][1]="150px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

      iArray[9]=new Array();
      iArray[9][0]="������������";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=0;     		    			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������

      //iArray[9]=new Array();
      //iArray[9][0]="״̬";         		//����
      //iArray[9][1]="40px";            		//�п�
      //iArray[9][2]=100;            			//�����ֵ
      //iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
      

      CostTypeDefGrid = new MulLineEnter( "document" , "CostTypeDefGrid" ); 
      CostTypeDefGrid.mulLineCount = 5;   
      CostTypeDefGrid.displayTitle = 1;
      CostTypeDefGrid.canSel=1;
      //ѡ��һ���Զ��������ֵ
      CostTypeDefGrid.selBoxEventFuncName = "ShowCostTypeDef";			      
      CostTypeDefGrid.locked = 1;	
			CostTypeDefGrid.hiddenPlus = 1;
			CostTypeDefGrid.hiddenSubtraction = 1;

      CostTypeDefGrid.loadMulLine(iArray);  
      CostTypeDefGrid.detailInfo="������ʾ��ϸ��Ϣ";
      //CostTypeDefGrid.detailClick=reportDetailClick;     
      }
      
      catch(ex)
      {
        alert(ex);
      }
}

</script>
