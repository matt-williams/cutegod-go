function renderTerrain(div, data) {
  imgMap = {
    "e": "img/block-earth.png",
    "g": "img/block-grass.png",
    "p": "img/block-plain.png",
    "s": "img/block-stone.png",
    "w": "img/block-water.png",
    "W": "img/block-wood.png",
  };
  while (div.firstChild) {
    div.removeChild(div.firstChild);
  }
  for (var y = 0; y < data.length; y++) {
    for (var x = 0; x < data[y].length; x++) {
      var c = data[y].charAt(x);
      if (imgMap[c]) {
        var img = document.createElement("img");
        img.src = imgMap[c];
        img.style.position = 'absolute';
        img.style.left = (x * 100) + "px";
        img.style.top = (y * 80 - 50) + "px";
        div.appendChild(img);
      }
    }
  }
}

var terrainData = [
  "ssseggggeeeesssss",
  "swseggwwwwwesssss",
  "ssseggggwwwesssss",
  "sssegggwwwwesssss",
  "ssseggwwwwwesssss",
  "sssssgggggggsssss",
  "essssssssssssssss",
  "sssssssssssssssss",
  "ggggggggggggsssss",
  "wwwwwwwwwwwwsssss",
  "wwwwwwwwwwwwsssss",
  "wWWWWWWWWWwwsssss",
];
