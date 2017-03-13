var ConstituenciesListContainer = React.createClass({
    getInitialState: function() {
        return {
            constituencies: []
        };
    },

 componentWillMount: function() {
    var self = this;
    axios.get('/constituencies/')
    .then(function (response) {
        self.setState({ 
            constituencies: response.data 
        });
    });
},


handleAddConstituency() {
    this.context.router.push('/apygardos/prideti');
},

handleConstituencyEdit: function(constituency) {
    var self = this;
    return function() {
            self.context.router.push('/apygardos/redaguoti/' + constituency.id);
    }
},

 handleConstituencyRemove: function(constituency) { 
        var self = this; 
        return function() { 
          axios.delete('/constituencies/'+ constituency.id).then(function(response) { 
              console.log('Apygarda ištrinta'); 
              axios.get('/constituencies/') 
              .then(function (response) { 
                  self.setState({  
                      constituencies: response.data  
                  }); 
              }); 
          }); 
        };
      },

      handleDistrictsList: function(constituency) {
        var self = this;
        return function() {
          self.context.router.push('/apygarda/' + constituency.id + '/apylinkes');
        }
      },



    render: function() {
        return (
            <ConstituenciesListComponent 
                constituencies={this.state.constituencies} 
                onAddClick={this.handleAddConstituency} 
                onEditItem={this.handleConstituencyEdit}
                onRemoveItem={this.handleConstituencyRemove}
                onDistrictsList={this.handleDistrictsList} 
            />
        );
    }
});

ConstituenciesListContainer.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

window.ConstituenciesListContainer = ConstituenciesListContainer;