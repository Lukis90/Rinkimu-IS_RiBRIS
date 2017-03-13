const React = require('react');
const axios = require('axios');
const  ActivityListPresentation = require('../../presentations/lists/activity-list-presentation');

var ActivityListContainer = React.createClass({
  getInitialState: function() {
    return ({
    	districts: [],
        activity: [],
        percentOfAllVoters: []
    });
  },
  
  componentWillMount: function(){
	  var self = this;
	  var districts;
	  var activity;
	  axios.get('http://localhost:8090/polling-districts/')
	  	.then(function(response){
	  			districts = response.data;
	  	}).then(function(){
	  		axios.get('http://localhost:8090/polling-districts/total-votes/')
	  		.then(function(response){
	  			activity = response.data;
	  		}).then(function(){
	  			axios.get('http://localhost:8090/polling-districts/total-votes-percent/')
	  			.then(function(response){
	  				self.setState({
	  					districts: districts,
	  					activity: activity,
	  					percentOfAllVoters: response.data
	  				});
	  			})
	  		})
	  	})
	  },

  render: function() {
	  console.log(this.state);
	  return (
      <ActivityListPresentation
        districts={this.state.districts}
      	activity={this.state.activity}
      	percents={this.state.percentOfAllVoters}
      />
    );
  }
});

ActivityListContainer.contextTypes = {
	    router: React.PropTypes.object.isRequired
	};
  
module.exports = ActivityListContainer;
