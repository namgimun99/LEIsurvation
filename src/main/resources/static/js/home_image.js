var imgArray = new Array();
imgArray[0]="img/leisure1.jpg";
imgArray[1]="img/leisure2.jpg";
imgArray[2]="img/leisure3.jpg";
imgArray[3]="img/leisure4.jpg";
imgArray[4]="img/leisure5.jpg";

var num = 1;
function showImage(){
    var num = Math.round(Math.random()*4);
    var objImg = document.getElementById("photo");
    objImg.src = imgArray[num];
    setTimeout(showImage, 1500);
}

function changePic(idx){

    if(idx){
        if(num == 5) return;
        num++;
        var objImg = document.getElementById("photo");
        objImg.setAttribute("src", "img/leisure" + num + ".jpg");
    }else{
        if(num == 1) return;
        num--;
        var objImg = document.getElementById("photo");
        objImg.setAttribute("src", "img/leisure" + num + ".jpg");
    }
}

