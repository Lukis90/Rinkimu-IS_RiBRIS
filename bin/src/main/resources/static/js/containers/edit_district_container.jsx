var EditDistrictContainer = React.createClass({
     getInitialState: function() {
        return {
            district: {
                name: '',
                address: '',
                numOfVoters: ''
            }
        }
    },

handleSaveClick: function(e) {
    e.preventDefault();
    var self = this;
    console.log(this.state.district)
        axios.post('/polling-districts/', this.state.district).then(function () {
            console.log('Apylinkė atnaujinta');
            self.context.router.push('/apylinkes');
        });
},

componentDidMount: function() {
    var self = this;
    var districtId = this.props.params.districtId;
      axios.get('/polling-districts/' + districtId).then(function (response) {
        self.setState({ district: response.data });
    });
},

handleFieldChange: function(fieldName) {
    var self = this;
    return function(e) {
      var district = self.state.district;
      district[fieldName] = e.target.value;
      self.setState({ district: district });
    };
},

handleCancelClick() {
    this.context.router.push('/apylinkes');
},

render: function() {
    return (
        <EditDistrictComponent
            district={this.state.district}
            onSaveClick={this.handleSaveClick}
            onCancelClick={this.handleCancelClick}
            onFieldChange={this.handleFieldChange}
        />
     );
}

});

EditDistrictContainer.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

window.EditDistrictContainer = EditDistrictContainer;