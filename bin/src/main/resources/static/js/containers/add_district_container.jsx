var AddDistrictContainer = React.createClass({
    getInitialState: function() {
        return {
           name: '',
           address: '',
           numOfVoters: '',
           constituency: 1,
           constituencies: []
        };
    },

handleSaveClick: function(e) {
    e.preventDefault();
    var self = this;
    var constituencyId = {id : this.state.constituency};
    var dataList = {
        name: this.state.name,
        address: this.state.address,
        numOfVoters: this.state.numOfVoters,
        constituency: constituencyId
    };

    axios.post('/polling-districts/', dataList).then(function (response) {
        console.log('Apylinke prideta');
        self.context.router.push('/apylinkes');
    });
},
 

componentWillMount: function() {
   var self = this;
   axios.get('/constituencies/').then(function(response) {
        self.setState({
            constituencies: response.data,
            constituency:  response.data[0].id
        });
   });
},
 
HandleConstituencyChange : function(e){
    var constituencyId = parseInt(e.target.value);
    this.setState({constituency : constituencyId}); 
},

HandleNameChange: function(e) {
    this.setState({name: e.target.value});
},

HandleAddressChange: function(e) {
    this.setState({address: e.target.value});
},

HandleVotersChange: function(e) {
    this.setState({numOfVoters: e.target.value});
},

handleCancelClick() {
    this.context.router.push('/apylinkes');
},

render: function() {
    return (
        <AddDistrictComponent
            onNameChange={this.HandleNameChange}
            name={this.state.name}
            onAddressChange={this.HandleAddressChange}
            address={this.state.address}
            onVotersChange={this.HandleVotersChange}
            numOfVoters={this.state.numOfVoters}
            constituencies={this.state.constituencies}
            constituency={this.state.constituency}
            onSaveClick={this.handleSaveClick}
            onCancelClick={this.handleCancelClick}
            HandleConstituencyChange={this.HandleConstituencyChange} 
        />
    );
}

});

AddDistrictContainer.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

window.AddDistrictContainer = AddDistrictContainer;