var EditConstituencyContainer = React.createClass({
    getInitialState: function() {
        return {
            constituency: {
                name: ''
            }
        }
    },

handleSaveClick: function(e) {
    e.preventDefault();
    var self = this;
    axios.put('/constituencies/' + this.state.constituency.id, this.state.constituency).then(function () {
        console.log('Apygarda atnaujinta');
        self.context.router.push('/apygardos');
      });
},

componentDidMount: function() {
    var self = this;
    var constituencyId = this.props.params.constituencyId;
      axios.get('/constituencies/' + constituencyId).then(function (response) {
        self.setState({ constituency: response.data });
      });
},

handleFieldChange: function(fieldName) {
    var self = this;
    return function(e) {
      var constituency = self.state.constituency;
      constituency[fieldName] = e.target.value;
      self.setState({ constituency: constituency });
    };
},

handleCancelClick() {
        this.context.router.push('/apygardos');
},

render: function() {
    return (
        <EditConstituencyComponent
            constituency={this.state.constituency}
            onSaveClick={this.handleSaveClick}
            onCancelClick={this.handleCancelClick}
            onFieldChange={this.handleFieldChange}
        />
     );
    }
});

EditConstituencyContainer.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

window.EditConstituencyContainer = EditConstituencyContainer;