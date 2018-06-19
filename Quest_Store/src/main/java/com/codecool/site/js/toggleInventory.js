var toggleInventory = function () {
  var mydiv = document.getElementById('inventory');
  if (mydiv.style.display === 'flex' || mydiv.style.display === '')
    mydiv.style.display = 'none';
  else
    mydiv.style.display = 'flex'
}