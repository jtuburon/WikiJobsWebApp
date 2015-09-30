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
    network.on("selectNode", function (params) {
        start_node_id= params.nodes[0];
        n= findNode(start_node_id);
        if(n){
            //console.log(n.pageURL);
            $("#page_title").text(n.label);
            $("#page_iframe").attr("src", n.pageURL+"?printable=yes");
            $("#wikiPageModal").modal('show');
        }
    });
}

function findNode(node_id){
    var j = 0;
    var found=false;
    node = null;
    while (j < nodes.length && !found) {
        if (nodes[j].id == node_id) {
            node= nodes[j];
            found = true;
        } else {
            j++;
        }
    }
    return node;
}

// Called when the Visualization API is loaded.
function filterAndDraw() {
    filter = $("#filter").val();
    temp_nodes=[];
    temp_edges=[];
    
    if (filter!='all'){
        $.each(edges, function (index, e) {
            if (e.name.indexOf(filter)>=0){
                temp_edges.push(e);
                start_node_id= e.from;
                end_node_id= e.to;
                $.each(nodes, function (index, n) {
                    if(n.id==start_node_id || n.id==end_node_id ){
                        if(temp_nodes.indexOf(n)<0){
                            temp_nodes.push(n);
                        }
                    }
                });
            }
        });        
    }else{
        temp_nodes= nodes;
        temp_edges= edges;
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