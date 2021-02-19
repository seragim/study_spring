$('#attach-file').on('change', function(){
	if( this.files[0] ){
		$('#file-name').text( this.files[0].name );
		$('#delete-file').css('display', 'inline-block');
	}
});

/*선택한 첨부파일이 이미지파일인 경우 미리보기되게*/
$('#attach-file').on('change', function(){
	var attach = this.files[0];
	if( attach ){
		if( isImage( attach.name ) ){
			var img = '<img src="" class="file-img" id="preview-img" />';
			$('#preview').html( img );
			
			var reader = new FileReader();
			reader.onload = function(e){
				$('#preview-img').attr('src', e.target.result);
			}
			reader.readAsDataURL( attach );
			
		}else{
			$('#preview').html( '' );
		}
	}

});


$('#delete-file').on('click', function(){
	$('#file-name').text('');
	$('#attach-file').val('');
	$('#delete-file').css('display', 'none');
	//삭제시 이미지 미리보기태그가 있는 경우 미리보기이미지 없애기
	if( $('#preview').length > 0 ) $('#preview').html( '' );  
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
	var imgs = [ 'jpg', 'jpeg', 'png', 'gif', 'bmp' ];
	if( imgs.indexOf(ext) > -1 ) return true;
	else return false;
}