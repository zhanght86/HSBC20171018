Ext.form.FileUploadField = Ext.extend(Ext.form.TextField,  {
    buttonText: 'Browse...',
    buttonOnly: false,
    buttonOffset: 3,
    autoSize: Ext.emptyFn,

    initComponent: function(){
        Ext.form.FileUploadField.superclass.initComponent.call(this);
    },

    onRender : function(ct, position){
        Ext.form.FileUploadField.superclass.onRender.call(this, ct, position);
        
        this.wrap = this.el.wrap({cls:'x-form-field-wrap x-form-file-wrap'});
        this.el.addClass('x-form-file-text');
        this.fileInput = this.createChild();

        var btnCfg = Ext.applyIf(this.buttonCfg || {}, {
            text: this.buttonText
        });
        this.button = new Ext.Button(Ext.apply(btnCfg, {
            renderTo: this.wrap,
            cls: 'x-form-file-btn' + (btnCfg.iconCls ? ' x-btn-icon' : '')
        }));
        
        if(this.buttonOnly){
            this.el.hide();
            this.wrap.setWidth(this.button.getEl().getWidth());
        }
    },

    getFileInput: function(){
        return this.fileInput;
    },

    createChild: function(){
        return 	this.wrap.createChild({
            id: this.id + '_file',
            name: this.name + '_file',
            cls: 'x-form-file',
            tag: 'input', 
            type: 'file',
            size: 1,
            onChange : "fileUploadChange({id:'"+this.id+"',name:'"+this.name + '_file'+"'});"
        });
    },
	
    onResize : function(w, h){
        Ext.form.FileUploadField.superclass.onResize.call(this, w, h);
        this.wrap.setWidth(w);
        if(!this.buttonOnly){
            var w = this.wrap.getWidth() - this.button.getEl().getWidth() - this.buttonOffset;
            this.el.setWidth(w);
        }
    },
    preFocus : Ext.emptyFn,
    getResizeEl : function(){
        return this.wrap;
    },
    getPositionEl : function(){
        return this.wrap;
    },
    alignErrorIcon : function(){
        this.errorIcon.alignTo(this.wrap, 'tl-tr', [2, 0]);
    }
    
});
Ext.reg('fileuploadfield', Ext.form.FileUploadField);

function fileUploadChange(obj){
	
	//开始
	if(document.getElementById('submitData').disabled ||document.getElementById('submitData').style.display =='none'){
		
		alert("必须生成决策表后才能上载数据！");
		return false;
	}
	
	
	$("#"+obj.id)
	.ajaxStart(function(){
		
		Ext.MessageBox.wait("File uploading...", 'Please Wait...');
	})
	.ajaxComplete(function(){
		Ext.MessageBox.hide();
	});

	$.ajaxFileUpload
	(
		{
			url:'../ibrms/FileUploadSave.jsp?action=save&rs_file='+obj.name,
			secureuri:false,
			fileElementId: obj.id+'_file',
			dataType: 'json',
			beforeSend:function()
			{
				
			},
			complete:function()
			{
				//完成
			},
			success: function (data, status)
			{
				//alert("data "+data.success);
				
				if(typeof(data.error) != 'undefined')
				{
					alert(data.error);
					return false;
				}
				displayJsonData(data);
				$("#"+obj.id).val('上载成功');
				$("#JsonDataFromExcel").val( Ext.util.JSON.encode(data));
			},
			error: function (data, status, e)
			{
				alert(e.message);
			}
		}
	)
	return false;
}