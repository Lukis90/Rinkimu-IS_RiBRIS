const React = require('react');
const axios = require('axios');
const PartiesListPresentation = require('../../presentations/lists/parties-list-presentation');

var PartiesListContainer = React.createClass({
  getInitialState: function() {
    return {
      parties: [],
      searchText: ''
    };
  },

  componentWillMount: function() {
    var self = this;
    axios.get('http://localhost:8090/parties/')
    .then(function (response) {
      self.setState({
        parties: response.data
      });
    });
  },

  handleSearchTextChange: function(e) {
    var text = e.target.value;
    this.setState({
      searchText: text
    });
  },

  handlePartyEdit: function(party) {
    var self = this;
    return function() {
      self.context.router.push('/admin/parties/edit/' + party.id);
    }
  },

  handlePartyRemove: function(party) {
    var self = this;
    return function() {
      axios.delete('http://localhost:8090/parties/'+ party.id).then(function() {
        axios.get('http://localhost:8090/parties/')
        .then(function (response) {
          self.setState({
            parties: response.data
          });
        });
      });
    };
  },

  handleCandidatesListClick: function(party) {
    var self = this;
    return function() {
      self.context.router.push('/admin/candidates/party/' + party.id);
    };
  },

  render: function() {
    return (
      <PartiesListPresentation
        searchText={this.state.searchText}
        onSearchTextChange={this.handleSearchTextChange}
        partiesList={this.state.parties}
        onEditItem={this.handlePartyEdit}
        onRemoveItem={this.handlePartyRemove}
        onCandidatesListClick={this.handleCandidatesListClick}
      />
    );
  }
});

PartiesListContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

module.exports = PartiesListContainer;
