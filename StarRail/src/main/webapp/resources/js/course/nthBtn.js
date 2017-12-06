
$(window).resize(function() {
  if ($(window).width() <= 600) {
    $('#beds-baths-group').removeClass('btn-group');
    $('#beds-baths-group').addClass('btn-group-vertical');
  } else {
    $('#beds-baths-group').addClass('btn-group');
    $('#beds-baths-group').removeClass('btn-group-vertical');
  }
});
