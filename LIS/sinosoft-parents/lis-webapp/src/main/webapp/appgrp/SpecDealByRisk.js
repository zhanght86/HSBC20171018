var sqlresourcename1 = "appgrp.SpecDealByRiskSql";

function specDealByRisk(){
	//Modify By Shuzhan 20061004 ȥ���������
	//108���ָ����Ա�����ȡ����
	/*if(document.all('RiskCode').value.substring(0,5)=="00108")
	{
			if (document.all('Sex').value==0)
				document.all('GetYear').value=60
			else
				document.all('GetYear').value=55
	}
	//145��128���ֱ����ڼ��������ڼ�֮��Ĺ�ϵ
	if(document.all('RiskCode').value.substring(0,5)=="00128"||document.all('RiskCode').value=="00145000")
	{
			//��������������ʱ��,�����ڼ�Ϊ����,����ʱ����������
			if (document.all('GetDutyKind').value=='1'||document.all('GetDutyKind').value=='2')
			{
				document.all('InsuYear').value=1000
			}
			else
				document.all('InsuYear').value=document.all('GetYear').value
	}
	//�����ձ����ڼ䣬�ɷ����޸�����һ��
	//alert(document.all('RiskCode').value);
	if(document.all('RiskCode').value=="00332000"||document.all('RiskCode').value=="00277000")
	{
			//alert(document.all('MainPolNo').value);
			var tSql="";
			//alert(document.all('MainPolNo').value);
			
  		tSql="select InsuYear from lcpol where polno='"+document.all('MainPolNo').value+"'";
    	var InsuYear = easyExecSql(tSql);   	
    	tSql="select PayIntv from lcpol where polno='"+document.all('MainPolNo').value+"'"; 
      	
    	var PayIntv = easyExecSql(tSql); 
    	//alert("���ѷ�ʽ"+PayIntv); 
    	if(PayIntv=='0')
    	{
    	var PayEndYear='1000';	
    	}
    else
    	{
    	tSql="select PayEndYear from lcpol where polno='"+document.all('MainPolNo').value+"'";
    	var PayEndYear=easyExecSql(tSql);	
    	}
    	//alert("�����ڼ�"+PayEndYear);
    	//alert("baoxian�ڼ�"+InsuYear);
    	document.all('InsuYear').value=InsuYear;
    	document.all('PayEndYear').value=PayEndYear;
    	document.all('PayIntv').value=PayIntv;
    	if (InsuYear==null || PayEndYear==null || InsuYear=='' || PayEndYear=='')
    	{
    		alert("ȡ���ձ����ڼ���߽ɷ�����ʧ�ܣ�");
    	}
    	//alert("ok");
    	return true;
    	//alert("Insuyear:"+InsuYear+"|PayEndYear:"+PayEndYear);
    	
	}
	
	if(document.all('RiskCode').value=="00280000")
	{
			var tSql="";
			//alert(document.all('MainPolNo').value);
  		tSql="select InsuYear from lcpol where polno='"+document.all('MainPolNo').value+"'";
    	var InsuYear = easyExecSql(tSql); 
    	//alert("�����ڼ�"+InsuYear);
    	tSql="select PayIntv from lcpol where polno='"+document.all('MainPolNo').value+"'"; 
      	
    	var PayIntv = easyExecSql(tSql); 
    	//alert("���ѷ�ʽ"+PayIntv); 
    	if(PayIntv=='0')
    	{
    	var PayEndYear='1000';	
    	}
    else
    	{
    	tSql="select PayEndYear from lcpol where polno='"+document.all('MainPolNo').value+"'";
    	var PayEndYear=easyExecSql(tSql);	
    	}
    	
    	document.all('InsuYear').value=InsuYear;
    	document.all('PayEndYear').value=PayEndYear;
    	document.all('PayIntv').value=PayIntv;
    	//alert("�����ڼ�1"+InsuYear); 	 
    	DutyGrid.setRowColData(0,3,document.all('InsuYear').value);
    	//alert(DutyGrid.getRowColData(0,3)+"oo");
    	DutyGrid.setRowColData(0, 5, document.all('PayEndYear').value);
    	DutyGrid.setRowColData(1, 3, document.all('InsuYear').value);
    	DutyGrid.setRowColData(1, 5, document.all('PayEndYear').value);
    	//alert("asdfas"+PayIntv);
    	DutyGrid.setRowColData(0,18,document.all('PayIntv').value);
    	DutyGrid.setRowColData(1,18,document.all('PayIntv').value);
    	//alert(DutyGrid.getRowColData(0,18));
    	if (InsuYear==null||PayEndYear==null||InsuYear==''||PayEndYear=='')
    	{
    		alert("ȡ���ձ����ڼ���߽ɷ�����ʧ�ܣ�");
    	}
    	
    	//alert("Insuyear:"+InsuYear+"|PayEndYear:"+PayEndYear);
    	
	}*/
	var chkNo=0;
	for (var index=0;index<DutyGrid.mulLineCount;index++){
	  if(DutyGrid.getChkNo(index)==true){
	    chkNo=chkNo+1;	
	  }	
	}
	if(DutyGrid.mulLineCount>0 && chkNo==0){
	  alert("��ѡ�����Σ�");
	  return false;	
	}
	/*if(document.all('RiskCode').value=="00607000")
	{
			
			var tpayintv=DutyGrid.getRowColData(0,18);
			var tamnt=DutyGrid.getRowColData(0,14);
			if (tpayintv==0)//����
			{
				if(DutyGrid.getChkNo(1)==true)
				{
					alert("��������ѡ���������");
	  			return false;	
				}
			}
			if(DutyGrid.getChkNo(1)==true)
			{
				DutyGrid.setRowColData(1, 14, tamnt);
				DutyGrid.setRowColData(1, 18, tpayintv);
			}
	}
	if(document.all('RiskCode').value=="140")
	{
      DutyGrid.setRowColData(0, 14, 0);
      DutyGrid.setRowColData(1, 14, 0);
	}
	
	if(DutyGrid.mulLineCount>0){ 
		//���Զ���������У��
		var strChooseDuty="";
		for(i=0;i<=DutyGrid.mulLineCount-1;i++)
		{
			if(DutyGrid.getChkNo(i)==true)
			{
				strChooseDuty=strChooseDuty+"1";
				DutyGrid.setRowColData(i, 8, document.all('GetYear').value);//�������ڵ�λ
				if(document.all('RiskCode').value=="00280000")
				{
					DutyGrid.setRowColData(i, 3, document.all('InsuYear').value);//��������
					DutyGrid.setRowColData(i, 5, document.all('PayEndYear').value);//��������
				}
				//DutyGrid.setRowColData(i, 3, document.all('InsuYear').value);//��������
				//DutyGrid.setRowColData(i, 4, document.all('InsuYearFlag').value);//�������ڵ�λ
				//if(DutyGrid.getRowColData(i,18)==null||DutyGrid.getRowColData(i,18)==""){ //���治¼��ɷѼ����ȡĬ��ֵ
				 // DutyGrid.setRowColData(i, 18, document.all('PayIntv').value);//�ɷѼ��
				//}
			}
			else
			{
				strChooseDuty=strChooseDuty+"0";
			}
		}
	}*/
	
	if(PremGrid.mulLineCount>0){
		chkNo=0;
		for (var index=0;index<PremGrid.mulLineCount;index++){
	  		if(PremGrid.getChkNo(index)==true){
	    		chkNo=chkNo+1;	
	  		}	
		}
		if(chkNo==0){
	  		alert("��ѡ�񽻷��");
	  		return false;	
		}
	}
	
/*if(document.all('RiskCode').value=="208000"||document.all('RiskCode').value=="208001"||document.all('RiskCode').value=="208002"||document.all('RiskCode').value=="208003")
{
			if(PremGrid.mulLineCount>0){
		DutyGrid.setRowColData(i,13,DutyGrid.getRowColData(i,23)*document.all('InsuredPeoples').value);//�ܱ���=���˱���*����
			DutyGrid.setRowColData(i,14,DutyGrid.getRowColData(i,24)*document.all('InsuredPeoples').value);//�ܱ���=���˱���*����
    
      	                }
      	       else
      		{
                        document.all('Prem').value=document.all('Pprem').value*document.all('InsuredPeoples').value;
                        document.all('Amnt').value=document.all('Pamnt').value*document.all('InsuredPeoples').value;
      	}
      		alert(document.all('StandbyFlag1').value);	
      		alert(document.all('Prem').value);	
      		alert(document.all('Amnt').value);
      		alert(document.all('Pprem').value);	
      		alert(document.all('Pamnt').value);
      		alert(document.all('InsuredPeoples').value);	
    
      }*/
      
	if(document.all('RiskCode').value=='211801')
	{
		//���Զ���������У��
		var strChooseDuty="";
		for(i=0;i<=8;i++)
		{
			alert(i+":"+DutyGrid.getChkNo(i));
			if(DutyGrid.getChkNo(i)==true)
			{
				strChooseDuty=strChooseDuty+"1";
				DutyGrid.setRowColData(i, 3, document.all('InsuYear').value);//��������
				//alert("InsuYear=="+ document.all('InsuYear').value);
				//DutyGrid.setRowColData(i, 4, document.all('InsuYearFlag').value);//�������ڵ�λ
				
				DutyGrid.setRowColData(i, 5, document.all('PayEndYear').value);//��������
				//alert("PayEndYear"+ document.all('PayEndYear').value);
				//DutyGrid.setRowColData(i, 6, document.all('PayEndYearFlag').value);//�������ڵ�λ
				DutyGrid.setRowColData(i, 9, document.all('StandByFlag1').value);//�Ƿ���ְ  0��ְ |1����
				//alert("StandByFlag2"+ document.all('StandByFlag2').value);
				DutyGrid.setRowColData(i, 10, document.all('StandByFlag2').value);//��������7������
				//alert("StandByFlag2"+ document.all('StandByFlag2').value);
				DutyGrid.setRowColData(i, 11, document.all('StandByFlag3').value);//��������8������
				//alert("StandByFlag3"+ document.all('StandByFlag3').value);
				//DutyGrid.setRowColData(i, 13, document.all('Prem').value);//����
				//alert("Prem"+ document.all('Prem').value);
				//DutyGrid.setRowColData(i, 17, document.all('FloatRate').value);//����/�ۿ�
				//alert("FloatRate"+ document.all('FloatRate').value);
				//DutyGrid.setRowColData(i, 19, document.all('GetLimit').value);//�����
				//alert("GetLimit"+ document.all('GetLimit').value);
				//DutyGrid.setRowColData(i, 20, document.all('GetRate').value);//�⸶����
				//alert("GetRate"+ document.all('GetRate').value);
				DutyGrid.setRowColData(i, 18, document.all('PayIntv').value);//�ɷѷ�ʽ
			}
			else
		{
				strChooseDuty=strChooseDuty+"0";
			}
		}
		//alert(strChooseDuty);
		//document.all('StandbyFlag1').value=strChooseDuty;
		return true;
		}
        
      	return true;	

}

//�����б�
function initDutyGrid()
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
				iArray[1][0]="���α���";
				iArray[1][1]="0px";
				iArray[1][2]=100;
				iArray[1][3]=3;
				iArray[1][4]="DutyCode";
				iArray[1][15]="RiskCode";
				iArray[1][16]=fm.RiskCode.value;
				
				iArray[2]=new Array();
				iArray[2][0]="��������";
				iArray[2][1]="0px";
				iArray[2][2]=100;
				iArray[2][3]=3;
				
				iArray[3]=new Array();
				iArray[3][0]="�����ڼ�";
				iArray[3][1]="0px";
				iArray[3][2]=100;
				iArray[3][3]=3;
				
				iArray[4]=new Array();
				iArray[4][0]="�����ڼ䵥λ";
				iArray[4][1]="0px";
				iArray[4][2]=100;
				iArray[4][3]=3;
				//iArray[4][11]="0|^M|��^D|��";          			//�Ƿ���������,1��ʾ����0��ʾ������
          		//iArray[4][10]="InsuYearFlag"; 
				
				iArray[5]=new Array();
				iArray[5][0]="�ս���������";
				iArray[5][1]="0px";
				iArray[5][2]=100;
				iArray[5][3]=3;
				
				iArray[6]=new Array();
				iArray[6][0]="�ս��������ڱ�־";
				iArray[6][1]="0px";
				iArray[6][2]=100;
				iArray[6][3]=3;
				
				iArray[7]=new Array();
				iArray[7][0]="��ȡ�ڼ䵥λ";
				iArray[7][1]="0px";
				iArray[7][2]=100;
				iArray[7][3]=3;
				
				iArray[8]=new Array();
				iArray[8][0]="��ȡ�ڼ�";
				iArray[8][1]="0px";
				iArray[8][2]=100;
				iArray[8][3]=3;
				
				iArray[9]=new Array();
				iArray[9][0]="���������ֶ�1";
				iArray[9][1]="0px";
				iArray[9][2]=100;
				iArray[9][3]=3;
				
				iArray[10]=new Array();
				iArray[10][0]="���������ֶ�2";
				iArray[10][1]="0px";
				iArray[10][2]=100;
				iArray[10][3]=3;
				
				iArray[11]=new Array();
				iArray[11][0]="���������ֶ�3";
				iArray[11][1]="0px";
				iArray[11][2]=100;
				iArray[11][3]=3;
				
				iArray[12]=new Array();
				iArray[12][0]="���㷽��";
				iArray[12][1]="0px";
				iArray[12][2]=100;
				iArray[12][3]=3;
				
				iArray[13]=new Array();
				iArray[13][0]="����";
				iArray[13][1]="0px";
				iArray[13][2]=100;
				iArray[13][3]=3;
				
				
				iArray[14]=new Array();
				iArray[14][0]="����";
				iArray[14][1]="0px";
				iArray[14][2]=100;
				iArray[14][3]=3;
				
				
					
				iArray[15]=new Array();
				iArray[15][0]="����";
				iArray[15][1]="0px";
				iArray[15][2]=100;
				iArray[15][3]=3;			
																																					
				iArray[16]=new Array();
				iArray[16][0]="�������";
				iArray[16][1]="0px";
				iArray[16][2]=100;
				iArray[16][3]=3;
				iArray[16][4]="PolCalRule";
				
				iArray[17]=new Array();
				iArray[17][0]="����/�ۿ�";
				iArray[17][1]="0px";
				iArray[17][2]=100;
				iArray[17][3]=3;
				
				iArray[18]=new Array();
				iArray[18][0]="���ѷ�ʽ";
				iArray[18][1]="0px";
				iArray[18][2]=100;
				iArray[18][3]=3;
		
				
				iArray[19]=new Array();
				iArray[19][0]="�����";
				iArray[19][1]="0px";
				iArray[19][2]=100;
				iArray[19][3]=3;

 				iArray[20]=new Array();
				iArray[20][0]="�⸶����";
				iArray[20][1]="0px";
				iArray[20][2]=100;
				iArray[20][3]=3;
				       
				iArray[21]=new Array();
				iArray[21][0]="�籣���";
				iArray[21][1]="0px";
				iArray[21][2]=100;
				iArray[21][3]=3;
				       
				iArray[22]=new Array();
				iArray[22][0]="�ⶥ��";
				iArray[22][1]="0px";
				iArray[22][2]=100;
				iArray[22][3]=3;
				
				iArray[23]=new Array();
				iArray[23][0]="����ÿ��";
				iArray[23][1]="0px";
				iArray[23][2]=100;
				iArray[23][3]=3;
				
				iArray[24]=new Array();
				iArray[24][0]="����ÿ��";
				iArray[24][1]="0px";
				iArray[24][2]=100;
				iArray[24][3]=3;
				
				iArray[25]=new Array();
				iArray[25][0]="����ֹ��";
				iArray[25][1]="0px";
				iArray[25][2]=100;
				iArray[25][3]=3;
     
				iArray[26]=new Array();
				iArray[26][0]="��ȡ��ʽ";
				iArray[26][1]="0px";
				iArray[26][2]=100;
				iArray[26][3]=3;

				iArray[27]=new Array();
				iArray[27][0]="��������";
				iArray[27][1]="0px";
				iArray[27][2]=100;
				iArray[27][3]=3;
				
				iArray[28]=new Array();
				iArray[28][0]="�ɷѹ���";
				iArray[28][1]="0px";
				iArray[28][2]=100;
				iArray[28][3]=3;				
				
				iArray[29]=new Array();
				iArray[29][0]="�������䷽ʽ";
				iArray[29][1]="0px";
				iArray[29][2]=100;
				iArray[29][3]=3;				
								
				iArray[30]=new Array();
				iArray[30][0]="�⽻����";
				iArray[30][1]="0px";
				iArray[30][2]=100;
				iArray[30][3]=3;					
				
				iArray[31]=new Array();
				iArray[31][0]="����";
				iArray[31][1]="0px";
				iArray[31][2]=100;
				iArray[31][3]=3;
				
				iArray[32]=new Array();
		    iArray[32][0]="��������";
		    iArray[32][1]="50px";
		    iArray[32][2]=100;
		    iArray[32][3]=3;
													
       
       //add by huangen �Զ����εĴ���
       if(document.all('RiskCode').value=="211701"||document.all('RiskCode').value=="211801")
       {
          //���δ���
          iArray[1][0]="���α���";
	  	  iArray[1][1]="60px";            		//�п�
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
          //��������
	  	  iArray[2][1]="150px";            		//�п�
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
				
          		iArray[3]=new Array();
				iArray[3][0]="�����ڼ�";
				iArray[3][1]="50px";
				//iArray[3][2]=100;
				iArray[3][3]=1;
				
				iArray[4]=new Array();
				iArray[4][0]="�����ڼ䵥λ";
				iArray[4][1]="30px";
				//iArray[4][2]=100;
				iArray[4][3]=0;
				iArray[4][11]="0|^M|��";          			//�Ƿ���������,1��ʾ����0��ʾ������
          		iArray[4][10]="InsuYearFlag"; 
          		iArray[4][14]="M";
				
				iArray[5]=new Array();
				iArray[5][0]="�ɷ��ڼ�";
				iArray[5][1]="50px";
				//iArray[5][2]=100;
				iArray[5][3]=1;
				
				iArray[6]=new Array();
				iArray[6][0]="�ɷ��ڼ䵥λ";
				iArray[6][1]="30px";
				//iArray[6][2]=100;
				iArray[6][3]=1;	
				iArray[6][11]= "0|^M|��";
				iArray[6][10]="PayEndYearFlag"; 
				iArray[6][14]="M";		  
		  //add by jyl 20061016   ����211701,����һ������������
		  //iArray[16][0]="�������";
		  //iArray[16][1]="40px";
		  //iArray[16][2]=100;
		  //iArray[16][3]=2;
		  //iArray[16][4]="PolCalRule";
		  //iArray[16][14] = "3";

			iArray[9][0]="�Ƿ���ְ";
			iArray[9][1]="50px";
		 //	iArray[9][2]=100;
			iArray[9][3]=1;
			iArray[9][10]="StandbyFlag1";
			iArray[9][11]="0|^0|��ְ^1|����";
			
		  //�����ֶ�2 ��������7������
			iArray[10][0]="��������7������";
			iArray[10][1]="60px";
			iArray[10][3]=1;
			
		  //�����ֶ�3,��������8������ 
		  iArray[11][0]="��������8������";
		  iArray[11][1]="60px";
		  iArray[11][3]=1;
		  
		  iArray[12][0]="���㷽��";
		  iArray[12][1]="0px";
		  iArray[12][2]=100;
		  iArray[12][3]=2;
		  iArray[12][4]="PremToAmnt";
		  iArray[12][14] = "O";
		  
		  iArray[13][0]="����";
		  iArray[13][1]="40px";
		  iArray[13][2]=100;
		  iArray[13][3]=1;				  
		  
		  iArray[17][0]="����/�ۿ�";
		  iArray[17][1]="40px";
		  iArray[17][2]=100;
		  iArray[17][3]=1;
		  
		  iArray[19]=new Array();
		  iArray[19][0]="�����";
		  iArray[19][1]="60px";
		  //iArray[30][2]=100;
		  iArray[19][3]=1;					
				
		  iArray[20]=new Array();
		  iArray[20][0]="�⸶����";
		  iArray[20][1]="60px";
		  //iArray[31][2]=100;
		  iArray[20][3]=1;
		  
		  //add end												
      	
       }
       
  	
  	      if(document.all('RiskCode').value=="212402"||document.all('RiskCode').value=="212401")
  	      {
  	      	//alert(PolTypeFlag);
  	        // if(PolTypeFlag=='2' || PolTypeFlag=='3')
  	        // {
            //     iArray[0][1]="30px";         			//�п�
            //     iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                 
                 //���δ���
	  			 //iArray[1][1]="60px";            		//�п�
                 //iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
	               
                 //��������
	  			 //iArray[2][1]="120px";            		//�п�
                 //iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                 
	               //����
                 //iArray[13][1]="80px";            		//�п�
                 //iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������  	      	
                 //iArray[13][1]="80px";            		//�п�
                 //iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
           /*
                 //��ȡ����
                 iArray[8][0]="��ȡ����"
                 iArray[8][1]="40px";            		//�п�
                 iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
                 iArray[8][10]="GetYear"
	               
	             //��ȡ���ڵ�λ
	             iArray[7][0]="��ȡ���䵥λ"
                 iArray[7][1]="40px";            		//�п�
                 iArray[7][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
                 iArray[7][10]="GetYearFlag"
                 iArray[7][11]="0|^A|��"
*/

  	         //}
  	         //else
  	         //{
                 iArray[0][1]="30px";         			//�п�
                 iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                 
                 //���δ���
	  			 iArray[1][1]="60px";            		//�п�
                 iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
	               
                 //��������
	  			 iArray[2][1]="120px";            		//�п�
                 iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
                 
	               //����
                 iArray[13][1]="80px";            		//�п�
                 iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
                 //��ȡ����
                 iArray[8][0]="��ȡ����"
                 iArray[8][1]="40px";            		//�п�
                 iArray[8][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
                 iArray[8][10]="GetYear"
	               
	             //��ȡ���ڵ�λ
	             iArray[7][0]="��ȡ���䵥λ"
                 iArray[7][1]="40px";            		//�п�
                 iArray[7][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
                 iArray[7][10]="GetYearFlag"
                 iArray[7][11]="0|^A|��"
                 

                 
                 //�ɷѹ���
	             iArray[28][0]="�ɷѹ���"
                 iArray[28][1]="0px";            		//�п�
                 iArray[28][2]=40;
                 iArray[28][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
                 iArray[28][4]="PayRuleCode";
                 iArray[28][15]="GrpContNo";
				 iArray[28][16]=tt;
				         

	               
				 iArray[27][0]="��������";
				 iArray[27][1]="0px";
				 iArray[27][2]=100;
				 iArray[27][3]=2;
				 iArray[27][4]="AscriptionRuleCode";
				 iArray[27][15]="GrpContNo";
				 iArray[27][16]=tt;
	
                 
                 iArray[29][0]="�������䷽ʽ";
				 iArray[29][1]="50px";
				 iArray[29][2]=100;
				 iArray[29][3]=2;
                 iArray[29][10]="BonusGetMode"
                 iArray[29][11]="0|^1|�ۻ���Ϣ^2|��ȡ�ֽ�^3|�ֽɱ���^4|�����^5|����"


        //}	
          }     
  	    if(document.all('RiskCode').value=="211901")
  	    {
          iArray[0][1]="30px";         			//�п�
          iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
            
            //���δ���
 		  iArray[1][1]="60px";            		//�п�
          iArray[1][3]=2;              			//�Ƿ���������,1��ʾ����0��ʾ������
              
            //��������
 		  iArray[2][1]="120px";            		//�п�
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
            
              //����
          iArray[13][1]="80px";            		//�п�
          iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������
            
          iArray[19]=new Array();
  		  iArray[19][0]="�����";
  		  iArray[19][1]="60px";
  		  //iArray[30][2]=100;
  		  iArray[19][3]=1;					
  				
  		  iArray[20]=new Array();
  		  iArray[20][0]="�⸶����";
  		  iArray[20][1]="60px";
  		  //iArray[31][2]=100;
  		  iArray[20][3]=1;


  	    }
  	    	
//221601-201002-genglj
 if(document.all('RiskCode').value=="221601")
       {
          //���δ���
          iArray[1][0]="���α���";
	  	  iArray[1][1]="60px";            		//�п�
          iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
          //��������
	  	  iArray[2][1]="150px";            		//�п�
          iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
				
        iArray[3]=new Array();
				iArray[3][0]="�����ڼ�";
				iArray[3][1]="50px";
				//iArray[3][2]=100;
				iArray[3][3]=1;
				
				iArray[4]=new Array();
				iArray[4][0]="�����ڼ䵥λ";
				iArray[4][1]="50px";
				//iArray[4][2]=100;
				iArray[4][3]=0;
				iArray[4][11]="0|^M|��";          			//�Ƿ���������,1��ʾ����0��ʾ������
        iArray[4][10]="InsuYearFlag"; 
        iArray[4][14]="M";
				

		    iArray[16][0]="�������";
		    iArray[16][1]="40px";
		    iArray[16][2]=100;
		    iArray[16][3]=2;
		    iArray[16][4]="PolCalRule";
		    iArray[16][11]="0|^1|������^2|�������ۿ�^3|Լ�����ѱ���"
		    iArray[16][14] = "0";

		  
		    iArray[13][0]="����";
		    iArray[13][1]="40px";
		    iArray[13][2]=100;
		    iArray[13][3]=1;				  

				iArray[14]=new Array();
				iArray[14][0]="����";
				iArray[14][1]="40px";
				iArray[14][2]=100;
				iArray[14][3]=1;
				
		    
		    iArray[17][0]="����/�ۿ�";
		    iArray[17][1]="40px";
		    iArray[17][2]=100;
		    iArray[17][3]=1;

		  
		  //add end												
      	
       }
	      
	     
   
      DutyGrid = new MulLineEnter( "fm" , "DutyGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      DutyGrid.mulLineCount = 0;   
      DutyGrid.displayTitle = 1;
      DutyGrid.canChk = 1;
      DutyGrid.loadMulLine(iArray);  
      //DutyGrid.checkBoxSel(1);

      //��Щ����������loadMulLine����
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        //alert(ex);
      }
}

function setGetDutyKind()
{
	alert("��ʼ��GetDutyKind���и�ֵ!");
}


//�������б�
function initPremGrid()
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
      iArray[1][0]="���α���";    	//����
      iArray[1][1]="80px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���ѱ���";         			//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="��������";         			//����
      iArray[3][1]="150px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="����";         			//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[5]=new Array();
      iArray[5][0]="�������";         			//����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[6]=new Array();
      iArray[6][0]="�����ʻ���";         			//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[7]=new Array();
      iArray[7][0]="�ʻ��������";         			//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      
      iArray[8]=new Array();
      iArray[8][0]="����ѱ���";         			//����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[9]=new Array();
      iArray[9][0]="����ռԱ�����ʱ���";         			//����
      iArray[9][1]="120px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
	  
     
        if(typeof(window.spanPremGrid) == "undefined" )
        {
              //alert("out");
              return false;
        }
        else
        {
      			
      			
      			
      			//alert("in");
      	    PremGrid = new MulLineEnter( "fm" , "PremGrid" );
	  	    //��Щ���Ա�����loadMulLineǰ
	 	     PremGrid.mulLineCount = 0;   
	 	     PremGrid.displayTitle = 1;
	 	     PremGrid.canChk = 1;
	 	     PremGrid.loadMulLine(iArray);  
     	 }

      
     }
     catch(ex)
    {
		return false;
    }
    return true;
}

/*function insertClick()
{
    if(document.all('StandbyDuty').value!=null&&document.all('StandbyDuty').value!=""){
      var rowCount = DutyGrid.mulLineCount;
      DutyGrid.addOne();
      saveValue(rowCount);
    }
    else{
      alert("��ѡ�������Σ�");
    } 
}
function saveValue(index){
    var mulLineObj = DutyGrid;
    var StandbyDutyCode = document.all('StandbyDuty').value;
    var StandbyDutyCodeName = document.all('StandbyDutyName').value;
    //alert("Code:"+document.all('StandbyDuty').value+"  name:"+document.all('StandbyDutyName').value);
    mulLineObj.setRowColData(index,1,StandbyDutyCode);
    mulLineObj.setRowColData(index,2,StandbyDutyCodeName);
    DutyGrid.checkBoxSel(index+1);
    
}

function setInsuYearToGrid(){
	var strChooseDuty="";
		for(i=0;i<=DutyGrid.mulLineCount-1;i++)
		{
			if(DutyGrid.getChkNo(i)==true)
			{
				strChooseDuty=strChooseDuty+"1";
				DutyGrid.setRowColData(i, 13, document.all('InsuYear').value);//��������
				DutyGrid.setRowColData(i, 12, document.all('InsuYearFlag').value);//�������ڵ�λ
				//DutyGrid.setRowColData(i, 11, document.all('PayIntv').value);//�ɷѷ�ʽ
			}
			else
			{
				strChooseDuty=strChooseDuty+"0";
			}
		}
}
*/


//�����㱣�ѵ����ַ�������Ϊ��
//modify by malong 2005-7-8
function chkMult(){
  var tSql="";
  //tSql="select AmntFlag from lmduty a,lmriskduty b where b.riskcode='"+document.all('RiskCode').value+"' and a.dutycode=b.dutycode";
 
 var sqlid1="SpecDealByRiskSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename1);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(document.all('RiskCode').value);
 
  var arrResult=easyExecSql(mySql1.getString());
  
  if(arrResult[0]=="2" && document.all('inpNeedDutyGrid').value == "1"){
  	for (var index=0;index<DutyGrid.mulLineCount;index++)
  	{
    	//alert(DutyGrid.getRowColData(index,15));
    	if(DutyGrid.getChkNo(index)){
    		if(DutyGrid.getRowColData(index,15)==0)
    		{
      		alert("��������Ϊ��!");
      		return false;	
    		}
    		else if (!isInteger(DutyGrid.getRowColData(index,15)))
    		{
    			alert("��������Ϊ����!");
    			return false;
    		}
    	}    	
  	}
	}
  return true;	
}


//У��CheckBox��108��280����ֻ����ͬʱѡ��һ��������
//create by malong 2005-7-13
function chkDutyNo(){
	if(document.all('RiskCode').value=="00108003"||document.all('RiskCode').value=="00108002"||document.all('RiskCode').value=='00108001'||document.all('RiskCode').value=='00108000' || document.all('RiskCode').value=='00280000'|| document.all('RiskCode').value=='00328000'){
		var DutyNo=0;
		//alert(DutyGrid.getRowColData(0,5));
		for (var index=0;index<DutyGrid.mulLineCount;index++){
		  if(DutyGrid.getChkNo(index)==true){
		    DutyNo=DutyNo+1;	
		  }	
		}
		if(DutyNo>1){
		alert("��������ֻ��ͬʱѡ��һ��������!");
		return false;
		}
		else{
		return true;
		}		
	}
	return true;
	
}		

/*function showFloatRate(tContPlanCode){
  //alert("calRule:"+document.all('CalRule').value+"plan:"+tContPlanCode+"grpcontno:"+document.all('GrpContNo').value);
  var arrResult=null;
  var tSql="";
  if(tContPlanCode!=null&&tContPlanCode!=""){
      	
  }
  if(document.all('CalRule').value=="1"){
    divFloatRate.style.display="none";
    divFloatRate2.style.display="";	
  }
  else if(document.all('CalRule').value=="2"){
    divFloatRate.style.display="";
    divFloatRate2.style.display="none";
  }
  else {
    divFloatRate.style.display="none";
    divFloatRate2.style.display="none";	
  }	
}*/

/*function getOtherInfo(payEndYear){
  //alert("payIntv:"+document.all('PayIntv').value);
  if(document.all('RiskCode').value=="208300"){
    if(payEndYear==1000){
      document.all('PayEndYearFlag').value='A';
      document.all('GetYear').className='code';
      //document.all('GetYearFlag').className='code';
      //document.all('GetYear').CodeData="0|^1|��ʱ��ȡ^55|55����ȡ^60|60����ȡ";
      document.all('GetYear').value="";
      document.all('GetYearFlag').value="";	
    }
    else {		
      if(payEndYear<22){
        document.all('PayEndYearFlag').value='A';	
      }	
      else {
        document.all('PayEndYearFlag').value='A';	
      }
      document.all('GetYear').className='readonly';
      document.all('GetYearFlag').className='readonly';
      if(payEndYear<22){
      	document.all('GetYear').value=1;
        document.all('GetYearFlag').value='A';	
      }
      else{
        document.all('GetYear').value=payEndYear;
       document.all('GetYearFlag').value='A';
     }
    }
  }
}*/

/*function getGetYearFlag(getYear){
  if(document.all('RiskCode').value=='208300'){
    if(getYear=='1'){
      document.all('GetYearFlag').value='A';	
    }	
    else {
      document.all('GetYearFlag').value='A';	
    }
  }	
}*/

function getPayRuleInfo()
{/*
if(document.all('RiskCode').value=="208300"){
 var tSql="select distinct payrulecode,payrulename from lcpayrulefactory where grpcontno='"+document.all('GrpContNo').value+"' and riskcode='"+document.all('RiskCode').value+"'";
  var arr=easyExecSql(tSql);
  if (arr.length>0){
    var tCodeData="0|";
   for (var i=0;i<arr.length;i++){
   	tCodeData=tCodeData+"^"+arr[i][0]+"|"+arr[i][1];
    }
   document.all('PayRuleCode').CodeData=tCodeData;
    
  	}
	}*/
    /*delete by jyl 20061016 ����Ϊһ�����ִ���
    if(document.all('RiskCode').value=="211701")
    {
        var tSql="select distinct payrulecode,payrulename from lcpayrulefactory where grpcontno='"+document.all('GrpContNo').value+"' and riskcode='"+document.all('RiskCode').value+"'";
        var arr=easyExecSql(tSql);
        
        if(arr==null||arr==""||arr=="null")
        {
            document.all('PayRuleCode').CodeData="0|^����|û��¼��ɷѹ������飡";
        }
        else
        {
            if (arr.length>0)
            {
                var tCodeData="0|";
                for (var i=0;i<arr.length;i++)
                {
          	        tCodeData=tCodeData+"^"+arr[i][0]+"|"+arr[i][1];
                }
                document.all('PayRuleCode').CodeData=tCodeData;
          
            }
            else
            {
                document.all('PayRuleCode').CodeData="0|^����|û��¼��ɷѹ������飡";
            }
        }
    }	
    delete End */
}
