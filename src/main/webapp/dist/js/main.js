function init() {
    $('.datepicker').datepicker({
        startDate: '-3d'
    });
}

function runJob() {
    countryName = $("#countryName").val();
    fromDate = $("#fromDate").val();
    toDate = $("#toDate").val();

    $.post("webresources/WikiJob/run",
            {
                countryName: countryName,
                fromDate: fromDate,
                toDate: toDate,
            },
            function (graph) {
                draw(graph);
                console.log(graph);
            }
    );

}

var nodes = null;
var edges = null;
var network = null;

// Called when the Visualization API is loaded.
function draw(graph) {
    // create people.
    // value corresponds with the age of the person
    nodes = graph.nodes;

    // create connections between people
    // value corresponds with the amount of contact between two people
    edges = graph.edges;
    dir = "dist/images/"
    $.each(nodes, function (index, node) {
        node.shape = 'circularImage';
        if (node.kind == 'country') {
            node.image = dir + 'country.png';
        } else {
            node.image = dir + 'person.png';
        }

        /*if(node.kind=='country'){
         node.group ='countries';
         }else{
         node.group ='persons';
         }*/
    });

    $.each(edges, function (index, edge) {
        edge.font = {align: 'top'};
    });

    // create a network
    var container = document.getElementById('page-wrapper');
    var data = {
        nodes: nodes,
        edges: edges
    };
    var options = {
        groups: {
            countries: {
                shape: 'icon',
                icon: {
                    face: 'Ionicons',
                    code: '\uf276',
                    size: 50,
                    color: '#f0a30a'
                }
            },
            persons: {
                shape: 'icon',
                icon: {
                    face: 'FontAwesome',
                    code: '\uf007',
                    size: 50,
                    color: '#aa00ff'
                }
            }
        }

    };
    network = new vis.Network(container, data, options);
}