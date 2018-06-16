var toggleStats = function () {
    var mydiv = document.getElementById('character');
    if (mydiv.style.display === 'none' || mydiv.style.display === '')
      mydiv.style.display = 'block';
    else
      mydiv.style.display = 'none'
  }