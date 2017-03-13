const React = require('react');
const axios = require('axios');
const SingleMandateResults = require('../presentations/add-single-mandate-results-presentation');

var AddSingleMandateResults = React.createClass({
  getInitialState: function() {
    return {
      district: {
        id: 1
      },
      constituencyId: 1,
      districts: [],
      candidatesList: [],
      voteArray: [],
      votesEnteredState: []
    };
  },

  componentWillMount: function() {
    var self = this;
    axios.get('http://localhost:8090/polling-districts/').then(function(response) {
      self.setState({
        districts: response.data,
        constituencyId: response.data[0].constituencyId,
        district: {
          id: response.data[0].id
        }
      });
      self.props.setIds(response.data[0].id, response.data[0].constituencyId);
      axios.get('http://localhost:8090/candidates/by-constituency/' + self.state.constituencyId).then(function(response) {
        self.setState({
          candidatesList: response.data,
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
      axios.get('http://localhost:8090/candidates/by-constituency/' + constituencyId).then(function(response) {
        self.setState({
          constituencyId: constituencyId,
          candidatesList: response.data,
          district: {
            id: districtId
          }
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
    var candidatesList = this.state.candidatesList;
    var sum = this.state.votesEnteredState.reduce(function(acc, val) {
      return acc + val;
    }, 0);
    if (sum == (candidatesList.length)) {
      self.props.handleVotesReport('singleMandateVotes', self.state.voteArray);
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
        onSingleChange={this.props.onSingleChange}
        results={this.props.results}
      />
    );
  }
});

AddSingleMandateResults.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

module.exports = AddSingleMandateResults;
