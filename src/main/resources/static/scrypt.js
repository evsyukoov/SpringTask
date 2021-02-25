var inner = document.getElementById("inner");
var butt = document.getElementById("butt");
inner.hidden = true;

butt.onclick = function (event) {
    if (event.target!= butt)
        return ;
    inner.hidden = !inner.hidden;
    if (inner.hidden)
    {
        butt.classList.add('hide');
        butt.classList.remove('show');
    }
    else{
        butt.classList.add('show');
        butt.classList.remove('hide');
    }
}