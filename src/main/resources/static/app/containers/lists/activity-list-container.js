const React = require('react');
const axios = require('axios');
const  ActivityListPresentation = require('../../presentations/lists/activity-list-presentation');

var ActivityListContainer = React.createClass({
  getInitialState: function() {
    return ({
    	constituencies: []
    });
  },
  
  componentWillMount: function(){
	  var self = this;
	  axios.get('http://localhost:8090/constituencies/activity/all/')
	  	.then(function(response){
	  		self.setState({
	  			constituencies: response.data
	  		});
	  	})
	  	
	  },

  render: function() {
	  console.log(this.state);
	  return (
      <ActivityListPresentation
        constituencies={this.state.constituencies}
      />
    );
  }
});

ActivityListContainer.contextTypes = {
	    router: React.PropTypes.object.isRequired
	};
  
module.exports = ActivityListContainer;
