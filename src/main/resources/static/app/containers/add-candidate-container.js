const React = require('react');
const axios = require('axios');
const AddCandidate = require('../presentations/add-candidate-presentation');

var AddCandidateContainer = React.createClass({
  getInitialState: function() {
    return {
      id: '',
      name: '',
      surname: '',
      birthDate: '',
      biography: '',
      party: {
        id: 1
      },
      parties: []
    };
  },

  componentDidMount: function() {
    var self = this;
    axios.get('http://localhost:8090/parties/').then(function(response) {
      self.setState({
        parties: response.data,
        party: {
        	id: response.data[0].id
        }
      });
    });
    if (this.props.params.candidateId != undefined) {
	      axios.get('http://localhost:8090/candidates/' + this.props.params.candidateId).then(function (response) {
	    	  self.setState({
	    		  id: response.data.id,
        	  name: response.data.name,
            surname: response.data.surname,
            birthDate: response.data.birthDate,
            biography: response.data.biography
	        });
	      });
	  }
  },

  handleNameChange: function(e) {
    this.setState({
      name: e.target.value
    });
  },

  handleSurnameChange: function(e) {
    this.setState({
      surname: e.target.value
    });
  },

  handleDateChange: function(e) {
    this.setState({
      birthDate: e.target.value
    });
  },

  handlePartyChange : function(e){
    var partyId = parseInt(e.target.value);
    this.setState({
      party: {
        id: partyId
      }
    });
  },

  handleBiographyChange: function(e) {
    this.setState({
      biography: e.target.value
    });
  },

  handleSaveClick: function(e) {
    e.preventDefault();
    var self = this;
    var elementsList = {
      id: this.state.id,
      name: this.state.name,
      surname: this.state.surname,
      birthDate: this.state.birthDate,
      party: {
        id: this.state.party.id
      },
      biography: this.state.biography
    };
    axios.post('http://localhost:8090/candidates/', elementsList).then(function () {
      self.context.router.push('/admin/candidates');
    });
  },

  handleCancelClick() {
    this.context.router.push('/admin/candidates');
  },

  render: function() {
    return (
      <AddCandidate
        onBiographyChange={this.handleBiographyChange}
        onDateChange={this.handleDateChange}
        birthDate={this.state.birthDate}
        onNameChange={this.handleNameChange}
        name={this.state.name}
        onSurnameChange={this.handleSurnameChange}
        surname={this.state.surname}
        party={this.state.party}
        parties={this.state.parties}
        onPartyChange={this.handlePartyChange}
        onSaveClick={this.handleSaveClick}
        onCancelClick={this.handleCancelClick}
      	candidateId={this.props.params.candidateId}
      />
    );
  }
});

AddCandidateContainer.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

module.exports = AddCandidateContainer;
