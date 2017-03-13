var EditRepresentativeContainer = React.createClass({
    getInitialState: function() {
        return {
            representative: {
                name: '',
                surname: ''
            }
        }
    },

handleSaveClick: function(e) {
    e.preventDefault();
    var self = this;
    axios.put('/representatives/' + this.state.representative.id, this.state.representative).then(function () {
        console.log('Atstovas atnaujintas');
        self.context.router.push('/atstovai');
      });
},

componentDidMount: function() {
    var self = this;
    var representativeId = this.props.params.representativeId;
      axios.get('/representatives/' + representativeId).then(function (response) {
        self.setState({ representative: response.data });
      });
},

handleFieldChange: function(fieldName) {
    var self = this;
    return function(e) {
      var representative = self.state.representative;
      representative[fieldName] = e.target.value;
      self.setState({ representative: representative });
    };
},

handleCancelClick() {
        this.context.router.push('/atstovai');
},

render: function() {
    return (
        <EditConstituencyComponent
            representative={this.state.representative}
            onSaveClick={this.handleSaveClick}
            onCancelClick={this.handleCancelClick}
            onFieldChange={this.handleFieldChange}
        />
     );
    }
});

EditRepresentativeContainer.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

window.EditRepresentativeContainer = EditRepresentativeContainer;