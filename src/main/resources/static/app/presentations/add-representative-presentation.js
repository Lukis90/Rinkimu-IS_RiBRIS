const React = require('react');
const TextValidator = require('../util/validation/text-validator-container');

var AddRepresentativePresentation = React.createClass({
  render: function() {
    if(this.props.representativeId == undefined) {
      return (
        <form autoComplete="off">
          <h4 className="alert alert-info">Registruoti naują atstovą</h4>
          <br />
          <div className="form-group">
            <label>Vardas</label>
            <TextValidator>
              <input className="form-control" type="text" value={this.props.name} onChange={this.props.onNameChange} />
            </TextValidator>
          </div>
          <div className="form-group">
            <label>Pavardė</label>
            <TextValidator>
              <input className="form-control" type="text" value={this.props.surname} onChange={this.props.onSurnameChange} />
            </TextValidator>
          </div>
          <div className="form-group">
            <h4 className="alert alert-info">Atstovas bus priskirtas <b>{this.props.districtName}</b> apylinkei</h4>
          </div>
          <button className="btn btn-success btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onSaveClick}>Priskirti</button>
          <button className="btn btn-danger btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onCancelClick}>Atšaukti</button>
        </form>
      );
    } else {
      return (
        <form autoComplete="off">
          <h4 className="alert alert-info">Redaguoti {this.props.districtName} apylinkės atstovą</h4>
          <br />
          <div className="form-group">
            <label>Vardas</label>
            <TextValidator>
              <input className="form-control" type="text" value={this.props.name} onChange={this.props.onNameChange} />
            </TextValidator>
          </div>
          <div className="form-group">
            <label>Pavardė</label>
            <TextValidator>
              <input className="form-control" type="text" value={this.props.surname} onChange={this.props.onSurnameChange} />
            </TextValidator>
          </div>
          <button className="btn btn-success btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onSaveClick}>Redaguoti</button>
          <button className="btn btn-danger btn-sm" style={{ marginRight: '20px' }} onClick={this.props.onCancelClick}>Atšaukti</button>
        </form>
      );
    }
  }
});

module.exports = AddRepresentativePresentation;
