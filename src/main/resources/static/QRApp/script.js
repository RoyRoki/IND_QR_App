function showDiv(divId) {
    var div1 = document.getElementById("div1");
    var div2 = document.getElementById("div2");

    var genQRButton = document.querySelector('.genQR');
    var savedQRButton = document.querySelector('.savedQR');

 if(window.innerWidth < 768) {
    if(divId === 'genQR') {
        div1.style.display = 'block';
        div2.style.display = 'none';

        genQRButton.classList.remove('toggled');
        savedQRButton.classList.add('toggled');

    } else if(divId === 'savedQR') {
        div1.style.display = 'none';
        div2.style.display = 'block';

        genQRButton.classList.add('toggled');
        savedQRButton.classList.remove('toggled');

    }
 } else {
    if (divId === 'genQR') {
        if(div2.classList.contains('inactive')) {
            div2.classList.remove('inactive');
            div2.classList.add('active');

            savedQRButton.classList.remove('toggled');

        } else if(div1.classList.contains('inactive')) {
            div1.classList.remove('inactive');
            div1.classList.add('active');

            genQRButton.classList.remove('toggled');
        } else {
            div1.classList.add('active');

            genQRButton.classList.remove('toggled');
            savedQRButton.classList.add('toggled');

            div2.classList.remove('active');
            div2.classList.add('inactive');
        }
 
    } else if (divId === 'savedQR') {
        if(div1.classList.contains('inactive')) {
            div1.classList.remove('inactive');
            div1.classList.add('active');

            genQRButton.classList.remove('toggled');

        } else if(div2.classList.contains('inactive')) {
            div2.classList.remove('inactive');
            div2.classList.add('active');

            savedQRButton.classList.remove('toggled');

        } else {
            div2.classList.add('active');
            div1.classList.remove('active');
            div1.classList.add('inactive');

            savedQRButton.classList.remove('toggled');
            genQRButton.classList.add('toggled');
        }
    }
 }
}
// Ensure both divs are shown side by side on larger screens
window.addEventListener('resize', function() {
    if (window.innerWidth >= 768) {
        document.getElementById('div1').style.display = 'block';
        document.getElementById('div2').style.display = 'block';
        document.getElementById('div1').classList.remove('inactive');
        document.getElementById('div2').classList.remove('inactive');
    } else {
        document.getElementById('div1').style.display = 'block';
        document.getElementById('div2').style.display = 'none';
    }
});

// Initialize the visibility based on the current window size
if (window.innerWidth >= 768) {
    document.getElementById('div1').style.display = 'block';
    document.getElementById('div2').style.display = 'block';
} else {
    document.getElementById('div1').style.display = 'block';
    document.getElementById('div2').style.display = 'none';
}

function downloadQR(imageId) {
    var qrImage = document.getElementById(imageId);
    if (qrImage) {
        var link = document.createElement('a');
        link.href = qrImage.src;
        link.download = qrImage.id.replace("qrImage-","")+'-QRApp'+'.png';
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    } else {
        console.error('QR image not found with id:', imageId);
    }
}