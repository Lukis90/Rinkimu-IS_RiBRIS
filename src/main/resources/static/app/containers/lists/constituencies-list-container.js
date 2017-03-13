const React = require('react');
const axios = require('axios');
const ConstituenciesListPresentation = require('../../presentations/lists/constituencies-list-presentation');

var ConstituenciesListContainer = React.createClass({
  getInitialState: function() {
    return {
      constituencies: [],
      searchText: ''
    };
  },

  componentWillMount: function() {
    var self = this;
    axios.get('http://localhost:8090/constituencies/')
    .then(function (response) {
      self.setState({
        constituencies: response.data
      });
    });
  },

  handleSearchTextChange: function(e) {
    var text = e.target.value;
    this.setState({
      searchText: text
    });
  },

  handleConstituencyEdit: function(constituency) {
    var self = this;
    return function() {
      self.context.router.push('/admin/constituencies/edit/' + constituency.id);
    }
  },

  handleConstituencyRemove: function(constituency) {
    var self = this;
    return function() {
      axios.delete('http://localhost:8090/constituencies/'+ constituency.id).then(function() {
        axios.get('http://localhost:8090/constituencies/')
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
      self.context.router.push('/admin/districts/list/' + constituency.id);
    };
  },

  handleCandidatesList: function(constituency) {
    var self = this;
    return function() {
      self.context.router.push('/admin/candidates/constituency/' + constituency.id);
    };
  },

  render: function() {
    return (
      <ConstituenciesListPresentation
        searchText={this.state.searchText}
        constituencies={this.state.constituencies}
        onSearchTextChange={this.handleSearchTextChange}
        onEditItem={this.handleConstituencyEdit}
        onRemoveItem={this.handleConstituencyRemove}
        onDistrictsListClick={this.handleDistrictsList}
        onCandidatesListClick={this.handleCandidatesList}
      />
    );
  }
});

ConstituenciesListContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

module.exports = ConstituenciesListContainer;
