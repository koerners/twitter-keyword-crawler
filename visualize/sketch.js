let data = [];

function preload() {
    crawlerData = loadTable('test.csv');
}

function setup() {


    var canvas = createCanvas(50, 20);
    canvas.parent('sketch-holder');
    noLoop();



    button = createButton('process');
    button.position(8, 5);
    button.mousePressed(processData);

    button1 = createButton('reload');
    button1.position(100, 5);
    button1.mousePressed(reloadPage);


}

function reloadPage() {
    document.location.reload(true);
}

function draw() {
    clear();
    for (let country of data) {
        var latlng = L.latLng(country.lat, country.lon);
        L.marker(latlng, title='test').addTo(mymap);
    }
}


function request(city) {

    $(document).ready(function () {
        const Url = 'https://nominatim.openstreetmap.org/search?format=json&limit=1&q=' + city;
        $.ajax({
            url: Url,
            type: "GET",
            success: function (result) {
                lat = result[0].lat;
                lon = result[0].lon;

                data.push({
                    lat,
                    lon
                });
                redraw();

            },
            error: function (error) {
                console.log("Error");
            }
        })
    })
}


function processData() {
    // request('munich');



    for (let row of crawlerData.rows) {
        let city = row.arr[0];

        request(city);

    }

}

