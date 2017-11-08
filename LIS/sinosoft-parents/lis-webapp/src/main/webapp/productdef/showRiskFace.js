

//程序名称：PD_LMDutyGetClmCal.js
//程序功能：责任给付赔付扩充计算公式
//创建日期：2009-3-16
//该文件中包含客户端需要处理的函数和事件
var showInfo;	function returnParent(){		top.opener.focus();		top.close();}
function initAllInput()
{
	//alert('222');
	       lockPage(""+"数据加载中..........."+"");
        		//alert('1');
  			jQuery.post(
  			"./initAllRiskParams.jsp?Type=Input&RiskCode="+jRiskCode+"&StandbyFlag1="+jStandbyFlag1,
  			//params,
  					function(data) {
							var RuleMapArray = data;
							//alert('RuleMapArray.length:'+RuleMapArray.length);
							try{
								  $('#DivLCRiskPay').hide();
								  $('#DivLCRiskDuty').hide();
									for(i=0;i<RuleMapArray.length;i++)
									{
										var tType = RuleMapArray[i].inputType;
										var detail = RuleMapArray[i].detail;
										
										var tDutyPayFlag = "";
										
										if(detail!=null&&detail!='')
										{
												//alert('tType'+tType);
											//prompt('',detail);
											if(tType=='Risk')
											{
												//if(detail==null||detail=='')
												//{
												//	detail = "<div>请先选择险种要素</div>";
												//}
												//$(document.body).append(detail);
												//divRisk
												//alert($('div.divRisk'));
												$('div.divRisk').html(detail);
												$(document.body).append("<hr>");
											}
											else if(tType=='Pay')
											{
												 $('#DivLCRiskPay').show();
												initPayForm();
												
											}
											else if(tType=='Duty')
											{
												initDutyForm();
												 $('#DivLCRiskDuty').show();
											}
											
										}
										
									}		
									var tbutton = "<div id=\"inputdetail\" class=\"demo-description\" style=\"display: " + ( "query"==contOpt ? "none" : "" )  + "\"><input type='button' class=common  value='"+"保"+"  "+"存"+"' onclick='saveRiskFace();'></div>";

									$(document.body).append(tbutton);
									$( "#ulRisk" ).sortable({
											revert: true});
	
									$( "ul, li" ).disableSelection();
		
									unLockPage();
									
		  				}
		  				catch(ex)
		  				{
		  					myAlert(ex);
		  				}
					},"json" 
				);
}



	function saveRiskFace()
	{
		var riskOrder = "";
		var ul = document.getElementById('ulRisk');
		var lis = ul.getElementsByTagName("li");
		for(i = 0 ; i < lis.length ; i++){
  		//alert(lis[i].id+":"+lis[i].innerHTML);
  		riskOrder = riskOrder  + lis[i].id + "#";
    }
	//	alert(riskOrder);
		
			lockPage(""+"数据保存中..........."+"");
	   $.ajax({
            type:'POST',
            url:'showRiskFaceSave.jsp',
            data:'riskOrder='+riskOrder+'&RiskCode='+jRiskCode+'&StandbyFlag1='+jStandbyFlag1,
            success:function(data){
            	//alert(data.trim());
            	unLockPage();
            	myAlert(""+"保存完毕"+"");
            	parent.refresh_tab();
            	return true;
                if(data){
                 
                }else{
                }
            }
        });
        
	}
	
	//显示缴费和责任选项
	
	function initPayForm()
{
	try{
		//修改初始化方式
			//初始化责任列表
		jQuery.post(
  			"./initDutyPayColSel.jsp?Type=InitPay&RiskCode="+jRiskCode,
  			//params,
  					function(data) {
							var RuleMapArray = data;
							try{
									for(i=0;i<RuleMapArray.length;i++)
									{
										var tType = RuleMapArray[i].inputType;
										var detail = RuleMapArray[i].detail;
										
										
										if(detail!=null&&detail!='')
										{
											$('#divPay').append(detail);
											 $("#divPay").height('150px');
											$('#payGrid').datagrid(
											{ fit:true,
												rownumbers:true,
												pagination:false,
 												Height:500, 
												title:''+"险种缴费信息"+'',

            url:'initAllRiskParams.jsp',
 					 queryParams:{
			   			  	  	//RiskCode:jRiskCode
			   			  	  	RiskCode:jRiskCode
			   			  	  	,Type:'PayGrid'
			   		},
            dataType:'json', 

						sortName: 'idx',

						sortOrder: 'asc',

						idField:'idx',
						frozenColumns:[[  
            {field:'ck',checkbox:true}  
        ]]  						
												
												});
										}
									}		
		  				}
		  				catch(ex)
		  				{
		  					myAlert(ex);
		  				}
					},"json" 
				);		
		
		
		
		
		
		
		
		
		/*
		
		
		
			$('#payGrid').datagrid({

            title:'险种缴费信息',

            url:'initAllRiskParams.jsp',
 					 queryParams:{
			   			  	  	//RiskCode:jRiskCode
			   			  	  	RiskCode:jRiskCode
			   			  	  	,Type:'PayGrid'
			   		},
            dataType:'json', 

						sortName: 'idx',

						sortOrder: 'asc',

						idField:'idx',
            columns:[[
            {field:'ck',checkbox:true},
            {title:'序号',field:'idx',width:50,sortable:true ,align:'center'},
             {title:'责任编码',field:'DutyCode',width:100,sortable:true ,align:'center'},
	           {title:'缴费编码',field:'PayPlanCode',width:100,sortable:true ,align:'center'},
	           {title:'缴费名称',field:'PayPlanName',width:200,sortable:true ,align:'center'},
             {title:'保费',field:'Prem',width:400,align:'center',sortable:true}
            ]],
            
				loadMsg:"正在加载数据,请稍候"
				,onLoadSuccess: function()
			   {
			   	}			  	
        });
*/
	
	}catch(re){
		//alert(re);
		//alert("HTPeopServManaInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}


	
	function initDutyForm()
{
	try{
			//修改初始化方式
			//初始化责任列表
		jQuery.post(
  			"./initDutyPayColSel.jsp?Type=InitDuty&RiskCode="+jRiskCode,
  			//params,
  					function(data) {
							var RuleMapArray = data;
						//	alert('RuleMapArray.length:'+RuleMapArray.length);
							try{
									for(i=0;i<RuleMapArray.length;i++)
									{
										var tType = RuleMapArray[i].inputType;
										var detail = RuleMapArray[i].detail;
										
										
										if(detail!=null&&detail!='')
										{
											$('#divDuty').append(detail);
											 $("#divDuty").height('150px');
											$('#dutyGrid').datagrid(
											{ fit:true,
												rownumbers:true,
												pagination:false,
 												Height:500, 
												title:''+"险种责任信息"+'',

            url:'initAllRiskParams.jsp',
 					 queryParams:{
			   			  	  	//RiskCode:jRiskCode
			   			  	  	RiskCode:jRiskCode
			   			  	  	,Type:'DutyGrid'
			   		},
            dataType:'json', 

						sortName: 'idx',

						sortOrder: 'asc',

						idField:'idx',
						frozenColumns:[[  
            {field:'ck',checkbox:true}  
        ]]  						
												
												});
										}
									}		
		  				}
		  				catch(ex)
		  				{
		  					myAlert(ex);
		  				}
					},"json" 
				);		
		
		//alert(tcol);
		/*
		var tcol= [[
            {field:'ck',checkbox:true},
            {title:'序号',field:'idx',width:50,sortable:true ,align:'center'},
             {title:'责任编码1',field:'DutyCode',width:100,sortable:true ,align:'center'},
	           {title:'责任名称',field:'DutyName',width:200,sortable:true ,align:'center'},
             {title:'保费',field:'Prem',width:400,align:'center',sortable:true}
            ]];
            */
            
            /*
			$('#dutyGrid').datagrid({

            title:'险种缴费信息',

            url:'initAllRiskParams.jsp',
 					 queryParams:{
			   			  	  	//RiskCode:jRiskCode
			   			  	  	RiskCode:jRiskCode
			   			  	  	,Type:'DutyGrid'
			   		},
            dataType:'json', 

						sortName: 'idx',

						sortOrder: 'asc',

						//idField:'idx',

            columns:[[
            {field:'ck',checkbox:true},
            {title:'序号',field:'idx',width:50,sortable:true ,align:'center'},
             {title:'责任编码',field:'DutyCode',width:100,sortable:true ,align:'center'},
	           {title:'责任名称',field:'DutyName',width:200,sortable:true ,align:'center'},
             {title:'保费',field:'Prem',width:400,align:'center',sortable:true}
            ]],
        
				loadMsg:"正在加载数据,请稍候"
				,onLoadSuccess: function()
			   {
			   	}			  	
        });
*/
	
	}catch(re){
		//alert(re);
		//alert("HTPeopServManaInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}
