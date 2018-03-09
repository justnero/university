(function($) {
	$(function() {
		var table = $('#table-group');
		$('#action')
			.on('change', function() {
				if($(this).val() == 'encrypt') {
					table.slideDown();
				} else {
					table.slideUp();
				}
			})
			.trigger('change');
	});
})(jQuery);