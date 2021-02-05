$('#attach-file').on('change', function(){
	if( this.files[0] ){
		$('#file-name').text( this.files[0].name );
		$('#delete-file').css('display', 'inline-block');
	}
});

$('#delete-file').on('click', function(){
	$('#file-name').text('');
	$('#attach-file').val('');
	$('#delete-file').css('display', 'none');
});

function emptyCheck(){
	var ok = true;
	$('.chk').each(function(){
		if( $(this).val()=='' ){
			alert( $(this).attr('title')+ ' 입력하세요');
			$(this).focus();
			ok = false;
			return ok;
		}
	});
	return ok;
}