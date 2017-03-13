var RepresentativesListContainer = React.createClass({
    getInitialState: function() {
        return {
            representatives: []
        };
    },

 componentWillMount: function() {
    var self = this;
    axios.get('/representatives/')
    .then(function (response) {
        self.setState({ 
            representatives: response.data 
        });
    });
},


handleAddRepresentative() {
    this.context.router.push('/atstovai/prideti');
},

handleRepresentativeEdit: function(representative) {
    var self = this;
    return function() {
            self.context.router.push('/atstovai/redaguoti/' + representative.id);
    }
},

 handleRepresentativeRemove: function(representative) { 
        var self = this; 
        return function() { 
          axios.delete('/representatives/'+ representative.id).then(function(response) { 
              console.log('Atstovas ištrintas'); 
              axios.get('/representatives/') 
              .then(function (response) { 
                  self.setState({  
                      representatives: response.data  
                  }); 
              }); 
          }); 
        };
      },


    render: function() {
        return (
            <RepresentativesListComponent 
                representatives={this.state.representatives} 
                onAddClick={this.handleAddRepresentative} 
                onEditItem={this.handleRepresentativeEdit}
                onRemoveItem={this.handleRepresentativeRemove}
            />
        );
    }
});

RepresentativesListContainer.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

window.RepresentativesListContainer = RepresentativesListContainer;