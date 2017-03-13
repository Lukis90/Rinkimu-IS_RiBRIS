const React = require('react');
const axios = require('axios');
const SingleMandateResults = require('../presentations/add-single-mandate-results-presentation');

var AddSingleMandateResults = React.createClass({
  getInitialState: function() {
    return {
      district: {
        id: this.props.districtId
      },
      constituencyId: this.props.constituencyId,
      districts: [],
      candidatesList: [],
      voteArray: [],
      votesEnteredState: []
    };
  },

  componentWillMount: function() {
    var self = this;
    axios.get('http://localhost:8090/polling-districts/').then(function(response) {
      var districts = response.data;
      axios.get('http://localhost:8090/candidates/by-constituency/' + response.data[0].constituencyId)
      .then(function(response) {
        self.setState({
          candidatesList: response.data,
          districts: districts
        });
      });
    });
  },

  handleDistrictChange: function(e) {
    var districtId = parseInt(e.target.value);
    var self = this;
    var constituencyId = 0;

    axios.get('http://localhost:8090/polling-districts/' + districtId).then(function(response) {
      constituencyId = response.data.constituencyId;
      self.props.setIds(districtId, constituencyId);
      axios.get('http://localhost:8090/candidates/by-constituency/' + constituencyId).then(function(response) {
        self.setState({
          candidatesList: response.data
        });
      });
    });
  },

  handleResultsChange: function(index) {
    var self = this;
    return function(e) {
      var voteArray = self.state.voteArray;
      var enteredState = self.state.votesEnteredState;
      voteArray[index] = e.target.value;
      enteredState[index] = 1;

      self.setState({
        voteArray: voteArray,
        votesEnteredState: enteredState
      });
    };
  },

  handleSaveClick: function(e) {
    e.preventDefault();
    var self = this;
    var sum = this.state.votesEnteredState.reduce(function(acc, val) {
      return acc + val;
    }, 0);

    if (sum == (candidatesList.length-1)) {
      self.props.handleVotesReport('ratingVotes', self.state.voteArray);
      self.context.router.push('/representative/results/parties');
    } else {
      console.log("Alert");
    }
  },

  render: function() {
    return (
      <SingleMandateResults
        district={this.state.district}
        constituencyId={this.state.constituencyId}
        districts={this.state.districts}
        candidatesList={this.state.candidatesList}
        voteArray={this.state.voteArray}
        onDistrictChange={this.handleDistrictChange}
        onResultsChange={this.handleResultsChange}
        onSaveClick={this.handleSaveClick}
      />
    );
  }
});

AddSingleMandateResults.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

module.exports = AddSingleMandateResults;
