
function specDealByRisk(){
	//Modify By Shuzhan 20061004 去除多余代码
	//108险种根据性别置领取年龄
	/*if(fm.all('RiskCode').value.substring(0,5)=="00108")
	{
			if (fm.all('Sex').value==0)
				fm.all('GetYear').value=60
			else
				fm.all('GetYear').value=55
	}
	//145，128险种保险期间与起领期间之间的关系
	if(fm.all('RiskCode').value.substring(0,5)=="00128"||fm.all('RiskCode').value=="00145000")
	{
			//当年领或者月领的时候,保险期间为终身,趸领时到起领年龄
			if (fm.all('GetDutyKind').value=='1'||fm.all('GetDutyKind').value=='2')
			{
				fm.all('InsuYear').value=1000
			}
			else
				fm.all('InsuYear').value=fm.all('GetYear').value
	}
	//附加险保险期间，缴费期限跟主险一致
	//alert(fm.all('RiskCode').value);
	if(fm.all('RiskCode').value=="00332000"||fm.all('RiskCode').value=="00277000")
	{
			//alert(fm.all('MainPolNo').value);
			var tSql="";
			//alert(fm.all('MainPolNo').value);
			
  		tSql="select InsuYear from lcpol where polno='"+fm.all('MainPolNo').value+"'";
    	var InsuYear = easyExecSql(tSql);   	
    	tSql="select PayIntv from lcpol where polno='"+fm.all('MainPolNo').value+"'"; 
      	
    	var PayIntv = easyExecSql(tSql); 
    	//alert("交费方式"+PayIntv); 
    	if(PayIntv=='0')
    	{
    	var PayEndYear='1000';	
    	}
    else
    	{
    	tSql="select PayEndYear from lcpol where polno='"+fm.all('MainPolNo').value+"'";
    	var PayEndYear=easyExecSql(tSql);	
    	}
    	//alert("交费期间"+PayEndYear);
    	//alert("baoxian期间"+InsuYear);
    	fm.all('InsuYear').value=InsuYear;
    	fm.all('PayEndYear').value=PayEndYear;
    	fm.all('PayIntv').value=PayIntv;
    	if (InsuYear==null || PayEndYear==null || InsuYear=='' || PayEndYear=='')
    	{
    		alert("取主险保险期间或者缴费期限失败！");
    	}
    	//alert("ok");
    	return true;
    	//alert("Insuyear:"+InsuYear+"|PayEndYear:"+PayEndYear);
    	
	}
	
	if(fm.all('RiskCode').value=="00280000")
	{
			var tSql="";
			//alert(fm.all('MainPolNo').value);
  		tSql="select InsuYear from lcpol where polno='"+fm.all('MainPolNo').value+"'";
    	var InsuYear = easyExecSql(tSql); 
    	//alert("保险期间"+InsuYear);
    	tSql="select PayIntv from lcpol where polno='"+fm.all('MainPolNo').value+"'"; 
      	
    	var PayIntv = easyExecSql(tSql); 
    	//alert("交费方式"+PayIntv); 
    	if(PayIntv=='0')
    	{
    	var PayEndYear='1000';	
    	}
    else
    	{
    	tSql="select PayEndYear from lcpol where polno='"+fm.all('MainPolNo').value+"'";
    	var PayEndYear=easyExecSql(tSql);	
    	}
    	
    	fm.all('InsuYear').value=InsuYear;
    	fm.all('PayEndYear').value=PayEndYear;
    	fm.all('PayIntv').value=PayIntv;
    	//alert("交费期间1"+InsuYear); 	 
    	DutyGrid.setRowColData(0,3,fm.all('InsuYear').value);
    	//alert(DutyGrid.getRowColData(0,3)+"oo");
    	DutyGrid.setRowColData(0, 5, fm.all('PayEndYear').value);
    	DutyGrid.setRowColData(1, 3, fm.all('InsuYear').value);
    	DutyGrid.setRowColData(1, 5, fm.all('PayEndYear').value);
    	//alert("asdfas"+PayIntv);
    	DutyGrid.setRowColData(0,18,fm.all('PayIntv').value);
    	DutyGrid.setRowColData(1,18,fm.all('PayIntv').value);
    	//alert(DutyGrid.getRowColData(0,18));
    	if (InsuYear==null||PayEndYear==null||InsuYear==''||PayEndYear=='')
    	{
    		alert("取主险保险期间或者缴费期限失败！");
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
	  alert("请选择责任！");
	  return false;	
	}
	/*if(fm.all('RiskCode').value=="00607000")
	{
			
			var tpayintv=DutyGrid.getRowColData(0,18);
			var tamnt=DutyGrid.getRowColData(0,14);
			if (tpayintv==0)//趸交
			{
				if(DutyGrid.getChkNo(1)==true)
				{
					alert("趸交不能选择豁免责任");
	  			return false;	
				}
			}
			if(DutyGrid.getChkNo(1)==true)
			{
				DutyGrid.setRowColData(1, 14, tamnt);
				DutyGrid.setRowColData(1, 18, tpayintv);
			}
	}
	if(fm.all('RiskCode').value=="140")
	{
      DutyGrid.setRowColData(0, 14, 0);
      DutyGrid.setRowColData(1, 14, 0);
	}
	
	if(DutyGrid.mulLineCount>0){ 
		//可以对险种条件校验
		var strChooseDuty="";
		for(i=0;i<=DutyGrid.mulLineCount-1;i++)
		{
			if(DutyGrid.getChkNo(i)==true)
			{
				strChooseDuty=strChooseDuty+"1";
				DutyGrid.setRowColData(i, 8, fm.all('GetYear').value);//保险年期单位
				if(fm.all('RiskCode').value=="00280000")
				{
					DutyGrid.setRowColData(i, 3, fm.all('InsuYear').value);//保险年期
					DutyGrid.setRowColData(i, 5, fm.all('PayEndYear').value);//保险年期
				}
				//DutyGrid.setRowColData(i, 3, fm.all('InsuYear').value);//保险年期
				//DutyGrid.setRowColData(i, 4, fm.all('InsuYearFlag').value);//保险年期单位
				//if(DutyGrid.getRowColData(i,18)==null||DutyGrid.getRowColData(i,18)==""){ //界面不录入缴费间隔则取默认值
				 // DutyGrid.setRowColData(i, 18, fm.all('PayIntv').value);//缴费间隔
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
	  		alert("请选择交费项！");
	  		return false;	
		}
	}
	
/*if(fm.all('RiskCode').value=="208000"||fm.all('RiskCode').value=="208001"||fm.all('RiskCode').value=="208002"||fm.all('RiskCode').value=="208003")
{
			if(PremGrid.mulLineCount>0){
		DutyGrid.setRowColData(i,13,DutyGrid.getRowColData(i,23)*fm.all('InsuredPeoples').value);//总保费=个人保费*人数
			DutyGrid.setRowColData(i,14,DutyGrid.getRowColData(i,24)*fm.all('InsuredPeoples').value);//总保额=个人保额*人数
    
      	                }
      	       else
      		{
                        fm.all('Prem').value=fm.all('Pprem').value*fm.all('InsuredPeoples').value;
                        fm.all('Amnt').value=fm.all('Pamnt').value*fm.all('InsuredPeoples').value;
      	}
      		alert(fm.all('StandbyFlag1').value);	
      		alert(fm.all('Prem').value);	
      		alert(fm.all('Amnt').value);
      		alert(fm.all('Pprem').value);	
      		alert(fm.all('Pamnt').value);
      		alert(fm.all('InsuredPeoples').value);	
    
      }*/
      
	if(fm.all('RiskCode').value=='211801')
	{
		//可以对险种条件校验
		var strChooseDuty="";
		for(i=0;i<=8;i++)
		{
			if(DutyGrid.getChkNo(i)==true)
			{
				strChooseDuty=strChooseDuty+"1";
				DutyGrid.setRowColData(i, 3, fm.all('InsuYear').value);//保险年期
				//alert("InsuYear=="+ fm.all('InsuYear').value);
				//DutyGrid.setRowColData(i, 4, fm.all('InsuYearFlag').value);//保险年期单位
				
				DutyGrid.setRowColData(i, 5, fm.all('PayEndYear').value);//交费年期
				//alert("PayEndYear"+ fm.all('PayEndYear').value);
				//DutyGrid.setRowColData(i, 6, fm.all('PayEndYearFlag').value);//交费年期单位
				DutyGrid.setRowColData(i, 9, fm.all('StandByFlag1').value);//是否在职  0在职 |1退休
				//alert("StandByFlag2"+ fm.all('StandByFlag2').value);
				DutyGrid.setRowColData(i, 10, fm.all('StandByFlag2').value);//公共责任7档保额
				//alert("StandByFlag2"+ fm.all('StandByFlag2').value);
				DutyGrid.setRowColData(i, 11, fm.all('StandByFlag3').value);//公共责任8档保额
				//alert("StandByFlag3"+ fm.all('StandByFlag3').value);
				DutyGrid.setRowColData(i, 13, fm.all('Prem').value);//保费
				//alert("Prem"+ fm.all('Prem').value);
				DutyGrid.setRowColData(i, 17, fm.all('FloatRate').value);//费率/折扣
				//alert("FloatRate"+ fm.all('FloatRate').value);
				DutyGrid.setRowColData(i, 19, fm.all('GetLimit').value);//免赔额
				//alert("GetLimit"+ fm.all('GetLimit').value);
				DutyGrid.setRowColData(i, 20, fm.all('GetRate').value);//赔付比例
				//alert("GetRate"+ fm.all('GetRate').value);
				DutyGrid.setRowColData(i, 18, fm.all('PayIntv').value);//缴费方式
			}
			else
		{
				strChooseDuty=strChooseDuty+"0";
			}
		}
		//alert(strChooseDuty);
		//fm.all('StandbyFlag1').value=strChooseDuty;
		return true;
		}
        
      	return true;	

}

//责任列表
function initDutyGrid()
{
    var iArray = new Array();
          
      try
      { 
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";         			//列宽
        iArray[0][2]=10;          			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
				iArray[1][0]="责任编码";
				iArray[1][1]="0px";
				iArray[1][2]=100;
				iArray[1][3]=3;
				iArray[1][4]="DutyCode";
				iArray[1][15]="RiskCode";
				iArray[1][16]=fm.RiskCode.value;
				
				iArray[2]=new Array();
				iArray[2][0]="责任名称";
				iArray[2][1]="0px";
				iArray[2][2]=100;
				iArray[2][3]=3;
				
				iArray[3]=new Array();
				iArray[3][0]="保险期间";
				iArray[3][1]="0px";
				iArray[3][2]=100;
				iArray[3][3]=3;
				
				iArray[4]=new Array();
				iArray[4][0]="保险期间单位";
				iArray[4][1]="0px";
				iArray[4][2]=100;
				iArray[4][3]=3;
				//iArray[4][11]="0|^M|月^D|天";          			//是否允许输入,1表示允许，0表示不允许
          		//iArray[4][10]="InsuYearFlag"; 
				
				iArray[5]=new Array();
				iArray[5][0]="终交年龄年期";
				iArray[5][1]="0px";
				iArray[5][2]=100;
				iArray[5][3]=3;
				
				iArray[6]=new Array();
				iArray[6][0]="终交年龄年期标志";
				iArray[6][1]="0px";
				iArray[6][2]=100;
				iArray[6][3]=3;
				
				iArray[7]=new Array();
				iArray[7][0]="领取期间单位";
				iArray[7][1]="0px";
				iArray[7][2]=100;
				iArray[7][3]=3;
				
				iArray[8]=new Array();
				iArray[8][0]="领取期间";
				iArray[8][1]="0px";
				iArray[8][2]=100;
				iArray[8][3]=3;
				
				iArray[9]=new Array();
				iArray[9][0]="备用属性字段1";
				iArray[9][1]="0px";
				iArray[9][2]=100;
				iArray[9][3]=3;
				
				iArray[10]=new Array();
				iArray[10][0]="备用属性字段2";
				iArray[10][1]="0px";
				iArray[10][2]=100;
				iArray[10][3]=3;
				
				iArray[11]=new Array();
				iArray[11][0]="备用属性字段3";
				iArray[11][1]="0px";
				iArray[11][2]=100;
				iArray[11][3]=3;
				
				iArray[12]=new Array();
				iArray[12][0]="计算方向";
				iArray[12][1]="0px";
				iArray[12][2]=100;
				iArray[12][3]=3;
				
				iArray[13]=new Array();
				iArray[13][0]="保费";
				iArray[13][1]="0px";
				iArray[13][2]=100;
				iArray[13][3]=3;
				
				
				iArray[14]=new Array();
				iArray[14][0]="保额";
				iArray[14][1]="0px";
				iArray[14][2]=100;
				iArray[14][3]=3;
				
				
					
				iArray[15]=new Array();
				iArray[15][0]="份数";
				iArray[15][1]="0px";
				iArray[15][2]=100;
				iArray[15][3]=3;			
																																					
				iArray[16]=new Array();
				iArray[16][0]="计算规则";
				iArray[16][1]="0px";
				iArray[16][2]=100;
				iArray[16][3]=3;
				iArray[16][4]="PolCalRule";
				
				iArray[17]=new Array();
				iArray[17][0]="费率/折扣";
				iArray[17][1]="0px";
				iArray[17][2]=100;
				iArray[17][3]=3;
				
				iArray[18]=new Array();
				iArray[18][0]="交费方式";
				iArray[18][1]="0px";
				iArray[18][2]=100;
				iArray[18][3]=3;
		
				
				iArray[19]=new Array();
				iArray[19][0]="免赔额";
				iArray[19][1]="0px";
				iArray[19][2]=100;
				iArray[19][3]=3;

 				iArray[20]=new Array();
				iArray[20][0]="赔付比例";
				iArray[20][1]="0px";
				iArray[20][2]=100;
				iArray[20][3]=3;
				       
				iArray[21]=new Array();
				iArray[21][0]="社保标记";
				iArray[21][1]="0px";
				iArray[21][2]=100;
				iArray[21][3]=3;
				       
				iArray[22]=new Array();
				iArray[22][0]="封顶线";
				iArray[22][1]="0px";
				iArray[22][2]=100;
				iArray[22][3]=3;
				
				iArray[23]=new Array();
				iArray[23][0]="保额每人";
				iArray[23][1]="0px";
				iArray[23][2]=100;
				iArray[23][3]=3;
				
				iArray[24]=new Array();
				iArray[24][0]="保费每人";
				iArray[24][1]="0px";
				iArray[24][2]=100;
				iArray[24][3]=3;
				
				iArray[25]=new Array();
				iArray[25][0]="保险止期";
				iArray[25][1]="0px";
				iArray[25][2]=100;
				iArray[25][3]=3;
     
				iArray[26]=new Array();
				iArray[26][0]="领取方式";
				iArray[26][1]="0px";
				iArray[26][2]=100;
				iArray[26][3]=3;

				iArray[27]=new Array();
				iArray[27][0]="归属规则";
				iArray[27][1]="0px";
				iArray[27][2]=100;
				iArray[27][3]=3;
				
				iArray[28]=new Array();
				iArray[28][0]="缴费规则";
				iArray[28][1]="0px";
				iArray[28][2]=100;
				iArray[28][3]=3;				
				
				iArray[29]=new Array();
				iArray[29][0]="红利分配方式";
				iArray[29][1]="0px";
				iArray[29][2]=100;
				iArray[29][3]=3;				
								
				iArray[30]=new Array();
				iArray[30][0]="免交比率";
				iArray[30][1]="0px";
				iArray[30][2]=100;
				iArray[30][3]=3;					
				
				iArray[31]=new Array();
				iArray[31][0]="起付线";
				iArray[31][1]="0px";
				iArray[31][2]=100;
				iArray[31][3]=3;
				
				iArray[32]=new Array();
		    iArray[32][0]="保险人数";
		    iArray[32][1]="50px";
		    iArray[32][2]=100;
		    iArray[32][3]=3;
													
       
       //add by huangen 对多责任的处理
       if(fm.all('RiskCode').value=="211701"||fm.all('RiskCode').value=="211801")
       {
          //责任代码
          iArray[1][0]="责任编码";
	  	  iArray[1][1]="60px";            		//列宽
          iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  
          //责任名称
	  	  iArray[2][1]="150px";            		//列宽
          iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
				
          		iArray[3]=new Array();
				iArray[3][0]="保险期间";
				iArray[3][1]="50px";
				//iArray[3][2]=100;
				iArray[3][3]=1;
				
				iArray[4]=new Array();
				iArray[4][0]="保险期间单位";
				iArray[4][1]="30px";
				//iArray[4][2]=100;
				iArray[4][3]=0;
				iArray[4][11]="0|^M|月";          			//是否允许输入,1表示允许，0表示不允许
          		iArray[4][10]="InsuYearFlag"; 
          		iArray[4][14]="M";
				
				iArray[5]=new Array();
				iArray[5][0]="缴费期间";
				iArray[5][1]="50px";
				//iArray[5][2]=100;
				iArray[5][3]=1;
				
				iArray[6]=new Array();
				iArray[6][0]="缴费期间单位";
				iArray[6][1]="30px";
				//iArray[6][2]=100;
				iArray[6][3]=1;	
				iArray[6][11]= "0|^M|月";
				iArray[6][10]="PayEndYearFlag"; 
				iArray[6][14]="M";		  
		  //add by jyl 20061016   更改211701,按照一般险种来处理
		  //iArray[16][0]="计算规则";
		  //iArray[16][1]="40px";
		  //iArray[16][2]=100;
		  //iArray[16][3]=2;
		  //iArray[16][4]="PolCalRule";
		  //iArray[16][14] = "3";

			iArray[9][0]="是否在职";
			iArray[9][1]="50px";
		 //	iArray[9][2]=100;
			iArray[9][3]=1;
			iArray[9][10]="StandbyFlag1";
			iArray[9][11]="0|^0|在职^1|退休";
			
		  //备用字段2 公共责任7档保额
			iArray[10][0]="公共责任7档保额";
			iArray[10][1]="60px";
			iArray[10][3]=1;
			
		  //备用字段3,公共责任8档保额 
		  iArray[11][0]="公共责任8档保额";
		  iArray[11][1]="60px";
		  iArray[11][3]=1;
		  
		  iArray[12][0]="计算方向";
		  iArray[12][1]="0px";
		  iArray[12][2]=100;
		  iArray[12][3]=2;
		  iArray[12][4]="PremToAmnt";
		  iArray[12][14] = "O";
		  
		  iArray[13][0]="保费";
		  iArray[13][1]="40px";
		  iArray[13][2]=100;
		  iArray[13][3]=1;				  
		  
		  iArray[17][0]="费率/折扣";
		  iArray[17][1]="40px";
		  iArray[17][2]=100;
		  iArray[17][3]=1;
		  
		  iArray[19]=new Array();
		  iArray[19][0]="免赔额";
		  iArray[19][1]="60px";
		  //iArray[30][2]=100;
		  iArray[19][3]=1;					
				
		  iArray[20]=new Array();
		  iArray[20][0]="赔付比例";
		  iArray[20][1]="60px";
		  //iArray[31][2]=100;
		  iArray[20][3]=1;
		  
		  //add end												
      	
       }
       
  	
  	      if(fm.all('RiskCode').value=="212402"||fm.all('RiskCode').value=="212401")
  	      {
  	      	//alert(PolTypeFlag);
  	        // if(PolTypeFlag=='2' || PolTypeFlag=='3')
  	        // {
            //     iArray[0][1]="30px";         			//列宽
            //     iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                 
                 //责任代码
	  			 //iArray[1][1]="60px";            		//列宽
                 //iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
	               
                 //责任名称
	  			 //iArray[2][1]="120px";            		//列宽
                 //iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                 
	               //保费
                 //iArray[13][1]="80px";            		//列宽
                 //iArray[13][3]=1;              			//是否允许输入,1表示允许，0表示不允许  	      	
                 //iArray[13][1]="80px";            		//列宽
                 //iArray[13][3]=1;              			//是否允许输入,1表示允许，0表示不允许
           /*
                 //领取年期
                 iArray[8][0]="领取年龄"
                 iArray[8][1]="40px";            		//列宽
                 iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
                 iArray[8][10]="GetYear"
	               
	             //领取年期单位
	             iArray[7][0]="领取年龄单位"
                 iArray[7][1]="40px";            		//列宽
                 iArray[7][3]=2;              			//是否允许输入,1表示允许，0表示不允许
                 iArray[7][10]="GetYearFlag"
                 iArray[7][11]="0|^A|岁"
*/

  	         //}
  	         //else
  	         //{
                 iArray[0][1]="30px";         			//列宽
                 iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                 
                 //责任代码
	  			 iArray[1][1]="60px";            		//列宽
                 iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
	               
                 //责任名称
	  			 iArray[2][1]="120px";            		//列宽
                 iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                 
	               //保费
                 iArray[13][1]="80px";            		//列宽
                 iArray[13][3]=1;              			//是否允许输入,1表示允许，0表示不允许
                 //领取年期
                 iArray[8][0]="领取年龄"
                 iArray[8][1]="40px";            		//列宽
                 iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
                 iArray[8][10]="GetYear"
	               
	             //领取年期单位
	             iArray[7][0]="领取年龄单位"
                 iArray[7][1]="40px";            		//列宽
                 iArray[7][3]=2;              			//是否允许输入,1表示允许，0表示不允许
                 iArray[7][10]="GetYearFlag"
                 iArray[7][11]="0|^A|岁"
                 

                 
                 //缴费规则
	             iArray[28][0]="缴费规则"
                 iArray[28][1]="0px";            		//列宽
                 iArray[28][2]=40;
                 iArray[28][3]=2;              			//是否允许输入,1表示允许，0表示不允许
                 iArray[28][4]="PayRuleCode";
                 iArray[28][15]="GrpContNo";
				 iArray[28][16]=tt;
				         

	               
				 iArray[27][0]="归属规则";
				 iArray[27][1]="0px";
				 iArray[27][2]=100;
				 iArray[27][3]=2;
				 iArray[27][4]="AscriptionRuleCode";
				 iArray[27][15]="GrpContNo";
				 iArray[27][16]=tt;
	
                 
                 iArray[29][0]="红利分配方式";
				 iArray[29][1]="50px";
				 iArray[29][2]=100;
				 iArray[29][3]=2;
                 iArray[29][10]="BonusGetMode"
                 iArray[29][11]="0|^1|累积生息^2|领取现金^3|抵缴保费^4|增额交清^5|其他"


        //}	
          }     

	      
	     
   
      DutyGrid = new MulLineEnter( "fm" , "DutyGrid" ); 
      //这些属性必须在loadMulLine前
      DutyGrid.mulLineCount = 0;   
      DutyGrid.displayTitle = 1;
      DutyGrid.canChk = 1;
      DutyGrid.loadMulLine(iArray);  
      //DutyGrid.checkBoxSel(1);

      //这些操作必须在loadMulLine后面
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        //alert(ex);
      }
}

function setGetDutyKind()
{
	alert("开始给GetDutyKind进行赋值!");
}


//保费项列表
function initPremGrid()
{
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="责任编码";    	//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="交费编码";         			//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="交费名称";         			//列名
      iArray[3][1]="150px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保费";         			//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="分配比例";         			//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="关联帐户号";         			//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[7]=new Array();
      iArray[7][0]="帐户分配比率";         			//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[8]=new Array();
      iArray[8][0]="管理费比例";         			//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="交费占员工工资比例";         			//列名
      iArray[9][1]="120px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许
	  
     
        if(typeof(window.spanPremGrid) == "undefined" )
        {
              //alert("out");
              return false;
        }
        else
        {
      			
      			
      			
      			//alert("in");
      	    PremGrid = new MulLineEnter( "fm" , "PremGrid" );
	  	    //这些属性必须在loadMulLine前
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
    if(fm.all('StandbyDuty').value!=null&&fm.all('StandbyDuty').value!=""){
      var rowCount = DutyGrid.mulLineCount;
      DutyGrid.addOne();
      saveValue(rowCount);
    }
    else{
      alert("请选择备用责任！");
    } 
}
function saveValue(index){
    var mulLineObj = DutyGrid;
    var StandbyDutyCode = fm.all('StandbyDuty').value;
    var StandbyDutyCodeName = fm.all('StandbyDutyName').value;
    //alert("Code:"+fm.all('StandbyDuty').value+"  name:"+fm.all('StandbyDutyName').value);
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
				DutyGrid.setRowColData(i, 13, fm.all('InsuYear').value);//保险年期
				DutyGrid.setRowColData(i, 12, fm.all('InsuYearFlag').value);//保险年期单位
				//DutyGrid.setRowColData(i, 11, fm.all('PayIntv').value);//缴费方式
			}
			else
			{
				strChooseDuty=strChooseDuty+"0";
			}
		}
}
*/


//份数算保费的险种份数不能为空
//modify by malong 2005-7-8
function chkMult(){
  var tSql="";
  tSql="select AmntFlag from lmduty a,lmriskduty b where b.riskcode='"+fm.all('RiskCode').value+"' and a.dutycode=b.dutycode";
  var arrResult=easyExecSql(tSql);
  
  if(arrResult[0]=="2" && fm.all('inpNeedDutyGrid').value == "1"){
  	for (var index=0;index<DutyGrid.mulLineCount;index++)
  	{
    	//alert(DutyGrid.getRowColData(index,15));
    	if(DutyGrid.getChkNo(index)){
    		if(DutyGrid.getRowColData(index,15)==0)
    		{
      		alert("份数不能为零!");
      		return false;	
    		}
    		else if (!isInteger(DutyGrid.getRowColData(index,15)))
    		{
    			alert("份数必须为整数!");
    			return false;
    		}
    	}    	
  	}
	}
  return true;	
}


//校验CheckBox：108和280险种只允许同时选中一个责任项
//create by malong 2005-7-13
function chkDutyNo(){
	if(fm.all('RiskCode').value=="00108003"||fm.all('RiskCode').value=="00108002"||fm.all('RiskCode').value=='00108001'||fm.all('RiskCode').value=='00108000' || fm.all('RiskCode').value=='00280000'|| fm.all('RiskCode').value=='00328000'){
		var DutyNo=0;
		//alert(DutyGrid.getRowColData(0,5));
		for (var index=0;index<DutyGrid.mulLineCount;index++){
		  if(DutyGrid.getChkNo(index)==true){
		    DutyNo=DutyNo+1;	
		  }	
		}
		if(DutyNo>1){
		alert("该险种下只能同时选择一个责任项!");
		return false;
		}
		else{
		return true;
		}		
	}
	return true;
	
}		

/*function showFloatRate(tContPlanCode){
  //alert("calRule:"+fm.all('CalRule').value+"plan:"+tContPlanCode+"grpcontno:"+fm.all('GrpContNo').value);
  var arrResult=null;
  var tSql="";
  if(tContPlanCode!=null&&tContPlanCode!=""){
      	
  }
  if(fm.all('CalRule').value=="1"){
    divFloatRate.style.display="none";
    divFloatRate2.style.display="";	
  }
  else if(fm.all('CalRule').value=="2"){
    divFloatRate.style.display="";
    divFloatRate2.style.display="none";
  }
  else {
    divFloatRate.style.display="none";
    divFloatRate2.style.display="none";	
  }	
}*/

/*function getOtherInfo(payEndYear){
  //alert("payIntv:"+fm.all('PayIntv').value);
  if(fm.all('RiskCode').value=="208300"){
    if(payEndYear==1000){
      fm.all('PayEndYearFlag').value='A';
      fm.all('GetYear').className='code';
      //fm.all('GetYearFlag').className='code';
      //fm.all('GetYear').CodeData="0|^1|即时领取^55|55岁领取^60|60岁领取";
      fm.all('GetYear').value="";
      fm.all('GetYearFlag').value="";	
    }
    else {		
      if(payEndYear<22){
        fm.all('PayEndYearFlag').value='A';	
      }	
      else {
        fm.all('PayEndYearFlag').value='A';	
      }
      fm.all('GetYear').className='readonly';
      fm.all('GetYearFlag').className='readonly';
      if(payEndYear<22){
      	fm.all('GetYear').value=1;
        fm.all('GetYearFlag').value='A';	
      }
      else{
        fm.all('GetYear').value=payEndYear;
       fm.all('GetYearFlag').value='A';
     }
    }
  }
}*/

/*function getGetYearFlag(getYear){
  if(fm.all('RiskCode').value=='208300'){
    if(getYear=='1'){
      fm.all('GetYearFlag').value='A';	
    }	
    else {
      fm.all('GetYearFlag').value='A';	
    }
  }	
}*/

function getPayRuleInfo()
{/*
if(fm.all('RiskCode').value=="208300"){
 var tSql="select distinct payrulecode,payrulename from lcpayrulefactory where grpcontno='"+fm.all('GrpContNo').value+"' and riskcode='"+fm.all('RiskCode').value+"'";
  var arr=easyExecSql(tSql);
  if (arr.length>0){
    var tCodeData="0|";
   for (var i=0;i<arr.length;i++){
   	tCodeData=tCodeData+"^"+arr[i][0]+"|"+arr[i][1];
    }
   fm.all('PayRuleCode').CodeData=tCodeData;
    
  	}
	}*/
    /*delete by jyl 20061016 将作为一般险种处理
    if(fm.all('RiskCode').value=="211701")
    {
        var tSql="select distinct payrulecode,payrulename from lcpayrulefactory where grpcontno='"+fm.all('GrpContNo').value+"' and riskcode='"+fm.all('RiskCode').value+"'";
        var arr=easyExecSql(tSql);
        
        if(arr==null||arr==""||arr=="null")
        {
            fm.all('PayRuleCode').CodeData="0|^错误|没有录入缴费规则，请检查！";
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
                fm.all('PayRuleCode').CodeData=tCodeData;
          
            }
            else
            {
                fm.all('PayRuleCode').CodeData="0|^错误|没有录入缴费规则，请检查！";
            }
        }
    }	
    delete End */
}
