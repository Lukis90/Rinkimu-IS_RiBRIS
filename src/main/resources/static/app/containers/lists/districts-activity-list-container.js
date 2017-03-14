const React = require('react');
const axios = require('axios');
const DistrictsActivityListPresentation = require('../../presentations/lists/districts-activity-list-presentation');

var DistrictsActivityListContainer = React.createClass({
  getInitialState: function() {
    return ({
    	districts: []
    });
  },
  
  componentWillMount: function(){
	  var self = this;
	  axios.get('http://localhost:8090/polling-districts/activity/all/'+ this.props.params.constituencyId)
	  	.then(function(response){
	  		console.log(response.data);
	  		self.setState({
	  			districts: response.data
	  		});
	  	})
  },
  

  render: function() {
	  console.log(this.state);
	  return (
      <DistrictsActivityListPresentation
        districts={this.state.districts}
      />
    );
  }
});

//ActivityListContainer.contextTypes = {
//	    router: React.PropTypes.object.isRequired
//	};
  
module.exports = DistrictsActivityListContainer;
