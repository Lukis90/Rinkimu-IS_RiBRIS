const React = require('react');
const axios = require('axios');
const PartyResults = require('../presentations/add-party-results-presentation');

var AddPartyResults = React.createClass({
  getInitialState: function() {
    return {
      district: {
        id: this.props.districtId
      },
      districts: [],
      partiesList: [],
      voteArray: [],
      votesEnteredState: [],
      valid: true
    };
  },

  componentWillMount: function() {
    var self = this;
    axios.get('http://localhost:8090/polling-districts/').then(function(response) {
      self.setState({
        districts: response.data,
      });
      axios.get('http://localhost:8090/parties/').then(function(response) {
        self.setState({
          partiesList: response.data,
        });
      });
    });
  },

  handleDistrictChange: function(e){
    var districtId = parseInt(e.target.value);
    var self = this;
    this.props.setIds(districtId, this.props.constituencyId);
    axios.get('http://localhost:8090/parties/').then(function(response) {
      self.setState({
        partiesList: response.data
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
    }
  },

  handleSaveClick: function(e) {
    e.preventDefault();
    var self = this;
    var partiesList = this.state.partiesList;
    var sum = this.state.votesEnteredState.reduce(function(acc, val) {
      return acc + val;
    }, 0);

    if (sum == partiesList.length) {
      var voteArray = this.state.voteArray;
      self.props.handleVotesReport('partyVotes', self.state.voteArray);
      self.context.router.push('representative/results/report');
    } else {
      console.log("Alert");
    }
  },

  render: function() {
    return (
      <PartyResults
        district={this.state.district}
        constituencyId={this.state.constituencyId}
        districts={this.state.districts}
        partiesList={this.state.partiesList}
        voteArray={this.state.voteArray}
        onDistrictChange={this.handleDistrictChange}
        onResultsChange={this.handleResultsChange}
        onMultiChange={this.props.onMultiChange}
        onSaveClick={this.handleSaveClick}
        results={this.props.results}
      />
    );
  }
});

AddPartyResults.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

module.exports = AddPartyResults;
