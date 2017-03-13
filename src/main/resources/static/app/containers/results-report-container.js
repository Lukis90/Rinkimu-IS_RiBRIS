const React = require('react');
const axios = require('axios');
const ResultsReport = require('../presentations/results-report-presentation');

var ResultsReportContainer = React.createClass({
    getInitialState: function() {
        return {
          district: {
            id: this.props.districtId
          },
          constituencyId: this.props.constituencyId,
          districtName: '',
          candidatesList: [],
          partiesList: [],
          results: this.props.results
        }
    },

    componentWillMount: function() {
      var self = this;
      axios.get('http://localhost:8090/candidates/by-constituency/' + this.state.constituencyId).then(function(response) {
        candidatesList = response.data;
        axios.get('http://localhost:8090/polling-districts/' + self.state.district.id).then(function(response) {
          districtName = response.data.name;
          axios.get('http://localhost:8090/parties/').then(function(response) {
            partiesList = response.data;
            self.setState({
              districtName: districtName,
              candidatesList: candidatesList,
              partiesList: partiesList
            });
          });
        });
      });
    },

    handleCancelClick() {
        this.context.router.push('/representative/results/single');
    },

    render: function() {
      return (
        <ResultsReport
          districtName={this.state.districtName}
          candidatesList={this.state.candidatesList}
          partiesList={this.state.partiesList}
          onCancelClick={this.handleCancelClick}
          results={this.props.results}
          onSaveClick={this.props.onSaveClick}
        />
      );
    }
});

ResultsReportContainer.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

module.exports = ResultsReportContainer;
