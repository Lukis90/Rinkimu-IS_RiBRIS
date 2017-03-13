const React = require('react');
const TextValidator = require('../util/validation/text-validator-container');

var AddCandidatePresentation = React.createClass({
  render: function() {
  var self = this;
  var greeting;
  var buttonText;
  var PartiesList = this.props.parties.map(function(party, index) {
      return (
          <option key={index} value={party.id}>{party.name}</option>
      );
  });

  if(this.props.candidateId  != undefined){
	     greeting = <h4>Redaguoti kandidatą</h4>;
		 buttonText = "Redaguoti";
  } else {
		 greeting = <h4>Registruoti naują kandidatą</h4>;
		 buttonText = "Registruoti";
	 }

  return (
		  
    <form autoComplete="off">
      {greeting}
      <br />
      <div className="form-group">
        <label>Vardas</label>
        <TextValidator>
          <input className="form-control" type="text" required value={this.props.name} onChange={this.props.onNameChange} />
        </TextValidator>
      </div>
      <div className="form-group">
        <label>Pavardė</label>
        <TextValidator>
          <input className="form-control" type="text" required value={this.props.surname} onChange={this.props.onSurnameChange} />
        </TextValidator>
      </div>
      <div className="form-group">
        <label>Gimimo data</label>
        <input className="form-control" type="text" placeholder="yyyy-mm-DD" value={this.props.birthDate} onChange={this.props.onDateChange} />
      </div>
      <div className="form-group">
        <label>Partija</label>
        <select className="form-control" value={this.props.party.id}
          onChange={this.props.onPartyChange}>
            {PartiesList}
        </select>
      </div>
      <div className="form-group">
        <label>Biografija</label>
        <input className="form-control" type="text" value={this.props.biography} onChange={this.props.onBiographyChange} />
      </div>
      <button className="btn btn-success btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onSaveClick}>{buttonText}</button>
      <button className="btn btn-danger btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onCancelClick}>Atšaukti</button>
    </form>
    );
  }
});

module.exports = AddCandidatePresentation;
