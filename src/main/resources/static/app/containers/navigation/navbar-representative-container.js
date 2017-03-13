const React = require('react');
const axios = require('axios');
const Modal = require('react-bootstrap');
const NavPresentation = require('../../presentations/navigation/navbar-rep-presentation');
const ResultsOptions = require('../../util/options-for-menu/results-options');

var NavRepContainer = React.createClass({

	  getInitialState: function() {
	    return {
	      options: ResultsOptions,
				districtId: 0,
				constituencyId: 0,
				results: {
	        singleMandateVotes: [],
	        partyVotes: [],
	        ratingMandateVotes: [],
					spoiledSingle: '',
					spoiledMulti: ''
	      }
	    };
	  },

		handleSingleChange: function(e) {
	    var count = e.target.value;
			var results = this.state.results;
			results.spoiledSingle = count;
	    this.setState({
	      results: results
	    });
	  },

    handleMultiChange: function(e) {
      var count = e.target.value;
			var results = this.state.results;
			results.spoiledMulti = count;
      this.setState({
        results: results
      });
    },

		handleDistrictChange(districtId, constituencyId) {
			this.setState({
				districtId: districtId,
				constituencyId: constituencyId
			});
		},

		handleVotesReport(fieldName, votes) {
			var results = this.state.results;
      results[fieldName] = votes;
      this.setState({
        results: results
      });
	  },

		handleSaveClick() {
			console.log("siuciama");
			var results = this.state.results;
			for (var i = 0; i < candidatesList.length; i++) {
				var data = {
					district: {
						id: this.state.districtId
					},
					numberOfVotes: results.singleMandateVotes[i],
					candidate: {
						id: candidatesList[i].id
					}
				}
				axios.post('http://localhost:8090/candidates-results/single-mandate/', data);
			}
			for (var i = 0; i < partiesList.length; i++) {
				var data = {
					numberOfVotes: results.partyVotes[i],
					party: {
						id: partiesList[i].id
					},
					district: {
						id: this.state.districtId
					}
				}
				axios.post('http://localhost:8090/party-results/', data);
			}
			var dataList = {
				spoiledSingle: this.state.spoiledSingle,
				spoiledMulti: this.state.spoiledMulti
			}
			axios.post('http://localhost:8090/polling-districts/spoiled-ballots/'+this.state.districtId, dataList)
		},

	  render: function() {
			var self = this;

			const childs = React.Children.map(this.props.children,
	      function(child) {
	        return React.cloneElement(child, {
	          handleVotesReport: self.handleVotesReport,
						results: self.state.results,
						setIds: self.handleDistrictChange,
						districtId: self.state.districtId,
						constituencyId: self.state.constituencyId,
						onSaveClick: self.handleSaveClick,
						onSingleChange: self.handleSingleChange,
						onMultiChange: self.handleMultiChange
	        })
	      }
	    );
	    return (
	      <div>
	        <NavPresentation
						handleVotesReport={this.handleVotesReport}
	          options={this.state.options}
	          childs={childs}
	        />
	      </div>
	    );
	  }
	});

NavRepContainer.contextTypes = {
	  router: React.PropTypes.object.isRequired,
	};

	module.exports = NavRepContainer;
