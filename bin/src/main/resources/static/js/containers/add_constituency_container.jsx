var AddConstituencyContainer = React.createClass({
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
        axios.post('/constituencies/', this.state.constituency).then(function () {
            console.log('Apygarda pridėta');
            self.context.router.push('/apygardos');
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
            <AddConstituencyComponent
                constituency={this.state.constituency}
                onSaveClick={this.handleSaveClick}
                onCancelClick={this.handleCancelClick}
                onFieldChange={this.handleFieldChange}
            />
        );
    }
});

AddConstituencyContainer.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

window.AddConstituencyContainer = AddConstituencyContainer;

