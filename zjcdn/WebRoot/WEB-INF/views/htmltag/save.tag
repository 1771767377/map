@var formId = formId!'save-form'; //form表单id
@var reloadUrl = reloadUrl!'false'; //是否url刷新
@var isHide = isHide!'no'; //是否隐藏按钮 默认no
@var subBtnId = subBtnId!(formId+'-save');
@var validId = validId!(strutil.replace(formId,"-",""));
@var config = config!'false';
@var data = data!'';
@var okText = okText!'确 定';
@var callBack = callBack!'';

@if(isHide == "no"){
<div class="width-100 clearfix" tag-save-btn style="bottom: 0px;left: 0px;position:absolute;">
	<span class="btn btn-primary btn-sm bigger-110 width-50  pull-left"  id="${subBtnId}">
		<i class="ace-icon fa fa-floppy-o align-top bigger-125"></i> ${okText}
	</span>
	<span class="btn btn-yellow btn-sm bigger-110 width-50 pull-right" id="${formId}-cancel">
		<i class="ace-icon fa fa-times align-top  bigger-125"></i> 取 消
	</span>
</div>
@}
<script type="text/javascript">

var count = 0;
$(function(){
	
var ${validId} = $("#${formId}").Validform({
		ajaxPost : true,
		beforeSubmit:function(curform){
			var loadi = layer.load(5,2);
			$("#${formId}").data('loadi',loadi);
		},
		callback:function(data){
			layer.close($("#${formId}").data('loadi'));
			if(data>0) {
				layer.msg('操作成功', 1, 1,function(){
					if("${callBack}" != undefined && "${callBack}".length > 0){
						${callBack};
					}else{
						if("${reloadUrl}" == "true"){
							location.reload();
						}else{
							//$curmenu.trigger('click');
						}
					}
					layer.closeAll();
				});
			}else{
				layer.msg('操作失败', 3, 1);
			}
		},
		tiptype : function(msg, o, cssctl) {
			if (!o.obj.is("form")) {
				if (o.type != 2) {
				 	if(msg.indexOf("所填信息没有经过验证") >= 0){//触发ajax url
				 		if(count > 0){
				 			layer.alert("请检查必须唯一的值",3,"检验错误");
				 		}else{//第一次直接触发
				 			count++;
					 		$(o.obj).focus();
					 		$(o.obj).blur();
				 		}
				 	}else if(msg.indexOf("请换一个") >=0 || msg.indexOf("占用") >=0){
				 		layer.alert(msg,3,"检验错误");
				 	}else{
						tip.errorTip(msg,o.obj);
				 	}
				}
			}
		},
		tipSweep : true
	});

	$("#${subBtnId}").click(function() {
		if("${config}" == "true"){
			${tagBody!}
		}
		${validId}.submitForm(false);
		return false;
	}); 
	
	$("#${formId}-cancel").click(function(){
		layer.closeAll();
		return false;
	});
	
	$("#${formId} input,#${formId} textarea").blur(function(){
		layer.closeTips();
		return false;
	})

});
</script>