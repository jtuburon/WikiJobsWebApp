function init() {
    $('.datepicker').datepicker({
        startDate: '-3d'
    });
    $('#filter').change(function () {
        filterAndDraw();
    });
}

function runJob() {
    countryName = $("#countryName").val();
    fromDate = $("#fromDate").val();
    toDate = $("#toDate").val();

    $.ajax({
        type: "POST",
        url: "webresources/WikiJob/run",
        data: { countryName: countryName, fromDate: fromDate, toDate: toDate },
        dataType: "json",
        timeout:  5 * 60 * 1000, // in milliseconds
        success: function (graph) {
            draw(graph);
            $("#filter").prop('disabled', false);
        },
        error: function (request, status, err) {
            if (status == "timeout") {
                $("#filter").prop('disabled', true);
            }
        }
    });

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
        node.title = node.label;
        if (node.kind == 'country') {
            node.image = dir + 'country.png';
        } else {
            node.image = dir + 'person.png';
        }
    });

    $.each(edges, function (index, edge) {
        edge.font = {align: 'top'};
        edge.title = edge.name;
        //edge.label = edge.name;
    });

    // create a network
    var container = document.getElementById('page-wrapper');
    var data = {
        nodes: nodes,
        edges: edges
    };
    var options = {
        layout: {
            improvedLayout:false
        }
    };
    network = new vis.Network(container, data, options);
}

// Called when the Visualization API is loaded.
function filterAndDraw() {
    filter = $("#filter").val();
    temp_nodes=[];
    temp_edges=[];
    
    
    $.each(nodes, function (index, node) {
        temp_nodes.push(node);
    });

    $.each(edges, function (index, edge) {
        temp_edges.push(edge);
    });

    if (filter!='all'){
        i=0;
        edgesLen = temp_edges.length
        for(var i = 0;  i < edgesLen; i++) {
            e = temp_edges[i];
            if (e.name.indexOf(filter)<0) {                
                node_id= e.from;
                var removed =false;
                var j=0; 
                while(j<temp_nodes.length && !removed){
                    if(temp_nodes[j].id==node_id){
                        temp_nodes.splice(j, 1);
                        removed=true;
                    }else{
                        j++;
                    }                    
                }
            }
        }
    }
    
    dir = "dist/images/"
    $.each(temp_nodes, function (index, node) {
        node.shape = 'circularImage';
        node.title = node.label;
        if (node.kind == 'country') {
            node.image = dir + 'country.png';
        } else {
            node.image = dir + 'person.png';
        }
    });

    $.each(temp_edges, function (index, edge) {
        edge.font = {align: 'top'};
        edge.title = edge.name;
        //edge.label = edge.name;
    });

    // create a network
    var container = document.getElementById('page-wrapper');
    var data = {
        nodes: temp_nodes,
        edges: temp_edges
    };
    var options = {
        layout: {
            improvedLayout:false
        }
    };
    network = new vis.Network(container, data, options);
}