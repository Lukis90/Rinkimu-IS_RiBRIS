const React = require('react');
const axios = require('axios');
const SingleMandateDistrictListComponent = require('../../presentations/lists/single-mandate-districts-presentation');

var SingleMandateDistrictListContainer = React.createClass({
  getInitialState: function() {
    return {
      districts: []
    };
  },

  componentWillMount: function() {
    var self = this;
      axios.get('http://localhost:8090/candidates-results/single-mandate/districts-results-time/' + self.props.params.constituencyId)
      .then(function (response) {
        self.setState({
            districts: response.data,
        });
      });
  },

  render: function() {
    return (
      <SingleMandateDistrictListComponent
        districts={this.state.districts}
      />
    );
  }
});

SingleMandateDistrictListContainer.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

module.exports = SingleMandateDistrictListContainer;
