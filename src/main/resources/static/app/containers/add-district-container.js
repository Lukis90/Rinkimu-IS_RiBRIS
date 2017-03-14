const React = require('react');
const axios = require('axios');
const AddDistrictComponent = require('../presentations/add-district-presentation');

var AddDistrictContainer = React.createClass({
  getInitialState: function() {
    return {
      id: '',
      name: '',
      address: '',
      numOfVoters: '',
      constituencies: [],
      constituency: {
        id: 1
      },
      isValid: false,
      text: ''
    };
  },

  componentWillMount: function() {
    var self = this;
    axios.get('http://localhost:8090/constituencies/').then(function(response) {
      self.setState({
        constituencies: response.data,
        constituency: {
          id: response.data[0].id
        }
      });
    });
    if(this.props.params.districtId != undefined) {
	    axios.get('http://localhost:8090/polling-districts/'+ this.props.params.districtId).then(function(response) {
	    	self.setState({
	    		id: response.data.id,
	    		name: response.data.name,
	    		address: response.data.address,
	    		numOfVoters: response.data.numOfVoters
	    	});
	    });
    }
  },

  handleValidStateChange: function(isValid) {
    this.setState({
      isValid: isValid
    });
  },

  handleSaveClick: function(e) {
    e.preventDefault();
    var self = this;
    var dataList = {
      id: this.state.id,
      name: this.state.name.trim(),
      address: this.state.address,
      numOfVoters: this.state.numOfVoters,
      constituency: {
        id : this.state.constituency.id
      }
    };
    if(this.state.isValid) {
      axios.post('http://localhost:8090/polling-districts/', dataList).then(function (response) {
        self.context.router.push('/admin/districts/');
      });
    } else {
      this.setState({
        text: "Ištaisykite klaidas"
      });
    }
  },

  handleNameChange: function(e) {
    this.setState({
      name: e.target.value
    });
  },

  handleAddressChange: function(e) {
    this.setState({
      address: e.target.value
    });
  },

  handleVotersChange: function(e) {
    this.setState({
      numOfVoters: e.target.value
    });
  },

  handleConstituencyChange: function(e){
    var constituencyId = parseInt(e.target.value);
    this.setState({
      constituency: {
        id: constituencyId
      }
    });
},

  handleCancelClick() {
    this.context.router.push('/admin/districts/');
  },

  render: function() {
    return (
      <AddDistrictComponent
        text={this.state.text}
        handleValidStateChange={this.handleValidStateChange}
        onVotersChange={this.handleVotersChange}
        numOfVoters={this.state.numOfVoters}
        onAddressChange={this.handleAddressChange}
        address={this.state.address}
        onNameChange={this.handleNameChange}
        name={this.state.name}
        onConstituencyChange={this.handleConstituencyChange}
        constituency={this.state.constituency}
        constituencies={this.state.constituencies}
        onSaveClick={this.handleSaveClick}
        onCancelClick={this.handleCancelClick}
        districtId={this.props.params.districtId}
      />
    );
  }
});

AddDistrictContainer.contextTypes = {
  router: React.PropTypes.object.isRequired,
};

module.exports = AddDistrictContainer;
