const React = require('react');
const axios = require('axios');
const AddParty = require('../presentations/add-party-presentation');

var AddPartyContainer = React.createClass({
    getInitialState: function() {
        return {
          party: {
        	id:'',
            name: '',
            partyNo: ''
          },
          candidates:[],
          isValid: false,
          text: ''
        }
    },

    componentDidMount: function() {
        var self = this;
        if (self.props.params.partyId != undefined) {
          axios.get('http://localhost:8090/parties/' + this.props.params.partyId).then(function (response) {
        	  self.setState({
        		  party: response.data,
        		  candidates: response.data.candidates
            });
          });
        }
      },

    // Added for CSV import
    handleUploadMultiCandidateFile: function( file ) {
        this.setState( { multiCandidateFile: file });
    },

    handleSaveClick: function(e) {
      e.preventDefault();
      var self = this;
      var data = new FormData();
      var config = {
      	headers: {
      		'Content-Type': 'multipart/form-data'
      	}
      };
      data.append( 'id', self.state.party.id);
      data.append( 'name', self.state.party.name );
      data.append( 'partyNo', self.state.party.partyNo );


      // Creating party with CSV candidate list
      if(this.state.isValid) {
        if (self.state.multiCandidateFile) {
        	data.append( 'file', self.state.multiCandidateFile );

        	axios.post('http://localhost:8090/parties/csv/', data, config).then(function (response) {
            	console.log("Party and CSV added.");
            	self.context.router.push('/admin/parties/');
            }).catch( function( error ) {
            	console.error( error );
            });

        } else {
        	// Creating party without CSV candidate list
        	axios.post('http://localhost:8090/parties/', data, config).then(function (response) {
            	console.log("Party added (no CSV).");
            	self.context.router.push('/admin/parties/');
            }).catch( function( error ) {
            	console.error( error );
            });
        }
      } else {
        this.setState({
          text: "Ištaisykite klaidas"
        });
      }
    },

    handleValidStateChange: function(isValid) {
      this.setState({
        isValid: isValid
      });
    },

//    Original code (before CSV import):
//    handleSaveClick: function(e) {
//      e.preventDefault();
//      var self = this;
//      var party = this.state.party
//      axios.post('http://localhost:8090/parties/', party).then(function (response) {
//        self.context.router.push('/parties/');
//      });
//    },

    handleFieldChange: function(fieldName) {
        var self = this;
        return function(e) {
          var party = self.state.party;
          party[fieldName] = e.target.value;
          self.setState({
            party: party
          });
        };
    },

    handleCancelClick() {
        this.context.router.push('/admin/parties');
    },

    handleDeleteCandidates: function(){
    	var self = this;
    	axios.delete('http://localhost:8090/candidates/by-party/' + this.state.party.id);
    },

    render: function() {
        return (
            <AddParty
              text={this.state.text}
              handleValidStateChange={this.handleValidStateChange}
              party={this.state.party}
            	onUploadMultiCandidateFile={this.handleUploadMultiCandidateFile}
              candidates={this.state.candidates}
            	onSaveClick={this.handleSaveClick}
              onCancelClick={this.handleCancelClick}
              onFieldChange={this.handleFieldChange}
            	onDeleteClick={this.handleDeleteCandidates}
            	partyId={this.props.params.partyId}
            />
        );
    }
});

AddPartyContainer.contextTypes = {
    router: React.PropTypes.object.isRequired,
};

module.exports = AddPartyContainer;
