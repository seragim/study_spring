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

//선택한 파일이 이미지인지 판단
function isImage(filename){
	//abc.txt, abc.png, abc.hwp, abc.JPG
	var ext = filename.substring( filename.lastIndexOf('.')+1 ).toLowerCase();
	var imgs = ['jpg', 'jpeg', 'png', 'gif', 'bmp'];
	if(imgs.indexOf(ext) > -1) return true;
	else return false;
}