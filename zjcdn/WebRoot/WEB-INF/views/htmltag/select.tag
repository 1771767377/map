@var id = id!"chosen-select";
@var name = name!;
@var width = width!"100%";
@var class = class!;
@var placeholder = placeholder !'';
@var value=value!;

<select class="chosen-select ${class}"  data-placeholder="${placeholder}" name="${name}" id="${id}" value="${value}">
	${tagBody!}
</select>
<script>
$(function(){
	$("#${id}").chosen({
		width: "${width}",
		disable_search_threshold: 10,
		search_contains: true
	}); 
});
</script>