var AddRepresentativeContainer = React.createClass({
    getInitialState: function() {
        return {
            name: '',
            surname: '',
            district: 1,
            districts: []
        };
    },

handleSaveClick: function(e) {
    e.preventDefault();
    var self = this;
    var districtId = {id : this.state.district};
    var elementsList = {
        name: this.state.name,
        surname: this.state.surname,
        district: districtId
    };

    axios.post('/representatives/', elementsList).then(function () {
        console.log('Atstovas pridėtas');
        self.context.router.push('/atstovai');
    });
},

componentWillMount: function() {
   var self = this;
   axios.get('/polling-districts/').then(function(response) {
        self.setState({
            districts: response.data,
            district:  response.data[0].id
        });
   });
},

HandleDistrictChange : function(e){
    var districtId = parseInt(e.target.value);
    this.setState({districts : districtId}); 
},

HandleNameChange: function(e) {
    this.setState({name: e.target.value});
},

HandleSurnameChange: function(e) {
    this.setState({surname: e.target.value});
},

handleCancelClick() {
    this.context.router.push('/atstovai');
},

render: function() {
    return (
        <AddRepresentativeComponent
            onNameChange={this.HandleNameChange}
            name={this.state.name}
            onSurnameChange={this.HandleSurnameChange}
            surname={this.state.surname}
            district={this.state.district}
            districts={this.state.districts}
            onDistrictChange={this.HandleDistrictChange}
            onSaveClick={this.handleSaveClick}
            onCancelClick={this.handleCancelClick}
        />
    );
}

});

AddRepresentativeContainer.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

window.AddRepresentativeContainer = AddRepresentativeContainer;
