const React = require('react');
const axios = require('axios');
const RepresentativesList = require('../../presentations/lists/representatives-list-presentation');

var RepresentativesContainer = React.createClass({
  getInitialState: function() {
    return {
      representatives: []
    };
  },

  componentWillMount: function() {
    var self = this;
    axios.get('http://localhost:8090/representatives/')
    .then(function (response) {
      self.setState({
          representatives: response.data,
      });
    });
  },

  handleRepresentativeEdit: function(representative) {
    var self = this;
    return function() {
      self.context.router.push('/admin/representatives/edit/' + representative.id);
    }
  },

  handleRepresentativeRemove: function(representative) {
    var self = this;
    return function() {
      axios.delete('http://localhost:8090/representatives/'+ representative.id).then(function() {
        axios.get('http://localhost:8090/representatives/').then(function (response) {
          self.setState({
            representatives: response.data
          });
        });
      });
    };
  },

  render: function() {
    return (
      <RepresentativesList
        representatives={this.state.representatives}
        onEditItem={this.handleRepresentativeEdit}
        onRemoveItem={this.handleRepresentativeRemove}
      />
    );
  }
});

RepresentativesContainer.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

module.exports = RepresentativesContainer;
