
//初始化
function initForm(){

	$('#TaskSet').datagrid({

            title:'已保存基本任务列表',

            //iconCls:'icon-edit',

            //queryParams:{operator:$("#operator").val()} ,

            url:'TaskQuery.action',

            dataType:'json', 

						sortName: 'TaskCode',

						sortOrder: 'asc',

						idField:'TaskCode',

			frozenColumns:[[

	                //{field:'ck',checkbox:true},

	                {title:'基本任务编码',field:'TaskCode',width:100,sortable:true ,align:'center'}

						]],

            columns:[[

                {field:'TaskDescribe',title:'任务描述',width:200,align:'center',sortable:true},

                {field:'TaskClass',title:'任务处理类',width:200,align:'center',sortable:false}

            ]],

           

            rownumbers:true,

						singleSelect:true,

						striped: true,

						nowrap: true,

						remoteSort: false,

            pagination:true,

             onSelect:function(rowIndex,rowData)

             {

              	//alert('rowIndex:'+rowIndex);
 

              	//alert('rowData:'+rowData.TaskCode);

              	$('#TaskCode').attr('value',rowData.TaskCode);

              	$('#TaskDescribe').attr('value',rowData.TaskDescribe); 

              	$('#TaskClass').attr('value',rowData.TaskClass);
             	}

        });

}



function appendTask(){

				//	parent.reloadTable(); 

				//	return;

				lockScreen('正在处理数据,请稍候...');

					var params = { 

        	//	beforeSubmit:  function(){

        	//		return $('#Logon').form('validate');

        	//	},

        		success: function(data){

        			reloadTable();

        			unlockScreen();

								if(data=="success"){

									$.messager.alert('成功',data,'info');

									//window.location.href="main/main.jsp";

									

									

									

								}else{

									$.messager.alert('错误',data,'error');

								}

        		}, 

        		url:       'appendTask.action', 

        		type:      'post', 

        		dataType:  'json' 

        //timeout:   3000 

    			};

    			$('#TaskPlan').ajaxSubmit(params); 

}



/* 

刷新任务

*/

function reloadTable()

{

	$('#TaskSet').datagrid('reload');

}



function deleteTask()

{
 

	var tSelected = $('#TaskSet').datagrid('getSelected');

	var tTaskCode;

	

	if (tSelected){

		lockScreen('正在处理数据,请稍候...');

				tTaskCode = tSelected.TaskCode;

				//$('#TaskPlanCode').attr('value',tTaskPlanCode);

				//return ;

					var params = { 

        	//	beforeSubmit:  function(){

        	//		return $('#Logon').form('validate');

        	//	},

        		success: function(data){

        			unlockScreen();

        			$('#TaskCode').attr('value','');

              	$('#TaskDescribe').attr('value',''); 

              	$('#TaskClass').attr('value','');

        			reloadTable();

								if(data=="success"){

									

									$.messager.alert('成功',data,'info');

									

									

								}else{

									$.messager.alert('错误',data,'error');

								}

        		}, 

        		url:       'deleteTask.action', 

        		type:      'post', 

        		dataType:  'json' 

    			};

    			$('#TaskPlan').ajaxSubmit(params);  	

	}

 

	else

	{

		$.messager.alert('错误','请选择一个任务!','error');	

	} 	
}